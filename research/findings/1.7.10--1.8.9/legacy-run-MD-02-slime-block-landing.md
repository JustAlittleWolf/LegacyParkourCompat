# MD-02: Slime blocks bounce players and suppress fall damage

- **1.7.10**, ornithe-feather: `1.7.10/ornithe-feather/net/minecraft/entity/Entity.java`; lines 457-481.
- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/entity/Entity.java`; lines 545-573.

## Difference

When a player lands vertically on slime in 1.8.9 while not sneaking, fall handling passes a zero damage multiplier and `beforeCollision` changes negative Y velocity to its positive counterpart instead of leaving the ordinary vertical collision response at zero.

Sneaking delegates to base behavior, so ordinary fall handling and collision velocity clearing apply. 1.7.10 has no slime block or pre-collision hook; its vertical collision response clears Y velocity.

## Movement meaning

`LocalClientPlayerEntity.tick()` -> `LivingEntity.mobTick()` -> `moveRelative(...)` -> `Entity.move(...)` -> vertical collision resolution -> landing/support block callback. 1.8.9 selects a block below the resulting player position, with fence/wall/gate fallback, before fall and collision callbacks.

The source proves callback order and guards.

The same `Entity.move` path is used by the local player.

The run checkpoint retains verification that the selected support block is slime for the stated top-surface collision precondition.

Source-proven: a non-sneaking entity with negative Y velocity receives positive Y velocity through slime's collision callback; slime fall handling passes a zero damage multiplier.

Inference: the landing can launch the player upward and suppress fall damage.

Slime is a 1.8.9-era block; no behavior is assigned to 1.7.10.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.7.10--1.8.9/findings/MD-02-slime-block-landing.md).
