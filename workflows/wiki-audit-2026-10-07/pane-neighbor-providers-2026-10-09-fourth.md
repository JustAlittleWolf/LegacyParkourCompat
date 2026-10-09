# Pane neighbor-provider slice 4: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-fourth`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or that their recorded hash difference is metadata-only. The prior provider memos retain the complete caveat.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | `isOpaque()` 154–156; constructor 198–207; base `isSolidRender()` 352–354; registrations 1196, 1208–1209 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `HardenedClayBlock.java` | constructor and inherited behavior 8–17 | `52aff4cecb01a8d7acb0572afe3ef4fdcb0eb1a5bfeefb803ce1539af9a4b7b5` |
| 1.8.9 `DoublePlantBlock.java` | constructor/default state 24–38 | `f48ec69baff5eea4bc6a28d456df525410da43642b5aef952e6574e9c8d1b49e` |
| 1.8.9 `PlantBlock.java` | inherited `isSolidRender()`/`isCube()` 67–74 | `a862ac13e8d25c8c3d4dc12af716351a8c51cbf4aa10f0ae2705b6a036a198bd` |
| 1.8.9 `BannerBlock.java` | base predicates 23–63; standing default state 122–125 | `6918850c398314d113f9e7d21e361d19c9af1fdb74977465ca3223fc3f52dbc4` |
| 1.9.4 `Block.java` | constructor 174–181; base `isCube(state)` 222–225; registrations 1117, 1129–1130 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `HardenedClayBlock.java` | constructor and inherited behavior 8–17 | `52aff4cecb01a8d7acb0572afe3ef4fdcb0eb1a5bfeefb803ce1539af9a4b7b5` |
| 1.9.4 `DoublePlantBlock.java` | constructor/default state 26–39 | `9b0c85d13dbf0040a5a6aa44d9c56aa23c4a390f641adf2f142d40bd280826b0` |
| 1.9.4 `PlantBlock.java` | inherited state predicates 74–82 | `42de347b817879ec707ecfb310b7b3619b6ffc60146ae6cb89e5ad9920ef1842` |
| 1.9.4 `BannerBlock.java` | base predicates 24–56; standing default state 129–132 | `eb3614e39eadccbb533164306b356469088d49210298a3d2599c89e235387fe0` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, `PaneBlock` tests `block.isOpaque()`, the cached `opaqueCube` set by the `Block` constructor from virtual `isSolidRender()`. In 1.9.4, it tests `block.defaultState().isCube()` and the state definition delegates to `block.isCube(state)`. Both versions resolve the four cardinal pane neighbors; the 1.9.4 test uses the provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `hardened_clay`, ID 172 (`HardenedClayBlock`) | true | true | Both registries instantiate the same `Material.STONE` class; it has no state properties or predicate overrides and inherits base true `isSolidRender()`/`isCube()` behavior. | E in both versions; no changed mask. |
| `double_plant`, ID 175 (`DoublePlantBlock`) | false | false | Both registries instantiate `DoublePlantBlock`, whose default is `VARIANT=SUNFLOWER`, `HALF=LOWER`, `FACING=NORTH`. It inherits `PlantBlock`, whose old `isSolidRender()`/`isCube()` and new state-taking equivalents return false. | No E arm in either version; no changed mask. |
| `standing_banner`, ID 176 (`BannerBlock.Standing`) | false | false | Both registries instantiate the standing subclass; its default is `ROTATION=0`. `BannerBlock` explicitly returns false from both old predicates and both new state-taking predicates. | No E arm in either version; no changed mask. |

No provider in this slice changes the pane predicate result. These dispositions do not classify collision geometry or inspect state-update, placement, or support lifecycles.

## Remaining census and next slice

This is the fourth additive three-registration memo. Remaining era-existing registry entries still require `isOpaque()` versus default-state `isCube()` classification and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next bounded shared registrations, selected by ID and not evaluated here: `daylight_detector_inverted` ID 178, `red_sandstone` ID 179, and `red_sandstone_stairs` ID 180. Their constructor/default-state dependencies, predicates, and reachable masks remain open. Blocks absent from 1.8.9 remain excluded from this shared-version comparison. Independent review, full provider closure, and original derived-JAR equivalence remain open.
