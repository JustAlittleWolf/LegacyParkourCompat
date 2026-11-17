# Keyboard sneak/use input scaling source comparison — revision 2

**Terminal: COMPLETED** (bounded corrected source comparison). The original `comparison.md` remains unchanged at SHA-256 `cdcee5107bacf3bf61007a38617d597d9ee2a286fcbc86438a853311744dde8e`. This revision independently checked the correction against the exact local `decompiled_minecraft/` sources and nested provenance. The audit verdict used as a correction lead was hash-verified: `early-input-scaling-audit/verdict.md`, SHA-256 `aae5ede695af5f824f769eb777a78c0f19e161a448dfd20c184de514e71883bd`.

No code, runtime, tests, source generation, product/wiki/Git/backlog inspection, or fluid-state producer investigation was performed. Scope is keyboard digital input scaling, its caller-selected slow flag, and following use-item input scaling. Sprint threshold paths and travel relative acceleration normalization are excluded.

## Source identities

Canonical local sources and their nested `.decompile-provenance.json` manifests identify the Yarn mapping builds as 1.14+build.21, 1.14.1+build.10, 1.14.2+build.7, and 1.14.3+build.13. 1.13.2 is Ornithe Feather build.2; 1.14.4 is Mojmap. The relevant source hashes match their manifests/sidecars. Earlier revision 1 incorrectly said the 1.14–1.14.3 Yarn trees lacked provenance: each has a nested Yarn manifest. There is no 1.14.4 Yarn tree; this comparison uses its available Mojmap source.

## Input construction, scalar and order

In 1.13.2 Feather, `KeyboardInput.tick()` reads direction keys into movement floats by increment/decrement and stores the directional booleans. It reads jump and sneak, then multiplies both movement floats by `(float)(value * 0.3)` only when the sneak key is down (`client/entity/living/player/KeyboardInput.java:13-49`, factor at 45-48). The `0.3` literal is a `double`, and the result is explicitly cast to `float`.

`LocalClientPlayerEntity.tickMovement()` calls `input.tick()`, invokes the tutorial input callback, then, if `isUsingItem() && !isRiding()`, multiplies both movement floats by `0.2F` (`client/entity/living/player/LocalClientPlayerEntity.java:702-707`). When both sneak and use gates are true, the 0.3 factor precedes the 0.2F factor.

In each checked 1.14 Yarn and 1.14.4 Mojmap `KeyboardInput.tick(boolean slowFlag, boolean spectator)` implementation, opposing direction keys cancel; each impulse is otherwise `0.0F`, `1`, or `-1`. After reading jump and sneak, the same `(float)(impulse * 0.3)` factor applies under `!spectator && (sneakKey || slowFlag)` (`client/input/KeyboardInput.java:13-26` in Yarn; Mojmap `client/player/KeyboardInput.java:13-26`). This keeps spectator exclusion and scales each axis independently.

Across the checked 1.14 client callers, `input.tick(slowFlag, isSpectator())` and the tutorial input callback precede use scaling. Yarn uses `isUsingItem() && !hasVehicle()`; Mojmap uses `isUsingItem() && !isPassenger()`. Both multiply both input floats by `0.2F`. Thus keyboard scaling precedes use scaling when both gates are active.

## Slow-flag selection and release changes

