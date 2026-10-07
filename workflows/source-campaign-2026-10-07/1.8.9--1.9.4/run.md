# Discovery: Java 1.8.9 to 1.9.4

- Run status: active
- Scope: source-only direct player movement; A = 1.8.9; B = 1.9.4. No runtime implementation.
- Repository revision and start date: base `002137b227676caea77f6832b9f4c8d0b6200bff`; 2026-10-07 Europe/Vienna.
- Selected naming namespace, CLI mode per side and alignment evidence: Ornithe Feather (`ornithe-feather`) on both sides; release-specific Feather Gen 2 build 2 source trees admitted. Derived mapped-artifact identity is pending canonical repair.
- Source preparation owner / command / log / readiness marker: initial pair run was the serialized `early-feather-4ccfc872a5a44dad985e62c22a2ffb82` batch from `C:\Users\Wolfi\.codex\worktrees\3e2d\LegacyParkourCompat`; exact command, cache/staging paths and runtime tool versions are recorded in each side's `ornithe-feather.provenance.json`. Per-version `ornithe-feather.success.log` is a success excerpt only; full original stdout was streamed in Codex but not persisted. Reproduction verification log for the six-release revision is recorded in each `revision.json`. Ready markers remain unchanged.
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1, Java 25.0.3+9-LTS, Vineflower 1.12.0, ASM 9.10.1, Mapping IO 0.9.1, Gson 2.14.0, Tiny Remapper 0.14.1; command used `--versions=1.8.9,1.9.4 --mappings=feather --decompiler-heap=4G` with isolated output root and explicit cache directory. The initial log limitation is recorded above.
- Discovery author(s): source-campaign worker for 1.8.9 -> 1.9.4.
- Independent reviewer: not assigned; must differ from discovery author.

## Artifact manifest

Paths are repository-relative. The shared source/artifact trees are read-only inputs. Initial admission verified both exact IDs, ready JSONs, manifests, and source/artifact rows. A subsequent source-owner revision reports the original derived mapped JARs unavailable; original markers/manifests remain untouched. The versioned `feather-r1-2026-10-07` snapshots and unchanged sources/raw inputs were freshly hash-checked by this worker below. Independent ops audit passed on 2026-10-07. This does not prove identity with the unavailable original derived JARs or explain the hash change; retain that limitation. No artifact marker was rewritten, no mismatch was waived, and no independent decompilation was performed.

### A — 1.8.9

- Requested/resolved/version metadata ID: `1.8.9` / `1.8.9` / `1.8.9`.
- Source root: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/`; 1,612 files, 8,248,472 bytes.
- Client jar `build/movement-campaign-2026-10-07/artifacts/1.8.9/client.jar`: SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`.
- Original mapped jar `build/movement-campaign-2026-10-07/artifacts/1.8.9/client-ornithe-feather.jar`: recorded SHA-256 `e36a366fd30d0adda0a803a4956782bab1f67cd644e396d4db6a1cd885548c09`; original jar unavailable and current mutable cache hash differs.
- Mapping `feather-gen2-1.8.9+build.2`; merged jar `build/movement-campaign-2026-10-07/artifacts/yarn/feather-gen2-1.8.9+build.2-mergedv2.jar` SHA-256 `303c4530c79a593b828bd778a97d3577e67f99d6a2c50760e5f9bec6fb32a9da`; mapping file `build/movement-campaign-2026-10-07/artifacts/yarn/feather-gen2-1.8.9+build.2.tiny` SHA-256 `de2023ea2cca9921402fbfcfe6e475f41da4932ea6dbc609e35c505b76a32c63`.
- Source manifest `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather.sources.sha256`: SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1,612/1,612 file hashes match.
- Artifact manifest `build/movement-campaign-2026-10-07/ready/1.8.9/artifacts.sha256`: SHA-256 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`; 36/37 current entries match, with only the unavailable original mapped-JAR entry differing. The separate revised snapshot is verified below.
- Diagnostics `build/movement-campaign-2026-10-07/ready/1.8.9/movement-diagnostics.txt`: SHA-256 `62dc9b445bec2f62b6dac9da501e875377636d08682891891212aea464999d28`; exact release succeeded, required entity/living/player/local-player files exist, no damaged movement body/error reported.

### B — 1.9.4

- Requested/resolved/version metadata ID: `1.9.4` / `1.9.4` / `1.9.4`.
- Source root: `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/`; 1,819 files, 9,320,183 bytes.
- Client jar `build/movement-campaign-2026-10-07/artifacts/1.9.4/client.jar`: SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`.
- Original mapped jar `build/movement-campaign-2026-10-07/artifacts/1.9.4/client-ornithe-feather.jar`: recorded SHA-256 `0df10c862f7fd4848d08597a76c0cf02cec5a9d9886975a57230a033cfb86b3a`; original jar unavailable and current mutable cache hash differs.
- Mapping `feather-gen2-1.9.4+build.2`; merged jar `build/movement-campaign-2026-10-07/artifacts/yarn/feather-gen2-1.9.4+build.2-mergedv2.jar` SHA-256 `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125`; mapping file `build/movement-campaign-2026-10-07/artifacts/yarn/feather-gen2-1.9.4+build.2.tiny` SHA-256 `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027`.
- Source manifest `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather.sources.sha256`: SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; 1,819/1,819 file hashes match.
- Artifact manifest `build/movement-campaign-2026-10-07/ready/1.9.4/artifacts.sha256`: SHA-256 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`; 36/37 current entries match, with only the unavailable-original mapped JAR entry differing. The separate revised snapshot is verified below.
- Diagnostics `build/movement-campaign-2026-10-07/ready/1.9.4/movement-diagnostics.txt`: SHA-256 `51bd42a633c04931814ab78a841cedd3bf87460e04f7877676599b51b59bbb1d`; exact release succeeded, required entity/living/player/local-player files exist, no damaged movement body/error reported.

Resource entry hashes will be added for data-backed slices. No test, game, TAS, server, Docker, build, or runtime validation was performed.

### Revised derived-artifact snapshot: worker hash verification

- Revision: `feather-r1-2026-10-07`; canonical recipe: `workflows/source-campaign-2026-10-07/ARTIFACT-REVISION-2026-10-07.md`, source-preparation docs commit `2f71b11`.
- Worker verification: every source-manifest row rehashed: A 1,612/1,612 and B 1,819/1,819 match; source manifest hashes match both revision records. Every original artifact-manifest row rehashed: A 36/37 and B 36/37 match. The one differing row per side is the unavailable original derived mapped JAR; all raw inputs are identical per revision records and all other artifact rows matched. Both original ready markers and manifests remain unchanged.
- A immutable snapshot `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.8.9/ornithe-feather/client-ornithe-feather.jar`: SHA-256 `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`; independently recomputed file hash matches `artifact.sha256` and `revision.json`. Revision JSON SHA-256 `95e2dc4aa3edba2d287f2bab092c61c0f66874f790af1b8c5d98196c980e105d`; full verification log SHA-256 `33b732892a03ffac60026663f7266c20b637330db6d480b4260267861bebd40c` matches `revision.json`.
- B immutable snapshot `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.9.4/ornithe-feather/client-ornithe-feather.jar`: SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`; independently recomputed file hash matches `artifact.sha256` and `revision.json`. Revision JSON SHA-256 `df0a26fd4c65292530cdad638e6789cc26fcd47875fe1c3eb2daef6de7e3f915`; full verification log SHA-256 `33b732892a03ffac60026663f7266c20b637330db6d480b4260267861bebd40c` matches `revision.json`.
- Limitation: the revision says source tree and raw inputs are hash-identical; the original derived JARs are unavailable and no equivalence/metadata-only claim is made. The coordinator relayed an independent ops audit PASS on 2026-10-07 after this worker's consumer-side hash verification.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending.
- Evidence inventory and finding IDs included at freeze: pending.
- Blind constraint: partial. No prior discovery report, old mod implementation, wiki, or audit was opened before the first four findings. An independent source-only early review was later read to route pair-specific omissions; subsequent slices are reviewer-informed. No browsing or old mod implementation was used.
- Source/mapping hashes covered by freeze: source and artifact manifests above are verified; cited source hashes will be recorded per slice/finding.

