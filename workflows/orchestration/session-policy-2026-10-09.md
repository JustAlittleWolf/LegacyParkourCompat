# Session policy log — 2026-10-09

## Current operating policy

- Target about 15 active campaign workers, excluding the coordinator and unrelated chats. This is an occupancy target, not a hard maximum. The coordinator launches or reassigns ready work to approach it without weakening source isolation, ownership, review or other campaign gates.
- Use a fresh worker for an unrelated assignment. Continue the same assignment with its existing worker. Preserve ongoing work when policy changes; do not restart it.
- Use isolated worktrees for concurrent workers. One designated owner exclusively handles integration, the Git index and builds. Promptly integrate coherent accepted batches into local `main`. Do not push.
- Worker assignments omit worker-count and subagent instructions. Workers decide whether to use subagents; coordinators do not proactively ask them to spawn.
- Wait concurrently across the full active roster using `wait_threads` batches of at most eight targets, each with cursors. The first completion or needs-attention event ends the blocking wait; immediately sweep the roster with nonblocking snapshots to catch other completions, consume results, route ready reviews/integration and refill eligible assignments before blocking again. Do not wait on one selected chat while other ready work is idle, and keep status rendering outside this scheduling cycle.
- Schedule closure-first: finish prerequisite preparation, ready reviews/revisions and accepted integrations before new discovery. Preserve independently accepted evidence and completed no-code dispositions.
- Maintain one authoritative live queue. Before and after dispatch, merge, build or publication, record phase, exact inputs, branch/worktree/HEAD, dirty files, process/staging state, cursor and next action. Publish readiness atomically. On pause, stop dispatch, halt owned processes and delegates, save checkpoints and verify all workers stopped. On resume, reconcile journals and outcomes before retrying to prevent duplicate work or mutation; retain immutable history/checkpoints without maintaining duplicate live state.
- Give every mutating worker a concrete absolute owned worktree and branch. Verify Git top-level and expected branch before staging, checkout, merge or build; stop on mismatch. The primary checkout/index belongs exclusively to the integration owner; documentation and status workers also use isolated worktrees. Lane-restricted source/wiki workers avoid whole-main diffs/merges that expose excluded lanes; after verdict, the integration owner handles report-only default-branch merge and semantic inspection while preserving the blind author checkpoint. Record worktree, branch, lane boundary and checkpoint verification in pause/resume journals.
- Preserve existing source blindness, exact immutable finding bindings, independent review, and the test, game, TAS, Gym, server and Docker launch authorization rules.
- Resolve routine technical or evidence questions from exact sources, repository rules and existing authorization; do not ask the user to decide them. For a genuine evidence or permission blocker, report the specific unresolved point, exact paths and a proposed safe action to the coordinator, then continue independent authorized work.

## Completed branch retirement

Retire redundant local refs after completion and integration. Verify ancestry in main or exact preservation of every changed blob, record the full branch tip, and check worker completion and worktree state. Preserve active and unfinished assignment refs. For completed attached worktrees, preserve the identical detached commit and all files before deleting the ref; directory removal and remote-ref cleanup are separate operations.

## Superseded policy

- The earlier 15-active-worker hard cap is superseded by the occupancy target above.
- The earlier policy of reusing healthy workers for compatible new assignments is superseded. Reuse the current worker only to continue the same assignment; use a fresh worker for unrelated work.
