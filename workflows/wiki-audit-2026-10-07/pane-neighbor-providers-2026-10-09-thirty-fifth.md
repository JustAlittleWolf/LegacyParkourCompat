# Pane neighbor-provider slice 35: Stone Stairs (1.8.9 to 1.9.4)

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-thirty-fifth-stone-stairs`

Status: one shared registration classified from exact source; independent review pending. This additive memo does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This memo uses canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report ready. The source-manifest SHA-256 values are `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files). Verification records report identical source trees and raw input artifacts. The original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that recorded hash differences are metadata-only.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | stone-stairs registration 67 at 967; base block binding 830–836; old opacity/cache 154–155, 202–206 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | four-direction resolver 35–39; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `StairsBlock.java` | constructor/default 36–41; false predicates 64–72 | `fadc964480227f6ffd5756471212b18d0172bffbcd0866ee0c278454ab079409` |
| 1.9.4 `Block.java` | stone-stairs registration 67 at 863; base block binding 722–728; default opacity 112–114, 179–181 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | four-direction resolver 106–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `StairsBlock.java` | constructor/default 54–59; false predicates 137–146 | `ee143aced2a6902563cddbae77e78d1d1a512244f278987684ebe5bcacba37cb` |
| 1.9.4 `block/state/StateDefinition.java` | resolved state delegates `isCube()` at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask disposition

In 1.8.9, pane connection tests call `block.isOpaque()`. The base constructor caches `opaqueCube` from virtual `isSolidRender()`, and `isOpaque()` returns that cached value. `StairsBlock` explicitly returns false from old `isSolidRender()` and `isCube()`. In 1.9.4, pane connection tests call `block.defaultState().isCube()`; the resolved state delegates to `Block.isCube(state)`, and `StairsBlock` returns false from its state-taking `isCube` override. Both pane resolvers evaluate north, south, west, and east and set those state properties from the respective neighbor result.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `stone_stairs`, ID 67 (`new StairsBlock(block.defaultState())`) | false | false | In both registry initializers, `block` is the registered stone-brick base block (`new Block(Material.STONE)` at ID 4). Both stair constructors set default `FACING=NORTH`, `HALF=BOTTOM`, and `SHAPE=STRAIGHT`; neither predicate depends on these default values or the base state. | No E arm in either version; mask remains empty. |

For the one-neighbor case, stone stairs are immediately east of the pane and north, south, and west are air. The predicate result is false in both versions, so this registration produces no connection-outcome or mask delta. This memo covers only the registered default-state predicate result and that directional mask; stair placement, shape recomputation, collision, and movement behavior are outside scope.

## Remaining census and next slice

This is the thirty-fifth additive memo and classifies one registration. The census and reachable-mask closure remain partial. The next lowest unclassified shared registrations, checked against both registries but not classified here, are `wall_sign` ID 68, `lever` ID 69, and `stone_pressure_plate` ID 70. Their provider predicates, defaults, and east-neighbor masks remain open. Independent review, complete provider closure, and original derived-JAR equivalence remain open.
