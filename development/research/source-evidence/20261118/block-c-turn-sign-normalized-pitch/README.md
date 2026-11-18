# Sign-normalized direct pitch width: 1.12.2→1.13

This archive retains one accepted, exact-source comparison of the ordinary direct camera pitch update and its next dry, active local Elytra movement request. It is a bounded operation finding, not full movement-pair coverage, a patch-introduction boundary, a product range, or runtime parity.

| Pair | Frozen source SHA-256 | Independent verdict SHA-256 | Finding scope |
| --- | --- | --- | --- |
| 1.12.2→1.13, Ornithe Feather build 2 | `bbe505676fe8eaf0df3096ffbe15fc21c0996985afbcea345fc1ebe8b6e71999` | `bf1705346f203fa0e76e4cd2639f912a27b7170f1d9de20e510aebf7d4f7b0bc` | Sign-normalized ordinary pitch intent retains adjacent binary32 values across the old float and new double setter paths, crossing the same trig lookup boundary. Under the fixed legal movement fixture, the first active dry local Elytra request differs by approximately `6.54e-8` in Y. |

The old and new source paths reverse the input sign conventions; the comparison normalizes to intended pitch change before setter conversion. This does not claim equal raw input values, device/event scheduling, or callback preprocessing. The movement consequence is conditional on the camera update preceding the next movement tick, as the audit states. No runtime observation is claimed.

`git diff --cached --check` reports preserved original whitespace in `edges/1.12.2-to-1.13/report.md`: trailing spaces near the report end and a blank line at EOF. Those bytes are not normalized because that would change the accepted source SHA.

The report and verdict are frozen byte-for-byte. The verdict embeds the exact source report SHA above. No code, backlog, shared sources, builds, tests, runtime, network, or generated source changed.

Base: `aae9c86464638cb5ed7faa535c92569b89484906`.