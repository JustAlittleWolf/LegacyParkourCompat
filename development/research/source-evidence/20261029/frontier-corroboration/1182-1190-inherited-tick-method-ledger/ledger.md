# Inherited local-player tick method ledger: 1.18.2 → 1.19

## Scope and method

Exact Mojmap source comparison for the inherited `Entity` / `LivingEntity` / `Player` portions of the ordinary local-player tick. This is a bounded method ledger, not a full-pair closure or independent audit. The permitted ordinary-movement inventory and pair audit were used only to locate slices already owned elsewhere; method call paths and selected source bodies were retraced here.

The ordered client route is `Player.tick` → `LivingEntity.tick` → `Entity.tick` → virtual `LivingEntity.baseTick` → `Entity.baseTick`, then `LivingEntity.tick`'s virtual `aiStep` → `Player.aiStep` → `LivingEntity.aiStep` → jump/travel dispatch. The LocalPlayer override and input setup are covered by the existing ordinary inventory; `Player.travel` delegates to `LivingEntity.travel`, whose movement reaches `Entity.move` and virtual `Player.maybeBackOffFromEdge` before collision resolution.

I compared exact method body text as SHA-256, from each opening `{` through its matching close. The scanner respects Java comments, strings and character literals. The selected slice has 82 method signatures: 65 paired identical bodies, 13 paired changed bodies, and four methods newly present in 1.19 (`getOnPos(float)`, `getOnPosLegacy()`, `getBlockStateOnLegacy()`, `hasPose(Pose)`). Full selected identities and body hashes are in `selected-method-hashes.tsv`; `extract.py` and `selected.py` reproduce the index. Exact body equality is a source-text prune, not a semantic dependency-closure claim.

