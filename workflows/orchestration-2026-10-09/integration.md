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
