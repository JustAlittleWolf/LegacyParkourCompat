# Pane neighbor-provider slice 19: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-nineteenth`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | base predicates 245–247, 352–354; registrations/bindings 875–878 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `BlockWithBlockEntity.java` | base constructor 12–20; no predicate overrides | `9126813af904415188c993830f592326fd78f1b9c9e6224373256f6ac23ce175` |
| 1.8.9 `DispenserBlock.java` | default `FACING=NORTH`, `TRIGGERED=false` 31–41; no predicate overrides | `794e6780d586a16a31264d953037d53e46cf44e7ef85ab69aae126f6a1444c31` |
| 1.8.9 `SandstoneBlock.java` | default `TYPE=DEFAULT` 14–21; no predicate overrides | `a6779715fada3d0d1e2479180fc22273e7fb38bc126c0abb8a02e0663974efb3` |
| 1.8.9 `NoteBlock.java` | constructor 17–23; no predicate overrides | `278e7ce95c549281870a68564dfa944e55b87f70708e4bbb39918aa19a855b1d` |
| 1.9.4 `Block.java` | base predicates 222–225, 357–360; registrations/bindings 767–770 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `BlockWithBlockEntity.java` | base constructor 13–21; no predicate overrides | `94455367c7160e48d0f8bf3b2d49265bc8ba1365f3ae3ac91bc2770e4de30816` |
| 1.9.4 `DispenserBlock.java` | default `FACING=NORTH`, `TRIGGERED=false` 34–44; no predicate overrides | `a4910c3aa32a0d6175f1407c258c7feda87fbec9a15540aa665245c6ec3d385e` |
| 1.9.4 `SandstoneBlock.java` | default `TYPE=DEFAULT` 14–21; no predicate overrides | `a6779715fada3d0d1e2479180fc22273e7fb38bc126c0abb8a02e0663974efb3` |
| 1.9.4 `NoteBlock.java` | constructor 24–32; no predicate overrides | `2bd481e38ec8710cf707ebe19420519a3d3fe8df9d2b67b9ec399a491f7d80d1` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` in the base constructor. In 1.9.4, it reads `block.defaultState().isCube()`, which dispatches through the state definition to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `dispenser`, ID 23 (`DispenserBlock`) | true | true | Both registries construct the same dispenser class with default `FACING=NORTH`, `TRIGGERED=false`. Its block-entity superclass does not override either predicate; it inherits true base behavior. | E in both versions; no changed mask. |
| `sandstone`, ID 24 (`SandstoneBlock`) | true | true | Both registries bind the same sandstone block variable. Default `TYPE=DEFAULT`; no predicate override, so it inherits true base behavior. | E in both versions; no changed mask. |
| `noteblock`, ID 25 (`NoteBlock`) | true | true | Both registries construct the same note-block class with no additional state properties. Its block-entity superclass does not override either predicate; it inherits true base behavior. | E in both versions; no changed mask. |

No provider in this slice changes the pane connection outcome. The memo classifies registration-level predicate results and defaults only; note pitch and block-entity behavior remain outside this inventory.

## Remaining census and next slice

This is the nineteenth additive three-registration memo, bringing the bounded dispositions to fifty-six registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next lowest unclassified shared registrations, selected by registry ID and not evaluated here: `bed` ID 26, `golden_rail` ID 27, and `detector_rail` ID 28. Their registration/default-state dependencies, predicates, and reachable masks remain open. Independent review, full provider closure, and original derived-JAR equivalence remain open.