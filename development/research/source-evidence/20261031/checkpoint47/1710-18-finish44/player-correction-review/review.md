# 1.7.10–1.8 player correction response audit

## Evidence identity

- 1.7.10, Ornithe Feather: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.7.10\ornithe-feather`; provenance manifest SHA-256 `11bada441ebe07661402d1c40e1c490b78b279ecaa084f9a4dc3ae2dd54caf6f`.
- 1.8, Ornithe Feather: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8\ornithe-feather`; provenance manifest SHA-256 `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05`.
- Both manifest hashes were independently recomputed from `.decompile-provenance.json`. All source trees were read-only. Individual file SHA-256 values appear below.

## Packet representation

1.7.10 `net/minecraft/network/packet/s2c/play/PlayerMoveS2CPacket.java:7–74` contains absolute `x/y/z`, `yaw/pitch`, and `onGround`. Its constructor stores all six values; `read` consumes three doubles, two floats and a boolean (`:18–35`); `write` emits the same sequence (`:37–45`). There is no relative-axis flag or alternate coordinate interpretation in this packet. File SHA-256: `E6B4285A120E4AA75EFFDCBCE847E4F18A21D1ABC8F1900C591ED300CB80A565`.

1.8 `PlayerMoveS2CPacket.java:8–75` has the five position/rotation values plus `Set relativeArgs`, and no `onGround` field. `read` obtains an unsigned flag byte and maps it with `Argument.byFlags` (`:28–36`); `write` serializes `Argument.getFlags(relativeArgs)` as a byte (`:38–46`). `PlayerMoveS2CPacket__Argument.java:6–48` assigns X/Y/Z/YAW/PITCH indices 0–4; each bit is `1 << index`, `byFlags` tests each bit, and `getFlags` ORs the selected bits. File SHA-256 values: packet `B50A7A2F46C5866D1008801246B69314D8A49654FEDE657B0F8182741DA7D8BE`; argument enum `9F292793498CBF4355E5FF114BDCF2FF8EC1DCCAD68888EF6F2498DAF3FA5119`.

## Client handler and concrete response

The handlers are in the client network package. 1.7.10 `ClientPlayNetworkHandler.handlePlayerMove` (`ClientPlayNetworkHandler.java:480–499`) reads the packet values, clears `eyeHeightSneakOffset`, sets all three velocity components to `0.0`, then calls `updatePositionAndAngles(x,y,z,yaw,pitch)` (`:487–489`). It sends a position-and-angles acknowledgement with `x`, `shape.minY`, player `y`, `z`, the packet yaw/pitch, and the packet `onGround` (`:490–491`). It does not assign the player's `onGround` field.

1.8 `ClientPlayNetworkHandler.handlePlayerMove` (`:525–568`) first calls `PacketUtils.ensureOnSameThread(packet,this,minecraft)` (`:526`). For each XYZ axis, a relative flag adds that packet delta to the current player coordinate and preserves that axis velocity; without the flag it uses the packet coordinate and zeroes only that axis velocity (`:533–549`). Relative PITCH and YAW add to current pitch and yaw, respectively (`:551–557`); absolute rotation uses the packet value. It then calls `updatePositionAndAngles` (`:559`) and acknowledges `x`, `shape.minY`, `z`, yaw/pitch, and literal `false` for onGround (`:560`). It does not assign the player's `onGround` field either. Handler file SHA-256 values: 1.7.10 `BAE3BAA7BC005B339EAC47D800CF391C3E35B0EA0E9162AF344793D7D104E4D0`; 1.8 `1CCA0DED9F1112D407B149931C1F067A104D2BEED74F1E7070200E8C035A102D`.

`Entity.updatePositionAndAngles` is 1.7.10 `Entity.java:858–876` and 1.8 `:887–904`: both set current/last position and rotation, normalize last yaw around the ±180 boundary, then call `setPosition` and `setRotation`; neither writes onGround. Their position conventions differ. The 1.7.10 `Entity.setPosition` (`:215–223`) makes the collision-box base `y - eyeHeight + eyeHeightSneakOffset`; its `updatePositionAndAngles` also zeros the sneak offset (`:864`). In 1.8, `setPosition` (`:225–233`) uses `y` directly as box minY. For equivalent logical world positions, raw packet Y therefore is not an equal numeric input: the 1.7.10 server's correction sender passes `y + 1.62F` (`ServerPlayNetworkHandler.java:321–325`), while the 1.8 sender passes y directly (`:349–375`). After that coordinate normalization, the common absolute-position response updates the equivalent point and zeros all three velocities on both clients.

## Comparability and verdict

Under the shared absolute position/rotation subset, position and velocity outcomes are comparable after the native Y convention is normalized: each absolute XYZ axis resets its velocity, all position/rotation coordinates are updated, and no client-side `onGround` field write occurs. The 1.7.10 S2C packet alone carries an onGround bit; it is echoed in the 1.7.10 C2S acknowledgement, whereas 1.8 S2C has no such bit and the acknowledgement writes `false`. That is an acknowledgement/protocol difference, not a local player movement-state write. It does not establish an accepted player-movement pair difference.

Relative XYZ velocity preservation, relative position addition, and relative yaw/pitch addition are 1.8-only packet semantics. They have no representable 1.7.10 `PlayerMoveS2CPacket` input, so those cases cannot be claimed as a paired native 1.7.10→1.8 behavioral delta under equal representable packet inputs. The 1.8 server-side `teleport(..., relativeArgs)` overload can construct such a packet (`ServerPlayNetworkHandler.java:349–375`), but its event production is outside this response audit. A 1.7.10 client cannot natively decode the 1.8 packet schema. Consequently, behavior for an old movement profile running on a newer client/protocol when a newer server sends relative correction remains an **applicability-open protocol route**, pending an exact transport/profile integration contract and reachable server packet path. This finding does not infer how a Via/proxy translator maps fields.

## Logical-side gates and residual

These are client receive handlers; their writes run on the logical client. Neither handler branches on `world.isClient`/`isMultiplayer`, and neither executes historical physics on a server. The added 1.8 `ensureOnSameThread` is a client-thread scheduling gate, not a logical-side or movement-profile gate. Server correction production is in `ServerPlayNetworkHandler`; its presence does not show historical server physics or a reachable nonempty relative correction for the assigned old-profile route.

Residual: packet framing and client response are source-confirmed, including a server-side sender capable of relative args in 1.8. The audit did not follow gameplay/event callers that invoke the sender, protocol translators, connection/profile negotiation, or modern-client profile dispatch. No implementation, decompilation, build, test, or launch was performed.