## Correspondence and call order

Common aligned Feather class paths include `LocalClientPlayerEntity`, `KeyboardInput`, `Input`, `PlayerEntity`, `LivingEntity`, `Entity`, `Block`, `BlockState`, and `BoatEntity`. Member correspondence is being completed from exact source ranges; no class-name-only inference is accepted.

Initial path: `LocalClientPlayerEntity.tick()V` -> `PlayerEntity.tick()V` -> `LivingEntity.tick()V` -> `Entity.tick()V`; local `mobTick()V` snapshots input, ticks the provider, applies movement gates, then invokes the player/living chain. `LivingEntity.mobTick()V` handles velocity cutoffs and jump state before `moveRelative(FF)V`; `LivingEntity.moveRelative(FF)V` selects travel branches and invokes `Entity.move(DDD)V`. `PlayerEntity.moveRelative(FF)V` wraps creative-flight behavior. B's `LocalClientPlayerEntity.rideTick()V` additionally transfers directional input to `BoatEntity`. All travel branches, callers, and state writers remain open.

## Required source inventories

- `INV-TICK` status=pending; slice_ids=TICK-01 through TICK-07; evidence=full local tick graph and player path inventory in progress
- `INV-STATE` status=pending; slice_ids=STATE-01 through STATE-03; evidence=all player state writers/readers still being inventoried
- `INV-COLLISION` status=pending; slice_ids=COLL-01 and COLL-02; evidence=axis, step, support, and shape-provider closure in progress
- `INV-WORLD-MOVEMENT` status=pending; slice_ids=WORLD-01, WORLD-02, WORLD-03, COLL-02; evidence=world states, neighboring blocks, fluids, and vehicle path in progress
- `INV-MODIFIERS` status=pending; slice_ids=MOD-01, MOD-02; evidence=equipment/effect/attribute producers and consumers in progress; modern-only Elytra/Levitation are scoped out
- `INV-EXTERNAL` status=pending; slice_ids=TICK-03 through TICK-07, WORLD-02, EXT-01, EXT-02; evidence=external velocity/position/vehicle writers in progress
- `INV-EXCLUSIONS` status=complete; slice_ids=scope boundary; evidence=health/food production and attack/damage resolution plus non-player/vehicle physics excluded; direct player velocity/impulse/knockback response remains in movement scope

## Coverage ledger

### Slice TICK-01: sprint timeout

