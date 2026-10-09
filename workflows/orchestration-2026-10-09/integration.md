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

## Accepted fresh 1.21.4–1.21.5 input/tick checkpoint — 2026-10-09

- Pre-integration local `main`: `45179643d0f1a40edf37107e624dbd5f2cc97870`.
- Integrated exact source checkpoint `b8c502b89d08df1436b16e5d98e156440a7bad40` (parent/base `1d027c706dd22fd6df2fe14deaf3a02695d6094e`) and independent bounded review `9aa262153439c191f67a57bd41b356868bde8bc4`. Each exact checkpoint changes only its named report path; no continuing source branch tip was merged.
- Input file identities (Git blob / raw SHA-256): `workflows/source-campaign-2026-10-07/1.21.4--1.21.5/run.md` `e670759b3bf1986c02deff7a8dc9ae548e26936d` / `c55a652fbe0a2f5b96e083e9154dde93de9a493d8c89533bb1fb3be935edede7`; `workflows/source-boundary-reviews/2026-10-09-fresh-input-1214-1215-review.md` `4b2c433d4146cbb249342873fde9ed71ef69967d` / `167f7283ad24a72c6f12d0d2f9535da388ffa4c52a96a5bac6678ef335c37cac`.
- The review accepts only the fresh `FRESH-S1-INPUT-TICK-ORDER` slice: paired class correspondence, tick/`aiStep` entry order, keyboard input sampling and bounded local-player decisions. It confirms the checkpoint’s overlap with F-03 and F-09 without creating duplicate findings. It does not accept inherited rows or claim full tick-graph closure.
- D7 input application/travel and direct `Entity#getInputVector()` tracing remain open; D8 keybinding/option producers remain open. Other inherited dependencies and coverage rows remain open, and the pair remains partial. The existing provenance limitation for earlier source-owner implementation exposure remains attached to inherited work; the review covers only this fresh slice.
- The exact `run.md` diff against pre-integration `main` adds only the fresh section; inherited report bytes and findings were preserved. No semantic correction was needed. No implementation files or unrelated reports were changed. No production code, build, tests, runtime, or push was performed.

## Accepted Minecraft Wiki Jumping attribution correction/history — 2026-10-09

- Pre-integration local `main`: `f1f3945eeb4025197c699c75cd4cda821c9135cd`.
- Owner correction commit `89f812397e6420f0381abc952242c848c59f5028`; final author tree `710c0f215ab364bf1df8ec0d59364fe739877198`. Only the four exact Wiki-audit paths listed below were imported from the final author tree; its merge commit and any unrelated incoming files were not merged.
- Review history preserved byte-for-byte: original `REQUEST CHANGES` report from `711dc27d9cdd816867704de9f99df4211814a748`, and bounded r2 `ACCEPT` report from `6a3339e222765cc61f98efc62234dba138c8483e`. The r2 report supersedes the first verdict only for reconciliation of the three stale summary passages; the original report remains an unchanged record of the earlier decision.
- Imported file identities (Git blob / raw SHA-256): `CHECKPOINT.md` `11144c2e1a578df2021bf3d69b0d808ebc36202e` / `1c5116584859788b6a43c9a95338427a16783b7bad63752484460398ee801999`; `jump-attribution-2026-10-09.md` `8c159d2cd95c05f9ec21a51f4c2f0ff784e726ac` / `9babcadce6d712ba95b3c07f65fb46c2acda446e3fc592924d0de467d96675be`; `minecraft-wiki.md` `77ec7ddead477a01c0f9ebab550251ed4e714931` / `89558197fc2d2b98638b5af9017d419be0585ca7c2d86b059623c50b8d9219d6`; `wiki-page-fetch-log.md` `80083cc40ceea78601b400d626af9d799d4545ca` / `9ba554ea2bb4ff804009ecafc5df49309b52aa6126b9e237f0b804343c0c985f`; original review `f49f27842db111c99179a0fa771d981890cc233b` / `69428c4025cdce4def744b985ed6a3e2a2f6dee833e19e46321ffbccffe29f85`; r2 review `fbc9b18393aade8f7a257d252a661af2cd01f14e` / `a70c93cf256c1f1a423854362837460a7d95852bb630ab102c4b5a96b8db656a`.
- The accepted correction records that rendered Minecraft Wiki revision `oldid=3825931` attributes the displayed 1.9 / 15w45a jump-height change to the article. Raw wikitext/HTTP bytes remain unavailable, the exact Minecraft source cutover remains unknown, and other jump paths remain open. Wiki attribution does not validate or broaden the separately bounded source recurrence.
- The exact author and review report bytes were retained. No other Wiki branch edits, source-lane material, implementation/runtime claims, or production changes were integrated. No build, tests, runtime, or push was performed.

## 1.17.1–1.18.2 source discovery status reconciliation — 2026-10-09

- Pre-integration local `main`: `f79074af4baad1ba737680712e12fd20bb65a89a`.
- Exact current source-ledger checkpoint: commit `64d8bc740de0860a43ecc691db0efbdcaef7c19b`, `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/run.md`, blob `d69cac145095a77c237ad6e40e85d06df4b1a293`, raw SHA-256 `c9766e0fc15800d1322225e5cc40c71e991355412118626463859b603ce2ab16`.
- Historical audit event: commit `7872eb6457f051df3575f10299ed1aaf1c13eddb`, `FULL-PAIR-AUDIT-2026-10-08.md`, blob `d3623ec27f9ca1b7ed2c6f6f12ac259bdfa8b8d7`, raw SHA-256 `3ffe60b2e62af2ddd060a2081c6104dac80908c5b6859c9285afd11bc16993d3`; verdict **REQUEST CHANGES**. Its original bytes and verdict remain historical.
- Corrected audit erratum and accepted verdict: commit `e0e36eff36a92be82327bf2a8140f18141d7cc21`, the same report path, blob `87fc1e9344c26ef60bec02075a4f05b16956ac92`, raw SHA-256 `08099783947b13ff03516b70b0f9ccf51455ebf5a0fee2e7d790385cd66011fc`; verdict **ACCEPT — full-pair source coverage freeze**. It supersedes the prior verdict and accepts the frozen run object at blob `53bef7aae76bef026d5f5d19bc111635ddc6265d`, raw SHA-256 `70610a914b440ab651163f1ccd708000f4261da24727f7a4614eca473095b3f5`. Author response commit `6334207dcbd1f06a97c99d489de12df3d4e474e2` contains that exact frozen run blob.
- Status contract checked in `workflows/movement-discovery/README.md`: top-level run status reports source-discovery coverage; implementation coverage and runtime validation are separate statuses. The exact current ledger has 28 terminal rows (13 compared-no-difference, 15 findings), all seven required inventories complete, no unresolved source dependencies or gaps, and the accepted independent audit routes no missed source slice.
- Metadata correction: top-level `Run status` changes from `partial` to `complete`. The current dependency summary's stale “independent inventory audit remains pending” phrase changes to “independent full-pair inventory audit accepted.” The source freeze was already accepted; these edits reconcile current metadata with that accepted freeze.
- The resulting `run.md` is blob `d13120bf2499d9e9a71f94b33d19ecd2f25148de`, raw SHA-256 `faed6852e9f7c551dd6a5fd0eb363486165a3e55c22fbc4137f93b82a0ae5ac8`. Only those two status phrases changed. Historical partial handoff/checkpoint entries, all source evidence and finding bytes, implementation reconciliation (`pending; implementation was not inspected`), and runtime validation (`not performed`) remain unchanged.
- No new source interpretation, implementation inspection, build, test, decompilation, runtime, or push was performed.

