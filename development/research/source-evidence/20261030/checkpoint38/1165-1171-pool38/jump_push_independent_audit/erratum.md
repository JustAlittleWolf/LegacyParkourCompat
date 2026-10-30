# Erratum: Entity.push client caller direction

This erratum corrects only the Entity.push player-applicability analysis in `audit.md` (the original audit remains unchanged; SHA-256 `d38df127fcda4f7aba31206afc787b5079b1016bacf74ce6d9331a38ce82f03c`). The Jump Boost verdict and evidence are unchanged.

## Corrected verdict

**ACCEPT: the 1.16.5→1.17.1 square-root precision source delta affects a reachable local-player velocity writer through a nearby client-side Mob’s `pushEntities()` call, under the gates below.** The prior audit’s proposed route with the local player as the `pushEntities()` caller is invalid for ordinary non-player pushables. The Entity.push candidate remains source-confirmed through the Mob-as-caller path. It is not a RemotePlayer path and does not by itself establish a normal game-state trace or runtime parity.

## Exact client call route

The route is present in both exact Mojmap endpoints:

1. `ClientLevel` ticks each eligible entity by calling `entity.tick()` (1.16.5 `client/multiplayer/ClientLevel.java:188-193`; 1.17.1 `:174-178`).
2. `Mob.tick()` calls `super.tick()` and its additional leash/control updates are server-only (1.16.5 `world/entity/Mob.java:299-307`; 1.17.1 `:307-315`). The superclass chain reaches `LivingEntity.tick()`, which unconditionally calls the dynamically dispatched `this.aiStep()` (1.16.5 `LivingEntity.java:2124-2171`; 1.17.1 `:2218-2258`).
3. Dynamic dispatch enters `Mob.aiStep()`, whose first movement-relevant operation is unconditional `super.aiStep()` (1.16.5 `Mob.java:493-494`; 1.17.1 `:501-502`). The later loot logic is guarded by `!level.isClientSide`; this guard does not cover the preceding superclass call.
4. That call enters `LivingEntity.aiStep()`. On a client, `LivingEntity.isEffectiveAi()` is false (1.16.5 `LivingEntity.java:2639-2640`; 1.17.1 `:2757-2759`), so server-side mob goal processing is skipped. The method nevertheless continues through its travel call and then unconditionally invokes `pushEntities()` (1.16.5 `LivingEntity.java:2454-2468`; 1.17.1 `:2565-2574`). There is no client-side early return around this movement/push section.
5. The Mob’s `pushEntities()` query uses `EntitySelector.pushableBy(this)` and then `this.doPush(entity)` (1.16.5 `LivingEntity.java:2502-2523`; 1.17.1 `:2614-2635`). On a logical client, `pushableBy` admits a `Player` only when it is the local player; it rejects non-player targets. Specifically, its condition is `!entity.level.isClientSide || entity2 instanceof Player && ((Player)entity2).isLocalPlayer()` (both endpoint `EntitySelector.java:39-51`). Thus a Mob’s client query may select the local player when the query bounds overlap and the shared selector/team gates pass.
6. `doPush(localPlayer)` calls `localPlayer.push(mob)` (1.16.5 `LivingEntity.java:2549-2550`; 1.17.1 `:2661-2662`). The local player is now the receiver of `Entity.push(Entity)`, while the Mob is its argument. `LivingEntity.push(Entity)` delegates to `Entity.push(Entity)` only if the local player is not sleeping (1.16.5 `LivingEntity.java:1856-1859`; 1.17.1 `:1942-1945`). In `Entity.push`, with receiver = local player, `d = mob.x - player.x`; after the shared-vehicle, both-`noPhysics`, and `absMax(d,e) >= 0.01F` gates and the sqrt/normalization operations, `this.push(-d,0,-e)` writes the local player’s impulse if the local player is not a vehicle (1.16.5 `Entity.java:1180-1215`; 1.17.1 `:1278-1312`). `Entity.push(double,double,double)` adds the impulse to delta movement and sets `hasImpulse` (1.16.5 `:1213-1215`; 1.17.1 `:1309-1311`).

