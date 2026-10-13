# F-3: Diagonal keyboard input normalizes at different precision

- **1.20.6**, mojmap: `1.20.6/mojmap/net/minecraft/client/player/KeyboardInput.java`; lines 21–37.
- **1.21.11**, mojmap: `1.21.11/mojmap/net/minecraft/client/player/KeyboardInput.java`; lines 23–36.

## Difference

For a diagonal keyboard vector with no sneak or item-use multiplier, 1.20.6 delays normalization until `Entity.getInputVector`: each axis first becomes `0.98F`, then the vector is normalized in double precision because its squared length exceeds 1.

At yaw 0, each horizontal component evaluates to approximately `0.7071067811865476`.

1.21.11 first normalizes `(1.0F, 1.0F)` in `Vec2` float precision, then `modifyInputSpeedForSquareMovement()` returns the float direction scaled to the unit-square diagonal.

The resulting components evaluate to approximately `0.7071067690849304`; their squared vector length is below 1, so the later `Entity.getInputVector()` guard does not renormalize them.

The difference is approximately `-1.21e-8` per horizontal component for this precondition.

## Movement meaning

`KeyboardInput.tick()` runs on the local client. 1.20.6's `LivingEntity.aiStep()` applies the 0.98 factor before travel; 1.21.11's local-player `applyInput()` override transfers the transformed input to the same movement fields before travel. `Entity.moveRelative()` / `getInputVector()` turns those fields into yaw-relative acceleration.

The finding is limited to the keyboard diagonal/no-slowdown path; analog/controller input and crouch/item scaling remain separate open dependencies.

The source and direct float/double evaluation prove a small input-component difference.

It predicts a correspondingly small difference in the horizontal acceleration input for a diagonal move.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.20.6--1.21.11/findings/F-3-diagonal-input-precision.md).
