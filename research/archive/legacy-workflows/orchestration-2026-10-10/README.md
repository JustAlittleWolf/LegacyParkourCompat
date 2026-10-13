# Current movement campaign coordination

Use [live-queue.json](live-queue.json) as the authoritative dispatch and resume record. Verify current refs, owned worktree state, chat events and outcomes before acting; captured values are checkpoints, not a replacement for reconciliation.

The coordinator owns this directory on `feat/campaign-coordination-2026-10-10`. The serial integration owner exclusively controls the primary checkout, its index, local main integration and builds. Source workers retain their blind author branches. Integration must preserve source-only snapshots and main-only accepted review records.

The active campaign-chat cap is twelve, including the coordinator and coordination/status chats. The four internal collaboration-tool slots are a separate limit and do not reduce the chat cap. Count active campaign chats; queued assignments are not active. Review and revision closure precede new discovery. The source-preparation owner remains reserved; published cache consumers are read-only.

The live queue retains its original capture and appends timestamped reconciliations of refs, chat outcomes, immutable finding identities and observed chat states. Treat every roster as a point-in-time snapshot; reconcile it before dispatch rather than treating captured states as current. The 2026-10-09 queue and branch-resume manifests are historical evidence. Their owner handles, phases, next actions and HEAD values may be stale. Branch cleanup was completed once; no archival, ref deletion, history rewriting or worktree cleanup is part of ongoing coordination.

On pause, stop dispatch and owned work, collect committed checkpoints, verify stopped processes and journal next actions. Do not infer stopped process state from a clean checkout. No tests or game/TAS/Gym/server/Docker launches or pushes are authorized. Source acceptance, implementation, compilation and runtime parity remain separate.

## Integration publication

The policy, typical-admission report, F004 R2 source disposition and captured queue publication are integrated locally through `46b5e4876413ea0d231ed915998567bd980a208c`. The queue preserves its 08:37 UTC observation; reconcile live state before dispatch. Queue JSON, local Markdown links and whitespace checks passed. No build, tests, decompile, runtime launch or push was performed for that batch.

The 2026-10-10 report-only batch is integrated on local `main` through `3e1f7edd5f63471a8c42f2f4da4885e5667bbd73`; its inputs, partial-pair statuses, review verdicts and 09:07 UTC chat-state snapshot are recorded in [live-queue.json](live-queue.json). The queue retains all 26 earlier source entries. F-003 is accepted at finding scope only; F-09 remains revision-required; the 1.19.3 profile split remains a documentation proposal. No implementation, build, tests, decompile, runtime launch or push was performed for this batch. The F-09 review's source whitespace is preserved byte-for-byte.
