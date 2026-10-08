# Discovery: <A> to <B>

- Run status: active | partial | blocked | complete
- Scope: client player movement; older A = ...; newer B = ...
- Repository revision and start date:
- Selected naming namespace, CLI mode per side and alignment evidence:
- Source preparation owner / command / log / readiness marker:
- Toolchain/decompiler/remapper versions and options:
- Discovery author(s):
- Independent reviewer (must differ from discovery authors):

## Artifact manifest

Repeat for A and B: exact release; source root; client jar hash; CLI mode; mapping coordinate/build/path/hash; bridge mapping path/hash if any; mapped jar hash; cited source relative paths/hashes; cited resource jar entry names/hashes; required external data and provenance. For a published unobfuscated release, mark mapping and mapped-jar fields not applicable and record that the original client jar was decompiled. Use SHA-256 and record publisher hashes separately. Distinguish original verified artifacts from revised derived snapshots; never overwrite the original record with a revised artifact identity.

## Artifact evidence identities

Add a record for each evidence artifact/revision cited by findings or snapshots. Values may honestly remain `pending` in an active report. A revised record must identify both the immutable revised snapshot and its original lineage, including unavailable original artifacts and equivalence limits.

### Evidence artifact <ID>

- Release / side / evidence role:
- Publication status: original-verified | revised-derived | pending
- Revision ID: none | <revision-id> | pending
- Immutable evidence path:
- Evidence artifact SHA-256:
- Evidence manifest path:
- Evidence manifest SHA-256:
- Original artifact-manifest path:
- Original artifact-manifest SHA-256:
- Original derived-artifact availability: verified | unavailable | not-applicable | pending
- Original derived-artifact SHA-256 or expected hash:
- Source/raw-input hash relation: verified | unverified | not-applicable | pending; verification reference:
- Revised-to-original derived-artifact equivalence: verified | unverified | not-applicable | pending; evidence reference:
- Provenance limitations:

<!-- Repeat per artifact ID. These declarations identify evidence; they do not verify hashes by themselves. -->

## Blind-discovery freeze

- This status is the full-pair freeze. Individual finding snapshots are tracked separately and do not change it.
- Status: pending | frozen
- Freeze commit/checkpoint and timestamp:
- Evidence inventory and finding IDs included at freeze:
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation):
- Source/mapping hashes covered by freeze:

## Correspondence and call order

For each logical role, record A class/member descriptor -> B descriptor; exact source anchors; rename/split/replacement evidence; callers; direct dependencies; state read/write edges and execution order. Include the complete reachable tick sequence through pre-travel, travel dispatch/branches, and post-travel. No guessed names or untraced stage labels.

## Required source inventories

Each inventory is a top-level map to bounded coverage-slice IDs, not a single broad “checked” row. Mark complete only when every listed source slice is dispositioned and dependencies are closed.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending | complete; slice_ids=<...>; evidence=<...>
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending | complete; slice_ids=<...>; evidence=<...>
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending | complete; slice_ids=<...>; evidence=<...>
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending | complete; slice_ids=<...>; evidence=<...>
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending | complete; slice_ids=<...>; evidence=<...>
- `INV-EXTERNAL` player-only external movement inputs and direct player velocity/impulse/knockback application, plus in-scope player-facing transitions; exclude non-player and vehicle physics: status=pending | complete; slice_ids=<...>; evidence=<...>
- `INV-EXCLUSIONS` explicit scope audit for health/food state production, attack/damage resolution, non-player movement and vehicle physics. Direct player-motion response remains in scope even when combat can trigger it: status=pending | complete; evidence=<...>; explain direct vanilla-state reads without emulating their producers.

## Coverage ledger

Create one entry per bounded behavior slice. Do not use an entire class, stage, travel method, or change list as a row. Every slice must identify the inspected method/body range on both sides, its producers and consumers, dependencies, and a conclusion. Split methods as needed while retaining enclosing guards and ordering.

### Slice <ID>: <one bounded behavior>

- Inventory ID(s):
- Exact behavior boundary and enclosing guards/order checked:
- A evidence: <relative source path>::<owner>#<signature/descriptor>, lines <start-end>, SHA-256 <...> (or checked absence path):
- B evidence: <relative source path>::<owner>#<signature/descriptor>, lines <start-end>, SHA-256 <...> (or checked absence path):
- State producers/writers -> consumers/readers:
- Parent slices / dependencies / closure evidence:
- Status: pending | in-progress | compared-no-difference | findings | not-applicable | blocked
- Disposition and rationale (including concrete reachability/preconditions):
- Finding IDs or checked absence/replacement path:

