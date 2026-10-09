# Independent source review: pane-provider slice 7 (IDs 184–186)

**Verdict: ACCEPTED, bounded to the three fence-gate registration-level predicate results and the one-east-neighbor masks below.** Review date: 2026-10-09.

## Immutable input identity

- Reviewed candidate commit: `78244124582c63dd8fa1f26425e28041eecc786d`.
- Memo: `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-seventh.md`; Git blob `686a50da5873b52762cff7d9ee616c90e391531c`; raw-file SHA-256 `a3d642013aefd50d387f762b9eaf807c8baf4aba6d28e9e77efb4d68e3afc839`.
- Exact read-only Feather roots: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready markers say `ready`.
- Source-manifest SHA-256: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- Artifact-manifest SHA-256: 1.8.9 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`; 1.9.4 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.
- The memo's cited Java-file hashes were recomputed from these roots and match their source-manifest entries. Verification records say source and raw input artifacts match; original derived JAR equivalence remains unproven. Recorded original mapped-JAR hashes (`e36a366f…`, `0df10c86…`) differ from regenerated snapshots (`5c4cff3e…`, `fbcf5079…`); the evidence does not establish metadata-only differences.

## Source check

The old pane path calls `Block.isOpaque()` (`PaneBlock.java:134–140`), which reads `opaqueCube` cached from virtual `isSolidRender()` during the base constructor (`Block.java:154–156, 198–207`). The new path tests the neighboring block's default state's `isCube()` (`PaneBlock.java:133–140`); state dispatch reaches `Block.isCube(state)` (`StateDefinition.java:268–270`). Both pane methods check north, south, west, and east. For a pane with only the listed gate due east and air in the other cardinal positions, a false provider result yields no east connection bit.

| Registration and exact dependency | 1.8.9 result | 1.9.4 result | East-only disposition |
|---|---:|---:|---|
| `birch_fence_gate`, ID 184; both registries construct `FenceGateBlock(BIRCH)` (`Block.java:1230–1234` / `1153–1157`). The gate defaults `OPEN`, `POWERED`, `IN_WALL` to false (`FenceGateBlock.java:21–24` / `30–34`); old and new `isSolidRender` and `isCube` methods return false independent of variant/state (`73–81` / `90–98`). | false | false | No east bit in either version. |
| `jungle_fence_gate`, ID 185; both registries construct `FenceGateBlock(JUNGLE)` (`Block.java:1235–1239` / `1158–1162`). Same defaults and state-independent false predicates. | false | false | No east bit in either version. |
| `dark_oak_fence_gate`, ID 186; both registries construct `FenceGateBlock(DARK_OAK)` (`Block.java:1240–1244` / `1163–1167`). Same defaults and state-independent false predicates. | false | false | No east bit in either version. |

The registration, default-state, dispatch, and east-mask conclusions are supported for these three entries. This review does not classify gate collision geometry or gate support/update lifecycles. It makes no lifecycle or non-player behavior claim, does not close the remaining provider census or reachable-mask set, and does not assert runtime validation or original derived-JAR equivalence.
