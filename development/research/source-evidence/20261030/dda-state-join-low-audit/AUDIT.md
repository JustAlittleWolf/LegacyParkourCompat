# Independent audit: 1.8.9 block metadata to 26.2 state join

## Immutable inputs

- Candidate finding: `.native-source/20261030/dda-state-join-low/FINDING.md` — SHA-256 `D8415BEBFB0059DE2C9D2EB0F20D9037087DE1DED06B72E1A6541B94C6C51091`.
- Tuple export: `.native-source/20261030/dda-state-join-low/state-tuples.jsonl` — SHA-256 `ADD63AAB0124009C356F489151D44E0C49842FA0976D2BABAA4B71EA034352C1`.
- Source/input manifest: `.native-source/20261030/dda-state-join-low/source-evidence.sha256` — SHA-256 `7335318536642360885801A17B755F96598FD6EA45305B691E406286AA7E30D2`.
- All 31 entries in the source/input manifest matched their listed SHA-256 at audit time, including all canonical source files under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

## Verdict

Accept the candidate's exact bounded serialized-state join for old block IDs 1, 3, 5, 12 and 17, metadata 0–15 (80 tuples), subject to the gates below. I found no tuple, lookup, rename, registry, default-state or codec-selection defect in this bounded claim. Static JSON parsing confirmed 80 unique ID/metadata rows, 16 rows per ID, in ascending ID and metadata order; independent expected-variant and log-axis calculations matched all 80 legacy decoder tuples.

The old metadata decoder evidence is `1.8.9/ornithe-feather`: `Block.deserialize` masks ID and metadata at `Block.java:131–135`; the variant decoders are at `StoneBlock.java:53–55,105–111`, `DirtBlock.java:57–59,119–125`, `PlanksBlock.java:36–38,92–98`, and `SandBlock.java:38–40,81–87`; `LogBlock.java:59–75` masks type and axis independently. Their enum declarations establish the accepted variant IDs, and `DirtBlock.java:22–25` establishes its `snowy=false` decoder default. The invalid metadata fallbacks stated in the finding are correct for the four `byId` decoders; log metadata uses masks and has no invalid enum fallback within the 0–15 domain.

The 26.2 join evidence is `26.2/unobfuscated`: `ChunkPalettedStorageFix.java:732–750` assembles the flattened `(blockId << 4) | metadata` lookup. `BlockStateData.java:686–705,737–744,9553–9572` establishes explicit registration, per-block default fill, indexed lookup, and the static bootstrap sequence that invokes `finalizeMaps`; its registrations at `748–754,756–780,858–882` match all candidate rows, including the `axis=none` bark rows. `DataFixers.java:531–535,715–716,725–729` establishes the chunk fixer, intervening LeavesFix and later rename order. `LeavesFix.java:57–76,247–263,274–309` uses bark/log palette IDs as leaf-distance bases and only rewrites leaf states. `BlockRenameFix.java:48–50` changes the block-state name while retaining other tag fields; `EntityTheRenameningFix.java:22–37` maps the four bark names to the corresponding wood names.

The actual current registry and codec support the candidate's final-state interpretation: `Blocks.java:56–80,87–93,98–145,324–343,399–412,478–515` registers the relevant names (with `BlockItemIds` keys); plain base `Block` creates the empty default state (`Block.java:238–243,531–543`), `SnowyBlock` sets `snowy=false` (`SnowyBlock.java:26–29`), and `RotatedPillarBlock` declares axis and sets default Y (`RotatedPillarBlock.java:21–24,48–51`). `BlockState.CODEC` dispatches by the actual block registry and supplies each block's default (`BlockState.java:8–10`); `StateHolder.codec` selects that default when `Properties` is absent (`StateHolder.java:145–157`). Therefore ID 17 metadata 12–15 serializes as bark with no properties, is renamed to `*_wood` with properties still absent, and the current `RotatedPillarBlock` codec state has `axis=y`, while the old decoded tuple had `axis=none`.

## Gates and scope

- This verdict covers the immutable 80-row state join only. It does not prove collision-shape or player-movement parity, chunk runtime execution, or behavior for other old block IDs.
- It does not claim the complete contextual output for neighboring leaves in a whole chunk; the inspected LeavesFix behavior only closes the claim that the log/bark palette entries act as neighbor bases and are not themselves rewritten there.
- No implementation, wiki, product history, tests, build, game, or decompilation was used. The codec conclusion is a source-derived static conclusion; no codec was executed.
