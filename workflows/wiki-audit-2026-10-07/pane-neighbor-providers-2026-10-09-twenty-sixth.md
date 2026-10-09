# Pane neighbor-provider slice 26: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-twenty-sixth`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registrations/instance 917–925 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `SingleStoneSlabBlock.java` | `isDouble() == false` 3–6 | `3b3808a68671d1f559f20ac6cc71a367223d50e9c646dbf470f2da3a503cca98` |
| 1.8.9 `StoneSlabBlock.java` | constructor/defaults 23–34 | `ce79650021c852ba1e70b3f9e5254cf00597a929154a7f10459db2e37d49fd76` |
| 1.8.9 `SlabBlock.java` | old solid-render and cube predicates 70–72, 90–92 | `35be26d8419f707ca36f1b3fc38676624193b5635250c57347cd2cd6d8357004` |
| 1.8.9 `TntBlock.java` | constructor/default `EXPLODE=false` 20–25 | `1c8e1f9453079db13e60b484e182c1813a054e81a7d4c524aba8ac54e1babed7` |
| 1.9.4 `Block.java` | registrations/instance 811–819 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `SingleStoneSlabBlock.java` | `isDouble() == false` 3–6 | `3b3808a68671d1f559f20ac6cc71a367223d50e9c646dbf470f2da3a503cca98` |
| 1.9.4 `StoneSlabBlock.java` | constructor/defaults 24–35 | `836c0f12f7aab9cb6dbfb84ba1a5a83f420c6260a4f423245f233d391fd9f933` |
| 1.9.4 `SlabBlock.java` | state-taking solid-render and cube predicates 48–50, 68–70 | `9c3d373497f5602d19bf8eb0aa59d670854ef209e8f73f946d5ba05496add00b` |
| 1.9.4 `TntBlock.java` | constructor/default `EXPLODE=false` 24–29 | `cc51fccb3ce88b2b2f3a9edb70bc2f74f04bf46845bdc65445e6cce614b90e14` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` during base construction. In 1.9.4, it reads `block.defaultState().isCube()`, which delegates to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `stone_slab`, ID 44 (`SingleStoneSlabBlock`) | false | false | Both defaults set `VARIANT=STONE`, `HALF=BOTTOM`; the class returns false from `isDouble()`, and SlabBlock derives old/new solid-render and cube predicates from that value. | No E arm in either version; no changed mask. |
| `brick_block`, ID 45 (base `Block(Material.STONE, MapColor.RED)`) | true | true | Both registries construct the base Block with no state properties or predicate override; the base old solid-render/cube and new state-taking cube predicates return true. | E arm in both versions; no changed mask. |
| `tnt`, ID 46 (`TntBlock`) | true | true | Both constructors set default `EXPLODE=false`. TntBlock does not override the pane-relevant old/new predicates, so the base true results apply. | E arm in both versions; no changed mask. |

No provider in this slice changes the pane connection outcome. The memo classifies registration-level predicate results and defaults only; slab placement/collision, brick state, and TNT priming or explosion behavior are out of scope.

## Remaining census and next slice

This is the twenty-sixth additive three-registration memo, bringing the bounded dispositions to seventy-seven registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next lowest unclassified shared registrations, selected by registry ID and not evaluated here: `bookshelf` ID 47, `mossy_cobblestone` ID 48, and `obsidian` ID 49. Their registration/default-state dependencies, predicates, and reachable masks remain open. Independent review, full provider closure, and original derived-JAR equivalence remain open.