## Full-pair audit binding correction — 2026-10-09

- Pre-correction local `main`: `ee38e8b1ed658e4b7ea2d128547c7571e3a5f7a2`.
- The original audit event is commit `7872eb6457f051df3575f10299ed1aaf1c13eddb`, report blob `d3623ec27f9ca1b7ed2c6f6f12ac259bdfa8b8d7`, raw SHA-256 `3ffe60b2e62af2ddd060a2081c6104dac80908c5b6859c9285afd11bc16993d3`, verdict **REQUEST CHANGES**. The corrected erratum is commit `e0e36eff36a92be82327bf2a8140f18141d7cc21`, report blob `87fc1e9344c26ef60bec02075a4f05b16956ac92`, raw SHA-256 `08099783947b13ff03516b70b0f9ccf51455ebf5a0fee2e7d790385cd66011fc`, verdict **ACCEPT — full-pair source coverage freeze**. The erratum supersedes the historical verdict while preserving the original event and bytes.
- Updated `run.md` now names both audit events distinctly and binds the accepted erratum to frozen run blob `53bef7aae76bef026d5f5d19bc111635ddc6265d`, raw SHA-256 `70610a914b440ab651163f1ccd708000f4261da24727f7a4614eca473095b3f5`. Its resulting blob is `1b9f86a10df83ec9092aa325950080b83cec4265`, raw SHA-256 `cb58a705c103f493b5267c4f17943c2ad58ea2adf37a383f88428994f8068e8c`.
- This corrects audit-binding metadata only. No source evidence or finding bytes changed; source discovery remains complete, implementation reconciliation pending, and runtime validation not performed.

## F-002 final no-code reconciliation — 2026-10-09

- Pre-integration branch base: `cb11257e593987c68be98a7dbda8bbea7d5e77fd`.
- Integrated exact final reconciliation from author commit `0edd1a92db59ff2ae326c15562c1a423be74e527` and bounded technical review `480baa90ab2364c49106d228ef54f122f5e3aede`.
- Reconciliation identity: `workflows/fix-implementation/reconciliations/F002-elytra-cosine-2026-10-09.md`, blob `af0903b06c1dfa7d53479621068c5d6560d2d3e8`, raw SHA-256 `65f4a1d7441784db7b0e87430f37dc102f06d752b89b2857e33b389a98fff751`. Review: `workflows/implementation-reviews/2026-10-09-F002-final-no-code-review.md`, blob `4acc102e1d3174851bd1e5681c38d8aeb896e067`, raw SHA-256 `34d583e5340c0dcf7ecb55fe8b2e85b1a3c0e900b2089263daa798a265e9d9f6`.
- ACCEPT is limited to the reviewed 1.17.1/1.18/1.18.1/1.18.2 float/double implementation boundary. It does not establish unreviewed-release fidelity or runtime parity; no code change was identified.
- No build, tests, client/TAS/server launch, or runtime validation was performed.

## F-3 arithmetic correction R2 — 2026-10-09

- Integrated exact corrected candidate from `b2b493a2521b036bca2fa590feeda3194c49bd4c`, its source-ledger binding from `bd244527013e8eb7a5ae912c4fe6ce42aba5dbcc`, and independent ACCEPT from `a76e28419f6b9767eab2e78147883c5459ed60c4`.
- Candidate: `workflows/source-campaign-2026-10-07/1.21.11--26.1.2/findings/F-3-nether-lava-shallow-overlap-r2-2026-10-09.md`, blob `73a244f66a6501b1d48f11fc4bcbde95d25c8518`, raw SHA-256 `69146a6130e72d7fffdf4bfa71954c41c62dc57aebfa0246f040e079c5f28907`. Review: `workflows/source-boundary-reviews/2026-10-09-lava-shallow-overlap-r2-review.md`, blob `10d56f3e76eca339a95f6ea3f47ec099decb3467`, raw SHA-256 `c589664e82974cc07b7010a9c4be9c42ef4e334221d1d73b11fe1e4261f7352e`.
- The current pair ledger binds the R2 correction to the bounded arithmetic acceptance. The original F-3 R1 REQUEST CHANGES event and candidate remain unchanged; the 1.21.11–26.1.2 pair remains partial, with no freeze or runtime claim.
- No build, tests, or runtime validation was performed.

## MCPK Y=256 conditional source mechanism — 2026-10-09

- Integrated exact bounded producer report from `681d1d54d69ec693f02e07e5a6b1841c401c4bb6` and independent review `2e47dad5165119de97b940e17eebf0bcad21b3c4`.
- Producer report: `workflows/wiki-audit-2026-10-07/mcpk-y256-producer-2026-10-09.md`, blob `fddcf603a83611d9d10a0f5f684e270433fcfd30`, raw SHA-256 `873d3934c9d902f2521913f358b4ca56b9f8d4d9b9acee9ad0a95354125b9a65`. Review: `workflows/wiki-audit-2026-10-07/mcpk-y256-producer-review-2026-10-09.md`, blob `835b09a7db975c4a4026ebadc1fdb15c2be651b4`, raw SHA-256 `b0221a0396b24bd4e43824d7ec494709b9e16195403d4f2f7bdb4616227838a4`.
- The exact reviewed conditional mechanism is also reconciled into `mcpk-checkpoint.md` and `mcpk-full-catalog-2026-10-08.md`. ACCEPT covers source-level stale positive water depth at the 1.13.2 Y=256 preflight and the bounded 1.16 reset/consumer contrast. It establishes no reported trajectory, universal jump result, MC-135831 cause, or first fixed release.
- No raw Wiki source, implementation, or runtime claim was introduced. No build, tests, or runtime validation was performed.

## Exact source-preparation records: 1.14–1.15.1 — 2026-10-09

