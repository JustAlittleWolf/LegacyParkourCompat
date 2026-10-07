# Difference discovery from Minecraft sources

## Contract

Discover changes in **client player movement** between two exact Java Edition releases, A (older) and B (newer). Document evidence; do not implement fixes, launch clients, record TAS runs, or perform gameplay validation. Source-level verification of a claim belongs here; runtime verification belongs to workflow 3. Split work only across non-overlapping version intervals, with explicit source/decompile ownership and one named independent reviewer. The coordinator requeues partial work until the stated slices and blockers are resolved; branch ancestry, worker completion messages, catalogs, or a clean build do not close coverage.

Cover the complete reachable per-tick player movement call graph, not only ordinary travel: input; pre-travel decisions and state writes; travel branches; post-travel work; collision and collision-shape providers; pose, dimensions and eye height where they affect movement; fluids; block callbacks; registrations and neighboring-block dependencies; direct movement predicates; attributes, effects, enchantments and equipment; velocity cutoffs; and operation order. Enumerate influential state writers and consumers, then trace dependencies and callers. Include historical bugs.

The campaign excludes health, regeneration, hunger, food, saturation, exhaustion, and the damage/combat systems that produce those values or decide attacks/damage. Preserve those vanilla systems; a movement predicate may read their vanilla state without emulating its producer. This exclusion does not remove direct player-motion response: inspect player-side velocity/impulse/knockback application and resulting player state writes when they are reachable movement behavior, even if a combat event can trigger them. Do not derive or emulate damage/attack results, non-player motion, or vehicle physics. Preserve the one-way scope for modern-only blocks/states.

Use release history or wiki material only to choose major/content-release boundary endpoints, never as movement evidence. Do not assume obsolete release-family groupings (including one interval for all of 1.21). Record exact coordinator-selected endpoints and inspect intermediate exact releases when establishing a first changed version. An endpoint comparison is not a whole release-line claim.

Required inputs: exact A and B; repository revision; available source/cache locations. If versions are missing, ask for them before a comparison. Do not substitute `latest`, nearby patches, release notes, mod implementations or remembered vanilla behavior for source evidence.

Create a tracked run at `workflows/movement-discovery/runs/<A>--<B>/`, copying [templates/run.md](templates/run.md). Store one finding per file under `findings/` using [templates/finding.md](templates/finding.md). Generated sources, jars and raw diffs remain ignored/local; commit manifests, correspondence, coverage, findings and unresolved questions. Use relative evidence paths rooted in the manifest, not machine-specific drive paths. Do not commit the Minecraft source tree.

### Tracked output layout

Every run has the same deliverable layout:

```text
runs/<A>--<B>/
  run.md
  findings/                 # present only when at least one finding exists
    <finding-id>.md
```

Keep the artifact manifest, correspondence, required inventories, coverage ledger, dependency queue, finding index, resume checkpoint, blind-discovery freeze, reconciliation, independent audit and closure in `run.md`, in the template's order. Put one independently scoped movement difference in each `findings/*.md`. Use exactly one top-level status: `active` while an agent is working, then `complete`, `partial` or `blocked`. Pending, in-progress, or blocked slices prevent `complete`. Use `blocked` when a precise external dependency prevents meaningful comparison; otherwise hand off `partial` with exact next work. A missing finding folder means zero confirmed findings, not equivalence.

Working navigation indexes, inventories and short source-preparation excerpts may be drafted locally, but fold any evidence, hashes, coverage decisions and resume information needed by the next agent into `run.md` before handoff. Keep raw logs, transcripts, generated sources, jars, caches and diffs outside the tracked run folder. Do not commit separate navigation, coverage, resource-inventory or preparation-log files. Links from findings must resolve within the deliverable. Before handoff, check that the run folder contains only `run.md` and optional `findings/*.md`, and that the manifest's status matches its open coverage rows. Every coverage row must identify the exact A and B method/body range (or evidence-backed absence path), state writers/producers and consumers, parent/dependency IDs, and one bounded behavior disposition; headings such as “travel checked” or a method name alone are too broad.

### Blind discovery and freeze

Before opening this mod's implementation or old mod catalogs/code for the interval, build a fresh vanilla-source inventory and compare the exact pair. Prior discovery reports may be read as navigation aids, but do not read isolated wiki-audit output or incorporate wiki findings before **full-pair** source freeze. Normal source workers must not browse the MCPK or Minecraft wikis; only the independent wiki-audit workers may use their assigned wiki and exact vanilla source. Record repository revision and source/mapping hashes. Full-pair freeze occurs only after every source slice is terminal, dependencies are closed and the independent full-pair audit passes.

