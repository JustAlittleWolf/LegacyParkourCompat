# Discovery: 1.20.6 to 1.21.1

- Status: active
- Scope: direct client-player movement; older A = 1.20.6; newer B = 1.21.1
- Repository revision and start date: base 002137b227676caea77f6832b9f4c8d0b6200bff (main); started 2026-10-07. Workflow hardening 40c34f5 and campaign roster 2422192 cherry-picked onto this task branch.
- Task branch: feat/source-discovery-movement-source-1-20-6-1-21-1
- Selected naming namespace, CLI mode per side and alignment evidence: requested Mojmap for A and Mojmap for B, per the campaign rule for endpoints after 1.14.4. Both exact Mojmap ready markers, source/artifact/diagnostic hashes, provenance and exact run metadata are verified; shared namespace alignment is established.
- Source preparation owner / command / log / readiness marker: shared campaign source owner (identity not provided); worker does not run decompilation. Source owner published A and B via the recorded exact-batch Gradle commands; provenance and success-log hashes for both are listed below; ready/1.20.6/mojmap.ready.json and ready/1.21.1/mojmap.ready.json verified.
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1, Java 25.0.3+9-LTS, TinyRemapper 0.14.1, Mapping IO 0.9.1, Vineflower 1.12.0, ASM 9.10.1; heap 4G for B as recorded in provenance.
- Discovery author(s): delegated source-discovery worker on this task branch.
- Independent reviewer (must differ from discovery authors): pending coordinator assignment.

## Artifact manifest

- A 1.20.6: requested ID 1.20.6 in provenance exactRun.batchVersionIds and success log; resolved version metadata ID 1.20.6 confirmed by movement diagnostics and exact-metadata success message. Namespace Mojmap; source root ready/1.20.6/mojmap; original client.jar SHA-256 02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2; mapped client-mojmap.jar SHA-256 2add08d295773a0615ca595877f5fd055fa454d7c68fac56d6a4894a1e055284; client_mappings.txt SHA-256 27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745. Source manifest SHA-256 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 (5,329 entries); artifact manifest SHA-256 e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31; diagnostics SHA-256 558c501d2956cfa2445e9a75512dcf6c8ff7fec453c7b2f439af171162cab498; provenance ready/1.20.6/mojmap.provenance.json SHA-256 ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8 records exact command and batch IDs; success log staging/mojmap-1.20.1-to-1.20.6-3e08bcae66a145b296f7f85b12623999/gradle.full.log SHA-256 e5acf99c0bf01e2a65818cea95da4873b5f8a3c610f795f887c7184216b160fe. Toolchain Gradle 9.7.1, Java 25.0.3+9-LTS, TinyRemapper 0.14.1, Mapping IO 0.9.1, Vineflower 1.12.0, ASM 9.10.1. The batch also resolved 1.20.1, 1.20.2 and 1.20.4; only the exact 1.20.6 tree is used for this pair.
- B 1.21.1: requested ID 1.21.1 in provenance exactRun.batchVersionIds and success log; resolved version metadata ID 1.21.1 confirmed by movement diagnostics and exact-metadata success message. Namespace Mojmap; source root ready/1.21.1/mojmap; original client.jar SHA-256 499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99; mapped client-mojmap.jar SHA-256 6b36d2ccc99e7eeb98e942b6dc093388d66eb6ede06b64238f35a0e26b092abe; client_mappings.txt SHA-256 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec. Source manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 (5,363 entries); artifact manifest SHA-256 09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486; diagnostics SHA-256 44c184cd1385e5b23991b2698ff0ae9f1e62a9491de8dcac27e5a3f9af88cd4f; provenance ready/1.21.1/mojmap.provenance.json SHA-256 6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9 records exact command and batch IDs; success log staging/mojmap-1.21.1-to-1.21.5-cd5a99cb1024417c9d370097c886a131/gradle.full.log SHA-256 610191fd377703263d0a7040f9874f56a167712bc25c440f35c58a8a44fa3f4b. The batch also resolved 1.21.3, 1.21.4 and 1.21.5; only the exact 1.21.1 tree is used for this pair.
- No source tree is treated as ready based on directory presence. Verify exact requested/resolved IDs, namespace, cited hashes, method bodies and diagnostics from each marker before evidence comparison.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending source-only comparison.
- Evidence inventory and finding IDs included at freeze: freeze not yet performed; two source-confirmed bounded findings are recorded; the L2 snapshot awaits independent review, and 15 planning slices remain pending while 8 are in progress.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; neither implementation nor wiki materials have been inspected.
- Source/mapping hashes covered by freeze: freeze pending; exact A/B source/artifact/diagnostic hashes are recorded in the artifact manifest, with cited source hashes for L2 and L5 in their findings.

## Correspondence and call order

Partial correspondence established for the first bounded movement paths: `LocalPlayer#tick` -> `LocalPlayer#aiStep` -> `LivingEntity#aiStep` -> `Player#travel` -> `LivingEntity#travel`, with `Entity#moveRelative` and `Entity#move` as movement helpers. Paired method line ranges and the limited observed deltas are recorded in the coverage rows/finding; full tick-sequence, callback, collision and external-input closure remain in progress.

## Required source inventories

Both exact source trees are verified. Required inventories remain pending until every bounded paired slice and dependency has evidence.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=I1-I4,L1-L6; evidence=partial paired input/tick/travel/jump paths; full tick graph, pre/post-travel callers, callbacks and all reachable branches remain open.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=P1-P4,I1-I4,L1-L6,C1-C5,X1-X4; evidence=partial input/travel/jump and Soul Speed writer/consumer paths; pose, dimensions, collision state, timers and many direct predicates remain open.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=C1-C5,B1-B8; evidence=callback and movement call sites partially indexed; collision bodies, shape providers, registries and neighbor chains remain open.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=B1-B8,L5; evidence=Depth Strider water path and Soul Speed tag/block factor checked; remaining block/fluid registries, shapes, callbacks and resources remain open.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=L2,L5,L6,E1-E4; evidence=bounded Depth Strider and Soul Speed paths plus player base/sneak attributes checked; other effect, equipment, modifier and data chains remain open.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=X1-X4,B7; evidence=attribute packet consumer indexed; correction, push, piston and mount/dismount paths remain open.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=campaign exclusions stated; full movement-predicate reads and direct player velocity/impulse/knockback separation still need audit.

## Coverage ledger

Create bounded pair-specific slices for every discovered method/behavior and dependency. The rows below are planning units; split them wherever source bodies contain separately scoped behavior. Keep each unit pending until both-side method ranges, producer/consumer links and dependency closure are recorded.

