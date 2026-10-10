# Historical Minecraft source decompilation

`buildSrc` implements the root `decompileMinecraft` Gradle task. It downloads the requested Minecraft client, applies an available mapping set, and runs Vineflower in a forked Java 25 process. Its output is reference source for matching historical movement math; it is not part of the mod at runtime.

Run the task from the repository root:

```powershell
$stagingRoot = 'D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\staging\source-run'
$cacheRoot = 'D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\staging\source-run-cache'
.\gradlew.bat decompileMinecraft --output-root=$stagingRoot --cache-directory=$cacheRoot
.\gradlew.bat decompileMinecraft --versions=1.16.5 --output-root=$stagingRoot --cache-directory=$cacheRoot
.\gradlew.bat decompileMinecraft --versions=1.13.2 --mappings=feather,legacy-yarn --output-root=$stagingRoot --cache-directory=$cacheRoot
.\gradlew.bat decompileMinecraft --versions=1.16.5 --decompiler-heap=2G --output-root=$stagingRoot --cache-directory=$cacheRoot
```

The default set is `latest,1.8.9,1.12.2,1.13.2,1.14.4`. `--versions` takes comma-separated exact release IDs or `latest`; it does not remap a requested patch version. `-Pversions=` and `-PminecraftVersions=` are alternatives. Every invocation must pass explicit `--output-root` and `--cache-directory` paths so disposable worktrees cannot silently create duplicate source trees and caches. Use an isolated staging pair for preparation; source consumers use the persistent publication at the stable absolute root `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft` and do not run this task. The root path is a compatibility junction to the readiness publication; Gradle `clean` preserves that publication. Downloads and decompilation intermediates live under the selected cache directory.

`--mappings=auto` is the default. It uses official Mojang mappings when published, prefers Ornithe Feather through 1.12, and falls back to Yarn for later releases without official mappings. The 1.13.2 auto profile produces both `ornithe-feather` and `legacy-yarn`; 1.14.4 produces both `mojmap` and `yarn`. Unobfuscated releases are decompiled as published. For a specific comparison, pass a comma-separated set from `mojmap`, `legacy-yarn`, `yarn`, `feather`, and `unobfuscated`. The default decompiler heap is `4G`; `--decompiler-heap` accepts values such as `2G` or `2048M`.

`DecompileMinecraftTask` owns the Gradle options and fork. `MinecraftDecompileMain`, `MinecraftDecompileEngine`, and `MojangMeta` resolve releases, mappings, and decompilation; the remaining classes handle output and logs. Keep version and mapping resolution here rather than in the movement runtime. Use the generated source as the primary reference for exact casts, operation order, and floating-point expressions. Do not use online mapping lookups.

## Sharing sources between worktrees

Git worktrees share Git history, but ignored generated sources and build caches are separate. Pass `--output-root=<absolute-directory>` and `--cache-directory=<absolute-directory>` explicitly when the single preparation owner runs a decompile. Relative paths resolve against the invoking project directory. Consumers use the global read-only source root named by the readiness records; they must not create worktree-local fallback sources.

Assign one preparation owner to a shared output/cache pair. Researchers read published sources and verify manifest hashes; they must not run the decompiler against shared directories concurrently. A rerun replaces the selected version/mapping tree and can invalidate another run's evidence. If a separate regeneration is required, only the preparation owner uses an isolated staging output/cache pair.

The task removes the selected `<output-root>/<version>/<mapping>` directory before decompiling and removes that same directory if decompilation fails. It does not write or refresh preparation markers. Only the serialized preparation owner runs it, against staging paths; source consumers read the published tree at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. Publish or refresh a ready marker only after the task succeeds and the source files and artifact hashes have been checked. A marker from before a rerun does not prove that the current tree is ready.
