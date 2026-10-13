# Knockback response normalization

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `applyKnockback`, lines 1011–1027.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `applyKnockback`, lines 1208–1215.

Difference: Both versions first apply the same random knockback-resistance guard and set `velocityDirty`. 1.13.2 computes a float horizontal magnitude with `MathHelper.sqrt(x*x + z*z)`, halves current X/Z velocity, then subtracts each incoming component divided by that magnitude times `amount`. There is no zero-length check. 1.14.4 constructs `(x,0,z)`, calls `Vec3d.normalize()` (which returns ZERO when its length is below `1.0E-4`), scales by `amount`, and subtracts it from half the current horizontal velocity. For a zero or sufficiently small input vector, 1.14.4's direction term is zero while 1.13.2's division can produce non-finite components. 1.13.2's grounded Y path halves then adds `amount` and caps above `0.4F`; 1.14.4 evaluates `Math.min(0.4, oldY/2 + amount)` using the double literal.

Movement meaning: This is the direct response method inherited by players. 1.13.2 call reaches it only if the resistance random check passes.
