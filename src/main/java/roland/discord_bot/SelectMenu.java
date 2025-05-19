package roland.discord_bot;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

public interface SelectMenu {
    public String name();
    public void run(StringSelectInteractionEvent event);
}
record PlantSeedMenu(String name) implements SelectMenu {
    public void run(StringSelectInteractionEvent event) {
        
    }
}
record HarvestPlantMenu(String name) implements SelectMenu {
    public void run(StringSelectInteractionEvent event) {
        
    }
}