# 1.8 legal witness supplement: layer-seven support

Status: COMPLETED

This supplement qualifies the original report without changing it. The original report remains bound to SHA-256 `2ac7d724831cce66920fe053a1cd30ba9b3e5b4a04cd0f124d40ead070166a0e`; the independent audit input is `b-audit-snow-one-historical-patches/audit.md`, SHA-256 `71c33aa41b944e4591e97e9a1aa902dbc5f9529446a946a938ea9478a13a0abd`.

## Exact 1.8 setup

In `1.8/ornithe-feather/net/minecraft/block/SnowLayerBlock.java`, the `LAYERS` property is 1–8. `canBePlaced` at lines 74–85 permits same-block support when the block below has `LAYERS == 7`. Therefore place an upper snow cell with `LAYERS=1` at block position `(0,1,0)` directly above same-block snow with `LAYERS=7` at `(0,0,0)`. This is legal in 1.8. It is distinct from the layer-eight arrangement, which the 1.8 predicate rejects.

For the upper state, `getCollisionShape` at lines 40–45 computes local `maxY = (LAYERS - 1) * 0.125F = 0`; the returned box has local `minY=0`, so after translation it is the degenerate plane `[1,1]`. The support snow below has its own collision maxY of `(7-1)/8 = 0.75`, leaving a clear gap below that plane.

## Ordinary local-player Y witness

Use an ordinary non-no-clip player/entity box centered at `x=z=0.5`, with feet at `y=1+1/64` and the normal `0.6` width / `1.8` height set by `Entity` (lines 142–143, 225–231). Its horizontal bounds are `[0.2,0.8]` on both axes, strictly inside the upper snow cell. Call the ordinary inherited `Entity.move(0,-1/32,0)` path (line 370), with no horizontal displacement.

The call at `Entity.java:438` collects against the current box expanded by the requested displacement. `Box.expanded` at lines 31–56 extends `minY` for negative movement, so the query spans from `1 + 1/64 - 1/32 = 1 - 1/64` up to the unchanged player-box top. It strictly straddles plane `y=1`. `World.getCollisions` enumerates the covered block cells; `Block.addCollisions` at lines 292–296 appends the non-null snow box when `shape.intersects(var7)`. `Box.intersects` at lines 153–158 uses strict overlap comparisons, which admit this flat box because `query.minY < 1 < query.maxY` and the horizontal ranges overlap.

At `Entity.java:442`, the collected obstacle is passed to `Box.intersectY` with the current player box and `dy=-1/32`. Its negative branch at `Box.java:120–124` sees the player bottom `1+1/64` at or above obstacle top `1`, calculates `1-(1+1/64)=-1/64`, and clamps the requested displacement from `-1/32` to `-1/64`. The feet stop exactly at `y=1`.

As the distinct empty-box control, if this zero-height box were absent from the collected list, no vertical obstacle would clamp the request: the feet would reach `1+1/64-1/32 = 1-1/64`. The lower support collision top is only `0.75`, so it does not clip this short movement. Thus the witness distinguishes the historical degenerate box from an empty collision result.

## Evidence binding and limit

The consulted exact 1.8 members were rehashed against `1.8/ornithe-feather/.decompile-provenance.json`: `SnowLayerBlock.java` `e68b477b30dfc38924f49859cd9e25722c5bc6ca6d5ee09d681285939ac415b4`; `Block.java` `4a4e6dfde24a135ecf87c359fd98f0380664e4bacaf679815c9016cb1ba0d814`; `World.java` `afbc701ef8b66020bdbb2831fd80683705d20237c68fbf23a4001c020876fc6d`; `Entity.java` `2be081abe1720f89d7ab90f782b8411e71bc5b8a6b53966b49a2380e3c47ffd9`; and `Box.java` `2d270470a11082e1cf9f772c94ecff9ecbf5430be78947dd152e2e72c20bbc52`. Each current file hash matched its embedded manifest entry.

This closes only 1.8 applicability for a legal one-layer-over-layer-seven setup and a concrete ordinary descending Y request. It does not revise the report's exact finding that layer one over layer eight is illegal in 1.8, nor extend the result to other movement branches or mechanics.
