# Orchestrating movement research

Use this workflow when explicitly authorized to coordinate a movement campaign. It schedules the existing [discovery](../movement-discovery/README.md), [campaign handoff](../source-campaign-2026-10-07/README.md) and [implementation](../fix-implementation/README.md) contracts; it does not weaken their evidence or completion gates. See [the retrospective](retrospective-2026-10-09.md) for the history behind these choices.

## Goal and authority

Deliver accepted, minimal historical player-movement deltas toward deterministic parity for fixed profiles. Track discovery, implementation and runtime validation separately. A partial pair can supply an accepted finding; finishing that finding does not complete the pair.

At startup record the user's current scope, release endpoints, permitted checks, delegation surface, concurrency cap, integration target and stop condition. Read the global/project guidance. Historical prompts and handoffs are evidence, not renewed authorization: the October campaign remains paused until explicitly resumed. This documentation update does not resume it or change its saved 20-role limit.

Use a very cheap model for routine orchestration and bounded worker assignments. Configure model/effort in the launch settings, not in the worker's prose. **Suggested starting choice: GPT-6 Luna High.** This is a recommendation to evaluate, not a guarantee that every task is cheaper or faster. Official [model documentation](https://developers.openai.com/api/docs/models/gpt-6-luna) describes Luna as efficient for focused high-volume tasks and supports high reasoning effort. Actual campaign cost also depends on context, reasoning, retries and tool work. Delegate an exact unresolved math, mapping, hook or integration problem to a stronger model only if a bounded attempt and independent review cannot settle it; keep routine scheduling on the cheap coordinator.

## 1. Establish state once

1. Inspect current refs, dirty files and managed worktrees. Read the latest authoritative resume record and queued open slices. Do not reread every chat, every report or the whole source tree on each restart.
2. Confirm accepted snapshots and reviews by their exact identities. Preserve immutable evidence and earlier verdicts. Transfer only unresolved work; a stale summary is not a reason to redo accepted evidence.
3. Name one source-preparation owner and one integration/build owner. They may be the same role when their queues do not overlap. Shared sources/cache stay outside disposable worktrees or the owner checkout must be retained explicitly. Git shares history, not ignored source trees.
4. The preparation owner verifies existing exact-version artifacts, mapping alignment and resources before regenerating missing material. Publish canonical roots/readiness manifests and hashes once. Consumers verify the manifests and files they cite; no repeated whole-tree hashing or competing decompilation.
5. Keep a frozen source/report base isolated from implementation integration. Blind workers must not inspect implementation diffs during a main merge. They commit report-only work from that base; the integration owner performs the required default-branch merge and semantic review before completed work is handed off. Record that integration separately from the source evidence. Give source workers only source-safe guidance corrections, not mixed-lane coordinator state.

## 2. Schedule ready work with backpressure

Each exact interval has one source owner and one report. That owner works through bounded named slices and their dependencies, following the full inventory contract; do not split overlapping writes among several workers. A useful starting assignment is at most three method pairs or roughly 300 lines per side, without cutting off guards or dependencies. The next assignment continues the recorded queue rather than restarting the pair.

Prefer, in order: resolve blockers on nearly closed slices; review evidence-ready findings; reconcile accepted findings with code; integrate accepted patches; then open more source intervals. Reserve capacity for independent review and the serial integration/build owner. Do not fill every slot with discovery while accepted findings wait.

The cap is a ceiling, not an occupancy target. For a new run without a specified cap, start with about 6–8 total roles including coordination, and adjust using completed accepted work and error rate. This is an experimental starting point, not a measured optimum. Within the human's cap, reduce active work when routing errors, tool retries or review backlog increase; record the choice. Do not interpret extra app chats as unlimited backend capacity.

Use internal agents by default; create user-visible chats only when explicitly requested. Reuse authorized healthy workers for compatible bounded assignments. Replace a worker for contamination, unusable context or persistent failure, with a committed checkpoint first. No nested managers unless an explicitly assigned domain needs them.

## 3. Dispatch a small, complete contract

Send only the role's inputs. Aim for a few hundred words plus links, rather than repeating the campaign history. Do not omit required safety/evidence gates to meet a word limit.

Every assignment includes:

- Role, exact interval/finding/slice IDs, branch/base SHA and writable report/code paths.
- Required workflow links; canonical readiness manifests and source roots.
- Exact input snapshot/review identities, or the source-only next slice and open dependency IDs.
- Permitted reads and prohibited lanes; permitted checks; exclusive writer/build ownership.
- Concrete output, completion criteria and next-action checkpoint. No additional delegation or messaging unless authorized.

Source workers/reviewers receive vanilla source, their own source report and source-safe guidance only. They do not receive this mixed-lane retrospective, implementation/backlog reports, coordinator state or either wiki lane before full-pair freeze. Minecraft Wiki and MCPK have separate owners and wiki-aware reviewers. Wiki hints never feed blind source workers.

Implementation workers receive independently accepted immutable findings, exact source/boundary evidence and current implementation. Their first step is to prove existing coverage or isolate a missing delta; a correct no-code disposition is useful output. Technical review checks source fidelity, resolver direction, hook descriptors/order, catalog registration and native fallback. Do not introduce an extra exact-profile gate that defeats closest-change resolution.

### Pasteable source assignment

