package com.nobowl.macelimiter

import com.nobowl.macelimiter.command.MaceCommand
import com.nobowl.macelimiter.config.ConfigManager
import com.nobowl.macelimiter.listener.MaceListener
import org.bukkit.plugin.java.JavaPlugin

class MaceLimiter : JavaPlugin() {

    companion object {
        lateinit var instance: MaceLimiter
            private set
    }

    override fun onEnable() {
        instance = this
        ConfigManager.load(this)

        server.pluginManager.registerEvents(MaceListener(), this)
        getCommand("macelimiter")?.setExecutor(MaceCommand())

        logger.info("MaceLimiter has been enabled.")
    }

    override fun onDisable() {
        logger.info("MaceLimiter has been disabled.")
    }
}
