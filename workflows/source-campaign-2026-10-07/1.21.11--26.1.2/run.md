# Discovery: 1.21.11 to 26.1.2

- Run status: active
- Scope: client player movement; older A = 1.21.11; newer B = 26.1.2.
- Repository revision and start date: 002137b227676caea77f6832b9f4c8d0b6200bff; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: official-name pairing verified by exact ready markers and artifact provenance: A Mojmap / mojmap; B native unobfuscated / unobfuscated.
- Source preparation owner / command / log / readiness marker: sole shared-source owner. Exact A/B markers, version IDs, namespaces and source/artifact/diagnostic manifest hashes verified.
- Toolchain/decompiler/remapper versions and options: Java 25.0.3+9-LTS; Vineflower 1.12.0; TinyRemapper 0.14.1; Mapping-IO 0.9.1; Gradle 9.7.1; 4G heap.
- Discovery authors: source-only worker for 1.21.11--26.1.2.
- Independent reviewer (must differ from discovery authors): pending coordinator assignment.

## Artifact manifest

Evidence paths are relative to the shared source campaign root (the repository build/movement-campaign-2026-10-07 directory); generated sources and jars remain untracked.

| Side | Exact release / requested and resolved ID | Source root | Client jar SHA-256 | CLI namespace/mode | Mapping artifact and SHA-256 | Remapped jar SHA-256 | Source / artifact / diagnostic manifest hashes |
|---|---|---|---|---|---|---|---|
| A | 1.21.11 / exact, ready status and metadata ID verified | ready/1.21.11/mojmap | client.jar SHA-256 1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd | mojmap; official-name namespace | Mojang client mappings 1.21.11, cache client_mappings.txt SHA-256 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd; publisher SHA-1 031a68bebf55d824f66d6573d8c752f0e1bf232a | client-mojmap.jar SHA-256 7055b6a734a8f9f0d80229c2438ba12d10952a57c3795f32b8611020b34eb89a | sources 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555; artifacts c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c; diagnostics a97183dfdbeb5000aac0c66aaed9bb85aa2f2655594e5e4dece254a7c46a13aa |
| B | 26.1.2 / exact, ready status and metadata ID verified | ready/26.1.2/unobfuscated | original client.jar SHA-256 b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0 | native unobfuscated; official-name namespace | not applicable: published unobfuscated; version metadata and artifact manifest have no client mapping file | not applicable: published unobfuscated; original client.jar is decompiler input | sources 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0; artifacts 89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92; diagnostics b4bd3875263a7155ac32cffd64cfacabc7bd54c82c5fe4082f1ef8a809024c02 |

A preparation command: decompileMinecraft --versions=1.21.8,1.21.10,1.21.11 --mappings=mojmap --decompiler-heap=4G; success log at staging/mojmap-1.21.8-to-1.21.11-9a8c76419cd14660b88f9fc90225e127/gradle.full.log. B preparation command: decompileMinecraft --versions=26.1.2 --mappings=unobfuscated --decompiler-heap=4G; success log at staging/unobfuscated-26.1.2-3db218180ac44a64ad496eeb8e05ac18/gradle.full.log. Both ready/source/artifact/diagnostic hashes were recomputed and match; A client, mapping and mapped-jar entries were verified against staged cache files; B original client jar was verified against its artifact entry and metadata has no client_mappings. Source inventories: A 6,622 files / 29,464,971 bytes; B 6,882 files / 32,992,615 bytes. Tool versions on both: Java 25.0.3+9-LTS; Vineflower 1.12.0; TinyRemapper 0.14.1; Mapping-IO 0.9.1; Gradle 9.7.1; decompiler heap 4G.

The 2026-10-07 decompiler-artifact revision applies to Feather-derived sources for 1.8.9-1.13.2; A is exact Mojmap and B is native unobfuscated, so neither side uses that derived artifact and the published inputs are unchanged by the revision.

A's successful log has access warnings for GUI classes and duplicate lambda processing; a targeted movement-class warning/error search found none. A diagnostics provide nine method anchors for Entity, LivingEntity, Player and LocalPlayer. B diagnostics provide seven anchors for Entity, LivingEntity and Player but omit LocalPlayer; the source exists, and LocalPlayer.tick/aiStep bodies were manually checked. Namespace alignment is official names by A Mojmap and B native-unobfuscated.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending source comparison.
- Evidence inventory and finding IDs included at freeze: partial inventory S1.1-S1.3, S3.3.1, S3.6.1 and S5.9; full-pair freeze remains pending.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; none were opened. No wiki or MCPK browsing.
- Source/mapping hashes covered by freeze: pending full-pair coverage and dependency closure.

## Correspondence and call order

Paired source members have been identified from exact sources. This is a partial call trace; the complete tick/state/provider/resource graph remains open.

| Logical role | A source member | B source member | Paired source hashes | Comparison / call evidence |
|---|---|---|---|---|
| Local keyboard input | A `KeyboardInput.tick()`, lines 23-36 | B `KeyboardInput.tick()`, lines 23-36 | A `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3`; B `b4bbb410650444c30d2a62d70fd3c6cd1aefa104b8a12e474e2a478a62089c39` | Same key read order, impulse branch and normalized `Vec2` construction; only local names differ. |
| Input record/controller | A `Input` declaration, line 6; `ClientInput.tick()`, line 10 | B same class/member anchors | A Input `1c0c12af07da1f90651c3a7c4eb19bab6486b6391dcbfdd2926f06e3f36c2f06`; B Input `ee9ce4cfd647e09c55a9562f917fa504c0fba081bcfbbeb15871cf70818bc447`; ClientInput both `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` | Same dispatch and bitfield schema. A `Input.EMPTY` is mutable while B declares it final; reassignment reachability remains open. |
| Local-player tick/pre-travel input | A `LocalPlayer.tick()` 211-235; `aiStep()` 728-890; `applyInput()` 653-666; `modifyInput()` 668-684; square helpers 686-704 | B `LocalPlayer.tick()` 227-251; `aiStep()` 767-929; `applyInput()` 692-705; `modifyInput()` 707-723; square helpers 725-743 | A LocalPlayer `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`; B `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` | Bodies checked in order: local tick dispatches to `aiStep`; input sampling and application/scaling math match, including expression order. |
| Player shared tick/aiStep/travel | A `Player.tick()` 238-294; `aiStep()` 452-493; `travel(Vec3)` 1360-1383 | B `Player.tick()` 231-291; `aiStep()` 442-483; `travel(Vec3)` 1381-1404 | A Player `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`; B `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` | Tick movement ordering matches; impulse-context timer update relocated from Player.tick to LivingEntity.tick and is covered by S1.2.1. Swim/flight travel adjustments match. Orb predicate syntax is a separate non-movement consumer candidate. |
| Living movement integration | A `LivingEntity.tick()` 2610-2685; `aiStep()` 2877-3000; `travel(Vec3)` 2309 onward | B `LivingEntity.tick()` 2699-2774; `aiStep()` 2976-3100; `travel(Vec3)` 2392 onward | A LivingEntity `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`; B `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` | Dynamic `tick()` -> `aiStep()` dispatch confirmed; player velocity cutoff, input/jump/travel path and inspected travel formulas match. Type predicate and fluid-shape dependencies remain open. |
| Entity movement/collision | A `Entity.move`, `maybeBackOffFromEdge`, `collide`, `moveRelative`, `refreshDimensions` | B corresponding `Entity` members identified in paired source | A Entity `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`; B `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` | Anchors identified; bounded body comparison remains open. |

Observed call order: client `LocalPlayer.tick()` -> `Player.tick()` -> `LivingEntity.tick()` -> `Entity.tick()`; `LivingEntity.tick()` dynamically invokes `LocalPlayer.aiStep()` before rotation/end-of-tick updates. That calls `Player.aiStep()` -> `LivingEntity.aiStep()`, whose `applyInput()` dynamically dispatches back to `LocalPlayer.applyInput()`, then jump/travel/post-travel. Keyboard state is sampled inside `LocalPlayer.aiStep()`. Travel/collision closure remains open. B diagnostics omit LocalPlayer anchors; its source file exists and relevant bodies were checked manually.

## Required source inventories

Each inventory maps to the bounded slices below. Exact A/B pair sources are ready and verified; inventory completion remains pending while paired slices, dependencies and resource-backed inputs are audited.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1.1-S1.7,S1.2.1-S1.2.2,S2.5,S2.7,S3.1-S3.10,S3.1.1,S3.3.1,S3.6.1,S7.4-S7.5; evidence=exact pair sources verified; slice audit open.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S1.2.1,S1.4-S1.6,S2.1-S2.7,S3.3-S3.10,S3.3.1,S4.4-S4.7; evidence=exact pair sources verified; writer-consumer audit open.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S2.2,S2.6,S4.1-S4.7,S5.3-S5.6,S5.9; evidence=exact pair sources verified; provider inventory open.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S3.2,S3.5-S3.7,S3.6.1,S4.6,S5.1-S5.9; evidence=exact pair sources verified; registrations/resources audit open.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S2.5,S3.2,S3.4,S3.6,S3.8-S3.9,S6.1-S6.6; evidence=exact pair sources verified; resource/data audit open.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=S7.1-S7.4; evidence=exact pair sources verified; external-input provenance audit open.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; slice_ids=S1.2.1; evidence=source-stage audit open; direct vanilla-state reads in movement predicates remain in scope as consumers only.

## Coverage ledger

Each entry is a bounded behavior boundary. Refine broad plans into exact member ranges as new dependencies appear; keep every terminal disposition closed over reachable callers, state writers, shape providers, registrations and resources.

### Slice S1.1: Local input sampling and key/controller input state

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: key-state reads in `KeyboardInput.tick()` and impulse/vector construction through `ClientInput.tick()`; exact paired bodies inspected.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/client/player/KeyboardInput.java` :: `tick()`, lines 23-36, SHA-256 `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3`; `Input.java` :: record, line 6; `ClientInput.java` :: `tick()`, line 10.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/KeyboardInput.java` :: `tick()`, lines 23-36, SHA-256 `b4bbb410650444c30d2a62d70fd3c6cd1aefa104b8a12e474e2a478a62089c39`; `Input.java` :: record, line 6; `ClientInput.java` :: `tick()`, line 10.
- State producers/writers -> consumers/readers: keyboard mappings produce booleans read by `KeyboardInput.tick()`; sampled `Input` is consumed by `LocalPlayer.aiStep()` / `applyInput()`. `ClientInput.tick()` is no-op in both; vector logic matches. A mutable `Input.EMPTY` has no explicit reassignment in either published source tree; B final modifier has no reachable vanilla movement effect found.
- Parent slices / dependencies / closure evidence: caller/input application tracked in S1.2-S1.3; direct static assignment scan found no `Input.EMPTY =` writes in either source tree.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): Same key read order, equal-key zero impulse and ±1 impulse cases, `Vec2(left, forward).normalized()` construction, and input record/controller behavior; local variable naming differs. The `Input.EMPTY` mutability syntax difference has no source-tree writer and the record is immutable.
- Finding IDs or checked absence/replacement path: none; checked absence of explicit `Input.EMPTY` reassignment in both exact source trees.

### Slice S1.2: LocalPlayer and Player.tick sequencing and packet updates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: client and shared player tick guards, `super.tick()` dispatch, position clamping, tick-state updates, pose update and local movement packet dispatch.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java` :: `tick()`, lines 211-235, SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`; `ready/1.21.11/mojmap/net/minecraft/world/entity/player/Player.java` :: `tick()`, lines 238-294, SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java` :: `tick()`, lines 227-251, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; `ready/26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java` :: `tick()`, lines 231-291, SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`.
- State producers/writers -> consumers/readers: `LocalPlayer.tick()` calls `Player.tick()`; `Player.tick()` invokes `LivingEntity.tick()` before post-super clamping/pose work; the inherited living tick calls the dynamic `aiStep()` used by the input/travel chain.
- Parent slices / dependencies / closure evidence: S1.1 input producer; S1.2.1 handles the moved damage-context timer; `LivingEntity.tick()`/dynamic `aiStep()` full order remains in S1.2.2.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): Inspected movement-relevant guard/order, position clamp, and packet/update path match; variable names differ. The non-movement impulse-context timer relocation is dispositioned separately in S1.2.1.
- Finding IDs or checked absence/replacement path: none.

