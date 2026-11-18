# 1.19.2 → 1.19.3: Player swim-pitch vertical writer

## Finding

No source difference in the assigned operation: `Player.travel`'s direct swim-pitch Y-velocity writer, its pitch-to-look-vector math, and the inherited `LivingEntity.travel` water branch are text-identical in exact Mojmap decompilations for 1.19.2 and 1.19.3. This closes only this named slice as a no-difference check; it does not close the release pair.

## Exact source slices

- 1.19.2, Mojmap: `decompiled_minecraft/1.19.2/mojmap/net/minecraft/world/entity/player/Player.java:1464-1476` (direct writer), `net/minecraft/world/entity/Entity.java:1322-1342,1841-1843` (view-vector and look-angle path), `net/minecraft/util/Mth.java:27-50` (float sine LUT and accessors), and `net/minecraft/world/entity/LivingEntity.java:2042-2087` (travel entry and water branch).
- 1.19.3, Mojmap: `decompiled_minecraft/1.19.3/mojmap/net/minecraft/world/entity/player/Player.java:1448-1460`, `net/minecraft/world/entity/Entity.java:1327-1347,1850-1852`, `net/minecraft/util/Mth.java:27-54`, and `net/minecraft/world/entity/LivingEntity.java:2052-2097`.

## Behavior and order

`Player.travel` first snapshots position, then evaluates `isSwimming() && !isPassenger()` in that order. Only when both pass does it sample `getLookAngle().y` once into a `double`; `getLookAngle()` calls `calculateViewVector(getXRot(), getYRot())`, so this path uses current raw float pitch and yaw, not partial-tick interpolation. The pitch conversion is float multiplication by `(float)(Math.PI / 180.0)`. `calculateViewVector` returns Y as `-Mth.sin(pitchRadians)`.

`Mth.sin(float)` indexes a `float[65536]` initialized by `Util.make` before class use. Each entry is `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`, so the computation inside `Math.sin` is double and each stored value is cast to float. Lookup computes `(int)(radians * 10430.378F) & 65535` (float multiply, truncating float-to-int cast, then mask); `Mth.cos` uses the same table plus `16384.0F`. Both releases have the same table width, initialization, casts, scale and lookup.

The sampled Y selects coefficient `0.085` only when `Y < -0.2`, otherwise `0.06`. The writer runs when `Y <= 0.0`, else when `jumping`, else when the block at `(getX(), getY() + 1.0 - 0.1, getZ())` has nonempty fluid. Those are Java short-circuit `||` gates, in that order; the above-block state read is skipped when either earlier condition passes. If the gate passes, it samples current delta movement and adds `(lookY - velocityY) * coefficient` to Y while adding zero to X and Z, then writes the resulting vector. Otherwise this operation makes no velocity write.

The subsequent `super.travel(input)` reaches the inherited `LivingEntity.travel`. Its water branch is separately gated by `isEffectiveAi() || isControlledByLocalInstance()`, then `isInWater() && isAffectedByFluids() && !canStandOnFluid(fluidAtBlockPosition)`. On that branch it computes sprint/water slowdown and depth-strider adjustments, applies Dolphin's Grace slowdown, calls `moveRelative(inputScale, input)`, then `move(SELF, deltaMovement)`, applies the horizontal-collision/climbable Y response, multiplies by water slowdown (`Y * 0.8F`), performs fluid-falling adjustment, and conditionally applies the horizontal-collision free-space Y boost. The checked branch and order are unchanged across the pair. This describes the inherited movement procedure; it does not audit collision/shape dependencies or resulting trajectories.

## Byte identity and source readiness

Both ready markers report status `ready`, the requested exact version, Mojmap, and Vineflower output. Each marker's source-manifest, artifact-manifest, and diagnostics SHA-256 was checked against the corresponding file. Each relied source file's SHA-256 was checked against its source-manifest entry. Provenance identifies one exact 1.19.2/1.19.3/1.19.4 batch using Java 25.0.3, Gradle 9.7.1, Vineflower 1.12.0, TinyRemapper 0.14.1, ASM 9.10.1, Gson 2.14.0, and Mapping IO 0.9.1.

| Release | Source manifest | Artifact manifest | Diagnostics | Player.java | Entity.java | LivingEntity.java | Mth.java |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1.19.2 | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` | `d0197d78578241b8cdde1123a3dee64aa279341681a7edde9f867689f9bb3490` | `ce308a5f2902c3d7f7c5e330935d130e5ce56302d03b7fa687bf14493400e44e` | `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` | `61727897223a78846a5f1fa270d57aa714c21b3c8d6f2b0e12fea4a07e6c327f` |
| 1.19.3 | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652` | `95e8189acb6b384b21a584f28d5e8d23922996ea9f446e17e54734ccba840e60` | `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` | `f5b2738a7145303f8598b9876c0f6950e853110f594fc4cffa633ab7bab98d3f` |

Independent UTF-8 snippet hashes matched across releases for `Player.travel` (`47f346499ffd6fdd8b54c5ceebf7616edcebd5e98bd70ff8e8deb4418419f67a`), `calculateViewVector` (`2d4d420cf6ca63500a4f85dc379ae6fc049061ac289472eef57d83365735c91c`), `getLookAngle` (`cd7eda7fd024034e2c41c1aaa90ba936ac909640065c758c72254854cf39d7c4`), and the inherited water branch (`119f92bf34d3430702b8a7ad994cbdf5b9c123404229611b52c4fee6a0e25c17`).

## Boundary

The 1.19.3 Elytra/downstream travel branch, fluid and collision dependency graph, and movement outside this swim-pitch writer were not evaluated here.