# Independent source review: pane-provider slice 10 (IDs 193–195)

**Verdict: ACCEPTED, bounded to these three shared door registrations and the one-east-neighbor connection bit.** Review date: 2026-10-09.

## Immutable input binding

- Frozen candidate tree: `8f97dda72e0775c1e39990b087433b2e552d5564`.
- Memo: `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-tenth.md`; introduced at `7cb49d73964ff62e5987b607bd73371a43e29c7e`; Git blob `2fa0f65c24b579d70fc7e76a9019ddf3101ab574`; raw SHA-256 `6c20cdd2498e247a832314fa011fea868a4f436393f284dfb7d33fecdf8cf410`.
- Read-only Feather roots: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`; both readiness records say `ready`.
- Source-manifest SHA-256: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- Artifact-manifest SHA-256: 1.8.9 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`; 1.9.4 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.
- Every source-file hash cited by this memo was recomputed and matches its ready-tree source-manifest entry. Verification records report zero source differences and no raw-input artifact differences. Original derived-JAR equivalence remains unproven: recorded originals `e36a366fd30d0adda0a803a4956782bab1f67cd644e396d4db6a1cd885548c09` / `0df10c862f7fd4848d08597a76c0cf02cec5a9d9886975a57230a033cfb86b3a` differ from regenerated snapshots `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` / `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`; the evidence does not establish metadata-only differences.

## Source verdict

The pane path changes from old `Block.isOpaque()` backed by constructor-cached `opaqueCube` (`1.8.9 Block.java:154–156, 198–207; PaneBlock.java:134–140`) to the default state's `isCube()` (`1.9.4 PaneBlock.java:133–140`, delegated at `StateDefinition.java:268–270`). Both resolvers examine all four cardinal directions. If each door is the only non-air cardinal neighbor east of a pane, its false predicate yields no E bit; the door is not one of the pane/glass exceptions.

| Registration | 1.8.9 → 1.9.4 predicate | East-only mask | Evidence |
|---|---|---|---|
| `spruce_door`, ID 193 | false → false | No E in either | Both registries construct `DoorBlock(Material.WOOD)` (`Block.java:1295–1297` / `1218–1220`). Default is NORTH, closed, LEFT hinge, unpowered, LOWER half; old/new solid-render and cube methods return false (`DoorBlock.java:31–42, 49–62` / `38–49, 75–88`). |
| `birch_door`, ID 194 | false → false | No E in either | Same registration class, default state, and constant predicates in both releases (`Block.java:1295–1297` / `1218–1220`; `DoorBlock.java` ranges above). |
| `jungle_door`, ID 195 | false → false | No E in either | Same registration class, default state, and constant predicates in both releases (`Block.java:1295–1297` / `1218–1220`; `DoorBlock.java` ranges above). |

The memo's predicate and east-bit dispositions are supported. Door collision geometry, placement, and update/support lifecycle are not reviewed here. This review makes no non-player behavior claim, does not close the census or full reachable-mask set, and does not claim runtime validation or original derived-JAR equivalence.
