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
- Exact behavior boundary and enclosing guards/order checked: local input impulses copied to LivingEntity's xxa/zza movement vector, the regular travel call, `handleRelativeFrictionAndCalculateMovement`'s relative-move dispatch, and `Entity.moveRelative/getInputVector` normalization, speed scaling, yaw rotation and velocity addition. Branch-specific acceleration and travel physics are separate S3 slices.
- A evidence: 1.20.4 `LocalPlayer.aiStep()` copies `input.leftImpulse`/`forwardImpulse` at lines 613-614; `LivingEntity.aiStep()` damps `xxa`/`zza` by `0.98F`, forms the movement vector at line 2595 and dispatches `travel` at lines 2601-2603. `Player.travel()` forwards to `super.travel()` on both branches, lines 1451-1469. The living ground movement helper dispatches `moveRelative` at line 2203. `Entity.moveRelative/getInputVector` lines 1295-1308. File hashes: LocalPlayer `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`; Player `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd`; LivingEntity `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`; Entity `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9`.
- B evidence: 1.20.6 corresponding input assignments at lines 615-616; LivingEntity damps `xxa`/`zza` by `0.98F`, forms the movement vector at line 2675 and calls travel at lines 2681-2683. `Player.travel()` forwards to `super.travel()` on both branches, lines 1473-1491. Relative-move dispatch is at line 2270. `Entity.moveRelative/getInputVector` lines 1311-1324. File hashes: LocalPlayer `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`; Player `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`; LivingEntity `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; Entity `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`.
- State producers/writers -> consumers/readers: sampled input impulses -> LocalPlayer xxa/zza -> LivingEntity movement vector -> regular player travel and friction helper -> Entity relative-motion helper -> delta movement. The paired normalization, scalar application, trigonometric yaw transform, and vector addition bodies are textually identical. Only this vector-transform subpath is closed; branch-specific input scalar producers, jump/flight transforms, and travel equations remain in their own open slices.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; `S1-input-sampling` is compared-no-difference and supplies the input reader; the exact player input-to-relative-motion call chain is source-verified. No claim is made about the surrounding travel branch outcomes.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): In the regular non-riding local-player movement path, both versions copy the same directional impulses and route them through the same movement-vector construction, relative-move dispatch and `getInputVector` body. That body returns zero below the same squared-length threshold, normalizes only above unit squared length, applies the same scalar, uses the same sine/cosine yaw formula and adds the same vector to delta movement. This closes that bounded transform only.
- Finding IDs or checked absence/replacement path: checked no difference for the paired input-to-relative-motion transform; no finding.

### Slice S1-sprint-gates: 1 / sprint start/stop, timers, gates, item-use and blindness consumers only

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: paired local sprint timer, start/stop block in `LocalPlayer.aiStep()`; item-use input slowdown and reset; `canStartSprinting`, forward-impulse and food-level predicates; shared sprint flag and sprint movement-speed modifier writer; client command and server handling for sprint state.
- A evidence: 1.20.4 `LocalPlayer.aiStep()` lines 646-715 contains sprint timer decrement/reset, item-use input scaling, double-tap timer, key start and stop gates. `canStartSprinting()` and helpers are lines 1013-1034. `Entity.isSprinting/setSprinting` lines 2155-2162 reads/writes shared flag 3. `LivingEntity.setSprinting()` lines 1951-1958 updates movement speed with modifier UUID `662A6B8D-DA3E-4C1C-8813-96EA6097278D`, amount `0.3F`, operation `MULTIPLY_TOTAL`. `LocalPlayer.sendIsSprintingIfNeeded()` lines 271-279 sends sprint state; `ServerGamePacketListenerImpl.handlePlayerCommand()` lines 1331-1373 applies it. Hashes: Entity `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9`; LocalPlayer `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`; LivingEntity `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`; `ServerGamePacketListenerImpl` `7e2026bc5dc571031a52fa6be38a71cee20e7ccfbb2e77aec48ea4bc7af11527`; `AttributeModifier` `f6a16d77b7620e72756bc079ba3aade4fc3da6a3763c1af8fe21320f89551100`; `AttributeInstance` `f78cd38b3fe74f98950ecc0fe4ae420f99299dc5bb43360e4e6b3c76b3509672`.
- B evidence: 1.20.6 corresponding `LocalPlayer.aiStep()` sprint logic lines 648-718 and `canStartSprinting()` helpers lines 1020-1041. The `Abilities` local and local-variable names differ, but sprint guards and call order match. `Entity.isSprinting/setSprinting` lines 2162-2169 still reads/writes shared flag 3. `LivingEntity.setSprinting()` lines 2008-2015 removes the same modifier by its `id()` accessor and re-adds it transiently when enabling; the constant keeps the same UUID and `0.3F` amount while its operation is named `ADD_MULTIPLIED_TOTAL`. Entity.java SHA-256 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`; LocalPlayer.java `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`; LivingEntity.java `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; `AttributeModifier.java` `5acf5ec4d5a3361af6da18ec7c26ddbfdce6fa8dece778be7c20d345ca846c4c`; `AttributeInstance.java` `5d060c1740336835c74138d3c5fe3ae5f3b7f6a1c2f16f6844289f18ef90782a`.
- B client sync evidence: `LocalPlayer.sendIsSprintingIfNeeded()` lines 273-281 sends START_SPRINTING or STOP_SPRINTING when `isSprinting()` differs from `wasSprinting`, then records the state. Server `handlePlayerCommand()` lines 1370-1412 applies those actions with `player.setSprinting(true/false)`. `ServerGamePacketListenerImpl.java` SHA-256 `be4268a7616ed94e2a03411a6fdf002b0e6dc3303ea959c9f41fef322867d533`.
- State producers/writers -> consumers/readers: input and player state -> LocalPlayer sprint gates and timer -> `setSprinting` -> shared sprint flag plus temporary movement-speed modifier -> client sprint command -> server handler updates the player flag and modifier. `canStartSprinting` reads item-use, blindness, passenger/local-control, fall-flying, forward impulse and food level; food production remains excluded. The passenger path delegates to vehicle `canSprint`, without examining vehicle physics.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; paired local sprint gate ranges, setter/aggregation path, and client/server sprint command path reviewed. `AttributeInstance.calculateValue()` A lines 130-148 and B lines 140-158 apply the total modifier with the same sequence and multiplication `value *= 1.0 + amount`; the renamed operation retains ordinal 2 and does not change this formula. Other sprint state writers and movement consumers remain unresolved.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The reviewed local start/stop block and client/server state synchronization have the same timer, conditions, thresholds and state writes. Sprint's `MULTIPLY_TOTAL` to `ADD_MULTIPLIED_TOTAL` rename feeds the same attribute aggregation loop and arithmetic. The full sprint-gates slice remains open because all other sprint-state writers and movement consumers are not inventoried.
- Finding IDs or checked absence/replacement path: no difference in the reviewed local gates or sprint-speed modifier pipeline; no finding. Remaining state writers/sync path stay open.

### Slice S1-jump-gates: 1 / jump request, cooldown, jump delay, auto-jump and sprint-jump scheduling

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: sampled jump input copied to player state; auto-jump timer; jump-trigger cooldown/double-tap flight toggle; ordinary ground/fluid jump eligibility and `noJumpDelay`; call order through `LocalPlayer.aiStep`, `Player.aiStep` and `LivingEntity.aiStep`. Jump power and sprint-jump response remain in S3.
- A evidence: 1.20.4 `LocalPlayer.aiStep()` copies `input.jumping` into `this.jumping` at line 615, sets the auto-jump input when `autoJumpTime > 0` at lines 673-678, and performs the mayfly jump-trigger set/toggle/reset at lines 726-732 without calling `jumpFromGround()` on flight activation. `LocalPlayer.aiStep()` calls `super.aiStep()` at line 800. `Player.aiStep()` decrements `jumpTriggerTime` at lines 506-509 and calls `super.aiStep()` at line 523. `LivingEntity.aiStep()` decrements `noJumpDelay` at lines 2512-2513 and applies the ground/water/lava eligibility checks and ten-tick delay at lines 2571-2587. `LocalPlayer.java` SHA-256 `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`; `Player.java` `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd`; `LivingEntity.java` `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`.
- B evidence: 1.20.6 corresponding input, auto-jump and `LivingEntity.aiStep()` gates at LocalPlayer lines 617, 676-681 and LivingEntity lines 2592-2667; cooldown decrement and superclass call in `Player.aiStep()` at lines 515-518 and 532. The double-tap flight path is lines 729-739 and calls `jumpFromGround()` only after enabling flight while on ground. `LocalPlayer.aiStep()` calls `super.aiStep()` at line 807. `LocalPlayer.java` SHA-256 `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`; `Player.java` `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`; `LivingEntity.java` `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`.
- State producers/writers -> consumers/readers: input rising edge/auto-jump timer -> LocalPlayer `input.jumping` and jump-trigger timer -> Player cooldown decrement and `super.aiStep` -> LivingEntity ground/fluid gate and `noJumpDelay` -> virtual `jumpFromGround`. The same cooldown and ground/fluid gate arithmetic/order is present at both endpoints; changed flight activation and jump power/cutoff are covered by MC1204-1206-01 through -03.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; `S1-input-sampling` and `S1-input-motion` provide paired input evidence. S3-ground-jump and S3-sprint-jump hold the downstream jump-power and impulse deltas. Health/food production in `Player.aiStep` is outside scope.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The inspected jump timers, auto-jump request, ordinary ground/fluid eligibility checks and `noJumpDelay` are paired with the same thresholds and call order. A B-only `jumpFromGround()` call on grounded mayfly flight activation is source-confirmed as MC1204-1206-01; downstream jump-power and sprint impulse differences are MC1204-1206-02/03. The full jump scheduling slice remains open for remaining consumers and dependencies.
- Finding IDs or checked absence/replacement path: MC1204-1206-01, MC1204-1206-02, MC1204-1206-03.

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
- Exact behavior boundary and enclosing guards/order checked: `Player.tick()` calls `updatePlayerPose()` after the other player-tick state updates (A line 294; B line 314). The full `updatePlayerPose()` body (A lines 373-401; B lines 393-421) and `canPlayerFitWithinBlocksAndEntitiesWhen(Pose)` body (A lines 403-405; B lines 423-425) were compared. The pose-priority order and fit fallback are identical: first require the SWIMMING pose to fit; then choose FALL_FLYING, SLEEPING, SWIMMING, SPIN_ATTACK, CROUCHING (only while not flying), or STANDING; accept that pose for spectators, passengers, or a successful fit, else fall back to CROUCHING if it fits and SWIMMING otherwise; write with `setPose`.
- A evidence: `Player.java` hashes to `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd`; paired methods above. `Entity.java` hash `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9`; `setPose` lines 353-355 stores DATA_POSE, and `onSyncedDataUpdated` lines 2708-2710 calls `refreshDimensions()` when DATA_POSE changes. `SynchedEntityData.set` lines 125-136 calls the entity callback after a changed value and before marking it dirty; hash `2fd8ab1c4309ba66f0b0249d89f4209cf720fc8923be63277ae695232824f3e7`.
- B evidence: `Player.java` hashes to `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`; paired methods above. `Entity.java` hash `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`; `setPose` lines 355-357 stores DATA_POSE, and `onSyncedDataUpdated` lines 2719-2721 calls `refreshDimensions()` when DATA_POSE changes. `SynchedEntityData.set` lines 60-67 calls the entity callback after a changed value and before marking it dirty; hash `755998494338969b6d47b91c463057d9a009d6804e8d06b0ab30719634cc9234`.
- State producers/writers -> consumers/readers: per-tick player flags (fall-flying, sleeping, swimming, spin attack, shift and flight) plus the current collision world -> `updatePlayerPose()` -> `canPlayerFitWithinBlocksAndEntitiesWhen` -> candidate `getDimensions(pose)` / position / `noCollision` -> `setPose(DATA_POSE)` -> dimension/eye-height/bounding-box refresh. The selection and fit-query bodies are identical. The candidate geometry differs when B's non-default SCALE attribute is supplied; that input path is separately recorded in MC1204-1206-06 and S2-dimensions.
- Parent slices / dependencies / closure evidence: D-SOURCES verified; `S1-local-tick-call-order` supplies the existing local-player-to-player tick entry path. MC1204-1206-06 closes the B SCALE producer/consumer path for candidate geometry. S2-dimensions and S4 collision-query/shape coverage remain open, so the unchanged branch logic is not a claim that pose-fit outcomes are equivalent across every input.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): No change was found in the player pose-selection priority, fit-test construction, fallback order or pose write. Exact pose-fit results still depend on the open collision/query inventory and the B scale-driven dimensions finding, so this slice remains open.
- Finding IDs or checked absence/replacement path: no new finding; MC1204-1206-06 is the linked, independently scoped non-default-scale input delta.

### Slice S2-dimensions: 2 / entity dimensions, eye height, resize ordering and collision checks

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: pose-to-dimensions and eye-height calculation, active dimension refresh after pose/attribute changes, and player pose-fit box construction. Exact downstream block/entity collision outcomes remain in S4.
- A evidence: `LivingEntity.getDimensions(Pose)` lines 3161-3163 returns fixed sleeping dimensions or `super.getDimensions(pose).scale(getScale())`; `Player.getDimensions(Pose)` lines 1944-1946 selects from POSES. `Player.getStandingEyeHeight` lines 1833-1844 supplies fixed per-pose eye heights; `LivingEntity.getEyeHeight` lines 3260-3266 handles sleeping and delegates to the standing-eye method. `Entity.refreshDimensions` lines 2722-2744 updates active dimensions, derives eye height through the pose/dimension call, reapplies position, then performs guarded server-side expansion resolution. `EntityType.PLAYER` A is sized 0.6F by 1.8F and its constructor dynamically calculates standing eye height; this resolves to Player's 1.62F override. `EntityDimensions.scale(float)` lines 27-33 scales width and height for scalable dimensions. Hashes: LivingEntity `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`; Player `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd`; Entity `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9`; EntityDimensions `ced817743f3bd35462c94c0112adf6f54eabf6132b0a349b05160e519bad3502`.
- B evidence: `LivingEntity.getDimensions(Pose)` lines 3240-3242 now calls `getDefaultDimensions(pose).scale(getScale())`; `Player.getDefaultDimensions(Pose)` lines 1997-1999 selects the same POSES map. `EntityDimensions.scale(float)` lines 25-33 scales width, height, eye height and attachments. Pose eye heights are stored on B's player pose dimensions (Player lines 130-147); `Entity.refreshDimensions` lines 2733-2755 copies `dimensions.eyeHeight()`, reapplies position and retains the same guarded server-side expansion-resolution order. `EntityType.PLAYER` is sized 0.6F by 1.8F with `.eyeHeight(1.62F)`, preserving A's default standing eye height before any attribute refresh. `Entity.onSyncedDataUpdated` calls refresh on DATA_POSE changes (A lines 2708-2710; B lines 2719-2721). Hashes: LivingEntity `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; Player `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`; Entity `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`; EntityDimensions `9fb9575ec615d4aefe6316c443e87a902f44e955393778a4b93623e8b680adc4`.
- State producers/writers -> consumers/readers: pose writer DATA_POSE -> refreshDimensions -> active bounding dimensions and eye height; B synchronized SCALE attribute -> `LivingEntity.tick()` dirty-attribute refresh at lines 2456-2461 -> refreshDimensions; player pose-fit and other bounding-box/eye consumers read those dimensions. A Player's `getDimensions` override selects the unscaled pose map directly, so it bypasses LivingEntity's generic scale call; eye height is computed separately by the standing-eye-height override. B moves the pose map override to `getDefaultDimensions` under final scale-aware `LivingEntity.getDimensions`, and carries/scales eye height within EntityDimensions. The B SCALE client receiver and external upstream value source are described in MC1204-1206-06.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved and all four cited per-class A/B hashes match their entries in the exact source manifests. MC1204-1206-06 covers the non-default SCALE attribute path. Pose-selection logic is separately inventoried in S2-pose-selection. S4 collision-query and shape-provider closure is still required before player fit/active-box collision outcomes are closed.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B's default SCALE=1.0 preserves ordinary player dimensions, while a supplied non-default scale changes scalable pose width, height and eye height, followed by the tick-tail refresh. The old and new pose-to-dimensions dispatch was paired, but exact pose-fit collision effects and the broader dimension consumer inventory remain open; no additional finding is split from MC1204-1206-06.
- Finding IDs or checked absence/replacement path: MC1204-1206-06; no new independent delta identified in the paired pose mapping/refresh order.

