# Independent receiver audit: sweep and Wind Burst

## Bound input

- Input: `sweep-hit-response-source/comparison.md`
- Exact input bytes SHA-256: `D72279F6E7F44E70D3BF43DFF8152F19E8044DE30FF523B187A2F61034981176`
- Verdict basis: exact local `decompiled_minecraft/1.21.4/mojmap` and `decompiled_minecraft/1.21.5/mojmap` sources, ready markers, provenance, and source manifests. Both markers identify the corresponding exact release and Mojmap; their manifests and relevant Java source hashes match the values in the bound input.

## Verdict

**Sweep — producer-side speculative victim write changes; incoming receiver response does not.** In 1.21.4, `MultiPlayerGameMode.attack` sends the interaction and invokes local `Player.attack` outside spectator mode. In `Player.attack`'s sweep loop, the client-side execution calls `victim.knockback(0.4F, sin(yaw), -cos(yaw))` before calling `victim.hurt(...)`, without using the `hurt` result to gate the knockback. Thus the attacking client's local entity copy of each eligible swept victim receives a speculative velocity write. In 1.21.5 the corresponding call is inside `level instanceof ServerLevel && victim.hurtServer(...)`; the attacker's ClientLevel does not take that branch, so the client no longer speculatively writes that victim velocity. The server-side branch gates knockback on accepted `hurtServer`. The knockback method's relevant math is unchanged across the pair.

The authoritative path remains the same: server entity tracking broadcasts `ClientboundSetEntityMotionPacket` for changed impulse state and/or `hurtMarked`; the packet clamps components to ±3.9 and truncates each clamped component after multiplication by 8000, then decodes by division by 8000. `ClientPacketListener.handleSetEntityMotion` looks up the packet's entity ID and calls `Entity.lerpMotion`; `lerpMotion` calls `setDeltaMovement` in both versions. This is an absolute replacement of the target entity's velocity, not an additive impulse. The receiving method is generic and has no local-player special case. For the swept victims, it reconciles their entity copies; it does not directly change the receiving client's own player unless that packet names that player.

**Wind Burst — no receiver delta found.** `Enchantments.WIND_BURST` uses the same post-attack `ExplodeEffect` definition across the pair. `ExplodeEffect.apply` calls server-side `ServerLevel.explode`; `ServerExplosion` pushes affected entities and stores eligible player knockback vectors; `ServerLevel.explode` sends those vectors as `Optional<Vec3> playerKnockback` in `ClientboundExplodePacket`. The client `handleExplosion` receiver applies the optional vector to `minecraft.player` via `addDeltaMovement`, and `Entity.addDeltaMovement` adds it to existing velocity. The effect definition, explosion vector collection, packet shape, and direct local-player addition are unchanged in the examined sources. This is a real direct local-player state write on receipt, common to both versions; it is not the sweep `SetEntityMotion` receiver behavior.

## Source cross-checks

The exact source hashes listed in the bound input were recomputed for all twelve relevant files on both sides and each matched its `mojmap.sources.sha256` entry. Ready marker and provenance hashes also matched the input: 1.21.4 ready `1c6617b7acf8bb357f77c6c6c276881c73d882bda1f93f6dd217bb54eb8aa4be`, provenance `ab0439864c89f4e0dab09daae7b57ab973368eeb0320626822e06f5a9bce9fb9`; 1.21.5 ready `5f65fb143209abcc6e28e77a7150aecefdbc9de00b6ef3fc924e53bc0e1a667e`, provenance `b0832a4e1507bd74ac300fae2fca78668964b21ef2d05be8cff01b9d1d60da8e`.

Primary slices inspected (line numbers are in local exact Mojmap trees):

- `Player.attack`: 1.21.4 sweep branch 1206–1223; 1.21.5 1167–1189. `MultiPlayerGameMode.attack`: 1.21.4 389–395; 1.21.5 392–398.
- `LivingEntity.knockback`: 1.21.4 1502–1516; 1.21.5 1531–1545.
- `ServerEntity` motion sends: 1.21.4 168–218; 1.21.5 168–219. `ClientboundSetEntityMotionPacket`: 20–72 in both. `ClientPacketListener.handleSetEntityMotion`: 1.21.4 538–543; 1.21.5 549–554. `Entity.lerpMotion`: 1.21.4 2284–2286; 1.21.5 2252–2254.
- Wind Burst registration: `Enchantments.java` 1156–1193 in both; `ExplodeEffect.apply`: `ExplodeEffect.java` 57–67 in both; `ServerExplosion.hurtEntities`: 191–215 in both; `ServerLevel.explode` packet send: 1.21.4 1079–1113; 1.21.5 1097–1131. `ClientboundExplodePacket` schema and `ClientPacketListener.handleExplosion` (1.21.4 1225–1242; 1.21.5 1245–1262); `Entity.addDeltaMovement` (1.21.4 3380–3382; 1.21.5 3378–3380).

## Limits

This is an independent audit of the supplied bounded 1.21.4→1.21.5 sweep victim-response and Wind Burst receiver claims only. It does not establish an intermediate patch boundary, general movement-pair coverage, runtime parity, attack/damage correctness, or eligibility beyond the inspected call path. No product implementation, wiki, Git history, tests, game launch, or source generation was used.

**TERMINAL: COMPLETED**