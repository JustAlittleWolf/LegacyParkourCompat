# Discovery: 1.20.2 to 1.20.4

- Status: active
- Scope: direct client player movement and every reachable vanilla influence on player movement state. Older A = 1.20.2; newer B = 1.20.4. This is a source-only discovery track; no runtime Java implementation is authorized here.
- Scope exclusions: health, regeneration, hunger, food, saturation, exhaustion, damage and combat emulation, including indirect sprint-gate effects; use vanilla state consumers only. Non-player physics is out of scope. Newer-only features remain modern-only and do not gain historical behavior.
- Provenance constraints: no Minecraft Wiki, MCPK, release-note mechanics, prior implementation, old mod diffs, old patch classes, or wiki-derived audit output. Exact-version decompiled Java and bytecode are the evidence.
- Repository revision and start date: 002137b227676caea77f6832b9f4c8d0b6200bff (`main` at worktree creation); 2026-10-07.
- Worktree / branch: `C:/Users/Wolfi/.codex/worktrees/movement-source-1-20-2-1-20-4/LegacyParkourCompat`; `feat/source-discovery-movement-source-1-20-2-1-20-4`.
- Selected naming namespace, CLI mode per side and alignment evidence: planned Mojmap / `mojmap` for both. Awaiting source-owner publication and exact readiness validation; no alignment claim yet.
- Source preparation command and log: source owner only; pending exact 1.20.2 and 1.20.4 Mojmap publication. Shared source root: `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07`.
- Toolchain/decompiler/remapper versions and options: pending readiness records and artifact manifests.

## Artifact manifest

- A (1.20.2): source root, exact resolved ID, Mojang client jar identity/hash, `mojmap` CLI mode, official mappings coordinate/path/hash, remapped jar hash, source manifest/hash, decompiler diagnostics, and relevant dependency versions: pending validated readiness JSON.
- B (1.20.4): source root, exact resolved ID, Mojang client jar identity/hash, `mojmap` CLI mode, official mappings coordinate/path/hash, remapped jar hash, source manifest/hash, decompiler diagnostics, and relevant dependency versions: pending validated readiness JSON.
- Source/resource citations will use paths relative to these manifests. Original client-jar resources will be inspected locally and cited by entry name and SHA-256 when relevant.

## Correspondence and call order

Exact paths, descriptors, inheritance, callers, fields, and execution order are pending source availability. For each navigation stage, establish A-to-B correspondence from inspected members and call sites; do not treat matching names as proof. Record dependencies read or written for input, position, velocity, bounding box, pose, on-ground and collision/fluid flags, supporting block, movement attributes, sprint/jump timers, and equipment/effect state. Include the complete client input/tick/travel path and external velocity writers.

## Coverage ledger

Behavior-slice inventory, all pending exact member correspondence and source availability. Split any large method by named behavior without omitting surrounding guards/order; add rows for every discovered callee, writer, override, registration, resource and external producer. These behavior labels are an inspection plan, not findings or proof that the behavior differs.

- S1.1 / stage 1 / client input sample and local tick/superclass/travel order: pending
  - A/B methods, callers, lines and hashes: pending validated source.
  - Closure: who samples input, when prior/current input/flags are captured, and all call-order transitions.
- S1.2 / stage 1 / directional input conversion, yaw-relative vector math, diagonal normalization and input slowdown: pending
  - A/B evidence and hashes: pending.
  - Closure: input type/defaults, keyboard/controller producers, sneak/use scaling and called vector math.
- S1.3 / stage 1 / sprint start/stop predicates, timers and stored sprint state: pending
  - A/B evidence and hashes: pending.
  - Closure: every producer/consumer and reset point; include only in-scope vanilla sprint gate consumers and exclude hunger/food-system emulation.
- S1.4 / stage 1 / jump request, cooldown, jump timing and jump input gates: pending
  - A/B evidence and hashes: pending.
  - Closure: local player to superclass jump path and reset/update order; impulse math is cross-linked to S3.
- S1.5 / stage 1 / flight toggles, vertical input, ability state and flight-speed selection: pending
  - A/B evidence and hashes: pending.
  - Closure: key/input producer, ability defaults/writers and all reachable movement consumers.
- S1.6 / stage 1 / auto-jump and input/tick unstuck probes: pending
  - A/B evidence and hashes: pending.
  - Closure: enabling gates, candidate move, collision probes and resulting jump/state writes.
- S1.7 / stage 1 / riding gates and rider input handoff relevant to player movement: pending
  - A/B evidence and hashes: pending.
  - Closure: player branch, mount-provided values and dismount/tick order; independent mount simulation is excluded.
- S2.1 / stage 2 / player state defaults, initialization, reset and movement-relevant writers: pending
  - A/B evidence and hashes: pending.
  - Closure: inheritance, constructors, tick writers and all reachable player movement consumers.
- S2.2 / stage 2 / pose choice, dimensions, eye height and resize/collision timing: pending
  - A/B evidence and hashes: pending.
  - Closure: pose predicate, dimensions, `refreshDimensions`-equivalent path, fluid/collision query users and order of position/box changes.
