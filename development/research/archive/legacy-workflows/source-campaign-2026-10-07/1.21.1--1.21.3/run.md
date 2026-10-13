# Discovery: 1.21.1 to 1.21.3

- Run status: active
- Scope: source-only client player movement; older A = 1.21.1; newer B = 1.21.3. This is one content-update boundary using the exact latest-hotfix representatives.
- Repository revision and start date: started at `002137b227676caea77f6832b9f4c8d0b6200bff` (`main`), 2026-10-07 Europe/Vienna; task branch `feat/source-discovery-movement-source-1-21-1-1-21-3`.
- Selected naming namespace: Mojmap on both endpoints; readiness IDs/metadata resolve exactly to 1.21.1 and 1.21.3.
- Source owner worktree: C:\Users\Wolfi\.codex\worktrees\3e2d\LegacyParkourCompat. Command from provenance: gradlew.bat decompileMinecraft --versions=1.21.1,1.21.3,1.21.4,1.21.5 --mappings=mojmap --decompiler-heap=4G. Success log path: ../../../build/movement-campaign-2026-10-07/staging/mojmap-1.21.1-to-1.21.5-cd5a99cb1024417c9d370097c886a131/gradle.full.log.
- Toolchain: Gradle 9.7.1, Java 25.0.3+9-LTS, Vineflower 1.12.0, mapping-io 0.9.1, tiny-remapper 0.14.1, ASM 9.10.1, Gson 2.14.0; heap 4G.
- Discovery author(s): Codex source worker for this branch.
- Independent reviewer (must differ from discovery authors): pending coordinator assignment.
- Scope constraints: no wiki/MCPK use; no wiki-audit output or old mod implementation/code inspected; no runtime Java implementation. Health/food state production, attack/damage resolution, non-player movement and vehicle physics are excluded. Direct player velocity/impulse/knockback application remains in scope when native triggers cause it; combat cause and damage calculations are not emulated. Direct vanilla-state reads by movement predicates may be recorded without emulating their producer systems. Modern-only blocks do not acquire old behavior.
- Runtime validation: not performed and not authorized. Tests, game/TAS/Gym/server/Docker launches and Gradle builds have not been run.

## Artifact manifest

### A — 1.21.1

- Requested/resolved release and metadata IDs: 1.21.1 / 1.21.1; ready, Mojmap; 5,363 files.
- Source root: ../../../build/movement-campaign-2026-10-07/ready/1.21.1/mojmap.
- Client 499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99; mappings 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec; Mojmap jar 6b36d2ccc99e7eeb98e942b6dc093388d66eb6ede06b64238f35a0e26b092abe; source manifest 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48; artifact manifest 09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486; diagnostics 44c184cd1385e5b23991b2698ff0ae9f1e62a9491de8dcac27e5a3f9af88cd4f. Digests independently matched marker.
- Resource/tag comparison open; no absence claim.

### B — 1.21.3

- Requested/resolved release and metadata IDs: 1.21.3 / 1.21.3; ready, Mojmap; 5,655 files.
- Source root: ../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap.
- Client 25190c556bc56d11070638ccab3b3c6efd76bb9684c92a2680a4de01c3c7138b; mappings e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41; Mojmap jar 9e5d42c42acaf0076ee92b41b13aa7440667967266ca67ecd50b08e5e4ba8024; source manifest  d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce; artifact manifest 0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf; diagnostics 065c0644d14a4ba2001713ba9a72470b414f01ab327aec772924709f3118f08f. Digests independently matched marker.
- Resource/tag comparison open; no absence claim.

## Blind-discovery freeze

- Status: pending (full-pair freeze only after every source slice closes and independent audit passes)
- Freeze commit/checkpoint and timestamp: pending
- Evidence inventory and finding IDs included at freeze: not frozen
- Confirmation: no old mod implementation, prior source reports, wiki-audit outputs or wikis were consulted.
- Source/mapping hashes covered by freeze: pending

## Correspondence and call order

Partial correspondence: LocalPlayer.tick -> inherited player tick -> LivingEntity.aiStep -> travel -> Entity.move. A checks final bounding box after move; B checks recorded path after travel for server or locally controlled instances. Full graph remains open.

## Required source inventories

