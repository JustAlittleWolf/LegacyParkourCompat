# Source coverage: 1.10 → 1.10.1

## Exact inputs and inventory

- Older source: `decompiled_minecraft/1.10/ornithe-feather/`; mapping-local `.decompile-provenance.json` SHA-256 `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da`; 1,845 source entries.
- Newer source: `decompiled_minecraft/1.10.1/ornithe-feather/`; mapping-local `.decompile-provenance.json` SHA-256 `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f`; 1,845 source entries.
- Both exact publication contracts and full source-hash manifests were checked against the local files by the neutral input queue; the local provenance hashes were rechecked. Full path/hash inventory comparison: 1,845 paths on each side, 0 removed, 0 added, 29 shared paths with changed hashes.

Mapping family is Ornithe Feather on both sides. This report is limited to the exact stable patch edge. No source regeneration or remapping was performed.

## Deterministic player movement inventory

The changed-path set was enumerated from the source manifests and each changed Java file was reviewed. The complete changed set is categorized below; no unchanged source path can introduce a pair-local implementation difference.

| Changed source area | Disposition |
| --- | --- |
| `block/FarmlandBlock.java` | **Movement finding.** Collision-provider override removed; exact provider and movement caller chain below. |
| `client/options/GameOptions.java`, `client/options/KeyBinding.java` | Input polling/key-code boundary change. Excluded input-device plumbing; no historical movement rule change is inferred. |
| `entity/projectile/PotionEntity.java`, `entity/data/SyncedData.java` | Corrected synchronized-data owner for a potion projectile plus registration diagnostics. Entity data/render/producer effects are not a direct local-player movement operation; no player velocity, box, predicate, effect consumer or local movement writer changes here. |
| `entity/living/player/PlayerEntity.java` | Only saved `DataVersion` changes 510→511; no tick or movement method change. |
| `world/World.java` | Removes `isEmpty()` implementation from the world/view contract; the pair's `getCollisions`, state lookup, and player movement caller paths are unchanged. No movement dependency consumes `isEmpty`. |
| `world/WorldRegion.java` | Block-entity retrieval overload used by region rendering; movement uses the client `World` query, and no player movement writer or collision provider is changed here. |
| `world/WorldView.java` | Removes matching `isEmpty()` declaration; same bounded disposition as `World.isEmpty`. |
| `client/render/world/RenderChunk.java` | Block-entity render collection/error handling only; rendering excluded. |
| `client/gui/overlay/DebugOverlay.java`, `client/gui/screen/game/StructureBlockScreen.java`, `client/gui/screen/menu/TitleScreen.java`, `client/Minecraft.java`, `client/options/ServerListEntry.java`, `client/resource/pack/ResourcePacks.java`, `realms/RealmsSharedConstants.java`, `server/integrated/IntegratedServer.java`, `server/MinecraftServer.java`, `server/network/handler/RemoteHandshakeNetworkHandler.java`, `snooper/Snooper.java`, `util/crash/CrashReport.java` | Version strings, UI/editor behavior, startup/status/handshake text or reporting. No player tick or movement operation. The handshake entry is treated only as version text and is not used as behavioral evidence. |
| `util/datafix/DataFixerUpper.java`, `util/datafix/DataFixes.java`, `util/datafix/fixes/EntityHealthFix.java`, `world/chunk/storage/AnvilChunkStorage.java`, `world/storage/WorldSaveInfo.java`, `world/WorldData.java`, `world/dimension/end/DragonFight.java` | DataVersion/save-data and End fight persistence/placement changes; no deterministic local-client player movement read or writer. Health-data migration is outside movement scope. |

### Finding: farmland collision height

1.10 Ornithe Feather `net/minecraft/block/FarmlandBlock.java` declares `SHAPE` as `(0,0,0)-(1,0.9375,1)` at line 22 and overrides `getCollisionShape` at lines 36–40 to return `FULL_BLOCK_SHAPE`.

