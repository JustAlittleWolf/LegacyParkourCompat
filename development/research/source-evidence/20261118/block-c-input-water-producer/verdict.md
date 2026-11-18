# Independent source verdict: local sprint water-contact producer gate

**Terminal: COMPLETED.** This verdict is bound to the immutable source draft `c-input-water-gate-source/comparison.md`, raw SHA-256 `3a2bce15a218d961779a90c78eeca0d482ca99e8294265e20301f5e39a58f75c`.

## Verdicts

- **ACCEPT — bounded contact-field producer claim.** For a player AABB whose contracted/deflated scan region is available, the 1.14.3 Yarn producer (`Entity.checkWaterState` → `updateMovementInFluid(WATER)`) and the 1.14.4 Mojmap producer (`Entity.updateInWaterState` → `checkAndHandleWater(WATER)`) use corresponding `0.001` box contraction/deflation, floor-minimum/ceil-maximum bounds, half-open coordinate loops, matching-fluid checks and `surfaceY >= box.minY` contact predicate. They assign the respective `insideWater` / `wasInWater` contact bit before the local player's sprint code runs in that entity tick. The base tick reaches water update before `LivingEntity.tickMovement()` / `LocalPlayer.aiStep()` (`Entity.tick` → `baseTick`; `LivingEntity.tick` calls `super.tick()` first). The compared source files match the draft's stated SHA-256 values, and their provenance files match the draft's stated hashes.
- **ACCEPT — boat branch as a narrow fact only.** Each producer explicitly writes its contact bit false when the vehicle is a boat (`BoatEntity` / `Boat`). This confirms the assignment's stated boat exclusion; it does not expand the positive loaded-AABB result to ridden movement generally.
- **NO VERDICT — world-border effects.** The bounded producer question does not establish equivalence of border damage, survival, or the entire sprint gate. Those paths do not alter the contact predicate shown here; no broader border claim is accepted.
- **REJECT — any all-producer/effects equivalence reading.** The scan's loaded-area guards differ (`World.isAreaLoaded` versus `Level.hasChunksAt`). The positive result is conditional on the complete scan region being loaded; unloaded or partly unavailable regions remain unresolved and are outside the accepted scope.
- **REJECT — fluid-current equivalence as part of this result.** The overlap loop also computes and applies current. The old call gates current handling with `canFly()`; the new call uses `isPushedByWater()`. Both base `Entity` implementations return true, but this audit accepts only the contact bit and does not assert override/reachable current behavior or velocity parity.
- **NO VERDICT — submersion and callback equivalence.** `inWater` / `wasUnderWater` are computed separately and participate in `isInWater` / `isUnderWater`, `updateSwimming`, and the neighboring input-slow flag. They were explicitly left outside this producer claim, so the audit does not close the full sprint-input prerequisite. On contact transitions, the callback is called before writing true; the player overrides are spectator-gated in both versions, but callback output/equivalence is excluded. No callback parity is accepted.

## Evidence retraced

- 1.14.3 Yarn `Entity.java`: `baseTick()` calls `updateInWater()`; `checkWaterState()` handles boat, transition callback, fall-distance reset and `insideWater` write; `updateMovementInFluid()` contains the scan and current application. `LivingEntity.tick()` calls `super.tick()` before `tickMovement()`.
- 1.14.4 Mojmap `Entity.java`: `baseTick()` calls `updateWaterState()`; `updateInWaterState()` handles boat, transition callback, fall-distance reset and `wasInWater` write; `checkAndHandleWater()` contains the scan and current application. `LivingEntity.tick()` calls `super.tick()` before `aiStep()`.
- Local sprint consumers in 1.14.3 `ClientPlayerEntity.tickMovement()` and 1.14.4 `LocalPlayer.aiStep()` read these contact predicates after the inherited living-entity tick's base update.

No product source, implementation, backlog, wiki inventory, tests, build, game launch or runtime was inspected or changed.
