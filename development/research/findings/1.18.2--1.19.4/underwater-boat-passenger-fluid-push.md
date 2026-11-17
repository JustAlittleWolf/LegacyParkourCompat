# Water currents can push a passenger of an underwater boat

1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/Entity.java`, baseTick 403-461; updateInWaterStateAndDoWaterCurrentPushing 1055-1071.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, baseTick 409-467; updateInWaterStateAndDoWaterCurrentPushing 1080-1096.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/player/Player.java`, isPushedByFluid 1822-1824.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/player/Player.java`, isPushedByFluid 1848-1850.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/vehicle/Boat.java`, isUnderWater 869-871.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/vehicle/Boat.java`, isUnderWater 876-878.

Difference: For a non-flying player on a submerged boat, 1.18.2's boat check unconditionally bypasses water-current sampling. 1.19.4 continues through the same fluid scan as an unmounted entity, and the player's `isPushedByFluid()` result permits the flow vector to be added to the player's delta movement. This is distinct from boat steering or boat motion: the write is to the passenger player's velocity.

Movement meaning: In 1.19.4, flowing water can directly change the velocity of a non-flying player riding an underwater boat; 1.18.2 suppresses that passenger current update. The branch and velocity write are confirmed from paired source.

## Exact 1.19.2 to 1.19.3 current-write boundary

In Mojmap 1.19.2, `Entity.updateInWaterStateAndDoWaterCurrentPushing` skips water current for all boat passengers. Exact 1.19.3 keeps that skip for surfaced boats and admits underwater boat passengers to the current calculation. The 1.19.4 and 1.20.1 sources retain the surfaced-only skip. In the accepted case, a non-flying local passenger overlaps loaded flowing water and the aggregate flow passes the current-write gates.

`rideTick` clears velocity before the player tick. An eligible current then changes player velocity, which travel consumes. The final rider-position update leaves velocity intact at the end of that tick. The next passenger tick or the ordinary dismount correction can clear it. This source finding covers the direct player current write and its tick-end state. Boat movement, the separate crouch response, exact packet timing, and a persistent post-dismount trajectory remain outside the finding. [Exact finding](../../source-evidence/20261117/block-a-passenger-water-current/underwater-boat-passenger-water-current.md), [source comparison](../../source-evidence/20261117/block-a-passenger-water-current/comparison.corrected-r1.md), [independent verdict](../../source-evidence/20261117/block-a-passenger-water-current/verdict-r1.md).
