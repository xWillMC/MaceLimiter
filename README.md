# 🪓 MaceLimiter
A professional, production-ready Minecraft plugin to **limit the number of maces** in the world. Ideal for SMPs, YouTubers, and competitive PvP servers. Made by **NoBowl**.

## ✅ Features
- 🔒 **Limit total maces globally or per world** (default: 1)
- 🎯 Automatically blocks item pickups if the limit is exceeded
- 🛡️ Bypass permission for operators or trusted players
- 💬 Fully configurable messages with placeholders
- 📜 YAML-based config and message files
- 🔍 Admin commands to check, list, purge, and reload
- 🧩 Developer API via `MaceLimiterAPI`

---

## 🛠️ Installation
1. Place the `MaceLimiter-x.x.x.jar` into your server's `/plugins` directory.
2. Start the server to generate default config files.
3. Modify `config.yml` and `messages.yml` as needed.
4. Reload with `/macelimiter reload` or restart the server.

---

## ⚙️ Configuration

### `config.yml`
```yaml
global-limit: true
default-limit: 1

limits-per-world:
  world: 1
  world_nether: 0
  world_the_end: 0

logging:
  enabled: true
  to-console: true
  to-file: true

silent-mode: false
actions:
  on-limit-exceeded: "DENY_PICKUP"

bypass-permission: "macelimiter.bypass"


📦 Developer API
Import MaceLimiterAPI in your plugin and use:

val totalMaces = MaceLimiterAPI.countTotalMaces()
val isMace = MaceLimiterAPI.isMace(itemStack)
val isFull = MaceLimiterAPI.wouldExceedLimit("world")