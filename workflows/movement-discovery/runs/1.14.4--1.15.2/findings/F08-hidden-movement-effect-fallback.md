# F08: 1.15.2 restores a hidden weaker movement effect

- Older version A: 1.14.4
- Newer version B: 1.15.2
- Mechanic / coverage slice IDs: stages 3 and 6; movement-speed effect lifecycle
- Classification: changed effect stacking behavior
- Confidence: source-confirmed
- Applicability: a stronger, shorter Speed or Slowness effect overlays a weaker, longer instance of the same effect
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A `MobEffectInstance.update` replaces the active amplifier and duration when a stronger instance arrives, without retaining the displaced instance (lines 54–101). A `tick(LivingEntity)` only applies the active effect and decrements its duration (lines 111–120); it has no hidden-effect field. A `MobEffectInstance.java` SHA-256: `80CC2A91B77D4687A9B36BCB38E3107E3B2B8F859C69893E4EEC0E98B4941B5E`.
- B `MobEffectInstance.update` retains a weaker longer instance as `hiddenEffect` when the stronger instance has less duration (lines 66–102). `tick(LivingEntity, Runnable)` decrements active and hidden durations, restores hidden details when the active duration reaches zero, and runs the callback to refresh attribute modifiers (lines 135–147). B `MobEffectInstance.java` SHA-256: `4999D12CD468E402506F067DBD8694B1C8125DD9131E4F8A2FCC1848F616F180`.
- A `LivingEntity.tickEffects` invokes `MobEffectInstance.tick` and removes the effect at expiry (lines 605–618). B supplies `onEffectUpdated(..., true)` as the tick callback and then follows the same expiry/removal route (lines 573–587). In both versions, `onEffectUpdated` removes and reapplies effect attribute modifiers on the server (A lines 816–823; B lines 784–791). A `LivingEntity.java` SHA-256: `428762178A876EFD4069086E6B7D51F571EA0401F44E7EFF0927B7D26D9EF681`; B: `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54`.
- `MobEffects.SPEED` and `MobEffects.MOVEMENT_SLOWDOWN` attach the same movement-speed modifiers in both versions: `0.2F` and `-0.15F`, respectively, with `MULTIPLY_TOTAL`. `MobEffects.java` is byte-identical (SHA-256 `472B25EDE6BED3AD3ED1090DB3310CEA9708B637F111981BCF083F492C030412`). `MobEffect.addAttributeModifiers` computes the modifier amount from the active amplifier and is byte-identical in both versions (`MobEffect.java` SHA-256 `F218A040FF9F77272395245C4FFECC3F2266335600B79BE0A5B2823C46AD7DD0`).
- `LivingEntity.addEffect` is reachable for local players and routes successful updates through `onEffectUpdated`; player movement reads the resulting movement-speed attribute through `Player.getSpeed` and the player `aiStep` speed field. Player source hashes are recorded in [Stage 6 modifier correspondence](../run.md#stage-6-modifier-correspondence).

## Source-level difference

If a shorter, stronger Speed or Slowness instance replaces a weaker instance with more remaining duration, B keeps the weaker instance and counts its remaining duration down behind the active one. When the stronger instance expires, B restores the weaker amplifier and refreshes the movement-speed attribute modifier immediately. A discards the weaker instance, so expiry removes the movement-speed modifier entirely. This changes the player's movement speed after the stronger effect ends.

## Reachability and boundary

Effect application or renewal -> `MobEffectInstance.update` -> `LivingEntity.addEffect` -> active effect tick -> modifier update or removal -> player movement-speed attribute. The finding is limited to Speed and Slowness attribute effects and their stacking lifecycle. It does not claim that an effect source or producer changed.

## Consequence and uncertainty

The source difference is explicit and player-reachable. The first release containing hidden-effect restoration is unknown within the endpoint interval. No gameplay test was performed.
