# 1.8.1 Feather / Vineflower failure diagnosis

## Finding

The first reported failure is a Vineflower enum-switch reconstruction failure in `net/minecraft/client/resource/model/BlockElement#getTextureCoords`: `const(1) not found, available: {}` (retained daemon log, line 30514). The same diagnostic occurs 59 times in this invocation, followed by `Vineflower reported 59 errors; incomplete source output will not be published` (line 30573). The engine counts Vineflower `ERROR` messages and aborts before provenance writing/publication (`buildSrc/src/main/java/legacyparkourcompat/minecraft/MinecraftDecompileEngine.java`, `decompileJar` and subsequent staging pipeline).

## Concrete input discrepancy

The pinned remapped client jars contain the same enum switch-table pattern for `BlockElement` in 1.8.1 and 1.8.2:

- Both `getTextureCoords` methods load a generated `int[]` switch map, index it with `Direction.ordinal()`, and use a `tableswitch` over 1..6.
- Both generated initializers allocate `Direction.values().length`, assign `DOWN` through `EAST` to 1..6, and catch `NoSuchFieldError` around each assignment.
- The 1.8.1 Feather Tiny mapping names the helper class `net/minecraft/client/resource/model/BlockElement__85922567` (`feather-gen2-1.8.1+build.2.tiny`, class mapping around line 34231). In the mapped jar it is a standalone synthetic class with only `SourceFile` metadata; `InnerClasses` and `EnclosingMethod` are absent.
- The 1.8.2 Feather Tiny mapping instead names it `net/minecraft/client/resource/model/BlockElement$85922567` (`feather-gen2-1.8.2+build.2.tiny`, around line 34328). Its mapped class retains both `InnerClasses` and `EnclosingMethod`, and the already-published 1.8.2 `BlockElement.java` reconstructs the enum switch successfully.

This is the strongest concrete explanation for the empty enum-constant mapping: the 1.8.1 mapping artifact flattens this compiler-generated nested helper, while 1.8.2 preserves its nesting metadata. The same kind of error across 59 methods is consistent with a mapping-wide synthetic switch-helper association problem. The bytecode inspected does not show missing constants or malformed switch assignments. This identifies the input metadata discrepancy and likely failure mechanism; it does not prove which internal Vineflower class-processing branch drops the helper.

The mapping pins identify `1.8.1+build.2` and `1.8.2+build.2` respectively. The mapped jars are cached at `D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache\1.8.1\client-ornithe-feather.jar` and `...\1.8.2\client-ornithe-feather.jar`; sources for the completed comparison are at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8.2\ornithe-feather\net\minecraft\client\resource\model\BlockElement.java`.

## Heap and options

No `OutOfMemoryError`, heap-space, GC-overhead, or native-thread exhaustion diagnostic appears in the retained task block. The failure is a deterministic named enum-switch reconstruction error, not a memory failure. The block does not log the child JVM's `-Xmx`, so its exact heap cannot be established from this log. The task class defaults the decompiler heap to `4G` and forwards an explicitly selected value as child `-Xmx`; the Gradle daemon's separate `-Xmx4G` line does not establish the child setting.

There is no evidenced safe existing task option that resolves this while retaining the strict validity gate:

- `--decompiler-heap` changes memory only.
- `--force` repeats generation using the same pinned inputs.
- `--mappings=auto` / `--mappings=feather` do not choose a different Feather build; the resolver selects the highest stable build from cached metadata (`1.8.1+build.2`). There is no task option for selecting build.1.
- `unobfuscated` would not provide the named `net/minecraft` inputs required by the output filter and movement-source checks.
- The engine fixes its Vineflower options in code. Vineflower 1.12.0's source shows enum-switch simplification is attempted by `SwitchHelper.simplify` directly; the `DECOMPILE_ENUM` preference is not a gate for this pass. No run with another preference was performed, so a code-level option change is not evidenced as safe.

Do not accept the failed-method marker or weaken the `ERROR` gate: that would publish source whose methods failed decompilation. Do not retry with 2G (or more heap) as a remedy; no memory symptom supports it, and it would preserve the same mapping input.

## Recommendation

Do not retry the same pinned 1.8.1 Feather input or change heap as a fix. Investigate a mapping-input correction that preserves the generated helper's nesting relationship, or a separately pinned mapping/tool combination, and only run generation after that candidate is concrete. Any eventual generation must still pass the existing zero-`ERROR` gate, failed-method-marker scan, required-source checks, and provenance/publication checks. No final or staging source tree was present under `decompiled_minecraft/1.8.1` during this inspection; the completed 1.8.2 tree remains available.

## Inspected task evidence

- Project decompile pipeline: `buildSrc/README.md`; `buildSrc/src/main/java/legacyparkourcompat/minecraft/DecompileMinecraftTask.java`; `buildSrc/src/main/java/legacyparkourcompat/minecraft/MinecraftDecompileEngine.java`.
- Pinned decompiler: `org.vineflower:vineflower:1.12.0` (`buildSrc/build.gradle`, `gradle/libs.versions.toml`; cached jar SHA-256 `1DFCFE974395734FA467CE620661C7623D05BA83670DE0529B1FBD63FF548B9D`). The cached source shows the logged message is emitted by `SwitchHelper` when a case constant is absent from its recovered enum mapping; it logs severity `ERROR`.
- Failure log: `C:\Users\Wolfi\.gradle\daemon\9.7.1\daemon-5508.out.log`, relevant task block lines 30299–30573.
- Read-only class inspection used `javap` against the cached remapped client jars; no Gradle/decompiler command was run and no project or cache source was modified.
