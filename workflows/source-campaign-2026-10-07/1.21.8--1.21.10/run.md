# Discovery: 1.21.8 to 1.21.10

- Status: active
- Scope: direct client-player movement; older A = `1.21.8`; newer B = `1.21.10`.
- Repository revision and start date: base `002137b227676caea77f6832b9f4c8d0b6200bff` (`main`), 2026-10-07.
- Work branch: `feat/source-discovery-movement-source-1-21-8-1-21-10`.
- Selected naming namespace: requested `mojmap` on both sides; exact availability and release alignment pending validated source-owner records.
- Source preparation owner / command / log / readiness marker: exclusive shared source owner; no command run by this worker. Exact pair's records not yet published.
- Toolchain/decompiler/remapper versions and options: pending readiness records.
- Discovery author(s): Codex source-only worker for this pair.
- Independent reviewer: pending coordinator assignment; must differ from discovery author.

## Artifact manifest

The source owner has not published either exact pair endpoint yet. Shared `ready/` currently contains records through `1.21.3` plus native `26.2`; neither `1.21.8` nor `1.21.10` source tree, readiness marker, nor artifact directory is present. The shared `decompile.lock` is held. This is a preparation dependency, not evidence of equivalence or a terminal source blocker. Do not infer readiness from directories. After publication verify exact requested/resolved IDs, namespace, cited hashes, movement-method diagnostics, and relevant decompiler diagnostics. Record per side: exact release, source root, original client jar SHA-256, CLI mode, mapping coordinate/build/path/hash, mapped jar SHA-256, source/resource hashes, toolchain versions/options, owner command/log/readiness marker. Reject prefix substitution or damaged relevant method bodies.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: not frozen; initial schema/coverage checkpoint is commit `e7f6ab7`, amended to this schema in a later checkpoint.
- Evidence inventory and finding IDs included at freeze: none; source comparison has not begun.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze: confirmed. Only repository docs, movement-discovery workflow docs/templates/checker, and decompiler tooling were read. No implementation source, code diff, Minecraft Wiki, MCPK, or wiki-audit output was opened.
- Source/mapping hashes covered by freeze: none yet.

## Correspondence and call order

Pair-specific classes, descriptors, inheritance, callers, replacements, method ranges, source hashes and tick order remain unresolved until the validated pair is published. Afterward index the entire reachable local player tick sequence through input sampling, local/super tick, pre-travel, travel dispatch and every branch, movement/collision calls, and post-travel. Record each state read/write edge (input; position/velocity; box/pose; on-ground/collision/fluid/support flags; timers; movement attributes; equipment/effects), all callers and relevant changed dependencies. Do not infer correspondence from Mojmap names alone.

## Required source inventories

Each inventory links to bounded slices below. All are pending; no source evidence has been read yet.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1.1,S1.2,S1.3,S1.4,S3.1,S3.2,S3.3,S3.4; evidence=awaiting exact source pair and member-level inventory.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S2.1,S2.2,S4.1,S4.3; evidence=awaiting exact source pair and writer/consumer walk.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S4.1,S4.2,S4.3,S5.1; evidence=awaiting exact source pair, registrations and shape inventory.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S5.1,S5.2,S5.3; evidence=awaiting exact source pair and client-jar resource inventory.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S6.1,S6.2; evidence=awaiting exact source pair and resource/data closure.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=S7.1,S7.2; evidence=awaiting exact source pair and client consumer/writer inventory.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=after source inventory, enumerate direct vanilla-state reads retained in movement predicates while excluding those producer systems and all independent non-player physics.

## Coverage ledger

The following are pending scope atoms for source-led member indexing. Each must be split further whenever source methods/dependencies exceed a bounded behavior. Pending entries do not assert that methods are unchanged or establish final coverage.

