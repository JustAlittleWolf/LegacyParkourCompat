# Independent source review: pane-provider slice 36 (2026-10-09)

## Scope and verdict

Reviewed only `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-thirty-sixth.md` in supplied author tree `7e4b6bb0a0070a8f7a48ada734721986b49f4a0c`. The memo was introduced at `6c1dc911252b87d28613ca5b8063e9ea78708549` and is unchanged at the author tip. Its Git blob is `809b29beed4a1e609180943a1318daa2563a5294`; raw SHA-256 is `d38d0b350047e5400bd2c307630c4b17af770a8a45028450f7aaab77c43ba621`.

**Verdict: ACCEPT, bounded to the three listed registrations and their one-east-neighbor pane-mask witnesses.** The false/false predicate results and no-east-arm outcomes are supported by the exact sources. This does not close the registered-provider census or all world-reachable masks.

## Source identity

Both canonical ready records identify `feather-r1-2026-10-07`, report `ready`, and match the memo’s source manifests: 1.8.9 source-manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 source-manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. I recomputed every one of the memo’s 17 cited file hashes. All match both the memo and the corresponding source-manifest row. Verification records report zero source-file differences and no raw-input-artifact differences. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence or establish that recorded hash differences are metadata-only.

## Bounded findings

The registries bind the same IDs and classes in both versions. The old pane resolver calls `block.isOpaque()` for north, south, west, and east; `Block` caches `opaqueCube` from virtual `isSolidRender()` in its constructor, and `isOpaque()` returns that cache. The new resolver checks the same four directions using `block.defaultState().isCube()`; resolved-state dispatch invokes `Block.isCube(state)`. None of the three providers is included in the explicit pane/same-pane/glass allowlist.

| Registration | Exact provider/default input | Predicate in 1.8.9 → 1.9.4 | East-only pane witness |
|---|---|---|---|
| Wall Sign, ID 68 | Both registries construct `WallSignBlock`; its default facing is north. It inherits `SignBlock` predicates. | `isSolidRender()` false, cached and read by `isOpaque()` → `isCube(state)` false. | No E arm in either version. |
| Lever, ID 69 | Both registries construct `LeverBlock`; defaults are facing north and `POWERED=false`. | `isSolidRender()` false, cached and read by `isOpaque()` → `isCube(state)` false. | No E arm in either version. |
| Stone Pressure Plate, ID 70 | Both registrations construct `PressurePlateBlock(Material.STONE, ActivationRule.MOBS)`; default `POWERED=false`. It inherits the shared abstract pressure-plate predicates. | `isSolidRender()` false, cached and read by `isOpaque()` → `isCube(state)` false. | No E arm in either version. |

For each witness, the provider is immediately east of the pane, with the other three cardinal neighbors air. No connection-outcome or mask delta results. The memo’s false/false and no-east-arm claims are supported.

## Source-file bindings

| Version | File | SHA-256 |
|---|---|---|
| 1.8.9 | `Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 | `PaneBlock.java` | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 | `WallSignBlock.java` | `5fa003c5ca40bfa2c901e35f8fa21fde096264038c8150780a2e49f8f24e97b2` |
| 1.8.9 | `SignBlock.java` | `7ccc9f3da44069426c3fc808e97631a56b8ad10045b89459219b2c2d062e81f6` |
| 1.8.9 | `LeverBlock.java` | `d8359d88616d0bd09462f90e98d5f4bdb76bf30b991ce841faf068424f0b5881` |
| 1.8.9 | `PressurePlateBlock.java` | `2a78b32351f803b1f48164dde6270cd96f60acfecebebb146fe6358ba7d3e84a` |
| 1.8.9 | `AbstractPressurePlateBlock.java` | `85896b070be577d228e8a660f1f95cae8956467a957463c4fc839080d2e5b1cb` |
| 1.8.9 | `RedstoneOreBlock.java` | `9b2efe542205a9ccba4f86f472f973a1beab413f3de3773dd28f4bf6915911db` |
| 1.9.4 | `Block.java` | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 | `PaneBlock.java` | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 | `WallSignBlock.java` | `bf90a4ca42cb61baee9051e80fb9e774472ee61f6c46aeb03cf26b18368935c5` |
| 1.9.4 | `SignBlock.java` | `09deedec28a527b42f45e9e352f2ea3a6e1efd95769a53121945cfdd5009c2d1` |
| 1.9.4 | `LeverBlock.java` | `556c5099ac53b435d6b65f143105ea894c2ff3684313083df4969fcc5bd23aaf` |
| 1.9.4 | `PressurePlateBlock.java` | `3d310346d115a5790a91736b3db9b9a866d9949cb17abd6e777965099f03dc56` |
| 1.9.4 | `AbstractPressurePlateBlock.java` | `0495b7e06db5d51ff7a3e6764587d9afd076365e41006e419eec08d4b3979990` |
| 1.9.4 | `RedstoneOreBlock.java` | `dcc46f1d6ce5cb4eaafdd9ff31eb8e18543714db832cd27b391ddcc50d8b7eae` |
| 1.9.4 | `StateDefinition.java` | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

The memo’s Redstone Ore citations are contextual only; this review does not classify that provider. Sign support, lever operation, pressure activation, collision, and movement are outside scope. The next IDs 71–73 and the complete provider/mask inventories remain open. No build, tests, runtime, decompilation, source writes, merge, or push was performed.
