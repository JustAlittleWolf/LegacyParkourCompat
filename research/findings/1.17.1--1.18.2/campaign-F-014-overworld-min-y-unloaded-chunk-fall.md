# F-014: default Overworld minY changes unloaded-chunk fall velocity

1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel`, lines 2117–2126.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel`, lines 2123–2132.
1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/level/dimension/DimensionType.java`, `DEFAULT_OVERWORLD`, lines 78–97.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/level/dimension/DimensionType.java`, `DEFAULT_OVERWORLD`, lines 78–96.

Difference: Both ordinary-air travel branches use level minY when the XZ chunk is missing and Levitation is absent. Built-in Overworld minY is 0 in 1.17.1 and -64 in 1.18.2. At -64 < player Y <= 0, this writes 0.0 versus -0.1 before the shared vertical friction step (0.0 versus -0.098 when friction applies). Equal server-synchronized minY removes the difference.

Movement meaning: The LocalPlayer ordinary tick reaches inherited `LivingEntity#aiStep` and `travel` when alive, not passenger-controlled, not in water/lava, not fall-flying or ability-flying, and with no Levitation effect. A player can fall below Y=0; `hasChunkAt(BlockPos)` is an XZ chunk-presence test, so an absent XZ chunk satisfies the client fallback independent of the block position's Y.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-014-overworld-min-y-unloaded-chunk-fall.md).
