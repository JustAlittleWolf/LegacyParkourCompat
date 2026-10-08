# F14: 1.15.2 resumes hidden movement-affecting effects

- Older version A: 1.14.4
- Newer version B: 1.15.2
- Mechanic / coverage slice IDs: stages 2, 3 and 6; effect stacking and movement-state readers
- Classification: changed effect stacking behavior
- Confidence: source-confirmed
- Applicability: a stronger, shorter Jump Boost, Slow Falling, Levitation, Dolphin's Grace or Blindness instance overlays a weaker instance of the same effect with enough duration to remain after the stronger instance expires
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A `MobEffectInstance.update` replaces the active effect with a stronger instance and does not retain the displaced one. A has no hidden-effect field; `tick(LivingEntity)` decrements only the active duration. A source hash: `80CC2A91B77D4687A9B36BCB38E3107E3B2B8F859C69893E4EEC0E98B4941B5E`.
- B `MobEffectInstance.update` stores a displaced stronger-or-weaker instance in a hidden chain when its duration outlasts the active instance. `tick(LivingEntity, Runnable)` decrements both active and hidden durations, restores hidden details when the active duration reaches zero, and invokes the callback. B source hash: `4999D12CD468E402506F067DBD8694B1C8125DD9131E4F8A2FCC1848F616F180`.
- `LivingEntity.tickEffects` supplies `onEffectUpdated(instance, true)` as B's expiry callback. This restores any effect attribute modifiers on the server; the active instance is also replaced on the client. Both versions route initial effect insertion and update through `LivingEntity.addEffect`. A/B `LivingEntity.java` hashes are `428762178A876EFD4069086E6B7D51F571EA0401F44E7EFF0927B7D26D9EF681` and `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54`.
- The paired `MobEffects` registrations are byte-identical (SHA-256 `472B25EDE6BED3AD3ED1090DB3310CEA9708B637F111981BCF083F492C030412`). The movement readers are paired: Jump Boost is read by `LivingEntity.jumpFromGround`; Slow Falling and Levitation by `LivingEntity.travel`; Dolphin's Grace by the water-travel branch; and Blindness by the local sprint-start gate. The Speed and Slowness attribute case is recorded separately in F08.

## Source-level difference

If the stronger instance expires while the weaker instance still has duration, B restores the weaker amplifier and makes that movement effect active again. A discarded the weaker instance when the stronger one replaced it, so it removes the effect at expiry. The resumed state can change jump height, fall gravity, levitation velocity, water drag, or whether the local sprint-start gate accepts a sprint attempt, depending on which effect is stacked.

## Reachability and dependencies

Effect insertion or renewal -> `MobEffectInstance.update` -> active effect tick/expiry -> B hidden-effect restoration -> the existing player movement reader. F08 separately records Speed/Slowness restoration and movement-speed attribute refresh. This finding does not claim that the effect source or producer changed.

## Consequence and uncertainty

The generic effect stack and the movement readers are source-confirmed in both endpoint releases. No gameplay result was observed. The first release containing hidden-effect restoration is unknown within the endpoint interval.

## Handoff

The movement formulas that consume these effects are otherwise paired in Stage 3 and the Stage 2 sprint correspondence. F14 records their changed active-state lifetime; remaining effect and equipment producers are still under audit.
