# F-4: Mounted boat clips the newer lava scan above a reachable Player fluid cell

1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/Entity.java`, baseTick 501-507; positionRider 2262-2282; updateInWaterStateAndDoFluidPushing 1501-1522; updateFluidHeightAndDoFluidPushing 3509-3582.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java`, baseTick 522-528; getFluidInteractionBox 4015-4026; updateFluidInteraction 1558-1583.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/EntityFluidInteraction.java`, update 32-101; applyCurrentTo 171-189.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2931-2957.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/vehicle/boat/AbstractBoat.java`, interact 694-703.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/vehicle/boat/AbstractBoat.java`, modifyPassengerFluidInteractionBox 783-795.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/player/Player.java`, isPushedByFluid 1679-1681; isAffectedByFluids 884-886.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java`, isPushedByFluid 1673-1675.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/vehicle/boat/Boat.java`, rideHeight 14-17.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/vehicle/boat/Boat.java`, rideHeight 14-17.

Difference: 1.21.11 keeps the full deflated passenger Player box for LAVA after its boat-specific WATER early return. 26.1.2 clips the dry boat passenger interaction box minimum to boat.maxY before cell enumeration. For the reviewed OAK_BOAT at (100.5,65.5,100.5), standing rider Y is 65.0875; the old deflated box scans Y=65-66, while the newer clipped box scans only Y=66. The Y=65 lava cell is omitted by scan bounds, not by a later fluid-top comparison.

Movement meaning: In the fixed Nether channel (level-1 LAVA at (100,65,100), level-2 east neighbor and source-fed row, solid side walls), 1.21.11 reads a nonzero westward current and adds the Player minimum horizontal impulse. 26.1.2 never enumerates the cell and records no LAVA current. Non-flying standing local Player, loaded cells and fixed dry-boat geometry are required; no vehicle trajectory is compared.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.11--26.1.2/findings/F-4-mounted-player-lava-box.md).
