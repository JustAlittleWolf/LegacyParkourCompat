# Pane neighbor-provider slice 28: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-twenty-eighth`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registrations 938–940 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `TorchBlock.java` | default `FACING=UP` 28–29; false predicates 40–46 | `20ac458631cdf68b24e739ac100616bb024ec2200262a4de66ad5c59e7967a5b` |
| 1.8.9 `FireBlock.java` | defaults 59–63; false predicates 124–131 | `ee65eb7b3256346282e3d17d51d8a92e6abb5ac08ef02b8a1e533989e056429d` |
| 1.8.9 `MobSpawnerBlock.java` | constructor 13–16; false solid-render override 40–43 | `5e824e03317356d778080b601181c97fde73f2a81eb75159d596c42033530876` |
| 1.8.9 `Block.java` | base cube predicate 245–247 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.9.4 `Block.java` | registrations 832–834; base cube predicate 223–225 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `TorchBlock.java` | default `FACING=UP` 33–34; false predicates 62–69 | `f4d7573ee4b16e9d293f1209f1edf8d6a05588fa9ba614fb927bfaa274ca7955` |
| 1.9.4 `FireBlock.java` | defaults 46–47; false predicates 103–110 | `48b734bf3665b9c743c05c176bb1208992e357f376fefcb1b6d337dade5a2c29` |
| 1.9.4 `MobSpawnerBlock.java` | constructor 16–19; false solid-render override 44–47 | `5e4a4c12c3cba9734dec8b1b4c8d1fe02a269b678b75abf6643b5dc650252862` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` during base construction. In 1.9.4, it reads `block.defaultState().isCube()`, which delegates to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `torch`, ID 50 (`TorchBlock`) | false | false | Both defaults set `FACING=UP`; the class explicitly returns false from old/new solid-render and cube predicates. | No E arm in either version; no changed mask. |
| `fire`, ID 51 (`FireBlock`) | false | false | Both defaults use `AGE=0` with their directional connection flags false; the class explicitly returns false from old/new solid-render and cube predicates. | No E arm in either version; no changed mask. |
| `mob_spawner`, ID 52 (`MobSpawnerBlock`) | false | true | Both registrations construct the same class with `Material.STONE`. It overrides old/new `isSolidRender()` to false but does not override `isCube()`; 1.8.9 therefore caches opacity false, while 1.9.4 inherits base `Block.isCube(state) == true`. | No E arm in 1.8.9; E arm in 1.9.4 for an east-only neighbor. |

Mob Spawner is a changed provider witness under the predicate replacement: the same registered class changes from rejected by old cached `isOpaque()` to accepted by the new default-state cube test, adding the east pane arm in the stated arrangement. Torch and Fire do not change the pane outcome. The memo classifies only registration-level predicate results and one directional mask; light, fire spread, block-entity behavior, and collision shape are outside scope.

## Remaining census and next slice

This is the twenty-eighth additive three-registration memo, bringing the bounded dispositions to eighty-three registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next lowest unclassified shared registrations, selected by registry ID and not evaluated here: `oak_stairs` ID 53, `chest` ID 54, and `redstone_wire` ID 55. Their registration/default-state dependencies, predicates, and reachable masks remain open. Independent review, full provider closure, and original derived-JAR equivalence remain open.