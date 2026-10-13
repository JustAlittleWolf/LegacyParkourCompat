# F-S3-GLIDE-THROUGH-CLIMBABLE: Fall-flying Players no longer take the climbable fallback on tagged vines

- **1.21.10**, mojmap: `1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 1662-1678.
- **1.21.11**, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 1669-1687.

## Difference

For a Player with `abilities.flying == false`, `isSpectator() == false`, `isFallFlying() == true`, and a current block state at `blockPosition()` that is one of the vanilla-default tag intersection states (vine, twisting vine or plant, weeping vine or plant, cave vine or plant), outside the earlier fluid-travel branch, 1.21.10 reports the Player climbable and 1.21.11 reports it not climbable.

The `Player.onClimbable()` wrapper delegates to `LivingEntity.onClimbable()` under this ability guard. 1.21.10 then calls `travelInAir()` and `stopFallFlying()`; 1.21.11 follows the fall-flying movement branch.

This is a branch and state-write difference, not a measured trajectory.

## Movement meaning

The intersection blocks are present in 1.21.10's vanilla `climbable` tag and are existing blocks in the 1.21.10 endpoint artifact.

This is a direct player movement-path difference; it does not depend on custom tags for the stated vanilla-default case.

Server/resource-pack tag overrides are not assessed.

The glide formula itself is otherwise unchanged for an equal branch decision.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.10--1.21.11/findings/F-S3-GLIDE-THROUGH-CLIMBABLE.md).
