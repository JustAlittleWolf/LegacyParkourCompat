# Discovery: 1.20.1 to 1.20.2

- Status: active
- Scope: client player movement; older A = 1.20.1; newer B = 1.20.2. Exact adjacent campaign assignment.
- Repository revision and start date: task branch started from `main` at `002137b227676caea77f6832b9f4c8d0b6200bff`; 2026-10-07. Current task branch includes workflow-only commits `1284c1f`, `980069d`, `e9a2af7`.
- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap / `mojmap` on both exact releases; official Mojang naming namespace alignment; both exact release metadata IDs and namespace verified from readiness/provenance JSON.
- Source preparation owner / command / log / readiness marker: sole source owner, source-preparation worktree `feat/movement-campaign-sources` at `802733dc38d80deef20dd508e0ffa661c5735bb9`; command: `.\gradlew.bat decompileMinecraft --versions=1.20.1,1.20.2,1.20.4,1.20.6 --mappings=mojmap --decompiler-heap=4G --output-root=<campaign staging>/mojmap-1.20.1-to-1.20.6-3e08bcae66a145b296f7f85b12623999 --cache-directory=<campaign>/artifacts`; exact run log: `build/movement-campaign-2026-10-07/staging/mojmap-1.20.1-to-1.20.6-3e08bcae66a145b296f7f85b12623999/gradle.full.log`, SHA-256 `e5acf99c0bf01e2a65818cea95da4873b5f8a3c610f795f887c7184216b160fe`.
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1; Java 25.0.3+9-LTS; Vineflower 1.12.0; TinyRemapper 0.14.1; Mapping-IO 0.9.1; Gson 2.14.0; ASM 9.10.1; decompiler heap 4G.
- Discovery author(s): delegated Codex source worker for 1.20.1--1.20.2.
- Independent reviewer (must differ from discovery authors): pending coordinator assignment.

## Artifact manifest

All paths below are relative to the repository root. The source owner published validated markers beside the namespace trees; every marker, provenance file, source/artifact manifest, diagnostic file, exact-run success log, source payload file (9,690 files total), and cache-artifact payload (138 entries total) checked for this pair matched its SHA-256 or exact ID.

A (1.20.1): requested/resolved/version-metadata ID `1.20.1`; source root `build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/`; CLI mode/namespace `mojmap`; original client jar `build/movement-campaign-2026-10-07/artifacts/1.20.1/client.jar`, SHA-256 `56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f`; official client mapping `.../artifacts/1.20.1/client_mappings.txt`, SHA-256 `67bfc33ef0c7103f002d6ac134f8eddd3d67e2ce45d266e83816d1a2e2c9b01d`; mapped client jar `.../artifacts/1.20.1/client-mojmap.jar`, SHA-256 `221d543b9f80ca42faaaa8e3f7cebc4fe1879b8f7ba954bb6ca62f8250be8b77`; bridge mapping: none. Source manifest `build/movement-campaign-2026-10-07/ready/1.20.1/mojmap.sources.sha256`, SHA-256 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`, 4,786 files. Artifact manifest `.../ready/1.20.1/artifacts.sha256`, SHA-256 `5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163bfa19b01c7db2`, 69 entries. Movement diagnostics `.../ready/1.20.1/movement-diagnostics.txt`, SHA-256 `3eecf5b2c48cb99795874a36a8eb83cfc07866bc909a51c997dbfaac8083f4fd`, 22 reviewed movement-method diagnostics.

B (1.20.2): requested/resolved/version-metadata ID `1.20.2`; source root `build/movement-campaign-2026-10-07/ready/1.20.2/mojmap/`; CLI mode/namespace `mojmap`; original client jar `.../artifacts/1.20.2/client.jar`, SHA-256 `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`; official client mapping `.../artifacts/1.20.2/client_mappings.txt`, SHA-256 `d4e2eb2b32ae7bd2e95ea3a8391813a7b6795e20884cf01d989dce68f906e2fd`; mapped client jar `.../artifacts/1.20.2/client-mojmap.jar`, SHA-256 `3dce01cbbb74e367414c811440ca6e80bc2ec18284386720ce413f9aec0ba901`; bridge mapping: none. Source manifest `.../ready/1.20.2/mojmap.sources.sha256`, SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`, 4,904 files. Artifact manifest `.../ready/1.20.2/artifacts.sha256`, SHA-256 `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54`, 69 entries. Movement diagnostics `.../ready/1.20.2/movement-diagnostics.txt`, SHA-256 `a60a6ea2a91cda32df8a7b837d5344ae54c8370058cc9ab3ae163b30e8fa6b79`, 22 reviewed movement-method diagnostics.

