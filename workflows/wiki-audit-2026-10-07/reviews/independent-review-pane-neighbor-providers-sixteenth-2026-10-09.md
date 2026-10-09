# Independent source review: pane-provider slice 16 (IDs 13–15)

**Verdict: ACCEPTED, bounded to gravel and the two ore registrations and their one-east-neighbor masks.** Review date: 2026-10-09.

## Immutable input binding

- Frozen candidate tree: `8f97dda72e0775c1e39990b087433b2e552d5564`.
- Memo: `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-sixteenth.md`; introduced at `816eb44a5fe788819653871dfa9d5d75803f2f35`; Git blob `cdf06c2e971a51c8120c9f813c81b3295d91febb`; raw SHA-256 `c2c658b1e1df9d68de24c93946e4300adb6637bb7edfed199589edb952fa04c8`.
- Read-only Feather roots: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`; both readiness records say `ready`.
- Source-manifest SHA-256: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- Artifact-manifest SHA-256: 1.8.9 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`; 1.9.4 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.
- Every source-file hash cited by this memo was recomputed and matches its ready-tree source-manifest entry. Verification records report zero source differences and no raw-input artifact differences. Original derived-JAR equivalence remains unproven: recorded originals `e36a366fd30d0adda0a803a4956782bab1f67cd644e396d4db6a1cd885548c09` / `0df10c862f7fd4848d08597a76c0cf02cec5a9d9886975a57230a033cfb86b3a` differ from regenerated snapshots `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` / `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`; the evidence does not establish metadata-only differences.

## Source verdict

The pane's old predicate is `isOpaque()` over the base-constructor cache; the new predicate is the neighbor's default-state `isCube()` delegated through `StateDefinition`. Both paths check N/S/W/E. Gravel inherits base true predicates through `FallingBlock`; `OreBlock` has no provider predicate override and uses `Material.STONE`.

| Registration | 1.8.9 → 1.9.4 predicate | East-only mask | Evidence |
|---|---|---|---|
| `gravel`, ID 13 | true → true | E in both | Both registries construct `GravelBlock` (`Block.java:856` / `748`); it extends `FallingBlock` and has no predicate override (`GravelBlock.java:9–17`; `FallingBlock.java:11–21`). |
| `gold_ore`, ID 14 | true → true | E in both | Both registries construct `OreBlock` (`Block.java:857` / `749`); its default constructor is stone material and it has no predicate override (`OreBlock.java:17–24`). |
| `iron_ore`, ID 15 | true → true | E in both | Both registries construct the same `OreBlock` class (`Block.java:858` / `750`) with the same default material and inherited predicates. |

The memo's predicate and east-bit dispositions are supported. This review does not classify falling-block behavior, ore drops, or any lifecycle behavior. It makes no non-player behavior claim, does not close the provider census or all reachable masks, and does not claim runtime validation or original derived-JAR equivalence.
