package roland.discord_bot;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SelectMenuListener extends ListenerAdapter {
    @Override public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent event) {
        if (event.getComponentId().startsWith("menu:plantseed")) {
            if (!event.getUser().getId().equals(event.getComponentId().split(":")[2])) return;
            Main.gardeningGame.plantSeed(event, event.getUser().getIdLong(), event.getValues().getFirst());
        } else if (event.getComponentId().startsWith("menu:harvestplant")) {
            if (!event.getUser().getId().equals(event.getComponentId().split(":")[2])) return;
            Main.gardeningGame.harvest(event, event.getUser().getIdLong(), Integer.parseInt(event.getValues().getFirst()));
        }
    }
}
