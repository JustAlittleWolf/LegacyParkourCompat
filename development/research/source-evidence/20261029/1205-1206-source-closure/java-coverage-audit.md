# Independent pair coverage audit: 1.20.5 → 1.20.6

## Verdict

**Java movement-source closure: accepted for the bounded source inventory below. Pair freeze: open.** The complete canonical Mojmap Java manifests were hash-checked against every listed source file and compared path-by-path. Exact player tick/response roots, packet handling, collision/query APIs, shape math, entity movement, and callback implementation sources are byte-identical. The 13 Java path differences do not change those movement operations. This closes the source-code partitions listed here, but not packaged data/resources and tags that feed movement predicates and world queries. No full-pair freeze or runtime-parity claim follows.

## Exact source identity and recheck

Canonical roots:

- `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.20.5/mojmap`
- `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.20.6/mojmap`

Both ready markers name their exact version and `mojmap`; the method/source naming family is compatible. The manifests independently recompute to their ready pins. I rehashed each live manifest-listed Java file:

| Release | Java files | Source bytes | Live hash mismatches | Source-manifest SHA-256 |
| --- | ---: | ---: | ---: | --- |
| 1.20.5 | 5,328 | 25,448,860 | 0 | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` |
| 1.20.6 | 5,329 | 25,451,020 | 0 | `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` |

I rechecked the pre-existing ordinary-movement report and independent audit against those active trees. The whole-tree path/hash comparison yields **5,316 identical Java paths, 12 modified, 0 removed, and 1 added**. This matches both reports. The 1.20.5 ready/provenance pins include source, artifact, diagnostics, client and mapping identities. The 1.20.6 ready marker pins the source/artifact/diagnostics and client hashes, but its provenance omits `mappingFamily` and the individual input hashes. Both ready trees' source manifests are intact; the original client jars named by the artifact records are absent at those paths, so the client binaries themselves cannot be rehashed here. This is an input-provenance limitation, not a source-manifest mismatch.

## Hash-first movement-source inventory

The hash comparison prunes identical files before method review. Every listed source below is present on both sides, has no manifest mismatch, and has the same whole-file hash in each release.

| Movement partition | Rechecked source identities / scope | Result |
| --- | --- | --- |
| Local input, player tick and travel | `client/player/LocalPlayer.java` `6b429dfa…46ffa`; `Input.java` `b302ffbc…97fbb`; `KeyboardInput.java` `a8064906…623ff0`; `world/entity/Entity.java` `71cd6b9f…31c18f3`; `LivingEntity.java` `c66ec8dc…3cd753b2`; `player/Player.java` `785d93cc…66dbe2` | Exact source equality, including enclosing gates, call order, movement writes and predicates in these roots. |
| Direct client responses and delivery | `client/multiplayer/ClientPacketListener.java` `e121e998…8eb7cd`; `ClientLevel.java` `cefc6ffb…fb911e6`; all 170 `network/protocol/game/` source paths are equal. Spot-checked `ClientboundPlayerPositionPacket`, `ClientboundSetEntityMotionPacket`, `ClientboundExplodePacket`, `ClientboundUpdateMobEffectPacket`, `ClientboundUpdateAttributesPacket`, `ClientboundRespawnPacket`, `ClientboundLoginPacket`, and `ClientboundLevelChunkWithLightPacket`; each is equal. | Exact Java-source equality for movement-relevant network response handlers/packet definitions in this namespace. This assumes the same received data and world inputs. |
| Collision queries and shape math | `CollisionGetter.java` `f21daa1e…1e6d95`; `BlockGetter.java` `d0a30788…3a4edcb`; `LevelReader.java` `d3327318…07a9b7`; `AABB.java` `13ab54ef…b96cad9`; `CollisionContext.java` `aa5cd170…10fbbf`; `EntityCollisionContext.java` `fa505d47…299587`; `Shapes.java` `ec207e73…e91311a`; `VoxelShape.java` `106eb81e…fbe9ed` | Exact source equality for query contracts, context construction and geometry math. |
| Block shape and callback implementations | All 417 `world/level/block/` source paths; 412 are hash-identical. The five differences are block-entity classes listed below; block implementations, `Block.java` `0c96f526…8bb8e09`, `BlockBehaviour.java` `5ccc577c…e685b`, `BlockState.java` `a3eec38a…a9bb7b`, and `Blocks.java` `684579bc…9c5b129` are equal. All 1208 `world/level/` source paths have only six differences (the five block entities plus `BaseCommandBlock`). | No changed block-shape provider or block callback implementation. All unchanged block callbacks, contextual shapes, block-state dispatch and registration code are pruned by exact source equality. |
| Fluids and movement attributes | `FluidState.java` `b52e2b1e…e7c1c`; `Attributes.java` `7e30d87c…e2b82e`; the full `world/entity/` (591 paths) and `world/phys/` (26 paths) source groups are hash-identical. | Exact Java-source equality for these transitive code dependencies. |

The explicitly checked full-file hashes for the compacted values in the table are in the corresponding manifests. All 170 game-packet files, 591 entity files and 26 physics files had zero changed paths; there are no hidden method-only substitutions behind equal filenames.

## Bounded review of the 13 Java differences

The changed paths are `ReportEnvironment`, `DetectedVersion`, `SharedConstants`, `DataFixers`, `ChestedHorsesInventoryZeroIndexingFix`, `CustomData`, `BaseCommandBlock`, `BannerBlockEntity`, `BaseContainerBlockEntity`, `BeaconBlockEntity`, `BlockEntity`, `EnchantingTableBlockEntity`, and the new `V3808_2` schema.

I compared the changed method bodies relevant to movement and followed the changed call edges far enough to classify them:

- Version/report constants and schemas are metadata or data-fix registration. The added schema and migration alter trader-llama inventory/equipment data; the chest-horse inventory fix touches llamas, trader llamas, mules and donkeys. These are not player movement code; vehicle/non-player simulation is excluded.
- `CustomData.loadInto(Entity)` is unchanged. Its changed `loadInto(BlockEntity)` path adds exception logging and rollback around block-entity custom-data loading. It does not alter block-state collision providers or movement responses. The affected custom-name parsing changes in `BlockEntity`, `BaseCommandBlock`, banner/container/beacon/enchanting-table block entities likewise do not change shape/query/callback methods. Beacon effect production is server-side and excluded; no client player effect-receipt code changed.
- The changed-path set contains no local-player, entity movement, collision/query/shape, fluid, game-packet, client packet listener or client-world update source. Whole-manifest equality therefore statically prunes every other Java source, including unchanged per-block implementations and callback callers.

These classifications close the changed Java paths for the player movement source inventory. They do not establish equivalence of arbitrary world state, item data, server output or trajectories.

## Actual open partition and exclusions

**Open source/resource coverage (pair-freeze prerequisite):** the canonical trees contain only `.java` files. No resources, tags, data-pack definitions, registry bootstrap data or tag values are represented in either source tree, and no extracted resource inventory was supplied. The original client-jar paths named in the artifact manifests are absent, so I could not hash-compare packaged default resources. Movement code reads vanilla tags and registries for block/fluid/entity classifications and query behavior; those resource inputs remain an unexamined dependency. The next bounded action is to obtain/read-only inventory the exact 1.20.5 and 1.20.6 packaged data/resource entries that feed those player movement queries and compare their paths and hashes, then inspect only changed movement-relevant entries and their references. Until that inventory closes, the pair must not be called frozen.

**Outside source-freeze requirements:** differing server-only producers (including beacon effect production), server-selected world/chunk/block-state values, packet timing/content, and other upstream environment inputs are not client historical movement-source comparisons. They are not listed as source holes. Runtime path/trajectory parity is a separate verification phase and remains unproven; the task provides no launch or test authorization.

## Full source-hash anchors

The following canonical source paths were explicitly rechecked in both manifests and have these identical whole-file SHA-256 values:

- `net/minecraft/client/player/LocalPlayer.java` — `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`
- `net/minecraft/client/player/Input.java` — `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb`
- `net/minecraft/client/player/KeyboardInput.java` — `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0`
- `net/minecraft/client/multiplayer/ClientLevel.java` — `cefc6ffb53ad508052a7a0a96e21ecfd6d2cf471d920c7e91f1911c61fb911e6`
- `net/minecraft/client/multiplayer/ClientPacketListener.java` — `e121e998ec25211aaecf91fffd0ea699cebd47eddea9134efd3f2ffbef8eb7cd`
- `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` — `03f4c58cb1f801cb62fe48a207a575b7bfd2a9fdcb5a0683bef3c0c9c75bed69`
- `net/minecraft/network/protocol/game/ClientboundSetEntityMotionPacket.java` — `34e141d58c2d3585ef6a4b00ed4819c42fa11301ba8428ca06c5379fb7ecdae9`
- `net/minecraft/network/protocol/game/ClientboundExplodePacket.java` — `41b8d60fd85e4811bbc0b7b6bc623825f7824ec8266bb00ab4e2fb371438038c`
- `net/minecraft/network/protocol/game/ClientboundUpdateMobEffectPacket.java` — `268e9368525964192b0fb8f15f4a6418a8efcbcb657035b73752367b8ebc7f2e`
- `net/minecraft/network/protocol/game/ClientboundUpdateAttributesPacket.java` — `9a1e3d3ef78da7c72324ad9d526b9cf0ee983dace618a56337ab6d97381d7d2d`
- `net/minecraft/network/protocol/game/ClientboundRespawnPacket.java` — `272d6c1981ded5f5adf4c560e649aad0e6705bc157bc46d41686881943e38c78`
- `net/minecraft/network/protocol/game/ClientboundLoginPacket.java` — `c35034688ecb4b1774221f4617b37b15278bb184bd22cba007c350574d191c43`
- `net/minecraft/network/protocol/game/ClientboundLevelChunkWithLightPacket.java` — `912d6f56793f62c12f07fe47d38182e4162344a89b78a1d154e5ec91826ca689`
- `net/minecraft/world/entity/Entity.java` — `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`
- `net/minecraft/world/entity/LivingEntity.java` — `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`
- `net/minecraft/world/entity/player/Player.java` — `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`
- `net/minecraft/world/level/CollisionGetter.java` — `f21daa1ed0365cc16a2638373d702fbaeb4242ad12a80a41105cf43c711e6d95`
- `net/minecraft/world/level/BlockGetter.java` — `d0a307889b2f18c9eab586baa92cf92f39a4df7918b17c631d5354a1e3a4edcb`
- `net/minecraft/world/level/LevelReader.java` — `d332731858d00cef003fa7f24dea6886f2d5a9029918f9c8c0540313ab07a9b7`
- `net/minecraft/world/level/block/Block.java` — `0c96f526470944baa74d212ca8bfb8b579fd6a1d7045498b8e062b4868bb8e09`
- `net/minecraft/world/level/block/Blocks.java` — `684579bbcbe48b1f0984090a4ca044c0b2cee6d08c2dc1e449e2259389c5b129`
- `net/minecraft/world/level/block/state/BlockBehaviour.java` — `5ccc577c6b257badf8bffab54181c45b6c57779c8545efe5c54a9766687e685b`
- `net/minecraft/world/level/block/state/BlockState.java` — `a3eec38ae13ae1e1305960fa13e34d8843c1ae9480c75d6ca1450d590ba9bb7b`
- `net/minecraft/world/phys/AABB.java` — `13ab54ef7b11abbe41465580eb5cddb05a4bad3178995d4609c252d70b96cad9`
- `net/minecraft/world/phys/shapes/CollisionContext.java` — `aa5cd1702e6061dd7db791bb4cd4498f39ad7fdfb2d22e7701d278f21c10fbbf`
- `net/minecraft/world/phys/shapes/EntityCollisionContext.java` — `fa505d477deaeed144816a3ec08512357ee178dc8769534b3a7f59e9a3299587`
- `net/minecraft/world/phys/shapes/Shapes.java` — `ec207e73b8e9de4b1234132f81126973f0cb2bb37646b070d273259f2e91311a`
- `net/minecraft/world/phys/shapes/VoxelShape.java` — `106eb81ee1b2b0f7aa22c225524d4ada28cb28906ffffc9f7e790e4ffefbe9ed`
- `net/minecraft/world/level/material/FluidState.java` — `b52e2b1e889a510b5d80c5eef09cd4f5b63448f15dbb131f02030497340e7c1c`
- `net/minecraft/world/entity/ai/attributes/Attributes.java` — `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e`
