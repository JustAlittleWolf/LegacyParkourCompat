# F-02: auto-jump probe uses a different inverse-square-root approximation

1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, updateAutoJump 911-1007; move 900-905.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, updateAutoJump 891-987; updateAutoJump 900-905; updateAutoJump 911.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/util/Mth.java`, fastInvSqrt 495-501.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/util/Mth.java`, invSqrt 387-389.

Difference: The methods normalize a horizontal direction and derive a forward probe distance before querying collision boxes and shapes. 1.19.3 uses the approximate `Mth.fastInvSqrt(float)` bit-hack plus a Newton step. 1.19.4 delegates to JOML's reciprocal square root implementation, which takes `Math.sqrt` and rounds the result to `float` before float division. The factor is used in probe endpoints and its reciprocal in search distance. The 1.19.3 `BlockPos(double,double,double)` conversion was replaced by 1.19.4 `BlockPos.containing(double,double,double)`; both call `Mth.floor` for each coordinate, so that replacement is checked as equivalent for this slice.

Movement meaning: `LocalPlayer#move` -> `updateAutoJump` -> inverse-square-root factor -> forward probe endpoints/distance -> world collision-box and block-shape queries. When the probe accepts a gap, it writes `autoJumpTime = 1`; local tick consumes that timer to schedule a jump. The gates above bound reachability. Source proves the normalization algorithm changed in a reachable auto-jump collision probe. This can change probe endpoints or distance and therefore a threshold/query result.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.3--1.19.4/findings/F-02-auto-jump-inverse-sqrt.md).
