# Shulker FaceShape effect on adjacent fences and walls — 1.11.2 → 1.12

## Scope and bound inputs

This closes only whether the 1.12 Shulker box FaceShape provider changes adjacent fence or wall connection state and collision output compared with 1.11.2. It does not add a trajectory claim or freeze the pair. Sources are exact `ornithe-feather` decompilations.

Reviewed immutable inputs:

- `dependencies/shulker-provider-reachability.md`, SHA-256 `fd714797984f6c590871100b85171b25a7a6bd8965ab87e4415851dc93462e44`; independent ACCEPT verdict `dependencies/shulker-provider-reachability-audit/verdict.md`, SHA-256 `ad157596dd942123e1db378a5cc9ad3d67f91c57e8e8705439b418e09f215ca0`.
- Corrected `dependencies/connected-shape-state-crosswalk-r2.md`, SHA-256 `EDA620F3A69BF7115B45DEC4328CC48E2E23A720595D66E34B0AE83EAA3694DE`; independent ACCEPT verdict `dependencies/connected-shape-state-crosswalk-audit-r2/verdict.md`, SHA-256 `87DA39170D47770FF91890486A11B8713493F9065297A2A57E691A0176C69B65`.
- Source identities from the accepted Shulker report: 1.11.2 Feather ready marker SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40` and source-list sidecar SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; 1.12 `.decompile-provenance.json` SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`.

## Exact source trace

**FaceShape by direction (1.12 only).** `net/minecraft/block/ShulkerBoxBlock.java`, `getFaceShape` (lines 313–321), resolves virtual properties, reads `FACING` and the block entity animation stage, then returns `SOLID` for every queried direction when `CLOSED`; for `OPENING` or `CLOSING`, it returns `UNDEFINED` for every direction; for `OPENED`, it returns `SOLID` only when the queried face equals `FACING` or `FACING.getOpposite()`, and `UNDEFINED` on the other four faces. Thus the open-stage result is axis-paired and covers all six directions. 1.11.2 has no `ShulkerBoxBlock.getFaceShape` method.

The separate collision-shape provider is unchanged: both `ShulkerBoxBlock.getShape` methods delegate to the block entity when present and otherwise use the full-block fallback (1.11.2 lines 217–220; 1.12 lines 221–224). The same byte-identical `ShulkerBoxBlockEntity` in both endpoints reads `FACING` and expands the box by the same progress expression. The FaceShape addition therefore does not itself alter the Shulker box's own collision box.

**Neighbor predicates and all adjacency cases.** In 1.11.2, `ShulkerBoxBlock.isCube` returns `false` (line 62). Both `FenceBlock.shouldConnectTo` (lines 124–133) and `WallBlock.shouldConnectTo` (lines 148–157) only accept an ordinary solid neighbor through `material.isSolidBlocking() && blockState.isCube()`; their other accepted cases are same-material fence / fence gate for fences, or same wall / fence gate for walls. A Shulker box is neither those special neighbor types nor a cube. It therefore yields no fence or wall connection on any horizontal side in 1.11.2.

In 1.12, `FenceBlock.shouldConnectTo` (lines 124–139) and `WallBlock.shouldConnectTo` (lines 146–160) read the adjacent state's FaceShape for the face toward the fence/wall. A `SOLID` result alone is insufficient: the solid-face arm rejects `Block.isExceptionForAttachment(block)`. `Block.isExceptionForAttachment` includes `isExceptionForPlacement`, which explicitly includes every `ShulkerBoxBlock` (1.12 `Block.java`, lines 224–233). The other predicate arms require `MIDDLE_POLE` (fence, plus matching material or gate) or `MIDDLE_POLE_THICK` (wall, or the fence-gate case); Shulker FaceShape never returns either pole shape. Therefore closed Shulkers, opened Shulkers on either facing-normal face, opened Shulkers on the other four faces, and either transitional stage all produce **no connection** for both fences and walls. The four horizontal adjacency cases map to opposite queried faces (`north→SOUTH`, `east→WEST`, `south→NORTH`, `west→EAST`); each remains disconnected. Vertical FaceShape values are included in the six-direction table above but are not queried by these fence/wall neighbor-state resolvers.

The accepted gate-axis fence/wall collision witnesses depend on the derived horizontal connection properties. Because a Shulker neighbor leaves those properties false in both endpoints, it cannot alter the accepted gate-axis witness or any other fence/wall collision box through this provider. The Shulker FaceShape change is consequently a source-backed no-impact for these adjacent blocks.

**Ordinary player query.** `Entity.move` in both releases obtains swept collision boxes through `World.getCollisions` (1.11.2 lines 459 and 564/610; 1.12 lines 462 and 567/613). The fence and wall `addCollisions` methods resolve their virtual properties when `forceShape` is false, then emit collision boxes from those properties. This is the ordinary movement query route; it sees the unchanged disconnected Shulker-adjacency state. The special `forceShape=true` branch consumes a supplied state and is outside this adjacency-resolution claim, consistent with the accepted crosswalk's caller boundary.

## Result and next gate

**No Shulker FaceShape impact on adjacent FenceBlock or WallBlock collision-state resolution, 1.11.2 → 1.12.** The added provider can return directional `SOLID` values, but the 1.12 fence/wall exception gate filters Shulker boxes, while the 1.11.2 cube predicate already rejects them. This closes the Shulker-specific adjacent fence/wall dependency for all six FaceShape directions and all animation stages; it does not revise the broader accepted fence/wall witnesses or claim full-pair freeze. Next gate remains the pair's other independently recorded provider/callback dependencies and full-pair coverage/audit.

## Source-file identities

SHA-256 of exact source-file bytes read:

| Release | Relative source path | SHA-256 |
| --- | --- | --- |
| 1.11.2 | `net/minecraft/block/ShulkerBoxBlock.java` | `c3a4082e6dff9b59c61c0ea1fd79fcc6b349c85d4abf47398e414c1cc52d17f5` |
| 1.12 | `net/minecraft/block/ShulkerBoxBlock.java` | `af7c152c3752223234ef406485bb667346d3511143b77f3384847f95ac81c076` |
| 1.11.2 / 1.12 | `net/minecraft/block/entity/ShulkerBoxBlockEntity.java` | `ae3127af04c5c4159026e91ab3bd4092f91f51ab8090ee40e85404c86b08f119` (both) |
| 1.11.2 | `net/minecraft/block/FenceBlock.java` | `856cb92d4613289747a34a6bc85f306ca9d44b7057a45c79f2020d13025d1cf7` |
| 1.12 | `net/minecraft/block/FenceBlock.java` | `633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e` |
| 1.11.2 | `net/minecraft/block/WallBlock.java` | `17da6d881365e4dce5134f88cb9a9e5273d1fa1734812d13410dc6924a3acbfd` |
| 1.12 | `net/minecraft/block/WallBlock.java` | `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50` |
| 1.12 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.11.2 | `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` |
| 1.12 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.11.2 | `net/minecraft/world/World.java` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff849a9a7d0cb82efb58` |
| 1.12 | `net/minecraft/world/World.java` | `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8` |





