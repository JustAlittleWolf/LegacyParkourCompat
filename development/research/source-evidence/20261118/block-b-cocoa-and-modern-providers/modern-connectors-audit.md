# Blind source audit: ordinary connector collisions

## Evidence identity

- 1.21.11 / Mojmap ready marker: `status=ready`, exact `versionId=1.21.11`, source manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` (matches marker).
- 26.1 / unobfuscated ready marker: `status=ready`, exact `versionId=26.1`, source manifest SHA-256 `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba` (matches marker).
- Relevant source files were SHA-256 checked against their corresponding source manifests. Hashes below are lowercase SHA-256; roots are `decompiled_minecraft/1.21.11/mojmap/` and `decompiled_minecraft/26.1/unobfuscated/`.

| File | 1.21.11 | 26.1 |
|---|---|---|
| `world/level/block/CrossCollisionBlock.java` | `5ab90620c172821dd0080599262ab786ab63862c54651bbc396d8b137b1db46d` | `e6cea1c67ebf69f726e3dca7781cf83249a20990c339e4d053f12ec21f1fa05c` |
| `world/level/block/FenceBlock.java` | `5cd60ac6466efbd64db7971ee2f6fcd519d12794be084afe40d1c4e085065326` | `be5e1aa19a07d68457ff21968fb4e264669588fb6e83a86a6f51e76833f17f93` |
| `world/level/block/WallBlock.java` | `30f65426c97e2507c13e55edbd078ab86f12d24a1496adb80e5d3cd44b2880f4` | `4dc61223dce4023deb3a7986983db677ccf4c89a53778692bacb301927b9b345` |
| `world/level/block/IronBarsBlock.java` | `09c0275dec572ad800acf4878ff113ef4ce697a2b8ed830851c5c9a3cfa6759e` | `b8d8467db13454fc5cd24c95827172b8550d5cba3d51930ba8b240b960606e54` |
| `world/level/block/FenceGateBlock.java` | `9f82118f3202e696688b6a83e0fd3cb649169ea1aa25cfe7cf86302a8e40c2ce` | `e9e51b00bf59a66951741e0f0ef28d5d92815df9591e4f229fdb838588d105df` |
| `world/level/block/state/BlockBehaviour.java` | `cf7eff07efb53d95c8ffa633ff38c7d3f454e185f197de49eb4da279a8da9d17` | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` |
| `world/level/BlockCollisions.java` | `05875bb3a6fa49628ac92174e68ec3b5bed1cf39ebd4e7158b1885aead0a6a68` | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` |
| `world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eeFC5A282D60E1011A1A33CC29CF18A3` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| `world/level/block/Blocks.java` | `cc6636f6ace603b32edd3db4c0cfcda60be08e2c6b5def783a698f105b8f7f23` | `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f` |

Note: `Entity.java` 1.21.11 hash is `32314478c6036fa9f3f3cc409c61c622eeFC5A282D60E1011A1A33CC29CF18A3` as emitted by PowerShell (case-insensitive hexadecimal); path-level source manifest entry agrees.

## Retrieval path and provider behavior

In both releases, `LocalPlayer.move` calls `super.move` (1.21.11 `LocalPlayer.java:947-950`; 26.1 `LocalPlayer.java:985+`), so ordinary non-riding movement reaches `Entity.move` and its `collide` / `collideBoundingBox` path (1.21.11 `Entity.java:685`, `1044-1047`, `1118`; 26.1 `Entity.java:731`, `1077-1080`, `1157`). `Entity` gets block colliders from `CollisionGetter.getBlockCollisions(source, box)`. `BlockCollisions` constructs `CollisionContext.of(entity)` when source is non-null and reuses one cached `BlockGetter` for consecutive positions in the same chunk; it reads the block state at each cursor position, asks the context for the collision shape, moves it to world coordinates, then tests intersection. The corresponding 1.21.11 and 26.1 bodies preserve this algorithm and ordering (`BlockCollisions.java:31-92` vs `41-105`).

State collision requests dispatch through `BlockState.getCollisionShape(level,pos,context)` to the owning block's provider (`BlockBehaviour.java:655-656` vs `684-685`). Its no-context overload can use a cached shape computed with `EmptyBlockGetter` and `CollisionContext.empty()` (`BlockBehaviour.java:651-656, 898-926` vs `681-685, 919-947`). Ordinary entity collision goes through `CollisionContext.of(entity)` instead; it does not replace the context-specific retrieval with that empty-context cache. No pair difference found in cache/context selection or ordinary local-player retrieval.

## Family verdicts

No source-confirmed ordinary solid-collision change found for these families at this endpoint pair. Coordinate conventions below are block-local, 0..16 units. Same directional flags and arm/post construction are present on both sides; source symbol names and local names vary only with mappings/decompiler naming.

- **Fences.** `FenceBlock` sets default `N/E/S/W=false` and `WATERLOGGED=false`; state definition has those five properties (1.21.11 `FenceBlock.java:36-42,116-119`; 26.1 `:36-42,124-127`). It inherits `CrossCollisionBlock` collision assembly: a centered post plus the horizontal arm for each true side, accumulated in N/E/S/W order through the same rotated arm map. Fence collision construction arguments remain post width 4, wall width 4, collision height 24 (1.21.11 `FenceBlock.java:36-38`, `CrossCollisionBlock.java:37-60`; 26.1 `FenceBlock.java:36-38`, `CrossCollisionBlock.java:37-69`). Connection rule and direction mapping are unchanged: sturdy neighbor face unless excluded, same fence tag category (wooden vs nonwooden), or connecting gate; north queries neighbor south face, east queries west, etc. Placement and horizontal neighbor updates set matching directional properties. Source status: **no difference**.
- **Walls.** State consists of `UP` plus `N/E/S/W` each `WallSide.NONE/LOW/TALL`, with waterlogging (both `WallBlock.java:52-68` and `:52-68`). Collision-specific function is built separately from outline with 24-high post and 24-high tall-side limit; one centered post is included only when UP is true, with a rotated side cuboid for each non-NONE arm. `LOW` uses wallTop 24 and `TALL` post height 24, effectively same top for this collision function; outline uses its separate 16/14 values. `getCollisionShape` selects precomputed function by state (both `:66-98`). Connections accept wall tags, eligible sturdy faces, iron bars and directionally compatible gates; post/arm updates also inspect shape of block above. No changed masks, arm orientation, cache, or collision-vs-outline selection found. Source status: **no difference**.
- **Iron bars and panes.** `IronBarsBlock` defaults four directional flags and waterlogged false and uses the same inherited cross collision assembly with post width 2, arm width 2 and height 16; arms are attached to eligible sturdy neighbors, any `IronBarsBlock`, or wall-tag states. Placement/update side order and opposite-face direction mapping match. The visual shape override returns empty in both versions, but collision remains the inherited `CrossCollisionBlock` shape. `StainedGlassPaneBlock` and `WeatheringCopperBarsBlock` inherit `IronBarsBlock` in both source trees, so their ordinary collision geometry follows this provider unless overridden (no override found in those class bodies). Source status: **no difference**.
- **Fence gates.** Default state is `OPEN=false`, `POWERED=false`, `IN_WALL=false`; horizontal `FACING` comes from superclass (both `FenceGateBlock.java:65-70`). Collision shape is a rotated 16-wide, 4-deep, y=0..24 wall selected by facing axis; OPEN returns empty. POWERED/IN_WALL do not affect collision. The constants, axis selection, open gate branch and collision method match (1.21.11 `FenceGateBlock.java:47-52,69-70,98-101`; 26.1 `:47-52,69-70,105-108`). Directional gate-connection predicate is also same. Source status: **no difference**.

## Registry and remaining gaps

The registry source confirms built-in base registrations for fences, walls, gates, bars and pane variants in `Blocks.java`; state geometry is shared by block class, not duplicated per named vanilla block. `StainedGlassPaneBlock` and `WeatheringCopperBarsBlock` inheritance was checked directly. Registration families with helper-driven/dynamic weathering entries require a dedicated complete ID-to-class/tag join before claiming registry exhaustion. This audit did not close every registry ID or data-driven tag membership, did not enumerate every block state tuple produced by `StateDefinition`, and did not audit modded blocks. Those are the remaining registry joins; they do not alter the verified provider bodies above.

This is a bounded endpoint source audit only. It does not establish intermediate patch boundaries, other collision families/edges, support behavior, callbacks, AutoJump, block state production beyond the named families, or runtime parity.
