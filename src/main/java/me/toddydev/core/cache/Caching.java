package me.toddydev.core.cache;

import lombok.Getter;
import me.toddydev.core.cache.categories.CategoryCache;
import me.toddydev.core.cache.gateways.GatewaysCache;
import me.toddydev.core.cache.orders.OrdersCache;
import me.toddydev.core.cache.products.ProductCache;
import me.toddydev.core.cache.users.UserCache;

public class Caching {

    @Getter
    private static UserCache userCache = new UserCache();
    
    @Getter
    private static OrdersCache ordersCache = new OrdersCache();

    @Getter
    private static ProductCache productCache = new ProductCache();

    @Getter
    private static CategoryCache categoryCache = new CategoryCache();

    @Getter
    private static GatewaysCache gatewaysCache = new GatewaysCache();

}
