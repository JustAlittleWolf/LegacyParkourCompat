# Discovery: 1.13.2 to 1.14.4

- Run status: partial
- Scope: source-only comparison of direct client player movement; A=1.13.2; B=1.14.4. Block-state placement/update lifecycle and world generation are excluded; fixed-state collision geometry remains in scope.
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

Resolved pairs include `LocalClientPlayerEntity.mobTick`, `KeyboardInput.tick` (no-arg to boolean args), `LivingEntity.jump`, `LivingEntity.moveRelative` (three floats to Vec3d), `Entity.updateVelocity` (float components to vector helper), `Entity.move` (three doubles to vector), and `PlayerEntity.updatePlayerPose`. Full local tick, superclass, pre-travel, all dispatch branches and post-travel call chain are not closed. S001-S049 contain bounded paired evidence; no stage label alone is treated as coverage.

## Required source inventories

- `INV-TICK` status=pending; slice_ids=S001,S003,S004,S005,S007,S008,S009,S010,S019,S030,S031,S040,S041,S043; evidence=paired client input/local player/living bodies, player air-speed reset/flight branches, sprint modifier and walk-speed attribute writers, and slime/bubble-column contact responses below; complete tick call graph open.
- `INV-STATE` status=pending; slice_ids=S001,S002,S003,S006,S008,S009,S010,S018,S029,S040,S041,S042,S043,S045,S046,S048,S049; evidence=paired input, pose, jump, movement, client correction velocity, synchronized effect state, player air-speed state, sprint modifier, speed/slowness effect modifiers, walk-speed attribute, End Rod/bed/cake/portal-frame shape-state paths, and End Portal Frame cube predicate below.
- `INV-COLLISION` status=pending; slice_ids=S002,S006,S009,S019,S020,S021,S023,S024,S025,S026,S027,S028,S030,S031,S032,S033,S034,S035,S036,S037,S038,S039,S045,S046,S047,S048; evidence=pose fit, escape probes, entity move, paired collision enumerators, static providers, wall categories, fixed-state fence/iron-bars/bubble-column/stairs/slabs/doors/trapdoors/concrete-powder/kelp/carpet/farmland/grass-path/pressure-plate/rail/tripwire/ladder/button/lever/torch/sign/banner/crop/aquatic-plant/end-rod geometry, changed bed and cauldron geometry, paired brewing-stand/cake/portal-frame geometry and slime callback reachability; remaining provider enumeration open.
- `INV-WORLD-MOVEMENT` status=pending; slice_ids=S005,S007,S026,S027,S028,S030,S031,S032,S033,S034,S035,S036,S037,S038,S039,S045,S046,S047,S048; evidence=slipperiness/climbing consumers, fixed-state fence/iron-bars/bubble-column/stairs/slabs/doors/trapdoors/concrete-powder/kelp/carpet/farmland/grass-path/pressure-plate/rail/tripwire/ladder/button/lever/torch/sign/banner/crop/aquatic-plant/end-rod geometry, changed bed/cauldron collision geometry, unchanged brewing-stand/cake/portal-frame geometry and player slime/bubble-column responses; other providers/resources remain open.
- `INV-MODIFIERS` status=pending; slice_ids=S003,S005,S029,S040,S041,S042,S043; Jump Boost/Slow Falling consumers, client effect-state ingress, player sprint air-speed baseline, sprint and Speed/Slowness movement-speed modifiers, and player walk-speed base initialization/server-side writer observed; remaining producer/application paths and attributes/equipment chains open.
- `INV-EXTERNAL` status=pending; slice_ids=S006,S009,S010,S012,S016,S017,S018,S019,S022,S026,S029,S030,S031,S043; direct player velocity/impulse/knockback remains in scope, including reachable push response; S018 compares the client player-correction velocity reset, S019 inventories entity obstacles in the player auto-jump scan, S026 confirms remote tag synchronization as a block-state input, S029 compares server-synchronized effect-state ingress, S030 compares the player-only slime bounce/drag response, S031 compares bubble-column velocity response, and S043 records the server-ability walk-speed attribute input; other correction paths, knockback sources, piston, mount, launch-item consumers and state inputs remain open; exclude non-player/vehicle physics and combat cause/damage resolution.
- `INV-EXCLUSIONS` status=pending; slice_ids=S044,S049; evidence=scope exclusions declared; direct-read inventory open. Exclude health/food state production, attack/damage resolution, block-state placement/update lifecycle, and world generation; fixed-state collision geometry and direct player velocity/impulse/knockback response remain in scope even if combat can trigger the latter; exclude non-player and vehicle physics. Food/blindness may remain movement-predicate inputs without emulating their producers.

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
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): F019 retains source evidence that A and B produce different wall connection flags for vanilla TNT/frosted-ice adjacency. Those flag producers are block-state placement/update lifecycle, expressly excluded from this assignment; the reported displacement/clipping was unmeasured and this does not create an implementation handoff. Fixed-state wall geometry is a separate in-scope question.
- Finding IDs or checked absence/replacement path: F019 retained as source-only, excluded lifecycle evidence; no accepted implementation candidate.
### Slice S024: special wall-neighbor shape categories

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: the A wall predicate's `MIDDLE_POLE_THICK` and fence-gate `MIDDLE_POLE` cases, paired with B wall-tag and direction-compatible gate cases.
- A evidence: `WallBlock.java` lines 51-55, 127-129, SHA-256 `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`; `FenceGateBlock.java` lines 159-165, SHA-256 `e251cbd1b5e06c42e856a1d537e4ba043f6760b8f54ea1388039428aa7c05164`.
- B evidence: `WallBlock.java` lines 48-52, SHA-256 `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7`; `FenceGateBlock.java` lines 154-156, SHA-256 `c6c869ac12993e09f4684798e843778b2b63b7d9766f9cac0894dae0f312a151`; `BlockTagsProvider.java` lines 270-281, SHA-256 `0bd051d11a0c85ecc61cd2202a6ed33923db97238da0b065a647b932132d1bfa`.
- State producers/writers -> consumers/readers: neighboring wall or gate block/facing -> wall connection predicate -> directional state flag -> collision-shape selector.
- Parent slices / dependencies / closure evidence: S023 wall predicate; D-COLLISION-SHAPES remains open for solid-face providers, exception/cache interactions, and other A-era shapes.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): this slice checks wall/gate state-writer categories: the A-era wall block categories and fence-gate orientation test. The inspected inputs produce the same flags, but block-state placement/update lifecycle is excluded regardless of equality, so this is retained as source-only exclusion evidence rather than counted as in-scope movement coverage. It does not close the separate fixed-state shape comparison.
- Finding IDs or checked absence/replacement path: none; excluded block-state lifecycle category evidence.

### Slice S025: wall connection beside a retracted piston

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: A `PistonBaseBlock.getFaceShape` for retracted states and `Block.isExceptionForAttachment` through `WallBlock.isExceptionForConnection`; B piston collision-shape provider, `Block.isFaceSolid` and attachment exception, plus `BlockState.m_87223014` cached/fallback paths. The wall placement and neighbor-update consumers pass the face opposite the wall direction into the connection predicate.
- A evidence: `PistonBaseBlock.java`::`getFaceShape`, lines 366-369, SHA-256 `1722edaf96c15af587b4e169b62c5af1d352800e82a2070e1f4af86bdde4cb31`; `Block.java`::`isExceptionForAttachment`, lines 255-257, same file hash `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`; `WallBlock.java`::`shouldConnectTo` and `isExceptionForConnection`, lines 51-62, SHA-256 `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`; `BlockState.java`::`getFaceShape`, lines 285-287, SHA-256 `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70`; inherited `PaneBlock.makeShapes` and `getShapeIndex`, lines 38-108, SHA-256 `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c`.
- B evidence: `PistonBaseBlock.java`::`getShape`, lines 51-71, SHA-256 `37409c9f2390642103155dcec3e922e0d387e9d0bbca069fb334277d5d84a058`; inherited `Block.getCollisionShape`, lines 376-378, and `Block.isFaceSolid`, lines 402-409, SHA-256 `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`; `Block.isExceptionForAttachment`, lines 260-267, same hash; `BlockState.m_87223014`, lines 326-328, and its face-solid cache initialization, lines 410-417, SHA-256 `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f`; `WallBlock.java`::`m_71853799`, `getPlacementState`, `updateShape`, and `getCollisionShape`, lines 39-40, 48-52, 55-104, SHA-256 `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7`; inherited `PaneBlock.makeShapes` and `getShapeIndex`, lines 40-115, SHA-256 `7150e89268a84113453cd5416ffe25e1ca7d42a39f264f7ae989878a10ee2158`.
- State producers/writers -> consumers/readers: vanilla piston `FACING`/`EXTENDED` state -> A face-shape result or B piston collision shape and face-solid cache -> wall placement/update direction flag -> wall collision-shape array -> world block-collision query used by player movement (S020).
- Parent slices / dependencies / closure evidence: S020 paired world block-collision query, S023 wall predicate, and S024 wall/gate categories; D-COLLISION-SHAPES remains open for other A-era face-shape providers, B tags/cache paths, panes and inherited shape providers.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): when a retracted vanilla piston is beside a wall, A/B placement/update writes different wall direction flags and therefore selects a different wall shape. This source evidence is a block-state lifecycle difference, expressly excluded from the assignment; it remains attached to F019 as source-only evidence and is not an implementation candidate. S027 covers fixed-state wall geometry separately.
- Finding IDs or checked absence/replacement path: F019 retained as source-only, excluded lifecycle evidence; no accepted implementation candidate.

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

