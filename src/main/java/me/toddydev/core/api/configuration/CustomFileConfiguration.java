package me.toddydev.core.api.configuration;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Create configuration files with custom file names
 */
public class CustomFileConfiguration extends YamlConfiguration {
    private final Plugin plugin;
    private final File configFile;

    /**
     * Create/get a configuration from the plugin folder
     *
     * @param name   The configuration file name, without {@code .yml} at the end
     * @param plugin An instance of your plugin
     * @throws IOException                   Failed to create de configuration file
     * @throws InvalidConfigurationException The configuration file represents an invalid YAML Configuration
     */
    public CustomFileConfiguration(String name, Plugin plugin) throws IOException, InvalidConfigurationException {
        this.plugin = plugin;

        configFile = new File(plugin.getDataFolder(), name.endsWith(".yml") ? name : name + ".yml");

        loadConfig();
    }

    /**
     * Saves the configuration to the file.
     */
    public void save() {
        try {
            save(configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "There was an error while saving the " + configFile.getName() + " configuration!", e);
        }
    }

    /**
     * Reloads the configuration from the file
     */
    public void reload() {
        try {
            loadConfig();
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "There was an error while creating the " + configFile.getName() + " configuration file!", e);
        } catch (InvalidConfigurationException e) {
            plugin.getLogger().log(Level.SEVERE, "The configuration \"" + configFile.getName() + "\" is invalid!", e);
        }
    }

    private void loadConfig() throws IOException, InvalidConfigurationException {
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();

        if (!configFile.exists()) {
            try {
                // Save the config file if found inside the jar
                plugin.saveResource(configFile.getName(), false);
            } catch (IllegalArgumentException e) {
                // File not found in resources, creating a blank one
                configFile.createNewFile();
            }
        }

        load(configFile);
    }

    /**
     * Gets the requested String by path, returning {@code null} if not found.
     *
     * @param path            Path of the String to get.
     * @param translateColors Translate colors from {@code &} color codes
     * @return Requested String.
     */
    public String getString(final String path, final boolean translateColors) {
        return getString(path, null, translateColors);
    }

    /**
     * Gets the requested String by path, returning the default value if not found.
     *
     * @param path            Path of the String to get.
     * @param defaultValue    The default value to return if the path is not found or is not a String.
     * @param translateColors Translate colors from {@code &} color codes
     * @return Requested String.
     */
    public String getString(final String path, final String defaultValue, final boolean translateColors) {
        String value = getString(path, defaultValue);
        return value != null && translateColors ? ChatColor.translateAlternateColorCodes('&', value) : value;
    }
}
