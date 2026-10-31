# WallBlock fixed-state collision audit — Mojmap 1.15.2 → 1.16.5

## Result

**Bounded fixed-state no-delta for local-player collision:** for corresponding UP/post and connected-direction states, 1.15.2 and 1.16.5 `WallBlock` providers return the same collision volume. The LOW/TALL distinction affects outline arm height, not collision arm height. The changed Iron Bars connection can produce a different wall state and therefore a different collision shape; that is the neighboring-state delta already captured in the separate wall-bars lead, not a fixed-state provider delta.

## Exact providers

- 1.15.2 `WallBlock.getCollisionShape` (lines 49–53) uses `collisionShapeWithPostByIndex` when `UP` is true and delegates to `CrossCollisionBlock` otherwise. Its post table is built at lines 37–38 with a 24-high post and side boxes whose top is 24. The inherited cross-collision table at `CrossCollisionBlock.java` lines 35–38, 41–76 gives each connected side a 24-high collision box. The connection mask is read from NORTH/EAST/SOUTH/WEST; `UP` adds only the post in the WallBlock override.
- 1.16.5 `WallBlock` no longer extends `CrossCollisionBlock`; it precomputes a `Map<BlockState, VoxelShape>` for outline and collision in lines 36–57, 68–113. `applyWallShape` (lines 60–66) chooses LOW or TALL boxes per side. In the collision table, the call at line 57 passes 24 for both the LOW-box top (`j`) and TALL-box top (`k`); therefore both variants span local Y `[0,24]`. The post is the same centered 8×8 footprint up to 24, added only when `UP` is true. `getCollisionShape` (lines 121–123) returns the state-keyed cached result.
- With equivalent direction masks, both endpoints give each connected side the same X/Z footprint (for example north: X `[5,11]`, Z `[0,11]`) and Y `[0,24]`; the post is the same when `UP=true`. The new shape map iterates the four side values and unions the same boxes. `WATERLOGGED` has no effect on either endpoint's collision geometry. Thus no fixed-state collision volume difference is established for LOW/TALL, cache construction, or post handling.
- The outline providers do differ: the old wall always uses 14-high connected arms (post height 16); the new `makeShapes` call at line 56 uses top 14 for LOW arms and top 16 for TALL arms (post height 16). This is not a local-player collision delta on the traced `Entity.move` path, which requests `getCollisionShape`.

## Cache and dispatch closure

The old `BlockState.getCollisionShape` (lines 192–198) uses its cached collision shape when present and otherwise calls the block provider; `Block.BlockState.Cache` (lines 415–424) computes the cached value through that provider. In 1.16.5, `BlockBehaviour.BlockStateBase.getCollisionShape` (lines 524–530) and `BlockBehaviour.BlockStateBase.Cache` (lines 752–765) retain the same provider-dispatch pattern. WallBlock’s collision provider uses only the supplied fixed state and its precomputed map/table, so the cache does not change the LOW/TALL result. Its signature accepts world, position and collision context, but neither implementation consults them.

The local-player route reaches this provider through `Entity.move` → `level.getBlockCollisions`. The 1.15.2 `CollisionGetter` obtains `CollisionContext.of(entity)` and calls the state collision provider for candidate cells; 1.16.5 `CollisionSpliterator` does the same at lines 33–40 and 70–85. The collector refactor does not mask the wall provider result. `WallBlock` instances are registered in both `Blocks.java` files, and the existing wall registrations use the same source block family.

## Neighbor/state dependencies and limits

The compared collision output is fixed once a wall state is supplied. State production is not closed here: 1.16.5 changes side properties from booleans to NONE/LOW/TALL, chooses LOW/TALL using the collision shape of the block above (`updateShape` lines 213–257), and adds an IronBars connection case (`connectsTo` lines 130–134). The 1.15.2 placement/update path computes Boolean sides and a top-post condition (`getStateForPlacement` lines 67–90; `updateShape` lines 93–123); its existing IronBars no-connection case is already documented in the separate wall-bars lead. Placement, neighbor notifications, upper-block collision, `WALLS`/`WALL_POST_OVERRIDE` tag membership and tag updates can affect which state is supplied. These dependencies remain open. The prior wall-bars lead establishes a neighbor-driven connection delta, which can change the supplied side mask and hence the collision volume despite the fixed-state provider parity found here.

No runtime, implementation, finding/backlog write, tests, build, game launch or decompilation was performed. No intermediate patch boundary or runtime parity is claimed.

## Evidence identity

Exact roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.5\mojmap`. Canonical manifest pins: `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` and `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`. The corrected pair inventory pin is `835ba455d3dd4825f01a74f282eee21c42254cee3870a400ee7f96d9824fbcd5`; its exact path row marks `WallBlock.java` modified. Individual source and bounded lead hashes are in `evidence.sha256`.