An individual source-confirmed finding may be snapshotted earlier for parallel implementation only after its own evidence and dependencies are complete and a separate blind source reviewer accepts that exact snapshot. The snapshot is an immutable Git commit plus the finding-file SHA-256 and exact source/artifact hashes; record it in the snapshot log in `run.md`. A separate implementation chat receives only that accepted snapshot. This does not freeze the pair, close any other coverage slice, or permit a pair-complete claim. The source-only owner must not inspect implementation work or receive implementation-derived feedback, and neither source workers nor their snapshots receive wiki-audit information, until full-pair source freeze. Full-pair implementation reconciliation remains after that freeze.

The incremental gate's historical behavior boundary is source-only: identify the affected player-movement phase/operation, producer-consumer path and version/applicability conditions from exact vanilla sources. Native target sources (including 26.2) may be inspected to establish that boundary. Blind source owners/reviewers do not inspect mod implementation, require an existing mod hook, or design a mixin; the separate implementation owner handles current hook feasibility, bindings and mixin design after handoff.

If source evidence or the finding changes, append an invalidation/supersession event with the old snapshot ID, reason, new commit/hash and reviewer decision. Keep the old record/history; do not overwrite or silently reuse an accepted snapshot. The new snapshot must be reviewed before additional implementation relies on it. Snapshot reviewers check only the finding's source evidence, reachability and dependency closure. The full-pair reviewer separately audits all inventories and the complete call graph; accepted snapshots do not count as that audit or alter the strict completion gate.

For the 2026 campaign, create new source-only reports under `workflows/source-campaign-2026-10-07/<A>--<B>/run.md` plus `findings/`; leave prior reports in place. Wiki-audit reports live separately under `workflows/wiki-audit-2026-10-07/` and remain isolated from source workers and snapshot reviews until the full-pair source freeze.

At campaign start, assign one owner for shared decompilation/build preparation and exact-source readiness markers. That owner alone writes shared source/cache artifacts; version workers read published paths and markers and never prepare competing copies. The coordinator assigns disjoint version intervals and work areas and names a reviewer who did not author the discovery. This workflow owns source content and evidence requirements; the coordinator owns exact lock commands and shared build/decompile scheduling. Do not run tests or launch Minecraft/TAS/Gym/server/Docker until the coordinator authorizes it; builds and static checks only.

## 1. Establish a comparable source pair

Read [buildSrc/README.md](../../buildSrc/README.md) and the current decompiler implementation. Use `decompileMinecraft`; never look up mappings online. Existing outputs can be reused only when their provenance can be established. Unproven source trees are navigation aids, not final evidence.

At resume time, check that both recorded source roots and the cited cache artifacts still exist and match the manifest hashes. A tracked run records past evidence; it does not guarantee that ignored sources or jars remain on disk. If they are gone, regenerate the exact pair through this task and reverify the hashes before extending the comparison. If regeneration or namespace alignment fails, retain the specific blocker and do not promote pending slices to complete.

### Revised derived artifacts and handoff identity

Keep the original artifact manifest and readiness record unchanged when a derived artifact is regenerated or replaced. Add an `Evidence artifact <ID>` record under `Artifact evidence identities` for the revised publication, and cite that ID in each finding and incremental snapshot that uses it. Record its revision ID, immutable snapshot path, artifact SHA-256, evidence-manifest path/hash, original artifact-manifest path/hash, original derived-artifact availability and expected hash, source/raw-input hash relation, revised-to-original equivalence status, and provenance limitations. A revision record describes lineage; it does not prove byte identity, source equivalence, or derived-artifact equivalence.

Before consuming a revised artifact, independently hash the bytes at its immutable path and compare that value with the artifact manifest and revision metadata. Also verify the referenced source and raw-input records. If the prior derived artifact is unavailable, state that explicitly and leave equivalence `unverified`; do not claim the revised artifact reproduces it. The completion checker validates that required fields are declared and that SHA-256 fields have the expected shape. It does not read those paths, authenticate hashes, or prove provenance/equivalence. Active reports may leave unresolved fields `pending`; an explicitly declared `revised-derived` artifact may not omit its revision identity and provenance declarations.

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

If neither a common mapping family nor the Mojmap/native-unobfuscated official-name pairing is available, mark the run **blocked: mapping alignment**. Do not silently compare Mojmap with Feather, or bridge through a third release and claim a direct comparison. A future, separately scoped mapping-normalization task would need to produce both trees in a verified common namespace. This workflow does not implement that infrastructure.

Record in the run manifest:

- Exact requested and resolved releases, repository commit, command, date and successful decompiler log location.
- Per side: client jar identity/hash, naming namespace and CLI mode, resolved mapping coordinate/build, mapping file and SHA-256, intermediate mapping bridge if used, remapped jar hash, source root. Mark mapping and remapped-jar fields `not applicable: published unobfuscated` on that side of an official-name pair.
- JDK, Vineflower, remapper and mapping-io versions/options from this repository; record relevant dependency versions rather than assuming they remain fixed.
- Hashes of every source/resource cited in the completed run. A short local log/hash inventory may remain ignored, but the manifest must retain the actual identities/hashes needed to check evidence.