### Slice S1.2.1: Impulse-context grace timer relocation

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: A decrements `Player.currentImpulseContextResetGraceTime` after `super.tick()`; B moves the field to `LivingEntity` and decrements it in `LivingEntity.tick()` after the dynamic `aiStep()` and rotation tail.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/player/Player.java` :: `tick()` lines 288-294 and impulse-context methods lines 2004-2035, SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java` :: grace-timer field line 196, `tick()` decrement lines 2811-2813 and context methods lines 1785-1815, SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`; B `Player.tick()` lines 231-291 SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`.
- State producers/writers -> consumers/readers: A/B timer is set/reset by impulse-context APIs and decremented once after `aiStep()` in the player tick chain. Its remaining consumers gate fall-damage context reset and explosion/fall attribution; it does not read or write player position, velocity, collision, jump or travel state.
- Parent slices / dependencies / closure evidence: tick order verified from Player.tick -> LivingEntity.tick -> dynamic aiStep; context field consumers are fall-damage/attribution paths and are excluded by INV-EXCLUSIONS. The relocated decrement has the same once-per-player-tick post-aiStep timing.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): For Player, timer update remains after movement `aiStep()` and once per tick; B generalizes its storage to LivingEntity, but no direct player-motion read/write is added or removed. Remaining context semantics affect fall damage only, outside the movement behavior comparison.
- Finding IDs or checked absence/replacement path: none; exact consumer search found no delta-movement or movement-state writes in the context APIs.

### Slice S1.2.2: Inherited LivingEntity tick dispatch to dynamic aiStep

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.tick()` calls `super.tick()`, performs pre-step state updates, invokes dynamic `aiStep()` under `!isRemoved`, then completes rotation/head/range and end-of-tick updates; parent `Player.tick()` calls this before its post-super movement-state tail.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java` :: `tick()`, lines 2610-2723, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`; `Player.tick()` lines 238-294, SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java` :: `tick()`, lines 2699-2815, SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`; `Player.tick()` lines 231-291, SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`.
- State producers/writers -> consumers/readers: local player `tick()` -> `Player.tick()` -> `LivingEntity.tick()` -> dynamic `LocalPlayer.aiStep()`; returned tick then continues Player and LocalPlayer post-super paths. Same dispatch guard and update ordering hold. Non-movement damage/equipment producers are excluded.
- Parent slices / dependencies / closure evidence: paired client/player tick bodies in S1.2; input producer S1.1; timer relocation split into S1.2.1.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): Dynamic `aiStep()` dispatch remains after `super.tick()` and before LivingEntity's rotation/end-state tail in both; same `!isRemoved` guard. Parent call chain preserves the same dispatch point; other local names differ.
- Finding IDs or checked absence/replacement path: none.

### Slice S1.3: Yaw-to-input conversion, diagonal normalization and input scaling

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer.applyInput()`, `modifyInput()`, square-movement normalization and distance-to-unit-square arithmetic.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java` :: `applyInput()` lines 653-666, `modifyInput()` lines 668-684, square helpers lines 686-704, SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java` :: `applyInput()` lines 692-705, `modifyInput()` lines 707-723, square helpers lines 725-743, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`.
- State producers/writers -> consumers/readers: `input` from S1.1 is assigned to `xxa`, `zza`, and `jumping`; scaled movement vector is consumed by shared travel path S3.
- Parent slices / dependencies / closure evidence: S1.1 input source; caller ordering under S1.2. Continued dependencies (effects/item use/sneak) are included in this body and modifier consumers remain under S6.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): Same zero-length checks, `.98F` scaling, item-use and sneak multipliers, length/direction normalization and `min(length * distance, 1)` order. Only variable names/formatting differ.
- Finding IDs or checked absence/replacement path: none.

