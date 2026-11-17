# Wall-face projection: flower pots and potted plants

## Bounded result

For the common base key `minecraft:flower_pot` and all 21 common registered `minecraft:potted_*` keys listed below, the 1.13.2 wall predicate is false on north, east, south and west, and the 1.14.4 wall predicate is also false on all four. No wall-bit or wall-collision-shape change is established for this bounded family.

In 1.13.2 every one of these keys is a `FlowerPotBlock`. Its horizontal `getFaceShape` result is explicitly `FaceShape.UNDEFINED` regardless of state, world, position or face. The old generic wall arm requires `shape == FaceShape.SOLID` and a non-exception block; flower pots are not in `isExceptionForConnection`, but fail the face-shape test. In 1.14.4 each registered pot uses the same `FlowerPotBlock` geometry. Its collision shape is the centered base cuboid `[5,11] / 16` in X and Z and `[0,6] / 16` in Y, with no state- or content-dependent variation. The target wall's `BlockState.m_87223014` routes to `Block.isFaceSolid`, which projects that collision shape for the queried face and accepts only a full-block face. The pot's projected face is not full for any horizontal direction. Flower pots are neither target wall-tag blocks nor fence gates, so the other wall arm cannot admit them.

This closes only these registered pot keys for wall-face projection. It does not close the overall wall crosswalk or the version pair.

## Exact evidence identity

Pair: Minecraft 1.13.2 → 1.14.4; Ornithe Feather Gen2 build 2.

