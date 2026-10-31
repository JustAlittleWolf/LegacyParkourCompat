# Frozen blind roster: player collision query and collision-response route

This roster was frozen before reading prior collision reports. Exact source roots are the read-only Mojmap publications under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.16.5,1.17,1.17.1}\mojmap`.

## Pair boundary

- 1.16.5 → 1.17.1, with adjacent inspection of 1.16.5 → 1.17 and 1.17 → 1.17.1.

## Ordered route to inspect

`LocalPlayer.aiStep` / `LivingEntity.travel` → `Entity.move` → player AABB expansion toward resolved displacement → `CollisionGetter.getBlockCollisions` / `CollisionSpliterator` → query shape creation and per-provider Boolean AND → block/world-border collision shapes → `Entity.collide` axis clipping and resolved movement write.

## Frozen source file roster

Core query and shape math:

- `net/minecraft/world/phys/shapes/Shapes.java`
- `net/minecraft/world/phys/shapes/VoxelShape.java`
- `net/minecraft/world/phys/shapes/IndirectMerger.java`
- `net/minecraft/world/phys/shapes/IdenticalMerger.java`
- `net/minecraft/world/phys/shapes/DiscreteCubeMerger.java`
- `net/minecraft/world/phys/shapes/NonOverlappingMerger.java`
- `net/minecraft/world/phys/shapes/IndexMerger.java`
- `net/minecraft/world/phys/shapes/ArrayVoxelShape.java`
- `net/minecraft/world/phys/shapes/BitSetDiscreteVoxelShape.java`
- `net/minecraft/world/phys/shapes/DiscreteVoxelShape.java`
- `net/minecraft/world/phys/shapes/BooleanOp.java`
- `net/minecraft/world/phys/AABB.java`
- `net/minecraft/world/phys/Vec3.java`

Collision collection / consumer:

- `net/minecraft/world/level/CollisionGetter.java`
- `net/minecraft/world/level/CollisionSpliterator.java`
- `net/minecraft/world/level/BlockGetter.java`
- `net/minecraft/world/level/LevelReader.java`
- `net/minecraft/world/level/EntityGetter.java`
- `net/minecraft/world/entity/Entity.java`
- `net/minecraft/world/entity/LivingEntity.java`
- `net/minecraft/world/entity/player/Player.java`
- `net/minecraft/client/player/LocalPlayer.java`
- `net/minecraft/world/level/border/WorldBorder.java`

Source-level provider witnesses named by the task scope:

- `net/minecraft/world/level/block/EndRodBlock.java`
- `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java`

Any subsequently discovered call edge or provider needed to validate the frozen route will be recorded in an addendum before its paired analysis is used; this list is the initial pre-report roster, not a claim all providers are covered.

## Frozen questions

1. Which player-generated AABBs can reach `Shapes.create(AABB)` in the movement collision route, including the displacement-expanded query and relevant entity poses?
2. Under the source's `findBits` rules, when does 1.16.5 use a fallback continuous box while 1.17/1.17.1 snap to a discrete shape, and can the changed coordinates survive the AND merger's coordinate coalescing?
3. Does the singleton-full-block query path bypass the created shape, and how do non-singleton providers reach AND overlap?
4. Can any reachable partial/provider shape plus an on-foot LocalPlayer state produce a different Boolean collision answer or axis-clipped displacement at equal AABB, requested movement and provider state?
5. If not, can a source-backed bound cover all reachable LocalPlayer query extents and provider coordinates, or must the gate remain open?

No prior collision report or product/wiki/backlog/history was consulted to freeze this roster.
