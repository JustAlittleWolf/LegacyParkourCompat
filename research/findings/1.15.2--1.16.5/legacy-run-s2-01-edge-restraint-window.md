# S2-01: Crouch edge restraint applies in a different support window

- **1.15.2**, mojmap: `Player.java`; lines 1002–1035.
- **1.16.5**, mojmap: `Player.java`; lines 1010–1043.

## Difference

The shared X-then-Z-then-both backoff loops reduce components in 0.05 steps. 1.15.2 enters them only when on ground. 1.16.5 enters when its `isAboveGround` predicate succeeds, and skips them for ability flight.

## Movement meaning

`Entity.move` calls the player override for SELF/PLAYER movement before collision resolution.

With shift held, a player falling less than `maxUpStep` while a downward support probe remains collision-free can have horizontal movement backed off in 1.16.5 while 1.15.2 skips the loop because `onGround` is false. 1.15.2 flying player on ground is restrained in 1.15.2 but excluded in 1.16.5.

The code changes the horizontal displacement submitted to collision resolution under those states.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.15.2--1.16.5/findings/s2-01-edge-restraint-window.md).
