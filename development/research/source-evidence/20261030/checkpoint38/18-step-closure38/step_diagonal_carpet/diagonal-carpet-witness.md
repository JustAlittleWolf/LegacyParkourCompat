# Diagonal carpet step-descent witness

## Verdict

**This geometry closes the ordinary-player witness gate for the 1.8 → 1.8.1 step-descent delta**, subject to the stated empty-world assumptions (no other collision shapes/entities, default full-cube floor, ordinary local player, grounded, not sneaking/cobwebbed/climbing). The proposed horizontal displacement is vanilla-reachable from ordinary sprint input. On ordinary grounded travel the incoming vertical request is typically negative (`-0.0784` after the preceding gravity update), rather than exactly zero; the first collision pass clips that request to zero on the floor and leaves the grounded step gate active. The alternate-pass witness is unchanged. Thus the exact triple `(0.25, 0, -0.03)` is a valid `Entity.move` geometry probe, and its horizontal part plus required grounded gate are also reachable in normal player travel.

## Exact geometry and provider list

Initial player box: `x=[0.20,0.80]`, `y=[1.00,2.80]`, `z=[-0.59,0.01]`. Initial center is `(0.50, -0.29)` and feet are `y=1`. Default `LivingEntity.stepHeight=0.6F`.

Assume the only nearby collision providers are the full floor block at `y=[0,1]`, carpet at block `(1,1,0)` with shape `[1,2]×[1,1.0625]×[0,1]`, and ceiling block `(0,3,-1)` with shape `[0,1]×[3,4]×[-1,0]`.

- Ordinary sweep for the stated `(dx,dy,dz)=(.25,0,-.03)` is `[.20,1.05]×[1,2.80]×[-.62,.01]`. Strict intersection includes **only the carpet**: its overlaps are `.05` in X, `.0625` in Y, `.01` in Z. The floor only touches sweep `minY=1`; ceiling starts above sweep `maxY=2.8`.
- The raised sweep is `[.20,1.05]×[1,3.40]×[-.62,.01]`. Strict intersection includes **carpet and ceiling**. The floor still touches only `minY=1`, so it is not listed. The ceiling overlaps `.40` in Y, positive X and Z.
- If normal travel supplies `dy=-.0784`, its first sweep additionally includes the floor. The downward Y clip is `0` at floor top `1`; it then has the same horizontal clips as the `dy=0` probe. `onGround` itself also satisfies the step gate.

This follows `Box.expanded` (1.8 `Box.java:31–55`; same in 1.8.1), strict `Box.intersects` (`:153–159`), `World.getCollisions` and `Block.addCollisions` (1.8 `World.java:873+`, `Block.java:292–304`; same collector behavior in 1.8.1). Carpet’s one-sixteenth collision height is provided by its `setShape(0,0,0,1,0.0625,1)` construction (1.8.1 `CarpetBlock.java:21–25,45–59`; the 1.8 provider has the same fixed shape).

## Normal pass and raised candidates

**Normal pass (Y→X→Z).** For `dy=0`, Y remains `0`. Carpet clips positive X to `1 - .80 = .20`; after this move the player maxX is exactly `1`, so strict X overlap is absent and carpet does not clip Z. Normal endpoint is box `[.40,1.00]×[1,2.80]×[-.62,-.01]`, with horizontal squared progress `.20²+.03²=.0409`. With actual grounded travel `dy=-.0784`, the floor clips Y to `0` before the same X and Z operations.

Both step candidates use the raised-pass list and hit the ceiling on ascent. The player starts with top `2.80`; the ceiling underside is `3`, so `intersectY` clips rise from `.60` to exactly `.20`. The raised box is then `[.20,.80]×[1.20,3.00]×[-.59,.01]`.

1. **Up → X → Z:** Carpet has no vertical overlap once raised (`player.minY=1.20 > carpet.maxY=1.0625`); the ceiling only touches the player top at `3.00`. X therefore reaches `.25`, then Z reaches `-.03`. Endpoint box is `[.45,1.05]×[1.20,3.00]×[-.62,-.02]`.
2. **Up → Z → X:** Z first reaches `-.03`, making player maxZ `-.02`, strictly outside the carpet footprint. X then reaches `.25`; the ceiling again only touches vertically. Endpoint and horizontal progress equal candidate 1.

The candidate squared progress is `.25²+.03²=.0634`, greater than normal `.0409`, so the ordinary fallback does not replace the raised route. The source's tie branch chooses the second route when equal; either candidate has the same endpoint.

On descent, carpet no longer overlaps Z (`maxZ=-.02 < 0`), and the ceiling is above the moving box. Neither clips downward motion. The floor is absent from the raised-pass collision list, as established by its strict nonintersection at the query's `minY=1`.

