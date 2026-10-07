# Discovery: 1.21.5 to 1.21.8

- Status: active
- Scope: exact adjacent content-update pair; older A = 1.21.5; newer B = 1.21.8. Direct client-player movement only. Excludes health, regeneration, hunger, food, saturation, exhaustion and damage/combat systems and their producers. An in-scope movement predicate may consume vanilla state from those systems; compare the consumer logic for supplied values without emulating or deriving findings from producer-system changes. Excludes independent non-player movement and historical behavior for modern-only blocks/states.
- Repository revision and start date: source branch created from `main` at `002137b227676caea77f6832b9f4c8d0b6200bff`; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: A is exact-release Mojmap / `mojmap` and its marker is hash-verified; B and pair alignment remain pending. Campaign policy selects Mojmap from 1.14.4 onward.
- Source preparation owner / command / log / readiness marker: shared source owner; A exact 1.21.5 readiness marker, hashes and command/log are verified above. B exact 1.21.8 marker is pending. No local decompilation run.
- Toolchain/decompiler/remapper versions and options: A verified in the 1.21.5 provenance record below; B pending its readiness record.
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
- Pair alignment to B remains pending its exact readiness record and method correspondence.

### B — 1.21.8

- Requested/resolved release, source root, client jar identity/hash, CLI mode, mapping coordinate/build/path/hash, remapped jar hash, relevant source/resource hashes, diagnostics: pending source-owner readiness record.
- Readiness record: not published at last check. Exact source root is not considered ready based on directory presence.

No pairwise method comparison or semantic evidence has started. Both source-owner publications must cite exact requested/resolved IDs, SHA-256 source/artifact manifests and movement-method diagnostics before use.

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
- `INV-STATE` movement-state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, support, timers and direct predicates: status=pending; slice_ids=S2.1,S2.2,S2.3,S2.4; evidence=A exact source marker verified and tick/input state subset inventoried; remaining producer/consumer inventory and B pending.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S4.1,S4.2,S4.3,S4.4,S5.2,S5.4; evidence=A exact source tree ready/hash-verified; collision/shape inventory pending, B publication pending.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, resource data/tags and defaults: status=pending; slice_ids=S5.1,S5.2,S5.3,S5.4; evidence=A exact source/artifact manifest verified; behavior/resource inventory pending, B publication pending.
- `INV-MODIFIERS` direct movement attributes, effects, enchantments, equipment and application/removal/conditions: status=pending; slice_ids=S6.1,S6.2,S6.3; evidence=A exact source/artifact manifest verified; producer/consumer and resource inventory pending, B publication pending.
- `INV-EXTERNAL` player-only external movement inputs and client consumers including corrections, pushes, pistons and mounts: status=pending; slice_ids=S7.1,S7.2,S7.3; evidence=A exact source tree ready/hash-verified; external consumer/writer inventory pending, B publication pending.
- `INV-EXCLUSIONS` scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=awaiting source-backed disposition of direct vanilla-state reads and excluded producer systems.

## Coverage ledger

Every row below is an unfinished discovery unit, not a claim that a method has been checked. Exact A/B methods, body ranges and hashes will be added only after validated readiness records. Split or add rows whenever source inventory reveals narrower behaviors or new dependencies.

