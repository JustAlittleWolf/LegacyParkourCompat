# Pane neighbor-provider slice 39: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-thirty-ninth`

Status: three shared registrations classified from exact source; independent review pending. This additive memo does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This memo uses canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report ready. The source-manifest SHA-256 values are `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files). Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence to those originals or establish that recorded hash differences are metadata-only.

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | registrations 77–79 at 1008–1010; old opacity/cache 154–155, 202–206; `setOpacity` 218–221 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | four-direction resolver 35–39; connection predicate 134–141 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `StoneButtonBlock.java` | ButtonBlock subclass/constructor 3–7 | `8cdcecae0e048a11cba3b65640635ab51b82cb3e02a685b06573e33f4a4207bc` |
| 1.8.9 `ButtonBlock.java` | default state 26–32; false predicates 44–52 | `e79f9805438fc92233e709feffa83091febde8de2a3daae8280183ef251e5681` |
| 1.8.9 `SnowLayerBlock.java` | default layers 22–31; false predicates 48–56 | `d922f1f9a8d6f543959835e8b21e800ecf5cd9c7c60816b9152d873ca132a3ed` |
| 1.8.9 `IceBlock.java` | TransparentBlock subclass and constructor 16–23 | `dae1d397d40f5167af82fbde78174f259ade1415c96e4d34c8c31c72bdb40a3a` |
| 1.8.9 `TransparentBlock.java` | class/constructor 10–20; false `isSolidRender()` 22–25 | `10780460a36e5e73d61381cd91b05a988e30a67e71fc182875a52bdcf387d244` |
| 1.9.4 `Block.java` | registrations 77–79 at 909–911; default opacity 112–114, 179–181; base `isCube` 223–225; `setOpacity` 193–196 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | four-direction resolver 106–110; connection predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `StoneButtonBlock.java` | ButtonBlock subclass/constructor 10–14 | `ea59bb2212d53c3a89118ad59cfe0666e0ef027e8245524d7372de63823c2a49` |
| 1.9.4 `ButtonBlock.java` | default state 39–45; false state-taking predicates 58–66 | `586f2332752a5aba0a4feb2e1d14dc894887e29f6de8a4ba35cd6526c1f154a7` |
| 1.9.4 `SnowLayerBlock.java` | default layers 37–42; false state-taking predicates 68–76 | `3f60db09c66fb07439869254db883eed5da679a17e369e019f95ba97d427b2a5` |
| 1.9.4 `IceBlock.java` | TransparentBlock subclass and constructor 20–26 | `23502fdd9ae0d32687fdd9fed4bf272bede481ad00e371c6fc7d9a76f6d87ba4` |
| 1.9.4 `TransparentBlock.java` | class/constructor 10–20; false `isSolidRender(state)` 22–25 | `b688c54180a7f67fad3a4a9a2e8e50d3b0d247c382800039f5d7c3d08ea0288b` |
| 1.9.4 `block/state/StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, pane resolution tests each neighbor with `block.isOpaque()`. The base constructor caches `opaqueCube` from virtual `isSolidRender()`, and `isOpaque()` returns that cache. In 1.9.4, the pane tests `block.defaultState().isCube()`; resolved states delegate to `Block.isCube(state)`. The pane resolver sets north, south, west, and east properties from those cardinal tests. Its explicit allowlist includes the pane itself, glass, stained glass, stained-glass pane, and other PaneBlock instances; it does not include buttons, snow layers, or ice.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `stone_button`, ID 77 (`new StoneButtonBlock()`) | false | false | StoneButtonBlock passes `false` to ButtonBlock. Both defaults set `FACING=NORTH` and `POWERED=false`; ButtonBlock explicitly returns false from the old and state-taking solid-render and cube predicates. | No E arm in either version; mask remains empty. |
| `snow_layer`, ID 78 (`new SnowLayerBlock()`) | false | false | Both defaults set `LAYERS=1`. SnowLayerBlock explicitly returns false from both versions' solid-render and cube predicates. The registration's opacity setter changes the separate opacity value; it does not change the cached old `opaqueCube` or the new state-taking cube result. | No E arm in either version; mask remains empty. |
| `ice`, ID 79 (`new IceBlock()`) | false | true | IceBlock extends TransparentBlock and has no predicate override. TransparentBlock returns false from old `isSolidRender()`, so the old base `opaqueCube` cache is false. In 1.9.4 it returns false from state-taking `isSolidRender`, but it inherits `Block.isCube(state) == true`; the resolved default state dispatches to that base cube predicate. The registration's `setOpacity(3)` only assigns the separate opacity field in both versions. | No E arm in 1.8.9; E arm in 1.9.4. |

For each witness, the provider is immediately east of the pane while north, south, and west are air. Stone Button and Snow Layer remain disconnected in both versions. Ice is not in the pane's explicit allowlist: its old false opacity result produces no arm, while its newer inherited true default-state cube result produces the E arm. Ice is the sole connection-outcome delta in this slice. This memo covers registration-level predicate results and these directional witnesses only; button interaction, snow accumulation, ice slipperiness, collision, and player movement are outside scope.

## Remaining census and next slice

This is the thirty-ninth additive three-registration memo. The bounded inventory now records 110 classified registrations, still partial. The next lowest unclassified shared registrations, verified in both registries but not classified here, are `snow` ID 80, `cactus` ID 81, and `clay` ID 82. Their predicates/defaults and east-neighbor masks remain open. Independent review, complete provider closure, all world-reachable pane masks, and original derived-JAR equivalence remain open.
