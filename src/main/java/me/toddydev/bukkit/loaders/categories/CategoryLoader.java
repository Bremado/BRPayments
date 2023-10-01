package me.toddydev.bukkit.loaders.categories;

import me.toddydev.core.cache.Caching;
import me.toddydev.core.model.product.categories.Category;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class CategoryLoader {

    public static void load(JavaPlugin plugin) {
        for (String s : plugin.getConfig().getConfigurationSection("categories").getKeys(false)) {
            Material material = Material.getMaterial(plugin.getConfig().getString("categories." + s + ".icon.material"));

            if (material == null) {
                material = Material.BARRIER;
                plugin.getServer().getConsoleSender().sendMessage("[BRPayments] Invalid material for category " + s + "! Changed to BARRIER.");
            }

            Category category = Category.builder()
                    .id(
                            s
                    ).name(
                            plugin.getConfig().getString("categories." + s + ".name")
                    ).description(
                            plugin.getConfig().getStringList("categories." + s + ".description")
                    ).material(
                            material
                    ).data(
                            plugin.getConfig().getInt("categories." + s + ".icon.id")
                    ).build();

            Caching.getCategoryCache().add(category);
        }
    }

}