### Slice S1.1: input sampling and vector shaping

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: input source sampling through produced movement vector; not yet checked.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.21.5/mojmap/net/minecraft/client/player/KeyboardInput.java::KeyboardInput#tick()`, lines 23-36, SHA-256 `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3`; `ClientInput#getMoveVector()`, lines 13-15, SHA-256 `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`; `Input` record/default, lines 6-39, SHA-256 `1c0c12af07da1f90651c3a7c4eb19bab6486b6391dcbfdd2926f06e3f36c2f06`; `Vec2#normalized()`, lines 48-51, SHA-256 `b637ef6899de5d8892e19273b114ffaf0397761e509170a56d52dd068f54bffe`; `LocalPlayer#applyInput()`, lines 608-621, `modifyInput(Vec2)`, lines 623-639, plus `modifyInputSpeedForSquareMovement(Vec2)` / `distanceToUnitSquare(Vec2)`, lines 641-658, file SHA-256 `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`. A input storage/writer path: `LocalPlayer.input` initializes to `new ClientInput()` at line 120; `ClientPacketListener#handleLogin(...)` lines 436-497 and `handleRespawn(...)` lines 1152-1229 assign KeyboardInput; `LocalPlayer#aiStep()` lines 693-703 reads and ticks input. `ClientPacketListener.java` SHA-256 `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617`. Older-side inventory only; B correspondence pending.
- B evidence: pending 1.21.8 publication and method inventory.
- State producers/writers -> consumers/readers: A LocalPlayer.input default -> login/respawn KeyboardInput replacement -> KeyboardInput.tick writes keyPresses/moveVector -> LocalPlayer.aiStep reads pre-tick forward/jump/shift values, then ticks input -> LocalPlayer.applyInput reads moveVector/keyPresses and writes xxa/zza/jumping. B trace pending; no pair disposition yet.
- Parent slices / dependencies / closure evidence: source dependencies DEP-SRC-A and DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; no equivalence claim.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S1.2: local-player tick and pre-travel ordering

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: local player tick sequence through pre-travel state writes and call into superclass/travel; not yet checked.
- A evidence: `LocalPlayer#tick()`, lines 191-217, SHA-256 `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`; `Player#tick()` lines 271-348 and `Player#aiStep()` lines 554-607, file SHA-256 `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036`; `LivingEntity#tick()` lines 2460-2572 and `LivingEntity#aiStep()` lines 2669-2804, file SHA-256 `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`; `LocalPlayer#aiStep()` lines 683-835 (same LocalPlayer hash); `Entity#tick()` lines 438-440 and `Entity#baseTick()` state-init excerpt lines 442-463, file SHA-256 `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`.
- B evidence: pending 1.21.8 publication and tick-chain inventory.
- State producers/writers -> consumers/readers: A call order verified: LocalPlayer.tick -> Player.tick -> LivingEntity.tick -> Entity.tick/baseTick -> dynamic LocalPlayer.aiStep -> Player.aiStep -> LivingEntity.aiStep. Entity.baseTick initializes inBlockState and powder-snow/fluid/swimming flags before AI (Entity.java:445,459-463); Player.tick updates underwater state before super.tick and pose after it. LivingEntity.aiStep dispatches applyInput, jump, then ridden/travel branch; Player.aiStep updates movement speed after super.aiStep returns. B chain pending; no pair disposition yet.
- Parent slices / dependencies / closure evidence: S1.1; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; sprint food/hunger producer systems excluded.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S1.3: jump, sprint timing and direct eligibility predicates

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: jump/sprint timers, start/stop predicates and direct movement inputs; compare movement predicate/timer consumption for supplied vanilla values; exclude producer-system emulation or findings about changes in hunger/food systems.
- A evidence: `LocalPlayer#aiStep()` sprint timer/start/stop slice, lines 683-744; `shouldStopRunSprinting()` / `shouldStopSwimSprinting()`, lines 837-852; `canStartSprinting()` / `vehicleCanSprint()` / `hasEnoughFoodToSprint()`, lines 1065-1083; all in `LocalPlayer.java`, SHA-256 `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`.
- B evidence: pending 1.21.8 publication and method inventory.
- State producers/writers -> consumers/readers: A sprintTriggerTime and sprinting are written/read in `LocalPlayer#aiStep`; start/stop gates consume key/forward impulse, passenger authority, item use, blindness, water state and `hasEnoughFoodToSprint()` (directly reads food level at lines 1081-1083). This records only a vanilla-state consumer; food producers remain excluded. B path pending.
- Parent slices / dependencies / closure evidence: S1.2; S2.3; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S1.4: input modes, flight, auto-jump, unstuck and riding gates

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: alternate input producers, auto-jump, flight-toggle/unstuck behavior and riding gates on the local player path; not yet checked.
- A evidence: `LocalPlayer#aiStep()` auto-jump/input and flight/riding slices, lines 705-716 and 746-830, SHA-256 `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`; `ClientInput#makeJump()`, lines 21-31, SHA-256 `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`; input assignment evidence from `ClientPacketListener` in S1.1.
- B evidence: pending 1.21.8 publication and method inventory.
- State producers/writers -> consumers/readers: A `LocalPlayer#aiStep` snapshots old keyPresses before `input.tick()` (693-703); then decrements autoJumpTime and calls `ClientInput.makeJump()` (705-709), after keyboard sampling. Mayfly/fall-flying/water-descent and vehicle jump state continue in the same method. B timing and producer closure pending; no pair disposition.
- Parent slices / dependencies / closure evidence: S1.1; S1.2; S2.2; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S2.1: pose, dimensions and eye-height state

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: pose selection/transitions, dimensions and eye height including query consumers; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending transition/default writers to collision and fluid-query readers.
- Parent slices / dependencies / closure evidence: S4.1; S4.3; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S2.2: movement flags and repeated state writers

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: position, velocity, on-ground/collision/fluid/support flags, stored air speed and transition/reset timing; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending all-writer/consumer inventory.
- Parent slices / dependencies / closure evidence: S2.1; S3.1-S3.5; S4.1-S4.4; S7.1-S7.3; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S2.3: direct movement predicates and active-item state

