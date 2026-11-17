# Wall predicate projection: mushrooms and selected plants, 1.13.2 → 1.14.4

## Scope and source identity

This bounded source-only packet compares the eight exact common keys still listed in `wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv` for the `MushroomBlock`, `MushroomPlantBlock`, `TallPlantBlock`, and `PotatoesBlock` rows. It compares the old wall-neighbor `FaceShape` predicate with the target wall-neighbor collision-face predicate. It does not establish movement trajectory impact, close other provider/query dependencies, or freeze the pair.

Pinned endpoint ready identities were checked directly:

- 1.13.2 Feather Gen2 build 2 source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- 1.14.4 Feather Gen2 build 2 source manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`.

The assigned residual-key input is `wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv`, SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`. The eight rows are brown_mushroom, red_mushroom, brown_mushroom_block, red_mushroom_block, mushroom_stem, fern, grass, and potatoes. These rows are distinct from the accepted `wall_plant_fluid_projection/report.md` (SHA-256 `41c1ec692df824c1aea57c186a4ff5968cc72fd257d359ccfb0cf88cb8ea20ee`), whose checked plant/fluid list does not include these keys.

The source crosswalk r2 audit notes that its r1 TSV was overwritten, preventing a byte-diff of all unchanged rows. This packet makes no claim to repair that historical limitation; it binds its own assigned rows to the current TSV digest above.

## Predicate routes

At 1.13.2, `BlockState.getFaceShape` delegates to the block's `getFaceShape` (`block/state/BlockState.java:285–286`). The base `Block.getFaceShape` returns `FaceShape.SOLID` (`block/Block.java:383–385`), while `PlantBlock.getFaceShape` returns `FaceShape.UNDEFINED` (`block/PlantBlock.java:43–45`). `WallBlock.shouldConnectTo` accepts non-exception `SOLID` faces, with a separate middle-pole/fence-gate rule (`block/WallBlock.java:51–59`). None of these eight keys is an old connection exception or fence gate.

At 1.14.4, the wall queries `BlockState.m_87223014` and `WallBlock.m_71853799` (`block/WallBlock.java:48–51, 67–70`). That wall predicate uses the face result from `Block.isFaceSolid` and separately accepts `BlockTags.WALLS` or the directional fence-gate rule. `Block.isFaceSolid` checks the `LEAVES` tag and whether the collision shape has a full face (`block/Block.java:402–408`). None of these keys is a leaf, a fence gate, or a member of the exact `WALLS` resource.

For the three `MushroomBlock` keys, `MushroomBlock.buildStateDefinition` declares six Boolean connection properties (`UP`, `DOWN`, `NORTH`, `EAST`, `SOUTH`, `WEST`), giving 64 states per key at both endpoints (`block/MushroomBlock.java:12–22, 87–89` in 1.13.2; `:12–22, 69–71` in 1.14.4; `state/property/Properties.java` supplies the Boolean properties). The class does not override `getShape`; target `Block.getShape` returns a unit cube and default `hasCollision` remains enabled. Thus every state has a full collision face in each direction. Their old base face is also SOLID.

For brown_mushroom and red_mushroom, the old and target registrations explicitly set `noCollision` (`1.13.2 block/Block.java:972–977`; `1.14.4 block/Blocks.java:376–383`). For fern and grass, both endpoint registrations likewise set `noCollision` (`1.13.2 block/Block.java:921–925`; `1.14.4 block/Blocks.java:268–273`). For potatoes, both registrations set `noCollision` (`1.13.2 block/Block.java:1264–1265`; `1.14.4 block/Blocks.java:866–869`). Their old classes inherit `PlantBlock.getFaceShape = UNDEFINED`; target no-collision means `Block.getCollisionShape` returns the empty shape, so `Block.isFaceSolid` returns false. Potatoes inherits its age property through `WheatBlock`, whose `AGE` is `Properties.AGE_7` and whose state definition adds that property (`block/WheatBlock.java:18, 193–195` and `state/property/Properties.java:74`; target `WheatBlock.java:22, 180–182` and `Properties.java:82`). The domain is AGE 0–7 (8 states) at both endpoints.

## Bounded result

| Registry keys | Registered states per key | 1.13.2 wall bit | 1.14.4 wall bit | Result |
| --- | ---: | --- | --- | --- |
| brown_mushroom, red_mushroom | 1 | false (UNDEFINED) | false (empty collision face) | false → false |
| fern, grass | 1 | false (UNDEFINED) | false (empty collision face) | false → false |
| potatoes | 8 (AGE 0–7) | false (UNDEFINED) | false (empty collision face) | false → false |
| brown_mushroom_block, red_mushroom_block, mushroom_stem | 64 each | true (SOLID) | true (full collision face) | true → true |

All four horizontal neighbor directions have the indicated result independently. The 204 key/state combinations produce 816 checked direction cases, with no wall-bit difference in this slice. This is a provider/predicate no-difference only; it does not assert any downstream player movement result.

The target tag facts reuse the independently accepted raw-resource extraction: `wall_resource_membership/extraction-record.md` SHA-256 `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`, audit `wall_resource_membership_audit/verdict-r2.md` SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`. Its exact `WALLS` payload SHA is `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c` and `LEAVES` payload SHA is `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991`; the listed values contain none of the eight keys.

## Canonical source hashes

All values below were checked against the exact endpoint source manifest and current canonical source bytes.

| Relative source path | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
| --- | --- | --- |
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| `net/minecraft/block/Blocks.java` (the old registrations are in `Block.java`) | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/PlantBlock.java` | `a1234ccec309bd8805fbe3bad48bce076e17c131e048d2a57d18cdb75f0f286e` | `6ede310fcb1e0163865dd4161b291cc02fe223bb56dfa1f89aa64b1cf72a1f75` |
| `net/minecraft/block/MushroomBlock.java` | `5f52979e5571b2ee4a784fce69110528617f18a5222c66e95d1d0b3b5b52e33b` | `257b5cb6a0b400371901e438f0e8f6f3d37d32e52c8c7f9f09acaaf8c610b3b4` |
| `net/minecraft/block/MushroomPlantBlock.java` | `2da1fde703c9c6a529fbc92af884927da0af631e2b4b01a28bffffc0a3bb53a8` | `72abb19d5276aa91ddd2ad3a50f25c5946225666367aab1bfc373b97a4f738cf` |
| `net/minecraft/block/TallPlantBlock.java` | `630b21b2de31c7261371da9f03f4f1dd108c65c670776ce42f72e4fcf36976f6` | `2af49d0bdc8e250dcb3484c1945766b6e0d60cae2760271e3108474b2529ea50` |
| `net/minecraft/block/PotatoesBlock.java` | `5ec0aa93d727c7eece5232d88f71933dfc3b1228c38e22bc48ede2679c75c573` | `f831104d5ab09dfedf53372d001a76f332ce827ec869ef659a7a75080a2a9cca` |
| `net/minecraft/block/WheatBlock.java` | `f8b50a11a50f546f2b474fa20cc272a0534d4276adf94ef5c936e83edfef2d62` | `32c765a4e19506214f5450b025d1476da6d8a947143262de633f51c36120e562` |
| `net/minecraft/block/PipeBlock.java` | `4c56867771e5848fec41d7ccbce5339af3dfefa7f98afb4712dfd0d5abd8f9a9` | `50974f5a929e70166c8edbac17dd22b295ceb50bacd345d2b89a25bb150740b6` |
| `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |
