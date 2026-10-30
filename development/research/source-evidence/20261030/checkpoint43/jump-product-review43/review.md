# Product integration review: Jump Boost receiver

- Reviewed commit: `29b244f7b1fe78f6a174d2d63db074b484e3757a`
- Base: `405b19c3`
- Review scope: receiver source projection, jump consumers, hook discovery/resolution, client-only gating, mixin registration, and consolidation of the prior fallback.
- Result: the finite receiver algorithms and resolver layout are broadly consistent with the supplied accepted evidence, but two source-level integration defects remain. This review does not establish runtime parity.

## Findings

### [P1] Preserve historical priority for duration `-1` packets

`src/client/java/me/wolfii/legacyparkourcompat/change/v1_15/LegacyJumpBoostEffectReceiver.java:32-38` (same special case in `v1_15_2/HiddenJumpBoostEffectReceiver.java:28-35`) switches to `nativeSession` for any duration `-1`; the amplifier accessor then returns empty and allows the modern vanilla effect map to supply the movement value. This is not equivalent to the historical 1.14.4/1.15.1 receiver, whose `MobEffectInstance.update()` still applies its ordinary integer amplifier-priority branches for negative durations; duration only gates ticking. A concrete divergence is an active amplifier 3 followed by a lower amplifier 0 packet with duration `-1`: the old receiver retains amplifier 3, whereas the current client handler calls `forceAddEffect` for the packet and this special case exposes the replaced amplifier 0. The same issue exists in the 1.15.2 receiver projection. Keep the selected historical projection for these receiver ranges; native modern infinite-duration behavior naturally applies where no historical receiver change resolves (1.16+).

Evidence: `decompiled_minecraft/1.14.4/mojmap/net/minecraft/world/effect/MobEffectInstance.java`, `update()` (amplifier replacement / equal-amplifier longer-duration checks); `decompiled_minecraft/1.15.2/mojmap/net/minecraft/world/effect/MobEffectInstance.java`, `update()` (same checks) and `tick()` (positive-duration ticking gate); `decompiled_minecraft/26.2/unobfuscated/net/minecraft/client/multiplayer/ClientPacketListener.java`, `handleUpdateMobEffect()` (constructs the packet effect and invokes `forceAddEffect`).

### [P2] Auto-jump still uses the modern effect-presence gate

`src/client/java/me/wolfii/legacyparkourcompat/mixin/client/LocalPlayerJumpBoostMixin.java:21-34` redirects only `MobEffectInstance.getAmplifier()`. Historical 1.14.4/1.15.2 `LocalPlayer.updateAutoJump()` first gates the height adjustment with `hasEffect(JUMP)`; current 26.2 does the same with `hasEffect(JUMP_BOOST)`. In the modeled weak-first / short-strong sequence, the shadow can restore the hidden weak effect when the modern effect expires, even though the modern native effect map has removed its effect. At that point the old client enters the jump-height adjustment using the restored amplifier, but the current `hasEffect` gate skips the redirected call entirely. The added direct-amplifier redirect therefore fixes the reported restore-update mismatch while leaving the effect-absence restore path divergent. Either route the presence predicate through the same selected receiver state or leave this consumer explicitly outside the claim for this integration; the current mixin comment describes the direct read but omits the still-native gate.

Evidence: `decompiled_minecraft/1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, `updateAutoJump()`; `decompiled_minecraft/1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `updateAutoJump()`; `decompiled_minecraft/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java`, `updateAutoJump()`.

## Verified areas

- `LegacyJumpBoostEffectReceiver` preserves higher-amplifier replacement, equal-amplifier duration extension, ignores weaker finite updates, and retains duration zero until REMOVE. The no-hidden 1.14/1.15.1 behavior is consistent with the accepted source supplement.
- `HiddenJumpBoostEffectReceiver` carries the 1.15.2 hidden chain, decrements it alongside active duration, and restores it on active expiry. Its active/hidden transition and the jump contribution preserve the integer amplifier and float operation order described by the accepted evidence.
- The old `JumpBoostHiddenFallback` / `JumpBoostFallbackMixin` state and power override are removed, so there is no second writer suppressing the new `getJumpBoostPower()` contribution. The earlier `v1_13/DoubleJumpBoostAddition` raw effect path remains unchanged.
- Hook changes are annotated and discoverable, with the empty `V1_13_1` fence and `V1_15` / `V1_15_2` implementations. Under the closest-applicable resolver, 1.14.x / 1.15–1.15.1 resolve to `V1_15`, 1.15.2 resolves to its hidden-chain implementation, and 1.16+ do not resolve a historical receiver implementation. The receiver mixins are in the client mixin list and restrict state capture/ticking/power substitution to `Minecraft.player`.
- `0.1F * (amplifier + 1)` retains integer addition before float multiplication for the legacy jump-power contribution. `FloatJumpBoostAddition` still composes through `getJumpBoostPower()` at its existing contribution seam.
- Mixin target descriptors used by the packet and auto-jump mixins match the inspected 26.2 decompiled members. Compilation and runtime injection were not checked under this review assignment.

