# Independent review: 1.8.9 to 26.2 ordinary block-state ore join

Verdict: the 240-pair serialized-state join is source-supported. Its bounded claims hold; it does not establish collision or runtime behavior.

Reviewed immutable inputs:

- Candidate: `.native-source/20261030/dda-state-join-ores/FINDING.md`, SHA-256 `F1B21E9D6AEDB1C7FD7CF613276D03221F6C00D80CCB6B98922C9A36C8C1A735`.
- Tuple table: `.native-source/20261030/dda-state-join-ores/state-tuples.json`, SHA-256 `C6955E535E085CE80777B74F231DCC36BCB2F54E6DAD4A7A660E7875AF7B42C0`.
- Source evidence manifest: `.native-source/20261030/dda-state-join-ores/source-evidence.sha256`, SHA-256 `B57E34D8354641B5A630939E5985C506E19E426EB9572649B5D872654A100B28`.

The exact source identities are supported by canonical provenance manifests at `decompiled_minecraft/1.8.9/ornithe-feather.provenance.json` (version 1.8.9, mapping artifact `feather-gen2-1.8.9+build.2`) and `decompiled_minecraft/26.2/unobfuscated.provenance.json` (version 26.2, unobfuscated). Every canonical Java file hash listed by the candidate evidence manifest was independently recomputed and matched.

Coverage checked: the tuple table has exactly 15 rows and 240 represented `(legacy ID, metadata)` inputs: IDs 14, 15, 16, 20, 21, 49, 56, 73, 74, 79, 80, 82, 87, 88, 89, each with metadata 0–15. The older decoder at `Block.deserialize` extracts `(data & 4095)` and `(data >> 12 & 15)`, then dispatches to the registered block's metadata decoder; selected registrations use the base default-state decoder. The two redstone IDs are separate registered instances with no metadata properties. The selected rows in `BlockStateData` resolve to the asserted aliases and full target tuples; metadata zero is explicit and the rest resolve by the block-default fill. ID 80's alias resolves directly to the snow-block target. The section conversion constructs the combined old state ID and calls `BlockStateData.getTag`. The selected IDs are absent from both the chunk `FIX` and `VIRTUAL` sets. The remaining ordered rename/state-fix list for these exact output tuples is empty; the targeted later block rename maps do not match these names.

Current registry and codec checks confirm that all target names are registered; propertyless targets decode via their default state. Both redstone targets use the registered `redstone_ore` state with the explicit `lit` property, false for ID 73 and true for ID 74. The report properly keeps redstone identity semantics distinct from current same-block property representation. Tuple/name-only/context boundaries are correctly stated for this ordinary chunk-section route.

No unresolved source or tuple gate within the assigned 240 inputs. This review does not audit the reused 1.8.9 decoder/table joins, other conversion routes, collision shape providers, block behavior, or movement parity.
