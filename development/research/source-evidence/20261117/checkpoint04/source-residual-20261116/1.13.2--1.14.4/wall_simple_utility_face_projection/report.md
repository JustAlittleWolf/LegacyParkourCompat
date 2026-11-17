# Simple utility face projection: 1.13.2 → 1.14.4

## Scope and immutable evidence

This source-only slice closes the eight common-key rows assigned from `wall_remaining_face_shape_census/report.md`, whose SHA-256 is `f1a12c7564f14c91c652bea6603e8ada8350b98c2892df05c591c0161d4e041d`: `cactus`, `snow`, `cake`, `farmland`, `grass_path`, `daylight_detector`, `enchanting_table`, and `structure_void`. It compares old horizontal `FaceShape` returns against the 1.14.4 target collision shape's four horizontal face projections. It does not freeze the pair or close any other census row.

The exact endpoints are Minecraft 1.13.2 and 1.14.4, Ornithe Feather Gen2 build 2. Their read-only ready markers identify the expected version and mapping. The SHA-256 of the source-manifest bytes matches the ready marker on each endpoint:

| Endpoint | Ready marker | Manifest | Manifest byte SHA-256 |
|---|---|---|---|
| 1.13.2 | `decompiled_minecraft/1.13.2/ornithe-feather.ready.json` | `ornithe-feather.sources.sha256` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |
| 1.14.4 | `decompiled_minecraft/1.14.4/ornithe-feather.ready.json` | `ornithe-feather.sources.sha256` | `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc` |

Registration join loci are 1.13.2 `Block.java:1000–1001` (farmland), `:1067,1070–1071` (snow, cactus), `:1090` (cake), `:1214` (enchanting table), `:1297` (daylight detector), `:1599–1600` (grass path), and `:1613` (structure void); target `Blocks.java:425–426` (farmland), `:558–559` (snow), `:565–566` (cactus), `:596` (cake), `:752–753` (enchanting table), `:945–946` (daylight detector), `:1428–1429` (grass path), and `:1454–1455` (structure void). These bind the requested common keys to the classes whose state and shape methods are compared below.

Every source file cited below was hashed from current bytes and its digest matched the corresponding endpoint sidecar entry. The compared keys join by registration: 1.13.2 registrations are in `Block.java`; 1.14.4 registrations are in `Blocks.java`. The target face test is `Block.isFaceFull(shape, direction)`, which projects with `VoxelShape.m_67423623(direction)` and checks whether the result is a full block (`Block.java:406–413`; `VoxelShape.java:170–200`). Target ordinary collision dispatch is `Block.getCollisionShape`; it returns empty when `hasCollision` is false, otherwise asks the state shape (`Block.java:376–378`).

## Exhaustive common-key results

Direction order in the two result columns is **north, east, south, west**. Each old result tuple gives the effective 1.13.2 `getFaceShape` enum for those directions. Each target tuple gives the 1.14.4 `Block.isFaceFull` result for the target collision shape projected toward the corresponding face. No listed state has a world-, position-, or actor-dependent geometry branch in these providers.