Current implementation caveats: caches live under `build/minecraft-decompile-cache/`; official mappings use `client_mappings.txt`; community mappings resolve a build from catalog metadata. The CLI has no mapping-build pin option. Preserve the resolved artifacts and record their coordinates/hashes; a fresh resolution is not guaranteed to reproduce that build. Reruns replace the selected version/family output directory. Do not regenerate evidence midway without invalidating affected findings. The task's required-class checks do not establish that every method decompiled correctly. Inspect decompiler errors and missing-library warnings; missing or damaged relevant method bodies block their slices even if the Gradle task succeeded.

## 2. Build a small navigation index once

Follow [source-navigation.md](source-navigation.md). Start with a filename inventory, not full-file reads. Resolve each logical role to the actual fully qualified class, inheritance chain and relevant member signature on both sides. Save that correspondence in `run.md`, including evidence for renames, split/merged methods and replacements. Absence requires checking inheritance, registrations, callers and resources; a failed name search alone proves nothing.

For every reachable tick entry point, record the ordered call graph including pre-travel, travel dispatch/branches, and post-travel. Record exact callers and state read/write edges for input; pose, dimensions and eye height; position, velocity, box, collision/ground/fluid flags and support; movement attributes; sprint/jump timers; and equipment/effect state. Maintain distinct inventories for collision-shape providers/registrations/neighbors, data and attributes/effects/equipment, and external influences. Split large methods into named behavior slices with exact body ranges. Mod hooks and deltas are search hints only after blind discovery is frozen; they do not define the vanilla inventory or prove a difference.

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

A complete run requires every required inventory to be populated and evidence-backed; every reachable source slice to have terminal status, both-side evidence, and closed producer/consumer dependencies; no pending, in-progress, or blocked rows or unresolved dependencies; explicit out-of-scope dispositions; and a different reviewer to re-walk the inventories and full tick call graph while blind to mod implementation and wiki-audit information. The reviewer records concrete missed-slice routes (or `none found`) and routes each miss to a coverage row/finding before passing. A worker self-check is not independent. Run `python workflows/movement-discovery/check_completion.py <run-folder>` as a mechanical gate; it checks structure/status only and cannot establish source truth. Otherwise hand off `partial` (or `blocked` when a precise external dependency prevents meaningful progress), enumerate gaps, and retain the resume checkpoint. Even a complete run is bounded by its recorded inventory; do not claim proof about unreachable or uninspected code.

Two endpoints show a difference between A and B, not the release where it first appeared. Record introduction as `unknown within (A, B]` unless additional exact-version sources were separately inspected with aligned mappings. Do not assign an implementation version boundary from endpoints alone.

Handoff the frozen blind catalog, coverage, provenance, reconciliation, independent audit and unresolved questions. Keep three statuses separate: source discovery coverage; implementation coverage per finding (implemented / intentionally excluded / open, with code/registration evidence); and runtime validation (not performed / planned / completed, with evidence). Before full-pair freeze, an accepted immutable finding snapshot may release only that finding to a separate implementation chat; it leaves pair discovery active/partial and does not expose implementation or wiki-derived information to the source owner. Cataloged, snapshot-accepted, or implemented does not mean pair-complete or runtime-validated. Leave implementation choices and test execution to workflows 2 and 3.

## Reusable operator prompt

> Freshly discover player movement from exact vanilla sources for <A> and <B>. Prior discovery reports may be used as navigation; do not inspect old mod implementation. Normal source-only workers do not browse either wiki or use isolated wiki-audit results before freeze. Follow this workflow and source-navigation.md. Record the full per-tick call graph (pre-travel, travel, post-travel), exact method-body ranges, state writers and producer-to-consumer dependencies; inventory pose/dimensions, collision-shape providers and registrations/neighbors, data/effects/enchantments/equipment, and external influences. Preserve operation order and source hashes. Exclude health/food state production and attack/damage resolution, while allowing movement predicates to read vanilla state; direct player velocity/impulse/knockback application remains in scope even if combat can trigger it. Exclude non-player movement and vehicle physics. Freeze reports under `workflows/source-campaign-2026-10-07/<A>--<B>/` before implementation reconciliation. Do not implement or execute tests/gameplay. Require an independent reviewer to audit all inventories and record concrete missed-slice routes. Keep discovery, implementation and runtime statuses separate. Complete only when evidence-backed slices are terminal, dependencies closed, and audit passed; otherwise continue or hand off precise partial/blocker state.
