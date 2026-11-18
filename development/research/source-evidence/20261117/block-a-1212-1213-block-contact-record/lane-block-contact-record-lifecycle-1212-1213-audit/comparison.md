# 1.21.2 → 1.21.3 block-contact record lifecycle

## Verdict

**No scoped source difference.** The four frozen player movement classes are byte-identical across the exact Mojmap publications. `Entity.move` and `LocalPlayer.move` create no environmental block movement record in either release. The same post-travel no-argument block-effects path supplies `oldPosition()` → current `position` as a fallback record, scans accumulated records, and clears its record and block-state collections. No movement-list copy appears in the scoped methods. The teleport-position setter remains a direct list invalidator in both releases.

This is source coverage for the assigned operation, not runtime parity or acceptance of a player trajectory.

## Exact sources and identity

All line references below apply to both exact versions because the paired files have matching SHA-256 values. The full paired roster and publication identities are in `roster.md`.

- `net/minecraft/world/entity/Entity.java`: 1.21.2 and 1.21.3 SHA-256 `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`.
- `net/minecraft/client/player/LocalPlayer.java`: both SHA-256 `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0`.
- `net/minecraft/world/entity/LivingEntity.java`: both SHA-256 `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`.
- `net/minecraft/world/entity/player/Player.java`: both SHA-256 `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de`.

## Record creation and post-travel scan

- `Entity.java:619–701`: `move(MoverType, Vec3)` performs movement. Its `PISTON` branch limits displacement (`624–628`), while the ordinary path performs collision/movement and post-move state handling. Neither branch appends to `movementThisTick` or calls `recordMovementThroughBlocks`.
- `LocalPlayer.java:872–881`: the override captures starting X/Z, calls `super.move`, then updates auto-jump and `walkDist`; it does not create a block movement record.
- `LocalPlayer.java:638,795`: `LocalPlayer.aiStep()` delegates to `super.aiStep()`. `LivingEntity.java:2774–2776`: after `travel`/`travelRidden`, calls `applyEffectsFromBlocks()` under `!level().isClientSide() || isControlledByLocalInstance()`. This retains the vanilla gate; it permits the controlled local player on the client.
- `Entity.java:736–752`: the no-argument overload delegates with `oldPosition()` and the current `position` (`736–738`). The two-argument overload, if affected by blocks, appends this fallback `Entity.Movement` (`748`), scans the existing list (`749`), evaluates the accumulated `blocksInside` set (`750`), then clears `movementThisTick` and `blocksInside` (`751–752`). It does not copy the movement list.
- `Entity.java:1034–1036`: `recordMovementThroughBlocks(from,to)` is a direct append API. In the frozen player classes there is no caller that supplies ordinary or piston movement to it. Thus the player path reaches the no-argument fallback after travel; `Entity.move` itself does not record either movement origin.

## Piston-origin immediate scan

`PistonMovingBlockEntity.java:182–186` calls `entity.move(MoverType.PISTON, displacement)` and immediately calls the no-argument `entity.applyEffectsFromBlocks()`; it passes no explicit endpoint pair. This uses the same `Entity.java:736–752` fallback and normal list/set clear path. There is no piston-specific record list or list copy. The fallback start is `oldPosition()` rather than a coordinate captured immediately before the piston move. This direct piston producer/consumer pair is byte-identical in the two versions.
## Teleport invalidation

`Entity.java:2721–2731` (`teleportSetPosition`) calculates/applies the corrected position and rotation, calls `setOldPosAndRot`, applies delta movement, then clears `movementThisTick`. This directly invalidates queued movement records in both versions. It does not clear `blocksInside` at that site. No other teleport path in the frozen classes directly appends or clears `movementThisTick`.

## Callback traversal observation

`Entity.java:1038–1077` is the actual inside-block callback scan, distinct from the solid-shape clipping in `Entity.move` / `collide` (`619–701`, `960–1015`). It forms a deflated current bounding box, walks each record in list order and each position from `BlockGetter.boxTraverseBlocks(from,to,box)` (`BlockGetter.java:172–189`), skips dead entities, air states, and positions already in `visitedBlocks`, then obtains `getEntityInsideCollisionShape`. It calls `BlockState.entityInside` followed by `onInsideBlock` when the entity-inside-shape predicate passes (`1052–1061`), and accumulates the state in `blocksInside` (`1071`). This is a player response/callback path; it does not establish a change to solid collision geometry. If a callback makes the entity not alive, the early return at `1048–1050` skips `visitedBlocks.clear()` at `1076`; the outer effects method still proceeds to its list/set clears after the scan returns.

## Exclusions and limits

This report does not characterize non-player producers, packet/server authority, solid block-shape parity, runtime execution, or all callers outside the frozen classes. The conclusion is limited to the requested player movement-record lifecycle and callback traversal in this 1.21.2 → 1.21.3 source pair.