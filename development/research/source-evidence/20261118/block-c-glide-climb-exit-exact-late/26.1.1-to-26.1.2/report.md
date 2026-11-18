# 26.1.1 → 26.1.2: active Elytra climb exit

## Result and bounds

No source difference was found in the assigned adjacent-release slice for a dry, on-foot local player whose chest slot already contains the default Elytra and whose shared fall-flying flag is active. Both releases follow the same pre-travel limiter and the same climbable-exit order: dispatch to ordinary air travel, perform its first move, then stop fall-flying. This closes only this bounded no-difference check; it is not a full-pair inventory or audit.

Scenario assumptions: no water/lava branch, no passenger, no newly starting flight, and an already-active shared flag. “Ordinary/ladder/vine feet” resolve through the live `onClimbable()` predicate described below; tag membership data was not available in these decompiled source trees and is not asserted here. Rendering, equipment-damage cadence, start/final glide paths, geometry, fluids and direct A/B producers are outside this check.

## Exact source identity and provenance

Both source trees are exact-version, `unobfuscated` Vineflower output and include ready/provenance markers. The 26.1.1 marker identifies exact metadata/client version `26.1.1`, published-unobfuscated mapping, and client SHA-256 `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3`; its source-manifest SHA-256 is `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3`, artifact-manifest SHA-256 `6ee17e11c2cf02a4ba93d66852ea172d5ba857028654fb5df3c1d9c87f0faa81`. See `decompiled_minecraft/26.1.1/unobfuscated.provenance.json` and `unobfuscated.ready.json`.

The 26.1.2 marker identifies exact metadata/client version `26.1.2`, published-unobfuscated mapping, and client SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`; its source-manifest SHA-256 is `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`, artifact-manifest SHA-256 `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`. See `decompiled_minecraft/26.1.2/unobfuscated.provenance.json` and `unobfuscated.ready.json`.

The exact Java source hashes below were checked against each tree’s `unobfuscated.sources.sha256`; the compared files are byte-identical across the releases:

| Relative source | 26.1.1 and 26.1.2 SHA-256 |
| --- | --- |
| `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |

## Ordered movement path (same in both releases)

1. `LocalPlayer.aiStep()` (`LocalPlayer.java:767–858`) handles the jump-edge start request separately; for this already-active scenario, it records `wasFallFlying = isFallFlying()` at line 858. The active state is shared flag 7 (`LivingEntity.java:3597–3599`).
2. `LivingEntity.aiStep()` checks `isFallFlying()` and calls `updateFallFlying()` before constructing the travel input and before calling `travel()` (`LivingEntity.java:3059–3073`). `updateFallFlying()` first calls `checkFallDistanceAccumulation()` (`:3126–3128`). That inherited `Entity` limiter is `if (deltaY > -0.5 && fallDistance > 1.0) fallDistance = 1.0` (`Entity.java:2835–2839`). Next, its glide-validity cancellation (`!canGlide()` then clear flag 7) is inside `if (!level().isClientSide())`; it does not clear an active local-client flag in this client-side tick. The player override of `canGlide()` additionally rejects abilities flight (`Player.java:1407–1409`).
3. `Player.travel()` delegates to `LivingEntity.travel()` for the ordinary on-foot case (`Player.java:1381–1404`). In dry conditions, `LivingEntity.travel()` bypasses fluid travel and, with active fall-flying, selects `travelFallFlying()` (`LivingEntity.java:2392–2399`).
4. `travelFallFlying()` first checks `onClimbable()`. For a climbable result, it calls `travelInAir(input)` and only after it returns calls `stopFallFlying()` (`LivingEntity.java:2528–2542`). The non-climbable glide branch instead updates Elytra movement, calls `move(SELF, deltaMovement)`, then runs collision handling only on non-client levels.
5. `travelInAir()` computes friction and calls `handleRelativeFrictionAndCalculateMovement()` before gravity/friction writes (`LivingEntity.java:2430–2453`). That helper calls `moveRelative`, applies `handleOnClimbable`, and then calls the first `move(SELF, deltaMovement)` (`LivingEntity.java:2623–2632`). Thus on the climbable exit, the first move occurs inside ordinary air travel before `stopFallFlying()` toggles shared flag 7 true and then false (`LivingEntity.java:2544–2547`).
6. `LivingEntity.onClimbable()` first rejects spectators, then reads `getInBlockState()`. If active fall-flying and that state is tagged `CAN_GLIDE_THROUGH`, it returns false before consulting `CLIMBABLE`; otherwise a `CLIMBABLE` state returns true (`LivingEntity.java:1689–1700`). Ordinary non-climbable feet therefore take the glide branch; a ladder/vine state takes the air-then-stop path only when this ordered predicate returns true. The source tree provides tag keys but not the tag JSON membership lists, so no claim is made about every ladder/vine’s membership or special state.

## Compared terminal slices

- `LocalPlayer.aiStep`, `Player.travel` / `canGlide`, `LivingEntity.aiStep` / `updateFallFlying` / `travel` / `travelFallFlying` / `travelInAir` / `onClimbable`, and `Entity.checkFallDistanceAccumulation`: identical source-file hashes and identical relevant bodies in 26.1.1 and 26.1.2.
- No difference in active-state observation, client/server cancellation gate, fall-distance clamp order, dry branch choice, climb-exit order, stop-flag writes, or first move was found for this slice.
- Not examined: full pair tick graph, tag member/resource changes, glide start/final/custom equipment, block geometry, fluid paths, or other C operations.
