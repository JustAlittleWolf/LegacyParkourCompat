# Slow input sprint-start restriction

1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/Entity.java`, isVisuallyCrawling 2338-2340; updateInWaterStateAndDoFluidPushing 1295-1301.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, updateInWaterStateAndDoFluidPushing 1316-1322.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, hasEffect 926-928; startUsingItem 3127-3138; aiStep 2680-2808.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, hasEffect 926-928; startUsingItem 3138-3149; aiStep 2691-2819.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 638-739; isCrouching 593-598; aiStep 638-800.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 643-753.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/player/Player.java`, tryToStartFallFlying 1525-1533.

Difference: 1.21.4 rejects slow-moving local-player sprint start unless underwater; 1.21.3 lacks that additional start gate.

Movement meaning: Existing crouch/crawl input state can select a different sprint flag for the following travel. This start predicate is independently resolved from stopping an existing sprint.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.3--1.21.4/findings/F-S1-02-sprint-policy-gates.md).
