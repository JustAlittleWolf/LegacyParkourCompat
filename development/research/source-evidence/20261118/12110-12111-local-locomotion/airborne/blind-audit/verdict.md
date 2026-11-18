# Independent review verdict: ordinary dry airborne local movement

**Verdict: ACCEPT, bounded no-difference claim.** The author's exact comparison supports the frozen finding for ordinary dry airborne local-player input projection, airborne speed selection, and the first `SELF` displacement request before clipping/collision resolution. No positive difference is warranted within this slice.

## Bound claim

The claim applies to ordinary input without an actively used item, a dry nonswimming path, airborne state, no ability flight, no passenger route, no sprint-specific speed selection, and ordinary physics. It covers projected local input through the velocity addition passed as the first displacement request to `Entity.move`. It does not assert realized position after collision, full tick parity, intermediate patch boundaries, or pair-wide coverage. The author's and frozen reports agree that active-item multiplier behavior is excluded; it is not part of this verdict.

## Evidence binding

- Frozen blind audit: `blind-audit/frozen.md`, SHA-256 `37601153c44129ed4728b735ce0f28ae53dca4d028cf5f2fe23f359846ca0eb1`.
- Exact author comparison: `source/comparison.md`, SHA-256 `dc4b1a06072a3686237d34099b1d4553fb9a7fb093ebe01f66f3ebcdb79d62c5`.
- The frozen audit establishes LocalPlayer input assignment and unchanged square-movement projection, followed by unchanged Entity input-vector math. The author comparison supplies the intervening Player-to-LivingEntity route and explicitly traces airborne speed selection through `Player.getFlyingSpeed()` to `0.02F` under the bounded exclusions, then confirms the same pre-collision request semantics. Its exact source identity includes Player.java hashes for both endpoints; the frozen identity includes LocalPlayer, LivingEntity and Entity source hashes and manifest verification.

## Audit note

The frozen report describes the airborne selector in `LivingEntity` but does not name the `Player.getFlyingSpeed()` override. The author comparison fills that source-coverage gap for this bounded player claim. This does not change the frozen bytes or expand its verdict. No source outside the two bound artifacts was consulted for this author comparison review.