# Discovery: 1.13.2 to 1.14.4

- Run status: partial
- Scope: source-only comparison of direct client player movement; A=1.13.2; B=1.14.4.
- Repository revision and start date: branch `feat/source-discovery-movement-source-1-13-2-1-14-4`, based on `main` at `002137b227676caea77f6832b9f4c8d0b6200bff`; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: Ornithe Feather `feather`, generated namespace `ornithe-feather`; exact mapping artifacts/builds and metadata IDs are readiness-verified.
- Source preparation owner / command / log / readiness marker: read-only records under `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready`; Exact per-version command and provenance are available in read-only `.provenance.json`; success logs are summary-excerpt-only. Source owner published revised immutable derived-artifact snapshot `feather-r1-2026-10-07` for A. Snapshot and unchanged source/raw inputs independently verified; original derived JAR is unavailable and equivalence is unproven. Coordinator reports independent ops audit passed; consumer verification completed by this worker. Original derived-JAR equivalence remains unproven and is retained as a source-provenance limitation. No decompiler run here.
- Toolchain/decompiler/remapper versions and options: Vineflower; Java 25. Repo pins Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1; BuildSrc options `ignoreConflicts=true`, `renameInvalidLocals=true`, `rebuildSourceFilenames=true`, `fixPackageAccess=true`. Provenance records Java 25.0.3+9-LTS, Gradle 9.7.1, Vineflower 1.12.0, ASM 9.10.1, Mapping IO 0.9.1, Gson 2.14.0, Tiny Remapper 0.14.1; `--decompiler-heap=4G`.
- Discovery author(s): source worker.
- Independent reviewer: unassigned.

## Artifact manifest

At initial readiness validation, all listed source and artifact hashes matched (A: 2711 sources, 42 artifacts; B: 3250 sources, 40 artifacts). The source-owner integrity notice led to a canonical immutable A derived-artifact snapshot. Readiness/manifest hashes and cited mapping artifacts were rechecked; original derived-JAR equivalence remains unproven. Roots below are relative to this run folder.

### A — 1.13.2

- Exact requested/resolved/versionMetadataId: 1.13.2 / 1.13.2 / 1.13.2.
- Source root: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`.
- Client SHA-256: `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`.
- Mapping: `ornithe-feather`; `feather-gen2-1.13.2+build.2-mergedv2.jar`, `feather-gen2-1.13.2+build.2.tiny`; mapping jar SHA-256 `317384d4faccc2939c3b14252993d31745eb42aad59f2f0ea78893cbe4e7283b`, tiny mapping SHA-256 `b3fd787448aed2c6e115d965d9edbb437ce47794ba63c7883e9014fd41262f71`. Artifact manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` (42 entries).
- Source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 2711 files / 14,770,525 bytes.
- Diagnostics `movement-diagnostics.txt`, SHA-256 `a89e4e3cefe704b58339a1e342e4d8a0fffe7e49dba3ca18402647b69c398a45`; successful decompile/metadata ID plus method anchors for PlayerEntity.jump/moveRelative, LivingEntity.jump/moveRelative and their mobTick callers (review marker 10; not full coverage proof).
- Revised derived artifact: revision ID `feather-r1-2026-10-07`; immutable `client-ornithe-feather.jar` SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`, independently matched against `artifact.sha256` and `revision.json`. Original derived JAR SHA-256 `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e` is unavailable; no equivalence claim. Revision record says raw inputs/source tree identical (0 source-file differences); this worker reverified all 2711 source entries and all 41 non-derived raw-artifact entries. Verification command `gradlew.bat decompileMinecraft --versions=1.8.9,1.9.4,1.10.2,1.11.2,1.12.2,1.13.2 --mappings=feather --decompiler-heap=4G` (unique staging root and shared artifacts cache). Verification log `staging/verify-feather-1.8.9-to-1.13.2-ac481b5296a44bd2ac40d126187e8b8a/gradle.full.log` SHA-256 `33b732892a03ffac60026663f7266c20b637330db6d480b4260267861bebd40c`, reports success for the six-release batch and 1.13.2 Feather. Coordinator reports independent ops audit passed; original derived-JAR equivalence remains unproven.

### B — 1.14.4

- Exact requested/resolved/versionMetadataId: 1.14.4 / 1.14.4 / 1.14.4.
- Source root: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`.
- Client SHA-256: `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`.
- Derived mapped JAR SHA-256: `5e914421086acc2f244ca34bfd0df68e199dba5539a819a0eed1e4af31bb465c` (all 40 B artifact entries currently hash-match).
- Mapping: `ornithe-feather`; `feather-gen2-1.14.4+build.2-mergedv2.jar`, `feather-gen2-1.14.4+build.2.tiny`; mapping jar SHA-256 `3162806b9fb266d7e6d2c594be8d4cc6c91ddb09b4e55d1b566e5429edbd793f`, tiny mapping SHA-256 `60d4906621c873dadba96425d1a233349c9afa407d09bfc8371202af6a71f25c`. Artifact manifest SHA-256 `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970` (40 entries).
- Source manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`; 3250 files / 16,680,788 bytes.
- Diagnostics `ornithe-feather.movement-diagnostics.txt`, SHA-256 `8b5fba7cae7c0187671005b3261d184cbc2caecf9f8cbfc25b284bb0a89d128a`; reports successful decompile, metadata match and movement body review marker 10 for Entity/LivingEntity/PlayerEntity/ClientPlayerEntity.

Current integrity verification: A source tree matched all 2711/2711 manifest entries; 41/41 non-derived raw artifacts matched the original artifact manifest; the original derived `1.13.2/client-ornithe-feather.jar` is unavailable and the shared cache now has SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c` instead of original `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5`. Immutable revision snapshot, `artifact.sha256`, `revision.json`, and verification log hash all match; B all 40/40 artifact entries match. The source owner makes no equivalence claim for the changed A derived JAR; this is a provenance limitation, not a hash mismatch in the revised snapshot or original source/raw inputs. Revision record SHA-256 is `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`; snapshot sidecar SHA-256 is `4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40625601868692c7dd8`; original source manifest SHA-256 is `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; original artifact manifest SHA-256 is `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`. Coordinator reports independent ops audit passed and this worker completed canonical consumer verification. Cite revision `feather-r1-2026-10-07` in each A-dependent finding; original derived-JAR equivalence remains unproven.

Exact source-owner commands from provenance (success logs are summary excerpts only): A `gradlew.bat decompileMinecraft --versions=1.13.2 --mappings=feather --decompiler-heap=4G`; B `gradlew.bat decompileMinecraft --versions=1.14.4 --mappings=feather,mojmap --decompiler-heap=4G`; each used its published staging output root and shared cache `build/movement-campaign-2026-10-07/artifacts`.

Cited Java files and whole-file SHA-256:

- `net/minecraft/client/entity/living/player/KeyboardInput.java`: A `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`; B `5932453a9e48e7a798ae1be1cd3a4bf660b6b3be43bb7e5dc22686b3c4a82526`.
- `net/minecraft/client/entity/living/player/Input.java` (B): `acb63d46fb6e6a1cb6b285a5d0702fb62575f7d54344ad1f0f9d51e8f671f33f`.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: A `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`; B `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce`.
- `net/minecraft/entity/living/LivingEntity.java`: A `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`; B `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.
- `net/minecraft/entity/Entity.java`: A `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`; B `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`.
- `net/minecraft/entity/living/player/PlayerEntity.java`: A `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`; B `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df`.
- `net/minecraft/util/math/MathHelper.java` (B): `e39d5dfc69c17a9032be086d7d4d14cc63525962a0ff83615b1befdcca599843`.
- `net/minecraft/util/math/Vec3d.java`: A `6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5`; B `b69b990b768e52d6fac6b3ed82f60e7526e78d0837c868a61a4171ebca21040b`.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: none.
- Evidence inventory and finding IDs included at freeze: none.
- Confirmation old mod implementation and isolated wiki-audit results were not opened before freeze: confirmed; no wiki browsing.
- Source/mapping hashes covered by freeze: pending.

