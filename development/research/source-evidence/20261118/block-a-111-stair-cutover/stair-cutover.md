# Straight cobblestone stair piston response: 1.10.2 to 1.11.2

**Bounded verdict: ACCEPT for this fixture.** The ordinary-player reachability blocker from the full-stone witness is avoided by the accepted bottom-north straight cobblestone-stair fixture. This establishes only the requested piston-response source consequence for that fixture.

## Exact state and ordinary collision

In all four sources, `Block.init` registers `stone_stairs` as `new StairsBlock(block.defaultState())`; the same local `block` was registered as `cobblestone`. `StairsBlock` defaults to `FACING=NORTH`, `HALF=BOTTOM`, `SHAPE=STRAIGHT`.

1.10.2 Ornithe Feather: `decompiled_minecraft/1.10.2/ornithe-feather/net/minecraft/block/Block.java:727-733,868`; `StairsBlock.java:29-56,67-89`; `Entity.java:165-170`.
1.11, 1.11.1 and 1.11.2 Ornithe Feather: matching `Block.java` registration and `StairsBlock.java:28-55,65-100`; `Entity.java` retains the ordinary 0.6-wide, 1.8-high player box defaults. The moved state is this same straight stair. The 1.11 collision callback resolves virtual shape from neighboring stairs; an isolated fixture stays straight. 1.11.1/1.11.2 pass `forceShape=true`, using the stored straight state directly.

The old mover's coarse shape uses inherited `Block.getShape`, which is a full cube. Its old `StairsBlock.addCollisions` still supplies actual ordinary collision boxes: bottom base `[0,0,0]-[1,0.5,1]` and north tread `[0,0.5,0]-[1,1,0.5]`. Thus a 0.6-wide player box with `feetY=0.5`, `minZ=0.65` is not interpenetrating the stationary bottom-north stair: the base only face-touches vertically and the tread ends before the player's Z interval. The player footprint overlaps the lower-half footprint positively; the fixture's stated support condition supplies support at the relevant pre-step position. At the prior moving progress `0.5`, the extension box is still behind `minX=0.65`, so the player is not inside the moving stair's full-cube outline either.

## Direct request in 1.10.2

For the second east half-step, progress changes from `0.5` to `1.0`; `getAmountExtended` gives offsets `-0.5` and `0`. Since the mover's `getShape` uses the full-cube outline and unions those positions, the east face reaches X `1`. It selects the player box beginning at X `0.65` and requests `1 - 0.65 + 0.01 = +0.36` on X through ordinary `Entity.move`.

Sources: `MovingBlockEntity.java:76-105,91-151,166-183`; `Block.java:277-279`; `StairsBlock.java:67-89`.

## New per-box sample in 1.11, 1.11.1 and 1.11.2

On the matching second half-step, the caller has `progressDelta=0.5` while the old progress is `0.5`. It obtains the straight stair's actual component boxes and translates them by the old extension offset `-0.5` before constructing each east movement area. For a player box with X/Z minima `0.65`, feet Y `0.5`, and ordinary 0.6 width:

- The lower-base movement area has Y `[0,0.5]`; it only face-touches the player's feet, and `Box.intersects` uses strict inequalities.
- The north-tread movement area has Z `[0,0.5]`; it misses the player's Z interval beginning at `0.65`.

Neither per-box sample intersects, so the accumulated movement amount stays zero and `if (!(e <= 0.0))` skips the piston `Entity.move` call. The broad query still includes the player: `getBounds` is the unit stair bound, moved to X `[-0.5,0.5]`; its east movement area reaches X `1`, so their union overlaps the player's box. This is a per-box applicability difference, not absence from the query.

Sources: for each of 1.11/1.11.1/1.11.2, `MovingBlockEntity.java:116-169,171-229,273-290`; `StairsBlock.java:28-55,65-110`; `Box.java:245-252` (strict overlap). The component-box definitions are the same in all three releases. The 1.11 `addCollisions` overload resolves virtual properties; with no adjacent stairs the state remains straight. 1.11.1 and 1.11.2 add a `forceShape` argument, and the mover supplies `true`; these two files are byte-for-byte identical.
