# Discovery: 1.14.4 to 1.15.2

- Run status: partial
- Scope: complete client player movement call graph; older A = 1.14.4; newer B = 1.15.2
- Repository revision and start date: base 002137b227676caea77f6832b9f4c8d0b6200bff (main); 2026-10-07; task branch feat/source-discovery-movement-source-1-14-4-1-15-2
- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap for A and B. Both ready JSONs report exact version IDs and mapping=mojmap; each client jar uses its own official client mappings.
- Source preparation owner / command / log / readiness marker: shared campaign source owner (identity not provided); exact command/log not yet published; A ready/1.14.4/mojmap.ready.json, B ready/1.15.2/mojmap.ready.json.
- Toolchain/decompiler/remapper versions and options: both report Vineflower on Java 25; exact Gradle, TinyRemapper, Mapping-IO versions/options and invocation/log requested from source owner.
- Discovery author(s): /root, source-only pair owner.
- Independent reviewer (must differ from discovery authors): not assigned; audit pending.

## Artifact manifest

- A 1.14.4: exact ID and metadata ID verified; source root ready/1.14.4/mojmap/; 3,250 files, 17,080,207 bytes; client jar artifacts/1.14.4/client.jar SHA-256 b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a; mode mojmap; official mapping artifacts/1.14.4/client_mappings.txt SHA-256 2dd53a5e70ba493cf6e33c0fc52bbdf4c57f9429c7a13842565aa825fd44d910; no community coordinate/build or bridge; mapped jar artifacts/1.14.4/client-mojmap.jar SHA-256 850d17d7e0f78e064d522474396753b3545e822feaa33c1ff9f8ff0ba2acd9aa; source manifest ready/1.14.4/mojmap.sources.sha256 SHA-256 af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b; artifact manifest ready/1.14.4/mojmap.artifacts.sha256 SHA-256 308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970; diagnostics ready/1.14.4/mojmap.movement-diagnostics.txt SHA-256 d404e46059270c9283f6767edb6050959548253d2725443c95b6ab9cb49b1a61.
- B 1.15.2: exact ID and metadata ID verified; source root ready/1.15.2/mojmap/; 3,332 files, 17,543,871 bytes; client jar artifacts/1.15.2/client.jar SHA-256 4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c; mode mojmap; official mapping artifacts/1.15.2/client_mappings.txt SHA-256 65ad295b6cf63821f5d8f961c128128475e6d39386358d22eb238a3ce6e31777; no community coordinate/build or bridge; mapped jar artifacts/1.15.2/client-mojmap.jar SHA-256 c0fe9cfe2a273c42ff269b58c9aabb93815d6bb7bf457ca1deb8695833c4380b; source manifest ready/1.15.2/mojmap.sources.sha256 SHA-256 cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7; artifact manifest ready/1.15.2/artifacts.sha256 SHA-256 208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406; diagnostics ready/1.15.2/movement-diagnostics.txt SHA-256 1ab6c473dbbdf2c590a7a8870befbd404e634fe7c1b0ae6a796b7b5f9c63d4d5.
- Verification: both statuses ready; exact IDs and namespace match; JSON-referenced manifest/diagnostic hashes match; source hashes checked A 3250/3250 and B 3332/3332; artifact hashes checked A 40/40 and B 38/38; zero mismatches. Diagnostics locate Entity.move, LivingEntity.jumpFromGround/travel/aiStep, Player.aiStep/jumpFromGround/travel and LocalPlayer.aiStep/move on both sides. Record per-source and resource hashes with each closed slice.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending source closure
- Evidence inventory and finding IDs included at freeze: pending
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; no prior pair report, mod implementation, patch class, code diff, wiki, MCPK or wiki-audit output opened.
- Source/mapping hashes covered by freeze: pending

## Correspondence and call order

- Pair anchors: client/player/LocalPlayer.java and KeyboardInput.java; world/entity/player/Player.java; world/entity/LivingEntity.java; world/entity/Entity.java. Exact bodies are paired per slice; names alone do not establish correspondence.
- Observed call order: LocalPlayer.tick -> superclass tick; LocalPlayer.aiStep -> Input.tick -> KeyboardInput.tick -> local pre-travel gates; LivingEntity.aiStep -> travel; Entity.move performs collision movement. Full callers, superclass order, post-travel writes and dependencies remain open.
- State edges: keys/input impulses -> sprint/jump/flight/auto-jump -> position/velocity/pose; Entity.move -> collision/on-ground/support/velocity -> callbacks/fluid state; block properties -> friction/speed/jump consumers.

## Required source inventories

All inventories remain pending while exact method and dependency coverage is in progress.

