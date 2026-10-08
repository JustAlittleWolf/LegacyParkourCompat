# Discovery: 26.1.2 to 26.2

- Status: active
- Scope: exact adjacent endpoint comparison, source-only player movement; older A = 26.1.2; newer B = 26.2. Direct player velocity/impulse/knockback response is in scope even when combat can trigger it; attack/damage resolution and health/food-state production are excluded. Non-player movement and vehicle physics are excluded. No runtime Java implementation, wiki browsing, release-notes mechanics, tests, client/TAS/Gym/server/Docker launches. Runtime validation is not authorized.
- Repository revision and start date: branch started at main `002137b227676caea77f6832b9f4c8d0b6200bff`; dedicated branch `feat/source-discovery-movement-source-26-1-2-26-2`; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: both endpoints are native Java 26 unobfuscated sources in Mojang official names; CLI `unobfuscated` for A and B. Current A and B readiness markers verify `versionId=26.1.2` and `versionId=26.2` respectively. Each is native `unobfuscated`; pair alignment is verified. Prior source report was used as navigation only; none of its hashes or conclusions are current evidence.
- Source preparation owner / command / log / readiness marker: source owner is the campaign's single shared-source owner; personal name not present in this worker handoff. A provenance `ready/26.1.2/unobfuscated.provenance.json` records `.\gradlew.bat decompileMinecraft --versions=26.1.2 --mappings=unobfuscated --decompiler-heap=4G --output-root=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\staging\unobfuscated-26.1.2-3db218180ac44a64ad496eeb8e05ac18\output --cache-directory=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\staging\unobfuscated-26.1.2-3db218180ac44a64ad496eeb8e05ac18\cache`; `unobfuscated.success.log` reports exit code 0 and the full Gradle log is persisted under the staging root. B `ready/26.2/unobfuscated.provenance.json` records the exact command, and its success excerpt says `BUILD SUCCESSFUL`; the initial full stdout was streamed but not persisted. This worker has not run decompilation.
- Toolchain/decompiler/remapper versions and options: both provenance records agree on Vineflower 1.12.0, Java 25.0.3+9-LTS, Gradle 9.7.1, Mapping IO 0.9.1, ASM 9.10.1, Tiny Remapper 0.14.1 and Gson 2.14.0; both use a 4G decompiler heap and native unobfuscated input. The source-owner personal name is not present, and B's full Gradle output was not persisted; exact preparation identity/toolchain are otherwise resolved.
- Discovery author(s): source-only worker for this exact pair.
- Independent reviewer (must differ from discovery authors): not assigned in the available task instructions.

## Artifact manifest

### A — 26.1.2

- Exact release: readiness JSON `ready/26.1.2/unobfuscated.ready.json`, `versionId=26.1.2`, status `ready`; source root `build/movement-campaign-2026-10-07/ready/26.1.2/unobfuscated/`; native `unobfuscated` namespace; original client jar is the decompiler input. Ready JSON reports Vineflower, Java runtime 25, 6,882 source files / 32,992,615 bytes.
- Original client jar `ready/26.1.2/artifacts.sha256` entry `26.1.2/client.jar`, SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`; the jar in the exact run's isolated staging cache at `staging/unobfuscated-26.1.2-3db218180ac44a64ad496eeb8e05ac18/cache/26.1.2/client.jar` was rehashed and matches. Version metadata says ID 26.1.2. Mappings and remapped jar: not applicable, published unobfuscated.
- Source manifest `ready/26.1.2/unobfuscated.sources.sha256`, SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`.
- Artifact manifest `ready/26.1.2/artifacts.sha256`, SHA-256 `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`.
- Movement-method diagnostics `ready/26.1.2/movement-diagnostics.txt`, SHA-256 `b4bd3875263a7155ac32cffd64cfacabc7bd54c82c5fe4082f1ef8a809024c02`. It lists Entity.move/moveRelative, LivingEntity.jumpFromGround/travel/aiStep and Player.aiStep/travel; diagnostics are anchor discovery only and do not establish complete coverage.
- Direct A source hashes, recomputed and matching the A source manifest: `net/minecraft/client/player/KeyboardInput.java` `b4bbb410650444c30d2a62d70fd3c6cd1aefa104b8a12e474e2a478a62089c39`; `net/minecraft/client/player/LocalPlayer.java` `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; `net/minecraft/world/entity/LivingEntity.java` `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`; `net/minecraft/world/entity/Entity.java` `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`; `net/minecraft/world/entity/player/Player.java` `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`; `net/minecraft/world/entity/ai/attributes/Attributes.java` `8eee57d8375c7af39525ccd25593618fcbcbe0348da6a8738556baaabea3c1d5`.

### B — 26.2

- Exact release: readiness JSON `ready/26.2/unobfuscated.ready.json`, `versionId=26.2`, status `ready`; source root `build/movement-campaign-2026-10-07/ready/26.2/unobfuscated/`; native `unobfuscated` namespace; original client jar is the decompiler input. Ready JSON reports Vineflower, Java runtime 25, 7,055 sources / 33,813,717 bytes.
- Original client jar `build/movement-campaign-2026-10-07/artifacts/26.2/client.jar`, SHA-256 `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290`; rehashed file matches marker and artifact-manifest entry. Version metadata says ID 26.2. Mappings and remapped jar: not applicable, published unobfuscated.
- Provenance `ready/26.2/unobfuscated.provenance.json` records exact command `.\gradlew.bat decompileMinecraft --versions=26.2 --mappings=unobfuscated --decompiler-heap=4G --output-root=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\staging\26.2-unobfuscated-3f17fe9da1bd4a17918a5b39990fcad5 --cache-directory=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts`; toolchain versions match A. The concise success excerpt is persisted as `unobfuscated.success.log`; the full initial stdout was not persisted.
- Source manifest `ready/26.2/unobfuscated.sources.sha256`, SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.
- Artifact manifest `ready/26.2/artifacts.sha256`, SHA-256 `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`.
- Movement-method diagnostics `ready/26.2/movement-diagnostics.txt`, SHA-256 `ba6fd6c5b1c77b3ee2988f0c54968cc5b915680670fdb66699dbcbf8b9fe21d3`. It lists decompiled anchors for Entity.move/moveRelative, LivingEntity travel/jump paths, Player.travel and LocalPlayer.aiStep. The cited file hashes and bodies have been checked on B; method anchors do not establish pair coverage or the integrity of unlisted methods.
- Direct B source hashes, each recomputed and matching the B source manifest: `net/minecraft/client/player/KeyboardInput.java` `b4bbb410650444c30d2a62d70fd3c6cd1aefa104b8a12e474e2a478a62089c39`; `net/minecraft/client/player/LocalPlayer.java` `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; `net/minecraft/world/entity/LivingEntity.java` `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; `net/minecraft/world/entity/Entity.java` `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`; `net/minecraft/world/entity/player/Player.java` `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; `net/minecraft/world/entity/player/Abilities.java` `e96537b6f633aa2b16e6ffd7b324c06f75ee0dd15597e7c583db833a6acb1a32`. Add source/resource hashes when further files are cited.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: not frozen; current active checkpoint commit `4947258` includes workflow hardening only and is not a discovery freeze.
- Evidence inventory and finding IDs included at freeze: none; no pairwise findings confirmed yet.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; neither implementation nor wiki/audit output has been opened. No wiki browsing.
- Source/mapping hashes covered by freeze: pending both-side inventory closure.

## Correspondence and call order

Pair-specific comparison is underway. `S-IN-01`, `S-IN-BASE` and the bounded snapshot range are dispositioned; remaining B anchors below are inventory seeds, not guessed A/B equivalence. All other class/member descriptors and complete caller/dependency chains must be checked on both sides.

Initial B-only call-order anchors inspected (all SHA-256 values in B manifest above):

- `KeyboardInput.tick()` samples key presses, creates move impulses and normalizes a `Vec2` (`KeyboardInput.java:14-36`). A tick producer/consumer chain still needs tracing on both sides.
- `LocalPlayer.aiStep()` snapshots jump/shift/forward flags before `input.tick()`, computes crouch, applies auto-jump and unstuck probes, then applies sprint gates, flight and riding-jump work before `super.aiStep()` (`LocalPlayer.java:767-919`).
- `LivingEntity.aiStep()` applies a player-specific horizontal velocity cutoff at `3060-3083`, input and jump handling at `3084-3123`, travel dispatch at `3125-3141`, block effects at `3143-3145`, and client animation at `3147-3149`. The nearby non-player per-axis cutoff (`3069-3076`) is out of movement-emulation scope but its branch guard is recorded so it is not confused with the player path.
- `LivingEntity.travel()` dispatches fluid, fall-flying, or air travel (`2430-2437`); `Player.travel()` adds swimming and ability-flight handling (`Player.java:1402-1429`). Exact callees and post-travel work remain to inventory and compare.
- `Entity.move()` performs edge adjustment and collision, movement/position and collision flags, fall damage check, collision restitution, movement emission, then block-speed velocity scaling (`Entity.java:711-800`); its collision resolver and shape dependencies must be indexed separately.

## Required source inventories

