# Discovery: 1.10.2 to 1.11.2

- Status: partial
- Scope: source-only comparison of reachable client-player movement for exact A=1.10.2 and B=1.11.2. Endpoint differences do not establish a first changed release.
- Repository revision and start date: base `002137b227676caea77f6832b9f4c8d0b6200bff`; campaign date 2026-10-07; report branch `feat/source-discovery-movement-source-1-10-2-1-11-2`.
- Selected naming namespace, CLI mode per side and alignment evidence: `ornithe-feather` / `--mappings=feather` for both. Exact metadata IDs match requested IDs; both use release-specific Feather Gen2 build 2 mappings. Both source trees have 1,845 / 1,921 files and verified manifests. This establishes aligned naming convention, not member correspondence by itself.
- Source preparation owner / command / log / readiness marker: shared source owner published the trees; worker did not decompile or write shared source/cache. Both provenance files record `.\gradlew.bat decompileMinecraft --versions=1.10.2,1.11.2,1.12.2 --mappings=feather --decompiler-heap=4G --output-root=... --cache-directory=...` (batch includes 1.12.2). Successful logs are `build/movement-campaign-2026-10-07/ready/<version>/ornithe-feather.success.log`; markers are `.../ready/<version>/ornithe-feather.ready.json`. Requested/resolved IDs are exact.
- Toolchain/decompiler/remapper versions and options: Java 25.0.3+9-LTS; Gradle 9.7.1; Vineflower 1.12.0; ASM 9.10.1; mapping-io 0.9.1; Gson 2.14.0; Tiny Remapper 0.14.1; decompiler heap 4G.
- Discovery author(s): Codex source worker, 2026-10-07.
- Independent reviewer (must differ from discovery authors): not assigned / pending.

## Artifact manifest

All paths below are repository-relative; generated sources and artifacts remain outside this tracked report. Publisher/client jar hashes are separately recorded as supplied in the owner's SHA256 manifest. Worker verified the readiness marker, provenance, source and artifact manifest file hashes, every listed source-file hash (3,766 files total), every listed artifact hash (74 entries total), and source file counts. The published source trees and shared cache were read-only.

| Side | Release / source root | Client jar SHA-256 | Namespace / CLI mode | Mapping coordinate; mapping file SHA-256 | Remapped client jar SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Diagnostics SHA-256 |
|---|---|---|---|---|---|---|---|---|
| A | 1.10.2; `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather/` | `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9` | `ornithe-feather`; `feather` | `feather-gen2-1.10.2+build.2`; `yarn/feather-gen2-1.10.2+build.2.tiny`, `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1` | `client-ornithe-feather.jar`, `ab7aa536f52e94c4c099999bc731020c11a1b29e2979a566cedcc2ffb00e701d` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116` | `3a8622864d424ff3ddd4d55febaf453d4201c8f1e05638c758616cea5443ab9e` |
| B | 1.11.2; `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather/` | `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2` | `ornithe-feather`; `feather` | `feather-gen2-1.11.2+build.2`; `yarn/feather-gen2-1.11.2+build.2.tiny`, `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6` | `client-ornithe-feather.jar`, `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3` | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f` | `8bb3b1169a21958a6570fc304a0a95d9c51cc3a2101869bcbd453c3588925cdc` |

The hash of each full mapped jar is present as an artifact-manifest entry but is not needed for the cited Java-source evidence in this partial report; all mapped client jar hashes above were verified. Mapping jar hashes: A `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0`; B `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757`. Exact shared-source readiness and diagnostics were verified on resume; diagnostics show required movement classes and no reported relevant decompiler failure. This is not proof that every body in either tree is decompiled correctly.

Cited source hash inventory (SHA-256; relative roots are the source roots in the table):

