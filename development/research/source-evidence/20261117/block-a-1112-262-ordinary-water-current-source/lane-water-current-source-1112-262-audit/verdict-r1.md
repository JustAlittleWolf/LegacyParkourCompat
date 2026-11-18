# Independent r1 source verdict: ordinary water current

**Verdict: ACCEPTED, bounded to this source slice, with the runtime water-tag gate retained.** The revised comparison and finding agree on the enclosed terminal-cell witness, source-level state mapping, and per-update impulse difference. This rebind does not change the original audit or roster.

## Exact artifact binding

| Artifact | SHA-256 |
|---|---|
| Corrected comparison, revision 1 | `14d118bd2b8bdbbc4f618ffb1cba209c9f32f051ff8ebe274f0d72fbad0ea701` |
| Ordinary-water-current finding | `a161e239dbfa03492cd8933f42b930802126d5734eaa4eff8db06870dd2e0f7a` |
| Initial comparison | `fdc66edbfcaa2f8794070f009c7a6952a86b88f8dc68b50164c8c20c5a64bf8c` |
| Initial audit JSON (file bytes) | `20e8d3ddb08991d35a20649b260a52494a75ea9be0f70ebbf869354f715b90c1`; canonical payload `52fecc460f52be5a30f333e328713b4b2f370f4ca2396b7c866e44d802b2b4ef` |
| Initial roster JSON (file bytes) | `0237e52965388e245b5fcb076f6fb9ee46f6c6457ac91e1b218f661e56d417c5`; canonical payload `29c3701d20effb61dba30cc6ee8b0e814e7d3e7a4b99671b5e555f2135c48dd6` |

The corrected comparison and finding hashes match the assigned immutable identities. The original audit and roster file bytes match the identities from the prior audit.

## Bounded witness check

The revised channel is enclosed: full-cube floor at `y=-1`; side walls along `z=7` and `z=9` for `x=8..15`; end walls at `(7,0,8)` and `(16,0,8)`. The water row is at `z=8,y=0`: terminal `LEVEL=7` / flowing amount `1` at `x=8`, levels `6..1` / amounts `2..7` through `x=14`, then source `LEVEL=0` / source amount `8` at `x=15`; no same-type fluid above. Keep chunks `(0,0)` and `(1,0)` loaded. The local player is centered at `(8.5,0,8.5)`, standing, on foot, not riding, not flying, with zero horizontal motion. Its narrowed scan reaches only water cell `(8,0,8)`; the expanded 26.2 preflight stays inside chunk `(0,0)`.

The channel boundaries block downward, sideward, and westward spread. The terminal old level 7 cannot advance to level 8; modern amount 1 has dropoff 1, so its side-spread amount is zero. Once the sourceward row has settled, the exact current state is stable across later fluid updates. Per-player fluid refresh reads the current state during the ordinary local-player base tick; no particular fluid-tick age enters the current arithmetic. The old still/flowing block identity does not change the query, which uses water material and `LEVEL`.

At the terminal cell, east is the only horizontal gradient. Old `6-7=-1` and modern `1/9-2/9=-1/9` both normalize west. Old adds `0.014` west. Modern computes height `1/9`, scales the one-cell average by `1/9`, then `0.014`, yielding `0.014/9 = 0.0015555555555555555` west. Zero horizontal prior motion satisfies the `<0.003` components gate; the conditional minimum raises the modern impulse to `0.0045000000000000005` west. The arithmetic and gate difference are confirmed at source level.

## Water-tag condition and bounds

The 26.2 Java data provider defines default `FluidTags.WATER` membership with `FluidIds.WATER` and `FluidIds.FLOWING_WATER`. That supports the vanilla default-data expectation. It does **not** prove which generated or shipped runtime tag JSON is present or loaded. Therefore the 26.2 witness is conditional on effective runtime membership of the flowing-water fluid type in `FluidTags.WATER`; that resource check remains open. This is not a runtime parity claim, an intermediate-version boundary, or a full-pair coverage/freeze verdict.
