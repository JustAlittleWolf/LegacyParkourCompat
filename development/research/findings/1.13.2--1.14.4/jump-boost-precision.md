# Jump Boost vertical impulse precision

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `getJumpStrength`, lines 1447–1464.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `getJumpStrength`, lines 1752–1772.

Difference: With Jump Boost, 1.13.2 first assigns float `getJumpStrength()` into double `velocityY`, then adds `(amplifier+1)*0.1F` to that double. 1.14.4 adds the base and boost as float into local `f`, then stores that float in the vertical velocity. 1.14.4 therefore rounds the sum to float before storage; 1.13.2 performs the addition at double precision after base assignment. Sprint horizontal impulse uses the same apparent formula and ordering in the inspected bodies.

Movement meaning: LivingEntity jump is reached from living tick jump handling; PlayerEntity overrides call the superclass on both sides.
