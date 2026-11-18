# Frozen packet-writer source supplement — 1.9.3 → 1.9.4

Frozen before opening either listed Java implementation. Scope is only to resolve the destination of the final `networkHandler.teleport(...)` call from `PlayerManager.changeDimension` and the packet it constructs/sends.

| Relative source path | SHA-256, 1.9.3 | SHA-256, 1.9.4 |
| --- | --- | --- |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `f96587355f356f95362dbca2c57c043fdb3ee8ee2fe81589f4c3379ab1a0336b` | `f96587355f356f95362dbca2c57c043fdb3ee8ee2fe81589f4c3379ab1a0336b` |
| `net/minecraft/network/packet/s2c/play/PlayerMoveS2CPacket.java` | `8bad09e146ce746085103081b7e3c6d09855114868267e12be8d869f34f0e77e` | `8bad09e146ce746085103081b7e3c6d09855114868267e12be8d869f34f0e77e` |

Both endpoint hashes were recomputed and matched their source manifests. These supplement the independently frozen call-chain roster in `source-roster.md`; they do not expand the operation beyond the server-side transfer correction writer.