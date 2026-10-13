# EXT-05: End portal transfer has an extra forced-spawn teleport in 1.8.9

- **1.8.9**, ornithe-feather: `ServerPlayerEntity.java`; lines 440-463.
- **1.9.4**, ornithe-feather: `ServerPlayerEntity.java`; lines 458-487.

## Difference

When 1.8.9's forced spawn point is non-null, 1.8.9 first teleports the player to that coordinate and sends the corresponding server-to-client position packet.

The subsequent `PlayerManager` transfer helper replaces that position with the destination world's End spawn point, and the manager sends its final teleport packet. 1.9.4 skips the early forced-spawn teleport and performs the shared destination-spawn assignment/final teleport, while also using its newer teleport acknowledgement state.

The source establishes an extra intermediate position write and correction packet in 1.8.9; it does not establish a client tick or player trajectory between packets.

The final destination position writer is the same.

## Movement meaning

When 1.8.9's forced spawn point is non-null, 1.8.9 first teleports the player to that coordinate and sends the corresponding server-to-client position packet.

The subsequent `PlayerManager` transfer helper replaces that position with the destination world's End spawn point, and the manager sends its final teleport packet. 1.9.4 skips the early forced-spawn teleport and performs the shared destination-spawn assignment/final teleport, while also using its newer teleport acknowledgement state.

The source establishes an extra intermediate position write and correction packet in 1.8.9; it does not establish a client tick or player trajectory between packets.

The final destination position writer is the same.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/EXT-05-end-portal-transfer-position-sequence.md).
