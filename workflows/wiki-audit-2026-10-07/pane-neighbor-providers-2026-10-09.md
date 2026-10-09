# Pane neighbor-provider slice: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09`

Status: three registered provider classes classified from exact source; independent review pending. This bounded result does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

The canonical ready roots are `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../ready/1.9.4/ornithe-feather`. Both ready records say `ready`. Their source-manifest SHA-256 values are `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 source files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 source files). Their artifact-manifest SHA-256 values are `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

The ready verification records report `verified-identical-source-and-input-artifacts`, zero source-file differences, and identical raw input artifacts. Revision `feather-r1-2026-10-07` binds the same source and original artifact manifests to immutable derived JAR snapshots (`5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` and `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`). The original derived JARs are unavailable, and the regenerated snapshots differ from the old recorded derived hashes. Source and raw-input identity therefore do not prove original JAR equivalence or that the hash difference is metadata-only.

Every source-file SHA-256 below was recomputed from the ready tree and matches that version's `ornithe-feather.sources.sha256` entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | `isOpaque()` 154–156; constructor 198–207; base `isCube()` 245–247; registrations 861, 1181, 1187 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | four-neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `AbstractLeavesBlock.java` | inheritance/constructor 18–32; solid-render predicate and culling setter 212–220 | `5f6d40d382f6622757437de1ce9bae916a6cfe9bfbbcf2b46817447e5074614e` |
| 1.8.9 `LeavesBlock.java` | default state 29–31 | `6a9202c3ebf53e7a0996173c4b201f9325782a97e7d33877fb522afc6ac35352` |
| 1.8.9 `Leaves2Block.java` | default state 27–29 | `7c38aca6c5fb3902eb1724f5693cc6c3d976bea86f3833fac56a953b65e5e208` |
| 1.8.9 `SlimeBlock.java` | constructor 11–16 | `fa00f5b8903fa580e860cd72cfc11827a06eaad4600d05bd0f209849b04cbafa` |
| 1.8.9 `TransparentBlock.java` | constructor and `isSolidRender()` 17–25 | `10780460a36e5e73d61381cd91b05a988e30a67e71fc182875a52bdcf387d244` |
| 1.8.9 `TranslucentBlock.java` | constructor and `isSolidRender()` 11–19 | `8f8845d3da95b878038f0b7cd9b51ebee5b2b075f1fd7bea9719f94c33b476a0` |
| 1.9.4 `Block.java` | constructor 174–181; default `isCube(state)` 222–225; registrations 753, 1100, 1106 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolver 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `AbstractLeavesBlock.java` | inheritance/constructor 19–32; solid-render predicate 199–205 | `c301c1dc31e5dccae557607c7ec546741ec6b862fc25f5bf3d6ad73fea5198f6` |
| 1.9.4 `LeavesBlock.java` | default state 28–30 | `2c5ef31d24e4e875808b1198b9eeb32ca26f773b1998bba13a8c8f3711b9a5f3` |
| 1.9.4 `Leaves2Block.java` | default state 28–30 | `b3b8d4a122ce74e61d5d7c21de6b63d64a9e85a324cbc9d267130ae95a310902` |
| 1.9.4 `SlimeBlock.java` | constructor 12–17 | `38e77cdaaf3681fe3a2357ebc0dce7a86d658429463bc1e06e6e1167fe627c8a` |
| 1.9.4 `TransparentBlock.java` | constructor and `isSolidRender(state)` 17–25 | `b688c54180a7f67fad3a4a9a2e8e50d3b0d247c382800039f5d7c3d08ea0288b` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegation, 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask path

In 1.8.9, `PaneBlock` tests `block.isOpaque()`. `Block.isOpaque()` returns the instance's cached `opaqueCube`, which the base constructor assigns from virtual `isSolidRender()` before creating the default state. In 1.9.4, pane resolution tests `block.defaultState().isCube()`. `StateDefinition` delegates this to `block.isCube(state)`, and the base 1.9.4 `Block.isCube(state)` returns true. Both pane implementations inspect the four cardinal neighbors. The 1.9.4 predicate examines the provider's default state, not the actual adjacent world's property values.

| Registered provider | 1.8.9 predicate result | 1.9.4 default-state result | Default/registration dependency | Reachable pane mask |
|---|---|---|---|---|
| `leaves`, ID 18 (`LeavesBlock`) | true | true | `AbstractLeavesBlock`'s inherited `isSolidRender()` returns `!culling`; the field is false during the base constructor call, so cached `opaqueCube` is true. Defaults: oak, `CHECK_DECAY=true`, `DECAYABLE=true`. The 1.9.4 class inherits base `isCube(state)=true`; the same property defaults do not override it. | No predicate delta. An east-adjacent leaves block yields E in both versions. |
| `leaves2`, ID 161 (`Leaves2Block`) | true | true | Same inherited constructor path; defaults are acacia, `CHECK_DECAY=true`, `DECAYABLE=true`. The 1.9.4 class also inherits base `isCube(state)=true`. | No predicate delta. An east-adjacent leaves2 block yields E in both versions. |
| `slime`, ID 165 (`SlimeBlock`) | false | true | Both registries construct `SlimeBlock` over `TransparentBlock`. Its `isSolidRender()` override returns false during the old `Block` constructor, so cached `opaqueCube` is false. In 1.9.4 `TransparentBlock` still overrides only `isSolidRender(state)`; `SlimeBlock` inherits base `isCube(state)=true`. | Predicate delta is reachable: with slime east and air north/south/west, 1.8.9 resolves no E arm and 1.9.4 resolves the E-only pane mask. |

Leaves' culling/render flag is separate from the selected pane predicate: the old `isOpaque()` reads the constructor-cached field, while the new predicate dispatches to `isCube(state)`. The pane mask statements above use only the selected registered provider in one cardinal direction; they do not repeat the prior Ice, End Portal Frame, or fixed-mask witnesses.

## Remaining census and next slice

This slice closes only the three dispositions in the table. The exact remaining dependency is the unclassified remainder of each version's registered `Block` registry: compare each era-existing registration's old `isOpaque()` result with its 1.9.4 default-state `isCube()` result, including inherited implementations and class defaults, then map changed results into cardinal pane masks. Do not count explicit pane/glass cases as changed-predicate witnesses, and do not add blocks without a 1.8.9 counterpart to the shared-version comparison.

Next bounded source slice, selected by shared registry ID and not evaluated in this snapshot: `cactus` ID 81, `barrier` ID 166, and `iron_trapdoor` ID 167. Continue using only the exact ready roots and record each class's full constructor/default-state and predicate dependency before assigning a mask disposition. Provider census, complete reachable-mask set, independent review, and original derived-JAR equivalence remain open.
