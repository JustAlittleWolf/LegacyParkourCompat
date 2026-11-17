# 1.13.2 to 1.14.4 wall-bit comparison: pumpkin family and melon

Scope: exact registered keys `minecraft:pumpkin`, `minecraft:carved_pumpkin`, `minecraft:jack_o_lantern`, and `minecraft:melon`; compare the old generic wall-connection result with the target wall attachment result for each of the four horizontal directions and every legal block state. This packet makes no trajectory or pair-completion claim.

## Source identity

Both sides are Ornithe Feather Gen2 build.2 exact decompilations. The ready markers identify version IDs 1.13.2 and 1.14.4 and bind the local source-manifest bytes:

- 1.13.2: `decompiled_minecraft/1.13.2/ornithe-feather.ready.json`, SHA-256 `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`; manifest `decompiled_minecraft/1.13.2/ornithe-feather.sources.sha256`, SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`, matching `ready.sourceManifestSha256`.
- 1.14.4: `decompiled_minecraft/1.14.4/ornithe-feather.ready.json`, SHA-256 `bf8c9399e3a36ddd7edc78d98413319000895f49e88db224335f62b2baf21e77`; manifest `decompiled_minecraft/1.14.4/ornithe-feather.sources.sha256`, SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`, matching `ready.sourceManifestSha256`.

Relevant source files, with hashes matching those manifests:

| Release | Relative source path | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` |
| 1.13.2 | `net/minecraft/block/CarvedPumpkinBlock.java` | `227d2975c736c3dbc9f83331a897c383d8ffef5b6c035d04b2d6b661a1007291` |
| 1.13.2 | `net/minecraft/block/PumpkinBlock.java` | `3cdfac9817a2ad46014223c28814cd8f8b5ed07ba08051915b1ff7972324973a` |
| 1.13.2 | `net/minecraft/block/MelonBlock.java` | `4a112ae70e4bf44889d2da375a773d14e2835aa2e0a51b3a2df7abd1c3b283b0` |
| 1.14.4 | `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.14.4 | `net/minecraft/block/WallBlock.java` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| 1.14.4 | `net/minecraft/block/Blocks.java` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| 1.14.4 | `net/minecraft/block/CarvedPumpkinBlock.java` | `d5ad9f54948ece63181f69f74e4c29db6393d237fc46928ec9609effa9ef5622` |
| 1.14.4 | `net/minecraft/block/PumpkinBlock.java` | `972b9140d9729c3b06971f14541373a70c5e078cffcf4568e7bf186230805785` |
| 1.14.4 | `net/minecraft/block/MelonBlock.java` | `636a7c3ca44dedeb300d80813a3bc2fc7a4076014829081a4c77f7993789172a` |
| 1.14.4 | `net/minecraft/data/tag/BlockTagsProvider.java` | `0bd051d11a0c85ecc61cd2202a6ed33923db97238da0b065a647b932132d1bfa` |

## Compared gates and state domains

In 1.13.2, `WallBlock.shouldConnectTo` (WallBlock.java:51-65) returns `!isExceptionForConnection(block) && shape == FaceShape.SOLID || bl`. `isExceptionForConnection` explicitly excludes `MELON_BLOCK`, `PUMPKIN`, `CARVED_PUMPKIN`, and `LIT_PUMPKIN` (lines 57-65), in addition to the shared `Block.isExceptionForAttachment` exclusions. Thus the result is false for every one of the four keys regardless of its face shape. `Block.getFaceShape` defaults to `FaceShape.SOLID` (Block.java:391-394); carved pumpkin's `FACING` cannot change the result because the explicit exception is checked before that face-shape result can enable a connection.

In 1.14.4, `WallBlock.m_71853799` (WallBlock.java:48-51) computes `bl2` as membership in `BlockTags.WALLS` or an eligible `FenceGateBlock`, then returns `!Block.isExceptionForAttachment(block) && bl || bl2`. `Block.isExceptionForAttachment` explicitly lists all four target block objects (Block.java:260-266), so its shape-based branch is false. The target `BlockTagsProvider` WALLS registration (BlockTagsProvider.java:270-286) contains the wall block registry keys only and contains none of these four; none of the four is a FenceGateBlock. Therefore the independent `bl2` branch is also false. This WALLS result is derived from the exact pinned source registration provider, not inferred from tag omission in a decompiled source tree. The target full-cube base shape and collision behavior cannot alter these false results because the exception gate dominates `bl`.

Registry/type anchors: 1.13.2 `Block.java` static registration at lines 1076-1088 and 1185-1186 binds pumpkin, carved_pumpkin, jack_o_lantern and melon to PumpkinBlock, CarvedPumpkinBlock, CarvedPumpkinBlock and MelonBlock respectively. 1.14.4 `Blocks.java` lines 576-578, 590-595 and 709-711 binds the same keys to those corresponding classes. The carved pumpkin classes use `HorizontalFacingBlock.FACING`; that property is `Properties.HORIZONTAL_FACING`, whose source domain is `Direction.Plane.HORIZONTAL` in both versions (`state/property/Properties.java`: 1.13.2 line 57, 1.14.4 line 63), hence four legal values: north, east, south, west. The crosswalk row's cardinality-six note is inconsistent with these exact class/property sources; this comparison covers the four horizontal states. Pumpkin and melon are singleton-state classes in both versions.

## Per-key result

| Registry key | Legal state domain | Old wall bit for N/E/S/W | Target wall bit for N/E/S/W | Difference |
|---|---|---|---|---|
| `minecraft:pumpkin` | singleton | false / false / false / false | false / false / false / false | none |
| `minecraft:carved_pumpkin` | `FACING` = north, east, south, west | false / false / false / false for each state | false / false / false / false for each state | none |
| `minecraft:jack_o_lantern` | `FACING` = north, east, south, west | false / false / false / false for each state | false / false / false / false for each state | none |
| `minecraft:melon` | singleton | false / false / false / false | false / false / false / false | none |

Conclusion: for the four named exact keys and full legal state domains, wall connection bits do not differ between these endpoints. This closes only this bounded key slice; it does not close the remaining registry/state/neighbor/provider crosswalk or the pair.
