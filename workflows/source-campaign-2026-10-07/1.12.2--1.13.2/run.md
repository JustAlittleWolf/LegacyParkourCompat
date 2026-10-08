# Discovery: 1.12.2 to 1.13.2

- Run status: active
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
- Initial validation: both ready markers reported status ready and the exact versionId; source-manifest, artifact-manifest and diagnostic hashes matched their JSON records; the then-current client, mapped-client and mapping artifacts matched their manifests. A later reproducibility rerun replaced shared-cache derived mapped JARs while preserving source-file/raw-input hashes. This discovery did not rewrite shared markers or cache. Resolution: the source owner supplied revision feather-r1-2026-10-07 immutable snapshots; this worker independently rehashed every source file against each source manifest, every original raw input except the unavailable mutable derived JAR, and both revision snapshots against revision records/manifests. The independent ops audit passed. Snapshot paths and hashes are cited in affected findings. Limitation: the original mapped JARs are unavailable, so snapshot equivalence to the original mapped bytecode (or metadata-only differences) is unproven; retain this limitation and do not claim original mapped-bytecode identity. Success excerpts are summary excerpts, not full stdout.
- Cited source-file hashes are repeated in each coverage slice. Original jar resources and referenced resource hashes are still to be inventoried; none have been cited. Required external/server data and provenance remain open.

## Artifact evidence identities

### Evidence artifact EA-FEATHER-R1-A

- Publication status: revised-derived
- Revision ID: `feather-r1-2026-10-07`
- Immutable evidence path: `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/client-ornithe-feather.jar`
- Evidence artifact SHA-256: fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87
- Evidence manifest path: `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/artifact.sha256`
- Evidence manifest SHA-256: 162170b94be0fa6393a515ea3beed10210b2c86a006366cf9d351eba136bae2d
- Revision record path / SHA-256: `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/revision.json`, `2be8645d57ca5c00411b037e7617f9860ac700f7fa8ef50d28220c2e1b0c60dc`
- Original artifact-manifest path: `ready/1.12.2/artifacts.sha256`
- Original artifact-manifest SHA-256: 8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c
- Original derived-artifact availability: unavailable
- Original derived-artifact SHA-256 or expected hash: `65a08f15d18c4ec2bd0f05b89dfc1ba7ea6b8280ba93ed136245f39e63ec8a2b`
- Source/raw-input hash relation: revision record says source tree and raw inputs are identical to original; this worker independently verified all 2,050 source files and 37/37 original raw inputs except the unavailable mutable derived JAR. Source manifest `ready/1.12.2/ornithe-feather.sources.sha256`, SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- Revised-to-original derived-artifact equivalence: unverified; original derived JAR is unavailable. Snapshot provenance does not establish byte identity or metadata-only changes.
- Provenance limitations: derived source snapshot only; do not claim original mapped-bytecode identity.

### Evidence artifact EA-FEATHER-R1-B

- Publication status: revised-derived
- Revision ID: `feather-r1-2026-10-07`
- Immutable evidence path: `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`
- Evidence artifact SHA-256: b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c
- Evidence manifest path: `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/artifact.sha256`
- Evidence manifest SHA-256: 4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40625601868692c7dd8
- Revision record path / SHA-256: `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/revision.json`, `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`
- Original artifact-manifest path: `ready/1.13.2/artifacts.sha256`
- Original artifact-manifest SHA-256: fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e
- Original derived-artifact availability: unavailable
- Original derived-artifact SHA-256 or expected hash: `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5`
- Source/raw-input hash relation: revision record says source tree and raw inputs are identical to original; this worker independently verified all 2,711 source files and 41/41 original raw inputs except the unavailable mutable derived JAR. Source manifest `ready/1.13.2/ornithe-feather.sources.sha256`, SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- Revised-to-original derived-artifact equivalence: unverified; original derived JAR is unavailable. Snapshot provenance does not establish byte identity or metadata-only changes.
- Provenance limitations: derived source snapshot only; do not claim original mapped-bytecode identity.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending full source comparison and independent closure.
- Evidence inventory and finding IDs included at freeze: pending.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; one prior same-pair source-discovery report/findings set was opened only as navigation. No mod implementation or wiki audit was opened.
- Source/mapping hashes covered by freeze: pending.

## Correspondence and call order

Pair is Feather Gen2 build 2 on both exact releases. Resolved hierarchy on both: net.minecraft.client.entity.living.player.LocalClientPlayerEntity -> net.minecraft.client.entity.living.player.ClientPlayerEntity -> net.minecraft.entity.living.player.PlayerEntity -> net.minecraft.entity.living.LivingEntity -> net.minecraft.entity.Entity. A and B files are separate; movement-member correspondence is checked by callers and behavior, not name alone.

A-side entry route observed: LocalClientPlayerEntity.tick() (lines 165-179) calls PlayerEntity.tick() (174-247), then LivingEntity.tick() (1619 onward), which calls Entity.tick()/baseTick() before virtual dispatch to LocalClientPlayerEntity.mobTick() (649-817). LocalClientPlayerEntity.mobTick() samples KeyboardInput.tick() (13-41), applies item-use scaling and sprint/flying gates, then calls superclass mobTick methods. LivingEntity.mobTick() (1781-1853) calls LocalClientPlayerEntity.serverTickAi() (631-642) to copy input fields into movement speeds/jumping, then jump gates, input damping, LivingEntity.moveRelative(), and post-travel push handling. This route is provisional: B order, overrides, and all pre/post-travel dependencies remain under audit.

## Required source inventories

Each inventory maps to bounded slice IDs. An inventory remains pending until every listed member/data slice and producer-consumer route is dispositioned.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=KB-SAMPLE, CLIENT-TICK, PLAYER-TICK, INPUT-TRANSFER, ITEM-USE-SCALE, SPRINT-STATE, FLIGHT-TOGGLE, AUTOJUMP, JUMP-GATES, VELOCITY-CUTOFF, TRAVEL-INPUT-DAMPING, POST-TRAVEL; evidence=source-only opening route recorded above; B call order and remaining slice ranges pending.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, support, timers and direct predicates: status=pending; slice_ids=POSE-DIMENSIONS, SWIM-STATE, FLUID-HEIGHT, VELOCITY-WRITERS, EYE-HEIGHT, SPRINT-JUMP-TIMERS, STAIRS-SHAPE-STATE-FORMULA, TRAPDOOR-COLLISION-GEOMETRY; evidence=movement source members to inventory.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=SNEAK-EDGE, ENTITY-MOVE-AXES, ENTITY-AXIS-ORDER, STEP-CANDIDATES, SUPPORT-CALLBACKS, WORLD-COLLISIONS, SHAPE-PROVIDERS, FENCE-GATE-COLLISION, FENCE-COLLISION-GEOMETRY, FENCE-CONNECTION-EXCEPTION, PANE-COLLISION-GEOMETRY, PANE-CONNECTION-EXCEPTION, PANE-COLLISION-AXIS-EPSILON, TRAPDOOR-COLLISION-GEOMETRY, WALL-SINGLE-ARM-COLLISION, WALL-OPPOSITE-ARMS-UP-FALSE, WALL-THREE-ARM-CORNER-GAPS, STAIRS-SHAPE-STATE-FORMULA, STAIRS-PROVIDER; evidence=Entity.move and most shape-source inventory open.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=BLOCK-REGISTRATIONS, COLLISION-SHAPES, BLOCK-CALLBACKS, FLUID-STATE, FLUID-FLOW, BUBBLE-COLUMNS, NEIGHBOR-SHAPES, FENCE-GATE-COLLISION, FENCE-COLLISION-GEOMETRY, FENCE-CONNECTION-EXCEPTION, PANE-COLLISION-GEOMETRY, PANE-CONNECTION-EXCEPTION, PANE-COLLISION-AXIS-EPSILON, TRAPDOOR-COLLISION-GEOMETRY, WALL-SINGLE-ARM-COLLISION, WALL-OPPOSITE-ARMS-UP-FALSE, WALL-THREE-ARM-CORNER-GAPS, STAIRS-SHAPE-STATE-FORMULA, STAIRS-PROVIDER; evidence=full class/registration/resource inventory pending.
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

- Resolved artifact dependency: revised Feather snapshots independently verified against `artifact.sha256`, `revision.json`, original source manifests, and raw input manifests; independent ops audit passed on 2026-10-07. Original derived JARs remain unavailable and their equivalence is unproven.

- Open dependencies: SRC-MANIFEST-ROWS (this discovery owner): populate and close every required member-level slice from all seven inventories; SOURCE-RESOURCE-ENTRIES (this discovery owner): inspect matching original jar entries, data, tags and defaults; METHODS-DIAGNOSTICS (this discovery owner): resolve movement-related decompiler diagnostics and use the immutable revised snapshots for ambiguous descriptors; INDEPENDENT-REVIEWER (coordinator): name a non-author to re-walk the inventories and full call graph after freeze; IMPLEMENTATION-RECONCILIATION (coordinator/integrator): defer until blind source report is frozen.

## Finding index

Source-confirmed bounded findings: `F-GROUND-ACCEL` (grounded coefficient and concrete default-friction scalar difference; snapshot review pending), `F-VOXEL-OFFSET-EPSILON` (paired full-cube collision sequence with a sub-1e-7 residual; independent finding review pending), `F-WALL-SINGLE-ARM-GAPS` (one-arm cobblestone-wall collision corners; independent source review pending), `F-WALL-THREE-ARM-CORNER-GAPS` (three-arm cobblestone-wall corner; finding-file SHA-256 `ba6ca32660018d45058804370694e67e576081c2f4f6d49f58b7fed72e843fd1`; independent source review pending), `F-FENCE-FROSTED-ICE-CONNECTION` (A connects to the historical Frosted Ice neighbor while B excludes it; finding-file SHA-256 `3a37e6ddb1a0ba3e95a99289005a84f0361d84ca33516e22aaa9906061ee3884`; independent source review pending), and `F-PANE-OFFSET-EPSILON` (pane center-voxel orthogonal interval inset; immutable finding snapshot recorded below; independent source review pending). The opposite-arm, UP=false wall collision comparison and the fence occupied-volume comparison found no difference within their bounded state/geometry scopes. The full `TRAVEL-MODIFIERS`, `ENTITY-MOVE-AXES`, and pair remain active.

