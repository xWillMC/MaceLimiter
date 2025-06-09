package com.nobowl.macelimiter.util

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object MaceUtil {

    /**
     * Define what qualifies as a "mace".
     * This example uses a custom name + material check.
     */
    fun isMace(item: ItemStack?): Boolean {
        if (item == null || item.type != Material.NETHERITE_AXE) return false

        val meta: ItemMeta = item.itemMeta ?: return false
        return meta.hasDisplayName() && meta.displayName.contains("Mace", ignoreCase = true)
    }

    /**
     * Count total maces in the world.
     * Could be optimized to cache if needed.
     */
    fun countTotalMaces(): Int {
        var total = 0
        for (world in Bukkit.getWorlds()) {
            for (entity in world.entities) {
                if (entity is org.bukkit.entity.Item) {
                    if (isMace(entity.itemStack)) {
                        total++
                    }
                }
                if (entity is org.bukkit.entity.Player) {
                    val inv = entity.inventory
                    inv.contents.filter { isMace(it) }.forEach { total++ }
                }
            }
        }
        return total
    }
}
