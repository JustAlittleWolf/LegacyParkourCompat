# Discovery: 1.19.2 to 1.19.3

- Status: active
- Scope: direct client player movement; older A = 1.19.2; newer B = 1.19.3
- Repository revision and start date: source comparison baseline `002137b227676caea77f6832b9f4c8d0b6200bff`; started 2026-10-07 (Europe/Vienna); discovery schema commits `40c34f5`, `2422192`, `fba28fa`
- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap/Mojmap is the campaign namespace for both releases (Mojmap from 1.14.4 onward); exact resolved mapping artifacts and IDs await readiness verification
- Source preparation owner / command / log / readiness marker: shared source owner (worktree `3e2d`); command `gradlew.bat decompileMinecraft --versions=1.19.2,1.19.3,1.19.4 --mappings=mojmap --decompiler-heap=4G --output-root=.../staging/mojmap-1.19.2-to-1.19.4-45d0418942a144298b19fb4ac74ff06a --cache-directory=.../artifacts`; successful log `build/movement-campaign-2026-10-07/staging/mojmap-1.19.2-to-1.19.4-45d0418942a144298b19fb4ac74ff06a/gradle.full.log`; exact readiness markers verified below
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1; Java 25.0.3+9-LTS; Vineflower 1.12.0; TinyRemapper 0.14.1; Mapping-IO 0.9.1; ASM 9.10.1; Gson 2.14.0; heap 4G
- Discovery author(s): source worker for this pair
- Independent reviewer (must differ from discovery authors): pending coordinator assignment

## Artifact manifest

### A — 1.19.2

- Exact requested/resolved release: 1.19.2 / pending exact readiness JSON
- Source root: `build/movement-campaign-2026-10-07/ready/1.19.2/mojmap/` (4,480 Java files)
- Original client jar SHA-256: `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db`
- CLI mode/namespace: `mojmap` / Mojmap official names
- Mapping coordinate/build/file/SHA-256: official Mojang 1.19.2 `client_mappings.txt`; `c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76`
- Bridge mapping: not used
- Remapped jar SHA-256: `a257c4c97ceac50fbc069dc6051dff5f0263716547ede05e85c44d675ade592f`
- Cited Java source paths/SHA-256: member ranges are listed below; aggregate `mojmap.sources.sha256` is `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`; each cited body hash was checked against the manifest
- Cited resource entries/SHA-256: pending resource inspection
- Required external data and provenance: pending dependency inventory

### B — 1.19.3

- Exact requested/resolved release: 1.19.3 / pending exact readiness JSON
- Source root: `build/movement-campaign-2026-10-07/ready/1.19.3/mojmap/` (4,618 Java files)
- Original client jar SHA-256: `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6`
- CLI mode/namespace: `mojmap` / Mojmap official names
- Mapping coordinate/build/file/SHA-256: official Mojang 1.19.3 `client_mappings.txt`; `f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b`
- Bridge mapping: not used
- Remapped jar SHA-256: `3be15cd54092cbac9853a67445a42b38d5c3b0d61503bd79b0d76589357d871e`
- Cited Java source paths/SHA-256: member ranges are listed below; aggregate `mojmap.sources.sha256` is `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`; each cited body hash was checked against the manifest
- Cited resource entries/SHA-256: pending resource inspection
- Required external data and provenance: pending dependency inventory

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending; only inventory setup is committed, not a source freeze
- Evidence inventory and finding IDs included at freeze: none yet
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; no old/mod implementation or either wiki/audit read
- Source/mapping hashes covered by freeze: pending

## Correspondence and call order

No class/member correspondence is certified until both exact readiness JSONs and their cited manifests are verified. Resolve class/member descriptors, inheritance, callers, body ranges, state read/write edges and execution order for each role on both sides. The navigation plan covers: local input sampling and local/super tick order; pre-travel predicates/state updates; travel dispatch and every reachable branch; post-travel work/callbacks; pose/dimensions/eye height and movement state writers; collision, support, shapes and callbacks; block/fluid registrations/data; effect/attribute/enchantment/equipment chains; externally supplied player movement inputs and consumers.

