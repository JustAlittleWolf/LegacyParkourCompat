# Water-flow admission drops the exact upper boundary cell

1.8.9, Ornithe Feather: `net/minecraft/entity/Entity.java`, `checkWaterCollisions`, lines 746–759; `net/minecraft/world/World.java`, `applyLiquidDrag`, lines 1486–1527.

1.9, Ornithe Feather: `net/minecraft/entity/Entity.java`, `checkWaterCollisions`, lines 845–861; `net/minecraft/world/World.java`, `applyLiquidDrag`, lines 1554–1596.

Difference: The player water-contact call contracts its box by 0.001 per face on both sides (`Box.contract(double,double,double)` in 1.8.9; uniform `Box.contract(double)` delegates to `expand(-amount)` and `grown` in 1.9). In 1.8.9, `applyLiquidDrag` sets each exclusive maximum cell bound to `floor(bounds.max + 1.0)`; in 1.9 it uses `ceil(bounds.max)`. If a contracted maximum coordinate is exactly integer `N`, the older loop includes coordinate `N`, while the newer loop excludes it. Vertical liquid-height admission remains `l >= p + 1 - LiquidBlock.getHeightLoss(level)`.

Movement meaning: For a non-flying player whose contracted upper Y bound is `N`, with X/Z inside a level-1 water block at Y=`N`, the old bound admits the cell because `l=N+1` and its liquid top is `N+1-((1+1)/9.0F)`; the new bound does not scan it because `l=N`. A neighboring level-0 source can make that level-1 cell's flow vector nonzero. The old path then includes that vector in the accumulated flow and adds its normalized components times `0.014` to player velocity; the new path omits that cell's contribution. `PlayerEntity.hasLiquidCollision()` is true for a non-flying player on both sides.
