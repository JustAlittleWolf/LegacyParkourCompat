# Source-only movement discovery: 1.9.4 to 1.10.2

- Status: active
- Scope: direct client-player movement; older A = 1.9.4; newer B = 1.10.2
- Track: source-only discovery; no wiki/MCPK, release-note mechanics, or mod implementation consulted
- Repository base: `main` at `002137b227676caea77f6832b9f4c8d0b6200bff`; task branch `feat/source-discovery-movement-source-1-9-4-1-10-2`
- Start date: 2026-10-07
- Discovery author(s): Codex source-discovery worker
- Independent reviewer (must differ from discovery authors): pending assignment
- Source-only constraint: do not read or change runtime implementation; do not launch tests, clients, TAS, Gym, servers or Docker

## Artifact manifest

The source owner published both exact pairs. The `ready` markers, source manifests, artifact manifests and diagnostics were read from the canonical campaign output and their recorded SHA-256 values match. Both ready records say `status=ready`; `versionId` and `versionMetadataId` exactly match the requested release. No source tree is accepted on directory presence alone.

- Selected aligned namespace: Ornithe Feather (`ornithe-feather`) for both sides; exact common family and release-specific Feather gen2 build.
- Preparation commands / successful logs: A provenance records `.\gradlew.bat decompileMinecraft --versions=1.8.9,1.9.4 --mappings=feather --decompiler-heap=4G` with the shared staging/cache arguments in `ornithe-feather.provenance.json`; B records `.\gradlew.bat decompileMinecraft --versions=1.10.2,1.11.2,1.12.2 --mappings=feather --decompiler-heap=4G` with its staging/cache arguments. Each has a successful `ornithe-feather.success.log` summary excerpt in its version ready directory. The full original Gradle stdout was not persisted. Exact provenance JSON and success-log SHA-256 values are recorded below.
- Toolchain: Java `25.0.3+9-LTS`; Gradle `9.7.1`; Vineflower `1.12.0`; ASM `9.10.1`; Tiny Remapper `0.14.1`; Mapping IO `0.9.1`; Gson `2.14.0`; heap `4G`. JVM vendor/options were not recorded in provenance.
- A (1.9.4): requested/resolved `versionId=1.9.4`, `versionMetadataId=1.9.4`; CLI mode `feather`, resolved namespace `ornithe-feather`; source root `../../../build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/`; Feather `net.ornithemc:feather-gen2:1.9.4+build.2`; client jar SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`; mapped client jar `0df10c862f7fd4848d08597a76c0cf02cec5a9d9886975a57230a033cfb86b3a`; merged-v2 mapping jar `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125`; extracted Tiny mapping `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027`; ready JSON SHA-256 `3d0a810d9f3a93233d880ca07ffc806ea232230d33197197ad50ba69181171c6`; provenance JSON SHA-256 `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd`; success-log excerpt SHA-256 `6cc7b346031356ca3dc697557eeee26d870eaf20e44d01d48c0ddc1dfa27ab9f`; version metadata SHA-256 `4b21379203f0d87df8cc2ab4dd3c3689f139772f1cdb37b40688252547e2d5be`; source manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; artifact manifest SHA-256 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`; diagnostics SHA-256 `51bd42a633c04931814ab78a841cedd3bf87460e04f7877676599b51b59bbb1d`.
- B (1.10.2): requested/resolved `versionId=1.10.2`, `versionMetadataId=1.10.2`; CLI mode `feather`, resolved namespace `ornithe-feather`; source root `../../../build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather/`; Feather `net.ornithemc:feather-gen2:1.10.2+build.2`; client jar SHA-256 `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9`; mapped client jar `ab7aa536f52e94c4c099999bc731020c11a1b29e2979a566cedcc2ffb00e701d`; merged-v2 mapping jar `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0`; extracted Tiny mapping `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1`; ready JSON SHA-256 `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d`; provenance JSON SHA-256 `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6`; success-log excerpt SHA-256 `c5354936e70714a24eb5996ddfafb256a9f7c2521de1218f8964919c7a650ba2`; version metadata SHA-256 `d0624badea6367233d47c830341952126cdb0f99b1e11fb60428dd530a0d4db5`; source manifest SHA-256 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; artifact manifest SHA-256 `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116`; diagnostics SHA-256 `3a8622864d424ff3ddd4d55febaf453d4201c8f1e05638c758616cea5443ab9e`.
- Body diagnostics: A flags `Entity` line 1838 and movement methods `PlayerEntity.moveRelative` lines 1372/1380/1386, `PlayerEntity.jump` lines 1361/1362 and `LivingEntity.jump`/`moveRelative` lines 1279/1302/1711/1698. B flags `Entity` line 1880 and movement methods `LivingEntity.jump`/`moveRelative` lines 1305/1332/1734/1747 and `PlayerEntity.jump`/`moveRelative` lines 1380/1391/1399/1405. `PLAYER-JUMP-AND-FLIGHT-RELATIVE` closes the paired PlayerEntity jump/moveRelative and LivingEntity jump methods with immutable-jar bytecode checks; the LivingEntity fluid moveRelative body and remaining stage call sites stay open.
- Artifact-manifest, source-manifest and diagnostics-file hashes were independently recomputed and match both ready JSON records. Source-tree method-body hashes are checked per citation. Jar resources will be inspected/hash-recorded only when reached by a movement dependency.
- Artifact revision: the immutable `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/<version>/ornithe-feather/client-ornithe-feather.jar` snapshots were independently checked against each `artifact.sha256` and `revision.json`; A hash `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`, revision JSON SHA-256 `df0a26fd4c65292530cdad638e6789cc26fcd47875fe1c3eb2daef6de7e3f915`; B hash `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`, revision JSON SHA-256 `79b5688af6f5cf6eb5c006a867de841de0033b42408c8461283cd9c4b9389856`. Independently rehashed all 1,819 A and 1,845 B source files against their unchanged source manifests, and the original client jars, raw Feather merged mapping jars/Tiny files, and full verification log (`33b732892a03ffac60026663f7266c20b637330db6d480b4260267861bebd40c`). The revision records `sourceTreeIdentical=true`, `sourceFileDifferences=0`, and `rawInputsIdentical=true`. Independent operations verification passed. The original derived jars are unavailable, so this does not prove the revised mapped jars byte-identical to them; that limitation remains attached to the findings.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending
- Evidence inventory and finding IDs included at freeze: pending; this report is still being expanded
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed
- Source/mapping hashes covered by freeze: source and artifact manifest hashes listed in Artifact manifest; discovery is not frozen

## Correspondence and call order

### S1 input/tick correspondence (in progress)

