# Major-version delta implementation campaign

Status: all fifteen fresh GPT-6 Luna High workers completed and their frozen tips are merged into `feat/major-movement-final-integration` by the final GPT-6.1 Sol Low integration owner. The clean removal `ebe56a21d17d120f11f8ede5b6a3d49bea7c7e41` remains authoritative. Superseded implementations and coverage claims were not used.

[major-campaign.json](major-campaign.json) records all fifteen branch tips and full commits, the integration implementation checkpoint and final branch ref. [final-integration.md](final-integration.md) records hook reconciliation, source evidence, exclusions, build and validation limits. Minor discovery and runtime movement validation remain deferred. The zero-confirmed 1.21.11 to 26.1.2 disposition is not an equivalence claim.

## Source handoff

Read-only canonical staging: `C:/Users/Wolfi/.codex/worktrees/df7f/LegacyParkourCompat/build/stable-shared-minecraft`, with sources/, ready/ and cache/. Follow exact-id/namespace markers and hashes. [The retained inventory](../../docs/shared-minecraft-source-staging.md) covers 48 sets and 187,230 Java files. The source writer is stopped. Preserve the source-owner worktree and ignored artifacts; do not clean, archive, copy or regenerate the source trees. D: source/cache/marker writes remain stopped. The disappearance cause is unknown and has not been attributed to worktree lifecycle.

## Worker checklist

For each assigned A→B range:

1. Read the root and project `AGENTS.md` and `README.md`, this workflow, and the assigned discovery run and findings.
2. Use the clean baseline. Re-establish each finding's scope, exact version boundary, and source evidence; do not reuse prior implementation code, coverage dispositions, or unverified endpoint sources.
3. Implement only independently scoped, in-scope, source-confirmed deltas. A finding is not “already covered” based on the superseded campaign.
4. Build with all Gradle `Test` tasks disabled and `-x test`. Do not run tests, gameplay/TAS, or Docker without a separate explicit request.
5. Commit the work on the worker branch and report its branch, commit, finding dispositions, evidence markers/hashes, build result, and unresolved questions to the coordinator. The coordinator updates that worker's status in `major-campaign.json` after the handoff is complete.

## Integration gate and checklist

The all-fifteen-complete gate was met before the new GPT-6.1 Sol Low integration chat was dispatched. All fresh worker histories and the clean baseline are retained.

After the gate, integration should review every finding's source provenance, numeric semantics, player path, and boundary; reconcile overlapping mixin injection points, mechanic keys, variants, and registrations; inspect native fallback and historical content limits; merge the default branch and review newly landed changes for semantic conflicts; then build with all tests excluded and record remaining runtime questions. Compilation alone does not establish tick-level parity.

## Superseded campaign record

The previous coordinator was `feat/major-movement-integration`; its old roster is preserved under `supersededCampaign.chatRosterForRecoveryOnly` in [major-campaign.json](major-campaign.json). Those chats and branches are recovery references only and must not be used as implementation or coverage inputs. The current fresh roster replaces them.
