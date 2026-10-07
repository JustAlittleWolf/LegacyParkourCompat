# Discovery: 1.20.2 to 1.20.4

- Status: active
- Scope: direct client player movement and the complete reachable vanilla movement call graph. Older A = 1.20.2; newer B = 1.20.4. This is a source-only discovery track; runtime Java implementation is not authorized here.
- Scope exclusions: health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations (including indirect sprint-gate effects), and non-player movement. Vanilla values may be read as movement inputs; excluded producer systems are not emulated. Modern-only features remain modern-only.
- Evidence constraints: exact-version decompiled sources and bytecode only. No Minecraft Wiki/MCPK browsing, release-note mechanics, old implementation, old patch classes, or wiki-audit outputs before freeze. Prior discovery reports are allowed only as navigation; none were used for this checkpoint.
- Repository revision and start date: source baseline `002137b227676caea77f6832b9f4c8d0b6200bff` (`main` at worktree creation), 2026-10-07. Workflow-hardening commits cherry-picked: `40c34f5` and `2422192`.
- Worktree / branch: `C:/Users/Wolfi/.codex/worktrees/movement-source-1-20-2-1-20-4/LegacyParkourCompat`; `feat/source-discovery-movement-source-1-20-2-1-20-4`.
- Selected naming namespace, CLI mode per side and alignment evidence: intended `mojmap` / official Mojang names on both sides; alignment remains unverified until both readiness records and mappings are checked.
- Source preparation owner / command / log / readiness marker: source owner only. Requested exact 1.20.2 and 1.20.4 Mojmap readiness records under `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07`; neither marker has been published at this checkpoint. No decompile command run by this worker.
- Toolchain/decompiler/remapper versions and options: pending validated readiness records.
- Discovery author(s): this task's source worker.
- Independent reviewer (must differ from discovery authors): not yet assigned.

## Artifact manifest

- A (1.20.2): exact resolved ID, source root, original client jar identity/publisher hash/SHA-256, Mojmap CLI mode, official mapping coordinate/path/hash, remapped jar hash, source manifest/hash, diagnostics, and tool versions: pending validated readiness record.
- B (1.20.4): exact resolved ID, source root, original client jar identity/publisher hash/SHA-256, Mojmap CLI mode, official mapping coordinate/path/hash, remapped jar hash, source manifest/hash, diagnostics, and tool versions: pending validated readiness record.
- Cited Java sources/resources will be recorded with manifest-relative paths and SHA-256. Relevant original client-jar entries and referenced values/tags will be hashed; missing server/datapack-supplied data will remain an explicit dependency.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending
- Evidence inventory and finding IDs included at freeze: none yet
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; neither wiki was browsed; no old implementation, old patch class, or wiki-audit output was opened.
- Source/mapping hashes covered by freeze: pending source publication and comparison.

## Correspondence and call order

No exact class/member correspondence has been asserted yet. Once both trees are validated, resolve each logical role to the exact class, inheritance chain, signature/descriptor and body range on each side. Record the complete local input/tick path through pre-travel predicates and writes, every reachable travel branch, post-travel work/callbacks, collision queries, and external movement writers. For every edge, record state read/write and order for input, pose/dimensions/eye height, position/velocity/box, on-ground/collision/fluid flags, support, movement attributes, sprint/jump timers, and equipment/effect state. Names alone are not correspondence evidence.

## Required source inventories

Each inventory maps to bounded slice IDs below. All are pending until each member-level slice and dependency is inspected on both exact sides.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1.1-S1.7,S3.1-S3.9; evidence=pending validated exact sources and body ranges.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S1.2-S1.6,S2.1-S2.4,S3.1-S3.9,S4.1-S4.8; evidence=pending writer/consumer inventory.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S2.2,S4.1-S4.8,S5.2,S5.7; evidence=pending exact providers, registrations and calls.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S3.2,S3.5-S3.7,S4.5-S4.8,S5.1-S5.7; evidence=pending source/resource manifests.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S2.3,S3.2,S3.4,S3.6,S3.8-S3.9,S6.1-S6.5; evidence=pending consumers through registrations/data.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=S1.7,S5.5,S7.1-S7.5; evidence=pending exact packet/callback/player state writers.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; slice_ids=S1.3,S2.4,S3.4,S7.2; evidence=pending direct consumer bounds and explicit exclusion audit.

## Coverage ledger

Every row below is a bounded behavior planning slice, not a claim of inspected methods or pair correspondence. Refine rows into exact method/body ranges, state writer/consumer edges and newly found dependencies. A pending row is not evidence of similarity.

