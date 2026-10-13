# F-003: Auto-jump uses a different first collision candidate order

1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java`, `updateAutoJump`, lines 956–965.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `updateAutoJump`, lines 952–960.
1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/level/CollisionGetter.java`, `noCollision`, lines 55–59.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/level/CollisionGetter.java`, `noCollision`, lines 65–75.

Difference: Both loops stop at the first intersecting AABB and use maxY. If eligible entity and block shapes both intersect at different heights, order can change the candidate.

Movement meaning: The independently accepted followup supplies a non-spectator Boat before a 1.5-high fence: the Boat rise 0.5625 passes q=1.2 and sets autoJumpTime=1 in 1.18+, whereas 1.17.1 visits the too-high fence first and writes no timer. The next tick consumes the timer as jump input. LocalPlayer#aiStep -> updateAutoJump -> CollisionGetter -> first AABB; entity filters and providers open.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-003-autojump-candidate-order.md).

Accepted followup: [independent witness/closure review](../../archive/legacy-workflows/source-campaign-2026-10-07/reviews/independent-review-F003-autojump-order-closure-2026-10-10.md).