### Slice I1 - local input sampling, input fields and keyboard

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `Input#tick(boolean,float)`, `getMoveVector()`, `hasForwardImpulse()`, `KeyboardInput#calculateImpulse(boolean,boolean)`, and `KeyboardInput#tick(boolean,float)`; `LocalPlayer#aiStep` calls the active input object's tick before using its movement vector.
- A evidence: `ready/1.20.6/mojmap/net/minecraft/client/player/Input.java`, lines 15-24, SHA-256 `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb`; `KeyboardInput.java`, lines 12-34, SHA-256 `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0`.
- B evidence: `ready/1.21.1/mojmap/net/minecraft/client/player/Input.java`, lines 15-24, SHA-256 `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb`; `KeyboardInput.java`, lines 12-34, SHA-256 `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0`.
- State producers/writers -> consumers/readers: input device state -> `KeyboardInput#calculateImpulse` -> `leftImpulse`/`forwardImpulse`/`jumping`/`shiftKeyDown` -> `Input#getMoveVector` and sprint predicates -> `LivingEntity#travel`/`moveRelative`; the inspected classes are byte-identical between A and B.
- Parent slices / dependencies / closure evidence: I2 local player tick/aiStep call order remains pending; this row closes only the input sampling classes and their direct fields.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): corresponding A/B class hashes are identical for both input classes; the bounded input behavior is unchanged. This does not imply later travel behavior is unchanged.
- Finding IDs or checked absence/replacement path: checked absence of source change in both complete source classes by matching SHA-256; no finding.
### Slice I2 - local player tick order, superclass tick, travel dispatch and repeated movement calls

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer#tick`, `LocalPlayer#aiStep` from portal processing through `super.aiStep()`, `Player#tick`, `Player#travel`, `LivingEntity#aiStep` and the reachable local-player movement dispatch order. Bounded call order is unchanged; two `LocalPlayer#aiStep` expressions were traced separately to their producer/effect paths.
- A evidence: `LocalPlayer.java::tick()` lines 190-209 and `aiStep()` lines 648-812, SHA-256 `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`; `Player.java::tick()` lines 244-315, SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`; `Player.java::travel(Vec3)` lines 1473-1495; `LivingEntity.java::aiStep()` lines 2591-2715, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`.
- B evidence: `LocalPlayer.java::tick()` lines 191-210 and `aiStep()` lines 644-809, SHA-256 `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583`; `Player.java::tick()` lines 249-323, SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`; `Player.java::travel(Vec3)` lines 1456-1478; `LivingEntity.java::aiStep()` lines 2586-2710, SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`.
- State producers/writers -> consumers/readers: `LocalPlayer#tick` -> `LocalPlayer#aiStep` -> `LivingEntity#aiStep` -> `Player#travel` -> `LivingEntity#travel` -> `Entity#moveRelative`/`Entity#move`; `LocalPlayer#move` dispatch remains paired. `LocalPlayer#aiStep` changes portal visual-state update and the crouch input factor source. Portal helpers write only spinning-effect/UI and portal-process/cooldown state; the cooldown helper remains called at the same point. The crouch factor routes to `Input#tick`; Swift Sneak modifier/default and levels 0-3 produce the same float factor on both sides. `Player#tick` adds a grace timer for current impulse damage context; traced consumers reset damage/explosion context and do not write movement velocity or position, so that state is excluded under INV-EXCLUSIONS.
- Parent slices / dependencies / closure evidence: I1 input fields; I3 yaw/normalization; I4 sprint; L1-L6 travel branch comparison; X1 external movement writers remain separate.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the local player tick/travel call order and repeated movement dispatch are unchanged. The two changed portal expressions affect transition presentation/cooldown bookkeeping rather than player motion. The changed sneak factor is numerically equal for vanilla Swift Sneak levels 0-3; the added impulse grace timer is damage-context state and out of scope. The water travel formula difference is separately recorded under L5.
- Finding IDs or checked absence/replacement path: no tick-order finding; the water formula finding is linked only to L5.
### Slice I3 - yaw-to-motion conversion, normalization and input scaling

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `Input#getMoveVector()` output -> `Entity#moveRelative(float,Vec3)` -> `Entity#getInputVector(Vec3,float,float)`, including zero-length threshold, length-squared normalization boundary, scaling, yaw sin/cos and component order; checked paired `LivingEntity#getFrictionInfluencedSpeed(float)` formula.
- A evidence: `ready/1.20.6/mojmap/net/minecraft/world/entity/Entity.java`, `moveRelative` lines 1311-1314 and `getInputVector` lines 1316-1326, SHA-256 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`; `LivingEntity#getFrictionInfluencedSpeed(float)` lines 2314-2316, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`.
- B evidence: `ready/1.21.1/mojmap/net/minecraft/world/entity/Entity.java`, `moveRelative` lines 1350-1353 and `getInputVector` lines 1355-1365, SHA-256 `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`; `LivingEntity#getFrictionInfluencedSpeed(float)` lines 2297-2299, SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`.
- State producers/writers -> consumers/readers: `Input` impulses -> `Vec3` movement input -> `getInputVector` normalization/rotation using entity yaw -> `Entity#moveRelative` adds to delta movement; ground friction is supplied by travel and consumed by `getFrictionInfluencedSpeed`. Compared bodies retain the same constants, guards, float conversions and expression order.
- Parent slices / dependencies / closure evidence: I1 input fields and I2 call order closed; L1/L2 travel and friction branches remain separate for full closure.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the paired yaw conversion, normalization, and ground/flying speed selection expressions are identical, including `1.0E-7`, `0.21600002F`, and sin/cos operation order. This bounded input-to-motion slice has no source difference.
- Finding IDs or checked absence/replacement path: checked absence in paired method bodies; no finding.
### Slice I4 - sprint start and stop gates

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXCLUSIONS, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer#aiStep` sprint start/stop section; `canStartSprinting`, `vehicleCanSprint`, `hasEnoughImpulseToStartSprinting`, `hasEnoughFoodToStartSprinting`, `isMoving`, `isMovingSlowly`, and `Player#canSprint`.
- A evidence: `ready/1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep()` lines 693-718 and sprint helpers lines 1011-1041, SHA-256 `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`; `ready/1.20.6/mojmap/net/minecraft/world/entity/player/Player.java::canSprint()`, lines 2112-2114, SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`.
- B evidence: `ready/1.21.1/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep()` lines 690-715 and sprint helpers lines 1014-1044, SHA-256 `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583`; `ready/1.21.1/mojmap/net/minecraft/world/entity/player/Player.java::canSprint()`, lines 2114-2116, SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`.
- State producers/writers -> consumers/readers: keyboard input -> forward impulse/forward-impulse predicate -> sprint thresholds (`0.8` on land, `hasForwardImpulse()` underwater) -> `setSprinting`; crouch/use-item/blindness/fall-flying/passenger and ground/fluid/collision predicates gate start/stop. `Player#canSprint` returns true; a passenger gate reads its vehicle's `canSprint` and local-control status. Food level is read directly as a sprint predicate input; its producer remains explicitly excluded. Vehicle physics remain in X3.
- Parent slices / dependencies / closure evidence: I1 input sampling and I2 tick call order; X3 passenger/mount movement remains open as a separate external influence.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the paired guards, thresholds, tick counters, sprint writes and Player predicate are the same. Vanilla food level is only read as the existing gate; its production is out of scope. No sprint-gate source difference found.
- Finding IDs or checked absence/replacement path: checked paired sprint-gate bodies; no finding.
### Slice P1 - pose selection, dimensions, eye height, resize timing and collision

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer#aiStep` crouch decision; `Player#updatePlayerPose` and `canPlayerFitWithinBlocksAndEntitiesWhen`; inherited `LivingEntity#getDimensions(Pose)`; `Entity#setPose` and `refreshDimensions` resize/effective-eye-height sequence. Collision-shape providers remain delegated to C4/C5.
- A evidence: `ready/1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java`, lines 661-665, SHA-256 `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`; `Player.java`, `updatePlayerPose()`/`canPlayerFitWithinBlocksAndEntitiesWhen(Pose)` lines 393-425, SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`; `LivingEntity.java#getDimensions(Pose)` lines 3240-3246, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; `Entity.java#setPose(Pose)` lines 355-357 and `refreshDimensions()` lines 2733-2755, SHA-256 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`.
- B evidence: `ready/1.21.1/mojmap/net/minecraft/client/player/LocalPlayer.java`, lines 658-662, SHA-256 `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583`; `Player.java`, `updatePlayerPose()`/`canPlayerFitWithinBlocksAndEntitiesWhen(Pose)` lines 401-433, SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`; `LivingEntity.java#getDimensions(Pose)` lines 3242-3248, SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`; `Entity.java#setPose(Pose)` lines 356-358 and `refreshDimensions()` lines 2762-2778, SHA-256 `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`.
- State producers/writers -> consumers/readers: input shift/flying/swimming/sleeping/fall-flying/auto-spin and fit predicates -> pose choice -> `setPose`; `LivingEntity` pose dimensions -> dimensions/eyeHeight assignment -> `reapplyPosition`; pose-fit test uses `Level#noCollision`. The new `Entity#fudgePositionAfterSizeChange` path is guarded by `!(this instanceof Player)`, so it does not change the player resize path. `Player#tick` still refreshes dimensions when pose changes.
- Parent slices / dependencies / closure evidence: I2 tick order; C4 world collision query and C5 callback/neighbor path remain open dependencies for the fit predicate.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): player pose precedence, dimensions scaling, eye-height update and resize order match. B's new free-position resize fallback is for non-player entities only. The player fit predicate's underlying collision/shape call chain is not yet closed, so the whole P1 slice remains in progress.
- Finding IDs or checked absence/replacement path: no player pose/dimension delta identified in inspected bodies; pending C4/C5 collision dependency.
### Slice P2 - abilities, flight state

