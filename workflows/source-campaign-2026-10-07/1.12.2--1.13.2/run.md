# Discovery: 1.12.2 to 1.13.2

- Status: active
- Scope: exact source-only client player movement comparison; older A = 1.12.2; newer B = 1.13.2. No wiki evidence or runtime validation.
- Repository revision and start date: base 002137b227676caea77f6832b9f4c8d0b6200bff; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: Ornithe Feather; explicit feather mode on both sides. Exact Feather Gen2 build 2 mapping artifacts verified per version; output namespace is ornithe-feather.
- Source preparation owner / command / log / readiness marker: campaign source owner (sole decompiler writer); exact commands, success excerpts, and readiness markers recorded per provenance JSON below. Shared campaign artifacts are read-only to this discovery owner.
- Toolchain/decompiler/remapper versions and options: Java 25.0.3+9-LTS; Gradle 9.7.1; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; ASM 9.10.1; 4G heap; explicit Feather.
- Discovery author(s): Codex source-discovery task on feat/source-discovery-movement-source-1-12-2-1-13-2.
- Independent reviewer (must differ from discovery authors): pending coordinator assignment.

## Artifact manifest

All paths are relative to shared campaign root build/movement-campaign-2026-10-07 unless noted.

- A: requested/resolved release 1.12.2; source root ready/1.12.2/ornithe-feather; client jar artifacts/1.12.2/client.jar SHA-256 8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f; CLI --versions=1.10.2,1.11.2,1.12.2 --mappings=feather --decompiler-heap=4G; mapping feather-gen2-1.12.2+build.2, artifacts/yarn/feather-gen2-1.12.2+build.2-mergedv2.jar SHA-256 e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca and .tiny SHA-256 a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58; mapped jar artifacts/1.12.2/client-ornithe-feather.jar SHA-256 65a08f15d18c4ec2bd0f05b89dfc1ba7ea6b8280ba93ed136245f39e63ec8a2b. Source manifest ready/1.12.2/ornithe-feather.sources.sha256 SHA-256 b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da (2,050 files); artifact manifest ready/1.12.2/artifacts.sha256 SHA-256 8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c; ready JSON SHA-256 b0aeec721e5c0af02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38; provenance JSON SHA-256 747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce; success excerpt SHA-256 986a06a8704103b61888040300fe5f5b2e4c00d3337971c0ff24ec66af3178af; movement diagnostics SHA-256 1ae1a796ac7650bf218af02eb602e1b7f46df2950e57b14263c65d3b58dc71b3.
- B: requested/resolved release 1.13.2; source root ready/1.13.2/ornithe-feather; client jar artifacts/1.13.2/client.jar SHA-256 3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9; CLI --versions=1.13.2 --mappings=feather --decompiler-heap=4G; mapping feather-gen2-1.13.2+build.2, artifacts/yarn/feather-gen2-1.13.2+build.2-mergedv2.jar SHA-256 317384d4faccc2939c3b14252993d31745eb42aad59f2f0ea78893cbe4e7283b and .tiny SHA-256 b3fd787448aed2c6e115d965d9edbb437ce47794ba63c7883e9014fd41262f71; mapped jar artifacts/1.13.2/client-ornithe-feather.jar SHA-256 d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5. Source manifest ready/1.13.2/ornithe-feather.sources.sha256 SHA-256 2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211 (2,711 files); artifact manifest ready/1.13.2/artifacts.sha256 SHA-256 fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e; ready JSON SHA-256 1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38; provenance JSON SHA-256 e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383; success excerpt SHA-256 2df48800e85ef769115801593f62f5e26827082e7623fda318559721bc49bcd1; movement diagnostics SHA-256 a89e4e3cefe704b58339a1e342e4d8a0fffe7e49dba3ca18402647b69c398a45.
- Validation: both ready markers say status ready and exact versionId; each JSON's source-manifest, artifact-manifest and diagnostic hash matched; client, mapped-client and both mapping artifact files matched artifact-manifest SHA-256 entries. Success excerpts state Gradle decompileMinecraft succeeded and exact requested/resolved IDs. They are summary excerpts, not full stdout.
- Cited source-file hashes are repeated in each coverage slice. Original jar resources and referenced resource hashes are still to be inventoried; none have been cited. Required external/server data and provenance remain open.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending full source comparison and independent closure.
- Evidence inventory and finding IDs included at freeze: pending.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; only repository docs, workflow guidance, templates and exact decompiled Minecraft sources have been opened.
- Source/mapping hashes covered by freeze: pending.

