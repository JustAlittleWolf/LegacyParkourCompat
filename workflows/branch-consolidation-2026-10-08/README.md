# Documentation consolidation — 2026-10-08

## Scope and checkpoint

This documentation-only integration started from local `main` at `bdd31fe258e8642a2e036a526071dd45dcc2a685` on branch `fix/branch-consolidation-2026-10-08`. The source, review, Wiki, MCPK, and coordinator refs were frozen at the exact heads recorded below and in [source-resume-heads.tsv](source-resume-heads.tsv) before integration. The canonical coordinator/backlog files and latest report revisions were preserved. All source worktrees and branches remain available for resume.

The latest production build recorded by the coordinator is commit `dc1b65b29e3c2561175bd54276ae83e37cd1c045`. This consolidation did not run a build, tests, a game client, TAS, a server, or Docker.

## Integrated branch heads

| Frozen head | Reason and result |
|---|---|
| `feat/movement-coordinator-2026-10-08` — `9b36dbd51a9cf1ce2332bafec06d2fa4d574be0b` | Current coordinator transfer, work queue, pause/goal status, and resume records. |
| `feat/accepted-finding-backlog-2026-10-08` — `0645b0078f0301d255826e3f28eb5bf18c0e9730` | Separate latest backlog tip. Its four ledger files were imported byte-for-byte after review found this tip was not an ancestor of the coordinator. Merge commit `8180f676068e83dbba697c7b9de7a66a68cd168c` records the branch history while preserving the current integration tree for unrelated docs. The stale `MC1204-1206-02` pending row is removed and the summary ledgers refreshed. |
| `fix/full-pair-audit-1171-1182-2026-10-08` — `b23789a924a67a9f24f6f85cd561067d38e0cb2c` | Full 1.17.1–1.18.2 source inventory and bounded findings. |
| `fix/review-1171-1182-source-2026-10-08` — `2fd121e116d7185165c76aea1c3708273afc7ea1` | Independent ACCEPT review for the full-pair coverage freeze and all 14 exact finding snapshots, including corrected F-013 binding. |
| `feat/source-1-17-1-18-2-fresh-oct8` — `df418e06b957399aa4c31eaf69d38422146ac296` | Latest author report and immutable source correction material referenced by the review. The F-013 finding and its correction binding remain byte-exact; no finding file was edited during integration. |
| `feat/blind-source-snapshot-review-2026-10-08` — `a2e510bdc7b284b4b4d0e7b323f6eea079abee80` | Additive source-only snapshot review records for separate release pairs. |
| `feat/blind-review-1204-1206-f14-2026-10-08` — `581b5bd2cb44981c4889eaf05ea739134e332b87` | Historical bounded review of six 1.20.4–1.20.6 findings and F-14. Its REQUEST CHANGES verdicts remain attached to those exact old snapshots; this report is not a blanket acceptance. |
| `fix/mcpk-independent-followup-review` — `32657c76af79c019fefae6f8cb604b896ca01cba` | Preserves the earlier independent follow-up report as review history. |
| `feat/mcpk-independent-review-2026-10-08` — `fcfd4480bfa2af6fa4a94a4c5ace863406709321` | Preserves the bounded independent MCPK review record. At the two overlapping summary files, the already newer clean-rereview text was retained. |
| `fix/mcpk-clean-rereview-dispositions` — `7c27de431c013edbd56592f86b83b3c1d0268e29` | Latest MCPK full catalog, corrected identities, review dispositions, and partial-lane checkpoints. |
| `feat/minecraft-wiki-review-2026-10-08` — `4e77925a59e3a8af38ea9f276f04903cb07ee927` | Preserves independent Wiki review reports and their bounded verdicts, including historical rejected snapshots. |
| `feat/minecraft-wiki-audit` — `636ef45f2969576c7d4a57f18469ec1f8539995e` | Latest Wiki catalog/checkpoint. At the two summary conflicts, its current report text was retained; separate review reports remain available. The catalog and research remain partial. |
| `fix/minecraft-wiki-audit-review-2026-10-08` — `b48a12bd3ef62cce13784fffc8b413cd29639ebc` | Additive Wiki audit review fix; its review report was already byte-identical at the latest Wiki audit tip, and the branch history is retained. |

