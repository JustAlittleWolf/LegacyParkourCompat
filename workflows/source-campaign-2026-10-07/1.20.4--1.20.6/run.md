# Discovery: 1.20.4 to 1.20.6

- Status: active
- Scope: direct client-player movement; older A = 1.20.4; newer B = 1.20.6. Source-only discovery; no Minecraft wiki/MCPK sources or audit outputs, no mod implementation inspection, no runtime implementation or runtime validation.
- Repository revision and start date: base main = 002137b227676caea77f6832b9f4c8d0b6200bff; task branch = feat/source-discovery-movement-source-1-20-4-1-20-6; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: pending validated source-owner handoff; requested alignment is Mojmap / Mojmap.
- Source preparation owner / command / log / readiness marker: shared source owner; exact pair publication and successful logs pending. This worker will not run decompileMinecraft or write shared artifacts.
- Toolchain/decompiler/remapper versions and options: pending validated provenance.
- Discovery author(s): Codex source worker in this task.
- Independent reviewer (must differ from discovery authors): not yet assigned.

## Artifact manifest

- A: requested/resolved 1.20.4; expected Mojmap. Source root, client jar identity/hash, mapping coordinate/build/path/hash, mapped jar hash, source file hashes, tool versions/options, and successful log: pending validated ready JSON and cited manifests.
- B: requested/resolved 1.20.6; expected Mojmap. Source root, client jar identity/hash, mapping coordinate/build/path/hash, mapped jar hash, source file hashes, tool versions/options, and successful log: pending validated ready JSON and cited manifests.
- No source-tree or artifact-directory presence is treated as readiness. Exact release IDs, namespace, cited manifest hashes and relevant body diagnostics must be verified before comparison.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending; do not freeze until all source slices and dependencies close.
- Evidence inventory and finding IDs included at freeze: pending source comparison.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; neither mod implementation nor wiki material/audit outputs has been opened.
- Source/mapping hashes covered by freeze: pending validated source handoff.

## Correspondence and call order

- Exact paired class/member correspondence, descriptors, inheritance/callers, split/replacement evidence, read/write state and ordered calls: pending validated source roots. No guessed correspondence is accepted.
- Navigation sequence: stage 1 local input and tick ordering; stage 2 player state and gates; stage 3 living movement; stage 4 entity movement/collision; stage 5 blocks/fluids; stage 6 attributes/effects/enchantments/equipment; stage 7 external influences and final dependency closure. Every entry point and complete method body/caller/dependency must be inventoried before disposition.
- Navigation-only prior report consulted: workflows/movement-discovery/runs/1.19.4--1.20.6/run.md. Candidate role names include LocalPlayer, KeyboardInput, Input, Player, LivingEntity, Entity, AABB, VoxelShape, FlowingFluid, MobEffects, Enchantments, EnchantmentHelper, Blocks and ClientPacketListener. These are unverified search seeds only; no prior finding, range, hash or status is carried as evidence or coverage for this pair.

