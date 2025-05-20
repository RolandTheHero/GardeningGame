package roland.discord_bot;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu.Builder;

import roland.games.gardening.DisplayHandler;
import roland.games.gardening.Garden;
import roland.games.gardening.Inventory;
import roland.games.gardening.Plant;
import roland.games.gardening.Seed;

public class DiscordDisplayHandler implements DisplayHandler<IReplyCallback> {
    static private final int PLANT_DISPLAY_PAGE_SIZE = 10;
    static private String formatUnixTime(long sec) {
        long min = sec/60;
        long hr = min/60;
        min -= hr*60;
        sec -= hr*3600 + min*60;
        String uptimeString = "";
        if (hr > 1) uptimeString += hr + " hours, ";
        else if (hr > 0) uptimeString += hr + " hour, ";
        if (min > 1) uptimeString += min + " minutes, ";
        else if (min > 0) uptimeString += min + " minute, ";
        if (sec == 1) uptimeString += sec + " second.";
        else uptimeString += sec + " seconds.";
        return uptimeString;
    }
    private MessageEmbed gardenEmbed(long userid, Garden garden) {
        var plots = garden.getPlots();
        String gardenMsgBox = plots.stream()
            .map(p -> ":seedling: " + p.seed().fullName() + " · Ready <t:" + p.harvestableAt() + ":R>")
            .collect(Collectors.joining("\n", "<@" + userid + ">'s garden\n\n", ""));
        if (plots.isEmpty()) gardenMsgBox = "<@" + userid + "> has not planted any seeds.";
        return new EmbedBuilder()
            .setColor(Main.kaiboColor)
            .setTitle("Garden")
            .appendDescription(gardenMsgBox)
            .build();
    }
    private StringSelectMenu gardenSeedSelectMenu(long userid) {
        Builder ssm = StringSelectMenu.create("plantseed:"+userid)
            .setPlaceholder("Select seed to plant");
        Main.gardeningGame.data().getInventory(userid).getSeeds().entrySet().stream()
            .filter(e -> e.getValue() > 0)
            .forEach(e -> ssm.addOption(e.getKey().fullName(), e.getKey().codeName(), "Owned: " + e.getValue(), Emoji.fromUnicode("U+1FAD8")));
        if (ssm.getOptions().isEmpty()) ssm.addOption("No available seeds", "none", "Use the forage command to find some!");
        return ssm.build();
    }
    private StringSelectMenu gardenHarvestSelectMenu(long userid) {
        Builder ssm = StringSelectMenu.create("harvestplant:"+userid)
            .setPlaceholder("Select plant to harvest");
        var garden = Main.gardeningGame.data().getGarden(userid);
        int gardenSize = garden.size();
        var plots = garden.getPlots();
        IntStream.range(0, gardenSize)
            .filter(i -> garden.canHarvest(i))
            .forEach(i -> ssm.addOption(plots.get(i).seed().fullName(), i + "", "This plant is ready to be harvested!", Emoji.fromUnicode("U+1FAB4")));
        if (ssm.getOptions().isEmpty()) ssm.addOption("No available plants", "-1", "Plant some seeds and wait for them to mature.");
        return ssm.build();
    }

    @Override
    public void forageSuccess(IReplyCallback cmdOrigin, Map<Seed, Integer> foragedSeeds) {
        String seedsMsgBox = foragedSeeds.entrySet().stream()
            .map(e -> "+" + e.getValue() + " " + e.getKey().fullName())
            .collect(Collectors.joining("\n", "```diff\n", "```"));
        MessageEmbed embed = new EmbedBuilder()
            .setColor(Main.kaiboColor)
            .setTitle("Forage")
            .addField("You received:", seedsMsgBox, false)
            .build();
        cmdOrigin.replyEmbeds(embed).queue();
    }

    @Override
    public void forageFailure(IReplyCallback cmdOrigin, long timeAvailable) {
        cmdOrigin.reply("You can forage again in " + formatUnixTime(timeAvailable - System.currentTimeMillis()/1000L)).queue();
    }

    @Override
    public void inventory(IReplyCallback cmdOrigin, long userid, Inventory inventory) {
        String seedsMsgBox = inventory.getSeeds().entrySet().stream()
            .filter(e -> e.getValue() > 0)
            .map(e -> ":beans: " + e.getValue() + " · " + e.getKey().fullName())
            .collect(Collectors.joining("\n", "<@" + userid + ">'s seed inventory\n\n", ""));
        if (inventory.getSeeds().isEmpty()) seedsMsgBox = "<@" + userid + "> does not have any seeds.";
        MessageEmbed embed = new EmbedBuilder()
            .setColor(Main.kaiboColor)
            .setTitle("Inventory")
            .appendDescription(seedsMsgBox)
            .build();
        cmdOrigin.replyEmbeds(embed).queue();
    }

