package me.toddydev.core.cache.gateways;

import me.toddydev.core.model.order.gateway.Gateway;
import me.toddydev.core.model.order.gateway.type.GatewayType;

import java.util.ArrayList;
import java.util.List;

public class GatewaysCache {

    private List<Gateway> gateways = new ArrayList<>();

    public void add(Gateway gateway) {
        this.gateways.add(gateway);
    }

    public void remove(Gateway gateway) {
        this.gateways.remove(gateway);
    }

    public Gateway find(GatewayType type) {
        return gateways.stream().filter(gateway -> gateway.getType() == type).findFirst().orElse(null);
    }
}
