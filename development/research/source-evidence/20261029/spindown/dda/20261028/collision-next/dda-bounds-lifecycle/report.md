# 1.8.9 movement collision bounds: Brewing Stand and End Portal Frame

## Bounded conclusion

In 1.8.9, movement collision collection writes the legacy `Block` bounds on the registered Brewing Stand or End Portal Frame block instance. These coordinates are mutable instance fields, shared by calls using that same block object across positions; they are not static class fields or one common provider-wide bounds slot. Each provider's `setShape`/`resetShape` calls target `this`.

Consequently, these movement calls can change and leave bounds on their own block objects, but cannot mutate, restore, or otherwise change the cached bounds on the separate Anvil or Chest objects. The named `Blocks` references resolve the four block types by distinct registry keys (`brewing_stand`, `end_portal_frame`, `anvil`, `chest`). The old client camera pick occurs before `World.tickEntities` in the same `Minecraft.tick`; movement collision calls made during an entity tick therefore occur after that tick's pick.

## Old provider lifecycle

`Block` initializes each new block object's bounds to the unit cube in its constructor (`Block.java:198-207`). Its six coordinates are non-static fields (`Block.java:103-108`). The non-static final `setShape` assigns all six on `this` (`Block.java:292-299`); base `addCollisions` calls `this.getCollisionShape` (`Block.java:339-344`), and the default getter constructs its box from `this.minX` through `this.maxZ` (`Block.java:346-350`). Thus these providers temporarily alter the same per-object values that the base collision getter reads.

- **Brewing Stand** (`BrewingStandBlock.java:33-36,64-74`): Its constructor does not replace the base unit cube. Each `addCollisions` call sets the central post bounds to X/Z `7/16..9/16`, Y `0..14/16`, calls base `addCollisions` once, calls `resetShape`, then calls base `addCollisions` a second time. `resetShape` sets the full-footprint, 1/8-high bounds (X/Z `0..1`, Y `0..1/8`). There is no branch around either reset or the second base call. The method returns with that 1/8-high shape stored on the Brewing Stand object. It does not restore the unit cube or snapshot/restore the object's previous values.
- **End Portal Frame** (`EndPortalFrameBlock.java:23-25,34-48`): Its constructor also inherits the unit-cube initialization. Each `addCollisions` call sets full-footprint bounds to Y `0..13/16`, then calls base `addCollisions`. It checks `world.getBlockState(pos).get(EYE)`; if true, it sets a second central column at X/Z `5/16..11/16`, Y `13/16..1`, and calls base `addCollisions` again. It unconditionally calls `resetShape`, which sets the full-footprint `0..13/16` frame bounds. It returns with those frame bounds stored, whether or not the eye branch ran. This is a fixed reset value, not restoration of the constructor cube or prior values.

The registrations in `Blocks.java:63,140,143,168,271,348,351,376` bind Chest, Brewing Stand, End Portal Frame, and Anvil to their respective registry keys. Combined with the instance fields and `this` writes above, neither provider's reset has a route to Anvil's or Chest's bounds. The block objects' own bounds do persist across positions and calls of that block type; they are not per-position or per-call temporaries.

## Tick and movement-call order

In 1.8.9 `Minecraft.tick`, `gameRenderer.pick(1.0F)` is called at line 1314; that picker dispatches to the camera entity's `Entity.rayTrace` at `GameRenderer.java:318-325`. The same client tick reaches `world.tickEntities()` later at `Minecraft.java:1629-1636`. `World.tickEntities` invokes entity `tick()` methods in its entity loops (`World.java:1159-1168,1330-1346`). A movement collision request from `Entity.move` reaches `World.getCollisions` (`Entity.java:439`); the world iterates block cells and dispatches `blockState2.getBlock().addCollisions(...)` (`World.java:891-922`). Therefore, collision-provider writes reached during movement inside those entity ticks are ordered after the old camera pick in that client tick.

This establishes same-tick order only. The persisted Brewing Stand and End Portal Frame bounds belong to their own block objects; this report does not characterize later camera queries or other non-movement callers. The accepted `camera-tick-raytrace` artifact is cited only as the already-accepted camera-caller identity; its cache-write proof and general ray-provider contract are not repeated here.

## 26.2 comparison

The corresponding 26.2 Brewing Stand and End Portal Frame providers use shape values rather than this old `addCollisions`/`resetShape` mutable-bound lifecycle: Brewing Stand has a `static final VoxelShape SHAPE` and returns it from `getShape` (`BrewingStandBlock.java:38,61-62`); End Portal Frame has static empty/full shapes and selects between them from the current state's eye property (`EndPortalFrameBlock.java:30-31,50-51`). `BlockBehaviour.getCollisionShape` delegates to `state.getShape` when collision is enabled (`BlockBehaviour.java:327-328`). These exact counterparts contain no old `addCollisions` mutation/reset sequence. The 26.2 client also calls `pick(1.0F)` before `level.tickEntities()` (`Minecraft.java:1787,1804-1810`), but that does not create the old cross-instance mutable-bound coupling.

## Evidence limits

This is a source-only lifecycle closure for these two 1.8.9 block providers and their relevant 26.2 counterparts. It does not prove which block states a particular camera ray or movement query visits, cover other mutable-bound writers, establish intermediate-version behavior, assess a product implementation, or establish runtime movement parity. No product source/history, backlog, worker drafts, build, tests, game launch, or source generation were used.

## Exact source identities

- 1.8.9: canonical `ornithe-feather` source export; ready metadata identifies version 1.8.9 and Feather Gen2 build.2 merged-v2.
- 26.2: canonical `unobfuscated` source export; ready metadata identifies version 26.2 and unobfuscated mapping.
- Accepted bounded inputs: `camera-tick-raytrace`, `full-ray-provider-inventory.md`, and `dda-default-provider-review/source-audit.md`. Their checked SHA-256 values are recorded in `hash-manifest.sha256`.
