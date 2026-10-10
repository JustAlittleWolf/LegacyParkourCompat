# Orchestrating movement research

Use this workflow to coordinate an authorized movement campaign through [discovery](../movement-discovery/README.md), [campaign handoff](../source-campaign-2026-10-07/README.md) and [implementation](../fix-implementation/README.md).

## Goal and authority

Deliver accepted, minimal historical player-movement deltas toward deterministic parity for fixed profiles. Track discovery, implementation and runtime validation separately. A partial pair can supply an accepted finding; finishing that finding does not complete the pair.

At startup record the authorized scope, release endpoints, permitted checks, chat roster, total active-agent cap and any lower runtime capacity, integration target and stop condition. Read the global/project guidance and current resume index. Resume paused work only when explicitly authorized.

Use **GPT-6.1 Sol Medium** for routine orchestration. Configure model/effort in launch settings, not in worker messages. Escalate a specific math, mapping, hook or integration problem only when a bounded attempt and independent review cannot settle it.

## 1. Establish state once

1. Inspect current refs, dirty files and managed worktrees. Read the latest authoritative resume record and queued open slices. Do not reread every chat, every report or the whole source tree on each restart.
2. Confirm accepted snapshots and reviews by their exact identities. Preserve immutable evidence and earlier verdicts. Transfer only unresolved work; a stale summary is not a reason to redo accepted evidence.
3. Name one source-preparation owner and one integration/build owner. They may be the same role when their queues do not overlap. Shared sources/cache stay outside disposable worktrees or the owner checkout must be retained explicitly. Git shares history, not ignored source trees.
4. The preparation owner verifies existing exact-version artifacts, mapping alignment and resources before regenerating missing material. Publish canonical roots/readiness manifests and hashes once. Consumers verify the manifests and files they cite; no repeated whole-tree hashing or competing decompilation.
5. Keep a frozen source/report base isolated from implementation integration. Lane-restricted source/wiki workers and reviewers must not inspect whole-main diffs or perform merges that can expose excluded lanes. Source workers commit report-only work from their authorized base, preserving the blind author checkpoint. After the independent verdict, the integration owner performs the report-only default-branch merge and semantic inspection; record that integration separately from source evidence. Give each lane only lane-safe inputs.

## 2. Schedule ready work with backpressure

Each exact interval has one source owner and one report. That owner works through bounded named slices and their dependencies, following the full inventory contract; do not split overlapping writes among several workers. A useful starting assignment is at most three method pairs or roughly 300 lines per side, without cutting off guards or dependencies. The next assignment continues the recorded queue rather than restarting the pair.

Use closure-first scheduling. Before opening new discovery, finish ready prerequisite preparation, reviews and requested revisions, then integrate accepted patches and record final implementation or no-code dispositions. Preserve independently accepted evidence and completed no-code dispositions; do not reopen them without new evidence that changes their basis. Reserve capacity for independent review and the serial integration/build owner.

For this campaign, enforce a **hard cap of 12 active campaign agents total**, across preparation, source, wiki, review, implementation, integration and operations. Count the coordinator and delegates/subagents in the cap. If actual runtime capacity is lower, follow that lower limit. Do not dispatch work that would exceed the applicable cap; preserve source isolation, exclusive ownership, independent review and all other campaign gates.

Use a fresh worker for each new, unrelated assignment. Continue an existing assignment with its current worker, and preserve ongoing work when the session policy changes; do not restart it. Use isolated worktrees for every worker that writes files, including documentation and status workers. The primary checkout and its index belong exclusively to the integration owner; never switch the primary branch to author a report. Workers may decide whether to use subagents, provided the total active campaign-agent count stays within the cap; do not include worker-count or subagent instructions in their assignments and do not proactively ask them to spawn subagents. Replace a worker for contamination, unusable context or persistent failure only after collecting its committed checkpoint.

Keep the date-specific policy record in the [current session policy](session-policy-2026-10-10.md); follow the operating rules in this workflow.

## 3. Dispatch a small, complete contract

Include a detail only if it changes this worker's action, evidence, constraints or output. State the instruction directly; omit user-message attribution, conversational history, coordinator reasoning, other workers' status and model identity. Preserve exact inputs, dependencies and required gates.

Resolve routine technical and evidence questions from the exact sources, repository rules and existing authorization; do not route them to the user as clarification questions. If evidence or permissions leave a genuine blocker, report the specific unresolved point, exact affected paths and a proposed safe action to the coordinator, then continue independent work. Do not let one blocked item stop unrelated authorized work.

Apply the same rule to workflow documentation: describe what to do and when, without explaining which chat or correction introduced it. Keep historical analysis in retrospective records.

For a stop instruction, send:

> Stop this assignment now. Do not start another slice. Preserve current edits and commit a checkpoint if possible. Return branch/HEAD, report path, open dependencies and the next exact action. If committing is blocked, report the file paths and blocker.

For a continuation, send only the changed task state:

> Continue <slice IDs> from <checkpoint SHA>, using <report path> and its dependency queue. Preserve <accepted snapshot identities>. Return <required output>.

Do not prepend “the user said to stop all work” or repeat unrelated campaign instructions. Include a reason only when the worker needs it to choose the correct action.

Every assignment includes:

- Role, exact interval/finding/slice IDs, branch/base SHA and writable report/code paths.
- The worker-owned absolute worktree path and assigned branch. Before staging, checkout, merge or build, verify Git's top-level path and expected current branch match the contract; stop on any mismatch.
- Required workflow links; canonical readiness manifests and source roots.
- Exact input snapshot/review identities, or the source-only next slice and open dependency IDs.
- Permitted reads and prohibited lanes; permitted checks; exclusive writer/build ownership.
- Concrete output, completion criteria and next-action checkpoint. No additional messaging unless authorized.

Source workers/reviewers receive vanilla source, their own source report and source-safe guidance only. They do not receive process retrospectives, implementation/backlog reports, coordinator state or either wiki lane before full-pair freeze. Minecraft Wiki and MCPK have separate owners and wiki-aware reviewers. Wiki hints never feed blind source workers.

Implementation workers receive independently accepted immutable findings, exact source/boundary evidence and current implementation. Their first step is to prove existing coverage or isolate a missing delta; a correct no-code disposition is useful output. Technical review checks source fidelity, resolver direction, hook descriptors/order, catalog registration and native fallback. Do not introduce an extra exact-profile gate that defeats closest-change resolution.

### Pasteable source assignment

> Compare exact <A> → <B>, slices <IDs>, from source/report base <SHA>. Read repository guidance, discovery and campaign protocols. Own only <report path> in the worker-owned absolute worktree <absolute path> on <branch>; vanilla sources at <roots>, readiness manifests <paths>. Before staging, checkout, merge or build, verify the Git top-level path and expected current branch match this assignment; stop on mismatch. Verify cited files and follow the recorded producer/consumer dependencies. No implementation, wiki or mixed-lane report reads before full-pair freeze. Preserve exact math/order and record both-side ranges, hashes, reachable player path, scope disposition and remaining dependencies. No decompile/build/tests/runtime or shared writes. Commit a source-only checkpoint and return the compact handoff below. A partial result must identify the next bounded slice; do not claim pair completion. Preserve the blind author checkpoint; after verdict, report-only main integration and semantic inspection belong to the integration owner.

### Pasteable implementation assignment

> Reconcile accepted finding <ID>, snapshot <commit/path/hash>, blind review <identity>, boundary evidence <path>, against code base <SHA> using the implementation workflow. Own <paths> in the worker-owned absolute worktree <absolute path> on <branch>. Before staging, checkout, merge or build, verify the Git top-level path and expected current branch match this assignment; stop on mismatch. Prove existing coverage or implement one minimal independent delta, with exact arithmetic/gates, resolver applicability, registration and native fallback. Commit the candidate and hand it to an independent technical reviewer; the build/integration owner handles accepted integration. No source-only owner contact, shared-source writes or tests/runtime. Return exact refs, disposition, required checks and unresolved cases.

## 4. Consume events, not repeated status narratives

Workers announce an actionable checkpoint: accepted-ready finding, blocked dependency, review verdict, committed patch, build result or terminal partial handoff. Routine commentary does not require an instruction back.

Maintain a cursor for every active roster member. Wait concurrently across the full roster using `wait_threads` batches of at most eight targets each, with a bounded timeout. A completion or needs-attention event from any batch ends the blocking scheduling wait; do not wait for the other batches before acting. Immediately sweep the full roster with nonblocking snapshots (`timeoutMs: 0`) to catch other completions, then consume the results, route ready reviews and integrations, and refill eligible assignments before blocking again. Never wait on one selected chat while unrelated ready work is idle. Keep status rendering outside this scheduling cycle. Inspect full thread output only for an unexpected result or a missing input; do not repeatedly enumerate the sidebar or immediately repoll unchanged workers.

An uncertain launch/send result must be recovered by checking its operation/thread state before retrying. Reserve its slot until reconciled. One owner investigates a shared infrastructure incident and returns a concrete recovery action; other workers checkpoint unaffected work. Do not broadcast restart instructions to every chat or recreate the roster after one transient error.

Keep one authoritative live queue, owned by the coordinator or one designated operations worker. Do not maintain several independently rewritten roster/backlog/status copies; keep immutable history and checkpoints only. A compact task entry needs role, ID, phase/state, exact inputs, branch/worktree/HEAD, dirty files and staging state, owned process state, dependency IDs, last delivered cursor and next exact action. Store large evidence in the role's report, not in the queue.

Keep local branch refs for the default branch and unfinished assignments with an explicit owner, next action and dispatch order. Use the current resume index for live work; dated cleanup manifests and backups are completed history, not live queue entries. Preserve unfinished canonical branch refs and designated archived checkpoint refs. Record accepted evidence and terminal dispositions without retaining a completed worker branch solely as their storage. Any future cleanup is a separate authorized operation; never retire an actively owned unfinished assignment. Restore historical work only from its recorded immutable identity, and verify the selected tip before using it; restoration does not imply that the change is merged or applied.

