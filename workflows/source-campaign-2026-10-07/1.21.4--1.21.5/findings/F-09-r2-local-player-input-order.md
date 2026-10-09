# F-09-r2: Controlled local-player input multiplication order

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S1-INPUT-SCALE, S1-LOCAL-TICK
- Classification: candidate changed behavior (single-precision rounding)
- Confidence: source-confirmed operation order; bounded witness
- Applicability: historical player movement on the controlled-camera LocalPlayer path
- Runtime validation: not performed
- Review status: pending independent source-only review

## Corrected claim

The earlier F-09 claim that B omits the `0.98F` input factor is incorrect. Both versions apply one `0.98F` factor on the controlled-camera path. The bounded difference is the factor order when both item-use slowdown and sneaking-speed scaling apply: A multiplies the input component by `0.2F`, then the sneaking-speed attribute, then `0.98F`; B multiplies by `0.98F`, then `0.2F`, then the attribute, before its square-movement correction. These are binary32 operations and are not interchangeable for every reachable input.

## Paired source evidence

- A `KeyboardInput#tick()` yields exactly `1.0F` for forward-only input (`KeyboardInput.java:21-36`; SHA-256 `c885bcc709e445c3ff91f03bf37acf9320e17fc9daf1bd2ba46ba20487f1d9ed`). A `LocalPlayer#aiStep()` calls `input.tick()` and, when `isUsingItem() && !isPassenger()`, multiplies both input impulses by `0.2F`; when `isMovingSlowly()`, it then multiplies them by the float-cast `SNEAKING_SPEED` attribute (`LocalPlayer.java:662-677`). `LocalPlayer#serverAiStep()` copies those impulses to `xxa`/`zza` on the controlled-camera path (`LocalPlayer.java:607-617`), and the reached `LivingEntity#aiStep()` then multiplies both fields by `0.98F` before constructing the travel vector (`LivingEntity.java:2766-2770`). `LocalPlayer.java` SHA-256 `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611`; `LivingEntity.java` SHA-256 `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`.
- B `KeyboardInput#tick()` creates `(leftImpulse, forwardImpulse)` and calls `Vec2.normalized()` (`KeyboardInput.java:21-36`; SHA-256 `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3`). For forward-only input this remains `(0.0F, 1.0F)`. `LivingEntity#aiStep()` dispatches to `LocalPlayer#applyInput()` before the later immobility zeroing and travel (`LivingEntity.java:2712-2721`). On the controlled-camera branch, `applyInput()` assigns `modifyInput()` results to `xxa`/`zza` (`LocalPlayer.java:608-620`). `modifyInput()` scales the vector by `0.98F`, then by `0.2F` when item use applies, then by the float-cast sneaking-speed attribute when `isMovingSlowly()`; it then calls square-movement correction (`LocalPlayer.java:623-638`). The correction performs length, reciprocal scale, distance-to-square, clamp and final scale (`LocalPlayer.java:641-650`). `LocalPlayer.java` SHA-256 `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`; `LivingEntity.java` SHA-256 `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`.
- The controlled-camera predicate in both LocalPlayer sources is `minecraft.getCameraEntity() == this` (A lines 620-622; B lines 660-662). The shared consumer is the LocalPlayer travel input handed into the existing `LivingEntity` travel path; downstream trajectory claims are outside this finding.

## Reachable one-ULP witness

Choose forward-only keyboard input, controlled camera, item use while not a passenger, crouching, and Swift Sneak I on leg armor. The keyboard source yields the unit forward axis. The slowdown gate is reachable because `isUsingItem()` is true and `isPassenger()` is false. The slow-movement gate is true while crouching. Both versions define the player `SNEAKING_SPEED` default as `0.3` (`Attributes.java:75-77`; identical A/B SHA-256 `14bcc5821d0f67f41fdc3e512556181823cff5c43f84fc1007e51d25f413a3ea`). Both register Swift Sneak's `+0.15F per level` additive `SNEAKING_SPEED` effect for `EquipmentSlotGroup.LEGS` (`Enchantments.java:551-579`; A SHA-256 `01bdf94faa8089e50a282ec0b50c2b9956ddfb4260ae37dd9ec23e460e9d1bd1`, B SHA-256 `ee37699b95dc530fdd91dadef27d19a54bc7c4febe79bca40cc1ee438a626356`). This supplies the reachable `0.45F` sneaking-speed value for Swift Sneak I with the default base and no other attribute modifiers.

For the selected forward axis, A's ordered float operations are `((1.0F * 0.2F) * 0.45F) * 0.98F`; B's are `((1.0F * 0.98F) * 0.2F) * 0.45F`. B's single-axis square correction follows those operations. Evaluating each binary32 multiplication in source order yields adjacent output encodings: A `0x3DB4A234`, B `0x3DB4A235` (one ULP). Both render as `0.0882` at ordinary decimal precision. This is a per-tick input-axis difference for the specified reachable state; no trajectory or persistence result is claimed.

## Snapshot and review history

- Preserve original F-09 candidate commit `95061345541a2b5a8c56e57a7fe71a6007e9d684`, path `findings/F-09-local-player-input-damping.md`, SHA-256 `3a1680c5e724a935cbc576705088f03e3a9d4c6a8e29e5375d4ddb8e93b1e328`; its bytes are not revised by this candidate.
- Original-candidate independent review `dc2b29c81c1fd57941d8ed4a27d1583b1b039b4e`: REQUEST CHANGES. This r2 candidate narrows the claim and is a separate immutable review target.
- Related finding IDs: F-03. F-03's normalization and square-movement behavior remains separate; this candidate isolates the factor-order rounding witness on a single-axis input.
- Applicability and effect are limited to the paired source behavior and witness above. Runtime validation and broader input-state enumeration remain open.