- Imported only the pending preparation paths from exact records `69de5a7cfddde6fdb08b2fdde1d5faf7b4eb2e18` (1.14) and `cbb98738c2d9b03d451d61eb3cec8ea37d2e91d9` (1.15/1.15.1); no stale branch tree or unrelated file was merged.
- `preparations/1.14-exact-2026-10-09.md`: blob `77b0005c9bfb0580d517bf5bbc8de0830715e973`, raw SHA-256 `f8923e3573d6df066aa8c0872f925bd845591fc68a970abdd73e8feb873ca657`.
- `preparations/1.15-mojmap-2026-10-09.md`: blob `d014d328bfb8c8b9bca9ceb944cfac537e863575`, raw SHA-256 `9251bc1d826b5bba5416e50261bdda4ff3cff85b2d3424b1540d1dd5880cd8ea`; `preparations/1.15.1-mojmap-2026-10-09.md`: blob `af25baef749ac3ba02d74a8564c31855b6ba55aa`, raw SHA-256 `88d3a697e3920dfd2f42c6a9c5978258dcb857d8533640b49c5f115c13932ac7`.
- These records establish artifact readiness only; they add no movement interpretation or runtime result.

## Coordinator queue checkpoint — exact snapshot — 2026-10-09

- Imported only `workflows/orchestration-2026-10-09/queue.json` from exact commit `7f6ad5859649e7cb56e7e328a4620743acc8d424`, parent `12196ffe99bb9fe7d69a559f42eff7cd52c0d87a`. Blob `dbbe933c5ba1eba28482739e2350cb72d3234b81`, raw SHA-256 `e59f5caab487cb620bb6361718ccf5c73de76a99eeb95c55fdeebe2361d7ceaf`; byte-identical to the requested snapshot.
- Snapshot records 15 occupied slots (13 campaign workers, coordinator, unrelated worker), 13 verified active workers, and `newRuntimeValidations: 0`. Queue metrics are coordination metadata only, not runtime results. `integration.md` is preserved and appended; the queue JSON itself was not edited.

## MCPK Y=256 endpoint boundary follow-up — 2026-10-09

- Integrated the exact 1.14 memo from author commit `a3a3dda4cdbc0e2df361542d86c2a6f885eb4ecc` / final report merge `85c1236c8539f9e07b56e4716951ec5a4ffd3cfc`, the exact 1.15.2 memo from `de623cb55be97374f1d13f621e0b9a47005ec092`, and bounded independent ACCEPT report from `9da67abd41f2f1f53c886e5c3519ed44f3e2e2f7` (review content commit `cb307b752a767290b91915f42b570f52ba35a4f2`).
- 1.14 memo `mcpk-y256-1.14-boundary-2026-10-09.md`: blob `3675c55628e64c857ae20969b454aabea5c8ee98`, raw SHA-256 `d3c0071e9f65393cb4600279de510704a8763331cc8755b7c53ea7a7d9dd210f`. 1.15.2 memo `mcpk-y256-1.15.2-boundary-2026-10-09.md`: blob `3620dfdca035c9fb7024d3b7bdc79a06b9292913`, raw SHA-256 `706fd96e5d64e6c2ed7f670905681181dd3140ef35390fb757a264b20e5a79fb`. Review `mcpk-y256-boundaries-review-2026-10-09.md`: blob `7d6088496e9efbddc0b2cea77e951859463cab44`, raw SHA-256 `43683c0b80c2dffcfd3214aa89a4f1a60315a53317f5eb5caa0b96ba98844d19`.
- The checkpoint and catalog now bind these additional accepted endpoints. Review accepts conditional source mechanisms at the exact endpoints only; first affected/fixed release, universal behavior, and the MCPK-reported trajectory remain unestablished. No raw Wiki or runtime claim was added.

## 1.21.4–1.21.5 corrected D7 acceptance — 2026-10-09

- Integrated source correction from `930e4ed490ef41a98b0527183e9b3b4ad9e0d339`, original R1 REQUEST CHANGES review `759a5c441cf7b052a88d020f6f26f8f30832e581`, and independent corrected D7 ACCEPT `d7b932a2f57285a52fbf3c96a89d4b7173fe22ea`.
- Added only the corrected D7 evidence section and exact reviewer binding to `1.21.4--1.21.5/run.md`. Original `b8` bounded acceptance and R1 rejection remain historical; D9/F-09 bytes are unchanged and separately assigned. D8 stays closed only for its bounded producer path; D10 and D1–D6 remain open. Pair status remains partial.
- The acceptance is limited to D7's controlled-camera input transfer, caller/phase gates, vector helpers, and direct relative-movement consumer. It is not a pair audit or freeze. No implementation/wiki material, build, tests, or runtime were used.

## F-001 final no-code reconciliation — 2026-10-09

- Integrated final author reconciliation from `936bc7213916335962f638d53263280fbf061e29` and independent technical ACCEPT from `0f30150627fe97d3d9a6ba60f9040574e05d6c85`.
- Reconciliation `workflows/fix-implementation/reconciliations/F001-sprint-collision-2026-10-09.md`: blob `ad8f0c6c10555fd0c6db5b5e0320d2dbc7553318`, raw SHA-256 `0f54257decdafdf70b7cc0b73f0068ac271964bf53bc43aa7c3397a70b77cb4e`. Review `workflows/implementation-reviews/2026-10-09-F001-final-no-code-review.md`: blob `48e2b2ba360ce039cca71aa35e3b85e2bb2e40b6`, raw SHA-256 `c2aef7a7d3fc753250a727c2469e30147811bebb862929f607979ccb7268b2e5`.
- ACCEPT is limited to the independently source-reviewed 1.17.1/1.18 sampled boundary and matching current routing. Routing through V1_8 is not proof of earlier-release source behavior. No runtime parity was established; no code change was indicated for the reviewed boundary.
- No build, tests, or runtime validation was performed.

## F-005 r2 source witness — 2026-10-09

- Integrated exact r2 source memo from `96b6f7253491903c0da443d74102dcc451be1245`, original r1 independent REQUEST CHANGES from `6702eda57a1f378576fca06778e11648cc0e5c8c`, and r2 independent ACCEPT from `7dfe93834ec72bf224c5ffe4e25dc102126dee5f`.
- Memo `workflows/source-boundary-reviews/F005-boundary-evidence-r2-2026-10-09.md`: blob `012209d04cc41482729b0bf4f66cd9c23686bf8e`, raw SHA-256 `6411e8b8f4f27cbfc28de95bb41c9236cdb2c08d05e28480c5906d6078ed8129`. R1 review blob `65bc5ff0c0a7c222267f882f5ffc62036360f54b`, raw SHA-256 `a73dd9aaeb03a02faceb67474ca2d31be0dda2083cd14274bfed7b40b07048a0`; r2 review blob `d2fd6bb126012d7ae16c297e9dbb5c1fa23126ae`, raw SHA-256 `67ce567f9edc39bef7a9fef511b464ee0bf76abc7fe2a2dbb5ea1d525269e313`.
- R1 witness-dependency REQUEST CHANGES remains preserved as history; r2 ACCEPT closes that source witness gap only. The separately accepted 1.18.2 first-changed-release boundary remains accepted. This is not a trajectory/runtime result or an implementation recommendation. F-005 implementation reconciliation/code remains queued for its separate technical review and serial build.
- No build, tests, or runtime validation was performed.

