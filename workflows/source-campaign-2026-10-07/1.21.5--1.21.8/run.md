# Discovery: 1.21.5 to 1.21.8

- Status: active
- Scope: exact adjacent content-update pair; older A = 1.21.5; newer B = 1.21.8. Direct client-player movement only. Excludes health, regeneration, hunger, food, saturation, exhaustion and damage/combat systems and their producers. An in-scope movement predicate may consume vanilla state from those systems; compare the consumer logic for supplied values without emulating or deriving findings from producer-system changes. Excludes independent non-player movement and historical behavior for modern-only blocks/states.
- Repository revision and start date: source branch created from `main` at `002137b227676caea77f6832b9f4c8d0b6200bff`; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: A and B are exact-release Mojmap / `mojmap`; pair alignment is confirmed by exact 1.21.5 and 1.21.8 marker version IDs and Mojmap source paths. Campaign policy selects Mojmap from 1.14.4 onward.
- Source preparation owner / command / log / readiness marker: shared source owner; A and B exact-release readiness records, hashes, commands and successful logs are verified below. No local decompilation run.
- Toolchain/decompiler/remapper versions and options: A and B verified in their provenance records below; both use Gradle 9.7.1, Java 25.0.3+9-LTS, Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1, ASM 9.10.1, Gson 2.14.0 and a 4G decompiler heap.
- Discovery author(s): Codex source-only worker for this pair.
- Independent reviewer (must differ from discovery authors): not yet assigned/recorded.
- Track declaration: source-only; no old mod implementation, no Minecraft Wiki/MCPK browsing or wiki-audit output, no tests/gameplay/TAS/Gym/server/Docker launches. Runtime validation is not authorized.

## Artifact manifest

### A — 1.21.5

- Requested/resolved release and metadata ID: `1.21.5` / `1.21.5`, verified in `ready/1.21.5/mojmap.ready.json`. Namespace/CLI mode: Mojmap / `mojmap`.
- Source root: `build/movement-campaign-2026-10-07/ready/1.21.5/mojmap/`; 5,921 files, 27,311,391 bytes per ready record. Readiness JSON SHA-256: `5f65fb143209abcc6e28e77a7150aecefdbc9de00b6ef3fc924e53bc0e1a667e`.
- Client jar SHA-256: `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11`; release-specific Mojang `1.21.5/client_mappings.txt` SHA-256: `3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5`; Mojmap remapped jar SHA-256: `124561e91a61714ca5a73495784c14c03a7a927d1f715eafbcb56ae115a6712a`; version metadata SHA-256: `4a9a484109ee5c3f430b685ddc719712e962e8d06c761cd58ec0a1e6fea2f18c`.
- Source manifest `ready/1.21.5/mojmap.sources.sha256` SHA-256 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`; artifact manifest `ready/1.21.5/artifacts.sha256` SHA-256 `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656`; movement diagnostics `ready/1.21.5/movement-diagnostics.txt` SHA-256 `d43c0fdfc627492b043962c0379b744461ebbb6b2ffb24f326fe72a63a039755`. Each file hash matches the readiness JSON. Source manifest entries and on-disk hashes were verified for KeyboardInput, ClientInput, LocalPlayer, ClientPacketListener, AbstractClientPlayer, Player, LivingEntity, Entity, Input and Vec2.
- Preparation command: `.\gradlew.bat decompileMinecraft --versions=1.21.1,1.21.3,1.21.4,1.21.5 --mappings=mojmap --decompiler-heap=4G --output-root=<shared staging root> --cache-directory=<shared artifacts root>`; exact owner run and full successful log: `build/movement-campaign-2026-10-07/staging/mojmap-1.21.1-to-1.21.5-cd5a99cb1024417c9d370097c886a131/gradle.full.log`.
- Toolchain: Gradle 9.7.1, JDK 25.0.3+9-LTS, Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1, ASM 9.10.1 and Gson 2.14.0. The log reports one class access repair and zero members; repeated-processing notices are in non-movement PalettedContainer/CarvingMask lambdas; task succeeded. Targeted diagnostics include player/input/travel/move entry methods; no relevant-body damage is reported there. Slice owners still verify each cited body.
- Pair alignment uses exact release markers and the shared Mojmap namespace. Method correspondence and semantics are resolved slice by slice below.

### B — 1.21.8

- Requested/resolved release and metadata ID: `1.21.8` / `1.21.8`, verified in `ready/1.21.8/mojmap.ready.json`. Namespace/CLI mode: Mojmap / `mojmap`.
- Source root: `build/movement-campaign-2026-10-07/ready/1.21.8/mojmap/`; 6,105 files, 27,900,784 bytes per ready record. Readiness JSON SHA-256: `2e3bf302503eb127061cc605797b390f5f4a4b508f09350a59d78ced857ba7d7`.
- Client jar SHA-256: `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f`; release-specific Mojang `1.21.8/client_mappings.txt` SHA-256: `dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca`; version metadata SHA-256: `3bf5cdd444dfa89199fe4f8a92b6c79f4e4fa8977ff52029c3acd99378506fad` (all entries verified in the artifact manifest).
- Source manifest `ready/1.21.8/mojmap.sources.sha256` SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`; artifact manifest `ready/1.21.8/artifacts.sha256` SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`; movement diagnostics `ready/1.21.8/movement-diagnostics.txt` SHA-256 `c954da48013372c7b423bf47597b447d32607b2c39b00e3cf353b1d5fcc95806`. Each hash matches the readiness JSON. Source-manifest entries and on-disk hashes verified for Player, LivingEntity, Entity, EntityDimensions, Pose, Attributes and EntityAttachments; input/tick source hashes are being verified per slice.
- Preparation command: `.\gradlew.bat decompileMinecraft --versions=1.21.8,1.21.10,1.21.11 --mappings=mojmap --decompiler-heap=4G --output-root=<shared staging root> --cache-directory=<shared artifacts root>`; exact owner run and successful log: `build/movement-campaign-2026-10-07/staging/mojmap-1.21.8-to-1.21.11-9a8c76419cd14660b88f9fc90225e127/gradle.full.log`.
- Targeted diagnostics report successful exact 1.21.8 decompilation, verified metadata ID and required Entity/LivingEntity/Player/LocalPlayer sources; indexed movement bodies include Entity.move, LivingEntity.jumpFromGround/travel/aiStep, Player.aiStep/travel and LocalPlayer.aiStep/move. Diagnostics are an index only; slice owners verify each cited body.

Pairwise source comparison is active. Both source-owner publications cite exact requested/resolved IDs and validated SHA-256 source/artifact manifests; movement diagnostics are indexes, with behavior established only by the slice evidence below.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: none; discovery is active.
- Evidence inventory and finding IDs included at freeze: none; source A is available and partially inventoried, but B and the source-only pair comparison are not yet available.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed. One prior source discovery report was read only for navigation leads; none of its findings, hashes or equivalence claims are carried into this run.
- Source/mapping hashes covered by freeze: none yet.

## Correspondence and call order

A-side inheritance is verified: `LocalPlayer` extends `AbstractClientPlayer` (`LocalPlayer.java:93`, SHA-256 `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`); `AbstractClientPlayer` extends `Player` (`AbstractClientPlayer.java:17`, SHA-256 `971bc296fe1feeb3fc54a2b78c0a0a6d0df2578fb79428c451f25be57c20a648`); `Player` extends `LivingEntity` (`Player.java:120`, SHA-256 `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036`); `LivingEntity` extends `Entity` (`LivingEntity.java:138`, SHA-256 `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`); `Entity` is the root movement base (`Entity.java:143`, SHA-256 `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`).

A-side tick path: `LocalPlayer#tick()` lines 191-217 -> inherited `Player#tick()` lines 271-348 -> `LivingEntity#tick()` lines 2460-2572 -> `Entity#tick()`/`baseTick()` lines 438-463, then `LivingEntity.tick` virtual `aiStep()` lines 2499-2501. Dynamic `LocalPlayer#aiStep()` lines 683-835 calls `Player#aiStep()` lines 554-607, which calls `LivingEntity#aiStep()` lines 2669-2804. That method dispatches `applyInput()` (line 2712), jump (2724-2749), then `travel` / `travelRidden` (2752-2767). `LocalPlayer#applyInput()` lines 608-621 supplies its camera-controlled path; `Player#tick()` updates underwater state before `super.tick()` and player pose after it. This is an older-side call index only; B correspondence remains pending.

Prior-run navigation-only leads to re-resolve include LocalPlayer input/tick, KeyboardInput/ClientInput, LivingEntity travel, Entity movement/collision, Player support/edge handling, powder-snow callbacks, movement effect/enchantment consumers and client correction handlers. These are not pair evidence or an exhaustive inventory.

## Required source inventories

