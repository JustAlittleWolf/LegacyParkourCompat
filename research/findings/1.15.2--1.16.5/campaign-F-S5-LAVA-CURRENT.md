# F-S5-LAVA-CURRENT: non-flying players receive lava-current force

1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/entity/Entity.java`, `baseTick, updateWaterState`, lines 361–363, 943–947.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/world/entity/Entity.java`, `baseTick, updateInWaterStateAndDoFluidPushing`, lines 380, 957–979.

Difference: 1.15.2's entity base tick updates water and its current; the inspected path does not update or push from lava. 1.16.5's entity base tick updates fluid heights and pushes from both water and lava. 1.15.2 non-flying Player satisfies 1.16.5's `isPushedByFluid()` guard, so the lava flow scan can add a dimension-scaled current vector to its delta movement. Flying players remain excluded by the Player override.

Movement meaning: `Entity.baseTick()` is called through the player tick superclass path.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S5-LAVA-CURRENT.md).
