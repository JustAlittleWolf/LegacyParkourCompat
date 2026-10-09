# Documentation integration checkpoint — 2026-10-09

## Refs

- Integration target before merge: `main` at `e890a9f59883ed167a1958c0d06a665b88cc3018`.
- Reviewed documentation branch: `fix/orchestrator-workflow` at `a6e4d9b344494fcea85532191c29170d3375e2c8`.
- Merge base: `e890a9f59883ed167a1958c0d06a665b88cc3018`.
- Integration branch: `fix/campaign-integration-2026-10-09`, with the integration record committed on top of the reviewed branch tip. The final integration is a fast-forward of `main` to this branch tip.

## Integrated paths

The reviewed branch changes only documentation and campaign records:

- `AGENTS.md`
- `README.md`
- `workflows/README.md`
- `workflows/fix-implementation/README.md`
- `workflows/fix-implementation/major-campaign.md`
- `workflows/movement-discovery/README.md`
- `workflows/movement-discovery/source-navigation.md`
- `workflows/movement-discovery/source-preparation.md`
- `workflows/orchestration/README.md`
- `workflows/orchestration/retrospective-2026-10-09.md`
- `workflows/orchestration/review-metrics.json`
- `workflows/source-campaign-2026-10-07/README.md`

## Review and disposition

- Verified the checkout was clean and `main` matched the expected pre-merge commit.
- Reviewed the orchestration guide and its discovery, implementation, campaign handoff and source-preparation references, plus the full branch path list and documentation diff.
- No semantic conflict with repository movement scope or implementation invariants was found. The updates preserve vanilla block states, exact-source evidence, independent minimal deltas, resolver behavior, source-only discovery boundaries, separate status tracking, and the requirement to disable all Gradle `Test` tasks for campaign builds.
- The orchestration guide's chat-roster directions are campaign-specific operating guidance; actual chat creation remains subject to the controlling app and user authorization rules.
- `git diff --check main...fix/orchestrator-workflow` passed. No build or runtime check was needed for this documentation-only merge.

## Handoff

The documentation branch and this checkpoint are committed on the task branch. Fast-forward `main` to the task branch, then await an accepted implementation batch before taking on any production-code integration or build work.

## Shared launch incident — 2026-10-09

`create_thread` returned a client ID without an error for each launch. The local app-server log contains a matching `thread/start` event for each, and `read_thread` resolves the resulting exact IDs even though `list_threads(limit: 50)` omits all four. Do not recreate these launches. Use `read_thread` with the recovered thread ID and `hostId: local` for follow-up.

The following statuses were observed at **2026-10-09 18:38 Europe/Vienna**. They are an incident snapshot, not current campaign slot dispositions.

- **Preparation:** client ID `aa9a34f4-1247-4f43-a2e3-e6376be60cb4`; thread `01a12183-c4f8-70e1-8d32-eafe7a37868d`; idle with turn completed. Worktree `C:/Users/Wolfi/.codex/worktrees/bfa3/LegacyParkourCompat`; `fix/source-readiness-2026-10-09` at `b809e54f`, clean.
- **Source ledger:** client ID `955cc4de-b13d-4903-94c7-bb9c0e99fcac`; thread `01a12184-5ece-7f23-be02-9f6407b89de4`; active with turn in progress. Worktree `C:/Users/Wolfi/.codex/worktrees/2442/LegacyParkourCompat`.
- **Sprint collision:** client ID `3134b2a6-87f2-4432-97e0-5c3a2b01849b`; thread `01a12184-70c0-7fb3-a3a0-f6032ef005f1`; idle with turn completed. Worktree `C:/Users/Wolfi/.codex/worktrees/dcfd/LegacyParkourCompat`; `fix/reconcile-sprint-collision-2026-10-09` at `bba3c42691c2bb1527ce98dc5ff942e35bb31690`, clean.
- **Elytra cosine:** clientThreadId `client-new-thread:889c32ad-6dbe-4984-a857-ae2e38917f11`; thread `01a12184-84a1-7f31-bbeb-f9a0a6c1cc0b`; idle with turn completed. Worktree `C:/Users/Wolfi/.codex/worktrees/f6a5/LegacyParkourCompat`; `fix/reconcile-elytra-cosine-2026-10-09` at `9be6ac9`, clean. Its record leaves the release boundary open and notes a mapped-jar hash mismatch requiring evidence correction.

At that time, the preparation, sprint-collision and Elytra slots were releasable and the source-ledger slot remained active. Preparation has since resumed for exact `1.18` and `1.18.1`; treat this slot disposition as historical and consult the coordinator's current queue. No launch retry, app-state mutation, test or runtime action was performed.

## Bounded documentation batch integration — 2026-10-09

