# Discovery: 1.20.4 to 1.20.6

- Status: partial
- Scope: direct client-player movement, including player-side velocity/impulse/knockback response and resulting state writes; older A = 1.20.4; newer B = 1.20.6. Excludes health/food state production, attack/damage resolution, non-player movement and vehicle physics. Source-only discovery; no Minecraft wiki/MCPK sources or audit outputs, no mod implementation inspection, no runtime implementation or runtime validation.
- Repository revision and start date: base main = 002137b227676caea77f6832b9f4c8d0b6200bff; task branch = feat/source-discovery-movement-source-1-20-4-1-20-6; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: verified exact A and B readiness/provenance records both name Mojmap and match the requested release IDs; they cite the same successful batch log.
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

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1-input-sampling, S1-input-motion, S1-sprint-gates, S1-jump-gates, S1-flight-tick, S1-tick-order, S1-local-tick-call-order, S2-pose-selection, S2-dimensions, S2-player-state, S2-sprint-consumers, S2-flight-abilities, S3-travel-dispatch, S3-ground-acceleration, S3-air-acceleration, S3-velocity-cutoffs, S3-gravity-drag, S3-ground-jump, S3-sprint-jump, S3-climbing, S3-water, S3-lava, S3-gliding, S3-effects-branch; evidence=pending exact-source inventory and method ranges.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S1-input-sampling, S1-input-motion, S1-sprint-gates, S1-jump-gates, S1-flight-tick, S1-tick-order, S1-local-tick-call-order, S2-pose-selection, S2-dimensions, S2-player-state, S2-sprint-consumers, S2-flight-abilities, S3-travel-dispatch, S3-ground-acceleration, S3-air-acceleration, S3-velocity-cutoffs, S3-gravity-drag, S3-ground-jump, S3-sprint-jump, S3-climbing, S3-water, S3-lava, S3-gliding, S3-effects-branch, S4-box-movement, S4-step-candidates, S4-edge-probes, S4-grounding, S4-collision-query, S4-shapes, S4-callbacks, S4-fluid-contact, S5-powder-snow-contact-state, S5-current-block-state-consumers, S7-velocity-writers, S7-position-writers, S7-riding, S7-client-motion-payloads, S7-player-knockback-push, S7-closure; evidence=pending exact-source inventory and method ranges.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S4-box-movement, S4-step-candidates, S4-edge-probes, S4-grounding, S4-collision-query, S4-shapes, S4-callbacks, S4-fluid-contact, S5-landing-bounce, S5-friction-speed, S5-slowdown-contact, S5-climbables, S5-fluid-blocks, S5-pistons, S5-partial-shapes, S5-registrations, S5-modern-only, S5-powder-snow-contact-state; evidence=pending exact-source inventory and method ranges.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S5-landing-bounce, S5-friction-speed, S5-slowdown-contact, S5-climbables, S5-fluid-blocks, S5-pistons, S5-partial-shapes, S5-registrations, S5-modern-only, S5-powder-snow-contact-state, S5-current-block-state-consumers, S6-attributes, S6-effects, S6-enchantments, S6-equipment, S6-resources, S6-server-boundary; evidence=pending exact-source inventory and method ranges.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S3-ground-acceleration, S3-air-acceleration, S3-gravity-drag, S3-ground-jump, S3-sprint-jump, S3-climbing, S3-water, S3-lava, S3-gliding, S3-effects-branch, S6-attributes, S6-effects, S6-enchantments, S6-equipment, S6-resources, S6-server-boundary; evidence=pending exact-source inventory and method ranges.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=S7-velocity-writers, S7-position-writers, S7-riding, S7-client-motion-payloads, S7-player-knockback-push, S7-closure; evidence=pending exact-source inventory and method ranges.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement; direct vanilla-state reads in movement predicates remain in scope: status=pending; slice_ids=SCOPE-exclusions; evidence=pending exact-source inventory and method ranges.

## Coverage ledger

Each bounded behavior remains open until both exact source sides, the relevant method/caller boundary, state producer/consumer links and dependencies are verified. A terminal disposition requires paired source ranges, exact hashes, reachability and a concrete rationale; a narrow terminal slice does not close neighboring behavior.

