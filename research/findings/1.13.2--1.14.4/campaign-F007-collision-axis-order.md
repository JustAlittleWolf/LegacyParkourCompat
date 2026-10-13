# F007: Collision axis order

- **1.13.2**, ornithe-feather: `Entity.java`; lines 578-599.
- **1.14.4**, ornithe-feather: `Entity.java`; lines 444-475.

## Difference

1.13.2 obtains one collision list and resolves Y, then X, then Z. 1.14.4 delegates to a solver that resolves Y and orders horizontal axes by requested component magnitude, so diagonal motion can resolve Z before X or X before Z.

## Movement meaning

Living travel and other displacement callers invoke Entity.move.

Collision shape query generation and step solver candidate selection are separate open slices; this finding is limited to ordinary axis resolution in the cited movement call.

With competing diagonal obstacles, a different horizontal resolution order can change the final resolved vector.

This is a source-level prediction; no world was simulated.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F007-collision-axis-order.md).
