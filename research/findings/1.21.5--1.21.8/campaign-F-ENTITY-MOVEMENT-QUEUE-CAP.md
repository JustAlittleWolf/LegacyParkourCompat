# Candidate: bounded player movement history can change block-contact path

- **1.21.5**, mojmap: `Entity#move(MoverType, Vec3)`; lines 667-680.
- **1.21.8**, mojmap: `Entity#move(MoverType, Vec3)`; lines 694-698.

## Difference

- Exact precondition: at least 101 accepted non-passenger player movement packets invoke `Entity.move` before that player's block-effect drain, and the first two queued samples' axis-ordered paths differ from the straight segment joining the first sample's start to the second sample's end.

If that path difference intersects a non-air block or fluid shape whose inside callback changes player movement state, the callback set/order may differ.

The source proves a native packet path to the threshold, but does not establish that ordinary client ticks generate that packet burst.
- Analysis: absent the cap, the 1.21.8 replay reconstructs each individual movement's axis order, matching 1.21.5's recorded sequence.

At the threshold 1.21.8 collapses history, so its later block/fluid contact scan can omit or add crossed candidates relative to 1.21.5.

This is a server-authoritative player-contact path difference, not a claim that the client's collision displacement, knockback calculation, combat damage, or vehicle physics changed.

Marked candidate until the complete 1.21.5/1.21.8 callback-provider inventory and blind source review establish impact and disposition.

## Movement meaning

- Player reachability: 1.21.5/1.21.8 `ServerGamePacketListenerImpl#handleMovePlayer` calls `player.move(MoverType.PLAYER, ...)` for accepted, non-passenger position packets (1.21.5 lines 958-1028; 1.21.8 lines 977-1047).

Its per-tick packet counter logs after five packets and changes the movement-check multiplier, but does not reject solely for packet frequency (1.21.5 lines 995-1004; 1.21.8 lines 1015-1024).

The next player tick drains recorded samples through inherited `LivingEntity#aiStep`/`applyEffectsFromBlocks` (1.21.5 `LivingEntity.java:2765-2770`; 1.21.8 `LivingEntity.java:2817-2822`).

By contrast, piston movement immediately calls `applyEffectsFromBlocks(from,to)` and removes its own recorded sample, so it is a Player movement path but does not accumulate toward this cap; 1.21.5/1.21.8 call sequence is unchanged (1.21.5 `PistonMovingBlockEntity.java:186-192` 1.21.8 `PistonMovingBlockEntity.java:185-191`
- Analysis: absent the cap, the 1.21.8 replay reconstructs each individual movement's axis order, matching 1.21.5's recorded sequence.

At the threshold 1.21.8 collapses history, so its later block/fluid contact scan can omit or add crossed candidates relative to 1.21.5.

This is a server-authoritative player-contact path difference, not a claim that the client's collision displacement, knockback calculation, combat damage, or vehicle physics changed.

Marked candidate until the complete 1.21.5/1.21.8 callback-provider inventory and blind source review establish impact and disposition.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.5--1.21.8/findings/F-ENTITY-MOVEMENT-QUEUE-CAP.md).