- Pre-integration local `main`: `e7f89348a619cc9feb2d47aab7a87fbefbe7649d`.
- Integrated report commits: readiness `b809e54f3096bd178f7248fafb0486465ae6f21f`; this incident record `79cd6945df2e4a2fb00d8594a997c955066adf0d`; coordinator queue snapshot `fce05dfcc142aa53c12665acbe058b6357421149`.
- Input file identities: readiness blob `8e32e4d70ea59433c9ba8407d82758617eba71b4`, SHA-256 `0a169a1dbbe4bfd4cce36164a5716bda407160abd432ce35622396443b7b3be4`; incident blob `254dee821048da510e5e1c82683decf4c897df34`, SHA-256 `750dd00dcc9281f112931da69f6327abcefeb803d009230fcd60eb6e417c1c32`; queue blob `fc423baf61a88e367d87939edcc1e355e5bd4420`, SHA-256 `b2092a3ca086402fee5a7b6300d577a0a285c2e43df984909c31e40c46a0ea91`.
- The Elytra clientThreadId above matches the original `create_thread` result exactly; no UUID correction was needed. The incident slot statuses are explicitly timestamped history because preparation has resumed.
- The queue snapshot remains byte-for-byte as committed by its coordinator owner. Its launch states are historical and require the coordinator's planned refresh; this integration did not edit the queue.
- Readiness remains a publication check only. It does not close discovery slices or verify movement behavior. No F-001 or F-002 proof was integrated; their separate review verdicts remain a gate.

## Accepted source-ledger and endpoint batch integration — 2026-10-09

- Pre-integration local `main`: `8ad316aa84a8704f99d042c2a649bfa23aa4365e`.
- Source commits: run ledger `64d8bc740de0860a43ecc691db0efbdcaef7c19b`; F-001 reconciliation `bba3c42691c2bb1527ce98dc5ff942e35bb31690`; F-002 reconciliation `9be6ac928841ec9d9552ae4e533561623b49866b`; independent endpoint review `d898d4c0ef42f0cdb065aacc36eb30d28dcc06a4`; coordinator queue update `fc6424e92ea5999b76b62e517b97c7dfc752a3c9`.
- Input file hashes (Git blob / raw SHA-256): `1.17.1--1.18.2/run.md` `d69cac145095a77c237ad6e40e85d06df4b1a293` / `c9766e0fc15800d1322225e5cc40c71e991355412118626463859b603ce2ab16`; F-001 reconciliation `e1ed834519d5a50841fc1af361957da998716243` / `c0a6dff4225befb68c0f394aed9198e01c7fde50db00b6b8baa75d027f3ea367`; F-002 reconciliation `dd183718a572208293e1b9da8bc8218c086b66ea` / `efdc4ee931184b09c2382b74939080836cd6fb1e9259671c02f70e3c0ce659d1`; endpoint review `95ea1be9272116a88af552fcaf64ff0570470b52` / `0b257ce7e7dd20d2a96a48a735a1584d7ed340033cae9ec6a496d269e1970dcf`; queue `c5a6fccf99e3a230f506264ba492658f8c9ee536` / `88b2cb83d0865c6528b1254f314760f798d6891fd4591c1a95559006416caeca`.
- The run remains `partial`; its bounded source discovery inventory and full-pair audit are accepted (28 terminal slices across seven inventories), while implementation reconciliation remains open and runtime validation is unperformed. The 14 accepted finding snapshots remain bound to the source review. F-013's original `REQUEST CHANGES` event and bytes remain recorded alongside the accepted identity-only correction; F-014 retains its conditional environment limits.
- The independent endpoint review accepts only the 1.17.1 and 1.18.2 endpoint correspondences. It does not establish the 1.18/1.18.1 cutover or runtime parity. The source ledger now records the accepted full-pair freeze for this exact interval; the review's earlier instruction to keep the pair partial is historical to its `e7f89348` baseline. F-001 still needs the 1.18/1.18.1 boundary check. F-002's mapped-jar identity correction remains open.
- The F-002 correction-binding file `workflows/source-boundary-reviews/F002-artifact-correction-binding-2026-10-09.md` is absent from `main` and this integration tree. It was introduced by `7a0811f1ee9fe49ba6f8b0411e08fce21d915cd7` on the Elytra author branch `fix/reconcile-elytra-cosine-2026-10-09` (tip observed at `741cab9a72aa7fe1e1e5164001b6895307d2e3ed`). The memo explicitly says the corrected snapshot is pending independent review; it is excluded from this batch, leaving the F-002 identity correction open. The author branch and its work were left untouched.
- The coordinator-owned queue is integrated at the exact `fc6424e9` commit and was not edited. Its blob is byte-identical to that source commit. The queue has mixed LF line endings and a final CRLF: plain `git diff --check` flags that final carriage return, while `git -c core.whitespace=cr-at-eol diff --check` passes. No Java changes, F-001/F-002 boundary claims, builds, tests, runtime checks, or pushes were made.

## Source preparation and rejected candidate reviews — 2026-10-09

