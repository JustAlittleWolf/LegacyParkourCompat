# 1.12.2 → 1.13.2 raw structure-piece block-state route

## Identity and bounded inputs

- Exact Feather Gen2 b2 endpoint source manifests: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- Residual lookup input: `build/source-residual-20261116/1.12.2--1.13.2/residual_lookup_112/coverage.md`, SHA-256 `3cb08b5715511a5c55354cceb60f06163b7d962f89b30a0c3121cb7567f9f38d`.
- Reused accepted lifecycle input: `build/source-residual-20261116/1.12.2--1.13.2/stateful_shape_lifecycle/stair-wall-state-lifecycle.md`, SHA-256 `02836adc143861993e1214fdc3ef3950e588e1bb20b7768258c63da1f1242c0f`.
- Scope is only whether the raw structure-piece block write recomputes neighbor-derived stored shape state, and its bounded reachability/context. Ordinary player placement and neighbor updates, chunk upgrade, template repair, and provider geometry are not re-evaluated here.

## Route result

In 1.12.2, `StructureFeature.place` calls `StructureStart.postProcess`, which calls piece `postProcess`; `StructurePiece.setBlockState(World, ...)` transforms the supplied state and calls `World.setBlockState(pos, state, 2)`. The flag-2 world write updates the chunk and notifies listeners, but does not take the `flags & 1` `onBlockChanged` path that performs general neighbor notifications. A changed block class can still receive its specific `onAdded` callback from `WorldChunk.setBlockState`; therefore this is not a claim that the old route suppresses every block-specific callback.

In 1.13.2, `StructureFeature.place` similarly reaches `StructureStart.postProcess` and `StructurePiece.postProcess`, so the helper remains a reachable structure-generation route. The piece helper again calls `world.setBlockState(pos, state, 2)`, but its world argument is the `WorldGenRegion` used by chunk decoration. `WorldGenRegion.setBlockState` selects a cached `ChunkAccess` and calls `chunkAccess.setBlockState(pos, state, false)` directly. On the ordinary `ProtoChunk` path this writes the section state and height maps; it does not call `World.setBlockState`, `updateNeighborShapes`, or a block `onAdded` hook. `ImposterProtoChunk.setBlockState` is a no-op returning null, so it is not evidence of an alternate propagation route. The normal `World.setBlockState` neighbor-shape path therefore does not run for this worldgen write.

This bypasses the ordinary 1.13 stored-state refresh invariant for neighbor-derived properties. It establishes a reachable route capable of preserving a caller-supplied state without the normal neighbor-shape recomputation. It does **not** establish that a registered movement-relevant provider has a concrete structure caller that supplies stale properties. The prior bounded `SwapHutPiece` inspection found explicit non-straight stair states at four roof corners but no demonstrated stale-state collision; that result is reused, not reopened. No movement displacement or vanilla stale-state difference is claimed.

## Exact remaining evidence gate

To turn this route boundary into a movement finding, an exact registered 1.12-era provider/state must be shown reachable from a concrete structure-piece call with supplied stored properties inconsistent with final neighbors, and that mismatch must alter the client player collision query. The current evidence does not name such a call/state. Until that caller/state witness exists, disposition is **raw worldgen refresh bypass confirmed; movement-relevant stale-state witness unproven**. Keep pair coverage partial; this bounded result does not freeze the pair.

## Source anchors and immutable file hashes

Line ranges refer to the pinned endpoint files below.

| Version | Source file and methods | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/world/gen/structure/StructureFeature.java`, `place` | `7c9c5247c87131e0ad1433207285662a434e95a8b5311b4643edca5fb13ef0f9` |
| 1.12.2 | `net/minecraft/world/gen/structure/StructureStart.java`, `postProcess` | `b232b7cdac39183fa1d1abd9fade913b297257c6e638b2eea73d072d22fd8108` |
| 1.12.2 | `net/minecraft/world/gen/structure/StructurePiece.java`, `postProcess`, `setBlockState` | `b88df83f6d1488db858444e47e26b6ec5b785f03f503674f16a1313e8f743781` |
| 1.12.2 | `net/minecraft/world/World.java`, `setBlockState` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 | `net/minecraft/world/chunk/WorldChunk.java`, `setBlockState` | `b80c4962661b31c9015faa9bb3d42e390528b59473956ba20e424c76625b844f` |
| 1.13.2 | `net/minecraft/world/gen/structure/StructureFeature.java`, `place` | `653429646fd5f10d6b0947c1658d405c7ea2d66bbfdf3ac4aa8bbf1e9e97c8a0` |
| 1.13.2 | `net/minecraft/world/gen/structure/StructureStart.java`, `postProcess` | `74b47cdf846e7d71d8c57a5f145896898daa2582f47f5048b5bab98264581077` |
| 1.13.2 | `net/minecraft/world/gen/structure/StructurePiece.java`, `postProcess`, `setBlockState` | `54b35519421355317ede9879fe602f7f398a5fedd0c96fb1298d91d38a4c45ba` |
| 1.13.2 | `net/minecraft/world/gen/WorldGenRegion.java`, `setBlockState` and cached chunk route | `a5a9d3e894a478ccc31c836a6583db5e9ed214c7f3c83399db343eae89271128` |
| 1.13.2 | `net/minecraft/world/chunk/ProtoChunk.java`, `setBlockState` | `e8b3af119b20b2c404fefb5f65dac74981ecd942cac19decadcbfc25da3057dc` |
| 1.13.2 | `net/minecraft/world/chunk/ImposterProtoChunk.java`, `setBlockState` | `592a59386abbf90282f07004bd31f90fee4bafd6033c180a233f21d31aa1acbc` |
| 1.13.2 | `net/minecraft/world/chunk/DecorateChunkTask.java`, region creation/call | `67a54c1a024858e1556c26445b74c0b5b0a8210bfe15e4ae38aeae2860f4f7a6` |
| 1.13.2 | `net/minecraft/world/chunk/ChunkTask.java`, region chunk collection | `5aa1777fb12a8ae3756b44bb70995ec390d8fb1979ddcf5dfeb4a83c3c5e55ca` |
| 1.13.2 | `net/minecraft/world/chunk/AbstractChunkGenerator.java`, decoration route | `b4b4411b97d9ad73a429c9764cd53e071efe4394bf14ae284e8edf4593e4ec12` |

**Verdict boundary:** source route and refresh bypass confirmed for the inspected endpoint methods; no concrete affected provider/state caller or movement delta established. No accepted independent audit is claimed by this author packet.