### Slice S2-player-state: 2 / player movement state initialization/update/reset (excluding excluded health/food systems)

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: bounded Entity movement-state defaults and constructor initialization (delta movement, on-ground, position, initial pose, dimensions and eye height), plus the direct ground-state setter/getter and delta-movement accessor bodies. Their caller inventory and remaining Player/LivingEntity state writers/readers are still open.
- A evidence: `Entity.java` hash `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9`: deltaMovement defaults to `Vec3.ZERO` (line 161), onGround defaults false (line 167), and constructor lines 244-263 assigns entity type dimensions, zero position and initial STANDING pose, then derives eye height through `getEyeHeight(Pose.STANDING, dimensions)`. `setOnGround`, `setOnGroundWithKnownMovement`, `checkSupportingBlock` and `onGround` are lines 556-594; both setters write the same flag before calling the support helper, whose grounded path queries `Level.findSupportingBlock` using the current box (and a movement-offset box only when the first query is empty and movement is supplied), while its false path clears support state. `getDeltaMovement`, `setDeltaMovement`, `addDeltaMovement` and the component setter are lines 3166-3180 and perform direct read, assignment and addition. `EntityType.PLAYER` lines 566-568 sets size 0.6F by 1.8F; `EntityType.java` hash `9c8840b79481b582d25ab72297bf0e75d04cd5028b33dbb6323d29aeba0c4455`. Player constructor lines 176-183 invokes that superclass then sets profile/inventory and position; remaining constructor-to-first-tick writers are open.
- B evidence: `Entity.java` hash `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`: deltaMovement defaults to `Vec3.ZERO` (line 163), onGround defaults false (line 169), and constructor lines 245-265 assigns the same type dimensions and zero position, builds synched data with initial STANDING pose, then copies `dimensions.eyeHeight()`. `setOnGround`, `setOnGroundWithKnownMovement`, `checkSupportingBlock` and `onGround` are lines 557-595 with the same field-write/call order, support-query boxes, optional movement-offset retry and support clearing as A. `getDeltaMovement`, `setDeltaMovement`, `addDeltaMovement` and the component setter are lines 3181-3195 with the same direct read, assignment and addition. `EntityType.PLAYER` lines 821-828 sets size 0.6F by 1.8F and explicit eye height 1.62F; `EntityType.Builder.eyeHeight` lines 1223-1225 applies it with `withEyeHeight`; `EntityType.java` hash `5de729295e0d4821db6e88272889291254b14e843baf82a3c2c7ec1588bb7c01`. Player constructor lines 194-201 invokes that superclass then sets profile/inventory and position; remaining constructor-to-first-tick writers are open.
- State producers/writers -> consumers/readers: Entity constructor and default fields -> active position/velocity/ground/dimension/eye-height state; direct ground setters update the flag then query/clear supporting-block state; `onGround()` exposes the flag to movement. The direct delta-movement accessors read/write velocity and `addDeltaMovement` adds to it. Pose changes reach `refreshDimensions` through the paired DATA_POSE callback in S2-pose-selection. B's type-level 1.62F eye-height default matches A's Player virtual eye-height calculation for standing pose; B non-default SCALE changes are separately in MC1204-1206-06. Exact support results depend on S4 collision/query providers; broader ground/velocity producers and consumers remain in S3/S4/S7.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; all cited Entity/EntityType hashes match their exact source-manifest entries. Ground setters call `Level.findSupportingBlock`, so provider/query and movement-call closure remain in S4; velocity application/cutoff consumers remain in S3/S4/S7. Pose/dimension paths link to S2-pose-selection and S2-dimensions.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The bounded initial velocity, ground, position and player standing-eye-height values match at the exact endpoints. The direct flag and velocity accessor methods also have the same operation order; the support helper's external query result and the callers that set these values are not closed by this comparison. No default eye-height delta is found in Entity construction: A's player override yields 1.62F, while B registers the Player EntityType with 1.62F. The broader player movement-state writer/reader inventory is incomplete, so no whole-state equivalence is claimed.
- Finding IDs or checked absence/replacement path: no new finding for these defaults; MC1204-1206-06 remains the linked conditional non-default-scale dimension change.
### Slice S2-sprint-consumers: 2 / hunger/blindness/item-use vanilla consumers of sprint gates

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: local `isMovingSlowly`, `canStartSprinting`, `hasEnoughImpulseToStartSprinting`, `hasEnoughFoodToStartSprinting` and `vehicleCanSprint` bodies; inherited `Player.canSprint`; the containing local sprint-start/stop order is cross-referenced in S1-sprint-gates.
- A evidence: `LocalPlayer.java` SHA-256 `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`; `isMovingSlowly` lines 605-607, `canStartSprinting` 1013-1019, `vehicleCanSprint` 1021-1023, `hasEnoughImpulseToStartSprinting` 1025-1029, and `hasEnoughFoodToStartSprinting` 1032-1034. `Player.java` SHA-256 `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd`; `canSprint` lines 2059-2061.
- B evidence: `LocalPlayer.java` SHA-256 `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`; corresponding bodies lines 607-609, 1020-1026, 1028-1030, 1032-1036, and 1039-1041. `Player.java` SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`; `canSprint` lines 2112-2114.
- State producers/writers -> consumers/readers: crouch/visual-crawl flags -> `isMovingSlowly` -> input slowdown; current forward input, sprint state, item-use state, blindness effect, fall-flying, passenger state and vehicle-provided canSprint/control results -> local sprint start; passenger/food level/mayfly -> food gate. Food and effect state are read as vanilla inputs only; their producer systems are excluded. `Player.canSprint()` returns true in both versions.
- Parent slices / dependencies / closure evidence: D-SOURCES verified; paired full-file hashes match source-manifest entries. `S1-sprint-gates` records the containing `LocalPlayer.aiStep()` decision and state-update order. This row closes these same-argument gate/read semantics; it does not audit food/effect production or vehicle movement.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): each paired gate reads the same inputs with the same thresholds and boolean order. Both use forward impulse `>= 0.8` outside water and the input forward-impulse predicate underwater; food allows passengers, food level above 6.0F, or mayfly; blindness and item-use gates are unchanged. For equal current vanilla inputs, the sprint eligibility result is unchanged.
- Finding IDs or checked absence/replacement path: no finding; direct food/blindness reads are preserved vanilla inputs, not producer-system changes.

### Slice S2-flight-abilities: 2 / flight ability flags and direct movement consumers

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: `Abilities` defaults, save/load accessors, client application of server-supplied flight capability/speed, and `Player.getFlyingSpeed()` branch. Remaining local flight-key transitions and direct player travel/pose/climbing consumers are cross-linked to S1-flight-tick, S2-pose-selection and S3 travel/climbing rows and remain open.
- A evidence: complete `Abilities.java` SHA-256 `a4f952ada7bc3b21406feaf13c171bfa22d36b01e265e3b987612f28b5609edc`; fields/defaults and methods are lines 5-55. `ClientPacketListener.handlePlayerAbilities` lines 1783-1792 assigns flying/mayfly and speed from the received packet; file SHA-256 `ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f`. `Player.getFlyingSpeed()` lines 2064-2070; Player hash `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd`.
- B evidence: complete `Abilities.java` SHA-256 matches A exactly. `ClientPacketListener.handlePlayerAbilities` lines 1796-1805 maps the same packet values to the same ability fields/accessors; file SHA-256 `e121e998ec25211aaecf91fffd0ea699cebd47eddea9134efd3f2ffbef8eb7cd`. `Player.getFlyingSpeed()` lines 2117-2123; Player hash `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`.
- State producers/writers -> consumers/readers: local `Abilities` defaults or external ClientboundPlayerAbilitiesPacket -> client packet receiver -> `flying`, `mayfly`, flying speed -> Player/LocalPlayer gates and flying movement. The packet also carries non-movement ability bits; only flight and resulting player-movement consumers are in scope. Local key toggles send an abilities update and are inventoried in S1-flight-tick.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; `Abilities.java` is byte-identical across A/B and cited packet receiver/Player files match source-manifest entries. Bounded defaults, receiver mapping and flying-speed formula show no difference. S1-flight-tick and S3 flight/travel branches still need complete per-tick consumer/caller closure.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): A/B ability defaults, persisted fields, client receiver assignment order and Player flying-speed branch match. Equal server-supplied ability values therefore enter the same bounded speed calculation. The full flight movement-state writer/consumer inventory remains open; this is not a whole-flight equivalence claim.
- Finding IDs or checked absence/replacement path: no new finding in this bounded defaults/receiver/speed slice; flight activation change is independently recorded in MC1204-1206-01.

### Slice S3-travel-dispatch: 3 / travel dispatch, branch selection and pre/post-travel sequence

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: local Player travel dispatch through the `LivingEntity.travel(Vec3)` branch selector and its local-control gate, including immediately preceding horizontal input damping and effect-driven fall-distance reset; all upstream inputs and remaining pre/post-travel call graph remain open.
- A evidence: 1.20.4 `LivingEntity.aiStep()` multiplies `xxa` and `zza` by `0.98F` at lines 2591-2592 before updating fall-flying and constructing the movement vector, then dispatches `this.travel($$9)` at lines 2595-2603; Slow Falling or Levitation resets fall distance before dispatch. `LivingEntity.travel(Vec3)` lines 2035-2166 branches under `isControlledByLocalInstance()` in water, lava, fall-flying, then ordinary order, followed by `calculateEntityAnimation`. `Player.travel(Vec3)` lines 1451-1475 reaches `super.travel` in both wrapper branches. `LivingEntity.java` SHA-256 `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`; `Player.java` `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd`.
- B evidence: 1.20.6 `LivingEntity.aiStep()` applies the same `xxa`/`zza` `0.98F` damping before fall-flying update and vector construction at lines 2671-2683, with the same Slow Falling/Levitation fall-distance reset; `LivingEntity.travel(Vec3)` lines 2104-2233 uses the same outer control gate and water, lava, fall-flying, then ordinary branch order before `calculateEntityAnimation`. `Player.travel(Vec3)` lines 1473-1497 reaches `super.travel` in both wrapper branches. `LivingEntity.java` SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; `Player.java` `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`.
- State producers/writers -> consumers/readers: `LivingEntity.aiStep()` input axes -> `0.98F` horizontal damping -> fall-flying state update and effect-gated fall-distance reset -> movement vector/current player state -> virtual `Player.travel` -> `LivingEntity.travel` only when locally controlled -> ordered fluid/fall-flying/ordinary branch selection -> velocity update and movement; method then performs the animation update. `Player.travel` also restores prior vertical velocity and resets fall distance in the abilities-flying, non-passenger branch after calling super. Branch-specific calculations remain in S3 rows; post-travel tick work outside this method is open.
- Parent slices / dependencies / closure evidence: D-SOURCES verified; S1 input/tick ordering supplies the movement-vector caller. Fall-flying branch reachability and gravity consumer are recorded in S3-gliding and MC1204-1206-04; water/lava and ordinary branch dependencies remain in their respective slices. Full tick call graph and post-travel writer inventory remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): local Player virtual dispatch and branch order are paired for the inspected caller path; branch outcomes vary by movement state and include independently recorded gravity differences. This bounded caller/selector comparison does not close the remaining input producers, branch formulas, callbacks or post-travel work.
- Finding IDs or checked absence/replacement path: MC1204-1206-04 covers one branch consumer; no standalone dispatch finding.

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
- Exact behavior boundary and enclosing guards/order checked: bounded gravity-local consumers and ordinary travel gravity/drag branch in controlled travel (ordinary, fall-flying, water, lava and fluid-falling helper); all gravity/effect producers, post-travel updates and remaining reachable gravity consumers stay open.
- A evidence: 1.20.4 `LivingEntity.travel(Vec3)` lines 2035-2166 and `getFluidFallingAdjustedMovement(double, boolean, Vec3)` lines 2215-2231, SHA-256 `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`; base gravity local is 0.08, descending Slow Falling replaces it with 0.01, and fall-flying lines 2099-2125 consume that same local in the ordered vertical glide term. Water/lava quarter-gravity adjustment is gated by `!isNoGravity()`; the fluid-falling helper is gated by `!isNoGravity() && !isSprinting()`.
- B evidence: 1.20.6 `LivingEntity.travel(Vec3)` lines 2104-2233, `getDefaultGravity()` lines 2100-2102 and `getFluidFallingAdjustedMovement(double, boolean, Vec3)` lines 2282-2298, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; local reads effective gravity and descending Slow Falling applies `Math.min(gravity, 0.01)`. Fall-flying lines 2168-2194 consume the same local. `Entity.getGravity()` lines 1101-1107 also maps no-gravity to zero; `Entity.java` SHA-256 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`. Travel numeric gravity gates and the helper's `gravity != 0.0` gate consume that effective value. In the ordinary branch, A's client-unloaded-chunk fallback precedes the no-gravity test; B reorders these predicates, with equivalent player outcomes for the combinations inspected. Both preserve the player vertical `0.98F` drag and `shouldDiscardFriction` bypass; B scales vertical drag with horizontal friction only for `FlyingAnimal`, outside scope. MC1204-1206-04.
- State producers/writers -> consumers/readers: B syncable GRAVITY attribute -> `LivingEntity.getDefaultGravity()` / `Entity.getGravity()` -> land, fall-flying, fluid-falling, water and lava travel branches -> velocity updates. The client attribute receiver installs base values and modifiers. LocalPlayer's enabled-Elytra jump gates set shared fall-flying flag 7, consumed by `LivingEntity.travel`; exact caller/branch route is in S3-travel-dispatch/S3-gliding and MC1204-1206-04. Server-side value provenance, effect-state production, the upstream chunk-availability state and post-travel tick updates remain open.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; the finding-specific player gravity input/client receiver and bounded travel/helper branches, including fall-flying, are recorded in MC1204-1206-04. S3-travel-dispatch and S3-gliding now contain their bounded caller and consumer evidence but remain in-progress. Full gravity/drag/effect comparison remains open, including upstream chunk-state provenance and post-travel tick updates.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Default gravity remains 0.08. B consumes effective gravity in ordinary, fall-flying, fluid-falling, water and lava branches; descending Slow Falling caps with `Math.min` in B but replaces the local in A. Ordinary-branch player vertical drag remains `0.98F` in both, with equivalent client chunk-fallback outcomes despite predicate reordering. B also scales vertical drag with horizontal friction for `FlyingAnimal`, excluded from player scope. Broad gravity/effect/post-travel closure remains open.
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
- Exact behavior boundary and enclosing guards/order checked: bounded fall-flying travel consumer, local-player state-entry gates and `LivingEntity.updateFallFlying()` tick validation/durability cadence; Elytra durability semantics, item-use slowdown and other equipment/state producers remain open.
- A evidence: 1.20.4 `LivingEntity.travel(Vec3)` fall-flying branch lines 2099-2125 reads `deltaMovement`, look angle, pitch, horizontal look length and horizontal speed; forms `cos(pitch) * cos(pitch) * min(1.0, lookLength / 0.4)`, applies the `0.75` glide factor and local gravity term, then descending-look lift, nose-down lift and horizontal alignment in order, scales velocity `(0.99F, 0.98F, 0.99F)`, moves, and clears shared flag 7 on grounded server movement. `LivingEntity.aiStep()` calls `updateFallFlying()` before travel at line 2593; the updater lines 2641-2666 preserves flag 7 only when airborne, unmounted, without Levitation and wearing an enabled Elytra; on server it damages the Elytra every second 10-tick interval and emits `ELYTRA_GLIDE` each 10 ticks. A `LivingEntity.tick()` increments `fallFlyTicks` while flag 7 is set and resets it otherwise (lines 2379-2383). `Player.tryToStartFallFlying()` lines 1506-1520 supplies the state gate/setter; `LivingEntity.isFallFlying()` lines 3086-3088 reads flag 7. The A updater calls `ItemStack.hurtAndBreak` with an Elytra-slot callback. `LivingEntity.java` SHA-256 `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`; `Player.java` `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd`; `ItemStack.java` `0e0b2a4973ee41e042a9ee6158ffdd4a366676f90019cb2c7d236de7bc809710`.
- B evidence: 1.20.6 corresponding `LivingEntity.travel(Vec3)` fall-flying branch lines 2168-2194 has the same ordered glide operations and consumes the effective gravity local from `getGravity()`; the source difference in that local is MC1204-1206-04. `LivingEntity.aiStep()` calls `updateFallFlying()` before travel at line 2673; the updater lines 2721-2746 has the same state guards, 10-tick event cadence and every-second-interval durability attempt, but calls the `EquipmentSlot.CHEST` overload. B `LivingEntity.tick()` increments/resets `fallFlyTicks` on the same flag at lines 2446-2450. `Player.tryToStartFallFlying()` lines 1534-1548 and `LivingEntity.isFallFlying()` lines 3165-3167 use the same gates and flag. B `ItemStack.java` SHA-256 `74a7e82a55577a299e25180bed9560c983ab0f620f72eaff0574a9b497e7f4e0`; `LivingEntity.java` `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; `Player.java` `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`.
- State producers/writers -> consumers/readers: jump-held local Elytra entry gate -> enabled chest Elytra and `tryToStartFallFlying()` gates -> `startFallFlying()` sets shared flag 7 -> LivingEntity tick increments `fallFlyTicks` -> pre-travel `updateFallFlying()` revalidates equipment/pose/effect and server-side durability -> local-controlled Player travel dispatch -> `isFallFlying()` selects glide branch -> gravity/look/current-velocity calculation -> velocity write and `move`. B's effective gravity affects the paired vertical term; durability callback/API and item-stack data are routed to S6.
- Parent slices / dependencies / closure evidence: D-SOURCES verified; LocalPlayer entry gates (A line 739, B line 746) and Player/LivingEntity consumers are paired in MC1204-1206-04. The A/B Elytra registration keeps durability 432 (`Items.java` A line 828, B line 884); default-stack durability representation moves from NBT/tag state to components. `S6-equipment` and `S6-enchantments` now track the reached wear/Unbreaking path; B gravity attribute input/client receiver is recorded in MC1204-1206-04 and its external upstream producer remains unknown. Item-use slowdown and other state writers/consumers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): for a locally controlled player in fall-flying state, the branch math/order matches except that the shared gravity local is fixed `0.08` in A and effective in B, including the preceding Slow Falling cap. `updateFallFlying()` state guards and wear/event cadence match, but the called durability APIs and stack data representation differ and are being closed in S6; no no-difference claim is made for unbreakable/custom stack state. No non-player `FlyingAnimal` movement is claimed. Item-use slowdown and the broader gliding inventory remain open.
- Finding IDs or checked absence/replacement path: MC1204-1206-04; wider Elytra/equipment dependencies remain open.

### Slice S3-effects-branch: 3 / movement effect branches and operation order within travel

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: paired bounded consumers for Jump Boost, Slow Falling and Levitation in jump-power lookup, travel, pre-travel fall-distance reset and Elytra validation; all effect producers/modifier lifecycles and other movement effects remain open.
- A evidence: 1.20.4 `LivingEntity.getJumpBoostPower()` lines 2004-2006 adds `0.1F * (amplifier + 1.0F)` when Jump Boost is active; `getJumpPower()` lines 2000-2002 adds it after the `0.42F * blockJumpFactor` term. `travel(Vec3)` lines 2039-2041 caps descending Slow Falling gravity to `0.01`; ordinary travel lines 2145-2147 apply Levitation's `(0.05 * (amplifier + 1) - currentY) * 0.2` adjustment. `aiStep()` lines 2591-2597 applies horizontal input damping, updates fall flying, then resets fall distance when Slow Falling or Levitation is active before travel. `updateFallFlying()` lines 2641-2644 rejects Levitation. `LivingEntity.java` SHA-256 `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`.
- B evidence: 1.20.6 `LivingEntity.getJumpBoostPower()` lines 2065-2067 uses the same amplifier formula; `getJumpPower(float)` lines 2061-2063 adds it after the attribute-scaled base/block term (the attribute change is MC1204-1206-03). `travel(Vec3)` lines 2108-2110 caps descending Slow Falling gravity and ordinary travel lines 2214-2216 apply the same Levitation adjustment. `aiStep()` lines 2671-2677 uses the same damping, fall-flying update and effect-gated fall-distance reset order. `updateFallFlying()` lines 2721-2724 rejects Levitation. `LivingEntity.java` SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`.
- State producers/writers -> consumers/readers: active effect instances/amplifiers -> Jump Boost power, Slow Falling gravity cap, Levitation travel adjustment and fall-flying rejection, and effect-gated fall-distance reset -> jump or travel velocity/state. Effect source/expiry, attribute-modifier additions/removals and resource definitions are not closed here.
- Parent slices / dependencies / closure evidence: D-SOURCES verified; jump-strength and ground-jump cutoff sources are already captured in MC1204-1206-02/03. Gravity and Elytra consumers are recorded in S3-gravity-drag/S3-gliding. S6-effects, S6-resources and full effect state lifecycle remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): the bounded Jump Boost amplifier term, Slow Falling cap and Levitation travel/reset/flight gates match at the two endpoints, subject to the separately reported jump-strength and gravity changes. No effect producer, modifier-lifecycle or resource-completeness claim is made.
- Finding IDs or checked absence/replacement path: MC1204-1206-02 and MC1204-1206-03 cover reached ground-jump dependencies; MC1204-1206-04 covers the gravity consumer.
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
- State producers/writers -> consumers/readers: B syncable STEP_HEIGHT attribute -> `ClientPacketListener.handleUpdateAttributes` applies supplied living-entity values -> `LivingEntity.maxUpStep` -> unchanged `Entity.move` candidate-height input; controlling-player passenger still floors the result at 1.0F. A player constructor sets the inherited field to 0.6F.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved. Finding-specific input-to-consumer dependency is closed in MC1204-1206-05. The wider collision candidate/shape/tie-break path remains open for this broad step-candidate slice and is outside the finding's stated consequence.
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
- Exact behavior boundary and enclosing guards/order checked: 6 / movement attributes, defaults, aggregation and modifier order; partial: player movement attributes in findings 03-06 and sprint-speed modifier aggregation.
- A evidence: 1.20.4 `Attributes.java` hash `287dcd477ccca2aebd24ad28db7b0e7a1e9dc9bbeb272d330cd15504b8669ff7`; no generic GRAVITY/SCALE/STEP_HEIGHT/JUMP_STRENGTH player inputs; jump strength entry is horse-specific.
- B evidence: 1.20.6 `Attributes.java` hash `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e`; generic gravity/jump-strength/scale/step-height are syncable and added by LivingEntity.createLivingAttributes(). Findings MC1204-1206-03 through -06.
- State producers/writers -> consumers/readers: Player.createAttributes inherits living attributes -> supplied/synchronized attributes -> jump, travel gravity, dimensions and step-limit consumers. Sprint state writes also add/remove the sprinting movement-speed modifier. The A `MULTIPLY_TOTAL` and B `ADD_MULTIPLIED_TOTAL` operation entries both have ID 2; `AttributeInstance.calculateValue()` retains the same add, base-multiply, then repeated `value *= 1.0 + amount` order.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; four movement attribute deltas are recorded in MC1204-1206-03 through -06. Sprint modifier aggregation is compared as no difference in S1-sprint-gates. Remaining movement attributes, modifier sources/order, registration/default-source coverage and resource/sync closure remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Four source-confirmed player movement attribute paths were found, and the sprint modifier formula is unchanged across the pair. This broad attributes inventory is still incomplete, so no terminal disposition is claimed.
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
- Exact behavior boundary and enclosing guards/order checked: bounded Unbreaking durability-drop formula reached by the fall-flying Elytra wear path; the listed movement enchantments Depth Strider, Soul Speed, Swift Sneak, Frost Walker and Riptide, plus full Unbreaking registration/application/data closure, remain open.
- A evidence: 1.20.4 `ItemStack.hurt(int, RandomSource, ServerPlayer)` lines 336-364 reads the Unbreaking level with `EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, stack)` and loops once per requested damage point, counting ignored drops through `DigDurabilityEnchantment.shouldIgnoreDurabilityDrop` (lines 33-35). For an Elytra, the helper's `ArmorItem` special case is false because `ElytraItem` extends `Item`; its ordinary result is `random.nextInt(level + 1) > 0`. `ItemStack.java` SHA-256 `0e0b2a4973ee41e042a9ee6158ffdd4a366676f90019cb2c7d236de7bc809710`; `DigDurabilityEnchantment.java` `40d38d3e0183f86fdb2a8a81c44041ab7738146cc498d9243e81e0a19aa1b8ee`.
- B evidence: 1.20.6 `ItemStack.hurtAndBreak(int, RandomSource, ServerPlayer, Runnable)` lines 407-435 uses the same Unbreaking level lookup, per-point loop and `DigDurabilityEnchantment.shouldIgnoreDurabilityDrop` formula (lines 12-14); for Elytra the same non-`ArmorItem` branch applies. `ItemStack.java` SHA-256 `74a7e82a55577a299e25180bed9560c983ab0f620f72eaff0574a9b497e7f4e0`; `DigDurabilityEnchantment.java` `49756598ac418212eff7891252e797dc7ff98087209b5c820ebd23cc963e9178`.
- State producers/writers -> consumers/readers: Elytra wear attempt in `LivingEntity.updateFallFlying()` -> `ItemStack.hurtAndBreak` -> current Unbreaking level -> per-point random durability suppression -> damage write/break callback. The bounded suppression formula matches; the enchantment's registration, application, item-stack serialization and external stack level remain unverified.
- Parent slices / dependencies / closure evidence: D-SOURCES verified; the parent consumer is S3-gliding. The adjacent S6-equipment row records the Elytra durability registration and tag/component representation; complete modifier/effect/enchantment, data and resource inventories remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): for the bounded Unbreaking suppression operation reached by vanilla Elytra wear, the A/B lookup, loop and random comparison are identical. The full enchantment inventory and value/application data are incomplete, so no broad no-difference disposition is claimed.
- Finding IDs or checked absence/replacement path: no new difference identified in the checked Unbreaking durability-drop formula; S3-gliding and S6-equipment dependencies remain open.

