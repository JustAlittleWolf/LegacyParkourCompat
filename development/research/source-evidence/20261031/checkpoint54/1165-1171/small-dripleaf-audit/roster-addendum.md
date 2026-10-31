# Roster addendum: direct state and movement consumers

Added after the frozen roster because the selected placement methods call or produce these reads:

- `net/minecraft/world/level/LevelReader.java`: `isWaterAt` tag predicate used by the 1.17.1 state helper and upper preclear.
- `net/minecraft/world/level/material/FluidState.java`, `Fluids.java`, and `net/minecraft/data/tags/FluidTagsProvider.java`: distinguish flowing water type from membership in the water tag and resolve a waterlogged state back to fluid.
- `net/minecraft/world/level/block/LiquidBlock.java`: flowing-water block state/fluid setup and clicked-cell replaceability context.
- `net/minecraft/world/level/block/VineBlock.java`, `net/minecraft/data/tags/BlockTagsProvider.java`, and `net/minecraft/world/level/block/state/BlockBehaviour.java`: upper Vine replacement and climbable classification.
- `net/minecraft/world/entity/Entity.java` and `LivingEntity.java`: fluid-contact update/read and climbable consumer paths.
- `net/minecraft/client/multiplayer/MultiPlayerGameMode.java` and `net/minecraft/world/item/ItemStack.java`: client item-use prediction route.
- `net/minecraft/world/level/block/Blocks.java`, `Block.java`, `BlockState.java`, and `SimpleWaterloggedBlock.java`: block registration, direct state properties, and waterlogged fluid-state behavior.

All listed files are source-hashed in `source-hashes.tsv` and match their exact publication declaration.
