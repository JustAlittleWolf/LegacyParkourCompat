# Frozen independent review roster: Small Dripleaf client placement

Frozen before reading any proposing-worker report or report-derived ledger. Review boundary: 1.17 → 1.17.1 only, because Small Dripleaf is absent from 1.16.5 and is a modern feature at that boundary. Exact read-only inputs: Mojmap 1.17 and 1.17.1 trees.

Selected source families:

- Registration and item dispatch: `net/minecraft/world/item/Items.java`, `DoubleHighBlockItem.java`, `BlockItem.java`, `context/BlockPlaceContext.java`, `world/level/block/Blocks.java`, `world/level/block/DoublePlantBlock.java`.
- Local prediction / client application: `net/minecraft/client/multiplayer/MultiPlayerGameMode.java`, `net/minecraft/client/player/LocalPlayer.java`, `world/level/Level.java`, `world/level/block/Block.java`, `world/level/block/state/BlockState.java`.
- Upper-cell replaceability and final states: `net/minecraft/world/level/block/SmallDripleafBlock.java`, `VineBlock.java`, `BlockBehaviour.java`, `world/level/material/Fluids.java`.
- Movement consumer: `net/minecraft/world/entity/LivingEntity.java`, `Entity.java`, `Player.java`, and `world/level/block/state/BlockState.java`; inspect the reachability and climbable tag query used by local travel.
- Relevant built-in tag provider/declared membership: `net/minecraft/data/tags/BlockTagsProvider.java` and `net/minecraft/tags/BlockTags.java`.

Each source hash will be recorded for both exact versions and checked against the 1.17 embedded Mojmap provenance source table or the 1.17.1 parent source manifest. Scope remains limited to equal raw local placement input and fixed initial vanilla state, with the client-predicted state before any authoritative correction.