- `INV-TICK` input sampling, local-player tick, pre-travel predicates/writes, travel dispatch/branches and post-travel: status=pending; slice_ids=S1.1,S1.2,S1.3,S1.4,S3.1,S3.2,S3.3,S3.4,S3.5; evidence=A exact source marker and selected tick/input bodies/hash entries verified; full branch inventory pending, B publication pending.
- `INV-STATE` movement-state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, support, timers and direct predicates: status=pending; slice_ids=S2.1,S2.2,S2.3,S2.4; evidence=A/B exact source manifests verified; paired S1.1-S1.4 input/tick, S2.1 pose/dimension/scale, S2.2 core movement setter, and S3.2/S3.5 movement-state consumer subsets inventoried; remaining producer/consumer inventory pending.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S4.1,S4.2,S4.3,S4.4,S5.2,S5.4; evidence=A exact source tree ready/hash-verified with pose-fit query entry inventoried; collision/shape inventory pending, B publication pending.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, resource data/tags and defaults: status=pending; slice_ids=S5.1,S5.2,S5.3,S5.4; evidence=A exact source/artifact manifest verified; behavior/resource inventory pending, B publication pending.
- `INV-MODIFIERS` direct movement attributes, effects, enchantments, equipment and application/removal/conditions: status=pending; slice_ids=S6.1,S6.2,S6.3; evidence=A exact source/artifact manifest verified; producer/consumer and resource inventory pending, B publication pending.
- `INV-EXTERNAL` player-only external movement inputs and client consumers including corrections, pushes, pistons and mounts: status=pending; slice_ids=S7.1,S7.2,S7.3; evidence=A exact source tree ready/hash-verified; external consumer/writer inventory pending, B publication pending.
- `INV-EXCLUSIONS` scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=awaiting source-backed disposition of direct vanilla-state reads and excluded producer systems.

## Coverage ledger

Every row below is an unfinished discovery unit, not a claim that a method has been checked. Exact A/B methods, body ranges and hashes will be added only after validated readiness records. Split or add rows whenever source inventory reveals narrower behaviors or new dependencies.

### Slice S1.1: input sampling and vector shaping

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: keyboard sampling through input-record packet update, movement-vector normalization/shaping and transfer into local movement impulses; shift-state packet transport also compared.
- A evidence: `KeyboardInput#tick()`, lines 23-36, SHA-256 `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3`; `ClientInput#getMoveVector()`, lines 13-15, SHA-256 `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`; `Input` record/default, lines 6-39, SHA-256 `1c0c12af07da1f90651c3a7c4eb19bab6486b6391dcbfdd2926f06e3f36c2f06`; `Vec2#normalized()`, lines 48-51, SHA-256 `b637ef6899de5d8892e19273b114ffaf0397761e509170a56d52dd068f54bffe`; `LocalPlayer#applyInput()`, `modifyInput(Vec2)` and square-movement shaping, lines 608-658, LocalPlayer SHA-256 `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`. A `LocalPlayer#tick()` sends the separate shift command after `super.tick()` (lines 191-217,269-280); server command handling updates shift only under `hasClientLoaded()` and resets action time (ServerGamePacketListenerImpl lines 1555-1565, SHA-256 `fdb8726f77618ebcfd4fe3973e1740d999c2fd3d220ca4a3ddba837f160e6d69`). Input assignment occurs through `ClientPacketListener#handleLogin()` / `handleRespawn()`, source hash `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617`. In A `ClientPacketListener#handleRespawn()` lines 1181-1182 seeds the replacement LocalPlayer with prior shift/sprint booleans and line 1216 installs a new `KeyboardInput`; `lastSentInput` defaults to `Input.EMPTY`.
- B evidence: the KeyboardInput, ClientInput, Input and Vec2 source hashes are identical to A. B LocalPlayer source hash `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee`; `applyInput`, `modifyInput` and square-movement shaping remain lines 608-658 with unchanged bodies. B sends the changed Input record after `super.tick()` (LocalPlayer lines 197-205); server `handlePlayerInput` updates shift only under `hasClientLoaded()` and resets action time (ServerGamePacketListenerImpl lines 390-396, SHA-256 `100f83ae1f264dd26170147cf1319f78b7bf837d5b1b5603934b414cde63a16d`). B input assignment source is `ClientPacketListener.java`, SHA-256 `a66556678a93c63c0ceb8953f25261b82bf3bb1971048fbc15b177c6dffea8cd`. B `ClientPacketListener#handleRespawn()` lines 1230-1231 seeds the replacement LocalPlayer with `old.getLastSentInput()` and line 1265 installs a new `KeyboardInput`; `LocalPlayer` stores the constructor argument only as `lastSentInput`. For the cross-dimension respawn path, `ServerPlayer#teleport` sends keep-all-data `(byte)3` and continues with the same ServerPlayer instance; the server input handler stores the received Input in `lastClientInput`, with no transfer reset found. `getLastClientMoveIntent()` consumers in both source trees are the old/new minecart behaviors; vehicle physics is outside scope.
- State producers/writers -> consumers/readers: A/B keyboard writes the same keyPresses and normalized moveVector. LocalPlayer reads/ticks them in the same AI order and maps the same vector to xxa/zza/jumping. Shift transport differs, but it is sampled from the same record after the local superclass tick; both server paths use the same loaded-player guard and update the movement-facing crouch state/reset action time. The respawn constructor argument differs (`Input.EMPTY` in A versus the prior `lastSentInput` in B), which can change whether an initial input packet is sent, but direct local movement still samples a fresh KeyboardInput in both. The server's remembered move-intent consumers are minecart behaviors; no direct player movement consumer was found.
- Parent slices / dependencies / closure evidence: source dependencies DEP-SRC-A and DEP-SRC-B.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): movement vector, local input shaping and movement-impulse writes match. Shift packet carrier changed, but the local send boundary and server-side shift update guard/consumer match. Respawn now preserves last-sent input solely for packet-difference suppression; it does not feed local movement-vector generation. This can change the value retained for vehicle move intent after transfer, but all `getLastClientMoveIntent()` consumers are minecart movement paths outside scope. No direct player movement difference was found for this slice.
- Finding IDs or checked absence/replacement path: none; S1.1 checked-no-difference.

### Slice S1.2: local-player tick and pre-travel ordering

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: local-player/base/player/living tick chain through input, jump and player travel dispatch, including direct pre-travel state writes.
- A evidence: `LocalPlayer#tick()` lines 191-217 and `LocalPlayer#aiStep()` lines 683-835, SHA-256 `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`; `Player#tick()` lines 271-348 / `Player#aiStep()` lines 554-607, SHA-256 `8fc187f33999db9dfc49251e95e92a17645a50ad16ca0f93f4948209f62036`; `LivingEntity#tick()` lines 2460-2572 / `LivingEntity#aiStep()` lines 2669-2804, SHA-256 `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`; `Entity#tick()` / `baseTick()` lines 438-463, SHA-256 `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`.
- B evidence: `LocalPlayer#tick()` starts at line 197 and its `aiStep()` remains lines 683-835; LocalPlayer SHA-256 `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee`. `Player#tick()` / `aiStep()` start at lines 281 / 564, Player SHA-256 `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987`. `LivingEntity#tick()` / `aiStep()` start at lines 2512 / 2721, LivingEntity SHA-256 `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8`. `Entity#tick()` / `baseTick()` start at lines 468 / 472, Entity SHA-256 `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704`.
- State producers/writers -> consumers/readers: Both retain `LocalPlayer.tick -> Player.tick -> LivingEntity.tick -> Entity.tick/baseTick -> dynamic LocalPlayer.aiStep -> Player.aiStep -> LivingEntity.aiStep`; base state initialization precedes the dynamic AI call, which dispatches input, jump and travel. The only added LivingEntity travel guard is `&& isEffectiveAi()` in B, but Player's `canSimulateMovement()` and `isEffectiveAi()` overrides have identical bodies in A/B, so the new predicate is redundant for direct player travel. The LocalPlayer.aiStep and Player.aiStep bodies are identical. B baseTick also changes fire-timer cleanup, excluded producer/damage behavior.
- Parent slices / dependencies / closure evidence: S1.1; DEP-SRC-A; DEP-SRC-B.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): player tick ordering and player travel gating remain equivalent; the new generic AI gate resolves true under the same Player overrides on the reachable direct-player path. Shift-state transport is dispositioned in S1.1.
- Finding IDs or checked absence/replacement path: none; S1.2 checked-no-difference.

