# Discovery: 1.21.10 to 1.21.11

- Run status: active
- Scope: fresh source-only direct client-player movement comparison; older A = 1.21.10; newer B = 1.21.11. Do not read old/current mod implementation or wiki-audit output, browse Minecraft Wiki/MCPK, or use release notes. Exclude health/food-state production, attack/damage resolution, non-player movement and vehicle physics; direct player-motion, velocity, impulse and knockback response triggered by native movement events remains in scope. No Java implementation or runtime validation before the required handoff.
- Repository revision and start date: comparison branch created from `main` at `002137b227676caea77f6832b9f4c8d0b6200bff`; 2026-10-07. Branch `feat/source-discovery-movement-source-1-21-10-1-21-11`.
- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap on both sides; exact pair markers confirm matching `mojmap` namespace and exact release IDs 1.21.10 and 1.21.11.
- Source preparation owner / command / log / readiness marker: shared source-preparation owner; exact command is in both provenance records. One serialized batch decompiled 1.21.8, 1.21.10 and 1.21.11 using Mojmap, 4G heap, Java 25.0.3+9-LTS, Gradle 9.7.1. Pair logs: `build/movement-campaign-2026-10-07/staging/mojmap-1.21.8-to-1.21.11-9a8c76419cd14660b88f9fc90225e127/gradle.full.log` (SHA-256 `2be0eaf67f24c0f1cfa585da9072572d1ac16b0c79c9864c67e5fb8c1bcd2c91`). Readiness markers and provenance hashes are recorded below. This worker did not run decompilation.
- Toolchain/decompiler/remapper versions and options: Java `25.0.3+9-LTS`; Gradle `9.7.1`; Vineflower `1.12.0`; Tiny Remapper `0.14.1`; Mapping IO `0.9.1`; Gson `2.14.0`; ASM `9.10.1`; `--mappings=mojmap --decompiler-heap=4G`.
- Discovery author(s): this worker.
- Independent reviewer (must differ from discovery authors): pending assignment.

## Artifact manifest

Both exact markers, provenance files, source manifests, artifact manifests, diagnostics and every file listed in the source/artifact manifests were verified read-only. Requested IDs, metadata IDs, resolved IDs and marker IDs all match exactly. Both sides are Mojmap in the same official-name mapping family. The artifact manifest paths are relative to the provenance-recorded batch cache, not the campaign `artifacts/` root.

- A 1.21.10 source root: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap`; 6,386 Java files; 28,999,290 bytes; source manifest `mojmap.sources.sha256`, SHA-256 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`; source hashes/count/bytes all match. Artifact manifest `artifacts.sha256`, SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`; all 257 listed cache artifacts match. Readiness marker SHA-256 `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0`; provenance SHA-256 `9a1db4c050c3caa28a1313f89dae9da3b28feaa5f8afba42cab798b47d7ad77c`; diagnostics SHA-256 `5642b893edbb01bc1dd57386e013c61572c20f75fd25542877bace1f1f457eb0`. Original client jar SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`; official `client_mappings.txt` SHA-256 `2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39`; derived `client-mojmap.jar` SHA-256 `0885181c5e4c2f21dd2f95591dd095fa0176807edbe239ef77218e861f0751da`.
- B 1.21.11 source root: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap`; 6,622 Java files; 29,464,971 bytes; source manifest `mojmap.sources.sha256`, SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`; source hashes/count/bytes all match. Artifact manifest `artifacts.sha256`, SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`; all 257 listed cache artifacts match. Readiness marker SHA-256 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`; provenance SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`; diagnostics SHA-256 `a97183dfdbeb5000aac0c66aaed9bb85aa2f2655594e5e4dece254a7c46a13aa`. Original client jar SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`; official `client_mappings.txt` SHA-256 `517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd`; derived `client-mojmap.jar` SHA-256 `7055b6a734a8f9f0d80229c2438ba12d10952a57c3795f32b8611020b34eb89a`.

The provenance batch command is `decompileMinecraft --versions=1.21.8,1.21.10,1.21.11 --mappings=mojmap --decompiler-heap=4G` with isolated `staging/mojmap-1.21.8-to-1.21.11-9a8c76419cd14660b88f9fc90225e127/output` and `.../cache`; successful exit 0 is recorded in each `.success.log`. The shared full log reports remapper access warnings for `OptionInstance$ValueSet` from `VideoSettingsScreen`/`OnlineOptionsScreen`, and Gradle deprecation warnings; no exception, Vineflower error, or error loading a selected movement class was found. Movement diagnostics mark exact required source identities and list nine methods per side; each body still requires direct inspection before its slice can close. The Java source saver excludes jar resources; source-derived resource claims must cite the original client jar and entry hash. The separate Feather derived-artifact revision `feather-r1-2026-10-07` does not apply to this Mojmap pair.


### Artifact evidence identities

- `PUB-1.21.10-MOJMAP-2026-10-07`: original-verified A publication. Exact source root `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap`; source manifest `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap.sources.sha256`, SHA-256 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`; original client jar at the provenance-recorded batch cache path `1.21.10/client.jar`, SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`; original mapped jar `1.21.10/client-mojmap.jar`, SHA-256 `0885181c5e4c2f21dd2f95591dd095fa0176807edbe239ef77218e861f0751da`; artifact manifest `build/movement-campaign-2026-10-07/ready/1.21.10/artifacts.sha256`, SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`; readiness marker `mojmap.ready.json`, SHA-256 `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0`; provenance `mojmap.provenance.json`, SHA-256 `9a1db4c050c3caa28a1313f89dae9da3b28feaa5f8afba42cab798b47d7ad77c`; original `client_mappings.txt` SHA-256 `2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39`. Targeted source files and hashes are recorded in each finding; this record cites the unchanged original verified publication, not a revised artifact.
- `PUB-1.21.11-MOJMAP-2026-10-07`: original-verified B publication. Exact source root `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap`; source manifest `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap.sources.sha256`, SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`; original client jar at the provenance-recorded batch cache path `1.21.11/client.jar`, SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`; original mapped jar `1.21.11/client-mojmap.jar`, SHA-256 `7055b6a734a8f9f0d80229c2438ba12d10952a57c3795f32b8611020b34eb89a`; artifact manifest `build/movement-campaign-2026-10-07/ready/1.21.11/artifacts.sha256`, SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`; readiness marker `mojmap.ready.json`, SHA-256 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`; provenance `mojmap.provenance.json`, SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`; original `client_mappings.txt` SHA-256 `517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd`. Targeted source files and hashes are recorded in each finding; this record cites the unchanged original verified publication, not a revised artifact.

## Blind-discovery freeze

- This status is the full-pair freeze. Individual finding snapshots are tracked separately and do not change it.
- Status: pending
- Freeze commit/checkpoint and timestamp: pending source coverage and independent audit.
- Evidence inventory and finding IDs included at freeze: pending full source coverage and independent audit; current source findings are `F-S1-FLIGHT-VEHICLE-GATE` and `F-S1-CLIENT-LOAD-GATE`, neither snapshot accepted.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; neither has been opened. No wiki browsing or wiki/MCPK/release-note evidence used.
- Source/mapping hashes covered by freeze: pending exact readiness manifests.

## Correspondence and call order

Exact source roots and diagnostic anchors are now verified; member correspondence and call order remain in progress. Initial movement diagnostics identify `Entity.move`/`moveRelative`, `LivingEntity.jumpFromGround`/`travel`/`aiStep`, `Player.aiStep`/`travel`, and `LocalPlayer.aiStep`/`move` on both sides. Resolve exact descriptors, callers, overrides and source anchors before using these method names as correspondence. Required end-to-end sequence: local input sampling → local tick/superclass tick → pre-travel state/input preparation → travel dispatch and every reachable branch → move/collision/support callbacks → post-travel state updates. Record exact call sequence and state read/write edges for input history/yaw; pose, dimensions and eye height; position, velocity and box; collision/ground/fluid/support flags; movement attributes; sprint/jump timers; and equipment/effect state. Follow every changed or influential helper, writer, consumer, registration and resource.

## Required source inventories

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1-*,S3-*; evidence=exact Mojmap roots, markers, source/artifact manifests and hashes verified; detailed inventory traversal remains open.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S1-*,S2-*,S3-*; evidence=exact Mojmap roots, markers, source/artifact manifests and hashes verified; detailed inventory traversal remains open.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S4-*,S5-contact-shapes,S5-landing-bounce,S5-neighbors,S5-climbables; evidence=exact Mojmap roots, markers, source/artifact manifests and hashes verified; detailed inventory traversal remains open.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S5-*,S6-data-resources; evidence=exact Mojmap roots, markers, source/artifact manifests and hashes verified; detailed inventory traversal remains open.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S3-attributes,S6-*; evidence=exact Mojmap roots, markers, source/artifact manifests and hashes verified; detailed inventory traversal remains open.
- `INV-EXTERNAL` player-only externally supplied movement inputs and direct player velocity/impulse/knockback application, plus in-scope player-facing transitions; exclude non-player and vehicle physics: status=pending; slice_ids=S7-*; evidence=exact Mojmap roots, markers, source/artifact manifests and hashes verified; detailed inventory traversal remains open.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=delegated scope set; direct vanilla-state consumers remain covered by owning movement slices.

## Verified source call-path checkpoint (2026-10-07)

- Exact roots: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap` and `.../ready/1.21.11/mojmap`; source manifests, artifact manifests, readiness markers and all manifest-listed hashes were rechecked.
- Input sequence correspondence: `Minecraft.tick` calls `ClientLevel.tickEntities`; `ClientLevel.tickNonPassenger` calls entity tick; `LivingEntity.tick` reaches `aiStep`; `LocalPlayer.aiStep` consumes the current input and dispatches player movement. The pair's `ClientLevel.tickEntities`/`tickNonPassenger` bodies and the relevant `Minecraft.tick` windows have no movement-order change observed in this first pass. Exact pair source hashes: A `Minecraft.java` `ae782d427ff3f2b0a15bd58fe447bc3a07b216faed243a7618420f816516be50`, `ClientLevel.java` `2a4bf7bac40707bf0d7d2feaa1f6564f5aff7aae1b923cf94270165425dc8ee3`; B `Minecraft.java` `e41d192579972ea043cf39ad7f754e1279dca63aa6a576edceb4c9928d685d59`, `ClientLevel.java` `e76d09de5acad450062d5acb98e76ff032ecb179fef0f7af434e4148f84183a2`.
- Input implementation sources `KeyboardInput.java`, `ClientInput.java`, and `world/entity/player/Input.java` are byte-identical across A/B: SHA-256 respectively `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3`, `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`, `1c0c12af07da1f90651c3a7c4eb19bab6486b6391dcbfdd2926f06e3f36c2f06`. They sample the same movement keys and preserve the same vector normalization and jump-flag behavior. Join and respawn assignments in `ClientPacketListener` were checked and match across the pair.
- Corresponding core source hashes: A/B `LocalPlayer.java` `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` / `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`; `Player.java` `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` / `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`; `LivingEntity.java` `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` / `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`; `Entity.java` `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` / `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`.
- Unresolved item-use dependency: A `LocalPlayer.aiStep` clears the sprint trigger while `isUsingItem()` (A lines 722-24), and `canStartSprinting` rejects `isUsingItem()` (A lines 1062-68). B uses `isSlowDueToUsingItem()` in both sites (B lines 763-65, 1102-08), whose definition reads synchronized item `USE_EFFECTS.canSprint` (B lines 526-28). B `modifyInput` reads the component's `speedMultiplier` instead of A's fixed `USING_ITEM_SPEED_FACTOR=0.2F`. B `UseEffects.DEFAULT` retains the 0.2F speed multiplier and disallows sprinting. B `Item.Properties.spear` and B `Items` apply sprint permission and 1.0F speed only to the new spear registrations; A has no matching item factory or registrations. The bounded modern-spear slice is recorded as not applicable to historical content; general item-use consumers remain open.
- Source-confirmed player-flight input delta: A `LocalPlayer.aiStep` toggles mayfly flight after the two-press jump gate when not swimming (A lines 751-70). B adds `(getVehicle() == null || jumpableVehicle() != null)` to that gate (B lines 791-811); `jumpableVehicle()` means the controlled vehicle implements `PlayerRideableJumping` and `canJump()` (A lines 522-24; B lines 563-65). Thus a player with `mayfly`, on a non-jumpable vehicle, and double-pressing jump can toggle `Abilities.flying` in A but the new condition prevents the toggle in B. Reachability is confirmed through `ClientLevel.tickPassenger` -> `LocalPlayer.rideTick` -> `LivingEntity.rideTick` -> `Entity.rideTick` -> virtual `tick()` -> `LivingEntity.tick` -> `LocalPlayer.aiStep`. The local client must have completed its existing loaded gate. The ability flag persists through the local tick; if the player dismounts before landing, the same `aiStep` has a player-side vertical-velocity branch guarded by `abilities.flying` and `isControlledCamera`. This is a source-confirmed delta, not a vehicle-physics claim or a measured trajectory.
- The sprint eligibility helper moved from local food-level comparison A `LocalPlayer.hasEnoughFoodToSprint` (A lines 1055-77) to `Player.hasEnoughFoodToDoExhaustiveManoeuvres` in B (B `LocalPlayer.isSprintingPossible` lines 1096-1100; B `Player` lines 1577-79). B `FoodData.hasEnoughFood()` returns `getFoodLevel() > 6.0F` (B lines 92-94); the direct threshold matches A, and passenger eligibility is equivalent because A's helper returns true for passengers before the separate vehicle gate. Keep health/food producers excluded; reopen only if other direct consumers show a movement delta.
- B `Entity.computeSpeed` is new and records position delta at base tick, while A has no corresponding method. First-pass consumer search finds `hasMovedHorizontallyRecently()` (used by `FollowBoatGoal`) and `getKnownSpeed()` (used by `KineticWeapon.getMotion`). No direct LocalPlayer movement consumer was found in this search; the observed consumers are vehicle AI or attack resolution, both excluded. The wider external/producer inventory remains open.
- Local-player loaded gate relocation (A `LocalPlayer.tick` calls `tickClientLoadTimeout`/`hasClientLoaded`; B checks `connection.hasClientLoaded`) is a connection lifecycle gate whose tick execution and packet consumers remain to be verified before disposition.
- This checkpoint is navigation evidence, not terminal coverage. The coverage ledger remains open; no pair freeze or reviewer acceptance is claimed.

## Coverage ledger

The entries below are provisional behavior buckets from the required navigation stages. They remain pending and must be split into source-bounded member/body ranges wherever a bucket spans multiple methods, writers, callers, providers, registrations or resources. These labels are not a completed inventory or evidence of equivalence.
### Slice S1-input-sampling: keyboard-controller sampling, input object lifetime and previous/current input capture

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `KeyboardInput.tick()V` samples forward/back/left/right/jump/shift/sprint and normalizes the same vector; `ClientInput` retains the same default and accessors; `Input` retains the same seven flags and codec; keyboard input assignment on join/respawn is unchanged; `LocalPlayer.aiStep()` captures jump/shift/forward before `input.tick()` on both sides. This slice excludes other independent decisions inside `aiStep`.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/client/player/KeyboardInput.java` lines 14-31, SHA-256 `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3`; `ClientInput.java` lines 6-29, SHA-256 `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`; `world/entity/player/Input.java` lines 6-43, SHA-256 `1c0c12af07da1f90651c3a7c4eb19bab6486b6391dcbfdd2926f06e3f36c2f06`; `ClientPacketListener.java` assignments lines 502 and 1269, SHA-256 `1bb341ee18704d882bb68bf917190be1045649026a99545057118b5cb9bfb348`; `LocalPlayer.java` lines 697-706, SHA-256 `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`.
- B evidence: corresponding Mojmap source files have the same per-file hashes as A for `KeyboardInput.java`, `ClientInput.java`, and `Input.java`; `ClientPacketListener.java` assignments lines 515 and 1282, SHA-256 `f1ebdb54d717266c894c87381c98bad101980d252de5bae6ca55c37aece1732e`; `LocalPlayer.java` lines 738-747, SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`.
- State producers/writers -> consumers/readers: keyboard options populate `Input`; `KeyboardInput` updates `keyPresses` and `moveVector`; `LocalPlayer.aiStep()` captures the previous jump/shift/forward values before sampling new input and may set jump through `ClientInput.makeJump()` during auto-jump. The client packet path reads current/previous `Input` from `LocalPlayer`; packet and tick lifecycle coverage remains in `S1-local-tick`.
- Parent slices / dependencies / closure evidence: `S1-local-tick` owns tick lifecycle and packet send ordering; `S1-sprint`, `S1-jump`, and `S1-flight-ride` own decisions that consume these unchanged flags. Constructor/assignment paths in `ClientPacketListener` and respawn were checked.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both versions sample the same seven controls in the same order, compute impulses with the same equal=>zero/positive/negative rule, normalize the same `Vec2`, retain the same input object behavior, and capture previous/current flags in the same order. No input-source or input-capture delta was found in this bounded slice.
- Finding IDs or checked absence/replacement path: checked pair comparison; no difference in the bounded input sampling/capture behavior.

### Slice S1-client-loaded-gate: local-player tick gate while level loading is incomplete

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer.tick()V` A lines 195-220 and B lines 211-35; A decrements the Player-owned client-load timer before checking it, B checks `ClientPacketListener.hasClientLoaded()` without a local fallback. Pair the producer with `ClientPacketListener.tick()`/`notifyPlayerLoaded()` and `LevelLoadTracker` readiness.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/client/player/LocalPlayer.java`, SHA-256 `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`, `tick()V` lines 195-220; `world/entity/player/Player.java`, SHA-256 `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82`, `hasClientLoaded()Z`/`tickClientLoadTimeout()V` lines 1875-88 (timer initializes to 60 at field declaration lines 155-56).
- B evidence: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java`, SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`, `tick()V` lines 211-35; `client/multiplayer/ClientPacketListener.java`, SHA-256 `f1ebdb54d717266c894c87381c98bad101980d252de5bae6ca55c37aece1732e`, `hasClientLoaded()Z`/`setClientLoaded(Z)V` lines 2740-45.
- State producers/writers -> consumers/readers: B `ClientPacketListener` sets the flag false at join/respawn (lines 509 and 1252), then its `tick()` checks `LevelLoadTracker.isLevelReady()` and calls `notifyPlayerLoaded()` (lines 2652-66), which sends `ServerboundPlayerLoadedPacket` and sets true. A `ClientPacketListener.tick()` likewise checks tracker readiness and calls notify (lines 2637-51), but the queried player method also becomes true when its 60-tick timer reaches zero. A/B `LevelLoadTracker.java` hashes `876841eef72f74decfbb52ca7de27d95604d9474ac2e65ef07a0bcda0e4fec13` / `da4ee92efe3b9e6cbfe312248346e6a2e159acec884cab0a3b84d7483ffb6dc5`; B readiness waits for a player chunk to be visible or the 30-second tracker timeout. A waits for the chunk to be compiled or the same tracker timeout.
- Call path: A/B `Minecraft.tick()` calls `gameMode.tick()` and, while level exists and the client is unpaused, `level.tickEntities()` (A/B `Minecraft.java` hashes `ae782d427ff3f2b0a15bd58fe447bc3a07b216faed243a7618420f816516be50` / `e41d192579972ea043cf39ad7f754e1279dca63aa6a576edceb4c9928d685d59`). `MultiPlayerGameMode.tick()` drains the network connection, advancing `ClientPacketListener.tick()` (A/B hashes `bf6541cc58c82cc167a196cfadce23f258967a4dcc250b9c85dd772eda60a979` / `4d15d5c6b8c280d0e2c6de1b0f41bde83bc0944d400b7f89cabff24679e9a9c0`). The entity tick path invokes `LocalPlayer.tick()` for a nonpassenger or through `rideTick()` for a passenger.
- Parent slices / dependencies / closure evidence: `S1-local-tick`; `D-LOAD-TRACKER-READINESS` checked in A/B `LevelLoadTracker`; `D-CLIENT-CONNECTION-TICK` checked in A/B `Minecraft.tick` -> `MultiPlayerGameMode.tick` -> connection tick. This row is separate from ordinary movement physics and does not close load/reconnect packet state outside the tick gate.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): source-confirmed tick-gate delta. If the client has an active level, is unpaused, and the level load tracker has not yet declared readiness for more than 60 local-player tick calls, A decrements the timer to zero and then runs `super.tick()` and local movement; B keeps `connection.hasClientLoaded()` false, so it skips those player-tick operations until `notifyPlayerLoaded()` sets the connection flag. The level tick path is reachable while the screen is showing load progress because `Minecraft.tick()` gates entity ticking on `level != null` and `!pause`, not on the screen being absent. This proves a difference in whether local-player movement ticks execute during a delayed load; it does not claim a trajectory or any damage/attack behavior.
- Finding IDs or checked absence/replacement path: `F-S1-CLIENT-LOAD-GATE`; independent finding-specific review pending.

### Slice S1-local-tick: local player tick, superclass tick, travel dispatch and pre/post-travel ordering

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S1-yaw-relative: yaw-to-motion conversion, diagonal normalization, relative acceleration and input scaling

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S1-sneak-use: sneak and item-use input scaling, edge-sneak path and timing

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: A/B `LocalPlayer.applyInput(Vec2)V` scales the sampled vector via `modifyInput(Vec2)V` before assigning movement impulses; the bounded comparison covers `isMovingSlowly()Z`, the scaling sequence and square-movement normalization, plus the `Player.maybeBackOffFromEdge(Vec3,MoverType)V` override and its `Entity.move(MoverType,Vec3)V` dispatch before collision resolution.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/client/player/LocalPlayer.java`, `isMovingSlowly()Z` lines 607-09, `applyInput(Vec2)V` lines 612-25, `modifyInput(Vec2)V` lines 627-43 and `modifyInputSpeedForSquareMovement(Vec2)V` lines 645-55, SHA-256 `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`; `world/entity/player/Player.java`, `isStayingOnGroundSurface()Z` lines 309-11 and `maybeBackOffFromEdge(Vec3,MoverType)V` / `isAboveGround(F)Z` / `canFallAtLeast(DDD)Z` lines 891-951, SHA-256 `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82`; `world/entity/Entity.java`, `move(MoverType,Vec3)V` lines 670-766 calls the edge hook at line 696 before `collide`, SHA-256 `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18`.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java`, `isMovingSlowly()Z` lines 648-50, `applyInput(Vec2)V` lines 653-66, `modifyInput(Vec2)V` lines 668-84 and `modifyInputSpeedForSquareMovement(Vec2)V` lines 686-96, SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`; `world/entity/player/Player.java`, `isStayingOnGroundSurface()Z` lines 309-11 and `maybeBackOffFromEdge(Vec3,MoverType)V` / `isAboveGround(F)Z` / `canFallAtLeast(DDD)Z` lines 889-951, SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`; `world/entity/Entity.java`, `move(MoverType,Vec3)V` lines 685-781 calls the edge hook at line 711 before `collide`, SHA-256 `32314478c6036fa9f3f3cc409c61c622eeffc5a282d60e1011a1a33cc29cf18a3`.
- State producers/writers -> consumers/readers: sampled directional input, item-use state, crouch/crawl state and `SNEAKING_SPEED` feed LocalPlayer input scaling; shift state, flying ability, requested Y movement, mover type, step height, ground state, fall distance and collision support checks gate edge retreat. The input is later consumed by player travel; edge retreat feeds the shared entity collision path.
- Parent slices / dependencies / closure evidence: `S1-input-sampling` verifies the input source and capture order; `S1-use-effects-modern-spear` covers the B item-use multiplier component and modern-only spear exception; `S3-ground-air` covers relative movement consumption. Full entity movement/collision remains open in `S4-move-core` and its child slices.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for A-era vanilla items, both versions scale nonzero input by `0.98F`, apply the item-use multiplier, apply `SNEAKING_SPEED` when crouching or visually crawling, then normalize/clamp through the same square-movement formula. B obtains its multiplier from the component; the default remains `0.2F`, while the only inspected vanilla override is the B-only spear family recorded separately as modern-only. The Player edge-retreat body and its shift-key surface predicate match; `Entity.move` invokes it at the same point before collision resolution. No historical-content difference was found in this bounded input/edge slice; this does not close the remaining movement/collision inventory.
- Finding IDs or checked absence/replacement path: no historical-content delta found in the compared methods; B-only spear applicability is recorded in `S1-use-effects-modern-spear`.
### Slice S1-use-effects-modern-spear: item-use slowdown and sprint permission component

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: A `LocalPlayer.modifyInput(Vec2)V` uses fixed `0.2F` when using an item outside a vehicle and blocks sprint while any item is used; B reads `DataComponents.USE_EFFECTS.speedMultiplier` for input scaling and `canSprint` for sprint eligibility/reset. B default is `UseEffects(false,true,0.2F)`; B spear item properties override it to `UseEffects(true,false,1.0F)`.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/client/player/LocalPlayer.java`, fixed constant at line 103, `modifyInput` lines 627-43, sprint guards lines 722-24 and 1062-68, SHA-256 `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`; A `world/item/Item.java` SHA-256 `564e768acd45f794416ecd16948524ce76f2abeecc0440e3763493b0ce5d26ef` and `Items.java` SHA-256 `2331d554a1365defd3e89c033e3321bec64cdcd9e5c92764744a788c376a2ce8` contain no `spear` property factory or spear registrations (checked full classes).
- B evidence: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java`, `isSlowDueToUsingItem()` lines 526-28, `itemUseSpeedMultiplier()` lines 530-32, `modifyInput` lines 668-83, sprint conditions lines 763-65 and 1102-08, SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`; `world/item/component/UseEffects.java` lines 8-11, SHA-256 `bb69726562d4f68006732a159abceb04e63685e8c05d2338b15d69a53f60bb6f`, defines `DEFAULT = new UseEffects(false, true, 0.2F)`; `core/component/DataComponents.java` lines 115-17 and 412, SHA-256 `a6647a61e315769a256e2a8cfe96596c083ab3ce11cda292a70e89f3aa46d1fc`, defines the persistent/network-synchronized component and installs the default for common item components; `world/item/Item.java` lines 463-509, SHA-256 `53e371efb8ef6eca5e8585a4598b3251dc37bd005b6d4c068c1f8c67d4efeee3`, adds `UseEffects(true,false,1.0F)` in `Item.Properties.spear`; `world/item/Items.java` lines 1897-1916, SHA-256 `4abbdcd5f3ba6d906d80e1bf3b42610fc0afbfa87a13b4be7d9bb579a429e1ad`, registers six material-tier spear items using that factory.
- State producers/writers -> consumers/readers: active `useItem` component is read by the local input/sprint paths. Existing items with the B default retain A's fixed `0.2F` slowdown and remain non-sprintable while in use. The only inspected B vanilla override is the newly registered spear family.
- Parent slices / dependencies / closure evidence: `S1-sneak-use`, `S1-sprint`; `D-USE-EFFECTS-DEFAULT` closed by `UseEffects.DEFAULT` and `DataComponents` common components; `D-SPEAR-APPLICABILITY` closed by full A/B Item and Items registration sources. Custom item components/data are not established by the vanilla pair and are outside this default-content conclusion.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): the component preserves the old item-use slowdown for vanilla items present in A. B's different `canSprint=true` and `speedMultiplier=1.0F` are applied only to its newly registered spear items; A has no spear item/factory. This is modern-only item behavior that the older target maps could not contain, so it is not a historical player movement delta for this comparison. The broader sneak/input and sprint rules remain open.
- Finding IDs or checked absence/replacement path: not applicable to historical content; checked B component/default/registration and A item factory/registration absence.

### Slice S1-sprint-eligibility-food-passenger: food threshold and passenger sprint gate

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: A `LocalPlayer.isSprintingPossible(Z)Z` lines 1055-60 and `hasEnoughFoodToSprint()Z` lines 1075-77; B `LocalPlayer.isSprintingPossible(Z)Z` lines 1096-1100 and `Player.hasEnoughFoodToDoExhaustiveManoeuvres()Z` lines 1577-79. This slice covers only food/mayfly/passenger eligibility, the vehicleCanSprint guard and shallow-water clause; item-use restrictions are covered separately.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/client/player/LocalPlayer.java`, `isSprintingPossible(Z)Z` lines 1055-60 and `hasEnoughFoodToSprint()Z` lines 1075-77, SHA-256 `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`; helper returns `isPassenger() || foodLevel > 6.0F || mayfly`, followed by the separate `vehicleCanSprint` condition.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java`, `isSprintingPossible(Z)Z` lines 1096-1100, SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`; B `world/entity/player/Player.java` lines 1577-79, SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`; B `world/food/FoodData.java` lines 92-94, SHA-256 `e76903ba0996a05e5109b9feba846915efe6f708274e6333938d96c29ea6c80c`, returns `getFoodLevel() > 6.0F`.
- State producers/writers -> consumers/readers: food level and mayfly flag are read as vanilla state; production of health/food/exhaustion is explicitly out of scope. Passenger branch bypasses the food helper in B and uses the same `vehicleCanSprint(vehicle)` condition as A. The flying-or-not shallow-water check is byte-for-byte the same clause.
- Parent slices / dependencies / closure evidence: `S1-sprint`; `D-FOOD-THRESHOLD` closed by A direct comparison and B `FoodData.hasEnoughFood`; `D-PASSENGER-SPRINT` closed by comparing the A helper's passenger short-circuit with B's explicit conditional. Does not close sprint initiation/reset triggers or sprint-jump interaction.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for nonpassengers, both versions require food level above `6.0F` or mayfly; for passengers, both versions bypass that check and still require the vehicle's same `canSprint()` and local authority predicates. Both apply the same mobility restriction and flying/shallow-water conditions. No eligibility difference was found under the same player/vehicle state.
- Finding IDs or checked absence/replacement path: checked pair comparison; no food/passenger sprint-eligibility difference in this bounded predicate.

### Slice S1-sprint: sprint start/stop gates, timers, transitions, sprint-jump interaction

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer.aiStep()` sprint-timer decrement/reset, double-tap start, manual sprint start and run/swim stop branch; `canStartSprinting`, `shouldStopRunSprinting`, `shouldStopSwimSprinting`, and `isMovingSlowly`. A/B preserve the same trigger window read, state/order, forward impulse, manual sprint key, shallow-water and collision predicates. The item-use guard changes from `isUsingItem()` to `isSlowDueToUsingItem()`; B's default `UseEffects(false,true,0.2F)` preserves the A gate for vanilla items present in A, while the new spear override is isolated in `S1-use-effects-modern-spear` as modern-only.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep()V` sprint window lines 687-748, `isMovingSlowly()Z` lines 607-609, `shouldStopRunSprinting()Z` lines 841-845, `shouldStopSwimSprinting()Z` lines 847-849, `isSprintingPossible(Z)Z` and `canStartSprinting()Z` lines 1055-1068, SHA-256 `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java`, corresponding `aiStep()V` sprint window lines 728-789, `isMovingSlowly()Z` lines 648-650, `shouldStopRunSprinting()Z` lines 882-886, `shouldStopSwimSprinting()Z` lines 888-890, `isSprintingPossible(Z)Z` and `canStartSprinting()Z` lines 1096-1108, SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`; default item-use component is documented with full B component evidence in `S1-use-effects-modern-spear`.
- State producers/writers -> consumers/readers: `sprintTriggerTime` is decremented before input evaluation and reset by shift/backward or item-use slowdown; double-tap/manual start writes sprint state, then run/swim predicates may stop it. Forward input, food/passenger eligibility, crouch/crawl state, item-use state, water state and collision flags feed the same branches. Food/passenger predicate is closed in `S1-sprint-eligibility-food-passenger`; vanilla item-use default and new spear override are closed for the default-content conclusion in `S1-use-effects-modern-spear`; sprint-jump velocity is covered by `S3-jump-impulse`.
- Parent slices / dependencies / closure evidence: `S1-input-sampling` owns captured input order; `S1-sprint-eligibility-food-passenger` closes food/passenger/shallow-water eligibility; `S1-use-effects-modern-spear` isolates the only inspected vanilla UseEffects override; `S3-jump-impulse` closes the velocity write after sprinted ground jump. The ability-toggle block in the surrounding `aiStep` is separately covered by `S1-flight-toggle-vehicle-gate`.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for Player inventory available in 1.21.10 with the same input and world/player state, sprint trigger timing, manual/double-tap start, run/swim stop conditions and branch order match. B's component predicate preserves the old use slowdown and sprint prohibition for those existing vanilla items; the B-only spear exception is modern-only and excluded from the historical content set. This does not claim food production, combat causes, or unrelated `aiStep` branches.
- Finding IDs or checked absence/replacement path: paired sprint gates compared; no historical-content sprint-transition difference found in this bounded slice. B-only spear applicability is separately recorded as not applicable.

### Slice S1-jump: jump input, cooldown, jump delay, auto-jump and jump state capture

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S1-flight-toggle-vehicle-gate: mayfly double-press toggle while passenger on a non-jumpable vehicle

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer.aiStep()V`, only the second-press mayfly branch; A `LocalPlayer.java` 751-70; B `LocalPlayer.java` 791-811. Guards: `mayfly`, not spectator/always-flying mode, second jump press before the 7-tick trigger expires, not swimming, and `isPassenger()` with `jumpableVehicle()==null`.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer.aiStep()V`, original lines 751-70, SHA-256 `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`. Expression: `else if (!this.isSwimming())` followed by `$$3.flying = !$$3.flying;`.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer.aiStep()V`, original lines 791-811, SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`. Expression adds `(this.getVehicle() == null || this.jumpableVehicle() != null)` to the same `else if`.
- State producers/writers -> consumers/readers: local jump input is captured before `input.tick()` in the same aiStep; `jumpTriggerTime` is initialized/decremented in this method; `jumpableVehicle()` returns the controlled vehicle only if it implements `PlayerRideableJumping` and `canJump()` (A `LocalPlayer.java` 522-24; B 563-65); the branch writes `Abilities.flying`, then calls `onUpdateAbilities()`. When the player is the camera, the later same-method vertical delta write is guarded by `abilities.flying` and applies `jump/shift * flyingSpeed * 3.0F` (A 791-803; B 832-44).
- Reachability: `ClientLevel.tickEntities` skips passengers; `tickPassenger` calls `rideTick` for Players (A `ClientLevel.java` 321-60; B 329-68). `LocalPlayer.rideTick` calls `super.rideTick`; `LivingEntity.rideTick` delegates to `Entity.rideTick`, which invokes virtual `this.tick()` (A `Entity.java` 2234-40; B 2254-60). The local tick calls `super.tick` only when its client-loaded gate passes (A `LocalPlayer.java` 195-99; B 211-14); `LivingEntity.tick` reaches `aiStep` when not removed (A 2542-83; B 2610-50). Relevant file hashes: A `ClientLevel.java` `2a4bf7bac40707bf0d7d2feaa1f6564f5aff7aae1b923cf94270165425dc8ee3`, `Entity.java` `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18`, `LivingEntity.java` `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`; B `ClientLevel.java` `e76d09de5acad450062d5acb98e76ff032ecb179fef0f7af434e4148f84183a2`, `Entity.java` `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`, `LivingEntity.java` `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`.
- Parent slices / dependencies / closure evidence: `S1-flight-ride`; `D-AI-STEP-CALLER` closed by the caller chain above; `D-JUMPABLE-VEHICLE` closed by exact A/B helper bodies; `D-ABILITY-MOTION-CONSUMER` closed for the in-scope local ability write and camera-controlled vertical delta branch above. Does not close the broad flight, vehicle or ability inventory.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): source-confirmed. With `mayfly=true`, non-spectator mode, not swimming, a completed loaded gate, and riding a vehicle that cannot satisfy `jumpableVehicle`, a second jump press inside the trigger window toggles the local `Abilities.flying` state in A; B's extra vehicle guard skips that toggle. After a dismount before grounding, the existing local camera-controlled flight-input path can consume the differing flag. No position/trajectory was observed.
- Finding IDs or checked absence/replacement path: `F-S1-FLIGHT-VEHICLE-GATE`; independent finding-specific review pending.

### Slice S1-flight-ride: flight toggle/input, unstuck behavior and riding gates affecting player

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S2-pose: pose selection, swimming/crawling transitions and stored pose state

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S2-dimensions: pose/entity dimensions, resize timing, eye height when used by movement/fluid queries

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S2-player-gates: player superclass movement gates, blindness/item-use/abilities consumers and field defaults/resets

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S2-flight-state: abilities, flight speed, flying/walking state and stored air speed

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S3-water-float-while-ridden: additive buoyancy for tagged living vehicles

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: B `LivingEntity.travelInFluid(Vec3)V` calls `floatInWaterWhileRidden()` after water travel; the helper adds `0.04F` vertical velocity only if the entity type is tagged `CAN_FLOAT_WHILE_RIDDEN`, is a vehicle, and its water height exceeds the fluid jump threshold. A `travelInFluid(Vec3)V` has no corresponding post-water addition.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travelInFluid(Vec3)V` lines 2331-80, SHA-256 `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`; whole-tree source search has no `CAN_FLOAT_WHILE_RIDDEN` symbol. A original client jar `build/movement-campaign-2026-10-07/staging/mojmap-1.21.8-to-1.21.11-9a8c76419cd14660b88f9fc90225e127/cache/1.21.10/client.jar`, SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`, lacks `data/minecraft/tags/entity_type/can_float_while_ridden.json`.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travelInFluid(Vec3)V` lines 2368-78 and `floatInWaterWhileRidden()V` lines 2434-38, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`. B `EntityTypeTags.java` defines `CAN_FLOAT_WHILE_RIDDEN` at line 53, SHA-256 `12dd847ea92c7bf5b581e62b1d5480219a7f261494678003cec7b4925d3d1e0a`; B `EntityTypeTagsProvider.java` lines 255-56 registers horse, zombie horse, mule, donkey, camel and camel husk, SHA-256 `35fdd54c0e27ac8e2ba20eb6565cb7dfdc243dc2a3583dafda941a4164d3f9ff`. The default 1.21.11 client-jar tag entry is `data/minecraft/tags/entity_type/can_float_while_ridden.json`, SHA-256 `d0fbdf6df64c9e6e1af9f13873a1572216b081c424371eb0182a28093f3ac31c`, with the same six entity types and no player type.
- State producers/writers -> consumers/readers: B `LivingEntity.travel()` dispatches to water travel, then tag/vehicle/fluid-height guards decide whether to add vertical delta. This changes the ridden living vehicle's own motion; it does not write the passenger player's velocity. The vanilla registration is from the B entity-type tag data/provider; any server-supplied tag override is outside this default-resource conclusion.
- Parent slices / dependencies / closure evidence: `S3-travel-dispatch`; `D-CAN-FLOAT-TAG` closed for vanilla defaults by B source provider and exact client-jar entry. The pair's complete world-movement/data inventory remains open.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): the added movement delta is limited by vanilla defaults to non-player rideable living entities and modifies the ridden entity's motion. That is vehicle physics and non-player movement, both outside this campaign's direct-player movement scope. Do not infer compatibility behavior for custom server datapacks from this default tag.
- Finding IDs or checked absence/replacement path: not applicable to in-scope player movement; checked A method/resource absence and B vanilla tag membership.

### Slice S3-travel-dispatch: living travel branch selection and dispatch conditions

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `Player.travel(Vec3)V` passenger branch delegates to `LivingEntity.travel`; the nonpassenger branch applies the same swimming vertical adjustment, then preserves the same flying `super.travel` call and 0.6 vertical-velocity retention. Inherited `LivingEntity.travel(Vec3)V` selects fluid, fall-flying, or air travel in that order. A tests `(isInWater || isInLava) && isAffectedByFluids() && !canStandOnFluid(fluidState)` inline; B extracts that exact expression to `shouldTravelInFluid(FluidState)Z` before the same branches.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/world/entity/player/Player.java`, `travel(Vec3)V` lines 1295-1318, SHA-256 `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82`; `world/entity/LivingEntity.java`, `canStandOnFluid(FluidState)Z` lines 2261-63 and `travel(Vec3)V` lines 2275-84, SHA-256 `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap/net/minecraft/world/entity/player/Player.java`, `travel(Vec3)V` lines 1360-1383, SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`; `world/entity/LivingEntity.java`, `canStandOnFluid(FluidState)Z` lines 2295-97, `travel(Vec3)V` lines 2309-17 and `shouldTravelInFluid(FluidState)Z` lines 2319-21, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`.
- State producers/writers -> consumers/readers: Player travel consumes passenger, swimming, flying ability, current motion, fluid state and fall-flying state; this bounded slice compares dispatch only, not the selected water/lava, fall-flying, air, or vehicle physics bodies. No Player override of `canStandOnFluid` or `shouldTravelInFluid` was found; `LocalPlayer` has no `travel` override, so it inherits the Player/LivingEntity dispatch chain.
- Parent slices / dependencies / closure evidence: `S1-local-tick` owns call timing; `S3-water`, `S3-lava`, `S3-glide`, and `S3-ground-air` own selected branch bodies; `S3-water-float-while-ridden` separately records B's post-water mount buoyancy addition. B helper expression was checked against A's inline guard, and both `canStandOnFluid` defaults return false.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for the same player state and `Vec3` input, both Player travel wrappers take the same passenger/nonpassenger, swimming, and flying branches and preserve the same operation order. The inherited LivingEntity selector makes the same fluid/fall-flying/air choice because the extracted B helper is A's original condition verbatim. Selected branch physics and B's separate ridden-mount buoyancy remain outside this slice.
- Finding IDs or checked absence/replacement path: checked A/B dispatch and helper correspondence; no Player travel-dispatch delta found in this bounded slice.

### Slice S3-ground-air: ground/air acceleration, friction, drag and gravity with exact FP order

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.travelInAir(Vec3)V` uses the same below-movement block friction lookup, `0.91F` horizontal multiplier, relative-friction acceleration, levitation-or-gravity branch including the unloaded-client fallback, then either discarded-friction write or per-axis drag (`FlyingAnimal` horizontal factor vs `0.98F` vertical factor). Supporting helpers `handleRelativeFrictionAndCalculateMovement`, `getFrictionInfluencedSpeed`, `getEffectiveGravity`, and `shouldDiscardFriction` are body-identical. The direct `Entity.moveRelative`, `getBlockPosBelowThatAffectsMyMovement`, and Player `getSpeed`/`getFlyingSpeed` consumers also match.
- A evidence: `LivingEntity.travelInAir(Vec3)V` lines 2306-2329, `handleRelativeFrictionAndCalculateMovement(Vec3,float)V` lines 2476-2486, `getFrictionInfluencedSpeed(float)F` lines 2520-2522, `getEffectiveGravity()D` lines 2270-2273, `shouldDiscardFriction()Z` lines 659-661, SHA-256 `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`; `Entity.moveRelative(float,Vec3)V` line 1590 and `getBlockPosBelowThatAffectsMyMovement()BlockPos` lines 938-940, `Entity.java` SHA-256 `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18`; `Player.getSpeed()F` line 1339 and `getFlyingSpeed()F` lines 1867-1873, `Player.java` SHA-256 `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82`.
- B evidence: `LivingEntity.travelInAir(Vec3)V` lines 2343-2366, `handleRelativeFrictionAndCalculateMovement(Vec3,float)V` lines 2534-2544, `getFrictionInfluencedSpeed(float)F` lines 2578-2580, `getEffectiveGravity()D` lines 2304-2307, `shouldDiscardFriction()Z` lines 657-659, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`; `Entity.moveRelative(float,Vec3)V` line 1608 and `getBlockPosBelowThatAffectsMyMovement()BlockPos` lines 957-959, `Entity.java` SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`; `Player.getSpeed()F` line 1404 and `getFlyingSpeed()F` lines 1959-1965, `Player.java` SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`.
- State producers/writers -> consumers/readers: current input, block friction, ground state, levitation, gravity, sprint, speed attribute and client chunk availability feed the same sequence of delta writes. Both Player speed methods return the same attribute/flying-speed results. Attribute aggregation and producer lifecycle remain open under `S3-attributes`; this slice compares the movement consumer path for equal state.
- Parent slices / dependencies / closure evidence: `S3-travel-dispatch` selects this branch; `S3-water`, `S3-lava` and `S3-glide` cover other travel branches; `S3-attributes` retains producer/aggregation review. Exact method-body comparison found the listed helper bodies equal, and the shared relative movement primitive was previously verified byte-identical.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for equal input vector, block, player state, effect and gravity/speed inputs, both versions apply the same travel, friction, gravity, fallback and axis-drag operations in the same order. No air/ground travel formula difference was found in this bounded consumer path.
- Finding IDs or checked absence/replacement path: checked paired LivingEntity and Entity travel helpers plus Player speed overrides; no bounded air/ground travel-consumer delta found.

### Slice S3-cutoffs: negligible velocity thresholds, comparisons, normalization and post-travel cleanup

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S3-jump-impulse: jump power, sprint jump impulse, yaw trigonometry and velocity writes

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: inherited `LivingEntity.getJumpPower()` and `jumpFromGround()`. Both calculate jump power from `JUMP_STRENGTH * 1.0F * blockJumpFactor + jumpBoostPower`; if above `1.0E-5F`, preserve existing X/Z and set Y to `max(jumpPower,currentY)`. If sprinting, both use yaw in radians and add `(-sin(yaw)*0.2,0,cos(yaw)*0.2)`. Only the final sync marker changes from `hasImpulse` to `needsSync`.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java`, `getJumpPower()F` / `getJumpPower(F)F` lines 2222-2232 and `jumpFromGround()V` lines 2235-2247, SHA-256 `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`; Player has no jump-method override, `Player.java` SHA-256 `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82`.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`, `getJumpPower()F` / `getJumpPower(F)F` lines 2256-2266 and `jumpFromGround()V` lines 2269-2281, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`; Player has no jump-method override, `Player.java` SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`.
- State producers/writers -> consumers/readers: jump-strength attribute, block jump factor, jump-boost effect, sprint flag, yaw and existing delta feed the same formula. The Player wrapper has no override of the impulse method. Marker consumer applicability for active, non-spectator ServerPlayer targets is covered by `S7-entity-sync-scheduling`; attribute aggregation and jump-input/timing remain in their dedicated open slices.
- Parent slices / dependencies / closure evidence: `S1-jump` owns input, cooldown and invocation timing; `S3-attributes` owns producer/aggregation order; `S7-entity-sync-scheduling` closes direct Player marker scheduling under the active Player-ticket precondition. Compared both jump-power and impulse methods, including exact float constants and multiplication/addition order.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for the same current state and jump call, both versions write the same vertical and sprint-horizontal velocity in the same order. The changed sync marker is the already-closed `needsSync` rename for active Player targets; this slice does not claim parity for jump eligibility or timing.
- Finding IDs or checked absence/replacement path: checked paired jump-power and impulse methods plus Player override absence; no jump-impulse formula delta found.

### Slice S3-climb: climbing detection, clamps, movement and exit velocity behavior

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: A `LivingEntity.onClimbable()Z` returns true for nonspectators inside `CLIMBABLE` blocks or usable open trapdoors. B adds an earlier guard: while `isFallFlying()` and the current block state is in `CAN_GLIDE_THROUGH`, it returns false before the existing `CLIMBABLE` branch. A/B `Player.onClimbable()` wrappers are unchanged and return false when `abilities.flying`; under the stated non-mayfly-flight state they delegate to the LivingEntity method. `travelFallFlying` consumes this result: A takes `travelInAir` and clears fall-flying on a climbable block; B keeps the fall-flying path for the newly excluded tagged blocks.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java`, `onClimbable()Z` lines 1662-1678 and `travelFallFlying(Vec3)V` lines 2383-2397, SHA-256 `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`; `Player.onClimbable()Z` lines 1955-1957, `Player.java` SHA-256 `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82`.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`, `onClimbable()Z` lines 1669-1687 and corresponding `travelFallFlying(Vec3)V` lines 2441-2455, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`; `Player.onClimbable()Z` lines 2043-2045, `Player.java` SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`.
- Default block-tag applicability: A and B `data/minecraft/tags/block/climbable.json` are byte-identical (SHA-256 `d0e3e76d7457f3f3f3d7219fe218c7746e4b2e7626d5c388fcf193089069e365`) and include vine, twisting/weeping vines and plants, plus cave vines and plants. A client jar SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482` has no `can_glide_through.json`; B client jar SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` adds that tag (entry SHA-256 `e1b304a494b93be2a3280ea6cc7c73ebc0fd76e21654af801d5e4359e94232a8`) with vine, twisting vines/plants, weeping vines/plants, and `#cave_vines`. B `cave_vines.json` SHA-256 `b936bbb57d972003dda38f1d3584e3749915f26220843b941fc2571ef901c75a` expands to cave vines and cave vines plant. B source `VanillaBlockTagsProvider.java` lines 320-324 (SHA-256 `8c17f2a6c6654721019e575377523edb0ac68c71b8325188a4e6412962c83474`) and `BlockTags.java` line 125 (SHA-256 `9db30d02bbb896538a35267b2f0e9c957a00ffdbf20bcc0ea025b5a7bbebc38e3`) register the tag. The vanilla-default tag intersection is seven existing climbable block states.
- State producers/writers -> consumers/readers: the fall-flying shared flag and current block state feed Player's inherited climbable predicate; the result selects the fallback branch in `LivingEntity.travelFallFlying`. A `Player` with `abilities.flying=true` already returns false in the unchanged Player wrapper, so the new difference requires `abilities.flying=false`, nonspectator, `isFallFlying=true`, and one of the vanilla tag intersection states at the player's current block position.
- Parent slices / dependencies / closure evidence: `S3-travel-dispatch` and `S3-glide`; the glide formula itself is unchanged, but this slice changes whether that formula or the climbable air fallback runs. Default tag membership and Player override applicability were checked from both exact endpoint source/artifact sets.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): source-confirmed player movement delta. For the same fall-flying Player state intersecting a tagged vine/climbable block while not using ability flight, A selects air travel and clears fall-flying; B's added tag guard reports the Player as not climbable and continues through fall-flying travel. This changes movement dispatch and the fall-flying state. No trajectory was measured.
- Finding IDs or checked absence/replacement path: `F-S3-GLIDE-THROUGH-CLIMBABLE`; independent finding-specific source review pending.

### Slice S3-water: water acceleration, drag, gravity, swimming and fluid-height interactions

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Player swimming pre-adjustment in `Player.travel(Vec3)V` plus water branch of `LivingEntity.travelInFluid(Vec3)V`. A applies the same sprint/water-slowdown and water-movement-efficiency factors, halves efficiency off ground, blends the same constants, applies Dolphin's Grace, calls relative movement then self movement, climbable collision adjustment, drag and fluid-falling adjustment, and the shared horizontal-collision jump-out. B's extracted `travelInWater` preserves that operation order and calls `jumpOutOfFluid` at the point of A's shared post-branch collision check. B then calls `floatInWaterWhileRidden`, separately recorded as out of scope in `S3-water-float-while-ridden`.
- A evidence: `Player.travel(Vec3)V` lines 1295-1318, `Player.java` SHA-256 `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82`; `LivingEntity.travelInFluid(Vec3)V` lines 2331-2381 and `getFluidFallingAdjustedMovement(double,boolean,Vec3)Vec3` lines 2488-2501, `LivingEntity.java` SHA-256 `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`.
- B evidence: `Player.travel(Vec3)V` lines 1360-1383, `Player.java` SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`; `LivingEntity.travelInFluid(Vec3)V` lines 2368-2378 and `travelInWater(Vec3,double,boolean,double)V` lines 2380-2407, `getFluidFallingAdjustedMovement(double,boolean,Vec3)Vec3` lines 2546-2559, `LivingEntity.java` SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`.
- State producers/writers -> consumers/readers: both formulas read current delta, gravity, sprint/ground state, water movement efficiency, Dolphins Grace, fluid height and collision state, then write delta through the same calls. `Entity.move`/`moveRelative` bodies were previously compared identical; exact producers of attributes and gravity remain in `S3-attributes`. A `getFluidFallingAdjustedMovement` body matches B; no Player `canStandOnFluid` override was found and the inherited default returns false.
- Parent slices / dependencies / closure evidence: `S3-travel-dispatch` supplies the same selection into this branch; `S3-ground-air` owns the shared relative movement primitive; `S3-attributes` retains source coverage for attribute/gravity producers; `S3-water-float-while-ridden` isolates B's extra mount-only post-water delta.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): under the same Player movement inputs and vanilla state, the swimming adjustment and water travel formulas use the same constants, guards, floating-point operation order, delta writes and collision response. B's additional ridden-vehicle buoyancy is not written to a Player target under the vanilla tag checked in its separate slice.
- Finding IDs or checked absence/replacement path: checked paired Player and LivingEntity formula bodies and the common fluid-falling helper; no in-scope Player water-travel delta found.

### Slice S3-lava: lava travel acceleration, drag, gravity and collision outcomes

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: lava `else` branch of `LivingEntity.travelInFluid(Vec3)V`. A applies relative movement at `0.02F`, moves with current delta, compares lava height with fluid jump threshold, applies either `(0.5, 0.8F, 0.5)` damping plus fluid-falling adjustment or `scale(0.5)`, applies gravity `/ 4.0` when nonzero, then performs the shared horizontal-collision jump-out. B's `travelInLava` preserves the same order and places `jumpOutOfFluid` after the branch-local gravity adjustment.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travelInFluid(Vec3)V` lines 2331-2381, SHA-256 `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`; `getFluidFallingAdjustedMovement(double,boolean,Vec3)Vec3` lines 2488-2501.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travelInFluid(Vec3)V` lines 2368-2378 and `travelInLava(Vec3,double,boolean,double)V` lines 2409-2425, `jumpOutOfFluid(double)V` lines 2427-2432, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`; `getFluidFallingAdjustedMovement(double,boolean,Vec3)Vec3` lines 2546-2559.
- State producers/writers -> consumers/readers: the branch reads current delta, effective gravity, lava fluid height, threshold and collision state. The exact falling-adjustment helper body is unchanged; producer details for gravity/attributes remain in `S3-attributes`.
- Parent slices / dependencies / closure evidence: `S3-travel-dispatch` supplies the same fluid selector; `S3-ground-air` owns the shared relative movement primitive; `S3-attributes` retains source coverage for attribute/gravity producers. A's common post-branch collision code and B's `jumpOutOfFluid` helper were compared operation-for-operation.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): when the Player reaches the lava branch under the same state and input, both versions execute the same movement, height guard, drag, gravity, adjustment and collision response in the same order. The helper extraction changes structure without changing the bounded lava path.
- Finding IDs or checked absence/replacement path: checked paired lava branch, `jumpOutOfFluid` and fluid-falling helper; no in-scope Player lava-travel delta found.

### Slice S3-glide: gliding travel and directly consumed movement attributes/state

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Given the same `onClimbable()` return value, `LivingEntity.travelFallFlying(Vec3)V` uses the same climbable fallback to air travel plus `stopFallFlying`; otherwise it captures horizontal speed, writes `updateFallFlyingMovement` result, moves, then calls server-side collision response. The update formula preserves look-vector normalization, pitch/gravity term, descending lift, upward-pitch acceleration, horizontal alignment adjustment and final `(0.99F,0.98F,0.99F)` drag in the same order. `handleFallFlyingCollisions` uses the same horizontal-collision guard and speed-loss threshold. Damage from `hurt(flyIntoWall, ...)` is explicitly outside this movement slice.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travelFallFlying(Vec3)V` lines 2383-2397, `updateFallFlyingMovement(Vec3)Vec3` lines 2404-2427 and `handleFallFlyingCollisions(double,double)V` lines 2429-2438, SHA-256 `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`, corresponding methods at lines 2441-2455, 2462-2485 and 2487-2496, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`; A/B `Player.canGlide()` overrides return `!abilities.flying && super.canGlide()` (A `Player.java` lines 1321-1323, SHA-256 `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82`; B lines 1386-1388, SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`).
- State producers/writers -> consumers/readers: current delta, look vector, pitch and effective gravity feed the formula; its result writes delta and calls the same entity movement routine. Server-only fall-collision handling consumes pre/post horizontal distance and collision state. Gravity/attribute producers remain under `S3-attributes`; fall-flying activation and equipment/item-use pathways are outside this method-bounded result.
- Parent slices / dependencies / closure evidence: `S3-travel-dispatch` selects the same fall-flying branch; `S3-ground-air` owns shared `Entity.move` behavior; `S3-attributes` retains full gravity/attribute producer review. Compared both exact formula bodies and collision response.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): when `isFallFlying()` is true and the entity is not on a climbable surface, both versions compute and apply the same gliding velocity; on climbables both use air travel then clear fall-flying. The collision speed threshold and movement order match for the same `onClimbable()` result; S3-climb records the newly changed predicate result for fall-flying Players in the default tag intersection. This does not claim parity for damage outcomes or for the broader gliding-state activation inventory.
- Finding IDs or checked absence/replacement path: checked paired travel, formula, stop-state and collision methods; no fall-flying travel delta found in this bounded slice.

### Slice S3-attributes: movement speed, jump/gravity/step-related attribute consumers, aggregation/order/defaults

- Inventory ID(s): INV-TICK, INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S4-move-core: entity move dispatch, axis ordering, position and velocity updates

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `Entity.move(MoverType,Vec3)V` full body, A lines 670-766 and B lines 685-781. It covers the `noPhysics` and piston branches, stuck multiplier, edge hook then collision query, position/movement-history update, collision and on-ground flags, fall/velocity updates, landing callback, movement emission and block speed scaling.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/world/entity/Entity.java`, `Entity.move(MoverType,Vec3)V` lines 670-766, SHA-256 `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18`.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap/net/minecraft/world/entity/Entity.java`, `Entity.move(MoverType,Vec3)V` lines 685-781, SHA-256 `32314478c6036fa9f3f3cc409c61c622eeffc5a282d60e1011a1a33cc29cf18a3`.
- State producers/writers -> consumers/readers: requested `MoverType`/`Vec3`, `noPhysics`, position, stuck-speed multiplier, `fallDistance`, collision flags, ground state, delta movement, level collision results, support block, removal state, simulation authority and passenger state feed the method. It writes position and movement history, collision/ground flags, delta movement and fall state, and invokes landing/block/sound callbacks before applying the block speed factor.
- Parent slices / dependencies / closure evidence: `S1-sneak-use` closes the edge-backoff hook body; `S4-collision-query`, `S4-step`, `S4-edge-support`, `S4-velocity-collision`, `S4-callbacks`, `S5-slow-surface` and `S5-landing-bounce` own the direct helpers and providers. `D-S3-CLIMBABLE-PUSHABILITY` remains open under the broader S4 collision path; it is not resolved by this method-body comparison.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): the complete A/B `Entity.move` method bodies contain no source-text difference and preserve the same operation and callback order. For Player instances, this closes only the shared orchestration method; collision-query, step selection, support, velocity/collision consequences and callbacks remain independently open. A newly traced `onClimbable()` -> `LivingEntity.isPushable()` -> `Entity.pushEntities()` path may gate direct Player push response and still requires source-level scope and writer tracing.
- Finding IDs or checked absence/replacement path: no difference identified in this method body. `F-S3-GLIDE-THROUGH-CLIMBABLE` is a separate fall-flying travel finding; its inherited pushability consumer is not resolved here.
### Slice S4-step: step-up eligibility, candidate paths, height comparisons and tie-breaking

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S4-edge-support: edge probes, support lookup, grounding and support-block state

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S4-velocity-collision: axis cancellation/restitution, collision flags and fall/ground callbacks

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S4-shape-math: AABB/voxel shape intersections, clipping, epsilon and iteration behavior

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S4-callbacks: block/entity movement callbacks reachable from player move and their order

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S5-block-registry: block registrations/default friction, speed/jump factors and shape-provider changes

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S5-landing-bounce: slime/bed landing, bounce, support and callback behavior

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S5-slow-surface: soul sand, ice and other speed/friction surface providers

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S5-contact-shapes: web/honey/powder snow and historical partial-block collision/support shapes

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S5-climbables: ladders, vines and other climbable registrations, callbacks and neighbor dependence

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S5-fluids: water/lava/bubble columns, flow vectors, fluid heights and push timing

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S5-neighbors: neighboring/state-dependent collision and movement providers, including piston displacement path

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S5-modern-only: blocks/states absent in A: verify registration/absence and record modern-only applicability; no historical behavior invented

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S6-effects: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace, Blindness consumers and formulas

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S6-enchantments: Depth Strider, Soul Speed, Swift Sneak, Frost Walker, Riptide and other registered movement-affecting entries

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S6-equipment-items: Elytra, item-use slowdown, equipment slots/components and player movement predicates

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S6-data-resources: client jar data, tags, effect/enchantment definitions, registry defaults and referenced values

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S6-attribute-lifecycle: attribute modifier application/removal, stacking, timers and equipment/effect lifecycle

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S7-external-velocity: client consumers and writers for incoming velocity/position corrections and player knockback/push

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S7-environment-displacement: explosions, pistons, launch items, mount/dismount transitions insofar as they write player movement state

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S7-state-writer-closure: exhaustive remaining reachable player movement-state writers, callbacks, registrations and dependencies discovered from prior stages

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

### Slice S7-player-knockback-vector: inherited LivingEntity knockback vector math for Player targets

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: inherited `LivingEntity.knockback(DDD)V` vector calculation and state write only; server scheduling/replication is tracked separately in `S7-entity-sync-scheduling`. A method at lines 1593-1608; B at 1596-1611.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java`, `knockback(DDD)V`, lines 1593-1608, SHA-256 `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`; exact velocity arithmetic multiplies strength by `1 - knockback resistance`, halves existing horizontal movement, subtracts normalized knockback, conditionally adds capped vertical velocity if grounded, sets delta movement and sets `hasImpulse=true`.
- B evidence: corresponding 1.21.11 `LivingEntity.java`, `knockback(DDD)V`, lines 1596-1611, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`; same arithmetic and ordering, then sets `needsSync=true`.
- State producers/writers -> consumers/readers: Player inherits this public method; neither `Player` nor `ServerPlayer` overrides `knockback`. When invoked, server `ServerEntity.sendChanges()` consumes the marker in its movement replication branch and sends `ClientboundSetEntityMotionPacket`; A `ServerEntity.java` SHA-256 `1d3c6afac845fbaa2783992c963508c41850ce5be6c38f837a776a52dc80db07`, lines 168-85; B SHA-256 `268073683299a1860b5032d184add16f757a4c8fc000b5be7cc1c00291ac930b`, same lines with the renamed marker. `ClientPacketListener.handleSetEntityMotion` applies packet movement through `lerpMotion` in both versions (A `ClientPacketListener.java` SHA-256 `1bb341ee18704d882bb68bf917190be1045649026a99545057118b5cb9bfb348`, lines 592-98; B SHA-256 `f1ebdb54d717266c894c87381c98bad101980d252de5bae6ca55c37aece1732e`, lines 604-10).
- Parent slices / dependencies / closure evidence: `S3-jump-impulse` shares the field rename but retains separate jump-power math coverage; `D-PLAYER-KNOCKBACK-OVERRIDE` closed by checking Player/ServerPlayer sources (no override); `D-VELOCITY-SYNC-CONSUMER` remains open for invocation scheduling; paired ServerEntity and ClientPacketListener consumer bodies were compared, and scheduling is split to `S7-entity-sync-scheduling`.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the direct Player response exists through inherited `LivingEntity.knockback`; for the same inputs and player state, both versions compute and write the same velocity with the same operation order. This no-difference result covers the vector method only; server invocation scheduling is not closed here. Triggering attack/damage decisions remain excluded.
- Finding IDs or checked absence/replacement path: checked pair comparison; no direct player-knockback vector-calculation delta found in this bounded method.

### Slice S7-entity-sync-scheduling: Entity impulse marker and out-of-range ServerEntity scheduling

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: A `ChunkMap.tick()V` lines 1119-1144 calls `TrackedEntity.serverEntity.sendChanges()` when the entity changed sections or is in entity-ticking range. B `ChunkMap.tick()V` lines 1153-1185 adds `entity.needsSync` as a third call condition. Both versions register an active, non-spectator ServerPlayer at `SectionPos.of(player)` and add a `PLAYER_SIMULATION` ticket for that chunk; its ticket level is at or below the entity-ticking threshold.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/server/level/ChunkMap.java`, SHA-256 `15898d494c7d6fca8ccc2ddeaf19f773fc0a92f1b120ed042c47ea4f95690744`, `tick()V` lines 1119-1144; `ServerEntity.sendChanges()` reads `hasImpulse` in lines 116-18 and 168-85.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap/net/minecraft/server/level/ChunkMap.java`, SHA-256 `b7309e9d6b6b3d0ebbda146231392b679a78dc0c5ce54f54549a986fda2aDEC7`, `tick()V` lines 1153-1185 adds `entity.needsSync`; `ServerEntity.sendChanges()` reads `needsSync` in the corresponding branches.
- A/B evidence for Player applicability: `ChunkMap.updatePlayerStatus` calls `DistanceManager.addPlayer(SectionPos.of(player), player)` when the player is registered and not skipped (A lines 980-992; B lines 1014-1025). `DistanceManager.addPlayer` writes a `PLAYER_SIMULATION` ticket at that chunk (A lines 111-118; B lines 109-116); `getPlayerTicketLevel` is `max(0, ENTITY_TICKING_LEVEL - simulationDistance)` (A lines 133-135; B lines 131-133), while `inEntityTickingRange` is true for levels `<=31` (A/B `ChunkLevel.java` lines 57-59). Hashes: `DistanceManager.java` A `0ba93374eac090dc6d787729c0e346fff6e59f27c8cbdc5fcddcf38809f482cb`, B `aa87548c530aabf26355f644ec206c7c476c11e8ebb71b157b5d191d35a7a320`; `ChunkLevel.java` A `c4b787197902407bb53222c00f14bb04edc23afec765afe7732bd873348747e8`, B `686bdc7528a11dd9dc5c91edfad2c6a9439e788be641befec60e6432f7ba5a29`.
- Movement of the ticket anchor: `ServerChunkCache.move(ServerPlayer)` delegates to `ChunkMap.move` in both versions (A `ServerChunkCache.java` lines 507-512, SHA-256 `b78230ad7b5ec94e073cabca9a262b924375d9d4b3d609a02176bf646eac5748`; B lines 501-507, SHA-256 `e49e4b0c7fadfe114eb6bd541da34f9145874997e54a78adbe3735893d95bff1`). `ChunkMap.move` compares prior/current `SectionPos`, removes the old simulation ticket, and adds one at the new player section (A lines 1008-1026; B lines 1042-1064), maintaining the active Player target precondition during movement.
- State producers/writers -> consumers/readers: B entity sync writers set `needsSync`; `ChunkMap.tick()` can now schedule the entity replication consumer while outside entity-ticking range. A has no `hasImpulse` check in the corresponding `ChunkMap.tick()` method. The active Player target's own section remains in entity-ticking range through its simulation ticket, so `ChunkMap.tick()` already calls `sendChanges()` for the direct Player response in both versions.
- Parent slices / dependencies / closure evidence: `S7-player-knockback-vector`; `D-VELOCITY-SYNC-CONSUMER` closed for active, non-spectator Player targets by the paired `ChunkMap` call condition and Player simulation-ticket path. B's additional out-of-range scheduling for other tracked entities is outside this direct Player slice.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for an active, non-spectator ServerPlayer target, the player's own section receives an entity-ticking simulation ticket in both versions, so the new `needsSync` fallback is not needed for direct Player knockback replication. The bounded Player response has no sync-scheduling difference; B's additional call path concerns entities outside the Player's own active range, which this direct Player slice excludes.
- Finding IDs or checked absence/replacement path: checked pair comparison; no direct Player knockback sync-scheduling delta under the stated Player-ticket precondition. The general out-of-range entity-sync change is recorded as out of this direct Player slice.

### Slice X-scope-exclusions: health, regeneration, hunger/food/saturation/exhaustion/damage/combat emulation and non-player physics

- Inventory ID(s): INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: pending pair source and method correspondence; split provisional buckets into bounded member-level slices.
- A evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- B evidence: pair root and source manifest verified; exact owner/member/descriptor, body line range and SHA-256 still required.
- State producers/writers -> consumers/readers: pending source call graph and field writer/consumer inventory.
- Parent slices / dependencies / closure evidence: `D-METHOD-BODY-REVIEW` plus dependencies discovered from both exact source trees; unresolved.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending; no behavioral conclusion can be drawn before source and dependency review.
- Finding IDs or checked absence/replacement path: no slice-level source disposition confirmed; first-pass comparison is underway.

## Dependency queue and blockers

- Open dependencies:
  - `D-METHOD-BODY-REVIEW`: continue the method-bounded source walk and producer/consumer closure across all inventories.
  - `D-S3-CLIMBABLE-PUSHABILITY` (parent `S3-climb`, `S4-move-core`): A/B `LivingEntity.isPushable()Z` lines 3077-79 / 3203-05 call `onClimbable()` and Player inherits the method; A/B `Entity.pushEntities()` callsites lines 1737-44 / 1755-62 gate both direct `push(...)` writes on `isPushable()`. Trace the method caller/tick path, Player-side velocity write and exact preconditions, then classify the direct Player movement response under the stated non-player movement exclusion. Owner: discovery worker.
  - `D-S3-CLIMBABLE-LAST-POS`: `onClimbable()` also writes `lastClimbablePos`; the only accessor reference found is `world/damagesource/FallLocation.java` (A line 37, B line 36). This is damage classification, excluded from the campaign; no movement consequence is claimed from this state write. Owner: discovery worker; disposition: excluded.
  - `D-S3-CLIMBABLE-START`: A/B `LocalPlayer.aiStep()` callsites lines 774 / 815 test `!onClimbable()` before `tryToStartFallFlying()`. The paired Player methods lines 1376-83 / 1441-48 return false while already fall-flying, so this sibling caller does not start flight or send the start packet under the finding's state; the climbable-position write is covered above. Disposition: no additional in-scope motion delta found for this caller.

## Finding index

Three source-confirmed findings are recorded in `findings/F-S1-FLIGHT-VEHICLE-GATE.md`, `findings/F-S1-CLIENT-LOAD-GATE.md`, and `findings/F-S3-GLIDE-THROUGH-CLIMBABLE.md`. Finding-specific independent review is pending for all three. `F-S3-GLIDE-THROUGH-CLIMBABLE` has an immutable, unaccepted finding snapshot; the other two findings have no snapshot. No implementation handoff or pair-complete conclusion is authorized by this state.

## Resume checkpoint

- Last completed slice: twelve bounded slices are compared-no-difference (input sampling, sprint transitions, knockback vector math, Player knockback sync scheduling, food/passenger sprint eligibility, Player travel dispatch, water travel, lava travel, fall-flying travel, jump impulse, air/ground travel, and sneak/item-use input scaling with edge retreat). The complete `Entity.move(MoverType,Vec3)V` bodies match, but `S4-move-core` remains in-progress while its caller/state dependencies and `D-S3-CLIMBABLE-PUSHABILITY` are resolved. Three source-confirmed findings remain in source review; `F-S3-GLIDE-THROUGH-CLIMBABLE` has immutable snapshot commit `1bc51109c02daf84c84c622ebf0bdd88f25f6729` with reviewer decision pending.
- Next bounded slice and exact files/members/body ranges to open: close `D-S3-CLIMBABLE-PUSHABILITY` by tracing A/B `LivingEntity.isPushable()Z` lines 3077-79 / 3203-05 and `Entity.pushEntities()` callers around lines 1737-44 / 1755-62, including direct Player velocity writes; then begin `S4-collision-query` with `Entity.collide(Vec3)V` A lines 1025-1054 and B lines 1044-1073.
- Outstanding dependencies and owners: `D-METHOD-BODY-REVIEW` (discovery worker); newly discovered producer/consumer, shape, registration and data dependencies will be added with exact owners/actions.
- Current assumptions requiring verification: all listed ready/source/artifact hashes were verified. Remaining assumptions: exact member correspondence, operation/callback order, every reachable player state writer and producer/consumer dependency, relevant jar resource entries, and source-level movement semantics.

## Finding snapshots (not pair freeze)

- `F-S3-GLIDE-THROUGH-CLIMBABLE`: immutable finding snapshot commit `1bc51109c02daf84c84c622ebf0bdd88f25f6729`; finding file SHA-256 `3d3f9a035bc14e7d4ef016acdb47184a372e6880f74d5afa061d45ab6218e3ed`. It cites original-verified publication records `PUB-1.21.10-MOJMAP-2026-10-07` and `PUB-1.21.11-MOJMAP-2026-10-07` and is bounded to the direct `travelFallFlying()` Player branch under the stated block, fall-flying, spectator, flight-ability and fluid-branch preconditions. The separate inherited `isPushable()` consumer remains open under `S4-move-core`; it is not claimed by this snapshot. Finding-specific blind source review is routed to reviewer thread `01a116ce-dfe4-7b11-b7e6-d62f5014e356`; decision pending. Snapshot acceptance and implementation handoff are pending.
- `F-S1-FLIGHT-VEHICLE-GATE` and `F-S1-CLIENT-LOAD-GATE` remain source-confirmed but have no immutable snapshot; finding-specific independent review is pending.
- No accepted finding snapshot exists. This incremental snapshot does not freeze the pair, complete any other coverage slice, or authorize a pair-complete claim.
## Implementation reconciliation

- Reconciliation status: pending
- Repository revision inspected: not inspected; blind source-only constraint.
- Finding -> implementation disposition/evidence: deferred until after blind-discovery freeze and assigned to an implementation reconciler.
- Existing implementation without a frozen source finding: not inspected; deferred.
- Coverage gaps routed back to discovery slices: pending source audit.

## Independent source audit

- Reviewer: pending assignment; reviewer must not be a discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: pending.
- Concrete missed-slice routes (or `none found`): pending.
- Misses routed to slice/finding IDs and owners: pending.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: 34 pending; 1 in-progress; 12 compared-no-difference; 3 findings; 2 not-applicable; 0 blocked. Pair provenance is verified; all three finding slices have source evidence and remain subject to independent finding review; other slices remain open.
- Required inventory status and evidence: all seven inventories pending method-bounded traversal, producer/consumer linkage and full closure. Source roots and artifact hashes are verified above.
- Open dependencies: `D-METHOD-BODY-REVIEW` and all method/resource dependencies discovered during the seven-stage walk.
- Unresolved gaps and limits: pair provenance is verified; three bounded movement/tick-gate deltas are source-confirmed, while the remaining movement behavior is not yet covered. At this handoff, open source slices and unassigned independent review require partial status.
- Evidence/hash/correspondence audit: ready JSON, provenance, source/artifact manifests and diagnostics hashes are verified; full source-file and cache-artifact inventories match the markers. Per-slice source ranges and hashes are recorded for closed slices; direct inspection remains pending for open slices.
- Full-pair blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Accepted finding snapshots: none. Full-pair freeze: pending. Implementation reconciliation: pending. Independent audit: pending. Runtime validation: not performed; not authorized.

The completion checker validates schema/status only and cannot establish source truth. The independent source audit remains mandatory. This is a partial checkpoint; no pair-complete conclusion is claimed. Keep this folder limited to run.md and optional findings/*.md.
