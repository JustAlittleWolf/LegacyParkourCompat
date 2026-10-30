# Stacked fluid height

1.13.2, Ornithe Feather Gen2: `net/minecraft/fluid/FlowingFluid.java`, `getHeight(FluidState)`, lines 447–450 returns `state.getLevel() / 9.0F`; entity contact scan uses it at `net/minecraft/entity/Entity.java:2532–2537` and eye submersion at `:1047–1050`.
1.14.4, Ornithe Feather Gen2: `net/minecraft/fluid/FlowingFluid.java`, lines 442–449 returns `1.0F` if the fluid directly above is the same fluid, otherwise the level-derived height; `FluidState.getHeight(world,pos)` routes through that overload. Entity contact uses it at `net/minecraft/entity/Entity.java:2586–2591`, and eye submersion at `:1044–1052`.
Difference: In a vertically stacked same-fluid column, the newer fluid surface is treated as height `1.0F` for the lower cell; the older surface is `level / 9.0F` (source level is 8, giving `8/9`). Otherwise the level-derived height is retained.
Movement meaning: A player whose AABB lower edge or eye lies in the upper interval above `8/9` and below `1.0` of a stacked water cell can receive different in-water/submerged state, affecting water travel and swimming admission. Exact ordinary-player witness remains open.
