# Discovery: 1.11.2 to 1.12.2

- Status: active
- Track: source-only; no wiki; no mod implementation
- Scope: direct client player movement; older A = 1.11.2; newer B = 1.12.2
- Repository revision and start date: `002137b227676caea77f6832b9f4c8d0b6200bff` (main); 2026-10-07
- Working branch: `feat/source-discovery-movement-source-1-11-2-1-12-2`
- Naming namespace requested: Ornithe Feather on both exact releases, release-specific mapping artifacts
- Source preparation: shared source-owner package validated; exact readiness records and every listed source/artifact hash verified. Researcher did not run the decompiler.
- Source command: exact `--versions=1.11.2 --mappings=feather` and `--versions=1.12.2 --mappings=feather` runs were successful per readiness diagnostics; raw Gradle logs not retained. Toolchain versions are recorded below.

## Artifact manifest

### A — 1.11.2

- Requested/resolved: 1.11.2 / 1.11.2; readiness `ready/1.11.2/ornithe-feather.ready.json` confirms `versionId` and `versionMetadataId` both exactly `1.11.2`.
- Source root: `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather` (shared artifact root in the primary checkout).
- Original client jar SHA-256: `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`.
- Namespace / CLI mode: Ornithe Feather / `feather`; mapping artifact `net.ornithemc:feather-gen2:1.11.2+build.2`.
- Mapping jar SHA-256 `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757`; Tiny mapping SHA-256 `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`.
- Remapped client jar SHA-256: `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3`.
- Source manifest `ready/1.11.2/ornithe-feather.sources.sha256`, SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; all 1,921 listed files were present and their hashes matched.
- Artifact manifest `ready/1.11.2/artifacts.sha256`, SHA-256 `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`; all 37 listed artifacts were present and their hashes matched. Diagnostics `ready/1.11.2/movement-diagnostics.txt`, SHA-256 `8bb3b1169a21958a6570fc304a0a95d9c51cc3a2101869bcbd453c3588925cdc`.

### B — 1.12.2

- Requested/resolved: 1.12.2 / 1.12.2; readiness `ready/1.12.2/ornithe-feather.ready.json` confirms `versionId` and `versionMetadataId` both exactly `1.12.2`.
- Source root: `build/movement-campaign-2026-10-07/ready/1.12.2/ornithe-feather` (shared artifact root in the primary checkout).
- Original client jar SHA-256: `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`.
- Namespace / CLI mode: Ornithe Feather / `feather`; mapping artifact `net.ornithemc:feather-gen2:1.12.2+build.2`.
- Mapping jar SHA-256 `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca`; Tiny mapping SHA-256 `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`.
- Remapped client jar SHA-256: `65a08f15d18c4ec2bd0f05b89dfc1ba7ea6b8280ba93ed136245f39e63ec8a2b`.
- Source manifest `ready/1.12.2/ornithe-feather.sources.sha256`, SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; all 2,050 listed files were present and their hashes matched.
- Artifact manifest `ready/1.12.2/artifacts.sha256`, SHA-256 `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`; all 38 listed artifacts were present and their hashes matched. Diagnostics `ready/1.12.2/movement-diagnostics.txt`, SHA-256 `1ae1a796ac7650bf218af02eb602e1b7f46df2950e57b14263c65d3b58dc71b3`.

### Shared provenance

- Repository commit: `002137b227676caea77f6832b9f4c8d0b6200bff`.
- Toolchain/options from the source-owner readiness package and repository: decompiler JVM Java 25; Gradle wrapper 9.7.1; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1. Decompiler task uses generic signatures, ASCII strings, synthetic removal, four-space indentation, hardware-available threads (minimum 1), skips Java runtime, and decompiles `net/minecraft` and `com/mojang` prefixes.
- Source hashes: complete source-tree manifests verified above; record each cited source and resource hash in its coverage row/finding.

## Correspondence and call order

