# Pane neighbor-provider slice 11: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-eleventh`

Status: two additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registrations 1298–1299 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `DoorBlock.java` | default state 31–42; old predicates 49–62 | `51b93fcb01758e1b809a503a9e63aab6d781dbd9a0e4d3b95823168e8958128d` |
| 1.9.4 `Block.java` | registrations 1221–1222 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `DoorBlock.java` | default state 38–49; new predicates 75–88 | `68ce8fe13f6b08b0803f214716784f6196c77728e4ab0840eb22a52c3f98217e` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` in the base constructor. In 1.9.4, it reads `block.defaultState().isCube()`, which dispatches through the state definition to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `acacia_door`, ID 196 (`DoorBlock(Material.WOOD)`) | false | false | Both registries construct the same wood door class. Its default state is north-facing, closed, left-hinged, unpowered, lower-half. Old `isSolidRender()`/`isCube()` and new state-taking equivalents explicitly return false independent of these properties. | No E arm in either version; no changed mask. |
| `dark_oak_door`, ID 197 (`DoorBlock(Material.WOOD)`) | false | false | Both registries construct the same wood door class with the same default state; its old/new solid-render and cube predicates are constant false. | No E arm in either version; no changed mask. |

No provider in this slice changes the pane predicate result. Registry ID 198 `end_rod` is present in 1.9.4 but has no 1.8.9 registration/class counterpart and is excluded from the shared-version comparison. A source registry-name scan found no further 1.9.4 registrations after ID 197 with matching 1.8.9 registry names; this does not classify the remaining shared registrations at lower IDs or close the provider census.

## Remaining census and next slice

This is the eleventh additive memo, bringing the bounded dispositions to thirty-two registrations across eleven slices. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Continue the shared registry-name inventory at its lowest unclassified IDs: `stone` ID 1, `grass` ID 2, and `dirt` ID 3. Their constructor/default-state dependencies, predicates, and reachable masks remain open. Independent review, full provider closure, and original derived-JAR equivalence remain open.