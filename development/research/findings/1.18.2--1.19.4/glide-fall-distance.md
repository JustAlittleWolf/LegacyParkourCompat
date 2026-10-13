# Glide updates slow fall distance only when above one block

1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, checkSlowFallDistance 2167-2170.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel 2074-2079.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel 2048-2051.

Difference: Under the shared glide-branch condition `vertical delta > -0.5`, 1.18.2 always overwrites accumulated `fallDistance` with `1.0F`, including when it was `0.0F` or between zero and one. 1.19.4 sets it to `1.0F` only if the accumulated value was already greater than `1.0F`; values at or below one remain unchanged. The delta is the extra `fallDistance > 1.0F` guard in 1.19.4's helper. Values greater than one are still reduced to one in 1.19.4.

Movement meaning: 1.18.2 local player can reach `LivingEntity.travel()` while fall-flying after the player equips/activates Elytra; the glide branch updates this state before its movement math. The helper is inherited from `Entity` in 1.19.4. `fallDistance` persists as player state and feeds later fall handling, but damage application is server-side. For a gliding player whose vertical delta is above `-0.5`, 1.19.4 preserves a smaller fall distance that 1.18.2 would raise to `1.0F`; for an accumulated value above one, both leave one. This can change later fall-distance-dependent handling after leaving glide.
