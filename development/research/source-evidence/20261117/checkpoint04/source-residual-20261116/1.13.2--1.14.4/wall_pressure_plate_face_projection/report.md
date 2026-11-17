# Pressure-plate wall-face projection (bounded source report)

## Scope and identities

This report covers only the nine common registered keys named in the accepted residual input `build/source-residual-20261116/1.13.2--1.14.4/wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv` (input SHA-256 `5deaea77f1ab904cadbc2a3338e8c31a92ec577c3c291932688aedef96c227b4`): `stone_pressure_plate`, `light_weighted_pressure_plate`, `heavy_weighted_pressure_plate`, and `oak_pressure_plate`, `spruce_pressure_plate`, `birch_pressure_plate`, `jungle_pressure_plate`, `acacia_pressure_plate`, `dark_oak_pressure_plate`.

Endpoint source identities are the ready Ornithe Feather manifests: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. The ready markers bind those manifests. The following relevant source-file hashes were checked against the source manifests and local bytes:

| Version | File | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/block/AbstractPressurePlateBlock.java` | `c920500d5b908f6f516e04b6c312ba7c848c3cfa97b24d3427ccf10d2b0de701` |
| 1.13.2 | `net/minecraft/block/PressurePlateBlock.java` | `9ab5cf13a568854699c4a06e1b25a9a606a83ab1aee5028e24300ef98111ac42` |
| 1.13.2 | `net/minecraft/block/WeightedPressurePlateBlock.java` | `f098879f6e52bab3f5371b603af36888d6fec455134ca9afae949954afafdb49` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` |
| 1.13.2 | `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` |
| 1.14.4 | `net/minecraft/block/AbstractPressurePlateBlock.java` | `23fa38e232fd91a736ae79c8d038c317707b72a2f312467efbacca42b903b1c3` |
| 1.14.4 | `net/minecraft/block/PressurePlateBlock.java` | `82dc2fda777367e78d27775bcd62f967e05984c5b4f3ff89945acca089374428` |
| 1.14.4 | `net/minecraft/block/WeightedPressurePlateBlock.java` | `ab382a01851b35aab6813a3e37f1b0637b5c9ebf5b03b2a780a89306f327b4f1` |
| 1.14.4 | `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.14.4 | `net/minecraft/block/WallBlock.java` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| 1.14.4 | `net/minecraft/state/property/Properties.java` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |

The endpoint registration source files are 1.13.2 `Block.java` (SHA above) and 1.14.4 `Blocks.java` (SHA-256 `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`). Both registries bind stone and six wood keys to `PressurePlateBlock`, and both weighted keys to `WeightedPressurePlateBlock`; no key in this nine-row scope changes owner class between endpoints. Both `Properties.java` files define `POWER` as integer 0–15 and `POWERED` as Boolean.

## Effective shape and wall predicate

At both endpoints, `AbstractPressurePlateBlock.getShape` chooses `PRESSED_SHAPE` when `getOutputSignal(state)>0`, otherwise `UNPRESSED_SHAPE`. The shapes are the same: pressed `[1,0,1]..[15,0.5,15]` and unpressed `[1,0,1]..[15,1,15]` in sixteenths of a block (respectively 1/32 and 1/16 block high). The unpressed/pressed shape change is only vertical height and retains the 1/16 inset on every horizontal side.

All nine registrations set `.noCollision()`. At 1.14.4 `Block.getCollisionShape` returns the empty shape when `hasCollision` is false; otherwise it delegates to the state shape. Therefore these blocks' **effective collision shapes are empty for every registered state**, regardless of visible outline shape. `Block.isFaceSolid` requires a non-leaf state whose effective collision shape has a full face; for each horizontal direction it is false here.

At 1.13.2 `AbstractPressurePlateBlock.getFaceShape` returns `FaceShape.UNDEFINED` unconditionally. `WallBlock.shouldConnectTo` accepts the generic face arm only for `FaceShape.SOLID` (or the middle-pole cases for fence gates); pressure plates provide neither. The old pressure-plate exception override is not needed for this result. At 1.14.4 the generic wall arm's `bl` is the full-face result from `Block.isFaceSolid`; its alternative arm admits only `BlockTags.WALLS` or the direction-matching `FenceGateBlock` case. Pressure plates are registered as neither wall-tagged wall blocks nor fence gates in this code path, so that alternative does not apply. Thus the changed generic predicate yields the same false result.

## Per-key state and four-direction result

`PressurePlateBlock` has Boolean `POWERED` (`false`,`true`), mapping to output 0 or 15. `WeightedPressurePlateBlock` has integer `POWER` 0–15, mapping directly to its output signal; this is not a Boolean state. These exhaust the registered property domains for the respective owners at both endpoints. The corresponding output picks one of the two outline shapes above, but both states resolve to empty collision shape because the registration disables collision.

| Key family | Registered keys | State domain evaluated | 1.13.2 old `FaceShape` result N/E/S/W | 1.14.4 `isFaceSolid` / wall generic result N/E/S/W |
|---|---|---|---|---|
| Stone and six wood plates | `stone`, `oak`, `spruce`, `birch`, `jungle`, `acacia`, `dark_oak` pressure plate | `POWERED=false,true` each | undefined / no arm in all directions | false / no arm in all directions |
| Weighted plates | `light_weighted`, `heavy_weighted` pressure plate | `POWER=0..15` each | undefined / no arm in all directions | false / no arm in all directions |

**Verdict:** no wall generic-arm/full-face change for these nine registered keys, for any legal listed state or horizontal direction. This closes only this exact roster slice. It does not assert broader registry/provider, neighbor-state, collision-query, trajectory, or pair completion.
