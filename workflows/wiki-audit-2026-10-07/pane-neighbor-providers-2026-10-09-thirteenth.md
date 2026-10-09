# Pane neighbor-provider slice 13: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-thirteenth`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | base `isCube()` 245–247; base `isSolidRender()` 352–354; registrations and local bindings 830–839 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `PlanksBlock.java` | default `VARIANT=OAK` 14–21 | `9d94a64ccdc7ebb4e817d6d05ad3fde47ad4b1cdfbb070b18026cccab96ef5be` |
| 1.8.9 `SaplingBlock.java` | inheritance/default `TYPE=OAK`, `STAGE=0` 25–34 | `d69b4e918449447cdb7438d6533e584f77d8fc1022eede67a36df35af10c6e14` |
| 1.8.9 `PlantBlock.java` | inherited old predicates 66–74 | `a862ac13e8d25c8c3d4dc12af716351a8c51cbf4aa10f0ae2705b6a036a198bd` |
| 1.9.4 `Block.java` | base `isCube(state)` 222–225; base `isSolidRender(state)` 357–360; registrations and local bindings 722–731 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `PlanksBlock.java` | default `VARIANT=OAK` 14–21 | `9d94a64ccdc7ebb4e817d6d05ad3fde47ad4b1cdfbb070b18026cccab96ef5be` |
| 1.9.4 `SaplingBlock.java` | inheritance/default `TYPE=OAK`, `STAGE=0` 27–35 | `06f79ca33ff43c89a25a696cb4f1a194e3c6772be7b07f29e568c12fd19b16c8` |
| 1.9.4 `PlantBlock.java` | inherited state-taking predicates 74–82 | `42de347b817879ec707ecfb310b7b3619b6ffc60146ae6cb89e5ad9920ef1842` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` in the base constructor. In 1.9.4, it reads `block.defaultState().isCube()`, which dispatches through the state definition to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `cobblestone`, ID 4 (`Block(Material.STONE)`) | true | true | Both registries bind the same local generic stone-material block to ID 4. It has no state properties or predicate override and inherits true base predicates. | E in both versions; no changed mask. |
| `planks`, ID 5 (`PlanksBlock`) | true | true | Both registries bind the same `PlanksBlock` class. Default `VARIANT=OAK`; it has no predicate override and inherits true base predicates. | E in both versions; no changed mask. |
| `sapling`, ID 6 (`SaplingBlock` → `PlantBlock`) | false | false | Both registries construct `SaplingBlock` with default `TYPE=OAK`, `STAGE=0`. The `PlantBlock` superclass explicitly returns false from old `isSolidRender()`/`isCube()` and new state-taking equivalents, independent of sapling state. | No E arm in either version; no changed mask. |

No provider in this slice changes the pane predicate result. The memo classifies only registration-level predicate results; it does not classify sapling growth, plant support, or lifecycle behavior.

## Remaining census and next slice

This is the thirteenth additive three-registration memo, bringing the bounded dispositions to thirty-eight registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next lowest unclassified shared registrations, selected by registry ID and not evaluated here: `bedrock` ID 7, `flowing_water` ID 8, and `water` ID 9. Their registration/default-state dependencies, predicates, and reachable masks remain open. Independent review, full provider closure, and original derived-JAR equivalence remain open.