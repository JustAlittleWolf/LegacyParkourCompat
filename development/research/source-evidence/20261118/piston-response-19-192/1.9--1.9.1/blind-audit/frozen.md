# Frozen blind audit: piston player callback, 1.9 → 1.9.1

## Verdict and boundary

**Bounded result: no change found in moving-piston player callback admission, selector/bound, slime axis-velocity write, displacement request, or callback/progress order.** The source-reachable witness is a normal (non-spectator) local player whose entity box intersects the moving block's queried box. This records source admission and the `Entity.move` request only; it makes no claim about final shape clipping, collision resolution, or resulting trajectory. It is not a full-pair coverage freeze or a runtime-parity verdict.

## Exact source identity

Both source roots are canonical `decompiled_minecraft/<version>/ornithe-feather/` outputs with `.decompile-provenance.json`; the compared mapping families are Ornithe Feather 1.9+build.2 and 1.9.1+build.2. I checked the actual cached inputs against the manifests and the listed source-file SHA-256 values against each manifest.

| Version | Manifest SHA-256 | Mojang client SHA-256 | Feather metadata SHA-256 | Feather merged mapping archive SHA-256 | Cached Tiny SHA-256 |
|---|---|---|---|---|---|
| 1.9 | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` | `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307` | `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371` | `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` | `75a137cbb2dd723fa133568189a599fe209bab0e56a552b44c6e9f2803f7d7f9` |
| 1.9.1 | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9` | `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4` | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` | `387e55b125e5691d993b0a553ab712eb906d59fe8dd25a5c9e805265e78d1334` |

The source-manifest input URLs identify the exact client jars, Feather version metadata, and Feather merged archives. The manifests name the exact released versions and tool/runtime pipeline; the source tree's origin and generation provenance are local evidence, not an online mapping lookup.

## Compared paths and observations

- `net/minecraft/block/entity/MovingBlockEntity.java`, both versions, `moveEntities` lines 90–149 and `tick` lines 163–182: file SHA-256 is identical (`7ad3426bb74c9e69b4f521d20e045b1dc1eafa9b725daf0e2c0b9b718bc2683c`). The callback forms its query bound from the union returned by `getShape(world,pos)` and translates that box by `pos`; it queries `world.getEntities(null, box)`, iterates the returned list in order, and skips only entities whose piston behavior is `IGNORE`.
- `net/minecraft/world/World.java`, `getEntities(Entity,Box)` around lines 2135–2155: both versions default to `EntityFilter.NOT_SPECTATOR`, then visit loaded chunks in the computed x/z range. The corresponding `WorldChunk.getEntities` around lines 663–685 checks entity-box intersection and the filter. These caller/query bodies are text-identical in the compared slices. The bound's construction and intersection are used only to establish the callback witness; no final shape or clipping result is inferred.
- `net/minecraft/entity/Entity.java`, `getPistonMoveBehavior` lines 2246–2248: both versions return `PistonMoveBehavior.NORMAL` (identical source digest `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a`). The player classes in this chain do not override this method. Thus an ordinary non-spectator local player in the queried list passes the callback's `!= IGNORE` gate; riding status is not a separate callback predicate.
- In `moveEntities`, the direction is `facing` when extending and `facing.getOpposite()` when retracting. For slime, the switch writes only the corresponding X, Y, or Z velocity component to that direction's offset. For every admitted moved-block type, the code initializes three displacement doubles to zero, computes the selected-axis edge overlap plus `0.01`, and requests `entity.move(d * offsetX, e * offsetY, f * offsetZ)`. Order and expressions are identical across the pair.
- In `tick`, `lastProgress` is copied from `progress` first. If that previous value is at least `1.0F`, `moveEntities()` occurs before removing/marking the block entity and conditionally replacing the moving block. Otherwise progress increases by `0.5F`, clamps at `1.0F`, then `moveEntities()` runs. This order is identical across the pair.
- Adjacent piston block setup, `net/minecraft/block/PistonBaseBlock.java`, `move` around lines 276–342: both sides choose `direction = extend ? facing : facing.getOpposite()`, create moving block entities for reversed move-list entries, and create the extension-head moving entity only on extension. One source-state write differs: clearing each moved block uses update flag `4` in 1.9 and `2` in 1.9.1 (around line 310). This is outside the callback algorithm; I record the raw flag change without assigning it a movement, update-propagation, clipping, or trajectory consequence.

## Relevant source hashes

| File | 1.9 SHA-256 | 1.9.1 SHA-256 |
|---|---|---|
| `net/minecraft/block/entity/MovingBlockEntity.java` | `7ad3426bb74c9e69b4f521d20e045b1dc1eafa9b725daf0e2c0b9b718bc2683c` | `7ad3426bb74c9e69b4f521d20e045b1dc1eafa9b725daf0e2c0b9b718bc2683c` |
| `net/minecraft/block/PistonBaseBlock.java` | `d2b396408dc26daf9fba18ccac8cc1bfa4bc197042c2629e8df062a66d61f639` | `58387062a4d8b07843e0086edcf80c7bf53f9e6c03afe52a72284e847a8c0bf1` |
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| `net/minecraft/world/chunk/WorldChunk.java` | `b98bccf16277a5a62b09ac2e77db7d77e098a62d8029c03a44fbed62e01ff267` | `b98bccf16277a5a62b09ac2e77db7d77e098a62d8029c03a44fbed62e01ff267` |

Frozen for subsequent exact-author-claim review. No product files, campaign records, or other outputs were read or written.