# Stationary extended piston-base collision provider

1.8.9, Ornithe Feather Gen2 build 2. `net/minecraft/block/PistonBaseBlock.java`, `updateShape` 173–203, `addCollisions` 211–214 and `getCollisionShape` 217–219. `net/minecraft/block/Block.java`, `addCollisions` 339–344.

1.9 and 1.9.4, Ornithe Feather Gen2 build 2. `net/minecraft/block/PistonBaseBlock.java`, shape constants and `getShape` 29–66 / 30–67, and `addCollisions` 75–77 / 76–77. The relevant collision behavior persists through the checked 1.9 patch line.

In 1.8.9, collision collection resets the shared bounds and dispatches through the overridden collision method. That method refreshes the bounds from the live world state at the queried position before the base method builds the translated box. Starting in 1.9, `getShape` selects the same axis-aligned box from the queried state and `addCollisions` uses that shape.

For a valid stationary piston or sticky-piston state, all six extended facings occupy the full block minus the facing-side quarter. Retracted pistons occupy a full cube. The provider architecture changes at 1.9. The occupied geometry agrees when the queried state matches the world state. Head and moving-piston shapes are separate operations.