## Correspondence and call order

Pair is Feather Gen2 build 2 on both exact releases. Resolved hierarchy on both: net.minecraft.client.entity.living.player.LocalClientPlayerEntity -> net.minecraft.client.entity.living.player.ClientPlayerEntity -> net.minecraft.entity.living.player.PlayerEntity -> net.minecraft.entity.living.LivingEntity -> net.minecraft.entity.Entity. A and B files are separate; movement-member correspondence is checked by callers and behavior, not name alone.

A-side entry route observed: LocalClientPlayerEntity.tick() (lines 165-179) calls PlayerEntity.tick() (174-247), then LivingEntity.tick() (1619 onward), which calls Entity.tick()/baseTick() before virtual dispatch to LocalClientPlayerEntity.mobTick() (649-817). LocalClientPlayerEntity.mobTick() samples KeyboardInput.tick() (13-41), applies item-use scaling and sprint/flying gates, then calls superclass mobTick methods. LivingEntity.mobTick() (1781-1853) calls LocalClientPlayerEntity.serverTickAi() (631-642) to copy input fields into movement speeds/jumping, then jump gates, input damping, LivingEntity.moveRelative(), and post-travel push handling. This route is provisional: B order, overrides, and all pre/post-travel dependencies remain under audit.

## Required source inventories

Each inventory maps to bounded slice IDs. An inventory remains pending until every listed member/data slice and producer-consumer route is dispositioned.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=KB-SAMPLE, CLIENT-TICK, PLAYER-TICK, INPUT-TRANSFER, ITEM-USE-SCALE, SPRINT-STATE, FLIGHT-TOGGLE, AUTOJUMP, JUMP-GATES, VELOCITY-CUTOFF, TRAVEL-INPUT-DAMPING, POST-TRAVEL; evidence=source-only opening route recorded above; B call order and remaining slice ranges pending.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, support, timers and direct predicates: status=pending; slice_ids=POSE-DIMENSIONS, SWIM-STATE, FLUID-HEIGHT, VELOCITY-WRITERS, EYE-HEIGHT, SPRINT-JUMP-TIMERS; evidence=movement source members to inventory.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=SNEAK-EDGE, ENTITY-MOVE-AXES, STEP-CANDIDATES, SUPPORT-CALLBACKS, WORLD-COLLISIONS, SHAPE-PROVIDERS; evidence=Entity.move and shape source inventory open.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=BLOCK-REGISTRATIONS, COLLISION-SHAPES, BLOCK-CALLBACKS, FLUID-STATE, FLUID-FLOW, BUBBLE-COLUMNS, NEIGHBOR-SHAPES; evidence=full class/registration/resource inventory pending.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=ATTRIBUTES, SPEED-SLOWNESS, JUMP-BOOST, LEVITATION, SLOW-FALLING, DEPTH-STRIDER, DOLPHINS-GRACE, ELYTRA, RIPTIDE; evidence=consumer-to-registration closure pending.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=VELOCITY-PACKETS, PLAYER-PUSHES, PISTON-MOVE, MOUNT-TRANSITIONS, LAUNCH-ITEMS; evidence=network/producer inventory pending.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=direct movement predicates may read vanilla state; excluded producer systems and non-player physics will not be emulated.