Each inventory maps to bounded slices below; all are pending. Add all newly discovered writers, consumers, overrides, registrations and resources before disposition.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S-TICK-ENTRY,S-IN-01,S-IN-BASE,S-LOCAL-SNAPSHOT,S-LOCAL-UNSTUCK,S-SPRINT-START,S-SPRINT-STOP,S-FLIGHT-TOGGLE,S-FALLFLY-REQUEST,S-SINK-INPUT,S-FLIGHT-VERTICAL-INPUT,S-RIDEABLE-JUMP,S-LOCAL-INPUT-MODIFIERS,S-SQUARE-MOVE,S-VELOCITY-HORIZONTAL,S-VELOCITY-VERTICAL,S-JUMP-GATE,S-JUMP-IMPULSE,S-JUMP-LIQUID,S-LIVING-GLIDE-UPDATE,S-TRAVEL-PREP,S-FALL-DISTANCE-RESET,S-TRAVEL-GATE,S-POST-BLOCK-EFFECTS,S-POST-ANIMATION,S-TRAVEL-DISPATCH,S-TRAVEL-FLYING,S-TRAVEL-AIR,S-TRAVEL-RELATIVE,S-TRAVEL-FRICTION-SPEED,S-TRAVEL-FLUID-DISPATCH,S-TRAVEL-WATER,S-TRAVEL-LAVA,S-TRAVEL-FLUID-EXIT,S-TRAVEL-FLUID-ADJUST,S-TRAVEL-GLIDE-DISPATCH,S-TRAVEL-GLIDE-FORMULA,S-TRAVEL-CLIMB,S-PLAYER-01,S-POSE-UPDATE,S-PLAYER-AISTEP-PRE,S-PLAYER-AISTEP-POST; evidence=A/B dispositions in S-IN-01,S-IN-BASE,S-LOCAL-SNAPSHOT,S-SQUARE-MOVE,S-TICK-ENTRY; `Player.aiStep` pre/post body slices are compared, with inventory and attribute dependencies still open.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S-TICK-ENTRY,S-IN-BASE,S-LOCAL-SNAPSHOT,S-LOCAL-UNSTUCK,S-SPRINT-START,S-SPRINT-STOP,S-FLIGHT-TOGGLE,S-FALLFLY-REQUEST,S-SINK-INPUT,S-FLIGHT-VERTICAL-INPUT,S-RIDEABLE-JUMP,S-LOCAL-INPUT-MODIFIERS,S-SQUARE-MOVE,S-VELOCITY-HORIZONTAL,S-VELOCITY-VERTICAL,S-POSE-UPDATE,S-DIMENSIONS,S-PLAYER-AISTEP-PRE,S-PLAYER-AISTEP-POST,S-PLAYER-IMPULSE-RESPONSE; evidence=A/B ClientInput defaults plus pending state writer/consumer inventory; Player.aiStep timer/reset, flight impulse, knockback and push responses are separately tracked.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S-MOVE-POS,S-MOVE-FLAGS,S-MOVE-RESTITUTE,S-MOVE-BLOCK-SPEED,S-EDGE-PROBE,S-COLLISION-STEP,S-COLLISION-QUERY,S-COLLISION-AXIS,S-WORLD-01; evidence=B Entity.move range below; provider/registry/neighbor inventory still open.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S-WORLD-01; evidence=B block/fluid providers and original client-jar resource inventory not yet cited.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S-SPRINT-START,S-SPRINT-STOP,S-LOCAL-INPUT-MODIFIERS,S-JUMP-GATE,S-JUMP-IMPULSE,S-TRAVEL-DISPATCH,S-TRAVEL-FLYING,S-TRAVEL-AIR,S-TRAVEL-RELATIVE,S-TRAVEL-FRICTION-SPEED,S-TRAVEL-FLUID-DISPATCH,S-TRAVEL-WATER,S-TRAVEL-LAVA,S-TRAVEL-FLUID-EXIT,S-TRAVEL-FLUID-ADJUST,S-TRAVEL-GLIDE-DISPATCH,S-TRAVEL-GLIDE-FORMULA,S-TRAVEL-CLIMB,S-MOD-01,S-PLAYER-AISTEP-POST,S-PLAYER-IMPULSE-RESPONSE; evidence=B consumers and producer/registration/resource inventory still open.
- `INV-EXTERNAL` player-only externally supplied movement and direct player velocity/impulse/knockback application, plus in-scope player-facing transitions: status=pending; slice_ids=S-FALLFLY-REQUEST,S-EXT-01,S-PLAYER-IMPULSE-RESPONSE; evidence=B local request plus player correction/impulse consumer mapping still open; exclude non-player and vehicle physics.
- `INV-EXCLUSIONS` explicit scope audit for health/food state production, attack/damage resolution, non-player movement and vehicle physics. Direct player-motion response remains in scope even if combat triggers it: status=pending; slice_ids=S-RIDEABLE-JUMP,S-CLIENT-AVATAR-STATE; evidence=campaign scope above, the vehicle-jump source range and the client-avatar consumer search; validate direct vanilla-state reads without emulating their producer systems.

## Coverage ledger

These are now 56 bounded work units, not an exhaustive inventory. Pair comparison has started now that A is ready; all units except any explicitly dispositioned below remain pending. B-only anchors are navigation evidence; do not infer equivalence or absence. Expand the ledger during the pair-specific inventory.

