package me.toddydev.core.cache;

import lombok.Getter;
import me.toddydev.core.cache.orders.OrdersCache;
import me.toddydev.core.cache.products.ProductCache;

public class Caching {

    @Getter
    private static OrdersCache ordersCache = new OrdersCache();

    @Getter
    private static ProductCache productCache = new ProductCache();

}
