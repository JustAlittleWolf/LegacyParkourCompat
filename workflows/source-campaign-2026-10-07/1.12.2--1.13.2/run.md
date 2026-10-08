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
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, support, timers and direct predicates: status=pending; slice_ids=POSE-DIMENSIONS, SWIM-STATE, FLUID-HEIGHT, VELOCITY-WRITERS, EYE-HEIGHT, SPRINT-JUMP-TIMERS; evidence=movement source members to inventory.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=SNEAK-EDGE, ENTITY-MOVE-AXES, STEP-CANDIDATES, SUPPORT-CALLBACKS, WORLD-COLLISIONS, SHAPE-PROVIDERS, FENCE-GATE-COLLISION, STAIRS-PROVIDER; evidence=Entity.move and most shape-source inventory open.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=BLOCK-REGISTRATIONS, COLLISION-SHAPES, BLOCK-CALLBACKS, FLUID-STATE, FLUID-FLOW, BUBBLE-COLUMNS, NEIGHBOR-SHAPES, FENCE-GATE-COLLISION, STAIRS-PROVIDER; evidence=full class/registration/resource inventory pending.
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

Source-confirmed bounded findings: `F-GROUND-ACCEL` (grounded coefficient and concrete default-friction scalar difference; snapshot review pending) and `F-VOXEL-OFFSET-EPSILON` (paired full-cube collision sequence with a sub-1e-7 residual; independent finding review pending). The full `TRAVEL-MODIFIERS`, `ENTITY-MOVE-AXES`, and pair remain active.

