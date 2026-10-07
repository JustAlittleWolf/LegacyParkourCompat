# Discovery: 1.15.2 to 1.16.5

- Status: active
- Scope: source-only comparison of exact Java Edition A=1.15.2 and B=1.16.5; direct client player movement.
- Repository revision and start date: campaign base 002137b227676caea77f6832b9f4c8d0b6200bff; 2026-10-07. Branch feat/source-discovery-movement-source-1-15-2-1-16-5.
- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap/Mojmap. Both ready records report exact requested/resolved metadata IDs and Mojmap; source and artifact manifests and cited Java files hash-match.
- Source preparation owner / command / log / readiness marker: shared source owner is sole writer. Command/log and mapping-build details requested, not yet provided. Ready markers are ../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap.ready.json and ../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap.ready.json.
- Toolchain/decompiler/remapper versions and options: pending source-owner provenance.
- Discovery author(s): source-only pair researcher.
- Independent reviewer (must differ from discovery authors): pending coordinator assignment.

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
- Cited source hashes verified against the source manifest: LocalPlayer.java 3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd; Input.java 367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201; KeyboardInput.java 746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7; Player.java 1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793; LivingEntity.java 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54; Entity.java 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e; AABB.java 82324c0e6a3d69e80424656e6098b39ce41f0f17a5e6efbe6542fe8eaca24a18; VoxelShape.java 5faceea0e4ce9ad0f2a1a9d8a768ab877c8728ae2968fe34aa1e03d3ea; FlowingFluid.java e8a395552e53f33bcb5648325615d31bc1796ce2a8aeb501bdda3127622dccdf.

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
- Cited source hashes verified against the source manifest: LocalPlayer.java 6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b; Input.java 367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201; KeyboardInput.java 746ea654cf4f46a5f4b94a237c4307652252a44807a488b606DC993E088396F7; Player.java d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960; LivingEntity.java b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88; Entity.java f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666; AABB.java 514558cf4827679d84a4debd4f400d7d65bd4b8982b7ae8124a0d83a916ba878; VoxelShape.java 6747647a5b94e69747340d432932b77ce5ad701c8f06e141e15b3dd68f8c3bde; FlowingFluid.java a85a5cd6625b1b4a047bd693399e1f78c2978ae207af927d4abfac3b34229552.

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
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer.tick() and movement reporting; detailed body comparison pending.
- A evidence: ../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java, tick(), lines 179-?, SHA-256 3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd.
- B evidence: ../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java, tick(), lines 184-?, SHA-256 6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b.
- State producers/writers -> consumers/readers: local tick/superclass tick -> player movement input and subsequent movement reporting; detailed call closure pending.
- Parent slices / dependencies / closure evidence: S1-LOCAL-AISTEP, packet handlers in S7.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending exact body ranges.
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
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): A checks suffocation on every integer y in a selected column; B queries suffocating collision shapes over an AABB. Need establish concrete common-state difference and query semantics.
- Finding IDs or checked absence/replacement path: pending

### Slice S1-SPRINT-RESET: held-shift cancellation of pending sprint trigger
- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer.aiStep prior input capture, input tick, sprint trigger writers and double-tap consumer.
- A evidence: LocalPlayer#aiStep, lines 625-641 and 657-671; source SHA-256 in A manifest.
- B evidence: LocalPlayer#aiStep, lines 627-647 and 663-681; source SHA-256 in B manifest.
- State producers/writers -> consumers/readers: previous Input.shiftKeyDown -> sprintTriggerTime; later sprint gate consumes timer.
- Parent slices / dependencies / closure evidence: S1-LOCAL-AISTEP, all sprintTriggerTime reads/writes in LocalPlayer.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B adds a reset while prior shift input is true; verify all timer writers and gate preconditions.
- Finding IDs or checked absence/replacement path: pending

### Slice S1-ELYTRA: fall-flying start and climbability gate
- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer.aiStep jump gate; A LivingEntity.onLadder vs B onClimbable and tag data.
- A evidence: LocalPlayer#aiStep lines 715-720; LivingEntity#onLadder lines 1226-1236; source hashes in A manifest.
- B evidence: LocalPlayer#aiStep lines 725-730; LivingEntity#onClimbable lines 1363-1377; source hashes in B manifest.
- State producers/writers -> consumers/readers: feet block state/trapdoor/tag -> fall-flying initiation and movement mode; B also records lastClimbablePos.
- Parent slices / dependencies / closure evidence: exact CLIMBABLE tag resource, legacy blocks, trapdoor helper and travel consumers.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): legacy block membership and B-only blocks not closed.
- Finding IDs or checked absence/replacement path: pending

