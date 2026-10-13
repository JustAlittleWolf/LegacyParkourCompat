# F07: Bee sting knockback changes player velocity

- **1.14.4**, mojmap: `Bee.java`; lines 200–215.
- **1.15.2**, mojmap: `DamageSource.java`; lines 929–938.

## Difference

When a 1.15.2 Bee sting successfully damages a player, the common living-entity damage route can add knockback to that player's velocity.

The Bee's own AI and movement are outside scope; this finding records only the new player-affecting interaction.

## Movement meaning

Bee attack -> sting damage source attributed to Bee -> player `LivingEntity.hurt` -> shared `knockback` velocity mutation.

This is an external entity effect, with server-authoritative state replication; it is not locally computed player travel.

The effect is conditional on attack and damage acceptance and may be reduced/canceled by knockback resistance.

Bee behavior is modern-only because 1.14.4 has no Bee entity.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F07-bee-sting-player-knockback.md).