### Slice S029: server-synchronized movement-effect state ingress

- Inventory ID(s): INV-STATE, INV-MODIFIERS, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: A/B client status-effect packet handlers resolve an effect ID, construct the instance from packet duration/amplifier/flags, set permanence, then add it to a living entity; paired removal handlers remove the effect from that entity; compare the effect map readers used by player movement.
- A evidence: `ClientPlayNetworkHandler.java`::`handleEntityStatusEffect`, lines 1396-1409, and `handleEntityRemoveStatusEffect`, lines 1488-1494, SHA-256 `4f65502fbee6476cef0838563f03a66f07a1a9ed8c63e580f5338ba5fe274f11`; `LivingEntity.java`::`hasStatusEffect`, `getEffectInstance`, `addStatusEffect`, `removeEffect`, `onStatusEffectApplied`, lines 656-718, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`; `StatusEffect.byId`, lines 34-40, SHA-256 `60cc53e7233d28413d3c93292fd216babe8ea9edeb70774141b68101c515c639`; `StatusEffects.java` names Jump Boost, Levitation and Slow Falling at lines 62,79,82, SHA-256 `d982a7e051da6014286e07dcbfd36fd62dd0faa4ae5218a1c2d89bff02a78d9e`.
- B evidence: `ClientPlayNetworkHandler.java`::`handleEntityStatusEffect`, lines 1453-1466, and `handleEntityRemoveStatusEffect`, lines 1548-1554, SHA-256 `6c840580285c7c7d4617e895edac82d4eebf8289dd4994e7b593cebeb49b43ef`; `LivingEntity.java`::`hasStatusEffect`, `getEffectInstance`, `addStatusEffect`, `removeEffect`, `onStatusEffectApplied`, lines 756-818, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`; `StatusEffect.byId`, lines 30-36, SHA-256 `9648b2d33f1adefafff71e1c14fa6427194ad2cff4ff242a4b2db355daa71085`; `StatusEffects.java` names and IDs for Jump Boost, Levitation and Slow Falling at lines 46,73,86, SHA-256 `46cf6a77e95de8be46feb39b258ef1eb4fd2d9f34538bd2d027bb9d58427500f`.
- Shared data evidence: `StatusEffectInstance.java` is byte-identical in both ready trees, SHA-256 `4e32f77b5e656de2f07b8ec2851dde4c3ee107f773984d2c03774f0c24a33e93`; its amplifier accessor and stored duration/flags are used by the paired effect map readers. Numeric registry IDs remain version-local; no cross-version ordinal equivalence is inferred.
- State producers/writers -> consumers/readers: server-supplied effect packet -> `StatusEffect.byId` in the client handler -> `LivingEntity.statusEffects` map -> `hasStatusEffect`/`getEffectInstance` -> Jump Boost, Slow Falling and Levitation movement consumers in S003/S008/S011. Removal packet calls `removeEffect` on the same map. Other status-effect creation/application is not emulated; server-authoritative effect state is an input.
- Parent slices / dependencies / closure evidence: S003, S008 and S011 movement consumers; S018 client packet-state handling; D-MOVEMENT-DATA and D-EXTERNAL remain open for remaining state producers, modifiers, equipment and external movement inputs.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for a living player selected by a normal server status-effect packet, A and B perform the same resolve/construct/permanence/add sequence and the same remove-from-map operation. Both map reader methods check the same effect key and retrieve the same `StatusEffectInstance`; that class is byte-identical. The three movement-effect names remain paired across the two version registries, although packet numeric IDs are resolved only within each version. Thus this bounded server-to-client state ingress and consumer representation has no source-level difference. This does not close effect generation, attribute modifiers, equipment, local integrated-server writers or other external movement inputs.
- Finding IDs or checked absence/replacement path: none within this bounded effect-state ingress slice.

### Slice S030: player slime bounce and ground-contact drag