A/B correspondences confirmed so far: `LocalClientPlayerEntity extends ClientPlayerEntity extends PlayerEntity extends LivingEntity extends Entity`; each class/member uses the same Feather names. `Minecraft` installs `KeyboardInput`; it is the only client class extending `Input` in both source trees. `LivingEntity.tick()` dispatches `this.mobTick()` (A line 1643; B 1681); the dynamic local-player override samples input, then delegates `LocalClientPlayerEntity.mobTick -> PlayerEntity.mobTick -> LivingEntity.mobTick`. On the locally controlled path, `LivingEntity.mobTick()` calls `serverTickAi()` before jump processing and relative movement (A lines 1776-1804; B 1820-1848); the local override copies sampled input into sideways/forward/jump movement fields. Full travel, collision, shape/data and external-writer correspondence remains open. Do not treat prior reports or names alone as complete correspondence.

## Coverage ledger

Every row remains `pending` until both exact source trees and dependency closure have been verified. The stages follow `workflows/movement-discovery/source-navigation.md`; after source readiness, split rows further when a bounded unit exceeds the method/context targets.

### Stage 1 — local input and tick ordering

- `S1.1` Keyboard/controller input sampling, directional flags, analog impulses, diagonal normalization and sneak/item-use scaling — `compared-no-difference`. A/B `Input#tick()` and `getMovement()` (source lines 15-20) and `KeyboardInput#tick()` (13-50) are byte-identical; both `Input.java` SHA-256 `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50`, both `KeyboardInput.java` SHA-256 `7be11425906be051c83e275f359816546e4677b16d212156a871f2a7d64cc64f`. Only `KeyboardInput` extends `Input`; `Minecraft` installs it at A lines 1894/1927, B 1953/1991. Movement key default bindings and codes match (`GameOptions` A 109-115; B 117-123), with class hashes A `3a28b6a6540a134fb24e2d3517465020ee8a2d095db5a754b96176abc0ae2ec1`, B `301be9703fdbd4169ef614ca41d2f7cb8508ebc76f89ec032c911609e5f43848`. The sampler produces signed digital axes, jump/sneak flags and multiplies axes by `(float)(axis * 0.3)` while sneaking; no diagonal normalization occurs here. The normalization consumer is covered in stage 3.
- `S1.2` Local player tick ordering, previous/current input and flags, superclass/tick/travel order — `in-progress`. Correspondence: `LocalClientPlayerEntity.tick()` A 159-173 / B 165-179; `serverTickAi()` A 614-625 / B 631-642; local `mobTick()` A 632-790 / B 649-812; source hashes A `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`, B `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`. In both, `bl` captures old jumping, `bl2` old sneaking, and `bl3` old forward input before `input.tick()`; values then flow through item slowdown/auto-jump/sprint/flight/mounted-jump handling before the superclass mob tick. B adds `Tutorial.onPlayerInput(input)` immediately after sampling (line 699), and closes an `InventoryMenuScreen` on portal entry (657-662); both are queued for bounded out-of-scope disposition below. Full tick/travel dependencies remain open.
- `S1.3` Sprint start/stop, timers, conditions and writes reachable from client input — `pending`.
- `S1.4` Jump input, jump state/cooldown, auto-jump probes and movement dispatch — `pending`.
- `S1.5` Flight toggles and local flying input/speed gates — `pending`.
- `S1.6` Riding input gates and player-specific mounted movement hooks — `pending`.
- `S1.7` Incoming client correction/velocity packet consumers that directly write local player movement state — `pending`.

### Stage 2 — player movement state and gates

- `S2.1` Movement-relevant ability defaults/updates and player initialization/reset timing — `pending`.
- `S2.2` Pose selection, dimensions, eye height, resize collision gate and all pose/dimension writers — `pending`.
- `S2.3` Swimming/crawling/fall-flying pose transitions and their movement gates — `pending`.
- `S2.4` Active item use and direct movement slowdown state — `pending`.
- `S2.5` Sprint eligibility consumers, including blindness and mounted/item gates; hunger/food systems themselves are excluded — `pending`.
- `S2.6` Edge sneaking, stored air speed and other local movement-state fields not covered above — `pending`.