### Slice S-IN-01: Keyboard input sampling and movement-vector construction

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: `KeyboardInput.calculateImpulse` and `KeyboardInput.tick`, key-state sampling, forward/side impulse signs and vector normalization; the LocalPlayer input-tick call edge was also checked.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/KeyboardInput.java::calculateImpulse(boolean,boolean),tick(), lines 14-36`, SHA-256 `b4bbb410650444c30d2a62d70fd3c6cd1aefa104b8a12e474e2a478a62089c39`; the recomputed full-file hash equals B. `Vec2.normalized(),length(),lengthSquared()` and `Mth.sqrt(float)` were read at `Vec2.java:9-25,48-59` and `Mth.java:57-59`, hashes `c52274a57d57b452c3ec349b6336341e7069252183a10838ee7e9e4462f6ba3d` and `19d00e9745368c34692d0dfd7eddec07797790ac7f6e0bc579208b7a4b682d33`; the branch, `1.0E-4F` threshold, square-root input and divide operations match B. Both KeyboardInput methods were read: positive/negative equality yields `0.0F`, otherwise sign is `1.0F` or `-1.0F`; tick samples the same seven key states, constructs `Input`, derives forward/side impulses and normalizes the same `Vec2` in both endpoints. The sole LocalPlayer `input.tick()` call is at line 786 in both A and B, after the same pre-sampling flags and crouch calculation; caller body remainder is tracked separately in S-LOCAL-SNAPSHOT.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/KeyboardInput.java::net.minecraft.client.player.KeyboardInput#calculateImpulse(boolean,boolean),tick(), lines 14-36`, SHA-256 `b4bbb410650444c30d2a62d70fd3c6cd1aefa104b8a12e474e2a478a62089c39`; `Vec2.normalized(),length(),lengthSquared()` and `Mth.sqrt(float)` at `Vec2.java:9-25,57-68` and `Mth.java:58-60`, hashes `284ba455cf02047283a01af3d4c77929cfd9bbcc772b0677de503a3774bd4b51` and `30455c684f2401c79290847822bca82e77162c4a2bcf8618e85cac9898a2dc17`; relevant helper bodies match A.
- State producers/writers -> consumers/readers: options key states -> `Input` keypress snapshot and `moveVector` -> LocalPlayer's single input-tick call and input consumers; the matching call edge is at `LocalPlayer.aiStep():786` in both endpoints. Other consumers remain in their own slices.
- Parent slices / dependencies / closure evidence: D-A-READY resolved; `S-LOCAL-SNAPSHOT` tracks the rest of the caller body and pre-sampling order; input application is `S-LOCAL-INPUT-MODIFIERS`.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the two exact KeyboardInput source files have identical verified SHA-256 hashes, the required method bodies were read, and the single reachable LocalPlayer caller and its relevant preceding order were checked at both endpoints. This disposition is limited to key sampling and movement-vector construction; it does not disposition downstream consumers or other input providers.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-IN-BASE: Default client input state and fallback consumers

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer.input` default initialization and `ClientInput.tick`, `getMoveVector`, `hasForwardImpulse`, and `makeJump`; this is the neutral fallback before keyboard input assignment and covers the auto-jump writer used by `S-LOCAL-SNAPSHOT`.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/ClientInput.java::tick(),getMoveVector(),hasForwardImpulse(),makeJump(), fields, lines 6-32`, SHA-256 `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`; default `input` field in `LocalPlayer.java:134`; `ClientPacketListener.handleLogin` assigns `KeyboardInput` at line 535 and `handleRespawn` assigns it at line 1330. The A listener full-file SHA-256 is `eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/ClientInput.java::tick(),getMoveVector(),hasForwardImpulse(),makeJump(), fields, lines 6-32`, SHA-256 `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`; default `input` field in `LocalPlayer.java:134`; `ClientPacketListener.handleLogin` assigns `KeyboardInput` at line 535 and `handleRespawn` assigns it at line 1328. The B listener full-file SHA-256 is `9cb0cc8afebae9f4e42f428a52719c645817d03893dc371e6711c7bd16eac1b6`.
- State producers/writers -> consumers/readers: default empty `Input` and zero `moveVector` -> no-op tick/readers; `makeJump()` rewrites only jump while retaining other flags. Login/respawn replace the base object with KeyboardInput before active local-player ticks.
- Parent slices / dependencies / closure evidence: `S-IN-01`, `S-LOCAL-SNAPSHOT`, `S-TICK-ENTRY`; field initialization and both reachable assignment sites were checked on both endpoints. Pair tick call graph remains pending in `S-TICK-ENTRY`.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both exact `ClientInput.java` files have the same full-file hash and the same methods/state defaults. The local player defaults to this class and the two observed login/respawn replacement sites use the same subclass assignments; only the independent tick-chain closure remains open.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-LOCAL-SNAPSHOT: Input snapshot, crouch selection and auto-jump ordering

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer.aiStep` captures previous jump/shift/forward state before crouch selection and `input.tick`, then applies auto-jump state; lines 777-793 in both endpoints.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(), lines 777-793`, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`. Extracted full `aiStep()` bodies are equal except for a portal/loading-screen GUI accessor at line 772, outside this slice; the exact covered range 777-793 is identical in A and B.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(), lines 777-793`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.
- State producers/writers -> consumers/readers: prior/current input flags -> crouch, input snapshot, auto-jump timer and synthesized jump input -> sprint/jump and travel consumers.
- Parent slices / dependencies / closure evidence: S-IN-01,S-TICK-ENTRY; S-POSE-UPDATE tracks later pose-fit selection. The A/B range confirms pose-fit predicates gate crouching and input is sampled after the three saved previous-state values.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): exact covered lines 777-793 match on A and B, including pre-sampling values, crouch guards, `input.tick()`, tutorial notification and auto-jump timer/synthetic-jump order. This is bounded to this range; the separate portal GUI gate before it is outside movement-snapshot behavior and the rest of `aiStep` remains open in other slices.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-LOCAL-UNSTUCK: Local player corner probes before movement

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: when physics is enabled, B calls `moveTowardsClosestSpace` at four ordered X/Z corners using `0.35 * bounding-box width`; lines 795-800. Follow helper and collision query on both versions.
- A evidence: pending exact calls, helper and player collision-context correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(),moveTowardsClosestSpace(...), lines 795-800`; `Entity.moveTowardsClosestSpace` helper anchor in `Entity.java:2912`, source hashes recorded for LocalPlayer and Entity in the manifest.
- State producers/writers -> consumers/readers: pose-dependent width/position/noPhysics -> four probe coordinates -> closest-space movement and position.
- Parent slices / dependencies / closure evidence: S-LOCAL-SNAPSHOT,S-DIMENSIONS,S-COLLISION-QUERY; helper body and AABB/world query dependencies pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B corner order read; A behavior unresolved.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-SPRINT-START: Sprint reset, start predicate and trigger timer

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: B clears trigger timer for shift, item slowdown or backward; checks start predicate; applies double-tap trigger timer or sprint key; lines 802-818 and predicate 1141-1148.
- A evidence: pending exact local method and predicate correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(),canStartSprinting(), lines 802-818,1141-1148`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.
- State producers/writers -> consumers/readers: input/shift/backward/item-use/fall-flight/swimming predicates -> sprint flag/trigger timer -> movement speed and travel. Record direct vanilla-state reads but exclude hunger/food producer emulation.
- Parent slices / dependencies / closure evidence: S-LOCAL-SNAPSHOT,S-LOCAL-INPUT-MODIFIERS; sprint possibility predicate and vanilla-state boundary pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B gates read; exact A condition boundary and operation order pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-SPRINT-STOP: Run/swim sprint stopping predicates

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: after sprint starts, B selects swim versus run stop predicate; conditions include sprint possibility, water state, forward input, ground/shift and major horizontal collision; lines 820-828,921-929.
- A evidence: pending exact predicates and caller correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(),shouldStopRunSprinting(),shouldStopSwimSprinting(), lines 820-828,921-929`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.
- State producers/writers -> consumers/readers: water/forward/ground/shift/collision flags and sprint ability -> sprint flag -> travel.
- Parent slices / dependencies / closure evidence: S-SPRINT-START,S-LOCAL-SNAPSHOT; movement predicates/vanilla-state consumers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B methods read; no comparison yet.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-FLIGHT-TOGGLE: Creative/spectator flight toggle and launch jump

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `mayfly` and spectator checks, jump-edge/timer logic, vehicle/swimming guard, ability flip, possible ground jump impulse, ability sync and timer reset; lines 830-852.
- A evidence: pending exact source and ability/member correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(), lines 830-852`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.
- State producers/writers -> consumers/readers: jump edge, ability flags, spectator/swimming/vehicle/on-ground -> flying state and possible jump impulse -> player travel.
- Parent slices / dependencies / closure evidence: S-LOCAL-SNAPSHOT,S-JUMP-IMPULSE,S-PLAYER-01; Abilities fields/defaults, packet/sync path pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B branch read; pair comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-FALLFLY-REQUEST: Local jump edge requests fall-flying

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: B checks jump edge, not a flight toggle, not climbable, and `tryToStartFallFlying`, then sends start-fall-flying command; it records current fall-flying state immediately afterward; lines 854-858.
- A evidence: pending exact request caller, server response and state update path.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(), lines 854-858`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.
- State producers/writers -> consumers/readers: previous/current jump state and glide eligibility -> packet -> externally supplied fall-flight state -> travel branch.
- Parent slices / dependencies / closure evidence: S-LOCAL-SNAPSHOT,S-TRAVEL-GLIDE-DISPATCH,S-EXT-01; eligibility, packet handler and server-supplied boundary pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B call read; no A/server conclusion.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-SINK-INPUT: Local shift input adds downward water velocity

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `isInWater && shift && isAffectedByFluids` invokes `goDownInWater`; lines 859-861. Compare call order to input tick and superclass aiStep.
- A evidence: pending exact local input and helper/call-order correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(), lines 859-861`; helper `LivingEntity.goDownInWater(), lines 2403-2405`; source hashes in manifest.
- State producers/writers -> consumers/readers: shift/current fluid state -> fixed Y impulse -> fluid travel.
- Parent slices / dependencies / closure evidence: S-LOCAL-SNAPSHOT,S-JUMP-LIQUID,S-TRAVEL-WATER; exact order and both helper hashes pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; no A comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-FLIGHT-VERTICAL-INPUT: Creative flight key input adds vertical velocity

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: when flying and camera-controlled, A/B map shift/jump to integer input and add `inputYa * abilities.getFlyingSpeed() * 3.0F` to Y velocity; `LocalPlayer.aiStep(), lines 871-884`.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(), lines 871-884`, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; direct branch, input signs, multiplication order and `setDeltaMovement` expression match B.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(), lines 871-884`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.
- State producers/writers -> consumers/readers: flight abilities and shift/jump key states -> Y-velocity write -> player travel.
- Parent slices / dependencies / closure evidence: S-FLIGHT-TOGGLE,S-PLAYER-01; abilities getters/defaults and their state producer remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): the paired direct player-velocity write matches, including branch order and float multiplication; flight ability production/getter semantics remain open, so reachability and effective impulse are not dispositioned.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-PLAYER-IMPULSE-RESPONSE: Player knockback and entity-push velocity application