The shared exact-run log records successful decompilation and exact metadata IDs for both versions. Relevant local-player/entity/player movement bodies and diagnostic anchors were opened and are intact. The log's remapper access warnings concern unrelated option-screen, renderer, and world-generation classes; no such warning names the cited movement classes. Every cited Java source hash is repeated with its bounded slice/finding. The separate `feather-r1-2026-10-07` revision applies only to six early Feather-derived jars; this pair uses later Mojmap artifacts, which were freshly rehashed and are outside that revision. Pair source/raw hashes remain the values listed above. No resource entry is cited yet; the resource/effect/tag audit remains open.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending completion of all seven inventories and independent audit.
- Evidence inventory and finding IDs included at freeze: current working catalog has `F-1.20.2-PASSENGER-CROUCH-INPUT`; not frozen.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed. This worker has not opened mod implementation, older catalogs, either wiki, or wiki-audit output.
- Source/mapping hashes covered by freeze: source manifests and all current cited source hashes are recorded above/below; source-only freeze pending.

## Correspondence and call order

Verified shared Mojmap correspondence for this first navigation slice: `LocalPlayer#tick()`, `LocalPlayer#aiStep()`, `KeyboardInput#tick(boolean,float)`, `Player#tick()`, `Player#updatePlayerPose()`, `Player#travel(Vec3)`, `LivingEntity#aiStep()`, `LivingEntity#travel(Vec3)`, `Entity#moveRelative(float,Vec3)`, `Entity#canEnterPose(Pose)` -> 1.20.2 `Player#canPlayerFitWithinBlocksAndEntitiesWhen(Pose)`, `Entity#getBoundingBoxForPose(Pose)` -> inline equivalent `Player#getDimensions(Pose).makeBoundingBox(position)`, and `CollisionGetter#noCollision(Entity,AABB)`. Exact source anchors/hashes appear in slices and finding F-1.20.2-PASSENGER-CROUCH-INPUT.

The source-backed route for the confirmed difference is: sampled shift/keyboard input -> LocalPlayer crouching flag -> `isCrouching()` -> `isMovingSlowly()` -> KeyboardInput impulse scaling -> LocalPlayer `serverAiStep()` -> LivingEntity `aiStep()` input vector and `travel()` -> Player/LivingEntity travel -> Entity `moveRelative()`. `LocalPlayer.isEffectiveAi()` returns true, so local movement uses the controlled-instance path. `Player.updatePlayerPose()` independently selects the same crouching pose from shift input and permits the selected pose while a passenger. The flag is distinct from that pose.

## Required source inventories

