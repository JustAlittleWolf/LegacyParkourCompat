# Ordinary-input witness attempt: 1.13.2 → 1.14.4

**Result.** The input-to-requested-delta gap is closed for a one-keyboard-tick witness of the resolved squared-distance cutoff. A standing player with zero prior velocity presses Forward once while grounded on stone; the paired `LivingEntity` source math sends approximately `(0,0,0.098)` to `Entity.move(SELF, …)` in each endpoint. A full-cube wall clips that to approximately `(0,0,0.0001)`: 1.13.2 applies the residual, while 1.14.4 rejects the position update because its squared length is below the strict `1.0E-7` gate.

**Limit.** The source trace closes the ordinary keyboard-to-SELF-delta route on this tick, without `setVelocity` or teleport. The exact initial player offset used by the static fixture (`zMax=0.9999`) remains an initial condition from the permitted collision-query closure. These source files do not establish a preceding ordinary input history that lands at that exact offset, so this is not a full spawn-to-divergence trajectory claim.

## Paired same-tick input and velocity trace

Preconditions for both endpoints: local, standing, non-riding player; yaw `0°`; prior velocity `(0,0,0)`; `onGround=true`; no jump, sneak, item-use, sprint, status effect, autojump, or entity interaction; default walk-speed attribute `0.1F`; stone beneath the player (slipperiness `0.6F`); and ordinary Forward keyboard key pressed. The option `autoJump=false` is set in both endpoints so post-move autojump cannot add a jump response. The current tick is an ordinary local-player `mobTick`, not an injected movement call.

| Tick stage | 1.13.2 | 1.14.4 |
| --- | --- | --- |
| Keyboard sampling | `KeyboardInput.tick()` reads Forward as `movementForward=1` (`KeyboardInput.java:13–49`). | `tick(bl,bl2)` reads Forward as `movementForward=1` (`KeyboardInput.java:13–25`). |
| Input reaches living travel | Local player samples input at `LocalClientPlayerEntity.java:698–707`; `serverTickAi` copies `movementSideways`/`movementForward` into entity speeds at `:635–645`. | Local player samples input at `LocalClientPlayerEntity.java:625–636`; `serverTickAi` copies the speeds at `:602–610`. |
| Same-tick travel scaling | `LivingEntity.mobTick` multiplies `forwardSpeed` by `0.98F`, then calls `moveRelative(sidewaysSpeed, verticalSpeed, forwardSpeed)` (`LivingEntity.java:1920–1926`). | It performs the same `0.98F` scaling and calls `moveRelative(new Vec3d(sidewaysSpeed, verticalSpeed, forwardSpeed))` (`LivingEntity.java:2221–2227`). Thus the request vector entering velocity acceleration is `(0,0,0.98F)`. |
| Ground acceleration | Stone gives `t=0.6F×0.91F=0.546F`. The ground coefficient is `getSpeed()×(0.16277137F/(t³))` (`LivingEntity.java:1538–1553`); with speed `0.1F`, the coefficient rounds to about `0.1F`. | The ground coefficient is `getSpeed()×(0.21600002F/(0.6F³))` (`LivingEntity.java:1841–1846,1961–1963`); with speed `0.1F`, it is `0.1F`. The stone default is `0.6F` (`Block.java:817`). |
| SELF move | `updateVelocity` normalizes only if input magnitude is at least one; `0.98F` remains `0.98F`. At yaw zero, the forward/upwards component rotates to `+Z`; request is approximately `(0,0,0.09799999)`. Travel passes velocity to `move(MoverType.SELF, …)` at `LivingEntity.java:1575`. | `updateVelocity` keeps a vector whose squared magnitude is below one, then scales by the ground coefficient (`Entity.java:1062–1076`). At yaw zero, request is approximately `(0,0,0.0980000034)`. Travel calls `move(MoverType.SELF, currentVelocity)` at `LivingEntity.java:1841–1846`. |

Player speed is initialized to `0.1F` in both `PlayerEntity.java:156–160` (1.13.2) and `:188–194` (1.14.4). The two acceleration formulas differ only by their float evaluation at this input; both requests exceed the fixture's `0.0001` gap by roughly three orders of magnitude. The vertical input and prior vertical velocity are zero; gravity is applied after SELF movement in these dry branches.

The input sample is not sneak-scaled: the keyboard only applies its `0.3` factor when sneaking in 1.13.2 (`KeyboardInput.java:44–49`), and the 1.14.4 flags do not request either sneak or pose slowdown (`KeyboardInput.java:18–25`). Neither player is using an item, so the local-player `0.2F` item-use factor is inactive (`LocalClientPlayerEntity.java:704–707` and `:632–636`). Sprint is false and the sprint key is released; the initial double-tap sprint timer is expired, so this input only arms the timer and does not add the sprint modifier. Autojump is disabled in the client option, which feeds the local player's `autoJumpEnabled` field (`LocalClientPlayerEntity.java:254–261`); `autoJump` is guarded by `isAutoJumpEnabled()` (`:878–885` in 1.13.2; `:853–860` in 1.14.4).

## Collision fixture and paired result

Initial player position: center `x=0.5`, feet `y=1`, center `z=0.6999`, standing dimensions `0.6F×1.8F` as in the permitted closure. That float width gives a Z footprint of approximately `[0.3998999881,0.9999000119]` (and X footprint approximately `[0.1999999881,0.8000000119]`). `onGround=true`; horizontal and vertical velocity are zero. The only intended collision candidate on the requested path is a vanilla base full-cube block at `(0,1,1)`, with a second at `(0,2,1)` to make a two-block-high wall. The initial positive-Z face gap is approximately `1−0.9999000119 = 0.0000999881` blocks. Add a stone floor at y=0 under x=0,z=0..1 and a ceiling at y=3 at x=0,z=0..1. All chunks are loaded; no entity shape intersects the swept box; the fixture is far from the border.

The floor is stone (`Blocks.java:626` in 1.13.2; `:22` in 1.14.4); regular block properties keep the default slipperiness `0.6F` (`Block.java:1905–1908` and `:817–819`). The wall/roof stone collision shape is the unit block, returned by the base block provider when collision is enabled (`Block.java:388–395` in 1.13.2; `:371–378` in 1.14.4). The permitted collision-query closure records the world candidate routes, loaded-chunk/border/entity exclusions, and endpoint hashes for this generic full-cube provider (`.native-source/20261030/1132-1144-remaining34/collision-query/closure-report.md`, SHA-256 `30670ce15cba31f5dc7ced918ae9911dd68ace3e9b5fcb1b7b2230d91a872aa8`).

The requested positive-Z move exceeds the gap, so `calculateMaxOffset` limits Z to the wall face. The step-height route cannot improve horizontal progress: both versions give LivingEntity step height `0.6F`; the ceiling permits at most `0.2` upward clearance, and the raised body still intersects the wall spanning y=1..3. The original clipped route therefore wins the existing horizontal-distance comparison (1.13.2 `Entity.java:601–672`; 1.14.4 `:669–685`). The resolved vector in each version is `(0,0,gap)` with `gap≈9.9988E-5`, and its squared distance is approximately `9.9976E-9`.

- **1.13.2:** `Entity.move` resolves nonzero requested movement, clips Z, moves the shape by the nonzero residual, then unconditionally calls `setPositionFromShape()` (`Entity.java:578–599,676–680`). The player position advances by the gap.
- **1.14.4:** `Entity.move` resolves the same residual (`Entity.java:463–468,656–666`) but calls shape/position update only when `resolved.squaredDistanceToOrigin() > 1.0E-7` (`:465–468`). Since `~1.0E-8` is below the strict threshold, position is unchanged for this tick.

## Evidence and hashes

Line references are relative to each exact `ornithe-feather` source root. Hashes are SHA-256 of the complete decompiled Java file. The collision-query closure is a permitted immutable input; its source-hash table covers its query/provider evidence. The following are the exact files used in the input and cutoff trace:

| Java source | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/entity/living/player/KeyboardInput.java` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` | `5932453a9e48e7a798ae1be1cd3a4bf660b6b3be43bb7e5dc22686b3c4a82526` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` | `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` | `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df` |
| `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` |
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/util/math/MathHelper.java` | `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69` | `e39d5dfc69c17a9032be086d7d4d14cc63525962a0ff83615b1befdcca599843` |
| `net/minecraft/block/shape/VoxelShapes.java` | `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95` | `51a09c297c7f01c8bad9b66af2a80900e95f33c6e3a250ce599f4817433587d9` |

No source generation, decompilation, build, test, game launch, or implementation change was performed. This closes only one ordinary keyboard-to-resolver witness on the fixed fixture; it does not prove the exact fixture start position's prior reachability or close the pair-wide collision inventory.
