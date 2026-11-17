# Coverage r3 predecessor-hash erratum — 1.9.2 → 1.9.3

This immutable erratum applies only to the predecessor identity line in `coverage-r3.md` (SHA-256 `E1D3BC5FD6D778C87CB9337884440D7E742B1DB493A8D51C09E1F35F71E9A636`), reviewed by `audit-r3/full-pair-verdict-r3.md` (SHA-256 `5F9EF7D9AF7D06DA7D2CB2306CB43E7FC1255390DE75BA5B0B6B3CB482B4AB62`, verdict REQUEST CHANGES).

The `Predecessor coverage` line in that coverage revision transcribed the SHA-256 of `coverage-r2.md` incorrectly as `C2BC2D53FDF47D36D779FD2A660F7A210DD7DA8A734EE48778EDC1CB88007A`. The verified SHA-256 of the exact `coverage-r2.md` bytes is `C2BC2D53FDF47D36D779FD2A660F7A21050DD7DA8A734EE48778EDC1CB88007A`. `acceptance-identities-r3.md` already records the corrected value.

This erratum corrects that identity binding only; it does not revise the coverage text or its claims. Pair coverage remains partial and unfrozen. The ordinary cache-population, local-player/query-reachability, and trajectory-significance gates remain unresolved, and the full-pair verdict remains REQUEST CHANGES.