- Inventory ID(s): INV-STATE, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: in-scope predicates reading player/active-item/vanilla system state; exclude producing/emulating health, food, exhaustion, damage and combat systems.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending direct read map; excluded producer systems will be marked out of scope.
- Parent slices / dependencies / closure evidence: S1.3; S6.3; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S2.4: swimming/crawling pose and flight ability state

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: swimming/crawling transitions, flight abilities/speed and state reset/update timing; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending state writer-to-tick/travel/collision consumer map.
- Parent slices / dependencies / closure evidence: S2.1; S2.2; S3.1; S3.3; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S3.1: travel dispatch and ground/air movement math

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: travel dispatch, branch predicates, ground/air acceleration/friction and exact arithmetic order; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending input/attribute/velocity consumers and post-travel writes.
- Parent slices / dependencies / closure evidence: S1.1-S1.4; S2.2; S6.1; S6.2; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S3.2: jump, sprint-jump, gravity, drag and velocity thresholds

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: jump-power/impulse math, gravity/drag, negligible-velocity cutoffs and order; exclude food/hunger producer-system emulation or findings about those systems; direct movement predicates remain in scope.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending direct jump/effect inputs and velocity writes/readers.
- Parent slices / dependencies / closure evidence: S1.3; S2.2; S6.1; S6.2; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S3.3: water, lava and swimming movement

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: fluid travel branches, depth/current/flow decisions and swimming motion; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending fluid state/property/resource producers to local-player travel consumers.
- Parent slices / dependencies / closure evidence: S2.1; S2.4; S5.3; S6.1; S6.2; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S3.4: climbing and fall-flying movement

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: climbable movement/clamps and gliding/fall-flying math, guards and direct equipment/effect inputs; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending climbable/elytra/effect/equipment producer-to-player consumer trace.
- Parent slices / dependencies / closure evidence: S2.4; S5.2; S6.1; S6.2; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S3.5: post-travel work and callbacks

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: post-move updates, fall-distance/flags and callbacks that directly change player position/velocity/pose; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending post-travel writer-to-next-tick consumer map.
- Parent slices / dependencies / closure evidence: S2.2; S4.4; S5.2; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S4.1: player Entity.move collision resolution

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: bounding-box and position movement, collision query/order, axis resolution and velocity cancellation; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending movement input and box/velocity/on-ground writes to later travel readers.
- Parent slices / dependencies / closure evidence: S2.1; S2.2; S4.3; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S4.2: step-up, edge probes and support lookup

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: candidate step paths/tie-breaks, edge restraint, support position and on-ground query bounds; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending collision shape/support query producer-to-ground and movement consumer map.
- Parent slices / dependencies / closure evidence: S2.1; S4.1; S4.3; S5.2; S5.4; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S4.3: world collision queries and shape evaluation

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: player-reachable collision query, AABB/shape iteration, context inputs and selection/tie rules; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending entity box/context to registered shape-provider chain.
- Parent slices / dependencies / closure evidence: S2.1; S4.1; S4.2; S5.2; S5.4; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S4.4: collision, fluid and block callback ordering

- Inventory ID(s): INV-COLLISION, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: callbacks after movement/collision and fluid contact/push that directly change local-player state; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending callback ordering and position/velocity/fluid flag edges.
- Parent slices / dependencies / closure evidence: S3.3; S4.1; S5.2; S5.3; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S5.1: block movement properties and registrations

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: friction, speed/jump factors, registry defaults and all relevant assignments/overrides; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending registration/property producer-to-player travel consumer trace.
- Parent slices / dependencies / closure evidence: S3.1; S3.2; S4.1; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S5.2: shape providers, movement callbacks and subclasses

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: registered/overridden shape and callback providers including landing/bounce/contact/climb/slowing mechanics; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending registrations, neighboring-block inputs and player callback consumers.
- Parent slices / dependencies / closure evidence: S3.4; S3.5; S4.1-S4.4; S5.4; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; do not give modern-only states historical behavior.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S5.3: fluids, flow vectors, height and current

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: fluid state, contact, height/flow/current calculation and movement push providers; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending fluid registry/data to player immersion/travel/push consumers.
- Parent slices / dependencies / closure evidence: S3.3; S4.4; S5.4; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison.
- Finding IDs or checked absence/replacement path: none yet.

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
- Exact behavior boundary and enclosing guards/order checked: direct movement attribute consumers, modifier aggregation/order, effect formulas and movement-affecting registration values; not yet checked.
- A evidence: pending source publication and matching resources.
- B evidence: pending source publication and matching resources.
- State producers/writers -> consumers/readers: pending effect/application/attribute producers to jump/travel/input consumers.
- Parent slices / dependencies / closure evidence: S1.1; S3.1-S3.4; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source and resource comparison; exclude health/food producer systems.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S6.2: movement enchantments and equipment conditions

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Depth Strider, Soul Speed, Swift Sneak, Frost Walker, Riptide, Elytra and any other direct movement equipment/enchantment formulas, slots, predicates and tags; not yet checked.
- A evidence: pending source publication and matching resources.
- B evidence: pending source publication and matching resources.
- State producers/writers -> consumers/readers: pending equipment/tag/condition producers through attribute/helper to player movement consumer.
- Parent slices / dependencies / closure evidence: S3.1-S3.4; S5.4; S6.1; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source and resource comparison; server-side Frost Walker world mutation is an explicit provenance boundary.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S6.3: use-item slowdown and movement components

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: active item/use state, slowdown and movement-relevant item components along reachable player input path; not yet checked.
- A evidence: pending source publication and matching resources.
- B evidence: pending source publication and matching resources.
- State producers/writers -> consumers/readers: pending item/application/component producer-to-input scaling consumer trace.
- Parent slices / dependencies / closure evidence: S1.1; S2.3; S6.1; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source and resource comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S7.1: incoming velocity and position corrections

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: local client packet consumers that write player position/velocity/flags and their acknowledgment/timing path; not yet checked.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending packet inputs to local-player state writers and following tick consumers.
- Parent slices / dependencies / closure evidence: S1.2; S2.2; S3.1; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; server-supplied values identified separately.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S7.2: player pushes, pistons, mounts and launch items

