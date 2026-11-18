# Collision candidate representation

The independent blind Domain B source review launched on 2026-11-18 (`collision_representation_source_gate`) accepted the bounded representation change in the [collision candidate collection finding](../../../findings/1.12.2--1.13.2/collision-representation.md). The reviewed finding is the blob `2a9c43247a583fe5cb18f3a02278d783ff20311a` at commit `583f8a9b01205b09922ed76d5a0eadfbe819cc85`. Its raw LF Git payload has SHA-256 `e0c478bd8299c310dd9aca6fabf1f19d7cf53f3893fc6b7518d768a81e159f5a`.

All four exact releases use `ornithe-feather`, Feather Gen2 `build.2`. The reviewed files match their `ornithe-feather.sources.sha256` manifests:

| Release | Source manifest SHA-256 |
| --- | --- |
| 1.12.2 | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.13 | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |
| 1.13.1 | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |
| 1.13.2 | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |

- **1.12.2.** `entity/Entity.java:462–795` (`move`) consumes a list of boxes. In `world/World.java:964–1015, 1017–1040` (`getCollisions`), the block collector fills the list first, then the entity pass adds overlapping collision boxes and skips same-vehicle entities.
- **1.13.** This is the first stable release with the voxel-shape representation. `entity/Entity.java:473–776` (`move`) consumes a combined `VoxelShape`. `world/WorldView.java:191–196` (`getCollisions`) uses signed `1.0E-7` offsets for the initial box and expands the swept box by `1.0E-7` before subtracting the shifted initial shape with `ONLY_FIRST`. The block collector is at `WorldView.java:113–185`. The combined collector and entity pass at `WorldView.java:203–246` check both the explicit exclusion set and same-vehicle membership.
- **1.13.1 and 1.13.2.** The collectors return streams of voxel shapes. In both releases, `world/WorldView.java:112–152` (`getBlockCollisions`) emits regular translated shapes before the aggregated full-cube shape, which includes eligible out-of-border cells. The swept query is at `WorldView.java:158–163`. In 1.13.2, `world/World.java:1755–1758` (`getCollisions`) concatenates block candidates before entity candidates, and `world/EntityView.java:21–35` (`getEntityCollisions`) retains same-vehicle and explicit exclusions. `block/shape/VoxelShapes.java:194–206` (`calculateMaxOffset`) consumes the stream to clip an axis offset.

The ordering above describes source traversal and consumption. The independent review established no additional movement-visible witness or implementation eligibility. The axis epsilon consequences remain in the separate findings identified by the reviewed finding. Runtime behavior was unverified.