- `INV-TICK` input sampling, tick/call graph, pre-travel, travel branches and post-travel: status=pending; slice_ids=S-INPUT-KEYS,S-LOCAL-PRETRAVEL,S-LIVING-TRAVEL,S-POST-TRAVEL; evidence=both verified diagnostics identify LocalPlayer.aiStep, LivingEntity.aiStep/travel and Player.aiStep.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags and timers: status=pending; slice_ids=S-PLAYER-POSE,S-STATE-WRITERS; evidence=paired Player/Entity/LocalPlayer sources are in verified manifests.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighbors: status=pending; slice_ids=S-ENTITY-MOVE,S-EDGE-BACKOFF,S-COLLISION-PROVIDERS; evidence=diagnostics locate Entity.move on both sides.
- `INV-WORLD-MOVEMENT` block/fluid properties, subclasses, registries, data/tags and defaults: status=pending; slice_ids=S-BLOCK-SPEED,S-BLOCK-JUMP,S-FRICTION-SAMPLE,S-HONEY-BLOCK,S-FLUIDS; evidence=paired registries and source manifests verified.
- `INV-MODIFIERS` movement attributes/effects/enchantments/equipment and application/removal chains: status=pending; slice_ids=S-EFFECTS,S-ENCHANTMENTS,S-ATTRIBUTES,S-EQUIPMENT; evidence=paired LivingEntity/Player anchors verified.
- `INV-EXTERNAL` player-only external inputs and client consumers: status=pending; slice_ids=S-CORRECTIONS,S-PLAYER-PUSH,S-PISTON-MOUNT; evidence=client/entity sources are in verified manifests.
- `INV-EXCLUSIONS` explicit audit of health, regen, hunger, food, saturation, exhaustion, damage/combat and non-player movement: status=pending; slice_ids=none; evidence=scope boundary recorded; direct vanilla predicate reads and exclusions remain to audit.

## Coverage ledger

### Slice S-INPUT-KEYS: keyboard sampling, normalization and sneak scaling

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Input.tick dispatch to KeyboardInput.tick; key booleans -> forward/side impulses -> optional 0.3 scaling. Caller boolean and spectator guard still require semantic closure.
- A evidence: ready/1.14.4/mojmap/net/minecraft/client/player/KeyboardInput.java::KeyboardInput#tick(boolean,boolean), lines 13-26; SHA-256 33daa0833a95e09e728c1b8f509020dd13b70e38aba922e2b1e10ec5c9b7739a.
- B evidence: ready/1.15.2/mojmap/net/minecraft/client/player/KeyboardInput.java::KeyboardInput#tick(boolean), lines 13-26; SHA-256 746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7.
- State producers/writers -> consumers/readers: options keys -> Input booleans/impulses -> LocalPlayer pre-travel predicates and LivingEntity.travel.
- Parent slices / dependencies / closure evidence: S-LOCAL-PRETRAVEL; Input.java both sides and local-player caller gates remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): direct local-player path confirmed; compare slow-movement and spectator caller predicates before disposition.
- Finding IDs or checked absence/replacement path: none frozen; input callers remain open.

### Slice S-LOCAL-PRETRAVEL: local input, sprint/jump/flight and auto-jump

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: paired LocalPlayer.aiStep bodies; split input scaling, sprint, flight/Elytra and auto-jump helpers before disposition.
- A evidence: ready/1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep(), lines 618-779; SHA-256 0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f.
- B evidence: ready/1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep(), lines 625-784; SHA-256 3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd.
- State producers/writers -> consumers/readers: sampled keys and prior jump state -> sprint timers/abilities/fall-flying request/autoJumpTime -> travel and pose; food systems excluded, direct vanilla reads are inputs only.
- Parent slices / dependencies / closure evidence: S-INPUT-KEYS; pose, item-use, equipment, ability and fall-flying producers open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): paired diff exposes candidate flow changes; each behavior needs a bounded slice and dependency closure.
- Finding IDs or checked absence/replacement path: candidate F-ELYTRA-START; source comparison indicates a changed start gate, but effect/equipment/client-state dependencies remain open. Honey's auto-jump gate is tracked under F-HONEY-MOVEMENT.

