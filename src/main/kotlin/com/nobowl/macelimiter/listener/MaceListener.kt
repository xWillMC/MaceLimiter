package com.nobowl.macelimiter.listener

import com.nobowl.macelimiter.MaceLimiter
import com.nobowl.macelimiter.config.ConfigManager
import com.nobowl.macelimiter.util.MaceUtil
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerPickupItemEvent
import org.bukkit.inventory.ItemStack

class MaceListener : Listener {

    @EventHandler
    fun onPickup(event: PlayerPickupItemEvent) {
        val player = event.player
        val item: ItemStack = event.item.itemStack

        if (!MaceUtil.isMace(item)) return
        if (player.hasPermission(ConfigManager.getBypassPermission())) return

        val world = player.world.name
        val currentMaces = MaceUtil.countTotalMaces()
        val limit = ConfigManager.getLimit(world)

        if (currentMaces >= limit) {
            event.isCancelled = true

            if (!ConfigManager.isSilentMode()) {
                player.sendMessage(ConfigManager.getMessage("limit-exceeded"))
            }

            if (ConfigManager.isLoggingEnabled()) {
                MaceLimiter.instance.logger.info(ConfigManager.getMessage("log-entry")
                    .replace("%player%", player.name)
                    .replace("%world%", world))
            }
        }
    }
}