    @Override
    public void garden(IReplyCallback cmdOrigin, long userid, Garden garden) {
        var reply = cmdOrigin.replyEmbeds(gardenEmbed(userid, garden));
        reply.addActionRow(gardenSeedSelectMenu(userid))
            .addActionRow(gardenHarvestSelectMenu(userid))
            .queue();
    }
    
    @Override
    public void plantSeedNotExistentFailure(IReplyCallback cmdOrigin) {
        if (cmdOrigin instanceof StringSelectInteractionEvent e) e.deferEdit().queue();
    }

    @Override
    public void plantSeedNotEnoughFailure(IReplyCallback cmdOrigin, Seed seed) {
        if (cmdOrigin instanceof StringSelectInteractionEvent) {
            cmdOrigin.reply("You need to have at least one " + seed.fullName() + " to plant it.").setEphemeral(true).queue();
        }
    }

    @Override
    public void plantSeedGardenFullFailure(IReplyCallback cmdOrigin) {
        if (cmdOrigin instanceof StringSelectInteractionEvent) {
            cmdOrigin.reply("You cannot plant anymore seeds in your garden — it's full!").setEphemeral(true).queue();
        }
    }

    @Override
    public void plantSeedSuccess(IReplyCallback cmdOrigin, Seed seed) {
        if (cmdOrigin instanceof StringSelectInteractionEvent e) {
            long userid = cmdOrigin.getUser().getIdLong();
            e.editMessageEmbeds(gardenEmbed(userid, Main.gardeningGame.data().getGarden(userid)))
                .setComponents(
                    ActionRow.of(gardenSeedSelectMenu(userid)),
                    ActionRow.of(gardenHarvestSelectMenu(userid))
                ).queue();
        }
    }

    @Override
    public void harvestNotReadyFailure(IReplyCallback cmdOrigin, int plotNum) {
        throw new UnsupportedOperationException("The 'harvestNotReadyFailure' method is uncallable.");
    }

    @Override
    public void harvestEmptyFailure(IReplyCallback cmdOrigin, int plotNum) {
        if (cmdOrigin instanceof StringSelectInteractionEvent e) e.deferEdit().queue();
    }

    @Override
    public void harvestSuccess(IReplyCallback cmdOrigin, Seed seed, Plant harvestedPlant) {
        if (cmdOrigin instanceof StringSelectInteractionEvent e) {
            long userid = cmdOrigin.getUser().getIdLong();
            e.editMessageEmbeds(gardenEmbed(userid, Main.gardeningGame.data().getGarden(userid)))
                .setComponents(
                    ActionRow.of(gardenSeedSelectMenu(userid)),
                    ActionRow.of(gardenHarvestSelectMenu(userid))
                ).queue();
        }
    }

    @Override
    public void plant(IReplyCallback cmdOrigin, Plant plant) {
        String traitsString = Arrays.stream(plant.traits())
            .map(trait -> trait.name())
            .collect(Collectors.joining("`,`", "`", "`"));
        MessageEmbed embed = new EmbedBuilder()
            .setColor(plant.colour())
            .setTitle("Plant Information")
            .appendDescription(String.format("Code · `%s`\nOwned by <@%d>\nAcquired on <t:%d:f>\n", plant.code(), plant.ownerid(), plant.acquisitionTime()))
            .appendDescription(String.format("Colour · R=%d, G=%d, B=%d\n\n", plant.colour().getRed(), plant.colour().getGreen(), plant.colour().getBlue()))
            .appendDescription("**Traits**\n" + traitsString)
            .build();
        cmdOrigin.replyEmbeds(embed).queue();
    }

    @Override
    public void plantNonExistentFailure(IReplyCallback cmdOrigin, String code) {
        cmdOrigin.reply("You must provide a valid plant code!").setEphemeral(true).queue();
    }

    @Override
    public void plants(IReplyCallback cmdOrigin, long userid, Map<String, Plant> plants, int page) {
        String plantsDisplayed = plants.entrySet().stream()
            .filter(e -> e.getValue().ownerid() == userid)
            .skip(PLANT_DISPLAY_PAGE_SIZE * page)
            .limit(PLANT_DISPLAY_PAGE_SIZE)
            .map(e -> String.format("`%s` · %s", e.getKey(),
                Arrays.stream(e.getValue().traits()).map(trait -> trait.name()).collect(Collectors.joining(",", "`", "`")))
            ).collect(Collectors.joining("\n"));
        MessageEmbed embed = new EmbedBuilder()
            .setColor(Main.kaiboColor)
            .setTitle("Plant Inventory")
            .appendDescription(String.format("<@%d>'s plants\n\n%s", userid, plantsDisplayed))
            .setFooter(String.format("Showing %d-%d of %d", 0, 0, 0))
            .build();
        cmdOrigin.replyEmbeds(embed).queue();
    }
    
}
