package me.toddydev.discord;

import com.avaje.ebean.validation.NotNull;
import lombok.Getter;
import lombok.Setter;
import me.toddydev.discord.commands.loader.CommandLoader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Discord {

    @Getter @Setter
    private static JDA api;
    public Discord(JavaPlugin plugin) {
        Bukkit.getConsoleSender().sendMessage("[BRDiscord] Connecting to Discord...");

        JDABuilder builder = JDABuilder.createDefault(plugin.getConfig().getString("discord.token"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);

        setApi(builder.build());

        CommandLoader.load(plugin, "me.toddydev.discord.commands.register");
    }
}
