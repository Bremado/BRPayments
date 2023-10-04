package me.toddydev.core.api.qrcore;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import me.toddydev.bukkit.BukkitMain;
import me.toddydev.core.cache.Caching;
import me.toddydev.core.model.order.Order;
import me.toddydev.core.utils.item.ItemBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.metadata.FixedMetadataValue;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

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

            ItemStack item = new ItemBuilder(Material.MAP, mapView.getId())
                    .name("§aExpira em 1 minuto.")
                    .lore(
                            "§7O pagamento será processado em até 1 minuto."
                    ).build();

            net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(item);
            NBTTagCompound tag = nms.getTag() == null ? new NBTTagCompound() : nms.getTag();
            tag.setString("brpayments:order", "");

            item = CraftItemStack.asBukkitCopy(nms);
            player.setMetadata("brpayments:order", new FixedMetadataValue(BukkitMain.getInstance(), System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1)));

            Order o = Caching.getOrdersCache().findByPayer(player.getUniqueId());

            TextComponent component = new TextComponent(BukkitMain.getMessagesConfig().getString("success-payment-link")
                    .replace("&","§")
                    .replace("{nl}", "\n"));

            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(BukkitMain.getMessagesConfig().getString("success-payment-link-hover").replace("&", "§").replace("{nl}", "\n"))));
            component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, o.getTicketLink()));

            player.spigot().sendMessage(component);

            player.setItemInHand(item);
        } catch (Exception e) {}
    }
}