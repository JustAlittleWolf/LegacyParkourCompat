# Major-version delta implementation campaign

Status: active with a fresh worker roster. The historical implementations and movement mixins were removed in clean-baseline commit `ebe56a21d17d120f11f8ede5b6a3d49bea7c7e41` on `feat/clean-movement-baseline`. All earlier implementation and coverage dispositions are invalidated. Treat every in-scope, source-confirmed delta as requiring fresh implementation. Prior campaign branches and chats are retained for recovery only; do not inspect their implementations or adopt their coverage claims.

The current roster, chat IDs, comparison endpoints, and per-worker status are tracked in [major-campaign.json](major-campaign.json). It has fifteen fresh implementation chats, all using GPT-6 Luna with High reasoning. The baseline for every worker is `feat/clean-movement-baseline` at the commit above.

## Source handoff

Use the existing shared source tree read-only at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/` and cache at `D:/Javastuff/LegacyParkourCompat/build/minecraft-decompile-cache/`. The ready target reference is `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated/`, with marker `D:/Javastuff/LegacyParkourCompat/build/major-movement-preparation/26.2--unobfuscated.json`. Historical endpoint restoration is ongoing. Confirm the relevant endpoint's successful marker and hashes before relying on that source; do not copy or overwrite published evidence.

## Worker checklist

For each assigned A→B range:

1. Read the root and project `AGENTS.md` and `README.md`, this workflow, and the assigned discovery run and findings.
2. Use the clean baseline. Re-establish each finding's scope, exact version boundary, and source evidence; do not reuse prior implementation code, coverage dispositions, or unverified endpoint sources.
3. Implement only independently scoped, in-scope, source-confirmed deltas. A finding is not “already covered” based on the superseded campaign.
4. Build with all Gradle `Test` tasks disabled and `-x test`. Do not run tests, gameplay/TAS, or Docker without a separate explicit request.
5. Commit the work on the worker branch and report its branch, commit, finding dispositions, evidence markers/hashes, build result, and unresolved questions to the coordinator. The coordinator updates that worker's status in `major-campaign.json` after the handoff is complete.

## Integration gate and checklist

Do not merge or integrate any worker branches until all fifteen fresh workers have finished. Then start integration in a **new GPT-6.1 Sol chat with Low reasoning**. Keep the implementation baseline and worker histories intact until that gate is met.

After the gate, integration should review every finding's source provenance, numeric semantics, player path, and boundary; reconcile overlapping mixin injection points, mechanic keys, variants, and registrations; inspect native fallback and historical content limits; merge the default branch and review newly landed changes for semantic conflicts; then build with all tests excluded and record remaining runtime questions. Compilation alone does not establish tick-level parity.

## Superseded campaign record

The previous coordinator was `feat/major-movement-integration`; its old roster is preserved under `supersededCampaign.chatRosterForRecoveryOnly` in [major-campaign.json](major-campaign.json). Those chats and branches are recovery references only and must not be used as implementation or coverage inputs. The current fresh roster replaces them.