The coordinator and backlog tips are separate, merged refs. Only the four current backlog ledger files were taken from `0645b0078f0301d255826e3f28eb5bf18c0e9730`; older or unrelated files from that branch were not allowed to replace current integration docs. The current source tips for all 26 pair lanes, with their retained branch names and statuses, are listed in [source-resume-heads.tsv](source-resume-heads.tsv); [source-merge-checkpoints.tsv](source-merge-checkpoints.tsv) maps each frozen tip to its merge commit. Pair-specific run reports and candidate/finding files came from those canonical tips; shared guidance, templates, navigation, and the completion checker were restored to the `main` versions. Where pair reports conflicted, the canonical lane tip was retained; earlier review and report versions remain in Git history. No immutable accepted finding bytes were edited.

The latest source reports, catalogs, and history remain separate from implementation and runtime status. The 1.17.1–1.18.2 mutable pair ledger remains `partial` pending owner reconciliation; its source-coverage freeze and 14 bounded finding ACCEPT reviews, including corrected F-013, remain independent evidence. The corrected F-013 snapshot blob is `3d2e66195b4d2620cba264a77b598bfbbaea833b` (raw SHA-256 `1fbd87afa13a28150fef0cb6acfaa1d06751883b00b319e7d56e7bebc9263bbc`) and matches the author tip. The corrected 26.1.2–26.2 BED finding blob is `7762fc6328e89c66afe6c28c563c6a4bd7dd1d47` (raw SHA-256 `c776cc145aa9e6373d83b24c209fb1c025d914dc63c64041b4aaf7d515fc227d`) and matches its canonical lane tip; rejected predecessor snapshots remain in report/history. The 1.21.4–1.21.5 report retains its disclosed provenance limitation. Other pair lanes, MCPK, and Wiki research remain partial/open.

Two supplemental bounded pair documents remain alongside the canonical lane reports: the 1.20.4–1.20.6 flight/jump snapshot from commit `7e7b7bb` and the 1.20.6–1.21.1 Soul Speed candidate from earlier review/candidate history. They do not replace the canonical run reports or change their partial status. The 1.21.5–1.21.8 canonical report removes two discarded Fishing Hook candidates; their older text remains recoverable in Git history.

## Production-code audit

The read-only audit against baseline `bdd31fe258e8642a2e036a526071dd45dcc2a685` found no production-changing merge candidates among the eight reviewed unmerged code branches. Their reviewed commit tips, reasons, and evidence are preserved verbatim in [code-branch-dispositions.json](code-branch-dispositions.json). The source tree, `buildSrc`, and build configuration remain unchanged from baseline in this documentation integration. The prior bad candidate implementations are not imported as production behavior; their existing review history remains in its original records.

## Remaining source resume heads

No source pair branch or source worktree was removed or advanced as part of this integration. The frozen coordinator registry at [coordinator-state.json](../orchestration-2026-10-08/coordinator-state.json) and [branch-tips.txt](../orchestration-2026-10-08/branch-tips.txt) remains the source for pair status and resume refs. Those refs retain the documented `partial`, `active`, or `pending review` states. The accepted 1.17.1–1.18.2 documents do not close its separate implementation/runtime gates.

The bounded audit of all 26 canonical source-pair heads found documentation/workflow changes only. All 26 latest pair tips are merged into this integration branch, ready for root's review and primary-main integration; their source branches and worktrees remain the resume points. The complete lane keep list and frozen OIDs are in [source-resume-heads.tsv](source-resume-heads.tsv). The eight reviewed production-changing refs remain excluded, with exact dispositions in [code-branch-dispositions.json](code-branch-dispositions.json). No pair is marked complete by this consolidation.

## Verification and handoff

The integration diff against baseline contains documentation paths only. `src/`, `buildSrc/`, and build configuration have no changed paths. The source/build byte-identity check against `bdd31fe258e8642a2e036a526071dd45dcc2a685` returned no differences. Shared guidance/template/navigation/checker files match local `main`. All 26 frozen source tips map to merge commits in [source-merge-checkpoints.tsv](source-merge-checkpoints.tsv). The corrected F-013 and BED artifact hashes above match their author lane tips. No tests, build, runtime launch, push, or ref cleanup was performed.

The integration branch is ready for independent review. The source branches/worktrees remain intact and the campaign remains paused; push, local obsolete-ref cleanup, and the bounded shutdown attempt remain root-owned.

## Administrative authority and pause

The human authorized this bounded consolidation and separately authorized a push, local obsolete-ref cleanup, and at most five shutdown attempts. That operational scope does not resume the movement campaign. The campaign remains **PAUSED**; source discovery/implementation queues, the coordinator's 20-role cap, root model `gpt-6.1-sol` Medium, worker model `gpt-6-luna` High, and the no-tests/no-runtime restrictions remain in force. The earlier stop still requires an explicit human request to resume campaign work. Push, ref cleanup, and shutdown are root-owned follow-up actions, not actions performed in this documentation worktree.
