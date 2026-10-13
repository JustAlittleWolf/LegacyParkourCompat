# WORLD-22: ground slipperiness providers and reader

- **1.8.9**, ornithe-feather: `LivingEntity.java`; `Block.java`.
- **1.9.4**, ornithe-feather: `LivingEntity.java`; `Block.java`.

## Difference

- 1.9.4 sources: `LivingEntity.java` `Block.java` ; `IceBlock.java` ; `PackedIceBlock.java` ; `SlimeBlock.java` ; `BlockPos.java` .

## Movement meaning

- Scope: water/lava are closed in TICK-11; angle conversion is recorded in TICK-09; client unloaded-chunk sampling and post-move gravity are recorded in TICK-10.

Fall-flying and Levitation are 1.9.4-only and excluded under the one-way historical scope.

Climbing retains the same bounded clamp/sneak writes in both source bodies.

Non-player control-gate cases are outside player movement scope.
- Disposition: shared historical slipperiness constants, two-read order and selected block coordinates match for a player in the supported world coordinate range.

The pooled-position lifetime change does not alter either read coordinate.

No net player displacement claim is made because collision shapes and clipping remain separate.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-22-ground-slipperiness.md).
