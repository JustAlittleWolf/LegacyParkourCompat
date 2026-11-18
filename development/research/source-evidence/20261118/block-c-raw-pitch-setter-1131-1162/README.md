# Direct mouse-turn pitch response: 1.13.1–1.16.2 source snapshot

This archive tracks twelve adjacent edges for the ordinary direct player pitch setter and final clamp, with the bounded current-pitch consumer in active local Elytra travel. Eight comparisons have accepted, exact source and audit bindings. Two additional source-body comparisons have matching nested provenance and an independent verdict, but canonical version-root publication readiness remains open. Two edges have not yet been accepted. Only the status and scope shown for each record apply; no gap or gate is inferred closed.

The scope is the direct player response to `Entity.turn(double,double)` and the cited current-pitch read by active local Elytra travel. UI, input scheduling, renderer interpolation, network/teleport paths, raw global pitch setters, runtime behavior, and full pair parity are excluded. This archive does not establish a product range extension.

## Accepted exact source-body comparisons

| Pair | Source report SHA-256 | Independent verdict SHA-256 |
| --- | --- | --- |
| 1.13.1→1.13.2 | `6fc626e980e9e0ae29cd827a041ccae540e86fb6cb71d2f495441644d16a1fe4` | `f89eba13feafb76fc31ba9afeb9140cc7963fc50f957f86ce46238450ce039be` |
| 1.13.2→1.14 | `a8cd96e7ac71bcb7a2a89c77dd7eddd9650a3005af7cb41797f61cc665325641` | `15c2e0583e4b47abc8267d160500eb41d5269084a7af3799cf85cfe63c254912` |
| 1.14.4→1.15 | `059e7902646c68a00fd5fac4be3dc53c816238813212171950cd4be2e4cdb4e5` | `1fcda7a8c2e776b934df199e1e5ae57ba7ba6476461413804d3c05cac881c903` |
| 1.15→1.15.1 | `a4a286452ca6108fadee6583f5df69851103bcfc6636714d97ef48b806bcae93` | `e75e3e635cdce98dff4ea3727174236729250269762121a209799c577574d933` |
| 1.15.1→1.15.2 | `144e2a31b2c2840f63489eef1deee82c1a1ea7cca24341ed2bdb7b04f200656e` | `b9c202781d12b390fabe8e6d7b75c6e148e1afdbbf64b7795059e110ae62f010` |
| 1.15.2→1.16 | `021ade5149924a90d72406f2d9502e6e0e81eb1914c66ae39c503b9cbbc2dcb3` | `5c29f61e9c02b31349ed784416f78a431a451203af1a290cf1f9de2ca06e1de0` |
| 1.16→1.16.1 | `c6fcf05cbbdc01fd9a661958ce9728a0831aa290bff07d743e32e2341d987bf0` | `518ed12e2da0eea0e4cecd075a0557ce836c155cb30cfd493e879dedf5d909f4` |
| 1.16.1→1.16.2 | `a07f7a719687b5d4c16282b6cc7de7ed8667f47e2a1783ca35720962abbd97dc` | `1deb7b62474fa218aded58feb175605678b85a37b16d9e00056589c4155be070` |

## Exact source-body verdict; publication readiness open

| Pair | Source report SHA-256 | Independent verdict SHA-256 | Open gate |
| --- | --- | --- | --- |
| 1.14→1.14.1 | `45c2643103a0261e224c5accbe4aa55d203852ece72302bcd28d919395a4730a` | `12c58d372fa6fb2c92efdc517b0c07121901d4f081aa2af2b0cd6023e2501490` | Exact Yarn patch endpoint publication-sidecar readiness remains open. |
| 1.14.1→1.14.2 | `11e030aab01e39028a61ef70580b28ba2d8304c9a1cd20c011c45ba862a05ae8` | `2c6b0cb23f068c2254bed12270dbf69ac61da8436fafe61ec48101b22c2b4792` | Root `yarn.ready.json`, `yarn.sources.sha256`, and `artifacts.sha256` are absent; nested provenance verifies the bounded body but canonical publication readiness is open. |

The 1.13.1→1.13.2 verdict notes the 1.13.2 ready marker lacks a provenance-hash field; its ready-pinned source manifest and whole-tree verification support the source identity, while that sidecar linkage remains weaker. Its finite `1.0e40` witness is source-level float narrowing, not a hardware input claim. The 1.13.2→1.14 comparison uses the canonical source ID `1.14` (not a separate `1.14.0` tree) and retains the audit's exact Feather readiness/provenance qualifications. The 1.14.4→1.15 audit corrects a one-character omission in the author's 1.15 ready-marker digest; both originals are preserved. The 1.15→1.15.1 audit accepts its finite >360 first-write arithmetic witness, while the author's concrete witness is +150; both clamp to 90F and no runtime observation is claimed. The 1.15.1→1.15.2 audit notes the 1.15.2 ready marker lacks an explicit provenance link, while source/artifact/diagnostic pins match and provenance was hashed separately. The 1.15.2→1.16 audit corrects an initial no-finite->360 reading: an ordinary source-level finite input can transiently produce `368.64F`, but both releases clamp the persisted pitch to `90.0F`; the raw-artifact-cache caveat remains. The 1.16.1→1.16.2 comparison includes its separately bound `identity.md` sidecar.

`git diff --check` reports original whitespace in these bound inputs: `edges/1.13.2-to-1.14/report.md`, `edges/1.14-to-1.14.1/verdict.md`, `edges/1.14.1-to-1.14.2/report.md`, and `edges/1.15.2-to-1.16/verdict.md`. They are kept byte-exact to preserve the supplied bindings; remaining owned text passes this check.

Two exact adjacent edges remain unaccepted: 1.14.2→1.14.3 and 1.14.3→1.14.4. Base: `aae9c86464638cb5ed7faa535c92569b89484906`. No product or backlog claim is made.