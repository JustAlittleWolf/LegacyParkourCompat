# Snow Layer native fallback audit (1.13 -> 26.2)

## Verdict

For the single-layer Snow Layer block state, native 26.2 fallback preserves the non-colliding movement outcome from 1.13. The collision provider returns an empty shape on both exact sources, and the reachable player movement collision enumerators consume that provider. This bounded comparison does **not** establish parity for higher snow layers or any other Snow behavior; it finds no 1.13-to-26.2 single-layer collision delta requiring consideration.

## Source identities and integrity

- **1.13 / Ornithe Feather:** `decompiled_minecraft/1.13/ornithe-feather`; ready marker says `versionId=1.13`, mapping `ornithe-feather`, status `ready`, mapping artifact `net.ornithemc:feather-gen2:1.13+build.2` (merged mapping jar and tiny file recorded in provenance). Provenance records client SHA-256 `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e`, metadata id `1.13`, and Vineflower 1.12.0. Ready source-manifest SHA-256 matches the manifest file hash: `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`.
- **26.2 / unobfuscated:** `decompiled_minecraft/26.2/unobfuscated`; ready marker says `versionId=26.2`, mapping `unobfuscated`, status `ready`. Provenance records an exact `--versions=26.2 --mappings=unobfuscated` run and Vineflower 1.12.0; this provenance has no raw client jar or mapping-artifact fields. Ready source-manifest SHA-256 matches the manifest file hash: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.
- Marker/provenance file SHA-256s: 1.13 ready `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99`, provenance `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d`; 26.2 ready `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`, provenance `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`.

Every source hash below was independently recomputed from the exact file bytes and matches its named source manifest entry.

| Exact source file | SHA-256 |
| --- | --- |
| 1.13 `net/minecraft/block/SnowLayerBlock.java` | `12b2d50064fd306c09007bc9a3629cc14a540123ce37e3b53a7f6615c9915df2` |
| 1.13 `net/minecraft/world/WorldView.java` | `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543` |
| 1.13 `net/minecraft/entity/Entity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` |
| 1.13 `net/minecraft/entity/living/player/PlayerEntity.java` | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` |
| 26.2 `net/minecraft/world/level/block/SnowLayerBlock.java` | `312952c724788f30a2255bc34ef7d3242d4737067c23939f0bd0a76059b473cc` |
| 26.2 `net/minecraft/world/level/block/Block.java` | `cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab` |
| 26.2 `net/minecraft/world/phys/shapes/Shapes.java` | `6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891` |
| 26.2 `net/minecraft/world/level/BlockCollisions.java` | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` |
| 26.2 `net/minecraft/world/level/CollisionGetter.java` | `8e7d1a54d27e0f187667439539c59b8892f966f1b9c8107eb162c6d43f830f6c` |
| 26.2 `net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.2 `net/minecraft/world/entity/player/Player.java` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| 26.2 `net/minecraft/world/entity/Avatar.java` | `01a8092bc2637e7d42a261fb564bffcabadde28625cc02b120e943559e2d4fe78` |

## Bounded source comparison

- **1.13 provider:** `SnowLayerBlock` initializes `SHAPES[0]` to `VoxelShapes.empty()` (source lines 31–33); `getCollisionShape` returns `SHAPES[state.get(LAYERS) - 1]` (lines 77–80). The vanilla one-layer state therefore resolves to index 0 and an empty collision shape.
- **26.2 provider:** `SnowLayerBlock` builds `SHAPES` as `Block.boxes(8, height -> Block.column(16.0, 0.0, height * 2))` (line 28) and `getCollisionShape` returns `SHAPES[state.getValue(LAYERS) - 1]` (lines 51–54). The one-layer state resolves to index 0. `Block.boxes` includes index 0; that entry calls `Block.column(16, 0, 0)`, which calls `Shapes.box` with equal Y bounds. `Shapes.create` returns `Shapes.empty()` when an extent is below `1.0E-7` (lines 52–72). Thus native 26.2 also supplies an empty collision shape.
- **Reachability in 1.13:** `PlayerEntity extends LivingEntity` (line 97). The inherited `Entity.move` calls `world.getCollisions` for movement (lines 578–583). `WorldView.getBlockCollisions` queries each candidate block state's `getCollisionShape` and skips it when empty (lines 141–145); `getCollisions` reaches that provider (lines 187–210). The single snow layer contributes no block collision.
- **Reachability in 26.2:** `Player extends Avatar` and `Avatar extends LivingEntity`; `Entity.move` proceeds through `collide`/`collideBoundingBox` to `level.getBlockCollisionsFromContext` (lines 711 onward, 1141–1145, 1195–1241). `CollisionGetter` creates `BlockCollisions` for that query (lines 96–105); `BlockCollisions.computeNext` gets each block state, asks the collision context for its collision shape, and yields it only when nonempty/intersecting (lines 79–104). Therefore the native single-layer empty shape is ignored by player movement just as in 1.13.

No product implementation, wiki material, findings/audits, backlog, tests, or game runtime were consulted or changed.

