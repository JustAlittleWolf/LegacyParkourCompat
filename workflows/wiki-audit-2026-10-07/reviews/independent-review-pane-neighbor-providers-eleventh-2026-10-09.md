# Independent source review: pane-provider slice 11 (IDs 196–197)

**Verdict: ACCEPTED for the two shared door registrations, bounded to their predicates and one-east-neighbor masks.** Review date: 2026-10-09.

## Immutable input binding

- Frozen candidate tree: `8f97dda72e0775c1e39990b087433b2e552d5564`.
- Memo: `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-eleventh.md`; introduced at `679ee7a700a8b4075bd026731fa60487febbd73d`; Git blob `89581ffadd3826318d1f709e4e3414aad218f967`; raw SHA-256 `3caea55916ef4df40ddf2480ba387b439e8a0fabcd3f1e279c480d39ed936d96`.
- Read-only Feather roots: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`; both readiness records say `ready`.
- Source-manifest SHA-256: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- Artifact-manifest SHA-256: 1.8.9 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`; 1.9.4 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.
- Every source-file hash cited by this memo was recomputed and matches its ready-tree source-manifest entry. Verification records report zero source differences and no raw-input artifact differences. Original derived-JAR equivalence remains unproven: recorded originals `e36a366fd30d0adda0a803a4956782bab1f67cd644e396d4db6a1cd885548c09` / `0df10c862f7fd4848d08597a76c0cf02cec5a9d9886975a57230a033cfb86b3a` differ from regenerated snapshots `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` / `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`; the evidence does not establish metadata-only differences.

## Source verdict

The two registered providers are `DoorBlock(Material.WOOD)` at IDs 196 and 197 in both registries (`Block.java:1298–1299` / `1221–1222`). Old pane resolution calls `Block.isOpaque()`, which reads the cache populated from virtual `isSolidRender()` in the base constructor; the new path tests `defaultState().isCube()` and dispatches through `StateDefinition` to `Block.isCube(state)`. Both door predicates return false regardless of the defaults NORTH/closed/LEFT/unpowered/LOWER (`DoorBlock.java:31–42, 49–62` / `38–49, 75–88`). Thus a door alone east of a pane yields no E bit in either release.

`end_rod` ID 198 exists in the 1.9.4 registry but has no 1.8.9 counterpart, so it is properly excluded from this shared-version comparison. This review accepts only IDs 196–197 and does not endorse the memo's broader registry-name census as full provider closure.

The bounded predicate and east-bit dispositions are supported. Door collision geometry and lifecycle behavior are outside scope. This review makes no non-player behavior claim, does not close the remaining registry census or reachable-mask set, and does not claim runtime validation or original derived-JAR equivalence.