### Slice S1-input-sampling: 1 / local input sampling, input defaults and per-tick capture

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: complete `KeyboardInput.tick(boolean,float)` sampling order and `Input` input defaults/representation/forward-impulse predicate; keyboard state values come from key mappings/options.
- A evidence: 1.20.4 `KeyboardInput.tick(boolean,float)` lines 21-36 and `Input.tick/getMoveVector/hasForwardImpulse` lines 15-24; full source hashes `KeyboardInput.java` `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0`, `Input.java` `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb`.
- B evidence: 1.20.6 `KeyboardInput.tick(boolean,float)` lines 21-36 and `Input.tick/getMoveVector/hasForwardImpulse` lines 15-24; full source hashes exactly match A: `KeyboardInput.java` `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0`, `Input.java` `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb`.
- State producers/writers -> consumers/readers: external key mappings/options state -> KeyboardInput.tick assigns directional/jump/shift impulses -> Input stores impulses and getMoveVector/hasForwardImpulse reads them. The two full source files are byte-identical across the exact pair.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; whole A/B KeyboardInput.java and Input.java hashes match, closing this bounded source-only input-sampling behavior. Key mapping values are external inputs; LocalPlayer consumption and later movement-vector transformation remain separate slices.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): The complete paired source files are identical, so field defaults, which key states are sampled, impulse calculation, assignment order, slowdown multiplication and Input readers are unchanged. This disposition does not cover key mapping configuration or LocalPlayer consumers.
- Finding IDs or checked absence/replacement path: checked no difference for these byte-identical input classes and bounded members; no finding.

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
- A evidence: 1.20.4 `LocalPlayer.aiStep()` lines 726-732, `LocalPlayer.java` SHA-256 `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`; grounded flight-toggle branch has no jump call.
- B evidence: 1.20.6 `LocalPlayer.aiStep()` lines 729-739, `LocalPlayer.java` SHA-256 `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`; calls `jumpFromGround()` after enabling flight when grounded. Finding MC1204-1206-01.
- State producers/writers -> consumers/readers: KeyboardInput captures jump state -> LocalPlayer.aiStep double-tap timer/toggle -> virtual Player.jumpFromGround -> LivingEntity.jumpFromGround; jump power/cutoff is a dependency. Flight input/speed and unstuck/riding gates remain open.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; partial flight-toggle call chain is closed in MC1204-1206-01. Ability update writers/defaults, the rest of LocalPlayer.aiStep and jump-power branch remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Confirmed B-only ground-jump call on the mayfly double-tap activation path; bounded finding is source-confirmed, but flight input/speed and unstuck/riding behaviors are not yet inventoried.
- Finding IDs or checked absence/replacement path: MC1204-1206-01; dependent jump-power threshold MC1204-1206-02.

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

### Slice S1-local-tick-call-order: 1 / LocalPlayer.tick direct call and state-update order

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: complete `LocalPlayer.tick()` method body; compare only its direct call order, guards and local movement-state snapshots/updates.
- A evidence: 1.20.4 `LocalPlayer.tick()` lines 188-207, `LocalPlayer.java` SHA-256 `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`.
- B evidence: 1.20.6 `LocalPlayer.tick()` lines 190-209, `LocalPlayer.java` SHA-256 `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`.
- State producers/writers -> consumers/readers: complete local-player tick body -> the same direct call sequence and local state reads/writes in both versions. Behavior inside called superclass/player methods is tracked separately in their stage slices.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; this exact method body is textually identical after paired extraction. S1-tick-order and downstream superclass/travel slices remain open for called-method behavior and full call-chain closure.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the full paired method body is identical at the cited ranges, including guard and direct update order. This closes only the direct LocalPlayer.tick wrapper sequence and makes no claim that called methods are behaviorally identical.
- Finding IDs or checked absence/replacement path: checked no difference for the complete LocalPlayer.tick body; no finding.

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
- A evidence: 1.20.4 `LivingEntity.getScale()` lines 556-558 and `getDimensions(Pose)` lines 3161-3163, `LivingEntity.java` SHA-256 `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`; player uses its pose dimensions.
- B evidence: 1.20.6 `LivingEntity.getScale()` lines 578-581, `getDimensions(Pose)` lines 3240-3245 and tick refresh lines 2456-2461, `LivingEntity.java` SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; see MC1204-1206-06.
- State producers/writers -> consumers/readers: B synchronized SCALE attribute -> LivingEntity.tick appliedScale check -> Entity.refreshDimensions updates dimensions, eye height and bounding box -> pose-fit/collision consumers. Exact server value and update timing remain external.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; MC1204-1206-06 closes the attribute-to-dimension path at tick tail. Pose transitions, scale-dependent consumers/collision and pre-refresh ordering remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Default player scale stays 1.0; B adds a syncable non-default scale input that changes player dimensions and refreshes active dimensions at tick tail. Full dimensions/collision slice remains open.
- Finding IDs or checked absence/replacement path: MC1204-1206-06.

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
- A evidence: 1.20.4 `LivingEntity.jumpFromGround()` lines 2008-2017, `LivingEntity.java` SHA-256 `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`; unconditional vertical velocity write and impulse flag.
- B evidence: 1.20.6 `LivingEntity.jumpFromGround()` lines 2069-2082, `LivingEntity.java` SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; guarded by `! (power <= 1.0E-5F)`. MC1204-1206-02.
- State producers/writers -> consumers/readers: jump callers -> jump-power calculation -> guarded velocity write and hasImpulse state -> later travel. B jump power additionally reads JUMP_STRENGTH (MC1204-1206-03); broader velocity cutoff inventory remains open.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; jump-specific threshold is source-confirmed in MC1204-1206-02. Other negligible-velocity thresholds/casts/zeroing in travel have not been closed.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The ground-jump cutoff is confirmed: A always writes and marks impulse; B skips both at power <= 1.0E-5F. Slice includes other travel thresholds not yet compared.
- Finding IDs or checked absence/replacement path: MC1204-1206-02.

