# Discovery: 1.21.10 to 1.21.11

- Status: active
- Scope: source-only direct client-player movement comparison; older A = 1.21.10; newer B = 1.21.11. No runtime Java implementation, no wiki/MCPK evidence, no release-note claims. Exclude health, regeneration, hunger, food, saturation, exhaustion, damage and combat emulation; non-player physics and historical behavior for modern-only features are out of scope.
- Repository revision and start date: base 002137b227676caea77f6832b9f4c8d0b6200bff; 2026-10-07. Dedicated branch: feat/source-discovery-movement-source-1-21-10-1-21-11.
- Selected naming namespace, CLI mode per side and alignment evidence: awaiting source owner readiness handoff; compare only after exact IDs, namespace and ready JSON are verified.
- Source preparation command and log: source owner owns generation/publication; no local decompiler run. Requested pair-specific readiness by commentary on 2026-10-07.
- Toolchain/decompiler/remapper versions and options: Gradle fork uses Java 25; Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1, Gson 2.14.0, ASM 9.10.1 are declared in the version catalog. Exact run options and artifacts remain pending verified source manifests.

## Artifact manifest

Repeat for A and B: exact release; source root; client jar hash; CLI mode; mapping coordinate/build/path/hash; bridge mapping path/hash if any; mapped jar hash; cited source relative paths/hashes; cited resource jar entry names/hashes; required external data and its provenance. For a published unobfuscated release, mark mapping and mapped-jar fields not applicable and record that the original client jar was decompiled. Use SHA-256 and record any publisher-provided hashes separately.

## Correspondence and call order

Repeat per logical role: A class/member descriptor -> B class/member descriptor; source anchors; rename/split/replacement evidence; callers; direct dependencies; read/write state and execution order. No unresolved guessed names.

## Coverage ledger

## Track declaration and source gate

This is a source-only track. No Minecraft Wiki, MCPK, release notes, previous mod implementation, Java change classes, or runtime/gameplay validation are evidence. The historical implementation remains unread during discovery. Canonical shared source root: `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07`; the source owner holds the generation lock. At this checkpoint the root exposes ready releases 1.8.9, 1.9.4, 1.10.2, 1.11.2, 1.12.2 and 26.2 only. Exact 1.21.10 and 1.21.11 readiness JSONs have not been published; the lock is in use. Presence/absence of directories is not accepted as readiness. Do not compare until pair-specific JSONs identify exact release IDs, namespace, source/artifact manifest hashes and method diagnostics; verify every cited hash and relevant source body after publication. The local resolver exact-matches release IDs first, but if an ID is not exact it may select the newest release with that prefix; requested/resolved IDs must therefore be checked in both JSON and decompiler logs. Mapping selection must be explicit/aligned. The source saver excludes jar resources, so relevant data must be extracted from the original client jar and independently hashed. Its required-class checks only establish presence of a few source filenames; they do not establish completeness or clean method bodies. Relevant decompiler diagnostics and method bodies are mandatory.

## Pair correspondence and navigation inventory

Pending until verified source publication. Resolve actual class names, descriptors, inheritance and callsites independently per version. Planned entry/state flow: client input sampling and local tick → player tick and travel dispatch → living travel/jump/move-relative → entity movement/box collision/support callbacks → world/shape/fluid queries → registered block/fluid movement providers → attributes/effects/enchantments/equipment/resource data → packet and external velocity/position writers. For each path record call order and read/write state including input history, yaw, position, velocity, bounding box, pose/dimensions, on-ground/horizontal/vertical collision flags, fluid contact, support block, jump/sprint timers and movement attributes. Every discovered helper, override, constructor/default, resource/tag and producer/consumer enters the dependency queue. Old partial catalogs are not assumed exhaustive.

## Coverage ledger