These inventory maps are not completion claims. Terminal slices cover only the member-level behaviors listed; the remaining source routes and dependencies below are open.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1-TICK-ORDER,S1-PASSENGER-CROUCH-INPUT,S3-PLAYER-TRAVEL,S3-LIVING-TRAVEL,S4-REMOTE-PLAYER-INTERPOLATION,S5-PARTIAL-UPDATE-LERP-TARGET; evidence=bounded member/call-path slices cited below; jump, collision, post-travel and other paths remain open
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S1-PASSENGER-CROUCH-INPUT,S2-POSE-CLEARANCE; evidence=LocalPlayer crouching flag and Player pose-query correspondence, cited below; remaining writers/consumers are queued
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S2-POSE-CLEARANCE; evidence=pose AABB/noCollision correspondence is closed; movement-axis, providers, registrations and neighbors remain open
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=pending bounded provider/registry inventory; evidence=source roots and exact jars validated, semantic inventory not yet completed
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=pending bounded movement modifier inventory; evidence=source roots and exact jars validated, semantic inventory not yet completed
- `INV-EXTERNAL` player-only external movement inputs and direct player velocity/impulse/knockback application, plus player-facing transitions: status=pending; slice_ids=S4-REMOTE-PLAYER-INTERPOLATION,S5-PARTIAL-UPDATE-LERP-TARGET; evidence=remote-player correction consumer slices cited below; remaining local correction, velocity, piston and transition inventory open; non-player/vehicle physics excluded
- `INV-EXCLUSIONS` health/food state production, attack/damage resolution, non-player movement and vehicle physics: status=pending; evidence=scope is recorded; direct movement-state reads and full exclusion audit remain open

## Coverage ledger

### Slice S1-TICK-ORDER: local player tick and input/send order

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: full `LocalPlayer.tick()` and `Player.tick()` wrappers; local player calls superclass tick before sending rider rotation/input or normal position, and Player calls `super.tick()` before the final `updatePlayerPose()`.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/client/player/LocalPlayer.java`::`LocalPlayer#tick()`, lines 189-208, SHA-256 `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2`; `.../net/minecraft/world/entity/player/Player.java`::`Player#tick()`, lines 228-299, SHA-256 `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac`.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.20.2/mojmap/net/minecraft/client/player/LocalPlayer.java`::`LocalPlayer#tick()`, lines 188-207, SHA-256 `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`; `.../net/minecraft/world/entity/player/Player.java`::`Player#tick()`, lines 229-300, SHA-256 `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c`.
- State producers/writers -> consumers/readers: client `input` tick writes key flags/impulses; LocalPlayer sends either rider input/rotation or normal position after its superclass tick; Player's final pose update consumes shift/pose state.
- Parent slices / dependencies / closure evidence: exact method-body pair diffed in full; adjacent S1-PASSENGER-CROUCH-INPUT and S2-POSE-CLEARANCE cover the changed crouch calculation and its pose dependency.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both wrapper bodies have the same superclass, packet, ambient-handler, and pose-update order. Line shifts do not change the sequence.
- Finding IDs or checked absence/replacement path: none; complete body comparison shows no changed operation.

### Slice S1-PASSENGER-CROUCH-INPUT: passenger crouch flag to local movement input

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: the crouch-flag assignment and immediate `Input.tick(isMovingSlowly(), sneakSpeed)` call in `LocalPlayer.aiStep()`; follow the local flag through the input vector consumed by travel.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/client/player/LocalPlayer.java`::`LocalPlayer#aiStep()`, lines 657-665, SHA-256 `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2`; `LocalPlayer#isCrouching()` / `isMovingSlowly()`, lines 603-609, same hash.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.20.2/mojmap/net/minecraft/client/player/LocalPlayer.java`::`LocalPlayer#aiStep()`, lines 655-664, SHA-256 `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`; `LocalPlayer#isCrouching()` / `isMovingSlowly()`, lines 601-607, same hash.
- State producers/writers -> consumers/readers: keyboard writes `Input.shiftKeyDown` and impulses; LocalPlayer.aiStep writes private `crouching`; `isCrouching()` returns that field; `isMovingSlowly()` combines it with visually crawling; KeyboardInput scales `leftImpulse` and `forwardImpulse`; LocalPlayer.serverAiStep writes `xxa/zza`; LivingEntity.aiStep builds the travel vector; Player/LivingEntity travel calls `moveRelative`.
- Parent slices / dependencies / closure evidence: S2-POSE-CLEARANCE closes the renamed fit query and confirms that the actual passenger pose selection remains unchanged. KeyboardInput.tick, LocalPlayer.serverAiStep, Player.travel, LivingEntity.aiStep/travel and Entity.moveRelative were paired; all use the exact source hashes and ranges cited in finding `F-1.20.2-PASSENGER-CROUCH-INPUT`.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): under a passenger tick where the player is dry, not visually crawling, not swimming or flying, can enter the crouching pose, the shift key is down, the local camera controls this player, and the sneaking-speed multiplier is below 1, A can set the local crouching flag true while B's added `!isPassenger()` forces it false. KeyboardInput therefore applies the A multiplier to both directional impulses and leaves the B impulses unscaled. LocalPlayer.serverAiStep copies those impulses into movement inputs; the locally effective player still takes Player.travel -> LivingEntity.travel -> Entity.moveRelative. Because the vector length remains <= 1 after A scaling, Entity.getInputVector does not normalize it back to unit length. The predicted difference is the input contribution to the player's own movement acceleration. Vehicle motion and final mounted position are not inferred. The helper rename itself is closed as equivalent in S2-POSE-CLEARANCE.
- Finding IDs or checked absence/replacement path: `F-1.20.2-PASSENGER-CROUCH-INPUT`.

