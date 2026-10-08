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

### A â€” 1.8.9

- Requested/resolved/version metadata ID: `1.8.9` / `1.8.9` / `1.8.9`.
- Source root: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/`; 1,612 files, 8,248,472 bytes.
- Client jar `build/movement-campaign-2026-10-07/artifacts/1.8.9/client.jar`: SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`.
- Original mapped jar `build/movement-campaign-2026-10-07/artifacts/1.8.9/client-ornithe-feather.jar`: recorded SHA-256 `e36a366fd30d0adda0a803a4956782bab1f67cd644e396d4db6a1cd885548c09`; original jar unavailable and current mutable cache hash differs.
- Mapping `feather-gen2-1.8.9+build.2`; merged jar `build/movement-campaign-2026-10-07/artifacts/yarn/feather-gen2-1.8.9+build.2-mergedv2.jar` SHA-256 `303c4530c79a593b828bd778a97d3577e67f99d6a2c50760e5f9bec6fb32a9da`; mapping file `build/movement-campaign-2026-10-07/artifacts/yarn/feather-gen2-1.8.9+build.2.tiny` SHA-256 `de2023ea2cca9921402fbfcfe6e475f41da4932ea6dbc609e35c505b76a32c63`.
- Source manifest `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather.sources.sha256`: SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1,612/1,612 file hashes match.
- Artifact manifest `build/movement-campaign-2026-10-07/ready/1.8.9/artifacts.sha256`: SHA-256 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`; 36/37 current entries match, with only the unavailable original mapped-JAR entry differing. The separate revised snapshot is verified below.
- Diagnostics `build/movement-campaign-2026-10-07/ready/1.8.9/movement-diagnostics.txt`: SHA-256 `62dc9b445bec2f62b6dac9da501e875377636d08682891891212aea464999d28`; exact release succeeded, required entity/living/player/local-player files exist, no damaged movement body/error reported.

### B â€” 1.9.4

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

### Slice TICK-05: active-item use state before local movement input

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: player tick -> active-item-use update -> polymorphic local `mobTick()` -> input sample/use attenuation -> travel input.
- A evidence: `PlayerEntity.tick()V` lines 183-202 decrements the selected-stack use timer or clears on stack mismatch before `super.tick()` at 228; `LivingEntity.tick()V` lines 1259-1296 dispatches to `mobTick()` at 1296; `LocalClientPlayerEntity.mobTick()V` lines 535-544 applies the use guard after `input.tick()`. `PlayerEntity.java` SHA-256 `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`; `LivingEntity.java` SHA-256 `082831c6578e3a70fa6cea5b90bc3eeefc26678259b66334470de22b90b5b0e4e`; local player SHA-256 `1762b116e6b06d682b7daa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`.
- B evidence: `PlayerEntity.tick()V` lines 170-200 delegates to `LivingEntity.tick()V`; lines 1489-1551 call `tickUsingItem()` at 1491 before `mobTick()` at 1551; `LocalClientPlayerEntity.mobTick()V` lines 645-654 applies the use guard after `input.tick()`. `LivingEntity.java` SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`; local player SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`; player SHA-256 `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`.
- State producers/writers -> consumers/readers: A `setItemInUse` initializes stack/timer (PlayerEntity lines 1487-1493); `stopUsingItem`/`clearItemInUse` and selected-stack mismatch clear it (162-176, 189-200). B `setActiveHand` initializes stack/duration and synced hand flags (LivingEntity lines 1924-1937); local `usingItem`/active-hand overrides and synced-flag handling are in LocalClientPlayerEntity lines 452-489; `tickUsingItem` clears on hand-stack mismatch (1907-1921), and stop/clear reset state (2026-2041). A local consumer uses `hasItemInUse()`; B uses `isUsingItem()`; both scale sideways and forward inputs by `0.2F`, clear double-tap sprint time, and gate sprint initiation while use is active (A lines 540-544, 551-571; B lines 650-654, 662-682).
- Parent slices / dependencies / closure evidence: compared player-reachable superclass dispatch, client use-state writers and sync, tick decrement, stack-mismatch clearing, stop/clear, and the local movement consumers in both source trees.
- Finding IDs or checked absence/replacement path: bounded comparison disposition `compared-no-difference`; both active-use predicates reach the same local input transform before player travel. A updates its timer before `super.tick()`; B updates it inside `LivingEntity.tick()` after `Entity.tick()`. Each update precedes the local `mobTick()` consumer. The client-side zero-duration guard does not finish/clear use on either side; server completion is outside this local-input slice. No movement delta found for this consumer path; broader tick, consumable-effect, and item producer inventories remain open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for locally controlled player movement while actively using a corresponding main-hand item and not riding, both versions apply the same `0.2F` input scaling after input sampling and prevent sprint initiation; no changed movement consequence was found in this bounded path. B also supports an active off-hand, a newer capability with no A counterpart, outside this equivalent main-hand comparison.

### Slice TICK-06: local pre-travel player block push-away

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: four local-player `pushAwayFrom` calls before sprint/jump/flight/riding gates and `super.mobTick()`; each probes block solidity at body offsets and may overwrite one horizontal velocity component.
- A evidence: `LocalClientPlayerEntity.mobTick()V` lines 535-560, `pushAwayFrom(DDD)Z` lines 292-341 and `canSurvive(BlockPos)Z` lines 344-346; SHA-256 `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`.
- B evidence: `LocalClientPlayerEntity.mobTick()V` lines 645-681, `pushAwayFrom(DDD)Z` lines 349-398 and `canSurvive(BlockPos)Z` lines 401-403; SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.
- State producers/writers -> consumers/readers: block state/material/shape -> `canSurvive` -> directional Â±0.1 horizontal velocity overwrite -> pre-travel cutoff and movement.
- Parent slices / dependencies / closure evidence: the state-solid provider subinventory is checked in WORLD-03. The piston-base predicate is unchanged; the EndPortalFrameBlock cube predicate differs. Player velocity cutoff and downstream travel remain separate open consumers.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): a non-`noClip` local-player body sample in an End Portal Frame with a survivable horizontal escape neighbor reaches the unchanged A/B push-away code; A may overwrite one horizontal velocity component while B skips that branch. This is a gate-level source result, not a trajectory claim.
- Finding IDs or checked absence/replacement path: `findings/WORLD-03-end-portal-frame-player-ejection.md` (prior piston claim withdrawn).

### Slice TICK-07: post-travel entity push

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: end of `LivingEntity.mobTick()` after `moveRelative()`; candidate query/filter and reciprocal `Entity.push()` writes.
- A evidence: `LivingEntity.mobTick()V` lines 1450-1455 runs the push query only under `!world.isClient`; `pushAwayCollidingEntities()V` queries the X/Z-expanded box at lines 1463-1475. `Entity.push()V` writes reciprocal horizontal velocity at lines 947-973. `LivingEntity.java` SHA-256 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`; `Entity.java` SHA-256 `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`.
- B evidence: `LivingEntity.mobTick()V` lines 1711-1715 calls the post-travel query without a client guard; `pushAwayCollidingEntities()V` passes the current shape box and `EntityFilter.canBePushedBy(this)` at lines 1742-1754. The filter (EntityFilter.java lines 56-89) admits a client candidate only if pushable, non-spectator, and locally controlled when it is a player; local-player `isLocal() == true` is at LocalClientPlayerEntity lines 306-308, and LivingEntity `isPushable()` returns `!removed` at lines 1851-1853. `Entity.push()V` writes reciprocal horizontal velocity at lines 1061-1088. Hashes: LivingEntity.java `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`; Entity.java `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`; EntityFilter.java `91158a5477935c911178433e1b2628d1d063604a23b08baee4706dd286e7e813`; LocalClientPlayerEntity.java `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.
- State producers/writers -> consumers/readers: another client-side living entity's post-move query -> local player candidate -> reciprocal `Entity.push` velocity write.
- Parent slices / dependencies / closure evidence: exact-box versus expanded query; local-player filter eligibility and push writer guards are recorded in TICK-07.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B runs client-side queries; a client living entity may select the local player and write its horizontal velocity under the documented filter/collision guards. A is server-only.
- Finding IDs or checked absence/replacement path: `findings/TICK-07-client-player-push.md`.

### Slice EXT-01: local-player authority packets and inbound corrections

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: local tick movement/riding packet emission after superclass tick and authoritative correction consumers.
- A evidence: `LocalClientPlayerEntity.tick()V` lines 105-115 calls `sendMovementToServer()` after the local superclass tick when not riding; sender conditions and state updates are lines 117-178. The sender tests the position timeout before incrementing `ticksSinceSentPosition` (146, 165), and emits an on-ground-only packet in the final `else` every camera tick with no position/angle report (146-158). `ClientPlayNetworkHandler.handlePlayerMove()` lines 517-557 applies relative coordinates, zeroes velocity only for absolute axes, applies position/angles, and sends a positional response. Paired server `handlePlayerMove()` detects a response within squared distance `0.25` of its pending teleport target (lines 202-213), and only continues normal player movement after that state is accepted (290-316); it resends after a timeout (357-358). `ServerPlayNetworkHandler.java` SHA-256 `47ef077a7fa0f74cf44e1c8bda449d1b220bf56503b2fef98fd8016e8092df5b`. `LocalClientPlayerEntity.java` SHA-256 `1762b116e6b06d682b7daa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`; `ClientPlayNetworkHandler.java` `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3`.
- B evidence: `LocalClientPlayerEntity.tick()V` lines 143-157 sends player input while riding and conditionally a vehicle packet; non-riding movement uses `sendMovementToServer()` at 159-220. The sender increments `ticksSinceSentPosition` before its timeout test (189-190), sends on-ground-only only when `sentOnGround != onGround` (202-204), then stores the current ground state (218). `ClientPlayNetworkHandler.handlePlayerMove()` lines 546-595 has the same relative/absolute coordinate and velocity-reset logic as A, then sends a teleport-ID acknowledgment and the same positional response. `PlayerMoveS2CPacket` adds and serializes `teleportId` (lines 17-51); `AcceptTeleportC2SPacket` carries that ID (lines 8-34). B server `handleAcceptTeleport()` accepts only the requested ID, updates the server player to the requested teleport position, completes teleportation state if present, and clears the pending position (ServerPlayNetworkHandler lines 348-364). The server `handlePlayerMove()` suspends regular movement while that position remains pending, resending it after more than 20 ticks (367-390). `ServerPlayNetworkHandler.java` SHA-256 `f96587355f356f95362dbca2c57c043fdb3ee8ee2fe81589f4c3379ab1a0336b`. Hashes: `LocalClientPlayerEntity.java` `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`; `ClientPlayNetworkHandler.java` `5a2fc039b81ec0e77df3354826aaaf00dfa6e0d1296cf111864900932e811a4c`; `PlayerMoveS2CPacket.java` `8bad09e146ce746085103081b7e3c6d09855114868267e12be8d869f34f0e77e`; `AcceptTeleportC2SPacket.java` `fb1c5b363e88df9a1edfa02fd569b4c0890e253d837724dc81a7aba08af61603`.
- State producers/writers -> consumers/readers: local `x/y/z`, yaw/pitch, on-ground flag, sprint/sneak flags, and riding state -> outbound packet choice; server `PlayerMoveS2CPacket` -> client `handlePlayerMove` relative/absolute correction and per-axis velocity reset. A stationary, unchanged-angle/ground local-camera tick emits an on-ground-only packet each time; B emits only on a ground-state transition. With the position counter reset to zero, source statement order makes the 20-tick resend occur on call 21 in A and call 20 in B. B also acknowledges the correction teleport ID. B vehicle-packet emission is recorded, while vehicle movement is outside scope for this slice.
- Parent slices / dependencies / closure evidence: paired client and server packet handlers inspected through correction acceptance and movement-state writes; no packet sequence or server trajectory is simulated.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): source-confirmed external authority-path difference for a loaded local-camera tick. A and B apply the same client-side position/rotation correction and per-axis velocity reset for the same relative-argument flags. Their server-side pending-teleport release differs: A accepts a positional response within squared distance `0.25`; B accepts a matching teleport-ID acknowledgment and gates regular movement until then. Outbound stationary packet cadence and position resend counter order also differ. Source shows the player movement/state writes after acceptance; no packet sequence or resulting trajectory is simulated.
- Finding IDs or checked absence/replacement path: `findings/EXT-01-player-authority-packet-differences.md`.

### Slice WORLD-03: state-solid providers in local player ejection

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-COLLISION`, `INV-WORLD-MOVEMENT`.
- Exact behavior boundary and enclosing guards/order checked: `LocalClientPlayerEntity.mobTick()`'s four `pushAwayFrom(DDD)Z` calls -> `canSurvive(BlockPos)Z` -> A `Block#isSolid()Z` inputs (`Material.isSolidBlocking`, block `isCube`, block `isSignalSource`) versus B `BlockState#isSolid()Z` -> `Block#isSolid(BlockState)Z` (state material, state `isCube`, state `isSignalSource`) -> directional horizontal velocity writes.
- A evidence: `LocalClientPlayerEntity.java` SHA-256 `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`; `Block.java` SHA-256 `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` (`isSolid` 237-239, default `isCube` 245-247, default `isSignalSource` 643-645); `EndPortalFrameBlock.java` SHA-256 `c2c876aefe34d001ff0e3eebddd0df01ee85b0bf499e205eb1eb95ec44857b9e` (STONE constructor 23-25; analog source only 61-63); `Material.java` SHA-256 `017713d76afe726ca243ce32cbc35c13d3f0f0e7e5d90d122f81103cc2ca1bd2` (STONE and `isSolidBlocking` 100-102).
- B evidence: `LocalClientPlayerEntity.java` SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`; `Block.java` SHA-256 `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` (`isSolid(BlockState)` 214-216, default `isCube(BlockState)` 223-225, default `isSignalSource(BlockState)` 532-534); `StateDefinition.java` SHA-256 `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` (`isSolid` 293-295, `isSignalSource` 298-300); `EndPortalFrameBlock.java` SHA-256 `1c6495a6d6c5d777eb643983c7e7b8151bb5b99ef1a4a3b991655792a0ad58eb` (STONE constructor 30-32; `isCube(BlockState)` false at 109-111; analog source at 68-70); `Material.java` SHA-256 `f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19` (STONE and `isSolidBlocking` 102-104).
- Provider/registration comparison: both registries contain `end_portal_frame` at `Block.java` A 1077-1087 / B 988-998; both constructors use `Material.STONE`. A EndPortalFrame inherits `Block#isCube()==true`; `isAnalogSignalSource()==true` does not override the separate `isSignalSource()`, whose A default is false. B EndPortalFrame explicitly returns false from `isCube(BlockState)`, and B's signal-source default remains false. Thus the block is solid in A and non-solid in B for this gate. For piston bases, A `PistonBaseBlock#isCube()` and B `PistonBaseBlock#isCube(BlockState)` both explicitly return false; the earlier piston-only candidate is refuted. A AirBlock inherits cube=true and B returns false, but both produce non-solid results because `Material.AIR.isSolidBlocking()` is false. The remaining B overrides with changed/default-false outcomes are ChorusFlower, ChorusPlant, EndGateway, EndRod, and GrassPath, which have no A counterpart in the paired source tree and are outside this one-way historical-block scope. The paired `isSignalSource` override inventory is semantically equivalent: the only expression rewrite is ChestBlock A `type == 1` versus B `type == ChestBlock.Type.TRAP`, with matching trapped-chest registrations; B's only `getMaterial(BlockState)` override returns the unchanged block material. Source hashes: A/B `ChestBlock.java` `8072bc317398297d1327a3be4edd8a66381a1a1021a6c4a8f3fbeebca2891d51` / `0fc74197f3f4710268944bb802a1621dc8ee359c29289ec8ce878ad0b2b834ce`; registry `Block.java` hashes are the A/B values above.
- State producers/writers -> consumers/readers: registered End Portal Frame block/state -> material/cube/signal predicates -> `canSurvive`'s current and above positions -> local player `pushAwayFrom` escape search -> `velocityX` or `velocityZ` set to `±0.1F` -> pre-travel velocity cutoff/travel. The four local-player call sites and helper bodies are A `LocalClientPlayerEntity.java` lines 292-346 and 546-549, B lines 349-403 and 657-660; their file hashes are above.
- Parent slices / dependencies / closure evidence: checked shared block classes' `isCube` overrides for the pair, state `isSignalSource` overrides, B state material accessor, `EndPortalFrameBlock` registration/material and A/B helper call sites. This closes only the solid-provider comparison feeding TICK-06; collision-shape/provider inventories and downstream velocity/travel consumers remain open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): when a non-`noClip` body probe lands in an End Portal Frame block and at least one horizontal neighbor position is survivable, A enters escape search and can write the nearest-direction horizontal velocity; B treats that frame state as non-solid, can accept the sampled location when the upper position is also non-solid, and skips that write. The method's unchanged tie-breaking and velocity writes are not generalized to a resulting trajectory.
- Finding IDs or checked absence/replacement path: `findings/WORLD-03-end-portal-frame-player-ejection.md`; piston-base path checked no-difference.

