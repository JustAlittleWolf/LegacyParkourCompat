# Shulker provider reachability — 1.11.2 → 1.12

## Binding and limits

This closes only the dynamic Shulker box provider/caller dependency named in the dispatch. It is not a general provider inventory, pair freeze, or acceptance of other findings. Sources are the read-only exact `ornithe-feather` trees for 1.11.2 and 1.12.

Input reports verified by SHA-256:

- `coverage.md`: `da8f52d9b0e6e6445612cdf1f80096712e10b824f72c3d499f829b6024313514`.
- `dependencies/provider-callback-inventory.md`: `39052ed2f95606f012876136a3277b2ff9fc55c075d19936e5e384afc6b193d8`; its named audit: `0bb66867a2a769aa52588ace79ef592c8ff8b5cc98c4cf726f5e8e735bb1129c`.
- `audit/full-pair-verdict-r2.md`: `82145bec322ff77fecf967fa5cf0f440f11ecb4b07dbfd499f10b5ca227df80b`.

Source identities supplied by the source lane: 1.11.2 `ornithe-feather.ready.json` SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`; 1.11.2 `ornithe-feather.sources.sha256` SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; 1.12 `.decompile-provenance.json` SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`.

## Exact paired trace

`net/minecraft/block/ShulkerBoxBlock.java` (1.11.2: `getShape`, lines 217–220; 1.12: lines 221–224) delegates to the block entity when present and otherwise returns `FULL_BLOCK_SHAPE`. Both versions have the same provider body. The 1.11.2/1.12 `ShulkerBoxBlockEntity.java` files have the same SHA-256, `ae3127af04c5c4159026e91ab3bd4092f91f51ab8090ee40e85404c86b08f119`. In both, `getShape(BlockState)` reads `FACING`; `getShape(Direction)` expands the full block by `0.5F * getProgress(1.0F) * facing offset` on each axis (lines 88–99). Opening/closing progress and its `0.1F` steps are identical (lines 51–82). Thus direction and animation progress determine the collision box, with the same expression, float precision, and stage timing at both endpoints.

The player movement route is present at both endpoints: `Entity.move` (1.11.2 line 459; 1.12 line 462) requests swept collision boxes from `World.getCollisions(this, shape)` (1.11.2 lines 564, 610; 1.12 lines 567, 613). `World.getCollisions` enumerates block positions and calls each state’s `addCollisions` (1.11.2 lines 960–1015; 1.12 lines 964–1019). `StateDefinition` forwards `addCollisions` to the block and `getCollisionShape` to `Block.getCollisionShape`; the base block then calls `state.getShape(world, pos)` (1.11.2 `StateDefinition.java:369–375`, `Block.java:353–370`; 1.12 `StateDefinition.java:370–376`, `Block.java:372–388`). This reaches the dynamic Shulker box `getShape` above. The block collision box itself is not filtered by entity type in this route.

There is also a separate direct response path in the identical block-entity code. While its stage is `OPENING` or `CLOSING`, `tick()` calls `moveEntities`; the opening completion path calls it immediately before setting `OPENED` (both versions, `ShulkerBoxBlockEntity.java:51–70`). `moveEntities` checks the block state is a Shulker box, reads its facing, forms the animated lid box, and queries `world.getEntities(null, box)` (lines 106–115). It skips entities whose piston move behavior is `IGNORE`, computes a directional displacement from the lid/entity boxes plus `0.01`, then calls `entity.move(MoverType.SHULKER_BOX, ...)` (lines 119–150). A local player intersecting the lid and not returning `IGNORE` can therefore be moved if this block entity ticks in the client world. The exact gate and response are identical at both endpoints. This entity query/filter is distinct from `World.getCollisions(entity, shape)`, whose additional entity-box pass excludes same-vehicle entities and checks collision shapes (1.11.2 lines 1013–1033; 1.12 lines 1017–1037); it does not alter the Shulker block-box path.

