# Double-chest state reconstruction lifecycle: 1.12.2 → 1.13.2

## Bounded verdict

**Closed for legal 1.12.2 adjacent same-kind chest pairs loaded by a 1.13.2 world.** Data fixing initially converts each old chest state to a target `type=SINGLE`, but the same chunk migration explicitly records old chest positions for `UpgradeData`. Interior pairs are repaired by `UpgradeData`'s chest fixer; chunk-edge pairs are deferred to its side pass until the full eight-chunk neighborhood is available. A 1.13.2 server sends a chunk only after its `WorldChunk` reports populated, which requires `POST_PROCESSED`; that status is set by the method that runs `UpgradeData.upgrade`. Thus, the client-player collision query receives reconstructed `LEFT/RIGHT` states. This closes the prior gate for this legal migrated-pair path; it does not assert behavior for malformed or manually authored inconsistent modern states.

No persistent shape mismatch or trajectory consequence is established. The prior provider comparison reports equal cuboids for aligned directions. The life-cycle evidence resolves whether converted legal doubles have aligned directions before the client can query them.

## Exact route and evidence

1. `AnvilChunkStorage.upgradeChunkNbt` runs `NbtUtils.fix(... DataFixTypes.CHUNK ...)` on disk chunks on read (`1.13.2`, `AnvilChunkStorage.java:82–107`). It then creates the `WorldChunk` from the fixed chunk NBT (`AnvilChunkStorage.java:122–131, 164–190, 455–493, 672–690`). The endpoint manifests bind these as exact Feather sources.
2. The chunk data-fix pipeline includes `ChunkPalettedStorageFix`: `C_36170522.makeRule` dispatches its chunk rewrite through `m_10744737` (`C_36170522.java:407–418`). The block-state lookup maps old `minecraft:chest` facing states to `minecraft:chest` with `type:'single'` (`C_80754635.java:1604–1607`); trapped chest does the same (`6033–6042`).
3. While converting legacy palette blocks, the fixer marks old block ID 54 as a block requiring neighbor postprocessing (`C_36170522.java:467`). For each legacy block at a non-edge position it records the local index (`621–625`); edge positions instead contribute side bits (`621–623`). It serializes those indices and sides under `UpgradeData` along with the converted sections (`955–966`). This applies to both chest and trapped chest legacy states represented by ID 54/146 in the side-update bitset (`467–468`); ID 146 is trapped chest.
4. `UpgradeData.upgrade` runs the inside pass and then each marked side pass (`UpgradeData.java:66–71`). The `CHEST` fixer registered for both `Blocks.CHEST` and `Blocks.TRAPPED_CHEST` pairs adjacent states only when the block instances match, both are `SINGLE`, both face the same direction, and the adjacency is horizontal on the perpendicular axis. It writes the complementary neighbor type and returns the current block's `LEFT` or `RIGHT` type (`242–261`). Interior pair records therefore reconstruct the pair while processing the first endpoint. Boundary positions are not dropped: their side bit causes `upgradeSides` to inspect the boundary after neighbors are available (`UpgradeData.java:75–112`), and `upgradeBlock` dispatches to the same chest fixer (`94–96`).
5. `WorldChunk.doPostProcessing` requires `neighbors == 8`; it performs queued block postprocessing, sets status to `POST_PROCESSED`, then calls `upgradeData.upgrade(this)` (`WorldChunk.java:1062–1093`). `WorldChunk.addToCache` schedules that method on the world event loop once all eight neighbors are counted (`1148–1155`). `ChunkMap.put` counts the 3×3 neighborhood for adjacent cached chunks (`server/world/chunk/ChunkMap.java:17–29`).
6. Chunk delivery is gated on that lifecycle: `ChunkHolder.populate` returns without sending while `chunk.isPopulated()` is false (`server/ChunkHolder.java:84–101`); `WorldChunk.isPopulated` is `status.isAtLeast(POST_PROCESSED)` (`WorldChunk.java:804–805`). The only shown initial and later full-chunk send paths are in `populate`/`sendChunk`, with `sendChunk` also requiring `this.populated` (`ChunkHolder.java:101–114`). `server/ChunkMap.tick` retries loading/populating holders (`server/ChunkMap.java:72–73, 117, 135`). The status assignment and upgrade call execute in the same `doPostProcessing` event-loop task, so holder population does not observe an intermediate status during that method on the server thread.
7. The already-read provider coverage establishes the player collision consumer and provider facts: old 1.12.2 provider reads current same-kind horizontal neighbors; the 1.13.2 provider reads stored `TYPE`/`FACING`; player movement reaches block collision shapes. It also records the corresponding normalized bounds. See the prior coverage file hash below.

