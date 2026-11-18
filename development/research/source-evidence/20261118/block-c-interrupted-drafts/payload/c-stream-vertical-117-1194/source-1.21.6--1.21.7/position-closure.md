# Position writer reachability closure: local passive air movement

## Source pins

- 1.21.6 Mojmap manifest `decompiled_minecraft/1.21.6/mojmap/.decompile-provenance.json`: SHA-256 `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e`. Client JAR input SHA-256 `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a`; Mojmap client mappings SHA-256 `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261`.
- 1.21.7 Mojmap manifest `decompiled_minecraft/1.21.7/mojmap/.decompile-provenance.json`: SHA-256 `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d`. Client JAR input SHA-256 `6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9`; Mojmap client mappings SHA-256 `328f2c5849c7c5fc4f2b1eae4f74a7cfe6bb337c7938242ac5c4f9eac89b1e50`.
- Each manifest hash matches the assigned exact identity. The cited source files below match both manifests' per-file source hashes and are byte-identical across releases.

| Mojmap source file | SHA-256 in both releases |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `b10c8a80de875262ffc8c95614f8e6eeff187ded7c042261ffde993230479d2e` |
| `net/minecraft/world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `net/minecraft/world/entity/LivingEntity.java` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| `net/minecraft/world/entity/Entity.java` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `a66556678a93c63c0ceb8953f25261b82bf3bb1971048fbc15b177c6dffea8cd` |
| `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` | `658e5968d7c83bd6b176e7b061078e0def68abcc2558f7d9ffd59640a88f317f` |

## Inherited tick and virtual dispatch

- `AbstractClientPlayer.java:23-24` establishes `AbstractClientPlayer extends Player`; `LocalPlayer.java:97` establishes `LocalPlayer extends AbstractClientPlayer`; `Player.java:128` establishes `Player extends LivingEntity`.
- The tick chain is real inherited dispatch: `LocalPlayer.tick` calls `super.tick` (`LocalPlayer.java:197-202`); `AbstractClientPlayer.tick` updates prior-tick velocity then calls `super.tick` (`AbstractClientPlayer.java:50-56`); `LivingEntity.tick` calls `aiStep` when present (`LivingEntity.java:2511-2553`). `LocalPlayer.aiStep` reaches `super.aiStep` and then returns (`LocalPlayer.java:682-835`); `LivingEntity.aiStep` reaches the travel block (`LivingEntity.java:2804-2819`).
- On a local client, dispatch gates are true. `LocalPlayer.isLocalPlayer()` explicitly returns true (`LocalPlayer.java:341-344`). `Player.canSimulateMovement()` and `Player.isEffectiveAi()` each return `!level.isClientSide || isLocalPlayer()` (`Player.java:1332-1340`), so both pass for the local player. `Entity.isLocalInstanceAuthoritative()` calls `isLocalClientAuthoritative()` on a client (`Entity.java:3335-3342`), and the Player override delegates to `isLocalPlayer()` (`Player.java:1323-1325`). The no-override default `Player.isLocalPlayer()` is false (`Player.java:1328-1330`), but it is overridden by LocalPlayer; it does not suppress this local path.
- Dynamic travel dispatch from `LivingEntity.aiStep` is `this.travel($$10)` (`LivingEntity.java:2815-2819`); LocalPlayer and AbstractClientPlayer have no travel override. It therefore selects `Player.travel` (`Player.java:1438-1461`), whose ordinary nonpassenger, nonswimming, nonflying branch calls `super.travel($$0)` at 1458. `LivingEntity.travel` selects `travelInAir` when fluid and fall-flying gates are false (`LivingEntity.java:2245-2254`). That air method updates velocity and ends after `setDeltaMovement` (`LivingEntity.java:2276-2299`); there is no call to `move` in this method, `LivingEntity.travel`, or the ordinary branch of `Player.travel`.

## Position consumer and boundary

- `Entity.move(MoverType, Vec3)` is the inherited position/collision writer: its no-physics branch adds the request to position; the ordinary branch collision-resolves and calls `setPos` (`Entity.java:663-698`). `LocalPlayer` overrides `move` and delegates to `super.move`, then updates horizontal auto-jump/walk distance (`LocalPlayer.java:916-924`). This override proves how virtual dispatch would work if an inherited air path called `move`; the ordinary path above never makes that call. The separate `LivingEntity.travelFlying` and `travelInFluid` branches contain their own move calls but are explicitly outside this review.
- The ordinary local tick proceeds from `super.tick()` to packet sending (`LocalPlayer.java:197-216`); `sendPosition` serializes the current `getX/Y/Z` values and does not integrate velocity (`LocalPlayer.java:234-270`). A position packet can write position through `ClientPacketListener.handleMovePlayer` and `setValuesFromPositionPacket(..., false)`, which uses `setPos` and sets packet delta movement (`ClientPacketListener.java:768-798`). That is an incoming server correction/teleport path, outside local passive tick integration; it is not the missing per-tick consumer.

## Verdict

The prior gap is closed as a **negative reachability finding**: with the stated ordinary local dry, nonvehicle, non-gliding/nonflying gates, the vanilla local tick reaches the ordinary air velocity update, and both local simulation predicates are true, but no inherited or virtual call consumes the resulting Y velocity into position on that path. A LocalPlayer-specific absence alone would not establish this; the complete inherited travel chain and dispatch predicates were checked. Position can be changed by the separate incoming player-position packet handler, but that is network correction, not ordinary passive air integration. The same call bodies and verdict apply to both exact pinned releases. This closes only the assigned reachability boundary; it does not assert a general absence of all other direct position updates or establish tick behavior by runtime execution.