### Slice S3-gravity-drag: 3 / gravity, drag, levitation/slow-falling and post-travel updates

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: 3 / gravity, drag, levitation/slow-falling and post-travel updates; pending source navigation and full enclosing method review.
- A evidence: 1.20.4 `LivingEntity.travel(Vec3)` lines 2035-2166, SHA-256 `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`; base gravity 0.08 and descending Slow Falling replaces it with 0.01.
- B evidence: 1.20.6 `LivingEntity.travel(Vec3)` lines 2104-2233 and `getDefaultGravity()` lines 2099-2102, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; reads attribute gravity and applies `Math.min(gravity, 0.01)`. MC1204-1206-04.
- State producers/writers -> consumers/readers: B synced GRAVITY attribute -> Entity.getGravity/ LivingEntity.getDefaultGravity -> land/fluid travel gravity and fluid-falling gravity gate -> velocity updates. Effect state and chunk-availability branch also affect this method.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; default and attribute/Slow Falling differences recorded in MC1204-1206-04. Need close every branch, gravity-dependent helper and post-travel drag/update before terminal disposition.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Default gravity remains 0.08. Non-default synced gravity is newly consumed; B Slow Falling caps gravity at 0.01 while A replaces it. Full gravity/drag/effect branch comparison remains open.
- Finding IDs or checked absence/replacement path: MC1204-1206-04.

### Slice S3-ground-jump: 3 / ground jump power, jump boost and jump attribute path

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: 3 / ground jump power, jump boost and jump attribute path; pending source navigation and full enclosing method review.
- A evidence: 1.20.4 `LivingEntity.getJumpPower()` lines 2000-2006 and `jumpFromGround()` lines 2008-2017, SHA-256 `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`.
- B evidence: 1.20.6 `LivingEntity.getJumpPower()` lines 2057-2067 and `jumpFromGround()` lines 2069-2082, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; JUMP_STRENGTH path and cutoff findings MC1204-1206-02/03.
- State producers/writers -> consumers/readers: jump input/ground or fluid gate -> block jump factor, Jump Boost and (B) JUMP_STRENGTH -> jump-power guard -> vertical velocity/hasImpulse. Food/stat side effects excluded.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; jump formula, cutoff and call path findings MC1204-1206-01 through -03. Block jump-factor producers and complete effect/attribute application chain remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Confirmed B default jump-strength value preserves the old base multiplier; non-default attribute input and changed threshold are source-confirmed. Block-factor/effect dependencies keep the full slice open.
- Finding IDs or checked absence/replacement path: MC1204-1206-01, MC1204-1206-02, MC1204-1206-03.

