# Edge escape product reconciliation

## Scope and evidence identity

Read-only reconciliation of the supplied Shulker push-out witness and edge-backoff findings against the product checkout. No product code, source, backlog, build, test, or game launch was changed or run.

- Shulker witness: `.native-source/20261030/1152-1165-finish43/suffocation-provider-witness-independent-audit/report.md`, SHA-256 `214e87591315d984782e035e51261666d7ca96f6e02648e14d18baaa5906547d` (identity supplied by coordinator).
- Positive-Y gate finding: `.native-source/20261030/1182-1194-inventory44/world_collision/edge-backoff-finding.md`.
- Foot-slab rewrite draft: `.native-source/20261030/1204-1206-pool44/drafts/player-edge-backoff-collision-probe.md`.

B/C reconciliation is not independent source acceptance. Applicability claims below are limited to supplied evidence and existing product comments; no parity claim is made from source correspondence or compilation.

## Findings

### A. Open-Shulker local-player push-out — already covered for the witness

The audited witness's changed decision is the open Shulker `isSuffocating` predicate during the same four `LocalPlayer` escape probes. Product dispatch is in `src/client/java/me/wolfii/legacyparkourcompat/mixin/LocalPlayerMixin.java`, in `legacyparkourcompat$suffocationQuery`: it resolves `SuffocationProbeHook` and passes the target profile and native result. The vanilla `moveTowardsClosestSpace` call path remains responsible for selecting/writing the push-out direction and velocity; the hook changes its occupancy answer.

For profiles 1.14 through 1.15.2, closest-applicable resolution selects `change/v1_15_2/SuffocationProbe.java` (`@MovementChange(V1_15_2)`). It explicitly treats any Shulker block as suffocating for targets at least V1_14, preserving the old open-box predicate. For 1.16 and later, `change/v1_16/SuffocationProbe.java` (`@MovementChange(V1_16)`) uses the current block-state predicate except for the separately reverted farmland/soul-sand/dirt-path behavior; an open Shulker therefore does not pass the suffocation test. These implementations match the concrete old/new witness. `SuffocationProbeColumn` supplies the shared column scan.

The exact local-player responder is not reimplemented, and does not need a new hook for this witness: the reported sample coordinates floor to the same block cell in both probes, so the changed predicate is sufficient to explain the old/new response. This disposition covers the supplied witness, not every possible `moveTowardsClosestSpace` probe geometry.

### B. Positive-Y request gate — represented by existing paths

`PlayerMixin.java` dispatches the whole `maybeBackOffFromEdge` operation through `SneakEdgeHook` at method head. The versioned paths are:

- `change/v1_18_2/SneakEdge.java` (`@MovementChange(V1_18_2)`) runs the edge-backoff loops without a requested-Y condition. Its source comment records the 1.18.2 boundary and the earlier no-Y-gate behavior.
- `change/v1_19_3/SneakEdgeNativeFence.java` (`@MovementChange(V1_19_3)`) returns `Optional.empty()`, preserving the native method path for the represented 1.19.3 boundary.
- `change/v1_20_2/SneakEdge.java` (`@MovementChange(V1_20_2)`) explicitly admits the loops only when `delta.y <= 0.0`; it also keeps the older full-box support probe.

`ChangeResolver` selects, per hook, the lowest `emulates` ordinal greater than or equal to the selected profile (`src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java`). Thus V1_18_2 selects the no-gate handler, while V1_20_2 and V1_19_4 select the handler with the gate. V1_19/1.19.2 resolve to the V1_19_3 native fence, leaving the native method to apply its 1.19 nonpositive-Y rule. This is existing coverage of the supplied boundary, subject to the product's native-fallback model; it does not expand the evidence finding's unknown earlier-than-1.18.2 or later-than-1.19 applicability.

### C. 1.20.5 feet-slab probe/backoff rewrite — not reachable through current product dispatch

The draft describes a method-level rewrite: `maybeBackOffFromEdge` changes the support and per-axis probes from a full box lowered by `maxUpStep` to `canFallAtLeast` feet-slab probes, and changes the decrement/cutoff behavior. Product currently has:

- `change/v1_20_2/SneakEdge.java` as the closest `SneakEdgeHook` for V1_20_5 and all later profiles. It replaces the native method at HEAD and calls `SneakEdgeBackoff.apply`.
- `change/common/SneakEdgeBackoff.java`, whose axis and diagonal loops probe `getBoundingBox().move(..., -probeDistance, ...)` and use the older `reduceTowardZero` comparisons.
- `change/v1_21_4/EdgeBackoffProbeBounds.java` as a `SneakEdgeProbeHook`, implementing the feet-slab AABB for `Player#canFallAtLeast`.
- `PlayerMixin.java` interception of `canFallAtLeast` at HEAD.

For V1_20_5, the closest `SneakEdgeHook` is V1_20_2, so its head injection returns a replacement vector before native `maybeBackOffFromEdge` can call `canFallAtLeast`. The V1_21_4 `SneakEdgeProbeHook` is therefore not reached through this movement path. Separately, `SneakEdgeBackoff` does not implement the draft's cutoff/axis-zeroing control flow. This is a real capability gap if C is independently accepted with the stated boundary.

