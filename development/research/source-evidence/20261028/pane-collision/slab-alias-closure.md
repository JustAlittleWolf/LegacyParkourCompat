# Slab alias and replacement witness closure

Scope: exact-source alias coverage for the accepted two-bottom-slab replacement witness. This closes the alias/state-shape dependency for the named releases and records the 1.13.0 collector boundary; it does not establish ordinary-input reachability or runtime parity.

## Result

- **1.12.2 aliases:** the single slab registrations use four class families (`SingleStoneSlabBlock`, `SingleWoodenSlabBlock`, `SingleRedSandstoneSlabBlock`, and `PurpurSlabBlock.Single`). Each is a single slab over the common `SlabBlock`; its bottom state resolves to the same `[0,0,0]..[1,0.5,1]` box. Thus the accepted primitive-box witness is not specific to the `stone_slab` registry entry: any two of these bottom single-slab states meet the same geometry and the same ordered primitive collision clipping, subject to the original scene assumptions.
- **1.13.0 (`1.13` source ID):** the same-bottom-half geometry exists for its registered aliases, but the accepted *two separate shapes* replacement result does not carry over. `WorldView.getBlockCollisions` unions non-full shapes and optimizes the aggregate before returning one `VoxelShape`; adjacent bottom slabs form one contiguous collision span. `Entity.move` clips against that aggregate once. The solver helper's `abs(d) < 1.0E-7` check is on the incoming requested distance; with the witness's initial `d=2.0`, it does not turn the later clipped sub-epsilon gap into zero. The exact 1.13.0 source therefore predicts the positive gap remains (about `4.9999999918171056e-8`) for the two-slab setup, rather than the accepted 1.13.1+ zero result. This is a source-derived solver result, not a gameplay/runtime test.
- **1.13.1 and 1.13.2:** all registered aliases use the unified `SlabBlock`, whose BOTTOM state has the same half-height shape. Their `WorldView.getBlockCollisions` returns an ordered stream of individual non-full shapes and a separate accumulator for shapes identical to the full-block singleton. Hence the two bottom slabs remain two stream elements, and the accepted strict epsilon-on-next-element result applies across these aliases. The 1.13.0 aggregate behavior must not be projected onto these later releases either; the collector design changes at 1.13.1.

The 1.12.2 registration families cover the legacy `stone_slab`/`double_stone_slab` pair (metadata variants include stone, sandstone, cobblestone, brick, stone brick, nether brick and quartz), `wooden_slab`/`double_wooden_slab` (wood variants), `stone_slab2`/`double_stone_slab2` (red sandstone), and `purpur_slab`/`purpur_double_slab`. The 1.13.0 registration list contains 19 single-slab keys: `prismarine_slab`, `prismarine_brick_slab`, `dark_prismarine_slab`; `oak_slab`, `spruce_slab`, `birch_slab`, `jungle_slab`, `acacia_slab`, `dark_oak_slab`; and `stone_slab`, `sandstone_slab`, `petrified_oak_slab`, `cobblestone_slab`, `brick_slab`, `stone_brick_slab`, `nether_brick_slab`, `quartz_slab`, `red_sandstone_slab`, `purpur_slab`. Registry identifiers/materials differ; the state geometry and collision collector paths cited below are common.

## Exact source evidence

