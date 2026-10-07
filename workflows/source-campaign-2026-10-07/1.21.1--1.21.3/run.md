# Discovery: 1.21.1 to 1.21.3

- Status: active
- Scope: source-only client player movement; older A = 1.21.1; newer B = 1.21.3. This is one content-update boundary using the exact latest-hotfix representatives.
- Repository revision and start date: started at `002137b227676caea77f6832b9f4c8d0b6200bff` (`main`), 2026-10-07 Europe/Vienna; task branch `feat/source-discovery-movement-source-1-21-1-1-21-3`.
- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap for both, per the finalized campaign rule for endpoints from 1.14.4 onward; CLI modes and exact release alignment await the published readiness records. No comparison has started.
- Source preparation owner / command / log / readiness marker: the campaign's sole source owner controls shared decompilation. Exact 1.21.1 and 1.21.3 sources were not published at initial check. No decompiler was run in this worktree. Await exact marker names, commands and logs from the owner.
- Toolchain/decompiler/remapper versions and options: pending exact source manifests/owner records.
- Discovery author(s): Codex source worker for this branch.
- Independent reviewer (must differ from discovery authors): pending coordinator assignment.
- Scope constraints: no wiki/MCPK use; no wiki-audit output or old mod implementation/code inspected; no runtime Java implementation. Health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement are excluded. Direct vanilla-state reads by movement predicates may be recorded without emulating their producer systems. Modern-only blocks do not acquire old behavior.
- Runtime validation: not performed and not authorized. Tests, game/TAS/Gym/server/Docker launches and Gradle builds have not been run.

## Artifact manifest

### A — 1.21.1

- Requested release: 1.21.1. Resolved release and metadata IDs: pending marker verification.
- Source root: pending. Initial exact-path check found no `build/movement-campaign-2026-10-07/ready/1.21.1/` directory.
- Client jar SHA-256, Mojmap coordinate/build, mapping file/hash, remapped jar hash, source manifest/hash, artifact manifest/hash, diagnostics/hash and publisher hashes: pending source-owner publication and independent verification.
- Resource jar identity and resource hashes: pending exact provenance; no resource has been used as evidence.

### B — 1.21.3

- Requested release: 1.21.3. Resolved release and metadata IDs: pending marker verification.
- Source root: pending. Initial exact-path check found no `build/movement-campaign-2026-10-07/ready/1.21.3/` directory.
- Client jar SHA-256, Mojmap coordinate/build, mapping file/hash, remapped jar hash, source manifest/hash, artifact manifest/hash, diagnostics/hash and publisher hashes: pending source-owner publication and independent verification.
- Resource jar identity and resource hashes: pending exact provenance; no resource has been used as evidence.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending source comparison.
- Evidence inventory and finding IDs included at freeze: none yet; zero findings at this checkpoint does not imply equivalence.
- Confirmation: no old mod implementation/code or isolated wiki-audit results were opened; no wiki was browsed. Prior source-discovery reports have not been used.
- Source/mapping hashes covered by freeze: none yet; exact pair markers and manifests are pending.

## Correspondence and call order

No class/member correspondence is asserted. After validating the exact pair, record per logical role: A class/member descriptor to B descriptor, original line anchors, rename/split/replacement evidence, callers, direct dependencies, state read/write edges, and tick order. The index must cover the reachable player tick chain through pre-travel, travel dispatch and every branch, and post-travel. Name similarity alone is not evidence.

## Required source inventories

All inventories remain pending until the exact pair is available and their bounded movement slices are enumerated. These inventory IDs must map to member-level coverage entries, not stage summaries.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=pending exact source inventory; evidence=pending exact source pair.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=pending exact source inventory; evidence=pending exact source pair.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=pending exact source inventory; evidence=pending exact source pair.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=pending exact source inventory; evidence=pending exact source pair.
- `INV-MODIFIERS` direct movement attributes, effects, enchantments, equipment, and applications/removals/conditions: status=pending; slice_ids=pending exact source inventory; evidence=pending exact source pair.
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, including corrections, pushes, pistons, mounts and launch effects: status=pending; slice_ids=pending exact source inventory; evidence=pending exact source pair.
- `INV-EXCLUSIONS` explicit scope audit for excluded systems: status=pending; evidence=pending exact source pair; direct vanilla-state reads and their movement consumers have not yet been inventoried.

