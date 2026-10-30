# 1.16.5 → 1.17.1 collision, shapes, support, and player callbacks

Status: bounded independent endpoint source review. Source roots were the assigned read-only Mojmap trees only. Exact refs below use paths relative to each version root. No implementation/runtime/test work was performed.

## Endpoint identity

Recomputed hashes for every source-manifest Java entry: 1.16.5 had 3,523 entries (0 missing, 0 mismatch); 1.17.1 had 4,142 entries (0 missing, 0 mismatch). Ready/source-manifest/provenance/artifact-manifest hashes recorded during the review:

- 1.16.5 ready `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee`; source manifest `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`; provenance `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d`; artifact manifest `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c`.
- 1.17.1 ready `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b`; source manifest `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`; provenance `de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333`; artifact manifest `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa`.

These checks establish consistency with the local manifests, not independent identity to raw JAR bytes (raw JAR payload was not opened).

## Candidate: Shapes.create/findBits discretization threshold

**1.16.5** `net/minecraft/world/phys/shapes/Shapes.java:55-86,89-104`: `create(AABB)` invokes `findBits` on all axes. For a coordinate accepted by `findBits`, it creates a discrete voxel shape with rounded endpoints (`:64-83`); if any axis is rejected it builds `ArrayVoxelShape` from the exact AABB endpoints (`:84-86`). `findBits` rejects coordinates outside `[-1e-7, 1.0000001]`, tries scales `1,2,4,8`, and accepts when `abs(coord*scale - floor(coord*scale)) < 1e-7` for both endpoints (`:89-104`). Effective tolerance in coordinate units therefore shrinks as `1e-7/scale`, and the test is one-sided relative to `floor`.

**1.17.1** `net/minecraft/world/phys/shapes/Shapes.java:61-94,96-113`: six-coordinate `create` first returns empty if any extent is `<1e-7` (`:62`), then uses `findBits`; on a fit, it rounds endpoint coordinates into discrete voxel bounds (`:76-90`), otherwise preserves endpoints in `ArrayVoxelShape` (`:66-70`). `findBits` uses `abs(coord*scale - Math.round(coord*scale)) < 1e-7*scale` (`:101-112`), i.e. a nearly constant `1e-7` tolerance in coordinate units at each scale, around the nearest grid coordinate. This can classify AABBs as grid-aligned where 1.16.5 retained exact array coordinates, and can snap the resulting voxel bounds by up to the new tolerance. Separately, the new `<1e-7` extent-to-empty check has no counterpart in the old `create(AABB)` body.

A concrete method-input witness is endpoints `0.12500005` and `0.62500005` on an axis: at scale 8 the offsets from the 1/8 grid are `5e-8`, exceeding the old `1.25e-8` tolerance but within the new `8e-7`; old creates an exact-coordinate array axis, new selects grid coordinates 1 and 5 (0.125 and 0.625). This demonstrates the method delta, not that a particular ordinary player tick emits those exact endpoints. Position/AABB values arise downstream from player movement; a naturally occurring minimum witness and resulting movement threshold crossing remain unproven. Likely effect is limited to near-contact overlap/displacement decisions, but that is an inference pending a concrete ordinary-input witness.

### Player reachability and consequence path

- `net/minecraft/world/entity/Entity.java:485-535` (1.16.5) / `:534-586` (1.17.1): `Entity.move` calls `collide(vec3)` (`:505` / `:554`), applies resolved displacement, and derives horizontal/vertical collision and `onGround` from requested vs resolved motion (`:513-518` / `:561-566`). The resolved axes then gate velocity zeroing, `updateEntityAfterFallOn`, and `stepOn` (`:520-535` / `:570-586`). Local players inherit this entity movement route; this does not imply server-side historical movement.
- `net/minecraft/world/entity/Entity.java:668-707,713+` (1.16.5) / `:750-789,791-800` (1.17.1): `collide` expands the entity AABB in the requested direction and invokes the bounding-box heuristic; the helper combines the entity/world-border stream with `level.getBlockCollisions(entity, expandedAABB)` before axis clipping. `CollisionGetter.getBlockCollisions` constructs the `CollisionSpliterator`, whose `Shapes.create(AABB)` path is shown above.
- `net/minecraft/world/level/CollisionSpliterator.java:38-55,61-88` (1.17.1; corresponding 1.16.5 constructor/query is `:37-54,60-87`): constructor stores `entityShape = Shapes.create(aABB)` (`:42` / `:41`); per block it gets the contextual collision shape and, except full-block fast path, uses `Shapes.joinIsNotEmpty(voxelShape2, entityShape, BooleanOp.AND)` as the overlap gate (`:75-85` / `:74-84`). Thus the changed encoding can be reached on ordinary entity collision queries and feed the move result. It is not established that this candidate changes a particular displacement or grounded result for a reproduced player input.
- Block providers route through `BlockBehaviour.BlockStateBase.getCollisionShape` (`net/minecraft/world/level/block/state/BlockBehaviour.java:524-529` old; `:540-545` new), forwarding the state, getter, position, and entity collision context to the block provider. The fallback/default and scanned existing block collision-shape bodies were unchanged in this review, apart from class moves/renames below.