## Required source inventories

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1-input-sampling, S1-input-motion, S1-sprint-gates, S1-jump-gates, S1-flight-tick, S1-tick-order, S2-pose-selection, S2-dimensions, S2-player-state, S2-sprint-consumers, S2-flight-abilities, S3-travel-dispatch, S3-ground-acceleration, S3-air-acceleration, S3-velocity-cutoffs, S3-gravity-drag, S3-ground-jump, S3-sprint-jump, S3-climbing, S3-water, S3-lava, S3-gliding, S3-effects-branch; evidence=pending exact-source inventory and method ranges.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S1-input-sampling, S1-input-motion, S1-sprint-gates, S1-jump-gates, S1-flight-tick, S1-tick-order, S2-pose-selection, S2-dimensions, S2-player-state, S2-sprint-consumers, S2-flight-abilities, S3-travel-dispatch, S3-ground-acceleration, S3-air-acceleration, S3-velocity-cutoffs, S3-gravity-drag, S3-ground-jump, S3-sprint-jump, S3-climbing, S3-water, S3-lava, S3-gliding, S3-effects-branch, S4-box-movement, S4-step-candidates, S4-edge-probes, S4-grounding, S4-collision-query, S4-shapes, S4-callbacks, S4-fluid-contact, S7-velocity-writers, S7-position-writers, S7-riding, S7-closure; evidence=pending exact-source inventory and method ranges.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S4-box-movement, S4-step-candidates, S4-edge-probes, S4-grounding, S4-collision-query, S4-shapes, S4-callbacks, S4-fluid-contact, S5-landing-bounce, S5-friction-speed, S5-slowdown-contact, S5-climbables, S5-fluid-blocks, S5-pistons, S5-partial-shapes, S5-registrations, S5-modern-only; evidence=pending exact-source inventory and method ranges.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S5-landing-bounce, S5-friction-speed, S5-slowdown-contact, S5-climbables, S5-fluid-blocks, S5-pistons, S5-partial-shapes, S5-registrations, S5-modern-only, S6-attributes, S6-effects, S6-enchantments, S6-equipment, S6-resources, S6-server-boundary; evidence=pending exact-source inventory and method ranges.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S3-ground-acceleration, S3-air-acceleration, S3-gravity-drag, S3-ground-jump, S3-sprint-jump, S3-climbing, S3-water, S3-lava, S3-gliding, S3-effects-branch, S6-attributes, S6-effects, S6-enchantments, S6-equipment, S6-resources, S6-server-boundary; evidence=pending exact-source inventory and method ranges.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=S7-velocity-writers, S7-position-writers, S7-riding, S7-closure; evidence=pending exact-source inventory and method ranges.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement; direct vanilla-state reads in movement predicates remain in scope: status=pending; slice_ids=SCOPE-exclusions; evidence=pending exact-source inventory and method ranges.

## Coverage ledger

Each bounded behavior below is pending until both exact source sides, the entire relevant method body/callers, state producer/consumer links and dependency closure have been verified. A terminal disposition requires paired source ranges, exact hashes, reachability and a concrete rationale.

