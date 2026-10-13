# knockback-degenerate-vector-jitter: 1.21.1 randomizes a zero horizontal knockback vector

- Older version A: 1.20.6
- Newer version B: 1.21.1
- Mechanic / coverage slice IDs: X2
- Classification: changed behavior
- Confidence: source-confirmed for a reachable player knockback call with a zero planar direction
- Applicability: historical player behavior; also applies to other LivingEntity recipients
- First changed release: unknown within (1.20.6, 1.21.1]
- Runtime validation: not performed

## Paired evidence

- A: ready/1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java, LivingEntity#knockback(double,double,double), lines 1475-1483, SHA-256 c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2. After the positive post-resistance strength guard, it normalizes the supplied X/Z vector and scales by strength; an exact zero vector remains zero. It then halves existing X/Z velocity and writes the unchanged Y velocity unless grounded, when Y is raised by the capped knockback amount. It sets hasImpulse.
- B: ready/1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java, LivingEntity#knockback(double,double,double), lines 1441-1455, SHA-256 324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8. After the same strength guard and reading current velocity, it repeatedly replaces X/Z with independent (Math.random()-Math.random())*0.01 values while their squared length is below 1.0E-5F. It then applies the same normalization, scaling, velocity formula, and hasImpulse write.
- Reachable player call path: LivingEntity#hurt calls blockUsingShield for a blocked, non-projectile hit from a direct LivingEntity (A lines 1139-1145; B lines 1104-1110). blockUsingShield delegates to the attacker’s blockedByShield method (A lines 1260-1265; B lines 1229-1234), which calls that attacker’s knockback with the X/Z difference between attacker and blocker. Player is a LivingEntity, so when their planar coordinates coincide and the attacker is a ServerPlayer with positive post-resistance knockback strength, this supplies the exact zero vector to the changed method. The A/B LivingEntity source hashes above cover these callsites; the corresponding Player class hashes are A 785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2 and B ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71.
- State writer -> consumer: on that precondition, A writes half of the prior horizontal velocity with no added horizontal knockback; B writes half of the prior horizontal velocity minus a strength-scaled random unit horizontal vector. Both set hasImpulse, allowing the server entity tracking path to publish the changed player movement. No gameplay trace is claimed.

## Source-level difference

The changed guard is inside the common LivingEntity knockback method and applies to direct callers, not only the ordinary damage path. For a reachable shield callback whose attacker and blocker have equal X/Z coordinates, A normalizes (0,0) to the zero vector. B instead draws random tiny components until their squared length is at least 1.0E-5F, normalizes that direction, and subtracts the resulting strength-scaled vector from the attacker’s horizontal velocity. The player velocity write therefore differs under this exact input.

## Reachability and dependencies

The positive-strength guard must pass after knockback resistance. A player can be the direct LivingEntity attacker in a blocked non-projectile hit; the shield callback computes knockback direction from the two entities’ X/Z positions. Equal planar coordinates make the input exactly zero. The common knockback method then writes the velocity and sets hasImpulse. The source establishes the preconditions and writes; it does not claim a particular random sample or position trace.

## Consequence and uncertainty

For the stated reachable player precondition, A adds no horizontal knockback impulse from this callback, while B adds a random horizontal impulse with the configured knockback strength. The first release containing the method change is unknown within the endpoint interval. No runtime validation was performed.

## Handoff

Independent review should verify the exact zero-vector call path, the post-resistance guard, and the horizontal velocity formula. Snapshot review is pending; pair discovery remains active and partial.