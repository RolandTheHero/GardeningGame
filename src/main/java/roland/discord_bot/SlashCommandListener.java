package roland.discord_bot;

import java.util.Map;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandListener extends ListenerAdapter {
    private Map<String, SlashCommand> commands = null; // TODO
    @Override public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        String eventName = event.getName();
        if (eventName.equals("echo")) {
            var op = event.getOption("message");
            if (op == null) return;
            event.reply(op.getAsString()).queue();
        } else if (eventName.equals("forage")) {

        }
    }
}
