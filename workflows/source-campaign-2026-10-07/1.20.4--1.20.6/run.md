# Discovery: 1.20.4 to 1.20.6

- Status: active
- Scope: direct client-player movement; older A = 1.20.4; newer B = 1.20.6. Source-only discovery; no Minecraft wiki/MCPK sources or audit outputs, no mod implementation inspection, no runtime implementation or runtime validation.
- Repository revision and start date: base main = 002137b227676caea77f6832b9f4c8d0b6200bff; task branch = feat/source-discovery-movement-source-1-20-4-1-20-6; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: pending validated source-owner handoff; requested alignment is Mojmap / Mojmap.
- Source preparation command and log: source-owner generated shared output; exact readiness JSON, source/artifact manifests and logs pending validation. This worker will not run decompileMinecraft or write shared artifacts.
- Toolchain/decompiler/remapper versions and options: pending validated provenance.

## Artifact manifest

- A: requested/resolved 1.20.4; expected Mojmap. Source root, client jar identity/hash, mapping coordinate/build/path/hash, mapped jar hash, source file hashes, tool versions/options, and successful log: pending validated ready JSON and cited manifests.
- B: requested/resolved 1.20.6; expected Mojmap. Source root, client jar identity/hash, mapping coordinate/build/path/hash, mapped jar hash, source file hashes, tool versions/options, and successful log: pending validated ready JSON and cited manifests.
- No source-tree or artifact-directory presence is treated as readiness. Exact release IDs, namespace, cited manifest hashes and relevant method bodies must be verified before comparison.

## Correspondence and call order

- Pending exact paired class/member correspondence, descriptors, inheritance/callers, split/replacement evidence, read/write state and ordered calls. No guessed correspondence is accepted.
- Stage 1: local input and tick order -> pending inventory and source verification.
- Stage 2: player-specific state/gates and pose/dimensions -> pending inventory and source verification.
- Stage 3: living movement integration -> pending inventory and source verification.
- Stage 4: entity movement/collision/support/callback order -> pending inventory and source verification.
- Stage 5: blocks/fluids/shapes/registrations/neighbors -> pending inventory and source verification.
- Stage 6: movement attributes/effects/enchantments/equipment/data -> pending inventory and source verification.
- Stage 7: external velocity/position writers and final dependency closure -> pending inventory and source verification.

## Coverage ledger

Every row starts pending and requires exact A/B evidence, hashes, member/range citations, and dependency closure before it can become terminal. HEALTH, regeneration, hunger, food, saturation, exhaustion, damage and combat mechanics are excluded; vanilla state consumers directly gating player movement remain in scope.

