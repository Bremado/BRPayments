package me.toddydev.bukkit.listeners.payment;

import me.toddydev.bukkit.events.PaymentCompletedEvent;
import me.toddydev.bukkit.events.PaymentExpiredEvent;
import me.toddydev.core.api.actionbar.ActionBar;
import me.toddydev.core.api.taskchain.TaskChain;
import me.toddydev.core.cache.Caching;
import me.toddydev.core.model.product.Product;
import me.toddydev.core.model.product.actions.Action;
import me.toddydev.core.model.product.actions.type.ActionType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.github.paperspigot.Title;

public class PaymentListener implements Listener {

    @EventHandler
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        Player player = event.getPlayer();
        Product product = Caching.getProductCache().findById(event.getOrder().getProductId());

        Action action = product.getActions().stream().filter(a -> a.getType().equals(ActionType.COLLECT)).findAny().orElse(null);

        player.playSound(player.getLocation(), action.getSound(), 5f, 5f);
        player.sendTitle(new Title(action.getScreen().getTitle().replace("&", "§"), action.getScreen().getSubtitle().replace("&", "§"), 10, 40, 10));

        player.sendMessage(action.getMessage().replace("&", "§"));

        ActionBar.sendActionBar(player, action.getActionBar().replace("&", "§"));

        TaskChain.newChain().add(new TaskChain.GenericTask() {
            @Override
            protected void run() {

                product.getRewards().getCommands().forEach(command -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{player}", player.getName()));
                });

                product.getRewards().getItems().forEach(item -> {
                    player.getInventory().addItem(item.stack());
                });
            }
        }).execute();

    }

    @EventHandler
    public void onPaymentExpired(PaymentExpiredEvent event) {
        Player player = event.getPlayer();
        Product product = Caching.getProductCache().findById(event.getOrder().getProductId());

        Action action = product.getActions().stream().filter(a -> a.getType().equals(ActionType.EXPIRED)).findAny().orElse(null);

        player.playSound(player.getLocation(), action.getSound(), 5f, 5f);
        player.sendTitle(new Title(action.getScreen().getTitle().replace("&", "§"), action.getScreen().getSubtitle().replace("&", "§"), 10, 40, 10));

        player.sendMessage(action.getMessage().replace("&", "§"));

        ActionBar.sendActionBar(player, action.getActionBar().replace("&", "§"));
    }
}
