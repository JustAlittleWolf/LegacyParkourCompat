# F-04: Climbing players are excluded from the push recipient filter

- Older version A: 1.10.2
- Newer version B: 1.11.2
- Mechanic / coverage slice IDs: external player push eligibility; S7-pushability
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player interaction with another entity
- First changed release: unknown within (1.10.2, 1.11.2]
- Runtime validation: not performed

## Paired evidence

- A: artifact `A` in `../run.md`; `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `LivingEntity#isPushable`, lines 1887-1889, SHA-256 `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`, returns `!removed`. `EntityFilter#canBePushedBy`, lines 56-70, `net/minecraft/entity/EntityFilter.java`, SHA-256 `91158a5477935c911178433e1b2628d1d063604a23b08baee4706dd286e7e813`, rejects candidates for which `isPushable()` is false. `RemoteClientPlayerEntity#mobTick`, lines 105-112, SHA-256 `e7e248b439f7356695b1bf196d08e997bb19b24c23b206763c772663ee7086a1`, calls `pushAwayCollidingEntities`. `Entity#push`, lines 1085-1107, SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`, can write target horizontal velocity through `addVelocity`.
- B: artifact `B` in `../run.md`; `LivingEntity#isPushable`, lines 1950-1952, `net/minecraft/entity/living/LivingEntity.java`, SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`, returns `isAlive() && !isClimbing()`. `EntityFilter#canBePushedBy`, lines 50-64, SHA-256 `49f2c3cbeea0bb9421fdfaad49a7742394f69cd73981a9cdd51e1badcf30c629`, uses the same `isPushable()` rejection. `RemoteClientPlayerEntity#mobTick`, lines 105-112, same source hash as A, calls the same inherited push candidate path. `Entity#push`, lines 1153-1175, SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`, retains the velocity-write path.

## Source-level difference

For a non-removed living player, A reports pushable regardless of climbing. B rejects the player while climbing. The paired filter excludes rejected entities from the collision candidate list. On the client, the filter permits a player candidate only when it is local; therefore a remote player can select the local player when its bounding box overlaps the local player's box and collision rules permit the interaction. Its inherited `pushAway` calls `entity.push(this)` on that local player. The `Entity.push` path can add horizontal velocity when entities are not in the same vehicle, neither is no-clip, horizontal separation is at least `0.01F`, and the target has no passengers.

## Reachability and dependencies

Remote player tick -> inherited `LivingEntity.pushAwayCollidingEntities` -> `EntityFilter.canBePushedBy(remotePlayer)` -> local player `isPushable` -> `pushAway`/`Entity.push` -> local player's velocity. The direct precondition for the difference is that the local player is climbing and the remote player's collision query otherwise admits the local player. Team collision rules, overlap geometry and `Entity.push` guards gate the call. This finding does not model entity movement or dead-player behavior.

## Consequence and uncertainty

Source proves B can omit a climbing local player from this push recipient list where A would include it, preventing the downstream local-player velocity write on this path. The magnitude and resulting trajectory are not claimed. The separate `isAlive()` gate can also affect removed/dead entities, but those state producers are excluded and are not part of this finding.

## Handoff

Keep the predicate delta separate from entity collision boxes in `World.getCollisions`, whose paired entity-list portions were observed to be present on both sides. Broader vehicle and external push paths remain part of D-EXTERNAL. Introduction is unknown within the endpoint interval.
