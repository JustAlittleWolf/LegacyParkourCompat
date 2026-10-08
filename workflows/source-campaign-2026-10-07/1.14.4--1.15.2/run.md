# Discovery: 1.14.4 to 1.15.2

- Run status: partial
- Scope: client player movement; older A = exact Java Edition 1.14.4; newer B = exact Java Edition 1.15.2. Pair coverage remains partial.
- Repository revision and start date: base 002137b227676caea77f6832b9f4c8d0b6200bff (main); 2026-10-07; task branch feat/source-discovery-movement-source-1-14-4-1-15-2
- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap output for A and B; exact IDs match marker and metadata IDs. A was produced by a dual-output `--mappings=feather,mojmap` run; only its Mojmap tree and official 1.14.4 mappings are used here. B was produced by a Mojmap batch run. This pair uses no bridge mapping.
- Source preparation owner / command / log / readiness marker: owner checkout `C:/Users/Wolfi/.codex/worktrees/3e2d/LegacyParkourCompat` (individual identity not recorded). A exact command `gradlew.bat decompileMinecraft --versions=1.14.4 --mappings=feather,mojmap --decompiler-heap=4G --output-root=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\staging\1.14.4-dual-7870320a676d4a4986540e7d0c40314f --cache-directory=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts`; B exact command `gradlew.bat decompileMinecraft --versions=1.15.2,1.16.5,1.17.1,1.18.2 --mappings=mojmap --decompiler-heap=4G --output-root=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\staging\mojmap-1.15-to-1.18-f41b958fe6b84e6eb832e0c465a0e56d --cache-directory=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts`. Readiness records are `ready/1.14.4/mojmap.ready.json` and `ready/1.15.2/mojmap.ready.json`; each has an adjacent success excerpt, not the original full Gradle stream. SHA-256: A marker `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043`, provenance `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97`, excerpt `faaa84fbb80b67131b5f0b6d6c828d59bb1c7a143d1bd92dfe2eaa99faf71e80`; B marker `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0`, provenance `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e`, excerpt `b8b60b3ce9a7f746be9e2023bbd9afb7c85dc58cb8582b9ac5962fc2c8a6348d`.
- Toolchain/decompiler/remapper versions and options: both provenance records report Java `25.0.3+9-LTS`, Gradle `9.7.1`, Vineflower `1.12.0`, ASM `9.10.1`, Mapping IO `0.9.1`, Gson `2.14.0`, Tiny Remapper `0.14.1`; heap 4G; `GRADLE_USER_HOME=C:\Users\Wolfi\.gradle`. Initial full Gradle streams were not persisted. The source-preparation repo revision was not recorded by provenance.
- Discovery author(s): /root, source-only pair owner.
- Independent reviewer (must differ from discovery authors): snapshot reviewer `01a116ce-b44d-75f0-974f-5038e0d227c7` assigned; whole-pair audit reviewer still pending.

## Artifact manifest

