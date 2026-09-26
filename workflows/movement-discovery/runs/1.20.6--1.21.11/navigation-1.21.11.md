# B-side navigation index: Minecraft 1.21.11 Mojmap

This is an exact-source inventory for the newer endpoint only. It records useful file/member anchors and source hashes to resume discovery after the 1.20.6 side is provenanced. It makes no A-to-B correspondence, difference, or no-difference claim. Line numbers refer to the existing 1.21.11 Mojmap output under the shared source root and must be refreshed if that output is ever regenerated.

## Artifact anchors

- Source root: `decompiled_minecraft/1.21.11/mojmap/`.
- The run manifest at `../1.21.11--26.1.2/run.md` records successful 1.21.11 resolution and Mojmap completion, client/mapping/remapped-jar/version-JSON identities, and the retained transcript extract limitation.
- This run independently rechecked the cached artifact hashes listed in `run.md` and the source file hashes below.

## Stage 1 — local input and tick ordering

- `net/minecraft/client/player/KeyboardInput.java`: `tick()` line 23; writes `keyPresses` and normalized `moveVector` (line 35). SHA-256 `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3`.
- `net/minecraft/client/player/ClientInput.java`: `tick()` line 10, `makeJump()` line 21; `keyPresses` and `moveVector` fields lines 7–8. SHA-256 `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`.
- `net/minecraft/client/player/LocalPlayer.java`: class declaration line 105; `tick()` line 211; `aiStep()` line 728; `move()` line 947. SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`.
- Seed path to resolve on both releases: input sampling -> `LocalPlayer.tick()` / `aiStep()` -> inherited player/living tick and travel; record guards and state writes during paired inspection.

## Stage 2 — player state and gates

- `net/minecraft/client/player/AbstractClientPlayer.java`: inheritance intermediary; SHA-256 `afe84ab1491a17aeeccf895ca4ecb17e941b8e517c980e31281b3d463fc593bd`.
- `net/minecraft/world/entity/player/Player.java`: `tick()` line 238; `aiStep()` line 452; edge-backoff override `maybeBackOffFromEdge()` line 889; helpers `isAboveGround()` line 940 and `canFallAtLeast()` line 944; travel wrapper line 1360. SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`.
- Resolve pose/dimensions, sprint/jump/item-use gates, abilities and defaults through consumers and writers; class presence is not coverage.

## Stage 3 — living movement integration

- `net/minecraft/world/entity/LivingEntity.java`: jump power methods lines 2256–2260; `jumpFromGround()` line 2269; `travel()` line 2309; air travel line 2343; fluid selection around line 2373 and water/lava methods lines 2380/2409; fall-flying line 2441; friction/movement helper line 2534; `aiStep()` line 2877 and travel dispatch around line 2974. SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`.
- `net/minecraft/world/entity/player/Player.java` travel wrapper line 1360 delegates into `LivingEntity.travel()`; establish input/state path and compare full relevant methods after alignment.

## Stage 4 — entity movement and collision

- `net/minecraft/world/entity/Entity.java`: `tick()` line 480; `move()` line 685; calls edge-backoff at line 711 and owns base `maybeBackOffFromEdge()` line 1000. SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`.
- `net/minecraft/world/entity/player/Player.java`: virtual edge-backoff override and support probes above.
- `net/minecraft/world/phys/AABB.java`: SHA-256 `b8ace04fa09628266e3e12ec5423dbceb7ecef000041824c00510a5ddca6e9a1`.
- `net/minecraft/world/phys/shapes/VoxelShape.java`: SHA-256 `9655dca09dfb10e942ac88fb6e31a44f2611a5c61472136649269f248569f44d`.

## Stage 5 — blocks and fluids

