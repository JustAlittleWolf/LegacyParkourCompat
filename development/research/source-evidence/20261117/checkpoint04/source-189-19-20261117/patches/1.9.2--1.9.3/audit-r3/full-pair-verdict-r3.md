# Independent full-pair source audit r3 — 1.9.2 → 1.9.3

## Verdict: REQUEST CHANGES

The coverage r3 correction properly separates the local tick-gate/clamp candidate from the conditional cache-backed collision-query observation. The underlying source claims and unresolved applicability boundaries are supported. However, the immutable revision chain in `coverage-r3.md` contains an incorrect SHA-256 for its predecessor report, so this revision is not fully bound and cannot be accepted as the current audited inventory. Pair coverage remains partial; no pair freeze, implementation eligibility, or runtime parity is accepted.

## Immutable inputs checked

| Input | SHA-256 |
|---|---|
| `coverage-r3.md` | `E1D3BC5FD6D778C87CB9337884440D7E742B1DB493A8D51C09E1F35F71E9A636` |
| `acceptance-identities-r3.md` | `933161BEA1A3BD8922524E24FC0F45E991BF80CF105BDC97C08104A7FFD9CF04` |
| `coverage-r2.md` (actual SHA-256) | `C2BC2D53FDF47D36D779FD2A660F7A21050DD7DA8A734EE48778EDC1CB88007A` |
| `audit-r2/verdict.md` | `DE54085FF2A6D5BD47693A395D11A6835F3C145933406F945CC31ADC6B43D0AD` |
| Prior full-pair `audit-r3/full-pair-verdict.md` | `CD078FE7396D09601A972CA60CB19526517BBBC2AB6435F537DFDD9943D0F180` |
| Conditional query follow-up | `5BFBA153A7AC91BE980B3126FA38FA59F1056116F02C0F22F2260A1B003F1CE8` |
| Bounded follow-up ACCEPT | `FB2F3CC8813782B5324FCA5FE10F386F36CC93BA92F0B6F1568EACB1B82BBA26` |
| Tick-gate candidate `findings/player-tick-world-limit-r2.md` | `B4B0ED48F380356ABCD47663C1B656A2EEFC39738EE5DE745B54102960456514` |

## Source and claim audit

I independently checked both mapping-local provenance manifests against the exact `ornithe-feather` source trees. The 1.9.2 provenance SHA-256 is `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`; the 1.9.3 provenance SHA-256 is `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`. Each contains 1,819 source entries and matches exactly 1,819 on-disk Java files, with no missing, extra, or mismatched source hashes. The pair has 1,816 shared paths, three removals, three additions, and 621 changed shared hashes.

The corrected r3 disposition is supported. `LocalClientPlayerEntity.tick()` checks `world.isChunkLoaded(BlockPos(x, 0, z))` before `super.tick()` in both releases. In 1.9.2, `World.isChunkLoaded(BlockPos, boolean)` rejects a position failing `contains`; in 1.9.3 it shifts x/z and calls `isChunkLoadedAt`. The one-argument overload supplies `allowEmpty=true`, which `ClientWorld.isChunkLoadedAt` accepts. `PlayerEntity.tick()` clamps x/z to ±29,999,999 in both releases after movement. Thus the bounded tick-gate candidate describes a skipped tick and unchanged out-of-range position in 1.9.2 versus an admitted tick and same-tick clamp in 1.9.3, conditional on an already out-of-range local-player state.

The distinct cached-state observation is also correctly bounded. In 1.9.2, the collision scan's loaded-chunk check rejects out-of-horizontal-range cells before the cache-backed state query. In 1.9.3, the client query can reach the cache; a missing entry resolves to `EmptyChunk`/air, while a matching already-cached chunk can supply its state. `World.getCollisions` is called from player movement resolution. The prior bounded ACCEPT verifies this source route only. It does not establish normal out-of-range chunk population, ordinary local-player/query reachability, or a trajectory consequence. Coverage r3 keeps all those gates unresolved and does not treat the bounded ACCEPT as pair acceptance.

The remaining ordered tick, movement, liquid, provider, callback, and direct-response coverage is retained from r2 and was confirmed by the prior full-pair audit. The accepted supplement changes the inventory disposition, not those unchanged paired claims. Pane and piston-head geometry remain the assigned exclusions.

## Required correction and remaining state

In `coverage-r3.md`, the “Predecessor coverage” line gives `C2BC2D53FDF47D36D779FD2A660F7A210DD7DA8A734EE48778EDC1CB88007A`; this is not the SHA-256 of `coverage-r2.md`. The correct value is `C2BC2D53FDF47D36D779FD2A660F7A21050DD7DA8A734EE48778EDC1CB88007A`, which is correctly recorded in `acceptance-identities-r3.md`. Correct that binding in a new immutable revision and request an independent review of the identity correction.

Even after the hash correction, the r3 report explicitly leaves ordinary cache population, local-player/query reachability, and trajectory significance unresolved. Those applicability gates remain open, so the exact pair must remain partial and unfrozen.