### Slice S1.1: client input sample and local tick/superclass/travel order

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S1.2: directional input conversion, yaw-relative vector math, diagonal normalization and input slowdown

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S1.3: sprint start/stop predicates, timers and stored sprint state

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S1.4: jump request, cooldown, jump timing and jump input gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S1.5: flight toggles, vertical input, ability state and flight-speed selection

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S1.6: auto-jump and input/tick unstuck probes

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S1.7: riding gates and rider input handoff relevant to player movement

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S2.1: player state defaults, initialization, reset and movement-relevant writers

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S2.2: pose choice, dimensions, eye height and resize/collision timing

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S2.3: swimming/crawling and active-use movement state

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S2.4: player edge sneaking and player-specific movement gates

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.1: travel dispatch and branch predicates before motion

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.2: ground acceleration, support friction and speed-factor selection

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.3: air acceleration, velocity cutoff/threshold and air-control math

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.4: jump power, sprint-jump impulse and velocity thresholds

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.5: climbing travel and vertical/horizontal clamps

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.6: water travel, fluid gravity/drag, swimming and water-jump gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.7: lava travel, fluid gravity/drag and fluid transition gates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.8: fall-flying/gliding travel and look/velocity transforms

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S3.9: gravity, drag, levitation/slow-fall and post-travel updates

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.1: bounding-box movement entry, position update and query timing

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.2: axis collision order, clipping and velocity cancellation/restitution

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.3: step-up candidate generation, selection and tie-breaking

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.4: edge restraint/support probing and stored on-ground state

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.5: collision-shape lookup, context, AABB/voxel operations and candidate ordering

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.6: block collision callbacks, inside-block effects and movement callbacks

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.7: fluid contact/current checks during movement and collision

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S4.8: collision/support shape context from pose, neighboring blocks and repeated queries

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.1: movement-relevant block/fluid registrations and base defaults

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.2: all registered shape overrides and collision/support shapes

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.3: ground friction, speed/jump factors and support block selection

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.4: special contacts and movement callbacks (bounce, slow/contact, launch)

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.5: climbables, bubble columns, pistons and neighboring-state movement effects

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.6: water/lava fluid properties, heights and flow vectors

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S5.7: historical partial-block shape/support inventory

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S6.1: movement attribute defaults, aggregation, operations and application/removal

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S6.2: movement status effect registrations, formulas, timers and consumers

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S6.3: movement enchantment registrations, level formulas, predicates and consumers

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S6.4: Elytra, equipment slots, active item use and movement components

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S6.5: resource-backed tags, registry data, server-synchronized movement inputs

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S7.1: incoming player velocity and position corrections

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S7.2: player knockback, push and explosion movement writers

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S7.3: piston displacement and launch-item movement paths

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S7.4: mount, rider and dismount transitions affecting player movement

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

### Slice S7.5: cross-stage writer/caller/callback/dependency closure

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: Not yet inspected; source owner has not published either exact source tree.
- A evidence: pending validated 1.20.2 source path, owner/member/body lines, and SHA-256.
- B evidence: pending validated 1.20.4 source path, owner/member/body lines, and SHA-256.
- State producers/writers -> consumers/readers: pending exact caller/writer/consumer inventory.
- Parent slices / dependencies / closure evidence: DEP-01 (both source publications); expand after exact method correspondence.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Not compared; exact-source evidence is a prerequisite.
- Finding IDs or checked absence/replacement path: none established.

## Dependency queue and blockers

- DEP-01; originating slices S1.1-S7.5; exact 1.20.2 and 1.20.4 Mojmap readiness records, source/artifact manifests and movement-method diagnostics are not published. They are required before source comparison. Next action: monitor source-owner publication, read each JSON and verify IDs, hashes, roots and relevant bodies. Owner: shared source owner. Status: pending; this queue item does not close the comparison.
- DEP-02; originating slices S5.1-S6.5; verify original client-jar resource contents and all referenced movement tags/defaults/registry values. This becomes resolvable after validated artifacts arrive; source-only missing data must be precisely identified and left blocked until obtained.
- Open dependencies: DEP-01, DEP-02

## Finding index

- No findings confirmed yet. Zero findings does not imply equivalence.
- Discarded candidates: none yet.

## Resume checkpoint

- Last completed slice: none; project/campaign instructions and decompiler protocol read; source-level comparison has not started.
- Next bounded slice: validate both exact readiness JSON records, cited SHA-256 manifests and movement-body diagnostics. Then inventory source filenames and begin S1.1 exact input/tick correspondence.
- Outstanding dependencies and owners: DEP-01 (shared source owner); DEP-02 (source worker after artifact publication).
- Current assumptions requiring verification: both requested IDs resolve exactly; Mojmap is available and aligned for both; no relevant method bodies are damaged; resource/data coverage is available from the published artifacts.

## Implementation reconciliation

Complete only after blind-discovery freeze; not authorized for this source-only assignment unless the parent explicitly changes the role.

- Reconciliation status: pending
- Repository revision inspected: not applicable yet
- Finding -> implementation disposition/evidence: deferred until source-only freeze and explicit role change
- Existing implementation without a frozen source finding: deferred
- Coverage gaps routed back to discovery slices: pending source audit

## Independent source audit

- Reviewer: not yet assigned; must differ from discovery author
- Status: pending
- Inventories and call-chain ranges re-walked: none
- Concrete missed-slice routes (or `none found`): pending review
- Misses routed to slice/finding IDs and owners: none identified yet
- Reviewer evidence / date: pending

## Source audit closure

- Coverage counts by status: pending 45 planning slices; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0.
- Required inventory status and evidence: INV-TICK, INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT, INV-MODIFIERS, INV-EXTERNAL, and INV-EXCLUSIONS all pending; evidence not yet available.
- Open dependencies: DEP-01, DEP-02
- Unresolved gaps and limits: source publication is pending; no comparison or equivalence claim has been made.
- Evidence/hash/correspondence audit: not started; readiness JSON/hash verification required before source citations.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).

This source-only report is not frozen. Keep implementation and runtime statuses separate. Runtime validation is not authorized in this task.