### Slice STATE-02: creative-flight movement reset

- Inventory ID(s): `INV-TICK`, `INV-STATE`.
- Exact behavior boundary and enclosing guards/order checked: `PlayerEntity.moveRelative(FF)V` creative-flight wrapper around living movement.
- A evidence: `PlayerEntity.moveRelative(FF)V` lines 1279-1294; SHA-256 `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`.
- B evidence: `PlayerEntity.moveRelative(FF)V` lines 1371-1390; SHA-256 `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`.
- State producers/writers -> consumers/readers: creative flying and not riding -> living move -> B resets fall distance (damage state, excluded) and clears flag 7 (B-only fall-flight state, excluded).
- Parent slices / dependencies / closure evidence: scoped against excluded fall-damage simulation and modern-only fall flight.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): the changed values feed excluded damage state or a modern-only flight mode; neither is a historical player-movement mechanic for A-era maps.
- Finding IDs or checked absence/replacement path: no implementation finding. B additionally resets fall distance (fall-damage state) and clears flag 7 (B-only fall-flight state) after living movement; both consequences are outside this campaign movement scope. Evidence: paired `PlayerEntity#moveRelative(FF)V` bodies, A lines 1279-1294 and B lines 1371-1390; source hashes are in their evidence rows above.

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
- A evidence: `LivingEntity` registers `MOVEMENT_SPEED` (line 147), `setSprinting(Z)V` updates the sprint modifier (1026-1036), Jump Boost adds `(amplifier + 1) * 0.1F` (1092-1100), and Depth Strider adjusts water friction/acceleration (1201-1217). The equipment update applies item modifiers without a slot filter (1274-1287). Paired source hashes: `LivingEntity.java` `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`; `PlayerEntity.java` `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`; `ItemStack.java` `32917e0c486e8ba2a2dc9f91545c4f2a95c78e3a341f3ba1340993efac99c360`; `StatusEffect.java` `f9bb4d1839337229cb6f6d8dc9ae17a49cfad757a42388e00abbaa68be2b21a3`.
- B evidence: matching sprint/jump/water behavior at `LivingEntity` lines 1180-1189, 1275-1283, and 1431-1448; equipment update passes each `EquipmentSlot` to `ItemStack.getAttributeModifiers(slot)` (1505-1536). The parser filters a present NBT `Slot` against the worn slot (ItemStack lines 700-721). Paired source hashes: `LivingEntity.java` `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`; `PlayerEntity.java` `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`; `ItemStack.java` `9e031b6f4e06c608aa844c6b3eb1d6ec0875a7ebbb8d78ecb34404b42945e692`; `StatusEffect.java` `cd56502d70b9c742dcffc61fbac6337f268838e35f268ffead4610ff964bea89`.
- State producers/writers -> consumers/readers: equipment and effect updates -> movement-speed attribute; jump effect -> vertical impulse; four armor slots -> Depth Strider level -> water acceleration/friction -> movement.
- Parent slices / dependencies / closure evidence: player base speed is `0.1F` on both sides; sprint modifier UUID/value/operation, status-effect modifier formulas and apply/remove/upgrade lifecycle, Jump Boost formula, and ordinary four-armor-slot Depth Strider lookup match. A's water acceleration uses `getSpeed() * 1.0F`; B uses `getSpeed()`, with no finite-value behavior difference claimed. The bounded source difference is conditional on an equipped item whose `generic.movementSpeed` NBT modifier has a `Slot` value different from the actual slot; no specific map item was examined. Modern-only Elytra/Frost Walker remain excluded; Levitation is scoped separately in `MOD-01`.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): A accepts each valid NBT `AttributeModifiers` entry without reading `Slot`; B accepts it only when the key is absent or matches the equipment slot. A mismatched-slot movement modifier can therefore change the player movement-speed attribute in A and be filtered in B. This is a source-level conditional path; map-specific prevalence and trajectory are unestablished.
- Finding IDs or checked absence/replacement path: `findings/MOD-02-item-attribute-slot-filter.md`; bounded unchanged paths recorded above.

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

