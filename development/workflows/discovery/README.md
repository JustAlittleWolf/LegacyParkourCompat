# Discover exact player movement differences

Compare two explicitly named Java releases from exact decompiled sources. Cover fixed-profile player movement, including historical bugs, operation order, types, casts and rounding. Keep vanilla block states; historical collision shapes of blocks existing in the selected era are eligible. Rendering, non-player/vehicle physics, modern-only features, health/food production and attack/damage resolution are excluded. Direct player movement responses and predicates reading vanilla state remain eligible.

## Choose one lane

Read these shared rules and only the procedure for the assigned lane:

- [Source](source/README.md): systematic blind comparison and independent coverage audit.
- [Minecraft Wiki](minecraft-wiki/README.md): version-page changes and bugfix lists, followed by exact-source verification.
- [MCPK Wiki](mcpk-wiki/README.md): parkour mechanics and individual change hints, followed by exact-source verification.

The master assigns exclusive finding paths and sends each worker only permitted inputs. Source workers and source reviewers must not read either wiki inventory, product implementations, whole-product diffs, implementation indexes or product Git history before full-pair freeze. Wiki workers keep the two hint inventories independent and do not use the source lane's findings or the other wiki lane as their checklist. Reconciliation happens after source freeze; disagreements reopen bounded verification without rewriting the historical frozen inventory. Wiki hints alone never establish source acceptance or complete tick coverage.

## Exact evidence

Receive the exact older/newer releases, source publication identity, mapping identity per side, a bounded assignment and finding ownership. One preparation owner publishes canonical sources in the main repository's `decompiled_minecraft/`, using `decompileMinecraft` and [buildSrc](../../../buildSrc/README.md). Consumers are read-only. Verify exact releases, mapping builds/files, relevant hashes and intact method bodies; directory presence or matching names are insufficient. Resolve unavailable or damaged evidence with the owner. Never use online mappings.

## Permanent findings

Save one small Markdown per independent difference at `development/research/findings/<older>--<newer>/<topic>.md`. Use exact release strings (for example `1.16.5--1.17.1`) and a stable lowercase English topic slug separated by hyphens (for example `jump-boost-addition-precision.md`). No campaign prefixes, sequence IDs, revision suffixes or dates; distinguish different operations by their topic. Include each concrete version, mapping family and relative source file/member/range, followed by the difference and player movement meaning. No standard confidence, classification, runtime or implementation-status fields, hashes or handoff bureaucracy per finding. Put genuinely needed acceptance identities and unresolved gates in `development/research/backlog.json`; exclusions belong in `development/research/decisions.md`. Never guess missing evidence. Correct all active links and index paths when renaming a finding. Preserve confirmed findings when implemented.

Bind source acceptance to an immutable finding revision, evidence identity and independent reviewer verdict. A changed claim/evidence needs renewed review. Record patch introduction as unknown within the interval between the concrete older and newer versions unless separately inspected exact intermediate releases prove the boundary. Broad endpoint comparisons do not justify exact implementation cutovers.

### Small finding template

```markdown
# <Independent change>
<Exact older version>, <mapping family>: <version/mapping/file, member, lines>.
<Exact newer version>, <mapping family>: <version/mapping/file, member, lines>.
Difference: <gates, order, types/math/state writes>.
Movement meaning: <reachable player path and concrete consequence>.
```

## Progress and authorized checks

Workers send bounded coverage, evidence, unresolved questions and next actions to the master in chat. They do not edit the shared backlog or create parallel status reports. The master uses the [backlog tool](../../backlog/README.md) to keep pair coverage, lane-specific hint inventories, evidence identities, source acceptance, implementation and runtime parity separate. Its storage file is not a worker reading list; source workers receive a source-only projection without wiki or implementation fields.

Preserve confirmed findings after implementation. Remove resolved temporary work items after retaining unique evidence centrally; do not archive completed development reports. Discovery performs source/static checks only: no implementation, tests, recording, gameplay launches or competing source generation.
