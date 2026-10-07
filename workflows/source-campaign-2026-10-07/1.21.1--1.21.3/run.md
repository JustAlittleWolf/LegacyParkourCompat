# 1.21.1 to 1.21.3 movement source discovery

- Status: active
- Track: source-only direct player movement comparison; no wiki/MCPK use and no mod implementation inspection.
- Scope: older A = Minecraft 1.21.1; newer B = Minecraft 1.21.3. This is one content-update boundary using the latest hotfix representatives.
- Repository revision at start: `002137b227676caea77f6832b9f4c8d0b6200bff` (`main`); task branch `feat/source-discovery-movement-source-1-21-1-1-21-3`.
- Start date: 2026-10-07 (Europe/Vienna).
- Selected naming namespace: pending exact-version source-owner publication; one identical namespace is required on both sides. Source comparison has not started.
- Source preparation: sole source owner is generating/publishing the exact endpoints under `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/ready/`; no source tree or readiness JSON for either endpoint was present at initial inventory. No decompiler was run in this worktree.
- Toolchain/decompiler/remapper versions and options: pending source-owner manifests. Repository decompiler implementation was inspected read-only; pair provenance must be taken from the published marker/manifests, not inferred.
- Runtime Java implementation: prohibited in this track. Tests, Gradle builds, game/TAS/Gym/server/Docker launches: not run; no runtime validation.

## Artifact manifest

### A — 1.21.1

- Requested/resolved release: requested 1.21.1; resolved ID and metadata ID pending exact readiness JSON verification.
- Source root: pending published namespace; initial check found no `ready/1.21.1/` directory.
- Client jar SHA-256, CLI mode, mapping coordinate/build, mapping file/hash, remapped jar hash, source manifest/hash, artifact manifest/hash, diagnostics/hash and exact toolchain: pending source-owner publication and verification.
- No source, jar or resource evidence has been used yet.

### B — 1.21.3

- Requested/resolved release: requested 1.21.3; resolved ID and metadata ID pending exact readiness JSON verification.
- Source root: pending published namespace; initial check found no `ready/1.21.3/` directory.
- Client jar SHA-256, CLI mode, mapping coordinate/build, mapping file/hash, remapped jar hash, source manifest/hash, artifact manifest/hash, diagnostics/hash and exact toolchain: pending source-owner publication and verification.
- No source, jar or resource evidence has been used yet.

## Correspondence and call order

No class/member correspondence has been asserted. Once the exact pair is ready, establish and record per-stage A-to-B member descriptors, original source line ranges, callers, direct dependencies, mutable state reads/writes and execution order. Name similarity alone is not correspondence. Track source hashes and verify every cited method body against the published source manifest.

## Coverage ledger

The following stage roots are pending paired source publication and exhaustive inventory. Each must be expanded into bounded method/dependency units; a stage root cannot be closed by a travel-only comparison.

- S1 / local input and tick ordering: `pending` — input sampling, local/super tick order, yaw-to-motion conversion, normalization, sneak/use scaling, sprint transitions/timers, jump/cooldown, auto-jump, flight toggles, unstuck and riding gates. Food/health-derived sprint-gate behavior is explicitly excluded by campaign scope.
- S2 / player state and gates: `pending` — pose selection, dimensions/resize, eye height where used by movement/fluid checks, swimming/crawling, abilities/flight speed, item-use state, edge sneaking and air-speed state. Health/regen/hunger/food/saturation/exhaustion/damage/combat emulation is excluded.
- S3 / living movement integration: `pending` — travel dispatch; ground/air/water/lava/gliding movement; acceleration, drag, gravity, velocity thresholds, jump power and impulses, climbing, post-travel state and direct movement attributes/effects/equipment consumers.
- S4 / entity movement and collision: `pending` — bounding-box move, position/velocity update, axis order, stepping/candidate tie-breaks, edge/support probes, on-ground/collision flags, callbacks, fluid contact/push, shape/AABB queries and repeated movement timing.
- S5 / blocks and fluids producing movement inputs: `pending` — every relevant registration/default and overriding shape/callback/friction/speed/jump factor; state and neighbor dependencies; fluids/flow/height; historical partial-block collision shapes. Modern-only blocks receive no historical behavior; non-player physics is out of scope.
- S6 / effects, enchantments, attributes and equipment: `pending` — direct movement consumers and both-side producer chains through defaults, operations/order, applications/removals, slots, predicates, tags and resource definitions. Exclude health/food systems except direct vanilla state consumers; classify server-supplied values explicitly.
- S7 / external movement-state writers and dependency closure: `pending` — local client consumers of incoming position/velocity corrections, player-directed pushes/explosions/pistons/launch effects and mount transitions; caller/state-writer closure, changed helper closure and cross-mechanic interactions. Independent non-player movement and combat are out of scope.

## Dependency queue and blockers

- SRC-PAIR — source owner publication for exact 1.21.1 and 1.21.3 sources in one aligned namespace, with readiness JSON, source/artifact manifests, hashes and method diagnostics. Required because there is no valid source evidence before exact provenance and method-body integrity are verified. Next action: wait for publication, read both exact JSON files, validate IDs/namespace/cited SHA-256 values, then inspect relevant bodies/diagnostics. Current state: open; endpoints were not published at initial inventory.
- METHOD-INVENTORY — per-stage reachable method/caller/state-writer and shape-provider inventory. Required for exhaustive coverage and dependency closure. Next action: enumerate from exact paired sources after SRC-PAIR resolves; keep every bounded unit pending until its full method and dependencies are checked.
- RESOURCE-CHAIN — movement-relevant client-jar data/tags/registrations and external/server-supplied defaults. Required where source consumers reference resource-defined values. Next action: inspect exact version-matched client jar entries and hash every cited resource; queue any unavailable server datapack/external input as blocked.

## Finding index

None yet. No source comparison has been performed; zero findings at this checkpoint does not imply equivalence.

## Resume checkpoint

- Last completed slice: source-pair request and initial artifact-root check only.
- Next bounded slice: verify exact A/B readiness JSON, manifest SHA-256 values, method-body diagnostics, release IDs and common namespace before opening any movement method.
- Outstanding dependencies: SRC-PAIR, METHOD-INVENTORY, RESOURCE-CHAIN.
- Assumption requiring verification: a common explicit namespace exists for both 1.21.1 and 1.21.3; do not infer availability from folders, the `auto` profile or nearby releases.

## Source audit closure

- Coverage counts at this checkpoint: pending 7 stage roots; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0. Counts are provisional and will be replaced by the complete bounded-unit ledger.
- Unresolved gaps: exact source pair not yet published; no paired navigation or semantics comparison; all seven stages remain open.
- Evidence/hash/correspondence audit: no source evidence or hashes cited; no correspondence claims made.
- Runtime validation: not performed (separate workflow); implementation reconciliation is deferred until this report is frozen and handed off.
