# Look-at square-root precision changes swimming steering

**Boundary:** 1.16.5 → 1.17; the 1.17 calculation persists in 1.17.1.

`ClientPacketListener.handleLookAt` forwards a coordinate-target packet to the local player’s inherited `Entity.lookAt`. With the same finite FEET-anchor target `(-411973, -745099, 283936)` and player position `(0,0,0)`, 1.16.5 stores the horizontal length using `Mth.sqrt(double)`, whose float result is widened to double; 1.17 uses `Math.sqrt(double)` and retains double precision. The resulting pitch differs by one float ULP (`0x42607901` vs `0x42607900`). The changed pitch selects different `Mth.sin` table entries, so `getLookAngle().y` is `-0.83018905` in 1.16.5 and `-0.8301356` in 1.17/1.17.1.

When the same local player is swimming, not flying, not spectating, on foot, and has Y delta movement `0`, `Player.travel(Vec3)` consumes that look-vector Y. Both values take the same branch and coefficient `0.085`; the written Y delta movement is `-0.0705660691857338` in 1.16.5 and `-0.0705615246295929` in 1.17/1.17.1.

**Applicability:** the finding assumes the same finite coordinate-target packet and equal local player state/input at the movement call. The server’s target production, device input production, runtime execution, and full trajectory are not established. The source route reaches the ordinary swimming travel write.


Exact sources: 1.16.5 Mojmap `net/minecraft/world/entity/Entity.java#lookAt` (2628–2639), versus 1.17 and 1.17.1 Mojmap (2758–2771 / 2760–2773); `ClientPacketListener#handleLookAt`, `Mth#sqrt(double)`, `Vec3#directionFromRotation`, and `Player#travel` supply the response and movement-consumer chain.
