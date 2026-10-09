# Pane neighbor-provider slice 40: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-fortieth`

Status: three shared registrations classified from exact source; independent review pending. This additive memo does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This memo uses canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report ready. The source-manifest SHA-256 values are `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files). Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that recorded hash differences are metadata-only.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registrations 80–82 at 1011–1013; `isOpaque()` 154–155; base constructor/default state 199–207; default `isCube()` 245–247 and `isSolidRender()` 352–354 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | four-direction resolver 35–39; connection predicate 134–141 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `SnowBlock.java` | class and constructor 13–17 | `7cd8709bcaabecbd10cc29a60537e46e0b22c922bc1da177e303c0e54cded33d` |
| 1.8.9 `CactusBlock.java` | class/default AGE 17–22; false `isCube()` and `isSolidRender()` 63–71 | `9724244c881244e4bd050524bf6179c276fe49129f136164fb0eeeba18a791cd` |
| 1.8.9 `ClayBlock.java` | class and constructor 10–14 | `03feb4f7de37aae893e951a267a7fcb9e81e8dc1283f88a143b1f4ab0bf91768` |
| 1.9.4 `Block.java` | registrations 80–82 at 912–914; base constructor/default state 175–181; default `isCube(state)` 223–225 and `isSolidRender(state)` 357–359 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | four-direction resolver 105–110; connection predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `SnowBlock.java` | class and constructor 14–18 | `70621f4fb42721de625bdb9fb0f2e3f8eea544714ef95b505f8a35933bb0f5e1` |
| 1.9.4 `CactusBlock.java` | class/default AGE 17–25; false state-taking `isCube` and `isSolidRender` 64–71 | `b03df2d440ac215902606d6d0f058d3684bb720aea5c6a4f19261d3ab09b7a49` |
| 1.9.4 `ClayBlock.java` | class and constructor 11–15 | `824c8523377114ab8adbe2405cf6b2f57b9e787b0ef5488d373d5f3b3fa7e1f8` |
| 1.9.4 `block/state/StateDefinition.java` | resolved state's `isCube()` delegation 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, `PaneBlock` resolves each cardinal neighbor using `block.isOpaque()`. The base `Block` constructor initializes its cached `opaqueCube` from virtual `isSolidRender()`; `isOpaque()` returns that cache. In 1.9.4, the pane uses `block.defaultState().isCube()`, with the resolved default state delegating to `Block.isCube(state)`. The explicit allowlist includes the pane itself, glass, stained glass, stained-glass pane, and other `PaneBlock` instances; none of these three registrations is in that allowlist. The registered names and IDs match in both versions, and their registration chains do not call `setOpacity`.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `snow`, ID 80 (`new SnowBlock()`) | true | true | SnowBlock extends Block, adds no state properties, and overrides neither predicate. It inherits Block's default state and true `isSolidRender()`/`isCube()` behavior. | E arm in both versions. |
| `cactus`, ID 81 (`new CactusBlock()`) | false | false | Both defaults set `AGE=0`. CactusBlock explicitly returns false from old `isSolidRender()` and `isCube()`, and from the state-taking versions of both methods. The old constructor cache is therefore false; the newer pane reads the false `isCube(state)` result. | No E arm in either version; mask remains empty. |
| `clay`, ID 82 (`new ClayBlock()`) | true | true | ClayBlock adds no state properties and overrides neither predicate. It inherits Block's default state and true `isSolidRender()`/`isCube()` behavior. | E arm in both versions. |

For each witness, the candidate is immediately east of the pane while north, south, and west are air. Snow and Clay connect in both versions; Cactus does not. There is no connection-outcome delta in this slice. This memo covers registration-level predicate results and these directional witnesses only; snow melting, cactus collision/damage and growth, clay item drops, and player movement are outside scope.

## Remaining census and next slice

This is the fortieth additive three-registration memo. The bounded inventory now records 113 classified registrations, still partial. The next lowest unclassified shared registrations verified in both registries are `reeds` ID 83 and `jukebox` ID 84; ID 85 has no registration in either source. Their predicates/defaults and east-neighbor masks remain open. Independent review, complete provider closure, all world-reachable pane masks, and original derived-JAR equivalence remain open.