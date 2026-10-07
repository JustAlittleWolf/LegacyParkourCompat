# Discovery: 1.20.6 to 1.21.1

- Status: active
- Track: source-only movement discovery; no wiki/MCPK audit and no mod implementation inspection
- Scope: direct client-player movement; older A = 1.20.6; newer B = 1.21.1
- Repository revision and start date: `002137b227676caea77f6832b9f4c8d0b6200bff` (`main`); 2026-10-07
- Task branch: `feat/source-discovery-movement-source-1-20-6-1-21-1`
- Selected naming namespace: pending exact ready markers. Requested 1.20.6 Feather and 1.21.1 Mojmap; validate aligned Feather release support or correct the request before comparison. Do not infer alignment from directory names.
- Source preparation command and log: source owner-managed shared publication; no decompiler run by this worker
- Toolchain/decompiler/remapper versions and options: pending source-owner manifests; no build/decompiler invoked

## Artifact manifest

- A (1.20.6): requested endpoint; source root and exact readiness record not yet published in `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07`; namespace, jar identity/hash, mapping coordinate/hash, remapped jar, source manifest, resource manifest and method diagnostics pending validated ready JSON.
- B (1.21.1): requested endpoint; source root and exact readiness record not yet published in `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07`; namespace, jar identity/hash, mapping coordinate/hash, remapped jar, source manifest, resource manifest and method diagnostics pending validated ready JSON.
- No source tree is treated as ready based on directory presence. Exact IDs, namespaces, cited hashes and relevant movement bodies must be checked against the published readiness records before comparison.

## Correspondence and call order

Pending source readiness. Correspondence will be established role by role from inheritance, callers, behavior, descriptors and state reads/writes, rather than from class names alone. Source-navigation stages: (1) local input/tick ordering; (2) player state/gates; (3) living movement integration; (4) entity movement/collision; (5) blocks/fluids; (6) attributes/effects/equipment; (7) external movement influences. For every completed slice, record both-side member descriptors, call order, line ranges, SHA-256 hashes, changed helpers, state producers/consumers and dependency closure.

## Coverage ledger

All slices are pending until the matching exact source bodies and dependencies are inspected on both sides. Each bounded unit must close as evidence-backed `findings`, `compared-no-difference`, `not-applicable`, or `blocked`.

