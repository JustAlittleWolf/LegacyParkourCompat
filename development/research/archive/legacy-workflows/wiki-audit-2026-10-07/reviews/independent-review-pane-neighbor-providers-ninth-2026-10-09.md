# Independent source review: pane-provider slice 9 (IDs 190–192)

**Verdict: ACCEPTED, bounded to these three shared fence registrations and the one-east-neighbor connection bit.** Review date: 2026-10-09.

## Immutable input binding

- Frozen candidate tree: `8f97dda72e0775c1e39990b087433b2e552d5564`.
- Memo: `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-ninth.md`; introduced at `e5d1915a32e30ebc27f0f8cb872889e592d90395`; Git blob `b0b9126edb3ffbd29b42fa6e061bbf2fe8c0631e`; raw SHA-256 `f684aa4d5303ae207889f0abffc8d703ae4cc1d71952f3f08bb0487745b6bdcc`.
- Read-only Feather roots: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../ready/1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`; both readiness records say `ready`.
- Source-manifest SHA-256: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- Artifact-manifest SHA-256: 1.8.9 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`; 1.9.4 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.
- Every source-file hash cited by this memo was recomputed and matches its ready-tree source-manifest entry. Verification records report zero source differences and no raw-input artifact differences. Original derived-JAR equivalence remains unproven: recorded originals `e36a366fd30d0adda0a803a4956782bab1f67cd644e396d4db6a1cd885548c09` / `0df10c862f7fd4848d08597a76c0cf02cec5a9d9886975a57230a033cfb86b3a` differ from regenerated snapshots `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` / `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`; the evidence does not establish metadata-only differences.

## Source verdict

The 1.8.9 pane resolver checks N/S/W/E and calls `Block.isOpaque()` (`PaneBlock.java:100–108, 134–140`), which reads the constructor-cached `opaqueCube` (`Block.java:154–156, 198–207`). The 1.9.4 resolver writes N/S/W/E from `block.defaultState().isCube()` (`PaneBlock.java:105–110, 133–140`); `StateDefinition` delegates that query to `Block.isCube(state)` (`StateDefinition.java:268–270`). With only the listed provider east and air at the other three cardinal neighbors, the provider predicate is the east bit; none of these providers matches the pane/glass special cases.

| Registration | 1.8.9 → 1.9.4 predicate | East-only mask | Evidence |
|---|---|---|---|
| `jungle_fence`, ID 190 | false → false | No E in either | Both registries construct `FenceBlock` with JUNGLE color (`Block.java:1271–1279` / `1194–1202`); its four direction defaults are false and old/new solid-render and cube predicates are constant false (`FenceBlock.java:25–33, 113–121` / `51–55, 105–113`). |
| `dark_oak_fence`, ID 191 | false → false | No E in either | Both registries use the same class and DARK_OAK color (`Block.java:1280–1288` / `1203–1211`); the same default and predicate methods apply. |
| `acacia_fence`, ID 192 | false → false | No E in either | Both registries use the same class and ACACIA color (`Block.java:1289–1294` / `1212–1217`); the same default and predicate methods apply. |

The memo's predicate and east-bit dispositions are supported. It does not classify fence collision geometry or connection/update lifecycles. This review makes no non-player behavior claim, does not close the census or full reachable-mask set, and does not claim runtime validation or original derived-JAR equivalence.
