# 1.14.1–1.14.3 Yarn provenance and local-player eligibility (r2)

Supplement to `source-114-1151-infinite-border-aliases.md`; the first report is unchanged.

## Hidden provenance sidecars

| Release | Exact mapping input | Pinned client JAR SHA-256 | Listed source count | Actual `.decompile-provenance.json` SHA-256 |
|---|---|---|---:|---|
| 1.14.1 | Yarn `1.14.1+build.10`, Intermediary `1.14.1-v2` | `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814` | 3,155 | `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` |
| 1.14.2 | Yarn `1.14.2+build.7`, Intermediary `1.14.2-v2` | `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25` | 3,155 | `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` |
| 1.14.3 | Yarn `1.14.3+build.13`, Intermediary `1.14.3-v2` | `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07` | 3,159 | `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a` |

Each hidden sidecar pins the exact Mojang `1.14.x.json`, original `client.jar`, Yarn and Intermediary artifacts, decompiler pipeline/tool hashes, runtime, and SHA-256 for every listed source path. The queued independent worker verified the listed file sets and pinned JARs. Relevant source hashes below are the values in those sidecars.

## Relevant file hashes

| Source | 1.14.1 | 1.14.2 | 1.14.3 |
|---|---|---|---|
| `world/border/WorldBorder.java` | `e191b5b5fcfc43c4412b9da49e5146219906c9bc6995b40ac6cab25f62a7cd52` | same | `2da4c764b819fdc78b66eedf4cdfd71aadfcf9e7800b80515a88cd050c877506` |
| `world/EntityView.java` | `fef34e5144d87f77a777e4dc1b62ace7a92af2ef9dba7ceb25a80c7068170eea` | same | `d5f906a1b0332f9ff978e9c0a92bb913cdff68ac173a33aad44aedccc9516ebb` |
| `world/IWorld.java` | `662d377c31f4e4366d558e24f325db0b00f48507f298cd718293917994bca72c` | same | `0930f466c86ebb07b77ecbf85661ea519ab43d884d5874edeaf52f21d534947a` |
| `entity/Entity.java` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| `client/network/ClientPlayerEntity.java` | `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933` | `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947` | `28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae` |
| `util/shape/VoxelShape.java` | `bda272dd780777c5706351078b280a9775ddc40fcaa518a912841be2ca6e047a` | same | `70a870fdeea376e9936ea62287f96c3377ac319c9877c0e19cb857dbd1ac96d2` |
| `util/math/BoundingBox.java` / `Box.java` | `453c00e018b05753d8ec7c3d35d165466faea9eb355bf20638bdcc5ce34bea87` | same | `09df39d6a6c99ac4a009a02c398b01ef2653d293ace3e230939b3a49152bf665` |
| `util/math/BlockPos.java` | `cb812b182cb0cb87eb1ab82307922d4bf4513837cd85f9c76dd1ed0ce509777a` | same | `a647a0a2f8364f107ae0afdc48920406c2624a203644c4fa5065c331f5a8e1de` |
| `util/math/Vec3i.java` | `534d56ad4fd83ee0f2c2187afa2e34145de41944d1d1ad092e1a8bc5e9833c61` | same | same |
| `util/math/MathHelper.java` | `21ed846ac48a5a2fbcdb2ac31fd5b74a461485effd881112c903c09d9297504a` | same | same |

## Operation and eligibility comparison

| Operation | Exact 1.14.1–1.14.3 Yarn behavior | Relationship to 1.14.4 and 1.15.2 candidate |
|---|---|---|
| Infinite-Y border shape | `WorldBorder.Area` builds the exterior as `UNBOUNDED − cuboid(floor(west), -∞, floor(north), ceil(east), +∞, ceil(south))`, `ONLY_FIRST`; moving and static areas use it. | Same infinite-Y exterior operation as 1.14.4 Feather and 1.15.2 Mojmap. |
| Ordinary entity movement | `Entity.method_17835` (.1/.2) and `Entity.handleCollisions` (.3) obtain the border shape and omit it only when it overlaps the current entity box contracted by `1.0E-7`; otherwise it is included in the movement collision stream. | Direct player movement has a border collision shape in these releases too. This is a current-box gate, not the later expanded thin-shell test. It does not by itself establish the auto-jump effect. |
| LocalPlayer auto-jump collision query | `.1/.2` call `world.getCollisionShapes`; `.3` calls `world.method_20743`. `EntityView` implementations return only other-entity collision boxes (including the player/entity hard-collision box); they do not enumerate block shapes or the world border. `IWorld` merely delegates to `EntityView`. | The border AABB cannot enter this local-player scan in the checked 1.14.1–1.14.3 sources, so the west/infinite-box center alias and timer path are ineligible there. |
| 1.14.4+ query | 1.14.4 player auto-jump calls `world.getCollisions`; `WorldView.getCollisions` concatenates `getBlockCollisions` and entity collisions. `getBlockCollisions` can emit the border shape using the expanded/contracted entity-AABB thin-shell gate before block candidates. The existing 1.14.4 → 1.15.2 first-hit AABB-center, `BlockPos` conversion and timer sequence is detailed in r1. | Wiring changes eligibility: the world-border AABB can reach the local-player scan by 1.14.4. This makes the origin-column alias/timer candidate relevant at 1.14.4 and 1.15.2, but not 1.14.1–1.14.3. |

The endpoint evidence establishes the candidate's 1.14.1–1.14.3 ineligibility and 1.14.4+ eligibility. It does not identify a narrower source change between those exact releases.

## Exact source locations

- 1.14.1 / 1.14.2 Yarn: `net/minecraft/world/border/WorldBorder.java` (area shape methods); `net/minecraft/entity/Entity.java:644–661` / `646–663`; `net/minecraft/world/EntityView.java:45–59`; `net/minecraft/world/IWorld.java:72–80`; `net/minecraft/client/network/ClientPlayerEntity.java:900–940`.
- 1.14.3 Yarn: `net/minecraft/world/border/WorldBorder.java:330–342,405–421`; `net/minecraft/entity/Entity.java:653–662`; `net/minecraft/world/EntityView.java:45–59`; `net/minecraft/world/IWorld.java:73–81`; `net/minecraft/client/network/ClientPlayerEntity.java:899–940`.
- 1.14.4 Feather and 1.15.2 Mojmap: see operation paths and hashes in r1.
