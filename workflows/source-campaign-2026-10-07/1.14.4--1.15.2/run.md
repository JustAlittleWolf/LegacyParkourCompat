# Discovery: 1.14.4 to 1.15.2

- Status: active
- Scope: direct client player movement; older A = 1.14.4; newer B = 1.15.2
- Repository revision and start date: base `002137b227676caea77f6832b9f4c8d0b6200bff` (`main`); 2026-10-07; task branch `feat/source-discovery-movement-source-1-14-4-1-15-2`
- Track: source-only movement comparison; no wiki/MCPK and no mod implementation inspection; runtime validation not performed
- Selected naming namespace, CLI mode per side and alignment evidence: pending source-owner publication and ready-record verification
- Source preparation command and log: pending source-owner publication
- Toolchain/decompiler/remapper versions and options: pending validated source manifest

## Artifact manifest

- A (1.14.4): pending validated ready JSON; exact source root, client jar identity/hash, mapping artifact/build/path/hash, mapped jar hash, cited source/resource hashes not yet verified.
- B (1.15.2): pending validated ready JSON; exact source root, client jar identity/hash, mapping artifact/build/path/hash, mapped jar hash, cited source/resource hashes not yet verified.
- Shared source root: `build/movement-campaign-2026-10-07/ready/<exact-version>/<namespace>/`, with a readiness JSON beside each namespace tree and SHA-256 manifests/movement-method diagnostics referenced by it. The source owner holds `decompile.lock` and is the sole writer. At the last readiness check, no exact-version readiness JSON was published for either 1.14.4 or 1.15.2. Do not infer readiness from directory presence.

## Correspondence and call order

Pending. Build paired logical-role correspondence from exact source members and descriptors, then trace callers, dependencies, state reads/writes and execution order. No class/member correspondence is assumed from names alone.

## Coverage ledger

Initial navigation inventory only. Each row is pending until exact paired members, evidence paths/lines/hashes, and dependency closure are recorded. Split into bounded behavior slices after source readiness; do not close these broad stage rows as equivalent.

- Slice 1 / stage 1 / local input and tick ordering: pending; input sampling, local tick/superclass tick/travel, input-to-motion conversion, sneak/use scaling, sprint/jump/flight/auto-jump, unstuck/riding gates, input and flag capture timing.
- Slice 2 / stage 2 / player-specific state and gates: pending; pose/dimensions/eye height, swimming/crawling, flight state/speed, sprint gates (excluding hunger/food/damage emulation), active-item state, edge sneaking, air speed and influential field lifecycle.
- Slice 3 / stage 3 / living movement integration: pending; dispatch, ground/air acceleration, friction, gravity/drag, velocity cutoffs, jump/sprint-jump, climbing, water/lava, swimming/gliding, post-travel updates, movement attributes/helpers.
- Slice 4 / stage 4 / entity movement and collision: pending; AABB movement, position updates, axis ordering, step candidates/ties, edge/support checks, grounding, velocity cancel/restitution, fluid push, callback order, collision queries/shapes/context and pose-driven query timing.
- Slice 5 / stage 5 / blocks and fluids: pending; movement callbacks, collision shapes/support, friction/speed/jump factors, state/neighbor dependence, relevant registrations/subclasses, fluids/flow/height and modern-only applicability dispositions.
- Slice 6 / stage 6 / effects, enchantments, attributes and equipment: pending; movement-state consumers and their producer/application/removal/default/resource chains. Excludes health, regen, hunger, food, saturation, exhaustion, damage and combat emulation; vanilla state consumers such as a sprint gate remain in scope.
- Slice 7 / stage 7 / external influences and dependency closure: pending; incoming velocity/position correction, player knockback/push, explosions, piston displacement, mount transitions and launch items as direct player-state writers; separate externally supplied values and close changed helpers/resources/callbacks/writers.

## Dependency queue and blockers

- D-001; source pair readiness; exact release IDs, aligned namespace/modes, validated JSON, artifact and source hashes; required for all slices; source owner reports endpoints are queued serially and holds the shared decompiler lock; next action: reread the per-namespace ready JSONs under `build/movement-campaign-2026-10-07/ready/`, verify exact IDs, namespace, cited hashes and method-body diagnostics; unresolved.

## Finding index

None confirmed yet. No prior report or implementation catalog is treated as exhaustive evidence.

## Resume checkpoint

- Last completed slice: none; initial navigation inventory captured.
- Next bounded slice: establish pair provenance and source/class correspondence for stage 1 after validated source-owner record arrives.
- Outstanding dependencies: D-001 source pair readiness.
- Current assumptions requiring verification: none; namespace and source availability intentionally unassumed.

## Source audit closure

- Coverage counts by status: pending 7 navigation-stage inventories; bounded-slice coverage not yet expanded; findings 0.
- Unresolved gaps and limits: source publication and all source-level coverage remain open; this is not a completed comparison.
- Evidence/hash/correspondence audit: pending source readiness.
- Runtime validation: not performed (separate workflow).

