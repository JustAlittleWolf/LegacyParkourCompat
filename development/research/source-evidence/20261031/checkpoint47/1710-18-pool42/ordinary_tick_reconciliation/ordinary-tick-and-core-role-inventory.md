# 1.7.10 → 1.8 source tick-path reconciliation

## Exact source identities

Both read-only canonical Ornithe Feather trees were rehashed against their embedded provenance manifests before comparison. `1.7.10/ornithe-feather`: manifest SHA-256 `11bada441ebe07661402d1c40e1c490b78b279ecaa084f9a4dc3ae2dd54caf6f`, 1,803 listed Java files and 1,803 present Java files, no file-hash mismatch; raw client SHA-256 `a4fc2284657544e0f4bcc964f927c2fda3e3a205178ed1d5d58883aaf9780cce`, Feather build.2 artifact SHA-256 `1a22781aa3bac248e25c619842ec2ec5c126d015436d528d411a9e85a6f491a7`. `1.8/ornithe-feather`: manifest SHA-256 `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05`, 2,497 listed Java files and 2,497 present Java files, no file-hash mismatch; raw client SHA-256 `ccccd0a92f76fbb24279691daad9b67f85d80471a37e59e0da76d7754982dfe8`, Feather build.2 artifact SHA-256 `3c020d1588b4aab403b5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe`.

The manifest member identities match these exact case-sensitive source paths. Mapping family: Ornithe Feather on both sides.

## Ordinary client tick graph

1. `net/minecraft/client/Minecraft.java` enters `world.tickEntities()` (1.7.10 line 1520; 1.8 line 1594).
2. `net/minecraft/world/World.java` iterates entities and dispatches through `tickEntity` (1.7.10 `tickEntities` line 1241, `tickEntity` line 1395; 1.8 lines 1139 and 1306). `tickEntity` only ticks an entity in a loaded area/chunk and chooses `rideTick()` when it has a vehicle, otherwise `tick()` (1.7.10 lines 1399–1418; 1.8 lines 1310–1329).
3. Normal local-player dispatch is `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.tick()` (1.7.10 line 69; 1.8 line 111), which gates on the player's chunk, calls `super.tick()`, and sends movement/input packets afterward. Packet serialization itself is not an ordinary movement update.
4. 1.7.10 class roles: `client/entity/living/player/LocalClientPlayerEntity` is networking wrapper; `InputClientPlayerEntity` reads keyboard/controller `Input`, updates sprint/fly/sneak/jump/riding decisions in `mobTick`, copies movement axes/jump edge in `aiTick`, and overrides `isLocallyControlled()` true (line 492); `ClientPlayerEntity` is primarily the textured client-player abstraction. 1.8 merges the input-driven client-player role into `LocalClientPlayerEntity` (including `serverTickAi`, `mobTick`, and `isLocallyControlled()` true at line 389); `InputClientPlayerEntity.java` is absent, while `ClientPlayerEntity` remains the parent class. This class-role relocation is not by itself a movement delta.
5. Both versions dispatch through `entity/living/player/PlayerEntity` to `entity/living/LivingEntity`: `LivingEntity.tick()` invokes `mobTick()` (1.7.10 line 1224; 1.8 line 1255). Player overrides `mobTick` (1.7.10 line 411; 1.8 line 425), then LivingEntity's local-control branch feeds input/AI, the jump branch, and then `moveRelative(sidewaysSpeed, forwardSpeed)` (1.7.10 `LivingEntity.mobTick` line 1350; 1.8 line 1381). Player `moveRelative` applies player/flying-specific adjustment and delegates to LivingEntity (1.7.10 `PlayerEntity` line 1237; 1.8 line 1277). `LivingEntity.moveRelative` chooses walking/water/lava handling, applies acceleration and gravity/friction, and calls `Entity.move` (1.7.10 line 1095; 1.8 line 1115).
6. `entity/Entity.move` clips the requested X/Y/Z against `World.getCollisions`, evaluates stepping, writes the settled shape/position and collision/on-ground flags, and routes movement contacts/step/fall checks (1.7.10 line 383; 1.8 line 370). `entity/Entity.baseTick` and `world/World`/`block/Block` callbacks feed contact/liquid state back to subsequent movement ticks. These paired caller/provider routes are covered by the focused lanes.
7. For native player-state corrections, the additional direct route is `client/network/handler/ClientPlayNetworkHandler.handlePlayerMove(PlayerMoveS2CPacket)`; paired protocol class paths are `network/packet/s2c/play/PlayerMoveS2CPacket.java`. This route is distinct from the ordinary client-generated tick and is a movement response, not packet event production.

## Case-sensitive core role inventory