All six cited `Entity.java`, `LivingEntity.java` and `Player.java` files were rehashed and matched their respective `mojmap.sources.sha256` entries. The canonical roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.18.2\mojmap` and `...\1.19\mojmap`.

| Side | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|---|---|
| 1.18.2 Mojmap | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` | `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` |
| 1.19 Mojmap | `b00a68db8600ec8cdc3917357aff59eb0537be6f382e621e5ed3eeb900898b88` | `833cf50d6eab0f24475dcc11085c1414c85eee0e708ff17c59bc8dcd2ae9c35a` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` | `eee4a52f029446e075ff10ef761cea9a621b40df6e66240b6323d1596ca60ebf` |

| Source file | 1.18.2 SHA-256 | 1.19 SHA-256 | Manifest match |
|---|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` | both |
| `net/minecraft/world/entity/LivingEntity.java` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` | both |
| `net/minecraft/world/entity/player/Player.java` | `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` | `4b5d3d71ac1c4b64efdeb1ad86a4b6f84afffe210e272850e620339430942de1` | both |

## Ordered coverage

1. **Base tick.** `Entity.tick()` and `Entity.baseTick()` have identical bodies. Dynamic dispatch enters `LivingEntity.baseTick()`, which is identical and calls the same entity base implementation after its first-tick/sleep and soul-speed-particle gates. The inherited body order remains portal handling, fluid/contact sampling, swimming update, fire/lava and out-of-world checks, and tick cleanup. `handleNetherPortal`, `checkOutOfWorld`, `lavaHurt`, `clearFire`, `updateSwimming`, `updateInWaterStateAndDoFluidPushing`, and `updateFluidOnEyes` were body-hash pruned as identical. Fluid scans/predicates are reused only within the liquid closure; tag/provider and collision semantics are not inferred here.
2. **Living tick.** `LivingEntity.tick()` has an identical body. It still calls `super.tick()`, then item-use/swim bookkeeping, then virtual `this.aiStep()`. Server-only equipment/combat/sleep maintenance remains under the same `!level.isClientSide` gate. Its post-ai position-derived animation/rotation calculations are unchanged. `updateSwimAmount()` is identical and pose/animation consumers remain outside this ledger.
3. **Player tick.** `Player.tick()` differs by `wardenSpawnTracker.tick()` inside the existing `!level.isClientSide` statistics block. The local-client path does not execute that call. Its movement-relevant gates and order are unchanged: spectator-derived `noPhysics`, spectator `onGround = false`, underwater sampling, superclass tick, X/Z world-limit clamping, then pose update. The pose-update method is excluded; its body also hashes identically. `updateIsUnderwater()` is identical and belongs to the liquid/pose boundary.
4. **Living aiStep.** `LivingEntity.aiStep()` has one textual change under `isControlledByLocalInstance()`: `setPacketCoordinates(x,y,z)` becomes `syncPacketPositionCodec(x,y,z)`. It resets interpolation first in the same gate; the changed call updates packet-position codec bookkeeping, not the player's position or velocity. This is in the direct packet/position bookkeeping closure and is not counted as a movement delta here. Remaining body order is identical: interpolation/effective-AI velocity handling, small-velocity zeroing, immobility/effective-AI gates, jump selection, `xxa`/`zza` attenuation, fall-flying update, then virtual travel. The jump and travel bodies are separately hash-identical and excluded by assignment.
5. **Player aiStep.** `Player.aiStep()` is body-identical. The inherited sequence around `super.aiStep()` remains: timer/inventory bookkeeping, set flying speed, read movement attribute into speed, then movement-derived bob/contact maintenance. Modifier/attribute consumers and fluid jump behavior reuse the bounded modifier/liquid closures, not a new claim.
6. **Travel → move.** `LivingEntity.travel(Vec3)`, `Player.travel(Vec3)`, both jump methods, `Entity.setPos`, `setDeltaMovement`, velocity/position accessors, `getBoundingBox`, and `setBoundingBox` have identical method bodies in the selected ledger. `Entity.move()` is textually changed, but its relevant changed hunks are the already-owned legacy support-position call, block contact/callback gates, and game-event emission. The call to `maybeBackOffFromEdge(request, moverType)` remains before `collide(request)` in both bodies. Collision solving and block-shape providers are excluded and remain open with the collision pool.

## Unreused movement-relevant difference

`Player.maybeBackOffFromEdge(Vec3, MoverType)` is the one newly identified movement-semantic change in the inherited/travel transitive slice. In 1.18.2, horizontal safe-edge reduction runs when the player is not flying, mover type is `SELF` or `PLAYER`, `isStayingOnGroundSurface()` is true, and `isAboveGround()` is true. In 1.19 the same gate adds `request.y <= 0.0`. `Entity.move()` dispatches the hook only on its `!noPhysics` branch. When a positive-Y request reaches this virtual hook with the original gates true, 1.19 returns the horizontal request without the old 0.05-step x/z reduction; the 1.18.2 method performed that reduction. A positive-Y `SELF` move can be reached on the local travel route while requesting ground-surface staying (shift). The method-body hashes are `8d05334d0195618f7eb46528f22cd4084b1cd43bc0b13c53e3b58acea54aa243` (1.18.2) and `de24cc092f168f4d9a706e4944e4cef8451b8e63551d442093ffd5b4e529dc27` (1.19). Later `Entity.collide` still constrains the move, so this records the precise changed pre-collision adjustment, not a guaranteed final trajectory.

`Entity.maybeBackOffFromEdge`'s base implementation is identical; the difference is specifically the Player override. `Player.isStayingOnGroundSurface()` is identical and returns the shift-key state. `Player.isAboveGround()` is identical, but it calls `Level.noCollision` and reads `onGround`, `fallDistance`, and `maxUpStep`. The 1.19 extra Y gate has no such provider dependency; whether a concrete ledge path is collision-free remains for the collision/provider closure. Body hashes for the two Player helper methods and base Entity hook are in the TSV.

## Other changed-body dispositions

- `Player.tick()`'s Warden tracker addition is server-only under `!level.isClientSide`.
- `LivingEntity.aiStep()`'s packet-coordinate method rename is in the packet-position bookkeeping closure.
- `LivingEntity.updateFallFlying()` changes `GameEvent.ELYTRA_FREE_FALL` to `GameEvent.ELYTRA_GLIDE` only inside its `!level.isClientSide` event gate; its local client flag/equipment/ground gates and movement state are unchanged.
- `Entity.isCrouching()` changes from `getPose() == CROUCHING` to `hasPose(CROUCHING)`. The new `Entity.hasPose(Pose)` is exactly `getPose() == pose`, so the source proves an alias-only change with no predicate delta.
- `LivingEntity.isVisuallySwimming()` similarly changes `getPose() == FALL_FLYING` to `hasPose(FALL_FLYING)`; it is the same alias and belongs to the pose/visual boundary.
- `Entity.setPosRaw()` removes a `GameEventListenerRegistrar.onListenerMove` notification after its unchanged position/level-callback update. The numeric position write is unchanged; downstream listener effects are not established here.
- `Entity.checkFallDamage()` and `Entity.move()` contain changed landing/step game-event and block callback code, and `Entity.getOnPos()` is split into new epsilon/legacy helpers. These are reused/excluded callback and support-query slices, not new findings in this ledger. `LivingEntity.onChangedBlock`, `tryAddSoulSpeed`, and `tryAddFrost` likewise belong to the modifier/callback closure.

## Dependencies still open

- `Player.maybeBackOffFromEdge`'s edge candidate tests call `Level.noCollision` with bounding boxes and `maxUpStep`; block shape/provider/world availability remains unreviewed here.
- The generic `getOnPos()`/`getBlockStateOn()` change is not equivalent to the legacy movement contact route by name alone. The pair audit records a support-query closure identity mismatch (assigned digest differs from current closure bytes); formal acceptance of that closure remains open. This ledger observes the 1.19 `Entity.move()` and modifier call sites using legacy helpers but does not rebind that review.
- The input/options closure leaves alternative `Input` providers and Swift Sneak resources/application data open. This report does not close those providers.
- Callback, liquid/tag, modifier/equipment, pose/dimension and direct packet-response roots remain with the sibling bounded closures. Entity collision, world/chunk state, server scheduling/delivery, and arbitrary external attribute/effect writers are not closed.

This ledger does not freeze the pair. It closes only the bounded inherited-method comparison above and leaves the stated dependencies for the coordinator.