### Slice S1.1: Local player tick and pre-travel/travel/post-travel call order

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: input sampling through local/super tick, pre-travel, travel dispatch/branches and post-travel; exact boundaries pending sources.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: pending full-tick state graph.
- Parent slices / dependencies / closure evidence: source readiness D1; correspondence and full caller graph.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; wait for pair publication and split to bounded method/body slices.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S1.2: Input sampling, yaw-to-motion conversion and input scaling

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: player-reachable input sampling/conversion, normalization, sneak/item-use scaling and producer timing; boundaries pending source inventory.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: input/key/options producers -> movement input fields -> pre-travel/travel consumers; exact members pending.
- Parent slices / dependencies / closure evidence: S1.1; source readiness D1.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S1.3: Sprint/jump gates, timers and impulses before travel

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: sprint start/stop, jump input/cooldown/state and reachable pre-travel impulse ordering; boundaries pending.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: sprint/jump input and timer writers -> gates, velocity writers and travel consumers; exact members pending.
- Parent slices / dependencies / closure evidence: S1.1, S1.2; movement predicates may read vanilla food/hunger state, whose producer systems remain excluded (S8.1).
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; predicate reads and producer-system scope must remain distinct.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S1.4: Auto-jump, flight toggles, unstuck and riding gates

- Inventory ID(s): INV-TICK, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: player-local control branches and state transitions affecting movement; boundaries pending.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: control inputs/abilities/riding transitions -> movement flags and velocity/position consumers; exact members pending.
- Parent slices / dependencies / closure evidence: S1.1; S7.2; source readiness D1.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S2.1: Pose, dimensions and eye height affecting movement/query timing

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: pose selection, resize timing, dimensions and eye-height consumers relevant to collision/fluid/player movement; boundaries pending.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: pose/dimension writers -> bounding box, collision and fluid query consumers; exact members pending.
- Parent slices / dependencies / closure evidence: S4.1; resource/pose definitions if reachable; source readiness D1.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S2.2: Movement state writers, direct gates, item use and edge sneaking

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: initialization/update/reset of movement flags/timers, item-use slowdown, edge-sneak logic and direct movement predicates; exclude health/food producer simulation.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: all discovered writers -> readers in local tick/travel/collision; exact edges pending.
- Parent slices / dependencies / closure evidence: S1.1-S1.4, S2.1, S8.1.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; writer inventory required before any closure.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S3.1: Ground and air acceleration, friction and speed input

- Inventory ID(s): INV-TICK, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: bounded ground/air travel branches and their direct movement-speed/friction dependencies; exact member slices pending.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: input, on-ground state, movement attributes and block factors -> acceleration/velocity consumers; exact edges pending.
- Parent slices / dependencies / closure evidence: S1.1-S1.3, S4.2-S4.3, S5.2, S6.1.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; preserve expression order, casts and cutoffs in member-level comparison.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S3.2: Gravity, drag, negligible velocity thresholds and post-travel updates

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: travel math and ensuing state updates affecting player velocity/position; split exact methods after inventory.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: velocity/gravity/drag writers -> threshold checks, collision movement and post-travel consumers; exact members pending.
- Parent slices / dependencies / closure evidence: S1.1, S3.1, S4.2-S4.3.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S3.3: Jump, sprint-jump and climbing movement

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: jump power/impulse and climbing branch/clamps, including ordering against collisions and travel updates; boundaries pending.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: jump/sprint inputs, effect/attribute/block state -> velocity writers and travel consumers; exact members pending.
- Parent slices / dependencies / closure evidence: S1.3, S3.1, S4.3, S5.2, S6.1.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S3.4: Water/lava, swimming, gliding and other fluid travel branches

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: branch predicates, fluid travel math, swimming/gliding and post-branch state; split by bounded source member.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: fluid contact/height/flow, equipment/effects/attributes -> travel velocity and flags; exact edges pending.
- Parent slices / dependencies / closure evidence: S2.1, S3.1-S3.2, S4.3, S5.3, S6.1-S6.2.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S4.1: Bounding-box resize and collision-query timing

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: player box updates and query timing across pose/tick movement, including repeated queries within a tick; boundaries pending.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: pose/dimensions/position -> AABB and world-query consumers; exact members pending.
- Parent slices / dependencies / closure evidence: S2.1-S2.2, S4.2-S4.3.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S4.2: Axis collision resolution and step candidates/tie-breaking

- Inventory ID(s): INV-COLLISION, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: player movement box clipping, axis order, step-up candidates, candidate selection/tie breaks and enclosing conditions; split exact methods after inventory.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: desired movement and shapes -> clipped displacement, collision flags and resulting velocity; exact edges pending.
- Parent slices / dependencies / closure evidence: S4.1, S4.3, S5.1.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S4.3: Support, on-ground flags, velocity response, fluids and callbacks

