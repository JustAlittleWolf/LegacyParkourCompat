# WORLD-27-SERVER: loaded-chunk gate difference; fallback correction reachability unestablished

- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`.
- **1.9.4**, ornithe-feather: `1.9.4/ornithe-feather/net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`.

## Difference

1.8.9's `World.isChunkLoaded(BlockPos,boolean)` first rejects positions outside `World.contains`, including X/Z outside `[-30,000,000,30,000,000)`. 1.9.4 removes that containment check. 1.8.9 `ServerWorld` inherits `World.isChunkLoadedAt`, which checks `chunkSource.hasChunk`; 1.9.4 `ServerWorld.isChunkLoadedAt` delegates to `ServerChunkCache.isChunkLoaded`.

The collision scan reaches these methods through the one-argument overload at `(x,64,z)`, which passes `allowEmpty=true`. 1.8.9's server cache tests loaded-position membership in `chunksByPos`; 1.9.4's tests membership in `chunks`.

Actual membership is an external provider input.

Under the finding's loaded-cell precondition, 1.8.9 rejects X=30,000,000 before consulting it, while 1.9.4 admits the cell.

Both `World.getCollisions` bodies retain the initial stone state only when the candidate is outside the **effective** border and the player is within its one-block margin.

The default full-cube provider and 1.9.4's non-corner scan guard remain source evidence, but `setSize` alone cannot establish effective bounds of +/-30,000,000.

The cap producer dependency below prevents the old fallback witness from proving historical-player applicability.

## Movement meaning

The server player route is exact on both sides: `ServerPlayNetworkHandler.handlePlayerMove` has an awake, non-riding packet branch; it queries `serverWorld.getCollisions` for the contracted player shape, calls `player.move` for the packet displacement, sets the requested position, and then queries collisions again. 1.8.9 uses lines 310-315 and 332-338; 1.9.4 uses lines 431-439 and 460-466. `ServerPlayerEntity` extends `PlayerEntity`, which extends `LivingEntity`, which extends `Entity`; none of the intervening classes declares `move`, so the call resolves to the paired `Entity.move(DDD)V` (1.8.9 line 371, 1.9.4 line 441).

The class/source hashes are 1.8.9 `ServerPlayerEntity.java` , `PlayerEntity.java` , and `LivingEntity.java` ; 1.9.4 `ServerPlayerEntity.java` , `PlayerEntity.java` , and `LivingEntity.java` .

As an unestablished hypothetical only, when the initial query is empty but 1.9.4's final query sees the fallback box, the existing `bl && (bl2 || !bl3)` guard sends a teleport correction to the pre-move position.

In 1.8.9 the out-of-bound candidate is skipped, so the same queries stay empty and that correction guard does not fire.

State input: server chunk-cache loaded membership -> `ServerWorld.isChunkLoadedAt` -> `World.getCollisions` candidate-cell gate.

The loaded state is supplied by the server chunk provider; this finding does not emulate or claim a change in chunk loading/unloading.

The player packet position is an input; the effective border bound must be derived from its cap writer, not assumed from diameter.

The handler-to-collision call route is proven; the stated fallback response reachability is not.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-27-server-player-chunk-gate.md).
