# 1.21.7 → 1.21.8 Elytra climb-exit source comparison

## Result

No source difference was found in the bounded operation. For an already active ordinary chest Elytra on a dry tick, the ordinary-feet route and the ladder/vine climbable route have identical source and ordering in 1.21.7 and 1.21.8. No difference finding is warranted for this slice.

## Exact source evidence

Both sides use Mojmap sources from `decompiled_minecraft/<version>/mojmap/`.

| Compared source | 1.21.7 SHA-256 | 1.21.8 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` |
| `net/minecraft/world/entity/LivingEntity.java` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| `net/minecraft/world/entity/Entity.java` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| `net/minecraft/world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |

For 1.21.7 these source hashes match the entries in the local `decompiled_minecraft/1.21.7/mojmap/.decompile-provenance.json` (file SHA-256 `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d`). For 1.21.8 the adjacent `decompiled_minecraft/1.21.8/mojmap.ready.json` is `ready`, identifies version `1.21.8` / `mojmap`, and points its recorded `outputPath` at the older `build/movement-campaign-2026-10-07/ready/1.21.8/mojmap` location. That recorded path is absent in this checkout, but the ready marker is adjacent to the accessible canonical source tree and its manifest. The marker SHA-256 is `2e3bf302503eb127061cc605797b390f5f4a4b508f09350a59d78ced857ba7d7`; it declares source-manifest SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`, which matches the local `decompiled_minecraft/1.21.8/mojmap.sources.sha256` bytes. That manifest records 6,105 source files; the four compared file hashes above also match their entries. This is a relocated-output-path condition, not missing 1.21.8 source evidence.

## Bounded behavior and order

The following references are the same in both releases.

- `LocalPlayer.tick()` calls `super.tick()` when the client player has loaded (`LocalPlayer.java:197–219`). `LocalPlayer.aiStep()` records `wasFallFlying` after the input/start-flight section (`LocalPlayer.java:683–830`); the start-flight trigger is outside this already-active case.
- `LivingEntity.aiStep()` first applies the existing movement deadband before the travel phase (`LivingEntity.java:2737–2761`): for `EntityType.PLAYER`, if the incoming velocity's `horizontalDistanceSqr()` is `< 9.0E-6`, x and z are both set to zero; independently, y is set to zero when `abs(y) < 0.003`. It then writes the resulting vector with `setDeltaMovement`. This is the same pre-travel cap/deadband on both sides.
- In that same `aiStep()`, active flight calls `updateFallFlying()` at `LivingEntity.java:2805–2807`, before the later `travel()` dispatch at lines 2815–2819. `updateFallFlying()` checks fall-distance accumulation, but its `canGlide()` cancellation (`setSharedFlag(7, false)`) is inside `if (!level().isClientSide)` (`LivingEntity.java:2871–2890`). Thus this direct cancellation branch is server-only; it does not clear the active flag in the actual client-owned player simulation.
- The movement dispatch is gated by `canSimulateMovement() && isEffectiveAi()` (`LivingEntity.java:2815–2819`). Both predicates return `isLocalInstanceAuthoritative()` (`Entity.java:3349–3355`); on a client that predicate uses `isLocalClientAuthoritative()` (`Entity.java:3335–3342`). For this `LocalPlayer`, travel is therefore performed by the locally authoritative client instance.
- For the dry case, `travel()` reaches its `isFallFlying()` branch (`LivingEntity.java:2245–2253`). `travelFallFlying()` tests `onClimbable()` (`LivingEntity.java:2353–2367`). On ordinary feet it updates Elytra velocity, stores it, then moves. On a normal ladder/vine witness it instead calls `travelInAir(input)` and only after that returns calls `stopFallFlying()`.
- In the climbable air fallback, `travelInAir()` reaches `handleRelativeFrictionAndCalculateMovement()` (`LivingEntity.java:2446–2455`): it applies relative input, passes velocity through `handleOnClimbable()`, stores that capped velocity, then calls the first movement (`move`). `handleOnClimbable()` resets fall distance and clamps x/z to `[-0.15F, 0.15F]`, limits downward y to `-0.15F`, and retains the player/ladder sliding gate (`LivingEntity.java:2473–2487`). After the air travel and that first move finish, `stopFallFlying()` toggles shared flag 7 `true` and then `false` (`LivingEntity.java:2369–2372`).

## Bounds

Checked only the active-flight updater/cancellation, pre-travel velocity deadband, dry active-flight route, climbable air fallback and first-move/stop-flag ordering, plus the local simulation gates for this pair. No source-tag membership or tag requirements were inspected beyond the supplied ordinary ladder/vine witness. Excluded: Elytra start/final-tick behavior, custom equipment, fluid/geometry comparisons, and other tick operations. Source comparison only; no runtime parity claim.
