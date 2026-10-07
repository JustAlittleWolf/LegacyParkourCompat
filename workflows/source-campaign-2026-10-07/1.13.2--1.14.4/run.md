# Discovery: 1.13.2 to 1.14.4

- Status: active
- Scope: source-only comparison of direct client player movement; older A = exact release 1.13.2; newer B = exact release 1.14.4.
- Repository revision and start date: comparison branch created from `main` at `002137b227676caea77f6832b9f4c8d0b6200bff`; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: Ornithe Feather (`feather` CLI mode on each side; expected generated folder `ornithe-feather`) requested as one aligned family. Mapping availability and resolved coordinates/builds await source-owner readiness record. Do not begin source comparison until exact artifacts are validated.
- Source preparation command and log: source-owner handoff pending. Candidate command is `gradlew.bat decompileMinecraft --versions=1.13.2,1.14.4 --mappings=feather`; do not run against a shared source/cache directory.
- Toolchain/decompiler/remapper versions and options: pending validated preparation record from the source owner.
- Track declaration: source-only discovery; no MCPK/Minecraft Wiki comparison; no mod implementation inspection; no implementation or runtime validation in this track.

## Artifact manifest

Source preparation is not yet published. Readiness marker search found no `*ready*.json` or release directories under either the new worktree's or primary checkout's `decompiled_minecraft` root. This is only a preparation-state observation, not evidence about release behavior. Source-owner handoff required before opening generated sources.

### A — 1.13.2

- Exact requested/resolved release: requested 1.13.2; resolver confirmation pending.
- Source root and client jar identity/SHA-256: pending validated readiness record.
- Naming namespace/CLI mode: Feather / `feather` requested; exact mapping coordinate/build, mapping file/hash, mapped jar hash and command/log: pending validated readiness record.
- Cited sources/resources and SHA-256: none yet.

### B — 1.14.4

- Exact requested/resolved release: requested 1.14.4; resolver confirmation pending.
- Source root and client jar identity/SHA-256: pending validated readiness record.
- Naming namespace/CLI mode: Feather / `feather` requested; exact mapping coordinate/build, mapping file/hash, mapped jar hash and command/log: pending validated readiness record.
- Cited sources/resources and SHA-256: none yet.

## Correspondence and call order

- No source members inspected and no correspondence asserted yet. After readiness: establish local client input/tick -> player/superclass tick -> travel/jump -> entity move/collision -> movement-state writers and externally supplied movement updates, resolving descriptors/callers independently in both releases.

## Coverage ledger

Each row is pending because the exact source artifacts have not been made ready. These are stage inventories, not claims that each stage is one bounded method slice. Expand to method-level bounded units and record exact paired evidence before terminal status.

- S1 / local input and tick ordering: `pending`; input sampling, local tick and superclass/travel ordering, input scaling, sprint/jump/flight/riding gates and state capture.
- S2 / player-specific state and gates: `pending`; pose/dimensions/eye height, swimming/crawling, flight speed, edge sneaking and directly movement-relevant state writers. Hunger/food/exhaustion/damage/combat emulation excluded; inspect only vanilla-state consumers where relevant.
- S3 / living movement integration: `pending`; ground/air acceleration, friction, gravity/drag, velocity thresholds, jumps, climbing, water/lava travel, swimming/gliding, attributes and directly relevant effect consumers.
- S4 / entity movement and collision: `pending`; bounding-box/pose timing, collision query shapes/order, axes/step candidates/ties, support/grounding, velocity cancellation/restitution, fluid contact/push and callbacks.
- S5 / blocks and fluids producing player movement inputs: `pending`; registrations/defaults, movement factors, shapes and callbacks for features present in each release, neighboring-state checks, flow/current and relevant resources.
- S6 / effects, enchantments, attributes and equipment: `pending`; movement consumers through aggregation, registration/application/removal, equipment/tags/resources. Server-provided values and modern-only mechanics separately classified.
- S7 / external influences and dependency closure: `pending`; player velocity/position update consumers, packet/correction path, knockback/push, explosion/piston/launch-item/mount transitions and remaining reachable state writers.

## Dependency queue and blockers