- Inventory ID(s): INV-TICK, INV-COLLISION, INV-WORLD-MOVEMENT, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: paired `Entity.move` calls to `Block.beforeCollision` after vertical collision resolution and `Block.onSteppedOn` under the `makesSteps`, ground/sneak/player/riding guards; compare the corresponding `SlimeBlock` player velocity response and the B vector getter/setter/scaling helpers.
- A evidence: `SlimeBlock.java`::`beforeCollision` and `onSteppedOn`, lines 30-51, SHA-256 `8a97ba1ed4b65141b6623c7a5b13162767a54bc5666a4d99e8518ba0113cfae4`; `Entity.java`::`move`, callback ordering/guards, lines 681-720, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`; `Blocks.java`::`SLIME` old-version registration, line 1008, SHA-256 `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303`.
- B evidence: `SlimeBlock.java`::`beforeCollision` and `onSteppedOn`, lines 30-52, SHA-256 `9c5cf3602653fd5699064364906cd1f08ae445cba74674a1c9d8026aeafd24ca`; `Entity.java`::`move`, callback ordering/guards, lines 481-516, SHA-256 `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`; velocity getter/setter, lines 2646-2655, same `Entity.java` hash; `Vec3d.m_17023014`, lines 83-89, SHA-256 `b69b990b768e52d6fac6b3ed82f60e7526e78d0837c868a61a4171ebca21040b`; `Blocks.java`::`SLIME` registration, lines 1069-1071, SHA-256 `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`.
- State producers/writers -> consumers/readers: player sneaking/ground/riding state and vertical collision response -> slime `beforeCollision`/`onSteppedOn` callbacks -> player velocity components -> next movement tick. The block's `onFallenOn` damage path is outside this bounded slice and damage simulation remains excluded.
- Parent slices / dependencies / closure evidence: S006 `Entity.move` axis/collision ordering and S020 world block collision; D-TICK-CLOSURE, D-COLLISION-SHAPES and D-EXTERNAL remain open for other callbacks/providers and callers.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both registries contain the common-era `slime_block`. When a player contacts it from above while descending and is not sneaking, both versions reverse the player's vertical velocity with magnitude preserved: A avoids its `0.8` multiplier for every `LivingEntity`; B selects `d=1.0` for a `LivingEntity`. When a grounded, non-sneaking player reaches `onSteppedOn` under the shared movement guards, both compute `0.4 + abs(vy) * 0.2` and multiply horizontal velocity by that factor while preserving vertical velocity. B's vector multiply is componentwise `x*d,y*1,z*d`, and its entity setter stores those components directly. The callback source order and player guards are paired; differences in non-player behavior and fall damage are outside scope.
- Finding IDs or checked absence/replacement path: none within the common-era player slime response slice.

### Slice S031: bubble-column player velocity response and fixed-state collision

- Inventory ID(s): INV-TICK, INV-COLLISION, INV-WORLD-MOVEMENT, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: A/B `Entity.checkBlockCollisions` box scan and callback order, `BubbleColumnBlock.onEntityCollision` air-above branch and DRAG read, paired surface/submerged player vertical-velocity responses, plus registration `noCollision` and inherited collision-shape selection for a fixed DRAG state.
- A evidence: `BubbleColumnBlock.java`::`onEntityCollision`, `DRAG`, lines 21-45, SHA-256 `0320e1b70b215dfff209f8e5003b97cac3a247fd3d642597f2db8ab46d251b2e`; `Entity.java`::`move` call to `checkBlockCollisions`, line 747, `checkBlockCollisions`, lines 801-827, and `onBubbleColumnSurfaceCollision`/`onBubbleColumnCollision`, lines 1873-1889, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`; registration `Block.java`::`register("bubble_column", ... noCollision())`, lines 1874-1875, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`; inherited `Block.getCollisionShape`, lines 393-394, same hash.
- B evidence: `BubbleColumnBlock.java`::`onEntityCollision`, `DRAG`, lines 22-47, SHA-256 `d5b9bdcf81349e96920134d6519629479787203d59ea2bd1683088c0b5223ef2`; `Entity.java`::`move` call to `checkBlockCollisions`, line 542, `checkBlockCollisions`, lines 797-823, and `onBubbleColumnSurfaceCollision`/`onBubbleColumnCollision`, lines 1905-1928, SHA-256 `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`; registration `Blocks.java`::`BUBBLE_COLUMN` with `noCollision()`, lines 1850-1852, SHA-256 `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`; inherited `Block.getCollisionShape`, lines 376-378, SHA-256 `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`; the explicit B `getShape` empty override is in `BubbleColumnBlock.java`, lines 170-172.
- State producers/writers -> consumers/readers: current block box and intersected block states -> `BubbleColumnBlock.onEntityCollision` -> surface/submerged player velocity callback using stored `DRAG` -> vertical velocity for the next movement step. Fixed-state collision query -> `hasCollision=false` registration -> empty collision shape in both versions. DRAG placement/update production is block-state lifecycle and excluded.
- Parent slices / dependencies / closure evidence: S020 player world block-collision context and S030 common block-callback dispatch; D-TICK-CLOSURE, D-COLLISION-SHAPES and D-EXTERNAL remain open for other callbacks/providers and callers.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the common-era bubble-column block is registered with `noCollision()` in both versions, so the inherited collision-shape query returns empty for the fixed state; B's explicit empty outline shape does not change player collision. During the paired entity box scan, an intersecting bubble-column block reads its stored `DRAG` flag and checks whether the block above is air in the same order before calling the matching surface or submerged response. For either branch and either flag, A and B use the same constants, `Math.max`/`Math.min` bounds, and `velocityY +/- constant` operation order. B's vector setter writes unchanged x/z and the computed y directly. A player overlaps the column during the normal per-tick block-collision scan, so these callbacks are reachable. This compares fixed DRAG input and callback results; block-state lifecycle and other fluid-current paths remain open/out of scope as appropriate.
- Finding IDs or checked absence/replacement path: none within the paired bubble-column callback and fixed-state collision slice.

### Slice S032: common state-dependent obstacle shapes

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: `StairsBlock`, `SlabBlock`, `DoorBlock`, and `TrapdoorBlock` shape constants and fixed-state shape selection. Stair current-state `SHAPE` selection and facing/half indexing are included; the stored `SHAPE` value is treated as input because block-state placement/update lifecycle is excluded. The added 1.14 `CollisionContext` parameter is unused in these four shape methods.
- A evidence: `StairsBlock.java`::shape constants/arrays, `getShape`, `getShapeIndex`, lines 37-104, SHA-256 `b226eb255a3a1b0a69e137c15024a3b0a48f488e7205174dd476ffd64ca82761`; `SlabBlock.java`::shape constants and `getShape`, lines 20-56, SHA-256 `2e7ff3fcfa5faa83aa4af7ea9b68f07af8d8fe7047e08ee3a9d7d37b78c7128f`; `DoorBlock.java`::shape constants and `getShape`, lines 28-66, SHA-256 `e01782f96a2214be2bc48dffad8b062db9eda12c04aa4d7747f01575d0df019b`; `TrapdoorBlock.java`::shape constants and `getShape`, lines 26-63, SHA-256 `26dd6911517029daef779cef046e943953356644a76372f1d47b1323054629da`.
- B evidence: `StairsBlock.java`::shape constants/arrays, `getShape`, `getShapeIndex`, lines 37-104, SHA-256 `b49cbd43a9be5e848623f25769dede5795f50435ba0e82bbdbbf571527396b9f`; `SlabBlock.java`::shape constants and `getShape`, lines 20-54, SHA-256 `91f2ebc592e5cb07d5ce8a6faa123ec0d4b7bf10aa6323d6aceb343bc0f73a53`; `DoorBlock.java`::shape constants and `getShape`, lines 28-67, SHA-256 `5c40e94e04427c8a16a301aad12f7d2efdef866f70b21acd830fc683099dcbcd`; `TrapdoorBlock.java`::shape constants and `getShape`, lines 28-65, SHA-256 `129b1c0eda5e1431fe3845ef129524dd96c4eb018b686e6d0e38b093e198b3a5`.
- State producers/writers -> consumers/readers: synchronized/fixed block state -> the four shape providers -> `Block.getCollisionShape` default shape path -> entity block collision query and axis clipping. Existing S020/S021 evidence covers the common entity/query/shape dispatch path; stair stored SHAPE state can depend on neighbors when produced, but that lifecycle is excluded and was not compared here.
- Parent slices / dependencies / closure evidence: S020/S021 establish the paired collision query and default collision-shape dispatch; D-COLLISION-SHAPES remains open for other providers, registrations and callbacks.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for equal fixed input state, A and B select the same geometric shapes. Stairs use the same slab bases, quarter-box definitions, shape-index table and state-to-array selection; slabs return the same full, top-half or bottom-half shape; doors use the same facing/open/hinge selection among the same 3/16-thick planes; trapdoors use the same open-facing planes and closed top/bottom 3/16 slabs. The only signature change is B's additional unused collision context. For these ordinary collidable block classes, the shared base collision-shape path consumes the provider shape. Their shapes are therefore equivalent for player collision for identical block states. Producer/update lifecycle and changes to state are not claimed equivalent.
- Finding IDs or checked absence/replacement path: none within the four fixed-state shape providers.

### Slice S033: concrete-powder inherited collision shape

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: all 16 common-era concrete-powder registrations, `ConcretePowderBlock` and its `FallingBlock` parent shape declarations, and the inherited default block outline/collision-shape path for a fixed dry powder state. Placement, falling/landing, hardening and neighbor-update lifecycle are excluded.
- A evidence: `Block.java` registrations `white_concrete_powder` through `black_concrete_powder`, lines 1692-1750, with each color using `ConcretePowderBlock` and `Material.SAND`, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`; `ConcretePowderBlock.java`, SHA-256 `21f7b5e889ba885bd44da3570aff685ef28b21bff636ca109f4e6bb9eb5ab52f`; neither it nor `FallingBlock.java` declares `getShape`/`getCollisionShape`; `Block.java` default shape/collision dispatch is at lines 388-394 and same whole-file hash above.
- B evidence: `Blocks.java` registrations `WHITE_CONCRETE_POWDER` through `BLACK_CONCRETE_POWDER`, lines 1581-1643, with each color using `ConcretePowderBlock` and `Material.SAND`, SHA-256 `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`; `ConcretePowderBlock.java`, SHA-256 `613fbbc5ee92ee15d977f43cac4defc0d37f09733c9443824d085215dde98052`; neither it nor `FallingBlock.java` declares `getShape`/`getCollisionShape`; `Block.java` default shape/collision dispatch is at lines 371-378, SHA-256 `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`.
- State producers/writers -> consumers/readers: fixed concrete-powder block state -> inherited default outline and collision shape -> paired entity block collision query/axis clipping. Concrete powder also reads neighboring collision face fullness during hardening; that lifecycle query is excluded from this slice.
- Parent slices / dependencies / closure evidence: S020/S021 establish the paired collision query and default collision-shape dispatch; D-COLLISION-SHAPES remains open for other providers, registrations and callbacks.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both registries contain all 16 color variants, each constructed as `ConcretePowderBlock` with sand material and default block collision settings. Neither the class nor its falling-block parent overrides outline or collision shape in either version. The inherited base provider and collision dispatcher therefore return the same full-block shape for an equivalent fixed powder state. No falling, placement, hardening or update behavior is compared.
- Finding IDs or checked absence/replacement path: none within the inherited fixed-state concrete-powder shape slice.

