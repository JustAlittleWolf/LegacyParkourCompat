# Redstone torch wall-neighbor predicate: 1.13.2 → 1.14.4

## Exact source identity

Both endpoints are Ornithe Feather Gen2 build 2, verified against ready markers and canonical source manifests:

- 1.13.2 `decompiled_minecraft/1.13.2/ornithe-feather/ornithe-feather.sources.sha256`: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- 1.14.4 `decompiled_minecraft/1.14.4/ornithe-feather/ornithe-feather.sources.sha256`: `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`.

The exact unexamined-key roster (`wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv`, SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`) identifies only `minecraft:redstone_torch` for this assignment. The full source dispatch crosswalk is SHA-256 `7b8d10690d85824b2605c701651b516fe82d654908e7d22c6e62eca08d30bdff`. Source paths below are relative to each `ornithe-feather/` root; every listed Java digest was checked against both canonical bytes and its endpoint manifest entry.

| Source | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `net/minecraft/block/TorchBlock.java` | `da11308e51f57ce95764f2ee645dde8d15607914a3a68bac9c0635ab3d1531e0` | `8430a1058184d9e87596d5ba961ce092e6c77469c642f50e5c901db1a0588aab` |
| `net/minecraft/block/RedstoneTorchBlock.java` | `d6be1c4a35b5112012e4b583310836bac3cac5e735e2671bb33c0cf9a8a7710f` | `dd5b2d30b0742609d6710c617013303e3d89db758f62043f454ea1dcd11f7612` |
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| Registration source (`Block.java` old / `Blocks.java` target) | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |

## Bounded comparison

The registered key is `minecraft:redstone_torch` at both endpoints. Both `RedstoneTorchBlock` definitions add only the Boolean `LIT` property (two registered states, `false` and `true`); both registrations use `Block.Properties...noCollision()`.

At 1.13.2, `WallBlock.getPlacementState` and the horizontal neighbor-update paths call `BlockState.getFaceShape` (`WallBlock.java:81–84, 106–115`; `BlockState.java:285–286`). The state dispatches to the block's face method. `RedstoneTorchBlock` inherits `TorchBlock.getFaceShape`, which unconditionally returns `FaceShape.UNDEFINED` (`TorchBlock.java:67–69`). `WallBlock.shouldConnectTo` accepts the generic face branch only for `FaceShape.SOLID`; its other accepted branch is a thick pole or a middle pole that is a `FenceGateBlock` (`WallBlock.java:51–55`). Thus both `LIT` states produce a false wall bit in every horizontal direction.

At 1.14.4, `WallBlock` passes `BlockState.m_87223014` to `m_71853799` for each horizontal neighbor (`WallBlock.java:48–52, 67–70, 93–102`; `BlockState.java:326–327`). `m_87223014` uses its face-solid cache or falls back to `Block.isFaceSolid`; that helper tests the collision shape (`Block.java:402–403`; `BlockState.java:416`). The registered `hasCollision` value is false because of `.noCollision()`, and base `Block.getCollisionShape` therefore returns `VoxelShapes.empty()` (`Block.java:376–377, 854`). The inherited floor outline in `TorchBlock.getShape` does not affect this empty collision result. Hence the generic face-solid input is false for both `LIT` states in every horizontal direction.

The remaining target wall predicate arms do not connect this neighbor: `minecraft:redstone_torch` is absent from the exact target `WALLS` resource payload (`wall_resource_membership/payloads/data/minecraft/tags/blocks/walls.json`, SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; independent resource audit verdict SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`), and its block is not a `FenceGateBlock`. The key is not the distinct `redstone_wall_torch` registration; plain `torch` and `redstone_wall_torch` packets were not reused as this result.

| Key and registered state | 1.13.2 wall bit, N/E/S/W | 1.14.4 wall bit, N/E/S/W | Result |
|---|---|---|---|
| `redstone_torch`, `LIT=false` | false / false / false / false | false / false / false / false | no difference |
| `redstone_torch`, `LIT=true` | false / false / false / false | false / false / false / false | no difference |

This closes only the `minecraft:redstone_torch` wall-neighbor predicate for its two registered states. It makes no player-trajectory or movement-impact claim and does not close the pair's other provider, state, callback, or tick dependencies.
