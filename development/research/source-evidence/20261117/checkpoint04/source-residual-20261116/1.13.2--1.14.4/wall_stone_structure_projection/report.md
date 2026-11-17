# Wall predicate projection: stone, grass, bedrock, structure block

## Source identity and scope

Exact endpoints are Minecraft 1.13.2 and 1.14.4, Ornithe Feather Gen2 build 2. The ready markers are `decompiled_minecraft/1.13.2/ornithe-feather.ready.json` SHA-256 `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` and `decompiled_minecraft/1.14.4/ornithe-feather.ready.json` SHA-256 `bf8c9399e3a36ddd7edc78d98413319000895f49e88db224335f62b2baf21e77`; each binds its exact source manifest and mapping identity. Manifest SHA-256 values are `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` (1.13.2) and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc` (1.14.4). Every Java file listed below was rehashed from the canonical read-only tree and matched its corresponding manifest entry.

This packet compares only wall neighbor connection bits for exact common registry keys `minecraft:stone`, `minecraft:grass_block`, `minecraft:bedrock`, and `minecraft:structure_block`, identified among the unexamined rows in `wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv` (SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`). It does not infer player movement displacement or close the pair.

## Registration and state domains

| Key | 1.13.2 registration | 1.14.4 registration | Registered domain |
|---|---|---|---|
| `stone` | `Block.java:779`, `StoneBlock` | `Blocks.java:22`, `Block` | singleton on both sides |
| `grass_block` | `Block.java:786`, `GrassBlock` | `Blocks.java:35–37`, `GrassBlock` | `SNOWY={false,true}` on both sides; `PodzolBlock.buildStateDefinition` adds `SNOWY`, inherited by grass |
| `bedrock` | `Block.java:828`, `BedrockBlock` | `Blocks.java:88`, `C_97172724` | singleton on both sides |
| `structure_block` | `Block.java:1875`, `StructureBlock` | `Blocks.java:1947–1949`, `StructureBlock` | `MODE={SAVE,LOAD,CORNER,DATA}` (4 states) on both sides |

The `SNOWY` property is `Properties.SNOWY` and Boolean on both endpoints. `StructureBlock.MODE` is the four-valued `StructureBlockMode` enum on both endpoints. Neither property changes the inherited collision shape or the queried face predicate.

## Exact query and provider comparison

At 1.13.2, `WallBlock.getPlacementState` passes each horizontal neighbor face to `BlockState.getFaceShape`; `BlockState.getFaceShape` delegates to the effective block's `getFaceShape`. `WallBlock.shouldConnectTo` returns true for `FaceShape.SOLID` unless `isExceptionForConnection` applies (the separate pole/gate arm is irrelevant here). The generic `Block.getFaceShape` returns `FaceShape.SOLID`. The four selected concrete providers do not override it: old `StoneBlock` and `BedrockBlock` have no face-shape override; `GrassBlock` inherits via `SpreadingSnowyDirtBlock`/`PodzolBlock`; `StructureBlock` inherits through `BlockWithBlockEntity`.

At 1.14.4, `WallBlock.getPlacementState` passes each queried face through `BlockState.m_87223014`. That method returns the state cache face bit when present, otherwise it calls `Block.isFaceSolid`; cache initialization also computes each face bit with `Block.isFaceSolid` against a dummy view. For a non-LEAVES state, this requires the collision shape to cover a full unit face. The default `Block.getCollisionShape` uses the state shape when `hasCollision` is true, and default `Block.getShape` is `VoxelShapes.block()`. Each selected registration leaves collision enabled (the default is true; none calls `noCollision`). Target `Stone` directly constructs `Block`; `GrassBlock` inherits the shape through `SpreadingSnowyDirtBlock`/`PodzolBlock`; bedrock's `C_97172724` only overrides `canSpawn`; and `StructureBlock`/`BlockWithBlockEntity` add no shape override. All therefore expose the full-cube collision face for every legal registered state.

The old connection-exception list does not include these four blocks. The target attachment-exception list does not include them either. The exact 1.14.4 `WALLS` payload has fourteen wall keys and none of these four; the `LEAVES` payload has six leaf keys and none of these four. Payloads were read from `wall_resource_membership/payloads/`, bound by accepted raw-resource extraction record SHA-256 `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea` and independent resource verdict `wall_resource_membership_audit/verdict-r2.md` SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`; payload SHA-256 values are `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c` (WALLS) and `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991` (LEAVES). Neither exception/resource branch alters the result for this slice.

## Predicate result

| Key | State count | 1.13.2 old face / bit | 1.14.4 target collision face / bit | Result |
|---|---:|---|---|---|
| `stone` | 1 | SOLID / true | full face / true | true→true, N/E/S/W |
| `grass_block` | 2 | SOLID / true | full face / true | true→true, both states and N/E/S/W |
| `bedrock` | 1 | SOLID / true | full face / true | true→true, N/E/S/W |
| `structure_block` | 4 | SOLID / true | full face / true | true→true, all modes and N/E/S/W |

This closes only these four neighbor-predicate rows. Other stateful providers, wall connection producers, player collision behavior and the full pair audit remain outside this report; no pair freeze is asserted.

## Canonical source hashes

| Relative source path | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/block/GrassBlock.java` | `dad1523a0a975de4a7100b708062bb30f652c5d9c95d09505de6f006aef3ce6c` | `6343c8037180f62cda5814ed64b21489cb6dd064cda732e3f06b7fe2c83c1bb0` |
| `net/minecraft/block/SpreadingSnowyDirtBlock.java` | `6b551644ea775c9d0e09124a7913b46015c21896f63bb7825cf81be6e7246d35` | `2c0ca85ce34cfb77eb411c4253d1b2f17b40001b413e4c8b65261c0b90d986cb` |
| `net/minecraft/block/PodzolBlock.java` | `6f6f1bee9feaaa4a0609259a4028c7ac67d563272b6981248400c680cc01b4c2` | `304b5b5a234134a3937d34fdc0f85fd0fdae6397028dfba05f2928379916142f` |
| `net/minecraft/block/BedrockBlock.java` | `36071246d817e9334a75574d95b513c32ed7812341495d89dffeba5f6ea10133` | — |
| `net/minecraft/block/StoneBlock.java` | `4a85d515f1195600042d7e67de00767c77c91c2e74aa00ac7796ceb7ea78c906` | — |
| `net/minecraft/block/StructureBlock.java` | `2d0c67f5993a5dbe3fac3ffe1268e6ac99328537bef596554d39fbf72ddf141c` | `4f08eda5abb4c36b9cf4dec79b75cc40fc4e962e3b606035c662f72e1034c996` |
| `net/minecraft/block/BlockWithBlockEntity.java` | `9f190eebfcff3f23ce76bceafd6295d4e84d93cc3a76d85f83972792c811a266` | `dc644d4ab779d5ee65f5a0828097d3732efdbd9cc8fc8ecd1660ae7f1b322a2a` |
| `net/minecraft/unmapped/C_97172724.java` | — | `bade3049c5136fba713bc2df9ba7a4294ec2e693bdf4c02be79479be7fbbc058` |
| `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |
| `net/minecraft/block/state/property/StructureBlockMode.java` | `74f3184e2e8661a8e77ddf0b2184a5a88c759bc1dcd36aa68c4c80fda1c0b916` | `74f3184e2e8661a8e77ddf0b2184a5a88c759bc1dcd36aa68c4c80fda1c0b916` |