- Inventory ID(s): INV-STATE, INV-MODIFIERS, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: inherited `LivingEntity.knockback` scales power by knockback resistance, handles near-zero horizontal direction, normalizes/scales the impulse and writes player delta movement; `LivingEntity.push(Entity)` rejects sleeping-player pushes before delegating to `Entity.push(Vec3/double)`, which finite-checks and adds the impulse. Damage resolution and non-player physics are outside this bounded slice.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::knockback(double,double,double),push(Entity), lines 1612-1630,2303-2309`, SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`; `Entity.java::push(Vec3),push(double,double,double), lines 1820-1831`, SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::knockback(double,double,double,DamageSource,float,boolean),knockback(double,double,double,DamageSource,float),push(Entity), lines 1641-1663,2341-2346`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; `Entity.java::push(Vec3),push(double,double,double), lines 1907-1918`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: `KNOCKBACK_RESISTANCE` and player grounded/current delta movement -> direct knockback response; collision push vector -> finite-checked addition to player delta movement. The movement formula in `knockback` and the `Entity.push` accumulation bodies match A; B adds contextual overload parameters that are unused in this method body.
- Parent slices / dependencies / closure evidence: S-MOD-01,S-EXT-01,S-VELOCITY-HORIZONTAL; knockback-resistance writers/conditions, applicable push sources, and player-facing packet/correction paths remain open. All `knockback` overrides and relevant caller routes must be paired before this slice closes.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): direct player response algorithms were compared and match in the inspected bodies, but modifier sources, all relevant override/caller paths, and external player impulse routes are not yet closed. This does not make a claim about attack or damage resolution.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-RIDEABLE-JUMP: Local charge/release jump for jumpable vehicles

- Inventory ID(s): INV-TICK, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: consumes a `PlayerRideableJumping` controlled vehicle's cooldown and held/released jump edges; charges rider jump scale, invokes the vehicle callback and sends `START_RIDING_JUMP`; lines 886-913, with the callback type predicate and packet sender checked.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(),jumpableVehicle(),sendRidingJump(), lines 886-913,600-604,392-398`, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(),jumpableVehicle(),sendRidingJump(), lines 886-913,600-604,392-398`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.
- State producers/writers -> consumers/readers: riding jump key edges -> local charge timer/scale -> controlled vehicle `onPlayerJump` callback and outbound riding-jump packet; this path does not write player delta movement or invoke player `move`.
- Parent slices / dependencies / closure evidence: `INV-EXCLUSIONS`; `jumpableVehicle()` requires the controlled vehicle implement `PlayerRideableJumping` and `canJump()`, and the packet action is `START_RIDING_JUMP`. Vehicle physics is explicitly out of scope.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): both endpoint source paths match, and the reachable operation supplies jump control to a vehicle while only advancing local charge bookkeeping; the actual motion target is the vehicle. Parent campaign scope excludes vehicle physics, so this slice has no in-scope player-motion result. The vehicle callback implementation is not traversed.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-LOCAL-INPUT-MODIFIERS: Local input slowdown, sneak speed and base fallback

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: virtual `applyInput` camera guard; `modifyInput` applies initial 0.98F scale, item-use multiplier when not passenger, sneaking-speed attribute while moving slowly, then square-movement helper; non-camera fallback delegates to LivingEntity's 0.98F X/Z scaling; LocalPlayer lines 692-723, LivingEntity A line 3113.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::applyInput(),modifyInput(Vec2), lines 692-723`, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; corresponding inherited `LivingEntity.applyInput(), lines 3113-3116`, SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`. `itemUseSpeedMultiplier(),isMovingSlowly(),isControlledCamera()` also match at LocalPlayer.java:567-569,687-689,744-746. Item `UseEffects` data/defaults and `SNEAKING_SPEED` producer remain open in `INV-MODIFIERS`; crouch/crawl state is routed to `S-LOCAL-SNAPSHOT`/`S-POSE-UPDATE`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::applyInput(),modifyInput(Vec2), lines 692-723`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; `LivingEntity.applyInput(), lines 3180-3183`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; same three gate/helper methods match at corresponding LocalPlayer.java lines.
- State producers/writers -> consumers/readers: keyboard vector and camera/using-item/passenger/sneaking state plus item-use/sneaking-speed values -> modified input vector or fallback X/Z fields -> travel.
- Parent slices / dependencies / closure evidence: S-IN-01,S-TICK-ENTRY,S-SQUARE-MOVE,S-LOCAL-SNAPSHOT,S-POSE-UPDATE; direct helper bodies match, but item `UseEffects` data/defaults and `SNEAKING_SPEED` producer remain pending in `INV-MODIFIERS`.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): A/B direct methods and inherited fallback were read and their code matches, but this slice includes the unresolved movement-modifier producer/condition chain. Direct vanilla-state reads remain movement inputs; excluded health/food systems are not emulated.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-SQUARE-MOVE: Square-movement vector cap and direction reconstruction

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: helper returns zero input unchanged; otherwise normalizes direction, computes distance to unit square using branch-selected ratio, caps modified length at 1.0F and rescales; `distanceToUnitSquare`; compare `LocalPlayer.java:725-742` plus relevant Vec2/Mth methods.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::modifyInputSpeedForSquareMovement(Vec2),distanceToUnitSquare(Vec2), lines 725-742`, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; helper bodies match B. Relevant Vec2 `scale(),length(),lengthSquared()` lines 28-30,53-59 and Mth `sqrt(float),square(float)` lines 57-59,616-618 match B; file hashes are recorded in `S-IN-01` and checked against both source manifests.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::modifyInputSpeedForSquareMovement(Vec2),distanceToUnitSquare(Vec2), lines 725-742`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.
- State producers/writers -> consumers/readers: modified input vector -> direction/length branch and arithmetic -> travel input.
- Parent slices / dependencies / closure evidence: S-IN-01,S-LOCAL-INPUT-MODIFIERS; the matching `modifyInput` call at LocalPlayer.java:722 was read; helper math dependencies Vec2 and Mth are compared above.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): exact A/B code for `modifyInputSpeedForSquareMovement` and `distanceToUnitSquare` matches at lines 725-742, including zero guard, float division, branch ratio, `Mth.sqrt(1.0F + Mth.square(tan))`, `Math.min` cap and direction rescale. Relevant Vec2 and Mth helper bodies were also compared, so this disposition includes the helper operation order, not merely identical caller text.
- Finding IDs or checked absence/replacement path: none yet.
### Slice S-VELOCITY-HORIZONTAL: Player-specific pre-input horizontal dead-zone

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B snapshots delta movement, enters the `EntityTypes.PLAYER` branch, compares `horizontalDistanceSqr()` against `9.0E-6`, and zeroes X/Z together; keep the non-player per-axis branch as scope context, not movement behavior; lines 3060-3077.
- A evidence: pending exact method and player-type guard correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::aiStep(), lines 3060-3077`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: prior tick/network/external X/Z velocity -> horizontal threshold and player guard -> rewritten delta -> input/jump/travel.
- Parent slices / dependencies / closure evidence: S-TICK-ENTRY,S-EXT-01; all player velocity writers and subsequent consumers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B exact branch read; threshold and operation-order comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-VELOCITY-VERTICAL: Pre-input vertical velocity dead-zone

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B independently tests `Math.abs(movement.y) < 0.003` and rewrites Y to zero before `setDeltaMovement`; lines 3079-3083.
- A evidence: pending exact method and writer/caller correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::aiStep(), lines 3079-3083`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: prior vertical velocity -> strict threshold -> rewritten velocity -> jump/travel branch.
- Parent slices / dependencies / closure evidence: S-VELOCITY-HORIZONTAL,S-EXT-01; A/B direct velocity writer/packet inventory pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B comparison threshold read; no A result.
- Finding IDs or checked absence/replacement path: none yet.
### Slice S-JUMP-GATE: Player jump edge, fluid-depth gates and delay

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B jump handling requires `jumping && isAffectedByFluids`; selects fluid height, compares with threshold, checks ground/shallow-fluid state and `noJumpDelay`, dispatches ground or liquid impulse, and clears delay otherwise; lines 3098-3123.
- A evidence: pending exact caller/method and fluid-state correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::aiStep(), lines 3098-3123`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: input jump state, fluid contact/height, on-ground state and delay -> ground/liquid jump helper and cooldown -> travel.
- Parent slices / dependencies / closure evidence: S-IN-01,S-TRAVEL-FLUID-DISPATCH; fluid tags/height helpers and A jump sequence pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B gates read; no pairwise conclusion.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-JUMP-IMPULSE: Ground jump power and sprint impulse

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: B jump power positive threshold, max with current Y, sprint-angle horizontal addition and sync flag in `jumpFromGround`; inspect `getJumpPower`/attribute/effect producer chain; lines 2376-2401.
- A evidence: pending exact methods and jump-power dependency closure.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::getJumpPower(),jumpFromGround(), lines 2376-2401`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: jump strength attribute/effect, existing velocity, sprint flag and yaw -> ground impulse -> travel.
- Parent slices / dependencies / closure evidence: S-JUMP-GATE; jump strength, jump boost and sprint-state provenance pending (do not emulate hunger/food producers).
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; exact A math/effect chain pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-JUMP-LIQUID: Liquid jump and sink impulses

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `goDownInWater` and `jumpInLiquid` add vertical impulses with float literals; lines 2403-2409. Confirm separate water/lava callers from both tick and local input paths.
- A evidence: pending exact helpers and caller mapping.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::goDownInWater(),jumpInLiquid(TagKey), lines 2403-2409`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: local shift/jump input and liquid branch gates -> Y velocity impulse -> fluid travel.
- Parent slices / dependencies / closure evidence: S-JUMP-GATE,S-LOCAL-SNAPSHOT,S-LOCAL-UNSTUCK,S-SPRINT-START,S-SPRINT-STOP; exact call timing/order pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B helpers read; no pairwise result.
- Finding IDs or checked absence/replacement path: none yet.
### Slice S-LIVING-GLIDE-UPDATE: Fall-flying eligibility update before travel

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: B tests `isFallFlying()` after jump handling and calls `updateFallFlying()` before travel; lines 3125-3130. Trace the eligibility flag and equipment/effect conditions separately.
- A evidence: pending exact tick order, glide-state writer and caller correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::aiStep(), lines 3125-3130`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: equipment/effects/ground/passenger state -> glide flag/update -> travel dispatch.
- Parent slices / dependencies / closure evidence: S-FALLFLY-REQUEST,S-TRAVEL-GLIDE-DISPATCH; `updateFallFlying`, `canGlide`, equipment/item data and A timing pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B call order read; exact A path unresolved.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-PREP: Bounding-box snapshot and movement input construction

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B captures `beforeTravelBox`, constructs `Vec3` from xxa/yya/zza, and does so immediately before travel gating; lines 3131-3132.
- A evidence: pending exact fields, call order and method correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::aiStep(), lines 3131-3132`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: `applyInput` xxa/yya/zza and current AABB -> travel vector and pre-movement AABB used by later tick logic.
- Parent slices / dependencies / closure evidence: S-LOCAL-INPUT-MODIFIERS,S-POSE-UPDATE; A input fields/call order pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B method body read; pair comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-FALL-DISTANCE-RESET: Slow-falling/levitation fall-distance reset before travel

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: B resets fall distance when Slow Falling or Levitation is active before travel; lines 3133-3135. Determine whether any in-scope movement consumer reads the field; damage/fall-damage production remains excluded.
- A evidence: pending exact effect check, writer and consumer closure.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::aiStep(), lines 3133-3135`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: active movement effect -> fall-distance write; inspect every in-scope reader and exclude damage-only consequence.
- Parent slices / dependencies / closure evidence: S-TRAVEL-AIR,S-TRAVEL-GLIDE-FORMULA; fall-distance readers and A method pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B guard read; disposition remains open until consumer inventory proves scope.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-GATE: Ridden-player-controller versus self-travel dispatch

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: B selects `travelRidden` when a controlling passenger is a live Player; otherwise calls self travel only when movement simulation and effective AI gates pass; lines 3137-3141.
- A evidence: pending exact gate and mount/player caller correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::aiStep(), lines 3137-3141`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: vehicle/controller/alive/simulation/AI state -> movement branch and input; only local player movement and player-facing mount transitions are in scope, not independent vehicle physics.
- Parent slices / dependencies / closure evidence: S-TRAVEL-PREP,S-EXT-01,S-PLAYER-01; A player-side mount state/callers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B gate read; branch ownership must be resolved before disposition.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-POST-BLOCK-EFFECTS: Block effects after movement

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: after travel, B applies block effects only on server or authoritative local instance; lines 3143-3145. Trace movement-history collection, block callback/provider dispatch and player movement writers.
- A evidence: pending exact guard, call order and block-effect dependency closure.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::aiStep(), lines 3143-3145`; `Entity.applyEffectsFromBlocks()`, `lines 903-915`; source hashes in manifest.
- State producers/writers -> consumers/readers: recorded movement segments and encountered block states -> block effects/callbacks -> any movement-relevant player flags or velocity.
- Parent slices / dependencies / closure evidence: S-TRAVEL-GATE,S-COLLISION-QUERY,S-WORLD-01; callback method and registration/resource inventory pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B call order identified; providers and A behavior unresolved.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-POST-ANIMATION: Client animation update after travel

