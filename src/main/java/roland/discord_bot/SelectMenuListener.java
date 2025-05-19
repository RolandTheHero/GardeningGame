package roland.discord_bot;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SelectMenuListener extends ListenerAdapter {
    private final Map<String, SelectMenu> commands = new HashMap<>();

    public SelectMenuListener(SelectMenu... commands) {
        for (SelectMenu cmd : commands) this.commands.put(cmd.name(), cmd);
    }

    @Override public void onStringSelectInteraction(@Nonnull StringSelectInteractionEvent event) {
        if (event.getComponentId().startsWith("plantseed")) {
            if (!event.getUser().getId().equals(event.getComponentId().split(":")[1])) return;
            Main.gardeningGame.plantSeed(event, event.getUser().getIdLong(), event.getValues().getFirst());
        } else if (event.getComponentId().startsWith("harvestplant")) {
            if (!event.getUser().getId().equals(event.getComponentId().split(":")[1])) return;
            Main.gardeningGame.harvest(event, event.getUser().getIdLong(), Integer.parseInt(event.getValues().getFirst()));
        }
    }
}