- `net/minecraft/world/level/block/state/BlockState.java`: SHA-256 `a3eec38ae13ae1e1305960fa13e34d8843c1ae9480c75d6ca1450d590ba9bb7b`.
- `net/minecraft/world/level/material/FluidState.java`: SHA-256 `146a5e6a19a93da631a6f4ed57020791bde31087fcedc6d52c68b1c582b71b0f`.
- `net/minecraft/world/level/material/FlowingFluid.java`: SHA-256 `905e5a02ab10b23b5f6e64a0f732ee552ae8895801e1944d9850a41cc8bce91a`.
- Extend inventory to movement-relevant block subclasses, registration/default properties, tags, collision shape and callback owners; inspect original jar resources separately because the Java source saver omits resources.

## Stage 6 — effects, enchantments, attributes and equipment

- `net/minecraft/world/effect/MobEffects.java`: SHA-256 `e27eef187ce134c3b64300fd7a4c7f691d553a172ec26ebac79797a030db7047`.
- `net/minecraft/world/item/enchantment/Enchantments.java`: SHA-256 `c4de10d11104f209de97a002744c277a869a05a86f303b1d68a2a389c8f4c5e9`.
- `net/minecraft/world/item/enchantment/EnchantmentHelper.java`: SHA-256 `38e944de3098847f62633a6f0747bc2e1dcf016694faebbe47009fbe8af514a85`.
- Follow movement consumers back through attribute aggregation, effect/enchantment registrations, conditions, equipment slots, tags, components and resource data.

## Stage 7 — external movement influences

- Begin from local-player packet consumers and movement-state writers. Inventory exact class/method anchors after Stage 1–6 dependencies are known; distinguish client-computed trajectories from synchronized server values.

## Resume order

1. Verify and record exact A=1.20.6 source provenance and Mojmap/official-name alignment.
2. Establish both inheritance chains, descriptors and entry-point correspondence; do not infer equivalence from shared names.
3. Compare bounded Stage 1 slices in order, then stages 2–7; hash every source/resource used and update coverage/dependency state incrementally.
4. Keep all coverage pending until each paired slice has evidence and a scoped conclusion.


## Pair-specific Stage 1 notes (partial)

### Slice 1.1 — input sampling and vector shaping; no terminal conclusion yet

