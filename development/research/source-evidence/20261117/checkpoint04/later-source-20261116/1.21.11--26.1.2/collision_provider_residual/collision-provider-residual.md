# 1.21.11 → 26.1.2 collision-provider residual: connected and oriented block families

## Scope and disposition

This is a bounded source-only comparison of the remaining direct block collision providers for fences/walls and the state-selected stair/slab providers. It is not a census of every `BlockState.getCollisionShape` implementation, a full provider inventory, or pair closure. The already accepted `Entity.move` kernel and five-provider core-shape slice are reused unchanged. No movement difference was found in this bounded slice.

The source pair is the exact ready pair already bound by `build/later-source-20261116/1.21.11--26.1.2/entity_move_collision/source-report.md`: 1.21.11 Mojmap at `decompiled_minecraft/1.21.11/mojmap/`; 26.1.2 unobfuscated at `decompiled_minecraft/26.1.2/unobfuscated/`. That accepted report binds ready markers `0AD98D0EBD654650492C97EB58BC324D33864787F0CE10D99FAEC3AE2F5B804B` and `F24AF0F4D38543C2BD9C19B50F347D4E89D6027050E4B21AE070734843946BB7`, source manifests `0C4D83FEF84C101D9DB88F331ACEC7960F3C8346228C28995E6CF57BBFCAE555` and `54AE320660CA911A6D20514C965C33EB414FBEDD1AD6727BBAA95FE906AA23C0`, and provenance sidecars `decompiled_minecraft/1.21.11/mojmap.provenance.json` / `decompiled_minecraft/26.1.2/unobfuscated.provenance.json` with hashes `99C9FB741EB5E4AC8FB5780E2158DA324D7C8B6AFC179D494E5986F78FE29F72` / `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A`. All additional cited Java file bytes below were checked against their respective source-manifest rows. The mapping namespace differs across endpoints; source names here are each endpoint's exact published paths.

## Paired source observations

### Fence collision geometry and connection state

`CrossCollisionBlock` constructs cached outline and collision functions from the same parameters in both versions. `makeShapes` constructs a central column, makes horizontal arms with `Block.boxZ`/`Shapes.rotateHorizontal`, and unions exactly the arms whose N/E/S/W boolean state properties are set. Its `getShape` and `getCollisionShape` return the corresponding cached function result (1.21.11 `CrossCollisionBlock.java:37–75`; 26.1.2 `CrossCollisionBlock.java:44–84`). The `FenceBlock` constructor supplies the same `(4,16,4,16,24)` collision dimensions, initializes all four connection properties and WATERLOGGED false, and builds its separate occlusion function with `(4,16,2,6,15)` (1.21.11 `FenceBlock.java:37–42`; 26.1.2 `FenceBlock.java:37–43`).

`FenceBlock.connectsTo` uses the same ordered expression: a neighbor connects if it is not a connection exception and its queried face is sturdy, or if it is a same-category fence, or if it is a direction-compatible gate. `isSameFence` requires `BlockTags.FENCES` and equality of the neighbor and local `WOODEN_FENCES` classification. Placement computes all four directions from opposite sturdy faces; neighbor updates rewrite the corresponding horizontal property with the same predicate. (1.21.11 `FenceBlock.java:60–68, 77–107`; 26.1.2 `FenceBlock.java:60–69, 79–121`.) This yields the same state-to-collision-shape path for this slice.

### Wall collision geometry and neighboring-state selection

Both `WallBlock` constructors default UP true, all four sides NONE, and WATERLOGGED false. They build outline shapes with `(postHeight=16, wallTop=14)` and collision shapes with `(24,24)`. `makeShapes` creates the same 8-wide central post, 6-wide horizontal arms, and for each direction unions the UP post and the selected NONE/LOW/TALL side. `getShape` and `getCollisionShape` return the matching per-state functions (1.21.11 `WallBlock.java:53–98`; 26.1.2 `WallBlock.java:53–98`).

Wall side selection is also paired: connections use `BlockTags.WALLS`, non-exception sturdy neighboring faces, iron bars, or direction-compatible fence gates. The post selector uses existing wall-above/post state, wall side pattern, `BlockTags.WALL_POST_OVERRIDE`, or coverage by the downward face of the upper neighbor's collision shape against the same post test shape. Placement reads the same four horizontal neighbors and upper neighbor; horizontal updates recompute only the touched side while vertical updates recompute the post from above-shape coverage. The predicates, order, shape constants, and boolean state writes match between the exact sources (1.21.11 `WallBlock.java:70–98, 105–130, 135–214`; 26.1.2 `WallBlock.java:70–108, 112–171, 175–254`).

### Stair and slab state-selected collision shapes

