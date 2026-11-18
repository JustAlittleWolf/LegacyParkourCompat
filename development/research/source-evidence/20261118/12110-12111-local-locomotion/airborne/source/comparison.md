# Airborne local locomotion: 1.21.10 to 1.21.11

## Bounded verdict

No source difference found in the assigned ordinary dry airborne local-player path. The compared endpoint methods preserve the same branch conditions, constants, operation order, float/double choices, vector projection, and pre-collision movement request. This is a bounded equivalence result; it is not a full player-tick audit.

## Exact source identity

Both sides are exact Mojmap decompilations. The ready markers identify Minecraft client inputs, mapping namespace, source manifest, and decompiler run; the source hashes below match each ready tree's `mojmap.sources.sha256`.

- 1.21.10: `decompiled_minecraft/1.21.10/mojmap/`; ready marker source manifest SHA-256 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`; client SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`.
- 1.21.11: `decompiled_minecraft/1.21.11/mojmap/`; ready marker source manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`; client SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`.
- Both manifests name mapping `mojmap`, Vineflower, Java runtime 25. Their provenance records Mojang mappings and the exact decompiler inputs. No online mapping lookup was used.

## Compared witnesses

- `net/minecraft/world/entity/player/Player.java`, `travel(Vec3)`: 1.21.10 lines 1295–1317; 1.21.11 lines 1360–1382. Under the assigned non-passenger, nonswimming, noncreative-flying ordinary route, both delegate unchanged input to `LivingEntity.travel`. Swimming and abilities flight are excluded.
- `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)` and `travelInAir(Vec3)`: 1.21.10 lines 2275–2284 and 2306–2329; 1.21.11 lines 2309–2321 and 2343–2366. Fluid selection was extracted into `shouldTravelInFluid(FluidState)` in 1.21.11, whose return expression is exactly the former condition: `(isInWater() || isInLava()) && isAffectedByFluids() && !canStandOnFluid(state)`. On dry air the same `travelInAir` path is reached. It obtains support friction, sets air friction to `1.0F`, and computes the same `0.91F` factor and post-move vertical/friction update. Gravity and drag are outside this assignment; they were not evaluated for parity.
- `net/minecraft/world/entity/LivingEntity.java`, `handleRelativeFrictionAndCalculateMovement(Vec3,float)` and `getFrictionInfluencedSpeed(float)`: 1.21.10 lines 2476–2486 and 2520–2529; 1.21.11 lines 2534–2544 and 2578–2587. Both call `moveRelative` with the same selected speed, run the same climbable handling, then call `move(SELF, deltaMovement)`. In airborne state the conditional selects `getFlyingSpeed()`; `LivingEntity`'s controlling-player passenger case is excluded, so `Player.getFlyingSpeed()` applies.
- `net/minecraft/world/entity/player/Player.java`, `getFlyingSpeed()`: 1.21.10 lines 1867–1873; 1.21.11 lines 1959–1965. With abilities flight off and sprint omitted from the assignment, both select `0.02F`. Sprint's alternate speed is excluded.
- `net/minecraft/world/entity/Entity.java`, `moveRelative(float,Vec3)` and `getInputVector(Vec3,float,float)`: 1.21.10 lines 1590–1605; 1.21.11 lines 1608–1623. Both add the projected input to current velocity. They compute squared input length as `double`, return zero below `1.0E-7`, normalize only above `1.0`, then scale by the `float` acceleration. Yaw sine/cosine are `float` (`Mth.sin/cos(yaw * (float)(PI / 180.0))`); horizontal component arithmetic and the input/result vector components are `double`. Formula/order are identical: `(x*cos - z*sin, y, z*cos + x*sin)`.
- `net/minecraft/world/entity/Entity.java`, `move(MoverType,Vec3)`: 1.21.10 lines 670–714; 1.21.11 lines 685–729. `LivingEntity` passes the same current delta movement as a `SELF` displacement request. In the ordinary physics branch, both then apply edge adjustment and collision before setting position to old position plus the collided vector. The request is the pre-collision witness; this comparison does not assert realized displacement through arbitrary geometry. The no-physics direct position branch also has the same coordinate additions but is not the ordinary route.

## Reachability and dependency limits

A client player on a dry, nonsolid mode-independent input state with `onGround() == false`, not flying by abilities, not passenger, and with ordinary physics follows the compared path when `LivingEntity.travel` is called. Holding an awake tick alive and reaching travel are assumed; input production, prior tick state changes, jump, gravity/drag evolution, collision outcomes, and external velocity responses are not claims here. Vanilla block state and collision geometry remain read-only dependencies. This bounded review found no changed acceleration/speed-selection, projection, or pre-collision displacement-request behavior from 1.21.10 to 1.21.11.

Excluded: grounded acceleration, jump, gravity and drag parity; swimming/crawling/fluid, fall-flying, ability flight, climbing/powder-snow response, vehicle/passenger, turn, sprint/sneak/timers, input production, accepted external responses, and arbitrary collision/shape implementation. No positive finding is warranted for this bounded slice.

## Consumed Java source SHA-256

| Version | Relative source | SHA-256 |
|---|---|---|
| 1.21.10 | `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| 1.21.10 | `net/minecraft/world/entity/Entity.java` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` |
| 1.21.10 | `net/minecraft/world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |
| 1.21.11 | `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| 1.21.11 | `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| 1.21.11 | `net/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |