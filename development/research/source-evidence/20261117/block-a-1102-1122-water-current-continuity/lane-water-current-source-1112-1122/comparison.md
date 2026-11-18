# Comparison: ordinary local-player water current (1.11.2 → 1.12.2)

## Verdict

No difference was found in the assigned ordinary, nonfalling local-player water-current operation. The exact paired `World.applyLiquidDrag`, ordinary `Entity.checkWaterCollisions`, caller box transforms, and liquid-flow aggregation/application methods have identical bodies. No finding is warranted for this slice. This is a bounded result, not full-pair coverage or a statement about other releases.

## Source evidence and operation

In both versions, `Entity.baseTick()` reaches `checkWaterCollisions()`. Local-player `tick()` calls its superclass; the inheritance chain reaches `Entity`. `checkWaterCollisions()` keeps the boat exclusion and calls `world.applyLiquidDrag(getShape().grown(0.0, -0.4F, 0.0).contract(0.001), Material.WATER, this)`. On a true result it conditionally emits splash effects, clears fall distance, sets `inWater`, and extinguishes; otherwise it clears `inWater`. The same method is reached by `LivingEntity.checkFallDamage()` when `!isInWater()`. Local-player tick enters `PlayerEntity.tick()`, then `LivingEntity.tick()`, then `Entity.tick()`/`baseTick()`; each superclass call precedes the subclass post-tick work, and this dispatch order is the same in both versions. `Entity.tick()` calls `baseTick()` in both.

`World.applyLiquidDrag` is text-identical at 1.11.2 `World.java:1571` and 1.12.2 `World.java:1579`. It floors each AABB minimum and ceils each maximum, uses half-open loops (`< max`) on X/Y/Z, then rejects the whole operation when `isAreaLoaded(i,k,m,j,l,n,true)` is false. It visits matching material cells only; the top threshold is `p + 1 - LiquidBlock.getHeightLoss((Integer) state.get(LEVEL))`, accepted when `ceil(bounds.maxY) >= threshold`. Each accepted cell sets the returned boolean and adds that block's `applyMaterialDrag` vector in traversal order. After releasing the pooled position, it applies current only when accumulated vector length is positive and `entity.hasLiquidCollision()`: normalize once and add `0.014 * component` to each current velocity component. A zero vector can still leave the returned water-contact boolean true.

The normal player caller's box transform is text-identical. `Box.grown` and `Box.contract` are also identical across the pair. The `isAreaLoaded` coordinate/chunk/vertical bounds helper, `LiquidBlock.getHeightLoss`, `MathHelper.floor(double)` and `ceil(double)` are identical. Default `Block.applyMaterialDrag` returns the incoming vector. `LiquidBlock.applyMaterialDrag` adds `getFlow` for that cell. `getFlow`, liquid-depth helpers, and `Vec3d.add`, `length`, and `normalize` are text-identical. `Entity.hasLiquidCollision()` returns true in both versions.

## Reachability and exclusions

The ordinary current operation runs for the local player through base tick, independently of whether callers later inspect the stored `isInWater()` flag. That stored flag is set by the box collision query; it is distinct from a query at the eye, a liquid depth/submersion threshold, or a swim predicate. None of those alternatives replaces the body-box query in this call path.

1.12.2 adds `Entity.checkWaterState()` at `Entity.java:944`, querying `getShape().grown(0.0, -20.0, 0.0).contract(0.001)`. The examined call site is `FlyingWaterAvoidingWanderAroundGoal`, a mob AI goal; it is not called by `LocalClientPlayerEntity` and is outside the assigned operation. Its large downward probe must not be treated as the local player's ordinary current box.

A separate conditional helper change was observed in `LiquidBlock.isFaceSolid`: 1.11.2 uses the previous liquid/block face-solid path, while 1.12.2 asks the neighboring state for `FaceShape.SOLID` with attachment/stair exclusions. `getFlow` consults this only for `LEVEL >= 8`, the falling-flow branch that injects downward current. That branch is explicitly outside this nonfalling slice, so it is not promoted to a local-player nonfalling finding here.

## Compared members

| Member | Result |
|---|---|
| `World.applyLiquidDrag(Box, Material, Entity)` | Identical |
| `World.isAreaLoaded(int, int, int, int, int, int, boolean)` | Identical |
| `Entity.checkWaterCollisions()` / `isInWater()` / `baseTick()` | Identical |
| `LivingEntity.checkFallDamage(...)` / `baseTick()` | Identical |
| `LocalClientPlayerEntity.tick()` and inheritance path | Identical for relevant tick dispatch |
| `Box.grown(...)` / `contract(...)`; `MathHelper.floor(double)` / `ceil(double)` | Identical |
| `Block.applyMaterialDrag(...)`; `LiquidBlock.getFlow(...)` / `applyMaterialDrag(...)` | Identical |
| `LiquidBlock.getLiquidState(...)` / `getLiquidDepth(...)`; `Vec3d.add(...)` / `length()` / `normalize()` | Identical |
| `Entity.hasLiquidCollision()` | Identical |
| 1.12.2 `Entity.checkWaterState()` | Added; out-of-scope mob-goal deep probe |
| `LiquidBlock.isFaceSolid(...)` | Changed; consulted by excluded `LEVEL >= 8` falling-flow branch |