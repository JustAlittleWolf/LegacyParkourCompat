# 26.1.2 → 26.2 stationary solid movement consumer comparison

Assignment: ordinary logical-client LocalPlayer movement, stationary dry solid block-shape query, and its ordinary collision clipping consumer. Exact mapping namespace is `unobfuscated`. Block-family state/shape implementations are outside this slice.

## Source identity and integrity

- `26.1.2`: ready marker says status `ready`, version `26.1.2`, namespace `unobfuscated`; its recorded `sourceManifestSha256` is `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`, matching the actual `unobfuscated.sources.sha256` file.
- `26.2`: ready marker says status `ready`, version `26.2`, namespace `unobfuscated`; its recorded `sourceManifestSha256` is `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`, matching the actual `unobfuscated.sources.sha256` file.
- The corresponding `unobfuscated.provenance.json` files exist and record Vineflower 1.12.0 / Java 25. The ready markers' `outputPath` values point to `build/movement-campaign-2026-10-07/ready/...`, which is absent in this checkout; provenance also records a previous worktree as the original working directory. The published `decompiled_minecraft/<version>/unobfuscated/` trees are present, and every relied-on source hash below matches its source manifest. Treat this as a stale provenance path pointer; no individual source integrity mismatch was found.

## Bounded source roster and raw source SHA-256

`manifest match` compares the actual source file's SHA-256 with its entry in that version's source manifest.

| Version | Source path | SHA-256 | Manifest match |
|---|---|---|---|
| 26.1.2 | `net/minecraft/client/player/LocalPlayer.java` | 433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe | True |
| 26.1.2 | `net/minecraft/world/entity/player/Player.java` | 44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d | True |
| 26.1.2 | `net/minecraft/world/entity/LivingEntity.java` | c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd | True |
| 26.1.2 | `net/minecraft/world/entity/Entity.java` | 8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf | True |
| 26.1.2 | `net/minecraft/world/level/BlockCollisions.java` | eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e | True |
| 26.1.2 | `net/minecraft/world/level/CollisionGetter.java` | dddff4897e8d5d01e3ac132d85c474e6a3b9bae928e48b8c907aa9eabdb7fb01 | True |
| 26.1.2 | `net/minecraft/world/level/Level.java` | 77cc835fc1a79cd970bc3f4897fe5f398958fcbc874400c297f465f0c7819f01 | True |
| 26.1.2 | `net/minecraft/world/phys/shapes/CollisionContext.java` | 8702d23bea0f119e0d63ce15ff59088559a319283186372fd69adebeed4dcd69 | True |
| 26.1.2 | `net/minecraft/world/phys/shapes/EntityCollisionContext.java` | 2f0a2bfd72a3d35121e3ef4ab80755f04cefaa7dd933988e2246b39ff5c39291 | True |
| 26.1.2 | `net/minecraft/world/phys/shapes/VoxelShape.java` | 419a46bbcc8b48e1c075127954eb243dcc60d10d4087bf256d895cf91a2ff854 | True |
| 26.1.2 | `net/minecraft/world/phys/shapes/Shapes.java` | cfa63cbed77c922a68e7e8a6a3b78b09a22c7de51e0146e481e9c9d26db66f58 | True |
| 26.1.2 | `net/minecraft/world/phys/AABB.java` | 22f23018fd588c2c4893d872f730381127e3d3ddd1a8c960901ecb8dae626392 | True |
| 26.1.2 | `net/minecraft/core/Cursor3D.java` | 9a1fdc92bb95013c7e70da467a0b5e5b1c9787daafcb0e7e3a8c950dd0e7287f | True |
| 26.1.2 | `net/minecraft/core/BlockPos.java` | 881e56bdaa8e852eee71420d2148d7d2a585c7c746d19bf8f466ed06c46f7fd1 | True |
| 26.1.2 | `net/minecraft/core/Direction.java` | 30fb61592f1a154bb8fc239ab8df1cd2b209f63317e0c7dde4d5b9b3e260d754 | True |
| 26.1.2 | `net/minecraft/world/level/block/state/BlockState.java` | df5b24701ee69145a78901ee0e370e063b63b3e74a0aae32c89b2ae45bc6d205 | True |
| 26.2 | `net/minecraft/client/player/LocalPlayer.java` | 8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6 | True |
| 26.2 | `net/minecraft/world/entity/player/Player.java` | 8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531 | True |
| 26.2 | `net/minecraft/world/entity/LivingEntity.java` | 7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a | True |
| 26.2 | `net/minecraft/world/entity/Entity.java` | 7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5 | True |
| 26.2 | `net/minecraft/world/level/BlockCollisions.java` | eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e | True |
| 26.2 | `net/minecraft/world/level/CollisionGetter.java` | 8e7d1a54d27e0f187667439539c59b8892f966f1b9c8107eb162c6d43f830f6c | True |
| 26.2 | `net/minecraft/world/level/Level.java` | 1ae4f565ad909f4e90786089b5b4f125e8a7d18e2ef7d8c376172a5787fc5639 | True |
| 26.2 | `net/minecraft/world/phys/shapes/CollisionContext.java` | 0af02ec998df9e5b093a1b1887255619dd9b9a08604de40b452c11922fbf3bca | True |
| 26.2 | `net/minecraft/world/phys/shapes/EntityCollisionContext.java` | 2f0a2bfd72a3d35121e3ef4ab80755f04cefaa7dd933988e2246b39ff5c39291 | True |
| 26.2 | `net/minecraft/world/phys/shapes/VoxelShape.java` | 419a46bbcc8b48e1c075127954eb243dcc60d10d4087bf256d895cf91a2ff854 | True |
| 26.2 | `net/minecraft/world/phys/shapes/Shapes.java` | 6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891 | True |
| 26.2 | `net/minecraft/world/phys/AABB.java` | 1ffdd0f6d26a4dd1e26407ea9a386eab7903e43462e60c9ae5fdc50a0468f10b | True |
| 26.2 | `net/minecraft/core/Cursor3D.java` | 9a1fdc92bb95013c7e70da467a0b5e5b1c9787daafcb0e7e3a8c950dd0e7287f | True |
| 26.2 | `net/minecraft/core/BlockPos.java` | c86391b8b18c0ba47117a197376adef8d79c8781435cfddc91f2e5806691b899 | True |
| 26.2 | `net/minecraft/core/Direction.java` | 30fb61592f1a154bb8fc239ab8df1cd2b209f63317e0c7dde4d5b9b3e260d754 | True |
| 26.2 | `net/minecraft/world/level/block/state/BlockState.java` | df5b24701ee69145a78901ee0e370e063b63b3e74a0aae32c89b2ae45bc6d205 | True |

