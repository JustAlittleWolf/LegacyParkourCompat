# Wall plant/fluid face projection: 1.13.2 → 1.14.4

## Evidence identity and scope

Exact endpoints are Minecraft 1.13.2 and 1.14.4, Ornithe Feather Gen2 build 2. The source-manifest SHA-256 values are `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` (1.13.2) and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc` (1.14.4); the ready markers bind those exact source sets. They match the manifests supplied in the assignment and in the census.

This closes only the common registered plant/fluid keys named below from `wall_remaining_face_shape_census/report.md` (input SHA-256 `f1a12c7564f14c91c652bea6603e8ada8350b98c2892df05c591c0161d4e041d`). It projects each registered state through the old horizontal `FaceShape` + old wall exception predicate and the 1.14.4 wall collision-face predicate. It does not inspect fluid movement/currents, plant growth/callbacks, or other census families.

## Predicate comparison

- **Old 1.13.2:** `WallBlock.shouldConnectTo` (`WallBlock.java:52–55`) accepts `FaceShape.SOLID` only when `!WallBlock.isExceptionForConnection(block)`, with the separate thick-pole / fence-gate arm. Its exception helper (`WallBlock.java:57–65`) delegates to `Block.isExceptionForAttachment` and adds barrier, melon/pumpkin variants, frosted ice and TNT. None of the keys below is in those exception sets or is a fence gate; thus old horizontal SOLID means connected and UNDEFINED means disconnected.
- **Target 1.14.4:** `WallBlock.shouldConnectTo` (`WallBlock.java:48–52`) accepts the complete predicate `(!isExceptionForAttachment && isFaceSolid) || (BlockTags.WALLS || direction-valid fence gate)`. `Block.isFaceSolid` (`Block.java:402–408`) rejects `LEAVES` and otherwise asks whether that direction's collision-shape face is a full unit face. For these eleven keys, no key is excluded by `isExceptionForAttachment`, none is in the vanilla `walls` or `leaves` tag, and none is a fence gate, so the tag and fence-gate arms do not change the row outcomes. The resource evidence is `wall_resource_membership/extraction-record.md`; its extracted `payloads/data/minecraft/tags/blocks/walls.json` and `leaves.json` hashes are respectively `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c` and `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991`. Those vanilla membership lists contain 14 wall IDs and six leaf IDs, with none of the eleven audited keys. Empty collision shapes and non-full face slices therefore produce false.

N/E/S/W below mean the four horizontal wall-neighbor directions (the queried face is the opposite face on the neighbor). Every row has the same result in all four directions; no row depends on direction-specific wall connection bits.

## Common-key state and projection census

Domains are the registered block-state Cartesian domains established by each class's state definition and its property declarations. They agree at the two endpoints. “All four” means N/E/S/W independently.

| Common key(s) → class | Registered states | 1.13.2 old horizontal face → wall result | 1.14.4 collision-face result → wall bit | Projection |
|---|---|---|---|---|
| `kelp` → `KelpBlock` | `AGE=0..25` (26) | `UNDEFINED` → false | `.noCollision()` → empty → false | false→false, all states/directions |
| `kelp_plant` → `KelpPlantBlock` | singleton | `UNDEFINED` → false | `.noCollision()` → empty → false | false→false |
| `bubble_column` → `BubbleColumnBlock` | `DRAG={false,true}` (2) | `UNDEFINED` → false | `.noCollision()` → empty → false | false→false, both states |
| `sugar_cane` → `SugarCaneBlock` | `AGE=0..15` (16) | `UNDEFINED` → false | `.noCollision()` → empty → false | false→false, all states |
| `cocoa` → `CocoaBlock` | `FACING={N,E,S,W} × AGE=0..2` (12) | `UNDEFINED` → false | Facing/age-selected inset cocoa shape has no full horizontal face → false | false→false, all 12 states |
| `chorus_plant` → `ChorusPlantBlock` | `NORTH,EAST,SOUTH,WEST,UP,DOWN ∈ {false,true}` (64) | `UNDEFINED` → false | Core plus narrow directional arms never fills a horizontal face → false | false→false, all 64 states |
| `chorus_flower` → `ChorusFlowerBlock` | `AGE=0..5` (6) | explicit `UNDEFINED` → false | Default full-block collision shape; no no-collision gate; full face → true | **false→true**, all 6 states/directions |
| `water` → `LiquidBlock(Fluids.WATER)` | `LEVEL=0..15` (16) | `UNDEFINED` → false | `.noCollision()` → empty → false | false→false, all states |
| `lava` → `LiquidBlock(Fluids.LAVA)` | `LEVEL=0..15` (16) | `UNDEFINED` → false | `.noCollision()` → empty → false | false→false, all states |
| `seagrass` → `SeaGrassBlock` | singleton | inherited `PlantBlock.UNDEFINED` → false | `.noCollision()` → empty → false | false→false |
| `tall_seagrass` → `TallSeaGrassBlock` | `HALF={LOWER,UPPER}` (2) | inherited `Block.SOLID`; no old connection exception → true | `.noCollision()` → empty → false | **true→false**, both states/directions |

Key/class registrations are common: 1.13.2 bootstrap in `Block.java` (water/lava `:829–830`, seagrass/tall seagrass `:927–932`, sugar cane `:1073`, cocoa `:1227`, chorus plant/flower `:1585–1592`, kelp/kelp plant `:1749–1753`, bubble column `:1874`); 1.14.4 registrations are in `Blocks.java` at the corresponding constant declarations and retain these keys/classes. The `LEVEL` domain comes from `LiquidBlock` and `Properties.LEVEL`; the plant domains come from the corresponding `buildStateDefinition` methods and `Properties` declarations.

For the target special cases: `chorus_flower` has no local shape override in `ChorusFlowerBlock`, inherits `Block.getShape = VoxelShapes.block()` and the collision-enabled base default, so its horizontal face slice is full. Conversely `tall_seagrass` explicitly has `noCollision` in both registrations, so its unchanged nonempty direct outline is not its collision shape. This distinction accounts for both directional flips; neither follows from direct outline geometry alone.

## Source anchors and hashes

Source locations: 1.13.2 `WallBlock.java:52–65`, `Block.java:255–267,383–395,927–932,1073,1227,1585–1592,1749–1753,1874`; `KelpBlock.java:23–39,58`, `KelpPlantBlock.java:36`, `BubbleColumnBlock.java:187–200`, `SugarCaneBlock.java:96–103`, `CocoaBlock.java:143–150`, `ChorusPlantBlock.java:19–22,110–116`, `ChorusFlowerBlock.java:21–28,187–188,244–245`, `LiquidBlock.java:49,184–190`, `PlantBlock.java:43–45`, `SeaGrassBlock.java:25–36`, `TallSeaGrassBlock.java:23–34`. Target locations: `WallBlock.java:48–52`; `Block.java:260–267,371–378,402–408`; `Blocks.java` corresponding registrations; corresponding provider classes' shape/state methods (the context argument is ignored where added); `ChorusFlowerBlock.java:21–28,163–164`.

SHA-256 of consulted source bytes (all `net/minecraft/block/` paths):

| File | 1.13.2 | 1.14.4 |
|---|---|---|
| `Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `KelpBlock.java` | `57330c0ac1cd00000f2e0c0f78ec98d2ec48024045b65d24f506408ea3cb8952` | `1e26cc67f68c836963a09582b24605856f9f99f08959cfab6f0b464d8b0c4707` |
| `KelpPlantBlock.java` | `dba4dffada2a7baae6a0f06e973b5af7dd175b71b9387581497087f76378751f` | `0178baad268c0ccc7ff9da8bda721f933cb3cce81736b2dcc968c8d626edd345` |
| `BubbleColumnBlock.java` | `0320e1b70b215dfff209f8e5003b97cac3a247fd3d642597f2db8ab46d251b2e` | `d5b9bdcf81349e96920134d6519629479787203d59ea2bd1683088c0b5223ef2` |
| `SugarCaneBlock.java` | `09e578a7ac2442448c72b640ffb5afb94354fc4bd0188fa2a3e59e021ac0138e` | `f1b06a1613e6c0058aa666c6bd3689521627bab82f59b17055c225298820271b` |
| `CocoaBlock.java` | `6655df26d136eb21d94b323496c77b5237484a86a1362e4ddd636e35c6997445` | `67deecadff4cc8435e15722f1d38523dd367fdbb175bc45d1463082e34f53d86` |
| `ChorusPlantBlock.java` | `299acaed9644e0837a29f282e830dc7582b8d8f14a1c904147c034518e474433` | `0c05da2c054d2912bd667aaa200c4428fcc8e4064874056907656376b6120189` |
| `ChorusFlowerBlock.java` | `cacbbd84e9be8912d8e79d6d90cafd72a70b81d3e96518b8f0eb2eaf65fe19c4` | `5f8de15e04efd8f98a1dcc82628550cfcfe2d65c00bfbae6f56586b9e11adda4` |
| `LiquidBlock.java` | `feedc9a5f1843604e6755a447e89d8d5599613799e25cf8c93a0562e52e97856` | `bfbbe2997ce6c03224b5833176d6b19b4b4b4cbc3337960fb3d867de77519ca1` |
| `SeaGrassBlock.java` | `a3019943a0a5a61055f4731566446e662b532b75e950d8f8676ada28ff4f5b10` | `26307e005f90a8930afba830d5f928b7a178cd081280420bb5384fc4f3f3c3d3` |
| `TallSeaGrassBlock.java` | `0876535db8acade7e4295c6044355452b936d8af99845dea71145553af35fe52` | `51230c1b8da12187b0296ee55a459a3656df3647ce817d8f1ccf104ed522f9a0` |
| `PlantBlock.java` | `a1234ccec309bd8805fbe3bad48bce076e17c131e048d2a57d18cdb75f0f286e` | `6ede310fcb1e0163865dd4161b291cc02fe223bb56dfa1f89aa64b1cf72a1f75` |
| `DoublePlantBlock.java` | `95e782d379a7e8fbbe24497bd25d840aab47ccc6fb2de8f50f5afa0cbd8080fa` | `44b9787e974632f4f9594d458388a3a84afdd3983012f809fb5b3d762fde7785` |

The existing Kelp, sea-grass and chorus provider reports were reused only for their matching provider/registration facts: `kelp_shape_provider/report.md` (audit accepted for its bounded provider claim), `sea_grass_shape_provider/review.md`, `all_seagrass_shape_provider/source-report.md`, and `chorus_shape_provider/coverage.md`. Their fluid, growth and support exclusions remain open and are not repeated here.

## Closure limits

Closed for these keys: registered key/class/state domains and the N/E/S/W wall predicate projection. Newly established directional changes are `chorus_flower` false→true and `tall_seagrass` true→false. No fluid physics was revisited. Other remaining census families, possible un-crosswalked aliases, full pair coverage, independent full-pair audit, intermediate-patch cutoff, and pair freeze remain unknown/open. This bounded projection makes no measured player trajectory or runtime-parity claim.
