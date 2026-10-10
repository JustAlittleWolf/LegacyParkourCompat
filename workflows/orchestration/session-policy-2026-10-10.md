# Session policy log — 2026-10-10

## Current operating policy

- The coordinator focuses on dispatch, status and admission. New campaign assignments run in separate Codex chats created with `gpt-6-luna` and high thinking; internal collaboration subagents are not used for campaign execution. Existing assignments may finish safely in their current chats.
- Enforce a hard cap of 12 active campaign chats, including the coordinator. Track desktop chat capacity separately from internal tool slots; follow the lower available desktop capacity.
- Use a fresh chat for each unrelated assignment. Give exclusive integration, build and Git-index ownership to a fresh chat for each coherent integration assignment. The primary checkout and index remain untouched by all other workers.
- Use isolated worktrees for concurrent writers. Preserve source/cache isolation and blind source/wiki lanes.
- Admit typical parkour behavior (walking, jumping, collisions, sneaking and ordinary block behavior) to implementation as soon as its immutable source snapshot is independently accepted, required version applicability is established and its dependencies are closed, while other pair discovery continues. World-border behavior, bed enter/exit and analogous nonstandard interactions remain discovery-only for this campaign. Do not remove or roll back existing implementations under this policy.
- Keep one authoritative live queue. Record exact inputs, branch/worktree/HEAD, dirty files, process/staging state, cursor, lane boundary and next action around dispatches, merges, builds and publication. Publish readiness atomically.
- Schedule closure-first and use event-driven waits across the active roster. On pause, stop dispatch and verify all owned work/processes are stopped. On resume, reconcile journals and actual repository state before retrying, preventing duplicate work or mutations.
- Preserve exact source evidence, independent reviews, no-code dispositions and blind author checkpoints. Report discovery, implementation and runtime states separately.
- Follow the repository authorization rules: no tests, builds, runtime launches, remote changes or pushes unless specifically authorized.

See the [orchestration workflow](README.md) for dispatch and integration rules, the [discovery handoff](../movement-discovery/README.md#blind-discovery-and-accepted-snapshots) for snapshot gates, and the [implementation admission rule](../fix-implementation/README.md#early-implementation-admission) for behavior categories.
