# Discovery: 1.15.2 to 1.16.5

- Run status: partial
- Scope: source-only comparison of exact Java Edition A=1.15.2 and B=1.16.5; direct player movement and player velocity/impulse/knockback response are in scope; exclude health/food-state production, attack/damage resolution, non-player movement and vehicle physics.
- Repository revision and start date: campaign base 002137b227676caea77f6832b9f4c8d0b6200bff; 2026-10-07. Resume branch feat/source-discovery-1-15-2-1-16-5-resume.
- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap/Mojmap. Both ready records report exact requested/resolved metadata IDs and Mojmap; source and artifact manifests and cited Java files hash-match.
- Source preparation owner / command / log / readiness marker: shared source owner; both ready markers and provenance sidecars under ../../../build/movement-campaign-2026-10-07/ready. Exact command: .\gradlew.bat decompileMinecraft --versions=1.15.2,1.16.5,1.17.1,1.18.2 --mappings=mojmap --decompiler-heap=4G --output-root=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\staging\mojmap-1.15-to-1.18-f41b958fe6b84e6eb832e0c465a0e56d --cache-directory=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts. Provenance SHA-256 A d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e; B b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d. Success sidecars are summary excerpts only (A b8b60b3ce9a7f746be9e2023bbd9afb7c85dc58cb8582b9ac5962fc2c8a6348d; B 0974f4cdada4d0549cb730901064681d11a0f2cfbf1fc61783993b9dc4488b07); complete raw Gradle streams were not retained.
- Toolchain/decompiler/remapper versions and options: Java 25.0.3+9-LTS; Gradle 9.7.1; Vineflower 1.12.0; ASM 9.10.1; mapping-io 0.9.1; Gson 2.14.0; TinyRemapper 0.14.1; 4G heap, C:\Users\Wolfi\.gradle. Mojmap mapping inputs are the exact 1.15.2/client_mappings.txt and 1.16.5/client_mappings.txt entries; provenance records mappingArtifacts=null, so no mapping build coordinate is asserted.
- Discovery author(s): source-only pair researcher.
- Independent full-pair source reviewer: pending coordinator assignment; finding-level review decisions are recorded individually below.

## Artifact manifest

Shared roots are read-only under ../../../build/movement-campaign-2026-10-07. Both ready records say Gradle decompileMinecraft succeeded and their metadata IDs equal the requested IDs. Source counts match trees: A 3,332; B 3,523. Movement diagnostics report no source-body damage; method-body review is still performed per slice.

### A — 1.15.2, Mojmap
- Source root: ../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap
- Ready JSON: SHA-256 64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0
- Source manifest mojmap.sources.sha256: SHA-256 cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7
- Artifact manifest artifacts.sha256: SHA-256 208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406
- Movement diagnostics: SHA-256 1ab6c473dbbdf2c590a7a8870befbd404e634fe7c1b0ae6a796b7f5f9c63d4d5
- Client jar: 4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c
- Version JSON: e974511243e845b2427ab635eba5612481cb420a70c0630aa041eab3eeb590c5
- Official client mappings: 65ad295b6cf63821f5d8f961c128128475e6d39386358d22eb238a3ce6e31777
- Mojmap client jar: c0fe9cfe2a273c42ff269b58c9aabb93815d6bb7bf457ca1deb8695833c4380b
- Cited source hashes verified against the source manifest: LocalPlayer.java 3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd; Input.java 367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201; KeyboardInput.java 746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7; Player.java 1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793; LivingEntity.java 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54; Entity.java 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e; AABB.java 82324c0e6a3d69e80424656e6098b39ce41f0f17a5e6efbe6542fe8eaca24a18; VoxelShape.java 5faceea0e4ce9ad0f2a1a2c72a89d9a768ab877c8728ae2968fe34aa1e03d3ea; FlowingFluid.java e8a395552e53f33bcb5648325615d31bc1796ce2a8aeb501bdda3127622dccdf.

### B — 1.16.5, Mojmap
- Source root: ../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap
- Ready JSON: SHA-256 e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee
- Source manifest mojmap.sources.sha256: SHA-256 9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b
- Artifact manifest artifacts.sha256: SHA-256 f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c
- Movement diagnostics: SHA-256 676ea98fca6e2b73e0db8214057397af8112b0b9d37b72ad7d9b1025bd77ec50
- Client jar: 00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263
- Version JSON: 3eeeab7b3165cc5263dc26ff8fe114fdb718b75a0244efead9d19635d090ba72
- Official client mappings: 7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c
- Mojmap client jar: 7c1a9f30983fa3c777d00116bdf3f3cef97c1c1aaf8294027052f5337fcde940
- Cited source hashes verified against the source manifest: LocalPlayer.java 6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b; Input.java 367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201; KeyboardInput.java 746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7; Player.java d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960; LivingEntity.java b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88; Entity.java f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666; AABB.java 514558cf4827679d84a4debd4f400d7d65bd4b8982b7ae8124a0d83a916ba878; VoxelShape.java 6747647a5b94e69747340d432932b77ce5ad701c8f06e141e15b3dd68f8c3bde; FlowingFluid.java a85a5cd6625b1b4a047bd693399e1f78c2978ae207af927d4abfac3b34229552.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: not frozen
- Evidence inventory and finding IDs included at freeze: pending
- Confirmation of source-only boundary: no mod implementation, wiki, MCPK, release notes, or wiki-audit output opened; prior discovery report used only as navigation
- Source/mapping hashes covered: marker and core-file hashes verified; full cited evidence pending

## Correspondence and call order

- Local player A/B: net.minecraft.client.player.LocalPlayer. tick() lines A 179 and B 184 calls superclass tick before later local reporting. aiStep() lines A 625-783 and B 627-791 samples movement input, processes sprint/jump/fly/fluid gates, then calls super.aiStep().
- Input A/B: net.minecraft.client.player.Input.tick(boolean), getMoveVector(), hasForwardImpulse(); class hashes match.
- Keyboard producer A/B: net.minecraft.client.player.KeyboardInput.tick(boolean); class hashes match.
- Player/living A/B: net.minecraft.world.entity.player.Player and net.minecraft.world.entity.LivingEntity under same official names. Member-level correspondence and full travel call order remain open.
- Entity/collision A/B: net.minecraft.world.entity.Entity, AABB, VoxelShape and level collision query roles; changed helpers and dependencies remain open.
- All writer-consumer edges and the full pre-travel/travel/post-travel chain remain to be inventoried.

## Required source inventories

- INV-TICK input sampling, player tick/call graph, pre-travel, travel branches and post-travel: status=pending; slice_ids=S1-INPUT-VECTOR,S1-KEYBOARD,S1-LOCAL-TICK,S1-LOCAL-AISTEP,S3-*; evidence=paired LocalPlayer/Input/KeyboardInput sources above
- INV-STATE movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags and timers: status=pending; slice_ids=S1-LOCAL-AISTEP,S2-LOCAL-CROUCHING-STATE,S2-POSE,S2-STATE,S2-GATES,S4-STATE; evidence=paired LocalPlayer, Player, LivingEntity, Entity
- INV-COLLISION player collision/query path, shape providers, registrations, callbacks and neighbor dependencies: status=pending; slice_ids=S1-ESCAPE,S2-EDGE,S4-MOVE,S4-STEP,S4-QUERY,S5-SHAPES; evidence=paired LocalPlayer, Player, Entity, AABB, VoxelShape
- INV-WORLD-MOVEMENT block/fluid properties, subclasses, registries, data/tags and resource defaults: status=pending; slice_ids=S5-REGISTRY,S5-SHAPES,S5-FACTORS,S5-CONTACT,S5-FLUIDS,S5-MODERN
- INV-MODIFIERS movement attributes, effects, enchantments, equipment and application/removal conditions: status=pending; slice_ids=S3-ATTRS,S6-EFFECTS,S6-ENCHANTS,S6-EQUIPMENT,S6-RESOURCES
- INV-EXTERNAL player-only external movement inputs and client consumers, corrections, pushes, pistons and mounts: status=pending; slice_ids=S7-CORRECTIONS,S7-PUSH,S7-MOUNTS,S7-PISTONS,S7-LAUNCH
- INV-EXCLUSIONS health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=direct vanilla-state reads will be listed only as movement predicate inputs; producer systems and non-player simulations excluded

## Coverage ledger

### Slice S1-INPUT-VECTOR: input vector and forward impulse accessors
- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Input.getMoveVector() and hasForwardImpulse() method bodies.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/client/player/Input.java, net.minecraft.client.player.Input#getMoveVector/hasForwardImpulse, lines 20-25, SHA-256 367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/client/player/Input.java, same members and lines 20-25, same SHA-256.
- State producers/writers -> consumers/readers: Input.tick -> leftImpulse/forwardImpulse; the getters read those fields.
- Parent slices / dependencies / closure evidence: S1-KEYBOARD and LocalPlayer call sites.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): exact file hash and both accessor bodies match; this row is bounded to these accessors.
- Finding IDs or checked absence/replacement path: none

### Slice S1-KEYBOARD: keyboard-to-input producer
- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: KeyboardInput.tick(boolean), key reads, impulse signs and slowdown scaling.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/client/player/KeyboardInput.java, tick(boolean), lines 13-28, SHA-256 746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/client/player/KeyboardInput.java, tick(boolean), lines 13-28, same SHA-256.
- State producers/writers -> consumers/readers: Options keys -> Input buttons/impulses -> LocalPlayer.aiStep/LivingEntity.aiStep.
- Parent slices / dependencies / closure evidence: S1-INPUT-VECTOR, S1-LOCAL-AISTEP.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): full file hash and tick body match.
- Finding IDs or checked absence/replacement path: none

### Slice S1-LOCAL-TICK: local tick and reporting order
- Inventory ID(s): INV-TICK, INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer.tick(), passenger packet path, sendPosition() sprint/shift state commands and positional reporting.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java, tick lines 179-197 and sendPosition lines 199-260; SHA-256 3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java, tick lines 184-202 and sendPosition lines 214-275; SHA-256 6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b.
- State producers/writers -> consumers/readers: superclass tick -> local aiStep/state; passenger input and player movement/rotation/ground state -> serverbound packets; sprint/crouch state -> command packets.
- Parent slices / dependencies / closure evidence: S1-LOCAL-AISTEP,S7-CORRECTIONS,S7-INPUT-PACKETS; exact `tick()` and `sendPosition()` method bodies compared byte-for-byte after extraction from their endpoint sources. Server packet consumers remain outside this client-method row.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both endpoint `tick()` and `sendPosition()` bodies are identical, including passenger handling, superclass tick order, sprint/shift command reporting, positional packet thresholds and rotation/onGround reporting. This closes only these two LocalPlayer methods; input-state production and server correction/packet consumers remain in their owning slices.
- Finding IDs or checked absence/replacement path: none within these LocalPlayer method bodies.

### Slice S1-LOCAL-AISTEP: local movement inputs and gates
- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer.aiStep(), prior-state capture through super.aiStep(), exact order.
- A evidence: LocalPlayer#aiStep, lines 625-783, source hash in A manifest.
- B evidence: LocalPlayer#aiStep, lines 627-791, source hash in B manifest.
- State producers/writers -> consumers/readers: input, sprintTriggerTime, crouching, abilities.flying, fluid gates, fall-flying and super.aiStep; closure pending.
- Parent slices / dependencies / closure evidence: S1-ESCAPE,S1-SPRINT-RESET,S1-ELYTRA,S1-WATER-DESCENT,S2-LOCAL-CROUCHING-STATE,S2-STATE,S3-TRAVEL.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): extracted full-body comparison routes the escape search, shift-reset of sprint trigger, elytra climbability gate and fluid descent gate to their named slices. It also reveals B's new cached-crouching writer before `input.tick`; its effective timing and pose/input consumers remain open in S2-LOCAL-CROUCHING-STATE.
- Finding IDs or checked absence/replacement path: F-S1-OPEN-SHULKER-ESCAPE,F-S1-SPRINT-RESET,F-S1-WATER-DESCENT; common climb-gate evidence in S1-ELYTRA/S3-CLIMB-COMMON; cached-crouching producer still open.

