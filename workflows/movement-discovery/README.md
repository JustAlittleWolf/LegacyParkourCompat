# Difference discovery from Minecraft sources

## Contract

Use one agent, with GPT-6 Luna as the intended operator. No model-specific API or parallel agents are required. Discover changes in **client player movement** between two exact Java Edition releases, A (older) and B (newer). Document evidence; do not implement fixes, launch clients, record TAS runs, or perform gameplay validation. Source-level verification of a claim belongs here; runtime verification belongs to workflow 3.

Cover input, physics, collision, block properties, fluids, effects, enchantments, equipment and every reachable influence on the player's movement state. Include historical bugs. Preserve the repository's one-way scope: newly introduced mechanics may be documented, but do not imply that modern-only blocks/states or other entities should acquire historical behavior. Inspect another entity's code only when needed to explain an effect on the player; label player-only integration separately.

Required inputs: exact A and B; repository revision; available source/cache locations. If versions are missing, ask for them before a comparison. Do not substitute `latest`, nearby patches, release notes, mod implementations or remembered vanilla behavior for source evidence.

Create a tracked run at `workflows/movement-discovery/runs/<A>--<B>/`, copying [templates/run.md](templates/run.md). Store one finding per file under `findings/` using [templates/finding.md](templates/finding.md). Generated sources, jars and raw diffs remain ignored/local; commit manifests, correspondence, coverage, findings and unresolved questions. Use relative evidence paths rooted in the manifest, not machine-specific drive paths. Do not commit the Minecraft source tree.

## 1. Establish a comparable source pair

Read [buildSrc/README.md](../../buildSrc/README.md) and the current decompiler implementation. Use `decompileMinecraft`; never look up mappings online. Existing outputs can be reused only when their provenance can be established. Unproven source trees are navigation aids, not final evidence.

Choose **one explicit mapping family supported for both exact versions**. Family identifiers are `mojmap`, `feather`, `legacy-yarn`, `yarn`, `unobfuscated`. `feather` writes the directory `ornithe-feather`; the others use their identifier. Example candidate commands, conditional on availability for both versions:

```powershell
.\gradlew.bat decompileMinecraft --versions=1.12.2,1.13.2 --mappings=feather
.\gradlew.bat decompileMinecraft --versions=1.14.4,1.16.5 --mappings=mojmap
```

Do not use `auto` or a comma-separated family list for the comparison. Auto chooses families per release and has dual-output special cases. Explicit unavailable families fail; do not interpret partial output from a failed command as a valid pair. Check both successful completion messages and both output directories. Verify requested and resolved release IDs match exactly: the current resolver can treat an unknown ID as a prefix and choose a matching release. Reject that substitution. `unobfuscated` is not a way to bypass mappings on obfuscated releases.

“Same mappings” means the same mapping project and target naming convention, with the correct release-specific artifact for each game version. Applying one release's mapping file to the other release is invalid. Yarn and Legacy Yarn are separate families. Even a common family can rename classes/members, change descriptors or move logic; names alone never prove correspondence. Intermediary or obfuscated names are not assumed stable across releases either.

If no common family exists, mark the run **blocked: mapping alignment**. Do not silently compare Mojmap with Feather, or bridge through a third release and claim a direct comparison. A future, separately scoped mapping-normalization task would need to produce both trees in a verified common namespace. This workflow does not implement that infrastructure.

Record in the run manifest:

- Exact requested and resolved releases, repository commit, command, date and successful decompiler log location.
- Per side: client jar identity/hash, mapping family, resolved mapping coordinate/build, mapping file and SHA-256, intermediate mapping bridge if used, remapped jar hash, source root.
- JDK, Vineflower, remapper and mapping-io versions/options from this repository; record relevant dependency versions rather than assuming they remain fixed.
- Hashes of every source/resource cited in the completed run. A short local log/hash inventory may remain ignored, but the manifest must retain the actual identities/hashes needed to check evidence.

Current implementation caveats: caches live under `build/minecraft-decompile-cache/`; official mappings use `client_mappings.txt`; community mappings resolve a build from catalog metadata. The CLI has no mapping-build pin option. Preserve the resolved artifacts and record their coordinates/hashes; a fresh resolution is not guaranteed to reproduce that build. Reruns replace the selected version/family output directory. Do not regenerate evidence midway without invalidating affected findings. The task's required-class checks do not establish that every method decompiled correctly. Inspect decompiler errors and missing-library warnings; missing or damaged relevant method bodies block their slices even if the Gradle task succeeded.

## 2. Build a small navigation index once

Follow [source-navigation.md](source-navigation.md). Start with a filename inventory, not full-file reads. Resolve each logical role to the actual fully qualified class, inheritance chain and relevant member signature on both sides. Save that correspondence in `run.md`, including evidence for renames, split/merged methods and replacements. Absence requires checking inheritance, registrations, callers and resources; a failed name search alone proves nothing.

For each entry point, record a short ordered call chain and the state it reads/writes: input, position, velocity, bounding box, pose, on-ground/collision/fluid flags, supporting block, movement attributes, sprint/jump timers and equipment/effect state. This is the reusable index for later slices. The repository's hooks and deltas provide search hints, not a complete list of vanilla behavior and not evidence that a difference exists.

## 3. Compare bounded mechanic slices

