# Independent product review: bounded lava-presence latch

## Verdict

**ACCEPT** for the bounded 1.16/1.16.1 local-client lava-presence operation and the documented native boundaries. No functional findings remain in the reviewed integration.

Reviewed product commit: `79fbeb5b` (`Implement bounded client lava presence latch`), with latest-main merge tip `cf17359bd7dac3a36602a9b8f0ae25ee810d389e` on `fix/lava-presence-116-20261029`. The product worktree was clean at review time. The changes are limited to the query/provider hook, the two bounded provider changes, per-entity latch context, and the existing client mixins. The merge also contains independent main-branch changes; this review evaluated the lava-presence patch against its parent and the merged resolver state.

## Bound evidence

The four assigned inputs were recomputed locally and matched their supplied SHA-256 values:

- Source report `.native-source/20261028/lava-presence-client-consumers/REPORT.md`: `d3bbd6c106a0051396aca17aa0269e17fc217542a44f07656ada428a99454b05`.
- Independent source audit `.native-source/20261028/lava-presence-client-consumers-audit/audit.md`: `02e6604f2c3115247747c43eb06c0fe041fd017a6f4cbc88b10442b4b1466b6e`.
- Neutral candidate `.native-candidates/AF-lava-presence-20261028/candidate.md`: `6c9f2803458d19385097007a42e0abe2562fbeac2f8cd68f11058a113db307a4`.
- Candidate review `.native-source/20261028/lava-presence-candidate-review/review.md`: `1273b0af53f3471ab3fb77e4eb5b421cb122e3756c520be37a6c5a2ab54dd5bd`.

The report and audit bound the operation to Mojmap 1.16.1 → 1.16.2, with the old callback latch for 1.16/1.16.1 and the newer cached query for 1.16.2–1.16.5. Their documented raw-cache and embedded-publication identity limitations remain unchanged; this review makes no pair-wide or runtime-parity claim.

## Product wiring

- `ChangeResolver` selects the nearest change whose `emulates` version is greater than or equal to the selected profile. The literal provider annotations resolve the existing immediate `LavaPresence@V1_13_1` for its older interval, the new empty/native `NativeLavaPresence@V1_15_2` for the unclosed 1.14–1.15.2 interval, and `LavaPresenceLatch@V1_16` for 1.16/1.16.1. At 1.16.2 and later, both older providers are ineligible, so the hook is absent and native behavior runs.
- `EntityLavaPresenceMixin` only intercepts a `LocalPlayer` and uses `Optional<Boolean>` so an explicit `false` is preserved while an empty result leaves the native return value intact. `MovementRuntime.find` additionally requires an enabled client-side Player. No server, remote-player or non-player path is modified.
- The latch is stored per Entity through the mixin context, read only for the local-player query and updated only for the local player on the logical client. It is initialized false by Java field initialization and cleared at each eligible movement pass before the historical lava scan.
- The update is injected at the existing `getBlockSpeedFactor` site. In the 1.16.1 source, `Entity.move` performs fall handling and collision, then step-on and movement-noise work, then clears `isTouchingLava` and runs `checkInsideBlocks`, and only afterward calls `getBlockSpeedFactor`. The current injection follows the replayed step-on hook and precedes contact traversal and subsequent move work. It updates the latch without invoking block callbacks, so it does not duplicate the current callback traversal.
- The scan uses the 1.16.1 inset domain (`0.001` on each AABB face), inclusive floored block bounds, and the exact `hasChunksAt(min,max)` admission; failure leaves the freshly reset latch false. It only considers `LiquidBlock`, the only source caller of `setInLava`. The surface expression uses float arithmetic and the original, un-deflated entity box, matching `LiquidBlock.entityInside`; it retains the source's `minY < surfaceY || surfaceY > maxY` test.
- The existing water-first `WaterFirstLavaResultProjection@V1_16` remains a separate depth/current projection. The reviewed patch changes `isInLava` presence only; it does not clear or rewrite water/lava numeric depth, eye-fluid state, damage behavior, or the projection's internal tracker state.

## Limits

No build, tests, game launch, or runtime trajectory check was performed. The source evidence does not establish full movement-pair coverage or runtime parity. The product review covers only the accepted local-player lava-presence lifecycle and its stated version boundaries.
