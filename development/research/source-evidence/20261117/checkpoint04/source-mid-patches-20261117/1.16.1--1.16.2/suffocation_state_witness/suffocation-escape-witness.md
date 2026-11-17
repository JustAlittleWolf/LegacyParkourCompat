# Vanilla-state witness: LocalPlayer suffocation escape (1.16.1 → 1.16.2)

## Scope and source identity

This packet closes the concrete vanilla block-state/collision-filter gate for the bounded candidate `ordinary_player/local-player-suffocation-escape.md` (draft SHA-256 `868DF34DD179E59546793F38DCE241395C8B8B3A3CA676A51EB012C2E56866B5`; parent coverage report SHA-256 `A542F16666AED2E489296B8AF886D7717092A4809EADC516DAEC79F11D57967A`; bounded report/audit ACCEPT SHA-256 `5203F7819178EB5EE399CD8DE31DFD5482B82B5657A4277923B9790BD3FED57E`). This is a static state witness only; it does not freeze the exact release pair or settle the wider dependency inventory.

Both sides are exact Mojmap publications. I verified the listed root manifests and provenance bytes against the source-input README:

- 1.16.1: `decompiled_minecraft/1.16.1/mojmap/`; `mojmap.sources.sha256` SHA-256 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`; `mojmap.provenance.json` SHA-256 `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98`.
- 1.16.2: `decompiled_minecraft/1.16.2/mojmap/`; `mojmap.sources.sha256` SHA-256 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`; `mojmap.provenance.json` SHA-256 `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`.

Every source file hash below was recomputed from disk and matched its exact endpoint manifest row.

## Concrete setup and reachability

Use an ordinary standing player centered at `(0.5, 4.8, 0.5)`, with vanilla `FARMLAND` at block `(0,4,0)`, air above/adjacent, and a normal solid support block below. The coordinates are within the 1.16 world bounds. `Player.STANDING_DIMENSIONS` are 0.6 wide and 1.8 high; `EntityDimensions.makeBoundingBox` uses the entity y as the box minimum. The player's AABB is therefore x/z `[0.2,0.8]` and y `[4.8,6.6]`, overlapping farmland's collision shape from y=4 through y=4.9375.

This is a vanilla map setup: the built-in `TeleportCommand` routes a same-world `ServerPlayer` through `ServerGamePacketListenerImpl.teleport`, which applies the supplied coordinates and sends `ClientboundPlayerPositionPacket`; `ClientPacketListener.handleMovePlayer` applies those coordinates to the local player. A command-block or other map-controlled `/tp` to the stated position is sufficient. This establishes a source-reachable ordinary vanilla position/state combination without relying on arbitrary block overrides or runtime. The packet shows an externally positioned transient case; it does not show that self-propelled movement can enter the farmland collision shape.

Farmland is a legal state here: its `FarmBlock.canSurvive` only tests the block above (air is allowed), and the registered block has `Material.DIRT`. Its collision shape is a full x/z slab with height 15/16, not a full cube. A solid support beneath is compatible with the built-in placement/survival checks.

## Old and new response for the setup

**1.16.1:** `LocalPlayer.aiStep` calls `checkInBlock` four times at x/z offsets ±`getBbWidth() * 0.35` and y=`getY()+0.5`. At x=z=0.5 with width 0.6, all four x/z probes floor to farmland's `(0,4,0)` column. `blocked` checks each integer y from `floor(AABB.minY)` through `< ceil(AABB.maxY)`, calling inherited `Player.freeAt`, which returns the negation of `BlockState.isSuffocating`. In this release the `FARMLAND` registration sets `.isViewBlocking(Blocks::always)` but does not override `.isSuffocating`; the default suffocation predicate requires both `Material.blocksMotion()` and a full collision shape. The dirt material blocks motion, but this 15/16-high farmland shape is not a full collision cube, so `isSuffocating` is false. Every `blocked` probe is false and no escape velocity write occurs; with initial horizontal velocity `(0,0)`, it remains unchanged.

**1.16.2:** The `FARMLAND` registration newly sets `.isSuffocating(Blocks::always)`, and `Blocks.always` returns true. `LocalPlayer.aiStep` calls `moveTowardsClosestSpace` at the same four x/z probes. Each probe's `suffocatesAt` checks a nearly whole block-column prism over the player's y interval, deflated by `1.0E-7`, with `CollisionGetter.noBlockCollision` filtered by `BlockState.isSuffocating`. The deflated query spans y `[4.8000001, 6.5999999]`; it geometrically intersects the farmland collision shape over y `[4,4.9375]` for 0.1375 blocks. `CollisionSpliterator` applies the suffocating predicate and tests shape/AABB intersection, so the current column is treated as suffocating. The adjacent cells are air; `Blocks.AIR` has no collision. With the strict nearest-edge comparisons and west/east/north/south order, probes one and two choose west; probes three and four choose east. The last call writes x velocity `+0.1`, so initial horizontal velocity `(0,0)` becomes `(0.1,0)`.