## Exact source-preparation records: 1.13–1.13.1 — 2026-10-09

- Imported only the two exact preparation paths from the report-only tree at `75767b06d1b728e672db2fd1872165153ce768a0` (author preparation commit `c98e0a21175f4af49eb4a38f3ff563abef64b3e5`).
- `preparations/1.13-mojmap-2026-10-09.md`: blob `b56500a45f69b42fcf9bc0cbc56f65c117ecdbe3`, raw SHA-256 `77c19f02802841056ebea4ea0cc6fa7215c9e07166c9e591095c75d9b1898ef9`; `preparations/1.13.1-mojmap-2026-10-09.md`: blob `6f9d4452c2fb897ecbaa18753392984f56dbd9c3`, raw SHA-256 `81af978490edaca30aedf4e801404f0facfc75863275c8b0733198241a72ce21`.
- These are artifact-readiness records only; they add no movement interpretation or runtime claim.

## Accepted 1.21.11–26.1.2 fluid checkpoints — 2026-10-09

- Integrated the exact bounded source checkpoints `2f13f54bd011a67e10314fc674cb270f68612f1f` (three bubble-column resource edges) and `b85cb22afbdc1ffdc7406cec74c1c46ebfdd5a0b` (mounted-player route and fluid-height consumer trace), with independent review `e2bfaf654561524682cbb4f4906f127f43da2374`.
- Review report: `workflows/source-boundary-reviews/2026-10-09-12111-2612-fluid-checkpoints-review.md`, blob `569a7c183c36d46c47b2ecc1851eaf1a3871715d`, raw SHA-256 `f9462839ef360baaac64f024eb57d56d567f39e5c0b703dca302e143a796f6fe`. Current pair-ledger blob `992ab5ea09f2387334b579c4e2a842757df65f45`, raw SHA-256 `dc97aa9af372be515443f685c619343152f62ee1ce9ab91ecf75c6c44753bf9d`.
- D2 accepts only the exact vanilla-default bubble resources and their bounded Player response edges. The mounted Player route is source-reachable but has no exact fluid-cell/load witness showing a response difference. Mounted applicability, eye-state writer/order, and `getFluidJumpThreshold()`/eye inputs remain open. Pair remains active/partial; no pair freeze or runtime result is inferred.
- No build, tests, or runtime validation was performed.

## Pane neighbor providers — bounded independent acceptance — 2026-10-09

- Integrated exact provider report from author commit `aa9e10f7792aef6215bab1f97ccdca61b64bbd86`, unchanged at final author tip `9e1841d0874217366149afa80dd8b25cbe2f4313`, and independent bounded ACCEPT from `6d80618cfac6351d09d18aab09c00fe85f2a155c`.
- Candidate `pane-neighbor-providers-2026-10-09.md`: blob `4af937333cfe189c1205c2bf4871874325e74a9a`, raw SHA-256 `da9196859a9456fa036ec9d446eaf4ff56314c03816da3c23cf6bc8de8ef361a`. Review `reviews/independent-review-pane-neighbor-providers-2026-10-09.md`: blob `6976b4c9658a94ef9904a595e28dd205a18034f1`, raw SHA-256 `3cb7de364a95e09c884b05d554a32b534145255a100c8f6b61be87eaf27b1972`.
- The checkpoint and Wiki row now bind the accepted three-class slice: leaves/leaves2 remain unchanged; slime changes the predicate and yields one reachable east-only pane mask. The result does not close the provider census or all reachable masks. Feather-derived source/JAR equivalence remains open.
- No source-lane or runtime claim was added; no build, tests, or runtime validation was performed.

## Coordinator scheduling and readiness snapshots — 2026-10-09

- Imported the exact scheduling/slot checkpoint from `663dce3c52d703ffb038ea4badabe27717ff4d21`, path `workflows/orchestration-2026-10-09/queue.json`, blob `978de7dc3b817d88b4d7bd53a380ef930b3e2475`, raw SHA-256 `643b928da1b638d82de9e0c674defffb2d82c065a199cff89b76349162d32df3`. The JSON remains byte-exact. It is a dated coordination snapshot; its `mainVerified` value records its capture baseline, while the current local main head is reported separately. Queue metrics are not runtime outcomes.
- Imported the exact five-profile readiness report from `2184473bb524d324d933b87207c6f6f051225023`: `preparations/1.19.2-to-1.20.2-readiness-2026-10-09.md`, blob `25b09d7170878fa404eec64c163b3b53fcc1ed19`, raw SHA-256 `0a62d0d5e4e47db0c192468c52085db3506fe695daf7f4c8b9a407c45b1153fd`; and updated summary `preparations/readiness-2026-10-09.md`, blob `1a410838d8aae96c7d71492b645a6e470291710a`, raw SHA-256 `b5961f72f534d0efaffee4731ab3c835ee46a5364fe191d845b10e087b1695a0`.
- All five listed existing profiles have exact marker, manifest, source-file, artifact, and readability checks recorded. No rebuild or movement interpretation was performed.

## D9 / F-09 original-claim review event — 2026-10-09

- Integrated the exact independent review from `dc2b29c81c1fd57941d8ed4a27d1583b1b039b4e`: `workflows/source-boundary-reviews/2026-10-09-f09-controlled-camera-input-damping-review.md`, blob `876aec376848f6b1502ae68e664822db7ebd6500`, raw SHA-256 `087b3582684c7b80892eac6ea6e9a5c4a17e9309da3683d32171326e3322f6f4`.
- Verdict: REQUEST CHANGES for the immutable original F-09 claim that B omits `0.98F`. The review supports only a narrower one-ULP float-order difference under its specific controlled-camera/item-use/sneaking-speed conditions. The pair ledger records that distinction and preserves the open status.
- The original finding remains byte-identical (blob `8a9c64a35705195d39d300f7fa0ea4f25cf049aa`, raw SHA-256 `3a1680c5e724a935cbc576705088f03e3a9d4c6a8e29e5375d4ddb8e93b1e328`). No corrected finding was created or accepted. D9 remains open for a source-owner revision and separate review; the pair remains partial.

## F-012 exact release-boundary source review — 2026-10-09

