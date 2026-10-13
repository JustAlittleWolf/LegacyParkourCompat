# F-014: default Overworld minY changes unloaded-chunk fall velocity

- **1.17.1**, mojmap: `DimensionType.java`.
- **1.18.2**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-014-overworld-min-y-unloaded-chunk-fall.md).

## Difference

- 1.17.1 `DimensionType.DEFAULT_OVERWORLD` supplies minY=0, height=256, logicalHeight=256; 1.18.2 supplies minY=-64, height=384, logicalHeight=384. `DimensionType.java`
- 1.17.1 `LevelReader#getMinBuildHeight` returns `dimensionType().minY()` and `hasChunkAt(BlockPos)` delegates to `hasChunkAt(x,z)`; 1.18.2 has the same behavior. `LevelReader.java`
- In the ordinary-air branch of `LivingEntity#travel`, after the Levitation branch and before gravity, both versions check `level.isClientSide && !level.hasChunkAt(blockPos)`. 1.17.1 sets the vertical local component to `0.0` when `getY() <= getMinBuildHeight()`, otherwise `-0.1`; 1.18.2 performs the same test against its level minY.

For -64 < Y <= 0 this produces 1.17.1 `0.0`, 1.18.2 `-0.1`. `LivingEntity.java`
- The subsequent common vertical friction write multiplies by `0.98F` when friction is not discarded, yielding 1.17.1 `0.0` and 1.18.2 `-0.098`; when friction is discarded, it writes the branch value unchanged, yielding 1.17.1 `0.0` and 1.18.2 `-0.1`. `Level.java`

## Movement meaning

The LocalPlayer ordinary tick reaches inherited `LivingEntity#aiStep` and `travel` when alive, not passenger-controlled, not in water/lava, not fall-flying or ability-flying, and with no Levitation effect. 1.17.1 player can fall below Y=0; `hasChunkAt(BlockPos)` is an XZ chunk-presence test, so an absent XZ chunk satisfies the client fallback independent of the block position's Y.

Under these concrete states the source writes different player vertical velocity and the following move consumes it.

This candidate relies on the built-in default Overworld dimension type on the respective client versions. 1.17.1 server-synchronized dimension type with an equal minY on both sides removes this difference for those inputs.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-014-overworld-min-y-unloaded-chunk-fall.md).
