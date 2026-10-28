# 26.2 primary collision step-up gate

## Conclusion

The collector call at the start of `Entity.collide` cannot generally know whether it will be followed by the alternate step-up collection. The branch decision depends on the result of clipping the requested movement against the first collected collider list. A sound pre-collector admission check must therefore be conservative.

The source proves that step-up cannot follow when **any** of these observable conditions holds at the primary collector call:

1. `!(source.maxUpStep() > 0.0F)` (the exact negation of the step branch's positive-height test);
2. the current entity is not on ground and the vertical movement component is nonnegative; or
3. both horizontal movement components are exactly zero.

For condition 2, the later predicate `onGroundAfterCollision` is `yCollision && movement.y < 0.0`; nonnegative Y makes it false, and the current on-ground term is false. For condition 3, the later gate requires `xCollision || zCollision`; clipping a zero component leaves it zero, so neither collision flag can be true. If none of these conditions is established before collection, preserve the native collector result. In particular, grounded or downward-moving player requests with nonzero horizontal movement may trigger a second query depending on the first collision result.

This closes only whether a second collection is possible. It does not establish other collector gates, source-query equivalence, step-up behavior parity, or runtime reachability.

## Source trace

In 26.2 `net.minecraft.world.entity.Entity.collide(Vec3)` (`Entity.java:1141-1168`):

- It first obtains `aabb` and entity collision shapes, then calls `collideBoundingBox(...)` for nonzero movement. That method collects the first collider list and clips movement (`Entity.java:1195-1200,1244-1259`).
- Only after this result does the method compute `xCollision`, `yCollision`, and `zCollision` by comparing the input components with `movementStep` (`Entity.java:1148`).
- It computes `onGroundAfterCollision = yCollision && movement.y < 0.0` (`Entity.java:1149`).
- The alternate collection is reached only if `maxUpStep() > 0.0F`, `(onGroundAfterCollision || this.onGround())`, and `(xCollision || zCollision)` are all true (`Entity.java:1149-1156`). It then constructs a raised `stepUpAABB` and calls `collectCollidersIgnoringWorldBorder` again.

`Entity.maxUpStep()` defaults to `0.0F` (`Entity.java:3922-3924`), but `LivingEntity.maxUpStep()` overrides it with the `Attributes.STEP_HEIGHT` value (`LivingEntity.java:3985-3988`), or at least `1.0F` when controlling a player (`LivingEntity.java:3988`). Therefore the base `Entity` default is not a valid general player gate; inspect the actual receiver's override result.

## Admission rule

At the first block collector call, a caller can safely permit a single-primary-query adapter only when it can establish at least one of:

```text
!(source.maxUpStep() > 0)
OR (!source.onGround() AND movement.y >= 0)
OR (movement.x == 0 AND movement.z == 0)
```

These are sufficient conditions, not a complete characterization of all movements that happen not to step up. When none applies, the first clipping outcome is not yet known, so do not speculate about whether step-up will occur. This proof is specific to the shown 26.2 `Entity.collide` branch and should be rechecked if its control flow changes.

## Exact source identity

Canonical read-only source is `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated`. The exact source files and SHA-256 digests are recorded in `source-hashes.sha256`.
