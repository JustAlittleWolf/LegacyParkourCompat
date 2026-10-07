# Discovery: Java 1.8.9 to 1.9.4

- Run status: active
- Scope: source-only direct player movement; A = 1.8.9; B = 1.9.4. No runtime implementation.
- Repository revision and start date: base `002137b227676caea77f6832b9f4c8d0b6200bff`; 2026-10-07 Europe/Vienna.
- Selected naming namespace, CLI mode per side and alignment evidence: Ornithe Feather (`ornithe-feather`) on both sides; release-specific Feather Gen 2 build 2 source trees admitted. Derived mapped-artifact identity is pending canonical repair.
- Source preparation owner / command / log / readiness marker: campaign source owner (name not supplied); exact invocation/log/options requested and pending. Markers: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather.ready.json` and corresponding `1.9.4` marker.
- Toolchain/decompiler/remapper versions and options: marker reports Vineflower, Java runtime 25; source-base catalog pins Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1. Exact runtime versions/options await source-owner confirmation (`DEP-PROVENANCE`).
- Discovery author(s): source-campaign worker for 1.8.9 -> 1.9.4.
- Independent reviewer: not assigned; must differ from discovery author.

## Artifact manifest

Paths are repository-relative. The shared source/artifact trees are read-only inputs. At initial admission, both exact IDs, ready JSONs, source/artifact sidecar hashes, every listed source, and all 37 listed artifacts per release were verified. A subsequent source-owner integrity notice reports that a reproducibility rerun replaced shared-cache derived mapped JARs while preserving source-file/raw-input hashes. The prior artifact verification is now stale; the source owner and ops are repairing immutability/provenance. No artifact marker was rewritten, no mismatch was waived, and no independent decompilation was performed. `DEP-ARTIFACT-IMMUTABILITY` blocks report freeze and finding acceptance pending canonical repair and fresh verification.

### A — 1.8.9

- Requested/resolved/version metadata ID: `1.8.9` / `1.8.9` / `1.8.9`.
- Source root: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/`; 1,612 files, 8,248,472 bytes.
- Client jar `build/movement-campaign-2026-10-07/artifacts/1.8.9/client.jar`: SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`.
- Mapped jar `build/movement-campaign-2026-10-07/artifacts/1.8.9/client-ornithe-feather.jar`: SHA-256 `e36a366fd30d0adda0a803a4956782bab1f67cd644e396d4db6a1cd885548c09`.
- Mapping `feather-gen2-1.8.9+build.2`; merged jar `build/movement-campaign-2026-10-07/artifacts/yarn/feather-gen2-1.8.9+build.2-mergedv2.jar` SHA-256 `303c4530c79a593b828bd778a97d3577e67f99d6a2c50760e5f9bec6fb32a9da`; mapping file `build/movement-campaign-2026-10-07/artifacts/yarn/feather-gen2-1.8.9+build.2.tiny` SHA-256 `de2023ea2cca9921402fbfcfe6e475f41da4932ea6dbc609e35c505b76a32c63`.
- Source manifest `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather.sources.sha256`: SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1,612/1,612 file hashes match.
- Artifact manifest `build/movement-campaign-2026-10-07/ready/1.8.9/artifacts.sha256`: initial SHA-256 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`; 37/37 matched at initial admission only. Current derived-JAR integrity is unverified pending canonical repair.
- Diagnostics `build/movement-campaign-2026-10-07/ready/1.8.9/movement-diagnostics.txt`: SHA-256 `62dc9b445bec2f62b6dac9da501e875377636d08682891891212aea464999d28`; exact release succeeded, required entity/living/player/local-player files exist, no damaged movement body/error reported.

### B — 1.9.4

- Requested/resolved/version metadata ID: `1.9.4` / `1.9.4` / `1.9.4`.
- Source root: `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/`; 1,819 files, 9,320,183 bytes.
- Client jar `build/movement-campaign-2026-10-07/artifacts/1.9.4/client.jar`: SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`.
- Mapped jar `build/movement-campaign-2026-10-07/artifacts/1.9.4/client-ornithe-feather.jar`: SHA-256 `0df10c862f7fd4848d08597a76c0cf02cec5a9d9886975a57230a033cfb86b3a`.
- Mapping `feather-gen2-1.9.4+build.2`; merged jar `build/movement-campaign-2026-10-07/artifacts/yarn/feather-gen2-1.9.4+build.2-mergedv2.jar` SHA-256 `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125`; mapping file `build/movement-campaign-2026-10-07/artifacts/yarn/feather-gen2-1.9.4+build.2.tiny` SHA-256 `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027`.
- Source manifest `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather.sources.sha256`: SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; 1,819/1,819 file hashes match.
- Artifact manifest `build/movement-campaign-2026-10-07/ready/1.9.4/artifacts.sha256`: initial SHA-256 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`; 37/37 matched at initial admission only. Current derived-JAR integrity is unverified pending canonical repair.
- Diagnostics `build/movement-campaign-2026-10-07/ready/1.9.4/movement-diagnostics.txt`: SHA-256 `51bd42a633c04931814ab78a841cedd3bf87460e04f7877676599b51b59bbb1d`; exact release succeeded, required entity/living/player/local-player files exist, no damaged movement body/error reported.