- A 1.14.4: source root `ready/1.14.4/mojmap/`; 3,250 files, 17,080,207 bytes; original client SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`; official mapping `artifacts/1.14.4/client_mappings.txt` SHA-256 `2dd53a5e70ba493cf6e33c0fc52bbdf4c57f9429c7a13842565aa825fd44d910`; Mojmap jar SHA-256 `850d17d7e0f78e064d522474396753b3545e822feaa33c1ff9f8ff0ba2acd9aa`; no bridge. The dual run also materialized Feather artifacts; they are unused by this comparison. Source manifest hash `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; artifact manifest hash `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970`; diagnostics hash `d404e46059270c9283f6767edb6050959548253d2725443c95b6ab9cb49b1a61`.
- B 1.15.2: source root `ready/1.15.2/mojmap/`; 3,332 files, 17,543,871 bytes; original client SHA-256 `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c`; official mapping `artifacts/1.15.2/client_mappings.txt` SHA-256 `65ad295b6cf63821f5d8f961c128128475e6d39386358d22eb238a3ce6e31777`; Mojmap jar SHA-256 `c0fe9cfe2a273c42ff269b58c9aabb93815d6bb7bf457ca1deb8695833c4380b`; no bridge. Source manifest hash `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; artifact manifest hash `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406`; diagnostics hash `1ab6c473dbbdf2c590a7a8870befbd404e634fe7c1b0ae6a796b7b5f9c63d4d5`.
- Fresh resume verification on 2026-10-07: status and release/metadata IDs match; readiness-referenced manifest hashes match; all source entries A 3,250/3,250 and B 3,332/3,332 verified with zero mismatches; all raw artifact-manifest entries A 40/40 and B 38/38 verified with zero mismatches; client hashes match markers. The six-release Feather artifact revision does not include either selected Mojmap artifact; no revised derived snapshot is used by this pair. Diagnostics locate Entity.move, LivingEntity.jumpFromGround/travel/aiStep, Player.aiStep/jumpFromGround/travel and LocalPlayer.aiStep/move on both sides. Record per-source and resource hashes with each closed slice.

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

- `INV-TICK` input sampling, tick/call graph, pre-travel, travel branches and post-travel: status=pending; slice_ids=S-INPUT-KEYS,S-LOCAL-PRETRAVEL,S-ELYTRA-START,S-LIVING-TRAVEL,S-POST-TRAVEL; evidence=both verified diagnostics identify LocalPlayer.aiStep, LivingEntity.aiStep/travel and Player.aiStep.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags and timers: status=pending; slice_ids=S-PLAYER-POSE,S-STATE-WRITERS,S-ELYTRA-START; evidence=paired Player/Entity/LocalPlayer sources are in verified manifests.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighbors: status=pending; slice_ids=S-ENTITY-MOVE,S-EDGE-BACKOFF,S-COLLISION-PROVIDERS; evidence=diagnostics locate Entity.move on both sides.
- `INV-WORLD-MOVEMENT` block/fluid properties, subclasses, registries, data/tags and defaults: status=pending; slice_ids=S-BLOCK-SPEED,S-BLOCK-JUMP,S-FRICTION-SAMPLE,S-HONEY-BLOCK,S-FLUIDS; evidence=paired registries and source manifests verified.
- `INV-MODIFIERS` movement attributes/effects/enchantments/equipment and application/removal chains: status=pending; slice_ids=S-EFFECTS,S-ENCHANTMENTS,S-ATTRIBUTES,S-EQUIPMENT,S-ELYTRA-START; evidence=paired LivingEntity/Player anchors verified.
- `INV-EXTERNAL` player-only external inputs and client consumers: status=pending; slice_ids=S-CORRECTIONS,S-PLAYER-PUSH,S-PISTON-MOUNT; evidence=client/entity sources are in verified manifests.
- `INV-EXCLUSIONS` explicit audit of health, regen, hunger, food, saturation, exhaustion, damage/combat and non-player movement: status=pending; slice_ids=none; evidence=scope boundary recorded; direct vanilla predicate reads and exclusions remain to audit.

## Coverage ledger

### Slice S-INPUT-KEYS: keyboard sampling, normalization and slow-input gates

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer.aiStep` refreshes key state and computes the slow-input condition before the tutorial, item-use modifier, sprint gates and `super.aiStep`; `KeyboardInput.tick` sets directional impulses and applies the 0.3 multiplier. At `Player.tick` end, `updatePlayerPose` recalculates dimensions/state after the input sample.
- A evidence: `ready/1.14.4/mojmap/net/minecraft/client/player/KeyboardInput.java::KeyboardInput#tick(boolean,boolean)`, lines 13-26, SHA-256 `33daa0833a95e09e728c1b8f509020dd13b70e38aba922e2b1e10ec5c9b7739a`; `LocalPlayer.java` lines 593-605, 618-630, SHA-256 `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f`; `Player.java` tick/pose, SHA-256 `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12`; `Entity.java` visual crawl/collision pose, SHA-256 `31c6be42d165102d3e6dc4295dfef6e8971fc3aea13f938a5b3a33b91d524a7`.
- B evidence: `ready/1.15.2/mojmap/net/minecraft/client/player/KeyboardInput.java::KeyboardInput#tick(boolean)`, lines 13-26, SHA-256 `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7`; `LocalPlayer.java` lines 596-612, 625-636, SHA-256 `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`; `Player.java` tick/pose, SHA-256 `1ba2724c22163862b8f7fdfdea5a04a6e4db26a119d7e5360ba024724a34793`; `Entity.java` visual crawl/collision pose, SHA-256 `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`.
- State producers/writers -> consumers/readers: Options movement/sneak keys -> Input fields/impulses -> LocalPlayer `serverAiStep` writes `xxa`/`zza` -> LivingEntity `aiStep` passes them to `travel`; Player pose and spectator state guard the slow-input predicate. Both LocalPlayer classes return true from `isEffectiveAi`; the normal input-to-travel path is reachable.
- Parent slices / dependencies / closure evidence: closed bounded input path; `S-LOCAL-PRETRAVEL` remains open for other sprint, flight, and auto-jump decisions. `Player.isImmobile` plus the paired `LivingEntity.aiStep` zero-input guard closes the sleeping-only auto-crouch predicate difference as no movement effect.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): directional key sampling and the 0.3 arithmetic are equivalent. Normal non-spectator slow input is equivalent. A sleep-specific condition difference is nullified by `isImmobile` zeroing both horizontal movement inputs before travel. Confirmed `F-SPECTATOR-CRAWL-SCALING`: if a controlled local player enters spectator mode with a dry swimming/crawl pose carried from the previous tick, `LocalPlayer.aiStep` samples it before end-of-tick pose recalculation; A's explicit spectator guard bypasses the 0.3 factor, while B applies it.
- Finding IDs or checked absence/replacement path: `F-SPECTATOR-CRAWL-SCALING`; no additional difference in key sampling or impulse normalization found.
### Slice S-LOCAL-PRETRAVEL: local input, sprint/jump/flight and auto-jump

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: paired LocalPlayer.aiStep bodies; split input scaling, sprint, flight/Elytra and auto-jump helpers before disposition.
- A evidence: ready/1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep(), lines 618-779; SHA-256 0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f.
- B evidence: ready/1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep(), lines 625-784; SHA-256 3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd.
- State producers/writers -> consumers/readers: sampled keys and prior jump state -> sprint timers/abilities/fall-flying request/autoJumpTime -> travel and pose; food systems excluded, direct vanilla reads are inputs only.
- Parent slices / dependencies / closure evidence: S-INPUT-KEYS; pose, item-use, equipment and ability dependencies remain open; see bounded S-ELYTRA-START slice.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): paired diff exposes candidate flow changes; each behavior needs a bounded slice and dependency closure.
- Finding IDs or checked absence/replacement path: F-ELYTRA-START is closed in its own bounded slice; F-FLYING-CRAWL-SNEAK-RESCALE freezes the flight crawl/sneak compensation; sprint, remaining flight-toggle order and non-Honey auto-jump eligibility remain open here. Honey's block-factor auto-jump gate is tracked under F-HONEY-FACTORS.

