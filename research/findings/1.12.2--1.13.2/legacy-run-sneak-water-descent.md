# 1.13.2 adds sneak-controlled downward motion in water

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; lines 649-812.
- **1.13.2**, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; lines 784-787.

## Difference

For a local player whose current input is sneaking while `isInWater()` is true, 1.13.2 subtracts `0.04F` from vertical velocity during the local player tick. 1.12.2 has no corresponding call or helper in the checked player/living source path. 1.13.2 applies this input repeatedly per tick while the condition remains true.

## Movement meaning

The input flag is set by the common keyboard input implementation and consumed by `LocalClientPlayerEntity.mobTick()`.

The condition uses the player's entity water-contact flag; 1.13.2 refreshes that flag through its water-state update.

No server-side diving result is inferred.

Source proves a per-tick downward velocity write in 1.13.2 and its absence from 1.12.2's corresponding path.

The exact interaction with fluid buoyancy and fluid-flow velocity is still in stage 3/5 dependency closure.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.12.2--1.13.2/findings/sneak-water-descent.md).
