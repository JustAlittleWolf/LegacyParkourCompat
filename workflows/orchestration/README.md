# Orchestrating movement research

Use this workflow to coordinate an authorized movement campaign through [discovery](../movement-discovery/README.md), [campaign handoff](../source-campaign-2026-10-07/README.md) and [implementation](../fix-implementation/README.md).

## Goal and authority

Deliver accepted, minimal historical player-movement deltas toward deterministic parity for fixed profiles. Track discovery, implementation and runtime validation separately. A partial pair can supply an accepted finding; finishing that finding does not complete the pair.

At startup record the authorized scope, release endpoints, permitted checks, chat roster, worker occupancy target and any explicit concurrency constraints, integration target and stop condition. Read the global/project guidance and current resume index. Resume paused work only when explicitly authorized.

Use a very cheap model for routine orchestration and bounded worker assignments. Configure model/effort in the launch settings, not in worker messages. **Suggested starting choice: [GPT-6 Luna High](https://developers.openai.com/api/docs/models/gpt-6-luna).** Escalate a specific math, mapping, hook or integration problem to a stronger model only when a bounded attempt and independent review cannot settle it.

## 1. Establish state once

1. Inspect current refs, dirty files and managed worktrees. Read the latest authoritative resume record and queued open slices. Do not reread every chat, every report or the whole source tree on each restart.
2. Confirm accepted snapshots and reviews by their exact identities. Preserve immutable evidence and earlier verdicts. Transfer only unresolved work; a stale summary is not a reason to redo accepted evidence.
3. Name one source-preparation owner and one integration/build owner. They may be the same role when their queues do not overlap. Shared sources/cache stay outside disposable worktrees or the owner checkout must be retained explicitly. Git shares history, not ignored source trees.
4. The preparation owner verifies existing exact-version artifacts, mapping alignment and resources before regenerating missing material. Publish canonical roots/readiness manifests and hashes once. Consumers verify the manifests and files they cite; no repeated whole-tree hashing or competing decompilation.
5. Keep a frozen source/report base isolated from implementation integration. Blind workers must not inspect implementation diffs during a main merge. They commit report-only work from that base; the integration owner performs the required default-branch merge and semantic review before completed work is handed off. Record that integration separately from the source evidence. Give source workers only source-safe guidance corrections, not mixed-lane coordinator state.

## 2. Schedule ready work with backpressure

Each exact interval has one source owner and one report. That owner works through bounded named slices and their dependencies, following the full inventory contract; do not split overlapping writes among several workers. A useful starting assignment is at most three method pairs or roughly 300 lines per side, without cutting off guards or dependencies. The next assignment continues the recorded queue rather than restarting the pair.

Prefer, in order: resolve blockers on nearly closed slices; review evidence-ready findings; reconcile accepted findings with code; integrate accepted patches; then open more source intervals. Reserve capacity for independent review and the serial integration/build owner. Do not fill every slot with discovery while accepted findings wait.

For this campaign, keep **about 15 active campaign workers** across preparation, source, wiki, review, implementation, integration and operations. Exclude the coordinator and unrelated chats from this count. Treat 15 as an occupancy target, not a hard maximum: launch or reassign ready work to approach the target while preserving source isolation, exclusive ownership, independent review and all other campaign gates. Do not relax a gate to fill a slot.

Use a fresh worker for each new, unrelated assignment. Continue an existing assignment with its current worker, and preserve ongoing work when the session policy changes; do not restart it. Give concurrent workers isolated worktrees. Keep the integration owner as the exclusive owner of integration, the Git index and builds. Workers may decide whether to use subagents; do not include worker-count or subagent instructions in their assignments and do not proactively ask them to spawn subagents. Replace a worker for contamination, unusable context or persistent failure only after collecting its committed checkpoint.

Keep the date-specific policy record in the [session policy log](session-policy-2026-10-09.md); follow the operating rules in this workflow.

## 3. Dispatch a small, complete contract

Include a detail only if it changes this worker's action, evidence, constraints or output. State the instruction directly; omit user-message attribution, conversational history, coordinator reasoning, other workers' status and model identity. Preserve exact inputs, dependencies and required gates.

Apply the same rule to workflow documentation: describe what to do and when, without explaining which chat or correction introduced it. Keep historical analysis in retrospective records.

For a stop instruction, send:

> Stop this assignment now. Do not start another slice. Preserve current edits and commit a checkpoint if possible. Return branch/HEAD, report path, open dependencies and the next exact action. If committing is blocked, report the file paths and blocker.

For a continuation, send only the changed task state:

> Continue <slice IDs> from <checkpoint SHA>, using <report path> and its dependency queue. Preserve <accepted snapshot identities>. Return <required output>.

Do not prepend “the user said to stop all work” or repeat unrelated campaign instructions. Include a reason only when the worker needs it to choose the correct action.

Every assignment includes:

- Role, exact interval/finding/slice IDs, branch/base SHA and writable report/code paths.
- Required workflow links; canonical readiness manifests and source roots.
- Exact input snapshot/review identities, or the source-only next slice and open dependency IDs.
- Permitted reads and prohibited lanes; permitted checks; exclusive writer/build ownership.
- Concrete output, completion criteria and next-action checkpoint. No additional messaging unless authorized.

Source workers/reviewers receive vanilla source, their own source report and source-safe guidance only. They do not receive process retrospectives, implementation/backlog reports, coordinator state or either wiki lane before full-pair freeze. Minecraft Wiki and MCPK have separate owners and wiki-aware reviewers. Wiki hints never feed blind source workers.

Implementation workers receive independently accepted immutable findings, exact source/boundary evidence and current implementation. Their first step is to prove existing coverage or isolate a missing delta; a correct no-code disposition is useful output. Technical review checks source fidelity, resolver direction, hook descriptors/order, catalog registration and native fallback. Do not introduce an extra exact-profile gate that defeats closest-change resolution.

### Pasteable source assignment

> Compare exact <A> → <B>, slices <IDs>, from source/report base <SHA>. Read repository guidance, discovery and campaign protocols. Own only <report path> in <isolated worktree> on <branch>; vanilla sources at <roots>, readiness manifests <paths>. Verify cited files and follow the recorded producer/consumer dependencies. No implementation, wiki or mixed-lane report reads before full-pair freeze. Preserve exact math/order and record both-side ranges, hashes, reachable player path, scope disposition and remaining dependencies. No decompile/build/tests/runtime or shared writes. Commit a source-only checkpoint and return the compact handoff below. A partial result must identify the next bounded slice; do not claim pair completion. Main integration belongs to the integration owner so it cannot expose you to implementation.

### Pasteable implementation assignment

> Reconcile accepted finding <ID>, snapshot <commit/path/hash>, blind review <identity>, boundary evidence <path>, against code base <SHA> using the implementation workflow. Own <paths> in <isolated worktree> on <branch>. Prove existing coverage or implement one minimal independent delta, with exact arithmetic/gates, resolver applicability, registration and native fallback. Commit the candidate and hand it to an independent technical reviewer; the build/integration owner handles accepted integration. No source-only owner contact, shared-source writes or tests/runtime. Return exact refs, disposition, required checks and unresolved cases.

## 4. Consume events, not repeated status narratives

Workers announce an actionable checkpoint: accepted-ready finding, blocked dependency, review verdict, committed patch, build result or terminal partial handoff. Routine commentary does not require an instruction back.

Maintain a cursor for every active roster member. Wait concurrently across the full roster using `wait_threads` batches of at most eight targets each, with a bounded timeout. A completion or needs-attention event from any batch ends the blocking scheduling wait; do not wait for the other batches before acting. Immediately sweep the full roster with nonblocking snapshots (`timeoutMs: 0`) to catch other completions, then consume the results, route ready reviews and integrations, and refill eligible assignments before blocking again. Never wait on one selected chat while unrelated ready work is idle. Keep status rendering outside this scheduling cycle. Inspect full thread output only for an unexpected result or a missing input; do not repeatedly enumerate the sidebar or immediately repoll unchanged workers.

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

The integration owner alone controls integration, the Git index and builds. Promptly integrate coherent accepted batches into local `main` after checking new commits for semantic conflicts and reconciling mixin/registration overlap. Build with all Gradle Test tasks disabled plus `-x test`. Reuse the build record for that exact code tree; docs-only changes do not require another build. Batch failure is repaired or isolated before acceptance. Recheck artifacts only after a new build/change or a concrete integrity concern, not on each coordinator wakeup. After local-main integration or any semantic correction, technical review must cover the resulting affected code. Exact accepted input identities and the integrated diff remain traceable. Runtime remains unverified unless separately authorized and performed. Do not push.

Do not delete old implementations for blind discovery. If a separately authorized reset occurs, preserve the removed-behavior inventory in an implementation lane and reconcile every entry after source freeze. This prevents a partial catalog from becoming the acceptance scope of a complete replacement.

## 6. Stop, resume and measure

Before a deadline, stop dispatching new slices, collect committed partial checkpoints, finish review/integration already safely underway, and write one current resume index. Report open dependencies and discovery/implementation/runtime states accurately. Do not spend the closing window creating many duplicate summaries or cleaning unrelated refs. Cleanup/push are separate authorized operations.

For comparable bounded batches, record accepted closed slices/findings, implemented/no-code dispositions, remaining coverage rows, worker active time, coordinator tool calls/context size, review revisions, preparation/build durations and infrastructure retries. Record token usage/cost where available. Compare time and cost **per accepted unit**, with scope and review quality held constant. Increase active work toward the occupancy target or reduce reasoning effort only if acceptance quality remains stable.

### Pasteable coordinator prompt

> Coordinate <authorized scope> using workflows/orchestration/README.md. Use a very cheap model for routine coordination and bounded work; configure the model outside worker messages. Keep about 15 active campaign workers, excluding the coordinator and unrelated chats; this is an occupancy target, not a hard maximum. Launch or reassign ready work to approach it without relaxing isolation, ownership or review gates. Use fresh workers for unrelated assignments and continue the same assignment with its existing worker; preserve ongoing work. Work within <permissions>, <deadline> and local <integration target>. Start from <current resume index>, preserve existing code and accepted evidence, and schedule the smallest ready units. Keep blind source and wiki/implementation lanes isolated, and use isolated worktrees for concurrent workers. Give each worker only its action, exact inputs, relevant constraints and required output; omit worker-count and subagent instructions. Workers decide whether to use subagents. Assign one exclusive integration, Git-index and build owner, and integrate coherent accepted batches promptly into local main. Maintain one concise queue. Wait concurrently across the full active roster using `wait_threads` batches of at most eight targets, each with cursors. Let the first completion or needs-attention event end the blocking wait, then sweep the full roster with nonblocking snapshots, consume results, route ready reviews/integration and refill eligible assignments before blocking again. Never wait on one selected chat while unrelated ready work is idle; keep status rendering outside this scheduling cycle. Escalate specific unresolved technical problems. Report discovery, implementation and runtime separately; leave committed resume points when stopping. Do not push.
