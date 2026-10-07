# Discovery: 1.21.5 to 1.21.8

- Status: active
- Scope: exact adjacent content-update pair; older A = 1.21.5; newer B = 1.21.8. Direct client-player movement only. Excludes health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulation and their producer systems, including consequences on sprint eligibility; vanilla state may still be read as an input to an in-scope movement predicate. Excludes independent non-player movement and historical behavior for modern-only blocks/states.
- Repository revision and start date: source branch created from `main` at `002137b227676caea77f6832b9f4c8d0b6200bff`; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: pending exact readiness records. Campaign policy selects Mojmap from 1.14.4 onward, but pair alignment and exact artifacts are not yet verified.
- Source preparation owner / command / log / readiness marker: shared source owner; exact command, logs and readiness records pending. No local decompilation run.
- Toolchain/decompiler/remapper versions and options: pending validated readiness records.
- Discovery author(s): Codex source-only worker for this pair.
- Independent reviewer (must differ from discovery authors): not yet assigned/recorded.
- Track declaration: source-only; no old mod implementation, no Minecraft Wiki/MCPK browsing or wiki-audit output, no tests/gameplay/TAS/Gym/server/Docker launches. Runtime validation is not authorized.

## Artifact manifest

### A — 1.21.5

- Requested/resolved release, source root, client jar identity/hash, CLI mode, mapping coordinate/build/path/hash, remapped jar hash, relevant source/resource hashes, diagnostics: pending source-owner readiness record.
- Readiness record: not published at last check. Exact source root is not considered ready based on directory presence.

### B — 1.21.8

- Requested/resolved release, source root, client jar identity/hash, CLI mode, mapping coordinate/build/path/hash, remapped jar hash, relevant source/resource hashes, diagnostics: pending source-owner readiness record.
- Readiness record: not published at last check. Exact source root is not considered ready based on directory presence.

No pairwise method comparison or semantic evidence has started. Both source-owner publications must cite exact requested/resolved IDs, SHA-256 source/artifact manifests and movement-method diagnostics before use.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: none; discovery is active.
- Evidence inventory and finding IDs included at freeze: none; exact sources unavailable.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed. One prior source discovery report was read only for navigation leads; none of its findings, hashes or equivalence claims are carried into this run.
- Source/mapping hashes covered by freeze: none yet.

## Correspondence and call order

No exact A/B roles or members are resolved yet. Once source is ready, map the complete player tick chain and state read/write edges, including pre-travel decisions, every travel dispatch/branch and post-travel work. Compare actual inheritance, signatures/descriptors, callers and bodies; shared names are only search leads. Prior-run navigation-only leads to re-resolve include LocalPlayer input/tick, KeyboardInput/ClientInput, LivingEntity travel, Entity movement/collision, Player support/edge handling, powder-snow callbacks, movement effect/enchantment consumers and client correction handlers. These are not pair evidence or an exhaustive inventory.

## Required source inventories

- `INV-TICK` input sampling, local-player tick, pre-travel predicates/writes, travel dispatch/branches and post-travel: status=pending; slice_ids=S1.1,S1.2,S1.3,S1.4,S3.1,S3.2,S3.3,S3.4,S3.5; evidence=awaiting exact A/B sources and bounded member inventory.
- `INV-STATE` movement-state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, support, timers and direct predicates: status=pending; slice_ids=S2.1,S2.2,S2.3,S2.4; evidence=awaiting exact A/B sources and producer/consumer inventory.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S4.1,S4.2,S4.3,S4.4,S5.2,S5.4; evidence=awaiting exact A/B sources and registration/shape inventory.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, resource data/tags and defaults: status=pending; slice_ids=S5.1,S5.2,S5.3,S5.4; evidence=awaiting exact A/B sources and resource inventory.
- `INV-MODIFIERS` direct movement attributes, effects, enchantments, equipment and application/removal/conditions: status=pending; slice_ids=S6.1,S6.2,S6.3; evidence=awaiting exact A/B sources and data inventory.
- `INV-EXTERNAL` player-only external movement inputs and client consumers including corrections, pushes, pistons and mounts: status=pending; slice_ids=S7.1,S7.2,S7.3; evidence=awaiting exact A/B sources and caller/state-writer inventory.
- `INV-EXCLUSIONS` scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=awaiting source-backed disposition of direct vanilla-state reads and excluded producer systems.

