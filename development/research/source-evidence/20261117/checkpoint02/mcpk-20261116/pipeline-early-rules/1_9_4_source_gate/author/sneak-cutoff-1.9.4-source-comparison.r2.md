# 1.9.4 source comparison: pose resizing and mob velocity cutoff

## Scope and verdict

Bounded comparison of exact Feather sources for 1.9, 1.9.1, 1.9.2, 1.9.3 and 1.9.4, limited to (a) `PlayerEntity.updatePlayerPose`, its `Entity.setSize` helper and direct player callers, and (b) the per-component velocity zero cutoff in `LivingEntity.mobTick`. No other movement operation or full-pair coverage is assessed.

**Result:** both operations are equal in 1.9.4 to each of 1.9–1.9.3. No operation-level difference was found.

## Exact source identities and manifest checks

All paths below are relative to the repository root. All five trees use the `ornithe-feather` namespace. For 1.9–1.9.3 the exact client, Feather mapping artifact/metadata and every source-file digest are recorded in that tree's `.decompile-provenance.json`; the selected Java file SHA-256 values below equal the corresponding `sources` entries. Those provenance files identify the release through their exact Mojang client and Feather input URLs; their sidecar digests are recorded here to bind this packet's inputs.

| Release | Client input SHA-256 | Feather merged mapping SHA-256 | Provenance sidecar SHA-256 |
|---|---|---|---|
| 1.9 | `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307` | `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` |
| 1.9.1 | `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9` | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` |
| 1.9.2 | `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65` | `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` |
| 1.9.3 | `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b` | `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3` | `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` |

The 1.9.4 canonical publication identity was verified against the actual marker bytes and manifests, not assumed from the supplied values:

| Binding | Actual SHA-256 | Ready marker expected | Check |
|---|---|---|---|
| `decompiled_minecraft/1.9.4/ornithe-feather.ready.json` bytes | `3d0a810d9f3a93233d880ca07ffc806ea232230d33197197ad50ba69181171c6` | — | Read; `status=ready`, `versionId=1.9.4`, `mapping=ornithe-feather` |
| `ornithe-feather.sources.sha256` bytes | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | Match |
| `artifacts.sha256` bytes | `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77` | `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77` | Match |
| 1.9.4 client artifact entry in `artifacts.sha256` | `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908` | same `clientSha256` in ready marker | Match |

The ready marker reports 1,819 source files, 37 artifacts and 9,320,183 source bytes. Its source and artifact manifest identities therefore bind the source hashes and artifact entries cited here. Sidecar SHA-256 values: `ornithe-feather.verification.json` = `9ef993b06552037873604f912cce078845241b6e6f3b9799a83795abd1adf688`; `ornithe-feather.provenance.json` = `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd`. Verification states all 1,819 published source files match, with zero source-file differences; the single artifact difference is the regenerated derived remapped jar, while raw input artifacts have no differences. It does not affect the source identity used here.

## Source files and method digests

File SHA-256 values were recomputed from the published source bytes and checked against the per-release manifest (`.decompile-provenance.json` `sources` for 1.9–1.9.3; `ornithe-feather.sources.sha256` for 1.9.4).

| Release | `PlayerEntity.java` SHA-256 | `Entity.java` SHA-256 | `LivingEntity.java` SHA-256 |
|---|---|---|---|
| 1.9 | `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` |
| 1.9.1 | `d7035f08f03b01823dff70ca504f7244f9b0753542cc0f6dd7301e66f8866b71` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` |
| 1.9.2 | `1fc5107b9b7f5711105d521a28829f2466fe9f297e832f4d209e6ec422111aac` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` |
| 1.9.3 | `ef96c8a00443f724eadad05a6a4dcc8a8a2e23cd4c3390b9e0f1f78c9ff4d1af` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| 1.9.4 | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |

The called `World.getCollisions(Box)` source was also rechecked and bound to the same per-release source manifests:

| Release | `World.java` SHA-256 |
|---|---|
| 1.9 | `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` |
| 1.9.1 | `cdda70c97f0e4d6f6c454c8f6d3c0d47f0e0ae612e75062d8fe93ddd3963e6b0` |
| 1.9.2 | `cdda70c97f0e4d6f6c454c8f6d3c0d47f0e0ae612e75062d8fe93ddd3963e6b0` |
| 1.9.3 | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` |
| 1.9.4 | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` |

Normalized method-body digest contract: extract from the method declaration through its matching closing brace; remove block and line comments; remove all whitespace; SHA-256 the resulting UTF-8 bytes. This neutralizes line-ending/formatting changes while retaining identifiers, literals, operators and operation order.

| Method | 1.9 range / digest | 1.9.1 range / digest | 1.9.2 range / digest | 1.9.3 range / digest | 1.9.4 range / digest |
|---|---|---|---|---|---|
| `PlayerEntity.updatePlayerPose()` | `PlayerEntity.java:284–308` / `b85aa173180cc012f8c44993d806f6551651d9f05891f94454fee20161f334bc` | `:284–308` / same | `:284–308` / same | `:285–309` / same | `:285–309` / same |
| `Entity.setSize(float,float)` | `Entity.java:256–267` / `65bb7335e2078d6f7b05c4fa92330a9c1680f23ab5026880d8f3c14c64440fd0` | `:256–267` / same | `:256–267` / same | `:259–270` / same | `:259–270` / same |
| `LivingEntity.mobTick()` | `LivingEntity.java:1632–1703` / `edb2b775675c93e42f016143bcc373c1247771e59f6dfb1b21b05f60b2963db6` | `:1636–1707` / same | `:1636–1707` / same | `:1645–1716` / same | `:1645–1716` / same |
| `World.getCollisions(Box)` | `World.java:1012–1049` / `d63400a365e5b4bb28893057c3238f3f249209a54d0ab4cd5b3facba6e2d4869` | `:1012–1049` / same | `:1012–1049` / same | `:1018–1055` / same | `:1018–1055` / same |

## Operation findings

### Pose sizing and collision rejection

`decompiled_minecraft/<version>/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `updatePlayerPose()` (ranges above): dimensions are selected in this order: fall-flying `(0.6F, 0.6F)`, sleeping `(0.2F, 0.2F)`, sneaking `(0.6F, 1.65F)`, otherwise `(0.6F, 1.8F)`. If either selected dimension differs from the current field, the method obtains the current shape and makes a candidate box from its existing `minX/minY/minZ` to `minX + width`, `minY + height`, `minZ + width`. It queries `world.getCollisions(candidate)`. An empty result allows `setSize(width,height)`; any collision leaves the current size and current box unchanged. No resize call is made when the dimensions already match. The method and each gate, selection priority, box coordinate, query and write are digest-identical in every compared release.