Each inventory maps to bounded source slices and remains pending until its full producer/consumer chain closes.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S-INPUT-AXES,S-LOCAL-TICK,S-BLOCK-CONTACT,S-ENTITY-MOVE,S-TRAVEL,S-TRAVEL-AIR,S-TRAVEL-FLUID,S-TRAVEL-FALLFLY,S-FALLFLY-ELIGIBILITY; evidence=paired source ranges in coverage ledger.
- `INV-STATE` movement state writers/readers: status=pending; slice_ids=S-BLOCK-CONTACT,S-ENTITY-MOVE,S-SUPPORT-GROUND,S-TRAVEL; evidence=paired source ranges in coverage ledger.
- `INV-COLLISION` player collision/query, shapes, providers, callbacks and neighbors: status=pending; slice_ids=S-BLOCK-CONTACT,S-ENTITY-MOVE,S-SUPPORT-GROUND; evidence=paired source ranges in coverage ledger.
- `INV-WORLD-MOVEMENT` block/fluid properties, subclasses, registries, data/tags and resources: status=pending; slice_ids=S-BLOCK-CONTACT; evidence=WebBlock and BlockBehaviour ranges in coverage ledger.
- `INV-MODIFIERS` attributes, effects, enchantments and equipment applications: status=pending; slice_ids=S-TRAVEL,S-TRAVEL-AIR,S-TRAVEL-FLUID,S-TRAVEL-FALLFLY,S-FALLFLY-ELIGIBILITY; evidence=travel, gravity/effect and equipment ranges in coverage ledger.
- `INV-EXTERNAL` player corrections, pushes, pistons, mounts and launch effects: status=pending; slice_ids=S-LOCAL-TICK,S-ENTITY-MOVE,S-EXTERNAL,S-FALLFLY-ELIGIBILITY; evidence=outer tick, move and fall-flying command ranges in coverage ledger.
- `INV-EXCLUSIONS` explicit excluded-system audit: status=pending; evidence=scope rules recorded above; full source audit open.

## Coverage ledger

### Slice S-INPUT-AXES: keyboard directional impulse

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: key sampling, directional axis calculation and slow-movement scaling only.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.1/mojmap/net/minecraft/client/player/KeyboardInput.java tick/calculateImpulse lines 5-35, SHA-256 a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0; Input.java lines 5-24, SHA-256 b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/net/minecraft/client/player/KeyboardInput.java lines 6-39, SHA-256 2a0994e8a6a2fdf9e76480227216db4a4252734fca8ca0aedd25dde77be75ba6; ClientInput.java lines 6-24, SHA-256 6bc3c2362657c0d5ce889eb1dddb203ddcb185a729d97cf577b6db0fe22abee5.
- State producers/writers -> consumers/readers: key mappings -> keyboard input record -> directional axes -> LocalPlayer.aiStep.
- Parent slices / dependencies / closure evidence: S-LOCAL-TICK; wider input consumers and packet paths remain open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): narrow directional normalization and slow scaling appear equivalent; B adds sprint to its input record, so whole-input behavior is not claimed.
- Finding IDs or checked absence/replacement path: no finding for this narrow axis calculation.

### Slice S-LOCAL-TICK: local player outer tick

- Inventory ID(s): INV-TICK, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer.tick entry through superclass tick and outgoing packet/ambient calls.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.1/mojmap/net/minecraft/client/player/LocalPlayer.java tick lines 191-210, SHA-256 c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java tick lines 189-212, SHA-256 fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0.
- State producers/writers -> consumers/readers: A current-chunk availability guards superclass tick and sends; B has no such guard. World-load/caller path remains open.
- Parent slices / dependencies / closure evidence: S-TRAVEL; exact method bodies and order are paired, but ordinary-client reachability of the guard condition is not closed.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): source behavior differs if the player's current chunk is unavailable; do not generalize beyond the exact condition until reachability is established.
- Finding IDs or checked absence/replacement path: candidate only; no source-confirmed finding.

### Slice S-BLOCK-CONTACT: per-move final-box versus post-travel path callback