- A `KeyboardInput.tick(boolean,float)` samples four directional, jump and shift keys into booleans/impulses (`KeyboardInput.java:21-37`, SHA-256 `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0`). `Input.getMoveVector()` returns `(leftImpulse, forwardImpulse)` directly (`Input.java:21-23`, SHA-256 `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb`). `LocalPlayer.aiStep()` samples the slow-movement/enchantment multiplier and calls `input.tick(...)` at line 667, then item-use slowdown at lines 669-672; `serverAiStep()` copies resulting impulses into `xxa`/`zza` at lines 612-616. A `LivingEntity.aiStep()` applies `0.98F` to both axes before travel at lines 2671-2672; `Entity.getInputVector()` normalizes only when squared length exceeds 1 (`Entity.java`, source hash in the A manifest).
- B `KeyboardInput.tick()` captures forward/back/left/right/jump/shift/sprint and sets `moveVector` from normalized `Vec2` impulses (`KeyboardInput.java:23-36`, SHA-256 `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3`). `ClientInput.getMoveVector()` returns that stored vector (`ClientInput.java:10-15`, SHA-256 `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`). `LocalPlayer.applyInput()` calls `modifyInput()` at lines 653-664; that method applies `0.98F`, item-use multiplier, sneaking-speed attribute and square-movement shaping (`LocalPlayer.java:668-700`, SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`). `LivingEntity.aiStep()` dispatches `applyInput()` before jump and travel at lines 2920 onward; its default `applyInput()` scales axes at lines 3014-3016, while the controlled-camera `LocalPlayer` override supplies already-modified axes without calling `super`.
- Observation: the responsibility for 0.98 scaling moved from `LivingEntity.aiStep()` to the local-player input transform. A's diagonal vector may be normalized later by `Entity.getInputVector`; B normalizes at sampling and reshapes in `modifyInputSpeedForSquareMovement`. A and B also compute sneak and item-use scaling at different points, and B sources sneak/item multipliers from attributes/components. These textual transformations alone do not settle floating-point equivalence or controller/alternate-input behavior. Keep slice in-progress until operation order, reachable producers, values and relevant data are closed.

### Slice 1.2 — sprint timer evidence

- A local-player sprint counter decrements at `LocalPlayer.java:649-650`; eligible tap handling assigns fixed `7` at line 698. File SHA-256 is recorded in F-1.
- B counter decrements at `LocalPlayer.java:729-730`; eligible tap handling reads the client `Options.sprintWindow()` at lines 767-775. The option is default `7`, range `0..10`, in `Options.java:556-565`.
- F-1 isolates the setting-dependent interval. Eligibility and stop conditions are separate open slices and were not treated as equivalent by this finding.



### Slice 1.1 — diagonal keyboard float normalization (F-3)

- A source order: keyboard impulses `(1.0F,1.0F)` -> `LivingEntity.aiStep()` multiplies both by `0.98F` -> `Entity.getInputVector` sees squared length over one and normalizes a `Vec3` with double `Math.sqrt`.
- B source order: keyboard creates normalized `Vec2` using `Mth.sqrt(float)` -> `LocalPlayer.modifyInput()` applies `0.98F` and square-movement shaping -> the resulting float diagonal components have squared length below one, so `Entity.getInputVector` does not normalize again.
- For the stated no-slowdown diagonal precondition and yaw zero, direct evaluation yields A approximately `0.7071067811865476` and B approximately `0.7071067690849304` per horizontal input component. Finding F-3 records the source anchors, hashes and inference boundary. The slice remains limited to built-in keyboard diagonal input; analog and other scaling paths remain in `DEP-INPUT-SHAPE`.

## Stage 2 — player pose dimensions (bounded no-difference slice)

- A `Player` directly owns the pose-dimension map at `world/entity/player/Player.java:132-153` and overrides `getDefaultDimensions(Pose)` at lines 1997-1998. B moves the same map values into `world/entity/Avatar.java:17-40` and `Avatar.getDefaultDimensions(Pose)` at lines 64-66; B `Player extends Avatar`, so LocalPlayer inherits it.
- The inspected entries match: standing `0.6F x 1.8F`, sleeping shared dimensions, fall-flying/swimming/spin-attack `0.6F x 0.6F` with `0.4F` eye height, crouching `0.6F x 1.5F` with `1.27F` eye height and the same vehicle attachment, dying fixed `0.2F x 0.2F` with `1.62F` eye height. The `LivingEntity.getDimensions(Pose)` special-cases sleeping, otherwise calls the selected default dimensions and scales by `getScale()` with the same expression on A and B.
- This no-difference conclusion is limited to those player dimensions and the wrapper. Pose selection/transition timing, eye/fluid consumers and other state defaults remain pending. B `Avatar.java` SHA-256 `a3f54b9ff81ee203f77efcf5dcab50d69ec9a3d2c5430ce4c4b2ae0abb4bfca9`; A/B Player and LivingEntity hashes are recorded in the artifact/source inventory.

## Stage 3 — ground jump vertical application (F-4)

- A `LivingEntity.jumpFromGround()` assigns the computed jump power directly to Y at lines 2069–2080; B uses `Math.max(jumpPower,currentY)` at lines 2269–2281.
- Both living-entity AI jump blocks reach the method under jump/fluid/ground gates (A line 2656, B line 2946). The sprint impulse remains the same in the bounded method. See F-4 for exact precondition, hashes and inference boundary.

## Stage 4 — edge-backoff probe (F-5)

- A and B share the `Entity.move()` -> virtual `Player.maybeBackOffFromEdge()` caller path and the inspected reduction loops/guards.
- The paired `Player.canFallAtLeast()` AABB differs: A uses the full player X/Z bounds and `1.0E-5F` below the lower step bound; B insets X/Z by `1.0E-7` and uses `1.0E-7` for the lower extension. See F-5 for exact line anchors and hashes.
- The query calls world `noCollision`; collision shape interactions in these margins remain dependencies. This slice records the changed query input, not a claim that ordinary support geometry always produces a different result.