### Slice S1.4: Sprint transitions, timers and start/stop gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer `aiStep()` sprint-trigger reset/decrement, double-tap and sprint-key start, run/swim stop checks, `canStartSprinting()`, `isSprintingPossible()`, and `sendIsSprintingIfNeeded()` packet edge; Entity sprint-flag getter/setter.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java` :: `aiStep()` lines 728-890, `sendIsSprintingIfNeeded()` lines 287-296, `shouldStopRunSprinting()`/`shouldStopSwimSprinting()` lines 882-891, `isSprintingPossible()`/`canStartSprinting()`/`vehicleCanSprint()` lines 1096-1117; SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`. `Entity.isSprinting()`/`setSprinting()` lines 2582-2588; SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java` :: `aiStep()` lines 767-929, `sendIsSprintingIfNeeded()` lines 303-312, `shouldStopRunSprinting()`/`shouldStopSwimSprinting()` lines 921-930, `isSprintingPossible()`/`canStartSprinting()`/`vehicleCanSprint()` lines 1135-1156; SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`. `Entity.isSprinting()`/`setSprinting()` lines 2646-2652; SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`.
- State producers/writers -> consumers/readers: sampled forward/sprint/back/shift input, prior sprint-trigger timer, current sprint/shared-flag state, water/fall-flying/slow-movement state, collision flags, passenger/vehicle sprint capability, mobility restriction and shallow-water state feed the same gates; state transitions write shared flag 3 and the transition packet is sent only when the client sprint state differs from `wasSprinting`. The non-passenger gate reads `hasEnoughFoodToDoExhaustiveManoeuvres()`; its food-state producer is excluded, while this direct movement consumer remains in scope under S2.7/INV-EXCLUSIONS.
- Parent slices / dependencies / closure evidence: LocalPlayer tick/input producer is in S1.1-S1.2. Entity stores sprint state in shared flag 3 in both. S2.5 item-use slowdown, S2.7 hunger/blindness consumers, swimming state and external vehicle sprint capability remain dependency rows; do not infer their producer behavior from this bounded gate comparison.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Inspected sprint start/stop and packet-edge bodies preserve the same two-tap timer reset/start order, held-sprint start, start gates, run/swim stop predicates, and flag-3 write. Paired expressions and comparison/order match; only locals/formatting differ. Food/mobility restriction producers, item-use, swim/fall-flying predicates, and vehicle behavior still require closure, so no terminal no-difference claim is made.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S1.5: Jump input, jump cooldown/state, auto-jump and riding gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer `aiStep()` auto-jump timer synthesis, `wasJumping` edge gates, flight-jump trigger interaction, rideable-jump cooldown check, charge/release state, scale arithmetic and `super.aiStep()` order.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java` :: `aiStep()` lines 728-890, including auto-jump lines 750-755 and riding-jump block 850-880; SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java` :: `aiStep()` lines 767-929, including auto-jump lines 789-794 and riding-jump block 889-919; SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`.
- State producers/writers -> consumers/readers: jump key state and auto-jump timer produce the synthetic jump input; `wasJumping`, `jumpTriggerTime`, vehicle jump cooldown, `jumpRidingTicks`, and `jumpRidingScale` control toggles and charge. A release edge writes `jumpRidingTicks=-10`, calls `PlayerRideableJumping.onPlayerJump(floor(scale*100))`, then sends the riding-jump packet; a press edge resets tick/scale, and held input increments charge with the same two scale branches. `super.aiStep()` follows the riding block.
- Parent slices / dependencies / closure evidence: S1.1-S1.2 input/tick order; S1.6 owns the mayfly/fall-flying branch encountered in the same method; S3.4 owns the resulting ground-jump impulse. Vehicle cooldown/capability and vehicle-side jump behavior are external dependencies; do not emulate vehicle physics.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The inspected auto-jump and rideable charge/release bodies preserve the same timer decrement, synthetic input order, cooldown gate, prior-jump release/press checks, floor conversion, tick/scale updates and `super.aiStep()` position. No player movement delta is established here. The interface callback may update a non-player vehicle and is excluded; player-side call and packet ordering remain in scope. Whole method input-state and vehicle eligibility dependencies are not yet closed.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S1.6: Flight toggle/input, abilities and flight-speed path

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer `aiStep()` mayfly/spectator enable, double-jump flight toggle, ground jump-on-enable, ability synchronization, fall-flying request edge, vertical flight input velocity, and ground flight reset.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java` :: `aiStep()` lines 728-890; SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`. A Player `getFlyingSpeed()` lines 1959-1964; SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java` :: `aiStep()` lines 767-929; SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`. B Player `getFlyingSpeed()` lines 1953-1958; SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`.
- State producers/writers -> consumers/readers: ability `mayfly/flying`, spectator/game-mode state, jump prior-state/timer, swim/vehicle eligibility, `isControlledCamera`, shift/jump input and on-ground state feed the same branch order. Flight toggle writes ability state and syncs it; vertical input adds `inputYA * flyingSpeed * 3.0F` to Y velocity; landing clears flight for non-spectators. Player flying speed returns ability flying speed (doubled while sprinting) while flying and `0.025999999F` sprint / `0.02F` otherwise.
- Parent slices / dependencies / closure evidence: S1.5 owns the shared jump edge and vehicle interaction; S1.2 input order; S3 travel owns the downstream flight path. Server-supplied ability state and flight speed defaults, game-mode transition and packet application remain external producer dependencies.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Paired player branches retain the same spectator grant, double-jump gate, swimming/jumpable-vehicle condition, flight toggle, optional `jumpFromGround()`, ability update, jump-trigger reset, fall-flying command guard, vertical delta expression and non-spectator ground reset. Player `getFlyingSpeed()` constants and sprint multiplier match. Ability provenance and downstream flight-state closure remain open, so this is a bounded comparison.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S1.7: Unstuck behavior and other input-to-tick movement gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer's four ordered `moveTowardsClosestSpace()` probes during `aiStep()` when `!noPhysics`, plus the inherited Entity neighbor scan and direct random velocity write. Other sprint/jump/flight input-to-tick gates are separately inventoried in S1.4-S1.6.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java` :: four probe calls at lines 756-761; SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`. `ready/1.21.11/mojmap/net/minecraft/world/entity/Entity.java` :: `moveTowardsClosestSpace()` lines 2779-2808; SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`. `BlockBehaviour.BlockStateBase.isCollisionShapeFullBlock()` lines 873-875; SHA-256 `cf7eff07efb53d95c8ffa633ff38c7d3f454e185f197de49eb4da279a8da9d17`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java` :: four probe calls at lines 795-800; SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`. `ready/26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java` :: `moveTowardsClosestSpace()` lines 2845-2874; SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`. `BlockBehaviour.BlockStateBase.isCollisionShapeFullBlock()` lines 894-896; SHA-256 `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13`.
- State producers/writers -> consumers/readers: Four corner probes are skipped only under no-physics. Each computes containing block and fractional position, then scans neighbors in fixed `NORTH,SOUTH,WEST,EAST,UP` order (no DOWN query). It accepts only neighbors whose block state reports a non-full collision shape, computes signed distance to that face, and replaces the selected direction only on strict smaller distance, preserving earlier-direction ties. Every call consumes one `random.nextFloat()` and derives speed as `random * 0.2F + 0.1F`; it scales the existing velocity by `0.75` and overwrites only the selected axis with direction step times speed. The state method uses cached full-collision shape when available, otherwise dispatches to the block's context/position collision-shape predicate.
- Parent slices / dependencies / closure evidence: Entity collision-shape behavior is paired; all possible selected block states, dynamic shape providers and relevant registry/data entries remain under S4/S5/D2. Random source/seed lifecycle is an entity-state dependency. S1.4-S1.6 still own other input/tick gates.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Both releases call all four probes in the same order under the same `!noPhysics` guard and preserve the same neighbor order, strict nearest-face tie rule, random draw count, float speed expression, `0.75` scale and axis-selective write. The selected face depends on each candidate state's collision shape and the random value depends on entity random state; provider/random-writer closure remains open, so no terminal conclusion is claimed.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.1: Player pose selection and pose transition timing

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: `Player.tick()` to `updatePlayerPose()`, including the always-fit-SWIMMING gate, desired pose priority, spectator/passenger exception, fit probes and CROUCHING fallback before SWIMMING fallback.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/player/Player.java` :: `tick()` line 290, `updatePlayerPose()` lines 352-366, `getDesiredPose()` lines 368-380; SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java` :: `tick()` line 283, `updatePlayerPose()` lines 342-356, `getDesiredPose()` lines 358-370; SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`.
- State producers/writers -> consumers/readers: sleep, swimming, fall-flying, auto-spin attack, shift and flying ability select the desired pose; spectator/passenger/fit state controls fallback. The method writes `DATA_POSE` only after the current pose can fit within SWIMMING dimensions; the transition causes dimension/eye-height consumers elsewhere in Entity/LivingEntity.
- Parent slices / dependencies / closure evidence: Player.tick caller order matches; S2.2 owns dimensions, eye-height, resize and collision-probe closure. Swimming/fall-flying/auto-spin state producers and collision-shape providers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Paired code has the same initial SWIMMING fit gate, desired-pose priority (sleep, swim, fall-flying, spin, then shift unless flying), spectator/passenger bypass, desired-pose test, CROUCHING fallback, final SWIMMING fallback and `setPose` order. Only names/formatting differ. Exact collision and pose-state producer/consumer closure is incomplete, so no terminal disposition is claimed.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.2: Pose dimensions, eye height, resize collision queries

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Player pose-fit AABB (`getDimensions(pose).makeBoundingBox(position).deflate(1.0E-7)`), `Entity.setPose/getPose`, `refreshDimensions()` dimension/eye-height writes and player exclusion from server-side post-size-change relocation.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/player/Player.java` :: `canPlayerFitWithinBlocksAndEntitiesWhen()` lines 382-384; SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`. `Entity.setPose/getPose()` lines 413-421 and `refreshDimensions()` lines 3226-3243; SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`. `LivingEntity.getDimensions(Pose)` lines 3561-3572; SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java` :: `canPlayerFitWithinBlocksAndEntitiesWhen()` lines 372-374; SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`. `Entity.setPose/getPose()` lines 432-440 and `refreshDimensions()` lines 3320-3337; SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`. `LivingEntity.getDimensions(Pose)` lines 3672-3683; SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`.
- State producers/writers -> consumers/readers: pose is stored in synced entity data; `refreshDimensions()` reads current pose/dimensions, writes dimensions and eye height from pose dimensions, then reapplies position. The fit helper uses a deflated pose-specific AABB. Entity size-change relocation is guarded against Player on both sides; remaining pose-data callback/refresh call paths and collision provider behavior are not yet closed.
- Parent slices / dependencies / closure evidence: S2.1 owns pose selection and caller order; S2.6/S4/S5 own collision query implementation and shape providers. Entity and LivingEntity dimension producers, pose registrations/default player pose dimensions and every refresh caller still need exhaustive comparison.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Paired pose-fit and refresh bodies preserve the `1.0E-7` deflation, dimensions/eye-height assignment, position reapplication, size limits, first-tick/no-physics guards, player exclusion and free-position relocation arithmetic. `setPose()` writes the same pose data accessor in both. The complete pose-dimension table, data callback/reachability graph and collision-provider dependencies remain open; this is bounded evidence only.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.3: Swim/crawl state and movement-mode selection

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Player `updateSwimming()` flight override and Entity `updateSwimming()` sprint/submersion/passenger predicates; tick ordering around water/eye-state update and swim-state write.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/player/Player.java` :: `updateSwimming()` lines 1391-1397; SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`. `Entity.updateSwimming()` lines 1491-1498 and tick call/order lines 501-507; SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java` :: `updateSwimming()` lines 1412-1418; SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`. `Entity.updateSwimming()` lines 1548-1555 and tick call/order lines 522-528; SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`.
- State producers/writers -> consumers/readers: Player forces swimming false while flying, otherwise delegates; shared Entity sets swimming from sprint, water/underwater, passenger and current block fluid state. Both Player overrides match. The preceding tick producer changed from `updateInWaterStateAndDoFluidPushing()` plus `updateFluidOnEyes()` to `wasEyeInWater` capture plus `updateFluidInteraction()`; that changed fluid producer/consumer path is open under S4.6 and may affect the predicates consumed here.
- Parent slices / dependencies / closure evidence: Player tick/pose order S1.2/S2.1 and fluid producer S4.6 are dependencies. Fluid-state and collision providers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The Player flying override and shared sprint/swim predicates match, including the non-swimming transition requiring underwater, not a passenger, and WATER at block position. Entity tick ordering changes around the producer: A updates fluid state then eye state before swimming; B records prior eye-in-water, updates the new interaction tracker, then swimming. No final equivalence claim until S4.6 resolves the producer path.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.4: Ability defaults, stored air speed and player movement state

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: `Abilities` defaults, mutable flight/walking speed storage and `Packed` persistence; player read/write of the ability record and walking-speed attribute base; clientbound ability update reader; and direct LocalPlayer vertical flight impulse consumer. Mouse-wheel spectator flight-speed writer is inventoried as a dependency to S1.6.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/player/Abilities.java` lines 6-65; SHA-256 `d82af05310709658c7d324632b6bf92afa539322c185078bd649d97e11ffca39`. `Player.java` :: ability read and movement-speed base write lines 649-650, ability serialization lines 658-672, `getFlyingSpeed()` lines 1958-1965; SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`. `ClientPacketListener.java` :: `handlePlayerAbilities()` lines 2000-2009; SHA-256 `f1ebdb54d717266c894c87381c98bad101980d252de5bae6ca55c37aece1732e`. `LocalPlayer.java` :: direct vertical flying input impulse lines 832-845; SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`. `MouseHandler.java` :: spectator scroll writer lines 211-217; SHA-256 `c431b49352e79fc196555ba337c210e6dded8504ef13bd8810fa17cee7efe44c`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/player/Abilities.java` lines 6-65; SHA-256 `0be56964201d7cae7ff4ba7c296afd568f9a65cd74f8926145c15714f82edae7`. `Player.java` :: ability read and movement-speed base write lines 639-640, ability serialization lines 646-660, `getFlyingSpeed()` lines 1952-1959; SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`. `ClientPacketListener.java` :: `handlePlayerAbilities()` lines 2087-2096; SHA-256 `eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82`. `LocalPlayer.java` :: direct vertical flying input impulse lines 872-884; SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`. `MouseHandler.java` :: spectator scroll writer lines 211-217; SHA-256 `887e11f879c5172c773d35020ea9ae5c98452b184607ec19b684a26c357a60dd`.
- State producers/writers -> consumers/readers: `Abilities` defaults invulnerability/flying/mayfly/instabuild false, mayBuild true, flying speed `0.05F`, walking speed `0.1F`; packed codec defaults and field order match. Player save/load serializes the same packed ability record, then restores walking speed to `Attributes.MOVEMENT_SPEED` base. Client packet handling writes the same five flags followed by flying/walking speeds. LocalPlayer adds vertical delta only under its flying-and-controlled-camera gate and nonzero up/down key sum, using `input * abilities.getFlyingSpeed() * 3.0F`. Spectator mouse scroll clamps flying speed from current value plus wheel delta times `0.005F` into `[0.0F,0.2F]` on both versions.
- Parent slices / dependencies / closure evidence: The ability fields/default/persistence and vertical flight impulse are paired, but the full `aiStep()` flight toggle/start/stop and packet edge belong to S1.6; movement-speed attribute definitions/operation chain are S3.9/S6.2; incoming ability packet provenance is S7.1. Those consumers/producers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Exact field defaults, packed codec defaults/order, save/load flow, packet application order, direct flight impulse math and spectator speed writer match. This source comparison does not establish that an externally supplied ability packet or attribute value is produced identically across eras; relevant direct producers/consumers remain assigned to S1.6/S3.9/S6.2/S7.1.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.5: Item-use slowdown and direct active-item movement gate

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer.isSlowDueToUsingItem()`, its use in sprint-trigger reset and sprint start, and `modifyInput()`'s active-item scaling stage; `LocalPlayer.startUsingItem()`/`stopUsingItem()` plus inherited `LivingEntity` active stack/remaining-use state; and `USE_EFFECTS` component type/default/codec and vanilla sword-property override. The item-use movement values are inputs; item/data resource enumeration remains D2.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java` :: `startUsingItem()` lines 510-519, `isSlowDueToUsingItem()`/`itemUseSpeedMultiplier()` lines 526-532, `modifyInput()` lines 668-684, sprint-trigger reset lines 763-765, and `canStartSprinting()` lines 1102-1108; SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`. `LivingEntity.startUsingItem()` lines 3335-3349 and `stopUsingItem()` lines 3448-3460; SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`. `UseEffects` lines 9-28; SHA-256 `bb69726562d4f68006732a159abceb04e63685e8c05d2338b15d69a53f60bb6f`. `DataComponents.USE_EFFECTS` lines 115-117 and `COMMON_ITEM_COMPONENTS` lines 407-416; SHA-256 `a6647a61e315769a256e2a8cfe96596c083ab3ce11cda292a70e89f3aa46d1fc`. `Item.Properties.sword()` lines 508-509; SHA-256 `53e371efb8ef6eca5e8585a4598b3251dc37bd005b6d4c068c1f8c67d4efeee3`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java` :: `startUsingItem()` lines 546-556, `isSlowDueToUsingItem()`/`itemUseSpeedMultiplier()` lines 563-569, `modifyInput()` lines 707-723, sprint-trigger reset lines 802-804, and `canStartSprinting()` lines 1141-1147; SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`. `LivingEntity.startUsingItem()` lines 3442-3456 and `stopUsingItem()` lines 3559-3571; SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`. `UseEffects` lines 9-28; SHA-256 `18525e459d066a046a20dce6176b3a9764de09f0d91e79c61c2203709744c8dd`. `DataComponents.USE_EFFECTS` lines 119-121 and `COMMON_ITEM_COMPONENTS` lines 415-424; SHA-256 `64c8592c21c83c35dee0d4cc780b273d18ff30c4fea92c0868f296a164b2a11e`. `Item.Properties.sword()` lines 542-543; SHA-256 `a92a71ef9acd0a98e26914b61382e87f1ba098f779587d452f7373dfa8690ba4`.
- State producers/writers -> consumers/readers: LocalPlayer accepts item use only for a nonempty hand stack while not already using; inherited `LivingEntity.startUsingItem()` captures that stack and its use duration, while `stopUsingItem()` clears the active stack and remaining duration. The LocalPlayer override also sets/clears its client `startedUsingItem` flag. In both versions, sprint-start eligibility reads `isUsingItem() && !activeStack.getOrDefault(USE_EFFECTS, DEFAULT).canSprint()`, and the input transform, after the zero-vector early return, applies `scale(0.98F)`, then active-item `speedMultiplier()` only when using an item and not riding, then sneaking speed, then square-movement normalization. The `UseEffects` codec bounds speed multiplier to `[0.0F,1.0F]`; default and common item component are `(false,true,0.2F)`, while the paired sword property sets `(true,false,1.0F)`. Component key `use_effects`, persistence/network codecs, defaults, and sword override match.
- Parent slices / dependencies / closure evidence: Direct active-item movement consumers and local/inherited active-use state wiring are paired. The item component values and resource/registry population audit remain under D2/INV-MODIFIERS; this slice claims equivalence of the consumer and paired Java defaults/override, not exhaustive serialized item definitions or server-supplied stack components. Sprint-input tick ordering outside the active-item reset condition remains S1.4.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): For a nonempty held stack that enters item use, with the same reachable `UseEffects` value, both versions use the same strict boolean sprint gate and the same float input scaling stages in the same order. Empty vectors return before any scaling; passenger status skips only the item multiplier in `modifyInput()`, while `isSlowDueToUsingItem()` itself checks active use and `canSprint()`. Same default/sword values and codec preserve the vanilla common cases. No consumer delta is evidenced; data/resource and externally supplied item-component values remain source inputs rather than being inferred here.
- Finding IDs or checked absence/replacement path: none; paired consumer/state/component declarations show no direct item-use movement delta.

