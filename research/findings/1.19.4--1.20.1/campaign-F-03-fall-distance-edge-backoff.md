

1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel 1983-1991.
1.20.1, mojmap: `1.20.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2562-2567.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/player/Player.java`, maybeBackOffFromEdge 1040-1093.
1.20.1, mojmap: `1.20.1/mojmap/net/minecraft/world/entity/player/Player.java`, maybeBackOffFromEdge 1043-1094.

Difference: 1.19.4 resets fall distance during LivingEntity travel only when current Y velocity is non-positive and Slow Falling is present. 1.20.1 resets it in LivingEntity.aiStep before travel whenever Slow Falling or Levitation is active. Thus with Levitation, and with rising movement under Slow Falling, 1.20.1 clears the prior fall-distance value before player movement while 1.19.4 can retain it.

Movement meaning: For a local player, sneaking input makes Player#isStayingOnGroundSurface true. Entity.move calls the Player override of maybeBackOffFromEdge before clipping for SELF or PLAYER movement. That method requires non-flying state and requested Y <= 0, then checks isAboveGround. If not onGround, isAboveGround compares fallDistance with maxUpStep and performs a no-collision query using a vertical offset derived from fallDistance. Clearing fallDistance can change whether backoff runs and the horizontal 0.05-step probe. The changed writer-to-reader path can affect horizontal movement near an edge for the stated guards. This is not a claim that every Levitation or Slow Falling tick changes position; the result depends on prior fall distance and collision geometry. Fall-damage consumers are outside movement scope.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.4--1.20.1/findings/F-03-fall-distance-edge-backoff.md).
