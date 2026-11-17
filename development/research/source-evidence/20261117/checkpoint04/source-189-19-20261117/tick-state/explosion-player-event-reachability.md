# 1.8.9 → 1.9 explosion player-event reachability

## Scope and bound evidence

This bounded source follow-up tests whether the already accepted conditional explosion-vector gate difference has an ordinary source-reachable event witness. It does not reopen the producer comparison or player-state crosswalk and does not assess explosion damage, fall damage, or a later trajectory.

| Input | Immutable identity |
| --- | --- |
| Pair | `1.8.9 → 1.9` |
| Accepted producer report | `build/source-189-19-20261117/tick-state/external-velocity-producers.md`, SHA-256 `33119428262a965543bd0a80ea00881d2f9c5b07a59a70f941be5c70433c9c38` |
| Producer audit | `build/source-189-19-20261117/tick-state/external-velocity-producers-audit/verdict.md`, SHA-256 `2c258197fefb5eb46ad37a45c251dbfab2b60971a51f321ccd9747d459e53d9f` (ACCEPT) |
| Accepted gate crosswalk | `build/source-189-19-20261117/tick-state/explosion-player-gate-crosswalk.md`, SHA-256 `33fb701cf9b585fe6f28b60a08f839690c88fd271c7ff9f93a296c5eb70c6736` |
| Gate crosswalk audit | `build/source-189-19-20261117/tick-state/explosion-player-gate-crosswalk-audit/verdict.md`, SHA-256 `12d7821fc7c27a522e05ef9a0c49f7e0a9f632003c9bfa1e5fccf4074d397f30` (ACCEPT) |
| 1.8.9 exact source namespace | `decompiled_minecraft/1.8.9/ornithe-feather/`; root sidecar `decompiled_minecraft/1.8.9/ornithe-feather.sources.sha256`, SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; provenance `decompiled_minecraft/1.8.9/ornithe-feather.provenance.json`, SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` |
| 1.9 exact source namespace | `decompiled_minecraft/1.9/ornithe-feather/`; mapping-local `.decompile-provenance.json`, SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` |

For every source file below, the listed SHA-256 was recomputed from the exact file and matched its release's source-sidecar/provenance row. The 1.8.9 sidecar is at the release root, outside the `ornithe-feather/` namespace.

