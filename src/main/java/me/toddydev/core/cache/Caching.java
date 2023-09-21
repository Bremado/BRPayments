package me.toddydev.core.cache;

import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.Getter;
import me.toddydev.core.cache.orders.OrdersCache;

public class Caching {

    @Getter
    private static OrdersCache ordersCache = new OrdersCache();

}
