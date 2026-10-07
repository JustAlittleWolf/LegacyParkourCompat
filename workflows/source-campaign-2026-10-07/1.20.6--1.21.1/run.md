# Discovery: 1.20.6 to 1.21.1

- Status: active
- Scope: direct client-player movement; older A = 1.20.6; newer B = 1.21.1
- Repository revision and start date: base 002137b227676caea77f6832b9f4c8d0b6200bff (main); started 2026-10-07. Workflow hardening 40c34f5 and campaign roster 2422192 cherry-picked onto this task branch.
- Task branch: feat/source-discovery-movement-source-1-20-6-1-21-1
- Selected naming namespace, CLI mode per side and alignment evidence: requested Mojmap for A and Mojmap for B, per the campaign rule for endpoints after 1.14.4. Both exact ready markers and resolved mapping builds are pending verification; do not begin comparison until alignment is evidenced.
- Source preparation owner / command / log / readiness marker: shared campaign source owner (identity not provided); worker does not run decompilation. Exact command/log and ready/<exact-version>/<namespace>.ready.json markers pending publication.
- Toolchain/decompiler/remapper versions and options: pending exact readiness metadata.
- Discovery author(s): delegated source-discovery worker on this task branch.
- Independent reviewer (must differ from discovery authors): pending coordinator assignment.

## Artifact manifest

- A 1.20.6: requested/resolved exact ID pending marker verification; requested Mojmap; source root, original client jar identity/hash, mapping coordinate/build/path/hash, remapped jar hash, source/resource manifests and diagnostics pending validated readiness JSON.
- B 1.21.1: requested/resolved exact ID pending marker verification; requested Mojmap; source root, original client jar identity/hash, mapping coordinate/build/path/hash, remapped jar hash, source/resource manifests and diagnostics pending validated readiness JSON.
- No source tree is treated as ready based on directory presence. Verify exact requested/resolved IDs, namespace, cited hashes, method bodies and diagnostics from each marker before evidence comparison.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending source-only comparison.
- Evidence inventory and finding IDs included at freeze: none yet; all 35 planned slices remain pending.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; neither implementation nor wiki materials have been inspected.
- Source/mapping hashes covered by freeze: pending validated markers and source comparison.

## Correspondence and call order

Not started: exact source trees are not published. After readiness, establish exact class/member descriptors and inheritance per role on both sides, then record the complete reachable local-player tick sequence through input, pre-travel work, each travel branch and post-travel work. Trace callers, method/body ranges, state reads/writes, changed callees and dependencies. No guessed member correspondence will be recorded.

## Required source inventories

All inventories remain pending until both exact source trees are verified and every bounded slice and dependency has evidence.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=I1-I4,L1-L6; evidence=awaiting exact A/B source readiness and full callgraph.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=P1-P4,I1-I4,L1-L6,C1-C5,X1-X4; evidence=awaiting exact source ranges and producer-to-consumer closure.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=C1-C5,B1-B8; evidence=awaiting exact source ranges, registries, shape providers and neighbor chains.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=B1-B8,L5; evidence=awaiting paired code and resource manifests.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=L6,E1-E4; evidence=awaiting paired consumers, producers and resource/data chains.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=X1-X4,B7; evidence=awaiting paired consumers and call chains.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=after source inventory, document excluded producer systems and any direct vanilla-state reads retained as movement predicate inputs.

## Coverage ledger

Create bounded pair-specific slices for every discovered method/behavior and dependency. The rows below are planning units; split them wherever source bodies contain separately scoped behavior. Keep each unit pending until both-side method ranges, producer/consumer links and dependency closure are recorded.