Resource entry hashes will be added for data-backed slices. No test, game, TAS, server, Docker, build, or runtime validation was performed.

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
- `INV-WORLD-MOVEMENT` status=pending; slice_ids=WORLD-01, WORLD-02, COLL-02; evidence=world states, neighboring blocks, fluids, and vehicle path in progress
- `INV-MODIFIERS` status=pending; slice_ids=TICK-03, MOD-01; evidence=equipment/effect/attribute producers and consumers in progress
- `INV-EXTERNAL` status=pending; slice_ids=TICK-03 through TICK-07, WORLD-02, EXT-01, MOD-01; evidence=external velocity/position/vehicle writers in progress
- `INV-EXCLUSIONS` status=complete; slice_ids=scope boundary; evidence=health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement explicitly excluded

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
- Exact behavior boundary and enclosing guards/order checked: keyboard sneak input scaling and local flight branch before `moveRelative`.
- A evidence: `LocalClientPlayerEntity.java` `mobTick()V`, lines 596-603; SHA-256 `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`.
- B evidence: `LocalClientPlayerEntity.java` `mobTick()V`, lines 715-725; SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.
- State producers/writers -> consumers/readers: keyboard scales sideways/forward values by `0.3F`; B divides both by `0.3` while flying and sneaking before travel; vertical fly-speed update remains guarded by sneak/jump.
- Parent slices / dependencies / closure evidence: `KeyboardInput.tick()` and `PlayerEntity.moveRelative(FF)V`.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B cancels crouch input scaling for horizontal creative flight; A retains it.
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

### Slice TICK-06: local pre-travel entity push

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: four local-player `pushAwayFrom` calls before sprint/jump/flight/riding gates and `super.mobTick()`.
- A evidence: `LocalClientPlayerEntity.mobTick()V` lines 535-560; `Entity.push()V` lines 947-973; hashes pending.
- B evidence: `LocalClientPlayerEntity.mobTick()V` lines 645-681; `Entity.push()V` lines 1061-1088; hashes pending.
- State producers/writers -> consumers/readers: nearby entity candidates and offsets -> push velocity writer -> local player pre-travel velocity, before superclass cutoff/travel.
- Parent slices / dependencies / closure evidence: candidate predicates and call order relative to cutoffs/travel.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): direct pre-travel player velocity writes are reachable; candidate and formula comparison pending.
- Finding IDs or checked absence/replacement path: pending.

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

### Slice STATE-03: player pose and dimensions

- Inventory ID(s): `INV-TICK`, `INV-STATE`, `INV-COLLISION`, `INV-EXTERNAL`.
- Exact behavior boundary and enclosing guards/order checked: all player dimensions/pose writers, resize timing, eye-height consumers and collision-fit query.
- A evidence: `PlayerEntity#getEyeHeight()F`, lines 1640-1650, and sleep/wake size writes at lines 1108 and 1163; source-wide checked absence of any sneaking-conditioned player size writer. SHA-256 `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`.
- B evidence: `PlayerEntity.tick()V` calls `updatePlayerPose()` at end; sneaking/standing dimensions and collision-free resize at lines 285-308; fall-flight/sleep pose cases are excluded; SHA-256 `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`.
- State producers/writers -> consumers/readers: pose flags -> candidate dimensions -> collision query -> `setSize`/box -> next movement and eye-height probes.
- Parent slices / dependencies / closure evidence: separately close sneak support-edge probe, size writers/queries, pose reset timing and eye-height consumers.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): B adds tick-end dynamic pose sizing for a sneaking player with a collision-free 1.65-high candidate box; A keeps standing dimensions. Elytra/sleep cases are not included in the historical finding.
- Finding IDs or checked absence/replacement path: `findings/STATE-03-sneak-collision-height.md`.

### Slice COLL-02: pane and iron-bar collision geometry

- Inventory ID(s): `INV-COLLISION`, `INV-WORLD-MOVEMENT`.
- Exact behavior boundary and enclosing guards/order checked: pane/bar registrations and subclasses -> state/neighbor resolution -> collision union -> player collision query.
- A evidence: `PaneBlock.addCollisions()` and `updateShape()`; provider/subclass list and hashes pending.
- B evidence: `PaneBlock.addCollisions()`, `getShape()`, `resolveVirtualProperties()`, `shouldConnectTo()`; hashes pending.
- State producers/writers -> consumers/readers: neighbor default-state shape -> `isOpaque()` (A) or cube test (B) -> pane connections -> collision geometry.
- Parent slices / dependencies / closure evidence: historical pane/bar neighbors, connection combinations, registrations, B-only block exclusions, and `BlockState` path into `Entity.move()`.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): neighbor-connect predicate changes from opacity to default-state cube-ness; registration reachability and union geometry remain open.
- Finding IDs or checked absence/replacement path: pending.

