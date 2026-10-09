# Pane neighbor-provider slice 14: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-fourteenth`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | `isOpaque()` 154–156; constructor cache 198–207; `setOpacity()` 218–220; registrations 840–852 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `LiquidBlock.java` | default `LEVEL=0` 24–27; old predicates 58–66 | `1537fd6b1bca65e0f910f9a12bcd3bb27f5a86be7f13a1caa380c5e66544f1d5` |
| 1.8.9 `FlowingLiquidBlock.java` | inheritance and constructor 12–17 | `16b30954bbb5bc9b40072ad19304a7efe8ac2483ee1a4baeda777fb4145daaf0` |
| 1.8.9 `LiquidSourceBlock.java` | inheritance and constructor 10–17 | `2b263d3dad30022c81c53d7682791447e4c8a22fa22c01f4e3c1ce84f8c49568` |
| 1.9.4 `Block.java` | constructor/default-state cache 174–181; registrations 732–744 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `BedrockBlock.java` | constructor 9–12; no predicate overrides | `13e0c4f4a015e1334cc4601efed1fcd97b34f2c8a532964e9ea6171d812d88a3` |
| 1.9.4 `LiquidBlock.java` | default `LEVEL=0` 27–30; state-taking predicates 66–74 | `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e` |
| 1.9.4 `FlowingLiquidBlock.java` | inheritance and constructor 12–17 | `12bc1bdbcb17a4fa321a14094144919d5a6c00058a3d28c5f8383a1465d47104` |
| 1.9.4 `LiquidSourceBlock.java` | inheritance and constructor 10–17 | `896a64f1a1170047cad30c1f65fe06e6a3a223f07f42b875c153748d1df3e3b3` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` in the base constructor. `setOpacity(3)` changes the separate opacity field; it does not change the cached `opaqueCube` read by `isOpaque()`. In 1.9.4, the pane predicate reads `block.defaultState().isCube()`, which dispatches through the state definition to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `bedrock`, ID 7 | true | true | 1.8.9 registers a generic `Block(Material.STONE)` with no state or predicate override. 1.9.4 constructs `BedrockBlock(Material.STONE)`, which inherits the same true base predicate behavior. | E in both versions; no changed mask. |
| `flowing_water`, ID 8 (`FlowingLiquidBlock(Material.WATER)`) | false | false | Both registrations construct the same subclass of `LiquidBlock`; its default `LEVEL=0`. The liquid superclass explicitly returns false from the old and new render/cube predicates. Registry `setOpacity(3)` leaves old cached opacity false. | No E arm in either version; no changed mask. |
| `water`, ID 9 (`LiquidSourceBlock(Material.WATER)`) | false | false | Both registrations construct the same source-liquid subclass and default `LEVEL=0`. The shared liquid superclass predicates are constant false; `setOpacity(3)` does not alter old cached opacity. | No E arm in either version; no changed mask. |

No provider in this slice changes the pane predicate result. The memo classifies registration-level predicate results and defaults only; fluid tick/spread behavior and bedrock lifecycle are outside this inventory.

## Remaining census and next slice

This is the fourteenth additive three-registration memo, bringing the bounded dispositions to forty-one registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next lowest unclassified shared registrations, selected by registry ID and not evaluated here: `flowing_lava` ID 10, `lava` ID 11, and `sand` ID 12. Their registration/default-state dependencies, predicates, and reachable masks remain open. Independent review, full provider closure, and original derived-JAR equivalence remain open.