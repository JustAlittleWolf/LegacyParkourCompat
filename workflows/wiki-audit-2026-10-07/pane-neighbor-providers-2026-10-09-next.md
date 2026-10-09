# Pane neighbor-provider slice 2: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-next`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the same canonical ready roots and immutable Feather revision as [the preceding provider slice](pane-neighbor-providers-2026-10-09.md): `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Their source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

The verification records continue to report identical source trees and raw input artifacts. The original derived JARs are unavailable; the regenerated immutable JARs do not prove equivalence to those originals or show that the prior hash difference is metadata-only. This limitation remains as recorded in the preceding provider slice.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | `isOpaque()` 154–156; constructor 198–207; registrations 1012, 1188–1189 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `CactusBlock.java` | default state 20–24; `isCube()`/`isSolidRender()` 63–71 | `9724244c881244e4bd050524bf6179c276fe49129f136164fb0eeeba18a791cd` |
| 1.8.9 `BarrierBlock.java` | constructor and `isSolidRender()` 8–25 | `12f92237924f64d44f327a65916c65b862cfea03f73a5048a68029977bcd493b` |
| 1.8.9 `TrapdoorBlock.java` | default state 22–34; predicates 36–44 | `e00c4fdfa234ac5cf265816fbbe088709668cd5a44eb537a1f391ce2f5f795ba` |
| 1.9.4 `Block.java` | constructor 174–181; base `isCube(state)` 222–225; registrations 913, 1107–1108 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `CactusBlock.java` | default state 22–26; `isCube(state)`/`isSolidRender(state)` 63–71 | `b03df2d440ac215902606d6d0f058d3684bb720aea5c6a4f19261d3ab09b7a49` |
| 1.9.4 `BarrierBlock.java` | constructor and `isSolidRender(state)` 9–26 | `57b4fb0ef1f0feda7dec76d62136e8cf302548cb5c9cd47ebd55d66db114b94b` |
| 1.9.4 `TrapdoorBlock.java` | default state 23–40; predicates 69–77 | `bbfbeac22de7e72b55736a35c29c10f372df1d846dd01cd16d7ee2a0913b2339` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, `PaneBlock` tests `block.isOpaque()`, which returns the `opaqueCube` value cached by the `Block` constructor from virtual `isSolidRender()`. In 1.9.4, it tests `block.defaultState().isCube()`; `StateDefinition` dispatches that to `block.isCube(state)`. Both pane paths resolve the four cardinal neighbors, and the new predicate tests the provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependencies | East-only pane result with other directions absent |
|---|---:|---:|---|---|
| `cactus`, ID 81 (`CactusBlock`) | false | false | Both registries instantiate `CactusBlock`; default `AGE=0`. Its `isSolidRender()`/`isCube()` (and state-taking equivalents) return false. | No E arm under either predicate; no changed mask. |
| `barrier`, ID 166 (`BarrierBlock`) | false | true | Both registries instantiate `BarrierBlock`, which declares no block-state properties. Its old `isSolidRender()` returns false and is cached as `opaqueCube=false`. In 1.9.4 it overrides only `isSolidRender(state)`; base `Block.isCube(state)` remains true. | Reachable predicate delta: an east-adjacent barrier yields no E arm in 1.8.9 and the E-only pane mask in 1.9.4. |
| `iron_trapdoor`, ID 167 (`TrapdoorBlock(Material.IRON)`) | false | false | Both registries construct the shared `TrapdoorBlock` class with iron material. Its default is north-facing, closed, bottom-half. Old and new implementations both explicitly return false from their cube predicates; those default properties do not change the result. | No E arm under either predicate; no changed mask. |

These dispositions cover only the pane predicate. They do not inspect block survival, update, or placement lifecycles, and do not imply that any other provider state or shape path has been closed.

## Remaining census and next slice

The two bounded provider memos classify six shared registrations total. Every other era-existing registration remains unclassified for the `isOpaque()` versus default-state `isCube()` comparison, and changed classifications still need cardinal-mask coverage. Explicit pane/glass allowlist cases remain separate; blocks without a 1.8.9 counterpart remain outside this shared-version comparison.

Next bounded shared-registration slice, selected by registry ID and not evaluated here: `prismarine` ID 168, `sea_lantern` ID 169, and `carpet` ID 171. Their class predicates, construction defaults, and reachable masks remain open. Full provider census, complete reachable-mask set, independent review, and original derived-JAR equivalence remain open.
