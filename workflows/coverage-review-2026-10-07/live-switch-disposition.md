# Live profile switch review disposition

This is a disposition addendum to `later-pose-review-f45f7a5.md`, for implementation commits `4a95574`, `efc1ee0`, and `f45f7a5` (reviewed tip `f45f7a57424f824871e19daacc250b58b955a00a`). The underlying source review remains static. No tests, builds, game sessions, or runtime witnesses were run.

## Decision

**Historical pose/profile correctness: no blocker found in this reviewed range.** For a stable selected profile, the resolver chooses the intended dimensions behavior and ordinary pose changes continue through the native pose-fit selector. This decision is limited by the accepted F002 fit-query context difference: historical 1.13.2 uses `hasNoCollisions(null, box)`, while the current native selector uses an entity-aware query. Conditional tight-space outcomes remain accepted; this is not a claim of arbitrary-world collision or trajectory parity.

**Cross-profile transition safety: retained limitation; no historical policy can be inferred.** The historical versions did not support an in-game profile switch. On the reachable current API path, a profile change takes effect on the next tick. If a player is crouching in 1.5-block clearance and changes from CURRENT/V1_14 to V1_13, the epoch refresh resolves the new 1.65-block crouch dimensions and calls `refreshDimensions()` immediately. The 26.2 refresh path installs that larger AABB without a collision-fit check, so it can intersect the ceiling. The later native pose selector cannot undo that expansion when its initial SWIMMING-fit candidate also fails.

This is not a blocker to integrating the historical steady-state pose corrections. It does block a claim that live profile changes are collision-safe in arbitrary tight spaces. If that separate safety contract is required, it remains an open product/API decision.

## Why this review does not prescribe a code correction

No minimal native-consistent transition correction is established by the accepted sources:

- Skipping or deferring the requested profile would contradict the next-tick effective-change promise.
- Relocating the player would invent transition behavior and could alter parkour position.
- Applying the target dimensions immediately preserves the API timing but can create the documented overlap.

The historical source provides no cross-profile transition rule to choose among those outcomes. Keep the limitation explicit until a transition policy is accepted; do not silently skip a profile or move the player as a workaround.

## Witness boundary

The live-switch witness remains unexecuted. Any initial witness of next-tick profile application must place the player in open space where the target profile's resolved dimensions and the native pose selector's fit candidate both fit. A low-ceiling switch witness must wait for an explicit transition policy; it should not be treated as a required parity witness under F002's conditional fit-query acceptance.

## Eye and camera scope

The physical crouch-height corrections retain the modern native crouching eye height. The accepted F002 boundary covers physical pose sizing and conditional pose selection, not eye height, camera position, fluid-state consequences, or complete pose parity. This disposition makes no eye-height or camera-parity claim.

## Blockers and limits

- **Integration blocker for ordinary historical pose/profile behavior:** none found in this reviewed range.
- **Transition limitation:** live profile changes can expand the AABB into a low ceiling; policy is undefined because historical profiles had no in-game switch. This limits claims about tight-space transition safety, not steady-state historical pose correctness.
- **Accepted F002 limit:** collision-query context differs, so tight-space results remain conditional and arbitrary-world parity is not established.
- **Unverified:** no runtime witness, build, or test was run. Eye/camera and fluid behavior remain outside the accepted boundary.
