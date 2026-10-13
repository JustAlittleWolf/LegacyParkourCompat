# F-06: The 1.11.2 query includes a lower-row moving-fence collision

- **1.10.2**, ornithe-feather: `1.10.2/ornithe-feather/net/minecraft/world/World.java`.
- **1.11.2**, ornithe-feather: `1.11.2/ornithe-feather/net/minecraft/world/World.java`.

## Difference

Both piston paths can place a movable fence state in a `MOVING_BLOCK` cell: `PistonBaseBlock.canMoveBlock` permits the registered oak fence when its ordinary border/height guards pass, and the push path stores the moved state in a `MovingBlockEntity`.

The oak fence uses `Material.WOOD` (whose default piston behavior is `NORMAL`), has no block entity, and has a collision post from local x/z `0.375` to `0.625` and local y `0.0` to `1.5`.

Use a moving-block cell at `(x,y,z)` with an extending piston facing WEST at progress `0.5`, and a player query box `[x+1.0,x+1.6] × [y+1.3,y+3.1] × [z+0.2,z+0.8]`.

The moved fence's progress offset is `WEST.offsetX * (0.5F - 1.0F) = +0.5`.

Its center collision post therefore occupies `[x+0.875,x+1.125] × [y,y+1.5] × [z+0.375,z+0.625]`, which strictly intersects the query box.

For that query, the moving-block cell is on the x perimeter (`o == i`) but its z coordinate is interior. 1.10.2's scan has `q == 1` and rejects the lower padded y row `r == k` with `q <= 0 || r != k && r != l - 1`. 1.11.2's shared helper reaches the same cell and admits that row because it rejects only the upper row, `q != l - 1`. 1.11.2 then calls the moving entity's collision provider, which translates the fence collision boxes by progress and appends the intersecting box.

In 1.10.2 the cell is not visited, so that candidate contributes no block collision.

This is independent of the retracting piston shape change in F-05.

## Movement meaning

The source path is piston movement eligibility and moved-state storage -> `World.getCollisions` block-state dispatch -> `MovingBlock`/`MovingBlockEntity` collision boxes -> the list consumed by `Entity.move`. 1.10.2 locally controlled player reaches inherited `Entity.move` through the living-player travel path; the collision list is therefore a direct player movement input.

The pair's S4-piston and S5-fence slices cover the paired piston state path and fence post geometry. `Material.WOOD` has the default `NORMAL` move behavior, the base `hasBlockEntity` flag defaults false, and `canMoveBlock` admits the fence under valid height/world-border conditions.

Source proves that 1.11.2 supplies this collision box to the player query while 1.10.2 skips the candidate cell.

If the player is moving into the box, it can constrain the movement clipping result; no final position or observed trajectory is claimed.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.10.2--1.11.2/findings/F-06-lower-row-moving-fence-collision.md).
