# Discovery: 1.21.11 to 26.1.2

- Status: blocked
- Scope: client player movement; older A = 1.21.11; newer B = 26.1.2
- Repository revision and start date: `f294fa30555e360a2f8bacee50852e45b89d6250`; 2026-09-26
- Mapping alignment: **blocked: mapping alignment**. The decompiler resolved both requested IDs exactly. No single supported family produced mapped sources for both releases. No source comparison or movement findings were started.
- Source preparation commands (all with `-g .gradle-user-home`):
  - `gradlew.bat decompileMinecraft --versions=1.21.11,26.1.2 --mappings=mojmap` — 1.21.11 finished as `mojmap`; 26.1.2 rejected: no official Mojang mappings.
  - `gradlew.bat decompileMinecraft --versions=1.21.11,26.1.2 --mappings=yarn` — 1.21.11 finished as `yarn` build `1.21.11+build.6`; 26.1.2 rejected: no Yarn builds.
  - `gradlew.bat decompileMinecraft --versions=26.1.2 --mappings=legacy-yarn` — rejected: no Legacy Yarn builds.
  - `gradlew.bat decompileMinecraft --versions=26.1.2 --mappings=feather` — rejected: no Feather builds.
  - `gradlew.bat decompileMinecraft --versions=1.21.11 --mappings=unobfuscated` — rejected: 1.21.11 client jar is obfuscated.
- Decompiler logs: Gradle output was captured in the task transcript but not persisted as files. The successful completion lines and the mapping rejection messages are recorded above. No raw log is claimed.
- Toolchain: JDK 25.0.3+9-LTS for Vineflower; Gradle 9.7.1; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1. Default heap 4G and repository decompiler options. (Gradle wrapper distribution downloaded during this run.)

## Artifact manifest

Cached artifacts are local, ignored files under `build/minecraft-decompile-cache/`; source outputs are ignored under `decompiled_minecraft/`. Hashes below are SHA-256. Mojang version JSON records identify the exact client and mapping artifacts; hashes are recorded here for reproducibility.

### A — 1.21.11

- Exact resolved release: `1.21.11` (requested `1.21.11`; log confirmed exact match).
- Client jar: `build/minecraft-decompile-cache/1.21.11/client.jar`; SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`; Mojang version JSON SHA-256 `13e195800429ad001c3d897dd646638b2bc9a9fc5ce01d840d440eb0f2ea5351`.
- Mojmap attempt: `client_mappings.txt`, SHA-256 `517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd`; mapped jar `client-mojmap.jar`, SHA-256 `853daa46088f8f5c6924d08a4019394c1647d847015fe399c791872aaac5f26f`; output `decompiled_minecraft/1.21.11/mojmap/`. Official mapping coordinate is the version JSON `downloads.client_mappings` object; release-specific mapping artifact.
- Yarn attempt: coordinate `net.fabricmc:yarn:1.21.11+build.6`; mapping file `build/minecraft-decompile-cache/yarn/yarn-1.21.11+build.6.tiny`, SHA-256 `c48df4f527e02d402a8ebc92c612cb152cb50adab9fb98d57c9b2124b040d14a`; mapped jar `client-yarn.jar`, SHA-256 `7885154016fb685870489b3c67158fda6eb0c5d37fb1a622aaf815266e94cce3`; output `decompiled_minecraft/1.21.11/yarn/`.
- No source/resource evidence was cited because comparison could not begin.

### B — 26.1.2

- Exact resolved release: `26.1.2` (requested `26.1.2`; log confirmed exact match).
- Client jar: `build/minecraft-decompile-cache/26.1.2/client.jar`; SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`; Mojang version JSON SHA-256 `2e7b23dcfb78ab3921ea663347be48508cbb286756d2b46027b47008a006c85c`.
- Mojmap rejected because the version JSON has no official `client_mappings` artifact. Yarn, Legacy Yarn and Feather were each rejected by their family resolver for lack of builds. No mapped jar or source output was produced for this run.
- No source/resource evidence was cited because comparison could not begin.

