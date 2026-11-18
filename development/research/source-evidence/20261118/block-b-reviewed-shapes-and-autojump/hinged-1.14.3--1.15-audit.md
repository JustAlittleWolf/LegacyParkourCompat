# COMPLETED — blind source audit: ordinary block collision, 1.14.3→1.14.4→1.15

## Verdict

For the bounded families **Slab, Stair, Iron Door, Iron Trapdoor, Fence Gate**, no occupied-region or state-selector change was found on either edge for a fixed vanilla state during ordinary dry, loaded player collision. The state-to-shape semantics are preserved across the Yarn→Mojmap naming transition. The move reader/cache routing changes names but retains the same ordinary collision-shape lookup and static-state cache semantics. No independent difference is accepted for this slice.

This is a family-bounded static source verdict, not full-pair coverage or runtime parity.

## Evidence identity and integrity

- 1.14.3 Yarn: `decompiled_minecraft/1.14.3/yarn`; embedded `.decompile-provenance.json` SHA-256 `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`; mapping input recorded as Yarn `1.14.3+build.13`.
- 1.14.4 Mojmap: `decompiled_minecraft/1.14.4/mojmap`; `mojmap.sources.sha256` SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`.
- 1.15 Mojmap: `decompiled_minecraft/1.15/mojmap`; `mojmap.sources.sha256` SHA-256 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`.

For each assigned family, the full block source files were read and their SHA-256 values matched the source manifest entry (1.14.3 embedded source table; 1.14.4/1.15 `.sources.sha256`). Entity and block-state source files used below likewise matched their manifest entries. This pins intact whole-file bodies, not merely matching member names.

## State selectors and occupied collision regions

All dimensions below are block-local coordinates in sixteenths; `y` is vertical. For 1.14.3, the block override is named `getOutlineShape`, but `Block.getCollisionShape` delegates to that state outline by default when the block is collidable. In 1.14.4 and 1.15 the corresponding override is `getShape`, with the same default collision delegation. Fence Gate explicitly overrides collision separately from outline in all three versions.

| Family | Fixed-state selector | Collision region |
|---|---|---|
| Slab | `TYPE`: DOUBLE/TOP/BOTTOM | DOUBLE `[0,16]×[0,16]×[0,16]`; TOP `[0,16]×[8,16]×[0,16]`; BOTTOM `[0,16]×[0,8]×[0,16]`. `WATERLOGGED` does not select shape. |
| Stair | `HALF`, `SHAPE`, `FACING`; selected table index is `SHAPE.ordinal()*4 + horizontalFacingIndex` | The selected shape is the corresponding half-slab plus 0–4 quarter-cube corner regions, unioned according to the shape-index mask. Top/bottom tables and the 16-entry selector order are preserved. `WATERLOGGED` does not select shape. |
| Iron Door | `FACING`, `OPEN`, `HINGE`; `HALF` does not select collision geometry | Closed: a 3/16-thick full-height edge plate selected by facing. Open: same plate rotated to the adjacent edge selected by facing and hinge. The four edge plates in all releases span local `y=0..16`. |
| Iron Trapdoor | `OPEN`, `HALF` when closed; `FACING` when open | Closed: full-footprint 3/16 plate at `y=0..3` (bottom half) or `13..16` (top half). Open: full-height 3/16 plate at the cardinal edge selected by `FACING`. `POWERED` and `WATERLOGGED` do not select shape. |
| Fence Gate | Collision uses `OPEN` and `FACING`; it does **not** use `IN_WALL` | Open: empty. Closed: 4/16-thick, full-footprint bar selected by facing, with `y=0..24` (height 1.5 blocks), including `IN_WALL=true`. `IN_WALL` changes outline/occlusion height, not player collision. |

Source locations: 1.14.3 `block/{SlabBlock,StairsBlock,DoorBlock,TrapdoorBlock,FenceGateBlock}.java`, especially shape overrides at Slab 44–54, Stairs 101–108, Door 56 onward, Trapdoor 49 onward, FenceGate 48–74; shape constants at the tops of each file. 1.14.4 and 1.15 Mojmap `world/level/block/{SlabBlock,StairBlock,DoorBlock,TrapDoorBlock,FenceGateBlock}.java`; relevant overrides begin at Slab 46, Stair 104, Door 58, TrapDoor 53, FenceGate 42/64 in 1.14.4 and the same nearby lines in 1.15. Exact file identities are in the source manifests above.

## State cache and local movement reader