## Coverage ledger

Every row below is an unfinished discovery unit, not a claim that a method has been checked. Exact A/B methods, body ranges and hashes will be added only after validated readiness records. Split or add rows whenever source inventory reveals narrower behaviors or new dependencies.

### Slice S1.1: input sampling and vector shaping

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: input source sampling through produced movement vector; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending input producer-to-local-player consumer trace.
- Parent slices / dependencies / closure evidence: source dependencies DEP-SRC-A and DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence claim.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S1.2: local-player tick and pre-travel ordering

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: local player tick sequence through pre-travel state writes and call into superclass/travel; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending caller and state-edge trace.
- Parent slices / dependencies / closure evidence: S1.1; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; sprint food/hunger producer systems excluded.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S1.3: jump, sprint timing and direct eligibility predicates

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: jump/sprint timers, start/stop predicates and direct movement inputs; exclude hunger/food system emulation and any indirect sprint-gate finding.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending timer/predicate writer-to-reader trace; vanilla input state will be distinguished from excluded producers.
- Parent slices / dependencies / closure evidence: S1.2; S2.3; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S1.4: input modes, flight, auto-jump, unstuck and riding gates

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: alternate input producers, auto-jump, flight-toggle/unstuck behavior and riding gates on the local player path; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending alternate input/state producer-to-consumer trace.
- Parent slices / dependencies / closure evidence: S1.1; S1.2; S2.2; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S2.1: pose, dimensions and eye-height state

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: pose selection/transitions, dimensions and eye height including query consumers; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending transition/default writers to collision and fluid-query readers.
- Parent slices / dependencies / closure evidence: S4.1; S4.3; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S2.2: movement flags and repeated state writers

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: position, velocity, on-ground/collision/fluid/support flags, stored air speed and transition/reset timing; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending all-writer/consumer inventory.
- Parent slices / dependencies / closure evidence: S2.1; S3.1-S3.5; S4.1-S4.4; S7.1-S7.3; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S2.3: direct movement predicates and active-item state

- Inventory ID(s): INV-STATE, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: in-scope predicates reading player/active-item/vanilla system state; exclude producing/emulating health, food, exhaustion, damage and combat systems.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending direct read map; excluded producer systems will be marked out of scope.
- Parent slices / dependencies / closure evidence: S1.3; S6.3; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S2.4: swimming/crawling pose and flight ability state

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: swimming/crawling transitions, flight abilities/speed and state reset/update timing; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending state writer-to-tick/travel/collision consumer map.
- Parent slices / dependencies / closure evidence: S2.1; S2.2; S3.1; S3.3; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S3.1: travel dispatch and ground/air movement math

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: travel dispatch, branch predicates, ground/air acceleration/friction and exact arithmetic order; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending input/attribute/velocity consumers and post-travel writes.
- Parent slices / dependencies / closure evidence: S1.1-S1.4; S2.2; S6.1; S6.2; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S3.2: jump, sprint-jump, gravity, drag and velocity thresholds

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: jump-power/impulse math, gravity/drag, negligible-velocity cutoffs and order; exclude food/hunger emulation or effects.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending direct jump/effect inputs and velocity writes/readers.
- Parent slices / dependencies / closure evidence: S1.3; S2.2; S6.1; S6.2; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S3.3: water, lava and swimming movement

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: fluid travel branches, depth/current/flow decisions and swimming motion; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending fluid state/property/resource producers to local-player travel consumers.
- Parent slices / dependencies / closure evidence: S2.1; S2.4; S5.3; S6.1; S6.2; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S3.4: climbing and fall-flying movement

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: climbable movement/clamps and gliding/fall-flying math, guards and direct equipment/effect inputs; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending climbable/elytra/effect/equipment producer-to-player consumer trace.
- Parent slices / dependencies / closure evidence: S2.4; S5.2; S6.1; S6.2; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S3.5: post-travel work and callbacks

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: post-move updates, fall-distance/flags and callbacks that directly change player position/velocity/pose; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending post-travel writer-to-next-tick consumer map.
- Parent slices / dependencies / closure evidence: S2.2; S4.4; S5.2; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S4.1: player Entity.move collision resolution

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: bounding-box and position movement, collision query/order, axis resolution and velocity cancellation; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending movement input and box/velocity/on-ground writes to later travel readers.
- Parent slices / dependencies / closure evidence: S2.1; S2.2; S4.3; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S4.2: step-up, edge probes and support lookup

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: candidate step paths/tie-breaks, edge restraint, support position and on-ground query bounds; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending collision shape/support query producer-to-ground and movement consumer map.
- Parent slices / dependencies / closure evidence: S2.1; S4.1; S4.3; S5.2; S5.4; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S4.3: world collision queries and shape evaluation

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: player-reachable collision query, AABB/shape iteration, context inputs and selection/tie rules; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending entity box/context to registered shape-provider chain.
- Parent slices / dependencies / closure evidence: S2.1; S4.1; S4.2; S5.2; S5.4; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S4.4: collision, fluid and block callback ordering