- Inventory ID(s): INV-STATE, INV-TICK, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: local ability-toggle logic in `LocalPlayer#aiStep`; shared `Abilities` fields/default and flying-speed access; `Player#travel` flying branch; `Player#getFlyingSpeed` and `getBlockSpeedFactor`; clientbound ability packet consumer identified, with full external packet writer/call-chain closure assigned to X1.
- A evidence: `ready/1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java`, flight toggle lines 721-741 and ground-triggered flight reset lines 808-810, SHA-256 `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`; `Player.java#travel(Vec3)` lines 1473-1495, `getBlockSpeedFactor()` lines 1978-1980 and `getFlyingSpeed()` lines 2117-2122, SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`; `Abilities.java`, full class, SHA-256 `a4f952ada7bc3b21406feaf13c171bfa22d36b01e265e3b987612f28b5609edc`.
- B evidence: `ready/1.21.1/mojmap/net/minecraft/client/player/LocalPlayer.java`, flight toggle lines 718-738 and ground-triggered flight reset lines 805-807, SHA-256 `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583`; `Player.java#travel(Vec3)` lines 1456-1478, `getBlockSpeedFactor()` lines 1973-1975 and `getFlyingSpeed()` lines 2119-2124, SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`; `Abilities.java`, full class, SHA-256 `a4f952ada7bc3b21406feaf13c171bfa22d36b01e265e3b987612f28b5609edc`.
- State producers/writers -> consumers/readers: local jump/fly-toggle input and ground reset -> `Abilities.flying` -> crouch/pose and Player travel/flying-speed/block-factor consumers. Serverbound ability update is sent by the local client; `ClientPacketListener#handlePlayerAbilities` consumes the server-authoritative ability packet. A/B shared Abilities class and local flight/travel expressions are equal. Attribute `FLYING_SPEED` exists in both version sources; Player's flight speed reads `Abilities#getFlyingSpeed` on both.
- Parent slices / dependencies / closure evidence: I2 tick order; P1 pose change; X1 server-supplied position/ability updates remains open for packet writer/consumer comparison.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): inspected flight-toggle, reset, travel and speed expressions match. The local flight state can also be overwritten from the server; its packet application/update producer must be closed with X1 before this external state slice is terminal.
- Finding IDs or checked absence/replacement path: no local flight-path source delta identified; X1 external ability update closure pending.
### Slice P3 - sprint gates and their vanilla state consumers; exclude hunger

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: I4 sprint entry/exit gates plus `LivingEntity#setSprinting(boolean)` modifier lifecycle, sprint modifier registration, and `Player#getSpeed()` / `Player#aiStep` movement-speed read.
- A evidence: I4's `LocalPlayer` sprint gate ranges; `ready/1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, sprint modifier declaration lines 134-136 and `setSprinting` lines 2008-2015, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; `Player.java#getSpeed()` lines 1511-1513 and `aiStep()` movement speed read near lines 533-534, SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`.
- B evidence: I4's `LocalPlayer` sprint gate ranges; `ready/1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, sprint modifier declaration lines 140-142 and `setSprinting` lines 1994-2001, SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`; `Player.java#getSpeed()` lines 1494-1496 and `aiStep()` movement speed read near lines 535-536, SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`.
- State producers/writers -> consumers/readers: sprint gate sets the sprint flag -> `LivingEntity#setSprinting` removes/re-adds a transient `MOVEMENT_SPEED` modifier of `0.3F` with `ADD_MULTIPLIED_TOTAL` -> `Player#getSpeed`/attribute value -> travel speed. Gate reads food level, blindness, water/ground/impulse, item-use and passenger status; the food system is excluded while the direct vanilla food read stays as a predicate.
- Parent slices / dependencies / closure evidence: I4 gate comparison; L1/L2 movement travel and friction branches remain separate; food/regeneration producers are intentionally outside scope.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): paired sprint modifier amount, operation, replace-before-add order and speed consumer are unchanged. The new ResourceLocation identifier in B replaces A's UUID for the same single sprint modifier and `setSprinting` removes that identifier before each optional addition. Sprinting contributes the same 1.3 total multiplier. No movement delta found in the sprint-speed path.
- Finding IDs or checked absence/replacement path: checked paired sprint modifier writers and consumers; no finding.
### Slice P4 - active-item state, edge sneaking, defaults, initialization, updates and reset timing

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer#aiStep` active-item slowdown and auto-jump/reset state; `LivingEntity#isUsingItem`; `LocalPlayer#move`; `Player#maybeBackOffFromEdge`; input shift state. `LivingEntity` use-item start/update timing is an E3 dependency because item use-duration APIs changed between endpoints.
- A evidence: `ready/1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java`, item slowdown lines 669-673 and `move(MoverType,Vec3)` lines 878-883, SHA-256 `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`; `Player.java#maybeBackOffFromEdge(Vec3,MoverType)` lines 1067-1104, SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`; `LivingEntity#isUsingItem()` lines 2970-2972, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`.
- B evidence: `ready/1.21.1/mojmap/net/minecraft/client/player/LocalPlayer.java`, item slowdown lines 666-670 and `move(MoverType,Vec3)` lines 881-886, SHA-256 `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583`; `Player.java#maybeBackOffFromEdge(Vec3,MoverType)` lines 1070-1107, SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`; `LivingEntity#isUsingItem()` lines 2967-2969, SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`.
- State producers/writers -> consumers/readers: synchronized using-item flag -> `LocalPlayer#aiStep` multiplies left/forward impulses by `0.2F` and clears sprint trigger when not a passenger. Shift input -> player discrete/staying-on-ground predicate -> `Entity#move` -> `Player#maybeBackOffFromEdge` reduction loops -> collision support queries. Item-use lifecycle also reads active item and remaining-use duration; exact component-driven duration dependencies are E3. The paired direct slowdown and edge-backoff constants/guards inspected so far match.
- Parent slices / dependencies / closure evidence: I1 input state; I2 tick order; C2/C4 edge-backoff support/collision query; E3 item use duration/stack behavior; X1 server-supplied use/shift state remain open dependencies.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): the active-item slowdown expression and local move override match. The edge-backoff source follows the same 0.05 decrement loops, but closure depends on its full `canFallAtLeast` collision query and server-supplied sneaking/input path. The use-item active state consumer matches, while its producer timing remains coupled to the changed item-use API.
- Finding IDs or checked absence/replacement path: no direct slowdown expression delta found; C2/C4/E3/X1 closure pending.
### Slice L1 - travel dispatch and ground

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: local-player `LivingEntity#aiStep` dispatch into `Player#travel(Vec3)` and `LivingEntity#travel(Vec3)`; paired non-water movement branches and ground travel dispatch. The water-only formula change is isolated under L5.
- A evidence: `ready/1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java#aiStep()` lines 2591-2715 and `travel(Vec3)` lines 2104-2233, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; `Player.java#travel(Vec3)` lines 1473-1495, SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`.
- B evidence: `ready/1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java#aiStep()` lines 2586-2710 and `travel(Vec3)` lines 2091-2216, SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`; `Player.java#travel(Vec3)` lines 1456-1478, SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`.
- State producers/writers -> consumers/readers: controlled local-player tick -> `LivingEntity#aiStep` -> `Player#travel` -> `LivingEntity#travel`; land/air movement reads movement input, gravity, ground state and block friction, then writes delta movement through relative movement and collision movement. A/B travel call order and every non-water branch expression are paired; the changed water factor is addressed separately in L5.
- Parent slices / dependencies / closure evidence: I2 dispatch call order; I3 yaw/input conversion; L2 friction/gravity/drag and L5 water factor are separate slices.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for the same local-control and non-water ground/air branch preconditions, dispatch order, jump/travel call placement and non-water travel expressions match. The water enchantment arithmetic is not counted as equivalence and is recorded under L5.
- Finding IDs or checked absence/replacement path: no non-water dispatch/ground travel finding; water finding linked to L5 only.
### Slice L2 - friction, gravity, drag and velocity thresholds

- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT, INV-MODIFIERS, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: paired `LivingEntity#travel` friction/gravity/drag paths, `Entity#move` movement thresholds and post-movement block-speed multiplier, and the player Soul Speed block-speed factor producer/consumer chain. The first local entry step is source-confirmed to use the baseline attribute; later packet latency remains variable.
- A evidence: `ready/1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java#travel(Vec3)` lines 2104-2233, `getFrictionInfluencedSpeed(float)` lines 2314-2317, `getBlockSpeedFactor()` and `onSoulSpeedBlock()` lines 489-496, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; `Player.java` lines 975-977 and 1978-1980, SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`; `Entity#move` stuck-multiplier threshold, collided-delta threshold and block multiplier lines 610-706, SHA-256 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`; `limitPistonMovement` tiny-motion threshold line 842.
- B evidence: `ready/1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java#travel(Vec3)` lines 2091-2216, `getFrictionInfluencedSpeed(float)` lines 2297-2300, `getBlockSpeedFactor()` lines 485-488 and server-only location-change callback lines 433-439/514-516, SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`; `Entity#move` stuck-multiplier threshold, collided-delta threshold and block multiplier lines 611-707, SHA-256 `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`; `limitPistonMovement` tiny-motion threshold line 843. Movement-efficiency default, Soul Speed effect registration, modifier application and attribute packet route are recorded in [soul-speed-first-entry-block-friction](findings/soul-speed-first-entry-block-friction.md).
- State producers/writers -> consumers/readers: A local player pose/block tag/equipped Soul Speed level -> direct `getBlockSpeedFactor()` -> post-movement horizontal delta multiplier. B server-side location effect -> syncable `MOVEMENT_EFFICIENCY` -> local `getBlockSpeedFactor()` -> same consumer; ServerEntity sends dirty syncable attributes to trackers and, for a ServerPlayer entity, also to its own connection; ClientPacketListener applies them. The paired travel bodies differ only in the water branch recorded under L5; the friction-speed helper and remaining gravity/drag expressions match. The inspected `Entity#move` movement thresholds are unchanged; the ground-support helper rename in B preserves the same `onGround` write and `checkSupportingBlock` call.
- Parent slices / dependencies / closure evidence: I2 call order; L1 paired non-water travel; E2 Soul Speed registration/data; X1 clientbound attribute updates. The Soul Speed tag matches between jars and Soul Sand's base speed factor is `0.4F` on both sides.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): the consumer changes from a local block/enchantment predicate to an attribute set by a server-only location effect. A local non-flying/non-fall-flying player entering Soul Sand with Soul Speed can use the `0.4F` base factor in B while its syncable modifier is absent or stale, whereas A directly returns `1.0F`. Source proves the local first-entry ordering: `LocalPlayer#tick()` completes its movement in `super.tick()` before sending the changed position. The server-only location effect therefore cannot set the owning client attribute before that first entry move; subsequent attribute packet timing can vary. The paired stuck-speed (`> 1.0E-7`), collided-delta (`> 1.0E-7`) and piston tiny-motion (`<= 1.0E-7`) thresholds and operation order match; external velocity producers and consumers remain open under X1/X2/X4.
- Finding IDs or checked absence/replacement path: source-confirmed [soul-speed-first-entry-block-friction](findings/soul-speed-first-entry-block-friction.md); external velocity-source closure pending.
### Slice L3 - jump power, sprint-jump impulse and jump gates

- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity#aiStep` fluid/ground jump gate, `getJumpPower(float)`, `getJumpBoostPower()`, `jumpFromGround()`, `jumpInLiquid(TagKey<Fluid>)`, `Entity#getBlockJumpFactor()`, and `Player#jumpFromGround()`.
- A evidence: `ready/1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, jump helpers lines 2061-2081 and aiStep gate lines 2643-2664, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; `Entity.java#getBlockJumpFactor()` lines 820-825, SHA-256 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`; `Player.java#jumpFromGround()` lines 1462-1470, SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`; `Attributes.java` line 46 sets syncable `JUMP_STRENGTH` default `0.42F`, SHA-256 `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e`.
- B evidence: `ready/1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, jump helpers lines 2047-2068 and aiStep gate lines 2638-2659, SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`; `Entity.java#getBlockJumpFactor()` lines 821-826, SHA-256 `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`; `Player.java#jumpFromGround()` lines 1445-1453, SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`; `Attributes.java` line 54 sets syncable `JUMP_STRENGTH` default `0.42F`, SHA-256 `a9a19f556bb77fc218b5b2f4831eb6b285f4398c24fa2a3519009ca8dad2db7c`.
- State producers/writers -> consumers/readers: the same `jumping`, fluid height, `onGround`, `noJumpDelay`, sprint state, `JUMP_STRENGTH`, block jump factor, and Jump Boost amplifier feed the same gate and formula; the same Y velocity write and sprint-horizontal impulse follow. `jumpInLiquid` and `getBlockJumpFactor` bodies are byte-for-byte equal; the block-property default jump factor is `1.0F`, and Honey Block's `0.5F` jump factor is identical in both `Blocks.java` sources.
- Parent slices / dependencies / closure evidence: I1/I2 input and tick dispatch; L1 travel dispatch; L6 block-property inventory remains open for broader world coverage. `LivingEntity#jumpFromGround` changes visibility from protected to public in B, but its body is identical and the Player override/call path is unchanged.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for the same player movement/effect/block/attribute inputs, jump gates, jump-power arithmetic and operation order, threshold, vertical write, sprint impulse and player exhaustion/stat calls match. The visibility change does not alter the paired vanilla player path. No broader block or modifier inventory is inferred from this bounded comparison.
- Finding IDs or checked absence/replacement path: no L3 player jump-path finding; broader block and modifier inputs remain with their separate inventories.

### Slice L4 - climbing clamps and movement callbacks

- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: paired `LivingEntity#onClimbable()`, the ladder/powder-snow clamp in `handleRelativeFrictionAndCalculateMovement(Vec3,float)` and `handleOnClimbable(Vec3)`, `LivingEntity#onChangedBlock` producers/call sites, and `Entity#tryCheckInsideBlocks`/`checkInsideBlocks` player movement dispatch. Block-specific callbacks remain assigned to B4/B6/B8.
- A evidence: `ready/1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, `onClimbable()` lines 1540-1557, `handleRelativeFrictionAndCalculateMovement` lines 2266-2281 and `handleOnClimbable` lines 2297-2314, block-position tick callback lines 412-419, `checkFallDamage` lines 309-338 without a block-change callback, and `onChangedBlock(BlockPos)` lines 557-568; SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`. `Entity#tryCheckInsideBlocks`/`checkInsideBlocks` lines 757-999 use the same wrapper and box iteration in both versions. `ServerPlayer#onInsideBlock(BlockState)` lines 472-474 triggers `ENTER_BLOCK`; class SHA-256 `259e6abf2549771ab14a14ddfebd3d4967c953f20666cb8075c46f4faaffdba3`.
- B evidence: `ready/1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, `onClimbable()` lines 1516-1533, `handleRelativeFrictionAndCalculateMovement` lines 2250-2265 and `handleOnClimbable` lines 2280-2297, block-position tick callback lines 433-439, `checkFallDamage` landing callback lines 327-355, and `onChangedBlock(ServerLevel,BlockPos)` lines 514-516; SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`. `Entity#tryCheckInsideBlocks`/`checkInsideBlocks` lines 758-1034 preserve the wrapper, box iteration and callback order. `ServerPlayer#onInsideBlock(BlockState)` lines 479-481 triggers the same `ENTER_BLOCK` criterion; class SHA-256 `ccdac581288507aae64bd844beefe9ac2e174f316458e181f23ce5631a4b1ba7`.
- State producers/writers -> consumers/readers: the climbable tag/trapdoor test and movement clamp are the same; A invokes its server tick callback when `lastPos` changes and that callback checks Frost Walker/Soul Speed. B also invokes a `ServerLevel` location-changed effect from server `checkFallDamage` on a landing with positive fall distance, then can invoke it from the server block-position callback; the callback delegates to registered location-changed enchantment effects. The shared `Entity#move` caller invokes `tryCheckInsideBlocks` before block-speed scaling; the paired checked-box iteration calls `BlockState#entityInside` then `onInsideBlock` in the same order. ServerPlayer retains its `ENTER_BLOCK` criterion body; only method visibility changes to public.
- Parent slices / dependencies / closure evidence: L1 paired travel; L2 source-confirmed first-entry Soul Speed finding; E2 effect registration; X1 attribute sync; B4/B6/B8 block/fluid callback and shape inventories. `onClimbable()` method bodies match byte-for-byte. The shared location-effect path is recorded in [soul-speed-first-entry-block-friction](findings/soul-speed-first-entry-block-friction.md); block-specific callback effects remain open in their inventories.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): the ordinary ladder/trapdoor reader and travel clamp show no source delta. B adds the server-only landing/location-effect path already bounded in L2. The generic `Entity` inside-block dispatcher and ServerPlayer criterion callback preserve order and behavior for this player path; block-specific `entityInside` movement effects remain open under B4/B6/B8, so the slice stays in progress.
- Finding IDs or checked absence/replacement path: no independent L4 finding yet; shared source-confirmed finding is recorded under L2; block-specific `entityInside` movement effects remain open under B4/B6/B8.

### Slice L5 - water

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: paired `LivingEntity#travel(Vec3)` water branch, from the water/fluids/stand-on-fluid guard through the `$$5` horizontal multiplier update and following move/drag calls; input is sprinting, on ground, depth strider III, no Dolphin's Grace.
- A evidence: `ready/1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#travel(Vec3)`, lines 2104-2129, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; producer `ready/1.20.6/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java::getDepthStrider(LivingEntity)`, lines 184-186, SHA-256 `e0b4c410e0aa9499d67b38f57b34467628e882be960dce6958d4bc8ef717a6b6`.
- B evidence: `ready/1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#travel(Vec3)`, lines 2091-2127, SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`; producer/default `Enchantments.java` lines 344-368 (SHA-256 `7848a2cd677aa0700434f85de37d8a0c47597741d2d1f26c8dc270f73d65b266`), `Attributes.java` lines 101-103 (SHA-256 `a9a19f556bb77fc218b5b2f4831eb6b285f4398c24fa2a3519009ca8dad2db7c`), and `LevelBasedValue.java` lines 126-138 (SHA-256 `f9a0bed1d7693606f8657fadd4a59d95eea9955fcc036687e7a9dd08a8b360e3`).
- State producers/writers -> consumers/readers: A equipment enchantment level -> A helper -> `$$7` -> water formula -> `$$5` drag multiplier; B equipped Depth Strider attribute modifier -> entity attribute value -> `$$7` -> water formula -> `$$5` drag multiplier. B `ItemStack#forEachModifier` delegates to `EnchantmentHelper#forEachModifier`, and the equipment-change path applies/removes the returned modifier; exact sources and hashes are cited in the finding.
- Parent slices / dependencies / closure evidence: I2/L1 travel dispatch; E2 Depth Strider registration/level bounds. The reachable branch guards and producer chain are closed for this finding; other water branches and B6 block/fluid data inventory remain separate.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): source-confirmed binary32 difference for the stated vanilla-reachable Depth Strider III preconditions; see [water-depth-strider-rounding](findings/water-depth-strider-rounding.md). The compared water branch otherwise preserves its call order. No runtime trace is claimed.
- Finding IDs or checked absence/replacement path: `water-depth-strider-rounding`.
### Slice L6 - movement speed, jump and gravity attributes

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: player movement-speed attribute creation/readback, gravity and jump-strength defaults/consumers, and the Swift Sneak input-speed producer migration in `LocalPlayer#aiStep`.
- A evidence: `Attributes.java` lines 36-61 declares `FLYING_SPEED=0.4F`, `GRAVITY=0.08`, `JUMP_STRENGTH=0.42F`, and generic `MOVEMENT_SPEED=0.7F`, SHA-256 `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e`; `Entity#getGravity()` lines 1104-1107, SHA-256 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`; `Player#createAttributes()` lines 220-230, speed write in `aiStep` line 533, load-time base writer line 779, `getSpeed()` lines 1511-1513 and `getFlyingSpeed()` lines 2117-2123, SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`; `LocalPlayer#aiStep` sneak factor lines 666-667, SHA-256 `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`; `EnchantmentHelper#getSneakingSpeedBonus` lines 168-169, SHA-256 `e0b4c410e0aa9499d67b38f57b34467628e882be960dce6958d4bc8ef717a6b6`.
- B evidence: `Attributes.java` lines 44-75 declares `FLYING_SPEED=0.4`, `GRAVITY=0.08`, `JUMP_STRENGTH=0.42F`, generic `MOVEMENT_SPEED=0.7`, and `SNEAKING_SPEED=0.3` at lines 86-88, SHA-256 `a9a19f556bb77fc218b5b2f4831eb6b285f4398c24fa2a3519009ca8dad2db7c`; `Entity#getGravity()` lines 1143-1146, SHA-256 `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`; `Player#createAttributes()` lines 221-235, speed write in `aiStep` line 535, load-time base writer line 785, `getSpeed()` lines 1494-1496 and `getFlyingSpeed()` lines 2119-2125, SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`; `LocalPlayer#aiStep` attribute read lines 663-664, SHA-256 `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583`; Swift Sneak `ATTRIBUTES` registration lines 561-580 in `Enchantments.java`, SHA-256 `7848a2cd677aa0700434f85de37d8a0c47597741d2d1f26c8dc270f73d65b266`, using `LevelBasedValue.perLevel(0.15F)` whose definition/calculation is lines 30-36/126-138, SHA-256 `f9a0bed1d7693606f8657fadd4a59d95eea9955fcc036687e7a9dd08a8b360e3`; shared equipment modifier application via `ItemStack#forEachModifier(EquipmentSlot,BiConsumer)` lines 915-923, SHA-256 `15094cc5a115bbbc81c550fafcfba51451fcc7afed2ad374ca1029ba1d1db687`, `EnchantmentHelper#forEachModifier` lines 311-316, SHA-256 `9e2f9acd5bc6a8f3b5292d720db81464954d41c9afb635f1a7c6cb31d5a2bf84`, and `LivingEntity` equipment update lines 2488-2502, B `LivingEntity.java` SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`.
- State producers/writers -> consumers/readers: player `MOVEMENT_SPEED` is created at `0.1F` on both sides, then set from the same abilities walking-speed value and read/cast by the same `Player#getSpeed()`; `Entity#getGravity()` has the same body and `GRAVITY`/`JUMP_STRENGTH` defaults are equal. A's `0.3F + level * 0.15F` Swift Sneak input factor is replaced by B's `SNEAKING_SPEED` default plus the registered additive attribute modifier; after the same float cast the reachable levels 0-3 yield `0.30000001192092896`, `0.45000001788139343`, `0.6000000238418579`, and `0.75` on both sides. Related water and Soul Speed efficiency attributes are separately covered by L5 and L2.
- Parent slices / dependencies / closure evidence: I1 input fields; I2 local player tick; I3 movement input conversion; L2 gravity/travel and Soul Speed; L3 jump power; L5 water efficiency; E2 Swift Sneak registration. Other effect/enchantment writers of movement attributes remain in E1-E4.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the inspected player speed/jump/gravity values and consumers preserve the same movement inputs, and the changed Swift Sneak factor is numerically identical at all vanilla levels 0-3. B changes generic `MOVEMENT_SPEED` and `FLYING_SPEED` defaults from float literals `0.7F`/`0.4F` to double literals `0.7`/`0.4`; the player factory overrides movement speed with `0.1F` and player flight reads abilities speed. These generic defaults have no path into the client player's compared movement and the non-player path is outside campaign scope. This row does not close other modifier producers.
- Finding IDs or checked absence/replacement path: no player-path delta; generic entity default literal precision is recorded as out of scope; remaining modifier producers are linked to E1-E4.

