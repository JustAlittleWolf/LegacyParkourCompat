# Shared Minecraft source persistence review

Review target: `91b7f0a6cf5f29580772a507797a15244212cfbb` (`fix/shared-source-persistence-2026-10-10`), reviewed against merged default base `73a239b2dcbfaa15d3d3efd6b7cedb1dd08e7794`.

## Verdict

**Revision required.** The task argument guard and the `clean` exclusion preserve the published Java source files during the reviewed paths, but the publication is not fully reusable after `clean`, and a decompile invocation can still delete a published version through the documented junction.

## Findings

### [P1] Reject decompile output paths that resolve into the published source tree

`buildSrc/src/main/java/legacyparkourcompat/minecraft/DecompileMinecraftTask.java:88-95` accepts any `--output-root`, including the stable `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft` path documented in `buildSrc/README.md:16,28`. That path is a junction to `build/movement-campaign-2026-10-07/ready`. The engine then resolves `<output-root>/<version>/<mapping>` and calls `deleteRecursively` before decompiling, and again on failure (`MinecraftDecompileEngine.java:226-239,656-665`). Thus an otherwise valid explicit-argument invocation through the junction deletes the published source tree for that version/mapping; its sibling readiness JSON remains and can still claim `ready`.

The docs tell source consumers not to run the task and preparation owners to use staging, but the task does not enforce that boundary. Canonicalize paths and reject output roots that resolve to, contain, or are contained by the published ready tree (including junction aliases), or otherwise make the destructive output operation alias-safe.

### [P1] Preserve cache artifacts referenced by retained readiness manifests

`build.gradle:661-664` excludes only `movement-campaign-2026-10-07/ready/**` from `clean`. The readiness records and their `artifacts.sha256` files are retained, but the cache root at `build/movement-campaign-2026-10-07/artifacts` is still deleted. For example, the retained 1.11 artifact manifest names `artifacts/prep-1.11-ornithe-feather-2026-10-08-r3/1.11/client-ornithe-feather.jar` and the provenance record points to the corresponding original client jar and mapping artifacts under that cache root. `workflows/movement-discovery/source-preparation.md:9` requires the cited cache artifacts to exist and match their manifest hashes before reuse. After `clean`, the ready marker/source hashes may remain valid, but the published preparation cannot pass its reuse/provenance check.

Preserve the artifact roots referenced by the published manifests along with `ready/`, or publish the immutable reuse artifacts outside the disposable Gradle build tree and point manifests there. The exclusion should follow the manifest contract rather than only preserving source and marker files.

### [P2] Update the source-preparation guide's cache-location statement

`workflows/movement-discovery/source-preparation.md:44` still says decompiler caches live under `build/minecraft-decompile-cache/`. The patch removes the task's cache-directory convention in `build.gradle` and now requires an explicit `--cache-directory` (`DecompileMinecraftTask.java:98-103`); the documented commands use a selected staging cache. This sentence can direct a preparation owner to inspect or preserve a path the task no longer uses. Describe the selected cache path instead of the removed default.

## Checks performed

- Compared the target merge against `main`; the resulting patch is limited to `build.gradle`, the decompiler task, and the source-preparation/buildSrc/orchestration guidance.
- Verified the requested review worktree is on `fix/review-source-cache-persistence-2026-10-10` at the target patch HEAD before inspection.
- Read the current Gradle task wiring, `DecompileMinecraftTask`, the engine output cleanup, `buildSrc/README.md`, `workflows/movement-discovery/source-preparation.md`, and the relevant orchestration guidance.
- Read-only inspected the live `decompiled_minecraft` junction target and a retained 1.11 ready/provenance/artifact manifest. The junction targets `build/movement-campaign-2026-10-07/ready`; artifact entries point outside the excluded `ready/` subtree.
- Confirmed the source-preparation guide's example commands pass both required options. The task's missing-path error is explicit, and `--output-root`/`--cache-directory` are wired through Gradle `@Option` setters.
- No clean, decompile, build, test, runtime command, source/cache/junction mutation, or owned process was run.

## Integration state

The target commit contains `main` through `73a239b2dcbfaa15d3d3efd6b7cedb1dd08e7794`. Before handoff, I merged the then-current `main` at `58df025fc4f9e8fd51e2b4bf652a33757dffa732`, producing review-branch merge `ea84a40483706ceaccf1d6e40ecd628f7d5bf04f`. The incoming commit changes only the campaign coordination README and live queue; I inspected those semantics and found no overlap with the source-persistence patch or this review. Source bytes, the primary checkout and its index were not changed.
