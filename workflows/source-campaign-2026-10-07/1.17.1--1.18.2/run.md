# Discovery: 1.17.1 to 1.18.2

- Status: active
- Scope: source-only comparison of direct client player movement; older A = 1.17.1; newer B = 1.18.2. Explicitly excluded: health, regeneration, hunger/food/saturation/exhaustion/damage/combat emulation; non-player physics; historical behavior for modern-only blocks/features.
- Repository revision and start date: base 002137b227676caea77f6832b9f4c8d0b6200bff; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: pending validated source-owner handoff. Candidate is common Feather; no mapping conclusion until exact outputs and artifacts are verified.
- Source preparation command and log: pending. This worker is a source reader; source owner alone may write/regenerate shared decompiler outputs.
- Toolchain/decompiler/remapper versions and options: pending source-owner artifact manifest.
- Track declaration: source-only discovery; no wiki/MCPK, release-note, mod-implementation, or runtime evidence used. Runtime validation is deferred.

## Artifact manifest

Pair sources are not yet available in this checkout. The shared source area currently contains only a ready marker for 26.2/unobfuscated, which is not relevant to this pair. Both source roots, exact resolved IDs, client jar identities/hashes, mapping artifacts/builds, remapped jar hashes, source manifests and decompiler diagnostics remain pending. Do not treat directory existence or prior catalogs as proof. Record and verify the exact pair markers and their referenced hashes before citing source.

## Correspondence and call order

Pending source readiness. Resolve class/member correspondence from both sides with inheritance, callers, registrations and state producers/consumers. Planned anchors follow `workflows/movement-discovery/source-navigation.md`: local client input/tick, player state, living travel/jump, entity move/collision, block/fluid properties and callbacks, movement effects/attributes/equipment, and external velocity/position writers. No class/member correspondence has yet been asserted.

## Coverage ledger

All rows below are pending source acquisition and method-level inventory. They are bounded discovery units, not evidence that the behavior is unchanged. Expand each into method-level slices and dependency rows after correspondence is established.

### Stage 1 — Local input and tick ordering

- Slice ID: S1.1; behavior: input sampling and current/previous input capture.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S1.2; behavior: yaw-to-relative-motion conversion, diagonal normalization and item-use/sneak scaling.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S1.3; behavior: local tick, superclass tick and travel call order; input and flag update timing.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- S1.4 / sprint start/stop, timers, eligibility consumers, and item-use interaction: pending; exclude hunger/food emulation itself.
- Slice ID: S1.5; behavior: jump input, jump delay/cooldown, auto-jump probes and flight toggles.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S1.6; behavior: riding gates, vehicle input dispatch and local-player movement handoff.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.

### Stage 2 — Player-specific state and gates

- Slice ID: S2.1; behavior: pose choice, dimensions, eye height, resize timing and collision-query effects.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S2.2; behavior: swimming/crawling state and transitions.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S2.3; behavior: flight abilities, flight speed and movement-state reset timing.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S2.4; behavior: blindness and active-item movement gates; inspect only vanilla state consumers for excluded food/hunger state.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S2.5; behavior: crouch edge-sneaking state and stored air-speed updates.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.

### Stage 3 — Living movement integration

- Slice ID: S3.1; behavior: travel dispatch and ground/air branch guards.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S3.2; behavior: ground acceleration, friction selection, air acceleration, gravity and drag.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S3.3; behavior: negligible-velocity thresholds, clamps, casts and operation order.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S3.4; behavior: ground jump power and sprint-jump impulse.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S3.5; behavior: climbing, ladder/vine clamps and associated flags.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S3.6; behavior: water/lava travel, swimming acceleration, drag, fluid depth and enchantment inputs.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S3.7; behavior: fall-flying/elytra travel, lift, trigonometry, drag and exit state.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S3.8; behavior: travel post-processing, fall distance, collision flags and state updates.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S3.9; behavior: movement attributes and helpers consumed by travel, with defaults, modifiers and exact arithmetic closure.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.

### Stage 4 — Entity movement and collision

