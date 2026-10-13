# MOVE-003: Safe dismount position queries use a changed collision box

- **1.11.2**, ornithe-feather: `1.11.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; lines 1282-1350.
- **1.12.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.11.2--1.12.2/findings/MOVE-003-server-dismount-position.md); lines 1321-1387.

## Difference

When a living player dismounts server-side from a mount that is neither a boat nor a `HorseBaseEntity`, and the mount has a movement direction, the candidate collision test uses a different box and vertical offset in 1.12.2.

If a candidate's collision result differs between the two query volumes, the loop may select a different safe exit or fallback location, and `teleport()` writes that coordinate.

The candidate ordering and final teleport helper are unchanged.

## Movement meaning

The `PlayerEntity.stopRiding()` override delegates to the `LivingEntity.stopRiding()` path.

That path only invokes the safe-exit method on the server.

The client-side local-player call does not execute this server-only placement branch; the resulting server position is authoritative external movement state.

This finding therefore describes player position during a server-authoritative riding transition, not a local movement acceleration change.

Source-proven: 1.11.2 and 1.12.2 query different collision boxes/offsets before choosing a safe dismount location.

Predicted consequence: in world geometry where those queries disagree, the authoritative player position after dismount can differ.

The exact exit position for a concrete map was not evaluated, and no client/server run was performed.

Other mount types in the excluded boat/horse branch are unaffected by this particular query change.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.11.2--1.12.2/findings/MOVE-003-server-dismount-position.md).