- Inventory ID(s): `INV-TICK`, `INV-STATE`.
- Exact behavior boundary and enclosing guards/order checked: `LocalClientPlayerEntity.setSprinting(Z)V` timer write and `mobTick()V` timer update/stop.
- A evidence: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, lines 349-352, 488-493; SHA-256 `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`.
- B evidence: `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, lines 406-409, 605-606; SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.
- State producers/writers -> consumers/readers: sprint transition -> `sprintTimer`; A decrements from 600 and clears sprint at zero. B increments but source-wide search found no read/stop consumer.
- Parent slices / dependencies / closure evidence: local sprint gates and network sprint action pending; all `sprintTimer` refs searched on both sides.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): A enforces a 600-tick auto-stop; B does not. Other normal sprint gates are separately inventoried.
- Finding IDs or checked absence/replacement path: `findings/TICK-01-sprint-timeout.md`.

### Slice TICK-02: sneaking during creative flight

- Inventory ID(s): `INV-TICK`, `INV-STATE`.
- Exact behavior boundary and enclosing guards/order checked: keyboard sneak scale -> local camera/flying/sneaking branch -> `LivingEntity.mobTick()` argument handoff to the player flight `moveRelative`; no item use or riding.
- A evidence: `KeyboardInput.tick()V` lines 34-36, SHA-256 `a5e5b2033322f8867cd845e4095cea7a82888f6382cbfafefc559cf9ba3a76dd`; `LocalClientPlayerEntity.mobTick()V` lines 596-603, SHA-256 `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`; `LivingEntity.mobTick()V` line 1450, SHA-256 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`; `PlayerEntity.moveRelative(FF)V` lines 1279-1294, SHA-256 `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`.
- B evidence: `KeyboardInput.tick()V` lines 46-48, SHA-256 `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`; `LocalClientPlayerEntity.mobTick()V` lines 715-725, SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`; `LivingEntity.mobTick()V` line 1711, SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`; `PlayerEntity.moveRelative(FF)V` lines 1372-1390, SHA-256 `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`.
- State producers/writers -> consumers/readers: keyboard multiplies both axes by `0.3`; B divides the resulting floats by double `0.3` and casts before travel; the living tick passes the resulting axes to the player flight branch.
- Parent slices / dependencies / closure evidence: exact keyboard producer, local branch guards, living call site, and player flight consumer are recorded in TICK-02. Item-use attenuation is excluded by the finding precondition; no trajectory/displacement claim depends on open collision/cutoff slices.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B changes the horizontal arguments to flight travel for a sneaking local camera player who is flying, not riding, and not using an item; A retains the `0.3` keyboard scale.
- Finding IDs or checked absence/replacement path: `findings/TICK-02-flight-sneak-input-rescaling.md`.

### Slice STATE-01: living velocity zero threshold

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: per-axis velocity cutoff before AI, jump and travel.
- A evidence: `LivingEntity.java` `mobTick()V`, lines 1406-1416; SHA-256 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`.
- B evidence: `LivingEntity.java` `mobTick()V`, lines 1666-1676; SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`.
- State producers/writers -> consumers/readers: previous tick or external `velocityX/Y/Z` writers -> cutoff -> movement.
- Parent slices / dependencies / closure evidence: external velocity writers and travel consumers remain open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): strict threshold changes from `0.005` to `0.003` per component.
- Finding IDs or checked absence/replacement path: `findings/STATE-01-velocity-zero-threshold.md`.

### Slice WORLD-01: trapdoor above ladder climb gate

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-COLLISION`, `INV-WORLD-MOVEMENT`.
- Exact behavior boundary and enclosing guards/order checked: floored player position, spectator guard, current block, trapdoor `OPEN/FACING`, block below and ladder `FACING`.
- A evidence: `LivingEntity.java` `isClimbing()Z`, lines 794-800; SHA-256 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`.
- B evidence: `LivingEntity.java` `isClimbing()Z` and `canClimbTrapdoor(BlockPos, BlockState)Z`, lines 905-926; SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`.
- State producers/writers -> consumers/readers: block state/property + neighbor ladder -> `isClimbing()` -> travel horizontal clamp and descent behavior.
- Parent slices / dependencies / closure evidence: trapdoor/ladder registrations and movement consumer.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B adds climbing when an open trapdoor is immediately above a ladder with matching facing; A accepts only ladder/vine (with spectator exclusion).
- Finding IDs or checked absence/replacement path: `findings/WORLD-01-trapdoor-ladder-climbing.md`.

### Slice TICK-03: fall-flight entry

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-MODIFIERS`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: jump edge, airborne/falling guard, chest equipment predicate, start-flight request, server flag writer and travel consumer.
- A evidence: checked absence of `ELYTRA` and `START_FALL_FLYING` in the complete 1.8.9 source tree; this feature cannot be present in maps from A's era.
- B evidence: `LocalClientPlayerEntity.mobTick()V`, lines 707-712, introduces the request only with jump input, falling airborne state, no active flight/creative flight, and usable chest Elytra; source SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.
- State producers/writers -> consumers/readers: modern Elytra + jump edge -> request packet -> fall-flight bit -> gliding branch and pose dimensions.
- Parent slices / dependencies / closure evidence: bounded exclusion only; no historical player movement claim. Artifact integrity must be repaired before freeze.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): Elytra/fall-flight is a B-only feature and cannot occur in historical A-era maps under the project scope rule; do not add its behavior to the compatibility layer.
- Finding IDs or checked absence/replacement path: excluded modern-only feature; checked A source-tree absence above.

### Slice TICK-04: boat directional input transport

- Inventory ID(s): `INV-TICK`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: input booleans through `LocalClientPlayerEntity.rideTick()` to boat input fields and paddle packet.
- A evidence: `Input.java` has no directional boolean fields; local ride transfer and exact absence evidence pending.
- B evidence: `Input.java`, `KeyboardInput.java`, `LocalClientPlayerEntity.rideTick()V` lines 763-769, `BoatEntity.setInput(ZZZZ)V`.
- State producers/writers -> consumers/readers: directional key flags -> boat `inputLeft/inputRight/inputUp/inputDown` -> boat update-paddles movement.
- Parent slices / dependencies / closure evidence: boat input consumers and rider update.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B adds direction-specific boat input; movement effect pending.
- Finding IDs or checked absence/replacement path: pending.