Provisional source candidates being traced after revised snapshot verification: 1.13 water/sprint/swimming gates; shallow-water jump and tagged-fluid jump; swimming pose and water-relative pitch acceleration; Slow Falling gravity and fall-distance state; changes to elytra acceleration/clamps; water movement multipliers (sprint, Depth Strider, Dolphin's Grace); broader collision shape/axis resolution, step candidates and provider changes beyond the bounded epsilon case. These are navigation checkpoints, not yet the exhaustive findings catalog. Modern-only equipment mechanics such as Riptide are separately classified against 1.12.2 scope.

## Resume checkpoint

- This continuation resumed from preserved branch tip `f744f3e5ca418ba8797341eca50ab1677933d1d7` in its dedicated managed worktree.
- Current resumed worktree: `D:\Javastuff\LegacyParkourCompat\.task-worktrees\source-discovery-1-12-2-1-13-2-resume`; exact resumed tip `1c027c68c8bcbe72b0891ee213458c14c32d3dff`; first resumed documentation checkpoint `2adc7be0be299265c4e8cecd33554d76a42eba4e`.
- Handoff status: partial and resumable; blind source comparison remains active. Current task branch: `feat/source-discovery-movement-source-1-12-2-1-13-2`. Latest source-confirmed finding snapshot: `F-PANE-OFFSET-EPSILON` at `2adc7be0be299265c4e8cecd33554d76a42eba4e`; its independent finding review is pending.
- Source identity: Feather Gen2 build 2 for both releases; immutable revised snapshots revision `feather-r1-2026-10-07`. Continue using `build/movement-campaign-2026-10-07/ready/1.12.2/ornithe-feather` and `.../ready/1.13.2/ornithe-feather` for verified source files, and retain the original-derived-JAR identity limitation above. A's source files, source manifest, artifact manifest, diagnostics and provenance were reverified; its live ready marker remains `status=ready` with the same source/artifact/diagnostics digests and file count, but its marker-file SHA-256 is `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a`, differing from the run's recorded marker digest `b0aeec721e5c0af02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`. B's marker matches its recorded digest. Shared ready files were not modified; this marker-only digest drift is logged as a nonblocking provenance dependency while all verified inputs remain unchanged.
- Last completed work: sixteen bounded no-difference slices, four collision findings, and eleven in-progress slices are listed under Source audit closure. Recent bounded work covers opposite wall arms, stair shape-state decisions and provider geometry, the pane neighbor predicate, the shared Y/X/Z axis order, and a source-derived pane center-voxel epsilon finding. The wall provider has source-confirmed corner gaps for one-arm and three-arm states. The earlier bounds-only no-difference statement was corrected and its history is recorded under Source audit closure. Swimming eye-height evidence was added to `POSE-DIMENSIONS` and `F-SWIM-POSE.md`. The collision collector and offset helpers have exact paired coverage, including `F-VOXEL-OFFSET-EPSILON` and `F-PANE-OFFSET-EPSILON`. Pane flag callback/persistence lifecycle remains open but is excluded as a difference by itself per user direction. The fence's remaining exception/resource inventory remains open. `F-GROUND-ACCEL` and `F-PANE-OFFSET-EPSILON` have source snapshots awaiting separate review. Independent review remains pending. The full `TRAVEL-MODIFIERS` and `ENTITY-MOVE-AXES` slices remain in progress.
- Next bounded slice: continue `ENTITY-MOVE-AXES` through sneak-edge candidate probes and step-candidate comparison, then proceed through remaining in-progress producer/consumer slices and required inventories. Keep findings bounded to the cited states and queries.
- Read-only commands to resume that slice in PowerShell:
  ```powershell
  $ready = 'D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready'
  Get-Content "$ready\1.12.2\ornithe-feather\net\minecraft\world\World.java" | Select-Object -Skip 963 -First 105
  Get-Content "$ready\1.13.2\ornithe-feather\net\minecraft\world\WorldView.java" | Select-Object -Skip 111 -First 92
  Get-Content "$ready\1.13.2\ornithe-feather\net\minecraft\world\World.java" | Select-Object -Skip 1754 -First 8
  ```
- Other pending source work: complete client/player tick and state-writer call graph, pose/size writers, all collision shape providers and neighbors, world movement properties/resources, remaining effect/enchantment/equipment paths, all external player-input routes, movement decompiler diagnostics, and explicit excluded-system audit. Keep findings provisional until every required inventory is closed.
- Required later steps: finish blind pair coverage; run the structural completion checker; obtain an independent source reviewer; freeze the source report; only then inspect implementation for reconciliation. No implementation or wiki lane was opened in this task.
- Git integration: `main` was merged into this branch on 2026-10-07 and was already an ancestor (`git merge --no-edit main` returned `Already up to date`). `main` had no commits beyond the pair's base, so there were no intervening semantic changes to reconcile.

## Finding snapshots (not pair freeze)

### Snapshot event F-GROUND-ACCEL-2026-10-08

- Snapshot status: source-confirmed; independent blind finding review pending.
- Snapshot commit: 2dae7b235bde47c7837fd4092ec67ccb014352f2.
- Finding file SHA-256: 6d217c9984cc02b85370d3892b9094a5454e43f4c42b2e93e6b044ebff5cac0c.
- Publication status: revised-derived
- Evidence artifact record IDs: EA-FEATHER-R1-A, EA-FEATHER-R1-B
- Revision ID(s): feather-r1-2026-10-07 on both sides
- Immutable evidence path(s): revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/client-ornithe-feather.jar; revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar
- Evidence artifact SHA-256(s): A fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87; B b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c
- Evidence manifest path(s): A revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/artifact.sha256; B revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/artifact.sha256
- Evidence manifest SHA-256(s): A 162170b94be0fa6393a515ea3beed10210b2c86a006366cf9d351eba136bae2d; B 4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40625601868692c7dd8
- Original artifact-manifest path(s): ready/1.12.2/artifacts.sha256; ready/1.13.2/artifacts.sha256
- Original artifact-manifest SHA-256(s): A 8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c; B fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e
- Original derived-artifact availability/hash: unavailable; expected original mapped JAR SHA-256 A 65a08f15d18c4ec2bd0f05b89dfc1ba7ea6b8280ba93ed136245f39e63ec8a2b, B d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5
- Source/raw-input hash relation: source files cited by F-GROUND-ACCEL were rehashed against unchanged ready manifests; revision records describe source trees and raw inputs as identical, with all original raw inputs except unavailable derived JARs verified. Full publication verification is recorded above.
- Revised-to-original equivalence: unverified because original derived JARs are unavailable.
- Provenance limitations: revised derived snapshots are verified; original mapped-bytecode identity and metadata-only equivalence are unproven.
- Source-proven historical behavior boundary/evidence (phase, producers/consumers, applicability), or unresolved source question: the grounded branch in LivingEntity.moveRelative(FFF)V changes the scalar passed to updateVelocity; the local-player call path, guards, 0.6F friction denominator and one-ULP multiplier difference are cited in F-GROUND-ACCEL.md.
- Finding-specific closed dependency IDs/evidence: TRAVEL-MODIFIERS grounded branch and direct caller ranges; BLOCK-FRICTION shared default and historical assigned values; bounded CLIENT-TICK input transfer. See F-GROUND-ACCEL.md source hashes and exact ranges.
- Independent blind source reviewer and decision date: reviewer 01a116ce-b44d-75f0-974f-5038e0d227c7; decision pending.
- Review basis / requested source-only revisions: verify the exact paired expression, local player reachability/guards, shared default slipperiness result, and artifact/source identities. Do not inspect implementation or wiki material.
- Pair run status and commit at handoff: active; snapshot commit 2dae7b235bde47c7837fd4092ec67ccb014352f2.
- Pair complete: no.
- Implementation handoff: blocked pending reviewer acceptance of this exact snapshot.
- Replaces/supersedes snapshot ID and reason: none.

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

- Accepted finding snapshots (metadata only; does not close pair): none accepted yet. `F-GROUND-ACCEL` source snapshot `2dae7b235bde47c7837fd4092ec67ccb014352f2` awaits independent review. `F-PANE-OFFSET-EPSILON` immutable file snapshot: commit `2adc7be0be299265c4e8cecd33554d76a42eba4e`, path `workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-PANE-OFFSET-EPSILON.md`, git blob `3772fad4aac1ed8a5dab267b2799669f72622bb4`, file SHA-256 `4c7a90a573e72f4d819a65348cb027bba1a51ffd45b41fbc5db53c84e40faf66`; independent blind review pending. `F-VOXEL-OFFSET-EPSILON` has no snapshot and awaits separate review.
- Coverage counts by status: 16 compared-no-difference (KB-SAMPLE, VELOCITY-PACKETS, BLOCK-FRICTION, PLAYER-PUSH-PRIMITIVE, PLAYER-CORRECTION, PISTON-DELTA-CLAMP, DEPTH-STRIDER, FENCE-GATE-COLLISION, WALL-OPPOSITE-ARMS-UP-FALSE, STAIRS-SHAPE-STATE-FORMULA, FENCE-COLLISION-GEOMETRY, PANE-COLLISION-GEOMETRY, TRAPDOOR-COLLISION-GEOMETRY, PANE-CONNECTION-EXCEPTION, STAIRS-PROVIDER, ENTITY-AXIS-ORDER); 4 findings (WALL-SINGLE-ARM-COLLISION, WALL-THREE-ARM-CORNER-GAPS, FENCE-CONNECTION-EXCEPTION, PANE-COLLISION-AXIS-EPSILON); 11 in-progress bounded slices (CLIENT-TICK, SPRINT-STATE, SWIM-STATE, JUMP-GATES, POSE-DIMENSIONS, TRAVEL-MODIFIERS, VELOCITY-CUTOFF, ENTITY-MOVE-AXES, WORLD-COLLISIONS, SUPPORT-CALLBACKS, ATTRIBUTES/SPEED-SLOWNESS/JUMP-BOOST); remaining required member/resource slices not yet enumerated; all seven required inventories pending.
- Evidence correction: commit `a97526b` marked `WALL-SINGLE-ARM-COLLISION` no-difference after comparing only outer bounds. A topology check of B's OR-built VoxelShape found missing corner cells in the same bounded shape. The current source finding supersedes that conclusion; no immutable finding snapshot or independent reviewer decision existed for the earlier entry. Correction commit `bab1bada3fb4f133d5548aa046d1773c98ba14da` records `findings/F-WALL-SINGLE-ARM-GAPS.md` (SHA-256 `9da2a9cb0a5876389d65ebc67e0efbd898dee21677c3ac0cdb04b28bc1b2c063`); independent source review: pending.
- Required inventory status and evidence: all pending; see inventory map above.
- Open dependencies: SRC-MANIFEST-ROWS, SOURCE-RESOURCE-ENTRIES, METHODS-DIAGNOSTICS, INDEPENDENT-REVIEWER, IMPLEMENTATION-RECONCILIATION. Resolved: revised artifact snapshot verification (feather-r1-2026-10-07; ops audit passed); original derived-artifact identity remains a limitation.
- Unresolved gaps and limits: full paired tick sequence, all state writers/consumers, all block/fluid collision shapes, modifiers/equipment, external inputs, resources, and reviewer audit remain open.
- Evidence/hash/correspondence audit: readiness/artifact/source manifest chain verified; new wall and fence finding files are identified by SHA-256 `ba6ca32660018d45058804370694e67e576081c2f4f6d49f58b7fed72e843fd1` and `3a37e6ddb1a0ba3e95a99289005a84f0361d84ca33516e22aaa9906061ee3884`; cited source files and member ranges match the ready manifests.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).

The run folder contains only run.md and, when findings exist, one file per finding under findings/. The completion checker verifies structure and statuses only; the independent source audit establishes evidence quality.
### Slice CLIENT-TICK and PLAYER-TICK: paired client/player tick entry and input transfer

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: LocalClientPlayerEntity.tick() A 165-179 / B 181-195 and PlayerEntity.tick() A 174-247 / B 176-256; the applicable PlayerEntity.tick() calls `super.tick()` and updates player pose, while LocalClientPlayerEntity performs its client tick around that superclass route. Source routes then enter Entity.tick/baseTick, LivingEntity.tick/baseTick, and virtual `mobTick`; exact client/server call-order remains pending bytecode/diagnostic reconciliation where decompiler member names differ.
- A evidence: LocalClientPlayerEntity.java SHA-256 01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc; PlayerEntity.java SHA-256 e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e.
- B evidence: LocalClientPlayerEntity.java SHA-256 2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf; PlayerEntity.java SHA-256 4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633.
- State producers/writers -> consumers/readers: client tick and player tick -> inherited Entity/LivingEntity tick/baseTick -> virtual LocalClientPlayerEntity.mobTick -> serverTickAi copies current input to LivingEntity speed/jump fields -> LivingEntity.mobTick jump/travel.
- Parent slices / dependencies / closure evidence: KB-SAMPLE; input transfer and tick-body call-order still need terminal exact ranges and diagnostics closure.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): 1.13.2 adds water-state preparation in the PlayerEntity/Entity tick route (tracked separately as SWIM-STATE/FLUID-HEIGHT); no global call-order equivalence is claimed while this inventory remains open.
- Finding IDs or checked absence/replacement path: F-WATER-STATE.

