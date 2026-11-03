# Negative-boundary `containsLiquid` witness

This bounded packet evaluates only the player call path needed to establish applicability of the `World.containsLiquid(Box)` scan difference. It does not assess the movement solver or fluid velocity production.

## Exact inputs

- Older source: Minecraft 1.7.10, Ornithe Feather `1.7.10+build.2`, `decompiled_minecraft/1.7.10/ornithe-feather/`.
- Newer source: Minecraft 1.8.9, Ornithe Feather `1.8.9+build.2`, `decompiled_minecraft/1.8.9/ornithe-feather/`.
- The 1.7.10 provenance source map lists 1,803 files; all listed file hashes matched. The 1.8.9 ready marker lists 1,612 files, and its source-manifest digest matches the ready marker. Relevant-source hashes were checked on both sides.

## Player call path

In 1.7.10, `PlayerEntity` extends `LivingEntity` (`entity/living/player/PlayerEntity.java:75`). Its `tick` calls `super.tick()` (`PlayerEntity.java:216`); `LivingEntity.tick` first calls `super.tick()` and later calls `moveRelative(this.sidewaysSpeed, this.forwardSpeed)` (`entity/living/LivingEntity.java:1225, 1420`). `Entity.baseTick` calls `checkWaterCollisions` (`entity/Entity.java:315`), which sets `inWater` from `world.applyLiquidDrag` (`Entity.java:741–779`). For a non-flying player in water, `LivingEntity.moveRelative` reaches the water branch (`LivingEntity.java:1095–1097, 1177–1187`); following a horizontal collision it calls `canMove` (`LivingEntity.java:1185`). `Entity.canMove` tests the moved box with `getCollisions` and then `!world.containsLiquid` (`entity/Entity.java:377–380`).

In 1.8.9, the same path is reachable for the local player: `PlayerEntity extends LivingEntity` (`entity/living/player/PlayerEntity.java:79`), `PlayerEntity.tick` calls `super.tick()` (`PlayerEntity.java:228`), and `LivingEntity.tick` calls `super.tick()` before travel and later invokes `moveRelative` (`entity/living/LivingEntity.java:1260, 1450`). `LocalClientPlayerEntity.isLocallyControlled()` returns true (`client/entity/living/player/LocalClientPlayerEntity.java:381–382`). `Entity.baseTick` refreshes water state (`entity/Entity.java:300, 746–756`); the non-flying water branch and horizontal-collision `canMove` call are at `LivingEntity.java:1120–1123, 1199–1226`. `Entity.canMove` delegates to `doesNotCollide`, whose final predicate reads `World.containsLiquid` (`entity/Entity.java:362–368`).

## Shared body and probe arithmetic

Both players use width `0.6F` and height `1.8F` (`1.7.10 entity/living/player/PlayerEntity.java:398`; `1.8.9 .../PlayerEntity.java:417`). Float division gives half-width `0.3F = 0.30000001192092896`. With horizontal position X `-0.4F` and Z `0.7F`, the AABB bounds are approximately:

```text
X: [-0.700000017881393, -0.0999999940395355]
Z: [ 0.399999976158142,  1.0]
```

Set the no-sneak body bottom to Y `10.5`. In 1.7.10, `Entity.setPosition` derives `shape.minY = y - eyeHeight + eyeHeightSneakOffset` (`entity/Entity.java:215–222`); `PlayerEntity.eyeHeight` is `1.62F` (`PlayerEntity.java:118`). Therefore pass entity Y `10.5 + (double)1.62F = 12.120000004768372`, with zero sneak offset, to obtain the same AABB as 1.8.9, where `Entity.setPosition` uses Y directly for the box bottom (`entity/Entity.java:226–232`). The shared body box is approximately `[10.5, 12.2999999523163]` vertically.

Precondition the water-branch movement with `velocityY = 0`, no vertical displacement in the blocked move, and a positive Z move clipped by the obstacle. After the water branch applies `velocityY *= 0.8F; velocityY -= 0.02`, its vertical probe delta is, in both releases:

```text
0.0 * 0.8 - 0.02 + (double)0.6F - currentY + startingY
= -0.02 + 0.6000000238418579
= 0.5800000238418579
```

Here `currentY == startingY`; in 1.7.10 both are the eye-position field, and in 1.8.9 both are the feet-position field. The identical cancellation leaves the same delta. The probe box is approximately:

```text
X: [-0.700000017881393, -0.0999999940395355]
Y: [11.0800000238419, 12.8799999761581]
Z: [ 0.399999976158142,  1.0]
```

The lower Y bound is strictly above 11, so water at Y=10 that caused the pre-travel `inWater` state is outside the probe.

## Isolated liquid cell and scan result

At the probe bounds, 1.7.10 `World.containsLiquid(Box)` (`world/World.java:1517–1540`) starts X at `floor(-0.7000000179) = -1`, then decrements it because `minX < 0`; its exclusive upper bound is `floor(-0.0999999940 + 1) = 0`. It scans X cells `-2, -1`. Its Y cells are `11, 12` (`floor(minY)` through `floor(maxY + 1)` exclusive), and Z cells are `0, 1`.

In 1.8.9, `World.containsLiquid(Box)` (`world/World.java:1438–1459`) scans X `floor(minX)..floor(maxX)`, inclusive: only cell `-1`. It scans the same Y cells `11, 12` and Z cells `0, 1`.

Use an otherwise dry, loaded instantaneous world snapshot with these blocks:

- Water at `(-1, 10, 0)`, to make the player `inWater` before its travel call.
- Lava at `(-2, 11, 0)`, the single extra cell in the old provider's probe scan. Use lava specifically: it is liquid for `containsLiquid` but does not add water to the player's pre-travel water state or alter the water-flow setup.
- No liquid in X `-1`, Y `11..12`, Z `0..1`.
- A closed top-half trapdoor at `(-1, 10, 1)`, with the player initially touching its front face at Z=1 and moving toward +Z.

Keep the snapshot fixed through the probe: no fluid or block ticks run between setting these states and evaluating the branch. The water block is inside the starting body box and its ordinary water collision check region. The lava block at X=-2 is outside the player's current/probe AABB; the regular liquid-drag scan does not apply `containsLiquid`'s negative-minimum decrement. It therefore supplies no `inWater` state and is not examined for drag. A horizontal move into the trapdoor is blocked; the probe raises the player by `0.58000002384`. Both trapdoor providers yield the top-half slab Y `[10.8125, 11]` (1.7.10 `block/TrapdoorBlock.java:48, 64–68`; 1.8.9 `TrapdoorBlock.java:58, 74–78`), so the raised probe clears it. The trapdoor does not intersect the probe's block-collision query.

Result: at this stipulated snapshot, 1.7.10 `containsLiquid` sees `(-2,11,0)` and `canMove` returns false; 1.8.9 omits that cell, sees no liquid in the probe, and `canMove` returns true. The source call paths establish that the water branch and post-horizontal-collision `canMove` predicate are reachable in both releases when their stated local conditions hold. The block/player snapshot, zero vertical displacement, and blocked move are stipulated inputs here; this packet does not establish a continuous ordinary player trajectory that produces the entire setup.

## Bounded status

This establishes a conditional source-level probe difference for a specified local player/world snapshot. It does not establish runtime reproduction, continuous ordinary trajectory reachability for the full snapshot, the exact first patch that introduced or removed the scan correction, or any movement-loop implementation decision. Probe logic, external fluid velocity and world-border behavior are outside this packet.
