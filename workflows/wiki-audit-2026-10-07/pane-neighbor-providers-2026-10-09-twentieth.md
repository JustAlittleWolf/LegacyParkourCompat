# Pane neighbor-provider slice 20: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-twentieth`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | base predicates 245–247, 352–354; registrations 879–881 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `BedBlock.java` | default `PART=FOOT`, `OCCUPIED=false` 25–29; old predicates 93–101 | `4f66dcf2dc770239fc94ae78bf68c642bd7632e3b1618ce32fd2e63e1f51b1fa` |
| 1.8.9 `AbstractRailBlock.java` | old predicates `isSolidRender()` 43–46; `isCube()` 65–68 | `743e8d1b8acf24aca8e297a2906d78b010960dae21b0be5a4c803aa20038111d` |
| 1.8.9 `PoweredRailBlock.java` | default `SHAPE=NORTH_SOUTH`, `POWERED=false` 27–30 | `129b4739c4d7f06660b53fff11a45af9cc494d4cadf69d7026a967bcf7be32c2` |
| 1.8.9 `DetectorRailBlock.java` | default `POWERED=false`, `SHAPE=NORTH_SOUTH` 38–42 | `4f8cafd027c333003a521b5ed65b5c3de382b028b8ad048f36175e8ce61d5e94` |
| 1.9.4 `Block.java` | base predicates 222–225, 357–360; registrations 771–773 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `BedBlock.java` | default `PART=FOOT`, `OCCUPIED=false` 31–34; state-taking predicates 110–118 | `7893d2ceb9e572562018b7e55d8fecdc33e0445be89c39522075a833ccd1e463` |
| 1.9.4 `AbstractRailBlock.java` | state-taking predicates `isSolidRender()` 45–48; `isCube()` 56–59 | `96256ad09e5833f44f1da66a1da255ad7e380ab63bdc60f74646f7089e1dabec` |
| 1.9.4 `PoweredRailBlock.java` | default `SHAPE=NORTH_SOUTH`, `POWERED=false` 28–31 | `45f482b7c9c1cc63c715121e56a2628d6c5cf01ff7e23cbf6f1351a97f4a8a96` |
| 1.9.4 `DetectorRailBlock.java` | default `POWERED=false`, `SHAPE=NORTH_SOUTH` 39–43 | `37e05427577188e1e2c506472c222360b50d9215f55f8fa602a9f68d84614b34` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` in the base constructor. In 1.9.4, it reads `block.defaultState().isCube()`, which dispatches through the state definition to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `bed`, ID 26 (`BedBlock`) | false | false | Both registries construct the same bed class with default `PART=FOOT`, `OCCUPIED=false`. The class explicitly returns false from old and new solid-render/cube predicates. | No E arm in either version; no changed mask. |
| `golden_rail`, ID 27 (`PoweredRailBlock`) | false | false | Both registries construct the same powered-rail class with default `SHAPE=NORTH_SOUTH`, `POWERED=false`. Its `AbstractRailBlock` superclass explicitly returns false from old/new solid-render and cube predicates. | No E arm in either version; no changed mask. |
| `detector_rail`, ID 28 (`DetectorRailBlock`) | false | false | Both registries construct the same detector-rail class with default `POWERED=false`, `SHAPE=NORTH_SOUTH`. Its `AbstractRailBlock` superclass supplies the same constant false predicates. | No E arm in either version; no changed mask. |

No provider in this slice changes the pane connection outcome. The memo classifies registration-level predicate results and defaults only; bed parts and rail shape/update behavior are out of scope.

## Remaining census and next slice

This is the twentieth additive three-registration memo, bringing the bounded dispositions to fifty-nine registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next lowest unclassified shared registrations, selected by registry ID and not evaluated here: `sticky_piston` ID 29, `web` ID 30, and `tallgrass` ID 31. Their registration/default-state dependencies, predicates, and reachable masks remain open. Independent review, full provider closure, and original derived-JAR equivalence remain open.