## Comparison result

**Terminal shared-consumer verdict: no material difference found in this bounded slice.** The ordinary LocalPlayer tick reaches inherited player/living-entity movement; the client override of `LocalPlayer.move` delegates to `super.move`, whose ordinary collision path queries `level.getBlockCollisions(entity, sweptBox)`. On this non-riding Player path, `CollisionContext.of(entity)` constructs `EntityCollisionContext` (the minecart-specific context arm is unreachable). Both releases use the same query iterator and collision-shape selection/filtering behavior below.

- `CollisionGetter.getBlockCollisions(entity, box)` uses `CollisionContext.of(entity)` and constructs a lazy `BlockCollisions` iterator with `onlySuffocatingBlocks=false` and identity shape result provider. (`26.1.2`: `CollisionGetter.java:96-105`; `26.2`: `CollisionGetter.java:96-105`.)
- `BlockCollisions` expands integer scan bounds using `floor(min - 1e-7) - 1` / `floor(max + 1e-7) + 1` and walks `Cursor3D`; it caches the collision `BlockGetter` for the current packed chunk position. It excludes cursor type 3, gates type 1 through `hasLargeCollisionShape()` and type 2 through `MOVING_PISTON`, obtains the selected shape from context, then handles `Shapes.block()` with the full-cube AABB test and other shapes with translated-shape nonempty AND intersection against the query AABB. (`BlockCollisions.java:52-61,64-76,79-104` in both.) The whole file SHA-256 is identical between versions.
- `CollisionContext.of` and `EntityCollisionContext` construction, including relevant predicates and `getCollisionShape` delegation to `BlockState`, are identical. `EntityCollisionContext.java` has identical SHA-256. `BlockState.java`, `VoxelShape.java`, `Cursor3D.java`, and `Direction.java` also have identical SHA-256.
- `CollisionGetter.getBlockCollisionsFromContext` changes from `private` to `default` in 26.2, while its body is identical. `Level.java` does not declare an override in either version, so the ordinary query continues through that same default iterator factory. The added `CollisionContext.positionContext(y)` is not selected by `CollisionContext.of(entity)` on this route.
- `Shapes.java` differs only in visibility/testing annotations for `findBits`, `lcm`, and `createIndexMerger`; the `joinIsNotEmpty` and `collide` bodies are unchanged. `AABB.java` differs only in formatting of `equals` in this bounded inspection. `BlockPos.java` has unrelated additions/removals; movement-used cursor positioning and shape translation calls in `BlockCollisions` are unchanged.
- Ordinary clipping consumes the resulting shapes with `Direction.axisStepOrder(movement)`, calling `Shapes.collide(axis, boundingBox.move(resolvedMovement), shapes, axisMovement)` for each nonzero component. This preserves the ordinary axis consumption order and accumulated-position barrier; this report does not assess step-up, restitution, AutoJump, support-only, or nonplayer paths. (`Entity.java`: `26.1.2:1168-1173`; `26.2:1251-1256`.)

## Bounded terminal inventory

Compared both release sides: `LocalPlayer.tick/aiStep/move` entry and superclass movement delegation; `Player.aiStep/travel` relevant inheritance; `LivingEntity.travel` movement call; `Entity.move/collide/collideBoundingBox` ordinary query handoff; `CollisionGetter` factory; `BlockCollisions` bounds, cursor, cache, guards, shape choice and intersection; `CollisionContext` / `EntityCollisionContext`; and the `Shapes`/`AABB`/`Cursor3D`/`BlockPos`/`BlockState`/`VoxelShape` consumer dependencies listed above. No family shape tables were inspected.
