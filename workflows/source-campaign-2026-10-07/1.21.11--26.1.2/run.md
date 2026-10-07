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

A's successful log has access warnings for GUI classes and duplicate lambda processing; a targeted movement-class warning/error search found none. A diagnostics provide nine method anchors for Entity, LivingEntity, Player and LocalPlayer. B diagnostics provide seven anchors for Entity, LivingEntity and Player but omit LocalPlayer; the source exists, and LocalPlayer.tick/aiStep bodies are being manually checked before their slices can close. Namespace alignment is official names by A Mojmap and B native-unobfuscated.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending source comparison.
- Evidence inventory and finding IDs included at freeze: none; comparison has not started.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; none were opened. No wiki or MCPK browsing.
- Source/mapping hashes covered by freeze: pending exact source publication and comparison.

## Correspondence and call order

Paired source members have been identified from exact sources. This is a partial call trace; the complete tick/state/provider/resource graph remains open.

| Logical role | A source member | B source member | Paired source hashes | Comparison / call evidence |
|---|---|---|---|---|
| Local keyboard input | A `KeyboardInput.tick()`, lines 23-36 | B `KeyboardInput.tick()`, lines 23-36 | A `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3`; B `b4bbb410650444c30d2a62d70fd3c6cd1aefa104b8a12e474e2a478a62089c39` | Same key read order, impulse branch and normalized `Vec2` construction; only local names differ. |
| Input record/controller | A `Input` declaration, line 6; `ClientInput.tick()`, line 10 | B same class/member anchors | A Input `1c0c12af07da1f90651c3a7c4eb19bab6486b6391dcbfdd2926f06e3f36c2f06`; B Input `ee9ce4cfd647e09c55a9562f917fa504c0fba081bcfbbeb15871cf70818bc447`; ClientInput both `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` | Same dispatch and bitfield schema. A `Input.EMPTY` is mutable while B declares it final; reassignment reachability remains open. |
| Local-player tick/pre-travel input | A `LocalPlayer.tick()` 211-235; `aiStep()` 728-890; `applyInput()` 653-666; `modifyInput()` 668-684; square helpers 686-704 | B `LocalPlayer.tick()` 227-251; `aiStep()` 767-929; `applyInput()` 692-705; `modifyInput()` 707-723; square helpers 725-743 | A LocalPlayer `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`; B `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` | Bodies checked in order: local tick dispatches to `aiStep`; input sampling and application/scaling math match, including expression order. |
| Player shared tick/aiStep/travel | A `Player.tick()` 238-280; `aiStep()` 452-493; `travel(Vec3)` 1360-1383 | B `Player.tick()` 231-274; `aiStep()` 442-483; `travel(Vec3)` 1381-1404 | A Player `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`; B `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` | Inspected movement ordering and swim/flight travel adjustments match. Orb predicate syntax differs; its entity-touch consumer is being resolved separately. |
| Living movement integration | A `LivingEntity.tick()` 2610-2685; `aiStep()` 2877-3000; `travel(Vec3)` 2309 onward | B `LivingEntity.tick()` 2699-2774; `aiStep()` 2976-3100; `travel(Vec3)` 2392 onward | A LivingEntity `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`; B `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` | Dynamic `tick()` -> `aiStep()` dispatch confirmed; player velocity cutoff, input/jump/travel path and inspected travel formulas match. Type predicate and fluid-shape dependencies remain open. |
| Entity movement/collision | A `Entity.move`, `maybeBackOffFromEdge`, `collide`, `moveRelative`, `refreshDimensions` | B corresponding `Entity` members identified in paired source | A Entity `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`; B `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` | Anchors identified; bounded body comparison remains open. |

The A-side diagnostics additionally anchor Entity.moveRelative at 1608, LivingEntity.jumpFromGround at 2269, LivingEntity.travel at 2309, LivingEntity.aiStep at 2877, Player.aiStep at 452, Player.travel at 1360, LocalPlayer.aiStep at 728, and LocalPlayer.move at 947. Need body ranges and direct callers/field edges before comparison. No B correspondence has been guessed. The per-tick chain, pose/state writers, shapes, registrations and dependencies remain to be traced on both sides.## Required source inventories

Each inventory maps to the bounded slices below. Exact A/B pair sources are ready and verified; inventory completion remains pending while paired slices, dependencies and resource-backed inputs are audited.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1.1-S1.7,S2.5,S2.7,S3.1-S3.10,S7.4-S7.5; evidence=pending pair sources and member-level ranges.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S1.4-S1.6,S2.1-S2.7,S3.3-S3.10,S4.4-S4.7; evidence=pending pair sources and writer-consumer links.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S2.2,S2.6,S4.1-S4.7,S5.3-S5.6; evidence=pending pair sources and provider inventories.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S3.2,S3.5-S3.7,S4.6,S5.1-S5.8; evidence=pending pair sources, registrations and resource provenance.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S2.5,S3.2,S3.4,S3.6,S3.8-S3.9,S6.1-S6.6; evidence=pending pair sources and resource/data dependencies.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=S7.1-S7.4; evidence=pending exact source and provenance.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=source-stage audit pending; direct vanilla-state reads in movement predicates remain in scope as consumers only.

