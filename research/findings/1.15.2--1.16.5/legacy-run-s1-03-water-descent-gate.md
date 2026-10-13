# S1-03: Gate water descent input on fluid-affectability

- **1.15.2**, mojmap: `1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 722–725.
- **1.16.5**, mojmap: `1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 732–735.

## Difference

The water-shift path still subtracts `0.04F` from vertical velocity when it runs, but 1.16.5 suppresses the call when the player's abilities are flying. 1.15.2 has no fluid-affectability guard at this call site.

In 1.16.5, the predicate is exactly false while `abilities.flying` is true.

## Movement meaning

`LocalPlayer.aiStep()` consumes shift state and updates vertical velocity before the parent living movement step calls `travel`.

For a player in water with flight enabled and shift held, 1.15.2 applies the downward velocity increment; 1.16.5 skips it. 1.16.5's player-specific gate is in `Player.isAffectedByFluids`; this does not establish the separate server's fluid rules.

The source proves a `-0.04F` vertical-velocity addition in 1.15.2 and no such addition from this path in 1.16.5 for a flying player.

It predicts a different subsequent vertical movement input in the same tick; actual velocity after later travel/collision depends on other gates.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.15.2--1.16.5/findings/s1-03-water-descent-gate.md).
