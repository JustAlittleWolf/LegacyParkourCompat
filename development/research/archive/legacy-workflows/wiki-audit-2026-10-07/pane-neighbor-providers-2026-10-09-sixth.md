# Pane neighbor-provider slice 6: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-sixth`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | `isOpaque()` 154–156; constructor cache 198–207; registrations 1220–1228 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `SlabBlock.java` | `isSolidRender()` 69–72; `isCube()` 89–92 | `35be26d8419f707ca36f1b3fc38676624193b5635250c57347cd2cd6d8357004` |
| 1.8.9 `RedSandstoneSlabBlock.java` | default state 24–34 | `07d59a26fc337cfb2533c9cf4b8f9cba0d3adaaac5ca96a50b6845f6c1845a64` |
| 1.8.9 `DoubleRedSandstoneSlabBlock.java` | `isDouble()` 3–8 | `71df9e432c231b3c6d0892a58957bbad1b2ece2daa3526a1d330b9944824b203` |
| 1.8.9 `SingleRedSandstoneSlabBlock.java` | `isDouble()` 3–8 | `80782d5bb0d60bc09e895efa4f09be5b321764f9e9f2dd7d2ad68ab192bb3b8a` |
| 1.8.9 `FenceGateBlock.java` | default `OPEN`/`POWERED`/`IN_WALL` 21–24; old predicates 73–81 | `43749004a2826051c627c794f72db705283df26e1810d8b4a7a78205ac974d2c` |
| 1.8.9 `PlanksBlock.java` | spruce variant registration dependency | `9d94a64ccdc7ebb4e817d6d05ad3fde47ad4b1cdfbb070b18026cccab96ef5be` |
| 1.9.4 `Block.java` | constructor/default state cache 174–181; registrations 1140–1152 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `SlabBlock.java` | `isSolidRender(state)` 47–50; `isCube(state)` 67–70 | `9c3d373497f5602d19bf8eb0aa59d670854ef209e8f73f946d5ba05496add00b` |
| 1.9.4 `RedSandstoneSlabBlock.java` | default state 25–35 | `46ece33f234b63e283ddd988b21e26e6fbd21bf4bc21d0d40d4c5ae4fec1f1d3` |
| 1.9.4 `DoubleRedSandstoneSlabBlock.java` | `isDouble()` 3–8 | `71df9e432c231b3c6d0892a58957bbad1b2ece2daa3526a1d330b9944824b203` |
| 1.9.4 `SingleRedSandstoneSlabBlock.java` | `isDouble()` 3–8 | `80782d5bb0d60bc09e895efa4f09be5b321764f9e9f2dd7d2ad68ab192bb3b8a` |
| 1.9.4 `FenceGateBlock.java` | default properties 30–34; new predicates 90–98 | `44ee568ddaf2e726b2017315d66b59fc013c093be4f0f2f23f669b0ca0e41366` |
| 1.9.4 `PlanksBlock.java` | spruce variant registration dependency | `9d94a64ccdc7ebb4e817d6d05ad3fde47ad4b1cdfbb070b18026cccab96ef5be` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` in the base constructor. In 1.9.4, it reads `block.defaultState().isCube()`, which dispatches through the state definition to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `double_stone_slab2`, ID 181 (`DoubleRedSandstoneSlabBlock`) | true | true | Both registries construct the same subclass. Its `isDouble()` is true; slab `isSolidRender()`/`isCube()` return that value. The parent's default state is `SEAMLESS=false`, `VARIANT=RED_SANDSTONE`; its cube predicates are independent of those state properties. | E in both versions; no changed mask. |
| `stone_slab2`, ID 182 (`SingleRedSandstoneSlabBlock`) | false | false | Both registries construct the same subclass. Its `isDouble()` is false; slab `isSolidRender()`/`isCube()` return that value. The parent's default state is `HALF=BOTTOM`, `VARIANT=RED_SANDSTONE`. | No E arm in either version; no changed mask. |
| `spruce_fence_gate`, ID 183 (`FenceGateBlock(PlanksBlock.Variant.SPRUCE)`) | false | false | Both registries use the spruce variant. The default `OPEN`, `POWERED`, and `IN_WALL` values are false. The class explicitly returns false from the old and new solid-render and cube predicates; these values do not depend on its state. | No E arm in either version; no changed mask. |

No provider in this slice changes the pane predicate result. The memo classifies only registration-level predicate results; it does not classify slab collision geometry, gate collision geometry, or block update/support lifecycles.

## Remaining census and next slice

This is the sixth additive three-registration memo, bringing the bounded dispositions to eighteen registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next bounded shared registrations, selected by ID and not evaluated here: `birch_fence_gate` ID 184, `jungle_fence_gate` ID 185, and `dark_oak_fence_gate` ID 186. Their registration/default-state dependencies, predicates, and reachable masks remain open. Blocks absent from 1.8.9 remain excluded from this shared-version comparison. Independent review, full provider closure, and original derived-JAR equivalence remain open.