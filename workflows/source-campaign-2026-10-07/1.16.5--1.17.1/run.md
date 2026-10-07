# Discovery: 1.16.5 to 1.17.1

- Status: active
- Scope: direct client-player movement; older A = `1.16.5`; newer B = `1.17.1`.
- Repository revision at start: `002137b227676caea77f6832b9f4c8d0b6200bff`; 2026-10-07.
- Selected namespace: expected official Mojang names (`mojmap`) on both sides; not accepted until the published readiness records confirm exact IDs, modes and alignment.
- Source preparation: sole source owner; no decompiler or source-tree writes by this run. Exact ready records for both endpoints are pending.
- Source protocol: canonical root `../../../../build/movement-campaign-2026-10-07/`; each endpoint's `ready/<version>/<namespace>.ready.json` must point to SHA-256 source/artifact manifests and movement-method diagnostics. Verify the JSON contents, hashes and relevant body diagnostics before citing source. Shared generation lock is `decompile.lock`; this run will not acquire it or generate sources.
- Wiki / implementation declaration: no wiki or release-note mechanics evidence; no mod implementation/code-diff inspection. This is source-only discovery, with no runtime implementation or validation.
- Exclusions: health, regeneration, hunger, food, saturation, exhaustion, damage and combat emulation are out of scope, including their indirect subsystems. Vanilla state may be read only where it directly gates player movement, and only the movement consumer is compared. Non-player physics are out of scope. Modern-only blocks/features receive no invented A-era behavior.

## Artifact manifest

All source/artifact identities remain pending until owner publication and verification. Relative paths below are rooted at this manifest.

### A — 1.16.5

- Requested/resolved release: pending readiness JSON.
- Source root: `../../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/` (expected; verify).
- Client jar identity/hash, mapping coordinate/build/hash, remapped jar hash, decompiler log, toolchain versions/options: pending.
- Readiness JSON, source manifest and movement diagnostics: pending; not yet published.

### B — 1.17.1

- Requested/resolved release: pending readiness JSON.
- Source root: `../../../../build/movement-campaign-2026-10-07/ready/1.17.1/mojmap/` (expected; verify).
- Client jar identity/hash, mapping coordinate/build/hash, remapped jar hash, decompiler log, toolchain versions/options: pending.
- Readiness JSON, source manifest and movement diagnostics: pending; not yet published.

### Cited evidence

No Minecraft source or resource evidence has been accepted yet. Once sources are ready, record SHA-256 for every cited source/resource and exact original line ranges. Verify method-body diagnostics before comparison; hash agreement alone does not establish a readable body.

## Correspondence and call order

No exact source correspondence is accepted before both readiness records are verified. Resolve actual fully qualified classes, inheritance, member signatures, callers, read/write state and execution order independently on A and B. Work through the seven navigation stages in order; method names alone do not establish correspondence.

Planned entry chain to resolve:
local input producer -> local player input sampling/tick -> player/living tick -> travel dispatch and branch helpers -> entity movement/collision query -> support/callback/state updates. Also resolve independent pose/dimension writers, block/fluid movement providers and registrations, movement-effect/attribute/equipment consumers, and external velocity/position writers. Record renamed, split, merged or replaced methods and their evidence.

## Coverage ledger

Every item below is pending, not a no-difference claim. Close a bounded item only after recording both-side method/body ranges and hashes, all reachable callers/producers/consumers, the dependency closure and a conclusion. Split further whenever a method is too broad; never truncate a method to meet a context target.

### Stage 1 — local input and tick ordering

- `S1-01` input producers and local sampling: controls, impulse conversion/normalization, yaw-to-motion input, slow-input scaling, previous/current input flags. Status: pending. Evidence/correspondence: pending. Dependency closure: key/options producers only where reachable.
- `S1-02` local-player tick chain: full local tick, superclass tick calls and ordering of input, AI, travel and post-tick work. Status: pending. Evidence/correspondence: pending.
- `S1-03` sprint start/stop state, timers, gates and writers, including direct vanilla-state consumers only. Status: pending. Evidence/correspondence: pending. Food/hunger/regeneration/exhaustion implementations excluded.
- `S1-04` jump input state, cooldowns, dispatch, auto-jump probes and jump ordering. Status: pending. Evidence/correspondence: pending.
- `S1-05` player flight/abilities, flight speed and flight toggle state; keep fall-distance side effects only where they directly affect player movement. Status: pending. Evidence/correspondence: pending.
- `S1-06` player riding, mount/dismount movement gates and local transition timing. Status: pending. Evidence/correspondence: pending; mounted-entity simulation excluded.

### Stage 2 — player state, pose, dimensions and gates

- `S2-01` pose selection, bounding-box dimensions, resize rules, blocked-resize collision query and state writers. Status: pending. Evidence/correspondence: pending.
- `S2-02` eye height, swimming/crawling/sleeping/fall-flying dimensions and fluid/collision checks that consume them. Status: pending. Evidence/correspondence: pending.
- `S2-03` item-use movement slowdown, edge sneaking and other player-only stored movement state; follow direct producers and reset timing. Status: pending. Evidence/correspondence: pending.

### Stage 3 — living movement integration

