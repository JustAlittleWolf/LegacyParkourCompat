# 1.21.10 → 1.21.11: position-derived speed cache consumer trace

**Verdict: closed negative for ordinary direct non-riding local-player locomotion.** 1.21.11 adds a position-delta cache, but the exact source inventory shows no eligible ordinary local-player movement-state consumer. The only observable consumers are a modern-only kinetic-weapon path and a non-player AI goal whose player case is vehicle-following. No canonical movement finding is warranted for this slice.

## Source identity and integrity

Both exact Mojmap source sets are marked `ready`. Their ready markers identify versions `1.21.10` and `1.21.11`, namespace `mojmap`, Vineflower, Java 25, and exact source-manifest digests `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` and `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`. Those hashes match the corresponding `mojmap.sources.sha256` files. The ready-recorded artifact manifest digest also matches on both sides: `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`.

Each following source digest was recomputed from the exact file bytes and matches that release's source manifest:

| Mojmap source | 1.21.10 SHA-256 | 1.21.11 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| `net/minecraft/world/entity/Avatar.java` | `6cfd006480fa708ec420a3d20a6207a49c515a6cfce2fe2fbf19b4b1ec633f17` | `a3f54b9ff81ee203f77efcf5dcab50d69ec9a3d2c5430ce4c4b2ae0abb4bfca9` |
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| `net/minecraft/world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `052e8387ace3ee65ea7456af23b8e9197931e5d5faeda54589166dce60269296` | `afe84ab1491a17aeeccf895ca4ecb17e941b8e517c980e31281b3d463fc593bd` |
| `net/minecraft/client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |
| `net/minecraft/network/syncher/SynchedEntityData.java` | `755998494338969b6d47b91c463057d9a009d6804e8d06b0ab30719634cc9234` | `11be676dee1c107c33e8266877e2b9098decfc567f84cd6a1726206784da2dd5` |
| `net/minecraft/world/entity/ai/goal/FollowBoatGoal.java` | `fd6c297a974ac9e4a5704763bf2f31944a9ef7fc982375adcc2981268bebdf0f` | `5709c8043eb518b801ac7fd1d711aed17e0a57603a16ce546f6d90f77ca54d67` |
| `net/minecraft/world/item/component/KineticWeapon.java` | absent | `c24230e6d3ba0abcae8b23be898a17aae658867d7136b0a2388b815bd2cc443b` |
| `net/minecraft/server/level/ServerPlayer.java` | `9599b6e7a5d457ac2bdbcae3c13c8c3a2436ad60e447fe00ea2e75546be9444d` | `5cd23824185202d9367674c9c208415f0f5f9922a247f0749c28849e295694b0` |
| `net/minecraft/world/entity/vehicle/minecart/AbstractMinecart.java` | absent | `7c5257f58d71fc8c1648773198d88af5eccf9c942e39ae61dc46e5a08ebf82ed` |

## Reachable sampling and resets

1.21.10 `Entity.tick()` calls `baseTick()` (`Entity.java:475–477`); `Entity.baseTick()` contains no speed sample or `lastKnownPosition` state. Its `reapplyPosition()` only reapplies the current coordinates (`Entity.java:457–459`). A whole-source search found no `computeSpeed`, `lastKnownPosition`, `lastKnownSpeed`, `getKnownSpeed`, or `hasMovedHorizontallyRecently` in the 1.21.10 Mojmap Java tree.

In 1.21.11, `Entity.java:279–280` introduces `lastKnownSpeed = Vec3.ZERO` and nullable `lastKnownPosition`. `Entity.baseTick()` calls `computeSpeed()` first (`:484–487`); `computeSpeed()` seeds a null baseline from `position()`, assigns `position().subtract(lastKnownPosition)` to the cache, then stores the current `position()` as the next baseline (`:540–547`). `AbstractMinecart.tick()` also calls it (`AbstractMinecart.java:260–272`), outside this player-only slice.

