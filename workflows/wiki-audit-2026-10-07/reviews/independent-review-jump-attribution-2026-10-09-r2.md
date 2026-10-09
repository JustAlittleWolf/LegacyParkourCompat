# Independent review r2: Jumping attribution summaries (2026-10-09)

## Scope and verdict

Re-review of the three corrected summary passages from correction commit `89f812397e6420f0381abc952242c848c59f5028`, as present on final author tip `710c0f215ab364bf1df8ec0d59364fe739877198` (`fix/wiki-attribution-2026-10-09`). Compared the correction with the prior attribution commit `97a8f2b374ea33980b2da5f4f4cdc0ebae5eea74` and checked the final-tree evidence records.

**Verdict: ACCEPT, bounded to the summary reconciliation.** Both `CHECKPOINT.md` passages and the `minecraft-wiki.md` near-zero-cutoff row now report rendered-revision attribution as verified while keeping it separate from the previously accepted bounded source recurrence. They retain the raw-wikitext limitation, unknown exact source cutover, and open other jump paths. The correction does not claim raw archival or expand the source finding.

This supersedes the specific REQUEST CHANGES disposition in the first review for the three stale summaries only. That original report remains unchanged at commit `711dc27d9cdd816867704de9f99df4211814a748` and still records the initial review accurately.

## Candidate file bindings

The following exact final-tip blobs bind the corrected summaries and the unchanged retrieval records:

| File at `710c0f215ab364bf1df8ec0d59364fe739877198` | Git blob | Bytes | Raw SHA-256 | Relation to correction |
|---|---|---:|---|---|
| `workflows/wiki-audit-2026-10-07/CHECKPOINT.md` | `11144c2e1a578df2021bf3d69b0d808ebc36202e` | 20,177 | `1c5116584859788b6a43c9a95338427a16783b7bad63752484460398ee801999` | Corrected summaries |
| `workflows/wiki-audit-2026-10-07/minecraft-wiki.md` | `77ec7ddead477a01c0f9ebab550251ed4e714931` | 47,079 | `89558197fc2d2b98638b5af9017d419be0585ca7c2d86b059623c50b8d9219d6` | Corrected source/Wiki distinction |
| `workflows/wiki-audit-2026-10-07/wiki-page-fetch-log.md` | `80083cc40ceea78601b400d626af9d799d4545ca` | 4,879 | `9ba554ea2bb4ff804009ecafc5df49309b52aa6126b9e237f0b804343c0c985f` | Unchanged retrieval evidence |
| `workflows/wiki-audit-2026-10-07/jump-attribution-2026-10-09.md` | `8c159d2cd95c05f9ec21a51f4c2f0ff784e726ac` | 2,015 | `9babcadce6d712ba95b3c07f65fb46c2acda446e3fc592924d0de467d96675be` | Unchanged rendered-page record |

Correction commit `89f812397e6420f0381abc952242c848c59f5028` changes only the two corrected files in this Wiki-audit directory. The fetch log and retrieval record are unchanged from the previously reviewed attribution snapshot. No further changes to this directory occur between correction commit and final author tip.

## Summary checks

- The “Newly published” checkpoint entry now says the immutable r2 record described the earlier review state, then says the article attribution is now verified against rendered revision `oldid=3825931`. It expressly says the page evidence does not validate the recurrence or identify exact source cutover; other jump paths remain open; raw wikitext was not archived.
- The checkpoint's “Partial inventory counts” entry states that the bounded recurrence is accepted for its stated path and the rendered revision confirms the article attribution. It retains unarchived raw wikitext, unknown exact source cutover, and open other paths.
- The near-zero-cutoff source row in `minecraft-wiki.md` now links the immutable rendered revision and its 1.9 / 15w45a values. It explicitly says the Wiki attribution does not validate the source recurrence, keeps the recurrence bounded to its assumptions, leaves exact source cutover open, and preserves the unavailable-derived-JAR equivalence limitation.
- The fetch log still preserves the earlier 2026-10-08 web-reader failure, the 2026-10-09 rendered browser retrieval, the exact displayed row and revision identity, and `net::ERR_BLOCKED_BY_CLIENT` for the raw route. Neither raw wikitext nor a saved HTTP response is claimed.

No renewed browser retrieval was needed: this correction does not change revision identity or retrieval evidence. The prior report binds the browser's rendered revision/history observation and the direct raw-route failure.

## Merge and execution

Merged current local `main` into the reviewer branch before handoff. The only incoming change under this Wiki-audit directory deleted the first report; it was retained byte-for-byte as required, and this separately bound r2 report records the new verdict. Other incoming changes did not touch this Wiki audit. No MCPK, blind source lane, implementation, or source tree was inspected. No source writes, decompilation, builds, tests, launches, or pushes were performed.
