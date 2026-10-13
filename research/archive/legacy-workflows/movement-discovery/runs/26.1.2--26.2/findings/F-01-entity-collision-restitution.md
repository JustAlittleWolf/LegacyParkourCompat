# F-01: Entity collision restitution and block landing

- Older version A: 26.1.2
- Newer version B: 26.2
- Mechanic / coverage slice IDs: entity collision restitution; C1-C3
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: 26.2; the 26.1 group-base and 26.1.2 sources have identical relevant movement-source hashes
- Runtime validation: not performed

## Paired evidence

- A manifest: `../run.md`, artifact A. `net/minecraft/world/entity/Entity.java`, `Entity.move(MoverType, Vec3)`, lines 757-785, SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`; `Block.java`, `updateEntityMovementAfterFallOn(BlockGetter, Entity)`, lines 498-500, SHA-256 `1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977`; `BedBlock.java`, `updateEntityMovementAfterFallOn` / `bounceUp`, lines 138-150, SHA-256 `996e4ca63f4bfaea14b215648f2318d52c49f6c802317381a670d87c0a6d0b03`; `SlimeBlock.java`, corresponding methods, lines 33-45, SHA-256 `84d22cf526d6bf1b4ec4b0b642a76fc2c90380a71f05ab92a7de9935f0d1c38e`.
- B manifest: `../run.md`, artifact B. `net/minecraft/world/entity/Entity.java`, `Entity.move` and `restituteMovementAfterCollisions`, lines 764-785 and 802-851, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`; `Block.java`, `getBounceRestitution()`, lines 494-496, SHA-256 `cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab`; `Blocks.java`, bed/slime registrations, lines 696-704 and 2976-2979, SHA-256 `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435`; `Attributes.java`, `BOUNCINESS`, line 31, SHA-256 `4a7c33552f256b5d35c6d46fd5810405f4e98182e2b26a9a3009ef4f1d3fdd5c`; `LivingEntity.java`, `getEntityBounciness()`, lines 2202-2204, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- Player reachability on both sides: `LocalPlayer.aiStep()` delegates to its superclass at line 914; `LivingEntity.aiStep()` dispatches `travel(input)` at A line 3073 and B line 3140; `LivingEntity.travel` calls `Entity.move`. LocalPlayer source hashes are in the run manifest.
- `javap` on the exact 26.2 client jar confirmed the private invocation owner and descriptors recorded in the run manifest.

## Source-level difference

Under equal post-collision movement state, A zeros delta X/Z for the collided horizontal axes. If movement is simulated and requested Y differs from clipped Y, A then calls the effect block's `updateEntityMovementAfterFallOn` callback. Base blocks multiply Y by `0.0`; beds use `-movement.y * 0.66F * factor` and slime uses `-movement.y * factor` only when descending and not suppressing bounce. `factor` is `1.0` for living entities and `0.8` otherwise. Sneaking delegates bed/slime to the base callback.

B replaces that callback path with `restituteMovementAfterCollisions`. It computes axis restitution from the living entity's bounciness attribute, block restitution from `Block.getBounceRestitution()`, effective gravity, air drag, and the suppression tag; it can also emit a bounce event and set position synchronization. Bed blocks register `0.75F`, slime registers `1.0F`, and the default living-entity bounciness attribute is `0.0` and syncable. The 26.1 group base has the same relevant source hashes as 26.1.2, establishing the `V26_1` boundary.

## Reachability and dependencies

`LocalPlayer.aiStep()` → `LivingEntity.aiStep()` → `LivingEntity.travel()` → `Entity.move()` → collision resolution → post-collision velocity handling. The block below the entity is the `effectState` input. In B, an additional server-synchronized `BOUNCINESS` attribute can alter entity restitution; B's bounce-suppression tag also gates its new response. A instead uses the block callback and the existing sneak predicate. The emulation intercepts the restitution invocation and composes block-specific historical landing behavior through the keyed `BlockLandingBehavior` hook.

## Consequence and uncertainty

Source proves that velocity after horizontal and vertical collision is calculated differently, including the old bed/slime formulas versus the new restitution expression and attribute input. Different player trajectories are predicted from those state changes; no gameplay trajectory was recorded. The 26.2 suppression-tag contents and non-collision movement stages are outside this focused finding.

## Handoff

`CollisionRestitution26_1` restores collided-axis clipping and routes vertical contact to an independently versioned `BlockLandingBehavior` keyed by block ID. This keeps the pair's general collision change separate from older bed/slime changes. During integration, adapt older block landing or bounce implementations to `BlockLandingBehavior` so the closest applicable block-specific version wins; reconcile the shared `Entity.move` redirect once across branches. Runtime testing remains for the TAS workflow.
