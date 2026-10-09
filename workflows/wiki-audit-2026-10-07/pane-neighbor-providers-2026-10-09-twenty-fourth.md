# Pane neighbor-provider slice 24: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-twenty-fourth`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registrations/instances 891–895 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `RedFlowerBlock.java` | class and red-group provider 3–6 | `66ca1f5e5b254f4536ec76117eb482e6ff61d23fc818406b1093f9bc63d8c14c` |
| 1.8.9 `FlowerBlock.java` | default type selection 17–25 | `4139a2fc5c274704d8cb861269bc2c62027ffb75f4e17b4aea85cb77a754ccf4` |
| 1.8.9 `MushroomPlantBlock.java` | PlantBlock inheritance and constructor 10–15 | `a751825f879b12b9a9d4450b581cd80abb1c16cd7f8c48ea3acbd958092e103a` |
| 1.8.9 `PlantBlock.java` | inherited false predicates 66–74 | `a862ac13e8d25c8c3d4dc12af716351a8c51cbf4aa10f0ae2705b6a036a198bd` |
| 1.9.4 `Block.java` | registrations/instances 783–787 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `RedFlowerBlock.java` | class and red-group provider 3–6 | `66ca1f5e5b254f4536ec76117eb482e6ff61d23fc818406b1093f9bc63d8c14c` |
| 1.9.4 `FlowerBlock.java` | default type selection 18–26 | `c99f86acd562457fbb03f921f35429e948f7701a85078b74ac0c5b000b0eeda3` |
| 1.9.4 `MushroomPlantBlock.java` | PlantBlock inheritance and constructor 12–17 | `33c857cca3a935a91b212976cc27a9dcd858053ded3e1dba661b0eaa88a7384e` |
| 1.9.4 `PlantBlock.java` | inherited false predicates 74–82 | `42de347b817879ec707ecfb310b7b3619b6ffc60146ae6cb89e5ad9920ef1842` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` during base construction. In 1.9.4, it reads `block.defaultState().isCube()`, which delegates to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `red_flower`, ID 38 (`RedFlowerBlock`) | false | false | Both classes identify the red flower group; `FlowerBlock` sets default `TYPE=POPPY`. It inherits PlantBlock's explicit false old/new solid-render and cube predicates. | No E arm in either version; no changed mask. |
| `brown_mushroom`, ID 39 (`MushroomPlantBlock`) | false | false | Both registries construct the same `MushroomPlantBlock` class, which inherits PlantBlock's explicit false old/new solid-render and cube predicates. The class has no block-state property affecting the tested default. | No E arm in either version; no changed mask. |
| `red_mushroom`, ID 40 (`MushroomPlantBlock`) | false | false | Both registries construct a second `MushroomPlantBlock` instance with the same class and predicate path as ID 39. | No E arm in either version; no changed mask. |

No provider in this slice changes the pane connection outcome. The 1.9.4 mushroom class changes its collision-shape implementation relative to 1.8.9, but this memo compares only the pane neighbor predicate and its default-state result; mushroom collision geometry and lifecycle are outside scope.

## Remaining census and next slice

This is the twenty-fourth additive three-registration memo, bringing the bounded dispositions to seventy-one registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next lowest unclassified shared registrations, selected by registry ID and not evaluated here: `gold_block` ID 41, `iron_block` ID 42, and `double_stone_slab` ID 43. Their registration/default-state dependencies, predicates, and reachable masks remain open. Independent review, full provider closure, and original derived-JAR equivalence remain open.