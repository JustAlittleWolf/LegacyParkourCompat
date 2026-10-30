# Independent audit: moving fence pose-query reachability

## Verdict

**Supported, with the spatial reachability gate left open.** In both 1.10.2 and 1.11.2 the cited client-source path can put a received `MovingBlockEntity` at progress `0.5` on the same world tick that local sneak release causes the standing-resize collision query. The exact player-box overlap needed for the reported moved-fence result is not established by this temporal proof and correctly remains conditional.

This review is limited to NBT receipt/persisted progress, tick ordering, the local sneak/pose path, and the already-loaded-chunk update distinction. It does not re-audit collision geometry or source the server-side piston trajectory.

## Traced evidence

The exact canonical sources under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.10.2,1.11.2}/ornithe-feather` agree with the closure on the claimed path:

- `WorldChunkS2CPacket(WorldChunk, sections)` includes each block entity in the packet's selected sections and serializes it through `toNbt()` (both versions, `net/minecraft/network/packet/s2c/play/WorldChunkS2CPacket.java:29–45`). The client `handleWorldChunk` updates the chunk and then looks up each NBT position and calls `readNbt` (1.10.2 `ClientPlayNetworkHandler.java:613–634`; 1.11.2 `:626–647`). `WorldChunk.getBlockEntity` can materialize the entity associated with the received moving-block state before that read; the closure's cited provider path is consistent with the source.
- `MovingBlockEntity.writeNbt` stores `lastProgress`; `readNbt` copies the restored `progress` into `lastProgress` (1.10.2 `MovingBlockEntity.java:190–207`; 1.11.2 `:296–315`). Its tick first assigns `lastProgress = progress`, then advances `progress` by `0.5F` (older `:166–182`; newer `:273–289`). Thus receipt with a restored value of `0.0F` followed by one block-entity tick yields progress `0.5F` for the next world tick.
- `Minecraft` invokes `world.tickEntities()` in its ordinary unpaused world tick (older `Minecraft.java:1438`; newer `:1419`). `World.tickEntities()` ticks players/entities before iterating `tickingBlockEntities` (older `World.java:1226–1314`; newer `:1227–1315`, including `tickPlayers()` before block-entity iteration). Consequently the local player's pose check sees progress `0.5F` on the world tick after the moving entity's first advancement and before its next advancement.
- The local player is gated through its loaded-chunk `tick()` into the inherited player tick (older `LocalClientPlayerEntity.java:152–154`; newer `:159–161`). The living-entity locally controlled AI path invokes the local `serverTickAi`; that method calls `input.tick()` (older `LocalClientPlayerEntity.java:602–665`; newer `:628–677`), and `KeyboardInput.tick()` samples `sneakKey.isPressed()` into `sneaking` (`KeyboardInput.java:13–46` both). `LocalClientPlayerEntity.isSneaking()` reads that input flag (older `:596–598`; newer `:608–610`). In the same player tick, `PlayerEntity.tick()` calls `updatePlayerPose()` after its superclass path (older `PlayerEntity.java:250`; newer `:249`). A crouched tick followed by a released-sneak tick can therefore select the standing dimensions in that player-before-moving-BE window.
- `updatePlayerPose()` selects height `1.65F` while sneaking and `1.8F` otherwise. When dimensions change, it makes the candidate box from the current box's minimum and asks `world.getCollisions(box)` before applying the new size (older `PlayerEntity.java:296–319`; newer `:295–318`). This proves the query can occur in the temporal window; the query's exact collision outcome still depends on the player's state and surrounding blocks.
- An already-loaded-chunk `handleBlockUpdate` only calls `setBlockStateFromPacket(pos, blockState)` (older `ClientPlayNetworkHandler.java:643–647`; newer `:657–660`). The separate block-entity update handler applies NBT only to its explicit type/class whitelist, which omits `MovingBlockEntity` (older `:999–1015`; newer `:1029–1046`). Therefore those update handlers do not establish moved-state/progress NBT for a newly created moving entity. This does not negate the distinct chunk-snapshot receipt path.

The cited SHA-256 values for both versions' `ClientPlayNetworkHandler.java`, `PlayerEntity.java`, and `MovingBlockEntity.java` were independently recomputed and match the closure. The remaining listed source-set/provenance identities were taken from the closure, not independently regenerated in this bounded audit.

## Remaining premise

To establish the report's exact moved-fence encounter, evidence still needs to place the local player at the required horizontal position and vertical state **at the instant `updatePlayerPose()` constructs the standing candidate box**, with the relevant neighboring layout and moving entity present in that same query. It also needs a receipt/tick history that supplies or preserves the moving entity through that local tick. No trajectory or exact box-overlap witness is supplied here. The spatial/player-box premise therefore remains open, as the closure rules.

## Method and limits

Read-only static inspection of the canonical 1.10.2 and 1.11.2 `ornithe-feather` sources. No wiki, product implementation/history, tests, build, game launch, or geometry re-audit was used.