### Slice S2-POSE-CLEARANCE: crouch/pose AABB clearance and pose selection

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: pose AABB construction with its `1.0E-7` deflation, collision query and Player pose-selection branch.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/world/entity/Entity.java`::`Entity#canEnterPose(Pose)`, lines 1891-1893, SHA-256 `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759`; `Entity#getBoundingBoxForPose(Pose)`, lines 2731-2737, same hash; `Player#updatePlayerPose()`, lines 372-400, and `Player#getDimensions(Pose)`, lines 2018-2020, SHA-256 `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac`; `CollisionGetter#noCollision(Entity,AABB)`, lines 46-63, SHA-256 `8e4863afe40705497b36d0a58d8ee11e5103f4e72cb642aa63cc57f57649a130`.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.20.2/mojmap/net/minecraft/world/entity/player/Player.java`::`Player#canPlayerFitWithinBlocksAndEntitiesWhen(Pose)`, lines 403-405, and `Player#updatePlayerPose()`, lines 373-401, SHA-256 `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c`; `Player#getDimensions(Pose)`, lines 2018-2020, same hash; `CollisionGetter#noCollision(Entity,AABB)`, lines 46-63, SHA-256 `f21daa1ed0365cc16a2638373d702fbaeb4242ad12a80a41105cf43c711e6d95`.
- State producers/writers -> consumers/readers: pose comes from passenger/shift/fall-flying/sleeping/swimming/spin state; Player.getDimensions supplies the requested pose dimensions; entity position supplies the AABB origin; CollisionGetter checks block and entity collisions before Player.setPose.
- Parent slices / dependencies / closure evidence: S1-PASSENGER-CROUCH-INPUT; full-tree exact-source search shows the removed `canEnterPose` and `getBoundingBoxForPose` calls replaced only by the Player helper and the two LocalPlayer fit predicates.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the old pose box is `getDimensions(pose).makeBoundingBox(position)`; the new Player helper constructs the same box then applies the same `1.0E-7` deflation. Player.getDimensions(Pose) is identical. CollisionGetter.noCollision still checks block shapes, entity collisions, and world-border intersection in the same order. Player.updatePlayerPose only changes the helper name and preserves each guard, branch, and setPose point, including its passenger shortcut. Thus pose clearance and the actual pose are unchanged by this helper move.
- Finding IDs or checked absence/replacement path: B has no Entity.canEnterPose/getBoundingBoxForPose; exact all-source search plus Player helper/caller and matching dimensions/noCollision implementation establish the replacement path.

### Slice S3-PLAYER-TRAVEL: Player swimming/flying dispatch

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: entire `Player#travel(Vec3)` branch that applies swimming vertical correction, flight damping, delegates to super travel and records movement statistics.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/world/entity/player/Player.java`::`Player#travel(Vec3)`, lines 1446-1473, SHA-256 `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac`.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.20.2/mojmap/net/minecraft/world/entity/player/Player.java`::`Player#travel(Vec3)`, lines 1450-1477, SHA-256 `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c`.
- State producers/writers -> consumers/readers: swimming/flying/passenger flags and velocity select the player branch; input Vec3 is passed to LivingEntity.travel; resulting position delta feeds checkMovementStatistics.
- Parent slices / dependencies / closure evidence: S1-PASSENGER-CROUCH-INPUT; S3-LIVING-TRAVEL compared the delegated method body; collision/provider/modifier dependencies remain open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): entire paired methods have identical operations; for passengers the swim and flight special branches remain gated by `!isPassenger()` and the same `super.travel(input)` fallback is used.
- Finding IDs or checked absence/replacement path: none; exact whole-member comparison.

