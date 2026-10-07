# Discovery: 1.18.2 to 1.19.2

- Status: active
- Scope: client player movement; older A = 1.18.2; newer B = 1.19.2. Source-only discovery track. Explicit exclusions: health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations, and non-player movement. Direct movement predicates may read vanilla state without emulating its producer system. Modern-only blocks/features do not acquire historical behavior.
- Repository revision and start date: base `002137b227676caea77f6832b9f4c8d0b6200bff`; started 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap (`mojmap`) for both exact releases; both readiness records identify the exact release and Mojmap namespace, so source names align directly.
- Source preparation owner / command / log / readiness marker: source-owner publication via canonical root `build/movement-campaign-2026-10-07/`; exact `mojmap.ready.json` markers beside each namespace tree and source-owner command/log verified in the two exact Mojmap readiness records; neither command was run by this worker. A/B logs: A success excerpt `ready/1.18.2/mojmap.success.log`; B full log under the recorded source-owner staging root.
- Toolchain/decompiler/remapper versions and options: version catalog pins Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping-IO 0.9.1, Gson 2.14.0, ASM 9.10.1. Decompiler task forks Java 25, default heap 4G, with generic signatures and ASCII string characters enabled, synthetic members removed, four-space indent, Java runtime excluded, and allowed prefixes `net/minecraft` and `com/mojang`. Actual JDK/Gradle and exact command metadata are recorded per side below.
- Discovery author(s): source-discovery worker for this pair.
- Independent reviewer (must differ from discovery authors): pending coordinator assignment.

## Artifact manifest

Artifact paths below are relative to repository root; generated artifacts remain in ignored shared build storage and are not tracked.

### A — 1.18.2

- Requested/resolved release: `1.18.2` / `1.18.2`; `versionMetadataId` exact match verified.
- Readiness marker: `build/movement-campaign-2026-10-07/ready/1.18.2/mojmap.ready.json`, status `ready`, SHA-256 `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946`.
- Source root: `build/movement-campaign-2026-10-07/ready/1.18.2/mojmap/`; 4,236 source files.
- Source manifest: `mojmap.sources.sha256`; SHA-256 `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`; verified against marker.
- Artifact manifest: `artifacts.sha256`; SHA-256 `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036`; verified against marker.
- Client jar: `artifacts/1.18.2/client.jar`; SHA-256 `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7`; publisher SHA-1 `2e9a3e3107cca00d6bc9c97bf7d149cae163ef21`.
- CLI mode / mapping: `mojmap`; official Mojang mappings, `artifacts/1.18.2/client_mappings.txt`, SHA-256 `a2aa6ee1030bfef79e9b2e08e79de1637fdd7ecb5bf8891cf2e9a4b186042543`; publisher SHA-1 `a661c6a55a0600bd391bdbbd6827654c05b2109c`.
- Remapped jar: `artifacts/1.18.2/client-mojmap.jar`; SHA-256 `60a2016dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba`.
- Movement diagnostics: `movement-diagnostics.txt`; SHA-256 `0bc8857ee048b0ede8e696f3b7b050006618b278936cf182b3464c6c99483ac8`; verified against marker. It reports exact decompile success and movement entry/body line anchors for `Entity`, `LivingEntity`, `Player`, `LocalPlayer`, jump, travel, `move` and `moveRelative`; no movement-body decompiler errors are reported.
- Success excerpt: `mojmap.success.log`; confirms requested 1.18.2, `Finished 1.18.2 using mojmap`, and `BUILD SUCCESSFUL` (full stdout not persisted by owner).
- Toolchain: Java 25.0.3+9-LTS; Gradle 9.7.1; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping-IO 0.9.1; ASM 9.10.1; Gson 2.14.0. Command used exact release in a batch with 1.15.2, 1.16.5 and 1.17.1: `decompileMinecraft --versions=1.15.2,1.16.5,1.17.1,1.18.2 --mappings=mojmap --decompiler-heap=4G --output-root=<shared staging root> --cache-directory=<shared artifacts root>`.
- Cited source paths/hashes and cited resource entries/hashes: individual evidence hashes are recorded in coverage slices and findings as they are cited. Resource inventory remains open. Required external data/provenance: pending resource audit.

