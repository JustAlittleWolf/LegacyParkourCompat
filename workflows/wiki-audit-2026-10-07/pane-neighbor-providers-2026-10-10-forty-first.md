# Pane neighbor-provider slice 41: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-10-forty-first`

Status: three shared registrations classified from exact source; independent review pending. This additive memo does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This memo uses canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report ready. The source-manifest SHA-256 values are `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248a37a27a1b4d77` (1,819 files). The canonical 1.9.4 manifest file and ready marker instead hash to `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; the distinct value printed in the frozen slice 40 review is corrected by accepted metadata-only event `25e5e107a05d5605ecd34f409c6fd746a6edc01a`. This memo uses the verified canonical value. Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that recorded hash differences are metadata-only.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registrations 83–86 at 1014–1022; old opacity/predicate defaults 154–155, 199–207, 245–247, 352–354; default collision box 346–349 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor mask resolution 34–39; collision/outline shaping 62–131; connection predicate 134–141 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `SugarCaneBlock.java` | default AGE and outline bounds 17–25; null collision shape 90–93; false predicates 100–108 | `6d6f21e4d4aec3ba8eaa5980265db1b45a1f2326a745ff758ff660958e295ca2` |
| 1.8.9 `JukeboxBlock.java` | default HAS_RECORD 20–27; state definition 125–128 | `546e3933a4876aa8ccfff8834ade5a1ae432cadcf9b692964465361b4a5fda89` |
| 1.8.9 `BlockWithBlockEntity.java` | parent class/constructor 12–20; no predicate or shape override | `9126813af904415188c993830f592326fd78f1b9c9e6224373256f6ac23ce175` |
| 1.8.9 `FenceBlock.java` | default directions 19–33; collision and outline bounds 35–110; false predicates 113–121 | `8d6a803988d77fc51f364520f2e0cd5567b1d5c29a4d28d01cdcf93e6d5890d9` |
| 1.9.4 `Block.java` | registrations 83–86 at 915–927; default predicate initialization 175–181, 223–225, 357–359; default collision shape 352–353 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | shape table and mask mapping 22–101; neighbor mask resolution 104–110; connection predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `SugarCaneBlock.java` | default AGE and outline shape 19–32; empty collision shape 99–103; false predicates 111–119 | `2296213eed8c853a81447288233a1aee30fa97bbd81ee235112debdd7b416eca` |
| 1.9.4 `JukeboxBlock.java` | default HAS_RECORD 23–30; state definition 140–142 | `893726a800dc15ec64098734e12f6421be1cd2789981bcca13609b71d9827f31` |
| 1.9.4 `BlockWithBlockEntity.java` | parent class/constructor 13–21; no predicate or shape override | `94455367c7160e48d0f8bf3b2d49265bc8ba1365f3ae3ac91bc2770e4de30816` |
| 1.9.4 `FenceBlock.java` | default directions and shape tables 22–55; collision/outline dispatch 58–101; false predicates 105–113 | `d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909` |

## Predicate, shape, and mask dispositions

In 1.8.9, pane connection tests `block.isOpaque()`. The base constructor initializes cached `opaqueCube` from `isSolidRender()`, and `isOpaque()` returns that cache. In 1.9.4, the resolver tests `block.defaultState().isCube()`, which dispatches to `Block.isCube(state)`. The explicit pane/glass allowlist includes none of Reeds, Jukebox, or Fence.

The assigned pointer expected ID 85 to be absent in both registries. The exact registry source disproves that expectation: both register `fence` as ID 85, so Fence is classified here as the third provider. The unchanged slice 40 memo still has its prior bytes; this memo and the checkpoint/index continuation record the corrected pointer without rewriting that memo.

| Registration | Registration/default input | Provider shape evidence | 1.8.9 `isOpaque()` → 1.9.4 default-state `isCube()` | One-east-neighbor pane mask |
|---|---|---|---|---|
| `reeds`, ID 83 (`new SugarCaneBlock()`) | Both default states set `AGE=0`. | The outline bounds are x/z 0.125–0.875 and y 0–1 in both versions (old `setShape`; new `SHAPE`/`getShape`). Collision source returns `null` in 1.8.9 and `EMPTY_BLOCK_SHAPE` in 1.9.4. | Explicit false `isSolidRender()` makes the old cached `isOpaque()` false → explicit state-taking `isCube(state)` false. | N/E/S/W all false in both; no E arm. |
| `jukebox`, ID 84 (`new JukeboxBlock()`) | Both default states set `HAS_RECORD=false`; the class inherits from `BlockWithBlockEntity`, which adds block-entity status but no shape/predicate override. | Inherits Block's full-cube default outline/collision shape in both source versions. | Inherited base `isSolidRender()` true yields old `isOpaque()` true → inherited base `isCube(state)` true. | E true, other directions false, in both; E arm. |
| `fence`, ID 85 (`new FenceBlock(Material.WOOD, oakColor)`) | Both defaults set NORTH/EAST/SOUTH/WEST false. Registration is present in both `Block.java` registries at 1016–1020 and 917–925. | Both define a centered post outline up to y=1 and collision post up to y=1.5; neighbor arms are added from resolved fence directions. 1.8.9 builds bounds dynamically; 1.9.4 selects predeclared outline/collision boxes. | Explicit false `isSolidRender()` makes the old cached `isOpaque()` false → explicit state-taking `isCube(state)` false. | N/E/S/W all false in both; no E arm. |

Each pane witness places the candidate immediately east of the pane and air north, south, and west. The pane resolver produces the same directional mask and arm geometry in both versions for each provider. There is no connection-outcome delta in this slice. This memo is limited to registration/default inputs, the cited source shape behavior, and these pane-mask witnesses; unrelated growth, jukebox interaction/audio, fence attachment rules beyond the cited default shape, and player movement are outside scope.

## Review reconciliation and remaining census

Slice 38's original report verdict is **ACCEPT**. Its wrong memo identity was corrected metadata-only by accepted event `b319cd54d1b22c3e1c8b5051bb9b02d3a9d2ad6c`; the original report and memo remain byte-for-byte unchanged. Slice 39 is **ACCEPT**, bounded to IDs 77–79 and their east-only witnesses. Slice 40 is **ACCEPT**, bounded to IDs 80–82 and their witnesses; accepted event `25e5e107a05d5605ecd34f409c6fd746a6edc01a` corrects only the 1.9.4 manifest value in its original review report. Both original review reports remain unchanged.

This is the forty-first additive memo and adds three classified registrations to the partial census, now 116. The next lowest unclassified shared registration is Pumpkin (ID 86), verified in both registries. Complete provider closure, all world-reachable pane masks, and original derived-JAR equivalence remain open.