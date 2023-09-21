package me.toddydev.core;

import lombok.Getter;
import me.toddydev.core.database.Database;

public class Core {

    @Getter
    private static Database database = new Database();

}
