package me.toddydev.core.model.product.actions.type;

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
