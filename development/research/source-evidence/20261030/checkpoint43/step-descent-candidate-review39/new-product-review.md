# Independent static review: integrated 1.8 step descent

## Verdict

**ACCEPT, bounded to this scalar hook wiring.** Reviewed product commit `518743a28e67cb0ade7789bb04323d2dc052cb07` against base `405b19c3`. The hook is reached through the existing step-up resolver and applies its returned scalar before the existing Y descent clip. I found no concrete wiring or fallback defect. This is static integration review only; it does not establish runtime parity.

## Dispatch and source arithmetic

`EntityStepUpCollision.resolveStepUp` derives `nativeDescent` as the existing double `-selected.rise()`, calls `CollisionCall.downwardReturn(nativeDescent, maxUpStep)`, and feeds the returned request into the same `sweep.collide(Direction.Axis.Y, selected.box(), ...)` operation. The mixin adapter delegates to `MovementRuntime.downwardStepReturn(..., entity)`, which resolves the independent `StepDescentHook` for that client player and returns the native scalar when no hook applies.

The V1_8 implementation returns `-configuredStepHeight` with a `float` parameter and `double` return. Unary negation therefore occurs in float precision before widening, matching 1.8 `Entity.move`'s `dy = -this.stepHeight`. The source request remains distinct from the later collision-clipped descent.

## Result, fallback, and profile behavior

The existing clip result `down` still feeds both `new Vec3(selected.x(), down, selected.z())` and the settled box translated by that same `down`. The existing ordinary-route comparison and fallback remain in place and unchanged. The descent pass is still evaluated before that comparison as in the base implementation; if the ordinary route wins, the result is discarded and the callback is pure, so it does not alter ordinary movement state or the resolved-box handoff.

`CollisionCall.downwardReturn` has a default implementation returning `nativeDescent`. The only new override is the mixin adapter, and the only resolver call is in the existing V1_13 step route. Change resolution excludes the V1_8 change for selected V1_8_1 and later profiles because its annotation is older than the selected version; the default therefore retains the existing selected-rise descent for 1.8.1+ without a redundant V1_8_1 rise hook. Other step-up resolvers do not invoke the callback. No profile beyond V1_8 receives this scalar substitution.

The diff is limited to the V1_13 resolver seam, the V1_8 scalar change, its hook/runtime lookup, and the callback adapter. No route loop, collision sweep, ordinary fallback, unrelated hook, or version-specific movement path was otherwise changed.

## Scope limits

No build, tests, or game launch was run. This review does not establish tick-level parity or broader 1.8 pair completeness.
