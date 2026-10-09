# Pane neighbor-provider slice 16: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-sixteenth`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | base `isCube()` 245–247; base `isSolidRender()` 352–354; registrations 856–858 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `GravelBlock.java` | superclass and class declaration 9–10; no predicate overrides | `0b571efbf7549e9067e1f7e5180eec4f1a3c0d5d6d6640d3e478cbf2dcc0f71f` |
| 1.8.9 `FallingBlock.java` | default constructor 11–21; no predicate overrides | `a3fcbc22ca73e0b31d2719f62094323c24ae7b56ebb3ce58b7a7909c1d5abaa0` |
| 1.8.9 `OreBlock.java` | constructor 15–24; no predicate overrides | `89e25ca382a879cc6c3eb4244450122798619462681b9cf246ea999c49db9144` |
| 1.9.4 `Block.java` | base `isCube(state)` 222–225; base `isSolidRender(state)` 357–360; registrations 748–750 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `GravelBlock.java` | superclass and class declaration 10–11; no predicate overrides | `cddf58cb713884cce8ce11ae4c8461781690fcacfec0f32dfd93600e7fb6f030` |
| 1.9.4 `FallingBlock.java` | default constructor 11–21; no predicate overrides | `d68a2a8d8cc34c4e00c9ca4b04988900efccfc07643053bf83f4f4e14e61c36f` |
| 1.9.4 `OreBlock.java` | constructor 17–25; no predicate overrides | `d178967a8bebfdb85bcfdfd57b676582838454f2a3a339bb96b65de22b789a45` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` in the base constructor. In 1.9.4, the pane predicate reads `block.defaultState().isCube()`, which dispatches through the state definition to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `gravel`, ID 13 (`GravelBlock` → `FallingBlock`) | true | true | Both registries construct the same gravel subclass. It inherits the `FallingBlock` default constructor and has no provider state or predicate override; it inherits true base predicates. | E in both versions; no changed mask. |
| `gold_ore`, ID 14 (`OreBlock`) | true | true | Both registries construct the same `OreBlock` with default `Material.STONE`; it has no provider state or predicate override and inherits true base predicates. | E in both versions; no changed mask. |
| `iron_ore`, ID 15 (`OreBlock`) | true | true | Both registries construct the same `OreBlock` with default `Material.STONE`; it has no provider state or predicate override and inherits true base predicates. | E in both versions; no changed mask. |

No provider in this slice changes the pane predicate result. The memo classifies registration-level predicate results only; it does not classify falling-block behavior or ore drops.

## Remaining census and next slice

This is the sixteenth additive three-registration memo, bringing the bounded dispositions to forty-seven registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next lowest unclassified shared registrations, selected by registry ID and not evaluated here: `coal_ore` ID 16, `log` ID 17, and `sponge` ID 19. Leaves ID 18 is already classified in the first provider slice. Their registration/default-state dependencies, predicates, and reachable masks remain open. Independent review, full provider closure, and original derived-JAR equivalence remain open.