### Slice SPRINT-STATE, FLIGHT-TOGGLE, and WATER-DESCENT: local input decisions

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: LocalClientPlayerEntity.mobTick() A 694-782 / B 698-807; setSprinting() A 444-447 / B 458-461; serverTickAi() A 631-642 / B 635-646. Differences are ordered in the same tick after KeyboardInput.tick() and item-use scaling and before superclass travel.
- A evidence: LocalClientPlayerEntity.java SHA-256 01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc.
- B evidence: LocalClientPlayerEntity.java SHA-256 2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf.
- State producers/writers -> consumers/readers: key state -> `input`; `input.jumping/sneaking/movementForward`, onGround, sprint flag, collision flag, water/submerged/swimming flags, canFly and food level -> sprint/flight decisions -> movement field copy and LivingEntity travel. Food level is only the vanilla sprint eligibility predicate here; food/exhaustion simulation is excluded.
- Parent slices / dependencies / closure evidence: KB-SAMPLE, SWIM-STATE, FLUID-HEIGHT, ITEM-USE-SCALE; prove state writers/timing before source closure.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): A sprint starts via ground-only double tap or held sprint and stops when forward input, horizontal collision, or food/canFly gate fails. B permits double-tap while on ground or submerged, held sprint when not in water or submerged, distinguishes swimming sprint cancellation from ordinary sprint cancellation, and prevents flight double-tap toggle while swimming. B additionally applies `knockDownwards()` whenever the player is in water and sneaking (B lines 785-787); the A body has no corresponding call. These are reachable local-player movement changes; B's separate underwater visibility counter is not classified as movement.
- Finding IDs or checked absence/replacement path: F-WATER-SPRINT; F-FLIGHT-TOGGLE; F-SNEAK-WATER-DESCENT.

### Slice SWIM-STATE and FLUID-HEIGHT: water sampling and swimming transition

- Inventory ID(s): INV-STATE, INV-TICK, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Entity.baseTick() A 328 onward / B 340 onward; B Entity helper m_03231680() lines 947-951, checkWaterState() lines 961-975 and updateSwimming() lines 953-959; PlayerEntity.updateSwimming() B 1471-1476; water-depth sampler m_69693160(Tag<Fluid>) B 2509-2570 and getter m_02485546() B 2573-2575. A Entity.checkWaterState() lines 944-946 delegates to World.applyLiquidDrag(..., Material.WATER, entity); `checkWaterCollisions()` A 948-964 updates inWater.
- A evidence: Entity.java SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a; World.java SHA-256 e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594.
- B evidence: Entity.java SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269; PlayerEntity.java SHA-256 4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633.
- State producers/writers -> consumers/readers: B fluid-state grid intersection/flow calculation writes inWater and raw `f_85121000` sampled depth; Entity base tick then caches submerged-in-water state and updates swimming; PlayerEntity suppresses swimming when flying; LivingEntity.mobTick consumes sampled depth for jump selection; LocalClientPlayerEntity consumes submerged/swimming predicates for sprint and flight. Source timing indicates base-tick sampling precedes local input decisions; owner diagnostics and mapped-jar correspondence for the raw helper remain an open dependency.
- Parent slices / dependencies / closure evidence: CLIENT-TICK, JUMP-GATES, FLUID-STATE, FLUID-FLOW, WATER-SPRINT; resource/flow closure pending.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): A uses the legacy material drag callback to set water contact and has no submerged-depth/swimming state. B samples fluid states and summed flow over the entity box, stores a maximum depth, and has a distinct swimming transition (already swimming uses sprint+inWater; entering uses sprint+isSubmergedInWater; both require not riding). This directly changes player gates; the sampler's exact bytecode/member-name pairing still requires closure before freeze.
- Finding IDs or checked absence/replacement path: F-WATER-STATE; F-FLUID-DRAG.

### Slice JUMP-GATES: water-height-dependent jump selection

- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: LivingEntity.mobTick() A 1827-1839 / B 1903-1917; A jumpInWater()/jumpInLava() and B m_74407200(Tag<Fluid>) exact descriptor `(Lnet/minecraft/tag/Tag;)V` confirmed in B mapped jar. The source method adds `0.04F` to velocityY for WATER and LAVA tags.
- A evidence: LivingEntity.java SHA-256 190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6.
- B evidence: LivingEntity.java SHA-256 bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c.
- State producers/writers -> consumers/readers: KeyboardInput/local input -> jumping field; onGround/cooldown and B fluid depth -> either water/lava impulse or ordinary jump -> velocityY -> travel. B depth is sourced by F-WATER-STATE.
- Parent slices / dependencies / closure evidence: SWIM-STATE, FLUID-HEIGHT, JUMP-BOOST, VELOCITY-CUTOFF.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): A applies a water or lava upward impulse whenever the respective `isInWater`/`isInLava` predicate holds; otherwise a grounded jump is accepted when cooldown is zero. B routes positive depth above 0.4 to water impulse, allows a ground jump in shallow depth `(0, 0.4]` when cooldown is zero, checks lava only in its non-water branch, and otherwise performs ordinary grounded jump. This creates a new water-height threshold on the reachable player jump path.
- Finding IDs or checked absence/replacement path: F-WATER-JUMP.

### Slice POSE-DIMENSIONS and SWIM-ACCELERATION

- Inventory ID(s): INV-STATE, INV-COLLISION, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: PlayerEntity.updatePlayerPose() A 291-315 / B 334-361; getEyeHeight() A 1756-1768 / B 1859-1870; PlayerEntity.moveRelative() A 1386-1410 / B 1442-1487; LivingEntity.moveRelative() A 1425-1618 / B 1478-1688. B swimming-or-spin-attack pose assigns 0.6-wide/0.6-high dimensions and checks collision fit through `world.hasNoCollisions(null, box)`; A has neither pose branch. B swimming eye height is 0.4F. B PlayerEntity.moveRelative adds pitch-dependent vertical motion when swimming and not riding, using look-vector Y and a 0.085/0.06 coefficient selected by the -0.2 pitch threshold.
- A evidence: PlayerEntity.java SHA-256 e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e; LivingEntity.java SHA-256 190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6.
- B evidence: PlayerEntity.java SHA-256 4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633; LivingEntity.java SHA-256 bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c.
- State producers/writers -> consumers/readers: swimming state and pose preference -> collision-tested dimension transition -> getShape/eye height/collision queries; look pitch, swimming, riding and jumping -> additive velocityY -> later travel/collision.
- Parent slices / dependencies / closure evidence: SWIM-STATE, WORLD-COLLISIONS, ENTITY-MOVE-AXES, SUPPORT-CALLBACKS.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B adds reachable swim pose/dimensions, sets swimming eye height to 0.4F, and adds direct swimming vertical acceleration. The main pose method and player eye-height override are now bounded; all other dimension writers and collision-query equivalence remain to be cataloged, so the parent inventory stays open.
- Finding IDs or checked absence/replacement path: F-SWIM-POSE; F-SWIM-LOOK.

### Slice TRAVEL-MODIFIERS: slow falling, elytra, fluid travel, ground/air coefficients

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: LivingEntity.travel()/m_74407200 body A 1425-1618 / B 1478-1688; modifiers are read in the body, with Depth Strider via EnchantmentHelper and B Dolphin's Grace, Slow Falling, Levitation and Elytra branches. Need split terminal findings by elytra, water, lava, land, effects, and exact float operation order before freeze.
- A evidence: LivingEntity.java SHA-256 190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6.
- B evidence: LivingEntity.java SHA-256 bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c.
- State producers/writers -> consumers/readers: status effects/equipment/enchantment and block slipperiness -> travel branch gravity/friction/acceleration -> velocity and Entity.move.
- Parent slices / dependencies / closure evidence: ELYTRA, SLOW-FALLING, LEVITATION, DEPTH-STRIDER, DOLPHINS-GRACE, ATTRIBUTES, COLLISION-SHAPES.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B initializes gravity factor `d=0.08`; Slow Falling with nonpositive Y sets it to 0.01 and clears fallDistance. Elytra vertical acceleration is expressed as `d * (-1.0 + n * 0.75)` instead of A's `-0.08 + m * 0.06`; the B pitch-up branch also adds a positive-horizontal-look guard before division. Water travel adds sprint/Dolphin's Grace handling, conditional gravity and terminal downward clamp; ground/air acceleration changes `0.16277136F` to `0.16277137F`; lava uses `d / 4.0`. These are source candidates requiring one finding per branch and direct effect/enchantment registration closure.
- Finding IDs or checked absence/replacement path: F-SLOW-FALLING (modern-only disposition); F-ELYTRA-GLIDE-MATH; F-ELYTRA-LOOK-GUARD; F-WATER-GRAVITY; F-WATER-SPRINT-TRAVEL; F-GROUND-ACCEL.

### Slice VELOCITY-CUTOFF and POST-TRAVEL

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: LivingEntity.mobTick() A 1781-1853 / B 1852-1936. Three per-axis `<0.003` velocity zeroing checks remain textually equivalent, with shifted surrounding order. Both damp local sideways/forward movement by `0.98F`, rotation by `0.9F`, call flyingTick then moveRelative, and push colliding entities. B additionally snapshots shape before travel and performs size-change collision adjustment when spin-attack timer is positive.
- A evidence: LivingEntity.java SHA-256 190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6.
- B evidence: LivingEntity.java SHA-256 bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c.
- State producers/writers -> consumers/readers: external/network velocity and earlier travel -> threshold zeroing -> jump/input/travel; Riptide spin timer and pose/shape -> post-travel collision adjustment; push-away callback -> velocity writer audit.
- Parent slices / dependencies / closure evidence: VELOCITY-PACKETS, PLAYER-PUSHES, LAUNCH-ITEMS, RIPTIDE, ENTITY-MOVE-AXES.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): `<0.003` is a no-difference bounded sub-slice; B's head-yaw interpolation is not a movement input, while its spin-attack shape adjustment is player-reachable only from 1.13 equipment and remains a modern-only disposition pending call closure. Push implementation remains open.
- Finding IDs or checked absence/replacement path: modern-only spin-attack shape adjustment (pending exact item/source trace; not in 1.12.2 scope).

### Slice ENTITY-MOVE-AXES and STEP-CANDIDATES

- Inventory ID(s): INV-COLLISION, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Entity.move() A 462-796 / B 473-782; includes sneak-edge probes, collision collection, vertical/X/Z offset resolution, step alternatives, flags, support checks and callbacks.
- A evidence: Entity.java SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a.
- B evidence: Entity.java SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269.
- State producers/writers -> consumers/readers: requested velocity/move type and current shape -> world collision candidates -> Y then X then Z clipping; step candidates may replace clipped horizontal path based on squared distance -> position, collision flags, onGround/support, velocity zeroing and block callbacks.
- Parent slices / dependencies / closure evidence: WORLD-COLLISIONS, SHAPE-PROVIDERS, BLOCK-CALLBACKS, NEIGHBOR-SHAPES.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): A queries `List<Box>` through `World.getCollisions` and intersects candidates sequentially; B queries voxel shapes and applies `VoxelShapes.calculateMaxOffset` per axis. Sneak-edge probe decrements by 0.05 in both. Vertical, X, Z axis order is retained; step alternatives use changed shape representation. Full semantic comparison requires helper algorithm, shape providers, border/world collision collection and block callback closure.
- Finding IDs or checked absence/replacement path: F-COLLISION-REPRESENTATION; F-VOXEL-OFFSET-EPSILON is the bounded source-confirmed epsilon consequence, with independent review pending.

### Slice ENTITY-AXIS-ORDER: sequential Y/X/Z movement resolution

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `Entity.move` movement-query setup and the primary vertical, X, and Z clipping blocks, A lines 567-601 and B lines 578-599.
- A evidence: `Entity.java#move`, SHA-256 `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`.
- B evidence: `Entity.java#move`, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- State producers/writers -> consumers/readers: requested movement components -> Y offset against current box -> updated box -> X offset -> updated box -> Z offset -> updated box.
- Parent slices / dependencies / closure evidence: ENTITY-MOVE-AXES, WORLD-COLLISIONS. This slice compares control-flow order only; collision-candidate enumeration, axis-offset arithmetic, step candidates, and callbacks remain in their owning slices.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): when supplied with nonzero corresponding movement components and the same candidate list/shape stream, both player-reachable `Entity.move` bodies resolve Y before X before Z. Each axis uses the box resulting from the preceding axis, and each position-shape update is guarded on that axis's resulting displacement being nonzero. This is a control-flow comparison only; it makes no claim that the A/B candidate sets or clipping helpers return equal offsets.
- Finding IDs or checked absence/replacement path: none for the ordered axis-control slice; helper/representation differences remain tracked by `F-COLLISION-REPRESENTATION`, `F-VOXEL-OFFSET-EPSILON`, and `F-PANE-OFFSET-EPSILON`.

