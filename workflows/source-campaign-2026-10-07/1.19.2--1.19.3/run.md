# Discovery: 1.19.2 to 1.19.3

- Status: active
- Scope: direct client player movement; older A = 1.19.2; newer B = 1.19.3
- Repository revision and start date: source comparison baseline `002137b227676caea77f6832b9f4c8d0b6200bff`; started 2026-10-07 (Europe/Vienna); discovery schema commits `40c34f5`, `2422192`, `fba28fa`
- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap/Mojmap; exact releases and official mapping files verified from the published readiness/provenance manifests below
- Source preparation owner / command / log / readiness marker: shared source owner (worktree `3e2d`); command `gradlew.bat decompileMinecraft --versions=1.19.2,1.19.3,1.19.4 --mappings=mojmap --decompiler-heap=4G --output-root=.../staging/mojmap-1.19.2-to-1.19.4-45d0418942a144298b19fb4ac74ff06a --cache-directory=.../artifacts`; successful log `build/movement-campaign-2026-10-07/staging/mojmap-1.19.2-to-1.19.4-45d0418942a144298b19fb4ac74ff06a/gradle.full.log`; exact readiness markers verified below
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1; Java 25.0.3+9-LTS; Vineflower 1.12.0; TinyRemapper 0.14.1; Mapping-IO 0.9.1; ASM 9.10.1; Gson 2.14.0; heap 4G
- Discovery author(s): source worker for this pair
- Independent reviewer (must differ from discovery authors): pending coordinator assignment

## Artifact manifest

### A — 1.19.2

- Exact requested/resolved release: 1.19.2 / 1.19.2; readiness `build/movement-campaign-2026-10-07/ready/1.19.2/mojmap.ready.json`, SHA-256 `90f5a351c60a1aab160b640567b716bbc563e62d92e7e555df55c4ce7952492c`; provenance JSON SHA-256 `c0c148d3c3a0092adc59c29c25f2106f3a5398156a4f266882384804b3534280`
- Source root: `build/movement-campaign-2026-10-07/ready/1.19.2/mojmap/` (4,480 Java files)
- Original client jar SHA-256: `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db`
- CLI mode/namespace: `mojmap` / Mojmap official names
- Mapping coordinate/build/file/SHA-256: official Mojang 1.19.2 `client_mappings.txt`; `c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76`
- Bridge mapping: not used
- Remapped jar SHA-256: `a257c4c97ceac50fbc069dc6051dff5f0263716547ede05e85c44d675ade592f`
- Cited Java source paths/SHA-256: aggregate `mojmap.sources.sha256` is `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`; cited body hashes below were checked against the manifest
- Cited resource entries/SHA-256: official `client.jar` movement tags were inspected; paired entry hashes are recorded in the source comparison section below
- Required external data and provenance: original `client.jar` SHA-256 `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db`; mapped jar SHA-256 `a257c4c97ceac50fbc069dc6051dff5f0263716547ede05e85c44d675ade592f`; source manifest `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`; artifact manifest `d0197d78578241b8cdde1123a3dee64aa279341681a7edde9f867689f9bb3490`; diagnostic manifest `ce308a5f2902c3d7f7c5e330935d130e5ce56302d03b7fa687bf14493400e44e`; verified against readiness record

### B — 1.19.3

- Exact requested/resolved release: 1.19.3 / 1.19.3; readiness `build/movement-campaign-2026-10-07/ready/1.19.3/mojmap.ready.json`, SHA-256 `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6`; provenance JSON SHA-256 `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe`
- Source root: `build/movement-campaign-2026-10-07/ready/1.19.3/mojmap/` (4,618 Java files)
- Original client jar SHA-256: `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6`
- CLI mode/namespace: `mojmap` / Mojmap official names
- Mapping coordinate/build/file/SHA-256: official Mojang 1.19.3 `client_mappings.txt`; `f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b`
- Bridge mapping: not used
- Remapped jar SHA-256: `3be15cd54092cbac9853a67445a42b38d5c3b0d61503bd79b0d76589357d871e`
- Cited Java source paths/SHA-256: aggregate `mojmap.sources.sha256` is `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`; cited body hashes below were checked against the manifest
- Cited resource entries/SHA-256: official `client.jar` movement tags were inspected; paired entry hashes are recorded in the source comparison section below
- Required external data and provenance: original `client.jar` SHA-256 `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6`; mapped jar SHA-256 `3be15cd54092cbac9853a67445a42b38d5c3b0d61503bd79b0d76589357d871e`; source manifest `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`; artifact manifest `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652`; diagnostic manifest `95e8189acb6b384b21a584f28d5e8d23922996ea9f446e17e54734ccba840e60`; verified against readiness record

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending; only inventory setup is committed, not a source freeze
- Evidence inventory and finding IDs included at freeze: none yet
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; no old/mod implementation or either wiki/audit read
- Source/mapping hashes covered by freeze: pending

## Correspondence and call order

The readiness records and cited manifests are verified. The navigation plan covers: local input sampling and local/super tick order; pre-travel predicates/state updates; travel dispatch and every reachable branch; post-travel work/callbacks; pose/dimensions/eye height and movement state writers; collision, support, shapes and callbacks; block/fluid registrations/data; effect/attribute/enchantment/equipment chains; externally supplied player movement inputs and consumers. Only the method correspondences explicitly recorded below are certified so far; inventory closure remains open.

Health, regeneration, hunger/food, saturation, exhaustion, damage/combat simulations, and independent non-player physics are out of scope. Direct reads of vanilla state remain in scope only as movement predicate inputs. Modern-only blocks/features do not acquire older behavior. Preserve exact casts, operation order, floating-point behavior and quirks.

## Required source inventories

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1-input,S3-travel-dispatch,S3-ground-air,S3-jump,S3-mounted-sprint,S3-climb-swim,S3-glide,S3-fall-distance-farm-trample; evidence=paired core method anchors, checked exclusion E-001 and findings F-002/F-003/F-004 recorded; complete tick graph still open
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S2-player-state,S4-move-core,S4-step-edge,S3-fall-distance-farm-trample; evidence=fallDistance writer-to-landing callback path recorded in F-004; full both-side writer/consumer map remains open
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S4-move-core,S4-step-edge,S4-shapes-query,S4-callbacks,S5-block-shapes,S3-fall-distance-farm-trample; evidence=FarmBlock landing callback and its shape transition recorded in F-004; full provider/registration/neighbor inventory remains open
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S5-block-registration,S5-block-shapes,S5-fluid-data,S5-block-coefficients,S5-movement-tags,S5-water-source,S5-lava-source,S3-fall-distance-farm-trample; evidence=bounded coefficient/tag checks plus F-002/F-003/F-004; remaining Java/resource inventory open
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and applications/removals/conditions: status=pending; slice_ids=S6-effects-attributes,S6-enchantments-equipment,S6-player-movement-speed-effects; evidence=bounded speed/slowness attribute path compared-no-difference; other effect/enchantment/equipment paths remain open
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=S7-external-velocity,S7-mount-transition,S3-mounted-sprint,S5-water-source,S5-lava-source,S3-fall-distance-farm-trample; evidence=E-001 checked mounted consumer boundary and F-002/F-003/F-004 world-state inputs; full producer/consumer inventory open
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=pending scope/consumer audit; direct vanilla-state reads will be listed as movement inputs only

