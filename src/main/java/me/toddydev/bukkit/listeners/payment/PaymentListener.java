package me.toddydev.bukkit.listeners.payment;

import me.toddydev.bukkit.BukkitMain;
import me.toddydev.bukkit.events.PaymentCompletedEvent;
import me.toddydev.bukkit.events.PaymentExpiredEvent;
import me.toddydev.core.Core;
import me.toddydev.core.api.actionbar.ActionBar;
import me.toddydev.core.api.taskchain.TaskChain;
import me.toddydev.core.cache.Caching;
import me.toddydev.core.model.product.Product;
import me.toddydev.core.model.product.actions.Action;
import me.toddydev.core.model.product.actions.type.ActionType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.github.paperspigot.Title;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static me.toddydev.discord.enums.MessageChannel.SELL;

public class PaymentListener implements Listener {

    @EventHandler
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        Player player = event.getPlayer();
        Product product = Caching.getProductCache().findById(event.getOrder().getProductId());

        Action action = product.getActions().stream().filter(a -> a.getType().equals(ActionType.COLLECT)).findAny().orElse(null);

        player.playSound(player.getLocation(), action.getSound(), 5f, 5f);
        player.sendTitle(new Title(action.getScreen().getTitle().replace("&", "§"), action.getScreen().getSubtitle().replace("&", "§"), 10, 40, 10));

        player.sendMessage(action.getMessage()
                .replace("&", "§")
                .replace("{player}", player.getName())
                .replace("{displayName}", player.getDisplayName())
                .replace("{product}", product.getName())
        );

        ActionBar.sendActionBar(player, action.getActionBar().replace("&", "§")
                .replace("{player}", player.getName())
                .replace("{displayName}", player.getDisplayName())
                .replace("{product}", product.getName())
        );

        TaskChain.newChain().add(new TaskChain.GenericTask() {
            @Override
            protected void run() {

                product.getRewards().getCommands().forEach(command -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{player}", player.getName()));
                });

                product.getRewards().getItems().forEach(item -> {
                    player.getInventory().addItem(item.stack());
                });

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                EmbedBuilder builder = new EmbedBuilder();

                builder.setColor(Color.getColor(BukkitMain.getInstance().getConfig().getString("discord.embeds.sell.color")));
                builder.setTitle(BukkitMain.getInstance().getConfig().getString("discord.embeds.sell.title"));

                builder.setDescription(BukkitMain.getInstance().getConfig().getString("discord.embeds.sell.description")
                        .replace("{player}", player.getName())
                        .replace("{displayName}", player.getDisplayName())
                        .replace("{product}", product.getName())
                );

                builder.addField(new MessageEmbed.Field(BukkitMain.getInstance().getConfig().getString("discord.embeds.sell.fields.player"), player.getName(), true));
                builder.addField(new MessageEmbed.Field(BukkitMain.getInstance().getConfig().getString("discord.embeds.sell.fields.price"), "R$ " + String.format(new Locale("pt", "BR"), "%.2f", product.getPrice()), true));
                builder.addField(new MessageEmbed.Field(BukkitMain.getInstance().getConfig().getString("discord.embeds.sell.fields.rate"), String.format(new Locale("pt", "BR"), "%.2f", ((product.getPrice() * (0.99 / 100)))) + " (0.99%)", false));

                builder.setFooter(BukkitMain.getInstance().getConfig().getString("discord.embeds.sell.footer")
                        .replace("{player}", player.getName())
                        .replace("{displayName}", player.getDisplayName())
                        .replace("{product}", product.getName())
                        .replace("{date}", format.format(System.currentTimeMillis()))
                );

                Core.getDiscord().sendEmbed(SELL, builder);
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

        player.sendMessage(action.getMessage()
                .replace("&", "§")
                .replace("{player}", player.getName())
                .replace("{displayName}", player.getDisplayName())
                .replace("{product}", product.getName())
        );

        ActionBar.sendActionBar(player, action.getActionBar()
                .replace("&", "§")
                .replace("{player}", player.getName())
                .replace("{displayName}", player.getDisplayName())
                .replace("{product}", product.getName())
        );
    }
}
