# Native structure raw-state placement route

## Scope and exact inputs

Bounded source dependency check for native structure generation only: whether `StructurePiece.setBlockState` routes through neighbor-shape normalization when placing pane, wall, slab, or stair states. This does not recompare those blocks' provider geometry.

| Endpoint | Mapping/source family | Ready source-manifest SHA-256 |
|---|---|---|
| 1.12.2 | Ornithe Feather | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.13.2 | Ornithe Feather | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |

The on-disk `ornithe-feather.sources.sha256` files hash to the exact values above. Every source file listed below was checked against its endpoint source manifest and matched.

## Paired route

In 1.12.2, `StructurePiece.setBlockState(World, ...)` transforms and bounds-checks the supplied state, then calls `World.setBlockState(pos, state, 2)` (`StructurePiece.java:178–190`). A concrete native generation caller is `OverworldChunkGenerator.populateChunkAfterTerrain`: when its monument-generation gates pass, it calls `oceanMonument.place(this.world, ...)` (`OverworldChunkGenerator.java:407–411`). `StructureFeature.place(World, ...)` calls `StructureStart.postProcess` for the intersecting structure (`StructureFeature.java:64–76`; `StructureStart.java:34–42`). `World.setBlockState` reaches `WorldChunk.setBlockState`; its neighbor/comparator callback `onBlockChanged` is guarded by flag bit 1, so the structure's flags value 2 does not take that callback (`World.java:255–289`). The chunk setter does invoke the placed block's `onAdded` callback when the block type changes (`WorldChunk.java:397–466`).

In 1.13.2, `StructurePiece.setBlockState(WorldAccess, ...)` makes the same transform/bounds checks and calls `world.setBlockState(pos, state, 2)`, then schedules a nonempty fluid tick and marks selected blocks for post-processing (`StructurePiece.java:200–220`). For native decoration, `DecorateChunkTask.run` creates a `WorldGenRegion` over the chunk-generation inputs and passes it to `generator.decorate`; `AbstractChunkGenerator.decorate` passes that region to biome feature placement (`DecorateChunkTask.java:6–12`; `AbstractChunkGenerator.java:97–110`). Structure features in that feature flow call `StructureStart.postProcess` with their `WorldAccess` (`StructureFeature.java:34–55`). The `WorldGenRegion.setBlockState` override writes through `ChunkAccess.setBlockState` directly and only handles block-entity and post-processing bookkeeping (`WorldGenRegion.java:207–230`). Its `ProtoChunk` implementation stores the state in the section palette (`ProtoChunk.java:128–153`); this route does not invoke `World.setBlockState`'s neighbor-shape update sequence or `WorldChunk`'s `onAdded` callback.

The target normal-world setter does have explicit neighbor normalization: unless flag bit 16 is set, `World.setBlockState` calls old-state indirect updates, new-state `updateNeighborShapes`, and new-state indirect updates (`World.java:201–245`). `Block.updateNeighborShapes` queries six neighbors and calls their `updateShape` implementations (`Block.java:166–174`). Native structure decoration does not use that setter for its writes; flag 2 does not change this because the receiver is `WorldGenRegion`.

## Disposition

**Native world-generation dependency closed:** the target's normal `World.setBlockState` shape-normalization route is bypassed for structure-piece placements made through the generation region. The old structure path also omits the flag-1 neighbor-change callback, while its `WorldChunk` insertion invokes `onAdded`; target generation-region insertion directly writes the proto-chunk state and does not invoke that callback. Target structure code's explicit post-processing marks are not evidence of neighbor-shape normalization.

This is a world-generation state-construction path, not evidence of a player movement behavior change or of any particular pane/wall/slab/stair collision state. It establishes no applicability to externally imported maps, edited worlds, or save migration: those writers were not traced here. No blanket provider parity, movement finding, implementation readiness, or pair freeze is asserted. Those external state-origin routes remain separate dependencies if they are claimed as applicable.

## Source identity ledger

SHA-256 values below are for the exact canonical Java files under `decompiled_minecraft/<version>/ornithe-feather/` and were verified against the corresponding endpoint source manifest.

| Version | Relative Java path | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/world/gen/structure/StructurePiece.java` | `b88df83f6d1488db858444e47e26b6ec5b785f03f503674f16a1313e8f743781` |
| 1.12.2 | `net/minecraft/world/gen/structure/StructureStart.java` | `b232b7cdac39183fa1d1abd9fade913b297257c6e638b2eea73d072d22fd8108` |
| 1.12.2 | `net/minecraft/world/gen/structure/StructureFeature.java` | `7c9c5247c87131e0ad1433207285662a434e95a8b5311b4643edca5fb13ef0f9` |
| 1.12.2 | `net/minecraft/world/gen/chunk/OverworldChunkGenerator.java` | `96df9a1b15529baa5f4450d29fff50004881ae45a304029d7ed30b1ddcae400f` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 | `net/minecraft/world/chunk/WorldChunk.java` | `b80c4962661b31c9015faa9bb3d42e390528b59473956ba20e424c76625b844f` |
| 1.12.2 | `net/minecraft/world/gen/chunk/ProtoChunk.java` | `36e9bc7b1fdc6afc66d0e3b6794e67a62f94d611a4738e6aed5cb33a509948bc` |
| 1.13.2 | `net/minecraft/world/gen/structure/StructurePiece.java` | `54b35519421355317ede9879fe602f7f398a5fedd0c96fb1298d91d38a4c45ba` |
| 1.13.2 | `net/minecraft/world/gen/structure/StructureStart.java` | `74b47cdf846e7d71d8c57a5f145896898daa2582f47f5048b5bab98264581077` |
| 1.13.2 | `net/minecraft/world/gen/structure/StructureFeature.java` | `653429646fd5f10d6b0947c1658d405c7ea2d66bbfdf3ac4aa8bbf1e9e97c8a0` |
| 1.13.2 | `net/minecraft/world/gen/WorldGenRegion.java` | `a5a9d3e894a478ccc31c836a6583db5e9ed214c7f3c83399db343eae89271128` |
| 1.13.2 | `net/minecraft/world/chunk/DecorateChunkTask.java` | `67a54c1a024858e1556c26445b74c0b5b0a8210bfe15e4ae38aeae2860f4f7a6` |
| 1.13.2 | `net/minecraft/world/chunk/AbstractChunkGenerator.java` | `b4b4411b97d9ad73a429c9764cd53e071efe4394bf14ae284e8edf4593e4ec12` |
| 1.13.2 | `net/minecraft/world/World.java` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |
| 1.13.2 | `net/minecraft/world/chunk/ProtoChunk.java` | `e8b3af119b20b2c404fefb5f65dac74981ecd942cac19decadcbfc25da3057dc` |
| 1.13.2 | `net/minecraft/world/chunk/ChunkAccess.java` | `07b26050bb38ff9a2e879cb63d1ebfe7e1f9ebd16d9661a186cc9fb98270ee95` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