### Slice S2.6: Edge sneaking and support probing

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Player `maybeBackOffFromEdge()`, `isAboveGround()`, `canFallAtLeast()`, and `isStayingOnGroundSurface()`, including X-only then Z-only then combined support probes; backing collision query implementation checked through `CollisionGetter.noCollision()`.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/player/Player.java` :: `isStayingOnGroundSurface()` 309-311, `maybeBackOffFromEdge()` 889-938, `isAboveGround()` 940-942, `canFallAtLeast()` 944-951; SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java` :: `isStayingOnGroundSurface()` 299-301, `maybeBackOffFromEdge()` 880-929, `isAboveGround()` 931-933, `canFallAtLeast()` 935-949; SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`.
- State producers/writers -> consumers/readers: reads flying ability, vertical/horizontal proposed movement, mover type, shift state, grounded/fall-distance state, max-up-step and bounding box; writes only a reduced proposed X/Z motion returned to `Entity.move()` before collision. The support probe tests a 1e-7 inset AABB lowered by minHeight plus 1e-7 with `noCollision(player, box)`.
- Parent slices / dependencies / closure evidence: `Entity.move()` caller ordering is recorded in `S4.1`. `CollisionGetter.noCollision(entity, aabb)` A 51-53 / 59-67 / 69-80 (SHA-256 `ca2a5c4561fa8b263d7ce8513a152f9c1d6537f4f011859351ce58b39a7f4e70`) and B 51-67 / 69-80 (SHA-256 `dddff4897e8d5d01e3ac132d85c474e6a3b9bae928e48b8c907aa9eabdb7fb01`) use the same block-then-entity-then-border checks, with `alwaysCollideWithFluids=false`. Their block path uses the paired `BlockCollisions` scan (A SHA-256 `05875bb3a6fa49628ac92174e68ec3b5bed1cf39ebd4e7158b1885aead0a6a68`, B SHA-256 `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e`); exact block-shape provider/resource closure remains open under S4/S5 and D2.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The paired player path has the same flying/upward/mover-type/staying-ground/above-ground gates, 0.05 step magnitude and sign, X-then-Z decrement loops, combined decrement loop, and returned Y preservation. The helper constructs the same epsilon-inset support AABB; `noCollision` selects the same ordered block/entity/border checks. Local names/formatting differ. Provider and relevant shape closure remain open, so no complete disposition is claimed.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.7: Sprint-gate consumers of hunger/blindness state

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Direct sprint-start restriction predicates only: `LocalPlayer.isSprintingPossible()` and its consumers in `canStartSprinting()`/run-stop logic, plus `Player.hasEnoughFoodToDoExhaustiveManoeuvres()` and `Player.isMobilityRestricted()`. Food-state production and blindness-effect application are excluded; their vanilla values are direct inputs here.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java` :: `isSprintingPossible()`/`canStartSprinting()` lines 1096-1109, `shouldStopRunSprinting()`/`shouldStopSwimSprinting()` lines 882-890, SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`; `Player.hasEnoughFoodToDoExhaustiveManoeuvres()` lines 1577-1579 and `isMobilityRestricted()` lines 1949-1951, SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java` :: `isSprintingPossible()`/`canStartSprinting()` lines 1135-1148, `shouldStopRunSprinting()`/`shouldStopSwimSprinting()` lines 921-929, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; `Player.hasEnoughFoodToDoExhaustiveManoeuvres()` lines 1571-1573 and `isMobilityRestricted()` lines 1943-1945, SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`. `MobEffects.BLINDNESS` is registered as the same `blindness` key on both sides (A MobEffects SHA-256 `e27eef187ce134c3b64300fd7a4c7f691d553a172ec26ebac79797a030db7047`; B `532df5c7cd47edd3d9d091c2eae0962c279224c8f5f206860e9df45bc3b0c684`, line 72).
- State producers/writers -> consumers/readers: `isSprintingPossible()` rejects mobility restriction, then selects vehicle sprint capability for passengers or `hasEnoughFoodToDoExhaustiveManoeuvres()` for non-passengers, then applies the shallow-water condition. `canStartSprinting()` and run-stop predicates consume this helper. The food predicate returns `getFoodData().hasEnoughFood() || abilities.mayfly`; mobility restriction returns `hasEffect(MobEffects.BLINDNESS)`. Food and effect-state producers remain excluded by campaign scope.
- Parent slices / dependencies / closure evidence: Exact direct consumer bodies are paired here. Broader input/toggle and other sprint gates remain tracked in S1.4-S1.6; item-use movement consumer and active-state wiring are separately paired in S2.5. This slice makes no claim about the excluded food/effect producers.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): For any reachable values of food state, mayfly ability, blindness effect, passenger/vehicle sprint gate, and shallow-water state, the hunger/blindness terms enter the same boolean expression in the same order. Both releases use the same `getFoodData().hasEnoughFood() || getAbilities().mayfly` check and both mobility predicates read the same blindness effect key. No food, exhaustion, saturation, or effect-application production is emulated or compared here; those remain intentionally excluded sources.
- Finding IDs or checked absence/replacement path: none; paired consumer expression shows no direct gate delta.

### Slice S3.1: Travel dispatch and ground acceleration

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Travel dispatch and ground acceleration. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.1.1: Living travel branch dispatch

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.travel(Vec3)` selects fluid travel first, then fall-flying, else air travel; `shouldTravelInFluid(FluidState)` guard checked with exact call order.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java` :: `travel()` lines 2309-2315 and `shouldTravelInFluid()` lines 2317-2319, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java` :: `travel()` lines 2392-2398 and `shouldTravelInFluid()` lines 2400-2402, SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`.
- State producers/writers -> consumers/readers: water/lava/affected-by-fluid/can-stand predicates select `travelInFluid`; fall-flying pose selects `travelFallFlying`; otherwise `travelInAir`. Branch results remain covered by S3.2-S3.8.
- Parent slices / dependencies / closure evidence: exact player caller is through inherited `LivingEntity.travel`; guard consumers are inventoried in S2/S6, while body branch order is closed here.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): Both releases evaluate the same fluid predicate and preserve the same fluid > fall-flying > air branch order and helper dispatch. No arithmetic or state write occurs in this dispatcher.
- Finding IDs or checked absence/replacement path: none.

### Slice S3.2: Ground friction, speed-factor consumption and post-travel drag

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Ground friction, speed-factor consumption and post-travel drag. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.3: Air acceleration, drag/gravity and velocity thresholds

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Air acceleration, drag/gravity and velocity thresholds. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.3.1: Player low-horizontal-velocity cutoff

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: player-specific branch at the beginning of `LivingEntity.aiStep()` that zeros X/Z below horizontal squared speed `9.0E-6`, then writes the clamped movement vector.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java` :: `aiStep()`, lines 2894-2912, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`; `Entity.getType()`, lines 347-349, SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`; `Vec3.horizontalDistanceSqr()`, lines 189-191, SHA-256 `a4050765738a0cfdb1100b83f2ba4effd40155de99bb703a8381dbe775148ec1`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java` :: `aiStep()`, lines 2993-3011, SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`; `Entity.typeHolder()`, lines 366-368, SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`; `TypedInstance.is(rawType)`, lines 22-23, SHA-256 `caa4db2c73d7a18a59964ec2db7d3401a823da9f15a8e51aa266ba6f8a80e6df`; `Holder.Reference.value()`, lines 162-168, SHA-256 `c8b9b5941f0aa5a81d00a24bf7ba1529e9bb3d5f82fe36beef8f25f489c5c546`; `EntityType.builtInRegistryHolder()`, lines 1606-1607, SHA-256 `e5f37ee2dd639b7271197e58f7a6f3bdbedc0f0f1d618039d159a0e7de315229`; `Vec3.horizontalDistanceSqr()`, lines 192-194, SHA-256 `57b08ae818868a4fffdc9b5dadba2b127138c8945c2194f6f4b01e6150cd3bcb`.
- State producers/writers -> consumers/readers: previous delta movement is read; the branch zeroes X/Z when the threshold passes and the same post-branch code writes `new Vec3(x,y,z)` through `setDeltaMovement`, which rejects non-finite values in both `Entity` sources.
- Parent slices / dependencies / closure evidence: player type source path closes D3: A `Entity.getType()` returns `this.type`; `EntityType` does not override `equals`, so `equals(PLAYER)` is reference identity; A `EntityType.java` SHA-256 `fc2abb3e905b9a63a27f0b027fad089d9d40e5d3d45608406b5da2a5ec47a73e` has no `equals` override. B `Entity.typeHolder()` returns `this.type.builtInRegistryHolder()`; that holder was constructed with the EntityType instance and `value()` returns that same stored value; `TypedInstance.is(rawType)` compares it by `==`. The same `EntityType.PLAYER` check therefore selects the same branch.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): Both paths use the same player gate, `horizontalDistanceSqr() < 9.0E-6`, X/Z zeroing, unchanged Y, and final delta-vector write order; helper arithmetic is `x*x + z*z` in both. Local variable names and type-check spelling differ only.
- Finding IDs or checked absence/replacement path: none.

### Slice S3.4: Jump power, sprint-jump impulse and jump state writes

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.getJumpPower()`, `getJumpBoostPower()`, `jumpFromGround()`, `jumpInLiquid()`, and the travel jump-selection branch, including the positive-power guard, Y maximum, sprinting yaw impulse, fluid-height selection, threshold comparisons, and `noJumpDelay` writes. Block jump-factor provider code is traced through property construction and registrations; movement attribute/effect production and block-state source closure remain open.
- A evidence: exact `ready/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java` :: `getJumpPower()` 2256-2262, `getJumpBoostPower()` 2264-2266, `jumpFromGround()` 2269-2281, `jumpInLiquid()` 2287-2289, jump selection 2934-2957; SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`.
- B evidence: exact `ready/26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java` :: `getJumpPower()` 2339-2345, `getJumpBoostPower()` 2347-2349, `jumpFromGround()` 2352-2364, `jumpInLiquid()` 2370-2372, jump selection 3033-3056; SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`.
- State producers/writers -> consumers/readers: reads jump-strength attribute, block jump factor, Jump Boost effect/amplifier, current delta movement, sprint state/yaw, fluid membership/height and ground state; writes delta movement, `needsSync`, and `noJumpDelay`. Exact block-factor provider chain is now closed: current-position and movement-below blocks -> `Block.getJumpFactor()` -> `BlockBehaviour.Properties.jumpFactor` field -> the `0.5F` honey-block registration or the `1.0F` default. Attribute/effect application and source-side block-state construction remain separate dependencies.
- Parent slices / dependencies / closure evidence: exact `Entity.getBlockJumpFactor()` bodies match (A `Entity.java` 984-988, SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`; B 1015-1019, SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`) and read the same current-position then movement-below block jump factors, returning current unless it equals `1.0`. A/B `Block.getJumpFactor()` returns the stored property (A `Block.java` SHA-256 `bd7f69ffe617fa1008c9311ad7c731b23f242acb5ff3bab8c1a81e41e7d15a73`, lines 471-472; B SHA-256 `1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977`, lines 510-511). Both `BlockBehaviour.Properties` default `jumpFactor` to `1.0F`, copy it in `ofFullCopy`, expose the same setter, and `BlockBehaviour` copies it into the block (A SHA-256 `cf7eff07efb53d95c8ffa633ff38c7d3f454e185f197de49eb4da279a8da9d17`, lines 97, 113, 962, 1001, 1079-1081; B SHA-256 `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13`, lines 95, 111, 988, 1029, 1107-1109). Whole-source `jumpFactor(` search found only the property setter and HoneyBlock registration in each tree; `Blocks` configures HoneyBlock with `.jumpFactor(0.5F)` (A SHA-256 `cc6636f6ace603b32edd3db4c0cfcda60be08e2c6b5def783a698f105b8f7f23`, line 5919; B SHA-256 `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f`, line 5931). `Attributes.JUMP_STRENGTH` also matches at base `0.42F`, bounds `[0.0, 32.0]`, syncable (A `Attributes.java` SHA-256 `62129028c2f7beb6d2f87bcd3345e63a7be8fc3950a7ecdbc6ee21f8fb89f843`, lines 48-50; B SHA-256 `8eee57d8375c7af39525ccd25593618fcbcbe0348da6a8738556baaaabea3c1d5`, lines 48-50). Exact `Entity.getFluidJumpThreshold()` bodies also match (A 3589-3591; B 3614-3616) as `getEyeHeight() < 0.4 ? 0.0 : 0.4`. Client-jar `data/minecraft/tags/fluid/water.json` and `lava.json` hashes match between A and B (`dcfa69a748d03dbf788d8f7b0e5eb6c8de6355a527fcaf74b9210fe4be2a3004`; `71f50fb9092d78260bc7434731fc5fd426a44e5284a6ad084ec71cb725630c6b`). Modifier/effect application and block-state construction/resource closure remain open; this is still a partial checkpoint.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Paired jump-power expressions preserve `(float)attribute * multiplier * blockFactor + jumpBoostPower`; Jump Boost uses `0.1F * (amplifier + 1.0F)` under the same effect guard; liquid jump adds `0.04F` to Y; and the travel selection uses the same fluid-height and threshold branches, including `noJumpDelay == 0` before ground jump and reset to zero when jump input is absent. `jumpFromGround()` has the same `> 1.0E-5F` guard, max-with-current-Y write, sprint check, radians conversion and operation order for `(-sin(yaw) * 0.2, 0, cos(yaw) * 0.2)`, then `needsSync = true`. Only names and line numbers differ. The slice remains open for block-factor sources, modifier/effect application, and fluid/state closure.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.5: Climbing movement and clamps

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.handleRelativeFrictionAndCalculateMovement()`, `handleOnClimbable()`, `getFrictionInfluencedSpeed()`, and the player override of `onClimbable()`. Paired bodies and call order checked; exact block-tag/resource contents and all called-state producers remain open.
- A evidence: exact `ready/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java` :: `handleRelativeFrictionAndCalculateMovement()` lines 2534-2544, `handleOnClimbable()` 2561-2576, `getFrictionInfluencedSpeed()` 2578-2580, and `onClimbable()` 1669-1687; SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`. Exact `Player.java` override lines 2043-2045, SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`.
- B evidence: exact `ready/26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java` :: `handleRelativeFrictionAndCalculateMovement()` lines 2623-2633, `handleOnClimbable()` 2650-2665, `getFrictionInfluencedSpeed()` 2667-2669, and `onClimbable()` 1689-1707; SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`. Exact `Player.java` override lines 2003-2005, SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`.
- State producers/writers -> consumers/readers: movement helper reads delta movement, friction, on-ground/horizontal-collision/jumping flags, climbability, powder-snow state, and Player flying ability; writes clamped delta movement before `move(SELF, ...)`, then may return a `0.2` vertical component for subsequent travel. `onClimbable()` reads spectator/fall-flying state, in-block state and block tags, trapdoor ladder checks; it writes `lastClimbablePos`. The Player override returns false while flying. Full tag data and called state producers remain open.
- Parent slices / dependencies / closure evidence: `S3.1.1` closes travel dispatcher order. Exact tag bytes were read from each verified client jar: both `data/minecraft/tags/block/climbable.json` entries hash to `d0e3e76d7457f3f3f3d7219fe218c7746e4b2e7626d5c388fcf193089069e365`, and both `data/minecraft/tags/block/can_glide_through.json` entries hash to `e1b304a494b93be2a3280ea6cc7c73ebc0fd76e21654af801d5e4359e94232a8`. D2 still owns the remaining movement tag/resource closure; cross-method movement predicates must be reconciled before this slice closes.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Player and LivingEntity climbability dispatch and helper sequence match. Both use identical spectator and fall-flying checks, `CAN_GLIDE_THROUGH` then `CLIMBABLE` membership, trapdoor ladder fallback, `[-0.15, 0.15]` X/Z clamp, lower Y clamp, Player-only ladder slide suppression, and ground-friction factor `0.21600002F / (friction * friction * friction)`. Movement then calls `move` and applies the same `0.2` branch. Direct resource/tag and state producer closure is not yet sufficient to claim full slice equivalence.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.6: Water travel, swimming, buoyancy/drag and fluid effects

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Water travel body after water-branch dispatch; speed/drag blend, horizontal collision climbable adjustment, fluid-fall adjustment, jump-out and ridden floating call order.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java` :: `travelInWater()` lines 2380-2407, `jumpOutOfFluid()` lines 2427-2432, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java` :: `travelInWater()` lines 2467-2494, `jumpOutOfFluid()` lines 2514-2519, SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`.
- State producers/writers -> consumers/readers: `isSprinting`, water slowdown, movement efficiency, speed, `onGround`, Dolphin's Grace, collision/climbable state, fluid gravity/fall state and jump threshold feed the same sequence; output delta movement is written after move and after fluid fall adjustment.
- Parent slices / dependencies / closure evidence: Direct `travelInWater()` and `jumpOutOfFluid()` arithmetic and ordering were compared in the paired LivingEntity bodies. Both apply the same sprint/water slowdown selection, water-movement-efficiency half-on-air adjustment and interpolation, Dolphin's Grace override, relative acceleration, move, horizontal-collision/climbable Y override, `(slowDown, 0.8F, slowDown)` scaling, fluid-fall adjustment, then jump-out check. Exact A/B `travelInWater()` helper and shared `travelInFluid()` call order match. D2 fluid membership/height/contact data, S6 movement-efficiency/effect producer paths and S4/S5 collision/fluid-state providers remain dependencies; they prevent terminal disposition.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The inspected water-travel body has no formula or order delta: speed/drag branches, constants (`0.9F`, `0.02F`, `0.54600006F`, `0.8F`, `0.96F`, `0.2`), branch predicates and delta writes match in order. This is a method-level comparison only; fluid-state, attribute/effect and collision-provider closures remain open, so the slice stays in-progress.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.6.1: Ridden-water floating type-tag gate

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: `travelInFluid()` calls `floatInWaterWhileRidden()` only on the water branch; helper checks `CAN_FLOAT_WHILE_RIDDEN`, `isVehicle()`, water depth against jump threshold, then adds `0.04F` vertical velocity.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java` :: `travelInFluid()` lines 2368-2377; `floatInWaterWhileRidden()` lines 2434-2438; SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`. `EntityType.is(TagKey)` lines 1556-1557; SHA-256 `fc2abb3e905b9a63a27f0b027fad089d9d40e5d3d45608406b5da2a5ec47a73e`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java` :: `travelInFluid()` lines 2455-2464; `floatInWaterWhileRidden()` lines 2521-2525; SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`. `Entity.typeHolder()` lines 366-368, SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`; `TypedInstance.is(TagKey)` lines 14-15, SHA-256 `caa4db2c73d7a18a59964ec2db7d3401a823da9f15a8e51aa266ba6f8a80e6df`; `Holder.Reference.is(TagKey)` lines 189-190, SHA-256 `c8b9b5941f0aa5a81d00a24bf7ba1529e9bb3d5f82fe36beef8f25f489c5c546`.
- State producers/writers -> consumers/readers: registry binds `EntityType` to its built-in holder; A checks the tag on that holder via `EntityType.is`, B checks the tag on the same holder via `TypedInstance.is`; both use the same remaining guards and add `(0.0, 0.04F, 0.0)`.
- Parent slices / dependencies / closure evidence: helper is reached from S3.6 water travel; the A/B type-holder/tag implementations reduce to the same built-in holder membership check. Resource tag values themselves remain within D2.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): Predicate spelling changed from `getType().is(tag)` to `this.is(tag)`, but both resolve to the same built-in type holder's tag membership. Movement guard order and impulse are unchanged.
- Finding IDs or checked absence/replacement path: none; tag contents remain an open data dependency, but predicate semantics are closed.

### Slice S3.7: Lava travel and fluid contact path

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Lava travel speed, move, low-fluid-height branch, fluid-fall adjustment, gravity quartering and jump-out ordering.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java` :: `travelInLava()` lines 2409-2425, `jumpOutOfFluid()` lines 2427-2432, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java` :: `travelInLava()` lines 2496-2512, `jumpOutOfFluid()` lines 2514-2519, SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`.
- State producers/writers -> consumers/readers: lava height, threshold, movement delta, gravity and collision/free-space probes feed the same guards; delta movement multipliers and final jump-out write follow the same order.
- Parent slices / dependencies / closure evidence: Direct `travelInLava()` arithmetic and ordering were compared in the paired LivingEntity bodies. Both apply relative acceleration `0.02F`, move, compare lava height `<= getFluidJumpThreshold()`, then either multiply `(0.5, 0.8F, 0.5)` and apply fluid-fall adjustment or scale by `0.5`; both add `-gravity / 4.0` only when gravity is nonzero and run the shared jump-out check last. D2 fluid-state/height inputs and S4/S5 contact/collision providers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): No formula or order delta appears in the inspected lava-travel body: both have the same `<=` threshold boundary, vector operation order, zero-gravity guard, quarter-gravity subtraction and jump-out call. This is not a terminal slice disposition while fluid and collision producers remain open.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.8: Gliding and movement-affecting elytra path

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Fall-flying travel, look/pitch/gravity lift calculation, velocity updates, collision distance comparison and collision-handler scope boundary.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java` :: `travelFallFlying()` lines 2441-2455, `updateFallFlyingMovement()` lines 2462-2485, `handleFallFlyingCollisions()` lines 2487-2496, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java` :: `travelFallFlying()` lines 2528-2542, `updateFallFlyingMovement()` lines 2549-2574, `handleFallFlyingCollisions()` lines 2576-2585, SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`.
- State producers/writers -> consumers/readers: fall-flying flag/pose, climbable state, look vector, pitch, horizontal speed, gravity and collision flags are read; update math writes velocity, then `move` runs. Collision handler's wall-damage call is a damage producer, excluded from this movement comparison; no direct velocity write appears in that handler.
- Parent slices / dependencies / closure evidence: Paired `travelFallFlying()`, `updateFallFlyingMovement()` and `handleFallFlyingCollisions()` bodies were compared. Both use the same climbable fallback, pre/post horizontal lengths, same gravity and pitch/looking-vector computation, and identical ordered lift, dive, pitch-down, horizontal-alignment and `(0.99F, 0.98F, 0.99F)` damping expressions before `move()`. Both collision handlers only calculate wall damage/play sound; damage resolution is excluded by INV-EXCLUSIONS. Fall-flying activation, pose/equipment/component state, and effective-gravity producers remain S1/S2/S6 dependencies.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The inspected glide travel and velocity formulas preserve branch order, casts, arithmetic grouping, comparison boundaries and damping constants. No direct player-motion difference is established in these bodies. The source path that activates fall flying and the equipment/pose and gravity producer closure remain open, so this is a bounded comparison only.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.9: Relative movement helpers, vector math and attributes

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: `Entity.moveRelative()` and its `getInputVector()` helper through `Vec3.lengthSqr()`, `normalize()`, `scale()`, `add()` and componentwise multiplication. The movement-speed/attribute producer chain remains open.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/Entity.java` :: `moveRelative()`/`getInputVector()`, lines 1608-1623, SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`; `ready/1.21.11/mojmap/net/minecraft/world/phys/Vec3.java` :: `normalize()` 80-83, `add()` 109-115, `scale()`/`multiply()` 149-163, `lengthSqr()` 181-183, SHA-256 `a4050765738a0cfdb1100b83f2ba4effd40155de99bb703a8381dbe775148ec1`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java` :: `moveRelative()`/`getInputVector()`, lines 1659-1674, SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`; `ready/26.1.2/unobfuscated/net/minecraft/world/phys/Vec3.java` :: `normalize()` 83-86, `add()` 112-118, `scale()`/`multiply()` 152-166, `lengthSqr()` 184-186, SHA-256 `57b08ae818868a4fffdc9b5dadba2b127138c8945c2194f6f4b01e6150cd3bcb`.
- State producers/writers -> consumers/readers: `moveRelative()` reads its input vector, speed and yaw, builds the rotated delta, then adds it to current delta movement through `setDeltaMovement`. The caller-selected speed depends on ground friction, movement attributes and mode-specific helpers; those producers and external input producers remain open.
- Parent slices / dependencies / closure evidence: caller order is covered in `S3.1`-`S3.8`; ground friction/flying-speed consumer evidence is recorded in `S3.5`. The forward attribute/effect/default and caller-selected speed audit remains open under `INV-MODIFIERS` and D2.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The helper bodies match in operation order: compute squared length, return `Vec3.ZERO` only below `1.0E-7`, normalize only when length squared exceeds `1.0`, scale by the supplied speed, compute sine then cosine from yaw radians, rotate X/Z with the same expression order, preserve Y, and add the resulting vector to current delta movement. Paired `Vec3` normalization threshold, length arithmetic, scale and component additions also match. This bounded math comparison has no difference; broader speed/attribute dependency closure is incomplete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.10: Travel post-updates, velocity reset/restitution and fall-state writes

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Travel post-updates, velocity reset/restitution and fall-state writes. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.1: Entity move entry, bounding-box and position updates

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Full `Entity.move(MoverType, Vec3)` body, including no-physics position update; piston limit; stuck-speed reset; edge-backoff then collision call order; movement threshold and position write; collision flags/ground update; horizontal velocity cancellation; block callback and movement emission ordering; final block speed factor multiplication. Nested movement/collision providers remain open.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/Entity.java` :: `move()` lines 685-781; SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java` :: `move()` lines 704-800; SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`.
- State producers/writers -> consumers/readers: reads delta, no-physics, mover type, stuck multiplier, fall distance, local authority and collision result; writes position, per-tick movement record, collision/ground flags, horizontal delta components and block-speed-adjusted movement. It dispatches `updateEntityMovementAfterFallOn` when Y is clipped, and movement emission callbacks after state updates. Damage/fall-resolution internals remain excluded; movement-state producers/consumers and collision result closure remain open.
- Parent slices / dependencies / closure evidence: `S3.2` owns friction/speed-factor values; `S4.2`-`S4.7` own collision, support and fluid result providers; `S5.1`-`S5.8` own block callback and data providers. All remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The inspected paired `move()` bodies preserve the same branch and write order, including `movementLength > 1.0E-7 || delta.lengthSqr() - movementLength < 1.0E-7`, fall-reset clip gate, set-position path, collision flags, ground-state dispatch, horizontal X/Z cancellation, fall-on callback, movement emissions, and final X/Z block-speed multiplication. Local names, profiler labels and line numbers differ. The overall slice remains open until collision result and callback/provider dependencies are traced.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.2: Axis resolution, collision candidates and tie-breaking

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `Entity.collide()`, `collectColliders()`, `collideWithShapes()`, `Shapes.collide()`, `VoxelShape.collide/collideX()`, `Direction.axisStepOrder()`, and AABB sweep/offset helpers. Axis iteration, collider iteration and per-shape clipping order checked; world/shape provider contents remain open.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/Entity.java` :: `collide()` 1044-1073, `collectColliders()` 1106-1120, `collideWithShapes()` 1122-1138; SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`. `ready/1.21.11/mojmap/net/minecraft/world/phys/shapes/Shapes.java` :: `collide()` 210-220, SHA-256 `fbe1aa9c92b4806128325ed82a5255aba5158de68ba62eb1afd53e7811b6b40b`; `VoxelShape.java` :: `collide()`/`collideX()` 247-306, SHA-256 `9655dca09dfb10e942ac88fb6e31a44f2611a5c61472136649269f248569f44d`; `AABB.java` :: `expandTowards()` 151-177, `move()` 217-229, SHA-256 `b8ace04fa09628266e3e12ec5423dbceb7ecef000041824c00510a5ddca6e9a1`; `ready/1.21.11/mojmap/net/minecraft/core/Direction.java` :: axis-order constants 48-49 and `axisStepOrder()` 372-374, SHA-256 `eaefe276ef6732d1f99c6d8268458714d1f84a37dd24654e238a710cc707b5be`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java` :: `collide()` 1077-1106, `collectColliders()` 1143-1159, `collideWithShapes()` 1161-1177; SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`. `ready/26.1.2/unobfuscated/net/minecraft/world/phys/shapes/Shapes.java` :: `collide()` 233-243, SHA-256 `cfa63cbed77c922a68e7e8a6a3b78b09a22c7de51e0146e481e9c9d26db66f58`; `VoxelShape.java` :: `collide()`/`collideX()` 252-311, SHA-256 `419a46bbcc8b48e1c075127954eb243dcc60d10d4087bf256d895cf91a2ff854`; `AABB.java` :: `expandTowards()` 151-177, `move()` 217-229, SHA-256 `22f23018fd588c2c4893d872f730381127e3d3ddd1a8c960901ecb8dae626392`; `ready/26.1.2/unobfuscated/net/minecraft/core/Direction.java` :: axis-order constants 48-49 and `axisStepOrder()` 378-380, SHA-256 `30fb61592f1a154bb8fc239ab8df1cd2b209f63317e0c7dde4d5b9b3e260d754`.
- State producers/writers -> consumers/readers: reads proposed movement, bounding box, current on-ground/max-step state and gathered entity/world-border/block shapes; writes per-axis clipped movement. `Direction.axisStepOrder()` selects YZX when `abs(x) < abs(z)`, otherwise YXZ; each axis clips against the moved box and the same shape list in sequence. Collider contents and collision-context/provider chains remain open.
- Parent slices / dependencies / closure evidence: `S4.1` establishes the reachable `move()` call path; `S4.3` covers the step-up alternative. D2/S5 provider and shape-data closure remains open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The inspected resolver order and arithmetic match: collect current entity shapes, then optional world border, then block shapes; clip on the same axis order; for each shape stop below the same `1.0E-7` distance threshold; apply the same positive/negative coordinate scan and min/max updates; and build the same swept/offset AABBs by sign-dependent expansion and coordinate addition. This is a bounded method comparison only; shape-list iteration/content and all provider dependencies remain unclosed.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.3: Step-up candidates, comparison and selection

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Step-up branch within `Entity.collide()` and `collectCandidateStepUpHeights()`, including the negative-Y/on-ground and horizontal-collision gates, candidate generation, unstable sort, horizontal-distance comparison and first winning candidate return.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/Entity.java` :: `collide()` 1044-1073 and `collectCandidateStepUpHeights()` 1075-1094; SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java` :: `collide()` 1077-1106 and `collectCandidateStepUpHeights()` 1108-1129; SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`.
- State producers/writers -> consumers/readers: reads horizontal/vertical clipped movement, bounding-box minY, maxUpStep and the collision shape list; creates candidate Y heights from each shape's ordered Y coordinates and returns the first resolved step whose horizontal distance squared exceeds the ordinary clipped movement. Shape coordinate/provider order and exact fastutil candidate-set dependency remain to be closed.
- Parent slices / dependencies / closure evidence: uses the collider list from `S4.2` and returns to `S4.1` position application. `FloatArraySet`/unstable-sort and world shape sources remain open; no candidate-order conclusion beyond the source body is claimed.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Both sources use the same branch guards, expanded AABB construction, optional `-1.0E-5F` downward extension, candidate filters (`>= 0`, not equal to clipped-Y, at most max step), FloatArraySet materialization and unstable sort; each candidate uses the same `horizontalDistanceSqr() > ordinaryMovement.horizontalDistanceSqr()` strict comparison and returns the first winner. The method bodies match; shape and iteration dependencies remain open.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.4: Edge probes, on-ground and support lookup

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `Entity.setOnGround()`, both `setOnGroundWithMovement()` overloads, `checkSupportingBlock()`, `onGround()`, and support-position selection in `CollisionGetter.findSupportingBlock()`; the concrete block collision iterator constructor/bounds/cursor/shape intersection path; and `getOnPos()` support-position consumers. Player edge-probe predicates remain detailed in S2.6.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/Entity.java` :: `setOnGround()`/`setOnGroundWithMovement()`/`checkSupportingBlock()`/`onGround()` lines 640-683, `getOnPosLegacy()`/`getBlockPosBelowThatAffectsMyMovement()`/`getOnPos()` lines 952-982; SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`. `CollisionGetter.findSupportingBlock()` lines 138-153; SHA-256 `ca2a5c4561fa8b263d7ce8513a152f9c1d6537f4f011859351ce58b39a7f4e70`. `BlockCollisions` lines 19-99; SHA-256 `05875bb3a6fa49628ac92174e68ec3b5bed1cf39ebd4e7158b1885aead0a6a68`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java` :: `setOnGround()`/`setOnGroundWithMovement()`/`checkSupportingBlock()`/`onGround()` lines 659-702, `getOnPosLegacy()`/`getBlockPosBelowThatAffectsMyMovement()`/`getOnPos()` lines 981-1012; SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`. `CollisionGetter.findSupportingBlock()` lines 138-153; SHA-256 `dddff4897e8d5d01e3ac132d85c474e6a3b9bae928e48b8c907aa9eabdb7fb01`. `BlockCollisions` lines 19-109; SHA-256 `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e`.
- State producers/writers -> consumers/readers: `move()` derives grounded state from vertical collision below, and the movement-aware setter also writes horizontal collision before support lookup; `checkSupportingBlock()` tests a 1e-6 downward AABB strip. It first queries with the current box, keeps the result (or preserves the prior support when on-ground had no block), else retries after translating the probe by negative movement X/Z when movement is nonnull; it then writes `onGroundNoBlocks`. A false on-ground input clears the flag and any saved support. `findSupportingBlock()` scans block collision results and chooses the block position closest by squared distance to entity position, breaking equal distances with `BlockPos.compareTo`. `getOnPos()` reads that support position, then applies the same fence/wall/gate floor-offset exception or falls back to floored entity coordinates.
- Parent slices / dependencies / closure evidence: `Entity.move()` sets grounded/collision results as recorded in S4.1 and S4.5. `findSupportingBlock()` instantiates `BlockCollisions` with `onlySuffocatingBlocks=false` and the identity-position result callback. Both iterators scan the same epsilon-expanded cursor bounds, skip cursor face type 3, gate the face types for large shapes/moving pistons, obtain collision shapes from entity collision context, fast-path full block shapes by box intersection, otherwise require nonempty shape and `Shapes.joinIsNotEmpty(..., AND)`. Chunk lookup, block state collision-shape implementations, dynamic/neighbor-aware providers and resource population remain open under S4/S5/D2.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The generic state transition and support selection order match: both query the current support strip, preserve/retry according to the same predicates, write/clear the same fields, use the same distance/tie selection, and use equivalent cursor bounds/face filters and collision intersection branches. Names and line positions vary. Pair source review has not closed which concrete block shapes are enumerated for every relevant state/provider or their resource inputs, so this is not a terminal no-difference disposition.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.5: Collision flags, velocity cancellation and callbacks

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `Entity.move()` post-collision comparison and collision flag writes, the client-player override of `isHorizontalCollisionMinor()`, horizontal velocity component cancellation, the vertical collision callback, and default `Block.updateEntityMovementAfterFallOn()` movement response. Full `move()` ordering is also recorded in S4.1.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/Entity.java` :: `move()` collision flags/callback/velocity lines 733-777 and default `isHorizontalCollisionMinor()` lines 915-917; SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`. `ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java` :: override `isHorizontalCollisionMinor()` lines 1064-1080; SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`. `ready/1.21.11/mojmap/net/minecraft/world/level/block/Block.java` :: `fallOn()`/`updateEntityMovementAfterFallOn()` lines 455-461; SHA-256 `bd7f69ffe617fa1008c9311ad7c731b23f242acb5ff3bab8c1a81e41e7d15a73`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java` :: `move()` collision flags/callback/velocity lines 752-796 and default `isHorizontalCollisionMinor()` lines 944-946; SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`. `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java` :: override `isHorizontalCollisionMinor()` lines 1103-1119; SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`. `ready/26.1.2/unobfuscated/net/minecraft/world/level/block/Block.java` :: `fallOn()`/`updateEntityMovementAfterFallOn()` lines 494-500; SHA-256 `1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977`.
- State producers/writers -> consumers/readers: After collision resolution, `Mth.equal(original, clipped)` derives X/Z collision booleans; horizontal collision is their OR. Vertical state updates only when `abs(originalY)>0.0` or local instance is authoritative; vertical collision is exact Y inequality and below means collision with negative clipped Y. Ground state receives below/horizontal flags plus the resolved movement. Minor collision is recomputed only when horizontal collision is true, otherwise cleared. On horizontal collision, only the clipped X and/or Z velocity components are zeroed. If movement simulation is allowed and Y clipped, the selected block callback runs; the base implementation multiplies Y velocity by zero. LocalPlayer computes desired world motion from `xxa/zza` and yaw, compares it to clipped X/Z motion, and returns false when either squared magnitude is strictly `<1.0E-5F`; otherwise it returns true only when `acos(dot/sqrt(product of squared magnitudes)) < 0.13962634F`. This result is consumed by sprint stop logic (S1.4).
- Parent slices / dependencies / closure evidence: The callback block and collision result are sourced through S4.1-S4.4; specialized block fall-on responses and movement surfaces remain in S5.3-S5.7, and component shape/provider resources remain under D2. The default callback and player collision-direction predicate match, but this does not disposition specialized block callbacks.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): For the direct player collision path, both versions use the same exact comparison/flag write order, strict float-cast tiny-vector tests, yaw transform and angular threshold, axis-wise velocity cancellation, and callback guard/order. The default block callback has the same Y-zeroing operation. Specialized callbacks on historical blocks and full collision-shape inputs have not been closed, so no terminal no-difference claim is made.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.6: Fluid state, contact, height and push/vector calculations

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Entity tick's fluid-state/eye update order; A `updateInWaterStateAndDoFluidPushing()`, `updateInWaterStateAndDoWaterCurrentPushing()` and `updateFluidHeightAndDoFluidPushing()`; B `updateFluidInteraction()`, `EntityFluidInteraction.update()`, per-tag trackers, and `applyCurrentTo()`; Player `isPushedByFluid()` applicability.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/Entity.java` :: tick call order lines 501-507, `updateInWaterStateAndDoFluidPushing()`/water helper lines 1501-1520, `updateFluidHeightAndDoFluidPushing()` lines 3509-3572; SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`. `Player.isPushedByFluid()` lines 1679-1681; SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java` :: tick call order lines 522-528, `updateFluidInteraction()` lines 1558-1583, `getFluidInteractionBox()` lines 4015-4025; SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`. `EntityFluidInteraction.update()`/loaded-chunk scan lines 30-101 and tracker `applyCurrentTo()` lines 171-193; SHA-256 `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62`. `Player.isPushedByFluid()` lines 1673-1675; SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`. `FluidState.getFlow()` lines 95-97 delegates to the same `FlowingFluid.getFlow()` algorithm on both sides (B FluidState SHA-256 `e35608d5775013c67653ea350309b5150a97f36aa65c27f1c6247ae2aa840f18`; B FlowingFluid SHA-256 `f8c0c4430e81efdf39b221968272ed8e3a4d030fa5192e04e04d34c9fbd37b28`, lines 55-95).
- State producers/writers -> consumers/readers: both player predicates allow current pushes exactly when `!abilities.flying`. A scans the deflated entity box, calculates max fluid height and average flow, skips current normalization for Player, enforces its minimum horizontal impulse, adds velocity and writes per-tag fluid height. B creates WATER/LAVA trackers, scans a fluid-interaction box after a loaded-section check, records height/eye state and accumulated flow; the player tracker averages by `currentCount`, scales and applies the minimum-impulse logic only after its accumulated-current cutoff, then calls `addDeltaMovement`. B's box may be modified by a vehicle hook, requiring caller/applicability closure for a mounted player.
- Parent slices / dependencies / closure evidence: This is the changed producer consumed by S2.3 and S3.6-S3.7. Paired A/B `FluidState.getFlow()` and `FlowingFluid.getFlow()` preserve neighbor iteration order, height-difference math (including `0.8888889F`), falling-fluid solid-face check, `-6.0` term and final normalization (A FluidState SHA-256 `146a5e6a19a93da631a6f4ed57020791bde31087fcedc6d52c68b1c582b71b0f`; A FlowingFluid SHA-256 `905e5a02ab10b23b5f6e64a0f732ee552ae8895801e1944d9850a41cc8bce91a`, lines 55-95). The source-confirmed F-1 witness closes the non-mounted Player WATER-current cutoff branch, including its exact queried fluid states, bbox, load gate, tag and math. Remaining S4.6 closure: lava counterpart and FAST_LAVA producer, broader fluid-height/eye consumers, mounted-player box caller, water/lava resource/source inventory and all cross-consumers. Non-player subclasses/physics are outside scope.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): The source architecture changed from direct nested state traversal and push to a tracker with a new box/loading/provider path. F-1 establishes one exact reachable WATER case: a non-flying, unmounted, standing Player at `(100.5, 64 + (1.0F / 9.0F) - 0.002, 100.5)`, zero horizontal velocity, one loaded chunk/section and flowing-water level 1 at `(100,64,100)`, with level 2 at `(101,64,100)` and a level-3-to-level-7/source chain eastward. The `0.6F × 1.8F` deflated box scans only X/Z cell 100 and Y cells 64-65. A evaluates `int blockY + float fluidHeight` as float before widening; B adds the float height to a double `fluidBottom`. Thus the old-side fluid top is about `3.39E-6` higher; A's overlap from the deflated box is about `0.00100339` and B's tracker height from entity Y is about `0.002`. The queried level-1 cell's `getFlow` is `(-1,0,0)`: east level 2 contributes the float difference `1/9F - 2/9F`, while the north/south/west solid walls block empty-neighbor below-fluid fallback. A scales by about `0.00100339 * 0.014 = 0.0000140475`, above Vec3's strict `1.0E-5F` normalization floor; with zero old X/Z it then writes the `0.0045000000000000005` minimum impulse. B scales to about `0.002`, whose squared length is about `4.0E-6 < 1.0E-5F`, and its strict early return skips all impulse logic. Level 1 is produced by the paired water flow path from a level-2 neighbor (drop-off 1); the level chain through level 7 to a source is representable and stable in a supported, side-walled channel. `hasFluidAndLoaded` passes when the query and its one-block X/Z border are in the loaded chunk and its section contains water; A's `touchingUnloadedChunk` passes when its inflated box is loaded. The exact flow source algorithms and this branch are established. Keep the broader S4.6 slice in-progress for the remaining closure listed above. Finding F-1 is source-evidenced and the immutable candidate snapshot is submitted for review; acceptance remains pending.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.7: Pose/dimension-dependent collision query repetition

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: The player's desired-pose fit probe, `CollisionGetter.noCollision()` branch order and block/entity/border queries, and `Entity.refreshDimensions()` pose-to-dimensions/eye-height write and size-change collision relocation guard. Player-only pose-fit and resize paths are considered; generic non-player relocation is outside scope.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/player/Player.java` :: `canPlayerFitWithinBlocksAndEntitiesWhen()` lines 382-384; SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`. `ready/1.21.11/mojmap/net/minecraft/world/level/CollisionGetter.java` :: `noCollision()`/block/entity/border methods lines 51-80; SHA-256 `ca2a5c4561fa8b263d7ce8513a152f9c1d6537f4f011859351ce58b39a7f4e70`. `ready/1.21.11/mojmap/net/minecraft/world/entity/Entity.java` :: `refreshDimensions()` lines 3226-3242; SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java` :: `canPlayerFitWithinBlocksAndEntitiesWhen()` lines 372-374; SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`. `ready/26.1.2/unobfuscated/net/minecraft/world/level/CollisionGetter.java` :: `noCollision()`/block/entity/border methods lines 51-80; SHA-256 `dddff4897e8d5d01e3ac132d85c474e6a3b9bae928e48b8c907aa9eabdb7fb01`. `ready/26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java` :: `refreshDimensions()` lines 3320-3336; SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`.
- State producers/writers -> consumers/readers: Player pose transitions ask whether target-pose dimensions at the current position fit, using a `1.0E-7` deflated AABB. `noCollision(entity, box)` short-circuits in the same order: block collisions with fluids excluded, entity collision, then world border. `refreshDimensions()` reads current pose, writes dimensions and eye height, reapplies position, and only attempts size-growth relocation when server-side, not first tick, physics-enabled, dimensions <=4 in each dimension, growth occurred, and the entity is not a Player; thus that relocation branch is unreachable for the player. Pose data and dimensions remain writers/readers from S2.1-S2.2.
- Parent slices / dependencies / closure evidence: S2.1/S2.2 cover pose selection and pose/dimension tables. Block collision enumeration is traced in S4.4 and still depends on S5 provider/resource closures; entity-collision enumeration and world-border shape results are external query inputs and remain open in the collision inventory.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Paired player fit tests use the same target pose dimensions, position, deflation and collision query. The same three collision classes are short-circuited in the same order, and the refresh write/guard ordering is unchanged; Player is excluded from the post-size-change relocation branch on both sides. Full pose-state producers, entity collision providers, border query results and block shape sources remain open, so no terminal no-difference disposition is claimed.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.1: Block/state movement defaults and registrations

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Block/state movement defaults and registrations. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.2: Friction/speed/jump factors and their consumers

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Friction/speed/jump factors and their consumers. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.3: Landing/bounce callbacks and support behavior

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Player-reachable `Block.updateEntityMovementAfterFallOn()` landing callback and grounded `Block.stepOn()` dispatch, including bed/slime bounce/suppress paths and slime's low-vertical-speed horizontal damping. Damage/fall-damage implementations in `fallOn()` and non-player outcomes are excluded.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/Entity.java` :: clipped-Y callback in `move()` lines 762-767 and on-ground `stepOn()` dispatch lines 860-864; SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`. `Entity.isSuppressingBounce()`/`isSteppingCarefully()` lines 2562-2572; same hash. `BedBlock` callback and bounce math lines 132-152; SHA-256 `51950145f90322b54f08ce620fdcddfe18c8b9b7ab88cc8491c05bd63c83837f`. `SlimeBlock` callback/bounce/step-on math lines 24-58; SHA-256 `86f4ced616a25bb8089f9ee2ff5a875f6e4f0ee0ab15b120863cd082cf35efd3`. `Blocks.java` registers colored beds at line 674 onward and `SLIME_BLOCK` at 3207; SHA-256 `cc6636f6ace603b32edd3db4c0cfda60be08e2c6b5def783a698f105b8f7f23`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java` :: clipped-Y callback in `move()` lines 781-786 and on-ground `stepOn()` dispatch lines 887-891; SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`. `Entity.isSuppressingBounce()`/`isSteppingCarefully()` lines 2626-2636; same hash. `BedBlock` callback and bounce math lines 132-152; SHA-256 `996e4ca63f4bfaea14b215648f2318d52c49f6c802317381a670d87c0a6d0b03`. `SlimeBlock` callback/bounce/step-on math lines 24-58; SHA-256 `84d22cf526d6bf1b4ec4b0b642a76fc2c90380a71f05ab92a7de9935f0d1c38e`. `Blocks.java` registers colored beds at line 674 onward and `SLIME_BLOCK` at 3221; SHA-256 `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f`.
- State producers/writers -> consumers/readers: After `move()` clips Y and simulation is allowed, a vertical delta difference dispatches the selected support block's `updateEntityMovementAfterFallOn()`. When the player is on ground, Entity resolves `getOnPosLegacy()` and dispatches `stepOn()`. The base landing callback zeros Y velocity. Beds and slime call the base response when shift suppresses bounce; otherwise they only alter negative Y: bed writes `-dy * 0.66F * (LivingEntity ? 1.0 : 0.8)`, slime writes `-dy * (LivingEntity ? 1.0 : 0.8)`, preserving X/Z. Slime's grounded hook reads `abs(deltaY)` and, only when `<0.1` and not stepping carefully, multiplies X/Z by `0.4 + abs(deltaY)*0.2`, preserving Y. Entity defaults both suppression predicates to shift-key state. Player inherits these predicates.
- Parent slices / dependencies / closure evidence: Support-position and callback dispatch callers are traced in S4.1/S4.4. Exact BedBlock and SlimeBlock response code, base fallback, and Java registration sites are paired. Remaining D2/block shape/state provider/resource closure still determines every reachable selected support block; a Java source census of fall/step hooks must be reconciled against registrations, and indirect block-state changes and excluded health/damage paths are not movement-response findings here.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Paired direct player velocity responses for the two bounce blocks preserve strict predicates, float/double arithmetic and write order. The same base zero-Y fallback, shift suppression, and slime X/Z damping apply. Other matching hook overrides include damage-only fall behavior or non-velocity block interactions, but complete registered provider/state census has not been reconciled with the broader D2/source inventories; no terminal disposition is claimed.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.4: Ice, soul sand, honey, web and other direct slowdown surfaces

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Ice, soul sand, honey, web and other direct slowdown surfaces. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.5: Climbable block registration, collision and contact behavior

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Climbable block registration, collision and contact behavior. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.6: Partial-block collision/support shapes and neighbor-dependent shapes

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Partial-block collision/support shapes and neighbor-dependent shapes. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.7: Pistons, bubble columns and other movement-producing block/fluid paths

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Pistons, bubble columns and other movement-producing block/fluid paths. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.8: Relevant block/fluid tags, data and resource defaults

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Relevant block/fluid tags, data and resource defaults. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.9: New liquid collision shape provider and player applicability

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: `LiquidBlock.getCollisionShape()` source-level and always-fluid-collision branches; entity collision context path and `LivingEntity.canStandOnFluid()` player applicability.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/level/block/LiquidBlock.java` :: `getCollisionShape()`, lines 76-82, SHA-256 `f0a0629d956ff2fb406fd524268f47596e506b06b2947452132a30631b35f802`; `LivingEntity.canStandOnFluid()`, lines 2295-2297, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`; A `EntityCollisionContext.canStandOnFluid()`, lines 47-49, SHA-256 `f5e66f9b4d3689993b402a51a3b00b91cee82befdae02ef28945b68109091ea1`; `CollisionContext.of(Entity)`, lines 24-30, SHA-256 `6f1ade5fa21f38d217a6c766ac34a4d2ddf54798320486ea33a7f676abbfdc640`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/level/block/LiquidBlock.java` :: `getCollisionShape()`, lines 81-95, SHA-256 `36740700683ec26c564d9566aa46277563abff6f038affa4ac1e370d807569f7`; `LivingEntity.canStandOnFluid()`, lines 2378-2380, SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`; B `EntityCollisionContext.canStandOnFluid()`, lines 61-65, SHA-256 `2f0a2bfd72a3d35121e3ef4ab80755f04cefaa7dd933988e2246b39ff5c39291`; `CollisionContext.of(Entity)`, lines 24-30, SHA-256 `8702d23bea0f119e0d63ce15ff59088559a319283186372fd69adebeed4dcd69`.
- State producers/writers -> consumers/readers: collision context created for a player carries `alwaysCollideWithFluid=false`; fluid shape checks `canStandOnFluid`, which delegates to player’s inherited `LivingEntity.canStandOnFluid()` returning false. `Player` has no override in either source tree. The new entity-provided liquid shape is reached only if stand-on-fluid predicate passes.
- Parent slices / dependencies / closure evidence: direct player applicability closed by the paired constructors, context predicates and inherited false return; non-player subclasses such as Strider are out of scope.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): B adds a per-entity liquid collision shape and changes the shape provider. For ordinary player contexts, both versions return empty liquid collision shape unless `alwaysCollideWithFluid` is true (which the player context constructor sets false); the `canStandOnFluid` gate is false in both. The B-only entity shape cannot be reached by player movement.
- Finding IDs or checked absence/replacement path: none; concrete B-only provider path is unreachable for Player under the inspected exact guards.

