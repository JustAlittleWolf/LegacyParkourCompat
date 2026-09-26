# F-6: Powder-snow climb boost reads current state in 1.20.6 and prior-tick state in 1.21.11

- Older version A: 1.20.6.
- Newer version B: 1.21.11.
- Mechanic / coverage slice IDs: stage 3, slice 3.2 (travel and climb movement).
- Classification: changed behavior.
- Confidence: source-confirmed for the condition; consequence is conditional on player powder-snow state and collision/jump timing.
- Applicability: living local player whose movement reaches the post-move branch with horizontal collision or `jumping`, and whose powder-snow walkability/current state differs from `wasInPowderSnow`.
- First changed release: unknown within (1.20.6, 1.21.11].
- Runtime validation: not performed.

## Paired evidence

- A source: `decompiled_minecraft/1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, `handleRelativeFrictionAndCalculateMovement(Vec3,float)` lines 2268–2282; SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`. After `move()`, when `(horizontalCollision || jumping)`, the vertical component is set to `0.2` if on a climbable or `getInBlockState().is(POWDER_SNOW) && canEntityWalkOnPowderSnow(this)`.
- B source: `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`, `handleRelativeFrictionAndCalculateMovement(Vec3,float)` lines 2533–2544; SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`. The corresponding condition uses `wasInPowderSnow && canEntityWalkOnPowderSnow(this)` instead of reading the current in-block state.
- A/B `Entity.java` SHA-256: `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` / `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`. Both roll `isInPowderSnow` into `wasInPowderSnow` at tick start and reset `isInPowderSnow`; both `getInBlockState()` methods lazily read the state at current `blockPosition()`.
- A/B `PowderSnowBlock.java` SHA-256: `7a6d6ad2e6d6d719344de4bb4f4e4973387aa88e61757988afea25b46c176ad3` / `84e3f4c9063c6be560b400686afa2bcd6761585c9c56566d023ad8fe47048940`. Both `canEntityWalkOnPowderSnow()` permit the same tagged mobs or living entities wearing leather boots.

## Source-level difference

The post-move vertical boost gate changed from the current block-state query in A to the prior-tick occupancy flag in B. The shared tick-start rollover and unchanged walkability helper show these are distinct temporal inputs. When the outer collision/jump gate and walkability gate pass but the current-state and prior-tick predicates disagree, A may return vertical velocity `0.2` while B returns the preexisting vertical component (unless the climbable branch applies).

## Reachability and uncertainty

LocalPlayer inherits this shared LivingEntity movement method. A player can meet the walkability predicate by wearing leather boots; the source comparison does not establish a concrete ordinary world/tick sequence where current and prior-tick state diverge at this call. Whether this produces a trajectory difference depends on powder-snow occupancy updates, movement timing, and the resulting collision/jump state. No such scenario was executed or runtime-tested.

## Handoff

Independent source delta: post-move powder-snow vertical-boost condition uses current block state in A and prior-tick occupancy in B. Do not generalize beyond the stated predicate divergence without tracing the exact occupancy sequence.