- Input: A/B `net.minecraft.client.entity.living.player.KeyboardInput extends Input`. Both exact `KeyboardInput.tick()` methods are text-identical (A and B source-manifest hash `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`): zero both axes, apply forward/back then left/right key increments/decrements, capture booleans, then apply `(float)(axis * 0.3)` to both axes when sneaking. `Input` has the same fields and no-op `tick()` on A; B adds `getMovement()` returning `new Vec2f(movementSideways, movementForward)`, used only from the new local-player auto-jump scan at B `LocalClientPlayerEntity.java:826` (whole-tree caller search).
- Input producer: A/B `Minecraft` create `new KeyboardInput(this.options)` at lines 1917/1950 and 1913/1946 respectively. The produced `Input` is recalculated by the local player's `mobTick`; no alternate player movement-input producer/caller was found in either source tree.
- Hierarchy: both `LocalClientPlayerEntity extends ClientPlayerEntity extends PlayerEntity extends LivingEntity extends Entity`; same Feather package and corresponding named classes.
- Tick order: A `LocalClientPlayerEntity.tick()` line 143 (B line 152) calls `super.tick()` only when the player's chunk is loaded, then sends movement or vehicle/input packets. `LivingEntity.tick()` calls virtual `this.mobTick()` (A line 1551; B line 1587); dispatch enters local-player `mobTick()` (A line 604; B line 620), which samples `input.tick()` (A line 649; B line 665), performs sprint/flight/riding gates, then `super.mobTick()`. `PlayerEntity.mobTick()` chains to `LivingEntity.mobTick()`, which calls virtual `serverTickAi()` (A line 1686; B line 1722), entering the local player's override (A line 586; B line 602) to copy current axes/jump into living movement fields before `moveRelative`/`jump`/`move`. Full caller/callee bodies and changed dependencies remain under review.
- Confirmed new B movement path: local-player `move(dx,dy,dz)` (B line 812) saves X/Z, calls `super.move`, then calls `autoJump((float)(x-d),(float)(z-e))`; no A counterpart exists in the checked class/inheritance path. The bounded timer/input/jump chain is source-confirmed in `LOCAL-AUTO-JUMP-INPUT`; broader S1.7/S2/S4/S5/S6 inventories and collision-shape coverage remain open.
- Local player field/method writers found so far: `input.tick()` in `mobTick`; input-to-living-field copy in `serverTickAi`; local sprint timers and sprint state in `mobTick`/`setSprinting`; flight state in `mobTick`; riding jump timers/size in `mobTick`; B auto-jump timer in `autoJump` and its next-tick consumption in `mobTick`. Complete state-writer search and dependency closure remain open.

## Required source inventories

- `INV-TICK` status=pending; slice_ids=S1.1–S1.8,S3.1–S3.9; evidence=initial local tick/input call graph in Correspondence and call order; travel and post-travel closures remain open
- `INV-STATE` status=pending; slice_ids=S2.1–S2.8,S4.1,S4.5; evidence=initial local-player state-writer index; pose, dimensions, flags and external writers remain open
- `INV-COLLISION` status=pending; slice_ids=S4.2–S4.6,S5.1–S5.2; evidence=F001 farmland collision dispatch and S5.2 rail outline/collision check below; broader shape registry and callback closure remains open
- `INV-WORLD-MOVEMENT` status=pending; slice_ids=S5.3–S5.6; evidence=initial block property search; complete registries, fluids, neighbors, tags and resource defaults remain open
- `INV-MODIFIERS` status=pending; slice_ids=S6.1–S6.6; evidence=initial jump-strength/effect lookup; modifiers, enchantments, equipment and defaults remain open
- `INV-EXTERNAL` status=pending; slice_ids=S7.1–S7.6; evidence=none; external player movement writers remain open
- `INV-EXCLUSIONS` status=pending; evidence=scope rules exclude health, regeneration, hunger/food/saturation/exhaustion, damage/combat simulation and non-player movement; direct movement-gate reads still need inventory

## Coverage ledger

All seven required navigation stages are open. The following is a **pre-source coverage plan**, not a claim that these are the complete reachable methods. Once exact source is ready, resolve members/callers and split any item whose full body or dependency closure exceeds a bounded slice. Add newly discovered work; never close a stage on unchanged top-level travel code.

### S1 — local input and tick ordering

- S1.1 input sampling and key/controller state: findings for the character-code keybinding reacquisition path (F004); B's additive Input.getMovement() read is separately covered by F003.
- S1.2 input axes, yaw-to-motion conversion and normalization: pending.
- S1.3 local tick, superclass tick and travel call order / previous-current flag capture: pending.
- S1.4 sprint start/stop gates, timers and state writers: pending.
- S1.5 jump input, jump timers/cooldowns and jump state writers: pending.
- S1.6 flight toggle, double-tap and unstuck paths: pending.
- S1.7 automatic jump and synthetic-input paths: pending.
- S1.8 riding input, jump charge and mount gates affecting the player: pending.

### S2 — player state and gates

- S2.1 pose selection and standing/crouching/swimming/gliding transition gates: pending.
- S2.2 dimensions, bounding-box resize and collision timing on pose change: pending.
- S2.3 eye height only where it changes movement/fluid queries: pending.
- S2.4 flight abilities/speed and other movement capability defaults/writers: pending.
- S2.5 swimming/crawling state and stored air-speed state: pending.
- S2.6 edge-sneak support probes and related movement gates: pending.
- S2.7 item-use movement slowdown and input scaling: pending.
- S2.8 direct vanilla sprint-gate consumers: pending; health, regeneration, hunger, food, saturation, exhaustion, damage and combat emulation are excluded.

### S3 — living movement integration

- S3.1 travel dispatch and branch-selection state: pending.
- S3.2 ground acceleration, friction and movement-speed derivation: pending.
- S3.3 air acceleration, sprint air control and relative-input math: pending.
- S3.4 jump power, sprint impulse and movement attribute/effect inputs: pending.
- S3.5 gravity, drag, vertical thresholds and negligible-velocity cutoffs: pending.
- S3.6 water and lava branch movement, depth/jump gates and post-travel updates: pending.
- S3.7 climbing clamps and climb-state producers: pending.
- S3.8 gliding/fall-flying travel math and transitions: pending.
- S3.9 motion callbacks/reset operations reached after travel: pending.

### S4 — entity movement and collision

- S4.1 entity position/velocity/bounding-box writers around movement: pending.
- S4.2 collision query timing, candidates and shape context: pending.
- S4.3 axis clipping order, tie-breaking and blocked-velocity response: pending.
- S4.4 step-up candidates, comparison and selected displacement: pending.
- S4.5 on-ground/support/vertical-collision state computation: pending.
- S4.6 block collision callbacks and movement callback order: pending.
- S4.7 fluid contact/push and their collision/query dependencies: pending.
- S4.8 player push/knockback effects from other entities (player integration only): pending.

