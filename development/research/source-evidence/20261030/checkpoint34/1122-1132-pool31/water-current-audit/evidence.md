# Water/current audit evidence index

All paths below are under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather\`. File digests were computed directly and matched the corresponding rows in `ornithe-feather.sources.sha256`.

## Identity digests

| Release | Marker | Digest or identity |
|---|---|---|
| 1.12.2 | `ornithe-feather.ready.json` | exact version `1.12.2`; Feather Gen2 build 2; `ornithe-feather.sources.sha256` SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 2,050 files |
| 1.12.2 | `ornithe-feather.provenance.json` | `feather-gen2-1.12.2+build.2-mergedv2.jar`, `feather-gen2-1.12.2+build.2.tiny`; Vineflower, Java 25 |
| 1.12.2 | `ornithe-feather.verification.json` | 2,050 source files verified, 0 source differences; raw input artifact differences empty; 1 derived remap archive difference |
| 1.13.2 | `ornithe-feather.ready.json` | exact version `1.13.2`; Feather Gen2 build 2; `ornithe-feather.sources.sha256` SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 2,711 files |
| 1.13.2 | `ornithe-feather.provenance.json` | `feather-gen2-1.13.2+build.2-mergedv2.jar`, `feather-gen2-1.13.2+build.2.tiny`; Vineflower, Java 25 |
| 1.13.2 | `ornithe-feather.verification.json` | 2,711 source files verified, 0 source differences; raw input artifact differences empty; 1 derived remap archive difference |

## Direct source-file SHA-256 values

| Release | Relative source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.12.2 | `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.12.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| 1.12.2 | `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `207a0ff4ee996916cd1fc73f47bfaae64f92719916cafed8395b1f2a8f797809` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 | `net/minecraft/block/LiquidBlock.java` | `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.13.2 | `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` |
| 1.13.2 | `net/minecraft/world/World.java` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |
| 1.13.2 | `net/minecraft/world/chunk/WorldChunk.java` | `d800fac11aa2672a4dd69cb5a599e5f84bb6e2c71cfc8080fa972acdabfb4914` |
| 1.13.2 | `net/minecraft/world/chunk/WorldChunkSection.java` | `df960f6239f7413dea7882775b9693a37c5f3650eabd0e5bf70f1504a0094e7e` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/LiquidBlock.java` | `feedc9a5f1843604e6755a447e89d8d5599613799e25cf8c93a0562e52e97856` |
| 1.13.2 | `net/minecraft/fluid/state/FluidState.java` | `05e2df9377a3bc1e91645ce4282bcaa4edebe93d7ef1907407961baaecff23a6` |
| 1.13.2 | `net/minecraft/fluid/FlowingFluid.java` | `2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253` |

## Exact source ranges inspected

- 1.12.2: `Entity.java:320-384, 948-963, 2141-2143`; `LivingEntity.java:195-199`; `PlayerEntity.java:174-204` (tick's `super.tick()` call at line 204) and `1733-1736` (liquid-collision override); `ClientPlayerEntity.java:21` (declaration extends `PlayerEntity`); `World.java:1579-1621`; `LiquidBlock.java:26-56, 139-185`.
- 1.13.2: `Entity.java:332-396, 947-976, 2509-2571, 2168-2170`; `LivingEntity.java:210-214`; `PlayerEntity.java:176-208` (tick's `super.tick()` call at line 208) and `1826-1829` (liquid-collision override); `ClientPlayerEntity.java:22` (declaration extends `PlayerEntity`); `World.java:529-535`; `WorldChunk.java:380-398`; `WorldChunkSection.java:34-35`; `BlockState.java:313-314`; `Block.java:678-681`; `LiquidBlock.java:36-71`; `FluidState.java:29-30, 67-69`; `FlowingFluid.java:54-98, 443-450`.

The full source-file hash comparisons were read-only. No source generation, tests, build, network access, product implementation/history, wiki or runtime execution was used.
