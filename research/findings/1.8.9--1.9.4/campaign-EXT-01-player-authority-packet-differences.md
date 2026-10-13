# EXT-01: local player movement packet selection and correction acknowledgment differ

- **1.8.9**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/EXT-01-player-authority-packet-differences.md); lines 105-115.
- **1.9.4**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/EXT-01-player-authority-packet-differences.md); lines 143-157.

## Difference

With the position counter reset to zero and no position delta above the `9.0E-4` squared-distance threshold, 1.8.9 checks the current count and then increments it, so the forced resend branch becomes true on the 21st subsequent call. 1.9.4 increments first, so it becomes true on the 20th.

On a camera tick with no position or angle report and no on-ground transition, 1.8.9 emits a ground-only movement packet; 1.9.4 emits none. 1.9.4 records its sent ground state after selecting the packet.

These claims describe client packet selection only.

For a received correction, both client handlers preserve velocity components for relative axes and set absolute-axis velocity to zero before applying resolved position and rotation.

The server release gate differs: 1.8.9 accepts a positional response within squared distance `0.25` of the pending target; 1.9.4 accepts a matching teleport ID and retains a pending target until that acknowledgment.

Both server handlers then have source paths that move/update the player and ground state.

These are code-path observations, not a simulated packet exchange or trajectory.

## Movement meaning

`LocalClientPlayerEntity.tick()` reaches its packet sender after `super.tick()` only while its current chunk is loaded.

The sender is gated by `isCamera()`. 1.8.9 riding tick sends player angles and input; 1.9.4 additionally emits a vehicle movement packet when the vehicle is locally authoritative.

Vehicle movement is excluded from this finding.

The paired sources prove different outbound packet timing/content and different server-side pending-teleport release gates.

Both server handlers assign the client-reported ground state and continue into player movement/collision handling after their respective gates.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/EXT-01-player-authority-packet-differences.md).
