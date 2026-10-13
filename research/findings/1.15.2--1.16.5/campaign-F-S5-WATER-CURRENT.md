# F-S5-WATER-CURRENT: weak water currents receive a minimum player push

1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/entity/Entity.java`, `checkAndHandleWater`, lines 2593–2652.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/world/entity/Entity.java`, `updateFluidHeightAndDoFluidPushing`, lines 2641–2705.

Difference: The flow-vector scan, partial-height scaling, averaging, and Player normalization exclusion correspond. 1.15.2 always adds the computed water-current vector at scale 0.014. 1.16.5 applies the same scale and then enforces a minimum horizontal current-push magnitude under the stated low-speed guard. A player already moving slowly in x/z can therefore receive a larger push in 1.16.5 for a nonzero but weak current. Both versions suppress fluid pushing for flying players through the respective Player override.

Movement meaning: 1.15.2 `Entity.updateWaterState()` calls `updateInWaterState()`, which reaches `checkAndHandleWater(WATER)`; 1.16.5 `Entity.updateWaterState()` reaches `updateInWaterStateAndDoWaterCurrentPushing()`, which calls `updateFluidHeightAndDoFluidPushing(WATER, 0.014)`.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S5-WATER-CURRENT.md).