### Slice S6.1: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphins Grace and Blindness consumers

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphins Grace and Blindness consumers. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S6.2: Movement attributes: definitions, operations, aggregation and movement consumers

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Movement attributes: definitions, operations, aggregation and movement consumers. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S6.3: Depth Strider, Soul Speed, Swift Sneak and Riptide paths

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Depth Strider, Soul Speed, Swift Sneak and Riptide paths. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S6.4: Frost Walker boundary and movement-relevant server-supplied effects

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Frost Walker boundary and movement-relevant server-supplied effects. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S6.5: Equipment, elytra, use-item state and movement-relevant components/tags

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Equipment, elytra, use-item state and movement-relevant components/tags. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S6.6: Other movement-affecting effects/enchantments found by registration inventory

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Other movement-affecting effects/enchantments found by registration inventory. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S7.1: Incoming velocity/position corrections and local player packet consumers

- Inventory ID(s): INV-EXTERNAL, INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Incoming velocity/position corrections and local player packet consumers. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S7.2: Player knockback/push and explosion velocity writers

- Inventory ID(s): INV-EXTERNAL, INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Player knockback/push and explosion velocity writers. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S7.3: Piston displacement, launch items and external movement impulses

- Inventory ID(s): INV-EXTERNAL, INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Piston displacement, launch items and external movement impulses. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S7.4: Mount/dismount transitions and riding movement gates

