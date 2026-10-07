# Discovery: 26.1.2 to 26.2

- Status: active
- Scope: exact adjacent endpoint comparison, source-only client player movement; older A = 26.1.2; newer B = 26.2. No runtime Java implementation. No wiki browsing, release-notes mechanics, tests, client/TAS/Gym/server/Docker launches. Runtime validation is not authorized.
- Repository revision and start date: branch started at main `002137b227676caea77f6832b9f4c8d0b6200bff`; dedicated branch `feat/source-discovery-movement-source-26-1-2-26-2`; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: both endpoints are native Java 26 unobfuscated sources in Mojang official names; CLI `unobfuscated` for A and B. B current marker verifies `versionId=26.2`. A marker is not yet published in the campaign ready tree, so current pair alignment is pending. Prior source report was used as navigation only; none of its hashes or conclusions are current evidence.
- Source preparation owner / command / log / readiness marker: source owner is the campaign's single shared-source owner; name not present in this worker handoff. B marker: `build/movement-campaign-2026-10-07/ready/26.2/unobfuscated.ready.json`, status `ready`; the marker lacks command, exit status and log path. A exact `unobfuscated` readiness marker, source root and preparation provenance are pending. This worker has not run decompilation.
- Toolchain/decompiler/remapper versions and options: B marker says Vineflower and Java runtime 25 but gives no Vineflower version/options, remapper version or preparation command. Native unobfuscated releases use original client jars; mapping and remapped jar are not applicable once each current marker confirms this. Source owner metadata request remains open.
- Discovery author(s): source-only worker for this exact pair.
- Independent reviewer (must differ from discovery authors): not assigned in the available task instructions.

## Artifact manifest

### A — 26.1.2

- Current exact requested/resolved release, source root, client jar hash, ready JSON, source/artifact manifest, movement diagnostics and toolchain details: pending current campaign publication. Intended CLI/namespace is native `unobfuscated` / Mojang official names; verify against the current marker before comparison. Earlier reports are navigation aids only.
- Mapping and mapped jar: pending current marker; record `not applicable: published unobfuscated` only after verification.

### B — 26.2

