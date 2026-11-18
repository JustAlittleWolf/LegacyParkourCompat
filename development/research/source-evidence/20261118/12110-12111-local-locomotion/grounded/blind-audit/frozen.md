# Frozen blind audit: grounded ordinary locomotion

## Verdict and boundary

**Scoped same-input equivalence.** For an ordinary player moving on a dry surface, not riding, swimming, flying, fall-flying, climbing, sneaking, sprinting, or jumping, the compared path preserves its input projection and acceleration, player speed read, grounded surface-friction sample and coefficient, and first `MoverType.SELF` displacement dispatch between 1.21.10 and 1.21.11. No reachable difference was found in this slice. The 1.21.11 extraction of the fluid test into `shouldTravelInFluid` is equivalent for the dry precondition.

This is a frozen verdict for the assigned slice, not a full-pair coverage audit. Support-position and block-state providers are treated as boundaries; their geometry and internals, collision response, post-move drag, and all excluded movement contracts remain outside the claim.

## Exact evidence identity

Both canonical trees are marked `ready`, with `versionId` and mapping `mojmap` matching the requested releases. Their ready records identify the client artifacts as:

- 1.21.10 client SHA-256: `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`; source manifest SHA-256: `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`.
- 1.21.11 client SHA-256: `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`; source manifest SHA-256: `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`.

The matching provenance records specify Mojmap, exact version IDs 1.21.10 and 1.21.11, Java `25.0.3+9-LTS`, Vineflower `1.12.0`, and a single pinned batch decompilation run. Each used Java source file below was SHA-256 checked against its side's `mojmap.sources.sha256` manifest; all values matched.

| Source file (Mojmap) | 1.21.10 SHA-256 | 1.21.11 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| `net/minecraft/world/entity/Entity.java` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| `net/minecraft/world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| `net/minecraft/client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `b941c0556d9ed3c3ce1ffd4c993c9902287a690230e1510696ff13f9efdf2020` | `62129028c2f7beb6d2f87bcd3345e63a7be8fc3950a7ecdbc6ee21f8fb89f843` |

## Compared path

1. **Reachable travel gate and player override.** `Player.travel(Vec3)` in both releases delegates to `super.travel` for the ordinary dry, non-riding, non-flying, non-swimming case (1.21.10 `Player.java:1295-1323`; 1.21.11 `Player.java:1360-1388`). `LivingEntity.travel` selects the air branch when its fluid test is false (1.21.10 `LivingEntity.java:2275-2283`; 1.21.11 `LivingEntity.java:2309-2322`). The newer `shouldTravelInFluid(FluidState)` returns the same `(isInWater() || isInLava()) && isAffectedByFluids() && !canStandOnFluid(state)` expression that was inline in 1.21.10. Under the dry precondition, both select `travelInAir`.

2. **Grounded surface sample.** `LivingEntity.travelInAir` first asks `getBlockPosBelowThatAffectsMyMovement()`, then chooses `level().getBlockState(pos).getBlock().getFriction()` when `onGround()`, otherwise `1.0F` (1.21.10 `LivingEntity.java:2306-2310`; 1.21.11 `LivingEntity.java:2343-2347`). The provider remains a boundary (`Entity.getBlockPosBelowThatAffectsMyMovement`, 1.21.10 `Entity.java:938-942`; 1.21.11 `Entity.java:957-961`). No claim is made about how that position is supplied or how block friction is defined.

3. **Speed and acceleration.** For grounded movement `getFrictionInfluencedSpeed(friction)` remains `getSpeed() * (0.21600002F / (friction * friction * friction))`, in the same float operation order (1.21.10 `LivingEntity.java:2520-2521`; 1.21.11 `LivingEntity.java:2578-2579`). `Player.getSpeed()` returns `(float)getAttributeValue(Attributes.MOVEMENT_SPEED)` in both releases (1.21.10 `Player.java:1339-1340`; 1.21.11 `Player.java:1404-1405`); the player attribute builder's base entry remains `.add(Attributes.MOVEMENT_SPEED, 0.1F` (1.21.10 `Player.java:213-216`; 1.21.11 `Player.java:211-214`). `Player.aiStep` also retains its post-`super.aiStep()` speed-cache write from that attribute (1.21.10 `Player.java:469`; 1.21.11 `Player.java:466`); the player's virtual `getSpeed()` reads the attribute directly on the compared acceleration path.

4. **Input projection and accumulation.** `handleRelativeFrictionAndCalculateMovement` supplies the friction-influenced speed to `moveRelative`, passes the resulting delta through the ordinary non-climbing path, then invokes `move(MoverType.SELF, getDeltaMovement())` (1.21.10 `LivingEntity.java:2476-2485`; 1.21.11 `LivingEntity.java:2534-2543`). `Entity.moveRelative` adds `getInputVector(input, scale, getYRot())` to current velocity in both releases (1.21.10 `Entity.java:1590-1593`; 1.21.11 `Entity.java:1608-1611`). `getInputVector` preserves the same gates and order: return zero below squared length `1.0E-7`; normalize only above squared length `1.0`; scale; compute float sine/cosine from yaw in degrees; rotate horizontal components while preserving Y (1.21.10 `Entity.java:1595-1604`; 1.21.11 `Entity.java:1613-1622`).

5. **First SELF dispatch.** `LocalPlayer.move` passes the same `MoverType` and `Vec3` to `super.move` in both releases (1.21.10 `LocalPlayer.java:906-912`; 1.21.11 `LocalPlayer.java:947-953`). The selected call is therefore the same initial `Entity.move(MoverType.SELF, deltaMovement)`; the audit stops at the movement/collision provider boundary and does not assess geometry or subsequent responses.

## Pair of concrete paths and exclusions

Concrete reachable witness: a grounded local player in ordinary dry air travel, not in a vehicle or excluded movement mode, with the same supplied input vector, yaw, movement-speed attribute, on-ground value, and result from the support-position/block-friction providers. Both versions add the same yaw-rotated, friction-scaled input acceleration to current velocity and dispatch that resulting velocity as the first SELF movement request. This is source-level same-input equivalence; it does not claim identical providers or a full tick trajectory when excluded state differs.

Excluded: fluid travel, swimming/crawling, orientation writing, Elytra, abilities/flying, climbing, riding, sprint/sneak state and timers, external movement responses, jump/effect/airborne contracts, support geometry, collision geometry/response, and all post-move friction/drag.