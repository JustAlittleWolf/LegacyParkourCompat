# Source preparation and artifact revisions

Read this guide when preparing shared sources, resolving mapping alignment or verifying a revised artifact publication. Source comparison workers consume published artifacts read-only; they do not run preparation commands.

## Publish a comparable source pair

Read [buildSrc/README.md](../../buildSrc/README.md) and the current decompiler implementation. Use `decompileMinecraft`; never look up mappings online. Existing outputs can be reused only when their provenance can be established. Unproven source trees are navigation aids, not final evidence.

Before preparing or extending a pair, check that both recorded source roots and the cited cache artifacts still exist and match the manifest hashes. A tracked run records past evidence; it does not guarantee that ignored sources or jars remain on disk. If they are gone, the preparation owner regenerates the exact pair through this task and reverifies the hashes before extending the comparison. If regeneration or namespace alignment fails, retain the specific blocker and do not promote pending slices to complete.

### Revised derived artifacts and handoff identity

Keep the original artifact manifest and readiness record unchanged when a derived artifact is regenerated or replaced. Add an `Evidence artifact <ID>` record under `Artifact evidence identities` for the revised publication, and cite that ID in each finding and incremental snapshot that uses it. Record its revision ID, immutable snapshot path, artifact SHA-256, evidence-manifest path/hash, original artifact-manifest path/hash, original derived-artifact availability and expected hash, source/raw-input hash relation, revised-to-original equivalence status, and provenance limitations. A revision record describes lineage; it does not prove byte identity, source equivalence, or derived-artifact equivalence.

Before first accepting a revised artifact publication, independently hash the bytes at its immutable path and compare that value with the artifact manifest and revision metadata. Also verify the referenced source and raw-input records. Later findings may cite that committed, verified publication by its exact revision and manifest identity as described above. If the prior derived artifact is unavailable, state that explicitly and leave equivalence `unverified`; do not claim the revised artifact reproduces it. The completion checker validates that required fields are declared and that SHA-256 fields have the expected shape. It does not read those paths, authenticate hashes, or prove provenance/equivalence. Active reports may leave unresolved fields `pending`; an explicitly declared `revised-derived` artifact may not omit its revision identity and provenance declarations.

Choose **one aligned naming namespace for both exact versions**. The CLI family identifiers are `mojmap`, `feather`, `legacy-yarn`, `yarn`, `unobfuscated`. `feather` writes the directory `ornithe-feather`; the others use their identifier. Mojmap remaps an obfuscated release to Mojang's official names. A release published unobfuscated already uses those official names, so `mojmap` on the older side and `unobfuscated` on the newer side form a valid official-name pair. Run the task separately with the explicit mode appropriate to each release; `unobfuscated` still rejects an obfuscated jar. Example candidate commands, conditional on availability:

```powershell
.\gradlew.bat decompileMinecraft --versions=1.12.2,1.13.2 --mappings=feather
.\gradlew.bat decompileMinecraft --versions=1.13.2,1.14.4 --mappings=feather
.\gradlew.bat decompileMinecraft --versions=1.14.4,1.16.5 --mappings=mojmap
.\gradlew.bat decompileMinecraft --versions=1.21.11 --mappings=mojmap
.\gradlew.bat decompileMinecraft --versions=26.1.2 --mappings=unobfuscated
```

Do not use `auto` or a comma-separated family list for the comparison. Auto chooses families per release and has dual-output special cases. Explicit unavailable families fail; do not interpret partial output from a failed command as a valid pair. Check both successful completion messages and both output directories. Verify requested and resolved release IDs match exactly: the current resolver can treat an unknown ID as a prefix and choose a matching release. Reject that substitution. `unobfuscated` is not a way to bypass mappings on obfuscated releases. For an official-name pair, verify the newer jar is actually unobfuscated and record that it has no mapping file or remapped jar; its original client jar is the decompiler input.

Do not infer mapping unavailability from the `auto` profile or from folders already on disk. For example, `auto` emits Feather plus Legacy Yarn for 1.13.2 and Mojmap plus Yarn for 1.14.4, yet explicit `--mappings=feather` succeeds for **both** exact releases (`feather-gen2` build 2 on each). Probe the candidate family through this task before recording a mapping-alignment blocker.

“Aligned namespace” means the same naming convention, with the correct release-specific mapping artifact where remapping is needed. The official-name pair above qualifies even though its output directories are named `mojmap` and `unobfuscated`. Applying one release's mapping file to the other release is invalid. Yarn and Legacy Yarn are separate families. Even an aligned namespace can rename classes/members, change descriptors or move logic; names alone never prove correspondence. Verify class and member correspondence through inheritance, callers, behavior and data on both sides. Intermediary or obfuscated names are not assumed stable across releases either.

If neither a common mapping family nor the Mojmap/native-unobfuscated official-name pairing is available, mark the run **blocked: mapping alignment**. Do not silently compare Mojmap with Feather, or bridge through a third release and claim a direct comparison. Mapping normalization requires a separately scoped task producing both exact trees in a verified common namespace.

Record in the run manifest:

- Exact requested and resolved releases, repository commit, command, date and successful decompiler log location.
- Per side: client jar identity/hash, naming namespace and CLI mode, resolved mapping coordinate/build, mapping file and SHA-256, intermediate mapping bridge if used, remapped jar hash, source root. Mark mapping and remapped-jar fields `not applicable: published unobfuscated` on that side of an official-name pair.
- JDK, Vineflower, remapper and mapping-io versions/options from this repository; record relevant dependency versions rather than assuming they remain fixed.
- Hashes of every source/resource cited in the completed run. A short local log/hash inventory may remain ignored, but the manifest must retain the actual identities/hashes needed to check evidence.

Decompiler constraints: caches live under `build/minecraft-decompile-cache/`; official mappings use `client_mappings.txt`; community mappings resolve a build from catalog metadata. The CLI has no mapping-build pin option. Preserve the resolved artifacts and record their coordinates/hashes; a fresh resolution is not guaranteed to reproduce that build. Reruns replace the selected version/family output directory. Do not regenerate evidence midway without invalidating affected findings. The task's required-class checks do not establish that every method decompiled correctly. Inspect decompiler errors and missing-library warnings; missing or damaged relevant method bodies block their slices even if the Gradle task succeeded.

## Shared publication ownership

One preparation owner serializes shared source/cache writes. Preserve existing publications while readers use them; generate replacements separately. After successful decompilation and artifact verification, atomically publish a readiness marker by renaming a temporary record. Include exact requested/resolved releases, command/result, namespace and mapping build, source root, original/mapped jar and mapping hashes, tool versions/options, warnings and the source file inventory/hash manifest. Consumers use the root named by that record; do not assume a marker naming convention or a worktree-local fallback. Record changed publication identities and invalidate affected snapshots before reuse.
