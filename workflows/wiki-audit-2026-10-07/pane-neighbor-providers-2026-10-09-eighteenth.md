# Pane neighbor-provider slice 18: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-eighteenth`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | base predicates 245–247, 352–354; registrations 863–873 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolver 100–108; predicate/explicit allowlist 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `GlassBlock.java` | `isCube()` 24–27 | `a7c3403c4e6d0d0014ef722755962f5f449071bb2ebffe0fd2d200bd5cd42144` |
| 1.8.9 `TransparentBlock.java` | `isSolidRender()` 22–24 | `10780460a36e5e73d61381cd91b05a988e30a67e71fc182875a52bdcf387d244` |
| 1.8.9 `OreBlock.java` | default stone material constructor 15–24; no predicate overrides | `89e25ca382a879cc6c3eb4244450122798619462681b9cf246ea999c49db9144` |
| 1.9.4 `Block.java` | base predicates 222–225, 357–360; registrations 755–765 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolver 104–110; predicate/explicit allowlist 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `GlassBlock.java` | state-taking `isCube()` 25–28 | `554a0884bfef7bc61b4a2535193037dfb0de0e338227ae0335dbd6432ef62471` |
| 1.9.4 `TransparentBlock.java` | state-taking `isSolidRender()` 22–24 | `b688c54180a7f67fad3a4a9a2e8e50d3b0d247c382800039f5d7c3d08ea0288b` |
| 1.9.4 `OreBlock.java` | default stone material constructor 17–25; no predicate overrides | `d178967a8bebfdb85bcfdfd57b676582838454f2a3a339bb96b65de22b789a45` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane first accepts opaque blocks, then its explicit glass/pane allowlist. In 1.9.4, it first accepts a default-state cube, then its explicit glass/pane allowlist. The latter tests `block.defaultState().isCube()`, which dispatches through the state definition to `Block.isCube(state)`. Both resolvers inspect all four cardinal directions.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `glass`, ID 20 (`GlassBlock(Material.GLASS, false)`) | false | false | Both registries construct the same glass class. `TransparentBlock.isSolidRender()` makes cached old opacity false; `GlassBlock` overrides old and new cube predicates to false. The pane allowlist separately accepts the registered glass block in both versions. | E in both versions via explicit allowlist; no changed mask. |
| `lapis_ore`, ID 21 (`OreBlock`) | true | true | Both registries construct the same `OreBlock` with default `Material.STONE`; no provider state or predicate override, so it inherits true base predicates. | E in both versions; no changed mask. |
| `lapis_block`, ID 22 (`Block(Material.IRON, MapColor.LAPIS)`) | true | true | Both registries construct the same generic block with no state properties or predicate override; it inherits true base predicates. | E in both versions; no changed mask. |

No provider in this slice changes the pane connection outcome. Glass demonstrates why the raw cube result and the explicit allowlist must be reported separately. The memo does not classify glass rendering or lapis block material behavior.

## Remaining census and next slice

This is the eighteenth additive three-registration memo, bringing the bounded dispositions to fifty-three registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next lowest unclassified shared registrations, selected by registry ID and not evaluated here: `dispenser` ID 23, `sandstone` ID 24, and `noteblock` ID 25. Their registration/default-state dependencies, predicates, and reachable masks remain open. Independent review, full provider closure, and original derived-JAR equivalence remain open.