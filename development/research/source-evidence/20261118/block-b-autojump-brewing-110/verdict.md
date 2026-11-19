# Renewal: two corrected Brewing Stand source clauses

Verdict: **COMPLETED — both corrected clauses are supported by exact source.** This renewal is bound to `domain-b-autojump/brewing-110-alias-source/source-check.md`, SHA-256 `cba108269bf1b487c77ec7d4d3a5aa17ac89dbca01ce91978663a179aee83083` (verified against file bytes). Scope is only the anchor collision-method signatures and the base-plate movement clip versus AutoJump's first matching probe box. The original `audit-result.md` remains unchanged at SHA-256 `70569e36b893c40a65b1709ce60a784ce403f0303b54efd67bda5a1af49722ad`.

## Anchor signature

Exact Feather Gen2 build.2 sources show `1.11 BrewingStandBlock.java:70` declares `addCollisions(..., @Nullable Entity entity)` with no boolean. `1.11.1` and `1.11.2 BrewingStandBlock.java:70` declare the same member with the additional `boolean forceShape`. In all three, lines 71–72 append `COLLISION_SHAPE` (the center stem) and then `SHAPE` (the base plate), without branching on the new parameter. For 1.11.1/1.11.2, `World.java:997` dispatches `forceShape=false`. The signatures therefore differ at the 1.11 patch boundary even though these fixture box additions remain the same and ordered the same way.

## Movement touch/retry, then AutoJump hit

The supplied fixture inputs put the player center at about x=-0.3, z=0.8, with feet y=0.5. `Entity` sets width to `0.6F` and centers its box using half-width, so its X max is exactly 0 and its Z span is about 0.5–1.1. The stand plate occupies local x/z `[0,1]`, y `[0,0.125]`, at block y=1. Thus the player touches the plate at x=0 while overlapping it in Y/Z. `Entity.move` clips Y before X and calls each collision box's `intersectX`; `Box.intersectX` limits a positive move when the moving box's `maxX <= obstacle.minX`. The stem's x-min 0.4375 is farther away than the requested small +X displacement, so it does not limit that displacement; the plate's x-min 0 clips it at the touch.

The grounded/horizontally clipped path retries from the original box with `stepHeight=0.6F`. Feet rise from 0.5 to 1.1, still 0.025 below the plate's top at 1.125. The retry box continues to overlap the plate vertically and sweeps into its X/Z footprint, so its +X movement is clipped at the plate as well. The outer LocalClientPlayerEntity callback runs after `super.move` returns; the step retry is internal to that call.

AutoJump then receives zero resolved X/Z displacement and uses the forward-input fallback toward +X. At the lifted probe height `0.5+0.51=1.01`, the side segment intersects both the stand stem and the plate; it is not geometrically isolated to the stem. However, the stand collision method adds the stem first, then plate, and AutoJump iterates the collision list in that order, records `box2.maxY`, and breaks on the first matching side segment. Therefore this fixture selects the stem's world maxY `1.875`; with feet at `0.5`, `u=1.375`, which fails the `u<=1.2F` bound. The full-floor control's supplied feet `1.125` place the lifted ray above the plate, and the stem gives `u=0.75`, which passes the timer interval. This confirms the corrected distinction: the plate blocks the movement attempt; the stem is AutoJump's first matching probe obstacle and determines the rejected height.

This is a static exact-source check, not a movement simulation or runtime-parity claim.
