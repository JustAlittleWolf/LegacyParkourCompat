# 1.13 entity-collision collector correction

Bound immutable source note: `evidence.md`, SHA-256 `F20BD238ABA21C93AD664C094D0752F9284AA3CF4398BF68099B9402E155BCDC`. This is a focused correction after independent audit `axis-alias-audit/audit.md`, SHA-256 `0692F9834AFDFACB3B7B3AFB1E0E9D91F8FFF3594E8EEE641DD9D87161CF7470`. The original note is preserved.

**Verdict: ACCEPT for this correction.** The claim that 1.13's `WorldView.getCollisions` returns only the block collector was false. The collector integration differs across the exact 1.13 releases below. Relevant files are in `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<release>\ornithe-feather\`; all are `ornithe-feather` namespace sources and the class digests below match each listed `*.sources.sha256` manifest.

| Release | Mapping/source identity evidence | Source SHA-256 values |
|---|---|---|
| 1.13 | `ornithe-feather`; ready SHA-256 `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99`; source manifest SHA-256 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`. | `world/WorldView.java` `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543`; `entity/Entity.java` `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642`. |
| 1.13.1 | `ornithe-feather`; ready SHA-256 `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de`; source manifest SHA-256 `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba`. | `world/WorldView.java` `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`; `world/World.java` `119ac6f61b93cf352b2026934e9f95877f55dc2f582b45d75c2bdae3652532b6`; `world/EntityView.java` `2b5b59eef6efa93bb51c1d838c7a1cf64725be96c55c47f76894fde11e13d6de`; `entity/Entity.java` `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`. |
| 1.13.2 | `ornithe-feather`; ready SHA-256 `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`; source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. | `world/WorldView.java` `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`; `world/World.java` `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a`; `world/EntityView.java` `2b5b59eef6efa93bb51c1d838c7a1cf64725be96c55c47f76894fde11e13d6de`; `entity/Entity.java` `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`. |

## Exact collector and caller behavior

### 1.13

`WorldView.getCollisions(Entity,VoxelShape,VoxelShape,Set<Entity>,boolean)` is at `world/WorldView.java:203-218`. It first calls `getBlockCollisions` (`:209`). It joins `m_19803363(entity, voxelShape, bl, set)` with `BooleanOp.OR` when `entity != null` and either `bl` (early existence mode) is false or the block result is empty (`:213-218`). Otherwise it returns the block collector result. `m_19803363` (`:220-247`) gets entities from the query bounds expanded by `0.25`, skips excluded entities and entities sharing the caller's vehicle, and checks both `other.getCollisionShape()` and the caller's `getCollisionAgainstShape(other)`. Each non-null candidate is joined only after strict `Box.intersects(queryBounds)`. When early-existence mode is true, it stops after the first accepted candidate shape.

`hasNoCollisions(Entity,Box,Set)` and its empty-set overload (`WorldView.java:270-275`) call the same collector with `bl=true`, then test the returned shape's `isEmpty()`. Therefore a non-null entity's collision shapes are considered even in this early-existence query when the block collector returns empty. If a block shape exists, the early path returns it without needing entity enumeration.

The ordinary movement caller in `Entity.move` calls `world.getCollisions(this, currentShape, x, y, z)` when any requested delta is nonzero (`entity/Entity.java:578-580`). Its alternate step-height candidates call `hasNoCollisions(this, candidateShape)` under their nonzero-axis gates (`:536`, `:548`, `:558`). These pass a non-null entity, so the 1.13 default collector's entity path is active.

### 1.13.1 and 1.13.2

`WorldView.getCollisions(Entity,VoxelShape,VoxelShape,Set<Entity>)` (`world/WorldView.java:170-179`) is the block stream path; its `hasNoCollisions` overloads (`:201-206`) call it and use `allMatch(VoxelShape::isEmpty)`. On the actual `World`, dynamic dispatch reaches `World.getCollisions` (`world/World.java:1755-1758`), which concatenates the `WorldAccess`/`WorldView` block stream with `getEntityCollisions` only when `entity != null`. A non-World implementation that inherits only the `WorldView` default has the block stream only; the concrete release `World` includes entities.

`EntityView.getEntityCollisions` (`world/EntityView.java:21-38`) returns empty for an empty query shape; otherwise it gets nearby entities within `shape.bounds().expand(0.25)`, excludes `excludeEntities` and shared-vehicle entities, then streams each other collision shape and reciprocal response shape. Null shapes are filtered and a shape is emitted only when `s.intersects(queryBounds)` succeeds; that is the strict `Box.intersects` predicate. The `World` override appends this stream for non-null entity callers, including `hasNoCollisions` calls through interface dispatch. The ordinary and step-height `Entity.move` caller gates are the same lines as 1.13 (`entity/Entity.java:536,548,558,578-580,611-612`).

## Effect on the bounded primitive conclusions

This correction changes the documented **collector contents and caller-visible `hasNoCollisions` result**: entity collision boxes and reciprocal response boxes can be included for non-null entities. It does not change `Box.intersectX/Y/Z`, `Box.intersects`, `VoxelShapes.m_84966497` / `m_14725303`, `VoxelShape.calculateMaxDistance`, their epsilon/order/boundary comparisons, or the conclusion that exact face contact is rejected by strict entity-box intersection. No trajectory or pair-coverage conclusion is made.
