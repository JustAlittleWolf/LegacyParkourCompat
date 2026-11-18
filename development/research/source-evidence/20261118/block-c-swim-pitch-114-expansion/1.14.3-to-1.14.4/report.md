# 1.14.3 → 1.14.4 swim pitch vertical velocity source trace

## Scope and result

Slice: the ordinary player `travel` prefix that uses the current look-vector Y component to add pitch-directed vertical velocity while swimming, for the active, nonpassenger local player. Compared the canonical 1.14.3 Yarn tree with the exact published 1.14.4 Mojmap tree. **No behavioral difference is present in this slice.** The mapping-facing names differ; the pitch conversion, double-valued Y component, threshold, fluid sample, gate, and velocity update are equivalent.

## Exact evidence identity

- 1.14.3 source: `decompiled_minecraft/1.14.3/yarn/` (`.yarn.staging-*` was excluded). Its `.decompile-provenance.json` is SHA-256 `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`; it records the 1.14.3 client input at `.../af100b34ec7ef2b8b9cf7775b544d21d690dddec/client.jar` with SHA-256 `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07`, Yarn 1.14.3+build.13 at `https://maven.fabricmc.net/net/fabricmc/yarn/1.14.3+build.13/yarn-1.14.3+build.13-v2.jar` with SHA-256 `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`, and Intermediary 1.14.3 with SHA-256 `f2e9990c00fa87ddc7158a95afb735a947ae26d1997b52916226e11c612542a9`. The local `client.jar.pin.json` binds the exact client URL/hash. Source hashes are listed below.
- 1.14.4 source: `decompiled_minecraft/1.14.4/mojmap/`, bound to `mojmap.provenance.json` SHA-256 `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97`, `mojmap.ready.json`, `mojmap.verification.json`, and `mojmap.sources.sha256` SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`. The ready record binds exact 1.14.4 client SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`; verification reports all 3,250 source files and raw inputs identical. The exact Feather inputs recorded alongside Mojmap are `feather-gen2-1.14.4+build.2-mergedv2.jar` SHA-256 `3162806b9fb266d7e6d2c594be8d4cc6c91ddb09b4e55d1b566e5429edbd793f` and `.tiny` SHA-256 `60d4906621c873dadba96425d1a233349c9afa407d09bfc8371202af6a71f25c`. The source mapping used for this comparison is the verified published Mojmap source tree.
- Compared source-file SHA-256 values:

| Release / mapping | Source | SHA-256 |
|---|---|---|
| 1.14.3 Yarn | `net/minecraft/entity/player/PlayerEntity.java` | `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963` |
| 1.14.3 Yarn | `net/minecraft/entity/Entity.java` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| 1.14.3 Yarn | `net/minecraft/entity/LivingEntity.java` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/player/Player.java` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` |

## Operation trace

- 1.14.3 `net/minecraft/entity/player/PlayerEntity.java:1363–1375`: under `isSwimming() && !hasVehicle()`, obtain `double g = getRotationVector().y`; choose `double h = g < -0.2 ? 0.085 : 0.06`; update only when `g <= 0.0 || jumping || !world.getBlockState(new BlockPos(x, y + 1.0 - 0.1, z)).getFluidState().isEmpty()`; add `(g - velocity.y) * h` to Y velocity.
- 1.14.4 `net/minecraft/world/entity/player/Player.java:1379–1391` is the same sequence and constants: `getLookAngle().y`, the same strict `< -0.2` comparison, same double multipliers, same `jumping` disjunct and exact `BlockPos(x, y + 1.0 - 0.1, z)` fluid-state-empty test, and same Y-only add. The method then reaches `super.travel(...)` in either normal or flying branch. The ordinary slice is the nonflying path; the extra flying handling is outside this comparison.
- The look-vector Y input is computed equivalently. Yarn `Entity.java:1226–1234,1677–1679` converts the float pitch using `float h = pitch * (float)(Math.PI / 180.0)`, computes float sine/cosine, and returns `-sin(h)` as `Vec3d.y` (double). Mojmap `Entity.java:1251–1259,1697–1699` performs the same float operations in `calculateViewVector(xRot,yRot)` and returns `-sin(h)` as `Vec3.y` (double). There is no cast of the resulting Y component before the strict `-0.2` threshold or velocity arithmetic; the threshold and coefficients are double literals.
- The extra block lookup is a fluid gate only: it samples the block at the player's X/Z and Y expression `y + 1.0 - 0.1`, then checks whether that state's `FluidState` is empty. It does not query a collision shape or collision predicate. It only suppresses this pitch-driven vertical correction when looking upward (`g > 0`), not jumping, and the sampled block has empty fluid state.
- Actual movement remains inherited. In 1.14.3 `LivingEntity.travel` (`LivingEntity.java:1779ff`, water branch around `1870–1919`) and 1.14.4 `LivingEntity.travel` (`LivingEntity.java:1782ff`, water branch `1878–1922`) consume the updated velocity and call `move(SELF, velocity)`; the water branch's fluid-state selection is separate from Player's one-block-above fluid gate. That call enters `Entity.move`: 1.14.3 `Entity.java:441–467` delegates to `handleCollisions` before offsetting the box, while 1.14.4 `Entity.java:446–472` delegates to `collide` before offsetting the box. These are the underlying collision/movement domains, not part of the pitch correction's fluid sample. Their broader collision implementation is not audited here.

## Bounded coverage

Compared the player swim-pitch adjustment, both current look-vector derivations, and the inherited water-travel dispatch to the real entity movement call. This establishes no patch difference for this operation in the 1.14.3→1.14.4 endpoint pair. It does not claim a wider player-tick audit or verify intermediate releases.
