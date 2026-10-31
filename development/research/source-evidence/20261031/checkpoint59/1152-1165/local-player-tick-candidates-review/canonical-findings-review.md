# Independent binding review — canonical local-player findings

## Reviewed immutable revisions

| Artifact | SHA-256 |
|---|---|
| `canonical-findings/1.15.2--1.16.5/shift-clears-double-tap-sprint-timer.md` | `c54a33eb2c6bab5edc839eacb289f7cc8e6e2001f1fbaea14af1b7eb2ec1318d` |
| `canonical-findings/1.15.2--1.16.5/ability-flight-water-shift-descent.md` | `4426bc50f7d7140277507a93d8d025fff988f0a3685ef8ea2ed6eb24cf967681` |
| Candidate report | `a0ad62636b1995748e8633a926b54625cae0310531519472362dd8510c919db1` |
| Accepted review addendum | `51b9024bed4dc2d802640a12fd472b3160cb88455e7a06844985dc07d3020353` |

I recomputed both finding hashes and confirmed the report/addendum hashes against the already bound revisions. I compared each finding's gates, operation order, response, movement consequence, and intermediate-boundary qualification with the accepted report and addendum. The source files named in the findings remain the six files already checked against the exact 1.15.2 and 1.16.5 Mojmap source manifests; the pair-manifest identities and candidate hash ledgers were verified in the bound addendum.

## Verdicts

### `shift-clears-double-tap-sprint-timer.md` — ACCEPT

No material claim drift. The finding accurately states the old decrement/item-use handling, the new sampled-shift clear before sprint evaluation, and the shift exclusion on the clearing tick. Its later consequence preserves the important condition that the timer remains positive after decrement on that tick: old code may sprint immediately on a later eligible press, while new code starts a fresh seven-tick window. The movement consequence and unknown intermediate boundary match the accepted candidate report. The wording “more than one tick remaining at the start” is an adequate statement of the necessary timer condition in the described state path.

### `ability-flight-water-shift-descent.md` — ACCEPT

No material claim drift. The finding correctly identifies the direct LocalPlayer descent call and the added `isAffectedByFluids()` gate, the ability-flight override result, and the unchanged `-0.04F` response implementation. It preserves the candidate's semantic separation from the water-current route. The `-0.024` post-travel Y-velocity statement is appropriately conditioned on equal sampled state and the same flying travel branch; its explicit limit on position claims matches the accepted addendum.

## Review boundary

This binds only these two immutable finding revisions to the accepted source candidate. It does not establish the introduction release within 1.15.2 → 1.16.5, complete pair coverage, implementation, or runtime parity. No product implementation or runtime was examined.
