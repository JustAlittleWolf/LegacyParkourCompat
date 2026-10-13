# TICK-10: unloaded-client-chunk sampling uses different block coordinates

- **1.8.9**, ornithe-feather: `LivingEntity.java`; lines 1119-1186.
- **1.9.4**, ornithe-feather: `BlockPos.java`; lines 1302-1485.

## Difference

1.8.9 truncates the floating coordinate toward zero before choosing a chunk; 1.9.4 floors it.

The resulting chunk differs only at negative chunk boundaries for coordinates just below a multiple of 16, such as `-0.2` (1.8.9 stores 0, 1.9.4 stores -1).

If this maps the two versions to chunks with different loadedness on a client, their Y-velocity update takes different branches: ordinary gravity versus the unloaded fallback (`-0.1` above Y=0, otherwise `0`), followed by the shared `0.98F` multiplier.

No chunk-loading state or subsequent trajectory is inferred.

## Movement meaning

1.8.9 truncates the floating coordinate toward zero before choosing a chunk; 1.9.4 floors it.

The resulting chunk differs only at negative chunk boundaries for coordinates just below a multiple of 16, such as `-0.2` (1.8.9 stores 0, 1.9.4 stores -1).

If this maps the two versions to chunks with different loadedness on a client, their Y-velocity update takes different branches: ordinary gravity versus the unloaded fallback (`-0.1` above Y=0, otherwise `0`), followed by the shared `0.98F` multiplier.

No chunk-loading state or subsequent trajectory is inferred.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/TICK-10-client-unloaded-chunk-coordinate-sampling.md).
