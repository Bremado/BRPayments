package me.toddydev.discord;

import lombok.Getter;
import lombok.Setter;
import me.toddydev.discord.commands.loader.CommandLoader;
import me.toddydev.discord.enums.MessageChannel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bukkit.plugin.java.JavaPlugin;

public class Discord {

    @Getter @Setter
    private static JDA api;

    private String sellChannel;

    public Discord(JavaPlugin plugin) {
        plugin.getLogger().info("[BRDiscord] Connecting to Discord...");

        String token = plugin.getConfig().getString("discord.token");
        sellChannel = plugin.getConfig().getString("discord.channels.sell");

        try {
            JDABuilder builder = JDABuilder.createDefault(token);

            builder.enableIntents(GatewayIntent.GUILD_MEMBERS); 
            builder.setMemberCachePolicy(MemberCachePolicy.ALL);

            setApi(builder.build());
        } catch (Exception e) {
            plugin.getLogger().info("[BRDiscord] Failed to connect to Discord. Token is invalid.");
            return;
        }

        CommandLoader.load(plugin, "me.toddydev.discord.commands.register");

    }

    public void sendEmbed(MessageChannel channel, EmbedBuilder builder) {
        try {
            getApi().getTextChannelById(channel == MessageChannel.SELL ? sellChannel : "").sendMessageEmbeds(builder.build()).queue();
        } catch (Exception e) {
            System.out.println("[BRDiscord] Failed to send message to Discord. (" + e.getLocalizedMessage() + ")");
        }
    }

}
