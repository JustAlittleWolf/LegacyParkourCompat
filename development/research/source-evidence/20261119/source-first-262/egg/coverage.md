# Turtle egg collision geometry, 26.1.2 to 26.2

## Verdict

No endpoint difference in the collision geometry supplied by `minecraft:turtle_egg` for any valid vanilla state. Both releases register `TurtleEggBlock` for the existing block. Geometry depends only on `EGGS`. All three values of `HATCH` produce the same geometry for a given `EGGS`.

| `EGGS` | Block-local X/Y/Z bounds in sixteenths of a block |
| --- | --- |
| 1 | (3, 0, 3) to (12, 7, 12) |
| 2, 3, 4 | (1, 0, 1) to (15, 7, 15) |

The second shape is `Block.column(14.0, 0.0, 7.0)`; `Block.column` centers the X and Z dimensions at 8 and `Block.box` divides each coordinate by 16.0. `Shapes.box` construction and the grid-versus-array decision are equivalent at these arguments. The full TurtleEggBlock diff changes an entity registry reference in the turtle-spawning path only; the collision provider and its state declaration are identical.

## Exact paired sources

Paths below are relative to `decompiled_minecraft/<version>/unobfuscated/net/minecraft/`. Published unobfuscated client names, no applied mapping files, Vineflower 1.12.0 and Java 25.0.3+9-LTS in both provenance markers.

| Path | 26.1.2 | 26.2 |
| --- | --- | --- |
| `world/level/block/TurtleEggBlock.java` | 35–53, 168–175 | 35–53, 168–175 |
| `world/level/block/state/properties/BlockStateProperties.java` | 104–105 | 104–105 |
| `world/level/block/Blocks.java` | 4920–4931 | 3930–3941 |
| `world/level/block/Block.java` | 164–166, 181–189 | 160–162, 177–185 |
| `world/phys/shapes/Shapes.java` | 52–85, 102–114 | 52–85, 102–114 |
| `world/level/block/state/BlockBehaviour.java` | 103, 333–335, 672–686, 926, 979, 1092–1095 | 104, 327–329, 678–692, 932, 985, 1100–1103 |
| `world/phys/shapes/CollisionContext.java` | 24–30 | 24–30 |
| `world/phys/shapes/EntityCollisionContext.java` | 68–70 | 68–70 |
| `world/level/CollisionGetter.java` | 96–106 | 96–106 |
| `world/level/BlockCollisions.java` | 68–104 | 68–104 |
| `world/entity/Entity.java` | 731, 1077–1097, 1131–1161 | 738, 1141–1161, 1195–1229, 1244 |

`Blocks` uses the turtle-egg registry key and the same collision-enabled properties. `noOcclusion()` sets `canOcclude` false and leaves `hasCollision` true. No positional offset is configured. `BlockBehaviour.getCollisionShape` calls state `getShape` for collidable blocks, and state shape dispatch reaches the TurtleEggBlock override. The context-free cache also records the provider's result per state.

A player AABB moving into either of these partial blocks can encounter this geometry during normal `Entity.move → collide → collideBoundingBox → collider collection → CollisionGetter.getBlockCollisions`. The player takes the default `EntityCollisionContext` branch, which delegates to the matching block state. The iterator moves the partial voxel shape to the block position and yields it when it intersects the query shape. The same supplier is reachable via the step-up collider collection. This establishes movement applicability to the provider; broader changes to the moving entity's collection, movement clipping or world-border handling belong to separate slices.

## Coverage and disposition

Examined the registered block, state domain and default, both provider branches, helper math, inherited collision dispatch and cache, and the movement query connection. Dependencies for this state-to-local-geometry verdict are closed. Egg production, destruction and hatching, rendering, and general movement changes are outside this slice. No independent difference to draft or implement. No launch, build or runtime parity check.