- Inventory ID(s): INV-EXTERNAL, INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Mount/dismount transitions and riding movement gates. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S7.5: Closure sweep: all reachable player movement writers, changed dependencies and cross-mechanic interactions

- Inventory ID(s): INV-EXTERNAL, INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Closure sweep: all reachable player movement writers, changed dependencies and cross-mechanic interactions. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

## Dependency queue and blockers

- Open dependencies: D2
- D0 (closed): exact 1.21.11 Mojmap ready JSON, source/artifact manifests, source inventory, original/mapped jar and mapping hashes, diagnostics and tool versions; recomputed and matched in this report.
- D1 (closed): exact 26.1.2 native-unobfuscated ready JSON, source/artifact manifests, source inventory, original client jar hash, diagnostics and tool versions; recomputed and matched in this report; 26.2 not substituted.
- D2: movement resources/tags/data referenced by movement source; version-matched jar entries and hashes needed to close data-driven dependencies; retrieve/reconcile against verified pair artifacts; owner: source worker; open.
- D3 (closed in S3.3.1): exact player cutoff predicate and helper equivalence; A type getter plus EntityType identity and B holder value path both compare the same registered `EntityType.PLAYER` instance.
- D4 (closed in S5.9): liquid collision-shape candidate; exact paired context constructors/predicates and inherited player `canStandOnFluid()` show the new entity-specific shape cannot be reached by Player.