- `DEP-AUDITOR`: coordinator; full-pair independent source audit not assigned. The focused review below accepts only the bounded STATE-01 cutoff snapshot and does not close the pair.
- `DEP-TICK-06-STATE-SOLID-PROVIDERS`: closed for paired A historical block classes after comparing `isSolid` material/cube/signal inputs, B state dispatch/accessors, registrations, and non-piston providers. Piston is no-difference; End Portal Frame is routed to corrected `WORLD-03`; B-only modern blocks are out of scope. Full collision-shape inventory and downstream velocity/travel closure remain separate open dependencies.
- `DEP-WORLD-03-BLIND-SNAPSHOT`: open; independent review of the corrected End Portal Frame candidate is pending.
- All remaining `TICK-*`, `STATE-*`, `COLL-*`, `WORLD-*`, `MOD-*`, and `EXT-*` inventories remain open.

## Finding index

- [TICK-01 â€” Sprint timeout](findings/TICK-01-sprint-timeout.md)
- [TICK-02 â€” Flight sneak input rescaling](findings/TICK-02-flight-sneak-input-rescaling.md)
- [STATE-01 â€” Velocity zero threshold](findings/STATE-01-velocity-zero-threshold.md)
- [TICK-07 â€” Client-side player push](findings/TICK-07-client-player-push.md)
- [STATE-03 â€” Sneak collision height](findings/STATE-03-sneak-collision-height.md)
- [WORLD-01 â€” Trapdoor ladder climbing](findings/WORLD-01-trapdoor-ladder-climbing.md)
- [WORLD-03 - End Portal Frame player ejection](findings/WORLD-03-end-portal-frame-player-ejection.md)
- [COLL-02 â€” Pane collision shapes](findings/COLL-02-pane-collision-shapes.md)

