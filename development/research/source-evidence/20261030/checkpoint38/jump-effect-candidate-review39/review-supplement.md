# Supplement: receiver branch fidelity and lifecycle

## Correction to the initial review

The accepted audit's weak-first `w > 1800` event stream is the witness that demonstrates a movement consequence, not a hard-coded domain restriction on the accepted receiver operation. I withdraw initial review item 2's request to gate the receiver to that fixture or obtain a new finding. `HiddenJumpBoostEffectReceiver.Effect.update()` implements the general amplifier/duration branches that produce that condition: it snapshots current only when incoming amplifier is stronger **and** incoming duration is shorter; on a longer update it extends equal amplifier or creates/updates a weaker hidden instance. This matches the complete movement-relevant state transitions in 1.15.2 `MobEffectInstance.update()` (lines 66–109). Its tick projection also matches `tick(LivingEntity, Runnable)` / `tickDownDuration()` (lines 135–157): hidden duration is decremented with active duration, active expiry restores hidden details, and further ticks stop for nonpositive active duration. The omitted fields and callback concern effect presentation / native effect notification; this shadow exposes only the Jump Boost amplifier and does not run effect application or mutate the native effect map.

`LegacyJumpBoostEffectReceiver` matches the movement-relevant branches of 1.14.4 and 1.15.1 `MobEffectInstance.update()` / `tick()`: greater amplifier replaces, equal amplifier only extends when incoming duration is greater, lower amplifier does not replace, and nonpositive duration is retained by client effect ticking until REMOVE. It preserves integer amplifier arithmetic. The 1.15.2 shadow's `0.1F * (amplifier + 1)` also preserves the old jump expression's integer `+ 1` before float multiplication (1.14.4 `LivingEntity.jumpFromGround()` lines 1752–1758; 1.15.2 lines 1803–1810). No movement-state counterexample was found for arbitrary integer amplifier or duration values, including nonpositive/infinite-duration sentinels: the compared source branches use the same integer comparisons and `duration > 0` tick gate.

The product's existing `JumpBoostHiddenFallback` / `JumpBoostFallbackMixin` overlap remains a product-reconciliation concern. The parent integrator has said it will remove that obsolete state/response while wiring this candidate; once removed, it no longer blocks acceptance of the candidate's source-projected receiver behavior. Review at final wiring should verify the receiver's result is composed once with the selected `JumpPowerHook` and that the older `V1_13` raw-effect branch remains outside this new receiver range.

## Join and respawn state

No extra global reset/bootstrap appears necessary for the candidate's `WeakHashMap<Player, Effect>` on the inspected 26.2 lifecycle:

- On login, `ClientPacketListener.handleLogin()` creates the `LocalPlayer` if needed, adds it to the `ClientLevel`, and installs it as `Minecraft.player` before the server's `PlayerList.placeNewPlayer()` calls `sendActivePlayerEffects()` for that player. Each active effect is sent as `ClientboundUpdateMobEffectPacket`.
- On respawn, `ClientPacketListener.handleRespawn()` creates a **new** `LocalPlayer`, assigns it to `Minecraft.player`, and copies selected entity data, movement, rotation and attributes; it does not copy `activeEffects`. The server's `PlayerList.respawn()` sends the respawn packet and then `sendActivePlayerEffects()` for the replacement server player. The packet mixin resolves the new local player and receiver, seeding state from those updates.
- The receiver's weak key is the prior `LocalPlayer` object, and the value has no player reference. The old shadow entry therefore does not attach to the replacement player and is collectible after other old-player references disappear. With no active Jump Boost, an absent entry correctly falls through to the native zero contribution.

This establishes the source lifecycle path, not runtime packet/tick parity.

## Separate movement consumer left uncovered

The candidate substitutes only `LivingEntity.getJumpBoostPower()` (`LivingEntityJumpBoostMixin.java:24–29`). The compared 1.14.4 and 1.15.2 `LocalPlayer.updateAutoJump()` also reads `hasEffect(JUMP)` and `getEffect(JUMP).getAmplifier()` directly to compute its jump-height allowance (`1.14.4 LocalPlayer.java:891–894`; `1.15.2 LocalPlayer.java:894–898`). Current 26.2 `LocalPlayer.updateAutoJump()` retains a corresponding direct `hasEffect(JUMP_BOOST)` / `getEffect(...).getAmplifier()` read (`LocalPlayer.java:1038–1041`), and the product's `LocalPlayerMixin` auto-jump redirects do not replace that read.

During the accepted old-client divergence, after an amp-0 restore update reaches the modern native map but before REMOVE, the legacy receiver shadow still reports amp 1 while `updateAutoJump()` reads the modern map's amp 0. Thus this candidate does not reproduce the receiver's value for that separate auto-jump predicate. This does **not** invalidate the bounded `jumpFromGround` / `getJumpBoostPower` operation accepted by the finding; it means product notes and integration must not claim complete historical Jump Boost receiver behavior for every movement consumer until that direct-read seam is handled by a separately accepted operation or explicitly remains outside this bounded change.

## Updated disposition

**ACCEPT the receiver algorithm's movement-state projection for the accepted bounded operation, subject to final product wiring that removes the duplicate old fallback mechanism.** The initial lifecycle-reset request is withdrawn based on the source trace above. The direct auto-jump effect read is a separate uncovered movement consumer, not a counterexample to the accepted jump-power operation. Overall runtime parity remains unverified.
