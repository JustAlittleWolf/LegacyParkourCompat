# client-side living pushes can write local-player velocity

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `protected void pushAwayCollidingEntities() {`, lines 1463-1476.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `protected void pushAwayCollidingEntities() {`, lines 1742-1750.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/EntityFilter.java`, `public static <T extends Entity> Predicate<T> canBePushedBy(Entity entity) {`, lines 56-94.

Difference: 1.8.9 runs the living entity post-travel push query only on the server. 1.9.4 runs it on client and server. 1.9.4's client filter rejects ordinary remote/non-player candidates but can select the locally controlled player from another living entity's query. `pushAway` forwards as `candidate.push(this)`.

The `Entity#push` body applies opposite horizontal velocity deltas to both participants, subject to same-vehicle/no-clip/passenger and minimum-distance guards.

Therefore, when a client-side living entity's query includes the local player, 1.9.4 has a reachable client-side write to local-player horizontal velocity; 1.8.9 has no corresponding client-side call.

The query also changes from an X/Z-expanded current box (`grown(0.2F, 0.0, 0.2)`) with `NOT_SPECTATOR && isPushable()` to the current box with `EntityFilter.canBePushedBy(this)`.

Those filters are not equivalent; the source establishes the changed query and filter boundaries, not which candidates occur in any particular world state.

Movement meaning: For this route, a non-local living entity is the query owner, while the local player is the selected candidate.

Required guards include overlap with that exact query box, local player pushability/non-spectator status, allowed team collision rules, not sharing a vehicle, neither participant no-clip, the relevant passenger checks, and horizontal separation reaching the method's `0.01F` cutoff.

The local player's own client query is not the path: its filter does not admit ordinary remote entities as candidates.

This distinction closes the producer-to-player-writer direction without attributing independent entity movement as an endpoint.

The paired source proves an additional client-side route that can alter local-player horizontal velocity after the local player's movement, when the listed collision and filter preconditions hold.

It does not establish a particular trajectory or compare server-authoritative correction outcomes.