Health, regeneration, hunger/food, saturation, exhaustion, damage/combat simulations, and independent non-player physics are out of scope. Direct reads of vanilla state remain in scope only as movement predicate inputs. Modern-only blocks/features do not acquire older behavior. Preserve exact casts, operation order, floating-point behavior and quirks.

## Required source inventories

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S1-input,S3-travel-dispatch,S3-ground-air,S3-jump,S3-climb-swim,S3-glide; evidence=pending exact sources, method ranges and callers
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S2-player-state,S4-move-core,S4-step-edge; evidence=pending both-side writer/consumer map
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S4-move-core,S4-step-edge,S4-shapes-query,S4-callbacks,S5-block-shapes; evidence=pending exact methods, overrides, registrations and neighbors
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S5-block-registration,S5-block-shapes,S5-fluid-data; evidence=pending Java and jar-resource inventories
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and applications/removals/conditions: status=pending; slice_ids=S6-effects-attributes,S6-enchantments-equipment; evidence=pending consumer-to-registration chains and resources
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=S7-external-velocity,S7-mount-transition; evidence=pending packet/state writer/consumer ranges
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=pending scope/consumer audit; direct vanilla-state reads will be listed as movement inputs only

## Coverage ledger

These are initial bounded behavior families and all are open. After exact source publication, split any family spanning multiple methods into method/body-range slices with both-side hashes and explicit producer -> consumer edges. No row below implies source equivalence or completion.

### Slice S1-input: Input producer and local tick ordering

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: input sampling, local player tick/super-tick/travel call order, yaw conversion, diagonal/sneak/use scaling, jump/sprint timers and riding/flight gates; exact method boundaries pending.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: pending correspondence; track input fields, timers and travel flags.
- Parent slices / dependencies / closure evidence: source identity and exact call graph pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): no source comparison yet; avoid claims from absent/unverified trees.
- Finding IDs or checked absence/replacement path: none yet

### Slice S2-player-state: Pose, dimensions and movement gates

- Inventory ID(s): INV-STATE, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: pose/size/eye-height transitions, movement-relevant field initialization/reset, flight/swim/crawl, sprint/jump predicates, edge sneak and active item; inspect excluded state only at direct vanilla movement consumers.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: constructors/defaults/transitions -> movement predicates and collision/fluid query dimensions; pending exact field map.
- Parent slices / dependencies / closure evidence: S1-input; exact writer/reader closures pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): source audit pending; excluded health/food producer systems will not become movement findings.
- Finding IDs or checked absence/replacement path: none yet

### Slice S3-travel-dispatch: Travel branch selection and post-travel order

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: dispatch guards, branch order, entry/exit work, and post-travel state updates/callbacks.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: tick flags and attributes -> branch predicates and post-travel writes; pending exact correspondence.
- Parent slices / dependencies / closure evidence: S1-input, S2-player-state; all branches pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): full dispatch and call order not yet sourced.
- Finding IDs or checked absence/replacement path: none yet

### Slice S3-ground-air: Ground/air acceleration, friction and cutoffs

- Inventory ID(s): INV-TICK, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: full ground/air calculation incl. friction/gravity/drag, velocity thresholds, clamps, constants and post-travel math; exact body ranges pending.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: movement speed/friction sources -> acceleration; velocity and ground flags -> branch/cutoff consumers; pending.
- Parent slices / dependencies / closure evidence: S3-travel-dispatch, S5-block-registration, S6-effects-attributes.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): no arithmetic conclusion until paired bodies and dependencies are verified.
- Finding IDs or checked absence/replacement path: none yet

