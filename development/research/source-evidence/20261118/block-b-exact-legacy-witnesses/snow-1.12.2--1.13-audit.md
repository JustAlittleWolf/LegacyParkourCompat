# Independent source audit: snow layer-one empty collision claim

Status: COMPLETED

## Immutable bindings

- Candidate: `b-audit-snow-layer-one/snow-layer-one-empty-collision.md`, SHA-256 `a6a049082fc38e0f642654a072c0f255049ba4b20a8ecb28066a91b3cadf2a99`.
- Companion report: `b-audit-snow-layer-one/report.md`, SHA-256 `409ebd43b9f86e49d4c4b361323d1161d7afd6b067224bf8d7c6765a3f2ca845`.
- Exact source identities: `decompiled_minecraft/1.12.2/ornithe-feather.sources.sha256`, SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; `decompiled_minecraft/1.13/ornithe-feather.sources.sha256`, SHA-256 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`.
- Both ready markers state status `ready`, exact requested IDs 1.12.2 / 1.13, mapping `ornithe-feather`, and Feather Gen2 build.2 mapping archives. 1.12.2 verification records 2,050 source files identical to the prepared source tree; 1.13 provenance records `net.ornithemc:feather-gen2:1.13+build.2` and the matching source manifest. Relevant intact source bodies match each manifest.

## Claim verdicts

| Claim | Verdict | Source basis |
|---|---|---|
| 1.12.2 layer-one shape is a non-null zero-height box | CONFIRMED | `SnowLayerBlock.SHAPES[0]` is `new Box(0,0,0,1,0,1)`. `getCollisionShape` computes `i = LAYERS - 1`, then returns a new `Box` with `maxY = i * 0.125F`; for LAYERS=1 this is zero. It is not `Block.EMPTY_BLOCK_SHAPE`. |
| Old broad phase retains it when movement query straddles the plane | CONFIRMED | `Block.addCollision` rejects only the sentinel empty-box identity, moves the shape to block position, and adds it when `shape.intersects(box)`. `Box.intersects` uses strict inequalities on both Y ends; a query with minY < plane and maxY > plane intersects `[plane,plane]`. |
| Old descending Y clamp treats the plane as a stop | CONFIRMED | `Box.intersectY` for negative `limit` checks player `box.minY >= this.maxY`, calculates `this.maxY - box.minY`, and adopts it when greater than the existing negative limit. `Entity.move` gathers the expanded requested movement box, loops through returned boxes applying `intersectY`, then moves by the clamped Y. |
| Layer one can legally occupy the cell above same-block LAYERS=8 | CONFIRMED | In both 1.12.2 and 1.13, `SnowLayerBlock.canBePlaced` explicitly returns true for `block == this && lower.LAYERS == 8`, subject to the surrounding ice/barrier exclusions. The survival/update path consults this placement rule. |
| The upper layer's old plane is 1/8 above the lower eight-layer collision top | CONFIRMED | Old layer 8 collision maxY is `(8-1)*0.125F = 0.875`; upper layer 1 has its zero-height plane at the next block's base, Y=1. The old `Entity.move` Y clipping therefore stops a descending player at the higher plane if the query includes both surfaces. |
| The witness applies to a player descending through normal client movement | CONFIRMED, source-level | `PlayerEntity` extends `LivingEntity` in both releases. Normal `LivingEntity` movement invokes `move(MoverType.SELF, velocityX, velocityY, velocityZ)`; the move body handles negative Y as above. This establishes a reachable client player path for a falling/downward movement crossing the plane, without claiming a full trajectory comparison. |
| 1.13 selects an empty collision shape for LAYERS=1 | CONFIRMED | `SnowLayerBlock.SHAPES[0]` is `VoxelShapes.empty()`, and `getCollisionShape` returns `SHAPES[LAYERS - 1]`. `Entity.move` passes its movement query to `WorldView.getCollisions`; the voxel collision path aggregates block collision shapes, so an empty shape contributes no collision interval. |

## Conclusion and limits

The bounded 1.12.2 → 1.13 source claim is accepted: a legal one-layer snow block above an eight-layer snow block presents an old degenerate collision plane at Y=1, while the lower block's collision top is Y=0.875; the old collector and Y-axis clip can stop a descending player on the upper plane. In 1.13 the upper collision selector returns empty, so the player can descend to the lower support surface. This is a source-derived local vertical displacement of 1/8 block for the described fixed state. It does not establish complete tick-trajectory parity, runtime parity, or an exact patch introduction boundary within the interval.
