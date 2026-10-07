# Discovery: 1.21.11 to 26.1.2

- Status: active
- Scope: client player movement; older A = 1.21.11; newer B = 26.1.2.
- Repository revision and start date: 002137b227676caea77f6832b9f4c8d0b6200bff; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: official-name pairing planned; A Mojmap / mojmap; B native unobfuscated / unobfuscated. Validate exact source records and pair correspondence before admitting the pair.
- Source preparation owner / command / log / readiness marker: sole shared-source owner; no command run by this worker. Exact ready markers not yet published for either endpoint.
- Toolchain/decompiler/remapper versions and options: pending source-owner readiness records.
- Discovery authors: source-only worker for 1.21.11--26.1.2.
- Independent reviewer (must differ from discovery authors): pending coordinator assignment.

## Artifact manifest

A: exact release 1.21.11; source root, client jar/hash, Mojmap mapping coordinate/build/file/hash, remapped jar hash, source manifest and diagnostics pending validated readiness JSON.  
B: exact release 26.1.2; source root, original published-unobfuscated client jar/hash, source manifest and diagnostics pending validated readiness JSON. Mapping and remapped jar will be recorded as not applicable only after verifying native unobfuscated publication.  
No sources, artifact manifests or hashes have been admitted yet. At initial readiness checks, the shared ready tree contained no marker for either exact release. Existing 26.2/unobfuscated is not a substitute. Recheck exact IDs, namespace, marker and cited hashes immediately before comparison. Do not infer readiness from directories.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending source comparison.
- Evidence inventory and finding IDs included at freeze: none; comparison has not started.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; none were opened. No wiki or MCPK browsing.
- Source/mapping hashes covered by freeze: pending exact source publication and comparison.

## Correspondence and call order

Pending exact A/B sources. Resolve all actual classes, member signatures/descriptors, inheritance, renames/splits/replacements, callers, direct dependencies, state read/write edges and ordered tick sequence through input, pre-travel decisions, travel dispatch/branches and post-travel. Navigation names remain seeds only. No guessed correspondence.

## Required source inventories

Each inventory maps to the bounded slices below. All are pending because no exact pair source has passed readiness verification.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1.1-S1.7,S2.5,S2.7,S3.1-S3.10,S7.4-S7.5; evidence=pending pair sources and member-level ranges.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S1.4-S1.6,S2.1-S2.7,S3.3-S3.10,S4.4-S4.7; evidence=pending pair sources and writer-consumer links.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S2.2,S2.6,S4.1-S4.7,S5.3-S5.6; evidence=pending pair sources and provider inventories.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S3.2,S3.5-S3.7,S4.6,S5.1-S5.8; evidence=pending pair sources, registrations and resource provenance.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S2.5,S3.2,S3.4,S3.6,S3.8-S3.9,S6.1-S6.6; evidence=pending pair sources and resource/data dependencies.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=S7.1-S7.4; evidence=pending exact source and provenance.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=source-stage audit pending; direct vanilla-state reads in movement predicates remain in scope as consumers only.

## Coverage ledger

These are planned behavior boundaries only. They have no source conclusions. Once exact sources are ready, split broad plans into bounded member/body slices, record exact original line ranges and hashes, and expand for every reachable caller, state writer, shape override, registration, resource and dependency. Keep terminal dispositions closed over producers and consumers.