### S5 — blocks and fluids

- S5.1 base state/block collision and outline-shape dispatch, registration and defaults: pending.
- S5.2 historical partial-block shape overrides and support effects: pending.
- S5.3 friction/speed/jump-factor registrations and consumers: pending.
- S5.4 contact/landing/climbing/collision callback overrides and state predicates: pending.
- S5.5 fluid height, flow vector and player push producers: pending.
- S5.6 neighbor-dependent block movement inputs, registries and checked absences: pending.

### S6 — effects, attributes, enchantments and equipment

- S6.1 movement effect consumers and amplification/timer formulas: pending.
- S6.2 movement attribute defaults, aggregation and operation order: pending.
- S6.3 enchantment consumers, level formulas, slots and applicability: pending.
- S6.4 equipment/item-use movement consumers and lifecycle: pending.
- S6.5 relevant registrations, tags and jar-resource/default dependency closure: pending.
- S6.6 modern-only mechanics and server/external-data boundaries: pending.

### S7 — external movement influences

- S7.1 incoming velocity and position correction handlers: pending.
- S7.2 player knockback, push and explosion integration: pending.
- S7.3 piston displacement and client/player movement path: pending.
- S7.4 mount/dismount and transition placement affecting the player: pending.
- S7.5 launch-item and external impulse writers: pending.
- S7.6 final cross-stage movement-state writer/caller closure and revisit: pending.

Per bounded slice, record one of `pending`, `in-progress`, `compared-no-difference`, `findings`, `not-applicable`, or `blocked`; cite both-side paths, complete member ranges and hashes, dependency closure and rationale. Checked absence must follow inheritance, callers, registration or replacement paths. Preserve exact arithmetic, casts, comparisons and execution order.

### Slice BLK-FARMLAND-COLLISION: farmland player collision height

- Inventory ID(s): `INV-COLLISION`, `INV-WORLD-MOVEMENT`
- Exact behavior boundary and enclosing guards/order checked: block-state collision-shape dispatch for farmland through the world block-collision collector used by Entity.move; outline shape is not substituted for collision shape.
- A evidence: `net/minecraft/block/FarmlandBlock.java::FarmlandBlock#getCollisionShape(BlockState,World,BlockPos)`, lines 31-40, SHA-256 `9df445485ba7e1603c20b5dede847fe412558148acefe1eedc9bb3b62b14c25`; `net/minecraft/block/Block.java::Block#addCollisions` and `#getCollisionShape`, lines 336-354, SHA-256 `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6`; `net/minecraft/world/World.java::World#getCollisions`, lines 899-981, SHA-256 `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`; `net/minecraft/entity/Entity.java::Entity#move(double,double,double)`, lines 441-721, SHA-256 `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`.
- B evidence: `net/minecraft/block/FarmlandBlock.java::FarmlandBlock#getShape` with checked absence of a `getCollisionShape` override, lines 20-39, SHA-256 `63d9048ebed65b890c370a1da6e79733904f67d953aff9fd8c0316f97f9d4e7f`; inherited `net/minecraft/block/Block.java::Block#getCollisionShape`, lines 355-359, SHA-256 `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01`; `net/minecraft/world/World.java::World#getCollisions`, lines 903-985, SHA-256 `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646`; `net/minecraft/entity/Entity.java::Entity#move(double,double,double)`, lines 446-726, SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`.
- State producers/writers -> consumers/readers: registered farmland block state -> `BlockState.getCollisionShape` -> `Block.addCollisions` -> `World.getCollisions` -> `Entity.move` axis clipping. Farmland outline remains `Box(0,0,0,1,0.9375,1)` on both sides.
- Parent slices / dependencies / closure evidence: depends on S4.2 collision dispatch; `net/minecraft/block/state/StateDefinition.java` inner-state `getCollisionShape` and `addCollisions`, A lines 357-370 SHA-256 `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493`, B lines 362-375 SHA-256 `00fea8cdf8a0cabf1af21e7e7ff47f071bd87a16f91697a3b31efce6c78bfda4`, forward state collision to block override. General player movement consumer remains open; this local producer-to-consumer path is established.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): source-confirmed delta. A farmland overrides collision to `FULL_BLOCK_SHAPE`; B has no override and inherits base `Block.getCollisionShape`, which returns the farmland state outline shape at height 0.9375. World collision enumeration dispatches through this method, so player movement collision can differ when intersecting farmland. Both source trees/raw inputs and revised immutable artifacts were verified; the unavailable original derived jars are not claimed identical. No trajectory is inferred.
- Finding IDs or checked absence/replacement path: F001; B replacement path is inherited `Block.getCollisionShape`.

### Slice BLK-RAIL-OUTLINE-COLLISION: ascending rail outline versus movement collision

- Inventory ID(s): `INV-COLLISION`
- Exact behavior boundary and enclosing guards/order checked: `AbstractRailBlock.getShape` ascending-state branch and `getCollisionShape`, then block collision dispatch; this slice covers player collision geometry only.
- A evidence: `net/minecraft/block/AbstractRailBlock.java::AbstractRailBlock#getShape` and `#getCollisionShape`, lines 39-54, SHA-256 `96256ad09e5833f44f1da66a1da255ad7e380ab63bdc60f74646f7089e1dabec`; empty collision is returned for all rail states.
- B evidence: `net/minecraft/block/AbstractRailBlock.java::AbstractRailBlock#getShape` and `#getCollisionShape`, lines 38-53, SHA-256 `b27f39a823f921c206f21ae8ec7afecf04bea049bded7d1748c3e5251507147e`; empty collision is returned for all rail states.
- State producers/writers -> consumers/readers: rail shape state -> `getShape` (outline); movement collision calls `BlockState.addCollisions` -> `Block.addCollisions` -> `getCollisionShape`.
- Parent slices / dependencies / closure evidence: S4.2 collision dispatch; `Block.addCollisions` and `World.getCollisions` use collision shape rather than outline shape on A and B.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): A ascending outline height is 0.15625; B ascending outline uses full-block outline. Both versions return `EMPTY_BLOCK_SHAPE` from rail `getCollisionShape`, so this outline change does not change player movement collision through the inspected block-collision path. Pair source trees and revised immutable artifacts were verified; original derived-jar equivalence is unproven.
- Finding IDs or checked absence/replacement path: checked non-collision geometry; `getCollisionShape` returns the shared empty sentinel on both versions.

