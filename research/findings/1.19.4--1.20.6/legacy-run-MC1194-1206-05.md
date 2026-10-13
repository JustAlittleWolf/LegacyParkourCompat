# MC1194-1206-05: The scale attribute changes player collision dimensions

- **1.19.4**, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`; line 541.
- **1.20.6**, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 578-583.

## Difference

For ordinary players with the default scale `1.0`, 1.20.6 returns the same pose width and height as 1.19.4.

When 1.20.6's synchronized `SCALE` attribute has a nondefault value, `getDimensions(Pose)` scales the player's width, height and eye height, and the entity refreshes its dimensions. 1.19.4 player in 1.19.4 always resolves to scale `1.0`; its `getScale()` has only the non-baby branch for a player.

## Movement meaning

The 1.20.6 scale attribute is registered in the player living-attribute supplier and is consumed by the dimensions used for the player bounding box and eye checks.

Source-proven: nondefault 1.20.6 scale changes player collision/eye dimensions, and the normal player tick refreshes those dimensions. 1.19.4 resulting collision, support, step or fluid trajectory has not been tested.

This is a modern-only attribute mechanic and must remain separate from historical behavior for maps from before it existed.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.19.4--1.20.6/findings/MC1194-1206-05.md).
