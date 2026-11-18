# Frozen source roster — lava presence, 1.16.1 → 1.16.2

Frozen before opening either author scratch input. Scope is exact Mojmap source for the specified pair, focused on the lava-presence producer/callback/cache and its local-player travel and held-jump consumers. No wiki, product implementation, Git history, patch midpoint, or runtime/game evidence was consulted.

## Source publication identity

| Version | Mapping | Root marker | Source count / bytes | Exact source tree digest |
|---|---|---|---:|---|
| 1.16.1 | Mojmap | `decompiled_minecraft/1.16.1/mojmap.ready.json` SHA-256 `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb`; provenance SHA-256 `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98` | 3,572 / 19,220,344 | `mojmap.sources.sha256` SHA-256 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |
| 1.16.2 | Mojmap | `decompiled_minecraft/1.16.2/mojmap.ready.json` SHA-256 `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214`; provenance SHA-256 `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c` | 3,516 / 19,198,358 | `mojmap.sources.sha256` SHA-256 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` |

Both ready markers state the exact matching version and `mapping=mojmap`; each source manifest has exactly the marker's source count and its computed SHA-256 matches the ready marker. Both provenance records declare the same exact batch (`1.16.1,1.16.2`), `mappingOption=mojmap`, Vineflower 1.12.0, Java 25.0.3+9-LTS. Artifact-manifest and movement-diagnostics hashes also match each ready marker. Client hashes: 1.16.1 `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`; 1.16.2 `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`.

## Frozen paired file/method roster

Hashes below are SHA-256 of the raw Java files, independently checked against the version-root source manifest. Pairing is by Mojmap paths; the source manifests establish the file presence. The complete pair is confined to producer/reset/timing, tagged fluid-height and chunk gates, LocalPlayer/Player call paths, and LivingEntity travel/jump consumers.

| Relative path | 1.16.1 SHA-256 | 1.16.2 SHA-256 | Rostered slices |
|---|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `tick`/`baseTick`; `move` and `checkInsideBlocks`; lava fields, reset/setter/predicate; `updateInWaterStateAndDoFluidPushing`; `updateFluidHeightAndDoFluidPushing`; `getFluidHeight`; `getFluidJumpThreshold` |
| `net/minecraft/world/level/block/LiquidBlock.java` | `e7b138668f426eaf7c2f89b34dd0448f6ba0eb133963929ebf12d64840f22fb3` | `24f1cfbd036cca77ec99d855a028194ed39f583699ce91bc2a4c61957ff22fb7` | `entityInside` and any lava-presence write; checked in both versions, including newer-file contents |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `1b524b5c45e3be4397dd680b9bd12c937497b9088e623428cf6387406018ef7f` | `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b` | `BlockState.entityInside` dispatch to block implementation |
| `net/minecraft/world/level/LevelReader.java` | `d301b992d71b05306a3139e69f3f1529190f82404791ec60f9a693188d29577a` | same | `hasChunksAt` defaults used by fluid scan |
| `net/minecraft/world/level/BlockGetter.java` | `0f81fc1750218ddefabb33a03120cf1ac3447640c1c97ff59850fd45fab38f44` | `847fe74a63e7c5a48c028cd40ddbd579ee616370bd3a19b03ff1ab4271175e2b` | fluid-state lookup surface |
| `net/minecraft/world/level/material/FluidState.java` | `665b4024a32f659d92ce1259b25f529616e44b6f3df8a6aab4a4a2c57c7bc26d` | same | tag checks and fluid-height accessor called by both producers |
| `net/minecraft/world/level/material/Fluid.java` | `b37e7870f93cf1c7d4c96456a668c3f42ee48b5c5b9882e9c7cecea55717ae2f` | same | common fluid height/flow implementation |
| `net/minecraft/world/level/material/FlowingFluid.java` | `a85a5cd6625b1b4a047bd693399e1f78c2978ae207af927d4abfac3b34229552` | same | flowing-fluid height behavior |
| `net/minecraft/world/level/material/LavaFluid.java` | `6e98f387c7561bff3b66f2e86ce54bab54a8ff3337639cb106e5e6aaad8ada9b` | same | lava fluid subtype |
| `net/minecraft/world/level/material/Fluids.java` | `7ee6840d600b06d6c803c8c741e39efdf5add797879f656a9c3abd9348fc7c64` | same | source/flowing lava registrations |
| `net/minecraft/tags/FluidTags.java` | `29756a98a15f0ab06c33eb6b414883c13c352c803e8c6d42608cbe7960905c58` | `75d072cdc3e9073fc3fa481e4ccadd802e77a880ec776412f69bc865c4c78267` | declared `LAVA` tag identity used by producer and consumers |
| `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | `baseTick`; `tick`/`aiStep`; jump selection and `jumpInLiquid`; lava `travel` branch |
| `net/minecraft/world/entity/player/Player.java` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | `tick`, `jumpFromGround`, and `travel` overrides/delegation |
| `net/minecraft/client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | local-player tick enters Player/LivingEntity chain |

The source roots are decompiled Java, not runtime tag-data packs. The roster covers the declared `FluidTags.LAVA` identity and all producer/consumer uses; it does not claim an independent enumeration of external tag membership. No intermediate release is part of this comparison.