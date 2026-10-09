# Pane neighbor-provider slice 7: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-seventh`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registrations 1230–1244 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `FenceGateBlock.java` | default `OPEN`/`POWERED`/`IN_WALL` 21–24; old predicates 73–81 | `43749004a2826051c627c794f72db705283df26e1810d8b4a7a78205ac974d2c` |
| 1.8.9 `PlanksBlock.java` | variants BIRCH/JUNGLE/DARK_OAK 55–61 | `9d94a64ccdc7ebb4e817d6d05ad3fde47ad4b1cdfbb070b18026cccab96ef5be` |
| 1.9.4 `Block.java` | registrations 1153–1167 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `FenceGateBlock.java` | default properties 30–34; new predicates 90–98 | `44ee568ddaf2e726b2017315d66b59fc013c093be4f0f2f23f669b0ca0e41366` |
| 1.9.4 `PlanksBlock.java` | variants BIRCH/JUNGLE/DARK_OAK 55–61 | `9d94a64ccdc7ebb4e817d6d05ad3fde47ad4b1cdfbb070b18026cccab96ef5be` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` in the base constructor. In 1.9.4, it reads `block.defaultState().isCube()`, which dispatches through the state definition to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `birch_fence_gate`, ID 184 (`FenceGateBlock(PlanksBlock.Variant.BIRCH)`) | false | false | Both registries construct the same fence-gate class with `BIRCH`. The default `OPEN`, `POWERED`, and `IN_WALL` values are false. Its old/new solid-render and cube predicates explicitly return false and do not depend on the variant or state. | No E arm in either version; no changed mask. |
| `jungle_fence_gate`, ID 185 (`FenceGateBlock(PlanksBlock.Variant.JUNGLE)`) | false | false | Both registries construct the same fence-gate class with `JUNGLE`; the same false defaults and constant false predicates apply. | No E arm in either version; no changed mask. |
| `dark_oak_fence_gate`, ID 186 (`FenceGateBlock(PlanksBlock.Variant.DARK_OAK)`) | false | false | Both registries construct the same fence-gate class with `DARK_OAK`; the same false defaults and constant false predicates apply. | No E arm in either version; no changed mask. |

No provider in this slice changes the pane predicate result. The memo classifies only registration-level predicate results; it does not classify gate collision geometry or block update/support lifecycles.

## Remaining census and next slice

This is the seventh additive three-registration memo, bringing the bounded dispositions to twenty-one registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next bounded shared registrations, selected by ID and not evaluated here: `acacia_fence_gate` ID 187, `spruce_fence` ID 188, and `birch_fence` ID 189. Their registration/default-state dependencies, predicates, and reachable masks remain open. Blocks absent from 1.8.9 remain excluded from this shared-version comparison. Independent review, full provider closure, and original derived-JAR equivalence remain open.