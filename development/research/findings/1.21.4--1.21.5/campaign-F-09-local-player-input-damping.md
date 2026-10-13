# F-09: Controlled local-player input skips the base 0.98 damping in 1.21.5

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, moveRelative 1424-1427; moveRelative 1425-1441.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, moveRelative 1453-1456.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2727-2735.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2712-2717; applyInput 2806-2809.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 643-809; serverAiStep 607-617; isControlledCamera 620-622.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 830; applyInput 608-621.

Difference: For the normal controlled-camera local-player path, 1.21.4 attenuates each current input axis by `0.98F` before travel. 1.21.5 dispatches to the `LocalPlayer` override, which sets the axes but bypasses the base method containing those multipliers. The player's item-use, sneaking, float normalization and square-movement modifiers still apply; the missing factor is specifically the shared `0.98F` attenuation. If `isControlledCamera()` is false, the override calls `super.applyInput()`, so this finding does not apply to that branch.

Movement meaning: `LocalPlayer.aiStep()` reaches `LivingEntity.aiStep()`. For a non-immobile controlled-camera local player with nonzero input surviving the other modifiers, 1.21.4 supplies the axes through `serverAiStep()` and then applies `0.98F`; 1.21.5 supplies them through `applyInput()` but bypasses the base multiplier. 1.21.5's non-controlled-camera branch calls `super.applyInput()` and retains the multiplier; the immobile path zeros the axes after input application, so it is outside this finding's applicability. The resulting axes enter the shared player-relative movement-vector consumer before travel. The full travel branch and modifier inventories remain separate, open coverage. For nonzero controlled-camera input axes, 1.21.5 passes values to travel without the extra `0.98F` multiplication present in 1.21.4, after applying its other input transforms. The source establishes this per-tick input difference but not a measured trajectory or downstream persistence.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-09-local-player-input-damping.md).
