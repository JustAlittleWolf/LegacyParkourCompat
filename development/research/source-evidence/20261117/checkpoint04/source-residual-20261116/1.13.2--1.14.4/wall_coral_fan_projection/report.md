# Coral fan wall predicate projection: 1.13.2 → 1.14.4

## Scope and source identity

This packet compares only the ten exact common registry keys listed for `BaseCoralFanBlock` and `CoralFanBlock` in the current residual wall projection crosswalk:

- `minecraft:dead_tube_coral_fan`, `dead_brain_coral_fan`, `dead_bubble_coral_fan`, `dead_fire_coral_fan`, `dead_horn_coral_fan`
- `minecraft:tube_coral_fan`, `brain_coral_fan`, `bubble_coral_fan`, `fire_coral_fan`, `horn_coral_fan`

The `BaseCoralPlantBlock` keys are excluded; they are assigned to a separate packet. This does not claim anything about coral wall-fan blocks or other unexamined registry entries.

Exact Feather Gen2 build 2 ready identities:

- 1.13.2: source manifest `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` (`decompiled_minecraft/1.13.2/ornithe-feather.ready.json` and `.sources.sha256`)
- 1.14.4: source manifest `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc` (`decompiled_minecraft/1.14.4/ornithe-feather.ready.json` and `.sources.sha256`)

The exact row roster is `build/source-residual-20261116/1.13.2--1.14.4/wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv`, SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`. It has five rows whose old concrete class is `BaseCoralFanBlock` and five whose old concrete class is `CoralFanBlock`, each mapped to target `BaseCoralFanBlock` or `CoralFanBlock` as applicable. Each row records `WATERLOGGED:2` on both endpoints.

Relevant canonical Java files, with SHA-256 values verified against each endpoint's source sidecar:

| File | 1.13.2 | 1.14.4 |
|---|---|---|
| `net/minecraft/block/BaseCoralPlantTypeBlock.java` | `661b6d0f87c8a872acd945c0716aa396ac53932d773b17e31be95b224e531fc6` | `561ff00b85e21d4256ddc91250e0ee317b48dab946de3ac71cf47ead6514630e` |
| `net/minecraft/block/BaseCoralFanBlock.java` | `a330d4276a97b14c5038ddd817f11f976b7061d4ef004f608fa4e7c53d285516` | `7c90eb5d706a2a5471d14fd5b6637268d2d322c09e4f31d1ab13b4440198cbd0` |
| `net/minecraft/block/CoralFanBlock.java` | `26570c87d6bde252c8c126181fd80e64f25ac8d56f0726bfd1a09080e7ef9a22` | `d019c3615b51076dec48d238621fe6c8c7031ed65dfddb275b34c630188318b2` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |

## Provider and predicate paths

In 1.13.2, `BaseCoralPlantTypeBlock` declares the `WATERLOGGED` property and sets the default true, and its state definition adds that property. Its `getFaceShape` at lines 83–85 returns `FaceShape.UNDEFINED` for every face. `BaseCoralFanBlock` only specializes `getShape` (lines 8–18); `CoralFanBlock` inherits it and adds lifecycle methods, not a face-shape override. `Block.java` lines 1839–1867 registers all ten keys through these two classes with `.noCollision()` in the block properties.

Old `WallBlock.shouldConnectTo` at lines 51–55 connects when the face is `SOLID` (except its separate pole cases); `UNDEFINED` is neither. None of these coral classes is a fence gate, so all ten keys produce false wall bits for every registered state and horizontal face at this endpoint.

In 1.14.4, `BaseCoralPlantTypeBlock` continues to add `WATERLOGGED` (lines 84–86); its fan subclass returns the thin four-sixteenths-high outline shape in `BaseCoralFanBlock.getShape`, while the registration sets `.noCollision()` for all ten keys (`Blocks.java` lines 1730–1770). `Block.getCollisionShape` at lines 376–377 returns `VoxelShapes.empty()` when `hasCollision` is false; `Block.Properties.noCollision` sets that flag false (lines 853–854). New `WallBlock` lines 48–51 uses the result of `BlockState.m_87223014` (`Block.isFaceSolid`, whose Block implementation checks `BlockTags.LEAVES` or whether the collision shape has a full face at `Block.java` lines 402–403), then applies the attachment exception, WALLS tag, and fence-gate fallback. For these keys the collision-face boolean is false, they are not fence-gate instances, and they are not in the exact `WALLS` tag list. Thus the target wall bit is false for every registered state and horizontal face.

The `WALLS` resource payload used for the last exclusion is the locally extracted pinned raw-client entry at `build/source-residual-20261116/1.13.2--1.14.4/wall_resource_membership/payloads/data/minecraft/tags/blocks/walls.json`, SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; its 14 values contain only wall block keys and no coral fan key. The existing source resource audit verdict is `wall_resource_membership_audit/verdict-r2.md`, SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`.

## Result and limits

For five dead-fan and five live-fan keys, each with two registered `WATERLOGGED` states and four horizontal neighbor faces, the wall-connection predicate is `false` at both endpoints in all 80 key/state/direction cases. This is a bounded provider/predicate no-difference result. It does not establish movement impact, shape parity for unrelated consumers, or pair completion.

The current r2 crosswalk corrected the four-state `FACING` cardinality for carved-pumpkin/jack-o-lantern rows. The historical r1 TSV was overwritten, so its auditor could not byte-diff every unchanged r1 row. That limitation remains attached to the crosswalk; this packet relies only on the exact current rows named above and does not upgrade the crosswalk or freeze the pair.