### Slice WORLD-02: boat movement and rider path

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: boat tick authority, water state, buoyancy/drag, propulsion, yaw, movement, collision and rider positioning.
- A evidence: `BoatEntity.java` `tick()V` lines 176-365; hash pending.
- B evidence: `BoatEntity.java` `tick()V` lines 207-281, `updateVelocity()V` 484-525, `updatePaddles()V` 527-556; hash pending.
- State producers/writers -> consumers/readers: rider inputs/authority -> boat velocity/yaw -> boat `Entity.move` -> passenger position.
- Parent slices / dependencies / closure evidence: LocalClient ride input and boat water/status, dimensions, collision, passenger methods.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B replaces A's passenger forward-speed thrust and liquid-height fraction with directional paddle inputs and status-dependent movement; bounded findings pending.
- Finding IDs or checked absence/replacement path: pending.

### Slice COLL-01: axis collision and step resolution

- Inventory ID(s): `INV-COLLISION`, `INV-STATE`.
- Exact behavior boundary and enclosing guards/order checked: collision list, Y/X/Z clipping, two step candidates/tie-break, support block, velocity cancellation and callbacks.
- A evidence: `Entity.java` `move(DDD)V` lines 371-638; hash pending.
- B evidence: `Entity.java` `move(DDD)V` lines 441-722; hash pending.
- State producers/writers -> consumers/readers: requested displacement + shape providers -> clipped displacement -> position/ground/collision/support flags -> callbacks and velocity writes.
- Parent slices / dependencies / closure evidence: all reachable shapes, support and callback providers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): axis ordering and step candidate selection appear structurally aligned; no no-difference claim until dependency closure.
- Finding IDs or checked absence/replacement path: pending.

<!-- Add one bounded slice per behavior/dependency. Pending and in-progress slices forbid completion. -->

### Slice TICK-05: active-item use tick before local movement input

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.tick()V` -> active-item-use update -> local input sample/use attenuation -> superclass movement.
- A evidence: `LivingEntity.tick()V` lines 1259-1296; `LocalClientPlayerEntity.mobTick()V` lines 535-544; exact hashes pending.
- B evidence: `LivingEntity.tick()V` lines 1489-1551 calls `tickUsingItem()` before equipment update and `mobTick()`; local consumer lines 645-660; exact hashes pending.
- State producers/writers -> consumers/readers: active stack/duration and use predicate -> input scaling and item-use transition -> local travel input.
- Parent slices / dependencies / closure evidence: active-item writers, cancel/finish consumers, and player-reachable superclass dispatch on both sides.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): B inserts an item-use tick before movement input sampling; player-motion consequence and all writers remain open.
- Finding IDs or checked absence/replacement path: pending.

### Slice TICK-06: local pre-travel player block push-away

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: four local-player `pushAwayFrom` calls before sprint/jump/flight/riding gates and `super.mobTick()`; each probes block solidity at body offsets and may overwrite one horizontal velocity component.
- A evidence: `LocalClientPlayerEntity.mobTick()V` lines 535-560, `pushAwayFrom(DDD)Z` lines 292-341 and `canSurvive(BlockPos)Z` lines 344-346; SHA-256 `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`.
- B evidence: `LocalClientPlayerEntity.mobTick()V` lines 645-681, `pushAwayFrom(DDD)Z` lines 349-398 and `canSurvive(BlockPos)Z` lines 401-403; SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.
- State producers/writers -> consumers/readers: block state/material/shape -> `canSurvive` -> directional ±0.1 horizontal velocity overwrite -> pre-travel cutoff and movement.
- Parent slices / dependencies / closure evidence: the state-solid predicate reaches piston base as proved in WORLD-03; remaining non-piston state providers stay in `WORLD-03` inventory follow-up.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B's state-aware solidity changes the local player ejection gate for extended piston bases; other state/shape providers remain open.
- Finding IDs or checked absence/replacement path: `findings/WORLD-03-piston-player-block-ejection.md`.

### Slice TICK-07: post-travel entity push

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: end of `LivingEntity.mobTick()` after `moveRelative()`; candidate query/filter and reciprocal `Entity.push()` writes.
- A evidence: `LivingEntity.mobTick()V` lines 1450-1455 and `pushAwayCollidingEntities()V` lines 1463-1475; `Entity.push()V` lines 947-973; hashes pending.
- B evidence: `LivingEntity.mobTick()V` lines 1711-1715 and `pushAwayCollidingEntities()V` lines 1742-1754; `EntityFilter.canBePushedBy()`; `Entity.push()V` lines 1061-1088; hashes pending.
- State producers/writers -> consumers/readers: another client-side living entity's post-move query -> local player candidate -> reciprocal `Entity.push` velocity write.
- Parent slices / dependencies / closure evidence: exact-box versus expanded query; local-player filter eligibility and push writer guards are recorded in TICK-07.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B runs client-side queries; a client living entity may select the local player and write its horizontal velocity under the documented filter/collision guards. A is server-only.
- Finding IDs or checked absence/replacement path: `findings/TICK-07-client-player-push.md`.

### Slice EXT-01: local-player authority packets and inbound corrections

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: local tick movement/riding packet emission after superclass tick and authoritative correction consumers.
- A evidence: `LocalClientPlayerEntity.tick()V` lines 105-115; hash pending.
- B evidence: `LocalClientPlayerEntity.tick()V` lines 143-157 and 182-205, including vehicle movement and on-ground-only packet branch; hash pending.
- State producers/writers -> consumers/readers: local position/velocity/ground/riding state -> outbound packet selection -> inbound authoritative position/velocity writers.
- Parent slices / dependencies / closure evidence: trace paired packet handlers/corrections; do not claim simulated server outcomes.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): outbound protocol behavior changes; downstream movement implications await correction closure.
- Finding IDs or checked absence/replacement path: pending.

### Slice WORLD-03: piston-base state solidity in local player ejection

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-COLLISION`, `INV-WORLD-MOVEMENT`.
- Exact behavior boundary and enclosing guards/order checked: `LocalClientPlayerEntity.pushAwayFrom(DDD)Z` -> `canSurvive(BlockPos)Z` -> A block-level or B state-level solidity -> existing piston-base block's cube classification -> direct player velocity write.
- A evidence: `Block#isSolid()Z` lines 237-239 and `PistonBaseBlock#isCube()Z` lines 223-225; `Block.java` SHA-256 `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528`, `PistonBaseBlock.java` SHA-256 `3c96698a674714446f9fd0d6cc1c4d5eb72937d9a2f396516ddb2461c3ea4929`.
- B evidence: `Block#isSolid(BlockState)Z` lines 214-216 and `StateDefinition#isSolid()Z` lines 293-295; `Block.java` SHA-256 `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6`, `StateDefinition.java` SHA-256 `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493`.
- State producers/writers -> consumers/readers: registered historical piston-base state including `EXTENDED`/facing -> `isSolid` branch gate -> local player ±0.1 horizontal overwrite before travel.
- Parent slices / dependencies / closure evidence: both sides register piston bases; B `PistonBaseBlock` uses `Material.PISTON`, extended partial shape, and inherited state-cube default; A class overrides cube false. Other stateful block providers are open in `COLL-02`/world inventory.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): when a local player's non-`noClip` body probe encounters an extended piston base and an escape neighbor is available, B's state-solid gate can write directional velocity where A's block-level gate skips the push-away branch.
- Finding IDs or checked absence/replacement path: `findings/WORLD-03-piston-player-block-ejection.md`.

