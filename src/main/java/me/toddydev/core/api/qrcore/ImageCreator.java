package me.toddydev.core.api.qrcore;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.toddydev.bukkit.BukkitMain;
import me.toddydev.core.api.placeholder.PlaceholderLoader;
import me.toddydev.core.cache.Caching;
import me.toddydev.core.database.tables.Tables;
import me.toddydev.core.model.order.Order;
import me.toddydev.core.player.User;
import me.toddydev.core.utils.item.ItemBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class ImageCreator {

    public static void generateMap(String data, Player player) {
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), BarcodeFormat.QR_CODE, 128, 128);
            MapView mapView = Bukkit.createMap(player.getWorld());
            mapView.setScale(MapView.Scale.CLOSEST);
            mapView.getRenderers().clear();
            mapView.addRenderer(new MapRenderer() {
                @Override
                public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
                    mapCanvas.drawImage(0, 0, MatrixToImageWriter.toBufferedImage(matrix));
                }
            });

            List<String> lore = PlaceholderLoader.setPlaceholders(player, BukkitMain.getMessagesConfig().getStringList("item-qrcode-description"));
            lore.replaceAll(line -> line.replace("&", "§"));
            ItemStack item = new ItemBuilder(Material.MAP, mapView.getId())
                    .name(PlaceholderLoader.setPlaceholders(player, BukkitMain.getMessagesConfig().getString("item-qrcode-name").replace("&", "§")))
                    .lore(
                            lore
                    ).build();

            net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(item);
            NBTTagCompound tag = nms.getTag() == null ? new NBTTagCompound() : nms.getTag();
            tag.setString("brpayments:order", "");

            item = CraftItemStack.asBukkitCopy(nms);
            player.setMetadata("brpayments:order", new FixedMetadataValue(BukkitMain.getInstance(), System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1)));

            User user = Caching.getUserCache().find(player.getUniqueId());
            Order o = Caching.getOrdersCache().findByPayer(player.getUniqueId());

            TextComponent component = new TextComponent(PlaceholderLoader.setPlaceholders(player, BukkitMain.getMessagesConfig().getString("success-payment-link")
                    .replace("&","§")
                    .replace("{nl}", "\n")
                    .replace("{ticket_link}", o.getTicketLink())
            ));

            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(
                    PlaceholderLoader.setPlaceholders(player, BukkitMain.getMessagesConfig().getString("success-payment-link-hover")
                            .replace("&", "§")
                            .replace("{nl}", "\n")))));

            component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, o.getTicketLink()));

            player.spigot().sendMessage(component);

            if (player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR) {
                user.setItemInHand(player.getItemInHand());
            }

            user.setTotalOrders(user.getTotalOrders() + 1);
            Tables.getUsers().update(user);

            player.setItemInHand(item);
        } catch (Exception e) {}
    }
}