### Slice S6-equipment: 6 / Elytra, movement item components/equipment slots and use slowdown

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: bounded Elytra registration, flight-enabled predicate and fall-flying wear/slot consumer; item-use slowdown, other movement equipment and full item-component/resource closure remain open.
- A evidence: `Items.ELYTRA` registers `new ElytraItem(new Item.Properties().durability(432).rarity(UNCOMMON))` at line 828; A `Item.Properties.durability` stores maxDamage and maxStackSize (Item.java lines 402-405). `ElytraItem.isFlyEnabled` lines 18-20 returns `damage < maxDamage - 1`. `LivingEntity.updateFallFlying()` lines 2641-2666 revalidates airborne/unmounted/no-Levitation state and enabled chest Elytra, then on server damages one point every second ten-tick interval; `fallFlyTicks` is ticked/reset in `LivingEntity.tick()` lines 2379-2383. `ItemStack.isDamageableItem()` lines 311-318 uses item max damage and the `Unbreakable` tag; its `hurtAndBreak`/`hurt` path is lines 336-385. Hashes: `Items.java` `5eeaf7ddccb5a9a5c1d744a8e41daf9be1ca423a065078b33f02b3f932e7d8f7`; `Item.java` `36fef23a0d0c399de7b9ac8512303bfd0fa0ef98077c785787754fd6a7874c6a`; `ElytraItem.java` `fa4d677a5b109ebf323458f88f05eacb9ffdeca66d952c1a5eb8a7114a911889`; `ItemStack.java` `0e0b2a4973ee41e042a9ee6158ffdd4a366676f90019cb2c7d236de7bc809710`.
- B evidence: `Items.ELYTRA` has the same `durability(432)` and uncommon rarity registration at line 884. B `Item.Properties.durability` assigns `MAX_DAMAGE`, `MAX_STACK_SIZE=1` and `DAMAGE=0` components (Item.java lines 351-355); `ItemStack.isDamageableItem()` lines 387-389 requires max-damage, damage and absence of `UNBREAKABLE` components. `ElytraItem.isFlyEnabled` lines 19-21 uses the same `damage < maxDamage - 1` gate. `LivingEntity.updateFallFlying()` lines 2721-2746 has the same gates and cadence but uses the `EquipmentSlot.CHEST` durability overload; `Player.hasInfiniteMaterials()` lines 1361-1364 returns `abilities.instabuild`, matching A's direct creative check. Hashes: `Items.java` `d32e79b387582f41a9c7806814305515541cff22016d775934704bf8378ac9b9`; `Item.java` `3053e803946f731e1853ff311c936e64a3f9f41ed19ed1206cda582fb5976d2f`; `ElytraItem.java` `aafc9e310847fccd7ffffca5773d7df8b55285a940c185e95096baccbee2605f`; `ItemStack.java` `74a7e82a55577a299e25180bed9560c983ab0f620f72eaff0574a9b497e7f4e0`; `Player.java` `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`.
- State producers/writers -> consumers/readers: registered Elytra max damage/default damage -> chest equipment and `isFlyEnabled` -> fall-flying validity gate -> `fallFlyTicks` cadence -> server-side damage attempt and break callback -> next-tick equipment/flight gate. Default Elytra durability and flight threshold match at the endpoints; the stored tag-to-component representation differs.
- Parent slices / dependencies / closure evidence: D-SOURCES verified; S3-gliding is the reachable caller and S6-enchantments contains the bounded Unbreaking formula. Remaining custom/unbreakable stack-state sources, item durability data, other equipment, item-use slowdown and component/resource inventory remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): the registered vanilla Elytra has durability 432 in both versions, with the same rarity and flight-enabled threshold; its glide wear cadence and creative exemption are paired. A changes from NBT/tag durability to data components in B, so the wider equipment/component inventory is not closed and no broad no-difference conclusion is made.
- Finding IDs or checked absence/replacement path: no new difference identified in the bounded default Elytra registration/flight threshold; gravity consumer is MC1204-1206-04.

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
- State producers/writers -> consumers/readers: Attribute builder supplies player instances. A/B `ClientPacketListener.handleUpdateAttributes` accepts snapshots for living entities and applies base values/modifiers (A lines 2049-2073, SHA-256 `ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f`; B lines 2059-2083, SHA-256 `e121e998ec25211aaecf91fffd0ea699cebd47eddea9134efd3f2ffbef8eb7cd`). Client movement consumes current values at jump/travel/dimension/step paths. The upstream producer of non-default values remains external and unverified.
- Parent slices / dependencies / closure evidence: D-SOURCES resolved; bounded attribute additions are evidenced by MC1204-1206-03 through -06. Client-side receipt/application is now verified; exact external value provenance and broader server boundary remain open.
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

