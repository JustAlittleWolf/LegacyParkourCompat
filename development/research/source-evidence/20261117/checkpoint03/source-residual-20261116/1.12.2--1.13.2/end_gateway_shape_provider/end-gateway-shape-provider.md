# End gateway collision provider: 1.12.2 → 1.13.2

## Bounded source identity

- 1.12.2 Ornithe Feather Gen2 build 2 manifest: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- 1.13.2 Ornithe Feather Gen2 build 2 manifest: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- The two ready markers report those manifest digests. Both manifest files and every source cited below were checked against the given identities.
- Registry join inputs were verified against the supplied identities: 1.12.2 inventory `29278a0c2b0899866f444296bae8575bc6e039c3bbe419d0c51051077c1624a2`, 1.13.2 inventory `d9bae34a1b4f02a5ab00f7ce7909f420663625c145c328fc5f332a9957a2b139`, crosswalk `bbb1c099dc384489dbb219daa90f797649fc0c8094e5931a68a0c2997f32280a`.

## Provider and registration

**1.12.2:** `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/block/EndGatewayBlock.java`, `getCollisionShape` (lines 36–40; file SHA-256 `68a5026caceb31ce4645acf7f978f99dab3c8f567ed1cb4618031c44c2947fcc`) returns `Block.EMPTY_BLOCK_SHAPE`, which is `null` (`Block.java`, line 54; `Block.java` SHA-256 `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`). The old default `Block.getShape` is a full unit box (`Block.java`, lines 306–309), so the explicit collision override is material: it bypasses that outline/default shape. The end-gateway registration is ID 209 under `minecraft:end_gateway` and constructs `EndGatewayBlock` (`Block.java`, line 1291). The old `Blocks.END_GATEWAY` field resolves that same key (`Blocks.java`, line 491).

**1.13.2:** `EndGatewayBlock.java` (file SHA-256 `f02bf4b06ab7bf2de97dac396f14a1e8d03d32bf386f3ef8676402d6baf05023`) has no `getCollisionShape` override. Its registration remains `minecraft:end_gateway` and still constructs `EndGatewayBlock`, now with `.noCollision()` (`Block.java`, line 1602; file SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`). `Properties.noCollision` sets `hasCollision = false` (same file, lines 1943–1946); base `Block.getCollisionShape` returns `VoxelShapes.empty()` when that flag is false (lines 393–395). Without this registration setting, the base `Block.getShape` default is a full unit shape (lines 388–390). The new `Blocks.END_GATEWAY` also resolves `end_gateway` (line 1099; `Blocks.java` SHA-256 `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303`).

The supplied exact-key crosswalk joins old key `minecraft:end_gateway` / `EndGatewayBlock` to new key `minecraft:end_gateway` / `EndGatewayBlock` (`EXACT_KEY_ONLY`; crosswalk row 263). The class-level change is therefore an override removal accompanied by a registration-level no-collision gate, not a default-full-shape regression.

## State/context construction and movement route

The old state implementation forwards `getCollisionShape(world, pos)` to the block (`BlockProperties.java`, line 82; `StateDefinition.java`, lines 368–372). EndGatewayBlock's override ignores the state and world/position context and always returns the empty sentinel. The ordinary old collision collector calls `BlockState.addCollisions` for blocks (`World.java`, line 1001); the default block implementation asks the state for its collision shape and skips `EMPTY_BLOCK_SHAPE` (`Block.java`, lines 372–382). The player's `Entity.move` collision query enters through `World.getCollisions` (`Entity.java`, lines 566–567).

In 1.13.2, `BlockState.getCollisionShape(world, pos)` forwards to the block (`BlockState.java`, lines 209–211). The ordinary block-collision scan obtains each candidate's shape using that state method (`WorldView.java`, lines 112–150), and `Entity.move` calls `world.getCollisions` while resolving displacement (`Entity.java`, lines 578–583). For the registered gateway, the inherited block provider exits through `hasCollision == false` before reading state shape or world/position context, yielding the empty voxel shape. `WorldView`'s collision stream filters empty shapes (lines 122–123, 150). Thus the player movement collision route excludes the end gateway in both versions; the class-local override is replaced by a registration-backed base-method early return.

## Verdict and limits

**Bounded verdict: no change in the effective End Gateway collision shape for ordinary player block collision.** In 1.12.2 the provider returns the empty `null` sentinel consumed as no collision; in 1.13.2 the registered `noCollision` flag makes the base provider return the empty voxel shape, also excluded from the collision result. This conclusion is specific to the `minecraft:end_gateway` registered state and the ordinary collision query. It does not close other provider families, other End Gateway operations, all player movement coverage, or the 1.12.2–1.13.2 pair.

## Dependencies and evidence file hashes

Dependencies closed for this bounded claim: exact registered-key/class join; old and new state-to-provider forwarding; old sentinel consumer; new `hasCollision` registration gate and empty-shape consumer; ordinary player movement callers.

Other cited canonical source files, each checked against its source manifest:

| Release | File | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/state/BlockProperties.java` | `6c311b6990c949c61872cdd3f94ab5e33f39c252467662aa667b54716bd48c16` |
| 1.12.2 | `net/minecraft/block/state/StateDefinition.java` | `934493deb8dad66936edf94264bda735ae8062c6ef091b1ff6a1501eed2b8d37` |
| 1.12.2 | `net/minecraft/block/Blocks.java` | `8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |

