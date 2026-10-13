# F-S5-WATER-CURRENT: weak water currents receive a minimum player push

- **1.15.2**, mojmap: `net/minecraft/world/entity/Entity.java`; lines 2593-2652.
- **1.16.5**, mojmap: `net/minecraft/world/entity/Entity.java`; lines 2641-2705.

## Difference

The flow-vector scan, partial-height scaling, averaging, and Player normalization exclusion correspond. 1.15.2 always adds the computed water-current vector at scale 0.014. 1.16.5 applies the same scale and then enforces a minimum horizontal current-push magnitude under the stated low-speed guard. 1.15.2 player already moving slowly in x/z can therefore receive a larger push in 1.16.5 for a nonzero but weak current.

Both versions suppress fluid pushing for flying players through the respective Player override.

## Movement meaning

1.15.2 `Entity.updateWaterState()` calls `updateInWaterState()`, which reaches `checkAndHandleWater(WATER)`; 1.16.5 `Entity.updateWaterState()` reaches `updateInWaterStateAndDoWaterCurrentPushing()`, which calls `updateFluidHeightAndDoFluidPushing(WATER, 0.014)`.

Entity base ticking reaches these state-update paths, and LivingEntity also invokes the corresponding update on the movement/fall-damage path when not in water.

For a Player, the override permits the push when `abilities.flying` is false.

The changed lower bound reads the velocity present immediately before adding the current push.

The source proves a larger delta-velocity addition in 1.16.5 when the flow sum is nonzero, the scaled vector is below the minimum, and both current horizontal velocity components satisfy the low-speed guard.

Net displacement depends on subsequent travel/collision and is not claimed.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S5-WATER-CURRENT.md).
