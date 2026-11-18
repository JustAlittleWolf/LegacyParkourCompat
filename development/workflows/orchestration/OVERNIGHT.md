# Unattended movement campaign

Start only on the user's start signal. Continue until the user sends stop, usable quota is exhausted, or an explicitly configured campaign time limit is reached. Treat the earlier two-hour estimate as planning context. A checkpoint ends a batch and immediately opens the next. On stop, propagate it to descendants immediately and report the latest durable state.

## Delegation

Use English throughout. Arm a native goal for the GPT-6.2 Sol high coordinator and for each of three GPT-6.2 Sol medium orchestrators. Give each orchestrator its own checkout, branch, queue and standing cross-release domain. All three carry their work from discovery through independent acceptance and eligible implementation.

| Orchestrator | Standing domain |
| --- | --- |
| A | Environmental block contact, portals, fluids and their direct movement responses |
| B | Solid-world collision, shapes, support, stepping and AutoJump geometry |
| C | Remaining player-state and locomotion operations, including Elytra, input, sprint, pose, equipment, effects and external state writers |

Assign the operation by its player-visible behavior. Its owner can trace shared dependencies and edit its operation-specific members in shared files on an isolated branch. Each orchestrator targets 20 useful concurrent workers in its own chat. Distribute contracts across relevant releases, independent operations, evidence gates and eligible implementation. Use the actual launcher's capacity and refill on any completion. See the [standing reservation and worker contract](WORKFLOW.md#reserve-an-end-to-end-block).

The coordinator allocates domains, transfers idle capacity, handles urgent organizational conflicts and propagates stop. A reported exhausted queue can trigger transfer of an unstarted independent slice from a busier domain. Grant ordinary in-domain decisions once for the full run. Orchestrators obtain routine instructions from this brief and exchange verified results through the publication queue. The coordinator waits for a user stop, a genuine cross-domain conflict, a global phase transition or an exhausted domain needing a substantial transfer. Bundle the transfer into one decision; worker counts, repeated grants, workflow reminders and routine acknowledgments stay local. At startup grant one technical publication worker exclusive ownership of the main checkout and backlog writes. The worker handles intake, combined verification and publication under the [Git handoff procedure](WORKFLOW.md#git-ownership-and-recovery).

Each orchestrator's first operational pass is to fill its own pool toward 20 active useful workers. Give a triage worker a bounded backlog projection if queue preparation is the bottleneck. Count running workers from the native roster, refill on each completion, and record a concrete capacity or readiness limit if it binds. Delegate implementation edits, source tracing, evidence writing, Git work and builds to workers. An orchestrator spends its time on task selection, dependency order, dispatch and refills.

Dispatch routine workers with explicit native arguments `model="gpt-6-luna"`, `reasoning_effort="high"` and `fork_turns="none"`. Verify execution on the first assignment. An omitted model suggestion alone does not establish unavailability. If the call fails, hold routine dispatch and report the error to the coordinator. This run requires Luna for routine work. Resolve technical issues within the owning group and reassign capacity around unavailable resources or approvals. Follow the [reusable orchestration procedure](WORKFLOW.md) for queue balancing, Git recovery, checkpoints and stop propagation.

## First dispatch and checks

### Capacity checkpoint

Include this instruction in each new orchestrator goal: "Keep up to 20 useful technical workers active across my domain, including nested workers. Refill on any completion. On context recovery and roughly once per hour while dispatching, reread the Capacity checkpoint in the primary checkout's development/workflows/orchestration/OVERNIGHT.md. Continue until user stop." Give stream schedulers the same instruction with their own slot allocation.

At that checkpoint count running direct and nested technical workers once, excluding managers. If below target, dispatch ready work before waiting. If the queue is thin, split known implementation residuals by independent caller, boundary or applicability gate and let the stream schedulers dispatch concurrently. Reuse completed verdicts and built refs. If only serial or externally blocked work remains across the domains, record the actual count, exact dependency and next wake condition once. Check again when that condition changes. Use this short section for routine reminders and reread changed phase procedures in full. A checkpoint requires no routine message to the coordinator.

Include the [implementation-first phase order](WORKFLOW.md#finish-known-implementations-before-new-discovery) in all four goals. At startup a single queue owner uses the commands below. Page with `--offset` when `nextOffset` is present. Give the domain owners disjoint sets of finding IDs and attach the MCPK claim IDs where relevant. Resolve disputed or missing historical evidence only for those existing operations until the global gate opens.

Finish the MCPK queue and drain all known eligible implementations, including actionable named prerequisites. When those queues are drained and the MCPK phase is complete, automatically resume exact local Minecraft source-led discovery in substantial disjoint slices of adjacent versions. Traverse from newest to oldest in actual release order and reuse existing coverage. When newly accepted implementation work is ready, complete it before starting more discovery. Give blind source workers only the permitted source-only projections and keep wiki and product context outside their assignment. Select the phase from the actual queue and evidence state regardless of the chat title.

```powershell
python development/workflows/orchestration/backlog.py show metadata mcpkWikiPriority
python development/workflows/orchestration/backlog.py list coverage --search mcpkWiki --limit 100
python development/workflows/orchestration/backlog.py list findings --status open --limit 100
python development/workflows/orchestration/backlog.py list findings --status partial --limit 100
```

Read the matching coverage entries with `show coverage <pair> --field wikiHints` and use their MCPK claims, including historical dispositions, to order the queue. As soon as a domain batch passes its required checks, pin its immutable ready ref and pull the next assignment. The publisher merges in parallel. Report branch-verified and published operations separately. If publication is blocked, report the last main commit, the isolated build-verified tip and the precise blocker together. A held tip stays visible in every checkpoint until published.

If workers repeatedly finish faster than one orchestrator can dispatch, split its existing reservation into three disjoint work streams. Explicitly grant two Sol medium schedulers permission to select and launch up to six Luna high workers each. The orchestrator fills up to eight direct technical slots. Count all live technical descendants once toward the same target of 20 and account for the two schedulers separately against host capacity. Each stream prepares its next contracts before its current workers finish and refills on any completion. Keep one integration writer per checkout. Verify a real nested worker launch before relying on delegated capacity. If the host rejects nesting, use longer direct assignments and record the actual limit.

Read the live backlog, latest evidence and checkout ownership. Start from the existing MCPK implementation dispositions, then drain all known implementation work under the global gate. Recheck recorded active ownership against live workers before assigning. Classify unexplained local edits as user-owned. Have the technical owners establish the first real evidence commit and first eligible product commit. Continue after both checkpoints.

Defer standalone product code reviews until project end. The integrator checks fidelity and wiring during integration and runs the prescribed test-disabled build on coherent batches. Keep independent source acceptance and full-pair audit gates. Tests, runtime launches, Docker restarts and remote pushes require separate authorization.