1.10.1 Ornithe Feather `FarmlandBlock.java` retains the same `SHAPE` and `getShape` at lines 22, 31–34, but removes the `getCollisionShape` override. The unchanged `net/minecraft/block/Block.java` default at lines 357–359 returns `state.getShape(world,pos)`. `net/minecraft/block/state/StateDefinition.java` lines 364–370 dispatches `BlockState.getCollisionShape` and `addCollisions` to the owning block. Consequently, a farmland block's movement collision changes from a full one-block-high box in 1.10 to the 15/16-high shape in 1.10.1.

The local-player path is concrete: unchanged `World.getCollisions(Entity,Box)` scans block states and invokes `BlockState.addCollisions` (1.10.1 lines 903–944); unchanged `Block.addCollisions` obtains the collision shape and appends its translated box (lines 342–350). `Entity.move` consumes those boxes while clipping requested movement (1.10.1 lines 446–619); `LivingEntity` travel calls that inherited move path (lines 1371, 1421, 1450, 1481). `PlayerEntity` inherits it. For a player whose vertical movement is clipped by farmland, the collision top is 1/16 block lower in 1.10.1, so the resolved player position can differ. `MOISTURE` is not read by the provider; no state, neighbor, tag or resource dependency is open.

Relevant local source SHA-256 values (exact file bytes):

| Source file | 1.10 | 1.10.1 |
| --- | --- | --- |
| `block/FarmlandBlock.java` | `9df445485ba7e1603c20b5dede847fe412558148acefe1eedc9bb3b62b14c25` | `63d9048ebed65b890c370a1da6e79733904f67d953aff9fd8c0316f97f9d4e7f` |
| `block/Block.java` | `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01` | same |
| `block/state/StateDefinition.java` | `00fea8cdf8a0cabf1af21e7e7ff47f071bd87a16f91697a3b31efce6c78bfda4` | same |
| `entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | same |
| `entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | same |
| `entity/living/player/PlayerEntity.java` | `db7bbbc15e2bcb0de18476fec1b88e73eccf8ae63b170276b1d8327dd0be796b` | `020370cffde2d1fe9f1b63fa5d51f1af2fab095908b19e79f88a7bcac7dfd501` |
| `world/World.java` | `827fea19d95ddc0a2847859f0c288bf0b6d6dc3692feb30b52b84c60fa19d877` | `41fd1edc24b39a5b9a979d15d54fbd3ebf9818651628109f232fcc24c7e28d0a` |

Candidate draft: `findings/farmland-collision-height.md` in this pair directory. This records the exact source delta and a bounded player movement consequence; independent audit remains required.

## Core tick, response and provider closure

- `Entity.java` and `LivingEntity.java` are byte-identical across all three releases (`05da145e…9039f77` and `d40dd476…33815e82`). Their movement loop, travel branches, velocity/box writes, clipping order, fluid handling, effects, knockback response and post-move checks therefore introduce no additional difference on this pair.
- `PlayerEntity.java` changes only its `DataVersion` literal in the examined diff; input state and player-specific movement consumers are unchanged.
- `Block.java` is byte-identical (`1971dbc2…aeb640c01`). The sole changed collision provider is farmland. Other block registrations, providers, callbacks and their property/neighbor inputs have no changed source file in the exact inventory.
- No changed files touch attributes/equipment application, status-effect movement math, fluids, pose/dimensions, client packet handlers applying direct player movement responses, or movement-relevant resources/tags. The changed potion synchronized-data declaration belongs to the non-player projectile and does not change a player response method or client player movement writer.
- World/block-entity, neighbor and cached-state dependencies for the farmland shape are closed: its provider ignores state/world/position and returns a constant shape; the move query uses the current vanilla `BlockState` and same coordinate in both releases.
- Exclusions: input-device polling semantics and all rendering, server-only, persistence, damage/health, vehicle and non-player physics changes remain excluded per project scope.

## State

Source discovery found one bounded movement finding and no other pair-local movement differences in the changed inventory. The pair is not frozen until an independent reviewer retraces the complete tick/dependency inventory and accepts this coverage revision. Runtime parity and implementation status are separate and unassessed.