### Slice S-ELYTRA-START: fresh jump press and fall-flying start

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: paired LocalPlayer.aiStep start gate, enabled Elytra check, B Player helper and shared-flag consumer in LivingEntity.travel.
- A evidence: ready/1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep(), lines 707-711; SHA-256 0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f.
- B evidence: ready/1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep(), lines 715-720; Player#tryToStartFallFlying(), lines 1578-1592; SHA-256 LocalPlayer 3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd; Player 1ba2724c22163862b8f7fdfdea5a04a6e4db26a119d7e5360ba024724a34793.
- State producers/writers -> consumers/readers: jump key rising edge + Elytra slot/enable state + airborne/ability/passenger/ladder/water gates -> shared flag 7 -> LivingEntity.travel fall-flying dispatch.
- Parent slices / dependencies / closure evidence: S-LOCAL-PRETRAVEL; B LivingEntity#travel() checks isFallFlying; Player#startFallFlying sets flag 7. Server acceptance is external and is not required to establish B's local state write.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): with an enabled Elytra, airborne and not passenger/on ladder/in water/ability flight, pressing jump while still ascending does not pass A's `deltaMovement.y < 0.0` gate. B calls its helper without a descent gate and sets local shared flag 7 before sending the command, which is consumed by the travel branch. Further sprint, flight-toggle and auto-jump pre-travel slices remain open.
- Finding IDs or checked absence/replacement path: F-ELYTRA-START.