## Coverage ledger

These are initial bounded behavior families and all are open. After exact source publication, split any family spanning multiple methods into method/body-range slices with both-side hashes and explicit producer -> consumer edges. No row below implies source equivalence or completion.

### Slice S1-input: Input producer and local tick ordering

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: input sampling, local player tick/super-tick/travel call order, yaw conversion, diagonal/sneak/use scaling, jump/sprint timers and riding/flight gates; exact method boundaries pending.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: pending correspondence; track input fields, timers and travel flags.
- Parent slices / dependencies / closure evidence: source identity and exact call graph pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): no source comparison yet; avoid claims from absent/unverified trees.
- Finding IDs or checked absence/replacement path: none yet

### Slice S2-player-state: Pose, dimensions and movement gates

- Inventory ID(s): INV-STATE, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: pose/size/eye-height transitions, movement-relevant field initialization/reset, flight/swim/crawl, sprint/jump predicates, edge sneak and active item; inspect excluded state only at direct vanilla movement consumers.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: constructors/defaults/transitions -> movement predicates and collision/fluid query dimensions; pending exact field map.
- Parent slices / dependencies / closure evidence: S1-input; exact writer/reader closures pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source audit pending; excluded health/food producer systems will not become movement findings.
- Finding IDs or checked absence/replacement path: none yet

### Slice S3-travel-dispatch: Travel branch selection and post-travel order

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: dispatch guards, branch order, entry/exit work, and post-travel state updates/callbacks.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: tick flags and attributes -> branch predicates and post-travel writes; pending exact correspondence.
- Parent slices / dependencies / closure evidence: S1-input, S2-player-state; all branches pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): full dispatch and call order not yet sourced.
- Finding IDs or checked absence/replacement path: none yet

### Slice S3-ground-air: Ground/air acceleration, friction and cutoffs

- Inventory ID(s): INV-TICK, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: full ground/air calculation incl. friction/gravity/drag, velocity thresholds, clamps, constants and post-travel math; exact body ranges pending.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: movement speed/friction sources -> acceleration; velocity and ground flags -> branch/cutoff consumers; pending.
- Parent slices / dependencies / closure evidence: S3-travel-dispatch, S5-block-registration, S6-effects-attributes.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): no arithmetic conclusion until paired bodies and dependencies are verified.
- Finding IDs or checked absence/replacement path: none yet

### Slice S3-jump: Jump gates, power and sprint-jump impulse

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: jump predicates, jump power, impulse, cooldown, auto-jump and jump state writes.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: input/timers/ground/attributes/effects -> jump; jump writes -> travel; pending exact edges.
- Parent slices / dependencies / closure evidence: S1-input, S2-player-state, S3-travel-dispatch, S6-effects-attributes.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): no source comparison yet.
- Finding IDs or checked absence/replacement path: none yet

### Slice S3-climb-swim: Climb, water/lava and swimming branches

- Inventory ID(s): INV-TICK, INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: travel branch conditions, clamps, acceleration/drag, fluid-height/push reads and post-branch writes.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: fluid state/properties and attributes -> player travel; pending.
- Parent slices / dependencies / closure evidence: S3-travel-dispatch, S5-fluid-data, S6-effects-attributes.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): fluids and player-only interactions require exact Java/resource evidence.
- Finding IDs or checked absence/replacement path: none yet

### Slice S3-glide: Elytra/gliding movement

- Inventory ID(s): INV-TICK, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: gliding branch calculations, eligibility transitions, equipment and post-travel state writes.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: equipment/pose/flags -> glide dispatch and calculations; pending.
- Parent slices / dependencies / closure evidence: S3-travel-dispatch, S2-player-state, S6-enchantments-equipment.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact reachability and arithmetic are not established.
- Finding IDs or checked absence/replacement path: none yet

### Slice S4-move-core: Entity movement, position and collision response

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: movement entry, box/position updates, axis order, collision/ground flags and velocity cancellation/restitution.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: travel velocity -> movement/query -> position, box and flags; pending exact writers.
- Parent slices / dependencies / closure evidence: travel branches; query/shape helpers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): no whole-movement no-difference claim permitted.
- Finding IDs or checked absence/replacement path: none yet

### Slice S4-step-edge: Step candidates, edge probing and support

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: step-up alternatives/ties, edge sneak probes, support lookup and repeated per-tick movement timing.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: pose/dimensions/velocity -> probe inputs; support/collision results -> flags and movement.
- Parent slices / dependencies / closure evidence: S2-player-state, S4-move-core, S4-shapes-query, S5-block-shapes.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending both-side shape/query closure.
- Finding IDs or checked absence/replacement path: none yet

### Slice S4-shapes-query: AABB/voxel shape and world query path

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: called AABB/shape operations, world collision lookup/filtering, shape context, dimensions and candidate/tie selection.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: player box/state/shape context -> collision queries -> selected movement response; pending.
- Parent slices / dependencies / closure evidence: S4-move-core, S4-step-edge; all called helpers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): method correspondence and relevant closure not yet available.
- Finding IDs or checked absence/replacement path: none yet

### Slice S4-callbacks: Landing, collision callbacks and timing

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: player-reachable callbacks, invocation conditions/order, support/landing velocity changes and relevant block overrides.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: movement query/collision result -> callback -> player velocity/flags; pending exact implementations.
- Parent slices / dependencies / closure evidence: S4-move-core and S5 block registration/subclass inventories.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): base and overriding callback bodies not yet enumerated.
- Finding IDs or checked absence/replacement path: none yet

### Slice S5-block-registration: Movement properties and registrations

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: registered blocks/states, friction/speed/jump properties, defaults and state/neighbor conditions.
- A evidence: pending source/resource ranges
- B evidence: pending source/resource ranges
- State producers/writers -> consumers/readers: registration/default/neighbor data -> player movement consumer; pending registry inventory.
- Parent slices / dependencies / closure evidence: S3-ground-air, S4-callbacks; exact consumer and registries pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): no absence claim from failed symbol searches.
- Finding IDs or checked absence/replacement path: none yet

### Slice S5-block-shapes: Shape providers, overrides and neighboring blocks

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: every reachable movement-relevant registered shape override/provider, context inputs, support/neighbors and modern-only block disposition.
- A evidence: pending source/resource ranges
- B evidence: pending source/resource ranges
- State producers/writers -> consumers/readers: block registration/state/neighbors/context -> shape provider -> support/collision query; pending.
- Parent slices / dependencies / closure evidence: S4-step-edge, S4-shapes-query; complete override/registration enumerations pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): modern-only blocks will not receive emulated behavior; prove version registries before classification.
- Finding IDs or checked absence/replacement path: none yet

### Slice S5-fluid-data: Fluid state, flow and push data

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: registrations/defaults, height/flow vector calculation, contact/push/bubble behavior and resource-backed data.
- A evidence: pending source/resource ranges
- B evidence: pending source/resource ranges
- State producers/writers -> consumers/readers: fluid data/contact -> player flags/velocity/travel; pending.
- Parent slices / dependencies / closure evidence: S3-climb-swim, S4-move-core; server/external boundaries pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Java and client-jar resource comparison needed.
- Finding IDs or checked absence/replacement path: none yet

