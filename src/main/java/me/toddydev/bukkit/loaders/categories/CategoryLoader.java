package me.toddydev.bukkit.loaders.categories;

import me.toddydev.core.cache.Caching;
import me.toddydev.core.model.categories.Category;
import me.toddydev.core.model.icon.Icon;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class CategoryLoader {

    public static void load(JavaPlugin plugin) {
        for (String s : plugin.getConfig().getConfigurationSection("categories").getKeys(false)) {
            Category category = Category.builder()
                    .id(
                            s
                    ).name(
                            plugin.getConfig().getString("categories." + s + ".name")
                    ).description(
                            plugin.getConfig().getStringList("categories." + s + ".description")
                    ).material(
                            Material.getMaterial(plugin.getConfig().getString("categories." + s + ".material"))
                    ).data(
                            plugin.getConfig().getInt("categories." + s + ".id")
                    ).build();

            Caching.getCategoryCache().add(category);
        }
    }

}
