# Citation correction: callback-eligibility closure

This supplement corrects one dependency fingerprint in the immutable report `.native-source/20261030/callback-eligibility-closure/report.md`. It does not amend or replace the report, and does not change the independent audit's accepted technical contract.

Mechanically computed SHA-256 identities from the workspace files:

| Exact repository-relative path | SHA-256 |
| --- | --- |
| `.native-source/20261030/callback-eligibility-closure/report.md` | `d3d20d5a1a02d07fc509cc7b4af39348419f397b12652a908e0a053b9bb3450a` |
| `.native-source/20261030/callback-eligibility-audit/audit.md` | `b05ae72ba8d1a8a8251b26e0c94101f1d283580178eb59ec65ddfe5c10b52f0b` |
| `.native-source/20261030/callback-order-audit/report.md` | `1f8d091f1e52705a76447a8972cfeee13d4138516025c281f9bedb2c1beeb8d0` |
| `.native-source/20261029/partial-sweep-no-fluid-callback-source/report.md` | `db064c1dee695a4c51e0b7c1f5f207149fd6b673686ce00fab3462841abfcdc9` |
| `.native-source/20261030/dda-collector-flush-audit/audit.md` | `d48a3bca758d408303cd5acbe5291a72ca5bf250c6faf3c4f02532b00724e1ba` |

**Correction:** the primary report's dependency list labels `db064...` as the hash of `.native-source/20261030/callback-order-audit/report.md`. That path currently hashes to `1f8d...`. The `db064...` digest belongs to `.native-source/20261029/partial-sweep-no-fluid-callback-source/report.md`. The callback-order audit itself binds the original no-fluid report as one of its inputs; the mistaken digest therefore referred to a real related source report but not to the path named in the primary report. The correct immutable callback-order dependency identity is the exact path/hash pair in the table above.

No source or product files were changed. This correction is limited to citation binding; the independent eligibility audit directly checked the exact sources and accepts the bounded admission contract.