- Inventory ID(s): INV-TICK, INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: A per-move current-box callback; B post-travel movement-path callback from old to current position; normal local client-player reachability; inside-shape gate; unchanged cobweb callback and registration.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.1/mojmap/net/minecraft/world/entity/Entity.java move lines 598-727 and checkInsideBlocks lines 1001-1031, SHA-256 b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850; WebBlock.entityInside lines 26-33, SHA-256 ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414; Blocks.java registers COBWEB at lines 765-770, SHA-256 538ac50c164484ca4c2367a2a6c07cf2faedcac0c4d5117d8817d36c2d1bde6d.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/net/minecraft/world/entity/Entity.java move lines 619-701, applyEffectsFromBlocks lines 736-771, checkInsideBlocks lines 1038-1078, SHA-256 a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9; LivingEntity.aiStep lines 2755-2779, SHA-256 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52; WebBlock body unchanged; Blocks.java registers COBWEB at lines 701-706, SHA-256 28a66da36ce98800226ec6d8e5fb4904fc7b137322a31f6d7ec822450374e62a; BlockBehaviour default inside shape lines 353-355, SHA-256 4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133.
- State producers/writers -> consumers/readers: A final bounding-box cells or B traversed movement cells -> entityInside -> WebBlock.makeStuckInBlock writes stuckSpeedMultiplier -> next Entity.move reads/scales/resets it (A lines 611-615; B lines 633-637). B's server-or-locally-controlled guard passes for LocalPlayer because its `isEffectiveAi()` override returns true.
- Parent slices / dependencies / closure evidence: S-TRAVEL and S-ENTITY-MOVE; local-player callback reachability, A final-box path, B movement-path scanner, shape gate and multiplier consumer are closed for this finding. Other block callback/provider/resource inventory remains open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): ordinary local client player, noPhysics false, and movement traversing a cobweb inside shape while ending with no bounding-box overlap: A's current-box scan omits that intermediate-only block; B's reachable post-travel scan traverses path cells and can call the unchanged WebBlock callback. The written multiplier can affect the next move.
- Finding IDs or checked absence/replacement path: F-BLOCK-CONTACT-TRAVERSE.

### Slice S-ENTITY-MOVE: collision result to position/support state

- Inventory ID(s): INV-STATE, INV-COLLISION, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: no-physics, piston limit, stuck multiplier, edge backoff, collision result, position-write guard, collision flags and immediate fall check.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.1/mojmap/net/minecraft/world/entity/Entity.java move lines 598-649, SHA-256 b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/net/minecraft/world/entity/Entity.java move lines 619-680, SHA-256 a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9.
- State producers/writers -> consumers/readers: requested vector -> piston/edge transforms -> collision result -> conditional setPos; deltas -> collision flags -> support/fall checks; stuck multiplier read/reset.
- Parent slices / dependencies / closure evidence: S-BLOCK-CONTACT; the tiny-position-write branch is isolated in S-MOVE-TINY-POS-WRITE/F-MOVE-TINY-POSITION-WRITE, and support-state assignment is isolated in S-SUPPORT-GROUND. Collision implementation, fall consequences, other epsilon effects and callers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B adds a second position-write condition at requested-length-squared minus actual-length-squared below 1.0E-7; near-zero consequences are unresolved.
- Finding IDs or checked absence/replacement path: F-MOVE-TINY-POSITION-WRITE; other position/collision differences remain under review.

### Slice S-MOVE-TINY-POS-WRITE: conditional position update after collision

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: requested movement after stuck multiplier and edge backoff -> collision result -> squared-length predicate -> position and bounding-box write.
- A evidence: `Entity.java` `move(MoverType, Vec3)` lines 617-630, SHA-256 `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`.
- B evidence: `Entity.java` `move(MoverType, Vec3)` lines 639-652, SHA-256 `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`.
- State producers/writers -> consumers/readers: requested vector -> edge backoff/collision -> actual vector squared length and requested-minus-actual squared lengths -> `setPos`/bounding box -> later position and collision queries.
- Parent slices / dependencies / closure evidence: S-ENTITY-MOVE; exact behavior and local-player route are detailed in `findings/F-MOVE-TINY-POSITION-WRITE.md`. Other collision/epsilon paths remain open in the parent.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): with a nonzero, unobstructed post-backoff request whose squared length is at most `1.0E-7`, A skips the position write while B's second predicate passes and writes the position. The exact consequences beyond that state write are not runtime tested.
- Finding IDs or checked absence/replacement path: F-MOVE-TINY-POSITION-WRITE; its earlier snapshot was invalidated for an A-source hash transcription error, now corrected and recorded in the snapshot history.