### Slice WORLD-COLLISIONS: block and entity candidate collection

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: A `World.getCollisions(Entity,Box)` and private collector A 964-1015, 1017-1040; B `WorldView.getBlockCollisions(VoxelShape,VoxelShape,boolean)` 112-152, `WorldView.getCollisions` overloads 154-178, `World.getCollisions(Entity,VoxelShape,VoxelShape,Set)` 1755-1758, and inherited `EntityView.getEntityCollisions` 21-36. Reachable `Entity.move` calls A 525-547, 567, 613 and B 525-575, 580, 612 use the collector for sneak-edge probes and movement/step candidates.
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/world/World.java` SHA-256 `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`; `Entity.java` SHA-256 `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`.
- B evidence: `ready/1.13.2/ornithe-feather/net/minecraft/world/WorldView.java` SHA-256 `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`; `World.java` SHA-256 `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a`; `EntityView.java` SHA-256 `2b5b59eef6efa93bb51c1d838c7a1cf64725be96c55c47f76894fde11e13d6de`; `Entity.java` SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- State producers/writers -> consumers/readers: requested player displacement and current player Box -> block-state collision-shape providers, world-border/chunk handling and nearby-entity collision shapes -> axis offset resolver and step/sneak probes -> player position, collision flags and velocity updates in `Entity.move`.
- Parent slices / dependencies / closure evidence: ENTITY-MOVE-AXES, STEP-CANDIDATES, SHAPE-PROVIDERS, BLOCK-REGISTRATIONS, NEIGHBOR-SHAPES, SUPPORT-CALLBACKS, PLAYER-PUSHES, MOUNT-TRANSITIONS. Source digests above match the ready source manifests; revised artifact publication identity is recorded under EA-FEATHER-R1-A/B.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): A scans a floor/ceil position range with a pooled mutable position, filters chunk-corner positions, handles border cases, calls each block state's `addCollisions` with the query Box, then appends intersecting entity and entity-against-player Boxes except same-vehicle entities. B constructs an expanded VoxelShape query from the player Box and displacement using a `1.0E-7` offset, subtracts the initial shape for movement queries, gathers block collision shapes through `WorldView`, and appends entity collision shapes through `EntityView` after `excludeEntities` and same-vehicle filtering. B's block collector also coalesces out-of-border cells into one boundary shape. This confirms a changed collection algorithm and representation on the reachable player path; whether candidate filtering/order, shape conversion, border aggregation or offset-helper semantics changes a player result remains open pending provider and resolver comparison.
- Finding IDs or checked absence/replacement path: F-COLLISION-REPRESENTATION remains candidate; this slice supplies collector evidence but does not establish a concrete old-block outcome.

### Slice SUPPORT-CALLBACKS

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Entity.move() A 695-796 / B 675-782; setPositionFromShape, collision/onGround flags, support block lookup and stepped-on/beforeCollision callbacks.
- A evidence: Entity.java SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a.
- B evidence: Entity.java SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269.
- State producers/writers -> consumers/readers: resolved axis offsets -> position and flags -> support block at floor(y-0.2), fence/wall/gate fallback, fall-distance callback, velocity component zeroing, beforeCollision, stepped-on and step sounds.
- Parent slices / dependencies / closure evidence: ENTITY-MOVE-AXES, SHAPE-PROVIDERS, BLOCK-CALLBACKS, FALL-DISTANCE (damage excluded except direct movement state write).
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): post-resolution path has shared callback ordering with changed source APIs (`Material.AIR`/Box-era versus `isAir`/voxel-shape-era); all support block shape and callback implementations are not yet closed.
- Finding IDs or checked absence/replacement path: F-COLLISION-REPRESENTATION.

### Shape-provider discovery inventory (enumeration, not closure)

- 1.12.2 block shape-provider source members (discovery list; method-by-method A/B disposition remains open): AbstractPressurePlateBlock.java, AbstractRailBlock.java, AirBlock.java, AnvilBlock.java, BannerBlock.java, BedBlock.java, BeetrootsBlock.java, Block.java, BlockProperties.java, BrewingStandBlock.java, ButtonBlock.java, CactusBlock.java, CakeBlock.java, CarpetBlock.java, CarrotsBlock.java, CauldronBlock.java, ChestBlock.java, ChorusPlantBlock.java, CobwebBlock.java, CocoaBlock.java, DaylightDetectorBlock.java, DeadBushBlock.java, DiodeBlock.java, DoorBlock.java, DoublePlantBlock.java, DragonEggBlock.java, EnchantingTableBlock.java, EnderChestBlock.java, EndGatewayBlock.java, EndPortalBlock.java, EndPortalFrameBlock.java, EndRodBlock.java, FarmlandBlock.java, FenceBlock.java, FenceGateBlock.java, FireBlock.java, FlowerBlock.java, FlowerPotBlock.java, GrassPathBlock.java, HopperBlock.java, LadderBlock.java, LeverBlock.java, LilyPadBlock.java, LiquidBlock.java, MovingBlock.java, MovingBlockEntity.java, MushroomPlantBlock.java, NetherWartBlock.java, PaneBlock.java, PistonBaseBlock.java, PistonHeadBlock.java, PlantBlock.java, PortalBlock.java, PotatoesBlock.java, PumpkinBlock.java, RedstoneWireBlock.java, SaplingBlock.java, ShulkerBoxBlock.java, ShulkerBoxBlockEntity.java, SignBlock.java, SkullBlock.java, SlabBlock.java, SnowLayerBlock.java, SoulSandBlock.java, StateDefinition.java, StemBlock.java, StructureVoidBlock.java, SugarCaneBlock.java, TallPlantBlock.java, TorchBlock.java, TrapdoorBlock.java, TripwireBlock.java, TripwireHookBlock.java, VineBlock.java, WallBlock.java, WallSignBlock.java, WheatBlock.java.
- 1.13.2 block shape-provider source members (discovery list; method-by-method A/B disposition remains open): AbstractPressurePlateBlock.java, AbstractRailBlock.java, AirBlock.java, AnvilBlock.java, AttachedPlantStem.java, BaseCoralFanBlock.java, BaseCoralPlantBlock.java, BaseCoralPlantTypeBlock.java, BaseCoralWallFanBlock.java, BedBlock.java, BeetrootsBlock.java, Block.java, BlockState.java, BrewingStandBlock.java, ButtonBlock.java, CactusBlock.java, CakeBlock.java, CarpetBlock.java, CarrotsBlock.java, CarvedPumpkinBlock.java, CauldronBlock.java, ChestBlock.java, CocoaBlock.java, ConcretePowderBlock.java, ConduitBlock.java, CoralPlantBlock.java, DaylightDetectorBlock.java, DeadBushBlock.java, DiodeBlock.java, DoorBlock.java, DragonEggBlock.java, EnchantingTableBlock.java, EnderChestBlock.java, EndPortalBlock.java, EndPortalFrameBlock.java, EndRodBlock.java, FarmlandBlock.java, FenceGateBlock.java, FireBlock.java, FloorSkullBlock.java, FlowerBlock.java, FlowerPotBlock.java, GrassPathBlock.java, HopperBlock.java, HopperBlockEntity.java, KelpBlock.java, KelpPlantBlock.java, LadderBlock.java, LeverBlock.java, LilyPadBlock.java, LiquidBlock.java, MovingBlock.java, MovingBlockEntity.java, MushroomPlantBlock.java, NetherWartBlock.java, PaneBlock.java, PipeBlock.java, PistonBaseBlock.java, PistonHeadBlock.java, PortalBlock.java, PotatoesBlock.java, RedstoneWallTorchBlock.java, RedstoneWireBlock.java, SaplingBlock.java, SeaGrassBlock.java, SeaPickleBlock.java, ShulkerBoxBlock.java, ShulkerBoxBlockEntity.java, SignBlock.java, SlabBlock.java, SnowLayerBlock.java, SoulSandBlock.java, StairsBlock.java, StandingBannerBlock.java, StemBlock.java, StructureVoidBlock.java, SugarCaneBlock.java, TallPlantBlock.java, TallSeaGrassBlock.java, TorchBlock.java, TrapdoorBlock.java, TripwireBlock.java, TripwireHookBlock.java, TurtleEggBlock.java, VineBlock.java, WallBannerBlock.java, WallBlock.java, WallSignBlock.java, WallSkullBlock.java, WallTorchBlock.java, WheatBlock.java, WitherFloorSkullBlock.java.

The source-level provider enumeration is driven by every `getShape`/`getCollisionShape` occurrence under each version's `net/minecraft/block` package, including state-dependent shapes. It must still be normalized into overrides versus call sites, paired by registry identity, and checked against constructors/default properties and neighbors. The 1.13.2 WorldView/World collectors return VoxelShape streams; the 1.12.2 World collector appends translated Box candidates. WorldView collision expansion, borders, entity collisions, VoxelShapes offset math, and individual neighboring block shape methods remain open.

### Slice FENCE-GATE-COLLISION: closed/open spruce fence-gate shape

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: A `FenceGateBlock.getCollisionShape` 81-87 and B `getCollisionShape` 67-73; collision constants A 26-27 / B 26-27; corresponding `spruce_fence_gate` registrations A 1179-1181 / B 1569.
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/block/FenceGateBlock.java`, collision constants lines 26-27 and `getCollisionShape` lines 81-87, SHA-256 `38ae9e6aa1057a8e9b65ff78414d0d6a9a70cf2645f1ea42141ff2815ad6a477`; `Block.java` registration line 1179 and `addCollision` empty-sentinel handling lines 376-382, SHA-256 `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`.
- B evidence: `ready/1.13.2/ornithe-feather/net/minecraft/block/FenceGateBlock.java`, collision constants lines 26-27 and `getCollisionShape` lines 67-73, SHA-256 `e251cbd1b5e06c42e856a1d537e4ba043f6760b8f54ea1388039428aa7c05164`; `Block.java` registration line 1569 and `Block.block` coordinate scaling lines 136-138, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`; `WorldView.java` collision shape filtering lines 122-150, SHA-256 `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`.
- State producers/writers -> consumers/readers: registered spruce fence-gate block and its `OPEN`/`FACING` state -> A/B collision getter -> World collision collector -> `Entity.move` axis resolver. `IN_WALL` and adjacent-wall state are read only by outline/occlusion shape code and do not select the movement collision shape.
- Parent slices / dependencies / closure evidence: WORLD-COLLISIONS, ENTITY-MOVE-AXES, BLOCK-REGISTRATIONS, SHAPE-PROVIDERS. Relevant cited source digests match the ready manifests; the shape normalization helper is Block.block(value/16.0) at B Block.java 136-138.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for the shared `spruce_fence_gate` registration, both collision getters return an empty collision shape when `OPEN` is true. When closed, both select Z bounds (0,0,0.375)-(1,1.5,0.625) for a Z-axis facing and X bounds (0.375,0,0)-(0.625,1.5,1) otherwise. B's values (6,24,10) and (6,0,10,24) scaled by 16 are exactly those A bounds. A's `IN_WALL` neighbor-derived virtual state affects `getShape`, not `getCollisionShape`; B's stored `IN_WALL` likewise does not affect `getCollisionShape`. This closes only the collision getter and its registered spruce block instance.
- Finding IDs or checked absence/replacement path: none for this bounded shape; no source difference was found in its `OPEN`/`FACING` collision result.

### Slice FENCE-COLLISION-GEOMETRY: occupied volume for corresponding connection flags

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: A `FenceBlock.addCollisions` emits the central post and selected NORTH/EAST/SOUTH/WEST arms. B's `FenceBlock` inherits `PaneBlock.getCollisionShape`, whose constructor-built union is selected by the same four connection flags. Compare the occupied geometric volume for all 16 flag patterns; exclude neighbor-connection classification and collision-offset iteration behavior.
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/block/FenceBlock.java`, Box table lines 28-53, `addCollisions` lines 59-78, and `getShapeIndex` lines 88-111, SHA-256 `633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e`.
- B evidence: `ready/1.13.2/ornithe-feather/net/minecraft/block/FenceBlock.java`, constructor and state flags lines 24-29, SHA-256 `abefbedea10306abf602d4e379a4793b415ca7635231752cd9f18a80aced5fc2`; inherited `PaneBlock.java` constructor/`makeShapes` lines 32-73 and `getCollisionShape` lines 82-84, SHA-256 `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c`. The digests match the exact-version manifests.
- State producers/writers -> consumers/readers: the common N/E/S/W Boolean connection bits -> A dynamically adds the center plus enabled arms; B's `PaneBlock` direction-bit index selects a VoxelShape composed from those same primitives -> block collision provider. A derives connection bits from neighboring faces at query time; B places/updates the bits; that lifecycle is separate.
- Parent slices / dependencies / closure evidence: WORLD-COLLISIONS, ENTITY-MOVE-AXES, SHAPE-PROVIDERS, BLOCK-REGISTRATIONS. B's inherited collision getter reads `PaneBlock.shapes`; `FenceBlock`'s private `shapes` field is used for occlusion and is not the movement collision array.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): A's center box occupies X[6,10]×Z[6,10]×Y[0,24] in sixteenth coordinates. Each enabled A arm adds a strip from the corresponding cell edge to the block boundary: north X[6,10]×Z[0,6], east X[10,16]×Z[6,10], south X[6,10]×Z[10,16], west X[0,6]×Z[6,10], all Y[0,24]. B's `PaneBlock.makeShapes` uses the same central and directional intervals, with each arm primitive overlapping the post rather than beginning at its outer edge; Boolean OR makes the occupied union identical. Comparing the 16 direction masks yields the same occupied sixteenth-cells for every corresponding flag state. This closes only shape volume. A appends up to five `Box` objects while B supplies one union `VoxelShape`; candidate iteration, epsilon handling, neighbor flag production, and resulting axis clipping remain open under `F-COLLISION-REPRESENTATION` and `ENTITY-MOVE-AXES`.
- Finding IDs or checked absence/replacement path: none for occupied shape volume across the 16 paired masks; representation and response remain open.