- Integrated only the source-boundary memo from author commit `1c4317af04f70a596642369a402ce38ccb5492da` and independent ACCEPT from `ee586d98cbd02df932f6f076daa56a9d2176557b`.
- Memo `workflows/source-boundary-reviews/F012-sprint-air-control-boundary-evidence-2026-10-09.md`: blob `ebd4cb218453a9b2ce7afbe15932d2668dc6b742`, raw SHA-256 `477812d4bd08dcb3841b902b311899ee51c2f72d9ad76e323cb4a206dee01a06`. Review `workflows/source-boundary-reviews/2026-10-09-F012-independent-boundary-review.md`: blob `7a8315f0e5f253ef8c02a7e373524b3f8859080b`, raw SHA-256 `7493c46aaf5e55125ff94c94b6ca6322bd150e41526a81ff78e3ef7187b37e9e`.
- ACCEPT is limited to the coefficient and next eligible ordinary-airborne consumer across the four sampled releases; the expression changes from double-literal addition to float compound addition at 1.18.2. It does not establish unsampled cutovers, full parity, or runtime behavior. The pair run/freeze is unchanged.
- The F-012 implementation reconciliation was not imported: its technical review/code gate remains pending.

## F3 R2 implementation technical review — REQUEST CHANGES — 2026-10-09

- Integrated independent technical review from review commit `b1da5dfb90bf39ccd26a76dd35b94cefab392ae6`, final report-only merge `8713e3447ba3d412f3ed56c551eb1129c8271bc7`.
- Report `workflows/implementation-reviews/2026-10-09-F3-r2-technical-review.md`: blob `3672e070dd382bd1ab0d1ac1ab37c874d6d283b1`, raw SHA-256 `e0872eeab806b9ef1db20813b274c32a0c9e6778e62a8d99f94b647cdfd030da`.
- REQUEST CHANGES: the reviewed implementation activates the LAVA cutoff bypass for mounted Players, while the accepted source witness covers only a standing, unmounted Player and leaves mounted fluid boxes unresolved. The review otherwise verifies the 26.2 source identities, target call order, cutoff math, registration and native fallback. It proposes retaining the native cutoff for mounted Players unless that applicability is separately accepted.
- The immutable source finding is unchanged. No F3 production code or final implementation reconciliation was integrated, and no build or tests were run. The pair remains partial; code stays gated on correction and independent ACCEPT, then the disabled-test build.

## Coordinator queue checkpoint — 2026-10-09

- Imported only `workflows/orchestration-2026-10-09/queue.json` from coordinator commit `b14272524804e913c6a5550e94e80029eb05fba7`. Blob `a429b689871f3c820acba6444e7c870f47d927ad`, raw SHA-256 `21C99E3D3E23AAA87E03042C8A5BD1E993A091F8B7E4AA361DE5D1562A56219D`. Its `mainVerified` is the checkpoint value `cbeeeacbf1303a559d1483969b2b821e1bbe0ab6`; retain it as capture metadata, not the current main head or a live worker count. The checkpoint has a soft target of 15, no hard cap or unrelated-slot reservation, and discretionary worker subagent use.
- Imported the exact status snapshot JSON and Markdown from `23c098bfa4e025fd7c1b80e9e4efe7c1b463b47b`, bound to indexed main `3467cc398a89bb86bfafa6eedb315e0a29e040de`. JSON blob `9774d7314078291fc80e4ad623750ce1d071cce3`, raw SHA-256 `05AFE84FC104BB4FDBA323C3C1B081EA150AF9CC01F8444500B8A3526276543A`; Markdown blob `41b61492a10430f6c7a4af7d89b27c8051dde4a7`, raw SHA-256 `86BB52CA780D616B5A059E4572A0EB2BF024FDD2E57FA765EC2C45C07C92A232`.
- The status audit records 26 interval statuses, with only 1/26 accepted full-pair source freezes and coverage audits. It records F005's successful disabled-Test build, F3 REQUEST CHANGES, F012's bounded source-boundary ACCEPT, and runtime as unverified. The snapshot's cumulative ledger counts and historical task states are not campaign-wide implementation totals or current worker counts. `integration.md` was preserved and extended with this import record; no unrelated branch code was integrated.

## F-3 mounted-scope correction integration and build — 2026-10-09

- The corrected code `b2eac92a9adf1d773aafc2d7013ea41fb977319f` was independently accepted at `f89440c372c9f795e75d7c6e0e1bfef9253001a3`; the merged F-3/F-005 tree was independently accepted at `f467c6df7101f26b542d941bd2fc05536520fc69`. Original F-3 REQUEST CHANGES and code candidate remain preserved in history.
- Integrated on local `main` at `242d67da6c9dbc3b6fa352a458839a987b70f4cc`; test-disabled build passed on tree `0bf2f846bae37d4811fa8205ab9c4bab870a277a` with 18 actionable tasks (3 executed, 15 up-to-date). JAR SHA-256 `5D66B610513BE5FB92F47C9C2410631413392692D61EB8102067C5CF88A2A98C`; runtime unverified. Full review and build bindings are recorded in `workflows/fix-implementation/runs/F3-shallow-lava-integration-2026-10-09.md`.

## F-005 accepted implementation integration and build — 2026-10-09

- Follow-on to the earlier source-witness entry: code `b4f2cc3742cd0b93fcf9301154b5bba85351bb86`, reconciliation `f73d176e98fdf1f56c5207046266a168c1834d73`, and independent technical ACCEPT at `3e0992e6d764360c77dccb20e38c4ff8ade9fa1b` were integrated on local `main` at `7cb12f7ec44cd39a7d4fea6056f03f1180775853`.
- The accepted code tree built successfully with every Gradle `Test` task disabled plus `-x test`: 18 actionable tasks (4 executed, 14 up-to-date). JAR SHA-256 is `837621839AA3F567F6A08C8E6252C08A95BC18FA568F272C318AF67FC8E16C01` (269447 bytes). The exact command, init-script identity, and artifact path are recorded in `workflows/fix-implementation/runs/F005-fall-reset-integration-2026-10-09.md` (blob `3e4372040b93051eefe8ca90a86d6e4b0035f36e`, raw SHA-256 `CD9AC52A436D26E34B2670BCA1C26E3C8A696806057D7CEF7271520CF7D9E940`). Runtime remains unverified.

## F-012 accepted implementation integration and build — 2026-10-09

