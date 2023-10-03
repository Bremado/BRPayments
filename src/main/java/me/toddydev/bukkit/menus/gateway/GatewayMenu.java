package me.toddydev.bukkit.menus.gateway;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import me.toddydev.bukkit.BukkitMain;
import me.toddydev.bukkit.menus.products.ProductsMenu;
import me.toddydev.core.api.qrcore.ImageCreator;
import me.toddydev.core.cache.Caching;
import me.toddydev.core.database.tables.Tables;
import me.toddydev.core.model.order.Order;
import me.toddydev.core.model.order.gateway.type.GatewayType;
import me.toddydev.core.model.order.status.OrderStatus;
import me.toddydev.core.model.product.Product;
import me.toddydev.core.services.Services;
import me.toddydev.core.utils.item.ItemBuilder;
import me.toddydev.core.utils.keys.RandomKey;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Locale;

public class GatewayMenu extends SimpleInventory {

    private Product product;

    public GatewayMenu(Product product) {
        super(
                "gateway-menu",
                "Loja Virtual - Método de Pagamento",
                9*3
        );

        this.product = product;
    }

    @Override
    protected void configureInventory(Viewer v, InventoryEditor e) {
        Player player = v.getPlayer();

        e.setItem(13, InventoryItem.of(new ItemBuilder(Material.SKULL_ITEM, 3)
                        .name("§aPague com §bMercado Pago§a!")
                        .texture("c0c9a3a4ffbee61d1ee1c3a533355bda9cdc377e07b0ff8bc618d3977b7f86cc")
                        .lore(
                                "",
                                "   §8§l⬤ §fPreço do produto: §2R$§a" + String.format(new Locale("pt", "BR"), "%.2f",product.getPrice()),
                                "   §8§l⬤ §fTaxa de pagamento: §2R$§a" + String.format(new Locale("pt", "BR"), "%.2f",(product.getPrice() * (0.99 / 100))),
                                "",
                                "   §8§l⬤ §fTotal: §2R$§a" + String.format(new Locale("pt", "BR"), "%.2f",(product.getPrice() + (product.getPrice() * (0.99 / 100)))),
                                "",
                                "§eClique aqui para pagar."
                        ).build())
                .defaultCallback(event -> {
                    event.setCancelled(true);
                    player.closeInventory();

                    if (Caching.getOrdersCache().findByPayer(player.getUniqueId()) != null) {
                        player.sendMessage(BukkitMain.getMessagesConfig().getString("already-have-order").replace("&", "§"));
                        return;
                    }

                    for (int i = 0; i < player.getInventory().getSize(); i++) {
                        if (player.getInventory().getItem(i) != null) {
                            if (!product.getRewards().getItems().isEmpty()) {
                                player.sendMessage(BukkitMain.getMessagesConfig().getString("must-inventory-clean").replace("&", "§"));
                                return;
                            }
                        }
                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Order order = Order.builder().payerId(player.getUniqueId()).referenceId(RandomKey.generateCode()).cost(product.getPrice() + (product.getPrice() * (0.99 / 100)))
                                    .status(OrderStatus.WAITING).gateway(Caching.getGatewaysCache().find(GatewayType.MERCADO_PAGO)).build();

                            order.setProductId(product.getId());

                            order = Services.getMercadoPagoService().create(order);

                            Caching.getOrdersCache().add(order);
                            ImageCreator.generateMap(order.getCode(), player);

                            Tables.getOrders().create(order);
                        }
                    }).start();
                })
        );

        e.setItem(22, InventoryItem.of(new ItemBuilder(Material.ARROW, 0)
                        .name("§cVoltar")
                        .lore("§eClique aqui para voltar.")
                        .build())
                .defaultCallback(event -> {
                    event.setCancelled(true);
                    player.closeInventory();
                    new ProductsMenu(product.getCategory()).init().openInventory(player);
                })
        );
    }
}
