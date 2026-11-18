# Fall-distance accumulation precision

1.18.1, Mojmap. `decompiled_minecraft/1.18.1/mojmap/net/minecraft/world/entity/Entity.java`, `checkFallDamage(double, boolean, BlockState, BlockPos)`, lines 979–981.

1.18.2, Mojmap. `decompiled_minecraft/1.18.2/mojmap/net/minecraft/world/entity/Entity.java`, `checkFallDamage(double, boolean, BlockState, BlockPos)`, lines 989–991.

When the player is airborne and the resolved Y displacement is negative, 1.18.1 subtracts the double displacement from the promoted float fall distance and narrows the result to float. In 1.18.2 the displacement narrows to float before subtraction.

Starting with `0.44999998807907104F` and a resolved displacement of `-0.15D` yields `0.5999999642372131F` in 1.18.1 and `0.6000000238418579F` in 1.18.2. On the next move, `Player.isAboveGround()` tests whether the accumulated value is below the player's `0.6F` step height. With shift held and the corresponding support geometry, 1.18.1 can run the edge-backoff loop and reduce the player's horizontal movement while 1.18.2 skips that loop.
