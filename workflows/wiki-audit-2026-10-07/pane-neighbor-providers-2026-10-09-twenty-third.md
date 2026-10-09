# Pane neighbor-provider slice 23: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-twenty-third`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registrations 888–890; base predicates 245–247, 352–354 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `ColoredBlock.java` | default `COLOR=WHITE` 14–19 | `cd90b925b3fba42d430bc117bc138ef26ad77e62ce3e915747c2dba9f1753353` |
| 1.8.9 `Material.java` | `WOOL` 15; base material solid/opaque 60–66 | `017713d76afe726ca243ce32cbc35c13d3f0f0e7e5d90d122f81103cc2ca1bd2` |
| 1.8.9 `MovingBlock.java` | constructor/defaults 25–28; false predicates 70–76 | `700010b071738738f04e0508050590d8d625f01115fc4886fef6e262f85c6e85` |
| 1.8.9 `YellowFlowerBlock.java` | extends `FlowerBlock` 3–6 | `0d556d22979c039a890473893e4939c13c6090b29cbe1802562414efde8edcc5` |
| 1.8.9 `FlowerBlock.java` | default type selection 17–25 | `4139a2fc5c274704d8cb861269bc2c62027ffb75f4e17b4aea85cb77a754ccf4` |
| 1.8.9 `PlantBlock.java` | inherited false predicates 66–74 | `a862ac13e8d25c8c3d4dc12af716351a8c51cbf4aa10f0ae2705b6a036a198bd` |
| 1.9.4 `Block.java` | registrations 780–782; base predicates 223–225, 357–359 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `ColoredBlock.java` | default `COLOR=WHITE` 14–19 | `cd90b925b3fba42d430bc117bc138ef26ad77e62ce3e915747c2dba9f1753353` |
| 1.9.4 `Material.java` | `WOOL` 19; base material solid/opaque 62–68 | `f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19` |
| 1.9.4 `MovingBlock.java` | constructor/defaults 28–31; false predicates 73–79 | `4f84cdf7c0d88c51ca065299c8755341d01b126d62c282c31fc0fa46bde1daf6` |
| 1.9.4 `YellowFlowerBlock.java` | extends `FlowerBlock` 3–6 | `0d556d22979c039a890473893e4939c13c6090b29cbe1802562414efde8edcc5` |
| 1.9.4 `FlowerBlock.java` | default type selection 18–26 | `4139a2fc5c274704d8cb861269bc2c62027ffb75f4e17b4aea85cb77a754ccf4` |
| 1.9.4 `PlantBlock.java` | inherited false predicates 74–82 | `42de347b817879ec707ecfb310b7b3619b6ffc60146ae6cb89e5ad9920ef1842` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` during base construction. In 1.9.4, it reads `block.defaultState().isCube()`, which delegates to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `wool`, ID 35 (`ColoredBlock(Material.WOOL)`) | true | true | Both constructors set default `COLOR=WHITE`. `ColoredBlock` does not override either predicate; the old and new base `Block` predicates return true, so the default state qualifies in both versions. | E arm in both versions; no changed mask. |
| `piston_extension`, ID 36 (`MovingBlock`) | false | false | Both constructors set default `FACING=NORTH`, `TYPE=DEFAULT`; the class explicitly returns false from old/new solid-render and cube predicates. | No E arm in either version; no changed mask. |
| `yellow_flower`, ID 37 (`YellowFlowerBlock`) | false | false | Both classes extend `FlowerBlock`; its constructor selects default `TYPE=DANDELION` for the yellow group. `FlowerBlock` inherits PlantBlock's explicit false old/new solid-render and cube predicates. | No E arm in either version; no changed mask. |

No provider in this slice changes the pane connection outcome. The memo classifies registration-level predicate results and defaults only; wool color behavior, moving-block collision interpolation, and flower support or placement are out of scope.

## Remaining census and next slice

This is the twenty-third additive three-registration memo, bringing the bounded dispositions to sixty-eight registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next lowest unclassified shared registrations, selected by registry ID and not evaluated here: `red_flower` ID 38, `brown_mushroom` ID 39, and `red_mushroom` ID 40. Their registration/default-state dependencies, predicates, and reachable masks remain open. Independent review, full provider closure, and original derived-JAR equivalence remain open.