### Slice S2-LOCAL-CROUCHING-STATE: cached local crouching state writer
- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B LocalPlayer.aiStep assignment to `crouching` before `input.tick`; paired A/B `isCrouching` implementations and LocalPlayer.isMovingSlowly consumer.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep lines 625-641 and isCrouching/isMovingSlowly lines 595-604, SHA-256 `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep lines 627-647 and isCrouching/isMovingSlowly lines 597-607, SHA-256 `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`.
- State producers/writers -> consumers/readers: B snapshots `!flying && !swimming && canEnterPose(CROUCHING) && (shift || (!sleeping && !canEnterPose(STANDING)))` before input tick -> `isCrouching()` -> `isMovingSlowly()` -> same-tick `input.tick` slowdown argument. A computes the crouching predicate on demand from current state/input.
- Parent slices / dependencies / closure evidence: S1-LOCAL-AISTEP,S1-LOCAL-TICK,S2-POSE,S2-STATE; the B writer order is source-confirmed, but same-tick input consumption, pose-clearance/canEnterPose call edges and all crouching-field readers are not yet inventoried.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B adds a stored field and assigns it before `input.tick`, while A's `isCrouching()` evaluates the condition when called. Since `input.tick` consumes `isMovingSlowly()` and pose clearance can affect the stored value, this is a candidate ordering/state delta. Exact consumer and call-order closure is pending; no movement finding is claimed yet.
- Finding IDs or checked absence/replacement path: source-level candidate for S2 pose/state inventory; pending consumer/call-order disposition.

### Slice S1-ESCAPE: player suffocation-space escape
- Inventory ID(s): INV-TICK, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: A LocalPlayer.checkInBlock/blocked/Player.freeAt and B LocalPlayer.moveTowardsClosestSpace/suffocatesAt plus four aiStep callers.
- A evidence: LocalPlayer#checkInBlock/blocked, lines 385-443; Player#freeAt, line 1489; LocalPlayer source SHA-256 in A manifest.
- B evidence: LocalPlayer#moveTowardsClosestSpace/suffocatesAt, lines 414-447; four callers lines 657-660; LocalPlayer source SHA-256 in B manifest.
- State producers/writers -> consumers/readers: position/bounding box -> four horizontal sample columns -> collision query -> selected x/z delta-velocity overwrite.
- Parent slices / dependencies / closure evidence: A Player.freeAt; B CollisionGetter.noBlockCollision/getBlockCollisions; BlockState.isSuffocating; shape providers.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): for a present open shulker block entity, A ShulkerBoxBlock.isSuffocating returns true unconditionally and A LocalPlayer checks this per block cell. B registers shulker suffocation as blockEntity.isClosed(); B collision query filters by this predicate before testing shape intersection. The open shulker is therefore a concrete common-block difference.
- Finding IDs or checked absence/replacement path: F-S1-OPEN-SHULKER-ESCAPE; A MovingPistonBlock reports false and B MOVING_PISTON isSuffocating is Blocks::never; A/B base piston predicates both reject extended bases. The shared common-block query-path checks are documented in the finding.

### Slice S1-SPRINT-RESET: held-shift cancellation of pending sprint trigger
- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer.aiStep prior input capture, input tick, sprint trigger writers and double-tap consumer.
- A evidence: LocalPlayer#aiStep, lines 625-641 and 657-671; source SHA-256 in A manifest.
- B evidence: LocalPlayer#aiStep, lines 627-647 and 663-681; source SHA-256 in B manifest.
- State producers/writers -> consumers/readers: previous Input.shiftKeyDown -> sprintTriggerTime; later sprint gate consumes timer.
- Parent slices / dependencies / closure evidence: S1-LOCAL-AISTEP, all sprintTriggerTime reads/writes in LocalPlayer.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B clears a live double-tap timer whenever the previously sampled shift input is held; A only suppresses sprint initiation during that sample and retains the timer.
- Finding IDs or checked absence/replacement path: F-S1-SPRINT-RESET

### Slice S1-ELYTRA: fall-flying start and climbability gate
- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer.aiStep jump gate; A LivingEntity.onLadder vs B onClimbable and tag data.
- A evidence: LocalPlayer#aiStep lines 715-720; LivingEntity#onLadder lines 1226-1236; source hashes in A manifest.
- B evidence: LocalPlayer#aiStep lines 725-730; LivingEntity#onClimbable lines 1363-1377; source hashes in B manifest.
- State producers/writers -> consumers/readers: feet block state/trapdoor/tag -> fall-flying initiation and movement mode; B also records lastClimbablePos.
- Parent slices / dependencies / closure evidence: B tag resource and membership; A explicit legacy block checks; paired trapdoor helper; travel consumers remain under S3-CLIMB.
- B resource evidence: the exact 1.16.5 client.jar entry `data/minecraft/tags/blocks/climbable.json` has SHA-256 `33bf334558c77268ba9bac414806b4b3842f8029e860857ff11abaf8447d03ff`; it lists ladder, vine, scaffolding, weeping vines/plant and twisting vines/plant. The client.jar SHA-256 is recorded in the B artifact manifest. No corresponding tag entry exists in the 1.15.2 client.jar, so A's comparison evidence is its `LivingEntity.onLadder` body, not a missing-resource inference.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): on the common historical block set, B's tag contains ladder/vine/scaffolding and A explicitly accepts those three blocks; both retain the same open-trapdoor/lower-ladder-facing helper. B-only weeping/twisting vines did not exist in A and are outside historical compatibility scope. This closes only the fall-flying/climbability gate boundary; helper/travel and state-writer dependencies remain in their owning slices.
- Finding IDs or checked absence/replacement path: none for the common historical block set; B-only vine membership excluded by scope.

### Slice S1-WATER-DESCENT: local crouch-to-descend impulse
- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer.aiStep water/shift condition; Player.isAffectedByFluids; LivingEntity.goDownInWater.
- A evidence: LocalPlayer#aiStep lines 722-725; goDownInWater lines 1819-1821; hashes in A manifest.
- B evidence: LocalPlayer#aiStep lines 732-735; Player#isAffectedByFluids lines 1005-1007; goDownInWater lines 1898-1900; hashes in B manifest.
- State producers/writers -> consumers/readers: input shift and water contact; abilities.flying -> isAffectedByFluids; goDownInWater adds (0,-0.04F,0) to delta movement.
- Parent slices / dependencies / closure evidence: all Player abilities writers/defaults and LocalPlayer.aiStep.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): for a flying player in water with shift held, A calls the shared -0.04F helper and B skips it through Player.isAffectedByFluids().
- Finding IDs or checked absence/replacement path: F-S1-WATER-DESCENT

### Slice S2-EDGE: edge-restraint trigger
- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Player.maybeBackOffFromEdge and B.isAboveGround, including unchanged 0.05-step loops.
- A evidence: Player#maybeBackOffFromEdge lines 1002-1049; SHA-256 1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793.
- B evidence: Player#maybeBackOffFromEdge lines 1010-1060 and isAboveGround lines 1062-1065; SHA-256 d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960.
- State producers/writers -> consumers/readers: flying/onGround/fallDistance/maxUpStep/support query -> reduced x/z movement -> Entity.move.
- Parent slices / dependencies / closure evidence: Player.isStayingOnGroundSurface; Entity.move A call at Entity.java:469 and B call at Entity.java:504 (the virtual call precedes collision resolution); exact B probe checked in Player.java:1062-1065. B returns true when onGround, or when fallDistance < maxUpStep and the shifted player box at y + fallDistance - maxUpStep is not collision-free. Its outer guard additionally requires not flying, SELF/PLAYER movement and shift held. No profile implementation or runtime applicability is claimed. Collision-query provider details remain linked to S4-QUERY/S5-SHAPES.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B replaces onGround with the source-confirmed isAboveGround() probe plus a not-flying guard; the common x/z reduction loops remain the same. Exact 1.16.5 branch applicability is bounded to this predicate and caller: SELF/PLAYER mover, shift held, not flying, and either onGround or the stated near-ground collision probe. The first changed release in the interval remains unknown; this does not establish coverage for any mod profile or runtime.
- Finding IDs or checked absence/replacement path: F-S2-EDGE; related collision-query finding F-S4-BORDER-EPSILON.

### Slice S3-WATER: water travel branch and falling adjustment
- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: LivingEntity.travel water dispatch, movement/friction, climb correction and post-move vertical adjustment.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java, lines 1927-1973; SHA-256 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java, lines 1923-1958 and helper getFluidFallingAdjustedMovement lines 2074-2087; SHA-256 b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88.
- State producers/writers -> consumers/readers: fluid contact/height and flying/stand-on-fluid predicates -> dispatch; sprinting, gravity, depth strider and dolphin's grace -> branch math; move -> collision and post-move velocity.
- Parent slices / dependencies / closure evidence: S1-WATER-DESCENT,S3-STATE,S3-ATTRS,S3-ENCHANTMENTS,S5-FLUIDS,S4-MOVE; verify Player affected-fluid and canStandOnFluid defaults.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for Player, B isAffectedByFluids is false only while flying and canStandOnFluid returns false; this matches A explicit flying bypass. B extracted falling-adjustment helper preserves A gravity/sprint conditions and y operation order after the shared x/z/y fluid friction.
- Finding IDs or checked absence/replacement path: none; helper and Player gates compared.

### Slice S3-LAVA: lava travel branch
- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: lava dispatch, input scaling, movement, velocity scaling, shallow-fluid branch and horizontal escape.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java, lines 1913-1926; SHA-256 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java, lines 1959-1978 and getFluidJumpThreshold lines 2712-2714; SHA-256 b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88.
- State producers/writers -> consumers/readers: lava contact and fluid-height map -> dispatch/threshold; eye height -> threshold; gravity and horizontal collision escape -> velocity result.
- Parent slices / dependencies / closure evidence: S3-STATE,S3-JUMP,S5-FLUIDS,S4-MOVE; exact lava fluid-height producer and player threshold reachability pending.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B adds shallow-lava vertical scaling and falling adjustment when fluid height is at or below the eye-height-derived threshold; A always scales all components by 0.5 before gravity.
- Finding IDs or checked absence/replacement path: F-S3-SHALLOW-LAVA-TRAVEL

### Slice S3-FALL-FLYING: fall-flying travel equations and collision response
- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: fall-distance initialization, look/velocity equations, drag, movement, wall impact and landing flag reset.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java, lines 1842-1884; SHA-256 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java, lines 1979-2022; SHA-256 b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88.
- State producers/writers -> consumers/readers: fall-flying flag, look vector, pitch, delta movement, horizontal collision and onGround -> drag, move, wall/landing response.
- Parent slices / dependencies / closure evidence: S1-ELYTRA,S4-MOVE,S2-STATE; arithmetic line-by-line correspondence pending.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): paired fall-flying equations preserve constants, casts, expression order, movement and landing collision response; local variable names and source positions differ.
- Finding IDs or checked absence/replacement path: none; damage/attack resolution remains in INV-EXCLUSIONS.

### Slice S3-GROUND-AIR: ordinary non-fluid travel and climbing response
- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION, INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: ordinary ground/air branch, friction lookup, relative input, climbable correction, move and gravity/levitation; B helper extraction included.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java, lines 1885-1912 and handleOnClimbable lines 1989-2018; SHA-256 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java, lines 2022-2042, handleRelativeFrictionAndCalculateMovement lines 2062-2071 and handleOnClimbable lines 2089-2118; SHA-256 b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88.
- State producers/writers -> consumers/readers: support block friction, input vector, movement attributes, climbability, gravity/levitation and collision flags -> requested/real movement and delta movement.
- Parent slices / dependencies / closure evidence: S1-ELYTRA,S4-MOVE,S5-SHAPES,S3-ATTRS,S3-RELATIVE-MOVEMENT,S3-CLIMB-COMMON; the extracted helper and common climbable tag/body are compared, while ground-air friction/state source and remaining travel dependencies stay open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B extracts ordinary relative-friction and climb handling and changes ladder terminology; full player-specific equality requires helper and tag closure.
- Finding IDs or checked absence/replacement path: pending

### Slice S3-RELATIVE-MOVEMENT: extracted ground/air relative-movement helper
- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: A inline `moveRelative` -> climbable delta correction -> SELF move -> post-move delta read; B extracted `handleRelativeFrictionAndCalculateMovement` body and matching friction-speed helper.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel lines 1889-1893 and `getFrictionInfluencedSpeed` lines 2006-2008, SHA-256 `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, helper lines 2062-2069 and `getFrictionInfluencedSpeed` lines 2106-2108, SHA-256 `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`.
- State producers/writers -> consumers/readers: ground friction/speed or flyingSpeed and input vector -> relative velocity -> climb correction -> move -> resulting delta vector returned to travel.
- Parent slices / dependencies / closure evidence: S3-GROUND-AIR,S4-MOVE; A's inline statements and B's extracted method preserve operation order, and the friction-influenced speed expression is identical.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): B's helper extraction preserves `moveRelative(speed, input)`, climbable velocity correction, SELF movement and post-move delta read in order; both friction-speed helpers use the same onGround ternary and arithmetic. Later ground-air gravity/drag and climbable predicate conditions remain in S3-GROUND-AIR/S3-CLIMB.
- Finding IDs or checked absence/replacement path: none within this extracted helper boundary.