The exact overload is `World.getCollisions(Box): boolean` (`decompiled_minecraft/<version>/ornithe-feather/net/minecraft/world/World.java`, ranges above). It scans candidate-adjacent block positions, calls `BlockState.addCollisions(..., candidate, list, null)`, and returns `true` when that block-collision list becomes nonempty; it also returns `true` for its explicit out-of-world X/Z guard. It returns `false` after the scan when neither condition is met. It does not enumerate or query entity collisions: the `Entity`-accepting overload is a distinct `getCollisions(Entity, Box): List<Box>` method. Thus the pose gate is a boolean block-collision/world-boundary check, not a general entity-collision test. Its normalized body digest is the same in all five releases.

The pose method is called at the end of `PlayerEntity.tick()` in all five sources. Its placement as the final operation in that tick method is unchanged. Direct `setSize` call sites are also stable: pose change (`updatePlayerPose`); normal dimensions in `resetPos()`; death dimensions in `die(DamageSource)`; small dimensions in `trySleep(BlockPos)` at `PlayerEntity:1196`; and normal dimensions in `wakeUp(...)`. The non-pose callers' literals and order relative to adjacent statements match across releases.

`decompiled_minecraft/<version>/ornithe-feather/net/minecraft/entity/Entity.java`, `setSize(float,float)`: when dimensions differ it saves old width, writes new width and height, rebuilds the box preserving the prior minima and extending X/Z by width and Y by height. Only when width grows, this is not the first tick, and the world is not client-side, it moves by `(oldWidth - newWidth, 0, oldWidth - newWidth)`. Thus this helper's server adjustment does not execute on the logical client, and the client pose path's successful call changes dimensions/box without this compensating move. Exact body/order matches across all five versions.

**Pose behavior in 1.9.4:** equal to 1.9, 1.9.1, 1.9.2 and 1.9.3.

### Living-entity velocity zero cutoff

`decompiled_minecraft/<version>/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `mobTick()` (ranges above): after interpolation or non-local `0.98` velocity scaling, and before profiler/AI work, each component is independently tested in X, then Y, then Z order. `Math.abs(velocityComponent) < 0.003` assigns that component `0.0`; equality to the threshold is retained. The literals, double arithmetic, comparison strictness, axis order and writes are digest-identical across all five versions. `PlayerEntity.mobTick()` continues to call `super.mobTick()` in each compared release.

**Cutoff behavior in 1.9.4:** equal to 1.9, 1.9.1, 1.9.2 and 1.9.3.

## Differences and limits

Operation-level diffs for both assigned behaviors: **none**. The comparison does not accept any prior claim, establish a patch introduction boundary, or close 1.9–1.9.4 pair coverage beyond these two operations.