## Correspondence and call order

Resolved pairs include `LocalClientPlayerEntity.mobTick`, `KeyboardInput.tick` (no-arg to boolean args), `LivingEntity.jump`, `LivingEntity.moveRelative` (three floats to Vec3d), `Entity.updateVelocity` (float components to vector helper), `Entity.move` (three doubles to vector), and `PlayerEntity.updatePlayerPose`. Full local tick, superclass, pre-travel, all dispatch branches and post-travel call chain are not closed. S001-S028 contain bounded paired evidence; no stage label alone is treated as coverage.

## Required source inventories

- `INV-TICK` status=pending; slice_ids=S001,S003,S004,S005,S007,S008,S009,S010,S019; evidence=paired client input/local player/living bodies below; complete tick call graph open.
- `INV-STATE` status=pending; slice_ids=S001,S002,S003,S006,S008,S009,S010,S018; evidence=paired input, pose, jump, movement and client correction velocity bodies below.
- `INV-COLLISION` status=pending; slice_ids=S002,S006,S009,S019,S020,S021,S023,S024,S025,S026,S027,S028; evidence=pose fit, escape probes, entity move, paired collision enumerators, static providers, wall categories, and wall/fence/iron-bars fixed-state geometry; remaining provider enumeration open.
- `INV-WORLD-MOVEMENT` status=pending; slice_ids=S005,S007,S026,S027,S028; evidence=slipperiness/climbing consumers and fixed-state fence/iron-bars collision geometry; other providers and resources remain open.
- `INV-MODIFIERS` status=pending; slice_ids=S003,S005; Jump Boost/Slow Falling consumers observed; producer/application chains open.
- `INV-EXTERNAL` status=pending; slice_ids=S006,S009,S010,S012,S016,S017,S018,S019,S022,S026; direct player velocity/impulse/knockback remains in scope, including reachable push response; S018 compares the client player-correction velocity reset and S019 inventories entity obstacles in the player auto-jump scan; S026 confirms remote tag synchronization as a block-state input; other correction paths, knockback sources, piston, mount and launch-item consumers remain open; exclude non-player/vehicle physics and combat cause/damage resolution.
- `INV-EXCLUSIONS` status=pending; evidence=scope exclusions declared; direct-read inventory open. Exclude health/food state production and attack/damage resolution; direct player velocity/impulse/knockback response remains in scope even if combat can trigger it; exclude non-player and vehicle physics. Food/blindness may remain movement-predicate inputs without emulating their producers.

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
- Parent slices / dependencies / closure evidence: pose dimension and block collision semantics open; D-COLLISION-SHAPES.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F012; A tests one block and above-block solidity for non-swimming; B scans vertical body range and uses view-blocking predicate. Calls are immediately before sprint work and superclass travel.
- Finding IDs or checked absence/replacement path: F012.
### Slice S010: local flight sneak/jump vertical input

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: flight branch guarded by abilities.flying and local camera, with sneaking/jumping vertical input.
- A evidence: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`::`mobTick`, lines 797-806, SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.
- B evidence: same file/member, lines 728-743, SHA-256 `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce`.
- State producers/writers -> consumers/readers: flight ability/camera/input/synced fly speed -> velocityY; player input then reaches superclass travel.
- Parent slices / dependencies / closure evidence: fly-speed ability provenance and synchronization open; D-EXTERNAL.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F013; simultaneous sneak+jump executes sequential subtract/add in A but nets B's integer adjustment to zero, skipping the vertical velocity update. Float/double roundoff consequence is not measured.
- Finding IDs or checked absence/replacement path: F013.
### Slice S011: land-travel Levitation consumer

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: ordinary non-fluid/non-glide travel after move; Levitation branch, fallDistance reset, and vertical damping.
- A evidence: `net/minecraft/entity/living/LivingEntity.java`::`moveRelative(float,float,float)`, lines 1575-1599, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- B evidence: `net/minecraft/entity/living/LivingEntity.java`::`moveRelative(Vec3d)`, lines 1846-1867, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.
- State producers/writers -> consumers/readers: Levitation amplifier/current vertical velocity -> post-move vertical adjustment and fallDistance reset -> 0.98 vertical damping.
- Parent slices / dependencies / closure evidence: effect registration, application, amplifier/lifetime and attribute sources open; D-MOVEMENT-DATA.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): Within this land-travel branch, both sides use `vY += (0.05 * (amplifier + 1) - vY) * 0.2`, reset fallDistance, and apply 0.98 vertical damping after the effect branch. A uses the velocity field directly and B a local vector component; the expression's ordering matches. This is limited to the consumer body.
- Finding IDs or checked absence/replacement path: none within the bounded consumer slice; producer closure remains pending.
### Slice S012: client velocity and explosion packet consumers

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: client entity-velocity packet application for the addressed entity; local-player explosion velocity addition after block processing.
- A evidence: `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`::`handleEntityVelocity`, lines 506-513, and `handleExplosion`, lines 967-977, SHA-256 `4f65502fbee6476cef0838563f03a66f07a1a9ed8c63e580f5338ba5fe274f11`; `Entity.java`::`lerpVelocity`, lines 1730-1734, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- B evidence: `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`::`handleEntityVelocity`, lines 536-543, and `handleExplosion`, lines 1043-1053, SHA-256 `6c840580285c7c7d4617e895edac82d4eebf8289dd4994e7b593cebeb49b43ef`; `Entity.java`::`lerpVelocity`/`m_32166403`, lines 1752-1754 and 2654-2656, SHA-256 `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`.
- State producers/writers -> consumers/readers: server-supplied packet entity ID/velocity components -> player/entity velocity fields; explosion packet's player velocity delta -> local player velocity.
- Parent slices / dependencies / closure evidence: server-provided values remain external; packet/correction and other push/knockback/mount/piston callers remain open; D-EXTERNAL.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): Both velocity handlers queue on the client thread, lookup the addressed entity, and if non-null call `lerpVelocity` with each packet component divided by `8000.0`. A writes those components to velocity fields; B's helper stores the equivalent Vec3d. Both explosion handlers construct/process the packet explosion then add the packet-provided player velocity componentwise. This is limited to these two client packet consumers; it does not inspect attack/damage cause or server-side knockback calculation.
- Finding IDs or checked absence/replacement path: none within these paired consumers.
### Slice S013: local-player tick dispatch and sleeping pitch edge

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: client LocalClientPlayerEntity.tick -> LivingEntity.tick -> virtual LocalClientPlayerEntity.mobTick -> LivingEntity.mobTick travel, plus LivingEntity post-mobTick tail.
- A evidence: `LocalClientPlayerEntity.java`::`tick`, lines 181-198, SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`; `LivingEntity.java`::`tick`, lines 1689-1825, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`; `LivingEntity.java`::`mobTick`, lines 1852-1936, same hash.
- B evidence: `LocalClientPlayerEntity.java`::`tick`, lines 178-196, SHA-256 `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce`; `LivingEntity.java`::`tick`, lines 1979-2123, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`; `LivingEntity.java`::`mobTick`, lines 2150-2237, same hash.
- State producers/writers -> consumers/readers: client tick dispatch -> LocalClientPlayerEntity input/state work -> superclass living movement; pitch state is postprocessed after travel.
- Parent slices / dependencies / closure evidence: full tick branches and movement inputs remain open; `D-TICK-CLOSURE`, `D-MOVEMENT-DATA`.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F014; tick dispatch order is structurally aligned. B adds a client-reachable `if (isSleeping()) pitch = 0.0F` after `mobTick` and walk/fall-flying updates; A has no counterpart. Server-only sleeping cleanup in B is excluded from the local client branch.
- Finding IDs or checked absence/replacement path: F014.