- 1.14.3 `BlockState.initShapeCache` installs `ShapeCache` only when `!block.hasDynamicBounds()` (lines 60–63). `getCollisionShape(BlockView, BlockPos)` returns cached `field_19360` when present (192–197); the cache constructor computes it once through `block.getCollisionShape(state, EmptyBlockView.INSTANCE, BlockPos.ORIGIN, EntityContext.absent())` (369–398).
- 1.14.4 and 1.15 `BlockState.initCache` uses the same condition, `!block.hasDynamicShape()` (1.14.4 lines 62–65; 1.15 lines 64–67). The no-context collision getter returns cached `cache.collisionShape` when present (1.14.4 lines 194–198; 1.15 lines 192–196); `Cache` computes the shape once against `EmptyBlockGetter.INSTANCE`, `BlockPos.ZERO`, and `CollisionContext.empty()` (1.14.4 lines 387–418; corresponding 1.15 lines 393–424).
- Each of these five blocks derives its collision geometry from its fixed state properties and does not opt into dynamic bounds/shape. Thus their states take the cache branch in all three releases; no cache removal or addition occurs on either edge. Cache is per block state, so differences among `TYPE`, stair shape/facing/half, door properties, trapdoor properties or gate open/facing remain represented by distinct state shapes.
- Local reader: 1.14.3 `Entity.handleCollisions` routes ordinary motion through `calculateMotionVector`, then `World.method_20812` for block-collision shapes (Entity lines 653–681); its collision state lookup uses `BlockState.getCollisionShape`. The 1.14.4/1.15 reader is `LevelReader.getBlockCollisions`, which constructs entity/empty `CollisionContext`, scans the box and queries the state collision shape; `Entity.collide` and its axis helper route through this reader (1.14.4 Entity lines 641–758; 1.15 lines 644–761). This is the ordinary local movement collision path, not outline rendering.
- The move methods retain the `requested displacement length-squared == 0` short-circuit before collision-shape resolution (1.14.3 Entity 460–462 and 653–662; 1.14.4 Entity 469–471 and 641–650; 1.15 Entity 469–471 and 644–653). A literally zero-vector move therefore does not read these block shapes; the family verdict concerns the queried shape during nonzero ordinary movement against a stationary, dry, loaded fixed block.

## Exact source member digests checked

| Release / mapping | Source members (SHA-256 matched to manifest) |
|---|---|
| 1.14.3 Yarn | `SlabBlock.java` `641a1d8e2268c5a622321f9b00f5e1b5d3b5fb7815a63656fa661184f395bea6`; `StairsBlock.java` `00b8d1a9a13b4441e10f9ad1f71b0d0de848a3e4f70e6841dca2a76272bc284d`; `DoorBlock.java` `0477cb19eaf5c9a2981bb2d35704134a74c2bd45768a2a13725b99817157342c`; `TrapdoorBlock.java` `215b4f51b1d2ce880f2373dcbba53dd4868475dc8317682efb11e7c303b41654`; `FenceGateBlock.java` `28a55fd56a1292de15545fe25b2ae802286646843cbb011769f2413213af17c6`; `Entity.java` `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| 1.14.4 Mojmap | `SlabBlock.java` `5a57ae37e77fc93074100d7e3eff1c5e23b43d8a23e0289b68ad8c9914df0d86`; `StairBlock.java` `b302c4e8e6abe8541051f09f4b502ce51927b5ad614fd214da6d30338331dac7`; `DoorBlock.java` `1280cbdebbfec5e14f4efa5b812616dab66293ccc1e4d7404c8866851ae25409`; `TrapDoorBlock.java` `286139ae1df614393a6debd68e9deba535cb67cb87659ce35a121f0b2883eb3f`; `FenceGateBlock.java` `51c17e70f96d37767d9cd077301d7f08b75d5cb3600754b1745e10b78b5c68e5`; `Entity.java` `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7`; `BlockState.java` `2bcc0167cb566df8745abbe967f0a8750038ad5a9b7843f5fb25bab714d73188`; `LevelReader.java` `1f86a029e4404b91aa532ca33ab0dbb63b085334c561ce44e0d0875aa50fdbc9` |
| 1.15 Mojmap | `SlabBlock.java` `5a57ae37e77fc93074100d7e3eff1c5e23b43d8a23e0289b68ad8c9914df0d86`; `StairBlock.java` `b273bfe6a1cd6a3fbb9e04a1ce13c28d697aa96105a6ffa1a7c511d264499788`; `DoorBlock.java` `cce2e855a0876155913a3cbc564441b3c90b56d476cb190774db0f86a8f0b241`; `TrapDoorBlock.java` `0de8aeb2981cefcbf8fb296e08401668e5fcaf3e003419bf1d45d56a06d81b86`; `FenceGateBlock.java` `a141dc6aaff85797fbec5552bd2b8499b418f05da00207b2c68e9be1bccdfd29`; `Entity.java` `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`; `BlockState.java` `77320fd1e50e58d7a3ed593ccbcf21853c97d125fccebde2e0e305dc47524f21`; `LevelReader.java` `d09aa8eaaa7f8ca8703091facb4d7e1a26c9f4bea8b99c5c8956682875241d37` |

## Limits

Covered only the five named fixed block families, their no-context ordinary collision shapes, per-state collision-shape cache selection, and the local player/entity move reader at 1.14.3→1.14.4→1.15. Excluded autojump, step behavior/results, collision callbacks, fluid behavior, placement/neighbor state transitions, dynamic/moving blocks, all other block families and state edges, and runtime testing. No game, test, TAS, Gym, server, Docker, or build process was run.
