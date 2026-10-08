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
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Sprint transitions, timers and start/stop gates. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S1.5: Jump input, jump cooldown/state, auto-jump and riding gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Jump input, jump cooldown/state, auto-jump and riding gates. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S1.6: Flight toggle/input, abilities and flight-speed path

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Flight toggle/input, abilities and flight-speed path. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S1.7: Unstuck behavior and other input-to-tick movement gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Unstuck behavior and other input-to-tick movement gates. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.1: Player pose selection and pose transition timing

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Player pose selection and pose transition timing. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.2: Pose dimensions, eye height, resize collision queries

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Pose dimensions, eye height, resize collision queries. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.3: Swim/crawl state and movement-mode selection

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Swim/crawl state and movement-mode selection. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.4: Ability defaults, stored air speed and player movement state

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Ability defaults, stored air speed and player movement state. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.5: Item-use slowdown and direct active-item movement gate

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Item-use slowdown and direct active-item movement gate. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.6: Edge sneaking and support probing

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Edge sneaking and support probing. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.7: Sprint-gate consumers of hunger/blindness state

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Sprint-gate consumers of hunger/blindness state. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

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
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.getJumpPower()`, `getJumpBoostPower()`, `jumpFromGround()`, `jumpInLiquid()`, and the travel jump-selection branch, including the positive-power guard, Y maximum, sprinting yaw impulse, fluid-height selection, threshold comparisons, and `noJumpDelay` writes. Block-jump-factor and modifier providers remain open.
- A evidence: exact `ready/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java` :: `getJumpPower()` 2256-2262, `getJumpBoostPower()` 2264-2266, `jumpFromGround()` 2269-2281, `jumpInLiquid()` 2287-2289, jump selection 2934-2957; SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`.
- B evidence: exact `ready/26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java` :: `getJumpPower()` 2339-2345, `getJumpBoostPower()` 2347-2349, `jumpFromGround()` 2352-2364, `jumpInLiquid()` 2370-2372, jump selection 3033-3056; SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`.
- State producers/writers -> consumers/readers: reads jump-strength attribute, block jump factor, Jump Boost effect/amplifier, current delta movement, sprint state/yaw, fluid membership/height and ground state; writes delta movement, `needsSync`, and `noJumpDelay`. Block-factor world providers and modifier/effect application closure remain open.
- Parent slices / dependencies / closure evidence: exact `Entity.getBlockJumpFactor()` bodies match (A `Entity.java` 984-988, SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`; B 1015-1019, SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`) and read the same current-position then movement-below block jump factors, returning current unless it equals `1.0`. Exact `Entity.getFluidJumpThreshold()` bodies also match (A 3589-3591; B 3614-3616) as `getEyeHeight() < 0.4 ? 0.0 : 0.4`. Client-jar `data/minecraft/tags/fluid/water.json` and `lava.json` hashes match between A and B (`dcfa69a748d03dbf788d8f7b0e5eb6c8de6355a527fcaf74b9210fe4be2a3004`; `71f50fb9092d78260bc7434731fc5fd426a44e5284a6ad084ec71cb725630c6b`). Modifier/effect application and the complete block provider/data closure remain open; this is still a partial checkpoint.
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
- Parent slices / dependencies / closure evidence: D2 fluid/tag/resource values remain open; S6 modifier/effect/attribute sources and S4/S5 fluid/collision providers remain dependencies.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
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
- Parent slices / dependencies / closure evidence: D2 fluid-state/resource values and S4/S5 contact/collision providers remain open; direct formula comparison is documented here.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.8: Gliding and movement-affecting elytra path

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Fall-flying travel, look/pitch/gravity lift calculation, velocity updates, collision distance comparison and collision-handler scope boundary.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java` :: `travelFallFlying()` lines 2441-2455, `updateFallFlyingMovement()` lines 2462-2485, `handleFallFlyingCollisions()` lines 2487-2496, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java` :: `travelFallFlying()` lines 2528-2542, `updateFallFlyingMovement()` lines 2549-2574, `handleFallFlyingCollisions()` lines 2576-2585, SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`.
- State producers/writers -> consumers/readers: fall-flying flag/pose, climbable state, look vector, pitch, horizontal speed, gravity and collision flags are read; update math writes velocity, then `move` runs. Collision handler's wall-damage call is a damage producer, excluded from this movement comparison; no direct velocity write appears in that handler.
- Parent slices / dependencies / closure evidence: elytra/fall-flying activation and equipment state remain S1/S2/S6 dependencies; damage resolution is explicitly excluded by INV-EXCLUSIONS.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.9: Relative movement helpers, vector math and attributes

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Relative movement helpers, vector math and attributes. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
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
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Entity move entry, bounding-box and position updates. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.2: Axis resolution, collision candidates and tie-breaking

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Axis resolution, collision candidates and tie-breaking. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.3: Step-up candidates, comparison and selection

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Step-up candidates, comparison and selection. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.4: Edge probes, on-ground and support lookup

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Edge probes, on-ground and support lookup. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.5: Collision flags, velocity cancellation and callbacks

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Collision flags, velocity cancellation and callbacks. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.6: Fluid state, contact, height and push/vector calculations

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Fluid state, contact, height and push/vector calculations. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.7: Pose/dimension-dependent collision query repetition

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Pose/dimension-dependent collision query repetition. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
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
- Exact behavior boundary and enclosing guards/order checked: Not yet compared as a complete slice. Planned boundary: Landing/bounce callbacks and support behavior. Exact paired sources are ready; member ranges, guards/order and producer/consumer closure remain open.
- A evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; A source/artifact readiness provenance is recorded in the Artifact Manifest.
- B evidence: exact member/body ranges and cited source file SHA-256 pending this slice audit; B source/artifact readiness provenance is recorded in the Artifact Manifest.
- State producers/writers -> consumers/readers: source writer/consumer inventory remains open.
- Parent slices / dependencies / closure evidence: source dependencies not yet audited; expand through paired call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
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

No findings yet. S1.1-S1.3 have paired no-difference dispositions. The pair remains open and no finding snapshot has been submitted.

## Resume checkpoint

- Last completed slices: S1.1-S1.3, S1.2.1-S1.2.2, S3.1.1, S3.3.1 and S3.6.1 (compared-no-difference); S5.9 (not-applicable to Player).
- Next bounded slices: close the remaining `S3.4` jump-factor/modifier providers and `S3.5` movement-state providers; continue `S3.6`-`S3.8` fluid/gliding and then ground/air travel, collision and post-travel closure. Reconcile all movement-referenced resources/tags under D2 before dispositioning dependent slices.
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

No source-confirmed findings have been submitted. Pair run remains active; no snapshot event exists.

## Source audit closure

- Coverage counts by status: 42 pending; 5 in-progress; 8 compared-no-difference; 0 findings; 1 not-applicable; 0 blocked (56 slices).
- Required inventory status and evidence: all 7 pending; exact source inputs are verified, but full movement/provider/resource inventories remain incomplete.
- Open dependencies: D2
- Unresolved gaps and limits: inherited tick/travel/collision body comparisons, movement state producers/consumers, shape providers/registrations, and data/resource dependency closure.
- Evidence/hash/correspondence audit: source manifests and cited source SHA-256 identities verified; paired correspondence is partial and call-chain closure remains open.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).