### Slice S014: ordinary ground travel resampling and climb helper

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: non-water, non-lava, non-fall-flying ordinary travel; slipperiness read before acceleration and after movement; climb clamping and post-move impulse.
- A evidence: `LivingEntity.java`::`moveRelative(float,float,float)`, lines 1537-1578, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- B evidence: `LivingEntity.java`::`moveRelative(Vec3d)`, lines 1840-1866, and helpers `m_19439509` / `m_70235197`, lines 1944-1963, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.
- State producers/writers -> consumers/readers: onGround and block below current shape -> slipperiness/acceleration -> movement/collision -> post-move velocity damping; climb state and input -> clamp/impulse.
- Parent slices / dependencies / closure evidence: F006 acceleration formula and F005 climb impulse are parent findings; block-property assignments and transition reachability remain open; `D-MOVEMENT-DATA`, `D-TICK-CLOSURE`.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F015 records that A re-reads onGround and slipperiness after movement before horizontal damping, while B reuses the pre-move `w`. This can differ when movement changes support or the block under the player. B's extra scaffolding exclusion in sneak-climb logic is not treated as a historical movement finding because scaffolding did not exist in A's target era.
- Finding IDs or checked absence/replacement path: F015; scaffolding predicate excluded by target-era feature scope.
### Slice S015: fall-flying movement calculation

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: `isFallFlying()` branch within `moveRelative`, look projection, glide velocity adjustments, damping, and SELF move; server-only collision damage excluded.
- A evidence: `LivingEntity.java`::`moveRelative(float,float,float)`, lines 1488-1536, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- B evidence: `LivingEntity.java`::`moveRelative(Vec3d)`, lines 1797-1839, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`; `Entity.java`::`m_23572928`, lines 688-690, SHA-256 `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`.
- State producers/writers -> consumers/readers: fall-flying flag, look vector, pitch, current velocity, and Slow Falling gravity scale -> glide acceleration/projection/damping -> Entity.move.
- Parent slices / dependencies / closure evidence: Elytra eligibility/equipment and state synchronization remain open; `D-TICK-CLOSURE`, `D-EXTERNAL`, `D-MOVEMENT-DATA`.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): In the bounded movement path both versions set fallDistance at the same velocity threshold, calculate look-vector horizontal magnitude and current horizontal speed, apply the same pitch-dependent vertical and horizontal terms in order, damp with `(0.99F,0.98F,0.99F)`, and call SELF movement. A uses scalar fields; B uses Vec3d additions/components. No movement difference is identified in this consumer slice.
- Finding IDs or checked absence/replacement path: none within this bounded branch; equipment/eligibility producers and other tick behavior remain open.
### Slice S016: direct living-player knockback response

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.applyKnockback` resistance guard, horizontal impulse normalization/application, and grounded vertical response; callers and attack/damage calculations excluded.
- A evidence: `LivingEntity.java`::`applyKnockback(Entity,float,double,double)`, lines 1011-1027, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`; `Vec3d.java`::`normalize`, lines 25-28, SHA-256 `6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5`.
- B evidence: `LivingEntity.java`::`applyKnockback(Entity,float,double,double)`, lines 1208-1215, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`; `Vec3d.java`::`normalize`, lines 25-28, SHA-256 `b69b990b768e52d6fac6b3ed82f60e7526e78d0837c868a61a4171ebca21040b`.
- State producers/writers -> consumers/readers: knockback resistance/random guard and incoming horizontal vector/current velocity/onGround -> direct velocity write -> later movement.
- Parent slices / dependencies / closure evidence: cause/caller inventory intentionally excluded; direct player response is in scope; `D-EXTERNAL`, `D-MOVEMENT-DATA`.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F016; A divides by a float horizontal magnitude with no zero-length guard. B normalizes the vector in double and returns `Vec3d.ZERO` for length below `1.0E-4`. Grounded vertical response also uses A's float cap `0.4F` versus B's double `0.4` in `Math.min`. Caller reachability, resistance attributes, and probability guard remain open.
- Finding IDs or checked absence/replacement path: F016; no attack/damage calculation inspected.
### Slice S017: sleeping guard on player entity push

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: player `push(Entity)` sleeping guard and delegation to the superclass push implementation.
- A evidence: `PlayerEntity.java`::`push(Entity)`, lines 2016-2022, SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`.
- B evidence: inherited `LivingEntity.java`::`push(Entity)`, lines 1667-1671, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.
- State producers/writers -> consumers/readers: sleeping state and collision push callback -> conditional delegation to base Entity push response.
- Parent slices / dependencies / closure evidence: caller enumeration and base push response remain open; `D-EXTERNAL`.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): Both versions suppress the push callback while the player is sleeping and otherwise delegate to the superclass. B moves the matching override to LivingEntity while PlayerEntity also retains the same guard; no changed player push guard is identified in this bounded slice.
- Finding IDs or checked absence/replacement path: none within the sleeping guard; push callers remain open.

### Slice S018: client player correction velocity reset

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: client `handlePlayerMove(PlayerMoveS2CPacket)` after same-thread packet dispatch; per-axis relative position flags, velocity clearing/preservation, relative rotation, and position/angle application. Server packet production and render interpolation are outside this movement slice.
- A evidence: `ClientPlayNetworkHandler.java`::`handlePlayerMove`, lines 621-671, SHA-256 `4f65502fbee6476cef0838563f03a66f07a1a9ed8c63e580f5338ba5fe274f11`; `Entity.java`::`updatePositionAndAngles`, lines 1096-1119, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- B evidence: `ClientPlayNetworkHandler.java`::`handlePlayerMove`, lines 652-713, SHA-256 `6c840580285c7c7d4617e895edac82d4eebf8289dd4994e7b593cebeb49b43ef`; `Entity.java`::`updatePositionAndAngles`, lines 1098-1121, and velocity helpers `m_94091929` / `m_32166403`, lines 2646-2656, SHA-256 `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`.
- State producers/writers -> consumers/readers: server-supplied packet coordinates and relative-axis set -> local player position/rotation; absolute axes clear the corresponding current velocity component, relative axes preserve it -> next local movement tick.
- Parent slices / dependencies / closure evidence: packet values remain external inputs; direct client velocity reset and position helper semantics are paired; `D-EXTERNAL` remains open for other correction paths and external writers.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): Both handlers apply on the client thread to `minecraft.player`. Each adds packet coordinates to current coordinates on relative axes and uses absolute packet coordinates otherwise; both apply relative yaw/pitch values and call `updatePositionAndAngles` with the resulting values. A writes zero to each velocity field whose position axis is absolute and leaves relative-axis velocity unchanged. B reads the velocity vector, zeroes those same components, and writes the resulting vector through a helper that stores the three components directly. The paired position helpers perform the same coordinate clamp, last-position update, rotation clamp and assignment, and shape-position refresh. B additionally updates `prevX/Y/Z` for interpolation; those values feed presentation paths and are excluded by the campaign's movement-only scope. No difference is identified in the bounded correction velocity or position application.
- Finding IDs or checked absence/replacement path: none within the bounded player-correction consumer; other packet correction and direct-motion paths remain open.

### Slice S019: local-player auto-jump collision query

- Inventory ID(s): INV-TICK, INV-COLLISION, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: `LocalClientPlayerEntity.move` calls the local auto-jump probe after superclass movement; compare the entity-obstacle query used by the scan and separate block collision-context behavior.
- A evidence: `LocalClientPlayerEntity.java`::`move(MoverType,double,double,double)`, lines 871-875, and `autoJump(float,float)`, lines 882-980, SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`; `WorldView.java`::`getCollisions(Entity,Box)`, lines 166-167, SHA-256 `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`; `World.java`::`getCollisions(Entity,VoxelShape,VoxelShape,Set)`, lines 1755-1757, SHA-256 `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a`; `EntityView.java`::`getEntityCollisions(Entity,VoxelShape,Set)`, lines 21-34, SHA-256 `2b5b59eef6efa93bb51c1d838c7a1cf64725be96c55c47f76894fde11e13d6de`.
- B evidence: `LocalClientPlayerEntity.java`::`move(MoverType,Vec3d)`, lines 846-852, and `autoJump(float,float)`, lines 857-959, SHA-256 `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce`; `WorldView.java`::`getCollisions(Entity,Box,Set)` lines 129-130 and `getBlockCollisions(Entity,Box)` lines 133-187, SHA-256 `fcdf8dc98f97781f4639c4b8cda8a238edf834f3963e401e6eeef6cb475c4909`; `WorldAccess.java`::`getEntityCollisions(Entity,Box,Set)`, lines 75-76, SHA-256 `f57639756acdedbd0e5a598f7740a8e17f0dc3b2ba710aa4690d05f4f3bcd746`; `EntityView.java`::`getEntityCollisions(Entity,Box,Set)`, lines 53-64, SHA-256 `3822844ced53882c52f0523a1bd714baa3cd9bb9dab38a5e2c53051c7b1590e3`.
- State producers/writers -> consumers/readers: horizontal displacement after resolved player movement, auto-jump enable/timer/ground/sneak/ride/input gates, block and entity collision shapes -> `ticksToNextAutojump` -> synthesized jump input on a later local-player tick.
- Parent slices / dependencies / closure evidence: parent entity movement slices S007-S009; block and entity collision-source closure remains open under `D-COLLISION-SHAPES`, and complete local tick reachability remains open under `D-TICK-CLOSURE`.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F017. The same enabled, timer-expired, grounded, non-sneaking, unmounted, nonzero-input conditions reach the post-move scan. A queries entities in the shape bounds expanded by `0.25` but retains only collision shapes intersecting the original bounds. B queries entities in a `1.0E-7`-expanded box and retains shapes intersecting that expanded box; its `Collections.emptySet()` matches A's default empty exclusion set. Thus candidate sets can differ at the epsilon boundary or when a collision shape extends beyond the entity-query bounds. Both versions include entity obstacles in addition to block shapes. B's block collision context is passed into shape providers; the only context-reading collision-shape provider identified is 1.14-only scaffolding, excluded as modern-only. Full common-era provider and boundary reachability remains conditional.
- Finding IDs or checked absence/replacement path: F017; scaffolding context behavior excluded as a modern-only block.

