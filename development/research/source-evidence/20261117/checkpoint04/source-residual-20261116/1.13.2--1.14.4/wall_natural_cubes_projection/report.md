# Natural full-cube wall-neighbor predicate slice: 1.13.2 → 1.14.4

## Source identity and bounded scope

This report compares only the six unexamined rows in `wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv` for `sponge`, `wet_sponge`, `gravel`, `hay_block`, `mycelium`, and `podzol`. The exact TSV SHA-256 is `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`. A read-only search of existing source-residual Markdown for these keys found no prior wall-predicate report; the registry crosswalk lists all six as `UNEXAMINED_FOR_WALL_PREDICATE`.

The source mapping is Ornithe Feather Gen2 build 2 at each endpoint. Ready markers `decompiled_minecraft/1.13.2/ornithe-feather.ready.json` and `decompiled_minecraft/1.14.4/ornithe-feather.ready.json` declare source-manifest SHA-256 values `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`, respectively. Each ready digest matches its on-disk `ornithe-feather.sources.sha256` file.

## Exact predicate routes

**1.13.2:** `BlockState.getFaceShape` delegates to the state's block (`block/state/BlockState.java:285–287`). For the six listed concrete types, no provider class in their inheritance chain overrides `getFaceShape`; `Block.getFaceShape` returns `FaceShape.SOLID` (`block/Block.java:383–385`). `WallBlock.shouldConnectTo` accepts `SOLID` unless its block is in `isExceptionForConnection` (`block/WallBlock.java:51–65`). The exceptions are attachment-exception blocks, barrier, melon, pumpkin variants, frosted ice, and TNT. None of these six keys/classes is an exception or fence gate. Thus the old wall bit is `true` in each of the four queried horizontal directions.