> Compare exact <A> → <B>, slices <IDs>, from source/report base <SHA>. Read repository guidance, discovery and campaign protocols. Own only <report path> on <branch>; vanilla sources at <roots>, readiness manifests <paths>. Verify cited files and follow the recorded producer/consumer dependencies. No implementation, wiki or mixed-lane report reads before full-pair freeze. Preserve exact math/order and record both-side ranges, hashes, reachable player path, scope disposition and remaining dependencies. No decompile/build/tests/runtime, shared writes or further delegation. Commit a source-only checkpoint and return the compact handoff below. A partial result must identify the next bounded slice; do not claim pair completion. Main integration belongs to the integration owner so it cannot expose you to implementation.

### Pasteable implementation assignment

> Reconcile accepted finding <ID>, snapshot <commit/path/hash>, blind review <identity>, boundary evidence <path>, against code base <SHA> using the implementation workflow. Own <paths/branch>. Prove existing coverage or implement one minimal independent delta, with exact arithmetic/gates, resolver applicability, registration and native fallback. Commit the candidate and hand it to an independent technical reviewer; the build/integration owner handles accepted integration. No source-only owner contact, shared-source writes, tests/runtime or additional delegation. Return exact refs, disposition, required checks and unresolved cases.

## 4. Consume events, not repeated status narratives

Workers announce an actionable checkpoint: accepted-ready finding, blocked dependency, review verdict, committed patch, build result or terminal partial handoff. Routine commentary does not require an instruction back.

Use compact bounded waits with cursors, up to the tool's supported target count. Maintain a rotating batch for larger rosters. Inspect full thread output only for an unexpected result or a missing input; do not repeatedly enumerate the sidebar. Avoid immediate repeated polling of unchanged workers. A batch wait can be bounded to 60 seconds so the coordinator remains responsive.

An uncertain launch/send result must be recovered by checking its operation/thread state before retrying. Reserve its slot until reconciled. One owner investigates a shared infrastructure incident and returns a concrete recovery action; other workers checkpoint unaffected work. Do not broadcast restart instructions to every chat or recreate the roster after one transient error.

Keep one authoritative queue, owned by the coordinator or one designated operations worker. Do not maintain several independently rewritten roster/backlog/status copies. Preserve existing historical ledgers; transition to one current index linking their exact entries. A compact task entry needs role, ID, state, branch/HEAD, source or snapshot identity, dependency IDs, next action and last delivered cursor. Store large evidence in the role's report, not in the queue.

### Compact handoff

```text
role / interval / slice or finding IDs:
state: active | ready-for-review | revision-required | accepted | blocked | terminal-partial
branch / HEAD / worktree:
report or candidate path / immutable input identities:
closed dependencies / remaining dependency IDs:
checks performed / checks not authorized:
next exact action:
```

## 5. Review and integrate bounded batches

Independent source acceptance binds to exact finding bytes and closed finding-specific dependencies. Full-pair coverage audit is a separate task and checks all required inventories, not a sample of findings. Mechanical structure checks precede review; they do not prove source truth.

Fix metadata-only errors in metadata, preserving accepted source evidence. Record an amended binding/review for corrected identity; reserve full source re-review for changed evidence, dependency closure or provenance contamination. Never relabel old rejected bytes as accepted.

Assign code changes that touch the same injection/catalog area serially, or reserve their integration to the single owner. Build small accepted batches together when their conflicts and review bases are understood; do not wait for every interval to finish. After default-branch integration or any semantic correction, technical review must cover the resulting affected code. Exact accepted input identities and the integrated diff remain traceable.

The integration owner merges the default branch, checks new commits for semantic conflicts, reconciles mixin/registration overlap, and builds with all Gradle Test tasks disabled plus `-x test`. Reuse the build record for that exact code tree; docs-only changes do not require another build. Batch failure is repaired or isolated before acceptance. Recheck artifacts only after a new build/change or a concrete integrity concern, not on each coordinator wakeup. Runtime remains unverified unless separately authorized and performed.

Do not delete old implementations for blind discovery. If a separately authorized reset occurs, preserve the removed-behavior inventory in an implementation lane and reconcile every entry after source freeze. This prevents a partial catalog from becoming the acceptance scope of a complete replacement.

## 6. Stop, resume and measure

Before a deadline, stop dispatching new slices, collect committed partial checkpoints, finish review/integration already safely underway, and write one current resume index. Report open dependencies and discovery/implementation/runtime states accurately. Do not spend the closing window creating many duplicate summaries or cleaning unrelated refs. Cleanup/push are separate authorized operations.

For the next two comparable bounded batches, record accepted closed slices/findings, implemented/no-code dispositions, remaining coverage rows, worker active time, coordinator tool calls/context size, review revisions, preparation/build durations and infrastructure retries. Record token usage/cost only where actually available. Compare time and cost **per accepted unit**, with scope and review quality held constant, rather than raw concurrency or registration count. Raise concurrency or reduce reasoning effort only if acceptance quality remains stable. There is no measured savings percentage yet.

### Pasteable coordinator prompt

> Coordinate <authorized scope> using workflows/orchestration/README.md. Use a very cheap model for routine coordination and bounded work; configure the model outside worker prose. Work autonomously within <permissions>, <cap>, <deadline> and <integration target>. Start from <current resume index>, preserve existing code and accepted evidence, and schedule the smallest ready units. Keep blind source and wiki/implementation lanes isolated. Delegate preparation, reviews, code and serial integration/builds with exact inputs/outputs. Maintain one concise queue, wait for actionable events, and escalate only a specific unresolved technical problem. Integrate accepted batches without waiting for all pairs. Report discovery, implementation and runtime separately; leave precise committed resume points when stopping.
