# Independent hash-correction audit — 1.9.2 → 1.9.3

## Verdict: ACCEPT

The identity-only erratum is correctly bound and makes no substantive change to the source coverage disposition. It identifies the incorrect predecessor digest in `coverage-r3.md` and supplies the digest verified from the exact `coverage-r2.md` bytes. The existing `acceptance-identities-r3.md` already contains the corrected digest.

## Immutable inputs checked

| Input | SHA-256 |
|---|---|
| `coverage-r3-hash-correction.md` | `95AF1CC1D941C701740A43AC25645AE6D054F6D568D0B49FFF6B6B5443C03846` |
| `coverage-r3.md` | `E1D3BC5FD6D778C87CB9337884440D7E742B1DB493A8D51C09E1F35F71E9A636` |
| `coverage-r2.md` (verified exact bytes) | `C2BC2D53FDF47D36D779FD2A660F7A21050DD7DA8A734EE48778EDC1CB88007A` |
| `acceptance-identities-r3.md` | `933161BEA1A3BD8922524E24FC0F45E991BF80CF105BDC97C08104A7FFD9CF04` |
| Prior full-pair audit verdict | `5F9EF7D9AF7D06DA7D2CB2306CB43E7FC1255390DE75BA5B0B6B3CB482B4AB62` |

The hash correction changes only the predecessor identity line. It does not alter any claim, pair disposition, or acceptance boundary. The source pair remains partial and unfrozen; ordinary cache population, local-player/query reachability, and trajectory significance remain unresolved under the prior full-pair verdict.
