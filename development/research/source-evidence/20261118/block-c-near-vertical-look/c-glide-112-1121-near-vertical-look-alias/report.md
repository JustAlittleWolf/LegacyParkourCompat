# 1.12 → 1.12.1: near-vertical Elytra look path

## Result

**No source difference found for this operation between 1.12 and 1.12.1.** For an ordinary dry, already-active, locally controlled player in Elytra flight, the negative-pitch look-vector correction and its near-vertical denominator behavior are unchanged across this adjacent pair. This is source comparison only; it does not establish runtime parity or a specific trajectory.

## Exact source identity and integrity

Both inputs are the local exact-version `ornithe-feather` decompilations. Each mapping directory contains `.decompile-provenance.json`; the provenance entries for the client, mapping metadata, Feather artifact, and every source file checked below were verified against the local bytes.

| Release | Client input SHA-256 | Feather artifact SHA-256 | Feather metadata SHA-256 | Provenance file SHA-256 |
|---|---|---|---|---|
| 1.12 | `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca` | `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719` (`1.12+build.2-mergedv2.jar`) | `5b4555cb0473f14b4bde13b7f60f7f979dc4f8e149187f5696ae8283e437fac4` | `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` |
| 1.12.1 | `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22` | `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7` (`1.12.1+build.2-mergedv2.jar`) | `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b` | `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8` |

Both provenance records name the same `lpc-decompile-v1` pipeline and Vineflower 1.12.0 (tool artifact SHA-256 `1dfcfe974395734fa467ce620661c7623d05ba83670de0529b1fbd63ff548b9d`). They pin the exact client and Feather inputs shown above. No standalone ready marker is present at either version root; the per-tree provenance is the available identity record.

SHA-256 of the checked source files matches the `sources` entries in each local provenance record:

| Source file | 1.12 | 1.12.1 | Pair result |
|---|---|---|---|
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | same | byte-identical |
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | same | byte-identical |
| `net/minecraft/util/math/MathHelper.java` | `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80` | same | byte-identical |
| `net/minecraft/util/math/Vec3d.java` | `059dd21505c13b6febbbe880889f3aa6b00f7648ba1b770268eb8841674e90e8` | same | byte-identical |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `6a9d5f420b13625195b149c0d9468cf1284d5eb7dc1b2a46cf01b952a27ee063` | `4dab93ea3178a28c616947004ecdc514e6542edca8c62ef83f686ccf6b2ce640` | file differs elsewhere; `moveRelative` body is text-identical |

## Compared path and behavior

- `Entity.java`, `getLookVector()` (1690–1692) delegates with the current `pitch` and `yaw` to `getRotationVector(float,float)` (1239–1244). That method forms the vector from float `MathHelper.cos`/`sin` results and returns `(g*h, i, f*h)` directly; this path does not normalize it.
- `MathHelper.java`, `sin(float)`/`cos(float)` (15–21), uses `SINE_TABLE[(int)(x * 10430.378F) & 65535]` and the corresponding cosine offset. Thus nearby camera angles that resolve to the same integer lookup index alias to the same trig outputs. `Vec3d.length()` (91–93) computes the square root of the three raw squared components; its separate `normalize()` helper is not called here.
- `LivingEntity.java`, `moveRelative` (1425 onward), enters only under `(isLocallyControlled() || isLogicalSideForUpdatingMovement())`; its water and lava guards also admit a flying `PlayerEntity`. In the assigned dry path, the active `isFallFlying()` branch uses `getLookVector()`, sets float `i = pitch * (float)(PI / 180.0)`, computes horizontal look magnitude `j = sqrt(vec3d.x² + vec3d.z²)`, horizontal speed `k`, and full raw look length `l`. These statements and their enclosing gates are identical in both releases.
- In the glide math, the descending correction is gated by `velocityY < 0 && j > 0`; the separate pitch-up correction is gated only by `i < 0`. That branch computes `o = k * -sin(i) * 0.04`, changes vertical velocity by `o * 3.2`, and adjusts horizontal velocity with `vec3d.x * o / j` and `vec3d.z * o / j`. The later horizontal steering is again gated by `j > 0`, followed by drag and `move(SELF, ...)` (1425–1464). A small positive `j` near vertical therefore remains the divisor for the pitch-up correction; no new guard, normalization, or ordering change appears in 1.12.1.
- Before movement, `LivingEntity` calls `flyingTick()` then `moveRelative(...)` (1846–1847). `PlayerEntity.moveRelative` (1386–1404) invokes `super.moveRelative` in the ordinary non-creative-flight branch. I compared this method body directly across the pair: identical text despite the unrelated `PlayerEntity.java` file-hash change.

The shared `LivingEntity.java`, `Entity.java`, `MathHelper.java`, and `Vec3d.java` file hashes establish identical full source files for the central math and gates. The only changed file among these inspected call-path sources is `PlayerEntity.java`; its relevant override is identical. No 1.12.1 change to the assigned near-vertical look alias or correction denominator is present.

## Boundary

This conclusion is limited to finite ordinary camera rotations, the already-active Elytra state, dry movement, and a locally controlled player. Elytra activation/eligibility, malformed state or inputs, server movement, unrelated player code, final trajectory effects, and runtime A/B parity were not assessed.

## Exact references

All refs are under `decompiled_minecraft/<version>/ornithe-feather/`:

- `net/minecraft/entity/Entity.java`: `getRotationVector(float,float)` 1239–1244; `getLookVector()` 1690–1692.
- `net/minecraft/entity/living/LivingEntity.java`: `moveRelative(...)` 1425–1464; `flyingTick()` then `moveRelative(...)` 1846–1847.
- `net/minecraft/entity/living/player/PlayerEntity.java`: `moveRelative(...)` 1386–1404.
- `net/minecraft/util/math/MathHelper.java`: `sin(float)` and `cos(float)` 15–21.
- `net/minecraft/util/math/Vec3d.java`: constructor 11–27; `length()` 91–93.

