# Bounded NO_DIFF: local-player pose selection and collision dimensions

Scope: 1.21.11 Mojmap → 26.1.2 unobfuscated. This checks local player crouching/standing/low-ceiling pose selection, pose-fit collision queries, and the dimension/AABB/eye-height update path. It does not claim pair-wide coverage, an intermediate boundary, runtime parity, or parity of the block/entity collision shapes returned by a changing world.

## Exact source identity

- 1.21.11: `decompiled_minecraft/1.21.11/mojmap`, version ID `1.21.11`, mapping `mojmap`. Ready marker `decompiled_minecraft/1.21.11/mojmap.ready.json` SHA-256 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`; provenance `decompiled_minecraft/1.21.11/mojmap.provenance.json` SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`; source manifest `mojmap.sources.sha256` SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`; artifact manifest `artifacts.sha256` SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`. Ready marker binds the source and artifact manifest digests.
- 26.1.2: `decompiled_minecraft/26.1.2/unobfuscated`, version ID `26.1.2`, mapping `unobfuscated`. Ready marker `unobfuscated.ready.json` SHA-256 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`; provenance `unobfuscated.provenance.json` SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`; source manifest `unobfuscated.sources.sha256` SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`; artifact manifest `artifacts.sha256` SHA-256 `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`. Ready marker binds the source and artifact manifest digests; provenance records the exact unobfuscated run and client artifact SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`.

Every Java SHA-256 below was checked both against the local canonical file bytes and its endpoint source manifest.

## Bounded comparison

The ordinary client path reaches the same pose logic at both endpoints: `LocalPlayer.tick` calls `super.tick`; `Player.tick` calls `updatePlayerPose` after its shared tick work. `LocalPlayer.aiStep` reaches `super.aiStep`. The local crouching boolean retains the same predicate and order: not flying, not swimming, not a passenger, the crouching-pose fit succeeds, and either shift is down or the player is awake and cannot fit standing.

`Player.updatePlayerPose` has the same gates and ordering at both endpoints. It first requires that the swimming-pose box fits. It then chooses the desired pose for spectators/passengers or when that pose fits; otherwise it chooses crouching when that box fits, and swimming when it does not. Desired-pose priority is sleeping, swimming, fall-flying, spin attack, then shift-and-not-flying crouch versus standing. `canPlayerFitWithinBlocksAndEntitiesWhen` builds the selected pose box at the current position, deflates it by `1.0E-7`, and calls `level().noCollision(this, box)` in both sources.

The collision query overload keeps the same short-circuit order: block collision, entity collision, then world-border collision. Consequently the pose algorithm feeds the same query shape and source entity to the same query interface; this does not establish that all block/entity shapes or world data are equal between releases.

Pose data updates still call `Entity.refreshDimensions`. Both versions compute dimensions from the new pose, assign dimensions and eye height, then reapply the current position to rebuild the bounding box. The server-only size-growth position fudge is behind the same client/first-tick/no-physics/size/player gates and excludes `Player` at both endpoints. The local-player pose change therefore uses the same client path and does not trigger that server-only player-excluded adjustment.

There is no `Player` override of `LivingEntity.getDimensions`/`getDefaultDimensions` at either endpoint. The inherited non-sleeping path uses the player `EntityType` dimensions with the same scale operations. `EntityType.PLAYER` remains sized `(0.6F, 1.8F)` with eye height `1.62F` at both endpoints. The same `EntityDimensions.makeBoundingBox` and scale formulas apply. Thus the source slice shows no changed pose-selection predicate, fit-box construction, player dimension/eye-height mapping, or AABB refresh operation.

## Exact source references and hashes

| Role | 1.21.11 Mojmap source (line anchors) | SHA-256 | 26.1.2 unobfuscated source (line anchors) | SHA-256 |
|---|---|---|---|---|
| Client tick/input pose context | `net/minecraft/client/player/LocalPlayer.java`, `tick` 211–214, `aiStep` crouching gate 742–747 and `super.aiStep` 875 | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` | `net/minecraft/client/player/LocalPlayer.java`, `tick` 227–230, `aiStep` crouching gate 781–786 and `super.aiStep` 914 | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| Pose updater, desired-pose gates, and no-collision box | `net/minecraft/world/entity/player/Player.java`, `tick` call 290, `updatePlayerPose` 352–364, `getDesiredPose` 366–380, `canPlayerFitWithinBlocksAndEntitiesWhen` 382–383 | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` | `net/minecraft/world/entity/player/Player.java`, `tick` call 283, `updatePlayerPose` 342–354, `getDesiredPose` 356–370, `canPlayerFitWithinBlocksAndEntitiesWhen` 372–373 | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| Player dimensions inherited for each pose | `net/minecraft/world/entity/LivingEntity.java`, `getDimensions` 3561–3563, `getDefaultDimensions` 3565–3566 | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `net/minecraft/world/entity/LivingEntity.java`, `getDimensions` 3672–3674, `getDefaultDimensions` 3676–3677 | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| Player default size and eye height | `net/minecraft/world/entity/EntityType.java`, `PLAYER` registration 1191–1198 | `fc2abb3e905b9a63a27f0b027fad089d9d40e5d3d45608406b5da2a5ec47a73e` | `net/minecraft/world/entity/EntityType.java`, `PLAYER` registration 1189–1196 | `e5f37ee2dd639b7271197e58f7a6f3bdbedc0f0f1d618039d159a0e7de315229` |
| Dimension box and scale math | `net/minecraft/world/entity/EntityDimensions.java`, `makeBoundingBox` 15–23, `scale` 25–33 | `9fb9575ec615d4aefe6316c443e87a902f44e955393778a4b93623e8b680adc4` | `net/minecraft/world/entity/EntityDimensions.java`, `makeBoundingBox` 15–23, `scale` 25–39 | `27fa1fb5c3e9bf49123a238675f6bea29e65ad5b0362a9a64dcb5d5f5407376a` |
| Pose setter and dimension/AABB refresh | `net/minecraft/world/entity/Entity.java`, `setPose` 413–419, `onSyncedDataUpdated` 3212–3216, `refreshDimensions` 3226–3243, `reapplyPosition` 458–461 | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` | `net/minecraft/world/entity/Entity.java`, `setPose` 432–438, `onSyncedDataUpdated` 3307–3311, `refreshDimensions` 3320–3337, `reapplyPosition` 477–480 | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| Fit-query implementation | `net/minecraft/world/level/CollisionGetter.java`, `noCollision` 39–70 | `ca2a5c4561fa8b263d7ce8513a152f9c1d6537f4f011859351ce58b39a7f4e70` | `net/minecraft/world/level/CollisionGetter.java`, `noCollision` 39–70 | `dddff4897e8d5d01e3ac132d85c474e6a3b9bae928e48b8c907aa9eabdb7fb01` |

## Open gates

- This is one pose/collision-query slice only; other movement routes, modifiers, block-shape providers, collision-state/resource inventories, and intermediate releases are unexamined.
- The shared collision-query algorithm is equivalent, but actual block/entity collision answers remain dependent on each endpoint's world data and shape providers; no world-wide pose result is asserted.
- No movement delta was found in the bounded operation. This is not full-pair acceptance, implementation readiness, or runtime parity.
