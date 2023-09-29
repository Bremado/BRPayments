package me.toddydev.bukkit.loaders.products;

import me.toddydev.core.cache.Caching;
import me.toddydev.core.model.Product;
import me.toddydev.core.model.actions.Action;
import me.toddydev.core.model.actions.screen.Screen;
import me.toddydev.core.model.actions.type.ActionType;
import me.toddydev.core.model.categories.Category;
import me.toddydev.core.model.icon.Icon;
import me.toddydev.core.model.rewards.Reward;
import me.toddydev.core.model.rewards.item.RewardItem;
import me.toddydev.core.player.order.gateways.Gateway;
import okhttp3.Cache;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductLoader {

    public static void load(JavaPlugin plugin) {
        File dir = new File(plugin.getDataFolder().getPath() + "/products");

        if (!dir.exists()) {
            dir.mkdirs();

            File file = new File(dir.getPath(), "example.yml");
            try {
                file.createNewFile();
                InputStream r = plugin.getResource("products/produto.yml");
                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(r);
                yaml.save(file);
            } catch (Exception e) {
                plugin.getServer().getConsoleSender().sendMessage("[BRPayments] Não foi possível criar um arquivo de exemplo.");
            }
            return;
        }

        File[] files = dir.listFiles();
        if (files == null) return;

        for (File f : files) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(f);

            Category category = Caching.getCategoryCache().find(config.getString("category"));

            if (category == null) {
                plugin.getServer().getConsoleSender().sendMessage("[BRPayments] Não foi possível encontrar a categoria " + config.getString("category") + " para o produto " + config.getString("name") + ".");
                continue;
            }

            Product product = Product.builder()
                    .name(config.getString("name"))
                    .id(config.getString("id"))
                    .price(config.getDouble("price"))
                    .category(category)
                    .rewards(Reward.builder().build()
                    ).build();


            Material material = Material.getMaterial(config.getString("icon.material"));

            if (material == null) {
                material = Material.BARRIER;
                plugin.getServer().getConsoleSender().sendMessage("[BRPayments] Não foi possível encontrar o material " + config.getString("icon.material") + " para o produto " + product.getName() + ". Portanto foi alterado para BARRIER.");
            }

            Icon icon = Icon.builder()
                    .name(
                            config.getString("icon.name").replace("&", "§")
                    )
                    .description(
                            config.getStringList("icon.description")
                    )
                    .material(
                            material
                    )
                    .id(
                            config.getInt("icon.id")
                    )
                    .build();

            product.setIcon(icon);

            List<Action> actions = new ArrayList<>();
            List<Gateway> gateways = new ArrayList<>();
            List<RewardItem> items = new ArrayList<>();
            List<String> commands = config.getStringList("rewards.commands");

            for (String s : config.getStringList("gateways")) {
                Gateway g = Gateway.find(s.toUpperCase());

                if (g == null) {
                    plugin.getServer().getConsoleSender().sendMessage("[BRPayments] Não foi possível encontrar a gateway " + s + " para o produto " + product.getName() + ".");
                    continue;
                }

                gateways.add(g);
            }

            for (String s : config.getConfigurationSection("rewards.items").getKeys(false)) {
                RewardItem item = RewardItem.builder()
                        .material(Material.getMaterial(config.getString("rewards.items." + s)))
                        .amount(config.getInt("rewards.items." + s + ".amount"))
                        .data((short) config.getInt("rewards.items." + s + ".data"))
                        .build();

                items.add(item);
            }

            for (String s : config.getConfigurationSection("actions").getKeys(false)) {
                ActionType type = ActionType.find(s.toUpperCase());

                if (type == null) {
                    plugin.getServer().getConsoleSender().sendMessage("[BRPayments] Não foi possível encontrar a ação " + s + " para o produto " + product.getName() + ".");
                    continue;
                }

                Sound sound;

                try {
                    sound = Sound.valueOf(config.getString("actions." + s + ".sound").toUpperCase());
                } catch (Exception e) {
                    sound = Sound.LEVEL_UP;
                    config.set("actions." + s + ".sound", "LEVEL_UP");
                    Bukkit.getConsoleSender().sendMessage("[BRPayments] Não foi possível encontrar o som " + config.getString("actions." + s + ".sound") + " para o produto " + product.getName() + ". Portanto foi alterado para LEVEL_UP.");
                }

                Action action = Action.builder()
                        .type(type)
                        .sound(sound)
                        .actionBar(config.getString("actions." + s + ".action-bar").replace("&", "§"))
                        .message(config.getString("actions." + s + ".message").replace("&", "§"))
                        .screen(Screen.builder()
                                .title(config.getString("actions." + s + ".screen.title"))
                                .subtitle(config.getString("actions." + ".screen.subtitle"))
                                .build())
                        .build();

                actions.add(action);
            }

            product.setActions(actions);
            product.getRewards().setCommands(commands);
            product.getRewards().setItems(items);
            product.setGateways(gateways);

            Caching.getProductCache().add(product);
            Bukkit.getConsoleSender().sendMessage("[BRPayments] O produto " + product.getName() + " foi carregado com sucesso.");
        }
    }
}