## Shape/support providers and registration scan

Method-body scan across the shared top-level `net/minecraft/world/level/block/*.java` source universe covered `getShape`, `getCollisionShape`, `getBlockSupportShape`, `getOcclusionShape`, `getVisualShape`, `getInteractionShape`, `isSuffocating`, `isViewBlocking`, `isCollisionShapeFullBlock`, `fallOn`, `stepOn`, `updateEntityAfterFallOn`, `entityInside`, `getBlockSpeedFactor`, and `getBlockJumpFactor`. It found no changed collision/support result for the scanned existing providers, with these same-result moves/renames:

- Cauldron `getShape`/interaction shape move to `AbstractCauldronBlock`; returns remain the same shape constants.
- EndRod `getShape` moves to `RodBlock`, retaining the same axis-specific 6/16-thick boxes.
- WoolCarpet `getShape` moves to `CarpetBlock`, retaining 0..1/16 height.
- GrassPath becomes DirtPath; Lantern, Seagrass/TallSeagrass are renamed/refactored; corresponding inspected `getShape` bodies still return the same `SHAPE`.
- `RedStoneWireBlock.getShape` is semantically the same cache lookup with equivalent state expression.
- `BlockBehaviour.BlockStateBase.getCollisionShape` and `getBlockSupportShape` still delegate to block behavior. `SupportType.java` is byte-identical; Leaves, SnowLayer, and SoulSand support overrides were identical. Snow support remains layer-indexed; Leaves remains empty; SoulSand remains full block.
- `BlockBehaviour.BlockStateBase.isCollisionShapeFullBlock` changes fallback dispatch: old calls `Block.isShapeFullBlock(getCollisionShape(...))`; new delegates to block `isCollisionShapeFullBlock`, whose default performs that same computation. New Bamboo override returns false but no player movement consumer was found; call use is through `Entity.moveTowardsClosestSpace`, reached from ItemEntity/ExperienceOrb, not Player.
- New block types (including 1.17-only types) are not historical era-existing providers and were excluded from candidate deltas.

The scan is source-name/body based; it is not a resource/tag or block-registration audit. Registry data and all newly added block behavior remain unexamined by design.

## Player-related callbacks / other bounded routes

- `Block.stepOn` gained a `BlockState` argument; `Block.fallOn` gained state and uses `DamageSource.FALL`. `Block.updateEntityAfterFallOn` remained the same direct horizontal-preserving, vertical-zeroing setter. `Entity.move` callback ordering is preserved in relevant operations, with state passed to `stepOn` in 1.17.1. The inspected Slime horizontal multiplier/gates are the same; Slime/Bed bounce setter math is unchanged; Honey slide, BubbleColumn `onAboveBubbleCol`, Web stuck multiplier, and `Entity.makeStuckInBlock` bodies are unchanged. Remaining callback differences observed are state/damage, server/world effects, or modern additions, not identified player-position/velocity/on-ground deltas.
- Piston-moving-block entity code was refactored to static helpers, but movement progress, axis amount, collided/stuck entity handling, and `Entity.move(MoverType.PISTON, ...)` operations inspected retain the same math/order. Piston path remains dependent on the common Entity collision route above.
- `Block.pushEntitiesUp` gains an empty-shape early return in 1.17.1. Its consumer call sites are farmland/path-to-dirt state changes. It calls `Entity.teleportTo`, which only applies positional mutation on a `ServerLevel`; not a local player movement callback delta.
- Existing offset providers fit the old default horizontal/vertical ranges. The new configured-offset max/clamp defaults therefore do not change inspected era-existing block offset outputs. New max overrides occur on 1.17-only blocks.

## Coverage limits / next action

Examined: endpoint source identity manifests; paired shape create/findBits bodies; player Entity.move and collision stream route; CollisionSpliterator and block-state collision/support dispatch; a bounded paired block-provider/callback method scan; identified piston and block push-up routes.

Partial: no generated dataset of all changed methods in the shape engine; no closed mathematical proof for all `DiscreteVoxelShape`/`BitSetDiscreteVoxelShape`/array-grid packing, boolean joins, voxel mergers, and iteration refactors. `VoxelShape.findIndex` was rewritten and shape box enumeration was refactored; these need focused proof if complete engine equivalence is required. No naturally reached player-AABB witness or threshold-crossing trace has been produced for the `findBits` candidate. Direct support-query consumer audit outside scanned block/entity paths is incomplete. Exact resources, tags, registration tables, and raw JAR bytes were not audited. Existing block geometry scan did not cover every nested/non-top-level class or every vanilla API that can construct custom shapes.

Next action: close the `findBits` candidate with an exact reachable AABB witness or classify it as a narrow source-level shape-engine candidate pending another lane’s tick/path analysis; then independently prove/refute the remaining changed shape-engine methods before declaring shape-engine coverage complete. No source-level callback/provider candidate beyond `Shapes.create/findBits` is currently established.