### Slice TRAPDOOR-COLLISION-GEOMETRY: open-facing and closed-half shapes

- Inventory ID(s): INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: paired `TrapdoorBlock.getShape` selection for OPEN/FACING and closed HALF states, plus normalization of all selected bounds to sixteenths. B's added POWERED and WATERLOGGED state properties are checked for non-use by this shape selector.
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/block/TrapdoorBlock.java`, shape constants and `getShape` lines 26-64, SHA-256 `a7033c517b6b71ea1926bbb16ff67c00172fe753226dae689c215ff24e8b26cb`.
- B evidence: `ready/1.13.2/ornithe-feather/net/minecraft/block/TrapdoorBlock.java`, shape constants and `getShape` lines 29-59, SHA-256 `26dd6911517029daef779cef046e943953356644a76372f1d47b1323054629da`; `Block.java#block` scales coordinates by 1/16 at lines 136-138, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`. A registration is `Blocks.TRAPDOOR` at `Block.java:966` (registry key `trapdoor`); B registration is `Blocks.OAK_TRAPDOOR` at `Block.java:1156` (registry key `oak_trapdoor`). Both instantiate `TrapdoorBlock`; the corresponding oak-block/resource mapping and full registration inventory remain open.
- State producers/writers -> consumers/readers: corresponding FACING, OPEN, HALF values -> the paired six-state box selection -> inherited default collision getter -> world collision provider. In B, POWERED and WATERLOGGED are ignored by `getShape`; water tick, fluid, placement, and neighbor-redstone state transitions remain separate slices.
- Parent slices / dependencies / closure evidence: WORLD-COLLISIONS, ENTITY-MOVE-AXES, BLOCK-REGISTRATIONS. The result is limited to the shape mapping for a corresponding trapdoor state, not placement/update lifecycle or broader registry reconciliation.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): with OPEN=true, each horizontal facing selects the same 3/16-thick vertical side slab in A and B: east x[0,3], west x[13,16], south z[0,3], north z[13,16], full other axes. With OPEN=false, TOP selects y[13,16] and BOTTOM selects y[0,3] in both versions; closed shape does not depend on FACING. A expresses these bounds directly as doubles; B calls `Block.block` with sixteenth coordinates, which divides by 16. Every corresponding open/facing and closed/half state therefore returns the same occupied volume. The collision provider and player axis response are not broadened by this result.
- Finding IDs or checked absence/replacement path: none for the bounded shape mapping; no source difference was found.

### Slice PANE-COLLISION-GEOMETRY: occupied volume across four pane connection flags

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: A `PaneBlock.addCollisions` emits its center post and the selected cardinal arm Boxes. B's `IronBarsBlock`/`GlassPaneBlock` path inherits `PaneBlock.getCollisionShape`, selecting the Boolean OR union constructed by the PaneBlock constructor. Compare occupied geometry for all 16 direction masks, conditional on matching flags; exclude state production and collision iteration order.
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/block/PaneBlock.java`, SHAPES table lines 26-43, `addCollisions` lines 54-75 and direction index lines 77-105, SHA-256 `83862f6633b8241c3788e9c9a7e4fd0275c010ec790019b8003908996f4814b3`.
- B evidence: `ready/1.13.2/ornithe-feather/net/minecraft/block/IronBarsBlock.java`, constructor lines 24-29 and shape-state inheritance, SHA-256 `df6d956bb2745241e59294056f31513151b976dbd9ceed3f5478fd72b66c68a0`; `PaneBlock.java`, constructor/makeShapes/getCollisionShape lines 32-84, SHA-256 `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c`; `GlassPaneBlock.java` inherits IronBarsBlock, SHA-256 `7abf507b21b920c76e63e34293e6f40bbdefc95df396eb4847406a6d41df96a0`; `StainedGlassPaneBlock.java` inherits GlassPaneBlock, SHA-256 `494d69648753478aa813a5b20a62b4a8f75b33b38c1d0f70061b9cafafda257d`.
- State producers/writers -> consumers/readers: same four directional bits select the A dynamic center-plus-enabled-arms list or the B PaneBlock union mask -> collision provider. The correspondence of individual registered pane blocks and neighbor-flag lifecycle is still inventoried separately.
- Parent slices / dependencies / closure evidence: WORLD-COLLISIONS, SHAPE-PROVIDERS, BLOCK-REGISTRATIONS, NEIGHBOR-SHAPES. This is occupied shape volume only; F-COLLISION-REPRESENTATION and ENTITY-MOVE-AXES remain open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): A's center is X[7,9]×Z[7,9]×Y[0,16] in sixteenth coordinates; each enabled arm extends that same 2/16-thick strip from its center overlap to the corresponding cell edge. B's `IronBarsBlock` constructor passes f=g=1, so `PaneBlock.makeShapes` computes the same 7..9 center and arm widths after `Block.block` scales coordinates by 1/16. The B collision getter uses constructor-built `shapes` at full Y[0,16]; its OR union of the center and enabled arms occupies the same sixteenth-cells for every 4-bit mask. This closes only geometric volume for matching connection bits. A appends multiple Box candidates while B supplies a union VoxelShape, and their movement clipping/offset iteration is not declared equivalent.
- Finding IDs or checked absence/replacement path: none for occupied volume across the 16 masks; response and connection classification remain open.

### Slice FENCE-CONNECTION-EXCEPTION: Frosted Ice neighbor classification

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: paired fence solid-face connection predicate, shared attachment exceptions, Frosted Ice registration/provider, and one East-neighbor flag flowing into the fence collision shape and player movement query.
- A evidence: `FenceBlock.java#shouldConnectTo/resolveVirtualProperties/addCollisions`, SHA-256 `633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e`; `Block.java#isExceptionForAttachment/getFaceShape/getShape/getCollisionShape`, SHA-256 `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`; Frosted Ice registration and shared block methods; `Blocks.java` SHA-256 `8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e`.
- B evidence: `FenceBlock.java#shouldConnectTo/getPlacementState/updateShape`, SHA-256 `abefbedea10306abf602d4e379a4793b415ca7635231752cd9f18a80aced5fc2`; `Block.java#isExceptionForAttachment/getFaceShape/getShape/getCollisionShape`, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`; Frosted Ice registration and inherited provider; `Blocks.java` SHA-256 `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303`. Exact ranges and Frosted Ice class digests are in `findings/F-FENCE-FROSTED-ICE-CONNECTION.md`; all cited digests match the ready manifests.
- State producers/writers -> consumers/readers: east-neighbor Frosted Ice state -> default solid face classification -> A/B fence connection predicate -> A query-time virtual EAST state / B placement or horizontal update state -> fence collision provider -> World collision collection -> player `Entity.move`.
- Parent slices / dependencies / closure evidence: FENCE-GATE-COLLISION, FENCE-COLLISION-GEOMETRY, WORLD-COLLISIONS, ENTITY-MOVE-AXES, BLOCK-REGISTRATIONS.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): A's effective exception list does not contain `Blocks.FROSTED_ICE`, and default solid-face neighbors connect, so A sets EAST=true. B explicitly rejects `Blocks.FROSTED_ICE`, so B sets EAST=false. The identifier `frosted_ice` exists in both releases; both classes inherit a full-cube collision and default solid face. With fence (0,0,0), Frosted Ice (1,0,0), and an airborne player box X[1,1.6], Z[0.2,0.8], Y[1.0001,2.8001], a -0.1 X request clips to zero against A's east arm up to y=1.5; B's center post ends at x=0.625 and the Ice cube ends below player y, so B applies the full displacement. The documented source query is bounded; no runtime validation or first-changed-release claim is made.
- Finding IDs or checked absence/replacement path: `F-FENCE-FROSTED-ICE-CONNECTION`; see `findings/F-FENCE-FROSTED-ICE-CONNECTION.md`.

### Slice PANE-CONNECTION-EXCEPTION: common neighbor predicate and exception membership

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: paired `shouldConnectTo` boolean expression and exception membership checked for corresponding historical neighbor states, including the common leaf and facing-pumpkin families, explicit exception identifiers, and custom face-shape overrides for shulker boxes, cauldrons, and piston parts. Complete registration/resource inventory remains open. The stored-state versus query-time lifecycle contrast alone is excluded by user direction.
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/block/PaneBlock.java`, `resolveVirtualProperties` and `shouldConnectTo` lines 108-157, SHA-256 `83862f6633b8241c3788e9c9a7e4fd0275c010ec790019b8003908996f4814b3`; `PumpkinBlock.java` is a `HorizontalFacingBlock` with FACING, SHA-256 `732365c7ba81c3301cf164ab749bf52b5ce540a7b3a84ad50798db465ab77c9d`.
- B evidence: `ready/1.13.2/ornithe-feather/net/minecraft/block/IronBarsBlock.java`, placement/update/predicate/exception list lines 15-97, SHA-256 `df6d956bb2745241e59294056f31513151b976dbd9ceed3f5478fd72b66c68a0`; parent `PaneBlock.java` SHA-256 `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c`; `Block.java` neighbor update routing lines 165-202, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`; `CarvedPumpkinBlock.java` is `HorizontalFacingBlock` with FACING, whereas the separate B `PumpkinBlock.java` extends `GrowableBlock`, SHA-256 `3cdfac9817a2ad46014223c28814cd8f8b5ed07ba08051915b1ff7972324973a`.
- State producers/writers -> consumers/readers: A derives neighbor bits from the current world snapshot; B placement/update callbacks write bits consumed by `getShape`. That lifecycle-only contrast is outside this slice's disposition per user direction. For common historical states, both predicates use `!exception && SOLID || MIDDLE_POLE_THIN`; A's leaf family and B's registered leaf variants map to the same class family, and the facing pumpkin entries correspond at the class/state level. B's separate growable `pumpkin` is a modern-only crop block and does not add a historical neighbor case. Checked custom face-shape overrides classify common states identically; other listed exception types inherit paired default SOLID.
- Parent slices / dependencies / closure evidence: PANE-COLLISION-GEOMETRY, WORLD-COLLISIONS, ENTITY-MOVE-AXES, BLOCK-REGISTRATIONS, SOURCE-RESOURCE-ENTRIES. This closes the common historical neighbor predicate/class-family mapping only; complete registration/resource inventories remain open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for corresponding historical neighbor states and equal FaceShape inputs, both source predicates return the same connection bit. The common leaf and facing-pumpkin class families and the checked custom face-shape overrides yield matching exception classifications. B's additional crop `pumpkin` is not the A facing pumpkin block and is excluded as modern-only. No claim is made about stored flag freshness or lifecycle; that contrast alone is out of scope per user instruction.
- Finding IDs or checked absence/replacement path: none for the common historical neighbor predicate; see `PANE-COLLISION-AXIS-EPSILON` for a separate bounded collision-offset finding.

### Slice PANE-COLLISION-AXIS-EPSILON: orthogonal voxel interval inset at pane center

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: A pane center box candidate collection uses strict translated Box intersection; B's swept VoxelShape X offset uses orthogonal cell range coordinates inset by `1.0E-7`; paired player move applies X-axis collision resolution.
- A evidence: `PaneBlock.java` lines 53-85, SHA-256 `83862f6633b8241c3788e9c9a7e4fd0275c010ec790019b8003908996f4814b3`; `Block.java#addCollision`, SHA-256 `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`; `Box.java#intersects`, SHA-256 `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a`; `Entity.java#move`, SHA-256 `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`.
- B evidence: `PaneBlock.java`, SHA-256 `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c`; `IronBarsBlock.java`, SHA-256 `df6d956bb2745241e59294056f31513151b976dbd9ceed3f5478fd72b66c68a0`; `WorldView.java`, SHA-256 `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`; `VoxelShape.java`, SHA-256 `d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1`; `VoxelShapes.java`, SHA-256 `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95`; `Entity.java#move`, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- State producers/writers -> consumers/readers: four AIR neighbors yield false pane flags -> matching historical center voxel -> A strict Box collector or B swept shape/cell query -> player X clip.
- Parent slices / dependencies / closure evidence: PANE-COLLISION-GEOMETRY, PANE-CONNECTION-EXCEPTION, WORLD-COLLISIONS, ENTITY-MOVE-AXES. Detailed exact preconditions and arithmetic: `findings/F-PANE-OFFSET-EPSILON.md`.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): for a pane at `(0,64,0)`, airborne player box X `[-0.16251,0.43749]`, Y `[64.2,66.0]`, Z `[-0.16249995,0.43750005]`, and requested `(1,0,0)`, A's strict box overlap includes the center voxel and clips X to `0.00001`. B's orthogonal Z coordinate `box.maxZ - 1.0E-7 = 0.43749995` lies below the occupied center interval beginning at `0.4375`, so its axis-cell range omits the voxel and allows X `1.0`. Scope is this old Glass Pane state and query only; no runtime measurement or broad pane claim.
- Finding IDs or checked absence/replacement path: `F-PANE-OFFSET-EPSILON`; related but distinct from `F-VOXEL-OFFSET-EPSILON`.

