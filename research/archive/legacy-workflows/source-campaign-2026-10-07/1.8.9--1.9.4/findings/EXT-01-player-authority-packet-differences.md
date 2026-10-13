# EXT-01: local player movement packet selection and correction acknowledgment differ

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: local-player authority packets and inbound corrections; `EXT-01`
- Classification: source-confirmed external player movement-authority behavior change; no packet-sequence trajectory simulated
- Confidence: source-confirmed for packet selection, correction writes, and pending-teleport acceptance gates; no server trajectory inferred
- Applicability: a loaded local-camera tick, including stationary ticks, and receipt of a player position correction
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A outbound: `LocalClientPlayerEntity.tick()V` lines 105-115 reaches `sendMovementToServer()V` when the local world chunk is loaded and the player is not riding; the method sends movement packets under `isCamera()` at lines 117-178. Position resend threshold is checked before the per-tick counter increment (146, 165). When position and angles are unchanged, the final branch sends an on-ground-only packet every tick (146-158). SHA-256 `1762b116e6b06d682b7daa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`.
- B outbound: same player tick gating at lines 143-157; non-riding player movement sender is lines 159-220. It increments the counter before checking the threshold (189-190), emits an on-ground-only packet only when `sentOnGround != onGround` (202-204), and stores `sentOnGround` after the branch (218). The riding branch can send a `VehicleMoveC2SPacket` for a vehicle whose movement is locally authoritative (146-152); vehicle movement is outside this finding. SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.
- A correction consumer: `ClientPlayNetworkHandler.handlePlayerMove(PlayerMoveS2CPacket)V` lines 517-557 resolves relative coordinates/angles, zeros each velocity component only when that axis is absolute, updates player position/angles, and sends a positional response. Handler SHA-256 `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3`; packet class SHA-256 `3c2c044610df527df20561b4ded36aca91c774b7d0520f9e4cfe657030bf1baf`. A server `ServerPlayNetworkHandler.handlePlayerMove()` computes the response delta from the stored teleport target and sets `teleported` when a positional reply is within squared distance `0.25` (lines 202-213); only then does it run the normal server player move path (290-316). It resends the target after a timeout (357-358). ServerPlayNetworkHandler.java SHA-256 `47ef077a7fa0f74cf44e1c8bda449d1b220bf56503b2fef98fd8016e8092df5b`.
- B correction consumer: `ClientPlayNetworkHandler.handlePlayerMove(PlayerMoveS2CPacket)V` lines 546-595 performs the same per-axis position/velocity/angle handling, then sends `AcceptTeleportC2SPacket` before the same positional response. Handler SHA-256 `5a2fc039b81ec0e77df3354826aaaf00dfa6e0d1296cf111864900932e811a4c`; `PlayerMoveS2CPacket` adds/serializes `teleportId` at lines 17-51 and has SHA-256 `8bad09e146ce746085103081b7e3c6d09855114868267e12be8d869f34f0e77e`; `AcceptTeleportC2SPacket` carries the ID at lines 8-34 and has SHA-256 `fb1c5b363e88df9a1edfa02fd569b4c0890e253d837724dc81a7aba08af61603`. B server `handleAcceptTeleport()` accepts only the requested ID, updates the player to the stored teleport position, completes teleportation state if present, and clears the pending position (ServerPlayNetworkHandler lines 348-364). While a target remains pending, `handlePlayerMove()` skips ordinary movement and resends after more than 20 ticks (367-390). ServerPlayNetworkHandler.java SHA-256 `f96587355f356f95362dbca2c57c043fdb3ee8ee2fe81589f4c3379ab1a0336b`.

## Source-level difference

With the position counter reset to zero and no position delta above the `9.0E-4` squared-distance threshold, A checks the current count and then increments it, so the forced resend branch becomes true on the 21st subsequent call. B increments first, so it becomes true on the 20th. On a camera tick with no position or angle report and no on-ground transition, A emits a ground-only movement packet; B emits none. B records its sent ground state after selecting the packet. These claims describe client packet selection only.

For a received correction, both client handlers preserve velocity components for relative axes and set absolute-axis velocity to zero before applying resolved position and rotation. The server release gate differs: A accepts a positional response within squared distance `0.25` of the pending target; B accepts a matching teleport ID and retains a pending target until that acknowledgment. Both server handlers then have source paths that move/update the player and ground state. These are code-path observations, not a simulated packet exchange or trajectory.

## Reachability and dependencies

`LocalClientPlayerEntity.tick()` reaches its packet sender after `super.tick()` only while its current chunk is loaded. The sender is gated by `isCamera()`. A riding tick sends player angles and input; B additionally emits a vehicle movement packet when the vehicle is locally authoritative. Vehicle movement is excluded from this finding.

## Consequence and uncertainty

The paired sources prove different outbound packet timing/content and different server-side pending-teleport release gates. Both server handlers assign the client-reported ground state and continue into player movement/collision handling after their respective gates. The exact resulting position/velocity for any packet sequence is not established here; no server sequence was executed. This is an external authority-path finding, not yet an implementation handoff.

## Handoff

Source finding only. Preserve for full-pair reconciliation; no server outcome or trajectory is claimed.
