# Independent source review: pane-provider slice 13 (IDs 4–6)

**Verdict: ACCEPTED, bounded to these three registrations and their one-east-neighbor connection bit.** Review date: 2026-10-09.

## Immutable input binding

- Frozen candidate tree: `8f97dda72e0775c1e39990b087433b2e552d5564`.
- Memo: `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-thirteenth.md`; introduced at `7100fb16981e6a25839f7fd7a4e346a43bb3bbd4`; Git blob `6cfb82c942392538c817435c368b66a7a7c81436`; raw SHA-256 `341c8bd080123c6d0bbfe2a911ac01d43d7aa1719ed8afeec603bb3a78da21e4`.
- Read-only Feather roots: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`; both readiness records say `ready`.
- Source-manifest SHA-256: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- Artifact-manifest SHA-256: 1.8.9 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`; 1.9.4 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.
- Every source-file hash cited by this memo was recomputed and matches its ready-tree source-manifest entry. Verification records report zero source differences and no raw-input artifact differences. Original derived-JAR equivalence remains unproven: recorded originals `e36a366fd30d0adda0a803a4956782bab1f67cd644e396d4db6a1cd885548c09` / `0df10c862f7fd4848d08597a76c0cf02cec5a9d9886975a57230a033cfb86b3a` differ from regenerated snapshots `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` / `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`; the evidence does not establish metadata-only differences.

## Source verdict

The paired pane paths are old cached `Block.isOpaque()` and new default-state `isCube()` dispatch through `StateDefinition`, with all four cardinal neighbors checked. Both registries bind a generic `Block(Material.STONE)` at ID 4, a `PlanksBlock` at ID 5, and `SaplingBlock` at ID 6 (`Block.java:830–839` / `722–731`).

| Provider | State/predicate evidence | 1.8.9 → 1.9.4 | East-only mask |
|---|---|---|---|
| `cobblestone`, ID 4 | Generic stone-material block, no state/predicate override; inherits base true predicates. | true → true | E in both |
| `planks`, ID 5 | `PlanksBlock` default `VARIANT=OAK` (`PlanksBlock.java:14–21`), no predicate override. | true → true | E in both |
| `sapling`, ID 6 | `TYPE=OAK`, `STAGE=0` (`SaplingBlock.java:25–34` / `27–35`); `PlantBlock` explicitly returns false from both old predicates and both state-taking replacements (`PlantBlock.java:66–74` / `74–82`). | false → false | No E in either |

The memo's predicate and mask dispositions are supported. This review does not classify sapling growth, plant support, or lifecycle behavior. It makes no non-player behavior claim, does not close the registry census or all reachable masks, and does not claim runtime validation or original derived-JAR equivalence.