### Slice S3-sprint-jump: 3 / sprint jump impulse direction, arithmetic order and velocity writes

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: 3 / sprint jump impulse direction, arithmetic order and velocity writes; pending source navigation and full enclosing method review.
- A evidence: 1.20.4 `LivingEntity.jumpFromGround()` lines 2008-2017, SHA-256 `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`; sprint uses `Mth.sin/cos(yaw) * 0.2F`.
- B evidence: 1.20.6 `LivingEntity.jumpFromGround()` lines 2069-2082, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; sprint uses float trig result times double `0.2` within jump guard. MC1204-1206-02.
- State producers/writers -> consumers/readers: sprint state and yaw -> sine/cosine -> sprint-jump delta -> vector addition and setDeltaMovement; ground jump power/cutoff gates this path.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; arithmetic and velocity write difference documented in MC1204-1206-02. Sprint state producer, yaw update path and other sprint-gate consumers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Under a sprinting ground jump above B cutoff, operation precision differs because B widens the float trig value for multiplication by a double literal. Other sprint scheduling/gates remain unreviewed.
- Finding IDs or checked absence/replacement path: MC1204-1206-02.

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
- A evidence: 1.20.4 `LivingEntity.maxUpStep()` lines 3423-3426 reads inherited 0.6F step field; `Entity.move()` lines 596-725 uses virtual maxUpStep for step-candidate checks.
- B evidence: 1.20.6 `LivingEntity.maxUpStep()` lines 3490-3493 reads STEP_HEIGHT; `Entity.move()` lines 597-726 has step-candidate checks. Source hashes Entity A/B: `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` / `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`. MC1204-1206-05.
- State producers/writers -> consumers/readers: B synced STEP_HEIGHT attribute -> LivingEntity.maxUpStep -> unchanged Entity.move candidate heights; controlling-player passenger still floors result at 1.0F.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; input-height finding MC1204-1206-05. Collision candidate/shape/tie-break path remains open for this broad step-candidate slice.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Default 0.6 matches A; B non-default STEP_HEIGHT changes candidate height. The remainder of candidate collision behavior has not been closed.
- Finding IDs or checked absence/replacement path: MC1204-1206-05.

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
- A evidence: 1.20.4 `Attributes.java` hash `287dcd477ccca2aebd24ad28db7b0e7a1e9dc9bbeb272d330cd15504b8669ff7`; no generic GRAVITY/SCALE/STEP_HEIGHT/JUMP_STRENGTH player inputs; jump strength entry is horse-specific.
- B evidence: 1.20.6 `Attributes.java` hash `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e`; generic gravity/jump-strength/scale/step-height are syncable and added by LivingEntity.createLivingAttributes(). Findings MC1204-1206-03 through -06.
- State producers/writers -> consumers/readers: Player.createAttributes inherits living attributes -> externally supplied/synchronized attribute values -> jump, travel gravity, dimensions and step-limit consumers. Only these four attribute paths have been reviewed.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; four movement attribute deltas are recorded. Remaining movement attributes, modifier order, registration/default-source coverage and resource/sync closure remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Four source-confirmed player movement attribute paths were found; this broad attributes inventory is still incomplete, so no terminal disposition is claimed.
- Finding IDs or checked absence/replacement path: MC1204-1206-03, MC1204-1206-04, MC1204-1206-05, MC1204-1206-06.

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
- A evidence: 1.20.4 `Attributes.java` has only horse-specific jump strength and no generic gravity/scale/step-height player attributes; SHA-256 `287dcd477ccca2aebd24ad28db7b0e7a1e9dc9bbeb272d330cd15504b8669ff7`.
- B evidence: 1.20.6 `Attributes.java` lines 42-47, 66-74 defines syncable GRAVITY/JUMP_STRENGTH/SCALE/STEP_HEIGHT; B LivingEntity attribute builder includes them at lines 292-305. SHA-256 `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e`.
- State producers/writers -> consumers/readers: Attribute builder supplies player instances; synchronization/source of non-default values remains external; client movement consumes current values at jump/travel/dimension/step paths.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; bounded attribute additions are evidenced by MC1204-1206-03 through -06. Client attribute packet handling and exact external value provenance have not been audited.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The attributes are marked syncable and flow into inherited player movement calculations, but the client update handler and complete server boundary remain unreviewed.
- Finding IDs or checked absence/replacement path: MC1204-1206-03, MC1204-1206-04, MC1204-1206-05, MC1204-1206-06.