### Slice STATE-02: creative-flight movement reset

- Inventory ID(s): `INV-TICK`, `INV-STATE`.
- Exact behavior boundary and enclosing guards/order checked: `PlayerEntity.moveRelative(FF)V` creative-flight wrapper around living movement.
- A evidence: `PlayerEntity.moveRelative(FF)V` lines 1279-1294; SHA-256 `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`.
- B evidence: `PlayerEntity.moveRelative(FF)V` lines 1371-1390; SHA-256 `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`.
- State producers/writers -> consumers/readers: creative flying and not riding -> living move -> B resets fall distance (damage state, excluded) and clears flag 7 (B-only fall-flight state, excluded).
- Parent slices / dependencies / closure evidence: scoped against excluded fall-damage simulation and modern-only fall flight.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): the changed values feed excluded damage state or a modern-only flight mode; neither is a historical player-movement mechanic for A-era maps.
- Finding IDs or checked absence/replacement path: `scope-notes/STATE-02-creative-flight-reset.md` records the evidence and scope reason; no implementation finding.

### Slice MOD-01: Levitation movement branch

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-MODIFIERS`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: `LivingEntity.moveRelative(FF)V` after `Entity.move()`; effect registry/application/removal and amplifier writer.
- A evidence: checked absence of `LEVITATION` from the complete 1.8.9 source tree; that effect could not be present in A-era maps.
- B evidence: `StatusEffects.LEVITATION` registration at `entity/living/effect/StatusEffects.java` lines 32 and 75, and `LivingEntity.moveRelative(FF)V` lines 1396-1399; source hashes are covered by the initial source manifest, artifact hold remains open.
- State producers/writers -> consumers/readers: modern-only status effect -> post-move vertical-velocity blend; otherwise gravity/client unloaded-chunk branch.
- Parent slices / dependencies / closure evidence: excluded by the modern-only feature boundary; no historical producer-to-consumer implementation claim.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): Levitation is a B-only effect and cannot occur in A-era map features under project scope; do not emulate its movement branch.
- Finding IDs or checked absence/replacement path: excluded modern-only effect; source-tree absence checked on A.

### Slice MOD-02: movement attributes, effects, and equipment modifiers

- Inventory ID(s): `INV-MODIFIERS`, `INV-STATE`, `INV-TICK`, `INV-WORLD-MOVEMENT`.
- Exact behavior boundary and enclosing guards/order checked: player movement-speed attribute, sprint modifier writer, Speed/Slowness effect modifiers, jump-strength effect, and water movement enchantment/equipment consumers.
- A evidence: `LivingEntity` registers `MOVEMENT_SPEED`; `setSprinting(Z)V`, `moveRelative(FF)V`, jump-effect and water-friction ranges; exact bounded inventory/hashes pending.
- B evidence: corresponding `LivingEntity` attribute registration/sprint modifier, travel/jump branches and enchantment/effect consumers; exact bounded inventory/hashes pending.
- State producers/writers -> consumers/readers: player base/equipment/effect modifiers -> movement/jump attribute reads and water friction -> pre-travel velocity and travel displacement.
- Parent slices / dependencies / closure evidence: compare attribute base values, modifier UUID/value/operation, duration/amplifier update and removal, player reachability, enchantment equipment slots, and fluid movement order. Modern-only Elytra/Frost Walker remain excluded; Levitation is scoped separately in `MOD-01`.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): historical movement modifiers remain under comparison; do not infer parity from shared names or a common attribute API.
- Finding IDs or checked absence/replacement path: pending.

### Slice STATE-03: player pose and dimensions

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-COLLISION`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: source-wide player size writers, tick-end pose branch, B candidate-box collision query and its block-provider dispatch, `setSize` box rebuild, and separate eye-height behavior.
- A evidence: no `updatePlayerPose()` call or sneak-conditioned `setSize` path in `PlayerEntity#tick()V`; only PlayerEntity size writes are reset line 417, death line 515, sleep line 1108, and wake line 1163. Sneak eye-height adjustment is lines 1640-1650. SHA-256 `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`.
- B evidence: end-of-`tick()V` pose call line 245; `updatePlayerPose()V` lines 285-308 selects `0.6F`/`1.65F` for ordinary sneaking and resizes only when `World#getCollisions(Box)` reports no collision. `getEyeHeight()F` lines 1740-1750; SHA-256 `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`.
- State producers/writers -> consumers/readers: sneaking/pose priority -> candidate dimensions -> `World#getCollisions(Box)` over block-state providers (including PaneBlock) -> `Entity#setSize` at fixed minima -> later collision and eye-height reads.
- Parent slices / dependencies / closure evidence: B's fit guard is explicit and conditional; the PaneBlock provider is tied to the exact `SNAP-COLL-02-PANE-02` source snapshot, and no fit result is claimed for an unspecified world. A's complete PlayerEntity `setSize` writer inventory is recorded above. `Entity#setSize` preserves minima and width is unchanged, so the width-growth branch does not run. Support-edge probing remains separate in `COLL-01`.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B adds tick-end dynamic pose sizing to `1.65F` when the sneaking candidate box is collision-free; A has no sneak-conditioned player dimension write and retains `1.8F`. The collision-fit provider dependency and guard are included in the frozen finding; Elytra/sleep cases are excluded.
- Finding IDs or checked absence/replacement path: `findings/STATE-03-sneak-collision-height.md`.

