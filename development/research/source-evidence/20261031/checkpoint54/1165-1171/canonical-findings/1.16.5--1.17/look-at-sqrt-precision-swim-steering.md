# Look-at square-root precision changes swimming steering

**Boundary:** 1.16.5 → 1.17; the 1.17 calculation persists in 1.17.1.

`ClientPacketListener.handleLookAt` forwards a coordinate-target packet to the local player’s inherited `Entity.lookAt`. With the same finite FEET-anchor target `(-411973, -745099, 283936)` and player position `(0,0,0)`, 1.16.5 stores the horizontal length using `Mth.sqrt(double)`, whose float result is widened to double; 1.17 uses `Math.sqrt(double)` and retains double precision. The resulting pitch differs by one float ULP (`0x42607901` vs `0x42607900`). The changed pitch selects different `Mth.sin` table entries, so `getLookAngle().y` is `-0.83018905` in 1.16.5 and `-0.8301356` in 1.17/1.17.1.

When the same local player is swimming, not flying, not spectating, on foot, and has Y delta movement `0`, `Player.travel(Vec3)` consumes that look-vector Y. Both values take the same branch and coefficient `0.085`; the written Y delta movement is `-0.0705660691857338` in 1.16.5 and `-0.0705615246295929` in 1.17/1.17.1.

**Applicability:** the finding assumes the same finite coordinate-target packet and equal local player state/input at the movement call. The server’s target production, device input production, runtime execution, and full trajectory are not established. The source route reaches the ordinary swimming travel write.

**Exact source binding:** the independent review below verifies the exact Mojmap call path, triple-version source hashes against parent sidecars or the embedded 1.17 provenance manifest, and the arithmetic consequence. Its accepted conclusion and scope are immutable under SHA-256 `9E2A5E88194C6A959635858E72A66D78D521E9E66D1FD86DED9A51EDB7A6942A`:

- `.native-source/20261031/1165-1171-finish45/lookat-precision-review/review.md`
- Independent verdict: **ACCEPT** as a source-level local-player movement candidate under the stated packet and movement-state gates.
