# Keyboard vector downstream scope: 1.21.4 → 1.21.5

## Immutable source identity

Exact local decompiled Mojmap sources were examined read-only. The 1.21.3, 1.21.4 and 1.21.5 ready markers identify those exact versions and `mojmap`; their provenance identifies the same `decompileMinecraft` batch (`1.21.1,1.21.3,1.21.4,1.21.5`), Vineflower 1.12.0, mapping-io 0.9.1, Java 25. The manifest SHA-256 values are:

- 1.21.3 `mojmap.sources.sha256`: `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`
- 1.21.4 `mojmap.sources.sha256`: `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`
- 1.21.5 `mojmap.sources.sha256`: `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`

Examined source files and SHA-256 (each matched the corresponding source manifest):

- 1.21.3 `net/minecraft/world/entity/LivingEntity.java`: `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`
- 1.21.4 `net/minecraft/world/entity/LivingEntity.java`: `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`
- 1.21.4 `net/minecraft/client/player/LocalPlayer.java`: `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611`
- 1.21.4 `net/minecraft/client/player/KeyboardInput.java`: `c885bcc709e445c3ff91f03bf37acf9320e17fc9daf1bd2ba46ba20487f1d9ed`
- 1.21.5 `net/minecraft/world/entity/LivingEntity.java`: `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`
- 1.21.5 `net/minecraft/client/player/LocalPlayer.java`: `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`
- 1.21.5 `net/minecraft/client/player/KeyboardInput.java`: `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3`

Prior bounded comparison and independent verdict: commit `f4e54477501fd012c44b975f64f16264d90a18f2`, `development/research/source-evidence/20261117/block-c-player-input/keyboard-rounding/comparison.md` and `verdict.md`. Reuse the verdict's ACCEPT for the endpoint claims without broadening its scope. Its bound comparison draft hash was `93b46651275edd1e26aca7d51933542ea42543a9342eb9811f8626f6d3c78e47`.

## Old component damping: x and z only

The nearest earlier exact stable version, 1.21.4, has `LivingEntity.travel(Vec3)` at `net/minecraft/world/entity/LivingEntity.java:2189-2199`. Only when `isControlledByLocalInstance()` is true does it dispatch to fluid, fall-flying, or air travel. In the travel section (`:2766-2774`), it performs two ordered float compound assignments, `this.xxa *= 0.98F;` then `this.zza *= 0.98F;`, and constructs `new Vec3(this.xxa, this.yya, this.zza)`. The enclosing `LivingEntity.aiStep` calls travel at `:2782`. The immediately earlier 1.21.3 source has the same xxa/zza compound assignments at `LivingEntity.java:2756-2757`.

This old operation damps lateral (`xxa`) and forward (`zza`) input. It does not multiply `yya`; the movement vector uses the existing `yya` value unchanged by these two assignments. The assignments and their order are directly source-proven. Do not characterize them as three-axis or vertical damping.

In 1.21.4, controlled-camera `LocalPlayer.serverAiStep()` copies `input.leftImpulse` to `xxa` and `input.forwardImpulse` to `zza` at `LocalPlayer.java:607-617`, after its input and item/sneak scaling path in `aiStep()` (`:662-678`). This supplies the components later subject to LivingEntity's travel damping. Keyboard input itself remains raw signed axis values in `KeyboardInput.tick()` (`KeyboardInput.java:22-34`).

## New controlled-camera transform and square correction

At the 1.21.5 endpoint, `LivingEntity.aiStep()` calls virtual `this.applyInput()` before subsequent travel processing (`LivingEntity.java:2712`). Base `LivingEntity.applyInput()` retains x-then-z `0.98F` compound assignments (`:2806-2809`). `LocalPlayer.applyInput()` overrides that operation: under `isControlledCamera()` it calls `modifyInput(input.getMoveVector())`, assigns returned `Vec2.x` to `xxa` and `.y` to `zza`, and sets jumping (`LocalPlayer.java:608-617`); when the camera gate is false, it delegates to `super.applyInput()` (`:618-620`). Thus the new vector transform applies to the controlled-camera player path; the base component damping remains the non-controlled-camera fallback.

`LocalPlayer.modifyInput()` (`:623-639`) first returns an exact zero vector when `lengthSquared() == 0.0F`. Otherwise it applies `0.98F`; applies `0.2F` only when `isUsingItem() && !isPassenger()`; applies the sneaking-speed attribute only when `isMovingSlowly()`; then calls square correction. `isMovingSlowly()` is `isCrouching() || isVisuallyCrawling()` (`:603-605`). The correction (`:641-658`) returns unchanged at length `<= 0.0F`; otherwise it normalizes the direction, computes the square-distance factor, caps `length * distance` at `1.0F`, and rescales the direction. These operations are float `Vec2` math. The correction is a separate .5 operation after damping and the item/sneak factors; no corresponding square-correction operation is present in the checked .4 LocalPlayer input path.

Input production is also changed at this endpoint: .4 `KeyboardInput.tick()` stores raw signed forward and lateral impulses (`KeyboardInput.java:22-34`); .5 computes forward then lateral, constructs `Vec2(lateral, forward)`, normalizes it, and stores `moveVector` (`KeyboardInput.java:23-36`). The accepted keyboard-normalization claim is distinct from the LocalPlayer damping/correction claim.

## Neighboring gates and scope limits

The speed transform's item gate explicitly excludes passengers; the slow-speed gate uses crouching or visually crawling. Sprint logic remains a neighboring operation: .4 `canStartSprinting()` uses the forward impulse threshold (`LocalPlayer.java:1053-1071`); .5 uses `input.hasForwardImpulse()` and separately checks item use, passenger/vehicle, slow movement, and fluid/fall-flying conditions (`:1065-1074`). The .5 sprint-trigger reset has its own shift, nonpassenger-item-use, and backward-input gate (`:718-719`). Those sprint decisions read input state and are not the square-correction arithmetic. No sprint or crouch behavior is included in the square-correction claim beyond the stated speed-factor gate.

Established boundary: 1.21.4 has raw keyboard axes and travel-time xxa/zza damping; 1.21.5 has normalized keyboard input and moves controlled-camera damping into the LocalPlayer vector transform, followed by distinct square correction. 1.21.3 corroborates that the old xxa/zza damping predates .4. This is a source-only, bounded comparison, not runtime parity or full movement-tick coverage. No 1.21.6 canonical ready source was available in this check. Therefore the exact prior-to-endpoint change is established, but persistence or any further change after 1.21.5 is unresolved; no next-patch claim is made.