### Slice S3-CLIMB-COMMON: common-era climbable movement response
- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: A onLadder versus B onClimbable common block set, trapdoor predicate, handleOnClimbable clamping/sliding guard and post-move climb bounce.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, onLadder lines 1226-1236, handleOnClimbable lines 1989-2003 and travel bounce lines 1893-1896, SHA-256 `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, onClimbable lines 1363-1377, handleOnClimbable lines 2089-2103 and helper bounce lines 2066-2069, SHA-256 `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`.
- Resource evidence: B `data/minecraft/tags/blocks/climbable.json` SHA-256 `33bf334558c77268ba9bac414806b4b3842f8029e860857ff11abaf8447d03ff`; its common entries ladder, vine and scaffolding match A's explicit block checks. B-only weeping/twisting vine entries are modern-only relative to A.
- State producers/writers -> consumers/readers: feet block/trapdoor and player movement flags -> fall-distance reset, x/z and y clamp, sliding suppression and 0.2 upward collision/jump bounce. B also writes lastClimbablePos, whose only exact-source consumer is CombatTracker and is excluded from movement simulation.
- Parent slices / dependencies / closure evidence: S1-ELYTRA,S3-GROUND-AIR,S4-MOVE; common tag members and A's explicit block checks align; trapdoor helper operation matches; clamp values, condition order and bounce constant match for common states.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for common-era ladder/vine/scaffolding and matching usable-trapdoor states, both versions apply the same climb reset, clamping, player ladder-slide suppression (except scaffolding), and collision/jump bounce. B's additional modern vines are excluded; its climb position writer feeds CombatTracker only and is outside the declared movement scope. This closes only the common climb-response branch, not full ordinary travel.
- Finding IDs or checked absence/replacement path: no difference for the common-era player movement response; modern-only climbable tags and excluded combat consumer noted above.

### Slice S3-JUMP: liquid jump branch and delay reset
- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: LivingEntity.aiStep jump block from outer input gate through water/lava/ground jump choice and noJumpDelay update.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java, lines 2265-2279; SHA-256 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java, lines 2427-2451; SHA-256 b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88.
- State producers/writers -> consumers/readers: jumping/onGround/noJumpDelay and A waterHeight/lava flag or B fluidHeight/affected-fluid gate -> selected jump helper and delay reset.
- Parent slices / dependencies / closure evidence: S1-LOCAL-AISTEP,S3-STATE; B Player.isAffectedByFluids and Entity fluid-height writer/threshold inspected.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B gates the liquid jump branch on isAffectedByFluids and selects ground jump for grounded lava at or below threshold; A can invoke the liquid helper under those states.
- Finding IDs or checked absence/replacement path: F-S3-FLUID-JUMP-GATE,F-S3-SHALLOW-LAVA-JUMP

### Slice S4-MOVE: player displacement and collision-result state
- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Entity.move(MoverType,Vec3) displacement, collision resolution, step-up dispatch, callbacks and post-move state writes; provider callback bodies are inventoried separately.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/entity/Entity.java`, lines 450-589; SHA-256 `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/entity/Entity.java`, lines 485-616; SHA-256 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`.
- State producers/writers -> consumers/readers: requested movement and collision-resolved vector -> position, horizontal/vertical collision, onGround, fall/step/contact callbacks, clipped delta movement and block-speed factor; downstream tick readers are tracked in S1/S3 rows.
- Parent slices / dependencies / closure evidence: S2-EDGE,S3-WATER,S3-LAVA,S3-GROUND-AIR,S4-QUERY,S4-STEP,S5-SHAPES; the displacement/collision-result route was compared, while callback providers and block-speed-factor producers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): this shared Entity path is reached by player travel and is covered within the exact method range. Collision result writes and axis resolution are compared; closure remains open for provider callbacks and the virtual block-speed-factor producer chain. No vehicle or non-player trajectory is claimed.
- Finding IDs or checked absence/replacement path: pending dependency closure.

### Slice S4-QUERY: player block and entity collision query stream
- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Entity.collide movement query; A CollisionGetter inline cursor/border scan; B CollisionSpliterator cursor/border scan; paired entity-collider query for Player input.
- A evidence: Entity.java, collide lines 641-684, SHA-256 `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`; CollisionGetter.java, getBlockCollisions lines 67-109, SHA-256 `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd`; EntityGetter.java, getEntityCollisions lines 53-67, SHA-256 `f2c614b216f498069b0e4a033705afef96509599c42eb21a6b612387207fcd2c`.
- B evidence: Entity.java, collide lines 668-711, SHA-256 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`; CollisionGetter.java, getBlockCollisions lines 52-68, SHA-256 `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac`; CollisionSpliterator.java, cursor/border scan lines 13-135, SHA-256 `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297`; EntityGetter.java, getEntityCollisions lines 59-78, SHA-256 `12b4ad28f0a92414ec1478dd42b6a41ff6cd0a0a00650ae37490ea8cd0a1bba5`.
- State producers/writers -> consumers/readers: Player source entity and current AABB plus requested vector -> expanded query bounds, block/entity collision shapes and world-border shape -> resolved movement and collision flags.
- Parent slices / dependencies / closure evidence: S2-EDGE,S2-POSE,S4-MOVE,S4-STEP,S5-SHAPES,S4-BORDER-GUARD. Cursor bounds and entity candidate filters were compared. The direct Entity.collide guard matches; the standalone lazy-query epsilon difference and Player support-probe reachability are recorded in F-S4-BORDER-EPSILON. Remaining entity-candidate filter/producer and provider extent remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B's CollisionSpliterator cursor and shape-intersection route and the Player entity-collider candidates were compared at the cited boundaries. The separate lazy border predicate has a bounded epsilon-near difference reachable through Player support probes, recorded as F-S4-BORDER-EPSILON. Complete registered/provider extent and entity-candidate filter behavior remain open; no player trajectory is claimed in this row.
- Finding IDs or checked absence/replacement path: F-S4-BORDER-EPSILON; remaining query-route dependencies stay open.

### Slice S4-BORDER-GUARD: world-border collision-query guard
- Inventory ID(s): INV-COLLISION, INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: direct Entity.collide border candidate, A CollisionGetter's lazy border check, B CollisionSpliterator's factored lazy border guard, border-shape integer bounds, and direct Player collision-query callers.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/entity/Entity.java`, collide lines 641-649, SHA-256 `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`; `CollisionGetter.java`, noCollision and border check lines 43-58 and 78-90, SHA-256 `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd`; `WorldBorder.java`, moving/static collision bounds lines 330-344 and 409-423, SHA-256 `5030e47d299d2741339dae0274dab41e63ae1eb1468171ef14f181a71a03086f`; Player support-probe caller lines 1002-1049, SHA-256 `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/entity/Entity.java`, collide lines 668-676, SHA-256 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`; `CollisionGetter.java` noCollision/getBlockCollisions lines 44-68, SHA-256 `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac`; `CollisionSpliterator.java`, worldBorderCheck/isCloseToBorder/isOutsideBorder/isBoxFullyWithinWorldBorder lines 101-135, SHA-256 `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297`; `WorldBorder.java`, moving/static collision bounds lines 324-338 and 500-514, SHA-256 `2925df8d985d0ec2d075d4f5c57538dcc628528b72a1d35e90af27fe4fbadb2a`; Player support-probe caller lines 1010-1075, SHA-256 `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`.
- State producers/writers -> consumers/readers: current entity AABB and integer world-border bounds -> A's deflate/inflate check or B's additional fully-within check -> border shape emission into direct noCollision queries -> Player edge-support branch; direct Entity.collide uses the same border candidate in both versions.
- Parent slices / dependencies / closure evidence: S2-EDGE,S4-MOVE,S4-QUERY; both WorldBorder sources construct the collision interior using floor(min) and ceil(max). For an entity AABB strictly inside those integer bounds but less than `1.0E-7` from a face, A's inflated-only overlap emits the border shape while B's fully-within guard suppresses it. Player support probes call the collision query directly. Other query callers and provider coverage remain open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): source-confirmed lazy-query difference, bounded to entity-sourced collision queries whose entity AABB is within `1.0E-7` of an integer collision boundary while remaining fully inside that boundary and with no other nonempty shape masking the result. No final displacement is claimed.
- Finding IDs or checked absence/replacement path: F-S4-BORDER-EPSILON.

