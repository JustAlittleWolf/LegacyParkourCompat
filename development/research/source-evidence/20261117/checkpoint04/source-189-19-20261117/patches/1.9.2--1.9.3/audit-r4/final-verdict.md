# Full-pair audit continuation r4 — 1.9.2 → 1.9.3

## Verdict: ACCEPT corrected source coverage; pair remains PARTIAL

The predecessor-hash defect identified by the prior full audit is resolved by the identity-only erratum. The erratum supplies the verified SHA-256 for `coverage-r2.md`, matching the value already recorded in `acceptance-identities-r3.md`, and changes no source claim or coverage disposition. I accept `coverage-r3.md` with that erratum applied as the current source-coverage revision.

## Immutable identities

| Evidence | SHA-256 |
|---|---|
| Prior full-pair r3 REQUEST CHANGES verdict | `5F9EF7D9AF7D06DA7D2CB2306CB43E7FC1255390DE75BA5B0B6B3CB482B4AB62` |
| `coverage-r3.md` | `E1D3BC5FD6D778C87CB9337884440D7E742B1DB493A8D51C09E1F35F71E9A636` |
| `acceptance-identities-r3.md` | `933161BEA1A3BD8922524E24FC0F45E991BF80CF105BDC97C08104A7FFD9CF04` |
| `coverage-r3-hash-correction.md` | `95AF1CC1D941C701740A43AC25645AE6D054F6D568D0B49FFF6B6B5443C03846` |
| Independent hash-correction ACCEPT verdict | `45B3DE1DC163C91F29031D8856C5491EDEB4A8B35F71CF215DAE0165DC924892` |

## Pair state

The accepted revision keeps the local tick-gate/clamp candidate separate from the conditional cache-backed collision-query observation. The query evidence remains conditional on an already-cached out-of-range chunk and a player/query path reaching it. Ordinary cache population, local-player/query reachability, and trajectory significance are unresolved. Therefore this ACCEPT applies to the corrected source-coverage report and its bounded dispositions only; the exact pair remains PARTIAL and is not frozen. No implementation eligibility or runtime parity is inferred.
