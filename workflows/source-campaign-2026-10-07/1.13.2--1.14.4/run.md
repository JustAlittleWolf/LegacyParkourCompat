# Discovery: 1.13.2 to 1.14.4

- Run status: partial
- Scope: source-only comparison of direct client player movement; A=1.13.2; B=1.14.4.
- Repository revision and start date: branch `feat/source-discovery-movement-source-1-13-2-1-14-4`, based on `main` at `002137b227676caea77f6832b9f4c8d0b6200bff`; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: Ornithe Feather `feather`, generated namespace `ornithe-feather`; exact mapping artifacts/builds and metadata IDs are readiness-verified.
- Source preparation owner / command / log / readiness marker: read-only records under `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready`; Exact per-version command and provenance are available in read-only `.provenance.json`; success logs are summary-excerpt-only. Source owner reports a shared-cache artifact-immutability incident affecting A and requires canonical repair before findings may be frozen/accepted (`D-ARTIFACT-INTEGRITY`). No decompiler run here.
- Toolchain/decompiler/remapper versions and options: Vineflower; Java 25. Repo pins Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1; BuildSrc options `ignoreConflicts=true`, `renameInvalidLocals=true`, `rebuildSourceFilenames=true`, `fixPackageAccess=true`. Provenance records Java 25.0.3+9-LTS, Gradle 9.7.1, Vineflower 1.12.0, ASM 9.10.1, Mapping IO 0.9.1, Gson 2.14.0, Tiny Remapper 0.14.1; `--decompiler-heap=4G`.
- Discovery author(s): source worker.
- Independent reviewer: unassigned.

## Artifact manifest

At initial readiness validation, all listed source and artifact hashes matched (A: 2711 sources, 42 artifacts; B: 3250 sources, 40 artifacts). After the source-owner integrity notice, readiness/manifest hashes and the cited Feather mapping artifacts were rechecked and match, but the owner requires canonical repair and fresh acceptance before findings can be frozen. Roots below are relative to this run folder.

### A — 1.13.2

- Exact requested/resolved/versionMetadataId: 1.13.2 / 1.13.2 / 1.13.2.
- Source root: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`.
- Client SHA-256: `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`.
- Mapping: `ornithe-feather`; `feather-gen2-1.13.2+build.2-mergedv2.jar`, `feather-gen2-1.13.2+build.2.tiny`; mapping jar SHA-256 `317384d4faccc2939c3b14252993d31745eb42aad59f2f0ea78893cbe4e7283b`, tiny mapping SHA-256 `b3fd787448aed2c6e115d965d9edbb437ce47794ba63c7883e9014fd41262f71`. Artifact manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` (42 entries).
- Source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 2711 files / 14,770,525 bytes.
- Diagnostics `movement-diagnostics.txt`, SHA-256 `a89e4e3cefe704b58339a1e342e4d8a0fffe7e49dba3ca18402647b69c398a45`; reports successful decompile, metadata match and movement body review marker 10 for Entity/LivingEntity/PlayerEntity/ClientPlayerEntity.

### B — 1.14.4

- Exact requested/resolved/versionMetadataId: 1.14.4 / 1.14.4 / 1.14.4.
- Source root: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`.
- Client SHA-256: `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`.
- Mapping: `ornithe-feather`; `feather-gen2-1.14.4+build.2-mergedv2.jar`, `feather-gen2-1.14.4+build.2.tiny`; mapping jar SHA-256 `3162806b9fb266d7e6d2c594be8d4cc6c91ddb09b4e55d1b566e5429edbd793f`, tiny mapping SHA-256 `60d4906621c873dadba96425d1a233349c9afa407d09bfc8371202af6a71f25c`. Artifact manifest SHA-256 `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970` (40 entries).
- Source manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`; 3250 files / 16,680,788 bytes.
- Diagnostics `ornithe-feather.movement-diagnostics.txt`, SHA-256 `8b5fba7cae7c0187671005b3261d184cbc2caecf9f8cbfc25b284bb0a89d128a`; reports successful decompile, metadata match and movement body review marker 10 for Entity/LivingEntity/PlayerEntity/ClientPlayerEntity.

Current integrity recheck: readiness JSON, source manifest, artifact manifest and diagnostics hashes still match their published values; A and B Feather mapping JAR/tiny hashes also match the current cache entries. However, the source owner has not yet published the required canonical immutability repair/re-acceptance. Therefore no A-dependent finding is frozen or accepted, even though cited generated Java file hashes currently match. Keep `D-ARTIFACT-INTEGRITY` open until repair protocol and fresh verification arrive.