### Slice S034: kelp outline and empty collision shape

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: fixed-state `kelp` and `kelp_plant` registrations, `KelpBlock`'s outline shape, inherited collision-shape dispatch, and `KelpPlantBlock`'s inherited outline. Plant growth, water occupancy and update lifecycle are outside this geometry slice.
- A evidence: `Block.java` registers both blocks with `noCollision()`, lines 1749-1753, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`; `KelpBlock.java`::`SHAPE`/`getShape`, lines 24-38, SHA-256 `57330c0ac1cd00000f2e0c0f78ec98d2ec48024045b65d24f506408ea3cb8952`; `KelpPlantBlock.java` SHA-256 `dba4dffada2a7baae6a0f06e973b5af7dd175b71b9387581497087f76378751f`.
- B evidence: `Blocks.java` registers both blocks with `noCollision()`, lines 1644-1648, SHA-256 `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`; `KelpBlock.java`::`SHAPE`/`getShape`, lines 25-34, SHA-256 `1e26cc67f68c836963a09582b24605856f9f99f08959cfab6f0b464d8b0c4707`; `KelpPlantBlock.java` SHA-256 `0178baad268c0ccc7ff9da8bda721f933cb3cce81736b2dcc968c8d626edd345`.
- State producers/writers -> consumers/readers: fixed kelp state -> 9/16-height outline shape; the shared `hasCollision=false` registration makes the player collision-shape query return empty in both versions. The plant block inherits the base outline and same empty collision path.
- Parent slices / dependencies / closure evidence: S020/S031 establish paired block collision queries and `hasCollision` dispatch; D-COLLISION-SHAPES remains open for other providers and registrations.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both versions register kelp and kelp plant with the same water-plant material and `noCollision()` flag. Kelp's outline constant remains the same 16-by-9-by-16 box and its shape method returns that constant; `kelp_plant` does not override the shape. Since the inherited collision provider returns the empty shape when `hasCollision` is false, neither block clips player movement. Other water/plant interactions are not covered here.
- Finding IDs or checked absence/replacement path: none within fixed-state kelp collision geometry.

### Slice S035: surface height and pressure-plate shapes

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: fixed-state `CarpetBlock`, `FarmlandBlock`, `GrassPathBlock`, and inherited `AbstractPressurePlateBlock` outline/collision shape constants and selectors. For pressure plates, the current output signal is treated as fixed input; signal production and entity-trigger lifecycle are not compared.
- A evidence: `AbstractPressurePlateBlock.java`::pressed/unpressed constants and `getShape`, lines 17-28, SHA-256 `c920500d5b908f6f516e04b6c312ba7c848c3cfa97b24d3427ccf10d2b0de701`; `CarpetBlock.java`::`SHAPE`/`getShape`, lines 13-28, SHA-256 `cc3a3e0356a9a0e1ba4d82e4dc2caf880999294bc72ad7be62a43e031faaa4a4`; `FarmlandBlock.java`::`SHAPE`/`getShape`, lines 24-59, SHA-256 `581dae15cc29abe90f9cf20232a3cd1b7ab6c868808bc5e4f05816952b61eef9`; `GrassPathBlock.java`::`SHAPE`/`getShape`, lines 17-58, SHA-256 `8acdb82a274f68c5dd89faa92c82d450bbe9906ee65584c981633ed922430cfe`.
- B evidence: `AbstractPressurePlateBlock.java`::pressed/unpressed constants and `getShape`, lines 18-29, SHA-256 `23fa38e232fd91a736ae79c8d038c317707b72a2f312467efbacca42b903b1c3`; `CarpetBlock.java`::`SHAPE`/`getShape`, lines 14-29, SHA-256 `55a7faf5d395480ebd6a47ff9820f6a5082807d5f5b2c6072d11a79316a00b25`; `FarmlandBlock.java`::`SHAPE`/`getShape`, lines 25-60, SHA-256 `27227a174fa9d51ab3b618e52d1ec6226fcd0610d4c89ed5648e113731eb66c3`; `GrassPathBlock.java`::`SHAPE`/`getShape`, lines 17-58, SHA-256 `5924874f0eda2942dd1ce889e665dfa7fa1d8b28a2dbed42917e44014540b0a8`.
- State producers/writers -> consumers/readers: fixed block state/current pressure-plate output -> shape selector -> shared block collision-shape dispatch -> player block collision query. S020/S031 establish the paired dispatch path.
- Parent slices / dependencies / closure evidence: S020/S021 paired world block-collision query and base collision-shape dispatch; D-COLLISION-SHAPES remains open for other providers, registrations and callbacks.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the paired pressure-plate provider chooses the same 1/2-pixel pressed or 1-pixel unpressed shape for the same positive/non-positive output signal. Carpets use the same 1-pixel-high full-footprint shape; farmland and grass path use the same 15-pixel-high full-footprint shape. B adds an unused `CollisionContext` argument to each override. Their shared base collision path receives these shapes for equivalent fixed inputs, so the resulting player collision geometry is unchanged.
- Finding IDs or checked absence/replacement path: none within these four fixed-state surface providers.

### Slice S036: rail, tripwire, ladder and control shapes

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: rail and tripwire/hook fixed-state shapes and registrations, lever/button fixed-state outline providers and their no-collision flags, and ladder facing-to-plane shape selection. Rail/tripwire/control block-state production and activation behavior are excluded.
- A evidence: `AbstractRailBlock.java`::flat/ascending constants and `getShape`, lines 17-42, SHA-256 `ef89230726ce9bd5ebb009d9332da90969d074762fc7eca8d477640d020a0a8d`; `TripwireBlock.java`::attached/free constants and `getShape`, lines 31-54, SHA-256 `708a553f53db6ff187e93e02e0e7ffc8e34fc0c9abd38a0ffe7c18a488534d59`; `TripwireHookBlock.java`::facing shapes and selector, lines 28-51, SHA-256 `c9b234f97b9ca6f93a519c2775aa91e88756456d8bfd4bd7e5281bb411f82629`; `LadderBlock.java`::facing shapes and selector, lines 18-46, SHA-256 `c89dea1d7648bf5908e9d925b31b1ad6ead25bea3784838ae3d0fb505cfb9ab5`; `LeverBlock.java`::state-based shape constants/selector, SHA-256 `0e9d5a08921c8b0f96c060742a24666f3a8eb2e4b9f7d408a5d242a0edc1daed`; `ButtonBlock.java`::state-based shape constants/selector, SHA-256 `0cdd88bf4f0ee6b2d935a59867c1ba6df68c098d97701e800f499f83dc7a9578`; common registrations in `Block.java` include noCollision rails, tripwire/hooks, lever and all wood/stone buttons; SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`.
- B evidence: `AbstractRailBlock.java`::flat/ascending constants and `getShape`, lines 17-42, SHA-256 `9f889ccea3e80c29fe4c5ac97c6713b5e40878eeaf5ffadd71918572a4fff70a`; `TripwireBlock.java`::attached/free constants and `getShape`, lines 32-55, SHA-256 `fea6cdd16b28ab7008ef4429fb50aeb4f2c3ec4c6f7de9ae54959ae5cfca9efd`; `TripwireHookBlock.java`::facing shapes and selector, lines 29-52, SHA-256 `b7c4e272133e4a5173b3896a5b090907fad232c76291d72686ca102ca4bae3b7`; `LadderBlock.java`::facing shapes and selector, lines 18-46, SHA-256 `12dc5ef28f2db058969218573f5d605b94cab4cc36b67b2d46e82aaf39fc767f`; `LeverBlock.java`::state-based shape constants/selector, SHA-256 `53dda36db5316f18985497b72e2d09b3e7a72e74141c4dfbe22addc621560111`; `ButtonBlock.java`::state-based shape constants/selector, SHA-256 `9153636ceafd3c93882d8b9603a7813ff99695b955d17f45507e87860408ba63`; common registrations in `Blocks.java` include noCollision rails, tripwire/hooks, lever and all wood/stone buttons; SHA-256 `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`.
- State producers/writers -> consumers/readers: fixed facing/rail/attached/control block state -> shared outline/collision provider. Rail/tripwire/hooks/buttons/levers have `hasCollision=false` in both registries, so their player collision shape is empty even where their outline methods return geometry. Ladders remain collidable and select the same 2/16-thick plane for equivalent facing. The common entity block-collision query and collision dispatch are established in S020/S031.
- Parent slices / dependencies / closure evidence: S020/S031 paired query and collision dispatch; D-COLLISION-SHAPES remains open for remaining provider classes, registrations and world callbacks.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the four rail registrations (`rail`, `powered_rail`, `detector_rail`, `activator_rail`) and the tripwire, tripwire-hook, lever and button families use `noCollision()` in both versions. Their outline shape selectors/constants also retain the same stored-state branches, but those outlines do not clip a player. Ladder remains an ordinary collidable block in both versions; each facing returns the same wall-adjacent 3/16-thick plane. B's added context argument is unused by these shape methods. The slice compares fixed collision inputs only, not block activation, rail state production or tripwire callbacks.
- Finding IDs or checked absence/replacement path: none within the fixed-state shape and registration subset above.

