package roland.discord_bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
    static public void main(String[] args) {
        JDA jda = JDABuilder.createDefault(Token.TOKEN)
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
            .build();
        
        jda.addEventListener(new SlashCommandListener());
        //jda.addEventListener(new ButtonListeners());
        jda.updateCommands().addCommands(
            Commands.slash("echo", "Repeats your message back to you.")
                .addOption(OptionType.STRING, "message", "The message to repeat."),
            Commands.slash("forage", "Look for seeds in the vicinity.")
        ).queue();
    }
}
