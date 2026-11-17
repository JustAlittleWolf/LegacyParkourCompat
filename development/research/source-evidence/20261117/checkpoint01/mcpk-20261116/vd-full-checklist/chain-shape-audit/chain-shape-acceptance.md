# Chains collision-shape source acceptance

Status: **source acceptance complete for the bounded clauses below**. This is not runtime-parity acceptance.

## Accepted clauses

| Supplied clause | Verdict | Source conclusion |
|---|---|---|
| Added Chains; collision box `0.1875 × 1 × 0.1875`, same base box as Bamboo but centered | **Confirmed** | In 1.16 and 1.16.1, `ChainBlock.SHAPE` is `Block.box(6.5, 0, 6.5, 9.5, 16, 9.5)`, which is `(3/16) × 1 × (3/16)` and centered on X/Z. It exactly matches 1.16 `BambooBlock.COLLISION_SHAPE`; Bamboo then applies a state/block offset to that base shape while Chains return the unoffset shape. Chains' registry does not disable collision, and `BlockBehaviour` defaults `hasCollision` to true, so this is collision geometry.
| In 1.16.2+, Chains can be placed in all orientations | **Confirmed at the first-release boundary and sampled later releases** | 1.16.0 and 1.16.1 have no `AXIS` state and always return the vertical box. 1.16.2 introduces `AXIS`; `RotatedPillarBlock.getStateForPlacement` assigns the clicked face's axis, so X, Y and Z are reachable. The three shapes follow those axes. The same definitions and placement rule are present in 1.16.5 and 26.2. The exact first stable release with this behavior is **1.16.2** (older 1.16.1, newer 1.16.2). No other 1.16.x patch boundary is claimed.

“All orientations” here means three axis-aligned states (X/Y/Z), not arbitrary continuous rotation. The source establishes a player-movement-relevant collision-shape change: a horizontal chain collides as a horizontal 3/16-thick beam from 1.16.2 onward, while 1.16.0/1.16.1 use a vertical 3/16-thick column regardless of placement orientation.

## Exact source evidence

All paths are relative to `decompiled_minecraft/` and line numbers are one-based.

- **1.16, Mojmap:** `1.16/mojmap/net/minecraft/world/level/block/ChainBlock.java:20-31,54-55` declares the single centered shape, returns it from `getShape`, and exposes only `WATERLOGGED` in state definition. `1.16/mojmap/net/minecraft/world/level/block/Blocks.java:909-918` registers Chains without `.noCollision()`. `1.16/mojmap/net/minecraft/world/level/block/BambooBlock.java:29-32,69-73` declares the identical collision box and moves it by the Bamboo state offset.
- **1.16.1, Mojmap:** the corresponding `ChainBlock.java` and `Blocks.java` source hashes equal 1.16.0; the shape/state/registration behavior is unchanged. This is the older side of the boundary check.
- **1.16.2, Mojmap:** `1.16.2/mojmap/net/minecraft/world/level/block/ChainBlock.java:20-42,64-65` extends `RotatedPillarBlock`, defines Y `(6.5,0,6.5)-(9.5,16,9.5)`, Z `(6.5,6.5,0)-(9.5,9.5,16)`, X `(0,6.5,6.5)-(16,9.5,9.5)`, selects by `AXIS`, and registers both `WATERLOGGED` and `AXIS`. `1.16.2/mojmap/net/minecraft/world/level/block/RotatedPillarBlock.java:37-45` defines `AXIS` and sets it to `clickedFace.getAxis()` on placement. `Blocks.java:916-925` keeps collision enabled by omission of `.noCollision()`.
- **1.16.5, Mojmap:** `ChainBlock.java:20-42,64-65` and `RotatedPillarBlock.java:37-45` retain the same three axis shapes and clicked-face placement rule. The 1.16.2 and 1.16.5 relevant source files have matching byte hashes.
- **26.2, unobfuscated:** `ChainBlock.java:24-41,69-72` uses the same `AXIS` state and shape provider. `Block.cube`/`column` at `Block.java:172-184` makes the starting shape a centered 3-wide column with Y bounds 6.5–9.5; `Shapes.rotateAllAxis` at `Shapes.java:387-401` maps the starting Z box to Z, rotates it to X and Y; `RotatedPillarBlock.java:49-56` assigns clicked-face axis. `Blocks.java:2348-2352` registers iron Chains without `.noCollision()`.

### Player collision path

