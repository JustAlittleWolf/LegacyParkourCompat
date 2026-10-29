# Independent review: segment lifecycle implementation candidate

**Verdict: ACCEPT** for the neutral candidate scope.

- Candidate commit: `a827fbf461ea4f253739147a64b1dda21f4085f7` (`feat/segment-lifecycle-1214-1215-candidate-20261029`)
- Reviewed checkout: `.native-candidates/segment-lifecycle-1214-1215-impl-20261029`
- Working tree was clean at review time.
- No build, tests, game launch, or runtime checks were run, as instructed.

## Findings

No required code revisions found.

- **Hook granularity and neutral scope:** the candidate exposes separate `*Hook` interfaces for per-move recording, endpoint drain, nested-batch drain, visited-position admission, callback phase, client gate, and piston call shape. Version classes contain only source-derived neutral behavior; there are no mixins or product call-site changes. Discovery registers annotated interfaces per class.
- **1.21.4 endpoint lifecycle:** `change/v1_21_4/BlockContactLifecycle` adds `oldPosition -> position`, snapshots the live list, clears it before returning the scan snapshot, and uses the accepted non-air/visited admission before the shape check. The no-op collector methods match the synchronous callback phase. The source comments identify `LivingEntity.aiStep()`; this follows the accepted correction supplement.
- **1.21.5 recording and drain:** the physical-move gate preserves the accepted `1.0E-7` comparisons and both early exclusions. It forms ordered axis-aligned pieces in the supplied `Direction.axisStepOrder(resolvedDisplacement)` order, preserving component addition order. Drain flattens batches, clears live state, uses old/current endpoints only for an empty flattened result, and otherwise appends the last-endpoint residual only for squared distance strictly greater than `9.9999994E-11F`.
- **1.21.5 visitation and callback phase:** non-air positions are inserted into the shared visited set before independent shape and fluid tests; a miss remains consumed in this version. `beforePassingCallback` advances the collector immediately before each passing callback, and `afterSegments` delegates the final flush/apply/clear/reset to the collector. This leaves the source-defined enum order and effect execution in the collector contract rather than duplicating it in the hook.
- **1.21.6 boundary:** the code is annotated only for 1.21.4 and 1.21.5, and the 1.21.5 source comment explicitly excludes the 1.21.6 capped deque/axis-independent record model. No 1.21.6 behavior is implied by this candidate.
- **Client scope and fallback:** the gate requires a player, logical client, affected-by-blocks, non-removed, physics-enabled, locally controlled state. This is appropriate for historical client emulation. The candidate deliberately adds no product dispatch; integrating call sites must preserve vanilla execution when the client gate/profile/hook does not apply. The neutral `MovementRuntime.find(...)` API returns an empty optional in those cases, so fallback remains implementable without changing this candidate.
- **Swept traversal dependency:** the candidate supplies no replacement voxel traversal and explicitly delegates enumeration, clipping, tie order, epsilon, and integer boundaries to the accepted swept-AABB contract. Therefore the report’s corrected pair-specific DDA tie rule is not contradicted or reimplemented here.

## Evidence checked

The supplied accepted evidence was checked against the candidate’s source behavior, including the exact 1.21.4/1.21.5 lifecycle claims, visitor admission order, traversal dependency, and the 1.21.3-to-1.21.4 callback/piston caller closure. The lifecycle correction review confirms the source member is `LivingEntity.aiStep()`, matching the candidate’s Java comments.

- Lifecycle report `C4468295B043B2457E561C49D2C741D7C783F80A766EC303390CB6D889651DB6`; audit `CC9DEA88864E9A62A552F9C04AD05F7F5C9C4EB6F1DDB7609861AFA293CBF400`; correction supplement `54C53D4AF8C0602E7DB6B50232F34FD404ABAEE635F36BB24138A9C1E3A6133B`; correction review `14EB9BB2C079BC7D2AD141AD67DF006D8B99A50A79637C365E03D20BC203D5ED`.
- Visited-admission report `C43EC8E8B47EDEAE326D5A68F5A9D006492781C12B091A562585778477EA4261`; audit `8E9DE3F919E978B12F7A58DC05137C858A7591390A2E413794DA5F3A8A9AFD6A`.
- Swept-AABB report `5B7ED6EE05DDA849182D8201EC90FAB8A6B23FEAB3A860BBDFB73ED504C3B25F`; audit `4AB7F818896302A85F9E30940BB01821FE670FFBA79FF2089907EE4068C7B800`; correction supplement `1A61B3770F0031380BE1DF36A01C820126A391C07E93DCD1FF6AA4340D0E91EF`; supplement review `0EA9A493DA0CF2F6DC245C7E45A0C4898359113B1BF4560F60B8A1CD33FB864`.
- 1.21.3-to-1.21.4 caller report `11c1e42f76911b4f527e3dc360e24834d81ea15374a12f6e41fc90a25a5b0c50`; audit `66772dbc839f21fa0243b5f3e87f40be2726bd35916a53fadda62c64fcd0651f`.

This acceptance covers the candidate’s neutral source operations only. Product integration still needs to connect each hook at the exact caller boundary, preserve the vanilla fallback, and separately verify the applicable profile boundaries and runtime behavior.