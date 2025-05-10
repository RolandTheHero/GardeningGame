package roland.discord_bot;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface SlashCommand {
    public String name();
    public void run(SlashCommandInteractionEvent event);
}
record EchoCommand(String name) implements SlashCommand {
    public void run(SlashCommandInteractionEvent event) {
        var op = event.getOption("message");
        if (op == null) return;
        event.reply(op.getAsString()).queue();
    }
}
record ForageCommand(String name) implements SlashCommand {
    public void run(SlashCommandInteractionEvent event) {
        Main.gardeningGame.forage(event, event.getUser().getIdLong());
    }
}
record InventoryCommand(String name) implements SlashCommand {
    public void run(SlashCommandInteractionEvent event) {
        var user = event.getOption("user");
        long userid;
        if (user == null) userid = event.getUser().getIdLong();
        else userid = user.getAsLong();
        Main.gardeningGame.seedInventory(event, userid);
    }
}