Exact source-owner commands from provenance (success logs are summary excerpts only): A `gradlew.bat decompileMinecraft --versions=1.13.2 --mappings=feather --decompiler-heap=4G`; B `gradlew.bat decompileMinecraft --versions=1.14.4 --mappings=feather,mojmap --decompiler-heap=4G`; each used its published staging output root and shared cache `build/movement-campaign-2026-10-07/artifacts`.

Cited Java files and whole-file SHA-256:

- `net/minecraft/client/entity/living/player/KeyboardInput.java`: A `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`; B `5932453a9e48e7a798ae1be1cd3a4bf660b6b3be43bb7e5dc22686b3c4a82526`.
- `net/minecraft/client/entity/living/player/Input.java` (B): `acb63d46fb6e6a1cb6b285a5d0702fb62575f7d54344ad1f0f9d51e8f671f33f`.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: A `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`; B `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce`.
- `net/minecraft/entity/living/LivingEntity.java`: A `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`; B `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.
- `net/minecraft/entity/Entity.java`: A `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`; B `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`.
- `net/minecraft/entity/living/player/PlayerEntity.java`: A `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`; B `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df`.
- `net/minecraft/util/math/MathHelper.java` (B): `e39d5dfc69c17a9032be086d7d4d14cc63525962a0ff83615b1befdcca599843`.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: none.
- Evidence inventory and finding IDs included at freeze: none.
- Confirmation old mod implementation and isolated wiki-audit results were not opened before freeze: confirmed; no wiki browsing.
- Source/mapping hashes covered by freeze: pending.

## Correspondence and call order

Resolved pairs include `LocalClientPlayerEntity.mobTick`, `KeyboardInput.tick` (no-arg to boolean args), `LivingEntity.jump`, `LivingEntity.moveRelative` (three floats to Vec3d), `Entity.updateVelocity` (float components to vector helper), `Entity.move` (three doubles to vector), and `PlayerEntity.updatePlayerPose`. Full local tick, superclass, pre-travel, all dispatch branches and post-travel call chain are not closed. S001-S009 contain first bounded paired evidence; no stage label alone is treated as coverage.

## Required source inventories

- `INV-TICK` status=pending; slice_ids=S001,S003,S004,S005,S007,S008,S009; evidence=paired client input/local player/living bodies below; complete tick call graph open.
- `INV-STATE` status=pending; slice_ids=S001,S002,S003,S006,S008,S009; evidence=paired input, pose, jump and movement bodies below.
- `INV-COLLISION` status=pending; slice_ids=S002,S006,S009; evidence=pose fit, escape probes and entity move excerpts; query/provider enumeration open.
- `INV-WORLD-MOVEMENT` status=pending; slice_ids=S005,S007; evidence=slipperiness/climbing consumers; providers and resources open.
- `INV-MODIFIERS` status=pending; slice_ids=S003,S005; Jump Boost/Slow Falling consumers observed; producer/application chains open.
- `INV-EXTERNAL` status=pending; slice_ids=S006; Entity move piston path observed; packet/push/mount consumers open.
- `INV-EXCLUSIONS` status=pending; evidence=scope exclusions declared; direct-read inventory open. Food/blindness values may remain movement-predicate inputs; producer-system emulation excluded.

## Coverage ledger

### Slice S001: input scaling and submerged sprint threshold

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: keyboard movement scaling and submerged forward sprint threshold.
- A evidence: `KeyboardInput.java`::`tick()`, lines 13-50; `LocalClientPlayerEntity.java` thresholds around 701,726,740. Hashes in manifest.
- B evidence: `KeyboardInput.java`::`tick(boolean,boolean)`, lines 13-26; `LocalClientPlayerEntity.java`::`m_03985577`, lines 961-964. Hashes in manifest.
- B helper evidence: `Input.java`::`m_49149051()`, lines 22-24; `LocalClientPlayerEntity.java`::`m_63723874()`, lines 596-598; `Entity.java`::`m_00306336()`/`m_99544176()`, lines 1818-1823.
- State producers/writers -> consumers/readers: raw input/sneak/spectator -> scaled movementForward -> sprint gate; submerged state chooses B's separate threshold.
- Parent slices / dependencies / closure evidence: full sprint caller/order and upstream pose-related input open; D-TICK-CLOSURE.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F001; B's submerged path uses `movementForward > 1.0E-5F` versus A `>=0.8F`; B also changes the conditions under which sneak scaling applies. Full sprint eligibility remains open.
- Finding IDs or checked absence/replacement path: F001.

### Slice S002: pose selection and collision-aware resize

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: `PlayerEntity.updatePlayerPose` selection, fallback and collision probe.
- A evidence: `net/minecraft/entity/living/player/PlayerEntity.java`::`updatePlayerPose()`, lines 334-360, SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`.
- B evidence: `net/minecraft/entity/living/player/PlayerEntity.java`::`updatePlayerPose()`, lines 343-370, SHA-256 `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df`.
- State producers/writers -> consumers/readers: pose predicates -> dimensions/eye height -> collision/fluid queries.
- Parent slices / dependencies / closure evidence: dimensions, base resize listener and collision query internals open; D-COLLISION-SHAPES.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F002; A retains old dimensions when candidate collision test fails and uses a 1.65 sneak height; B chooses/falls back among poses, and its tested collision context passes `this` whereas A passes `null`. Full query behavior open.
- Finding IDs or checked absence/replacement path: F002.

