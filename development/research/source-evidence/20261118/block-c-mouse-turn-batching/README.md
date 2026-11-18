# Mouse callback batching source evidence (excluded input-device plumbing)

This source-only archive preserves two exact comparisons of upstream mouse callback handling: 1.20.4→1.20.5 and a separate 1.20.4→26.2 destination witness. The project README explicitly excludes input-device plumbing. Callback delta accumulation and scheduling therefore remain an excluded source disposition, even though the resulting live pitch can later reach movement code. No product candidate or movement implementation claim is made.

The bounded source witness is ordinary same-frame input at initial pitch 85° with vertical mouse delta +100 then −100: the older per-callback path reaches about 75°, while the newer batch reaches 85°; the modulus branch is inactive for this witness. A larger independent witness also exercises modulo, so it is corroborating evidence rather than the sole basis for the result. These are source-conditional predictions, not runtime measurements.

| Record | Source identity SHA-256 | Independent verdict SHA-256 | Additional identity |
| --- | --- | --- | --- |
| 1.20.4→1.20.5 | `45436c6764dc4abcf0ba333d80dc2110de686400944757fb73ca7681605120de` | `a0dd152ebc9b70fda3b6d5860577c0bb34ad362810d56d1089cba8237d348120` | — |
| 1.20.4→26.2 destination witness | Original report `2602924a7855d0e9c4fe31e94c4d32b5bae1c6f60620a4db1b98a1f3b75af85b` | `14f56153aa9a960abecacbe0367f178323453c6622c9646c33733a32890aba7e` | Reconciliation `034898b07eaa0c5233c6864d42a2a3ca338217bd968a02eb5bff1c77b52872a0` binds original report, appended copy, and frozen verdict |

The auditor accidentally appended material to its scratch `report.md`; that exact 9,583-byte file is preserved as `report-with-misplaced-append.md` (SHA-256 `ce49122741f763667eaea9c539855485097fd7b430d227bba8ed2ed2157cf9ad`). The unmodified 7,321-byte original report is preserved separately as `report-original.md`. The original bytes exactly match the appended file's prefix; the originals and verdict were not rewritten.

The direct `Entity.turn` first-argument pitch response is an independent, in-scope operation and is not the callback batching behavior archived here. This archive makes no claim about UI, rendering, server dispatch, network behavior, runtime parity, or authoritative movement-read timing. No product or backlog claim is made. Base: `aae9c86464638cb5ed7faa535c92569b89484906`.