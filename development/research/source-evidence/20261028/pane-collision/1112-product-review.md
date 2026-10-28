# Product review: 1.11.2 pane neighbor integration

## Verdict

**ACCEPT** commit `4c46d2805d74ae8a2a92c1a2e5f3388049c0b291` (`Integrate 1.11.2 pane neighbor policy`), parent `a0448a5d12d6fb714da8a14562eab5db05a8affe`, branch `feat/pane-1112-neighbor-integration`.

## Review findings

- The integration adds one `PaneNeighborEligibilityHook` technical operation. The `V1_11_1` native barrier, `V1_11_2` default-state selector, and `V1_12` FaceShape policy compose under `ChangeResolver`'s nearest change at or after selected version rule. This avoids duplicate resolution for one hook and preserves the existing V1_12 policy interval. The V1_11_1 barrier is below the new 1.11.2 boundary.
- The 1.11.2 selector implements the accepted registered-ID provider table: 133 cube IDs, 35 explicit pane/glass exceptions, 257 false-provider IDs and 16 slab aliases disambiguated only by `SlabBlock.TYPE == SlabType.DOUBLE`. Explicit pane/glass cases precede false-provider classification. Unknown IDs and ambiguous states without `TYPE` return `NATIVE`.
- The accepted slab supplement supersedes the interim no-slab conclusion in `closure.md`; the final source bundle binds the supplemental selector evidence. This supports the bounded pane eligibility selector, without claiming metadata conversion or general geometry equivalence.
- `PaneCollisionComponents.selected` supplies the shared component order and bounds. The accepted 1.11.2 source finding establishes identical literals and collector order across 1.11.2 and the 1.12 line; its lower-fence audit establishes strict component admission and axis clipping. For 1.11.2, the per-interface resolver continues to select the existing V1_12 movement collector because no V1_11_2 collector change is added; accepted evidence bounds that collector operation as unchanged across these source versions. The V1_12 shape path now delegates through the new hook and retains `PaneNeighborFaceShapePolicy`, preserving the accepted later directional predicate and the modern-stair unknown-dependency fallback.
- Native fallback is wired at both call sites. In `BlockStateCollisionShapeMixin`, an empty `collisionShape` result does not replace the vanilla shape. In `MovementRuntime.collectMovementBlock`, an empty result from a resolved provider returns `nativeShape` before collection. `MovementCollisionContext.delegate()` retains the entity context, so `playerFrom` in the pane provider resolves the gated player. `MovementRuntime.appliesTo` restricts behavior to enabled client-side players and excludes logical servers, including integrated servers.
- Discovery is client-side and automatically registers annotated hook interfaces. New concrete change classes are public, annotated, and have public no-argument constructors (or a class-local public factory for keyed shape variants). No manual catalog/provider loop or core resolver modification was introduced.
- No semantic conflict with current `main` was found: `a0448a5d12d6fb714da8a14562eab5db05a8affe` is an ancestor of the reviewed commit and is its direct parent. The worktree is clean. `git diff --check` passes for the reviewed commit.

## Changed files reviewed

- `src/client/java/me/wolfii/legacyparkourcompat/change/common/PaneCollisionComponents.java`
- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_11_1/PaneCollisionNative.java` (rename from `v1_11_2` native fence)
- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_11_1/PaneNeighborEligibilityNative.java`
- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_11_2/PaneCollisionShape.java`
- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_11_2/PaneNeighborEligibility.java`
- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_12/PaneCollisionShape.java`
- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_12/PaneMovementComponents.java`
- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_12/PaneNeighborFaceShapePolicy.java`
- `src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/block/pane/PaneNeighborEligibilityHook.java`

## Evidence and limits

Bound source/candidate artifacts and their SHA-256 values are recorded in `hash-manifest.txt`. No product files were edited. No build, tests, game launch, or runtime check was performed. This is a static product integration review; it does not establish tick-level runtime parity or expand the accepted evidence to modded IDs, unknown blocks, arbitrary metadata conversion, or unreviewed source versions.