### Slice S037: torch, sign and banner no-collision providers

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: fixed-state torch/wall-torch, sign/wall-sign and standing/wall-banner shape provider families and their registry `noCollision()` flags. The comparison concerns player block-collision output; lighting, sign/banner interaction and block-state lifecycle are excluded.
- A evidence: `TorchBlock.java` floor outline, lines 16,23-24, SHA-256 `da11308e51f57ce95764f2ee645dde8d15607914a3a68bac9c0635ab3d1531e0`; `WallTorchBlock.java` facing shape map/selector, lines 23-48, SHA-256 `f06b2a546ba8465627511969efc1203e654a1af3ccf61d27aed9b28b00c77462`; `RedstoneWallTorchBlock.java` delegates to wall-torch shape, lines 34-35, SHA-256 `14551c617a070a3591bfc3fac571ade37bd7f2324c6360e3936ee792a8f9d8da`; `SignBlock.java` post/shape provider, lines 21,37-38, SHA-256 `64d61e8c4758d6f1d13d92e56a25f7599736e625fcfaa980fcb1f8bebfe5ad39`; `WallSignBlock.java` facing map, lines 22-47, SHA-256 `d2040a687cb404d5c8679f79f65491ff829d3d83ef0c555155c0b41748d97b9b`; `StandingBannerBlock.java` post shape, lines 21,35-36, SHA-256 `bc8a64d47d7ed894288c7923f7e6feb1c9c1a0b58ad961c194fc04dbf7793c6d`; `WallBannerBlock.java` facing map, lines 20-57, SHA-256 `70d1fb6b45df1bd86b6a7276cab817472c2c974cad31cb7ac041da3224f04296`; common registration evidence in `Block.java`, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`.
- B evidence: `TorchBlock.java` floor outline, lines 17,24-25, SHA-256 `8430a1058184d9e87596d5ba961ce092e6c77469c642f50e5c901db1a0588aab`; `WallTorchBlock.java` facing shape map/selector, lines 24,48-53, SHA-256 `ff6966b0f5236251c86776c156f5aa57fa214ef50a0a78509a126d916b92db5f`; `RedstoneWallTorchBlock.java` delegates to wall-torch shape helper, lines 35-36, SHA-256 `237ba36f03e040c4ff1531d46887bef719698fe07d580f375947cdf7c4243c16`; `SignBlock.java` post/shape provider, lines 24,40-41, SHA-256 `0094a15d80c84593ebff08e68f0f0d5b9983ead9e289940b1dbdde734929c51c`; `WallSignBlock.java` facing map, lines 23,47-48, SHA-256 `627ac5d3b3eff0ce9bce86ea8e0df288ade8b1b95348d6a8c746b8d59359d566`; `StandingBannerBlock.java` post shape, lines 23,37-38, SHA-256 `b5631687ac64a18de1973d56a8f67c6446718b38674966b68f8ef74323d1ca31`; `WallBannerBlock.java` facing map, lines 22,58-59, SHA-256 `78ab22665aaf9c4700d20b885cc2a640915bf8e59fe98ee58fba7d2d5d0b6930`; common registration evidence in `Blocks.java`, SHA-256 `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`.
- State producers/writers -> consumers/readers: fixed attachment/facing state -> outline provider; registry hasCollision flag -> shared base collision dispatcher -> player movement query. Every cited torch, sign and banner family is registered with `noCollision()` in both versions, so these outline shapes do not become player collision shapes.
- Parent slices / dependencies / closure evidence: S020/S031 paired collision query and `hasCollision` dispatch; D-COLLISION-SHAPES remains open for other providers and external block callbacks.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both registries construct torch, wall/redstone torch, sign/wall-sign, and standing/wall-banner families with `noCollision()`. Their outline shapes are retained for other vanilla queries, but the shared collision provider returns empty for player movement. Shape helpers may differ in routing (the redstone wall torch uses a static helper in B), but both resolve the fixed facing to the same visible outline and cannot clip the player because the collision flag is false. This does not compare lighting, interaction, or writer behavior.
- Finding IDs or checked absence/replacement path: none within these fixed-state no-collision provider families.

### Slice S038: common crop and stem no-collision providers

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: wheat, carrots, potatoes, beetroots, nether wart, melon/pumpkin stems and attached stems registrations, age/facing-dependent outline selectors, and inherited collision output for fixed states. Crop growth, stem attachment and neighboring-fruit updates are excluded.
- A evidence: `WheatBlock.java`::age shape array/selector, lines 19-37, SHA-256 `f8b50a11a50f546f2b474fa20cc272a0534d4276adf94ef5c936e83edfef2d62`; `CarrotsBlock.java`, lines 11-38, SHA-256 `bdff4737ca9912f6e76f6460ac7d8ccb1253861b3544238b1fce5ccc618bb307`; `PotatoesBlock.java`, lines 13-50, SHA-256 `5ec0aa93d727c7eece5232d88f71933dfc3b1228c38e22bc48ede2679c75c573`; `BeetrootsBlock.java`, lines 16-66, SHA-256 `9102e147211d5767e1fcb642553491414579c2ee7908a77c836bd92bdfa46497`; `NetherWartBlock.java`, lines 17-31, SHA-256 `dc403d65587fedcb5b72b3e9ebc44369cb8f4fa1df0855d48318195e4aa9c4f9`; `StemBlock.java`, lines 21-41, SHA-256 `c247cd56d97ff7d39a8436fe9a0396480158b2ead6aa3ee0870953c2ad30e35e`; `AttachedPlantStem.java`, lines 23-44, SHA-256 `9b5c6b3dad10cd26b18492c52cf715d738cf9f9ee9153e81b9495c345863bdbf`; the common `Block.java` registrations set `noCollision()` for all cited variants, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`.
- B evidence: `WheatBlock.java`::age shape array/selector, lines 23-41, SHA-256 `32c765a4e19506214f5450b025d1476da6d8a947143262de633f51c36120e562`; `CarrotsBlock.java`, lines 12-34, SHA-256 `3a72c65249925c2b4b98e77796505eabc3347255b6ed160a5d7bc026eadabe7a`; `PotatoesBlock.java`, lines 12-34, SHA-256 `f831104d5ab09dfedf53372d001a76f332ce827ec869ef659a7a75080a2a9cca`; `BeetrootsBlock.java`, lines 17-62, SHA-256 `83ab558b214807a1c1be064971587477a092b7d09bd3c01768f31807d976aed1`; `NetherWartBlock.java`, lines 17-31, SHA-256 `3a6ece5a35f898d2e00411156162f3ca53bd003a18de3721736be9eecbce2178`; `StemBlock.java`, lines 21-41, SHA-256 `b9035f20363564a7d529864b94e4fe4c255fe0a84f721abad2a847930aeb63aa`; `AttachedPlantStem.java`, lines 22-43, SHA-256 `2870e74dca87b0ca7f6ff615d2450626c4d2e4b70190d8dbed4d2e24d065bb15`; the common `Blocks.java` registrations set `noCollision()` for all cited variants, SHA-256 `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`.
- State producers/writers -> consumers/readers: fixed crop age/stem facing -> class outline provider; registration `hasCollision=false` -> shared base collision dispatcher -> player movement query. The crop and stem outlines cannot clip the player in either version.
- Parent slices / dependencies / closure evidence: S020/S031 paired query and `hasCollision` dispatch; D-COLLISION-SHAPES remains open for other common providers and non-shape movement callbacks.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both registries construct each cited crop/stem family with `noCollision()`. For equal fixed ages, the paired crop selectors use the matching age-indexed outline; for equal attached-stem facing the selector maps to its matching directional outline. B's extra collision-context parameter is unused. Regardless of those outline results, the common base collision path returns empty for all variants, so player collision geometry is equal. Growth and state-writer behavior are not compared.
- Finding IDs or checked absence/replacement path: none within these fixed-state crop/stem collision families.

### Slice S039: aquatic plants, coral and pickle shapes

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: fixed-state seagrass/tall-seagrass and coral plant/fan/wall-fan no-collision registrations; sea-pickle count-to-outline selection; lily-pad fixed outline and inherited collision shape. Plant growth, waterlogging, coral survival and block-state update lifecycle are excluded.
- A evidence: `SeaGrassBlock.java`::constant/shape, lines 26,33-34, SHA-256 `a3019943a0a5a61055f4731566446e662b532b75e950d8f8676ada28ff4f5b10`; `TallSeaGrassBlock.java`, lines 25,32-33, SHA-256 `0876535db8acade7e4295c6044355452b936d8af99845dea71145553af35fe52`; `SeaPickleBlock.java`::four shapes/selector, lines 26-29,88-97, SHA-256 `1b232549de646d46b2f986d78802b0f7208978e103f00bd98fe64e4fe47d76a4`; `LilyPadBlock.java`, lines 15,30-31, SHA-256 `741784cff408619ec5f262366b481c92bd5892d329fc2f77393e3faeae6397eb`; `CoralPlantBlock.java`, lines 15,49-50, SHA-256 `1d4599b6c1e43ade173cba46cbbc17f5bc0c42a4996ac5fa8a11a9cd1c988956`; inherited coral outlines: `BaseCoralFanBlock.java`, lines 9,16-17, SHA-256 `a330d4276a97b14c5038ddd817f11f976b7061d4ef004f608fa4e7c53d285516`; `BaseCoralWallFanBlock.java`, lines 21-41, SHA-256 `12326ecd456026b98d1b4471f54cf5f399763e33833bb4224c3e88592a4ecef0`; `CoralFanBlock.java` inheritance, lines 11-12, SHA-256 `26570c87d6bde252c8c126181fd80e64f25ac8d56f0726bfd1a09080e7ef9a22`; `CoralWallFanBlock.java` inheritance, lines 11-12, SHA-256 `f85288a635e27c9222ccde668b8580f0ec715b9f8f661e3fe3aef34f7f02bd12`; registrations in `Block.java` cover these families, including noCollision for seagrass/coral and collidable sea pickle/lily pad, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`.
- B evidence: `SeaGrassBlock.java`::constant/shape, lines 21,28-29, SHA-256 `26307e005f90a8930afba830d5f928b7a178cd081280420bb5384fc4f3f3c3d3`; `TallSeaGrassBlock.java`, lines 23,30-31, SHA-256 `51230c1b8da12187b0296ee55a459a3656df3647ce817d8f1ccf104ed522f9a0`; `SeaPickleBlock.java`::four shapes/selector, lines 26-29,88-97, SHA-256 `8adcb2c2bf60478e41ca2542ac9e661172e8c3d1bac9674fb293eca4d164e12f`; `LilyPadBlock.java`, lines 16,31-32, SHA-256 `044fb0551c40490da3507e21ebcc2af81b82f6f5477df324681b07635f9875e5`; `CoralPlantBlock.java`, lines 16,50-51, SHA-256 `378e4139703495c115aa30cdb046f01d16ed1e28d86d395c4681d62e2ac97f0f`; inherited coral outlines: `BaseCoralFanBlock.java`, lines 10,17-18, SHA-256 `7c90eb5d706a2a5471d14fd5b6637268d2d322c09e4f31d1ab13b4440198cbd0`; `BaseCoralWallFanBlock.java`, lines 22-42, SHA-256 `91ed215892e79082363b375cae70225abe5f4b545296f13cab4c3a2418c36f03`; `CoralFanBlock.java` inheritance, lines 11-12, SHA-256 `d019c3615b51076dec48d238621fe6c8c7031ed65dfddb275b34c630188318b2`; `CoralWallFanBlock.java` inheritance, lines 11-12, SHA-256 `29f213cca08e66d999aac9ce555ead0aa217a2c738c81124be8a738d97359a89`; registrations in `Blocks.java` cover these families, including noCollision for seagrass/coral and collidable sea pickle/lily pad, SHA-256 `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`.
- State producers/writers -> consumers/readers: fixed seagrass/coral/pickle-count/facing state -> outline provider -> inherited collision path. Seagrass and coral have `hasCollision=false`, so their player collision output is empty. Sea pickle selects the same count-indexed geometry in both versions; lily pad uses the same 1.5/16-high outline and collidable base path.
- Parent slices / dependencies / closure evidence: S020/S031 paired query and `hasCollision` dispatch; D-COLLISION-SHAPES remains open for other common providers and callbacks.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): registrations for seagrass, tall seagrass and coral plants/fans/wall-fans use `noCollision()` in both versions. Their fixed outlines do not clip the player. Sea-pickle shape constants and pickle-count branches match; lily-pad shape constants match and the registration leaves the default collision flag enabled in both versions. B's added collision-context parameters are unused. This compares fixed shape inputs, not water/plant state writers or fluid movement paths.
- Finding IDs or checked absence/replacement path: none within these fixed-state aquatic plant providers.

### Slice S040: player sprint air-speed baseline and flight override

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: `PlayerEntity.mobTick` writes the ordinary air-speed baseline after `super.mobTick`, applies the sprint multiplier, then sets the movement-speed attribute value; ability flight temporarily overrides `speedInAir`, calls `moveRelative`, restores the saved value and clears fall distance. The A `flyingSpeed` field has no other vanilla source-tree reads/writes than its declaration and these assignments.
- A evidence: `PlayerEntity.java` field, `mobTick` air-speed assignments/order, lines 135,480-495, SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`; active flight override and restore, lines 1456-1461, same hash.
- B evidence: `PlayerEntity.java` final default field, `mobTick` air-speed assignments/order, lines 150,509-520, SHA-256 `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df`; active flight override and restore, lines 1396-1404, same hash.
- State producers/writers -> consumers/readers: player sprint state and fixed default air-speed -> `speedInAir` -> `LivingEntity` air travel coefficient (S005 bounded consumer); ability flying state -> temporary abilities fly-speed override -> `moveRelative` -> restore baseline.
- Parent slices / dependencies / closure evidence: S001/S005 movement consumer; S029 client state ingress. D-TICK-CLOSURE and D-MOVEMENT-DATA remain open for surrounding tick callers, other attribute/effect chains and server ability synchronization.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the vanilla `flyingSpeed` field is initialized to `0.02F` and has no other vanilla source-tree writer. A sets `speedInAir` from that value; B writes the same literal. Under sprint, A adds `flyingSpeed * 0.3`, which with the stored `0.02F` is the same double addend `0.005999999865889549` B uses before the same float cast. Both perform this after `super.mobTick`. In the ability-flight branch, both temporarily use `abilities.getFlySpeed() * (isSprinting() ? 2 : 1)`, call the matching `moveRelative` overload, multiply vertical velocity by `0.6`, restore prior air speed and clear fall distance. The source-visible field mutability difference has no vanilla writer/use path beyond this default computation. No difference is established for vanilla player inputs; mod-side reflective/subclass mutation is outside scope.
- Finding IDs or checked absence/replacement path: none within the vanilla player air-speed baseline and flight override slice.