### Slice WALL-SINGLE-ARM-COLLISION: cobblestone wall with one connected side

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: A `WallBlock` shape table and `addCollisions` lines 28-62, 90-96, `getShapeIndex` lines 105-124, and `resolveVirtualProperties` lines 190-197; B `WallBlock` constructor and `getCollisionShape` lines 22-39, `getPlacementState`/`updateShape` lines 69-122, inherited `PaneBlock.makeShapes` lines 32-73, and `Block.block` scale lines 136-138. Compare one connected arm; use NORTH for the concrete player path below.
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/block/WallBlock.java`, SHA-256 `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50`; `Blocks.java` registration `COBBLESTONE_WALL = getOrThrow("cobblestone_wall")` line 437, SHA-256 `8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e`; `Direction.java` horizontal IDs, SHA-256 `116a61f67e93f895d29c71128b392fbde5f1ca85f1b51d9f7fce55a0dd5960bf`.
- B evidence: `ready/1.13.2/ornithe-feather/net/minecraft/block/WallBlock.java`, SHA-256 `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`; `PaneBlock.java` shape construction/indexing, SHA-256 `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c`; `Block.java` `Block.block` coordinate scaling, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`; `BlockState.java` block-provider dispatch lines 209-210, SHA-256 `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70`; `Blocks.java` registration `COBBLESTONE_WALL = getOrThrow("cobblestone_wall")` line 906, SHA-256 `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303`; `Direction.java` horizontal IDs, SHA-256 `6d5c496ff65377fee9f00c826e48c7be14d3889e5ae5191e110a464007549082`. Each cited digest matches its exact-version source manifest.
- State producers/writers -> consumers/readers: neighbor face shapes -> A `resolveVirtualProperties` derives connection bits at collision query time; B placement/horizontal `updateShape` write the corresponding connection bits and `UP` -> A `World.getCollisions` passes `forceShape=false` to the block state's `addCollisions` (`World.java` lines 964-1015, 1001; SHA-256 `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`) -> B `World.getCollisions` delegates to `WorldAccess.getCollisions` at lines 1755-1756 (`World.java` SHA-256 `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a`); `WorldAccess` extends `WorldView` (line 18; SHA-256 `fc75177d629ef8d0d491821fe557e21606e4cb381164f1ca41b2763073f854be`) -> `WorldView.getBlockCollisions` requests the block state's collision shape (`WorldView.java` lines 112-152; SHA-256 `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`; `BlockState.java` dispatches lines 209-210) -> player `Entity.move` collision query. Player width and height are `0.6F`/`1.8F` in the paired `PlayerEntity` constructors; A/B hashes are `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` and `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`.
- Parent slices / dependencies / closure evidence: WORLD-COLLISIONS, ENTITY-MOVE-AXES, SHAPE-PROVIDERS, BLOCK-REGISTRATIONS, NEIGHBOR-SHAPES. Source call routes and source-file hashes are checked against the exact ready manifests.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): for a NORTH-only connection, both neighbor formulas make `UP=true`. A selects `COLLISION_SHAPES[4]`, the full box X[4,12], Z[0,12], Y[0,24] at sixteenths of a block. B selects the OR union of a center box X[4,12], Z[4,12] and north arm X[5,11], Z[0,11], both Y[0,24]; `VoxelShapes.or` preserves the empty corner regions X[4,5]×Z[0,4] and X[11,12]×Z[0,4]. A concrete reachable query uses a NORTH-connected wall and an open east-west-facing fence gate immediately north (its south face returns `MIDDLE_POLE`, while its open collision shape is empty), with air in the other horizontal neighbors. A player at y=0 with center x=-0.06,z=-0.31 has the shared 0.6-wide box X[-0.36,0.24], Z[-0.61,-0.01]. For movement dx=0.07,dz=0.25, A's swept Box intersects its wall box; after the X axis clears, A clips Z at 0.01. B's swept shape reaches only X<=0.3100001,Z<=0.2400001, so it overlaps neither the north arm (which starts at X=0.3125) nor center (which starts at Z=0.25); the open gate contributes no collision. B therefore has no wall candidate for this query and can apply the full requested Z displacement. The exact old/new shape-set difference and player collision query are source-proven; this remains bounded to the cited wall state and query. Other wall connection patterns and UP=false remain open.
- Finding IDs or checked absence/replacement path: `F-WALL-SINGLE-ARM-GAPS`; it links to `F-COLLISION-REPRESENTATION` for the broader collision-collector representation candidate.

### Slice WALL-OPPOSITE-ARMS-UP-FALSE: straight wall arms using inherited PaneBlock collision shape

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: A `WallBlock.COLLISION_SHAPES` entries 5 and 10 plus `getShapeIndex`; B `WallBlock.getCollisionShape` `UP=false` fallback and constructor-selected inherited `PaneBlock.shapes` entries 5 and 10. This compares collision geometry only for N+S and E+W opposite pairs with `UP=false`.
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/block/WallBlock.java`, collision table lines 46-63 and shape index lines 105-124, SHA-256 `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50`.
- B evidence: `ready/1.13.2/ornithe-feather/net/minecraft/block/WallBlock.java`, constructor lines 22-28 and `getCollisionShape` lines 37-38, SHA-256 `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`; `PaneBlock.java`, constructor and `makeShapes` lines 32-73 plus `getCollisionShape` lines 82-84, SHA-256 `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c`. The digests match the exact ready manifests.
- State producers/writers -> consumers/readers: A neighbor-derived N/E/S/W connection bits -> `getShapeIndex` -> collision Box table. B placement/horizontal neighbor updates write N/E/S/W and `UP`; with `UP=false`, `WallBlock.getCollisionShape` calls `PaneBlock.getCollisionShape`, which indexes the inherited `shapes` array from the PaneBlock constructor. The position state is read by the block collision provider and then the common world collision collector.
- Parent slices / dependencies / closure evidence: WALL-SINGLE-ARM-COLLISION, SHAPE-PROVIDERS, NEIGHBOR-SHAPES, WORLD-COLLISIONS. Paired wall and pane source digests are verified against the exact ready manifests.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for N+S (A index 5; B direction bits 4+1) the collision volume in both versions is X[5,11], Z[0,16], Y[0,24] in sixteenths. A's outline table entry has height 14/16, but its collision table raises that same footprint to 24/16 via `withMaxY(1.5)`. For E+W (A index 10; B direction bits 2+8), both collision volumes are X[0,16], Z[5,11], Y[0,24]. In B's `UP=false` inherited path, the two opposing arm boxes join across the full axis; the constructor's central box with f=0 has zero width and adds no volume. A and B both reach `UP=false` for an opposite straight pair when the block above is air: A's `resolveVirtualProperties` and B's placement/update formula agree for these inputs. This closes only the two opposite-arm collision footprints with `UP=false`; wall outline, other arm patterns, state-update lifecycle, and resource registration remain open.
- Finding IDs or checked absence/replacement path: none for these two bounded states; matching occupied sixteenth-cells are found in the paired shape formulas.

### Slice WALL-THREE-ARM-CORNER-GAPS: three connected wall sides with UP=true

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: A `WallBlock` bit-indexed collision Box for NORTH+EAST+SOUTH (index 13) and B `WallBlock` `UP=true` shape built from the inherited PaneBlock center and arm VoxelShapes; neighbor-derived connection and UP state; one player query through the paired collision collectors and Y/X/Z resolver.
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/block/WallBlock.java`, shape table lines 28-62, `getShapeIndex` lines 105-124, and `resolveVirtualProperties` lines 190-197, SHA-256 `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50`; `FenceGateBlock.java` face/collision members as listed in `FENCE-GATE-COLLISION`.
- B evidence: `ready/1.13.2/ornithe-feather/net/minecraft/block/WallBlock.java`, constructor/getter/index/state formulas, SHA-256 `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`; inherited `PaneBlock.java#makeShapes`, SHA-256 `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c`; `VoxelShapes.java#or`, SHA-256 `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95`; exact source ranges and paired player/query hashes are documented in `findings/F-WALL-THREE-ARM-CORNER-GAPS.md`.
- State producers/writers -> consumers/readers: three neighboring open fence gates provide `MIDDLE_POLE` faces accepted by the wall's connection predicate -> the A query-time virtual-state derivation / B placed or updated connection state selects N+E+S and UP=true -> wall collision provider -> world swept collision collector -> local player `Entity.move` axis resolver.
- Parent slices / dependencies / closure evidence: WALL-SINGLE-ARM-COLLISION, WALL-OPPOSITE-ARMS-UP-FALSE, SHAPE-PROVIDERS, NEIGHBOR-SHAPES, WORLD-COLLISIONS, ENTITY-MOVE-AXES. Finding artifact `findings/F-WALL-THREE-ARM-CORNER-GAPS.md` holds the source-derived concrete query and provenance.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): for A index 13, the collision Box fills X[4,16]×Z[0,16]×Y[0,24] in sixteenth coordinates. B composes center X[4,12]×Z[4,12], north arm X[5,11]×Z[0,11], south arm X[5,11]×Z[5,16], and east arm X[5,16]×Z[5,11], leaving X[4,5]×Z[0,4] empty. Three open fence-gate neighbors can provide the corresponding connection faces without adding collision; air above yields UP=true in both formulas. A 0.6-wide player at center (-0.3, -0.3), y=0, with request (0.3, 0, 0.24) is source-derived to have Z clipped to zero by A after X moves, while B's expanded swept query remains before the B north arm and center boundaries and has no wall collision candidate. This finding is bounded to that wall state and query; independent source review remains pending.
- Finding IDs or checked absence/replacement path: `F-WALL-THREE-ARM-CORNER-GAPS`; see `findings/F-WALL-THREE-ARM-CORNER-GAPS.md`.

