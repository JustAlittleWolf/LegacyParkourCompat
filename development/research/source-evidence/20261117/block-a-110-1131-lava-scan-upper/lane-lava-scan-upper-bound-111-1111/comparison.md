# 1.11 → 1.11.1: lava presence scan and travel branch

## Finding

No source change was found in these assigned operations. The direct lava presence query, its exact scan volume, its voxel-boundary behavior, and the player travel branch consuming the result match between 1.11 and 1.11.1. This is negative coverage for this pair, not a claim about intermediate releases or adjacent lava operations.

## Query shape and scan bounds

`Entity.isInLava()` passes `this.getShape().grown(-0.1F, -0.4F, -0.1F)` and `Material.LAVA` to `World.containsMaterial`. This expression is identical in both releases. In `Box.grown`, each minimum is computed as `min - delta`, each maximum as `max + delta`; the negative float constants are widened to the double parameters. Thus the query narrows the entity box by 0.1 on X/Z and 0.4 on Y.

`World.containsMaterial` is also identical. It assigns `i=floor(minX)`, `j=ceil(maxX)`, `k=floor(minY)`, `l=ceil(maxY)`, `m=floor(minZ)`, `n=ceil(maxZ)`, then visits `o=i..<j`, `p=k..<l`, `q=m..<n` in X/Y/Z nested order. The Y upper endpoint is exactly `ceil(bounds.maxY)` with a strict `p < l` loop; there is no epsilon, cast, decrement, area-loaded test, or chunk-loaded test. At an exact integral upper bound N, voxel row Y=N is excluded. If the max lies just above N, ceil is N+1 and row N is included. Block material equality (`== material`) is a presence test; the first match releases the pooled mutable position and returns true, otherwise the mutable is released once after the loops and false is returned. The method reads block state directly through `getBlockState`; it does not call `isAreaLoaded` or `isChunkLoaded`. The same `World.getBlockState(BlockPos)` implementation first returns air only when `isOutsideWorldHeight(pos)` is true; otherwise it calls `getChunk(pos)` and then `worldChunk.getBlockState(pos)`. There is no explicit loaded-chunk guard in this query path.

## Local-player consumer/order and gates

`PlayerEntity.mobTick()` calls `super.mobTick()`. The inherited `LivingEntity.mobTick()` reaches travel after multiplying sideways and forward inputs by `0.98F`, rotating input by `0.9F`, and invoking `flyingTick()`, then calls `moveRelative(sidewaysSpeed, forwardSpeed)`. This order is the same in both releases.

The player override first snapshots position. If abilities flight is enabled and the player is not riding, it snapshots Y velocity and `speedInAir`, sets air speed to ability fly speed (doubled while sprinting), invokes `super.moveRelative`, restores Y velocity as saved value times `0.6`, restores air speed, clears fall distance and clears flag 7. Otherwise it directly invokes `super.moveRelative`; both paths then update non-riding movement stats. This wrapper is unchanged.

In inherited `LivingEntity.moveRelative`, the first gate is `isLocallyControlled() || isLogicalSideForUpdatingMovement()`. The local client player overrides `isLocallyControlled()` to true and `isLocal()` to true, so it enters this method. The next conditional tests water first (`!isInWater() || player && abilities.flying`); only if that condition passes does it test lava (`!isInLava() || player && abilities.flying`). Thus the lava predicate is reached after water is absent or when player flight bypasses water. Lava physics is selected only when lava is present and the player-flight exception is false. Its unchanged order is: snapshot Y; `updateVelocity(sideways, forwards, 0.02F)`; `move(SELF, velocityX, velocityY, velocityZ)`; halve X/Y/Z velocity; subtract `0.02` from Y unless no-gravity; if horizontally colliding and `canMove(velocityX, velocityY + 0.6F - this.y + savedY, velocityZ)`, set Y velocity to `0.3F`.

The query and lava branch have no loaded-area or chunk gate. The adjacent ordinary land branch does contain a distinct post-move gravity gate: `!world.isClient || (world.isChunkLoaded(pos) && world.getChunk(pos).isLoaded())`, followed by no-gravity handling or unloaded-client fallback velocity. That gate is not wrapped around the lava query or lava branch. This placement is unchanged.

## Compared source paths and checksums

All six exact files per release listed in `roster.md` match their source-manifest SHA-256 entries. Relevant source SHA-256 values:

| Source | 1.11 | 1.11.1 |
|---|---|---|
| `Entity.java` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` |
| `World.java` | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` |
| `LivingEntity.java` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` |
| `PlayerEntity.java` | `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` | `8b37fd286b2dac1b343bd708b80284b43bb31c61bafc63bdcf6620ba3a59065c` |
| `LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |
| `Box.java` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |

## Source references

- 1.11: `decompiled_minecraft/1.11/ornithe-feather/net/minecraft/entity/Entity.java`, `World.java`, `LivingEntity.java`, `PlayerEntity.java`, `client/entity/living/player/LocalClientPlayerEntity.java`, `util/math/Box.java`.
- 1.11.1: same paths under `decompiled_minecraft/1.11.1/ornithe-feather/`.