### Slice S1.3: jump, sprint timing and direct eligibility predicates

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: jump dispatch and sprint timers/start-stop eligibility predicates, comparing movement consumers for the same supplied vanilla values.
- A evidence: `LocalPlayer#aiStep()` lines 683-744; `shouldStopRunSprinting()` / `shouldStopSwimSprinting()` lines 837-852; `canStartSprinting()` / `vehicleCanSprint()` / `hasEnoughFoodToSprint()` lines 1065-1083; LocalPlayer SHA-256 `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`; `LivingEntity#jumpFromGround()` source belongs to LivingEntity hash in S1.2.
- B evidence: `LocalPlayer#aiStep()` lines 683-744, `shouldStopRunSprinting()` / `shouldStopSwimSprinting()` lines 837-852, and `canStartSprinting()` / `vehicleCanSprint()` / `hasEnoughFoodToSprint()` lines 1065-1083 have unchanged movement predicate bodies, LocalPlayer SHA-256 `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee`; jumpFromGround body unchanged in LivingEntity SHA-256 `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8`.
- State producers/writers -> consumers/readers: LocalPlayer.aiStep and sprint predicates match; they read the same input, timers, water/passenger/item-use/blindness state and direct food-level predicate. The direct food consumer is in scope for supplied values; hunger/food producers remain excluded.
- Parent slices / dependencies / closure evidence: S1.2; S2.3; DEP-SRC-A; DEP-SRC-B.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): exact A/B method bodies match; no change in jump eligibility, sprint timing, or start/stop predicates. Food and hunger producer systems are excluded from claims.
- Finding IDs or checked absence/replacement path: none; S1.3 checked-no-difference.

### Slice S1.4: input modes, flight, auto-jump, unstuck and riding gates

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer input mode selection, post-sampling auto-jump, flight/descent toggles, unstuck path and player-facing passenger/riding gates. Vehicle motion itself is outside the player-physics scope.
- A evidence: `LocalPlayer#aiStep()` input snapshot, auto-jump and flight/riding ranges 683-835, SHA-256 `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`; `ClientInput#makeJump()` lines 21-31, SHA-256 `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`.
- B evidence: `LocalPlayer#aiStep()` lines 683-835 and `ClientInput#makeJump()` lines 21-31 have bodies identical to A, with B hashes `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` and `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`.
- State producers/writers -> consumers/readers: Both snapshot old keyPresses before input.tick, then decrement autoJumpTime and call makeJump after keyboard sampling; mayfly/fall-flying/water-descent, unstuck, and passenger input gates execute in the same order and same bodies. Input producer/shift transport correspondence is recorded in S1.1.
- Parent slices / dependencies / closure evidence: S1.1; S1.2; S2.2; DEP-SRC-A; DEP-SRC-B.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): A/B LocalPlayer.aiStep and ClientInput.makeJump bodies match, so their state read/write ordering and movement gates do not differ. No player movement difference found; vehicle movement is excluded by scope.
- Finding IDs or checked absence/replacement path: none; S1.4 checked-no-difference.

### Slice S2.1: pose, dimensions and eye-height state

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: A/B pose selection order, pose-fit collision query, player pose dimensions, scale application and dimension/eye-height refresh path compared; full state-producer closure remains pending.
- A evidence: `Player#updatePlayerPose()` / `getDesiredPose()` / `canPlayerFitWithinBlocksAndEntitiesWhen(Pose)`, lines 449-481, SHA-256 `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036`; `Player` pose dimension table, lines 137-153, same hash; `LivingEntity#getDimensions(Pose)` lines 3343-3345 and `getScale()` lines 517-520 / `attributeUpdated` lines 1088-1090, SHA-256 `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`; `Attributes.SCALE` registration/default/range/syncable lines 72-74 and LivingEntity attribute-builder inclusion lines 305-324, SHA-256 `14bcc5821d0f67f41fdc3e512556181823cff5c43f84fc1007e51d25f413a3ea`; `Entity#onSyncedDataUpdated` / `refreshDimensions()` lines 2927-2957 and `getEyeHeight(Pose)` / `getEyeHeight()` lines 3008-3014, SHA-256 `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`; `EntityDimensions` lines 6-45, SHA-256 `9fb9575ec615d4aefe6316c443e87a902f44e955393778a4b93623e8b680adc4`; `Pose` enum values/IDs lines 9-30, SHA-256 `85c714072555d18f9bfac2e2a70460bd2c69318e11122e8dbc8c7814a7019fca`; `EntityAttachments` SHA-256 `20ccc3e67c43ec6f1a498fb5d97e33ccfae04adc23af3933d5fbfccf190be173` (standing/crouching vehicle attachment construction at Player.java:136-139,147-150). All cited hashes match A's source manifest.
- B evidence: `Player#updatePlayerPose()` / `getDesiredPose()` / `canPlayerFitWithinBlocksAndEntitiesWhen(Pose)`, lines 459-491, SHA-256 `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987`; `Player` pose table, lines 146-162, same hash; `LivingEntity#getScale()` lines 531-536, `attributeUpdated` scale branch lines 1113-1115 and `getDimensions(Pose)` lines 3398-3404, SHA-256 `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8`; `Attributes.SCALE` lines 75-77, SHA-256 `b941c0556d9ed3c3ce1ffd4c993c9902287a690230e1510696ff13f9efdf2020`; `Entity#onSyncedDataUpdated` / `refreshDimensions()` lines 3111-3142 and `getDimensions(Pose)` lines 3510-3516, SHA-256 `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704`; `EntityDimensions` SHA-256 `9fb9575ec615d4aefe6316c443e87a902f44e955393778a4b93623e8b680adc4`; `Pose` SHA-256 `85c714072555d18f9bfac2e2a70460bd2c69318e11122e8dbc8c7814a7019fca`; `EntityAttachments` SHA-256 `8b55d9113e9742e4a891e53bcd02d45ac7b5ff1b2ae18e0d94b3374e106a266a`. Every cited hash matches B's source manifest.
- State producers/writers -> consumers/readers: A and B source bodies match for desired-pose priority, transition guards/fallback, pose presets, pose-fit AABB shrink/query, sleeping special-case, scale lookup, attribute update refresh and pose-sync refresh (line offsets differ as recorded in A/B evidence). `EntityDimensions.java` and `Pose.java` have identical SHA-256 across releases; `Attributes.SCALE` registration is equal in value/default/range/syncability; `EntityAttachments` source differs elsewhere, while the cited player vehicle-attachment construction is unchanged. Both versions therefore use the same movement-relevant pose/dimension behavior in this slice: sleeping dimensions bypass scale; other LivingEntity dimensions scale player defaults; player fit uses the same epsilon-deflated candidate box; refresh stores dimensions/eye height and reapplies position. B has an additional later `WAYPOINT_TRANSMIT_RANGE` attribute callback after the scale branch, outside this slice. No S2.1 behavior difference found.
- Parent slices / dependencies / closure evidence: S4.1; S4.3; DEP-SRC-A; DEP-SRC-B; DEP-SCALE.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): no difference. Exact A/B bodies and shared immutable `EntityDimensions`/`Pose` hashes show the same reachable player pose priority, collision-fit path, pose presets and scale/refresh behavior; scale updates or synchronized pose changes refresh the dimensions, while sleeping dimensions remain fixed. Body-level ranges and hashes are recorded above.
- Finding IDs or checked absence/replacement path: none; S2.1 checked-no-difference.

### Slice S2.2: movement flags and repeated state writers

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: paired core position, bounding-box, pose, velocity, on-ground, sprint/shift/swimming/shared movement-flag setters; the all-writer producer/consumer closure remains open.
- A evidence: `Entity#setPose`, `setPos`, `setOnGround`, `absSnapTo`, `setShiftKeyDown`, `setSprinting`, `setSwimming`, `setSharedFlag`, `setBoundingBox`, `setDeltaMovement` and `addDeltaMovement`; `LivingEntity#setSprinting` and `setJumping`; Entity SHA-256 `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`, LivingEntity SHA-256 `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`.
- B evidence: the same core setter method bodies match A; Entity SHA-256 `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704`, LivingEntity SHA-256 `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8`. Server player correction receiver methods are also paired unchanged in `ClientPacketListener.java` (A/B hashes and ranges recorded in S7.1).
- State producers/writers -> consumers/readers: core writers preserve field/flag update behavior; A/B server corrections set the server player target then the client `handleMovePlayer` applies relative/absolute position and delta movement, acknowledges teleport, and sends the resulting position in identical order. The distinct B server pre-move collision query is recorded in S4.3. A source scan of every `Player.java` method body containing direct `setDeltaMovement`, `addDeltaMovement`, `lerpMotion` or `move` writes found all paired Player bodies identical (A/B full-file hashes `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036` / `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987`). The only direct-writer method-body difference in `LivingEntity` is its generic `aiStep` guard, already traced as redundant for Player through identical Player overrides; B `travelFlying` has only non-player callers. Environmental flags, support state and external producer/correction routes remain to enumerate.
- Parent slices / dependencies / closure evidence: S1.1-S1.4; S2.1; S3.1-S3.5; S4.1-S4.4; S7.1-S7.3; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): the enumerated movement-state setter bodies are equal; the broad reachable writer/consumer map is incomplete and includes the S4.3 candidate path.
- Finding IDs or checked absence/replacement path: candidate `F-PLAYER-MOVE-VALIDATION-CONTEXT-1.21.5-1.21.8` affects a server-side collision decision before position correction; full state inventory remains open.

### Slice S2.3: direct movement predicates and active-item state

