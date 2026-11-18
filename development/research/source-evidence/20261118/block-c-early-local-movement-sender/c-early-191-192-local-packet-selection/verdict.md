# Independent blind audit verdict: 1.9.1 → 1.9.2 local packet selection

## Verdict

**Accepted: bounded no-difference claim is supported.** The audited claim is limited to local-player tick dispatch, ordinary local movement packet selection and its sent-state/pacing bookkeeping, plus the immediate local movement packet written after a position response. This verdict does not close pair coverage, establish movement parity, or assess upstream movement formulas/trajectory results.

## Frozen input

Audited report: `c-early-191-192-local-packet-selection/report.md`  
SHA-256 of exact report bytes: `A7AE455D7AE688D92DFED8D5CEE1917B08D862DD8F3F289ACFDB02C83D837597`.

## Source identity and integrity

Both inputs are local canonical decompilations at `decompiled_minecraft/<version>/ornithe-feather/`. The `.decompile-provenance.json` files hash to `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` (1.9.1) and `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` (1.9.2). Each manifest pins its raw client and Feather Gen2 build.2 mapping artifact:

- 1.9.1: client `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9`; Feather `1.9.1+build.2`, `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3`.
- 1.9.2: client `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65`; Feather `1.9.2+build.2`, `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859`.

Recomputed Java source SHA-256 matched the `sources` entry in each corresponding manifest. Relevant files are byte-identical across the two releases:

| Source relative to mapping root | SHA-256 on both sides |
| --- | --- |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6` |
| `net/minecraft/network/packet/c2s/play/PlayerMoveC2SPacket.java` | `acc2ec0bf5f5ef64bff47b411a065ee3c41b07053c95de9a5ed19d2eb2f941a7` |
| `net/minecraft/network/packet/c2s/play/AcceptTeleportC2SPacket.java` | `fb1c5b363e88df9a1edfa02fd569b4c0890e253d837724dc81a7aba08af61603` |
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |

`PlayerEntity.java` differs as a full file between the releases; its claimed `tick()` slice, lines 169–245, was not needed to support this bounded sender/response claim and must not be treated as whole-file no-difference coverage.

## Independent trace

- `LocalClientPlayerEntity.java`, `tick()` 142–156, uses the same loaded-chunk gate and superclass call, followed by the same mounted (`isRiding`) dispatch versus unmounted `sendMovementToServer()` call. The mounted tick sends angles and input, and conditionally the vehicle packet.
- `sendMovementToServer()` 158–219 is identical. It sends sprint/sneak action transitions, then only runs position/rotation selection when `isCamera()` is true. Deltas are `(x - sentX)`, `(shape.minY - sentY)`, `(z - sentZ)`, yaw and pitch. The position trigger is squared delta greater than `9.0E-4` or `ticksSinceSentPosition >= 20`; rotation triggers on nonzero yaw/pitch delta. Identical branch order selects position+angles, position-only, angles-only, or ground-only. The riding sentinel uses `(velocityX, -999.0, velocityZ, yaw, pitch, onGround)` and clears the position-report flag. Position baselines and timer reset update only while that flag remains true; angle baselines update only for changed angles; sent ground is then updated. These gates, values, order and writes match byte-for-byte.
- `LocalClientPlayerEntity.java`, fields 72–80 and `isSneaking()` 576–580 / `isCamera()` 596–598, confirms the sent baselines/timer are the same fields and the writer's direct local predicates are the same. `Entity.java` lines 1588–1608 and 2029–2031 confirms matching riding/sneaking/sprinting predicates and shape accessor. `updatePositionAndAngles()` lines 985–1003 is also identical for the response path.
- `ClientPlayNetworkHandler.java`, `handlePlayerMove()` 545–594, applies the resolved position and angles at 579, then sends `AcceptTeleportC2SPacket` at 580, and separately writes an immediate `PlayerMoveC2SPacket.PositionAndAngles` at 581–586 using current player coordinates, shape minimum Y, yaw/pitch and `onGround=false`. It is a response-time writer, separate from the tick-paced sender above. Both packet source files, the handler and directly read entity code hash identically on both sides.
- `PlayerMoveC2SPacket.java` constructors show the position/angles presence flags and packet payload types used by these writer branches; its source is identical. `AcceptTeleportC2SPacket.java` is likewise identical.

## Coverage boundary

No difference found in the assigned local packet writer and immediate response-write slice. Server-side packet handling/replay or correction policy, correction math beyond the direct `updatePositionAndAngles` call, velocity behavior beyond the matching direct code, upstream movement formulas, and pair-wide inventory are not accepted by this verdict.