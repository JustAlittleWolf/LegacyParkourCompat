# Validate deterministic player movement

This workflow is for later explicitly authorized lab work. Preparation, source acceptance and compilation do not authorize tests, Minecraft/TAS/Gym/server launches or Docker restarts. The lab tools live under `development/{tas-client,parkourgym-server,testing}`; read their module guides before execution.

## Validation order

1. Record a bounded scenario in the exact Minecraft version being tested. Store version/artifact identities, initial player/world/server state, input and camera sequence, tick alignment, movement profile and recording-format identity. Include relevant environmental, equipment/effect and synchronized state; no profile switching.
2. Replay the recording in that same exact version and compare every recorded movement value tick by tick at exact precision: **zero ULP difference**, with exact discrete state. Preserve signed-zero/nonfinite representation checks where relevant. Repeat playback from the same controlled initial state to establish reproducibility. A tolerance or a single successful run cannot establish exact determinism.
3. Once same-version recording/playback is reliable, capture and replay the scenario in the exact historical reference version. Its recording/playback must satisfy the same exact repeated-run checks. Record the historical reference identity and any unavoidable external-state differences; unresolved differences block parity claims.
4. Replay the equivalent inputs and controlled state on the modern target with the fixed historical profile. Compare the modern emulated trajectory against the historical reference tick by tick. Require exact movement/discrete state agreement and zero ULP differences for relevant numeric outputs. Report the first diverging tick/value and enough prior state to reproduce it. Separate recording defects, environment divergence and movement-implementation defects.

Bound conclusions to tested versions, scenarios, fields and initial conditions. A passing scenario does not prove all movement paths or all patches in a release line. Keep the historical reference intact when correcting the implementation or capture tool.

The existing lab compares positions and tick counts, and its recorder trims stationary ends. Before using it as evidence for this fuller workflow, establish that capture preserves the required state and relevant ticks; unchanged position alone does not imply unchanged movement state or irrelevant input. Use the exact comparison mode without tolerance options. Extending capture and comparison where necessary belongs to the later authorized test phase, not to a documentation or discovery assignment.

For a user-specified movement and variant scope, choose representative exact versions, block surfaces/edges, effects and equipment, avoiding redundant cases with identical relevant behavior. Reconstruct controlled initial state for each case and retain its reference identity. Later integration should expose these real-client recording/playback comparisons through the product project's test entry points. Determine safe lab concurrency from available resources during that phase; existing client-pool defaults are not a proven capacity guarantee.

## Later execution and retained data

This remains a framework for later detailed work after expert manual assessment; it does not prescribe or build new test infrastructure. Test execution reports only failures, with the first divergence and the minimum data needed to reproduce it. Do not write routine success, status or completion reports as permanent research files.

Keep native reference recordings, controlled initial-state/input data and comparison data needed to reproduce and assess a result in the authorized lab. The master orchestrator may retain a concise final association of finding, product commit, exact versions/scenario and comparison data in `development/research/backlog.json`. Only that bounded evidence can establish runtime parity; unexecuted work remains unverified. Remove a current gap record when its actual gap is closed, rather than keeping a completed remainder or archive.
