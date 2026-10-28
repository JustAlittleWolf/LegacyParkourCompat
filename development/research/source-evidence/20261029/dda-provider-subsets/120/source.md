# End Portal Frame old ID 120 provider and alias closure

## Scope and status

This source-only closure maps 1.8.9 registry ID 120, `end_portal_frame`, to 26.2 `minecraft:end_portal_frame` and compares the collision provider, state aliases, and mutable shared-bounds lifecycle. It is not an independent review of a movement finding, does not prove DDA traversal equivalence, and makes no runtime trajectory claim. Canonical source and ready/provenance identities are bound in `sources.sha256`.

## Legacy registry and state mapping

In 1.8.9 `Block.java:1078-1086` registers numeric block ID 120 as `end_portal_frame` with an `EndPortalFrameBlock`. The class declares `FACING` over horizontal directions and Boolean `EYE` (`EndPortalFrameBlock.java:18-20`), with default `NORTH,false` (`21-24`). Its metadata decoder is total over the four-bit input: it takes `metadata & 3` for facing and `(metadata & 4) != 0` for eye (`71-73`). Bits 3 therefore alias the corresponding low state: metadata 8–15 decode to the same eight states as 0–7. In old `Direction`, horizontal data values are SOUTH=0, WEST=1, NORTH=2, EAST=3 (`Direction.java:11-17,24-30`); current `Direction` uses the same horizontal `data2d` values and `from2DDataValue` mapping (`Direction.java:32-37,50-64,287-289`).

26.2 registers `Blocks.END_PORTAL_FRAME` through `BlockItemIds.END_PORTAL_FRAME`, whose key is `end_portal_frame` (`Blocks.java:2601-2611`; `references/BlockItemIds.java:397`). `EndPortalFrameBlock` aliases `FACING` to the same horizontal property and `HAS_EYE` to `BlockStateProperties.EYE` (`EndPortalFrameBlock.java:25-27`), defaults to `NORTH,false` (`36-38`), and selects its collision shape by `HAS_EYE` (`46-49`). Its ordinary `BlockBehaviour.Properties` has `hasCollision=true` unless disabled, and this registration does not call `noCollision` (`BlockBehaviour.java:985`; `Blocks.java:2601-2611`). Thus all 16 raw legacy metadata values project to current facing/eye states by the same bit mapping; facing does not affect either collision outline.

## Historical collision provider and shared-bounds lifecycle

The 1.8.9 `Block` constructor initializes the singleton block's six mutable `min/max` fields to a full unit cube (`Block.java:198-207`). `EndPortalFrameBlock` does not replace that initial geometry in its constructor; it only establishes its default state. Its explicit `resetShape` writes the base frame from `(0,0,0)` through `(1,0.8125,1)` (`EndPortalFrameBlock.java:32-35`).

Its `addCollisions` overrides the ordinary provider (`EndPortalFrameBlock.java:37-43`): it first resets to the base 13/16-high frame, delegates to `Block.addCollisions` using those shared fields, then, when the current world state at the position has an eye, writes and delegates a second box from `(0.3125,0.8125,0.3125)` through `(0.6875,1,0.6875)`. It unconditionally calls `resetShape()` at normal method completion. The base `Block.addCollisions` asks `getCollisionShape` for a world-positioned box and appends it only if it intersects the query (`Block.java:339-350`). Consequently, the actual old collision output for an eye-bearing frame can contain two separate boxes, but the singleton's residual shared bounds after any completed `addCollisions` call are always the 13/16-high base frame. The old `FACING` state does not affect these bounds.

The inherited `Block.rayTrace` invokes virtual `updateShape` and then reads the singleton min/max planes (`Block.java:456-460`). `EndPortalFrameBlock` has no `updateShape` override; the base implementation is empty (`Block.java:596-597`). So a ray trace of this block reads whatever bounds were last left in that singleton: the constructor's full cube before its first collision-provider call, then the base frame after a completed `addCollisions` call. A ray query itself does not write this class's bounds. The accepted camera-tick source finding places `GameRenderer.pick` before world entity movement in the ordinary 1.8.9 tick, so this residual can be read by camera raytrace before that tick's movement provider refreshes it. This is a conditional early-reader/termination dependency for the DDA observer: if the frame hit/miss changes, it can alter whether later ray cells are visited; it is not evidence that this block itself is a camera-path mutable-bounds writer. It also means source code must not treat a ray miss as proof that this provider has no effect on the later visitation sequence.

## 26.2 state-based geometry

26.2 builds `SHAPE_EMPTY` as a full-footprint column from Y=0 to 13/16 and `SHAPE_FULL` as its OR with a centered 8/16-wide column from Y=13/16 to 1 (`EndPortalFrameBlock.java:29-31`; `Block.column` and coordinate scaling in `Block.java:152-162,177-184`). `getShape` selects the state-based shape by `HAS_EYE` (`EndPortalFrameBlock.java:46-49`). The base collision provider returns the state's collision shape when `hasCollision` is true (`BlockBehaviour.java:323-328`), so modern geometry is immutable with respect to a shared mutable block-bounds cache and available from the queried state.

There is a concrete provider-shape difference for eye=true. The old optional top box spans X/Z 5/16–11/16 (6/16 width); modern `Block.column(8.0,13.0,16.0)` spans X/Z 4/16–12/16 (8/16 width). The base frame geometry is the same 13/16 height. Modern returns a union shape selected from the state; old `addCollisions` submits the base and eye top as separate boxes and restores shared bounds after. Therefore a direct modern-shape substitution is not exact for eye=true, and it cannot reproduce the old rayTrace residual cache state.

## DDA adapter boundary

For a source-backed current alias, use key `minecraft:end_portal_frame` and map `oldMetadata & 3` to the identical current horizontal direction value, `oldMetadata & 4` to `HAS_EYE`; metadata bit 3 is ignored. All four facing directions and both eye values are valid. Collision bounds depend on eye only.

For old collision-provider emulation, preserve the old two-box output and method lifecycle if that output is consumed. For any observer emulation that reads `Block.getCollisionShape` after camera ray visitation, the old frame's geometry depends on whether an `addCollisions` call has already reset the singleton; the camera `rayTrace` does not refresh it. The current state-based shape alone cannot express that historic mutable lifecycle. The End Portal Frame is an early DDA reader, not a newly demonstrated writer like the accepted Anvil/Chest examples. No claim is made that this source closure alone explains an observed cache mismatch or closes old-versus-current cell visitation. Eye-bearing collision-box width also remains a real provider difference.

## Source references and identities

- 1.8.9 `Block.java:198-207,292-298,339-350,456-460,596-597,1078-1086`; `EndPortalFrameBlock.java:18-24,32-43,69-84`; `Direction.java:11-30`.
- 26.2 `EndPortalFrameBlock.java:25-49,77-82`; `Blocks.java:2601-2611`; `references/BlockItemIds.java:397`; `Block.java:152-162,177-184`; `BlockBehaviour.java:323-328,985`; `Direction.java:32-64,287-289`.
- The accepted `.native-source/20261028/collision-dependencies/camera-tick-raytrace/camera-tick-raytrace.md` establishes the bounded 1.8.9 camera-pick-before-movement ordering for its listed providers. This report does not broaden that accepted writer finding to End Portal Frame.

No product code, builds, tests, or runtime actions were used.