### B — 1.19.2

- Requested/resolved release: `1.19.2` / `1.19.2`; `versionMetadataId` exact match verified.
- Readiness marker: `build/movement-campaign-2026-10-07/ready/1.19.2/mojmap.ready.json`, status `ready`, SHA-256 `90f5a351c60a1aab160b640567b716bbc563e62d92e7e555df55c4ce7952492c`.
- Source root: `build/movement-campaign-2026-10-07/ready/1.19.2/mojmap/`; 4,480 source files.
- Source manifest: `mojmap.sources.sha256`; SHA-256 `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`; verified against marker.
- Artifact manifest: `artifacts.sha256`; SHA-256 `d0197d78578241b8cdde1123a3dee64aa279341681a7edde9f867689f9bb3490`; verified against marker.
- Client jar: `artifacts/1.19.2/client.jar`; SHA-256 `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db`; publisher SHA-1 `055b30d860ead928cba3849ba920c88b6950b654`.
- CLI mode / mapping: `mojmap`; official Mojang mappings, `artifacts/1.19.2/client_mappings.txt`, SHA-256 `c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76`; publisher SHA-1 `8e8c9be5dc27802caba47053d4fdea328f7f89bd`.
- Remapped jar: `artifacts/1.19.2/client-mojmap.jar`; SHA-256 `a257c4c97ceac50fbc069dc6051dff5f0263716547ede05e85c44d675ade592f`.
- Movement diagnostics: `movement-diagnostics.txt`; SHA-256 `ce308a5f2902c3d7f7c5e330935d130e5ce56302d03b7fa687bf14493400e44e`; verified against marker. It reports exact decompile success and movement entry/body line anchors for `Entity`, `LivingEntity`, `Player`, `LocalPlayer`, jump, travel, `move` and `moveRelative`; no movement-body decompiler errors are reported.
- Full Gradle log: `build/movement-campaign-2026-10-07/staging/mojmap-1.19.2-to-1.19.4-45d0418942a144298b19fb4ac74ff06a/gradle.full.log`; confirms `Finished 1.19.2 using mojmap` and `BUILD SUCCESSFUL`. It contains remapper access-fix warnings for GUI `OptionInstance$ValueSet` classes; those are unrelated to the movement body anchors reviewed so far.
- Toolchain matches A: Java 25.0.3+9-LTS; Gradle 9.7.1; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping-IO 0.9.1; ASM 9.10.1; Gson 2.14.0. Command used exact release in a batch with 1.19.3 and 1.19.4: `decompileMinecraft --versions=1.19.2,1.19.3,1.19.4 --mappings=mojmap --decompiler-heap=4G --output-root=<shared staging root> --cache-directory=<shared artifacts root>`.
- Cited source paths/hashes and cited resource entries/hashes: individual evidence hashes are recorded in coverage slices and findings as they are cited. Resource inventory remains open. Required external data/provenance: pending resource audit.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending.
- Evidence inventory and finding IDs included at freeze: pending source comparison.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; neither implementation nor wiki outputs were inspected, and no wiki was browsed.
- Source/mapping hashes covered by freeze: pending exact source verification.

## Correspondence and call order

- Exact sources are verified. Member correspondence is direct for the checked movement methods: `KeyboardInput.tick(boolean)` becomes `tick(boolean,float)`; `Player.maybeBackOffFromEdge(Vec3,MoverType)` keeps its signature; `Entity.move(MoverType,Vec3)` keeps its signature and routes support lookup through `getOnPosLegacy()` in B. Remaining caller ordering and dependency closure are still being inventoried.
- Confirmed source differences are F-001 (sneaking input scale) and F-002 (ascending edge-restraint guard). Other movement-path no-difference claims are limited to the specific closed ranges in the ledger.

