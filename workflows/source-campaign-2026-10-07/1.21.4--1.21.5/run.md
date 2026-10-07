# Source-only movement comparison: Minecraft 1.21.4 to 1.21.5

- Status: active
- Scope: direct client player movement; older A = 1.21.4; newer B = 1.21.5.
- Track: source-only discovery; no wiki comparison, mod implementation inspection, or runtime implementation.
- Repository base: 002137b227676caea77f6832b9f4c8d0b6200bff (main); branch feat/source-discovery-movement-source-1-21-4-1-21-5; start date 2026-10-07.
- Source preparation owner: shared source preparer (identity pending); command/log/toolchain pending readiness.
- Namespace and CLI mode: Mojmap is the expected candidate for both 1.21.x releases; verify exact readiness artifacts before use.
- Discovery author: this task.
- Independent reviewer: pending coordinator assignment.

## Artifact manifest

Neither exact release currently has a ready publication under build/movement-campaign-2026-10-07/ready/. Verify the readiness JSONs, IDs, namespace, cited source/artifact SHA-256 manifests, method diagnostics and relevant bodies before comparison.

- A 1.21.4: readiness JSON, jar identity/hash, CLI mode, mapping coordinate/build/file/hash, remapped jar hash, source root and cited source/resource hashes: pending.
- B 1.21.5: readiness JSON, jar identity/hash, CLI mode, mapping coordinate/build/file/hash, remapped jar hash, source root and cited source/resource hashes: pending.
- Generated sources and artifacts are owned by the shared source preparer. This branch does not write or regenerate them.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending; source comparison has not started.
- Evidence inventory and finding IDs included at freeze: pending.
- Confirmation old mod implementation/code and isolated wiki-audit results were not opened before freeze: confirmed; source-navigation docs and an earlier source-discovery report were used only as navigation.
- Source/mapping hashes covered by freeze: pending.

## Correspondence and call order

Exact-source correspondence pending. Resolve both-side class/member descriptors, inheritance, callers, replacements, field writers, registrations and resources. Record the ordered full tick chain through input, pre-travel, travel branches and post-travel, with state reads/writes and enclosing guards. Similar names and adjacent reports are not proof.

## Required source inventories


- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1-INPUT-SAMPLE..S3-POST; evidence=pending exact source publication.
- `INV-STATE` movement state writers/readers: status=pending; slice_ids=S1-SPRINT-GATE..S2-STATE-WRITERS; evidence=pending exact source publication.
- `INV-COLLISION` collision/query path, shape providers, registrations, callbacks, neighboring-block dependencies: status=pending; slice_ids=S4-COLLISION-QUERY..S5-NEIGHBORS; evidence=pending exact source publication.
- `INV-WORLD-MOVEMENT` block/fluid properties, subclasses, registries, tags/data and resource-backed defaults: status=pending; slice_ids=S5-BLOCK-SHAPES..S5-FLUIDS; evidence=pending exact source publication.
- `INV-MODIFIERS` movement attributes/effects/enchantments/equipment and application/removal/conditions: status=pending; slice_ids=S6-ATTRIBUTES..S6-EQUIPMENT; evidence=pending exact source publication.
- `INV-EXTERNAL` player-only external movement inputs and client consumers: status=pending; slice_ids=S7-CORRECTIONS..S7-MOUNTS; evidence=pending exact source publication.
- `INV-EXCLUSIONS` health, regeneration, hunger, food, saturation, exhaustion, damage/combat and non-player movement: status=pending; slice_ids=direct vanilla-state reads in S1-SPRINT-GATE; evidence=pending explicit scope audit. Do not emulate these producer systems or non-player physics.

## Coverage ledger

These are initial bounded navigation units, all pending because exact sources are not published. Split further into exact method/body ranges and dependencies after inventory. Every terminal slice needs both-side evidence, state producers/writers and consumers/readers, parent/dependencies and reachability disposition.

### Slice S1-INPUT-SAMPLE: Input sampling and previous/current input timing

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending


### Slice S1-INPUT-SCALE: Directional scaling, normalization, yaw-to-motion

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S1-LOCAL-TICK: Local tick order, superclass tick and pre-travel work

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S1-SPRINT-GATE: Sprint eligibility predicates and vanilla state reads

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S1-SPRINT-TIMER: Sprint start/stop state, windows and timers

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory

- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S1-JUMP-GATE: Jump input, eligibility, cooldown and stored state

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S1-SPRINT-JUMP: Sprint-jump impulse and state writes

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S1-AUTOJUMP: Auto-jump probes, timing, normalization and trigger

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S1-FLIGHT: Flight input, toggles and movement speed state

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory

- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S1-RIDING: Player riding gates, jump charge and mount transition

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S2-POSE: Pose selection transitions and movement predicates

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S2-DIMENSIONS: Dimensions, box resize and collision rejection timing

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending


### Slice S2-EYE-HEIGHT: Eye height in fluid and collision checks

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S2-STATE-WRITERS: Movement flags, defaults, resets, timers and consumers

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S3-DISPATCH: Travel dispatch and branch guards

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S3-GROUND: Ground acceleration, friction source and order

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending

- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S3-AIR: Air acceleration, friction and speed attribute use

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S3-GRAVITY-DRAG: Gravity, drag, clamps and velocity cutoffs

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S3-JUMP-MATH: Jump power, vertical impulse and modifier terms

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S3-SPRINT-MATH: Sprint velocity inputs and arithmetic

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range

- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S3-CLIMB: Climb contact, velocity clamps and wall state

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S3-WATER: Water travel, drag, gravity and current

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S3-LAVA: Lava travel, drag, gravity and current

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S3-SWIM: Swimming input/look vector and pose interaction


- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S3-GLIDE: Fall-flying travel and equipment gates

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S3-POST: Post-travel fall-distance, collision and state writes

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S4-COLLISION-QUERY: Player collision query and candidate collection

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending

- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S4-AXIS: Axis order, collision tie-breaks and velocity cancellation

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S4-STEP: Step conditions, alternatives and tie-breaking

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S4-EDGE: Edge support/sneak probes and bounded queries

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S4-GROUND-SUPPORT: Ground/support lookup and collision flag updates

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range

- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S4-SHAPES: Shape context, box operations and repeated movement

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S4-CALLBACKS: Block/entity callbacks and invocation order

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S5-BLOCK-SHAPES: Historical block shapes, providers and overrides

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending


### Slice S5-BLOCK-FACTORS: Friction, speed/jump factors and contact behavior

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S5-NEIGHBORS: Neighbor rules, properties and block registrations

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S5-FLUIDS: Fluid data, height/flow, currents and pushes

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S6-ATTRIBUTES: Movement attributes, aggregation and travel consumers

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending

- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S6-EFFECTS: Direct movement effects, formulas, conditions and timing

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S6-ENCHANTMENTS: Movement enchantments, conditions and resource data

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S6-EQUIPMENT: Equipment/components, applicability and use slowdown

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S7-CORRECTIONS: Player corrections and position/velocity state writers

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory

- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S7-PUSH: Player push/knockback inputs; exclude damage outcomes

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S7-PISTON-LAUNCH: Piston displacement and launch items

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

### Slice S7-MOUNTS: Mount/dismount transitions and externally supplied values

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending


### Slice S7-DEPENDENCIES: Revisit unchanged callers after dependency closure

- Inventory ID(s): pending source-to-slice mapping
- Exact behavior boundary and enclosing guards/order checked: pending source inventory
- A evidence: pending exact 1.21.4 readiness and source range
- B evidence: pending exact 1.21.5 readiness and source range
- State producers/writers -> consumers/readers: pending source inventory
- Parent slices / dependencies / closure evidence: pending
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources not published; no behavior conclusion
- Finding IDs or checked absence/replacement path: pending

## Dependency queue and blockers

- D0: Obtain exact 1.21.4/1.21.5 readiness JSONs, SHA-256 manifests and diagnostics; verify IDs, namespace, hashes and bodies. Owner: shared source preparer.
- D1: Inventory both player/input/travel/collision hierarchies, callers and writers; follow changed/influential helpers into bounded slices. Owner: discovery.
- D2: Inspect matching client-jar resources and referenced defaults/tags/components omitted by source output; hash entries. Owner: discovery after D0.
- Current dependency: exact-pair ready publications absent; this is not equivalence evidence.

## Finding index

No confirmed findings. This is not a no-difference claim; create one file per independently scoped, source-confirmed delta.

## Resume checkpoint

- Last completed slice: none; guidance and ordered navigation read, pair inventory unavailable.
- Next: validate readiness, inventory input and local tick entry points, then compare paired bodies.
- Outstanding dependencies and owners: D0 source preparer; D1-D2 discovery.
- Assumptions requiring verification: expected Mojmap alignment, provenance, all movement call graphs and resource dependencies.

## Implementation reconciliation

- Reconciliation status: pending
- Repository revision inspected: none; blind freeze not reached.
- Finding -> implementation disposition/evidence: deferred until freeze.
- Existing implementation without a frozen source finding: not inspected.
- Coverage gaps routed back to discovery slices: pending.

## Independent source audit

- Reviewer: pending coordinator assignment; must differ from author.
- Status: pending
- Inventories and call-chain ranges re-walked: pending
- Concrete missed-slice routes (or `none found`): pending
- Misses routed to slice/finding IDs and owners: pending

- Reviewer evidence / date: pending

## Source audit closure

- Coverage counts: 0 compared-no-difference; 0 findings; 0 not-applicable; 0 blocked; 46 pending; 0 in-progress.
- Required inventories: all seven pending, with exact-source evidence outstanding.
- Open dependencies: D0-D2.
- Gaps: full movement call graph, state writers/consumers, collision/shape providers/registrations/neighbors, resource data and external player influences.
- Evidence/hash/correspondence audit: not started; readiness verification required.
- Blind freeze: pending.
- Implementation reconciliation: pending.
- Independent audit: pending.
- Runtime validation: not performed; separate workflow and not authorized.