### Slice S-ENTITY-MOVE: collision movement ordering and post-move block factor

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Entity.move after checkInsideBlocks through post-move velocity updates; block lookup and Player override are linked dependencies.
- A evidence: ready/1.14.4/mojmap/net/minecraft/world/entity/Entity.java::Entity#move(MoverType,Vec3), lines 446-573; SHA-256 31c6be42d165102d3e6dc4295dfef6e8971fc3aea13f938a5b3a33b91d524a7.
- B evidence: ready/1.15.2/mojmap/net/minecraft/world/entity/Entity.java::Entity#move(MoverType,Vec3), lines 450-565; `getBlockSpeedFactor()` lines 590-598; SHA-256 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e.
- State producers/writers -> consumers/readers: block state and collision result -> horizontal delta movement after collision; Player override disables factor while flying/fall-flying.
- Parent slices / dependencies / closure evidence: S-BLOCK-SPEED; all non-default factors, Player override, water/bubble case and support lookup.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): confirmed delta. B applies horizontal `getBlockSpeedFactor()` after `checkInsideBlocks`; A has no corresponding post-collision factor. Registry/property scan found Soul Sand speedFactor 0.4 in B and no speedFactor property in A; B Player override bypasses it while flying or fall-flying. Reachable for a grounded, non-flying 1.14.4 player standing on Soul Sand (A block exists in both versions).
- Finding IDs or checked absence/replacement path: F-SOUL-SAND-SPEED, F-HONEY-FACTORS; broader full inventory of dynamic block contributors remains open.

### Slice S-LIVING-JUMP: grounded jump power and block factor

- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: LivingEntity.getJumpPower -> jumpFromGround -> sprint impulse and Jump Boost addition; Entity.getBlockJumpFactor and registrations are dependencies.
- A evidence: ready/1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#getJumpPower()/jumpFromGround(), lines 1748-1775; SHA-256 428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681.
- B evidence: ready/1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#getJumpPower()/jumpFromGround(), lines 1799-1817; Entity#getBlockJumpFactor(), lines 584-588; SHA-256 LivingEntity 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54 and Entity 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e.
- State producers/writers -> consumers/readers: jump input/ground gate -> vertical velocity; block factor and Jump Boost amplifier -> jump power; sprint yaw -> separate horizontal impulse.
- Parent slices / dependencies / closure evidence: S-LOCAL-PRETRAVEL; all block factors and effect application chain.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B multiplies base jump power by block factor before the unchanged additive Jump Boost; Soul Sand's factor remains default 1.0 and Honey Block is the sole non-default jumpFactor registration found in B. The latter block did not exist in A and is treated as modern-only in S-HONEY-BLOCK. No A-era block jump change identified in the inspected registrations.
- Finding IDs or checked absence/replacement path: F-HONEY-FACTORS; the complete world/modifier inventories remain open elsewhere.

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
- Exact behavior boundary and enclosing guards/order checked: B registration, collision shape, entityInside -> slide predicate -> velocity/fallDistance writes; A complete registry absence path verified.
- A evidence: ready/1.14.4/mojmap/net/minecraft/world/level/block/Blocks.java lines 1-2035, checked absence of HONEY_BLOCK in the complete registry inventory; SHA-256 983d0cde25f55ddb055015b682bbdf3b131394208561805f26d9b2a240dee9a9; block source directory has no HoneyBlock class.
- B evidence: ready/1.15.2/mojmap/net/minecraft/world/level/block/HoneyBlock.java lines 21-35, 49-77, 85-95; SHA-256 40760aeb3c084f1143e87e1e057f18165492eb01b8fce0815dfdd0882cdc8a03; registration `Blocks.java` lines 2118-2123, SHA-256 0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9.
- State producers/writers -> consumers/readers: HoneyBlock contact/onGround/position/vertical velocity -> X/Z scaling, Y velocity -0.05 and fallDistance reset -> following movement/fall checks.
- Parent slices / dependencies / closure evidence: S-ENTITY-MOVE; registration, collision provider and Entity.checkInsideBlocks callback order.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B-only block is modern-only; document its player path without assigning behavior to A-era maps.
- Finding IDs or checked absence/replacement path: F-HONEY-SLIDE (modern-only, no 1.14.4 map behavior to emulate); F-HONEY-FACTORS is cross-referenced from S-ENTITY-MOVE and S-LIVING-JUMP.

