# Water sprint start predicates

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 649-817.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 653-842.

Difference: 1.12.2 double-tap requires onGround and its held-key gate has no water predicate. 1.13.2 double-tap permits onGround or eye-submerged water; held-key start additionally requires dry or eye-submerged state. Other input/food/item/blindness gates and input sampling order remain as recorded.

Movement meaning: The same eligible forward/sprint input can start sprint in different water/support states. This assertion retains the independently accepted endpoint start-gate comparison.

Exact stable boundary evidence: 1.13 Feather `LocalClientPlayerEntity.mobTick` already contains `onGround || isSubmergedInWater()` for double-tap support and `!isTouchingWater() || isSubmergedInWater()` for held-key start; exact 1.13.1 and 1.13.2 retain those predicates. The local override returns the Player eye-query cache. This changes an existing sprint predicate: 1.12.2 already supports double-tap and held-key sprint, including held-key start in water. Swimming's separate introduction does not make all earlier water sprint decisions native fallbacks.
