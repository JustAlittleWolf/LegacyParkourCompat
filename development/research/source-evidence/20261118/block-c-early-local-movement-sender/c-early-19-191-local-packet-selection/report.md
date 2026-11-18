# 1.9 → 1.9.1 local movement-packet selection

## Evidence identity

Exact pair: Minecraft **1.9 → 1.9.1**. Mapping family: **Ornithe Feather Gen2 build.2** for each release. These are the canonical read-only decompilations at `decompiled_minecraft/<version>/ornithe-feather/`.

Pinned inputs recorded by each source tree's `.decompile-provenance.json`:

| Release | Minecraft client JAR SHA-256 | Feather mergedv2 mapping JAR SHA-256 | Feather metadata SHA-256 | Provenance manifest SHA-256 |
| --- | --- | --- | --- | --- |
| 1.9 | `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307` | `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` (`1.9+build.2`) | `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371` | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` |
| 1.9.1 | `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9` | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` (`1.9.1+build.2`) | `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4` | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` |

The source-file SHA-256 values below were checked against both the files and their manifest `sources` entries.

## Bounded comparison

**Ordinary local packet selection:** `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` — 1.9 lines 141–218 (source SHA-256 `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06`); 1.9.1 lines 142–219 (SHA-256 `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb`). The paired `tick()` and complete `sendMovementToServer()` block is text-identical with the one-line source offset.

In both releases, `tick()` calls `super.tick()` after the chunk-loaded gate, then sends riding packets or calls `sendMovementToServer()`. The ordinary sender updates sprint/sneak actions first; under `isCamera()`, it computes position deltas from `sentX/Y/Z` and `box.minY`, rotation deltas from `sentYaw/Pitch`, increments `ticksSinceSentPosition`, and selects the riding, position+angles, position-only, angles-only, on-ground-only, or no movement packet with the same gates and order. It updates position baselines and resets the position timer only when the position condition is true; it updates rotation baselines only when the rotation condition is true, then updates `sentOnGround`.

**Ordinary server teleport confirmation path:** `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`, `handlePlayerMove(PlayerMoveS2CPacket)` — lines 545–594 on both sides. The complete source file is byte-identical on both sides (SHA-256 `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6`), and its manifest entry matches. The handler applies the supplied move, sends `AcceptTeleportC2SPacket`, then immediately sends `PlayerMoveC2SPacket.PositionAndAngles` using the player's current position/shape-min-Y and rotation with `false` for on-ground. Its first-start branch separately writes inherited `lastX/Y/Z`; the local sender's `sentX/Y/Z`, rotation baselines, on-ground baseline, and position timer are distinct fields, and this handler does not write them.

## Result

**No difference found in the assigned slice.** Normal packet choice, local sent-state bookkeeping, and teleport-confirmation behavior are unchanged from 1.9 to 1.9.1. The confirmation path does send an immediate position-and-angles packet in both versions; source comparison shows no change to the periodic local writer's state or timing in this pair. This result does not cover server physics or correction/velocity formulas.
