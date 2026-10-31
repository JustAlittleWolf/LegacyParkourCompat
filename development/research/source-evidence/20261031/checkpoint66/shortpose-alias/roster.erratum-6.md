# Roster erratum 6 — LocalPlayer SELF pre-query gates

Because `Entity.move(MoverType.SELF, Vec3)` dispatches through the Player override before `Entity.collide`, include the exact `Player.maybeBackOffFromEdge`, `isStayingOnGroundSurface`, and `isAboveGround` members. These establish when the supplied three-axis vector reaches `Entity.collide` unchanged. The collision result route remains the assigned operation; no ordinary input-generation claim is added.