## Required source inventories

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=T01,T02,T03,T04,T05,T06,T07; evidence=F-001 links local sneaking input to Swift Sneak; remaining per-branch comparison is open.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=P01,P02,P03,P04; evidence=F-002 documents the changed edge predicate; full writer/reader graph remains open.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=C01,C02,C03,C04; evidence=F-002 and the legacy support lookup in `Entity.move`; shape/callback coverage remains open.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=W01,W02,W03,W04; evidence=pending exact sources and client-jar resource access.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=M01,M02,M03,M04; evidence=F-001 traces the new Swift Sneak level bonus; resources and other modifiers remain open.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=E01,E02,E03; evidence=exact source trees verified; method comparison is in progress.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=pending exact sources; direct vanilla-state reads, if any, will be recorded as movement inputs only.

## Coverage ledger

### Slice T01: input sampling and input-state capture

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: pending; bound to input sample, current/previous input fields, and consumers after exact source inventory.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: input sampling/writers -> local player movement predicates and travel input; exact fields pending.
- Parent slices / dependencies / closure evidence: source provenance `D-SOURCES`.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; no behavior claim.
- Finding IDs or checked absence/replacement path: none yet.

### Slice T02: local player tick and pre-travel call order

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: pending; local tick/super tick/pre-travel order.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: tick writers -> travel dispatch and movement state; exact edges pending.
- Parent slices / dependencies / closure evidence: source provenance `D-SOURCES`; T01.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; no behavior claim.
- Finding IDs or checked absence/replacement path: none yet.

