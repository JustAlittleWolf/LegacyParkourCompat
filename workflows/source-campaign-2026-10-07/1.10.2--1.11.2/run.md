# Source discovery campaign: 1.10.2 to 1.11.2

- Status: active
- Scope: source-only comparison of direct client-player movement between exact releases A=1.10.2 and B=1.11.2.
- Repository base: `002137b227676caea77f6832b9f4c8d0b6200bff` (`main`), 2026-10-07.
- Task branch: `feat/source-discovery-movement-source-1-10-2-1-11-2`.
- Track declaration: fresh source discovery; no wiki/MCPK evidence; no mod-implementation inspection; no runtime implementation or validation.
- Namespace and artifact provenance: awaiting source-owner protocol for exact pair. Shared root is `build/movement-campaign-2026-10-07`; no shared source tree or readiness JSON has been read yet.

## Artifact manifest

Awaiting validated readiness JSON for both exact releases. Source roots, resolved release IDs, namespace, jars, mappings, decompiler diagnostics and artifact/source hashes will be recorded only after independently verifying the protocol and exact IDs.

## Correspondence and call order

Pending source readiness. Build a fresh pair-specific class/member correspondence and ordered call/state graph, verifying callers, overrides, registry entries, field writers and resources. Earlier reports are historical leads only, not proof of exhaustive coverage.

## Coverage ledger

All rows are pending until both source bodies and relevant dependency closure are checked. Exhaustion, hunger, food, saturation, health, regeneration, damage and combat calculations are excluded even when they affect an indirect sprint gate; vanilla consumers of resulting state are in scope only to record the boundary. No historical behavior is assigned to modern-only blocks/features. Non-player simulation is out of scope.

- S1a input sampling and local tick order: pending.
- S1b yaw-to-motion conversion, diagonal normalization and input rescaling: pending.
- S1c previous/current input and movement-flag capture timing: pending.
- S1d sprint start/stop gates, timers and state writers (excluding hunger mechanics): pending.
- S1e jump input, jump timers/cooldowns, and auto-jump: pending.
- S1f flight toggles, riding gates and unstuck handling: pending.
- S1g other reachable local-input producers/callbacks affecting movement: pending.
- S2a player movement-state defaults, initialization, writers and resets: pending.
- S2b pose selection, dimensions, eye height, resize timing and dependent queries: pending.
- S2c flight abilities, speed and movement-related ability state: pending.
- S2d item-use slowdown and movement-relevant active-item state: pending.
- S2e sprint-gate consumers and external/synchronized state boundary: pending.
- S3a travel dispatch, branch predicates and velocity thresholds: pending.
- S3b ground/air acceleration, friction and speed calculations: pending.
- S3c gravity, drag, negligible-velocity cutoffs and exact operation order: pending.
- S3d jump power and sprint-jump impulse: pending.
- S3e climbing and other direct movement clamps/forces: pending.
- S3f water/lava travel, fluid height, flow and jump gates: pending.
- S3g fall-flying/gliding and flying travel: pending.
- S3h pre-travel and post-travel math, flags, timers and state updates: pending.
- S4a bounding-box movement, position updates and collision-query timing: pending.
- S4b axis resolution order, collision candidate selection and tie-breaking: pending.
- S4c step-up candidates, heights, ordering and result selection: pending.
- S4d sneak-edge probes, support lookup and grounding transitions: pending.
- S4e velocity cancellation/restitution, landing and movement callbacks: pending.
- S4f pose/dimension-dependent collision shapes and repeated same-tick queries: pending.
- S4g collision/fluid/on-ground flags and supporting-block state writers: pending.
- S5a block/fluid movement-property providers, defaults and registrations: pending.
- S5b historical collision shapes, state-dependent/neighbor-dependent shapes and support: pending.
- S5c movement-affecting block callbacks, contact hooks and special-block subclasses: pending.
- S5d water/lava/bubble fluid properties, flow-vector and height calculations: pending.
- S5e tags/resources/registry data that feed movement shapes or inputs: pending.
- S5f added/removed block applicability and modern-only disposition: pending.
- S6a movement attribute consumers, aggregation, defaults and modifier order: pending.
- S6b movement effects, amplifiers, application/removal timing and consumers: pending.
- S6c movement enchantments, conditions, equipment slots and applicability: pending.
- S6d movement-relevant equipment/items and use state: pending.
- S6e resource/tag/registry dependencies and server-synchronized input boundaries: pending.
- S7a incoming velocity/position corrections and player-state packet consumers: pending.
- S7b player-facing knockback, push and explosion velocity writers: pending.
- S7c piston and other external block-driven player displacement: pending.
- S7d mount/dismount transitions and player launch-item paths: pending.
- S7e dependency closure for all reachable external state writers: pending.
- D1 cross-stage dependency closure, caller rechecks and interaction deduplication: pending.

## Dependency queue and blockers

- D0 — Await source-owner readiness JSON and artifact/source manifests for exact 1.10.2 and 1.11.2 in one aligned namespace. Verify exact requested/resolved IDs, hashes and movement-method body diagnostics before opening source. Source owner is the sole writer of the shared decompiler output/cache; do not run generation or alter shared artifacts.

## Finding index

No findings confirmed in this fresh campaign yet. Existing records in `workflows/movement-discovery/runs/1.10.2--1.11.2/` are historical leads only; they do not count as fresh comparisons or coverage.

## Resume checkpoint

- Last completed slice: none; project instructions, navigation stages, templates and decompiler implementation read.
- Next: verify exact source-owner protocol; inventory each side; then compare all pending slices in navigation order and close their dependencies.
- Explicit pending count: 44 rows (S1a–S1g, S2a–S2e, S3a–S3h, S4a–S4g, S5a–S5f, S6a–S6e, S7a–S7e, D1).
- Exclusions: health, regeneration, hunger, food, saturation, exhaustion, damage and combat mechanics; modern-only blocks/features do not receive historical behavior; non-player simulation is out of scope.

## Source audit closure

- Coverage counts: 0 resolved; 44 pending.
- Unresolved gaps: exact pair source publication and all source comparisons/dependency closures.
- Evidence/hash/correspondence audit: not started; shared source presence is not considered proof of readiness.
- Runtime validation: not performed; this is a source-only track.