### Stage 3 — living movement integration

- `S3.1` Travel dispatch and branch guards; ground, air, water, lava, climb and fall-flying paths — `pending`.
- `S3.2` Relative input acceleration, normalization, friction and sprint multipliers — `pending`.
- `S3.3` Gravity, drag, negligible-velocity cutoffs, clamps and post-travel velocity/flag updates — `pending`.
- `S3.4` Ground jump impulse, sprint-jump impulse, jump strength and jump providers — `pending`.
- `S3.5` Climbing, levitation, slow-falling and other movement-effect consumers — `pending`.
- `S3.6` Fall-flying math, vectors, trigonometry, branch thresholds and state updates — `pending`.
- `S3.7` Movement attributes/helpers called by travel and direct player-path modifiers — `pending`.

### Stage 4 — entity movement and collision

- `S4.1` Player-reachable `Entity.move` call order, bounding-box/position updates and requested/resolved deltas — `pending`.
- `S4.2` Axis resolution order, collision candidate iteration/tie-breaking and step-up alternatives — `pending`.
- `S4.3` Edge probes, support lookup, grounding and collision-flag writes — `pending`.
- `S4.4` Velocity cancellation/restitution, fall-distance changes and collision callbacks — `pending`.
- `S4.5` Shape/AABB calculations and context-sensitive query timing on the player path — `pending`.
- `S4.6` Fluid contact/push checks and repeated movement within a tick — `pending`.

### Stage 5 — blocks and fluids that produce movement inputs

- `S5.1` Registration/default friction, speed and jump factors for blocks reachable in supported historical maps — `pending`.
- `S5.2` Landing/bounce, ice/soul-sand slowdown, web and other contact-factor classes and callbacks — `pending`.
- `S5.3` Climbable and fluid block/state callbacks, flow-vector and fluid-height inputs — `pending`.
- `S5.4` Collision shapes/support behavior for historical partial blocks, including state and neighbor dependencies — `pending`.
- `S5.5` Exhaustive movement-relevant subclass override and registration inventory; modern-only entries classified without inventing old behavior — `pending`.

### Stage 6 — effects, enchantments, attributes and equipment

- `S6.1` Movement attribute definitions, aggregation/operation ordering, defaults and player consumers — `pending`.
- `S6.2` Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace and Blindness registration/application/removal and direct movement consumers — `pending`.
- `S6.3` Depth Strider, Frost Walker and all registered movement-relevant enchantment formulas, conditions and consumers — `pending`.
- `S6.4` Equipment slots, item-use movement effects, relevant defaults and applicability predicates — `pending`.
- `S6.5` Tag/data/resource dependencies and synchronized/server-supplied movement values with provenance — `pending`.
- `S6.6` Explicit completeness check for all movement-state consumers and data-driven definitions found from registry scans — `pending`.

### Stage 7 — external influences and dependency closure

- `S7.1` Player knockback, explosions, piston displacement, launch-item and other external velocity writers — `pending`.
- `S7.2` Mount/dismount transitions and player-specific mounted state/position writes — `pending`.
- `S7.3` Client packet/authority boundaries and reconciliation of externally supplied velocity/position — `pending`.
- `S7.4` Final scan of reachable player movement-state writers, callbacks, overrides, registries and newly discovered dependencies — `pending`.
- `S7.5` Cross-mechanic interactions and final dependency closure/revisit of affected unchanged callers — `pending`.

## Dependency queue and blockers