- Inventory ID(s): INV-STATE, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: client sprint-start/stop predicates, active-use and slow-movement state reads, food/Blindness gates, server-side player `canSprint`, and health predicates encountered in player tick. This is a scoped predicate comparison, not closure of every LivingEntity state read.
- A evidence: `LocalPlayer#shouldStopRunSprinting()` / `shouldStopSwimSprinting()` / `hasBlindness()` lines 837-855; `canStartSprinting()` / `vehicleCanSprint()` / `hasEnoughFoodToSprint()` lines 1065-1082; `isMovingSlowly()` line 603 and `isUsingItem()` line 489. `Player#canSprint()` line 2025; `LivingEntity#isDeadOrDying()` line 1108 and `isAlive()` line 1628. Source hashes: LocalPlayer `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`, Player `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036`, LivingEntity as recorded in S1/S3.
- B evidence: corresponding LocalPlayer ranges 837-855, 1065-1082, 603 and 484; Player `canSprint()` line 2040; LivingEntity health predicate methods lines 1140 and 1660. Source hashes: LocalPlayer `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee`, Player `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987`, LivingEntity as recorded in S1/S3. Exact method-body hashes match A/B for both sprint-stop predicates, sprint-start, food and blindness gates, `isMovingSlowly`, `Player#canSprint`, `LivingEntity#isUsingItem`, `isDeadOrDying`, `isAlive`, and `hasEffect`.
- State producers/writers -> consumers/readers: sprint can start only with forward input, enough food or passenger/mayfly allowance, no active item use or Blindness, compatible vehicle, and the same water/fall-flight/slow-movement conditions; sprint-stop guards read the same predicates. `isMovingSlowly()` is crouching or visually crawling. Active-use state is the same local `startedUsingItem` read or LivingEntity synchronized flag read. Food/health/effect producer systems remain excluded; their directly read values are tracked as movement predicates. `Player#aiStep()` also checks health/death for bobbing/interaction work, not for the physical travel calculation. Other direct predicate call sites still need inventory.
- Parent slices / dependencies / closure evidence: S1.1-S1.4; S2.2; S2.4; S3.1-S3.4; S6.2; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): no difference found in the compared local sprint gates, active-item state, player sprint capability, or named health/effect readers. This does not close the full direct-predicate or excluded-producer map.
- Finding IDs or checked absence/replacement path: no movement difference in the compared S2.3 methods; additional predicate call sites remain open.
### Slice S2.4: swimming/crawling pose and flight ability state

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Entity fluid-state refresh precedes swimming refresh in the same per-tick order; player swimming override, glide eligibility, fall-flight flag and player flying-speed consumer.
- A evidence: `Entity#baseTick()` calls `updateInWaterStateAndDoFluidPushing()`, `updateFluidOnEyes()`, then `updateSwimming()` in that order (lines 459-463); `Entity#updateSwimming()` lines 1336-1344; `Player#canGlide()` / `updateSwimming()` lines 1452-1462; `tryToStartFallFlying()` / `startFallFlying()` lines 1507-1518; `LivingEntity#isFallFlying()` line 3268; `Player#getFlyingSpeed()` lines 2030-2037. `Abilities.java` SHA-256 `0aff0ce1dc88540028d7b0e5d40cc37835bdcb8cf67170188c852adfd9a2679e`.
- B evidence: corresponding base-tick order lines 489-493, `Entity#updateSwimming()` lines 1413-1421; `Player#canGlide()` / `updateSwimming()` lines 1464-1474; `tryToStartFallFlying()` / `startFallFlying()` lines 1519-1530; `LivingEntity#isFallFlying()` line 3324; `Player#getFlyingSpeed()` lines 2045-2052. These movement bodies and the `Abilities` speed getters/setters match A. `Abilities.java` SHA-256 `d82af05310709658c7d324632b6bf92afa539322c185078bd649d97e11ffca39`; its only relevant source change replaces NBT save/load methods with `Packed`, `pack()` and `apply()`, preserving the same seven runtime values and defaults.
- State producers/writers -> consumers/readers: base tick refreshes water and eye immersion before updating swimming; sprinting, water/submersion, passenger and flying predicates select the same swimming flag. Player `abilities.flying` gates glide/swimming and selects the same flying-speed branch; `startFallFlying()` writes shared flag 7 and `isFallFlying()` reads it. Pose and dimensions are covered in S2.1; fluid inputs remain open under S5.3/S5.4.
- Parent slices / dependencies / closure evidence: S2.1; S2.2; S3.1; S3.3; DEP-SRC-A; DEP-SRC-B.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the compared player swimming/crawling and fall-flight predicates, update order, runtime ability values, setters and flying-speed consumer match. The Abilities persistence representation was refactored without changing these runtime movement values. Fluid resources and the broader state-writer inventory remain open in their owning slices.
- Finding IDs or checked absence/replacement path: no movement difference in the compared S2.4 paths.
### Slice S3.1: travel dispatch and ground/air movement math

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: player dispatch among fluid/fall-flight/air travel, ground friction lookup, air acceleration and movement update order.
- A evidence: `LivingEntity#travel(Vec3)` / `travelInAir(Vec3)` lines 2213-2247, `handleRelativeFrictionAndCalculateMovement` lines 2394-2404, `Entity#moveRelative` lines 1453-1468; LivingEntity SHA-256 `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`, Entity SHA-256 `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`.
- B evidence: corresponding `LivingEntity#travel` / `travelInAir` lines 2245-2299, `handleRelativeFrictionAndCalculateMovement` lines 2446-2456, `Entity#moveRelative` lines 1530-1545; bodies are identical to A, with LivingEntity SHA-256 `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8`, Entity SHA-256 `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704`.
- State producers/writers -> consumers/readers: player travel dispatch consumes same fluid/fall-flight flags, friction, input vector, velocity, gravity and effects and writes through identical travel bodies. B introduces `travelFlying` helper, but its only source callsites are Ghast, Allay, Phantom and HappyGhast (non-player); it is not on the Player path. Collision movement history is separately tracked in S4.4; block friction and direct modifiers remain in S5.1/S6.1.
- Parent slices / dependencies / closure evidence: S1.1-S1.4; S2.2; S6.1; S6.2; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): paired player-reachable dispatcher and ground/air arithmetic bodies match; movement-property/modifier producer closure and the Entity.move callback interaction remain open.
- Finding IDs or checked absence/replacement path: none for travel arithmetic; cross-reference S4.4 candidate only for movement-history callback behavior.

### Slice S3.2: jump, sprint-jump, gravity, drag and velocity thresholds

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: jump-power composition and impulse, effective-gravity selection, sprint-jump addition and local jump dispatch.
- A evidence: `LivingEntity#getJumpPower()` / `getJumpBoostPower()` lines 2160-2170, `jumpFromGround()` lines 2173-2185 and `getEffectiveGravity()` lines 2208-2211; LivingEntity SHA-256 `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`.
- B evidence: same method bodies at lines 2192-2217 and 2240-2243, LivingEntity SHA-256 `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8`; player jump dispatch in Player.aiStep is unchanged (S1.3).
- State producers/writers -> consumers/readers: direct jump-strength, jump-boost and gravity inputs feed the same arithmetic and velocity write order in both releases; jump and sprint eligibility match in S1.3. Attribute/effect registration and modifier producer closure remains in S6.1/S6.2; food systems stay excluded.
- Parent slices / dependencies / closure evidence: S1.3; S2.2; S6.1; S6.2; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): jump and gravity consumer formulas match exactly for supplied values; attribute/effect producer and full velocity-writer inventory remain open.
- Finding IDs or checked absence/replacement path: none for these compared consumers.

### Slice S3.3: water, lava and swimming movement

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: travel fluid dispatch, water/lava acceleration and damping, swim-speed/efficiency use, fluid falling adjustment and fluid jump threshold branch.
- A evidence: `LivingEntity#travel()` lines 2213-2222, `travelInFluid()` lines 2249-2299 and `getFluidFallingAdjustedMovement()` lines 2406-2419; LivingEntity SHA-256 `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`.
- B evidence: corresponding methods lines 2245-2254, 2301-2351 and 2458-2471; bodies match A, LivingEntity SHA-256 `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8`.
- State producers/writers -> consumers/readers: identical formulas consume same `isInWater`/`isInLava`, affected-by-fluids flag, fluid height, on-ground, sprint, gravity, swim movement efficiency and velocity. Fluid state/tag/flow sources remain pending S5.3/S5.4, so this is not a closed end-to-end inventory.
- Parent slices / dependencies / closure evidence: S2.1; S2.4; S5.3; S6.1; S6.2; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): paired consumer arithmetic matches; fluid provider/resource and state-writer inventories remain open.
- Finding IDs or checked absence/replacement path: none for travel consumer formulas.

