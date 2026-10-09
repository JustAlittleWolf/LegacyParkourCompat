# Independent review: Jumping-page attribution (2026-10-09)

## Scope and verdict

Independent review of `fix/wiki-attribution-2026-10-09` at commit `97a8f2b374ea33980b2da5f4f4cdc0ebae5eea74` (parent `e7f89348a619cc9feb2d47aab7a87fbefbe7649d`). The review checks the immutable rendered Minecraft Wiki revision and the commit's new retrieval record and edits to `wiki-page-fetch-log.md`, `minecraft-wiki.md`, and `CHECKPOINT.md`, against the parent and the prior independent review.

**Verdict: REQUEST CHANGES.** The new evidence resolves the previously rejected direct Jumping-page attribution claim for this exact rendered revision. However, three unchanged summary passages still call the attribution unverified or search-result-only, contradicting the new records. Reconcile those passages before accepting the commit as a consistent checkpoint.

## Snapshot bindings

| Candidate file at `97a8f2b374ea33980b2da5f4f4cdc0ebae5eea74` | Git blob | Bytes | Raw SHA-256 |
|---|---|---:|---|
| `workflows/wiki-audit-2026-10-07/jump-attribution-2026-10-09.md` | `8c159d2cd95c05f9ec21a51f4c2f0ff784e726ac` | 2,015 | `9babcadce6d712ba95b3c07f65fb46c2acda446e3fc592924d0de467d96675be` |
| `workflows/wiki-audit-2026-10-07/wiki-page-fetch-log.md` | `80083cc40ceea78601b400d626af9d799d4545ca` | 4,879 | `9ba554ea2bb4ff804009ecafc5df49309b52aa6126b9e237f0b804343c0c985f` |
| `workflows/wiki-audit-2026-10-07/minecraft-wiki.md` | `15e227e566d13061d74c00a7c8c0817740da07d1` | 46,980 | `60b80500fcd7afb17f7f4c55e2131e29d73b7bd31742f01465ebf952e55d0372` |
| `workflows/wiki-audit-2026-10-07/CHECKPOINT.md` | `2ad69a0e362f8fd1b39158581816fedfcc48b6f3` | 20,016 | `2ae1ee41f3998582b7aa1aded7a7caa964bfaa44cfc79915acd58223f563c87f` |

The commit changes only those four Wiki-lane files. The prior review report remains unchanged and is historical evidence of the earlier rejection, not a statement about this new retrieval.

## Retrieval verification

The in-app browser opened [Jumping, revision 3825931](https://minecraft.wiki/w/Jumping?oldid=3825931). Its revision banner showed “Revision as of 08:17, 7 October 2026” by `~2026-WikiMesaDragon36696`. The rendered Java Edition history row reads: “1.9 / 15w45a / Player's jump height is increased from 1.24919 blocks to 1.2522 blocks.” The page's Jump height section separately displays the current-in-that-revision normal value `1.2522` blocks.

The page history identifies the same entry as 08:17, 7 October 2026 and reports `9,027 bytes`; the history radio control ID is `mw-diff-3825931`, independently binding that row to revision 3825931. This verifies the page revision identity, displayed history claim, and recorded byte count.

The raw route was attempted in the browser and returned `net::ERR_BLOCKED_BY_CLIENT`. No raw wikitext bytes or archived HTTP response were obtained. The owner correctly describes this as a rendered-page capture and does not claim raw archival. The web reader also could not retrieve the immutable page; the browser result is the direct retrieval evidence.

## Claim decisions

### Direct article attribution — ACCEPT, exact revision only

The new retrieval closes the prior review request about whether the Jumping article itself contains the 1.9 / 15w45a row. The immutable rendered revision visibly contains the exact rounded values cited in the record. The fetch-log change accurately distinguishes the 2026-10-08 failed access from the successful 2026-10-09 browser retrieval, and the new record accurately retains the raw-route limitation.

The attribution is Wiki evidence only. It does not prove exact Minecraft source behavior, establish the exact release/source cutover, validate the source-order recurrence, or make `1.2522` a universal jump height. The prior review's bounded source recurrence acceptance remains separate and unchanged.

### Checkpoint/catalog consistency — REQUEST CHANGES

Three passages remain stale relative to the new retrieval:

- `CHECKPOINT.md`, “Newly published, awaiting Wiki-lane review,” still says the r2 record's Jumping-page attribution is unverified and that the official HistoryLine result is only a search lead.
- `CHECKPOINT.md`, “Partial inventory counts,” still calls direct Jumping-page attribution an unverified Wiki-only lead.
- `minecraft-wiki.md`, the accepted near-zero-cutoff source row, still says the Jumping article was inaccessible, has no oldid/page text, and that attribution remains unverified.

Update these summaries to say the exact revision confirms only what the article reports, while preserving the open exact source cutover, other jump paths, bounded recurrence assumptions, and the distinction between Wiki provenance and source acceptance. Historical records, including the prior review and earlier failed-fetch date, should remain intact as historical records.

## Review execution

Compared the candidate commit with its parent, checked all four candidate blob IDs and raw-file SHA-256 values, inspected the prior independent review, and retrieved the cited immutable page and history entry directly in the browser. No MCPK lane, blind source lane, source tree, implementation, or non-Wiki campaign files were inspected. No source writes, decompilation, builds, tests, launches, or external writes were performed.