- `net/minecraft/entity/Entity.java`: A `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`; B `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`.
- `net/minecraft/entity/living/LivingEntity.java`: A `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`; B `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`.
- `net/minecraft/entity/living/player/PlayerEntity.java`: A `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302`; B `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: A `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`; B `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`.
- `net/minecraft/client/entity/living/player/Input.java`: both `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e` (identical source content).
- `net/minecraft/client/entity/living/player/KeyboardInput.java`: both `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`.
- `net/minecraft/world/World.java`: A `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646`; B `27cfaa5ff45c2d88c492fc5daeea4a2bb4536fd64bff8498a9a7d0cb82efb58`.
- `net/minecraft/block/entity/MovingBlockEntity.java`: A `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1`; B `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e`.
- `net/minecraft/entity/EntityFilter.java` and `net/minecraft/util/math/Box.java` still need exact hashes before their slices can be closed.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending; this report is not frozen.
- Evidence inventory and finding IDs included at freeze: pending.
- Confirmation old mod implementation/code and wiki-audit results were not opened before freeze: confirmed. Prior source-discovery reports were used only as navigation leads. No wiki/MCPK was opened.
- Source/mapping hashes covered by freeze: pending.

## Correspondence and call order

Verified initial correspondence: `Input.tick` / `Input.getMovement` and `KeyboardInput.tick` are identical by file hash. `LocalClientPlayerEntity.mobTick` is the direct local movement entry: it samples input, updates local player pre-travel flags, then calls `LivingEntity.mobTick`; `LivingEntity.mobTick` performs the locally controlled `serverTickAi`, jump cooldown/input handling, `flyingTick`, `moveRelative`, and post-travel entity pushing. `PlayerEntity.serverTickAi` sets `headYaw = yaw` before local travel. `LivingEntity.moveRelative` resolves to `Entity.move`; A's `move(double,double,double)` corresponds to B's `move(MoverType,double,double,double)`. B's piston caller supplies `MoverType.PISTON`; ordinary LivingEntity travel supplies `MoverType.SELF`. This is only the inspected path prefix, not a complete tick graph.

Other correspondence requiring further walk: remote/client corrections and packet application; riding and dismount; abilities and item-use state; all travel branch helpers; fluids and environmental callbacks; world collision query/shape production; state writers, registries, attributes/effects/equipment, and resource data. `LocalClientPlayerEntity.getRotationVector(float)` exists only in B; local `serverTickAi` writes `headYaw=yaw` before the confirmed locally controlled fall-flying travel call, but other callers and complete call ordering remain open.

## Required source inventories

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1-input,S1-local-order,S3-travel,S3-post; evidence=`Input.java` lines 15-19 (both), `LocalClientPlayerEntity.mobTick` A 620-790 / B 632-802, `LivingEntity.mobTick` A 1681-1752 / B 1737-1808; only input method identity and partial ordering have been checked.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S2-resize,S2-state-writers,S3-jump,S4-collision-flags; evidence=`Entity.java` A 264-274 / B 271-287, and move bodies A 446-700 / B 459-724; only resize logic and part of collision are reviewed.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S4-sneak-probe,S4-move-order,S4-step,S4-callbacks,S5-shapes; evidence=`Entity.java` A 468-512 / B 519-562 for the sneak probe and `World.getCollisions` A line 903 / B lines 960-1013; full providers/registrations not inventoried.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S5-blocks,S5-fluids,S5-resources; no source/resource inventory yet. Relevant `MovingBlockEntity` A 91-183 / B 116-285 identified.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S6-attributes,S6-effects,S6-enchantments,S6-equipment; `LivingEntity` has `flyingTick` A 1754-1773 / B 1810-1830, with Elytra read at A 1757 / B 1813. Dependencies/data remain open.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=S7-piston,S7-pushability,S7-corrections,S7-mounts; direct piston caller is `MovingBlockEntity` B 116-160 and 210-242; A caller is 91-159. Corrections and mount graph remain open.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=complete for scope declaration only; evidence=campaign contract and the `LocalClientPlayerEntity`/`LivingEntity` tick boundary above. Movement predicates that read food/sprint state remain in scope; this report does not emulate food, health, exhaustion, or damage producers. Non-player movement is not researched. Revisit if call-chain review finds an excluded producer mixed into a movement operation.

## Coverage ledger

### Slice S1-input: keyboard sampling and vector accessor identity

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: full `Input.tick`, `Input.getMovement`, and `KeyboardInput.tick`; called from local player's `mobTick` after the movement-update gates and before inherited living travel.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather/net/minecraft/client/entity/living/player/Input.java::Input#tick/getMovement`, lines 15-19, SHA-256 `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e`; `KeyboardInput.java::KeyboardInput#tick`, lines 13-50, SHA-256 `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather/net/minecraft/client/entity/living/player/Input.java::Input#tick/getMovement`, lines 15-19, SHA-256 `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e`; `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java::KeyboardInput#tick`, lines 13-50, SHA-256 `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`; both are byte-identical to A.
- State producers/writers -> consumers/readers: key bindings -> forward/sideways/jump/sneak flags -> `LocalClientPlayerEntity.mobTick`, `LivingEntity.moveRelative`, and edge-probe predicate.
- Parent slices / dependencies / closure evidence: input producer only; consumer/caller graph is partial. No equivalence claim outside these input bodies.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): exact paired source files are byte-identical; method behavior is identical for these input sampling/accessor bodies.
- Finding IDs or checked absence/replacement path: no finding; local caller identified, remaining downstream calls stay in S1-local-order/S3-travel.

