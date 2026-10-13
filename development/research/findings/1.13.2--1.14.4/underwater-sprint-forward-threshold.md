# Underwater sprint-forward threshold

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick`, lines 701–740.
1.14.1, Yarn `1.14.1+build.10`: `1.14.1/yarn/net/minecraft/client/network/ClientPlayerEntity.java`, `tickMovement`, prior-forward snapshot and current start checks.
1.14.2, Yarn `1.14.2+build.7`: `1.14.2/yarn/net/minecraft/client/network/ClientPlayerEntity.java`, `tickMovement`, `method_20623`; `client/input/Input.java`, `method_20622`.
1.14.4, Mojmap: `1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, `hasEnoughImpulseToStartSprinting`, lines 960–963.

Difference: 1.13.2, 1.14 and 1.14.1 require movementForward >= 0.8F for sprint-forward eligibility. Starting with stable 1.14.2, eye-submerged eligibility requires movementForward > 1.0E-5F; otherwise the promoted float is compared with the double constant 0.8. The dry comparison has the same boolean result as >=0.8F for every float value. This helper supplies both the prior-forward snapshot before input polling and current-forward checks after polling and item slowdown. Exact 1.14.3 and 1.14.4 retain the new operation.

Movement meaning: A smaller positive submerged forward impulse can pass the newer local sprint predicate. Vanilla keyboard input slowed for crouch or crawl can produce 0.3F, which fails the old threshold but passes the new eye-submerged threshold. Downstream sprint state and trajectory remain conditional on the surrounding gates. The local-player underwater override reads the Player eye-query cache alone, rather than Entity's combined head-and-body predicate. Player updates that cache before its inherited tick. Eye-query production, contextual fluid height, and the separate loaded-eye-chunk guard introduced in 1.14 remain independent operations.

The same scaled-float eligibility operation remains in the following inspected Mojmap releases. Every reference below is to `<version>/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep` for the prior snapshot and `hasEnoughImpulseToStartSprinting` for the helper. Current start checks occur after input polling and item-use scaling, either directly or through `canStartSprinting`.

| Exact versions | Prior snapshot line | Helper lines |
| --- | --- | --- |
| 1.15, 1.15.1, 1.15.2 | 634 | 977–980 |
| 1.16, 1.16.1 | 661 | 1014–1017 |
| 1.16.2, 1.16.3, 1.16.4, 1.16.5 | 636 | 989–992 |
| 1.17 | 660 | 1021–1024 |
| 1.17.1 | 658 | 1019–1022 |
| 1.18, 1.18.1 | 656 | 1033–1036 |
| 1.18.2 | 651 | 1031–1034 |
| 1.19 | 734 | 1115–1118 |
| 1.19.2 | 740 | 1121–1124 |
| 1.19.3 | 661 | 1042–1045 |
| 1.19.4 | 653 | 1036–1039 |
| 1.20.1 | 659 | 1028–1031 |
| 1.20.2, 1.20.4 | 657 | 1027–1030 |
| 1.20.5, 1.20.6 | 659 | 1034–1037 |
| 1.21, 1.21.1 | 656 | 1037–1040 |
| 1.21.2, 1.21.3 | 650 | 1037–1040 |
| 1.21.4 | 655 | 1068–1071 |

The input object retains the float axis after that tick's input scaling. On the next tick, the prior snapshot reads that retained axis, not a raw key boolean. `Input.hasForwardImpulse` (renamed `ClientInput.hasForwardImpulse` by 1.21.2) tests the retained forward float against `1.0E-5F`. Physical keyboard input constructs each axis independently as exactly -1.0F, 0.0F or 1.0F before scaling: opposing keys cancel, and diagonal input does not normalize the forward component in these versions. In 1.15–1.18.1, keyboard slowdown casts a double multiplication by 0.3 back to float; in 1.18.2, each axis instead uses float multiplication by 0.3F; from the inspected 1.19 sources the supplied sneak speed is multiplied as a float. In 1.21.4, keyboard polling no longer applies slowdown; `LocalPlayer.aiStep`, lines 668–677, applies item scaling first and sneak-speed attribute scaling second. These producer changes do not replace the float eligibility operation.

The earlier 1.14 flight producer must also be preserved when reconstructing the retained float. In 1.14 Feather `LocalClientPlayerEntity.mobTick`, lines 626–636 and 729–742; 1.14.1 Yarn `ClientPlayerEntity.tickMovement`, lines 614–624 and 717–730; 1.14.2 and 1.14.3 Yarn `tickMovement`, lines 614–623 and 716–729; and 1.14.4 Mojmap `LocalPlayer.aiStep`, lines 625–634 and 727–740, the order is keyboard axis production, conditional keyboard `(float)(axis * 0.3)` slowdown, conditional item `axis *= 0.2F`, current sprint decisions, flight ability decisions, then conditional `(float)(axis / 0.3)` on the stored input axes. Division requires current `abilities.flying`, the controlled camera, and current sneak input; it has no separate spectator or passenger guard. Item scaling requires item use and no passenger; keyboard slowdown requires no spectator and either current sneak or the pre-poll crouch/crawl predicate. The flight division occurs after current sprint decisions but before the next tick's prior snapshot. The corresponding division is absent in inspected 1.15 and later sources.

The inherited movement fields are separate from stored input. 1.14.4 Mojmap `LocalPlayer.serverAiStep`, lines 600–605, copies input to `xxa`/`zza` for the controlled camera; `LivingEntity.aiStep`, lines 2193–2196 and 2218–2220, calls that producer and then damps the copies by `0.98F`. Equivalent copy/damping paths occur in 1.14 Feather `serverTickAi` and `LivingEntity.mobTick`, lines 2190–2193 and 2215–2217; 1.14.1 Yarn `tickNewAi` and `LivingEntity.tickMovement`, lines 2174–2177 and 2199–2201; 1.14.2 Yarn, lines 2180–2183 and 2205–2207; and 1.14.3 Yarn, lines 2190–2193 and 2215–2217. The damping does not write back into the input object. This remains explicit in 1.21.4 Mojmap `LocalPlayer.serverAiStep`, lines 607–612, and `LivingEntity.aiStep`, lines 2767–2768.

1.21.5, Mojmap: `1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 693–733, `canStartSprinting`, lines 1065–1075, and `modifyInput`, lines 623–639; `client/player/ClientInput.java`, `hasForwardImpulse`, lines 17–19; `client/player/KeyboardInput.java`, `tick`, lines 23–36. The older helper is removed. Both prior snapshot and current start eligibility now call `ClientInput.hasForwardImpulse`, which tests the raw normalized movement vector's forward component against `1.0E-5F`, without an underwater-dependent 0.8 branch. The keyboard still constructs the axes from opposing physical key pairs, then normalizes the vector; downstream 0.98, item and sneak scaling applies to a derived vector, not the input vector read by eligibility. This is not a direct test of the forward-key boolean: forward and backward together produce zero forward input even if the forward key is down. Forward diagonal input has a positive normalized component and passes the new predicate on land as well as underwater.

26.2, published unobfuscated source: `26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 777–822, `canStartSprinting`, lines 1141–1148, and `modifyInput`, lines 707–723; `client/player/ClientInput.java`, `hasForwardImpulse`, lines 17–19; `client/player/KeyboardInput.java`, `tick`. The raw normalized-vector eligibility operation remains. This establishes the concrete 1.21.4-to-1.21.5 replacement and its presence in 26.2; intervening releases after 1.21.5 have not been compared here.
