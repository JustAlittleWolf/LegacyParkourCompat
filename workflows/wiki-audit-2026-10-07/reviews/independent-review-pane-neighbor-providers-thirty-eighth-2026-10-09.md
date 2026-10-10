# Independent source review: pane-provider slice 38 (2026-10-09)

## Scope and verdict

Reviewed only `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-thirty-eighth.md` at frozen author commit `18d273debae556ade81b38c6a920c6e533ebf4ab`, parent `39f78735aef320e260be0050f4aa8652a76f51ef`. Memo Git blob: `bc6d99248ccdec2fe2164815714fc9d089fbdc7d`; raw SHA-256: `f8a5bdbc2c8b2a59339306cec47aecb7a0e4c30b618f961b2d8b02219cda6fa8`.

**Verdict: ACCEPT, bounded to the three listed registrations and their one-east-neighbor pane-mask witnesses.** Lit Redstone Ore remains true/true and produces an east arm in both versions; both redstone torch registrations remain false/false and produce no east arm.

## Source identity

Both canonical ready records identify `feather-r1-2026-10-07`, report `ready`, and match the memo’s source-manifest hashes: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. All eleven cited source-file hashes were recomputed; each matches both the memo and its source-manifest row. Verification records report zero source differences and no raw-input differences. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence or establish that recorded hash differences are metadata-only.

## Bounded findings

The old pane resolver calls `block.isOpaque()` for each cardinal neighbor. The 1.8.9 base constructor caches `opaqueCube` from virtual `isSolidRender()`, and `isOpaque()` returns that cache. The 1.9.4 resolver uses `block.defaultState().isCube()` for the same north, south, west, and east positions; resolved-state dispatch invokes `Block.isCube(state)`. These providers are not in the pane’s explicit same-pane/glass allowlist.

| Registration | Registration and provider inputs | 1.8.9 → 1.9.4 predicate | East-only pane mask |
|---|---|---|---|
| Lit Redstone Ore, ID 74 | Both registries construct `RedstoneOreBlock(true)` with `Material.STONE`; class inherits the base predicates. Its `lit` field is not a state property. The registry light setter changes the light field only. | Base `isSolidRender()` true, cached and read as `isOpaque()` true → base `isCube(state)` true. | E in both versions; no change. |
| Unlit Redstone Torch, ID 75 | Both registries construct `RedstoneTorchBlock(false)`, a `TorchBlock` subclass. Default `FACING=UP`; lit is a constructor field, not a state property. | `TorchBlock.isSolidRender()` false, cached and read as `isOpaque()` false → `TorchBlock.isCube(state)` false. | No E arm in either version. |
| Redstone Torch, ID 76 | Both registries construct `RedstoneTorchBlock(true)`; relative to ID 75 the lit flag and light setter differ. The inherited default facing is up. | The same TorchBlock predicates return false independently of the lit flag. | No E arm in either version. |

The Redstone Ore source declares no predicate overrides and inherits base predicates that return true in both versions. TorchBlock explicitly returns false from both relevant old predicates and both state-taking predicates; RedstoneTorchBlock’s lit field does not participate in them. With each provider immediately east of the pane and air north, south, and west, the memo’s E/empty/empty mask results follow. The batch has no connection-outcome or mask delta.

## Source-file bindings

| Version | File | SHA-256 |
|---|---|---|
| 1.8.9 | `Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 | `PaneBlock.java` | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 | `RedstoneOreBlock.java` | `9b2efe542205a9ccba4f86f472f973a1beab413f3de3773dd28f4bf6915911db` |
| 1.8.9 | `RedstoneTorchBlock.java` | `e85ac1cd0988ea3657c32623206c72b8131fa30e3dde275e8b9e1610cdbafa70` |
| 1.8.9 | `TorchBlock.java` | `20ac458631cdf68b24e739ac100616bb024ec2200262a4de66ad5c59e7967a5b` |
| 1.9.4 | `Block.java` | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 | `PaneBlock.java` | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 | `RedstoneOreBlock.java` | `dcc46f1d6ce5cb4eaafdd9ff31eb8e18543714db832cd27b391ddcc50d8b7eae` |
| 1.9.4 | `RedstoneTorchBlock.java` | `19faa81c850e46f91a64368fc2c1be51f196bb79d0eb21f29c2eccd874ecaaff` |
| 1.9.4 | `TorchBlock.java` | `f4d7573ee4b16e9d293f1209f1edf8d6a05588fa9ba614fb927bfaa274ca7955` |
| 1.9.4 | `StateDefinition.java` | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

This does not assess redstone ticks, torch power, lighting behavior beyond the setter’s predicate independence, collision, or player movement. The next IDs 77–79, full registered-provider census, reachable-mask closure, and original derived-JAR equivalence remain open. No build, tests, runtime, decompilation, source writes, merge, or push was performed.
