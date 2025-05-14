package roland.discord_bot;

import java.util.Map;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu.Builder;
import roland.games.gardening.DisplayHandler;
import roland.games.gardening.Garden;
import roland.games.gardening.Inventory;
import roland.games.gardening.Plant;
import roland.games.gardening.Seed;

public class DiscordDisplayHandler implements DisplayHandler<SlashCommandInteractionEvent> {
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

    @Override
    public void forageSuccess(SlashCommandInteractionEvent cmdOrigin, Map<Seed, Integer> foragedSeeds) {
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
    public void forageFailure(SlashCommandInteractionEvent cmdOrigin, long timeAvailable) {
        cmdOrigin.reply("You can forage again in " + formatUnixTime(timeAvailable - System.currentTimeMillis()/1000L)).queue();
    }

    @Override
    public void inventory(SlashCommandInteractionEvent cmdOrigin, long userid, Inventory inventory) {
        String seedsMsgBox = inventory.getSeeds().entrySet().stream()
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
    public void garden(SlashCommandInteractionEvent cmdOrigin, long userid, Garden garden) {
        var plots = garden.getPlots();
        String gardenMsgBox = plots.stream()
            .map(p -> ":seedling: " + p.seed().fullName() + " · Harvestable in <t:" + p.harvestableAt() + ":R>")
            .collect(Collectors.joining("\n", "<@" + userid + ">'s garden\n\n", ""));
        if (plots.isEmpty()) gardenMsgBox = "<@" + userid + "> has not planted any seeds.";
        MessageEmbed embed = new EmbedBuilder()
            .setColor(Main.kaiboColor)
            .setTitle("Garden")
            .appendDescription(gardenMsgBox)
            .build();
        Builder ssm = StringSelectMenu.create("menu:plantseed")
            .setPlaceholder("Select seed to plant")
            .setRequiredRange(1, 1);
        Main.gardeningGame.data().getInventory(userid).getSeeds().entrySet().stream()
            .filter(e -> e.getValue() > 0)
            .forEach(e -> ssm.addOption(e.getKey().fullName() + " (Owned: " + e.getValue() + ")", e.getKey().codeName()));
        var reply = cmdOrigin.replyEmbeds(embed);
        if (!ssm.getOptions().isEmpty()) reply.addActionRow(ssm.build());
        reply.queue();
    }
    
    @Override
    public void plantSeedNotExistentFailure(SlashCommandInteractionEvent cmdOrigin) {
        cmdOrigin.reply("Please specify a valid seed.").queue();
    }

    @Override
    public void plantSeedNotEnoughFailure(SlashCommandInteractionEvent cmdOrigin, Seed seed) {
        cmdOrigin.reply("You need to have at least one " + seed.fullName() + " to plant it.").queue();
    }

    @Override
    public void plantSeedGardenFullFailure(SlashCommandInteractionEvent cmdOrigin) {
        cmdOrigin.reply("You cannot plant anymore seeds in your garden — it's full!").queue();
    }

    @Override
    public void plantSeedSuccess(SlashCommandInteractionEvent cmdOrigin, Seed seed) {
        cmdOrigin.reply("You planted a " + seed.fullName() + " in your garden.").queue();
    }

    @Override
    public void harvestNotReadyFailure(SlashCommandInteractionEvent cmdOrigin, int plotNum) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'harvestNotReadyFailure'");
    }

    @Override
    public void harvestEmptyFailure(SlashCommandInteractionEvent cmdOrigin, int plotNum) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'harvestEmptyFailure'");
    }

    @Override
    public void harvestSuccess(SlashCommandInteractionEvent cmdOrigin, Seed seed, Plant harvestedPlant) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'harvestSuccess'");
    }

    @Override
    public void plant(SlashCommandInteractionEvent cmdOrigin, Plant plant) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'plant'");
    }

    @Override
    public void plantNonExistentFailure(SlashCommandInteractionEvent cmdOrigin, String code) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'plantNonExistentFailure'");
    }

    @Override
    public void plants(SlashCommandInteractionEvent cmdOrigin, long userid, Map<String, Plant> plants, int page) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'plants'");
    }
    
}
