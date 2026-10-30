# Correction supplement 2: narrow water-current witness

This supplement narrows the candidate in the following immutable artifacts; none was edited:

- `coverage.md` SHA-256: `2cb4040f8e2aad534d2ca9a60380a97b8e1bd28ddf7dfed903b9daf1b24bf888`
- `water-contact-and-current-query.md` SHA-256: `391d448311d3719bc42776861da02c59f34221e7fbaec3d6200ad70670cede70`
- `correction-supplement.md` SHA-256: `1895e10caf739762a66323bcd901ddac25e391b3949cfc5b7518744671e72b71`
- Independent water-current audit SHA-256: `8fb1d972e480c92f07f3f06bc65bec2e99233a55d41d860af41ebab2d6ef3eed`

## Bounds and gates

1.12.2 `Entity.checkWaterCollisions` (`net/minecraft/entity/Entity.java:948-963`) queries `getShape().grown(0.0, -0.4F, 0.0).contract(0.001)`. `Box.grown` subtracts `dy` from `minY` and adds it to `maxY` (`net/minecraft/util/math/Box.java:132-140`), so this shrinks the box at both vertical ends. 1.13.2 `Entity.m_69693160(Tag<Fluid>)` (`net/minecraft/entity/Entity.java:2510-2518`) queries `getShape().contract(0.001)`; `Box.contract` delegates to `expand(-amount)` (`Box.java:222-228`). Thus, the scan volumes differ vertically; the current-impulse comparison below is limited to a witness where the same relevant water cell is included by both scans and both current outputs are nonzero.

The player current gate is `PlayerEntity.hasLiquidCollision() == !abilities.flying` in both releases (`1.12.2 net/minecraft/entity/living/player/PlayerEntity.java:1732-1735`; `1.13.2 net/minecraft/entity/living/player/PlayerEntity.java:1826-1828`). The impulse witness therefore requires a non-flying player. Flying players do not pass either release's current-application gate.

## Single-cell witness

Let the one relevant water cell have block Y `p`, surface `p + 8/9`, and let the 1.13.2 contracted query box bottom be `p + 0.55`. Its measured depth is `8/9 - 0.55 = 0.338888...`, below `0.4`. Assume the cell has nonzero flow and is the only current-contributing water cell in the query ranges. For a common uncontracted player-box bottom `p + 0.549`, the 1.13.2 contraction yields `p + 0.55`; the 1.12.2 `-0.4F` grow followed by `0.001` contraction yields lower bound `p + 0.95`. Both scan starts therefore floor to row `p`, and with the player's upper bound above `p+1`, both loops enumerate that same cell. The 1.12.2 water surface formula gives `p + 8/9` for level `0` (`LiquidBlock.java:50-56`). Assume the cell's flow vector is nonzero; the old liquid provider returns its normalized direction (`LiquidBlock.java:139-180`), and `World.applyLiquidDrag` normalizes the one-cell aggregate before the `0.014` velocity increment (`World.java:1594-1618`). In 1.13.2 the fluid provider reports its height from its level (`FlowingFluid.java:448-450`) and returns normalized per-cell flow (`FlowingFluid.java:54-98`); the entity query scales the single vector by `d=0.338888...` before aggregation (`Entity.java:2537-2545`) and the player path does not normalize the result (`Entity.java:2554-2566`). The supplied audit identifies the matching one-cell surface/box witness; thus the current impulse magnitude is scaled by `d` relative to the old normalized one.

For multiple contributing cells, the prior correction still applies: `d` is the running maximum updated at `Entity.java:2537` before each cell's scale at `2540-2544`, so per-cell scale factors and the aggregate can depend on scan order. This supplement claims only the single-cell witness above. The bounds differ, so no normalized-versus-scaled comparison is claimed unless the compared current-producing cell is present in both scan ranges.

## Source hashes

- 1.12.2 `net/minecraft/entity/Entity.java`: `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`
- 1.12.2 `net/minecraft/world/World.java`: `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`
- 1.12.2 `net/minecraft/entity/living/player/PlayerEntity.java`: `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`
- 1.12.2 `net/minecraft/util/math/Box.java`: `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a`
- 1.12.2 `net/minecraft/block/LiquidBlock.java`: `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260`
- 1.13.2 `net/minecraft/entity/Entity.java`: `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`
- 1.13.2 `net/minecraft/entity/living/player/PlayerEntity.java`: `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`
- 1.13.2 `net/minecraft/util/math/Box.java`: `0f573d00cc10386f94cf51671c62815979973053c9e67a806e9fee98e55a4d8d`
- 1.13.2 `net/minecraft/fluid/FlowingFluid.java`: `2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253`
