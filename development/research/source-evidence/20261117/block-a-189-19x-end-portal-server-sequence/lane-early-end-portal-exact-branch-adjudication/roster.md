# Frozen source roster: End portal transfer branches, 1.8.9 → 1.9

Frozen before consulting prior comparison or audit drafts. Read-only source paths are relative to `D:\Javastuff\LegacyParkourCompat`.

## Source identities

- **1.8.9 Ornithe Feather**: `decompiled_minecraft/1.8.9/ornithe-feather/`. Version-root ready marker states exact `versionId=1.8.9`, mapping `ornithe-feather`, mapping inputs `feather-gen2-1.8.9+build.2-mergedv2.jar` plus `.tiny`, client SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`; `sourceFileCount=1612`. Actual SHA-256 of `ornithe-feather.sources.sha256` is `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`, equal to the ready marker value. Root sidecar hashes: ready `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95`; verification `a4206eee9ffb45a6f54f93371c8275b7a6c37cf9ab1ca93d5fa0f5d889881620`; provenance `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`; artifacts manifest `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`. The verification sidecar reports all 1,612 published source files verified with zero source differences; its one derived remapped-jar difference is explicitly distinguished from unchanged source files. The root provenance's exact run also lists 1.9.4; it is not used as evidence for the newer side, which is separately pinned below.
- **1.9 Ornithe Feather**: `decompiled_minecraft/1.9/ornithe-feather/`. Hidden `.decompile-provenance.json` SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`; it contains 1,819 source hashes. Its pinned inputs include the 1.9 client SHA-256 `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307`, 1.9 Feather metadata SHA-256 `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371`, Feather Gen2 1.9 build.2 merged-v2 jar SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`, and exact 1.9 version metadata SHA-256 `8d6f1e7f3a99f1236914b61e64eeade955ba21e8150563457142d3981aeee688`.

## Frozen cited-source roster

Actual file SHA-256 matched the version's source manifest for every row (1.8.9 root `ornithe-feather.sources.sha256`; 1.9 mapping-root `.decompile-provenance.json` `sources`).

| Release | Relative source path | SHA-256 |
|---|---|---|
| 1.8.9 | `net/minecraft/block/EndPortalBlock.java` | `1cb419f44592e68244e06b34799c6354a2bcc3d08bd849e0709fca36b54e1e40` |
| 1.8.9 | `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `047158bda6fa8fe2ea8a097972c098c5abe492ffbbe280006ae7592cd97fbb81` |
| 1.8.9 | `net/minecraft/server/PlayerManager.java` | `0498b44f26782daa8b82db0c0823cef615c6bd7e7234a43c32456e15647d9a5d` |
| 1.8.9 | `net/minecraft/server/world/PortalForcer.java` | `5fe634ce1537e1fbe6c615a47c23059b66b52f24b9c21f99d52b300f9c00ee44` |
| 1.9 | `net/minecraft/block/EndPortalBlock.java` | `5e04abb22c37fb6c8af646d93702b54c5abac4737edd92a8b2fc5c593bbf2ba5` |
| 1.9 | `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `dc6b4d3dfff336aed5efa03c9f97a3ae09531e0ae8cd80f8cb6de949333fbe10` |
| 1.9 | `net/minecraft/server/PlayerManager.java` | `ec75fbd8978b92d18f16b649c65ed596ddce6a437fd22bb18fc0b86d2c937e86` |
| 1.9 | `net/minecraft/server/world/PortalForcer.java` | `13488be38f1b0426b40388e30fbbfec8ddfef885382b3354a9e7180254b4c947` |

The frozen branch inventory is the successful server-side End portal collision callback → virtual `ServerPlayerEntity.changeDimension(1)` → `PlayerManager.changeDimension(ServerPlayerEntity,int)` → `PlayerManager.changeDimension(Entity,int,ServerWorld,ServerWorld)` → destination End `PortalForcer.onDimensionChanged` path. It covers only these exact endpoints and this ordinary alive Overworld-to-End player path.