| Role | 1.7.10 exact source path | 1.8 exact source path |
|---|---|---|
| Game-loop owner | `net/minecraft/client/Minecraft.java` | `net/minecraft/client/Minecraft.java` |
| Entity tick routing / loaded-area gate / collision query provider | `net/minecraft/world/World.java` | `net/minecraft/world/World.java` |
| Client world implementation and client entity membership | `net/minecraft/client/world/ClientWorld.java` | `net/minecraft/client/world/ClientWorld.java` |
| Shared position/box/velocity/collision/contact base | `net/minecraft/entity/Entity.java` | `net/minecraft/entity/Entity.java` |
| Living tick, jump, travel, effect reads and movement | `net/minecraft/entity/living/LivingEntity.java` | `net/minecraft/entity/living/LivingEntity.java` |
| Player jump, abilities, attributes, movement wrapper | `net/minecraft/entity/living/player/PlayerEntity.java` | `net/minecraft/entity/living/player/PlayerEntity.java` |
| Local tick, player correction/packet send, local input decisions | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` |
| Input-specific player role | `net/minecraft/client/entity/living/player/InputClientPlayerEntity.java` | absent (folded into local-player class) |
| Client player parent | `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` |
| Player correction application | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` |
| Correction packet fields / protocol representation | `net/minecraft/network/packet/s2c/play/PlayerMoveS2CPacket.java` | `net/minecraft/network/packet/s2c/play/PlayerMoveS2CPacket.java` |
| Base block shape/collision/provider defaults | `net/minecraft/block/Block.java` | `net/minecraft/block/Block.java` |
| Block registration and static instances | `net/minecraft/block/Blocks.java` | `net/minecraft/block/Blocks.java` |

## Paired path hashes

| Role | 1.7.10 file SHA-256 | 1.8 file SHA-256 |
|---|---|---|
| `client/Minecraft.java` | `f1161ee015867615ac8f719aa5b1c58ab3b3ffa91382627f751b8d0ac3c28f96` | `3073fca8df5f2c4ef782e521fdf0fc8ed23a74a31d8e589230a6ecc08e9b3ba2` |
| `world/World.java` | `2d648edfe7fdc953825a1df94709e889ea21354494d85762bca49b4a109d01f9` | `afbc701ef8b66020bdbb2831fd80683705d20237c68fbf23a4001c020876fc6d` |
| `client/world/ClientWorld.java` | `e4e3814806ad29fd94f1a25888c199f97900cec68237faff7194faa5ede4652b` | `6886f5682f606427571d559ce15eb4b758e3f3dacac8f1db37da6dbb354086b7` |
| `entity/Entity.java` | `af4ef7b11bc709d96bd3eea1260a61d38a1a04996bdf87f2a77c459ddf070a69` | `2be081abe1720f89d7ab90f782b8411e71bc5b8a6b53966b49a2380e3c47ffd9` |
| `entity/living/LivingEntity.java` | `5c1c5cf82d98c2f57a65b857fe02975b749c0562e4c1437ab5f072e202ca941c` | `645624913e265cad50293f659812a35c3c4303d1e8bdafdcca4eaf2e8ed5c69d` |
| `entity/living/player/PlayerEntity.java` | `350901699e449537372e1c4af6a79ae256de39638cdad049dfc7800b6607791c` | `13fa1367b71035efbfccc593de57838268b10a8ceebb0aa76ad3d819b44cb0ca` |
| `client/entity/living/player/LocalClientPlayerEntity.java` | `11b9c8e9691e434d9f80bdcfae3e5a62143817dc9ae0ca810c05974c8f8f548b` | `42a92a74e5c64992572760196a2052273d96bc061cd9d1120045ba03532f1e2c` |
| `client/entity/living/player/InputClientPlayerEntity.java` | `1e37eeaebb22b759bfcae11c8c08f24b37dff7ebf5f5b17531f92e6cfbb48794` | absent (folded into `LocalClientPlayerEntity.java`) |
| `client/network/handler/ClientPlayNetworkHandler.java` | `bae3baa7bc005b339eac47d800cf391c3e35b0ea0e9162af344793d7d104e4d0` | `1cca0ded9f1112d407b149931c1f067a104d2beed74f1e7070200e8c035a102d` |
| `network/packet/s2c/play/PlayerMoveS2CPacket.java` | `e6b4285a120e4aa75effdcbce847e4f18a21d1abc8f1900c591ed300cb80a565` | `b50a7a2f46c5866d1008801246b69314d8a49654fede657b0f8182741da7d8be` |
| `block/Block.java` | `462ea8943470de83f2d923d1d267bebc6b9af05eecf3c60c5e90eae870394d0b` | `4a4e6dfde24a135ecf87c359fd98f0380664e4bacaf679815c9016cb1ba0d814` |
| `block/Blocks.java` | `b912e4c682f9e1ffecd916f3ee5cc6ff1bba3196e0634e20442b618a0a062e11` | `af42dc7d1fe95a3cc070427e32cd76213d6eab4df49118fee99aa3c7a3a004b0` |

The ordinary path is traced to the movement writer and its principal readers. Exact provider, callback, fluid and state-reader path inventory and individual terminal classifications remain in the assigned independent lanes until reconciled. This artifact does not claim complete pair coverage, source acceptance, or a pair freeze.