- `SRC-PAIR`: resolved. Both readiness JSON records report `status=ready`, exact requested/resolved release IDs, `mapping=ornithe-feather`; full source manifests (1,921 A / 2,050 B files), artifact manifests (37 A / 38 B artifacts), and diagnostic hashes match their records. Every listed source and artifact file hash was rechecked. Movement diagnostics contain the exact jump, relative movement and travel method anchors; these bodies are still inspected individually before their slices close.
- `DIAG-A`, `DIAG-B`: inspect decompiler/remapper diagnostics and identify whether any damaged/warning-affected bodies intersect movement coverage; bytecode-check relevant methods if required.
- `CAND-OLD-001` (historical candidate only): re-check fall-flying look-vector dispatch and yaw source through both exact class hierarchies and player travel caller; no current finding until evidence is re-established.
- `CAND-OLD-002` (historical candidate only): re-check bed landing callback, collision callback order, player/sneak guards, shape and registration; no current finding until evidence is re-established.
- `CAND-OLD-003` (historical candidate only): re-check safe dismount candidate-box geometry, candidate ordering, player stop-riding path and authoritative position write; no current finding until evidence is re-established.
- Prior no-difference rows are not accepted as closure; warning-affected bodies and full source methods/dependency producers must be rechecked.
- Additional method, resource, state-writer and correspondence dependencies will be queued per parent slice and resolved before closure.

## Finding index

No findings recorded in this fresh campaign yet. Prior `1.11.2--1.12.2` run contains historical claims only; each candidate will be reverified against current validated artifacts before reuse. B-only tutorial callback in `LocalClientPlayerEntity.mobTick()` routes through `Tutorial.onPlayerInput(Input)` A: absent caller / B: `Tutorial.java:25-29` (SHA-256 `c5281a3f8000275b1e2387f97e88d6e0af409e3cedbff8c1668a775d3a780945`) -> `TutorialStep.onPlayerInput(Input)` default no-op at 18-19 (SHA-256 `3150f62d0b96c83fecb231dabdecd0c618f6970c8f8ff093f27547ecb0b866fa`) -> the only override, `MovementTutorialStep.onPlayerInput(Input)` 105-109 (SHA-256 `44f8769ebc81ffe6f368b085987e7c8879ef5db86388741310979bc94a80de17`), which only writes tutorial field `moved`; its tick consumes/resets this field for a tutorial counter. It does not mutate `Input` or player movement state: bounded `not-applicable` to direct movement. The portal branch only closes the inventory UI: A/B `closeMenu` and `doCloseMenu` bodies at A 275-284 / B 281-290 send a close-menu packet, clear cursor item, close menu and open screen; no movement field writes: bounded `not-applicable` to direct movement. Scope declaration: source-only, no wiki, no mod implementation.

## Resume checkpoint

- Last completed: source readiness and complete listed-file hash verification for both exact Feather trees; stage 1.1 input producer/defaults compared; read global/project guidance, source discovery workflow, navigation order, templates, decompiler guide and current decompiler implementation; verified branch and base; read prior pair report as historical context only.
- Next: inventory player-path source files and establish method-level correspondence; then process each bounded stage in order, including per-method checks against diagnostics and exact source hashes.
- Open coverage count: 42 pending rows; 0 in-progress; 0 terminal.
- Outstanding dependencies: per-stage method, registry, resource and state-writer dependencies; review decompiler method-body diagnostics for every cited slice.
- Assumptions requiring verification: prior pair's findings and no-difference claims remain candidates only.

## Source audit closure

- Coverage counts: 40 pending; 1 in-progress; 1 compared-no-difference; 0 findings; 0 not-applicable; 0 blocked.
- Findings in this fresh run: 0.
- Unresolved gaps: full seven-stage inventory, dependency closure and per-body warning/damage disposition.
- Evidence/hash audit: source and artifact manifests verified; stage 1.1 evidence hashes recorded; method correspondence and remaining evidence hashing per coverage slice remain in progress.
- Runtime validation: not performed (separate workflow).