| Common key | Complete legal state domain at both endpoints | Old horizontal `FaceShape` (N,E,S,W) for every listed state | 1.14.4 collision shape used by projection, in sixteenths | Target full-face result (N,E,S,W) for every listed state |
|---|---|---|---|---|
| `minecraft:cactus` | `age=0..15` (16 states) | `UNDEFINED, UNDEFINED, UNDEFINED, UNDEFINED` | `[1,15] × [0,15] × [1,15]`; `CactusBlock.getCollisionShape` returns `COLLISION_SHAPE` for all ages | `false, false, false, false` |
| `minecraft:snow` | `layers=1..8` (8 states) | `UNDEFINED, UNDEFINED, UNDEFINED, UNDEFINED` | Full X/Z footprint; collision height is `(2 × (layers − 1))/16`, i.e. 0/16, 2/16, 4/16, 6/16, 8/16, 10/16, 12/16, 14/16 in state order | `false, false, false, false` for each of all 8 states |
| `minecraft:cake` | `bites=0..6` (7 states) | `UNDEFINED, UNDEFINED, UNDEFINED, UNDEFINED` | `x=[1+2×bites,15]`, `y=[0,8]`, `z=[1,15]`; seven state-indexed shapes | `false, false, false, false` for each of all 7 states |
| `minecraft:farmland` | `moisture=0..7` (8 states) | `UNDEFINED, UNDEFINED, UNDEFINED, UNDEFINED` | `[0,16] × [0,15] × [0,16]`; `FarmlandBlock.getShape` returns the constant for every moisture value | `false, false, false, false` for each of all 8 states |
| `minecraft:grass_path` | No block-state properties (1 state) | `UNDEFINED, UNDEFINED, UNDEFINED, UNDEFINED` | `[0,16] × [0,15] × [0,16]`; `GrassPathBlock.getShape` returns the constant | `false, false, false, false` |
| `minecraft:daylight_detector` | `power=0..15`, `inverted=false/true` (32 states) | `UNDEFINED, UNDEFINED, UNDEFINED, UNDEFINED` | `[0,16] × [0,6] × [0,16]`; `DaylightDetectorBlock.getShape` returns the constant for every combination | `false, false, false, false` for each of all 32 states |
| `minecraft:enchanting_table` | No block-state properties (1 state) | `UNDEFINED, UNDEFINED, UNDEFINED, UNDEFINED` | `[0,16] × [0,12] × [0,16]`; `EnchantingTableBlock.getShape` returns the constant | `false, false, false, false` |
| `minecraft:structure_void` | No block-state properties (1 state) | `UNDEFINED, UNDEFINED, UNDEFINED, UNDEFINED` | **Empty collision shape**: registration sets `noCollision()`, so inherited collision dispatch returns empty. Its outline shape `[5,11] × [5,11] × [5,11]` is not the collision shape used here. | `false, false, false, false` |

The state and method loci behind each row are as follows. `Properties.java` declares `AGE_15=0..15`, `LAYERS=1..8`, `BITES=0..6`, `MOISTURE=0..7`, `POWER=0..15`, and boolean `INVERTED` at 1.13.2 lines `75, 82, 77, 86, 89, 32` and 1.14.4 lines `83, 90, 85, 95, 98, 37`, respectively. Each class's builder adds exactly the properties named in the domain column; where no builder is overridden, the class has no block-specific properties. Old direct face methods are: `CactusBlock.java:118–120`; `SnowLayerBlock.java:68–70`; `CakeBlock.java:110–112`; `FarmlandBlock.java:127–129`; `GrassPathBlock.java:71–73`; `DaylightDetectorBlock.java:104–106`; `EnchantingTableBlock.java:108–110`; `StructureVoidBlock.java:49–51`. Target state builders and collision/shape bodies are: `CactusBlock.java:24,61–63,109–111`; `SnowLayerBlock.java:23,60–62,114–116`; `CakeBlock.java:23,39–41,83–85`; `FarmlandBlock.java:25,58–60,113–115`; `GrassPathBlock.java:17,56–58`; `DaylightDetectorBlock.java:25,33–35,99–101`; `EnchantingTableBlock.java:29,41–43`; `StructureVoidBlock.java:12,24–26`. The corresponding old property builders are at `CactusBlock.java:113–115`, `SnowLayerBlock.java:155–157`, `CakeBlock.java:95–97`, `FarmlandBlock.java:122–124`, and `DaylightDetectorBlock.java:99–101`.

All old tuples are the explicit old methods: CactusBlock and CakeBlock return `UNDEFINED` unconditionally; StructureVoidBlock does likewise. SnowLayerBlock, FarmlandBlock, GrassPathBlock, DaylightDetectorBlock, and EnchantingTableBlock return `SOLID` only for `Direction.DOWN`, hence `UNDEFINED` for every horizontal direction. No state guard alters these methods. In 1.14.4 these classes no longer provide `getFaceShape`; the table therefore uses their effective old method against the requested target collision-face projection.

The target shape outcomes follow from the actual collision shapes and `isFaceFull` projection, not from outline or occlusion shapes. For cactus, cake, and structure_void the horizontal projection is inset or empty. For snow, farmland, grass_path, daylight_detector, and enchanting_table, X/Z extents reach the block sides, but the vertical interval is strictly shorter than the full 0..16 face in every state, including empty snow collision at layer one. Thus no target horizontal projected face is a full 1×1 face. The 1.14.4 registrations retain ordinary collision for the first seven keys (default `hasCollision=true` and no `.noCollision()` call); `structure_void` explicitly sets `.noCollision()` as it did in the old registration.

## Bounded conclusion