### Slice S1-local-order: local pre-travel state and inherited travel dispatch

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LocalClientPlayerEntity.mobTick` input/pre-travel segment and `LivingEntity.mobTick` through `moveRelative`; not the complete bodies.
- A evidence: `LocalClientPlayerEntity.java::mobTick`, lines 620-790, SHA-256 `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`; `LivingEntity.java::mobTick`, lines 1681-1752, SHA-256 `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`.
- B evidence: corresponding `LocalClientPlayerEntity.java::mobTick`, lines 632-802, SHA-256 `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`; `LivingEntity.java::mobTick`, lines 1737-1808, SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`.
- State producers/writers -> consumers/readers: local input and sprint/flight/riding flags -> inherited jump gate, `flyingTick`, `moveRelative`, movement call; inherited serverTickAi writes headYaw before locally controlled travel.
- Parent slices / dependencies / closure evidence: S1-input; open S2/S3/S6/S7 dependencies and all callers.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): paired methods are broadly similar; audited hunks include one changed null-to-empty Elytra guard and a movement signature change. Complete operation-level comparison and helper dependencies are not closed.
- Finding IDs or checked absence/replacement path: candidate API/null guard is not a finding pending empty-stack and Elytra trace; movement signature is traced to S4/S7.

### Slice S3-jump-impulse: base living jump math

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: protected `LivingEntity.jump` impulse assignments, excluding `PlayerEntity.jump` exhaustion/fatigue changes.
- A evidence: `LivingEntity.java::jump`, lines 1305-1318, SHA-256 above.
- B evidence: `LivingEntity.java::jump`, lines 1361-1374, SHA-256 above.
- State producers/writers -> consumers/readers: jump gate/cooldown and jump effect -> velocityY, sprint horizontal velocity, dirty velocity flag -> `Entity.move`.
- Parent slices / dependencies / closure evidence: S1-local-order; Jump Boost/equipment and full caller predicates remain open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): inspected formulas and operation order match: base `0.42F`, Jump Boost `(amplifier+1)*0.1F`, sprint impulse based on yaw with `0.2F`; upstream and effect-data closure is not claimed.
- Finding IDs or checked absence/replacement path: no finding for this bounded impulse body; PlayerEntity's additional cost path is excluded and separately inventoried.

