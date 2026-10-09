# Difference discovery from Minecraft sources

## Contract

Discover changes in **client player movement** between two exact Java Edition releases, A (older) and B (newer). Document evidence; do not implement fixes, launch clients, record TAS runs, or perform gameplay validation. Source-level verification of a claim belongs here; runtime verification belongs to workflow 3. Split work only across non-overlapping version intervals, with explicit source/decompile ownership and one named independent reviewer. The coordinator requeues partial work until the stated slices and blockers are resolved; branch ancestry, worker completion messages, catalogs, or a clean build do not close coverage.

Cover the complete reachable per-tick player movement call graph, not only ordinary travel: input; pre-travel decisions and state writes; travel branches; post-travel work; collision and collision-shape providers; pose, dimensions and eye height where they affect movement; fluids; block callbacks; registrations and neighboring-block dependencies; direct movement predicates; attributes, effects, enchantments and equipment; velocity cutoffs; and operation order. Enumerate influential state writers and consumers, then trace dependencies and callers. Include historical bugs.

The campaign excludes health, regeneration, hunger, food, saturation, exhaustion, and the damage/combat systems that produce those values or decide attacks/damage. Preserve those vanilla systems; a movement predicate may read their vanilla state without emulating its producer. This exclusion does not remove direct player-motion response: inspect player-side velocity/impulse/knockback application and resulting player state writes when they are reachable movement behavior, even if a combat event can trigger them. Do not derive or emulate damage/attack results, non-player motion, or vehicle physics. Preserve the one-way scope for modern-only blocks/states.

### Source findings versus emulation eligibility

Keep two decisions separate: whether exact-version vanilla source proves a behavioral difference, and whether that behavior is eligible for emulation under this project's scope. Preserve source evidence for a real difference even when its scope disposition is out of scope; do not turn every source change into a player-movement mechanic.

- A repeater/comparator being removed when a neighbor update makes it unsupported is a block-state lifecycle change, so it is not an emulatable player-collision finding. The historical collision shape of that same native block state remains eligible when it affects player movement; describe that shape difference separately and keep block states vanilla.
- Potion/effect production and projectile physics are excluded. A player-movement consumer of an already-present effect state, and direct player velocity/impulse application, remain in scope when reachable. Do not classify the excluded producer or projectile trajectory as an eligible movement change merely because it can eventually affect a player.

For each candidate, record the source-proven behavior and player path, then state the scope disposition and rationale. If the source change is real but excluded, retain it as an explicit out-of-scope disposition and continue checking any distinct in-scope consumer or collision-shape behavior.

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

### Blind discovery and accepted snapshots

Before full-pair freeze, source workers and blind reviewers may read vanilla sources and prior source reports as navigation, but not mod implementation, either wiki or wiki-audit output. Full-pair freeze requires terminal source slices, closed dependencies and an independent full-tick coverage audit. Implementation reconciliation follows that freeze.

A finding may enter separate implementation earlier through the [snapshot acceptance gate](../source-campaign-2026-10-07/README.md#incremental-finding-to-implementation-handoff). Verify its exact source evidence, reachable player path, historical behavior boundary and closed dependencies; a different blind reviewer must accept the immutable finding commit/hash. Source owners and reviewers establish vanilla behavior, not mod hook feasibility. Snapshot acceptance leaves the pair open and does not release source isolation. Keep implementation outcomes outside the source report before full-pair freeze.

Record cited source-file hashes and artifact publication identities. Reuse independently verified immutable publications without rehashing unrelated files. Reverify a publication when its identity changes or integrity is challenged. Changed evidence requires a new snapshot and independent review, preserving prior events and verdicts.

For this campaign, reports go under `workflows/source-campaign-2026-10-07/<A>--<B>/`. Keep wiki reports separate. One preparation owner writes shared artifacts; source workers read published roots and readiness markers. Tests and runtime launches require explicit user authorization; source workers perform source/static checks only.

## 1. Verify the published source pair

Check exact requested/resolved A and B, aligned naming namespace, source roots, artifact manifests and cited hashes before comparison or resume. The manifest must identify jars, mapping builds/files, tool versions/options and decompiler warnings. Reject substituted patch versions, mismatched namespaces and damaged method bodies. Missing artifacts block dependent slices; report the precise missing path/identity to the preparation owner.

Use the correct release-specific mappings for each side. Mojmap and a published unobfuscated release share Mojang's official-name namespace; different output-directory names do not prevent alignment. Verify class/member correspondence even in aligned namespaces. Names alone do not establish behavior or absence.

**Preparation owners:** read [source preparation](source-preparation.md) before decompilation or mapping probes. Only that owner may repair or regenerate shared sources/cache. **Consumers of revised evidence:** read its [artifact revision rules](source-preparation.md#revised-derived-artifacts-and-handoff-identity), verify the immutable bytes and lineage, and preserve original manifest records. Unavailable originals leave equivalence unverified.

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

## Assignment prompt

> Compare exact <A> → <B>, slices <IDs>, from <source/report base SHA>. Read this workflow and source-navigation.md. Use published sources <roots> and readiness manifests <paths>; own <report path> on <branch>. Follow the dependency queue and preserve source-only isolation. Record paired ranges/hashes, behavior, applicability and open dependencies. No implementation, wiki reads, shared-source writes, decompilation, tests or runtime launches. Commit a checkpoint with the next exact slice. Submit evidence-ready findings for blind snapshot review; claim pair completion only after the full inventory/dependency/audit gates pass.