### Slice S6-effects-attributes: Movement effects and attribute chains

- Inventory ID(s): INV-MODIFIERS, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: movement consumers -> attributes/helpers -> modifier aggregation/application/removal/defaults -> registrations for effects and movement attributes.
- A evidence: pending source/resource ranges
- B evidence: pending source/resource ranges
- State producers/writers -> consumers/readers: effect/application/equipment/server-synced values -> modifier state -> player travel/sprint/jump consumers; pending.
- Parent slices / dependencies / closure evidence: S2-player-state, S3-ground-air, S3-jump, S3-climb-swim; explicit effect inventory pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace, Blindness and all other discovered movement consumers require disposition.
- Finding IDs or checked absence/replacement path: none yet

### Slice S6-enchantments-equipment: Enchantments, items and gear

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: movement enchantment/item consumers, formulas, applicability/slots, equipment/use-item slowdown and resource/tag conditions.
- A evidence: pending source/resource ranges
- B evidence: pending source/resource ranges
- State producers/writers -> consumers/readers: registration/tags/equipment/applications -> attributes/helpers/pose/travel consumer; pending.
- Parent slices / dependencies / closure evidence: S2-player-state, S3-glide, S3-climb-swim, S6-effects-attributes.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): explicitly inspect Depth Strider, Soul Speed, Swift Sneak, Frost Walker, Riptide, Elytra and other discovered entries; record synchronized/server data boundary.
- Finding IDs or checked absence/replacement path: none yet

### Slice S7-external-velocity: External player movement inputs

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: player consumers of corrections, velocities, push/knockback, explosions, piston displacement and launch items; no independent nonplayer simulation.
- A evidence: pending source ranges
- B evidence: pending source ranges
- State producers/writers -> consumers/readers: packet/server/external producer -> player state writer -> later movement consumer; pending.
- Parent slices / dependencies / closure evidence: S4-move-core and stage-3 consumers; exact network/client path pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): distinguish client-computed behavior from externally supplied values.
- Finding IDs or checked absence/replacement path: none yet

### Slice S7-mount-transition: Player mount and dismount state

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: local player mount/dismount transition path and movement-state reset/gate effects only.
- A evidence: pending source ranges
- B evidence: pending source ranges
- State producers/writers -> consumers/readers: transition/correction inputs -> player flags/velocity/position -> local tick gates; pending.
- Parent slices / dependencies / closure evidence: S1-input, S2-player-state; no mount simulation in scope.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): player-only method/caller closure not yet inspected.
- Finding IDs or checked absence/replacement path: none yet

### Slice S7-closure: Cross-stage state writers and dependencies

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT, INV-MODIFIERS, INV-EXTERNAL, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: revisit every unresolved writer/consumer, callback, registration, resource and newly discovered dependency; enumerate cross-mechanic interactions after parent slices close.
- A evidence: pending source ranges and inventories
- B evidence: pending source ranges and inventories
- State producers/writers -> consumers/readers: full two-sided player-state dependency graph not yet collected.
- Parent slices / dependencies / closure evidence: all listed stages and dependencies; this is the final closure slice.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): cannot close until bounded child slices and dependencies are terminal.
- Finding IDs or checked absence/replacement path: none yet

### Slice S3-mounted-sprint: Passenger sprint eligibility state