- **1.14 and 1.14.1 Yarn:** `slowFlag = isInSneakingPose() || shouldLeaveSwimmingPose()` (`ClientPlayerEntity.java:618-619`). In both versions, `isInSneakingPose()` requires `!abilities.flying` and that the sneaking pose would not collide, then returns `isHoldingSneakKey() || !wouldPoseNotCollide(STANDING)` (`ClientPlayerEntity.java:582-585`).
- **1.14.2 Yarn:** The caller expression is unchanged (`ClientPlayerEntity.java:617-618`), but `isInSneakingPose()` adds `!isSwimming()` to its leading conjunction (`ClientPlayerEntity.java:582-585`). This filters the sneak-pose disjunct while the swimming flag is true. The `shouldLeaveSwimmingPose()` disjunct remains present, so the overall slow flag is not simply disabled in swimming states.
- **1.14.3 Yarn:** The added `!isSwimming()` gate remains. Pose constants are named `SNEAKING` and `STANDING` (`ClientPlayerEntity.java:582-585`); the caller remains the same (`:617-618`). Therefore a source change in the pose-derived input gate is first present at 1.14.2 among these adjacent checked releases; no exact earlier/later unexamined patch is inferred.
- **1.14.4 Mojmap:** `LocalPlayer.isVisuallySneaking()` has the same boolean structure as 1.14.3 `isInSneakingPose()`: `!flying && !isSwimming() && canEnterPose(SNEAKING)`, then `isTryingToSneak() || !canEnterPose(STANDING)` (`LocalPlayer.java:593-597`). The caller uses `isVisuallySneaking() || isVisuallyCrawling()` (`LocalPlayer.java:628-629`), so this helper does not show a new sneak-pose gate relative to 1.14.3.

The swimming-exit disjunct has an unresolved predicate dependency. In 1.14.3 Yarn, `shouldLeaveSwimmingPose()` tests swimming pose and `!isInsideWater()` (`entity/Entity.java:1814-1816`), and `isInsideWater()` returns `insideWater` (`:905-906`). In 1.14.4 Mojmap, `isVisuallyCrawling()` tests swimming pose and `!isInWater()` (`world/entity/Entity.java:1836-1841`), while `isInWater()` returns `wasInWater` (`:929-930`). These are visibly different state accessors; this bounded review does not inspect their producers or claim equivalence. A separate source dependency comparison is needed to settle whether that disjunct has a behavioral cutover.

## Release-cutover summary

- **1.13.2 → 1.14:** input slowdown moves into a parameterized keyboard tick. It retains sneak-key scaling and adds caller-selected pose/swim slow input, with an explicit spectator exclusion. Use scaling remains afterward.
- **1.14 → 1.14.1:** no difference found in the selected caller gate, keyboard factor, or use factor.
- **1.14.1 → 1.14.2:** `isInSneakingPose()` adds `!isSwimming()`. The slow-flag caller and keyboard/use scaling order do not change.
- **1.14.2 → 1.14.3:** no difference found in the selected gate apart from named pose constants.
- **1.14.3 → 1.14.4:** the sneak-pose helper is structurally equivalent under Mojmap names; the swimming-exit branch switches from `isInsideWater()` to `isInWater()`, whose direct state access differs. Producer equivalence and any behavioral cutover remain open.

## Key source SHA-256

| Release / mapping | Relative source file | SHA-256 |
|---|---|---|
| 1.13.2 Feather | `client/entity/living/player/KeyboardInput.java` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |
| 1.13.2 Feather | `client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.14 Yarn | `client/input/KeyboardInput.java` | `ade8c1b46b4b25888d326a963f645dd4fe82f53ef0797c2e499fe37e9bb47aa0` |
| 1.14 Yarn | `client/network/ClientPlayerEntity.java` | `92d0f97a9293f7bd57d49af00b70f13d78fc8b2202e7ed67edcfed917e0071be` |
| 1.14.1 Yarn | `client/network/ClientPlayerEntity.java` | `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933` |
| 1.14.2 Yarn | `client/network/ClientPlayerEntity.java` | `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947` |
| 1.14.3 Yarn | `client/network/ClientPlayerEntity.java` | `28b6b4031e02af4d2e6e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae` |
| 1.14.3 Yarn | `entity/Entity.java` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| 1.14.4 Mojmap | `client/player/KeyboardInput.java` | `33daa0833a95e09e728c1b8f509020dd13b70e38aba922e2b1e10ec5c9b7739a` |
| 1.14.4 Mojmap | `client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |
| 1.14.4 Mojmap | `world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |

## Limits

This is not full-pair coverage or runtime parity. Sprint gates, fluid-state producers, and the equivalence of 1.14.3 `insideWater` versus 1.14.4 `wasInWater` remain outside this bounded comparison. No implementation recommendation is made.