# Documentation integration checkpoint — 2026-10-09

## Refs

- Integration target before merge: `main` at `e890a9f59883ed167a1958c0d06a665b88cc3018`.
- Reviewed documentation branch: `fix/orchestrator-workflow` at `a6e4d9b344494fcea85532191c29170d3375e2c8`.
- Merge base: `e890a9f59883ed167a1958c0d06a665b88cc3018`.
- Integration branch: `fix/campaign-integration-2026-10-09`, with the integration record committed on top of the reviewed branch tip. The final integration is a fast-forward of `main` to this branch tip.

## Integrated paths

The reviewed branch changes only documentation and campaign records:

- `AGENTS.md`
- `README.md`
- `workflows/README.md`
- `workflows/fix-implementation/README.md`
- `workflows/fix-implementation/major-campaign.md`
- `workflows/movement-discovery/README.md`
- `workflows/movement-discovery/source-navigation.md`
- `workflows/movement-discovery/source-preparation.md`
- `workflows/orchestration/README.md`
- `workflows/orchestration/retrospective-2026-10-09.md`
- `workflows/orchestration/review-metrics.json`
- `workflows/source-campaign-2026-10-07/README.md`

## Review and disposition

- Verified the checkout was clean and `main` matched the expected pre-merge commit.
- Reviewed the orchestration guide and its discovery, implementation, campaign handoff and source-preparation references, plus the full branch path list and documentation diff.
- No semantic conflict with repository movement scope or implementation invariants was found. The updates preserve vanilla block states, exact-source evidence, independent minimal deltas, resolver behavior, source-only discovery boundaries, separate status tracking, and the requirement to disable all Gradle `Test` tasks for campaign builds.
- The orchestration guide's chat-roster directions are campaign-specific operating guidance; actual chat creation remains subject to the controlling app and user authorization rules.
- `git diff --check main...fix/orchestrator-workflow` passed. No build or runtime check was needed for this documentation-only merge.

## Handoff

The documentation branch and this checkpoint are committed on the task branch. Fast-forward `main` to the task branch, then await an accepted implementation batch before taking on any production-code integration or build work.

## Shared launch incident — 2026-10-09

`create_thread` returned a client ID without an error for each launch. The local app-server log contains a matching `thread/start` event for each, and `read_thread` resolves the resulting exact IDs even though `list_threads(limit: 50)` omits all four. Do not recreate these launches. Use `read_thread` with the recovered thread ID and `hostId: local` for follow-up.

- **Preparation:** client ID `aa9a34f4-1247-4f43-a2e3-e6376be60cb4`; thread `01a12183-c4f8-70e1-8d32-eafe7a37868d`; idle with turn completed. Worktree `C:/Users/Wolfi/.codex/worktrees/bfa3/LegacyParkourCompat`; `fix/source-readiness-2026-10-09` at `b809e54f`, clean.
- **Source ledger:** client ID `955cc4de-b13d-4903-94c7-bb9c0e99fcac`; thread `01a12184-5ece-7f23-be02-9f6407b89de4`; active with turn in progress. Worktree `C:/Users/Wolfi/.codex/worktrees/2442/LegacyParkourCompat`.
- **Sprint collision:** client ID `3134b2a6-87f2-4432-97e0-5c3a2b01849b`; thread `01a12184-70c0-7fb3-a3a0-f6032ef005f1`; idle with turn completed. Worktree `C:/Users/Wolfi/.codex/worktrees/dcfd/LegacyParkourCompat`; `fix/reconcile-sprint-collision-2026-10-09` at `bba3c42691c2bb1527ce98dc5ff942e35bb31690`, clean.
- **Elytra cosine:** client ID `889c32ad-6dbe-4984-a857-ae2e38917f11`; thread `01a12184-84a1-7f31-bbeb-f9a0a6c1cc0b`; idle with turn completed. Worktree `C:/Users/Wolfi/.codex/worktrees/f6a5/LegacyParkourCompat`; `fix/reconcile-elytra-cosine-2026-10-09` at `9be6ac9`, clean. Its record leaves the release boundary open and notes a mapped-jar hash mismatch requiring evidence correction.

Release the preparation, sprint-collision and Elytra concurrency slots. Retain the source-ledger slot while its turn remains active. No launch retry, app-state mutation, test or runtime action was performed.
