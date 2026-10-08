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
- INV-STATE movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags and timers: status=pending; slice_ids=S1-LOCAL-AISTEP,S2-POSE,S2-STATE,S2-GATES,S4-STATE; evidence=paired LocalPlayer, Player, LivingEntity, Entity
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
- Parent slices / dependencies / closure evidence: S1-LOCAL-AISTEP,S7-CORRECTIONS,S7-INPUT-PACKETS; exact remaining sendPosition tail pending.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): tick call and packet branches correspond in the inspected ranges; full sendPosition tail and movement-reporting correspondence remain open.
- Finding IDs or checked absence/replacement path: pending

### Slice S1-LOCAL-AISTEP: local movement inputs and gates
- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer.aiStep(), prior-state capture through super.aiStep(), exact order.
- A evidence: LocalPlayer#aiStep, lines 625-783, source hash in A manifest.
- B evidence: LocalPlayer#aiStep, lines 627-791, source hash in B manifest.
- State producers/writers -> consumers/readers: input, sprintTriggerTime, crouching, abilities.flying, fluid gates, fall-flying and super.aiStep; closure pending.
- Parent slices / dependencies / closure evidence: S1-ESCAPE,S1-SPRINT-RESET,S1-ELYTRA,S1-WATER-DESCENT,S2-STATE,S3-TRAVEL.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): candidate changes identified in escape search, shift-reset of sprint trigger, elytra climbability gate and fluid descent gate. Each is checked separately before conclusion.
- Finding IDs or checked absence/replacement path: pending

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
- Finding IDs or checked absence/replacement path: F-S2-EDGE

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
- Parent slices / dependencies / closure evidence: S1-ELYTRA,S4-MOVE,S5-SHAPES,S3-ATTRS,S3-CLIMB; helper-equivalence and climbable tag correspondence pending.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B extracts ordinary relative-friction and climb handling and changes ladder terminology; full player-specific equality requires helper and tag closure.
- Finding IDs or checked absence/replacement path: pending

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
- Parent slices / dependencies / closure evidence: S2-EDGE,S2-POSE,S4-MOVE,S4-STEP,S5-SHAPES. Cursor bounds and entity candidate filters were compared. The current main snapshot has no standalone border-query finding; the border guard remains an open source route pending a fresh bounded finding disposition.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B's CollisionSpliterator cursor and shape-intersection route and the Player entity-collider candidates were compared at the cited boundaries. Border behavior and complete registered/provider extent remain open; no player trajectory or border finding is claimed in this row.
- Finding IDs or checked absence/replacement path: pending border-route re-evaluation.

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
- Exact behavior boundary and enclosing guards/order checked: CollisionGetter.getBlockCollisions -> BlockState.getCollisionShape(..., CollisionContext) -> block getCollisionShape. The base implementation then calls BlockState.getShape(BlockGetter,BlockPos), selecting the empty-context overload; direct getCollisionShape overrides receive the supplied CollisionContext. A's support/sturdiness path derives from collision shapes; B has a separate getBlockSupportShape dispatch and provider overrides. Neighbor-driven state updates, provider-specific bodies and registry/resource reachability remain open.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/CollisionGetter.java`, getBlockCollisions lines 67-109, SHA-256 `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd`; `net/minecraft/world/level/block/state/BlockState.java`, getShape/getCollisionShape lines 184-199 and isFaceSturdy lines 329-330, SHA-256 `77320fd1e50e58d7a3ed593ccbcf21853c97d125fcebd2e0e305dc47524f21`; `net/minecraft/world/level/block/Block.java`, getShape/getCollisionShape lines 386-393, SHA-256 `a5819a4c676d2b7e08cce7f80ae80a13726dd17b15e0b29efdce7e37efa2bf0d`.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/CollisionGetter.java`, getBlockCollisions/getCollisions lines 58-70, SHA-256 `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac`; `net/minecraft/world/level/block/state/BlockBehaviour.java`, BlockStateBase shape forwarding lines 516-533 and block getShape/getCollisionShape/getBlockSupportShape lines 227-282, SHA-256 `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b`.
- State producers/writers -> consumers/readers: neighboring block states and block-specific state properties -> collision-context-sensitive provider -> collision stream and movement resolution; entity, position and AABB -> CollisionContext/query bounds. B support-shape consumers include floor-height and support predicates; corresponding A/B support-provider paths remain open.
- Parent slices / dependencies / closure evidence: S1-ESCAPE,S2-EDGE,S2-POSE,S4-MOVE,S4-QUERY,S4-STEP,S5-REGISTRY,S5-CONTACT. A and B each have 17 Java files in the block tree with at least one declared getCollisionShape method; B adds LiquidBlock, A SoulsandBlock is SoulSandBlock in B, and the common base/state dispatch moves from Block/BlockState to BlockBehaviour/BlockStateBase. B additionally declares getBlockSupportShape in LeavesBlock, SnowLayerBlock and SoulSandBlock as overrides of its base; provider bodies, common-block registrations, neighbor writers and shape-cache effects remain unreviewed.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): the compared base route preserves `hasCollision ? state.getShape(blockGetter, blockPos) : Shapes.empty()`; the base fallback uses the empty-context state overload in both versions. This is not evidence that common block shapes are equal or that the provider inventory is complete. Modern-only B blocks remain excluded from A-version claims.
- Finding IDs or checked absence/replacement path: no result from the dispatch-only comparison; pose-clearance shape dependency remains under S2-POSE.