`StairBlock` uses the same outer/straight/inner base voxel-shape unions and horizontal/top rotations. `getShape` selects the same map from HALF and SHAPE, then applies the same direction selection from FACING and SHAPE. Placement and horizontal neighbor updates use the same `getStairsShape` order: inspect the stair behind first for an outer corner, then the stair in front for an inner corner; both require matching HALF, differing facing axis, and `canTakeShape` success. `canTakeShape` has the same neighboring stair/facing/half test; otherwise the state falls back to STRAIGHT. (1.21.11 `StairBlock.java:28–42, 74–88, 97–151`; 26.1.2 `StairBlock.java:28–42, 74–88, 97–167`.)

`SlabBlock.getShape` selects the same top, bottom, or full-block shape from TYPE. Its shape constants, default BOTTOM state, and placement TYPE selection are paired; WATERLOGGED does not select a different collision shape (1.21.11 `SlabBlock.java:30–65, 68–78`; 26.1.2 `SlabBlock.java:30–65, 68–80`).

The common default collision dispatch remains `hasCollision ? state.getShape(level,pos) : Shapes.empty()` in `BlockBehaviour.getCollisionShape` at 1.21.11 lines 308–313 and 26.1.2 lines 329–334. These selected providers override it only where described above. The expression/order is the same; broader `BlockBehaviour` behavior is not reopened here.

## Source identities for added rows

The following file hashes were computed from current bytes and matched exactly to the named endpoint source-manifest entries. The accepted movement report supplies exact ready/source/artifact/provenance identities cited above; no full-tree rehash was repeated.

| Canonical relative source path | 1.21.11 Mojmap SHA-256 | 26.1.2 unobfuscated SHA-256 |
|---|---|---|
| `net/minecraft/world/level/block/CrossCollisionBlock.java` | `5AB90620C172821DD0080599262AB786AB63862C54651BBC396D8B137B1DB46D` | `E6CEA1C67EBF69F726E3DCA7781CF83249A20990C339E4D053F12EC21F1FA05C` |
| `net/minecraft/world/level/block/FenceBlock.java` | `5CD60AC6466EFBD64DB7971EE2F6FCD519D12794BE084AFE40D1C4E085065326` | `BE5E1AA19A07D68457FF21968FB4E264669588FB6E83A86A6F51E76833F17F93` |
| `net/minecraft/world/level/block/WallBlock.java` | `30F65426C97E2507C13E55EDBD078AB86F12D24A1496ADB80E5D3CD44B2880F4` | `4DC61223DCE4023DEB3A7986983DB677CCF4C89A53778692BACB301927B9B345` |
| `net/minecraft/world/level/block/StairBlock.java` | `CA07DF20D87CD5E06C4C5939F4CE828D3B52CCD9460103FD566ECA6E23DC749D` | `2A8AD6EEDC5A151AF7A739CCD476F18CAB4FE78E9DF7C1EC601CB9687A77630B` |
| `net/minecraft/world/level/block/SlabBlock.java` | `016AD58B741E761AF8E44E881ABC91FBFD16DDCA8B83B980CD369CA98FA156BF` | `883AC7561B118096D215DEF3E92BCC56336F4527D2A2587F2F4F9E948E162C8E` |
| `net/minecraft/world/level/block/Block.java` | `BD7F69FFE617FA1008C9311AD7C731B23F242ACB5FF3BAB8C1A81E41E7D15A73` | `1693CFB7B84190A2FE664470A56D59E78ED5BD7D722A2BD16888B036D4D8E977` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `CF7EFF07EFB53D95C8FFA633FF38C7D3F454E185F197DE49EB4DA279A8DA9D17` | `9DB85DE84E502903E6FE497F043B58620B92089236FFB213F0B93DB979428D13` |
| `net/minecraft/tags/BlockTags.java` | `9DB30D02BBB896538A35267B2F0E9C957A00FFDBF20BCC0EA025B5A7BBEC38E3` | `EDB77CA338C6E831D47B41D799B2F2CAD432F34EE465BE33A4D1E69EDF0F8C1F` |

## Remaining gates

- The connection predicates depend on `minecraft:fences`, `minecraft:wooden_fences`, `minecraft:walls`, `minecraft:wall_post_override`, and `minecraft:shulker_boxes` (the last through `Block.isExceptionForConnection`). Both `BlockTags.create` helpers use the default `minecraft` namespace, so their exact resource paths use `data/minecraft/tags/block/<key>.json`. This report does not establish exact pinned-archive membership, reload/effective membership, or world-effective neighbor tag state for these keys. A pinned-resource membership packet is the next narrow dependency gate if not already covered by a matching accepted input reference.
- The `isFaceSturdy` neighbor provider route and full population of support-face states used by fence/wall connection updates are not enumerated here. Stair corner state transitions are compared at the caller/order level, but world-state update scheduling and neighbor-state population are not closed.
- This slice does not compare the many other concrete block shape providers or all registrations, including dynamic/context-dependent providers. It therefore does not close the broader `CollisionContext.getCollisionShape → BlockState.getCollisionShape → block provider` residual listed by the accepted `Entity.move` report; no pair freeze, full inventory, tag-membership or runtime claim is made.

No source generation, builds, tests, game/runtime checks, or canonical source changes were performed.
