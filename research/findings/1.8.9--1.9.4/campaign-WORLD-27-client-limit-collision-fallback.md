# WORLD-27: client collision scan at the horizontal world-coordinate limit

- **1.8.9**, ornithe-feather: `World.java`; `ClientChunkCache.java`.
- **1.9.4**, ornithe-feather: `World.java`; `ClientWorld.java`.

## Difference

1.8.9 hard-coordinate query gate and 1.9.4 allowEmpty client gate differ, but the proposed +/-30,000,000 fallback collision witness is invalid: available vanilla cap producers supply 29,999,984.

## Movement meaning

The local player reaches Entity.move and World.getCollisions. At the actual vanilla cap, the client reads the unloaded candidate as air rather than inserting the claimed fallback stone cube, so that example does not establish a player clipping difference.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-27-client-limit-collision-fallback.md).
