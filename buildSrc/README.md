# Historical Minecraft source decompilation

`buildSrc` implements the root `decompileMinecraft` Gradle task. It downloads the requested Minecraft client, applies an available mapping set, and runs Vineflower in a forked Java 25 process. Its output is reference source for matching historical movement math; it is not part of the mod at runtime.

Run the task from the repository root:

```powershell
.\gradlew.bat decompileMinecraft
.\gradlew.bat decompileMinecraft --versions=1.16.5
.\gradlew.bat decompileMinecraft --versions=1.13.2 --mappings=feather,legacy-yarn
.\gradlew.bat decompileMinecraft --versions=1.16.5 --decompiler-heap=2G
```

The default set is `latest,1.8.9,1.12.2,1.13.2,1.14.4`. `--versions` takes comma-separated exact release IDs or `latest`; it does not remap a requested patch version. `-Pversions=` and `-PminecraftVersions=` are alternatives. Output goes to the ignored `decompiled_minecraft/<version>/<mapping>/` directory. Downloads and decompilation intermediates are cached under the root build directory.

`--mappings=auto` is the default. It uses official Mojang mappings when published, prefers Ornithe Feather through 1.12, and falls back to Yarn for later releases without official mappings. The 1.13.2 auto profile produces both `ornithe-feather` and `legacy-yarn`; 1.14.4 produces both `mojmap` and `yarn`. Unobfuscated releases are decompiled as published. For a specific comparison, pass a comma-separated set from `mojmap`, `legacy-yarn`, `yarn`, `feather`, and `unobfuscated`. The default decompiler heap is `4G`; `--decompiler-heap` accepts values such as `2G` or `2048M`.

`DecompileMinecraftTask` owns the Gradle options and fork. `MinecraftDecompileMain`, `MinecraftDecompileEngine`, and `MojangMeta` resolve releases, mappings, and decompilation; the remaining classes handle output and logs. Keep version and mapping resolution here rather than in the movement runtime. Use the generated source as the primary reference for exact casts, operation order, and floating-point expressions. Do not use online mapping lookups.
