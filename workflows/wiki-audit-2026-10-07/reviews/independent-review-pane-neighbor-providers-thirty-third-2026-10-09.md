# Independent source review: pane-provider slice 33, Ladder

## Scope and verdict

Reviewed `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-thirty-third.md` at frozen memo commit `9a9c3e695176e3e6f57bdd375601ee93f45121a9`, parent `57eb11151f08bde4adaab46d16b4d8e5c11b058c`; Git blob `93c81581d0bc6f4977c469598cff8d8857eb7e61`, raw SHA-256 `acc8c089549de696118276b3e74e493e470d0f483af36f8558f135bf9823a3ba`.

**Verdict: ACCEPT, bounded to Ladder ID 65’s registered default-state predicate and the one-east-neighbor pane-mask witness.**

## Source bindings and evidence

Both ready records identify `feather-r1-2026-10-07`, report `ready`, and match the memo’s source-manifest hashes: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. I recomputed all seven cited source-file hashes; each matches its source-manifest entry. The verification records report zero source differences and no raw-input differences. Original derived-JAR equivalence remains unproven; regenerated JARs do not establish that the recorded differences are metadata-only.

The 1.8.9 registry binds ladder ID 65 to `new LadderBlock()`. Its default facing is north; `LadderBlock.isSolidRender()` returns false, and `Block` caches that result in `opaqueCube`, which `isOpaque()` returns. The 1.9.4 registry binds the same ID and class; the default facing is north, and `LadderBlock.isCube(state)` returns false through the resolved-state dispatch used by the pane predicate. Both pane resolvers evaluate the four cardinal neighbors. Ladder is not in the pane’s explicit same-pane/glass allowlist. An east-only Ladder neighbor therefore yields no E arm in either version; no predicate or mask delta.

| Version | Cited source SHA-256 |
|---|---|
| 1.8.9 `Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `LadderBlock.java` | `c13b123f8fd7427d7f646da3b789eb97d4e54aa2fe2860f66cf4fd1302865106` |
| 1.9.4 `Block.java` | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `LadderBlock.java` | `f411d494a4f8c87b2b23d182527713d3b0c2fad4da329b82b84ea301a9d65fcb` |
| 1.9.4 `StateDefinition.java` | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

This does not assess ladder support, placement, climbing, shape/collision behavior, or movement, and does not close the provider census or reachable-mask inventory.
