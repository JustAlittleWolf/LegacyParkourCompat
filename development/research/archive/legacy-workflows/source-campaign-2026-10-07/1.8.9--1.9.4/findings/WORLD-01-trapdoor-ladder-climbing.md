# WORLD-01: matching open trapdoor above a ladder is climbable

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: climb-state predicate and neighbor ladder dependency; `WORLD-01`
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: historical player behavior; only for trapdoor/ladder arrangements available in both versions
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A: source manifest A; `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; `LivingEntity#isClimbing()Z`, lines 794-800; SHA-256 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`. `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/net/minecraft/block/TrapdoorBlock.java` state inventory lines 23-29, SHA-256 `e00c4fdfa234ac5cf265816fbbe088709668cd5a44eb537a1f391ce2f5f795ba`; `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/net/minecraft/block/LadderBlock.java` facing property lines 17-21, SHA-256 `c13b123f8fd7427d7f646da3b789eb97d4e54aa2fe2860f66cf4fd1302865106`.
- B: source manifest B; `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; `LivingEntity#isClimbing()Z` and `#canClimbTrapdoor(BlockPos, BlockState)Z`, lines 905-926; SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`. `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/block/TrapdoorBlock.java` declares `OPEN` and `FACING` at lines 24-25, SHA-256 `bbfbeac22de7e72b55736a35c29c10f372df1d846dd01cd16d7ee2a0913b2339`; `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/block/LadderBlock.java` exposes the horizontal facing property at line 17, SHA-256 `f411d494a4f8c87b2b23d182527713d3b0c2fad4da329b82b84ea301a9d65fcb`.

## Source-level difference

A returns climbing only when the block at the player's floored position is a ladder or vine, and excludes spectators. B keeps that spectator exclusion and adds a trapdoor branch: the block must be a `TrapdoorBlock`, its `OPEN` property must be true, the block directly below must be a ladder, and the ladder and trapdoor `FACING` values must match. The ladder and trapdoor state properties and their registrations exist on both sides; the new rule therefore changes movement for an arrangement of existing blocks rather than assigning historical behavior to a modern-only block.

## Reachability and dependencies

`LivingEntity.moveRelative(FF)V` consults `isClimbing()` before movement and applies the climbing horizontal clamp, fall-distance reset, downward-velocity clamp, and player-sneak descent rule. A's consumer is at lines 1150-1162; B's corresponding consumer is at lines 1376-1393 in the same paired `LivingEntity.java` files. The new predicate also reads the block state directly below and compares its facing.

## Consequence and uncertainty

When all B predicate conditions hold, B selects the climbing travel branch; A does not. The resulting player velocity follows the existing climbing clamp branch. No runtime trajectory was measured, and the introduction release is unknown within this interval.

## Handoff

Independent delta: trapdoor-assisted climb detection using a matching ladder below. Shape equivalence for trapdoors and ladders is covered separately. Runtime validation is deferred.