- Inventory ID(s): INV-COLLISION, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: callbacks after movement/collision and fluid contact/push that directly change local-player state; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending callback ordering and position/velocity/fluid flag edges.
- Parent slices / dependencies / closure evidence: S3.3; S4.1; S5.2; S5.3; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S5.1: block movement properties and registrations

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: friction, speed/jump factors, registry defaults and all relevant assignments/overrides; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending registration/property producer-to-player travel consumer trace.
- Parent slices / dependencies / closure evidence: S3.1; S3.2; S4.1; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S5.2: shape providers, movement callbacks and subclasses

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: registered/overridden shape and callback providers including landing/bounce/contact/climb/slowing mechanics; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending registrations, neighboring-block inputs and player callback consumers.
- Parent slices / dependencies / closure evidence: S3.4; S3.5; S4.1-S4.4; S5.4; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; do not give modern-only states historical behavior.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S5.3: fluids, flow vectors, height and current

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: fluid state, contact, height/flow/current calculation and movement push providers; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending fluid registry/data to player immersion/travel/push consumers.
- Parent slices / dependencies / closure evidence: S3.3; S4.4; S5.4; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S5.4: neighboring blocks, tags, states and resource defaults

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: neighbor/property/context-dependent shape and movement inputs plus relevant jar resource tags/defaults; not yet checked.
- A evidence: pending source publication and matching client-jar resources.
- B evidence: pending source publication and matching client-jar resources.
- State producers/writers -> consumers/readers: pending resource/registration/neighbor producer to shape and player query consumer trace.
- Parent slices / dependencies / closure evidence: S4.2; S4.3; S5.1-S5.3; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source and resource comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S6.1: movement effects and attribute aggregation

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: direct movement attribute consumers, modifier aggregation/order, effect formulas and movement-affecting registration values; not yet checked.
- A evidence: pending source publication and matching resources.
- B evidence: pending source publication and matching resources.
- State producers/writers -> consumers/readers: pending effect/application/attribute producers to jump/travel/input consumers.
- Parent slices / dependencies / closure evidence: S1.1; S3.1-S3.4; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source and resource comparison; exclude health/food producer systems.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S6.2: movement enchantments and equipment conditions

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Depth Strider, Soul Speed, Swift Sneak, Frost Walker, Riptide, Elytra and any other direct movement equipment/enchantment formulas, slots, predicates and tags; not yet checked.
- A evidence: pending source publication and matching resources.
- B evidence: pending source publication and matching resources.
- State producers/writers -> consumers/readers: pending equipment/tag/condition producers through attribute/helper to player movement consumer.
- Parent slices / dependencies / closure evidence: S3.1-S3.4; S5.4; S6.1; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source and resource comparison; server-side Frost Walker world mutation is an explicit provenance boundary.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S6.3: use-item slowdown and movement components

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: active item/use state, slowdown and movement-relevant item components along reachable player input path; not yet checked.
- A evidence: pending source publication and matching resources.
- B evidence: pending source publication and matching resources.
- State producers/writers -> consumers/readers: pending item/application/component producer-to-input scaling consumer trace.
- Parent slices / dependencies / closure evidence: S1.1; S2.3; S6.1; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source and resource comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S7.1: incoming velocity and position corrections

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: local client packet consumers that write player position/velocity/flags and their acknowledgment/timing path; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending packet inputs to local-player state writers and following tick consumers.
- Parent slices / dependencies / closure evidence: S1.2; S2.2; S3.1; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; server-supplied values identified separately.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S7.2: player pushes, pistons, mounts and launch items