### Slice S4-sneak-probe: grounded-player horizontal edge restraint

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `Entity.move` after cobweb scaling, before the movement collision list; grounded + sneaking + player guard, x then z then combined x/z probes, 0.05 trimming loop.
- A evidence: `Entity.java::move(double,double,double)`, lines 446-512, SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`.
- B evidence: `Entity.java::move(MoverType,double,double,double)`, lines 459-562, SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`.
- State producers/writers -> consumers/readers: local sneak/ground flags and stepHeight -> probe box -> world block/entity collision query -> retained requested x/z -> position/collision flags.
- Parent slices / dependencies / closure evidence: S1-local-order; `stepHeight` defaults/writers and `World.getCollisions`/shape-producing block path remain open. See finding F-01.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): confirmed changed probe depth from fixed `-1.0` to `-this.stepHeight` for the same SELF/PLAYER eligible grounded sneaking player path. Consequence depends on support/collision shapes; no trajectory tested.
- Finding IDs or checked absence/replacement path: F-01.

### Slice S4-piston-cap: external piston displacement

- Inventory ID(s): INV-EXTERNAL, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `Entity.move` piston-specific per-world-time axis accumulation and cutoff before ordinary collision resolution; piston caller's entity displacement. A `Entity.move` has no mover type or cap; A moving block caller directly passes displacement.
- A evidence: `Entity.java::move(double,double,double)`, lines 446-514, SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`; `MovingBlockEntity.java::moveEntities`, lines 91-159, SHA-256 `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1`.
- B evidence: `Entity.java::move(MoverType,double,double,double)`, lines 459-500, SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`; `MovingBlockEntity.java::moveEntities(float)`, lines 116-160 and 210-242, SHA-256 `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e`.
- State producers/writers -> consumers/readers: moving piston progress -> MoverType.PISTON displacement -> per-entity per-axis accumulated movement keyed by world time -> box/collision/position/flags. B clamps cumulative same-axis movement to +/-0.51, returns for incremental movement <=1.0E-5F.
- Parent slices / dependencies / closure evidence: S4-sneak-probe; exact tick order/progress and piston collision shapes are open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): confirmed B-only cap on a reachable piston displacement path. Repeated same-axis piston movement during one world time is capped; exact player consequence depends on piston geometry/progress and other callers. No runtime claim.
- Finding IDs or checked absence/replacement path: F-02.

### Slice S4-piston-edge-bypass: mover-type guard on sneak restraint

- Inventory ID(s): INV-EXTERNAL, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: eligibility guard before the grounded-player sneak edge probes in `Entity.move`; compare A's untyped method with B's new `MoverType` predicate and B's piston caller.
- A evidence: `Entity.java::move(double,double,double)`, lines 468-472, source hash as S4-sneak-probe. The guard is `onGround && isSneaking() && this instanceof PlayerEntity`; every call enters the same restraint.
- B evidence: `Entity.java::move(MoverType,double,double,double)`, lines 519-523, source hash as S4-sneak-probe. The guard additionally requires mover type SELF or PLAYER. `MovingBlockEntity.java::moveEntities(float)`, lines 159 and 241, supplies PISTON.
- State producers/writers -> consumers/readers: sneak/ground state + displacement producer/mover type -> edge-probe selection -> player position.
- Parent slices / dependencies / closure evidence: S4-sneak-probe and S4-piston-cap; exact piston/collision progress remains open under D-PISTON.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B skips the sneak-edge restraint for a grounded sneaking player receiving piston movement because its caller passes PISTON. A has no mover category and applies the restraint to the same player state. Piston pushing is an external movement route; actual positional outcome depends on geometry and remains a source-predicted consequence.
- Finding IDs or checked absence/replacement path: F-03.

### Slice S7-pushability: living player collision eligibility

- Inventory ID(s): INV-EXTERNAL, INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.isPushable` and entity-filter predicate consuming it; no non-player movement consequence is asserted.
- A evidence: `LivingEntity.java::isPushable`, lines 1887-1889, SHA-256 `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`; `EntityFilter.java` lines 62-70 (exact file hash pending).
- B evidence: `LivingEntity.java::isPushable`, lines 1950-1952, SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`; corresponding `EntityFilter.java` lines 56-64 (exact file hash pending).
- State producers/writers -> consumers/readers: alive/removal and climbing state -> `isPushable` -> EntityFilter/entity collision selection. The consumer context and player-player reachability require further caller audit.
- Parent slices / dependencies / closure evidence: S1-local-order; F-04 is a source-confirmed predicate change, but collision consumer closure remains open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B changes the predicate from `!removed` to `isAlive() && !isClimbing()`. This can alter eligibility for a living player when climbing, and for dead living entities; the latter producer is in excluded damage/health territory and is not simulated here. Determine exact player movement caller(s) before terminal disposition.
- Finding IDs or checked absence/replacement path: candidate F-04; pending caller closure.

