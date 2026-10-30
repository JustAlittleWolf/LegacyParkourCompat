# 1.8.9 metadata decoding joined to 26.2 chunk-state serialization

## Scope and result

This bounded static join covers old block IDs **1, 3, 5, 12, 17** (stone, dirt, planks, sand, log), metadata **0–15** for each, and the ordinary chunk-section input route in 26.2. `state-tuples.jsonl` contains all 80 rows: the exact old decoder tuple, the DataFixer serialized `Name`/`Properties`, and the state selected by the actual 26.2 registry and state codec. It also records explicit-map versus block-default lookup, source line, rename trace and later-fix scope.

The old decoders and the exact rows agree for all 80 serialized nibble inputs, including each old enum fallback. For log metadata 12–15 the final 26.2 state is a wood block with absent `Properties`; the current codec therefore selects `axis=y`, while 1.8.9 decoded `axis=none`. This documents the serialized conversion result and its state loss. It is not a movement-parity assertion.

## Old decoder and invalid values

1.8.9 `Block.deserialize` extracts block ID with `blockdata & 4095` and metadata with `blockdata >> 12 & 15` (`block/Block.java:129–135`). The 80-row domain therefore covers the full serialized metadata nibble for the five registered IDs.

- ID 1: `StoneBlock.getStateFromMetadata` selects `Variant.byId(metadata)`; IDs 0–6 are stone, granite, polished granite, diorite, polished diorite, andesite, polished andesite. Metadata 7–15 (and direct decoder arguments below 0 or above 6) fall back to stone/id 0 (`StoneBlock.java:53–55,105–124`).
- ID 3: dirt, coarse dirt, and podzol at metadata 0–2; metadata 3–15 and direct decoder values outside 0–2 fall back to dirt/id 0. The old default also has `snowy=false` (`DirtBlock.java:57–59,119–134`).
- ID 5: oak, spruce, birch, jungle, acacia, and dark oak planks at metadata 0–5; metadata 6–15 and direct decoder values outside 0–5 fall back to oak/id 0 (`PlanksBlock.java:36–38,92–111`).
- ID 12: sand at 0, red sand at 1; metadata 2–15 and direct decoder values outside 0–1 fall back to sand/id 0 (`SandBlock.java:38–40,81–100`).
- ID 17: type is `PlanksBlock.Variant.byId((metadata & 3) % 4)` and axis is Y for `(metadata & 12)==0`, X for 4, Z for 8, otherwise NONE (`LogBlock.java:59–75`). All nibble inputs are accepted; values outside the nibble domain are masked and alias by their low four bits if this method is called directly.

Invalid enum metadata is deliberately kept distinct from invalid block IDs and from malformed current serialized states. This proof does not classify unknown old registry IDs. In 26.2 `BlockState.CODEC` is registry-dispatched by `Name`; an unregistered name or a supplied invalid property value is not one of these valid row outputs and is not assigned a fallback here.

## DataFixer route

The 26.2 `DataFixers` sequence registers `ChunkPalettedStorageFix` at 1451.1 (`DataFixers.java:534`); the section upgrade computes `(addBlocks << 12) | ((blocks[idx] & 255) << 4) | data[idx]` and passes that index to `BlockStateData.getTag` (`ChunkPalettedStorageFix.java:735–750`). `BlockStateData.register` stores the flattened entry and first per-block default, `finalizeMaps` fills missing metadata slots from that block default, and `getTag` performs indexed lookup (`BlockStateData.java:686–705,737`). The exact row source lines and lookup modes appear in the tuples file.

IDs 1, 3, 5 and 12 use explicit entries for their valid enum variants and block-default fill for fallback metadata. Their DataFixer names/properties exactly match the old decoded meaning after converting variant values to separate current block names. ID 17 uses explicit entries for all 16 metadata values. Its axes Y/X/Z are preserved for metadata 0–11. Metadata 12–15 map `axis=none` to bark names with no properties; the ordered 1510 rename at `DataFixers.java:726` changes those to the corresponding `*_wood` names and leaves properties absent. `LeavesFix` is registered at 1496 (`:716`), recognizes log/bark palette entries and uses them as neighborhood bases, but its section code only changes leaf entries/distances; it does not rewrite log palette states (`LeavesFix.java:57–76,244–263,275–315`). This row result is exact; complete neighboring leaf output remains contextual and outside the tuple claim.