### Slice S020: world block-collision enumeration

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: paired block-shape query invoked by player collision resolution and auto-jump; compare bounds, position traversal, chunk/world-border guards, and shape stream construction.
- A evidence: `WorldView.java`::`getBlockCollisions(VoxelShape,VoxelShape,boolean)`, lines 112-151, SHA-256 `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`; `World.java`::`getCollisions(Entity,VoxelShape,VoxelShape,Set)`, lines 1755-1757, SHA-256 `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a`.
- B evidence: `WorldView.java`::`getCollisions(Entity,Box,Set)`, lines 129-130, and `getBlockCollisions(Entity,Box)`, lines 133-187, SHA-256 `fcdf8dc98f97781f4639c4b8cda8a238edf834f3963e401e6eeef6cb475c4909`; `C_29205863.java`::`m_97468855`, lines 24-48, SHA-256 `d15373ef21f58ed67755269d32b2dd5ff65eb33dd4eea33e854b505b9ac9afd`.
- State producers/writers -> consumers/readers: player query box/entity context, block states and chunk status, world-border state -> collision-shape stream -> displacement resolution/clipping/step and auto-jump obstacle scan.
- Parent slices / dependencies / closure evidence: S007-S009 and S019; D-COLLISION-SHAPES and D-TICK-CLOSURE remain open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F018. A traverses floor(min)-1 through ceil(max)+1 (exclusive upper iterator endpoint), skips selected outer corners, checks chunk-loaded state, aggregates full blocks, and uses the world-border mode flag. B uses epsilon-adjusted bounds and inclusive endpoints, chunk-status lookup, collision-shape flags, entity context, and separately emits border and block shapes. Exact integral query bounds can visit an extra outer cell in B; whether any extra or differently constructed shape intersects a reachable player query for a common-era block remains conditional.
- Finding IDs or checked absence/replacement path: F018; no common-era provider outcome established.

### Slice S021: shared static collision-shape providers

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: A-era cactus, fence-gate, snow-layer, and soul-sand collision-shape constants and the methods selecting them.
- A evidence: `CactusBlock.java` lines 23-24, 60-62, SHA-256 `d337d776966b6a50070a5ed79ec8cbabb064e15b7b2d6b8e4992a66ccea5e8c9`; `FenceGateBlock.java` lines 26-27, 67-73, SHA-256 `e251cbd1b5e06c42e856a1d537e4ba043f6760b8f54ea1388039428aa7c05164`; `SnowLayerBlock.java` lines 31-41, 78-80, SHA-256 `12b2d50064fd306c09007bc9a3629cc14a540123ce37e3b53a7f6615c9915df2`; `SoulSandBlock.java` lines 14-23, SHA-256 `259645f4b6dbc7b71481756c8fcd804d7bd3dadb679d09f06284ffb9d145b5be`.
- B evidence: `CactusBlock.java` lines 24-25, 61-63, SHA-256 `3e5d6d42cef91cd8bab1b2a96589f80f6d164fea8f0b286a0589cbf211d2f67b`; `FenceGateBlock.java` lines 29-30, 70-76, SHA-256 `c6c869ac12993e09f4684798e843778b2b63b7d9766f9cac0894dae0f312a151`; `SnowLayerBlock.java` lines 23-33, 60-62, SHA-256 `2307bd4ad1b41210c8c82fddad03dea2e6867b87e7539b9249a6ed49960f6dcb`; `SoulSandBlock.java` lines 16-25, SHA-256 `fd8afe4294317c439f7a8c90143aea1a59491c746d9fe1645db6094e7c53f21f`.
- State producers/writers -> consumers/readers: vanilla block state, fence-gate open/facing fields, snow layer count -> constant shape selection -> world collision query.
- Parent slices / dependencies / closure evidence: S020 paired block enumeration; D-COLLISION-SHAPES remains open for dynamic/shared providers, cached defaults, neighbor-state derivation, and the remaining A-era shape provider set.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the four paired providers use the same collision geometry: cactus inset from the unit block with a 15/16 height, fence gate oriented bars extending to height 24/16 when closed and empty when open, snow layers indexed by the same 2/16 increments, and soul sand at 14/16 height. Their direct shape selectors and constants match. The comparison does not cover other methods in these source files.
- Finding IDs or checked absence/replacement path: none within this bounded static-provider slice; no modern-only provider was treated as a difference.
### Slice S022: moving-piston source shape selection