### Slice C1 - bounding-box movement, position updates and axis ordering

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: `Entity#move(MoverType,Vec3)` (A lines 597-647; B lines 598-648), `setPos(double,double,double)`, `setBoundingBox`, `setPosRaw`, `setOnGround`, `checkSupportingBlock`, and the grounded-movement helper. A/B `move` bodies match apart from the helper call rename `setOnGroundWithKnownMovement` -> `setOnGroundWithMovement`; both preserve axis-resolved displacement application, collision flags, velocity cancellation and support refresh order. Other `setPos` call sites, including portal/dimension transitions, remain separate.
- A evidence: `ready/1.20.6/mojmap/net/minecraft/world/entity/Entity.java`, SHA-256 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`; `setPos` lines 383-390, ground/support methods 557-578, movement 597-647, base edge-backoff method 837-839, bounding-box writer 2782+, and raw position writer 3249+ were paired. The local player's edge-backoff override and its support predicates are in `Player.java`, SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`.
- B evidence: `ready/1.21.1/mojmap/net/minecraft/world/entity/Entity.java`, SHA-256 `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`; corresponding bodies are at lines 384-391, 558-579, 598-648, base edge backoff 838-840, bounding-box writer 2829+, and raw position writer 3288+. The local player's edge-backoff override and support predicates are in `Player.java`, SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`; they match A. The renamed grounded-movement helper body is identical to A after symbol normalization.
- State producers/writers -> consumers/readers: `move` consumes `collide` output, updates position/bounding box, derives horizontal/vertical collision state and invokes support refresh; the grounded-movement helper delegates to the same `checkSupportingBlock`. `collide(Vec3)` is a material changed producer tracked in C2, while AABB/world-query enumeration and shape inputs remain open in C4/B8.
- Parent slices / dependencies / closure evidence: C2 step-up candidate algorithm remains in progress; C4 AABB/world collision queries and B8 shape providers remain open; X4 covers dimension/portal position paths. This is only the ordinary `move` application and position-writer slice.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): ordinary movement application and the inspected position/support writers show no independent delta beyond the helper rename, but the changed `collide` producer and open collision/query dependencies prevent a terminal disposition for C1.
- Finding IDs or checked absence/replacement path: no C1 finding recorded. The renamed helper has the same body; changed step-up generation remains routed to C2, and other position writers remain assigned to their owning slices.

