# 1.8.9 colored block metadata → 26.2 chunk palette state

## Qualified subset

For each of old block IDs **35 wool, 95 stained glass, 159 stained hardened clay, 160 stained glass pane, and 171 carpet**, every metadata value **0–15** has a bounded color identity and exact current registry key: 80 `(id, metadata)` pairs total. In 1.8.9 these color decoders use `DyeColor.byId(metadata)` and the legacy color IDs are identical to their metadata values. The old enum’s ID order and the 26.2 enum / `ColorCollection.VALUES` order are both white, orange, magenta, light blue, yellow, lime, pink, gray, silver/light gray, cyan, purple, blue, brown, green, red, black. Thus the DataFixer rows’ names select the same color for this subset.

| Metadata / color | 35 wool | 95 stained glass | 159 stained hardened clay | 160 stained glass pane | 171 carpet |
|---:|---|---|---|---|---|
| 0 white | `white_wool` | `white_stained_glass` | `white_terracotta` | `white_stained_glass_pane` | `white_carpet` |
| 1 orange | `orange_wool` | `orange_stained_glass` | `orange_terracotta` | `orange_stained_glass_pane` | `orange_carpet` |
| 2 magenta | `magenta_wool` | `magenta_stained_glass` | `magenta_terracotta` | `magenta_stained_glass_pane` | `magenta_carpet` |
| 3 light_blue | `light_blue_wool` | `light_blue_stained_glass` | `light_blue_terracotta` | `light_blue_stained_glass_pane` | `light_blue_carpet` |
| 4 yellow | `yellow_wool` | `yellow_stained_glass` | `yellow_terracotta` | `yellow_stained_glass_pane` | `yellow_carpet` |
| 5 lime | `lime_wool` | `lime_stained_glass` | `lime_terracotta` | `lime_stained_glass_pane` | `lime_carpet` |
| 6 pink | `pink_wool` | `pink_stained_glass` | `pink_terracotta` | `pink_stained_glass_pane` | `pink_carpet` |
| 7 gray | `gray_wool` | `gray_stained_glass` | `gray_terracotta` | `gray_stained_glass_pane` | `gray_carpet` |
| 8 silver / light_gray | `light_gray_wool` | `light_gray_stained_glass` | `light_gray_terracotta` | `light_gray_stained_glass_pane` | `light_gray_carpet` |
| 9 cyan | `cyan_wool` | `cyan_stained_glass` | `cyan_terracotta` | `cyan_stained_glass_pane` | `cyan_carpet` |
| 10 purple | `purple_wool` | `purple_stained_glass` | `purple_terracotta` | `purple_stained_glass_pane` | `purple_carpet` |
| 11 blue | `blue_wool` | `blue_stained_glass` | `blue_terracotta` | `blue_stained_glass_pane` | `blue_carpet` |
| 12 brown | `brown_wool` | `brown_stained_glass` | `brown_terracotta` | `brown_stained_glass_pane` | `brown_carpet` |
| 13 green | `green_wool` | `green_stained_glass` | `green_terracotta` | `green_stained_glass_pane` | `green_carpet` |
| 14 red | `red_wool` | `red_stained_glass` | `red_terracotta` | `red_stained_glass_pane` | `red_carpet` |
| 15 black | `black_wool` | `black_stained_glass` | `black_terracotta` | `black_stained_glass_pane` | `black_carpet` |

All names in the table are `minecraft:<name>`. The source-backed 26.2 `BlockStateData` aliases bind each row to the old serialized block name (`wool`, `stained_glass`, `stained_hardened_clay`, `stained_glass_pane`, `carpet`) and corresponding color property where applicable. The chunk conversion indexes the table with `(id << 4) | metadata` and passes the selected entry to `setBlock`; the post-rename names above are the emitted keys. None of these 80 rows has a later name/property DataFixer registered for its target family. The already extracted candidate projection covers the 80 exact rows; this finding adds old decoder-to-color and current default-state qualification, not collision-shape equivalence.

## Exact source evidence

