# Residual neighbor pushability overrides

Compared the five remaining `isPushable` override families named in the residual assignment against exact Mojmap sources for 1.20.4, 1.20.5 and 1.20.6. Inputs are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{version}\mojmap`; the assigned inventory is `.native-source/20261030/1204-1206-pool44/whole-pair-inventory/three-way-java-path-inventory.csv`. Whole-file hashes below are the inventory's exact SHA-256 values. Method-body hashes are SHA-256 over exact UTF-8 source substrings from declaration through matching closing brace, including whitespace.

## Result

No player movement delta was found. In the normal client `LivingEntity.pushEntities` route, the queried candidate class is `Player`, and `EntitySelector.pushableBy(caller)` evaluates that candidate. The neighbor's `isPushable()` override is not used as the client query gate. Once a local player receives `Player.push(neighbor)`, `Entity.push` applies the player's impulse under the player's own vehicle, `noPhysics`, displacement and pushability gates; the neighbor's `isPushable()` value controls whether the neighbor receives its opposite impulse. Special `doPush` overrides below can suppress or replace the call, and those were checked as well.

| Neighbor type and source member | Player-path result |
|---|---|
| `ambient/Bat.java`: `isPushable()` returns `false`; `doPush` and `pushEntities` are empty. A bat does not issue the local-player push call. |
| `decoration/ArmorStand.java`: `isPushable()` returns `false`; `doPush` is empty. Its `pushEntities` override only queries `RIDABLE_MINECARTS` and invokes `minecart.push(armorStand)`, so it does not target the player. |
| `animal/Parrot.java`: `isPushable()` returns `true`; `doPush` delegates only when the candidate is not a `Player`. It suppresses the player response. |
| `animal/horse/AbstractHorse.java`: `isPushable()` returns `!this.isVehicle()`. It inherits `LivingEntity.pushEntities` and `doPush`, which can call the local player's inherited `push(Entity)` on client contact. The vehicle gate affects the horse's opposite impulse, not the player's impulse; being passengers of the same vehicle rejects the pair at `Entity.push`. |
| `monster/warden/Warden.java`: `isPushable()` returns `!this.isDiggingOrEmerging() && super.isPushable()`. Its `doPush(Player)` records the Warden touch cooldown/anger/disturbance state and does not call `super.doPush` or change player velocity/position. |

All listed method bodies are identical across the three versions; no type-specific gate or response changed within this interval.

## Source identities and body hashes

The source file hashes below come from the assigned three-way inventory; every row is `changed` for 1.20.4→1.20.5 and `same` for 1.20.5→1.20.6.

| Mojmap relative file | SHA-256 1.20.4 | SHA-256 1.20.5 and 1.20.6 |
|---|---|---|
| `net/minecraft/world/entity/ambient/Bat.java` | `ffe4485fd2d23bb15b8fc7c77b40aa2d7f36be4c8a538851923af8aca9088b80` | `ab9c2dd862e01b6e9e7c293c5f0c32f8b9f4c7b18dc24468fc08a38724b14a20` |
| `net/minecraft/world/entity/decoration/ArmorStand.java` | `f601186e6d39b83621f3984945b211ea3830f6ba21fc88c426c70170fab8a143` | `85f2722b5dd6fad2d4c75c5dcdaf8ffa99123a8eca0c47ec4f7a03a492f3a21b` |
| `net/minecraft/world/entity/animal/Parrot.java` | `fe9414a6fbc297b3d714d4faf53d8908c9295b4b7c64d52aa5b6dfb0cb78d297` | `0dbfc3f7c916cd9c6f9e4aa598e2c966d382a3d0d5b95bedb1b64a656a796c5e` |
| `net/minecraft/world/entity/animal/horse/AbstractHorse.java` | `2d8bdd4ac7eb0a1ac2d2c5de31a9656729ea030cd7b48654a705725cba4c3cdd` | `b67090c3ee44cf5cdf880dc99b7f599f8b8c0c5752447dae6ee5c513d60341d0` |
| `net/minecraft/world/entity/monster/warden/Warden.java` | `e2f6543cc2e55fe75e5792e636874672641ca0f85914aad9fa74a8f4488c800b` | `d8f66fcf4862a3fdb7a17c2b76d08f09595c1fd5424e3fc68d08fc4c4826b56e` |

| Member | Lines (.4; .5/.6) | SHA-256 in each version |
|---|---:|---|
| `Bat.isPushable()` | 87; 85 | `0fef6371efa441af58da46a410872b77edc82a444ff0af1539be9efa5b92aaca` |
| `Bat.doPush(Entity)` | 92; 90 | `b6badbf6ad7263cd17747b5a72a1c0d3e587239c9e3d0a89480e02aa7d09b725` |
| `Bat.pushEntities()` | 96; 94 | `837cc4e3a74761dd2aade388d90ab1213e9cac186479bb7d113ffc41592ddf67` |
| `ArmorStand.isPushable()` | 281; 284 | `0fef6371efa441af58da46a410872b77edc82a444ff0af1539be9efa5b92aaca` |
| `ArmorStand.doPush(Entity)` | 286; 289 | `b6badbf6ad7263cd17747b5a72a1c0d3e587239c9e3d0a89480e02aa7d09b725` |
| `ArmorStand.pushEntities()` | 290; 293 | `5e164122d46b48a7ba08d18c94bf6af9618a842cc4638bee2ec7b8d737e0c048` |
| `Parrot.isPushable()` | 392; 372 | `4de529c0a71cfb55ebd599f170c0b037d06709a089ad2d54bb7b330609d0d86c` |
| `Parrot.doPush(Entity)` | 397; 377 | `03242cb2cd35cb0ced48cea63a2688dde09d4870c767a83f73efe53a5562f63f` |
| `AbstractHorse.isPushable()` | 266; 279 | `86f5202977822351b8dc57cd799d047bebf392ff330fb7d405c3822d1bff8774` |
| `Warden.isPushable()` | 533; 531 | `905156dfd9dcbce884e33deb1f95007b3ce48f34a3c68b87bdead89fdaa5a527` |
| `Warden.doPush(Entity)` | 538; 536 | `20af0950fd9dbfa9c8a8b018ec9086fad0aa9e15dfdc0051d386aee1511b144a` |

This closes only the named pushability-override residual for the assigned versions. It does not claim full tick or pair coverage and provides no runtime parity evidence.