Thus, for this concrete vanilla state, the old response leaves horizontal velocity unchanged while the new response writes `+0.1` on x. The difference depends on the new farmland suffocation predicate and the collision-shape intersection filter together; it is not a blanket claim that every suffocating block or every embedded position changes.

## Exact source anchors and hashes

All paths below are relative to the endpoint source root named above.

| Endpoint / file | SHA-256 | Relevant source anchor |
|---|---|---|
| 1.16.1 `net/minecraft/client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `checkInBlock` 414–455; `blocked` 460–470; `aiStep` 682–685 |
| 1.16.2 `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | `moveTowardsClosestSpace` 414–441; `suffocatesAt` 443–447; `aiStep` 657–660 |
| 1.16.1 `net/minecraft/world/entity/player/Player.java` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` | `freeAt` 1421–1423 |
| 1.16.2 `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | `STANDING_DIMENSIONS` 112–119 |
| 1.16.1 `net/minecraft/world/level/block/Blocks.java` | `61f18f903f7d96731bbc3cac544a8614f55fddac872dc0b0fee5a53511c313a9` | `FARMLAND` 495–498: no explicit suffocation override |
| 1.16.2 `net/minecraft/world/level/block/Blocks.java` | `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12` | `AIR` 35; `FARMLAND` 494–503; `always` 3189–3191 |
| 1.16.1 `net/minecraft/world/level/block/FarmBlock.java` | `030dce448154c6f07adefaaba8e21d8815547fc37acc8d98850f5a42334cbed5` | `SHAPE` 29; `canSurvive` 48–51; `getShape` 66–68 |
| 1.16.2 `net/minecraft/world/level/block/FarmBlock.java` | `1298367814491ef92fe8c0f43f1c14e173e54182859c3a68fa9422b60d23c810` | same 15/16 shape and survival predicate |
| 1.16.1 `net/minecraft/world/level/block/state/BlockBehaviour.java` | `1b524b5c45e3be4397dd680b9bd12c937497b9088e623428cf6387406018ef7f` | default suffocation predicate 786–788 |
| 1.16.2 `net/minecraft/world/level/block/state/BlockBehaviour.java` | `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b` | default predicate 806–808; collision-shape delegation 275–277; predicate setter 943–945 |
| 1.16.1 `net/minecraft/world/level/material/Material.java` | `e307629ca8dd5abde8fbd4581307a43308f0b7b37f45e6a5bd12175c2589b7de` | `DIRT` 67; builder `blocksMotion = true` 147 |
| 1.16.2 `net/minecraft/world/level/material/Material.java` | `bdf12e15ec1d4ce7520c88fcddefd8ae1a208f84e4e6ff0143d79fbed4bb0f4e` | `DIRT` 74; builder `blocksMotion = true` 154 |
| 1.16.1 `net/minecraft/world/level/block/Block.java` | `ca9a413bf8198402ec9f2febb65c2e89eef4386cb0f1f3d36650f4047d896655` | `box` scales coordinates from sixteenths (method near 131) |
| 1.16.2 `net/minecraft/world/level/CollisionGetter.java` | `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac` | `noBlockCollision` 62–64 |
| 1.16.2 `net/minecraft/world/level/CollisionSpliterator.java` | `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297` | predicate + collision-shape/AABB intersection 73–84 |
| 1.16.2 `net/minecraft/server/commands/TeleportCommand.java` | `2f24d880b488b3a9eb4a327ca66272547dcf58c86528fb2fa4619e4e9c1884d4` | player target routed to connection teleport 268–279 |
| 1.16.2 `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `1837e3179a34c4905462e8bc3b881b2795b43cc945369225b64c48f5a2aabf05` | `teleport` applies `absMoveTo` and sends position packet 909–923 |
| 1.16.2 `net/minecraft/client/multiplayer/ClientPacketListener.java` | `4d043b866c970910ef079334b0d506f7bc0f78f679e5225f11a0418bab7a72f9` | `handleMovePlayer` applies raw position and `absMoveTo` 671–733 |
| 1.16.2 `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `setPos` constructs bounding box 331–334 |
| 1.16.2 `net/minecraft/world/entity/EntityDimensions.java` | `6a4ded79d936bb740232ae923372e47d24a297a35eaba22e9d18fbd637a7eedf` | `makeBoundingBox` 15–27 |

## Limits

This establishes one exact legal vanilla-state witness for the player suffocation-escape response through a vanilla position-teleport path. It does not prove self-propelled entry into farmland, generated-world frequency, all suffocating blocks, datapack behavior, or the rest of 1.16.1→1.16.2 dependencies. It makes no runtime claim and does not freeze the pair.
