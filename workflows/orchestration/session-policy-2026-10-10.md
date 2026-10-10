# Session policy log — 2026-10-10

## Current operating policy

- Use GPT-6.1 Sol Medium for routine orchestration. Configure model and effort in launch settings, not worker messages; escalate a bounded technical issue only when the available evidence and independent review cannot settle it.
- Enforce a hard cap of 12 active campaign agents total, including the coordinator, delegates and subagents. Respect any lower actual runtime capacity. Do not dispatch work that would exceed the applicable cap.
- Use a fresh worker for each unrelated assignment; continue the same assignment with its existing worker. Preserve ongoing work when policy changes.
- Use isolated worktrees for concurrent writers. The integration owner exclusively controls integration, the primary checkout, Git index and builds. Preserve source/cache isolation and blind source/wiki lanes.
- Keep one authoritative live queue. Record exact inputs, branch/worktree/HEAD, dirty files, process/staging state, cursor, lane boundary and next action around dispatches, merges, builds and publication. Publish readiness atomically.
- Schedule closure-first and use event-driven waits across the active roster. On pause, stop dispatch and verify all owned work/processes are stopped. On resume, reconcile journals and actual repository state before retrying, preventing duplicate work or mutations.
- Preserve exact source evidence, independent reviews, no-code dispositions and blind author checkpoints. Report discovery, implementation and runtime states separately.
- Follow the repository authorization rules: no tests, builds, runtime launches, remote changes or pushes unless specifically authorized.
