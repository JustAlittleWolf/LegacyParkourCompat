# Independent correction audit: ACCEPT

## Bound inputs

- Corrected report: `build/source-189-19-20261117/providers/stair-state-coordinate-reachability-r2.md`
- SHA-256: `3E7438882405A235DB17378C6476AF0ABB0B7044FA34182BF96416C8B2EBF73F`
- Original report: `build/source-189-19-20261117/providers/stair-state-coordinate-reachability.md`, SHA-256 `67606516C0D27056671C2FB3FB1F2B8D0F7891B4F24F0665AE1386DA8C5BA497`.
- Original bounded verdict: `build/source-189-19-20261117/providers/stair-state-coordinate-reachability-audit/verdict.md`, SHA-256 `6CD03A4712DAA41C0F10B49F76A98FDEC58D3B4CCD648730D2CD2D80162D4C76` (REQUEST CHANGES limited to the bottom stair step-box interval sentence).

## Correction verified

The r2 report accurately states the paired vertical intervals:

- `HALF=BOTTOM`: base `[y,y+0.5]`; step boxes `[y+0.5,y+1]`.
- `HALF=TOP`: base `[y+0.5,y+1]`; step boxes `[y,y+0.5]`.

The 1.8.9 `setBaseShapeForCollisions` source (lines 74–80) selects the upper half for the TOP base and lower half for the BOTTOM base. Its `setStepShapeForCollisions` (lines 297–307) sets the complementary vertical interval. The 1.9 constants likewise define `BOTTOM_BASE_SHAPE` at y=0..0.5, `TOP_BASE_SHAPE` at y=0.5..1, `BOTTOM_*_SHAPE` at y=0.5..1 and `TOP_*_SHAPE` at y=0..0.5 (lines 32–45); `getCollisionShapes` selects them by HALF (lines 74–80).

The paired `StairsBlock.java` source hashes remain 1.8.9 `FADC964480227F6FFD5756471212B18D0172BFFBCD0866EE0C278454AB079409` and 1.9 `19E43095405BB61EEC8C9529922384C97EB6645B28F5007D3652FEE9F19FAA98`, matching the original audit's bound source identities. The r2 artifact cites both immutable prior identities above. No other claims or mappings changed.

## Verdict boundary

ACCEPT for the interval correction only. Reuse the original audit's verified horizontal all-facing/half state-to-coordinate mapping and legal placement/query reachability. This does not re-audit those claims, reopen the accepted clipping-order review, establish a trajectory, close other dependencies, or freeze the 1.8.9→1.9 pair.