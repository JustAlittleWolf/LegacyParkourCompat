# Falling water and plain fence: 1.8 → 1.8.1

Status: exact bounded source comparison complete. The two operations below have no behavior change across this pair. Scope is the pre-variant plain oak fence (`Blocks.FENCE` / registry key `fence`), dry, in the horizontal falling-water wall cue and the locally controlled client player's water-current application. No other fence types are covered.

## Source readiness and identity

The older in-tree publication does not use adjacent `.ready.json` or `.sources.sha256` files. Its `.decompile-provenance.json` embeds exact input pins and the per-source SHA-256 roster; the provenance files hash to the identities below. Every Java file listed under “Verified source hashes” was independently SHA-256 checked against its embedded roster entry before relying on its source body. The checked source file counts match the roster counts (2,497 for 1.8; 2,500 for 1.8.1).

| Release | Version metadata SHA-256 | Feather metadata SHA-256 | Feather artifact | Artifact SHA-256 | Decompile provenance SHA-256 |
|---|---|---|---|---|---|
| 1.8 | `8be392d3e8b3e61500b60eb141de56519299069c9169081483ea5aae9fa9d593` | `83ac5ccbc65ec6496a6b4a54413ee22082f60bc99c74ea7b20f68b97acc0be7b` | `1.8+build.2` | `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe` | `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05` |
| 1.8.1 | `8c6a1660665a2b1551b628f819552ed5ba0327cbb8045fb266f3fdc3e98db149` | `40754754c7133b6e01354a49e8c8ba6be2b16bac02adc881057831b6b543b7c9` | `1.8.1+build.2` | `dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7` | `f3a3baa039f0e0ade6e656dfa11a4529909a4b709ac489703c4250262cefb1ca` |

The provenance pins raw client SHA-256 `ccccd0a92f76fbb24279691daad9b67f85d80471a37e59e0da76d7754982dfe8` (1.8) and `66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd` (1.8.1). The helper-order pipeline identities are `lpc-18-enum-helper-order-v1` and `lpc-181-enum-helper-order-v3`, respectively. The distinct mapping/version metadata URLs and exact artifacts are pinned in their respective provenance files.

## Comparison

1. **Horizontal falling-water wall cue.** 1.8 `LiquidBlock.getFlow` (`LiquidBlock.java:129-161`) and 1.8.1 (`LiquidBlock.java:130-162`) use the same gate and operations: only a liquid level `>= 8` enters the wall-cue scan; for each horizontal direction, the cue is true when `isFaceSolid` is true at the adjacent position or at that position's `up()`, using the same horizontal face. On a cue, the accumulated vector is normalized, `(0.0, -6.0, 0.0)` is added, the loop breaks, and the final vector is normalized.

   Both releases' `LiquidBlock.isFaceSolid` (`1.8:74-82`, `1.8.1:75-83`) reject the same liquid material, accept `UP`, reject ice for other faces, and otherwise delegate to `Block.isFaceSolid`. Both `Block.isFaceSolid` bodies (`1.8:282-284`, `1.8.1:289-291`) return the target block material's `isSolid()`. The plain fence reaches this material-based test; its `isSolidRender() == false` (`FenceBlock.java:109` / `114`) is not consulted by this wall cue. Thus a dry adjacent or above-adjacent plain oak fence with solid wood material cues the downward component in both versions. There is no fence-specific or oak-specific branch in the flow method.

