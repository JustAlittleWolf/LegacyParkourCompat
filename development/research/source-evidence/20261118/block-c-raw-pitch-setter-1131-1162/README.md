# Direct mouse-turn pitch response: 1.13.1–1.16.2 source snapshot

This partial archive preserves five exact, independently accepted comparisons for the ordinary direct player pitch setter and its final clamp, with the bounded active-local Elytra pitch consumer. The requested interval contains twelve adjacent edges; only listed comparisons are closed, and no gap is inferred.

The scope is the direct player response to `Entity.turn(double,double)` and the cited current-pitch read by active local Elytra travel. UI, input scheduling, renderer interpolation, network/teleport paths, raw global pitch setters, runtime behavior, and full pair parity are excluded.

| Pair | Source report SHA-256 | Independent verdict SHA-256 |
| --- | --- | --- |
| 1.13.1→1.13.2 | `6fc626e980e9e0ae29cd827a041ccae540e86fb6cb71d2f495441644d16a1fe4` | `f89eba13feafb76fc31ba9afeb9140cc7963fc50f957f86ce46238450ce039be` |
| 1.14.4→1.15 | `059e7902646c68a00fd5fac4be3dc53c816238813212171950cd4be2e4cdb4e5` | `1fcda7a8c2e776b934df199e1e5ae57ba7ba6476461413804d3c05cac881c903` |
| 1.15.1→1.15.2 | `144e2a31b2c2840f63489eef1deee82c1a1ea7cca24341ed2bdb7b04f200656e` | `b9c202781d12b390fabe8e6d7b75c6e148e1afdbbf64b7795059e110ae62f010` |
| 1.16→1.16.1 | `c6fcf05cbbdc01fd9a661958ce9728a0831aa290bff07d743e32e2341d987bf0` | `518ed12e2da0eea0e4cecd075a0557ce836c155cb30cfd493e879dedf5d909f4` |
| 1.16.1→1.16.2 | `a07f7a719687b5d4c16282b6cc7de7ed8667f47e2a1783ca35720962abbd97dc` | `1deb7b62474fa218aded58feb175605678b85a37b16d9e00056589c4155be070` |

The 1.13.1→1.13.2 verdict notes the 1.13.2 ready marker lacks a provenance-hash field; its ready-pinned source manifest and whole-tree verification support the source identity, while that sidecar linkage remains weaker. Its 1.0e40 witness is source-level float narrowing, not a hardware input claim. The 1.14.4→1.15 audit corrects a one-character omission in the author's 1.15 ready-marker digest; the original report and verdict remain unchanged. The 1.15.1→1.15.2 archive includes only the signed report and verdict; the report's stated caveat is that 1.15.2's ready marker lacks an explicit provenance link, while source/artifact/diagnostic pins match and provenance was hashed separately. The 1.16.1→1.16.2 comparison includes its separately bound `identity.md` sidecar.

`git diff --check` reports original whitespace in these byte-bound artifacts: `edges/1.13.1-to-1.13.2/verdict.md`, `edges/1.14.4-to-1.15/report.md`, `edges/1.14.4-to-1.15/verdict.md`, `edges/1.15.1-to-1.15.2/verdict.md`, `edges/1.16-to-1.16.1/report.md`, `edges/1.16-to-1.16.1/verdict.md`, `edges/1.16.1-to-1.16.2/identity.md`, and `edges/1.16.1-to-1.16.2/report.md`. These original lines are preserved unchanged because normalization would invalidate the supplied byte bindings; other owned text passes this static check.

Seven adjacent edges remain outside this snapshot: 1.13.2→1.14, 1.14→1.14.1, 1.14.1→1.14.2, 1.14.2→1.14.3, 1.14.3→1.14.4, 1.15→1.15.1, and 1.15.2→1.16. Base: `aae9c86464638cb5ed7faa535c92569b89484906`. No product or backlog claim is made.