- **1.16:** `LocalPlayer.move` calls `super.move` (`client/player/LocalPlayer.java:887-893`). `Entity.move` calls `collide` (`world/entity/Entity.java:469-505`), and the collision helper incorporates `level.getBlockCollisions` (`Entity.java:698-710`). `CollisionGetter.getBlockCollisions` creates the block collision iterator (`world/level/CollisionGetter.java:58-63`); `CollisionSpliterator` reads `blockState.getCollisionShape(...)` (`world/level/CollisionSpliterator.java:70-84`). `BlockBehaviour.getCollisionShape` returns `blockState.getShape(...)` when `hasCollision`, otherwise empty (`world/level/block/state/BlockBehaviour.java:274-277`).
- **26.2:** `LocalPlayer.move` calls `super.move` (`client/player/LocalPlayer.java:986-992`); `Entity.move` resolves collisions (`world/entity/Entity.java:711-738,1214-1229`). `CollisionGetter.getBlockCollisions` feeds `BlockCollisions` (`world/level/CollisionGetter.java:96-105`); `BlockCollisions` obtains the context collision shape from each block state (`world/level/BlockCollisions.java:89-102`). `BlockBehaviour.getCollisionShape` returns the state's shape when `hasCollision` (`world/level/block/state/BlockBehaviour.java:323-328`).

Thus the provider's axis-dependent shape is consumed by the local player's ordinary movement collision path. The assignment covers the supplied shape/orientation clauses only; this verdict does not accept unrelated movement differences or runtime parity.

## Evidence identities and byte checks

Exact source namespaces are Mojmap for 1.16.0/.1/.2/.5 and unobfuscated for 26.2. The source-manifest SHA-256 values below match the supplied verification packet. For every source listed in the hash groups, I computed SHA-256 from the canonical file bytes and compared it with that version's `*.sources.sha256` row; all matched.

| Exact source | Mapping | Source-manifest SHA-256 |
|---|---|---|
| 1.16 | Mojmap | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` |
| 1.16.1 | Mojmap | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |
| 1.16.2 | Mojmap | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` |
| 1.16.5 | Mojmap | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` |
| 26.2 | unobfuscated | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` |

The following are source-file SHA-256 values computed from bytes. Slash-separated groups mean the exact hashes matched in each listed release.

| Source file | 1.16 / 1.16.1 | 1.16.2 / 1.16.5 | 26.2 |
|---|---|---|---|
| `world/level/block/ChainBlock.java` | `3f05483f3f4053910f658fb951b93b1dc85022594520bff77aa36ae7d5edfdcb` | `6a7fed55ffa892a3781b64849d9f42fb85a125eeb023a23171bc8880bf67fb6e` | `5f00aa21fdbd449a7a3d1d461b3964f3b31238801a08b033621ca8ab2c5ae8f8` |
| `world/level/block/RotatedPillarBlock.java` | `0982dea306a1874be949545e5065443b92491fc24d7f7c49fa082716272ecbc8` | `3fddbbd612f0d557d4af2ba784a8f223aafa11525aed0091ae976b6cdbb2ac93` | `88f305673517c901e4422642365f004fe75692a04298454c80a889a8224a9843` |
| `world/level/block/Blocks.java` | `61f18f903f7d96731bbc3cac544a8614f55fddac872dc0b0fee5a53511c313a9` | `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12` | `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435` |
| `world/level/block/state/BlockBehaviour.java` | `1b524b5c45e3be4397dd680b9bd12c937497b9088e623428cf6387406018ef7f` | `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b` | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` |
| `world/entity/Entity.java` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| `client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| `world/level/CollisionGetter.java` | `fc7f175b10c2c6683913a903a366bd775facecf3c58f04cc6e9cfc95ddceed98` | `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac` | `8e7d1a54d27e0f187667439539c59b8892f966f1b9c8107eb162c6d43f830f6c` |
| `world/level/CollisionSpliterator.java` / `BlockCollisions.java` | `CollisionSpliterator`: `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297` | `CollisionSpliterator`: same hash | `BlockCollisions`: `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` |
| `world/level/block/BambooBlock.java` | `d1a1cb579f784bd7ae04fac16a83cab7b34fcd66b089e8da2a95ffbfefd3c704` | `7522c0fa5014068a8d0a2909950fbbaa5e2344bdb0553698f3c01cb7434e4897` | not used |
| `world/level/block/Block.java` | `ca9a413bf8198402ec9f2febb65c2e89eef4386cb0f1f3d36650f4047d896655` | `2838aa4fb4052b3bd78d77b3d232a6321d03bd2dfd52275ffacfe6915a89e5b6` | `cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab` |
| `world/phys/shapes/Shapes.java` | `ec218dca68cad3525364967aadfbb8c0bead0cc44aa3ee2beba31e00e55ae2d4` | same hash | `6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891` |

No remaining dependency blocks acceptance of these bounded shape/orientation claims. No implementation, tests, build, or game runtime was run.
