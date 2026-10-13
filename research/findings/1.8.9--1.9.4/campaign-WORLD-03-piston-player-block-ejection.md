# WORLD-03: piston cube predicate

- **1.8.9**, ornithe-feather: `PistonBaseBlock.java`, `isCube()`; lines 223-225.
- **1.9.4**, ornithe-feather: `PistonBaseBlock.java`, `isCube(BlockState)`; lines 216-218.

## Difference

Both releases return false from the piston cube predicate. The earlier piston-specific ejection claim therefore lacks the asserted predicate difference.

## Movement meaning

This predicate comparison does not establish a piston-specific player block-ejection delta. The corrected end-portal-frame candidate is a separate source record.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-03-piston-player-block-ejection.md).