### Slice I1 - local input sampling, input fields and keyboard

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: local input sampling, input fields and keyboard; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice I2 - local player tick order, superclass tick, travel dispatch and repeated movement calls: `pending`.

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: local player tick order, superclass tick, travel dispatch and repeated movement calls: `pending`.; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice I3 - yaw-to-motion conversion, normalization and input scaling (sneak

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: yaw-to-motion conversion, normalization and input scaling (sneak; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice I4 - sprint start

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: sprint start; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice P1 - pose selection, dimensions, eye height, resize timing and collision

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pose selection, dimensions, eye height, resize timing and collision; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice P2 - abilities, flight state

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: abilities, flight state; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice P3 - sprint gates and their vanilla state consumers; exclude hunger

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: sprint gates and their vanilla state consumers; exclude hunger; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice P4 - active-item state, edge sneaking, defaults, initialization, updates and reset timing: `pending`.

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: active-item state, edge sneaking, defaults, initialization, updates and reset timing: `pending`.; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice L1 - travel dispatch and ground

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: travel dispatch and ground; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice L2 - friction, gravity, drag and velocity thresholds

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: friction, gravity, drag and velocity thresholds; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice L3 - jump power, sprint-jump impulse and jump gates: `pending`.

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: jump power, sprint-jump impulse and jump gates: `pending`.; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice L4 - climbing clamps and movement callbacks: `pending`.

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: climbing clamps and movement callbacks: `pending`.; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice L5 - water

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: water; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice L6 - movement speed, jump and gravity attributes

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: movement speed, jump and gravity attributes; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice C1 - bounding-box movement, position updates and axis ordering: `pending`.

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: bounding-box movement, position updates and axis ordering: `pending`.; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice C2 - step-up candidates, tie-breaking, edge probes and support

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: step-up candidates, tie-breaking, edge probes and support; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice C3 - velocity cancellation

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: velocity cancellation; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice C4 - world collision queries, AABB

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: world collision queries, AABB; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice C5 - fluid contact, block callbacks and callback order; track repeated queries

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: fluid contact, block callbacks and callback order; track repeated queries; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice B1 - base block

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: base block; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice B2 - landing

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: landing; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice B3 - friction

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: friction; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice B4 - contact slowdown (webs, honey, powder snow) and historical applicability: `pending`.

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: contact slowdown (webs, honey, powder snow) and historical applicability: `pending`.; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice B5 - climbables (ladders, vines and related blocks), callbacks and support: `pending`.

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: climbables (ladders, vines and related blocks), callbacks and support: `pending`.; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice B6 - water

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: water; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice B7 - moving pistons and player displacement: `pending`.

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: moving pistons and player displacement: `pending`.; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice B8 - shape

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: shape; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice E1 - Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace and Blindness consumers

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace and Blindness consumers; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice E2 - Depth Strider, Soul Speed, Swift Sneak, Frost Walker and Riptide consumer-to-registration

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Depth Strider, Soul Speed, Swift Sneak, Frost Walker and Riptide consumer-to-registration; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice E3 - Elytra, use-item slowdown, equipment, movement item components and application

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Elytra, use-item slowdown, equipment, movement item components and application; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice E4 - other discovered movement-affecting attributes

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: other discovered movement-affecting attributes; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice X1 - incoming velocity

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: incoming velocity; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice X2 - player knockback

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: player knockback; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice X3 - mount

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: mount; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice X4 - final unresolved movement-state writers, changed dependencies and cross-mechanic interactions: `pending`.

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: final unresolved movement-state writers, changed dependencies and cross-mechanic interactions: `pending`.; exact paired method ranges await readiness.
- A evidence: pending 1.20.6 Mojmap readiness and source hash verification.
- B evidence: pending 1.21.1 Mojmap readiness and source hash verification.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0 and D1; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.
## Dependency queue and blockers

- Open dependencies: D0 (exact 1.20.6 and 1.21.1 Mojmap readiness markers, manifests and movement-method diagnostics; source-owner publication; required before comparison); D1 (verify resolved IDs, namespace/build, source/artifact hashes and exact relevant body diagnostics against those markers); D2 (pair-specific callers, state writers, shapes, registrations, resources/tags and dependencies discovered during traversal; add precise retrieval actions and owners as found).
- These are queued source-publication dependencies, not evidence of equivalence or differences. If a relevant damaged/missing body or absent data prevents a slice, record its exact path/range and blocked dependency.

## Finding index

No findings recorded yet. A missing findings directory means zero confirmed findings, not equivalence. Every candidate will be paired, reachable and independently scoped before it is retained.

## Resume checkpoint

- Last completed slice: none; comparison has not begun.
- Next bounded slice and exact files/members/body ranges to open: validate both pair readiness markers, hashes and diagnostics; then inventory filename/callgraph and open first bounded stage-1 methods on both sides.
- Outstanding dependencies and owners: D0 and D1 with shared source owner; D2 dependencies to be assigned as found.
- Current assumptions requiring verification: both Mojmap markers exist and are fresh; requested and resolved IDs match exactly; mapping builds align; cited source/artifact hashes validate; movement-relevant decompiler diagnostics are sound.

## Implementation reconciliation

Complete only after blind-discovery freeze and explicit authorization from the coordinator. This source-only assignment does not inspect or reconcile mod implementation.

- Reconciliation status: pending
- Repository revision inspected: not authorized in this source-only phase.
- Finding -> implementation disposition/evidence: pending; no findings frozen.
- Existing implementation without a frozen source finding: pending separate implementation owner.
- Coverage gaps routed back to discovery slices: pending.

## Independent source audit

Reviewer must differ from discovery authors and must independently re-walk full exact source callgraph and all inventories. No reviewer assigned yet.

- Reviewer: pending coordinator assignment
- Status: pending
- Inventories and call-chain ranges re-walked: pending
- Concrete missed-slice routes (or none found): pending
- Misses routed to slice/finding IDs and owners: pending
- Reviewer evidence / date: pending

## Source audit closure

- Coverage counts by status: 0 findings; 0 compared-no-difference; 0 not-applicable; 0 blocked; 0 pending; 0 in-progress.
- Required inventory status and evidence: all seven required inventories pending; no pair evidence yet.
- Open dependencies: D0, D1, D2.
- Unresolved gaps and limits: all seven source-navigation stages remain open pending source readiness and exhaustive pair-specific inventories. Queued sources are not a comparison result.
- Evidence/hash/correspondence audit: not started; no exact A/B source lines or hashes cited.
- Blind freeze: pending.
- Implementation reconciliation: pending and outside this assignment before explicit post-freeze authorization.
- Independent audit: pending coordinator assignment.
- Source-only declaration: no runtime Java implementation/mod implementation inspection; no wiki/MCPK browsing or wiki-audit output; no release-notes-derived movement claims; health/regen/hunger/food/saturation/exhaustion/damage/combat simulations and non-player movement excluded. Vanilla values may be traced only as direct movement predicate inputs.
- Runtime validation: not performed (separate workflow; not authorized).