### Slice S1-input-sampling: 1 / local input sampling, input defaults and per-tick capture

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 1 / local input sampling, input defaults and per-tick capture; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: input class, options/key state producers; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S1-input-motion: 1 / yaw-to-motion, diagonal normalization, input scaling and move-relative dispatch

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 1 / yaw-to-motion, diagonal normalization, input scaling and move-relative dispatch; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: local input to movement vector; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S1-sprint-gates: 1 / sprint start/stop, timers, gates, item-use and blindness consumers only

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 1 / sprint start/stop, timers, gates, item-use and blindness consumers only; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: state consumers; exclude food/health emulation; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S1-jump-gates: 1 / jump request, cooldown, jump delay, auto-jump and sprint-jump scheduling

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 1 / jump request, cooldown, jump delay, auto-jump and sprint-jump scheduling; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: input, local-player tick, jump helper; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S1-flight-tick: 1 / flight toggle, flight input/speed and unstuck/riding gates

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 1 / flight toggle, flight input/speed and unstuck/riding gates; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: abilities, packet/state inputs; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S1-tick-order: 1 / local tick, superclass tick, travel call and previous/current flags

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 1 / local tick, superclass tick, travel call and previous/current flags; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: complete reachable call sequence; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S2-pose-selection: 2 / pose choice and swimming/crawling/standing/crouching transitions

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: 2 / pose choice and swimming/crawling/standing/crouching transitions; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: pose predicates and timers; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S2-dimensions: 2 / entity dimensions, eye height, resize ordering and collision checks

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: 2 / entity dimensions, eye height, resize ordering and collision checks; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: dimensions, pose, AABB/query timing; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S2-player-state: 2 / player movement state initialization/update/reset (excluding excluded health/food systems)

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: 2 / player movement state initialization/update/reset (excluding excluded health/food systems); pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: state writers and lifetime; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S2-sprint-consumers: 2 / hunger/blindness/item-use vanilla consumers of sprint gates

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: 2 / hunger/blindness/item-use vanilla consumers of sprint gates; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: inspect consumers only; no excluded mechanic emulation; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S2-flight-abilities: 2 / flight ability flags and direct movement consumers

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: 2 / flight ability flags and direct movement consumers; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: ability defaults, update writers; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S3-travel-dispatch: 3 / travel dispatch, branch selection and pre/post-travel sequence

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 3 / travel dispatch, branch selection and pre/post-travel sequence; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: full reachable method bodies and callers; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S3-ground-acceleration: 3 / ground acceleration and movement-speed inputs

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: 3 / ground acceleration and movement-speed inputs; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: attribute/helper aggregation and friction; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S3-air-acceleration: 3 / air acceleration, stored air speed and flying travel branch

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: 3 / air acceleration, stored air speed and flying travel branch; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: player-specific values and abilities; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S3-velocity-cutoffs: 3 / negligible-velocity thresholds, comparisons, casts and zeroing

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 3 / negligible-velocity thresholds, comparisons, casts and zeroing; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: exact literals and caller timing; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S3-gravity-drag: 3 / gravity, drag, levitation/slow-falling and post-travel updates

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: 3 / gravity, drag, levitation/slow-falling and post-travel updates; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: effects/attributes and exact operation order; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S3-ground-jump: 3 / ground jump power, jump boost and jump attribute path

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: 3 / ground jump power, jump boost and jump attribute path; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: attributes, effects, callbacks; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S3-sprint-jump: 3 / sprint jump impulse direction, arithmetic order and velocity writes

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: 3 / sprint jump impulse direction, arithmetic order and velocity writes; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: yaw, sprint and jump call chain; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S3-climbing: 3 / climbable branch, clamps, fall resets and wall contact

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: 3 / climbable branch, clamps, fall resets and wall contact; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: climbable state/callbacks; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S3-water: 3 / water travel, swimming, buoyancy, drag and fluid height

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: 3 / water travel, swimming, buoyancy, drag and fluid height; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: fluid state, flow vectors, attributes; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S3-lava: 3 / lava travel, buoyancy, drag and fluid height

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: 3 / lava travel, buoyancy, drag and fluid height; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: fluid state and exact thresholds; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S3-gliding: 3 / elytra/gliding travel, launch and item-use slowdown

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: 3 / elytra/gliding travel, launch and item-use slowdown; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: equipment, durability/use state, attributes; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S3-effects-branch: 3 / movement effect branches and operation order within travel

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: 3 / movement effect branches and operation order within travel; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S4-box-movement: 4 / bounding-box movement, position update and collision axis order

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 4 / bounding-box movement, position update and collision axis order; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: Entity, AABB, world collision calls; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S4-step-candidates: 4 / step-up candidates, comparison, tie-breaking and step height

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 4 / step-up candidates, comparison, tie-breaking and step height; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: full step helper/dependencies; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S4-edge-probes: 4 / edge sneaking/probes, support checks and query timing

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 4 / edge sneaking/probes, support checks and query timing; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: shape context, block support lookup; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S4-grounding: 4 / on-ground, horizontal/vertical collision flags and velocity response

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 4 / on-ground, horizontal/vertical collision flags and velocity response; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: axis result, support position, thresholds; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S4-collision-query: 4 / collision candidate iteration, shape selection and filtering

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 4 / collision candidate iteration, shape selection and filtering; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: world lookup, entity/context, iteration order; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S4-shapes: 4 / voxel shape operations, unions, clipping and AABB helpers

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 4 / voxel shape operations, unions, clipping and AABB helpers; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: all reachable helpers/overrides; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S4-callbacks: 4 / block contact, fall/landing, inside-block and movement callback order

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 4 / block contact, fall/landing, inside-block and movement callback order; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: callback producers and block overrides; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S4-fluid-contact: 4 / fluid contact/height/push and player fluid flags

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 4 / fluid contact/height/push and player fluid flags; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: fluid implementation, tag/resource dependencies; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S5-landing-bounce: 5 / slime/bed/other landing and bounce behavior

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: 5 / slime/bed/other landing and bounce behavior; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: block registrations, callback and state conditions; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S5-friction-speed: 5 / friction, speed and jump factors (ice, soul sand, honey, etc.)

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: 5 / friction, speed and jump factors (ice, soul sand, honey, etc.); pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: defaults, subclasses, registration values; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S5-slowdown-contact: 5 / webs, powder snow and other direct player contact slowdown

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: 5 / webs, powder snow and other direct player contact slowdown; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: block state/callback and shape; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S5-climbables: 5 / ladders, vines and other climbable registrations/overrides

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: 5 / ladders, vines and other climbable registrations/overrides; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: tags, neighboring/state predicates; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S5-fluid-blocks: 5 / water/lava/bubble column movement inputs and flow calculations

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: 5 / water/lava/bubble column movement inputs and flow calculations; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: fluids, blocks, resources and exact call order; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S5-pistons: 5 / moving piston player displacement and contact behavior

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: 5 / moving piston player displacement and contact behavior; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: callbacks, collision queries, external writer path; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S5-partial-shapes: 5 / fences, walls, stairs, slabs, trapdoors, doors, snow, farmland, paths

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: 5 / fences, walls, stairs, slabs, trapdoors, doors, snow, farmland, paths; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: enumerate relevant registries/overrides and neighbor conditions; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S5-registrations: 5 / registration/default properties and all additional movement-relevant block subclasses

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: 5 / registration/default properties and all additional movement-relevant block subclasses; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: complete source/resource inventory; additions/removals; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S5-modern-only: 5 / absent-from-A block/state registry checks and applicability

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: 5 / absent-from-A block/state registry checks and applicability; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: registry evidence; no historical invention; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S6-attributes: 6 / movement attributes, defaults, aggregation and modifier order

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: 6 / movement attributes, defaults, aggregation and modifier order; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: consumers, constructors, registration/resource data; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S6-effects: 6 / Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace, Blindness

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: 6 / Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace, Blindness; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: consumers to registration/application/removal chain; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S6-enchantments: 6 / Depth Strider, Soul Speed, Swift Sneak, Frost Walker, Riptide

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: 6 / Depth Strider, Soul Speed, Swift Sneak, Frost Walker, Riptide; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: formulas, level gates, tags, conditions, data; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S6-equipment: 6 / Elytra, movement item components/equipment slots and use slowdown

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: 6 / Elytra, movement item components/equipment slots and use slowdown; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: item predicates, effects, attributes, data; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S6-resources: 6 / jar tags, enchantment/effect/component definitions and referenced defaults

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: 6 / jar tags, enchantment/effect/component definitions and referenced defaults; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: exact entry/hash; identify synchronized/external inputs; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S6-server-boundary: 6 / server-synchronized movement attributes/data and client-only conclusion

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: 6 / server-synchronized movement attributes/data and client-only conclusion; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: establish provenance; mark unavailable inputs blocked; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S7-velocity-writers: 7 / incoming velocity, explosions, knockback/push and player velocity writes

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 7 / incoming velocity, explosions, knockback/push and player velocity writes; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: packet/event caller to state writer; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S7-position-writers: 7 / position corrections, piston displacement, launch items and direct setters

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 7 / position corrections, piston displacement, launch items and direct setters; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: packet/event caller to state writer; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S7-riding: 7 / mount/dismount transitions and player movement-state changes

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 7 / mount/dismount transitions and player movement-state changes; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: local player path only; other entity simulation excluded; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice S7-closure: 7 / enumerate changed helpers, writers, callbacks, registries and cross-slice interactions

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 7 / enumerate changed helpers, writers, callbacks, registries and cross-slice interactions; pending source navigation and full enclosing method review.
- A evidence: pending validated 1.20.4 Mojmap source; no method body inspected.
- B evidence: pending validated 1.20.6 Mojmap source; no method body inspected.
- State producers/writers -> consumers/readers: pending exact member-level inventory.
- Parent slices / dependencies / closure evidence: source dependency D-SOURCES; planned dependencies: revisit dependent callers and close every reachable queue item; pending source verification.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; no equivalence or difference claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

