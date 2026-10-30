# Independent static review: 1.8 step-descent candidate

## Verdict

**ACCEPT, bounded to the scalar request change.** Candidate commit `96dd8fa760b00d6aa778868ed28d9bb08594b6f8` implements the accepted 1.8 operation accurately: after a raised route is selected, use the configured 1.8 step height as the downward request, while leaving the caller responsible for the existing collision clip. I found no source-fidelity defect in the candidate. It has no caller yet, so this accepts the scalar implementation only; it does not accept an integrated change or claim movement parity.

## Source and witness checks

The candidate's `StepDescent.downwardReturn` returns `-configuredStepHeight`, where the argument is `float`, and the method's return type is `double`. Java unary numeric promotion preserves `float` for unary minus on a `float`; the negative float is then widened to double for the return. This matches 1.8 Ornithe Feather `Entity.move(double,double,double)`, which executes `dy = -this.stepHeight` while `stepHeight` is a float. Do not cast/widen the configured height before negation.

The compared source rule is:

- 1.8: request `-this.stepHeight` (Entity.java:526), then clip that request against the selected raised box using the reused collision list.
- 1.8.1: request the selected route's clipped rise, `-var33` or `-var81` (Entity.java:524/529), then run the same descent clip.
- 1.8.2: retain the selected clipped rise, `-n` or `-q` (Entity.java:520/525), then run the same descent clip.

This is the scalar distinction represented by the candidate. The accepted diagonal witness plus its axis-order correction and independent review close ordinary-player applicability for the 1.8 → 1.8.1 difference. The correction confirms both raised candidates execute Up → X → Z; the reviewed geometry still clears the carpet vertically on ascent, wins against the ordinary clipped route, and is Z-disjoint from carpet during descent. That evidence supports the bounded source finding, not pair-wide or runtime parity.

## Candidate inspection

The hook is a separate `@MechanicType("entity.step.downward-return")` operation. The 1.8 class is annotated `@MovementChange(emulates = ParkourVersion.V1_8)`, has the required public no-argument constructor, and performs only the scalar substitution. `MovementRuntime.downwardStepReturn` preserves the native request when no change resolves. The runtime helper is not wired into movement yet, as reported; that is an integration requirement rather than a candidate defect.

When `V1_8_1` is added as the separate profile boundary, preserve resolver direction: profile `V1_8` should resolve this `V1_8` change; profile `V1_8_1` should resolve its separately implemented rise-based change. `V1_8` must then cover only 1.8, with 1.8.1+ assigned to the new profile grouping. The candidate must not be broadened to the current `V1_8` alias set if the profile split is deferred.

## Minimal product integration plan

Use the existing 1.13 step-up resolver and its shared descent sweep. In `EntityStepUpCollision.resolveStepUp`, after choosing `selected`, derive the native request as the existing double `-selected.rise()`. Dispatch that scalar through the new independently resolved hook with the configured height as a `float`, then pass the returned double directly to the existing `sweep.collide(Direction.Axis.Y, selected.box(), request)` call. Keep the route-building loop, route score, ordinary-route fallback, selected box, collision list, and descent clipping in place; do not copy or replace the loop.

The resolver currently lacks an entity reference. Add a narrow descent-selection callback to its existing `CollisionCall` adapter (or equivalent mixin-to-runtime seam). Implement that callback in `EntityStepUpCollisionMixin` with `MovementRuntime.downwardStepReturn(nativeRequest, configuredHeight, entity)`. This keeps the mixin general, dispatches via the existing runtime resolver, and leaves the version-specific scalar in the independent hook. No exact-profile gate is needed: the hook resolver and `@MovementChange` version determine selection, and the empty-hook path returns the native request.

For the ordinary winning step route, use the scalar result only as the input to the existing descent clip. Continue to use that clipped `down` both in the returned movement vector and in the settled bounding box. This preserves native collision flags/position bookkeeping driven by the returned movement and the resolved-box handoff in the mixin. If the raised candidate loses to the ordinary route, its result remains discarded as today.

## Scope limits

This review is static. I did not run a build, tests, or a game. No complete 1.8 pair freeze, integration approval, or runtime tick parity follows from this review.
