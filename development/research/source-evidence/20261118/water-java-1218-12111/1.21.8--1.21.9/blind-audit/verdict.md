# Final claim review: 1.21.8 to 1.21.9 water slice

## Immutable claim and evidence binding

- Frozen blind audit: `frozen.md`, raw SHA-256 `31fa7c504487ccd59448f883fb035c48e60e69395fc5f72076596dcc5f412ecb`.
- Author comparison reviewed: `source/comparison.md`, raw SHA-256 `2c0e11fdd1b8841f1f30bf058cf9582e8091c3f735868df4338e64801e1fa930`. This matches the authorized comparison revision.
- The comparison identifies exact Mojmap client sources and pins version/mapping evidence. Its relevant class-file hashes match the pair's evidence identity, and its compared water/travel procedure claims agree with the exact source bodies inspected.

## Verdicts

1. **Ordinary water body-height and eye-fluid prediction: accept, no difference in the bounded operations.** `Entity.baseTick` performs the fluid-height/current update before eye-fluid refresh in both versions. The water check uses the same box deflation, floor/ceil bounds, ascending x/y/z scan, matching-fluid gate, maximum surface depth, cached water predicate, eye coordinates, strict `surface > eyeY` test, and previous-eye snapshot order. The comparison's stated one-scan eye-state lag is source-supported.
2. **Water flow sampling and push: accept, no difference in the bounded operations.** The method retains identical sample order and gates, running-max-depth scaling below `0.4`, averaging, player normalization exception, `0.014` coefficient, low-horizontal-velocity minimum-current adjustment, and delta addition. This verdict is bounded by equal player classification, box, fluid samples/flow vectors, coefficient, and initial movement.
3. **First ordinary water travel move, drag, and vertical response: accept, no difference under equal current sprint/swim state and equal inputs.** The same travel gates select the same water branch; slowdown/efficiency math, `moveRelative`, self move, climbable correction, `(drag, 0.8F, drag)` multiplication, and fluid-falling adjustment retain order and literals.

## Citation correction and sprint boundary

The frozen audit's body-height/current method line ranges are too long. The exact method spans are 1.21.8 `Entity.java:3414-3479` and 1.21.9 `Entity.java:3480-3545`; the frozen record listed through 3488 and 3554. The comparison revision gives the correct method spans. This citation defect does not change the no-difference verdict; preserve the frozen file unchanged and use these corrected spans in any reconciliation.

`LocalPlayer.aiStep` changes sprint-trigger arming from hard-coded `7` in 1.21.8 to `minecraft.options.sprintWindow().get()` in 1.21.9 (source sites: 1.21.8 `LocalPlayer.java:724-727`; 1.21.9 `LocalPlayer.java:728-731`). This is an upstream sprint-state/timer boundary. The water travel slowdown is sprint-sensitive (`0.9F` when sprinting, otherwise `getWaterSlowDown()`), so the no-difference verdict does not establish equivalent water movement when these different timer transitions produce different current sprint states. The assigned water procedures are compared with equal current sprint state; the sprint timer/input transition and the separately excluded abilities-flight/run-sprint shallow-water exemption are outside this verdict.

## Limits

This review covers only the three assigned bounded paths. It does not establish full-pair inventory closure, every path producing sprint/swim state, implementation acceptance, or runtime parity. No tests, game launch, or build were run.