### Slice SCOPE-exclusions: excluded producers and direct vanilla-state reads

- Inventory ID(s): INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: confirm the campaign does not emulate health, regeneration, hunger, food, saturation, exhaustion, damage/combat systems or non-player movement; identify any direct vanilla-state reads in player movement predicates.
- A evidence: pending validated 1.20.4 Mojmap source and movement predicate ranges.
- B evidence: pending validated 1.20.6 Mojmap source and movement predicate ranges.
- State producers/writers -> consumers/readers: excluded producer systems remain untouched; movement consumers of vanilla state must be listed after source review.
- Parent slices / dependencies / closure evidence: all input, sprint-gate and travel slices; exact source reads pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): scope audit not started; no excluded-system emulation or equivalence claim.
- Finding IDs or checked absence/replacement path: none; source comparison not started.

## Dependency queue and blockers

- D-SOURCES: originating slice = all; exact 1.20.4 and 1.20.6 Mojmap validated-ready JSONs, source/artifact manifest files, movement-method diagnostics and successful logs are pending from source owner. Do not begin comparison until IDs, hashes, and relevant bodies verify. Next action: verify the shared build/movement-campaign-2026-10-07/ready publication when announced.
- D-CHECKER: workflow tooling only; check_completion.py currently matches every required per-slice '- Status:' field as a top-level status, so its exact-one-top-level-status gate rejects the new template. The report remains active and can be manually audited meanwhile. Owner: campaign workflow maintainer; resolve checker pattern to scope it to the run header.
- Open dependencies: D-SOURCES (source owner); D-CHECKER (campaign workflow maintainer; completion gate only).