## Incremental finding snapshot log

### SNAP-WORLD-03-STATE-SOLID-01 — piston candidate withdrawn; End Portal Frame candidate recorded

- Prior finding ID/file: `WORLD-03` / `findings/WORLD-03-piston-player-block-ejection.md`; prior candidate file SHA-256 `a3125e53e15bbe8f4a7692bd3494716f9845ff69c13db551b379654852c5d07d`.
- Exact source identity: source manifests A/B remain SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` / `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; cited source files match their manifests.
- Correction evidence: piston `isCube` false on A and B; EndPortalFrame inherits A cube=true but B explicitly returns false, with `Material.STONE` and signal-source inputs making A solid and B non-solid. Exact file hashes and call-path evidence are in the corrected `WORLD-03-end-portal-frame-player-ejection.md` finding.
- Provider inventory boundary: paired historical block cube overrides, signal-source overrides, state-material accessor, relevant block registrations, and local-player `canSurvive` consumers compared. Air yields no final predicate change; B-only modern blocks are excluded under one-way scope. Full collision shape and movement consumer inventories remain open.
- Evidence commit: `a1fd03a9ce1446633b09c295c806f3ffa2a7522a`; corrected finding file SHA-256: `f45dfb003c1dfcc64df5c5d7710fd22a4e6c311b1b8b50a3d77ed1537689479d`.
- Decision: prior piston-specific finding withdrawn; corrected End Portal Frame finding is a new candidate at the same coverage slice and awaits independent blind review. No pair freeze or implementation handoff is implied.
- Timestamp: 2026-10-08 Europe/Vienna.


