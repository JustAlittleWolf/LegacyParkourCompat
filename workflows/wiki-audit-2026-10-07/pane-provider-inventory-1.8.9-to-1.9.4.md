# Pane connection-provider inventory: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-provider-inventory-1.8.9-1.9.4`

Status: bounded predicate and named-provider inventory recorded; exhaustive provider census remains open. This report expands the single Ice witness in [pane-ice-neighbor-1.8.9-to-1.9.4.md](pane-ice-neighbor-1.8.9-to-1.9.4.md); it does not claim that every registered block's predicate result or reachable neighbor state has been classified. Independent review pending. No runtime validation was performed.

## Ready-source provenance

Both canonical sources are `ornithe-feather` ready trees: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`. Their ready records say `ready`, with 1,612 and 1,819 source files respectively. The source-manifest SHA-256 values are `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.

| Source slice | Exact range | SHA-256 |
|---|---|---|
| 1.8.9 `PaneBlock.java` | `resolveVirtualProperties()` lines 33–39; `shouldConnectTo(Block)` lines 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.9.4 `PaneBlock.java` | neighbor resolver lines 104–110; `shouldConnectTo(Block)` lines 133–139 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.8.9 `Block.java` | registered pane/glass IDs at lines 863, 1031, 1040–1041, 1180 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.9.4 `Block.java` | registered pane/glass IDs at lines 755, 936, 950–952, 1099, 1263 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.8.9 `Blocks.java` | registered block singleton references | `1da2d85406ed991e8f6bc1f420babf0bd43598478f347c6a4dc9fea322ed0695` |
| 1.9.4 `Blocks.java` | registered block singleton references | `dc561d17e9e12767774d9ef01057f0c981e5a9bb666e2d11cba3517062cc8dd7` |
| 1.8.9 / 1.9.4 `GlassBlock.java` | `isCube()` overrides | `a7c3403c4e6d0d0014ef722755962f5f449071bb2ebffe0fd2d200bd5cd42144` / `554a0884bfef7bc61b4a2535193037dfb0de0e338227ae0335dbd6432ef62471` |
| 1.8.9 / 1.9.4 `IceBlock.java` | class inheritance and constructor material | `dae1d397d40f5167af82fbde78174f259ade1415c96e4d34c8c31c72bdb40a3a` / `23502fdd9ae0d32687fdd9fed4bf272bde481ad00e371c6fc7d9a76f6d87ba4` |
| 1.8.9 / 1.9.4 `TransparentBlock.java` | transparent base rendering/opacity behavior | `10780460a36e5e73d61381cd91b05a988e30a67e71fc182875a52bdcf387d244` / `b688c54180a7f67fad3a4a9a2e8e50d3b0d247c382800039f5d7c3d08ea0288b` |
| 1.9.4 `StateDefinition.java` | default-state `isCube()` delegation | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |
| 1.9.4 `FrostedIceBlock.java` | inheritance from `IceBlock` | `daaddf3502cfa75a7e17fff47ce64c150109dc9846064de2af6915d761cc7bb0` |

Each source hash matches the corresponding exact version's source manifest. No readiness marker or shared source artifact changed.

## Predicate comparison and bounded inventory

Both versions resolve the same four cardinal neighbors. In 1.8.9, `PaneBlock.shouldConnectTo(Block)` accepts an opaque block, the pane itself, glass, stained glass, stained-glass panes, and other `PaneBlock` instances. In 1.9.4 the general test changes from `block.isOpaque()` to `block.defaultState().isCube()`; the explicit glass and pane-family cases remain. `defaultState()` is the block's default state. The inspected 1.9.4 state method delegates `isCube()` to `Block.isCube(state)`, whose base implementation returns true unless a block class overrides it. Thus the predicate tests the provider block's default state, not the adjacent world's current property values.

The bounded registration scan counted the explicitly named pane-family entries in each `Block.java` registry: `iron_bars`, `glass_pane`, and `stained_glass_pane` (three IDs per version). The explicit glass allowlist contributes `glass` and `stained_glass` (two more IDs per version); stained-glass pane is also in the pane family. These category counts are not a census of all qualifying providers.

- **Shared changed-predicate witness:** Ice fails 1.8.9's `isOpaque()` because `IceBlock` extends `TransparentBlock` with ice material; it passes in 1.9.4 because it inherits the default cube predicate. The registered Ice state therefore gains a pane connection under the 1.9.4 predicate. The independent bounded witness and mask consequence are recorded separately in the pane/Ice finding.
- **Additional known 1.9.4 candidate:** `frosted_ice` is registered as ID 212 and its class extends `IceBlock`, so it inherits the same default-cube path. It has no 1.8.9 registry counterpart.
- **Non-witnesses:** glass and stained glass remain explicit allowlist members. Their `GlassBlock.isCube()` override returns false in both versions, so they do not demonstrate a behavioral delta in this predicate comparison.

## Concrete open source gap

The registry initializer is present and source-inventoried, but this bounded pass did not evaluate every registered block's 1.8.9 `isOpaque()` result against its 1.9.4 default-state `isCube()` result, including subclasses, overrides, and block construction defaults. Exhaustive provider closure requires classifying every registered ID through those exact methods and then validating which resolved directions are reachable in actual pane neighbor states. Until that census is complete, the three pane-family and two named-glass counts above are category counts only, not a closed total.
