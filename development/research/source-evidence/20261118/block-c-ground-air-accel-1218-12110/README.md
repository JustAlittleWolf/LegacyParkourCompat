# Ground and air horizontal acceleration: 1.21.8→1.21.10

This archive preserves two exact adjacent source comparisons for ordinary local-player horizontal input preparation, ground/air acceleration and friction, yaw transform, velocity accumulation, and the pre-collision self-move request. The inspected operation is unchanged across the two edges. The records do not establish full tick movement parity or broader pair coverage.

| Exact pair | Author report SHA-256 | Independent audit SHA-256 | Scope note |
| --- | --- | --- | --- |
| 1.21.8→1.21.9 | `13345c0854a6650bc4be9063a7522d662bf4507ec364be4da6725ed2167b937c` | `cba5c10d54125b21eb64b54f5f08c78e43847151b6bf154fbeb9c845338e0bad` | Audit compares local input, speed, travel and helpers. The report's `Player → Avatar → LivingEntity` inheritance statement is not independently authenticated here. `Player.getFlyingSpeed()` is checked as an override; its source branch remains distinct from the report's inherited wording. |
| 1.21.9→1.21.10 | `ceba78d5565e0fd2cfc5145a23c862ac88489f663890d95c449e669674dd36a3` | `545d0b0ab6b053ac586b1984c0eee93e3e9d764b4e13b532626dbae5c51a689e` | The audit's helper-normalization/trig dependency caveat is paired with the separate exact helper verdict `350e2109e1343cdd325de6ef0605d1a4877209879a1329c087043641783f6c95`. Together they support only the bounded inspected operation. |

## Source identity and exclusions

- The 1.21.9 Mojmap source uses nested `.decompile-provenance.json` SHA-256 `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`; the source owner verified all 6,386 source entries and 88 input pins under shared read leases. Staging was excluded.
- The exact 1.21.10 nested provenance digest is `9a1db4c050c3caa28a1313f89dae9da3b28feaa5f8afba42cab798b47d7ad77c`. The helper verdict corrects an omitted `a` in the report's earlier transcription; preserve the original report and cite this verified digest.
- The earlier partial helper verdict `c-ground-air-accel-1219-12110-audit/verdict.md` is deliberately excluded. Only the final `exact-verdict.md` is archived with the helper correction.
- This scope excludes vertical gravity, the S3 broader coverage, and the 1.21.10→1.21.11 edge assigned elsewhere. Fluid/fall-flying, sprint lifecycle, collision response, input-device scheduling, and runtime behavior are not claimed.
- All source reports and signed verdicts are preserved byte-for-byte. No product, backlog, shared source, build, test, runtime, network, or generated-source change was made.

`git diff --cached --check` reports existing trailing whitespace in `edges/1.21.8-to-1.21.9/report.md`, `edges/1.21.8-to-1.21.9/verdict.md`, `edges/1.21.9-to-1.21.10/report.md`, `edges/1.21.9-to-1.21.10/verdict.md`, and `edges/1.21.9-to-1.21.10/helper-exact-verdict.md`. They are preserved verbatim to retain audited digests.

Base: `aae9c86464638cb5ed7faa535c92569b89484906`.