# Moving-fence pose-query reachability closure

## Ruling

The client-side **progress and pose-mode sequence is source-reachable** in both exact releases: an ordinary chunk snapshot can install a moving-block entity with persisted progress; the local-player entity is ticked before block entities; and a local sneak-key release can make `PlayerEntity.updatePlayerPose()` issue its standing-size collision query while the moving entity is at progress `0.5`.

The audit's **specific spatialized query remains conditional**, however. These sources do not establish a local-player trajectory that places the standing candidate box at the report's overlapping x/y/z footprint on that same tick. The proven temporal route requires chunk-snapshot receipt of the initialized moving entity (or an already initialized local entity); a block update in an already-loaded chunk is not shown to carry its moved-state/progress tag. Therefore this closes the possible client progress/posture sequence, but does not upgrade the report to a demonstrated exact moved-fence encounter. The remaining precise premise is a suitable local receipt history plus simultaneous local position/vertical state in the report's collision window when the standing resize is attempted. Server-side piston/block-state production remains outside this assessment; this is about client consumption of the supplied vanilla state.

## Source-backed client progression

The route is present in both 1.10.2 and 1.11.2 (`ornithe-feather`):

1. `WorldChunkS2CPacket(WorldChunk, sections)` serializes each included chunk block entity using `toNbt()` (packet lines 29–45). `MovingBlockEntity.writeNbt()` persists `lastProgress`; `readNbt()` restores `progress` and sets `lastProgress` to that value (1.10.2 `MovingBlockEntity.java:190–196`; 1.11.2 `:296–303`). The ordinary client `handleWorldChunk` updates the chunk state, then reads each packet block-entity tag into `world.getBlockEntity(pos)` (1.10.2 `ClientPlayNetworkHandler.java:613–632`; 1.11.2 `:626–645`). `WorldChunk`'s block-entity lookup can instantiate the provider for the received `MOVING_BLOCK` state before the handler applies its tag (`WorldChunk.java:587–603` in both). Thus chunk receipt can supply an initialized moved-fence entity and a saved progress value to client movement consumers.
2. If such a chunk is received with the entity at progress `0.0`, the client tick ordering permits the query state without any client-side placement logic. The client main tick calls `world.tickEntities()` when unpaused (1.10.2 `Minecraft.java:1438`; 1.11.2 `:1419`); that method iterates ordinary entities before `tickingBlockEntities` (1.10.2 `World.java:1273–1314`; 1.11.2 `:1274–1315`). `LocalClientPlayerEntity.tick()` enters its superclass path while the player's chunk is loaded (1.10.2 `:152–154`; 1.11.2 `:159–161`), and `PlayerEntity.tick()` invokes `updatePlayerPose()` (1.10.2 `PlayerEntity.java:250`; 1.11.2 `:249`). The later moving-block-entity tick advances progress by `0.5F` (1.10.2 `MovingBlockEntity.java:166–181`; 1.11.2 `:273–289`). On the following world tick, the player pose consumer therefore runs while progress is `0.5`, before that block entity advances again.
3. The local pose transition is also an ordinary input path. `KeyboardInput.tick()` reads the sneak key into `sneaking` (`KeyboardInput.java:13–46` in both); local `isSneaking()` reads that input (`LocalClientPlayerEntity.java:596–598` older, `608–610` newer). `PlayerEntity.updatePlayerPose()` chooses 1.65 height while sneaking and 1.8 otherwise; when those dimensions differ it constructs the candidate box at the current box minimum and calls `world.getCollisions(box)` (`PlayerEntity.java:296–319` older; `:295–318` newer). A crouched tick followed by a released-sneak tick can consequently issue the standing query during the progress-0.5 player-before-block-entity window.

This proves a source route for the *temporal co-occurrence* of a live moving fence, progress `0.5`, and the standing-resize query. It does not independently prove that the player's collision box reaches the report's specified overlap while the route is active. No source trajectory was derived for those exact coordinates, vertical state, and neighboring layout; the previous report's box remains an explicit state precondition for the changed boolean result.

