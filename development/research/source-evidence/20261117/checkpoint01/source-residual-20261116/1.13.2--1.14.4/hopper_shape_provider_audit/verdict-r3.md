# Independent source audit — Hopper provider r3

## Verdict

**ACCEPT** for the bounded Hopper collision-provider comparison and the revised cache/context dispatch account. This verdict is bound to report SHA-256 `650CEF0AA79238C29E1629CC2261EDD392031C27C05C48E9D9076CF5044079FF`. It does not establish full block-query parity, a movement difference, or pair completion.

## Source identity and hashes

- 1.13.2 Feather ready marker binds source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; the on-disk manifest hash matches.
- 1.14.4 Feather ready marker binds source manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`; the on-disk manifest hash matches.
- Independently recomputed all 22 Java-file hashes in the r3 report; each matches both the report and its source sidecar.

## Claim checks

- Both registries associate `hopper` with `HopperBlock`. The shape-driving property schema and default are the same; `HOPPER_FACING` excludes `UP` at both endpoints. The getter reads only `FACING` and selects the same `DOWN`, four horizontal, or base shape for each value.
- The shape constants and unions are identical. Both `Hopper.java` files have the same digest, confirming the shared `INSIDE` bounds. `Block.block` applies the same sixteenth scaling. `ENABLED`, world, position, block entity and context do not alter the Hopper getter's selected collision shape.
- The report accurately distinguishes the 1.14.4 cache path from movement-time dispatch. `Blocks` iterates all registered states and calls `cacheShape`; `BlockState.cacheShape` constructs a cache for blocks without dynamic shapes. The Hopper registration does not set dynamic shape, whose default is false. The cache constructor computes a collision shape with `CollisionContext.empty()`, and the no-context `BlockState.getCollisionShape(world,pos)` overload returns this cache when present.
- Normal movement's 1.14.4 `WorldView.getBlockCollisions(entity,box)` is the context-taking path: it constructs `CollisionContext.of(entity)` and dispatches states using `BlockState.getCollisionShape(world,pos,context)`. That overload calls the block's context-taking `getCollisionShape`. The inherited `Block.getCollisionShape` calls `state.getSHape(world,pos)` without forwarding context; `BlockState.getSHape` calls `getShape(...,CollisionContext.empty())`. `HopperBlock` does not override `getCollisionShape`, and its context-taking `getShape` does not read context. This is distinct from the no-context overload's cached return, as r3 now states.
- On 1.13.2 the entity collision query reaches the no-context state collision getter, which delegates through the inherited base collision getter to the same facing-based Hopper geometry. The cited `Entity.move`/travel ancestry supports local-player reachability on both sides.

Therefore, for matching Hopper `FACING` state, both endpoints supply the same movement collision shape. The previous r1/r2 context/cache-route issues are resolved by r3's distinct overload tracing. No Hopper collision-provider movement delta is supported in this slice.

## Boundary

This audit does not validate other block providers, the whole world-query inventory, collision resolver math, tick/external/resource dependencies, intermediate pair cutoffs, or full-pair acceptance/runtime parity.
