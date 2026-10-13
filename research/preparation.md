# Preparation handoff — 2026-10-13

Repository/infrastructure preparation, content reconciliation of historical findings, the development-directory consolidation and final local branch integration are authorized. Do not start new movement discovery, new historical change implementation or runtime verification. The [original brief](project-brief.md) is background; the decisions below supersede its undecided alternatives.

## Decisions

- Keep existing Changes; do not reset or automatically restore previously removed implementations. Archive old reports and migrate existing findings to the compact format.
- Product stays in `src/`; development tools live in `development/`; Gradle `buildSrc/` stays at root. Procedures live in `workflows/`, findings/status/history in `research/`.
- Canonical sources are in the local main project: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft`; persistent cache `.minecraft-decompile-cache` next to it. Worktrees infer the main checkout from Git metadata. No AppData/user-home source root. Existing 44 version directories were observed; no sources were regenerated or moved in this preparation.
- Master orchestration may use a few separate suborchestrator chats, each using subagents. Two parallel chats each successfully ran three workers concurrently; see [measurement](capacity-2026-10-13.md). This does not establish capacity for fifteen simultaneous workers.
- Initial version coverage: newest bugfix release per older minor line through 1.20; relevant 1.21 sub-lines; then newest bugfix of each 26.x line through the configured client target (26.2). All stable releases are the eventual goal. Exact applicability still requires source evidence.
- Independent implementers receive a sanitized infrastructure repository, no concrete existing hooks/mixins/changes/history. Integration reconciles their candidates with existing product code.
- Current staffing preference: GPT-6.1 Sol Medium for reasonably complex delegated tasks; GPT-6 Luna High for trivial tasks. Successor chat explicitly requested with GPT-6.1 Sol High. These are session preferences, not permanent workflow model rules.

## Completed implementation and checks

Code checkpoint: `a1c492c8` on `fix/workflow-and-client-architecture`. Checkout: `C:/Users/Wolfi/.codex/worktrees/87e3/LegacyParkourCompat`. Local primary checkout remains `D:/Javastuff/LegacyParkourCompat`; do not confuse its main branch with the prepared task branch.

Historical injections are client-only, and MovementRuntime also gates the logical client, including integrated-server separation. Server speed/collision/floating relaxations are tied to a successful fixed-profile mod handshake on the connection; native/unconfirmed clients and vehicle checks retain vanilla checks. Two obsolete historical server mixins were removed. Existing 110 Change files, including catalog/helpers, retain their arithmetic. 89 hooks were renamed and grouped by topic.

The decompiler adds shared paths, source/cache writer locks, input/tool provenance, default reuse and explicit `--force` regeneration through staged output. Legacy outputs remain explicitly unverified. A mutable release catalog allows new exact releases to be found; pinned exact inputs stay fixed. Vineflower ERROR prevents publication. A short replacement gap remains for readers; serialize publication with consumers. No end-to-end regeneration/reproducibility run was performed.

Development modules moved with Gradle/launcher/log/documentation paths and the world LFS rule updated. The Gym compile dependency was aligned to its already configured PolarPaper 2.2.4 runtime after the old compile-only version returned 404; runtime version did not change.

The allowlist exporter `development/export_implementation_base.py` creates a fresh repository and branch, an empty catalog/mixin configuration, neutral APIs and an infrastructure hash manifest. It was executed into ignored `build/verification-candidate` and that export compiled successfully. Context isolation is not an OS access sandbox.

Successful commands (all Test tasks disabled, no tests or game/server processes started):

```powershell
.\gradlew.bat build :compileTasClientJava -x test --init-script development/disable-tests.gradle --console=plain
# In build/verification-candidate:
.\gradlew.bat build -x test --init-script disable-tests.gradle --console=plain
```

The full build covers product, Gym, test coordinator and current TAS sources. Compilation does not validate Mixin runtime application or tick-level parity. Static independent reviews checked server gating and the decompiler fixes. Git diff whitespace checks passed. Both local main and fetched origin/main were merged into the task branch (already up to date); no incoming semantic corrections were required. No push.

## Remaining work and next step

The research index describes migrated historical records, not newly accepted independent findings. The 340 original records now have canonical/disposition/concrete-gap mappings and current implementation correspondence; see [migration remainder](migration-remainder.md) and [historical bindings](historical-bindings.md). No old evidence was intentionally discarded. Do not treat formatting as renewed acceptance or launch new discovery to fill metadata during preparation.

First inspect the prepared changes against this brief and the current workflow documents, then present the state for expert review. Runtime/Mixin checks, recording-playback determinism, exact historical source regeneration and a later discovery/implementation campaign remain separately authorized phases. Avoid repeating successful builds or capacity probes unless new changes justify them.

Decompiler placement decision: it belongs conceptually to development infrastructure, but its Gradle task implementation remains in root buildSrc, the conventional automatically loaded build-logic location. Moving it into development is possible through explicit source-set/included-build wiring, but adds indirection without a current functional benefit. The development entry point links to it. This was considered before successor handoff.

Documentation migration is complete: all 340 standalone historical records have compact counterparts; 864 original workflow files and the separate staging report remain byte-identical in the archive. 47 old review bindings and 180 wiki/memo references are preserved centrally. Dispositions and unresolved evidence remain explicit; counts do not claim 340 newly accepted independent changes.

## Content reconciliation completion

All 340 baseline records are assigned: 223 canonical findings, 45 consolidated duplicate/revision records, 78 dispositions and four unresolved classification cases. Seventeen grouped records map to multiple independent operations. Multi-file source references, omitted callbacks and invalid witness examples were repaired from existing evidence. Historical exact verdicts were retained; changed assertions are explicitly distinguished from formatting-only content transfer. Current Changes/Hooks/catalog correspondence is present, partial, open or excluded with concrete evidence in the [central map](reconciliation/README.md). No new movement changes, source generation or runtime activity occurred.

All 47 accepted-backlog decision documents were recoverable; 44 archived snapshots match their recorded hashes and three exact reviewed finding revisions were recovered separately. Additional outside-backlog source/code decisions and recovered followup/boundary documents are preserved in the ledgers. The original archive bytes remain unchanged. Directory consolidation and final branch integration follow this content checkpoint.