- Inventory ID(s): INV-COLLISION, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: `MovingBlockEntity.getStateForShape` collision-shape selection for piston-driven entity movement; compare the new B source-state guard and the piston construction paths that set `source`.
- A evidence: `MovingBlockEntity.java`::constructor and `getStateForShape`, lines 42-48, 91-98, SHA-256 `6684ccc05adf8b29cac55f9796133405c6d4f6853051e651895303b657cd1c46`; `PistonBaseBlock.java`, source creation at lines 194, 313, 326, SHA-256 `1722edaf96c15af587b4e169b62c5af1d352800e82a2070e1f4af86bdde4cb31`.
- B evidence: `MovingBlockEntity.java`::constructor and `getStateForShape`, lines 44-50, 93-100, SHA-256 `e03c4123c06309c04dbc039c6f276f1bf988018a4f13cc329bd6d07acc6bc54a`; `PistonBaseBlock.java`, source creation at lines 195, 310, 323, SHA-256 `37409c9f2390642103155dcec3e922e0d387e9d0bbca069fb334277d5d84a058`.
- State producers/writers -> consumers/readers: piston action creates moving block entity with moved state, extending and source flags -> `getStateForShape` -> piston collision box used by `moveEntities` -> entity displacement/velocity response.
- Parent slices / dependencies / closure evidence: S020 block query and S021 static providers; D-COLLISION-SHAPES and D-EXTERNAL remain open for other block providers and external player-motion writers.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): B adds `movedState.getBlock() instanceof PistonBaseBlock` to A's `!isExtending() && isSource()` test. Both PistonBaseBlock creation sites set source=true for a non-extending moved piston-base state during retraction, source=false for ordinary moved blocks, and source=true with a piston-head state for extension (where `!isExtending()` is false). The constructor stores those inputs unchanged. Thus the added guard is redundant on the paired normal construction paths, and `getStateForShape` returns the same collision state for those reachable cases. Loaded-state corruption or nonstandard external construction is not treated as a normal player movement path.
- Finding IDs or checked absence/replacement path: none on the inspected PistonBaseBlock construction paths.
### Slice S023: wall neighbor connection predicate

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: A/B wall placement and neighbor updates recompute horizontal connection flags; compare predicate inputs and the downstream wall collision-shape selector.
- A evidence: `WallBlock.java`::`shouldConnectTo`, `getPlacementState`, `updateShape`, `getCollisionShape`, lines 37-38, 51-55, 69-115, SHA-256 `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`; `BlockState.getFaceShape`, lines 285-287, SHA-256 `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70`; `Block.getFaceShape`, lines 383-385, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`.
- B evidence: `WallBlock.java`::`m_71853799`, `getPlacementState`, `updateShape`, `getCollisionShape`, lines 39-40, 48-52, 55-102, SHA-256 `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7`; `BlockState.m_87223014`, lines 326-328, SHA-256 `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f`; `Block.isFaceSolid`, lines 402-409, SHA-256 `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`.
- State producers/writers -> consumers/readers: neighboring vanilla block state/face shape or collision-face/cache/tag result -> wall horizontal flags and `UP` -> wall collision-shape array -> local player collision query.
- Parent slices / dependencies / closure evidence: S020-S021 block query/static provider slices; D-COLLISION-SHAPES remains open for the A-era face-shape providers and B tag/cache paths.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F019. A accepts `MIDDLE_POLE_THICK`, `MIDDLE_POLE` for a fence gate, or a non-exempt `SOLID` face. B accepts a non-exempt full collision face, a block tagged as a wall, or a direction-compatible fence gate. For vanilla TNT and frosted ice, A rejects the connection via `isExceptionForConnection` while B sees a full collision face and does not exclude either block; placement/update therefore changes the direction flag and collision-shape selection. Player displacement/clipping remains unmeasured; other providers remain open.
- Finding IDs or checked absence/replacement path: F019; source-confirmed flag/shape selection difference for TNT and frosted-ice adjacency; modern-only block types excluded.
### Slice S024: special wall-neighbor shape categories

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: the A wall predicate's `MIDDLE_POLE_THICK` and fence-gate `MIDDLE_POLE` cases, paired with B wall-tag and direction-compatible gate cases.
- A evidence: `WallBlock.java` lines 51-55, 127-129, SHA-256 `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`; `FenceGateBlock.java` lines 159-165, SHA-256 `e251cbd1b5e06c42e856a1d537e4ba043f6760b8f54ea1388039428aa7c05164`.
- B evidence: `WallBlock.java` lines 48-52, SHA-256 `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7`; `FenceGateBlock.java` lines 154-156, SHA-256 `c6c869ac12993e09f4684798e843778b2b63b7d9766f9cac0894dae0f312a151`; `BlockTagsProvider.java` lines 270-281, SHA-256 `0bd051d11a0c85ecc61cd2202a6ed33923db97238da0b065a647b932132d1bfa`.
- State producers/writers -> consumers/readers: neighboring wall or gate block/facing -> wall connection predicate -> directional state flag -> collision-shape selector.
- Parent slices / dependencies / closure evidence: S023 wall predicate; D-COLLISION-SHAPES remains open for solid-face providers, exception/cache interactions, and other A-era shapes.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for the A-era wall blocks `COBBLESTONE_WALL` and `MOSSY_COBBLESTONE_WALL`, A reports `MIDDLE_POLE_THICK` and B's `WALLS` tag lists both. For fence gates, A's `MIDDLE_POLE` branch checks that the facing axis equals `face.clockwiseY().getAxis()`; B's `m_26120570` checks the same axes against `direction.clockwiseY().getAxis()`. These special wall/gate inputs therefore have equivalent connection results in the inspected normal states. The review does not close the separate SOLID-face predicate.
- Finding IDs or checked absence/replacement path: none within this bounded wall/gate mapping slice; F019's TNT/frosted-ice exception difference remains.

### Slice S025: wall connection beside a retracted piston

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: A `PistonBaseBlock.getFaceShape` for retracted states and `Block.isExceptionForAttachment` through `WallBlock.isExceptionForConnection`; B piston collision-shape provider, `Block.isFaceSolid` and attachment exception, plus `BlockState.m_87223014` cached/fallback paths. The wall placement and neighbor-update consumers pass the face opposite the wall direction into the connection predicate.
- A evidence: `PistonBaseBlock.java`::`getFaceShape`, lines 366-369, SHA-256 `1722edaf96c15af587b4e169b62c5af1d352800e82a2070e1f4af86bdde4cb31`; `Block.java`::`isExceptionForAttachment`, lines 255-257, same file hash `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`; `WallBlock.java`::`shouldConnectTo` and `isExceptionForConnection`, lines 51-62, SHA-256 `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`; `BlockState.java`::`getFaceShape`, lines 285-287, SHA-256 `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70`; inherited `PaneBlock.makeShapes` and `getShapeIndex`, lines 38-108, SHA-256 `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c`.
- B evidence: `PistonBaseBlock.java`::`getShape`, lines 51-71, SHA-256 `37409c9f2390642103155dcec3e922e0d387e9d0bbca069fb334277d5d84a058`; inherited `Block.getCollisionShape`, lines 376-378, and `Block.isFaceSolid`, lines 402-409, SHA-256 `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`; `Block.isExceptionForAttachment`, lines 260-267, same hash; `BlockState.m_87223014`, lines 326-328, and its face-solid cache initialization, lines 410-417, SHA-256 `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f`; `WallBlock.java`::`m_71853799`, `getPlacementState`, `updateShape`, and `getCollisionShape`, lines 39-40, 48-52, 55-104, SHA-256 `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7`; inherited `PaneBlock.makeShapes` and `getShapeIndex`, lines 40-115, SHA-256 `7150e89268a84113453cd5416ffe25e1ca7d42a39f264f7ae989878a10ee2158`.
- State producers/writers -> consumers/readers: vanilla piston `FACING`/`EXTENDED` state -> A face-shape result or B piston collision shape and face-solid cache -> wall placement/update direction flag -> wall collision-shape array -> world block-collision query used by player movement (S020).
- Parent slices / dependencies / closure evidence: S020 paired world block-collision query, S023 wall predicate, and S024 wall/gate categories; D-COLLISION-SHAPES remains open for other A-era face-shape providers, B tags/cache paths, panes and inherited shape providers.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): when a vanilla piston base is retracted (`EXTENDED=false`) and a wall is placed beside it or receives the corresponding neighbor update, A's piston override reports `FaceShape.SOLID`, but A's `Block.isExceptionForAttachment` includes `PISTON` and `STICKY_PISTON`; `WallBlock.isExceptionForConnection` delegates to that helper, so the wall flag stays false. B's retracted piston `getShape` returns `VoxelShapes.block()`. The inherited collision-shape method returns that shape, and the cached or fallback `Block.isFaceSolid` path reports a full face. B's attachment-exception list omits piston blocks, so the wall flag becomes true. In both versions, inherited `PaneBlock.getShapeIndex` maps the directional flag to the wall collision-shape array index; the added direction contributes that arm to the returned shape. This is another concrete common-era instance of F019's wall-connection predicate change, not a separate root finding.
- Finding IDs or checked absence/replacement path: F019; no second finding filed for the same wall-predicate change.

### Slice S026: fence neighbor connection predicate

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: A `FenceBlock.shouldConnectTo(Block, FaceShape)`, four placement flag writes and horizontal `updateShape`; B paired `shouldConnectTo(BlockState, boolean, Direction)`, placement/update calls, full-face predicate, and `FENCES` tag input. Also checked the inherited fence-gate face predicate against B's directional gate helper.
- A evidence: `FenceBlock.java`::`shouldConnectTo`, `getPlacementState`, `updateShape`, lines 46-60, 76-108, SHA-256 `abefbedea10306abf602d4e379a4793b415ca7635231752cd9f18a80aced5fc2`; `Block.java`::`isExceptionForPlacement`/`isExceptionForAttachment`, lines 241-256, and default `getFaceShape`, lines 383-385, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`; oak and nether-brick fence registrations at lines 1075 and 1211 in the same file; `FenceGateBlock.getFaceShape`, lines 159-165, SHA-256 `e251cbd1b5e06c42e856a1d537e4ba043f6760b8f54ea1388039428aa7c05164`.
- B evidence: `FenceBlock.java`::`shouldConnectTo`, `getPlacementState`, `updateShape`, lines 43-48, 61-93, SHA-256 `4319ffb95ad81e969b11b39a8e6361d678b5fb5f3ad210d9834ce5e30d5f45d8`; `Block.isFaceSolid`, lines 402-409, and `Block.isExceptionForAttachment`, lines 260-267, SHA-256 `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`; oak and nether-brick fence registrations at lines 574 and 744 of `Blocks.java`, SHA-256 `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`; `FenceGateBlock.m_26120570`, lines 154-156, SHA-256 `c6c869ac12993e09f4684798e843778b2b63b7d9766f9cac0894dae0f312a151`; `BlockTags.FENCES`, line 43, SHA-256 `dc59d07204b25e5d852d0384e849975367590a3cbc2b9a293396d0bf5637e52a`.
- B resource evidence: original `artifacts/1.14.4/client.jar` (SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`) entry `data/minecraft/tags/blocks/fences.json`, SHA-256 `8c4ebabbf6d088340c70c29091eadfd94d11cd551b193af52ea06cc073bc7483`, lists `#minecraft:wooden_fences` and `minecraft:nether_brick_fence`; referenced entry `data/minecraft/tags/blocks/wooden_fences.json`, SHA-256 `6c7f827ceb45ad6b6b7df14e8871908b2a17f904ec3c03cb3551ef48acdf188f`, lists oak, acacia, dark-oak, spruce, birch and jungle fences. B `ClientPlayNetworkHandler.handleTags`, lines 1469-1476, SHA-256 `6c840580285c7c7d4617e895edac82d4eebf8289dd4994e7b593cebeb49b43ef`, installs packet block tags for non-local connections; these resource values are vanilla defaults, while remote tag replacement is server-supplied.
- State producers/writers -> consumers/readers: neighbor block state and face classification -> `NORTH`/`EAST`/`SOUTH`/`WEST` writes in fence placement/update -> pane-shape index -> collision shape -> world block-collision query (S020). Remote `FENCES` tag packets can also affect the B predicate.
- Parent slices / dependencies / closure evidence: S020 player world block-collision query, S023/S025 wall predicate examples, and S027 fixed-state geometry; D-COLLISION-SHAPES, D-MOVEMENT-DATA and D-EXTERNAL remain open.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): source confirms a fence-state transition difference, but it is block-state lifecycle and is excluded by this assignment. For example, A accepts the default `FaceShape.SOLID` neighbor for an oak fence next to a nether-brick fence because neither is in its exception list. B's inherited fence collision shape does not fill the neighbor face; both blocks are in the default `FENCES` tag, but their registered materials are `WOOD` and `STONE`, so the same-material tag branch is false and the fence gate branch does not apply. A therefore writes the directional flag true and B false. The A/B fence collision geometry for a fixed set of directional flags is handled separately in S027; this state-writer difference is retained as evidence, not a player-movement finding or implementation handoff. For fence gates, A's `FACING.axis == face.clockwiseY.axis` face classification and B's `FACING.axis == direction.clockwiseY.axis` helper match for the paired callers.
- Finding IDs or checked absence/replacement path: none; excluded block-state lifecycle evidence. Continue tracing synchronized tags under D-MOVEMENT-DATA/D-EXTERNAL.

