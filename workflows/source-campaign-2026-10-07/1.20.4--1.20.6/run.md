# Discovery: 1.20.4 to 1.20.6

- Status: active
- Scope: direct client-player movement; older A = 1.20.4; newer B = 1.20.6. Source-only discovery; no Minecraft wiki/MCPK sources or audit outputs, no mod implementation inspection, no runtime implementation or runtime validation.
- Repository revision and start date: base main = 002137b227676caea77f6832b9f4c8d0b6200bff; task branch = feat/source-discovery-movement-source-1-20-4-1-20-6; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: pending validated source-owner handoff; requested alignment is Mojmap / Mojmap.
- Source preparation owner / command / log / readiness marker: sole shared source owner; batch command was decompileMinecraft --versions=1.20.1,1.20.2,1.20.4,1.20.6 --mappings=mojmap --decompiler-heap=4G, with shared campaign staging/cache roots. Full successful log: ../../../build/movement-campaign-2026-10-07/staging/mojmap-1.20.1-to-1.20.6-3e08bcae66a145b296f7f85b12623999/gradle.full.log (SHA-256 e5acf99c0bf01e2a65818cea95da4873b5f8a3c610f795f887c7184216b160fe). This worker did not run decompileMinecraft or write shared artifacts.
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1; Java 25.0.3+9-LTS; Vineflower 1.12.0; TinyRemapper 0.14.1; Mapping-IO 0.9.1; ASM 9.10.1; Gson 2.14.0; decompiler heap 4G.
- Discovery author(s): Codex source worker in this task.
- Independent reviewer (must differ from discovery authors): not yet assigned.

## Artifact manifest

