# Frozen source roster

Assignment: Mojmap exact 1.14.4 to 1.15, portal-adjacent block dismount and local detach/network correction scope.
Freeze captured before reading author drafts. Host/shell: Windows PowerShell.
Source roots: `decompiled_minecraft/1.14.4/mojmap/` and `decompiled_minecraft/1.15/mojmap/`.
Both mapping roots lack `.decompile-provenance.json`; their decompilation source provenance is unverified by repository manifests. Mappings identified by the selected `mojmap` root only; exact artifact/build identity unavailable in these trees.

| Relative source file | Bytes | SHA-256 |
|---|---:|---|
| `1.14.4/mojmap/net/minecraft/world/entity/Entity.java` | 92319 | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |
| `1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java` | 103514 | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` |
| `1.14.4/mojmap/net/minecraft/world/entity/player/Player.java` | 74436 | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` |
| `1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java` | 39123 | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |
| `1.14.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java` | 121500 | `6dddf37b79830d5413d91675e0bed8b4a3f0a179ac4535616dc57b510b778b45` |
| `1.14.4/mojmap/net/minecraft/server/network/ServerGamePacketListenerImpl.java` | 75742 | `912f528c03da34fa703646b6383c2b49ac3493efd7ba78cb8ff27aa7f693b67d` |
| `1.14.4/mojmap/net/minecraft/server/level/ServerPlayer.java` | 55823 | `0b07bb15e2e3001f03dab2dc943cbca4814ada36c82b6d83f933be4ff01c4b92` |
| `1.14.4/mojmap/net/minecraft/network/protocol/game/ServerboundPlayerCommandPacket.java` | 1887 | `7aca8116c660890a0ddf6284daa238c3b528e94c0885f171c00a6cfd24284879` |
| `1.15/mojmap/net/minecraft/world/entity/Entity.java` | 94257 | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `1.15/mojmap/net/minecraft/world/entity/LivingEntity.java` | 105989 | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| `1.15/mojmap/net/minecraft/world/entity/player/Player.java` | 77673 | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |
| `1.15/mojmap/net/minecraft/client/player/LocalPlayer.java` | 38838 | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| `1.15/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java` | 126606 | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` |
| `1.15/mojmap/net/minecraft/server/network/ServerGamePacketListenerImpl.java` | 76414 | `3d46e455509891a3e0ef50472610d565255f5991987e971ba00af2140d25982c` |
| `1.15/mojmap/net/minecraft/server/level/ServerPlayer.java` | 54707 | `719416651f82ff78e9990a77e5bea0998938bf8070898865c37ae2df6e3b22da` |
| `1.15/mojmap/net/minecraft/network/protocol/game/ServerboundPlayerCommandPacket.java` | 1892 | `057daaa7635a76c11ce65106c57fcfd7dd520ed643e4871e22fdf033a6905e55` |

Roster limits: Entity/LivingEntity for riding and dimension placement; Player/LocalPlayer/ClientPacketListener and ServerGamePacketListenerImpl/ServerPlayer/ServerboundPlayerCommandPacket for local initiation, client packet dispatch, server counterpart and authoritative correction. Neighbor block sources excluded because the requested predicate and placement are defined at player detach/dismount operation; no block state or shape change reviewed.