- Inventory ID(s): INV-STATE, INV-COLLISION, INV-TICK, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: support/ground lookup, collision-flag writers, velocity cancellation/restitution, fluid push and callback order; split exact methods as needed.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: collision and support result writers -> next travel gates; callbacks/fluids -> player velocity/flags; exact edges pending.
- Parent slices / dependencies / closure evidence: S2.1, S3.1-S3.4, S4.1-S4.2, S5.1-S5.3.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S5.1: Historical block shape providers, registrations and neighbor dependencies

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: blocks present in A and B whose player collision/support shapes or shape contexts can change movement; enumerate providers, registration and neighboring-state dependencies; block states remain vanilla.
- A evidence: pending validated `1.21.8` Mojmap source/resources; no source range/hash yet.
- B evidence: pending validated `1.21.10` Mojmap source/resources; no source range/hash yet.
- State producers/writers -> consumers/readers: state/context/neighbors -> shape provider -> player world collision/support query; exact edges pending.
- Parent slices / dependencies / closure evidence: S4.1-S4.3; registry, resource, tags and subclasses inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; modern-only blocks do not receive historical behavior.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S5.2: Block friction, speed/jump factors and movement callbacks

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-TICK, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: registered/overridden movement factors and player-reachable callbacks (ice/soul sand/slime/bed/web/honey/snow/climbables/pistons and newly discovered entries); split by provider/callback.
- A evidence: pending validated `1.21.8` Mojmap source/resources; no source range/hash yet.
- B evidence: pending validated `1.21.10` Mojmap source/resources; no source range/hash yet.
- State producers/writers -> consumers/readers: block/provider registry and contact state -> acceleration, jump, collision or player callback consumers; exact edges pending.
- Parent slices / dependencies / closure evidence: S3.1, S3.3, S4.3, S5.1; client-jar resource entries and referenced data.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; inventory is not limited to previously known block names.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S5.3: Fluid contact, height, flow vectors and bubble-column movement

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: fluid state/contact/height/flow and player push/travel paths, including relevant registry/data dependencies; split exact helpers.
- A evidence: pending validated `1.21.8` Mojmap source/resources; no source range/hash yet.
- B evidence: pending validated `1.21.10` Mojmap source/resources; no source range/hash yet.
- State producers/writers -> consumers/readers: fluid blocks/state/data -> contact, flow, height/push -> player velocity/travel consumers; exact edges pending.
- Parent slices / dependencies / closure evidence: S3.4, S4.3; source and jar resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; no conclusion.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S6.1: Movement attribute/effect consumers, aggregation and application

- Inventory ID(s): INV-MODIFIERS, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: movement-speed/jump/gravity/other movement attributes and Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace, Blindness consumers; trace operations, order, defaults, modifiers and application/removal.
- A evidence: pending validated `1.21.8` Mojmap source/resources; no source range/hash yet.
- B evidence: pending validated `1.21.10` Mojmap source/resources; no source range/hash yet.
- State producers/writers -> consumers/readers: effect/attribute registration/application -> aggregation -> travel/jump/player predicates; exact edges pending.
- Parent slices / dependencies / closure evidence: S3.1-S3.4; source registrations, resource definitions/tags and external synchronization boundary.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; presence/absence and data-driven definitions need evidence.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S6.2: Enchantments, equipment and movement item components

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Depth Strider, Soul Speed, Swift Sneak, Frost Walker, Riptide, Elytra, use-item slowdown, relevant equipment slots/components and other registry-discovered movement entries; trace formulas, conditions, tags and server boundaries.
- A evidence: pending validated `1.21.8` Mojmap source/resources; no source range/hash yet.
- B evidence: pending validated `1.21.10` Mojmap source/resources; no source range/hash yet.
- State producers/writers -> consumers/readers: equipment/effect/enchantment registration and application -> player movement consumers; exact edges pending.
- Parent slices / dependencies / closure evidence: S3.1-S3.4, S5.2-S5.3; jar resources, tags and synchronized external inputs.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; server-side Frost Walker/world mutations are not inferred from client movement source.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S7.1: Player velocity/position corrections and incoming movement inputs

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: client consumers and timing for server-supplied corrections/velocity; retain protocol values as external input rather than emulated vanilla producer rules.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: inbound packet/correction consumer -> player position/velocity/flags -> next tick readers; exact members pending.
- Parent slices / dependencies / closure evidence: S1.1-S1.4, S2.2; networking consumers and state writers.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; exact client path remains to be indexed.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S7.2: Player push, explosion/piston displacement and mount transitions

