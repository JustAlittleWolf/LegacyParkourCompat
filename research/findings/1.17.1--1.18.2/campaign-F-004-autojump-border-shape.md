# F-004: Auto-jump collision candidates omit the old world-border shape

1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java`, `updateAutoJump`, lines 956–959.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `updateAutoJump`, lines 952–953.
1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/level/CollisionSpliterator.java`, `getChunk`, lines 103–116.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/level/CollisionGetter.java`, `getCollisions`, lines 67–80.

Difference: 1.17.1 auto-jump query may include border shape; 1.18.2 query omits it, with border collision handled elsewhere.

Movement meaning: LocalPlayer#aiStep -> updateAutoJump -> getCollisions; general solver border path open.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-004-autojump-border-shape.md).
