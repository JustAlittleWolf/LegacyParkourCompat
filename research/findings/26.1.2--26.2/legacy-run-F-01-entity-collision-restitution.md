# F-01: Entity collision restitution and block landing

- **26.1.2**, unobfuscated: `net/minecraft/world/entity/Entity.java`; lines 757-785.
- **26.2**, unobfuscated: `net/minecraft/world/entity/Entity.java`; lines 764-785.

## Difference

Under equal post-collision movement state, 26.1.2 zeros delta X/Z for the collided horizontal axes.

If movement is simulated and requested Y differs from clipped Y, 26.1.2 then calls the effect block's `updateEntityMovementAfterFallOn` callback.

Base blocks multiply Y by `0.0`; beds use `-movement.y * 0.66F * factor` and slime uses `-movement.y * factor` only when descending and not suppressing bounce. `factor` is `1.0` for living entities and `0.8` otherwise.

Sneaking delegates bed/slime to the base callback.

26.2 replaces that callback path with `restituteMovementAfterCollisions`.

It computes axis restitution from the living entity's bounciness attribute, block restitution from `Block.getBounceRestitution()`, effective gravity, air drag, and the suppression tag; it can also emit a bounce event and set position synchronization.

Bed blocks register `0.75F`, slime registers `1.0F`, and the default living-entity bounciness attribute is `0.0` and syncable.

The 26.1 group base has the same relevant source hashes as 26.1.2, establishing the `V26_1` boundary.

## Movement meaning

`LocalPlayer.aiStep()` → `LivingEntity.aiStep()` → `LivingEntity.travel()` → `Entity.move()` → collision resolution → post-collision velocity handling.

The block below the entity is the `effectState` input.

In 26.2, an additional server-synchronized `BOUNCINESS` attribute can alter entity restitution; 26.2's bounce-suppression tag also gates its new response. 26.1.2 instead uses the block callback and the existing sneak predicate.

The emulation intercepts the restitution invocation and composes block-specific historical landing behavior through the keyed `BlockLandingBehavior` hook.

Source proves that velocity after horizontal and vertical collision is calculated differently, including the old bed/slime formulas versus the new restitution expression and attribute input.

The 26.2 suppression-tag contents and non-collision movement stages are outside this focused finding.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/26.1.2--26.2/findings/F-01-entity-collision-restitution.md).
