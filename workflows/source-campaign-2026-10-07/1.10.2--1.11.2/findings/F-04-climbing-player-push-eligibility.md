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

### Derived-artifact provenance for this source finding

This source finding uses unchanged published Java source trees, with canonical consumer revision `feather-r1-2026-10-07` independently ops-verified. A revised snapshot: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.10.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`; `revision.json` at the same directory, SHA-256 `79b5688af6f5cf6eb5c006a867de841de0033b42408c8461283cd9c4b9389856`, records `originalDerivedArtifactAvailable: false`, `sourceTreeIdentical: true`, `rawInputsIdentical: true`, and `sourceFileDifferences: 0`. Its `artifact.sha256` has SHA-256 `5fdba25a0ac067808c51a7ecad58d2034717b83402e8020bb11a07628942c8d9`. Original source manifest: `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather.sources.sha256`, SHA-256 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; original raw-artifact manifest: `build/movement-campaign-2026-10-07/ready/1.10.2/artifacts.sha256`, SHA-256 `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116`. The unavailable original mapped-JAR hash in that manifest is `ab7aa536f52e94c4c099999bc731020c11a1b29e2979a566cedcc2ffb00e701d`; identity with the revised snapshot is not claimed.

B revised snapshot: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.11.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`; `revision.json` at the same directory, SHA-256 `49fca091d3ef83551745119f740d7a66a2773e81262db137bfc747369e8f61ac`, records `originalDerivedArtifactAvailable: false`, `sourceTreeIdentical: true`, `rawInputsIdentical: true`, and `sourceFileDifferences: 0`. Its `artifact.sha256` has SHA-256 `40131c4f05a8229d384ee7cb3680ec2c45eb6e6b7a10a25544dd7c9b71c7cc3eb`. Original source manifest: `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather.sources.sha256`, SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; original raw-artifact manifest: `build/movement-campaign-2026-10-07/ready/1.11.2/artifacts.sha256`, SHA-256 `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`. The unavailable original mapped-JAR hash in that manifest is `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3`; identity with the revised snapshot is not claimed.

## Source-level difference

For a non-removed living player, A reports pushable regardless of climbing. B rejects the player while climbing. The paired filter excludes rejected entities from the collision candidate list. On the client, the filter permits a player candidate only when it is local; therefore a remote player can select the local player when its bounding box overlaps the local player's box and collision rules permit the interaction. Its inherited `pushAway` calls `entity.push(this)` on that local player. The `Entity.push` path can add horizontal velocity when entities are not in the same vehicle, neither is no-clip, horizontal separation is at least `0.01F`, and the target has no passengers.

## Reachability and dependencies

Remote player tick -> inherited `LivingEntity.pushAwayCollidingEntities` -> `EntityFilter.canBePushedBy(remotePlayer)` -> local player `isPushable` -> `pushAway`/`Entity.push` -> local player's velocity. The direct precondition for the difference is that the local player is climbing and the remote player's collision query otherwise admits the local player. Team collision rules, overlap geometry and `Entity.push` guards gate the call. This finding does not model entity movement or dead-player behavior.

## Consequence and uncertainty

Source proves B can omit a climbing local player from this push recipient list where A would include it, preventing the downstream local-player velocity write on this path. The magnitude and resulting trajectory are not claimed. The separate `isAlive()` gate can also affect removed/dead entities, but those state producers are excluded and are not part of this finding.

## Handoff

Keep the predicate delta separate from entity collision boxes in `World.getCollisions`, whose paired entity-list portions were observed to be present on both sides. Broader vehicle and external push paths remain part of D-EXTERNAL. Introduction is unknown within the endpoint interval.