- D0; originating stage: pair establishment; exact 1.13.2 and 1.14.4 source readiness JSON with resolved IDs, aligned Feather namespace/builds, source/client/mapping/mapped-jar hashes, relevant resource artifact hashes, and body diagnostics; required to establish comparable evidence; next action: obtain source-owner publication and verify all hashes/IDs and successful decompiler diagnostics. No source comparison begun.

## Finding index

- No findings yet; no behavioral comparisons have been made.

## Resume checkpoint

- Last completed slice: none; required project/workflow documentation read.
- Next action: consume the validated source-owner readiness record; verify exact release IDs, namespaces, hashes and relevant-body diagnostics; then construct the per-role/member/caller inventory in source-navigation order.
- Outstanding dependencies: D0 source readiness.
- Assumptions requiring verification: both releases can be published in the requested aligned Feather family; candidate CLI command is not yet executed or proven successful.

## Source audit closure

- Coverage counts by status: pending 7 stage inventories; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0.
- Unresolved gaps and limits: source preparation and the entire source comparison are pending; no movement conclusions are supported yet. Scope includes all reachable direct player movement influences and movement-relevant block shapes; excludes health/regeneration/hunger/food/saturation/exhaustion/damage/combat emulation, modern-only historical behavior and independent non-player physics.
- Evidence/hash/correspondence audit: not started; no source citations or correspondence claims.
- Runtime validation: not performed (separate workflow).

### Navigation work queue (all pending until method evidence exists)

The seven stage rows above remain open inventory slices. This checklist scopes the bounded member-level slices to create after the exact source pair is verified; none is a conclusion or no-difference claim.

- S1 input production: current/previous input capture; forward/strafe conversion and diagonal normalization; sneak/item-use movement scaling; sprint start/stop gates and timers; jump input/cooldown; local-tick, superclass-tick and travel order; auto-jump; flight toggle and vertical input; riding and unstuck gates.
- S2 player state: pose selection and dimensions; bounding-box resize timing and collision fallback; eye height where it affects fluid/collision probes; swim/crawl transitions and state lifetime; edge sneaking probes; flight ability/speed state; air-speed carryover; sprint gates that consume vanilla food/blindness state (consumer only, no food/health behavior audit).
- S3 living travel: dispatch and branch order; ground acceleration/friction; air acceleration; velocity epsilon/cutoff and gravity/drag; jump impulse, sprint-jump and jump-boost arithmetic; climb clamps; water travel, jump/descent/sneak, swim and current; lava travel and current; fall-flying/glide controls; pre/post-travel updates and directly used attributes/effect helpers.
- S4 collision/movement: entity move entry and requested displacement; axis ordering and collision candidate selection; step-up candidates/tie-breaking; grounding and support position; collision flags and velocity cancellation/restitution; AABB/voxel-shape operations used on player paths; repeated move/query timing after pose changes; block callback ordering; fluid contact, height and push queries; external position/velocity correction path.
- S5 block/fluid movement inputs: registrations and default state/property providers; friction/speed/jump-factor consumers and assignments; ice variants and soul sand; slime/bed landing and bounce; web slowdown; ladder/vine/climbable checks; water/lava/bubble-column contact and flow; piston displacement; existing partial-block shapes and neighbor/state-dependent collision for stairs, slabs, trapdoors, doors, fences, walls, snow layers, farmland/path; all additional reachable overrides found by registration/subclass enumeration; resource/tag-backed shape or fluid inputs.
- S6 movement data: speed/slowness/jump-boost/slow-falling/levitation/Dolphin's Grace consumers; blindness only where vanilla sprint gate consumes it; effect registration, formulas and lifetime relevant to direct motion; attribute aggregation/operation ordering/defaults; Depth Strider and other present movement enchantments with registration/level/conditions/equipment/tags; item-use slowdown; movement-relevant equipment slots/components; server-synchronized values and missing external data boundaries. Health, regeneration, hunger/food/saturation/exhaustion/damage/combat behavior excluded.
- S7 external writers and closure: client packet consumers for position/velocity corrections; player knockback/push and explosions; piston/launch-item displacement; mount/dismount/transition writes that change player motion; remaining reachable movement-state writers and changed dependency callsites; cross-mechanic interactions; player-only consequences of another entity (independent non-player simulation excluded).

