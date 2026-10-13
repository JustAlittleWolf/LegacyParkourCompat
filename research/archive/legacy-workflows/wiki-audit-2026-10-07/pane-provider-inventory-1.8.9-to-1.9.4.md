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

## Bounded census continuation through slice 41 (2026-10-10)

This remains a partial inventory. The additive author memos classify source registrations and directional pane witnesses only; they do not establish exhaustive source or world-reachable-mask closure.

- Slice 38 ACCEPT is bound to exact memo bytes after metadata-only identity correction event `b319cd54d1b22c3e1c8b5051bb9b02d3a9d2ad6c` (correction report blob `d83e43ca2d2b83b74bb09ca48ecbb8e61a90e9f2`).
- Slice 39 ACCEPT is recorded from report `reviews/independent-review-pane-neighbor-providers-thirty-ninth-2026-10-09.md`, commit `785823dc0ea08dfe16c17aa15f901813e7cc1a39`, blob `4c754f951e75656915f25774bf4df62a14f71711`. It is bounded to exact memo IDs 77–79 and their east-only witnesses. The report's printed 1.9.4 source-manifest digest differs from the canonical ready marker and memo digest; this metadata discrepancy is noted in the checkpoint and remains without a separate correction event in the supplied records.
- Slice 40 ACCEPT is recorded from report `reviews/independent-review-pane-neighbor-providers-fortieth-2026-10-10.md`, commit `7042a4cb3c50b865d86bb95d09c55934b638549f`, blob `d5fd5780a6fa868c1a095f41e3b85835a550061e`. The report's canonical-manifest binding is corrected metadata-only by `reviews/identity-correction-pane-neighbor-providers-fortieth-2026-10-10.md`, commit `25e5e107a05d5605ecd34f409c6fd746a6edc01a`, blob `4d92c4df361d485cea03d0967eef3a93562f8709`; canonical 1.9.4 manifest SHA-256 is `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- Slice 41 is `pane-neighbor-providers-2026-10-10-forty-first.md`, commit `74972fba154142008af172ddabcd7417eac6be96`, blob `0c6453722ebbb7e9c7c6c6829fa593e97e78f8d4`, raw SHA-256 `4aab6a95a7b976228a90d6b155089d30d5f2023fe4a3f6a6f62d8ef18bd34a10`. It classifies Reeds (83) and Fence (85) as false in both predicates with no E arm, and Jukebox (84) as true in both with an E arm; no connection-outcome delta was found. ID 85 is registered as Fence in both exact registries, correcting the prior expected-absence pointer without changing the frozen slice 40 memo or review.
- The bounded registration count is now 116 across 41 memos. Current next pointer: Pumpkin (86) in both registries. Slice 41 review is pending; full provider census, all world-reachable pane masks, and original derived-JAR equivalence remain open.

## Slice 41 independent review update — 2026-10-10

- Independent review `reviews/independent-review-pane-neighbor-providers-forty-first-2026-10-10.md` is **ACCEPT, bounded** to Reeds, Jukebox, and Fence (IDs 83–85), cited source behavior, and east-only pane-mask witnesses. It binds author memo commit `74972fba154142008af172ddabcd7417eac6be96`, blob `0c6453722ebbb7e9c7c6c6829fa593e97e78f8d4`; review commit `0c6ae18a32e272e79c53b09aa9b13b7de5e5588b`, blob `c881b086a5fd4579dc957cc692a9e7fd89cd4f98`.
- The review confirms Fence is registered at ID 85 in both exact registries; Pumpkin (86) is next. The census remains partial at 41 memos/116 registrations. Full provider closure, all world-reachable pane masks, and original derived-JAR equivalence remain open.

## Slice 39 identity-correction disposition — 2026-10-10

- This appended disposition supersedes the earlier captured statement that no correction event was supplied; that historical text remains preserved. The separate metadata-only correction exists at `reviews/identity-correction-pane-neighbor-providers-thirty-ninth-2026-10-10.md`, commit `3a70d1291156502a5c0cef4cd332bc3bf1d6aadb`, blob `b9bb0e0744926e6e6fb2f285c2a59c0e1b01e136`. It binds the canonical 1.9.4 source manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. The original bounded ACCEPT remains unchanged; the correction changes manifest identity metadata only.
