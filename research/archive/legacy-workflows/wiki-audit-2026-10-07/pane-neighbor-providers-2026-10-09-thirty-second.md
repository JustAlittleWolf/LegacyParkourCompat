# Pane neighbor-provider slice 32: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-thirty-second`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registrations 962–964 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `FurnaceBlock.java` | constructor/default facing/lit value 22–31 | `0636e207e37602ef48a7d0d08f6cc4fb4635694cf67ddd67f580d3d8685ac69a` |
| 1.8.9 `StandingSignBlock.java` | sign subclass and default rotation 9–14 | `741888b079e1ab5712493c985935c5e9b66adef7eccf5398b8779387b6214ccd` |
| 1.8.9 `SignBlock.java` | false old predicates 37–49 | `7ccc9f3da44069426c3fc808e97631a56b8ad10045b89459219b2c2d062e81f6` |
| 1.8.9 `DoorBlock.java` | default state 32–41; false predicates 50–62 | `51b93fcb01758e1b809a503a9e63aab6d781dbd9a0e4d3b95823168e8958128d` |
| 1.9.4 `Block.java` | registrations 858–860 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `FurnaceBlock.java` | constructor/default facing/lit value 27–36 | `c334aa4a6c3fc8b392fa259262ae64d1d19d78c9c7017da4d922832b7d603d40` |
| 1.9.4 `StandingSignBlock.java` | sign subclass and default rotation 9–14 | `2cbe4bf10e3672868796486e09f02eaf022a0044aa9a345183485032f29463eb` |
| 1.9.4 `SignBlock.java` | false state-taking predicates 39–50 | `09deedec28a527b42f45e9e352f2ea3a6e1efd95769a53121945cfdd5009c2d1` |
| 1.9.4 `DoorBlock.java` | default state 39–48; false predicates 76–88 | `68ce8fe13f6b08b0803f214716784f6196c77728e4ab0840eb22a52c3f98217e` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` during base construction. In 1.9.4, it reads `block.defaultState().isCube()`, which delegates to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `lit_furnace`, ID 62 (`FurnaceBlock(true)`) | true | true | Both constructors set default `FACING=NORTH`; the lit flag differs from ID 61 but does not change the inherited base predicates. | E arm in both versions; no changed mask. |
| `standing_sign`, ID 63 (`StandingSignBlock`) | false | false | Both defaults set `ROTATION=0`. StandingSignBlock inherits SignBlock, which explicitly returns false from old/new solid-render and cube predicates. | No E arm in either version; no changed mask. |
| `wooden_door`, ID 64 (`DoorBlock(Material.WOOD)`) | false | false | Both defaults set `FACING=NORTH`, `OPEN=false`, `HINGE=LEFT`, `POWERED=false`, `HALF=LOWER`; DoorBlock explicitly returns false from old/new solid-render and cube predicates. | No E arm in either version; no changed mask. |

No provider in this slice changes the pane connection outcome. The memo classifies registration-level predicate results and defaults only; furnace operation, sign rotation, door placement, collision, and interaction are outside scope.

## Remaining census and next slice

This is the thirty-second additive three-registration memo, bringing the bounded dispositions to ninety-five registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next lowest unclassified shared registrations, selected by registry ID and not evaluated here: `ladder` ID 65, `rail` ID 66, and `stone_stairs` ID 67. Their registration/default-state dependencies, predicates, and reachable masks remain open. Independent review, full provider closure, and original derived-JAR equivalence remain open.