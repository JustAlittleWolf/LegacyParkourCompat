# Verify deterministic player movement

Use this framework only for explicitly authorized lab work after expert manual assessment. Detailed test integration remains future work. Preparation, discovery acceptance and compilation do not authorize tests, Minecraft/TAS/Gym/server launches or Docker restarts.

## Prepare the comparison

1. Before execution, read the [lab](../../testing/README.md), [TAS](../../tas-client/README.md) and [Gym](../../parkourgym-server/README.md) guides in full.
2. Select cases from the requested movement and variant scope: exact versions, block surfaces/edges, effects and equipment. Avoid redundant cases with identical relevant behavior. Determine safe concurrency from actual resources.
3. Establish that capture preserves relevant ticks and movement state. The existing lab compares XYZ and tick count and trims stationary ends; unchanged position does not establish unchanged state or irrelevant input. Address missing capture/comparison capabilities during the authorized test phase.
4. Retain exact version/artifact and recording-format identities, controlled initial player/world/server state, inputs/camera sequence, tick alignment, effects/equipment and a fixed profile. Unresolved external-state differences block parity claims.

## Establish determinism, then parity

1. Record and replay in the same exact native version. Repeat playback from identical initial state. Require exact discrete state and zero ULP difference in relevant numeric outputs, preserving signed-zero/nonfinite checks where relevant. Omit tolerance options; one passing run is insufficient.
2. Establish the same repeated-run determinism for each historical native reference, without historical emulation. Preserve the reference and its exact identity.
3. Replay equivalent inputs and controlled state on the modern client with the fixed historical profile. Compare every relevant movement value and discrete state tick by tick with the historical reference.
4. Report the first diverging tick/value and enough prior state to reproduce it. Distinguish capture defects, environment divergence and movement defects. Bound conclusions to inspected fields, versions, scenarios and initial conditions.

When implementing the later test integration, expose these real-client recording/playback comparisons through the product project's test entry points. Preserve historical references when fixing the capture tool or movement implementation.

## Return and retain evidence

Report failures only during execution; do not create routine success/status reports. Keep native recordings, controlled setup/input and necessary comparison data in the authorized lab. Send the master a concise finding/commit/version/scenario/data association for `development/research/backlog.json`. Leave unexecuted work unverified and remove gap records only when their actual gaps close.
