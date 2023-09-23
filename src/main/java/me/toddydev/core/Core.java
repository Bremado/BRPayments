package me.toddydev.core;

import lombok.Getter;
import lombok.Setter;
import me.toddydev.core.database.Database;
import me.toddydev.discord.Discord;
import org.bukkit.Sound;

public class Core {

    @Getter @Setter
    private static Discord discord;

    @Getter
    private static Database database = new Database();

}
