# Snow block and non-vanilla ice wall-neighbor projection: 1.13.2 → 1.14.4

## Scope and source identity

This bounded source comparison covers only the three exact common registry keys `minecraft:snow_block`, `minecraft:packed_ice`, and `minecraft:blue_ice`, assigned from the current unexamined wall-projection crosswalk. It compares the four horizontal neighbor bits in `WallBlock`; it does not assert full block-provider coverage, downstream wall-state equivalence, movement consequences, or pair freeze.

Both endpoints are Ornithe Feather Gen2 build 2. The 1.13.2 ready marker binds source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; the 1.14.4 ready marker binds `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`. These match the assigned endpoint manifests. The input `unexamined-wall-projection-keys.tsv` is SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`; its exact rows list singleton domains and old/new effective owners for these three keys.

## Predicate comparison

At 1.13.2, `WallBlock.shouldConnectTo` returns true for a horizontal `FaceShape.SOLID` neighbor unless `isExceptionForConnection` rejects its block (`WallBlock.java:51–65`). The three keys inherit `Block.getFaceShape`, which returns `FaceShape.SOLID` (`Block.java:383–385`). `BlueIceBlock` extends `TransparentBlock` and only overrides drop count; `TransparentBlock` changes rendering only. `PackedIceBlock` and `SnowBlock` extend `Block` and have no face-shape override. The old attachment/connection exception sets include `Blocks.ICE`, but not `SNOW_BLOCK`, `PACKED_ICE`, or `BLUE_ICE`; none is another listed wall exception or a fence gate. Therefore all three old keys yield true for each of NORTH/EAST/SOUTH/WEST wall-neighbor checks.

At 1.14.4, the three registrations are `snow_block` as `Block`, `packed_ice` as `Block`, and `blue_ice` as `TransparentBlock` (`Blocks.java:564, 1157–1159, 1831–1833`). Their declarations define no block-state properties, so each key has one state. Default `Block.Properties.hasCollision` is true (`Block.java:808–812`); target `Block.getShape` is the full unit cube and `getCollisionShape` delegates to the state shape when collision is enabled (`Block.java:370–378`). `TransparentBlock` does not override either shape provider. The wall call uses the cached `BlockState.m_87223014` directional face-solid result or computes `Block.isFaceSolid` (`BlockState.java:326–328, 415–417`); `Block.isFaceSolid` rejects LEAVES-tagged states and then checks whether the collision face is full (`Block.java:402–408`). The three keys are absent from the exact target LEAVES and WALLS tag payloads. They are also absent from the target attachment-exception list and are not fence gates. Each target collision shape is a full cube, so all four directional wall bits are true.

The accepted source comparison of old wall attachment-exception candidates covered only `minecraft:ice` (alongside glass, glowstone, sea lantern, cauldron, and conduit); its report explicitly excludes packed and blue ice. No existing wall-predicate report covers these three keys.

## Bounded result

| Key | State domain | 1.13.2 horizontal wall bit | 1.14.4 horizontal wall bit | Result |
|---|---:|---|---|---|
| `minecraft:snow_block` | 1 | `FaceShape.SOLID`, not excluded → true | full collision face, not excluded → true | true → true |
| `minecraft:packed_ice` | 1 | `FaceShape.SOLID`, not excluded → true | full collision face, not excluded → true | true → true |
| `minecraft:blue_ice` | 1 | inherited `FaceShape.SOLID`, not excluded → true | inherited full collision face, not excluded → true | true → true |

All 12 key/state/direction checks (3 keys × 1 state × 4 horizontal directions) are no-difference for the wall neighbor bit. This does not close wall neighbor registration, state-update, wall-shape, or movement dependencies beyond these exact predicates.

## Exact source hashes

All values below were recomputed from canonical source bytes and matched the corresponding endpoint `ornithe-feather.sources.sha256` entry.

| Relative source path | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/Blocks.java` | — | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/block/BlueIceBlock.java` | `ae9c5fee59fc1761981b236440b1592eca475df80b40299377e44ff9050ec05b` | — |
| `net/minecraft/block/PackedIceBlock.java` | `b533ff3dfd50ec74a2b518a2a23b7c576accc2cdba462f4ea70953c85d1c92a4` | — |
| `net/minecraft/block/SnowBlock.java` | `dda428538e36f7c6f10f9e34eca0f4177c71a5a75cd1530bb9f65fc26c4d15ed` | — |
| `net/minecraft/block/TransparentBlock.java` | `392cbb712f2e3cc70b23f40b917ed837958c37173a72f3a6aedb3403769c07f6` | `392cbb712f2e3cc70b23f40b917ed837958c37173a72f3a6aedb3403769c07f6` |

The reused target raw-resource claim is bound by `wall_resource_membership/extraction-record.md` SHA-256 `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea` and its independent ACCEPT audit r2 SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`. The extracted exact target payloads are `walls.json` SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c` and `leaves.json` SHA-256 `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991`; neither contains any of the three keys.