### Slice SCOPE-01: excluded hunger/regeneration producers

- Inventory ID(s): `INV-EXCLUSIONS`, `INV-STATE`.
- Exact behavior boundary and enclosing guards/order checked: sprint-gate read of current food state versus excluded natural-regeneration/hunger/exhaustion producers.
- A evidence: `LocalClientPlayerEntity.mobTick()V`, lines 550-550, reads `getHungerManager().getFoodLevel()` solely in the sprint eligibility predicate; SHA-256 `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`.
- B evidence: `LocalClientPlayerEntity.mobTick()V`, lines 661-661, reads `getHungerManager().getFoodLevel()` solely in the sprint eligibility predicate; SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.
- State producers/writers -> consumers/readers: vanilla food state may be read by sprint predicate; natural regeneration/hunger/exhaustion simulation is outside this campaign.
- Parent slices / dependencies / closure evidence: no excluded producer appears in finding index; direct state reads only define a movement precondition.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): health, regeneration, hunger, food, saturation, exhaustion, damage and combat simulation are out of scope; excluded producers are not movement findings.
- Finding IDs or checked absence/replacement path: excluded by scope; no implementation finding.

## Dependency queue and blockers

- `DEP-PROVENANCE`: source owner; exact preparation command/log, pinned/runtime decompiler/remapper/mapping-io versions/options missing.
- `DEP-ARTIFACT-IMMUTABILITY`: source owner and ops; mapped JARs changed after initial verification; do not freeze or accept findings until canonical repair and fresh verification.
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

## Incremental finding snapshot log

### SNAP-STATE-01-01 — blocked pending artifact integrity and dependency closure

- Finding ID: `STATE-01` (`findings/STATE-01-velocity-zero-threshold.md`).
- Evidence/snapshot commit: `f5eca2d932b7544c4eebaa13864491a4e40cba95`.
- Finding-file SHA-256 at that commit: `51506e004c8ae129cf45db365f8ee5bfea0f54c8ec5170f99250003e8f0115e1`.
- Exact source identity: A source manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`, cited `LivingEntity.java` SHA-256 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`; B source manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`, cited `LivingEntity.java` SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`. Source-owner notice says raw/source hashes are unchanged.
- Exact artifact identity at initial verification: A artifact manifest `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`; B artifact manifest `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`; mapped JAR hashes are recorded in Artifact manifest above. **These artifact identities are stale for snapshot acceptance** because the shared-cache rerun replaced derived mapped JARs. Canonical repair and fresh verification are pending; no artifact mismatch is waived.
- Finding scope and implementation boundary: the only candidate code boundary is the three strict per-axis `<` comparisons in `LivingEntity#mobTick()V` before jump dispatch/travel: A `0.005`, B `0.003`. Any implementation must change only the historical cutoff at the correct resolved release; first introduction release and exact release-version activation boundary are not established by this pair alone. This is not authorization or an implementation handoff.
- Finding-specific dependencies: `DEP-STATE-01-PRODUCER-CONSUMER` remains open for complete player-path, input velocity-writer, jump/travel consumer, and external correction closure. Do not claim it closed.
- Blind reviewer and decision: not assigned; no acceptance decision.
- Timestamp: 2026-10-07 Europe/Vienna.
- Pair run status/commit at handoff: `active`; evidence commit above. `pair complete: no`.
- Implementation handoff: `blocked` — mapped-artifact integrity is under canonical repair, finding-specific producer/consumer closure remains open, and no independent snapshot reviewer has accepted it.

## Resume checkpoint

- Last completed slice: source roots admitted; source-backed movement candidates and explicit scope exclusions recorded; initial artifact verification invalidated by the owner integrity notice.
- Next bounded slice and exact files/members/body ranges: close active-item and pre-travel push writers; compare input/packet authority boundaries; finish modifier/equipment and collision-provider inventories; then revisit cutoff-specific dependency closure after canonical artifact repair.
- Outstanding dependencies and owners: `DEP-PROVENANCE` and `DEP-ARTIFACT-IMMUTABILITY` source-preparation/ops; `DEP-AUDITOR` coordinator; `DEP-STATE-01-PRODUCER-CONSUMER` source worker; remaining coverage slices.
- Assumptions requiring verification: exact generation invocation/tool runtime; no first-version claim inside the interval; complete resource/provider inventory remains open.

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
- Open dependencies: `DEP-PROVENANCE`, `DEP-ARTIFACT-IMMUTABILITY`, `DEP-STATE-01-PRODUCER-CONSUMER`, `DEP-AUDITOR`, and all pending/in-progress slices.
- Unresolved gaps/limits: comprehensive audit in progress; no equivalence closure claimed.
- Evidence/hash/correspondence audit: source hashes were checked against the admitted source manifests and cited files; derived artifact hashes are stale under the integrity notice and await canonical repair.
- Blind freeze: pending.
- Implementation reconciliation: pending.
- Independent audit: pending.
- Runtime validation: not performed (separate workflow; runtime not authorized).
