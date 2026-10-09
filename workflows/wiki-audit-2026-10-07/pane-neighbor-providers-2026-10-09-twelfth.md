# Pane neighbor-provider slice 12: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-twelfth`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. Source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files); artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that their recorded hash differences are metadata-only. Preserve this caveat in every bounded provider memo.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | base `isCube()` 245–247; base `isSolidRender()` 352–354; registrations 827–829 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `StoneBlock.java` | default `VARIANT=STONE` 17–24; no predicate overrides | `ca15180da4a946055afe68f8c6c578070fae700d13b50b4015272d772ddeed23` |
| 1.8.9 `GrassBlock.java` | default `SNOWY=false` 17–25; no predicate overrides | `f1121c1d84909e1d553bd9d3e84b41eddb841f4ad709cbf377179dcdca7a2b46` |
| 1.8.9 `DirtBlock.java` | default `VARIANT=DIRT`, `SNOWY=false` 18–26; no predicate overrides | `7eace0ea43e8e32c848fb69dfe773b67cf2cb5c6c2e22ee766d8180af6927ee4` |
| 1.9.4 `Block.java` | base `isCube(state)` 222–225; base `isSolidRender(state)` 357–360; registrations 719–721 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `StoneBlock.java` | default `VARIANT=STONE` 17–24; no predicate overrides | `3a5fe9daed1a7cf39f2c15c14a2c9f8852a2ec9e195a3937ce5825293cc84967` |
| 1.9.4 `GrassBlock.java` | default `SNOWY=false` 16–24; no predicate overrides | `b07917abd30d51ce700f982503c5c54aa4d83db5e33996fb339a0cefc0127a1e` |
| 1.9.4 `DirtBlock.java` | default `VARIANT=DIRT`, `SNOWY=false` 18–26; no predicate overrides | `bebd11193fc7ae4e2ce5488d502b47da1e2e587631b67bb2c5108c08aa5d4619` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, the pane predicate reads `Block.isOpaque()`, backed by `opaqueCube` cached from virtual `isSolidRender()` in the base constructor. In 1.9.4, it reads `block.defaultState().isCube()`, which dispatches through the state definition to `Block.isCube(state)`. Both pane resolvers inspect all four cardinal directions, and the new predicate uses each provider's default state.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `stone`, ID 1 (`StoneBlock`) | true | true | Both registries construct the same stone class. Default `VARIANT=STONE`; it inherits base true `isSolidRender()`/`isCube()` and base true `isCube(state)` behavior. | E in both versions; no changed mask. |
| `grass`, ID 2 (`GrassBlock`) | true | true | Both registries construct the same grass class. Default `SNOWY=false`; it has no predicate overrides and inherits the true base behavior in both versions. | E in both versions; no changed mask. |
| `dirt`, ID 3 (`DirtBlock`) | true | true | Both registries construct the same dirt class. Default `VARIANT=DIRT`, `SNOWY=false`; it has no predicate overrides and inherits the true base behavior in both versions. | E in both versions; no changed mask. |

No provider in this slice changes the pane predicate result. The memo classifies registration-level predicate results and their constructors/default states; it does not classify grass/dirt tick, fertility, or growth behavior.

## Remaining census and next slice

This is the twelfth additive three-registration memo, bringing the bounded dispositions to thirty-five registrations. The unclassified remainder of the shared registries still requires exact old-opacity/default-state-cube results and directional-mask coverage; no full census or complete reachable-mask set is claimed.

Next lowest unclassified shared registrations, selected by registry ID and not evaluated here: `cobblestone` ID 4, `planks` ID 5, and `sapling` ID 6. Their exact provider classes, defaults, predicates, and reachable masks remain open. Independent review, full provider closure, and original derived-JAR equivalence remain open.