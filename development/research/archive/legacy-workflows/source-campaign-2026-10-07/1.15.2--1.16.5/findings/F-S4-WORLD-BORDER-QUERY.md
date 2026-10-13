# F-S4-WORLD-BORDER-QUERY: 1.16.5 skips the near-border shape check for interior boxes

- Older version A: 1.15.2, Mojmap
- Newer version B: 1.16.5, Mojmap
- Mechanic / coverage slice IDs: player movement collision query and world-border shape input; S4-QUERY, INV-COLLISION
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player movement collision queries with the current player AABB strictly inside the floored/ceiled world-border envelope and within `1.0E-7` of the border collision shape
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap`; `net/minecraft/world/level/CollisionGetter.java`, inline `getBlockCollisions` border check, lines 79-90; SHA-256 `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd`. For non-null entity input, A obtains the world-border collision shape, tests the entity bounding box deflated and inflated by `1.0E-7`, and yields the border shape only when the deflated box does not intersect but the inflated box does.
- B manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap`; `net/minecraft/world/level/CollisionGetter.java`, `getBlockCollisions`, lines 58-67; SHA-256 `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac`. The extracted `CollisionSpliterator` performs the border query at lines 102-130; SHA-256 `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297`. Its `worldBorderCheck` first returns without querying the border shape when `isBoxFullyWithinWorldBorder` is true. That predicate requires each AABB x/z endpoint to lie strictly between `floor(min border)` and `ceil(max border)`.
- Border shape providers: A `net/minecraft/world/level/border/WorldBorder.java`, SHA-256 `5030e47d299d2741339dae0274dab41e63ae1eb1468171ef14f181a71a03086f`; B same class, SHA-256 `2925df8d985d0ec2d075d4f5c57538dcc628528b72a1d35e90af27fe4fbadb2a`. This finding concerns the changed decision to query/yield the live shape, not a claimed difference in shape geometry.
- Paired entity-collider route checked: A `EntityGetter.java`, SHA-256 `f2c614b216f498069b0e4a033705afef96509599c42eb21a6b612387207fcd2c`; B `EntityGetter.java`, SHA-256 `12b4ad28f0a92414ec1478dd42b6a41ff6cd0a0a00650ae37490ea8cd0a1bba5`. A's empty exclusion set and B's always-true query predicate both retain only same-vehicle rejection for a Player source. A's only `getCollideBox` overrides in the entity source tree are Boat and Shulker; each returns its bounding box when eligible. B's matching `canBeCollidedWith` overrides are Boat (true) and Shulker (alive). Player inherits the base null `getCollideAgainstBox`, so the entity-candidate shape route is equivalent for Player movement on these examined subclasses. A Boat SHA-256 `4f7d74f414a3949869fdd987082b6153cf66b21ebfd25d44a642b7ae00db4047`, A Shulker `ec230631b5e0ffb7896890bd0f6ad31d54a540eff018828d6e7a93a29584c7e6`; B Boat `9f3c92c0516ab31dc42918fcedbb5894fb695a1fcc32f05e73db25784d30c056`, B Shulker `148223a61c38992efdb88f1537886efcbff4697ab4b46a18f335995a40739570`.

## Source-level difference

A tests every non-null entity's current bounding box against the border collision shape with the same `1.0E-7` deflate/inflate pair. B's `CollisionSpliterator` skips that test when the current box passes its strict floor/ceiling containment predicate. Thus, for a box passing B's predicate while A's inflated-box test intersects the border shape, A yields the border shape to collision resolution; B yields no border shape from this check. The block cursor scan preserves A's bounds, collision context, large-shape and moving-piston filters, and block-shape intersection flow.

The entity-collider API was also rewritten, but the player-facing Boat and Shulker eligibility and boxes inspected here correspond, as detailed above. The block collision resolver helpers (`collideBoundingBoxHeuristically`, legacy axis resolution and current axis resolution) have matching paired method bodies in these sources.

## Reachability and dependencies

`Entity#move` calls the shared collision resolver with a Player source, which calls the collision stream while resolving a requested movement vector. A non-null Player source activates the A and B border-check paths. The condition is restricted to the exact border-near box case; ordinary interior positions take no border collision shape on either endpoint. The full `WorldBorder` extent and AABB shape/query audit remains part of INV-COLLISION.

## Consequence and uncertainty

The source proves a changed collision-shape stream at the stated boundary condition. It does not establish a specific clipped movement vector or trajectory; the actual outcome also depends on the current border extent, request vector, block shapes and subsequent movement state. Runtime validation was not performed, and the first changed release in the endpoint interval is unknown.

## Handoff

Independent source delta: B bypasses the near-border collision-shape test for boxes passing its floor/ceiling containment check. The finding is limited to the player movement collision query and does not claim a general change to block-shape iteration or entity collider eligibility.
