package roland.discord_bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

import roland.games.gardening.GardeningGame;
import roland.games.gardening.LocalDataHandler;

public class Main {
    static final public int kaiboColor = 41172;

    static final public GardeningGame<IReplyCallback> gardeningGame = new GardeningGame<>(new LocalDataHandler(), new DiscordDisplayHandler());

    static public void main(String[] args) {
        JDA jda = JDABuilder.createDefault(Token.TOKEN)
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
            .build();
        
        jda.addEventListener(new SlashCommandListener(
            new ForageCommand("forage"),
            new InventoryCommand("inventory"),
            new GardenCommand("garden"),
            new ViewPlantCommand("viewplant"),
            new ViewPlantInventory("plantinventory")
        ));
        jda.addEventListener(new SelectMenuListener(
            new PlantSeedMenu("plantseed"),
            new HarvestPlantMenu("harvestseed")
        ));
        jda.updateCommands().addCommands(
            Commands.slash("forage", "Look for seeds in the vicinity."),
            Commands.slash("inventory", "View you or another user's seed inventory.")
                .addOption(OptionType.USER, "user", "The user to view."),
            Commands.slash("garden", "View you or another user's garden.")
                .addOption(OptionType.USER, "user", "The user to view."),
            Commands.slash("viewplant", "View details about a plant.")
                .addOption(OptionType.STRING, "code", "The code of the plant to view."),
            Commands.slash("plantinventory", "View all the plants belonging to a user.")
                .addOption(OptionType.USER, "user", "The user to view.")
        ).queue();
    }
}
