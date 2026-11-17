# GrassPath direct collision and shape provider: bounded source result

## Scope and immutable inputs

This is only the GrassPathBlock direct collision/shape-provider, registration/state-construction, and ordinary client player collision-reachability slice for 1.13.2 to 1.14.4. It does not close the release pair or claim full player-tick coverage.

- 1.13.2 Feather source manifest SHA-256: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` (`decompiled_minecraft/1.13.2/ornithe-feather.sources.sha256`).
- 1.14.4 Feather source manifest SHA-256: `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc` (`decompiled_minecraft/1.14.4/ornithe-feather.sources.sha256`).
- Exact-version publication/readiness and verification records identify those source-manifest digests. The canonical source trees were read only.

## Defensible registration join and state construction

The join is the `minecraft:grass_path` registry key, not the mapped class name alone. In 1.13.2, `net/minecraft/block/Block.java` constructs a `GrassPathBlock` with `Material.DIRT`, strength `0.65F`, and grass sounds, then registers that instance under `grass_path` (around lines 1599–1600). `Blocks.GRASS_PATH` resolves the registered entry (Blocks.java around line 1098). In 1.14.4, `net/minecraft/block/Blocks.java` directly declares `GRASS_PATH = register("grass_path", new GrassPathBlock(...))` (around lines 1428–1429), with the same material, strength, and sounds. The class and key therefore have a defensible same-block join. The class has no GrassPath-specific state property declaration in either version; the compared construction supplies the same properties.

## Provider comparison

Both `GrassPathBlock.java` versions set `SHAPE = FarmlandBlock.SHAPE`. Both corresponding `FarmlandBlock.java` sources define that shape as `Block.block(0.0, 0.0, 0.0, 16.0, 15.0, 16.0)`, so the provider geometry is the same 1-block-wide, 15/16-block-high box. GrassPath overrides the shape method to return this constant on both sides. The 1.14.4 override accepts `CollisionContext`; the 1.13.2 method has no context parameter. The implementation ignores world, position, and (in 1.14.4) context, returning the same constant.

For ordinary collision, 1.13.2 `Block.getCollisionShape` returns `state.getShape(world,pos)` when the block has collision; its `BlockState` forwards to the block. In 1.14.4 the analogous method returns the state's shape with `CollisionContext.empty()` for cached collision data, and the contextual overload forwards the supplied context. GrassPath’s provider returns the same constant for either path, so neither the signature change nor context changes this block's collision geometry.

## Ordinary client player reachability

`Entity.move` in 1.13.2 asks `world.getCollisions(...)` while resolving axis movement (`Entity.java` around lines 473 and 580 onward). `WorldView.getBlockCollisions` obtains each block state's collision shape. In 1.14.4, `Entity.move(MoverType, Vec3d)` (Entity.java around line 444) resolves movement through world collision queries; `WorldView.getBlockCollisions` (around lines 129 onward) builds a context from the entity and calls `blockState.getCollisionShape(..., collisionContext)`. The player is an Entity on this ordinary client movement path; no GrassPath-only reachability gate was found in the compared provider/collision dispatch. Thus the same GrassPath collision box is reachable by ordinary player movement on both sides.

## Bounded verdict and limits

**No difference established for direct GrassPath collision shape or its ordinary player collision reachability in this pair.** No independent movement finding is warranted for this slice.

The GrassPath subclass does change other direct methods: 1.13.2 overrides `getOpacity` to return `world.getMaxLight()` and `isCube` to return false; 1.14.4 drops `getOpacity` and overrides `isCube` to return true. These are observable source differences, but this assignment is bounded to collision/shape provision and construction; their broader predicates/render/light effects are not adjudicated here and do not alter the explicit shape-return bodies. They require a separate operation-level investigation if covered by another lane.

Precise dependencies examined: same registry key and constructor properties; `GrassPathBlock` provider; `FarmlandBlock.SHAPE`; `Block` and `BlockState` shape/collision forwarding and 1.14 cache/context path; `WorldView` block-collision enumeration; `Entity.move` entry into collision resolution. This does not audit block-state serialization, neighboring-block updates, surface/support predicates, all tick callers, or the rest of the player movement graph. No intermediate patch boundary is established.