### Slice S3.4: climbing and fall-flying movement

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: climbable motion clamps/reset and fall-flying movement formulas, state guards and direct equipment/effect inputs.
- A evidence: `LivingEntity#travelFallFlying()` lines 2301-2315, `updateFallFlyingMovement()` lines 2322-2345, and `handleOnClimbable()` starts at line 2421; LivingEntity SHA-256 `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`.
- B evidence: matching bodies at lines 2353-2367, 2374-2397 and `handleOnClimbable()` line 2473 onward; LivingEntity SHA-256 `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8`.
- State producers/writers -> consumers/readers: direct movement formulas, climbable clamps and fall-flying state updates match. Elytra/equipment and enchantment/effect producers remain in S6.2/S6.3 and contextual climbable providers in S5.2/S5.4. Fall-impact damage/sound is excluded; direct movement math remains in scope.
- Parent slices / dependencies / closure evidence: S2.4; S5.2; S6.1; S6.2; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): compared player-reachable movement formulas match; equipment/effect/provider inputs and movement-state producer closure remain pending.
- Finding IDs or checked absence/replacement path: none for compared formulas.

### Slice S3.5: post-travel work and callbacks

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: post-travel fall-distance accumulation, player movement state writes and callback timing through the living-entity tick.
- A evidence: `LivingEntity#updateFallFlying()` lines 2815-2825 calls `checkSlowFallDistance()`; `Entity#checkSlowFallDistance()` lines 2506-2511; A movement recording is consumed by `applyEffectsFromBlocks()` / `checkInsideBlocks()` as recorded in S4.4.
- B evidence: `LivingEntity#aiStep()` lines 2721-2856 differs in its generic travel guard by adding `&& isEffectiveAi()`; Player overrides both `canSimulateMovement()` and `isEffectiveAi()` with identical A/B bodies. `LivingEntity#updateFallFlying()` at line 2871 calls the renamed `Entity#checkFallDistanceAccumulation()` at lines 2674-2679 with the same body as A; B movement callback evidence is in S4.4.
- State producers/writers -> consumers/readers: the renamed helper applies the same `deltaMovement.y > -0.5 && fallDistance > 1.0` reset, and direct player AI remains reachable under the same gate. B's `Entity.move` recording/replay differs and is routed to S4.4; callback provider writes remain to inventory.
- Parent slices / dependencies / closure evidence: S2.2; S4.4; S5.2; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): generic post-travel gate is equivalent for Player and fall-distance accumulation is unchanged; callback ordering/provider and complete movement-state writer inventory remain open.
- Finding IDs or checked absence/replacement path: callback candidate `F-ENTITY-MOVEMENT-QUEUE-CAP-1.21.5-1.21.8` remains open in S4.4.

### Slice S4.1: player Entity.move collision resolution

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: direct player movement entry, stuck-speed/edge gates, collision query, step/collision branch, axis clipping and post-collision velocity cancellation.
- A evidence: `Entity#move(MoverType, Vec3)` begins at line 636; `collide(Vec3)` line 968; `collideBoundingBox(...)` line 1020; `collideWithShapes(...)` line 1041; Entity SHA-256 `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`.
- B evidence: corresponding methods begin at lines 663, 1002, 1054 and 1075; collision clipping/arithmetic bodies match A. Entity SHA-256 `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704`. The only semantic change found inside `Entity#move` is recording one whole displacement for callback replay instead of per-axis movement segments; routed to S4.4.
- State producers/writers -> consumers/readers: accepted move vector -> identical collision clip/step result -> position and collision flags/velocity are written by matching bodies; queue representation differs only for later contact callback sampling. Collision query/provider inventory remains open in S4.3/S5.2/S5.4.
- Parent slices / dependencies / closure evidence: S2.1; S2.2; S4.3; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): collision displacement and velocity resolution match for compared methods; callback replay dependency and full world-shape provider inventory remain open.
- Finding IDs or checked absence/replacement path: `F-ENTITY-MOVEMENT-QUEUE-CAP-1.21.5-1.21.8` concerns callback path replay, not collision clipping.

### Slice S4.2: step-up, edge probes and support lookup

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: player-specific edge restraint, generic back-off, supporting-block check and collision/step shape clipping entry.
- A evidence: `Entity#checkSupportingBlock()` starts at line 610, `Entity#maybeBackOffFromEdge()` at 931; `Player#maybeBackOffFromEdge()` at 1026; `Entity#collideBoundingBox()` at 1020, with source hashes in S4.1 and S2.2.
- B evidence: corresponding generic methods start at 637 and 965, `Player#maybeBackOffFromEdge()` at 1038, and `Entity#collideBoundingBox()` at 1054; bodies are identical to A.
- State producers/writers -> consumers/readers: same support and collision results feed the same on-ground/step decision; provider shapes and neighboring-block/resource dependencies are still pending S5.2/S5.4.
- Parent slices / dependencies / closure evidence: S2.1; S4.1; S4.3; S5.2; S5.4; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): compared support, player edge and collision clipping methods match; full collision-shape and neighboring-block inventory remains open.
- Finding IDs or checked absence/replacement path: no direct difference found in these compared methods.

### Slice S4.3: world collision queries and shape evaluation

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: one server-authoritative player packet-validation query path and its context-sensitive shape inputs are compared; the broader player collision-query/shape-iteration/provider inventory remains open.
- A evidence: `ServerGamePacketListenerImpl#isPlayerCollidingWithAnythingNew()` lines 1131-1143 uses `CollisionGetter#getCollisions`; `CollisionGetter.java` lines 83-91 uses ordinary entity context. Hashes and paired method ranges are recorded in candidate `F-PLAYER-MOVE-VALIDATION-CONTEXT-1.21.5-1.21.8`.
- B evidence: `ServerGamePacketListenerImpl#isEntityCollidingWithAnythingNew()` lines 1151-1163 uses `CollisionGetter#getPreMoveCollisions`; `CollisionGetter.java` lines 89-105 calls `CollisionContext.withPosition` with the old box bottom-center Y. The factory sets `placement=true`, which contextual providers may consume. Hashes and paired method ranges are recorded in the candidate finding.
- State producers/writers -> consumers/readers: accepted position packet -> `Entity.move` -> validator's target AABB and prior box -> collision query -> block `getCollisionShape(context)` -> accept target or send position correction. Scaffolding and powder-snow providers read `isPlacement`/`isAbove`; they are unchanged across A/B, with hashes and ranges in the candidate finding.
- Parent slices / dependencies / closure evidence: S2.1; S4.1; S4.2; S5.2; S5.4; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): candidate difference routed to `F-PLAYER-MOVE-VALIDATION-CONTEXT-1.21.5-1.21.8`; the exact query/context change and scaffolding/powder-snow consumers are confirmed, but full player collision-provider coverage and blind review remain open.
- Finding IDs or checked absence/replacement path: candidates `F-PLAYER-MOVE-VALIDATION-CONTEXT-1.21.5-1.21.8` and `F-NETHER-PORTAL-INSIDE-SHAPE-1.21.5-1.21.8`; no terminal disposition yet.

### Slice S4.4: collision, fluid and block callback ordering

- Inventory ID(s): INV-COLLISION, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: movement recording and the player block/fluid contact scan are being compared; the complete callback-provider and player state-write inventory remains open.
- A evidence: `Entity#move()` lines 636-681 records each accepted displacement as axis-ordered path segments; `applyEffectsFromBlocks()` / `checkInsideBlocks()` lines 768-779 and 1079-1122 consume them; `Entity.java` SHA-256 `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`. `ServerGamePacketListenerImpl#handleMovePlayer()` lines 958-1028 invokes `player.move(MoverType.PLAYER, ...)` for accepted non-passenger movement packets; file SHA-256 `fdb8726f77618ebcfd4fe3973e1740d999c2fd3d220ca4a3ddba837f160e6d69`.
- B evidence: `Entity#move()` lines 663-698 records one axis-independent displacement; `addMovementThisTick()` lines 798-807 caps history at 100 and merges its first two samples into a straight segment; `applyEffectsFromBlocks()` / `checkInsideBlocks()` lines 785-807 and 1113-1181 replay ordinary axis-independent records but scan merged paths directly; `Entity.java` SHA-256 `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704`. `ServerGamePacketListenerImpl#handleMovePlayer()` lines 977-1047 provides the same accepted non-passenger packet-to-`player.move` path; file SHA-256 `100f83ae1f264dd26170147cf1319f78b7bf837d5b1b5603934b414cde63a16d`.
- State producers/writers -> consumers/readers: A/B accepted serverbound player moves write one queue sample through `Entity.move`; the player's later living tick drains samples into block/fluid collision-shape callbacks (`LivingEntity#aiStep`, A line 2770; B line 2822). Server movement packets beyond five since its last tick are logged and affect the movement-check multiplier but are not rejected solely for frequency (A `ServerGamePacketListenerImpl.java:995-1004`; B `:1015-1024`), so repeated accepted move calls can reach the new cap. The B cap can merge two recorded paths before the stateful callback consumer; details and exact precondition are routed to `F-ENTITY-MOVEMENT-QUEUE-CAP-1.21.5-1.21.8`. Separately, Nether portal callback dispatch now uses the inherited full-block entity-inside sentinel instead of the portal plane; candidate `F-NETHER-PORTAL-INSIDE-SHAPE-1.21.5-1.21.8` records the exact player transition route. Piston push samples are immediately contact-processed and removed, and are not queue accumulation evidence.
- Parent slices / dependencies / closure evidence: S3.3; S4.1; S5.2; S5.3; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): candidates `F-ENTITY-MOVEMENT-QUEUE-CAP-1.21.5-1.21.8` and `F-NETHER-PORTAL-INSIDE-SHAPE-1.21.5-1.21.8`; threshold and player-packet reachability for the cap and the portal plane-vs-sentinel callback path are source-confirmed, but provider impact and blind review remain open. Other block/fluid callback ordering and movement-state writes still require inventory.
- Finding IDs or checked absence/replacement path: candidate `F-ENTITY-MOVEMENT-QUEUE-CAP-1.21.5-1.21.8`; no terminal disposition yet.

### Slice S5.1: block movement properties and registrations

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: block friction, speed-factor and jump-factor getters plus explicit vanilla block property assignments and their player travel consumers.
- A evidence: `Block#getFriction/getSpeedFactor/getJumpFactor()` source SHA-256 `d66c7efd4afb3e52f8d239a1b5fcd00a5c24874d75b61b5b16b794faf200f2c2`; `BlockBehaviour` property writers source SHA-256 `15418b0026919ef5ab97780a6ce8844677dc82d36cc4433a58f379b837d5cc50`; `Blocks.java` explicit friction/speed/jump assignments source SHA-256 `026290c103c399d94ba4218317e3e627e7275836c5abe91fc4711443b7d2bec0`.
- B evidence: Block and BlockBehaviour hashes are identical to A. `Blocks.java` source hash is `5f929198d7e975bdf82582e7448be45601b9a0e140d435a1a6013e1b6d10ca9f`; the enumerated explicit movement-property assignments match A's values: friction `0.98F`, `0.8F`, `0.989F`; speed factor `0.4F`; jump factor `0.5F`.
- State producers/writers -> consumers/readers: player travel reads block friction and jump/speed factors through unchanged consumers; the searched block source property call sites and values match. Registry additions and their associated modern-only blocks are outside the historical pair. State/tag/resource overrides and any data-driven property inputs remain pending S5.4.
- Parent slices / dependencies / closure evidence: S3.1; S3.2; S4.1; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): compared built-in block movement-property accessors and explicit registration values match; remaining tags/resources and complete property source inventory are open.
- Finding IDs or checked absence/replacement path: no difference found in the compared property accessors and assignments.

### Slice S5.2: shape providers, movement callbacks and subclasses

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: contextual scaffolding and powder-snow collision shapes are confirmed in server player validation, and NetherPortalBlock’s entity-inside shape override changes; complete provider/callback/tag inventory remains open.
- A evidence: `ScaffoldingBlock#getCollisionShape()` lines 133-140 and `PowderSnowBlock#getCollisionShape()` lines 114-130; their source hashes match B and are listed in `F-PLAYER-MOVE-VALIDATION-CONTEXT-1.21.5-1.21.8`. `NetherPortalBlock#getEntityInsideCollisionShape()` returns its thin portal shape (lines 61-69; hash `1cb1b0a796f491deb2317d8a2706442741a627582ca86e3cc7adb6345f975d1b`).
- B evidence: scaffolding/powder-snow hashes and method ranges match A; context-selected return path can differ because the validator supplies a placement context. NetherPortalBlock removes the inside-shape override (hash `883beec01685148971b2b284be2267b5a6a1f258c49e2da4dd28fb4155d49967`) and inherits shared `BlockBehaviour` default `Shapes.block()`; the inside-block scanner calls its unchanged portal callback based on that sentinel, routed to `F-NETHER-PORTAL-INSIDE-SHAPE-1.21.5-1.21.8`.
- State producers/writers -> consumers/readers: server movement query's CollisionContext -> context-sensitive block shape -> candidate collision overlap -> server accepts the target player location or corrects it; tag/resource and remaining movement callbacks pending. The changed Nether portal callback can initiate portal processing under a broader block-inside condition; candidate is separately recorded. In the changed block paths screened by shape/inside-callback signatures, EndPortal’s modified Overworld angle returns through the ServerPlayer respawn path before use; rail changes route through rail-shape rotation/mirroring, while newly added B-only blocks are outside the historical pair. Cauldron callback changes apply fire/extinguish effects, which are excluded. The changed-source signature screen additionally compared `DetectorRailBlock#entityInside`, `EndPortalBlock#getEntityInsideCollisionShape/entityInside`, `PistonMovingBlockEntity#getCollisionShape`, and relevant `updateShape` methods (`BaseRailBlock`, `NetherPortalBlock`, `SignBlock`); those bodies match A/B. `LavaCauldronBlock` and `LayeredCauldronBlock` add filled-content entity-inside shapes and refactor callbacks to LAVA_IGNITE/LAVA_HURT/EXTINGUISH outcomes (file hashes A/B: Lava `12064c02bcaeaae4686a3da48bc6d7ce423c39a1d07e1faF1cdf7e406e612097` / `1a319de4861b1fc4ca5a7fd2f96c9ca1d68e6e8cb46ffc64b61f7815a1547a70`; Layered `69dc9c2530171205d62cfdde290dcdd05e61e94e6248ecb0a02bbdebe2e945bc1` / `822567192b232127f67e092a45cc2a441a7b607855d8725b901d8dd5a183e017`). Those changed callback outcomes are fire, damage and extinguish effects, which are excluded from the movement audit. `DriedGhastBlock` exists only in B and is modern-only. This changed-source screen narrows but does not close remaining provider, tag, property or resource inventories.
- Parent slices / dependencies / closure evidence: S3.4; S3.5; S4.1-S4.4; S5.4; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): candidates `F-PLAYER-MOVE-VALIDATION-CONTEXT-1.21.5-1.21.8` and `F-NETHER-PORTAL-INSIDE-SHAPE-1.21.5-1.21.8`; no modern-only state is used in these candidates. The `EndPortalBlock` spawn-angle source change is also checked: A/B player return to Overworld takes the earlier `ServerPlayer` respawn-position return path, so the changed angle is only used for non-player destination handling; that path is out of scope. Full provider inventory and blind review remain open.
- Finding IDs or checked absence/replacement path: candidates `F-PLAYER-MOVE-VALIDATION-CONTEXT-1.21.5-1.21.8` and `F-NETHER-PORTAL-INSIDE-SHAPE-1.21.5-1.21.8`; no terminal disposition yet.

### Slice S5.3: fluids, flow vectors, height and current

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: water/lava travel consumer, fluid height/current/flow source code, player immersion-state and current-push update path.
- A evidence: movement methods in `LivingEntity#travel/travelInFluid` and Entity fluid-state readers/writers are recorded in S3.3; `Fluid`, `FluidState`, `FlowingFluid`, `Fluids`, `WaterFluid`, and `LavaFluid` source hashes are respectively `e98bdfd14b3092096c6ba763642c3724f852b7589edbf0052b2c8767a874bf94`, `1bfc76676cc7cf136220c05f3506ae4d931cfa33ce0833d307b28048a5d28c61`, `fd3f64f3739a9aded48358bd76362abf6a1248b42a636035cc5ca988b3f8238d`, `913cb5d937af92a7f90f7dc233a3d02c05f75e70a2a740300b2f26eedcbed931`, `9a829ec0d8083d6a3e73a98d40d6c674fd3431aafdb7d05a78cca1daff6e1674`, `04134f233b68e7a8856fbf410414eca8ee75f6acb565276d4c4859dc28a6308c`.
- B evidence: all listed fluid source hashes are identical to A; fluid package manifest diff contains only `FogType.java`. Exact `Entity#updateInWaterStateAndDoFluidPushing`, `updateFluidOnEyes`, `getFluidHeight`, `getFluidJumpThreshold`, `isInWater`, and `LivingEntity#isAffectedByFluids` method bodies also match A (Entity and LivingEntity hashes in S2.2/S3.3).
- State producers/writers -> consumers/readers: fluid travel consumes the same immersion flags, heights, current/flow and jump threshold; in-water current pushes and eye immersion update through identical methods. Fluid tag membership/data resources and full block-fluid callback reachability remain pending S5.4/S4.4.
- Parent slices / dependencies / closure evidence: S3.3; S4.4; S5.4; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): compared source-side fluid classes, player travel formulas and immersion/current methods match; relevant resource/tag data and full callback inventory remain open.
- Finding IDs or checked absence/replacement path: no difference found in the compared fluid source bodies.

### Slice S5.4: neighboring blocks, tags, states and resource defaults

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: neighbor/property/context-dependent shape and movement inputs plus relevant jar resource tags/defaults; not yet checked.
- A evidence: pending source publication and matching client-jar resources.
- B evidence: pending source publication and matching client-jar resources.
- State producers/writers -> consumers/readers: pending resource/registration/neighbor producer to shape and player query consumer trace.
- Parent slices / dependencies / closure evidence: S4.2; S4.3; S5.1-S5.3; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source and resource comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S6.1: movement effects and attribute aggregation

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: base movement attribute registrations and direct attribute value/modifier aggregation methods; item/effect producer and attribute-sync closure remains open.
- A evidence: `AttributeInstance#getValue()` line 145 and modifier mutation `addPermanentModifier()` line 107; `AttributeMap` and `Attributes` source hashes: `6e557daca10617e6a8f4af17d6436a8d1165cd175048e98ea795629a274c44cb`, `60651acdcd2cc66c590b1203978b26e2b253892861f1156004d5040d728210bf`, `14bcc5821d0f67f41fdc3e512556181823cff5c43f84fc1007e51d25f413a3ea`.
- B evidence: same `AttributeInstance#getValue()` and modifier mutation method bodies; B `AttributeInstance`, `AttributeMap`, `Attributes`, `DefaultAttributes` hashes are `c2d4f89e7ae2abbe856e7e1aa5bff2ca613073cbda282c1975636e479ba8e346`, `06088d7aeedd65b468b807c3a500da7a3a16d787917812a55dd7b81ad60cd079`, `b941c0556d9ed3c3ce1ffd4c993c9902287a690230e1510696ff13f9efdf2020`, `56e43f365ae0971e3c8417faeb025f9e3e73983ba83517cb853b80f25fa01991`.
- State producers/writers -> consumers/readers: attribute value and modifier operation order remain unchanged. Among the changed registration entries, new `CAMERA_DISTANCE` and waypoint range attributes do not feed player travel; player attribute builder additions in B are waypoint-only. `AttributeInstance`/`AttributeMap` changes are pack/apply persistence shape, not movement aggregation. Item/effect modifiers and resource-defined entries remain to inventory.
- Parent slices / dependencies / closure evidence: S1.1; S2.3; S3.1-S3.4; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): core attribute aggregation and listed player movement attributes are unchanged; full attribute producers, item resources and effect application paths remain open.
- Finding IDs or checked absence/replacement path: no difference found in the compared aggregation methods.

### Slice S6.2: enchantment/effect movement consumers

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: movement-relevant vanilla effect registrations referenced by player jump/travel formulas; complete effect application, enchantment and resource producer inventory remains open.
- A evidence: `MobEffects.java` SHA-256 `1511be2a9bfd2bd17112ef03b26d645e0cfaf315019b1d906d9d34c54d4ee245`; movement registrations include speed/slowness, jump boost, levitation, slow falling and dolphin's grace at source lines 15-109.
- B evidence: `MobEffects.java` SHA-256 `59460ae5a2087122e88eb4e6f799466ed3ea313a04a9e9162a9abf76dea440ae`; the manifest-listed file difference adds a waypoint-range modifier to invisibility only. The named movement effect registrations retain their A values/operations. Existing `LivingEntity` jump/travel consumers match, as recorded in S3.1-S3.4.
- State producers/writers -> consumers/readers: paired movement consumers read the same effect presence/amplifier values and perform the same formulas; invisibility's added waypoint attribute is not read by the player movement path. Enchantment-driven effects and item/resource-defined effects remain pending.
- Parent slices / dependencies / closure evidence: S3.1-S3.4; S5.2; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): no change found in the compared built-in movement effect registrations; effect/enchantment producer and data inventory remains open.
- Finding IDs or checked absence/replacement path: none for the compared built-in movement registrations.

### Slice S6.3: equipment, active-item and conditional modifiers

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: item attribute modifier iteration and conditional-effect validation entry; equipment, active-item, enchantment and resource producer routes remain open.
- A evidence: `ItemAttributeModifiers#forEach(EquipmentSlotGroup, BiConsumer)` line 51 and `ConditionalEffect.conditionCodec` source SHA-256 `b3df278b9967e886e52cf117e0516143b28cfe0a2020b59a7aa184d2ce450f08`, `ce16e6ccd714f73bd9e2e3d32507a82e3d45410936ba380bca25b769e7537b12`.
- B evidence: existing BiConsumer modifier iteration body is unchanged at line 74; new display-aware overload is presentation-only. `ConditionalEffect.conditionCodec` validation retains the same validation and error/success condition in a refactored expression. B hashes are `de6e2f7df09d37c20a653915b6db9910335da16fbb6b48fd471b901f18a7f289`, `edc7955872fbb5f8c1c8d341e6e438a32e6eeef426375889373019ef0ef4f51e`.
- State producers/writers -> consumers/readers: the compared modifier enumeration still passes the same attribute holder and modifier to consumers; conditional-effect codec change validates data rather than changing application. Actual equipped item/enchantment data and direct active-item movement predicates still require trace.
- Parent slices / dependencies / closure evidence: S2.3; S3.4; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): no movement change found in the compared modifier iteration or validation expression; full equipment, active-item, enchantment and resource producer inventory remains open.
- Finding IDs or checked absence/replacement path: none for the compared methods.

### Slice S7.1: incoming velocity and position corrections

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: incoming entity velocity packets and local-player position/teleport correction consumer plus serverbound accepted movement validation/correction branch.
- A evidence: server `ServerGamePacketListenerImpl#handleMovePlayer()` lines 958-1096, hash `fdb8726f77618ebcfd4fe3973e1740d999c2fd3d220ca4a3ddba837f160e6d69`; client `ClientPacketListener#handleSetEntityMotion()` lines 549-555 and `handleMovePlayer()` / `setValuesFromPositionPacket()` lines 720-749, ClientPacketListener hash `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617`; separate `handleEntityPositionSync()` lines 567-592 body SHA-256 `4aa603e7d5185e5c7e64ab40f5c80dd87d8801428a1146eca371849ac3deb116`.
- B evidence: server `ServerGamePacketListenerImpl#handleMovePlayer()` lines 977-1116, hash `100f83ae1f264dd26170147cf1319f78b7bf837d5b1b5603934b414cde63a16d`; client `handleSetEntityMotion()` lines 598-604 and `handleMovePlayer()` / `setValuesFromPositionPacket()` lines 769-798, ClientPacketListener hash `a66556678a93c63c0ceb8953f25261b82bf3bb1971048fbc15b177c6dffea8cd`; separate `handleEntityPositionSync()` lines 616-641 body SHA-256 `8c92d7e3c89952015281a6499e9113ec3922c403fa06f4010df37cc7773d0b47`. The named velocity/position-correction consumers are identical to A.
- State producers/writers -> consumers/readers: incoming entity-motion packet -> `lerpMotion`; relative/absolute player position correction -> write position, delta movement and rotation -> send teleport acceptance and current PosRot packet. The `handleSetEntityMotion` and `handleMovePlayer` correction consumers and acknowledgment order match. The separate `handleEntityPositionSync` path reverses the interpolation guard around `positionRider(localPlayer)`, routed to candidate `F-CLIENT-PASSENGER-POSITION-SYNC-1.21.5-1.21.8`. Server-side player move validator now uses B pre-move collision context; the exact changed query and concrete shape consumers are in candidate `F-PLAYER-MOVE-VALIDATION-CONTEXT-1.21.5-1.21.8`.
- Parent slices / dependencies / closure evidence: S1.2; S2.2; S3.1; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): client velocity/position correction consumers match, but the server validator candidate and remaining external writer inputs/correction routes remain open.
- Finding IDs or checked absence/replacement path: candidate `F-PLAYER-MOVE-VALIDATION-CONTEXT-1.21.5-1.21.8`; no terminal disposition.

### Slice S7.2: player pushes, pistons, mounts and launch items

- Inventory ID(s): INV-EXTERNAL, INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: direct player knockback and velocity pushes, piston translation/contact processing; remaining push sources, mount/dismount player-facing gates and launch-item paths remain open. Vehicle physics is excluded.
- A evidence: `LivingEntity#knockback(double, double, double)` line 1531 and `Entity#push(Vec3)` / `push(double, double, double)` lines 1614-1624; corresponding method bodies match B. LivingEntity SHA-256 `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`; Entity SHA-256 `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`. `PistonMovingBlockEntity#moveEntityByPiston()` lines 186-192 moves, immediately calls `applyEffectsFromBlocks(oldPosition, newPosition)`, then removes the latest movement recording; file SHA-256 `bd7bcc95fb811b6ed4e50fe0679fcc72e86cbf2445ef72c35ed8e25c80da361e`. `ServerExplosion#hurtEntities()` starts at line 170; the A source file hash is `d49f486abfa0d97a8da26f763c51c917c8b1f8064588962f21a3b113002c4f71`.
- B evidence: `LivingEntity#knockback` line 1563 and `Entity#push(Vec3)` / `push(double, double, double)` lines 1691-1701 have bodies identical to A; LivingEntity SHA-256 `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8`; Entity SHA-256 `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704`. `PistonMovingBlockEntity#moveEntityByPiston()` lines 185-191 keeps the same move -> immediate callback -> remove-latest order; the cleanup method is renamed but removes the last recording entry in both releases. File SHA-256 `5965f9f6adb4fe10222208a411a8cde72dd6e1821de06416330e17203db0e2fd`. `ServerExplosion#hurtEntities()` starts at line 170, and its body is unchanged; the B source file hash is `cceba7bfb58cdcb4b0ed124586b149792aea07695022c8ba665999c44b30c70e`.
- State producers/writers -> consumers/readers: native LivingEntity knockback and direct velocity push formulas produce the same player delta movement/impulse writes; client velocity packet consumer is in S7.1. Piston moves directly through Entity.move and immediately contact-processes/removes its sample in the same order in both versions, so the API rename does not add piston samples to the queue-cap path. `FishingHook#pullEntity()` is unchanged, but its B tick gravity guard differs for non-water grounded/hooked states and its unchanged retrieval route can add velocity to another eligible Player; candidate `F-FISHING-HOOK-PLAYER-PULL-1.21.5-1.21.8` records the grounded swept-hit precondition and remains pending geometry/review. Firework player-boost tick and TridentItem source bodies are unchanged. `ServerExplosion#hurtEntities()` is unchanged in this pair; `shouldAffectBlocklikeEntities()` changes at lines 316-320 by dropping the source-in-water check, but its consumers gate block-attached/item entity explosion behavior, not player velocity. Ender pearl player teleport movement is unchanged and its `level()` substitution supplies only the damage call. Remaining external velocity sources and provider closure remain open; combat/damage producers stay excluded while their supplied knockback response is in scope.
- Additional comparison: the common Entity passenger-position writer, generic `startRiding`/`canRide` gates and `stopRiding` core path match at A lines 2019-2115 and B lines 2187-2283 (Entity hashes in S4.1/S2.2). This does not close vehicle-specific passenger eligibility or physics.
- Parent slices / dependencies / closure evidence: S2.2; S4.4; S5.2; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): compared native knockback and velocity-push formulas match; piston callback processing and cleanup order also match for the immediate player push path. Source/provider closure for other external player movement writers remains open.
- Finding IDs or checked absence/replacement path: candidate `F-ENTITY-MOVEMENT-QUEUE-CAP-1.21.5-1.21.8` affects server player callback replay; no direct knockback/push difference found in compared methods.

### Slice S7.3: final reachable movement-writer dependency closure

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT, INV-MODIFIERS, INV-EXTERNAL, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: cross-stage revisit of discovered reachable player movement state writers and changed helpers, including one movement-triggered portal position transition; registration/resource and full producer/consumer closure remains open.
- A evidence: `NetherPortalBlock#entityInside()` calls `Entity#setAsInsidePortal()` when `canUsePortal(false)`; `Entity#handlePortal()` later applies its transition (ranges and hashes in `F-NETHER-PORTAL-INSIDE-SHAPE-1.21.5-1.21.8`).
- B evidence: those callback and transition methods are unchanged, but the callback's shape gate is broader because the Nether portal now inherits `Shapes.block()`; see candidate finding for exact ranges and hashes.
- State producers/writers -> consumers/readers: player movement callback -> portal process state -> later native position/dimension transition; B also adds cross-dimension spectator-follow position writes through `Entity#teleportSpectators` and `ServerPlayer#teleport`, routed to candidate `F-SPECTATOR-DIMENSION-FOLLOW-1.21.5-1.21.8`; remaining external writers/providers remain to trace.
- Parent slices / dependencies / closure evidence: S1.1-S7.2; DEP-SRC-A; DEP-SRC-B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): portal transition path is source-confirmed, while all-stage writer/resource/modifier/exclusion closure remains pending.
- Finding IDs or checked absence/replacement path: candidate `F-NETHER-PORTAL-INSIDE-SHAPE-1.21.5-1.21.8`; no terminal disposition.

## Dependency queue and blockers

- `DEP-SRC-A` / all slices / exact 1.21.5 source preparation / resolved: exact marker IDs are 1.21.5 / 1.21.5, namespace Mojmap, marker and source/artifact/diagnostic hashes verified, and nine cited input/player source hashes verified; continue body-level checks as slices are inventoried.
- `DEP-SRC-B` / all slices / same validated publication for exact 1.21.8 / required to establish alignment and compare pair / source preparation owner / resolved: exact marker version 1.21.8, Mojmap namespace, and marker/source/artifact/diagnostic hashes verified; individual source bodies still require slice evidence.
- `DEP-SCALE` / S2.1, S3.1-S3.4, S4.1-S4.3 / exact attribute registration/default and sync behavior, update callback, pose dimensions and dimension scaling / resolved for S2.1: A/B scale defaults, range, syncable declaration, consumers, pose tables and dimension scaling match; remaining consumers are tracked by their dependent slices.
- More dependencies will be added as exact source inventories resolve callers, field writers, registrations and resource data.

## Finding index

- Confirmed findings: none yet.
- Candidate findings: `F-ENTITY-MOVEMENT-QUEUE-CAP-1.21.5-1.21.8` (S4.4/S5.2/S7.2; blind review and callback-provider inventory pending); `F-PLAYER-MOVE-VALIDATION-CONTEXT-1.21.5-1.21.8` (S4.3/S5.2/S7.1; blind review and full provider/correction-path inventory pending); `F-NETHER-PORTAL-INSIDE-SHAPE-1.21.5-1.21.8` (S4.4/S5.2/S7.3; blind review and portal/provider closure pending); `F-FISHING-HOOK-PLAYER-PULL-1.21.5-1.21.8` (S4.1/S4.4/S7.2; exact grounded-hit geometry and blind review pending); `F-SPECTATOR-DIMENSION-FOLLOW-1.21.5-1.21.8` (S2.2/S7.1/S7.3; blind review and scope disposition pending); `F-CLIENT-PASSENGER-POSITION-SYNC-1.21.5-1.21.8` (S2.2/S7.1/S7.2; blind review and passenger-scope disposition pending).
- Discarded candidates: none yet.

## Resume checkpoint

- Last completed slice: S2.1 compared-no-difference.
- Next bounded slice and exact files/members/body ranges to open: continue S2.2 remaining movement-state writer closure; trace the remaining collision provider, fluid, movement-property, modifier and external-input call chains before final dependency closure.
- Outstanding dependencies and owners: remaining state writer/consumer routes, collision/provider callback inventory, block/fluid property and resource inventory, movement modifiers, external movement inputs, exclusion disposition and independent source audit; source publications are ready.
- Current assumptions requiring verification: remaining changed provider behavior and resources, all movement writer/consumer edges, and the exact preconditions of each candidate.

## Implementation reconciliation

Complete only after source-only freeze. No mod implementation was opened for this run.

- Reconciliation status: pending
- Repository revision inspected: none; deferred until after blind-discovery freeze.
- Finding -> implementation disposition/evidence: pending.
- Existing implementation without a frozen source finding: pending.
- Coverage gaps routed back to discovery slices: pending.

## Independent source audit

- Reviewer: not yet assigned/recorded; must differ from discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: none; source inventory is pending.
- Concrete missed-slice routes (or `none found`): pending review.
- Misses routed to slice/finding IDs and owners: pending review.
- Reviewer evidence / date: pending review.

## Source audit closure

- Coverage counts by status: 1 pending; 20 in-progress; 6 compared-no-difference; 0 findings; 0 not-applicable; 0 blocked (all 7 inventories pending).
- Required inventory status and evidence: INV-TICK has paired S1.1-S1.4 input/tick/jump/sprint/flight/riding comparisons and partial S3.1-S3.5 travel consumer evidence; INV-STATE has core movement setter pairs, input, pose/dimension/scale and swimming/fall-flight subsets; S1.1-S1.4, S2.1 and S2.4 compared, S2.2-S2.3 and S3.2/S3.5 partially checked; INV-COLLISION has pose-fit, collision/edge/support method comparisons and partial S4.3/S4.4 evidence; INV-WORLD-MOVEMENT has partial S5.1-S5.3 property/fluid source evidence and S5.2 provider evidence; INV-EXTERNAL has partial S7.1-S7.3 evidence; INV-MODIFIERS has partial attribute/effect/modifier-source evidence in S6.1-S6.3. Remaining stages, providers, resources and full exclusions inventory remain pending. Both exact sources and markers are hash-verified.
- Open dependencies: body-level slice and relevant resource/provider inventory; source publication dependencies are resolved.
- Unresolved gaps and limits: S1.1-S1.4, S2.1 and S2.4 are compared-no-difference; S3.1-S3.5 remain in progress with exact travel/jump consumers compared and dependencies open; S2.2-S2.3, S4.1-S4.4, S5.1-S5.3, S6.1-S6.3 and S7.1-S7.3 are in progress with six source-supported candidates, none independently reviewed. All other pair coverage remains open.
- Evidence/hash/correspondence audit: A/B readiness JSON and source/artifact/diagnostic hashes verified; S1.1-S1.4 input/tick/jump/sprint/flight path, S2.1 bodies, and S3.1-S3.5 travel/jump consumer methods, S2.2 core movement setters and client correction consumers, S4.1/S4.2 collision/support methods, S4.3 validator query, S4.4 movement-recording/contact path and Nether Portal callback shape, S6 attribute/effect/modifier method subsets, plus S7.2 knockback/velocity push consumers checked; remaining member/resource/provider/body diagnostics pending.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (not authorized).

