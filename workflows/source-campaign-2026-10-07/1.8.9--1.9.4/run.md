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
- `INV-COLLISION` status=pending; slice_ids=COLL-01, COLL-01-NOCLIP, COLL-01-SNEAK-EDGE, COLL-01-AXES, COLL-01-STEP, COLL-01-FLAGS, COLL-01-VEHICLE, COLL-02, WORLD-SLAB-SHAPES, WORLD-SNOW-SHAPE, WORLD-TRAPDOOR-SHAPES, WORLD-LADDER-SHAPES; block callbacks, post-collision support logic and most shape providers remain open; bounded slab, snow-layer, trapdoor and ladder shapes compared
- `INV-WORLD-MOVEMENT` status=pending; slice_ids=WORLD-01, WORLD-02, WORLD-03, COLL-02, WORLD-SLAB-SHAPES, WORLD-SNOW-SHAPE, WORLD-TRAPDOOR-SHAPES, WORLD-LADDER-SHAPES; other world states, neighboring blocks, fluids, and non-boat vehicle passenger paths remain open; bounded shape dispositions recorded
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
- Exact behavior boundary and enclosing guards/order checked: keyboard input sampling during local player's inherited `tick()` inside `Entity.rideTick()`, then B-only directional-field transfer after `super.rideTick()` to `BoatEntity.setInput(ZZZZ)V`.
- A evidence: `Input.java` declares only movement magnitudes plus jumping/sneaking (SHA-256 `aabf9cfed6156121c67c9f16002ccc02f14fa6213d5717732dcf9a1d26368b74`); `KeyboardInput.tick()V` writes magnitudes/jump/sneak (SHA-256 `a5e5b2033322f8867cd845e4095cea7a82888f6382cbfafefc559cf9ba3a76dd`); `LocalClientPlayerEntity.mobTick()V` polls input at line 539 and writes movement speeds at 473-474 (SHA-256 `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`); inherited `Entity.rideTick()V` calls virtual `this.tick()` at 1282 and returns to the vehicle update at 1284 (SHA-256 `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`). `BoatEntity.tick()V` consumes rider `sidewaysSpeed`/`forwardSpeed` at 259-263 (SHA-256 `77ef72c28024b754ff76f2c67e14dd61832394bac2bd7b62b29708560e588da2`).
- B evidence: `Input.java` adds four directional booleans (SHA-256 `206dea2ff5977599596c907c54f36300c4e72d07d6778d82e8850f57bb0fb009`); `KeyboardInput.tick()V` samples them (SHA-256 `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`); `LocalClientPlayerEntity.mobTick()V` polls input at line 649 and copies movement speeds at 589-590, while `rideTick()V` sets boat inputs at 763-769 after inherited `rideTick()` (SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`). `BoatEntity.setInput(ZZZZ)V` writes directional fields and `updatePaddles()V` consumes them at 527-554 (SHA-256 `3d41b9df0ef0d36158105235c92b35dac4b51070476e7ef688520961d1d56e57`).
- State producers/writers -> consumers/readers: keyboard state -> B direction booleans -> B boat input fields -> boat paddle/velocity/yaw state. A's boat consumes the passenger's movement magnitudes instead. The last consumers write vehicle state, whose physics is explicitly outside this campaign's scope.
- Parent slices / dependencies / closure evidence: the complete input polling -> passenger tick -> B transfer path and both boat consumer forms are paired. Direct player passenger position/yaw writes are tracked separately in WORLD-02; boat velocity, buoyancy, water, collision and vehicle motion are excluded.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): B adds directional input transport for client-authoritative boat steering, replacing A's passenger movement-magnitude reads inside boat physics. The only changed consumer is vehicle state/motion, which the campaign excludes; no player-motion finding is based on the boat's resulting trajectory.
- Finding IDs or checked absence/replacement path: direct passenger transform finding is `WORLD-02-boat-rider-transform.md`; movement-magnitude path and B field consumer are cited above.

### Slice WORLD-02: boat movement and rider path

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: loaded passenger dispatch through `Entity.rideTick()V`, vehicle-specific passenger position callback, and direct player position/yaw/head-yaw writers. Boat water classification, buoyancy, drag, propulsion, collisions and vehicle displacement are inventoried as excluded vehicle physics, not compared as player movement.
- A evidence: `World.tickEntities()V` selects `rideTick()` for mounted entities at lines 1341-1346 (World.java SHA-256 `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee`); `Entity.rideTick()V` calls `this.tick()` and then `vehicle.updateRiderPositon()` at 1275-1286 (SHA-256 `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`); `BoatEntity.updateRiderPositon()V` writes player position with a yaw-based 0.4 horizontal offset and mount/ride heights at 366-372 (SHA-256 `77ef72c28024b754ff76f2c67e14dd61832394bac2bd7b62b29708560e588da2`). A `Entity.rideTick()` has no rider yaw/head-yaw write; its yaw/pitch delta accumulators are only normalized, clamped and reduced at 1285-1324, and source-wide reference search found no other consumers.
- B evidence: `World.tickEntities()V` selects `rideTick()` for mounted entities at lines 1406-1413 (World.java SHA-256 `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`); `Entity.rideTick()V` dispatches `entity.updateRiderPositon(this)` after `this.tick()` at 1432-1444 (SHA-256 `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`); `BoatEntity.updateRiderPositon(Entity)V` sets passenger position, increments rider yaw and head yaw by `yawVelocity`, and calls `copyEntityData` at 558-595 (SHA-256 `3d41b9df0ef0d36158105235c92b35dac4b51070476e7ef688520961d1d56e57`).
- State producers/writers -> consumers/readers: boat yaw/mount geometry -> direct rider x/y/z assignment; B boat `yawVelocity` -> rider yaw/head-yaw assignment and clamped body/head orientation. This is a player-state response after vehicle tick; source comparison does not trace how the boat acquired its velocity or trajectory.
- Parent slices / dependencies / closure evidence: input acquisition/transfer is paired in TICK-04. Both-side world passenger dispatch, base rider callback and BoatEntity override are checked. Boat movement/physics remains excluded; other rideable entities and external authority/correction paths remain separate open inventory work.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): for a locally controlled player riding a boat that reaches the normal passenger-update callback, A positions the passenger 0.4 blocks from the boat center along boat yaw and does not directly change rider yaw/head yaw; B centers a single passenger horizontally and directly modifies rider yaw/head yaw from boat yaw velocity before applying its orientation clamp. This is a direct player position/orientation write, not a claim about vehicle movement or a resulting trajectory.
- Finding IDs or checked absence/replacement path: `findings/WORLD-02-boat-rider-transform.md`.

### Slice COLL-01: axis collision and step resolution

- Inventory ID(s): `INV-COLLISION`, `INV-STATE`.
- Exact behavior boundary and enclosing guards/order checked: normal clipped movement path only; collision list, Y/X/Z clipping, two step candidates/tie-break, support block, velocity cancellation and callbacks. The same-vehicle candidate exclusion is split into COLL-01-VEHICLE; remaining behaviors are still open.
- A evidence: `Entity.java` `move(DDD)V` lines 371-638; hash pending.
- B evidence: `Entity.java` `move(DDD)V` lines 441-722; hash pending.
- State producers/writers -> consumers/readers: requested displacement + shape providers -> clipped displacement -> position/ground/collision/support flags -> callbacks and velocity writes.
- Parent slices / dependencies / closure evidence: all reachable shapes, support and callback providers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): axis ordering and step candidate selection appear structurally aligned; no no-difference claim until dependency closure.
- Finding IDs or checked absence/replacement path: pending.

### Slice COLL-01-VEHICLE: same-vehicle collision candidate exclusion

- Inventory ID(s): `INV-COLLISION`, `INV-STATE`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: entity-candidate loop in `World.getCollisions(Entity,Box)`, boat collision shape providers, and the mounted local-player `Entity.move(DDD)V` caller.
- A evidence: `World.getCollisions(Entity,Box)` lines 927-942 queries entities in an expanded box and compares `entity.rider` and `entity.vehicle` against the whole `list2` at 930-932 (World.java SHA-256 `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee`). Immutable revised mapped artifact bytecode `World#getCollisions(Entity,Box)` offsets 300-321 confirms `getfield Entity.rider`, `aload list2`, `if_acmpeq`, then `getfield Entity.vehicle`, `aload list2`, `if_acmpne`; revised A jar SHA-256 `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`. `BoatEntity#getCollisionShape()Box` returns its shape at lines 58-60 (BoatEntity.java SHA-256 `77ef72c28024b754ff76f2c67e14dd61832394bac2bd7b62b29708560e588da2`); base `Entity#getCollisionAgainstShape(Entity)Box` returns null at 1271-1273 (Entity.java SHA-256 `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`).
- B evidence: `World.getCollisions(@Nullable Entity,Box)` lines 941-957 compares each entity candidate via `!entity.hasSameVehicle(entity2)` at 944-947 (World.java SHA-256 `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`). `Entity.hasSameVehicle(Entity)Z` compares root vehicle identities at lines 2226-2238; `BoatEntity#getCollisionShape()Box` returns its shape at 104-108; the base `Entity#getCollisionAgainstShape(Entity)Box` returns null at 1427-1430. B revised mapped bytecode at offsets 419-431 confirms the `hasSameVehicle` call guards candidate collision shape collection; revised B jar SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`.
- Reachability and state flow: for a locally controlled mounted player, `World.tickEntities()` dispatches to `Entity.rideTick()`, which invokes the rider's virtual `tick()`; `LivingEntity.mobTick()` calls `moveRelative()`, whose ordinary local movement path invokes `Entity.move()`. `Entity.move()` calls the paired world collision query (A lines 439-444; B lines 509-518; Entity hashes above). B's `getVehicle()` follows the mount chain and returns the boat for both the player and boat, so `hasSameVehicle` is true for the passenger's own boat. Both BoatEntity providers expose their shape; the player's base `getCollisionAgainstShape` is null.
- Parent slices / dependencies / closure evidence: entity-query loop, boat collision shape, base opposing-shape callback, mounted player dispatch and ordinary travel-to-move call checked. Entity collision response through all clipping axes remains open in COLL-01. The revised mapped JAR bytecode confirms the A expression but does not resolve the documented unavailable-original mapped-JAR identity/equivalence limitation.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): in A the reference comparisons are against the result `List<Entity>` rather than a list element, so the boat candidate is not excluded; its collision box is added when it intersects the player's query box. In B a player riding that boat and the boat have the same root vehicle, so the boat candidate is skipped. This is a source-confirmed collision-query input difference for the mounted player; no clipped displacement or runtime trajectory is claimed.
- Finding IDs or checked absence/replacement path: `findings/COLL-01-vehicle-collision-exclusion.md`.

### Slice COLL-01-NOCLIP: direct shape translation

- Inventory ID(s): `INV-COLLISION`, `INV-STATE`.
- Exact behavior boundary and enclosing guards/order checked: `Entity.move(DDD)V` no-clip branch and `setPositionFromShape()V`; collision queries and callbacks are bypassed by this branch.
- A evidence: `Entity.move(DDD)V` lines 371-375 moves the existing box by `(dx,dy,dz)` and recomputes entity coordinates; `setPositionFromShape()V` lines 638-642 sets X/Z to the box center and Y to `minY`; `Box.moved(DDD)LBox;` lines 89-91 adds the same deltas independently to all six bounds. Entity.java SHA-256 `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`; Box.java SHA-256 `8fc7bb57b1132afc7ae9539c30ff9c2585f0e36c2b0e59c60946cb1e3cee9eea`.
- B evidence: `Entity.move(DDD)V` lines 441-445 performs the same moved-box call and coordinate recomputation; `setPositionFromShape()V` lines 722-727 uses the same center/minY expressions; `Box.moved(DDD)LBox;` lines 124-126 adds the deltas in the same bound order. Entity.java SHA-256 `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`; Box.java SHA-256 `055d57e1e555cc378fd1d7ea14d57036190ae335238175d284b969f439abe198`.
- State producers/writers -> consumers/readers: caller-provided requested displacement -> Box bounds -> recomputed entity x/y/z. `noClip` guard bypasses the ordinary collision provider and all subsequent clipping/callback code.
- Parent slices / dependencies / closure evidence: paired `move` branch, coordinate recomputation helper and Box translation body checked. No block/entity collision shape dependency is reachable from this branch.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for an entity with `noClip == true`, both versions translate all box bounds by the requested double deltas, then derive x/z from the same midpoint sums divided by `2.0` and y from the minimum bound. B caches the box in a local before those expressions; the floating-point operation order for each coordinate is unchanged.
- Finding IDs or checked absence/replacement path: none; ordinary clipped movement remains in COLL-01.

### Slice COLL-01-SNEAK-EDGE: sneaking support-edge reduction

- Inventory ID(s): `INV-COLLISION`, `INV-STATE`.
- Exact behavior boundary and enclosing guards/order checked: `onGround && isSneaking() && this instanceof PlayerEntity`; requested X is repeatedly reduced first, then Z, then the diagonal pair, probing support at one block below the moved shape; each component is zeroed when within the inclusive/exclusive `0.05` band or reduced by `0.05` toward zero.
- A evidence: `Entity.move(DDD)V` lines 390-437 preserves original horizontal deltas in `g/i`, applies the same guard, initializes `j = 0.05`, and tests moved shapes `(dx,-1,0)`, `(0,-1,dz)`, then `(dx,-1,dz)` in that order. Entity.java SHA-256 `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`; A `Box.moved(DDD)` evidence is in COLL-01-NOCLIP.
- B evidence: `Entity.move(DDD)V` lines 460-507 retains the same guard, literal, support probes, loop order, delta comparisons, `0.05` reductions and original-delta assignments. Entity.java SHA-256 `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`; B `Box.moved(DDD)` evidence is in COLL-01-NOCLIP.
- State producers/writers -> consumers/readers: local player on-ground/sneaking state + requested horizontal delta + collision/support query -> reduced dx/dz -> later collision collection and Y/X/Z clipping.
- Parent slices / dependencies / closure evidence: both method bodies and the support-probe boxes were paired. Shape providers and the entity candidate difference are not presumed equal; those are separate WORLD/COLL slices.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): under the same initial player flags, requested deltas and identical answers to the paired support queries, A and B apply the same guard and sequence of X, Z, and diagonal reductions using unchanged double comparisons and `0.05` arithmetic. Collision query results may differ because providers or entity-candidate filters differ; only this edge-reduction algorithm is closed.
- Finding IDs or checked absence/replacement path: none; downstream collision-list deltas are tracked separately.

### Slice COLL-01-AXES: ordered Y/X/Z clipping math

- Inventory ID(s): `INV-COLLISION`, `INV-STATE`.
- Exact behavior boundary and enclosing guards/order checked: normal non-no-clip `Entity.move(DDD)V` after collection of the ordered collision list; vertical clipping first, then X, then Z, including replacement of requested delta with each clipping result.
- A evidence: `Entity.move(DDD)V` lines 439-459 iterates the same `List<Box>` in order for `intersectY`, moves shape by clipped Y, computes `bl2`, then iterates for X and Z with a shape move between axes; Entity.java SHA-256 `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`. A `Box.intersectX/Y/Z(Box,double)D` lines 93-151 has strict overlap predicates and signed-delta clamps; Box.java SHA-256 `8fc7bb57b1132afc7ae9539c30ff9c2585f0e36c2b0e59c60946cb1e3cee9eea`.
- B evidence: `Entity.move(DDD)V` lines 509-532 uses indexed iteration over the same list in order for Y, then X, then Z; it moves the shape and computes `bl2` at the same stage boundaries. B `Box.intersectX/Y/Z(Box,double)D` lines 139-197 preserves A's overlap conditions, signed tests, subtraction order, strict comparisons and return values; Entity.java SHA-256 `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`; Box.java SHA-256 `055d57e1e555cc378fd1d7ea14d57036190ae335238175d284b969f439abe198`.
- State producers/writers -> consumers/readers: ordered collision list -> per-axis clipped delta -> moved box -> next-axis query box. Delta arithmetic and axis order are downstream of the list's candidate/provider inventory.
- Parent slices / dependencies / closure evidence: paired `Box.intersectX/Y/Z` bodies and `Entity.move` axis loop checked. COLL-01-VEHICLE records a player-reachable A/B collision-list difference; collision shape providers and list assembly are not treated as equivalent here.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): when supplied the same ordered collision boxes and requested deltas, both versions apply Y then X then Z, pass each current shape and current delta to the same strict per-axis clipping formulas, and move the shape after each axis. B changes enhanced-for iteration to indexed `List.get` iteration without changing list order or mutating the list during these loops. This disposition covers clipping math/order only, not equality of the collision list or final player displacement.
- Finding IDs or checked absence/replacement path: none; collision-list differences are tracked separately in COLL-01-VEHICLE and provider rows.

### Slice COLL-01-STEP: step-up candidate selection

- Inventory ID(s): `INV-COLLISION`, `INV-STATE`.
- Exact behavior boundary and enclosing guards/order checked: positive `stepHeight`, ground/support condition, and horizontal clipping guard; two candidate boxes each resolve Y then X then Z against the step collision list; squared horizontal progress chooses candidate one only when strictly greater; a final Y clip is applied before restoring the original move when its squared horizontal distance is greater than or equal.
- A evidence: `Entity.move(DDD)V` lines 460-539; source collision-list order feeds both candidates. A computes first candidate `(o,p)`, second `(r,s)`, compares `o * o + p * p > r * r + s * s`, otherwise selects candidate two, then uses `k * k + m * m >= dx * dx + dz * dz` to restore the original box and delta. Entity.java SHA-256 `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`.
- B evidence: `Entity.move(DDD)V` lines 533-620 performs the same candidate ordering with `(w,z)` and `(af,ai)`, same strict `al > am` choice, same final `q * q + s * s >= dx * dx + dz * dz` restoration, and the same Y-clip order. Entity.java SHA-256 `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`.
- State producers/writers -> consumers/readers: requested dx/dy/dz and stepHeight plus on-ground/support/collision results -> two candidate clipped boxes -> horizontal squared-distance comparison -> chosen shape and returned dx/dy/dz.
- Parent slices / dependencies / closure evidence: step candidate bodies and their enclosing entry guard are paired; ordered-axis clip math is separately closed in COLL-01-AXES. Exact list contents, box providers and support flags remain separate dependencies.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): when both versions receive the same ordered collision boxes, same initial shape and same movement/step state, the two step candidates use Y/X/Z clipping in the same sequence, with identical squared-distance ordering and tie behavior. B caches intermediate boxes in locals and indexes the list; A repeatedly assigns the candidate box and uses enhanced-for loops. These representation changes do not change the compared candidate expressions or branch ordering. Collision-list equality and the player displacement remain unclaimed.
- Finding IDs or checked absence/replacement path: none; collision-list differences are tracked in COLL-01-VEHICLE and provider rows.

### Slice COLL-01-FLAGS: collision flags and horizontal velocity cancellation

- Inventory ID(s): `INV-COLLISION`, `INV-STATE`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: after selected clipped shape and `setPositionFromShape()V`, assignment of horizontal/vertical collision flags and on-ground state, aggregate collision flag, and X/Z velocity zeroing when the resolved component differs from the requested component.
- A evidence: `Entity.move(DDD)V` lines 544-572 writes `collidingHorizontally = g != dx || i != dz`, `collidingVertically = h != dy`, `onGround = collidingVertically && h < 0.0`, and `colliding = collidingHorizontally || collidingVertically`; it clears X/Z velocity under `g != dx` / `i != dz`. Entity.java SHA-256 `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`.
- B evidence: `Entity.move(DDD)V` lines 625-654 has the same assignments and strict component comparisons before the vertical block callback. Entity.java SHA-256 `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`.
- State producers/writers -> consumers/readers: selected shape and requested/resolved deltas -> collision flags, on-ground state and horizontal velocity writes; those flags are later read by jump/travel/player gates.
- Parent slices / dependencies / closure evidence: flag and X/Z velocity statements paired; no-clip branch is in COLL-01-NOCLIP and clipping deltas are in COLL-01-AXES/STEP. Vertical `beforeCollision` callbacks and support/block selection remain open; fall-distance/damage simulation is excluded.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for the same requested and resolved deltas, both versions write the same flags from the same comparisons in the same order and zero X/Z velocity under the same inequality guards. The finding does not extend to how different collision lists may produce different resolved deltas.
- Finding IDs or checked absence/replacement path: none; provider/candidate list differences are separate.

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

### Slice WORLD-SLAB-SHAPES: historical single/double slab collision geometry

- Inventory ID(s): `INV-COLLISION`, `INV-WORLD-MOVEMENT`.
- Exact behavior boundary and enclosing guards/order checked: world block-collision dispatch -> A `SlabBlock.addCollisions()`/`updateShape()` -> A `Block.getCollisionShape()` versus B `Block.addCollisions()` -> `BlockState.getCollisionShape()` -> `SlabBlock.getShape()`; single/double plus top/bottom bounds for the shared stone, wooden and red-sandstone slab registrations.
- A evidence: `SlabBlock.java` lines 22-28, 39-67 writes full cube for `isDouble()`, top half for `HALF.TOP`, otherwise bottom half; `Block.java` lines 292-299 stores the shape literals in double min/max fields and 339-350 constructs the world-space collision box. Hashes: SlabBlock `35be26d8419f707ca36f1b3fc38676624193b5635250c57347cd2cd6d8357004`; Block `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528`; Blocks registry `1da2d85406ed991e8f6bc1f420babf0bd43598478f347c6a4dc9fea322ed0695`.
- B evidence: `SlabBlock.java` lines 19-20, 33-40 returns full, top-half or bottom-half local shape from `isDouble()` and `HALF`; `Block.java` lines 337-354 obtains the state collision shape and translates it by block position; `StateDefinition.java` lines 359-360 dispatches the state to its owning block. Hashes: SlabBlock `9c3d373497f5602d19bf8eb0aa59d670854ef209e8f73f946d5ba05496add00b`; Block `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6`; StateDefinition `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493`; Blocks registry `dc561d17e9e12767774d9ef01057f0c981e5a9bb666e2d11cba3517062cc8dd7`.
- Variant and registration path: A/B `Blocks` declare and resolve the same `double_stone_slab`, `stone_slab`, `double_wooden_slab`, `wooden_slab`, `double_stone_slab2`, and `stone_slab2` registrations. Corresponding A/B `SingleStoneSlabBlock` hash `3b3808a68671d1f559f20ac6cc71a367223d50e9c646dbf470f2da3a503cca98`; `DoubleStoneSlabBlock` `134f7fafbbc635b8367f1fbf0f0375e0eb95a387bc1d8667ef3c9160e899121f`; `SingleWoodenSlabBlock` `367dbdd8ff807d45734d4074ac236dcdd8f7ceb75324eb4e9dad0ac56b11d4d9`; `DoubleWoodenSlabBlock` `7e00e9517411d8e8a6ce48a79203ece8bbb64e10a9d1070c597a55efb466e21a`; `SingleRedSandstoneSlabBlock` `80782d5bb0d60bc09e895efa4f09be5b321764f9e9f2dd7d2ad68ab192bb3b8a`; `DoubleRedSandstoneSlabBlock` `71df9e432c231b3c6d0892a58957bbad1b2ece2daa3526a1d330b9944824b203`. Each listed variant source is byte-identical across A/B and selects the same `isDouble()` result. B's `purpur_slab` has no A registration and is excluded as a modern-only block.
- State producers/writers -> consumers/readers: slab block variant and `HALF` state -> local slab shape -> block-position translation and intersection filter -> ordered collision list -> player `Entity.move(DDD)V` clipping.
- Parent slices / dependencies / closure evidence: both-side historical registrations, slab shape producer, generic block collision dispatch, state accessor and double/single subclass predicates checked. Neighbor-state shape providers, block callbacks and other block families remain open.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for a stable world state in any shared stone/wood/red-sandstone slab block, both implementations supply the same full cube for double slabs, `[0,0.5]` or `[0.5,1]` vertical bounds for single bottom/top slabs, and full `[0,1]` horizontal bounds. All compared endpoints are exactly representable (`0`, `0.5`, `1`); both versions translate them using double-valued bounds plus integer block coordinates before intersecting the player query. This covers these slab providers only.
- Finding IDs or checked absence/replacement path: none; B-only purpur slab is excluded by its absent A registration.

### Slice WORLD-SNOW-SHAPE: snow-layer collision height

- Inventory ID(s): `INV-COLLISION`, `INV-WORLD-MOVEMENT`.
- Exact behavior boundary and enclosing guards/order checked: shared `snow_layer` registration; A `SnowLayerBlock.getCollisionShape(World,BlockPos,BlockState)` and B `getCollisionShape(BlockState,World,BlockPos)` read `LAYERS`, derive collision maximum Y using `(layers - 1) * 0.125F`, and emit/translocate a full-horizontal box.
- A evidence: `SnowLayerBlock.java` lines 23, 39-46, 64-71: `LAYERS` ranges 1-8; collision `i = layers - 1`, `f = 0.125F`, world-space max Y is `pos.getY() + i * f`; default/update geometry preserves full X/Z bounds. SHA-256 `d922f1f9a8d6f543959835e8b21e800ecf5cd9c7c60816b9152d873ca132a3ed`.
- B evidence: `SnowLayerBlock.java` lines 24-35 and 44-66: same layer range, local shape table spans `layers * 0.125`, collision uses the same `i = layers - 1` and `f = 0.125F`, then returns that height with X/Z bounds from the local shape; generic block collision dispatch translates the local box by block position. SHA-256 `3f60db09c66fb07439869254db883eed5da679a17e369e019f95ba97d427b2a5`.
- Registration and state provider: both `Blocks.java` files declare and resolve `snow_layer` (A lines 92/300, B 96/327); source hashes are the A/B registry hashes cited in WORLD-SLAB-SHAPES. B's `StateDefinition#getCollisionShape(World,BlockPos)` dispatches to the state-owning block (SHA-256 `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493`).
- State producers/writers -> consumers/readers: `LAYERS` block state -> layer-minus-one float height -> block-space collision Box -> `World.getCollisions` intersection and ordered list -> player `Entity.move(DDD)V` clipping.
- Parent slices / dependencies / closure evidence: exact shared registry, layers property, both collision methods and B state-to-block dispatch checked. The comparison does not close all SnowLayer placement/tick semantics; those are unrelated block-state lifecycle and consumer work.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for each stable shared snow-layer state with layer count 1 through 8, the collision top is `(layers - 1) * 0.125F` in both sources and the X/Z extent is the full block. A constructs world-space Y directly; B translates a local state shape with the same double block coordinate addition. The B local shape table uses `layers * 0.125` as its visual/default shape, but `getCollisionShape` explicitly uses `layers - 1`, matching A's collision formula, including the zero-height layer-1 box.
- Finding IDs or checked absence/replacement path: none.

### Slice WORLD-TRAPDOOR-SHAPES: trapdoor collision geometry

- Inventory ID(s): `INV-COLLISION`, `INV-WORLD-MOVEMENT`.
- Exact behavior boundary and enclosing guards/order checked: shared wood/iron trapdoor shape for all `OPEN`, `HALF`, and horizontal `FACING` combinations; A collision query calls `updateShape(world,pos)` and generic `Block.getCollisionShape`, B gets a state-local shape and generic state-aware collision dispatch translates it.
- A evidence: `TrapdoorBlock.java` lines 23-25, 57-104 sets full-footprint bottom/top plates for closed states and facing-specific `0.1875F` edge plates for open states, with `OPEN` taking precedence over `HALF`; SHA-256 `e00c4fdfa234ac5cf265816fbbe088709668cd5a44eb537a1f391ce2f5f795ba`.
- B evidence: `TrapdoorBlock.java` lines 24-32 and 43-66 returns the same six local boxes: bottom `[0,0.1875]`, top `[0.8125,1]`, north/south/east/west strips using the same `0.1875`/`0.8125` coordinates; SHA-256 `bbfbeac22de7e72b55736a35c29c10f372df1d846dd01cd16d7ee2a0913b2339`.
- Registration and geometry dispatch: both registries contain `trapdoor` and `iron_trapdoor` (A `Blocks.java` lines 114/185 and 322/393; B lines 118/189 and 349/420). Registry source hashes are A/B values cited in WORLD-SLAB-SHAPES. A generic block-world translation is `Block#getCollisionShape(World,BlockPos,BlockState)` at lines 346-350, hash `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528`; B uses `Block.addCollisions` and state dispatch at lines 337-354, `Block.java` hash `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6`; `StateDefinition#getCollisionShape` hash is recorded above.
- State producers/writers -> consumers/readers: registered trapdoor state -> conditional local collision bounds -> world-space block box -> collision intersection/list -> player movement clipping; `WORLD-01` separately inventories the open-trapdoor climbing predicate.
- Parent slices / dependencies / closure evidence: all paired shape state branches, shape extents and both registrations checked. This does not close trapdoor neighbor updates, placement/opening state lifecycle, or the ladder provider/climb consumer.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for the same stable registered trapdoor state, both sources select the same collision bounds, including the case where open facing replaces the closed top/bottom plate. The A shape setter's values are float literals stored in double bounds; B's constants are exact doubles for the same binary fractions. Translation and intersection remain the paired generic block path.
- Finding IDs or checked absence/replacement path: none; climb-gate comparison remains WORLD-01.

### Slice WORLD-LADDER-SHAPES: ladder collision geometry

- Inventory ID(s): `INV-COLLISION`, `INV-WORLD-MOVEMENT`.
- Exact behavior boundary and enclosing guards/order checked: shared `ladder` registration and collision box for each horizontal `FACING`; A's collision method refreshes shared block bounds from the world state, while B returns a local state shape.
- A evidence: `LadderBlock.java` lines 21, 26-28, 38-56 sets a `0.125F` edge strip for each horizontal direction and translates through inherited `Block.getCollisionShape`; SHA-256 `c13b123f8fd7427d7f646da3b789eb97d4e54aa2fe2860f66cf4fd1302865106`.
- B evidence: `LadderBlock.java` lines 20-40 returns the equivalent directional constants: north z `[0.8125,1]`, south z `[0,0.1875]`, west x `[0.8125,1]`, east x `[0,0.1875]`; SHA-256 `f411d494a4f8c87b2b23d182527713d3b0c2fad4da329b82b84ea301a9d65fcb`.
- Registration and geometry dispatch: both `Blocks` registries contain `ladder`; the A/B registry hashes are cited in WORLD-SLAB-SHAPES. A uses the generic world-space collision box from `Block.getCollisionShape`; B uses state-based `Block.addCollisions`/`BlockState.getCollisionShape` as recorded in WORLD-TRAPDOOR-SHAPES.
- State producers/writers -> consumers/readers: ladder `FACING` -> axis/side-specific box -> collision intersection and movement clip; ladder climb predicate is separately covered by WORLD-01.
- Parent slices / dependencies / closure evidence: all four horizontal facing branches and collision dispatch checked. Placement/neighbor support state and ladder movement/climbing consumers remain separate.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for each stable registered horizontal ladder state, A and B produce the same single 1/8-block-thick wall strip in the same direction and translate it with the same world block coordinates. The move to state-local constant shapes changes storage/dispatch but not the collision bounds.
- Finding IDs or checked absence/replacement path: none; climbing behavior remains WORLD-01.

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
- `DEP-WORLD-02-BOAT-PHYSICS`: closed by scope disposition; input-controlled boat velocity, buoyancy, water interaction, collision and vehicle trajectory are out of scope. Direct player passenger position/orientation callback is retained as `WORLD-02` and recorded in its finding.
- `DEP-COLL-01-VEHICLE-EXCLUSION`: source behavior is corroborated by revised A/B mapped-artifact bytecode and the reachable mounted-player caller; canonical original mapped-JAR equivalence remains unresolved under the run's artifact caveat. Downstream axis clipping consequences remain open in COLL-01.
- All remaining `TICK-*`, `STATE-*`, `COLL-*`, `WORLD-*`, `MOD-*`, and `EXT-*` inventories remain open.

## Finding index

- [TICK-01 â€” Sprint timeout](findings/TICK-01-sprint-timeout.md)
- [TICK-02 â€” Flight sneak input rescaling](findings/TICK-02-flight-sneak-input-rescaling.md)
- [STATE-01 â€” Velocity zero threshold](findings/STATE-01-velocity-zero-threshold.md)
- [TICK-07 â€” Client-side player push](findings/TICK-07-client-player-push.md)
- [STATE-03 â€” Sneak collision height](findings/STATE-03-sneak-collision-height.md)
- [WORLD-01 â€” Trapdoor ladder climbing](findings/WORLD-01-trapdoor-ladder-climbing.md)
- [WORLD-03 - End Portal Frame player ejection](findings/WORLD-03-end-portal-frame-player-ejection.md)
- [WORLD-02 — Boat rider transform](findings/WORLD-02-boat-rider-transform.md)
- [COLL-01 — Same-vehicle collision exclusion](findings/COLL-01-vehicle-collision-exclusion.md)
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
- Next bounded comparison: split remaining `COLL-01` normal movement into named axis clipping / step selection / support-edge / callbacks, then close downstream TICK-06 velocity-cutoff/travel dependencies. Also continue player state/equipment and external influence inventories; WORLD-02 covers only direct boat passenger writes and does not close other mount/authority paths. Resolve canonical artifact identity if further bytecode-level claims depend on the unavailable original mapped JARs. The state-solid provider subinventory is closed for paired A historical block classes: shared cube overrides were compared; A/B signal-source overrides and B state-material lookup were compared; Air and modern-only block cases were dispositioned; the End Portal Frame difference is recorded in WORLD-03. Entry paths: A `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` lines 292-346 and `Block.java` lines 237-239; B same-named local-player file lines 349-403, `block/state/StateDefinition.java` lines 293-300, and `Block.java` lines 214-216. Shared sources remain read-only. TICK-06 source hashes: A `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`, B `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.
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