- Last completed checkpoint: added bounded local Player travel dispatch and pre-travel input damping/order, fall-flying gravity-consumer coverage, ordinary-branch player gravity/drag comparison, Jump Boost/Slow Falling/Levitation consumers, Elytra wear cadence and Unbreaking suppression paths; five original finding snapshots are accepted and corrected MC1204-1206-04 is awaiting focused re-review after REQUEST CHANGES on its original snapshot. Wider state, collision, gravity/drag and other producer/consumer inventories remain open.
- Task branch / checkpoint: `feat/source-discovery-1-20-4-1-20-6-resume-2026-10-08`, based on saved ref `477b6af`; source-only report updates are in progress. Latest `main` tip `270e8c85d656a0d2fae0770799e207ed1557d9ba` was merged after the pair-scoped report check; no pair-report commit after the previous merge was present.
- Default-branch reconciliation: the pair-specific conflict from `44958e8` remains resolved in favor of this branch's later exact-source evidence and snapshot history; latest `main` tip `270e8c85d656a0d2fae0770799e207ed1557d9ba` is merged. No pair-report commits after the import were observed.
- Next bounded comparison: continue `S3-gravity-drag` by tracing post-travel drag/effect-cap state writers and callers, then extend the remaining exact-source inventories. Use only the published 1.20.4 and 1.20.6 Mojmap trees and record each method range/hash before disposition.