### Slice S1-WATER-DESCENT: local crouch-to-descend impulse
- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer.aiStep water/shift condition; Player.isAffectedByFluids; LivingEntity.goDownInWater.
- A evidence: LocalPlayer#aiStep lines 722-725; goDownInWater lines 1819-1821; hashes in A manifest.
- B evidence: LocalPlayer#aiStep lines 732-735; Player#isAffectedByFluids lines 1005-1007; goDownInWater lines 1898-1900; hashes in B manifest.
- State producers/writers -> consumers/readers: input shift and water contact; abilities.flying -> isAffectedByFluids; goDownInWater adds (0,-0.04F,0) to delta movement.
- Parent slices / dependencies / closure evidence: all Player abilities writers/defaults and LocalPlayer.aiStep.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B adds a flying-state gate; exact reachability and ability state sources pending.
- Finding IDs or checked absence/replacement path: pending

### Slice S2-EDGE: edge-restraint trigger
- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Player.maybeBackOffFromEdge and B.isAboveGround, including unchanged 0.05-step loops.
- A evidence: Player#maybeBackOffFromEdge lines 1002-1049; SHA-256 1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793.
- B evidence: Player#maybeBackOffFromEdge lines 1010-1060 and isAboveGround lines 1062-1065; SHA-256 d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960.
- State producers/writers -> consumers/readers: flying/onGround/fallDistance/maxUpStep/support query -> reduced x/z movement -> Entity.move.
- Parent slices / dependencies / closure evidence: Player.isStayingOnGroundSurface; Entity.move caller; Level.noCollision/support query.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B removes onGround as the direct requirement, adds not-flying and permits support within maxUpStep; close query semantics.
- Finding IDs or checked absence/replacement path: pending

### Slice S3-TRAVEL: LivingEntity travel dispatch
- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: all A/B LivingEntity.travel branches, split into bounded ground/air, water, lava, fall-flying and climbing slices.
- A evidence: LivingEntity#travel lines 1831-1987; SHA-256 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54.
- B evidence: LivingEntity#travel lines 1914-2046; SHA-256 b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88.
- State producers/writers -> consumers/readers: per-branch records pending.
- Parent slices / dependencies / closure evidence: S3-GROUND-AIR,S3-WATER,S3-LAVA,S3-GLIDE,S3-CLIMB,S3-JUMP; extracted-helper correspondence pending.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B restructures fluid predicates and extracts helpers; each player path is compared separately.
- Finding IDs or checked absence/replacement path: pending

## Dependency queue and blockers

- SRC-OWNER-PROVENANCE: command/log, mapping build and toolchain versions/options absent from ready records; requested through commentary. Owner: source-preparation owner.
- S1-ESCAPE-QUERY: compare A per-block suffocation tests with B collision-shape AABB query; inspect exact query implementation and relevant shapes. Owner: discovery author.
- S1-CLIMB-TAG: verify B CLIMBABLE tag and compare with A explicit set; establish legacy vs B-only membership and state writers. Owner: discovery author.
- S2-EDGE-QUERY: close noCollision/support query dependency. Owner: discovery author.
- Stages 2-7: method-level slices/dependencies still to be inventoried.

## Finding index

No findings are complete yet. Candidate records remain in-progress until evidence closure. The historical pair report is not imported as source confirmation.

## Resume checkpoint

- Last completed slices: S1-INPUT-VECTOR and S1-KEYBOARD.
- Active slices: local tick/input order, suffocation-space escape, sprint timer, climbing/elytra, fluid descent, edge restraint and LivingEntity travel.
- Next: close the four dependencies above; then finish all navigation stages and resource inventories.
- Outstanding dependencies: SRC-OWNER-PROVENANCE,S1-ESCAPE-QUERY,S1-CLIMB-TAG,S2-EDGE-QUERY plus all pending stage work.

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

- Coverage counts by status: pending 1; in-progress 6; compared-no-difference 2; findings 0; remaining required stage slices not yet entered and open.
- Required inventory status and evidence: all seven pending.
- Open dependencies: SRC-OWNER-PROVENANCE,S1-ESCAPE-QUERY,S1-CLIMB-TAG,S2-EDGE-QUERY plus all stage 2-7 work.
- Unresolved gaps and limits: exhaustive source comparison is incomplete.
- Evidence/hash/correspondence audit: Ready marker, artifact manifest, source manifest and core-file hashes verified; toolchain provenance and full method index pending.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed.
