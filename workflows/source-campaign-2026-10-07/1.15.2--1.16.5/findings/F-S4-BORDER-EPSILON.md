# F-S4-BORDER-EPSILON: lazy collision query drops the border shape within epsilon of its integer boundary

- Older version A: 1.15.2, Mojmap
- Newer version B: 1.16.5, Mojmap
- Mechanic / coverage slice IDs: collision query world-border candidate; S4-BORDER-GUARD, S4-QUERY
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: an entity-sourced block-collision query where the entity AABB is strictly inside the integer collision interior but within `1.0E-7` of one border face, and no other nonempty collision shape masks the query result
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap`; `net/minecraft/world/level/CollisionGetter.java`, lazy border check in `getBlockCollisions`, lines 78-90, SHA-256 `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd`. It emits the border shape when the entity AABB does not intersect the border after `deflate(1.0E-7)` but does intersect after `inflate(1.0E-7)`.
- B manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap`; `net/minecraft/world/level/CollisionSpliterator.java`, `worldBorderCheck`, `isOutsideBorder`, `isCloseToBorder` and `isBoxFullyWithinWorldBorder`, lines 101-135, SHA-256 `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297`. It first requires `!isBoxFullyWithinWorldBorder` before the matching deflated/inflated predicates can emit the border shape.
- A `WorldBorder.java` extent collision-shape methods, lines 330-344 and 439-441, SHA-256 `5030e47d299d2741339dae0274dab41e63ae1eb1468171ef14f181a71a03086f`; B corresponding methods, lines 324-338 and 530-532, SHA-256 `2925df8d985d0ec2d075d4f5c57538dcc628528b72a1d35e90af27fe4fbadb2a`. Both collision shapes are the complement of the box whose horizontal bounds are `floor(min)` and `ceil(max)` for the world border.
- A `CollisionGetter.noCollision` reduces the collision stream with `allMatch(VoxelShape::isEmpty)`, and A `Player.maybeBackOffFromEdge` reaches it in the support probes; source hashes are CollisionGetter `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd` and Player `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`.
- B `CollisionGetter.noCollision` likewise reduces the collision stream, and B `Player.maybeBackOffFromEdge` reaches it from the support probe; source hashes are CollisionGetter `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac` and Player `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`.

## Source-level difference

Let a collision-border face be at an integer coordinate and place the entity AABB strictly inside that face, with its nearest face less than `1.0E-7` from the integer boundary. The AABB and its deflated form remain strictly inside the border collision box, while the inflated form intersects the border shape. A therefore emits the nonempty border shape. In B, `isBoxFullyWithinWorldBorder` is true because every AABB bound remains strictly between the same floor/ceiling coordinates used to construct the collision shape; the new guard skips both later tests and emits no border shape.

The two `noCollision` helpers reduce this stream directly to a boolean. With no other intersecting nonempty shapes, the same supplied entity/query state can therefore produce `false` in A and `true` in B. This finding is limited to the lazy entity-sourced query path and the epsilon-near boundary condition; it does not claim a final displacement.

## Reachability and dependencies

The exact endpoint sources show the query reachable from Player edge-support collision probes. The A/B `WorldBorder` extent implementations establish that the additional B guard uses the same integer bounds as the returned border collision shape. The direct `Entity.collide` border stream is a separate path and is not used to dismiss this standalone `noCollision` query difference. The broader collision-provider inventory and all other player movement call sites remain open under S4-QUERY/S5-SHAPES.

## Consequence and uncertainty

At the stated input, A includes the nonempty world-border shape in a direct player collision query and B omits it. Whether the boolean difference changes a specific movement branch depends on the caller's AABB, other collision shapes and predicate. No trajectory is claimed. The first release boundary within the endpoint interval is not identified, and runtime validation was not performed.

## Handoff

Independent source delta: B's broad fully-within guard suppresses A's epsilon-near border candidate in entity-sourced collision queries. Related finding IDs: F-S2-EDGE. Runtime validation was not performed.
