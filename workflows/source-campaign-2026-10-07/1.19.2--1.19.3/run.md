# Discovery: 1.19.2 to 1.19.3

- Status: active
- Scope: direct client player movement; older A = 1.19.2; newer B = 1.19.3
- Repository revision and start date: base `002137b227676caea77f6832b9f4c8d0b6200bff`; source audit started 2026-10-07 (Europe/Vienna)
- Selected naming namespace, CLI mode per side and alignment evidence: pending source-owner publication and readiness JSON verification; request is exact 1.19.2 and 1.19.3 with one aligned namespace, preferably Feather if exact mappings resolve
- Source preparation command and log: source owner owns the shared decompiler; awaiting published exact-release readiness records. This track will not run or write the shared decompiler.
- Toolchain/decompiler/remapper versions and options: pending readiness/artifact metadata
- Track declaration: source-only; no Minecraft wiki/MCPK/wiki-derived audit; no mod implementation/code inspection; no runtime Java changes

## Artifact manifest

### A — 1.19.2

- Exact requested/resolved release: 1.19.2 / pending readiness JSON
- Shared source root: `build/movement-campaign-2026-10-07/ready/1.19.2/<namespace>/` (not yet published at inventory start)
- Client jar identity/SHA-256: pending readiness manifest
- Naming namespace/CLI mode: pending
- Mapping coordinate/build/file/SHA-256: pending
- Mapped jar SHA-256: pending
- Cited Java source paths/SHA-256: pending source verification
- Cited resource entries/SHA-256: pending source/resource inspection
- Required external data/provenance: pending data/resource dependency inventory

### B — 1.19.3

- Exact requested/resolved release: 1.19.3 / pending readiness JSON
- Shared source root: `build/movement-campaign-2026-10-07/ready/1.19.3/<namespace>/` (not yet published at inventory start)
- Client jar identity/SHA-256: pending readiness manifest
- Naming namespace/CLI mode: pending
- Mapping coordinate/build/file/SHA-256: pending
- Mapped jar SHA-256: pending
- Cited Java source paths/SHA-256: pending source verification
- Cited resource entries/SHA-256: pending source/resource inspection
- Required external data/provenance: pending data/resource dependency inventory

## Correspondence and call order

No class/member correspondence is certified before source readiness is verified. Resolve every role against each exact source tree, including class hierarchy, descriptors, callers, state producers/consumers and order. The following navigation seed roles are an inventory plan only, not evidence of equivalence:

1. Local client player/input: input sampling, tick/super-tick/travel ordering; local player, input, keyboard input, options/key bindings only when reachable.
2. Player state/gates: pose/dimensions/eye height, sprint/jump timers, abilities/flight, swimming/crawling, active item, edge sneak, stored air speed, attributes/effects/equipment. Hunger/food state is excluded except a vanilla state consumer directly governing sprint entry/continuation.
3. Living integration: travel dispatch, ground/air/water/lava/gliding branches, acceleration, gravity/drag, velocity cutoffs, jump/sprint-jump, climb, swimming, post-travel updates and direct math/vector helpers.
4. Entity movement/collision: move/bounding-box updates, axis order, step candidates/ties, edge/support probes, collision flags and velocity cancellation/restitution, fluids, callbacks, shape/AABB/world-query helpers, entity resize and query timing.
5. Blocks/fluids: all registered/overridden player-reachable movement callbacks and collision shapes, friction/speed/jump properties, neighbor/state conditions, shape providers, fluids/flow/push/bubble columns; registry and relevant tags/resources.
6. Effects/enchantments/attributes/equipment: movement consumers -> attributes/helpers -> modifiers/registrations -> conditions/slots/tags/data; include all direct movement modifiers and document server-synchronized/external data limits.
7. External influences: received corrections/velocity, player-affecting push/knockback/explosion/piston/launch-item paths and mount transitions; exclude independent nonplayer movement simulation.

Health, regeneration, hunger/food, saturation, exhaustion, damage and combat emulation are excluded; inspect a vanilla state consumer only where it directly affects player movement, such as a sprint gate. Modern-only blocks/features do not acquire old behavior. Non-player physics is out of scope. Preserve exact floating-point operation order and intentional quirks.

## Coverage ledger

Each entry is a bounded coverage family to split into source-backed method/resource slices after exact correspondence is established. All remain pending; no no-difference inference is made from source trees being absent or from prior reports.