## Dependency queue and blockers

- D-001; exact source commands and tool versions are recovered from provenance; original full Gradle streams and source-preparation repository revision were not retained (success excerpts are hashed). Record as a provenance limitation; it does not invalidate independently reverified Mojmap sources/raw inputs.
- D-002; hashes/spans for remaining inventory slices are still pending; record each as that slice closes.
- D-003; block/property/resource, modifier, collision-provider, fluid and external-state closure open; trace consumers backward to producers and forward to player paths.
- D-004; reviewer `01a116ce-b44d-75f0-974f-5038e0d227c7` assigned to finding snapshots; whole-pair independent re-walk remains unassigned.

## Finding index

Source-confirmed findings (not yet pair-frozen or independently accepted): [F-SOUL-SAND-SPEED](findings/F-SOUL-SAND-SPEED.md), [F-FRICTION-SAMPLE](findings/F-FRICTION-SAMPLE.md), [F-HONEY-SLIDE](findings/F-HONEY-SLIDE.md), [F-HONEY-FACTORS](findings/F-HONEY-FACTORS.md), [F-ELYTRA-START](findings/F-ELYTRA-START.md), [F-SPECTATOR-CRAWL-SCALING](findings/F-SPECTATOR-CRAWL-SCALING.md), [F-FLYING-CRAWL-SNEAK-RESCALE](findings/F-FLYING-CRAWL-SNEAK-RESCALE.md). The full tick and state inventories remain incomplete, including broader local pre-travel, collision-provider, fluids, modifiers, external-input and exclusions work. No implementation disposition has been inspected.

## Resume checkpoint

- Checkpoint branch: `feat/source-discovery-movement-source-1-14-4-1-15-2`; the earlier checkpoint was based on `c4e84a6cc7a026a25dce887025308890bac7ed21`; resumed from `1d776787edfd53b5230d51b012454cb4e4921d3d`, with `main` (`002137b227676caea77f6832b9f4c8d0b6200bff`) already merged; latest finding payload is `8eb4d395210d6886982e5735f8d7b0b189e70b6e`.
- Source identity: A `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/ready/1.14.4/mojmap/`, B `.../ready/1.15.2/mojmap/`; both markers, version metadata, source manifests, artifact manifests, original client jars and every manifested source/raw artifact entry were freshly reverified with zero mismatches. This pair uses Mojmap; the revised early-Feather snapshots are not inputs.
- Closed bounded slices: S-ENTITY-MOVE, S-LIVING-JUMP, S-FRICTION-SAMPLE, S-HONEY-BLOCK, S-ELYTRA-START and S-INPUT-KEYS (findings); finding files F-SOUL-SAND-SPEED, F-FRICTION-SAMPLE, F-HONEY-SLIDE, F-HONEY-FACTORS, F-ELYTRA-START and F-SPECTATOR-CRAWL-SCALING. Snapshot event -01 remains submitted; event -02 freezes F-SOUL-SAND-SPEED at `68af793511194dbcb35752ab1f17cf75831ab82b`; event -03 freezes F-SPECTATOR-CRAWL-SCALING at `9d2da16e265d2fed8c4c57b55270bf9f8918f2e1`; event -04 freezes F-FLYING-CRAWL-SNEAK-RESCALE at `8eb4d395210d6886982e5735f8d7b0b189e70b6e`; events -02, -03 and -04 await independent review; the pair remains partial.
- Materialized open slices: S-LOCAL-PRETRAVEL and S-EDGE-BACKOFF (both `in-progress`). Planned but not yet materialized as ledger rows: S-LIVING-TRAVEL, S-POST-TRAVEL, S-PLAYER-POSE, S-STATE-WRITERS, S-COLLISION-PROVIDERS, S-FLUIDS, S-EFFECTS, S-ENCHANTMENTS, S-ATTRIBUTES, S-EQUIPMENT, S-CORRECTIONS, S-PLAYER-PUSH, S-PISTON-MOUNT and the explicit exclusion audit. All seven required inventories therefore remain pending.
- Next bounded slice: continue S-LOCAL-PRETRAVEL by closing the remaining sprint start/stop, ability-flight toggle, Elytra-start and auto-jump branches and their guards; begin at `LocalPlayer.aiStep()` A 637-772/B 643-778, then follow `updateAutoJump` and the local travel caller. S-EDGE-BACKOFF follows.
- Exact read-only next commands (PowerShell, repo root):

