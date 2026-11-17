# Independent source audit — sapling and dead-bush collision providers

## Verdict

**ACCEPT for the bounded provider and state-mapping conclusion.** Bound to report SHA-256 `ADE4A60FBD9A9D32E728FB0E32E23E2BA7F50CFE8C08AAFBA8AB6E9128730BA9`. The report correctly distinguishes outline shapes from movement collision shapes and establishes no collision-volume delta for the mapped sapling/dead-bush family. This is not a whole-pair or runtime-parity verdict.

## Identity and source-hash checks

- The 1.12.2 Ornithe Feather ready marker specifies source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; the on-disk `ornithe-feather.sources.sha256` hashes to the same value.
- The 1.13.2 Ornithe Feather ready marker specifies source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; its on-disk source sidecar hashes to the same value.
- All nine whole-file Java hashes listed in the report match their corresponding source-sidecar entries: the old Block, Blocks, PlantBlock, SaplingBlock and DeadBushBlock; the new Block, SaplingBlock, DeadBushBlock and C_80754635.
- The route checks below additionally matched the sidecars: 1.12.2 LocalClientPlayerEntity `01a58e94d8c6ff98a8e3794227cdc76a5fcbdad795c70c9cf28854aff9823cc`, Entity `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`, World `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`; 1.13.2 LocalClientPlayerEntity `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`, Entity `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`, WorldView `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`.

## Claim checks

- The 1.13.2 migration helper `C_80754635.m_11856336` stores its first state string as the canonical state and indexes remaining strings as legacy inputs (`:22–30`). Entries 96–101 and 104–109 map the six `sapling` type/stage combinations to corresponding tree-specific sapling states; entry 512 maps `deadbush` to `dead_bush`. The report's mappings are source-supported. New registrations pair the six sapling names with six `SaplingBlock` instances and register `dead_bush` as `DeadBushBlock`; the sapling and dead-bush block properties all call `noCollision()` (`Block.java:804–827, 923–926`).
- The old `PlantBlock.getCollisionShape` returns `EMPTY_BLOCK_SHAPE` unconditionally (`PlantBlock.java:69–73`). `Block.addCollisions` passes the returned shape to `addCollision`, which appends only a non-sentinel box that intersects the query (`Block.java:372–379`). Thus old sapling and dead-bush outline boxes do not enter movement collision lists.
- On the new side, `Block.Properties.noCollision()` sets `hasCollision=false` (`Block.java:1943–1945`); base `Block.getCollisionShape` returns `VoxelShapes.empty()` when that flag is false (`:393–395`). This suppresses the separate, narrower `getShape` results reported for SaplingBlock and DeadBushBlock.
- The direct player query path is reachable: both LocalClientPlayerEntity `move` overrides call `super.move` (old `:846–849`, new `:871–874`); inherited Entity movement uses the world block-collision query (old `Entity.java:567`, new `:580` and related call `:612`). Old `World.getCollisions` visits candidate block states and dispatches `addCollisions` (`World.java:964–1017`); new `WorldView.getBlockCollisions` obtains each candidate state's `getCollisionShape` (`:112–140`), with `BlockState.getCollisionShape` delegating to the block's getter (`BlockState.java:209–211`). The family-specific empty results therefore reach the normal player collision route.

## Scope boundary

The report's no-delta conclusion is supported for saplings and dead bush: the changed outline boxes are not movement collision boxes at either endpoint. The audit does not cover other plant families, outline-ray or support predicates, broad provider/query equivalence, the ordered tick graph, external/resource dependencies, intermediate-release cutovers, or full-pair coverage.
