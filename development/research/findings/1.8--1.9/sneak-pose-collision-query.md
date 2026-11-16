# Sneak pose collision query gate

1.8, Ornithe Feather: `decompiled_minecraft/1.8/ornithe-feather/net/minecraft/entity/Entity.java`, `Entity.<init>`, lines 138–144; `decompiled_minecraft/1.8/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `PlayerEntity.tick()`, lines 185–292, `setSize` calls at 411, 509, 1105 and 1160, and `getEyeHeight()`, lines 1634–1645.
1.9, Ornithe Feather: `decompiled_minecraft/1.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `tick()` lines 243–245 and `updatePlayerPose()` lines 284–308; `decompiled_minecraft/1.9/ornithe-feather/net/minecraft/entity/Entity.java`, `setSize`, lines 256–267; `decompiled_minecraft/1.9/ornithe-feather/net/minecraft/world/World.java`, `getCollisions(Box)`, lines 1012–1049.
Difference: 1.8 initializes entities at 0.6×1.8 and has no tick-time sneak-pose resize; its four `PlayerEntity.setSize` calls are outside `tick()`.
In 1.9, the tick calls `updatePlayerPose`, which selects dimensions by fall-flying, sleeping, sneaking or standing state, then builds a candidate box from the current minimum coordinates and the selected dimensions.
The candidate is queried unchanged through `World.getCollisions(Box)`, which checks block-state collision shapes without an entity argument; `setSize` runs only when the candidate has no block collision.
If the query finds a collision, the resize is skipped and the previous dimensions remain; `setSize` updates the entity dimensions and collision box from the existing minimum coordinates.
Movement meaning: sneaking only lowers eye height in 1.8; in 1.9 the player can gain the shorter collision shape only when the proposed shape clears blocks, so an obstructed pose retains its prior collision dimensions.
The pose-selection, candidate-box, collision-query and resize-gate bodies remain identical in the validated exact 1.9–1.9.4 V1_9 release series.

