# Underwater sprint-forward threshold

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick`, lines 701–740.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `m_03985577`, lines 961–964.

Difference: 1.13.2 requires movementForward >= 0.8F for sprint-forward eligibility. 1.14.4 requires movementForward > 1.0E-5F when submerged and retains >=0.8F otherwise.

Movement meaning: A smaller positive submerged forward impulse can pass the newer local sprint predicate; downstream sprint state and trajectory remain conditional on the surrounding gates.
