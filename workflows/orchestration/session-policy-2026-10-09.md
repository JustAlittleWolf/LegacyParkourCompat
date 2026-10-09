# Session policy log — 2026-10-09

## Current operating policy

- Target about 15 active campaign workers, excluding the coordinator and unrelated chats. This is an occupancy target, not a hard maximum. The coordinator launches or reassigns ready work to approach it without weakening source isolation, ownership, review or other campaign gates.
- Use a fresh worker for an unrelated assignment. Continue the same assignment with its existing worker. Preserve ongoing work when policy changes; do not restart it.
- Use isolated worktrees for concurrent workers. One designated owner exclusively handles integration, the Git index and builds. Promptly integrate coherent accepted batches into local `main`. Do not push.
- Worker assignments omit worker-count and subagent instructions. Workers decide whether to use subagents; coordinators do not proactively ask them to spawn.
- Preserve existing source blindness, exact immutable finding bindings, independent review, and the test, game, TAS, Gym, server and Docker launch authorization rules.

## Superseded policy

- The earlier 15-active-worker hard cap is superseded by the occupancy target above.
- The earlier policy of reusing healthy workers for compatible new assignments is superseded. Reuse the current worker only to continue the same assignment; use a fresh worker for unrelated work.
