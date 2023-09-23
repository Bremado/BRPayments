package me.toddydev.core.model.actions.type;

import org.bukkit.Sound;

public enum ActionType {

    COLLECT,
    REFUND,
    EXPIRED;

    public static ActionType find(String s) {
        for (ActionType type : values()) {
            if (type.name().equalsIgnoreCase(s)) {
                return type;
            }
        }
        return null;
    }

}