Resume with these read-only source navigation commands from the repository root (use outputs only as navigation, then record exact method ranges/hashes in the ledger):

```powershell
$A = 'D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.20.4\mojmap'
$B = 'D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.20.6\mojmap'
rg -n -C 8 'updatePlayerPose|canPlayerFitWithinBlocksAndEntitiesWhen|getDefaultDimensions|getDimensions\(Pose|onSyncedDataUpdated|refreshDimensions' "$A/net/minecraft/world/entity/Entity.java" "$B/net/minecraft/world/entity/Entity.java" "$A/net/minecraft/world/entity/LivingEntity.java" "$B/net/minecraft/world/entity/LivingEntity.java" "$A/net/minecraft/world/entity/player/Player.java" "$B/net/minecraft/world/entity/player/Player.java"
rg -n -C 6 'getDeltaMovement|setDeltaMovement|onGround|isOnGround|wasTouchingWater|jumpTriggerTime|fallDistance|abilities\.flying' "$A/net/minecraft/world/entity/Entity.java" "$B/net/minecraft/world/entity/Entity.java" "$A/net/minecraft/world/entity/LivingEntity.java" "$B/net/minecraft/world/entity/LivingEntity.java" "$A/net/minecraft/world/entity/player/Player.java" "$B/net/minecraft/world/entity/player/Player.java"
```
- Outstanding dependencies and owners: S2-player-state remains open beyond its bounded constructor/accessor evidence; S2-pose-selection and S2-dimensions remain open on geometry-to-collision closure; S2-flight-abilities remains open on its remaining direct consumers; S3-gravity-drag remains open on remaining gravity consumers, effect caps and post-travel updates. No external source-preparation blocker. Focused re-review of corrected MC1204-1206-04 is pending; the eventual full-pair blind audit remains unassigned.
- Current assumptions requiring verification: no canonical 1.20.5 ready marker or source root is published beside the exact 1.20.4 and 1.20.6 sources, so all six endpoint findings retain first changed release `unknown within (1.20.4, 1.20.6]`; no boundary is inferred. Pose/dimensions method bodies used in this checkpoint were re-read from the immutable source roots and their file hashes match both manifests.