All rows remain `pending` until exact-pair source evidence and dependency closure are checked. Slice IDs below are navigation units, not a claim that a method was inspected.
- Slice `S1-input-sampling` / stage 1 / keyboard-controller sampling, input object lifetime and previous/current input capture: `pending`; exact A/B members and line/hash evidence pending.
- Slice `S1-local-tick` / stage 1 / local player tick, superclass tick, travel dispatch and pre/post-travel ordering: `pending`; evidence pending.
- Slice `S1-yaw-relative` / stage 1 / yaw-to-motion conversion, diagonal normalization, relative acceleration and input scaling: `pending`; evidence pending.
- Slice `S1-sneak-use` / stage 1 / sneak and item-use input scaling, edge-sneak path and timing: `pending`; evidence pending.
- Slice `S1-sprint` / stage 1 / sprint start/stop gates, timers, transitions, sprint-jump interaction: `pending`; hunger/food-derived gates are observed as vanilla state consumers only; no health/food emulation.
- Slice `S1-jump` / stage 1 / jump input, cooldown, jump delay, auto-jump and jump state capture: `pending`; evidence pending.
- Slice `S1-flight-ride` / stage 1 / flight toggle/input, unstuck behavior and riding gates affecting player: `pending`; evidence pending.
- Slice `S2-pose` / stage 2 / pose selection, swimming/crawling transitions and stored pose state: `pending`; evidence pending.
- Slice `S2-dimensions` / stage 2 / pose/entity dimensions, resize timing, eye height when used by movement/fluid queries: `pending`; evidence pending.
- Slice `S2-player-gates` / stage 2 / player superclass movement gates, blindness/item-use/abilities consumers and field defaults/resets: `pending`; evidence pending.
- Slice `S2-flight-state` / stage 2 / abilities, flight speed, flying/walking state and stored air speed: `pending`; evidence pending.
- Slice `S3-travel-dispatch` / stage 3 / living travel branch selection and dispatch conditions: `pending`; evidence pending.
- Slice `S3-ground-air` / stage 3 / ground/air acceleration, friction, drag and gravity with exact FP order: `pending`; evidence pending.
- Slice `S3-cutoffs` / stage 3 / negligible velocity thresholds, comparisons, normalization and post-travel cleanup: `pending`; evidence pending.
- Slice `S3-jump-impulse` / stage 3 / jump power, sprint jump impulse, yaw trigonometry and velocity writes: `pending`; evidence pending.
- Slice `S3-climb` / stage 3 / climbing detection, clamps, movement and exit velocity behavior: `pending`; evidence pending.
- Slice `S3-water` / stage 3 / water acceleration, drag, gravity, swimming and fluid-height interactions: `pending`; evidence pending.
- Slice `S3-lava` / stage 3 / lava travel acceleration, drag, gravity and collision outcomes: `pending`; evidence pending.
- Slice `S3-glide` / stage 3 / gliding travel and directly consumed movement attributes/state: `pending`; item applicability and data dependencies pending.
- Slice `S3-attributes` / stage 3 / movement speed, jump/gravity/step-related attribute consumers, aggregation/order/defaults: `pending`; evidence pending.
- Slice `S4-move-core` / stage 4 / entity move dispatch, axis ordering, position and velocity updates: `pending`; evidence pending.
- Slice `S4-collision-query` / stage 4 / collision candidate acquisition, shape context/query timing, pose/box dependence: `pending`; evidence pending.
- Slice `S4-step` / stage 4 / step-up eligibility, candidate paths, height comparisons and tie-breaking: `pending`; evidence pending.
- Slice `S4-edge-support` / stage 4 / edge probes, support lookup, grounding and support-block state: `pending`; evidence pending.
- Slice `S4-velocity-collision` / stage 4 / axis cancellation/restitution, collision flags and fall/ground callbacks: `pending`; evidence pending.
- Slice `S4-shape-math` / stage 4 / AABB/voxel shape intersections, clipping, epsilon and iteration behavior: `pending`; evidence pending.
- Slice `S4-callbacks` / stage 4 / block/entity movement callbacks reachable from player move and their order: `pending`; evidence pending.
- Slice `S5-block-registry` / stage 5 / block registrations/default friction, speed/jump factors and shape-provider changes: `pending`; evidence pending.
- Slice `S5-landing-bounce` / stage 5 / slime/bed landing, bounce, support and callback behavior: `pending`; evidence pending.
- Slice `S5-slow-surface` / stage 5 / soul sand, ice and other speed/friction surface providers: `pending`; evidence pending.
- Slice `S5-contact-shapes` / stage 5 / web/honey/powder snow and historical partial-block collision/support shapes: `pending`; evidence pending.
- Slice `S5-climbables` / stage 5 / ladders, vines and other climbable registrations, callbacks and neighbor dependence: `pending`; evidence pending.
- Slice `S5-fluids` / stage 5 / water/lava/bubble columns, flow vectors, fluid heights and push timing: `pending`; evidence pending.
- Slice `S5-neighbors` / stage 5 / neighboring/state-dependent collision and movement providers, including piston displacement path: `pending`; evidence pending.
- Slice `S5-modern-only` / stage 5 / blocks/states absent in A: verify registration/absence and record modern-only applicability; no historical behavior invented: `pending`.
- Slice `S6-effects` / stage 6 / Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace, Blindness consumers and formulas: `pending`; evidence pending.
- Slice `S6-enchantments` / stage 6 / Depth Strider, Soul Speed, Swift Sneak, Frost Walker, Riptide and other registered movement-affecting entries: `pending`; evidence pending.
- Slice `S6-equipment-items` / stage 6 / Elytra, item-use slowdown, equipment slots/components and player movement predicates: `pending`; evidence pending.
- Slice `S6-data-resources` / stage 6 / client jar data, tags, effect/enchantment definitions, registry defaults and referenced values: `pending`; resource hashes and server-supplied boundaries pending.
- Slice `S6-attribute-lifecycle` / stage 6 / attribute modifier application/removal, stacking, timers and equipment/effect lifecycle: `pending`; evidence pending.
- Slice `S7-external-velocity` / stage 7 / client consumers and writers for incoming velocity/position corrections and player knockback/push: `pending`; evidence pending.
- Slice `S7-environment-displacement` / stage 7 / explosions, pistons, launch items, mount/dismount transitions insofar as they write player movement state: `pending`; evidence pending.
- Slice `S7-state-writer-closure` / stage 7 / exhaustive remaining reachable player movement-state writers, callbacks, registrations and dependencies discovered from prior stages: `pending`; evidence pending.
- Slice `X-scope-exclusions` / all stages / health, regeneration, hunger/food/saturation/exhaustion/damage/combat emulation and non-player physics: `not-applicable` by delegated scope; vanilla-state consumption (for example sprint gates) remains covered in its owning slice.
## Dependency queue and blockers

For each: ID; originating slice; precise missing member/resource/question; why it can affect movement; next retrieval action; resolution evidence or blocked reason.

## Finding index

Link each finding with its short behavioral title and confidence. Record discarded candidates and reasons so they are not rediscovered.

## Resume checkpoint

- Last completed slice:
- Next bounded slice and exact files/members to open:
- Outstanding dependencies:
- Current assumptions requiring verification:

## Source audit closure

- Coverage counts by status:
- Unresolved gaps and limits:
- Evidence/hash/correspondence audit:
- Runtime validation: not performed (separate workflow).

Keep all tracked coverage, provenance, correspondence and resume evidence in this file. At handoff, the run folder contains only this file and, when findings exist, one file per finding under `findings/`.
