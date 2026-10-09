# Pane neighbor-provider slice 9: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-ninth`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registrations 1268–1294 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `FenceBlock.java` | default directions 29–32; solid-render/cube predicates 113–121 | `8d6a803988d77fc51f364520f2e0cd5567b1d5c29a4d28d01cdcf93e6d5890d9` |
| 1.8.9 `PlanksBlock.java` | jungle/dark-oak/acacia variant definitions 55–61 | `9d94a64ccdc7ebb4e817d6d05ad3fde47ad4b1cdfbb070b18026cccab96ef5be` |
| 1.9.4 `Block.java` | registrations 1192–1217 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `FenceBlock.java` | default directions 51–54; state-taking predicates 105–113 | `d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909` |
| 1.9.4 `PlanksBlock.java` | jungle/dark-oak/acacia variant definitions 55–61 | `9d94a64ccdc7ebb4e817d6d05ad3fde47ad4b1cdfbb070b18026cccab96ef5be` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` in the base constructor. In 1.9.4, it reads `block.defaultState().isCube()`, which dispatches through the state definition to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `jungle_fence`, ID 190 (`FenceBlock(Material.WOOD, JUNGLE color)`) | false | false | Both registries construct the same fence class with the jungle color. The constructor's default north/east/south/west properties are false. The class explicitly returns false from old `isSolidRender()`/`isCube()` and new state-taking equivalents; the predicates do not depend on variant or connection properties. | No E arm in either version; no changed mask. |
| `dark_oak_fence`, ID 191 (`FenceBlock(Material.WOOD, DARK_OAK color)`) | false | false | Both registries construct the same fence class with the dark-oak color and the same four false default connection properties. Its old/new solid-render and cube predicates are constant false. | No E arm in either version; no changed mask. |
| `acacia_fence`, ID 192 (`FenceBlock(Material.WOOD, ACACIA color)`) | false | false | Both registries construct the same fence class with the acacia color and the same four false default connection properties. Its old/new solid-render and cube predicates are constant false. | No E arm in either version; no changed mask. |

No provider in this slice changes the pane predicate result. The memo classifies only registration-level predicate results; it does not classify fence collision geometry or connection/update lifecycles.

## Remaining census and next slice

This is the ninth additive three-registration memo, bringing the bounded dispositions to twenty-seven registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next bounded shared registrations, selected by ID and not evaluated here: `spruce_door` ID 193, `birch_door` ID 194, and `jungle_door` ID 195. Their registration/default-state dependencies, predicates, and reachable masks remain open. Blocks absent from 1.8.9 remain excluded from this shared-version comparison. Independent review, full provider closure, and original derived-JAR equivalence remain open.