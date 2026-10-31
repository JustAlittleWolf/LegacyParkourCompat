# Frozen short-pose collision-query roster

Freeze point: before consulting any prior collision/candidate reports.
Pair: exact Mojmap 1.16.5 → 1.17 → 1.17.1 trees under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`.

## Player dimensions and movement query producers

- `net/minecraft/world/entity/player/Player.java`: pose dimension registrations and lookup, existing movement-relevant poses.
- `net/minecraft/world/entity/Entity.java`: `collide(Vec3)`, `collideBoundingBox*`, `move`, `getBoundingBox`, `getCollisionBox`, `CollisionSpliterator` caller.
- `net/minecraft/world/entity/LivingEntity.java`: jump/travel mover path, player pose-dependent movement dispatch.
- `net/minecraft/world/entity/EntityDimensions.java`: player pose box construction.
- `net/minecraft/world/phys/AABB.java`: `expandTowards`, `inflate`, `move`, intersection.

## Query encoding, collection and axis response

- `net/minecraft/world/phys/shapes/Shapes.java`: `create(AABB)`, `findBits`, `joinIsNotEmpty`, axis merger construction.
- `net/minecraft/world/phys/shapes/IndirectMerger.java`, `IndexMerger.java`, `IdenticalMerger.java`, `DiscreteCubeMerger.java`, `NonOverlappingMerger.java`, `BitSetDiscreteVoxelShape.java`, `VoxelShape.java`.
- `net/minecraft/world/level/CollisionGetter.java`, `CollisionSpliterator.java`, `Level.java`, `LevelReader.java`, `EntityGetter.java`.
- `net/minecraft/world/level/border/WorldBorder.java`: border shape as one permitted direct provider with arbitrary equal-state extent.
- `net/minecraft/world/phys/shapes/CollisionContext.java`, `EntityCollisionContext.java`: pose/entity-specific shape context gates.

## Scope boundary

Analyze only LocalPlayer's vanilla dimensions and `Entity.collide` queries produced by ordinary SELF movement and step-up candidates in swimming, fall-flying, spin-attack and dying poses; keep pose state equal. Check one-, two- and three-axis displacement expansions where source route permits. Compare the query-encoding and Boolean AND overlap output through the resolved movement axis result. No runtime, server-side world/provider production, product/history, wiki or backlog inputs are used. This roster is limited to the named pose/query domain and cannot claim pair-wide collision-provider closure.
