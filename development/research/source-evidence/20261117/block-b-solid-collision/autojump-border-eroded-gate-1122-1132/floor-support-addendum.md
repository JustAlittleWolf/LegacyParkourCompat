# Floor support addendum: y=63 full-cube Stone fixture

This is a separate fixture detail for the AutoJump border witness. The parent report was left unchanged. The world contains only the support floor as a physical collider; the x=10 border wall below is the source-generated collision treatment, not a placed block.

## Exact floor and player geometry

Use ordinary vanilla `Blocks.STONE.defaultState()` at exactly these two positions in both releases:

- `(8, 63, 0)`
- `(9, 63, 0)`

In 1.12.2 this means the `StoneBlock` default `VARIANT=STONE` state. In 1.13.2 it means the plain registered Stone state; there is no waterlogging property. No other physical block with collision is present in the fixture or AutoJump query volume. In particular, the physical world state is AIR at `(10,63,0)`, `(10,64,0)`, `(10,65,0)`, and `(10,66,0)`; the border collision query substitutes synthetic full cubes at the outside-border cells instead of using the AIR collision shape. The headroom block-state checks at `(8,65,0)` and `(8,66,0)` see ordinary AIR.

The player has width 0.6 and height 1.8 in both exact sources. `Entity.setPosition` centers the box horizontally on `(x,z)` and sets its bottom to y. With z=0.5, the supported player's box at x=8.89 is `[8.59, 64, 0.20] .. [9.19, 65.8, 0.80]`; at x=8.99 it is `[8.69, 64, 0.20] .. [9.29, 65.8, 0.80]`. Its swept horizontal footprint therefore stays inside the two Stone cells' union `[8,10) × [0,1)`. Their top faces are y=64, which supports feet at y=64. The two-by-one-block floor is the minimum integer-aligned finite footprint that covers the complete player AABB at both endpoints and throughout this eastward sweep; no third floor cell is needed.

Both Stone collision providers are full cubes. In 1.12.2, `Block.FULL_BLOCK_SHAPE` is `[0,0,0]..[1,1,1]`, `Block.getShape` returns it, and `Block.getCollisionShape` returns the state shape; `StoneBlock` does not override those methods. In 1.13.2, the registered Stone is a `StoneBlock` using ordinary `Block.Properties.of(Material.STONE, MapColor.STONE)` without `.noCollision()`; `Properties.hasCollision` defaults true. `Block.getShape` returns `VoxelShapes.block()`, and `Block.getCollisionShape` returns the state shape when collision is enabled. The unit-cube bounds place each y=63 Stone top at exactly y=64 in both releases.

## Why support does not become an AutoJump hit

The AutoJump headroom preflight uses `new BlockPos(this.x, this.getShape().maxY, this.z)`, then the block above. At post-move x=8.99, feet y=64 and height 1.8 give maxY=65.8; flooring selects `(8,65,0)` and `(8,66,0)`, both AIR. The support floor is below these checks.

AutoJump forms its collision query box from y=64 to y=65.8 and grows it by `q=this.width=0.6` in x/z only. The raised ray pair is shifted by `0.51F`, so its y coordinate is 64.51, strictly above the Stone top at y=64. The y=63 full cube ends where the collision query begins. The 1.12.2 `Box.intersects` test is strict on all axes, so face contact at `block.maxY == query.minY == 64` does not add the floor box. In 1.13.2 the block-shape collision stream uses a Boolean AND overlap predicate; the y=63 cube and query share no positive-volume y interval, so the support shape is filtered out. The ray at y=64.51 is also 0.51 above the floor. Therefore no support cube can become `s` or cause an early AutoJump outcome; the first intersected raised-ray shape remains the synthetic east-border wall described in the parent report.

The separate preflight range calculation for that AutoJump query is:

- Query shape: x `[8.39,10.67]`, y `[64,65.8]`, z `[-0.10,1.10]`.
- Both source scans add one block to the floored minima and ceiled maxima: x positions 7 through 11, y 63 through 66, and z -2 through 2 (inclusive loop positions; upper bounds are exclusive after `ceil(max)+1`).
- In 1.12.2 the scan tests loaded chunks using `(x,z)` at y=64; in 1.13.2 `WorldView` checks the candidate `BlockPos`'s chunk. The scanned x positions all belong to chunk x=0. The interior scan positions include z=-1 and z=0, so chunks `(0,-1)` and `(0,0)` must both be loaded. Keeping both loaded also covers boundary candidates and the separate movement sweep query.
- The scan sees the two physical Stone floor cells at y=63, but their collision shape only touches the query bottom and is excluded as above. It reaches physical AIR positions at x=10, including the ray-path cells at z=0. At x=10 the square border with maxX=10 does not contain those block positions. The entity-center border predicate is true at x=8.99, so both releases use the synthetic border collision there despite the stored AIR state.

