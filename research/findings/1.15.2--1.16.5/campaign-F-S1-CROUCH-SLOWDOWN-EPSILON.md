# F-S1-CROUCH-SLOWDOWN-EPSILON: pose-clearance epsilon changes keyboard slowdown

- **1.15.2**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S1-CROUCH-SLOWDOWN-EPSILON.md).
- **1.16.5**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S1-CROUCH-SLOWDOWN-EPSILON.md).

## Difference

1.15.2 `LocalPlayer#isCrouching()` computes the crouch predicate on demand and passes it through `isMovingSlowly()` to the same-tick `input.tick(boolean)`. 1.16.5 writes the same predicate to `crouching` immediately before that call, and its `isCrouching()` returns the cached field.

The adjacent writer/consumer order means the cache itself introduces no intervening tick or input-sampling delay.

The predicate's `canEnterPose(Pose.CROUCHING)` dependency differs: 1.15.2 submits the full candidate box to `Level.noCollision`; 1.16.5 first deflates the candidate box by `1.0E-7`.

With the shift key already down, and with a block collision shape intersecting only the boundary shell removed by that deflation, 1.15.2's clearance test is blocked while 1.16.5's is clear.

Assuming the player is not flying or swimming and is not visually crawling, 1.15.2 therefore passes `false` to `KeyboardInput#tick`, while 1.16.5 passes `true`.

Both versions' keyboard input bodies then multiply the forward and lateral impulses by `0.3` when that flag is true.

## Movement meaning

The resulting input vector is consumed by the ordinary player travel path. `LocalPlayer#isShiftKeyDown()` reads the already sampled `input.shiftKeyDown` in both versions.

For the stated narrow collision-boundary condition, 1.16.5 can scale both movement input axes by `0.3` for this input tick when 1.15.2 does not.

Which concrete fixed-state block layout realizes the condition remains subject to the open provider inventory.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S1-CROUCH-SLOWDOWN-EPSILON.md).
