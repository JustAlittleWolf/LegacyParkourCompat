# Product reconciliation: bed wake placement and opened Shulker escape

Product snapshot: `f39f584ecc72c18b2916dc9041e8d04404181068` (`fix/resume-movement-20261030`). Product files were inspected read-only. No product source, tests, builds, launches, or decompilation were changed or run.

## Accepted input identity

- Bed canonical finding: `canonical-findings/1.16.1--1.16.2/bed-wake-stand-up-position.md`, SHA-256 `508f35100e17eadb6a0d94b688d899b9012c01f08bf20816af680f0fc885d4bc`.
- Bed renewal review: `bed-wake-witness-independent-review/renewal-boundary-508f3510.md`, SHA-256 recomputed as `6caf306eaaab5523864e0de8a9865e71ece5a6fe0fd79e0c376f708a7e0c3ec6`. The review accepts the finding hash above.
- Bed exact-boundary report: `direct-response-boundaries/report.md`, SHA-256 `1bfa29de90e99fbb1a82370035d8f7edad2d5f85162458f0d5a6607de137376`.
- Shulker canonical finding: `canonical-findings/1.15.2--1.16/open-shulker-stops-local-escape-impulse.md`, SHA-256 `50147c54a3b34d644f52799feb2b32dc0726a751f3d5f52334db492ea3b5dd27`.
- Shulker canonical-binding review: `localplayer-residual-independent-review/canonical-binding-review.md`, SHA-256 `2dbde38b83185c9968e8538c7258de94f65ea078dabac654ac94d7d3928f235f`.

## Bed wake stand-up position: missing product operation

**Disposition: no-code gap; do not conflate with bed-entry placement or the existing border exception.**

The product has `SleepPositionHook` (`src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/player/SleepPositionHook.java`) with entry-only contract “position assigned after a player successfully enters a bed.” Its implementations are `change/v1_10_1/SleepPosition.java` and `change/v1_13/SleepPosition.java`; they compute the position used when sleeping starts. They do not implement `LivingEntity.stopSleeping` / `BedBlock.findStandUpPosition` wake selection.

The only existing wake-specific pieces are `mixin/border/LivingEntityWakeContextMixin.java` and `mixin/border/DismountHelperWakeBorderMixin.java`, with `change/v1_17_1/WakeBorderAdmission.java` and `mechanic/hook/border/WakeBorderAdmissionHook.java`. This is a narrow world-border candidate-admission delta. It leaves candidate ordering, support-height selection, and the final wake position to native code. It does not cover the accepted 1.16.1→1.16.2 change.

**Smallest missing operation:** add one independently resolved client-player wake-position/search hook for the complete paired bed candidate search and support algorithm, then route the existing native wake call through it with native fallback. Keep bed-entry `SleepPositionHook` independent. Do not compose a new-result yaw order with only an old helper: the accepted evidence attributes the result to the complete paired algorithm.

**Boundary and resolver consequence:** 1.16.1 retains the old x/z grid search and `2.0E-7` support-Y adjustment; 1.16.2 introduces the yaw-ordered candidates and `DismountHelper` support path, which persists through 1.16.5. The accepted report also checked old search forms in 1.15.2 and 1.16.0. Under closest-applicable-at-or-after resolution, an old-behavior change annotated `emulates = V1_16_2` can serve selected profiles through 1.16.1; profiles selecting 1.16.2 or later retain native behavior. Bed-entry eligibility remains out of scope.

## Open Shulker escape impulse: covered

**Disposition: no additional product code required for this finding.**

The resolved product operation is `SuffocationProbeHook` (`src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/collision/SuffocationProbeHook.java`), dispatched from `mixin/LocalPlayerMixin.java` at `LocalPlayer.suffocatesAt`. Its old-side implementation is `change/v1_15_2/SuffocationProbe.java`, annotated `V1_15_2`: for the legacy target range beginning at 1.14, it treats every `ShulkerBoxBlock` as suffocating, regardless of open state. Its new-side implementation is `change/v1_16/SuffocationProbe.java`, annotated `V1_16`: it delegates to the state’s registered `isSuffocating` predicate, after the separate 1.16.2 farmland/soul-sand/dirt-path correction. The opened Shulker’s 1.16 registered predicate is false for a matching open block entity, so it does not qualify as a blocking probe. `SuffocationProbeColumn.any` preserves the vertical column probe in both variants.

This matches the accepted direct response: 1.15.2's open Shulker still causes LocalPlayer's escape probe and its final `+0.1` X velocity write; from 1.16.0, the opened block no longer satisfies that suffocation probe, so the velocity response is omitted. The product's `v1_15_2` and `v1_16` annotations place that cutover correctly under the closest-applicable resolver. The product's v1_16 comment currently documents the later three-block suffocation predicate correction but not this already-covered open-Shulker behavior; adding a concise source-evidence comment would improve traceability, but is not needed for the behavior or a new hook.

The accepted witness is conditional on the matching opened block entity being loaded and surrounding columns being clear. This disposition covers the direct velocity response, not subsequent travel or trajectory.