### Slice S-SUPPORT-GROUND: support-block and ground-state selection

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: `Entity.move` writes collision flags, then on-ground state, chooses support blocks at the resulting box or a reverse-horizontal probe, and updates the no-block/support-position fields; only the `move` caller is claimed.
- A evidence: `Entity.java` `setOnGroundWithMovement`/`checkSupportingBlock` lines 563-592 and `move` lines 634-646, SHA-256 `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`; `CollisionGetter.java` `findSupportingBlock` lines 105-120, SHA-256 `f21daa1ed0365cc16a2638373d702fbaeb4242ad12a80a41105cf43c711e6d95`.
- B evidence: `Entity.java` `setOnGroundWithMovement`/`checkSupportingBlock` lines 583-613 and `move` lines 656-669, SHA-256 `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`; `CollisionGetter.java` `findSupportingBlock` lines 128-143, SHA-256 `42301653b90eea59caf787a92699bd707d2454155b7aa19e4ff2de57beef774f`.
- State producers/writers -> consumers/readers: collision result -> verticalCollisionBelow/horizontalCollision -> onGround/mainSupportingBlockPos/onGroundNoBlocks; `findSupportingBlock` iterates `BlockCollisions`, picks minimum block-center distance to entity position, and breaks ties using `BlockPos.compareTo`.
- Parent slices / dependencies / closure evidence: S-ENTITY-MOVE. Exact-source search found `setOnGroundWithMovement` only at its declaration and the paired `Entity.move` caller on each side; B's extra `horizontalCollision` parameter reassigns the same flag already written before minor-collision classification.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for the paired `Entity.move` call, support-box epsilon, fallback movement reversal, `onGroundNoBlocks` persistence, candidate iteration and distance/tie-break order are textually equivalent. Block collision-shape/provider inventory remains open, so this closes support selection only for the same collision candidates.
- Finding IDs or checked absence/replacement path: no support-state difference in this bounded slice.

### Slice S-TRAVEL: travel branch formulas

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: method correspondence, controlled-local gate and branch dispatch; formulas were split into bounded child rows below. This parent remains open for cross-branch modifiers, fluid/block data and caller inventory.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java aiStep lines 2586-2681 and travel lines 2091-2217, SHA-256 324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java aiStep lines 2690-2779 and travel lines 2178-2314, SHA-256 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52.
- State producers/writers -> consumers/readers: input, attributes, fluids, effects, equipment, collision and external velocity feed travel; outputs feed later tick/callbacks.
- Parent slices / dependencies / closure evidence: S-LOCAL-TICK, S-INPUT-AXES, S-BLOCK-CONTACT; child rows close only their formulas, not the full modifier/data chains.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B splits travel into air, fluid and fall-flying helpers and relocates the fall-flying state update in aiStep. Air/fluid formula rows are compared narrowly; fall-flying math and eligibility dependencies remain open.
- Finding IDs or checked absence/replacement path: F-FALLFLY-EQUIPMENT-COMPONENT records B's generalized modern-only component gate; full branch and modifier audit remains open.