## Finding index

Finding F-1 records a source-confirmed shallow-overlap WATER-current cutoff difference with a concrete vanilla reachable fluid chain, exact player box/load gates and operation order. Its immutable candidate snapshot is submitted for blind review; it is not accepted yet. S4.6 remains in-progress for its broader lava, mounted-box, fluid-contact/eye and resource/dependency closures. S1.1-S1.3 have paired no-difference dispositions.

## Resume checkpoint

- Last completed slices: S1.1-S1.3, S1.2.1-S1.2.2, S3.1.1, S3.3.1 and S3.6.1 (compared-no-difference); S5.9 (not-applicable to Player).
- Next bounded slices: close `S1.4` dependencies (S2.5 item-use, S2.7 input consumer, swimming and vehicle predicates), then continue S1.5-S1.6 external ability/vehicle producers; finish `S2.1-S2.2` pose writer/refresh callbacks and dimensions, then continue `S2.3-S2.6` state/support providers; finish S4.6's broader lava, mounted-player, contact/eye and tag/resource dependencies and reconcile F-1 with S2.3/S3.6-S3.7; finish `S3.4` attribute/effect application and block-state source closure; continue `S3.5` movement-state providers; resolve `S3.8` gliding dependencies; close `S3.9` speed/attribute sources and `S3.2`-`S3.3` ground/air branches; continue `S4.1`-`S4.5` and S4.7 collision results. Reconcile all movement-referenced resources/tags under D2 before dispositioning dependent slices. Earlier resume wording implied verified S7 work, but review of the saved report found no S7 evidence: S7.1-S7.5 remain pending.
- Outstanding dependencies and owners: D2, source worker.
- Current assumptions requiring verification: decompiled movement member bodies are semantically intact; B LocalPlayer diagnostics omit anchors, so manual body review is cited; resource-backed data closure remains open.
- Resumable state: branch `feat/source-discovery-movement-source-1-21-11-26-1-2`; this report is the pair ledger. Exact A/B source roots and artifact manifests are recorded above. The report checker accepts the current active, non-complete state; no source finding snapshot exists. Preserve the source-only blind phase and do not inspect implementation/wiki material until the campaign explicitly transitions.

