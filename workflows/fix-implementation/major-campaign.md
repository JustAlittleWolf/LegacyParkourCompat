# Major-version delta implementation campaign

Status: archived and invalidated by the clean baseline reset. This document records the prior campaign only; it is not a plan or evidence of current implementation coverage. All historical implementation classes and movement mixins have been removed. Every in-scope, source-confirmed discovery delta requires fresh implementation, even if a prior campaign marked it implemented or already covered. The finding/source catalogs and shared source evidence remain available for independent review.

Fresh workers: start from `feat/clean-movement-baseline`, read the root and project `AGENTS.md` plus `README.md`, the matching discovery run and finding, and `workflows/fix-implementation/README.md`. Use the existing read-only shared decompilation tree at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/` directly, with cache `D:/Javastuff/LegacyParkourCompat/build/minecraft-decompile-cache/`. Do not copy or regenerate the published evidence. The preparation directory currently has only 1.16.1, 1.16.2, and 1.17 mapping manifests and 4G attempt logs; no successful campaign readiness marker is present. Treat unmarked outputs as unverified and coordinate source preparation before relying on them. Build only with every Gradle `Test` task disabled; do not run tests, gameplay/TAS, or Docker without a separate explicit request.

The coordinator branch was `feat/major-movement-integration`, starting at `223bf0c`. Worker chats started at `38c4330`, which adds explicit shared source/cache options to the decompiler. The chat IDs, exact comparison endpoints and assigned worktrees are in [major-campaign.json](major-campaign.json). The prior fifteen chats used GPT-6 Luna with High reasoning: fourteen implementation owners and one source-preparation owner. Supported comparisons started at 1.8.9 and ended at 1.21.11 versus 26.1.2. The native injection reference was the build target, 26.2.

## Shared source publication

Only the preparation owner writes the primary checkout's ignored `decompiled_minecraft` and `build/minecraft-decompile-cache` directories. The decompiler accepts `--output-root` and `--cache-directory`, so no copied source trees or worktree junctions are required. Source preparation runs serially. Researchers verify the successful provenance marker and artifact hashes before using a published tree; directory existence alone is insufficient. Failed attempts remain documented, and published evidence is not overwritten during a comparison.

The initial 26.2 attempt exhausted a 2G heap. Its partial output must not be used. Preparation was instructed to retry with at least 4G and publish readiness only after successful completion and relevant-body checks.

## Worker handoff

Each owner audits existing implementations and handles independently scoped catalog findings through the implementation workflow. Each finding receives an implemented, already-covered, out-of-scope or blocked disposition, with evidence and the exact version boundary. Additional exact-version source checks are limited to establishing necessary boundaries; endpoints do not prove the first changed patch. A run's broad partial discovery status does not block an independently verified finding.

Workers commit on their own branches, merge local `main` and report their handoff in `runs/<A>--<B>.md`. They do not merge into the integration branch or default branch. Builds are permitted with every Gradle `Test` task disabled; tests, gameplay/TAS runs and Docker launches are not authorized.

## Integration acceptance

1. Review each incoming finding and its source provenance, exact numeric semantics, supported player path and boundary before merging its commits.
2. Compare overlapping injections by target class, method descriptor, injection location and execution order. Consolidate bridges for the same operation in the existing general mixin, preserving independent historical implementations.
3. Reconcile mechanic keys and variants: successive versions of one mechanic use the same hook/key so the closest applicable historical change wins; unrelated deltas keep independent hooks. Inspect registrations for duplicate same-version keys and missing dispatch.
4. Confirm native/disabled fallback, non-player exclusion, historically available content and interaction order from source and static inspection. Compilation does not verify runtime parity.
5. Merge the default branch into integration and review newly landed commits for semantic conflicts. Compile/package with all tests excluded and commit the integrated result, recording remaining evidence and runtime limitations.

## Current verification

Shared-source CLI options compiled and were listed by `help --task decompileMinecraft`. The coordinator's baseline `build` completed successfully with an ignored init script disabling all `Test` tasks and `-x test`. No gameplay validation or tests were performed. Worker integration is still pending; no campaign completion or runtime parity is claimed.
