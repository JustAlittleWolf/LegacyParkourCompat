# Discovery: 1.19.4 to 1.20.1

- Status: active
- Scope: direct client-player movement; older A = 1.19.4; newer B = 1.20.1.
- Repository revision and start date: Base main 002137b227676caea77f6832b9f4c8d0b6200bff; task branch feat/source-discovery-movement-source-1-19-4-1-20-1; started 2026-10-07 Europe/Vienna.
- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap / mojmap on both sides, as required by the source-campaign roster for pairs from 1.14.4 onward; endpoint artifact alignment and exact mapping builds remain unverified pending ready markers.
- Source preparation owner / command / log / readiness marker: campaign source owner is sole writer. Exact 1.19.4/mojmap and 1.20.1/mojmap publications requested. No ready JSON for either endpoint is currently published; worker will not invoke the shared decompiler.
- Toolchain/decompiler/remapper versions and options: pending exact ready/provenance JSON.
- Discovery author(s): /root (delegated source-only worker).
- Independent reviewer (must differ from discovery authors): pending assignment.

## Artifact manifest

The shared protocol root is build/movement-campaign-2026-10-07/. At the last catalog inspection, published Mojmap ready/provenance pairs included 1.14.4, 1.15.2, 1.16.5, 1.17.1 and 1.18.2; 1.19.4 and 1.20.1 were absent. Published earlier Feather sources and native 26.2 are unrelated to this exact pair and are not used as comparison evidence.

### A — 1.19.4

- Exact release/source root/client jar SHA-256: pending 1.19.4/mojmap ready and provenance JSON.
- CLI mode/mapping coordinate/build/path/hash/mapped jar SHA-256: Mojmap requested; exact values pending source-owner publication.
- Cited source/resource paths and SHA-256 hashes: none accepted yet.
- Required external data and provenance: pending dependency inventory.

### B — 1.20.1

- Exact release/source root/client jar SHA-256: pending 1.20.1/mojmap ready and provenance JSON.
- CLI mode/mapping coordinate/build/path/hash/mapped jar SHA-256: Mojmap requested; exact values pending source-owner publication.
- Cited source/resource paths and SHA-256 hashes: none accepted yet.
- Required external data and provenance: pending dependency inventory.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: not frozen.
- Evidence inventory and finding IDs included at freeze: none; exact source pair unavailable.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed. The permitted prior 1.19.4-to-1.20.6 source report was read only for candidate navigation; its findings are not evidence for this run.
- Source/mapping hashes covered by freeze: none.

## Correspondence and call order

Pending exact pair publication. Resolve each logical class/member descriptor, inheritance/caller chain, rename/split/replacement evidence and dependencies independently in both Mojmap trees. No guessed names or copied member correspondence from prior reports will be accepted.

Once sources are ready, inventory the complete reachable local player tick chain: input sampling, local tick and superclass tick; pre-travel predicates/state writes; every reachable travel dispatch and branch; collision/move calls; post-travel callbacks/state writes. Link each relevant movement-state writer to its readers and record exact per-side source ranges and hashes.

## Required source inventories

These inventory rows are mandatory pair-wide maps. Their behavior slices cannot be created or dispositioned until both exact source trees pass ready-manifest and method-body-diagnostic checks.

- INV-TICK input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=pending source pair; evidence=pending.
- INV-STATE movement state writers/readers: pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, support position, timers and direct predicates: status=pending; slice_ids=pending source pair; evidence=pending.
- INV-COLLISION player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=pending source pair; evidence=pending.
- INV-WORLD-MOVEMENT block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=pending source pair; evidence=pending.
- INV-MODIFIERS movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=pending source pair; evidence=pending.
- INV-EXTERNAL player-only externally supplied movement inputs and client consumers: corrections, pushes, pistons, mounts/dismounts and launch inputs: status=pending; slice_ids=pending source pair; evidence=pending.
- INV-EXCLUSIONS scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=pending; movement predicates may read vanilla values without emulating their producer systems.

The in-scope audit will preserve exact operation order, casts, float/double boundaries, literal suffixes and historical quirks. Modern-only blocks/features will not acquire old behavior. Other-entity code is in scope only as necessary to explain a direct player movement effect.

## Coverage ledger

No member-level comparison slices exist yet because neither required exact ready manifest has been published. The source request is open under dependency D0. Directory presence or previous-report claims will not be treated as readiness or coverage.

## Dependency queue and blockers

- D0 — source owner; originating scope: pair provenance. Publish exact 1.19.4/mojmap and 1.20.1/mojmap ready JSON plus provenance/source/artifact SHA-256 manifests, requested/resolved IDs, successful decompiler records, exact mappings and method-body diagnostics. These records are required to trust and cite any source. Next action: consume the owner publication read-only, verify both exact release IDs/namespaces/hashes and inspect relevant body diagnostics. This is an active external dependency, not a mapping blocker.
- D1 — after D0; originating slices: all inventories. Identify any relevant damaged/missing method body from the diagnostic inventory; request exact-version bytecode/source assistance for the specific member and side.
- D2 — after INV-WORLD-MOVEMENT and INV-MODIFIERS inventories; inspect matching client-jar resource/tag/default entries reached by movement consumers, hash cited uncompressed entries, and identify synchronized/datapack inputs.
- Open dependencies: D0, D1, D2.

## Finding index

No source-confirmed findings. This is not a no-difference conclusion. The 1.19.4-to-1.20.6 prior report is only a navigation aid; its later endpoint does not establish behavior at 1.20.1. All candidates must be rechecked against the exact source pair and complete dependencies.

## Resume checkpoint

- Last completed slice: read current project/workflow/campaign rules; created the dedicated task branch and initial provenance checkpoint; read the updated workflow hardening commits; corrected the status checker’s top-level parser.
- Next bounded slice: verify both exact Mojmap ready and provenance JSON files, all referenced hashes and relevant movement-body diagnostics; then resolve Stage 1 input/tick class/member correspondence and call order.
- Outstanding dependencies and owners: D0 source owner; D1 and D2 worker follow-ups after pair readiness and inventories.
- Current assumptions requiring verification: both exact releases successfully resolve to themselves; both Mojmap outputs are valid and use aligned release-specific mappings; relevant methods decompile without damage.

## Implementation reconciliation

This is a source-only assignment. Existing/old mod implementation has not been inspected. No reconciliation is authorized or appropriate before blind discovery is frozen; the parent may route a separate integrator after freeze.

- Reconciliation status: pending
- Repository revision inspected: not inspected for implementation.
- Finding -> implementation disposition/evidence: deferred to integrator after freeze.
- Existing implementation without a frozen source finding: not inspected; deferred to integrator.
- Coverage gaps routed back to discovery slices: none yet; source pair is pending.

## Independent source audit

- Reviewer: pending; must differ from discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: none; source pair is not published.
- Concrete missed-slice routes (or none found): pending source review.
- Misses routed to slice/finding IDs and owners: pending source review.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: 0 member-level slices created; 7 required inventory maps pending; 3 dependencies open.
- Required inventory status and evidence: all pending exact source publication; none has member-level evidence yet.
- Open dependencies: D0 source owner; D1 worker method-diagnostic follow-up; D2 worker jar-resource/data audit.
- Unresolved gaps and limits: exact endpoint sources/provenance/diagnostics; all navigation inventories, member correspondences, resources, findings and dependency closure; independent audit.
- Evidence/hash/correspondence audit: no source evidence accepted. Require exact IDs, namespace, manifest hashes and relevant method body diagnostics before comparison.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: not performed; explicitly not authorized by campaign coordinator.