### Slice S-ENTITY-MOVE: collision movement ordering and post-move block factor

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Entity.move after checkInsideBlocks through post-move velocity updates; block lookup and Player override are linked dependencies.
- A evidence: ready/1.14.4/mojmap/net/minecraft/world/entity/Entity.java::Entity#move(MoverType,Vec3), lines 446-573; SHA-256 31c6be42d165102d3e6dc4295dfef6e8971fc3aea13f938a5b3a33b91d524a7.
- B evidence: ready/1.15.2/mojmap/net/minecraft/world/entity/Entity.java::Entity#move(MoverType,Vec3), lines 450-565; `getBlockSpeedFactor()` lines 590-598; SHA-256 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e.
- State producers/writers -> consumers/readers: block state and collision result -> horizontal delta movement after collision; Player override disables factor while flying/fall-flying.
- Parent slices / dependencies / closure evidence: S-BLOCK-SPEED; all non-default factors, Player override, water/bubble case and support lookup.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): confirmed delta. B applies horizontal `getBlockSpeedFactor()` after `checkInsideBlocks`; A has no corresponding post-collision factor. Registry/property scan found Soul Sand speedFactor 0.4 in B and no speedFactor property in A; B Player override bypasses it while flying or fall-flying. Reachable for a grounded, non-flying 1.14.4 player standing on Soul Sand (A block exists in both versions).
- Finding IDs or checked absence/replacement path: F-SOUL-SAND-SPEED; broader full inventory of dynamic block contributors remains open.

### Slice S-LIVING-JUMP: grounded jump power and block factor

- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: LivingEntity.getJumpPower -> jumpFromGround -> sprint impulse and Jump Boost addition; Entity.getBlockJumpFactor and registrations are dependencies.
- A evidence: ready/1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#getJumpPower()/jumpFromGround(), lines 1748-1775; SHA-256 428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681.
- B evidence: ready/1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#getJumpPower()/jumpFromGround(), lines 1799-1817; Entity#getBlockJumpFactor(), lines 584-588; SHA-256 LivingEntity 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54 and Entity 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e.
- State producers/writers -> consumers/readers: jump input/ground gate -> vertical velocity; block factor and Jump Boost amplifier -> jump power; sprint yaw -> separate horizontal impulse.
- Parent slices / dependencies / closure evidence: S-LOCAL-PRETRAVEL; all block factors and effect application chain.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B multiplies base jump power by block factor before the unchanged additive Jump Boost; Soul Sand's factor remains default 1.0 and Honey Block is the sole non-default jumpFactor registration found in B. The latter block did not exist in A and is treated as modern-only in S-HONEY-BLOCK. No A-era block jump change identified in the inspected registrations.
- Finding IDs or checked absence/replacement path: F-HONEY-MOVEMENT; the complete world/modifier inventories remain open elsewhere.

### Slice S-FRICTION-SAMPLE: ground friction support-block position

- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: grounded LivingEntity.travel branch from support BlockPos through friction and acceleration.
- A evidence: ready/1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#travel(Vec3), grounded lookup lines 1837-1842; SHA-256 428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681.
- B evidence: ready/1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#travel(Vec3), grounded lookup lines 1886-1891; Entity#getBlockPosBelowThatAffectsMyMovement(), lines 600-602; SHA-256 LivingEntity 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54 and Entity 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e.
- State producers/writers -> consumers/readers: pose/dimensions/support -> sampled BlockPos -> friction -> getFrictionInfluencedSpeed and travel acceleration.
- Parent slices / dependencies / closure evidence: S-ENTITY-MOVE; support shapes, friction registrations and partial-height blocks open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): confirmed delta. A samples `floor(minY - 1.0)`; B samples `floor(minY - 0.5000001)`. A reachable example uses six-layer snow over Slime: collision top is 0.625 above the snow cell (snow collision shape uses layers minus one); A samples the Slime below, friction 0.8, while B samples Snow, default friction 0.6. Both snow implementations allow survival on Slime; Ice support is disallowed and is not used as the example.
- Finding IDs or checked absence/replacement path: F-FRICTION-SAMPLE.

### Slice S-EDGE-BACKOFF: careful player edge probing

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: guard and axis/backoff loops directly before collision resolution.
- A evidence: ready/1.14.4/mojmap/net/minecraft/world/entity/Entity.java::Entity#move(MoverType,Vec3), lines 465-466; Entity#applySneaking(Vec3,MoverType), lines 575 onward; file hash pending.
- B evidence: ready/1.15.2/mojmap/net/minecraft/world/entity/Entity.java::Entity#move(MoverType,Vec3), lines 469-470; Player#maybeBackOffFromEdge(Vec3,MoverType), lines 1002-1050; file hash pending.
- State producers/writers -> consumers/readers: shift/sneak and onGround -> X then Z then diagonal 0.05 backoff -> collision input.
- Parent slices / dependencies / closure evidence: S-ENTITY-MOVE; shift-state, noCollision and bounding-box dependencies.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): method ownership/call changed; compare guards, loops and key-state semantics.
- Finding IDs or checked absence/replacement path: no delta established; A and B backoff loops are structurally alike, but the player guard's shift/sneak state path needs closure.