For the ordinary local player the inheritance route is `LocalPlayer extends AbstractClientPlayer` (`LocalPlayer.java:105`), `AbstractClientPlayer extends Player` (`AbstractClientPlayer.java:26`), `Player extends Avatar` (`Player.java:124`), `Avatar extends LivingEntity` (`Avatar.java:12`), and `LivingEntity extends Entity` (`LivingEntity.java:145`). The tick route reaches `Entity.baseTick()` through `LocalPlayer.tick()` → `AbstractClientPlayer.tick()` → `Player.tick()` → `LivingEntity.tick()` → `Entity.tick()`; `LivingEntity.tick()` calls `aiStep()` after `super.tick()` (`LivingEntity.java:2610–2651`). Thus `computeSpeed()` samples the player's position before this tick's `aiStep`/movement work; its displacement is available to readers on subsequent execution.

The only direct reset of `lastKnownPosition` is `Entity.reapplyPosition()` (`Entity.java:458–461`), which nulls the baseline before `setPos`. Its callers in this source are `Entity.snapTo` (`:1669–1675`), entity loading and reposition-after-load (`:2032–2048`), `Entity.teleportSetPosition` (`:3022–3032`), `Entity.refreshDimensions` (`:3226–3242`), `LivingEntity.setPosToBed` (`LivingEntity.java:277`), and `Player.die` (`Player.java:536–543`). `lastKnownSpeed` itself is not reset there; after a null-baseline reset, the next `computeSpeed()` initializes the baseline to the current position and then subtracts that same position, replacing speed with zero.

Pose is included as a reset boundary: `Player.tick()` calls `updatePlayerPose()` after `super.tick()` (`Player.java:265–290`); the pose updater calls `setPose` (`:352–365`). `SynchedEntityData.set()` notifies `onSyncedDataUpdated` when a value changes (`SynchedEntityData.java:56–67`); `Entity.onSyncedDataUpdated(DATA_POSE)` calls `refreshDimensions()` (`Entity.java:3211–3215`), which calls `reapplyPosition()` (`:3226–3232`). An unchanged pose does not trigger this setter callback.

## Exhaustive cache reader and dispatch inventory

The 1.21.11 whole-source identifier search found these cache reads and only these:

- `Entity.hasMovedHorizontallyRecently()` reads `lastKnownSpeed.horizontalDistance()` and compares its absolute value to `1.0E-5F` (`Entity.java:850–852`). The only call sites are `FollowBoatGoal.canUse()` and `canContinueToUse()` (`FollowBoatGoal.java:22–45`): this is a non-player AI goal; its player branch checks a boat's controlling passenger or an already-followed passenger. Its output drives mob goal choice and is in the excluded non-player/vehicle path, not ordinary local-player locomotion.
- `Entity.getKnownSpeed()` selects a live controlling passenger's `Player.getKnownSpeed()` or this entity's `lastKnownSpeed` (`Entity.java:3918–3920`). For an ordinary non-riding `LocalPlayer`, it selects the local entity cache. The sole call site in the complete 1.21.11 tree is `KineticWeapon.getMotion()` (`KineticWeapon.java:78–84`), which scales the value by 20. `KineticWeapon` consumes that motion in its contact/damage path (`:95–129`); this is a modern-only item, explicitly outside historical feature scope. `ServerPlayer.getKnownSpeed()` is an override backed by `lastKnownClientMovement` (`ServerPlayer.java:2145–2149`), not a reader of `Entity.lastKnownSpeed` for ordinary local movement.

There is no ordinary local-player movement-state consumer between the sampled cache and player movement. In particular, `LocalPlayer.canAutoJump()`'s `isMoving()` checks the input move vector (`LocalPlayer.java:1082–1094`), not this cache. Attribute/travel speed, rendering and networking paths are not cache readers. This conclusion is bounded to the exact 1.21.10→1.21.11 Mojmap endpoints and the source inventory above; it does not establish intermediate patch introduction timing or runtime parity.