### Slice LIVING-FLUID-NO-GRAVITY: fluid travel vertical gravity gates

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-MODIFIERS`
- Exact behavior boundary and enclosing guards/order checked: full `LivingEntity.moveRelative(float,float)` fluid, climb, and fallback branches, preserving water/lava/player-flight guards, collision check, fluid drag and vertical gravity order.
- A evidence: `net/minecraft/entity/living/LivingEntity.java::LivingEntity#moveRelative(float,float)`, lines 1302-1470, SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`.
- B evidence: `net/minecraft/entity/living/LivingEntity.java::LivingEntity#moveRelative(float,float)`, lines 1332-1506, SHA-256 `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`.
- State producers/writers -> consumers/readers: LivingEntity travel dispatch passes current player velocities and fluid/contact state into `moveRelative`; B's inherited `Entity.NO_GRAVITY` synced Boolean defaults false, is read by `isNoGravity`, and conditionally gates fluid gravity subtraction. B's player-data load path reads saved entity NBT; `Entity.readNbt` sets `NoGravity`, `SyncedData.set` marks it dirty, `TrackedEntity` sends the update to its own `ServerPlayerEntity`, and `ClientPlayNetworkHandler.handleEntityData` applies entries to the client world entity by network ID. A's player hierarchy has no equivalent generic state; A's no-gravity storage is ArmorStand-specific.
- Parent slices / dependencies / closure evidence: S1.3/S3.1 travel dispatch and S4.7 fluid state; B external player-state path traced through `PlayerManager`, `AlphaWorldStorage`, `Entity.readNbt`, `SyncedData`, `TrackedEntity`, `EntityDataS2CPacket`, and `ClientPlayNetworkHandler`. Rechecked A/B source bodies and `javap -c -p` for `LivingEntity.moveRelative` and `LivingEntity.mobTick` on the exact `feather-r1-2026-10-07` jars. The direct caller order is the same: `serverTickAi`, ordinary jump handling, input-speed decay, `flyingTick`, virtual `moveRelative`, then entity pushing. The only source-level player-movement delta in this body is the already recorded B `NoGravity` gates; B's water drag accessor returns the same `0.8F` for players (see `LIVING-WATER-FRICTION-BASE-VALUE`).
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): source-confirmed difference: B guards vertical `-0.08` (loaded-client/server fallback branch) and `-0.02` (water/lava) gravity subtractions with `!isNoGravity()`; A has no generic player flag gate. The flag defaults false, but saved server-player NBT can set it and the server tracker sends the update back to that player's client entity; the client handler applies it. Therefore the condition is reachable for a player whose saved `NoGravity` state is true. The exact source and bytecode evidence is bound to revision `feather-r1-2026-10-07`; unavailable original derived-jar equivalence is not claimed.
- Finding IDs or checked absence/replacement path: F002; B writer/default path is `Entity` synced data and NBT.

### Slice LOCAL-KEYBOARD-INPUT-SAMPLING: keyboard and mouse state into player movement input

- Inventory ID(s): `INV-TICK`
- Exact behavior boundary and enclosing guards/order checked: option key bindings and keyboard/mouse events update `KeyBinding.pressed`; the camera local player's `KeyboardInput.tick()` samples forward/back/left/right/jump/sneak, then applies sneak scaling; `LocalClientPlayerEntity.serverTickAi()` copies axes and jump only under `isCamera()`. Axis-to-world-yaw conversion remains S1.2/S3.3.
- A evidence: `KeyboardInput.tick()` lines 11-46, SHA-256 `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`; `KeyBinding.setAll()` lines 40-47, SHA-256 `29cf0668c652d8d830b3926c2be5d87bf3e8ddc43411dacbc1319c1a96eb4ecb`; `Minecraft.handleKeyboardEvents()` lines 1497-1541 and `openScreen()/lockMouse()` lines 761-795/1130-1141, SHA-256 `6778bd1f0e8aa8a29fdce3bc5285cbd3ea56fa18ca8a43afc4d88439ab12133e`; `ControlsOptionsScreen` lines 93-105, SHA-256 `a3b878360e97a06e44cc352bb6a7f23afa86a3d7dc4aea594e65b4ae212fc5b2`; `ChatScreen.keyPressed` lines 58-75, SHA-256 `2e7a96577f91e4e688b5cc4a598bae3656cede55d0c76a428b9d0d61d15c5cfe`; local-player `serverTickAi()` lines 586-597, SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.
- B evidence: `KeyboardInput.tick()` lines 11-46, SHA-256 `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`; `KeyBinding.setAll()` lines 40-47, SHA-256 `208ee44f87b9cbc99b5fdfa5f99f026be68677f11c25f6dd02aebf3cce9321b`; `Minecraft.handleKeyboardEvents()` lines 1486-1530 and `openScreen()/lockMouse()` lines 763-798/1120-1131, SHA-256 `0979d770e8742a07bb8c54cf7fd3449a8c43a4f48910600ffabf75f1f67ba2d9`; `ControlsOptionsScreen` lines 92-104, SHA-256 `ddf26d661ea89ec9580cbd5432b18c2325e3864ae686269e3f339d83a47ffb2e`; `ChatScreen.keyPressed` lines 58-75, SHA-256 `cfd95fa9a2dbc32d52a8b3138353fcee3de1c93887f6cba87b4bb9e54474d099`; local-player `serverTickAi()` lines 602-613, SHA-256 `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`.
- State producers/writers -> consumers/readers: Controls binds a positive character as `char + 256`. `Minecraft.handleKeyboardEvents` encodes `eventKey == 0` as `eventCharacter + 256`, calls screen handling, then writes `KeyBinding.set(code,true/false)`. ChatScreen closes via `openScreen(null)`; its null branch calls `lockMouse()`, whose active non-Mac focus-reacquisition branch calls `KeyBinding.setAll()` without `releaseAll()`. The resulting pressed state is consumed by `KeyboardInput.tick()` and copied by the camera local player's `serverTickAi()` into living movement axes.
- Parent slices / dependencies / closure evidence: S1.3 local-player sampling/camera guard; S1.2/S3.3 axis transform remains open. A/B `KeyboardInput.java` body is identical; B's additional `Input.getMovement()` is read only by the F003 auto-jump path. Vanilla version metadata selects LWJGL 2.9.4 off macOS for both versions; exact revised Feather and LWJGL bytecode verifies A's caught out-of-range poll and B's >=256 false branch. Revised source/artifact hashes and the unavailable original derived-jar limitation are recorded in F004.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F004 identifies a reachable difference when a user-bound character-code movement key remains physically held across chat closure and its release is not handled before the next input sample. A catches the LWJGL bounds exception, preserving the pressed state; B sets the binding false. Default movement keys below 256 are unaffected.
- Finding IDs or checked absence/replacement path: F004; B's keyCode < 256 guard replaces A's caught out-of-range poll.
### Slice LOCAL-AUTO-JUMP-INPUT: obstacle-triggered local-player jump input

