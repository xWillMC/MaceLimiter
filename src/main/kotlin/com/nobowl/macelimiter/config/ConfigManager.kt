package com.nobowl.macelimiter.config

import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object ConfigManager {

    private lateinit var config: FileConfiguration
    private lateinit var messages: FileConfiguration

    private lateinit var configFile: File
    private lateinit var messagesFile: File

    fun load(plugin: org.bukkit.plugin.java.JavaPlugin) {
        // Load config.yml
        plugin.saveDefaultConfig()
        plugin.reloadConfig()
        config = plugin.config

        // Load messages.yml
        messagesFile = File(plugin.dataFolder, "messages.yml")
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false)
        }
        messages = YamlConfiguration.loadConfiguration(messagesFile)
    }

    fun reload() {
        configFile = File(Bukkit.getPluginManager().getPlugin("MaceLimiter")!!.dataFolder, "config.yml")
        config = YamlConfiguration.loadConfiguration(configFile)

        messagesFile = File(Bukkit.getPluginManager().getPlugin("MaceLimiter")!!.dataFolder, "messages.yml")
        messages = YamlConfiguration.loadConfiguration(messagesFile)
    }

    fun getPrefix(): String {
        return messages.getString("prefix") ?: "[MaceLimiter] "
    }

    fun getMessage(key: String): String {
        return messages.getString(key)?.replace("%prefix%", getPrefix())
            ?: "[MaceLimiter] Message missing: $key"
    }

    fun getLimit(world: String): Int {
        val perWorld = config.getConfigurationSection("limits-per-world")
        return if (config.getBoolean("global-limit")) {
            config.getInt("default-limit", 1)
        } else {
            perWorld?.getInt(world) ?: config.getInt("default-limit", 1)
        }
    }

    fun isLoggingEnabled(): Boolean {
        return config.getBoolean("logging.enabled")
    }

    fun getBypassPermission(): String {
        return config.getString("bypass-permission") ?: "macelimiter.bypass"
    }

    fun getExceedAction(): String {
        return config.getString("actions.on-limit-exceeded") ?: "DENY_PICKUP"
    }

    fun isSilentMode(): Boolean {
        return config.getBoolean("silent-mode")
    }
}