- Exact release: readiness JSON `ready/26.2/unobfuscated.ready.json`, `versionId=26.2`, status `ready`; source root `build/movement-campaign-2026-10-07/ready/26.2/unobfuscated/`; native `unobfuscated` namespace; original client jar is the decompiler input. Ready JSON reports Vineflower, Java runtime 25, 7,055 sources / 33,813,717 bytes.
- Original client jar `build/movement-campaign-2026-10-07/artifacts/26.2/client.jar`, SHA-256 `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290`; version metadata says ID 26.2. Mappings and remapped jar: not applicable, published unobfuscated.
- Source manifest `ready/26.2/unobfuscated.sources.sha256`, SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.
- Artifact manifest `ready/26.2/artifacts.sha256`, SHA-256 `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`.
- Movement-method diagnostics `ready/26.2/movement-diagnostics.txt`, SHA-256 `ba6fd6c5b1c77b3ee2988f0c54968cc5b915680670fdb66699dbcbf8b9fe21d3`. It lists decompiled anchors for Entity.move/moveRelative, LivingEntity travel/jump paths, Player.travel and LocalPlayer.aiStep. The cited file hashes and bodies have been checked on B; method anchors do not establish pair coverage or the integrity of unlisted methods.
- Direct B source hashes, each recomputed and matching the B source manifest: `net/minecraft/client/player/KeyboardInput.java` `b4bbb410650444c30d2a62d70fd3c6cd1aefa104b8a12e474e2a478a62089c39`; `net/minecraft/client/player/LocalPlayer.java` `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; `net/minecraft/world/entity/LivingEntity.java` `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; `net/minecraft/world/entity/Entity.java` `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`; `net/minecraft/world/entity/player/Player.java` `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; `net/minecraft/world/entity/player/Abilities.java` `e96537b6f633aa2b16e6ffd7b324c06f75ee0dd15597e7c583db833a6acb1a32`. Add source/resource hashes when further files are cited.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: not frozen; current active checkpoint commit `4947258` includes workflow hardening only and is not a discovery freeze.
- Evidence inventory and finding IDs included at freeze: none; no pairwise findings confirmed yet.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; neither implementation nor wiki/audit output has been opened. No wiki browsing.
- Source/mapping hashes covered by freeze: pending both-side inventory closure.

## Correspondence and call order

No pair-specific correspondence is resolved until A's current exact source is ready. B anchors below are inventory seeds, not guessed A/B equivalence. All class/member descriptors and the complete caller/dependency chains must be checked on both sides.

Initial B-only call-order anchors inspected (all SHA-256 values in B manifest above):

- `KeyboardInput.tick()` samples key presses, creates move impulses and normalizes a `Vec2` (`KeyboardInput.java:14-36`). A tick producer/consumer chain still needs tracing on both sides.
- `LocalPlayer.aiStep()` snapshots jump/shift/forward flags before `input.tick()`, computes crouch, applies auto-jump and unstuck probes, then applies sprint gates, flight and riding-jump work before `super.aiStep()` (`LocalPlayer.java:767-919`).
- `LivingEntity.aiStep()` applies a player-specific horizontal velocity cutoff at `3060-3083`, input and jump handling at `3084-3123`, travel dispatch at `3125-3141`, block effects at `3143-3145`, and client animation at `3147-3149`. The nearby non-player per-axis cutoff (`3069-3076`) is out of movement-emulation scope but its branch guard is recorded so it is not confused with the player path.
- `LivingEntity.travel()` dispatches fluid, fall-flying, or air travel (`2430-2437`); `Player.travel()` adds swimming and ability-flight handling (`Player.java:1402-1429`). Exact callees and post-travel work remain to inventory and compare.
- `Entity.move()` performs edge adjustment and collision, movement/position and collision flags, fall damage check, collision restitution, movement emission, then block-speed velocity scaling (`Entity.java:711-800`); its collision resolver and shape dependencies must be indexed separately.

## Required source inventories

Each inventory maps to bounded slices below; all are pending. Add all newly discovered writers, consumers, overrides, registrations and resources before disposition.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S-TICK-ENTRY,S-IN-01,S-LOCAL-01,S-LOCAL-02,S-LIVING-01,S-LIVING-02,S-LIVING-03,S-TRAVEL-01,S-PLAYER-01,S-POSE-POST; evidence=B-only anchors in correspondence; A source pending.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S-TICK-ENTRY,S-LOCAL-01,S-LOCAL-02,S-LIVING-01,S-STATE-01,S-POSE-POST; evidence=B source methods listed in coverage; constructor/default/reset/writer inventory still open.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S-MOVE-POS,S-MOVE-FLAGS,S-MOVE-RESTITUTE,S-MOVE-BLOCK-SPEED,S-EDGE-PROBE,S-COLLISION-STEP,S-COLLISION-QUERY,S-COLLISION-AXIS,S-WORLD-01; evidence=B Entity.move range below; provider/registry/neighbor inventory still open.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S-WORLD-01; evidence=B block/fluid providers and original client-jar resource inventory not yet cited.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S-LIVING-02,S-TRAVEL-01,S-MOD-01; evidence=B consumers and producer/registration/resource inventory still open.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=S-EXT-01; evidence=B client consumer/packet mapping not yet cited.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=explicit campaign scope above; validate direct vanilla-state reads in movement predicates during pair audit, and exclude the systems that produce those values.

## Coverage ledger

These are 22 initial bounded work units, not an exhaustive inventory. All remain pending because A source is not yet in the campaign ready tree. B-only anchors are navigation evidence; do not infer equivalence or absence. Expand the ledger during the pair-specific inventory.

### Slice S-IN-01: Keyboard input sampling and movement-vector construction

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: `KeyboardInput.calculateImpulse` and `KeyboardInput.tick`, key-state sampling, forward/side impulse signs and vector normalization; callers still to trace.
- A evidence: pending exact 26.1.2 readiness and member correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/KeyboardInput.java::net.minecraft.client.player.KeyboardInput#calculateImpulse(boolean,boolean),tick(), lines 14-36`, SHA-256 `b4bbb410650444c30d2a62d70fd3c6cd1aefa104b8a12e474e2a478a62089c39`.
- State producers/writers -> consumers/readers: options key states -> `Input` keypress snapshot and `moveVector` -> local player input/tick consumers; verify exact A/B edges.
- Parent slices / dependencies / closure evidence: source dependency D-A-READY; option/input classes and local tick callers unindexed.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; no pairwise disposition is possible before A source arrives.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-LOCAL-01: Local pre-travel crouch, input tick, auto-jump, unstuck and sprint gating

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer.aiStep` lines 777-829, including input snapshots and enclosing checks/order.
- A evidence: pending exact source and correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::net.minecraft.client.player.LocalPlayer#aiStep(), lines 767-829`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.
- State producers/writers -> consumers/readers: keypress/input -> crouching, `sprintTriggerTime`, sprint flag, auto-jump input; pose-fit/collision and direct sprint predicates remain dependencies. Health/food producers are excluded.
- Parent slices / dependencies / closure evidence: S-IN-01; D-A-READY; pose-fit, sprint predicates and input methods pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read only; pair comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-LOCAL-02: Local flight, fall-flying and rideable jump input

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer.aiStep` lines 830-919; jump edge flags, mayfly gates, flight velocity writer, fall-flying request, rideable jump charging and superclass call order.
- A evidence: pending exact source and correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::net.minecraft.client.player.LocalPlayer#aiStep(), lines 830-919`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.
- State producers/writers -> consumers/readers: jump/shift state and `Abilities` -> flight toggle and vertical velocity; LocalPlayer request -> server fall-flying transition; rideable jump input -> vehicle callback is player/vehicle interaction, independently inspect player-facing behavior only.
- Parent slices / dependencies / closure evidence: S-IN-01; S-LIVING-03; player abilities and packet handlers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read only; pair comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-LIVING-01: Player horizontal/vertical velocity dead-zone before input and jump processing

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.aiStep` movement snapshot and player-specific horizontal cutoff plus vertical cutoff, lines 3060-3083; do not merge the distinct non-player per-axis branch into the player slice.
- A evidence: pending exact source and inherited method correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::net.minecraft.world.entity.LivingEntity#aiStep(), lines 3060-3083`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: incoming/existing delta movement -> player-specific threshold -> rewritten delta -> input, jump and travel consumers.
- Parent slices / dependencies / closure evidence: D-A-READY; velocity writers and other readers to inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B branch distinguishes player from non-player code; no difference claimed.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-LIVING-02: Fluid jump gates and ground-jump cooldown

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.aiStep` jump block lines 3098-3123 and direct `jumpFromGround()` helper; split fluid depth and jump-power producer dependencies separately after A inventory.
- A evidence: pending exact source and inherited method correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::net.minecraft.world.entity.LivingEntity#aiStep(), lines 3098-3123; jumpFromGround(), lines 2388-2401`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: jump key via input -> `jumping` and no-jump delay/fluid-height guards -> liquid or ground impulse -> travel. Jump Boost consumer is in helper chain; exclude food/damage producers.
- Parent slices / dependencies / closure evidence: S-IN-01; fluid height/threshold, jump-power and effect chains pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B bodies read; pair behavior and data dependencies unresolved.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-LIVING-03: Living pre-travel and post-travel dispatch/order

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.aiStep` interpolation/dead-zone context, `applyInput`, fall-flying update, box snapshot, fall-distance reset, ridden-vs-self travel dispatch, block effects and client animation; cover lines 3043-3059, 3084-3097, 3125-3151 and related post-travel callers.
- A evidence: pending exact source and inherited method correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::net.minecraft.world.entity.LivingEntity#aiStep(), lines 3043-3059, 3084-3097, 3125-3151`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: interpolation/input/pose/movement flags -> travel and its guards -> block callbacks, fall state and animation; caller tick ordering still open.
- Parent slices / dependencies / closure evidence: S-LOCAL-01,S-LOCAL-02; local/player/entity tick callers and all nested travel branches pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; no pairwise order comparison yet.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-01: Living travel dispatch and air movement integration

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.travel` dispatch lines 2430-2446 and `travelInAir` lines 2468-2502. Split ground/air friction, gravity, effects and each fluid/glide branch into bounded slices after source index is built.
- A evidence: pending exact source and inherited method correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::net.minecraft.world.entity.LivingEntity#travel(Vec3),travelInAir(Vec3), lines 2430-2446, 2468-2502`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: movement input/attributes/block friction/effects/gravity -> acceleration and delta movement -> `Entity.move`; producer data and movement threshold interactions pending.
- Parent slices / dependencies / closure evidence: S-LIVING-03; attributes, fluids, block speed/friction, effect and helper chains pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B method bodies read; no pairwise disposition.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-PLAYER-01: Player swimming and ability-flight travel wrapper

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: `Player.travel(Vec3)` passenger, swim-look, flight and superclass path, lines 1402-1429; downstream conditions and abilities are dependencies.
- A evidence: pending exact source and inheritance correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/player/Player.java::net.minecraft.world.entity.player.Player#travel(Vec3), lines 1402-1429`, SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`.
- State producers/writers -> consumers/readers: passenger/swimming/abilities/look/fluid state -> vertical delta writer and living travel; abilities and fluid checks pending.
- Parent slices / dependencies / closure evidence: S-LIVING-03; abilities/defaults and shape/fluid queries pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; no A pair yet.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-STATE-01: Player pose fit and dimension-dependent collision query

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: B `Player.updatePlayerPose` and `canPlayerFitWithinBlocksAndEntitiesWhen`, plus Entity pose/dimension refresh, bounding box and eye-height consumers; exact A/B writer inventory not yet built.
- A evidence: pending exact source and inheritance/override inventory.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/player/Player.java::updatePlayerPose(),canPlayerFitWithinBlocksAndEntitiesWhen(Pose), lines 343-374`; and `Entity.java::setPose(Pose),refreshDimensions(), lines 439-? / 3393-?`. Player SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; Entity SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`. Resolve exact full Entity method ranges before any disposition.
- State producers/writers -> consumers/readers: input/swimming/sleeping and collision fit -> pose -> dimensions/eye height/AABB -> collision, fluid and support queries.
- Parent slices / dependencies / closure evidence: S-LOCAL-01; all pose/dimension defaults, entity size, caller and collision-query chains pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B navigation only; no pairwise or complete writer inventory.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-MOVE-POS: Entity movement clipping, position integration and zero-motion threshold

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: B `Entity.move` call from edge adjustment through collision result acceptance, movement record, and `setPos`; lines 737-757. Compare its enclosing no-physics/piston/stuck guards separately.
- A evidence: pending exact source and caller correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::move(MoverType,Vec3), lines 737-757`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: edge-adjusted requested movement -> collided movement and squared-length threshold -> movement history and position -> later collision/support/tick readers.
- Parent slices / dependencies / closure evidence: S-TICK-ENTRY,S-TRAVEL-01; `collide`, AABB and collision result producers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read only; A comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-MOVE-FLAGS: Collision-axis, vertical support and on-ground state updates

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: B `Entity.move` computes X/Z and vertical collision flags, vertical-below, then calls `setOnGroundWithMovement`; lines 759-778. Keep damage processing excluded while tracing any movement-state writers and callers.
- A evidence: pending exact source and caller/member correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::move(MoverType,Vec3),setOnGroundWithMovement(boolean,boolean,Vec3), lines 759-778, 671-706`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: requested vs clipped movement -> collision/ground flags -> next jump, friction, support and travel gates.
- Parent slices / dependencies / closure evidence: S-MOVE-POS,S-COLLISION-STEP; support-position lookup and flag consumers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; player authoritative guard and A semantics pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-MOVE-RESTITUTE: Post-collision player velocity response

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `Entity.move` restitution call gate plus `restituteMovementAfterCollisions`, including horizontal axes, vertical restitution, gravity/drag compensation and bounce event; lines 781-786 and 802-843.
- A evidence: pending exact source, replacement/absence path and call-site correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::move(MoverType,Vec3),restituteMovementAfterCollisions(BlockState,boolean,boolean,Vec3), lines 781-786,802-843`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: current velocity, collision flags, effect block, gravity/air-drag attributes and bounce-suppression tag -> restitution -> rewritten velocity and sync/event state -> next tick/travel.
- Parent slices / dependencies / closure evidence: S-MOVE-FLAGS; block restitution registrations, entity attributes and referenced data/tags pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): earlier report suggests a candidate, but current A source is unavailable and no finding is imported.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-MOVE-BLOCK-SPEED: Post-move block speed factor applied to horizontal velocity

- Inventory ID(s): INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: B `Entity.move` reads `getBlockSpeedFactor()` after collision and movement emissions and multiplies X/Z delta; lines 788-797, with getter and support-block lookup as dependencies.
- A evidence: pending exact source and caller/member correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::move(MoverType,Vec3),getBlockSpeedFactor(), lines 788-797,1085-1095`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: support block/state and block speed property -> post-move horizontal velocity -> next tick; neighbor/support computation and registrations pending.
- Parent slices / dependencies / closure evidence: S-MOVE-POS,S-MOVE-FLAGS; S-WORLD-01 registration inventory pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body anchors read; no A comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-EDGE-PROBE: Movement edge backoff hook

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `Entity.move` invokes `maybeBackOffFromEdge(delta,moverType)` before collision; base implementation currently returns the input unchanged. Enumerate player-reachable overrides before deciding applicability.
- A evidence: pending exact method, subclasses and caller inventory.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::move(MoverType,Vec3),maybeBackOffFromEdge(Vec3,MoverType), lines 737,1097-1099`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: input movement and mover type -> virtual edge adjustment -> collision query; player-specific override/callability must be verified.
- Parent slices / dependencies / closure evidence: S-TICK-ENTRY,S-MOVE-POS; inheritance/override search on both trees pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B base method read; no no-difference claim from base alone.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-COLLISION-STEP: Candidate movement and step-up selection

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `Entity.collide` clipping, collision-axis tests, step-up guard, grounded/expanded boxes, candidate iteration and horizontal-distance winner; `collectCandidateStepUpHeights` candidate filtering and sort; lines 1141-1192.
- A evidence: pending exact methods and caller/member correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::collide(Vec3),collectCandidateStepUpHeights(AABB,List<VoxelShape>,float,float), lines 1141-1192`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: input movement/on-ground/max-step -> candidate heights from collision shapes -> ordered step candidate and clipped movement -> position/ground flags.
- Parent slices / dependencies / closure evidence: S-MOVE-POS,S-MOVE-FLAGS; AABB, shape coordinate ordering and candidate dependencies pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; no pair result.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-COLLISION-QUERY: Collider gathering and axis clipping

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: B collision-bounding-box entry points and source-specific collider gathering, including entity collisions, close world-border shape and block-context collision queries; compare axis order/clipping separately in `collideWithShapes`; lines 1195-1243.
- A evidence: pending exact methods and world-call correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::collideBoundingBox(...),collectAllColliders(...),collectCollidersIgnoringWorldBorder(...), lines 1195-1243`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: entity/player collision context and expanded AABB -> world/entity/border/block shape lists -> clipping.
- Parent slices / dependencies / closure evidence: S-COLLISION-STEP; Level collision-query, border, entity and block provider dependencies pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; A and provider closure pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-COLLISION-AXIS: Sequential clipping axis order

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: B `Entity.collideWithShapes` iterates `Direction.axisStepOrder(movement)`, carries resolved movement into the next-axis bounding box, and calls `Shapes.collide`; lines 1244-1262.
- A evidence: pending exact method and vector/shape-helper correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::collideWithShapes(Vec3,AABB,List<VoxelShape>), lines 1244-1262`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: movement vector/axis order/shape list -> per-axis resolved vector -> final clipped movement.
- Parent slices / dependencies / closure evidence: S-COLLISION-QUERY; `Direction.axisStepOrder`, `Shapes.collide`, `VoxelShape` dependencies pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B method body read; pair comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-WORLD-01: Block/fluid movement providers, registrations and resource data
 Block/fluid movement properties, callbacks, registries and resource data

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: enumerate every player-reachable shape provider/override, movement callback, friction/speed/jump/bounce property, fluid push/height/flow input, block registration, state/context and neighbor dependency; this is an inventory root to be expanded into bounded individual slices, not a disposition.
- A evidence: pending exact source, registry and resource inventory.
- B evidence: B seed files include Block, BlockBehaviour, Blocks, all registered block subclasses, Fluid/FlowingFluid, shape classes and client-jar data; individual members/entry hashes not yet inventoried.
- State producers/writers -> consumers/readers: block/fluid registration/state/resource values -> player shape/contact/property/fluid queries -> movement, support, velocity and callback consumers.
- Parent slices / dependencies / closure evidence: S-STATE-01,S-COLLISION-01,S-COLLISION-02,S-TRAVEL-01; registry/resource inventory and all reachable providers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B root inventory unbuilt; no pairwise no-difference or absence conclusion.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-MOD-01: Movement attributes, effects, enchantments and equipment chains

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: consumer -> aggregation/default -> attribute/effect/enchantment registration and application/removal/conditions -> slots/data/tags for movement-affecting values; exclude health/food producers and combat simulations.
- A evidence: pending exact source and resource inventory.
- B evidence: B seed consumers include LivingEntity, Player, LocalPlayer, Attributes and effect/enchantment helpers; exact producer/consumer ranges and data entry hashes pending.
- State producers/writers -> consumers/readers: effect/equipment/server attribute inputs -> movement modifier aggregation -> travel/sprint/jump/flight/contact consumers.
- Parent slices / dependencies / closure evidence: S-LOCAL-01,S-LIVING-02,S-TRAVEL-01,S-PLAYER-01; resource/tag and server-synchronization provenance pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B dependency inventory unbuilt; no difference claim.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-EXT-01: External player movement inputs and client consumers

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: client player velocity/position corrections, movement synchronization, pushes, piston displacement, mount/dismount transitions and launch inputs; exclude independent non-player movement and damage/combat simulation.
- A evidence: pending exact packet/caller source and consumer correspondence.
- B evidence: B Entity contains packet velocity/position setters and movement synchronization writers; exact protocol callers and player-specific gates not yet indexed.
- State producers/writers -> consumers/readers: server/external packet or world callback -> player position/velocity/pose/vehicle state -> local tick/travel.
- Parent slices / dependencies / closure evidence: S-STATE-01,S-MOVE-POS,S-MOVE-FLAGS,S-MOVE-RESTITUTE; client packet handler, correction and vehicle call chains pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B external-input inventory unbuilt; no pairwise disposition.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TICK-ENTRY: Local player tick, superclass chain and virtual aiStep dispatch

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer.tick()` entry/super call and packet-sending guard; `Player.tick()` pre/post work; `LivingEntity.tick()` call to virtual `aiStep()`; `Entity.tick()`/`baseTick()` state updates preceding it. B ranges: LocalPlayer 227-251; Player 231-285; LivingEntity 2765-2808; Entity 506-535.
- A evidence: pending exact source and inheritance/caller correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::tick(), lines 227-251`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; `world/entity/player/Player.java::tick(), lines 231-285`, SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; `world/entity/LivingEntity.java::tick(), lines 2765-2808`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; `world/entity/Entity.java::tick(),baseTick(), lines 506-535`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: local input tick/packet lifecycle; Entity fluid/powder-snow/swimming state -> LivingEntity.tick -> virtual LocalPlayer.aiStep -> `super.aiStep()` travel; `Player.tick` performs pose update after its superclass tick returns.
- Parent slices / dependencies / closure evidence: S-IN-01,S-LOCAL-01,S-LOCAL-02,S-LIVING-03,S-POSE-POST; caller chain must be resolved on A.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B call order read only; A timing and correspondence are unknown, so no pairwise disposition.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-POSE-POST: Player pose selection after superclass tick

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: `Player.tick()` calls `updatePlayerPose()` after `super.tick()` (lines 283-285); inspect `updatePlayerPose()` through `canPlayerFitWithinBlocksAndEntitiesWhen(Pose)` plus dimensions/AABB refresh and collision query dependencies.
- A evidence: pending exact source and method/caller correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/player/Player.java::tick(),updatePlayerPose(),canPlayerFitWithinBlocksAndEntitiesWhen(Pose), lines 231-285, 343-374`, SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`.
- State producers/writers -> consumers/readers: swimming/sleeping/fall-flying/shift/flight and collision-space predicates -> pose -> dimensions/eye height/bounding box -> next tick collision and fluid queries.
- Parent slices / dependencies / closure evidence: S-TICK-ENTRY,S-LOCAL-01,S-STATE-01,S-COLLISION-02; A pose/update timing, dimension defaults and all pose writers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; post-super ordering is a comparison target, not a finding without A evidence.
- Finding IDs or checked absence/replacement path: none yet.
## Dependency queue and blockers