- Inventory ID(s): `INV-TICK`, `INV-INPUT`, `INV-JUMP`, `INV-STATE`
- Exact behavior boundary and enclosing guards/order checked: B's `LocalClientPlayerEntity.move` calls its superclass first, measures actual horizontal displacement, then runs `autoJump`; candidate timer is set only when auto-jump is enabled, timer is clear, player is grounded, not sneaking/riding, movement input is nonzero, and the forward path meets the helper's geometry checks. In B `mobTick`, a positive timer is decremented and sets `input.jumping=true`; then `super.mobTick()` dispatches through `LivingEntity.mobTick`, whose `serverTickAi` copies that input to the inherited `jumping` field before the same method processes the jump. A has the same ordinary jump consumer but no synthesized input path.
- A evidence: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, full source SHA-256 `8AAF711948B7602C2E6C015A37E36ED06073D39727D999D80480B4910B704F5D`; `serverTickAi` lines 586-597 copies direct input, and the local class has no `ticksToNextAutojump`, `autoJump`, or `move(double,double,double)` override. Inherited movement and the complete A tick/input caller path still require closure.
- B evidence: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, full source SHA-256 `A9637065F21AD67464EB5C204C74EBF228C3BB0DA8A96DDF4AE73C0490FED443`; timer field lines 105-106; `serverTickAi` lines 602-613; timer consumption/input write in `mobTick` lines 661-677; movement override lines 812-817; geometry/guard and timer writer `autoJump` lines 823-923.
- State producers/writers -> consumers/readers: movement collision result -> B `move` override -> `autoJump` timer writer -> next `mobTick` countdown and synthetic input write -> `LivingEntity.serverTickAi` jump-state copy -> `LivingEntity.mobTick` jump branch -> virtual `PlayerEntity.jump`. B's normal `KeyboardInput` and auto-jump option writers are traced.
- Parent slices / dependencies / closure evidence: the bounded local-player path through `LivingEntity.moveRelative`, `Entity.move`, `LocalClientPlayerEntity.move`, `LocalClientPlayerEntity.mobTick`, `LivingEntity.mobTick`, and `PlayerEntity.jump` is source-traced. B bytecode for the local-player class was inspected on the exact immutable revision jar; A's corresponding class has no auto-jump fields or methods. Relevant diagnostics contain no entries for these local-player methods. Broader tick inventory and collision-shape coverage remain open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): source-confirmed added path. With auto-jump enabled and the local player grounded, not sneaking/riding, and moving, B tests forward collision geometry and can set a one-tick timer; the next local-player mob tick synthesizes the jump input before the ordinary player jump consumer. B explicitly prevents this generated input from triggering the creative-flight double-tap. The exact path is bound to `feather-r1-2026-10-07`; unavailable original derived-jar equivalence is not claimed.
- Finding IDs or checked absence/replacement path: F003; A's local-player class and A client-entity tree have no corresponding helper, timer, or movement override.

### Slice LOCAL-FALL-FLYING-SOUND-STATE: synchronized fall-flight audio guard

- Inventory ID(s): `INV-STATE`, `INV-TICK`
- Exact behavior boundary and enclosing guards/order checked: B updates a local `falling` boolean from `isFallFlying()` in `mobTick` and uses it only in `onDataValueChanged` to gate playback of `ElytraOnPlayerSoundInstance` when `SHARED_FLAGS` changes.
- A evidence: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, full-file SHA-256 `8AAF711948B7602C2E6C015A37E36ED06073D39727D999D80480B4910B704F5D`; `onDataValueChanged` lines 478-491 has no `falling` state or `SHARED_FLAGS` sound branch. A plays `ElytraOnPlayerSoundInstance` in `mobTick` when requesting fall flying (lines 707-713).
- B evidence: same path, full-file SHA-256 `A9637065F21AD67464EB5C204C74EBF228C3BB0DA8A96DDF4AE73C0490FED443`; field line 107, callback lines 489-504 plays sound under the shared-flag/is-fall-flying/not-falling guard, and `mobTick` line 737 updates `falling` from `isFallFlying()`.
- State producers/writers -> consumers/readers: shared flag update -> callback's sound-only branch; local-player `mobTick` writes the guard field. `falling` has no other source-tree read or write in this class.
- Parent slices / dependencies / closure evidence: local-player callback and writer are source-traced. No velocity, input, movement call, collision query, or jump result is changed by this field; only sound playback is gated.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): outside player movement scope; the new boolean changes only whether an Elytra sound instance is played on a synchronized flag change.
- Finding IDs or checked absence/replacement path: checked non-movement effect; no movement finding.

### Slice LIVING-WATER-FRICTION-BASE-VALUE: player water drag accessor