- Follow-on to the earlier source-boundary entry: integrated code `e386e31f7b55d584461469f20752eefd5bfde955`, proof `cc3ee3c8f3239e894923a1f23810c3a9d3130d31`, and portable independent technical ACCEPT at `7888435906df30af3dd9d050d91f5add60b6b45e`. The exact report blob is `8537a884a0aa12637f85111e0a818a6f362a9dd9`, raw SHA-256 `f81dab23afe820682499139c9775880237cad761a445ab42de728211d7c38e64`.
- Local `main` integration commit `9481608463947248ba5004b1dabf999c5e3e59d6` combines the F-012 air-speed hooks with the already integrated F-005 V1_18 fall-distance reset. No registration was dropped. Source fidelity remains bounded to 1.17.1, 1.18, 1.18.1 and 1.18.2; older profiles and runtime parity remain unproven.
- The integrated code tree built successfully with every Gradle `Test` task disabled and `-x test`: 18 actionable tasks (3 executed, 15 up-to-date). JAR SHA-256 `86608F4BF26A86767BF972307D52E3FA68050CA2F71D5FC7898F07B43B59DE45` (272031 bytes). Exact build bindings are recorded in `workflows/fix-implementation/runs/F012-sprint-air-control-integration-2026-10-09.md`.

## Orchestration session policy update — 2026-10-09

- Integrated documentation commit `0b62a628c99d067ff569c0d6ffc9d8790e1988a3` and current-main merge `cbeeeacbf1303a559d1483969b2b821e1bbe0ab6`. The updated workflow targets about 15 active workers as a soft occupancy target, excludes the coordinator from that count, permits worker autonomy on subagents without prompting, uses fresh workers for unrelated assignments, and reserves integration, Git-index, and build control to one owner on local `main`.
- `workflows/orchestration/session-policy-2026-10-09.md` records the former hard cap and compatible-assignment reuse rule as superseded. The dated queue snapshot was not rewritten; its values remain historical capture metadata. Updated README blob `516ac9ae6680c5934eef6adb549d795ba629f54f`, raw SHA-256 `14C0D4E38CD50F140023696676B99E4C035902D496796BD417C96ED4CC82DCD2`; policy-log blob `5249e7e56f8bc440e63c88edd5595141c2506293`, raw SHA-256 `5A9516B7D23DA7B1B638B22B322E215AD9ED474326D97DD3AC5634311DC010AE`.

## Report-only source and provenance checkpoints — 2026-10-09

- Imported the Y=256 introduction/protection report updates from MCPK review merge `30e3caac80bb776403f034ee8884666865c696d2`. They only update branch-integration history; the bounded source verdicts and exclusions are unchanged. Introduction report blob `d179d852809c8e5bdf52c0e38605ff601cf50a60`, raw SHA-256 `BE351AE14CDA963943760D4E41F14C4B735A8E7E7A339AB0F5A17CF7A6923ABF`; protection report blob `9c83e280416cf2ba8094493ebd8bd077c2c595ec`, raw SHA-256 `858895DD77FFF92683EFA6E4CCF977581258A535F9ADD7CB476F31A8BB83A516`.
- Imported F-4's independent source ACCEPT from `964ae707a85126a692de3bf9d41a6db5574867d5`; it accepts only the exact mounted-player lava-box snapshot and does not freeze the pair, establish a first changed release, approve implementation, or validate runtime. Review blob `b43b9b0d5af21cc8e7d7ae108e2d256db8e52d3e`, raw SHA-256 `0703E61E5FE417BF48C2BB8EA9AB4D287417FBA7F833950B4E9DB45FA5E299CA`. The paired run update from `97d6dd2d2978a357340588c81af880bb0b6e4bbc` records the bounded F-4 event and S4.7 writer/consumer trace while the 1.21.11–26.1.2 pair remains partial: 56 slices, 45 in-progress, 10 compared-no-difference, 1 not-applicable, inventories and blind freeze still open. Run blob `9ab46cd93ec9a41cb946163b2b60fe10a2240b3b`, raw SHA-256 `0DCB8F46CD511A7F53CDC78928A72F1F4C6A3C4E855014A2A3F0874496434B55`.
- Imported the D11–D13 independent source review from `5e4c9acaeca8f438b5c385e60993d948035cbc3b` and its bounded D14–D16 update from `9cd04bc12214cbe2f3af0af15da7266ddf0c0a77`. D11–D14 and D16 are accepted for their stated routes; D15 remains explicitly open because transformed option tag types are unproven. D8 and the pair remain partial, with no full-pair freeze. Final review blob `e36de9a2d7228760ae4b9a598be5d573a5940a4b`, raw SHA-256 `FDAD8E5026545A349AB781F02F894F4BE778B70368A7F30747A7651A996A5A8C`.
- Imported metadata-only provenance bindings for F-007 (`1e5add13b0aff167f85ccaf340f463a61d15029f`, blob `1c67b53000576b8ae258c43447bcea0a78b0f04f`, raw SHA-256 `388CC689C53537C3CAD6757D2887B90B1F2CC489318F469D01585F1652E33986`) and F-008 (`6fd3bcce82235228dd892f28a4c07bfad3b0c08b`, blob `0f7aa978efce4606f7832fd4b60f233aaf0a08fb`, raw SHA-256 `0CCAC06CE80D72C0664D257488C952CCB021AA39BE5177CB2812A5E52D690824`). These verify the exact existing ACCEPT records and source identities; they are not new movement-source verdicts. F-008 remains held for the separate portable implementation review.
- Imported the 1.13.2 provenance-recovery record from `9484e88f9f2cbbc83456d91911e4610f6cc697e2`: the original ready publication is unchanged, the mapped JAR identity remains unresolved, and the available rerun candidate's equivalence remains unverified. Report blob `c8679874fbb9b759e3460f606509e2dc4708978f`, raw SHA-256 `269D3C240AF5114526364018C7934D64EA61F44CEB5DD724E3B64CF4BF43BDD8`.

## Additional source-run and review checkpoints — 2026-10-09