```powershell
$A = 'D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.14.4\mojmap'
$B = 'D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.15.2\mojmap'
Get-Content -LiteralPath "$A\net\minecraft\client\player\LocalPlayer.java" | Select-Object -Skip 636 -First 145
Get-Content -LiteralPath "$B\net\minecraft\client\player\LocalPlayer.java" | Select-Object -Skip 642 -First 145
Get-Content -LiteralPath "$A\net\minecraft\client\player\LocalPlayer.java" | Select-Object -Skip 835 -First 55
Get-Content -LiteralPath "$B\net\minecraft\client\player\LocalPlayer.java" | Select-Object -Skip 847 -First 55
```

- Outstanding dependencies and owners: D-001 provenance limitation (full original console streams, source-preparation repo revision and owner identity were not retained); D-002 remaining exact source ranges/hashes (discovery author); D-003 full movement/data/resource/external-input inventory (discovery author); D-004 independent finding and full-pair reviewers (coordinator).
- Current assumptions requiring verification: sleeping/obstructed crouch equivalence and spectator slowdown; remaining local pre-travel gates; player shape and state writers; fluid/modifier/resource chains; direct player velocity corrections/pushes; external block support/collision routes; complete exclusions.

## Finding snapshots (not pair freeze)

### Snapshot event FS-2026-10-07-1.14.4-1.15.2-01