## Coverage ledger

### Slice KB-SAMPLE: keyboard input sampling and sneak scaling

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: full KeyboardInput.tick() body samples four directional keys, jump and sneak; then applies 0.3F scaling to both movement components when sneaking. The complete source file is byte-identical in the aligned namespace. This slice does not disposition downstream input consumption.
- A evidence: build/movement-campaign-2026-10-07/ready/1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java::net.minecraft.client.entity.living.player.KeyboardInput#tick(), lines 13-41, SHA-256 7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a
- B evidence: build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java::net.minecraft.client.entity.living.player.KeyboardInput#tick(), lines 13-41, SHA-256 7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a
- State producers/writers -> consumers/readers: GameOptions key states -> KeyboardInput.tick() writes movementSideways/movementForward/jumping/sneaking and direction flags -> LocalClientPlayerEntity.mobTick() applies later item-use/flying handling and LocalClientPlayerEntity.serverTickAi() copies movement/jump fields for LivingEntity.mobTick().
- Parent slices / dependencies / closure evidence: exact source manifest hashes verified on both sides; KeyboardInput.java source hash equal. Downstream scaling and consumer slices remain separate and open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): on the local client player path, the same full sampled-key-to-input-field and sneak-scaling body executes in both exact source trees. This proves no source difference within this bounded method; it does not imply equivalence of later player input decisions or movement.
- Finding IDs or checked absence/replacement path: no finding for this bounded method; identical full-file SHA-256 confirms the checked body.

## Dependency queue and blockers

- Open dependencies: SRC-MANIFEST-ROWS (this discovery owner): populate and close every required member-level slice from all seven inventories; SOURCE-RESOURCE-ENTRIES (this discovery owner): inspect matching original jar entries, data, tags and defaults; METHODS-DIAGNOSTICS (this discovery owner): resolve movement-related decompiler diagnostics and use mapped-jar bytecode/descriptors for any damaged or unmapped methods; INDEPENDENT-REVIEWER (coordinator): name a non-author to re-walk the inventories and full call graph after freeze; IMPLEMENTATION-RECONCILIATION (coordinator/integrator): defer until blind source report is frozen.

## Finding index

Source-confirmed candidates being traced before finding records are frozen: 1.13 water/sprint/swimming gates; shallow-water jump and tagged-fluid jump; swimming pose and water-relative pitch acceleration; Slow Falling gravity and fall-distance state; changes to elytra acceleration/clamps; water movement multipliers (sprint, Depth Strider, Dolphin's Grace); collision shape/axis resolution and step candidates. These are navigation checkpoints, not yet the exhaustive findings catalog. Modern-only equipment mechanics such as Riptide are separately classified against 1.12.2 scope.

## Resume checkpoint

- Last completed slice: KB-SAMPLE (bounded no-difference); exact source pair/provenance and primary movement source manifests verified.
- Next bounded slice and exact files/members/body ranges to open: compare LocalClientPlayerEntity.mobTick() A lines 649-817 vs B lines 653-842, then split water sprint start/maintenance, flight-toggle, water sneak descent and swimming gates with their field producers/consumers.
- Outstanding dependencies and owners: all required inventory slices; resource jar audit; movement-body diagnostics and unmapped method descriptors; independent reviewer and post-freeze implementation reconciliation.
- Current assumptions requiring verification: f_85121000/m_74407200 names in B need producer/caller/descriptor resolution; no semantic alias will be assumed.

## Implementation reconciliation

Complete only after blind-discovery freeze; not started and no mod implementation was inspected.

- Reconciliation status: pending
- Repository revision inspected: none
- Finding -> implementation disposition/evidence: pending
- Existing implementation without a frozen source finding: pending
- Coverage gaps routed back to discovery slices: pending

## Independent source audit

- Reviewer: pending coordinator assignment
- Status: pending
- Inventories and call-chain ranges re-walked: pending
- Concrete missed-slice routes (or none found): pending
- Misses routed to slice/finding IDs and owners: pending
- Reviewer evidence / date: pending

## Source audit closure

- Coverage counts by status: 1 compared-no-difference (KB-SAMPLE); additional bounded source slices not yet enumerated; all seven required inventories pending.
- Required inventory status and evidence: all pending; see inventory map above.
- Open dependencies: SRC-MANIFEST-ROWS, SOURCE-RESOURCE-ENTRIES, METHODS-DIAGNOSTICS, INDEPENDENT-REVIEWER, IMPLEMENTATION-RECONCILIATION.
- Unresolved gaps and limits: full paired tick sequence, all state writers/consumers, all block/fluid collision shapes, modifiers/equipment, external inputs, resources, and reviewer audit remain open.
- Evidence/hash/correspondence audit: readiness/artifact/source manifest chain verified; cited KeyboardInput hashes verified; future source citations require per-file hashes and exact member ranges.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).

