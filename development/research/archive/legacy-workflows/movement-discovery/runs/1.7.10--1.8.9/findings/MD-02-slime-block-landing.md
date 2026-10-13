# MD-02: Slime blocks bounce players and suppress fall damage

- Older version A: 1.7.10
- Newer version B: 1.8.9
- Mechanic / coverage slice IDs: stages 4.1 and 5.1
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.7.10, 1.8.9]
- Runtime validation: not performed

## Paired evidence

- A artifact A; `../../../../decompiled_minecraft/1.7.10/ornithe-feather/net/minecraft/entity/Entity.java`; `Entity.move(double,double,double)`; lines 457-481 and 567-579; SHA-256 `AF4EF7B11BC709D96BD3EEA1260A61D38A1A04996BDF87F2A77C459DDF070A69`. When requested Y differs from clipped Y, A's collision response clears vertical velocity. A `net/minecraft/block/Block.java:1073-1074` has an empty `onSteppedOn` callback and no `beforeCollision` callback; A `Blocks.java` has no slime registration. Hashes: `462EA8943470DE83F2D923D1D267BEBC6B9AF05EECF3C60C5E90EAE870394D0B`, `B912E4C682F9E1FFECD916F3EE5CC6FF1BBA3196E0634E20442B618A0A062E11`.
- B artifact B; `../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/entity/Entity.java`; `Entity.move(double,double,double)`; lines 545-573 and 700-711; SHA-256 `D4C10932CB5BB1067A5A58BE4E1BB1B42BDDE3B6FC893D88178CC07435A1696B`. After vertical collision and fall handling, B calls `block.beforeCollision(world, entity)` when requested Y differs from clipped Y. Base `Block.beforeCollision` clears Y velocity; `SlimeBlock` overrides it and reverses negative Y velocity. Slime fall handling passes zero damage multiplier unless the entity is sneaking.
- B block registration and implementation: `Blocks.java:193,401` declares and resolves `SLIME`; `SlimeBlock.java:24-39` contains fall/collision behavior; `Block.java:730-736` defines defaults. Hashes: `1DA2D85406ED991E8F6BC1F420BABF0BD43598478F347C6A4DC9FEA322ED0695`, `FA00F5B8903FA580E860CD72CFC11827A06EAAD4600D05BD0F209849B04CBAFA`, `EA10F05106A3CF7189AEC85236A7ECF9C7106A717F37BED583B5EA4ADEFFA528`.

## Source-level difference

When a player lands vertically on slime in B while not sneaking, fall handling passes a zero damage multiplier and `beforeCollision` changes negative Y velocity to its positive counterpart instead of leaving the ordinary vertical collision response at zero. Sneaking delegates to base behavior, so ordinary fall handling and collision velocity clearing apply. A has no slime block or pre-collision hook; its vertical collision response clears Y velocity.


## Reachability and dependencies

`LocalClientPlayerEntity.tick()` -> `LivingEntity.mobTick()` -> `moveRelative(...)` -> `Entity.move(...)` -> vertical collision resolution -> landing/support block callback. B selects a block below the resulting player position, with fence/wall/gate fallback, before fall and collision callbacks. The source proves callback order and guards. The same `Entity.move` path is used by the local player. The run checkpoint retains verification that the selected support block is slime for the stated top-surface collision precondition.

## Consequence and uncertainty

Source-proven: a non-sneaking entity with negative Y velocity receives positive Y velocity through slime's collision callback; slime fall handling passes a zero damage multiplier. Inference: the landing can launch the player upward and suppress fall damage. No trajectory or fall outcome was runtime-tested. Slime is a B-era block; no behavior is assigned to A.

## Handoff

Keep bounce and fall damage behavior under their captured sneak predicates. Preserve callback timing after collision and fall handling. Slime's low-speed ground contact damping and slipperiness are documented separately. The release boundary is unknown within `(1.7.10, 1.8.9]`; runtime validation is deferred.
