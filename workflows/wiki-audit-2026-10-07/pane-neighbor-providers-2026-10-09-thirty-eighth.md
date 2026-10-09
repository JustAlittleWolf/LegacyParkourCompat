# Pane neighbor-provider slice 38: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-thirty-eighth`

Status: three shared registrations classified from exact source; independent review pending. This additive memo does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This memo uses canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report ready. The source-manifest SHA-256 values are `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files). Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that recorded hash differences are metadata-only.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registrations 74–76 at 997–1007; old opacity/cache 154–155, 202–206 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | four-direction resolver 35–39; connection predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `RedstoneOreBlock.java` | class/constructor and `lit` field 16–24 | `9b2efe542205a9ccba4f86f472f973a1beab413f3de3773dd28f4bf6915911db` |
| 1.8.9 `RedstoneTorchBlock.java` | subclass/`lit` field 16–18; constructor 44–47 | `e85ac1cd0988ea3657c32623206c72b8131fa30e3dde275e8b9e1610cdbafa70` |
| 1.8.9 `TorchBlock.java` | default facing and false predicates 27–47 | `20ac458631cdf68b24e739ac100616bb024ec2200262a4de66ad5c59e7967a5b` |
| 1.9.4 `Block.java` | registrations 74–76 at 893–907; default opacity 112–114, 179–181 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | four-direction resolver 106–110; connection predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `RedstoneOreBlock.java` | class/constructor and `lit` field 18–26 | `dcc46f1d6ce5cb4eaafdd9ff31eb8e18543714db832cd27b391ddcc50d8b7eae` |
| 1.9.4 `RedstoneTorchBlock.java` | subclass/`lit` field 20–22; constructor 48–52 | `19faa81c850e46f91a64368fc2c1be51f196bb79d0eb21f29c2eccd874ecaaff` |
| 1.9.4 `TorchBlock.java` | default facing and false predicates 32–69 | `f4d7573ee4b16e9d293f1209f1edf8d6a05588fa9ba614fb927bfaa274ca7955` |
| 1.9.4 `block/state/StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, pane resolution tests each neighbor with `block.isOpaque()`. The base constructor caches `opaqueCube` from virtual `isSolidRender()`, and `isOpaque()` returns that value. In 1.9.4, the pane tests `block.defaultState().isCube()`; resolved states delegate to `Block.isCube(state)`. The pane resolver sets north, south, west, and east properties from those cardinal tests. None of these registrations is in the pane's explicit same-pane/glass allowlist.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `lit_redstone_ore`, ID 74 (`new RedstoneOreBlock(true)`) | true | true | RedstoneOreBlock extends Block and declares no solid-render or cube predicate override. The constructor's `lit=true` field and registry light setter do not alter the inherited provider predicates. | E arm in both versions; no changed mask. |
| `unlit_redstone_torch`, ID 75 (`new RedstoneTorchBlock(false)`) | false | false | RedstoneTorchBlock extends TorchBlock and carries a constructor `lit=false` field. TorchBlock sets default `FACING=UP` and explicitly returns false from both old and state-taking solid-render and cube predicates; the lit flag does not alter them. | No E arm in either version; mask remains empty. |
| `redstone_torch`, ID 76 (`new RedstoneTorchBlock(true)`) | false | false | The registration differs from ID 75 in the constructor lit flag and light setter. The inherited TorchBlock default is `FACING=UP`, and its old and state-taking predicates return false independently of the lit flag. | No E arm in either version; mask remains empty. |

For each witness, the provider is immediately east of the pane while north, south, and west are air. Lit Redstone Ore returns true in both versions and yields the E arm in both. Both redstone torch registrations return false in both versions and yield no arm. The batch has no connection-outcome or mask delta. This memo covers only registration-level predicate results and these directional witnesses; redstone tick behavior, torch power, light, collision, and player movement are outside scope.

## Remaining census and next slice

This is the thirty-eighth additive three-registration memo. The bounded inventory now records 107 classified registrations, still partial. The next lowest unclassified shared registrations, verified in both registries but not classified here, are `stone_button` ID 77, `snow_layer` ID 78, and `ice` ID 79. Their predicates/defaults and east-neighbor masks remain open. Independent reviews for slices 36–38, complete provider closure, all world-reachable pane masks, and original derived-JAR equivalence remain open.