The caller-side gates are therefore: a client-ticked Mob with an overlapping query box; a local player returned by the query and accepted by its pushability/team rules (including non-spectator, alive, and not climbing); no shared root vehicle; both entities have `noPhysics == false`; local player is not sleeping; horizontal `absMax(dx,dz) >= 0.01F`; and local player is not itself a vehicle. Mob AI goals need not run on the client for this caller path to execute. Entity presence, exact overlap/coordinate values, team/collision state, passenger/root-vehicle state, and noPhysics state remain environment inputs.

## Incorrect route and RemotePlayer disposition

The earlier audit incorrectly generalized that `LocalPlayer.pushEntities()` can select an ordinary non-player entity on a client. It cannot under the shared predicate: with caller `entity = LocalPlayer`, a non-player `entity2` makes the client-side `instanceof Player && isLocalPlayer()` condition false. That proposed local-player-as-caller/non-player-target route is rejected.

A remote player does not supply the missing route. The selector rejects nonlocal Player targets when a Mob queries, while `RemotePlayer` also sets `noPhysics = true` in both endpoint constructors (1.16.5 and 1.17.1 `client/player/RemotePlayer.java:11-15`). A reverse remote-player collision cannot pass the `Entity.push(Entity)` noPhysics guard. The valid client caller is an eligible non-player Mob selecting the local player, not a remote player selecting or being selected by the local player.

## Corrected conditional arithmetic witness

Use receiver = local player, argument = Mob; condition on the Mob being returned by the client query, local player not being a vehicle, awake local player, no shared vehicle, and both `noPhysics == false`. Let player position be `(0.03, y, 0)` and Mob position `(0, y2, 0)`, so Entity.push computes `d = -0.03`, `e = 0`, and `f = absMax(d,e) = 0.03`. The threshold passes. Both versions then clamp `g = 1/sqrt(0.03)` to 1. With local starting X velocity zero and default vanilla `pushthrough == 0`, source operation order yields a local-player X impulse of `+0.008660254322328477` in 1.16.5 versus `+0.008660254166892228` in 1.17.1 (difference `-1.5543624894143182e-10`). The sign is positive because the local receiver uses `this.push(-d,0,-e)` and `d` is negative for these coordinates. Whether the Mob itself is a vehicle affects only its separate impulse branch.

This witness establishes arithmetic conditional on explicit input values and the caller gates. It does not establish an exact entity/world/tick trace, entity scheduling order, multi-tick trajectory, or runtime parity.

## Evidence identity

This erratum uses only the already verified exact Mojmap trees `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.5\mojmap` and `...\1.17.1\mojmap`. Their full Java source manifests remain verified at 3,523/3,523 and 4,142/4,142 entries, respectively (manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` and `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`). Added caller-file SHA-256 values, matching those manifests:

| Source | 1.16.5 SHA-256 | 1.17.1 SHA-256 |
|---|---|---|
| `world/entity/Mob.java` | `47758bd4e5749b39cc243e4bb9f646aa646b3059436e722c6e4e490c999053ed` | `b3dae9dd39ac551782aa76aa97be3ab755832e83d606cc56c094fb63c3664fd1` |
| `client/multiplayer/ClientLevel.java` | `96c80a10623d41f136fa70391b3920cf7f3ef15547923f7d772b78d356f16d93` | `fd2b3ebade86250cf1ab05f6086dd48f11b6bcd3a01e67c8a03f5c34f553fbc2` |
| `world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| `world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` |
| `world/entity/EntitySelector.java` | `1fd642a27e3773d2c8bb79a20db965b2ea4876fea232490fbbbdc7efbf78fd9a` | `63f72ac3907b18f09716e9e64cfd3b7e8767f43792430ef79f15c11b59845c51` |

No product source, Git, wiki, backlog, runtime, tests, build, or game launch was consulted or used.