The run folder contains only run.md and, when findings exist, one file per finding under findings/. The completion checker verifies structure and statuses only; the independent source audit establishes evidence quality.
### Slice CLIENT-TICK and PLAYER-TICK: paired client/player tick entry and input transfer

- Inventory ID(s): INV-TICK
- Exact boundary checked: LocalClientPlayerEntity.tick() A 165-179 / B 181-195 and PlayerEntity.tick() A 174-247 / B 176-256; the applicable PlayerEntity.tick() calls `super.tick()` and updates player pose, while LocalClientPlayerEntity performs its client tick around that superclass route. Source routes then enter Entity.tick/baseTick, LivingEntity.tick/baseTick, and virtual `mobTick`; exact client/server call-order remains pending bytecode/diagnostic reconciliation where decompiler member names differ.
- A evidence: LocalClientPlayerEntity.java SHA-256 01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc; PlayerEntity.java SHA-256 e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e.
- B evidence: LocalClientPlayerEntity.java SHA-256 2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf; PlayerEntity.java SHA-256 4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633.
- Producers -> consumers: client tick and player tick -> inherited Entity/LivingEntity tick/baseTick -> virtual LocalClientPlayerEntity.mobTick -> serverTickAi copies current input to LivingEntity speed/jump fields -> LivingEntity.mobTick jump/travel.
- Parent slices/dependencies: KB-SAMPLE; input transfer and tick-body call-order still need terminal exact ranges and diagnostics closure.
- Status: compared-with-difference (entry windows inspected; whole route open)
- Disposition: 1.13.2 adds water-state preparation in the PlayerEntity/Entity tick route (tracked separately as SWIM-STATE/FLUID-HEIGHT); no global call-order equivalence is claimed while this inventory remains open.
- Finding IDs: F-WATER-STATE.

### Slice SPRINT-STATE, FLIGHT-TOGGLE, and WATER-DESCENT: local input decisions

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXCLUSIONS
- Exact boundary checked: LocalClientPlayerEntity.mobTick() A 694-782 / B 698-807; setSprinting() A 444-447 / B 458-461; serverTickAi() A 631-642 / B 635-646. Differences are ordered in the same tick after KeyboardInput.tick() and item-use scaling and before superclass travel.
- A evidence: LocalClientPlayerEntity.java SHA-256 01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc.
- B evidence: LocalClientPlayerEntity.java SHA-256 2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf.
- Producers -> consumers: key state -> `input`; `input.jumping/sneaking/movementForward`, onGround, sprint flag, collision flag, water/submerged/swimming flags, canFly and food level -> sprint/flight decisions -> movement field copy and LivingEntity travel. Food level is only the vanilla sprint eligibility predicate here; food/exhaustion simulation is excluded.
- Parent slices/dependencies: KB-SAMPLE, SWIM-STATE, FLUID-HEIGHT, ITEM-USE-SCALE; prove state writers/timing before source closure.
- Status: compared-with-difference
- Disposition: A sprint starts via ground-only double tap or held sprint and stops when forward input, horizontal collision, or food/canFly gate fails. B permits double-tap while on ground or submerged, held sprint when not in water or submerged, distinguishes swimming sprint cancellation from ordinary sprint cancellation, and prevents flight double-tap toggle while swimming. B additionally applies `knockDownwards()` whenever the player is in water and sneaking (B lines 785-787); the A body has no corresponding call. These are reachable local-player movement changes; B's separate underwater visibility counter is not classified as movement.
- Finding IDs: F-WATER-SPRINT; F-WATER-DESCENT.

