# Independent verdict: 1.9 → 1.9.1 local movement-packet selection

## Reviewed claim and identity

Reviewed frozen report `c-early-19-191-local-packet-selection/report.md`, SHA-256 `f58189ba768e0ecf488f323098b68fba53abe0810f8d12efad9f8910069b18f3`. Its reported evidence identity matches the canonical local inputs. Both sides use Ornithe Feather Gen2 build.2:

| Release | Client JAR SHA-256 | Feather mergedv2 mapping JAR SHA-256 | Feather metadata SHA-256 | Provenance SHA-256 |
| --- | --- | --- | --- | --- |
| 1.9 | `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307` | `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` (`1.9+build.2`) | `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371` | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` |
| 1.9.1 | `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9` | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` (`1.9.1+build.2`) | `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4` | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` |

For each inspected Java file, its byte SHA-256 also matches that release manifest's `sources` entry.

## Independent comparison

- `decompiled_minecraft/1.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, lines 141–218, SHA-256 `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06`.
- `decompiled_minecraft/1.9.1/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, lines 142–219, SHA-256 `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb`.

The paired `tick()` and full `sendMovementToServer()` region is text-identical after aligning the one-line offset. `tick()` retains its chunk-loaded gate and calls `super.tick()` before choosing the riding packet path or ordinary sender. The sender retains the same sprint/sneak action ordering, camera gate, shape-minY position deltas, 20-tick / `9.0E-4` position trigger, nonzero rotation trigger and riding special packet. Position baselines and the timer update only under `bl3`; rotation baselines update only under `bl4`; `sentOnGround` updates on each camera-gated pass. The packet branches and these writes have the same order on both sides.

The ordinary teleport-confirmation handler is also byte-identical on both sides:

- `decompiled_minecraft/1.9/ornithe-feather/net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`, `handlePlayerMove(PlayerMoveS2CPacket)`, lines 545–594, SHA-256 `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6`.
- `decompiled_minecraft/1.9.1/ornithe-feather/net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`, same method and lines, same SHA-256 `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6`.

Within that unchanged method, after applying the supplied player move, the client sends `AcceptTeleportC2SPacket`, then immediately sends `PlayerMoveC2SPacket.PositionAndAngles` with current player x/z, `getShape().minY`, yaw/pitch and `false` on-ground. The first-start branch follows those sends and writes inherited `lastX`, `lastY`, `lastZ`, then `started` and screen state. It does not assign the local sender's `sentX/Y/Z`, `sentYaw/Pitch`, `sentOnGround` or `ticksSinceSentPosition` fields.

## Verdict

**Accept the frozen no-difference claim for these bounded slices.** The ordinary packet-selection branches, local sent-state maintenance, and teleport-confirmation response/order match from 1.9 to 1.9.1. The immediate confirmation packet exists in both releases and is not evidence of a change to the periodic local sender's baseline or timer.

Scope is limited to these paired sender and handler slices. This does not establish full-pair movement coverage, trajectory parity, correction/velocity formula behavior, server replay behavior, or any behavior from crafted inputs.
