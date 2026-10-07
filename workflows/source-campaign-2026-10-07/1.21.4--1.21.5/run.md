# Source-only movement comparison: Minecraft 1.21.4 to 1.21.5

- Status: active
- Scope: direct client player movement; older A = 1.21.4; newer B = 1.21.5.
- Track: source-only discovery; no wiki comparison, mod implementation inspection, or runtime implementation.
- Repository base: 002137b227676caea77f6832b9f4c8d0b6200bff (main); branch feat/source-discovery-movement-source-1-21-4-1-21-5; start date 2026-10-07.
- Namespace, CLI modes, mapping alignment, source command/log, and toolchain: pending validated source-owner readiness records.

## Artifact manifest

Neither exact release currently has a ready publication under build/movement-campaign-2026-10-07/ready/. Verify the exact readiness JSONs, IDs, namespace, cited source/artifact SHA-256 manifest hashes, movement-method diagnostics, and relevant source bodies before comparison.

- A 1.21.4: pending readiness JSON; jar identity/hash; mode; mapping coordinate/build/file/hash; mapped jar hash; source root; cited source/resource hashes.
- B 1.21.5: pending readiness JSON; jar identity/hash; mode; mapping coordinate/build/file/hash; mapped jar hash; source root; cited source/resource hashes.
- Source preparation is owned externally. This branch will not write or regenerate shared source artifacts.

## Correspondence and call order

Exact-source inspection pending. Resolve class/member descriptors, inheritance, callers, replacements, state writers, registrations and resource-backed values on both sides. Class-name similarity and neighboring reports are navigation aids, not correspondence evidence.

Document ordered calls and state reads/writes for: (1) input sampling/scaling, local player tick, inherited tick and travel dispatch, sprint/jump/flight/auto-jump/riding; (2) pose/dimension/eye-height changes, resize and collision-check timing; (3) travel branches, acceleration/friction/gravity/drag, velocity thresholds, jump impulses, climbing, water/lava and gliding; (4) entity movement, box/shape collision queries, axis resolution, step alternatives/tie-breaking, support/grounding and callbacks; (5) movement-relevant block/fluid shapes, overrides, registrations, neighboring-state rules, tags and resources; (6) direct movement attribute/effect/enchantment/equipment/component consumers and their data/application/removal paths; (7) reachable player velocity/position writers, corrections, knockback/push, piston/launch and mount transitions. Exclude health, regeneration, hunger, food, saturation, exhaustion, damage and combat emulation; include only vanilla movement-state consumers such as sprint eligibility. Exclude non-player physics and keep newly introduced blocks modern-only.

## Coverage ledger

Rows are pending until exact A/B evidence and dependency closure are recorded. Split into method-sized slices after the complete inventories; track every discovered caller, writer, override, shape/provider, registration/resource and consumer.

| Slice | Navigation unit | Status | Required closure |
|---|---|---|---|
| S1-input-tick | Input producers; local and superclass tick/travel order | pending | Calls, predicates, timers and flag snapshots |
| S1-sprint-jump | Sprint, jump, auto-jump, flight and riding gates | pending | All state readers/writers and exact ordering |
| S2-pose-resize | Pose, dimensions, eye height and resize checks | pending | Defaults/transitions and collision-query timing |
| S2-player-gates | Direct movement eligibility and player state | pending | Vanilla consumers; health/food emulation excluded |
| S3-land-travel | Dispatch, ground/air acceleration, friction, drag and cutoffs | pending | Helpers, constants, precision, attributes |
| S3-jump-climb | Jump power/impulses, climbing and post-travel updates | pending | Effects/attributes, wall state and order |
| S3-fluid-glide | Fluid travel, swimming, gliding and branch boundaries | pending | Contact, height/flow, pose, equipment/effects |
| S4-move-collision | Move, collision lookup, axis resolution | pending | Shape/AABB helpers, query order and state writes |
| S4-step-support | Step candidates, tie-breaks, edge probes, grounding/support | pending | Conditions, support lookup, velocity cancellation |
| S4-callbacks | Collision callbacks and inside-block contact | pending | Implementations and invocation order |
| S5-shapes-blocks | Shapes, friction/speed/jump factors and contact behavior | pending | Overrides, registrations, neighbors, modern-only absences |
| S5-fluids | Fluid properties, height/flow, currents, bubble columns/push | pending | Data/tags, registrations and client/server boundary |
| S6-attributes-effects | Direct movement attributes/effects and aggregation | pending | Producers/consumers, defaults, timers and resources |
| S6-enchantments-equipment | Movement enchantments, equipment, item components/use slowdown | pending | Data, conditions, consumers and external inputs |
| S7-external-writers | Player velocity/position corrections and external writers | pending | Reachable client paths; no non-player simulation |
| S7-cross-dependencies | Revisit unchanged callers after dependency closure | pending | Resolve or precisely block each dependency |

## Dependency queue and blockers

- D0: Obtain exact 1.21.4 and 1.21.5 readiness JSONs, manifests and diagnostics from source owner; verify namespace, IDs, hashes and relevant method bodies.
- D1: Inventory both complete player/input/travel/collision hierarchies, callers and state writers; follow every changed/influential helper into bounded slices.
- D2: Inspect corresponding client-jar resources and referenced defaults/tags/components omitted by Java source saver; record entry names and hashes.
- Current external dependency: no exact-pair ready publication is present. Navigation remains open; this is not an equivalence result.

## Finding index

No confirmed findings yet. Absence of findings is not a no-difference claim. Add one independently scoped file per source-confirmed delta after reachability and both-side evidence are verified.

## Resume checkpoint

- Last completed slice: none. Project guidance and ordered navigation were read; no exact-pair source inventory yet.
- Next: validate readiness records; inventory input and local tick entry points before opening paired movement bodies.
- Outstanding: D0-D2.
- Unverified: aligned namespace, provenance, mapping artifacts and all movement behavior.

## Source audit closure

- Counts: 0 compared-no-difference; 0 findings; 0 not-applicable; 0 blocked; 17 pending; 0 in-progress.
- Explicit pending count: 17 navigation slices, before method-level subdivision and extra dependencies found during inventory.
- Gaps: all seven navigation stages and resource/dependency closure are unexamined for this pair.
- Evidence/hash/correspondence audit: not started; readiness and hashes required before source use.
- Runtime validation: not performed. No clients, TAS, game/server launches, tests, builds or Docker were run.

