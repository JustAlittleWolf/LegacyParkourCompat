# 1.8.5–1.8.8 pooled method/hash sweep

## Inputs and source identity

Canonical sources are read-only at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather/`, mapping family `ornithe-feather` for all four releases. Each mapping directory has `.decompile-provenance.json`; no release-parent marker exists at `decompiled_minecraft/<version>/.decompile-provenance.json` for any of the four. Provenance records distinct Mojang client artifact object IDs and SHA-256 values:

| Release | Client artifact object | Client JAR SHA-256 | Provenance marker SHA-256 | Sources verified |
|---|---|---|---|---:|
| 1.8.5 | `5d39957c61d19042f8bd669a6faa99989cf37083` | `2d825073de0da4383c0525e81d181ec81f92d17daa2f0b81faff3850492e461d` | `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` | 1,612 / 1,612 |
| 1.8.6 | `faa55e34ded35089d34fe921ea83d317fc152e93` | `e597384cf964950b02650a39f73c2cbf5ce9e51d934cdfa1291530896e7cf100` | `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9` | 1,612 / 1,612 |
| 1.8.7 | `d546a6a092060c85f1eb1d9213ff823c558b1255` | `2e45066ec194c6daec5f7dbb27822ef0b5855d5829f62392906620145689a3e0` | `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef` | 1,612 / 1,612 |
| 1.8.8 | `0983f08be6a4e624f5d85689d1aca869ed99c738` | `9481ed51d7fc4be54ec38c509f84124fbac5d9fea238bbde5b2e6c4f753b1ac8` | `588f83dc64ce0bdf0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` | 1,612 / 1,612 |

The provenance source hash maps matched every Java source file on disk. Provenance is present inside each mapping directory; the separately requested release-parent provenance marker is absent and remains an evidence limitation. The generated source root contains no other mapping directory for these releases.

## Hash-pruning results

Compared SHA-256 for every `.java` path in each adjacent pair (1,612 source files per release). Modified Java paths: 21 for 1.8.5→1.8.6, 16 for 1.8.6→1.8.7, and 28 for 1.8.7→1.8.8. None of the modified paths is a player/entity movement implementation or a block collision/movement provider. Core methods therefore prune as unchanged with their complete enclosing class file hashes; full SHA-256 values for the tick, input, player, world and block-provider source classes are in `movement-core-sha256.tsv`.

Modified paths, for exhaustive accounting:

- **1.8.5→1.8.6:** `block/entity/BlockEntity.java`, `block/entity/CommandBlockBlockEntity.java`, `block/entity/MobSpawnerBlockEntity.java`, `block/entity/SignBlockEntity.java`, `Bootstrap.java`, `client/gui/overlay/DebugOverlay.java`, `client/gui/screen/menu/TitleScreen.java`, `client/Minecraft.java`, `client/options/ServerListEntry.java`, `item/BlockItem.java`, `item/PlaceableItem.java`, `item/SignItem.java`, `realms/RealmsSharedConstants.java`, `server/integrated/IntegratedServer.java`, `server/MinecraftServer.java`, `server/network/handler/RemoteHandshakeNetworkHandler.java`, `server/network/handler/ServerPlayNetworkHandler.java`, `snooper/Snooper.java`, `util/crash/CrashReport.java`, `world/BlockSource.java`, `world/IBlockSource.java`.
- **1.8.6→1.8.7:** `client/gui/overlay/DebugOverlay.java`, `client/gui/screen/menu/TitleScreen.java`, `client/Minecraft.java`, `client/options/ServerListEntry.java`, `client/render/TextRenderer.java`, `client/resource/pack/ResourcePacks.java`, `network/packet/c2s/play/SignUpdateC2SPacket.java`, `realms/RealmsSharedConstants.java`, `server/integrated/IntegratedServer.java`, `server/MinecraftServer.java`, `server/network/handler/RemoteHandshakeNetworkHandler.java`, `server/network/handler/ServerPlayNetworkHandler.java`, `server/world/ServerWorld.java`, `snooper/Snooper.java`, `util/crash/CrashReport.java`, `util/HttpUtil.java`.
- **1.8.7→1.8.8:** `Bootstrap.java`, `client/gui/overlay/DebugOverlay.java`, `client/gui/overlay/PlayerTabOverlay.java`, `client/gui/screen/menu/resourcepacks/CustomResourcePackEntry.java`, `client/gui/screen/menu/resourcepacks/DefaultResourcePackEntry.java`, `client/gui/screen/menu/resourcepacks/ResourcePackEntry.java`, `client/gui/screen/menu/resourcepacks/ResourcePacksScreen.java`, `client/gui/screen/menu/TitleScreen.java`, `client/gui/screen/ProgressScreen.java`, `client/Minecraft.java`, `client/options/GameOptions.java`, `client/options/ServerListEntry.java`, `client/render/entity/layer/WornSkullLayer.java`, `client/render/platform/GLX.java`, `client/render/texture/LayeredColorMaskTexture.java`, `client/resource/pack/ResourcePacks.java`, `client/resource/skin/SkinManager.java`, `realms/Realms.java`, `realms/RealmsConnect.java`, `realms/RealmsSharedConstants.java`, `server/integrated/IntegratedServer.java`, `server/MinecraftServer.java`, `server/network/handler/RemoteHandshakeNetworkHandler.java`, `server/network/handler/ServerQueryNetworkHandler.java`, `snooper/Snooper.java`, `util/crash/CrashReport.java`, `util/Utils.java`, `world/chunk/storage/AnvilChunkStorage.java`.

## Classification of changed candidates

- 1.8.5→1.8.6 adds a `requireOpForPlacingWithNbt()` predicate for command blocks, spawners and signs. `BlockItem` / `PlaceableItem` / `SignItem` pass the player to the NBT setup gate; the server side rejects protected block-entity NBT for non-operators. This is placement authorization/block-entity data handling, not a player movement response or historical movement provider. The normal block state and player pose/velocity/box paths are unchanged.
- `Minecraft.java` changes across the range are version strings, resource-pack cleanup or crash-report details. The inspected hunks do not touch input, tick, travel, collision or position/velocity writes. `DebugOverlay`, `TitleScreen`, version/snooper/server handshake/report changes are presentation or version metadata.
- `ServerWorld` removes a debug log; `AnvilChunkStorage` changes save logging/flush bookkeeping. Other server/network hunks are sign formatting, HTTP/resource-pack handling or version metadata. They do not run historical player physics, and no client movement method/provider changed.
- 1.8.7→1.8.8 `PlayerTabOverlay` changes cape rendering conditions, which falls under the rendering exclusion.

No new movement finding from these three adjacent patch intervals. Do not repeat or revise the already accepted 1.8.4→1.8.5 speed-cap evidence. The frozen 1.8.2→1.8.3 finding inventory is reused by the coordinator; this sweep does not reopen it.

## Coverage status and next bounded work

This is a complete Java-file hash-pruning pass for the three requested intervals with focused method/caller inspection of client tick/version paths, block-entity placement gates and changed server candidates. No player movement/provider file changed. It does not establish complete pair tick/inventory coverage, dependency closure or independent audit, so it is not a pair freeze and must not be reported as one. Any broader pair closure still needs the source lane's full reachable tick and provider inventory plus blind audit.
