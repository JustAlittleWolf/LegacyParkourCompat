# Early knockback response source comparison

## Scope and evidence identity

Exact comparison: Minecraft 1.8.9 versus 1.9.4, Ornithe Feather namespace (`ornithe-feather`). The adjacent `ornithe-feather.provenance.json` files identify exact releases and Feather build.2 artifacts; the corresponding `ornithe-feather.sources.sha256` files identify the inspected source trees. Both provenance manifests report the same exact source preparation run and distinct version IDs/artifacts.

## Finding

There is no changed local client movement calculation for applying an incoming entity-velocity response in this pair. In 1.8.9, `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`, `handleEntityVelocity` (lines 402–408), checks the network thread, looks up the entity, then calls `entity.lerpVelocity(packet.getVelocityX() / 8000.0, packet.getVelocityY() / 8000.0, packet.getVelocityZ() / 8000.0)` when present. In 1.9.4 the same sequence appears in `handleEntityVelocity` (lines 435–441). In both exact sources, `net/minecraft/entity/Entity.java`, `lerpVelocity` simply assigns the three arguments to `velocityX`, `velocityY`, and `velocityZ` (1.8.9 lines 1424–1428; 1.9.4 lines 1565–1569). No interpolation or additional impulse math occurs on this path.

`EntityVelocityS2CPacket.java` is byte-identical by the source manifests in both releases. Its value constructor clamps each component to [-3.9, 3.9] and stores `(int)(component * 8000.0)` (lines 22–51); decoding reads three shorts (lines 54–60). Thus the client's authoritative motion input is the packet's quantized three-component velocity, which the client applies by replacement.

A normal player-versus-player scenario reaches this path when a successful hit produces a damaged target and the target is a server player. In both `net/minecraft/entity/living/player/PlayerEntity.java` attack methods, after `target.takeDamage(...)` succeeds, the code conditionally applies knockback; if the target is `ServerPlayerEntity` and `target.damaged`, it sends `new EntityVelocityS2CPacket(target)`, clears `damaged`, and restores the target's saved velocity fields on the server (1.8.9 lines 972–994; 1.9.4 lines 987–1037). This packet is the client-visible motion response. The knockback generation differs server-side: 1.8.9 adds a yaw-derived vector to the target, while 1.9.4 routes living targets through `LivingEntity.applyKnockback`, which halves existing horizontal velocity and conditionally updates vertical velocity (1.9.4 `LivingEntity.java`, lines 870–885). That difference can change the server-supplied packet payload, but it is not a second local client formula and does not justify recomputing or modifying the authoritative payload in the client handler.

The incoming `EntityEventS2CPacket` is a separate status path. Both network handlers dispatch it to `entity.doEvent(event)` (1.8.9 lines 771–780; 1.9.4 lines 836–846). `LivingEntity.doEvent` handles event 2 as hurt animation/sound/timers and event 3 as death sound/health/death (1.8.9 lines 955–978; 1.9.4 lines 1067–1092). Neither event carries velocity or performs the knockback motion update. The 1.9.4 method adds other status codes, with no equivalent changed impulse math.

## Applicability boundary

For fixed-profile client movement, the receive-and-apply operation is identical across this exact pair and must consume the velocity packet values as supplied. The source difference in attack/knockback generation belongs to server-side attack and damage resolution; the distinct hurt/death status event is not a knockback trigger. This comparison covers only 1.8.9 versus 1.9.4 and does not establish an intermediate patch boundary or other release pairs.

## Terminal result

COMPLETED — no local incoming knockback-response math difference found for 1.8.9/1.9.4. Exact applicability is limited to the client entity-velocity packet receive/apply path and separation from entity status events; server-side payload generation is identified as context, not a client movement delta.