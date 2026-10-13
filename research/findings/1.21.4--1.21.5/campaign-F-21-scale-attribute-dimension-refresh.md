# F-21: Player scale attribute changes refresh dimensions only in 1.21.5

- **1.21.4**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-21-scale-attribute-dimension-refresh.md).
- **1.21.5**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-21-scale-attribute-dimension-refresh.md).

## Difference

Both releases register `Attributes.SCALE` in `LivingEntity.createLivingAttributes()`, and `Player.createAttributes()` extends that builder. `LivingEntity#getScale()` reads the live attribute value.

The paired `AttributeCommand` can set an entity attribute's base value with `AttributeInstance#setBaseValue`, establishing a vanilla player-reachable mutation path; dirty attributes are processed by `LivingEntity#refreshDirtyAttributes()`, which invokes `onAttributeUpdated`.

In 1.21.4, `LivingEntity#onAttributeUpdated` handles maximum health and absorption only.

In 1.21.5, it additionally calls `refreshDimensions()` when the changed attribute is `Attributes.SCALE`.

That shared refresh stores `getDimensions(currentPose)` and its eye height before updating the bounding box; D36 records the paired writer and its operation order.

Consequently, after a live player scale change is processed while pose remains unchanged, 1.21.5 refreshes the stored dimensions, bounding box and eye height, while 1.21.4 retains the previous stored dimensions and eye height until another refresh path runs.

## Movement meaning

Player attribute builder -> inherited `Attributes.SCALE` registration -> vanilla attribute command base-value update -> dirty-attribute processing -> `LivingEntity#onAttributeUpdated` -> version-specific dimension refresh.

This finding is limited to a scale mutation after the player's dimensions have been established, with no intervening pose or other refresh that would mask the callback difference.

The paired scale declaration, Player attribute-builder inheritance, mutation path, dirty dispatcher, callback and downstream dimensions writer are closed for this condition.

The source proves a difference in when the cached player dimensions and eye height refresh after a scale attribute update.

In 1.21.4, later direct `getScale()` reads return the new attribute while the stored bounding box/eye height can remain at the old scale; in 1.21.5, the dirty callback refreshes them.

Subsequent collision, movement bounds and eye-height fluid sampling can therefore observe different state.

The exact movement consequence depends on the attribute value, timing, pose, world contacts and subsequent movement.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-21-scale-attribute-dimension-refresh.md).
