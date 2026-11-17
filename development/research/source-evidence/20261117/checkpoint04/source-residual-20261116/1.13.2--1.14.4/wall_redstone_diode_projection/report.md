# Repeater and comparator wall-neighbor predicates: 1.13.2 → 1.14.4

## Source identity and scope

This is a source-only comparison of the exact common registered keys `minecraft:repeater` and `minecraft:comparator`, limited to their horizontal wall-neighbor connection predicates. Endpoints are Ornithe Feather Gen2 build 2: 1.13.2 source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`, and 1.14.4 source-manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. Each ready marker binds the corresponding sidecar source manifest. Every Java hash below was checked against both canonical file bytes and the exact version's source-hash sidecar.

The input unexamined-key crosswalk is `wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv`, SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`. This packet covers only the two rows named above. The crosswalk's stated `FACING:6` property count is incorrect for these rows; the exact inherited property is `HorizontalFacingBlock.FACING = Properties.HORIZONTAL_FACING`, whose `Direction.Plane.HORIZONTAL` values are NORTH, EAST, SOUTH, WEST (four) at both endpoints.

## Registered state domains

Both versions register the same two keys and classes. The old registrations are in `Block.java` (repeater line 1091, comparator line 1296); the target registrations are in `Blocks.java` (repeater line 597, comparator lines 942–943). The registrations pass `Block.Properties.of(Material.DECORATION).instabreak().sounds(BlockSounds.WOOD)` and do not call `noCollision()`.

| Key | State properties in both releases | Registered combinations |
|---|---|---:|
| `minecraft:repeater` | `FACING` = NORTH/EAST/SOUTH/WEST (4); `DELAY` = 1–4 (4); `LOCKED` = false/true (2); `POWERED` = false/true (2) | 64 |
| `minecraft:comparator` | `FACING` = NORTH/EAST/SOUTH/WEST (4); `MODE` = COMPARE/SUBTRACT (2); `POWERED` = false/true (2) | 16 |

The property builders in `RepeaterBlock` and `ComparatorBlock` are unchanged in kind at both endpoints. `HorizontalFacingBlock` obtains `FACING` from `Properties.HORIZONTAL_FACING`; `Properties.DELAY` is 1 through 4; `LOCKED` and `POWERED` are Boolean properties; and `ComparatorMode` has exactly COMPARE and SUBTRACT. Thus the slice has 80 registered key/state combinations. Four horizontal directions are checked per state, for 320 predicate cases.

## Predicate comparison

**1.13.2:** Both concrete blocks inherit `DiodeBlock.getFaceShape`. Its return is `SOLID` only for `Direction.DOWN`; for each horizontal face it returns `UNDEFINED`. `WallBlock` queries the neighbor's opposite horizontal face and `shouldConnectTo` accepts only `SOLID`, or a middle-pole shape under its fence-gate clause. The diode result is neither, so both keys produce a false wall connection bit for all registered states and horizontal directions.

**1.14.4:** Both concrete blocks inherit `DiodeBlock.getShape`, which returns the state-independent `SHAPE = Block.block(0, 0, 0, 16, 2, 16)`: a full-width 2/16-high slab. The `Block.Properties` default keeps `hasCollision = true`; these registrations do not disable it. `Block.getCollisionShape` therefore returns that shape. `BlockState.m_87223014` uses the cached or direct `Block.isFaceSolid` result; that method extracts the collision shape's queried face and requires the result to be a full block. On each horizontal face, the extracted face has only the slab's 2/16 vertical extent, so it is not a full block. The face-solid argument to `WallBlock`'s generic arm is false for every state and horizontal direction.

The target's separate `WALLS`/fence-gate arm is also false: neither key occurs in the exact 1.14.4 `data/minecraft/tags/blocks/walls.json` payload (SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`, 14 values), and neither block is a `FenceGateBlock`. This payload's source extraction and membership audit are reused from `wall_resource_membership`; the independent resource ACCEPT verdict is `wall_resource_membership_audit/verdict-r2.md`, SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`. No new resource extraction was performed. The target attachment-exception condition cannot turn the false face and false tag/gate branches into a connection.

**Result:** no difference in the wall-neighbor predicate for repeater or comparator across these endpoints. Their state properties do not affect either face result. This closes only these two predicate rows; it establishes no player trajectory consequence and does not freeze the pair.

## Source anchors and hashes

| Source file | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/DiodeBlock.java` | `972925eff56fcaa0f1f00662679c12505a3e64dc79b24996d26110ec14b8608b` | `54b3f2984c4b26a25e183fc8e958bfdbcdc66ca8950b78847aa1040faf2494a8` |
| `net/minecraft/block/HorizontalFacingBlock.java` | `7ce4cee45329c93352c8a1740d691b046c8cd573dbb9e4b6549924f07f2fe1ad` | `7ce4cee45329c93352c8a1740d691b046c8cd573dbb9e4b6549924f07f2fe1ad` |
| `net/minecraft/block/RepeaterBlock.java` | `c13357f93761ad9be3f5e26d07ab2a086a8b8bb3949165a7e01be4811da8ba65` | `35aa4f31a6c2280f610e27b39a2ade29a67edc2a6bf46378fad595a27230587b` |
| `net/minecraft/block/ComparatorBlock.java` | `e9a0910b44c8fca00cff9bbe54f5227dac4b90401390d1f9529f668933777fe2` | `a01c308d27e9456be827e28505ed90676ae2a51b330d1a4d479f6b0052681bf1` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |
| `net/minecraft/block/state/property/ComparatorMode.java` | `6c3c395bcd232d377466aab7abf13522db5623246bf23ee5de9449e6e2a8e995` | `6c3c395bcd232d377466aab7abf13522db5623246bf23ee5de9449e6e2a8e995` |
| `net/minecraft/util/math/Direction.java` | `6d5c496ff65377fee9f00c826e48c7be14d3889e5ae5191e110a464007549082` | `121f8b78eda07640eccbe369f40acc7ccbdcb275dcb6aca87c4495cd193b01c0` |
| `net/minecraft/block/shape/VoxelShape.java` | `d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1` | `8efb68bf9d5efdb0ec48e401d1852e90458c7094f8fa0c8e9ae418f268c40fdd` |
| `net/minecraft/block/shape/VoxelShapes.java` | `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95` | `51a09c297c7f01c8bad9b66af2a80900e95f33c6e3a250ce599f4817433587d9` |
| `net/minecraft/block/shape/SliceShape.java` | `de082958ebe09f02d999e39d15bfea8bbc22b5501065f7b92718bdb4e0d34c87` | `cdc2794ebcf185410c9171ce72ce9a63a8d1f32c067496c8624125ef77861455` |

Relevant anchors: old `DiodeBlock.java:19,27–29,217–219`, `RepeaterBlock.java:18–24,87–89`, `ComparatorBlock.java:22–30,169–171`, `BlockState.java:285–286`; old `WallBlock.java:51–55,81–84`; target `DiodeBlock.java:21,29–31`, `RepeaterBlock.java:18–24,86–88`, `ComparatorBlock.java:26–32,163–165`; target `WallBlock.java:48–51,67–70`, `Block.java:84–90,376–378,402–413`, `BlockState.java:197–202,326–331,405–419`, `VoxelShape.java:170–199`, `VoxelShapes.java:317–331`, `SliceShape.java:8–28`; property/domain anchors are in `HorizontalFacingBlock.java`, `Properties.java`, `Direction.java:400/415`, and `ComparatorMode.java`.