Provisional source candidates being traced after revised snapshot verification: 1.13 water/sprint/swimming gates; shallow-water jump and tagged-fluid jump; swimming pose and water-relative pitch acceleration; Slow Falling gravity and fall-distance state; changes to elytra acceleration/clamps; water movement multipliers (sprint, Depth Strider, Dolphin's Grace); broader collision shape/axis resolution, step candidates and provider changes beyond the bounded epsilon case. These are navigation checkpoints, not yet the exhaustive findings catalog. Modern-only equipment mechanics such as Riptide are separately classified against 1.12.2 scope.

## Resume checkpoint

- Handoff status: partial and resumable; blind source comparison remains active. Current task branch: `feat/source-discovery-movement-source-1-12-2-1-13-2`. Latest source-confirmed finding snapshot: `F-GROUND-ACCEL` at `2dae7b235bde47c7837fd4092ec67ccb014352f2`; its independent finding review is pending.
- Source identity: Feather Gen2 build 2 for both releases; immutable revised snapshots revision `feather-r1-2026-10-07`. Continue using `build/movement-campaign-2026-10-07/ready/1.12.2/ornithe-feather` and `.../ready/1.13.2/ornithe-feather` for verified source files, and retain the original-derived-JAR identity limitation above.
- Last completed work: eight bounded no-difference slices and twelve in-progress slices are listed under Source audit closure. Latest closures recorded in `PLAYER-CORRECTION`, `PISTON-DELTA-CLAMP`, `DEPTH-STRIDER`, and `FENCE-GATE-COLLISION`; swimming eye-height evidence was added to `POSE-DIMENSIONS` and `F-SWIM-POSE.md`. The collision collector and offset helpers now have exact paired coverage, including `F-VOXEL-OFFSET-EPSILON`. Static stair geometry tables and neighbor-shape formulas match for corresponding shape/facing/half states; stored-state lifecycle remains open. `F-GROUND-ACCEL` has a source-confirmed snapshot awaiting separate review. Independent review remains pending. The full `TRAVEL-MODIFIERS` and `ENTITY-MOVE-AXES` slices remain in progress.
- Next bounded slice: continue `SHAPE-PROVIDERS` and `ENTITY-MOVE-AXES`: verify corresponding A/B collision providers and registrations for the existing shape families, then close the remaining VoxelShape offset/tie cases and support callbacks. Keep the newly documented `F-VOXEL-OFFSET-EPSILON` constrained to its exact two-Stone geometry.
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

- Accepted finding snapshots (metadata only; does not close pair): none accepted yet; F-GROUND-ACCEL source-confirmed snapshot 2dae7b235bde47c7837fd4092ec67ccb014352f2 awaits independent review. F-VOXEL-OFFSET-EPSILON has no snapshot and awaits separate review.
- Coverage counts by status: 8 compared-no-difference (KB-SAMPLE, VELOCITY-PACKETS, BLOCK-FRICTION, PLAYER-PUSH-PRIMITIVE, PLAYER-CORRECTION, PISTON-DELTA-CLAMP, DEPTH-STRIDER, FENCE-GATE-COLLISION); 12 in-progress bounded slices (CLIENT-TICK, SPRINT-STATE, SWIM-STATE, JUMP-GATES, POSE-DIMENSIONS, TRAVEL-MODIFIERS, VELOCITY-CUTOFF, ENTITY-MOVE-AXES, WORLD-COLLISIONS, STAIRS-PROVIDER, SUPPORT-CALLBACKS, ATTRIBUTES/SPEED-SLOWNESS/JUMP-BOOST); remaining required member/resource slices not yet enumerated; all seven required inventories pending.
- Required inventory status and evidence: all pending; see inventory map above.
- Open dependencies: SRC-MANIFEST-ROWS, SOURCE-RESOURCE-ENTRIES, METHODS-DIAGNOSTICS, INDEPENDENT-REVIEWER, IMPLEMENTATION-RECONCILIATION. Resolved: revised artifact snapshot verification (feather-r1-2026-10-07; ops audit passed); original derived-artifact identity remains a limitation.
- Unresolved gaps and limits: full paired tick sequence, all state writers/consumers, all block/fluid collision shapes, modifiers/equipment, external inputs, resources, and reviewer audit remain open.
- Evidence/hash/correspondence audit: readiness/artifact/source manifest chain verified; cited KeyboardInput hashes verified; future source citations require per-file hashes and exact member ranges.
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

### Slice STAIRS-PROVIDER: static collision shapes and neighbor-state derivation

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: A `StairsBlock.addCollisions` 66-89, `getCollisionShapes` 76-89, `resolveVirtualProperties` / `getStairShape` 328-368; B `StairsBlock.getShape` 99-105, `getPlacementState` 224-232, `updateShape` 235-242, `getStairShape` / `isStairSameHalfSameFacing` 245-281.
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/block/StairsBlock.java`, SHA-256 `c52a011a759b6f3ef6d45162d3b61f464ceceee5d2d8735de6f6e25a56e55611`; exact base/half/quarter Box primitives at lines 32-49; source digest matches A ready manifest.
- B evidence: `StairsBlock.java` SHA-256 `b226eb255a3a1b0a69e137c15024a3b0a48f488e7205174dd476ffd64ca82761` (shape primitives/index table lines 37-53 and builder/getShape lines 57-81, 99-105); `SlabBlock.java` SHA-256 `2e7ff3fcfa5faa83aa4af7ea9b68f07af8d8fe7047e08ee3a9d7d37b78c7128f` (shared top/bottom base shapes lines 27-28); `Block.java` SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` (`Block.block` coordinate scaling lines 136-138); `Direction.java` SHA-256 `6d5c496ff65377fee9f00c826e48c7be14d3889e5ae5191e110a464007549082` (horizontal IDs lines 14-44, accessor 89-91); `StairShape.java` SHA-256 `1f67c3ff79edc82d93be6ad553fd198cdd031277571fb7342a926c979f4236b3` (enum order lines 5-13). Each digest matches its B ready manifest.
- State producers/writers -> consumers/readers: stair facing/half and north/south neighbor stair states -> shared A/B shape-selection formula; A resolves a virtual `SHAPE` during collision collection, while B computes `SHAPE` at placement and horizontal `updateShape`, then its collision getter indexes a prebuilt shape table from stored state.
- Parent slices / dependencies / closure evidence: WORLD-COLLISIONS, BLOCK-REGISTRATIONS, SHAPE-PROVIDERS, NEIGHBOR-SHAPES, SOURCE-RESOURCE-ENTRIES.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): for each corresponding half, facing, and `STRAIGHT`/`INNER_LEFT`/`INNER_RIGHT`/`OUTER_LEFT`/`OUTER_RIGHT` state, A's base Box plus its selected half-plane and corner Box(s) form the same normalized occupied cubes as B's `SHAPE_INDICES` table and `VoxelShapes.or` builder. B's scaled `Block.block` inputs and the B horizontal direction IDs map the 20 shape/facing entries to the same north/east/south/west quadrants; the neighbor-shape branch order and same-half/facing/axis checks match A. This closes the static geometry table and shape-selection formula only. A recalculates the virtual shape at query time, while B consumes stored state; full state-update/loaded-neighbor lifecycle and block registration/resource closure remain open, so no whole-provider equivalence is claimed.
- Finding IDs or checked absence/replacement path: F-COLLISION-REPRESENTATION remains candidate; no separate stair collision difference is confirmed by this bounded table comparison.


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
