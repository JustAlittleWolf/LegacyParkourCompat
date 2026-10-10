# Independent source review: pane-provider slice 39 (2026-10-09)

## Scope and verdict

Reviewed only `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-thirty-ninth.md` at frozen memo commit `10c021a1ebcdd8b4356ebbe3433cb0bcbe6ed304`. Memo Git blob: `4c0f23c7d2b6476c6b3bec1f2d9406bccf9f5a3c`; raw SHA-256: `f9c818b2ad326b37028745a446bc819c84c3a732a48e454b07a005694417d93a`.

**Verdict: ACCEPT, bounded to the three listed registrations and their one-east-neighbor pane-mask witnesses.** Stone Button and Snow Layer remain false/false with no east arm; Ice changes false→true and gains the east arm in 1.9.4. This does not close the provider census or all world-reachable masks.

## Source identity

Both canonical ready records identify `feather-r1-2026-10-07`, report `ready`, and match the memo’s source-manifest hashes: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248a37a27a1b4d77`.

I recomputed all fifteen cited source-file hashes; every hash matches both the memo and its source-manifest row. Verification records report zero source-file differences and no raw-input differences. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence or establish that recorded hash differences are metadata-only.

## Bounded findings

The old pane resolver tests north, south, west, and east with `block.isOpaque()`. The 1.8.9 `Block` constructor caches `opaqueCube` from virtual `isSolidRender()`, and `isOpaque()` returns that cache. The 1.9.4 pane resolver tests the same four directions with `block.defaultState().isCube()`; resolved-state dispatch invokes `Block.isCube(state)`. The pane allowlist does not include Stone Button, Snow Layer, or Ice.

| Registration | Registration/default input | 1.8.9 → 1.9.4 predicate | East-only pane mask |
|---|---|---|---|
| Stone Button, ID 77 | Both registries construct `StoneButtonBlock`, which passes `false` to `ButtonBlock`. Defaults are `FACING=NORTH` and `POWERED=false`. | ButtonBlock’s `isSolidRender()` false, cached and read as `isOpaque()` false → `isCube(state)` false. | No E arm in either version. |
| Snow Layer, ID 78 | Both defaults set `LAYERS=1`. Registration calls `setOpacity(0)`; that setter changes only the separate opacity field. | Explicit old `isSolidRender()` / `isCube()` false → explicit state-taking `isSolidRender(state)` / `isCube(state)` false. | No E arm in either version. |
| Ice, ID 79 | Both registries construct `IceBlock`, which extends `TransparentBlock`. Neither IceBlock nor TransparentBlock overrides `isCube`; the registration calls `setOpacity(3)`. | TransparentBlock’s `isSolidRender()` false makes the old cached `isOpaque()` false → inherited base `isCube(state)` true through default-state dispatch. `setOpacity(3)` does not alter either predicate. | No arm in 1.8.9; E arm in 1.9.4. |

Each witness places the provider immediately east of the pane, with north, south, and west set to air. The memo’s predicate results and one-east-neighbor masks are supported. Ice is the only connection-outcome delta in this slice.

## Source-file bindings

| Version | File | SHA-256 |
|---|---|---|
| 1.8.9 | `Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 | `PaneBlock.java` | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 | `StoneButtonBlock.java` | `8cdcecae0e048a11cba3b65640635ab51b82cb3e02a685b06573e33f4a4207bc` |
| 1.8.9 | `ButtonBlock.java` | `e79f9805438fc92233e709feffa83091febde8de2a3daae8280183ef251e5681` |
| 1.8.9 | `SnowLayerBlock.java` | `d922f1f9a8d6f543959835e8b21e800ecf5cd9c7c60816b9152d873ca132a3ed` |
| 1.8.9 | `IceBlock.java` | `dae1d397d40f5167af82fbde78174f259ade1415c96e4d34c8c31c72bdb40a3a` |
| 1.8.9 | `TransparentBlock.java` | `10780460a36e5e73d61381cd91b05a988e30a67e71fc182875a52bdcf387d244` |
| 1.9.4 | `Block.java` | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 | `PaneBlock.java` | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 | `StoneButtonBlock.java` | `ea59bb2212d53c3a89118ad59cfe0666e0ef027e8245524d7372de63823c2a49` |
| 1.9.4 | `ButtonBlock.java` | `586f2332752a5aba0a4feb2e1d14dc894887e29f6de8a4ba35cd6526c1f154a7` |
| 1.9.4 | `SnowLayerBlock.java` | `3f60db09c66fb07439869254db883eed5da679a17e369e019f95ba97d427b2a5` |
| 1.9.4 | `IceBlock.java` | `23502fdd9ae0d32687fdd9fed4bf272bede481ad00e371c6fc7d9a76f6d87ba4` |
| 1.9.4 | `TransparentBlock.java` | `b688c54180a7f67fad3a4a9a2e8e50d3b0d247c382800039f5d7c3d08ea0288b` |
| 1.9.4 | `StateDefinition.java` | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

Button interaction, snow accumulation, ice slipperiness, collision, and movement are outside this finding. The next IDs 80–82, complete provider census, reachable-mask closure, and original derived-JAR equivalence remain open. No build, tests, runtime, decompilation, source writes, merge, or push was performed.
