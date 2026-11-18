# Frozen independent source roster — 1.11 → 1.11.1

Mapping family: Ornithe Feather. Sources are read-only under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather/`.

Before reading the author's report, freeze this roster for the two assigned claims and their direct dependencies:

| Concern | 1.11 and 1.11.1 source files |
|---|---|
| Fence state-to-shape and connections | `net/minecraft/block/FenceBlock.java`; `net/minecraft/block/Block.java`; `net/minecraft/block/state/BlockState.java`; `net/minecraft/block/state/AbstractBlockState.java` |
| Falling-water face / current vector | `net/minecraft/block/FlowingLiquidBlock.java`; `net/minecraft/block/LiquidBlock.java`; `net/minecraft/block/state/BlockState.java`; `net/minecraft/util/math/BlockPos.java`; `net/minecraft/util/math/Direction.java` |
| Direct local-player water-current response and loaded gates | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; `net/minecraft/client/entity/living/player/ClientPlayerEntity.java`; `net/minecraft/entity/living/player/PlayerEntity.java`; `net/minecraft/entity/living/LivingEntity.java`; `net/minecraft/entity/Entity.java`; `net/minecraft/world/WorldView.java`; `net/minecraft/world/World.java`; `net/minecraft/client/world/ClientWorld.java`; `net/minecraft/world/chunk/WorldChunk.java`; `net/minecraft/world/chunk/ChunkSource.java` |

This roster is the audit boundary; trace only the listed operation dependencies needed to decide the two assigned claims. Do not inspect wiki lanes, product implementations/history or other candidate reports.