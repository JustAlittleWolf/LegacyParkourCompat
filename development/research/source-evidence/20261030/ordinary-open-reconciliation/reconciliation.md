# Ordinary open movement findings: product reconciliation

Product inputs were read only. This report records current hook/change coverage and the smallest next source gate; it does not grant source acceptance, change backlog status, edit product files, or claim runtime parity.

## F-ELYTRA-GLIDE-MATH — missing operation; acceptance and boundary gate remain open

The finding compares `LivingEntity.moveRelative(float,float,float)` in 1.12.2 and 1.13.2 and identifies the regrouped fall-flying vertical acceleration/lift arithmetic, including a shared gravity factor. Current nearby providers are:

- `change/v1_12/VerticalPitchGlideLook` / `FallFlyingLookHook`: look-vector math at exactly vertical pitch; its own comment says the related source verdict and applicability are unproven.
- `change/v1_18/ElytraLiftForce` and `change/v1_18_2/ElytraLiftForce` / `ElytraLiftForceHook`: the lift coefficient passed into the modern travel path.
- `change/v1_20_2/TravelFallFlyingGravity` / `TravelFallFlyingGravityHook`: gravity value for the modern fall-flying velocity update.

None implements the independent grouped arithmetic in the 1.12.2–1.13.2 `moveRelative` branch. Do not treat look-vector, lift-force, or gravity-value hooks as coverage; a new minimal hook would need to preserve the source expression grouping, intermediate float/double types, and statement order for the fall-flying velocity update, with its invocation limited to the local player's historical fall-flying path and native fallback for other entities/features. The natural candidate annotation is the first confirmed profile exhibiting that exact group, but this report does not establish that boundary.

The backlog records no exact independent source acceptance for F-ELYTRA-GLIDE-MATH. Existing `.native-source` reports found in the scoped search do not independently accept this exact pair/operation; the 1.13.2→1.14 Feather audit explicitly left Elytra arithmetic equality open and excluded fall-flying from its grounded follow-up. **Disposition:** do not implement yet. Smallest gate: independent exact-source review of both 1.12.2 and 1.13.2 method bodies plus reachable local-player dispatch, then verify the first stable release/selected-profile boundary (including intervening 1.13 releases) before assigning a provider version.

## F-017 Auto-jump entity collision query bounds — no ordinary implementation; witness gate open

No current hook implements this entity-query bound operation. `AutoJumpCollisionOrder` (`change/v1_17_1`) and `AutoJumpBorderCandidate` (`change/v1_17_1`) cover distinct collision ordering and border candidates; they must not be reused as query-bound coverage.

The supplied finding says the 1.14.3+ query bounds differ, but the entity providers available to local-player auto-jump return null or the entity bounding box, and the later candidate-segment test is contained by both queries for ordinary player heights. The existing source-only `.native-source/20261028/frontier-c/AD-empty-stream-gate/empty-stream-epsilon-gate.md` narrows the 1.14.3 size gate: it is unreachable for dimension-valid player bounds at representable coordinates, while extreme-coordinate degenerate bounds remain unproven. The paired patch alias closure at `.../AD114-empty-stream-patch-alias/alias-report.md` identifies 1.14.3 as the first observed epsilon gate and confirms it in 1.14.4. Neither report establishes a changed auto-jump decision.

The backlog's existing source review is **REVISE; no concrete changed-jump witness**, and identifies the sleeping/dying 0.2F pose as the residual. **Disposition:** no code. Smallest gate: source-review a reachable sleeping/dying ordinary-player route through auto-jump input/on-ground/head-space filters to a changed timer/next-tick jump, or close that route negatively. Do not infer this witness from block order, world-border candidates, or the epsilon predicate alone.

## Eye submersion context/chunk gate — 1.13 behavior covered; 1.14 behavior missing and boundary gate open

The existing `PlayerEyeWaterQueryHook` is the right operation for the player's cached WATER query. `change/v1_13/fluid/PlayerEyeWaterQuery` supplies the 1.13.2 point sample and strict `getOwnHeight() + 0.11111111F` predicate; `PlayerMixin#legacyparkourcompat$queryCachedEyeWater` dispatches it from `Player.updateIsUnderwater`. `ContextFreePlayerEyeSubmersion` plus `NativeBeforeEyeSubmersion` separately preserve the historical context-free eye predicate in the `EntityFluidInteraction` tracker path. These implementations cover the old point-query behavior, including no eye-chunk preflight; they do not establish 1.14 behavior.

There is no later `PlayerEyeWaterQueryHook` provider for the reported 1.14.4 operation. The 1.13 provider remains the closest provider for later selectable profiles. A bounded source report, `.native-source/20261028/eye-admission/finding.md`, establishes the 1.13.2 point query. Read-only exact 1.14 Feather `Entity.java:m_69330161(Tag, boolean)` has the `bl && !world.isChunkLoaded(...)` false branch and computes the threshold using `fluidState.getHeight(world, blockPos)`; 1.14.4 Feather retains those operations. Thus 1.14.0 is a source-confirmed candidate cutover, although this product review does not independently accept it: caller arguments for the cached player refresh and the 1.14.0–1.14.4 alias chain still need an accepted bounded source report. The backlog records an accepted 1.13.2/1.14.4 endpoint review, but the referenced reviewer snapshot is outside the checked `.native-source` tree; I did not treat that metadata alone as renewed acceptance. The separate `.native-source/20261028/eye-fluid-query-closure/comparison-r2.md` covers 1.21.11→26.x and is not evidence for the 1.14 cutover.

**Disposition:** existing old-version behavior is already covered; the genuinely missing part is the 1.14 cache-query behavior, not a second 1.13 hook. Once the source boundary is closed, extend the existing `PlayerEyeWaterQueryHook` with a 1.14 provider that (a) returns false if the sampled eye block's chunk is not loaded, without loading it, and (b) uses the exact 1.14 contextual fluid height (full block when the same fluid is directly above, otherwise the level-derived amount), retaining the strict eye comparison and `+ 0.11111111F` arithmetic. Do not implement that provider under this reconciliation assignment.

The backlog-recorded endpoint acceptance does not supply a proven implementation range in the inspected source reports. Smallest gate: an independent source-only review of the exact 1.14.0 caller's `checkLoaded=true` use and relevant fluid helper, then verify .1–.4 patch aliases (or set a narrower provider annotation) and confirm the local-player cached consumer/dependency route. The old point-query implementation is evidence for the earlier side, not proof of the first later cutover.

## Scope

No product edits, builds, tests, game launches, or source verdicts were made. Exact decompiled sources under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft` were treated as read-only. Product review here is limited to operation coverage and integration contracts.
