# WORLD-23: inherited state-shape collision providers

- **1.8.9**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-23-inherited-state-shape-providers.md).
- **1.9.4**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-23-inherited-state-shape-providers.md).

## Difference

- 1.8.9 class hashes: Anvil ; Bed ; Carpet ; Chest ; DaylightDetector ; Diode ; DragonEgg ; EnchantingTable ; EnderChest ; FlowerPot .
- 1.9.4 class hashes: Anvil ; Bed ; Carpet ; Chest ; DaylightDetector ; Diode ; DragonEgg ; EnchantingTable ; EnderChest ; FlowerPot .
- Dispatch evidence: 1.8.9 `World.getCollisions` invokes `block.getBlock().addCollisions(...)`; generic `Block.addCollisions` reads `getCollisionShape(world,pos,state)`, which uses the singleton block's mutable `min/max` fields. 1.9.4 `World.getCollisions` invokes `BlockState.addCollisions`; `StateDefinition.State.addCollisions` dispatches to the block and generic 1.9.4 `Block.getCollisionShape` reads `state.getShape(world,pos)`.

Paired hashes: 1.8.9 `World.java` , `Block.java` ; 1.9.4 `World.java` , `Block.java` , `StateDefinition.java` .

## Movement meaning

- Disposition: eight fixed providers (Bed, Carpet, DaylightDetector, Diode, DragonEgg, EnchantingTable, EnderChest, FlowerPot) have matching constructor/reset bounds and 1.9.4 state constants; WORLD-24 records that bounded no-difference result.

No final player displacement is claimed for either open provider.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-23-inherited-state-shape-providers.md).