Canonical source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/` (read-only). Embedded provenance states exact source versions **1.8.9** with `ornithe-feather` mappings (`feather-gen2-1.8.9+build.2`) and **26.2** `unobfuscated`; the `ready.json`, `success.log`, and `sources.sha256` artifacts were present in each version directory. The recomputed SHA-256 values for all cited source files are recorded in `source-hashes.json`.

1.8.9 `ornithe-feather`: `block/Block.java:888,1031,1175–1180,1195` registers the five legacy IDs/names (159 is the stained hardened-clay registration at 1175–1179); `Block.deserialize:130–134` extracts metadata from the legacy packed value and dispatches to the registered block decoder. `block/ColoredBlock.java:15–20,41–43` defines white default and `getStateFromMetadata` as `defaultState().set(COLOR, DyeColor.byId(metadata))`; it is used for IDs 35 and 159. `block/StainedGlassBlock.java:18–24,65–67`, `block/StainedGlassPaneBlock.java:17–23,49–51`, and `block/CarpetBlock.java:18–28,103–105` provide the same metadata decoder for 95, 160, and 171 respectively. `item/DyeColor.java:8–23,67–73,84–88` establishes the full ordered ID table and bounds behavior. `block/Blocks.java` and the source decoder do not add a color permutation for these IDs.

26.2 `unobfuscated`: `util/datafix/DataFixers.java:530,534` registers the structure-template map and chunk palette conversion at schema 1450/1451.1. `util/datafix/fixes/ChunkPalettedStorageFix.java:735–750` calculates the legacy index, applies the virtual-neighbor path where listed, then calls `BlockStateData.getTag(id)`. `fixes/BlockStateData.java` has the per-color registrations: wool at `1196–1211`, stained glass at `4092–4107`, dyed terracotta at `7087–7102`, stained glass pane beginning at `7106` (with per-color 16 neighbor-mask variants), and carpet at `7663–7678`. These entries emit flattened per-color block names. The existing extraction includes the source-backed rename chain and absence of additional applicable state fixes for these rows.

The current registry is also source-backed: `world/level/block/Blocks.java:833–840` registers wool; `2150–2157` stained glass; `2953–2960` dyed terracotta; `2963–2970` stained glass panes; and `3062–3069` carpet through `ColorCollection.registerBlocks`, paired with the corresponding `BlockItemIds` color groups. `ColorCollection.java:18–35,42–53,65–66` defines the exact same ordered 16-color zip and color-prefixed registry names. The current default state has **no serialized color property**: wool and terracotta are plain `Block` instances; stained glass, pane, and wool carpet carry color in the concrete block instance. `BlockState.CODEC` uses the registry block’s `defaultBlockState` (`BlockState.java:9`), while `StateHolder.codec` substitutes that default when `Properties` is absent (`StateHolder.java:145–158`). Therefore the qualified current tuples are `{}` for wool, stained glass, terracotta and carpet. This is a tuple statement only; it does not claim those blocks’ behavior or shapes equal the 1.8.9 blocks.

## Open context: stained glass pane

The color dimension is qualified for all 16 stained-glass-pane states, but its complete movement-relevant state is **not**. The serialized palette candidates include old `north/east/south/west` masks, while `ChunkPalettedStorageFix` puts legacy block ID 160 on the `VIRTUAL` path and performs side-mask/neighborhood repair. Modern `StainedGlassPaneBlock` defaults all four connections to `false` and also has `waterlogged=false`; 1.8.9 has no waterlogged property. The exact post-DataFixer connection tuple remains dependent on chunk/neighbor context, and there is no old-to-modern waterlogged equivalence. Both facts remain open. Do not use the `false` defaults to claim pane tuple equivalence or collision equivalence. This qualification intentionally stops at the pane’s color key and enumerated current default; it does not close its contextual tuple.

## Inputs and limits

The existing source projection input is `.native-source/20261028/collision-next/dda-current-state-projection-20261029/block-state-data-current-name-projection.jsonl` (SHA-256 `C7A8B04869425690B2D6BD200C9630752FC1A58C34526B6A5F51898F64B0DC02`). Its `source-hashes.json` binds accepted 1.8.9 projection/crosswalk source partitions and reviews; the exact partition hashes are retained there. This bounded extension reads that projection and the exact canonical Java sources listed above. No wiki material, product source/history, build, test, game launch, or decompilation was used. It establishes saved chunk palette state naming/default decoding only; shape/provider equivalence and runtime movement parity remain unexamined.
