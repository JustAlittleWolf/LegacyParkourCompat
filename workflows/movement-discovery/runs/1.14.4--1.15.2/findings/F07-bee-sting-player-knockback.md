# F07: Bee sting knockback changes player velocity

- Older version A: 1.14.4
- Newer version B: 1.15.2
- Mechanic / coverage slice IDs: stages 7; external entity effect on player
- Classification: added interaction
- Confidence: source-confirmed
- Applicability: player struck by a Bee sting when damage is accepted and knockback resistance does not cancel it
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A has no `Bee.java` and no registered Bee entity type (`EntityType.java` SHA-256 `675B46B1B2680C222E9B160F2FF3610DE40B4609A80124EED0A573F2502EA813`). B registers `EntityType.BEE` (`EntityType.java` SHA-256 `9A3AC5B01EBF9CB783E5B7073AE227EE2F58DD96437936F6AA21134D72A8D818`) and `Bee.doHurtTarget` applies sting damage (lines 200–215; `Bee.java` SHA-256 `7F6E404B815E2F8372EA7E6CC702A8B2AB8BD92FDE5751A504DC104599DE3170`).
- B `DamageSource.sting` creates an entity damage source attributed to the Bee (`DamageSource.java` SHA-256 `0A49B3A2FCB9413A31E4328157DA91C75821E4F2B4CC445D282F973E6B3A6ACA`). `LivingEntity.hurt` applies knockback for entity-attributed damage at lines 929–938; `LivingEntity.knockback` changes horizontal delta and, when grounded, vertical delta at lines 1195–1201 (`LivingEntity.java` SHA-256 `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54`).
- A's `LivingEntity` also contains the shared entity-damage knockback route and knockback implementation (A `LivingEntity.java` SHA-256 `428762178A876EFD4069086E6B7D51F571EA0401F44E7EFF0927B7D26D9EF681`); the added difference is the new Bee sting source, not a general knockback rewrite.

## Source-level difference

When a B Bee sting successfully damages a player, the common living-entity damage route can add knockback to that player's velocity. The Bee's own AI and movement are outside scope; this finding records only the new player-affecting interaction.

## Reachability and boundary

Bee attack -> sting damage source attributed to Bee -> player `LivingEntity.hurt` -> shared `knockback` velocity mutation. This is an external entity effect, with server-authoritative state replication; it is not locally computed player travel.

## Consequence and uncertainty

The effect is conditional on attack and damage acceptance and may be reduced/canceled by knockback resistance. No gameplay test was performed. Bee behavior is modern-only because A has no Bee entity.