- Finding ID(s): F-SOUL-SAND-SPEED, F-FRICTION-SAMPLE, F-HONEY-SLIDE, F-HONEY-FACTORS, F-ELYTRA-START
- Source finding author(s): /root
- Status: submitted
- Immutable snapshot commit: `4a0c35f2008d785867c00360a7b72726e3506435`
- Finding file path and SHA-256: `findings/F-SOUL-SAND-SPEED.md` `a43043bed85d233478d323621ed97ef7ac27e7fc9a286f439dc2cd5280604f7e`; `findings/F-FRICTION-SAMPLE.md` `ca432d90d831032a8ca4574024a1442127705a197a6f329c321944f9dadb67c3`; `findings/F-HONEY-SLIDE.md` `828e2ebfadc5d2f639bd4b3cd05d5f046804908ddcbe68c271534287829f233b`; `findings/F-HONEY-FACTORS.md` `d28defde835c9fc5d0dcfc76b3ab81e056eab9953ac1179297f2f318cd50e824`; `findings/F-ELYTRA-START.md` `b6d5091e95e7cc0d35eb696dea6d06f720aaad91540ad88915ddc61dad576e59`.
- Exact A/B artifact-manifest identities/hashes: A `ready/1.14.4/mojmap.artifacts.sha256` SHA-256 `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970`; B `ready/1.15.2/artifacts.sha256` SHA-256 `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406`; each manifest was fully reverified against raw artifacts at handoff.
- Cited source/resource hashes: source hashes are enumerated in each immutable finding; the pair source manifests are A `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` and B `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; no data resource entry is cited.
- Verified implementation boundary/evidence, or unresolved boundary reason: each finding proves the difference between exact 1.14.4 and 1.15.2 sources; the first affected release within the interval is unknown. A finer release cutover is unresolved and must not be inferred from endpoint evidence.
- Finding-specific closed dependency IDs/evidence: F-SOUL-SAND-SPEED—Soul Sand registration, all B speedFactor/jumpFactor consumers and registrations searched, player `Entity.move` path; F-FRICTION-SAMPLE—both lookup formulas, six-layer Snow collision height, Snow survival on Slime, Slime/default friction, unchanged friction consumer; F-HONEY-SLIDE—B registry/class/predicate/writes and paired `Entity.checkInsideBlocks` callback path, A complete registry absence; F-HONEY-FACTORS—A/B factor properties, registrations, post-move speed consumer, jump and auto-jump consumers; F-ELYTRA-START—paired fresh-press gates, B helper/shared-flag write and travel consumer. See the finding files for ranges and hashes.
- Independent blind source reviewer and decision date: `01a116ce-b44d-75f0-974f-5038e0d227c7` assigned for event -02; decision pending.
- Review basis / requested source-only revisions: pending independent review; review each finding's concrete preconditions, endpoint provenance, hashes, player reachability and scoped dependencies.
- Pair run status and commit at handoff: `partial`; `4a0c35f2008d785867c00360a7b72726e3506435`.
- Pair complete: no
- Implementation handoff: blocked; independent source acceptance is pending and no finer-than-endpoint change boundary has been established.
- Replaces/supersedes snapshot ID and reason, if applicable: none.

### Snapshot event FS-2026-10-07-1.14.4-1.15.2-02

- Finding ID(s): F-SOUL-SAND-SPEED
- Source finding author(s): /root
- Status: submitted; reviewer decision pending
- Immutable snapshot commit: `68af793511194dbcb35752ab1f17cf75831ab82b`
- Finding file path and SHA-256: `findings/F-SOUL-SAND-SPEED.md` `93de76cba69e7e697a5fddf83efc0b6e36586c1348007e3a2a89542321d1947d`
- Exact A/B source identity: `ready/1.14.4/mojmap/` and `ready/1.15.2/mojmap/`; ready markers match IDs 1.14.4/1.15.2 and mapping `mojmap`. Source manifests A `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`, B `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; artifact manifests A `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970`, B `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406`. Cited source body hashes were revalidated against the published source roots for Entity.java, Blocks.java, Block.java, LivingEntity.java and Player.java; see the immutable finding for each hash and range.
- Verified implementation boundary/evidence, or unresolved boundary reason: exact endpoints establish the delta; earliest affected release inside (1.14.4, 1.15.2] is not established. This snapshot covers only the bounded Soul Sand speed-factor finding and does not freeze or close the pair.
- Finding-specific closed dependency IDs/evidence: S-ENTITY-MOVE and the grounded/non-flying Player path; registry/property and factor-consumer search is recorded in the finding and coverage ledger. Other pair inventories remain open.
- Independent blind source reviewer and decision date: `01a116ce-b44d-75f0-974f-5038e0d227c7`; pending.
- Review basis / requested source-only revisions: independently re-check endpoint pairing/provenance, cited body hashes/ranges, speed-factor guards, Soul Sand registration, and grounded player reachability.
- Pair run status and commit at handoff: `partial`; payload `68af793511194dbcb35752ab1f17cf75831ab82b`.
- Pair complete: no
- Implementation handoff: blocked pending independent acceptance; no precise cutover within the interval is claimed.
- Replaces/supersedes snapshot ID and reason, if applicable: none.

### Snapshot event FS-2026-10-07-1.14.4-1.15.2-03