### Slice S7-velocity-writers: 7 / remaining direct velocity writers and cross-caller closure

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: 7 / incoming velocity, explosions, knockback/push and player velocity writes; pending source navigation and full enclosing method review.
- A evidence: 1.20.4 paired handlers/writers checked: `ClientPacketListener.handleSetEntityMotion` 494-500, `handleMovePlayer` 597-668, `handleExplosion` 1132-1149; `LivingEntity.knockback` 1442-1450; `Entity.push` 1398-1432. File hashes recorded in S7-client-motion-payloads and S7-player-knockback-push.
- B evidence: 1.20.6 corresponding handlers/writers checked: `ClientPacketListener` 515-521, 618-689, 1167-1184; `LivingEntity.knockback` 1475-1483; `Entity.push` 1414-1448. File hashes recorded in S7-client-motion-payloads and S7-player-knockback-push.
- State producers/writers -> consumers/readers: Network payloads or direct knockback/push arguments -> player-side deltaMovement/position state writers. Packet payload and event arguments are external; only receiver-player movement is in scope. Piston, launch-item and remaining writer paths are not yet enumerated.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; packet state application and direct knockback/push response have terminal bounded rows S7-client-motion-payloads and S7-player-knockback-push. Remaining velocity writers/callers and their direct player-motion consequences remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The cited network and player-side knockback/push methods match at both endpoints, but this broader writer inventory is incomplete; direct piston/launch-item and other player-state paths remain to be checked.
- Finding IDs or checked absence/replacement path: S7-client-motion-payloads and S7-player-knockback-push are compared-no-difference; no movement finding from those bounded methods.

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

### Slice S7-riding: 7 / player mount/dismount state changes; vehicle physics excluded

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

### Slice S5-powder-snow-contact-state: 5 / powder-snow current-block query in contact slowdown and movement boost

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: powder-snow contact predicate in `PowderSnowBlock.entityInside` and powder-snow vertical movement boost in `LivingEntity.handleRelativeFrictionAndCalculateMovement`; compare only the cached current `blockPosition()` state path.
- A evidence: 1.20.4 `LivingEntity.handleRelativeFrictionAndCalculateMovement(Vec3,float)` lines 2202-2213 reads `getFeetBlockState`; `Entity.getFeetBlockState()` lines 3154-3160 reads level state at `blockPosition`; `PowderSnowBlock.entityInside` lines 64-69 uses the same getter for its living-entity guard. File hashes: `LivingEntity.java` `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`, `Entity.java` `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9`, `PowderSnowBlock.java` `302e784a106a37b00aafa0ff7ac5d638c78ccb61f34c2efac4d952f6a284a55c`.
- B evidence: 1.20.6 corresponding `LivingEntity.handleRelativeFrictionAndCalculateMovement(Vec3,float)` lines 2269-2280 reads `getInBlockState`; `Entity.getInBlockState()` lines 3169-3175 reads level state at `blockPosition`; `PowderSnowBlock.entityInside` lines 64-69 uses that same getter. File hashes: `LivingEntity.java` `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`, `Entity.java` `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`, `PowderSnowBlock.java` `7a6d6ad2e6d6d719344de4bb4f4e4973387aa88e61757988afea25b46c176ad3`.
- State producers/writers -> consumers/readers: entity `blockPosition()` and level block-state lookup -> lazy A `feetBlockState` / B `inBlockState` cache -> powder-snow entity-inside guard and living friction helper. Both clear their cache in `Entity.baseTick()` and when block position changes in `Entity.setPosRaw`/movement position update (A lines 415-420 and 3238-3244; B lines 417-422 and 3253-3259).
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; direct powder-snow current-block lookup, invalidation and these two consumers were compared. Other powder-snow contact callbacks and slowdown formulas, snow collision shapes, tags and neighboring-state behavior remain in S5-slowdown-contact/S5-partial-shapes.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for an entity in the same block position, both cache getters obtain the same current level `BlockState`; both cache lifetimes are reset at tick base and on block-position change. Both callbacks retain the same `instanceof LivingEntity` disjunction and `makeStuckInBlock` call, while the living friction helper retains the same `(horizontalCollision || jumping) && (onClimbable || powder-snow && canEntityWalkOnPowderSnow)` guard and writes Y `0.2`. This closes only the getter rename/cached-state path for these two powder-snow consumers; it makes no claim about other powder-snow mechanics.
- Finding IDs or checked absence/replacement path: checked no difference for this bounded cached current-block-state path; no finding.

### Slice S5-current-block-state-consumers: 5 / cached current-block lookup in climbable, scaffolding and bubble-column movement predicates

