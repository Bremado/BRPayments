package me.toddydev.bukkit.listeners.interaction;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Material.AIR;
import static org.bukkit.Material.MAP;

public class InteractionListener implements Listener {

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();

        if (item.getType() != MAP)return;

        net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(item);

        if (nms.getTag() == null)return;
        if (nms.getTag().getString("brpayments:order") == null)return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (item == null)return;

        if (item.getType() != MAP)return;

        net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(item);

        if (nms.getTag() == null)return;
        if (nms.getTag().getString("brpayments:order") == null)return;

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item == null || item.getType() == AIR)return;

        if (item.getType() != MAP)return;

        net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(item);

        if (nms.getTag() == null)return;
        if (nms.getTag().getString("brpayments:order") == null)return;

        event.setCancelled(true);
    }
}
