package roland.discord_bot;

import java.util.function.Consumer;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface SlashCommand {
    public void run(Consumer<SlashCommandInteractionEvent> eventConsumer);
}
