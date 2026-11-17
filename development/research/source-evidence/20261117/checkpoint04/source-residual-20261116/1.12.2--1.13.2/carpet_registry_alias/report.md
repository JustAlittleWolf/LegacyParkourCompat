# Carpet registry alias and block-state flattening

## Bound inputs

This source-only follow-up is bound to source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` (1.12.2) and `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` (1.13.2), both ready Ornithe Feather Gen2 build 2 sources. The companion CarpetBlock report is `5b9a38b6714b4eb8f70222af4c7ef2742a3bd9540add1ae139d751807191aef9`; its independent audit is `06eb5a3e79d97d42fd66486290e6eff124457d4fef5a195d2f2345ecc1101fe4`.

Manifest-listed source hashes were recomputed for the files used here. 1.12.2: `Block.java` `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`, `Blocks.java` `8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e`, `CarpetBlock.java` `67db1ac10dbe872d9ddeb13914333f614eef52003ea8f274fd113488ad84d1f2`, and `item/DyeColor.java` `b6157274ae9af0ad72e8bd6538663a916a4e27702c4cadf70e7cd018dbcb8f9f`. 1.13.2: `Block.java` `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`, `Blocks.java` `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303`, `CarpetBlock.java` `cc3a3e0356a9a0e1ba4d82e4dc2caf880999294bc72ad7be62a43e031faaa4a4`, and `unmapped/C_80754635.java` `2aff1baacd621e5719ee221181cac4593e382a6fd1c3f0215f1a7bddbbb7330e`.

## Old block and states

In 1.12.2, `Block.java:1145` registers block id 171 under `carpet`, and `Blocks.java:462` resolves `CARPET` from that key (`minecraft:carpet`). `CarpetBlock.java:19-24, 83-106` defines the sole variant property `COLOR`, defaults it to WHITE, and serializes/deserializes carpet metadata using `DyeColor.getId()` / `DyeColor.byId()`. `item/DyeColor.java:7-22` defines the IDs and serialized color keys below. The separate enum `metadata` values are reversed for this dye enum; CarpetBlock explicitly uses `id`, so the table uses IDs 0 through 15 in the listed order.

## Exact alias crosswalk

In 1.13.2, `unmapped/C_80754635.java:22-38` registers old block-state SNBT aliases against an indexed canonical block-state value: the helper stores the first state string at index `i`, and maps each following state string to that same index; `m_11299400` returns the indexed canonical state for a matching alias and otherwise returns its input unchanged. Its carpet entries at lines 7284-7299 map every legal old `minecraft:carpet` color state to a color-specific canonical key. The index equals old carpet block id `171 * 16` plus CarpetBlock metadata/id.

| Old id / metadata | Old block state | 1.13.2 canonical registry key | New CarpetBlock color |
|---:|---|---|---|
| 171 / 0 | `minecraft:carpet[color=white]` | `minecraft:white_carpet` | WHITE |
| 171 / 1 | `minecraft:carpet[color=orange]` | `minecraft:orange_carpet` | ORANGE |
| 171 / 2 | `minecraft:carpet[color=magenta]` | `minecraft:magenta_carpet` | MAGENTA |
| 171 / 3 | `minecraft:carpet[color=light_blue]` | `minecraft:light_blue_carpet` | LIGHT_BLUE |
| 171 / 4 | `minecraft:carpet[color=yellow]` | `minecraft:yellow_carpet` | YELLOW |
| 171 / 5 | `minecraft:carpet[color=lime]` | `minecraft:lime_carpet` | LIME |
| 171 / 6 | `minecraft:carpet[color=pink]` | `minecraft:pink_carpet` | PINK |
| 171 / 7 | `minecraft:carpet[color=gray]` | `minecraft:gray_carpet` | GRAY |
| 171 / 8 | `minecraft:carpet[color=silver]` | `minecraft:light_gray_carpet` | SILVER |
| 171 / 9 | `minecraft:carpet[color=cyan]` | `minecraft:cyan_carpet` | CYAN |
| 171 / 10 | `minecraft:carpet[color=purple]` | `minecraft:purple_carpet` | PURPLE |
| 171 / 11 | `minecraft:carpet[color=blue]` | `minecraft:blue_carpet` | BLUE |
| 171 / 12 | `minecraft:carpet[color=brown]` | `minecraft:brown_carpet` | BROWN |
| 171 / 13 | `minecraft:carpet[color=green]` | `minecraft:green_carpet` | GREEN |
| 171 / 14 | `minecraft:carpet[color=red]` | `minecraft:red_carpet` | RED |
| 171 / 15 | `minecraft:carpet[color=black]` | `minecraft:black_carpet` | BLACK |

The new registrations and constructor colors are in 1.13.2 `Block.java:1399-1429`; `Blocks.java:986-1001` resolves the corresponding sixteen named fields. In particular, old `silver` maps to the `light_gray_carpet` key, whose new CarpetBlock is constructed with `DyeColor.SILVER` (`Block.java:1417-1420`). There is no old `light_gray` dye key in this version's CarpetBlock state vocabulary.

## Result and limits

**Closed for legal vanilla CarpetBlock states:** the alias is one old registry key plus exactly sixteen legal color/id states, each with an explicit old-state-to-new-key mapping. The flattening entries are block-state mappings, not evidence about item-stack fixers. This result does not claim item migration behavior. Inputs outside the finite set of registered old states—such as an invalid/missing color property or a non-vanilla key—have no carpet alias entry in this table; `m_11299400` leaves an unmatched Dynamic unchanged. No movement behavior or geometry claim is added here.