## Finding snapshots (not pair freeze)

- `MC1204-1206-01` submitted for independent blind source review: finding file `findings/MC1204-1206-01.md`, SHA-256 `eb15d0f72764cf783cb0ac260d28f8a31827f3bb407015dd66f1601ca3ad0fda`; immutable snapshot commit `1985fa829eb597acbc23685935c5f6929c078f78`. A/B artifact manifest hashes: `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` / `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`; source manifest hashes: `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` / `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`.
  - Review status: accepted by independent blind source review in commit `581b5bd2cb44981c4889eaf05ea739134e332b87` (pair section of `../independent-review-1204-1206-and-f14-2026-10-08.md`).
  - Finding dependency status: jump-input, flight-toggle, Player override and LivingEntity jump call chain are paired; downstream conditional jump power and attribute inputs are separately recorded in MC1204-1206-02/03.
  - Implementation handoff: blocked. Exact first changed release remains unknown within (1.20.4, 1.20.6].
- `MC1204-1206-03` submitted for independent blind source review: finding file `findings/MC1204-1206-03.md`, SHA-256 `ad2013db24d087f36d443c1b83ad1c571846274d95790f5ad5fbba3891dc6e1d`; immutable snapshot commit `467e5a80114239be1135b9ebc4bd2ee1d2b8bdc2`. A/B artifact manifest hashes: `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` / `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`; source manifest hashes: `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` / `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`.
  - Review status: accepted by independent blind source review in commit `581b5bd2cb44981c4889eaf05ea739134e332b87` (pair section of `../independent-review-1204-1206-and-f14-2026-10-08.md`).
  - Finding dependency status: player attribute builder, client attribute update receiver and jump-power consumer are paired; producer of a non-default value remains external, as stated in the finding.
  - Implementation handoff: blocked. Exact first changed release remains unknown within (1.20.4, 1.20.6].
