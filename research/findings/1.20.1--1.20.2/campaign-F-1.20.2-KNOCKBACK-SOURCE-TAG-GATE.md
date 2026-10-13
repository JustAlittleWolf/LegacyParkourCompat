# F-1.20.2-KNOCKBACK-SOURCE-TAG-GATE: direct player knockback gating changes between endpoints

- **1.20.1**, mojmap: `1.20.1/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 1158-1166.
- **1.20.2**, mojmap: `1.20.2/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 1183-1191.

## Difference

For the same accepted-hit and non-null-attacker preconditions, 1.20.1's generic LivingEntity#hurt path excludes sources carrying IS_EXPLOSION from this call to knockback; 1.20.2 replaces that predicate with NO_KNOCKBACK.

The velocity-writing body itself is unchanged.

Thus the source-confirmed difference is which tagged source categories reach the direct knockback writer, not knockback arithmetic or hit/damage production.

Player inherits the cited LivingEntity method on the server-side class hierarchy.

The client motion handler can apply an addressed entity velocity through the common lerpMotion path. 1.20.1 separate ClientPacketListener#handleExplosion adds the explosion packet's vector to local-player delta movement on both endpoints; that independent packet path is not attributed to this changed hurt guard.

The finding therefore claims only the packaged vanilla default case and does not establish a final client velocity or trajectory.

## Movement meaning

Source-proven: on the exact packaged vanilla server defaults, a fireworks source with a non-null causing entity reaches the shared direct delta-movement/impulse writer for a Player-derived LivingEntity in 1.20.2 and is filtered before that writer in 1.20.1.

The identical writer performs the same knockback math when reached.

Health, damage amounts, attack production and non-player motion are not claimed.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.20.1--1.20.2/findings/F-1.20.2-KNOCKBACK-SOURCE-TAG-GATE.md).