### Slice S-TRAVEL-AIR: land/air gravity and friction formulas

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: A's non-fluid/non-fall-flying branch, B's travelInAir, getEffectiveGravity and shared relative-friction helper; no conclusion about block-friction providers or caller branch state.
- A evidence: `LivingEntity.java` lines 2191-2211; gravity preamble lines 2092-2098; `handleRelativeFrictionAndCalculateMovement` lines 2252-2263; SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`.
- B evidence: `LivingEntity.java` lines 2173-2176, 2191-2214, 2351-2362; SHA-256 `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`.
- State producers/writers -> consumers/readers: current delta Y plus slow-falling effect -> gravity (`min(getGravity(), 0.01)` when descending with Slow Falling); ground-friction block -> horizontal/y decay; levitation amplifier -> vertical adjustment; resulting movement -> next tick.
- Parent slices / dependencies / closure evidence: S-TRAVEL. Formula correspondence is direct; Attributes.GRAVITY, block friction, effects, and exact default/data producers remain open in INV-MODIFIERS and INV-WORLD-MOVEMENT.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): within the matched air branch, B's helper returns the same gravity value A computes inline, and both retain the same levitation formula, gravity subtraction, chunk-availability fallback, `shouldDiscardFriction` gate, horizontal `friction * 0.91F` and flying-animal vertical multiplier. This closes only these expressions, not their input producers or reachable branch conditions.
- Finding IDs or checked absence/replacement path: no bounded air-formula finding.

### Slice S-TRAVEL-FLUID: water/lava formula factoring

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: A's water and lava branches versus B's travelInFluid water/else-lava branches, from relative input through movement/drag/gravity and horizontal-collision free-space check.
- A evidence: `LivingEntity.java` lines 2092-2150; SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`.
- B evidence: `LivingEntity.java` lines 2178-2187, 2216-2266; SHA-256 `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`.
- State producers/writers -> consumers/readers: water slowdown and WATER_MOVEMENT_EFFICIENCY; sprint and Dolphin's Grace; lava fluid height and jump threshold; descending/Slow Falling gravity; collision and `isFree` result.
- Parent slices / dependencies / closure evidence: S-TRAVEL; water/lava tag and fluid-height resources, effect/attribute values, and branch producer inventory remain open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for the corresponding water and lava cases, arithmetic and order match, including `0.54600006F`, `0.8F`, `0.02F`, float casts, `getFluidFallingAdjustedMovement`, lava half-scaling, `-gravity / 4.0`, and the final horizontal-collision jump. B factors gravity into `getEffectiveGravity` and factors the common post-branch test without changing the inspected formula. This is not a claim that all water/lava inputs or tags are equivalent.
- Finding IDs or checked absence/replacement path: no bounded fluid-formula finding.