- Inventory ID(s): `INV-TICK`, `INV-MODIFIERS`
- Exact behavior boundary and enclosing guards/order checked: A's water travel branch initializes horizontal drag `f` to `0.8F`; B obtains the same value from `getBaseMovementSpeedMultiplier()` before applying the same Depth Strider interpolation and velocity multiplications.
- A evidence: `net/minecraft/entity/living/LivingEntity.java`, full-file SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`; water branch lines 1430-1455 uses `float f = 0.8F`.
- B evidence: same path, full-file SHA-256 `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`; `getBaseMovementSpeedMultiplier()` lines 1328-1330 returns `0.8F`, and the water branch lines 1462-1491 calls it for `f` before the unchanged drag operation order.
- State producers/writers -> consumers/readers: the inherited accessor supplies the base water drag; only `PolarBearEntity` overrides it in B's source tree. Neither `PlayerEntity` nor either client-player class overrides it; the player receives the same `0.8F` value on both sides.
- Parent slices / dependencies / closure evidence: source search and exact source hashes `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` (A `PlayerEntity`) and `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302` (B `PlayerEntity`) confirm the same player override and superclass dispatch. A's water branch loads literal `0.8F`; B's exact revised bytecode calls `getBaseMovementSpeedMultiplier()`, whose base body returns `0.8F`. The only B override in the source tree is `PolarBearEntity`, outside player scope. `PlayerEntity` and both local-player classes do not override the accessor. The adjacent gravity gate remains separately tracked as F002.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for the direct player class chain, the old literal and new accessor both yield `0.8F`; the remaining Depth Strider arithmetic and multiplication sequence is unchanged. No player water-drag difference is established.
- Finding IDs or checked absence/replacement path: checked accessor replacement; only B PolarBear override is non-player and excluded.

### Slice PLAYER-JUMP-AND-FLIGHT-RELATIVE: player jump and flight input overrides

- Inventory ID(s): `INV-TICK`, `INV-JUMP`, `INV-MODIFIERS`
- Exact behavior boundary and enclosing guards/order checked: player `jump()` delegates to living jump then records jump/fatigue; `moveRelative()` preserves the flying-and-not-riding guard, fly-speed multiplier, inherited relative acceleration, vertical velocity `* 0.6`, speed restore, fall-distance reset, shared flag clear, and movement-stat update.
- A evidence: `net/minecraft/entity/living/player/PlayerEntity.java`, full-file SHA-256 `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`; `jump()` lines 1361-1369 and `moveRelative(float,float)` lines 1372-1390. `net/minecraft/entity/living/LivingEntity.java`, SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`; `getJumpStrength()` lines 1275-1277 returns `0.42F`; `jump()` lines 1279-1300.
- B evidence: `net/minecraft/entity/living/player/PlayerEntity.java`, full-file SHA-256 `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302`; `jump()` lines 1380-1388 and `moveRelative(float,float)` lines 1391-1409. `net/minecraft/entity/living/LivingEntity.java`, SHA-256 `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`; `getJumpStrength()` lines 1301-1303 returns `0.42F`; `jump()` lines 1305-1330.
- State producers/writers -> consumers/readers: `LivingEntity.mobTick` applies its jump gate to `PlayerEntity.jump`; player flight ability and sprint state guard `PlayerEntity.moveRelative`; both methods call the same corresponding `LivingEntity` methods and retain the same operation order.
- Parent slices / dependencies / closure evidence: A/B source bodies were compared line-for-line; `javap -c -p` over the exact immutable revision jars confirms the same jump calls, constants, branches and operation order in `PlayerEntity.jump`, `PlayerEntity.moveRelative`, and `LivingEntity.jump`. D1 is closed for these bounded members. `LivingEntity.moveRelative` has its own conditional findings/slice and remains separate.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the paired source bodies and relevant bytecode agree. This slice establishes no release difference for the player-specific jump wrapper or flight-specific relative-movement wrapper; other parent movement methods remain open.
- Finding IDs or checked absence/replacement path: checked no difference; continuation is the separately tracked `LIVING-FLUID-NO-GRAVITY` slice and open broader `LivingEntity.moveRelative` review.

## Dependency queue and blockers

- D0 — source provenance: resolved from both provenance JSONs; full original Gradle stdout, JVM vendor/options and named preparation-owner identity were not retained. The excerpt hashes, exact command, worktree, mapping mode, tool versions and ready markers are recorded above.
- D1 — resolved for the exact published movement-diagnostics lists: the listed `LivingEntity.jump`, `LivingEntity.moveRelative`, their `mobTick` call sites, `PlayerEntity.jump`, and `PlayerEntity.moveRelative` bodies/call chains were checked against the exact revised immutable jars; source ranges and hashes are recorded in their bounded slices/findings. The only other diagnostic match is `Entity.removeEntity` (A line 1838; B line 1880), which removes an entity from the world and does not read or write player movement state. Preserve the revised-artifact provenance limit: original derived jars are unavailable and byte identity is unproven.
- D-ART — resolved for source evidence: locally verified both immutable `feather-r1-2026-10-07` jars, source manifests and raw inputs; independent operations audit passed. Preserve the explicit limitation that unavailable original derived jars are not proven byte-identical. Ready markers remain untouched; mutable cache jars are not evidence.
- D2 — resource closure: once movement consumers identify tags/defaults/equipment/effects, check matching versioned client-jar entries and hash cited resources; identify synchronized/external data boundaries.
- D3 — transitive movement-state writers/callers: enumerate after all navigation stages, revisit unchanged callers when a dependency changes.

## Finding index

F001 - [farmland player collision height](findings/F001-farmland-collision-height.md), source-confirmed. F002 - [generic no-gravity state gates fluid travel gravity](findings/F002-no-gravity-fluid-travel.md), source-confirmed for a server-loaded player whose saved `NoGravity` flag is true. F003 - [local-player auto-jump input](findings/F003-local-player-auto-jump.md), source-confirmed when B's auto-jump option and geometry/state guards pass. Prior pair reports are historical hints only, not coverage evidence.

## Resume checkpoint

- Checkpoint date/time: 2026-10-08 09:44 UTC
- Worktree: `C:\Users\Wolfi\.codex\worktrees\movement-source-1-9-4-1-10-2\LegacyParkourCompat`; branch: `feat/source-discovery-movement-source-1-9-4-1-10-2`; prior source-evidence checkpoint before this resume-index update: `dcd8b8e69f96ececae8b724d4a0ac53dfe2f2feb`.
- Default-branch integration: `main` is `002137b227676caea77f6832b9f4c8d0b6200bff`; merge reported `Already up to date`. No default-branch commits landed after the recorded base.
- Last completed slices: `BLK-RAIL-OUTLINE-COLLISION`, `PLAYER-JUMP-AND-FLIGHT-RELATIVE`, and `LIVING-WATER-FRICTION-BASE-VALUE` compared-no-difference; `BLK-FARMLAND-COLLISION`, `LIVING-FLUID-NO-GRAVITY`, and `LOCAL-AUTO-JUMP-INPUT` have source-confirmed findings; `LOCAL-FALL-FLYING-SOUND-STATE` is not applicable to player movement.
- Last resumed work: revalidated the exact ready JSON IDs and immutable Feather revision records; checked current `LivingEntity.moveRelative`, `LivingEntity.mobTick`, player override/accessor dispatch, and targeted bytecode for both revision jars. D1 is resolved for the published diagnostics list; F001–F003 remain source findings awaiting reviewer decisions. Broader inventories are still open.
- Next action: continue the S1 local-player tick/input inventory and close each newly discovered producer/consumer dependency into a bounded slice; then proceed through the required state, collision, world, modifier and external inventories. No pair freeze or implementation handoff is claimed.
- Exact resume commands (PowerShell from the worktree root):
  1. `python workflows/movement-discovery/check_completion.py workflows/source-campaign-2026-10-07/1.9.4--1.10.2/`
  2. `Get-Content 'D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.9.4\ornithe-feather\net\minecraft\entity\living\LivingEntity.java' | Select-Object -Skip 1301 -First 169`
  3. `Get-Content 'D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.10.2\ornithe-feather\net\minecraft\entity\living\LivingEntity.java' | Select-Object -Skip 1331 -First 175`
  4. `javap -c -p -classpath 'D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\revisions\derived-artifact-snapshots\feather-r1-2026-10-07\1.9.4\ornithe-feather\client-ornithe-feather.jar' net.minecraft.entity.living.LivingEntity`
  5. `javap -c -p -classpath 'D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\revisions\derived-artifact-snapshots\feather-r1-2026-10-07\1.10.2\ornithe-feather\client-ornithe-feather.jar' net.minecraft.entity.living.LivingEntity`