### SNAP-STATE-01-01 â€” superseded blocked evidence reference

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
- Implementation handoff: `blocked` â€” mapped-artifact integrity is under canonical repair, finding-specific producer/consumer closure remains open, and no independent snapshot reviewer has accepted it.
- Snapshot event: superseded on 2026-10-07 by `SNAP-STATE-01-02` after canonical revision `feather-r1-2026-10-07` was published and freshly hash-checked. This prior entry remains in history and was never accepted.

### SNAP-STATE-01-02 â€” accepted bounded source cutoff; pair remains open

- Finding ID: `STATE-01` (`findings/STATE-01-velocity-zero-threshold.md`).
- Snapshot/evidence commit: `7437cfb2782e7085bd63b9360ba36e07b605f23f`.
- Finding-file SHA-256 at that commit: `8d74c339535db2e86dee838bef9b3a1cf1a52872b97b3c3a20a505552d8dc412`.
- Exact source identity: A source manifest `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`, cited `LivingEntity.java` `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`; B source manifest `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`, cited `LivingEntity.java` `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`.
- Exact artifact identity: original A/B artifact-manifest hashes `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` / `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`; revised immutable mapped JAR hashes `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` / `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`, revision JSON SHA-256 `95e2dc4aa3edba2d287f2bab092c61c0f66874f790af1b8c5d98196c980e105d` / `df0a26fd4c65292530cdad638e6789cc26fcd47875fe1c3eb2daef6de7e3f915`. Source and raw input rows match; original derived JARs are unavailable and original identity/equivalence is unproven. Independent ops verification passed.
- Verified implementation boundary/evidence: only the strict per-axis cutoff in `LivingEntity#mobTick()V` before jump dispatch/travel (`0.005` A, `0.003` B). First applicable release and version activation outside these endpoints are unknown; do not generalize the threshold to other methods or claim a jump outcome.
- Closed finding-specific dependency IDs: bounded local cutoff producer/consumer trace closed for this exact snapshot; the wider source inventories remain open and no universal downstream trajectory is claimed.
- Blind reviewer and decision: accepted for the bounded source-level cutoff delta by the focused blind reviewer in commit `fb0415396838962eb421768daa07f3fd9ff44796`; exact accepted file hash remains `8d74c339535db2e86dee838bef9b3a1cf1a52872b97b3c3a20a505552d8dc412`.
- Timestamp: 2026-10-07 Europe/Vienna.
- Pair run status/commit at handoff: `active`; snapshot evidence commit above. `pair complete: no`.
- Source decision: accepted for this bounded finding only. Implementation reconciliation/feedback remains sealed until full-pair freeze; no implementation or wiki feedback has been supplied.
- Snapshot event: independent ops audit passed on 2026-10-07 after both immutable snapshots were checked against `artifact.sha256` and `revision.json`, source rows rehashed, and raw-input manifest rows checked. The original derived JARs remain unavailable and equivalence remains unproven. The later focused blind review accepted this exact finding snapshot; it did not accept the whole pair.

