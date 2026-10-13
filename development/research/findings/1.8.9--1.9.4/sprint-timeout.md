# local sprint timeout removed

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 488-637.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 604-760.

Difference: In 1.8.9, entering sprint sets `sprintTimer` to 600 and local `mobTick` decrements it; when it reaches zero, that method calls `setSprinting(false)`.

In 1.9.4, `setSprinting` sets the timer to zero and `mobTick` increments it. 1.8.9 source-wide reference search found no 1.9.4 read of the incremented field or timeout consumer.

Therefore an otherwise valid sprint can remain active beyond 600 ticks in 1.9.4; 1.8.9 forcibly ends it at that point.

For corresponding local-player states with no newer off-hand-use capability, the normal sprint-initiation and stop gates have the same conditions in both versions: on-ground double-tap or held sprint key, forward input, collision, food-or-flight permission, main-hand item-use and blindness. 1.9.4's off-hand item use has no 1.8.9 counterpart and is outside this historical comparison. 1.8.9 timeout-driven `setSprinting(false)` is sent through the same client action packet and applied by the same server START/STOP handler in each endpoint.

Health/food producers and combat are excluded.

Movement meaning: The local player's `mobTick` runs before `LivingEntity.mobTick` travel. `setSprinting` also updates the state consumed by sprint movement speed and sprint-jump paths.

The 600-tick stop occurs in 1.8.9 before the next travel call; 1.9.4 has no corresponding timer read.

The source manifest's `LocalClientPlayerEntity.java` hash covers the entire cited body.

The source proves only the extra persistence of the sprint flag beyond 1.8.9's 600-tick deadline, provided no normal sprint gate clears it first.

When the timeout is the clearing condition, 1.8.9's local state change reaches the same STOP_SPRINTING packet consumer that 1.9.4 uses for other stop conditions; 1.9.4 emits no timeout stop.

It does not establish a measured trajectory.
