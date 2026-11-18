# Environmental LocalPlayer block-inside record lifecycle — 1.21.2 → 1.21.3

## Pair verdict

**No difference in the bounded operations.** The relevant paired source files are byte-identical (see `roster.md`); line references below therefore apply to both releases. This is source comparison only and makes no runtime-parity claim.

## 1. Movement recording and the client travel fallback

- `Entity.move(MoverType, Vec3)` is at `Entity.java:619`. In its no-physics branch it updates position directly; otherwise it resolves movement and calls `setPos` after collision. Neither branch appends to `movementThisTick`. `LocalPlayer.move` at `LocalPlayer.java:873` delegates to `super.move`, then performs auto-jump and walk-distance bookkeeping; it adds no movement record.
- The local player reaches `LivingEntity.aiStep` through `LocalPlayer.aiStep` (`LocalPlayer.java:638`, `795`: `super.aiStep()`). After `travel`/`travelRidden` at `LivingEntity.java:2768–2772`, `LivingEntity.aiStep` calls `applyEffectsFromBlocks()` when the level is not client-side or the entity is locally controlled (`LivingEntity.java:2774–2775`).
- `Entity.applyEffectsFromBlocks()` (`Entity.java:736–737`) supplies `oldPosition()` and current `position`. Its Vec3 overload (`Entity.java:740–752`) appends that endpoint pair as a `Movement` when affected by blocks, checks the whole current list, then clears the movement list and `blocksInside`. Thus the normal LocalPlayer post-travel path contributes an old-position-to-current-position fallback after any earlier records already queued.
- The public `recordMovementThroughBlocks(from,to)` appends to the same list (`Entity.java:1034–1035`). A full source search finds its direct callers only in `ServerGamePacketListenerImpl.java:452,930`; these are server packet-handler paths, not a LocalPlayer-specific movement recorder. No client-side copy, alternative record list, or LocalPlayer-specific list reset was found. `Entity.teleportSetPosition` also clears `movementThisTick` at `Entity.java:2731`.

## 2. Piston callback endpoints and list handling

`PistonMovingBlockEntity.moveEntityByPiston` (`PistonMovingBlockEntity.java:182–186`) sets the piston no-clip direction, calls `entity.move(MoverType.PISTON, displacement)`, then calls the no-argument `entity.applyEffectsFromBlocks()`. The piston helper passes no explicit from/to coordinates to that callback. The no-argument overload therefore supplies `oldPosition()` and the post-move current `position` (not a captured pre-piston-move position); the regular fallback append/check/clear path processes it. No piston-specific movement list, copy, or reset is used in this callback.

## 3. Post-travel consumer and callback-cell visitation

`Entity.checkInsideBlocks` (`Entity.java:1038–1078`) iterates each queued `Movement` in list order and asks `BlockGetter.boxTraverseBlocks(from,to,deflatedEntityBox)` for the candidate cells. A per-entity `visitedBlocks` set receives each non-air candidate cell's packed `BlockPos` before the shape gate, preventing another queued trajectory from revisiting that cell during this pass. The set is cleared at the normal end of the pass (`Entity.java:1077`); if the entity dies and the method returns early (`:1048–1049`), that clear is skipped. Callback shape gating is intentionally not expanded here.

`applyEffectsFromBlocks(Vec3,Vec3)` invokes this consumer and clears `movementThisTick` and `blocksInside` after it returns (`Entity.java:748–752`). Those list/block-state clears occur inside the outer `isAffectedByBlocks()` guard; when unaffected, that method skips appending, checking, and those clears. No difference between the paired versions appears in this lifecycle.