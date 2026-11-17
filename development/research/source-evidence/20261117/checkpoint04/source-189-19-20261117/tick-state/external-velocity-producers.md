# 1.8.9 → 1.9 direct player velocity packet producers

## Exact source identity

- Older source root: `decompiled_minecraft/1.8.9/ornithe-feather/`; source-manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; provenance SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`.
- Newer source root: `decompiled_minecraft/1.9/ornithe-feather/`; `.decompile-provenance.json` SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.
- Each source file below matched its exact source-sidecar/provenance row and local SHA-256.

| Source | 1.8.9 SHA-256 | 1.9 SHA-256 |
| --- | --- | --- |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` | `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1` |
| `net/minecraft/network/packet/s2c/play/EntityVelocityS2CPacket.java` | `a2240f867e2486d6929a66928e3b45b75e7ff21d36eed132ec682634c7c7af3d` | same |
| `net/minecraft/network/packet/s2c/play/ExplosionS2CPacket.java` | `d3adb63f5cce384fbd3b8679893425910152dd3f06df6645ac692141114e0391` | same |
| `net/minecraft/server/TrackedEntity.java` | `c8f4c2115e2bd348c85be8cc4a35f18eece8edcce5560989dfa45cacb39f9b15` | `82e4772fb6da96cbc92548211f7e0f5161b68e1b71726f26a6e2b7bb08ef09f0` |
| `net/minecraft/server/world/ServerWorld.java` | `6de0ad8b28e72a7b77c01dbf149a71e1e118182ce1fda22883651d4f2616c7c7` | `2ee110ca2786b99cc1068b0d80275586e7c59e10cb130b9f7c2281063dfb8d59` |
| `net/minecraft/world/explosion/Explosion.java` | `68d633251f2a6e604e53c2838693ed986692b87bf48e1bd390ee07c1e9c3390f` | `2da5d23e51fe8ecf2ae1596fbeeee4c00ab22390a23d374fd347ac97fd16414c` |

## Coverage and result

This report is limited to server-side construction and routing of direct entity-velocity and explosion S2C packets. The earlier tick-state coverage report checked the client handlers; they are not re-audited here. Damage calculation, generic non-player physics and logical-server historical movement are excluded.

### Explosion response

`ServerWorld.explode` has the same operation in both versions: construct the explosion, run entity/block processing, then send an `ExplosionS2CPacket` to each server player whose squared distance from the center is `< 4096`. The packet receives the player-keyed vector from `Explosion.getDamagedPlayers()` (1.8.9 `ServerWorld.java` 789–805; 1.9 949–965). `ExplosionS2CPacket` is byte-identical across the pair; its constructor copies the three vector components to float fields when non-null (both versions, lines 25–35). The client response packet and server recipient/radius path therefore have no producer-envelope delta.

The player-vector admission gate inside `Explosion.damageEntities` does differ. 1.8.9 stores `(aa * af, ab * af, ac * af)` when the entity is a `PlayerEntity` and `!abilities.invulnerable` (lines 118–140, especially 138–139). 1.9 stores the same vector when the entity is a player, is not a spectator, and `(!isCreative() || !abilities.flying)` (lines 120–149, especially 144–148). The explosion packet consequently has a conditional direct-player-velocity difference when those predicates disagree. This report does not establish the player-state crosswalk or ordinary scenario that makes the gates disagree; it does not compare explosion damage formulas or infer a trajectory. For states admitted by both gates, the stored direction/intensity vector is the same expression.

### Entity velocity response

`PlayerEntity`'s successful-hit path sends `EntityVelocityS2CPacket(target)` to a `ServerPlayerEntity` target if `target.damaged`, then restores the saved server velocity components (1.8.9 lines 988–994; 1.9 lines 1026–1032). The send ordering/recipient gate is present in both. The packet class is byte-identical. The value can depend on upstream attack/knockback changes; this bounded producer comparison does not close those formulas or damage/attack eligibility.

The generic `TrackedEntity` delta path sends the network ID and its tracked velocity components when velocity delta squared exceeds `0.02²` (1.8.9 lines 195–205; 1.9 lines 205–216, written as `4.0E-4`), or under the identical nonzero-to-zero reset condition. `0.02²` equals `4.0E-4`; packet values/order match. The `entity.damaged` full-velocity send is also present in both (`TrackedEntity.java` 251–255 / 262–266). The tracker recipient list is not asserted here to include the moving entity's own client.

1.9 adds a tracked `isFallFlying()` force-velocity-send branch (`TrackedEntity.java` 325–345); Elytra/fall-flying is post-1.8 and is excluded for the 1.8.9 profile. It is not a historical player-response candidate.

## Disposition and remaining gates

- Bounded source observation: explosion packet recipient/radius and packet field packing are unchanged; the player-vector gate changes conditionally as described.
- No movement difference found in the shared velocity packet class, player-target send envelope, or ordinary tracked-velocity threshold/reset envelope.
- Open: whether `abilities.invulnerable` corresponds to the newer spectator/creative/flying predicate for the relevant player states, and whether any in-scope profile scenario reaches the differing gate. Upstream attack knockback values remain a separate dependency. No trajectory, implementation-readiness or pair-freeze claim is made.