- Inventory ID(s): INV-EXTERNAL, INV-COLLISION, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: direct player-facing movement writers and local transition consumers; other entities' independent simulation is excluded.
- A evidence: pending validated `1.21.8` Mojmap source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap source; no source range yet.
- State producers/writers -> consumers/readers: external event/client handler -> player position/velocity/state consumers; exact ownership boundary pending.
- Parent slices / dependencies / closure evidence: S1.4, S4.2-S4.3, S7.1; changed callback/event paths.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): not compared; inspect other-entity code only to explain a direct player effect.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

### Slice S8.1: Excluded health/food/damage systems and direct vanilla-state reads

- Inventory ID(s): INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: identify direct movement predicate reads of vanilla hunger/food/blindness or other excluded-system state; do not inventory/implement their producers or emulate health, regeneration, food, saturation, exhaustion, damage/combat.
- A evidence: pending validated `1.21.8` Mojmap movement-predicate source; no source range yet.
- B evidence: pending validated `1.21.10` Mojmap movement-predicate source; no source range yet.
- State producers/writers -> consumers/readers: vanilla-state producer systems excluded; record only predicate read -> movement gate edges.
- Parent slices / dependencies / closure evidence: S1.3, S2.2, S6.1; full gate inventory required.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): scope audit not started; direct vanilla state reads may be documented without emulating producer systems.
- Finding IDs or checked absence/replacement path: none; no candidate disposition yet.

## Dependency queue and blockers

- Open dependencies:
  - D1 / source owner / exact `1.21.8` and `1.21.10` Mojmap output and readiness markers with exact resolved IDs, source/artifact hashes, movement-method diagnostics, warnings, logs and tool versions; required by all slices. Next action: await shared publication; source owners alone run generation.
  - D2 / discovery worker / after D1, verify cited source hashes and relevant body diagnostics, then enumerate matching client-jar resources/tags/defaults and classify vanilla default vs server-synchronized vs external input.
  - D3 / coordinator / assign independent source auditor before closure.

## Finding index

No findings confirmed yet. Zero confirmed is not equivalence. Candidate/discarded-candidate inventory awaits source access.

## Resume checkpoint

- Last completed slice: none. Read repository/global instructions, README/buildSrc README, discovery workflow and navigation stages/templates/checker, and decompiler implementation. Created schema/coverage checkpoint; no source comparison begun.
- Next bounded slice: verify pair readiness JSONs, exact IDs, namespace, hashes and method diagnostics; then enumerate filenames and resolve the actual local tick entry, callers and member ranges before comparing S1.1/S1.2.
- Outstanding dependencies and owners: D1 source owner; D2 discovery worker after D1; D3 coordinator.
- Current assumptions requiring verification: Mojmap is aligned/available for both exact patch releases; resolver did not prefix-substitute; relevant method bodies are complete and hash-verified.

## Implementation reconciliation

Do only after blind-discovery freeze and through the designated integrator; this source-only worker will not inspect or reconcile mod implementation.

- Reconciliation status: pending
- Repository revision inspected: not inspected in this source-only track.
- Finding -> implementation disposition/evidence: deferred to integrator after freeze.
- Existing implementation without a frozen source finding: deferred to integrator.
- Coverage gaps routed back to discovery slices: none yet; route after source freeze/reconciliation.

## Independent source audit

Independent reviewer not yet assigned; no source audit performed.

- Reviewer: pending coordinator assignment
- Status: pending
- Inventories and call-chain ranges re-walked: none
- Concrete missed-slice routes (or `none found`): pending
- Misses routed to slice/finding IDs and owners: pending
- Reviewer evidence / date: pending

## Source audit closure

- Coverage counts by status: pending 21; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0. These pending atoms must be split further into member-bounded slices after source inventory.
- Required inventory status and evidence: INV-TICK pending; INV-STATE pending; INV-COLLISION pending; INV-WORLD-MOVEMENT pending; INV-MODIFIERS pending; INV-EXTERNAL pending; INV-EXCLUSIONS pending. No source inventories complete.
- Open dependencies: D1, D2, D3
- Unresolved gaps and limits: exact sources not yet published; no method-level correspondence, source hashes, data/resource closure, findings, or bounded terminal dispositions established. The canonical completion checker accepts this active report structurally; no completion pass is claimed.
- Evidence/hash/correspondence audit: no source evidence yet; no claims of equivalence.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).
