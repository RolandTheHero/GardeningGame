package roland.discord_bot;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SelectMenuListener extends ListenerAdapter {
    @Override public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent  event) {
        if (event.getComponentId().equals("menu:plantseed")) {
            //TODO
        }
    }
}
