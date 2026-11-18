# Independent audit: 1.8 layer-seven witness supplement

Status: COMPLETED — ACCEPT

## Bound input and scope

Audited only `b-source-snow-one-historical-patches/snow-one-1.8-legal-witness-supplement.md`, input SHA-256 `65d9df9d3d62c3c238e495302023a3c8d59ea362d53164979508ea2cbd50e085`. Independently traced exact 1.8 `ornithe-feather` source for the layer-seven placement predicate, upper layer-one collision box, broad-phase block enumeration, strict block collision collection, and ordinary entity vertical clip. No other version, product code, history, wiki, backlog, other lane, gameplay or tests were inspected.

## Verdict: ACCEPT

The concrete witness is source-consistent and legal. In `SnowLayerBlock.canBePlaced`, an upper snow cell is accepted above the same block only when the lower cell has `LAYERS == 7`; layer 7 is within the state property's 1–8 range. At upper cell `(0,1,0)` over layer 7 at `(0,0,0)`, the upper collision box has `minY=maxY=1`, while the lower layer's collision top is `0.75`. The layer-eight arrangement remains rejected because the same-block comparison is equality to 7 and snow is neither solid-rendered nor movement-blocking.

The movement arithmetic checks out. A normal-width/height entity box centered at `(0.5, 0.5)` horizontally and with feet at `1+1/64` has horizontal bounds `[0.2,0.8]`. For `move(0,-1/32,0)`, `Entity.move` queries the AABB expanded downward to `1-1/64`. The query also extends above plane 1 and overlaps it horizontally. `World.getCollisions` enumerates the upper cell, `Block.addCollisions` retains the non-null snow box via strict `intersects`, and `Box.intersectY` changes `-1/32` to `1-(1+1/64)=-1/64`. The resulting feet position is exactly 1. Without that collected box, the request would end at `1-1/64`; the lower support top at `0.75` cannot affect this short request. This is a concrete reachable descending clip through the ordinary inherited `Entity.move` path, not just a shape or placement possibility.

## Evidence checks

All five cited 1.8 members were rehashed and matched their embedded `.decompile-provenance.json` entries: `SnowLayerBlock.java` `e68b477b30dfc38924f49859cd9e25722c5bc6ca6d5ee09d681285939ac415b4`; `Block.java` `4a4e6dfde24a135ecf87c359fd98f0380664e4bacaf679815c9016cb1ba0d814`; `World.java` `afbc701ef8b66020bdbb2831fd80683705d20237c68fbf23a4001c020876fc6d`; `Entity.java` `2be081abe1720f89d7ab90f782b8411e71bc5b8a6b53966b49a2380e3c47ffd9`; `Box.java` `2d270470a11082e1cf9f772c94ecff9ecbf5430be78947dd152e2e72c20bbc52`.

The supplement's relevant locations and descriptions agree with source: snow placement and shape at `SnowLayerBlock.java:40–45,74–85`; movement query and Y clip at `Entity.java:438,442`; strict collector at `Block.java:292–296`; broad-phase cell enumeration at `World.java:873–901`; expansion and strict overlap at `Box.java:31–56,153–158`; negative Y clamp at `Box.java:113–128`. No correction is required for this narrow supplement. Its explicit limit to 1.8 and to this witness is appropriate.