## Actual current registration and codec

The 26.2 `Blocks` static registrations bind each final name to an actual registered block (`Blocks.java`: stone family 56–70; dirt family 87–93; plank family 98–140; sand/red sand 324–345; log family 399–412; wood family 478–508). These names match the `BlockItemIds` keys referenced by those expressions. The plain stone, plank, sand, and dirt blocks use the base `Block`, whose state definition has no properties and whose constructor registers the empty default (`Block.java:238–243,531–543`). `PODZOL` uses `SnowyBlock`, whose default is `snowy=false` (`SnowyBlock.java` constructor). Logs and wood use `RotatedPillarBlock`; its state has the axis property and default Y (`RotatedPillarBlock.java` constructor/state definition; `BlockStateProperties.java` axis declaration).

`BlockState.CODEC` resolves the registered block and uses `Block::defaultBlockState` (`BlockState.java:9`). `StateHolder.codec` decodes a supplied `Properties` map through that block's property codec, and absent `Properties` selects its registered default state (`StateHolder.java:145–156`). Thus every explicit axis tuple and podzol's `snowy=false` is validated against the actual property definition; name-only rows select the corresponding registered default. For the renamed bark rows, name-only `*_wood` resolves to `RotatedPillarBlock` default `axis=y`.

## Bounded exact tuples

The JSONL contains one row per `(old ID, metadata)` and embeds the exact output and lookup/rename trace. Distinct tuple patterns, with metadata ranges explicit:

| Old ID | Metadata | Old decoded tuple | 26.2 serialized state → codec state |
|---:|---|---|---|
| 1 | 0 stone; 1 granite; 2 smooth_granite; 3 diorite; 4 smooth_diorite; 5 andesite; 6 smooth_andesite; 7–15 stone | `minecraft:stone{variant=<listed>}` | Same names as distinct registered blocks, no properties |
| 3 | 0 dirt; 1 coarse_dirt; 2 podzol; 3–15 dirt | `minecraft:dirt{variant=<listed>,snowy=false}` | `minecraft:dirt`; `minecraft:coarse_dirt`; `minecraft:podzol{snowy=false}`; then dirt; default property tuples `{}`, `{}`, `{snowy=false}`, `{}` |
| 5 | 0 oak; 1 spruce; 2 birch; 3 jungle; 4 acacia; 5 dark_oak; 6–15 oak | `minecraft:planks{variant=<listed>}` | Corresponding registered `minecraft:<wood>_planks`, no properties |
| 12 | 0 sand; 1 red_sand; 2–15 sand | `minecraft:sand{variant=<listed>}` | `minecraft:sand`; `minecraft:red_sand`; then sand, no properties |
| 17 | 0–3 = oak/spruce/birch/jungle with axis Y; 4–7 same order with X; 8–11 same order with Z; 12–15 same order with NONE | `minecraft:log{variant=<wood>,axis=<axis>}` | 0–11: `minecraft:<wood>_log{axis=<axis>}`. 12–15: `minecraft:<wood>_wood{axis=y}` after bark rename and absent-property defaulting |

For all IDs 1, 3, 5 and 12, the 26.2 block-default-filled metadata rows correspond to the old decoder's id-0 fallback. For logs, the explicit rows encode every axis/type outcome. Machine-readable row order is ID ascending then metadata ascending.

## Evidence identity and open gates

Older source identity is 1.8.9 / Ornithe Feather; newer source identity is 26.2 / unobfuscated. Canonical source files are read-only under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. SHA-256 values below bind exact class evidence and the previously accepted source-only partition inputs. The existing 1.8.9/26.2 current-name projection is candidate data; acceptance here comes from joining each row back to the old decoder and from checking the full fixer/default/registry/codec chain above.

`source-evidence.sha256` binds all source and accepted-input files used here plus the immutable tuple export. The export SHA-256 is `ADD63AAB0124009C356F489151D44E0C49842FA0976D2BABAA4B71EA034352C1` (80 rows, ID then metadata order).

Open: this does not prove old/current collision shape or movement-response parity, does not evaluate unsupported old block IDs, and does not claim a complete contextual result for neighboring leaves in a whole chunk. No decompiler, test, build, or game was run. No product, wiki, backlog, or history source was read.
