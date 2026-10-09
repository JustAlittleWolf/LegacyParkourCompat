# Pane neighbor-provider slice 37: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-thirty-seventh`

Status: three shared registrations classified from exact source; independent review pending. This additive memo does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This memo uses canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report ready. The source-manifest SHA-256 values are `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files). Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that recorded hash differences are metadata-only.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registrations 71–73 at 978–995; old opacity/cache 154–155, 202–206; base cube predicate 245–247 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | four-direction resolver 35–39; connection predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `DoorBlock.java` | constructor/default state 32–47; false predicates 49–62 | `51b93fcb01758e1b809a503a9e63aab6d781dbd9a0e4d3b95823168e8958128d` |
| 1.8.9 `PressurePlateBlock.java` | constructor/default and activation rule 18–22 | `2a78b32351f803b1f48164dde6270cd96f60acfecebebb146fe6358ba7d3e84a` |
| 1.8.9 `AbstractPressurePlateBlock.java` | false predicates 51–58 | `85896b070be577d228e8a660f1f95cae8956467a957463c4fc839080d2e5b1cb` |
| 1.8.9 `RedstoneOreBlock.java` | class and constructor/lit flag 16–24 | `9b2efe542205a9ccba4f86f472f973a1beab413f3de3773dd28f4bf6915911db` |
| 1.9.4 `Block.java` | registrations 71–73 at 874–891; default opacity 112–114, 179–181; base cube predicate 223–225 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | four-direction resolver 106–110; connection predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `DoorBlock.java` | constructor/default state 39–54; false predicates 75–88 | `68ce8fe13f6b08b0803f214716784f6196c77728e4ab0840eb22a52c3f98217e` |
| 1.9.4 `PressurePlateBlock.java` | constructor/default and activation rule 20–24 | `3d310346d115a5790a91736b3db9b9a866d9949cb17abd6e777965099f03dc56` |
| 1.9.4 `AbstractPressurePlateBlock.java` | false state-taking predicates 49–56 | `0495b7e06db5d51ff7a3e6764587d9afd076365e41006e419eec08d4b3979990` |
| 1.9.4 `RedstoneOreBlock.java` | class and constructor/lit flag 18–26 | `dcc46f1d6ce5cb4eaafdd9ff31eb8e18543714db832cd27b391ddcc50d8b7eae` |
| 1.9.4 `block/state/StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, pane resolution tests a neighbor with `block.isOpaque()`. `Block` caches `opaqueCube` from virtual `isSolidRender()` in its constructor, and `isOpaque()` returns that value. In 1.9.4, the pane tests `block.defaultState().isCube()`; resolved states delegate to `Block.isCube(state)`. The pane resolver sets its north, south, west, and east properties from those cardinal tests. These registrations are not in the pane's explicit same-pane/glass allowlist.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `iron_door`, ID 71 (`new DoorBlock(Material.IRON)`) | false | false | Both defaults set `FACING=NORTH`, `OPEN=false`, `HINGE=LEFT`, `POWERED=false`, and `HALF=LOWER`. DoorBlock explicitly returns false from both old and state-taking solid-render and cube predicates; the iron material and defaults do not change them. | No E arm in either version; mask remains empty. |
| `wooden_pressure_plate`, ID 72 (`new PressurePlateBlock(Material.WOOD, ActivationRule.EVERYTHING)`) | false | false | Both defaults set `POWERED=false` and both registrations choose `ActivationRule.EVERYTHING`. AbstractPressurePlateBlock explicitly returns false from the old and state-taking predicates. | No E arm in either version; mask remains empty. |
| `redstone_ore`, ID 73 (`new RedstoneOreBlock(false)`) | true | true | RedstoneOreBlock extends Block and declares no solid-render or cube predicate override. It passes `Material.STONE`; its `lit` field is false and is not a block-state property read by the pane. The inherited base predicates return true. | E arm in both versions; no changed mask. |

For each witness, the provider is immediately east of the pane while north, south, and west are air. Iron Door and Wooden Pressure Plate return false in both versions, producing no arm. Redstone Ore returns true in both, producing the E arm in both. This batch has no connection-outcome or mask delta. The memo covers registration-level defaults, predicates, and these directional witnesses only; door placement, pressure activation, redstone light/tick behavior, collision, and player movement are outside scope.

## Remaining census and next slice

This is the thirty-seventh additive three-registration memo. The bounded inventory now records 104 classified registrations, still partial. The next lowest unclassified shared registrations, verified in both registries but not classified here, are `lit_redstone_ore` ID 74, `unlit_redstone_torch` ID 75, and `redstone_torch` ID 76. Their predicates, defaults, and east-neighbor masks remain open. Independent review, complete provider closure, all world-reachable pane masks, and original derived-JAR equivalence remain open.