## Mapping-family availability result

| Family | 1.21.11 | 26.1.2 | Result |
| --- | --- | --- | --- |
| `mojmap` | Available; successful decompile | Unavailable; no official mappings | Not common |
| `yarn` | Available (`1.21.11+build.6`); successful decompile | Unavailable; catalog returned no builds | Not common |
| `legacy-yarn` | Not queried | Unavailable; catalog returned no builds | Not common |
| `feather` | Not queried | Unavailable; catalog returned no builds | Not common |
| `unobfuscated` | Unavailable; client jar is obfuscated | Not queried | Not common |

Family checks used the repository's `decompileMinecraft` resolver and its configured mapping catalogs. A third release or a different mapping namespace was not used to bridge the pair. Existing `decompiled_minecraft` outputs were inventoried before the task; neither requested version had an output directory. The generated `mojmap` and `yarn` outputs for A are task-created navigation artifacts only and are not evidence for a cross-release finding.

## Correspondence and call order

Not established. Per workflow, source correspondence requires one explicit mapping family available for both exact releases. No guessed name-based correspondence is recorded.

## Coverage ledger

Comparison could not begin due to mapping alignment. All workflow navigation stages are blocked at the shared prerequisite:

- Slice ID / stage / behavior: 1 / local input and tick ordering; Status: `blocked`; A and B source members: not paired; rationale: no aligned mapped source pair.
- Slice ID / stage / behavior: 2 / player-specific state and gates; Status: `blocked`; A and B source members: not paired; rationale: no aligned mapped source pair.
- Slice ID / stage / behavior: 3 / living movement integration; Status: `blocked`; A and B source members: not paired; rationale: no aligned mapped source pair.
- Slice ID / stage / behavior: 4 / entity movement and collision; Status: `blocked`; A and B source members: not paired; rationale: no aligned mapped source pair.
- Slice ID / stage / behavior: 5 / blocks and fluids; Status: `blocked`; A and B source members: not paired; rationale: no aligned mapped source pair.
- Slice ID / stage / behavior: 6 / effects, enchantments, attributes and equipment; Status: `blocked`; A and B source members: not paired; rationale: no aligned mapped source pair.
- Slice ID / stage / behavior: 7 / external influences and dependency closure; Status: `blocked`; A and B source members: not paired; rationale: no aligned mapped source pair.

## Dependency queue and blockers

- Blocker `MAP-ALIGNMENT`: 26.1.2 has no build in any community mapping catalog supported by this repository and no official Mojmap artifact. 1.21.11 is obfuscated, so `unobfuscated` cannot align it with a native namespace. A comparable pair needs both releases remapped into a verified common namespace using exact release-specific mappings, or a separately scoped mapping-normalization workflow. Do not infer alignment from similar names or bridge through another version.

## Finding index

None. No behavioral conclusion was drawn.

## Resume checkpoint

- Last completed slice: none; release and family availability check only.
- Next step: obtain or build an independently verified normalization path that places both exact client jars in one common namespace, preserving per-release mapping provenance; then restart source navigation at stage 1.
- Outstanding dependencies: mapping alignment; persisted raw Gradle logs are unavailable (the run transcript records success/failure summaries).
- Assumptions requiring verification: any proposed mapping bridge must have exact release-specific inputs and validated namespace semantics before use.

## Source audit closure

- Coverage counts: 0 compared; 7 blocked.
- Unresolved gaps: all movement behavior, correspondence, resources and indirect dependencies remain unaudited because aligned source evidence is unavailable.
- Evidence/hash/correspondence audit: release IDs confirmed by exact decompiler resolution logs; client and available mapping artifacts identified and hashed. No source hashes or findings are claimed.
- Runtime validation: not performed (separate workflow).