- Inventory ID(s): INV-TICK, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: the local sprint-start condition, its food/permission condition and passenger ground clause; preserve surrounding shift, impulse, item-use and blindness guards. Sprint command send is an external boundary, not proof of a mounted trajectory change.
- A evidence: `net/minecraft/client/player/LocalPlayer.java`::`aiStep()`, lines 772-780, SHA-256 `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef`; older food predicate and ground/water gate.
- B evidence: `net/minecraft/client/player/LocalPlayer.java`::`aiStep()`, lines 693-701, and `hasEnoughFoodToStartSprinting()`, lines 1047-1049, SHA-256 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`; passenger ground and food bypass predicates. `tick()` lines 188-207 and `sendIsSprintingIfNeeded()` lines 271-279 expose the outbound state command.
- State producers/writers -> consumers/readers: sampled forward/shift input and vehicle grounded state + food/ability predicate -> `setSprinting`; sprint state -> client command packet -> external server consumer (not yet closed).
- Parent slices / dependencies / closure evidence: S1-input and S7-mount-transition; D3 mounted sprint-state consumer remains open for trajectory relevance.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): paired source change only updates sprint state/command while passenger. `Player.travel` skips the affected swimming/flight operations for passengers, the passenger position is assigned by the vehicle `positionRider` path, and a B low-food sprint is cleared after dismount before normal travel. Vehicle physics/command consumption is outside player-movement scope. This source delta is retained as checked exclusion E-001, not an in-scope finding.
- Finding IDs or checked absence/replacement path: checked scope exclusion E-001 above; no in-scope finding file

### Slice S5-water-source: Water source-conversion rule

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: `FlowingFluid.getNewLiquid` source-conversion gate and the water-specific rule provider/default; existing neighbor count and solid/source-below guards remain in their prior order.
- A evidence: `FlowingFluid.java` `getNewLiquid(LevelReader,...)` lines 154-178 and abstract `canConvertToSource()` line 235; `WaterFluid.java` `canConvertToSource()` lines 72-74, SHA-256 `bbfb661b524ac92f74579cd4b61c1a25df00ecc4bfe775a5516f7e4a7c8768f3` / `9d305751d39633ee519e260be184208b20cdbb3422acadb84f8871722c1a383f`.
- B evidence: `FlowingFluid.java` `getNewLiquid(Level,...)` lines 154-178 and abstract `canConvertToSource(Level)` line 235; `WaterFluid.java` `canConvertToSource(Level)` lines 73-75; `GameRules.java` lines 162-164, SHA-256 `d84ceb3c0b83da25fb68599e16cc2298106e745a9ef0c9f32f616d097a8ec56c` / `3e10320356b11d415d8e4f3ea2b7fbb27e5bd8abdc45d524afb18b834e613e5f` / `9f7c0b5ff3ebc46cac4275e6c856610e00e796f9cf5368d9b380ac0e478e1bae`.
- State producers/writers -> consumers/readers: gamerule-backed source eligibility -> server/world fluid block state -> client fluid-state/contact reader `LivingEntity.travel`; incoming world-state provenance and resulting topology are external.
- Parent slices / dependencies / closure evidence: S3-climb-swim and S7-external-velocity; D3 and remaining S5-fluid-data resource/provider inventory remain open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B rule default `true` matches A. Non-default false prevents source conversion only when existing source-neighbor and support preconditions pass; movement impact requires resulting world-state difference.
- Finding IDs or checked absence/replacement path: F-002, `findings/F-002-water-source-conversion.md`

### Slice S5-lava-source: Lava source-conversion rule

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: `FlowingFluid.getNewLiquid` source-conversion gate and the lava-specific rule provider/default; existing neighbor count and solid/source-below guards remain in their prior order.
- A evidence: `FlowingFluid.java` `getNewLiquid(LevelReader,...)` lines 154-178 and abstract `canConvertToSource()` line 235; `LavaFluid.java` `canConvertToSource()` lines 189-191, SHA-256 `bbfb661b524ac92f74579cd4b61c1a25df00ecc4bfe775a5516f7e4a7c8768f3` / `0f3fab21b95d66f9ecbe216b8bfc888aa033718b17e273de0e0f0be99bd1fc6c`.
- B evidence: `FlowingFluid.java` `getNewLiquid(Level,...)` lines 154-178 and abstract `canConvertToSource(Level)` line 235; `LavaFluid.java` `canConvertToSource(Level)` lines 189-191; `GameRules.java` lines 165-167, SHA-256 `d84ceb3c0b83da25fb68599e16cc2298106e745a9ef0c9f32f616d097a8ec56c` / `1d239a6e0c99aee386b5dfb0244a29f4a48689808f9be64dda89c9a63b9fc6da` / `9f7c0b5ff3ebc46cac4275e6c856610e00e796f9cf5368d9b380ac0e478e1bae`.
- State producers/writers -> consumers/readers: gamerule-backed source eligibility -> server/world fluid block state -> client fluid-state/contact reader `LivingEntity.travel`; incoming world-state provenance and resulting topology are external.
- Parent slices / dependencies / closure evidence: S3-climb-swim and S7-external-velocity; D3 and remaining S5-fluid-data resource/provider inventory remain open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B rule default `false` matches A. Non-default true permits source conversion only when existing source-neighbor and support preconditions pass; movement impact requires resulting world-state difference.
- Finding IDs or checked absence/replacement path: F-003, `findings/F-003-lava-source-conversion.md`

### Slice S3-fall-distance-farm-trample: Glide fall-distance value passed to farmland landing callback

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: the fall-flying fallDistance write in player-reachable `LivingEntity.travel`, its on-ground callback route through `Entity.move` and `Entity.checkFallDamage`, and the server-side `FarmBlock.fallOn` predicate plus farmland-to-dirt shape transition.
- A evidence: `net/minecraft/world/entity/LivingEntity.java` `travel(Vec3)` lines 2107-2111 sets `fallDistance = 1.0F` when vertical movement is greater than `-0.5`; SHA-256 `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77`. `Entity.java` `move` lines 594-608 calls `checkFallDamage` before `updateEntityAfterFallOn`, and `checkFallDamage` lines 991-1001 passes positive `fallDistance` to the landing block's `fallOn`; SHA-256 `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`. `FarmBlock.java` `fallOn` lines 89-103 and `getShape` lines 29, 63-65; SHA-256 `ef03ebba83a5621c1f91bb4f777bd69f1ba3ab6afbc9ed04bcbbbba62cf90fbb`.
- B evidence: `net/minecraft/world/entity/LivingEntity.java` `travel(Vec3)` lines 2117-2119 calls `checkSlowFallDistance`; B `Entity.java` helper lines 2147-2151 resets only if vertical movement is greater than `-0.5` and `fallDistance > 1.0F`; SHA-256 `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`. `Entity.java` `move` lines 594-608 calls `checkFallDamage` before `updateEntityAfterFallOn`, and `checkFallDamage` lines 1000-1010 passes positive `fallDistance` to `fallOn`; SHA-256 `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6548983667adc32`. `FarmBlock.java` has the same callback and 15/16-height shape as A, SHA-256 `ef03ebba83a5621c1f91bb4f777bd69f1ba3ab6afbc9ed04bcbbbba62cf90fbb`. `Blocks.java` registers `DIRT` as `new Block(...)` at lines 63-65 (A) / 68-70 (B); `BlockBehaviour.java` default `getShape` returns `Shapes.block()` at lines 278-280 (A) / 288-290 (B). Supporting hashes: `Blocks.java` `f532585f836ee7ad685d368889ed13d735c570ef38971db0c1d2a7debb4cf27b` / `9944877c941fa209d2a8b9a7d4eb6333edf2dfca255a3e90601242483eb7a476`; `BlockBehaviour.java` `c0e62233fa21be352953edda6b411727762165fa2cc8d645c7c08406f92df08b` / `183ea122421dc3c0649ff0c64affda3d3cc3090c9fc2e01d96da44410d4b39c1`.
- State producers/writers -> consumers/readers: fall-flying player travel -> `fallDistance` -> on-ground `Entity.checkFallDamage` -> `FarmBlock.fallOn` randomized server block update -> replicated world block state -> later player collision shape query.
- Parent slices / dependencies / closure evidence: S3-glide, S4-move-core, S4-callbacks, S5-block-shapes, S7-external-velocity; D1/D3 and the complete callback/shape/provider inventory remain open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): when a player is fall-flying, vertical movement is above `-0.5`, pre-branch `fallDistance` is below `1.0F`, and the player lands on farmland on the server, A replaces the value with `1.0F` while B retains it. `Entity.checkFallDamage` supplies a positive value to `FarmBlock.fallOn`; its random threshold is `fallDistance - 0.5F`, so A and B have different trample intervals (and B does not enter the callback if the retained value is zero). If the server updates farmland to dirt, the world state can affect a later collision: farmland uses a 15/16-height shape while dirt inherits the full-block shape. The callback, block registration and shape implementation are identical across releases; the source-confirmed delta is the upstream travel value. Server RNG/world update and delivery to the client are external; no actual later client trajectory is claimed.
- Finding IDs or checked absence/replacement path: F-004, `findings/F-004-fall-distance-farmland-shape.md`

### Slice S6-player-movement-speed-effects: Speed and slowness attribute modifiers

- Inventory ID(s): INV-MODIFIERS, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: vanilla speed/slowness registrations targeting `Attributes.MOVEMENT_SPEED`, amplifier scaling and add/remove behavior, attribute aggregation, player ai-step copy to `speed`, and the ground friction speed helper read.
- A evidence: `MobEffects.java` lines 13-24 registers speed `+0.2F` and slowness `-0.15F`, both `MULTIPLY_TOTAL`; file SHA-256 `1e8122137e459c09ceee822668c27a4bcb32100dd1f53570e868b9faee0bdcb9`. `MobEffect.java` `addAttributeModifiers` lines 163-174, `removeAttributeModifiers` lines 154-161, and `getAttributeModifierValue` lines 176-178; full file SHA-256 `071a7729b6ebc458de29cc742b338933bca18fab63090722a646d8840336325a`. `AttributeInstance.java` `calculateValue` lines 130-148; full file SHA-256 `a4e677efd64a8f9c44e05afa14b83618d7dad9744e34f7e04aa463ecdd576ff0`. `Player.java` `aiStep` lines 500-562, including `setSpeed((float)getAttributeValue(MOVEMENT_SPEED))` at line 523; full file SHA-256 `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1`.
- B evidence: `MobEffects.java` lines 14-25 registers the same two values, operations and UUIDs; file SHA-256 `18a1190e1127ab4b3822d02aa11f81932db4f01f7b9c4e5a85c7f531b412ef8b`. `MobEffect.java` `addAttributeModifiers` lines 163-174, `removeAttributeModifiers` lines 154-161, and `getAttributeModifierValue` lines 176-178, full file SHA-256 `2671c14ca3a494c3668c2bcdf5c6268be8f18e833f011fbcd7befb63ffb3acf0`. `AttributeInstance.java` same `calculateValue` lines 130-148, full file SHA-256 `5b6d3067d0c8543230a01743c3cbee403175788731252a05e6ec8711bfd75f2`. `Player.java` `aiStep` lines 496-558, same method body and setSpeed read at line 519; full file SHA-256 `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`.
- State producers/writers -> consumers/readers: effect amplifier -> `MobEffect` modifier value -> `AttributeInstance` aggregation -> `Player.aiStep` writes `LivingEntity.speed` -> `LivingEntity.getFrictionInfluencedSpeed` reads it in movement; the paired helper body is A lines 2239-2241 / B lines 2246-2248.
- Parent slices / dependencies / closure evidence: S1-input, S3-ground-air, S3-travel-dispatch; full effect-state application, non-attribute movement effects, synchronized/external modifiers and S6-enchantments-equipment remain open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for an active speed or slowness effect, both versions register the same attribute, UUID, amount, operation and amplifier-dependent modifier flow. Attribute modifier aggregation uses the same order/body, and player ai-step copies the resulting movement attribute into `speed` on both sides; the ground friction speed helper is text-identical. This bounded vanilla effect-to-player-speed path has no source difference. It does not close direct slow-falling/Dolphin's Grace branches, other effect applications, equipment, enchantments, or external attribute updates.
- Finding IDs or checked absence/replacement path: paired bounded registration/application/aggregation/player-speed bodies checked; no difference.

### Slice S5-block-coefficients: Existing friction, speed and jump factors

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: the registered vanilla block `friction`, `speedFactor`, and `jumpFactor` values; shared property storage/getters; player ground-friction and block speed/jump-factor read path.
- A evidence: `Blocks.java` lines 895-899, 933-937, 1717-1720, 1874-1877, 2323-2327, 2911-2914, 3371-3376; `Block.java` getters `getFriction/getSpeedFactor/getJumpFactor` lines 403-413; SHA-256 `f532585f836ee7ad685d368889ed13d735c570ef38971db0c1d2a7debb4cf27b` / `e95d5abe175a3648b697e542981ba01535157093d133b774aadef719393ccb8f`.
- B evidence: `Blocks.java` lines 1217-1221, 1255-1259, 2091-2094, 2257-2260, 2809-2813, 3397-3400, 3901-3905; `Block.java` same getters lines 397-407; SHA-256 `9944877c941fa209d2a8b9a7d4eb6333edf2dfca255a3e90601242483eb7a476` / `c7cabe648cf2a153f892499560492204a0fb3b6b4cbcd3e209ea4a08a8acca60`.
- State producers/writers -> consumers/readers: `BlockBehaviour.Properties` builder -> `Block` stored fields -> `LivingEntity.travel` ground friction and `Entity` block speed/jump factor readers; A/B stored-field getters preserve the same values.
- Parent slices / dependencies / closure evidence: S3-ground-air; bounded to existing registered friction/speed/jump coefficients, not shape subclasses or callbacks. Full S5-block-registration remains open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): all six existing non-default coefficient registrations and values match; getter behavior returns the stored field on both endpoints. The source-tree occurrence inventory contains no additional `friction`, `speedFactor`, or `jumpFactor` declarations outside `Blocks.java` and the property builder. No changed coefficient is established in this bounded slice.
- Finding IDs or checked absence/replacement path: checked no difference in the coefficient-registration/getter path; new block shape and callback behavior remains in S5-block-shapes/S4-callbacks.

### Slice S5-movement-tags: Movement tag values referenced by player/world path

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: version-matched values for the block/fluid/item tags referenced by the indexed player movement sources; direct call paths remain separately open where method slices are not yet closed.
- A evidence: exact `client.jar` entries under `data/minecraft/tags`: `blocks/climbable.json`, `blocks/fences.json`, `blocks/fire.json`, `blocks/portals.json`, `blocks/signs.json`, `blocks/soul_speed_blocks.json`, `blocks/walls.json`, `fluids/lava.json`, `fluids/water.json`, `items/freeze_immune_wearables.json`; A hashes are listed in the paired-comparison resource checkpoint. Movement consumers include `LivingEntity` lines 1466-1470 and `Entity` lines 734-744; A source hashes `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` / `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`.
- B evidence: the same exact client.jar entries and SHA-256 values as A are listed in the paired-comparison resource checkpoint; B movement consumers include `LivingEntity` lines 1473-1477 and `Entity` lines 742-752; B source hashes `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` / `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6548983667adc32`.
- State producers/writers -> consumers/readers: official jar tag values -> block/fluid/item tag lookup -> climb, friction, block movement factor, and fluid travel input paths; source/server-provided tag overrides are not represented in the client jar and remain an external data boundary.
- Parent slices / dependencies / closure evidence: S3-climb-swim, S3-ground-air, S4-step-edge and S7-external-velocity; this slice closes only the inspected vanilla client-jar tag values. `inside_step_sound_blocks` is handled as audio-only in the evidence checkpoint; freeze, entity-type, and non-player tag producers are not movement findings.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): each enumerated movement-relevant official tag entry is byte-identical A/B. No vanilla tag membership difference is established for this bounded set. Custom server/data-pack tag overlays and remaining method/callback inventories are not closed.
- Finding IDs or checked absence/replacement path: checked no difference for the enumerated vanilla jar tag entries; remaining registrations, dynamic data, and external overlays stay open under D2/D3.

### Slice S1-input-sample: Input vector production

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: keyboard directional/jump/shift input to the normalized movement vector and digital input tick update.
- A evidence: `net/minecraft/client/player/Input.java` `tick` lines 15-20 and `net/minecraft/client/player/KeyboardInput.java` `tick` lines 21-34; SHA-256 `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` / `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0`.
- B evidence: same relative files/members and lines 15-20 / 21-34; identical SHA-256 values `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` / `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0`.
- State producers/writers -> consumers/readers: keyboard bindings -> `Input` directional/button fields and normalized move vector -> `LocalPlayer.aiStep` and travel dispatch (caller/order closure remains under S1-input).
- Parent slices / dependencies / closure evidence: S1-input broader input sampling and player tick order remains open; this row is only the two input method bodies.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both exact bodies are text-identical and file hashes match on A/B, so this bounded producer slice has no version difference.
- Finding IDs or checked absence/replacement path: checked paired method bodies; no difference.

### Slice S2-pose-refresh: Pose and dimension refresh methods

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pose assignment and dimension refresh, plus player pose selection transitions for crawl/swim/sleep/stand.
- A evidence: `net/minecraft/world/entity/Entity.java` `setPose(Pose)` lines 344-347 and `refreshDimensions()` lines 2497-2518; `net/minecraft/world/entity/player/Player.java` `updatePlayerPose()` lines 375-405. SHA-256 `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` / `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1`.
- B evidence: same relative files/members: `Entity.setPose` lines 344-347, `refreshDimensions()` lines 2522-2543 and `Player.updatePlayerPose()` lines 368-398. SHA-256 `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` / `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`.
- State producers/writers -> consumers/readers: player pose/ability/fluid/item state -> `updatePlayerPose` -> `setPose` / `refreshDimensions` -> bounding box and eye-height consumers. Collision-query consequence remains under S4-shapes-query.
- Parent slices / dependencies / closure evidence: S2-player-state remains open for constructors, all field writers, timers, direct predicates and eye-height dependents.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the bounded setter, refresh and player pose-selection method bodies are text-identical; method movement line offsets are recorded above. This does not close every pose writer or dimension consumer.
- Finding IDs or checked absence/replacement path: checked paired method bodies; no difference.

### Slice S3-player-jump-travel: Player-specific jump and travel wrapper

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: player jump impulse wrapper and player travel pre/post logic, including swim/flying conditions and speed scaling.
- A evidence: `net/minecraft/world/entity/player/Player.java` `jumpFromGround()` lines 1453-1463 and `travel(Vec3)` lines 1464-1496; SHA-256 `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1`.
- B evidence: same methods lines 1437-1447 and 1448-1480; SHA-256 `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`.
- State producers/writers -> consumers/readers: local jump/input/ability/swim/passenger state -> Player wrapper -> `LivingEntity.jumpFromGround/travel` and position/velocity state; underlying travel branches have separate slices.
- Parent slices / dependencies / closure evidence: S1-input, S2-player-state, S3-travel-dispatch, S3-jump, S3-climb-swim and S6-effects-attributes remain open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both paired player-specific wrapper bodies are text-identical with unchanged expression and branch order. No claim is made about inherited `LivingEntity` branches or indirect dependencies.
- Finding IDs or checked absence/replacement path: checked paired method bodies; no difference.

### Slice S4-entity-move-method: Entity axis collision response body

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: `Entity.move(MoverType,Vec3)` entry, requested displacement, collision axis order, selected resolved displacement, position and collision/on-ground flag updates.
- A evidence: `net/minecraft/world/entity/Entity.java` `move` lines 547-680; SHA-256 `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`.
- B evidence: same method lines 547-680; SHA-256 `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6548983667adc32`.
- State producers/writers -> consumers/readers: travel velocity -> `Entity.move` and its queried shapes -> position/AABB/collision flags and velocity resolution; shape and world-query callees remain in S4-shapes-query/S5-block-shapes.
- Parent slices / dependencies / closure evidence: S3 travel branches; S4-shapes-query, S4-step-edge, S4-callbacks and S5-block-shapes remain open dependencies.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the bounded movement response method body is text-identical. Shape-provider/query differences can still affect its inputs and are not covered by this method-only disposition.
- Finding IDs or checked absence/replacement path: checked paired `Entity.move` bodies; no difference.

## Dependency queue and blockers

- D0 — readiness/provenance: resolved. IDs and official metadata are exactly 1.19.2/1.19.3, namespace `mojmap`; both ready statuses are `ready`; physical Java counts and manifest entries match (4,480/4,618); readiness/provenance/source/artifact/diagnostic manifest hashes match; original client jar, mapping file and remapped-jar hashes match both artifact manifests; 21 relevant diagnostic anchors per side have no error/warning/missing/failed/exception text.
- D1 — paired member correspondence and full player tick graph. Why: names do not establish correspondence or reachability. Next action: finish bounded body ranges, callers, writers/readers and closure for all stages. Owner: discovery author. State: open.
- D2 — version-matched resources/tags/defaults. Why: Java source saver omits resources. Next action: inspect relevant jar entries and hashes once provenance is verified. Owner: discovery author; server/datapack provision if identified. State: open.
- D3 — external/synchronized movement inputs. Why: client sources cannot prove server rules or value origin. Next action: trace concrete player consumer and label each producer boundary. Owner: discovery author. State: open.
- D4 — independent coverage reviewer. Why: source completion requires an author-independent re-walk. Next action: coordinator assignment after freeze. Owner: coordinator. State: pending; does not block source comparison.


The exact source pair is published and verified. No source-generation blocker remains; do not rerun or write the shared decompiler.

## Verified paired comparisons (partial; not frozen)

All source roots below are read-only under `build/movement-campaign-2026-10-07/ready/<version>/mojmap/`. Hashes identify the complete cited Java files and were matched to each side's `mojmap.sources.sha256` manifest.

### Input, local tick, player travel and entity movement

- `net/minecraft/client/player/Input.java`: A/B `tick` lines 15-20, SHA-256 `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` on each side; text-identical. `KeyboardInput.java`: `tick` lines 21-34, SHA-256 `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` on each side; text-identical.
- `LocalPlayer.tick`: A lines 200-218, B lines 188-207; local-control vehicle packet path in B adds `sendIsSprintingIfNeeded()` at B197. `LocalPlayer.sendPosition`: A lines 230-276, B lines 219-269; B factors the sprint-state packet into helper lines 271-279 and calls it from `sendPosition` and the locally controlled passenger path. Outbound synchronization boundary; linked to the mounted sprint predicate finding, not a direct local trajectory operation.
- `Player.aiStep`: A lines 500-563, B 496-559; text-identical. `LivingEntity.aiStep`: A 2497-2621, B 2508-2632; text-identical. `LivingEntity.tick`: A 2257-2377, B 2264-2384; text-identical.
- `Player.jumpFromGround`: A 1453-1463, B 1437-1447; text-identical. `Player.travel`: A 1464-1496, B 1448-1480; text-identical. `LivingEntity.jumpFromGround`: A 2014-2041, B 2024-2051; text-identical. `LivingEntity.travel`: A 2042-2256, B 2052-2263; only fall-distance update in fall-flying branch differs (see F-004 below); remaining compared body is text-identical.
- `Entity.tick`: A/B 403-406; `baseTick`: A/B 407-466; text-identical. `Entity.move`: A/B 547-680; text-identical. `Entity.refreshDimensions`: A 2497-2518, B 2522-2543; text-identical. `Entity.setPose`: A/B 344-347; `setPos` overload: A/B 372-380; text-identical. `Player.updatePlayerPose`: A 375-405, B 368-398; text-identical. `LocalPlayer.move`, `moveTowardsClosestSpace`, `suffocatesAt`, `setSprinting`, `updateAutoJump`, `canAutoJump`, `isMoving`/sprint impulse helpers, `isMovingSlowly`, `sendRidingJump`: compared bodies text-identical; individual exact paired ranges and hashes remain to be entered as member slices before these slices can close.
- `LocalPlayer.aiStep`: A 731-898, B 652-819. In addition to the mounted sprint-state exclusion below, the input/crouch, double-tap, auto-jump, flight, glide, rider charge arithmetic and other compared branches retain their order and expressions. This one large body is not considered closed until it is split into method-range slices.
- `LocalPlayer.tick` B also adds a sprint-state send within the locally controlled vehicle branch. `Player.tick`: A 230-303, B 224-296; differences are removed warden spawn tracking and `ItemStack.isSameIgnoreDurability` -> `isSame`, both outside movement scope.

### Movement-property/resource checkpoint

- Movement coefficient declarations were inventoried across each `world/level/block` source tree. `BlockBehaviour.Properties` exposes `friction`, `speedFactor`, and `jumpFactor` builders at A 916-930 / B 945-959; `Blocks.java` has the same seven existing movement-property registrations and values (ice/frosted ice 0.98F; soul sand and honey speed 0.4F; slime 0.8F friction; packed ice 0.98F; blue ice 0.989F; honey jump 0.5F). A/B cited hashes: `Blocks.java` `f532585f836ee7ad685d368889ed13d735c570ef38971db0c1d2a7debb4cf27b` / `9944877c941fa209d2a8b9a7d4eb6333edf2dfca255a3e90601242483eb7a476`; `Block.java` `e95d5abe175a3648b697e542981ba01535157093d133b774aadef719393ccb8f` / `c7cabe648cf2a153f892499560492204a0fb3b6b4cbcd3e209ea4a08a8acca60`; `BlockBehaviour.java` `c0e62233fa21be352953edda6b411727762165fa2cc8d645c7c08406f92df08b` / `183ea122421dc3c0649ff0c64affda3d3cc3090c9fc2e01d96da44410d4b39c1`. The `Block.getFriction/getSpeedFactor/getJumpFactor` getters return stored fields on both sides; player-ground friction consumer is `LivingEntity.travel` A line 2152 / B 2159. This bounded property-value check found no changed existing coefficient; remaining block-state/subclass/shape inventory is still open.
- Exact `client.jar` tag entries referenced from player/world movement source were hashed from each original official jar. A and B entries are byte-identical for `data/minecraft/tags/blocks/climbable.json` `d0e3e76d7457f3f3f3d7219fe218c7746e4b2e7626d5c388fcf193089069e365`; `fences.json` `214462701d11703b222317fb7d470caa40e017b5036b127db242986b92fb30cd`; `fire.json` `0074ed2ae89b03a2008a8220636c7544c868326e925b4bfae223891bd5727a97`; `portals.json` `fe9759678b07daacfebc3ce9e5aa616489cb432af36d14ce4cc1128cd3a410ef`; `signs.json` `f17adec59bd57315440524f6edfe7d18a8f6f372afe887dc9a0151055a15706e`; `soul_speed_blocks.json` `8ba7eac6f7c74d25601ef4455b71e3947fdb8e51b8fafd55dddee48397b144b6`; `walls.json` `9d59ebd9851de9061d12fe294a947f3c327a7e1b24d2f21f855138321f0d6ef4`; `data/minecraft/tags/fluids/lava.json` `71f50fb9092d78260bc7434731fc5fd426a44e5284a6ad084ec71cb725630c6b`; `fluids/water.json` `dcfa69a748d03dbf788d8f7b0e5eb6c8de6355a527fcaf74b9210fe4be2a3004`; `data/minecraft/tags/items/freeze_immune_wearables.json` `8df0fa68f14a7f9705df39043a43c0b07abf3c30642608ec47c52f78a3ee0c3d`. `inside_step_sound_blocks.json` differs (`fa00747293dc962d25373dae303230a0b955506f7232c8741ab4630bc4f202c8` -> `50ba30b3445083a3e331d025f5f0edf0eb6071b4a23b8cbbd1a82f076fe19477`) but its `Entity.playStepSound` consumer selects audio (A/B `Entity.java` lines 933-941), not a movement input. `crystal_sound_blocks.json` hashes to `6217bd619dea59a80a48ae687cb20ac977d50bd5cffca7c53f5692fc478c063f` on both sides and is consumed by the amethyst step-sound path. Original jar identities are recorded above.
- Artifact revision `feather-r1-2026-10-07` is not applicable to this pair: it covers six early Feather-derived snapshots only; 1.19.2 and 1.19.3 use verified Mojmap source/raw artifacts. No revised snapshot was consumed by these findings.

### Movement-speed effect and attribute checkpoint

- In both `MobEffects.java` files, the only registrations targeting `Attributes.MOVEMENT_SPEED` are speed (+0.2F, `MULTIPLY_TOTAL`) and slowness (-0.15F, `MULTIPLY_TOTAL`); the UUIDs and amounts are identical. The registered `MOVEMENT_SPEED` attribute metadata is also identical (A `Attributes.java` lines 13-15, SHA-256 `c41860b83315d5265632e9a90978e38794d83d1a0cd996dbb9c7fd8e56560df5`; B lines 14-16, SHA-256 `28e439335af9ccd2017ff29c24c645e85da1c8193ad07ce20f0f37ce053ff662`): generic movement speed, default 0.7F, range 0.0-1024.0, syncable. These checks are bounded to the movement-speed attribute, not a complete effect or attribute inventory.
- `MobEffect.addAttributeModifiers`, `removeAttributeModifiers`, and amplifier-based `getAttributeModifierValue` have identical method-body hashes A/B (`341d2fa9fe7afa39ebb2589d15ddba00ac2d702a0ded48ca1238de53a4255dc4`, `8c1f7d262416d80f3fded21cd6a20a48704cead20d3bf21799b74a79b2b0d1a4`, `b905ad63779f5a996a56975c4c1dfb298518a97e77fba62fa38731435c3526bc`). `AttributeInstance.calculateValue` is identical A/B (`d1111add6279a99e2edb13f97becf48435655182568b0e47a47a5d948a0bb5d0`); player `aiStep` bodies are identical A/B (`7355b5b7c1166c8b1a91a441c22bcce16d44992c3a71d1ae860c2de29b4e9246`) and assign the merged movement attribute to `speed`. `LivingEntity.getFrictionInfluencedSpeed` is identical A/B (`ba1ca4460a07a15a5c0320e7ad2cc9170c4278ae9786e1a53760ffc6fd8bc24b`). Coverage row S6-player-movement-speed-effects records the ranges, file hashes and reachability boundary.

### Checked scope exclusion — mounted sprint eligibility (E-001)

- A `LocalPlayer.aiStep` lines 772-780 uses `foodLevel > 6.0F || mayfly`; its ground eligibility is `onGround || isUnderWater` (A lines 772-778). B `LocalPlayer.aiStep` lines 693-701 adds `isPassenger() && getVehicle().isOnGround()` to the ground eligibility and delegates food eligibility to B `hasEnoughFoodToStartSprinting` lines 1047-1049, which returns true for passengers before reading food/flight permission. B calls the helper from the sprint continuation check as well (verify exact relevant body lines before slice closure).
- A/B `LocalPlayer.java` SHA-256: A `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef`; B `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`.
- Preconditions: local passenger, non-sprinting, not shift-keyed, forward impulse threshold met, and no item-use or blindness gate; the new ground clause needs the vehicle grounded, while passenger food bypass applies even at food <= 6. The changed state is player sprint eligibility/state and corresponding sprint command synchronization; whether this changes the mounted vehicle trajectory depends on server-side consumer behavior and remains an external dependency, not claimed here.
- Scope disposition: the code changes only the player's sprint flag and its command synchronization while mounted. Player `Player.travel` explicitly skips swimming and flight branches when `isPassenger()` is true (A 1464-1496; B 1448-1480); passenger position is subsequently assigned through `Entity.rideTick` -> vehicle `positionRider` (A 1699-1705; B 1704-1710), which enters excluded vehicle movement. When dismounted, the B sprint continuation condition clears sprint when the non-passenger food/impulse guards fail (B 719-728), before ordinary player travel. The server-side mounted sprint consumer is vehicle behavior and outside player-only scope. Record the paired source change for traceability, but do not treat it as a player movement finding under the clarified scope.
- Inventory/slices: INV-TICK, INV-EXTERNAL; S3-mounted-sprint and S7-mount-transition. Checked exclusion only; no implementation recommendation.

### Source-confirmed candidates F-002 / F-003 — fluid source conversion inputs

- `FlowingFluid.getNewLiquid`: A lines 154-178 takes `LevelReader` and gates source creation with parameterless `canConvertToSource()` at A171; B lines 154-178 takes `Level` and passes it to `canConvertToSource(level)` at B171. `FlowingFluid` SHA-256: A `bbfb661b524ac92f74579cd4b61c1a25df00ecc4bfe775a5516f7e4a7c8768f3`; B `d84ceb3c0b83da25fb68599e16cc2298106e745a9ef0c9f32f616d097a8ec56c`.
- Water: A `WaterFluid.canConvertToSource` lines 72-74 returns `true`; B lines 73-75 reads `RULE_WATER_SOURCE_CONVERSION`. A/B file SHA-256: `9d305751d39633ee519e260be184208b20cdbb3422acadb84f8871722c1a383f` / `3e10320356b11d415d8e4f3ea2b7fbb27e5bd8abdc45d524afb18b834e613e5f`.
- Lava: A `LavaFluid.canConvertToSource` lines 189-191 returns `false`; B lines 189-191 reads `RULE_LAVA_SOURCE_CONVERSION`. A/B file SHA-256: `0f3fab21b95d66f9ecbe216b8bfc888aa033718b17e273de0e0f0be99bd1fc6c` / `1d239a6e0c99aee386b5dfb0244a29f4a48689808f9be64dda89c9a63b9fc6da`.
- 1.19.3 `GameRules` lines 162-167 register `waterSourceConversion` default `true` and `lavaSourceConversion` default `false`; both absent from A. Thus defaults reproduce A's source-conversion boolean, while a non-default server rule can alter the resulting world fluid-state topology. FlowingFluid's player-facing downstream reader remains in `LivingEntity.travel` (A 2042-2256, B 2052-2263) and fluid contact/push path; actual remote rule/world-state production is outside the local client and is tracked as external input. Gamerule hash B `9f7c0b5ff3ebc46cac4275e6c856610e00e796f9cf5368d9b380ac0e478e1bae`; A `d59d612944db56c897e407791e405bef71f131bfd4a5bff5b216bd75515b6fb6`.
- These are separate source candidates because water and lava use different rules and defaults. Their impact on player movement requires a fluid block-state difference and remains contingent on server/world inputs. Inventory/slices: INV-WORLD-MOVEMENT, INV-TICK, INV-EXTERNAL; S3-climb-swim, S5-fluid-data, S7-external-velocity.

### Fall-distance callback path and unresolved shape candidates

- `LivingEntity.travel` fall-flying branch: A directly sets `fallDistance = 1.0F` when vertical velocity > -0.5; B calls `checkSlowFallDistance`, whose B `Entity` body lines 2147-2151 additionally requires `fallDistance > 1.0F`. The indirect consumer path is now bounded for farmland: `Entity.move` calls `checkFallDamage` before the separate `updateEntityAfterFallOn` movement-response callback (A/B lines 594-608). `FarmBlock.fallOn` is server-only and uses the supplied fall distance in its random trample threshold; when it converts farmland to dirt, the block collision shape changes from 15/16 height to full block. Other landing callback consumers and the full provider inventory remain open. This supports F-004 as a conditional player-to-world-to-later-collision path; it does not establish the external server update or a realized client trajectory.
- B `Entity.fixupDimensions()` lines 2515-2521 has no source-tree caller in B (definition-only whole-source search); not a reachable movement change on current evidence.
- A/B `PlayerRideableJumping` and `AbstractHorse`: B changes the interface to `canJump(Player)` and adds default cooldown 0; `AbstractHorse` B `canJump(Player)` returns the same `isSaddled()` as A `canJump()`. Camel is B-only and must remain modern-only. Reaching and classifying all other implementations/subclasses is still open; do not infer a generic old-horse difference.
- B adds `getBlockSupportShape` overrides to existing FenceGate and ShulkerBox blocks (FenceGate B 75-81; ShulkerBox B 235-239). `SupportType` consumes support shapes and `BlockState.isFaceSturdy` uses that API; current caller scan found placement/neighbor and fluid support paths, but did not establish a player collision/support consumer. Keep S4/S5 open until full collision/support callgraph and block registration inventory closes.
- B `BlockStateBase.getFluidState` returns a cached state initialized from `Block.getFluidState(state)`; this API takes immutable state-only input. No movement consequence is established; override enumeration and cache lifecycle remain open.

## Finding index

Findings F-002 and F-003 are source-confirmed paired fluid-state input differences with externally contingent player movement effects; F-004 is a source-confirmed fall-distance-to-farmland callback delta with an external server-world update and conditional later collision-shape effect. All three are indexed in `findings/`. E-001 is a checked scope exclusion for a mounted sprint-state/command difference whose trajectory consumer is vehicle behavior. None of these dispositions closes the source inventories or claims runtime trajectory impact. Record every discarded candidate with inspected dependency path and reason after source verification.

## Resume checkpoint

- Last completed slice: exact source provenance and several member-level input, pose, player travel, entity move, movement coefficient and client-tag slices; in-scope water/lava source-conversion differences recorded; fall-distance farmland callback path recorded as F-004; mounted sprint-state difference checked as an out-of-scope vehicle consumer.
- Next bounded slice and exact files/members/body ranges to open: full body/member-range ledger for LocalPlayer.aiStep, LivingEntity.travel, movement collision/support helper callgraph; then effect/attribute/enchantment/equipment and packet/external-state producers, with exact resource entry hashes.
- Outstanding dependencies and owners: D1–D3 discovery; D4 coordinator independent reviewer assignment.
- Current assumptions requiring verification: current source candidates are not yet frozen, resource and movement-modifier inventories remain open, and external server/world consumption of the candidates has not been closed.

## Implementation reconciliation

- Reconciliation status: pending (deferred until blind-discovery freeze)
- Repository revision inspected: none; old/current mod implementation has not been opened
- Finding -> implementation disposition/evidence: pending; implementation read is not authorized before freeze
- Existing implementation without a frozen source finding: pending
- Coverage gaps routed back to discovery slices: none yet

## Independent source audit

- Reviewer: pending; must differ from discovery author
- Status: pending
- Inventories and call-chain ranges re-walked: none yet
- Concrete missed-slice routes: pending
- Misses routed to slice/finding IDs and owners: pending
- Reviewer evidence / date: pending

## Source audit closure

- Coverage counts by status: pending 20 initial families; in-progress 0; compared-no-difference 7 bounded member slices; findings 3 bounded member slices; not-applicable 1 checked scope exclusion; blocked 0. The initial families are not member-range closure and must be split before terminal dispositions.
- Required inventory status and evidence: INV-TICK pending; INV-STATE pending; INV-COLLISION pending; INV-WORLD-MOVEMENT pending; INV-MODIFIERS pending; INV-EXTERNAL pending; INV-EXCLUSIONS pending.
- Open dependencies: D1, D2, D3, D4 (D0 resolved)
- Unresolved gaps and limits: exact trees and provenance are verified, but member-level slice ledger, full source coverage, resources, external producers, freeze and independent audit remain open.
- Evidence/hash/correspondence audit: not yet performed
- Static schema check: canonical checker exited 0 on 2026-10-07; it reported active structurally valid and made no source-completion claim.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from source or static checks)