### Slice S027: fence collision geometry for fixed connection flags

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: A/B `FenceBlock` constructor shape parameters, inherited `PaneBlock.makeShapes`, `getCollisionShape`, and directional `getShapeIndex`; compare geometry and direction-bit mapping with identical stored fence flags.
- A evidence: `FenceBlock.java` class/constructor, lines 22-29, SHA-256 `abefbedea10306abf602d4e379a4793b415ca7635231752cd9f18a80aced5fc2`; inherited `PaneBlock` constructor, `makeShapes`, `getCollisionShape`, `getShapeIndex`, lines 34-108, SHA-256 `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c`.
- B evidence: `FenceBlock.java` class/constructor, lines 24-31, SHA-256 `4319ffb95ad81e969b11b39a8e6361d678b5fb5f3ad210d9834ce5e30d5f45d8`; inherited `PaneBlock` constructor, `makeShapes`, `getCollisionShape`, `getShapeIndex`, lines 36-115, SHA-256 `7150e89268a84113453cd5416ffe25e1ca7d42a39f264f7ae989878a10ee2158`.
- State producers/writers -> consumers/readers: stored directional flags -> identical horizontal bit-index construction -> pane-derived collision-shape array -> S020 world block-collision query.
- Parent slices / dependencies / closure evidence: S026 separates connection-state writers from shape selection; D-COLLISION-SHAPES remains open for other providers/neighbor-state sources.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both `FenceBlock` constructors pass `(2.0F, 2.0F, 16.0F, 16.0F, 24.0F)` to `PaneBlock`; both inherited `makeShapes` construct the same center post and directional arms, both collision-shape methods select the corresponding directional array entry, and both `getShapeIndex` methods OR the same north/east/south/west direction bits. B adds a per-state index cache but computes the same mask. For a given stored connection state, the fence collision geometry is the same. This conclusion does not cover how placement/update writers produce those flags.
- Finding IDs or checked absence/replacement path: none within this fixed-state collision-geometry slice.