### Slice S003: base jump impulse and Jump Boost arithmetic

- Inventory ID(s): INV-TICK, INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.jump` vertical impulse; sprint impulse visible but not fully inventoried.
- A evidence: `net/minecraft/entity/living/LivingEntity.java`::`jump()`, lines 1447-1464, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- B evidence: `net/minecraft/entity/living/LivingEntity.java`::`jump()`, lines 1752-1772, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.
- State producers/writers -> consumers/readers: Jump Boost amplifier and base strength -> vertical velocity; yaw+sprint -> horizontal impulse.
- Parent slices / dependencies / closure evidence: tick callers located, effect producer/application chain open; D-MOVEMENT-DATA.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F003; with Jump Boost A adds the float boost to already-double velocity, B sums as float before storing; exact applicability depends on effect active.
- Finding IDs or checked absence/replacement path: F003.

### Slice S004: input acceleration normalization

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: cutoff, normalization, yaw transform and velocity accumulation.
- A evidence: `net/minecraft/entity/Entity.java`::`updateVelocity(float,float,float,float)`, lines 1057-1075, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- B evidence: `net/minecraft/entity/Entity.java`::`updateVelocity(float,Vec3d)` and `m_51093791`, lines 1062-1077, SHA-256 `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`.
- State producers/writers -> consumers/readers: travel input/scale -> acceleration vector -> velocity consumed by Entity.move.
- Parent slices / dependencies / closure evidence: all travel callers open; D-TICK-CLOSURE.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F004; float cutoff `1.0E-4F`/normalization in A changes to double cutoff `1.0E-7`/vector normalization in B. Reachable input impact unmeasured.
- Finding IDs or checked absence/replacement path: F004.

### Slice S005: ground acceleration and ordinary climb response

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: ground acceleration helper and post-move climb impulse.
- A evidence: `net/minecraft/entity/living/LivingEntity.java`::`moveRelative(float,float,float)`, lines 1537-1600, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- B evidence: `net/minecraft/entity/living/LivingEntity.java`::`moveRelative(Vec3d)`, lines 1840-1860, and `m_70235197(float)`, lines 1961-1962, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.
- State producers/writers -> consumers/readers: below-block slipperiness/onGround/getSpeed -> ground acceleration; jumping/collision/isClimbing -> vertical velocity.
- Parent slices / dependencies / closure evidence: registered climbable/slipperiness providers open; D-MOVEMENT-DATA.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F005 records B's `(collidingHorizontally || jumping) && isClimbing()` versus A's collision-only guard. F006 records changed ground acceleration constants and float operation order. Block-provider closure remains open.
- Finding IDs or checked absence/replacement path: F005,F006.

### Slice S006: collision axis order, no-op cutoff and horizontal flag

- Inventory ID(s): INV-STATE, INV-COLLISION, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: ordinary Entity.move collision route, resolved shape/position update and collision flag.
- A evidence: `net/minecraft/entity/Entity.java`::`move(MoverType,double,double,double)`, lines 578-599 and 601-680, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- B evidence: `net/minecraft/entity/Entity.java`::`move(MoverType,Vec3d)`, lines 444-475, and `m_79801352`/`m_73670363`, lines 708-771, SHA-256 `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`.
- State producers/writers -> consumers/readers: travel/external displacement -> resolved position/flags/velocity cancellation/callbacks.
- Parent slices / dependencies / closure evidence: helper/step ties, world query, shapes, callbacks and caller inventory open; D-COLLISION-SHAPES,D-EXTERNAL.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F007 records A's Y-X-Z vs B's magnitude-ordered horizontal resolution. F008 records B's resolved-vector squared cutoff `>1.0E-7` before shape update, absent in A. F009 records approximate horizontal collision comparison in B versus exact inequality in A; velocity cancellation remains exact in B.
- Finding IDs or checked absence/replacement path: F007,F008,F009.

### Slice S007: water-branch climbing response

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: water travel after movement; horizontal collision and climbing predicate.
- A evidence: `net/minecraft/entity/living/LivingEntity.java`::`moveRelative(float,float,float)`, lines 1617-1655, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- B evidence: `net/minecraft/entity/living/LivingEntity.java`::`moveRelative(Vec3d)`, lines 1882-1928 (climb check 1907-1911), SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.
- State producers/writers -> consumers/readers: water branch selection -> Entity.move collision flag and isClimbing -> vertical velocity -> water damping/gravity.
- Parent slices / dependencies / closure evidence: fluid contacts and climbable block providers open; D-MOVEMENT-DATA,D-COLLISION-SHAPES.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F010; B adds a 0.2 vertical assignment under `collidingHorizontally && isClimbing()` in water travel; no corresponding A action in this branch.
- Finding IDs or checked absence/replacement path: F010.

### Slice S008: slow-falling water descent predicate timing

- Inventory ID(s): INV-TICK, INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Slow Falling selection at travel entry and descent branch after water movement/damping.
- A evidence: `net/minecraft/entity/living/LivingEntity.java`::`moveRelative(float,float,float)`, lines 1480-1484 and 1639-1649, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- B evidence: `net/minecraft/entity/living/LivingEntity.java`::`moveRelative(Vec3d)`, lines 1786-1793 and 1912-1921, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.
- State producers/writers -> consumers/readers: initial velocity and Slow Falling -> stored gravity scale/direction -> water movement and collision -> descent adjustment predicate.
- Parent slices / dependencies / closure evidence: effect lifecycle and collision state effects open; D-MOVEMENT-DATA,D-COLLISION-SHAPES.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F011; A checks post-move velocity direction for the special descent case; B gates it using direction captured before travel. Applicable only with Slow Falling in water and velocity direction changing across movement.
- Finding IDs or checked absence/replacement path: F011.
### Slice S009: local stuck-block escape probes and velocity writes

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: four LocalClientPlayerEntity push-away calls before sprint checks, noClip guard, current/neighbor block probe and direct velocity component write.
- A evidence: `LocalClientPlayerEntity.java`::`mobTick`, lines 717-721; `pushAwayFrom`/`wouldCollideAt`, lines 401-455; `PlayerEntity.java` suffocation helpers, lines 1479-1485. Hashes in source manifest.
- B evidence: `LocalClientPlayerEntity.java`::`mobTick`, lines 645-651; `pushAwayFrom`/`wouldCollideAt`, lines 388-447; `PlayerEntity.java`::`shouldSuffocate`, lines 1420-1422. Hashes in source manifest.
- State producers/writers -> consumers/readers: pose/dimensions and block state -> suffocation tests -> chosen neighbor -> player velocity write.
- Parent slices / dependencies / closure evidence: pose dimension and block collision semantics open; D-COLLISION-SHAPES,D-ARTIFACT-INTEGRITY.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F012; A tests one block and above-block solidity for non-swimming; B scans vertical body range and uses view-blocking predicate. Calls are immediately before sprint work and superclass travel.
- Finding IDs or checked absence/replacement path: F012.
## Dependency queue and blockers

- D-ARTIFACT-INTEGRITY: coordinator/source owner reported a 1.13.2 Feather derived-artifact cache replacement incident. Current readiness/source/artifact/diagnostics manifest hashes and cited mapping JAR/tiny hashes match, but canonical immutability repair and fresh source-owner acceptance have not been published. Do not freeze or accept any A-dependent finding until the owner provides repair protocol and verification; do not alter markers/cache. Owner: source preparation owner/ops.
- D-TICK-CLOSURE: full local tick/pre-travel/travel branches/post-travel; source worker to trace both trees.
- D-COLLISION-SHAPES: world queries, shape providers, registrations, neighbor-state inputs; source worker to enumerate.
- D-MOVEMENT-DATA: effects/attributes/enchantments/equipment/block property producer chains; source worker to trace.
- D-EXTERNAL: player correction/push/piston/mount/launch-item consumers; source worker to enumerate.
- D-INDEPENDENT-AUDIT: reviewer unassigned; coordinator to assign.
- Open dependencies: above.

## Finding index

- [F001](findings/F001-input-scaling-and-underwater-sprint-gate.md) input scaling and underwater sprint gate; candidate pending A artifact-integrity repair.
- [F002](findings/F002-pose-selection-and-collision-aware-resize.md) pose selection and collision-aware resize; candidate pending A artifact-integrity repair.
- [F003](findings/F003-jump-boost-precision.md) Jump Boost precision; candidate pending A artifact-integrity repair.
- [F004](findings/F004-input-acceleration-normalization.md) acceleration normalization; candidate pending A artifact-integrity repair.
- [F005](findings/F005-jump-held-climb-impulse.md) jump-held climb impulse; candidate pending A artifact-integrity repair.
- [F006](findings/F006-ground-acceleration-operation-order.md) ground acceleration arithmetic; candidate pending A artifact-integrity repair.
- [F007](findings/F007-collision-axis-order.md) collision axis order; candidate pending A artifact-integrity repair.
- [F008](findings/F008-movement-zero-displacement-cutoff.md) movement cutoff; candidate pending A artifact-integrity repair.
- [F009](findings/F009-approximate-horizontal-collision-flag.md) collision flag tolerance; candidate pending A artifact-integrity repair.
- [F010](findings/F010-water-travel-climb-impulse.md) water-travel climb impulse; candidate pending A artifact-integrity repair.
- [F011](findings/F011-slow-falling-water-descent-timing.md) slow-falling water descent timing; candidate pending A artifact-integrity repair.
- [F012](findings/F012-local-stuck-block-escape-probe.md) local stuck-block escape probe; candidate pending artifact and collision review.

## Resume checkpoint

- Last completed slice: none; S001-S009 are initial evidence only.
- Next bounded slice and exact files/members/body ranges to open: remaining LivingEntity travel branches; `Entity` axis/step helpers; full LocalClientPlayerEntity tick; pose dimensions/base resize; world collision queries and registered shape providers.
- Outstanding dependencies and owners: D-ARTIFACT-INTEGRITY source owner/ops; D-TICK-CLOSURE/D-COLLISION-SHAPES/D-MOVEMENT-DATA/D-EXTERNAL source worker; D-INDEPENDENT-AUDIT coordinator.
- Current assumptions requiring verification: source preparation exact invocation; full reachability and provider closure.

## Finding snapshots (not pair freeze)

No snapshot has been submitted or accepted. F001-F012 remain candidates while `D-ARTIFACT-INTEGRITY` is open; finding-specific dependency closure and implementation boundaries are also incomplete, and no blind finding reviewer is assigned. No snapshot commit/hash or reviewer decision exists. Pair remains partial; no implementation handoff is ready.

## Implementation reconciliation

- Reconciliation status: pending
- Repository revision inspected: not applicable before freeze.
- Finding -> implementation disposition/evidence: deferred.
- Existing implementation without a frozen source finding: not inspected.
- Coverage gaps routed back to discovery slices: deferred.

## Independent source audit

- Reviewer: unassigned
- Status: pending
- Inventories and call-chain ranges re-walked: none.
- Concrete missed-slice routes: pending.
- Misses routed to slice/finding IDs and owners: pending.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: findings=9 bounded slices (12 deltas); pending inventory closure=7; in-progress=0; compared-no-difference=0; not-applicable=0; blocked=0.
- Required inventory status and evidence: all seven inventories pending; initial paired evidence in S001-S008.
- Accepted finding snapshots: none (no finding handoff ready).
- Open dependencies: D-ARTIFACT-INTEGRITY,D-TICK-CLOSURE,D-COLLISION-SHAPES,D-MOVEMENT-DATA,D-EXTERNAL,D-INDEPENDENT-AUDIT.
- Unresolved gaps and limits: full tick, shapes/resources, modifier chains, external writers, exact prep log and independent audit. First changed release unknown within (1.13.2,1.14.4].
- Evidence/hash/correspondence audit: readiness/source/artifact/diagnostic manifests and cited Java hashes verified; cited body ranges paired; full helper/call graph closure incomplete.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).
