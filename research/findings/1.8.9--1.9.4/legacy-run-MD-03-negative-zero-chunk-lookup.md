# MD-03: Unloaded-chunk fallback selects a different chunk near zero

- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; lines 1170-1184.
- **1.9.4**, ornithe-feather: `1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; lines 1396-1412.

## Difference

For a negative fractional X or Z coordinate between `-1.0` and `0.0`, 1.8.9's explicit Java `(int)` conversion truncates toward zero and queries block coordinate `0`; 1.9.4's pooled mutable position floors the coordinate and queries block coordinate `-1`.

The world then shifts that block coordinate by four to select the chunk, so these positions belong to chunks 0 and -1 respectively.

The fallback predicates and resulting vertical-velocity assignments are otherwise the same.

## Movement meaning

The local player reaches `LivingEntity.moveRelative()` in the ordinary non-water, non-lava, non-fall-flying branch while locally controlled.

For this slice 1.9.4 must not have Levitation active, because its new Levitation branch bypasses the chunk fallback.

The chunk lookup occurs after `move`, using the resulting player X/Z. `World.isChunkLoaded` and `World.getChunk` derive their selected chunk from the integer block coordinates using `>> 4`.

Source proves a distinct chunk lookup only at the negative-zero boundary.

If one of chunks -1 and 0 is loaded while the other is not, 1.8.9 and 1.9.4 can take opposite fallback branches.

For `y > 0.0`, the selected chunk determines whether this code assigns `-0.1` or subtracts `0.08` before the shared `0.98F` vertical drag.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.8.9--1.9.4/findings/MD-03-negative-zero-chunk-lookup.md).
