# Correction: F-07 is withdrawn after rechecking the boolean query argument flow

- **1.10.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.10.2--1.11.2/findings/F-07-correction-helper-argument-flow.md).
- **1.11.2**, ornithe-feather: `1.11.2/ornithe-feather/net/minecraft/world/World.java`.

## Difference

The earlier stair pose-fit claim misread enforceWorldBorder as forceShape. 1.11.2 passes false to shape providers and resolves virtual neighbor properties, matching 1.10.2.

## Movement meaning

Both releases resolve the outer-stair neighbor shape for the boolean collision query; the earlier stale straight-shape example therefore does not change the player size-fit result.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.10.2--1.11.2/findings/F-07-correction-helper-argument-flow.md).
