# Independent source review: pane-provider slice 40 (2026-10-10)

## Scope and verdict

Reviewed only `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-fortieth.md` at frozen author commit `13a111d86ccf214e21ad864a52aaca1db3a1c9f7`. Memo Git blob: `8d78dcbe45906381cb8cdc7536ad8689ad394372`; raw SHA-256: `125318e47e89c719bf7aae97d298e612a69d158b2e7c6b4f73753e74a31a0cb6`.

**Verdict: ACCEPT, bounded to the three listed registrations and their one-east-neighbor pane-mask witnesses.** Snow and Clay retain E arms in both versions; Cactus remains disconnected in both. There is no connection-outcome delta in this slice.

## Source identity

Both canonical ready records identify `feather-r1-2026-10-07`, report `ready`, and match the memo’s source-manifest hashes: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248a37a27a1b4d77`.

All eleven cited source-file hashes were recomputed; each matches both the memo and the corresponding source-manifest row. Verification records report zero source-file differences and no raw-input differences. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence or establish that recorded hash differences are metadata-only.

## Bounded findings

The old pane resolver calls `block.isOpaque()` for north, south, west, and east. The 1.8.9 base constructor caches `opaqueCube` from virtual `isSolidRender()`, and `isOpaque()` returns that cache. The 1.9.4 resolver tests those same cardinal neighbors with `block.defaultState().isCube()`; resolved-state dispatch invokes `Block.isCube(state)`. The explicit pane/same-pane/glass allowlist does not include Snow, Cactus, or Clay.

| Registration | Registration/default input | 1.8.9 → 1.9.4 predicate | East-only pane mask |
|---|---|---|---|
| Snow, ID 80 | Both registries construct `SnowBlock()` with no extra state properties or predicate overrides. | Inherited base `isSolidRender()` true, cached and read as `isOpaque()` true → inherited base `isCube(state)` true. | E arm in both versions. |
| Cactus, ID 81 | Both registries construct `CactusBlock()` with default `AGE=0`. | Explicit old `isSolidRender()` false, cached and read as `isOpaque()` false → explicit `isCube(state)` false. | No E arm in either version. |
| Clay, ID 82 | Both registries construct `ClayBlock()` with `Material.CLAY`; no extra state properties or predicate overrides. | Inherited base `isSolidRender()` true, cached and read as `isOpaque()` true → inherited base `isCube(state)` true. | E arm in both versions. |

Each witness places its provider immediately east of the pane, with north, south, and west set to air. None of the three registration chains calls `setOpacity`. The memo’s predicate results and masks are supported.

## Source-file bindings

| Version | File | SHA-256 |
|---|---|---|
| 1.8.9 | `Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 | `PaneBlock.java` | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 | `SnowBlock.java` | `7cd8709bcaabecbd10cc29a60537e46e0b22c922bc1da177e303c0e54cded33d` |
| 1.8.9 | `CactusBlock.java` | `9724244c881244e4bd050524bf6179c276fe49129f136164fb0eeeba18a791cd` |
| 1.8.9 | `ClayBlock.java` | `03feb4f7de37aae893e951a267a7fcb9e81e8dc1283f88a143b1f4ab0bf91768` |
| 1.9.4 | `Block.java` | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 | `PaneBlock.java` | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 | `SnowBlock.java` | `70621f4fb42721de625bdb9fb0f2e3f8eea544714ef95b505f8a35933bb0f5e1` |
| 1.9.4 | `CactusBlock.java` | `b03df2d440ac215902606d6d0f058d3684bb720aea5c6a4f19261d3ab09b7a49` |
| 1.9.4 | `ClayBlock.java` | `824c8523377114ab8adbe2405cf6b2f57b9e787b0ef5488d373d5f3b3fa7e1f8` |
| 1.9.4 | `StateDefinition.java` | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

Snow melting, cactus growth/collision/damage, clay drops, and player movement are outside this finding. The next registrations are IDs 83–84; ID 85 has no registration in either checked source. Full provider census, reachable-mask closure, and original derived-JAR equivalence remain open. No build, tests, runtime, decompilation, source writes, merge, or push was performed.
