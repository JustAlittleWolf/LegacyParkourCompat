# Ladder collision thickness

1.8, Ornithe Feather: `net/minecraft/block/LadderBlock.java`, `getCollisionShape` and `updateShape`, lines 27–31 and 39–57; `net/minecraft/entity/living/LivingEntity.java`, `isClimbing`, lines 790–795.
1.9, Ornithe Feather: `net/minecraft/block/LadderBlock.java`, `getShape`, lines 18–41; `net/minecraft/block/Block.java`, `getCollisionShape`, lines 326–328; `net/minecraft/entity/living/LivingEntity.java`, `isClimbing`, lines 896–908.
Difference: 1.8 sets the ladder bounds to 0.125 block (2/16) thick and routes those bounds through `getCollisionShape`. In 1.9 the facing shapes are 0.1875 block (3/16) thick, and the block collision shape returns the selected state shape. Both versions gate ladder/vine climbing using the block at floor(X), floor(player-box minY), floor(Z), excluding spectator players.
Movement meaning: the ladder’s solid movement collision extends 1/16 block farther into the adjacent space in 1.9. A player moving close alongside or across a ladder can therefore collide with it at positions that clear the 1.8 shape, changing horizontal clearance and resulting player movement.