### Slice COLL-02: pane and iron-bar collision geometry

- Inventory ID(s): `INV-COLLISION`, `INV-WORLD-MOVEMENT`.
- Exact behavior boundary and enclosing guards/order checked: era-existing PaneBlock-derived registrations -> four cardinal neighbor decisions -> all reachable connection masks -> collision-box assembly -> World query -> inherited player `Entity.move()` clipping.
- A evidence: `PaneBlock.addCollisions()` lines 62-92; `shouldConnectTo()` lines 134-141; `Block.java` registrations for iron bars, glass panes, and stained glass panes at lines 1040-1041 and 1180. `PaneBlock.java` SHA-256 `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61`; `Block.java` SHA-256 `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528`.
- B evidence: `PaneBlock.addCollisions()` lines 53-70, shape map lines 25-40 and 73-101, `resolveVirtualProperties()` lines 104-110, `shouldConnectTo()` lines 133-140; `Block.java` registrations at lines 950-952 and 1099. `PaneBlock.java` SHA-256 `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8`; `Block.java` SHA-256 `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6`.
- State producers/writers -> consumers/readers: four independently sampled neighbors -> A opacity / B default-state cube predicate -> `N/E/S/W` connection mask -> emitted boxes; stained pane subclass inherits this path on both sides.
- Parent slices / dependencies / closure evidence: mask-conditioned geometry is closed in `findings/COLL-02-pane-collision-shapes.md`; World collision collection and `Entity.move()` consumer are paired and cited there. Iron bars, clear panes, and stained panes all use the shared class. Other collision providers and the complete movement inventory remain open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): collision boxes differ for 13 of 16 reachable masks; opposite-pair and fully-connected masks produce the same union. The connection predicate itself also changed from opacity to cube classification, recorded as a separate source property in the finding; no rendering consequence is claimed.
- Finding IDs or checked absence/replacement path: `findings/COLL-02-pane-collision-shapes.md`.

### Slice SCOPE-01: excluded hunger/regeneration producers

- Inventory ID(s): `INV-EXCLUSIONS`, `INV-STATE`.
- Exact behavior boundary and enclosing guards/order checked: sprint-gate read of current food state versus excluded natural-regeneration/hunger/exhaustion producers.
- A evidence: `LocalClientPlayerEntity.mobTick()V`, lines 550-550, reads `getHungerManager().getFoodLevel()` solely in the sprint eligibility predicate; SHA-256 `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`.
- B evidence: `LocalClientPlayerEntity.mobTick()V`, lines 661-661, reads `getHungerManager().getFoodLevel()` solely in the sprint eligibility predicate; SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.
- State producers/writers -> consumers/readers: vanilla food state may be read by sprint predicate; natural regeneration/hunger/exhaustion simulation is outside this campaign.
- Parent slices / dependencies / closure evidence: no excluded producer appears in finding index; direct state reads only define a movement precondition.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): health/food state production and attack/damage resolution are excluded; direct player velocity/impulse/knockback application remains in scope. A sprint predicate may read vanilla food state without emulating its producer. Non-player and vehicle physics remain excluded.
- Finding IDs or checked absence/replacement path: excluded producer/physics boundary; direct player velocity writer is separately compared in `EXT-02`.

### Slice EXT-02: direct player velocity/impulse writer

- Inventory ID(s): `INV-STATE`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: inherited `Entity.addVelocity(DDD)V` writer only; trigger/cause remains outside this slice, with direct player response in scope.
- A evidence: `Entity#addVelocity(DDD)V`, lines 980-985; `PlayerEntity` inherits `LivingEntity` and `Entity`; `Entity.java` SHA-256 `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`.
- B evidence: `Entity#addVelocity(DDD)V`, lines 1095-1099; `PlayerEntity` inherits `LivingEntity` and `Entity`; `Entity.java` SHA-256 `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`.
- State producers/writers -> consumers/readers: direct impulse `(dx, dy, dz)` -> add to the receiver's three velocity fields and set `velocityDirty` -> player living tick cutoff/travel.
- Parent slices / dependencies / closure evidence: local player inherits this writer; `STATE-01` records its cutoff consumer; `TICK-07` records a reachable reciprocal push producer. External combat/damage causes and non-player outcomes are excluded.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the generic inherited player velocity writer uses the same three additions and dirty-flag write in both endpoints. This closes only the application primitive, not every external velocity producer/correction.
- Finding IDs or checked absence/replacement path: checked method body; no difference in `Entity.addVelocity(DDD)V`.