- S2.3 / stage 2 / swimming/crawling and active-use movement state: pending
  - A/B evidence and hashes: pending.
  - Closure: pose transition, input scaling, slowdown consumer and equipment/item preconditions.
- S2.4 / stage 2 / player edge sneaking and player-specific movement gates: pending
  - A/B evidence and hashes: pending.
  - Closure: edge support probes, sprint/jump gates and relevant state writers; health/food systems remain excluded.
- S3.1 / stage 3 / travel dispatch and branch predicates before motion: pending
  - A/B evidence and hashes: pending.
  - Closure: exact caller path, mode flags, ordering and all branches selected from player state.
- S3.2 / stage 3 / ground acceleration, support friction and speed-factor selection: pending
  - A/B evidence and hashes: pending.
  - Closure: movement speed attributes/helpers, block friction/speed factors, support-block selection and operation order.
- S3.3 / stage 3 / air acceleration, velocity cutoff/threshold and air-control math: pending
  - A/B evidence and hashes: pending.
  - Closure: constants, casts, float/double path, helper calls and state writes before/after travel.
- S3.4 / stage 3 / jump power, sprint-jump impulse and velocity thresholds: pending
  - A/B evidence and hashes: pending.
  - Closure: jump helpers, attributes/effects, effect amplifier arithmetic, sprint state and precondition timing.
- S3.5 / stage 3 / climbing travel and vertical/horizontal clamps: pending
  - A/B evidence and hashes: pending.
  - Closure: climbable contact checks, block/state inputs, collision and velocity reset paths.
- S3.6 / stage 3 / water travel, fluid gravity/drag, swimming and water-jump gates: pending
  - A/B evidence and hashes: pending.
  - Closure: fluid heights/current, depth enchantment/effect, pose, sprint gates and post-travel order.
- S3.7 / stage 3 / lava travel, fluid gravity/drag and fluid transition gates: pending
  - A/B evidence and hashes: pending.
  - Closure: lava contact/height, fluid helpers, velocity reset and mode transitions.
- S3.8 / stage 3 / fall-flying/gliding travel and look/velocity transforms: pending
  - A/B evidence and hashes: pending.
  - Closure: start/stop gates, Elytra equipment/durability state consumer, exact trig/order and all velocity writes.
- S3.9 / stage 3 / gravity, drag, levitation/slow-fall and post-travel updates: pending
  - A/B evidence and hashes: pending.
  - Closure: attributes/effects, gravity toggles, fall distance and tick timing; separate health/damage consequences.
- S4.1 / stage 4 / bounding-box movement entry, position update and query timing: pending
  - A/B evidence and hashes: pending.
  - Closure: all callers, repeated move calls and box/position writers.
- S4.2 / stage 4 / axis collision order, clipping and velocity cancellation/restitution: pending
  - A/B evidence and hashes: pending.
  - Closure: shape enumeration, collision flags and velocity component resets.
- S4.3 / stage 4 / step-up candidate generation, selection and tie-breaking: pending
  - A/B evidence and hashes: pending.
  - Closure: step-height input, all candidate paths, axis order, collision shapes and final box selection.
- S4.4 / stage 4 / edge restraint/support probing and stored on-ground state: pending
  - A/B evidence and hashes: pending.
  - Closure: probe dimensions/order, support lookup, velocity thresholds and flag updates.
- S4.5 / stage 4 / collision-shape lookup, context, AABB/voxel operations and candidate ordering: pending
  - A/B evidence and hashes: pending.
  - Closure: caller-to-world query path and every relevant shape/shape utility dependency.
- S4.6 / stage 4 / block collision callbacks, inside-block effects and movement callbacks: pending
  - A/B evidence and hashes: pending.
  - Closure: callback order, state/entity conditions and callback implementations affecting player state.
- S4.7 / stage 4 / fluid contact/current checks during movement and collision: pending
  - A/B evidence and hashes: pending.
  - Closure: fluid state/height/vector calculation, update order and movement-state writers; cross-link S3 fluids.
- S4.8 / stage 4 / collision/support shape context from pose, neighboring blocks and repeated queries: pending
  - A/B evidence and hashes: pending.
  - Closure: shape contexts and neighbor state dependencies, including support/contact time.
- S5.1 / stage 5 / movement-relevant block/fluid registrations and base defaults: pending
  - A/B source/resource evidence and hashes: pending.
  - Closure: exhaustive relevant registration inventory, vanilla defaults, resource-backed state values and checked absences.
- S5.2 / stage 5 / all registered shape overrides and collision/support shapes: pending
  - A/B evidence and hashes: pending.
  - Closure: discover by override/caller/registry traversal, not only named candidate classes; record modern-only registrations separately.
- S5.3 / stage 5 / ground friction, speed/jump factors and support block selection: pending
  - A/B evidence and hashes: pending.
  - Closure: base and subclass implementations, all providers and caller selection order.
