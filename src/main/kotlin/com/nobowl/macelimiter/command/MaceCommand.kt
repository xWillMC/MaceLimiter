package com.nobowl.macelimiter.command

import com.nobowl.macelimiter.config.ConfigManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class MaceCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission("macelimiter.admin")) {
            sender.sendMessage("§cNo permission.")
            return true
        }

        when (args.getOrNull(0)?.lowercase()) {
            "reload" -> {
                ConfigManager.reload()
                sender.sendMessage(ConfigManager.getMessage("reload-complete"))
            }

            "check" -> {
                val target = if (args.size > 1) Bukkit.getPlayer(args[1]) else sender as? Player
                if (target == null) {
                    sender.sendMessage("§cPlayer not found.")
                    return true
                }
                val count = countMaces(target)
                sender.sendMessage("§e${target.name} holds §a$count §emace(s).")
            }

            "list" -> {
                val owners = Bukkit.getOnlinePlayers().filter { countMaces(it) > 0 }
                if (owners.isEmpty()) {
                    sender.sendMessage(ConfigManager.getMessage("no-maces-found"))
                } else {
                    val list = owners.joinToString(", ") { it.name }
                    sender.sendMessage(ConfigManager.getMessage("list-owners").replace("%list%", list))
                }
            }

            "purge" -> {
                var total = 0
                Bukkit.getWorlds().forEach { world ->
                    total += world.entities.removeIf { it is org.bukkit.entity.Item && isMace(it.itemStack) }.compareTo(false)
                    world.players.forEach { player ->
                        val inv = player.inventory
                        val cleaned = inv.contents.map { if (isMace(it)) null else it }.toTypedArray()
                        inv.contents = cleaned
                        total++
                    }
                }
                sender.sendMessage("§cAll maces purged.")
            }

            else -> {
                sender.sendMessage(
                    """
                    §6MaceLimiter Commands:
                    §e/macelimiter reload §7- Reload the plugin
                    §e/macelimiter check [player] §7- Check mace count
                    §e/macelimiter list §7- List all current mace holders
                    §e/macelimiter purge §7- Remove all maces
                    """.trimIndent()
                )
            }
        }

        return true
    }

    private fun countMaces(player: Player): Int {
        return player.inventory.contents.count { isMace(it) } +
               player.inventory.extraContents.count { isMace(it) }
    }

    private fun isMace(item: ItemStack?): Boolean {
        return item != null && item.type.name.contains("MACE", ignoreCase = true)
    }
}