### SNAP-COLL-02-PANE-01 â€” rejected; corrected snapshot follows

- Finding ID: `COLL-02` (`findings/COLL-02-pane-collision-shapes.md`).
- Snapshot/evidence commit: `9222079631775ff5a6d0d566624ba3add7c36c2b`.
- Finding-file SHA-256 at that commit: `c9d5ef96fce3dffc0b433da08b7ddcbb3bd615c69aa773ef9fc36193ae50ceb2`.
- Exact source identity: A source manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; B source manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- Exact artifact identity: revised snapshot `feather-r1-2026-10-07`; A immutable mapped JAR SHA-256 `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`; B immutable mapped JAR SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`. Source trees and raw inputs match the original records; original derived JARs are unavailable and their equivalence is unproven. Independent ops audit passed for the revised snapshots; this does not accept the finding.
- Closed finding-specific dependencies: both-side registration and shared PaneBlock subclass path; four-neighbor virtual connection producer and same-mask reachability of all sixteen combinations; exact A/B collision assembly for every mask; World collision collection and player-reachable `Entity#move(DDD)V` consumer.
- Blind reviewer and decision: focused reviewer commit `fb0415396838962eb421768daa07f3fd9ff44796` rejected this exact snapshot solely for the wrong normal movement reference.
- Timestamp: 2026-10-07 Europe/Vienna.
- Pair run status/commit at handoff: `active`; `pair complete: no`.
- Implementation handoff: rejected for this exact snapshot; the corrected replacement is `SNAP-COLL-02-PANE-02`. This does not close the full pane/bar and collision-provider inventory or freeze the source pair.
- Snapshot event: the focused blind review at commit `fb0415396838962eb421768daa07f3fd9ff44796` verified the geometry table and rejected this snapshot solely because its movement reachability citations pointed inside `Entity#pushAwayFrom`. See corrected replacement `SNAP-COLL-02-PANE-02` below. No geometry contradiction or implementation feedback was supplied.