- Inventory ID(s): INV-TICK, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: B client-only branch calls `calculateEntityAnimation(omnidirectionalAirMover())` after block effects; lines 3147-3149. Check its state writes/readers to establish whether any feed movement or can be explicitly not-applicable.
- A evidence: pending exact call/method and consumer audit.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::aiStep(),calculateEntityAnimation(boolean), lines 3147-3149,2662-2675`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: post-travel position delta -> animation state; inspect whether any movement predicate reads those animation fields before assigning a scope disposition.
- Parent slices / dependencies / closure evidence: S-TRAVEL-GATE,S-STATE inventory; all readers and A method pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B branch read; not assumed irrelevant without consumer evidence.
- Finding IDs or checked absence/replacement path: none yet.
### Slice S-TRAVEL-DISPATCH: Fluid, fall-flying or air travel branch selection

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `LivingEntity.travel` selects `shouldTravelInFluid`, fall-flying, or ordinary air path; `shouldTravelInFluid` requires in-water/lava, affected-by-fluids, and not standing on the current fluid; lines 2430-2446.
- A evidence: pending exact method, enclosing tick call and correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::travel(Vec3),shouldTravelInFluid(FluidState), lines 2430-2446`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: fluid contacts/standing state and fall-flying state -> branch selection -> branch-specific velocity/position consumers.
- Parent slices / dependencies / closure evidence: S-LIVING-GLIDE-UPDATE,S-TRAVEL-PREP,S-FALL-DISTANCE-RESET,S-TRAVEL-GATE,S-POST-BLOCK-EFFECTS,S-POST-ANIMATION,S-PLAYER-01; fluid contact and gliding state writers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B methods read; no pairwise branch disposition.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-FLYING: Generic fluid/air flying travel

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `travelFlying` has separate water, lava and air input speeds, movement calls and per-branch damping; lines 2448-2466.
- A evidence: pending exact source and caller applicability.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::travelFlying(Vec3,float),travelFlying(Vec3,float,float,float), lines 2448-2466`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: flight/input speed and current velocity -> moveRelative/move -> water/lava/air damping -> next movement tick.
- Parent slices / dependencies / closure evidence: S-TRAVEL-DISPATCH; all player callers and abilities pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B branch read; player reachability and A behavior unresolved.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-AIR: Ground/air acceleration, gravity and damping integration

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: B `travelInAir` chooses supporting-block friction, gets movement from the relative-friction helper, applies levitation/gravity/chunk guards, then air-drag and vertical-friction terms; `getAirDrag`; lines 2468-2502.
- A evidence: pending exact method body and support/attribute correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::travelInAir(Vec3),getAirDrag(), lines 2468-2502`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: on-ground/support-block friction and input -> movement vector; levitation/gravity/chunk state -> vertical movement; drag attributes -> delta movement.
- Parent slices / dependencies / closure evidence: S-MOVE-FLAGS,S-TRAVEL-RELATIVE,S-TRAVEL-FRICTION-SPEED; block friction, attributes, effects and helper chains pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B bodies read; exact math and pair comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-RELATIVE: Relative input acceleration and in-air climb response

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B relative-friction helper calls `moveRelative`, applies climbable motion handling, moves, then has a separate horizontal-collision/jump climb or powder-snow vertical override; lines 2676-2686.
- A evidence: pending exact helper, caller and block-state dependencies.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::handleRelativeFrictionAndCalculateMovement(Vec3,float), lines 2676-2686`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: input/yaw/speed and collision/climbing/powder-snow state -> relative acceleration/movement -> velocity/position.
- Parent slices / dependencies / closure evidence: S-TRAVEL-AIR,S-TRAVEL-CLIMB; `moveRelative`, climbable and powder-snow support providers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B helper read; no A correspondence or disposition.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-FRICTION-SPEED: Ground friction conversion to input speed

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: B `getFrictionInfluencedSpeed` uses a ground-only cubic block-friction expression above its threshold and the flying-speed path otherwise; lines 2720-2726.
- A evidence: pending exact method and block-friction/speed producer chain.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::getFrictionInfluencedSpeed(float), lines 2720-2726`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: ground flag/block friction/speed attribute -> moveRelative acceleration.
- Parent slices / dependencies / closure evidence: S-TRAVEL-AIR,S-WORLD-01,S-MOD-01; property and attribute chains pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B method read; comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-FLUID-DISPATCH: Water versus lava travel setup

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B computes falling state, old Y and effective gravity once, then selects water travel plus float-while-ridden or lava travel; lines 2504-2514.
- A evidence: pending exact source and branch/caller correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::travelInFluid(Vec3), lines 2504-2514`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: fluid state/current Y/vertical velocity/effective gravity -> water/lava parameters -> branch movement.
- Parent slices / dependencies / closure evidence: S-TRAVEL-DISPATCH; fluid-height and gravity dependency chains pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B method read; no pairwise conclusion.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-WATER: Water acceleration, slowdown and falling adjustment

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: B sprinting slowdown/water slowdown, movement-efficiency scaling and airborne half factor, Dolphin's Grace override, movement/collision, climb response, slowdown and fluid vertical adjustment; lines 2516-2547.
- A evidence: pending exact body and attribute/effect/data chain.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::travelInWater(Vec3,double,boolean,double), lines 2516-2543`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: sprint/ground/fluid state, WATER_MOVEMENT_EFFICIENCY and Dolphin's Grace -> speed/slowdown -> delta movement.
- Parent slices / dependencies / closure evidence: S-TRAVEL-FLUID-DISPATCH,S-TRAVEL-FLUID-ADJUST; effect/attribute/enchantment/resource providers and fluid data pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; no pairwise formula disposition.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-LAVA: Lava acceleration and depth-dependent damping

- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: B lava movement-relative step, move, shallow-fluid test, branch-specific damping, gravity quarter-step and fluid-exit helper call; lines 2549-2565.
- A evidence: pending exact source and fluid-threshold correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::travelInLava(Vec3,double,boolean,double), lines 2549-2565`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: fluid height/threshold, gravity and input -> lava movement/damping -> delta movement.
- Parent slices / dependencies / closure evidence: S-TRAVEL-FLUID-DISPATCH,S-TRAVEL-FLUID-EXIT; tags and height consumers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B method read; pairwise comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-FLUID-EXIT: Horizontal-collision exit impulse from water/lava

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `jumpOutOfFluid` checks horizontal collision and a clearance query with `movement.y + 0.6F - this.getY() + oldY`, then sets vertical velocity to `0.3F`; lines 2567-2572.
- A evidence: pending exact method, caller and clearance-query correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::jumpOutOfFluid(double), lines 2567-2572`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: fluid travel old Y/current velocity and horizontal collision -> free-space query -> vertical velocity.
- Parent slices / dependencies / closure evidence: S-TRAVEL-WATER,S-TRAVEL-LAVA; AABB/free-space query dependency pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B helper read; no pair conclusion.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-FLUID-ADJUST: Vertical fluid falling and tiny-velocity adjustment

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B nonzero gravity and non-sprinting guard; falling plus epsilon comparisons choose `-0.003`, otherwise subtract gravity/16; lines 2688-2701.
- A evidence: pending exact helper and caller correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::getFluidFallingAdjustedMovement(double,boolean,Vec3), lines 2688-2701`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: gravity/sprint/falling/current Y velocity -> adjusted fluid velocity -> water/lava travel writers.
- Parent slices / dependencies / closure evidence: S-TRAVEL-WATER,S-TRAVEL-LAVA; effective-gravity producer pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B exact comparisons read; A arithmetic/order pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-GLIDE-DISPATCH: Fall-flying branch and collision response

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B climbable condition falls back to air travel and stops fall-flying; otherwise updates glide velocity, moves, then runs server-side fall-flying collision handler; lines 2581-2600.
- A evidence: pending exact branch and player glide-state correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::travelFallFlying(Vec3),stopFallFlying(),handleFallFlyingCollisions(double,double), lines 2581-2600,2629-2636`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: fall-flying/climbable/collision state -> glide movement or air fallback and flag transition; server damage response is excluded, but its movement-state dependencies remain bounded.
- Parent slices / dependencies / closure evidence: S-TRAVEL-DISPATCH,S-TRAVEL-GLIDE-FORMULA,S-TRAVEL-CLIMB; Elytra/player transition inputs pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B method read; no A/elytra dependency closure.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-GLIDE-FORMULA: Fall-flying velocity update arithmetic

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: B `updateFallFlyingMovement` computes look/lean lengths, gravity lift, downward/forward conversion, horizontal alignment and final per-axis drag; lines 2602-2635.
- A evidence: pending exact helper and look/attribute correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::updateFallFlyingMovement(Vec3), lines 2602-2627`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: look vector, pitch, velocity, gravity and elytra state -> exact arithmetic -> glide delta movement.
- Parent slices / dependencies / closure evidence: S-TRAVEL-GLIDE-DISPATCH; gravity, equipment and item-component applicability pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B formulas read; no pairwise operation-order conclusion.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-CLIMB: Climbable velocity clamps and player ladder predicate

- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: B `handleOnClimbable` resets fall distance, clamps X/Z and downward Y, then applies the player-only ladder-slide suppression condition with scaffolding check; lines 2703-2718.
- A evidence: pending exact helper, player predicate and climbable block-state correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::handleOnClimbable(Vec3), lines 2703-2718; `onClimbable()`, lines 1722-1739`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: block/entity climbing state, player identity and scaffolding state -> velocity clamps/predicate -> air/water travel.
- Parent slices / dependencies / closure evidence: S-TRAVEL-RELATIVE,S-TRAVEL-WATER,S-TRAVEL-GLIDE-DISPATCH; all climbable providers and exact predicate line/hash pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B helper read; player-only precondition and A comparison unverified.
- Finding IDs or checked absence/replacement path: none yet.
### Slice S-PLAYER-01: Player swimming and ability-flight travel wrapper

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: `Player.travel(Vec3)` passenger, swim-look, flight and superclass path, lines 1402-1429; downstream conditions and abilities are dependencies.
- A evidence: pending exact source and inheritance correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/player/Player.java::net.minecraft.world.entity.player.Player#travel(Vec3), lines 1402-1429`, SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`.
- State producers/writers -> consumers/readers: passenger/swimming/abilities/look/fluid state -> vertical delta writer and living travel; abilities and fluid checks pending.
- Parent slices / dependencies / closure evidence: S-LIVING-GLIDE-UPDATE,S-TRAVEL-PREP,S-FALL-DISTANCE-RESET,S-TRAVEL-GATE,S-POST-BLOCK-EFFECTS,S-POST-ANIMATION; abilities/defaults and shape/fluid queries pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; no A pair yet.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-POSE-UPDATE: Player pose selection, fit predicate and post-super tick timing

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: `Player.tick()` invokes pose selection after `super.tick()` returns (283-285); `updatePlayerPose`, `getDesiredPose` and `canPlayerFitWithinBlocksAndEntitiesWhen` determine requested/actual pose and collision fit (343-375). Dynamic/local callers and exact A order remain to trace.
- A evidence: pending exact caller/member source and override correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/player/Player.java::tick(),updatePlayerPose(),getDesiredPose(),canPlayerFitWithinBlocksAndEntitiesWhen(Pose), lines 231-285,343-375`, SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`.
- State producers/writers -> consumers/readers: input/swimming/sleeping/fall-flying/shift/flight and fit query -> pose state -> dimension/eye-height/bounding-box consumers.
- Parent slices / dependencies / closure evidence: S-TICK-ENTRY,S-LOCAL-SNAPSHOT,S-LOCAL-UNSTUCK,S-SPRINT-START,S-SPRINT-STOP,S-FLIGHT-TOGGLE,S-FALLFLY-REQUEST,S-SINK-INPUT,S-FLIGHT-VERTICAL-INPUT,S-RIDEABLE-JUMP,S-DIMENSIONS,S-COLLISION-QUERY; all pose writers and fit-query dependencies pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B methods read; post-super call order is only a comparison target until A is ready.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-DIMENSIONS: Pose-dependent dimensions, eye height and bounding-box refresh

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: B `Entity.setPose`, `getDimensions`, `refreshDimensions`, `reapplyPosition`, bounding-box creation and eye-height getters; include constructor/default and state writers when pair inventory is built.
- A evidence: pending exact source, defaults and method correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::setPose(Pose),makeBoundingBox(Vec3),refreshDimensions(),getEyeHeight(Pose),getEyeHeight(),getDimensions(Pose), lines 439-445,476-482,3393-3409,3462-3468,3703-3705`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: pose/entity type/dimension defaults -> width/height/eye height, position reapplication, AABB -> movement collision, fluid and support queries.
- Parent slices / dependencies / closure evidence: S-POSE-UPDATE; LivingEntity dimension override, Player type/default dimensions, Pose definitions and all resize callers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B methods read; no pairwise disposition or equivalence claim.
- Finding IDs or checked absence/replacement path: none yet.
### Slice S-MOVE-POS: Entity movement clipping, position integration and zero-motion threshold

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: B `Entity.move` call from edge adjustment through collision result acceptance, movement record, and `setPos`; lines 737-757. Compare its enclosing no-physics/piston/stuck guards separately.
- A evidence: pending exact source and caller correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::move(MoverType,Vec3), lines 737-757`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: edge-adjusted requested movement -> collided movement and squared-length threshold -> movement history and position -> later collision/support/tick readers.
- Parent slices / dependencies / closure evidence: S-TICK-ENTRY,S-TRAVEL-DISPATCH,S-TRAVEL-FLYING,S-TRAVEL-AIR,S-TRAVEL-RELATIVE,S-TRAVEL-FRICTION-SPEED,S-TRAVEL-FLUID-DISPATCH,S-TRAVEL-WATER,S-TRAVEL-LAVA,S-TRAVEL-FLUID-EXIT,S-TRAVEL-FLUID-ADJUST,S-TRAVEL-GLIDE-DISPATCH,S-TRAVEL-GLIDE-FORMULA,S-TRAVEL-CLIMB; `collide`, AABB and collision result producers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read only; A comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-MOVE-FLAGS: Collision-axis, vertical support and on-ground state updates

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: B `Entity.move` computes X/Z and vertical collision flags, vertical-below, then calls `setOnGroundWithMovement`; lines 759-778. Keep damage processing excluded while tracing any movement-state writers and callers.
- A evidence: pending exact source and caller/member correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::move(MoverType,Vec3),setOnGroundWithMovement(boolean,boolean,Vec3), lines 759-778, 671-706`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: requested vs clipped movement -> collision/ground flags -> next jump, friction, support and travel gates.
- Parent slices / dependencies / closure evidence: S-MOVE-POS,S-COLLISION-STEP; support-position lookup and flag consumers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; player authoritative guard and A semantics pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-MOVE-RESTITUTE: Post-collision player velocity response

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `Entity.move` restitution call gate plus `restituteMovementAfterCollisions`, including horizontal axes, vertical restitution, gravity/drag compensation and bounce event; lines 781-786 and 802-843.
- A evidence: pending exact source, replacement/absence path and call-site correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::move(MoverType,Vec3),restituteMovementAfterCollisions(BlockState,boolean,boolean,Vec3), lines 781-786,802-843`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: current velocity, collision flags, effect block, gravity/air-drag attributes and bounce-suppression tag -> restitution -> rewritten velocity and sync/event state -> next tick/travel.
- Parent slices / dependencies / closure evidence: S-MOVE-FLAGS; block restitution registrations, entity attributes and referenced data/tags pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): earlier report suggests a candidate, but current A source is unavailable and no finding is imported.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-MOVE-BLOCK-SPEED: Post-move block speed factor applied to horizontal velocity