### Slice SWIM-STATE and FLUID-HEIGHT: water sampling and swimming transition

- Inventory ID(s): INV-STATE, INV-TICK, INV-WORLD-MOVEMENT
- Exact boundary checked: Entity.baseTick() A 328 onward / B 340 onward; B Entity helper m_03231680() lines 947-951, checkWaterState() lines 961-975 and updateSwimming() lines 953-959; PlayerEntity.updateSwimming() B 1471-1476; water-depth sampler m_69693160(Tag<Fluid>) B 2509-2570 and getter m_02485546() B 2573-2575. A Entity.checkWaterState() lines 944-946 delegates to World.applyLiquidDrag(..., Material.WATER, entity); `checkWaterCollisions()` A 948-964 updates inWater.
- A evidence: Entity.java SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a; World.java SHA-256 e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594.
- B evidence: Entity.java SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269; PlayerEntity.java SHA-256 4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633.
- Producers -> consumers: B fluid-state grid intersection/flow calculation writes inWater and raw `f_85121000` sampled depth; Entity base tick then caches submerged-in-water state and updates swimming; PlayerEntity suppresses swimming when flying; LivingEntity.mobTick consumes sampled depth for jump selection; LocalClientPlayerEntity consumes submerged/swimming predicates for sprint and flight. Source timing indicates base-tick sampling precedes local input decisions; owner diagnostics and mapped-jar correspondence for the raw helper remain an open dependency.
- Parent slices/dependencies: CLIENT-TICK, JUMP-GATES, FLUID-STATE, FLUID-FLOW, WATER-SPRINT; resource/flow closure pending.
- Status: compared-with-difference
- Disposition: A uses the legacy material drag callback to set water contact and has no submerged-depth/swimming state. B samples fluid states and summed flow over the entity box, stores a maximum depth, and has a distinct swimming transition (already swimming uses sprint+inWater; entering uses sprint+isSubmergedInWater; both require not riding). This directly changes player gates; the sampler's exact bytecode/member-name pairing still requires closure before freeze.
- Finding IDs: F-WATER-STATE; F-FLUID-DRAG.

### Slice JUMP-GATES: water-height-dependent jump selection

- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT
- Exact boundary checked: LivingEntity.mobTick() A 1827-1839 / B 1903-1917; A jumpInWater()/jumpInLava() and B m_74407200(Tag<Fluid>) exact descriptor `(Lnet/minecraft/tag/Tag;)V` confirmed in B mapped jar. The source method adds `0.04F` to velocityY for WATER and LAVA tags.
- A evidence: LivingEntity.java SHA-256 190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6.
- B evidence: LivingEntity.java SHA-256 bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c.
- Producers -> consumers: KeyboardInput/local input -> jumping field; onGround/cooldown and B fluid depth -> either water/lava impulse or ordinary jump -> velocityY -> travel. B depth is sourced by F-WATER-STATE.
- Parent slices/dependencies: SWIM-STATE, FLUID-HEIGHT, JUMP-BOOST, VELOCITY-CUTOFF.
- Status: compared-with-difference
- Disposition: A applies a water or lava upward impulse whenever the respective `isInWater`/`isInLava` predicate holds; otherwise a grounded jump is accepted when cooldown is zero. B routes positive depth above 0.4 to water impulse, allows a ground jump in shallow depth `(0, 0.4]` when cooldown is zero, checks lava only in its non-water branch, and otherwise performs ordinary grounded jump. This creates a new water-height threshold on the reachable player jump path.
- Finding IDs: F-WATER-JUMP.