### Slice S3-jump: Jump gates, power and sprint-jump impulse

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: jump predicates, jump power, impulse, cooldown, auto-jump and jump state writes.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: input/timers/ground/attributes/effects -> jump; jump writes -> travel; pending exact edges.
- Parent slices / dependencies / closure evidence: S1-input, S2-player-state, S3-travel-dispatch, S6-effects-attributes.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): no source comparison yet.
- Finding IDs or checked absence/replacement path: none yet

### Slice S3-climb-swim: Climb, water/lava and swimming branches

- Inventory ID(s): INV-TICK, INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: travel branch conditions, clamps, acceleration/drag, fluid-height/push reads and post-branch writes.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: fluid state/properties and attributes -> player travel; pending.
- Parent slices / dependencies / closure evidence: S3-travel-dispatch, S5-fluid-data, S6-effects-attributes.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): fluids and player-only interactions require exact Java/resource evidence.
- Finding IDs or checked absence/replacement path: none yet

### Slice S3-glide: Elytra/gliding movement

- Inventory ID(s): INV-TICK, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: gliding branch calculations, eligibility transitions, equipment and post-travel state writes.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: equipment/pose/flags -> glide dispatch and calculations; pending.
- Parent slices / dependencies / closure evidence: S3-travel-dispatch, S2-player-state, S6-enchantments-equipment.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): exact reachability and arithmetic are not established.
- Finding IDs or checked absence/replacement path: none yet

### Slice S4-move-core: Entity movement, position and collision response

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: movement entry, box/position updates, axis order, collision/ground flags and velocity cancellation/restitution.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: travel velocity -> movement/query -> position, box and flags; pending exact writers.
- Parent slices / dependencies / closure evidence: travel branches; query/shape helpers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): no whole-movement no-difference claim permitted.
- Finding IDs or checked absence/replacement path: none yet

### Slice S4-step-edge: Step candidates, edge probing and support

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: step-up alternatives/ties, edge sneak probes, support lookup and repeated per-tick movement timing.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: pose/dimensions/velocity -> probe inputs; support/collision results -> flags and movement.
- Parent slices / dependencies / closure evidence: S2-player-state, S4-move-core, S4-shapes-query, S5-block-shapes.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): pending both-side shape/query closure.
- Finding IDs or checked absence/replacement path: none yet

### Slice S4-shapes-query: AABB/voxel shape and world query path

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: called AABB/shape operations, world collision lookup/filtering, shape context, dimensions and candidate/tie selection.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: player box/state/shape context -> collision queries -> selected movement response; pending.
- Parent slices / dependencies / closure evidence: S4-move-core, S4-step-edge; all called helpers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): method correspondence and relevant closure not yet available.
- Finding IDs or checked absence/replacement path: none yet

### Slice S4-callbacks: Landing, collision callbacks and timing

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: player-reachable callbacks, invocation conditions/order, support/landing velocity changes and relevant block overrides.
- A evidence: pending member-range comparison
- B evidence: pending member-range comparison
- State producers/writers -> consumers/readers: movement query/collision result -> callback -> player velocity/flags; pending exact implementations.
- Parent slices / dependencies / closure evidence: S4-move-core and S5 block registration/subclass inventories.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): base and overriding callback bodies not yet enumerated.
- Finding IDs or checked absence/replacement path: none yet

### Slice S5-block-registration: Movement properties and registrations

- Inventory ID(s): INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: registered blocks/states, friction/speed/jump properties, defaults and state/neighbor conditions.
- A evidence: pending source/resource ranges
- B evidence: pending source/resource ranges
- State producers/writers -> consumers/readers: registration/default/neighbor data -> player movement consumer; pending registry inventory.
- Parent slices / dependencies / closure evidence: S3-ground-air, S4-callbacks; exact consumer and registries pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): no absence claim from failed symbol searches.
- Finding IDs or checked absence/replacement path: none yet