Smallest reconciliation after acceptance: establish a V1_20_5 `SneakEdgeHook` boundary so V1_20_5 no longer resolves to the V1_20_2 full-box implementation, and bind the feet-slab probe behavior at its evidenced V1_20_5 boundary. A V1_20_5 native-fallback fence would expose the existing `canFallAtLeast` hook only if the running native method is shown to retain the desired 1.20.5 method behavior; otherwise the V1_20_5 hook must implement the full accepted method and receive/use the independently resolved probe capability. The current V1_21_4 annotation is later than the draft's first changed release and does not itself prove the boundary. Until B/C source acceptance closes the gates, this is a conditional reconciliation finding, not an implementation instruction or parity claim.

## Relevant product paths

- `src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java`
- `src/client/java/me/wolfii/legacyparkourcompat/mixin/PlayerMixin.java`
- `src/client/java/me/wolfii/legacyparkourcompat/mixin/LocalPlayerMixin.java`
- `src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/collision/SneakEdgeHook.java`
- `src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/collision/SneakEdgeProbeHook.java`
- `src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/collision/SuffocationProbeHook.java`
- `src/client/java/me/wolfii/legacyparkourcompat/change/common/SneakEdgeBackoff.java`
- `src/client/java/me/wolfii/legacyparkourcompat/change/common/SuffocationProbeColumn.java`
- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_15_2/SuffocationProbe.java`
- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_16/SuffocationProbe.java`
- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_18_2/SneakEdge.java`
- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_19_3/SneakEdgeNativeFence.java`
- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_20_2/SneakEdge.java`
- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_4/EdgeBackoffProbeBounds.java`

### Resolver interaction to preserve at the probe hook

`EdgeBackoffProbeBounds` is the only `SneakEdgeProbeHook` change and is annotated V1_21_4. Under `ChangeResolver`'s closest `emulates >= selected` rule it is also the selected probe change for earlier profiles whenever their running `Player#maybeBackOffFromEdge` reaches `canFallAtLeast`. The V1_19_3 `SneakEdgeNativeFence` makes the whole edge method native, but it does not fence the separate probe hook. Therefore the positive-Y gate is covered as a method-level condition, while an assertion that the 1.19 native-fallback path retains the old full-box probe cannot be made from this dispatch alone. C's possible V1_20_5 boundary should be resolved on the probe operation too; B's accepted gate alone does not establish the probe behavior boundary. The probe interface currently has no `Optional`/native-fallback result, so a lower barrier needs an explicit behavior (or a dispatch/signature that can preserve native behavior), not just an unimplemented marker.

## Erratum — resolver direction correction (supersedes C verdict above)

The original C verdict above is incorrect because it treated the V1_20_2 `SneakEdgeHook` as applicable to V1_20_5. `ChangeResolver.resolve` filters candidates with `change.emulates().newerThanOrEqual(selected)` before choosing the lowest ordinal. V1_20_2 is older than V1_20_5 and is therefore excluded for that profile (and later profiles). Preserve the original analysis above as recorded, but disregard its statements that V1_20_2 preempts native `maybeBackOffFromEdge` at V1_20_5 and that this creates a preemption gap.

Correct V1_20_5 dispatch:

- There is no `SneakEdgeHook` change with `emulates >= V1_20_5`: the latest one is `change/v1_20_2/SneakEdge.java`. Thus `PlayerMixin.legacyparkourcompat$maybeBackOffFromEdge` finds no applicable hook and does not cancel the native method. The native 26.2 `Player#maybeBackOffFromEdge` path runs, including its own backoff decrement/cutoff flow.
- `EdgeBackoffProbeBounds` is the sole `SneakEdgeProbeHook` change and is annotated V1_21_4. For a V1_20_5 profile, it is the closest applicable probe change under the resolver rule. When the native method calls `Player#canFallAtLeast`, `PlayerMixin.legacyparkourcompat$historicalSupportProbe` supplies the feet-slab AABB calculation in that change.
- Therefore the product dispatch does not have the alleged 1.20.5 full-box override gap. The feet-slab probe has an applicable implementation, and the revised per-axis loop/cutoff is left to native `maybeBackOffFromEdge`. This is a no-code reconciliation disposition for the alleged gap.

C remains a draft, so its independent source acceptance is still open. Existing code comments for `EdgeBackoffProbeBounds` cite later source representatives and explicitly lack an independently accepted exact source binding. Reconciliation therefore establishes dispatch reachability, not that 26.2's native loop is source-identical to the draft's 1.20.5 algorithm or that V1_21_4 is the earliest annotation justified by C. If acceptance establishes the 1.20.5 boundary and method identity, the annotation/evidence can be reconciled then; do not add a V1_20_5 SneakEdgeHook solely to bypass the older V1_20_2 change, since the resolver already excludes it.
