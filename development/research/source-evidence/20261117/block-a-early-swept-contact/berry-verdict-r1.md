# Independent source verdict — corrected r1

## Verdict

**Accepted as a conditional source-level witness, bound to the corrected r1 artifacts.** The amended side-graze is collision-free while the 1.21.2/1.21.3 DDA still enumerates the intermediate berry cell. The callback and next-move slowdown chain is source-supported. The explosion route can provide the qualifying horizontal movement, conditional on the impulses being applied before the same local travel. No runtime, tick-order observation or runtime parity claim is made.

## Immutable input identity

Both offered corrected artifacts were hashed by absolute path with `Get-FileHash -LiteralPath` and match the supplied digests:

- `comparison.corrected-r1.md`: `b2a3e8386d2fb78d9bfca479c74f1d30e59d545bb31d8e5f71c72292da3cc269`
- `berry-bush-path-contact.corrected-r1.md`: `36f18901ed30b485cd9a72da71981c133e22e8fb97a233686085b777d139c4d4`

The prior verdict bytes were preserved unchanged: `verdict.md` SHA-256 `07583005e66b223ed9df64209be53afd0219f224c16d9e832033c882a756cbb9`. The frozen source roster also remains unchanged: SHA-256 `fdc6672f0b4ec452e02449fb9569768d4f140ac41d4d7f6e5062fa2367f3c644`. Twelve source hashes cited in the corrected comparison were rechecked against absolute canonical Mojmap paths and matched; the 1.21.2 explosion-knockback-resistance base value was also checked against the embedded 1.21.2 provenance.

## Independent checks

- **Geometry:** for a standing player centered at z=1.25, the AABB z-min is 0.95. Age 1 berry collision shape reaches z=0.9375, so the 0.0125 gap remains clear during the x sweep. Dirt at y=0 supports the bush. With from `(0.5,1.1,1.25)` to `(2.6,1.3,1.25)`, the final AABB starts at x=2.3 and cannot include bush cell x=1. The deflated final-box dimensions and reconstructed sweep start in the corrected comparison are consistent with `boxTraverseBlocks`; its DDA crosses `(1,1,0)` and expands candidate coordinates by the box dimensions. Thus the corrected candidate is intermediate-only for the new callback scan and is not blocked by the berry's actual collision shape.
- **Length fallback:** the scan falls back to final-box cells only for `delta.lengthSqr() < Mth.square(0.99999F)`; the comparison is strict. The corrected vector has squared length `4.45`, so it takes the sweep branch. A genuinely short segment below the threshold cannot supply this intermediate-only witness through that branch.
- **Callback and stored factor:** the 1.21.1 post-move scan is bounded by the final AABB. In 1.21.2/1.21.3, a live, physics-enabled local player passes the local-client caller gate and the `isAffectedByBlocks` provider gate. The berry inherits `Shapes.block()` as its entity-inside shape, so the sweep skips the moving-shape predicate and invokes its callback. The callback stores `(0.8F,0.75,0.8)` for a non-flying player without an age gate; `Entity.move` consumes that factor on the next move and clears it. These findings match the corrected artifacts.
- **Conditional movement magnitude:** standard primed TNT has power 4; `ServerExplosion` adds its impulse to current entity velocity, server code includes player knockback in the explosion packet, and `ClientPacketListener` adds that packet vector to the local player. The default explosion-knockback-resistance attribute is 0. With four unobstructed eye-height-aligned blasts 3.1 blocks behind the standing player, the radial distance used by `ServerExplosion` is based on entity position and is `sqrt(3.1^2 + 1.62^2) ≈ 3.498`, not 2.8. Each x impulse is therefore about `1 - 3.498/8 = 0.563`; four add about `2.25`, still above the needed 2.1. The corrected comparison's `~0.65` per-blast arithmetic is high, but its threshold conclusion survives. Slightly moving the centers farther back can reduce the accumulated impulse to about 2.1. Ordinary air travel passes current delta movement to `move` before applying subsequent damping. The source proves this additive route, not that four packets arrive before a particular travel tick; that remains the corrected artifact's explicit condition.

The checked 1.21.3 callback/travel/provider files are byte-identical to the inspected 1.21.2 files. The supported interval is the inspected 1.21.1 to 1.21.2 change; no uninspected intermediate patch boundary is inferred. No runtime claim is attached.