# Axis/log generic wall-face projection: 1.13.2 → 1.14.4

## Exact inputs

Both endpoints are Ornithe Feather Gen2 build 2. Ready-marker source-manifest identities: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. Canonical source files are read-only and their file hashes below match the endpoint `ornithe-feather.sources.sha256` manifests.

Residual row input: `wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv`, SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`. Selected only rows whose old and target classes are `AxisBlock` or `AbstractLogBlock`, with old `Block.getFaceShape` owner. No overlapping accepted packet reference is asserted by these rows.

| Release | Path | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/AxisBlock.java` | `6d80b24a181d5891794b16576d4641fd25b2b8c5eb879362ffdca671975919a4` |
| 1.13.2 | `net/minecraft/block/AbstractLogBlock.java` | `cd3b60a9250bfa699d9ec5889709bd941490a57942189e37e0d78370a476915c` |
| 1.13.2 | `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.13.2 | `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` |
| 1.13.2 | `net/minecraft/util/math/Direction.java` | `6d5c496ff65377fee9f00c826e48c7be14d3889e5ae5191e110a464007549082` |
| 1.13.2 | `net/minecraft/block/Block.java` registration section | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.14.4 | `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.14.4 | `net/minecraft/block/AxisBlock.java` | `6d80b24a181d5891794b16576d4641fd25b2b8c5eb879362ffdca671975919a4` |
| 1.14.4 | `net/minecraft/block/AbstractLogBlock.java` | `cd3b60a9250bfa699d9ec5889709bd941490a57942189e37e0d78370a476915c` |
| 1.14.4 | `net/minecraft/block/WallBlock.java` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| 1.14.4 | `net/minecraft/block/state/BlockState.java` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| 1.14.4 | `net/minecraft/state/property/Properties.java` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |
| 1.14.4 | `net/minecraft/util/math/Direction.java` | `121f8b78eda07640eccbe369f40acc7ccbdcb275dcb6aca87c4495cd193b01c0` |
| 1.14.4 | `net/minecraft/block/Blocks.java` registrations | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |

## Exact unexamined rows and state domain

The selected rows contain 27 common exact keys: `acacia_log`, `acacia_wood`, `birch_log`, `birch_wood`, `bone_block`, `dark_oak_log`, `dark_oak_wood`, `jungle_log`, `jungle_wood`, `oak_log`, `oak_wood`, `purpur_pillar`, `quartz_pillar`, `spruce_log`, `spruce_wood`, and the twelve `stripped_{acacia,birch,dark_oak,jungle,oak,spruce}_{log,wood}` keys. Every row has only `AXIS` in its state definition. `AxisBlock.buildStateDefinition` adds `AXIS`, `Properties.AXIS` is an `EnumProperty<Direction.Axis>`, and both endpoint `Direction.Axis` declarations have exactly `X`, `Y`, `Z`; hence 81 common legal key/state combinations (27 × 3). `AbstractLogBlock` extends `AxisBlock`; its only override is `getMapColor`.

## Predicate and provider comparison

- 1.13.2 `Block.getFaceShape` returns `FaceShape.SOLID` for these classes: `AxisBlock` and `AbstractLogBlock` do not override it. `BlockState.getFaceShape` delegates directly to that method. `WallBlock.shouldConnectTo` accepts the solid-face arm unless `isExceptionForConnection` rejects the block. Its inherited attachment exceptions and its extra barrier/melon/pumpkin/frosted-ice/TNT list contain neither `AxisBlock` nor `AbstractLogBlock`.
- The 1.13.2 registrations construct all selected keys as `AxisBlock` or `AbstractLogBlock`; the supplied `Block.Properties` do not call `noCollision`. `Block.Properties.hasCollision` defaults to true. `Block.getShape` is the full `VoxelShapes.block()` cube, and `Block.getCollisionShape` returns state shape when `hasCollision` is true.
- 1.14.4 keeps the same `AxisBlock` and `AbstractLogBlock` source bytes. Registrations likewise construct the selected keys as those classes with no `.noCollision()` call; target `Block.Properties.hasCollision` also defaults true. `Block.getShape` remains the full cube and `Block.getCollisionShape` routes to state shape through the default collision-enabled gate.
- `Block.isFaceSolid` evaluates `!state.is(BlockTags.LEAVES) && isFaceFull(state.getCollisionShape(...), face)`. None of the selected classes is `LeavesBlock`, and their target shape is the full cube, so all four horizontal face tests are true for every selected state. `BlockState.m_87223014` is the cached state-face path used by target `WallBlock`'s generic connection arm. Target `WallBlock`'s attachment exception list contains leaves, barrier and pumpkin/melon entries, but not these classes; its walls-tag arm is not needed to reach the true generic face-solid arm.

## Bounded verdict

For all 27 exact common registry keys, all three axis states and all four horizontal directions, the wall neighbor bit is true on both endpoints. This closes only the old `AxisBlock`/`AbstractLogBlock` generic-face rows selected from the supplied residual TSV (324 key/state/direction cases). It establishes no movement trajectory, other provider families, external state reachability, intermediate patch boundary or full-pair freeze. Remaining unexamined crosswalk rows and whole-pair dependencies remain open.
