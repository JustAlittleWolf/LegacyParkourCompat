# Pane neighbor-provider slice 36: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-thirty-sixth`

Status: three shared registrations classified from exact source; independent review pending. This additive memo does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This memo uses canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report ready. The source-manifest SHA-256 values are `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files). Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that recorded hash differences are metadata-only.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registrations 68–70 at 968–977; old opacity/cache 154–155, 202–206 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | four-direction resolver 35–39; connection predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `WallSignBlock.java` | subclass/default facing 11–16 | `5fa003c5ca40bfa2c901e35f8fa21fde096264038c8150780a2e49f8f24e97b2` |
| 1.8.9 `SignBlock.java` | false predicates 36–38, 46–49 | `7ccc9f3da44069426c3fc808e97631a56b8ad10045b89459219b2c2d062e81f6` |
| 1.8.9 `LeverBlock.java` | default state 22–25; false predicates 33–40 | `d8359d88616d0bd09462f90e98d5f4bdb76bf30b991ce841faf068424f0b5881` |
| 1.8.9 `PressurePlateBlock.java` | constructor/default powered and activation rule 18–22 | `2a78b32351f803b1f48164dde6270cd96f60acfecebebb146fe6358ba7d3e84a` |
| 1.8.9 `AbstractPressurePlateBlock.java` | false predicates 51–58 | `85896b070be577d228e8a660f1f95cae8956467a957463c4fc839080d2e5b1cb` |
| 1.8.9 `RedstoneOreBlock.java` | class, constructor and lit flag 16–24 | `9b2efe542205a9ccba4f86f472f973a1beab413f3de3773dd28f4bf6915911db` |
| 1.9.4 `Block.java` | registrations 68–70 at 864–873; opacity/default 112–114, 179–181; base `isCube` 223–225 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | four-direction resolver 106–110; connection predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `WallSignBlock.java` | subclass/default facing 12–21 | `bf90a4ca42cb61baee9051e80fb9e774472ee61f6c46aeb03cf26b18368935c5` |
| 1.9.4 `SignBlock.java` | false predicates 38–40, 49–51 | `09deedec28a527b42f45e9e352f2ea3a6e1efd95769a53121945cfdd5009c2d1` |
| 1.9.4 `LeverBlock.java` | default state 33–37; false predicates 45–52 | `556c5099ac53b435d6b65f143105ea894c2ff3684313083df4969fcc5bd23aaf` |
| 1.9.4 `PressurePlateBlock.java` | constructor/default powered and activation rule 20–24 | `3d310346d115a5790a91736b3db9b9a866d9949cb17abd6e777965099f03dc56` |
| 1.9.4 `AbstractPressurePlateBlock.java` | false predicates 49–56 | `0495b7e06db5d51ff7a3e6764587d9afd076365e41006e419eec08d4b3979990` |
| 1.9.4 `RedstoneOreBlock.java` | class, constructor and lit flag 18–26 | `dcc46f1d6ce5cb4eaafdd9ff31eb8e18543714db832cd27b391ddcc50d8b7eae` |
| 1.9.4 `block/state/StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, pane resolution tests each neighbor with `block.isOpaque()`. `Block` caches `opaqueCube` from virtual `isSolidRender()` in its constructor, and `isOpaque()` returns that cached value. In 1.9.4, the pane tests `block.defaultState().isCube()`; the resolved state delegates to `Block.isCube(state)`. The pane resolver sets north, south, west, and east properties from those respective neighbor tests. None of these three registrations is in the pane's explicit same-pane/glass allowlist.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `wall_sign`, ID 68 (`new WallSignBlock()`) | false | false | WallSignBlock extends SignBlock. Both defaults set `FACING=NORTH`; SignBlock explicitly returns false from the old and state-taking solid-render and cube predicates. Registry strength, sound, key and stats settings do not change the predicates. | No E arm in either version; mask remains empty. |
| `lever`, ID 69 (`new LeverBlock()`) | false | false | Both defaults set `FACING=NORTH` and `POWERED=false`; LeverBlock explicitly returns false from the old and state-taking predicates. The powered default does not change the result. | No E arm in either version; mask remains empty. |
| `stone_pressure_plate`, ID 70 (`new PressurePlateBlock(Material.STONE, ActivationRule.MOBS)`) | false | false | Both defaults set `POWERED=false`, and both registrations select `ActivationRule.MOBS`. The shared AbstractPressurePlateBlock explicitly returns false from the old and state-taking predicates. | No E arm in either version; mask remains empty. |

For each witness, the provider is immediately east of the pane while north, south, and west are air. The three predicate results remain false in both versions, so this batch has no connection-outcome or mask delta. This memo covers registration-level default predicate results and these directional witnesses only; sign support, lever behavior, pressure activation, collision, and player movement are outside scope.

## Remaining census and next slice

This is the thirty-sixth additive three-registration memo. The bounded inventory now records 101 classified registrations, still partial. The next lowest unclassified shared registrations, verified in both registries but not classified here, are `iron_door` ID 71, `wooden_pressure_plate` ID 72, and `redstone_ore` ID 73. Their predicates, defaults, and east-neighbor masks remain open. Independent review, complete provider closure, all world-reachable pane masks, and original derived-JAR equivalence remain open.
