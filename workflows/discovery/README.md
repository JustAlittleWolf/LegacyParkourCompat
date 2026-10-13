# Discover exact player movement differences

Compare two explicitly named Java releases from exact decompiled sources. Cover fixed-profile player movement, including historical bugs, operation order, types, casts and rounding. Keep vanilla block states; historical collision shapes of blocks existing in the selected era are eligible. Rendering, non-player/vehicle physics, modern-only features, health/food production and attack/damage resolution are excluded. Direct player movement responses and predicates reading vanilla state remain eligible.

## Inputs and isolation

Receive the exact older/newer releases, source publication identity, mapping identity per side, a bounded assignment and report ownership. One preparation owner publishes canonical sources in the main repository's `decompiled_minecraft/`, using `decompileMinecraft` and [buildSrc](../../buildSrc/README.md). Consumers are read-only. Verify exact releases, mapping builds/files, relevant hashes and intact method bodies; directory presence or matching names are insufficient. Resolve unavailable or damaged evidence with the owner. Never use online mappings.

Source workers and independent source reviewers remain blind to implementation and both wiki lanes until full-pair freeze. Do not expose Git history, whole-product diffs, implementation indexes or wiki hints as source-navigation aids. Independently accepted immutable findings may be handed to separate implementers before freeze; this does not relax source isolation or close the pair.

Before many pair assignments, make one rough movement-area map to estimate size and dependencies. Then assign whole pairs or recurring areas according to their size, preserving exclusive ownership and full-pair closure. Check compatible naming families (Yarn/Legacy Yarn or Mojmap/published unobfuscated names), especially the transition around 1.14; verify correspondence rather than assuming equal symbols establish equal behavior.

Minecraft Wiki and parkour-wiki lanes independently gather hints and locate exact source evidence. Keep their reports separate. Their hints are compared with the frozen source inventory afterward and may open further source verification; a wiki claim alone is not accepted movement evidence.

Collect MCPK's individual change hints and Minecraft Wiki version-page changes including bugfix lists first, then group related hints into bounded source-verification assignments. Several related points may share a worker; there is no mandatory one-agent-per-point split.

## Work and closure

1. Map the complete reachable player tick: input, pre-travel state/decisions, travel branches, post-travel, position/velocity/box writers and consumers, collision/shape providers and registrations, callbacks, fluids, pose/dimensions, modifiers/equipment/effects and externally triggered direct player responses. Follow callers, inherited methods, resources/tags and neighboring-block dependencies. A method-name match or unchanged caller does not close its dependencies.
2. Compare bounded paired slices with enclosing gates and call order. Preserve historical numeric procedures exactly. Explain a concrete reachable precondition, the changed behavior and its player movement meaning; distinguish source facts from predicted trajectory effects. Addition/removal needs evidence of the replacement or checked absence on the other side.
3. Save one small finding per independent change under `research/findings/<older>--<newer>/`. Name each concrete version, mapping family and relative source file/member/range, followed by the difference and player movement meaning. Keep hashes, provenance, review/status fields, patch boundaries and unresolved dependencies in the central research records, with a short link to the original evidence when useful. No separate provenance document per pair is required. Keep excluded candidates as central dispositions; do not guess missing evidence.
4. Maintain one concise pair record under `research/pairs/`: exact inputs, ordered tick/inventory coverage, compared slices, open dependencies, findings, next action and independent audit. Full-pair freeze requires complete inventories, paired evidence for every terminal slice, dependency closure and a different blind reviewer retracing the complete inventory and tick graph. Self-review or a worker's completion message is insufficient.
5. Bind source acceptance to an immutable finding revision, evidence identity and independent reviewer verdict. A changed claim/evidence needs renewed review. Record patch introduction as unknown within `(A, B]` unless separately inspected exact intermediate releases prove the boundary. Broad endpoint comparisons do not justify exact implementation cutovers.

## Small finding template

```markdown
# <Independent change>
<Exact older version>, <mapping family>: <version/mapping/file, member, lines>.
<Exact newer version>, <mapping family>: <version/mapping/file, member, lines>.
Difference: <gates, order, types/math/state writes>.
Movement meaning: <reachable player path and concrete consequence>.
Original evidence: <relative report link>.
```

Use concrete version names in prose. Pair coverage, evidence identities, open questions, source acceptance, implementation and runtime evidence stay central and separate from the small finding. Discovery performs source/static checks; no implementation, tests, recording, gameplay launches or competing source generation.