### Slice C2 - step-up candidates, tie-breaking, edge probes and support

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: `Entity#collide(Vec3)` step-up branch, `collectCandidateStepUpHeights`, `collideBoundingBox`/`collectColliders`/`collideWithShapes`, `LivingEntity#maxUpStep`, `Attributes.STEP_HEIGHT`, and the player override of `maybeBackOffFromEdge` with `isStayingOnGroundSurface`, `isAboveGround` and `canFallAtLeast`. A's `Entity#collide` is at `Entity.java` lines 874-898; B's at lines 875-904 with candidate enumeration at 906-923. The candidate branch is reached only when `maxUpStep()>0`, horizontal collision occurs, and either the player is on ground or the requested downward movement is vertically blocked. Player edge probes call `Level#noCollision` on a thin box below the current bounding box; their collision-query dependencies are not closed here.
- A evidence: `ready/1.20.6/mojmap/net/minecraft/world/entity/Entity.java`, SHA-256 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`; `collide(Vec3)` first computes the regular result, then tests a direct horizontal-plus-maximum-step candidate and a vertical-first fallback, choosing the larger horizontal distance before resolving downward movement. The player edge-probe override and its `isStayingOnGroundSurface`/`isAboveGround`/`canFallAtLeast` helpers are in `Player.java`, SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`; the helper calls `Level#noCollision` over a thin box below the player. `CollisionGetter.java` SHA-256 `f21daa1ed0365cc16a2638373d702fbaeb4242ad12a80a41105cf43c711e6d95`; `AABB.java` SHA-256 `13ab54ef7b11abbe41465580eb5cddb05a4bad3178995d4609c252d70b96cad9`.
- B evidence: `ready/1.21.1/mojmap/net/minecraft/world/entity/Entity.java`, SHA-256 `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`; `collide(Vec3)` gathers colliders over the step-up sweep, obtains candidate heights from each shape's Y coordinates relative to the box, float-sorts them with `FloatArrays.unstableSort`, and returns the first candidate whose horizontal distance exceeds the regular result. `collectColliders` includes the world border and block collisions; `collideWithShapes` is the shared axis-resolution method. The paired player edge-probe override and helpers are in `Player.java`, SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`; their bodies match A. `CollisionGetter.java` is byte-identical to A; `AABB.java` SHA-256 `ffbb9d717f54538451a58a0b53d5ebb4b56843fad46b7ed9becc5f7d2ac78ba8`.
- State producers/writers -> consumers/readers: `Attributes.STEP_HEIGHT` has the same default `0.6`, bounds `0.0..10.0`, and syncable flag in A (`Attributes.java` SHA-256 `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e`) and B (SHA-256 `a9a19f556bb77fc218b5b2f4831eb6b285f4398c24fa2a3519009ca8dad2db7c`). `LivingEntity#maxUpStep` casts the effective attribute to float on both sides and applies the same minimum of `1.0F` while controlling a player passenger (`LivingEntity.java` A SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; B SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`). Other STEP_HEIGHT writers/modifiers and all provider/query inputs remain to be inventoried.
- Parent slices / dependencies / closure evidence: C1 movement/position update and C4 world collision/AABB queries remain open; B8 shape providers remains in progress; E4 STEP_HEIGHT modifier producers remain open. The paired `CollisionGetter` and edge-backoff bodies were checked, but this does not close their producer, contextual-shape, neighbor-provider or consumer inventories.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): a reachable source-level algorithm change is established, but no concrete paired collision arrangement and resulting player movement delta has yet been proven. The candidate-height generation/order and collider sweep therefore remain open for source-only reachability analysis; do not infer equivalence from the shared attribute bounds or shared axis resolver.
- Finding IDs or checked absence/replacement path: no finding recorded yet. The differing candidate-selection path is routed to continued C2 analysis; support shapes, query ordering and STEP_HEIGHT modifier reachability remain dependencies.

### Slice C3 - velocity cancellation

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: velocity cancellation; exact paired method ranges await readiness.
- A evidence: source root ready/1.20.6/mojmap is ready and its source manifest hash is verified; exact member/body ranges and content hashes await paired slice inspection.
- B evidence: ready/1.21.1/mojmap verified; cited source member range/hash is to be recorded during this bounded comparison.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0/D1 source readiness and alignment closed; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice C4 - world collision queries, AABB

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: world collision queries, AABB; exact paired method ranges await readiness.
- A evidence: source root ready/1.20.6/mojmap is ready and its source manifest hash is verified; exact member/body ranges and content hashes await paired slice inspection.
- B evidence: ready/1.21.1/mojmap verified; cited source member range/hash is to be recorded during this bounded comparison.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0/D1 source readiness and alignment closed; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice C5 - fluid contact, block callbacks and callback order; track repeated queries

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: fluid contact, block callbacks and callback order; track repeated queries; exact paired method ranges await readiness.
- A evidence: source root ready/1.20.6/mojmap is ready and its source manifest hash is verified; exact member/body ranges and content hashes await paired slice inspection.
- B evidence: ready/1.21.1/mojmap verified; cited source member range/hash is to be recorded during this bounded comparison.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0/D1 source readiness and alignment closed; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice B1 - base block

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: base block; exact paired method ranges await readiness.
- A evidence: source root ready/1.20.6/mojmap is ready and its source manifest hash is verified; exact member/body ranges and content hashes await paired slice inspection.
- B evidence: ready/1.21.1/mojmap verified; cited source member range/hash is to be recorded during this bounded comparison.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0/D1 source readiness and alignment closed; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice B2 - landing

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: landing; exact paired method ranges await readiness.
- A evidence: source root ready/1.20.6/mojmap is ready and its source manifest hash is verified; exact member/body ranges and content hashes await paired slice inspection.
- B evidence: ready/1.21.1/mojmap verified; cited source member range/hash is to be recorded during this bounded comparison.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0/D1 source readiness and alignment closed; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice B3 - friction

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: friction; exact paired method ranges await readiness.
- A evidence: source root ready/1.20.6/mojmap is ready and its source manifest hash is verified; exact member/body ranges and content hashes await paired slice inspection.
- B evidence: ready/1.21.1/mojmap verified; cited source member range/hash is to be recorded during this bounded comparison.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0/D1 source readiness and alignment closed; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice B4 - contact slowdown (webs, honey, powder snow) and historical applicability: `compared-no-difference`.

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: registered Cobweb/WebBlock, Honey Block, Powder Snow and Sweet Berry Bush `entityInside` contact behavior; their `makeStuckInBlock` multiplier inputs; and `Entity#makeStuckInBlock`/movement consumption. The callback dispatch and box iteration are paired under L4.
- A evidence: `ready/1.20.6/mojmap/net/minecraft/world/level/block/Blocks.java` registrations for Cobweb, Sweet Berry Bush, Honey Block and Powder Snow at lines 766, 6183, 6594, 6920, SHA-256 `684579bbcbe48b1f0984090a4ca044c0b2cee6d08c2dc1e449e2259389c5b129`; `HoneyBlock#entityInside`, `isSlidingDown` and `doSlideMovement` lines 62-105, SHA-256 `fd9c67c31c34833bf442e81ec0b1647e9f9e9042f01b9cd63127d2f832d5d8ff`; `PowderSnowBlock#entityInside` lines 64-80, SHA-256 `7a6d6ad2e6d6d719344de4bb4f4e4973387aa88e61757988afea25b46c176ad3`; `WebBlock#entityInside` lines 26-33, SHA-256 `ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414`; `SweetBerryBushBlock#entityInside` lines 80-91, SHA-256 `615b36436b64e20f6f33235aa2e24aa84a3d0e9b54ff27aa7d97821d8ffb6d3c`; `Entity#makeStuckInBlock` lines 2361-2364 and `Entity#move` stuck-speed multiplier consumption lines 610-613, SHA-256 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`.
- B evidence: `ready/1.21.1/mojmap/net/minecraft/world/level/block/Blocks.java` registrations at lines 765, 6181, 6592, 6906, SHA-256 `538ac50c164484ca4c2367a2a6c07cf2faedcac0c4d5117d8817d36c2d1bde6d`; the corresponding `HoneyBlock`, `PowderSnowBlock`, `WebBlock` and `SweetBerryBushBlock` source files have the same respective A/B SHA-256 values listed above and the same cited method bodies; `Entity#makeStuckInBlock` lines 2427-2430 and `Entity#move` stuck-speed multiplier consumption lines 611-614, SHA-256 `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`.
- State producers/writers -> consumers/readers: contact block callback -> `Entity#makeStuckInBlock` stores a per-tick `Vec3` multiplier and resets fall distance -> `Entity#move` applies that multiplier to requested movement when its squared length exceeds `1.0E-7`, then clears it. Honey's falling slide clamps Y velocity and resets fall distance; Powder Snow, Cobweb and Sweet Berry Bush provide the same movement vectors on both sides. Their contact methods and all listed block registrations are paired; code and source hashes match for each cited block class.
- Parent slices / dependencies / closure evidence: L4 paired generic inside-block dispatcher and callback order; L2 paired movement multiplier consumer. The WebBlock `WEAVING` predicate reads an effect state; A's `MobEffects.WEAVING` registration requires `FeatureFlags.UPDATE_1_21` while A's `FeatureFlags.DEFAULT_FLAGS` is only `VANILLA_SET`; B registers the effect without that feature requirement. This marks the weaving-enabled slowdown as modern-only for the vanilla 1.20.6 profile. Producer mechanics remain in E1/E4 and are not inferred here.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for the shared vanilla block/effect-state preconditions, the player-reachable contact slowdown and honey slide bodies, movement multipliers, and registrations are unchanged. B's availability of the Weaving-conditioned web multiplier under default 1.21.1 features is a modern-only effect state relative to A's default feature set; preserve it as an explicit out-of-scope disposition rather than inventing 1.20.6-era behavior. Sweet Berry Bush damage is excluded; its identical movement slowdown remains in scope.
- Finding IDs or checked absence/replacement path: no in-scope B4 finding. Paired contact methods and checked registrations establish unchanged historical block movement; the 1.21.1 default availability of the modern Weaving effect is excluded by the A feature-gate/default-feature source path.

### Slice B5 - climbables (ladders, vines and related blocks), callbacks and support: `pending`.

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: climbables (ladders, vines and related blocks), callbacks and support: `pending`.; exact paired method ranges await readiness.
- A evidence: source root ready/1.20.6/mojmap is ready and its source manifest hash is verified; exact member/body ranges and content hashes await paired slice inspection.
- B evidence: ready/1.21.1/mojmap verified; cited source member range/hash is to be recorded during this bounded comparison.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0/D1 source readiness and alignment closed; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice B6 - water and lava current producers: `compared-no-difference`.

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `Entity#updateInWaterStateAndDoFluidPushing`, `updateInWaterStateAndDoWaterCurrentPushing`, `updateFluidHeightAndDoFluidPushing`, water/lava flow vectors through `FluidState#getFlow` and `FlowingFluid#getFlow`, plus `BubbleColumnBlock#entityInside` and `Entity#onAboveBubbleCol`/`onInsideBubbleColumn` velocity writes. Fluid-contact query and collision geometry consumers remain separately tracked under C4/C5/B8.
- A evidence: `ready/1.20.6/mojmap/net/minecraft/world/entity/Entity.java`, fluid-state dispatch lines 1203-1220, bubble velocity writers lines 2291-2314, `isPushedByFluid()` line 2614, and fluid-push accumulator lines 3052-3117, SHA-256 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`; `LivingEntity#isAffectedByFluids()` lines 587-589, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; `FlowingFluid#getFlow()` lines 54-96, SHA-256 `0f44b1af25533dd8a1fbfec13b279739341db0c563568337a2825d3e46a1a5bb`; `FluidState` lines 49-54 and 91-93, SHA-256 `b52e2b1e889a510b5d80c5eef09cd4f5b63448f15dbb131f02030497340e7c1c`; `BubbleColumnBlock#entityInside` lines 49-79, SHA-256 `39c4da51460f4c2c06927f15de65a9ee2ae2f8bee0a2c9289cea375ad5d53a7c`.
- B evidence: `ready/1.21.1/mojmap/net/minecraft/world/entity/Entity.java`, corresponding fluid-state dispatch lines 1242-1259, bubble velocity writers lines 2357-2380, `isPushedByFluid()` line 2652, and fluid-push accumulator lines 3091-3156, SHA-256 `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`; `LivingEntity#isAffectedByFluids()` lines 535-537, SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`; `FlowingFluid`, `FluidState` and `BubbleColumnBlock` source hashes match A exactly; `Fluids.java` is byte-identical at SHA-256 `913cb5d937af92a7f90f7dc233a3d02c05f75e70a2a740300b2f26eedcbed931`; `FluidTags` water/lava entries lines 8-9 retain the same IDs. `Blocks#BUBBLE_COLUMN` registration at lines 5948-5956 uses the same no-collision bubble block properties; Blocks file SHA-256 `538ac50c164484ca4c2367a2a6c07cf2faedcac0c4d5117d8817d36c2d1bde6d`.
- State producers/writers -> consumers/readers: per-tick entity update clears/rebuilds fluid heights and reads water/lava flow through the same bounding-box scan, fluid height, averaging, player-specific non-normalization, small-current floor and delta-movement addition. Bubble column states dispatch the same up/down velocity clamps both inside the column and above it. A's accumulator scales by `$$1 * 1.0`; B scales by `$$1`. The reachable callers pass the same finite lava factor (`0.007` or `0.0023333333333333335`) and water factor (`0.014`), and the extra multiplication by exactly `1.0` does not change these finite movement values. Water/lava fluid registry definitions, flow equations, tags and bubble-column callback bodies are otherwise identical.
- Parent slices / dependencies / closure evidence: I2 per-tick call order; L1/L2 travel and friction consumers; C4/C5 still cover box/fluid contact query enumeration and callback order; B8 covers shapes. `Fluids`, `FlowingFluid`, `FluidState`, and `BubbleColumnBlock` hashes were matched to both ready source manifests.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for the local player, same water/lava fluid heights and neighboring flow states yield the same accumulated movement, including the non-normalized player current path and bubble-column vertical impulses. The sole source-expression simplification is multiplication by `1.0` on the fluid-push strength; its call inputs are the finite constants above, so no changed movement value is established. This closure does not assert completeness of the separate collision-query or shape inventories.
- Finding IDs or checked absence/replacement path: no in-scope B6 finding; paired fluid-flow, fluid-push, bubble-column and player velocity-writer paths show no movement difference.

### Slice B7 - moving pistons and player displacement: `pending`.

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: moving pistons and player displacement: `pending`.; exact paired method ranges await readiness.
- A evidence: source root ready/1.20.6/mojmap is ready and its source manifest hash is verified; exact member/body ranges and content hashes await paired slice inspection.
- B evidence: ready/1.21.1/mojmap verified; cited source member range/hash is to be recorded during this bounded comparison.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0/D1 source readiness and alignment closed; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice B8 - historical collision and support-shape providers

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: initial paired scan of shape-provider source files under `world/level/block`: declarations of `getShape`, `getCollisionShape`, `getBlockSupportShape`, and `getOcclusionShape`, plus static `VoxelShape` field declarations. This scan established the common provider set and method/field equality, but block registration/property inputs, neighbor-dependent providers and the full player query/support routes remain open.
- A evidence: ready source tree `ready/1.20.6/mojmap`, source manifest SHA-256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`; paired provider set is 140 files selected by those exact method names. `state/BlockBehaviour.java` (A SHA-256 `5ccc577c6b257badf8bffab54181c45b6c57779c8545efe5c54a9766687e685b`) contains shared default and state-delegating shape paths. Major era-existing providers include `FenceBlock`, `WallBlock`, `StairBlock`, `SlabBlock`, `DoorBlock`, `TrapDoorBlock`, `SnowLayerBlock`, `FarmBlock` and `DirtPathBlock`.
- B evidence: ready source tree `ready/1.21.1/mojmap`, source manifest SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`; the same 140-file provider set is present, with no A-only or B-only provider path. Every paired declared shape method body and static `VoxelShape` field declaration in that set compared byte-for-byte equal. `state/BlockBehaviour.java` B SHA-256 `35420ee4fca120ccf1d1542a6b1746fcdd1e9dafd125cf0b8193f9b8b406b292`; its shape defaults and `BlockStateBase` delegates were compared despite unrelated file changes. The major era-existing provider method bodies listed on A are unchanged on B.
- State producers/writers -> consumers/readers: registered block/state properties and neighboring blocks feed shape providers -> `BlockState` shape accessors -> `BlockGetter` collision/support queries -> `Entity#collide`, step/edge resolution and support-state writes. The method/field scan closes only the provider-body layer. It does not yet establish matching `Blocks` registrations, state/property defaults, contextual or neighboring-state shapes, piston movement shapes, or that all query consumers and shape contexts have been enumerated.
- Parent slices / dependencies / closure evidence: C1-C4 bounding-box/step/support/query routes remain pending; B1-B7 block-specific registration and movement dependencies remain pending; L4 callback closure still depends on B8. A/B `Blocks.java` source hashes differ due broader release content, so exact registrations for the old block provider set still require paired checking.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): no changed declared shape method body or static `VoxelShape` field was found in the paired 140-file source scan. This is only an intermediate no-difference observation. Registration, blockstate, neighboring-provider and collision-query dependency closure is incomplete, so B8 has no terminal equivalence disposition.
- Finding IDs or checked absence/replacement path: no shape finding yet. The changed End/Nether portal `entityInside` transition behavior is outside this shape-only scan and remains queued for X4 movement-state review; the portal shape methods/constants are unchanged.