### Slice S2-resize: entity box adjustment when dimensions change

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: `Entity.setSize`; B adds early return for width shrink, re-centering the new box on current x/z; A reconstructs from prior minX/minZ. Player call sites include sleep and dimension restoration.
- A evidence: `Entity.java::setSize`, lines 264-274, SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`.
- B evidence: `Entity.java::setSize`, lines 271-287, SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`.
- State producers/writers -> consumers/readers: pose/sleep dimensions -> bounding box -> position and collision queries; width/height defaults and eye height not yet closed.
- Parent slices / dependencies / closure evidence: S4-move-order; PlayerEntity sleep/pose calls require full guard and order comparison.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): source difference identified; exact player pose paths and resulting position semantics remain to be checked before finding classification.
- Finding IDs or checked absence/replacement path: candidate only; not indexed as finding.

### Slice S3-travel-and-world-dependencies: all remaining tick movement

- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: all branches and data outside S1-input, S1-local-order, and S3-jump; includes water/lava/fall-flying, attributes/effects, post-travel, shape callbacks and source resources.
- A evidence: `LivingEntity.java` travel-related bodies include `moveRelative` lines 1332 onward, `flyingTick` 1754-1773, `mobTick` 1681-1752; SHA-256 above.
- B evidence: corresponding `moveRelative` lines 1388 onward, `flyingTick` 1810-1830, `mobTick` 1737-1808; SHA-256 above.
- State producers/writers -> consumers/readers: open; fluid, block, modifiers, attribute/effect/equipment, input and synchronized state edges have not been fully enumerated.
- Parent slices / dependencies / closure evidence: all first-stage tick slices; no dependency closure yet.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): endpoint pair is comparable but broad tick and data-driven coverage is not complete.
- Finding IDs or checked absence/replacement path: open; do not infer parity from bounded slices already checked.

## Dependency queue and blockers

- D-SOURCE-DIAGNOSTICS: retain and recheck body-level diagnostics for every cited movement method; owner markers and global hashes match, but the full method-body-by-method diagnostics audit is not recorded in this report. Owner/source paths above; open.
- D-STEPHEIGHT: trace default and every writer of player `stepHeight`, its timing, and callers; needed by F-01.
- D-COLLISION: inspect both `World.getCollisions` overloads, entity filtering, Box axis intersection, every reachable player collision shape provider/callback/neighbor dependency and registrations; needed by F-01/F-03/F-04 and all movement.
- D-PISTON: fully compare `MovingBlockEntity` A/B progress, selection, shape and tick call order; tie movement cap to the exact reachable player path; needed by F-02.
- D-PUSHABILITY: trace player `isPushable` consumers and player-to-player collision/movement path; obtain EntityFilter hashes; needed by candidate F-04.
- D-POSE: trace player dimension/pose/eye-height constructors, sleep and pose transition writers, complete `trySleep`, and all collision readers; needed by S2-resize.
- D-TRAVEL: complete travel branch and exact operation order, including fluid/flight/climb, velocity cutoffs, jump/effect/item gates and post-travel state.
- D-BLOCK-DATA: inventory block/fluid providers, registrations, neighbors and resources (tags/data defaults and jar entries with hashes); modern-only applicability disposition per added block.
- D-MODIFIERS: trace movement attributes, effects, enchantments, equipment slots/applicability and application/removal timing; identify server-synchronized values and client-only boundary.
- D-EXTERNAL: trace corrections/teleports, velocity writes, mounts, shulkers and all player-facing movement producers; separate player movement from non-player simulation.
- D-REVIEWER: coordinator to assign an independent reviewer who did not author this report.
- D-CHECKER: resolved by applying canonical workflow checker fix `8fa4ab0`; report was checked against the local checker and accepted as structurally valid partial. This is schema validation only.
- Open dependencies: D-SOURCE-DIAGNOSTICS, D-STEPHEIGHT, D-COLLISION, D-PISTON, D-PUSHABILITY, D-POSE, D-TRAVEL, D-BLOCK-DATA, D-MODIFIERS, D-EXTERNAL, D-REVIEWER.

