# R2 correction: pose sample is real; proposed sprint witness is gated

## Source delta retained

The R1 sample/cache comparison is source-accurate: 1.13.2 Feather samples a sneaking player's eye at 1.54F; 1.14 Feather's current `Pose.SNEAKING` eye value is 1.27F. The player WATER cache is updated before `super.tick()`, and the local `isSubmergedInWater()` accessor returns that cache field directly on both sides. Query position/math are unchanged for the witness (source block at y=1; old eye point y=2.24 resolves to air; new y=1.97 resolves to water, and the source-height plus `0.11111111F` threshold is approximately y=2). The optional 1.14 chunk gate still cannot fail in the ordinary local tick because `LocalClientPlayerEntity#tick()` admits the same x/z chunk first.

## R1 first-branch story remains invalid

The auto-start branch captures `bl2 = input.sneaking` before refreshing the keyboard and requires `!bl2`. If sneak was held for the prior crouch sample, physical release during this tick does not change the captured `bl2`; the branch is still closed. While sneak is held, both keyboard input methods reduce forward movement below `0.8F` as well. A held sneak input therefore cannot demonstrate an auto-start difference through `(onGround || cache)`.

## Proposed release-edge path also fails the speed gate

The suggested alternative releases sneak, holds forward and sprint, and relies on the next keyboard refresh restoring forward movement to 1.0. On the 1.14 path, the call order prevents that:

1. `LocalClientPlayerEntity#mobTick()` saves `bl2 = input.sneaking` and `bl3 = input.movementForward >= 0.8F` before calling `input.tick(bl4, isSpectator())` (`LocalClientPlayerEntity.java:625–633`).
2. `bl4` is computed from `m_63723874() || m_99544176()` before keyboard refresh. For the held-sneak prior state and a normal pose fit, `m_63723874()` returns true because its expression includes the still-true `input.sneaking` value (`LocalClientPlayerEntity.java:596–598`). The just-released physical key has not yet been sampled into the field.
3. `KeyboardInput#tick(bl, spectator)` reads the new keys, so it sets `sneaking=false` and initially sets forward to 1.0. It then still multiplies forward by 0.3 because `bl4` is true and the player is not spectator (`KeyboardInput.java:13–27`). The held-sprint branch later requires the resulting `input.movementForward >= 0.8F` (`LocalClientPlayerEntity.java:671–681`), so it fails regardless of the changed eye cache.

On 1.13.2, `KeyboardInput#tick()` has no prior-pose slowdown argument; it reads the released keys and leaves forward at 1.0 (`KeyboardInput.java:13–48`). But the old eye cache is false while body-water is true, so the held-sprint gate `(!isInWater() || cache)` fails. On 1.14, the new eye cache makes that body-water gate pass, but the 0.3 input scale makes the forward gate fail. Both versions therefore remain unsprinting in this witness. A pre-existing double-tap timer cannot fix the held-sprint branch's post-refresh forward requirement and does not open the first branch's saved `!bl2` gate.

## Other ordinary gates checked

Use an unsprinting player, a loaded local chunk, a held sprint key, forward key, no item use, no blindness, food above 6, no vehicle, and body contact with WATER. These satisfy the remaining ordinary held-sprint gates and isolate the failed input-speed requirement. If item use is active, both code paths further scale movement input and 1.13.2 also clears its double-tap timer. Hunger, blindness, ground state, and the cache's body-water conjunct do not rescue the release path. The local cache accessor does not include a body-water conjunct; the sprint condition checks body water separately.

An ordinary stable flying state is not a substitute: when `abilities.flying` is true, `PlayerEntity#updatePlayerPose()` selects `STANDING` rather than `SNEAKING` at tick end, so a steady local tick no longer samples the crouched eye value. Spectator input bypasses `KeyboardInput` scaling, but it is a distinct movement regime; any one-tick game-mode transition with a stale crouch pose requires a separately closed witness and is not claimed here. No alternate ordinary local movement consumer of this eye cache was found beyond sprint/swimming state; with the sneaking slowdown, the proposed ordinary release candidate does not establish a changed movement result.

## R2 disposition

Keep the 1.13.2→1.14 source-level eye-height/cache difference and the numeric query witness. Reject both the held-sneak auto-start explanation and the proposed sneak-release held-sprint explanation as movement witnesses. The cache writer and consumer are proven, but the ordinary input refresh negates the suggested cache-flip effect before sprint can change. No corrected movement finding is claimed in R2; retain the accompanying `.corrected-r2.md` finding draft as a reconciliation note, not an accepted permanent movement finding. Earlier drafts remain byte-for-byte unchanged.

Bounded evidence: exact 1.13.2/1.14 Feather source methods and prior independent source verdict only. No broader movement audit or runtime trajectory claim is made.