# Discovery: 1.21.10 to 1.21.11

- Status: active
- Scope: fresh source-only direct client-player movement comparison; older A = 1.21.10; newer B = 1.21.11. Do not read old/current mod implementation or wiki-audit output, browse Minecraft Wiki/MCPK, use release notes, implement Java, or perform runtime validation before the blind source report is frozen. Exclude health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulation and non-player movement; direct vanilla-state reads by movement predicates remain in scope.
- Repository revision and start date: comparison branch created from `main` at `002137b227676caea77f6832b9f4c8d0b6200bff`; 2026-10-07. Branch `feat/source-discovery-movement-source-1-21-10-1-21-11`.
- Selected naming namespace, CLI mode per side and alignment evidence: campaign roster selects Mojmap from 1.14.4 onward; intended CLI mode is `mojmap` for both sides, pending exact readiness-marker verification.
- Source preparation owner / command / log / readiness marker: shared source owner owns all generation/publication; identity, exact commands, logs and pair markers not yet supplied. No local decompiler run. Canonical root relative to repository: `build/movement-campaign-2026-10-07`.
- Toolchain/decompiler/remapper versions and options: Java 25 fork; Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1, Gson 2.14.0, ASM 9.10.1 from the version catalog. Exact pair options pending readiness records.
- Discovery author(s): this worker.
- Independent reviewer (must differ from discovery authors): pending assignment.

## Artifact manifest

A = 1.21.10; B = 1.21.11. No exact-pair readiness JSON has been published under `build/movement-campaign-2026-10-07/ready/` at this checkpoint. The shared decompile lock was observed held. Per side, source root, exact resolved release ID, client jar SHA-256, Mojmap mapping artifact/build/path/SHA-256, mapped jar SHA-256, source inventory and source hashes are pending the source-owner marker. Verify the marker itself and every cited hash before opening evidence. The local decompiler resolver can map a non-exact ID to the newest matching prefix; confirm both requested and resolved IDs in marker/log. Required-class checks do not establish method-body completeness. The Java source saver omits jar resources; hash relevant original jar resource entries separately. Do not regenerate or modify shared artifacts.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending source coverage and independent audit.
- Evidence inventory and finding IDs included at freeze: pending; no source finding is confirmed yet.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; neither has been opened. No wiki browsing or wiki/MCPK/release-note evidence used.
- Source/mapping hashes covered by freeze: pending exact readiness manifests.

## Correspondence and call order

Pending pair publication. Resolve exact classes, descriptors, inheritance, callers, overrides and source anchors separately for both releases. Required end-to-end sequence: local input sampling → local tick/superclass tick → pre-travel state/input preparation → travel dispatch and every reachable branch → move/collision/support callbacks → post-travel state updates. Record exact call sequence and state read/write edges for input history/yaw; pose, dimensions and eye height; position, velocity and box; collision/ground/fluid/support flags; movement attributes; sprint/jump timers; and equipment/effect state. Follow every changed or influential helper, writer, consumer, registration and resource.

## Required source inventories

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1-*,S3-*; evidence=exact pair sources unavailable.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S1-*,S2-*,S3-*; evidence=exact pair sources unavailable.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S4-*,S5-contact-shapes,S5-landing-bounce,S5-neighbors,S5-climbables; evidence=exact pair sources unavailable.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S5-*,S6-data-resources; evidence=exact pair sources unavailable.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S3-attributes,S6-*; evidence=exact pair sources unavailable.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=S7-*; evidence=exact pair sources unavailable.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=delegated scope set; direct vanilla-state consumers remain covered by owning movement slices.

## Coverage ledger

The entries below are provisional behavior buckets from the required navigation stages. They remain pending and must be split into source-bounded member/body ranges wherever a bucket spans multiple methods, writers, callers, providers, registrations or resources. These labels are not a completed inventory or evidence of equivalence.
### Slice S1-input-sampling: keyboard-controller sampling, input object lifetime and previous/current input capture

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S1-local-tick: local player tick, superclass tick, travel dispatch and pre/post-travel ordering

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S1-yaw-relative: yaw-to-motion conversion, diagonal normalization, relative acceleration and input scaling

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S1-sneak-use: sneak and item-use input scaling, edge-sneak path and timing

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S1-sprint: sprint start/stop gates, timers, transitions, sprint-jump interaction

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S1-jump: jump input, cooldown, jump delay, auto-jump and jump state capture

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S1-flight-ride: flight toggle/input, unstuck behavior and riding gates affecting player

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S2-pose: pose selection, swimming/crawling transitions and stored pose state

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S2-dimensions: pose/entity dimensions, resize timing, eye height when used by movement/fluid queries

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S2-player-gates: player superclass movement gates, blindness/item-use/abilities consumers and field defaults/resets

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S2-flight-state: abilities, flight speed, flying/walking state and stored air speed

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S3-travel-dispatch: living travel branch selection and dispatch conditions

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S3-ground-air: ground/air acceleration, friction, drag and gravity with exact FP order

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S3-cutoffs: negligible velocity thresholds, comparisons, normalization and post-travel cleanup

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S3-jump-impulse: jump power, sprint jump impulse, yaw trigonometry and velocity writes

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S3-climb: climbing detection, clamps, movement and exit velocity behavior

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S3-water: water acceleration, drag, gravity, swimming and fluid-height interactions

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S3-lava: lava travel acceleration, drag, gravity and collision outcomes

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S3-glide: gliding travel and directly consumed movement attributes/state

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S3-attributes: movement speed, jump/gravity/step-related attribute consumers, aggregation/order/defaults