## Dependency queue and blockers

- `DEP-STATE-01-PRODUCER-CONSUMER`: source worker; full local tick/jump/travel/external correction chain remains open for a cutoff-specific implementation boundary.
- `DEP-AUDITOR`: coordinator; independent source reviewer not assigned.
- All remaining `TICK-*`, `STATE-*`, `COLL-*`, `WORLD-*`, `MOD-*`, and `EXT-*` inventories remain open.

## Finding index

- [TICK-01 — Sprint timeout](findings/TICK-01-sprint-timeout.md)
- [TICK-02 — Flight sneak input rescaling](findings/TICK-02-flight-sneak-input-rescaling.md)
- [STATE-01 — Velocity zero threshold](findings/STATE-01-velocity-zero-threshold.md)
- [TICK-07 — Client-side player push](findings/TICK-07-client-player-push.md)
- [STATE-03 — Sneak collision height](findings/STATE-03-sneak-collision-height.md)
- [WORLD-01 — Trapdoor ladder climbing](findings/WORLD-01-trapdoor-ladder-climbing.md)
- [WORLD-03 — Piston player block ejection](findings/WORLD-03-piston-player-block-ejection.md)
- [COLL-02 — Pane collision shapes](findings/COLL-02-pane-collision-shapes.md)

## Incremental finding snapshot log

### SNAP-STATE-01-01 — superseded blocked evidence reference

- Finding ID: `STATE-01` (`findings/STATE-01-velocity-zero-threshold.md`).
- Evidence/snapshot commit: `f5eca2d932b7544c4eebaa13864491a4e40cba95`.
- Finding-file SHA-256 at that commit: `51506e004c8ae129cf45db365f8ee5bfea0f54c8ec5170f99250003e8f0115e1`.
- Exact source identity: A source manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`, cited `LivingEntity.java` SHA-256 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`; B source manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`, cited `LivingEntity.java` SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`. Source-owner notice says raw/source hashes are unchanged.
- Revised snapshot identity: revision `feather-r1-2026-10-07`; A immutable mapped JAR SHA-256 `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`; B immutable mapped JAR SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`. Worker hash verification and the unavailable-original limitation are recorded above; independent ops review remains pending.
- Finding scope and implementation boundary: the only candidate code boundary is the three strict per-axis `<` comparisons in `LivingEntity#mobTick()V` before jump dispatch/travel: A `0.005`, B `0.003`. Any implementation must change only the historical cutoff at the correct resolved release; first introduction release and exact release-version activation boundary are not established by this pair alone. This is not authorization or an implementation handoff.
- Finding-specific dependencies: `DEP-STATE-01-PRODUCER-CONSUMER` remains open for complete player-path, input velocity-writer, jump/travel consumer, and external correction closure. Do not claim it closed.
- Blind reviewer and decision: not assigned; no acceptance decision.
- Timestamp: 2026-10-07 Europe/Vienna.
- Pair run status/commit at handoff: `active`; evidence commit above. `pair complete: no`.
- Implementation handoff: `blocked` — mapped-artifact integrity is under canonical repair, finding-specific producer/consumer closure remains open, and no independent snapshot reviewer has accepted it.
- Snapshot event: superseded on 2026-10-07 by `SNAP-STATE-01-02` after canonical revision `feather-r1-2026-10-07` was published and freshly hash-checked. This prior entry remains in history and was never accepted.

### SNAP-STATE-01-02 — candidate evidence, handoff blocked

- Finding ID: `STATE-01` (`findings/STATE-01-velocity-zero-threshold.md`).
- Snapshot/evidence commit: `7437cfb2782e7085bd63b9360ba36e07b605f23f`.
- Finding-file SHA-256 at that commit: `8d74c339535db2e86dee838bef9b3a1cf1a52872b97b3c3a20a505552d8dc412`.
- Exact source identity: A source manifest `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`, cited `LivingEntity.java` `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`; B source manifest `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`, cited `LivingEntity.java` `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`.
- Exact artifact identity: original A/B artifact-manifest hashes `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` / `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`; revised immutable mapped JAR hashes `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` / `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`, revision JSON SHA-256 `95e2dc4aa3edba2d287f2bab092c61c0f66874f790af1b8c5d98196c980e105d` / `df0a26fd4c65292530cdad638e6789cc26fcd47875fe1c3eb2daef6de7e3f915`. Source and raw input rows match; original derived JARs are unavailable and original identity/equivalence is unproven. Independent ops verification is pending.
- Verified implementation boundary/evidence: only the strict per-axis cutoff in `LivingEntity#mobTick()V` before jump dispatch/travel (`0.005` A, `0.003` B). First applicable release and version activation outside these endpoints are unknown; do not generalize the threshold to other methods or claim a jump outcome.
- Closed finding-specific dependency IDs: none. `DEP-STATE-01-PRODUCER-CONSUMER` remains open for local tick dispatch, all preceding input/external velocity writers, jump/travel consumers, and authority corrections.
- Blind reviewer and decision: not assigned; no acceptance decision.
- Timestamp: 2026-10-07 Europe/Vienna.
- Pair run status/commit at handoff: `active`; snapshot evidence commit above. `pair complete: no`.
- Implementation handoff: `blocked` — finding-specific dependency is open, independent blind snapshot review is not assigned, and independent ops verification is pending. No implementation feedback has been received.
- Snapshot event: independent ops audit passed on 2026-10-07 after this worker verified both immutable snapshots against `artifact.sha256` and `revision.json`, rehashed source rows, and checked all raw-input manifest rows. The original derived JARs remain unavailable and equivalence remains unproven; no finding acceptance is implied.

