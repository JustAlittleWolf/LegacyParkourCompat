# Auto-jump collision route: 1.20.6 → 1.21 → 1.21.1

## Verdict

The `LocalPlayer.updateAutoJump` movement-response algorithm and its collision-query machinery are equivalent in all three exact source trees. For the same valid vanilla block states and shared tag memberships, this bounded route has no remaining source candidate. The only observed collision-related tag change is the 1.21 addition of three new tuff-wall block IDs to `minecraft:walls`; none of the 1.20.6 members are removed or reassigned, and those IDs are not states shared with 1.20.6.

This closes only the automatic-jump response route below. It is not a pair-wide coverage or freeze claim. No build, tests, game launch, or runtime parity check was performed.

## Exact source identity

All sources were read from the canonical read-only trees at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{version}\mojmap`.

| Version | Identity evidence |
| --- | --- |
| 1.20.6 Mojmap | `1.20.6/mojmap.ready.json`: versionId `1.20.6`, mapping `mojmap`, 5,329 source files, client SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`; ready-file SHA-256 `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41`. `mojmap.sources.sha256` SHA-256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`, matching the ready marker. |
| 1.21 Mojmap | `1.21/mojmap/.decompile-provenance.json` SHA-256 `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`. Embedded inputs identify client JAR SHA-256 `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8` and Mojang `client.txt` SHA-256 `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`. The embedded `sources` hashes for every cited 1.21 source file were checked against the file contents. |
| 1.21.1 Mojmap | `1.21.1/mojmap.ready.json`: versionId `1.21.1`, mapping `mojmap`, 5,363 source files, client SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`; ready-file SHA-256 `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310`. `mojmap.sources.sha256` SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`, matching the ready marker. |

The principal source identities are `LocalPlayer.java` 1.20.6 `6b429dfa…ed46ffa`, 1.21 `c555e68a…8bc24b583`, 1.21.1 `c555e68a…8bc24b583`; `CollisionGetter.java` `f21daa1e…11ccb836`, `BlockCollisions.java` `3313a38f…cf0506b`, and `CollisionContext.java` `aa5cd170…c10fbbf` are byte-identical across all three. Abbreviated class digests here are fully recorded in the source manifests/provenance above.

## Route and gates

`LocalPlayer.move` captures the old X/Z, calls `super.move`, then unconditionally calls `updateAutoJump((float)(newX-oldX), (float)(newZ-oldZ))` (`LocalPlayer.java`: 1.20.6 lines 878–883; 1.21 and 1.21.1 lines 880–886). This is a client-local player class; this route is not a logical-server movement path.

`canAutoJump` gates the response on auto-jump being enabled, `autoJumpTime <= 0`, grounded, not staying on the ground surface (player shift state), not a passenger, nonzero input movement, and `getBlockJumpFactor() >= 1.0` (`LocalPlayer.java`: 1.20.6 lines 1005–1013; 1.21/1.21.1 lines 1008–1016). `autoJumpEnabled` is refreshed from the client option in `aiStep` (1.20.6 line 269; 1.21/1.21.1 line 270); positive `autoJumpTime` is decremented there (1.20.6 lines 676–677; 1.21/1.21.1 lines 673–674). The inherited jump-factor method reads the block at the player position and the block below that affects movement, returning the current block's factor unless it is `1.0` (`Entity.java`: 1.20.6 lines 794–826; 1.21/1.21.1 lines 795–826). The compared `Player.isStayingOnGroundSurface`, `Entity.getBlockJumpFactor`, and `Entity.getBlockPosBelowThatAffectsMyMovement` bodies are equal across the trio; the latter's access modifier changes from protected to public in 1.21 without changing its body.

## Collision and height route

The three `updateAutoJump` bodies are byte-for-byte equal after extracting the method (method-body SHA-256 `8ea19121b3ec8d54b66dc1c40c91604d8e444b3f36b575b93a05a1d8f2716182`); the `canAutoJump` bodies are also equal (`1cce3e3a1cc35ff81a13a530c94635c787a39621ee60b3090e6994b69e4ae2ef`). The operation order is:

1. Use the post-move X/Z delta; if its squared length, cast to float, is at most `0.001F`, construct a fallback horizontal vector from current move input, speed, and yaw using `Mth.sin/cos`. Return if the fallback is also at or below the threshold. Normalize with `Mth.invSqrt`, then reject a direction whose dot product against `getForward()` is below `-0.15F`.
2. Build `CollisionContext.of(this)`. Starting at `BlockPos.containing(x, boundingBox.maxY, z)`, query that state and the one above using `getCollisionShape(level, pos, context)`; continue only when both shapes are empty.
3. Set the height limit to `1.2F`, adding `(jumpAmplifier + 1) * 0.75F` when the player has Jump. The scan distance is `max(speed * 7.0F, 1.0F / inverseSqrtResult)`. Build the swept AABB from old position to projected endpoint plus player height, inflate X/Z by player width, and create two lateral probe segments offset by `0.51F` vertically and half the scaled cross-product width.
4. Call `level.getCollisions(this, sweptAabb)`. Iterate returned shapes in order, flatten each shape via `toAabbs()`, and take the first AABB intersecting either probe. Initialize the candidate top from that AABB's `maxY`; sample block states above its center for integer heights `1 .. < heightLimit`, replacing the top with each nonempty context-sensitive collision shape's `max(Y) + blockY`. Return if any sampled top exceeds the limit. For loop heights above one, increment the original headroom position and return on any nonempty collision shape there.
5. If a candidate top was found, cast `top - playerY` to float. Only when the rise is greater than `0.5F` and no greater than the height limit, write `autoJumpTime = 1`.

The last write is the only action performed by `updateAutoJump`: it does not call a jump method or write velocity. The method's local shape scans and early returns leave the timer unchanged.

`CollisionGetter.getCollisions` is unchanged and concatenates entity collision shapes before block collision shapes (`CollisionGetter.java: 77–80`). It does not add a world-border shape. The entity branch uses unchanged `EntityGetter.getEntityCollisions` (`EntityGetter.java: 57–74`), unchanged `Level.getEntities(Entity,AABB,Predicate)` and `LevelEntityGetterAdapter` / `EntitySectionStorage` AABB collection; `Entity.canCollideWith`, `Entity.getBoundingBox`, and the resulting `Shapes.create(entityBox)` route are unchanged. The block branch is `getBlockCollisions` → `BlockCollisions` (`CollisionGetter.java: 83–85`; `BlockCollisions.java: 32–96`): the same expanded cursor bounds, chunk lookup, visit order, type checks, `hasLargeCollisionShape` filter, `BlockState.getCollisionShape(context)`, shape translation, and `Shapes.joinIsNotEmpty` test are used. `Level.getChunkForCollisions`, `LevelReader.getChunkForCollisions`, `Level.getBlockState`, and `LevelChunk.getBlockState` method bodies are equal across the trio.

The context-aware `BlockBehaviour.BlockStateBase.getCollisionShape(BlockGetter, BlockPos, CollisionContext)` directly calls the block's context-aware collision-shape method (`BlockBehaviour.java: 633–634` in all three); it does not use the no-context cached-shape overload. `hasLargeCollisionShape` and the base collision-shape method are also equal. This closes the state lookup, broadphase, and context dispatch used by the collector.

The method bodies used for `AABB.intersects(Vec3,Vec3)` and `intersects(double...)`, `AABB.getCenter/getSize/inflate`, `VoxelShape.max/toAabbs/forAllBoxes/move/getCoords`, `Shapes.create(AABB)/joinUnoptimized/joinIsNotEmpty`, `ArrayVoxelShape.getCoords`, `CubeVoxelShape.getCoords`, `SliceShape.getCoords`, `BitSetDiscreteVoxelShape.forAllBoxes`, `Cursor3D.advance/getNextType`, `Mth.floor/sin/cos/invSqrt`, `SectionPos.blockToSectionCoord/asLong`, `ChunkPos.asLong`, and `BlockPos.containing/above/MutableBlockPos.set(double...)` were compared and are equal. Supporting `IndexMerger`, `DiscreteVoxelShape`, `BitSetDiscreteVoxelShape`, and `Cursor3D` files are also byte-identical. Some shape source files differ at the first cutover because `getCoords` changes from protected to public (including the abstract `VoxelShape.getCoords` declaration); the compared implementations have identical bodies. Several other containing files differ, so this verdict is based on the invoked methods and same-state provider declarations, not whole-file identity.

### Same-state block shape and tag dependencies

For 1.20.6→1.21, 54 shared Java sources under `world/level/block` have file-hash changes; for 1.21→1.21.1, five do. The context-aware `VoxelShape` provider methods and `VoxelShape` static field definitions in those changed shared block files are unchanged. Added block sources are new states and are excluded by the same-valid-state comparison. This also holds for the shape providers in the five changed 1.21.1 block files.

The neighbor-connection shape paths that consult block tags use `FENCES`, `WOODEN_FENCES`, `WALLS`, and `WALL_POST_OVERRIDE` (`FenceBlock.java: 61–69`; `FenceGateBlock.java: 64–93, 136–137`; `WallBlock.java: 129–146, 238`; `IronBarsBlock.java: 89`). The exact client JARs were verified against the manifest/provenance client hashes above. `FENCES`, `WOODEN_FENCES`, and `WALL_POST_OVERRIDE` are identical across releases. `minecraft:walls` adds only `tuff_wall`, `polished_tuff_wall`, and `tuff_brick_wall` in 1.21, with no removal or change to any 1.20.6 member. The tag sets are identical from 1.21 to 1.21.1. Thus this tag delta only affects newly introduced states, not the shared-state route.

## Terminal route dispositions

| Terminal route | Disposition |
| --- | --- |
| Local-player call, option/input/ground/passenger/surface/jump-factor gates | **Closed:** same invocation, gates, input fallback, numeric operations, and timer update. |
| Direct headroom collision-shape queries | **Closed:** same block positions, context dispatch, emptiness checks, and height limit math. |
| Swept collision collection, entity/block result order, AABB probes, and height sampling | **Closed:** same entity collector, chunk/block collector, shape flattening/intersection, iteration order, and Y-height extraction. |
| Shared vanilla block collision shapes and relevant tag membership | **Closed for shared states/tags:** shape-provider bodies/constants and shared tag members match; the WALLS delta names only new 1.21 states. |
| Cross-version result for 1.21-only block states | **Partial / outside same-state closure:** new tuff-wall state behavior has no 1.20.6 counterpart; this audit establishes no delta for any shared valid vanilla state, not state equivalence for blocks that did not exist in 1.20.6. |
| Action/velocity result | **Closed:** only `autoJumpTime = 1` is written; no velocity or direct jump action is written on this route. |
