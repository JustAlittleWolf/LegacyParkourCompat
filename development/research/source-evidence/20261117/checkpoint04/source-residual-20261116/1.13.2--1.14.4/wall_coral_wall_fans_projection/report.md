# Coral wall-fan wall-neighbor predicate projection: 1.13.2 to 1.14.4

## Exact source identity

Both canonical endpoint trees are ready Ornithe Feather Gen2 build 2 sources. Their ready markers bind the trees to these source-manifest SHA-256 values:

- 1.13.2: `decompiled_minecraft/1.13.2/ornithe-feather.ready.json`, manifest `ornithe-feather.sources.sha256`, SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- 1.14.4: `decompiled_minecraft/1.14.4/ornithe-feather.ready.json`, manifest `ornithe-feather.sources.sha256`, SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`.

The table records SHA-256 of each Java source in the canonical tree indicated by its endpoint ready marker.

| Endpoint | Source file | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` |
| 1.13.2 | `net/minecraft/block/BaseCoralWallFanBlock.java` | `12326ecd456026b98d1b4471f54cf5f399763e33833bb4224c3e88592a4ecef0` |
| 1.13.2 | `net/minecraft/block/CoralWallFanBlock.java` | `f85288a635e27c9222ccde668b8580f0ec715b9f8f661e3fe3aef34f7f02bd12` |
| 1.13.2 | `net/minecraft/block/BaseCoralFanBlock.java` | `a330d4276a97b14c5038ddd817f11f976b7061d4ef004f608fa4e7c53d285516` |
| 1.13.2 | `net/minecraft/block/BaseCoralPlantTypeBlock.java` | `661b6d0f87c8a872acd945c0716aa396ac53932d773b17e31be95b224e531fc6` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.13.2 | `net/minecraft/block/HorizontalFacingBlock.java` | `7ce4cee45329c93352c8a1740d691b046c8cd573dbb9e4b6549924f07f2fe1ad` |
| 1.13.2 | `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` |
| 1.13.2 | `net/minecraft/state/property/DirectionProperty.java` | `3d36632d97141b65d1c45fe11d632d7cf6c5ef0c7c08cf7b57635fb87e8ac188` |
| 1.13.2 | `net/minecraft/state/property/BooleanProperty.java` | `7a414a5c7a8110a97b32fce9ad0bfa9df0212f286bfbe5b777962205cc9dcf11` |
| 1.13.2 | `net/minecraft/util/math/Direction.java` | `6d5c496ff65377fee9f00c826e48c7be14d3889e5ae5191e110a464007549082` |
| 1.14.4 | `net/minecraft/block/Blocks.java` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| 1.14.4 | `net/minecraft/block/WallBlock.java` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| 1.14.4 | `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.14.4 | `net/minecraft/block/state/BlockState.java` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| 1.14.4 | `net/minecraft/block/BaseCoralWallFanBlock.java` | `91ed215892e79082363b375cae70225abe5f4b545296f13cab4c3a2418c36f03` |
| 1.14.4 | `net/minecraft/block/CoralWallFanBlock.java` | `29f213cca08e66d999aac9ce555ead0aa217a2c738c81124be8a738d97359a89` |
| 1.14.4 | `net/minecraft/block/BaseCoralFanBlock.java` | `7c90eb5d706a2a5471d14fd5b6637268d2d322c09e4f31d1ab13b4440198cbd0` |
| 1.14.4 | `net/minecraft/block/BaseCoralPlantTypeBlock.java` | `561ff00b85e21d4256ddc91250e0ee317b48dab946de3ac71cf47ead6514630e` |
| 1.14.4 | `net/minecraft/block/HorizontalFacingBlock.java` | `7ce4cee45329c93352c8a1740d691b046c8cd573dbb9e4b6549924f07f2fe1ad` |
| 1.14.4 | `net/minecraft/state/property/Properties.java` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |
| 1.14.4 | `net/minecraft/state/property/DirectionProperty.java` | `3d36632d97141b65d1c45fe11d632d7cf6c5ef0c7c08cf7b57635fb87e8ac188` |
| 1.14.4 | `net/minecraft/state/property/BooleanProperty.java` | `7a414a5c7a8110a97b32fce9ad0bfa9df0212f286bfbe5b777962205cc9dcf11` |
| 1.14.4 | `net/minecraft/util/math/Direction.java` | `121f8b78eda07640eccbe369f40acc7ccbdcb275dcb6aca87c4495cd193b01c0` |

## Exact unexamined crosswalk slice and state domain

The source-only residual roster is `wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv`, SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`. This packet covers only these 10 exact common keys from that roster:

- `minecraft:dead_tube_coral_wall_fan`
- `minecraft:dead_brain_coral_wall_fan`
- `minecraft:dead_bubble_coral_wall_fan`
- `minecraft:dead_fire_coral_wall_fan`
- `minecraft:dead_horn_coral_wall_fan`
- `minecraft:tube_coral_wall_fan`
- `minecraft:brain_coral_wall_fan`
- `minecraft:bubble_coral_wall_fan`
- `minecraft:fire_coral_wall_fan`
- `minecraft:horn_coral_wall_fan`

The older `Block.java` registrations instantiate five dead keys as `BaseCoralWallFanBlock` and five live keys as `CoralWallFanBlock`, all with `.noCollision()`. Target `Blocks.java` registers the same five dead keys as `BaseCoralWallFanBlock` and live keys as `CoralWallFanBlock`, also with `.noCollision()`.

Both endpoint class chains are `CoralWallFanBlock -> BaseCoralWallFanBlock -> BaseCoralFanBlock -> BaseCoralPlantTypeBlock`; dead keys are direct `BaseCoralWallFanBlock` instances. `BaseCoralWallFanBlock.buildStateDefinition` adds `FACING` and inherited `WATERLOGGED`. `FACING` is `HorizontalFacingBlock.FACING`, which resolves to `Properties.HORIZONTAL_FACING`, backed by `Direction.Plane.HORIZONTAL` = NORTH/EAST/SOUTH/WEST (4 values). `BooleanProperty.values()` is true/false (2 values). Thus each key has 8 states (4 × 2), and this slice has 80 key-state combinations.

The crosswalk TSV transcribes `FACING:6,WATERLOGGED:2` for these rows. That cardinality is inconsistent with the cited exact source chain: `HorizontalFacingBlock.FACING` is the horizontal property, `Direction.Plane.HORIZONTAL` contains four directions, and its `DirectionProperty.of` filters the six `Direction` values to those four. This packet corrects the state-domain count to four FACING values, not six.

## Wall predicate comparison

**1.13.2.** `BaseCoralPlantTypeBlock.getFaceShape` returns `FaceShape.UNDEFINED`; `BaseCoralWallFanBlock` does not override that method. `WallBlock.shouldConnectTo` accepts non-exception `FaceShape.SOLID` or a middle-pole shape (the `MIDDLE_POLE` case is restricted to fence gates). Therefore the old wall bit is false for all four horizontal directions and every FACING/WATERLOGGED state of these keys.

**1.14.4.** `WallBlock` calls `BlockState.m_87223014` to query a neighbor face and passes the result to `m_71853799`, whose alternate positive routes are WALLS-tag membership or a directional fence gate. `BlockState.m_87223014` delegates to `Block.isFaceSolid`; `Block.getCollisionShape` returns `VoxelShapes.empty()` when `hasCollision` is false, and these exact registrations set it false via `.noCollision()`. The directional outline returned by `BaseCoralWallFanBlock.getShape` is consequently not used by this collision-face query. The five keys are not fence gates and are absent from the accepted 1.14.4 WALLS resource inventory (`wall_resource_membership_audit/verdict-r2.md`, SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`; the independently checked payload has 14 values and no coral wall-fan key). Thus the target wall bit is false for all four directions and every registered state.

## Bounded result and limits

For these 10 exact keys, all 320 key-state-direction cases (10 × 8 × 4) are false in both releases. This closes only the wall-neighbor predicate slice for these keys. It does not establish movement trajectories, state-transition behavior, collision-provider parity beyond this predicate, other coral block/fan families, or full pair coverage. The pair remains partial; no pair freeze is asserted.



