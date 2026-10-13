# F-3 correction snapshot: Shallow-overlap Nether lava current crosses 1.21.11's Player minimum impulse but 26.1.2's tracker cutoff returns

1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/Entity.java`, baseTick 501-507; updateFluidHeightAndDoFluidPushing 3509-3582.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java`, baseTick 522-528; updateFluidInteraction 1558-1583; getFluidInteractionBox 4015-4025.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/EntityFluidInteraction.java`, update 32-129; applyCurrentTo 171-189.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/player/Player.java`, isPushedByFluid 1679-1681.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java`, isPushedByFluid 1673-1675.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/level/material/LavaFluid.java`, getDropOff 164-166.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/level/material/LavaFluid.java`, getDropOff 165-167.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/data/worldgen/DimensionTypes.java`, bootstrap 81.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/level/material/FlowingFluid.java`, getNewLiquid 160-195; getFlow 55-100.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/level/material/FlowingFluid.java`, getNewLiquid 161-195; getFlow 55-100.

Difference: Define float h=1.0F/9.0F, then double top=64.0D+(double)h and playerY=top-0.003D, with a standing unmounted non-flying Player at X/Z (100.5,100.5) and zero horizontal velocity. In a loaded Nether side-walled source-fed lava row, level 1 at (100,64,100) and east level 2 generate (-1,0,0). 1.21.11 uses float blockY+height before widening and overlap from the 0.001-deflated box; its scaled current magnitude is 1.4023730099168574E-5, above Vec3's floor. 26.1.2 measures 0.0030000000000001137 from the raw minimum and rejects squared accumulated current 9.000000000000683E-6 < 1.0E-5F before averaging/scaling/minimum impulse.

Movement meaning: The same exact double coordinate is used on both sides. 1.21.11's Player branch adds the minimum westward impulse 0.0045000000000000005; 26.1.2 adds no LAVA current. This preserves the reviewed R2 arithmetic correction. Other lava heights/vectors, mounted boxes and first changed release remain outside the bounded assertion.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.11--26.1.2/findings/F-3-nether-lava-shallow-overlap-r2-2026-10-09.md).