## Implementation reconciliation

- Reconciliation status: pending
- Repository revision inspected: none; source-only blind phase.
- Finding -> implementation disposition/evidence: deferred until blind-discovery freeze and explicit role transition.
- Existing implementation without a frozen source finding: not inspected.
- Coverage gaps routed back to discovery slices: pending source-only comparison.

## Independent source audit

- Reviewer: pending coordinator assignment; must not be a discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: pending independent reviewer.
- Concrete missed-slice routes (or none found): pending.
- Misses routed to slice/finding IDs and owners: pending.
- Reviewer evidence / date: pending.

## Finding snapshots (not pair freeze)

F-1 candidate snapshot submitted for blind review; acceptance is pending and does not freeze or complete the pair. Snapshot commit `6374ff904018e102afd8353864d3cbcbb1b99ddd`; finding path `1.21.11--26.1.2/findings/F-1-water-current-shallow-overlap.md`; Git blob `90c8a48282c036e6082c85f0be7a69e041ac0cb7`; finding-file SHA-256 `9d2c273a1c3b358eac7ebaa1e4cbe9e91a126648a90e89d8d6bdeb1953159cdd`. Exact pair provenance at this checkpoint: A 1.21.11 Mojmap source manifest `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`, artifacts manifest `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`, client jar `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`, mapping file `517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd`, mapped jar `7055b6a734a8f9f0d80229c2438ba12d10952a57c3795f32b8611020b34eb89a`; B 26.1.2 native unobfuscated source manifest `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`, artifacts manifest `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`, original client jar `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` (no mapping/remapped jar). The finding file records SHA-256 for every cited source member/file and the water-tag resource. Reviewer decision: pending coordinator assignment.

## Source audit closure

- Coverage counts by status: 22 pending; 23 in-progress; 10 compared-no-difference; 0 findings; 1 not-applicable; 0 blocked (56 slices). F-1 is an evidence-complete candidate within the still-open broader S4.6 slice; it does not change that slice's in-progress status or the pair coverage count.
- Required inventory status and evidence: all 7 pending; exact source inputs are verified, but full movement/provider/resource inventories remain incomplete.
- Open dependencies: D2
- Unresolved gaps and limits: inherited tick/travel/collision body comparisons, movement state producers/consumers, shape providers/registrations, and data/resource dependency closure.
- Evidence/hash/correspondence audit: source manifests and cited source SHA-256 identities verified; paired correspondence is partial and call-chain closure remains open.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).
