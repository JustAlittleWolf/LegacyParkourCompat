# Discovery: 1.14.4 to 1.15.2

- Run status: active
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

- INV-TICK input sampling, tick/call graph, pre-travel, travel branches and post-travel: status=pending; slice_ids=S-INPUT-KEYS,S-LOCAL-PRETRAVEL,S-LIVING-TRAVEL,S-POST-TRAVEL; evidence=both verified diagnostics identify LocalPlayer.aiStep, LivingEntity.aiStep/travel and Player.aiStep.
- INV-STATE movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags and timers: status=pending; slice_ids=S-PLAYER-POSE,S-STATE-WRITERS; evidence=paired Player/Entity/LocalPlayer sources are in verified manifests.
- INV-COLLISION player collision/query path, shape providers, registrations, callbacks and neighbors: status=pending; slice_ids=S-ENTITY-MOVE,S-EDGE-BACKOFF,S-COLLISION-PROVIDERS; evidence=diagnostics locate Entity.move on both sides.
- INV-WORLD-MOVEMENT block/fluid properties, subclasses, registries, data/tags and defaults: status=pending; slice_ids=S-BLOCK-SPEED,S-BLOCK-JUMP,S-FRICTION-SAMPLE,S-HONEY-BLOCK,S-FLUIDS; evidence=paired registries and source manifests verified.
- INV-MODIFIERS movement attributes/effects/enchantments/equipment and application/removal chains: status=pending; slice_ids=S-EFFECTS,S-ENCHANTMENTS,S-ATTRIBUTES,S-EQUIPMENT; evidence=paired LivingEntity/Player anchors verified.
- INV-EXTERNAL player-only external inputs and client consumers: status=pending; slice_ids=S-CORRECTIONS,S-PLAYER-PUSH,S-PISTON-MOUNT; evidence=client/entity sources are in verified manifests.
- INV-EXCLUSIONS explicit audit of health, regen, hunger, food, saturation, exhaustion, damage/combat and non-player movement: status=pending; slice_ids=none; evidence=scope boundary recorded; direct vanilla predicate reads and exclusions remain to audit.

## Coverage ledger

### Slice S-INPUT-KEYS: keyboard sampling, normalization and sneak scaling

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Input.tick dispatch to KeyboardInput.tick; key booleans -> forward/side impulses -> optional 0.3 scaling.
- A evidence: ready/1.14.4/mojmap/net/minecraft/client/player/KeyboardInput.java::KeyboardInput#tick(boolean,boolean), lines 13-26; file hash pending.
- B evidence: ready/1.15.2/mojmap/net/minecraft/client/player/KeyboardInput.java::KeyboardInput#tick(boolean), lines 13-26; file hash pending.
- State producers/writers -> consumers/readers: options keys -> Input booleans/impulses -> LocalPlayer pre-travel predicates and LivingEntity.travel.
- Parent slices / dependencies / closure evidence: S-LOCAL-PRETRAVEL; Input.java both sides and local-player caller gates remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): direct local-player path confirmed; compare slow-movement and spectator caller predicates before disposition.
- Finding IDs or checked absence/replacement path: pending.

### Slice S-LOCAL-PRETRAVEL: local input, sprint/jump/flight and auto-jump

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: paired LocalPlayer.aiStep bodies; split input scaling, sprint, flight/Elytra and auto-jump helpers before disposition.
- A evidence: ready/1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep(), lines 618-779; file hash pending.
- B evidence: ready/1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep(), lines 625-778; file hash pending.
- State producers/writers -> consumers/readers: sampled keys and prior jump state -> sprint timers/abilities/fall-flying request/autoJumpTime -> travel and pose; food systems excluded, direct vanilla reads are inputs only.
- Parent slices / dependencies / closure evidence: S-INPUT-KEYS; pose, item-use, equipment, ability and fall-flying producers open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): paired diff exposes candidate flow changes; each behavior needs a bounded slice and dependency closure.
- Finding IDs or checked absence/replacement path: pending.

### Slice S-ENTITY-MOVE: collision movement ordering and post-move block factor

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Entity.move after checkInsideBlocks through post-move velocity updates; block lookup and Player override are linked dependencies.
- A evidence: ready/1.14.4/mojmap/net/minecraft/world/entity/Entity.java::Entity#move(MoverType,Vec3), lines 446-573; file hash pending.
- B evidence: ready/1.15.2/mojmap/net/minecraft/world/entity/Entity.java::Entity#move(MoverType,Vec3), lines 450-565; Entity#getBlockSpeedFactor(), lines 590-598; file hash pending.
- State producers/writers -> consumers/readers: block state and collision result -> horizontal delta movement after collision; Player override disables factor while flying/fall-flying.
- Parent slices / dependencies / closure evidence: S-BLOCK-SPEED; all non-default factors, Player override, water/bubble case and support lookup.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B has a post-collision horizontal scale; enumerate contributors and player applicability before final finding.
- Finding IDs or checked absence/replacement path: candidate F-BLOCK-SPEED; pending.

### Slice S-LIVING-JUMP: grounded jump power and block factor

- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: LivingEntity.getJumpPower -> jumpFromGround -> sprint impulse and Jump Boost addition; Entity.getBlockJumpFactor and registrations are dependencies.
- A evidence: ready/1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#getJumpPower()/jumpFromGround(), lines 1740-1775; exact boundaries/hash pending.
- B evidence: ready/1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#getJumpPower()/jumpFromGround(), lines 1790-1830; Entity#getBlockJumpFactor(), lines 584-588; exact boundaries/hash pending.
- State producers/writers -> consumers/readers: jump input/ground gate -> vertical velocity; block factor and Jump Boost amplifier -> jump power; sprint yaw -> separate horizontal impulse.
- Parent slices / dependencies / closure evidence: S-LOCAL-PRETRAVEL; all block factors and effect application chain.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B multiplies base jump power by a block factor before Jump Boost; enumerate sources and player path.
- Finding IDs or checked absence/replacement path: candidate F-BLOCK-JUMP; pending.

### Slice S-FRICTION-SAMPLE: ground friction support-block position

- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: grounded LivingEntity.travel branch from support BlockPos through friction and acceleration.
- A evidence: ready/1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#travel(Vec3), lookup new BlockPos(x,minY-1.0,z) near line 1835; file hash pending.
- B evidence: ready/1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#travel(Vec3), helper lookup near line 1886; Entity#getBlockPosBelowThatAffectsMyMovement(), lines 600-602; file hash pending.
- State producers/writers -> consumers/readers: pose/dimensions/support -> sampled BlockPos -> friction -> getFrictionInfluencedSpeed and travel acceleration.
- Parent slices / dependencies / closure evidence: S-ENTITY-MOVE; support shapes, friction registrations and partial-height blocks open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): lookup differs; establish a reachable surface that selects a different block and friction value.
- Finding IDs or checked absence/replacement path: candidate F-FRICTION-SAMPLE; pending.

### Slice S-EDGE-BACKOFF: careful player edge probing

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: guard and axis/backoff loops directly before collision resolution.
- A evidence: ready/1.14.4/mojmap/net/minecraft/world/entity/Entity.java::Entity#move(MoverType,Vec3), lines 465-466; Entity#applySneaking(Vec3,MoverType), lines 575 onward; file hash pending.
- B evidence: ready/1.15.2/mojmap/net/minecraft/world/entity/Entity.java::Entity#move(MoverType,Vec3), lines 469-470; Player#maybeBackOffFromEdge(Vec3,MoverType), lines 1002-1050; file hash pending.
- State producers/writers -> consumers/readers: shift/sneak and onGround -> X then Z then diagonal 0.05 backoff -> collision input.
- Parent slices / dependencies / closure evidence: S-ENTITY-MOVE; shift-state, noCollision and bounding-box dependencies.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): method ownership/call changed; compare guards, loops and key-state semantics.
- Finding IDs or checked absence/replacement path: pending.

### Slice S-HONEY-BLOCK: modern-only Honey Block player movement

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B registration, collision shape, entityInside -> slide predicate -> velocity/fallDistance writes; A registry absence path to verify.
- A evidence: ready/1.14.4/mojmap/net/minecraft/world/level/block/Blocks.java registration inventory; checked absence range/hash pending.
- B evidence: ready/1.15.2/mojmap/net/minecraft/world/level/block/HoneyBlock.java getCollisionShape/entityInside/isSlidingDown/doSlideMovement; exact lines/hash pending.
- State producers/writers -> consumers/readers: HoneyBlock contact/onGround/position/vertical velocity -> X/Z scaling, Y velocity -0.05 and fallDistance reset -> following movement/fall checks.
- Parent slices / dependencies / closure evidence: S-ENTITY-MOVE; registration, collision provider and Entity.checkInsideBlocks callback order.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B-only block is modern-only; document its player path without assigning behavior to A-era maps.
- Finding IDs or checked absence/replacement path: candidate F-HONEY-SLIDE; pending.

## Dependency queue and blockers

- D-001; exact source command, successful log path and Gradle/TinyRemapper/Mapping-IO versions/options absent from ready JSONs; request source owner metadata for reproducibility.
- D-002; cited file hashes and final bounded line spans pending; hash each evidence file as slices close.
- D-003; block/property/resource, modifier, collision-provider, fluid and external-state closure open; trace consumers backward to producers and forward to player paths.
- D-004; independent reviewer unassigned; after source-only freeze, coordinator must assign an independent re-walk.

## Finding index

No findings frozen. Candidate roots: block speed factor, block jump factor, friction lookup, auto-jump gate and Honey Block slide behavior. Candidates require both-side evidence, hashes, dependencies, applicability and concrete preconditions before becoming findings.

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

- Coverage counts by status: 5 in-progress, 1 pending.
- Required inventory status and evidence: all seven inventories pending; source pair hashes verified, but method/dependency and registration/resource inventories remain open.
- Open dependencies: D-001,D-002,D-003,D-004
- Unresolved gaps and limits: comparison is active and incomplete; diagnostics and initial candidates do not prove comprehensive coverage.
- Evidence/hash/correspondence audit: pair manifests verified; cited body hashes and complete correspondence pending.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (not authorized; separate workflow).
