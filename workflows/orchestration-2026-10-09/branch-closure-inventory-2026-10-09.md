# Branch-closure inventory — 2026-10-09

## Snapshot

- Local checkout: `main`, HEAD `5289e70dcd58da6a9ffc9181b1a48d817de41546` (F-4 integration/build input). No production changes are pending in this inventory.
- The coordinator completed cleanup of 52 verified redundant refs from 119, leaving 67 local refs at cleanup time. Three audit-task refs were then created; the first advanced with its report-only commit. The current snapshot observes 70 local refs: 19 ancestors of `main` (including `main`) and 51 non-ancestor refs. `git worktree list` reports 145 checkouts. Cleanup preserved worktree directories, remote refs, unfinished/paused refs and all deleted ref identities in the imported records.
- Cleanup source is current tip `fix/campaign-coordinator-2026-10-09` at `9b6d2cc3890b5950a30aaeeb0aa24ea7e193542c`. Imported exact `branch-cleanup/` records and `queue.json`; imported the coordinator session policy before adding the requested question-routing rule below. `queue.json` is a coordinator snapshot dated `2026-10-09T19:03:50.915Z`; its per-worker states and next-action text are historical. Its `mainVerified` value matches the present main tip, but its assignments are not a live status feed.

## Ancestry-merged refs

These refs are ancestors of the current `main`; cleanup had already removed other unattached redundant ancestors. The passenger-yaw ref is an exception to ordinary retirement: an active recovery worker is preserving staged work in its attached legacy checkout, so keep that worktree and do not clean up the ref while recovery is active.

`main`; `feat/movement-discovery-1-11`; `feat/movement-passenger-yaw-2026-10-08`; `feat/source-discovery-1-15-2-1-16-5-resume`; `feat/source-discovery-1-20-2-1-20-4-resume-2026-10-08`; `feat/source-discovery-1-20-6-1-21-1-resume-2026-10-08`; `feat/source-discovery-1-21-1-1-21-3-resume-2026-10-08`; `feat/source-discovery-1-21-10-1-21-11-resume-2026-10-08`; `feat/source-discovery-movement-1-21-8-1-21-10-resume-2026-10-08`; `feat/source-discovery-movement-source-1-11-2-1-12-2`; `feat/source-discovery-movement-source-1-12-2-1-13-2`; `feat/source-discovery-movement-source-1-14-4-1-15-2`; `feat/source-discovery-movement-source-1-21-4-1-21-5-resume`; `feat/source-discovery-movement-source-1-21-5-1-21-8-resume-2026-10-08`; `feat/source-discovery-movement-source-1-8-9-1-9-4-solid-resume`; `feat/source-discovery-movement-source-1-9-4-1-10-2-resume3`; `feat/source-movement-1-13-2-1-14-4-resume6`; `fix/resume-coverage-audit-2026-10-09`; `fix/uncommitted-work-audit-2026-10-09`.

## Non-ancestor refs with exact content already integrated

### Code copied by cherry-pick, with reviewed content retained

- `fix/mounted-player-lava-box`: F-4 source/code evidence and corrected implementation are integrated; main code commits `f6b4ffae` and `5289e70d`. Initial `NEEDS CHANGES` candidate and bounded correction `ACCEPT` remain preserved. Build passed; runtime and pair completion remain open.
- `fix/water-depth-jump-initiation-2026-10-09`: accepted Y=256 production source is integrated by commits `db833568`, `90f8f805`, `a7be5f77`, and `9eb93a3e`; exact reviewed source tree match and test-disabled build are recorded in `integration.md`. Rejected intermediate history is preserved.

### Report-only or checkpoint content already on main

The source branches below are intentionally not ancestry-merged; their relevant report/checkpoint content is already present on `main` and bound in `integration.md` or the imported cleanup records:

`feat/review-f009-coordinate-scope-r2-2026-10-09`; `fix/campaign-closure-audit-2026-10-09`; `fix/campaign-closure-audit-isolated-2026-10-09`; `fix/campaign-closure-audit-report-2026-10-09`; `fix/campaign-coordinator-2026-10-09`; `fix/campaign-docs-integration-2026-10-09`; `fix/campaign-status-2026-10-09`; `fix/fluid-current-player-impulse-r3-2026-10-09`; `fix/pane-neighbor-providers-third-2026-10-09`; `fix/pane-reviews-17-24-2026-10-09`; `fix/pane-reviews-25-32-2026-10-09`; `fix/reconcile-sprint-collision-2026-10-09`; `fix/review-1171-1182-boundaries-2026-10-09`; `fix/review-F3-r2-technical-2026-10-09`; `fix/review-clean-input-2026-10-09`; `fix/review-endpoint-reconciliation-2026-10-09`; `fix/review-f007-source-boundary-2026-10-09`; `fix/review-f09-input-scaling-2026-10-09`; `fix/review-fluid-checkpoints-2026-10-09`; `fix/review-fluid-current-flow-r3-2026-10-09`; `fix/review-input-producers-d11-d13-2026-10-09`; `fix/review-lava-shallow-overlap-r2-2026-10-09`; `fix/review-mcpk-y256-2026-10-09`; `fix/review-pane-neighbor-providers-2026-10-09`; `fix/review-pane-neighbor-providers-9-16-2026-10-09`; `fix/review-pane-provider-continuation-2026-10-09`; `fix/review-wiki-attribution-2026-10-09`; `fix/source-boundary-1171-1182-2026-10-09`; `fix/source-prep-261-2611-2026-10-09`; `fix/source-readiness-1192-1202-2026-10-09`; `fix/sprint-air-speed-early-boundary-2026-10-09`; `fix/wiki-attribution-2026-10-09`; `fix/y256-technical-binding-2026-10-09`; `newfix/review-f008-source-boundary-2026-10-09`; `newfix/review-mounted-lava-f4-2026-10-09`; `fix/remaining-branch-audit-2026-10-09`.

Exact report identities are recorded in `integration.md`; the branch-cleanup equivalence manifests give per-ref changed-path and blob-equivalence checks where performed. The additional F-4 integration record and coordinator files imported in this batch are documented there too. The 1.17.1–1.18.2 boundary review imported in this closure pass accepts only its bounded source conclusions and notes a non-substantive marker-hash typo in the candidate memo.

## Preserved superseded or invalidated history

These refs and immutable report events remain available for audit; they do not authorize implementation:

- `feat/source-1214-1215-clean-2026-10-09`: incomplete scale candidate was independently invalidated; fresh blind discovery is required.
- `fix/review-f21-scale-attribute-2026-10-09`: F-21 invalidation report; preserve the candidate and its invalidation without implementing it.
- `feat/review-f009-player-coordinate-clamp-2026-10-09`: original F-009 scope/reachability rejection, superseded only for the separately corrected narrowed finding in r2. The local ref uses the `feat/` prefix; no `fix/` ref exists.
- `fix/review-eyeheight-correction-hold-2026-10-09`: earlier hold history remains, alongside the later bounded standing-provider acceptance; mounted/non-default cases remain unresolved.
- `fix/review-mcpk-current-flow-r2-2026-10-09`: r2 cutoff wording was superseded by r3; retain the original review and correction chain.
- `fix/mcpk-current-flow-review-2026-10-09` and `fix/mcpk-water-exit-2026-10-09`: superseded R2 report/candidate chain. Keep the original bytes; continue open source coverage only from accepted R3 evidence. The remaining water-current implementation status is separate from those old refs.
- `fix/remaining-branch-audit-2026-10-09` report contains an invalid full commit ID for the pane-next ref. Preserve the imported report unchanged; the live local ref resolves to `f6be0910fd0e4f7ed16fe2a146545f87fe1d2f68`. Record this identity correction separately from the audit report.
- Within completed integrations, preserve Y=256 rejected intermediate `6401f793`, F-4 initial candidate `8de4544e` and its `NEEDS CHANGES`, and pane slice 23/29 original `REQUEST CHANGES` reports. Their later corrections are separate immutable evidence, not edits to those records.

