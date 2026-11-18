# Frozen source/provenance roster addendum — 1.20.1 → 1.20.2

Frozen before opening the corrected author claim. Scope: rebind the unchanged audited water/lava slices and inspect the omitted Java dependencies for player fluid push eligibility, flow vector math, loaded-chunk preflight, missing client chunk fallback, and LocalPlayer subtype routing. Canonical source tree is read-only at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<release>/mojmap/`.

## Exact release and export provenance

Each ready marker's raw SHA-256, provenance raw SHA-256, source-manifest raw SHA-256, artifact-manifest raw SHA-256 and diagnostics raw SHA-256 were checked locally. The marker values for `versionId`, `mapping`, `sourceManifestSha256`, `artifactManifestSha256`, and `clientSha256` agree with their respective release/mapping and the file hashes. The source-manifest digest equals the ready marker's `sourceManifestSha256`; the artifact-manifest digest equals the ready marker's `artifactManifestSha256`. Provenance identifies the exact run and Mojmap namespace. Each Java file hash below also matches its line in that version's verified `mojmap.sources.sha256`.

| Release | ready marker SHA-256 | provenance SHA-256 | source manifest SHA-256 | artifact manifest SHA-256 | diagnostics SHA-256 | Ready metadata |
|---|---|---|---|---|---|---|
| 1.20.1 | `f8dc81374dc42521ee9d17a04ea0dc77f1c5384548cfc01c9621b3d84b670fed` | `5aeb1df09671480aeddb921c919e92098e33d0a0bce47e22f82a865cb32400aa` | `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` | `5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163bfa19b01c7db2` | `3eecf5b2c48cb99795874a36a8eb83cfc07866bc909a51c997dbfaac8083f4fd` | `versionId=1.20.1`, `mapping=mojmap`, `versionMetadataId=1.20.1`, `clientSha256=56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f` |
| 1.20.2 | `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f` | `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05` | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` | `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54` | `a60a6ea2a91cda32df8a7b837d5344ae54c8370058cc9ab3ae163b30e8fa6b79` | `versionId=1.20.2`, `mapping=mojmap`, `versionMetadataId=1.20.2`, `clientSha256=fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda` |

## Frozen paired source files

Paths are relative to each `<version>/mojmap/` root.

| Java file | 1.20.1 SHA-256 | 1.20.2 SHA-256 | Scope |
|---|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` | Existing scan, current-push aggregation, touching-unloaded-chunk preflight, refresh/call order |
| `net/minecraft/world/entity/LivingEntity.java` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | Existing vertical response, collision exit and jump depth dispatch |
| `net/minecraft/client/player/LocalPlayer.java` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | LocalPlayer parent/tick route and held-input handoff |
| `net/minecraft/world/entity/player/Player.java` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` | `isPushedByFluid`; Player superclass in client routing |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `393b88eb4f41a84528f584311bb7aefc02adbf474c2744f3555acc0663c85dbf` | `759f8f03b89dab1f6b65c73e1e25a68ae6a6083cb49b5d2719136edda7edf696` | Client subclass chain |
| `net/minecraft/world/level/LevelReader.java` | `ec8c1f883dc1a71d2666f57cf7e124298272ae8b483770d32ecdb32c385b0bdd` | `ec8c1f883dc1a71d2666f57cf7e124298272ae8b483770d32ecdb32c385b0bdd` | `hasChunksAt`, default `hasChunkAt` routing |
| `net/minecraft/world/level/Level.java` | `c13ead6630a8f77529574afcd6009b0395698489ff8ad6fdf75b0e745c65f61c` | `c13ead6630a8f77529574afcd6009b0395698489ff8ad6fdf75b0e745c65f61c` | Level fluid-state forwarding to chunk access |
| `net/minecraft/world/level/material/FlowingFluid.java` | `ba1dff6ccb28b62760c74d66832d8fdbe7a1cdc37b9c5a0ff476bd200215c904` | `0f44b1af25533dd8a1fbfec13b279739341db0c563568337a2825d3e46a1a5bb` | `getFlow` and helper operation routing |
| `net/minecraft/client/multiplayer/ClientLevel.java` | `8d7434e668bbb868f3d25d638abab4cd612ac89f1b54ae701fa81f212694fbc5` | `6204df69a17b375e46f4405fe67a106def336c819e589ec0794c04920a31c5d7` | Client-level chunk-presence / client chunk-source routing |
| `net/minecraft/client/multiplayer/ClientChunkCache.java` | `e7670ad6944e6c8df79d7ac3dc89b3761c38404f2c83f371d77701cb3be8ab04` | `1c35cc715b1078bd981caeed7a0fbec768253c986ea5ffa37c5f1cffa31f9bf7` | Client loaded-chunk presence, missing-chunk fallback |
| `net/minecraft/world/level/chunk/EmptyLevelChunk.java` | `090235cbdebd1989b64f9be7cb191b31cb51e9b211f2e8be041ba229e6b54e69` | `090235cbdebd1989b64f9be7cb191b31cb51e9b211f2e8be041ba229e6b54e69` | Missing-chunk fluid-state fallback |
| `net/minecraft/world/level/chunk/LevelChunk.java` | `d1c6503679563a0ea2a61aea1a09c51c43260044ab2b7048c9ca527aa323cfc2` | `3883c0efdc814efd5a0e1d3d4857350de2b9918963bfd9096ab6c38f7aab3eab` | Loaded-chunk fluid-state delegation |

The paired `FluidState.java` source remains bound by the original frozen roster; its bytes were identical in that roster. Effective `minecraft:water` and `minecraft:lava` tag membership is a separate, still-open resource/data dependency and is not inferred from Java.

No wiki, backlog, product source/history, or other lane was consulted. No corrected author text was opened before this roster was written.