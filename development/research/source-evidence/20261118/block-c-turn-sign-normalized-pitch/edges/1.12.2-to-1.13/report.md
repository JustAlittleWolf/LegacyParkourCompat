# Feather 1.12.2 → 1.13: direct pitch precision and next Elytra movement

## Verdict

Yes. `Entity.updateLocalPlayerCamera` can produce a different current `float pitch` from the same sign-normalized physical delta because 1.12.2 narrows the delta to `float`, while 1.13 retains it as `double`. A one-ULP pitch difference can cross a `MathHelper` sine-table bucket and change the next dry Elytra movement. This is input-sensitive, not a claim that every delta changes movement.

The calculation below fixes caller intent before API conversion, applies each release's own `Entity` and `LivingEntity` source, and holds a legal local-flight state constant. It does not infer input-device scheduling or use a game/runtime observation.

## Exact source identity

Canonical IDs are literal `1.12.2` and `1.13`. Both source sets are Feather Gen2 build.2 in `ornithe-feather`, decompiled by Vineflower 1.12.0 on Java 25.0.3. The ready markers identify the exact client artifacts and full-source manifests. The cited Java files match their manifest entries.

| Release | Client SHA-256 | Feather mergedv2 JAR; Tiny SHA-256 | Source-manifest SHA-256 |
|---|---|---|---|
| `1.12.2` | `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f` | `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca`; `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| `1.13` | `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` | `a7a61604c7b365db1f4250c4c0079172c3beff37f4e16f7a53a13d77f3976897`; `7fa6b27aab7245c2d47ecbfe86ad9178272d7b1fd9ba69a482a41659387ffd84` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |

Cited file SHA-256s, in `Entity.java`, `LivingEntity.java`, `MathHelper.java`, `Vec3d.java`, `PlayerEntity.java` order:

- `1.12.2`: `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`; `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`; `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80`; `059dd21505c13b6febbbe880889f3aa6b00f7648ba1b770268eb8841674e90e8`; `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`.
- `1.13`: `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642`; `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864`; `ad7615e7acf9d5ebe1d7cfc236a7b2dc7726c42a32bd46254ba29f9fc68c567f`; `6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5`; `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd`.

## Pitch operation and numeric witness

- Feather `1.12.2`, `net/minecraft/entity/Entity.java:307–318`: `updateLocalPlayerCamera(float yaw, float pitch)` saves the old pitch, computes `(float)(this.pitch - pitch * 0.15)`, clamps to `[-90, 90]`, then advances `lastPitch` by the clamped current-pitch change.
- Feather `1.13`, `net/minecraft/entity/Entity.java:318–329`: `updateLocalPlayerCamera(double cursorDeltaX, double cursorDeltaY)` computes `double d = cursorDeltaY * 0.15`, casts `this.pitch + d` to float, clamps to the same range, then casts `lastPitch + d` to float.

Use the same pre-conversion upward intent `δ = 2.343714356422422` and initial current/previous pitch `0.0F`. The old subtracting API receives the opposite sign narrowed to `-2.3437142372131348F`; the new API receives positive `δ` as a double. Neither result clamps.

| Source arithmetic | Current pitch after clamp | Binary32 bits |
|---|---:|---|
| 1.12.2: `(float)(0 - (-2.3437142372131348F * 0.15))` | `0.35155713558197021` | `3eb3ff4c` |
| 1.13: `d = δ * 0.15`; `(float)(0 + d)` | `0.3515571653842926` | `3eb3ff4d` |

The outputs are adjacent binary32 values, differing by `2.9802322387695312e-8` degrees. The 1.13 double intermediate is `0.3515571534633633`; 1.12.2 has already rounded the physical delta at its float parameter. With both previous/current pitch initially zero, each method leaves `lastPitch` at its own new current pitch. The fixture therefore does not depend on a stale previous orientation.

## Next-tick movement fixture

Fixed legal state: local logical-client player, dry (not in water or lava), not swimming, not creative-flying, not riding, airborne, chest Elytra is flyable, fall-flying flag 7 active, no slow-falling effect, yaw `0`, initial velocity `(0, 0, 0)`, and collision-free air along the move. The movement tick calls `flyingTick()` before `moveRelative(...)`. `flyingTick` preserves flag 7 only when airborne, not riding, and wearing a flyable Elytra; `isFallFlying()` reads flag 7. The local dry player `moveRelative` override falls through to `LivingEntity.moveRelative` in both versions.

The glide branch's local-control gate is satisfied. It reads current `pitch` directly to form `i = pitch * (float)(Math.PI / 180.0)` and calls `getLookVector()`. That method also uses current pitch and yaw; this flight path does not use `lastPitch` interpolation. With no collision, the `move(SELF, velocityX, velocityY, velocityZ)` arguments are the next movement displacement. This isolates prior-orientation interpolation from the result.

For the two pitches, the Elytra branch's `MathHelper.cos(i)` table lookup crosses a bucket:

| Current pitch | Branch angle `i` | Cos index expression | Lookup index/value |
|---:|---:|---:|---:|
| `0.35155713558197021F` | `0.006135829258710146F` | `16447.998046875F` → truncate | `16447` → `0.9999817609786987F` |
| `0.3515571653842926F` | `0.0061358301900327206F` | `16448.0F` → truncate | `16448` → `0.999981164932251F` |

Both `MathHelper` sources use `SINE_TABLE[(int)(x * 10430.378F + 16384.0F) & 65535]`, initializing that table from `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)`. The direct pitch ULP thus changes the float glide factor.

I propagated each release's source expressions separately. Values below are the collision-free next `move` Y argument after Elytra gravity/lift and `0.98F` drag. Each version is held fixed while only current pitch changes, separating pitch sensitivity from the release's distinct travel formula.

| Version-specific `LivingEntity.moveRelative` | Pitch `3eb3ff4c` | Pitch `3eb3ff4d` | Pitch-only change |
|---|---:|---:|---:|
| 1.12.2, lines 1425–1464 | `-0.017642002266163952` | `-0.017642067688743077` | `-6.5422579124951863e-8` |
| 1.13, lines 1459–1504 | `-0.017642002266163950` | `-0.017642067688743070` | `-6.5422579121482416e-8` |

The paired actual source outcomes use the first pitch for 1.12.2 and second pitch for 1.13: a different next vertical displacement, about `6.54e-8` blocks in this fixture. Forward Z displacement also changes from about `0.001782130000630194` to `0.001782134248776732` blocks. These are calculated source consequences, not observed runtime results.

The travel code has an independent edit: 1.12.2 uses `-0.08 + m * 0.06`; 1.13 uses `d * (-1.0 + n * 0.75)` with `d = 0.08` in this no-slow-falling state. The within-version rows hold those formulas fixed; the pitch sensitivity is not attributed to that separate rewrite. `getLookVector` also has version-specific trig expressions. Yaw zero keeps its tiny residual X difference out of the reported Y result.

## Exact source locations

- `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`: camera update lines 307–318; direct `getLookVector` lines 1690–1692.
- `1.13/ornithe-feather/net/minecraft/entity/Entity.java`: camera update lines 318–329; `getLookVector` lines 1690–1692; pitch interpolation accessor lines 1242–1244 (not used by this Elytra branch).
- `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`: local/dry/fall-flying gate and glide lines 1425–1464; Elytra equip gate lines 1854–1872; flag predicate lines 2184–2186; movement call order lines 1842–1848.
- `1.13/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`: local/dry/fall-flying gate and glide lines 1459–1504; Elytra equip gate lines 1919–1938; flag predicate lines 2295–2297; movement call order lines 1900–1908.
- `PlayerEntity.java`: dry, non-riding, non-creative-flight overrides call `super.moveRelative` in 1.12.2 lines 1385–1404 and 1.13 lines 1434–1461; 1.13 swimming handling precedes it but is disabled here. `LocalClientPlayerEntity.java:485–487` returns `true` from `isLocallyControlled()` in both releases. Its file SHA-256 is `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` in 1.12.2 and `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` in 1.13.
- `MathHelper.java`: sine/cosine lookup functions at 1.12.2 lines 15–20 and 1.13 lines 24–29; table initialization at 1.12.2 line 428 and 1.13 line 474.
- `Vec3d.java`: `length()` at 1.12.2 lines 91–93 and 1.13 lines 79–81; both call `MathHelper.sqrt(double)`, which returns float.

## Scope limits

This establishes a possible current-pitch and next-movement difference under a legal fixed state. It does not establish how often ordinary input deltas hit a table boundary, which caller/device scheduling supplies a particular delta on any tick, or the exact patch where the change first appeared between endpoints. No game, TAS, Gym, server, test, build, or generated-source run was used.