### Slice S-TRAVEL-FALLFLY: gliding velocity formula

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: A inline fall-flying movement and B `travelFallFlying`/`updateFallFlyingMovement`, excluding collision damage, eligibility and gear state.
- A evidence: `LivingEntity.java` lines 2151-2190; SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`.
- B evidence: `LivingEntity.java` lines 2268-2302; SHA-256 `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`.
- State producers/writers -> consumers/readers: look vector and pitch; velocity and effective gravity; horizontal velocity feeds pull-up adjustment and collision-speed loss.
- Parent slices / dependencies / closure evidence: S-TRAVEL and S-FALLFLY-ELIGIBILITY. `calculateViewVector` is unchanged in both Entity sources (A lines 1530-1538, B lines 1619-1627), but proving the removed `min(1.0, look.length() / 0.4)` factor cannot affect reachable player movement still needs exact orientation/input bounds. Fall-flying collision response and flight-state update are separately open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B replaces `cos(pitch)^2 * min(1.0, look.length() / 0.4)` with `Mth.square(cos(pitch))`. The view-vector method returns trigonometric components but no enforced length postcondition has been established for every reachable player rotation; no movement equivalence is claimed yet.
- Finding IDs or checked absence/replacement path: none pending closure of the look-vector range and exact operation-order consequence.

### Slice S-FALLFLY-ELIGIBILITY: fall-flying equipment gates

- Inventory ID(s): INV-TICK, INV-MODIFIERS, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer jump input -> `Player.tryToStartFallFlying` -> server START_FALL_FLYING handler -> shared flag/update gate; ordinary Elytra and generalized component equipment.
- A evidence: `LocalPlayer.java` lines 741-745, SHA-256 `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583`; `Player.java` lines 1529-1538, SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`; `LivingEntity.java` lines 2716-2741, SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`; ElytraItem line 20 and Items registration line 883, hashes recorded in `findings/F-FALLFLY-EQUIPMENT-COMPONENT.md`.
- B evidence: `LocalPlayer.java` lines 735-737, SHA-256 `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0`; `Player.java` lines 1471-1472 and 1525-1536, SHA-256 `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de`; `LivingEntity.java` lines 2814-2848 and 3615-3622, SHA-256 `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`; `DataComponents.java` GLIDER and `Items.java` Elytra default, hashes recorded in the finding.
- State producers/writers -> consumers/readers: client jump -> command packet -> server equipment check -> shared fall-flying flag -> B fall-flying travel branch.
- Parent slices / dependencies / closure evidence: S-TRAVEL-FALLFLY; client and server gates plus A/B built-in Elytra durability boundary are paired. Custom/server-supplied item-component production remains an external-data dependency.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): source confirms B adds a component-based, any-matching-slot glide eligibility path; its built-in Elytra retains the A chest/Elytra/durability gate. The additional component case has no A representation and is explicitly dispositioned modern-only/out of scope, not a historical movement difference.
- Finding IDs or checked absence/replacement path: F-FALLFLY-EQUIPMENT-COMPONENT.

### Slice S-EXTERNAL: corrections and externally supplied movement

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: packet movement flag writer/decoder and accepted server movement handler traced; correction, impulse, mount, piston and tick-order consumers remain open.
- A evidence: `ServerboundMovePlayerPacket.java` contains no horizontal-collision field/flag (SHA-256 `03ff21dba5a0937e25c8ea0fe8d11146bf8595560115e4235176dc8e5d9d75db`); `ServerGamePacketListenerImpl.handleMovePlayer` lines 822-924 ends accepted movement with two-argument `setOnGroundWithMovement(onGround, movementDelta)` (SHA-256 `cc73bb8fab95346aea10f7018ba7de6c723cde8a52a4ae40629b5370b6ff6d10`). LocalPlayer.tick lines 191-210 and Entity.move piston path are also in scope.
- B evidence: `ServerboundMovePlayerPacket.java` adds `horizontalCollision`, reads/writes it in packet flag paths (lines 17, 49, 85-86, 120, 156, 186, 212; SHA-256 `d66f0025ee8606944fd5abb4a5ebd0d46c09556c3c2192d8c61d11d49b3a6519`); `ServerGamePacketListenerImpl.handleMovePlayer` lines 827-928 passes the flag to `setOnGroundWithMovement(onGround, horizontalCollision, movementDelta)` (SHA-256 `e4ad1ac2fa3236f659220c44dd4d5f63d9ef2ab5791bdabbfa2d229b14452a38`). LocalPlayer.tick lines 189-212 and Entity.move lines 619-680 are also in scope.
- State producers/writers -> consumers/readers: B LocalPlayer collision state -> movement packet -> accepted server handler -> server `horizontalCollision` writer. `Entity.move` recomputes that field; `LivingEntity` has later reads in travel/friction and auto-spin handling. Relative server-player tick and packet-handler ordering, plus any movement-relevant reads before recomputation, remain unclosed, so no effect or equivalence disposition is claimed.
- Parent slices / dependencies / closure evidence: S-LOCAL-TICK, S-ENTITY-MOVE; exact flag transport is mapped, but packet/world caller and writer-to-consumer order inventory remains required.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): no absence or equivalence claim.
- Finding IDs or checked absence/replacement path: none.
## Dependency queue and blockers

- Open dependencies: METHOD-INVENTORY, RESOURCE-CHAIN, SOURCE-AUDIT.
- METHOD-INVENTORY: close LocalPlayer and LivingEntity tick/input order; Entity.move support/collision/epsilon dependencies; fall-flying look-vector range/operation-order review; external player correction/impulse callers; and the B horizontal-collision packet writer-to-reader ordering before server movement recomputes collision state.
- RESOURCE-CHAIN: client jar hashes verified; compare referenced water/lava tags, movement block/fluid defaults/providers and applicable effect/attribute/equipment data from exact pair artifacts.
- SOURCE-AUDIT: independent reviewer assignment and full call-graph/inventory audit remain pending; the source worker must not self-audit this requirement.
- DEP-CHECKER resolved by canonical fix fba28fa154d29572263ea3f2c44cf1dc23134329, cherry-picked as 4223d9c; static schema gate only.

## Finding index

- F-BLOCK-CONTACT-TRAVERSE — findings/F-BLOCK-CONTACT-TRAVERSE.md.
- F-MOVE-TINY-POSITION-WRITE — findings/F-MOVE-TINY-POSITION-WRITE.md.
- F-FALLFLY-EQUIPMENT-COMPONENT — findings/F-FALLFLY-EQUIPMENT-COMPONENT.md (B-side component generalization; explicitly out of scope for historical emulation).

## Resume checkpoint

- Resume branch: feat/source-discovery-1-21-1-1-21-3-resume-2026-10-08 (created from immutable source checkpoint `9efca3a29af87debd09cf76d92affbf475e98e16`).
- Resume worktree: D:\Javastuff\LegacyParkourCompat\.task-worktrees\source-1211-1213-resume-2026-10-08.
- Starting checkpoint: `9efca3a29af87debd09cf76d92affbf475e98e16`. Source evidence checkpoint: `097fed3` (`docs: resume 1.21.1 movement source discovery`). Main at `6d89340a2f84a7aa045f119d73396346522c077d` was merged in `dda6efe`; the remaining report metadata update follows that merge.
- Main-merge review: the only main-side change to this pair was an earlier partial import of the same run and block-contact finding; the branch's fuller call-path/disposition evidence and all newer pair slices/findings were retained. Updated shared workflow guidance came from main. No additional source-semantic repair was needed.
- Closed bounded slices: S-INPUT-AXES (directional normalization/slow scaling only); S-BLOCK-CONTACT (intermediate-path callback difference); S-MOVE-TINY-POS-WRITE (tiny position-write condition); S-SUPPORT-GROUND (support-state selection for paired Entity.move only); S-TRAVEL-AIR (air-branch formula only); S-TRAVEL-FLUID (water/lava formula only); S-FALLFLY-ELIGIBILITY (component-only B extension dispositioned out of scope; default Elytra gates compared).
- Open slices: S-LOCAL-TICK, S-ENTITY-MOVE, S-TRAVEL, S-TRAVEL-FALLFLY in-progress; S-EXTERNAL pending. Required inventories remain partial/pending; full-pair freeze and independent audit remain pending.
- Finding handoff: F-BLOCK-CONTACT-TRAVERSE remains in immutable finding commit `07902783e412b7055b1b8d15d99ed5df92388f26`, SHA-256 `3538afb87392bd856c3b358af9b12c84341163d188c77738244603641900d686`. The tiny-position snapshot at `6aa873efa6f2fb7ffe3f545674859a9fff82273e` is superseded; corrected F-MOVE-TINY-POSITION-WRITE and F-FALLFLY-EQUIPMENT-COMPONENT are in `097fed3`, SHA-256s `771f4ac4f720f2a8bdfcc5b025a809448bcbee83619e861e248515fd8336a0d1` and `405af71090ba5bfe2a31b8c526df35be1810170fdf5d1ad8360bf78575801ff3`. These are source findings, not independently accepted snapshots. Independent acceptance and implementation handoff remain pending.
- Source identities: A 1.21.1 Mojmap source manifest 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48; B 1.21.3 Mojmap source manifest d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce; artifact manifest identities are recorded above.
- Next source-only commands, from the repository root:
  1. $a='D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.21.1\mojmap'; $b='D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.21.3\mojmap'; rg -n -C 5 'travelInAir|travelInFluid|travelFallFlying|updateFallFlying|checkSlowFallDistance' "$a\net\minecraft\world\entity\LivingEntity.java" "$b\net\minecraft\world\entity\LivingEntity.java"
  2. rg -n -C 6 'setOnGroundWithMovement|checkSupportingBlock|collide\\(|maybeBackOffFromEdge|actual.*lengthSqr|checkFallDamage' "$a\net\minecraft\world\entity\Entity.java" "$b\net\minecraft\world\entity\Entity.java"
  3. rg -n -C 5 'lerpTo|handleMovePlayer|ServerboundMovePlayer|push\\(|knockback|recordMovementThroughBlocks|ServerboundPlayerInputPacket' "$a\net\minecraft\client\player\LocalPlayer.java" "$b\net\minecraft\client\player\LocalPlayer.java" "$a\net\minecraft\world\entity\Entity.java" "$b\net\minecraft\world\entity\Entity.java"
- Then inspect exact paired callers/writers/readers for each hit, add bounded slices/findings under the existing source-only protocol, and run the static structure check only: python workflows\movement-discovery\check_completion.py workflows\source-campaign-2026-10-07\1.21.1--1.21.3
- Assumptions still open: whether the missing-current-chunk guard is ordinarily reachable, near-zero position-write consequences, all travel branch arithmetic, complete collision/modifier/resource chains, and external impulse/correction reachability.

## Implementation reconciliation

Must remain pending until blind-discovery freeze. Runtime Java implementation has not been inspected and will not be changed by this source-only task.

- Reconciliation status: pending
- Repository revision inspected: not inspected
- Finding -> implementation disposition/evidence: pending freeze
- Existing implementation without a frozen source finding: pending freeze
- Coverage gaps routed back to discovery slices: pending freeze

## Independent source audit

An independent reviewer has not yet been assigned and no audit has occurred.

- Reviewer: pending coordinator assignment; must differ from discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: none
- Concrete missed-slice routes (or `none found`): pending
- Misses routed to slice/finding IDs and owners: pending
- Reviewer evidence / date: pending

## Finding snapshots (not pair freeze)

- Status: resubmitted; this is a finding-only snapshot, not the full-pair freeze.
- Superseded finding snapshot: F-BLOCK-CONTACT-CLIENT-GATE at commit `e8271b2f1737729282ad93dacdadb43167202b80`, file SHA-256 `71d9783c2f5d8bd5636907ea710079ee8f088c15b9f26d73a92810c18338781c`. Invalidated after source review found both `LocalPlayer.isEffectiveAi()` overrides return true, making B's local-player callback guard reachable.
- Superseded swept snapshot: F-BLOCK-CONTACT-TRAVERSE at commit `3c595d1139b6f85f3cc525a7f9e370f2fc61a114`, file SHA-256 `ccdf65eaf5177d7e92a349d8faadad86bd46f4dbdd769c964d65b8647f7ffb4c`; replaced by a snapshot that records the exact `BlockGetter.boxTraverseBlocks` member and source hash.
- Current finding: F-BLOCK-CONTACT-TRAVERSE; immutable source commit `07902783e412b7055b1b8d15d99ed5df92388f26`; file SHA-256 `3538afb87392bd856c3b358af9b12c84341163d188c77738244603641900d686`.
- Superseded tiny-position snapshot: F-MOVE-TINY-POSITION-WRITE at commit `6aa873efa6f2fb7ffe3f545674859a9fff82273e`, file SHA-256 `bc3bcb15fe51d8cf3917d788c069ada356c332f609bcc139b0703313ce5368c1`; invalidated because its A `Entity.java` digest contained a transcription error (`...c063...`) and did not match the verified source file hash. The source conclusion is unchanged; corrected evidence and review remain pending.
- Current corrected finding: F-MOVE-TINY-POSITION-WRITE; source file hash SHA-256 `771f4ac4f720f2a8bdfcc5b025a809448bcbee83619e861e248515fd8336a0d1`; committed in `097fed3`; corrected snapshot awaits independent review.
- Current source-only finding: F-FALLFLY-EQUIPMENT-COMPONENT; file SHA-256 `405af71090ba5bfe2a31b8c526df35be1810170fdf5d1ad8360bf78575801ff3`; committed in `097fed3`, not independently reviewed. Its added component path is explicitly modern-only/out of scope; it does not assert changed movement for default Elytra.
- Evidence identities: A Mojmap source manifest `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`, artifact manifest `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486`; B Mojmap source manifest `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`, artifact manifest `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf`.
- Independent finding reviewer previously identified as `01a116ce-dfe4-7b11-b7e6-d62f5014e356`; the old tiny-position hash is invalidated, and none of the three current finding hashes has independent acceptance recorded.
- Implementation handoff: blocked until that reviewer accepts both exact finding snapshots. Pair status remains active; remaining inventories and full-pair audit are open.

## Source audit closure

- Coverage counts: 3 findings, 4 compared-no-difference, 4 in-progress, 1 pending (bounded rows; parent slices remain open where listed above; S-EXTERNAL now has partial packet evidence but remains pending).
- Inventory status: INV-TICK partial; INV-STATE partial; INV-COLLISION partial; INV-WORLD-MOVEMENT partial; INV-MODIFIERS partial; INV-EXTERNAL pending; INV-EXCLUSIONS partial.
- Open dependencies: METHOD-INVENTORY, RESOURCE-CHAIN, SOURCE-AUDIT.
- Full-pair blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed.