## Source identity

Read-only exact-version sources were taken from `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft`; the specified candidate review supplement was `review-supplement.md` under `.native-source/20261030/jump-effect-candidate-review39/`. No product files were changed.

## Review correction (2026-10-30)

The `[P1]` duration `-1` finding above is **withdrawn**. The receiver audit intentionally routes the modern infinite-duration sentinel through native behavior: infinite duration is a modern-only feature introduced in 1.19.4, outside the historical finite-receiver behavior this change emulates. The requested native lifecycle therefore takes precedence for that sentinel; do not treat it as an old-client finite effect update. The original finding text is retained above for review history, but it is no longer an actionable defect or a blocker.

The `[P2]` auto-jump presence-gate finding remains open pending review of the integrator's adapter commit. No product source was changed by this correction.

## Adapter re-review (2026-10-30, commit `6398a57203da0be6f22c5d70456ae401cf754dac`)

The `Optional<Boolean>` projection is semantically consistent for the concrete receiver states: `Optional.of(false)` suppresses an observed absent finite effect, `Optional.of(true)` preserves an observed legacy effect (including an expired effect retained client-side until REMOVE), and `Optional.empty()` leaves unobserved or native-session presence to vanilla. When the shadow says present but the modern map has no entry, `updateAutoJump()`'s following `getEffect()` can return null; the existing amplifier redirect is safe in that case because it returns the present shadow amplifier without dereferencing its `MobEffectInstance` receiver. The presence and amplifier values are sourced from the same receiver state.

However, the new presence redirect is bound to the wrong current-version invoke and is not buildable as written. At 26.2, the compiled `LocalPlayer.updateAutoJump(FF)V` bytecode uses the method reference `net/minecraft/client/player/LocalPlayer.hasEffect:(Lnet/minecraft/core/Holder;)Z` for its Jump Boost gate. Commit `6398a572` instead targets `LivingEntity.hasEffect(MobEffect)Z` and its handler calls `entity.hasEffect(effect)` with `MobEffect`. `javap` on the specified compiled 26.2 jar confirms that `LivingEntity` exposes `hasEffect(Holder<MobEffect>)`, not `hasEffect(MobEffect)`, and the invoke owner in the compiled LocalPlayer method is `LocalPlayer`. Thus this callback cannot compile against the target API; even after changing its Java types, the current `@Redirect` target owner/descriptor would not match the bytecode call. With the client mixin configuration's `defaultRequire: 1`, an unresolved injection would also fail mixin application. The previous P2 presence-gate finding is therefore not resolved by this commit.

Bounded verdict for `6398a57203da0be6f22c5d70456ae401cf754dac`: **reject pending correction** of the presence callback to the actual compiled invoke owner and descriptor (and handler argument type). This review made no product edits and ran no build or game.

## Final adapter re-review (2026-10-30, commit `4ef33e1958119dbf442257ac12135657a8d1e885`)

The final correction now targets `LocalPlayer.hasEffect(Holder)` and accepts `(LocalPlayer, Holder<MobEffect>)`, matching the exact method reference observed in the 26.2 compiled `LocalPlayer.updateAutoJump(FF)V` constant pool and invocation. `LivingEntity`'s public API confirms the corresponding `hasEffect(Holder<MobEffect>)` / `getEffect(Holder<MobEffect>)` signatures. Its identity/equality filter limits substitution to this player's Jump Boost gate. For an observed receiver state, `Optional.of(false)` prevents the modern map from suppressing historical absence, while `Optional.of(true)` enables the following amplifier redirect; for unobserved state or native sessions, `Optional.empty()` retains native behavior. In the shadow-present/native-absent case, the subsequent native `getEffect` may yield null, but the amplifier redirect consumes the receiver's present amplifier without dereferencing its nullable effect argument. Concrete legacy receivers make their presence and amplifier optionals consistent: a non-native, non-null shadow produces both true and an amplifier; a null shadow produces false and no amplifier.

The auto-jump presence-gate finding is **resolved** by commit `4ef33e1958119dbf442257ac12135657a8d1e885`. Bounded verdict for the final receiver integration plus this correction: **ACCEPT**, subject to the integration build owner’s required compile and with runtime parity still unverified. This review changed no product files and ran no build or game.
