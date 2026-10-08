# Powder snow adds an entity-dependent collision shape

- Older version A: `1.16.5`
- Newer version B: `1.17.1`
- Mechanic / coverage slice IDs: `S4-03-POWDER-SNOW-SHAPE-CONSUMER`, `S5-POWDER-SNOW`
- Classification: added collision geometry
- Confidence: source-confirmed
- Applicability: modern-only mechanic
- First changed release: unknown within (1.16.5, 1.17.1]
- Runtime validation: not performed

## Paired evidence

- A has no Powder Snow block class or registry entry. A's `world/level/CollisionGetter.java` calls `BlockState.getCollisionShape(...)` with the supplied entity collision context; SHA-256 `B507D6BE11E5985A62CFEB249A99DCB5F8EDAF346F12CB2487797D9E01763EAC`. A's `Entity.collide(Vec3)` consumes block collision shapes as recorded in the run manifest.
- B `world/level/block/PowderSnowBlock.java`, `getCollisionShape(...)`, lines 87–102, SHA-256 `4069B3EF70E2D0CE146BBFF79BE54B2BE99B7C7B32818C1EB45F11FD66F99AA3`: it returns `FALLING_COLLISION_SHAPE` when the entity's fall distance exceeds `2.5F`; otherwise it returns the superclass shape for a falling-block entity or for an entity that can walk on Powder Snow when the context is above the block and not descending. Other cases return `Shapes.empty()`.
- B `PowderSnowBlock.canEntityWalkOnPowderSnow(Entity)`, lines 111–116, accepts the `POWDER_SNOW_WALKABLE_MOBS` tag or a `LivingEntity` wearing leather boots in the feet slot. The B client tag resource and hash are recorded in the run manifest.
- B `world/level/CollisionGetter.java` calls `BlockState.getCollisionShape(...)` with the entity context; SHA-256 `EB707479E75CF200731DF4546A546FB984BE33CF3E4A8E17D3065A916497F747`. The paired `BlockState.java` hashes are also recorded in the run manifest. `Entity.collide(Vec3)` consumes the resulting block shapes through the paired collision path indexed under S4-03.
- B `world/level/block/Blocks.java` registers `POWDER_SNOW`; its source hash is recorded in the run manifest. The A registry has no such entry.

## Source-level difference

The shared entity collision pipeline asks each block state for a collision shape using the moving entity's collision context. B's newly registered Powder Snow block supplies a shape conditionally: high-fall entities receive the falling shape; entities allowed to walk on it receive the superclass shape only while above and not descending; otherwise the block is non-colliding. A has no corresponding block or shape provider.

## Reachability and dependencies

`Entity.move` reaches `Entity.collide`, which requests block collision shapes through `CollisionGetter`; the generic consumer correspondence was checked on both sides and is recorded in the run manifest. For a player with leather boots, the B predicate permits the full block shape in the above/non-descending case. This affects the shape used when the player's movement is clipped. The separate Powder Snow contact callback and climb-assist behavior have their own findings.

## Consequence and uncertainty

The source confirms the shape-selection predicates and that the player collision path consumes the returned shape. It does not establish a complete trajectory or every interaction with falling, stepping, or contact callbacks. Powder Snow did not exist in A, so this modern-only geometry must not be added to A-era maps.

## Handoff

This finding closes only the Powder Snow shape provider's correspondence to the shared shape consumer. The general S4-01 entity collision and axis-clipping comparison remains pending.
