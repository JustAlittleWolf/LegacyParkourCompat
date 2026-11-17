# Source coverage: 1.10.1 → 1.10.2

## Immutable correction binding

This revision corrects one caller/scope description in the original coverage report, SHA-256 `A25A3B142686C31702CDED51F9AB192C06CF8F30BFAC54B890EC0F0D5DFEF71A`. It is bound to the independent full-pair REQUEST CHANGES verdict, SHA-256 `F5F85EBB0DE8B41D84C0F435DF6E18D69F7EF517E02518D3C55EB67FAA11E43E`. All other source dispositions and conclusions remain as reported in the original.

## Exact inputs and inventory

- Older source: `decompiled_minecraft/1.10.1/ornithe-feather/`; mapping-local `.decompile-provenance.json` SHA-256 `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f`; 1,845 source entries.
- Newer source: `decompiled_minecraft/1.10.2/ornithe-feather/`; root readiness marker SHA-256 `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d`; root source manifest SHA-256 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; 1,845 entries.
- Both exact publication contracts and full source-hash manifests were checked against the local files by the neutral input queue; the local 1.10.1 provenance and 1.10.2 manifest hashes were rechecked. Full path/hash inventory comparison: 1,845 paths on each side, 0 removed, 0 added, 21 shared paths with changed hashes.

Mapping family is Ornithe Feather on both sides. This report is limited to this exact stable patch edge. No source regeneration or remapping was performed.

## Deterministic player movement inventory

The changed-path set was enumerated from the source manifests and each changed Java file was reviewed.

| Changed source area | Disposition |
| --- | --- |
| `block/FlowerPotBlock.java` | Changed `resolveVirtualProperties` avoids creating a block entity when the `WorldView` is a `WorldRegion`, using `BlockEntityCreationType.CHECK`. Its `getShape` remains the constant `SHAPE` (1.10.1 lines 45–47; 1.10.2 lines 47–49); block-entity contents do not change the collision box. `WorldRegion` is constructed both for render-region reads and by `PathNavigation` path searches (1.10.1 and 1.10.2 `net/minecraft/entity/ai/pathing/PathNavigation.java`, lines 90 and 112). `MobEntity.createNavigation` constructs `GroundPathNavigation` (both releases, `net/minecraft/entity/living/mob/MobEntity.java`, lines 117–118), so this additional caller is non-player pathfinding and excluded from local-player movement. `PathNavigation.java` is byte-identical across the pair (SHA-256 `71e45321f485734ca9f313b1e0cc414f70b06cc4095054f6f7d1b233e94b856f`). No player collision delta. |
| `world/World.java` | `addBlockEntity` now, on a client world, reads the existing block state and notifies listeners with that same state at the same position, flag 2. The changed method is at lines 1378–1390. The sole client `WorldEventListener` implementation found in the exact client source is `WorldRenderer`; its `notifyBlockChanged` only marks nearby render chunks dirty (1.10.2 lines 1890–1895). No block state, player box, velocity or collision provider changes. Rendering invalidation only. |
| `entity/living/player/PlayerEntity.java` | Saved `DataVersion` literal only, 511→512. No movement tick method change. |
| `client/gui/overlay/DebugOverlay.java`, `client/gui/screen/menu/TitleScreen.java`, `client/Minecraft.java`, `client/options/ServerListEntry.java`, `client/resource/pack/ResourcePacks.java`, `realms/RealmsSharedConstants.java`, `server/integrated/IntegratedServer.java`, `server/MinecraftServer.java`, `server/network/handler/RemoteHandshakeNetworkHandler.java`, `snooper/Snooper.java`, `util/crash/CrashReport.java` | Release-version strings/status text or UI; no player movement operation. Handshake paths are described only as version text and are not used for behavioral evidence. |
| `client/options/GameOptions.java`, `util/datafix/DataFixerUpper.java`, `util/datafix/DataFixes.java`, `world/chunk/storage/AnvilChunkStorage.java`, `world/storage/WorldSaveInfo.java`, `world/WorldData.java` | Keymap save version and data/save version updates; no movement-rule change. |
| `client/render/world/RenderChunk.java` | Removes a render-time exception for missing block-entity data and conditionally renders available entities; rendering excluded. |

No source difference creates an accepted movement finding for this edge.

## Core tick, response and provider closure

- `Entity.java` and `LivingEntity.java` are byte-identical across 1.10.1 and 1.10.2 (`05da145e…9039f77`; `d40dd476…33815e82`). Player travel, collision clipping, velocity and box writers, effects, fluids and post-travel checks have no delta.
- `PlayerEntity.java` changes only the saved data version. Local input state, player movement decisions, dimensions, movement-speed/effect consumers and direct player-response methods are unchanged.
- `Block.java` and `FarmlandBlock.java` are byte-identical across the edge (`1971dbc2…aeb640c01`; `63d9048e…f9d4e7f`). The 1.10→1.10.1 farmland collision-height change therefore persists unchanged; it is not a 1.10.2 delta.
- `World.getCollisions` does not change; the added `World.addBlockEntity` notification reaches `WorldRenderer.notifyBlockChanged`, which marks render regions dirty. No movement caller consumes that event callback.
- No changed file adds a player movement state writer, collision provider, block callback, fluid rule, pose/attribute/effect rule or client packet response. There is no movement-relevant state/neighbor/resource dependency for the added rendering notification.
- Exclusions: rendering, UI, release text and save/data migration remain out of scope. Vehicle/non-player physics, health/food production, damage, modern-only behavior and bed-entry remain excluded.

## State

No eligible deterministic local-client movement difference was found in the complete changed-file inventory. This pair remains unfrozen until an independent reviewer verifies source identities, the complete inventory and tick/dependency closure. Runtime parity and implementation status are separate and unassessed.

Relevant local source SHA-256 values (exact file bytes):

| Source file | 1.10.1 | 1.10.2 |
| --- | --- | --- |
| `entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | same |
| `entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | same |
| `entity/living/player/PlayerEntity.java` | `020370cffde2d1fe9f1b63fa5d51f1af2fab095908b19e79f88a7bcac7dfd501` | `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302` |
| `world/World.java` | `41fd1edc24b39a5b9a979d15d54fbd3ebf9818651628109f232fcc24c7e28d0a` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` |
| `block/Block.java` | `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01` | same |
| `block/FarmlandBlock.java` | `63d9048ebed65b890c370a1da6e79733904f67d953aff9fd8c0316f97f9d4e7f` | same |
| `block/FlowerPotBlock.java` | `f6efdf10cd1dc6b8e83c0fa15b16cd8e055cf8e04c34eaaf1864538f540303aa6` | `807076009d4c433643d51ec3927b44b03a941ecf68f130ea826bddaabd005ac9` |
| `client/render/world/WorldRenderer.java` | `ce38dbde3b12c531c418f53a55e46a27ff8a408fe09a6d677ee502c1fbae40d8` | same |
