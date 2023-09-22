package me.toddydev.core.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import me.toddydev.core.model.Order;

import java.lang.reflect.Type;
import java.util.Set;

public class GsonUtil {

    private static final Gson GSON = new Gson();
    private static final Type TYPE_SET = new TypeToken<Set<Order>>() {}.getType();

    public static String toJson(Set<Order> orders) {
        return GSON.toJson(orders, Set.class);
    }

    public static Set<Order> fromJson(String json) {
        return GSON.fromJson(json, TYPE_SET);
    }

}
