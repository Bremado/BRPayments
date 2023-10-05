package me.toddydev.core.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import me.toddydev.bukkit.BukkitMain;
import me.toddydev.core.database.credentials.DatabaseCredentials;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.SQLException;

@Getter
public class Database {

    private HikariDataSource dataSource;
    private DatabaseCredentials credential;

    public void start(DatabaseCredentials credential) {
        this.credential = credential;

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://" + credential.getHost() + ":" + credential.getPort() + "/" + credential.getDatabase());
        config.setUsername(credential.getUsername());
        config.setPassword(credential.getPassword());
        config.setDriverClassName("com.mysql.jdbc.Driver");

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        try {
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            BukkitMain.getInstance().getLogger().severe("Failed to connect to the database! Please check your credentials and try again. (" + e.getLocalizedMessage() + ")");
            Bukkit.getPluginManager().disablePlugin(BukkitMain.getInstance());
        }

    }


    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            start(credential);
        }
        return getConnection();
    }

    public void stop() {
        dataSource.close();
    }
}