- Finding ID(s): F-SPECTATOR-CRAWL-SCALING
- Source finding author(s): /root
- Status: submitted; reviewer decision pending
- Immutable snapshot commit: `9d2da16e265d2fed8c4c57b55270bf9f8918f2e1`
- Finding file path and SHA-256: `findings/F-SPECTATOR-CRAWL-SCALING.md` `dd205ec23634d701e33adaa47268a5752f602c71b8bc2263b8511efd2aa880a7`
- Exact A/B source identity: `ready/1.14.4/mojmap/` and `ready/1.15.2/mojmap/`; ready markers match exact release IDs and `mojmap`. Source manifests A `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`, B `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; artifact manifests A `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970`, B `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406`. Cited source body hashes were revalidated against published roots for KeyboardInput, LocalPlayer, AbstractClientPlayer, Player, Entity and LivingEntity; each is listed in the finding.
- Verified implementation boundary/evidence, or unresolved boundary reason: exact endpoints establish a one-tick input multiplier delta under the documented stale crawl pose and spectator transition; the first affected release inside (1.14.4, 1.15.2] is unknown. This partial snapshot does not freeze or close the pair.
- Finding-specific closed dependency IDs/evidence: pre-input pose tick order; `isVisuallyCrawling` pose/water predicate; LocalPlayer input refresh and spectator guard; serverAiStep impulse writes; LivingEntity.aiStep travel vector; spectator flying travel branch. The sleeping auto-crouch path was checked and has no movement effect because `isImmobile` zeros movement impulses.
- Independent blind source reviewer and decision date: `01a116ce-b44d-75f0-974f-5038e0d227c7`; pending.
- Review basis / requested source-only revisions: independently re-check carried-pose transition ordering, keyboard multiplier guard, paired source ranges/hashes, and local travel reachability.
- Pair run status and commit at handoff: `partial`; payload `9d2da16e265d2fed8c4c57b55270bf9f8918f2e1`.
- Pair complete: no
- Implementation handoff: blocked pending independent acceptance; no precise cutover within the interval is claimed.
- Replaces/supersedes snapshot ID and reason, if applicable: none.

### Snapshot event FS-2026-10-07-1.14.4-1.15.2-04

- Finding ID(s): F-FLYING-CRAWL-SNEAK-RESCALE
- Source finding author(s): /root
- Status: submitted; reviewer decision pending
- Immutable snapshot commit: `8eb4d395210d6886982e5735f8d7b0b189e70b6e`
- Finding file path and SHA-256: `findings/F-FLYING-CRAWL-SNEAK-RESCALE.md` `f214e1fd29ac67af9de86f87325eea1cd765a8490694dc19e4838476f8af18aa`
- Exact A/B source identity: `ready/1.14.4/mojmap/` and `ready/1.15.2/mojmap/`; release IDs, Mojmap markers, source manifests and artifact manifests match the run ledger. Cited source bodies were revalidated for KeyboardInput, LocalPlayer, Player and Entity; hashes and exact ranges are listed in the finding.
- Verified implementation boundary/evidence, or unresolved boundary reason: exact endpoints prove the input scale difference for a local flying player in a dry crawl pose while holding sneak and a direction; earliest affected release inside the interval remains unknown. This partial snapshot does not freeze or close the pair.
- Finding-specific closed dependency IDs/evidence: S-INPUT-KEYS, flight-compensation portion of S-LOCAL-PRETRAVEL; forced crawl pose selection, controlled-camera input writes, flying Player.travel and the paired moveRelative/getInputVector consumer. Other pre-travel gates remain open.
- Independent blind source reviewer and decision date: `01a116ce-b44d-75f0-974f-5038e0d227c7`; pending.
- Review basis / requested source-only revisions: independently re-check crawling pose reachability while ability-flying, key slowdown and compensating divide order, exact source hashes/ranges, and the movement-vector consumer.
- Pair run status and commit at handoff: `partial`; payload `8eb4d395210d6886982e5735f8d7b0b189e70b6e`.
- Pair complete: no
- Implementation handoff: blocked pending independent acceptance; no precise cutover within the interval is claimed.
- Replaces/supersedes snapshot ID and reason, if applicable: none.

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

- Coverage counts by status: 7 findings, 2 in-progress, 0 pending.
- Required inventory status and evidence: all seven inventories pending; source pair hashes verified, but method/dependency and registration/resource inventories remain open.
- Accepted finding snapshots (metadata only; does not close pair): none; events -01, -02, -03 and -04 are submitted and awaiting independent review.
- Open dependencies: D-001,D-002,D-003,D-004
- Unresolved gaps and limits: comparison is partial; the full tick inventory remains open beyond the bounded input-key slice, along with remaining local pre-travel, full collision providers/shapes, fluids, modifier application, external player motion inputs, and explicit exclusions.
- Evidence/hash/correspondence audit: pair manifests and hashes for the cited Entity, LivingEntity, Player, Block, Blocks, SnowLayerBlock, LocalPlayer, KeyboardInput and HoneyBlock sources recorded; complete tick correspondence and remaining source inventories pending.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (not authorized; separate workflow).