### SNAP-COLL-02-PANE-02 â€” corrected candidate, fresh blind decision pending

- Finding ID: `COLL-02` (`findings/COLL-02-pane-collision-shapes.md`).
- Snapshot/evidence commit: `cd978384d9d360c584e139f68bda5bbafd8749fa`.
- Finding-file SHA-256 at that commit: `c24e71656773beb12e5f96f05f8c37fb56b43464b483c4abd347a35a0e368190`.
- Exact source identity: A manifest `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; B manifest `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- Exact artifact identity: revision `feather-r1-2026-10-07`; A/B immutable mapped JAR SHA-256 `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` / `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`. Original derived JARs are unavailable and equivalence is unproven; independent ops verification passed for the revised snapshots.
- Corrected reachability evidence: A `World#getCollisions(Entity,Box)` lines 891-923 (block dispatch 921), then `Entity#move(DDD)V` lines 439 and 469; B `World#getCollisions(Entity,Box)` lines 899-935 (dispatch 933), then `Entity#move(DDD)V` lines 509 and 542 before axis clipping. All cited source hashes are in the finding.
- Closed finding-specific dependencies: pane variant registration/inheritance; four-neighbor mask producer and all sixteen reachable masks; collision assembly; normal world-to-player movement dispatch. The neighbor predicate may map other block classes to different masks, and the full collision-provider inventory remains open.
- Blind reviewer and decision: fresh decision pending for this exact commit and finding-file hash.
- Timestamp: 2026-10-07 Europe/Vienna.
- Pair run status: `active`; `pair complete: no`.
- Implementation handoff: pending review; no implementation feedback has been supplied.

### SNAP-STATE-03-01 â€” frozen bounded candidate, blind decision pending

- Finding ID: `STATE-03` (`findings/STATE-03-sneak-collision-height.md`).
- Snapshot/evidence commit: `c942939e8755d67ca1e3445dfd7680e7d0b68b41`.
- Finding-file SHA-256 at that commit: `c92149bb776ae1e8a91bc7e2cbb85ca47ada9f55b46779a7daebdd80def4f7dd`.
- Exact source identity: A manifest `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; B manifest `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- Exact artifact identity: revision `feather-r1-2026-10-07`; A/B immutable mapped JAR SHA-256 `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` / `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`. Original derived JARs are unavailable and equivalence is unproven; independent ops verification passed for the revised snapshots.
- Closed finding-specific dependencies: A's complete PlayerEntity size-writer inventory has no sneaking-conditioned write; B's tick-end pose branch, priority guards, `1.65F` candidate, `World#getCollisions(Box)` block-provider path, conditional setter, fixed-minimum resize, and unchanged width are cited. The collision-fit condition is explicit and linked to `SNAP-COLL-02-PANE-02`; the finding claims no fit result for unspecified blocks/worlds.
- Blind reviewer and decision: pending for this exact snapshot.
- Timestamp: 2026-10-07 Europe/Vienna.
- Pair run status: `active`; `pair complete: no`.
- Implementation handoff: pending blind review; no implementation feedback has been supplied.

### SNAP-TICK-02-01 â€” candidate evidence, blind review pending

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

