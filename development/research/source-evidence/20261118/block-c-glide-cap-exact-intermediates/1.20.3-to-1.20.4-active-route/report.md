# 1.20.3 → 1.20.4 Elytra active-route and damping comparison

## Result

No difference found in the assigned active-glide slice. The compared `LivingEntity.java` files are byte-identical at both releases (SHA-256 `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`). The adjacent pair therefore yields no source finding for active-flag clearing, dry travel routing, climbable cleanup bypass, or pre-move Elytra damping.

## Exact sources and provenance

- Older: `decompiled_minecraft/1.20.3/mojmap/net/minecraft/world/entity/LivingEntity.java`; `travel(Vec3)` lines 2035–2166, `handleRelativeFrictionAndCalculateMovement(Vec3,float)` lines 2202–2213, `handleOnClimbable(Vec3)` lines 2230–2244, `aiStep()` call site `updateFallFlying()` line 2593, and `updateFallFlying()` lines 2641–2666. `LocalPlayer.java` SHA-256: `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`.
- Newer: `decompiled_minecraft/1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java`; the same members and line bounds. `LocalPlayer.java` has the same SHA-256 as the older file.
- 1.20.3 source identity: `.decompile-provenance.json` SHA-256 `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f`; its inputs identify release metadata `.../1.20.3.json` (SHA-256 `05e727aac55594b6014e9714ad38fa16af09f075daf5efa0f5743f2453fa8fc2`), client JAR (SHA-256 `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`) and Mojang client mappings (SHA-256 `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`).
- 1.20.4 source identity: top-level `mojmap.provenance.json` declares exact `versionId: 1.20.4`, namespace `mojmap`, `mappingOption: mojmap`, and the pinned Gradle/Java/Vineflower tool versions. `mojmap.ready.json` identifies the exact client JAR SHA-256 `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838`; `artifacts.sha256` identifies `1.20.4/client_mappings.txt` as `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`. Source-manifest SHA-256: `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` (the per-file entry for `LivingEntity.java` agrees with the file hash above). `artifacts.sha256` SHA-256: `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948`.

## Bounded comparison

The `updateFallFlying()` tick update begins from shared flag 7. It keeps the flag only while it was already set, the entity is airborne, not a passenger and not affected by levitation; it then requires an enabled Elytra in the chest slot. Invalid state clears the local flag, and only the logical server writes the shared flag back. The method and its call site are unchanged. Starting eligibility and final tick consequences were excluded as assigned.

With the assignment's dry-state assumption, `travel()` bypasses the water and lava branches, then routes an already active flyer through `isFallFlying()` before the ordinary friction/climbable route. The glide branch computes horizontal look magnitude and incoming horizontal speed, applies the same ordered lift/dive/pitch corrections and horizontal-speed convergence in both versions, then executes `setDeltaMovement($$13.multiply(0.99F, 0.98F, 0.99F))` immediately before `move(MoverType.SELF, ...)`. The damping operands are float literals widened for the `Vec3` multiplication; no separate hard velocity cap precedes this move in the compared slice. The subsequent collision response is unchanged and its geometry/contact dependencies are outside this assignment.

For ordinary feet and ladder/vine feet alike, the active-glide branch does not enter `handleRelativeFrictionAndCalculateMovement()` or its `handleOnClimbable()` cleanup. That cleanup still checks `onClimbable()`, resets fall distance, clamps X/Z with `Mth.clamp(...,-0.15F,0.15F)`, lower-bounds Y with `Math.max(y,-0.15F)`, and applies the player/feet-block/sneak predicate to suppress negative Y. These lines are also byte-identical across the pair.

Compared terminal slices: `LivingEntity.travel`, active-flag update and call site, normal friction/climbable route and cleanup. No difference. Out of bounds: start eligibility, final-tick behavior, fluid branches, movement collision/contact and geometry dependencies, plus other movement operations.