- Inventory ID(s): INV-TICK, INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S4-move-core: entity move dispatch, axis ordering, position and velocity updates

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S4-collision-query: collision candidate acquisition, shape context/query timing, pose/box dependence

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S4-step: step-up eligibility, candidate paths, height comparisons and tie-breaking

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S4-edge-support: edge probes, support lookup, grounding and support-block state

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S4-velocity-collision: axis cancellation/restitution, collision flags and fall/ground callbacks

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S4-shape-math: AABB/voxel shape intersections, clipping, epsilon and iteration behavior

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S4-callbacks: block/entity movement callbacks reachable from player move and their order

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S5-block-registry: block registrations/default friction, speed/jump factors and shape-provider changes

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S5-landing-bounce: slime/bed landing, bounce, support and callback behavior

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S5-slow-surface: soul sand, ice and other speed/friction surface providers

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S5-contact-shapes: web/honey/powder snow and historical partial-block collision/support shapes

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S5-climbables: ladders, vines and other climbable registrations, callbacks and neighbor dependence

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S5-fluids: water/lava/bubble columns, flow vectors, fluid heights and push timing

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S5-neighbors: neighboring/state-dependent collision and movement providers, including piston displacement path

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S5-modern-only: blocks/states absent in A: verify registration/absence and record modern-only applicability; no historical behavior invented

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S6-effects: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace, Blindness consumers and formulas

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S6-enchantments: Depth Strider, Soul Speed, Swift Sneak, Frost Walker, Riptide and other registered movement-affecting entries

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S6-equipment-items: Elytra, item-use slowdown, equipment slots/components and player movement predicates

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S6-data-resources: client jar data, tags, effect/enchantment definitions, registry defaults and referenced values

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S6-attribute-lifecycle: attribute modifier application/removal, stacking, timers and equipment/effect lifecycle

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S7-external-velocity: client consumers and writers for incoming velocity/position corrections and player knockback/push

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S7-environment-displacement: explosions, pistons, launch items, mount/dismount transitions insofar as they write player movement state

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice S7-state-writer-closure: exhaustive remaining reachable player movement-state writers, callbacks, registrations and dependencies discovered from prior stages

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

### Slice X-scope-exclusions: health, regeneration, hunger/food/saturation/exhaustion/damage/combat emulation and non-player physics

- Inventory ID(s): INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pending exact 1.21.10 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- B evidence: pending exact 1.21.11 source root, owner/member/descriptor, body line range and SHA-256 (or checked absence path).
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-PAIR-READY`, then dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: none confirmed; source comparison has not started.

## Dependency queue and blockers

- Open dependencies: `D-PAIR-READY`, `D-METHOD-DIAGNOSTICS`.
- `D-PAIR-READY` / source gate / exact readiness markers for 1.21.10 and 1.21.11 plus selected namespace, artifact manifests and logs are unavailable; provenance and alignment cannot yet be verified. Next: read the source-owner markers, verify requested/resolved IDs and all cited hashes. Owner: shared source owner. Status: pending.
- `D-METHOD-DIAGNOSTICS` / source gate / pair-specific movement-method diagnostics and relevant full bodies are unavailable; required-class checks are insufficient to establish method completeness. Next: inspect diagnostics and every selected body; request exact source/bytecode help for any damaged body. Owner: source owner for artifacts, discovery worker for slice audit. Status: pending; depends on `D-PAIR-READY`.

## Finding index

No source findings confirmed yet; comparison has not started. This is not a no-difference conclusion.

## Resume checkpoint

- Last completed slice: source-gate preparation and 44 provisional stage behavior buckets; no exact-pair source slice completed.
- Next bounded slice and exact files/members/body ranges to open: after marker/hash verification, start `S1-input-sampling`; resolve the exact local player/input classes and complete input sampling → local tick → superclass/tick/travel call sequence in both releases.
- Outstanding dependencies and owners: `D-PAIR-READY` (shared source owner); `D-METHOD-DIAGNOSTICS` (shared source owner, then discovery worker verification).
- Current assumptions requiring verification: intended Mojmap alignment; exact resolved release IDs; marker/source/artifact hashes; decompiler diagnostics; class/member correspondence; relevant jar resource availability and provenance.

## Implementation reconciliation

- Reconciliation status: pending
- Repository revision inspected: not inspected; blind source-only constraint.
- Finding -> implementation disposition/evidence: deferred until after blind-discovery freeze and assigned to an implementation reconciler.
- Existing implementation without a frozen source finding: not inspected; deferred.
- Coverage gaps routed back to discovery slices: pending source audit.

## Independent source audit

- Reviewer: pending assignment; reviewer must not be a discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: pending.
- Concrete missed-slice routes (or `none found`): pending.
- Misses routed to slice/finding IDs and owners: pending.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: 44 pending; 0 in-progress; 0 compared-no-difference; 0 findings; 0 not-applicable; 0 blocked.
- Required inventory status and evidence: all seven inventories pending exact-pair readiness and source traversal.
- Open dependencies: `D-PAIR-READY`, `D-METHOD-DIAGNOSTICS`.
- Unresolved gaps and limits: both exact source sets and method diagnostics are unavailable; no movement behavior has been examined. Keep active while source publication is in progress; at handoff, any open source slice requires partial status.
- Evidence/hash/correspondence audit: no Minecraft source evidence cited yet. Verify exact JSON and artifact hashes before promoting any slice.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed; explicit authorization not given.

The completion checker validates schema/status only and cannot establish source truth. The independent source audit remains mandatory. Keep this folder limited to run.md and optional findings/*.md.
