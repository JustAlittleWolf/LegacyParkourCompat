# Final claim review: ordered contact effects

## Bound inputs

Reviewed only the authorized author artifacts and the previously frozen independent audit. Their exact current-byte SHA-256 values match the supplied identities:

- `source/comparison.md`: `b08e34996a5d7ca952ca620999ffaf149caa7a96a05ca66121dc8fe4d1f19fc5`
- `source/claims/ordered-contact-effects.md`: `e659135b08a409a498b74a91881e6150ae69fc7b90483698254d10059939f66c`
- `blind-audit/frozen.md`: `b13612eecc2b79aa8bf33577600727a7ddfaa7f8fdbc6d9b352d413efd4af965`

## Verdict

**Accepted for the bounded source claim.** The claim that 1.21.4→1.21.5 changes ordinary SELF contact enumeration, with a conditional consequence for direct Honey/Web/Sweet Berry writes and the next move’s stuck multiplier, is supported by the compared mapped methods and the frozen independent audit. The claim does not establish full-pair coverage, exact patch introduction, or runtime parity.

The independent Web witness is feasible with identical state/support/geometry inputs: full-cube support at y=0; a no-collision COBWEB at `(0,1,1)`; player feet at y=1, width 0.6, center `(0.5,0.5)`; ordinary SELF displacement `(1,0,1)`. In 1.21.4, `boxTraverseBlocks` traces the endpoint bounding-box minimum corner from `(0.20001, 1.00001, 0.20001)` to `(1.20001, 1.00001, 1.20001)`. Its diagonal tie visits z before x; the clamped crossing point at `(0.99999, 1.00001, 1.00001)` is expanded by the 0.6 box dimensions, so block `(0,1,1)` is enumerated. The entity-shape sweep intersects that full entity-inside cube, and WebBlock stores its multiplier. In 1.21.5, equal X/Z displacement selects YXZ; the accepted movement is recorded as X then Z legs. On the X leg the player’s Z extent ends below 1; on the Z leg its X extent begins above 1. The target block is not enumerated/contacted, so this movement writes no Web multiplier. The next `Entity.move` therefore sees a different stored multiplier. The witness remains dry, supported, local, non-riding, non-flying, and non-piston.

Honey and berry are supported as conditional callback consequences, not as separately witnessed trajectories: Honey’s eligible `entityInside` path directly rewrites velocity and resets fall distance; Sweet Berry Bush directly writes its multiplier for non-FOX/non-BEE living entities before its excluded server damage branch. If their eligible callbacks differ by route/order, the source bodies make the movement consequence direct. The author claim correctly conditions this effect on an eligible contact change.

For 1.21.5→1.21.6, the comparison’s bounded equivalence is supported for ordinary movement below the 100-record compaction cap: 1.21.5 records each axis leg, while 1.21.6 expands `axisIndependant=true` through the same `axisStepOrder`. On equal X/Z, this remains Y then X then Z; direct Web/Berry writes and next-move consumption retain their order. No full-pair or runtime conclusion follows.

## Residual limits

- Honey and berry have no separate concrete trajectory witness in this review; only their direct callback bodies and the Web contact-order witness support the conditional claim.
- The 1.21.6 100-record compaction branch is outside the accepted ordinary bound.
- The exact introducing patch inside 1.21.4–1.21.5 remains unknown.
- Runtime movement parity and full pair inventory were not reviewed.