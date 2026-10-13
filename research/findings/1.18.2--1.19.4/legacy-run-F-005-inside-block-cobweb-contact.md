# F-005: Narrower inside-block inset detects shallow cobweb overlap

- **1.18.2**, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/Entity.java`; lines 872–886.
- **1.19.4**, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/Entity.java`; lines 896–910.

## Difference

After a move, the entity invokes `tryCheckInsideBlocks()`. 1.18.2's `0.001` inset can exclude a block whose only intersection with an entity-box face is shallower than `0.001`; 1.19.4's `1.0E-7` inset includes such a cell when the overlap exceeds that smaller epsilon.

For example, if the box's max X is `1.0005`, the 1.18.2 upper sample is `0.9995` (cell 0), while the 1.19.4 upper sample is `1.0004999` (cell 1).

Both versions' cobwebs are non-colliding and share the same `entityInside` implementation.

In that shallow-overlap case 1.19.4 dispatches the cobweb callback and stores `(0.25, 0.05F, 0.25)` as the stuck movement multiplier; 1.18.2 does not dispatch it from this query.

## Movement meaning

`LocalPlayer` movement calls `Entity.move()`; after position/collision updates that method calls `tryCheckInsideBlocks()` before its final block-speed-factor multiplication.

The changed bounds decide which block callbacks run.

In 1.19.4, `WebBlock.entityInside()` sets the stuck multiplier; the next `Entity.move()` applies it before collision resolution.

Other movement-relevant `entityInside` callbacks share this changed query and remain queued individually (including honey, bubble columns, powder snow and sweet-berry bush).

Source proves a larger region in which 1.19.4 recognizes inside-block contact.

Under the stated web overlap precondition, 1.19.4 schedules the slowdown multiplier and 1.18.2 does not.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.18.2--1.19.4/findings/F-005-inside-block-cobweb-contact.md).
