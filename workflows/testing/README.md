# Validate deterministic player movement

This workflow is for later explicitly authorized lab work. Preparation, source acceptance and compilation do not authorize tests, Minecraft/TAS/Gym/server launches or Docker restarts. The lab tools live under `development/{tas-client,parkourgym-server,testing}`; read their module guides before execution.

## Validation order

1. Record a bounded scenario in the exact Minecraft version being tested. Store version/artifact identities, initial player/world/server state, input and camera sequence, tick alignment, movement profile and recording-format identity. Include relevant environmental, equipment/effect and synchronized state; no profile switching.
2. Replay the recording in that same exact version and compare every recorded movement value tick by tick at exact precision: **zero ULP difference**, with exact discrete state. Preserve signed-zero/nonfinite representation checks where relevant. Repeat playback from the same controlled initial state to establish reproducibility. A tolerance or a single successful run cannot establish exact determinism.
3. Once same-version recording/playback is reliable, capture and replay the scenario in the exact historical reference version. Its recording/playback must satisfy the same exact repeated-run checks. Record the historical reference identity and any unavoidable external-state differences; unresolved differences block parity claims.
4. Replay the equivalent inputs and controlled state on the modern target with the fixed historical profile. Compare the modern emulated trajectory against the historical reference tick by tick. Require exact movement/discrete state agreement and zero ULP differences for relevant numeric outputs. Report the first diverging tick/value and enough prior state to reproduce it. Separate recording defects, environment divergence and movement-implementation defects.

Bound conclusions to tested versions, scenarios, fields and initial conditions. A passing scenario does not prove all movement paths or all patches in a release line. Keep the historical reference intact when correcting the implementation or capture tool.

## Result template

```markdown
# <Scenario and exact versions>
Authorization: <scope including permitted launches/checks>.
Artifacts/state/inputs: <identities and relative recording/result paths>.
Same-version replay: <exact comparison, repeat count, outcomes>.
Historical reference: <exact version, repeated replay outcomes>.
Modern target/profile: <exact version and fixed historical profile>.
Comparison: <zero ULP + discrete-state result, or first divergence>.
Finding/implementation: <immutable source finding and product commit>.
Limits and next action: <unverified conditions or corrective work>.
```

Only mark runtime parity verified after these results exist. Leave unexecuted work pending and link it from the central research progress index.
