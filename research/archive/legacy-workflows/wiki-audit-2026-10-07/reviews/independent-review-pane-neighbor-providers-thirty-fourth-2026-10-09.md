# Independent source review: pane-provider slice 34, Rail

## Scope and verdict

Reviewed `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-thirty-fourth.md` at frozen memo commit `60f70ded62ebed9eec375a559414213506801bca`, parent `9a9c3e695176e3e6f57bdd375601ee93f45121a9`; Git blob `e941833c39a4544ebb987a100ae6b9eb999e1bed`, raw SHA-256 `5242bcc61f2a9cda949b7095b3f2a771428fb77f5f2a077eff1fc403a8ad2dc1`.

**Verdict: ACCEPT, bounded to Rail ID 66’s registered default-state predicate and the one-east-neighbor pane-mask witness.**

## Source bindings and evidence

Both ready records identify `feather-r1-2026-10-07`, report `ready`, and match the memo’s source-manifest hashes: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. I recomputed all nine cited source-file hashes; each matches its source-manifest entry. The verification records report zero source differences and no raw-input differences. Original derived-JAR equivalence remains unproven; regenerated JARs do not establish that the recorded differences are metadata-only.

Both registries bind Rail ID 66 to `new RailBlock()`. Each `RailBlock` constructor calls `super(false)` and sets its default shape to `NORTH_SOUTH`. In 1.8.9, `AbstractRailBlock.isSolidRender()` returns false, which the base constructor caches as `opaqueCube`; the class also returns false from `isCube()`. In 1.9.4, `AbstractRailBlock.isCube(state)` returns false through the resolved-state dispatch used by the pane predicate. These results do not depend on the rail shape. Both pane resolvers evaluate four cardinal neighbors, and Rail is not allowlisted. An east-only Rail neighbor therefore yields no E arm in either version; no predicate or mask delta.

| Version | Cited source SHA-256 |
|---|---|
| 1.8.9 `Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `RailBlock.java` | `2cb1bd437d0b0893d310d1e371ee3df0075d2a2aafaa1a612010c523bfa339aa` |
| 1.8.9 `AbstractRailBlock.java` | `743e8d1b8acf24aca8e297a2906d78b010960dae21b0be5a4c803aa20038111d` |
| 1.9.4 `Block.java` | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `RailBlock.java` | `62c1c8f2d37fd80234c397b55a3662baafae6f75316853cc95f7ac4d1bda03fc` |
| 1.9.4 `AbstractRailBlock.java` | `96256ad09e5833f44f1da66a1da255ad7e380ab63bdc60f74646f7089e1dabec` |
| 1.9.4 `StateDefinition.java` | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

This does not assess rail placement, powered behavior, collision, or movement, and does not close the provider census or reachable-mask inventory.