- `S3-01` complete travel dispatch and pre-branch state/call order, split by bounded behavior while preserving enclosing guards. Status: pending. Evidence/correspondence: pending.
- `S3-02` ground acceleration, friction/support-factor sampling and arithmetic order. Status: pending. Evidence/correspondence: pending.
- `S3-03` air acceleration/speed and retained input/momentum state. Status: pending. Evidence/correspondence: pending.
- `S3-04` gravity, drag, velocity thresholds/cutoffs, clamps and exact float/double operation order. Status: pending. Evidence/correspondence: pending.
- `S3-05` ground jump, sprint-jump impulse, jump power/factors and movement-effect contribution. Status: pending. Evidence/correspondence: pending.
- `S3-06` climbable travel and vertical/horizontal clamps. Status: pending. Evidence/correspondence: pending.
- `S3-07` water/lava travel, swimming controls, fluid-height selection and post-travel damping. Status: pending. Evidence/correspondence: pending.
- `S3-08` fall-flying/gliding travel and movement state updates. Status: pending. Evidence/correspondence: pending.
- `S3-09` post-travel velocity/ground/fall-distance/state updates and changed helper callers. Status: pending. Evidence/correspondence: pending.

### Stage 4 — entity movement, collision and support

- `S4-01` move entry, collision-query timing, axis order, clipped velocity and position/box updates. Status: pending. Evidence/correspondence: pending.
- `S4-02` step-up candidate generation, height selection, tie-breaking and repeated movement. Status: pending. Evidence/correspondence: pending.
- `S4-03` collision iteration, AABB/voxel-shape operations, shape contexts and world query call graph. Status: pending. Evidence/correspondence: pending.
- `S4-04` on-ground/support position lookup, edge probes and collision/fluid flags. Status: pending. Evidence/correspondence: pending.
- `S4-05` block/entity collision callbacks, fall/on-position updates and velocity cancellation/restitution on the player path. Status: pending. Evidence/correspondence: pending; independent non-player physics excluded.

### Stage 5 — blocks, shapes, neighbors and fluids

- `S5-01` exhaustive movement-property registrations/providers for A-era blocks: friction, speed/jump factors, callbacks and state defaults. Status: pending. Evidence/correspondence: pending.
- `S5-02` collision-shape providers for A-era blocks, including partial blocks and connected/neighbor-dependent shapes. Status: pending. Evidence/correspondence: pending.
- `S5-03` contact/landing/slowdown/climb providers and their reachable player callbacks. Status: pending. Evidence/correspondence: pending.
- `S5-04` water/lava flow, height, current and player push calculations, with relevant registration/resource data. Status: pending. Evidence/correspondence: pending.
- `S5-05` additions/removals and modern-only blocks/tags/resources. Status: pending. Evidence/correspondence: pending; never back-port behavior to states unavailable in A.

### Stage 6 — direct movement effects, enchantments, attributes and equipment

- `S6-01` movement consumers and source of movement-related effects/attributes; trace consumer to aggregation, registration, application and removal. Status: pending. Evidence/correspondence: pending.
- `S6-02` enchantment/item/equipment formulas, applicability, slots, conditions and movement attribute operations. Status: pending. Evidence/correspondence: pending.
- `S6-03` movement-relevant defaults, tags and client-jar resources or server-supplied input dependencies. Status: pending. Evidence/correspondence: pending. Excluded health/food systems are not emulated.

### Stage 7 — external velocity/position writers and dependency closure

- `S7-01` client player packet consumers and synchronized position/velocity/ability updates. Status: pending. Evidence/correspondence: pending.
- `S7-02` player-reachable pushes, explosions, pistons, launch effects and riding transitions that directly write player movement state. Status: pending. Evidence/correspondence: pending; damage/combat semantics and non-player simulation excluded.
- `S7-03` enumerate remaining reachable position/velocity/pose/box/ground/fluid writers, changed dependencies and cross-stage interactions; revisit consumers of changed dependencies. Status: pending. Evidence/correspondence: pending.

- Current pending count: 34.
- Closed findings, compared-no-difference and not-applicable rows: 0.
- Blocked rows: 0; source publication is queued, so no comparison slice is marked complete or blocked yet.

## Dependency queue and blockers

- `SRC-A`: exact 1.16.5 aligned source publication, readiness JSON, artifact/source manifests and movement-method diagnostics. Owner: source owner. Next action: verify owner publication and hashes.
- `SRC-B`: exact 1.17.1 aligned source publication, readiness JSON, artifact/source manifests and movement-method diagnostics. Owner: source owner. Next action: verify owner publication and hashes.
- `RESOURCE-INVENTORY`: exact client-jar resources/data referenced by movement consumers; identify server/datapack-supplied values separately. Next action: inventory after jar provenance is verified.
- `DECOMPILER-DIAGNOSTICS`: inspect relevant method bodies and diagnostics for every coverage slice; request exact bytecode/source assistance through commentary if a relevant body is missing or damaged.

## Finding index

No findings accepted yet. Prior reports under `workflows/movement-discovery/runs/1.16.5--1.17.1/` are navigation/candidate context only and are not evidence for this run.

## Resume checkpoint

- Last completed slice: none; source pair is not published.
- Next: verify both exact ready JSON records and their artifact/source hashes; inspect relevant method-body diagnostics; then establish the paired navigation index and begin Stage 1.
- Outstanding dependencies: `SRC-A`, `SRC-B`, resource inventory and diagnostics review.
- Current assumptions requiring verification: Mojmap on both endpoints; source roots/namespace in the manifest.
- Runtime validation: not performed; separately disallowed for this source-only run.

## Source audit closure

Not closed. Status is active. Coverage: 34 pending, 0 in-progress, 0 compared-no-difference, 0 findings, 0 not-applicable, 0 blocked. This ledger is a work plan only and makes no source-derived claim or exhaustive-equivalence claim.


