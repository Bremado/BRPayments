package me.toddydev.discord.commands;

import lombok.Getter;
import me.toddydev.discord.Discord;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Getter
public abstract class AbstractCommand extends ListenerAdapter {

    private String command;
    private String description;
    private String usage;

    public AbstractCommand(String command, String description, String usage) {
        this.command = command;
        this.description = description;
        this.usage = usage;
    }

    public void register() {
        Discord.getApi().upsertCommand(this.command, this.description).queue();
        Discord.getApi().addEventListener(this);
    }
}