### SNAP-COLL-02-PANE-01 — candidate evidence, blind review pending

- Finding ID: `COLL-02` (`findings/COLL-02-pane-collision-shapes.md`).
- Snapshot/evidence commit: `9222079631775ff5a6d0d566624ba3add7c36c2b`.
- Finding-file SHA-256 at that commit: `c9d5ef96fce3dffc0b433da08b7ddcbb3bd615c69aa773ef9fc36193ae50ceb2`.
- Exact source identity: A source manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; B source manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- Exact artifact identity: revised snapshot `feather-r1-2026-10-07`; A immutable mapped JAR SHA-256 `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`; B immutable mapped JAR SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`. Source trees and raw inputs match the original records; original derived JARs are unavailable and their equivalence is unproven. Independent ops audit passed for the revised snapshots; this does not accept the finding.
- Closed finding-specific dependencies: both-side registration and shared PaneBlock subclass path; four-neighbor virtual connection producer and same-mask reachability of all sixteen combinations; exact A/B collision assembly for every mask; World collision collection and player-reachable `Entity#move(DDD)V` consumer.
- Blind reviewer and decision: not assigned; no acceptance decision.
- Timestamp: 2026-10-07 Europe/Vienna.
- Pair run status/commit at handoff: `active`; `pair complete: no`.
- Implementation handoff: `blocked` pending independent blind review of this exact finding snapshot. This candidate does not close the full pane/bar and collision-provider inventory or freeze the source pair.
- Snapshot event: this entry references the immutable evidence commit and finding-file hash above. No implementation feedback has been received.

### SNAP-TICK-02-01 — candidate evidence, blind review pending

- Finding ID: `TICK-02` (`findings/TICK-02-flight-sneak-input-rescaling.md`).
- Snapshot/evidence commit: `20d100ee7d4c1f64bb905e5b05b242b42625af6e`.
- Finding-file SHA-256 at that commit: `2e81aceef1bde41ac6597e28c2fa5f6f3e457e9a8ff22163f38e3b07546d2693`.
- Exact source identity: A source manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; B source manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- Exact artifact identity: revised snapshot `feather-r1-2026-10-07`; A immutable mapped JAR SHA-256 `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`; B immutable mapped JAR SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`. Source trees and raw inputs match the original records; original derived JARs are unavailable and their equivalence is unproven. Independent ops audit passed for the revised snapshots; this does not accept the finding.
- Closed finding-specific dependencies: paired keyboard sneak scaling; local camera/flying/sneaking guards; no-item-use/no-riding precondition; living tick argument handoff; player flight `moveRelative` consumer. Collision, velocity cutoff, and final displacement are explicitly outside the finding claim.
- Blind reviewer and decision: not assigned; no acceptance decision.
- Timestamp: 2026-10-07 Europe/Vienna.
- Pair run status/commit at handoff: `active`; `pair complete: no`.
- Implementation handoff: `blocked` pending independent blind review of this exact finding snapshot. This does not close the full tick, input, or movement inventory.
- Snapshot event: this entry references the immutable evidence commit and finding-file hash above. No implementation feedback has been received.

## Resume checkpoint

- Last completed slice: source roots and per-file hashes reverified; revised snapshots rehashed and ops audit passed; source-backed movement candidates and explicit scope exclusions recorded. Original mapped-JAR identity remains unproven.
- Next bounded slice and exact files/members/body ranges: close active-item and pre-travel push writers; compare input/packet authority boundaries; finish modifier/equipment and collision-provider inventories; then revisit cutoff-specific dependency closure.
- Outstanding dependencies and owners: `DEP-AUDITOR` coordinator; `DEP-STATE-01-PRODUCER-CONSUMER` source worker; remaining coverage slices.
- Assumptions requiring verification: no first-version claim inside the interval; complete resource/provider inventory remains open.

## Implementation reconciliation

- Reconciliation status: pending; full-pair reconciliation follows source freeze. An incremental snapshot remains separately blocked unless its own evidence/dependencies are closed and independently accepted.
- Repository revision inspected:
- Finding -> implementation disposition/evidence:
- Existing implementation without a frozen source finding:
- Coverage gaps routed back to discovery slices:

## Independent source audit

- Reviewer: not assigned.
- Status: pending
- Inventories and call-chain ranges re-walked:
- Concrete missed-slice routes:
- Misses routed to slice/finding IDs and owners:
- Reviewer evidence/date:

## Source audit closure

- Coverage counts: pending.
- Required inventory status/evidence: all inventories remain open.
- Open dependencies: `DEP-STATE-01-PRODUCER-CONSUMER`, `DEP-AUDITOR`, and all pending/in-progress slices.
- Unresolved gaps/limits: comprehensive audit in progress; no equivalence closure claimed.
- Evidence/hash/correspondence audit: source hashes were checked against the admitted source manifests and cited files; derived artifact hashes are stale under the integrity notice and await canonical repair.
- Blind freeze: pending.
- Implementation reconciliation: pending.
- Independent audit: pending.
- Runtime validation: not performed (separate workflow; runtime not authorized).