| Slice ID | Stage / bounded behavior | Status | A and B evidence | Dependency closure / conclusion |
|---|---|---|---|---|
| S1-input-sampling | 1 / local input sampling, input defaults and per-tick capture | pending | pending | input class, options/key state producers |
| S1-input-motion | 1 / yaw-to-motion, diagonal normalization, input scaling and move-relative dispatch | pending | pending | local input to movement vector |
| S1-sprint-gates | 1 / sprint start/stop, timers, gates, item-use and blindness consumers only | pending | pending | state consumers; exclude food/health emulation |
| S1-jump-gates | 1 / jump request, cooldown, jump delay, auto-jump and sprint-jump scheduling | pending | pending | input, local-player tick, jump helper |
| S1-flight-tick | 1 / flight toggle, flight input/speed and unstuck/riding gates | pending | pending | abilities, packet/state inputs |
| S1-tick-order | 1 / local tick, superclass tick, travel call and previous/current flags | pending | pending | complete reachable call sequence |
| S2-pose-selection | 2 / pose choice and swimming/crawling/standing/crouching transitions | pending | pending | pose predicates and timers |
| S2-dimensions | 2 / entity dimensions, eye height, resize ordering and collision checks | pending | pending | dimensions, pose, AABB/query timing |
| S2-player-state | 2 / player movement state initialization/update/reset (excluding excluded health/food systems) | pending | pending | state writers and lifetime |
| S2-sprint-consumers | 2 / hunger/blindness/item-use vanilla consumers of sprint gates | pending | pending | inspect consumers only; no excluded mechanic emulation |
| S2-flight-abilities | 2 / flight ability flags and direct movement consumers | pending | pending | ability defaults, update writers |
| S3-travel-dispatch | 3 / travel dispatch, branch selection and pre/post-travel sequence | pending | pending | full reachable method bodies and callers |
| S3-ground-acceleration | 3 / ground acceleration and movement-speed inputs | pending | pending | attribute/helper aggregation and friction |
| S3-air-acceleration | 3 / air acceleration, stored air speed and flying travel branch | pending | pending | player-specific values and abilities |
| S3-velocity-cutoffs | 3 / negligible-velocity thresholds, comparisons, casts and zeroing | pending | pending | exact literals and caller timing |
| S3-gravity-drag | 3 / gravity, drag, levitation/slow-falling and post-travel updates | pending | pending | effects/attributes and exact operation order |
| S3-ground-jump | 3 / ground jump power, jump boost and jump attribute path | pending | pending | attributes, effects, callbacks |
| S3-sprint-jump | 3 / sprint jump impulse direction, arithmetic order and velocity writes | pending | pending | yaw, sprint and jump call chain |
| S3-climbing | 3 / climbable branch, clamps, fall resets and wall contact | pending | pending | climbable state/callbacks |
| S3-water | 3 / water travel, swimming, buoyancy, drag and fluid height | pending | pending | fluid state, flow vectors, attributes |
| S3-lava | 3 / lava travel, buoyancy, drag and fluid height | pending | pending | fluid state and exact thresholds |
| S3-gliding | 3 / elytra/gliding travel, launch and item-use slowdown | pending | pending | equipment, durability/use state, attributes |
| S3-effects-branch | 3 / movement effect branches and operation order within travel | pending | pending | Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace |
| S4-box-movement | 4 / bounding-box movement, position update and collision axis order | pending | pending | Entity, AABB, world collision calls |
| S4-step-candidates | 4 / step-up candidates, comparison, tie-breaking and step height | pending | pending | full step helper/dependencies |
| S4-edge-probes | 4 / edge sneaking/probes, support checks and query timing | pending | pending | shape context, block support lookup |
| S4-grounding | 4 / on-ground, horizontal/vertical collision flags and velocity response | pending | pending | axis result, support position, thresholds |
| S4-collision-query | 4 / collision candidate iteration, shape selection and filtering | pending | pending | world lookup, entity/context, iteration order |
| S4-shapes | 4 / voxel shape operations, unions, clipping and AABB helpers | pending | pending | all reachable helpers/overrides |
| S4-callbacks | 4 / block contact, fall/landing, inside-block and movement callback order | pending | pending | callback producers and block overrides |
| S4-fluid-contact | 4 / fluid contact/height/push and player fluid flags | pending | pending | fluid implementation, tag/resource dependencies |
| S5-landing-bounce | 5 / slime/bed/other landing and bounce behavior | pending | pending | block registrations, callback and state conditions |
| S5-friction-speed | 5 / friction, speed and jump factors (ice, soul sand, honey, etc.) | pending | pending | defaults, subclasses, registration values |
| S5-slowdown-contact | 5 / webs, powder snow and other direct player contact slowdown | pending | pending | block state/callback and shape |
| S5-climbables | 5 / ladders, vines and other climbable registrations/overrides | pending | pending | tags, neighboring/state predicates |
| S5-fluid-blocks | 5 / water/lava/bubble column movement inputs and flow calculations | pending | pending | fluids, blocks, resources and exact call order |
| S5-pistons | 5 / moving piston player displacement and contact behavior | pending | pending | callbacks, collision queries, external writer path |
| S5-partial-shapes | 5 / fences, walls, stairs, slabs, trapdoors, doors, snow, farmland, paths | pending | pending | enumerate relevant registries/overrides and neighbor conditions |
| S5-registrations | 5 / registration/default properties and all additional movement-relevant block subclasses | pending | pending | complete source/resource inventory; additions/removals |
| S5-modern-only | 5 / absent-from-A block/state registry checks and applicability | pending | pending | registry evidence; no historical invention |
| S6-attributes | 6 / movement attributes, defaults, aggregation and modifier order | pending | pending | consumers, constructors, registration/resource data |
| S6-effects | 6 / Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace, Blindness | pending | pending | consumers to registration/application/removal chain |
| S6-enchantments | 6 / Depth Strider, Soul Speed, Swift Sneak, Frost Walker, Riptide | pending | pending | formulas, level gates, tags, conditions, data |
| S6-equipment | 6 / Elytra, movement item components/equipment slots and use slowdown | pending | pending | item predicates, effects, attributes, data |
| S6-resources | 6 / jar tags, enchantment/effect/component definitions and referenced defaults | pending | pending | exact entry/hash; identify synchronized/external inputs |
| S6-server-boundary | 6 / server-synchronized movement attributes/data and client-only conclusion | pending | pending | establish provenance; mark unavailable inputs blocked |
| S7-velocity-writers | 7 / incoming velocity, explosions, knockback/push and player velocity writes | pending | pending | packet/event caller to state writer |
| S7-position-writers | 7 / position corrections, piston displacement, launch items and direct setters | pending | pending | packet/event caller to state writer |
| S7-riding | 7 / mount/dismount transitions and player movement-state changes | pending | pending | local player path only; other entity simulation excluded |
| S7-closure | 7 / enumerate changed helpers, writers, callbacks, registries and cross-slice interactions | pending | pending | revisit dependent callers and close every reachable queue item |

## Dependency queue and blockers

- D-SOURCES: originating slice = all; exact 1.20.4 and 1.20.6 Mojmap validated-ready JSONs, source/artifact manifest files, method diagnostics and logs are pending from source owner; movement comparison must not begin until IDs, hashes, and relevant bodies verify. Next action: verify the shared build/movement-campaign-2026-10-07/ready/ publication when announced.
- Additional dependencies: none registered before source navigation. Add each with its source slice, reason, retrieval action and resolution evidence.

## Finding index

- No source-confirmed findings yet. No prior catalogs or implementation changes were used as proof of coverage.

## Resume checkpoint

- Last completed slice: none; branch and workflow/template orientation complete.
- Next action: receive exact Mojmap source-owner publication; validate both readiness JSONs, exact release IDs, source/artifact hashes, and relevant body diagnostics. Then build filename inventories and correspondence/call graph before comparing bounded slices in navigation order.
- Outstanding dependencies: D-SOURCES.
- Current assumptions requiring verification: Mojmap is available/aligned on both exact endpoints; every relevant source body is intact; source manifests and diagnostic logs are preserved.
- Explicit coverage counts: 0 terminal; 54 pending; 0 in-progress; 0 blocked; 0 unresolved source findings (comparison not started).

## Source audit closure

- Coverage counts by status: pending 54; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0. The run remains active because the external source handoff is queued and useful inventory work remains.
- Unresolved gaps and limits: all 54 slices pending; source provenance and member correspondence pending; no semantic equivalence claims made.
- Evidence/hash/correspondence audit: no source evidence cited yet; validate exact source and artifact hashes before citing.
- Runtime validation: not performed (separate workflow). No tests, client/game/TAS/Gym/server/Docker launches performed.

