# Independent source review: Levitation fall-distance reset phase

## Scope and verdict

This fresh source examination compares the local-player-reachable Levitation path in exact Mojmap 1.19.4 and 1.20 sources. I independently verified the tick/travel, movement, edge-backoff, local input, and effect-receipt paths before looking at any prior closure or audit. The conditional source claim is **confirmed**: with equivalent received Levitation and player state, moving the reset before travel can change the same tick's `isAboveGround()` result and horizontal sneak edge-backoff. This is a conditional source-level movement consequence, not a claim that every effect onset or tick produces a trajectory difference.

The comparison requires: an off-ground local player; a newly received/equivalent active Levitation effect before its next local `LivingEntity.aiStep`; prior `fallDistance >= maxUpStep`; non-flying abilities; shift held; the SELF travel move has requested `y <= 0`; and support geometry for which lowering the player's box by `maxUpStep` collides while a horizontal displacement across the ledge would not. The exact collided block shape and the client event-loop timing of a packet relative to a tick are not established here.

## Exact source identity

The source root is `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

- 1.19.4 uses Mojmap. `1.19.4/mojmap.ready.json` is SHA-256 `59f59ab516d41eb97e09d63256ee26a68c15102052bf7acafdbd5440b72547d0`, says `versionId=1.19.4`, `status=ready`, and binds `mojmap.sources.sha256` SHA-256 `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`. The source-manifest file hash equals the ready marker's source-manifest hash. `artifacts.sha256` is SHA-256 `e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72`.
- 1.20 uses Mojmap. Its embedded `1.20/mojmap/.decompile-provenance.json` SHA-256 is `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f`; all selected source files below match its embedded `sources` map.

The selected canonical source hashes and path/manifest checks are in `source-hashes.tsv` beside this report. Every selected path matched the corresponding exact release source manifest or embedded provenance map.

## Phase order and local movement call path

`LocalPlayer.aiStep()` reaches `super.aiStep()` in both versions (1.19.4 `client/player/LocalPlayer.java:795`; 1.20 `:801`). LocalPlayer's `isShiftKeyDown()` reads `input.shiftKeyDown` (1.19.4 `LocalPlayer.java:595-596`; 1.20 `:598-599`). The same `Input.java` hash is present in both trees.

In 1.19.4, `LivingEntity.aiStep()` runs the travel phase without first clearing fall distance (`world/entity/LivingEntity.java:2540-2551`). For the ordinary controlled-player travel path, `LivingEntity.travel()` enters its land movement branch, calls `handleRelativeFrictionAndCalculateMovement()` (`:2092`), and only after that helper returns does its Levitation branch call `resetFallDistance()` (`:2094-2096`). The helper calls `moveRelative`, `handleOnClimbable`, then `move(MoverType.SELF, deltaMovement)` before returning (`LivingEntity.java:2152-2155`). Thus the `SELF` move in that tick observes the prior fall-distance value.

In 1.20, `LivingEntity.aiStep()` calls `resetFallDistance()` when Slow Falling or Levitation is active, immediately before `travel()` (`world/entity/LivingEntity.java:2565-2572`). Its ordinary travel helper still calls `move(MoverType.SELF, deltaMovement)` (`:2171-2174`), but the reset has already happened. The changed placement is an observable order difference before an existing local movement callback.

## Edge-backoff consequence

`Entity.move()` bypasses collision processing for `noPhysics`; for the normal collision path it calls `maybeBackOffFromEdge(requestedMovement, moverType)` before `collide()` in both versions (1.19.4 `world/entity/Entity.java:549-570`; 1.20 `:593-614`). `Player.maybeBackOffFromEdge()` has the same relevant gates and 0.05 decrement loops in both versions: not flying, requested Y `<= 0`, mover type SELF or PLAYER, `isStayingOnGroundSurface()`, and `isAboveGround()` (1.19.4 `world/entity/player/Player.java:1041-1092`; 1.20 `:1043-1094`). `isStayingOnGroundSurface()` is precisely `isShiftKeyDown()` in each version (1.19.4 `Player.java:308-310`; 1.20 `:309-311`).

Both versions' `isAboveGround()` is `onGround || (fallDistance < maxUpStep && !noCollision(box moved by (0, fallDistance - maxUpStep, 0)))` (1.19.4 `Player.java:1095-1099`; 1.20 `:1097-1101`). With `onGround=false` and prior `fallDistance >= maxUpStep`, the old pre-move predicate is false. After the 1.20 reset to zero, it is true if the player's box lowered by `maxUpStep` intersects support. The subsequent existing 0.05 loops can reduce horizontal movement where the proposed offset box lowered by `maxUpStep` has no collision. Therefore, on qualifying ledge geometry, the reset-phase change can suppress old edge-backoff versus enable the new edge-backoff in the same tick.

This reasoning establishes a changed gate and a conditional horizontal response; it does not assert a specific block arrangement or collision result without the shape/provider closure.

## Equivalent received state and reachability

In each version, `ClientPacketListener.handleUpdateMobEffect()` ensures main-thread handling, resolves the packet entity, constructs the supplied effect/duration/amplifier, and calls `LivingEntity.forceAddEffect()` when the entity is a `LivingEntity` and the effect is non-null (1.19.4 `client/multiplayer/ClientPacketListener.java:1483-1500`; 1.20 `:1497-1514`). For a packet naming the local player, the same Levitation instance becomes active. `forceAddEffect()` updates the active-effect map and dispatches `onEffectAdded`; client-side `onEffectAdded` marks effects dirty and skips the server-only attribute update (1.19.4 `LivingEntity.java:958-966,988-993`; 1.20 `:973-981,1003-1009`). This path contains no fall-distance reset, so equivalent already-received player/effect state preserves the preexisting fall distance until the next player tick.

Before Levitation is received, the local player's ordinary downward SELF movement accumulates fall distance: `Entity.move()` reaches `checkFallDamage()` after collision and passes the collision-resolved vertical displacement (`1.19.4 Entity.java:569,599`; 1.20 `:613,643`). If the player is not on ground and that resolved displacement is negative, `checkFallDamage()` subtracts it from the float `fallDistance` (1.19.4 `:1002-1012`; 1.20 `:1101-1116`). Landing resets it. Repeated downward movement can therefore make the pre-receipt value at least `maxUpStep`; the client effect handler does not clear it. Exact packet/tick scheduling is left conditional: the claim applies if the effect update is processed before the next local tick, as the handler's main-thread dispatch permits, and the listed state survives until that tick.

The new reset also runs for Slow Falling, but this review's movement witness is specifically Levitation. It holds received effect state and the rest of the local-player/world state equal across versions, so it does not infer or reproduce server-side effect production.

## Limits

- No exact collision-shape witness was constructed; `noCollision` and the lowered-box overlap condition depend on block geometry and world state.
- No packet scheduling order, runtime trajectory, server producer, or damage/fall-damage outcome is claimed.
- This is a bounded source closure for one direct player movement dependency. It does not freeze 1.19.4→1.20 or establish complete pair coverage.

## Independent conclusion recorded before prior-report comparison

The canonical source alone establishes a conditional difference in the local player's first ordinary nonpositive-Y SELF travel move after Levitation is received when preexisting fall distance is at least the step height and the support/edge geometry satisfies the two `isAboveGround`/backoff tests. Existing subsequent reset behavior means this is specifically an onset-phase ordering case; it does not by itself establish multi-tick trajectory divergence.
