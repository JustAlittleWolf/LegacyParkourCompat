# Movement-speed attribute gains a 1024 cap

1.8.4, Ornithe Feather Gen2 build.2: `1.8.4/ornithe-feather/net/minecraft/entity/living/attribute/EntityAttributes.java`, `MOVEMENT_SPEED` line 18; `ModifiableEntityAttributeInstance.computeValue` lines 128–155; `RangedEntityAttribute.clamp` lines 36–39; `PlayerEntity.getSpeed` lines 1298–1300.
1.8.5, Ornithe Feather Gen2 build.2: corresponding `EntityAttributes.MOVEMENT_SPEED` declaration and attribute calculation members.

Difference: 1.8.4 permits a computed movement-speed attribute up to `Double.MAX_VALUE`; 1.8.5 bounds it to 1024. Both apply operation-0 additions, operation-1 additions relative to that intermediate, operation-2 multipliers, then the configured clamp and player float conversion. Exact mapped 1.8.2–1.8.4 sources and direct pinned Mojang 1.8/1.8.1 bytecode establish the uncapped earlier interval. Exact 1.8.5–1.8.8 checks establish persistence of the cap.

Movement meaning: grounded land acceleration and Depth Strider water acceleration read the player movement-speed attribute. Values above 1024 can therefore change those responses. Ordinary airborne land movement instead reads its separate airborne speed. The calculation alone does not establish a concrete map trajectory or runtime parity.
