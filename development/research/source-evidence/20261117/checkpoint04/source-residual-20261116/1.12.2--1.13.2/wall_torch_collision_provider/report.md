# Wall torch collision provider: 1.12.2 → 1.13.2

## Scope and source identity

This is a source-only comparison of the horizontal-facing legacy `minecraft:torch` states that the 1.13.2 flattening table maps to `minecraft:wall_torch`. It excludes the redstone wall-torch key, which is a separate provider/mapping. It compares registered collision-provider behavior and records the outline and `FaceShape` routes; it does not establish which external migration paths invoke the fixer or claim a movement trajectory.

The read-only exact endpoint identities are Ornithe Feather Gen2 build 2:

- 1.12.2 source-manifest SHA-256: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- 1.13.2 source-manifest SHA-256: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

Both ready markers agree with their manifest bytes. Every cited Java source digest below was recomputed from the canonical read-only source and matched the corresponding manifest entry.

## Key and state mapping

In 1.12.2, `Block.java` registers numeric block ID 50 as `minecraft:torch` (`Block.java:861`). `TorchBlock.FACING` admits every direction except DOWN, so the legacy state manager has five values: UP, NORTH, EAST, SOUTH, WEST (`TorchBlock.java:20–27,34,250–253`). Its horizontal states are the four states relevant to this slice.

The 1.13.2 `net.minecraft.unmapped.C_80754635` table contains four flattening entries (`C_80754635.java:950–953`) whose output state is `minecraft:wall_torch` with `facing=east|west|south|north`, respectively, and whose legacy input is `minecraft:torch` with the same facing. The next entry maps legacy bare `minecraft:torch` to target `minecraft:torch[facing=up]` (`:954`). The table implementation stores the first state as the output and indexes each subsequent state as a legacy input; `m_11299400` returns the indexed output (`:18–35`). Thus, within this exact table, the four old horizontal states map to the four target wall-torch states without a facing rotation.

Target `WallTorchBlock` declares `FACING = HorizontalFacingBlock.FACING` and adds only that property to its state definition (`WallTorchBlock.java:21–22,108–111`); the target domain is the four horizontal directions. The table and class source prove the mapping declaration and state domains. No call site for `C_80754635.m_11299400` appears in the inspected source tree, so this report does not claim that every external chunk/world importer applies the table.

## Collision, outline, and face-shape providers

Old `TorchBlock.getShape` selects orientation-specific outline boxes for the horizontal states; `getCollisionShape` returns `EMPTY_BLOCK_SHAPE` for all states (`TorchBlock.java:40–59`). Its `getFaceShape` returns `UNDEFINED` (`:256–258`). The old block registration creates the ordinary torch block; the source state mapping above selects only the horizontal states for this target key.

Target `Block.java` registers `wall_torch` as `WallTorchBlock` with `.noCollision()` (`Block.java:987`). The base target `Block.getCollisionShape` returns the state outline only when `hasCollision` is true, and otherwise returns `VoxelShapes.empty()` (`Block.java:393–395`); `Properties.noCollision()` sets that flag false (`:1943–1946`). Therefore target `wall_torch` has empty collision shape in every facing state, even though `WallTorchBlock.getShape` selects a nonempty facing-specific outline (`WallTorchBlock.java:23–36,45–50`). The target `TorchBlock.getFaceShape` returns `UNDEFINED` (`TorchBlock.java:66–69`), inherited by `WallTorchBlock` because it declares no override.

For ordinary entity movement collision dispatch, 1.12.2 `Entity.move` calls `World.getCollisions` with the expanded player/entity box (`Entity.java:462,567`). `World.getCollisions` scans block states and calls the state collision callback (`World.java:964–1002`); `StateDefinition.StateImplementation.addCollisions` delegates to `Block.addCollisions`, whose base method reads `state.getCollisionShape` (`StateDefinition.java:375–376; Block.java:372–389`). Target 1.13.2 `Entity.move` calls the world collision stream (`Entity.java:580,612`); `WorldView` obtains each block collision shape through `BlockState.getCollisionShape`, which dispatches to `Block.getCollisionShape` (`WorldView.java:104,130–136; BlockState.java:209–211`). The provider results for this mapped slice are both empty, so these wall-torch states contribute no block collision shape to either movement query.
## Bounded verdict and limits

For the four horizontal legacy `minecraft:torch` states mapped by the 1.13.2 table to `minecraft:wall_torch`, the effective ordinary block-collision provider is empty in both releases. No collision-provider delta is found for this bounded slice. The outline implementation is orientation-specific in both versions; this report does not assert coordinate-for-coordinate outline equality. `FaceShape.UNDEFINED` is inherited in both providers.

The mapping is proven as a source table entry, but its application by external/custom import paths is not. The comparison does not cover standing/up-facing torch mappings, redstone torches, wall-torch support/placement behavior, wall-predicate projection, custom block states, or pair-wide coverage/freeze. No trajectory, build, test, game, or runtime claim is made.

## Canonical source file hashes

| Version | Canonical Java path | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/TorchBlock.java` | `78aeee1e8c03aa0544f6d7e9fbba2555608ca9c721d1541d0d01ebf2aa9ea009` |
| 1.12.2 | `net/minecraft/block/state/BlockState.java` | `3fb6f06df86c3b89716b5efaed5d97eee670d007d3c4535e47eb0fdd993e92eb` |
| 1.12.2 | `net/minecraft/block/state/StateDefinition.java` | `934493deb8dad66936edf94264bda735ae8062c6ef091b1ff6a1501eed2b8d37` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/TorchBlock.java` | `da11308e51f57ce95764f2ee645dde8d15607914a3a68bac9c0635ab3d1531e0` |
| 1.13.2 | `net/minecraft/block/WallTorchBlock.java` | `f06b2a546ba8465627511969efc1203e654a1af3ccf61d27aed9b28b00c77462` |
| 1.13.2 | `net/minecraft/block/HorizontalFacingBlock.java` | `7ce4cee45329c93352c8a1740d691b046c8cd573dbb9e4b6549924f07f2fe1ad` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 | `net/minecraft/unmapped/C_80754635.java` | `2aff1baacd621e5719ee221181cac4593e382a6fd1c3f0215f1a7bddbbb7330e` |


