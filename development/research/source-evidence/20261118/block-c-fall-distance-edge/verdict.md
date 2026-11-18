# Independent source-only verdict: fall-distance-edge-backoff

**Terminal status:** COMPLETED  
**Verdict:** ACCEPTED for the bounded Levitation edge-backoff claim, with the concrete witness constraint below.  
**Finding revision:** Git payload `583f8a9b:development/research/findings/1.19.4--1.20.1/fall-distance-edge-backoff.md`  
**Git blob SHA-1:** `83cd60913520882c40d6c9bae31070bdbbce3310`

## Exact source identities

Canonical Mojmap source files under `decompiled_minecraft/<release>/mojmap/`; SHA-256 of complete files:

| Release | `LivingEntity.java` | `Entity.java` | `player/Player.java` |
|---|---|---|---|
| 1.19.4 | `C8D91AF61F87AAA1666DE79696D7CD9D4A8212D05F873BDAF28D7BB2926BF165` | `3667FEE610CBC5F58012E3A8FB8D6C4849F649FB7FE5300595158112D8B4B58B` | `5E4436AFCCB361156F8184E7A5CFD91D5B12DCFE8F5937B4AC23DD3EDDA737A2` |
| 1.20 | `DECF8CD70D194098AD51D0C82E5A0087E0687881E0A2DC66456F2BA6EA76676F` | `94B9C3656715DE2D61FA9A02CCEF164261E50EB13D6090A6F07E58FE9C5B0759` | `873D82C6F5471D06812929E471D970FD973BFBE21638E4E348A6FCDE45637DAC` |
| 1.20.1 | `DECF8CD70D194098AD51D0C82E5A0087E0687881E0A2DC66456F2BA6EA76676F` | `94B9C3656715DE2D61FA9A02CCEF164261E50EB13D6090A6F07E58FE9C5B0759` | `873D82C6F5471D06812929E471D970FD973BFBE21638E4E348A6FCDE45637DAC` |

The three relevant 1.20.1 files are byte-identical to 1.20. The 1.20 Mojmap tree contains `.decompile-provenance.json`; the 1.19.4 and 1.20.1 trees have no such sidecar. The release directory labels and hashes above are the source identities used for this audit.

## Retrace and verdict basis

- **1.19.4:** `LivingEntity.travel`, lines 2089–2096, invokes `handleRelativeFrictionAndCalculateMovement` to obtain `$$28`; that helper, lines 2152–2155, issues `move(MoverType.SELF, ...)`. Only after the helper returns does `travel`, lines 2093–2096, process Levitation and call `resetFallDistance()`.
- **1.20 and 1.20.1:** `LivingEntity.aiStep`, lines 2559–2573 in 1.20.1, calls `resetFallDistance()` at 2565–2566 for Slow Falling or Levitation, before `travel` at 2569–2572. Within `travel`, the shared friction helper still issues the `SELF` move before it returns. This is the timing change relevant to the finding.
- **Both endpoints:** `Entity.move` invokes `maybeBackOffFromEdge` before collision resolution (1.19.4 lines 568–569; 1.20.1 lines 612–613). `Player.maybeBackOffFromEdge` requires not flying, requested Y `<= 0`, `SELF` or `PLAYER`, `isStayingOnGroundSurface()`, and `isAboveGround()`. The ordinary player implementation of `isStayingOnGroundSurface()` returns `isShiftKeyDown()` (1.19.4 lines 308–309; 1.20.1 lines 309–310), so sneaking is a sufficient ordinary crouch condition while airborne. `isAboveGround()` is `onGround || (fallDistance < maxUpStep && !noCollision(box moved by fallDistance - maxUpStep))` (1.19.4 lines 1095–1098; 1.20.1 lines 1097–1100).
- Therefore an airborne crouching non-flying player with `fallDistance > maxUpStep` fails `isAboveGround()` in 1.19.4 (assuming `onGround` is false); in 1.20/1.20.1 the pre-travel reset makes the tested fall distance zero. If support intersects the box shifted downward by `maxUpStep`, `noCollision` is false and `isAboveGround()` succeeds after reset. The edge backoff loops then reduce horizontal movement in 0.05 increments when the proposed movement-height probe has no collision.

## Concrete witness and limits

The edge-contact geometry is an explicit support precondition, not a general collision-shape result. A source-logic witness is a crouching, airborne, non-flying player with Levitation, prior fall distance greater than `maxUpStep`, a small negative Y request and a total requested movement vector with squared length below 1 (avoiding the separate total-distance-squared `>=1` fall-distance-reset path in `Entity.move`), support intersecting the box shifted down by `maxUpStep`, and a full horizontal request whose corresponding support probe has no collision. At the old edge-backoff predicate the high prior fall distance disables backoff, so the full horizontal request reaches collision resolution; after the new pre-travel reset, the support predicate enables the 0.05-step backoff and reduces that request. This establishes the claimed horizontal-position difference conditionally, without claiming a general support-shape result.

The claim is Levitation-specific. The 1.19.4/1.20.1 source comparison supports the finding; 1.20 has the same relevant source hashes as 1.20.1. No execution, tests, build, or runtime check was performed.

## Acceptance gates

- **Closed:** ordering of fall-distance reset relative to the `SELF` movement; edge-backoff method gates and fall-distance predicate; ordinary sneaking gate; endpoint behavior persists through 1.20.1.
- **Explicit applicability precondition:** support contact for the shifted probe and a horizontal request that would leave that probe's support; this is not a proof for arbitrary block shapes.
- **Witness isolation:** use a negative Y request and keep the total movement vector squared length below 1, so the later `Entity.move` reset gated by total movement length squared `>= 1` does not alter the old-state check before it is consumed.
- **Out of claim:** Slow Falling timing; shape-generation/general support geometry; exact intermediate patch boundary within 1.19.4–1.20.1.