### Slice S041: sprint movement-speed attribute modifier

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: sprint modifier declaration and `LivingEntity.setSprinting(boolean)` removal/re-add sequence; operation application order in `ModifiableEntityAttributeInstance.computeValue`; `PlayerEntity.getSpeed()` reads the resolved movement-speed attribute. This does not close all movement-speed consumers or attribute producers.
- A evidence: `LivingEntity.java` modifier declaration lines 96-98 and `setSprinting` lines 1349-1359, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`; `ModifiableEntityAttributeInstance.java` application lines 147-165, SHA-256 `4eec5f0347baba347a84e4c66702321fb34157329f95e70e8ce541aeb4bf6b7a`; `PlayerEntity.java`::`getSpeed`, lines 1486-1490, SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`.
- B evidence: `LivingEntity.java` modifier declaration lines 113-117 and `setSprinting` lines 1640-1650, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`; `AttributeModifier.java` operation enum lines 86-89 maps `MULTIPLY_TOTAL` to ordinal 2, SHA-256 `8dcb59bf00043beda44a4f69e1905dbb281219984c2725c1e269a95e0920b7d6`; `ModifiableEntityAttributeInstance.java` application lines 141-158, SHA-256 `4ff7dd1bf7a48e4894860cb048bc9c6a8adcc8b98b9c5d29ce28da2a15db8f69`; `PlayerEntity.java`::`getSpeed`, lines 1423-1427, SHA-256 `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df`.
- State producers/writers -> consumers/readers: sprint state gates replacement of the movement-speed modifier (same UUID, name, amount `0.3F`, and operation 2); resolved movement-speed attribute is read by `PlayerEntity.getSpeed()` and other movement paths whose complete inventory remains open.
- Parent slices / dependencies / closure evidence: S001/S040 provide paired sprint-input and sprint-air-speed context. D-TICK-CLOSURE and D-MOVEMENT-DATA remain open for call ordering, base movement-speed writers, server synchronization and other modifiers/equipment.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both versions call `super.setSprinting`, fetch the movement-speed attribute, remove the existing modifier with the sprint UUID when present, then add the declared modifier only when `sprinting` is true. A passes operation integer `2`; its calculator applies operation 2 after additions and base multipliers using `e *= 1.0 + amount`. B names operation 2 `MULTIPLY_TOTAL` and applies it with the same arithmetic after the same preceding stages. Both `PlayerEntity.getSpeed()` methods return the movement-speed attribute value cast to float. No difference is established for this sprint modifier path; the broader source of base movement speed and all other modifier consumers are not closed.
- Finding IDs or checked absence/replacement path: none within the sprint movement-speed modifier path.

### Slice S042: Speed and Slowness movement-speed modifiers

- Inventory ID(s): INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Speed and Slowness effect declarations and their movement-speed attribute modifiers; effect amplifier scaling and replacement/removal behavior in `StatusEffect.addModifiers` / `removeModifiers`. Effect synchronization and other effect mechanics are outside this slice.
- A evidence: `StatusEffect.java`::`init`, Speed/Slowness registrations and modifiers, lines 209-222, SHA-256 `60cc53e7233d28413d3c93292fd216babe8ea9edeb70774141b68101c515c639`; `removeModifiers`, `addModifiers` and `getModifier`, lines 169-198, same hash.
- B evidence: `StatusEffects.java` Speed/Slowness declarations and modifiers, lines 13-25, SHA-256 `46cf6a77e95de8be46feb39b258ef1eb4fd2d9f34538bd2d027bb9d58427500f`; `StatusEffect.java` `addModifiers`, `removeModifiers` and `getModifier`, lines 139-170, SHA-256 `9648b2d33f1adefafff71e1c14fa6427194ad2cff4ff242a4b2db355daa71085`.
- State producers/writers -> consumers/readers: server-synchronized active effect state (S029) selects the Speed or Slowness modifier; the effect amplifier scales the configured amount by `amplifier + 1`, then the movement-speed attribute calculator applies it as a total multiplier (S041).
- Parent slices / dependencies / closure evidence: S029 compares the client ingress/map for synchronized effect state; S041 compares attribute operation application. Full effect lifecycle call order and all attribute modifiers remain open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both versions register Speed with UUID `91AEAA56-376B-4498-935B-2F7F68070635`, amount `0.2F`, operation 2, and Slowness with UUID `7107DE5E-7CE8-4030-940E-514C1F160890`, amount `-0.15F`, operation 2. Both `addModifiers` methods remove the prior modifier and add a new one using the same UUID, translation-key-plus-amplifier name, `modifier.get() * (amplifier + 1)`, and stored operation; both `removeModifiers` methods remove each configured effect modifier. A's operation 2 is the total multiplier per S041's calculator comparison, matching B's `MULTIPLY_TOTAL`. No difference is established for these two movement-speed effects; effect lifecycle ordering and other modifiers remain unclosed.
- Finding IDs or checked absence/replacement path: none within Speed and Slowness movement-speed modifiers.

### Slice S043: player walk-speed attribute base

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: player movement-speed base initialization in `initAttributes`, and the `mobTick` server-only writer from `abilities.getWalkSpeed()` after `super.mobTick`, before the resolved attribute value is passed to `setSpeed`. Ability value production/synchronization and other attribute consumers remain open.
- A evidence: `PlayerEntity.java`::`initAttributes`, movement-speed base `0.1F`, lines 156-162; `mobTick`, server-only `getWalkSpeed()` write and resolved-value read, lines 479-492; SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`.
- B evidence: `PlayerEntity.java`::`initAttributes`, movement-speed base `0.1F`, lines 188-194; `mobTick`, server-only `getWalkSpeed()` write and resolved-value read, lines 507-520; SHA-256 `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df`.
- State producers/writers -> consumers/readers: player construction writes base `0.1F`; server-side `mobTick` replaces that base with externally supplied `abilities.getWalkSpeed()`; the resulting movement-speed attribute value is passed to `setSpeed`, while the complete travel consumers remain under inventory.
- Parent slices / dependencies / closure evidence: S040 covers adjacent player air-speed work; S041 compares sprint modifiers; S042 compares Speed/Slowness modifiers. D-TICK-CLOSURE and D-EXTERNAL remain open for the full call graph and ability value ingress.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both versions register the player's movement-speed base to `0.1F` in `initAttributes`. In `mobTick`, both call `super.mobTick()`, fetch the movement-speed attribute, and only when `!world.isClient` set its base from `abilities.getWalkSpeed()`. Both then pass the resolved attribute value, cast to float, to `setSpeed`. The producer of the server-provided walk-speed value and its network synchronization are not compared here. No difference is established for this bounded default/writer path.
- Finding IDs or checked absence/replacement path: none within player movement-speed base initialization and the server-only walk-speed writer.

### Slice S044: snow-layer cube classification

