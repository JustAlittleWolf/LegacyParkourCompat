# Independent source review: pane-provider slice 15 (IDs 10–12)

**Verdict: ACCEPTED, bounded to flowing lava, lava, and sand predicates and the one-east-neighbor masks.** Review date: 2026-10-09.

## Immutable input binding

- Frozen candidate tree: `8f97dda72e0775c1e39990b087433b2e552d5564`.
- Memo: `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-fifteenth.md`; introduced at `79ce93ef5a0c3a6968d37843d679a1196317938c`; Git blob `2de4cfde4c26231036bcadba7e7deed6ac02e653`; raw SHA-256 `12ddf90a60e12fd2abd4e3ec5a14c06b2ee8bd1f0dc1e54480b8534c35d4b540`.
- Read-only Feather roots: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`; both readiness records say `ready`.
- Source-manifest SHA-256: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- Artifact-manifest SHA-256: 1.8.9 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`; 1.9.4 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.
- Every source-file hash cited by this memo was recomputed and matches its ready-tree source-manifest entry. Verification records report zero source differences and no raw-input artifact differences. Original derived-JAR equivalence remains unproven: recorded originals `e36a366fd30d0adda0a803a4956782bab1f67cd644e396d4db6a1cd885548c09` / `0df10c862f7fd4848d08597a76c0cf02cec5a9d9886975a57230a033cfb86b3a` differ from regenerated snapshots `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` / `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`; the evidence does not establish metadata-only differences.

## Source verdict

The old path reads cached `Block.isOpaque()` and the new path reads the provider default state's `isCube()` via `StateDefinition`. Both pane resolvers inspect all four cardinal neighbors. Flowing and source liquids inherit the `LiquidBlock` predicate overrides; `LEVEL=0` is the constructor default. Sand inherits the base true predicates through `FallingBlock`.

| Registration | 1.8.9 → 1.9.4 predicate | East-only mask | Evidence |
|---|---|---|---|
| `flowing_lava`, ID 10 | false → false | No E in either | Both registries construct `FlowingLiquidBlock(Material.LAVA)` (`Block.java:853` / `745`); the `LiquidBlock` parent has `LEVEL=0` and constant false predicates (`LiquidBlock.java:21–29, 58–66` / `24–31, 66–74`). |
| `lava`, ID 11 | false → false | No E in either | Both registries construct `LiquidSourceBlock(Material.LAVA)` (`Block.java:854` / `746`), with default `LEVEL=0` and the same parent predicates (`LiquidSourceBlock.java:10–17`; `LiquidBlock.java` ranges above). |
| `sand`, ID 12 | true → true | E in both | Both registries construct `SandBlock` (`Block.java:855` / `747`); it defaults to `VARIANT=SAND` and extends `FallingBlock` (`SandBlock.java:13–18`), which has no predicate overrides. Base predicates return true. |

The memo's predicate and east-bit dispositions are supported. Liquid spreading and falling-block lifecycle are outside this review. It makes no non-player behavior claim, does not close the provider census or all reachable masks, and does not claim runtime validation or original derived-JAR equivalence.
