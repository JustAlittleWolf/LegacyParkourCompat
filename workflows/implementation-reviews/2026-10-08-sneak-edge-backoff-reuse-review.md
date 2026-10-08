# Sneak-edge backoff reuse investigation

**Result: keep the common historical helper; add a narrow dispatch-placement improvement.** The helper has two callers, and both need the same historical three-loop calculation. Current vanilla exposes no reusable method with the same geometry or gates. An invoker cannot call an arbitrary method subrange.

## Exact source comparison

Read the ready source files without running decompilation:

- 1.16.1 `Player.java`, `maybeBackOffFromEdge()` lines 1010–1059, SHA-256 `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e`.
- 1.18.2 `Player.java`, lines 1032–1080, SHA-256 `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a`.
- Current 26.2 `Player.java`, lines 880–949, SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`.

The existing V1_16 and V1_18_2 behavior classes each apply their own version gate, then call `change/common/SneakEdgeBackoff.apply`. Its axis-X, axis-Z, and diagonal loops match the 1.16.1 and 1.18.2 source: each probe uses `getBoundingBox().move(x, -probeDistance, z)` and the same 0.05 reduction order. Both versions preserve the incoming Y component. Their gates differ: 1.16.1 requires a SELF/PLAYER mover, on-ground state, and staying-on-ground surface; 1.18.2 also applies flying and above-ground checks. Keeping the shared calculation below those gates retains version dispatch and probe distance.

Current 26.2 `Player.maybeBackOffFromEdge()` is not a reusable replacement. Its whole-method gates include `!abilities.flying`, `!(delta.y > 0)`, and current `isAboveGround`; these are not the 1.16.1 gate. Its loops call private `canFallAtLeast`, which probes an AABB inset by `1.0E-7` horizontally and below the box. Historical loops probe the full translated bounding box. The current loop also precomputes signed steps and uses `Math.abs(value) <= 0.05`, while historical code uses `value < 0.05 && value >= -0.05` before adding or subtracting `0.05`. Reusing `canFallAtLeast` or the whole modern method would change historical probes or gates.

`PlayerMovementAccessor` already invokes the narrow vanilla `isStayingOnGroundSurface()` method. There is no vanilla method for the historical loops. An invoker for `maybeBackOffFromEdge()` would invoke the complete modern implementation; one for `canFallAtLeast()` would use the changed inset geometry. No accessor reuse is safe.

## Placement change

The existing `PlayerMixin` return hook replaced the result after the modern method had already performed its probe loops. The current method body only reads player state, probes collision and returns a vector; it does not mutate player state. The hook now dispatches at `HEAD` and cancels only when `MovementRuntime.find(SneakEdgeBehavior.class, player)` resolves. It passes the same movement, mover type, staying-on-ground value and versioned probe distance to the unchanged behavior/helper. That avoids calculating and discarding the modern result for handled historical profiles. When no behavior resolves, the method continues unchanged, including the separate `SneakEdgeDistanceBehavior` redirect. The edit is commit `504f45a91fbbd5627ee9695bc2033c1ca02b69de` and changes only the injection point plus its explanatory comment.

## Scope and limits

Only `SneakEdgeBackoff`, its two callers, `PlayerMixin`, the existing `PlayerMovementAccessor`, and the exact Player source were examined. The helper remains unchanged and continues to carry the historical loops; the two versioned callers retain their distinct gates and probe-distance resolution.

Current local `main` remained at `d8f3956602da94bf0cf67753cc0a9f4665397729`, the review branch base; it is an ancestor of the task branch. No main commits landed during this task, so there are no incoming changes to reconcile.

`git diff --check` passed for the code change. No tests, build, decompilation, client, server, TAS, Gym, Docker, or push was run. Runtime behavior was not exercised; this is a static placement cleanup.

**Next action:** the movement implementation owner can include the placement change in integration and perform the authorized build/runtime validation.

Reviewer: static implementation investigation, 2026-10-08.
