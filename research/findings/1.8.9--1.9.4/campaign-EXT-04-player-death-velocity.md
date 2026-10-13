# EXT-04: player death velocity uses a different angle conversion

- **1.8.9**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/EXT-04-player-death-velocity.md); lines 512-531.
- **1.9.4**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/EXT-04-player-death-velocity.md); lines 483-502.

## Difference

The degree-to-radian conversion has a different floating-point operation order: 1.8.9 converts `Math.PI` to float before multiplication/division; 1.9.4 divides in double precision and casts the quotient to float before multiplying.

The formula feeds the direct X/Z velocity assignment.

Both endpoints preserve the same vertical `0.1F` write and the same null-source horizontal-zero branch.

This records only a player velocity assignment in the death handler.

Health and damage production, death/respawn lifecycle, and subsequent motion are not evaluated.

## Movement meaning

The degree-to-radian conversion has a different floating-point operation order: 1.8.9 converts `Math.PI` to float before multiplication/division; 1.9.4 divides in double precision and casts the quotient to float before multiplying.

The formula feeds the direct X/Z velocity assignment.

Both endpoints preserve the same vertical `0.1F` write and the same null-source horizontal-zero branch.

This records only a player velocity assignment in the death handler.

Health and damage production, death/respawn lifecycle, and subsequent motion are not evaluated.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/EXT-04-player-death-velocity.md).