### Slice S3-LIVING-TRAVEL: living-entity fluid, fall-flying and ground travel dispatch

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: entire `LivingEntity#travel(Vec3)` method body, including locally controlled water, lava, fall-flying and ordinary ground/air branches and final animation update.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/world/entity/LivingEntity.java`::`LivingEntity#travel(Vec3)`, lines 2004-2135, SHA-256 `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f`.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.20.2/mojmap/net/minecraft/world/entity/LivingEntity.java`::`LivingEntity#travel(Vec3)`, lines 2038-2169, SHA-256 `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828`.
- State producers/writers -> consumers/readers: controlled-instance state, fluid state/effects, enchantments, sprint state, support-block friction, collision flags and input Vec3 choose the branch; movement delegates to `moveRelative`, `move`, friction and fluid-adjustment helpers.
- Parent slices / dependencies / closure evidence: S1-PASSENGER-CROUCH-INPUT and S3-PLAYER-TRAVEL. Exact body comparison found no operation/order difference. Collision, effect/enchantment, block/fluid provider and external inputs remain separate open dependencies.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): all four branches and the common tail match in operation order and constants after line-offset alignment. The local Player route reaches these branches through `Player#travel`'s unchanged passenger/special-case dispatch.
- Finding IDs or checked absence/replacement path: none; entire member compared. This bounded body disposition does not close the broader provider/modifier inventories.

### Slice S4-REMOTE-PLAYER-INTERPOLATION: remote player position/rotation interpolation arithmetic

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: `RemotePlayer#aiStep()` position/rotation interpolation when `lerpSteps > 0`; compare update expressions, setter order and decrement placement.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/client/player/RemotePlayer.java`::`RemotePlayer#aiStep()`, lines 44-54, SHA-256 `52ed2fc76847fce6414747f5a99e148ad816b3554889b57467ce131d62bf1a7e`.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.20.2/mojmap/net/minecraft/client/player/RemotePlayer.java`::`RemotePlayer#aiStep()`, lines 44-48, SHA-256 `a21a23d9e653ca2205b66e77006e1d14e171496081ff16a55f551e2a2b28344b`; replacement helper `Entity#lerpPositionAndRotationStep(int,double,double,double,double,double)`, lines 3385-3394, Entity SHA-256 `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b`.
- State producers/writers -> consumers/readers: `ClientPacketListener` target packets call inherited `LivingEntity#lerpTo`, which writes target coordinates/angles and `lerpSteps`; `RemotePlayer#aiStep` reads them and writes position and rotation. RemotePlayer inherits `isEffectiveAi()`, which returns false client-side, so network targets remain active.
- Parent slices / dependencies / closure evidence: `LivingEntity#lerpTo`, A lines 2720-2727 / B lines 2747-2754, in A/B `LivingEntity.java` hashes cited above; `ClientPacketListener#handleTeleportEntity` / `handleMoveEntity`, A lines 541-585 / B lines 499-546, file hashes A `8b21ccd4106abb0698f6872250e665ae3f0fc942c8953b9ddd45976660ed624b` / B `0208f6942035adaf2e787546851ca296f0e7ec2179d3610f694911eda5283264`. LocalPlayer's controlled path clears `lerpSteps` before its base aiStep interpolation; RemotePlayer overrides `aiStep` and is a separate client Player route.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): A computes XYZ as `current + (target - current) / steps`. It casts the yaw/pitch delta to float and divides before adding; the counter decrement precedes the setters. B computes `1.0 / steps` once, then calls `Mth.lerp` / `Mth.rotLerp`, which multiply by that reciprocal and cast angles after interpolation; the counter decrements after the helper returns. These operations can differ in IEEE-754 rounding. For `currentX = pi`, `targetX = 123456.789`, `steps = 3`, A's expression produces `41154.3573951024`, while B's `Mth.lerp` produces `41154.357395102394` in double precision. No resulting player trajectory is claimed.
- Finding IDs or checked absence/replacement path: `F-1.20.2-REMOTE-PLAYER-LERP-ARITHMETIC`.

