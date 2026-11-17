# Snow layer layer-one collision candidate correction (r2)

This is a narrowly scoped correction to `../snow_layer_collision_provider/report.md` (SHA-256 `0286fec4882b27d5921fa086d183d48351f1e6a6e7c97bd6c944e24cc2ef6a93`); its independent r1 audit verdict is `../snow_layer_collision_provider_audit/verdict.md` (SHA-256 `4241afc0aa165a2eb45add70437aad366864b26cfb0466996167278d4bde4187`). That report's geometric comparison remains accurate for all eight layer states, but its disposition treating the layer-one zero-height box and target empty shape as equivalent throughout the collision dispatch was too broad. The old output is a degenerate box plane, not the empty set returned by the target provider. The old plane can still be admitted as a collision candidate.

## Exact source inputs

The source pair remains 1.12.2→1.13.2, Ornithe Feather Gen2 build 2, with exact source manifest digests 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. The prior report binds SnowLayerBlock, Block, Blocks, and collision-enumerator sources. This addendum adds these verified 1.12.2 manifest-bound sources:

| Source file | SHA-256 |
|---|---|
| `net/minecraft/util/math/Box.java` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |

Both hashes were recomputed from the canonical source files and match `ornithe-feather.sources.sha256`.

## Candidate admission and consumer condition

For `LAYERS=1`, the old provider returns a box whose horizontal extent is the full block and whose minimum and maximum Y are both the block's integer Y coordinate. `Block.addCollision` translates it to the block position and calls `Box.intersects` (old `Block.java`, lines 372–379). `Box.intersects` (old `Box.java:245–251`) uses strict overlap on each axis. Therefore this degenerate box is admitted when, and only when, the collision query has positive horizontal overlap with the block footprint and strictly straddles the block-Y plane:

`query.minX < blockX + 1 && query.maxX > blockX && query.minZ < blockZ + 1 && query.maxZ > blockZ && query.minY < blockY && query.maxY > blockY`.

The target layer-one provider returns `VoxelShapes.empty()` (`SnowLayerBlock.java:78–79`). Target `WorldView.getBlockCollisions` filters empty shapes before returning candidate shapes (`WorldView.java:132–151`), so that old candidate is absent on the target route.

Old `Entity` collision movement calls `World.getCollisions(this, this.getShape().expanded(x, y, z))` and applies each candidate through `Box.intersectY(this.getShape(), y)` (`Entity.java:567–574`). `Box.intersectY` (old `Box.java:205–222`) can consume the degenerate candidate: with strict X/Z overlap, a positive proposed Y offset is capped if the moving box's `maxY <= blockY`, and a negative proposed Y offset is capped if its `minY >= blockY`. Thus source math establishes a conditional candidate-set difference and a conditional path into old Y-offset clipping. It does not establish that a normal eligible player movement call presents these conditions, nor a resulting displacement or runtime movement outcome.

## Bounded disposition and gates

The layer-one collision provider is not fully closed as an end-to-end no-difference: source proves that some expanded query boxes can include an old zero-height candidate which the target empty-shape path omits, and that the old generic clip helper accepts that candidate under the conditions above. Whether the exact query and clip conditions are reached by an ordinary player in this pair remains unproven here. No player-reachable finding, displacement, or trajectory conclusion is asserted. Layers 2–8 retain matching collision geometry; outline geometry for layers 1–8 remains matching. Pair coverage remains partial.
