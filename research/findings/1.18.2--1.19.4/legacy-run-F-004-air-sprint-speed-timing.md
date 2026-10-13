# F-004: Air sprint speed follows the current sprint flag in 1.19.4

- **1.18.2**, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/player/Player.java`; lines 508–513.
- **1.19.4**, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/player/Player.java`; lines 2112–2117.

## Difference

In 1.18.2, inherited travel runs inside `Player.aiStep()` before that method updates `flyingSpeed`.

If the sprint flag changed earlier in the same local-player tick, the current airborne travel still reads the value stored after the preceding tick's travel; the new value is stored only after the current travel returns.

In 1.19.4, the air travel accessor calls `Player.getFlyingSpeed()` and reads the current sprint flag directly.

Thus a sprint start or stop while already airborne affects the 1.19.4 air-speed lookup in that travel call, while 1.18.2 uses its previous cached sprint state for one such call.

This finding concerns lookup timing; the one-ULP stable-state value difference is separate in F-003.

## Movement meaning

Local client path: `LocalPlayer.aiStep()` sprint-state transition -> `Player.aiStep()` -> `LivingEntity.aiStep()` -> `travel(Vec3)` -> `handleRelativeFrictionAndCalculateMovement()` -> air-speed lookup -> `moveRelative()`.

Preconditions exclude grounded movement, fluids, fall-flying and creative flight. 1.18.2 sprint transition can come from the local sprint key or automatic sprint rules; the individual gates remain under stage 1 coverage.

1.19.4 applies the current sprint value to the airborne input acceleration one movement call sooner on a transition.

The direction and value are source-proven; no position trajectory was measured.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.18.2--1.19.4/findings/F-004-air-sprint-speed-timing.md).