- Last completed slices/checkpoints: TICK-05 active-item countdown and local input consumer compared with no equivalent-main-hand movement delta; TICK-07 paired client-push source evidence committed; EXT-01 packet-selection/correction source finding recorded with downstream server effect unresolved; MOD-02 paired attribute/effect inventory committed at `313e7909e0bf4be57ff33df1412a378b3a6de390`, including the conditional NBT equipment-slot filter finding; exact bounded STATE-01 cutoff snapshot independently accepted; corrected COLL-02 and STATE-03 candidates preserved. Original mapped-JAR identity remains unproven.
- Supersession of the piston candidate: the prior finding-file SHA-256 was `a3125e53e15bbe8f4a7692bd3494716f9845ff69c13db551b379654852c5d07d`. B `PistonBaseBlock#isCube(BlockState)` returns false at lines 216-218 (file SHA-256 `4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36`); A `PistonBaseBlock#isCube()` returns false at 223-225 (SHA-256 `3c96698a674714446f9fd0d6cc1c4d5eb72937d9a2f396516ddb2461c3ea4929`). The prior piston-specific finding is withdrawn. The provider comparison found a distinct non-piston End Portal Frame delta, now recorded in the corrected `WORLD-03-end-portal-frame-player-ejection.md` finding; the prior claim and exact evidence are retained in its superseded-candidate record.
- Next bounded comparison: continue the remaining collision-shape/provider inventory and downstream TICK-06 velocity-cutoff/travel consumer closure. The state-solid provider subinventory is closed for paired A historical block classes: shared cube overrides were compared; A/B signal-source overrides and B state-material lookup were compared; Air and modern-only block cases were dispositioned; the End Portal Frame difference is recorded in WORLD-03. Entry paths: A `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` lines 292-346 and `Block.java` lines 237-239; B same-named local-player file lines 349-403, `block/state/StateDefinition.java` lines 293-300, and `Block.java` lines 214-216. Shared sources remain read-only. TICK-06 source hashes: A `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`, B `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.
- Outstanding dependencies and owners: `DEP-AUDITOR` coordinator for full-pair review; fresh independent decisions for exact candidates `SNAP-COLL-02-PANE-02` and `SNAP-STATE-03-01`; `WORLD-03` End Portal Frame snapshot awaits independent review; remaining open `INV-TICK`, `INV-STATE`, `INV-COLLISION`, `INV-WORLD-MOVEMENT`, `INV-MODIFIERS`, and `INV-EXTERNAL` source inventories. The pair remains active and incomplete.
- Assumptions requiring verification: no first-version claim inside the interval; complete resource/provider inventory remains open; original mapped-JAR identity/equivalence remains unproven.
- Stop checkpoint: follow-up branch `feat/source-discovery-movement-source-1-8-9-1-9-4-solid-resume`, based on requested source branch commit `0481dfe227ccc14b935352c306afbd04035cc635`; the named resume branch is checked out by another task. Candidate evidence is commit `a1fd03a9ce1446633b09c295c806f3ffa2a7522a`; the worktree checkpoint was committed after it. This checkpoint includes the bounded TICK-06/WORLD-03 provider comparison and corrected finding. No tests, builds, runtime, decompile, or gameplay validation was run.

## Implementation reconciliation

- Reconciliation status: pending; full-pair reconciliation follows source freeze. An incremental snapshot remains separately blocked unless its own evidence/dependencies are closed and independently accepted.
- Repository revision inspected:
- Finding -> implementation disposition/evidence:
- Existing implementation without a frozen source finding:
- Coverage gaps routed back to discovery slices:

## Independent source audit

- Reviewer: focused blind source review, commit `fb0415396838962eb421768daa07f3fd9ff44796`; report SHA-256 `163150719a588bed111f01bcba60545aee37105a6f30f0309b39c8275805f73a`; path `C:/Users/Wolfi/.codex/worktrees/critical-1-8-movement-source-review/LegacyParkourCompat/workflows/coverage-review-2026-10-07/critical-1.8.9.md`.
- Status: partial focused review; no full-pair audit or freeze.
- Inventories and call-chain ranges re-walked: direct ground-jump impulse/height; PaneBlock collision assembly, registrations, World collision dispatch and `Entity#move`; PlayerEntity sneak pose writes, B collision-fit query and `Entity#setSize`.
- Concrete missed-slice routes: prior COLL-02 candidate cited `Entity#pushAwayFrom`'s `getBlockCollisions` rather than the normal movement `World#getCollisions(Entity,Box)` chain. The finding was corrected in `SNAP-COLL-02-PANE-02`.
- Misses routed to slice/finding IDs and owners: COLL-02 corrected by source worker; STATE-03 frozen with explicit collision-fit and pane dependency; STATE-01 bounded cutoff snapshot accepted. Full inventory gaps remain with source worker/coordinator.
- Reviewer evidence/date: STATE-01 accepted at its exact immutable file hash; prior COLL-02 snapshot rejected solely for the reachability citation, geometry table confirmed; STATE-03 source path confirmed and awaits review of the new exact candidate. 2026-10-07.

## Source audit closure

- Coverage counts: pending.
- Required inventory status/evidence: all inventories remain open.
- Open dependencies: `DEP-AUDITOR` for full-pair review, fresh decisions for `SNAP-COLL-02-PANE-02` and `SNAP-STATE-03-01`, and all remaining pending/in-progress slices.
- Unresolved gaps/limits: comprehensive audit in progress; no equivalence closure claimed.
- Evidence/hash/correspondence audit: source and raw inputs match the admitted manifests; revised immutable mapped-artifact hashes match their `artifact.sha256` and `revision.json` records, and the independent ops audit passed. Original derived mapped JARs remain unavailable; their identity/equivalence is unproven.
- Blind freeze: pending.
- Implementation reconciliation: pending.
- Independent audit: pending.
- Runtime validation: not performed (separate workflow; runtime not authorized).
