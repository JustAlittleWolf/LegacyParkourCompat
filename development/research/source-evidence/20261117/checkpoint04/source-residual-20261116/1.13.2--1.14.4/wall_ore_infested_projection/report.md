# Ore and infested block wall-face projection

Pair: 1.13.2 → 1.14.4. This note covers exactly the 13 registry keys classified as `OreBlock` or `InfestedBlock` with old face-shape owner `Block` in the unexamined wall projection TSV. The TSV SHA-256 is `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`. Its rows mark all 13 old and target state products as singleton. This packet does not claim full provider inventory closure, player trajectory impact, or pair freeze.

## Source identity

- 1.13.2 Ornithe Feather Gen2 build.2 ready source-manifest SHA-256: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; ready marker: `decompiled_minecraft/1.13.2/ornithe-feather.ready.json`.
- 1.14.4 Ornithe Feather Gen2 build.2 ready source-manifest SHA-256: `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`; ready marker: `decompiled_minecraft/1.14.4/ornithe-feather.ready.json`.
- The report's Java hashes below were checked against the corresponding ready source manifests.

| Version | Relative Java path | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` |
| 1.13.2 | `net/minecraft/block/OreBlock.java` | `e9e6b3266604d8436d0afe6a9d1eab08e060d5e9ded3f3f213f9694d4f769786` |
| 1.13.2 | `net/minecraft/block/InfestedBlock.java` | `7c3f64c18c06be38e70d74dbdc23ba4da3e82e97000cbda1983d3927e78558a4` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.14.4 | `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.14.4 | `net/minecraft/block/WallBlock.java` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| 1.14.4 | `net/minecraft/block/OreBlock.java` | `dc06a4b4186a91937c36a2c90cd7d43b18400e70181f1fbba7847c32dcc48fe3` |
| 1.14.4 | `net/minecraft/block/InfestedBlock.java` | `a746a621ea6250f73fe9f9d1fa4a39d640193a1194b98a422a24ec8f3c877b03` |
| 1.14.4 | `net/minecraft/block/Blocks.java` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| 1.14.4 | `net/minecraft/block/state/BlockState.java` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| 1.14.4 | `net/minecraft/tag/BlockTags.java` | `dc59d07204b25e5d852d0384e849975367590a3cbc2b9a293396d0bf5637e52a` |

## Compared keys

Seven ore keys: `minecraft:coal_ore`, `minecraft:diamond_ore`, `minecraft:emerald_ore`, `minecraft:gold_ore`, `minecraft:iron_ore`, `minecraft:lapis_ore`, and `minecraft:nether_quartz_ore`.

Six infested keys: `minecraft:infested_chiseled_stone_bricks`, `minecraft:infested_cobblestone`, `minecraft:infested_cracked_stone_bricks`, `minecraft:infested_mossy_stone_bricks`, `minecraft:infested_stone`, and `minecraft:infested_stone_bricks`.

The 1.13.2 `Block` bootstrap registers these as `OreBlock` or `InfestedBlock` (`Block.java`, registration sites around lines 834–1299). In 1.14.4 `Blocks.java` registers the same keys with those concrete classes (ore registrations around lines 104–778; infested registrations around lines 678–692). Both subclasses inherit the base shape/face methods and do not override them. Their classes declare no state properties, matching the singleton property product in the exact TSV rows.

## Predicate comparison

In 1.13.2, `Block.getFaceShape` returns `FaceShape.SOLID` by default (`Block.java:382–385`). `BlockState.getFaceShape` dispatches to that block method (`block/state/BlockState.java:285–287`). `WallBlock.shouldConnectTo` accepts a SOLID face unless `isExceptionForConnection` rejects the block (`WallBlock.java:51–64`). Neither selected class nor its registered keys matches the attachment/connection exception list, so all 13 yield true on each horizontal face.

In 1.14.4, `WallBlock` calls the neighbor state's face-full query and then accepts it when the block is not an attachment exception, or when it is in `BlockTags.WALLS` or a matching fence gate (`WallBlock.java:48–51, 67–70`). `BlockState.m_87223014` returns the cached face-full value or calls `Block.isFaceSolid` (`block/state/BlockState.java:326–328`). The base `Block` has `hasCollision = true` by default and `getCollisionShape` returns the state's shape when that flag is set (`Block.java:371–377, 811, 853–854`); base `getShape` returns the full block shape (`Block.java:371–374`). The two selected classes inherit those defaults and use ordinary `Material.STONE` or `Material.CLAY` properties without `.noCollision()` in registration. They are not leaves, attachment exceptions, walls-tag members, or fence gates.

For the resource gate, the pinned raw 1.14.4 client extraction records `data/minecraft/tags/blocks/walls.json` SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; its 14 values are wall block keys and none of the 13 examined keys. `leaves.json` SHA-256 `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991` likewise lists six leaves keys, none examined. These payload hashes and resource membership are already independently accepted by `wall_resource_membership_audit/verdict-r2.md` SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`; that verdict's membership claim is limited to TNT absence, while this report explicitly checked the 13 values against its extracted payload.

## Result and limits

No difference in the wall horizontal neighbor bit for these 13 keys: old SOLID/non-exception is true; target full collision face/non-exception is true. The comparison does not cover wall state combination/shape geometry, other registry classes or keys, any player movement path, or pair completion. This is a source coverage note, not a permanent difference finding.