### Slice S5-PARTIAL-UPDATE-LERP-TARGET: rotation-only remote update preserves pending position target

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: `ClientPacketListener#handleMoveEntity(ClientboundMoveEntityPacket)` rotation-only branch and the inherited lerp target accessors it passes to `LivingEntity#lerpTo`.
- A evidence: `ClientPacketListener#handleMoveEntity`, lines 567-585, SHA-256 `8b21ccd4106abb0698f6872250e665ae3f0fc942c8953b9ddd45976660ed624b`; with rotation but no position, A passes `getX()/getY()/getZ()`. `LivingEntity#lerpTo`, lines 2720-2727, SHA-256 `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f`.
- B evidence: `ClientPacketListener#handleMoveEntity`, lines 525-546, SHA-256 `0208f6942035adaf2e787546851ca296f0e7ec2179d3610f694911eda5283264`; the rotation-only branch passes `lerpTargetX/Y/Z()`. `LivingEntity#lerpTargetX/Y/Z()`, B lines 2757-2769, returns the pending target when `lerpSteps > 0`, otherwise the current coordinate; B `LivingEntity.java` SHA-256 `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828`.
- State producers/writers -> consumers/readers: a prior positional update leaves pending XYZ and positive `lerpSteps`; a rotation-only packet then reads current displayed XYZ (A) or pending target XYZ (B), passes them to `lerpTo`, and resets the interpolation count. RemotePlayer's `aiStep` consumes that target.
- Parent slices / dependencies / closure evidence: S4-REMOTE-PLAYER-INTERPOLATION; packet handlers enter this path only for an entity not controlled by the local instance. The paired RemotePlayer/lerpTo bodies establish the downstream position consumer.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): with positional interpolation pending, a remote Player receives a relative-move packet with rotation but no position. A replaces the position target with current displayed XYZ; B retains the pending target while updating yaw/pitch and resetting the count. Thus the next interpolation continues toward the pending target in B, while A cancels it and interpolates from the displayed position. No vehicle or non-player physics is inferred.
- Finding IDs or checked absence/replacement path: `F-1.20.2-PARTIAL-UPDATE-LERP-TARGET`.

## Dependency queue and blockers

Resolved:
- D0 source publication: both exact Mojmap readiness markers, IDs/namespaces, manifest-file hashes, all 9,690 Java payload hashes, all 138 listed artifacts, movement diagnostics, and success logs validated.
- D-CHECKER: canonical workflow checker fix `fba28fa154d29572263ea3f2c44cf1dc23134329` applied on this branch as `e9a2af7`; no checker tooling changes authored here.

Open:
- D-LIVING-TRAVEL: body-level branches are compared in S3-LIVING-TRAVEL; changed/consulted helpers and block/fluid/modifier/collision dependencies remain open; owner: this worker.
- D-LIVING-AISTEP: close jump/threshold/post-travel updates and reconcile interpolation changes across LivingEntity and the RemotePlayer override; owner: this worker.
- D-COLLISION: enumerate player movement query/axis/step/support/callback slices and state writers/consumers; owner: this worker.
- D-WORLD-MOVEMENT: inventory block/fluid shape/property providers, registrations, neighboring-state dependencies and jar resource/tag defaults; owner: this worker.
- D-MODIFIERS: inventory movement attributes/effects/enchantments/equipment and application/removal/condition chains; owner: this worker.
- D-EXTERNAL: remote-player position snapshots are partly covered by S4/S5; complete local-player correction/velocity consumers and player-facing transitions; owner: this worker.
- Open dependencies: D-LIVING-TRAVEL, D-LIVING-AISTEP, D-COLLISION, D-WORLD-MOVEMENT, D-MODIFIERS, D-EXTERNAL.

