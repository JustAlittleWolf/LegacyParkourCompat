# Independent source review: pane-provider slice 37 (2026-10-09)

## Scope and verdict

Reviewed only `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-thirty-seventh.md` at frozen author commit `2f00ab7328b275010911b4fa800fd52e128abdc9`. It is unchanged in checkpoint commit `39f78735aef320e260be0050f4aa8652a76f51ef`. Memo Git blob: `bc6d99248ccdec2fe2164815714fc9d089fbdc7d`; raw SHA-256: `f8a5bdbc2c8b2a59339306cec47aecb7a0e4c30b618f961b2d8b02219cda6fa8`.

**Verdict: ACCEPT, bounded to the three listed registrations and their one-east-neighbor pane-mask witnesses.** The two false/false results, Redstone Ore’s true/true result, and the resulting empty/E/empty masks are supported by the exact sources. This does not close the provider census or all world-reachable masks.

## Source identity

Both canonical ready records identify `feather-r1-2026-10-07`, report `ready`, and match the memo’s source-manifest hashes: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. All thirteen cited source-file hashes were recomputed; each matches both the memo and its source-manifest row. Verification records report zero source differences and no raw-input differences. Original derived JARs remain unavailable; regenerated immutable JARs do not prove equivalence or establish that recorded hash differences are metadata-only.

## Bounded findings

The old pane resolver tests four cardinal neighbors using `block.isOpaque()`. The 1.8.9 `Block` constructor caches `opaqueCube` from virtual `isSolidRender()`, and `isOpaque()` returns that cache. The 1.9.4 resolver checks the same four directions using `block.defaultState().isCube()`; the resolved state delegates to `Block.isCube(state)`. None of these providers is covered by the pane’s explicit same-pane/glass allowlist.

| Registration | Registration and defaults | 1.8.9 → 1.9.4 result | East-only pane witness |
|---|---|---|---|
| Iron Door, ID 71 | Both registries construct `DoorBlock(Material.IRON)`. Defaults: north-facing, closed, left hinge, unpowered, lower half. | `isSolidRender()` false, hence cached `isOpaque()` false → `isCube(state)` false. | No E arm in either version. |
| Wooden Pressure Plate, ID 72 | Both registries construct `PressurePlateBlock(Material.WOOD, ActivationRule.EVERYTHING)`. Default `POWERED=false`; the activation rule is constructor configuration. | Inherited `AbstractPressurePlateBlock` predicate false → state-taking predicate false. | No E arm in either version. |
| Redstone Ore, ID 73 | Both registries construct `RedstoneOreBlock(false)` with `Material.STONE`. The `lit` value is an instance field, not a block-state property, and the class does not override the relevant predicates. | Inherited base solid-render/cube predicates true → true. | E arm in both versions. |

For each witness, the provider is immediately east of the pane and the north, south, and west neighbors are air. The memo’s per-registration predicate results and unchanged east masks are supported. The adjacent Redstone Ore citation is contextual; this review does not classify later lit Redstone Ore registrations.

## Source-file bindings

| Version | File | SHA-256 |
|---|---|---|
| 1.8.9 | `Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 | `PaneBlock.java` | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 | `DoorBlock.java` | `51b93fcb01758e1b809a503a9e63aab6d781dbd9a0e4d3b95823168e8958128d` |
| 1.8.9 | `PressurePlateBlock.java` | `2a78b32351f803b1f48164dde6270cd96f60acfecebebb146fe6358ba7d3e84a` |
| 1.8.9 | `AbstractPressurePlateBlock.java` | `85896b070be577d228e8a660f1f95cae8956467a957463c4fc839080d2e5b1cb` |
| 1.8.9 | `RedstoneOreBlock.java` | `9b2efe542205a9ccba4f86f472f973a1beab413f3de3773dd28f4bf6915911db` |
| 1.9.4 | `Block.java` | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 | `PaneBlock.java` | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 | `DoorBlock.java` | `68ce8fe13f6b08b0803f214716784f6196c77728e4ab0840eb22a52c3f98217e` |
| 1.9.4 | `PressurePlateBlock.java` | `3d310346d115a5790a91736b3db9b9a866d9949cb17abd6e777965099f03dc56` |
| 1.9.4 | `AbstractPressurePlateBlock.java` | `0495b7e06db5d51ff7a3e6764587d9afd076365e41006e419eec08d4b3979990` |
| 1.9.4 | `RedstoneOreBlock.java` | `dcc46f1d6ce5cb4eaafdd9ff31eb8e18543714db832cd27b391ddcc50d8b7eae` |
| 1.9.4 | `StateDefinition.java` | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

Door placement, pressure activation, Redstone Ore lighting/tick behavior, collision, movement, and runtime parity are outside this finding. The next IDs 74–76, full registered-provider census, reachable-mask closure, and original derived-JAR equivalence remain open. No build, tests, runtime, decompilation, source writes, merge, or push was performed.
