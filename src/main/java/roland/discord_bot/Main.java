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
            new EchoCommand("echo"),
            new ForageCommand("forage"),
            new InventoryCommand("inventory"),
            new GardenCommand("garden")
        ));
        jda.addEventListener(new SelectMenuListener());
        jda.updateCommands().addCommands(
            Commands.slash("echo", "Repeats your message back to you.")
                .addOption(OptionType.STRING, "message", "The message to repeat."),
            Commands.slash("forage", "Look for seeds in the vicinity."),
            Commands.slash("inventory", "View you or another user's seed inventory.")
                .addOption(OptionType.USER, "user", "The user to view."),
            Commands.slash("garden", "View you or another user's garden.")
                .addOption(OptionType.USER, "user", "The user to view.")
        ).queue();
    }
}