- Inventory ID(s): INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: B `Entity.move` reads `getBlockSpeedFactor()` after collision and movement emissions and multiplies X/Z delta; lines 788-797, with getter and support-block lookup as dependencies.
- A evidence: pending exact source and caller/member correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::move(MoverType,Vec3),getBlockSpeedFactor(), lines 788-797,1085-1095`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: support block/state and block speed property -> post-move horizontal velocity -> next tick; neighbor/support computation and registrations pending.
- Parent slices / dependencies / closure evidence: S-MOVE-POS,S-MOVE-FLAGS; S-WORLD-01 registration inventory pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body anchors read; no A comparison.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-EDGE-PROBE: Movement edge backoff hook

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `Entity.move` invokes `maybeBackOffFromEdge(delta,moverType)` before collision; base implementation currently returns the input unchanged. Enumerate player-reachable overrides before deciding applicability.
- A evidence: pending exact method, subclasses and caller inventory.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::move(MoverType,Vec3),maybeBackOffFromEdge(Vec3,MoverType), lines 737,1097-1099`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: input movement and mover type -> virtual edge adjustment -> collision query; player-specific override/callability must be verified.
- Parent slices / dependencies / closure evidence: S-TICK-ENTRY,S-MOVE-POS; inheritance/override search on both trees pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B base method read; no no-difference claim from base alone.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-COLLISION-STEP: Candidate movement and step-up selection

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `Entity.collide` clipping, collision-axis tests, step-up guard, grounded/expanded boxes, candidate iteration and horizontal-distance winner; `collectCandidateStepUpHeights` candidate filtering and sort; lines 1141-1192.
- A evidence: pending exact methods and caller/member correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::collide(Vec3),collectCandidateStepUpHeights(AABB,List<VoxelShape>,float,float), lines 1141-1192`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: input movement/on-ground/max-step -> candidate heights from collision shapes -> ordered step candidate and clipped movement -> position/ground flags.
- Parent slices / dependencies / closure evidence: S-MOVE-POS,S-MOVE-FLAGS; AABB, shape coordinate ordering and candidate dependencies pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; no pair result.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-COLLISION-QUERY: Collider gathering and axis clipping

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: B collision-bounding-box entry points and source-specific collider gathering, including entity collisions, close world-border shape and block-context collision queries; compare axis order/clipping separately in `collideWithShapes`; lines 1195-1243.
- A evidence: pending exact methods and world-call correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::collideBoundingBox(...),collectAllColliders(...),collectCollidersIgnoringWorldBorder(...), lines 1195-1243`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: entity/player collision context and expanded AABB -> world/entity/border/block shape lists -> clipping.
- Parent slices / dependencies / closure evidence: S-COLLISION-STEP; Level collision-query, border, entity and block provider dependencies pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; A and provider closure pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-COLLISION-AXIS: Sequential clipping axis order

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: B `Entity.collideWithShapes` iterates `Direction.axisStepOrder(movement)`, carries resolved movement into the next-axis bounding box, and calls `Shapes.collide`; lines 1244-1262.
- A evidence: pending exact method and vector/shape-helper correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::collideWithShapes(Vec3,AABB,List<VoxelShape>), lines 1244-1262`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: movement vector/axis order/shape list -> per-axis resolved vector -> final clipped movement.
- Parent slices / dependencies / closure evidence: S-COLLISION-QUERY; `Direction.axisStepOrder`, `Shapes.collide`, `VoxelShape` dependencies pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B method body read; pair comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-WORLD-01: Block/fluid movement providers, registrations and resource data
 Block/fluid movement properties, callbacks, registries and resource data

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: enumerate every player-reachable shape provider/override, movement callback, friction/speed/jump/bounce property, fluid push/height/flow input, block registration, state/context and neighbor dependency; this is an inventory root to be expanded into bounded individual slices, not a disposition.
- A evidence: pending exact source, registry and resource inventory.
- B evidence: B seed files include Block, BlockBehaviour, Blocks, all registered block subclasses, Fluid/FlowingFluid, shape classes and client-jar data; individual members/entry hashes not yet inventoried.
- State producers/writers -> consumers/readers: block/fluid registration/state/resource values -> player shape/contact/property/fluid queries -> movement, support, velocity and callback consumers.
- Parent slices / dependencies / closure evidence: S-POSE-UPDATE,S-DIMENSIONS,S-MOVE-POS,S-MOVE-FLAGS,S-MOVE-RESTITUTE,S-MOVE-BLOCK-SPEED,S-EDGE-PROBE,S-COLLISION-STEP,S-COLLISION-QUERY,S-COLLISION-AXIS,S-TRAVEL-DISPATCH,S-TRAVEL-FLYING,S-TRAVEL-AIR,S-TRAVEL-RELATIVE,S-TRAVEL-FRICTION-SPEED,S-TRAVEL-FLUID-DISPATCH,S-TRAVEL-WATER,S-TRAVEL-LAVA,S-TRAVEL-FLUID-EXIT,S-TRAVEL-FLUID-ADJUST,S-TRAVEL-GLIDE-DISPATCH,S-TRAVEL-GLIDE-FORMULA,S-TRAVEL-CLIMB; registry/resource inventory and all reachable providers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B root inventory unbuilt; no pairwise no-difference or absence conclusion.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-MOD-01: Movement attributes, effects, enchantments and equipment chains

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: consumer -> aggregation/default -> attribute/effect/enchantment registration and application/removal/conditions -> slots/data/tags for movement-affecting values; exclude health/food producers and combat simulations.
- A evidence: pending exact source and resource inventory.
- B evidence: B seed consumers include LivingEntity, Player, LocalPlayer, Attributes and effect/enchantment helpers; exact producer/consumer ranges and data entry hashes pending.
- State producers/writers -> consumers/readers: effect/equipment/server attribute inputs -> movement modifier aggregation -> travel/sprint/jump/flight/contact consumers.
- Parent slices / dependencies / closure evidence: S-LOCAL-SNAPSHOT,S-LOCAL-UNSTUCK,S-SPRINT-START,S-SPRINT-STOP,S-JUMP-GATE,S-JUMP-IMPULSE,S-JUMP-LIQUID,S-TRAVEL-DISPATCH,S-TRAVEL-FLYING,S-TRAVEL-AIR,S-TRAVEL-RELATIVE,S-TRAVEL-FRICTION-SPEED,S-TRAVEL-FLUID-DISPATCH,S-TRAVEL-WATER,S-TRAVEL-LAVA,S-TRAVEL-FLUID-EXIT,S-TRAVEL-FLUID-ADJUST,S-TRAVEL-GLIDE-DISPATCH,S-TRAVEL-GLIDE-FORMULA,S-TRAVEL-CLIMB,S-PLAYER-01; resource/tag and server-synchronization provenance pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B dependency inventory unbuilt; no difference claim.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-EXT-01: External player movement inputs and client consumers

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: client player velocity/position corrections, movement synchronization, pushes, piston displacement, mount/dismount transitions and launch inputs; exclude independent non-player movement and damage/combat simulation.
- A evidence: pending exact packet/caller source and consumer correspondence.
- B evidence: B Entity contains packet velocity/position setters and movement synchronization writers; exact protocol callers and player-specific gates not yet indexed.
- State producers/writers -> consumers/readers: server/external packet or world callback -> player position/velocity/pose/vehicle state -> local tick/travel.
- Parent slices / dependencies / closure evidence: S-POSE-UPDATE,S-DIMENSIONS,S-MOVE-POS,S-MOVE-FLAGS,S-MOVE-RESTITUTE; client packet handler, correction and vehicle call chains pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B external-input inventory unbuilt; no pairwise disposition.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TICK-ENTRY: Local player tick, superclass chain and virtual aiStep dispatch

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: A/B `LocalPlayer.tick()` -> `AbstractClientPlayer.tick()` -> `Player.tick()` -> `LivingEntity.tick()` -> `Entity.tick()/baseTick()`; then virtual `LivingEntity.aiStep()` dispatches through `LocalPlayer`, `AbstractClientPlayer`, `Player`, and `LivingEntity` super calls. `Player.tick()` performs pose update after its superclass returns. Movement-specific Player.aiStep work is split into the next two slices; client avatar visual state is excluded in `S-CLIENT-AVATAR-STATE`.
- A evidence: `LocalPlayer.tick(), lines 227-251` (file SHA `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`); `AbstractClientPlayer.tick(), lines 44-47` (file SHA `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc`); `Player.tick(), lines 231-284` (file SHA `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`); `LivingEntity.tick(), lines 2699-2814` (file SHA `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`); `Entity.tick(),baseTick(), lines 499-501,503-557` (file SHA `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`). Method bodies were extracted and compared with B; all five tick bodies are identical.
- B evidence: `LocalPlayer.tick(), lines 227-251` (file SHA `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`); `AbstractClientPlayer.tick(), lines 44-47` (file SHA `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc`); `Player.tick(), lines 232-285` (file SHA `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`); `LivingEntity.tick(), lines 2765-2880` (file SHA `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`); `Entity.tick(),baseTick(), lines 506-508,510-564` (file SHA `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`). Method bodies were extracted and compared with A; all five tick bodies are identical.
- State producers/writers -> consumers/readers: `Entity.baseTick()` state update -> LivingEntity tick -> virtual player aiStep chain -> movement slices and travel; then `Player.tick()` applies pose after `super.tick()` returns. This slice records dispatch/order only; health and regeneration producers are excluded.
- Parent slices / dependencies / closure evidence: S-IN-01,S-LOCAL-SNAPSHOT,S-LOCAL-UNSTUCK,S-SPRINT-START,S-SPRINT-STOP,S-FLIGHT-TOGGLE,S-FALLFLY-REQUEST,S-SINK-INPUT,S-FLIGHT-VERTICAL-INPUT,S-RIDEABLE-JUMP,S-LIVING-GLIDE-UPDATE,S-TRAVEL-PREP,S-FALL-DISTANCE-RESET,S-TRAVEL-GATE,S-POST-BLOCK-EFFECTS,S-POST-ANIMATION,S-POSE-UPDATE,S-PLAYER-AISTEP-PRE,S-PLAYER-AISTEP-POST,S-CLIENT-AVATAR-STATE; behavior within those slices remains independently accounted.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the complete five-method tick path is identical in A and B, and both class inheritance chains plus the `aiStep` virtual/super call sites were read. This closes tick topology/order only; it does not close any called movement body.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-PLAYER-AISTEP-PRE: Jump-trigger countdown and flight fall-distance reset

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `Player.aiStep` decrements a positive `jumpTriggerTime`, calls regeneration and inventory tick, then resets fall distance when flight is enabled and the player is not a passenger, all before `super.aiStep()`; lines 443-453.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java::aiStep(), lines 443-453`, SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`; inherited `Entity.resetFallDistance(), lines 2841-2843`, SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`. LocalPlayer sets/resets `jumpTriggerTime` in S-FLIGHT-TOGGLE.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/player/Player.java::aiStep(), lines 443-453`, SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; inherited `Entity.resetFallDistance(), lines 2908-2910`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`. The movement-relevant calls and order match A.
- State producers/writers -> consumers/readers: LocalPlayer flight-toggle jump timer -> Player countdown; abilities/passenger state -> fall-distance zeroing -> next LivingEntity travel/fall update.
- Parent slices / dependencies / closure evidence: S-TICK-ENTRY,S-FLIGHT-TOGGLE,S-TRAVEL-AIR,S-FALL-DISTANCE-RESET; `inventory.tick()` may update movement-relevant item/equipment state and remains in D-DEPENDENCIES/INV-MODIFIERS.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): movement-specific direct code and reset helper were compared; the pre-super inventory tick's movement-modifier consequences remain unclosed. `tickRegeneration()` is an excluded health producer and is not emulated.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-PLAYER-AISTEP-POST: Post-travel cached movement speed update

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: after `super.aiStep()` returns from LivingEntity travel, Player updates swing/head rotation and calls `setSpeed((float)getAttributeValue(Attributes.MOVEMENT_SPEED))`; lines 453-456. The speed write occurs before later pickup work.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java::aiStep(), lines 453-456`, SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`; `LivingEntity.setSpeed(float), lines 2679-2681`, SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/player/Player.java::aiStep(), lines 453-456`, SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; `LivingEntity.setSpeed(float), lines 2736-2738`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`. The setter and call expression match A.
- State producers/writers -> consumers/readers: movement-speed attribute value -> cached living-entity speed after current travel -> subsequent relative movement; its attribute source/modifiers remain in `INV-MODIFIERS`.
- Parent slices / dependencies / closure evidence: S-TICK-ENTRY,S-TRAVEL-RELATIVE,S-MOD-01; movement-speed attribute defaults/modifiers and all writers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): call order and setter bodies match, but the attribute producer/condition chain must be closed before disposition.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-CLIENT-AVATAR-STATE: Client animation state from position, velocity and bob