**1.14.4:** wall neighbor queries call `BlockState.m_87223014` (`block/WallBlock.java:67–70`), which delegates to `Block.isFaceSolid` when not using the equivalent cached face result (`block/state/BlockState.java:326–328`). `Block.isFaceSolid` excludes the `LEAVES` tag, then asks if the collision shape has a full face (`block/Block.java:402–404`); `WallBlock` accepts that result unless `isExceptionForAttachment` rejects the block, or through the separate `WALLS` tag/fence-gate arm (`block/WallBlock.java:48–52`). None of the six keys is in the target `LEAVES` or `WALLS` resource, an attachment exception, or a fence gate. The accepted resource packet provides the raw tag payloads: `walls.json` SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; `leaves.json` SHA-256 `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991`. Its independent resource verdict is `wall_resource_membership_audit/verdict-r2.md`, SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`.

All six target registrations use ordinary collision-enabled properties: `Block.Properties.hasCollision` defaults to `true` (`block/Block.java:811`), and none of their registrations calls `noCollision`. None of their own or inherited concrete provider classes overrides `getShape` or `getCollisionShape` (source scan of each class and the relevant inheritance chain). The inherited target `Block.getShape` is `VoxelShapes.block()` (`block/Block.java:371–373`), and `Block.getCollisionShape` returns that state shape when collision is enabled (`block/Block.java:376–378`). It therefore supplies a full unit face in all horizontal directions for every listed state.

## Registered keys and state domains

The 1.13.2 block bootstrap registers the six identifiers at `Block.java:789` (`podzol`), `833` (`gravel`), `890–891` (`sponge`, `wet_sponge`), `1207` (`mycelium`), and `1398` (`hay_block`). The 1.14.4 constants register the same key-to-class mappings at `Blocks.java:42–43`, `102`, `200–203`, `739–740`, and `1102–1104`.

| Registry key | Effective class / state source | Registered states per endpoint | Old wall bit | Target wall bit |
|---|---|---:|---:|---:|
| `minecraft:sponge` | `SpongeBlock`; no state properties | 1 | true | true |
| `minecraft:wet_sponge` | `WetSpongeBlock`; no state properties | 1 | true | true |
| `minecraft:gravel` | `GravelBlock` → `FallingBlock`; no state properties | 1 | true | true |
| `minecraft:hay_block` | `HayBlock` → `AxisBlock`; `AXIS = X,Y,Z` | 3 | true | true |
| `minecraft:mycelium` | `MyceliumBlock` → `SpreadingSnowyDirtBlock` → `PodzolBlock`; `SNOWY = false,true` | 2 | true | true |
| `minecraft:podzol` | `PodzolBlock`; `SNOWY = false,true` | 2 | true | true |

The `AXIS` property is `Properties.AXIS` and is built by `AxisBlock`; `Direction.Axis` declares X/Y/Z (`AxisBlock.java:10, 36–38`; `Properties.java:46`; `Direction.java:288–322`). `PodzolBlock` defines and builds the `SNOWY` property; `MyceliumBlock` inherits that domain (`PodzolBlock.java:13–18, 37–40`; `SpreadingSnowyDirtBlock.java:12–15`; `Properties.java:41`). The combined legal-state domain is 10 key/state pairs. Each has four horizontal directions, for 40 old/new predicate comparisons, all `true → true`.

## Canonical Java source SHA-256

| Canonical source path under `decompiled_minecraft/<version>/ornithe-feather/` | 1.13.2 | 1.14.4 |
|---|---|---|
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| `net/minecraft/block/SpongeBlock.java` | `924211cbd821f0ff205bf0fc8a02d4071841f31494b271df75477476e1238a18` | `ef4ee6906cdec31aab6d3dba76c97bf67fa1d71c614babce28606dc3414f30fa` |
| `net/minecraft/block/WetSpongeBlock.java` | `d7e3b15be72802d0f5edb494beba3b8f4719e7e133d867c9a108a1da0f56e32b` | `cc475db4841c0afe3cff82bcbbc6d365b368e453a64cde26480054c140d0330c` |
| `net/minecraft/block/GravelBlock.java` | `d9cea103365ce3184bdd021c32fb3a7a43058a839c46192b7c5d416592d64c73` | `1dc5f30af23f5b1c6e1c8ef6b0ad8d0453d35126add9a77d8a730c92fa577f34` |
| `net/minecraft/block/FallingBlock.java` | `ae927f5efdf15c38e9ad9f8a5e573c25b1ff5f59f4370bf08995399522afd2e2` | `1e91e14e3b1dad42178f5867d97449bb605964e139c4d001abe84292974e757b` |
| `net/minecraft/block/HayBlock.java` | `a69ddb3554c2453c8d04ddeec14a1e0feed001592e49384a710f630bac0c7a64` | `a69ddb3554c2453c8d04ddeec14a1e0feed001592e49384a710f630bac0c7a64` |
| `net/minecraft/block/AxisBlock.java` | `6d80b24a181d5891794b16576d4641fd25b2b8c5eb879362ffdca671975919a4` | `6d80b24a181d5891794b16576d4641fd25b2b8c5eb879362ffdca671975919a4` |
| `net/minecraft/block/MyceliumBlock.java` | `b0c1b38d9b394cb04f94dd961ecd57829b602d448e30e7ac2ca1e461dbd95dcc` | `b0c1b38d9b394cb04f94dd961ecd57829b602d448e30e7ac2ca1e461dbd95dcc` |
| `net/minecraft/block/SpreadingSnowyDirtBlock.java` | `6b551644ea775c9d0e09124a7913b46015c21896f63bb7825cf81be6e7246d35` | `2c0ca85ce34cfb77eb411c4253d1b2f17b40001b413e4c8b65261c0b90d986cb` |
| `net/minecraft/block/PodzolBlock.java` | `6f6f1bee9feaaa4a0609259a4028c7ac67d563272b6981248400c680cc01b4c2` | `304b5b5a234134a3937d34fdc0f85fd0fdae6397028dfba05f2928379916142f` |
| `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |
| `net/minecraft/util/math/Direction.java` | `6d5c496ff65377fee9f00c826e48c7be14d3889e5ae5191e110a464007549082` | `121f8b78eda07640eccbe369f40acc7ccbdcb275dcb6aca87c4495cd193b01c0` |

These hashes were checked against the canonical Java bytes; the ready manifests bind the corresponding source sidecars.

## Result and limits

This closes only the stated wall-neighbor predicate slice: the six vanilla full-cube registrations return the same horizontal wall bit in both endpoints for every legal state. No player-trajectory, collision-dispatch parity, or pair-freeze claim follows from this predicate comparison. Pair-wide tick/provider coverage, dependencies, and independent full-pair audit remain open.