### Slice T03: input scaling and yaw-to-motion conversion

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer.aiStep()` samples `isMovingSlowly()` before `KeyboardInput.tick`; the input method scales lateral and forward impulses only when that predicate is true. `isMovingSlowly()` is crouching OR visually crawling in both. The downstream relative movement conversion is not yet closed.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:601-604,642-657` and `.../client/player/KeyboardInput.java:20-34`; SHA-256 LocalPlayer `99C2D18BCD23243AFB8F95C5BAFB21FB0BE7EA04AACBB14FCF7BE7CED2C9C095`, KeyboardInput `281622F8481654035196A7BC1554D5251C1040518375E3AC6F6439E5EC894A75`.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java:690-693,731-746` and `.../client/player/KeyboardInput.java:19-32`; SHA-256 LocalPlayer `36AE4AABD609B457FFFB7A8B14ABB50DB9AC775857DDE1774C0C68A8CF50DEEF`, KeyboardInput `A8064906872955A3520398AB5B2A326552D424F41887D1294AA6A038E2623FF0`.
- State producers/writers -> consumers/readers: crouch/visual-crawl state -> `isMovingSlowly()` -> keyboard input impulses -> local player input consumers.
- Parent slices / dependencies / closure evidence: D-SOURCES; T01; movement consumers in T07.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): F-001. When local keyboard input is slow (crouching or visually crawling), A multiplies both impulses by `0.3F`; B computes `clamp(0.3F + SwiftSneakLevel * 0.15F, 0, 1)` and passes that factor to the same two input fields. `SWIFT_SNEAK` is registered for `EquipmentSlot.LEGS`, has max level 3, and the helper derives bonus as level times `0.15F`. At levels 1-3 this raises the scale to 0.45/0.60/0.75; without it B remains 0.3. Resource applicability/data/tag audit remains open.
- Finding IDs or checked absence/replacement path: F-001.

### Slice T04: sprint state, start/stop gates and timers

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: pending; start/stop gates, timer transitions and direct predicates.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: input/player predicates -> sprint state/timers -> acceleration and sprint-jump paths.
- Parent slices / dependencies / closure evidence: D-SOURCES; P03; M02; excluded-system boundary X01.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; no behavior claim. Food/health producer systems remain excluded.
- Finding IDs or checked absence/replacement path: none yet.

### Slice T05: jump input, cooldown, auto-jump and impulse dispatch

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: pending; jump input timing, eligibility, cooldown, auto-jump probes and impulse caller order.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: sampled jump/state/timers -> jump method -> velocity writers and travel.
- Parent slices / dependencies / closure evidence: D-SOURCES; P04; T07; C02.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; no behavior claim.
- Finding IDs or checked absence/replacement path: none yet.

### Slice T06: flight, riding and unstuck movement gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: pending; ability toggles, riding eligibility and unstuck/anti-collision branches.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: abilities/vehicle/player flags -> movement branch and velocity/position.
- Parent slices / dependencies / closure evidence: D-SOURCES; P03; E03; T07.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; no behavior claim.
- Finding IDs or checked absence/replacement path: none yet.

### Slice T07: travel dispatch, branch order and post-travel updates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: pending; caller chain around all movement branches and post-travel writers.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: pre-travel state/input -> travel branches -> position/velocity/flags/timers/callbacks.
- Parent slices / dependencies / closure evidence: D-SOURCES; T01-T06; P01-P04; C01-C04; W01-W04; M01-M04; E01-E03.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; a travel method alone will not close the full tick inventory.
- Finding IDs or checked absence/replacement path: none yet.

### Slice P01: pose, dimensions and eye-height writers

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending; constructors, pose transitions, resize/collision checks and eye-height calculations.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: pose/size/eye-height writers -> collision/fluid queries and movement dimensions.
- Parent slices / dependencies / closure evidence: D-SOURCES; C03.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; no behavior claim.
- Finding IDs or checked absence/replacement path: none yet.

### Slice P02: position, velocity and movement-flag state writers

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending; position/velocity/AABB/on-ground/collision/fluid/support writers, resets and transitions.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: movement and external writers -> later predicates, collision queries and travel.
- Parent slices / dependencies / closure evidence: D-SOURCES; C01-C04; E01-E03; T02/T07.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; no behavior claim.
- Finding IDs or checked absence/replacement path: none yet.

### Slice P03: direct movement predicates and vanilla-state input boundaries

- Inventory ID(s): INV-STATE, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: pending; sprint/flight/swim/climb/item-use and other movement predicates; producer systems excluded by campaign scope.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: vanilla state reads -> movement predicates; excluded systems are not emulated.
- Parent slices / dependencies / closure evidence: D-SOURCES; T04/T06; M02; X01.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact sources unavailable; predicate-only reads may be recorded without modeling health/food systems.
- Finding IDs or checked absence/replacement path: none yet.

### Slice P04: movement timers, abilities and stored speed state

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending; timer/speed/ability fields, defaults, updates and reset timing.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: field constructors/tick writers -> movement gates, acceleration and travel branches.
- Parent slices / dependencies / closure evidence: D-SOURCES; T04-T07; M01.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; no behavior claim.
- Finding IDs or checked absence/replacement path: none yet.

### Slice C01: entity movement axis order and collision velocity response

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: pending; movement entry and axis resolution order, clipped delta and velocity cancellation.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: requested delta/velocity/AABB -> collision solver -> position, velocity and flags.
- Parent slices / dependencies / closure evidence: D-SOURCES; P02; C03.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; no behavior claim.
- Finding IDs or checked absence/replacement path: none yet.

### Slice C02: stepping, edge restraint and support lookup

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: `Player.maybeBackOffFromEdge()` precondition and 0.05 X/Z backoff loops; `Entity.move()` call order and support lookup method; exact `getOnPos()` feet offset. Step callback dispatch changed but block movement-output audit remains open.
- A evidence: `.../1.18.2/mojmap/net/minecraft/world/entity/player/Player.java:1032-1081`; `.../world/entity/Entity.java:543-563,591-608,704-720`; SHA-256 Player `BF639C1962FF90D69E4569B2B18F6FCF57AC46EF80B19686F0FBC1687FCA744A`, Entity `2228FDACA5793171CBD94038306D571A6ADA78CA96F5734EFB4CADA5B744C10A`.
- B evidence: `.../1.19.2/mojmap/net/minecraft/world/entity/player/Player.java:1061-1114`; `.../world/entity/Entity.java:547-567,595-612,710-734`; SHA-256 Player `155C5FCFBA322D968F3180383E7D283DDEB5EDEE4E04314906310D4E3CE0CCC1`, Entity `759DE9CDED43BD882AFCF5B7023BCF804D92419ACB656B493F3B490C83EB18B6`.
- State producers/writers -> consumers/readers: pose/AABB/velocity/support shape -> step and edge decisions -> movement delta/flags.
- Parent slices / dependencies / closure evidence: D-SOURCES; P01/P02; C03/W02.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): F-002: A applies edge restraint to self/player moves while non-flying, crouch-ground-surface and above-ground, regardless of requested Y; B adds `requested.y <= 0.0`. The method preserves the same 0.05 decrement loops. `Entity.move()` invokes this before collision, so for an ascending player/self request under the other guards, B bypasses the horizontal edge-backoff loops that A applies. B's `getOnPosLegacy()` retains A's `position.y - 0.2F` support selection for `Entity.move`; B's new `getOnPos()` uses `1.0E-5F` for other consumers. Post-grounded `stepOn` dispatch also changes from skipping careful steps to always calling the block; movement effects/callback audit remains open and is not classified here.
- Finding IDs or checked absence/replacement path: F-002; legacy feet lookup is a checked compatibility replacement for this `Entity.move()` consumer, not a finding.

### Slice C03: collision query path, shape contexts and shape providers

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending; every reachable player collision query and all invoked shape provider implementations/context inputs.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: world/state/context/neighbors -> shape providers -> collision/query result.
- Parent slices / dependencies / closure evidence: D-SOURCES; P01/P02; W01/W02.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; absence must be supported by registrations/inheritance, not string search.
- Finding IDs or checked absence/replacement path: none yet.

### Slice C04: collision callbacks and fluid-contact query timing

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: `Entity.move()` calls fall response then `updateEntityAfterFallOn()` after collision resolution, and invokes `stepOn()` when grounded. Compared the shared step callbacks on MagmaBlock, RedStoneOreBlock, SlimeBlock and TurtleEggBlock, plus B-only sculk callbacks; fluid-contact ordering remains open.
- A evidence: `.../1.18.2/mojmap/net/minecraft/world/entity/Entity.java:591-608`; block callback anchors: MagmaBlock.java:28-34, RedStoneOreBlock.java:39-42, SlimeBlock.java:45-53, TurtleEggBlock.java:46-49. SHA-256 Entity `2228FDACA5793171CBD94038306D571A6ADA78CA96F5734EFB4CADA5B744C10A`; Magma `7403EFB17BEE9EF2E1CCCB587E5CCE6B84F04F369A4C649E77263F164CD095CA`; RedStoneOre `489F0B98DAB62B5183FCBD2CF70E671DCDB2573F45D5E7488E13C31E6F98C8E5`; Slime `4E552C1D1AA49B115F1549A8F19415B0C9F81C0524C0BF4AFED37277D75F6388`; TurtleEgg `79C495789FF7183321456E357F1C34BBB678A627BB4A80D9109E8654ED1B662F`.
- B evidence: `.../1.19.2/mojmap/net/minecraft/world/entity/Entity.java:595-612`; block callback anchors: MagmaBlock.java:28-34, RedStoneOreBlock.java:39-45, SlimeBlock.java:45-53, TurtleEggBlock.java:47-53, SculkSensorBlock.java:131-141, SculkShriekerBlock.java:51-60. SHA-256 Entity `759DE9CDED43BD882AFCF5B7023BCF804D92419ACB656B493F3B490C83EB18B6`; Magma `CC1A02AC7EF23F61B4A5539CE3BD700BE06DDF0D8673DEE5B169413CC9236BBD`; RedStoneOre `CB408BACFFD402EAD0E04AD0A64F190141C73FB33E060BEAC8CCB0818669E511`; Slime `4E552C1D1AA49B115F1549A8F19415B0C9F81C0524C0BF4AFED37277D75F6388`; TurtleEgg `39E4E6E92B6234E33B3DBB7D9A85F1DFB26AB1A9341209E2BAFA8637B3FD4DD3`.
- State producers/writers -> consumers/readers: movement/query position and shape -> callbacks/fluid state -> velocity/state updates.
- Parent slices / dependencies / closure evidence: D-SOURCES; C01/C03; W03.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B calls shared `stepOn()` callbacks even for careful steps, whereas A skips them. On the shared blocks inspected, B adds careful-step guards to MagmaBlock, RedStoneOreBlock and TurtleEggBlock; SlimeBlock already uses that guard in both. The direct movement effects therefore remain suppressed while careful stepping. Magma/redstone/turtle-egg callbacks can cause excluded damage or world/game-event effects; no movement-output difference is claimed. B-only sculk callbacks are for modern-only blocks. Fluid contact and remaining callback families are open.
- Finding IDs or checked absence/replacement path: checked absence of a direct movement-output delta in the inspected shared step callbacks; remainder open.

### Slice W01: block movement properties, registrations and overrides

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending; friction/speed/jump properties, registry defaults and movement-relevant subclass overrides.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: block/state registration or override -> player travel/collision consumers.
- Parent slices / dependencies / closure evidence: D-SOURCES; C03; T07.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; modern-only additions remain modern-only.
- Finding IDs or checked absence/replacement path: none yet.

### Slice W02: historical block shapes and neighboring-state dependencies

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: pending; movement-relevant shapes, properties, context and neighboring-block dependencies, with registration evidence.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: block registration/state/neighbors -> shape provider -> player support/collision path.
- Parent slices / dependencies / closure evidence: D-SOURCES; C02/C03.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; no behavior claim.
- Finding IDs or checked absence/replacement path: none yet.

### Slice W03: fluid travel, flow vectors and contact

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending; water/lava travel, height/flow calculations, current pushes and contact timing.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: fluid registration/state/flow -> fluid travel and player velocity/contact state.
- Parent slices / dependencies / closure evidence: D-SOURCES; T07; C04.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; resource evidence still needs auditing; no behavior claim.
- Finding IDs or checked absence/replacement path: none yet.

### Slice W04: movement data, tags and resource-backed defaults

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: pending; relevant client-jar resource entries/tags/defaults referenced by movement consumers/providers.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: jar resources/tags/registry defaults -> movement-relevant consumers.
- Parent slices / dependencies / closure evidence: D-SOURCES; resource dependency `D-RESOURCES`.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact client jars and resource hashes unavailable; no Java-class absence will imply data absence.
- Finding IDs or checked absence/replacement path: none yet.

### Slice M01: movement attribute consumers, defaults and aggregation

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: pending; movement speed/jump/gravity/air-speed consumers, defaults, modifier order and aggregation.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: attribute registrations/synchronized values/equipment/effects -> attribute instances -> movement consumers.
- Parent slices / dependencies / closure evidence: D-SOURCES; T07; P04; M02/M03.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; synchronized/server-provided inputs will be labeled.
- Finding IDs or checked absence/replacement path: none yet.

### Slice M02: movement effects and application/removal conditions

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: pending; Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace, Blindness and other discovered movement effects.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: effect registrations/amplifiers/application/removal -> modifiers/predicates -> travel/sprint/jump consumers.
- Parent slices / dependencies / closure evidence: D-SOURCES; M01; P03/T04.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; excluded health/food systems remain outside scope.
- Finding IDs or checked absence/replacement path: none yet.

### Slice M03: movement enchantments, equipment and item applicability

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: checked Swift Sneak's registry presence/absence, equipment slot, max level, enchantment level lookup, and local crouch/crawl input consumer. Remaining enchantment/equipment consumers remain open.
- A evidence: `.../1.18.2/mojmap/net/minecraft/world/item/enchantment/Enchantments.java:1-42` contains no `SWIFT_SNEAK` registration; SHA-256 `BB945530CB616FFE8C23156EC0BDD5819094C92D4FB808BD9254EDFDB7953BF2`.
- B evidence: `.../1.19.2/mojmap/net/minecraft/world/item/enchantment/Enchantments.java:30`; `.../world/item/enchantment/SwiftSneakEnchantment.java:5-35`; `.../world/item/enchantment/EnchantmentHelper.java:171-194`; SHA-256 Enchantments `83A612CB3D234083E53358E98402B904F0F244DBE464F65C30B1658841148C93`, SwiftSneakEnchantment `6D68936DA23F57A2D9725155035B0A01D3B5017A899B5F9EC3C69F3E2256D64F`, EnchantmentHelper `0565D51D86FBD0854B4BF256C0E0EFF1CAAACE22B4C8F5F59B3A98E402F6319D`.
- State producers/writers -> consumers/readers: `SWIFT_SNEAK` equipment slot and item enchantment level -> `EnchantmentHelper.getSneakingSpeedBonus()` -> `LocalPlayer.aiStep()` factor -> `KeyboardInput.tick()` lateral/forward impulses.
- Parent slices / dependencies / closure evidence: D-SOURCES; M01/M04; W03; resource dependency D-RESOURCES.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): F-001 is source-confirmed and player-reachable when leggings provide Swift Sneak and local input is crouching or visually crawling. Enchantment availability/loot/resource data still needs client-jar resource audit; other listed modifiers remain open. Server-side world mutation is not inferred from client code.
- Finding IDs or checked absence/replacement path: F-001; remaining modifier families open.

### Slice M04: modifier data, tags and synchronized/external values

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: pending; data-driven modifier definitions, tags, attributes and server-synchronized input provenance.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: versioned data/registries/server input -> attribute/effect/enchantment application -> movement consumer.
- Parent slices / dependencies / closure evidence: D-SOURCES; M01-M03; D-RESOURCES.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; client resource evidence remains open; no equivalence claim.
- Finding IDs or checked absence/replacement path: none yet.

### Slice E01: correction packets and externally supplied position/velocity

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: pending; client packet consumers that write local player position/velocity or movement flags.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: server/network input -> local player state writers -> subsequent tick/travel consumers.
- Parent slices / dependencies / closure evidence: D-SOURCES; P02; T02/T07.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; externally supplied values will be identified as such.
- Finding IDs or checked absence/replacement path: none yet.

### Slice E02: player-facing pushes, pistons and launch movement

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: pending; player velocity/position writes from pushes, piston displacement, explosions only insofar as direct player movement input, and launch items.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: direct player-facing external/block events -> velocity/position -> movement tick.
- Parent slices / dependencies / closure evidence: D-SOURCES; P02; C04/W01.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; damage/combat simulation and non-player movement excluded.
- Finding IDs or checked absence/replacement path: none yet.

### Slice E03: mount, dismount and player-only vehicle integration

- Inventory ID(s): INV-EXTERNAL, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: pending; player state and movement inputs at mount/dismount transitions, without auditing another entity's independent physics.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: vehicle/player transition -> local player state/position/velocity -> tick movement gates.
- Parent slices / dependencies / closure evidence: D-SOURCES; T06; P02.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison pending; player-only integration retained in scope.
- Finding IDs or checked absence/replacement path: none yet.

### Slice X01: excluded systems and permitted vanilla-state reads

- Inventory ID(s): INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: pending; verify discovery does not model health, regeneration, hunger, food, saturation, exhaustion, damage/combat or non-player movement; identify direct vanilla state reads in movement predicates.
- A evidence: not yet compared; readiness is recorded in D-SOURCES.
- B evidence: not yet compared; readiness is recorded in D-SOURCES.
- State producers/writers -> consumers/readers: excluded producer systems are not traced as emulation targets; direct movement predicate reads are linked to their consumer only.
- Parent slices / dependencies / closure evidence: D-SOURCES; P03/T04; campaign exclusions.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): scope boundaries are recorded; the explicit source audit remains pending.
- Finding IDs or checked absence/replacement path: none yet.

## Dependency queue and blockers

- Resolved `D-SOURCES`: exact 1.18.2 and 1.19.2 Mojmap readiness records, source/artifact SHA-256 manifests, client jar identities, and movement diagnostics were verified before comparison. Exact metadata IDs and namespace match; readiness source/artifact/diagnostic hashes match; 17 initial paired source files match their source manifests.
- `D-RESOURCES`: originating W04/M03/M04 and newly discovered resource-backed dependencies; exact client-jar identities are verified, but resource entry names/hashes and any required external data remain uninspected. Owner: discovery worker; resource inspection is unblocked.
- No mod implementation or wiki output has been read; no source evidence or findings have been inferred from adjacent reports.

## Finding index

- F-001 — 1.19.2 Swift Sneak raises crouch/crawl keyboard input scale from A's fixed `0.3F`; evidence and reachability in T03/M03.
- F-002 — 1.19.2 only restrains crouch-ground X/Z edge movement when requested vertical delta is nonpositive; evidence and caller order in C02.

## Resume checkpoint

- Last source comparison work: T03 sneaking input scaling, C02 edge/support lookup, C04 shared step callback outputs, and M03 Swift Sneak applicability; these rows remain in-progress where their wider dependency boundaries are still open.
- Next bounded slice and exact files/members/body ranges to open: finish `LocalPlayer.aiStep()`/`Input` consumer path and the full `Player`/`LivingEntity` travel and jump branch inventories (T01/T02/T04-T07); then finish block callbacks/resources (C04/W04/M03).
- Outstanding dependencies and owners: D-RESOURCES (discovery worker); independent reviewer assignment (coordinator, after source-only freeze).
- Current assumptions requiring verification: Swift Sneak resource/tag data and all remaining modifier chains; all movement branch helpers and block callback outputs. Directory presence alone is not readiness.

## Implementation reconciliation

- Reconciliation status: pending — source-only role; do not open current/old mod implementation in this track unless the parent explicitly changes the role after blind freeze.
- Repository revision inspected: not inspected; discovery remains blind to implementation.
- Finding -> implementation disposition/evidence: deferred to a separate authorized reconciliation owner after freeze.
- Existing implementation without a frozen source finding: deferred to separate authorized owner.
- Coverage gaps routed back to discovery slices: none routed yet; source comparison has started and the open inventories remain assigned to this discovery worker.

## Independent source audit

- Reviewer: pending coordinator assignment (must not be the discovery author).
- Status: pending
- Inventories and call-chain ranges re-walked: none yet.
- Concrete missed-slice routes (or `none found`): pending reviewer work.
- Misses routed to slice/finding IDs and owners: pending reviewer work.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: pending 23; in-progress 4; compared-no-difference 0; findings 0; not-applicable 0; blocked 0.
- Required inventory status and evidence: INV-TICK pending; INV-STATE pending; INV-COLLISION pending; INV-WORLD-MOVEMENT pending; INV-MODIFIERS pending; INV-EXTERNAL pending; INV-EXCLUSIONS pending. No inventory is closed.
- Open dependencies: D-RESOURCES (discovery worker), independent reviewer assignment (coordinator, after source-only freeze).
- Unresolved gaps and limits: exact sources are ready and comparison has begun; method-level inventories, resources, and dispositions remain incomplete. Twenty-three coverage entries are pending and four are in progress.
- Evidence/hash/correspondence audit: two bounded difference slices include paired source hashes, ranges, call paths, and writer-to-consumer links; the remaining source hashes and correspondence are not yet inventoried.
- Blind freeze: pending.
- Implementation reconciliation: pending and deferred outside this source-only assignment.
- Independent audit: pending.
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).