- Slice I1 / stage 1 / local input sampling, input fields and keyboard/controller producers: `pending`; both-side source evidence awaits ready artifacts.
- Slice I2 / stage 1 / local player tick order, superclass tick, travel dispatch and repeated movement calls: `pending`.
- Slice I3 / stage 1 / yaw-to-motion conversion, normalization and input scaling (sneak/item use): `pending`.
- Slice I4 / stage 1 / sprint start/stop, timers, jump state/cooldown, auto-jump, flight toggles, unstuck and riding gates: `pending`.
- Slice P1 / stage 2 / pose selection, dimensions, eye height, resize timing and collision/fluid-query effects: `pending`.
- Slice P2 / stage 2 / abilities, flight state/speed, swimming/crawling and stored air speed: `pending`.
- Slice P3 / stage 2 / sprint gates and their vanilla state consumers; exclude hunger/food/health/damage emulation: `pending`.
- Slice P4 / stage 2 / active-item state, edge sneaking, defaults, initialization, updates and reset timing: `pending`.
- Slice L1 / stage 3 / travel dispatch and ground/air acceleration: `pending`.
- Slice L2 / stage 3 / friction, gravity, drag and velocity thresholds/cutoffs: `pending`.
- Slice L3 / stage 3 / jump power, sprint-jump impulse and jump gates: `pending`.
- Slice L4 / stage 3 / climbing clamps and movement callbacks: `pending`.
- Slice L5 / stage 3 / water/lava movement, fluid heights/push, swimming, gliding and post-travel updates: `pending`.
- Slice L6 / stage 3 / movement speed, jump and gravity attributes/helpers and their direct producers: `pending`.
- Slice C1 / stage 4 / bounding-box movement, position updates and axis ordering: `pending`.
- Slice C2 / stage 4 / step-up candidates, tie-breaking, edge probes and support/grounding lookup: `pending`.
- Slice C3 / stage 4 / velocity cancellation/restitution and movement collision response: `pending`.
- Slice C4 / stage 4 / world collision queries, AABB/voxel shapes, shape context and entity-size effects: `pending`.
- Slice C5 / stage 4 / fluid contact, block callbacks and callback order; track repeated queries/calls within a tick: `pending`.
- Slice B1 / stage 5 / base block/state/fluid movement properties and registrations/defaults: `pending`.
- Slice B2 / stage 5 / landing/bounce blocks (slime, beds) and callbacks: `pending`.
- Slice B3 / stage 5 / friction/speed blocks (soul sand and ice variants) and registrations: `pending`.
- Slice B4 / stage 5 / contact slowdown (webs, honey, powder snow) and historical applicability: `pending`.
- Slice B5 / stage 5 / climbables (ladders, vines and related blocks), callbacks and support: `pending`.
- Slice B6 / stage 5 / water/lava/bubble columns, flow and fluid-height calculations: `pending`.
- Slice B7 / stage 5 / moving pistons and player displacement: `pending`.
- Slice B8 / stage 5 / shape/support changes across partial blocks; subclass overrides, neighbors, tags, state properties and registry evidence: `pending`.
- Slice E1 / stage 6 / Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace and Blindness consumers/registrations/formulas: `pending`.
- Slice E2 / stage 6 / Depth Strider, Soul Speed, Swift Sneak, Frost Walker and Riptide consumer-to-registration/data chains: `pending`.
- Slice E3 / stage 6 / Elytra, use-item slowdown, equipment, movement item components and application/removal conditions: `pending`.
- Slice E4 / stage 6 / other discovered movement-affecting attributes/effects/enchantments, ordering, stacking, timers and tags: `pending`.
- Slice X1 / stage 7 / incoming velocity/position corrections and packet-to-player state writers: `pending`.
- Slice X2 / stage 7 / player knockback/push, explosions, piston displacement and launch-item inputs: `pending`.
- Slice X3 / stage 7 / mount/dismount transitions and other direct external player movement inputs: `pending`.
- Slice X4 / stage 7 / final unresolved movement-state writers, changed dependencies and cross-mechanic interactions: `pending`.

## Dependency queue and blockers

- D0 / source publication / exact validated ready JSON, source/artifact manifests, and movement-method diagnostics for 1.20.6 and 1.21.1; required to start evidence comparison. Next action: read each published JSON when available; verify exact IDs, namespace, hashes and body diagnostics. This is queued with the source owner, not treated as a completed comparison or a permanent blocker.
- D1 / namespace alignment / validate a common exact mapping family for both endpoints (requested Feather). If unavailable, probe permitted explicit family candidates through source-owner publication; do not accept mixed naming namespaces without an allowed official-name pairing.
- D2 / stage-discovered dependencies / record exact unresolved member/resource/tag/data questions as stages are traversed; resolve each or leave its slice blocked with a precise retrieval action.

## Finding index

No findings recorded yet. Absence of a finding file does not imply equivalence. Preserve independently scoped findings only after paired source evidence, reachability and dependencies are verified. No runtime validation is performed in this source-only track.

## Resume checkpoint

- Last completed slice: none; comparison has not begun pending validated endpoint publication.
- Next bounded slice and exact files/members to open: verify source-owner ready JSON and manifests for both endpoints; inspect the cited movement-method bodies/diagnostics first, then begin stage I1 and record every reached producer/caller.
- Outstanding dependencies: D0, D1; later dependencies must be added as discovered.
- Current assumptions requiring verification: 1.20.6 Feather availability and exact namespace alignment; 1.21.1 Mojmap readiness; requested/resolved IDs; source/artifact hashes; decompiler body integrity.

## Source audit closure

- Coverage counts by status: 0 findings; 0 compared-no-difference; 0 not-applicable; 0 blocked; 35 pending; 0 in-progress.
- Unresolved gaps and limits: all seven navigation stages remain open until source readiness and complete method/dependency coverage. Queue status is not evidence of a version difference or equivalence.
- Evidence/hash/correspondence audit: not started; no source evidence cited.
- Source-only declaration: no runtime Java implementation or mod implementation inspection; no wiki/MCPK browsing; no release-notes-derived mechanic claims; no tests, game clients, TAS, Gym/server or Docker launches.
- Runtime validation: not performed (separate workflow).

