# F-1.20.2-PASSENGER-CROUCH-INPUT: passenger crouch input no longer scales local player movement axes

- **1.20.1**, mojmap: `1.20.1/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 657-665.
- **1.20.2**, mojmap: `1.20.2/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 655-664.

## Difference

In 1.20.1, when a passenger is not flying or swimming, the LocalPlayer crouching flag can be true if the crouching pose fits and either shift is held or standing does not fit.

In 1.20.2, a new `!isPassenger()` conjunct forces that local flag false for every passenger and short-circuits the following pose-fit queries.

The flag is read by `isCrouching()`; `isMovingSlowly()` passes it to `KeyboardInput.tick`.

Under the concrete preconditions of a passenger, shift held, crouching pose fit, not visually crawling, and sneak-speed multiplier below 1, 1.20.1 scales both movement inputs by `clamp(0.3F + sneakingSpeedBonus, 0.0F, 1.0F)`; 1.20.2 does not scale them.

With the default zero bonus, this is a 0.3 multiplier in 1.20.1 and 1.0 in 1.20.2.

The actual entity pose is selected separately in `Player.updatePlayerPose()`.

Since that method still gives passengers the selected pose and selects CROUCHING from shift input, this finding concerns local movement-input magnitude rather than a changed collision pose or dimensions.

## Movement meaning

Local client keyboard shift -> LocalPlayer.aiStep crouching flag -> isCrouching/isMovingSlowly -> KeyboardInput directional impulses -> LocalPlayer.serverAiStep xxa/zza -> LivingEntity.aiStep Vec3 -> Player.travel -> LivingEntity.travel -> Entity.moveRelative.

The local player's isEffectiveAi override and the unchanged controlled-instance predicate establish the local travel branch.

The new guard's concrete precondition is a passenger tick where the old crouching-fit condition succeeds; it does not require changing the vehicle's movement.

Actual vehicle repositioning and final mounted trajectories are outside this source conclusion.

The pose-fit helper replacement is semantically equal for this path: old Entity.canEnterPose used getBoundingBoxForPose, which used Player.getDimensions(pose) at the current position, deflated the box by 1.0E-7, and called CollisionGetter.noCollision.

The new Player helper directly constructs the same box and uses the same noCollision call; Player dimensions and the collision-query body match.

See S2-POSE-CLEARANCE.

Source-proven: in the stated passenger state, 1.20.1 supplies smaller horizontal input impulses to the local player's travel calculation and 1.20.2 supplies the unslowed impulses; the move-relative operation preserves sub-unit input magnitude.

Predicted: all else equal, this changes the player's own directional movement acceleration while mounted.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.20.1--1.20.2/findings/F-1.20.2-PASSENGER-CROUCH-INPUT.md).