- Continue with S1 local-player input/tick ordering, then route each discovered caller and state writer into the bounded S1–S7 coverage slices; do not inspect implementation or wiki material before full-pair freeze.
- Outstanding dependencies: D2–D3 and full-pair independent source audit; D1 and D-ART are resolved for the recorded evidence, with the original-derived-jar limitation preserved.
- Assumptions requiring verification: although the current movement-diagnostics list is reviewed, the broader per-tick call graph and transitive state-writer dependencies remain open; resource/state-writer inventories and full paired tick coverage are incomplete. Revised-artifact byte identity to unavailable original derived jars is unproven.

## Finding snapshots (not pair freeze)

### Snapshot `FS-1.9.4-1.10.2-2026-10-07-r1` — superseded before review

- Findings: F001 and F002; immutable finding snapshot commit `e399f2b306aacde492fd9d5427e9fc5b29acc7b9`.
- Finding-file SHA-256: `findings/F001-farmland-collision-height.md` = `31dd34ef5a227aa139d68a3457faf5ef54dc7071ac098fbadee443eec516bd3c`; `findings/F002-no-gravity-fluid-travel.md` = `36fa38a1414d2007ca7259d17341ed9aabb21f22685b7e9e422cc3d8aaff95db`.
- Event: superseded before reviewer submission to correct the B `World#getCollisions` end line from 984 to 985. No reviewer used this snapshot and no acceptance was claimed.

### Snapshot `FS-1.9.4-1.10.2-2026-10-07-r2` — superseded before review

- Findings: F001 and F002; immutable finding snapshot commit `c4ca5a7c3a6ae275eac8c5c7adaffa428c9a3b93` (F001 correction atop snapshot r1 commit `e399f2b306aacde492fd9d5427e9fc5b29acc7b9`).
- Finding-file SHA-256: `findings/F001-farmland-collision-height.md` = `f2f3af8b4d5efcf9c1a208fc84b57a4cd39938a3ae97f6235598f26431115a78`; `findings/F002-no-gravity-fluid-travel.md` = `36fa38a1414d2007ca7259d17341ed9aabb21f22685b7e9e422cc3d8aaff95db`.
- Event: superseded before reviewer submission to clarify that the unloaded-client fallback uses the separate `y > 0 ? -0.1 : 0.0` assignment and is not gated by `NoGravity`. No reviewer used this snapshot and no acceptance was claimed.

### Snapshot `FS-1.9.4-1.10.2-2026-10-07-r3` — superseded before reviewer confirmation

- Findings: F001 and F002; immutable finding snapshot commit `163841261802b34cbb2b6eb9c607e1baef6bb468` (F002 fallback clarification atop snapshot r2 commit `c4ca5a7c3a6ae275eac8c5c7adaffa428c9a3b93`).
- Finding-file SHA-256: `findings/F001-farmland-collision-height.md` = `f2f3af8b4d5efcf9c1a208fc84b57a4cd39938a3ae97f6235598f26431115a78`; `findings/F002-no-gravity-fluid-travel.md` = `6a26dc88f71e6ab1e7c79d1e59e4db003a444253d98b924971f39df50559805d`.
- Exact A/B artifact-manifest and source identities: A artifact manifest `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`, source manifest `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; B artifact manifest `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116`, source manifest `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`.
- Revised mapped artifacts: revision `feather-r1-2026-10-07`; A immutable jar SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`; B immutable jar SHA-256 `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`. Revision JSON hashes: A `df0a26fd4c65292530cdad638e6789cc26fcd47875fe1c3eb2daef6de7e3f915`; B `79b5688af6f5cf6eb5c006a867de841de0033b42408c8461283cd9c4b9389856`. Operations audit passed. Both source trees/raw inputs match original manifests; original derived-jar equivalence is unproven.
- Verified paired boundary and source evidence: F001 is present in A 1.9.4 and differs in B 1.10.2 at farmland collision dispatch. F002's B `NoGravity` gates cover water/lava `-0.02` and the ordinary loaded-client/server `-0.08` subtraction; the unloaded-client fallback remains `y > 0 ? -0.1 : 0.0`. Saved player NBT can set the flag and the server syncs it to that player's client. A has no generic player flag. These establish the endpoint difference for this pair only.
- Closed finding dependencies: F001 paired farmland shape dispatch through `StateDefinition`, `Block`, `World.getCollisions`, and `Entity.move`; F002 paired `LivingEntity.moveRelative` body plus B saved-player NBT → synced data → owning-player packet → client entity path, with exact unloaded-client fallback disposition. D0 and D-ART are resolved for source evidence. Report-wide D1–D3 and open navigation inventories remain open.
- Independent blind reviewer: reference `374cc5db26e60dff443e9593e8d288a49907eebc` independently verified the 1/16 farmland collision-height delta, registration/player-movement chain, revised Feather JARs, and revision identities. This review identified that the frozen F001 text still described the result as provisional pending artifact repair, despite the operations audit being complete. This r3 snapshot is superseded by corrected F001 text in r4; no acceptance is claimed for r3.
- Timestamp: 2026-10-07 16:09 UTC. Pair status at finding commit: `active`; report checkpoint `1de64ada358f42ae12034ccc34444e187c8d70fb`; pair complete: no.
- Implementation handoff: `blocked` pending independent blind review acceptance of r3; this snapshot is superseded by r4. The pair remains active while remaining slices and the full-pair audit are unfinished.

### Snapshot `FS-1.9.4-1.10.2-2026-10-07-r4` — submitted, reviewer confirmation pending

