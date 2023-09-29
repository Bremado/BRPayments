package me.toddydev.bukkit.menus;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import me.toddydev.core.cache.Caching;
import me.toddydev.core.player.User;
import me.toddydev.core.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public final class IndexMenu extends SimpleInventory {

    public IndexMenu() {
        super(
                "shop.menu",
                "Loja Virtual",
                9*3
        );

        configuration(config ->  {
            config.secondUpdate(1);
        });
    }

    @Override
    protected void configureInventory(Viewer v, InventoryEditor e) {
        Player viewer = v.getPlayer();

        User user = Caching.getUserCache().find(viewer.getUniqueId());

        InventoryItem viewerInfo = InventoryItem.of(
                new ItemBuilder(
                        Material.SKULL_ITEM, 3
                ).skullOwner(
                        viewer.getName()
                ).lore(
                        "§7Visualize suas estatísticas.",
                        "",
                        "  §8§l⬤ §fTotal de Pedidos: §a" + user.getTotalOrders(),
                        "  §8§l⬤ §fTotal Pago: §a " + user.getTotalPaid(),
                        "  §8§l⬤ §fTotal Reembolsado: §a" + user.getTotalRefunded(),
                        "",
                        "§eClique para fechar."
                        )
                        .build()
        ).defaultCallback(event -> {
            event.setCancelled(true);
            viewer.closeInventory();
        });

        InventoryItem categories = InventoryItem.of(
                new ItemBuilder(
                        Material.CHEST, 0
                )
                        .name("§aCategorias")
                        .lore(
                                "§eClique para visualizar as categorias."
                        ).build()
        ).defaultCallback(event -> {
            event.setCancelled(true);
        });

        InventoryItem lastOrders = InventoryItem.of(
                new ItemBuilder(
                        Material.PAPER,
                        0
                ).name("§aÚltimos Pedidos")
                        .lore(
                                "§eClique para visualizar os últimos pedidos."
                        ).build()
        ).defaultCallback(event -> {
            event.setCancelled(true);
        });

        InventoryItem orderHistory = InventoryItem.of(
                new ItemBuilder(
                        Material.BOOK,
                        0
                ).name("§aHistórico de Pedidos")
                        .lore(
                                "§eClique para visualizar o histórico de pedidos."
                        ).build()
        ).defaultCallback(event -> {
            event.setCancelled(true);
        });

        InventoryItem topBuyers = InventoryItem.of(
                new ItemBuilder(
                        Material.GOLD_INGOT,
                        0
                ).name("§aTop Compradores")
                        .lore(
                                "§eClique para visualizar os top compradores."
                        ).build()
        ).defaultCallback(event -> {
            event.setCancelled(true);
        });

        e.setItem(16, lastOrders);
        e.setItem(15, categories);
        e.setItem(13, topBuyers);
        e.setItem(11, orderHistory);
        e.setItem(10, viewerInfo);
    }
}
