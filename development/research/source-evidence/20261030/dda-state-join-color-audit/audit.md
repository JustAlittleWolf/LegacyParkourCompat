# Independent source-only audit: colored block state join

## Verdict

Accepted with the finding's stated boundary. The 80 old `(block ID, metadata)` rows identify the matching current color block registry names. This verifies serialized palette naming and the qualified default-state behavior described below; it does not establish collision shape or movement equivalence.

## Accepted row and property scope

- Exact pair: 1.8.9 (`ornithe-feather`, Feather `feather-gen2-1.8.9+build.2`) to 26.2 (`unobfuscated`).
- Accepted old rows: IDs 35, 95, 159, 160, and 171, each with metadata 0–15: 80 distinct pairs.
- For all rows, the color name order is white, orange, magenta, light blue, yellow, lime, pink, gray, silver/light gray, cyan, purple, blue, brown, green, red, black. In 1.8.9 the decoder calls `DyeColor.byId(metadata)`; `DyeColor` assigns IDs 0–15 in this order. In 26.2, `DyeColor` IDs and `ColorCollection.VALUES` use the same order, with `silver` renamed `light_gray`.
- The 26.2 `BlockStateData` registrations bind each legacy source name to the corresponding color target name: `wool` → `<color>_wool`; `stained_glass` → `<color>_stained_glass`; `stained_hardened_clay` → `<color>_terracotta`; `stained_glass_pane` → `<color>_stained_glass_pane`; `carpet` → `<color>_carpet`. The sequential registration indices are respectively 560–575, 1520–1535, 2544–2559, 2560–2575, and 2736–2751, equal to `(old ID << 4) + metadata`.
- The current registry uses the matching `BlockItemIds` color groups and `ColorCollection.registerBlocks` ordering. Four current state tuples are qualified as empty `Properties` (`{}`): wool, stained glass, dyed terracotta, and carpet. Their color identity is carried by the current block key/registry object; it is not a state `color` property. This is a current codec/default-state qualification, not a claim of full historical state or behavior equivalence.
- Pane acceptance stops at the color-qualified block key for all 16 metadata values. No exact full pane tuple is accepted. The old ID 160 is in `ChunkPalettedStorageFix.MappingConstants.VIRTUAL`; the converter marks affected entries for neighbor/context repair and the pane `BlockStateData` entries include connection-mask variants. Current pane defaults are north/east/south/west `false` and `waterlogged=false`, while 1.8.9 has no waterlogged property. The fixed current default is not evidence of the converted pane's full tuple, and no waterlogged equivalence is established.

## Evidence and integrity

- All 24 source-file SHA-256 entries in the assigned `source-hashes.json` match the read-only canonical files under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.
- Both exact source publication identities were checked in provenance, ready markers, success excerpts and source manifests. The 1.8.9 provenance identifies exact 1.8.9, `ornithe-feather`, and `feather-gen2-1.8.9+build.2`; 26.2 identifies exact 26.2, `unobfuscated`. Both source-manifest hashes match their ready markers.
- The prior projection hash is `C7A8B04869425690B2D6BD200C9630752FC1A58C34526B6A5F51898F64B0DC02`; the prior manifest hash is `7D60A12E39C718ABD390116F723A7935B1C7A230E6E1D6A0493BE8524558F77E`. Both match. Every accepted input entry in that prior manifest also matches its recorded hash.
- No source hash, provenance, or bounded semantic defect found. No additional gate is needed for the stated color-name/default-state scope. Pane contextual tuple, old-to-current shape/provider equivalence, and runtime movement parity remain open and outside this verdict.

## Main source anchors

- 1.8.9 `Block.java`: packed legacy metadata decode at 131–134; registry IDs/names at 888, 1031, 1175–1180, and 1195.
- 1.8.9 `ColoredBlock.java`: white default and `byId(metadata)` decoder at 14–20, 41–43; `StainedGlassBlock.java` at 18–23, 65–67; `StainedGlassPaneBlock.java` at 17–23, 49–51; `CarpetBlock.java` at 18–24, 102–105.
- 1.8.9 `DyeColor.java`: ID table and fallback lookup at 8–23, 67–88.
- 26.2 `BlockStateData.java`: target rows at 1196–1211, 4092–4107, 7087–7102, 7106 onward for pane masks, and 7663–7678.
- 26.2 `ChunkPalettedStorageFix.java`: VIRTUAL contains ID 160 at 660; ID lookup, VIRTUAL neighbor/section handling, and `setBlock(...BlockStateData.getTag(id))` at 732–751.
- 26.2 `DataFixers.java`: structure-template and chunk palette fixes registered at 530 and 534.
- 26.2 `ColorCollection.java`: ordered color collection at 18–36 and name prefixing at 65–66; registry groups in `Blocks.java` at 833–843, 2150–2164, 2953–2968, and 3062–3067; group identifiers in `BlockItemIds.java` at 751–755.
- 26.2 `BlockState.java` and `StateHolder.java`: block codec uses `Block::defaultBlockState` and absent `Properties` falls back to that state at 9 and 145–157; pane default and waterlogged property at `StainedGlassPaneBlock.java` 19–24, with the property declared by `IronBarsBlock`.
