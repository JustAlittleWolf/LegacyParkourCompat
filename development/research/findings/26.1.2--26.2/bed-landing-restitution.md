# Bed landings use restitution in the new collision response

26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java`, move 781-785; isSuppressingBounce 2630-2632; collideWithShapes 1161-1177.
26.2, unobfuscated: `26.2/unobfuscated/net/minecraft/world/entity/Entity.java`, move 784-786; restituteMovementAfterCollisions 802-843; isSuppressingBounce 2697-2699; collideWithShapes 1244-1259.
26.2, unobfuscated: `26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java`, createLivingAttributes 332-359; getEntityBounciness 2202-2204; aiStep 3073; travel 2430-2453; shouldDiscardFriction 676-678.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java`, aiStep 3073; travelInAir 2430-2453; handleRelativeFrictionAndCalculateMovement 2623-2638; shouldDiscardFriction 653-655.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java`, isShiftKeyDown 678-680; aiStep 914.
26.2, unobfuscated: `26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java`, isShiftKeyDown 678-680; aiStep 914.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java`, canSimulateMovement 1269-1271; travel 1381-1403; travel 1381-1404.
26.2, unobfuscated: `26.2/unobfuscated/net/minecraft/world/entity/player/Player.java`, canSimulateMovement 1290-1292; travel 1402-1425.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/level/block/BedBlock.java`, updateEntityMovementAfterFallOn 138-151; class declaration / field initialization 57-62.
26.2, unobfuscated: `26.2/unobfuscated/net/minecraft/world/level/block/BedBlock.java`, class declaration / field initialization 55-60.

Difference: Use a fresh local Player's first travel tick at feet Y=1.0 over a full-cube ledge beside an existing bed at Y=0 (top 0.5625), with initial +0.0 Y velocity, default gravity 0.08 and widened 0.98F drag, no effects, no flight/passenger and ordinary friction. The move precedes gravity. Departure stores -0.07840000152587891; following fall tails store -0.1552320045166016 and -0.230527368912964. Hold shift for the bed landing, clipping -0.20386799395751953 to reach exactly 0.5625; both response/tails then store -0.07840000152587891. Release shift next tick. 26.1.2 clips the request to +0.0 but bed callback writes +0.05174400306320195 using widened 0.66F. 26.2 sees 0.07840000152587891 < effective gravity 0.08, chooses zero restitution and writes +0.0.

Movement meaning: The subsequent air-travel tail stores -0.027690877537002466 versus -0.07840000152587891. The reviewed initial zero is a fresh-entity first-travel setup; it is not asserted for an already settled standing player. Bed geometry, shift suppression and local canSimulateMovement establish the direct Player Y-velocity difference; damage and measured trajectories are excluded.
