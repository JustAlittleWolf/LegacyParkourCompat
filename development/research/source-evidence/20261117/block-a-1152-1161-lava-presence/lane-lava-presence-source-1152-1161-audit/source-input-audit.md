# Source input identity audit

## Frozen comparison inputs

- Exact pair: Minecraft 1.15.2 -> 1.16.1.
- Mapping family: Mojmap on both sides.
- Runtime/tool records: Java 25.0.3+9-LTS; Gradle 9.7.1; Vineflower 1.12.0; ASM 9.10.1; Mapping IO 0.9.1; Gson 2.14.0; Tiny Remapper 0.14.1.
- Frozen ordered source candidate set: see `source-roster.md`.
- Roster SHA-256: `7b7cf479bb5901487413929365459d7705f796307cd5581e608b5663a689d663`.

## Version-root publication identities

For both versions, the ready marker, provenance file, source manifest, artifact manifest, and movement diagnostics were read from the version-root siblings under `decompiled_minecraft/<version>/`. Ready marker identity is exact version + `mojmap`; source/artifact manifest hashes and diagnostics hash match the ready marker. Every frozen source file was recalculated and matched its source-manifest entry.

### 1.15.2

- Ready marker SHA-256: `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0`
- Provenance SHA-256: `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e`
- Source manifest SHA-256: `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`
- Artifact manifest SHA-256: `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406`
- Movement diagnostics SHA-256: `1ab6c473dbbdf2c590a7a8870befbd404e634fe7c1b0ae6a796b7b5f9c63d4d5`
- Version metadata ID: `1.15.2`; official client SHA-256: `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c`.
- Recorded preparation command: `decompileMinecraft --versions=1.15.2,1.16.5,1.17.1,1.18.2 --mappings=mojmap --decompiler-heap=4G`; recorded cache root: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts`.

### 1.16.1

- Ready marker SHA-256: `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb`
- Provenance SHA-256: `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98`
- Source manifest SHA-256: `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`
- Artifact manifest SHA-256: `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98`
- Movement diagnostics SHA-256: `3a7c0831f5c6d4b5d3bc8aef2b7ef88f0c1ee7e3981379ea0181d77733a4a827`
- Version metadata ID: `1.16.1`; official client SHA-256: `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`.
- Recorded preparation command: `decompileMinecraft --versions=1.16.1,1.16.2 --mappings=mojmap --decompiler-heap=4G`; recorded cache root: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts`.

## Frozen source hashes

All paths are relative to each version's `mojmap/` root. Each listed hash was recomputed from the local file and matched its version's `mojmap.sources.sha256` entry.

| Source path | 1.15.2 SHA-256 | 1.16.1 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` |
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` |
| `net/minecraft/world/entity/LivingEntity.java` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |
| `net/minecraft/world/entity/player/Player.java` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` |
| `net/minecraft/world/level/BlockGetter.java` | `7c144c5890245b8189faea94a56c628d469e4c142977645f753deac7f62f9545` | `0f81fc1750218ddefabb33a03120cf1ac3447640c1c97ff59850fd45fab38f44` |
| `net/minecraft/world/level/LevelReader.java` | `d09aa8eaaa7f8ca8703091facb4d7e1a26c9f4bea8b99c5c8956682875241d37` | `d301b992d71b05306a3139e69f3f1529190f82404791ec60f9a693188d29577a` |
| `net/minecraft/world/level/block/Blocks.java` | `0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9` | `61f18f903f7d96731bbc3cac544a8614f55fddac872dc0b0fee5a53511c313a9` |
| `net/minecraft/world/level/block/LiquidBlock.java` | `cc7638d876a7e70cd111891cdb7be424741a4a5bdd4851f3623881c30734c24e` | `e7b138668f426eaf7c2f89b34dd0448f6ba0eb133963929ebf12d64840f22fb3` |
| `net/minecraft/world/level/block/state/BlockState.java` | `77320fd1e50e58d7a3ed593ccbcf21853c97d125fccebde2e0e305dc47524f21` | `edd2d9dba0c10d035774f8b2dd7dcf00d112d7a2d67734fba1851a2d2f91bd36` |
| `net/minecraft/world/level/material/Fluid.java` | `b20c97ed8fdae1e3ef945340113a2629a8bc53b12fad84bf1ac6c689669de13b` | `b37e7870f93cf1c7d4c96456a668c3f42ee48b5c5b9882e9c7cecea55717ae2f` |
| `net/minecraft/world/level/material/FluidState.java` | `f88bff7d54ea0220ee67e0fe36a78df67871e52ae636e584ea26dd107f652c15` | `665b4024a32f659d92ce1259b25f529616e44b6f3df8a6aab4a4a2c57c7bc26d` |
| `net/minecraft/tags/FluidTags.java` | `7b75994500c5286295f17227cd89ce0c42ffe829af57e6a5ff3415e41342953c` | `29756a98a15f0ab06c33eb6b414883c13c352c803e8c6d42608cbe7960905c58` |
| `net/minecraft/world/level/material/Fluids.java` | `7ee6840d600b06d6c803c8c741e39efdf5add797879f656a9c3abd9348fc7c64` | `7ee6840d600b06d6c803c8c741e39efdf5add797879f656a9c3abd9348fc7c64` |
| `net/minecraft/world/level/material/FlowingFluid.java` | `e8a395552e53f33bcb5648325615d31bc1796ce2a8aeb501bdda3127622dccdf` | `a85a5cd6625b1b4a047bd693399e1f78c2978ae207af927d4abfac3b34229552` |
| `net/minecraft/world/level/material/LavaFluid.java` | `d315b4cc1cd215e981bf343c03d1fe86de64b4fcea3b0869efff5ac57aff7fd2` | `6e98f387c7561bff3b66f2e86ce54bab54a8ff3337639cb106e5e6aaad8ada9b` |
| `net/minecraft/world/phys/AABB.java` | `82324c0e6a3d69e80424656e6098b39ce41f0f17a5e6efbe6542fe8eaca24a18` | `514558cf4827679d84a4debd4f400d7d65bd4b8982b7ae8124a0d83a916ba878` |
| `net/minecraft/world/level/material/FluidStateImpl.java` | `4d678aa8c80271d1a3f1069accd2c726b094602727875436b5e05e3101236b13` | not present/not used |

## Input verification limit

The `artifacts.sha256` files are intact and each hash matches its ready marker. Their 38 entries per version declare exact client, mapping and dependency input hashes. However, the recorded cache directory `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts` is absent in this checkout, so the raw client/mapping/dependency bytes could not be rehashed here. The ready marker's version/client identities and the local source-manifest-to-file hash chain are verified; byte-level verification of the original source-generation inputs remains open. The recorded `mappingArtifacts` field is null on both provenance files, so an individual mapping artifact build is not identified there.