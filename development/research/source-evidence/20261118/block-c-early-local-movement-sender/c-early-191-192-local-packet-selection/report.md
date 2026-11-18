# 1.9.1 → 1.9.2 local movement packet writer slice

## Scope result

No source difference was found in the assigned slice: local-player tick dispatch, ordinary movement packet selection and sent-position/rotation/ground pacing state, plus the ordinary player-position response's teleport acknowledgement and immediate movement packet. This is bounded no-difference coverage, not a movement-parity conclusion and not a permanent change finding.

## Exact source identity

Both sides are canonical local decompilations under `decompiled_minecraft/<version>/ornithe-feather/`, using Ornithe Feather Gen2 `build.2` mappings. Each provenance manifest was hashed from its actual bytes; each listed Java source hash below was recomputed from the actual file and matched the corresponding manifest `sources` entry.

| Release | Provenance manifest SHA-256 | Raw client SHA-256 (pinned input) | Feather mapping artifact SHA-256 (pinned input) |
| --- | --- | --- | --- |
| 1.9.1 | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9` | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` (`1.9.1+build.2`) |
| 1.9.2 | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` | `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65` | `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` (`1.9.2+build.2`) |

| Source relative to mapping root | Exact member/range | Actual SHA-256 on both sides; matches manifest |
| --- | --- | --- |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `tick()` 142–156; `sendMovementToServer()` 158–219; `isSneaking()` 576–580; `isCamera()` 596–598 | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `handlePlayerMove()` 545–594; acknowledgement and immediate movement write 579–591 | `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6` |
| `net/minecraft/network/packet/c2s/play/PlayerMoveC2SPacket.java` | packet type definitions and constructors | `acc2ec0bf5f5ef64bff47b411a065ee3c41b07053c95de9a5ed19d2eb2f941a7` |
| `net/minecraft/network/packet/c2s/play/AcceptTeleportC2SPacket.java` | acknowledgement packet | `fb1c5b363e88df9a1edfa02fd569b4c0890e253d837724dc81a7aba08af61603` |
| `net/minecraft/entity/Entity.java` | `updatePositionAndAngles()` 985–1003; riding/sneaking/sprinting predicates 1588–1608; `getShape()` 2029–2031 | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `tick()` 169–245; this method range is identical although the full file differs between releases | Not used as a whole-file no-difference claim |

## Compared behavior

`LocalClientPlayerEntity.tick()` performs the same loaded-chunk gate and invokes the same superclass tick before choosing the mounted or unmounted send path. Its writer is byte-identical across the pair. In the camera branch it computes position deltas from `x`, `box.minY`, and `z`, increments `ticksSinceSentPosition`, and selects position reporting when squared displacement exceeds `9.0E-4` or the counter reaches 20. Rotation is selected on nonzero yaw/pitch deltas. It sends position-plus-rotation, position-only, rotation-only, or ground-only packets in the same order and under the same gates. The mounted branch sends the velocity-X/`-999.0`/velocity-Z sentinel position-plus-rotation packet and clears the position-report flag. Sent position and the pacing counter update only when that flag remains true; sent rotation updates only when rotation changed; sent ground state updates after the selection chain.

`handlePlayerMove()` applies the resolved position/angles, sends `AcceptTeleportC2SPacket`, then sends an immediate `PlayerMoveC2SPacket.PositionAndAngles` built from the player's current `x`, shape minimum Y, `z`, yaw and pitch with `onGround=false`. The handler, packet classes and directly used `Entity` methods have matching full source hashes on both sides, so this acknowledgement/write bookkeeping has no patch-boundary difference in this pair.

The claim is limited to this writer and its directly read predicates/shape and correction acknowledgement write. Upstream historical movement formulas and resulting trajectories are not assessed here.
