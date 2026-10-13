# F-04: sprint initiation is blocked while fall-flying

- **1.19.3**, mojmap: `1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 709–717.
- **1.19.4**, mojmap: `1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 696–698.

## Difference

Under the stated non-passenger key-sprint preconditions, 1.19.3 can write the sprint state during fall flight and 1.19.4 rejects initiation through the added fall-flying guard.

Passenger eligibility is a separate condition and finding in F-03.

## Movement meaning

The player can reach `LocalPlayer#aiStep` while fall-flying.

The direct key branch does not require being grounded; 1.19.3's listed state predicates can permit its sprint write during flight. 1.19.4's helper includes `!isFallFlying()`. `setSprinting(true)` installs the movement-speed modifier in both versions; the state can feed later non-fall-flying player movement.

F-01 separately covers the airborne sprint-speed consumer.

The paired fall-flying travel equations themselves do not consume sprint state, so this finding does not assert an immediate change to gliding speed or trajectory.

The source pair proves a reachable difference in sprint-state eligibility and ties its state writer to the same movement-speed modifier in both versions. 1.19.3 later non-fall-flying movement tick can observe different sprint state if it has not been cleared by then.

No specific subsequent trajectory is claimed.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.3--1.19.4/findings/F-04-sprint-during-fall-flying.md).
