# Crop wall-predicate projection: 1.13.2 → 1.14.4

## Scope and source identity

This source-only packet covers only the four unexamined registry keys `minecraft:beetroots`, `minecraft:carrots`, `minecraft:nether_wart`, and `minecraft:wheat` listed in `wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv` (SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`). It compares their state-dependent wall-neighbor predicate inputs at the exact endpoints. It does not claim a movement trajectory or pair completion.

Both endpoints are Ornithe Feather Gen2 build 2. The 1.13.2 ready marker binds version `1.13.2`, mapping `ornithe-feather`, and source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. The 1.14.4 ready marker binds version `1.14.4`, mapping `ornithe-feather`, and source manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`. The ready markers and sidecars were read locally; every listed consulted source hash below matched its corresponding sidecar entry and current file bytes.

## Predicate and providers

On 1.13.2, `WallBlock.shouldConnectTo` (lines 51–55) connects its generic arm only when the neighbor face is `SOLID` and the block is not an attachment exception. Its other arm accepts `MIDDLE_POLE_THICK`, or `MIDDLE_POLE` when the neighbor is a fence gate. The four crop classes inherit `PlantBlock.getFaceShape` (lines 43–45), which returns `FaceShape.UNDEFINED` for every face. The wall placement caller passes each neighbor's returned face shape to `shouldConnectTo` (WallBlock lines 81–84); its wall-bit result is therefore false for these keys regardless of their outline-shape height.

On 1.14.4, the wall helper `m_71853799` (WallBlock lines 48–52) combines the collision-face boolean with the `WALLS` resource and fence-gate alternatives. Placement obtains that boolean from `BlockState.m_87223014` (BlockState lines 326–327), which delegates to `Block.isFaceSolid`; that helper checks the collision shape's face (Block lines 402–408). All four registry constructors pass `Block.Properties.noCollision()` at both endpoints. The 1.14.4 base collision provider returns `VoxelShapes.empty()` when `hasCollision` is false (Block lines 376–377), so the collision-face boolean is false for every crop state and horizontal direction. None of these four keys is a fence gate or a member of the accepted 1.14.4 `WALLS` resource: the verified wall tag contains only the fourteen wall-block keys. The resource extraction record SHA is `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`; its independent r2 verdict SHA is `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`; the exact `walls.json` payload SHA is `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`.

## Keys, states, and result

| Registry key | Old and target provider | Registered age states at both endpoints | Old 1.13.2 wall bit | New 1.14.4 wall bit |
|---|---|---:|---|---|
| `minecraft:beetroots` | `BeetrootsBlock` → `WheatBlock` → `PlantBlock` | `AGE=0..3` (4) | `UNDEFINED` → false | no collision → false |
| `minecraft:carrots` | `CarrotsBlock` → `WheatBlock` → `PlantBlock` | `AGE=0..7` (8) | `UNDEFINED` → false | no collision → false |
| `minecraft:nether_wart` | `NetherWartBlock` → `PlantBlock` | `AGE=0..3` (4) | `UNDEFINED` → false | no collision → false |
| `minecraft:wheat` | `WheatBlock` → `PlantBlock` | `AGE=0..7` (8) | `UNDEFINED` → false | no collision → false |

The age domains come from the classes' state builders and `Properties.AGE_3` / `AGE_7`. `CarrotsBlock` inherits `WheatBlock`'s `AGE_7` definition. The registration blocks and all four class sources were checked at both endpoints. Result: no wall-neighbor bit difference across 24 key/state combinations and four horizontal directions (96 checks). This closes only this bounded predicate slice.

## Consulted source hashes

All hashes are SHA-256 and match the endpoint source manifests.

| Source file | 1.13.2 | 1.14.4 |
|---|---|---|
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/Blocks.java` | n/a (registrations in `Block.java`) | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/PlantBlock.java` | `a1234ccec309bd8805fbe3bad48bce076e17c131e048d2a57d18cdb75f0f286e` | `6ede310fcb1e0163865dd4161b291cc02fe223bb56dfa1f89aa64b1cf72a1f75` |
| `net/minecraft/block/BeetrootsBlock.java` | `9102e147211d5767e1fcb642553491414579c2ee7908a77c836bd92bdfa46497` | `83ab558b214807a1c1be064971587477a092b7d09bd3c01768f31807d976aed1` |
| `net/minecraft/block/CarrotsBlock.java` | `bdff4737ca9912f6e76f6460ac7d8ccb1253861b3544238b1fce5ccc618bb307` | `3a72c65249925c2b4b98e77796505eabc3347255b6ed160a5d7bc026eadabe7a` |
| `net/minecraft/block/NetherWartBlock.java` | `dc403d65587fedcb5b72b3e9ebc44369cb8f4fa1df0855d48318195e4aa9c4f9` | `3a6ece5a35f898d2e00411156162f3ca53bd003a18de3721736be9eecbce2178` |
| `net/minecraft/block/WheatBlock.java` | `f8b50a11a50f546f2b474fa20cc272a0534d4276adf94ef5c936e83edfef2d62` | `32c765a4e19506214f5450b025d1476da6d8a947143262de633f51c36120e562` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| `net/minecraft/state/StateDefinition.java` | `d7702c839e8d8181a49a24d9d81df69412f78253e9e66633bb5c6c03ef349365` | `d7702c839e8d8181a49a24d9d81df69412f78253e9e66633bb5c6c03ef349365` |
| `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |
