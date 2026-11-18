# 1.21.4 to 1.21.5 Player swim pitch-to-Y adjustment

## Result

No source difference was found in the operation owned by this assignment: the nonpassenger `Player.travel` swim pitch-to-Y adjustment, its gates, the `Entity` pitch/look-vector path, or `Mth.sin`'s lookup-table behavior. The direct adjustment is reachable when `Player.travel` runs with `isPassenger() == false` and `isSwimming() == true`. It executes before the inherited `LivingEntity.travel` call.

## Exact source evidence

All paths below are relative to `decompiled_minecraft/<version>/mojmap/` and refer to the exact Mojmap decompilations.

- `1.21.4`: `net/minecraft/world/entity/player/Player.java:1448-1469`; `net/minecraft/world/entity/Entity.java:1640-1647,2220-2222,3509-3518`; `net/minecraft/util/Mth.java:28-50`; inherited route `net/minecraft/world/entity/LivingEntity.java:2189-2199`.
- `1.21.5`: `net/minecraft/world/entity/player/Player.java:1426-1447`; `net/minecraft/world/entity/Entity.java:1669-1676,2188-2190,3509-3518`; `net/minecraft/util/Mth.java:28-50`; inherited route `net/minecraft/world/entity/LivingEntity.java:2213-2222`.

The paired `Player.travel` bodies have the same operations and conditions. In each release, passenger calls go straight to `super.travel`. Otherwise swimming first reads `double lookY = getLookAngle().y`, chooses a double coefficient of `0.085` when `lookY < -0.2` and `0.06` otherwise, then applies the adjustment only when `lookY <= 0.0`, `jumping`, or the sampled fluid state is nonempty. The sample is `BlockPos.containing(getX(), getY() + 1.0 - 0.1, getZ())`. If allowed, it adds `(lookY - deltaMovement.y) * coefficient` to Y using `Vec3.add(0.0, ..., 0.0)`, then sets that result as delta movement. An upward look with no jump skips the write only when that above-position fluid sample is empty.

`Entity.xRot` is the private `float` pitch field. `getXRot()` returns it directly. `setXRot(float)` rejects non-finite input; otherwise it stores `Math.clamp(input % 360.0F, -90.0F, 90.0F)`. Both releases use this same accessor/writer logic. `getLookAngle()` uses the current `getXRot()` and `getYRot()` (not an interpolated rotation) and passes them to `calculateViewVector`.

Both `calculateViewVector` implementations first multiply pitch and negative yaw by `(float)(Math.PI / 180.0)`, keeping those intermediates as `float`; then they call `Mth.cos`/`Mth.sin`, and construct Y as `-sin(pitchRadians)`. Thus the swim value is the look vector's double Y component derived from the stored float pitch through the float trigonometric path.

Both `Mth.java` files are byte-identical by SHA-256. `SIN` is a `float[65536]` initialized by casting each `Math.sin(i * Math.PI * 2.0 / 65536.0)` result to float. `sin(float)` multiplies by float scale `10430.378F`, casts the float product to `int` (Java truncation toward zero), masks with integer `65535`, and returns the selected float entry. `cos(float)` uses the same table/index width and adds float `16384.0F` before the cast. No trigonometric precision or index change explains a pair delta.

## Inherited movement reach

The direct swim adjustment runs before `super.travel` and is not guarded by `isControlledByLocalInstance()` in `Player.travel` in either source. `Player`'s parent implementation is `LivingEntity.travel`. In 1.21.4, that method wraps its fluid/fall-flying/air dispatch in `isControlledByLocalInstance()`. In 1.21.5, the wrapper is absent; the fluid-state lookup and same dispatch run without that gate. When the older gate passes, water swimming reaches `travelInFluid` only when the entity is in water or lava, is affected by fluids, and cannot stand on that fluid; otherwise it follows fall-flying or air movement. This is an adjacent inherited-route difference, not a change to the owned direct pitch-driven Y adjustment. For a locally controlled active player, the older gate passes, so the two versions reach the same parent branch for the same state.

## Readiness and immutable source identity

The canonical version directories both have `mojmap.ready.json` with status `ready`, matching exact version IDs and mapping `mojmap`. Each ready marker's `sourceManifestSha256` matches the SHA-256 of the adjacent `mojmap.sources.sha256`. The four cited source files' computed hashes match their entries in those manifests.

| Version | Client SHA-256 | Source manifest SHA-256 | Player.java SHA-256 | Entity.java SHA-256 | LivingEntity.java SHA-256 | Mth.java SHA-256 |
|---|---|---|---|---|---|---|
| 1.21.4 | `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e` | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1` | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` | `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` | `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da` |
| 1.21.5 | `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11` | `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` | `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036` | `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4` | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` | `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da` |

The ready markers report 5,744 and 5,921 source files respectively and diagnostics review value 22. The provenance manifests identify versions 1.21.4 and 1.21.5, namespace `mojmap`, Vineflower 1.12.0, Java 25.0.3+9-LTS, Gradle 9.7.1, Mapping IO 0.9.1, Tiny Remapper 0.14.1, ASM 9.10.1, and Gson 2.14.0. They record the same exact batch decompilation command for `1.21.1,1.21.3,1.21.4,1.21.5` with Mojmap and a 4G heap, and identify the exact outputs as ready. No source generation was performed for this comparison.