## Finding index

- [F-1.20.2-PASSENGER-CROUCH-INPUT](findings/F-1.20.2-PASSENGER-CROUCH-INPUT.md): passenger-only crouch-flag gate disables local sneak input scaling from 1.20.2; source-confirmed. No runtime validation.
- [F-1.20.2-REMOTE-PLAYER-LERP-ARITHMETIC](findings/F-1.20.2-REMOTE-PLAYER-LERP-ARITHMETIC.md): remote-player position/rotation interpolation changes arithmetic order from division to reciprocal multiplication; source-confirmed, no trajectory validation.
- [F-1.20.2-PARTIAL-UPDATE-LERP-TARGET](findings/F-1.20.2-PARTIAL-UPDATE-LERP-TARGET.md): rotation-only remote movement update preserves pending position target in 1.20.2; source-confirmed, no trajectory validation.

## Resume checkpoint

- Last completed slices: S3-PLAYER-TRAVEL, S3-LIVING-TRAVEL, S4-REMOTE-PLAYER-INTERPOLATION, S5-PARTIAL-UPDATE-LERP-TARGET.
- Next bounded slice and exact files/members/body ranges to open: close the remaining `LivingEntity#aiStep()` jump/threshold/post-travel behavior after its interpolation and RemotePlayer overrides; then enumerate `Entity#move` axis/step/support paths and changed collision dependencies.
- Outstanding dependencies and owners: D-LIVING-TRAVEL, D-LIVING-AISTEP, D-COLLISION, D-WORLD-MOVEMENT, D-MODIFIERS, D-EXTERNAL; all owned by this worker.
- Current assumptions requiring verification: no unseen changed methods/data paths outside the current three paired entry slices; decompiler warnings outside cited movement classes do not damage movement bodies; the exact subclass/provider/resource inventory is still pending.

## Implementation reconciliation

Complete only after blind-discovery freeze. Do not open implementation or prior catalogs before then.

- Reconciliation status: pending
- Repository revision inspected: not inspected
- Finding -> implementation disposition/evidence: pending freeze
- Existing implementation without a frozen source finding: pending freeze
- Coverage gaps routed back to discovery slices: pending freeze

## Independent source audit

No reviewer assigned or source inventory available for a complete re-walk; this is not a pass.

- Reviewer: pending coordinator assignment
- Status: pending
- Inventories and call-chain ranges re-walked: none; full pair audit remains open
- Concrete missed-slice routes (or `none found`): pending independent re-walk
- Misses routed to slice/finding IDs and owners: pending independent re-walk
- Reviewer evidence / date: none

## Source audit closure

- Coverage counts by status: 4 compared-no-difference; 3 findings; 7 terminal slices total; all 7 required inventories remain pending.
- Required inventory status and evidence: member-level slices S1-TICK-ORDER, S1-PASSENGER-CROUCH-INPUT, S2-POSE-CLEARANCE, S3-PLAYER-TRAVEL, S3-LIVING-TRAVEL, S4-REMOTE-PLAYER-INTERPOLATION and S5-PARTIAL-UPDATE-LERP-TARGET are populated; every remaining route stays open.
- Open dependencies: D-LIVING-TRAVEL, D-LIVING-AISTEP, D-COLLISION, D-WORLD-MOVEMENT, D-MODIFIERS, D-EXTERNAL.
- Unresolved gaps and limits: source comparison is in progress; collision movement axes/steps/support, block/fluid providers and resources, modifiers, full state-writer graph, and local-player external inputs are not closed. RemotePlayer findings concern client-side Player entities receiving server targets, not local-player trajectory or vehicle physics.
- Evidence/hash/correspondence audit: exact source/artifact manifests and all payloads verified; cited bodies have SHA-256 and paired line ranges; no source tree or raw diff is tracked.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).