- Imported the 1.19.2–1.19.3 pair run update from `1dad000b6a8662a9ebc589d450d44728f59732be`. It records a source comparison of the server correction-acknowledgment path; the run remains active, with no blind freeze or independent finding verdict implied. Run blob `8059ff7f67758ac390bce567504c09890ccb1e58`, raw SHA-256 `7AFA9E62778305514C054A02B190DF1B788847B5A4981F1709F7129BF970F554`.
- Imported the F-009 independent REQUEST CHANGES report from `f539b65862249af400c4ff15553323f864ddd3cf`. The vanilla `/teleport` path does not reach the claimed out-of-range coordinate packet; preserve the candidate and rejection, and do not mark it accepted. Report blob `5431e100bd571819b6d39d7febacafae41073081`, raw SHA-256 `28DBDB5150D79E7708F8122D1C2C4B50084D7F97757ADCEB5DE63BD1A7801D05`.
- Imported only the new queue snapshot from coordinator commit `3b0a3fbfcf75c07a1d88543a66e10859f4fbd68b`. It captures first-event roster scheduling at `2026-10-09T18:21:49.536Z`, with 14 active and 32 known workers and a soft target of 15. This is dated coordination metadata; it is not a hard cap, live status, or a replacement for the integration/build record above. Queue blob `9544b7e9321502714c8735216222f005fe28ce5d`, raw SHA-256 `5957ADCF6A99CA36BCEBA8F87E27F4051AE1C3E2FF6CADC6ACC5EAB9E5E8BE6E`.
- Imported the full-roster event-first scheduling edits from `80030483c65f16a25f281074317614a50188c942` after checking the merged workflow branch `81d5784802a0bbea087d255b8cd65deccbf384ac`; the startup wording clarification from `95bdb9faf5622d029231137c35c3a157ba736458` is included as well. README blob `a60eb28f54738c9798332cdcadf730db2585e874`, raw SHA-256 `83DD44C854329E951BB3FEAE88326EE69A2385EC2CA913B2D3D9BB965FC702A9`; session-policy blob `84cf638cf1c3cddd3813c401feaf4ea76e8c0049`, raw SHA-256 `6E1C372957473E4E247E7C83233084DF756C52B8EDF663EA9F60A034406DACFA`. This supplements the earlier soft-occupancy policy and leaves its independent worker, isolation and integration ownership rules intact.

## Pause checkpoint — 2026-10-09

- Local branch: `main`; HEAD `cefcf44347aec30782ddfe3d873d8b22bbe7fc07`. Working tree was clean. No build or other long-running process remained active.
- F012 is integrated and its disabled-Test build passed; details and artifact hash are recorded above. The latest report-only source/queue/workflow batch is committed at this HEAD.
- Resume by importing the MCPK 1.13.2 water-current R2 memo, its binding and independent REQUEST CHANGES report from commits `5ede13dafa339e9c6c482c63ff711b19ac6e74e9` / `cc8d9585737e4a4d0e5f1af3a1837c0b24bd8544` as exact documentation paths, preserving the rejection. Then import the portable F008 technical ACCEPT report from `fix/review-f008-technical-binding-2026-10-09` (`40c12762ab3c9893d863b6abd65681db8cdce48a`); inspect its bound candidate and merged tree against current `main` before any F008 code merge/build. No F008 code integration has started.

## F-008 pre-merge checkpoint — 2026-10-09

- Current local `main` starts this batch at `309b330d546da8e0e25fa86158e993454e58f53f`, clean before this checkpoint. Candidate `2aede8384f0a9ea4ea8518b7ec8dcfedfcc12732` is bound by portable technical ACCEPT commit `40c12762ab3c9893d863b6abd65681db8cdce48a`, report blob `6142b28ac9d73c1448348461fad763291ea2bf0a`, raw SHA-256 `c31723cba9bcb819c2d865d21e45bb7091210db61500ca8c94721680e5dfff3b`.
- The accepted reviewed code tree is merge commit `a198e74cb0cfe6027d24ab7294bff11c1fe87d0c`, tree `9d64c11967fedd2c35f0e684490ed7e61fd53f01`, merging candidate branch `534c0c7098ce441ed8d07b91312ce18744c34b21` with then-current main `ee7ce616ae00aea11b335c80ece87f847d3cb3b5`. All seven implementation blobs named in the report were verified at that tree. The source diff from `ee7ce` to `a198` is exactly the ten F-008 implementation files; current `main` since `ee7ce` has documentation-only commits.
- No F-008 code has been merged yet. Next action: merge the review branch tip `40c12762...` into local `main`, inspect any catalog/mixin conflict semantically, record the integration tree, then run the authorized build with every Gradle `Test` task disabled and `-x test`.

## F-008 accepted implementation integration — 2026-10-09

- Merged portable ACCEPT branch tip `40c12762ab3c9893d863b6abd65681db8cdce48a` on local `main` at `e56156e6a97736090379e5cf87d2c560b29ae848`, tree `2077fc2c1ff9528ea8a21fc6891438366c719e4d`. Its reviewed code tree `a198e74cb0cfe6027d24ab7294bff11c1fe87d0c` is the exact source tree at this merge; no source-file differences or semantic corrections were needed.
- All F005/F012/F3 catalog entries remain present alongside F008's V1_15_2, V1_16 and V1_17_1 query providers. The static review's unverified pre-1.15 and 1.17.0 profile limits remain explicit; the source boundary is still bounded and runtime parity remains unverified. Build was pending at this checkpoint; see `workflows/fix-implementation/runs/F008-border-edge-integration-2026-10-09.md`.

## F-008 test-disabled build — 2026-10-09

- Build passed on input commit `0d65efb7b6c071fb0a01c86f7b5670ea65057d25`, tree `8189427cb909dfd74835f5eb678c5fad35a04585`, which contains no production-code change after the reviewed integration tree. All Gradle `Test` tasks were disabled and `-x test` was supplied: 18 actionable tasks (3 executed, 15 up-to-date).
- JAR `build/libs/LegacyParkourCompat+26.2-1.0.0.jar`: 277869 bytes, SHA-256 `B57254F81E71E23F07633B97A78E9BA9C98A463EDA664025F5FF1081F008CFA7`. Runtime remains unverified. Full candidate/review/integration/build bindings are in `workflows/fix-implementation/runs/F008-border-edge-integration-2026-10-09.md`.

## MCPK 1.13.2 water-current memo R2 — REQUEST CHANGES — 2026-10-09

- Imported the original memo from `aac1ea3010da3de7956130b2a008e3f8af755708` (blob `4a347380378ea1acebce15f56c3c9ab8f7f22384`, raw SHA-256 `9138b0314678d5c116b90a23006777e62397139f0b9c0bb01487c663f6743e4d`) and its prior review from `9bc84d4ea8f33e7490ee09ba5e6d3d5d368481de` (blob `e09244902f42f53d607feb8fae676936a9788303`, raw SHA-256 `21cd9b516fa57d1b17809afdb880de236f66e772a5f8510b9c173ab2bf4311dd`). The prior review marked revision required and was not eligible as a clean independent review because of cross-lane exposure; both records remain preserved.
- Imported the corrected R2 source memo and its pre-review identity binding from review base `5ede13dafa339e9c6c482c63ff711b19ac6e74e9`: memo blob `ff8bbbf238922b60f3340dbf5c1643a4070a9811`, raw SHA-256 `0160291faba21a1863871ccafe525af98bf46296ae7403e3ea4c4e67c182cce`; binding blob `af0b5ebdbf1e97320f4023ab67a7e01ec1d6d85e`, raw SHA-256 `0a8c1c4308fb137476dd51cbf41199896c82e156d7992599e56a52628ffda4c5`.
- Imported the clean independent R2 review from `cc8d9585737e4a4d0e5f1af3a1837c0b24bd8544`: REQUEST CHANGES, report blob `2c5792a30a091bdcba10efb2fb06e6ca60c4ff91`, raw SHA-256 `444b178424c85ba26fde0fb5c92c63f8e500cb03d92cb5c8ae0e6914f05c9f77`. It requests one qualification: `LivingEntity.mobTick()` can zero components with absolute value below `0.003` between the water-current producer and travel. The binding's earlier “pending review” status is historical; the later exact review is the operative R2 disposition.
- R2 remains unaccepted/open until the memo qualifies that cutoff and receives a fresh independent review. The 1.12.2–1.13.2 pair and MCPK Water and Lava row remain open. The expected original mapped-JAR bytes remain unrecovered; no bytecode or runtime claim is made. No build or tests were run for this documentation-only import.