- `MC1204-1206-06` submitted for independent blind source review: finding file `findings/MC1204-1206-06.md`, SHA-256 `2d6264950d59ddddaceca004e4c82dd53ab7773f6e894cf97d4f5add254ac6a0`; immutable snapshot commit `d6479a08528c346d9462c952cf8c13da8aaf4915`. A/B artifact manifest hashes: `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` / `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`; source manifest hashes: `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` / `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`.
  - Review status: accepted by independent blind source review in commit `581b5bd2cb44981c4889eaf05ea739134e332b87` (pair section of `../independent-review-1204-1206-and-f14-2026-10-08.md`).
  - Finding dependency status: syncable scale value, player dimension consumer and refreshDimensions path are paired; upstream producer and pre-refresh same-tick effects are outside this finding.
  - Implementation handoff: blocked. Exact first changed release remains unknown within (1.20.4, 1.20.6].
- `MC1204-1206-02` submitted for independent blind source review: finding file `findings/MC1204-1206-02.md`, SHA-256 `9e073eb434e750f6babac4cd6881b2cf38b1e4aad37523db0f62657b82071315`; immutable snapshot commit `b168d90c05e813d6dff2ccd97090dc1602d9d418`. Exact A/B artifact manifest hashes are `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` / `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`; source manifest hashes are `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` / `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`.
  - Review status: accepted by independent blind source review in commit `581b5bd2cb44981c4889eaf05ea739134e332b87` (pair section of `../independent-review-1204-1206-and-f14-2026-10-08.md`).
  - Finding dependency status: ordinary ground/fluid caller, Player override and movement write are paired; the conditional is stated in terms of calculated jump power. MC1204-1206-03 separately records the B jump-strength attribute input.
  - Implementation handoff: blocked. Exact first changed release remains unknown within (1.20.4, 1.20.6]; 1.20.5 source is not in this pair's published source roster.