## Coverage ledger

These are planned behavior boundaries only. They have no source conclusions. Once exact sources are ready, split broad plans into bounded member/body slices, record exact original line ranges and hashes, and expand for every reachable caller, state writer, shape override, registration, resource and dependency. Keep terminal dispositions closed over producers and consumers.

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

### Slice S1.2: Client player tick ordering, superclass tick and travel dispatch

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer tick guard, superclass tick call, movement packet dispatch and ordered client tick path.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java` :: `tick()`, lines 211-235, SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java` :: `tick()`, lines 227-251, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`.
- State producers/writers -> consumers/readers: tick dispatch invokes `super.tick()` and then sends input/position updates under the same guards; subsequent input/travel dispatch is traced in S1.3 and S3.
- Parent slices / dependencies / closure evidence: input producer S1.1; superclass player/living tick continues through S3.1 and post-travel ordering remains open there.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): Inspected body has same guard/order and operations; only variable names differ. This closes only `LocalPlayer.tick()`, not the full inherited tick graph.
- Finding IDs or checked absence/replacement path: none.

### Slice S1.3: Yaw-to-input conversion, diagonal normalization and input scaling

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer.applyInput()`, `modifyInput()`, square-movement normalization and distance-to-unit-square arithmetic.
- A evidence: `ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java` :: `applyInput()` 653-666, `modifyInput()` 668-684, square helpers 686-704, SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`.
- B evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java` :: `applyInput()` 692-705, `modifyInput()` 707-723, square helpers 725-743, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`.
- State producers/writers -> consumers/readers: `input` from S1.1 is assigned to `xxa`, `zza`, and `jumping`; scaled movement vector is consumed by shared travel path S3.
- Parent slices / dependencies / closure evidence: S1.1 input source; caller ordering under S1.2. Continued dependencies (effects/item use/sneak) are included in this body and modifier consumers remain under S6.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): Same zero-length checks, `.98F` scaling, item-use and sneak multipliers, length/direction normalization and `min(length * distance, 1)` order. Only variable names/formatting differ.
- Finding IDs or checked absence/replacement path: none.

### Slice S1.4: Sprint transitions, timers and start/stop gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Sprint transitions, timers and start/stop gates. Pair sources unavailable; exclude health/food simulation, retain only direct state consumers.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S1.5: Jump input, jump cooldown/state, auto-jump and riding gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Jump input, jump cooldown/state, auto-jump and riding gates. Pair sources unavailable; resolve producers and reset timing.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S1.6: Flight toggle/input, abilities and flight-speed path

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Flight toggle/input, abilities and flight-speed path. Pair sources unavailable; include only direct player movement path.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S1.7: Unstuck behavior and other input-to-tick movement gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Unstuck behavior and other input-to-tick movement gates. Pair sources unavailable; discover from complete entry call graph.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.1: Player pose selection and pose transition timing

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Player pose selection and pose transition timing. Pair sources unavailable; include all reachable writers and guards.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.2: Pose dimensions, eye height, resize collision queries

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Pose dimensions, eye height, resize collision queries. Pair sources unavailable; follow shape/query timing and state writes.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.3: Swim/crawl state and movement-mode selection

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Swim/crawl state and movement-mode selection. Pair sources unavailable; resolve triggers and downstream travel dispatch.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.4: Ability defaults, stored air speed and player movement state

- Inventory ID(s): INV-STATE, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Ability defaults, stored air speed and player movement state. Pair sources unavailable; follow initialization, updates and resets.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.5: Item-use slowdown and direct active-item movement gate

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Item-use slowdown and direct active-item movement gate. Pair sources unavailable; food/hunger simulation excluded.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.6: Edge sneaking and support probing

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Edge sneaking and support probing. Pair sources unavailable; follow collision and support dependencies.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S2.7: Sprint-gate consumers of hunger/blindness state

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Sprint-gate consumers of hunger/blindness state. Pair sources unavailable; disposition consumers only; health, hunger, food and effect simulation excluded.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.1: Travel dispatch and ground acceleration

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Travel dispatch and ground acceleration. Pair sources unavailable; preserve complete method order and dependencies.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.2: Ground friction, speed-factor consumption and post-travel drag

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Ground friction, speed-factor consumption and post-travel drag. Pair sources unavailable; resolve block/state providers and attribute inputs.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.3: Air acceleration, drag/gravity and velocity thresholds

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Air acceleration, drag/gravity and velocity thresholds. Pair sources unavailable; explicit cutoff and all velocity writers/consumers required.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.4: Jump power, sprint-jump impulse and jump state writes

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Jump power, sprint-jump impulse and jump state writes. Pair sources unavailable; exact arithmetic and conditions required.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.5: Climbing movement and clamps

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Climbing movement and clamps. Pair sources unavailable; resolve climbable blocks/shapes and movement state.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.6: Water travel, swimming, buoyancy/drag and fluid effects

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Water travel, swimming, buoyancy/drag and fluid effects. Pair sources unavailable; include fluid heights/flow and effect/attribute dependencies.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.7: Lava travel and fluid contact path

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Lava travel and fluid contact path. Pair sources unavailable; exclude damage effects except direct movement-state consumers.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.8: Gliding and movement-affecting elytra path

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Gliding and movement-affecting elytra path. Pair sources unavailable; disposition item/attribute data and direct player calls.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.9: Relative movement helpers, vector math and attributes

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Relative movement helpers, vector math and attributes. Pair sources unavailable; close changed helper and aggregation dependencies.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S3.10: Travel post-updates, velocity reset/restitution and fall-state writes

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Travel post-updates, velocity reset/restitution and fall-state writes. Pair sources unavailable; follow all reachable callers and state writers.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.1: Entity move entry, bounding-box and position updates

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Entity move entry, bounding-box and position updates. Pair sources unavailable; preserve axis order and collision-query timing.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.2: Axis resolution, collision candidates and tie-breaking

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Axis resolution, collision candidates and tie-breaking. Pair sources unavailable; include AABB/shape/world query dependencies.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.3: Step-up candidates, comparison and selection

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Step-up candidates, comparison and selection. Pair sources unavailable; compare all candidate paths and exact comparisons.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.4: Edge probes, on-ground and support lookup

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Edge probes, on-ground and support lookup. Pair sources unavailable; include support block and neighboring-state reads.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.5: Collision flags, velocity cancellation and callbacks

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Collision flags, velocity cancellation and callbacks. Pair sources unavailable; include callback ordering and implementations.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.6: Fluid state, contact, height and push/vector calculations

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Fluid state, contact, height and push/vector calculations. Pair sources unavailable; connect fluid path to local player movement.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S4.7: Pose/dimension-dependent collision query repetition

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Pose/dimension-dependent collision query repetition. Pair sources unavailable; close interaction with S2 pose slices.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.1: Block/state movement defaults and registrations

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Block/state movement defaults and registrations. Pair sources unavailable; enumerate registrations/properties; do not infer from names.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.2: Friction/speed/jump factors and their consumers

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Friction/speed/jump factors and their consumers. Pair sources unavailable; follow default values and all reachable overrides.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.3: Landing/bounce callbacks and support behavior

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Landing/bounce callbacks and support behavior. Pair sources unavailable; include slime/bed paths where present and applicable.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.4: Ice, soul sand, honey, web and other direct slowdown surfaces

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Ice, soul sand, honey, web and other direct slowdown surfaces. Pair sources unavailable; classify absent blocks as modern-only with checked registry evidence.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.5: Climbable block registration, collision and contact behavior

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Climbable block registration, collision and contact behavior. Pair sources unavailable; close S3 climbing dependencies.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.6: Partial-block collision/support shapes and neighbor-dependent shapes

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Partial-block collision/support shapes and neighbor-dependent shapes. Pair sources unavailable; enumerate overrides/providers; compare states only where block existed in A.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.7: Pistons, bubble columns and other movement-producing block/fluid paths

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Pistons, bubble columns and other movement-producing block/fluid paths. Pair sources unavailable; distinguish direct player displacement from entity simulation.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S5.8: Relevant block/fluid tags, data and resource defaults

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Relevant block/fluid tags, data and resource defaults. Pair sources unavailable; inspect original version-matched jars; absent external data remains blocked.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S6.1: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphins Grace and Blindness consumers

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphins Grace and Blindness consumers. Pair sources unavailable; source-list every consumer/formula; exclude health/damage/food simulation.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S6.2: Movement attributes: definitions, operations, aggregation and movement consumers

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Movement attributes: definitions, operations, aggregation and movement consumers. Pair sources unavailable; include defaults, modifier order, clamping and synchronized inputs.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S6.3: Depth Strider, Soul Speed, Swift Sneak and Riptide paths

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Depth Strider, Soul Speed, Swift Sneak and Riptide paths. Pair sources unavailable; trace registration to condition/data to application to consumer.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S6.4: Frost Walker boundary and movement-relevant server-supplied effects

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Frost Walker boundary and movement-relevant server-supplied effects. Pair sources unavailable; do not infer server-side world mutation.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S6.5: Equipment, elytra, use-item state and movement-relevant components/tags

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Equipment, elytra, use-item state and movement-relevant components/tags. Pair sources unavailable; exclude modern-only equipment behavior from old profiles.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S6.6: Other movement-affecting effects/enchantments found by registration inventory

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Other movement-affecting effects/enchantments found by registration inventory. Pair sources unavailable; discovery must extend beyond named examples.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S7.1: Incoming velocity/position corrections and local player packet consumers

- Inventory ID(s): INV-EXTERNAL, INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Incoming velocity/position corrections and local player packet consumers. Pair sources unavailable; separate client computation from external input.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S7.2: Player knockback/push and explosion velocity writers

- Inventory ID(s): INV-EXTERNAL, INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Player knockback/push and explosion velocity writers. Pair sources unavailable; inspect only player-facing path, not independent entity physics.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S7.3: Piston displacement, launch items and external movement impulses

- Inventory ID(s): INV-EXTERNAL, INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Piston displacement, launch items and external movement impulses. Pair sources unavailable; identify client vs server ownership.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S7.4: Mount/dismount transitions and riding movement gates

- Inventory ID(s): INV-EXTERNAL, INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Mount/dismount transitions and riding movement gates. Pair sources unavailable; follow player transition state only.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

### Slice S7.5: Closure sweep: all reachable player movement writers, changed dependencies and cross-mechanic interactions

- Inventory ID(s): INV-EXTERNAL, INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not inspected; pending exact source publication. Planned boundary: Closure sweep: all reachable player movement writers, changed dependencies and cross-mechanic interactions. Pair sources unavailable; audit all stages and update parents after dependency findings.
- A evidence: ready JSON, three manifest/diagnostic hashes and six navigation source hashes verified; exact slice body range remains pending paired comparison.
- B evidence: exact native-unobfuscated ready record and three manifest/diagnostic hashes verified; exact slice body range remains pending paired comparison.
- State producers/writers -> consumers/readers: pending source inventory.
- Parent slices / dependencies / closure evidence: source dependencies pending; expand after exact-source call graph and resource inventory.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; no equivalence or difference conclusion. Paired member ranges and producer/consumer closure are not yet complete.
- Finding IDs or checked absence/replacement path: none; no absence claim made.

## Dependency queue and blockers

- Open dependencies: D0, D1, D2
- D0: all stages; exact 1.21.11 Mojmap ready JSON, source/artifact manifests, original/mapped jar and mapping hashes, diagnostics and tool versions; required to start A-side comparison; owner: shared source-preparation owner; pending.
- D1: all stages; exact 26.1.2 native-unobfuscated ready JSON, source/artifact manifests, original client jar hash, diagnostics and tool versions; 26.2 is not an acceptable substitute; owner: shared source-preparation owner; pending.
- D2: movement resources/tags/data referenced by source; version-matched jar entries and hashes; required to close data-driven movement dependencies; retrieve from validated pair artifacts after D0/D1; pending.

## Finding index

No findings yet. No equivalence conclusions or checked absences. Candidate inventory will be expanded and dispositions recorded only after exact pair comparison.

## Resume checkpoint

- Last completed slice: none; paired analysis has begun, but no slice is terminal yet.
- Next bounded slice: establish exact A/B local input and client-tick correspondence at S1.1-S1.2, including body ranges, direct callers and state flow.
- Outstanding dependencies and owners: D0/D1 shared source owner; D2 source worker after artifact publication.
- Current assumptions requiring verification: all relevant decompiled method bodies are semantically intact; B LocalPlayer bodies require review because readiness diagnostics omitted them; resource-backed data closure remains open.

## Implementation reconciliation

- Reconciliation status: pending
- Repository revision inspected: none; source-only blind phase.
- Finding -> implementation disposition/evidence: deferred until blind-discovery freeze and explicit role transition.
- Existing implementation without a frozen source finding: not inspected.
- Coverage gaps routed back to discovery slices: pending source-only comparison.

## Independent source audit

- Reviewer: pending coordinator assignment; must not be a discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: none; source comparison has not started.
- Concrete missed-slice routes (or none found): pending.
- Misses routed to slice/finding IDs and owners: pending.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: 50 pending; 0 in-progress; 0 compared-no-difference; 0 findings; 0 not-applicable; 0 blocked.
- Required inventory status and evidence: all 7 pending; exact pair source publication and source inventory remain open.
- Open dependencies: D0, D1, D2.
- Unresolved gaps and limits: readiness, source/artifact hash verification, diagnostics/body quality, all member correspondences, all movement comparisons and data/resource dependency closure.
- Evidence/hash/correspondence audit: not started; no source evidence admitted.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).
