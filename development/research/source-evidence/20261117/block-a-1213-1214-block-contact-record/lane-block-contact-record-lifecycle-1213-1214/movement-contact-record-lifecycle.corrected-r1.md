# Block-contact movement record lifecycle (corrected r1)

1.21.3, Mojmap: `net/minecraft/world/entity/Entity.java`, `move`, `applyEffectsFromBlocks`, `isAffectedByBlocks`, `recordMovementThroughBlocks`, `checkInsideBlocks`, and `teleportSetPosition` (lines 619–769, 1034–1077, 2721–2732).
1.21.4, Mojmap: `net/minecraft/world/entity/Entity.java`, corresponding members (lines 627–781, 1046–1089, 2749–2760).

## Record creation and movement writes

Both releases keep a per-entity `movementThisTick` list of `Movement(Vec3 from, Vec3 to)` records. `Entity.move` updates the entity position through the collided displacement and `setPos`; it does not append a movement record (1.21.3 lines 619–652; 1.21.4 lines 627–660). The `MoverType.PISTON` branch first applies `limitPistonMovement`; if that returns `Vec3.ZERO`, `move` returns before the ordinary movement position write (1.21.3 lines 622–626; 1.21.4 lines 630–634). That piston-specific suppression does not itself append a record and is unchanged across this pair.

The no-argument `applyEffectsFromBlocks()` supplies `oldPosition()` and current `position` to the overload (1.21.3 lines 736–740; 1.21.4 lines 747–751). `oldPosition()` constructs a vector from stored `xOld`, `yOld`, and `zOld` (1.21.3 line 1501; 1.21.4 line 1522); the destination is current actual `position`, which ordinary movement updates via `setPos` after collision resolution. In the overload, record creation is conditional: only when `isAffectedByBlocks()` passes does it run the optional on-ground `stepOn` callback and append `Movement(from,to)` (1.21.3 lines 740–749; 1.21.4 lines 751–759). That predicate is `!isRemoved() && !noPhysics` in both releases (1.21.3 lines 769–771; 1.21.4 lines 781–783). When it fails, the overload does not synthesize the tick-level record or clear the accumulator.

The public `recordMovementThroughBlocks(from,to)` also appends a record directly (1.21.3 lines 1034–1035; 1.21.4 lines 1046–1047). The inspected `Entity.move` and `LocalPlayer.move` bodies do not call it. Direct server call-site bodies are outside this accepted claim and were not reviewed.

## Tick fallback and list lifecycle

The zero-limited piston early return exits only `Entity.move`. After `travel` returns, the enclosing `LivingEntity.tick` can still call the no-argument `applyEffectsFromBlocks()` under its client/local-instance gate. If `isAffectedByBlocks()` then passes, that later call appends the tick-level `oldPosition()`→current-`position` record, including the fallback when the piston move itself made no position update. This later append is conditional and is not performed by the piston branch.

With the block-effects gate open, 1.21.3 appends the tick-level pair, passes the live accumulated list to `checkInsideBlocks`, evaluates the fire/lava-state predicate, then clears `movementThisTick` (lines 740–752). 1.21.4 appends the same pair, snapshots the accumulated list with `List.copyOf`, clears the mutable list before calling `checkInsideBlocks(snapshot, ...)`, then evaluates the predicate (lines 751–763). Therefore 1.21.3 traverses the live list and clears it after traversal; 1.21.4 traverses a snapshot and clears the accumulator before traversal. If the gate is closed, neither release performs the append or this tick-path clear, so existing records remain accumulated. Both releases also clear `movementThisTick` in `teleportSetPosition` after resetting position/orientation/delta movement (1.21.3 lines 2721–2732; 1.21.4 lines 2749–2760).

## Callback bounds difference

`checkInsideBlocks` passes each record's `from` and `to` to `BlockGetter.boxTraverseBlocks`. In 1.21.3 it computes one `getBoundingBox().deflate(1.0E-5F)` before iterating and reuses that current entity AABB for every record (lines 1038–1046). In 1.21.4 it computes `makeBoundingBox(to).deflate(1.0E-5F)` inside the record loop (lines 1050–1059), so each record's destination supplies that record's swept AABB endpoint. The reviewed `AABB.java` helpers are source-identical across the pair; no `deflate`, `move`, or `expandTowards` change was found.

The remaining callback traversal order is unchanged in the compared Entity methods: for each candidate cell, the alive, non-air, and visited-cell checks precede `BlockState.entityInside` and `onInsideBlock`. Solid-shape outcomes are outside this finding.

## LocalPlayer reachability and bounded no-difference witness

`LocalPlayer.tick` delegates through `AbstractClientPlayer`→`Player`→`LivingEntity.tick` (1.21.3 lines 189–191, 280, 2418–2419; 1.21.4 lines 191–195, 283, 2429–2430; 1.21.4 delegation is inside `hasClientLoaded()`). After `travel`, `LivingEntity.tick` invokes `applyEffectsFromBlocks()` when `!level.isClientSide() || isControlledByLocalInstance()` (1.21.3 lines 2774–2777; 1.21.4 lines 2785–2788). `Entity.isControlledByLocalInstance()` uses `controllingPassenger.isLocalPlayer()` when the controlling passenger is a `Player`, and otherwise falls back to `this.isEffectiveAi()` (1.21.3 lines 3149–3150; 1.21.4 lines 3173–3174). `LocalPlayer.isEffectiveAi()` returns true (1.21.3 lines 478–480; 1.21.4 lines 483–485), establishing the ordinary LocalPlayer client gate. This describes the source predicate, not server authority.

`LocalPlayer.move` only delegates to `super.move` and updates auto-jump/walk-distance state; it has no record append in either release (1.21.3 lines 873–881; 1.21.4 lines 903–911). For the ordinary LocalPlayer movement path, the automatic append occurs after travel and is conditional on `isAffectedByBlocks()`. No in-scope per-move writer was found. Thus the segment-specific AABB change has no demonstrated effect for the one automatically appended old-position→final-position segment when that gate passes; this does not close the separate BlockGetter candidate-traversal claim or establish a trajectory result.

## Source disposition

Independent blind audit: this bounded Entity lifecycle claim is accepted with the qualifications stated here. The audit accepts this claim only; it does not establish full-pair coverage, an intermediate patch boundary, or runtime parity.