1.12 adds `ShulkerBoxBlock.getFaceShape` (`ShulkerBoxBlock.java:313–321`), absent in 1.11.2. It resolves the state’s virtual properties, reads `FACING` and the block entity’s animation stage, and returns `SOLID` when closed or when opened on one of the two facing-normal faces; transitional animation and other opened faces return `UNDEFINED`. This provider reads no moving box dimensions and is not called directly by `Entity.move`/`World.getCollisions`. There is, however, a conditional indirect route: `FenceBlock.shouldConnectTo` (`FenceBlock.java:124–130`) and `WallBlock.shouldConnectTo` (`WallBlock.java:146–152`) query the adjacent state’s face shape, then their virtual-property resolution feeds their collision providers (`FenceBlock.java:59–60, 83`; `WallBlock.java:91–102, 190–196`). If a Shulker face result changes one of those neighbor connection states, its derived collision box can reach player movement through the block-collision route above. `PaneBlock.shouldConnectTo` (`PaneBlock.java:136–144`) explicitly excludes Shulker boxes. Whether fence/wall state and box outputs differ from the 1.11.2 equivalents belongs to the separately assigned connection candidates and was not reassessed here; support/placement-only state production remains excluded under the keep-vanilla-states invariant.

## Disposition and remaining gate

**Shulker dynamic collision provider and block-entity player push: no difference across this endpoint pair.** The animated box math, collision delegation, and direct push gate/response are identical. The 1.12 FaceShape addition is a separate provider; it can reach player collision only indirectly through the named fence/wall connection-state callers if those callers derive a different collision box. That downstream comparison remains a bounded gate owned by the existing connection candidates, so this report makes no movement-delta claim for that route and supplies no Shulker-specific implementation delta.

The direct dynamic Shulker collision and push paths are closed as no-difference. Remaining bounded gate: the connection-candidate owner must determine whether the 1.12 Shulker FaceShape result changes adjacent fence/wall connection state and collision output versus 1.11.2. Other open provider/callback edges and the independent full-pair coverage/audit requirements in the bound reports remain unchanged; this result makes no pair-freeze claim.

## Source file identities

SHA-256 of exact source files read:

| Release | Relative source path | SHA-256 |
| --- | --- | --- |
| 1.11.2 | `net/minecraft/block/ShulkerBoxBlock.java` | `c3a4082e6dff9b59c61c0ea1fd79fcc6b349c85d4abf47398e414c1cc52d17f5` |
| 1.12 | `net/minecraft/block/ShulkerBoxBlock.java` | `af7c152c3752223234ef406485bb667346d3511143b77f3384847f95ac81c076` |
| 1.11.2 / 1.12 | `net/minecraft/block/entity/ShulkerBoxBlockEntity.java` | `ae3127af04c5c4159026e91ab3bd4092f91f51ab8090ee40e85404c86b08f119` (both) |
| 1.11.2 | `net/minecraft/block/state/StateDefinition.java` | `95704c944742efb2ec9cba7eb73399a330195556e669451131ba542e6a9fac65` |
| 1.12 | `net/minecraft/block/state/StateDefinition.java` | `934493deb8dad66936edf94264bda735ae8062c6ef091b1ff6a1501eed2b8d37` |
| 1.11.2 | `net/minecraft/block/Block.java` | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` |
| 1.12 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.11.2 | `net/minecraft/world/World.java` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` |
| 1.12 | `net/minecraft/world/World.java` | `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8` |
| 1.11.2 | `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` |
| 1.12 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.12 | `net/minecraft/block/FenceBlock.java` | `633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e` |
| 1.12 | `net/minecraft/block/WallBlock.java` | `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50` |
| 1.12 | `net/minecraft/block/PaneBlock.java` | `83862f6633b8241c3788e9c9a7e4fd0275c010ec790019b8003908996f4814b3` |
