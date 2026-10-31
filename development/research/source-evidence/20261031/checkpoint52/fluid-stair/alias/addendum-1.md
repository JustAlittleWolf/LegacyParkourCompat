# Addendum 1: impulse path alias and 1.14 stair domain

This addendum extends the source-only audit in `report.md`. It binds the additional client-player applicability and registry-domain conclusions to the exact source revisions identified below.

## 1.13.1 and 1.13.2 body scan and player impulse are source aliases

Exact roots are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.13.1/ornithe-feather` and `.../1.13.2/ornithe-feather`; both are Ornithe Feather Gen2 build 2. Their ready/provenance/source-manifest identities are recorded in `report.md`. Each compared file's on-disk SHA-256 matches its version's source manifest.

| Relative source file | SHA-256, 1.13.1 and 1.13.2 |
|---|---|
| `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |

`Entity.tick` calls `baseTick` (`Entity.java:337`); its water-state path calls `m_69693160(FluidTags.WATER)` (`Entity.java:960–980`). The exact same `Entity.java` bytes at both endpoints therefore preserve the body-fluid scan and its consumer route. `m_69693160(Tag<Fluid>)` (`Entity.java:2509–2571`) contracts the entity box by `0.001`, bounds the integer-cell scan, rejects an unloaded area, walks X/Y/Z, filters fluid by the requested tag and surface height, updates maximum immersion depth, and—when liquid collision is enabled—queries each qualifying `FluidState.getFlow`. It scales a contributor by the running depth only below `0.4`, sums contributors and counts them, averages a nonzero aggregate by count, normalizes only when `this` is not a `PlayerEntity`, and then adds each component times `0.014` to velocity. Thus the previously reviewed one-cell falling-water witness receives the direct `(0,-0.014,0)` velocity write under the same paired scan assumptions at 1.13.1 as at 1.13.2.

The local player inherits that writer: `LocalClientPlayerEntity extends ClientPlayerEntity` (`LocalClientPlayerEntity.java:89`), `ClientPlayerEntity extends PlayerEntity` (`ClientPlayerEntity.java:22`), and `PlayerEntity extends LivingEntity` (`PlayerEntity.java:98`), which in turn inherits `Entity`. `LivingEntity.moveRelative` and its travel caller are identical across the two releases because the complete `LivingEntity.java` hash matches; it consumes the resulting velocity through the ordinary travel/move path. This closes client-player operation applicability for the alias pair. It does not claim a world-state producer history or a later position trajectory.

## Independent 1.14.0 predicate check across the old stair domain

The exact source root is `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.14/yarn`, Minecraft 1.14 with Yarn `1.14+build.21`, as identified in the 1.14 source provenance and `../1132-1144-finish46/fluid-face-boundary/result.md`. On-disk source hashes:

| Relative source file | SHA-256 |
|---|---|
| `net/minecraft/fluid/BaseFluid.java` | `c596bd4fa6aaf4f176c4be18d9e3125ff8b3e47846cf5a99253cccc9ba7bf5b5` |
| `net/minecraft/block/Block.java` | `799e08e9631c0a6cdb74be2faeacb054f3c638e1b35c13863eeeba96417b2f2c` |
| `net/minecraft/block/StairsBlock.java` | `370dbbe6502e888c952c0104ce9b4cd49ee8a0c9d0149ca4394c695da48cbe3d` |
| `net/minecraft/block/Blocks.java` | `62acdf407d1fa85095da0351d441b5ece10681b036505f7b9972bc21f77b0a0d` |
| `net/minecraft/data/server/BlockTagsProvider.java` | `f389b69d9abfe6d001b3d357acbe2b7d5da1eeb4f4ee802a6d423ee4e78cce41` |

`BaseFluid.method_15749` (`BaseFluid.java:105–114`) has no stairs-class exclusion. After rejecting a same-fluid neighbor and accepting UP, its horizontal path rejects ICE and delegates to `Block.isSolidFullSquare` (`Block.java:388–395`). That helper rejects the `LEAVES` tag, then tests whether the collision shape fills the queried face. The generated default `LEAVES` recipe contains the six leaf registrations only (`BlockTagsProvider.java:288–289`); it contains no stair ID.

The 1.14 `Blocks.java` source registers 31 stair IDs as `StairsBlock`; all 17 IDs in the 1.13.1/1.13.2 domain are present: `oak_stairs`, `cobblestone_stairs`, `brick_stairs`, `stone_brick_stairs`, `nether_brick_stairs`, `sandstone_stairs`, `spruce_stairs`, `birch_stairs`, `jungle_stairs`, `quartz_stairs`, `acacia_stairs`, `dark_oak_stairs`, `prismarine_stairs`, `prismarine_brick_stairs`, `dark_prismarine_stairs`, `red_sandstone_stairs`, and `purpur_stairs`. They all instantiate the same `StairsBlock` provider with copied base-block settings. Its state-indexed outline shape (`StairsBlock.java:37–107`) is used for collision shape; for the bottom, straight, south-facing, non-waterlogged state, the SOUTH collision face is full. Consequently the 1.14 predicate accepts that same SOUTH face for each of the 17 old IDs. This is the specific conditional behavior: 1.14 no longer categorically rejects a stair; a stair face passes when it is not tagged LEAVES and its collision shape fills that face. It does not mean every face of every stair state passes.

The other 14 stair IDs registered in 1.14 are not in the 1.13.1/1.13.2 historical domain: `andesite_stairs`, `diorite_stairs`, `end_stone_brick_stairs`, `granite_stairs`, `mossy_cobblestone_stairs`, `mossy_stone_brick_stairs`, `polished_andesite_stairs`, `polished_diorite_stairs`, `polished_granite_stairs`, `red_nether_brick_stairs`, `smooth_quartz_stairs`, `smooth_red_sandstone_stairs`, `smooth_sandstone_stairs`, and `stone_stairs`. Their existence proves the 1.14 registry is wider, not that those blocks had an old stair equivalent. For a historical 1.13.x profile, keep these 14 modern-only IDs on the native 1.14 predicate; the old categorical compatibility rule is scoped to the 17 older stair IDs. The old 17-key lists match between 1.13.1 and 1.13.2, and every key is present in the 1.14 registry.

This recheck renews neither the accepted candidate nor its fixture assumptions; it confirms the old-ID applicability and the exact-version domain boundary against 1.14.0 source.
