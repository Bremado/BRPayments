package me.toddydev.bukkit.commands.register;

import me.toddydev.bukkit.commands.BukkitCommand;
import me.toddydev.bukkit.menus.IndexMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand extends BukkitCommand {
    public ShopCommand() {
        super(
                "shop"
        );
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        Player player = (!(sender instanceof Player)) ? null : (Player) sender;

        if (player == null) {
            sender.sendMessage("§cEste comando só pode ser executado in-game.");
            return false;
        }

        new IndexMenu().init().openInventory(player);

        return false;
    }
}