- Slice ID: S4.1; behavior: bounding-box movement entry, requested delta handling and position synchronization.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S4.2; behavior: collision-shape gathering, axis resolution order and tie-breaking.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S4.3; behavior: step-up candidates, comparison and final selected movement.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S4.4; behavior: edge support probes, sneaking reduction and on-ground determination.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S4.5; behavior: velocity cancellation/restitution and collision thresholds.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S4.6; behavior: fluid contact, height/push calculations and callback/tick order.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- S4.7 / block/entity collision callbacks reachable from player movement: pending; other entities' independent simulation excluded.
- Slice ID: S4.8; behavior: client packet handlers and external position/velocity writers that change local player movement.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.

### Stage 5 — Blocks and fluids producing movement inputs

- Slice ID: S5.1; behavior: registrations and properties for applicable historical friction, speed, jump and climb blocks.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S5.2; behavior: historical collision shapes for blocks present in A (slabs, stairs, fences, walls, trapdoors, doors, snow layers, farmland/path and related support shapes).
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S5.3; behavior: slime and bed landing/bounce callbacks and support dependencies.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S5.4; behavior: ice variants, soul sand and other movement-factor blocks.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S5.5; behavior: cobweb/contact slowdown and other block-inside effects.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S5.6; behavior: ladders, vines and climbable registration/shape/callbacks.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S5.7; behavior: water/lava and bubble-column movement interactions, flow and height helpers.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S5.8; behavior: moving piston displacement and player callback ordering.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S5.9; behavior: state-, neighbor- and context-dependent shape/callback behavior, plus checked additions/removals and modern-only disposition.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.

### Stage 6 — Effects, enchantments, attributes and equipment

- Slice ID: S6.1; behavior: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace and Blindness consumers and application data.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S6.2; behavior: Depth Strider, Soul Speed, Frost Walker, Riptide and equipment applicability/conditions.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S6.3; behavior: Elytra and active-item/use slowdown movement inputs.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S6.4; behavior: movement attribute registration, aggregation order, operations, defaults, modifiers and reset/removal paths.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S6.5; behavior: matching client-jar resources, tags, enchantment/effect data and referenced defaults.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S6.6; behavior: server-synchronized/external movement inputs and Frost Walker world-mutation boundary.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S6.7; behavior: exhaustive movement-relevant registrations and entries beyond the named seeds.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.

### Stage 7 — External influences and dependency closure

- Slice ID: S7.1; behavior: player knockback, push, explosions, launch items and relevant local velocity writers.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S7.2; behavior: mount/dismount transitions and player movement state handoff.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S7.3; behavior: changed helper, override, constructor/default, registry, resource or callback dependencies discovered from prior stages.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S7.4; behavior: revisit unchanged callers affected by changed dependencies; cross-mechanic interactions.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.
- Slice ID: S7.5; behavior: disposition of every reachable movement-state writer and unresolved/unsupported data boundary.
  - Status: pending.
  - A/B evidence: pending exact source acquisition.
  - Dependency closure: D0 source readiness; conclusion: pending.

## Dependency queue and blockers

- D0; source preparation; both exact releases, aligned namespace, source/artifact manifests and diagnostics are missing here; these are prerequisites for all comparisons. Next action: source owner supplies validated ready markers and their output roots; verify exact IDs, manifest hashes and relevant method-body diagnostics. Current state: pending, not an equivalence conclusion.
- Follow-on dependencies will be added from concrete callers, overrides, field writers, registrations and resources. No guessed dependency has been marked resolved.

## Finding index

No findings yet. No confirmed no-difference or out-of-scope coverage decisions yet. Do not import prior findings as completeness evidence; independently re-evaluate them against the whole bounded slice and its producers/consumers.

## Resume checkpoint

- Last completed slice: none; instructions and coverage plan read only.
- Next bounded slice: verify exact source-owner ready markers for 1.17.1 and 1.18.2, artifact/source hashes and diagnostics; then inventory stage 1 input and tick anchors on both sides.
- Outstanding dependencies: D0 source preparation; then method correspondence, data/resource inventory and caller/producer closure per slice.
- Current assumptions requiring verification: none promoted to evidence; Feather is only a candidate common family.

## Source audit closure

- Coverage counts by status: pending 49; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0.
- Unresolved gaps and limits: the exact source pair has not been published to this checkout; all seven navigation stages remain pending.
- Evidence/hash/correspondence audit: no source evidence or source hash cited; no method correspondence asserted.
- Runtime validation: not performed (separate workflow).
