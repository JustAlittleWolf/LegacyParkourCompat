# Discovery: 1.20.1 to 1.20.2

- Status: active
- Scope: client player movement; older A = 1.20.1; newer B = 1.20.2. Exact adjacent campaign assignment.
- Repository revision and start date: base `002137b227676caea77f6832b9f4c8d0b6200bff` (`main`); 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap / `mojmap` requested for both endpoints by the source coordinator; not yet evidenced by ready markers.
- Source preparation owner / command / log / readiness marker: coordinator-assigned sole source owner; exact preparation command, successful logs, and readiness markers not yet published to the canonical campaign root.
- Toolchain/decompiler/remapper versions and options: pending the exact source-owner manifests.
- Discovery author(s): delegated source worker for 1.20.1--1.20.2.
- Independent reviewer (must differ from discovery authors): pending coordinator assignment.

## Artifact manifest

A (1.20.1): not yet published. Required values pending: resolved version ID; source root; original client jar identity/hash; CLI mode; Mojmap mapping coordinate/build/path/hash; remapped jar hash; source manifest/hash; artifact manifest/hash; relevant decompiler diagnostics/hash; cited source/resource hashes; required external data and provenance.
B (1.20.2): not yet published. Same required values pending.
Pair verification on 2026-10-07: checked canonical `build/movement-campaign-2026-10-07/ready/`; it currently has no exact 1.20.1 or 1.20.2 directory/marker. Other-version directories and any unvalidated decompiled output do not establish this pair's provenance. Do not open or compare those trees.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending
- Evidence inventory and finding IDs included at freeze: none; exact endpoint sources are unavailable.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed for this worker. No implementation/catalog/wiki-audit material has been opened.
- Source/mapping hashes covered by freeze: none yet.

## Correspondence and call order

Pending the exact pair. No class/member correspondence or movement path has been inferred from names. After source validation, record exact descriptors, bodies, inheritance, callers, replacements, and state read/write edges, including the complete reachable local player tick path from input sampling through pre-travel decisions, each travel branch, and post-travel work. Maintain distinct evidence-backed maps for state producers/consumers, collision-shape providers/registrations/neighbors, data-driven modifiers, and external influences.

## Required source inventories

Each inventory is pending source publication. No coverage slice IDs have been invented before an exact member inventory exists.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=pending exact source inventory; evidence=none, source pair unavailable
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=pending exact source inventory; evidence=none, source pair unavailable
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=pending exact source inventory; evidence=none, source pair unavailable
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=pending exact source inventory; evidence=none, source pair unavailable
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=pending exact source inventory; evidence=none, source pair unavailable
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=pending exact source inventory; evidence=none, source pair unavailable
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=none, exact consumer search awaits sources

## Coverage ledger

No movement slice has been opened: both exact Mojmap source publications are absent. Stage labels are not counted as coverage. Once source preparation is ready, enumerate bounded method/body behaviors in navigation order and include every reachable dependency; each row must carry exact A/B ranges or evidence-backed absence, state producers/readers, dependency closure, and a concrete disposition.

## Dependency queue and blockers

- D0; originating slice: source-pair precondition / all required inventories; exact missing item: source-owner publication and validated readiness metadata for exact releases 1.20.1 and 1.20.2 in Mojmap; why it can affect movement: without the exact aligned Java bodies, manifests, and diagnostics no pair-specific call graph, source claim, or range/hash can be verified; next action: consume the published canonical endpoint markers read-only and check IDs, hashes, relevant movement bodies, and warnings; owner: coordinator-assigned source owner; status: requested and unresolved.
- Open dependencies: D0.

## Finding index

None. No difference or no-difference claim has been made. Prior source-discovery reports have not been used as evidence.

## Resume checkpoint

- Last completed slice: none; source-pair precondition only.
- Next bounded slice and exact files/members/body ranges to open: validate the exact 1.20.1 and 1.20.2 Mojmap readiness markers and all cited hashes/diagnostics, then create a filename inventory and pair-specific Stage 1 tick-entry correspondence.
- Outstanding dependencies and owners: D0; coordinator-assigned source owner.
- Current assumptions requiring verification: the requested namespace and resolved mapping artifact; exact requested/resolved IDs; completeness and integrity of required movement bodies; absence of relevant decompiler warnings.
- Worktree/branch: `C:/Users/Wolfi/.codex/worktrees/movement-source-1-20-1-1-20-2/LegacyParkourCompat`, `feat/source-discovery-movement-source-1-20-1-1-20-2`.

## Implementation reconciliation

Must remain untouched until the blind-discovery freeze. The implementation source and older catalogs are not part of this source-only report.

- Reconciliation status: pending
- Repository revision inspected: not inspected
- Finding -> implementation disposition/evidence: pending freeze
- Existing implementation without a frozen source finding: pending freeze
- Coverage gaps routed back to discovery slices: pending freeze

## Independent source audit

No reviewer assigned or source inventory available yet; this is not a pass.

- Reviewer: pending coordinator assignment
- Status: pending
- Inventories and call-chain ranges re-walked: none; exact source pair unavailable
- Concrete missed-slice routes (or `none found`): pending independent re-walk
- Misses routed to slice/finding IDs and owners: pending independent re-walk
- Reviewer evidence / date: none

## Source audit closure

- Coverage counts by status: 0 terminal movement slices; all 7 required inventories pending; open dependency D0.
- Required inventory status and evidence: all pending; no exact source evidence has been inspected.
- Open dependencies: D0 (exact Mojmap source publications and validation).
- Unresolved gaps and limits: comparison has not begun; no method/member inventory, call-chain audit, collision/provider/resource audit, movement modifier audit, findings, or exclusions audit.
- Evidence/hash/correspondence audit: no source hashes or correspondence claimed; only absence of exact ready markers was checked.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).

Current status remains active while awaiting source publication. If handed off before resolution, report blocked because the exact source dependency prevents meaningful comparison. The run folder contains this `run.md` only; a `findings/` folder will be added only if findings exist.

