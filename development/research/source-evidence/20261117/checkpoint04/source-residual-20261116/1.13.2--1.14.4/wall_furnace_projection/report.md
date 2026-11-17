# Furnace wall-neighbor predicate: 1.13.2 → 1.14.4

## Exact source identity and scope

This is a bounded comparison of the common `minecraft:furnace` key's four horizontal neighbor bits in the legacy and target `WallBlock` predicates. It does not infer a player trajectory or freeze the release pair. Canonical decompiled sources were read only.

Both endpoints are Ornithe Feather Gen2 build 2. The ready-marker source-manifest digests are 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`; both match the corresponding `ornithe-feather.sources.sha256` bytes and the source entries used below.

I checked the existing residual wall reports for scope overlap. The accepted container projection names chest, trapped chest, ender chest, hopper, anvil variants, daylight detector and enchanting table; the simple-utility projection names cactus, snow, cake, farmland, grass path, daylight detector, enchanting table and structure void. Neither includes furnace. The sign/banner/head projection and six old wall attachment-exception families also do not include furnace. The source-only residual crosswalk input `wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv` (SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`) marks `minecraft:furnace` unexamined for this predicate. Its state count is corrected below from six `FACING` values to four horizontal values, as established by the exact `HorizontalFacingBlock.FACING` property.

## Registrations, state domain, and provider path

1.13.2 registers `minecraft:furnace` as `new FurnaceBlock(...)` in `net/minecraft/block/Block.java:1002`. Target 1.14.4 registers the same key as `new C_53340811(...)` in `net/minecraft/block/Blocks.java:428`. `C_53340811` extends `FurnaceBlock` and does not override shape methods. At both endpoints `FurnaceBlock` extends `BlockWithBlockEntity`, declares `FACING = HorizontalFacingBlock.FACING` and `LIT = RedstoneTorchBlock.LIT`, and builds state definitions from exactly those two properties (`FurnaceBlock.java:23–33, 139–140` old; `:23–28, 101–102` target).

`HorizontalFacingBlock.FACING` aliases `Properties.HORIZONTAL_FACING`. That property is constructed from `Direction.Plane.HORIZONTAL`, whose exact members are NORTH, EAST, SOUTH and WEST; it therefore has four values. `LIT` is `Properties.LIT`, a boolean property with false and true values. The registered domain is consequently **4 × 2 = 8 states at both endpoints**, not the 12-state `FACING:6,LIT:2` count in the earlier crosswalk. Neither property selects a collision shape.

Old 1.13.2 `BlockState.getFaceShape(world,pos,face)` delegates to `getBlock().getFaceShape(world,state,pos,face)` (`block/state/BlockState.java:285–286`). `FurnaceBlock` has no face-shape override, so it inherits `Block.getFaceShape`, which returns `FaceShape.SOLID` for any direction (`Block.java:383–384`). `WallBlock.shouldConnectTo` accepts `SOLID` when the neighbor is not in `isExceptionForConnection` (`WallBlock.java:51–66`). Furnace is absent from the old exception members; its inherited solid face therefore yields a true connection bit in every horizontal direction, for every one of the eight states.

Target 1.14.4 `C_53340811` inherits the `FurnaceBlock` state and shape behavior; neither this class nor `FurnaceBlock` overrides `getShape` or `getCollisionShape`. Target `BlockState.m_87223014` returns the cached face-solid value or calls `Block.isFaceSolid` (`block/state/BlockState.java:326–327`; cache construction at `:410,416`). The helper rejects only `LEAVES` before checking whether the directional face of the collision shape is full (`Block.java:402–408`). Furnace is not a leaves-tagged block.

The effective target collision route is inherited `Block.getCollisionShape` → `BlockState.getSHape` → `Block.getShape` (`Block.java:371–378`; `block/state/BlockState.java:193–203`). The base shape is `VoxelShapes.block()` (`Block.java:371–373`), and collision is enabled by default (`Block.java:811`). The furnace registration supplies ordinary stone properties without calling `noCollision`; that method is the only relevant property setter here and sets `hasCollision=false` (`Block.java:853–855`). Thus every directional collision-face projection is a full face and `Block.isFaceSolid` is true for all eight states.

Target `WallBlock.m_71853799` combines that boolean with the attachment-exception and alternate WALLS/fence-gate branches (`WallBlock.java:48–52`). Furnace is not an attachment exception, a `WALLS` member, or a fence gate. The generic face-solid branch alone yields true in each horizontal direction.

## Bounded result

| Exact key | Registered states at both endpoints | Old horizontal wall bits (N/E/S/W) | Target horizontal wall bits (N/E/S/W) | Result |
|---|---:|---|---|---|
| `minecraft:furnace` | `FACING={NORTH,EAST,SOUTH,WEST}` × `LIT={false,true}` = 8 | true in all directions for all states | true in all directions for all states | No difference in this bounded predicate slice |

The result closes only the furnace neighbor-predicate row. It does not claim the pair's complete block-provider inventory, movement result, or full-pair source freeze.

## Source hashes

Each digest below was read from the canonical file and matched its endpoint manifest entry.

| Relative Java path | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| `net/minecraft/block/BlockWithBlockEntity.java` | `9f190eebfcff3f23ce76bceafd6295d4e84d93cc3a76d85f83972792c811a266` | `dc644d4ab779d5ee65f5a0828097d3732efdbd9cc8fc8ecd1660ae7f1b322a2a` |
| `net/minecraft/block/HorizontalFacingBlock.java` | `7ce4cee45329c93352c8a1740d691b046c8cd573dbb9e4b6549924f07f2fe1ad` | `7ce4cee45329c93352c8a1740d691b046c8cd573dbb9e4b6549924f07f2fe1ad` |
| `net/minecraft/block/FurnaceBlock.java` | `6e946bbdf3fcbcd74146c08ec6f1d1d044ffaa471f4418b38098f9acb9938542` | `c152b8d5c309056cfed0d76a90069b9b9a7070b6d03edd20eee3f40f90ea406d` |
| `net/minecraft/unmapped/C_53340811.java` | — | `91efb41a33e8548ce157741391217d953363aa61854ee02dc57482ef4d094a93` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/RedstoneTorchBlock.java` | `d6be1c4a35b5112012e4b583310836bac3cac5e735e2671bb33c0cf9a8a7710f` | `dd5b2d30b0742609d6710c617013303e3d89db758f62043f454ea1dcd11f7612` |
| `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |
| `net/minecraft/state/property/BooleanProperty.java` | `7a414a5c7a8110a97b32fce9ad0bfa9df0212f286bfbe5b777962205cc9dcf11` | `7a414a5c7a8110a97b32fce9ad0bfa9df0212f286bfbe5b777962205cc9dcf11` |
| `net/minecraft/state/property/DirectionProperty.java` | `3d36632d97141b65d1c45fe11d632d7cf6c5ef0c7c08cf7b57635fb87e8ac188` | `3d36632d97141b65d1c45fe11d632d7cf6c5ef0c7c08cf7b57635fb87e8ac188` |
| `net/minecraft/util/math/Direction.java` | `6d5c496ff65377fee9f00c826e48c7be14d3889e5ae5191e110a464007549082` | `121f8b78eda07640eccbe369f40acc7ccbdcb275dcb6aca87c4495cd193b01c0` |
