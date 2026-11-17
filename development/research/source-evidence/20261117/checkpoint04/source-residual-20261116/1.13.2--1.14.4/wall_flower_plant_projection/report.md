# Flower and tall-flower wall-neighbor projection

## Scope and immutable source identities

This source-only packet examines only the 14 rows in `build/source-residual-20261116/1.13.2--1.14.4/wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv` whose old face-shape owner is `PlantBlock` and whose concrete class is `FlowerBlock` or `TallFlowerBlock`. It closes only the wall-neighbor predicate for these registered keys and states. It does not establish a movement trajectory, provider-wide parity, or pair freeze.

- 1.13.2 Ornithe Feather Gen2 build 2, ready marker `decompiled_minecraft/1.13.2/ornithe-feather.ready.json`, source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- 1.14.4 Ornithe Feather Gen2 build 2, ready marker `decompiled_minecraft/1.14.4/ornithe-feather.ready.json`, source manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`.

Canonical source files were read without modification. Relevant file SHA-256 values:

| Version | File | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` |
| 1.13.2 | `net/minecraft/block/PlantBlock.java` | `a1234ccec309bd8805fbe3bad48bce076e17c131e048d2a57d18cdb75f0f286e` |
| 1.13.2 | `net/minecraft/block/BushBlock.java` | `f4602bf78bd7477f7a4ea5ff3fbab862ff523d853900c7a72a7ce14b5d7b30f8` |
| 1.13.2 | `net/minecraft/block/FlowerBlock.java` | `72cc99f3abf2679c1589f2fe22c55f2850abebdf747b9ad08bb48af96d9fb1d1` |
| 1.13.2 | `net/minecraft/block/TallFlowerBlock.java` | `be73145a27670281b9a7dcebaaa635210205cd408d31e8f463fcaa0aa75c564` |
| 1.13.2 | `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fc13f7b42f8633c6183964d00dfe8d6960e07bf` |
| 1.14.4 | `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc393e7fb770f0682a6e66f2c5` |
| 1.14.4 | `net/minecraft/block/Blocks.java` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| 1.14.4 | `net/minecraft/block/PlantBlock.java` | `6ede310fcb1e0163865dd4161b291cc02fe223bb56dfa1f89aa64b1cf72a1f75` |
| 1.14.4 | `net/minecraft/block/BushBlock.java` | `bae19aadcd260d90011dbed798f4d3b343c678da36d04fbb0fcb559e47f7b443` |
| 1.14.4 | `net/minecraft/block/FlowerBlock.java` | `02ed99801d9b665313b0efec6ef04fd29e0464a2fd676d5e41878ccb09462e09` |
| 1.14.4 | `net/minecraft/block/TallFlowerBlock.java` | `be73145a27670281b9a7dcebaaa635210205cd408d31e8f463fcaa0aa75c564` |
| 1.14.4 | `net/minecraft/block/WallBlock.java` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |

## Exact keys and state domains

The crosswalk identifies these keys and state products:

- Singleton `FlowerBlock` keys: `minecraft:allium`, `minecraft:azure_bluet`, `minecraft:blue_orchid`, `minecraft:dandelion`, `minecraft:orange_tulip`, `minecraft:oxeye_daisy`, `minecraft:pink_tulip`, `minecraft:poppy`, `minecraft:red_tulip`, and `minecraft:white_tulip` (10 keys × 1 state).
- `TallFlowerBlock` keys: `minecraft:lilac`, `minecraft:peony`, `minecraft:rose_bush`, and `minecraft:sunflower` (4 keys × `HALF={LOWER,UPPER}`).

Total: 14 common registry keys, 18 registered states, and four horizontal neighbor directions per state (72 key/state/direction cases). The row selection is from the r2 corrected registry crosswalk (`wall_full_registry_owner_crosswalk/coverage.md`, TSV `unexamined-wall-projection-keys.tsv`); its independent correction verdict records that r1 TSV was overwritten, so unchanged r1 rows could not all be byte-diffed. This slice does not renew that crosswalk audit. The old `BushBlock` definition declares the `HALF` property for tall flowers; the target keeps the same two values. No non-registered states are included.

## Paired wall predicate

In 1.13.2, `WallBlock.getPlacementState` and its neighbor update route call `neighborState.getFaceShape(..., oppositeFace)` and pass that result to `shouldConnectTo`. The generic branch requires `FaceShape.SOLID` (with a separate middle-pole fence-gate branch); none of these flower classes is a fence gate. `PlantBlock.getFaceShape` returns `FaceShape.UNDEFINED`. `FlowerBlock` inherits that override, while `TallFlowerBlock` extends `BushBlock`, which itself extends `PlantBlock` and does not override `getFaceShape`. Therefore all 18 states yield false in all four directions in the old wall predicate.

In 1.14.4, `WallBlock` obtains the neighbor boolean through `BlockState.m_87223014`, the face-solid query, then its helper applies the result to the generic connection branch. `Block.isFaceSolid` requires the face of the neighbor's collision shape to be full, except for the leaves-tag exclusion. The 10 flower registrations and four tall-flower registrations in `Blocks.java` all use `Block.Properties...noCollision()`. Base `Block.getCollisionShape` therefore returns `VoxelShapes.empty()` for these states regardless of the smaller render/outline shapes supplied by `FlowerBlock.getShape` or inherited by `TallFlowerBlock`; the face-full predicate is false. The audited raw 1.14.4 `walls.json` payload (SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; see `wall_resource_membership/extraction-record.md` and `wall_resource_membership_audit/verdict-r2.md`) enumerates 14 wall keys and contains none of these 14 flower keys; none is a fence gate. Thus all 18 target states also yield false in all four directions.

## Result and limits

No wall-neighbor predicate difference was found for this exact registered flower/tall-flower slice: old and target bits are false in all 72 cases. This does not close flower collision/provider geometry for other consumers, placement/update lifecycle, resource or state migration paths, unrelated wall keys, other block providers, or either pair's remaining tick/provider inventory. Independent source acceptance is pending.