- Inventory ID(s): INV-EXTERNAL, INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: client-side player consumers of external pushes, piston movement, mount/dismount and launch-item movement inputs; exclude other-entity simulation.
- A evidence: pending source publication.
- B evidence: pending source publication.
- State producers/writers -> consumers/readers: pending external producer-to-local-player movement state writer trace.
- Parent slices / dependencies / closure evidence: S2.2; S4.4; S5.2; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending source comparison; external authority boundary will be recorded.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S7.3: final reachable movement-writer dependency closure

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT, INV-MODIFIERS, INV-EXTERNAL, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: cross-stage revisit of all discovered reachable player movement state writers, changed helpers, registrations, resources and producer/consumer edges; not yet checked.
- A evidence: pending full pair inventory.
- B evidence: pending full pair inventory.
- State producers/writers -> consumers/readers: pending all-stage dependency graph.
- Parent slices / dependencies / closure evidence: S1.1-S7.2; DEP-SRC-A; DEP-SRC-B.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending final source audit; cannot close any stage from a narrow travel comparison.
- Finding IDs or checked absence/replacement path: none yet.

## Dependency queue and blockers

- `DEP-SRC-A` / all slices / exact 1.21.5 source preparation / resolved: exact marker IDs are 1.21.5 / 1.21.5, namespace Mojmap, marker and source/artifact/diagnostic hashes verified, and nine cited input/player source hashes verified; continue body-level checks as slices are inventoried.
- `DEP-SRC-B` / all slices / same validated publication for exact 1.21.8 / required to establish alignment and compare pair / source preparation owner / unresolved; shared source queue is active, so this is pending, not currently blocked.
- More dependencies will be added as exact source inventories resolve callers, field writers, registrations and resource data.

## Finding index

- Confirmed findings: none yet.
- Candidate findings: none yet.
- Discarded candidates: none yet.

## Resume checkpoint

- Last completed slice: none; all current rows await exact-source publication.
- Next bounded slice and exact files/members/body ranges to open: complete the A-side stage 1 caller/state-edge index and input helper coverage; then validate the 1.21.8 readiness record and resolve matching KeyboardInput/ClientInput/LocalPlayer/LivingEntity members.
- Outstanding dependencies and owners: DEP-SRC-B (shared source preparation owner). A source root is ready; body/semantic closure remains assigned to this inventory, not a source-publication dependency.
- Current assumptions requiring verification: aligned Mojmap namespace and exact release resolution; manifest/source hashes; source diagnostics; class/member correspondence; resource availability and contents.

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

- Coverage counts by status: 27 pending; 0 in-progress; 0 compared-no-difference; 0 findings; 0 not-applicable; 0 blocked (all 7 inventories pending).
- Required inventory status and evidence: INV-TICK has an A-side input/tick-chain subset; INV-STATE has the associated input/timing subset; other stages and the full exclusions inventory remain pending. A source is hash-verified; exact B evidence is not yet available.
- Open dependencies: DEP-SRC-B (shared source preparation owner).
- Unresolved gaps and limits: A-side input/tick source inventory has begun, but no A/B behavior comparison has started because exact 1.21.8 is unpublished. Source publication queue is not a terminal disposition.
- Evidence/hash/correspondence audit: A readiness JSON and cited source/manifest hashes verified; A tick/input bodies read; exact B readiness, pair correspondence and remaining member/resource/body diagnostics pending.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (not authorized).

