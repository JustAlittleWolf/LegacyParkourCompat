# Double plants and melon/pumpkin stem wall-predicate projection

## Scope and exact inputs

This note checks only six common registry keys left unexamined in the source-only wall projection crosswalk: `large_fern`, `tall_grass`, `melon_stem`, `pumpkin_stem`, `attached_melon_stem`, and `attached_pumpkin_stem`. The exact crosswalk input is `build/source-residual-20261116/1.13.2--1.14.4/wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv` (SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`); its accepted correction packet is `wall_full_registry_owner_crosswalk/coverage.md` (SHA-256 `a8285ea8c199dd0261e25202214ea401fdf1f54af4db836917c6e9cd5fe369d0`). The six key/class rows are the only crosswalk rows included here. The existing wall plant/fluid projection checked kelp, bubble column, sugar cane, cocoa, chorus, fluids and seagrass; it does not include these six keys.

The canonical read-only endpoint identities match their ready markers: 1.13.2 Ornithe Feather Gen2 build 2, source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 Ornithe Feather Gen2 build 2, source-manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`.

## Registered state domains

The crosswalk's attached-stem rows list `FACING:6`; that count is incorrect. In both exact sources `AttachedPlantStem.FACING` aliases `HorizontalFacingBlock.FACING`, and `HorizontalFacingBlock.FACING` aliases `Properties.HORIZONTAL_FACING`, which is `DirectionProperty.of("facing", Direction.Plane.HORIZONTAL)`. Its legal domain is NORTH, EAST, SOUTH, WEST (four values), not all six directions. The two attached-stem keys each therefore have four states.

`StemBlock` declares `AGE = Properties.AGE_7` and adds it to its state definition; `AGE_7` is the inclusive integer range 0–7. `large_fern` and `tall_grass` use the `HALF` property added by `BushBlock`; the two registered values are LOWER and UPPER. These domains are unchanged across the endpoints.

| Keys | Old registered class → target registered class | Legal states per key | State × horizontal direction cases |
|---|---|---:|---:|
| `large_fern`, `tall_grass` | `DoublePlantBlock` → `BushBlock` | `HALF={LOWER,UPPER}` (2) | 16 |
| `melon_stem`, `pumpkin_stem` | `StemBlock` → `StemBlock` | `AGE=0..7` (8) | 64 |
| `attached_melon_stem`, `attached_pumpkin_stem` | `AttachedPlantStem` → `AttachedPlantStem` | `FACING={NORTH,EAST,SOUTH,WEST}` (4) | 32 |
| **Total** | 6 keys | 28 key-state pairs | **112** |

## Predicate comparison

At 1.13.2, `WallBlock.shouldConnectTo` accepts a non-exception `FaceShape.SOLID`; it gets the neighbor face directly through `BlockState.getFaceShape`. Each of these six old classes inherits `PlantBlock.getFaceShape`, which always returns `FaceShape.UNDEFINED`. The old wall exception list and its inherited attachment exception list do not name any of these six keys, but that does not alter the result because the face is not SOLID. Thus all 112 old wall bits are false.

At 1.14.4, wall placement and neighbor updates call `BlockState.m_87223014(world, pos, face)` and pass that boolean to the wall predicate. `Block.isFaceSolid` tests the collision-shape face; base `Block.getCollisionShape` returns the empty shape when `hasCollision` is false. The exact `Blocks` registrations for all six target keys set `.noCollision()`. Although `StemBlock` and `AttachedPlantStem` provide nonempty age/facing-dependent outline shapes, those do not reach the collision-face check when `hasCollision` is false. Target `isExceptionForAttachment` does not name these keys; none is a fence gate or a `WALLS` tag member. Therefore every target wall bit is also false across the 112 checked key-state-direction cases.

**Disposition:** no difference in this bounded registered-neighbor wall-predicate slice. This is not a movement trajectory claim and does not close the pair. It does not compare growth/update callbacks, paired-block lifecycle, resource membership beyond the previously accepted WALLS input, placement survival, wall upper-post combination, or other registry keys.

## Source anchors and hashes

Paths are relative to `decompiled_minecraft/<version>/ornithe-feather/`. The hashes below were checked against the exact endpoint source manifests named above.

| Source | 1.13.2 SHA-256 | 1.14.4 SHA-256 | Relevant members/anchors |
|---|---|---|---|
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` | old `isExceptionForPlacement`/`isExceptionForAttachment`; target `isExceptionForAttachment`, `getCollisionShape`, `isFaceSolid` |
| `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` | endpoint key registration; target `.noCollision()` for each of the six keys |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` | old `shouldConnectTo`/`isExceptionForConnection` and direct face query; target `m_71853799`, `getPlacementState`, `updateShape` |
| `net/minecraft/block/PlantBlock.java` | `a1234ccec309bd8805fbe3bad48bce076e17c131e048d2a57d18cdb75f0f286e` | `6ede310fcb1e0163865dd4161b291cc02fe223bb56dfa1f89aa64b1cf72a1f75` | old inherited `getFaceShape` returns UNDEFINED |
| `net/minecraft/block/DoublePlantBlock.java` | `95e782d379a7e8fbbe24497bd25d840aab47ccc6fb2de8f50f5afa0cbd8080fa` | `44b9787e974632f4f9594d458388a3a84afdd3983012f809fb5b3d762fde7785` | `HALF` aliases `BushBlock.HALF` |
| `net/minecraft/block/BushBlock.java` | `f4602bf78bd7477f7a4ea5ff3fbab862ff523d853900c7a72a7ce14b5d7b30f8` | `bae19aadcd260d90011dbed798f4d3b343c678da36d04fbb0fcb559e47f7b443` | `HALF` state declaration; old double-plant inheritance / target registered class |
| `net/minecraft/block/StemBlock.java` | `c247cd56d97ff7d39a8436fe9a0396480158b2ead6aa3ee0870953c2ad30e35e` | `b9035f20363564a7d529864b94e4fe4c255fe0a84f721abad2a847930aeb63aa` | `AGE_7`, `buildStateDefinition`, age-based outline provider |
| `net/minecraft/block/AttachedPlantStem.java` | `9b5c6b3dad10cd26b18492c52cf715d738cf9f9ee9153e81b9495c345863bdbf` | `2870e74dca87b0ca7f6ff615d2450626c4d2e4b70190d8dbed4d2e24d065bb15` | horizontal `FACING`, `buildStateDefinition`, facing outline provider |
| `net/minecraft/block/HorizontalFacingBlock.java` | `7ce4cee45329c93352c8a1740d691b046c8cd573dbb9e4b6549924f07f2fe1ad` | `7ce4cee45329c93352c8a1740d691b046c8cd573dbb9e4b6549924f07f2fe1ad` | `FACING = Properties.HORIZONTAL_FACING` |
| `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` | `HORIZONTAL_FACING` is horizontal plane; `AGE_7` range 0–7 |

For the target `WALLS` resource gate, reused the accepted 1.14.4 resource extraction `wall_resource_membership/extraction-record.md` (SHA-256 `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`) and independent resource verdict `wall_resource_membership_audit/verdict-r2.md` (SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`). Its exact `walls.json` payload SHA-256 is `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; the payload has only the 14 wall registrations and none of these six plant/stem keys.