A single-block update is a distinct, weaker receipt path: `handleBlockUpdate` passes only the block state to `setBlockStateFromPacket` (1.10.2 `ClientPlayNetworkHandler.java:644–646`; 1.11.2 `:657–659`). A block-state change can create a provider entity in `WorldChunk.setBlockState` (`WorldChunk.java:468–473`), but this is not itself the tag-restoration path. The separate `handleBlockEntityUpdate` applies NBT only to its explicit supported-type whitelist (1.10.2 `ClientPlayNetworkHandler.java:999–1014`; 1.11.2 `:1029–1045`), which does not include `MovingBlockEntity`. Consequently an already-loaded-chunk block update does not establish that the client has received moved-state/progress data for the newly created entity. The temporal route above is specifically the chunk-snapshot route; availability and timing of such a snapshot at the report's player location are not established here.

## Exact evidence identity

Canonical sources: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.10.2,1.11.2}/ornithe-feather`. The paired source-set identities are recorded in `world-moving/report.md`: 1.10.2 ready/source manifests `4c6d236e1d06ffc365452c48e73dfa2bf241bbf4ea67a24a7010c5583906a2d` / `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; 1.11.2 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40` / `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`.

SHA-256 (relative to each version's `ornithe-feather/net/minecraft/`):

| Source | 1.10.2 | 1.11.2 |
|---|---|---|
| `client/network/handler/ClientPlayNetworkHandler.java` | `AF3EFBA0A1D5A0824C29C4AF4AC7F8E4484D1787A82208E4D72BCC65F957E656` | `65D362C00DE1ADFD55FAE5F858B91BF9B227525B987C09A2D223279376A66175` |
| `network/packet/s2c/play/WorldChunkS2CPacket.java` | `71F802E4CF8986EE19008228E61B28B9C055494901FFCD41E742FD87D58C7F8E` | `650DCEA2255C0826493FA103F4EE0D8350888163661FE33AB7B44AA8998D3A86` |
| `world/chunk/WorldChunk.java` | `DCFCDA41F28CC205F17A547C69EFDF70AEDE8F4770A43101D20EAD08E8ED86B5` | `B80C4962661B31C9015FAA9BB3D42E390528B59473956BA20E424C76625B844F` |
| `client/Minecraft.java` | `0979D770E8742A07BB8C54CF7FD3449A8C43A4F48910600FFABF75F1F67BA2D9` | `3CA93038ADF4CEA512C21AE86D653376E963396640A02EB50CB42A67302F6236` |
| `world/World.java` | `888ED0E9DE765DEF87B05C4126ECDF0B10E9DD448B4543DD1CB98211E9951646` | `27CFAA5FF45C2D88C492FC5DAE3EA4A2BB4536FD64BFF8498A9A7D0CB82EFB58` |
| `client/entity/living/player/LocalClientPlayerEntity.java` | `A9637065F21AD67464EB5C204C74EBF228C3BB0DA8A96DDF4AE73C0490FED443` | `65C2747BD8C70DEF6BE7F41F624D4C9493342B39AE7BED7967F9FF63608F59ED` |
| `client/entity/living/player/KeyboardInput.java` | `7BE11425906BE051C83E275F359816546E4677B16D212156380E8D2E9258654A` | `7BE11425906BE051C83E275F359816546E4677B16D212156380E8D2E9258654A` |
| `entity/living/player/PlayerEntity.java` | `A055B84B98D98E828E177CAD9BBA47A1334EE9BF3ECD4331792979106235A302` | `87FE94FA6CBF7ABA18B9A5E3401664439EB8EBA9958173DA8FBCD05CC7AD948B` |
| `block/entity/MovingBlockEntity.java` | `720E1305C494A3A316B21BEEF823158C435826F50383778A5BD00AD0E4FF6AC1` | `A55EE14227FE6AC0932283CBA0414E946BA8E2637F543B3A3FE13D4E45B7B16E` |

Static source inspection only. No wiki, product implementation/history, server-side piston trajectory, builds, tests, or game launch was used.
