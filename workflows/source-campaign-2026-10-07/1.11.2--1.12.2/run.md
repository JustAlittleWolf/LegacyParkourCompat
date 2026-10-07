# Discovery: 1.11.2 to 1.12.2

- Status: active
- Track: source-only; no wiki; no mod implementation
- Scope: direct client player movement; older A = 1.11.2; newer B = 1.12.2
- Repository revision and start date: `002137b227676caea77f6832b9f4c8d0b6200bff` (main); 2026-10-07
- Working branch: `feat/source-discovery-movement-source-1-11-2-1-12-2`
- Naming namespace requested: Ornithe Feather on both exact releases, release-specific mapping artifacts
- Source-preparation request: pending source-owner response for validated ready JSON, exact resolved IDs, roots and artifact hashes. No source directory is treated as ready based on presence.
- Source command/log and toolchain: pending validated source package; no decompiler run initiated by this researcher.

## Artifact manifest

### A — 1.11.2

- Requested/resolved: 1.11.2 / pending source-owner readiness record.
- Source root: pending validated readiness record.
- Original client jar identity and SHA-256: pending.
- Namespace / CLI mode: requested Feather / `feather`; pending validation.
- Mapping coordinate/build, mapping path and SHA-256: pending.
- Remapped jar SHA-256: pending.
- Relevant decompiler diagnostics and warning disposition: pending.

### B — 1.12.2

- Requested/resolved: 1.12.2 / pending source-owner readiness record.
- Source root: pending validated readiness record.
- Original client jar identity and SHA-256: pending.
- Namespace / CLI mode: requested Feather / `feather`; pending validation.
- Mapping coordinate/build, mapping path and SHA-256: pending.
- Remapped jar SHA-256: pending.
- Relevant decompiler diagnostics and warning disposition: pending.

### Shared provenance

- Repository commit: `002137b227676caea77f6832b9f4c8d0b6200bff`.
- JDK, Gradle, Vineflower, remapper and Mapping IO versions/options: pending validated source-owner record.
- Source hashes: pending; hash every source/resource cited before terminalizing its slice.

## Correspondence and call order

Pending exact source package. Build correspondence from the local client player input/tick path through player and living movement to entity collision, blocks/fluids, movement data consumers and external state writers. Record each exact class/member descriptor on A and B, inheritance/callers, ordered call chain, fields read/written, relevant constructor/default values and bytecode cross-checks for warning-affected or uncertain bodies. Do not treat prior reports or names alone as complete correspondence.

## Coverage ledger

Every row remains `pending` until both exact source trees and dependency closure have been verified. The stages follow `workflows/movement-discovery/source-navigation.md`; after source readiness, split rows further when a bounded unit exceeds the method/context targets.

### Stage 1 — local input and tick ordering

- `S1.1` Keyboard/controller input sampling, directional flags, analog impulses, diagonal normalization and sneak/item-use scaling — `pending`.
- `S1.2` Local player tick ordering, previous/current input and flags, superclass/tick/travel order — `pending`.
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

- `SRC-PAIR`: obtain source-owner validated readiness JSON for exact 1.11.2/1.12.2 Feather outputs, including successful completion, exact resolved IDs, source roots, artifact hashes and diagnostics. No directory-presence assumption. Until received, no source claim can be terminal.
- `DIAG-A`, `DIAG-B`: inspect decompiler/remapper diagnostics and identify whether any damaged/warning-affected bodies intersect movement coverage; bytecode-check relevant methods if required.
- Additional method, resource, state-writer and correspondence dependencies will be queued per parent slice and resolved before closure.

## Finding index

No findings recorded in this fresh campaign yet. Prior `1.11.2--1.12.2` run contains historical claims only; each candidate will be reverified against current validated artifacts before reuse. Scope declaration: source-only, no wiki, no mod implementation.

## Resume checkpoint

- Last completed: read global/project guidance, source discovery workflow, navigation order, templates and decompiler guide; verified branch and base; read prior pair report as historical context only.
- Next: receive validated source readiness; verify exact IDs/hashes/diagnostics; inventory player-path source files and establish method-level correspondence; then process each bounded stage in order.
- Open coverage count: 38 pending rows; 0 in-progress; 0 terminal.
- Outstanding dependencies: `SRC-PAIR`, then per-stage dependencies.
- Assumptions requiring verification: Feather can be aligned for both exact release artifacts; prior pair's findings and no-difference claims remain candidates only.

## Source audit closure

- Coverage counts: 42 pending; 0 in-progress; 0 compared-no-difference; 0 findings; 0 not-applicable; 0 blocked.
- Findings in this fresh run: 0.
- Unresolved gaps: source readiness, full seven-stage inventory, dependency closure and warning disposition.
- Evidence/hash/correspondence audit: not started; no source evidence admitted yet.
- Runtime validation: not performed (separate workflow).