### Slice S5-block-shapes: Shape providers, overrides and neighboring blocks

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: every reachable movement-relevant registered shape override/provider, context inputs, support/neighbors and modern-only block disposition.
- A evidence: pending source/resource ranges
- B evidence: pending source/resource ranges
- State producers/writers -> consumers/readers: block registration/state/neighbors/context -> shape provider -> support/collision query; pending.
- Parent slices / dependencies / closure evidence: S4-step-edge, S4-shapes-query; complete override/registration enumerations pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): modern-only blocks will not receive emulated behavior; prove version registries before classification.
- Finding IDs or checked absence/replacement path: none yet

### Slice S5-fluid-data: Fluid state, flow and push data

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: registrations/defaults, height/flow vector calculation, contact/push/bubble behavior and resource-backed data.
- A evidence: pending source/resource ranges
- B evidence: pending source/resource ranges
- State producers/writers -> consumers/readers: fluid data/contact -> player flags/velocity/travel; pending.
- Parent slices / dependencies / closure evidence: S3-climb-swim, S4-move-core; server/external boundaries pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Java and client-jar resource comparison needed.
- Finding IDs or checked absence/replacement path: none yet

### Slice S6-effects-attributes: Movement effects and attribute chains

- Inventory ID(s): INV-MODIFIERS, INV-TICK
- Exact behavior boundary and enclosing guards/order checked: movement consumers -> attributes/helpers -> modifier aggregation/application/removal/defaults -> registrations for effects and movement attributes.
- A evidence: pending source/resource ranges
- B evidence: pending source/resource ranges
- State producers/writers -> consumers/readers: effect/application/equipment/server-synced values -> modifier state -> player travel/sprint/jump consumers; pending.
- Parent slices / dependencies / closure evidence: S2-player-state, S3-ground-air, S3-jump, S3-climb-swim; explicit effect inventory pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace, Blindness and all other discovered movement consumers require disposition.
- Finding IDs or checked absence/replacement path: none yet

### Slice S6-enchantments-equipment: Enchantments, items and gear

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: movement enchantment/item consumers, formulas, applicability/slots, equipment/use-item slowdown and resource/tag conditions.
- A evidence: pending source/resource ranges
- B evidence: pending source/resource ranges
- State producers/writers -> consumers/readers: registration/tags/equipment/applications -> attributes/helpers/pose/travel consumer; pending.
- Parent slices / dependencies / closure evidence: S2-player-state, S3-glide, S3-climb-swim, S6-effects-attributes.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): explicitly inspect Depth Strider, Soul Speed, Swift Sneak, Frost Walker, Riptide, Elytra and other discovered entries; record synchronized/server data boundary.
- Finding IDs or checked absence/replacement path: none yet

### Slice S7-external-velocity: External player movement inputs

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: player consumers of corrections, velocities, push/knockback, explosions, piston displacement and launch items; no independent nonplayer simulation.
- A evidence: pending source ranges
- B evidence: pending source ranges
- State producers/writers -> consumers/readers: packet/server/external producer -> player state writer -> later movement consumer; pending.
- Parent slices / dependencies / closure evidence: S4-move-core and stage-3 consumers; exact network/client path pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): distinguish client-computed behavior from externally supplied values.
- Finding IDs or checked absence/replacement path: none yet

### Slice S7-mount-transition: Player mount and dismount state

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: local player mount/dismount transition path and movement-state reset/gate effects only.
- A evidence: pending source ranges
- B evidence: pending source ranges
- State producers/writers -> consumers/readers: transition/correction inputs -> player flags/velocity/position -> local tick gates; pending.
- Parent slices / dependencies / closure evidence: S1-input, S2-player-state; no mount simulation in scope.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): player-only method/caller closure not yet inspected.
- Finding IDs or checked absence/replacement path: none yet

