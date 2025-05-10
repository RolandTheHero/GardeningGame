package roland.discord_bot;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandListener extends ListenerAdapter {
    private final Map<String, SlashCommand> commands = new HashMap<>();

    public SlashCommandListener(SlashCommand... commands) {
        for (SlashCommand scmd : commands) this.commands.put(scmd.name(), scmd);
    }

    @Override public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        String eventName = event.getName();
        SlashCommand scmd = commands.get(eventName);
        if (scmd == null) throw new IllegalArgumentException("No such command: " + scmd);
        scmd.run(event);
    }
}
