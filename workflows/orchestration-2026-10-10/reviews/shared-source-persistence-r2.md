# Shared Minecraft source persistence R2 review

Review target: `0f6a39288e1c07267b78680ce2f028d4c8d5b0b4` (`fix: guard resolved decompile destinations`), compared with default base `b3850a69266402b4e7abbd70cf239c9e401077d3` and revision-required review `425101a4c1e21a5aaca95756e35ff4974b87d30e`.

## Verdict

**Accept.** The revised patch closes the prior destructive-path and cache-retention findings. No new source-persistence issue was found in the reviewed task wiring, cleanup guards, retained artifacts, or documentation.

## Review

- **Publication alias and overlap protection:** `DecompileMinecraftTask.validateWritableRoots` canonicalizes the selected output and cache roots and the worktree/repository publication roots. It compares both ancestor directions, so roots that contain or are contained by the publication are refused. Existing prefixes are resolved with `toRealPath()` before missing suffix components are reattached; this handles junction aliases and non-existing descendants. Dangling links fail closed. `MinecraftDecompileEngine` repeats the check against each concrete version/mapping output immediately before its initial recursive deletion and immediately before failure cleanup. Thus a descendant path reached through the documented `decompiled_minecraft` junction or another alias to the publication is rejected before either cleanup path.
- **Shared Git worktrees:** for a linked worktree, `findRepositoryRoot` follows the worktree `.git` file and `commondir` to the shared Git directory, then derives the repository root. It adds the canonical repository publication roots alongside the task-worktree roots. The checked-in worktree metadata shape is compatible with that resolution; source outputs are not scoped only to the task worktree.
- **`clean` retention:** the `clean` task excludes `movement-campaign-2026-10-07/**`, retaining the complete publication subtree, including `ready/` and `artifacts/`. The 1.11 readiness manifest points to source and cache artifacts under those retained roots. Its four referenced metadata hashes matched; all 1,918 source entries matched `ornithe-feather.sources.sha256`, and all 37 artifact entries matched `artifacts.sha256`.
- **Task wiring and docs:** the task no longer configures fallback output/cache conventions. Its `@Option` setters populate the properties, and the task action rejects missing explicit values before launching the fork. The fork receives the resolved protected roots; `MinecraftDecompileMain` requires protected roots, mappings and versions. The buildSrc and source-preparation examples pass both required path options, and the guide now names the selected `--cache-directory` rather than the removed default. The guard behavior and read-only consumer contract are described consistently.

## Checks and limits

- Read `README.md`, `workflows/orchestration/README.md`, the current orchestration record, `buildSrc/README.md`, and `workflows/movement-discovery/source-preparation.md`.
- Compared the target patch with `b3850a69266402b4e7abbd70cf239c9e401077d3` and reviewed the findings from `425101a4c1e21a5aaca95756e35ff4974b87d30e`.
- Read-only verified the `decompiled_minecraft` junction target and the cited 1.11 readiness, provenance, source and artifact manifests and their listed hashes.
- Inspected current task properties/options, Gradle wiring, fork argument parsing, output deletion/failure-cleanup call order, canonicalization and clean exclusion. `git diff --check` passed.
- No `clean`, decompile, build, tests, runtime launch, or shared source/cache mutation was performed.

## Integration state

The assigned worktree is `D:/Javastuff/LegacyParkourCompat/.task-worktrees/review-source-cache-r2-2026-10-10` on `fix/review-source-cache-r2-2026-10-10`. The task branch started at `0f6a39288e1c07267b78680ce2f028d4c8d5b0b4`; local `main` is `b3850a69266402b4e7abbd70cf239c9e401077d3`, already an ancestor of the target. `git merge main` reported `Already up to date`; no incoming semantic changes remained to inspect. Only this review file is owned by this assignment.
