package me.toddydev.bukkit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class BukkitCommand extends Command {

    public BukkitCommand(String name) {
        super(name);
    }

    public BukkitCommand(String name, String description) {
        super(name, description, "", new ArrayList<String>());
    }

    public BukkitCommand(String name, String description, List<String> aliases) {
        super(name, description, "", aliases);
    }

    public BukkitCommand(String name, String description, String... aliases) {
        super(name, description, "", Arrays.asList(aliases));
    }

    public abstract boolean execute(CommandSender commandSender, String label, String[] args);

    public Integer getInteger(String string) {
        return Integer.valueOf(string);
    }

    public boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean isBool(String string) {
        try {
            Boolean.parseBoolean(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    public boolean isUUID(String string) {
        try {
            UUID.fromString(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getArgs(String[] args, int starting) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = starting; i < args.length; i++) {
            stringBuilder.append(args[i] + " ");
        }
        return stringBuilder.toString().substring(0, stringBuilder.length() - 1);
    }

}