| ID | Stage / behavior to inventory and compare | Status | A/B evidence | Dependency closure / next action |
|---|---|---|---|---|
| S1-input | 1 — input producer, sampling, yaw conversion, diagonal scaling, sneak/use scaling, timers and local tick ordering | pending | Exact 1.19.2/1.19.3 readiness/source pending | Resolve local-player/input signatures, full callers and field read/write order |
| S2-player-state | 2 — pose/dimension/eye-height, movement flags, sprint/jump gates/timers, flight, swimming/crawling, active item and edge-sneak state; food only at direct vanilla movement consumer | pending | Exact sources pending | Resolve superclass methods, constructors/defaults and all movement-state writers; exclude health/food mechanics themselves |
| S3-travel-dispatch | 3 — travel dispatch and branch selection, before/after travel updates | pending | Exact sources pending | Resolve local player -> living travel -> helpers and relevant flags |
| S3-ground-air | 3 — ground/air acceleration, friction, gravity/drag, negligible-velocity threshold, ground speed and post-travel math | pending | Exact sources pending | Resolve friction/speed attributes, constants, vector helpers and operation ordering |
| S3-jump | 3 — jump gates/power, sprint-jump impulse, cooldowns, auto-jump and jump state writes | pending | Exact sources pending | Trace all jump-field writers, movement attributes/effects and callers |
| S3-climb-swim | 3 — climb clamps, water/lava travel, swimming and fluid-specific velocity/update paths | pending | Exact sources pending | Trace fluid state/height/push and relevant attributes/effects |
| S3-glide | 3 — gliding/elytra travel and movement-relevant state transitions | pending | Exact sources pending | Trace equipment/eligibility and all direct player-state consumers |
| S4-move-core | 4 — entity movement entry, bounding-box/position updates, axis order, collision/ground flags and velocity response | pending | Exact sources pending | Trace move callers and collision query/helper closure |
| S4-step-edge | 4 — step-up candidates/ties, edge sneak probes, support lookup and repeated movement/tick timing | pending | Exact sources pending | Trace pose/shape-dependent query inputs and relevant helper writers |
| S4-shapes-query | 4 — AABB/voxel shape operations, world collision lookup/filtering, shape context and entity dimensions | pending | Exact sources pending | Enumerate called shape/query implementations; no rendering/worldgen scope |
| S4-callbacks | 4 — collision/landing/block callbacks, velocity cancellation/restitution, callback timing/order | pending | Exact sources pending | Inventory reachable block overrides and full invocation sites |
| S5-block-registration | 5 — registered blocks/states and movement properties: friction, speed, jump factors, defaults and state/neighbor conditions | pending | Exact sources and jar resources pending | Enumerate registry providers/overrides, tags, relevant properties and resource defaults |
| S5-block-shapes | 5 — historical player-reachable partial/slow/bounce/climbable block shapes and shape providers; modern-only additions dispositioned | pending | Exact sources and jar resources pending | Enumerate all overriding subclasses and registrations; verify vanilla registry presence on both sides |
| S5-fluid-data | 5 — fluid registration/defaults, height/flow vectors, push, bubble columns and contact rules | pending | Exact sources and jar resources pending | Follow Java producers and resource/tag data; identify synchronized/external input |
| S6-effects-attributes | 6 — effect registrations/application/removal, modifiers, operation order and movement consumers (Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace, Blindness and any others found) | pending | Exact sources and jar resources pending | Trace consumer-to-registration and reverse chain, defaults, timers and data |
| S6-enchantments-equipment | 6 — Depth Strider, Soul Speed, Swift Sneak, Frost Walker, Riptide, Elytra/use-item slowdown, other movement items/equipment and data-driven conditions | pending | Exact sources and jar resources pending | Inspect jar definitions/tags/slots/conditions, server-provided boundaries, and player-only integration |
| S7-external-velocity | 7 — incoming player position/velocity corrections and player-affecting knockback/push/explosion/piston/launch-item writers | pending | Exact sources pending | Follow network/client consumers and vanilla writers; isolate external values from client computation |
| S7-mount-transition | 7 — player mount/dismount and transition state affecting local movement | pending | Exact sources pending | Inspect only player transition path and its inputs; no mount/entity simulation |
| S7-closure | 7 — revisit changed helpers, unresolved state writers, callbacks, registrations/resources and cross-mechanic interactions across stages 1–7 | pending | Exact sources pending | Close dependency queue only after all parent slices have source-backed outcomes |

## Dependency queue and blockers

- D0 — exact pair publication: exact 1.19.2 and 1.19.3 ready JSONs, cited SHA-256 source/artifact manifests, namespace/mapping metadata and relevant movement-method diagnostics. Why: comparison evidence requires provenance and whole relevant bodies. Next action: wait for source owner publication; read the actual readiness JSONs and verify IDs, namespace, hashes and diagnostics. State: open.
- D1 — method-level correspondence: all movement navigation roles, hierarchy and callers on both sides. Why: aligned names do not guarantee same class/member. Next action: resolve after D0, then link dependencies to parent slices. State: open.
- D2 — jar resource inventory: movement-relevant block/fluid tags/defaults, enchantment/effect data and registry resources absent from Java source saver. Why: resource-only changes can affect movement. Next action: inspect version-matched original jar entries after D0 and hash cited values. State: open.
- D3 — client/server/external boundary: mark synchronized attributes, datapack/server-supplied values and received velocity/corrections. Why: client source cannot prove server rules or input provenance. Next action: identify concrete consumers and source boundary per slice. State: open.

No blocker is inferred from source-owner queueing. If a relevant body is damaged, request exact-release bytecode/source evidence through commentary; never generate or edit the shared source artifacts in this track.

## Finding index

No findings certified yet. Each discovered behavioral delta will receive one independent `findings/<id>.md`; absence of files is not equivalence. Discarded candidates and decompiler-only differences will be recorded here with the checked dependency path and reason.

## Resume checkpoint

- Last completed slice: initial source-independent scope/navigation inventory only; no source comparison complete.
- Next bounded slice: verify exact 1.19.2 and 1.19.3 readiness JSONs and manifests, confirm aligned namespace and relevant body diagnostics, then resolve Stage 1 input/tick correspondence.
- Outstanding dependencies: D0–D3.
- Assumptions requiring verification: common Feather mapping family is only a candidate; no class/member equivalence, no source equivalence, and no release-boundary claim is established.

## Source audit closure

- Coverage counts by status: pending 20; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0. These are broad initial families and will be split/refined into method/resource-level slices.
- Unresolved gaps and limits: all exact-source comparison, dependency closure, registration/resource data, findings and method-level correspondence remain open pending source readiness and subsequent audit.
- Evidence/hash/correspondence audit: not yet performed for either release.
- Runtime validation: not performed (separate workflow).