| Relative source path | 1.8.9 SHA-256 | 1.9 SHA-256 |
| --- | --- | --- |
| `net/minecraft/block/TntBlock.java` | `1c8e1f9453079db13e60b484e182c1813a054e81a7d4c524aba8ac54e1babed7` | `4f386996bae1773ed8e73ea3cb8b859e1df498b82773392c90f3dbcb418d5cc0` |
| `net/minecraft/entity/PrimedTntEntity.java` | `a0b79c54b5d225d4a768268cb28a6c755e7c22a7f74668647d5e5bcd38df8d65` | `e58e27c8a1072785a9a225602a7e02677c397f97a2aa29cf79410c14dc6350b3` |
| `net/minecraft/entity/Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| `net/minecraft/entity/EntityFilter.java` | `d97be58e0b8e965e06a0b989685f156be517d89d3b4d0f3154adf7bd3612b6b9` | `f45827ffebad59c151aa5efa834824ba4d63bf60a645d14909df5d41f4b6e440` |
| `net/minecraft/world/World.java` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` | `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` |
| `net/minecraft/server/world/ServerWorld.java` | `6de0ad8b28e72a7b77c01dbf149a71e1e118182ce1fda22883651d4f2616c7c7` | `2ee110ca2786b99cc1068b0d80275586e7c59e10cb130b9f7c2281063dfb8d59` |
| `net/minecraft/world/explosion/Explosion.java` | `68d633251f2a6e604e53c2838693ed986692b87bf48e1bd390ee07c1e9c3390f` | `2da5d23e51fe8ecf2ae1596fbeeee4c00ab22390a23d374fd347ac97fd16414c` |
| `net/minecraft/network/packet/s2c/play/ExplosionS2CPacket.java` | `d3adb63f5cce384fbd3b8679893425910152dd3f06df6645ac692141114e0391` | same |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3` | `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6` |

## Source path and gates

An ordinary TNT use reaches the event in each exact release. `TntBlock.use` accepts flint and steel or a fire charge, calls `ignite` with its `EXPLODE` state set, and removes the block (1.8.9 lines 70–87; 1.9 lines 73–98). On the logical server, `ignite` constructs and adds a `PrimedTntEntity` (1.8.9 lines 60–68; 1.9 lines 63–71). The primed TNT starts with fuse 80 and power 4. Its server-side tick decrements the fuse, removes it at expiry, and calls `explode`; that calls `world.explode` at the TNT coordinates (1.8.9 `PrimedTntEntity.java:18–29,47–76`; 1.9 `:22–34,51–82`). The runtime world dispatches to `ServerWorld.explode` in both releases.

`ServerWorld.explode` constructs the explosion, calls `damageEntities`, then sends an `ExplosionS2CPacket` to every server player with squared distance from the center `< 4096.0` (1.8.9 `ServerWorld.java:789–805`; 1.9 `:949–965`). The packet receives that player's map vector, or null when there is no entry. Its constructor copies each non-null component to float fields. The corresponding `ClientPlayNetworkHandler.handleExplosion` appends all three received fields to the local player's velocity (1.8.9 `:821–830`; 1.9 `:877–886`). Thus the server recipient envelope includes the affected local connection whenever the player is within 64 blocks of the center; the client response is direct velocity addition.

`Explosion.damageEntities` queries entities in the box around the center with radius parameter `r = power * 2`; TNT power 4 gives `r = 8`. The default entity query uses `EntityFilter.NOT_SPECTATOR`, which excludes spectators but includes Creative players. The world query collects entities in loaded chunks intersecting the query bounds. A candidate player proceeds when not explosion-immune, with distance ratio `z <= 1` and nonzero direction length `ad`; `Entity.isImmuneToExplosions()` defaults to false and PlayerEntity has no override. The stored response is `(aa * af, ab * af, ac * af)`, where `(aa,ab,ac)` is the normalized vector from explosion center to player eye position and `af = (1-z) * blockDensity` (`Explosion.java:107–140` in 1.8.9; `:109–149` in 1.9). The preceding `takeDamage(...)` call's result is not tested before this vector write; damage success or amount is not a premise of the response claim.

The already accepted state crosswalk establishes that server-applied Creative with `abilities.flying == false` is legal and synchronized to the local client: game-mode application sets Creative, canFly and invulnerable but does not set flying; the server clamps received flying to canFly; ability sync copies the server fields to the client. On this state, the 1.8.9 producer gate `!abilities.invulnerable` rejects the vector. The 1.9 gate `!isSpectator() && (!isCreative() || !abilities.flying)` admits it. The entity query's not-spectator filter does not separately exclude Creative.

## Conditional source witness

Choose a server-side TNT explosion center at `(0,0,0)` and a loaded, unobstructed Creative player whose base position is `(1,0,0)` and whose applied mode is Creative with flying false. This is a legal ordinary source configuration: TNT can be ignited through the normal block-use path, and nothing in the accepted Creative synchronization path requires flight to be on. Let the player's eye height be `h > 0`. Then `entity.distanceTo(center) = 1`, so `z = 1/8 < 1`; the eye-direction length is `ad = sqrt(1+h²) > 0`; and in an unobstructed sample box `blockDensity > 0`. Therefore `af > 0` and the stored x component is `(1/ad) * af > 0`. The player is inside the entity query bounds and the server packet radius (`1² < 4096`), so 1.9 stores and delivers a nonzero vector. The same state has no player-vector map entry in 1.8.9; its packet's velocity fields remain zero. These are source formula implications, not a runtime or map observation.

This establishes ordinary conditional reachability of the accepted gate difference through a direct player response. The concrete coordinate choice demonstrates the source predicates and formulas; it does not assert that a particular parkour map places TNT or that a particular trajectory reaches that position. Because the difference is a server-computed explosion response added directly to local player velocity, later movement may depend on the surrounding profile and collision state, which this bounded task does not trace. Damage and fall-damage calculations remain excluded and are not premises of the vector witness.

## Disposition

Conditional source reachability: **established** for an ordinary TNT event, a synchronized Creative/non-flying player in query range, and an unobstructed off-center position. The accepted producer and crosswalk claims remain the bound claims; this report closes only their ordinary event-reachability gate. It does not accept pair-wide coverage, infer an actual map/trajectory, evaluate damage, or establish runtime parity.
