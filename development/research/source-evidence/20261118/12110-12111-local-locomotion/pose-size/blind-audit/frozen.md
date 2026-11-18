# Frozen blind audit: 1.21.10 → 1.21.11 player pose and dimensions

## Frozen scope and result

Exact pair: Minecraft 1.21.10 → 1.21.11, Mojmap on both sides. Scope is the awake, non-riding player''s ordinary post-tick pose choice/writer, with requested pose held equal to `Pose.CROUCHING`; swim/crawl state selection, sprint/sneak gates and timers, rotation, flight, climbing, external responses, collision-fit implementation, and resize collision internals are outside this audit.

The paired `Player.updatePlayerPose`, `Player.canPlayerFitWithinBlocksAndEntitiesWhen`, `Entity.setPose`, and `LivingEntity.getDimensions(Pose)` source bodies are equivalent. There is no independent pose-choice or effective-size delta in this bounded slice. In an open-space witness where the fixed player box passes the fit predicates, both versions choose and write `Pose.CROUCHING`.

The first pose-triggered box refresh has the same sequence in both versions: changed pose data invokes `Entity.onSyncedDataUpdated(DATA_POSE)`; that calls `refreshDimensions()`; refresh reads old dimensions, reads dimensions for the new pose, stores dimensions and eye height, then calls `reapplyPosition()` to rebuild the AABB at the unchanged position. The later server-only size-growth/fudge branch explicitly excludes every `Player`, so it does not reposition this player.

There is one adjacent exact-source change on this path. 1.21.11 `Entity.reapplyPosition()` first sets `lastKnownPosition = null`, then calls `setPos` with the existing position. 1.21.10 only calls `setPos`. In 1.21.11 the next `computeSpeed()` seeds a null `lastKnownPosition` from the current position before subtracting, so the next known-speed delta is zero after a pose-triggered refresh. This changes auxiliary known-speed state, not the pose choice, dimensions, player position, or box coordinates. The examined consumer paths are generic known-speed access and the modern kinetic-weapon path; vehicle/ride use is excluded here. I do not promote this to a locomotion pose/size finding.

## Exact source identities

The canonical source roots were read-only at `decompiled_minecraft/1.21.10/mojmap/` and `decompiled_minecraft/1.21.11/mojmap/`. Both version-root ready markers report exact IDs, `mapping: mojmap`, Mojmap release client hashes below, and movement-diagnostics review 9. Their provenance files report one exact run of Vineflower 1.12.0, Java 25.0.3+9-LTS, mapping-io 0.9.1, TinyRemapper 0.14.1, and identical artifact-manifest SHA-256. Each named source''s raw SHA-256 matches its version-root `mojmap.sources.sha256` manifest entry.

| Evidence | 1.21.10 | 1.21.11 |
|---|---|---|
| Ready marker SHA-256 | `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0` | `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b` |
| Provenance file SHA-256 | `9a1db4c050c3caa28a1313f89dae9da3b28feaa5f8afba42cab798b47d7ad77c` | `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72` |
| Client SHA-256 (ready marker) | `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482` | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` |
| Source-manifest SHA-256 (also matches ready marker) | `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` |
| Artifact-manifest SHA-256 | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` |

## Paired source coverage

Source line references are one-based and refer to the canonical Mojmap trees.

| Slice | 1.21.10 | 1.21.11 | Source fact |
|---|---|---|---|
| Player tick calls pose update | `world/entity/player/Player.java:290` | same line | Both call `updatePlayerPose()` after `super.tick()` and the intervening player tick work. |
| Pose selection and fit order | `Player.java:352-384` | `Player.java:352-384` | Bodies match: require the SWIMMING-pose fit predicate first; for the fixed CROUCHING desired-pose input, retain it for spectator/passenger or if its fit predicate passes; otherwise choose CROUCHING if that fit passes, else SWIMMING; write selected pose with `setPose`. Only the ordinary non-spectator, non-passenger case is in scope. The outer SWIMMING value is a fit candidate gate, not an audit of swimming state. |
| Pose writer and synchronous callback | `world/entity/Entity.java:412`; `world/entity/Entity.java:3193-3196`; `network/syncher/SynchedEntityData.java:56-68` | `Entity.java:413`; `Entity.java:3212-3215`; `SynchedEntityData.java:56-68` | `setPose` updates DATA_POSE. The setter calls `onSyncedDataUpdated` only when the value changes; Entity routes DATA_POSE to `refreshDimensions()`. |
| Dimensions by pose | `world/entity/LivingEntity.java:3429-3434` | `LivingEntity.java:3561-3566` | Only SLEEPING selects a separate dimension constant. For non-sleeping poses, default dimensions ignore the Pose argument and use the entity type size times age scale, then entity scale. |
| Player entity size | `world/entity/EntityType.java:1164-1171` | `EntityType.java:1191-1198` | PLAYER is registered with scalable width 0.6, height 1.8, eye height 1.62. With ordinary player scale and age scale equal to 1, every non-sleeping pose has effective width 0.6 and height 1.8; eye height is 1.62. |
| Box construction | `world/entity/EntityDimensions.java:15-20` | same lines | At position (x,y,z), the box is [x−0.3,x+0.3] × [y,y+1.8] × [z−0.3,z+0.3] for ordinary player scale. |
| Refresh and first box rebuild | `world/entity/Entity.java:3207-3220` | `Entity.java:3226-3239` | Both store new dimensions and eye height before `reapplyPosition()`; refresh''s later growth/fudge condition excludes `Player`. |
| Reposition helper | `world/entity/Entity.java:457-459` | `Entity.java:458-461` | 1.21.10 calls `setPos(current x,y,z)`; 1.21.11 first clears `lastKnownPosition`, then calls the same `setPos`. That call rebuilds the AABB from the already-stored dimensions. |
| Speed-state consumer bound | no `computeSpeed`/known-speed field in `Entity.java` | `world/entity/Entity.java:487-488,540-546,3918-3919`; `client/player/LocalPlayer.java:1088-1095` | 1.21.11 base tick computes known speed, and clearing its remembered position makes the next delta zero. The examined LocalPlayer auto-jump movement predicate uses input vector instead. Generic known-speed access is used by vehicle/kinetic-weapon paths, which are outside this fixed ordinary-player slice. |

## Source file SHA-256 (all match source manifests)

| Relative source file | 1.21.10 | 1.21.11 |
|---|---|---|
| `world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| `world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| `world/entity/Entity.java` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| `world/entity/EntityType.java` | `11bc0dcc9a447bcc4fb369a29f78f76cbe9cd5b4eb5f05c8a0fa520c9ac66bab` | `fc2abb3e905b9a63a27f0b027fad089d9d40e5d3d45608406b5da2a5ec47a73e` |
| `world/entity/EntityDimensions.java` | `9fb9575ec615d4aefe6316c443e87a902f44e955393778a4b93623e8b680adc4` | `9fb9575ec615d4aefe6316c443e87a902f44e955393778a4b93623e8b680adc4` |
| `world/entity/Pose.java` | `6c5440c2b4c7790baf36e008c2b355940431d641cc69a0476619fe38c5e113f7` | `6c5440c2b4c7790baf36e008c2b355940431d641cc69a0476619fe38c5e113f7` |
| `network/syncher/SynchedEntityData.java` | `755998494338969b6d47b91c463057d9a009d6804e8d06b0ab30719634cc9234` | `11be676dee1c107c33e8266877e2b9098decfc567f84cd6a1726206784da2dd5` |