- 1.13.2 `decompiled_minecraft/1.13.2/ornithe-feather.sources.sha256`: SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- 1.14.4 `decompiled_minecraft/1.14.4/ornithe-feather.sources.sha256`: SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`.
- Census input `wall_remaining_face_shape_census/report.md`: SHA-256 `f1a12c7564f14c91c652bea6603e8ada8350b98c2892df05c591c0161d4e041d` (matches the assigned revision).

Recomputed source-file hashes agree with each endpoint manifest:

| Release | Source file | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` |
| 1.13.2 | `net/minecraft/block/FlowerPotBlock.java` | `7b958c584124459e209b4e626e0268bf5e479ea4cb190fd4ce7afb6940f4f529` |
| 1.13.2 | `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` |
| 1.14.4 | `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.14.4 | `net/minecraft/block/Blocks.java` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| 1.14.4 | `net/minecraft/block/FlowerPotBlock.java` | `15c602804adb2bdd5c2d61df859770592f3e0ae6f945a6ec59c5ccc1ebc7fdb4` |
| 1.14.4 | `net/minecraft/block/WallBlock.java` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| 1.14.4 | `net/minecraft/block/state/BlockState.java` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |

## Registration and content join

The old bootstrap registers the base pot with AIR at `Block.java:1242`, and its 21 potted variants at `Block.java:1243–1263`. The target directly registers the same base and common potted identifiers at `Blocks.java:798–862`. `FlowerPotBlock` stores each content block in a field but every instance inherits the same empty state definition and the same shape provider. The content selector therefore has no collision-shape effect.

| Common registered key | 1.13.2 content | 1.14.4 content |
|---|---|---|
| `flower_pot` | `air` | `air` |
| `potted_oak_sapling` | `oak_sapling` | `oak_sapling` |
| `potted_spruce_sapling` | `spruce_sapling` | `spruce_sapling` (`Blocks.ALL` is registered under `spruce_sapling`) |
| `potted_birch_sapling` | `birch_sapling` | `birch_sapling` |
| `potted_jungle_sapling` | `jungle_sapling` | `jungle_sapling` |
| `potted_acacia_sapling` | `acacia_sapling` | `acacia_sapling` |
| `potted_dark_oak_sapling` | `dark_oak_sapling` | `dark_oak_sapling` |
| `potted_fern` | `fern` | `fern` |
| `potted_dandelion` | `dandelion` | `dandelion` |
| `potted_poppy` | `poppy` | `poppy` |
| `potted_blue_orchid` | `blue_orchid` | `blue_orchid` |
| `potted_allium` | `allium` | `allium` |
| `potted_azure_bluet` | `azure_bluet` | `azure_bluet` |
| `potted_red_tulip` | `red_tulip` | `red_tulip` |
| `potted_orange_tulip` | `orange_tulip` | `orange_tulip` |
| `potted_white_tulip` | `white_tulip` | `white_tulip` |
| `potted_pink_tulip` | `pink_tulip` | `pink_tulip` |
| `potted_oxeye_daisy` | `oxeye_daisy` | `oxeye_daisy` |
| `potted_red_mushroom` | `red_mushroom` | `red_mushroom` |
| `potted_brown_mushroom` | `brown_mushroom` | `brown_mushroom` |
| `potted_dead_bush` | `dead_bush` | `dead_bush` |
| `potted_cactus` | `cactus` | `cactus` |

The 1.13.2 `Blocks.POTTED_CORNFLOWER` field resolves to the `spruce_sapling` registry entry (`Blocks.java:645`); there is no old registered `potted_cornflower` key. The target adds `potted_cornflower`, `potted_lily_of_the_valley`, `potted_wither_rose` and `potted_bamboo` (`Blocks.java:844–862, 1847`). They are target-only block keys, not common-key wall comparisons for this assigned slice. Field aliases and `FlowerPotBlock.BLOCKS_BY_CONTENT` are registry/item interactions; they do not change the provider or face projection, so no item-use, item-map overwrite, pick-item, loot or block-entity claim is made here.

## Source comparison details

- **Old provider and old wall predicate:** 1.13.2 `FlowerPotBlock.java:22–35, 110–112` shares one static `SHAPE`, returns it from `getShape`, and returns `UNDEFINED` from `getFaceShape`. `WallBlock.java:51–55` admits the generic face only for `SOLID` after its exception check; `:81–84, 105–116` query the four opposite neighbor faces both at placement and horizontal updates. `WallBlock.isExceptionForConnection` delegates to `Block.isExceptionForAttachment` and adds special cases (`WallBlock.java:57–71`); pot blocks are not among them. Neither that exception result nor the neighbor face alters the failed `UNDEFINED != SOLID` gate.
- **Target shape and wall projection:** 1.14.4 `FlowerPotBlock.java:23–36` holds the same content-independent `SHAPE = Block.block(5.0, 0.0, 5.0, 11.0, 6.0, 11.0)` and returns it with an ignored `CollisionContext`. `Block.Properties.hasCollision` defaults true (`Block.java:808–812`); pot registrations do not call `noCollision`. `Block.java:376–378, 402–413` gates collision shape and defines face-fullness as full-block comparison after face projection. `BlockState.java:189–202, 326–328` dispatches the shape and resolves wall's per-face predicate (`m_87223014`) to `Block.isFaceSolid`. The common keys use no additional state properties and do not override the provider. Target `WallBlock.java:48–51, 67–70, 91–103` applies that result to the four neighbor directions and has the same result on updates. A projected 6/16-wide by 6/16-high pot side cannot cover the full unit face; the base also occupies only a 6/16 by 6/16 footprint, so the four horizontal face predicates are false.
- **State/domain coverage:** each common registry key constructs a fresh `FlowerPotBlock` with only its content and `Material.DECORATION`/`instabreak()` properties. `FlowerPotBlock` declares no state properties. The 21 potted keys therefore each have exactly the provider's same collision shape in their sole default state; the base pot likewise has one default state with AIR content. This reports registry keys, not a claim about arbitrary modded subclasses or dynamic worlds.

## Open limits

No movement difference is established for this pot-family wall projection. The target-only potted identifiers are not compared against absent old block keys. `potted_bamboo` does not have a source-era counterpart; item, rendering, block-entity and placement behavior are outside this wall-collision result. Remaining keys and dependencies in the census report remain open, and the overall common wall registration/state-space census is incomplete. No pair freeze is implied.
