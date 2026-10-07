# Discovery: 1.14.4 to 1.15.2

- Status: active
- Scope: direct client player movement; older A = 1.14.4; newer B = 1.15.2
- Repository revision and start date: base `002137b227676caea77f6832b9f4c8d0b6200bff` (`main`); 2026-10-07; task branch `feat/source-discovery-movement-source-1-14-4-1-15-2`
- Track: source-only movement comparison; no wiki/MCPK and no mod implementation inspection; runtime validation not performed
- Selected naming namespace, CLI mode per side and alignment evidence: pending source-owner publication and ready-record verification
- Source preparation command and log: pending source-owner publication
- Toolchain/decompiler/remapper versions and options: pending validated source manifest

## Artifact manifest

- A (1.14.4): ready JSON `ready/1.14.4/ornithe-feather.ready.json`; `status=ready`, exact `versionId=1.14.4`, `mapping=ornithe-feather`, `mappingJar=feather-gen2-1.14.4+build.2-mergedv2.jar` plus `.tiny`, Vineflower, Java 25. Relative source root `ready/1.14.4/ornithe-feather/`; 3,250 files / 16,680,788 bytes. Client jar SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`; mapped jar `artifacts/1.14.4/client-ornithe-feather.jar`, SHA-256 `5e914421086acc2f244ca34bfd0df68e199dba5539a819a0eed1e4af31bb465c`; Feather mapping artifacts `artifacts/yarn/feather-gen2-1.14.4+build.2-mergedv2.jar` SHA-256 `3162806b9fb266d7e6d2c594be8d4cc6c91ddb09b4e55d1b566e5429edbd793f`, and `artifacts/yarn/feather-gen2-1.14.4+build.2.tiny` SHA-256 `60d4906621c873dadba96425d1a233349c9afa407d09bfc8371202af6a71f25c`. Source manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`; artifact manifest SHA-256 `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970`; diagnostics SHA-256 `8b5fba7cae7c0187671005b3261d184cbc2caecf9f8cbfc25b284bb0a89d128a`. All three referenced hashes match; all 3,250 source and 40 artifact entries verify. Diagnostics confirm exact metadata ID and movement bodies in `Entity.move`, `LivingEntity.jump/moveRelative/travel`, and `PlayerEntity.jump/moveRelative`. Full source prep command/log and remapper/tool versions still require owner metadata. No comparison result is claimable until an aligned B source is verified.
- B (1.15.2): no exact-version ready JSON yet; source root, namespace, artifact/source hashes and method diagnostics pending.
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

- D-001; paired source readiness/provenance; B 1.15.2 exact JSON not published, and A `1.14.4` is currently available only in `ornithe-feather`; aligned namespace, both source/artifact hashes, diagnostics and toolchain/command provenance must be verified; source owner holds the shared decompiler lock; next action: verify the 1.15.2 JSON and manifests, request an aligned source if needed, and record exact command/log/tool versions; unresolved.

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


