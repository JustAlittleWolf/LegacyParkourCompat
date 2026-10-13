# knockback-degenerate-vector-jitter: 1.21.1 randomizes a zero horizontal knockback vector

1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, knockback 1475-1483; hurt 1139-1145.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, knockback 1441-1455; hurt 1139-1145.

Difference: The changed guard is inside the common LivingEntity knockback method and applies to direct callers, not only the ordinary damage path. For a reachable shield callback whose attacker and blocker have equal X/Z coordinates, 1.20.6 normalizes (0,0) to the zero vector. 1.21.1 instead draws random tiny components until their squared length is at least 1.0E-5F, normalizes that direction, and subtracts the resulting strength-scaled vector from the attacker’s horizontal velocity. The player velocity write therefore differs under this exact input.

Movement meaning: The positive-strength guard must pass after knockback resistance. 1.20.6 player can be the direct LivingEntity attacker in a blocked non-projectile hit; the shield callback computes knockback direction from the two entities’ X/Z positions. Equal planar coordinates make the input exactly zero. The common knockback method then writes the velocity and sets hasImpulse. For the stated reachable player precondition, 1.20.6 adds no horizontal knockback impulse from this callback, while 1.21.1 adds a random horizontal impulse with the configured knockback strength.
