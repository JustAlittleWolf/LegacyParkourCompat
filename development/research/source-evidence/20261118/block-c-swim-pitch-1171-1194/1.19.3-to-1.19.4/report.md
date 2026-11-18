# 1.19.3–1.19.4 swim-pitch vertical adjustment source review

## Scope and result

Bounded source comparison: `Player.travel(Vec3)` swim-pitch direct vertical-velocity adjustment for a nonpassenger player, its pitch-to-look-vector numeric path, its gates/sample order, and the inherited water-movement path reached by `super.travel`. Mojmap exact decompilations only. Effects, fluid-state/block-position semantics, collision behavior, and other changes in `Player.travel` are outside this review.

**Result: no source difference found in the pitch-derived Y value, its numeric conversion, the pitch gates, or the inherited water branch itself.** The 1.19.4 swim gate uses `BlockPos.containing(...)` where 1.19.3 spells the construction `new BlockPos(...)`; that is a changed fluid/block sample expression and is explicitly outside this bounded verdict. Likewise, the inherited travel parent eligibility expression differs (`isEffectiveAi() || isControlledByLocalInstance()` in 1.19.3; `isControlledByLocalInstance()` in 1.19.4); the local-player-controlled branch's water movement body is the same. These are recorded as boundaries, not adjudicated here.

## Source and publication identity

Exact source roots: `decompiled_minecraft/1.19.3/mojmap/` and `decompiled_minecraft/1.19.4/mojmap/`.

- 1.19.3 ready marker: `decompiled_minecraft/1.19.3/mojmap.ready.json`, SHA-256 `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6`; it declares exact version `1.19.3`, mapping `mojmap`, status `ready`, 4,618 source files, source-manifest SHA-256 `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`, and client input SHA-256 `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6`.
- 1.19.4 ready marker: `decompiled_minecraft/1.19.4/mojmap.ready.json`, SHA-256 `59f59ab516d41eb97e09d63256ee26a68c15102052bf7acafdbd5440b72547d0`; it declares exact version `1.19.4`, mapping `mojmap`, status `ready`, 4,740 source files, source-manifest SHA-256 `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`, and client input SHA-256 `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec`.
- Both provenance sidecars identify Vineflower 1.12.0, Java 25.0.3+9-LTS, TinyRemapper 0.14.1, Gradle 9.7.1, and the exact 1.19.2/1.19.3/1.19.4 batch command with `--mappings=mojmap`. Provenance sidecar SHA-256s: 1.19.3 `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe`; 1.19.4 `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34`.
- The listed ready-marker source-manifest hashes were checked against `mojmap.sources.sha256`. All eight relied source files below match their corresponding manifest entry and direct SHA-256.

## Compared source slices and hashes

Line references are within each exact Mojmap source file.

| Source file | 1.19.3 lines; SHA-256 | 1.19.4 lines; SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `1448–1478`; `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203` | `1440–1470`; `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2` |
| `net/minecraft/world/entity/Entity.java` | `calculateViewVector` 1339–1346, `getLookAngle` 1850–1852; `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` | `calculateViewVector` 1345–1352, `getLookAngle` 1867–1869; `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` |
| `net/minecraft/util/Mth.java` | SIN initialization 28–32, `sin`/`cos` 49–54; `f5b2738a7145303f8598b9876c0f6950e853110f594fc4cffa633ab7bab98d3f` | SIN initialization 26–30, `sin`/`cos` 42–47; `201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797` |
| `net/minecraft/world/entity/LivingEntity.java` | `travel` 2052 onward; water branch 2062–2096; `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` | `travel` 1983 onward; water branch 1993–2027; `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` |

The source-manifest files have SHA-256 `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` (1.19.3) and `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` (1.19.4), matching the ready markers.

## Numeric path and swim gates

In both `Player.travel`, the swim adjustment runs first under `isSwimming() && !isPassenger()`. It samples `this.getLookAngle().y` once into a `double`, then chooses `0.085` when that value is `< -0.2`, otherwise `0.06`. The adjustment is applied when the same sampled value is `<= 0.0`, or `jumping` is true, or the subsequent head-adjacent-fluid test succeeds. These short-circuit checks and their order match. When applied, it reads the current delta movement after the gates and writes `delta.y + (lookY - delta.y) * selectedScale`, with X and Z additions zero. It does so before the flying/nonflying superclass travel branch in both releases.

`getLookAngle()` in both versions passes raw `getXRot()` and `getYRot()` floats directly to `calculateViewVector(float,float)`. The Y component is `-Mth.sin(pitchRadians)`, where `pitchRadians` is formed as `pitch * (float)(Math.PI / 180.0)` in float arithmetic. `Mth.sin(float)` returns a float table entry indexed by `(int)(radians * 10430.378F) & 65535`; the cast truncates toward zero before masking. The table is a 65,536-element `float[]` initialized in ascending integer-index order with `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`, keeping the angle expression in double until the result cast. `calculateViewVector` negates the float sine and passes that float to `new Vec3(double,double,double)`, widening the already-rounded float to double exactly. `getLookAngle().y` therefore carries that widened float value into `Player.travel`; the `-0.2` and `0.0` comparisons and the easing expression thereafter use double arithmetic. The table initialization, trig lookup, casts, constants, expression order, and widening match in the paired source.

## Inherited water path

On a locally controlled player, `Player.travel` delegates to `super.travel` after the pitch adjustment. `LivingEntity.travel` enters the water branch under the same conditions in both bodies (`isInWater() && isAffectedByFluids() && !canStandOnFluid(currentFluidState)`). Its ordered swim movement is unchanged: determine slowdown/depth-strider adjustments, `moveRelative`, move by current delta, apply horizontal-climbable Y handling, multiply velocity by `(slowdown, 0.8F, slowdown)`, apply fluid-falling-adjusted movement, then the horizontal-collision upward escape check. No difference in this inherited water branch contributes a change to the pitch-derived Y adjustment.