## Input identity and source hashes

Mapping/source family: Feather Gen2 (Ornithe Feather). Manifest SHA-256 values exactly match the assigned IDs:

- 1.12.2 `ornithe-feather.sources.sha256`: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`
- 1.13.2 `ornithe-feather.sources.sha256`: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`
- Prior gate read, `build/source-residual-20261116/1.12.2--1.13.2/chest_shape_provider/coverage.md`: `3ae1bc28a668f58f06a2ecb4338b93c35334853b956bcf1b861c9560123c8d52`

SHA-256 per source file (all checked against each Feather manifest):

| Release | Source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/ChestBlock.java` | `cc63486b970a8a7670910b9d4e24c8de4b616dc53c9ce2f01260cfad53a5e178` |
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/state/BlockState.java` | `3fb6f06df86c3b89716b5efaed5d97eee670d007d3c4535e47eb0fdd993e92eb` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.12.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| 1.13.2 | `net/minecraft/block/ChestBlock.java` | `483ed7dc673f8acb33fbac6d5d883c652db05de2314931c522e0bee482792541` |
| 1.13.2 | `net/minecraft/block/TrappedChestBlock.java` | `ce2db33ed4b4e5dec67d757ad0211b78c9f92d406bee833485961a3a3ff611e8` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.13.2 | `net/minecraft/unmapped/C_80754635.java` | `2aff1baacd621e5719ee221181cac4593e382a6fd1c3f0215f1a7bddbbb7330e` |
| 1.13.2 | `net/minecraft/unmapped/C_36170522.java` | `0f53d470214cfeaf6755215ff9c1635155030217c89b542c955b53c64ea4115a` |
| 1.13.2 | `net/minecraft/world/chunk/UpgradeData.java` | `155002cd5fa86b7d69653e2c25a94181d1514db9ad51f38de2fa3af673d7cfe7` |
| 1.13.2 | `net/minecraft/world/chunk/WorldChunk.java` | `d800fac11aa2672a4dd69cb5a599e5f84bb6e2c71cfc8080fa972acdabfb4914` |
| 1.13.2 | `net/minecraft/world/chunk/storage/AnvilChunkStorage.java` | `9037625793eb4b046d4c80ebf352d73a0b750f969dfaa1fc2a53b35467dc5031` |
| 1.13.2 | `net/minecraft/server/world/chunk/ChunkMap.java` | `73f54bd59823c76ad07004ebf560cd6e98b3accd022e8e9c73a2fc3d2fa4c768` |
| 1.13.2 | `net/minecraft/server/ChunkHolder.java` | `034ef97299aae6d99f6a99467f1becdff1763cff88456fd114464a62c303514d` |
| 1.13.2 | `net/minecraft/server/ChunkMap.java` | `7b0cb54e72656ea76abcf44b2e06c6c9778003d981420db2f73c0bfd83dbbe28` |

Source-only result: lifecycle gate is closed for player-visible 1.13.2 chunks from legal legacy adjacent chest pairs. No product edits, tests, builds, launches, source generation, network access, or Git operations were performed.