### Slice S4-STEP: step-up candidate selection
- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Entity.collide step-up branch, candidate vector construction, candidate-box shifts, horizontal-distance comparisons and downward correction.
- A evidence: Entity.java, lines 651-681; SHA-256 `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`.
- B evidence: Entity.java, lines 679-709; SHA-256 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`.
- State producers/writers -> consumers/readers: maxUpStep, onGround, downward requested/resolved y and horizontal collision flags -> three step candidates, horizontal-distance comparisons and final downward correction.
- Parent slices / dependencies / closure evidence: S4-MOVE,S4-QUERY,S5-SHAPES; candidate arithmetic and helper order compared.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both versions require maxUpStep > 0, onGround or downward y clipping, and horizontal clipping; they build the same candidates in the same order, retain the candidate with greatest horizontal distance squared, and apply the same downward resolution. The underlying collision query remains separately open in S4-QUERY.
- Finding IDs or checked absence/replacement path: none within candidate construction/selection.

### Slice S5-SHAPES: block collision-shape dispatch route (provider inventory open)
- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: CollisionGetter.getBlockCollisions -> BlockState.getCollisionShape(..., CollisionContext) -> block getCollisionShape. The base implementation then calls BlockState.getShape(BlockGetter,BlockPos), selecting the empty-context overload; direct getCollisionShape overrides receive the supplied CollisionContext. A's support/sturdiness path derives from collision shapes; B has a separate getBlockSupportShape dispatch and provider overrides. Neighbor-driven state updates, remaining provider-specific bodies and registry/resource reachability remain open.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/CollisionGetter.java`, getBlockCollisions lines 67-109, SHA-256 `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd`; `net/minecraft/world/level/block/state/BlockState.java`, getShape/getCollisionShape lines 184-199 and isFaceSturdy lines 329-330, SHA-256 `77320fd1e50e58d7a3ed593ccbcf21853c97d125fcebd2e0e305dc47524f21`; `net/minecraft/world/level/block/Block.java`, getShape/getCollisionShape lines 386-393, SHA-256 `a5819a4c676d2b7e08cce7f80ae80a13726dd17b15e0b29efdce7e37efa2bf0d`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/CollisionGetter.java`, getBlockCollisions/getCollisions lines 58-70, SHA-256 `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac`; `net/minecraft/world/level/block/state/BlockBehaviour.java`, BlockStateBase shape forwarding lines 516-533 and block getShape/getCollisionShape/getBlockSupportShape lines 227-282, SHA-256 `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b`.
- State producers/writers -> consumers/readers: block-specific state properties -> collision-context-sensitive provider -> collision stream and movement resolution; entity, position and AABB -> CollisionContext/query bounds. B's separate support-shape providers are inventoried under S5-SUPPORT-SHAPES.
- Parent slices / dependencies / closure evidence: S1-ESCAPE,S2-EDGE,S2-POSE,S4-MOVE,S4-QUERY,S4-STEP,S5-REGISTRY,S5-CONTACT. Each full block subtree has 17 Java files with at least one getCollisionShape declaration: the base provider, 13 named block overrides, MovingPistonBlock, PistonMovingBlockEntity's dynamic-shape method and the state wrapper. A block overrides are Block, BambooBlock, BellBlock, CactusBlock, ComposterBlock, CrossCollisionBlock, FenceGateBlock, GrindstoneBlock, HoneyBlock, LecternBlock, ScaffoldingBlock, SnowLayerBlock, SoulsandBlock and WallBlock, plus piston/MovingPistonBlock; B replaces the Block base with BlockBehaviour, adds LiquidBlock and renames SoulsandBlock to SoulSandBlock, with the other providers and piston path retained. State dispatch moves from BlockState to BlockBehaviour.BlockStateBase. B's support-shape overrides in LeavesBlock, SnowLayerBlock and SoulSandBlock and their exact-tree player-consumer search are dispositioned in S5-SUPPORT-SHAPES. CrossCollisionBlock cache/index, fixed wall/fence collision geometry, scaffolding, slab, snow and ladder collision selection, plus cactus/bamboo/honey bounded providers are compared in their named slices. Remaining provider bodies and registrations stay open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): the compared base route preserves `hasCollision ? state.getShape(blockGetter, blockPos) : Shapes.empty()`; the base fallback uses the empty-context state overload in both versions. This is not evidence that common block shapes are equal or that the provider inventory is complete. Modern-only B blocks remain excluded from A-version claims.
- Finding IDs or checked absence/replacement path: no result from the dispatch-only comparison; pose-clearance shape dependency remains under S2-POSE.

### Slice S5-CACTUS-COLLISION: cactus fixed collision shape
- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards checked: CactusBlock.getCollisionShape and its fixed COLLISION_SHAPE definition; AGE is not read by collision selection.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/CactusBlock.java`, COLLISION_SHAPE definition and getCollisionShape lines 25-26, 68-70, SHA-256 `d3734ca45940417c6f3fb83039cd0686320b3dad3862f9d8bc868dc4d4b08428`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/CactusBlock.java`, corresponding definition/method lines 26-27, 70-72, SHA-256 `48bae92088793ff2002ac3d79bd35f923b54a78a9d976479a21b80bea9a6b787`.
- State producers/writers -> consumers/readers: supplied cactus state -> fixed 1..15 by 0..15 by 1..15 collision box -> CollisionGetter sweep.
- Parent slices / dependencies / closure evidence: S4-QUERY,S5-SHAPES; the paired method returns and box coordinates match.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both endpoint methods return the same fixed `COLLISION_SHAPE`; AGE and collision context do not affect the box. This does not cover cactus state lifecycle or entity-inside damage.
- Finding IDs or checked absence/replacement path: none within this fixed collision provider.

### Slice S5-BAMBOO-COLLISION: bamboo offset collision shape
- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards checked: BambooBlock.getCollisionShape state offset and translated collision shape; state-offset producer is a supplied-state dependency.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/BambooBlock.java`, getCollisionShape lines 111-114, SHA-256 `c6df0adcf3d1203d66eb196404ca3cb006e7c5aa6e1cb9d7d3471bb7903b5b6b`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/BambooBlock.java`, getCollisionShape lines 112-115, SHA-256 `7522c0fa5014068a8d0a2909950fbbbae2344bdb0553698f3c01cb7434e4897`.
- State producers/writers -> consumers/readers: supplied bamboo state and position -> same `getOffset` vector applied to the same `COLLISION_SHAPE` -> CollisionGetter sweep.
- Parent slices / dependencies / closure evidence: S4-QUERY,S5-SHAPES; paired methods have identical offset-and-translate operation order. A/B block-offset producer and registration reachability remain open under S5-REGISTRY.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for a supplied state and position, both methods obtain the same state offset and translate the collision shape by vector x/y/z in the same order. This bounded provider comparison does not disposition offset state production or registration.
- Finding IDs or checked absence/replacement path: none within this provider method.

### Slice S5-BAMBOO-OFFSET: deterministic bamboo collision offset
- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards checked: BambooBlock.getOffsetType plus A Block.getOffset / B BlockStateBase.getOffset seed and coordinate arithmetic.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/BambooBlock.java`, getOffsetType lines 47-49, SHA-256 `c6df0adcf3d1203d66eb196404ca3cb006e7c5aa6e1cb9d7d3471bb7903b5b6b`; `Block.java`, getOffset lines 774-788, SHA-256 `a5819a4c676d2b7e08cce7f80ae80a13726dd17b15e0b29efdce7e37efa2bf0d`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/BambooBlock.java`, getOffsetType lines 48-50, SHA-256 `7522c0fa5014068a8d0a2909950fbbaa5e2344bdb0553698f3c01cb7434e4897`; `BlockBehaviour.java`, BlockStateBase.getOffset lines 552-565, SHA-256 `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b`.
- State producers/writers -> consumers/readers: BambooBlock returns XZ -> block-position x/z seed -> deterministic x/z vector -> translated collision shape from S5-BAMBOO-COLLISION; y offset is zero for XZ.
- Parent slices / dependencies / closure evidence: S4-QUERY,S5-SHAPES,S5-BAMBOO-COLLISION; both implementations use `Mth.getSeed(x, 0, z)`, identical low/high seed-bit coordinate expressions and the same `(value - 0.5) * 0.5` horizontal scaling. State and position are fixed inputs to the collision query.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): BambooBlock selects XZ in both versions, and A's block helper and B's state helper compute the same x/z offset with y=0 in the same arithmetic order. This closes the supplied-position offset consumed by bamboo collision/outline shapes; it does not close the full registry/resource inventory.
- Finding IDs or checked absence/replacement path: none within this deterministic offset provider.

### Slice S5-HONEY-COLLISION: honey fixed collision shape
- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards checked: HoneyBlock.getCollisionShape and fixed SHAPE return; fall-on/inside movement callbacks are separate slices.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/HoneyBlock.java`, lines 37-39, SHA-256 `40760aeb3c084f1143e87e1e057f18165492eb01b8fce0815dfdd0882cdc8a03`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/HoneyBlock.java`, lines 37-39, SHA-256 `6ca24cf40dbb2dcfabfd26c4ac188746ec2e21906f54a23f40d6eb681d94a492`.
- State producers/writers -> consumers/readers: supplied honey block -> fixed SHAPE -> CollisionGetter sweep; collision callback behavior remains separately inventoried.
- Parent slices / dependencies / closure evidence: S4-QUERY,S5-SHAPES; paired provider methods return the same named shape constant.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both methods return SHAPE without reading state, position or collision context. This establishes only the fixed provider return, not the separate honey movement callbacks or shape construction reachability.
- Finding IDs or checked absence/replacement path: none within this fixed collision provider.

### Slice S5-COMPOSTER-COLLISION: composter collision shape
- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards checked: ComposterBlock.getCollisionShape and the SHAPES[0] construction used by that fixed return; LEVEL affects outline shape only.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/ComposterBlock.java`, SHAPES construction lines 41-47 and getCollisionShape lines 180-182, SHA-256 `a52c4867b8aeb55b11157c25217503faa62dfcfaf39ac768d2a1825e8bcfe407`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/ComposterBlock.java`, SHAPES construction lines 42-48 and getCollisionShape lines 192-194, SHA-256 `e822660ad4dcc0180b2af2dbca07680d909637ba05e53b8e6b5398e931d630ac`.
- State producers/writers -> consumers/readers: supplied composter -> fixed SHAPES[0] (outer cube minus same inner volume from y=2 through 16) -> CollisionGetter sweep; LEVEL is not read by collision selection.
- Parent slices / dependencies / closure evidence: S4-QUERY,S5-SHAPES; paired SHAPES[0] return and array-entry construction match.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both methods return the same first array entry, whose outer cube and inner cutout construction are identical. This is limited to collision shape and does not compare composter interaction or lifecycle behavior.
- Finding IDs or checked absence/replacement path: none within this fixed collision provider.

### Slice S5-SOUL-SAND-COLLISION: soul sand fixed collision shape
- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards checked: Soul(s)andBlock.getCollisionShape and its fixed SHAPE definition; support-shape dispatch is separately dispositioned in S5-SUPPORT-SHAPES.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/SoulsandBlock.java`, SHAPE line 16 and getCollisionShape lines 23-25, SHA-256 `355f0cc25e78dce76cbc9c89d8bd8ff1956d4b53780cba8e3fb5c23f485802cf`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/SoulSandBlock.java`, SHAPE line 18 and getCollisionShape lines 25-27, SHA-256 `da92572788c9416b511c003e50688d802e8248ccdb3e2d5eb155aa482549b095`.
- State producers/writers -> consumers/readers: supplied soul-sand state -> fixed full-footprint 14/16-height collision box -> CollisionGetter sweep.
- Parent slices / dependencies / closure evidence: S4-QUERY,S5-SHAPES; shape coordinates and provider return match in both sources.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both providers return the same context-independent box from y=0 through 14 of the 16-unit block. B's full-cube support provider remains outside player movement and is handled independently.
- Finding IDs or checked absence/replacement path: none within this fixed collision provider.

### Slice S5-FENCE-GATE-COLLISION: gate collision shape selection
- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards checked: FenceGateBlock.getCollisionShape and the two axis collision boxes; OPEN controls emptiness and FACING axis selects the box.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/FenceGateBlock.java`, box definitions lines 31-32 and method lines 66-72, SHA-256 `a141dc6aaaff85797fbec5552bd2b8499b418f05da00207b2c68e9be1bccdfd29`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/FenceGateBlock.java`, box definitions lines 32-33 and method lines 67-73, SHA-256 `b8bb1b3f41cec611d9fd447a8d52b133eeba123902ea974aae9d144426ca3085`.
- State producers/writers -> consumers/readers: OPEN and FACING axis -> empty or 4-unit-wide, 24-unit-high collision plane -> CollisionGetter sweep; IN_WALL affects visual shape only.
- Parent slices / dependencies / closure evidence: S4-QUERY,S5-SHAPES; paired decision order, box bounds and dimensions match. Neighbor writers for OPEN/IN_WALL are not closed here.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for supplied states, both providers return empty when OPEN and otherwise select the same axis-aligned collision box. This method-level result does not close state writers or gate registration reachability.
- Finding IDs or checked absence/replacement path: none within this fixed collision provider.

### Slice S5-VINE-SHAPE: vine state-to-shape construction
- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards checked: A VineBlock.getShape plus face constants versus B cached calculateShape result and getShape lookup, for supplied UP/NORTH/EAST/SOUTH/WEST values.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/VineBlock.java`, face constants lines 32-36 and getShape lines 46-69, SHA-256 `3cf8e12e44c88728cd86c3191055df05b81562d71bcf5d977a935fada43f8a5d`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/VineBlock.java`, face constants lines 33-37, calculateShape lines 56-80 and cached getShape lines 82-84, SHA-256 `b9fc4bd02bccb4807bf206f85adb354012bdb678925567294d9b90502dc7aedb`.
- State producers/writers -> consumers/readers: five supplied boolean face properties -> union of top and four 1-unit-thick face planes -> CollisionGetter sweep.
- Parent slices / dependencies / closure evidence: S4-QUERY,S5-SHAPES; A's reversed constant names are paired with opposite property selection, while B's names match property direction. Coordinates and resulting unions agree for each supplied property set; B precomputes/caches all possible state shapes.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): each property yields the same occupied face plane in both endpoints despite A's opposite-direction constant naming; the top plane and OR accumulation are also equal. B's eager cache changes calculation timing only. Neighbor attachment/survival writers and vine registration remain open.
- Finding IDs or checked absence/replacement path: none within the supplied-state collision shape construction.

### Slice S5-BELL-COLLISION: bell attachment collision geometry
- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards checked: BellBlock.getCollisionShape -> getVoxelShape using FACING and ATTACHMENT; all returned collision shape constants were compared.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/BellBlock.java`, shape constants lines 39-50, helper and collision method lines 138-158, SHA-256 `e16da46fc837b526c41571ed1221cb612bf83522899baa6dcf8693a41ce81a24`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/BellBlock.java`, corresponding constants lines 40-51 and helper/method lines 137-157, SHA-256 `6977af9b5ee64408bd57cc570225be2af29a95f313cc63c346c838a44d4370f7`.
- State producers/writers -> consumers/readers: supplied FACING and BellAttachType -> matching floor, ceiling, between-wall or directional bell shape -> CollisionGetter sweep.
- Parent slices / dependencies / closure evidence: S4-QUERY,S5-SHAPES; helper branch order and every referenced collision constant have identical coordinates/shape composition. Attachment state production remains open under the neighbor/state inventory.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for each supplied attachment and facing value, both helpers choose the same named shape constants with the same branch ordering; all constants are identical. This closes only fixed-state collision selection, not attachment writers or registration.
- Finding IDs or checked absence/replacement path: none within the supplied-state bell collision provider.

### Slice S5-LECTERN-COLLISION: lectern fixed collision geometry
- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards checked: LecternBlock.getCollisionShape and SHAPE_COLLISION construction; FACING, POWERED and HAS_BOOK do not affect collision selection.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/LecternBlock.java`, SHAPE_COLLISION construction lines 38-42 and getCollisionShape lines 94-96, SHA-256 `740afa10689d0ac1b7c88cb14bfc854c2a35c9128cc3f1a62f7e3e55f1aeb908`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/LecternBlock.java`, corresponding construction lines 40-44 and method lines 108-110, SHA-256 `f114900df6174a6a88fa60c91122aff16d52d2a59dbd0efe59e4c275fd1894a9`.
- State producers/writers -> consumers/readers: supplied lectern block -> union of same base, post and top plate -> CollisionGetter sweep.
- Parent slices / dependencies / closure evidence: S4-QUERY,S5-SHAPES; paired constant construction and fixed-return methods match.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both methods return the same `SHAPE_COLLISION`, composed from identical base/post and top-plate boxes. The facing-specific outline and lectern block-entity behavior do not alter this collision provider.
- Finding IDs or checked absence/replacement path: none within this fixed collision provider.

### Slice S5-GRINDSTONE-COLLISION: grindstone state-to-shape selection
- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards checked: GrindstoneBlock.getCollisionShape -> getVoxelShape, including the full directional/attachment shape table and all referenced shape constants.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/GrindstoneBlock.java`, all 64 VoxelShape declarations lines 27-90, getVoxelShape lines 103-134 and collision method lines 136-138, SHA-256 `a1c070ab45b0d58ebcfd0821939701c6de41fd8295ec23e96916de50add43197`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/GrindstoneBlock.java`, all 64 corresponding VoxelShape declarations lines 27-92, getVoxelShape lines 105-136 and collision method lines 138-140, SHA-256 `2427ca4055a07ca81b55964abf14cbb7ad8bc605bbb6f8bd08369583a7f7ac61`.
- State producers/writers -> consumers/readers: supplied FACING and attachment state -> same directional floor/wall/ceiling grindstone shape -> CollisionGetter sweep.
- Parent slices / dependencies / closure evidence: S4-QUERY,S5-SHAPES; all 64 shape declarations and the helper body are identical across endpoint source text, and both collision methods call the helper.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for each supplied facing and attachment type, both providers execute the same helper branches over identical shape constants. This closes only the fixed-state provider and leaves grindstone attachment writers and registry reachability open.
- Finding IDs or checked absence/replacement path: none within this supplied-state collision provider.

### Slice S5-WALL: wall neighbor-state producer (lifecycle scope)
- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards checked: WallBlock.connectsTo and its placement/neighbor state writers; the resulting state is consumed by the separate fixed-state shape slice S5-WALL-FIXED-COLLISION.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/WallBlock.java`, lines 60-125, SHA-256 `4ad01bfd8d84b77abe161289294667e9f482cf9477cd81f725162aa9dc891850`; `Block.java` sturdy/exception predicates lines 269-276 and 419-425, SHA-256 `a5819a4c676d2b7e08cce7f80ae80a13726dd17b15e0b29efdce7e37efa2bf0d`; IronBarsBlock narrow-shape constructor lines 14-18, SHA-256 `377a52caac0e4d8de9829b30e62e73430bd160ce868f1347fe414587fe2ba373`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/WallBlock.java`, lines 129-168 and 192-258, SHA-256 `aa770704573ee65ca0d0b102bc55e3ece81f7b25aef452bb5c89ec0350a23442`; IronBarsBlock narrow-shape constructor lines 19-23, SHA-256 `e84392b278ceeaf02abb0c72c74e1011629d7c0add57f6bf0f6ec5b6006feaab`; CrossCollisionBlock collision provider lines 46-95, SHA-256 `cebb3dcbbb94ab7513e11430fa4969bc1ff0a96c733298940889fc749b7ec1e4`; BlockBehaviour support fallback lines 231-237, SHA-256 `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b`; Block.java isExceptionForConnection line 167, SHA-256 `2838aa4fb4052b3bd78d77b3d232a6321d03bd2dfd52275ffacfe6915a89e5b6`; Blocks.java IRON_BARS registration line 906, SHA-256 `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12`.
- State producers/writers -> consumers/readers: neighbor class/tag/support checks -> wall-side state -> wall collision provider. A's WALLS tag is `walls.json` SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; B's is `0b9aa2dd6ac31252fcd7786d53a3f79c0f2365998711c38cc19f2e8b788796a2` in the exact client jars.
- Parent slices / dependencies / closure evidence: S5-SUPPORT-SHAPES,S5-WALL-FIXED-COLLISION,S5-SHAPES; exact paired source and WALLS resources inspected.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): B's predicate explicitly connects to IronBarsBlock; A has no such branch, and the narrow iron-bars collision face is not full, the block is not in WALLS, and it is not a connection exception. The neighbor update can therefore produce a different wall-side state in B. That producer is block-state lifecycle behavior, which the user scoped out; the fixed-state collision geometry remains separately covered and is not inferred from this state update.
- Finding IDs or checked absence/replacement path: explicit out-of-scope lifecycle disposition; no movement finding.

### Slice S5-WALL-FIXED-COLLISION: wall collision geometry for supplied states
- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards checked: WallBlock.getCollisionShape and shape construction after UP and side-state inputs are supplied; placement/update producers are excluded.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/WallBlock.java`, constructor/getCollisionShape lines 25-53, SHA-256 `4ad01bfd8d84b77abe161289294667e9f482cf9477cd81f725162aa9dc891850`; inherited CrossCollisionBlock.makeShapes/getCollisionShape lines 35-90, SHA-256 `5088eb166ca1ca36eb496ab120338d713c30bec84ff5815ec266a52ea1444ecd`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/WallBlock.java`, shape construction/getCollisionShape lines 30-123, SHA-256 `aa770704573ee65ca0d0b102bc55e3ece81f7b25aef452bb5c89ec0350a23442`.
- State producers/writers -> consumers/readers: supplied UP and four side values -> union of center post and side arms -> CollisionGetter shape sweep.
- Parent slices / dependencies / closure evidence: S4-QUERY,S5-SHAPES; A boolean side states project to B NONE/non-NONE values. B LOW and TALL collision arms use equal 24-unit heights, so both project to A's same side boxes; UP and waterlogging inputs select the same collision geometry.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for each fixed common wall-state projection, both providers produce the same occupied collision regions: a 4-unit-half-width post when UP is true, plus the same 6-unit-wide, 24-unit-high arms for each connected side; UP false omits the post. B's LOW/TALL distinction changes visual arm geometry, not these collision boxes. This does not close the out-of-scope neighbor-state producer or claim an observed trajectory.
- Finding IDs or checked absence/replacement path: none within the bounded fixed-state collision provider.

### Slice S5-SLAB-SHAPE: slab collision-shape selection
- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards checked: SlabBlock.getShape selection by TYPE; placement, waterlogging, neighbor updates and support-shape behavior are excluded from this method-only slice.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/SlabBlock.java`, lines 47-58, SHA-256 `5a57ae37e77fc93074100d7e3eff1c5e23b43d8a23e0289b68ad8c9914df0d86`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/SlabBlock.java`, lines 48-59, SHA-256 `21ff767d6bcf05ff225e864f4360b724b6fe70cad8636566c046dd22b5509e6b`.
- State producers/writers -> consumers/readers: slab TYPE (BOTTOM/TOP/DOUBLE) -> full, upper-half or lower-half shape -> collision query and movement resolution.
- Parent slices / dependencies / closure evidence: S4-QUERY,S5-SHAPES; paired getShape bodies inspected and return the same named static shapes for each TYPE value.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both methods use the same TYPE switch: DOUBLE returns Shapes.block(), TOP returns TOP_AABB, and the remaining value returns BOTTOM_AABB. This establishes only shape selection once TYPE is supplied; its writers, state resources and other shape consumers remain outside this row.
- Finding IDs or checked absence/replacement path: none within the bounded getShape method.

### Slice S5-SNOW-COLLISION: snow-layer collision shape
- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards checked: SnowLayerBlock.getCollisionShape selection by LAYERS; support-shape behavior and layer-state writers remain separate.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/SnowLayerBlock.java`, lines 56-63, SHA-256 `89a20299049e7b33f92bc5a6d18f3c0c5d429a7891d85179315b68845a1ff486`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/SnowLayerBlock.java`, lines 57-64, SHA-256 `b7c42f5797f41f9281f850cbd9ff7af9256018f092135cc799d76c019d850f70`.
- State producers/writers -> consumers/readers: LAYERS -> `SHAPE_BY_LAYER[LAYERS - 1]` collision shape -> movement collision sweep.
- Parent slices / dependencies / closure evidence: S4-QUERY,S5-SHAPES; paired collision-shape methods select the same indexed shape.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both methods return `SHAPE_BY_LAYER[LAYERS - 1]`; this is only collision-shape selection after LAYERS is supplied. B also adds a separate `getBlockSupportShape` method returning `SHAPE_BY_LAYER[LAYERS]`; its support-predicate consumers remain open under S5-SHAPES.
- Finding IDs or checked absence/replacement path: none within the bounded collision method.

### Slice S5-SUPPORT-SHAPES: sturdy-face support dispatch
- Inventory ID(s): INV-COLLISION, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards checked: A Block.isFaceSturdy collision-face test versus B BlockStateBase.isFaceSturdy/SupportType dispatch and the B Leaves/SnowLayer/SoulSand support providers.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/Block.java`, lines 419-425, SHA-256 `a5819a4c676d2b7e08cce7f80ae80a13726dd17b15e0b29efdce7e37efa2bf0d`; BlockState.java lines 184-199, SHA-256 `77320fd1e50e58d7a3ed593ccbcf21853c97d125fcebd2e0e305dc47524f21`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java`, support dispatch lines 231-237, 532-533 and 703-712, SHA-256 `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b`; SupportType.java FULL/CENTER/RIGID consumers lines 11-40, SHA-256 `7a0fd4eb61a3cc444116af052dea22fb52c60fccb754bead28f5219f6ad99d19`; LeavesBlock.java lines 32-34, SHA-256 `4a39d80f4c709b74c8a0e87a5c49140eec40679c32a008b5ef64e1b07d098498`; SnowLayerBlock.java lines 67-69, SHA-256 `b7c42f5797f41f9281f850cbd9ff7af9256018f092135cc799d76c019d850f70`; SoulSandBlock.java lines 30-32, SHA-256 `da92572788c9416b511c003e50688d802e8248ccdb3e2d5eb155aa482549b095`.
- State producers/writers -> consumers/readers: support-shape provider -> FULL/CENTER/RIGID face test -> consumers such as wall/fence/ladder state updates -> collision shape selected by movement sweep.
- Parent slices / dependencies / closure evidence: A/B support dispatch and the three B overrides inspected; an exact-tree search of `net/minecraft/client` and `net/minecraft/world/entity` found no LocalPlayer, Player, Entity or LivingEntity consumer. The only client/entity source hits are ClientLevel drip-particle handling (A line 373, hash `4ed37f68a8c25ab5a780c7d646e2f1f4f375e39e4591e0a3115c8d637e500835`; B line 364, hash `96c80a10623d41f136fa70391b3920cf7f3ef15547923f7d772b78d356f16d93`) and non-player Evoker behavior (A line 193, hash `cbc97cb22075f24d503ebe448f6c84620c912958094a7ea42be319d3b3dac541`; B line 192, hash `e5657e73285709c937c557eb51305da6a9405709352487f9207677072a1a5729`).
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): A's default sturdy-face test excludes LEAVES then tests the collision face; B asks SupportType against getBlockSupportShape. B returns empty for Leaves, full layer shape for SnowLayer and Shapes.block for SoulSand. The exact source differences feed block placement/update logic; the client hit is drip-particle rendering and the entity hit is an Evoker. No fixed-state player movement consumer exists in the searched exact client/entity trees, and the lifecycle producer route is out of scope. These shape methods are retained as source evidence without a movement finding.
- Finding IDs or checked absence/replacement path: explicit out-of-scope support/lifecycle disposition; no player movement finding.

### Slice S5-LADDER-SHAPE: ladder facing-to-shape selection
- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards checked: LadderBlock.getShape selection by FACING; attachment and survival writers are excluded from this method-only slice.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/LadderBlock.java`, lines 34-46, SHA-256 `36014d99536a74a5761cd11cfa3c1ab6ac6aa6947609808852679dddb5625108`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/LadderBlock.java`, lines 35-47, SHA-256 `d7f65336fe5cd9432d5e10b85115e3bccbf1cf545bbccd29f31624b5848530c3`.
- State producers/writers -> consumers/readers: FACING -> corresponding 3-unit-deep full-height wall plane -> collision query and movement resolution.
- Parent slices / dependencies / closure evidence: S4-QUERY,S5-SHAPES; the paired methods use the same four directional shape constants and identical switch choices.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): with FACING supplied, both methods select identical NORTH/SOUTH/WEST/EAST collision planes. This is bounded to shape selection; the attachment predicate differs and remains open under the block state/neighbor inventory.
- Finding IDs or checked absence/replacement path: none within the bounded getShape method.

### Slice S5-FENCE-CONNECTION: fence neighbor-state producer (lifecycle scope)
- Inventory ID(s): INV-COLLISION, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards checked: the common-era fence-to-fence category term in FenceBlock.connectsTo, tag membership and block registrations; placement/neighbor writers are recorded only for lifecycle scope.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/FenceBlock.java`, lines 46-103, SHA-256 `81eda679e834659db079607b64a14d79284221b852f75aad43655ae7e0b96ab5`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/FenceBlock.java`, lines 53-114, SHA-256 `ded56e297e8711e382d485aed4cb34ab0e827bec9a033b1f6c16947592fa15f2`.
- State producers/writers -> consumers/readers: neighbor block/tag/material/sturdy-face checks -> NORTH/EAST/SOUTH/WEST fence state -> inherited CrossCollisionBlock collision shape -> CollisionGetter movement sweep.
- Parent slices / dependencies / closure evidence: S5-FENCE-FIXED-COLLISION,S5-SHAPES; exact predicate, common registrations and tag resources inspected.
- Resource evidence: `fences.json` is byte-identical in A/B (SHA-256 `8c4ebabbf6d088340c70c29091eadfd94d11cd551b193af52ea06cc073bc7483`). `wooden_fences.json` A SHA-256 `6c7f827ceb45ad6b6b7df14e8871908b2a17f904ec3c03cb3551ef48acdf188f`; B SHA-256 `b1844d51d7afa26334bb118c74c192f1315fc04f86be9ce0c6fa9656ccf74b3e`, with only crimson/warped entries added. The common six wood fences are tagged in both; nether brick is in FENCES but outside WOODEN_FENCES in both. A/B registrations give the common six Material.WOOD and nether brick Material.STONE.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): the common-era category tests agree for all seven shared vanilla fence blocks. B adds crimson/warped fence states that do not exist in A, and the placement/update callbacks only write neighbor-derived block states. Those modern-only and lifecycle producer changes are outside the fixed-state movement scope. Common fixed-state collision geometry is handled by S5-FENCE-FIXED-COLLISION.
- Finding IDs or checked absence/replacement path: explicit modern-state/lifecycle disposition; no movement finding.

### Slice S5-FENCE-FIXED-COLLISION: fence collision geometry for supplied states
- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards checked: FenceBlock construction and inherited CrossCollisionBlock.getCollisionShape after NORTH/EAST/SOUTH/WEST/WATERLOGGED inputs are supplied.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/FenceBlock.java`, lines 25-37, SHA-256 `81eda679e834659db079607b64a14d79284221b852f75aad43655ae7e0b96ab5`; CrossCollisionBlock.java lines 35-90, SHA-256 `5088eb166ca1ca36eb496ab120338d713c30bec84ff5815ec266a52ea1444ecd`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/FenceBlock.java`, lines 27-39, SHA-256 `ded56e297e8711e382d485aed4cb34ab0e827bec9a033b1f6c16947592fa15f2`; CrossCollisionBlock.java lines 36-95, SHA-256 `cebb3dcbbb94ab7513e11430fa4969bc1ff0a96c733298940889fc749b7ec1e4`.
- State producers/writers -> consumers/readers: four boolean connection flags -> the shared 16-entry collision-shape index -> CollisionGetter sweep; WATERLOGGED does not change the collision shape.
- Parent slices / dependencies / closure evidence: S4-QUERY,S5-CROSS-CACHE,S5-SHAPES; constructors and inherited shape/index methods compared.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both FenceBlock constructors pass the same geometry arguments and register the same four false side defaults. Both CrossCollisionBlock implementations build the same post/arm boxes and return the indexed collision shape. The B-only visual-shape override is outside collision behavior.
- Finding IDs or checked absence/replacement path: none within the bounded fixed-state collision provider.

### Slice S5-CROSS-CACHE: cross-collision shape index cache
- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards checked: CrossCollisionBlock.makeShapes, getAABBIndex and constructor cache priming.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/CrossCollisionBlock.java`, lines 35-122, SHA-256 `5088eb166ca1ca36eb496ab120338d713c30bec84ff5815ec266a52ea1444ecd`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/CrossCollisionBlock.java`, lines 36-127, SHA-256 `cebb3dcbbb94ab7513e11430fa4969bc1ff0a96c733298940889fc749b7ec1e4`.
- State producers/writers -> consumers/readers: NORTH/EAST/SOUTH/WEST booleans -> the same bitwise index and shape array; WATERLOGGED is omitted from index computation on both sides.
- Parent slices / dependencies / closure evidence: S5-FENCE-FIXED-COLLISION,S5-WALL-FIXED-COLLISION,S5-SHAPES; paired makeShapes/index bodies inspected.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the index mapping and generated arm/post sets match. B eagerly primes every possible state in the constructor; A computes the same index lazily. This changes cache timing only, not the index or collision geometry returned for a supplied state.
- Finding IDs or checked absence/replacement path: none within the bounded cache/index behavior.

### Slice S5-PISTON-SHAPE: moving source-piston collision geometry
- Inventory ID(s): INV-COLLISION, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards checked: MovingPistonBlock.getCollisionShape dispatch, PistonMovingBlockEntity.getCollisionShape source-piston branch, tick progress writer and PistonHeadBlock SHORT shape consumer.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java`, lines 280-307 and 333-361, SHA-256 `43ef9a249446cac093eb1f755bd55c4db5416474fa1668772f0f1257672e57a`; MovingPistonBlock.java lines 105-107, SHA-256 `7b767f2f15974a36734037b7342c00f609b13d85505ff818cde18620d733a79e`; PistonHeadBlock.java lines 42-98, SHA-256 `3db4ed7ed90f66b2534c4c19de8adb62416c9092091ae83a5d39bbdd5fe5165f`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java`, lines 278-307 and 335-363, SHA-256 `75d58043d5a9951f9b505a42b7656a5f01028633d971ba948167a9a95729a8b5`; MovingPistonBlock.java lines 96-98, SHA-256 `34f882c090cfec263f767e72f6104fc546d189688cfc4094f67022a6413e0b3b`; PistonHeadBlock.java lines 44-88, SHA-256 `9854f1d4728809cf9d89ac2632b0458eea12d1ea4488e36585922b1cadddb4bb`.
- State producers/writers -> consumers/readers: piston block-entity tick progresses by 0.5F -> `SHORT` threshold -> short/long piston-head shape -> moving-block collision dispatch -> player CollisionGetter sweep.
- Parent slices / dependencies / closure evidence: S4-QUERY,S5-SHAPES; exact source-piston retract branch, shape-property consumer and reachable progress value compared.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): at progress 0.5F during source-piston retraction, A's `< 4.0F` condition makes SHORT true and B's `< 0.25F` condition makes it false. The moving-block provider dispatches this shape to player block-collision queries outside the matching NOCLIP direction. The finding is bounded to this state and does not claim a trajectory.
- Finding IDs or checked absence/replacement path: F-S5-PISTON-HEAD-THRESHOLD.

### Slice S5-SCAFFOLDING-COLLISION: collision shape selection
- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: ScaffoldingBlock.getCollisionShape state/context decision only; DISTANCE/BOTTOM writer and block/tag registration not included.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/ScaffoldingBlock.java`, lines 118-126, SHA-256 `9a9e7a7e26e2b001a285e3944178b94b1ff149b500d3a0a5b9b719392662d84`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/ScaffoldingBlock.java`, lines 119-127, SHA-256 `3910d1c554a6937d61fbbf3027fc60dda429e5ae46311f965b65e218bf1d069c`.
- State producers/writers -> consumers/readers: DISTANCE, BOTTOM and CollisionContext.isAbove/isDescending -> STABLE_SHAPE, UNSTABLE_SHAPE_BOTTOM or empty shape; writer and neighbor route remain open under S5-SHAPES.
- Parent slices / dependencies / closure evidence: S5-SHAPES; exact paired method bodies compared, with the player collision-context call reachable in S4-QUERY.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the method bodies match: above-block and non-descending contexts use STABLE_SHAPE; otherwise nonzero distance plus BOTTOM and the lower-context test select UNSTABLE_SHAPE_BOTTOM; all other cases return empty. No state-writer, registration or trajectory claim follows from this method-only comparison.
- Finding IDs or checked absence/replacement path: none within this bounded method.

### Slice S5-WATER-CURRENT: water-flow velocity application
- Inventory ID(s): INV-WORLD-MOVEMENT, INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: per-tick water flow scan, velocity aggregation and application for Player.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/entity/Entity.java, checkAndHandleWater lines 2593-2652; Player.isPushedByWater lines 1846-1849; source hashes in artifact manifest.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/entity/Entity.java, updateFluidHeightAndDoFluidPushing lines 2641-2705 and water caller lines 965-979; Player.isPushedByFluid lines 1788-1791; source hashes in artifact manifest.
- State producers/writers -> consumers/readers: fluid flow/height and current velocity -> flow sum, scaling and player delta movement; baseTick/move check-fall path -> update call.
- Parent slices / dependencies / closure evidence: Entity baseTick, updateWaterState, Player push override, fluid scan inspected.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B adds a conditional minimum current vector for sufficiently slow non-flying players; vector scan and Player normalization exclusion correspond.
- Finding IDs or checked absence/replacement path: F-S5-WATER-CURRENT

### Slice S5-LAVA-CURRENT: lava-flow velocity application
- Inventory ID(s): INV-WORLD-MOVEMENT, INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: baseTick lava flow scan and player push gate.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/entity/Entity.java, baseTick lines 361-384 and updateWaterState lines 943-972; Entity source SHA-256 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/entity/Entity.java, baseTick call line 380 and updateInWaterStateAndDoFluidPushing lines 957-963; Player.isPushedByFluid lines 1788-1791; Entity/Player hashes in artifact manifest.
- State producers/writers -> consumers/readers: matching lava fluid state -> height/flow scan -> baseTick delta-velocity addition when Player is not flying.
- Parent slices / dependencies / closure evidence: A checkAndHandleWater callers and B baseTick/current scan/push gate inspected.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B adds the lava-tag scan and dimension-scaled current push; A's base tick current path scans water only.
- Finding IDs or checked absence/replacement path: F-S5-LAVA-CURRENT

## Dependency queue and blockers

- S1-ESCAPE-QUERY: pair A blocked/freeAt/isSuffocating with B noBlockCollision/getBlockCollisions and relevant shapes; establish same-state reachability. Owner: discovery author.
- S4-MOVE/S4-QUERY: close callback/provider edges and remaining entity-candidate/query-provider routes; the bounded lazy border omission for epsilon-near player support queries is recorded in F-S4-BORDER-EPSILON. Owner: discovery author.
- S5-SHAPES: compare common-block provider methods, block/tag registrations and relevant resources; retain bounded cache/state rows independently from pair closure. S5-SCAFFOLDING-COLLISION, S5-SLAB-SHAPE, S5-SNOW-COLLISION, S5-LADDER-SHAPE, S5-CACTUS-COLLISION, S5-BAMBOO-COLLISION, S5-BAMBOO-OFFSET, S5-HONEY-COLLISION, S5-COMPOSTER-COLLISION, S5-SOUL-SAND-COLLISION, S5-FENCE-GATE-COLLISION, S5-VINE-SHAPE, S5-BELL-COLLISION, S5-LECTERN-COLLISION, S5-GRINDSTONE-COLLISION, S5-WALL-FIXED-COLLISION, S5-FENCE-FIXED-COLLISION and S5-CROSS-CACHE close only their named provider/cache behaviors. S5-WALL and S5-FENCE-CONNECTION are out-of-scope lifecycle producers; S5-SUPPORT-SHAPES is out-of-scope support/lifecycle behavior after exact player-consumer search. Registry/resource/provider callback chains remain open; ladder attachment and piston push/tick paths remain open. Owner: discovery author.
- Remaining stage 2-7 slices: continue pose/dimensions and state writers/readers after S2-LOCAL-CROUCHING-STATE; add movement attributes/effects/enchantments/equipment/resources, block/fluid registrations/callbacks, and client external inputs. Owner: discovery author.
- Independent source reviewer: assign someone who did not author discovery; finding-snapshot decisions and eventual full-pair audit remain pending. Owner: coordinator.

## Finding index
- F-S1-SPRINT-RESET — held shift cancels pending double-tap sprint window (source-confirmed).
- F-S1-WATER-DESCENT — flying players skip crouch descent in water (source-confirmed).
- F-S2-EDGE — edge restraint applies during supported near-ground motion and excludes flying players (source-confirmed).
- F-S3-SHALLOW-LAVA-TRAVEL — shallow lava uses a different vertical scale and falling adjustment (source-confirmed).
- F-S3-FLUID-JUMP-GATE — flying players skip liquid jump processing (source-confirmed).
- F-S3-SHALLOW-LAVA-JUMP — grounded shallow-lava jump selects the ground-jump helper (source-confirmed).
- F-S5-WATER-CURRENT — weak water currents receive a minimum player push (source-confirmed).
- F-S5-LAVA-CURRENT — non-flying players receive lava-current force (source-confirmed).
- F-S5-PISTON-HEAD-THRESHOLD — retracting source-piston intermediate progress selects a different short-head collision shape (source-confirmed).
- F-S4-BORDER-EPSILON — epsilon-near entity collision queries omit the border candidate in B (submitted; independent review pending).

The historical pair report is not imported as source confirmation.

## Resume checkpoint

- Last completed slices: S1-INPUT-VECTOR,S1-KEYBOARD,S1-LOCAL-TICK,S1-ELYTRA,S1-ESCAPE,S3-WATER,S3-FALL-FLYING,S3-RELATIVE-MOVEMENT,S3-CLIMB-COMMON,S4-STEP,S5-SLAB-SHAPE,S5-SNOW-COLLISION,S5-LADDER-SHAPE,S5-PISTON-SHAPE,S5-SCAFFOLDING-COLLISION,S5-WALL-FIXED-COLLISION,S5-FENCE-FIXED-COLLISION,S5-CROSS-CACHE,S5-CACTUS-COLLISION,S5-BAMBOO-COLLISION,S5-BAMBOO-OFFSET,S5-HONEY-COLLISION,S5-COMPOSTER-COLLISION,S5-SOUL-SAND-COLLISION,S5-FENCE-GATE-COLLISION,S5-VINE-SHAPE,S5-BELL-COLLISION,S5-LECTERN-COLLISION,S5-GRINDSTONE-COLLISION; finding slices F-S1-SPRINT-RESET,F-S1-WATER-DESCENT,F-S1-OPEN-SHULKER-ESCAPE,F-S2-EDGE,F-S3-SHALLOW-LAVA-TRAVEL,F-S3-FLUID-JUMP-GATE,F-S3-SHALLOW-LAVA-JUMP,F-S4-BORDER-EPSILON,F-S5-WATER-CURRENT,F-S5-LAVA-CURRENT,F-S5-PISTON-HEAD-THRESHOLD.
- Active slices: S1-LOCAL-AISTEP,S2-LOCAL-CROUCHING-STATE,S3-GROUND-AIR,S4-MOVE,S4-QUERY,S5-SHAPES; S4-BORDER-GUARD is findings, S5-WALL and S5-FENCE-CONNECTION are lifecycle not-applicable, and S5-SUPPORT-SHAPES is support/lifecycle not-applicable. Required pair inventories remain partial.
- Next: close the cached-crouching writer's same-tick pose/input reader chain, then continue common-block registration/resource routes and remaining S1/S3, state/pose, modifier, fluid/callback and external-input inventories.
- Outstanding dependencies: S1 escape-query shape providers; S2 crouching pose/input readers and remaining dimensions/state; S4 callback/entity-collider enumeration and other query providers; S5 remaining state/neighbor writers, registrations/resources, ladder attachment and piston push/tick paths; remaining stage 2-7 slices; independent reviewer assignment.
- Resume branch: feat/source-discovery-1-15-2-1-16-5-resume. Main commit d4c4f154a0c2487dd6dd7d20d92eb57b3d8ab1ae has been merged; the merge checkpoint and current tip are recorded by git log -1.
- First next work: finish remaining exact common-block provider helpers and their registration/resource/callback dependencies; then continue S1 tick/call-order, S3 helper/resource closure and remaining stage 2-7 rows. Keep pair PARTIAL and findings limited to independently supported source claims.
- Read-only resume commands from the repository root:
  - `git status --short; git log -1 --oneline`
  - `$A='D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.15.2\mojmap'; $B='D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.16.5\mojmap'`
  - `Get-Content -LiteralPath "$A\net\minecraft\world\entity\LivingEntity.java"`; repeat for the paired `$B` file and each named slice dependency.

## Finding snapshots (not pair freeze)

Each finding is committed as an immutable source snapshot. F-S1-OPEN-SHULKER-ESCAPE has separate blind-source acceptance and finding-only handoff eligibility recorded below; the other ten snapshots remain submitted and await independent review. Pair-wide implementation/source reconciliation remains deferred until the full-pair source freeze.

### Snapshot event F-S1-SPRINT-RESET
- Finding ID(s): F-S1-SPRINT-RESET
- Source finding author(s): source-only pair researcher
- Status: submitted
- Immutable snapshot commit: ce9369e
- Finding file path and SHA-256: findings/F-S1-SPRINT-RESET.md — 7546416c26179fd4b4128fa3042280f8f24622b237299b6eeabc5010ef035cff
- Exact A/B artifact-manifest identities/hashes: ready/1.15.2/artifacts.sha256 208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406; ready/1.16.5/artifacts.sha256 f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c
- Cited source hashes: A LocalPlayer 3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd; B LocalPlayer 6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b
- Verified implementation boundary/evidence, or unresolved boundary reason: not inspected; source-only owner remains blind until full-pair freeze.
- Finding-specific closed dependency IDs/evidence: S1-SPRINT-RESET timer writer/decrement/read paths and aiStep input order closed from paired LocalPlayer bodies; food/mayfly values are direct predicates only.
- Independent blind source reviewer and decision date: pending coordinator assignment
- Review basis / requested source-only revisions: pending
- Pair run status and commit at handoff: active at ce9369e
- Pair complete: no
- Implementation handoff: blocked; independent snapshot acceptance pending.
- Replaces/supersedes snapshot ID and reason, if applicable: none

### Snapshot event F-S1-WATER-DESCENT
- Finding ID(s): F-S1-WATER-DESCENT
- Source finding author(s): source-only pair researcher
- Status: submitted
- Immutable snapshot commit: ce9369e
- Finding file path and SHA-256: findings/F-S1-WATER-DESCENT.md — 5a74ac4924dc847e77a8edfeea771b31c93a2878a015627786f0171bd7b60226
- Exact A/B artifact-manifest identities/hashes: ready/1.15.2/artifacts.sha256 208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406; ready/1.16.5/artifacts.sha256 f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c
- Cited source hashes: A LocalPlayer 3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd and LivingEntity 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54; B LocalPlayer 6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b, Player d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960 and LivingEntity b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88
- Verified implementation boundary/evidence, or unresolved boundary reason: not inspected; source-only owner remains blind until full-pair freeze.
- Finding-specific closed dependency IDs/evidence: S1-WATER-DESCENT Player.isAffectedByFluids gate and goDownInWater helper compared on both endpoints.
- Independent blind source reviewer and decision date: pending coordinator assignment
- Review basis / requested source-only revisions: pending
- Pair run status and commit at handoff: active at ce9369e
- Pair complete: no
- Implementation handoff: blocked; independent snapshot acceptance pending.
- Replaces/supersedes snapshot ID and reason, if applicable: none

### Snapshot event F-S2-EDGE
- Finding ID(s): F-S2-EDGE
- Source finding author(s): source-only pair researcher
- Status: submitted
- Immutable snapshot commit: ce9369e
- Finding file path and SHA-256: findings/F-S2-EDGE.md — 7382329a99db906b15a366a6ee2b6567aa50924eb7fb15e0b07d771d5aa02a3b
- Exact A/B artifact-manifest identities/hashes: ready/1.15.2/artifacts.sha256 208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406; ready/1.16.5/artifacts.sha256 f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c
- Cited source hashes: A Player 1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793; B Player d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960
- Verified implementation boundary/evidence, or unresolved boundary reason: not inspected; source-only owner remains blind until full-pair freeze.
- Finding-specific closed dependency IDs/evidence: S2-EDGE guards, inherited shift predicate, isAboveGround helper and noCollision semantics inspected in Player/CollisionGetter sources.
- Independent blind source reviewer and decision date: pending coordinator assignment
- Review basis / requested source-only revisions: pending
- Pair run status and commit at handoff: active at ce9369e
- Pair complete: no
- Implementation handoff: blocked; independent snapshot acceptance pending.
- Replaces/supersedes snapshot ID and reason, if applicable: none

### Snapshot event F-S3-SHALLOW-LAVA-TRAVEL
- Finding ID(s): F-S3-SHALLOW-LAVA-TRAVEL
- Source finding author(s): source-only pair researcher
- Status: submitted
- Immutable snapshot commit: f8fceba272e880b0afbd4b497b50624d019e309
- Finding file path and SHA-256: findings/F-S3-SHALLOW-LAVA-TRAVEL.md — 5aba31638bf743d19fb68dcf9f1b5441ca1d971dba32a1f72af40b688f2b6dc9
- Exact A/B artifact-manifest identities/hashes: ready/1.15.2/artifacts.sha256 208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406; ready/1.16.5/artifacts.sha256 f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c
- Cited source hashes: A LivingEntity 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54; B LivingEntity b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88 and Entity f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666
- Verified implementation boundary/evidence, or unresolved boundary reason: not inspected; source-only owner remains blind until full-pair freeze.
- Finding-specific closed dependency IDs/evidence: S3-LAVA dispatch, affected-fluid/stand-on-fluid gates, lava fluid-height writer/accessor and eye-height threshold inspected.
- Independent blind source reviewer and decision date: pending coordinator assignment
- Review basis / requested source-only revisions: pending
- Pair run status and commit at handoff: partial at f8fceba272e880b0afbd4b497b50624d019e309
- Pair complete: no
- Implementation handoff: blocked; independent snapshot acceptance pending.
- Replaces/supersedes snapshot ID and reason, if applicable: none

### Snapshot event F-S3-FLUID-JUMP-GATE
- Finding ID(s): F-S3-FLUID-JUMP-GATE
- Source finding author(s): source-only pair researcher
- Status: submitted
- Immutable snapshot commit: f8fceba272e880b0afbd4b497b50624d019e309
- Finding file path and SHA-256: findings/F-S3-FLUID-JUMP-GATE.md — 08eca5bb4f32db47de8012fe69c9be9648153f87cd8864494d8f76c3c606e63b
- Exact A/B artifact-manifest identities/hashes: ready/1.15.2/artifacts.sha256 208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406; ready/1.16.5/artifacts.sha256 f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c
- Cited source hashes: A LivingEntity 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54; B LivingEntity b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88 and Player d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960
- Verified implementation boundary/evidence, or unresolved boundary reason: not inspected; source-only owner remains blind until full-pair freeze.
- Finding-specific closed dependency IDs/evidence: S3-JUMP outer gate, B Player override and default fluid gate traced; A liquid-height/lava branches traced.
- Independent blind source reviewer and decision date: pending coordinator assignment
- Review basis / requested source-only revisions: pending
- Pair run status and commit at handoff: partial at f8fceba272e880b0afbd4b497b50624d019e309
- Pair complete: no
- Implementation handoff: blocked; independent snapshot acceptance pending.
- Replaces/supersedes snapshot ID and reason, if applicable: none

### Snapshot event F-S3-SHALLOW-LAVA-JUMP
- Finding ID(s): F-S3-SHALLOW-LAVA-JUMP
- Source finding author(s): source-only pair researcher
- Status: submitted
- Immutable snapshot commit: f8fceba272e880b0afbd4b497b50624d019e309
- Finding file path and SHA-256: findings/F-S3-SHALLOW-LAVA-JUMP.md — b3a383137d1155a71816eea1f0efc203d9849ab652620fe21a09d8613689217e
- Exact A/B artifact-manifest identities/hashes: ready/1.15.2/artifacts.sha256 208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406; ready/1.16.5/artifacts.sha256 f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c
- Cited source hashes: A LivingEntity 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54; B LivingEntity b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88 and Entity f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666
- Verified implementation boundary/evidence, or unresolved boundary reason: not inspected; source-only owner remains blind until full-pair freeze.
- Finding-specific closed dependency IDs/evidence: S3-JUMP nested lava conditions, lava height writer/accessor and eye-height threshold traced; exact jump helper implementations remain outside this call-selection finding.
- Independent blind source reviewer and decision date: pending coordinator assignment
- Review basis / requested source-only revisions: pending
- Pair run status and commit at handoff: partial at f8fceba272e880b0afbd4b497b50624d019e309
- Pair complete: no
- Implementation handoff: blocked; independent snapshot acceptance pending.
- Replaces/supersedes snapshot ID and reason, if applicable: none

### Snapshot event F-S5-WATER-CURRENT
- Finding ID(s): F-S5-WATER-CURRENT
- Source finding author(s): source-only pair researcher
- Status: submitted
- Immutable snapshot commit: f8fceba272e880b0afbd4b497b50624d019e309
- Finding file path and SHA-256: findings/F-S5-WATER-CURRENT.md — 1bfa35236cfcdbc66218b7240b5d498d6b2a34edf4d9a13d9780cb389f7ffe9a
- Exact A/B artifact-manifest identities/hashes: ready/1.15.2/artifacts.sha256 208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406; ready/1.16.5/artifacts.sha256 f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c
- Cited source hashes: A Entity 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e and Player 1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793; B Entity f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666 and Player d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960
- Verified implementation boundary/evidence, or unresolved boundary reason: not inspected; source-only owner remains blind until full-pair freeze.
- Finding-specific closed dependency IDs/evidence: A water-only call path, B water scale caller, Player push overrides and weak-current minimum conditions inspected.
- Independent blind source reviewer and decision date: pending coordinator assignment
- Review basis / requested source-only revisions: pending
- Pair run status and commit at handoff: partial at f8fceba272e880b0afbd4b497b50624d019e309
- Pair complete: no
- Implementation handoff: blocked; independent snapshot acceptance pending.
- Replaces/supersedes snapshot ID and reason, if applicable: none

### Snapshot event F-S5-LAVA-CURRENT
- Finding ID(s): F-S5-LAVA-CURRENT
- Source finding author(s): source-only pair researcher
- Status: submitted
- Immutable snapshot commit: f8fceba272e880b0afbd4b497b50624d019e309
- Finding file path and SHA-256: findings/F-S5-LAVA-CURRENT.md — dcd03f046ab14548f1d6f7049396edc30d399b1aa94e7c73e09459d0b5bcdad4
- Exact A/B artifact-manifest identities/hashes: ready/1.15.2/artifacts.sha256 208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406; ready/1.16.5/artifacts.sha256 f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c
- Cited source hashes: A Entity 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e and Player 1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793; B Entity f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666 and Player d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960
- Verified implementation boundary/evidence, or unresolved boundary reason: not inspected; source-only owner remains blind until full-pair freeze.
- Finding-specific closed dependency IDs/evidence: A water-only base-tick scan and B base-tick lava scan plus Player fluid-push gate inspected.
- Independent blind source reviewer and decision date: pending coordinator assignment
- Review basis / requested source-only revisions: pending
- Pair run status and commit at handoff: partial at f8fceba272e880b0afbd4b497b50624d019e309
- Pair complete: no
- Implementation handoff: blocked; independent snapshot acceptance pending.
- Replaces/supersedes snapshot ID and reason, if applicable: none

### Snapshot event F-S1-OPEN-SHULKER-ESCAPE
- Finding ID(s): F-S1-OPEN-SHULKER-ESCAPE
- Source finding author(s): source-only pair researcher
- Status: accepted (incremental finding-only eligibility)
- Immutable snapshot commit: d859478b9f6de63ba4a121a1f31d6dc90ad4f51e
- Finding file path and SHA-256: findings/F-S1-OPEN-SHULKER-ESCAPE.md — 11860ba106197cae687735e641db4c9ef7057ddadcddeafe5c16f012e8e657e4
- Exact A/B artifact-manifest identities/hashes: ready/1.15.2/artifacts.sha256 208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406; ready/1.16.5/artifacts.sha256 f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c
- Cited source hashes: A LocalPlayer 3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd, Player 1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793 and ShulkerBoxBlock de5e19cffb75499b509bc361fcfb3dbe0b0fa7ab490ac42adedf89466721b02d; B LocalPlayer 6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b, Blocks 3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12, ShulkerBoxBlockEntity 4aab41f71aacf9e1138a225e1253e202a303276ff21c7b4974b11a76d694c322, CollisionGetter b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac and CollisionSpliterator 19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297
- Verified implementation boundary/evidence, or unresolved boundary reason: not inspected; source-only owner remains blind until full-pair freeze.
- Finding-specific closed dependency IDs/evidence: S1-ESCAPE-QUERY; paired escape call sites, A/B suffocation semantics, open shulker predicate/shape query, common piston cases, and the B open-state producer-to-client-consumer route are source-verified.
- Independent blind source reviewer and decision date: reviewer for feat/coverage-review-middle; 2026-10-07; decision record at workflows/coverage-review-2026-10-07/middle.md, commit 0b494993e7b9b40b5526c80e36c99407e7e405f3, file SHA-256 5437414a3b24a5722aba2207d245977a55aadc02e419da36793063e39801cd18
- Reviewer-closed open-state producer route: B ShulkerBoxBlock#newBlockEntity/use -> ShulkerBoxBlockEntity#createMenu -> ShulkerBoxMenu#startOpen -> ShulkerBoxBlockEntity#startOpen writes OPENING via block event -> ServerLevel/ClientPacketListener/Level/BaseEntityBlock dispatches the update to the client. Manifest-matched B source hashes: ShulkerBoxBlock a88a18bc4c4299876053f9ec38987861fa81acf3a38289a49cc3dbee04b940d8; ShulkerBoxMenu d8061c03c502c413812f3fa22db4eebafd4f548271768e254c771a52f62658a6; ShulkerBoxBlockEntity 4aab41f71aacf9e1138a225e1253e202a303276ff21c7b4974b11a76d694c322; ServerPlayer 9fad233370c04ab17f973bc12476466d824e3c1ba59a400542b073380bf550c2; ServerLevel c3d3ccc873efb17048613f1b465401b139f05955cc2b1e1d1bdd99d0744d629f; ClientPacketListener a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e; Level e9a23488863fd43a7d8975efc23f4c7cf4af874554bab5b76eaa2c79f028db80; BaseEntityBlock 211813ad33e63b550e77af30ed25cf68375521cdd8b550b5fd91035d93f9883f.
- Accepted claim limit: only the sampled-column/clear-neighbor escape-branch comparison; no final displacement or trajectory is claimed. First changed release remains unknown in (1.15.2, 1.16.5]; runtime validation was not performed.
- Review basis / requested source-only revisions: independent rewalk of the immutable finding, all eight cited source hashes, and the reachable open-state writer/update route; accepted with no source-only revisions requested. Review inspected no implementation or wiki-audit material.
- Pair status at snapshot submission: partial at d859478b9f6de63ba4a121a1f31d6dc90ad4f51e; pair status remains partial with full-pair coverage open.
- Pair complete: no
- Implementation handoff: eligible for a separate finding-only handoff limited to the accepted source-level branch claim; no handoff was sent by the reviewer.
- Replaces/supersedes snapshot ID and reason, if applicable: none

### Snapshot event F-S5-PISTON-HEAD-THRESHOLD
- Finding ID(s): F-S5-PISTON-HEAD-THRESHOLD
- Source finding author(s): source-only pair researcher
- Status: submitted; independent blind review pending
- Immutable snapshot commit: 58e56ee59d7cc4015d499e3e2a37ec1d45049d56
- Finding file path and SHA-256: findings/F-S5-PISTON-HEAD-THRESHOLD.md — 58cd05981150b0c162ecd10ea16560e7cfbb50cdf5027cb57577cf32a4c30163
- Git blob: `54def810126026ecb51f389c346b564f4b7503d3`
- Exact A/B artifact-manifest identities/hashes: ready/1.15.2/artifacts.sha256 208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406; ready/1.16.5/artifacts.sha256 f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c
- Cited source hashes: A PistonMovingBlockEntity 43ef9a249446cac093eb1f755bd55c4db5416474fa1668772f0f1257672e57a, MovingPistonBlock 7b767f2f15974a36734037b7342c00f609b13d85505ff818cde18620d733a79e, PistonHeadBlock 3db4ed7ed90f66b2534c4c19de8adb62416c9092091ae83a5d39bbdd5fe5165f; B PistonMovingBlockEntity 75d58043d5a9951f9b505a42b7656a5f01028633d971ba948167a9a95729a8b5, MovingPistonBlock 34f882c090cfec263f767e72f6104fc546d189688cfc4094f67022a6413e0b3b, PistonHeadBlock 9854f1d4728809cf9d89ac2632b0458eea12d1ea4488e36585922b1cadddb4bb
- Verified implementation boundary/evidence, or unresolved boundary reason: implementation not inspected; source-only owner remains blind until full-pair freeze.
- Finding-specific closed dependency IDs/evidence: S4-QUERY,S5-PISTON-SHAPE; moving-block collision dispatch, piston-head SHORT consumer, and 0.5F progress writer verified in the exact endpoint sources.
- Independent blind source reviewer and decision date: pending.
- Accepted claim limit: short/long collision-shape selection differs for a retracting source piston at progress 0.5F outside the matching NOCLIP direction; no displacement or trajectory is claimed. First changed release remains unknown in (1.15.2, 1.16.5]; runtime validation was not performed.
- Pair status at snapshot submission: partial at 58e56ee59d7cc4015d499e3e2a37ec1d45049d56; pair status remains partial with full-pair coverage open.
- Pair complete: no
- Implementation handoff: pending independent snapshot review; no handoff was sent.
- Replaces/supersedes snapshot ID and reason, if applicable: none

### Snapshot event F-S4-BORDER-EPSILON
- Finding ID(s): F-S4-BORDER-EPSILON
- Source finding author(s): source-only pair researcher
- Status: submitted; independent blind review pending
- Immutable snapshot commit: 24be762ab611a5ac64f76f3f2fbfd5d6d09e97c3
- Finding file path and SHA-256: findings/F-S4-BORDER-EPSILON.md — 893a748783cb23d0613867cd92c9c065aa066c896eb1929df07d1b66361d8767
- Git blob: `f78f781cf04fe1ac14bb9ebb2df9edb412266398`
- Exact A/B artifact-manifest identities/hashes: ready/1.15.2/artifacts.sha256 208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406; ready/1.16.5/artifacts.sha256 f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c
- Cited source hashes: A CollisionGetter `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd`, WorldBorder `5030e47d299d2741339dae0274dab41e63ae1eb1468171ef14f181a71a03086f`, Player `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`; B CollisionGetter `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac`, CollisionSpliterator `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297`, WorldBorder `2925df8d985d0ec2d075d4f5c57538dcc628528b72a1d35e90af27fe4fbadb2a`, Player `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`.
- Verified implementation boundary/evidence, or unresolved boundary reason: implementation not inspected; source-only owner remains blind until full-pair freeze.
- Finding-specific closed dependency IDs/evidence: S4-BORDER-GUARD,S4-QUERY,S2-EDGE; A/B lazy border predicates, matching integer collision bounds, direct `noCollision` reduction and Player support-probe callers are compared. No final movement trajectory is claimed.
- Independent blind source reviewer and decision date: pending coordinator assignment.
- Review basis / requested source-only revisions: pending.
- Pair status at snapshot submission: partial at 24be762ab611a5ac64f76f3f2fbfd5d6d09e97c3; pair status remains partial with full-pair coverage open.
- Pair complete: no
- Implementation handoff: pending independent snapshot review; no handoff was sent.
- Replaces/supersedes snapshot ID and reason, if applicable: none

## Implementation reconciliation

- Reconciliation status: pending
- Repository revision inspected: none; implementation not opened.
- Finding -> implementation disposition/evidence: pending source-only freeze.
- Existing implementation without a frozen source finding: pending.
- Coverage gaps routed back to discovery slices: pending.

## Independent source audit

- Reviewer: pending assignment; must differ from discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: pending
- Concrete missed-slice routes (or none found): pending
- Misses routed to slice/finding IDs and owners: pending
- Reviewer evidence / date: pending

## Source audit closure

- Coverage counts by status at this checkpoint: pending 0; in-progress 6; compared-no-difference 27; findings 10; not-applicable 3; required stage slices not yet entered and open.
- Required inventory status and evidence: all seven pending.
- Open dependencies: S1 escape-query provider coverage and CLIMBABLE resource consumers; S4 callback/entity-collider filters and remaining query providers; S5 shape registration/resource/neighbor routes; remaining required stage 2-7 inventory slices; independent source reviewer assignment.
- Unresolved gaps and limits: exhaustive source comparison is incomplete.
- Evidence/hash/correspondence audit: Ready/source/artifact manifest hashes verified; cited block collision providers, border query/collision bounds, and paired LocalPlayer/Player/LivingEntity sources checked against the exact published Mojmap files; exact provenance sidecars verified, mappingArtifacts=null, success logs are summary-only; the full provider method/resource index remains pending.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed.