### Slice STAIRS-SHAPE-STATE-FORMULA: placement and horizontal-neighbor shape decision

- Inventory ID(s): INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: A stair placement initializes facing/half and later `resolveVirtualProperties` calls `getStairShape`; B placement calls `getStairShape` and `updateShape` calls it for horizontal neighbor directions. The bounded formula covers forward outer-corner priority, rear inner-corner selection, same-half check, side-neighbor suppression, left/right decision, and straight fallback.
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/block/StairsBlock.java`, `getPlacementState` lines 278-286, `resolveVirtualProperties` lines 328-330, `getStairShape` lines 332-359, `isStairSameHalfSameFacing` lines 361-368; SHA-256 `c52a011a759b6f3ef6d45162d3b61f464ceceee5d2d8735de6f6e25a56e55611`.
- B evidence: `ready/1.13.2/ornithe-feather/net/minecraft/block/StairsBlock.java`, `getPlacementState` lines 224-232, `updateShape` lines 235-242, `getStairShape` lines 245-276, and `isStairSameHalfSameFacing` lines 278 onward; SHA-256 `b226eb255a3a1b0a69e137c15024a3b0a48f488e7205174dd476ffd64ca82761`. The digest matches the exact B source manifest.
- State producers/writers -> consumers/readers: primary stair facing/half and neighboring stair block/facing/half values -> identical ordered `getStairShape` predicate -> A resolves the shape immediately before collision-provider lookup; B stores it at placement or horizontal `updateShape`, then its collision provider reads that state to choose the static shape table.
- Parent slices / dependencies / closure evidence: STAIRS-PROVIDER, NEIGHBOR-SHAPES, WORLD-COLLISIONS, BLOCK-REGISTRATIONS. The static geometry/table mapping remains separately documented under STAIRS-PROVIDER.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the paired decision trees read the same forward neighbor first and rear neighbor second; require a stair of the same half; compare perpendicular facing axes; use the same side-neighbor suppression check; select left versus right from the same `counterClockwiseY()` comparison; otherwise fall back to STRAIGHT. For an unchanged world snapshot, every corresponding state arrangement therefore yields the same shape enum in A's query-time derivation and B's placement/horizontal-update formula. This is only the formula and its stated entry points. World callback delivery/order, chunk-load transitions, state persistence and WATERLOGGED tick behavior remain open and are not covered by this result.
- Finding IDs or checked absence/replacement path: none for the paired shape-selection formula; no source difference was found in this bounded method.

### Slice STAIRS-PROVIDER: static collision shapes and neighbor-state derivation

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: A `StairsBlock.addCollisions` 66-89, `getCollisionShapes` 76-89, `resolveVirtualProperties` / `getStairShape` 328-368; B `StairsBlock.getShape` 99-105, `getPlacementState` 224-232, `updateShape` 235-242, `getStairShape` / `isStairSameHalfSameFacing` 245-281.
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/block/StairsBlock.java`, SHA-256 `c52a011a759b6f3ef6d45162d3b61f464ceceee5d2d8735de6f6e25a56e55611`; exact base/half/quarter Box primitives at lines 32-49; `Block.java` registration calls construct `StairsBlock` instances for legacy materials, including oak, stone, brick, nether brick, sandstone, wood variants, quartz and purpur; `Block.java` SHA-256 `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`.
- B evidence: `StairsBlock.java` SHA-256 `b226eb255a3a1b0a69e137c15024a3b0a48f488e7205174dd476ffd64ca82761` (shape primitives/index table lines 37-53 and builder/getShape lines 57-81, 99-105); `SlabBlock.java` SHA-256 `2e7ff3fcfa5faa83aa4af7ea9b68f07af8d8fe7047e08ee3a9d7d37b78c7128f` (shared top/bottom base shapes lines 27-28); `Block.java` SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` (`Block.block` coordinate scaling lines 136-138 and stair registrations); `Direction.java` SHA-256 `6d5c496ff65377fee9f00c826e48c7be14d3889e5ae5191e110a464007549082` (horizontal IDs lines 14-44, accessor 89-91); `StairShape.java` SHA-256 `1f67c3ff79edc82d93be6ad553fd198cdd031277571fb7342a926c979f4236b3` (enum order lines 5-13). Each digest matches its B ready manifest.
- State producers/writers -> consumers/readers: corresponding stair facing/half and north/south neighbor stair states -> shared A/B shape-selection formula -> matching half/facing/shape table entry -> block collision provider. A resolves the virtual shape during collision collection; B computes it at placement and horizontal `updateShape`. The stored-state versus query-time lifecycle contrast alone is excluded by user direction.
- Parent slices / dependencies / closure evidence: WORLD-COLLISIONS, BLOCK-REGISTRATIONS, SHAPE-PROVIDERS, NEIGHBOR-SHAPES, SOURCE-RESOURCE-ENTRIES.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for corresponding historical stair materials and every corresponding half, facing, and `STRAIGHT`/`INNER_LEFT`/`INNER_RIGHT`/`OUTER_LEFT`/`OUTER_RIGHT` state, A's base Box plus its selected half-plane and corner Box(s) form the same normalized occupied cubes as B's `SHAPE_INDICES` table and `VoxelShapes.or` builder. B's scaled `Block.block` inputs and horizontal direction IDs map the 20 shape/facing entries to the same north/east/south/west quadrants; the paired neighbor-shape decision tree selects the same enum for unchanged neighbor states. The registered B stair materials that did not exist in A are modern-only and outside this historical comparison. No collision difference was found in the bounded common-provider table; the query-time versus stored-state lifecycle contrast alone is excluded by user direction.
- Finding IDs or checked absence/replacement path: none for the paired static collision table and shape selection; full block/resource registration inventories remain open under their owning inventory slices.


### Artifact-revision provenance and limitation

The provisional hold is cleared: this worker verified both immutable revision snapshots and all source/raw manifests, and independent ops audit passed on 2026-10-07. Original derived mapped jars are unavailable; revised snapshots are not proven identical or metadata-only, so no original-bytecode identity claim is made. Keep this limitation in source evidence and use only the immutable snapshot paths.

### Slice VELOCITY-PACKETS: authoritative entity velocity update

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: ClientPlayNetworkHandler.handleEntityVelocity() A 464-470 / B 507-513; handler resolves the packet entity and calls `lerpVelocity(packet velocity / 8000.0)` on each axis.
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/client/network/handler/ClientPlayNetworkHandler.java::handleEntityVelocity, lines 464-470, SHA-256 fce21d9902e555fd46545fb04bdb6c4a912eef6398790776ad1a122e09960c7c.
- B evidence: corresponding member, lines 507-513, SHA-256 4f65502fbee6476cef0838563f03a66f07a1a9ed8c63e580f5338ba5fe274f11.
- State producers/writers -> consumers/readers: server EntityVelocityS2CPacket -> client handler -> Entity.lerpVelocity -> Entity velocity fields -> later LivingEntity cutoff/travel and Entity.move.
- Parent slices / dependencies / closure evidence: VELOCITY-CUTOFF; other writers/corrections remain pending.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the bounded packet application order, entity lookup, divisor and null guard are identical in A and B source. This does not close server velocity generation, teleport corrections, push writers, pistons, mounts or launch-item inputs.
- Finding IDs or checked absence/replacement path: none for this bounded handler.

### Slice PLAYER-CORRECTION and PISTON-DELTA-CLAMP: bounded external movement inputs

- Inventory ID(s): INV-EXTERNAL, INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: `ClientPlayNetworkHandler.handlePlayerMove(PlayerMoveS2CPacket)` A lines 578-620 / B 621-663; `Entity.move(MoverType.PISTON,...)` per-tick accumulated-axis clamp A lines 462-501 / B 473-512.
- A evidence: `ClientPlayNetworkHandler.handlePlayerMove`, lines 578-620; `Entity.move` piston clamp, lines 467-500; `ClientPlayNetworkHandler.java` SHA-256 `fce21d9902e555fd46545fb04bdb6c4a912eef6398790776ad1a122e09960c7c`; `Entity.java` SHA-256 `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`.
- B evidence: `ClientPlayNetworkHandler.handlePlayerMove`, lines 621-663; `Entity.move` piston clamp, lines 478-511; `ClientPlayNetworkHandler.java` SHA-256 `4f65502fbee6476cef0838563f03a66f07a1a9ed8c63e580f5338ba5fe274f11`; `Entity.java` SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- State producers/writers -> consumers/readers: authoritative player move packet -> resolve each relative position/angle flag; absolute position axes clear the corresponding local velocity component; update player position/angles, acknowledge teleport and send corrected position. MovingBlockEntity piston moves -> Entity piston mover type -> reset accumulated delta on world-time change, clamp the current axis total to ±0.51, update the axis accumulator, and ignore negligible remainder.
- Parent slices / dependencies / closure evidence: VELOCITY-PACKETS; MovingBlockEntity scheduling and full push path, MOUNT-TRANSITIONS, WORLD-COLLISIONS.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both bounded source paths match in axis order, relative-flag handling, velocity clearing, position/angle update and acknowledgement; both piston branches use the same X-then-Y-then-Z selection, per-world-time reset, ±0.51 clamp and `1.0E-5F` early return. This closes only these consumers; complete packet producers, moving-block scheduling/collision effects and mount transitions remain open.
- Finding IDs or checked absence/replacement path: none for these bounded sub-slices.

### Original-jar resource data and modern-only feature dispositions

- A original client jar: `artifacts/1.12.2/client.jar`, SHA-256 8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f. `jar tf` found no `data/minecraft/tags/fluids/{water,lava}.json` entries in this jar.
- B original client jar: `artifacts/1.13.2/client.jar`, SHA-256 3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9. `data/minecraft/tags/fluids/water.json` is 94 bytes, SHA-256 698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18; entries are `minecraft:water` and `minecraft:flowing_water`. `data/minecraft/tags/fluids/lava.json` is 92 bytes, SHA-256 f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd; entries are `minecraft:lava` and `minecraft:flowing_lava`. Hashes are computed directly from original jar entries; no resource was extracted or changed.
- B source: `tag/FluidTags.java` SHA-256 b9d4c2d93f4594f27e235e2d5973cd0c81d269597cbdc400a11333cc617247aa; `fluid/state/FluidState.java` 05e2df9377a3bc1e91645ce4282bcaa4edebe93d7ef1907407961baaecff23a6; `FluidStateImpl.java` cffddf7de4779c0d6e7508b827db506ea3b0cfe1a010fe24c2a8e042c2398b71; `WaterFluid.java` 183894d0a4f7a85becd97eb2c0f6cedc1d5adc4648dc6d3d2b5624df173df28b; `LavaFluid.java` 1db521d50ec5b5fa744b9a5151bd32e5282638355ecedb51c31e4e09f7fb2924.
- BUBBLE-COLUMNS: B registers `Blocks.BUBBLE_COLUMN` (`Blocks.java` line 652, SHA-256 3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303); `BubbleColumnBlock.onEntityCollision` calls Entity surface/column handlers (lines 38-71; SHA-256 0320e1b70b215dfff209f8e5003b97cac3a247fd3d642597f2db8ab46d251b2e), which write movement velocity in Entity (lines 1873-1893). A `Blocks.java` SHA-256 8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e has no BUBBLE_COLUMN registration or class source. This is a B-only block/state; mark its upward/downward bubble push modern-only, not historical behavior for 1.12.2 maps.
- Other remaining data: movement effects/enchantment registrations are Java-backed and still need source-member closure; check server/datapack supplied data separately. The lack of client-jar tag entries in A alone is not proof of absent server-provided state.

### Slice ATTRIBUTES, SPEED-SLOWNESS and JUMP-BOOST

- Inventory ID(s): INV-MODIFIERS, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: EntityAttributes.MOVEMENT_SPEED registration (A/B EntityAttributes.java line 19); PlayerEntity constructor base movement speed A line 157 / B nearby constructor; LivingEntity.setSprinting A 1295-1305 / B 1349-1359; jump() A 1398-1411 / B 1451-1464; StatusEffect speed/slowness modifier registrations A around lines 213-230 / B 209-221.
- A evidence: PlayerEntity.java SHA-256 e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e; LivingEntity.java 190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6; EntityAttributes.java 495f2845c167f9429611c847296cdc543cd37a6e55649ab07bbbd21ef92741da; StatusEffect.java b7953929609d29d36fb4714013870f9d3b6362b71682f3c06b597536de3eb2db.
- B evidence: PlayerEntity.java SHA-256 4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633; LivingEntity.java bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c; EntityAttributes.java 19b33da74fcfbb08b98e80c15554929229b1c3b36d009215b1803190f2ff755d; StatusEffect.java 60cc53e7233d28413d3c93292fd216babe8ea9edeb70774141b68101c515c639.
- State producers/writers -> consumers/readers: player constructor sets movement-speed base; Speed/Slowness effect registrations add the same movement-speed modifiers (0.2F and -0.15F, operation 2); sprint setter removes/re-adds the same sprint modifier; jump() adds the same `(amplifier + 1) * 0.1F` Jump Boost term; all values feed movement input acceleration or velocityY.
- Parent slices / dependencies / closure evidence: ATTRIBUTES, SPEED-SLOWNESS, JUMP-BOOST, SPRINT-STATE.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): bounded Java values and operations observed identical for these direct consumers. Full effect registry/application source, player base assignment, attribute synchronization/external modifiers and any relevant resource defaults remain to be closed; no whole modifier inventory conclusion is claimed.
- Finding IDs or checked absence/replacement path: none for the bounded identical formulas.

### Slice DEPTH-STRIDER aggregation

- Inventory ID(s): INV-MODIFIERS, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: `EnchantmentHelper.getDepthStriderLevel` and its equipment-level lookup; Depth Strider registration, max level, valid armor category/slots; travel cap at 3, airborne halving, and water travel coefficient adjustment.
- A evidence: `Enchantment.java` registration/equipment slots lines 131-150 and lookup lines 45-54, SHA-256 `02d9f335263bf167421c126e3291d12f33fa9801b7ca709c4e8108a512e2e6c9`; `DepthStriderEnchantment.java` lines 1-28, SHA-256 `dd39c3a98d6d468dbb8b9f21288a788eef9934c061549acba120a8fed5d5b739`; `EnchantmentHelper.java` item/entity level lookup lines 31-48 and 153-166, SHA-256 `226243b031824fbea660520a891f461272482808318787f6c04679e9a241d9c9`; travel source `LivingEntity.java` lines 1557-1579, SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`.
- B evidence: `Enchantment.java` registration/equipment slots lines 133-142 and lookup lines 38-47, SHA-256 `fcffa719e9c249261e69552c63a4041632b34a9044d4aa8b31022750822d3373`; `DepthStriderEnchantment.java` lines 1-26, SHA-256 `d2daf7d22ada9f56bc3a486560cac446b0280a3fb294ceb27da8cdd197234063`; `EnchantmentHelper.java` item/entity level lookup lines 31-48 and 149-162, SHA-256 `0c94bd4d076c2c7b39b6480dfb8c29b7e37d98817c9b878de67a0dd5c4f3a750`; travel source `LivingEntity.java` lines 1618-1649, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- State producers/writers -> consumers/readers: equipped item stacks in armor slots -> registry-resolved enchantment level, selecting the maximum level across slots -> travel cap at 3 and airborne halving -> water horizontal multiplier and acceleration adjustment.
- Parent slices / dependencies / closure evidence: WATER-TRAVEL; full effect/application and attribute synchronization audit remains in ATTRIBUTES/SPEED-SLOWNESS/JUMP-BOOST and INV-MODIFIERS.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the registry key is `depth_strider` in both, both register the same rare `DepthStriderEnchantment`, max level 3, armor-feet category, and HEAD/CHEST/LEGS/FEET equipment slots. Both enumerate the same selected equipment and use the same level cap, airborne division and travel expressions. NBT representation differs (A short numeric ID/level, B namespaced string ID/integer level), which is a serialization change; it does not change the bounded movement result for valid corresponding equipped enchantments. This closes only the Depth Strider path, not the whole modifier inventory.
- Finding IDs or checked absence/replacement path: none for the bounded Depth Strider path; F-WATER-SPRINT-TRAVEL remains provisional for the independent sprint/Dolphin's Grace branch.