### Slice S7-closure: Cross-stage state writers and dependencies

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT, INV-MODIFIERS, INV-EXTERNAL, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: revisit every unresolved writer/consumer, callback, registration, resource and newly discovered dependency; enumerate cross-mechanic interactions after parent slices close.
- A evidence: pending source ranges and inventories
- B evidence: pending source ranges and inventories
- State producers/writers -> consumers/readers: full two-sided player-state dependency graph not yet collected.
- Parent slices / dependencies / closure evidence: all listed stages and dependencies; this is the final closure slice.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): cannot close until bounded child slices and dependencies are terminal.
- Finding IDs or checked absence/replacement path: none yet

## Dependency queue and blockers

- D0 — readiness/provenance: resolved. IDs and official metadata are exactly 1.19.2/1.19.3, namespace `mojmap`; both ready statuses are `ready`; physical Java counts and manifest entries match (4,480/4,618); ready/provenance/source/artifact/diagnostic manifest hashes match; original client jar, mapping file and remapped-jar hashes match both artifact manifests; 21 relevant diagnostic anchors per side have no error/warning/missing/failed/exception text.
- D1 — paired member correspondence and full player tick graph. Why: names do not establish correspondence or reachability. Next action: after D0, resolve bounded body ranges, callers, writers/readers and closure. Owner: discovery author. State: open.
- D2 — version-matched resources/tags/defaults. Why: Java source saver omits resources. Next action: inspect relevant jar entries and hashes once provenance is verified. Owner: discovery author; server/datapack provision if identified. State: open.
- D3 — external/synchronized movement inputs. Why: client sources cannot prove server rules or value origin. Next action: trace concrete player consumer and label each producer boundary. Owner: discovery author. State: open.
- D4 — independent coverage reviewer. Why: source completion requires an author-independent re-walk. Next action: coordinator assignment after freeze. Owner: coordinator. State: pending; does not block source comparison.


The exact source pair is published and verified. No source-generation blocker remains; do not rerun or write the shared decompiler.

## Finding index

No source-confirmed findings yet. Missing findings folder means none are certified, not equivalence. Record every discarded candidate with inspected dependency path and reason after source verification.

## Resume checkpoint

- Last completed slice: source-independent scope and required-inventory setup only.
- Next bounded slice and exact files/members/body ranges to open: first verify the published 1.19.2/Mojmap and 1.19.3/Mojmap JSONs, exact IDs, SHA-256 manifests and diagnostics; then resolve the Stage 1 local-player input/tick entry methods and callers.
- Outstanding dependencies and owners: D0 source owner; D1–D3 discovery; D4 coordinator.
- Current assumptions requiring verification: both pair endpoints support the selected Mojmap namespace/build; no member correspondence, equivalence or changed behavior has been source-verified.

## Implementation reconciliation

- Reconciliation status: pending (deferred until blind-discovery freeze)
- Repository revision inspected: none; old/current mod implementation has not been opened
- Finding -> implementation disposition/evidence: pending; implementation read is not authorized before freeze
- Existing implementation without a frozen source finding: pending
- Coverage gaps routed back to discovery slices: none yet

## Independent source audit

- Reviewer: pending; must differ from discovery author
- Status: pending
- Inventories and call-chain ranges re-walked: none yet
- Concrete missed-slice routes: pending
- Misses routed to slice/finding IDs and owners: pending
- Reviewer evidence / date: pending

## Source audit closure

- Coverage counts by status: pending 20 initial families; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0. These will be refined to member/body-range slices before dispositions.
- Required inventory status and evidence: INV-TICK pending; INV-STATE pending; INV-COLLISION pending; INV-WORLD-MOVEMENT pending; INV-MODIFIERS pending; INV-EXTERNAL pending; INV-EXCLUSIONS pending.
- Open dependencies: D0, D1, D2, D3, D4
- Unresolved gaps and limits: both exact source trees, provenance/hash checks, all method-level correspondence/coverage, resources, findings, freeze and independent audit remain pending.
- Evidence/hash/correspondence audit: not yet performed
- Static schema check: canonical checker exited 0 on 2026-10-07; it reported active structurally valid and made no source-completion claim.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from source or static checks)
