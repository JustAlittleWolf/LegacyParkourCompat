# Stair state-to-coordinate reachability correction (r2)

This immutable correction binds to `build/source-189-19-20261117/providers/stair-state-coordinate-reachability.md`, SHA-256 `67606516C0D27056671C2FB3FB1F2B8D0F7891B4F24F0665AE1386DA8C5BA497`, and its independent REQUEST CHANGES audit, SHA-256 `6CD03A4712DAA41C0F10B49F76A98FDEC58D3B4CCD648730D2CD2D80162D4C76`.

The original report's half-height sentence was reversed for the bottom stair. Correct intervals are:

- `HALF=BOTTOM`: the base occupies `[y,y+0.5]`; step boxes occupy `[y+0.5,y+1]`.
- `HALF=TOP`: the base occupies `[y+0.5,y+1]`; step boxes occupy `[y,y+0.5]`.

This follows from the paired collision definitions: 1.8.9 `StairsBlock.setBaseShapeForCollisions` (lines 74–80) sets the upper base for TOP and the lower base for BOTTOM, while its step helpers use the opposite vertical half. In 1.9, `BOTTOM_BASE_SHAPE` is y=0.0..0.5, `TOP_BASE_SHAPE` is y=0.5..1.0, `BOTTOM_*_SHAPE` step boxes are y=0.5..1.0, and `TOP_*_SHAPE` step boxes are y=0.0..0.5 (`StairsBlock.java` lines 32–47, 76–80, 93–101, 125–133). The paired StairsBlock source hashes remain those bound in r1: 1.8.9 `FADC964480227F6FFD5756471212B18D0172BFFBCD0866EE0C278454AB079409`; 1.9 `19E43095405BB61EEC8C9529922384C97EB6645B28F5007D3652FEE9F19FAA98`.

Only this interval sentence is corrected. The facing/neighbor-coordinate predicates, enum-label caveat, placement reachability, and reuse of the accepted per-stair clipping-order evidence are otherwise unchanged from r1. This correction does not claim new broad technical acceptance or full-pair freeze.
