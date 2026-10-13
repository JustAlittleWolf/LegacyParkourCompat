# Pane neighbor-provider slice 34: Rail (1.8.9 to 1.9.4)

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-thirty-fourth-rail`

Status: one shared registration classified from exact source; independent review pending. This additive memo does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This memo uses canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report ready. The source-manifest SHA-256 values are `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files). Verification records report identical source trees and raw input artifacts. The original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that recorded hash differences are metadata-only.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registration 66 at 966; old opacity/cache 154–155, 202–206 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | four-direction resolver 35–39; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `RailBlock.java` | subclass/constructor/default shape 10–16 | `2cb1bd437d0b0893d310d1e371ee3df0075d2a2aafaa1a612010c523bfa339aa` |
| 1.8.9 `AbstractRailBlock.java` | constructor 31–35; false predicates 43–68 | `743e8d1b8acf24aca8e297a2906d78b010960dae21b0be5a4c803aa20038111d` |
| 1.9.4 `Block.java` | registration 66 at 862; default opacity 112–114, 179–181 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | four-direction resolver 106–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `RailBlock.java` | subclass/constructor/default shape 10–16 | `62c1c8f2d37fd80234c397b55a3662baafae6f75316853cc95f7ac4d1bda03fc` |
| 1.9.4 `AbstractRailBlock.java` | constructor 33–37; false state-taking predicates 45–59 | `96256ad09e5833f44f1da66a1da255ad7e380ab63bdc60f74646f7089e1dabec` |
| 1.9.4 `block/state/StateDefinition.java` | resolved state delegates `isCube()` at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask disposition

In 1.8.9, pane connection tests call `block.isOpaque()`. The base constructor caches `opaqueCube` from virtual `isSolidRender()`, and `isOpaque()` returns that cached value. `RailBlock` extends `AbstractRailBlock`; its constructor passes `false` and assigns the `SHAPE` default `NORTH_SOUTH`. `AbstractRailBlock` returns false from both old `isSolidRender()` and `isCube()` predicates. In 1.9.4, pane connection tests call `block.defaultState().isCube()`; the resolved state delegates to `Block.isCube(state)`, and `AbstractRailBlock` returns false from its state-taking `isCube` override. Both pane resolvers evaluate north, south, west, and east and set those state properties from the respective neighbor result.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `rail`, ID 66 (`new RailBlock()`) | false | false | Both RailBlock constructors call `super(false)` and set default `SHAPE=NORTH_SOUTH`; the inherited AbstractRailBlock predicates do not depend on shape. Registry strength, sound, and key setters do not change the result. | No E arm in either version; mask remains empty. |

For the one-neighbor case, the rail is immediately east of the pane and north, south, and west are air. The predicate result is false in both versions, so this registration produces no connection-outcome or mask delta. This memo covers only the registered default-state predicate result and that directional mask; rail placement, powered behavior, collision, and movement behavior are outside scope.

## Remaining census and next slice

This is the thirty-fourth additive memo and classifies one registration. The census and reachable-mask closure remain partial. The next lowest unclassified shared registration, checked against both registries but not classified here, is `stone_stairs` ID 67. Its provider predicate, registration/default dependencies, and east-neighbor mask remain open. Independent review, complete provider closure, and original derived-JAR equivalence remain open.
