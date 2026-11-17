COMPLETED — read-only product correspondence for corrected flowing-water current finding.

Inputs verified by SHA-256:
- Finding `development/research/findings/1.12.2--1.13.2/flowing-water-current-level-and-depth.md`: `242fdf2f38f1e9c7cce581ea7312ce8aea5bea18c8d05fcba81a29c90368def7`.
- Acceptance `build/mcpk-20261116/vd-full-checklist/accept-water-current-corrected/acceptance.md`: `45bad09584c7882fb834d7c706de7ad4a338ef470aedfbeb9270621aa5e47fc0`.

Product mapping and resolver:
- Normalize aliases: `ParkourVersion.V1_12` covers 1.12/1.12.1/1.12.2; `V1_13` is 1.13.0; `V1_13_1` covers 1.13.1/1.13.2 (`api/ParkourVersion.java`, enum lines 76–78). Finding/acceptance place the checked change boundary at 1.13.0 and confirm the operation unchanged through 1.13.2; no earlier patch boundary is proven.
- `ChangeResolver` independently chooses the closest annotation at-or-after selected profile (`impl/ChangeResolver.java`, lines 10–33). For the loaded-scan hook at selected V1_13/V1_13_1, the selected provider is V1_16_2 `FluidScanLoadedChunks`; its guard returns `Optional.empty()` for selected versions older than V1_16 (`change/v1_16_2/FluidScanLoadedChunks.java`, lines 15–39). Thus current native loaded-area preflight remains active for 1.13 profiles. This is appropriate: the corrected finding says the early return already existed in 1.12.2 and does not claim it as a 1.13 delta.
- For both current-depth hooks, the only provider is `v1_21_11/FluidCurrentDepthChange`, so it is selected for V1_13 and later. Its water applicability guard is selected >= V1_13 (`change/v1_21_11/FluidCurrentDepthChange.java`, lines 32–47); this is correctly aligned with the accepted 1.13.0 boundary for those two depth arithmetic operations.
- `v1_13/PlayerEyeWaterQuery` and `ContextFreePlayerEyeSubmersion` are eye-water cache/submersion operations, not current scan/current-vector hooks; do not treat them as implementation of this finding.

Correspondence / disposition:
- Partial relevant support exists: `FluidCurrentDepthChange` restores water surface arithmetic `(double)(blockY + fluidHeight)` and a deflated-box depth origin for selected >= V1_13. `FluidCurrentDepthMixin` changes only the first argument of native `Math.max`; its own comment explicitly leaves native admission, eye checks and current accumulation intact (`mixin/fluid/FluidCurrentDepthMixin.java`, lines 35–66). This does not implement the accepted per-contributor running-depth scaling and averaging.
- No product hook/provider implements the accepted 1.13 current-cell scan surface admission predicate (qualify `cellY + fluidState.getHeight() >= contractedBox.minY`) or reproduces the 1.13 per-contributor immersion scaling/aggregation. Those remain native 26.2 scanner/accumulator behavior. Current `FluidCurrentSurfaceHook` controls the computed depth value only after native admission; it is not the cell-admission predicate.
- No product hook/provider implements the player-only no-normalize current-vector result. The current scan continues to call native `FluidState.getFlow`, and native `EntityFluidInteraction.Tracker.applyCurrentTo` remains in force except for separate weak-current threshold/minimum seams. Thus player-vector normalization/application is a gap for 1.13 profiles.
- Separate current threshold work exists: V1_15_2 `FluidCurrentMinimum` returns false and the V1_21_11 shallow-water cutoff provider permits any positive water depth. These are separate operation seams and do not supply the missing scan/aggregation/no-normalize behavior.
- Actual fallback: the unimplemented pieces above use current native 26.2 scanner/aggregation/vector handling. The loaded-area path also falls back to native current preflight for 1.13 because selected V1_16_2 provider returns empty. The existing V1_21_11 `ClientFluidScanAdmission` is not selected for those profiles; it is the closest provider only at V1_21_11+, where it returns true.

No product files changed. No source re-audit, implementation, backlog/Git access, test/build/runtime action performed.

Scratch packet: `build/mcpk-20261116/vd-full-checklist/product-water-current-corrected/terminal.md`.