### Slice S028: iron-bars collision geometry for fixed connection flags

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: A/B `IronBarsBlock` constructor shape parameters and inherited `PaneBlock.makeShapes`, `getCollisionShape`, and `getShapeIndex`; compare returned geometry for identical stored directional flags, with waterlogged state held fixed.
- A evidence: `IronBarsBlock.java` constructor, lines 15-18, SHA-256 `df6d956bb2745241e59294056f31513151b976dbd9ceed3f5478fd72b66c68a0`; inherited `PaneBlock` constructor, `makeShapes`, `getCollisionShape`, `getShapeIndex`, lines 34-108, SHA-256 `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c`.
- B evidence: `IronBarsBlock.java` constructor, lines 15-18, SHA-256 `10ccd6a2135cb05e74cdd4c440da29992dbc60655beedcb5f648f73bbc150541`; inherited `PaneBlock` constructor, `makeShapes`, `getCollisionShape`, `getShapeIndex`, lines 36-115, SHA-256 `7150e89268a84113453cd5416ffe25e1ca7d42a39f264f7ae989878a10ee2158`.
- State producers/writers -> consumers/readers: stored north/east/south/west flags -> pane-derived shape index -> cached collision-shape array -> S020 world block-collision query; waterlogged state does not enter the shape-index calculation.
- Parent slices / dependencies / closure evidence: S020 player block-collision query and S027 paired `PaneBlock` geometry; D-COLLISION-SHAPES remains open for other providers and fixed-state variants.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both constructors pass `(1.0F, 1.0F, 16.0F, 16.0F, 16.0F)` to `PaneBlock`. For identical directional flags, inherited `makeShapes` and `getShapeIndex` select the same arms and pane collision geometry in both versions; B's index cache preserves the calculated shape. The `WATERLOGGED` property is absent from the shape-index calculation, so holding the directional flags fixed yields the same collision geometry regardless of that property. This slice does not evaluate connection-state placement/update writers, which are excluded lifecycle.
- Finding IDs or checked absence/replacement path: none within this fixed-state collision-geometry slice.
## Dependency queue and blockers

- D-TICK-CLOSURE: full local tick/pre-travel/travel branches/post-travel; S019 adds the post-move auto-jump callback; surrounding call-order closure remains open.
- D-COLLISION-SHAPES: world queries, shape providers, registrations, neighbor-state inputs; S019 records the auto-jump entity query, S020 compares block enumerators, S021 checks four static providers, S022 compares the normal piston source guard, S023/S025 retain wall connection-state differences as excluded lifecycle evidence, S024 checks wall/gate categories, S026 retains the fence connection-state difference as excluded lifecycle evidence, and S027/S028 compare fixed-state fence and iron-bars geometry; other providers remain open.
- D-MOVEMENT-DATA: effects/attributes/enchantments/equipment/block property producer chains remain open; S026 verified the B default `FENCES` resource values and found that remote connections can replace block tags from a server packet; inspect further data consumers and keep server-supplied tag values external.
- D-EXTERNAL: direct player velocity/impulse/knockback writers plus correction/push/piston/mount/launch-item consumers; S018 closes only the bounded client player-correction velocity reset; S019 records entity-obstacle input to local auto-jump; S026 records remote block-tag replacement; other corrections/callers remain open; exclude combat causation and non-player/vehicle physics; source worker to enumerate.
- D-INDEPENDENT-AUDIT: reviewer unassigned; coordinator to assign.
- Open dependencies: above.

## Finding index

- [F001](findings/F001-input-scaling-and-underwater-sprint-gate.md) input scaling and underwater sprint gate; candidate; A revision cited and consumer-verified; original derived-artifact equivalence unproven; dependencies/reviewer open.
- [F002](findings/F002-pose-selection-and-collision-aware-resize.md) pose selection and collision-aware resize; exact snapshot submitted for blind confirmation; bounded pose/dimension/helper dependencies independently confirmed; conditional world collision outcomes and original derived-artifact equivalence remain unresolved.
- [F003](findings/F003-jump-boost-precision.md) Jump Boost precision; candidate; A revision cited and consumer-verified; original derived-artifact equivalence unproven; dependencies/reviewer open.
- [F004](findings/F004-input-acceleration-normalization.md) acceleration normalization; candidate; A revision cited and consumer-verified; original derived-artifact equivalence unproven; dependencies/reviewer open.
- [F005](findings/F005-jump-held-climb-impulse.md) jump-held climb impulse; candidate; A revision cited and consumer-verified; original derived-artifact equivalence unproven; dependencies/reviewer open.
- [F006](findings/F006-ground-acceleration-operation-order.md) ground acceleration arithmetic; candidate; A revision cited and consumer-verified; original derived-artifact equivalence unproven; dependencies/reviewer open.
- [F007](findings/F007-collision-axis-order.md) collision axis order; candidate; A revision cited and consumer-verified; original derived-artifact equivalence unproven; dependencies/reviewer open.
- [F008](findings/F008-movement-zero-displacement-cutoff.md) movement cutoff; candidate; A revision cited and consumer-verified; original derived-artifact equivalence unproven; dependencies/reviewer open.
- [F009](findings/F009-approximate-horizontal-collision-flag.md) collision flag tolerance; candidate; A revision cited and consumer-verified; original derived-artifact equivalence unproven; dependencies/reviewer open.
- [F010](findings/F010-water-travel-climb-impulse.md) water-travel climb impulse; candidate; A revision cited and consumer-verified; original derived-artifact equivalence unproven; dependencies/reviewer open.
- [F011](findings/F011-slow-falling-water-descent-timing.md) slow-falling water descent timing; candidate; A revision cited and consumer-verified; original derived-artifact equivalence unproven; dependencies/reviewer open.
- [F012](findings/F012-local-stuck-block-escape-probe.md) local stuck-block escape probe; candidate pending collision/dependency review.
- [F013](findings/F013-simultaneous-flight-input-arithmetic.md) simultaneous flight input arithmetic; candidate pending ability/dependency review.
- [F014](findings/F014-sleeping-pitch-reset-after-travel.md) sleeping pitch reset after travel; candidate pending movement-scope/dependency review.
- [F015](findings/F015-ground-drag-resamples-support.md) ground drag resamples post-move support; candidate pending world-movement/dependency review.
- [F016](findings/F016-knockback-normalization-response.md) knockback response normalization; candidate pending external/state dependency review.
- [F017](findings/F017-auto-jump-entity-collision-query.md) auto-jump entity collision-query bounds; candidate; conditional collision outcomes and provider closure remain open.
- [F018](findings/F018-world-block-collision-enumeration.md) world block-collision enumeration; candidate; common-era obstacle outcomes remain unresolved.
- [F019](findings/F019-wall-neighbor-connection-predicate.md) wall neighbor connection predicate; source-confirmed source evidence for TNT, frosted-ice and retracted-piston flag differences; block-state lifecycle is excluded from this assignment and no implementation handoff is accepted.