### Slice POSE-DIMENSIONS and SWIM-ACCELERATION

- Inventory ID(s): INV-STATE, INV-COLLISION, INV-TICK
- Exact boundary checked: PlayerEntity.updatePlayerPose() A 291-315 / B 334-? (method ends before following movement methods); PlayerEntity.moveRelative() A 1386-1410 / B 1442-1487; LivingEntity.moveRelative() A 1425-1618 / B 1478-1688. B swimming pose assigns 0.6-wide/0.6-high dimensions and checks collision fit through `world.hasNoCollisions`; A has no swimming pose. B PlayerEntity.moveRelative adds pitch-dependent vertical motion when swimming and not riding, using look-vector Y and a 0.085/0.06 coefficient selected by the -0.2 pitch threshold.
- A evidence: PlayerEntity.java SHA-256 e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e; LivingEntity.java SHA-256 190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6.
- B evidence: PlayerEntity.java SHA-256 4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633; LivingEntity.java SHA-256 bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c.
- Producers -> consumers: swimming state and pose preference -> collision-tested dimension transition -> getShape/eye height/collision queries; look pitch, swimming, riding and jumping -> additive velocityY -> later travel/collision.
- Parent slices/dependencies: SWIM-STATE, WORLD-COLLISIONS, ENTITY-MOVE-AXES, SUPPORT-CALLBACKS.
- Status: compared-with-difference
- Disposition: B adds reachable swim pose/dimensions and a direct swimming vertical acceleration. Exact full pose method range and all dimension/eye-height writers remain to be cataloged, so the parent inventory stays open.
- Finding IDs: F-SWIM-POSE; F-SWIM-ACCELERATION.

### Slice TRAVEL-MODIFIERS: slow falling, elytra, fluid travel, ground/air coefficients

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact boundary checked: LivingEntity.travel()/m_74407200 body A 1425-1618 / B 1478-1688; modifiers are read in the body, with Depth Strider via EnchantmentHelper and B Dolphin's Grace, Slow Falling, Levitation and Elytra branches. Need split terminal findings by elytra, water, lava, land, effects, and exact float operation order before freeze.
- A evidence: LivingEntity.java SHA-256 190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6.
- B evidence: LivingEntity.java SHA-256 bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c.
- Producers -> consumers: status effects/equipment/enchantment and block slipperiness -> travel branch gravity/friction/acceleration -> velocity and Entity.move.
- Parent slices/dependencies: ELYTRA, SLOW-FALLING, LEVITATION, DEPTH-STRIDER, DOLPHINS-GRACE, ATTRIBUTES, COLLISION-SHAPES.
- Status: compared-with-difference (candidate paths traced; split evidence remains open)
- Disposition: B initializes gravity factor `d=0.08`; Slow Falling with nonpositive Y sets it to 0.01 and clears fallDistance. Elytra vertical acceleration is expressed as `d * (-1.0 + n * 0.75)` instead of A's `-0.08 + m * 0.06`; the B pitch-up branch also adds a positive-horizontal-look guard before division. Water travel adds sprint/Dolphin's Grace handling, conditional gravity and terminal downward clamp; ground/air acceleration changes `0.16277136F` to `0.16277137F`; lava uses `d / 4.0`. These are source candidates requiring one finding per branch and direct effect/enchantment registration closure.
- Finding IDs: F-SLOW-FALLING; F-ELYTRA-MATH; F-ELYTRA-GUARD; F-WATER-TRAVEL; F-LAVA-GRAVITY; F-GROUND-COEFFICIENT.