Process the navigation stages in order. A slice is one behavior (for example crouch edge probing), typically one entry method and its direct helpers, on both sides. Start with at most three method pairs or roughly 300 source lines per side. These are soft context targets, never permission to truncate a method or omit a dependency. Split large methods by named behavior and retain their surrounding order/guards in the index.

For each slice:

1. Open the paired methods with enclosing guards, signatures and original line numbers. Diff only the selected files/members. A textual no-change result is a useful filter, not a behavioral verdict.
2. Compare constants and types, float/double conversions, literal suffixes, arithmetic grouping, intermediate rounding, trigonometry, floors/clamps, comparison boundaries, axis order, iteration order, collision candidate selection and tie-breaking, callback/tick order, predicates, state initialization and persistence. Never algebraically simplify movement math or normalize away casts, parentheses or branch order.
3. Follow changed or influential callees, overridden methods, field writers, constructor/default values, attributes, tags and registry/resource entries. Queue each new dependency with a reason and parent slice. A caller can be unchanged while its callee, data or execution timing changes. Revisit callers after dependent slices change their conclusions.
4. Trace each candidate to a reachable client player path and state a concrete precondition under which the code paths differ. Separate what the source proves from a predicted movement consequence. Discard symbol-only/decompiler-only differences with a written reason; keep uncertain decompilation as unresolved. If needed, inspect only the relevant member in the mapped jar with `javap -c -p` (plus descriptors); do not substitute guessed Java for missing code.
5. Write one finding per independently describable behavioral delta using the finding template. Link related deltas instead of merging a whole travel loop into one finding. Record both versions' evidence even for an addition/removal, with the checked absence/replacement path on the other side.
6. Update coverage and the dependency queue immediately. Save a concise next-step checkpoint so another session can continue without rereading full classes.

Optional local commands (substitute paths resolved in the index):

```powershell
rg --files decompiled_minecraft/1.12.2/ornithe-feather -g '*.java'
rg -n 'travel|jump|move|sneak|sprint' <resolved-player-file>
git diff --no-index -- <A-file> <B-file>
Get-FileHash -Algorithm SHA256 <cited-source-or-mapping-file>
```

`git diff --no-index` exits 1 when differences exist. Keep raw diffs local and show the agent relevant hunks plus dependency context. Restrict searches to the indexed classes/packages first; expand to the full tree only to resolve a specific missing owner/caller. Never paste a whole-tree diff into model context.

## 4. Cover indirect and data-driven movement

Visit every navigation stage even if the main movement loops look identical. Review registrations and relevant subclasses as well as base methods: friction, speed/jump factors, collision shapes, callbacks, effect amplifiers/attribute operations, enchantment formulas/conditions, equipment slots and applicability may change without a changed travel method.

The current `JavaDirectorySaver` excludes jar resources. Inspect relevant original client-jar entries locally using `jar tf` and a ZIP reader/extraction into ignored scratch storage. In particular, resolve referenced block/fluid tags, enchantment definitions, effect/component data and registry defaults, including referenced values. Record resource entry names and hashes. If required data is server/datapack supplied or absent from available artifacts, identify the required version-matched artifact and mark that slice blocked until obtained. Never equate “no Java class” with “no enchantment.” Record whether a value is a vanilla default, synchronized server value or external input; do not infer server movement rules from client code.

## 5. Close the source research

Each coverage row must be one of: `pending`, `in-progress`, `compared-no-difference`, `findings`, `not-applicable`, `blocked`. Every terminal row needs both-side evidence and a rationale. A no-difference claim is scoped to the inspected slice and its dependency closure. Missing information is `blocked`, not `not-applicable`.

Before completing, resolve every newly discovered dependency or explicitly report the remaining gaps. Check each finding for accurate file/member/line references, exact versions, source hashes, reachable player path, independently scoped behavior, and separation of evidence from inference. Deduplicate findings about one root cause reached from multiple callers. Record cross-mechanic interactions such as fluids plus enchantments, sprint plus item use, or bounce plus sneaking. This is a source audit, not gameplay testing.

A complete run has no pending/in-progress/blocked coverage rows or unresolved dependencies. Otherwise deliver it as **partial** (or **blocked** if comparison could not begin), enumerate gaps, and retain the resume checkpoint. Even a complete run is bounded by its recorded inventory; do not claim an exhaustive proof of equivalence.

Two endpoints show a difference between A and B, not the release where it first appeared. Record introduction as `unknown within (A, B]` unless additional exact-version sources were separately inspected with aligned mappings. Do not assign an implementation version boundary from endpoints alone.

Handoff only the catalog, coverage, provenance and unresolved questions. Findings remain `runtime validation: not performed`. Leave implementation choices and test execution to workflows 2 and 3.

## Reusable operator prompt

> Run workflows/movement-discovery/README.md for older version <A> and newer version <B>. Use one agent and the same explicit mapping family for both. First establish provenance and member correspondence, then compare one bounded mechanic slice at a time in source-navigation order. Persist findings, coverage, dependencies and resume state under runs/<A>--<B>/. Trace indirect dependencies and data-driven effects/enchantments; preserve exact numeric semantics. Do not implement fixes or execute gameplay validation. Report mapping/decompilation/data gaps honestly. Finish only when all coverage rows are resolved, or deliver an explicitly partial/blocked catalog with precise next steps.
