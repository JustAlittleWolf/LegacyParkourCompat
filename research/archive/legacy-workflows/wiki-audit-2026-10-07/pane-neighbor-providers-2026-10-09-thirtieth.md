# Pane neighbor-provider slice 30: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-thirtieth`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registrations/instance 944–955 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `OreBlock.java` | class inheritance and constructor 15–21 | `89e25ca382a879cc6c3eb4244450122798619462681b9cf246ea999c49db9144` |
| 1.8.9 `CraftingTableBlock.java` | base Block inheritance/material 17–20 | `3eb3c84621127107bf6e65421ab520064ed8b9aa347c842e85797f2954487286` |
| 1.9.4 `Block.java` | registrations/instance 838–849 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `OreBlock.java` | class inheritance and constructor 17–23 | `d178967a8bebfdb85bcfdfd57b676582838454f2a3a339bb96b65de22b789a45` |
| 1.9.4 `CraftingTableBlock.java` | base Block inheritance/material 20–23 | `0a5282614b785c9eabf16d14cd2e52dcb1456cd1c1197c92b13f54ecb589610d` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` during base construction. In 1.9.4, it reads `block.defaultState().isCube()`, which delegates to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `diamond_ore`, ID 56 (`OreBlock`) | true | true | Both registrations construct `OreBlock`; it inherits the base Block true old solid-render/cube and new state-taking cube predicates. | E arm in both versions; no changed mask. |
| `diamond_block`, ID 57 (base `Block(Material.IRON, MapColor.DIAMOND)`) | true | true | Both registries construct base Block with no state properties or predicate override; the base true predicates apply. | E arm in both versions; no changed mask. |
| `crafting_table`, ID 58 (`CraftingTableBlock`) | true | true | Both constructors pass `Material.WOOD`; the class inherits the base Block predicates and has no state property affecting the tested default. | E arm in both versions; no changed mask. |

No provider in this slice changes the pane connection outcome. The memo classifies registration-level predicate results and defaults only; ore drops, diamond material behavior, and crafting-table interaction are outside scope.

## Remaining census and next slice

This is the thirtieth additive three-registration memo, bringing the bounded dispositions to eighty-nine registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next lowest unclassified shared registrations, selected by registry ID and not evaluated here: `wheat` ID 59, `farmland` ID 60, and `furnace` ID 61. Their registration/default-state dependencies, predicates, and reachable masks remain open. Independent review, full provider closure, and original derived-JAR equivalence remain open.