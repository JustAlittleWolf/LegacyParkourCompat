# Frozen source roster: 1.8.6 → 1.8.7 falling-water / dry oak fence

Frozen before inspecting the assigned comparison draft or roster. Scope: a dry, ordinary oak fence beside falling water; cue distinguishes liquid on the fence's same-height or upper side; follow vanilla fence shape/material, liquid propagation and flow vector, collision/overlap detection, local-player movement entry and velocity effects. No product implementation or wiki source was consulted.

## Exact source identity

- Both trees are `ornithe-feather` (Ornithe Feather Gen2), exact Feather mapping builds `1.8.6+build.2` and `1.8.7+build.2`.
- 1.8.6 client input SHA-256: `e597384cf964950b02650a39f73c2cbf5ce9e51d934cdfa1291530896e7cf100`; Feather mergedv2 mapping input SHA-256: `d1507e3253a944d59dd0b924b6495deb7732ecadb5f8bd06f7139c241639ad81`; Feather metadata SHA-256: `6815d58a74d666853a7e61f23e3a3cc86596ec02dab374765da12c02018d2a0b`.
- 1.8.7 client input SHA-256: `2e45066ec194c6daec5f7dbb27822ef0b5855d5829f62392906620145689a3e0`; Feather mergedv2 mapping input SHA-256: `e0cd7842acda42ae23d2e9d031358c1faac1ac01a139bb29ac8034fbaa5fa27b`; Feather metadata SHA-256: `690dedfac870ea1fe38cbf622ec2d32ee91efd124a6485a3f3454650ff4160cc`.
- Exact local provenance manifests: `decompiled_minecraft/<version>/ornithe-feather/.decompile-provenance.json`; SHA-256 1.8.6 `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9`, 1.8.7 `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef`. Selected source hashes checked against each manifest; all matched.
- Source inventories contain 1,612 entries each. Comparing manifest path/hash maps identifies 16 differing sources, listed below; none is a client player, entity movement, block, liquid or collision-shape source. This inventory comparison is a search aid; the listed bounded sources and their callers remain the audit roster.

## Paired source roster (SHA-256 identical on both sides)

| Relative source path | SHA-256, both releases | Planned slice/caller closure |
|---|---|---|
| `net/minecraft/block/Blocks.java` | `1da2d85406ed991e8f6bc1f420babf0bd43598478f347c6a4dc9fea322ed0695` | Ordinary oak fence and flowing/source water registrations/material construction. |
| `net/minecraft/block/material/Material.java` | `017713d76afe726ca243ce32cbc35c13d3f0f0e7e5d90d122f81103cc2ca1bd2` | `WOOD` and water `blocksMovement`, solidity and liquid predicates. |
| `net/minecraft/block/FenceBlock.java` | `8d6a803988d77fc51f364520f2e0cd5567b1d5c29a4d28d01cdcf93e6d5890d9` | Collision boxes, neighbor connection gates, shape state, default oak fence behavior. |
| `net/minecraft/block/Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` | Shape/collision dispatch, block material predicates and player/entity collision inputs. |
| `net/minecraft/block/LiquidBlock.java` | `1537fd6b1bca65e0f910f9a12bcd3bb27f5a86be7f13a1caa380c5e66544f1d5` | Liquid depth/height, side-face predicate, `getFlow` same-height/lower-neighbor/upper-side barrier logic, `applyMaterialDrag`. |
| `net/minecraft/block/FlowingLiquidBlock.java` | `16b30954bbb5bc9b40072ad19304a7efe8ac2483ee1a4baeda777fb4145daaf0` | Flowing-water tick, downward spread and neighbor state updates. |
| `net/minecraft/util/math/Direction.java` | `ede4dc29795263853c048e569f24e74407255767c5e02349a90895ac5b08e544` | Horizontal iteration and direction offsets/order. |
| `net/minecraft/util/math/Box.java` | `8fc7bb57b1132afc7ae9539c30ff9c2585f0e36c2b0e59c60946cb1e3cee9eea` | Entity overlap bounds relevant to liquid material query. |
| `net/minecraft/world/World.java` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` | Block-state reads, collision enumeration and material scan context. |
| `net/minecraft/entity/Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | Tick/travel flow; liquid overlap predicate, velocity response, collision and drag callers. |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` | Player movement overrides and fluid-dependent gates, including relative movement. |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` | Local-player tick/input/travel overrides and calls into inherited player/entity movement. |

## Other hash-different mapped sources

`net/minecraft/client/gui/overlay/DebugOverlay.java`, `net/minecraft/client/gui/screen/menu/TitleScreen.java`, `net/minecraft/client/Minecraft.java`, `net/minecraft/client/options/ServerListEntry.java`, `net/minecraft/client/render/TextRenderer.java`, `net/minecraft/client/resource/pack/ResourcePacks.java`, `net/minecraft/network/packet/c2s/play/SignUpdateC2SPacket.java`, `net/minecraft/realms/RealmsSharedConstants.java`, `net/minecraft/server/integrated/IntegratedServer.java`, `net/minecraft/server/MinecraftServer.java`, `net/minecraft/server/network/handler/RemoteHandshakeNetworkHandler.java`, `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`, `net/minecraft/server/world/ServerWorld.java`, `net/minecraft/snooper/Snooper.java`, `net/minecraft/util/crash/CrashReport.java`, `net/minecraft/util/HttpUtil.java`.

This roster is frozen. The post-freeze trace must inspect these paired sources and close the identified method/caller edges before deciding whether the proposed behavior differs.