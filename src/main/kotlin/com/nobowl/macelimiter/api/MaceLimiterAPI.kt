package com.nobowl.macelimiter.api

import com.nobowl.macelimiter.config.ConfigManager
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack

object MaceLimiterAPI {

    /**
     * Count total maces across all players and entities.
     */
    fun countTotalMaces(): Int {
        var count = 0
        for (world in Bukkit.getWorlds()) {
            count += world.entities.count { it is org.bukkit.entity.Item && isMace(it.itemStack) }
            for (player in world.players) {
                count += player.inventory.contents.count { isMace(it) }
                count += player.inventory.extraContents.count { isMace(it) }
            }
        }
        return count
    }

    /**
     * Check if an item is a mace.
     */
    fun isMace(item: ItemStack?): Boolean {
        return item != null && item.type.name.contains("MACE", ignoreCase = true)
    }

    /**
     * Check if adding another mace would exceed the current world/global limit.
     */
    fun wouldExceedLimit(worldName: String): Boolean {
        val current = countTotalMaces()
        val limit = ConfigManager.getLimit(worldName)
        return current >= limit
    }
}
