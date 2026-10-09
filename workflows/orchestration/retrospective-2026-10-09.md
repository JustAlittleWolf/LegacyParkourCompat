# Project intent and orchestration retrospective

Reviewed 2026-10-09. This is process/history analysis, not movement discovery. Do not distribute it to blind source workers or reviewers. The resulting procedure is [the orchestrator workflow](README.md).

## Original goal and method

The earliest tracked `AGENTS.md`, `e6055e6d` (2026-09-01), already specified modern Fabric client/server support for old parkour maps, independent minimal mechanic deltas, hierarchical resolution, deterministic tick-level parity, exact floating-point operations, historical bugs and general mixin dispatch. Its worked example resolved two operations independently across several releases. The initial build instructions used an npm wrapper; subsequent revisions moved to Gradle. Preserve the intent, not obsolete commands.

`2e4f7731` clarified one-way compatibility, vanilla block states versus historical collision shapes, player-only scope, client/server version authority and no backward compatibility with old mod releases. `21f46b38` (2026-09-26) moved the human-facing description into the root README and module guides; `5562b1dc` restored the compact agent guidance. The goal was not lost there. The reset/rebuild acceptance scope was where behavior coverage was lost.

Later corrections are deliberate: health/food production excluded (`117ecaa6`), one independently resolved operation per hook (`1336a6a4`), static central registration (`e9e5a059`) and independent release implementations (`a3ec0c9c`). The October 7 user's explicit statement that profile switching is undefined behavior survived in coordinator handoffs but was absent from the root README/AGENTS. This update restores it. It also restores a short resolver example and separates campaign procedure from always-loaded rules, following [AGENTS.md's separation of agent guidance and human documentation](https://agents.md/).

## Runs inspected

The three primary rounds are September 26 discovery, October 1 fresh implementation, and October 7–8 completeness discovery/implementation. The September 22 overnight pilot was also inspected as context; it mixed source audit, runtime diagnosis and TAS tooling, including a duplicate investigation that was stopped. It is not included in the counts below.

The root chats were read through their available paginated turn histories, including user corrections, dispatch instructions, tool metadata and final handoffs. Supporting evidence includes the actual 1.8 discovery worker, the prior [lost-behavior retrospective](../retrospective-2026-10-07.md), [major implementation campaign](../fix-implementation/major-campaign.md), [operations audit](../orchestration-2026-10-08/campaign-ops-audit-2026-10-08.md), transfer/resume records and branch-consolidation history. Exact chat IDs and metadata counts are retained in [review-metrics.json](review-metrics.json).

These counts are coordinator call entries, including retries and administrative turns. They are not successful-operation counts, worker totals, token usage or invoices. Some turn durations are missing; completed durations can overlap. No defensible percentage breakdown of time or cost is available. Bottleneck ranking below is a qualitative inference from repeated events and corrections.

### September 26: discovery and preparation

“Launch movement discovery runs” assigned latest exact representatives, shared decompiled sources and separate Luna Medium chats, initially with a user-reviewed pilot. The root history contains 277 shell calls, 68 waits, 41 worker messages and 16 creation calls.

Time went into artifact acquisition/alignment, root-side preparation, output standardization and repeated partial continuation. The pilot rejected Mojmap versus published unobfuscated source until the user corrected their common namespace. Another pair was called blocked because the existing directories were unaligned, though exact Feather sources could be prepared for both releases. The root later restored missing shared sources; the deletion cause remained unknown.

The actual “Movement discovery: 1.8.9 → 1.9.4” worker continued after several instructions but stopped with collision, shapes, effects/equipment and external influences pending. Its scoped no-difference travel result was valid only within that checked slice. This is evidence for preparation before dispatch, bounded continuation queues and independent full-tick coverage audits, not for removing the source evidence requirements.

### October 1: reset, restart and final integration

“Orchestrate major version discovery” contains 35 creation calls, 262 worker messages, 342 waits and 92 shell calls. The user's instruction was to do little root work and delegate implementation and merging.

The campaign started implementing partial catalogs, then paused and restarted after the user requested removal of old implementations to prevent bias. The authorized reset `ebe56a21` removed 116 files / 4,511 lines. Fifteen fresh implementation branches later merged at `5205356d`, with successful compilation and 75 registrations.

The costly failure was an acceptance mismatch: replacing all old behavior with only previously cataloged findings. Velocity cutoff, pane shapes and crouch dimensions were omitted. The later user observations triggered another investigation and repairs. The earlier [retrospective](../retrospective-2026-10-07.md) traces the exact evidence chain.

Source staging also moved after repeated missing-tree/marker incidents. The root repeatedly relayed preparation and integration checks; the user had to remind it to delegate merging. Improved blind discovery should isolate access to implementation rather than deleting it. A reset needs its own removed-behavior reconciliation after source freeze.

### October 7–8: stronger evidence, excessive coordination

“Find why movement patches are unap​​” became the original completeness coordinator. Its available history contains 50 creation calls, 688 messages, 308 waits and 216 full-thread reads. Its fresh continuation, ID `01a11bae-d354-7931-8746-2ef6befcce75`, adds 48 creation calls, 300 messages, 702 app-thread waits, 84 reads and 75 internal-agent waits. Those continuation counts include the later authorized consolidation/ref cleanup, so they must not all be charged to discovery.

Assignments now demanded full tick/state/provider/resource coverage, exact snapshot hashes, independent blind reviews and separate Wiki/MCPK lanes. The user explicitly permitted early implementation of accepted findings, restricted validation to builds/static checks and requested small root effort. These are useful corrections.

The campaign initially reactivated 40 assignments, encountered reconnect/routing failures, and replaced workers and the coordinator with fresh chats. The user introduced a 15-role cap, later raised to 20. That history supports conservative admission control, but does not prove concurrency caused every failure or that either cap is optimal.

Much visible coordination dealt with reservations, ambiguous launches, stale hashes/paths, provenance corrections, repeated artifact checks and terminal handoffs. Review caught substantive errors too: excluded producer/vehicle work, incorrect witnesses, resolver direction/profile gates, inherited release implementations and catalog visibility/API issues. Removing independent review would save calls at the expense of correctness.

A particularly avoidable cost was blind source contamination through main merges and mixed information. The saved 1.21.4–1.21.5 report discloses implementation-diff reads and requires fresh blind coverage work. The new workflow assigns main merging to the integration owner outside the blind source lane.

The saved campaign remains paused and partial/open. The 1.17.1–1.18.2 source freeze and 14 findings have independent acceptance, while mutable owner reconciliation, implementation and runtime states remain separate. This retrospective does not change those states.

## Changes with the highest expected benefit

1. **Avoid restart/research churn:** ready artifacts before dispatch; keep existing code and accepted snapshots; resume exact open slices; isolate blind lanes through integration. Expected largest benefit because these failures forced whole assignments to restart or repeat review.
2. **Shrink the coordinator's work:** cheap root model, role-specific contracts, one current queue, cursor-based waits, no reply to routine commentary, one infrastructure owner. Delegate source preparation and technical integration rather than having the root perform them.
3. **Finish ready work before opening more:** one owner per pair, bounded slices, capacity reserved for review/integration and concurrency adjusted to accepted throughput. An occupied slot is not a completed finding.
4. **Batch unavoidable work:** review several independent ready findings per reviewer assignment, serially reconcile shared hook areas, build small accepted batches once per code tree. Keep full-pair audits and exact acceptance identities.
5. **Measure before claiming savings:** accepted units per hour/cost, queue latency, revision rate, preparation/build time and coordinator activity. Current chat counts identify coordination volume but cannot establish money spent or a speedup.

Do not optimize by weakening exact source comparisons, dropping pending coverage, accepting implementation-biased discovery, tolerating arithmetic drift or substituting compilation for runtime evidence. Do not inherit the pilot's temporary 10-ULP TAS threshold as the project's parity target.