- Inventory ID(s): INV-EXTERNAL, INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: client-side player consumers of external pushes, piston movement, mount/dismount and launch-item movement inputs; exclude other-entity simulation.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending external producer-to-local-player movement state writer trace.
- Parent slices / dependencies / closure evidence: S2.2; S4.4; S5.2; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; external authority boundary will be recorded.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S7.3: final reachable movement-writer dependency closure

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT, INV-MODIFIERS, INV-EXTERNAL, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: cross-stage revisit of all discovered reachable player movement state writers, changed helpers, registrations, resources and producer/consumer edges; not yet checked.
- A evidence: pending full pair inventory.
- B evidence: pending full pair inventory.
- State producers/writers -> consumers/readers: pending all-stage dependency graph.
- Parent slices / dependencies / closure evidence: S1.1-S7.2; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending final source audit; cannot close any stage from a narrow travel comparison.
- Finding IDs or checked absence/replacement path: none yet.

## Dependency queue and blockers

- `DEP-SRC-A` / all slices / validated exact 1.21.5 publication: exact requested/resolved ID, namespace, source/artifact manifests, original/mapped client jar and mapping hashes, source root, method diagnostics and decompiler warnings / required for body and hash evidence / source preparation owner / unresolved; shared source queue is active, so this is pending, not currently blocked.
- `DEP-SRC-B` / all slices / same validated publication for exact 1.21.8 / required to establish alignment and compare pair / source preparation owner / unresolved; shared source queue is active, so this is pending, not currently blocked.
- More dependencies will be added as exact source inventories resolve callers, field writers, registrations and resource data.

## Finding index

- Confirmed findings: none yet.
- Candidate findings: none yet.
- Discarded candidates: none yet.

## Resume checkpoint

- Last completed slice: none; all current rows await exact-source publication.
- Next bounded slice and exact files/members/body ranges to open: after validating both ready records and manifests, build filename inventories and resolve stage 1 local input sampling, tick callers and input-vector consumers on both sides.
- Outstanding dependencies and owners: DEP-SRC-A and DEP-SRC-B (shared source preparation owner).
- Current assumptions requiring verification: aligned Mojmap namespace and exact release resolution; manifest/source hashes; source diagnostics; class/member correspondence; resource availability and contents.

## Implementation reconciliation

Complete only after source-only freeze. No mod implementation was opened for this run.

- Reconciliation status: pending
- Repository revision inspected: none; deferred until after blind-discovery freeze.
- Finding -> implementation disposition/evidence: pending.
- Existing implementation without a frozen source finding: pending.
- Coverage gaps routed back to discovery slices: pending.

## Independent source audit

- Reviewer: not yet assigned/recorded; must differ from discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: none; source inventory is pending.
- Concrete missed-slice routes (or `none found`): pending review.
- Misses routed to slice/finding IDs and owners: pending review.
- Reviewer evidence / date: pending review.

## Source audit closure

- Coverage counts by status: 27 pending; 0 in-progress; 0 compared-no-difference; 0 findings; 0 not-applicable; 0 blocked (all 7 inventories pending).
- Required inventory status and evidence: INV-TICK, INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT, INV-MODIFIERS, INV-EXTERNAL, INV-EXCLUSIONS all pending; exact source evidence is not yet available.
- Open dependencies: DEP-SRC-A, DEP-SRC-B; both owned by the shared source preparation owner.
- Unresolved gaps and limits: source pair comparison has not started. Queued source publication is not a terminal disposition.
- Evidence/hash/correspondence audit: pending validated readiness JSON, manifest hash checks, exact member correspondence and relevant method-body diagnostics.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (not authorized).

