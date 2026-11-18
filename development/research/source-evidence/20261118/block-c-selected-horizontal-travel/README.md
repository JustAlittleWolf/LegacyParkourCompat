# Selected early horizontal travel source comparisons

This archive preserves six independently audited, non-contiguous comparisons of bounded ordinary dry horizontal travel slices. Each row records only the assigned input-to-travel arithmetic and the audit's qualifications. The records do not constitute full pair coverage, a continuous version range, complete travel equivalence, runtime parity, or an implementation claim. The excluded 1.13.2→1.14 airborne acceleration-width comparison is separately archived and is not repeated here.

| Pair | Author report SHA-256 | Independent audit SHA-256 | Scoped result and qualification |
| --- | --- | --- | --- |
| 1.14→1.14.1 | `6085815a0e80cebf3a873a10da6fc7fb49a3fa30a73b115edafdb79ef930d964` | `d66dc0da34a84966d0aed84473292f8afc8f66de77416279f6ad4ecf98b3c131` | The audit accepts the bounded ordinary horizontal slice and records that sprint from an earlier tick can carry airborne speed into a nonsprinting travel; `0.02F` applies to steady nonsprinting history. Additional input-assembly audit `dry-input-audit.md` (`e9dfab904f1ebdc63791fc36601e539769031cf8262bd5b001e1c5769c422e46`) finds no difference in that bounded input assembly and excludes item-use. |
| 1.14.1→1.14.2 | `1b65e78a28332f1da464c0b05e03e396379aac1f4238d08f604eded9e856556a` | `f12883faba0926e99a85a5fd6b17bb446262febabfc95b2ae96f825f5edb90ee` | Consumed axes and speed writes for the next tick are bounded; the Player override is accounted for. The audit records a separate upstream residual without asserting it is ready here. |
| 1.14.2→1.14.3 | `85a6735f5d791a0449f2524d41945b14995565810b1a19a777ae9e37fe41c52a` | `b4274396eacc97a199360635d816db49af44a27eee41f657441b10dbc300359f` | Bounded supplied-input horizontal arithmetic. |
| 1.14.3→1.14.4 | `4311d24605dbc70d2b4839d540847859c47d0a529a4571ffe3c68fe3aab6b837` | `993b9dd3edbd15718af785219610c0f4f6cc34200ddbcddaba606011993fa8bf` | Audit corrects the 1.14.4 post-move citation to line 1862 and extends ordinary/item-use input relative to the supplied vector. The embedded BEL byte in its stage-2 path label is preserved verbatim. |
| 1.14.4→1.15 | `32c8ece9cdd57ed19e15ee52c724b56e74a8d3988dcd70914bec48ef5cff2fc3` | `781ecf316a6ea1ec1be74dec8f4cb19e7f9c98fd8e10e488fc8dd0b413c27821` | Arithmetic is conditional on the supplied consumed vector, yaw, `onGround`, and friction. The friction lookup location change is outside this scope. |
| 1.15.2→1.16 | `a8c95c1d1bc34d33563dd2f1bff0fe14e128c690b451c4c9497da6045b07aa76` | `3a09f9d4cdd6d8ee52bb8fec12044994804a561ffb5e317b14874616fdef3fdc` | Qualified supplied-state arithmetic. The audit corrects two malformed author source-hash rows and verifies the actual bytes. |

Every archived report and audit is copied byte for byte from the assigned lab path. Original paths relative to `c-stream-horizontal-114-1165/`:

- `PAIR/author/report.md`
- `PAIR/audit/audit.md`
- For 1.14→1.14.1 only: `1.14--1.14.1/dry-input-assembly/audit/audit.md`

The exact base is `aae9c86464638cb5ed7faa535c92569b89484906`. No product or backlog files were changed; no builds, tests, runtime, network access, or generated sources were used.
Static check: `git diff --cached --check` reports only original blank lines at EOF in `1.14--1.14.1/report.md`, `1.14.2--1.14.3/report.md`, and `1.14.3--1.14.4/report.md`. These bytes are preserved to retain their accepted hashes.