- Inventory ID(s): INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: `AbstractClientPlayer.tick()` records position/velocity and updates cloak interpolation; `AbstractClientPlayer.aiStep()` updates bob before `super.aiStep()`; `ClientAvatarState` stores prior movement, cloak and bob values. Consumers were searched in A/B camera and avatar renderer paths.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/AbstractClientPlayer.java::tick(),aiStep(),updateBob(), lines 44-47,76-90`, SHA-256 `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc`; `ClientAvatarState.tick(Vec3,Vec3),updateBob(float), lines 19-22,75-78`, SHA-256 `12508a7c8e595ab17fc6cf12979657c2c4b8c345d55a1f0e7556c3e4b566caaa`.
- B evidence: `AbstractClientPlayer.java::tick(),aiStep(),updateBob(), lines 44-47,76-90`, SHA-256 `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc`; `ClientAvatarState.java::tick(Vec3,Vec3),updateBob(float), lines 19-22,75-78`, SHA-256 `12508a7c8e595ab17fc6cf12979657c2c4b8c345d55a1f0e7556c3e4b566caaa`; consumer references `Camera.java:150-152` and `AvatarRenderer.java:218` read avatar state into camera/render state. These consumers write presentation state and do not feed player position, velocity or dimensions.
- State producers/writers -> consumers/readers: player position/velocity -> client avatar bob/cloak/interpolation state -> camera/rendering; no movement-state write or physics consumer found in the call path.
- Parent slices / dependencies / closure evidence: `INV-EXCLUSIONS`; rendering-only and camera presentation state are outside player movement scope.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): the state is consumed by client camera/avatar presentation and only stores interpolation/bob/cloak values. It is not an input to LocalPlayer/Player movement or collision, so this is a rendering-only path excluded by campaign scope.
- Finding IDs or checked absence/replacement path: none yet.

## Dependency queue and blockers

- D-A-READY / D-A-SOURCE: resolved. Exact current `26.1.2/unobfuscated` marker, source tree, manifests, client artifact identity, diagnostics and provenance were verified on 2026-10-07; cited source file hashes match the source manifest. Owner: shared source-preparation owner.
- D-B-PROVENANCE: exact command, source/artifact hashes, toolchain and success excerpt are now recorded in `ready/26.2/unobfuscated.provenance.json`; the original client jar was rehashed against its marker. The source owner's personal name and full Gradle stdout are absent (only the success excerpt persists). These recordkeeping gaps remain open but do not block pair source comparison.
- D-DEPENDENCIES: all pair-specific state writers, source overrides, registry/resource entries, tags, data, shape providers, attributes/effects/equipment and external packet dependencies found by the seven inventories. Add each as a child dependency with a parent slice and close against both endpoints.
- Open dependencies: D-B-PROVENANCE (missing owner name/full stdout only), D-DEPENDENCIES.

## Finding index

No pairwise findings confirmed. Earlier reports for this interval were consulted only as navigation aids; their finding is not imported into this run and must be rechecked from current exact sources. Zero confirmed findings at this checkpoint does not mean equivalence.

## Resume checkpoint

- Last completed slices: S-IN-01, S-IN-BASE, S-LOCAL-SNAPSHOT, S-SQUARE-MOVE, S-TICK-ENTRY; `S-RIDEABLE-JUMP` and `S-CLIENT-AVATAR-STATE` are not applicable. `S-LOCAL-INPUT-MODIFIERS`, `S-PLAYER-AISTEP-PRE`, `S-PLAYER-AISTEP-POST`, `S-FLIGHT-VERTICAL-INPUT`, and `S-PLAYER-IMPULSE-RESPONSE` remain in progress; A/B readiness and selected source hashes are verified across 56 bounded units.
- Next bounded slice and exact files/members/body ranges to open: close in-scope player knockback/push applicability by following player-reachable callers and the `KNOCKBACK_RESISTANCE` writer/registration path, then resolve the LocalPlayer pre-travel slices. Continue with `LocalPlayer.aiStep()` around lines 777-884 and player-specific `LivingEntity.aiStep()/travel()` in each endpoint; rehash newly cited source files before disposition.
- Outstanding dependencies and owners: D-B-PROVENANCE (shared source owner), D-DEPENDENCIES (worker inventory).
- Current assumptions requiring verification: no movement-relevant resource/data is omitted. No release introduction point can be inferred from these two endpoints alone.

## Implementation reconciliation

- Reconciliation status: pending; outside this source-only assignment and prohibited until the source-only report is frozen and an explicit follow-up owner is assigned.
- Repository revision inspected: none.
- Finding -> implementation disposition/evidence: none; source findings are not yet confirmed.
- Existing implementation without a frozen source finding: not inspected.
- Coverage gaps routed back to discovery slices: pending; no implementation files inspected.

## Independent source audit

- Reviewer: not assigned in available task instructions; reviewer must not be a discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: none; discovery currently has B-only prep.
- Concrete missed-slice routes (or `none found`): not audited.
- Misses routed to slice/finding IDs and owners: not applicable before reviewer assignment.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: 44 pending; 5 in-progress; 5 compared-no-difference; 0 findings; 2 not-applicable; 0 blocked. This remains a working inventory; expand it as source/provider reachability is enumerated.
- Required inventory status and evidence: `INV-TICK` pending, with `S-IN-01`, `S-IN-BASE`, `S-LOCAL-SNAPSHOT`, `S-SQUARE-MOVE`, and `S-TICK-ENTRY` compared; `S-RIDEABLE-JUMP` and `S-CLIENT-AVATAR-STATE` routed to `INV-EXCLUSIONS`; `S-LOCAL-INPUT-MODIFIERS`, `S-PLAYER-AISTEP-PRE`, `S-PLAYER-AISTEP-POST`, `S-FLIGHT-VERTICAL-INPUT`, and `S-PLAYER-IMPULSE-RESPONSE` are in progress; other slices await source/provider closure.
- Open dependencies: D-B-PROVENANCE (missing owner name/full stdout only), D-DEPENDENCIES. D-A-READY is resolved for exact `26.1.2/unobfuscated`.
- Unresolved gaps and limits: current dispositions cover keyboard sampling/vector construction, neutral client-input fallback, the selected pre-travel input snapshot range and square-movement math; paired tick topology and direct flight/knockback velocity formulas have bounded source comparisons but retain open dependencies. Input modifier producers and conditions, player-facing external impulse/correction routes, the full call-path/provider inventory, findings, source freeze, independent audit and downstream reconciliation remain open.
- Evidence/hash/correspondence audit: A readiness JSON verified `versionId=26.1.2`, `unobfuscated`, `ready`; marker-cited source, artifact and diagnostics manifest hashes recomputed and match. A success log reports build success/exit code 0; provenance records the exact single-version command and tool versions. Six A source hashes recomputed and match the source manifest. B readiness/manifests and six cited B files were previously verified. Both `KeyboardInput.java` full-file hashes are equal; method bodies, key order, sign/normalization operations and LocalPlayer call location were read on both sides. Other slices remain unpaired.
- Blind freeze: pending.
- Implementation reconciliation: pending.
- Independent audit: pending.
- Runtime validation: not performed; runtime is not authorized.