## Coverage ledger

No movement slice has been compared yet. Add one bounded entry per behavior and dependency after exact source methods and callers are verified. Each terminal entry must include both-side method/body ranges or checked absence paths, SHA-256 source identities, producer/writer-to-consumer/reader links, parent/dependency closure, and concrete reachability/preconditions. Pending or in-progress entries remain open and prevent completion.

## Dependency queue and blockers

- Open dependencies: `SRC-PAIR` (campaign source owner; exact 1.21.1 and 1.21.3 Mojmap readiness markers, trees and manifests are unpublished), `METHOD-INVENTORY` (this source worker; enumerate the full reachable player tick/movement call graph and every required producer, consumer, state writer and shape provider once sources are ready), `RESOURCE-CHAIN` (this source worker; inspect matching client-jar resources/tags/defaults after provenance is verified; identify server-supplied data explicitly).`r`n- Resolved dependency: `DEP-CHECKER` — canonical workflow fix `fba28fa154d29572263ea3f2c44cf1dc23134329` was cherry-picked as `4223d9c`; use the corrected checker for future structure/status checks. This does not resolve any source coverage.
- `SRC-PAIR`: next action is to read the exact owner-published records, validate requested/resolved release IDs, common Mojmap namespace, manifests/hashes and relevant body diagnostics. Do not infer readiness from a directory.
- `METHOD-INVENTORY`: no exact method names/ranges are asserted until paired sources are ready. Expand stage labels into bounded entries and retain full guards/order and dependency closure.
- `RESOURCE-CHAIN`: no jar entry or data absence claim is asserted until both exact client jars and their hashes are verified.

## Finding index

None yet. No source comparison has been performed.

## Resume checkpoint

- Last completed slice: repository/source-workflow requirements read; dedicated task branch created at the verified main base; active report checkpoint committed. Shared source preparation was not touched.
- Next bounded slice and exact files/members/body ranges to open: verify the exact 1.21.1 and 1.21.3 Mojmap readiness markers, source/artifact manifests and method diagnostics; then enumerate S1 input and full local-player tick call order from exact sources.
- Outstanding dependencies and owners: `SRC-PAIR` — campaign source owner; `METHOD-INVENTORY` and `RESOURCE-CHAIN` — source worker after publication.
- Current assumptions requiring verification: exact resolved IDs equal requested IDs; both source trees are Mojmap and compatible; every cited method/resource body matches its published SHA-256 manifest.

## Implementation reconciliation

Must remain pending until blind-discovery freeze. Runtime Java implementation has not been inspected and will not be changed by this source-only task.

- Reconciliation status: pending
- Repository revision inspected: not inspected
- Finding -> implementation disposition/evidence: pending freeze
- Existing implementation without a frozen source finding: pending freeze
- Coverage gaps routed back to discovery slices: pending freeze

## Independent source audit

An independent reviewer has not yet been assigned and no audit has occurred.

- Reviewer: pending coordinator assignment; must differ from discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: none
- Concrete missed-slice routes (or `none found`): pending
- Misses routed to slice/finding IDs and owners: pending
- Reviewer evidence / date: pending

## Source audit closure

- Coverage counts by status: no bounded coverage entries yet; seven required inventories pending.
- Required inventory status and evidence: `INV-TICK` pending; `INV-STATE` pending; `INV-COLLISION` pending; `INV-WORLD-MOVEMENT` pending; `INV-MODIFIERS` pending; `INV-EXTERNAL` pending; `INV-EXCLUSIONS` pending. Evidence awaits exact endpoint publication.
- Open dependencies: `SRC-PAIR` (campaign source owner), `METHOD-INVENTORY` (source worker after publication), `RESOURCE-CHAIN` (source worker after artifact verification).
- Unresolved gaps and limits: no paired source provenance or semantics comparison; all movement coverage remains open.
- Evidence/hash/correspondence audit: no source findings/hashes/correspondence claims yet.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).

