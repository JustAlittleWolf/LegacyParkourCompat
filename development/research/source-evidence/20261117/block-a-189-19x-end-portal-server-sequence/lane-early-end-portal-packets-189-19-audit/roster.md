# Frozen source roster — 1.8.9 → 1.9 End portal transfer

Frozen before opening the assigned author group. Evidence is the primary checkout's published exact-version Ornithe Feather source.

## Publication identity

- 1.8.9: `decompiled_minecraft/1.8.9/ornithe-feather.ready.json` says `ready`, exact version `1.8.9`, mapping `ornithe-feather`, 1,612 Java sources. Its declared SHA-256 for `ornithe-feather.sources.sha256` is `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; actual digest matches and all 1,612 listed source digests verify.
- 1.9: `decompiled_minecraft/1.9/ornithe-feather/.decompile-provenance.json`, SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`; 1,819 source entries verify with zero mismatches. Mapping input is the pinned Ornithe Feather 1.9 metadata/artifact recorded in the manifest.

## Paired paths and members to inspect

1. `net/minecraft/block/EndPortalBlock.java` — `onEntityCollision`; portal collision eligibility and `changeDimension(1)` entry.
2. `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` — `changeDimension`; player-specific delegation into the manager.
3. `net/minecraft/server/PlayerManager.java` — `changeDimension(ServerPlayerEntity,int)`, `changeDimension(Entity,int,ServerWorld,ServerWorld)`, and portal-forcer placement call sites. Track local `d/e`, entity XYZ, world changes, life gates, and packet call order for Overworld to End (target dimension 1).
4. `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` — `teleport(...)` overloads and the dimension-change entity transfer path as a caller context; record requested final XYZ, absolute/relative flags, packet construction and 1.9 teleport ID behavior.
5. `net/minecraft/network/packet/s2c/play/PlayerMoveS2CPacket.java` — payload fields, relative flags and (in 1.9) teleport ID.
6. `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` — `handlePlayerMove` and respawn packet handler; absolute coordinate application, velocity-component resets, acknowledgment and client movement response call order.
7. `net/minecraft/network/packet/c2s/play/AcceptTeleportC2SPacket.java` (1.9 only) — acknowledgment payload/dispatch.
8. `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` (1.9 only) — `handleAcceptTeleport` and pending-request confirmation gates/effects.

## Bounded trace and exclusions

Trace ordinary player Overworld (dimension 0) → End (dimension 1) through server transfer, final teleport request, the `PlayerMoveS2CPacket` client handler and its acknowledgment. For the manager helper, distinguish the provisional destination coordinates used during its moving phase from the retained coordinates ultimately sent; establish whether the `dimension != 1` placement/portal-forcer block runs for target dimension 1. Compare exact server source invocation order, not network runtime arrival order. Track only XYZ and velocity side effects needed to judge the absolute position packet. No wiki or product implementation enters this roster.