- Inventory ID(s): INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: `SnowLayerBlock.isCube(BlockState)` for fixed layer counts and its source-tree call sites. The collision-shape constants and selectors are separately compared in S021.
- A evidence: `SnowLayerBlock.java`::`isCube`, lines 62-65, returns `LAYERS == 8`, SHA-256 `12b2d50064fd306c09007bc9a3629cc14a540123ce37e3b53a7f6615c9915df2`; `Block.java` predicates consuming `state.isCube()`, lines 261-281, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`; source-tree uses include Enderman block-carry eligibility at line 456, SHA-256 `fabb9bd7e626d5d702dbda055ef5b06f79ea3858dc2d9a78a212b0f8e78c81c8`, structure-template block handling at line 84, SHA-256 `cae0f13ef607290ef362ad7d434943cdb04c53bd9d35cd8dc1f490d181cf7b59`, entity rendering at line 196, SHA-256 `9872eb91f21d5c7c0921db503d7d056aba6044db861a62a334ef39d34711fef0`, and block rendering at lines 199-219, SHA-256 `00d483521e601e743e5948484c70936c2e0e677b8fa7daadf7f71101b3d63cef`.
- B evidence: `SnowLayerBlock.java`::`isCube`, lines 64-67, returns `true`, SHA-256 `2307bd4ad1b41210c8c82fddad03dea2e6867b87e7539b9249a6ed49960f6dcb`; `BlockState.java` caches the block result during state construction, lines 58-63 and 99-103, SHA-256 `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f`; source-tree uses include `World.setBlockState` light-update scheduling, lines 180-190, SHA-256 `498a72cdd4bd035cd731d2c49fce401bef439d6f92f498501577d9cfc59ab487`, `ProtoChunk` block processing, lines 151-162, SHA-256 `34ed9fb796dac37f30bd2ce33cfa4ec739335bf387e796e2f16fafd4575a2c1c`, and block rendering in `C_71862855.java`, lines 92,111-112, SHA-256 `2b01c3154384e2088272e5566162b8fe60dca2564c0d0c8c7eaf6f60c4978c4b`.
- State producers/writers -> consumers/readers: layer property -> `isCube` classification; source-tree consumers are block predicates, non-player Enderman block-carry eligibility, block light-update/chunk processing, structure-template handling, and rendering. S020's player block-collision enumerator consumes `getCollisionShape` directly; the fixed layer collision shapes/selectors match per S021.
- Parent slices / dependencies / closure evidence: S020 bounds the player block-collision query; S021 compares snow-layer collision geometry; D-COLLISION-SHAPES remains open for other providers and callback paths.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): the source values differ (`LAYERS == 8` in A, unconditional `true` in B), but the collision shape returned for each fixed layer value is unchanged and S020's player query consumes that shape without consulting `isCube`. The paired source-tree consumers place this classification in non-player Enderman behavior, block light-update scheduling, block predicates outside the player collision path, and chunk processing. Those behaviors do not produce a direct player movement response in this scope; block-state and world-processing effects are also excluded. Retain this as a checked source difference with no player-movement disposition.
- Finding IDs or checked absence/replacement path: none; outside the direct player-movement scope.

### Slice S045: end-rod fixed-facing collision geometry

- Inventory ID(s): INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: `EndRodBlock` axis-selected shape constants and `getShape(BlockState)`; inherited collision-shape delegation and default collision flag for the registered End Rod block.
- A evidence: `EndRodBlock.java` constants and facing-axis selector, lines 16-19, 36-47, SHA-256 `e7ba3191e3303d94987648d57384bab787226c5a8914cc5f72e92fc2ffb8545d`; `Block.java` default collision shape delegates to the block state's shape and defaults `hasCollision` to true, lines 393-395 and 1901-1902, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`; A registration is `end_rod` with `Material.DECORATION` properties and no `noCollision`, line 1584 of the same file.
- B evidence: `EndRodBlock.java` constants and facing-axis selector, lines 17-20, 37-48, SHA-256 `e876070e1ed5bded108d67d9d641e1152e2393bbeee71867a672c8920e17490e`; `Block.java` default collision shape delegates to the block state's shape and defaults `hasCollision` to true, lines 376-378 and 811-812, SHA-256 `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`; B `END_ROD` registration uses the same `Material.DECORATION` properties and no `noCollision`, lines 1407-1410 of `Blocks.java`, SHA-256 `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`.
- State producers/writers -> consumers/readers: fixed `FACING` axis -> shared X/Y/Z end-rod outline shape -> inherited collision delegation -> player world block-collision query (S020). Registration leaves the shared default collision flag enabled in both versions.
- Parent slices / dependencies / closure evidence: S020 bounds player block-collision enumeration. D-COLLISION-SHAPES remains open for other shared/default providers and their registrations.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both versions define the same three 4/16-thick axis-aligned rods: vertical `SHAPE_Y`, Z-axis `SHAPE_Z`, and X-axis `SHAPE_X`. Both selectors choose by `FACING.getAxis()` with the same switch mapping. Neither version overrides `getCollisionShape`; the paired base implementations delegate to `state.getShape(...)` when collision is enabled, and the matching registrations retain the default enabled collision flag. No fixed-facing collision geometry difference is established.
- Finding IDs or checked absence/replacement path: none within this fixed-state End Rod geometry slice.

### Slice S046: bed collision shape by facing and half

- Inventory ID(s): INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: A `BedBlock.getShape(BlockState,BlockView,BlockPos)` fixed slab; B `BedBlock.getShape(BlockState,BlockView,BlockPos,CollisionContext)` facing/part-selected mattress and support boxes; inherited collision-shape delegation and bed registrations.
- A evidence: `BedBlock.java` fixed `SHAPE` at lines 38-42 and `getShape` at lines 209-212, SHA-256 `a274ed3d3dd8e4f4895fe1f3d1444425a9248e9cd41bc6e1f2677af7dacbec18`; `Block.java` collision delegation/default enabled collision at lines 393-395 and 1901-1902, and white-bed registration at line 901, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`.
- B evidence: `BedBlock.java` shape constants at lines 48-56 and `getShape` selector at lines 180-192, SHA-256 `aab431c7d85bc654b74215d2b2be5fbe6de81643f5e2be466658d918f272abe1`; `Block.java` collision delegation/default enabled collision at lines 376-378 and 811-812, SHA-256 `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`; white-bed registration at lines 212-214 of `Blocks.java`, SHA-256 `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`.
- State producers/writers -> consumers/readers: bed `FACING` and `PART` state -> B selects the head/foot support pair plus common mattress; inherited collision path -> S020 block-shape query -> player displacement resolution (S007-S009).
- Parent slices / dependencies / closure evidence: S007-S009 player entity movement; S020 block-collision enumeration; D-COLLISION-SHAPES remains open for the complete provider set and conditional world-query outcomes.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F020. Both versions register beds with collision enabled and route player block-collision queries through the shape returned by `BedBlock`. A returns one full-footprint box from y=0 to 9/16. B uses a full-footprint mattress from y=3/16 to 9/16 and selects two 3/16-by-3/16 corner supports from y=0 to 3/16 according to facing and `BedPart`; the adjacent head/foot half supplies the other pair. Thus at a fixed bed state, the lower collision footprint differs. The player query can reach this provider when movement bounds overlap a bed; the exact displacement result remains conditional on query position and other shapes.
- Finding IDs or checked absence/replacement path: F020.

### Slice S047: cauldron lower collision shape

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: state-independent `CauldronBlock` shape construction and `getShape`; compare `ONLY_FIRST` subtraction operand and inherited collision-enabled registration.
- A evidence: `CauldronBlock.java` shape construction and `getShape`, lines 32-45, SHA-256 `6948b2e208e1d7a52123fece400aa717b6fa5c35189fff1137490a96c170a3ac`; `BooleanOp.java` `ONLY_FIRST = (a, b) -> a && !b`, line 8, SHA-256 `7c8739dc92118e1e4d9cc563c6a9b07780f3c7da4f80f3fd0ccda65dd3e5c470`; `Block.java` default collision flag `true` and cauldron registration, lines 1216 and 1901-1902, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`.
- B evidence: `CauldronBlock.java` shape construction and `getShape`, lines 33-52, SHA-256 `686519b540dcb313c5226ebae2f77e902eb1d5b76997ba8fdf8cd37ab772179e`; `BooleanOp.java` `ONLY_FIRST = (a, b) -> a && !b`, line 8, same SHA-256; `Blocks.java` cauldron registration, line 756, SHA-256 `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`; `Block.java` default collision flag `true`, lines 811-812, SHA-256 `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`.
- State producers/writers -> consumers/readers: registered cauldron -> constant shape independent of `LEVEL` -> inherited collision delegation -> S020 player block-shape query -> displacement resolution (S007-S009).
- Parent slices / dependencies / closure evidence: S020 block collision enumeration and S007-S009 player movement; D-COLLISION-SHAPES remains open for the complete provider set and conditional world-query outcomes.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): F021. Both versions subtract their second shape from the full-block shape using `ONLY_FIRST` and return the result from `getShape`; registration and inherited collision enablement match. A subtracts only the interior box beginning at y=4/16, leaving a full bottom slab from y=0 to4/16. B also subtracts three boxes spanning y=0 to3/16: two cross-shaped strips and a central square, while retaining the same interior box from y=4/16 upward. The B lower geometry therefore leaves four corner L-shaped supports through y=3/16 and a continuous full-footprint plate from y=3/16 to4/16. The shape can reach player collision queries wherever bounds overlap the cauldron; exact displacement effects depend on player position and nearby shapes.
- Finding IDs or checked absence/replacement path: F021.

### Slice S048: brewing stand, cake and portal-frame collision shapes