### Modern-only modifiers and launch equipment

- B `StatusEffects` registers SLOW_FALLING and DOLPHINS_GRACE (B StatusEffects.java lines 286-288; SHA-256 d982a7e051da6014286e07dcbfd36fd62dd0faa4ae5218a1c2d89bff02a78d9e); A's same source file SHA-256 65a621ba5a80957f742aa14f109224088388340d0b28b94023440ad0f8f74dd2 has no such registrations. The former selects B travel factor `d`; the latter overrides B water drag multiplier to `0.96F`. These effects are modern-only dispositions for this one-way 1.12.2 target.
- B TridentItem.java SHA-256 97e5b7c3a84ba25c0dd229b78f571c936d7370006585c531896d5c49c76437f3 is a B-only class; its Riptide path and LivingEntity spin timer can affect velocity/pose/collision. A has no trident item or spin-attack timer path. Still trace exact registration, guards and item tags before closing INV-EXCLUSIONS; do not assign those mechanics to A-era maps.
- B bubble-column source and registration are documented above. `data/minecraft/tags/fluids/*` entries are B-only resource-backed type groups; relevant B hashes are listed above. Original resource/source inspection found no corresponding A-era tag entries, but server-provided state remains an explicit external dependency.

### Slice BLOCK-FRICTION: registered slipperiness values

- Inventory ID(s): INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: all direct `slipperiness` assignments/defaults under `net/minecraft/block` in both source trees. A: Block default 0.6F (line 70), IceBlock 0.98F, PackedIceBlock 0.98F, SlimeBlock 0.8F. B: Block.Properties default 0.6F (line 1907), registry values ice/frosted_ice/packed_ice 0.98F and slime_block 0.8F; B-only blue_ice is 0.989F (line 1870).
- A evidence: `Block.java` lines 70-70 and registry assignment sites; `IceBlock.java` line 23; `PackedIceBlock.java` line 10; `SlimeBlock.java` line 16; Block.java SHA-256 e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1; direct class assignments confirmed by source search across the exact A block package.
- B evidence: `Block.java` lines 1068-1949, including registry values and default/property setter; SHA-256 735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4; property defaults and every explicit registration captured by source search across the exact B block package.
- State producers/writers -> consumers/readers: block registry/property values -> LivingEntity ground friction lookup of block below `getShape().minY - 1.0` -> `s * 0.91F` -> ground acceleration denominator and post-travel friction.
- Parent slices / dependencies / closure evidence: GROUND-ACCEL, SHAPE-PROVIDERS, BLOCK-REGISTRATIONS.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): default and every A-era assigned slipperiness remain 0.6F / 0.98F / 0.8F in B for corresponding blocks. Blue Ice's 0.989F is introduced only in B and is modern-only; do not map it onto an A-era block. Collision shape, neighbor interactions and other movement properties remain separate.
- Finding IDs or checked absence/replacement path: none for shared historical friction values; modern-only Blue Ice is an exclusion disposition.

### Slice PLAYER-PUSH-PRIMITIVE: direct entity-to-entity velocity push

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: PlayerEntity.push(Entity) A 1918-1922 / B 2016-2020 and Entity.push(Entity) A 1176-1207 / B 1186-1217.
- A evidence: PlayerEntity.push lines 1918-1922, PlayerEntity.java SHA-256 e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e; Entity.push lines 1176-1207, Entity.java SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a.
- B evidence: PlayerEntity.push lines 2016-2020, PlayerEntity.java SHA-256 4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633; Entity.push lines 1186-1217, Entity.java SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269.
- State producers/writers -> consumers/readers: push-away call sites -> PlayerEntity sleeping guard -> Entity push distance normalization and 0.05F impulse, gated by same vehicle/noClip/passenger checks -> Entity.addVelocity changes horizontal velocity.
- Parent slices / dependencies / closure evidence: POST-TRAVEL, ENTITY-PUSH-DISCOVERY, MOUNT-TRANSITIONS.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both full direct push primitive bodies use the same guards, normalization, operation order, coefficient and velocity writes. This does not close which entities are enumerated, push-away call timing, server velocity synchronization, mounts or piston movement.
- Finding IDs or checked absence/replacement path: none for the bounded primitive.

### Revised derived-artifact verification (feather-r1-2026-10-07)

- Source owner revision protocol: `workflows/source-campaign-2026-10-07/ARTIFACT-REVISION-2026-10-07.md` in the source-owner published worktree; revised immutable snapshots are under `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/<version>/ornithe-feather/`.
- Independent local verification: 1.12.2 snapshot SHA-256 fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87 matched both `artifact.sha256` and `revision.json`; 1.13.2 snapshot SHA-256 b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c matched both records. Revision log SHA-256 on both sides matched revision.json: 33b732892a03ffac60026663f7266c20b637330db6d480b4260267861bebd40c.
- Independently rehashed complete source trees against unchanged ready manifests: A 2,050 / 2,050 files, source-manifest SHA-256 b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da; B 2,711 / 2,711 files, source-manifest SHA-256 2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211. Independently rehashed all original raw artifact-manifest entries except the mutable derived mapped-jar path: A 37 / 37 and B 41 / 41 matched; the artifact manifests and ready markers still matched their originally published hashes.
- Verified B/A method descriptors against the revised snapshots using `javap -p -s`: `LivingEntity.jump()V`, A `jumpInWater()V`/`jumpInLava()V`, B `m_74407200(Tag)V`, and `moveRelative(FFF)V`; B Entity sampler `m_69693160(Tag)Z` and getter `m_02485546()D`. Any finding relying on these bytecode checks must cite revision ID `feather-r1-2026-10-07` and its exact snapshot hash.
- Material limitation: original mapped jars (A `65a08f...`, B `d118dc...`) are unavailable; revised snapshots differ and are not proven identical or metadata-only. This does not invalidate unchanged source-file hashes but prevents claims of original mapped-bytecode identity. Independent ops audit passed on 2026-10-07. Original derived artifact equivalence remains unproven.
- Do not rewrite ready markers, artifact manifests or source trees. No decompilation or cache mutation was performed by this discovery worker.
