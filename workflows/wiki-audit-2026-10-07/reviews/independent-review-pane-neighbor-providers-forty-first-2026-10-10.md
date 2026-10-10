# Independent source review: pane-provider slice 41 (2026-10-10)

## Scope and verdict

Reviewed only `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-10-forty-first.md` at frozen author commit `74972fba154142008af172ddabcd7417eac6be96`. Memo Git blob: `0c6453722ebbb7e9c7c6c6829fa593e97e78f8d4`; raw SHA-256: `4aab6a95a7b976228a90d6b155089d30d5f2023fe4a3f6a6f62d8ef18bd34a10`.

**Verdict: ACCEPT, bounded to Reeds, Jukebox, and Fence (IDs 83–85), their cited source behavior, and the east-only pane-mask witnesses.** Reeds and Fence remain disconnected in both versions; Jukebox connects east in both. There is no pane connection-outcome delta in this slice. This review also corrects the prior slice 40 report's erroneous statement that ID 85 is absent; the correction is recorded separately in `reviews/continuation-correction-pane-neighbor-providers-fortieth-2026-10-10.md` (commit `ba9e66b2`).

## Source identity

Both canonical ready roots are `build/movement-campaign-2026-10-07/ready/{1.8.9,1.9.4}/ornithe-feather`, revision `feather-r1-2026-10-07`. The source-manifest SHA-256 values were recomputed from the manifest files and match their ready-marker values: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files), and 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files).

All twelve cited source-file hashes below were recomputed from the exact ready tree and match the memo and corresponding source-manifest entries. Verification records report identical source trees, zero source-file differences, and no raw-input differences. Each verification record also reports one derived remapped-JAR difference. The original derived JARs remain unavailable, so regenerated immutable JARs do not establish equivalence to the originals or that the recorded hash differences are metadata-only.

## Bounded findings

The 1.8.9 pane resolver calls `block.isOpaque()` for each cardinal neighbor. The base constructor initializes cached opacity through virtual `isSolidRender()`, which Reeds and Fence override to false; Jukebox inherits the base true result. The 1.9.4 pane resolver calls `block.defaultState().isCube()`: Reeds and Fence override state-taking `isCube(state)` to false, while Jukebox inherits the base true result. The pane allowlists do not otherwise include these candidates.

| Registration | Defaults and geometry | 1.8.9 `isOpaque()` → 1.9.4 default-state `isCube()` | East-only pane mask |
|---|---|---|---|
| Reeds, ID 83 (`new SugarCaneBlock()`) | Default `AGE=0`. Outline bounds x/z 0.125–0.875 and y 0–1 in both versions. Old collision source returns `null`; new source returns `EMPTY_BLOCK_SHAPE`. | Explicit false → explicit false. | N/E/S/W false in both; no E arm. |
| Jukebox, ID 84 (`new JukeboxBlock()`) | Default `HAS_RECORD=false`. Inherits the full-cube default outline and collision shape through `BlockWithBlockEntity`; that parent sets block-entity status but adds no pane predicate or shape override. | Inherited true → inherited true. | E true, other directions false, in both; E arm. |
| Fence, ID 85 (`new FenceBlock(Material.WOOD, oakColor)`) | Registered as `fence` at ID 85 in both source registries. Default N/E/S/W false. Outline post reaches y=1 and collision post reaches y=1.5; arms use resolved directions. The old implementation builds these bounds dynamically; the new one selects predeclared boxes. | Explicit false → explicit false. | N/E/S/W false in both; no E arm. |

Each pane witness places the candidate immediately east of the pane and air north, south, and west. The pane resolver therefore yields the same mask and arm geometry in both versions for each candidate. Fence's own attachment rules are distinct from the pane predicate and are not used to classify this pane witness. The review is limited to registration/default inputs, cited shape and predicate behavior, and these witnesses; growth, jukebox interaction/audio, broader fence attachment behavior, and player movement are outside scope.

## Source-file bindings

| Version | File | SHA-256 |
|---|---|---|
| 1.8.9 | `Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 | `PaneBlock.java` | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 | `SugarCaneBlock.java` | `6d6f21e4d4aec3ba8eaa5980265db1b45a1f2326a745ff758ff660958e295ca2` |
| 1.8.9 | `JukeboxBlock.java` | `546e3933a4876aa8ccfff8834ade5a1ae432cadcf9b692964465361b4a5fda89` |
| 1.8.9 | `BlockWithBlockEntity.java` | `9126813af904415188c993830f592326fd78f1b9c9e6224373256f6ac23ce175` |
| 1.8.9 | `FenceBlock.java` | `8d6a803988d77fc51f364520f2e0cd5567b1d5c29a4d28d01cdcf93e6d5890d9` |
| 1.9.4 | `Block.java` | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 | `PaneBlock.java` | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 | `SugarCaneBlock.java` | `2296213eed8c853a81447288233a1aee30fa97bbd81ee235112debdd7b416eca` |
| 1.9.4 | `JukeboxBlock.java` | `893726a800dc15ec64098734e12f6421be1cd2789981bcca13609b71d9827f31` |
| 1.9.4 | `BlockWithBlockEntity.java` | `94455367c7160e48d0f8bf3b2d49265bc8ba1365f3ae3ac91bc2770e4de30816` |
| 1.9.4 | `FenceBlock.java` | `d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909` |

## Review reconciliation and remaining census

The slice 40 report's next-registration claim is corrected by the separate continuation event. Slice 40's ACCEPT verdict and findings for IDs 80–82 remain unchanged. The exact source registers Fence at ID 85 in both versions, and Pumpkin at ID 86 in both; Pumpkin is the next lowest shared unclassified registration.

This additive slice classifies three registrations and does not close the registered-provider census or all world-reachable pane masks. The memo's stated partial census count is not independently audited here. Complete provider closure, reachable-mask closure, an independent coverage audit, and original derived-JAR equivalence remain open. No implementation, build, test, game/TAS/Gym/server launch, Docker restart, source write, merge, or push was performed.