The ordinary movement sweep from the pre-move AABB to x=8.99 ends at x=9.29, still short of x=10. Its query may scan x=10 due the one-block block-scan padding, but that synthetic wall has no positive-volume intersection with the swept player box. The y=63 support cubes only touch the feet and do not obstruct the eastward displacement. Thus the concrete accepted dx=0.10 remains geometrically reachable with this finite Stone platform.

Do not replace this floor with bottom slabs: the specified fixture uses feet exactly at y=64 and height 1.8. A bottom slab has its top at y=63.5, changing both the feet/headroom positions and the measured AutoJump rise; that would require a new witness calculation.

## Exact source publication and hashes

Both sides are the primary checkout's read-only `decompiled_minecraft/<version>/ornithe-feather` trees, published as exact Feather generation-2 build.2 sources. Ready-marker source-manifest hashes match the local manifest files. Every SHA-256 below was recomputed from the named source file and matched that release's `ornithe-feather.sources.sha256` entry.

| Release | Feather mapping input | Source manifest SHA-256 | Exact client SHA-256 |
|---|---|---|---|
| 1.12.2 | `feather-gen2-1.12.2+build.2-mergedv2.jar` plus `feather-gen2-1.12.2+build.2.tiny` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f` |
| 1.13.2 | `feather-gen2-1.13.2+build.2-mergedv2.jar` plus `feather-gen2-1.13.2+build.2.tiny` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` |

| Release | Source path | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/Blocks.java` | `8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e` |
| 1.12.2 | `net/minecraft/block/StoneBlock.java` | `49358cc69e314af3952211266696dd45b48a98eb37dacf93e32c59fdff758d22` |
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.12.2 | `net/minecraft/util/math/Box.java` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |
| 1.12.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` |
| 1.13.2 | `net/minecraft/block/StoneBlock.java` | `4a85d515f1195600042d7e67de00767c77c91c2e74aa00ac7796ceb7ea78c906` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 | `net/minecraft/util/math/Box.java` | `0f573d00cc10386f94cf51671c62815979973053c9e67a806e9fee98e55a4d8d` |
| 1.13.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.2 | `net/minecraft/block/shape/VoxelShapes.java` | `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95` |

Relevant exact source locations:

- 1.12.2 `Entity.java`: dimension defaults lines 178–179; `setPosition` line 298; `getShape` line 2218.
- 1.12.2 `Block.java`: `FULL_BLOCK_SHAPE` line 52; full default shape line 307; collision shape line 387; strict `addCollision` call/filter lines 372–379; Stone registration line 748. `StoneBlock.java`: default state variant STONE line 23.
- 1.12.2 `Box.java`: strict `intersects` line 249.
- 1.12.2 `LocalClientPlayerEntity.java`: AutoJump headroom checks lines 884–891; query and +0.51 ray offset lines 901–902; world collision list line 910.
- 1.12.2 `World.java`: padded scan bounds lines 965–970; loaded-chunk gate line 982; block collision provider line 1001; border replacement lines 988–1000.
- 1.13.2 `Entity.java`: dimension defaults lines 190–191; `setPosition` line 309; `getShape` line 2246.
- 1.13.2 `Block.java`: default full shape line 389; collision shape line 393; `Properties.hasCollision=true` line 1901; Stone registration line 778. `Blocks.java`: Stone lookup line 626. `StoneBlock.java` has no collision-shape override.
- 1.13.2 `Box.java`: strict box `intersects` line 192; `VoxelShapes.java`: BooleanOp overlap line 140.
- 1.13.2 `LocalClientPlayerEntity.java`: AutoJump headroom lines 909–916; query and +0.51 ray offset lines 926–927; flattened collision shapes line 935.
- 1.13.2 `WorldView.java`: padded scan bounds lines 112–117; loaded-chunk/shape selection lines 130–146; overlap-filtered block collision output through line 153.

Source-only geometry review; no implementation, game run, test, or build was performed.