- Findings: F001 and F002; immutable finding snapshot commit `c1799749fdef26e706aea1aef8150d5b72458ecf` (corrected F001 source-confirmed handoff atop snapshot r3 commit `163841261802b34cbb2b6eb9c607e1baef6bb468`).
- Finding-file SHA-256: `findings/F001-farmland-collision-height.md` = `8a8b68a4e70c44d666a55caaeff6a077fece5b7382f77d5d082cf1cca10c07c6`; `findings/F002-no-gravity-fluid-travel.md` = `6a26dc88f71e6ab1e7c79d1e59e4db003a444253d98b924971f39df50559805d`.
- Exact A/B source and artifact identities: A source manifest `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`, artifact manifest `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`; B source manifest `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`, artifact manifest `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116`.
- Revised mapped artifacts: revision `feather-r1-2026-10-07`; A immutable jar SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`, revision.json SHA-256 `df0a26fd4c65292530cdad638e6789cc26fcd47875fe1c3eb2daef6de7e3f915`; B immutable jar SHA-256 `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`, revision.json SHA-256 `79b5688af6f5cf6eb5c006a867de841de0033b42408c8461283cd9c4b9389856`. Independent operations audit passed. Source trees and raw inputs match their original manifests; original derived-jar byte identity remains unproven.
- Finding status and scope: F001 is source-confirmed for the paired releases; the independent reviewer reference `374cc5db26e60dff443e9593e8d288a49907eebc` verified the 1/16 farmland collision-height delta and registration/player-movement chain. F002 remains as frozen in r3. No releases outside this pair were assessed, and original-derived-jar equivalence remains unproven.
- Independent blind reviewer: review reference `374cc5db26e60dff443e9593e8d288a49907eebc`; confirmation of this corrected r4 snapshot is pending. No acceptance is claimed yet.
- Timestamp: 2026-10-07 17:14 UTC. Pair status at finding commit: `active`; report checkpoint `1de64ada358f42ae12034ccc34444e187c8d70fb`; pair complete: no.
- Implementation handoff: `blocked` pending reviewer confirmation of this corrected snapshot. Remaining slices and the full-pair audit are unfinished.

### Snapshot `FS-1.9.4-1.10.2-2026-10-07-auto-jump-r1` — submitted, review pending

- Finding: F003; immutable finding snapshot commit `4ffe7bcc101f17fdc5f5d6edcc26a96ea6cce910`.
- Finding-file SHA-256: `findings/F003-local-player-auto-jump.md` = `bcf01c530efa874168cfcfe347770859e399f967c42e5b381a492fbb72d4ccc3`.
- Exact A/B artifact-manifest and source identities: A artifact manifest `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`, source manifest `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; B artifact manifest `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116`, source manifest `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`.
- Revised artifacts: revision `feather-r1-2026-10-07`; A immutable jar SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`; B immutable jar SHA-256 `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`. Both source trees/raw inputs match their original manifests; original derived-jar equivalence is unproven.
- Verified paired boundary and evidence: B `LocalClientPlayerEntity.move` invokes obstacle checks and schedules a jump input; A has no corresponding local-player move override, auto-jump helper, or timer in its class or client-entity source tree. B's synthetic input reaches the same-call `LivingEntity.mobTick` jump consumer. B local-player bytecode and the A class absence path were inspected on the exact immutable jars.
- Closed finding dependencies: B option/input producer, movement call into virtual `Entity.move`, local move override/helper and geometry guards, timer writer/consumer, `serverTickAi` copy, `LivingEntity.mobTick` jump branch, and virtual `PlayerEntity.jump`; the paired A absence and ordinary consumer were checked. D0 and D-ART are resolved for source evidence. Report-wide D1–D3 and wider inventories remain open.
- Independent blind reviewer: pending assignment. Decision: pending; no acceptance is claimed. Review basis requested: eligibility guards, input/state reachability, paired source and bytecode evidence, exact hashes, and artifact-revision limitation.
- Timestamp: 2026-10-07 16:04 UTC. Pair status at finding commit: `active`; pair report checkpoint was `f52d696cab43c3d77b0384843a5e688a8b00a880`; pair complete: no.
- Implementation handoff: `blocked` pending independent blind review acceptance of this exact snapshot. Full-pair discovery and audit remain open.

### Snapshot `FS-1.9.4-1.10.2-2026-10-08-character-key-r1` — submitted, review pending

- Finding: F004; immutable finding snapshot commit `0202863998a8c6d9a3ff6555235f2add358eca1d`.
- Finding-file SHA-256: `findings/F004-character-key-stale-movement-input.md` = `2AA3AC152B00EB0B3EB19F29D133227D3968A8534DBF69E2EE583B502D650282`.
- Exact A/B artifact/source identities: A artifact manifest `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`, source manifest `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; B artifact manifest `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116`, source manifest `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`.
- Revised mapped artifacts: revision `feather-r1-2026-10-07`; A immutable jar SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`, revision JSON `df0a26fd4c65292530cdad638e6789cc26fcd47875fe1c3eb2daef6de7e3f915`; B jar `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`, revision JSON `79b5688af6f5cf6eb5c006a867de841de0033b42408c8461283cd9c4b9389856`. Original derived-jar byte identity remains unproven.
- Verified paired boundary: Controls supports character-code movement bindings; event processing writes the binding after screen handling; ChatScreen closes through `openScreen(null)`; active non-Mac focus reacquisition invokes `setAll()` without `releaseAll()`. A catches the out-of-range LWJGL poll and preserves state; B clears codes at least 256. `KeyboardInput.tick()` consumes that state into camera-player movement axes. The release-before-next-sample condition is explicit. No runtime validation or trajectory claim.
- Independent blind reviewer: pending assignment; decision pending and no acceptance is claimed. Review F004 as this exact immutable snapshot; implementation handoff remains blocked pending review.
- Timestamp: 2026-10-08 11:26 UTC. Pair status at finding commit: `active`; pair complete: no.
## Implementation reconciliation

- Reconciliation status: pending
- Repository revision inspected: none; blind-discovery freeze has not occurred
- Finding -> implementation disposition/evidence: pending
- Existing implementation without a frozen source finding: pending
- Coverage gaps routed back to discovery slices: pending

## Independent source audit

- Reviewer: pending assignment; must differ from discovery author
- Status: pending
- Inventories and call-chain ranges re-walked: none
- Concrete missed-slice routes (or `none found`): pending
- Misses routed to slice/finding IDs and owners: pending
- Reviewer evidence / date: pending

## Source audit closure

- Coverage counts: 3 compared-no-difference; 4 source-confirmed findings submitted for review (0 accepted); 1 not-applicable; 0 blocked; 50 initial planned behavior slices pending.
- Pending bounded-slice count: 50 initial planned behavior slices remain; revise upward whenever source navigation exposes additional distinct methods, writers, consumers or dependencies.
- Unresolved gaps: inventories and the full-pair audit remain open; movement decompiler diagnostics (D1), resource closure (D2), and transitive state-writer/caller closure (D3) remain open. The source-provenance record and revised-artifact integrity checks are complete, with original derived-jar equivalence unproven.
- Evidence/hash/correspondence audit: partial; F001–F004 source and revised immutable-artifact evidence and rail hashes are recorded. Independent operations verification passed; independent finding review remains pending.
- Runtime validation: not performed (separate workflow).