- D-A-READY / D-A-SOURCE: exact current 26.1.2 native `unobfuscated` readiness JSON, source tree, hash manifests, artifact identity and relevant method-body diagnostics; requested through task commentary. Needed before paired source comparison. Owner: shared source-preparation owner.
- D-B-PROVENANCE: command, exit status/log path, named source owner, Vineflower version/options and other tool versions are absent from B ready JSON; obtain current source-owner metadata or record exact unavailable fields and their effect. B source, original jar and cited manifest hashes currently match.
- D-DEPENDENCIES: all pair-specific state writers, source overrides, registry/resource entries, tags, data, shape providers, attributes/effects/equipment and external packet dependencies found by the seven inventories. Add each as a child dependency with a parent slice and close against both endpoints.
- Open dependencies: D-A-READY, D-B-PROVENANCE, D-DEPENDENCIES.

## Finding index

No pairwise findings confirmed. Earlier reports for this interval were consulted only as navigation aids; their finding is not imported into this run and must be rechecked from current exact sources. Zero confirmed findings at this checkpoint does not mean equivalence.

## Resume checkpoint

- Last completed slice: none; current B readiness/provenance and selected B method bodies verified; 22 bounded preparation slices recorded; required workflow/template hardening cherry-picked.
- Next bounded slice and exact files/members/body ranges to open: on current A marker publication, validate A exact ID/manifests/hashes/diagnostics; resolve A/B KeyboardInput and local tick callers; begin `S-IN-01`, then the complete pre-travel call path. Before any pair comparison, update exact artifact provenance and rehash all cited sources.
- Outstanding dependencies and owners: D-A-READY (shared source owner), D-B-PROVENANCE (shared source owner), D-DEPENDENCIES (worker inventory).
- Current assumptions requiring verification: A marker confirms native unobfuscated official names; each cited body is intact; no movement-relevant resource/data is omitted. No release introduction point can be inferred from these two endpoints alone.

