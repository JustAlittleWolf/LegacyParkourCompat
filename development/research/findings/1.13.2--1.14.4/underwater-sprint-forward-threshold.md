# Underwater sprint-forward threshold

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick`, lines 701–740.
1.14.1, Yarn `1.14.1+build.10`: `1.14.1/yarn/net/minecraft/client/network/ClientPlayerEntity.java`, `tickMovement`, prior-forward snapshot and current start checks.
1.14.2, Yarn `1.14.2+build.7`: `1.14.2/yarn/net/minecraft/client/network/ClientPlayerEntity.java`, `tickMovement`, `method_20623`; `client/input/Input.java`, `method_20622`.
1.14.4, Mojmap: `1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, `hasEnoughImpulseToStartSprinting`, lines 960–963.

Difference: 1.13.2, 1.14 and 1.14.1 require movementForward >= 0.8F for sprint-forward eligibility. Starting with stable 1.14.2, eye-submerged eligibility requires movementForward > 1.0E-5F; otherwise the promoted float is compared with the double constant 0.8. The dry comparison has the same boolean result as >=0.8F for every float value. This helper supplies both the prior-forward snapshot before input polling and current-forward checks after polling and item slowdown. Exact 1.14.3 and 1.14.4 retain the new operation.

Movement meaning: A smaller positive submerged forward impulse can pass the newer local sprint predicate. Vanilla keyboard input slowed for crouch or crawl can produce 0.3F, which fails the old threshold but passes the new eye-submerged threshold. Downstream sprint state and trajectory remain conditional on the surrounding gates. The local-player underwater override reads the Player eye-query cache alone, rather than Entity's combined head-and-body predicate. Player updates that cache before its inherited tick. Eye-query production, contextual fluid height, and the separate loaded-eye-chunk guard introduced in 1.14 remain independent operations.