### Slice S1.1: Local input sampling and key/controller input state

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Local input sampling and key/controller input state. Pair sources unavailable; resolve producers, consumers, fields and timing.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S1.2: Client player tick ordering, superclass tick and travel dispatch

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Client player tick ordering, superclass tick and travel dispatch. Pair sources unavailable; full ordered call path and state snapshot required.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S1.3: Yaw-to-input conversion, diagonal normalization and input scaling

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Yaw-to-input conversion, diagonal normalization and input scaling. Pair sources unavailable; exact float/cast/order comparison required.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S1.4: Sprint transitions, timers and start/stop gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Sprint transitions, timers and start/stop gates. Pair sources unavailable; exclude health/food simulation, retain only direct state consumers.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S1.5: Jump input, jump cooldown/state, auto-jump and riding gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Jump input, jump cooldown/state, auto-jump and riding gates. Pair sources unavailable; resolve producers and reset timing.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S1.6: Flight toggle/input, abilities and flight-speed path

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Flight toggle/input, abilities and flight-speed path. Pair sources unavailable; include only direct player movement path.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S1.7: Unstuck behavior and other input-to-tick movement gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Unstuck behavior and other input-to-tick movement gates. Pair sources unavailable; discover from complete entry call graph.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.1: Player pose selection and pose transition timing

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Player pose selection and pose transition timing. Pair sources unavailable; include all reachable writers and guards.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.2: Pose dimensions, eye height, resize collision queries

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Pose dimensions, eye height, resize collision queries. Pair sources unavailable; follow shape/query timing and state writes.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.3: Swim/crawl state and movement-mode selection

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Swim/crawl state and movement-mode selection. Pair sources unavailable; resolve triggers and downstream travel dispatch.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.4: Ability defaults, stored air speed and player movement state

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Ability defaults, stored air speed and player movement state. Pair sources unavailable; follow initialization, updates and resets.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.5: Item-use slowdown and direct active-item movement gate

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Item-use slowdown and direct active-item movement gate. Pair sources unavailable; food/hunger simulation excluded.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.6: Edge sneaking and support probing

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Edge sneaking and support probing. Pair sources unavailable; follow collision and support dependencies.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.7: Sprint-gate consumers of hunger/blindness state

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Sprint-gate consumers of hunger/blindness state. Pair sources unavailable; disposition consumers only; health, hunger, food and effect simulation excluded.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.1: Travel dispatch and ground acceleration

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Travel dispatch and ground acceleration. Pair sources unavailable; preserve complete method order and dependencies.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.2: Ground friction, speed-factor consumption and post-travel drag

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Ground friction, speed-factor consumption and post-travel drag. Pair sources unavailable; resolve block/state providers and attribute inputs.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.3: Air acceleration, drag/gravity and velocity thresholds

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Air acceleration, drag/gravity and velocity thresholds. Pair sources unavailable; explicit cutoff and all velocity writers/consumers required.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.4: Jump power, sprint-jump impulse and jump state writes

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Jump power, sprint-jump impulse and jump state writes. Pair sources unavailable; exact arithmetic and conditions required.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.5: Climbing movement and clamps

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Climbing movement and clamps. Pair sources unavailable; resolve climbable blocks/shapes and movement state.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.6: Water travel, swimming, buoyancy/drag and fluid effects

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Water travel, swimming, buoyancy/drag and fluid effects. Pair sources unavailable; include fluid heights/flow and effect/attribute dependencies.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.7: Lava travel and fluid contact path

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Lava travel and fluid contact path. Pair sources unavailable; exclude damage effects except direct movement-state consumers.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.8: Gliding and movement-affecting elytra path

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Gliding and movement-affecting elytra path. Pair sources unavailable; disposition item/attribute data and direct player calls.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.9: Relative movement helpers, vector math and attributes

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Relative movement helpers, vector math and attributes. Pair sources unavailable; close changed helper and aggregation dependencies.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.10: Travel post-updates, velocity reset/restitution and fall-state writes

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Travel post-updates, velocity reset/restitution and fall-state writes. Pair sources unavailable; follow all reachable callers and state writers.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.1: Entity move entry, bounding-box and position updates

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Entity move entry, bounding-box and position updates. Pair sources unavailable; preserve axis order and collision-query timing.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.2: Axis resolution, collision candidates and tie-breaking

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Axis resolution, collision candidates and tie-breaking. Pair sources unavailable; include AABB/shape/world query dependencies.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.3: Step-up candidates, comparison and selection

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Step-up candidates, comparison and selection. Pair sources unavailable; compare all candidate paths and exact comparisons.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.4: Edge probes, on-ground and support lookup

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Edge probes, on-ground and support lookup. Pair sources unavailable; include support block and neighboring-state reads.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.5: Collision flags, velocity cancellation and callbacks

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Collision flags, velocity cancellation and callbacks. Pair sources unavailable; include callback ordering and implementations.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.6: Fluid state, contact, height and push/vector calculations

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Fluid state, contact, height and push/vector calculations. Pair sources unavailable; connect fluid path to local player movement.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.7: Pose/dimension-dependent collision query repetition

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Pose/dimension-dependent collision query repetition. Pair sources unavailable; close interaction with S2 pose slices.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.1: Block/state movement defaults and registrations

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Block/state movement defaults and registrations. Pair sources unavailable; enumerate registrations/properties; do not infer from names.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.2: Friction/speed/jump factors and their consumers

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Friction/speed/jump factors and their consumers. Pair sources unavailable; follow default values and all reachable overrides.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.3: Landing/bounce callbacks and support behavior

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Landing/bounce callbacks and support behavior. Pair sources unavailable; include slime/bed paths where present and applicable.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.4: Ice, soul sand, honey, web and other direct slowdown surfaces

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Ice, soul sand, honey, web and other direct slowdown surfaces. Pair sources unavailable; classify absent blocks as modern-only with checked registry evidence.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.5: Climbable block registration, collision and contact behavior

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Climbable block registration, collision and contact behavior. Pair sources unavailable; close S3 climbing dependencies.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.6: Partial-block collision/support shapes and neighbor-dependent shapes

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Partial-block collision/support shapes and neighbor-dependent shapes. Pair sources unavailable; enumerate overrides/providers; compare states only where block existed in A.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.7: Pistons, bubble columns and other movement-producing block/fluid paths

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Pistons, bubble columns and other movement-producing block/fluid paths. Pair sources unavailable; distinguish direct player displacement from entity simulation.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.8: Relevant block/fluid tags, data and resource defaults

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Relevant block/fluid tags, data and resource defaults. Pair sources unavailable; inspect original version-matched jars; absent external data remains blocked.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S6.1: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphins Grace and Blindness consumers

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphins Grace and Blindness consumers. Pair sources unavailable; source-list every consumer/formula; exclude health/damage/food simulation.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S6.2: Movement attributes: definitions, operations, aggregation and movement consumers

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Movement attributes: definitions, operations, aggregation and movement consumers. Pair sources unavailable; include defaults, modifier order, clamping and synchronized inputs.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S6.3: Depth Strider, Soul Speed, Swift Sneak and Riptide paths

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Depth Strider, Soul Speed, Swift Sneak and Riptide paths. Pair sources unavailable; trace registration to condition/data to application to consumer.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S6.4: Frost Walker boundary and movement-relevant server-supplied effects

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Frost Walker boundary and movement-relevant server-supplied effects. Pair sources unavailable; do not infer server-side world mutation.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S6.5: Equipment, elytra, use-item state and movement-relevant components/tags

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Equipment, elytra, use-item state and movement-relevant components/tags. Pair sources unavailable; exclude modern-only equipment behavior from old profiles.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S6.6: Other movement-affecting effects/enchantments found by registration inventory

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Other movement-affecting effects/enchantments found by registration inventory. Pair sources unavailable; discovery must extend beyond named examples.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S7.1: Incoming velocity/position corrections and local player packet consumers