### Slice VELOCITY-CUTOFF and POST-TRAVEL

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXTERNAL
- Exact boundary checked: LivingEntity.mobTick() A 1781-1853 / B 1852-1936. Three per-axis `<0.003` velocity zeroing checks remain textually equivalent, with shifted surrounding order. Both damp local sideways/forward movement by `0.98F`, rotation by `0.9F`, call flyingTick then moveRelative, and push colliding entities. B additionally snapshots shape before travel and performs size-change collision adjustment when spin-attack timer is positive.
- A evidence: LivingEntity.java SHA-256 190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6.
- B evidence: LivingEntity.java SHA-256 bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c.
- Producers -> consumers: external/network velocity and earlier travel -> threshold zeroing -> jump/input/travel; Riptide spin timer and pose/shape -> post-travel collision adjustment; push-away callback -> velocity writer audit.
- Parent slices/dependencies: VELOCITY-PACKETS, PLAYER-PUSHES, LAUNCH-ITEMS, RIPTIDE, ENTITY-MOVE-AXES.
- Status: compared-with-difference
- Disposition: `<0.003` is a no-difference bounded sub-slice; B's head-yaw interpolation is not a movement input, while its spin-attack shape adjustment is player-reachable only from 1.13 equipment and remains a modern-only disposition pending call closure. Push implementation remains open.
- Finding IDs: F-SPIN-SHAPE (modern-only pending exact Riptide/source trace).

### Slice ENTITY-MOVE-AXES and STEP-CANDIDATES

- Inventory ID(s): INV-COLLISION, INV-STATE, INV-WORLD-MOVEMENT
- Exact boundary checked: Entity.move() A 462-796 / B 473-782; includes sneak-edge probes, collision collection, vertical/X/Z offset resolution, step alternatives, flags, support checks and callbacks.
- A evidence: Entity.java SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a.
- B evidence: Entity.java SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269.
- Producers -> consumers: requested velocity/move type and current shape -> world collision candidates -> Y then X then Z clipping; step candidates may replace clipped horizontal path based on squared distance -> position, collision flags, onGround/support, velocity zeroing and block callbacks.
- Parent slices/dependencies: WORLD-COLLISIONS, SHAPE-PROVIDERS, BLOCK-CALLBACKS, NEIGHBOR-SHAPES.
- Status: compared-with-difference (algorithm boundary identified; helper/world providers open)
- Disposition: A queries `List<Box>` through `World.getCollisions` and intersects candidates sequentially; B queries voxel shapes and applies `VoxelShapes.calculateMaxOffset` per axis. Sneak-edge probe decrements by 0.05 in both. Vertical, X, Z axis order is retained; step alternatives use changed shape representation. Full semantic comparison requires helper algorithm, shape providers, border/world collision collection and block callback closure.
- Finding IDs: F-COLLISION-REPRESENTATION.

### Slice SUPPORT-CALLBACKS

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact boundary checked: Entity.move() A 695-796 / B 675-782; setPositionFromShape, collision/onGround flags, support block lookup and stepped-on/beforeCollision callbacks.
- A evidence: Entity.java SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a.
- B evidence: Entity.java SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269.
- Producers -> consumers: resolved axis offsets -> position and flags -> support block at floor(y-0.2), fence/wall/gate fallback, fall-distance callback, velocity component zeroing, beforeCollision, stepped-on and step sounds.
- Parent slices/dependencies: ENTITY-MOVE-AXES, SHAPE-PROVIDERS, BLOCK-CALLBACKS, FALL-DISTANCE (damage excluded except direct movement state write).
- Status: compared-with-difference (callbacks identified; providers pending)
- Disposition: post-resolution path has shared callback ordering with changed source APIs (`Material.AIR`/Box-era versus `isAir`/voxel-shape-era); all support block shape and callback implementations are not yet closed.
- Finding IDs: F-COLLISION-REPRESENTATION.