- Inventory ID(s): INV-STATE, INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.onClimbable`, `LivingEntity.handleOnClimbable` scaffolding descent gate and `Entity.isInBubbleColumn` current-position block lookup; compare only the current `blockPosition()` state source/read.
- A evidence: 1.20.4 `LivingEntity.onClimbable()` lines 1508-1524 reads `getFeetBlockState`; `LivingEntity.handleOnClimbable(Vec3)` lines 2230-2245 reads it for scaffolding; `Entity.isInBubbleColumn()` lines 1153-1155 directly reads level state at `blockPosition`. Source hashes: `LivingEntity.java` `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`, `Entity.java` `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9`.
- B evidence: 1.20.6 `LivingEntity.onClimbable()` lines 1541-1557 and `LivingEntity.handleOnClimbable(Vec3)` lines 2297-2312 use `getInBlockState`; `Entity.isInBubbleColumn()` lines 1169-1171 uses that getter. Source hashes: `LivingEntity.java` `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`, `Entity.java` `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`.
- State producers/writers -> consumers/readers: `blockPosition()` -> direct level lookup in A or lazy cached lookup in B -> climbable state/tag and trapdoor test, scaffolding descent condition, and bubble-column membership. Cached A/B values are cleared in `Entity.baseTick()` and on block-position change (ranges recorded in S5-powder-snow-contact-state).
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; these query paths and method guards are compared. Climbable tag contents, trapdoor ladder-use conditions, scaffolding registration and bubble-column movement inputs remain open in S5-climbables/S5-fluid-blocks/S5-registrations.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): on each inspected path both versions test the state at the entity's current integer `blockPosition()`; B replaces direct/cached current-state access with `getInBlockState`, whose lazy lookup reads the same level position. `onClimbable` retains its spectator guard, climbable-tag test, trapdoor fallback and `lastClimbablePos` write. `handleOnClimbable` retains its velocity clamps and scaffolding/player descent gate. This closes only these current-block query consumers; producer tags/registrations and full climb/fluid mechanics are not dispositioned.
- Finding IDs or checked absence/replacement path: checked no difference for these bounded current-block query paths; no finding.

### Slice S7-client-motion-payloads: 7 / packet-supplied player velocity, position correction and explosion impulse

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: player-facing application of set-entity-motion, player-position correction and explosion knockback payloads through the local player velocity/position writers; packet values and explosion calculation are external inputs.
- A evidence: 1.20.4 `ClientPacketListener.handleSetEntityMotion` lines 494-500, `handleMovePlayer` lines 597-668 and `handleExplosion` lines 1132-1149; `Entity.lerpMotion` lines 2074-2076, `setDeltaMovement` lines 3170-3180 and `addDeltaMovement` lines 3174-3176. `ClientPacketListener.java` SHA-256 `ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f`; `Entity.java` `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9`.
- B evidence: 1.20.6 corresponding `ClientPacketListener` handlers lines 515-521, 618-689 and 1167-1184; `Entity.lerpMotion` lines 2096-2098, `setDeltaMovement` lines 3185-3195 and `addDeltaMovement` lines 3189-3191. `ClientPacketListener.java` SHA-256 `e121e998ec25211aaecf91fffd0ea699cebd47eddea9134efd3f2ffbef8eb7cd`; `Entity.java` `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`.
- State producers/writers -> consumers/readers: externally supplied packet values -> handler guard/local-player or entity lookup -> lerpMotion/setDeltaMovement/addDeltaMovement -> player delta movement and corrected position. Relative-axis handling preserves current velocity for relative components and sets absolute-axis components from the packet.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; these three player-facing payload handlers and their state-writer path are byte-for-byte method-equivalent. The server packet values and explosion cause/effect calculation are external or excluded. Other direct velocity/position writers remain in S7-velocity-writers/S7-position-writers.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): all three paired handlers and the cited writer methods have identical bodies at the cited ranges. Given the same packet and same entity/player lookup result, both versions apply the same decoded velocity, relative/absolute position axes and explosion knockback addition in the same order. This bounded result covers client payload application only; it does not cover the source or correctness of server-provided values.
- Finding IDs or checked absence/replacement path: checked no difference for these packet-to-player state writes; no finding.

### Slice S7-player-knockback-push: 7 / direct player knockback and collision-push response

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: player receiver path for `LivingEntity.knockback` and `LivingEntity.push(Entity)` through inherited `Entity.push` response; event magnitude/direction and the other entity's independent movement are outside this bounded comparison.
- A evidence: 1.20.4 `LivingEntity.knockback(double,double,double)` lines 1442-1450 and `LivingEntity.push(Entity)` lines 1975-1979; inherited `Entity.push(Entity)` lines 1398-1427 and `push(double,double,double)` lines 1429-1432. `LivingEntity.java` SHA-256 `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`; `Entity.java` `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9`.
- B evidence: 1.20.6 corresponding `LivingEntity.knockback` lines 1475-1483, `LivingEntity.push` lines 2032-2036, `Entity.push(Entity)` lines 1414-1443 and `push(double,double,double)` lines 1445-1448. `LivingEntity.java` SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; `Entity.java` `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`.
- State producers/writers -> consumers/readers: direct knockback arguments plus current knockback resistance, velocity, and on-ground state -> identical LivingEntity formula/guard -> hasImpulse and delta-velocity write. For collisions, a non-sleeping player delegates push to Entity.push; player receiver movement is in scope, movement of the other entity is excluded.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; the direct player response methods and inherited writer bodies are identical. Event-source call sites are intentionally not traced into attack/damage computation. Remaining velocity, piston, launch-item and position writers remain open in S7-velocity-writers/S7-position-writers.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): knockback retains the same resistance scaling, `power <= 0` guard, impulse flag, normalized horizontal vector, X/Z halving/subtraction and grounded-only capped Y calculation. Player push retains the sleeping guard and inherited Entity push resolves through the same receiver and vector writer bodies. This comparison accepts arbitrary equal runtime arguments and makes no claim about combat or other-entity results.
- Finding IDs or checked absence/replacement path: checked no difference for the bounded player-side knockback/push response; no finding.

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

- MC1204-1206-01 grounded flight activation additionally invokes the jump routine. MC1204-1206-02 ground-jump cutoff and sprint impulse arithmetic differ. MC1204-1206-03 generic jump-strength attribute; MC1204-1206-04 gravity attribute and Slow Falling cap; MC1204-1206-05 step-height attribute; MC1204-1206-06 scale-driven player dimensions. Findings are source-only and do not claim observed trajectories. No implementation reconciliation or independent audit has been performed.

## Resume checkpoint

- Last completed slice: source provenance and exact source-tree hash validation; six bounded behaviors are closed as compared-no-difference, and ten broader behavior slices contain partial source-confirmed evidence but remain in-progress.
- Task branch / checkpoint: `feat/source-discovery-movement-source-1-20-4-1-20-6`, prior committed tip `23603b4`; `main` is `002137b` and is already an ancestor of the task branch.
- Next bounded slice and exact files/members/body ranges to open: start S1-input-motion and sprint/jump scheduling by comparing `KeyboardInput.tick`, `Input.getMoveVector`, `LocalPlayer.aiStep` and its pre-travel calls, then trace into `Player.travel(Vec3)` and `LivingEntity.travel(Vec3)`; close the remaining flight input/speed gates after MC1204-1206-01.

Resume with these read-only source navigation commands from the repository root (use outputs only as navigation, then record exact method ranges/hashes in the ledger):

```powershell
$A = 'D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.20.4\mojmap'
$B = 'D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.20.6\mojmap'
rg -n -C 6 'calculateImpulse|public void tick|public Vec2 getMoveVector|hasForwardImpulse' "$A/net/minecraft/client/player/KeyboardInput.java" "$B/net/minecraft/client/player/KeyboardInput.java" "$A/net/minecraft/client/player/Input.java" "$B/net/minecraft/client/player/Input.java"
rg -n -C 8 'public void aiStep|jumpTriggerTime|isAlwaysFlying|isSprinting|input\.jumping|public void travel\(Vec3\)' "$A/net/minecraft/client/player/LocalPlayer.java" "$B/net/minecraft/client/player/LocalPlayer.java" "$A/net/minecraft/world/entity/player/Player.java" "$B/net/minecraft/world/entity/player/Player.java" "$A/net/minecraft/world/entity/LivingEntity.java" "$B/net/minecraft/world/entity/LivingEntity.java"
```
- Outstanding dependencies and owners: 40 pending slices and 10 in-progress slices require exact A/B methods, writers/readers, callers, resources and dependency closure; no external source-preparation blocker. A different blind source reviewer is still unassigned for both individual finding snapshots and the eventual full-pair audit.
- Current assumptions requiring verification: per-method movement body integrity and complete class/member correspondence must still be verified despite the successful source diagnostics.

## Finding snapshots (not pair freeze)

- MC1204-1206-01 snapshot `1985fa829eb597acbc23685935c5f6929c078f78` was accepted by blind source review commit `581b5bd2cb44981c4889eaf05ea739134e332b87` on 2026-10-08. The reviewed path is `findings/MC1204-1206-01.md`, Git blob `28b3534a690e497a570f2936a2b08e1ef2a803ee`, raw SHA-256 `eb15d0f72764cf783cb0ac260d28f8a31827f3bb407015dd66f1601ca3ad0fda`; an exact byte copy is retained at `findings/snapshots/MC1204-1206-01-1985fa829.md`.
- The accepted snapshot's A/B ready source-manifest hashes are `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` / `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`; artifact-manifest hashes are `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` / `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`.
- The current mutable `findings/MC1204-1206-01.md` at this integration checkpoint differs from that accepted blob (current raw SHA-256 `ce59cbc33f0c280fa2da45d3c2548a5dab2cd0c1b53cbd857dff31d58e5c1bd8`). It changes the caller-chain and dependency wording. Review `581b` accepts only the exact immutable `1985fa8` snapshot; it does not accept the current mutable copy. The no-code reconciliation in this integration uses only the accepted snapshot. Any implementation handoff relying on the revised copy requires a fresh snapshot review.
- This integration records MC1204-1206-01 only. Other findings' decisions remain in the independent review report `independent-review-1204-1206-and-f14-2026-10-08.md` (commit `581b5bd2cb44981c4889eaf05ea739134e332b87`) and are not reconciled by this batch. Pair status remains active/partial with 40 pending and 10 in-progress slices; `pair complete: no`.

## Implementation reconciliation

- Reconciliation status: pending
- Repository revision inspected: none; implementation/code intentionally not opened during blind source discovery.
- Finding -> implementation disposition/evidence: deferred until blind discovery is frozen and parent explicitly assigns implementation reconciliation.
- Existing implementation without a frozen source finding: not inspected.
- Coverage gaps routed back to discovery slices: no implementation-reconciliation gaps have been routed; source comparison continues in the pending and in-progress slices.

## Independent source audit

- Reviewer: not yet assigned; must differ from discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: none; awaiting completed source comparison.
- Concrete missed-slice routes (or none found): not assessed.
- Misses routed to slice/finding IDs and owners: none yet.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: pending 40; in-progress 10; compared-no-difference 6; findings 0; not-applicable 0; blocked 0. The in-progress slices contain bounded source-confirmed findings but remain open for broader dependency closure.
- Slice IDs by status: pending (40): S1-input-motion, S1-sprint-gates, S1-jump-gates, S1-tick-order, S2-pose-selection, S2-player-state, S2-sprint-consumers, S2-flight-abilities, S3-travel-dispatch, S3-ground-acceleration, S3-air-acceleration, S3-climbing, S3-water, S3-lava, S3-gliding, S3-effects-branch, S4-box-movement, S4-edge-probes, S4-grounding, S4-collision-query, S4-shapes, S4-callbacks, S4-fluid-contact, S5-landing-bounce, S5-friction-speed, S5-slowdown-contact, S5-climbables, S5-fluid-blocks, S5-pistons, S5-partial-shapes, S5-registrations, S5-modern-only, S6-effects, S6-enchantments, S6-equipment, S6-resources, S7-position-writers, S7-riding, S7-closure, SCOPE-exclusions; in-progress (10): S1-flight-tick, S2-dimensions, S3-velocity-cutoffs, S3-gravity-drag, S3-ground-jump, S3-sprint-jump, S4-step-candidates, S6-attributes, S6-server-boundary, S7-velocity-writers; compared-no-difference (6): S1-input-sampling, S1-local-tick-call-order, S5-powder-snow-contact-state, S5-current-block-state-consumers, S7-client-motion-payloads, S7-player-knockback-push.
- Required inventory status and evidence: all seven pending; exact source roots and hashes validated, method-level inventory remains.
- Open dependencies: no unresolved source-preparation blocker; dependencies remain within the 40 pending and 10 in-progress slices, including full method/caller closure, resource-backed block data and external attribute provenance.
- Unresolved gaps and limits: 40 bounded slices remain pending and 10 remain in-progress; the six compared-no-difference rows cover input sampling, LocalPlayer.tick wrapper order, bounded current-block query consumers, packet-to-player motion application, and direct player knockback/push response. Pair-wide member correspondence, semantic comparison and dependency closure are incomplete; no campaign-level equivalence or completion claim is made.
- Evidence/hash/correspondence audit: readiness, source and artifact manifests and all source hashes verified; pair-specific method ranges and hashes are recorded for six findings and six bounded no-difference slices; remaining slices are not yet audited.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from source completion). No tests, client/game/TAS/Gym/server/Docker launches performed.
