# Pane neighbor-provider slice 17: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-seventeenth`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | base predicates 245–247, 352–354; registrations 859–862 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `OreBlock.java` | default stone material constructor 15–24; no predicate overrides | `89e25ca382a879cc6c3eb4244450122798619462681b9cf246ea999c49db9144` |
| 1.8.9 `LogBlock.java` | default oak variant/Y axis 13–24 | `ea2fe5825a60706cb6d3649aef829bcb02b3b81142bb19bc3579b17da6142508` |
| 1.8.9 `AbstractLogBlock.java` | axis-block inheritance and wood material 13–20 | `a28eb4c72e8ded3da6f6fb0fee636af44a9fc72694babf1cc4ef902dcf4bb165` |
| 1.8.9 `AxisBlock.java` | inherits Block; constructors 8–17; no predicate overrides | `6e9b75057943373f31baab674d7f7e9cccbe1fd5efdde9d44c9261bb7ba93036` |
| 1.8.9 `SpongeBlock.java` | default `WET=false` 22–29; no predicate overrides | `afd03e8371749c023978f73459f50cf6c73ee65e7df26ebb0caf72de21634d2d` |
| 1.9.4 `Block.java` | base predicates 222–225, 357–360; registrations 751–754 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `OreBlock.java` | default stone material constructor 17–25; no predicate overrides | `d178967a8bebfdb85bcfdfd57b676582838454f2a3a339bb96b65de22b789a45` |
| 1.9.4 `LogBlock.java` | default oak variant/Y axis 14–25 | `bc40f04c5ccc70552f23337454361112c8dac1504a654693c341252a169daee6` |
| 1.9.4 `AbstractLogBlock.java` | axis-block inheritance and wood material 14–21 | `f219ee05d89892084dfe202560bd248164026a8ff1da618bef0c02d1ba572387` |
| 1.9.4 `AxisBlock.java` | inherits Block; constructors 15–24; no predicate overrides | `043094888447998c451a572d303cdf2c572d7760e311573f7ce12c91e5ceed4a` |
| 1.9.4 `SpongeBlock.java` | default `WET=false` 21–28; no predicate overrides | `3e7385352008c3303c222544bcb9c8ccee3c7b6f14d7095145bea7a678eff4a1` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` in the base constructor. In 1.9.4, the pane predicate reads `block.defaultState().isCube()`, which dispatches through the state definition to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `coal_ore`, ID 16 (`OreBlock`) | true | true | Both registries construct the same `OreBlock` with default `Material.STONE`; it has no provider state or predicate override and inherits true base predicates. | E in both versions; no changed mask. |
| `log`, ID 17 (`LogBlock` → `AbstractLogBlock` → `AxisBlock`) | true | true | Both registries construct the same log class. Default `VARIANT=OAK`, `LOG_AXIS=Y`; neither log nor its axis-block ancestors override the base predicates. | E in both versions; no changed mask. |
| `sponge`, ID 19 (`SpongeBlock`) | true | true | Both registries construct the same sponge class. Default `WET=false`; it has no predicate override and inherits true base behavior. | E in both versions; no changed mask. |

No provider in this slice changes the pane predicate result. The memo classifies registration-level predicate results and defaults only; it does not classify log orientation geometry or sponge/water lifecycle behavior.

## Remaining census and next slice

This is the seventeenth additive three-registration memo, bringing the bounded dispositions to fifty registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next lowest unclassified shared registrations, selected by registry ID and not evaluated here: `glass` ID 20, `lapis_ore` ID 21, and `lapis_block` ID 22. Glass is in the pane's explicit allowlist; its underlying old/new opacity/cube result must still be classified separately from the overall allowlist outcome. Independent review, full provider closure, and original derived-JAR equivalence remain open.