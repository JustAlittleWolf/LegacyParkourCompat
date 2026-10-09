# Pane neighbor-provider slice 31: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-thirty-first`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registrations/instance 956–960 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `WheatBlock.java` | PlantBlock inheritance/default age 14–18 | `5960755f7a5060d81f3aa148b370eb8a135407b65537492f9687364d786f1edf` |
| 1.8.9 `PlantBlock.java` | inherited false predicates 66–74 | `a862ac13e8d25c8c3d4dc12af716351a8c51cbf4aa10f0ae2705b6a036a198bd` |
| 1.8.9 `FarmlandBlock.java` | constructor/default moisture 18–25; false predicates 35–42 | `0d5c03d8706d7b3ef2091c1bf640f64807d173689fb76245603a7ab56af65271` |
| 1.8.9 `FurnaceBlock.java` | constructor/default facing 22–30 | `0636e207e37602ef48a7d0d08f6cc4fb4635694cf67ddd67f580d3d8685ac69a` |
| 1.8.9 `BlockWithBlockEntity.java` | inherits from Block and provides no predicate override 12 | `9126813af904415188c993830f592326fd78f1b9c9e6224373256f6ac23ce175` |
| 1.8.9 `Block.java` | base predicates 245–247, 352–354 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.9.4 `Block.java` | registrations/instance 850–856 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `WheatBlock.java` | PlantBlock inheritance/default age 18–32 | `7caaa65c3401a3dd5d5d2eb0218fb786be0230318c23765fe4092cc8af34d285` |
| 1.9.4 `PlantBlock.java` | inherited false predicates 74–82 | `42de347b817879ec707ecfb310b7b3619b6ffc60146ae6cb89e5ad9920ef1842` |
| 1.9.4 `FarmlandBlock.java` | constructor/default moisture 20–28; false predicates 43–50 | `9df445485ba7e1603c20b5dede847fe412558148acefe1eeddc9bb3b62b14c25` |
| 1.9.4 `FurnaceBlock.java` | constructor/default facing 27–35 | `c334aa4a6c3fc8b392fa259262ae64d1d19d78c9c7017da4d922832b7d603d40` |
| 1.9.4 `BlockWithBlockEntity.java` | inherits from Block and provides no predicate override 13 | `94455367c7160e48d0f8bf3b2d49265bc8ba1365f3ae3ac91bc2770e4de30816` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` during base construction. In 1.9.4, it reads `block.defaultState().isCube()`, which delegates to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `wheat`, ID 59 (`WheatBlock`) | false | false | Both defaults set `AGE=0`; WheatBlock inherits PlantBlock's explicit false old/new solid-render and cube predicates. | No E arm in either version; no changed mask. |
| `farmland`, ID 60 (`FarmlandBlock`) | false | false | Both defaults set `MOISTURE=0`; the class explicitly returns false from old/new solid-render and cube predicates. The 1.9.4 `setOpacity(255)` affects the separate opacity value, not the default state's `isCube()` result. | No E arm in either version; no changed mask. |
| `furnace`, ID 61 (`FurnaceBlock(false)`) | true | true | Both constructors set default `FACING=NORTH`; FurnaceBlock inherits the base true old solid-render/cube and new state-taking cube predicates. | E arm in both versions; no changed mask. |

No provider in this slice changes the pane connection outcome. The memo classifies registration-level predicate results and defaults only; crop growth, farmland height/support, furnace interaction, and block-entity behavior are outside scope.

## Remaining census and next slice

This is the thirty-first additive three-registration memo, bringing the bounded dispositions to ninety-two registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next lowest unclassified shared registrations, selected by registry ID and not evaluated here: `lit_furnace` ID 62, `standing_sign` ID 63, and `wooden_door` ID 64. Their registration/default-state dependencies, predicates, and reachable masks remain open. Independent review, full provider closure, and original derived-JAR equivalence remain open.