<!-- Repeat the Slice block for every inventory behavior and every newly discovered dependency. -->

## Dependency queue and blockers

For each open item: ID; originating slice; exact missing member/resource/data/range; why it can affect movement; next retrieval action; owner; resolution evidence or precise external blocker.

- Open dependencies: none | <IDs and owners>

## Finding index

Link each finding with its short behavioral title and confidence. Record discarded candidates and reasons so they are not rediscovered. Findings must use `workflows/movement-discovery/templates/finding.md`.

## Resume checkpoint

- Last completed slice:
- Next bounded slice and exact files/members/body ranges to open:
- Outstanding dependencies and owners:
- Current assumptions requiring verification:

## Finding snapshots (not pair freeze)

Append an event for each source-confirmed finding snapshot and each later invalidation/supersession. This log is source-review metadata only. Do not record implementation outcomes here while the pair is open. Snapshot acceptance never changes the pair run status or counts as a full-pair audit.

### Snapshot event <ID>

- Finding ID(s):
- Source finding author(s):
- Status: submitted | accepted | rejected | revision-required | invalidated | superseded
- Immutable snapshot commit:
- Finding file path and SHA-256:
- Exact A/B artifact-manifest identities/hashes:
- Evidence artifact record IDs:
- Publication status: original-verified | revised-derived | pending
- Revision ID(s):
- Immutable evidence path(s):
- Evidence artifact SHA-256(s):
- Evidence manifest path(s):
- Evidence manifest SHA-256(s):
- Original artifact-manifest path(s):
- Original artifact-manifest SHA-256(s):
- Original derived-artifact availability/hash:
- Source/raw-input hash relation:
- Revised-to-original equivalence:
- Provenance limitations:
- Cited source/resource hashes:
- Source-proven historical behavior boundary/evidence (phase, producers/consumers, applicability), or unresolved source question:
- Finding-specific closed dependency IDs/evidence:
- Independent blind source reviewer and decision date:
- Review basis / requested source-only revisions:
- Pair run status and commit at handoff:
- Pair complete: no
- Implementation handoff: ready | blocked; reason:
- Replaces/supersedes snapshot ID and reason, if applicable:

<!-- Append subsequent events; preserve prior records and commits. -->

## Implementation reconciliation

Complete only after blind-discovery freeze. Reconcile every frozen finding against the current implementation and prior catalogs without changing discovery coverage. For each finding, record one implementation disposition: implemented (exact code/hook/provider evidence), intentionally excluded (scope rationale), or open (owner/next action). Record separately whether any previously implemented behavior lacks a source-discovery finding. Do not infer discovery completion from implementation coverage.

- Reconciliation status: pending | complete
- Repository revision inspected:
- Finding -> implementation disposition/evidence:
- Existing implementation without a frozen source finding:
- Coverage gaps routed back to discovery slices:

## Independent source audit

Reviewer must not be a discovery author and must remain blind to mod implementation and wiki-audit information through this full-pair audit. Re-walk the full player tick entry/call graph and every required inventory against exact A/B source, without accepting stage labels or narrow ordinary-travel parity as whole-tick evidence. Check method ranges, state writers/consumers, shape providers/registrations/neighbors, direct vanilla-state reads, and excluded systems. For every miss, create the precise coverage slice and assign its dependency/owner before passing.

- Reviewer:
- Status: pending | passed
- Inventories and call-chain ranges re-walked:
- Concrete missed-slice routes (or `none found`):
- Misses routed to slice/finding IDs and owners:
- Reviewer evidence / date:

## Source audit closure

- Coverage counts by status:
- Required inventory status and evidence:
- Accepted finding snapshots (metadata only; does not close pair):
- Open dependencies: none | <IDs and owners>
- Unresolved gaps and limits:
- Evidence/hash/correspondence audit:
- Full-pair blind freeze: pending | frozen
- Implementation reconciliation: pending | complete
- Independent audit: pending | passed
- Runtime validation: not performed (separate workflow; never inferred from build/source completion).

The run folder contains only this `run.md` and, when findings exist, one file per finding under `findings/`. The completion checker verifies the schema and statuses only; the independent source audit establishes evidence quality.