## Resume checkpoint

- Last completed slices: S026 fence connection-state predicate (excluded lifecycle), S027 fixed-state fence collision geometry (no difference), and S028 fixed-state iron-bars collision geometry (no difference); S001-S025 remain as recorded, with F019/S023/S025 retained as source-only lifecycle evidence.
- Next bounded work: continue static/provider enumeration while keeping state writers separate from fixed-state collision geometry. Exact source roots are the manifest paths above. S026 citations include `FenceBlock.java`, A/B hashes `abefbedea10306abf602d4e379a4793b415ca7635231752cd9f18a80aced5fc2` / `4319ffb95ad81e969b11b39a8e6361d678b5fb5f3ad210d9834ce5e30d5f45d8`, `PaneBlock.java` A/B `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c` / `7150e89268a84113453cd5416ffe25e1ca7d42a39f264f7ae989878a10ee2158`, `FenceGateBlock.java` A/B `e251cbd1b5e06c42e856a1d537e4ba043f6760b8f54ea1388039428aa7c05164` / `c6c869ac12993e09f4684798e843778b2b63b7d9766f9cac0894dae0f312a151`, and `Blocks.java` B `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`; client tag resource entry hashes and remote sync path are recorded in S026. S028 cites `IronBarsBlock.java`, A/B `df6d956bb2745241e59294056f31513151b976dbd9ceed3f5478fd72b66c68a0` / `10ccd6a2135cb05e74cdd4c440da29992dbc60655beedcb5f648f73bbc150541`. Reverify cited files against their ready manifests before handoff; do not rehash entire trees.
- Outstanding dependencies and owners: D-TICK-CLOSURE/D-COLLISION-SHAPES/D-MOVEMENT-DATA/D-EXTERNAL source worker; D-INDEPENDENT-AUDIT coordinator.
- Current assumptions requiring verification: full reachability, A-era SOLID-provider closure, B tag/cache closure, and remaining tick/external dependencies; original A derived-artifact equivalence remains unproven.

## Finding snapshots (not pair freeze)

### Snapshot event F002-2026-10-07-01

- Finding ID(s): F002.
- Source finding author(s): source worker.
- Status: submitted for exact-snapshot blind confirmation; no acceptance decision is recorded.
- Immutable snapshot commit: `448934e826fb41266dc79a313ef1187899d76382` (the F002 blob is unchanged through pair-run HEAD `5bba2ff359432a554984cd6225815c3dd252cede`).
- Finding file path and SHA-256: `workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F002-pose-selection-and-collision-aware-resize.md`; SHA-256 `baa5c6b30167eaa8024d34b39186440be944115061c8f39fdf5e17555b8ec67e`; Git blob `0c2b00b276604d6c20d4536722cc8304fb293148`.
- Exact A/B artifact-manifest identities/hashes:
  - A: Minecraft 1.13.2, Ornithe Feather `ornithe-feather`, client SHA-256 `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`; source manifest `1.13.2/ornithe-feather.sources.sha256` SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; raw artifact manifest `1.13.2/artifacts.sha256` SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`; Feather mapping JAR `feather-gen2-1.13.2+build.2-mergedv2.jar` SHA-256 `317384d4faccc2939c3b14252993d31745eb42aad59f2f0ea78893cbe4e7283b`, Tiny mapping SHA-256 `b3fd787448aed2c6e115d965d9edbb437ce47794ba63c7883e9014fd41262f71`. Cited source root is the readiness-verified `ready/1.13.2/ornithe-feather/` tree. Finding cites revised immutable derived artifact `feather-r1-2026-10-07`, client JAR SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`, `artifact.sha256` sidecar SHA-256 `4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40625601868692c7dd8`, and revision record SHA-256 `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`. The original derived JAR SHA-256 `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e` is unavailable; equivalence is unproven.
  - B: Minecraft 1.14.4, Ornithe Feather `ornithe-feather`, client SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`; source manifest `1.14.4/ornithe-feather.sources.sha256` SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`; artifact manifest `1.14.4/ornithe-feather.artifacts.sha256` SHA-256 `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970`; derived mapped client JAR SHA-256 `5e914421086acc2f244ca34bfd0df68e199dba5539a819a0eed1e4af31bb465c`; Feather mapping JAR `feather-gen2-1.14.4+build.2-mergedv2.jar` SHA-256 `3162806b9fb266d7e6d2c594be8d4cc6c91ddb09b4e55d1b566e5429edbd793f`, Tiny mapping SHA-256 `60d4906621c873dadba96425d1a233349c9afa407d09bfc8371202af6a71f25c`. Cited source root is readiness-verified `ready/1.14.4/ornithe-feather/`.
- Cited source/resource hashes: A `PlayerEntity.java` SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`; B `PlayerEntity.java` SHA-256 `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df`. The paired body ranges and B pose-dimension provider are recorded in F002. No resource hash is used by this finding.
- Finding-specific dependencies closed for this bounded finding: the early blind reviewer independently confirmed the A/B crouch-height delta and pose-selection difference, and checked the collision-fit helper semantics. The handoff is limited to these source-level pose, dimension, fallback, and query-context differences.
- Conditional collision-fit limits: whether a requested pose fits still depends on the world collision set and query context. The source comparison does not enumerate every world/provider/neighbor shape or assert a particular collision outcome; F002’s tight-space consequence remains conditional and unsimulated.
- Review basis / requested source-only revisions: source worker submits this exact commit/path/hash for blind confirmation. Early blind confirmation covers the height delta, pose selection, and collision-fit helper semantics; reviewer identity and an acceptance decision were not supplied to this ledger update. No implementation or wiki feedback was supplied or used.
- Pair run status and commit at handoff: partial at `5bba2ff359432a554984cd6225815c3dd252cede`.
- Pair complete: no.
- Implementation handoff: blocked pending blind acceptance of this exact snapshot; no implementation reconciliation is included.
- Replaces/supersedes snapshot ID and reason, if applicable: none.

Pair remains partial. This event does not close unrelated inventories, freeze the pair, establish a first changed release, or claim runtime validation.

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

- Coverage counts by status: findings=17 slices (19 deltas); compared-no-difference=10; pending inventory closure=7; in-progress=0; not-applicable=1; blocked=0.
- Required inventory status and evidence: all seven inventories pending; paired evidence in S001-S028.
- Accepted finding snapshots: none; F002 snapshot event F002-2026-10-07-01 is submitted and awaits exact-snapshot blind confirmation.
- Open dependencies: D-TICK-CLOSURE,D-COLLISION-SHAPES,D-MOVEMENT-DATA,D-EXTERNAL,D-INDEPENDENT-AUDIT.
- Unresolved gaps and limits: full tick, shapes/resources, modifier chains, external writers beyond S016, and independent audit. First changed release unknown within (1.13.2,1.14.4].
- Evidence/hash/correspondence audit: readiness markers, source manifests and S025 cited Java hashes verified; corrected the shortened A and B `BlockState.java` hashes in S023/F019; cited body ranges paired; full helper/call graph closure incomplete.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).
