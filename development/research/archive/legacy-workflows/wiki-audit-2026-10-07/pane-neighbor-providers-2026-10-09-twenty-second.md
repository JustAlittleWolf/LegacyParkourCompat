# Pane neighbor-provider slice 22: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-twenty-second`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registrations 885–887 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `DeadBushBlock.java` | class inheritance and constructor 16–18 | `118f60c5fbba71c9969dcda3b03f84becdcaa745b2e99f8b46447494362fb8a1` |
| 1.8.9 `PlantBlock.java` | inherited false predicates 66–74 | `a862ac13e8d25c8c3d4dc12af716351a8c51cbf4aa10f0ae2705b6a036a198bd` |
| 1.8.9 `PistonBaseBlock.java` | constructor/defaults 29–31; predicates 39–41, 222–225 | `3c96698a674714446f9fd0d6cc1c4d5eb72937d9a2f396516ddb2461c3ea4929` |
| 1.8.9 `PistonHeadBlock.java` | constructor/defaults 26–28; predicates 62–68 | `c5515744ce27521195ec802e2bd89ae5327448302542436576bcacf19f245fa4` |
| 1.9.4 `Block.java` | registrations 777–779 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `DeadBushBlock.java` | class inheritance and constructor 19–23 | `7f520f41bed66ced99730bb258cd503ef5527d75b4afb1b1fee4162556971d36` |
| 1.9.4 `PlantBlock.java` | inherited false predicates 74–82 | `42de347b817879ec707ecfb310b7b3619b6ffc60146ae6cb89e5ad9920ef1842` |
| 1.9.4 `PistonBaseBlock.java` | constructor/defaults 38–40; predicates 81–83, 216–218 | `4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36` |
| 1.9.4 `PistonHeadBlock.java` | constructor/defaults 38–40; predicates 119–125 | `9e8bd56219d3a00e57082481d6e17a2c9383e994dac6cecac2534918f4aeb579` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` during base construction. In 1.9.4, it reads `block.defaultState().isCube()`, which delegates to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `deadbush`, ID 32 (`DeadBushBlock`) | false | false | Both registrations construct `DeadBushBlock`, which extends `PlantBlock`; that superclass explicitly returns false from old/new solid-render and cube predicates. | No E arm in either version; no changed mask. |
| `piston`, ID 33 (`PistonBaseBlock(false)`) | false | false | Both constructors set default `FACING=NORTH`, `EXTENDED=false`; the class explicitly returns false from old/new solid-render and cube predicates. | No E arm in either version; no changed mask. |
| `piston_head`, ID 34 (`PistonHeadBlock`) | false | false | Both constructors set default `FACING=NORTH`, `TYPE=DEFAULT`, `SHORT=false`; the class explicitly returns false from old/new solid-render and cube predicates. | No E arm in either version; no changed mask. |

No provider in this slice changes the pane connection outcome. The memo classifies registration-level predicate results and defaults only; plant support, piston/head geometry, moving-block state, and piston extension behavior are out of scope.

## Remaining census and next slice

This is the twenty-second additive three-registration memo, bringing the bounded dispositions to sixty-five registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next lowest unclassified shared registrations, selected by registry ID and not evaluated here: `wool` ID 35, `piston_extension` ID 36, and `yellow_flower` ID 37. Their registration/default-state dependencies, predicates, and reachable masks remain open. Independent review, full provider closure, and original derived-JAR equivalence remain open.