# Farmland landing conversion and local-player position response

Scope: exact-source trace for 1.10.2 → 1.11 → 1.11.1 → 1.11.2. This is a bounded source finding, not a trajectory/parity result or pair-freeze claim.

## Source identities

All four published trees identify the version and namespace as `<version>/ornithe-feather`, with the source manifest hash recorded in the matching `ornithe-feather.ready.json`. The verified source-manifest hashes are:

| Release | Client jar SHA-256 | Feather mapping artifact | Mapping artifact SHA-256 (mergedv2 jar) | Source manifest SHA-256 |
|---|---|---|---|---|
| 1.10.2 | `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9` | `feather-gen2-1.10.2+build.2` | `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` |
| 1.11 | `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634` | `feather-gen2-1.11+build.2` | `658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44` | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` |
| 1.11.1 | `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6` | `feather-gen2-1.11.1+build.2` | `83c2f49749b8eff663bf67664204e9cf02c75174e264d4cd57a6ba9f01bd2928` | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` |
| 1.11.2 | `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2` | `feather-gen2-1.11.2+build.2` | `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757` | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` |

The 1.11 and 1.11.1 provenance records resolve their requested IDs to those exact release IDs and record Mojang client, version metadata, mapped-client, and Feather inputs. Their mapping files are `feather-gen2-1.11[.1]+build.2-mergedv2.jar` and corresponding `.tiny` files. The 1.10.2 and 1.11.2 release-parent provenance records name their exact version IDs, `ornithe-feather` namespace, Feather mapping artifacts and batch inputs; the release-parent `artifacts.sha256` manifests bind the client jars, version JSON, and mappings. The matching source manifests and canonical source files were checked directly. No mapping-root `.decompile-provenance.json` file exists in these published source roots; release-parent provenance, ready markers and artifact/source manifests are present.

Relevant selected source SHA-256 values:

| Source member | 1.10.2 | 1.11 | 1.11.1 | 1.11.2 |
|---|---|---|---|---|
| `net/minecraft/block/FarmlandBlock.java` | `63d9048ebed65b890c370a1da6e79733904f67d953aff9fd8c0316f97f9d4e7f` | `397c8f2ed0152b8b67bd6a064f82b0e833bd6bc6c85aba58486da5747ea2309c` | `09e260528fd78b4bbdbc302726c7ae937fbc49f17b674c4a7ad802e0f7b6f5e8` | same as 1.11.1 |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `af3efba0a1d5a0824c29c4af4ac7f8e4484d1787a82208e4d72bcc65f957e656` | `cef5d643eca0b32119571eff5201705cc8c99125f8266f763172202d89e3a40b` | `65d362c00de1adfd55fae5f858b91bf9b227525b987c09a2d223279376a66175` | same as 1.11.1 |

## Source trace

In every release, farmland's `FarmlandBlock.SHAPE` is one block wide with height `0.9375`; `getShape` returns that shape (`FarmlandBlock.java`: 1.10.2 lines 20–34; 1.11+ lines 20–32). The player therefore contacts the farmland top below the full-block dirt top.

On a landing, `Entity.move` resolves the collision, sets position from its box, determines the block under the player's feet, then calls `checkFallDamage` (`Entity.java`: 1.10.2 lines 630–650; 1.11 lines 652–672; 1.11.1/1.11.2 lines 693–713). `Entity.checkFallDamage` invokes `state.getBlock().onFallenOn(...)` only when landed and `fallDistance > 0` (1.10.2 lines 808–816; 1.11 lines 830–838; 1.11.1 lines 880–888; 1.11.2 lines 873–881). Server player movement also calls its `handleFall` after movement processing and routes through the same superclass fall callback (`ServerPlayNetworkHandler.handlePlayerMove`: 1.10.2 lines 367–480; 1.11 lines 369–482; 1.11.1/1.11.2 lines 369–487; `ServerPlayerEntity.handleFall`: 1.10.2 lines 581–598; 1.11 lines 585–602; 1.11.1/1.11.2 lines 606–623).

`FarmlandBlock.onFallenOn` in 1.10.2 and later has the same production gates: `!world.isClient`, random float `< fallDistance - 0.5F`, entity is living, entity is a player or `mobGriefing` is enabled, and `width * width * height > 0.512F`. At 1.10.2 line 67 it directly writes `Blocks.DIRT.defaultState()` and then calls the superclass. There is no entity repositioning in that conversion.

In 1.11, 1.11.1 and 1.11.2, the same gated landing path calls `setDirt(world, pos)` instead (1.11 `FarmlandBlock.java` lines 59–68; 1.11.1/1.11.2 lines 59–68). The new helper first writes the dirt state, then gets the dirt collision shape moved to the block position, then iterates `world.getEntities(null, box)`, setting each intersecting entity's position to `(entity.x, box.maxY, entity.z)` (1.11 `FarmlandBlock.java` lines 71–78; same lines in 1.11.1/1.11.2). This is a direct server-side entity position mutation for the falling player when the on-fall gates pass. It uses the top of the full dirt collision box, replacing the farmland contact height `y + 0.9375` with `y + 1.0` for entities in the queried box. The 1.11.1 and 1.11.2 `FarmlandBlock.java` hashes are identical; the 1.11 change remains in both.

The helper itself has no logical-side guard: dry farmland decay (`tick`, 1.11 `FarmlandBlock.java` lines 45–57) and solid-block-above replacement (`neighborChanged`, lines 97–101) also call it. The landing/trampling caller is the one explicitly server-gated. Do not generalize that gate to every helper caller.

The server movement handler's order matters: it performs movement and collision validation first, calls `player.handleFall(...)` near the end, then saves `lastGoodX/Y/Z` from the resulting player coordinates. Its explicit `teleport(...)` calls are in earlier too-fast / invalid-collision correction branches; the farmland callback occurs after those checks. The callback's `setPosition` does not itself call `ServerPlayNetworkHandler.teleport` or construct/send a `PlayerMoveS2CPacket`. Thus this source does not show an immediate player teleport packet emitted by the landing conversion. A later movement/check outcome could cause a correction, but its occurrence and payload are conditional and are not established here.

On the client, `ClientPlayNetworkHandler.handleBlockUpdate` only ensures the packet is handled on the client thread and calls `world.setBlockStateFromPacket(pos, state)` (1.10.2 lines 644–646; 1.11 lines 657–659; 1.11.1/1.11.2 lines 657–659). It does not call `FarmlandBlock.setDirt` or position the local player. `handlePlayerMove(PlayerMoveS2CPacket)` resolves relative coordinates and calls the local player's `updatePositionAndAngles(...)` (1.10.2 lines 552–586; 1.11 lines 565–599; 1.11.1/1.11.2 lines 565–599). That is a distinct inbound position path, and the handler body is identical in 1.11.1 and 1.11.2.

## Finding and limits

The bounded source difference is the addition in 1.11 of a full-dirt-top reposition for every entity in the replacement dirt collision box; 1.10.2 only changes the block state in the gated landing callback. This reposition is produced server-side for landing conversion. On receipt of an equal `BlockUpdateS2CPacket`, the client-side block handler does not produce a new direct local-player position response. On receipt of equal `PlayerMoveS2CPacket` values, the local player's existing position update call is the same across these releases. A later server-authored position correction remains conditional; this trace does not claim one always occurs or prescribe server physics replay.