### Slice E1 - Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace and Blindness consumers

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace and Blindness consumers; exact paired method ranges await readiness.
- A evidence: source root ready/1.20.6/mojmap is ready and its source manifest hash is verified; exact member/body ranges and content hashes await paired slice inspection.
- B evidence: ready/1.21.1/mojmap verified; cited source member range/hash is to be recorded during this bounded comparison.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0/D1 source readiness and alignment closed; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice E2 - Depth Strider, Soul Speed, Swift Sneak, Frost Walker and Riptide consumer-to-registration

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Depth Strider, Soul Speed, Swift Sneak, Frost Walker and Riptide consumer-to-registration; exact paired method ranges await readiness.
- A evidence: source root ready/1.20.6/mojmap is ready and its source manifest hash is verified; exact member/body ranges and content hashes await paired slice inspection.
- B evidence: ready/1.21.1/mojmap verified; cited source member range/hash is to be recorded during this bounded comparison.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0/D1 source readiness and alignment closed; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice E3 - Elytra, use-item slowdown, equipment, movement item components and application

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Elytra, use-item slowdown, equipment, movement item components and application; exact paired method ranges await readiness.
- A evidence: source root ready/1.20.6/mojmap is ready and its source manifest hash is verified; exact member/body ranges and content hashes await paired slice inspection.
- B evidence: ready/1.21.1/mojmap verified; cited source member range/hash is to be recorded during this bounded comparison.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0/D1 source readiness and alignment closed; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice E4 - other discovered movement-affecting attributes

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: other discovered movement-affecting attributes; exact paired method ranges await readiness.
- A evidence: source root ready/1.20.6/mojmap is ready and its source manifest hash is verified; exact member/body ranges and content hashes await paired slice inspection.
- B evidence: ready/1.21.1/mojmap verified; cited source member range/hash is to be recorded during this bounded comparison.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0/D1 source readiness and alignment closed; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice X1 - incoming velocity

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: clientbound set-entity-motion packet encoding/accessors, client handler and `Entity#lerpMotion`; paired `ServerEntity` delta-tracking writers, including initialization and hurt-marked send path; `ChunkMap.TrackedEntity#broadcast` and `updatePlayer` to determine whether the changed tracker baseline can reach the locally controlled entity. Player knockback source is handled separately under X2.
- A evidence: `ClientPacketListener#handleSetEntityMotion` lines 515-521 and `Entity#lerpMotion` lines 2096-2098, SHA-256 `e121e998ec25211aaecf91fffd0ea699cebd47eddea9134efd3f2ffbef8eb7cd`; `ClientboundSetEntityMotionPacket` lines 11-72, SHA-256 `34e141d58c2d3585ef6a4b00ed4819c42fa11301ba8428ca06c5379fb7ecdae9`; `ServerEntity` field `ap=Vec3.ZERO`, delta threshold, tracked send and hurt-marked send lines 62/169-175/202-212, SHA-256 `c987db52a532d93be66fa1d5cd05dc849c1c46f8d70f641fa636db3078c262b7`; `ChunkMap.TrackedEntity#broadcast`/`broadcastAndSend`/`updatePlayer` lines 1277-1287/1302-1318, SHA-256 `d832b42453d570da97ecb34190c5c369316a3f9151407492fbd90e299a6e85bd`.
- B evidence: `ClientPacketListener#handleSetEntityMotion` lines 517-523 and `Entity#lerpMotion` lines 2162-2164, SHA-256 `822e00d89e8def4cf7a8c22fdfefba3b9dc2135a7630e4ebe561a9d547d7f79d`; `ClientboundSetEntityMotionPacket` lines 11-72, SHA-256 `bc6c957cb33fd07b3b2b6e22f62aa15f9f68872e44d67b172bb0249f81950d91`; `ServerEntity` initializes `lastSentMovement` from entity velocity and uses it in the delta threshold/tracked send and hurt-marked path, lines 62/71-78/168-185/213-220, SHA-256 `0994216505c8df7cc67a7a068ae8e71d1f023dbef5dc1dc31360e3e5bdc2ce14`; `ChunkMap.TrackedEntity#broadcast`/`broadcastAndSend`/`updatePlayer` lines 1204-1214/1229-1245, SHA-256 `079b7c5e3d5bcdfff8b18a69513c93aaca56dfd739bde642c2ece79d127c5088`.
- State producers/writers -> consumers/readers: server `ServerEntity` encodes clamped signed-short components at scale 8000; A packet getters expose integers and `ClientPacketListener` divides by 8000, while B packet getters return already-scaled doubles and the handler passes them directly to `Entity#lerpMotion`. The packet wire fields, clamp, truncating cast and final client velocity assignment match. The server tracker delta baseline changes from zero in A to current entity movement in B, but `ChunkMap.TrackedEntity#updatePlayer` excludes the entity's own `ServerPlayer`, and `broadcast` only iterates those other tracked connections. The changed initial delta packet therefore does not reach that player's own movement path. The separate `hurtMarked` path uses `broadcastAndSend` to include the subject `ServerPlayer` in both versions; its packet encoding and client application match.
- Parent slices / dependencies / closure evidence: D0/D1 readiness; I2 local tick/remote state boundaries; X2 direct player knockback remains separate and untouched; X4 other velocity writers remain open. Entity movement consumers remain covered by L1/L2.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): a real tracker baseline change is present, but its first-update delta is broadcast only to other tracked clients. The exact `updatePlayer` self guard plus `broadcast` recipient loop rule out delivery to the locally controlled player. Such remote-entity presentation is outside this movement scope. For a local `ServerPlayer`, the explicit hurt-marked send includes self on both sides and preserves packet values and the client velocity write. No in-scope X1 velocity change is established. Damage/combat production is not traced; direct player-knockback math remains X2.
- Finding IDs or checked absence/replacement path: no X1 finding; the only tracker-baseline difference is excluded because its verified consumer path targets other clients, while the self-delivered velocity path is unchanged.

