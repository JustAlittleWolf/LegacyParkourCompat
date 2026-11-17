# Carpet registry alias audit

## Verdict

**Accepted for the bounded block-state alias claim.** The audited input is `carpet_registry_alias/report.md`, SHA-256 `48bc0f384f756ea13f2354e78b99c92178f08cabc81e017314d2365f2dfdcb40`. I independently checked the exact-pair readiness records and source-manifest hashes, and recomputed every source-file hash used by the report. Both source sets are marked ready, version IDs are 1.12.2 and 1.13.2, and use Ornithe Feather Gen2 build 2.

The source-manifest hashes match the readiness records and report:

- 1.12.2: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`
- 1.13.2: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`

The report's per-file hashes also match the canonical manifest entries and recomputed bytes: 1.12.2 `Block.java` `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`, `Blocks.java` `8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e`, `CarpetBlock.java` `67db1ac10dbe872d9ddeb13914333f614eef52003ea8f274fd113488ad84d1f2`, and `item/DyeColor.java` `b6157274ae9af0ad72e8bd6538663a916a4e27702c4cadf70e7cd018dbcb8f9f`; 1.13.2 `Block.java` `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`, `Blocks.java` `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303`, `CarpetBlock.java` `cc3a3e0356a9a0e1ba4d82e4dc2caf880999294bc72ad7be62a43e031faaa4a4`, and `unmapped/C_80754635.java` `2aff1baacd621e5719ee221181cac4593e382a6fd1c3f0215f1a7bddbbb7330e`.

## Accepted crosswalk

In 1.12.2, `Block.java:1145` registers block ID 171 as `carpet`; `Blocks.java:462` resolves `CARPET` from the `carpet` registry key. `CarpetBlock.java:19-24, 95-106` defines `COLOR`, defaults to WHITE, and reads/writes metadata through `DyeColor.byId` / `getId`. The old enum's IDs 0–15 and serialized keys produce this exact crosswalk. In 1.13.2, `C_80754635.java:7284-7299` registers each old block-state SNBT alias and its canonical state at index `171 * 16 + id`; `Block.java:1399-1429` constructs the named carpet blocks and `Blocks.java:986-1001` resolves their fields.

| Old ID / color ID | Old state key and property | 1.13.2 canonical key | Constructed target color |
|---:|---|---|---|
| 171 / 0 | `minecraft:carpet[color=white]` | `minecraft:white_carpet` | `DyeColor.WHITE` |
| 171 / 1 | `minecraft:carpet[color=orange]` | `minecraft:orange_carpet` | `DyeColor.ORANGE` |
| 171 / 2 | `minecraft:carpet[color=magenta]` | `minecraft:magenta_carpet` | `DyeColor.MAGENTA` |
| 171 / 3 | `minecraft:carpet[color=light_blue]` | `minecraft:light_blue_carpet` | `DyeColor.LIGHT_BLUE` |
| 171 / 4 | `minecraft:carpet[color=yellow]` | `minecraft:yellow_carpet` | `DyeColor.YELLOW` |
| 171 / 5 | `minecraft:carpet[color=lime]` | `minecraft:lime_carpet` | `DyeColor.LIME` |
| 171 / 6 | `minecraft:carpet[color=pink]` | `minecraft:pink_carpet` | `DyeColor.PINK` |
| 171 / 7 | `minecraft:carpet[color=gray]` | `minecraft:gray_carpet` | `DyeColor.GRAY` |
| 171 / 8 | `minecraft:carpet[color=silver]` | `minecraft:light_gray_carpet` | `DyeColor.SILVER` |
| 171 / 9 | `minecraft:carpet[color=cyan]` | `minecraft:cyan_carpet` | `DyeColor.CYAN` |
| 171 / 10 | `minecraft:carpet[color=purple]` | `minecraft:purple_carpet` | `DyeColor.PURPLE` |
| 171 / 11 | `minecraft:carpet[color=blue]` | `minecraft:blue_carpet` | `DyeColor.BLUE` |
| 171 / 12 | `minecraft:carpet[color=brown]` | `minecraft:brown_carpet` | `DyeColor.BROWN` |
| 171 / 13 | `minecraft:carpet[color=green]` | `minecraft:green_carpet` | `DyeColor.GREEN` |
| 171 / 14 | `minecraft:carpet[color=red]` | `minecraft:red_carpet` | `DyeColor.RED` |
| 171 / 15 | `minecraft:carpet[color=black]` | `minecraft:black_carpet` | `DyeColor.BLACK` |

The index arithmetic is consistent: `171 * 16 = 2736`, through `2751` for ID 15. In the especially relevant ID 8 row, the old spelling is `silver`; the new registry key is `light_gray_carpet`, and its constructor explicitly uses `DyeColor.SILVER` (1.13.2 `Block.java:1417-1420`). The 1.13.2 `DyeColor` enum names that value's serialized key `light_gray`; it does not change the alias's old property spelling.

## Scope and limits

`C_15874933` applies `C_80754635.m_11299400` to the `TypeReferences.BLOCK_STATE` remainder (`C_15874933.java:14-21`), establishing this as a block-state data-fixer path. It is not evidence of item-stack fixer behavior; no item migration result is accepted here. `C_66595058` is a separate `BlockNameFlatteningFix` over `TypeReferences.BLOCK_NAME`, and the general item-ID/item-name fixes are separate operations; neither is covered by this crosswalk.

`m_11299400` looks up the incoming `Dynamic` in the registered alias map and returns the indexed canonical state only on a match; otherwise this method returns the incoming `Dynamic` unchanged (`C_80754635.java:33-41`). A missing/invalid carpet color or extra data that makes the `Dynamic` unequal to every registered old-state alias therefore has no match in this table. This is a statement about this specific block-state alias lookup, not a prediction for every data-fixer path a malformed record might traverse. The static SNBT parser logs and throws on malformed table literals (`:62-69`); that is distinct from lookup of a valid `Dynamic` and does not support a broader promise about malformed external input handling.

This audit accepts only the carpet registry/block-state alias and its finite legal-state crosswalk. It makes no movement, geometry, item migration, general data-fixer, or exact intermediate-version-boundary claim.
