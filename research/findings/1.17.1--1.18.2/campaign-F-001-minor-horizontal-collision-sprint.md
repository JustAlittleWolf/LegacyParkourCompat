# F-001: Minor horizontal collisions no longer always stop local sprinting

- **1.17.1**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-001-minor-horizontal-collision-sprint.md); lines 715-724.
- **1.18.2**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-001-minor-horizontal-collision-sprint.md); lines 708-718.

## Difference

1.17.1 LocalPlayer#aiStep lines 715-724 stops a sprinting, non-swimming player when the forward/food-or-flight gate fails, any horizontal collision is present, or the player is in water but not underwater.

LocalPlayer.java

1.18.2 LocalPlayer#aiStep lines 708-718 changes the collision term to horizontalCollision && !minorHorizontalCollision.

LocalPlayer.java

The 1.18.2-only minorHorizontalCollision field is written after Entity#move resolves movement: horizontal collision sets it from LocalPlayer#isHorizontalCollisionMinor(resolvedDelta), otherwise the field is reset to false. 1.18.2 classifier lines 999-1014 rotates xxa/zza by yaw, rejects squared input or resolved horizontal magnitudes below 1.0E-5, computes the angle between the world-space input and resolved displacement, and returns true below 0.13962634F. 1.18.2 Entity#move lines 543-674 and classifier base lines 676-678; Entity.java 1.17.1 Entity#move lines 534-651 writes horizontalCollision from requested/resolved X/Z deltas and has no minor flag; Entity.java

Input path on 1.17.1: KeyboardInput#tick lines 14-27 maps key pairs to -1, 0 or 1 and applies the slow factor Input.java fields, getMoveVector and hasForwardImpulse lines 5-24

Input path on 1.18.2: KeyboardInput#tick lines 22-36 calculates the same discrete impulses and applies 0.3F when slowed Input.java corresponding fields/getters lines 5-24

In both versions LocalPlayer#aiStep ticks input before the sprint gate.

LivingEntity#aiStep calls LocalPlayer#serverAiStep, which copies current input.leftImpulse/forwardImpulse to xxa/zza, then calls travel; Entity#move writes collision flags during that movement.

LocalPlayer#aiStep reads the prior movement's collision flag before entering super.aiStep on the next tick. 1.17.1 LivingEntity.java 1.18.2

## Movement meaning

Use forward keyboard input with no lateral input, on ground, with negligible prior horizontal velocity, outside water and away from the world border and other entities. 1.17.1 wall made from a stone block exists in both versions: Blocks.STONE is registered as a plain Block at lines 39-41 in both Blocks.java files, and BlockBehaviour's default shape is Shapes.block() while its collision shape delegates to that shape. 1.17.1 BlockBehaviour.java 1.18.2 1.17.1 Blocks.java 1.18.2

Set yaw so the world-space requested horizontal movement has blocked X magnitude equal to 0.1 times its unblocked Z magnitude. 1.17.1 one-block-high stone wall face clips the X displacement while Z remains.

Both LivingEntity constructors set maxUpStep to 0.6F (1.17.1 line 239; 1.18.2 line 243), so from the support-floor level this one-block wall cannot be cleared by a step candidate. 1.17.1 reports horizontalCollision for a clipped X component; 1.18.2's horizontalCollision is also true and its classifier compares the rotated input vector with that resolved displacement.

The angle is atan(0.1), about 0.0997 radians, below 1.18.2's 0.13962634F cutoff.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-001-minor-horizontal-collision-sprint.md).
