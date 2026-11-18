# Local sprint-input chunk-gate boundary — bounded comparison

## Assignment and result

This note closes the known 1.14.3→1.14.4 local sprint-input chunk-availability dependency left outside the accepted contact-producer and eye-submersion comparisons. It does not revise or inspect the A-owned environmental writers, and it does not establish general movement parity.

**Result:** No difference was found at these gates for a partially loaded local client. Both area-gate defaults use the same vertical bounds, `>> 4` chunk conversion, and inclusive terminal chunk loops. More importantly, each local client world overrides its chunk predicate to return `true` unconditionally, so these AABB and eye-point guards do not reject unloaded client chunks in either version. The base `ChunkManager`/`ChunkSource` implementations also match (`getChunk(..., FULL, false) != null`), but local-client dispatch bypasses them. No artificial divergent witness is supported by these methods.

This result is deliberately limited to gate behavior. After the guards pass, the fluid scans still read world fluid state; this comparison does not establish equivalence of how every missing client chunk supplies that state.

## Exact sources and hashes

The bounded source identities are Minecraft 1.14.3 Yarn `1.14.3+build.13` and Minecraft 1.14.4 Mojmap, as recorded in the accepted input producer and eye-submersion comparison. The 1.14.3 provenance hash recorded there is `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`; the prior 1.14.4 record identifies the Mojmap 1.14.4 source manifest hash `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`. The 1.14.4 sidecar is not present in the current canonical directory view, so that prior publication identity is carried forward rather than reverified here. All Java source hashes below were freshly computed from the current canonical files.

| Release / mapping | Relative source file | SHA-256 |
|---|---|---|
| 1.14.3 Yarn | `net/minecraft/world/ViewableWorld.java` | `3e6f183c66c98206cef362551c53dbf8ea7ac11e44123060c640da09c944d29d` |
| 1.14.3 Yarn | `net/minecraft/world/chunk/ChunkManager.java` | `06bebe0a01ffd7a80c5508eb2fcd850543e1ce71b34ea33d58fcf7b56a7c7120` |
| 1.14.3 Yarn | `net/minecraft/client/world/ClientWorld.java` | `9c72e6f4d142b5ccbe33f4f33e6593705fd7e02755275f0f8a330561463117e2` |
| 1.14.3 Yarn | `net/minecraft/entity/Entity.java` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| 1.14.3 Yarn | `net/minecraft/entity/LivingEntity.java` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` |
| 1.14.3 Yarn | `net/minecraft/client/network/ClientPlayerEntity.java` | `28b6b4031e02af4d2e6e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae` |
| 1.14.4 Mojmap | `net/minecraft/world/level/LevelReader.java` | `1f86a029e4404b91aa532ca33ab0dbb63b085334c561ce44e0d0875aa50fdbc9` |
| 1.14.4 Mojmap | `net/minecraft/world/level/chunk/ChunkSource.java` | `98fa075980543e2a495e5d421bd2ceed17b98a94097902f1601b5aa8060a2beb` |
| 1.14.4 Mojmap | `net/minecraft/client/multiplayer/MultiPlayerLevel.java` | `3d31b9e969da5a4c79c49987648c921ddd30da1946e4fae5a3dec7f62252bf10` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` |
| 1.14.4 Mojmap | `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |

## Gate comparison

### AABB contact producer

1.14.3 `Entity.updateMovementInFluid(WATER)` (`Entity.java:2554–2563`) contracts the AABB by `0.001`, derives `floor(min)` / `ceil(max)` on each axis, then calls `world.isAreaLoaded(minX,minY,minZ,maxX,maxY,maxZ)`. 1.14.4 `Entity.checkAndHandleWater(WATER)` (`Entity.java:2588–2597`) performs the same operations and calls `level.hasChunksAt` with the same argument order.

The defaults in 1.14.3 `ViewableWorld.isAreaLoaded` (`:235–254`) and 1.14.4 `LevelReader.hasChunksAt` (`:238–257`) match: accept only when max-Y is at least zero and min-Y is below 256; shift both X/Z endpoints right by four; iterate from each minimum chunk through its maximum chunk **inclusive**; reject if any predicate call is false. This means a ceil-derived terminal coordinate exactly on a chunk boundary includes that terminal chunk in the guard, even though the subsequent fluid scan iterates its block end exclusively. Both versions make the same guard decision for corresponding coordinates.

For local clients, dispatch reaches 1.14.3 `ClientWorld.isChunkLoaded` (`ClientWorld.java:212–215`) and 1.14.4 `MultiPlayerLevel.hasChunk` (`MultiPlayerLevel.java:225–228`); each returns `true`. Therefore partial/missing client chunks do not make either area guard fail. The base implementations (`ChunkManager.isChunkLoaded`, `ChunkManager.java:25–27`; `ChunkSource.hasChunk`, `ChunkSource.java:29–31`) are also structurally identical: `getChunk(x,z,FULL,false) != null`, but are not selected for the local client override.

### Eye-point gate

1.14.3 `Entity.isSubmergedIn(tag,true)` (`Entity.java:1032–1045`) computes `BlockPos(x, y + getStandingEyeHeight(), z)` and checks `world.isChunkLoaded(posX >> 4, posZ >> 4)`. 1.14.4 `Entity.isUnderLiquid(tag,true)` (`Entity.java:1057–1070`) computes the corresponding eye point and checks `level.hasChunk(posX >> 4, posZ >> 4)`. The loaded-point predicates dispatch to the same local-client overrides above, both unconditionally true. Their shift operation also selects the same chunk for negative coordinates. Thus the point guard does not reject a partially loaded local-client edge in either version.

### Tick ordering and disposition

Both entity `tick()` methods call `baseTick()`. In 1.14.3, `baseTick()` invokes `updateInWater()` before `LivingEntity.tick()` reaches `ClientPlayerEntity.tickMovement()`; in 1.14.4, `baseTick()` invokes `updateWaterState()` before `LivingEntity.tick()` reaches `LocalPlayer.aiStep()`. The contact producer and eye-submersion writer therefore evaluate before the sprint input readers in both versions, including on the partial-client edge. No update-timing difference was found for this dependency.

This comparison closes only the chunk-gate dependency. Missing-chunk fluid-state lookup behavior after a `true` gate is not adjudicated here, so no broader partially-loaded fluid producer equivalence or divergent witness is claimed. No product/backlog/wiki files were edited; no build, tests, or runtime checks were performed.
