# 1.8.9 → initial 1.9: local movement packet selection

## Scope and evidence

Blind, passive comparison of the local player’s ordinary tick packet selection and last-sent movement bookkeeping, plus the normal client handler for a server player-position correction. Exact source roots: `decompiled_minecraft/1.8.9/ornithe-feather/` and `decompiled_minecraft/1.9/ornithe-feather/`. This is a bounded operation report, not a full-pair movement inventory or independent coverage audit.

The 1.8.9 source provenance identifies `versionId: 1.8.9`, namespace `ornithe-feather`, Feather Gen2 1.8.9 build.2 artifacts, and the exact decompile run. The 1.9 source manifest pins the 1.9 client artifact, Feather Gen2 1.9 build.2 mapping artifact, mapping metadata, and decompiler/toolchain. Relevant files’ direct SHA-256 values match their source manifests:

| Release | Source file | SHA-256 |
|---|---|---|
| 1.8.9 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `1762b116e6b06d682b7daa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` |
| 1.8.9 | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3` |
| 1.9 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06` |
| 1.9 | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6` |

Metadata files checked: `decompiled_minecraft/1.8.9/ornithe-feather.provenance.json`, `decompiled_minecraft/1.8.9/ornithe-feather.sources.sha256`, and `decompiled_minecraft/1.9/ornithe-feather/.decompile-provenance.json`. Tool versions recorded there include Java 25.0.3, Vineflower 1.12.0, Mapping IO 0.9.1 and Tiny Remapper 0.14.1.

## Ordinary tick packet selection

- 1.8.9 `LocalClientPlayerEntity.tick()` and `sendMovementToServer()`, lines 105–177. The position delta and rotation delta are tested against sent coordinates/angles; `ticksSinceSentPosition >= 20` is tested before the counter is incremented at line 165. If no position/rotation packet branch matches, the code unconditionally sends the ground-only `PlayerMoveC2SPacket` at lines 154–157.
- 1.9 same class, lines 141–218. It increments `ticksSinceSentPosition` at line 187 before testing `>= 20` at line 188. It sends a ground-only packet only when `sentOnGround != onGround` (lines 200–202), and stores `sentOnGround = onGround` at line 216. Position sent coordinates and the counter are still reset only under `bl3` (lines 204–209); sent yaw/pitch are still updated only under `bl4` (lines 211–214).
- Thus, with no movement or look change and the same ground state, the 1.9 periodic position threshold is reached one tick earlier: the old method tests a counter value of 20 on the next call, while the new method increments to 20 and tests during that call. This is a source-derived timing consequence, assuming the same `isCamera()` path and no intervening positional send. Ground-only packets also stop repeating every such tick in 1.9; they are emitted when the tracked ground state changes. The position/rotation sent-value write gates otherwise match in this slice.
- `tick()` keeps the loaded-chunk gate and calls the same sender when not riding (1.8.9 lines 105–114; 1.9 lines 141–153). 1.9 also sends a vehicle movement packet from its riding branch; vehicle movement/network behavior is outside this assignment’s scope.

## Legitimate player-position correction / teleport confirmation

- 1.8.9 `ClientPlayNetworkHandler.handlePlayerMove()`, lines 517–565, applies the correction and immediately sends a position-and-angles movement packet at lines 551–557. It has no teleport acknowledgement in this handler.
- 1.9 corresponding handler, lines 545–594, applies the correction, sends `AcceptTeleportC2SPacket` at line 580, then sends the same kind of immediate position-and-angles packet at lines 581–586. Its first-start `lastX/lastY/lastZ` initialization remains after these writes, like 1.8.9 (1.8.9 lines 558–564; 1.9 lines 587–593).
- The added acknowledgement is a correction-handler write, not a change to the periodic movement sender’s timer, sent position/rotation fields, or reset conditions. The handler itself does not update `ticksSinceSentPosition`, `sentX/Y/Z`, or `sentYaw/Pitch`. It therefore adds no separate local movement timing/bookkeeping change in this bounded slice. The corrected player state and immediate correction response can of course affect later ordinary movement; correction math and actual network interaction are outside scope here.

## Boundaries

No crafted or malformed packet behavior, server movement simulation, correction math/velocity formulas, vehicle physics, actual network timing, alternate movement environments, implementation, launch, build, or tests were examined.
