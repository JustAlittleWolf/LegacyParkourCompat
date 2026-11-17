# Stairs multipart clipping-order dependency

Pair: Minecraft 1.8.9 → 1.9, Ornithe Feather exact sources. This report closes only the per-StairsBlock collision-box ordering dependency. It does not audit the whole provider inventory or freeze the pair.

## Source identity

- 1.8.9 publication contract: `ornithe-feather.sources.sha256` SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; provenance SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`.
- 1.9 mapping-local `.decompile-provenance.json` SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.
- Paired source file hashes (all rechecked against the exact source sidecars):
  - `block/StairsBlock.java`: 1.8.9 `FADC964480227F6FFD5756471212B18D0172BFFBCD0866EE0C278454AB079409`; 1.9 `19E43095405BB61EEC8C9529922384C97EB6645B28F5007D3652FEE9F19FAA98`.
  - `block/Block.java`: 1.8.9 `EA10F05106A3CF7189AEC85236A7ECF9C7106A717F37BED583B5EA4ADEFFA528`; 1.9 `91C053E11D6BEDAAC292B6BE2F5670E5A5C4522F175C1E227163EEB5499780B2`.
  - `world/World.java`: 1.8.9 `3C04F5B874FBB6692039164C882A3C47FE28C7ECA864E91C9AC57798153888EE`; 1.9 `D1E23DB2E20C86EB37B964A0DF6838F1422ADE7B0C2FFF9C869FF8345979AA98`.
  - `entity/Entity.java`: 1.8.9 `D4C10932CB5BB1067A5A58BE4E1BB1B42BDDE3B6FC893D88178CC07435A1696B`; 1.9 `74D3AC34D7E6355C8FDCDD777DCD95BA9EF4626718C8772C1B58D0E28419E42A`.
  - `entity/living/LivingEntity.java`: 1.8.9 `082831C6578E3A70FA6CEA5B90BC3EEFC26678259B66334470DE22B90B5B0E4E`; 1.9 `5B5936656AACC64A4D75DB7B370985F9456AC96076331DA047BD246F76A705FB`.

## Provider emission and order

In 1.8.9, `StairsBlock.addCollisions` (lines 384–394) sets and emits the base half-box first. `isInnerStair` (216–295) then sets the main step box and returns whether the geometry is straight/inner rather than an outer corner; that box is emitted second. Only when that result is true and `setStepShapeForCollisions` succeeds (297–378) is the corner-quarter box emitted third. The block's mutable bounds are reset after the calls.

In 1.9, `StairsBlock.addCollisions` (66–72) resolves `SHAPE` from the state/world neighborhood, then iterates `getCollisionShapes` (74–87). That method emits the base half-box first; it emits the main half-footprint step for `STRAIGHT` and `INNER_LEFT/RIGHT`; it emits the corner-quarter step for every non-straight shape. Thus the ordered outputs are:

| Resolved stair kind | 1.8.9 emitted sequence | 1.9 emitted sequence |
| --- | --- | --- |
| Straight | base, main step | base, main step |
| Outer left/right | base, outer quarter | base, outer quarter |
| Inner left/right | base, main step, inner-corner quarter | base, main step, inner-corner quarter |

The old `resolveVirtualProperties` (StairsBlock lines 554 onward) and collision helpers classify neighboring same-half stairs; the new resolver and `getStairShape` (309–338) do so from the queried state and neighboring states. This report's order comparison is conditional on both sides resolving the same valid state-local stair kind and corresponding box coordinates. It does not assert equivalence for arbitrary inconsistent state/world inputs.

## Collision collection and clipping consequence

The base `Block.addCollisions` contract appends only boxes that intersect the requested movement query. In 1.8.9 (Block lines 339–350), the stair's mutable bounds form an already world-positioned box. In 1.9 (Block lines 313–328), the provider returns a local box, `addCollision` translates it by `BlockPos`, intersects it with the same query, and appends it. For matching local shapes/position/query, this preserves the stair boxes and their provider order.

The `World.getCollisions(Entity, Box)` scan itself changes: 1.8.9 (World lines 891–945) uses floor-based X/Z/Y bounds; 1.9 (893–1009) expands X/Z by one block, uses floor/ceil bounds and prunes some corner columns. Therefore the complete world collision list may contain other boxes in a different set/order near query boundaries. This report closes only the StairsBlock-local subsequence; it does not close ordering effects from other providers or the changed scan bounds.

The shared `Entity.move` resolver is used by `PlayerEntity`, which extends `LivingEntity` (PlayerEntity 1.8.9 line 79, 1.9 line 91), and `LivingEntity` calls it from travel (1.8.9 lines 1165–1218; 1.9 lines 1328–1435). In both releases, ordinary movement clips the requested Y against the returned list in order, moves Y, then clips/moves X, then clips/moves Z (Entity 1.8.9 lines 439–459; 1.9 lines 506–528). Step-up re-queries and sequentially clips its alternatives in the same Y/X/Z order before selecting a path (1.8.9 lines 460–511; 1.9 lines 529–587). With the same ordered stair boxes and same other colliders, this consumer applies the stair boxes in the same sequence; the per-stair decomposition/order therefore adds no release-specific sequential-clipping delta for that bounded input.

## Disposition and remaining gates

BOUNDED SOURCE OBSERVATION: for matching valid state-local stair kind/coordinates and the same collision query, both releases emit the stairs base/main/corner boxes in the same order, and `Entity.move` clips them in the same per-axis order. This closes the previously listed per-box clipping-order subdependency only.

Still open: complete world query membership/order where other blocks are present; independent verification of the full state-neighbor-to-coordinate correspondence across all stair directions/halves; unrelated source/provider/tick dependencies and full-pair independent audit. No trajectory, runtime, implementation, or full-pair acceptance is claimed.

