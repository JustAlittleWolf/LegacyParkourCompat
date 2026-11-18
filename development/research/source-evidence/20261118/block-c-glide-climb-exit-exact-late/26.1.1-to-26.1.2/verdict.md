# Independent source audit: active Elytra climb exit

## Verdict

**Accepted as a bounded no-difference check**, with applicability conditional on the actual `Player.onClimbable()` result. The supplied claim concerns the same active-flight pre-travel clamp and the same climbable-exit ordering in exact 26.1.1 and 26.1.2. The four relevant source files are byte-identical across these exact trees and each tree's source manifest records the same file digests. This verdict does not close a full pair inventory or audit.

The ordinary-air distinction matters: when `onClimbable()` is false, `travelFallFlying()` takes the glide branch, moves once there, and does not call `stopFallFlying()` in that branch. The `travelInAir()` then stop sequence applies only when the live climbability predicate is true. For ladder/vine cases, this verdict therefore requires the effective in-block state to be `CLIMBABLE` and not excluded by `CAN_GLIDE_THROUGH`; no resource/tag membership list was established here.

## Frozen input identity

Input report: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-2611-2612-actual-climb-exit/report.md`  
SHA-256 of literal report bytes: `84a2ba87e9bcdad0c3a51f8f7b83a39feb5077c3be01d6a96cbba362a18695c7`.

Canonical source inputs are the local exact decompiler outputs under `decompiled_minecraft/<version>/unobfuscated/`:

| Release | Ready marker source manifest SHA-256 | Ready marker artifact manifest SHA-256 | Exact published client SHA-256 |
| --- | --- | --- | --- |
| 26.1.1 | `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3` | `6ee17e11c2cf02a4ba93d66852ea172d5ba857028654fb5df3c1d9c87f0faa81` | `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3` |
| 26.1.2 | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` |

Both ready markers identify the exact requested release, `unobfuscated` mapping, ready status, and `Vineflower`; the matching provenance files identify the same release and mapping option. The actual SHA-256 of each tree's `unobfuscated.sources.sha256` and `artifacts.sha256` matches its ready marker. The source-manifest entries and direct file hashes agree for all compared files:

| Relative source | SHA-256 in both releases |
| --- | --- |
| `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |

## Independent path trace and exact references

The following references are to the files above in **both** releases. Their identical SHA-256 values establish identical complete file contents, not merely matching excerpts.

1. `LocalPlayer.aiStep()` records the already-active shared fall-flying state at `LocalPlayer.java:858`, then calls `super.aiStep()` at `:914`. `Player.aiStep()` calls `super.aiStep()` at `Player.java:453`. Inherited `LivingEntity.aiStep()` reaches its travel section, checks `isFallFlying()` and calls `updateFallFlying()` before constructing the travel input and invoking `travel()` (`LivingEntity.java:3059–3073`).
2. `updateFallFlying()` first calls `checkFallDistanceAccumulation()` and only performs its can-glide cancellation inside `if (!level().isClientSide())` (`LivingEntity.java:3126–3133`). Thus this already-active logical-client path first applies the inherited limiter. `Entity.checkFallDistanceAccumulation()` sets `fallDistance` to `1.0` exactly when vertical delta is greater than `-0.5` and fall distance is greater than `1.0` (`Entity.java:2835–2839`).
3. In the stated dry, on-foot, non-flying-player case, `Player.travel()` delegates to `super.travel(input)` (`Player.java:1381–1404`). `LivingEntity.travel()` routes active fall-flying to `travelFallFlying()` in the non-fluid branch (`LivingEntity.java:2392–2399`).
4. `travelFallFlying()` calls `travelInAir(input)` and then `stopFallFlying()` when `this.onClimbable()` is true (`LivingEntity.java:2528–2532`). The alternative branch updates glide movement and calls `move()` without this stop call (`:2533–2541`). `stopFallFlying()` writes shared flag 7 true and then false (`:2544–2547`).
5. In ordinary air travel, `travelInAir()` calls `handleRelativeFrictionAndCalculateMovement()` (`LivingEntity.java:2430–2434`). That helper applies relative movement, runs `handleOnClimbable`, then calls `this.move(MoverType.SELF, this.getDeltaMovement())` before returning (`:2623–2632`). Since the receiver is the local player, Java virtual dispatch reaches `LocalPlayer.move()` (`LocalPlayer.java:986–992`), which calls `super.move()` and then updates auto-jump and walked distance. The first ordinary-air movement call therefore occurs before `travelInAir()` returns and before `stopFallFlying()` clears the flag.
6. The dispatch target is the player override: `Player.onClimbable()` returns false while abilities flight is enabled and otherwise delegates to `super.onClimbable()` (`Player.java:2002–2005`). For the stated non-spectator, non-abilities-flying case, `LivingEntity.onClimbable()` reads `getInBlockState()`, rejects a state tagged `CAN_GLIDE_THROUGH` while fall-flying, and otherwise returns true for `CLIMBABLE` (`LivingEntity.java:1689–1700`). This is why the ladder/vine path is conditional on effective state/tag data; untagged non-climbable air reaches the alternative glide branch.

## Boundaries

Externally supplied state is assumed: client-side level; alive, locally simulated, on-foot player; no abilities flight; no water/lava travel; already-active flag 7; default chest-slot Elytra; and the requested in-block state/predicate outcome. This source-only audit does not establish tag JSON membership, world geometry, any other fluid or equipment state, starting/final-glide paths, direct flag producers, a trajectory result, other C-lane operations, or full 26.1.1–26.1.2 tick coverage. It performs no implementation or runtime-parity claim.