- Inventory ID(s): INV-EXTERNAL, INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Incoming velocity/position corrections and local player packet consumers. Pair sources unavailable; separate client computation from external input.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S7.2: Player knockback/push and explosion velocity writers

- Inventory ID(s): INV-EXTERNAL, INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Player knockback/push and explosion velocity writers. Pair sources unavailable; inspect only player-facing path, not independent entity physics.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S7.3: Piston displacement, launch items and external movement impulses

- Inventory ID(s): INV-EXTERNAL, INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Piston displacement, launch items and external movement impulses. Pair sources unavailable; identify client vs server ownership.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S7.4: Mount/dismount transitions and riding movement gates

- Inventory ID(s): INV-EXTERNAL, INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Mount/dismount transitions and riding movement gates. Pair sources unavailable; follow player transition state only.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S7.5: Closure sweep: all reachable player movement writers, changed dependencies and cross-mechanic interactions

- Inventory ID(s): INV-EXTERNAL, INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Closure sweep: all reachable player movement writers, changed dependencies and cross-mechanic interactions. Pair sources unavailable; audit all stages and update parents after dependency findings.
- A evidence: pending exact 1.21.11 Mojmap ready record and source body range.
- B evidence: pending exact 26.1.2 native-unobfuscated ready record and source body range.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Exact endpoint source publication is required.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

## Dependency queue and blockers

- Open dependencies: D0, D1, D2
- D0: all stages; exact 1.21.11 Mojmap ready JSON, source/artifact manifests, original/mapped jar and mapping hashes, diagnostics and tool versions; required to start A-side comparison; owner: shared source-preparation owner; pending.
- D1: all stages; exact 26.1.2 native-unobfuscated ready JSON, source/artifact manifests, original client jar hash, diagnostics and tool versions; 26.2 is not an acceptable substitute; owner: shared source-preparation owner; pending.
- D2: movement resources/tags/data referenced by source; version-matched jar entries and hashes; required to close data-driven movement dependencies; retrieve from validated pair artifacts after D0/D1; pending.

## Finding index

No findings yet. No equivalence conclusions or checked absences. Candidate inventory will be expanded and dispositions recorded only after exact pair comparison.

## Resume checkpoint

- Last completed slice: none; the exact source comparison has not begun.
- Next bounded slice: validate exact readiness JSON and every cited source/artifact manifest hash, inspect movement-method diagnostics, then establish correspondence and start S1.1.
- Outstanding dependencies and owners: D0/D1 shared source owner; D2 source worker after artifact publication.
- Current assumptions requiring verification: 1.21.11 Mojmap is available; 26.1.2 is natively unobfuscated; relevant bodies are intact.

## Implementation reconciliation

- Reconciliation status: pending
- Repository revision inspected: none; source-only blind phase.
- Finding -> implementation disposition/evidence: deferred until blind-discovery freeze and explicit role transition.
- Existing implementation without a frozen source finding: not inspected.
- Coverage gaps routed back to discovery slices: pending source-only comparison.

## Independent source audit

- Reviewer: pending coordinator assignment; must not be a discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: none; source comparison has not started.
- Concrete missed-slice routes (or none found): pending.
- Misses routed to slice/finding IDs and owners: pending.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: 50 pending; 0 in-progress; 0 compared-no-difference; 0 findings; 0 not-applicable; 0 blocked.
- Required inventory status and evidence: all 7 pending; exact pair source publication and source inventory remain open.
- Open dependencies: D0, D1, D2.
- Unresolved gaps and limits: readiness, source/artifact hash verification, diagnostics/body quality, all member correspondences, all movement comparisons and data/resource dependency closure.
- Evidence/hash/correspondence audit: not started; no source evidence admitted.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).
