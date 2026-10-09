# Pane neighbor-provider slice 5: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-fifth`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve the caveat in the [prior provider memos](pane-neighbor-providers-2026-10-09-fourth.md).

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | `isOpaque()` 154–156; constructor 198–207; registrations 1211–1218 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `DaylightDetectorBlock.java` | default state 22–35; cube/solid-render overrides 98–105 | `2810c80b4edfef9d50ac5193161550da4b25a98e05dd7e7df5f8c2faa1f9e822` |
| 1.8.9 `RedSandstoneBlock.java` | default type 13–20 | `63d8fcbc4ed255b0e6e9db3020a99e51fa7261c5612bc2d3bf82a1d0c6adbc7a` |
| 1.8.9 `StairsBlock.java` | constructor/default state 26–46; predicate overrides 64–72 | `fadc964480227f6ffd5756471212b18d0172bffbcd0866ee0c278454ab079409` |
| 1.9.4 `Block.java` | constructor 174–181; base `isCube(state)` 222–225; registrations 1132–1138 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `DaylightDetectorBlock.java` | default state 27–40; cube/solid-render overrides 118–125 | `60718f5e14c0a132f66d03c0247c594f0e944b6c32bf356b31a88962cb381196` |
| 1.9.4 `RedSandstoneBlock.java` | default type 13–20 | `63d8fcbc4ed255b0e6e9db3020a99e51fa7261c5612bc2d3bf82a1d0c6adbc7a` |
| 1.9.4 `StairsBlock.java` | constructor/default state 29–63; predicate overrides 138–146 | `ee143aced2a6902563cddbae77e78d1d1a512244f278987684ebe5bcacba37cb` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` in the base constructor. In 1.9.4, it reads `block.defaultState().isCube()`, which dispatches through the state definition to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `daylight_detector_inverted`, ID 178 (`DaylightDetectorBlock(true)`) | false | false | Both registries pass `inverted=true`; default `POWER=0`. The class returns false from both old predicates and both new state-taking predicates, independent of the default power value. | No E arm in either version; no changed mask. |
| `red_sandstone`, ID 179 (`RedSandstoneBlock`) | true | true | Both registries bind the same block variable; default `TYPE=DEFAULT`. It inherits base true `isSolidRender()`/`isCube()` behavior in both versions. | E in both versions; no changed mask. |
| `red_sandstone_stairs`, ID 180 (`StairsBlock`) | false | false | Both registrations construct stairs from the smooth red-sandstone base state. Stairs defaults are north-facing, bottom half, straight. Old/new class cube predicates explicitly return false. The constructor's `setOpacity(255)` changes the opacity field only; it does not change old `opaqueCube`, cached false from `isSolidRender()`. | No E arm in either version; no changed mask. |

No provider in this slice changes the pane predicate result. The memo classifies only registration-level predicate results; it does not classify stair collision geometry or block update/support lifecycles.

## Remaining census and next slice

This is the fifth additive three-registration memo. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next bounded shared registrations, selected by ID and not evaluated here: `double_stone_slab2` ID 181, `stone_slab2` ID 182, and `spruce_fence_gate` ID 183. Their constructors/default-state dependencies, predicates, and reachable masks remain open. Blocks absent from 1.8.9 remain excluded from this shared-version comparison. Independent review, full provider closure, and original derived-JAR equivalence remain open.