| Release | Requested descent | Descent clip | Final feet Y |
| --- | ---: | ---: | ---: |
| 1.8 | `-stepHeight = -0.6` | `-0.6` | `1 + .2 - .6 = 0.6` |
| 1.8.1 | `-var33` / `-var81 = -0.2` | `-0.2` | `1 + .2 - .2 = 1.0` |

The 1.8 result penetrates the full floor block by `.4` in this tick because that block was omitted from the upward sweep. In 1.8.1 the player returns to the starting floor level. This is precisely the extra-descent consequence in question, not a carpet endpoint catch.

## Ordinary player travel reachability

The exact source call chain reaches `Entity.move` from ordinary `LivingEntity.moveRelative`/travel. Both releases use `updateVelocity`: normalized input is rotated by yaw and added to existing X/Z velocity (`Entity.java:843+` in 1.8, `:848+` in 1.8.1). Player base movement speed is `.1F`; sprinting adds the `0.3F` operation-2 modifier (`PlayerEntity.java:133–136`; `LivingEntity.java:58–60,1022–1037`), giving `.13F`. The local travel input is multiplied by `.98F` before `moveRelative` (both `LivingEntity.java`, near `moveRelative` at 1115/1121), so full forward sprint input contributes approximately `a=.13*.98=.1274` per grounded move on a default floor. Ground friction is `f=.6*.91=.546`.

A concrete construction from rest on clear, level floor: sprint forward for five grounded ticks with one yaw, then on the witness tick keep sprint+forward and rotate yaw. With same-heading full sprint moves, prior horizontal velocity before the witness move is

`p5 = f*a*(1-f^5)/(1-f) ≈ 0.145782`.

The witness move vector is `p5*u(previousYaw) + a*u(currentYaw)`, where `u(yaw)=(-sin(yaw), cos(yaw))` in `(X,Z)` as implemented by `updateVelocity`. The two lengths are `.145782` and `.1274`; their resultant can range from `.018382` to `.273182`, so the target horizontal length `sqrt(.25²+.03²)=.251794` lies inside the reachable range. Choosing the turn angle by the cosine rule gives about `45.756°`; orienting the previous heading so the resultant points at `atan2(-.03,.25)≈-6.843°` yields the requested vector. The source uses float yaw and its trig helper, so regard those displayed angles as construction values; choose the nearest representable yaw pair. The required geometric conditions have room: `dx` exceeds the normal `.20` carpet clip by `.05`, and final `maxZ=-.02` clears carpet by `.02`. Nearby realizable float results preserve both inequalities.

The five approach moves can be placed on the same open floor and end at center `(.50,-.29)`; their path remains west of the carpet (`player.maxX≤.80`) and below the ceiling underside. The ordinary vertical request on this grounded path is clipped to zero by the floor in the initial pass, while `onGround` independently opens the step path. No teleport, direct velocity injection, or non-player physics is needed.

## Exact source identities / anchors

Sources are read-only under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.8,1.8.1}\ornithe-feather`.

- 1.8 `Entity.java` SHA-256 `2be081abe1720f89d7ab90f782b8411e71bc5b8a6b53966b49a2380e3c47ffd9`: `move` starts at 370; second-pass candidate ascent/descent and fallback around 459–533; unconditional descent is line 526; `updateVelocity` at 843.
- 1.8.1 `Entity.java` SHA-256 `aa359621cf9348e5ce4bcb9e28b67f8a5c75d7e5a3d554c8d93471c79540a55c`: `move` starts at 375; selected rise-based descents at 524/529; fallback at 538; `updateVelocity` at 848.
- `Box.java` is byte-identical in both releases, SHA-256 `2d270470a11082e1cf9f772c94ecff9ecbf5430be78947dd152e2e72c20bbc52`.
- 1.8 carpet `CarpetBlock.java` SHA-256 `75016cb9c1c7e237b2a09833d9ab9169c0a542f849d8f22adaa0e804b820b9cf`; 1.8.1 carpet `CarpetBlock.java` SHA-256 `991a6b1fce6e593803b73d44668091522551e3ea1443a8ab5d6685fa82b4ce15`.
- Paired `World.java`, `Block.java`, `LivingEntity.java`, and `PlayerEntity.java` hashes match the values recorded in the supplied collision/core coverage reports; key anchors are listed above and in those hash-bound reports.

Input report identities were rechecked against the assigned SHA-256 values: core coverage `fcb0360dc6ea6d14e28a8777008f81ae7ee8df21b225370c5af1387ec1a43824`; collision coverage `8d79cfe4cbfe9a5c514f61a4d821a744f0e6d8b4ee2505451cdc90150b1eef00`; rejected prior witness `7703d1d4434fbb25834f4799e569aa12d4f5653b9f419238e6808f0814717811`.

This closes only the requested bounded geometric/reachability witness. It is not pair freeze, implementation approval, or runtime parity evidence.
