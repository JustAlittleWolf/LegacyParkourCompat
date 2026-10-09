# Pane neighbor-provider slice 29: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-twenty-ninth`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registrations 944–955 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `StairsBlock.java` | defaults 37–40; false predicates 65–72 | `fadc964480227f6ffd5756471212b18d0172bffbcd0866ee0c278454ab079409` |
| 1.8.9 `ChestBlock.java` | default facing/type 34–36; false predicates 41–48 | `8072bc317398297d1327a3be4edd8a66381a1a1021a6c4a8f3fbeebca2891d51` |
| 1.8.9 `RedstoneWireBlock.java` | default state 37–44; false predicates 76–83 | `8a5115b44772531811908704f51b6fc233b89836b15440ca42138680fa8abbb2` |
| 1.9.4 `Block.java` | registrations 838–849 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `StairsBlock.java` | defaults 55–58; false predicates 139–146 | `ee143aced2a6902563cddbae77e78d1d1a512244f278987684ebe5bcacba37cb` |
| 1.9.4 `ChestBlock.java` | default facing/type 42–44; false predicates 48–55 | `0fc74197f3f4710268944bb802a1621dc8ee359c29289ec8ce878ad0b2b834ce` |
| 1.9.4 `RedstoneWireBlock.java` | default state 57–64; false predicates 135–142 | `834e8da7632a50c8d9008426870678a919dd3ed28ee1ef7cb1a1e0b8f86516ad` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` during base construction. In 1.9.4, it reads `block.defaultState().isCube()`, which delegates to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `oak_stairs`, ID 53 (`StairsBlock`) | false | false | Both defaults set `FACING=NORTH`, `HALF=BOTTOM`, `SHAPE=STRAIGHT`; the class explicitly returns false from old/new solid-render and cube predicates. | No E arm in either version; no changed mask. |
| `chest`, ID 54 (`ChestBlock`) | false | false | Both defaults set `FACING=NORTH`; the registrations select the basic chest type. The class explicitly returns false from old/new solid-render and cube predicates. | No E arm in either version; no changed mask. |
| `redstone_wire`, ID 55 (`RedstoneWireBlock`) | false | false | Both default states set `POWER=0` and each directional connection property to `ConnectionSide.NONE`; the class explicitly returns false from old/new solid-render and cube predicates. | No E arm in either version; no changed mask. |

No provider in this slice changes the pane connection outcome. The memo classifies registration-level predicate results and defaults only; stair collision shape, chest geometry and pairing, and redstone power behavior are outside scope.

## Remaining census and next slice

This is the twenty-ninth additive three-registration memo, bringing the bounded dispositions to eighty-six registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next lowest unclassified shared registrations, selected by registry ID and not evaluated here: `diamond_ore` ID 56, `diamond_block` ID 57, and `crafting_table` ID 58. Their registration/default-state dependencies, predicates, and reachable masks remain open. Independent review, full provider closure, and original derived-JAR equivalence remain open.