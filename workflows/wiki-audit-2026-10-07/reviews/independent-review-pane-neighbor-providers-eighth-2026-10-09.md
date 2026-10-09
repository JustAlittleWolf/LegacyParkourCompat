# Independent source review: pane-provider slice 8 (IDs 187–189)

**Verdict: ACCEPTED, bounded to the three registration-level predicate results and the one-east-neighbor masks below.** Review date: 2026-10-09.

## Immutable input identity

- Reviewed candidate commit: `e59bd2deb56e674660fffc6fe5ca45ac6cfd7230`.
- Memo: `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-eighth.md`; Git blob `9164319f0ae08f02392d933165083a3ac29f5634`; raw-file SHA-256 `fa6766926294b4123f432656b91a9b05cac43ec3ebea96aa3e83e267317ea949`.
- Exact read-only Feather roots: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready markers say `ready`.
- Source-manifest SHA-256: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- Artifact-manifest SHA-256: 1.8.9 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`; 1.9.4 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.
- The memo's cited Java-file hashes were recomputed from these roots and match their source-manifest entries. Verification records say source and raw input artifacts match; original derived JAR equivalence remains unproven. Recorded original mapped-JAR hashes (`e36a366f…`, `0df10c86…`) differ from regenerated snapshots (`5c4cff3e…`, `fbcf5079…`); the evidence does not establish metadata-only differences.

## Source check

In 1.8.9 the pane tests `Block.isOpaque()`, backed by the constructor-cached `opaqueCube` (`PaneBlock.java:134–140`; `Block.java:154–156, 198–207`). In 1.9.4 it tests `defaultState().isCube()` (`PaneBlock.java:133–140`), with `StateDefinition` delegating to `Block.isCube(state)` (`StateDefinition.java:268–270`). Both pane resolvers inspect all four cardinal positions. With the registered provider due east and air north, south, and west, the predicate result determines the only east connection bit.

| Registration and exact dependency | 1.8.9 result | 1.9.4 result | East-only disposition |
|---|---:|---:|---|
| `acacia_fence_gate`, ID 187; both registries use `FenceGateBlock(ACACIA)` (`Block.java:1245–1249` / `1168–1172`). Gate defaults `OPEN`, `POWERED`, `IN_WALL` to false (`FenceGateBlock.java:21–24` / `30–34`); old/new solid-render and cube predicates are state-independent false (`73–81` / `90–98`). | false | false | No east bit in either version. |
| `spruce_fence`, ID 188; both registries use `FenceBlock(Material.WOOD, SPRUCE color)` (`Block.java:1250–1258` / `1173–1181`). Default directions are all false; old/new solid-render and cube predicates are constant false (`FenceBlock.java:25–33, 113–121` / `51–55, 105–113`). | false | false | No east bit in either version. |
| `birch_fence`, ID 189; both registries use `FenceBlock(Material.WOOD, BIRCH color)` (`Block.java:1259–1267` / `1182–1190`). Same four false defaults and constant false predicates. | false | false | No east bit in either version. |

The registration, default-state, predicate-dispatch, and east-mask conclusions are supported for these three entries. The memo does not classify fence collision geometry or connection/update lifecycles. This review makes no lifecycle or non-player behavior claim, does not close the provider census or all reachable masks, and does not assert runtime validation or original derived-JAR equivalence.
