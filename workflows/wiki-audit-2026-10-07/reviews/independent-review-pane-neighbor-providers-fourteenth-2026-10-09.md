# Independent source review: pane-provider slice 14 (IDs 7–9)

**Verdict: ACCEPTED, bounded to bedrock, flowing water, and water predicates and their one-east-neighbor masks.** Review date: 2026-10-09.

## Immutable input binding

- Frozen candidate tree: `8f97dda72e0775c1e39990b087433b2e552d5564`.
- Memo: `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-fourteenth.md`; introduced at `0229e5f7099142b2cd483554cebdcdaff6cc1f6c`; Git blob `3a36b03452ad12b10fd4f7ff15f11d69808987f1`; raw SHA-256 `dc35ba93e206027d5f1d39b15e584a6b31b0ceedc3db1ef23554e6754e24e5eb`.
- Read-only Feather roots: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`; both readiness records say `ready`.
- Source-manifest SHA-256: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- Artifact-manifest SHA-256: 1.8.9 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`; 1.9.4 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.
- Every source-file hash cited by this memo was recomputed and matches its ready-tree source-manifest entry. Verification records report zero source differences and no raw-input artifact differences. Original derived-JAR equivalence remains unproven: recorded originals `e36a366fd30d0adda0a803a4956782bab1f67cd644e396d4db6a1cd885548c09` / `0df10c862f7fd4848d08597a76c0cf02cec5a9d9886975a57230a033cfb86b3a` differ from regenerated snapshots `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` / `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`; the evidence does not establish metadata-only differences.

## Source verdict

In 1.8.9 pane checks use `isOpaque()` over `opaqueCube` cached from virtual `isSolidRender()`; `setOpacity(3)` changes the separate opacity field only (`Block.java:154–156, 198–207, 218–220`). In 1.9.4 pane checks use the default state's `isCube()` delegated through `StateDefinition`. Both resolvers inspect N/S/W/E.

| Registration | 1.8.9 → 1.9.4 predicate | East-only mask | Evidence |
|---|---|---|---|
| `bedrock`, ID 7 | true → true | E in both | 1.8.9 registers a generic `Block(Material.STONE)` at ID 7 (`Block.java:840–850`); 1.9.4 registers `BedrockBlock(Material.STONE)` (`Block.java:732–742`), which has no predicate override and inherits base true behavior. |
| `flowing_water`, ID 8 | false → false | No E in either | Both registries use `FlowingLiquidBlock(Material.WATER)` and `setOpacity(3)` (`Block.java:851` / `743`). `LiquidBlock` default `LEVEL=0`; its old solid-render/cube methods and new state-taking equivalents return false (`LiquidBlock.java:21–29, 58–66` / `24–31, 66–74`). |
| `water`, ID 9 | false → false | No E in either | Both use `LiquidSourceBlock(Material.WATER)` with `LEVEL=0`, inheriting the same constant false predicates (`Block.java:852` / `744`; `LiquidSourceBlock.java:10–17`; `LiquidBlock.java` ranges above). |

The memo's predicate and east-bit dispositions are supported. Fluid tick/spread behavior and bedrock lifecycle are outside this review. It makes no non-player behavior claim, does not close the provider census or all reachable masks, and does not claim runtime validation or original derived-JAR equivalence.