## Implementation reconciliation

- Reconciliation status: pending; outside this source-only assignment and prohibited until the source-only report is frozen and an explicit follow-up owner is assigned.
- Repository revision inspected: none.
- Finding -> implementation disposition/evidence: none; source findings are not yet confirmed.
- Existing implementation without a frozen source finding: not inspected.
- Coverage gaps routed back to discovery slices: pending; no implementation files inspected.

## Independent source audit

- Reviewer: not assigned in available task instructions; reviewer must not be a discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: none; discovery currently has B-only prep.
- Concrete missed-slice routes (or `none found`): not audited.
- Misses routed to slice/finding IDs and owners: not applicable before reviewer assignment.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: 22 pending; 0 in-progress; 0 compared-no-difference; 0 findings; 0 not-applicable; 0 blocked. This count is an initial B-prep queue, not the final exhaustive pair inventory; add every newly discovered slice and dependency.
- Required inventory status and evidence: all seven pending; current evidence is B-only and does not disposition any pair slice.
- Open dependencies: D-A-READY, D-B-PROVENANCE, D-DEPENDENCIES.
- Unresolved gaps and limits: no pairwise comparison started because the current 26.1.2 ready directory is absent. Full inventory, dependencies, findings, source freeze, independent audit and downstream reconciliation remain open.
- Evidence/hash/correspondence audit: B readiness JSON verified `versionId=26.2`; JSON-cited source, artifact and diagnostics manifest hashes recomputed and match; six cited B file hashes match the source manifest. Relevant B body ranges above were read. A current marker/source has not been read. Prior report hashes are not current evidence.
- Blind freeze: pending.
- Implementation reconciliation: pending.
- Independent audit: pending.
- Runtime validation: not performed; runtime is not authorized.