- S5.4 / stage 5 / special contacts and movement callbacks (bounce, slow/contact, launch): pending
  - A/B evidence and hashes: pending.
  - Closure: slime/bed/web/honey/powder-snow/other discovered overrides, predicates, neighbor dependence and callback order.
- S5.5 / stage 5 / climbables, bubble columns, pistons and neighboring-state movement effects: pending
  - A/B evidence and hashes: pending.
  - Closure: state/neighbor dependencies, registration, player path and client/server boundary.
- S5.6 / stage 5 / water/lava fluid properties, heights and flow vectors: pending
  - A/B source/resource evidence and hashes: pending.
  - Closure: every relevant fluid implementation/helper, resource default/tag and travel consumer.
- S5.7 / stage 5 / historical partial-block shape/support inventory: pending
  - A/B evidence and hashes: pending.
  - Closure: fences, walls, stairs, slabs, trapdoors, doors, snow layers, farmland, paths and any discovered candidates; checked registrations and actual player collision reachability.
- S6.1 / stage 6 / movement attribute defaults, aggregation, operations and application/removal: pending
  - A/B source/resource evidence and hashes: pending.
  - Closure: consumers through attribute modifiers, operation ordering, defaults, synchronization and equipment/effect writers.
- S6.2 / stage 6 / movement status effect registrations, formulas, timers and consumers: pending
  - A/B source/resource evidence and hashes: pending.
  - Closure: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace, Blindness plus discovered effects; health/damage excluded.
- S6.3 / stage 6 / movement enchantment registrations, level formulas, predicates and consumers: pending
  - A/B source/resource evidence and hashes: pending.
  - Closure: Depth Strider, Soul Speed, Swift Sneak, Frost Walker, Riptide plus discovered entries, item/equipment applicability and tags.
- S6.4 / stage 6 / Elytra, equipment slots, active item use and movement components: pending
  - A/B source/resource evidence and hashes: pending.
  - Closure: direct movement consumers, item/component defaults, applicability, timers and relevant client/server boundary.
- S6.5 / stage 6 / resource-backed tags, registry data, server-synchronized movement inputs: pending
  - A/B source/resource evidence and hashes: pending.
  - Closure: cited jar entry hashes, referenced values/tags and explicit provenance; missing external data becomes blocked, not assumed absent.
- S7.1 / stage 7 / incoming player velocity and position corrections: pending
  - A/B evidence and hashes: pending.
  - Closure: packet handlers, local player state writers, correction/lerp order and subsequent tick behavior.
- S7.2 / stage 7 / player knockback, push and explosion movement writers: pending
  - A/B evidence and hashes: pending.
  - Closure: source callers and incoming external versus local computation; do not audit independent non-player physics.
- S7.3 / stage 7 / piston displacement and launch-item movement paths: pending
  - A/B evidence and hashes: pending.
  - Closure: player-facing callbacks/packets, velocity/position writes and world/server supplied values.
- S7.4 / stage 7 / mount, rider and dismount transitions affecting player movement: pending
  - A/B evidence and hashes: pending.
  - Closure: local player gates and player position/velocity/state writers; mount's independent physics excluded.
- S7.5 / stage 7 / cross-stage writer/caller/callback/dependency closure: pending
  - A/B evidence and hashes: pending.
  - Closure: revisit unchanged callers whose dependencies differ; resolve all discovered writers, overrides, registries and interactions.
## Dependency queue and blockers

- DEP-01; originating slices S1-S7; exact 1.20.2 and 1.20.4 Mojmap source roots, readiness JSON, source/artifact SHA-256 manifests, exact release IDs and relevant body diagnostics are not yet published. These are prerequisites for comparison. Next action: wait for source-owner publication; then read the JSON and verify all cited hashes and movement-method bodies before opening source. Status: pending, not a comparison blocker yet.
- DEP-02; originating slice S5-S6; original client-jar resources and referenced registries/tags/default data must be verified for movement data omitted by the Java saver. Resolve after validated artifacts are available.

## Finding index

- No findings confirmed yet. Zero findings does not imply equivalence.
- Discarded candidates: none yet.

## Resume checkpoint

- Last completed slice: none; workflow and repository inputs read, pair inventory not yet source-verified.
- Next bounded slice: verify both readiness JSON files against exact requested/resolved IDs, source/artifact manifests, hashes and diagnostics; then enumerate exact movement entrypoints for stage 1.
- Outstanding dependencies: DEP-01 source publication; DEP-02 original client-jar movement resources.
- Current assumptions requiring verification: both exact releases can be compared in Mojmap; no source-body damage affects relevant methods; all exact manifest entries refer to currently published bytes.

## Source audit closure

- Coverage counts by status: pending 44 behavior-slice registrations; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0. The stage registrations are intentionally coarse and must be expanded to exhaustive per-stage method/dependency slices before closure.
- Explicit pending count: 44 behavior-slice registrations plus 2 source/data dependencies; exact method-level inventory is not yet established.
- Unresolved gaps and limits: all substantive comparison remains pending source publication. No equivalence claim.
- Evidence/hash/correspondence audit: not started; readiness records must be validated before use.
- Runtime validation: not performed (separate workflow).