- Shared invocation/provenance: exact output and cache are the canonical campaign staging and artifacts roots. Batch resolved IDs: 1.20.1, 1.20.2, 1.20.4, 1.20.6; this run uses only exact 1.20.4 and 1.20.6 endpoints. Provenance records are at ../../../build/movement-campaign-2026-10-07/ready/1.20.4/mojmap.provenance.json (SHA-256 10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385) and ../../../build/movement-campaign-2026-10-07/ready/1.20.6/mojmap.provenance.json (SHA-256 ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8). Both record Mojmap and the same successful batch log.
- A: requested/resolved release and version metadata ID 1.20.4; namespace/CLI mode Mojmap. Source root ../../../build/movement-campaign-2026-10-07/ready/1.20.4/mojmap; 5,048 files / 24,323,941 source bytes; full source manifest ../../../build/movement-campaign-2026-10-07/ready/1.20.4/mojmap.sources.sha256 SHA-256 fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1 (all 5,048 listed source hashes verified). Artifact manifest ../../../build/movement-campaign-2026-10-07/ready/1.20.4/artifacts.sha256 SHA-256 ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948. Original client jar ../../../build/movement-campaign-2026-10-07/artifacts/1.20.4/client.jar SHA-256 9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838 (publisher SHA-1 fd19469fed4a4b4c15b2d5133985f0e3e7816a8a). Mojang official client mappings from exact 1.20.4 version metadata: ../../../build/movement-campaign-2026-10-07/artifacts/1.20.4/client_mappings.txt SHA-256 ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f (publisher SHA-1 be76ecc174ea25580bdc9bf335481a5192d9f3b7). Remapped client jar ../../../build/movement-campaign-2026-10-07/artifacts/1.20.4/client-mojmap.jar SHA-256 d20c183016505cceecd33d508973e7ca6f2ee8cbd860f316592f4a09a96bc339. Version metadata version.json SHA-256 48131d228cdaee5087bc9179ad5ed1ae39d0e52750609bdb2cf0596f20d3bdc6. Readiness JSON SHA-256 6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1. Movement diagnostics ../../../build/movement-campaign-2026-10-07/ready/1.20.4/movement-diagnostics.txt SHA-256 8909a0341c2141f476160c19a05cbf2ef39e28bee7357dd50e3693e4a3174879, review 22.
- B: requested/resolved release and version metadata ID 1.20.6; namespace/CLI mode Mojmap. Source root ../../../build/movement-campaign-2026-10-07/ready/1.20.6/mojmap; 5,329 files / 25,451,020 source bytes; full source manifest ../../../build/movement-campaign-2026-10-07/ready/1.20.6/mojmap.sources.sha256 SHA-256 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 (all 5,329 listed source hashes verified). Artifact manifest ../../../build/movement-campaign-2026-10-07/ready/1.20.6/artifacts.sha256 SHA-256 e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31. Original client jar ../../../build/movement-campaign-2026-10-07/artifacts/1.20.6/client.jar SHA-256 02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2 (publisher SHA-1 05b6f1c6b46a29d6ea82b4e0d42190e42402030f). Mojang official client mappings from exact 1.20.6 version metadata: ../../../build/movement-campaign-2026-10-07/artifacts/1.20.6/client_mappings.txt SHA-256 27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745 (publisher SHA-1 de46c8f33d7826eb83e8ef0e9f80dc1f08cb9498). Remapped client jar ../../../build/movement-campaign-2026-10-07/artifacts/1.20.6/client-mojmap.jar SHA-256 2add08d295773a0615ca595877f5fd055fa454d7c68fac56d6a4894a1e055284. Version metadata version.json SHA-256 78a5b3480319194842a7829ae81f4e04235199c384817cf13ad2a62eb70c12a5. Readiness JSON SHA-256 1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41. Movement diagnostics ../../../build/movement-campaign-2026-10-07/ready/1.20.6/movement-diagnostics.txt SHA-256 558c501d2956cfa2445e9a75512dcf6c8ff7fec453c7b2f439af171162cab498, review 23.
- Manifest checks: both marker IDs, version metadata IDs, namespace and source roots matched the exact requested pair; marker-cited source, artifact and diagnostics hashes matched; every listed source file hash matched its manifest; both exact client jar hashes appeared in the artifact manifests and matched the cached jars. Method diagnostics reported successful decompilation and the required movement classes/methods on both sides. The full log reports BUILD SUCCESSFUL and remapper access-repair warnings only for GUI/renderer classes, with no player movement body warning observed.

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

- D-SOURCES resolved: exact 1.20.4 and 1.20.6 Mojmap ready JSONs, manifests, client/mapping artifacts, method diagnostics and successful log verified as recorded in Artifact manifest. Source trees are now available read-only.
- D-CHECKER resolved by canonical workflow commit f7f65f8 (cherry-pick of fba28fa); active/partial reports are structurally checked without claiming source closure.

## Finding index

- No source-confirmed findings yet. No previous movement catalogs or implementation changes were used as proof of coverage.

## Resume checkpoint

- Last completed slice: source provenance and exact source-tree hash validation; no behavior slice completed.
- Next bounded slice and exact files/members/body ranges to open: stage 1 input sampling and local-player tick order in Input.java, KeyboardInput.java, LocalPlayer.java, Player.java and LivingEntity.java; establish pair-specific ranges and callers before disposition.
- Outstanding dependencies and owners: none presently; add source-method/data retrieval items as encountered.
- Current assumptions requiring verification: per-method movement body integrity and complete class/member correspondence must still be verified despite the successful source diagnostics.

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
- Required inventory status and evidence: all seven pending; exact source roots and hashes validated, method-level inventory remains.
- Open dependencies: none currently; source comparisons will add exact member/resource dependencies as discovered.
- Unresolved gaps and limits: all bounded comparison slices pending; pair-specific member correspondence and semantic comparison not yet complete; no equivalence claims made.
- Evidence/hash/correspondence audit: readiness, source and artifact manifests and all source hashes verified; exact source member hashes and ranges will be recorded per slice.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from source completion). No tests, client/game/TAS/Gym/server/Docker launches performed.