### Slice X2 - player knockback

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: player knockback; exact paired method ranges await readiness.
- A evidence: source root ready/1.20.6/mojmap is ready and its source manifest hash is verified; exact member/body ranges and content hashes await paired slice inspection.
- B evidence: ready/1.21.1/mojmap verified; cited source member range/hash is to be recorded during this bounded comparison.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0/D1 source readiness and alignment closed; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice X3 - mount

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: mount; exact paired method ranges await readiness.
- A evidence: source root ready/1.20.6/mojmap is ready and its source manifest hash is verified; exact member/body ranges and content hashes await paired slice inspection.
- B evidence: ready/1.21.1/mojmap verified; cited source member range/hash is to be recorded during this bounded comparison.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0/D1 source readiness and alignment closed; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.

### Slice X4 - final unresolved movement-state writers, changed dependencies and cross-mechanic interactions: `pending`.

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: final unresolved movement-state writers, changed dependencies and cross-mechanic interactions: `pending`.; exact paired method ranges await readiness.
- A evidence: source root ready/1.20.6/mojmap is ready and its source manifest hash is verified; exact member/body ranges and content hashes await paired slice inspection.
- B evidence: ready/1.21.1/mojmap verified; cited source member range/hash is to be recorded during this bounded comparison.
- State producers/writers -> consumers/readers: pair-specific call graph and producer/consumer closure pending.
- Parent slices / dependencies / closure evidence: D0/D1 source readiness and alignment closed; add pair-specific dependency IDs during source traversal.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source comparison has not begun; no equivalence or difference is inferred.
- Finding IDs or checked absence/replacement path: none recorded; absence is not evidence of equivalence.
## Dependency queue and blockers

- Open dependencies: D0 closed (both exact Mojmap markers, source/artifact/diagnostic manifests and provenance verified); D1 closed for readiness/alignment (cited methods and dependencies continue to be checked per slice); D2 open for remaining paired callers, collision/state writers, block/fluid shapes, registrations, callbacks, external velocity sources, modifier conditions and the Soul Speed attribute update timing against client prediction.
- These are queued source-publication dependencies, not evidence of equivalence or differences. If a relevant damaged/missing body or absent data prevents a slice, record its exact path/range and blocked dependency.

## Finding index

- [water-depth-strider-rounding](findings/water-depth-strider-rounding.md): Depth Strider III water speed factor changes by one binary32 ulp; source-confirmed.
- [soul-speed-first-entry-block-friction](findings/soul-speed-first-entry-block-friction.md): source-confirmed first local entry move reads baseline movement efficiency before the server-triggered attribute can return to the client.

### Incremental source snapshot log

- Snapshot ID: `l5-water-depth-strider-2337ef4`
- Immutable finding commit: `2337ef4dbcab9a99b48f0212b218726c004462b5` (parent `a004acf793b7ba3ea6a12f14407fcb81fdc2b8a6`); this commit adds the finding file, whose current bytes were re-read from the commit and match the working copy.
- Finding file: `findings/water-depth-strider-rounding.md`; `findingSHA256=25e4a6799a9f56b830c5e0c814a50ea04a0abf4c8219f2ab6a790fd48b436ca2`.
- A artifact identity: exact ready marker `ready/1.20.6/mojmap.ready.json`, status ready, version 1.20.6, Mojmap; source manifest SHA-256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`; artifact manifest SHA-256 `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`; cited source hashes and ranges are in the finding. Marker, manifests, diagnostics, provenance and all cited Java source hashes were revalidated on 2026-10-08.
- B artifact identity: exact ready marker `ready/1.21.1/mojmap.ready.json`, status ready, version 1.21.1, Mojmap; source manifest SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`; artifact manifest SHA-256 `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486`; cited source hashes and ranges are in the finding. Marker, manifests, diagnostics, provenance and all cited Java source hashes were revalidated on 2026-10-08.
- Snapshot review: pending independent source review by coordinator-designated reviewer `01a116ce-dfe4-7b11-b7e6-d62f5014e356`; not yet accepted for implementation handoff. Pair discovery remains active and partial.

- Snapshot ID: `l2-soul-speed-first-entry-da102c2`
- Immutable finding commit: `da102c26ab56cf559b7003439a53444f6ed26e75` (parent `a7abfea1a182fbc40ee15c0dcc7727f5833fc0d2`); current file bytes match the committed snapshot.
- Finding file: `findings/soul-speed-first-entry-block-friction.md`; `findingSHA256=daf4af79c8551efd5601e4fee7b99b72084b03141c13805a5ac91ec785927845`.
- A artifact identity: exact ready marker `ready/1.20.6/mojmap.ready.json`, status ready, version 1.20.6, Mojmap; source manifest SHA-256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`; artifact manifest SHA-256 `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`; cited source hashes and ranges are in the finding. Marker, manifests, diagnostics, provenance and cited Java/tag hashes were revalidated on 2026-10-08.
- B artifact identity: exact ready marker `ready/1.21.1/mojmap.ready.json`, status ready, version 1.21.1, Mojmap; source manifest SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`; artifact manifest SHA-256 `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486`; cited source hashes and ranges are in the finding. Marker, manifests, diagnostics, provenance and cited Java/tag hashes were revalidated on 2026-10-08.
- Snapshot review: pending independent source review by coordinator-designated reviewer `01a116ce-dfe4-7b11-b7e6-d62f5014e356`; not yet accepted for implementation handoff. Pair discovery remains active and partial.

## Resume checkpoint

- Last completed slices: I1-I4 input/tick/yaw/sprint compared; L3 jump path and L6 player movement attributes compared; L5 water-travel arithmetic finding recorded; X1 tracked velocity packet and tracker-baseline reachability compared-no-difference for the locally controlled player; B4 contact slowdowns compared with the modern-only Weaving state explicitly excluded; B6 fluid currents and bubble-column velocity producers compared-no-difference. B8 has a common-provider method/field scan with registration/query closure open. C1 now records ordinary `Entity#move` application and position/support methods as in-progress, depending on C2/C4; C2 records the paired step-up candidate algorithms as in-progress. L4 callback closure remains open through B8. L2 has a committed source-confirmed first-entry Soul Speed snapshot awaiting independent review; P1/P2/P4, L2, L4, C1 and C2 remain in progress pending dependencies.
- Next bounded slice and exact files/members/body ranges to open: continue C2 from `Entity#collide(Vec3)` and `collectCandidateStepUpHeights` through candidate heights and collider geometry; close STEP_HEIGHT modifier producers under E4 and the supporting C1/C4/B8 dependencies. Then continue B8 through paired registration/property, contextual-shape, neighbor-support and query-consumer paths, and resume C3-C4 and B1-B7 routes. X2 player knockback remains separate. Record the End/Nether portal transition callback change under X4 if its player position/timing path is reachable. Entity movement thresholds, generic inside-block dispatch, B4 and B6 are now compared. L6 player movement attributes are complete.
- Outstanding dependencies and owners: D0/D1 readiness closed; D2 remains open for movement source dependencies and is owned by this discovery worker until handoff.
- Current assumptions requiring verification: only cited source bodies are hash-checked so far; all remaining method correspondence, dependencies, callbacks, registrations and state writer/consumer closure are incomplete.

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

- Coverage counts by status: 1 findings; 11 compared-no-difference; 0 not-applicable; 0 blocked; 15 pending; 8 in-progress (35 planned slices; one source-confirmed finding spans L2 and L4, with broader closure still in progress).
- Required inventory status and evidence: all seven required inventories remain pending; L5 and L2 have bounded source-confirmed findings (L2 snapshot review pending), while complete inventories are not closed.
- Open dependencies: D2 only; D0/D1 readiness and namespace alignment are closed.
- Unresolved gaps and limits: 15 slices remain pending and 8 remain in progress, including registration/query closure for collision/shape providers, full modifier coverage, blocks, X2 player knockback and other external-input slices, exclusions and independent audit. The two bounded findings and X1/B4/B6 closures do not close those inventories.
- Evidence/hash/correspondence audit: exact source hashes and paired ranges are recorded for L2-L6 bounded rows and findings; the overall evidence audit is incomplete.
- Blind freeze: pending until pair coverage is complete.
- Implementation reconciliation: pending and outside this assignment before explicit post-freeze authorization.
- Independent audit: pending coordinator assignment.
- Source-only declaration: no runtime Java implementation/mod implementation inspection; no wiki/MCPK browsing or wiki-audit output; no release-notes-derived movement claims; health/regen/hunger/food/saturation/exhaustion/damage/combat simulations and non-player movement excluded. Vanilla values may be traced only as direct movement predicate inputs.
- Runtime validation: not performed (separate workflow; not authorized).