## Finding index

- No source-confirmed findings yet. No previous movement catalogs or implementation changes were used as proof of coverage.

## Resume checkpoint

- Last completed slice: none; branch and updated workflow orientation complete.
- Next bounded slice and exact files/members/body ranges to open: await source owner; then enumerate local player input/tick classes and exact paired callers before beginning stage 1.
- Outstanding dependencies and owners: D-SOURCES; source owner.
- Current assumptions requiring verification: Mojmap is available/aligned on both exact endpoints; every relevant source body is intact; source manifests and diagnostic logs are preserved.

## Implementation reconciliation

- Reconciliation status: pending
- Repository revision inspected: none; implementation/code intentionally not opened during blind source discovery.
- Finding -> implementation disposition/evidence: deferred until blind discovery is frozen and parent explicitly assigns implementation reconciliation.
- Existing implementation without a frozen source finding: not inspected.
- Coverage gaps routed back to discovery slices: none yet; comparison is not started.

## Independent source audit

- Reviewer: not yet assigned; must differ from discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: none; awaiting completed source comparison.
- Concrete missed-slice routes (or none found): not assessed.
- Misses routed to slice/finding IDs and owners: none yet.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: pending 51; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0.
- Required inventory status and evidence: all seven pending; source tree not yet validated.
- Open dependencies: D-SOURCES (source owner).
- Unresolved gaps and limits: all bounded comparison slices pending; source provenance, exact method correspondence and diagnostics pending; no semantic equivalence claims made.
- Evidence/hash/correspondence audit: no source evidence cited yet; validate exact artifact and source hashes before citing.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from source completion). No tests, client/game/TAS/Gym/server/Docker launches performed.
