# 1.13.2 changes water travel drag while sprinting

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: 3.5 (water travel and sprint state)
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player movement while sprinting in water
- First changed release: unknown within (1.12.2, 1.13.2]
- Runtime validation: not performed

## Paired evidence

- A: `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative(FFF)V`, lines 1555-1584; SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`. Water movement begins with `f = getBaseMovementSpeedMultiplier()` (base method returns `0.8F`) and applies `velocityX *= f`, `velocityZ *= f`; after vertical damping it applies `velocityY -= 0.02` when gravity is enabled, regardless of sprint state.
- B: corresponding method, lines 1617-1655; SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`. Water movement begins with `f = isSprinting() ? 0.9F : getBaseMovementSpeedMultiplier()`, then depth-strider adjustment may modify `f`. With Dolphin's Grace absent, it applies `velocityX/Z *= f`; with gravity enabled it subtracts no water gravity while sprinting.
- Reachability: A LocalClientPlayerEntity can enter sprint through the held sprint key while in water if other gates pass (A `mobTick()V`, lines 734-745). B permits the sprint flag when submerged under its new water gates (B `mobTick()V`, lines 738-758). The movement body is reached through `LivingEntity.mobTick()` -> virtual `moveRelative()` on the local player.

## Source-level difference

For a sprinting player in water, with no Depth Strider or Dolphin's Grace and gravity enabled, A damps horizontal velocity by `0.8F` and subtracts `0.02` from vertical velocity. B damps horizontal velocity by `0.9F` and skips that vertical gravity update. The shared `0.8F` fallback is returned by `LivingEntity.getBaseMovementSpeedMultiplier()` in both versions. This finding assumes the sprint flag is already active at water travel.

## Reachability and dependencies

The local sprint flag is an input-controlled player state; it feeds the inherited water branch. B's submerged-sprint gate and swim flag are documented separately. Depth Strider changes `f` and gravity state/tags can affect surrounding movement; their data and aggregation remain in stages 5-6. The finding excludes Dolphin's Grace so that its separate coefficient override is not conflated.

## Consequence and uncertainty

Source proves different post-move horizontal damping and a skipped vertical adjustment under the stated precondition. It predicts retained horizontal momentum and greater vertical velocity in B than A for equivalent incoming state; no trajectory was measured. Exact depth-strider/resource inputs and interactions remain open.

## Handoff

Sprint-dependent water travel behavior. Related to water sprint eligibility and ordinary water gravity, but scoped to the already-sprinting branch.