## Finding index

- [F-01](findings/F-01-sneak-edge-probe-depth.md): grounded sneaking player edge restraint probes a different vertical depth; source-confirmed, consequence conditional on shapes/support.
- [F-02](findings/F-02-piston-movement-cap.md): B applies per-world-time axis movement cap to piston displacement; source-confirmed, no runtime validation.
- F-04 (candidate): `LivingEntity.isPushable` predicate differs; caller/reachability audit is open, so not yet a confirmed finding.
- Discarded candidate: `LocalClientPlayerEntity.getRotationVector(float)` is added in B and reads yaw instead of inherited LivingEntity headYaw; at confirmed locally controlled travel, `PlayerEntity.serverTickAi` sets headYaw=yaw before travel. This bounds that travel use only; other callers remain open and this candidate is not globally discarded.
- Potential null-to-empty API changes in item/equipment code are not movement findings until empty ItemStack behavior and use/Elytra call order are proven equivalent or different.

## Resume checkpoint

- Last completed slice: exact source readiness verification; S1-input; bounded S3-jump; source differences F-01/F-02; partial S1-local-order.
- Next bounded slice and exact files/members/body ranges to open: `D-STEPHEIGHT` (`Entity` constructor/attribute writers/defaults), `D-COLLISION` (`World.getCollisions`, `Box` axis intersections, relevant `BlockState.addCollisions`), then `D-PISTON` (`MovingBlockEntity` complete progress/tick paths A 91-183 and B 116-285). Continue all open queue items in navigation order.
- Outstanding dependencies and owners: shared source owner is read-only publisher; source worker owns this run and findings; coordinator must assign independent reviewer.
- Current assumptions requiring verification: line ranges cited above remain stable under the verified source hashes; complete method correspondence, branch coverage, data resources and player-player push reachability remain open.

## Implementation reconciliation

Complete only after blind-discovery freeze. No mod implementation was opened.

- Reconciliation status: pending
- Repository revision inspected: none
- Finding -> implementation disposition/evidence: pending
- Existing implementation without a frozen source finding: pending
- Coverage gaps routed back to discovery slices: pending

## Independent source audit

- Reviewer: not assigned
- Status: pending
- Inventories and call-chain ranges re-walked: none
- Concrete missed-slice routes (or `none found`): pending
- Misses routed to slice/finding IDs and owners: pending
- Reviewer evidence / date: pending

## Source audit closure

- Coverage counts by status: 3 findings, 2 compared-no-difference, 3 in-progress, 1 pending (bounded rows only; broad inventory remains open).
- Required inventory status and evidence: only `INV-EXCLUSIONS` declaration complete; all movement inventories pending, with partial anchors above.
- Open dependencies: D-SOURCE-DIAGNOSTICS, D-STEPHEIGHT, D-COLLISION, D-PISTON, D-PUSHABILITY, D-POSE, D-TRAVEL, D-BLOCK-DATA, D-MODIFIERS, D-EXTERNAL, D-REVIEWER.
- Unresolved gaps and limits: complete tick graph, body-level diagnostic review, collision providers/resources, exact entity-player collision paths, modifiers, external writers, source-only freeze and independent audit remain open. Source comparison only; no gameplay behavior observed.
- Evidence/hash/correspondence audit: readiness and all 3,766 listed Java-source hashes were checked. Obtain missing EntityFilter/Box hashes before freezing. Exact cited line ranges should be rechecked at freeze.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).