2. **Current application to the local client player.** Both `Entity.tick` bodies call `checkWaterCollisions()` (`1.8:299`, `1.8.1:304`). `Entity.checkWaterCollisions` (`1.8:745-759`, `1.8.1:750-764`) passes the player's shape grown by `(0.0, -0.4F, 0.0)` and contracted by `(0.001, 0.001, 0.001)` to `World.applyLiquidDrag` for `Material.WATER`. If the call reports water contact, it clears fall distance, sets `inWater`, and resets the fire timer.

   `World.applyLiquidDrag` (`1.8:1462-1501`, `1.8.1:1468-1506`) first requires the covered area loaded, then selects water blocks whose liquid surface reaches the query bounds and adds each block's `applyMaterialDrag` vector in nested X/Y/Z order. If the aggregate vector has length `> 0.0` and `entity.hasLiquidCollision()` is true, it normalizes the vector and adds `vector.x/y/z * 0.014` to the corresponding velocity components. The sign is **plus**: current follows the computed water-flow vector. `PlayerEntity.hasLiquidCollision` is `!abilities.flying` in both versions (`1.8:1612-1614`, `1.8.1:1615-1617`), so the local player receives current while not flying and does not while flying. `LocalClientPlayerEntity` adds no water-specific gate; this path has no local-player identity check in the vanilla update method.

   The only source-level edit in the encompassing world method relevant here is allocation: 1.8 constructs an immutable `BlockPos` per visited cell; 1.8.1 reuses a `BlockPos__Mutable`. The material/surface gates, scan order, flow aggregation, length and player gates, normalization and `0.014` velocity additions are unchanged. The liquid flow and player gate bodies are unchanged as well.

## Verified source hashes

Each value below is the actual file SHA-256 and matches the corresponding `sources` entry in that release's `.decompile-provenance.json`; this also confirms the examined method bodies are intact for the published source tree.

| Relative source path | 1.8 SHA-256 | 1.8.1 SHA-256 |
|---|---|---|
| `net/minecraft/block/Block.java` | `4a4e6dfde24a135ecf87c359fd98f0380664e4bacaf679815c9016cb1ba0d814` | `4f645cb1f25248ba16649d9d1ab17b3dbe4c7316191deb982f641beff437e67c` |
| `net/minecraft/block/Blocks.java` | `af42dc7d1fe95a3cc070427e32cd76213d6eab4df49118fee99aa3c7a3a004b0` | `af42dc7d1fe95a3cc070427e32cd76213d6eab4df49118fee99aa3c7a3a004b0` |
| `net/minecraft/block/FenceBlock.java` | `2e75c087705278524a3a54acf3b56c4a476294357a11741640b457cea4e781e1` | `0a4b6623a3b5d59b521f082676b2a7400d40d696ee239da7f74c146487637271` |
| `net/minecraft/block/FlowingLiquidBlock.java` | `d52c2ca1f9d145960da1342f2b74458a4327e0803d0d85a07c715d3079e57f8c` | `d52c2ca1f9d145960da1342f2b74458a4327e0803d0d85a07c715d3079e57f8c` |
| `net/minecraft/block/LiquidBlock.java` | `e4ef9385d025aa4672d4b6a55afc72803267d0721469bf036217ab30a7760fe3` | `36d345c4b0985902ed265602e391982cdc13bc447906e219d3efe568aed4676a` |
| `net/minecraft/world/World.java` | `afbc701ef8b66020bdbb2831fd80683705d20237c68fbf23a4001c020876fc6d` | `f0873207c5c7a793189597470392fa0184513b3c5a682a6f8795e1c9ffde9f71` |
| `net/minecraft/entity/Entity.java` | `2be081abe1720f89d7ab90f782b8411e71bc5b8a6b53966b49a2380e3c47ffd9` | `aa359621cf9348e5ce4bcb9e28b67f8a5c75d7e5a3d554c8d93471c79540a55c` |
| `net/minecraft/entity/living/LivingEntity.java` | `645624913e265cad50293f659812a35c3c4303d1e8bdafdcca4eaf2e8ed5c69d` | `fbfb0f0821a2d6b9d1d665387b2c5423fc0c1d8f5f36f7b5e1258b26d90c4c4f` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `13fa1367b71035efbfccc593de57838268b10a8ceebb0aa76ad3d819b44cb0ca` | `6c24523140f31d588214da9c9ce15ac7999446e1b647fc64194e695ebbd30d8e` |
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `3149d74c0dd33c4c9346ddae077fc318e7a20c3f81921eae8d85becb258b5d47` | `3149d74c0dd33c4c9346ddae077fc318e7a20c3f81921eae8d85becb258b5d47` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `42a92a74e5c64992572760196a2052273d96bc061cd9d1120045ba03532f1e2c` | `42a92a74e5c64992572760196a2052273d96bc061cd9d1120045ba03532f1e2c` |