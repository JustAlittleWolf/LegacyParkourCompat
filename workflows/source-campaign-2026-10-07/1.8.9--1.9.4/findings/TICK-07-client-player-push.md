# TICK-07: client-side living pushes can write local-player velocity

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: post-travel living entity push; `TICK-07`
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: a client-side living entity's post-travel query overlaps the local player and its push/filter preconditions pass
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A: source manifest A; `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; `LivingEntity#mobTick()V`, lines 1450-1455, calls push only when `!world.isClient`; `#pushAwayCollidingEntities()V`, lines 1463-1475. SHA-256 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`.
- B: source manifest B; `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; `LivingEntity#mobTick()V`, lines 1711-1715, calls push without a client guard; `#pushAwayCollidingEntities()V`, lines 1742-1754. SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`.
- B filter: `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/entity/EntityFilter.java`; `EntityFilter#canBePushedBy(Entity)`, lines 56-85, permits a client-side candidate only when it is a locally controlled `PlayerEntity`, subject to pushability, spectator, and team-collision rules. SHA-256 `91158a5477935c911178433e1b2628d1d063604a23b08baee4706dd286e7e813`.
- Reciprocal writer: A `Entity#push(Entity)V`, lines 947-973, SHA-256 `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`; B same method, lines 1061-1088, SHA-256 `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`.

## Source-level difference

A runs the living entity post-travel push query only on the server. B runs it on client and server. B's client filter rejects ordinary remote/non-player candidates but can select the locally controlled player from another living entity's query. `pushAway` forwards as `candidate.push(this)`. The `Entity#push` body applies opposite horizontal velocity deltas to both participants, subject to same-vehicle/no-clip/passenger and minimum-distance guards. Therefore, when a client-side living entity's query includes the local player, B has a reachable client-side write to local-player horizontal velocity; A has no corresponding client-side call.

The query also changes from an X/Z-expanded current box (`grown(0.2F, 0.0, 0.2)`) with `NOT_SPECTATOR && isPushable()` to the current box with `EntityFilter.canBePushedBy(this)`. Those filters are not equivalent; the source establishes the changed query and filter boundaries, not which candidates occur in any particular world state.

## Reachability and dependencies

For this route, a non-local living entity is the query owner, while the local player is the selected candidate. Required guards include overlap with that exact query box, local player pushability/non-spectator status, allowed team collision rules, not sharing a vehicle, neither participant no-clip, the relevant passenger checks, and horizontal separation reaching the method's `0.01F` cutoff. The local player's own client query is not the path: its filter does not admit ordinary remote entities as candidates. This distinction closes the producer-to-player-writer direction without attributing independent entity movement as an endpoint.

## Consequence and uncertainty

The paired source proves an additional client-side route that can alter local-player horizontal velocity after the local player's movement, when the listed collision and filter preconditions hold. It does not establish a particular trajectory or compare server-authoritative correction outcomes. Exact first changed release is unknown inside the interval.

## Handoff

Independent delta: B enables client-side living push queries that can write the local player's horizontal velocity. Runtime validation is deferred.