### Slice S5-WALL: wall collision geometry and connection-state producers
- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards checked: WallBlock.getShape/getCollisionShape dispatch and the wall connection/state update path; player reachability is through S4-QUERY's block collision request.
- A evidence: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/world/level/block/WallBlock.java`, lines 21-128, SHA-256 `4ad01bfd8d84b77abe161289294667e9f482cf9477cd81f725162aa9dc891850`; inherited array/index helpers are in CrossCollisionBlock and remain a dependency.
- B evidence: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/world/level/block/WallBlock.java`, lines 30-258, SHA-256 `aa770704573ee65ca0d0b102bc55e3ece81f7b25aef452bb5c89ec0350a23442`.
- State producers/writers -> consumers/readers: horizontal neighbor connection predicates, sturdy-face queries, wall-above support-shape query and wall state properties -> state-keyed visual/collision shapes -> CollisionGetter sweep.
- Parent slices / dependencies / closure evidence: S4-QUERY,S5-SHAPES; exact dispatch methods and source ranges inspected, but full shape construction, connection predicates and neighbor/state writers are not yet compared.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): A selects array-backed shapes using `getAABBIndex` and an UP guard; B looks up state-keyed shape maps. Both are reachable for player movement collision, but this dispatch-level structural difference does not establish a geometric or trajectory difference. Connection predicates and wall-above support checks differ in structure and require state/neighbor/resource closure before disposition.
- Finding IDs or checked absence/replacement path: none; no behavior claim pending full paired state and geometry comparison.

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
- S4-MOVE/S4-QUERY: close the callback/provider edges and re-evaluate the B border guard after the newer main snapshot removed the prior standalone border finding. Owner: discovery author.
- S5-SHAPES: compare common-block provider methods, B support-shape overrides, shape caches, neighboring-state writers, block/tag registrations and relevant resources. S5-SCAFFOLDING-COLLISION and S5-SLAB-SHAPE close bounded provider methods; S5-WALL is active with full geometry/state comparison open, including A's inherited CrossCollisionBlock shape/index helpers. Owner: discovery author.
- Remaining stage 2-7 slices: add bounded rows for state/pose/dimensions, movement attributes/effects/enchantments/equipment/resources, block/fluid registrations/callbacks, and client external inputs. Owner: discovery author.
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

The historical pair report is not imported as source confirmation.

## Resume checkpoint

- Last completed slices: S1-INPUT-VECTOR,S1-KEYBOARD,S1-ELYTRA,S1-ESCAPE,S3-WATER,S3-FALL-FLYING,S4-STEP,S5-SCAFFOLDING-COLLISION; finding slices F-S1-SPRINT-RESET,F-S1-WATER-DESCENT,F-S1-OPEN-SHULKER-ESCAPE,F-S2-EDGE,F-S3-SHALLOW-LAVA-TRAVEL,F-S3-FLUID-JUMP-GATE,F-S3-SHALLOW-LAVA-JUMP,F-S5-WATER-CURRENT,F-S5-LAVA-CURRENT.
- Active slices: S1-LOCAL-TICK,S1-LOCAL-AISTEP,S3-GROUND-AIR,S4-MOVE,S4-QUERY,S5-SHAPES,S5-WALL; all required producer/consumer inventories remain partial.
- Next: compare high-reachability common-block shapes and their neighbor/state/registration routes; then continue pose/dimensions, modifiers/resources, fluid/callback and external-input inventories.
- Outstanding dependencies: S1 escape-query shape providers; S4 callback/entity-collider and border-guard disposition; S5 common-block providers, support shapes, registrations and neighboring state writers; remaining stage 2-7 slices; independent reviewer assignment.
- Resume branch: feat/source-discovery-1-15-2-1-16-5-resume. Main commit d4c4f154a0c2487dd6dd7d20d92eb57b3d8ab1ae has been merged; the merge checkpoint and current tip are recorded by git log -1.
- First next work: inventory exact common-block shape providers and compare the fence/wall, slab/stair, ladder/vine, moving-piston and scaffolding state/neighbor routes. Continue S1 tick/call-order and S3 helper/resource closure, then fill remaining stage 2-7 rows. Keep pair PARTIAL and findings limited to independently supported source claims.
- Read-only resume commands from the repository root:
  - `git status --short; git log -1 --oneline`
  - `$A='D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.15.2\mojmap'; $B='D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.16.5\mojmap'`
  - `Get-Content -LiteralPath "$A\net\minecraft\world\entity\LivingEntity.java"`; repeat for the paired `$B` file and each named slice dependency.

## Finding snapshots (not pair freeze)

Each finding is committed as an immutable source snapshot. F-S1-OPEN-SHULKER-ESCAPE has separate blind-source acceptance and finding-only handoff eligibility recorded below; the other eight snapshots remain submitted and await independent review. Pair-wide implementation/source reconciliation remains deferred until the full-pair source freeze.

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

- Coverage counts by status: pending 0; in-progress 5; compared-no-difference 7; findings 8; remaining required stage slices not yet entered and open.
- Required inventory status and evidence: all seven pending.
- Open dependencies: S1 escape-query and CLIMBABLE resource; S4 callback/query border disposition; S5 shape providers/support/registration/neighbor routes; remaining required stage 2-7 inventory slices; independent source reviewer assignment.
- Unresolved gaps and limits: exhaustive source comparison is incomplete.
- Evidence/hash/correspondence audit: Ready/source/artifact manifest hashes verified; cited A Block/BlockState, B BlockBehaviour/CollisionGetter and paired ScaffoldingBlock source hashes checked against the exact published Mojmap files; exact provenance sidecars verified, mappingArtifacts=null, success logs are summary-only; the full provider method/resource index remains pending.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed.