All source paths are relative to `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<release>\ornithe-feather\`.

### 1.12.2 — Ornithe Feather

- `net/minecraft/block/Block.java:837-840,1050-1053,1169-1176,1274-1278`: legacy single/double registrations resolve to the four single families listed above. The stone slab metadata family and wood variants share their respective single classes; red sandstone and purpur have their own single classes.
- `net/minecraft/block/SlabBlock.java:18-21,38-45`: the common base defines bottom `[0,0,0]..[1,0.5,1]` and selects it for non-top single slabs; double slabs use the full block.
- `net/minecraft/block/StoneSlabBlock.java:24-34,72-80,98-101`, `WoodenSlabBlock.java:18-29,91-93`, `RedSandstoneSlabBlock.java:20-34,103-105`, and `PurpurSlabBlock.java:17-29,62-64,81-91`: each family initializes its non-double default to `HALF.BOTTOM` and identifies its single variant as non-double. `SingleStoneSlabBlock.java:3-7`, `SingleWoodenSlabBlock.java:3-7`, and `SingleRedSandstoneSlabBlock.java:3-7` return false from `isDouble`; `PurpurSlabBlock.Single` does likewise at `:88-92`.
- `net/minecraft/block/Block.java:371-389`: collision collection translates the state collision box and retains intersecting boxes individually. `net/minecraft/world/World.java:964-1010` visits X in ascending order. `net/minecraft/entity/Entity.java:570-585` clips X across the collected list, and `net/minecraft/util/math/Box.java:185-201` clips to the positive gap without a `1.0E-7` zeroing gate.

### 1.13.0 — Ornithe Feather (`versionId: 1.13`)

- `net/minecraft/block/Block.java:1411-1413,1568-1583`: all 19 named slab keys register a `SlabBlock` instance (the legacy `stone_slab` key is among them).
- `net/minecraft/block/SlabBlock.java:24-32,51-60`: the unified class defaults `HALF` to BOTTOM; BOTTOM uses `(0,0,0)-(16,8,16)`, the half-height block shape.
- `net/minecraft/world/WorldView.java:112-187,205-212`: the collision collector accumulates non-full collision shapes with `joinUnoptimized` (including the per-X aggregate), then calls `optimize()` and returns one aggregate voxel shape, with full blocks separately represented in an integer voxel accumulator. This differs from 1.13.1's stream contract below.
- `net/minecraft/block/shape/VoxelShape.java:70-78,95-101` and `net/minecraft/block/shape/VoxelSet.java:234-284`: `optimize()` rebuilds the shape from its boxes; voxel-set box extraction merges adjacent occupied cells. For the witness's two aligned bottom slabs, the aggregate has continuous occupancy from X=2 through X=4 at the slab's half-height.
- `net/minecraft/entity/Entity.java:580-594`: `move` requests one `VoxelShape` and clips each axis against it.
- `net/minecraft/block/shape/VoxelShapes.java:207-218,220-255`: the axis-distance helper checks the epsilon against the incoming `d`, then searches/clips against the shape and returns the clipped value; there is no post-clip epsilon return. Starting at `d=2.0`, the first face clips to the small positive gap.

### 1.13.1 and 1.13.2 — Ornithe Feather

- `net/minecraft/block/Block.java:1392-1394,1549-1564` and `net/minecraft/block/Blocks.java:764`: all 19 named aliases are registered with the same `SlabBlock` provider; `stone_slab` is resolved from its registry key.
- `net/minecraft/block/SlabBlock.java:24-32,51-61`: BOTTOM default and half-height bottom shape are unchanged from 1.13.0.
- `net/minecraft/world/WorldView.java:112-151` and `:157-177`: the collector now returns a stream, preserving each non-full collision shape individually; only `VoxelShapes.block()` is stored in the separate `IntVoxelShape` accumulator. `net/minecraft/entity/Entity.java:580-594` consumes this stream through `VoxelShapes.calculateMaxOffset`; `net/minecraft/block/shape/VoxelShapes.java:194-205` zeroes the already-clipped gap when another stream element remains.

## Identity and integrity

Ready-marker mapping identities, client hashes, and source-manifest hashes were checked against the canonical release directories. Every source file cited/used for this closure was recomputed and matched its own `*.sources.sha256` entry.

| Release ID | Mapping | Client SHA-256 | Source-manifest SHA-256 |
|---|---|---|---|
| 1.12.2 | `ornithe-feather` (Feather gen2 1.12.2 build 2) | `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.13 (`1.13.0`) | `ornithe-feather` (Feather gen2 1.13 build 2) | `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |
| 1.13.1 | `ornithe-feather` (Feather gen2 1.13.1 build 2) | `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |
| 1.13.2 | `ornithe-feather` (Feather gen2 1.13.2 build 2) | `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |

Selected source SHA-256 values (all matched their release's manifest):

| Release | Source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/SlabBlock.java` | `dc510f95b373bc2eccd128fbc82e2f07babc658d8446ab1dbc4e5dd4f27293e0` |
| 1.12.2 | `net/minecraft/block/StoneSlabBlock.java` | `00562d8697bf21950356a54b01d5cc13310623a672330df4105261e539d362c4` |
| 1.12.2 | `net/minecraft/block/SingleStoneSlabBlock.java` | `3b3808a68671d1f559f20ac6cc71a367223d50e9c646dbf470f2da3a503cca98` |
| 1.12.2 | `net/minecraft/block/WoodenSlabBlock.java` | `a9c768c41157611d357596923dfc44a7b3688a772ebfaa44ba36d69cd8db30ed` |
| 1.12.2 | `net/minecraft/block/SingleWoodenSlabBlock.java` | `367dbdd8ff807d45734d4074ac236dcdd8f7ceb75324eb4e9dad0ac56b11d4d9` |
| 1.12.2 | `net/minecraft/block/RedSandstoneSlabBlock.java` | `1a8b040422bc9c96a5a5428d953ca2ac1d3996c4335c6429ebee2070a5ed9adb` |
| 1.12.2 | `net/minecraft/block/SingleRedSandstoneSlabBlock.java` | `80782d5bb0d60bc09e895efa4f09be5b321764f9e9f2dd7d2ad68ab192bb3b8a` |
| 1.12.2 | `net/minecraft/block/PurpurSlabBlock.java` | `0a49f331e58ce2b75153dc36c38179ede0a927a9e92e3f91f8bdc859bd304e28` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.12.2 | `net/minecraft/util/math/Box.java` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |
| 1.13 | `net/minecraft/block/Block.java` | `e5be72b77a28e89595009289964bb031bbb187d15d5698570615e6fc9a21a476` |
| 1.13 | `net/minecraft/block/SlabBlock.java` | `2e7ff3fcfa5faa83aa4af7ea9b68f07af8d8fe7047e08ee3a9d7d37b78c7128f` |
| 1.13 | `net/minecraft/world/WorldView.java` | `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543` |
| 1.13 | `net/minecraft/block/shape/VoxelShapes.java` | `6ae1a6fa0ab76098252208fad00a6d4f3d3f1cf2023d76f63d450855df00a330` |
| 1.13 | `net/minecraft/block/shape/VoxelSet.java` | `c450bdcdbf1e0f4fa4bf5675ee6f8f08d03bb73f63eeb35f0f622b6799aeca65` |
| 1.13 | `net/minecraft/entity/Entity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` |
| 1.13.1 / 1.13.2 | `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` |
| 1.13.1 / 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.1 / 1.13.2 | `net/minecraft/block/SlabBlock.java` | `2e7ff3fcfa5faa83aa4af7ea9b68f07af8d8fe7047e08ee3a9d7d37b78c7128f` |
| 1.13.1 / 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.1 / 1.13.2 | `net/minecraft/block/shape/VoxelShapes.java` | `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95` |
| 1.13.1 / 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |

The present `.minecraft-decompile-cache` does not contain the exact release artifact directories referenced by provenance metadata; cache inputs were therefore not independently reopened. Ready markers, source manifests, and the selected source files were present and hash-consistent. No source was generated or modified; no tests, builds, or runtime/game checks were run.
