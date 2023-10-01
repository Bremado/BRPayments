package me.toddydev.bukkit.loaders.gateways;

import me.toddydev.core.cache.Caching;
import me.toddydev.core.model.order.gateway.Gateway;
import me.toddydev.core.model.order.gateway.type.GatewayType;
import org.bukkit.plugin.java.JavaPlugin;

public class GatewayLoader {

    public static void load(JavaPlugin plugin) {
        for (String value : plugin.getConfig().getConfigurationSection("gateways").getKeys(false)) {
            GatewayType gatewayType = GatewayType.find(value.toUpperCase());
            if (gatewayType == null) {
                plugin.getLogger().warning("Gateway " + value + " not found!");
                continue;
            }

            Gateway gateway = new Gateway();
            gateway.setToken(plugin.getConfig().getString("gateways." + value + ".authentication"));
            gateway.setType(gatewayType);

            Caching.getGatewaysCache().add(gateway);
        }
    }
}