- Pre-integration local `main`: `e1dd0a65474a56e24ceea36ba300ea54c135d6a2`.
- Integrated exact refs: 1.18/1.18.1 source preparation merge `62346b3c8cf6eb8f2315b994aeaadf88f980842e`; independent F-3 lava and F-2 spectator Lunge rejection reviews `a5fd2b761e08ed63798af40def8ece1509c2e536`. The refs contribute only the four files listed below relative to pre-integration `main`.
- Input file identities (Git blob / raw SHA-256): 1.18 prep `55c90f755e1b94c30713a9d6d7d603036432f15c` / `8d709442ed5ea39d81dd3524bc9860712f28c1021d6787065bdbf450d97e2d86`; 1.18.1 prep `ba2f806c8bdf2ea025c211ab0c1296571ea2acca` / `4f4d6e3d9079b09b5aa8ce6876ca6ba632b544419bf2aeed33a04a22cd565c45`; F-3 lava review `68851a0eb370e75ef5315dea6ece9353cdf4889f` / `297b62dd7066a721db1a23830de6536c7a653b2554893c0c9705327ba2fb47b3`; F-2 spectator Lunge review `2328977e7b536b37fcd369fe71b827878659f38a` / `5d430d2b2dbd8afc7b3439a471b5d6dc282cdd92a7c7c94a8c9d73fae4df0ba8`.
- The preparation reports establish exact 1.18 and 1.18.1 Mojmap artifact identities, manifests and provenance. They do not verify method bodies, source correspondence, or movement behavior.
- The F-3 review is `REQUEST CHANGES` for the immutable candidate because its coordinate expression uses float addition before double subtraction, while its reported overlap values correspond to a different double-precision expression. Both interpretations still pass the cited branches, so the source difference remains supported; a replacement snapshot must use one consistent coordinate definition and recomputed values, then receive blind review. The original snapshot and rejection remain immutable history.
- The F-2 review is `REQUEST CHANGES`: on the A spectator server path, virtual dispatch reaches `ServerPlayer.attack`, whose spectator override selects the camera instead of calling `Player.attack`; the claimed Lunge impulse is therefore unreachable. B also routes spectator input to camera selection. No accepted movement finding comes from this candidate.
- These reviews are rejection history, not accepted findings. No candidate edit, replacement/correction snapshot, source-ledger update, source freeze change, or native implementation was included. The pair remains active/partial. Any pending F-002 artifact correction remains open and was not integrated.
- The task branch was created from `main`, explicitly merged `main`, then merged only the two exact refs above. No code, build, test, runtime, push, or cleanup action was performed.

## F-002 artifact identity acceptance and coordinator queue — 2026-10-09

- Pre-integration local `main`: `a2be7717c34b3c41a131b23629504f358d8b1fce`.
- Integrated exact report sources: corrected F-002 identity snapshot commit `2f3425601753d8b153e795a973a147e6ce0cb0b5`; author binding memo commit `959751e85c053f1f1d84dd7b6ed741f92d36620f`; independent metadata-only acceptance commit `3026b37ea424037ea6b948bae52c2c9888910be1`. The author commit also contains an implementation-reconciliation file; it was excluded. Only the three exact report paths were imported.
- Coordinator queue source: exact commit `6111ad781dca30c008c6541d0150886296ea7993`, path `workflows/orchestration-2026-10-09/queue.json`. Its bytes were imported unchanged. This queue snapshot predates the independent acceptance: its F-002 slot says the corrected snapshot is pending metadata review, which is the state recorded when the queue was captured. The subsequent acceptance is recorded here and in its own report; no queue status was rewritten.
- Input file identities (Git blob / raw SHA-256): corrected F-002 snapshot `dbce0be4ade1fdd84acd9867be201f9f630609cc` / `ccc39601c087a9df5d60e9506e7eaa1831adb7336b89c346bbdeecfcddb71f01`; author binding memo `6a80bf497d48502d92e68b18f90853a6370e8e58` / `3da3d1afb502bd4b201f77417a7241873d813116f2d4905a37409b54c27b9507`; independent identity review `7952d5702c7dbeec85e1a200dcc221519ba5355e` / `8f89b2b12cdf184538cb9779b5ad049a01107134fa5019625bd08de22b23a1d4`; coordinator queue `29b139777ddfa3d766365bd20be793d806116736` / `d17e9923674cb3292c8ccad0c476450b6083c9d59273716cab21e65f02058770`.
- Binding chain: the original accepted F-002 snapshot remains commit `e531086eecc778432b40b2b9ef39499b8a7f0dba`, path `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-002-elytra-cosine-precision.md`, blob `75b6206ca3434ffd96d84caa1a6ff2fc6b5c4ab5`, raw SHA-256 `bb72676167340fa5e001062b3f9cb69ce36d0160b37fe834212693a4feb0b546`. Its prior source acceptance remains the review at `2fd121e116d7185165c76aea1c3708273afc7ea1`. The independent identity review binds the exact corrected snapshot above and verifies the one-digest byte replacement against the canonical 1.18.2 artifact manifest and mapped jar.
- The accepted correction changes only the 1.18.2 mapped-jar identity from `60a2017dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba` to `60a2016dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba`. It does not change the finding’s movement claim, accept a first changed release within `(1.17.1, 1.18.2]`, or establish implementation or runtime coverage. The original finding bytes and original acceptance remain unchanged.
- No production code, build, tests, runtime, push, or cleanup was performed. The integration branch was created from local `main` and explicitly merged `main` before importing these exact report and queue bytes.
