# Independent audit: Shulker provider reachability, 1.11.2 → 1.12

Verdict: **ACCEPT** for the exact dynamic Shulker collision-box provider and direct player-push path only. Their behavior is identical at these endpoints. This does not accept other providers, close the neighboring FaceShape dependency, or freeze the pair.

## Bound evidence

- Audited claim/report: `dependencies/shulker-provider-reachability.md`, SHA-256 `fd714797984f6c590871100b85171b25a7a6bd8965ab87e4415851dc93462e44`.
- Provider inventory: `dependencies/provider-callback-inventory.md`, SHA-256 `39052ed2f95606f012876136a3277b2ff9fc55c075d19936e5e384afc6b193d8`.
- Its independent audit: `dependencies/provider-callback-audit/verdict.md`, SHA-256 `0bb66867a2a769aa52588ace79ef592c8ff8b5cc98c4cf726f5e8e735bb1129c`.
- Pair coverage audit r2: `audit/full-pair-verdict-r2.md`, SHA-256 `82145bec322ff77fecf967fa5cf0f440f11ecb4b07dbfd499f10b5ca227df80b`.

I independently read the exact `decompiled_minecraft/<version>/ornithe-feather` source files listed below. Hashes are SHA-256 of the source-file bytes.

| Source | 1.11.2 SHA-256 | 1.12 SHA-256 |
| --- | --- | --- |
| `net/minecraft/block/ShulkerBoxBlock.java` | `c3a4082e6dff9b59c61c0ea1fd79fcc6b349c85d4abf47398e414c1cc52d17f5` | `af7c152c3752223234ef406485bb667346d3511143b77f3384847f95ac81c076` |
| `net/minecraft/block/entity/ShulkerBoxBlockEntity.java` | `ae3127af04c5c4159026e91ab3bd4092f91f51ab8090ee40e85404c86b08f119` | `ae3127af04c5c4159026e91ab3bd4092f91f51ab8090ee40e85404c86b08f119` |
| `net/minecraft/block/state/StateDefinition.java` | `95704c944742efb2ec9cba7eb73399a330195556e669451131ba542e6a9fac65` | `934493deb8dad66936edf94264bda735ae8062c6ef091b1ff6a1501eed2b8d37` |
| `net/minecraft/block/Block.java` | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| `net/minecraft/world/World.java` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8` |
| `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/entity/EntityFilter.java` | `49f2c3cbeea0bb9421fdfaad49a7742394f69cd73981a9cdd51e1badcf30c629` | `49f2c3cbeea0bb9421fdfaad49a7742394f69cd73981a9cdd51e1badcf30c629` |
| `net/minecraft/world/chunk/WorldChunk.java` | `b80c4962661b31c9015faa9bb3d42e390528b59473956ba20e424c76625b844f` | `b80c4962661b31c9015faa9bb3d42e390528b59473956ba20e424c76625b844f` |
| `net/minecraft/client/Minecraft.java` | `3ca93038adf4cea512c21ae86d653376e963396640a02eb50cb42a67302f6236` | `308ea5b049c7410d03039a12acde604b8f6f184710cbc8ec53240524cb5e795a` |

## Independent trace

**Collision shape.** In both releases, `ShulkerBoxBlock.getShape(state, world, pos)` gets the block entity at the position and delegates to its `getShape(state)`, falling back to `FULL_BLOCK_SHAPE` if the entity is absent (1.11.2 lines 217–220; 1.12 lines 221–224). The block-entity file is byte-identical across the endpoints. It reads `FACING`; its shape expands `FULL_BLOCK_SHAPE` by `0.5F * getProgress(1.0F) * facingOffset` per axis. `getProgress(1.0F)` evaluates to the current progress. `tickAnimation` copies current to last progress and advances opening/closing by `0.1F`; the expression, float operations, stage transitions and shape timing therefore match.

`Entity.move` asks `World.getCollisions` for the swept box (1.11.2 line 459 onward; 1.12 line 462 onward). `World.getCollisions` visits block states and calls `addCollisions`; `StateDefinition` forwards through `Block.addCollisions` and `getCollisionShape` to `state.getShape(world, pos)`. These forwarding bodies match at the endpoints apart from line offsets. The Shulker box shape is therefore reachable from local-player movement and is not conditionally filtered by the querying entity in this block-provider path. A local player with `noClip` bypasses collision collection in `Entity.move`; otherwise this route applies normally.

**Direct push.** The byte-identical block-entity `tick()` first updates animation, then calls `moveEntities()` while the resulting stage remains `OPENING` or `CLOSING`. When opening reaches full progress, `tickAnimation()` calls `moveEntities()` once immediately before it changes the stage to `OPENED`; closing that reaches `CLOSED` does not take the post-animation stage branch. `moveEntities()` requires the block still be a Shulker box, reads its `FACING`, builds the animated lid box, and calls `world.getEntities(null, box)`. It computes the same axis-directed box displacement plus `0.01` and calls `entity.move(MoverType.SHULKER_BOX, ...)` in both releases.

The client caller invokes `world.tickEntities()` only with a non-null world and while not paused (1.11.2 `Minecraft.java:1394–1420`; 1.12 `:1443–1469`). Its world block-entity loop ticks a Shulker entity only when it is not removed, has a world, and its position is in a loaded chunk inside the world border. The local-player entity query is also identical: `World.getEntities(null, box)` uses `EntityFilter.NOT_SPECTATOR`; `WorldChunk.getEntities` includes an entity only when its shape intersects the lid box and it is not the excluded entity, and `null` excludes none. The Shulker loop then skips only `PistonMoveBehavior.IGNORE`. The base `Entity.getPistonMoveBehavior()` returns `NORMAL`, so an ordinary non-spectator local player indexed in the queried chunk and intersecting the lid box can be moved. There is no local-player-only requirement in the Shulker block-entity routine. Spectators are filtered out by the default query predicate.

## Explicit unresolved boundary

In 1.12, `ShulkerBoxBlock.getFaceShape` is a separate added provider (lines 313–321). It resolves virtual state, reads facing and animation stage, and returns `SOLID` or `UNDEFINED`. It is not called by the dynamic Shulker box collision shape route above. It can be read indirectly by adjacent fence/wall connection logic, whose derived vanilla states can then affect their collision boxes. Whether those resulting fence/wall states and player collision outputs differ from the 1.11.2 equivalents remains unresolved and belongs to the separate neighbor-state crosswalk. This verdict does not close that dependency.

**Accepted boundary:** no Shulker dynamic collision-box or direct Shulker player-push delta on 1.11.2 → 1.12. FaceShape-to-adjacent-fence/wall state and collision effects remain open; no pair-freeze claim.
