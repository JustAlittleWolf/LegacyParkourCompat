# Water sprint start predicates

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 649-817.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 653-842.

Difference: 1.12.2 double-tap requires onGround and its held-key gate has no water predicate. 1.13.2 double-tap permits onGround or eye-submerged water; held-key start additionally requires dry or eye-submerged state. Other input/food/item/blindness gates and input sampling order remain as recorded.

Movement meaning: The same eligible forward/sprint input can start sprint in different water/support states. This assertion retains the independently accepted endpoint start-gate comparison.