For every legal state of these eight common keys and each of north/east/south/west, the old `FaceShape` is `UNDEFINED` and the corresponding 1.14.4 collision-face full-block predicate is `false`. **No horizontal face-classification delta is established for these rows.** This statement is limited to the specified old FaceShape-versus-target full-face operation; it does not adjudicate other block predicates, state history, movement trajectories, other residual keys, or pair-wide coverage.

## Reused direct-geometry evidence

Existing provider reports are reused only for the identical same-state collision geometry claims for snow, cake, farmland, and grass_path; their state domains and values are restated above to make this projection comparison complete. Their report byte hashes are:

- `snow_layer_shape_provider/coverage.md`: `e5aef1279c141f2104ad1e15857c924a68c7a3cc446de0b340d800045c7c9891`.
- `cake_shape_provider/coverage.md`: `0bd0167edac8df3eb52b847541d09ada39ad1a53c5e20a470129a5d7d0c536c0`.
- `farmland_shape_provider/coverage.md`: `345e2fa2b033f76d2b7c3525d52eb957e46b371a7ae8b349973c667b18a8837e`.
- `grass_path_shape_provider/result.md`: `a6caad3c938a5546e1b3780e8f6082f334aa889e737dccc0f7ded090681b5581`.

## Exact source-file hashes

These complete-file SHA-256 values match their endpoint sidecar entries:

| Source file | 1.13.2 | 1.14.4 |
|---|---|---|
| `net/minecraft/block/CactusBlock.java` | `d337d776966b6a50070a5ed79ec8cbabb064e15b7b2d6b8e4992a66ccea5e8c9` | `3e5d6d42cef91cd8bab1b2a96589f80f6d164fea8f0b286a0589cbf211d2f67b` |
| `net/minecraft/block/SnowLayerBlock.java` | `12b2d50064fd306c09007bc9a3629cc14a540123ce37e3b53a7f6615c9915df2` | `2307bd4ad1b41210c8c82fddad03dea2e6867b87e7539b9249a6ed49960f6dcb` |
| `net/minecraft/block/CakeBlock.java` | `f9464e8a2f328c21b788654dc81115ba236c31984e93949f69df2b2dace5fd6c` | `72f4d89064e007e24a98465e67631928d41a90e8fa581f189b8de4d5863f7905` |
| `net/minecraft/block/FarmlandBlock.java` | `581dae15cc29abe90f9cf20232a3cd1b7ab6c868808bc5e4f05816952b61eef9` | `27227a174fa9d51ab3b618e52d1ec6226fcd0610d4c89ed5648e113731eb66c3` |
| `net/minecraft/block/GrassPathBlock.java` | `8acdb82a274f68c5dd89faa92c82d450bbe9906ee65584c981633ed922430cfe` | `5924874f0eda2942dd1ce889e665dfa7fa1d8b28a2dbed42917e44014540b0a8` |
| `net/minecraft/block/DaylightDetectorBlock.java` | `9abc985cb0fd300cc26edf1a0ec0343911354e3ba4b59d0e823531274e23c49a` | `e7be527301144649bfb10929304e624d9cf9697af521a5fd8e416f15078c94a5` |
| `net/minecraft/block/EnchantingTableBlock.java` | `db240cb9087fa871f964df7ded66581c567b41240ecd8d517b02295329a3f69d` | `ef5fe857df45dc69c7ef67f75d5f397c92bc312cf1a5c5e3b8dfcae5e2739c88` |
| `net/minecraft/block/StructureVoidBlock.java` | `4f3eadb2f4d3e5dfc3f193f7cb7e7fd477a29e8511df2a7bc60494a936dbabc8` | `9acd9449376d07a2b4a9e5fb3428c3ef37dc42ee4e4c08fd4ef556c31fffd250` |
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |
| `net/minecraft/block/shape/VoxelShape.java` | `d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1` | `8efb68bf9d5efdb0ec48e401d1852e90458c7094f8fa0c8e9ae418f268c40fdd` |
| `net/minecraft/block/shape/VoxelShapes.java` | `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95` | `51a09c297c7f01c8bad9b66af2a80900e95f33c6e3a250ce599f4817433587d9` |

`Block.java` is the old registration source and contains the old generic face/collision methods. `Blocks.java` is the target registration source. The property files prove the shared integer domains (`AGE_15`, `LAYERS`, `BITES`, `MOISTURE`, `POWER`, `INVERTED`); the block source definitions show which subset each block includes. `VoxelShape.java` and `VoxelShapes.java` ground the target face projection and full-block test. No shared source was written or regenerated.
