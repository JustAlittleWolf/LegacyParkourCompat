# Server-gated farmland replacement position write

1.11.2, Ornithe Feather Gen2: `net/minecraft/entity/Entity.java`, `checkFallDamage`, lines 873–883; `net/minecraft/block/FarmlandBlock.java`, `onFallenOn`, lines 59–69 and `setDirt`, lines 71–79.

1.12.2, Ornithe Feather Gen2: `net/minecraft/entity/Entity.java`, `checkFallDamage`, lines 890–900; `net/minecraft/block/FarmlandBlock.java`, `onFallenOn`, lines 60–70 and `setDirt`, lines 72–79.

Source disposition: both releases gate farmland replacement on `!world.isClient`. The old helper resets intersecting entities to the dirt box's `maxY`; the newer helper computes a height delta from `entity.getShape().minY` and teleports upward by that delta plus `0.001`. Since this is a server-side block-state replacement and position correction, it is excluded from the client-only movement lane and is not counted as a player movement finding.