- `MC1204-1206-05` submitted for independent blind source review: finding file `findings/MC1204-1206-05.md`, SHA-256 `a6296dcbedc454962625693587baf4d4a3a950f4453518178d6799bca92a26d4`; immutable snapshot commit `8797e40a04bbcb2de46fa4a689b181afb8a1a4ad`. Exact A/B artifact manifest hashes are `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` / `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`; source manifest hashes are `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` / `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`.
  - Review status: accepted by independent blind source review in commit `581b5bd2cb44981c4889eaf05ea739134e332b87` (pair section of `../independent-review-1204-1206-and-f14-2026-10-08.md`).
  - Finding dependency status: input-to-consumer path closed; wider S4 collision audit is out of the stated finding consequence.
  - Implementation handoff: blocked. Exact first changed release remains unknown within (1.20.4, 1.20.6]; 1.20.5 source is not in this pair's published source roster. The snapshot records a source finding, not an implementation-ready release boundary.
  - The pair remains partial; snapshot review does not alter active status or open-slice counts.
- `MC1204-1206-04` original snapshot, retained after REQUEST CHANGES: finding file `findings/MC1204-1206-04.md`, SHA-256 `405a295fd0ca7b7b00311f234745e748ff50acdd437ca9cc019bd4e746966918`; immutable snapshot commit `0251c08c91a20339c826a0f755078dc47f035f4b`. Exact A/B artifact manifest hashes are `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` / `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`; source manifest hashes are `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` / `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`.
  - Review status: REQUEST CHANGES in independent blind review commit `581b5bd2cb44981c4889eaf05ea739134e332b87` (pair section of `../independent-review-1204-1206-and-f14-2026-10-08.md`); omitted fall-flying consumption of the same gravity local. The original file and snapshot remain preserved at this commit.
  - Supersession event: corrected fall-flying source path, gates and math in the new snapshot below; pair-wide discovery remains partial.
  - Finding dependency status: gravity attribute builder, client attribute receiver, `Entity.getGravity`, ordinary travel, water/lava adjustments and fluid-falling helper were paired; fall-flying consumer is now added. Server-side producer provenance and broader drag closure remain open.
  - Implementation handoff: blocked. Exact first changed release remains unknown within (1.20.4, 1.20.6]; 1.20.5 source is not in this pair's published source roster.
- `MC1204-1206-04` corrected authoring snapshot v1 (superseded before review because the B `Entity.java` source hash was not included): path `findings/MC1204-1206-04.md`, SHA-256 `14ebb43b25b192ec65e32a37aff477c69f7391d81e7ceea667a90bd00e908189`, commit `c7af42ef63fb5b979e39342dfffea9907ac9e937`.
- `MC1204-1206-04` corrected snapshot prepared for focused independent blind re-review: path `findings/MC1204-1206-04.md`, SHA-256 `3354ad673e73ec35247699184eb26fcf82519f4dd2cf6963164d71c35add0a2f`; immutable snapshot commit `13cf725ab0ac943f78c2ec7e5851a473022edfb4`; Git blob `a0f45a5a2ae43c5f3bce2fcc4931c9ecb329f8d0`. A/B artifact manifest hashes: `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` / `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`; source manifest hashes: `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` / `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`.
  - Relevant source-file SHA-256: `LivingEntity.java` A/B `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` / `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; `Player.java` A/B `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` / `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`; `LocalPlayer.java` A/B `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` / `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`; B `Entity.java` `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`, `Attributes.java` `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e`, and `ClientPacketListener.java` `e121e998ec25211aaecf91fffd0ea699cebd47eddea9134efd3f2ffbef8eb7cd`.
  - Review status: focused re-review pending; no acceptance is claimed for this corrected snapshot.
  - Correction: added the fall-flying consumer, exact local-player activation gates and Player/LivingEntity dispatch, the gravity-dependent vertical term and its subsequent velocity/move order; excludes `FlyingAnimal` and does not infer upstream attribute/no-gravity producers.
  - The pair remains partial; this finding snapshot does not alter pair-wide open-slice counts.

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

- Coverage counts by status: pending 28; in-progress 20; compared-no-difference 8; findings 0; not-applicable 0; blocked 0. In-progress slices contain bounded source evidence but remain open for broader dependency closure.
- Slice IDs by status: pending (28): S1-tick-order, S3-ground-acceleration, S3-air-acceleration, S3-climbing, S3-water, S3-lava, S4-box-movement, S4-edge-probes, S4-grounding, S4-collision-query, S4-shapes, S4-callbacks, S4-fluid-contact, S5-landing-bounce, S5-friction-speed, S5-slowdown-contact, S5-climbables, S5-fluid-blocks, S5-pistons, S5-partial-shapes, S5-registrations, S5-modern-only, S6-effects, S6-resources, S7-position-writers, S7-riding, S7-closure, SCOPE-exclusions; in-progress (20): S1-sprint-gates, S1-jump-gates, S1-flight-tick, S2-pose-selection, S2-dimensions, S2-player-state, S2-flight-abilities, S3-travel-dispatch, S3-velocity-cutoffs, S3-gravity-drag, S3-ground-jump, S3-sprint-jump, S3-gliding, S3-effects-branch, S4-step-candidates, S6-attributes, S6-enchantments, S6-equipment, S6-server-boundary, S7-velocity-writers; compared-no-difference (8): S1-input-sampling, S1-input-motion, S1-local-tick-call-order, S2-sprint-consumers, S5-powder-snow-contact-state, S5-current-block-state-consumers, S7-client-motion-payloads, S7-player-knockback-push; findings (0): none; not-applicable (0): none; blocked (0): none.
- Required inventory status and evidence: all seven remain pending; exact source roots and hashes are verified, while method-level inventory and dependency closures remain open.
- Open dependencies: no unresolved source-preparation blocker; dependencies remain within 28 pending and 20 in-progress slices, including full method/caller closure, resource-backed block data and external attribute provenance.
- Unresolved gaps and limits: 28 slices remain pending and 20 remain in-progress. 8 bounded no-difference rows cover only: input sampling, input-to-relative-motion transform, LocalPlayer tick wrapper, sprint input readers, powder-snow contact-state query, current-block movement consumers, packet-to-player motion application, and direct player knockback/push response. Pair-wide member correspondence, semantic comparison and dependency closure are incomplete; no campaign-level equivalence or completion claim is made.
- Evidence/hash/correspondence audit: readiness, source and artifact manifest identities match the recorded publications; this resume reverified the used source hashes against both exact source manifests. Pair-specific method ranges/hashes are recorded for six findings and 8 bounded no-difference slices; remaining slices are not yet audited.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from source completion). No tests, client/game/TAS/Gym/server/Docker launches performed.