### Slice S-HONEY-BLOCK: modern-only Honey Block player movement

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B registration, collision shape, entityInside -> slide predicate -> velocity/fallDistance writes; A registry absence path to verify.
- A evidence: ready/1.14.4/mojmap/net/minecraft/world/level/block/Blocks.java lines 1-2035, checked absence of HONEY_BLOCK in the complete registry inventory; SHA-256 983d0cde25f55ddb055015b682bbdf3b131394208561805f26d9b2a240dee9a9; block source directory has no HoneyBlock class.
- B evidence: ready/1.15.2/mojmap/net/minecraft/world/level/block/HoneyBlock.java lines 21-35, 49-77, 85-95; SHA-256 40760aeb3c084f1143e87e1e057f18165492eb01b8fce0815dfdd0882cdc8a03; registration `Blocks.java` lines 2118-2123, SHA-256 0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9.
- State producers/writers -> consumers/readers: HoneyBlock contact/onGround/position/vertical velocity -> X/Z scaling, Y velocity -0.05 and fallDistance reset -> following movement/fall checks.
- Parent slices / dependencies / closure evidence: S-ENTITY-MOVE; registration, collision provider and Entity.checkInsideBlocks callback order.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B-only block is modern-only; document its player path without assigning behavior to A-era maps.
- Finding IDs or checked absence/replacement path: F-HONEY-MOVEMENT (modern-only, no 1.14.4 map behavior to emulate).

## Dependency queue and blockers

- D-001; exact source command, successful log path and Gradle/TinyRemapper/Mapping-IO versions/options absent from ready JSONs; request source owner metadata for reproducibility.
- D-002; cited file hashes and final bounded line spans pending; hash each evidence file as slices close.
- D-003; block/property/resource, modifier, collision-provider, fluid and external-state closure open; trace consumers backward to producers and forward to player paths.
- D-004; independent reviewer unassigned; after source-only freeze, coordinator must assign an independent re-walk.

## Finding index

Source-confirmed findings (not yet blind-frozen): [F-SOUL-SAND-SPEED](findings/F-SOUL-SAND-SPEED.md), [F-FRICTION-SAMPLE](findings/F-FRICTION-SAMPLE.md), [F-HONEY-MOVEMENT](findings/F-HONEY-MOVEMENT.md). F-ELYTRA-START remains a candidate pending state/equipment dependency closure. The input, full local pre-travel, collision-provider, state-writer, fluids, modifiers, external-input and exclusions inventories are incomplete. No implementation disposition has been inspected.

## Resume checkpoint

- Last completed slice: exact pair readiness and full source/artifact hash verification.
- Next bounded slice and exact files/members/body ranges to open: close S-ENTITY-MOVE and S-LIVING-JUMP with Entity.java, LivingEntity.java, Player.java, Block.java and Blocks.java; split LocalPlayer.aiStep into sprint/jump/flight/auto-jump.
- Outstanding dependencies and owners: D-001 source owner; D-002/D-003 discovery author; D-004 coordinator/reviewer.
- Current assumptions requiring verification: full factor registrations and modifier chains; partial-height friction consequences; local shift/crouch semantics and Elytra helper; collision, fluid and external state writers.

## Implementation reconciliation

This source-only worker has not inspected implementation and will not do so before blind-discovery freeze.

- Reconciliation status: pending
- Repository revision inspected: not applicable before freeze
- Finding -> implementation disposition/evidence: pending downstream work
- Existing implementation without a frozen source finding: pending downstream work
- Coverage gaps routed back to discovery slices: pending source closure

## Independent source audit

- Reviewer: not assigned
- Status: pending
- Inventories and call-chain ranges re-walked: none
- Concrete missed-slice routes (or none found): pending
- Misses routed to slice/finding IDs and owners: pending
- Reviewer evidence / date: pending

## Source audit closure

- Coverage counts by status: 4 findings, 3 in-progress, 0 pending.
- Required inventory status and evidence: all seven inventories pending; source pair hashes verified, but method/dependency and registration/resource inventories remain open.
- Open dependencies: D-001,D-002,D-003,D-004
- Unresolved gaps and limits: comparison is active and incomplete; diagnostics and initial candidates do not prove comprehensive coverage.
- Evidence/hash/correspondence audit: pair manifests and hashes for the cited Entity, LivingEntity, Player, Block, Blocks, SnowLayerBlock, LocalPlayer, KeyboardInput and HoneyBlock sources recorded; complete tick correspondence and remaining source inventories pending.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (not authorized; separate workflow).

