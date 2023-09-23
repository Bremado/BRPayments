package me.toddydev.discord.commands.register;

import me.toddydev.discord.Discord;
import me.toddydev.discord.commands.AbstractCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

public class PingCommand extends AbstractCommand {

    public PingCommand() {
        super(
                "ping", "See the bot's ping", "ping"
        );
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(!(event.getName().equals(getCommand())))return;

        event.reply("Pong! " + Discord.getApi().getGatewayPing() + "ms").setEphemeral(true).queue();
    }
}
