# Discovery: 1.21.3 to 1.21.4

- Status: active
- Scope: source-only client player movement; older A = exact 1.21.3; newer B = exact 1.21.4. Health, regeneration, hunger, food, saturation, exhaustion, damage and combat simulations are excluded; movement predicates may read their vanilla state. Modern-only blocks/features and non-player movement are out of scope.
- Repository revision and start date: base `002137b227676caea77f6832b9f4c8d0b6200bff` (`main`); 2026-10-07. Task branch: `feat/source-discovery-movement-source-1-21-3-1-21-4`.
- Selected namespace and alignment: explicit `mojmap` requested for both exact releases; publication and readiness verification pending.
- Source preparation owner / command / log / readiness marker: shared source owner only; exact artifacts, command/log, and ready JSON pending.
- Toolchain/decompiler/remapper versions/options: pending validated manifests.
- Discovery author(s): current source-only worker for this interval.
- Independent reviewer: pending coordinator assignment; reviewer must not be a discovery author.

## Artifact manifest

A and B exact requested IDs are 1.21.3 and 1.21.4. Their source roots, client jar hashes, official mapping artifact/build/path/hash, remapped jar hashes, cited source/resource hashes, decompiler logs, and toolchain versions are pending validated readiness JSON and referenced manifests. Neither target version's source body has been used as comparison evidence. No directory-presence assumption is used for readiness.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending.
- Evidence inventory and finding IDs included at freeze: pending source pair and full inventory.
- Confirmation: old mod implementation/code and isolated wiki-audit outputs have not been opened; no wiki/MCPK or release-note mechanics have been used. One prior source-discovery report was read only as a navigation aid and will not substitute for fresh exact-pair checks.
- Source/mapping hashes covered by freeze: pending.

## Correspondence and call order

Pending exact source availability. Resolve exact A/B classes and member descriptors, inheritance, callers, replacements, and state read/write edges for the complete reachable player tick: input sampling; pre-travel decisions/writes; travel dispatch and every branch; post-travel work; pose/dimensions/eye height; position, velocity, bounding box, collision/ground/fluid flags, support; movement attributes; sprint/jump timers; and equipment/effect state. No correspondence is inferred from matching names.

## Required source inventories

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=pending source inventory; evidence=pending exact A/B bodies.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=pending source inventory; evidence=pending exact A/B bodies.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=pending source inventory; evidence=pending exact A/B bodies and data.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=pending source inventory; evidence=pending exact A/B source and jar resources.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=pending source inventory; evidence=pending exact A/B source and jar resources.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, including corrections, pushes, pistons and mounts: status=pending; slice_ids=pending source inventory; evidence=pending exact A/B bodies.
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=pending; direct vanilla-state reads that remain in movement predicates will be identified during comparison.

## Coverage ledger

No bounded behavior slices have been enumerated: exact A/B readiness manifests and method diagnostics are not yet published. This is open preparation, not equivalence or a completed audit. Once sources are ready, add an individual slice per bounded behavior with exact body ranges, state producers/consumers, dependency closure, and disposition. Pending/in-progress/blocked rows will prevent completion.

## Dependency queue and blockers

- `SOURCE-PAIR` — originating inventory: all. Exact missing items: readiness JSONs for A=1.21.3 and B=1.21.4 in aligned `mojmap`, cited source/artifact manifests, successful generation logs, and movement-method diagnostics. Why: exact provenance, body integrity, and comparable source namespace are prerequisites. Next action: shared source owner publishes the pair; then verify JSON IDs, namespace, hashes and relevant body diagnostics before reading/comparing. Owner: shared source owner/coordinator. Status: pending; not yet a terminal blocker because comparison can start after publication.
- `WORKTREE-GIT` — managed checkout status initially failed under the default sandbox. Dedicated task branch creation succeeded via narrowly scoped elevated Git operation; edits/commits remain confined to the returned managed checkout.

## Finding index

No source-confirmed findings yet. Missing pair sources do not imply a difference or no difference.

## Resume checkpoint

- Last completed slice: none; workflow and templates read; task branch created; open source-preparation checkpoint committed.
- Next: verify exact A/B readiness JSONs and cited hashes, inspect movement method diagnostics, then enumerate stage 1 filename inventory and full call graph.
- Outstanding dependencies and owners: `SOURCE-PAIR` — shared source owner/coordinator.
- Current assumptions requiring verification: explicit Mojmap is available and resolves successfully for both exact releases; no artifact is accepted until its ready JSON, exact IDs, hashes and relevant method bodies are validated.

## Implementation reconciliation

- Reconciliation status: pending; prohibited before blind-discovery freeze.
- Repository revision inspected: none.
- Finding -> implementation disposition/evidence: none.
- Existing implementation without a frozen source finding: not inspected.
- Coverage gaps routed back to discovery slices: pending source-only inventory.

## Independent source audit

- Reviewer: pending coordinator assignment; must differ from discovery authors.
- Status: pending
- Inventories and call-chain ranges re-walked: none yet.
- Concrete missed-slice routes (or `none found`): pending.
- Misses routed to slice/finding IDs and owners: pending.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: 0 terminal; 0 bounded slices enumerated; source inventory not started; 7 required inventories pending.
- Required inventory status and evidence: all pending; see inventory map.
- Open dependencies: `SOURCE-PAIR` (shared source owner/coordinator); reviewer assignment pending for eventual independent audit.
- Unresolved gaps and limits: all seven navigation stages await exact validated source pair and subsequent full inventory.
- Evidence/hash/correspondence audit: none claimed.
- Blind freeze: pending.
- Implementation reconciliation: pending.
- Independent audit: pending.
- Runtime validation: not performed (separate workflow; not authorized).

This run currently contains only `run.md`. The completion checker is a schema/status gate, not source proof; no completion claim is made.