## Coordinator restart history and newer roster snapshot — 2026-10-09

- Preserved the exact historical restart bundle from `8a0649da18d431746204175f6eb6a42e01a9e9e6`: `RESUME-AFTER-REBOOT-2026-10-09.md` blob `3135e79ad57b9d05ecea3e5ddae0770599762986`, `restart-state-final.json` blob `bff879fc5cc53ee7a9ec3d2384f7173ab018d70a`, `restart-state.json` blob `51206dcc952cc222fbea6541e341bde387d64aa9`, and `worktrees-before-reboot.txt` blob `e641e70c37b881fa76ef7cc9d9a3683905480916`. These are historical pause/worktree records; no worktree was restored, moved, or cleaned.
- Imported only the newer `queue.json` from coordinator commit `4f5f9907fa56c772f9972b9318f1b11a8cb3d49d`, blob `39c7ffa83d8c24e166a1907e071a9ed76b3befcf`, raw SHA-256 `73BFD8CD1EE5297DDD238CB39DA09C15F3CA7010FF27DE281A25CCBB81F8725B`. Its capture is `2026-10-09T18:46:07.666Z`: 15 active, 32 known workers, soft target 15 and no hard cap. It supersedes the restart bundle's paused roster state as a dated checkpoint, not the exact current live roster.

## Pane-provider source review slices 5–8 — bounded ACCEPT — 2026-10-09

- Imported only the four independent reports from review commit `44b2cb295dfa712c3b1d441e175dbe714b1550ed`: slice 5 (IDs 178–180), slice 6 (181–183), slice 7 (184–186), and slice 8 (187–189). Each accepts the stated registration predicates and one-east-neighbor pane mask only; the provider census and overall pair remain open, and Feather-derived JAR equivalence is not established.
- Exact report blobs/raw SHA-256: fifth `80740fcafb5a0334d4ca668e28fc08d3471b2acb` / `12D770C4BA9382A345EB94870FD5FBEA8AEC5FB6FB590C935B346ECE0A5D15F0`; sixth `1658c1a70b02f259fef907fa66debb0784199a72` / `EAA87B278EC577E86DFFFC32AF71467540BFBD5A6699CDA79FB4AE6103B3D4B5`; seventh `6a785712b570e6e38f24b4bba73d9672180f72ae` / `6D5F18A99D58C91753BC269482BE7DBE2849D11BBA31B6143FB13699DA2D1AE9`; eighth `e507ceeab2de770f6a8ead1f1b4b5d87893508bd` / `8C62C432CD775D3A038CE85242132AC314A660626DE76B2B44F34F7F0B526862`.

## F-009 narrowed source ACCEPT and F-21 invalidation history — 2026-10-09

- **F-009:** imported revised finding `0f17dc31e6619d38752c862b9ec3a30c13b79f48`, blob `fd6662d4d3eebe9671e9440cf67c27eb94d6bece`, raw SHA-256 `26fca3cbdc84084458484c7cc305f684dc94cc43ccc4d77b0609a7fc296926e3`; accepted-ledger run checkpoint `03de5e41f82b9d9c7705e8ee19267fd27b59ba6b`, blob `f55368b8085537ded2809813fd825856d31c833b`; and independent ACCEPT report `9ed69387b21f384ab800fec335e6a59b422ee027`, blob `940acdd0694017a57d549959f56e07c663a9880b`, raw SHA-256 `0d7c2018c343fb5514a83ccd8ee7312c85f261cb7129559bb527a50d7fc1285c`. Acceptance is only for the conditional client response to finite out-of-range absolute X/Z coordinates supplied by an external/custom server. The original vanilla `/teleport` reachability claim remains rejected at `f539b65862249af400c4ff15553323f864ddd3cf`; the earlier rejection applies to its original blob, not the revised finding. The pair remains partial; no runtime or full-pair claim is implied.
- **F-21:** preserved the original candidate from `a6a244d2e46b57c512b8d4c1508e955b667397b9`, blob `7e62e675d1870cdb4cff352f8f5182d683a1d16b`, raw SHA-256 `5c9eb375c6a88c45979b03863b2287581844435727d0066ebb9bd84aa7308a34`; source-owner invalidation in pair run commit `c80f0292773bedf3b3c687bf24edcc6cac5f3c84`, blob `d878e3d7e0bc5f60b6c511286b208c644e4ecd7b`; and independent REQUEST CHANGES review `c58c407041ad2e03235391a2b4cb7b56f040a543`, blob `dff3ee11cbf93a61c4ddeba12c7c804026d770d6`, raw SHA-256 `03aa3e8cefa4888a50ddffdb791352a29735b0481c1a4d4db1cc6330d05797e4`. A's tick-tail applied-scale poll refreshes dimensions before the next movement tick for the submitted command path, so the candidate does not establish the claimed movement consequence. It remains invalidated/not accepted; unrelated immediate refresh paths and the pair remain open. No implementation or build is authorized by this source review.

## Closure-first pause/resume workflow protocol — 2026-10-09

- Imported the finalized workflow files from branch merge `5420f2b8917e9834848e7609f73d0d9c4f040650`, which includes protocol commit `01aee46bf9178f96f957da86b03c5f7f048304a1` and current main at `038e3072977ffa7e9c31a8bdc5d53643f811d381`. README blob `ef1a12b800e82878f95ec80af4cbc1676ee324d5`, raw SHA-256 `A2B63403A3745BAC97A17D1680663D8025481B328297FD91C7A22A719991306B`; session-policy blob `8c622eb4536262693286e80e2c29f4d3a0ac6f74`, raw SHA-256 `93D262338DF707C63892426431542C5925CB50720A411A3B0BDDFCE09E49A11F`. The protocol puts ready preparation/reviews/revisions and accepted-patch integration ahead of new discovery, preserves completed dispositions, and resumes paused assignments without duplication.