## Unfinished or paused refs

These refs remain outside main because their assignments are incomplete, paused, or lack an accepted implementation handoff. Keep them and their worktrees intact; check the owner's latest record before resuming.

`feat/source-1192-1193-resume-2026-10-09`; `feat/source-1193-1194-2026-10-09`; `feat/source-1201-1202-2026-10-09` (paused); `feat/source-12111-2612-d2-2026-10-09`; `feat/source-discovery-1-19-4-1-20-1-resume-2026-10-09`; `fix/pane-neighbor-providers-next-2026-10-09`; `fix/review-eye-state-fluid-2026-10-09`.

The bounded current-flow r3 report and F-4 finding are accepted, but they do not close their source pairs. The current-flow r2 reports and water-exit candidate are superseded history; continue the open fluid-row and pair coverage only from accepted r3 evidence. F-4 still needs full-pair discovery and runtime validation; Y=256 still has the first protected release, 1.13.2 mapped-JAR identity, and runtime questions open. The 1.17.1–1.18.2 pair remains partial, including the separate F-002 artifact correction. F-008's portable implementation review, other source inventories, and wiki/provider coverage also remain separate open work.

## Remaining integration actions

- **No accepted implementation/build batch remains in this closure slice.** F-4 and Y=256 are integrated and built; their build identities and limits are in `integration.md`.
- The coordinator snapshot's explicit report-only actions for F-009, F-007 build-record recovery, current-flow r3, and the early sprint boundary are already represented on current `main`. The early-sprint memo/review and current-flow r3 memo/binding/review have exact file identities recorded in `integration.md`.
- The 1.17.1–1.18.2 independent boundary review was the remaining accepted report path identified by the retained equivalence manifest; it is now imported exactly and recorded in `integration.md`.
- First audit report `remaining-branch-closure-audit.md` is now imported exactly; it confirms the corrected F-009 ref name and identifies no other unclassified non-source refs. Two audit reports remain in progress. A separate recovery worker is preserving three staged source changes and three deleted report paths from the legacy passenger-yaw worktree at `C:/Users/Wolfi/.codex/worktrees/movement-passenger-yaw-2026-10-08`, ref `feat/movement-passenger-yaw-2026-10-08`, HEAD `32c8b0a7`. Do not alter that worktree/ref during recovery; the worker's next action is to preserve the exact patch and reconcile it against accepted evidence and main.
- Three dedicated audit branches/worktrees were created at the current main tip. The first report from `fix/remaining-branch-audit-2026-10-09` is now imported at exact blob `96ed5cfee803ed3689258e87f838c98e6b4fc704`; two reports remain in progress on `fix/resume-coverage-audit-2026-10-09` and `fix/uncommitted-work-audit-2026-10-09`. Import their exact committed results and reconcile any newly identified missing content before final closure. Do not retire these audit refs or worktrees.
- No refs were deleted, merged, renamed, or moved by this inventory. Do not treat the presence of an old unmerged ref as evidence that its unique report is missing; use its recorded content identity and the main ledger.

## Static verification

The source import files were checked by Git blob ID and raw SHA-256; cleanup records and queue match their exact coordinator blobs. `git diff --cached --check` passes for the staged documentation except the immutable slice-28 digest-correction report, whose source-exact blob `e8ceae6273aa59b02da00cb19d9d1bbe649fb0af` retains one trailing space on its final content line (raw SHA-256 `8FE8928DA48649302A98B31F7A779056B3025FB884C8A8AD7D05B2551AB60A1F`). That report was preserved byte-for-byte. No source code, build, tests, runtime, branch ref updates, or worktree cleanup occurred in this inventory pass.