### Compact handoff

```text
role / interval / slice or finding IDs:
state: active | ready-for-review | revision-required | accepted | blocked | terminal-partial
branch / HEAD / absolute worktree path:
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

Before and after each dispatch, merge, build or publication, update the authoritative queue with the phase, exact inputs, absolute worktree, branch/HEAD, dirty files, process and staging state, last cursor, lane boundary, blind author checkpoint and next exact action. Before staging, checkout, merge or build, confirm `git rev-parse --show-toplevel` equals the assigned absolute worktree and `git branch --show-current` equals the expected current branch; stop on mismatch. For an authorized checkout, record the target ref and verify the resulting branch afterward. Publish readiness atomically: validate the complete artifact before exposing its final manifest or readiness marker.

To pause, stop dispatching, halt coordinator-owned processes and delegated workers safely, save partial work/checkpoints, update the queue, and verify that every worker and owned process has stopped. Do not declare the pause complete until the stop state is reconciled. To resume, reconcile queue entries and worker journals against actual outcomes, refs, files, process state and publications before retrying any action; verify each worker's absolute worktree, Git top-level, expected branch, lane boundary and blind author checkpoint. Stop on any mismatch and resolve uncertain outcomes before retrying so work or mutations are not duplicated. Preserve accepted findings, immutable review bindings and no-code dispositions. Resume with closure-first ordering: finish prerequisite preparation for ready work, complete ready reviews and revisions, integrate accepted patches, then open new discovery. Lane-restricted source/wiki workers must not use whole-main diffs or merges; after the verdict, the integration owner performs report-only default-branch merge and semantic inspection, preserving the blind author checkpoint. Keep discovery, implementation and runtime states accurate.

Before a deadline, stop dispatching new slices, collect committed partial checkpoints, finish review/integration already safely underway, and write one current resume index. Do not spend the closing window creating many duplicate summaries or cleaning unrelated refs. Cleanup/push are separate authorized operations.

For comparable bounded batches, record accepted closed slices/findings, implemented/no-code dispositions, remaining coverage rows, worker active time, coordinator tool calls/context size, review revisions, preparation/build durations and infrastructure retries. Record token usage/cost where available. Compare time and cost **per accepted unit**, with scope and review quality held constant. Keep all staffing within the active-agent cap; adjust effort only if acceptance quality remains stable.

### Pasteable coordinator prompt

> Coordinate <authorized scope> using workflows/orchestration/README.md. Use GPT-6.1 Sol Medium for routine orchestration; configure model and effort outside worker messages. Enforce a hard cap of 12 active campaign agents total, including the coordinator, delegated workers and subagents, or the lower actual runtime capacity. Do not dispatch work above that cap; preserve isolation, ownership and review gates. Use fresh workers for unrelated assignments and continue the same assignment with its existing worker; preserve ongoing work. Work within <permissions>, <deadline> and local <integration target>. Start from <current resume index>, preserve existing code and accepted evidence, and schedule the smallest ready units. Assign every mutating worker a concrete absolute worktree path and branch; require Git top-level/branch verification before staging, checkout, merge or build, and stop on mismatch. Keep the primary checkout and index exclusive to the integration owner; documentation/status workers use isolated worktrees and never switch the primary branch to author reports. Keep blind source and wiki lanes isolated: lane-restricted workers avoid whole-main diffs/merges exposing excluded lanes, and the integration owner performs report-only default-branch merge and semantic inspection after verdict, preserving blind author checkpoints. Give each worker only its action, exact inputs, relevant constraints and required output; omit worker-count and subagent instructions. Workers decide whether to use subagents within the cap. Assign one exclusive integration, Git-index and build owner, and integrate coherent accepted batches promptly into local main. Keep one authoritative live queue and immutable checkpoints only. Journal phase, exact inputs, absolute worktree, branch/HEAD, dirty files, process/staging state, cursor, lane boundary, author checkpoint and next action before and after dispatch, merge, build and publication. Publish readiness atomically. Wait concurrently across the full active roster using `wait_threads` batches of at most eight targets, each with cursors. Let the first completion or needs-attention event end the blocking wait, then sweep the full roster with nonblocking snapshots, consume results, route ready reviews/integration and refill eligible assignments before blocking again. Never wait on one selected chat while unrelated ready work is idle; keep status rendering outside this scheduling cycle. On pause, stop dispatching, halt owned processes and delegates, save checkpoints and verify all workers stopped. On resume, reconcile journals/outcomes and verify worktree, branch and lane boundaries before retrying; use closure-first order: prerequisite prep, ready reviews/revisions, accepted integration, then new discovery. Preserve accepted evidence and no-code dispositions. Escalate specific unresolved technical problems. Report discovery, implementation and runtime separately; leave committed resume points when stopping. Do not push.