- Inventory ID(s): INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: fixed-state shape constants and selectors for bottle-state-independent Brewing Stand shape, Cake bite-indexed shapes and Eye-of-Ender-selected End Portal Frame shape; inherited collision delegation and registrations.
- A evidence: `BrewingStandBlock.java` shape and selector, lines 33 and 50-58, SHA-256 `0ff465b18dc0377c1a1a123ad083f04d07f77cea8a0bc88a218a67d750f886d3`; `CakeBlock.java` seven bite shapes and selector, lines 21-41, SHA-256 `f9464e8a2f328c21b788654dc81115ba236c31984e93949f69df2b2dace5fd6c`; `EndPortalFrameBlock.java` base/eye/combined shapes and selector, lines 23-39, SHA-256 `c196aeba3dd6c9683659ab08b71a759b448709e8cc45e68aca448a7e6e470523`; registrations and collision-enabled default at lines 1090,1215,1221,1901-1902 of `Block.java`, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`.
- B evidence: `BrewingStandBlock.java` shape and selector, lines 34 and 51-54, SHA-256 `ccda744516bdf232db4ed600bd468833640ac56a4b559725f220cd838c283761`; `CakeBlock.java` seven bite shapes and selector, lines 21-41, SHA-256 `72f4d89064e007e24a98465e67631928d41a90e8fa581f189b8de4d5863f7905`; `EndPortalFrameBlock.java` base/eye/combined shapes and selector, lines 22-43, SHA-256 `4346ef0aa80c4d40edc7f33c65a72cc4428190c02af16516d9df5084ed2be4db`; registrations at lines 596,755,762-765 of `Blocks.java`, SHA-256 `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`; collision-enabled default at lines 811-812 of `Block.java`, SHA-256 `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`.
- State producers/writers -> consumers/readers: bottle flags do not select Brewing Stand shape; Cake `BITES` selects its horizontal footprint; End Portal Frame `EYE` selects whether the top eye box is included; each returned shape reaches inherited collision delegation and S020's player block-shape query. End Portal Frame `isCube` is a separate predicate and not dispositioned in this slice.
- Parent slices / dependencies / closure evidence: S020 player block-collision enumeration; D-COLLISION-SHAPES remains open. End Portal Frame cube classification is queued separately.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the paired Brewing Stand cuboid union, all seven Cake bite cuboids and bite-index selector, and the End Portal Frame base/eye/combined boxes and eye selector have identical dimensions and return conditions. Their registrations keep collision enabled. No collision-shape difference is established in these three bounded providers; the separate End Portal Frame `isCube` predicate difference is routed to S049.
- Finding IDs or checked absence/replacement path: none within these three collision-shape providers.

### Slice S049: End Portal Frame cube classification

- Inventory ID(s): INV-STATE, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: `EndPortalFrameBlock.isCube` classification; distinguish this block predicate from the fixed collision-shape selector compared in S048 and the player block-shape consumer in S020.
- A evidence: `EndPortalFrameBlock.java`::`isCube`, lines 76-79, SHA-256 `c196aeba3dd6c9683659ab08b71a759b448709e8cc45e68aca448a7e6e470523`.
- B evidence: `EndPortalFrameBlock.java`::`isCube`, lines 36-39, SHA-256 `4346ef0aa80c4d40edc7f33c65a72cc4428190c02af16516d9df5084ed2be4db`.
- State producers/writers -> consumers/readers: End Portal Frame block instance -> `isCube` boolean; S044's paired callsite inventory categorizes the predicate's source-tree readers, including rendering/general block predicates, A's Enderman block-carry eligibility, and B's world light-update/chunk processing. The direct player collision path consumes `getCollisionShape` through S020; S048 establishes that selector and geometry are unchanged.
- Parent slices / dependencies / closure evidence: S020 and S048; S044 source-tree callsite categories; D-COLLISION-SHAPES and INV-EXCLUSIONS remain open.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): A returns false and B returns true for this predicate, but it does not select or alter the player collision shape. The player block-collision query uses the unchanged shape path documented in S020/S048. The identified uses are general block/render classification, non-player Enderman eligibility in A, and light/chunk processing in B, outside direct player movement scope. No in-scope player-movement delta is established by this predicate.
- Finding IDs or checked absence/replacement path: none; the separate collision shape is dispositioned in S048.
## Dependency queue and blockers

- D-TICK-CLOSURE: full local tick/pre-travel/travel branches/post-travel; S019 adds the post-move auto-jump callback, S030 pairs slime contact callbacks, S031 pairs bubble-column block callbacks, S040 bounds the player air-speed writers and temporary flight override, S041 compares the sprint modifier writer, and S043 compares the walk-speed base writer; surrounding call-order closure remains open.
- D-COLLISION-SHAPES: world queries, fixed-state shape providers, registrations and movement callbacks; S019 records the auto-jump entity query, S020 compares block enumerators, S021 checks four static providers, S022 compares the normal piston source guard, S023-S026 retain excluded wall/fence lifecycle evidence, S027/S028 compare fixed-state fence and iron-bars geometry, S030 pairs slime callbacks, S031 compares bubble-column no-collision registration/response, S032 compares stairs/slabs/doors/trapdoors, S033 compares inherited concrete-powder geometry, S034 compares kelp outline/no-collision dispatch, S035 compares carpet/farmland/grass-path/pressure-plate shapes, S036 compares rail/tripwire/control/ladder geometry, S037 compares torch/sign/banner no-collision providers, S038 compares common crop/stem no-collision providers, S039 compares aquatic plant/coral/pickle/lily-pad shapes, S045 compares the inherited End Rod collision shape, S048 compares Brewing Stand/Cake/End Portal Frame geometry, S049 routes the separate End Portal Frame cube predicate outside player collision, and F020/F021 record the bed and cauldron shape deltas; remaining common-era providers and callbacks remain open.
- D-MOVEMENT-DATA: effects/attributes/enchantments/equipment and block-property producer chains remain open; S029 closes only the paired client ingress/map representation for Jump Boost, Slow Falling and Levitation, S026 records remote tag values, S040 closes the vanilla player air-speed default/sprint writer, S041 compares the sprint movement-speed modifier and its operation application, S042 compares Speed/Slowness modifier declarations and amplifier scaling, and S043 compares the walk-speed base writer while leaving its server-supplied value external; inspect remaining movement-data readers.
- D-EXTERNAL: direct player velocity/impulse/knockback writers plus correction/push/piston/mount/launch-item consumers and state inputs; S018 closes only the bounded client player-correction velocity reset, S019 records entity-obstacle input to local auto-jump, S026 remote block-tag replacement, S029 effect-state ingress, S030 slime response, S031 bubble-column response and S043 the server-ability walk-speed attribute input; other corrections/callers and external influences remain open; exclude combat causation and non-player/vehicle physics.
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
- [F020](findings/F020-bed-collision-shape-change.md) bed collision footprint; candidate source-confirmed geometry delta; exact player displacement outcomes remain conditional on overlap and surrounding shapes; independent review open.
- [F021](findings/F021-cauldron-collision-shape-change.md) cauldron lower collision geometry; candidate source-confirmed shape delta; exact player displacement outcomes remain conditional on overlap and surrounding shapes; independent review open.

## Resume checkpoint

- Last completed slices: S026 fence connection-state predicate (excluded lifecycle), S027 fixed-state fence collision geometry (no difference), S028 fixed-state iron-bars collision geometry (no difference), S029 server-synchronized effect-state ingress (no difference), S030 player slime response (no difference), S031 bubble-column response/fixed-state collision (no difference), S032 fixed-state stairs/slabs/doors/trapdoors geometry (no difference), S033 fixed-state concrete-powder geometry (no difference), S034 kelp fixed-state collision (no difference), S035 carpet/farmland/grass-path/pressure-plate geometry (no difference), S036 rail/tripwire/control/ladder geometry (no difference), S037 torch/sign/banner no-collision shapes (no difference), S038 crop/stem no-collision geometry (no difference), S039 aquatic plant/coral/pickle/lily-pad geometry (no difference), S040 player air-speed baseline/flight override (no difference), S041 sprint movement-speed attribute modifier (no difference), S042 Speed/Slowness movement-speed modifiers (no difference), S043 player walk-speed attribute base (no difference), S044 snow-layer cube classification (outside scope), S045 end-rod fixed-facing collision geometry (no difference), S046 bed collision shape by facing and half (F020 candidate), S047 cauldron lower collision shape (F021 candidate), S048 Brewing Stand/Cake/End Portal Frame collision shapes (no difference), and S049 End Portal Frame cube classification (outside scope); F019/S023/S025 remain source-only lifecycle evidence.
- Next bounded work: enumerate common-era fixed-state collision providers, then continue the full tick/state/external inventories. Exact source roots are the manifest paths above. S030/S031 cited hashes are recorded in their evidence; verify those files against ready manifests at handoff without rehashing entire trees.
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

- Coverage counts by status: findings=17 slices (18 deltas); compared-no-difference=26; pending inventory closure=7; in-progress=0; not-applicable=6; blocked=0.
- Required inventory status and evidence: all seven inventories pending; paired evidence in S001-S049.
- Accepted finding snapshots: none; F002 snapshot event F002-2026-10-07-01 is submitted and awaits exact-snapshot blind confirmation.
- Open dependencies: D-TICK-CLOSURE,D-COLLISION-SHAPES,D-MOVEMENT-DATA,D-EXTERNAL,D-INDEPENDENT-AUDIT.
- Unresolved gaps and limits: full tick, shapes/resources, modifier chains, external writers beyond S016, and independent audit. First changed release unknown within (1.13.2,1.14.4].
- Evidence/hash/correspondence audit: readiness markers, source manifests and S025 cited Java hashes verified; corrected the shortened A and B `BlockState.java` hashes in S023/F019; cited body ranges paired; full helper/call graph closure incomplete.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).
