# Direct player position correction receiver: 1.16.3–1.16.5

Disposition: no difference found across the bounded receiver slice. Under the same initial player state and the same `ClientboundPlayerPositionPacket`, the three versions perform the same relative/absolute position and velocity selection, old-position updates, vehicle dismount gate, rotation resolution, position write, and teleport acknowledgement sequence.

## Evidence identity and integrity

All source paths below are relative to `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap\` and use Mojmap names. Exact source was read locally; no online mappings or wiki material was used.

- `1.16.3`: `.decompile-provenance.json` pins the 1.16.3 client JAR (`da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`), client mappings (`c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`) and 1.16.3 metadata (`193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead`). The manifest's source hashes match the examined files.
- `1.16.4`: `.decompile-provenance.json` pins the 1.16.4 client JAR (`d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`), client mappings (`93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`) and 1.16.4 metadata (`667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`). The manifest's source hashes match the examined files.
- `1.16.5`: `mojmap.provenance.json` identifies version `1.16.5`, mapping `mojmap`, Vineflower `1.12.0`, and the exact run; `mojmap.ready.json` identifies metadata `1.16.5`, pins client JAR `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`, and binds source manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`; `mojmap.sources.sha256` matches the examined files.

Examined source hashes (SHA-256):

| Release | `ClientPacketListener.java` | `ClientboundPlayerPositionPacket.java` |
| --- | --- | --- |
| 1.16.3 | `4d043b866c970910ef079334b0d506f7bc0f78f679e5225f11a0418bab7a72f9` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` |
| 1.16.4 | `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` |
| 1.16.5 | `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` |

The selected handler body, source lines 671–740 inclusive in each release, has SHA-256 `89ba00bea0de4df0a779b3c09bd4e733e5e772db4e2022be727c0ee7d2f5733b` in all three sources. The packet class is byte-identical across all three according to its matching source-file hash.

## Compared receiver behavior

`net/minecraft/client/multiplayer/ClientPacketListener.java`, `handleMovePlayer(ClientboundPlayerPositionPacket)` (lines 671–740), and `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` (lines 9–121) were compared in 1.16.3, 1.16.4 and 1.16.5.

The packet carries three doubles, yaw and pitch floats, relative-argument flags and an ID. `read` decodes XYZ, yaw, pitch, an unsigned flag byte and a VarInt ID; `handle` dispatches to `handleMovePlayer`. The flag set has X, Y, Z, Y_ROT and X_ROT entries, with the same bit assignments in all three versions.

For each position axis, the receiver first reads the current velocity. With the axis marked relative, it adds the packet component to the player's current coordinate and corresponding old coordinate, while preserving that velocity component. Without the flag, the packet component is the target coordinate and old coordinate, while the resulting velocity component is `0.0`. This same branch and update ordering is present for X, Y and Z in all three versions.

After computing the three axes, the handler removes the vehicle only when `tickCount > 0` and a vehicle is present. It then writes raw position, sets `xo/yo/zo`, and writes the selected velocity. For orientation, packet pitch/yaw are used directly unless their respective X_ROT/Y_ROT flags are set, in which case the existing pitch/yaw are added. It calls `absMoveTo` with the computed coordinates and rotations, sends the accept-teleportation packet followed by a position-and-rotation packet, and clears the initial screen on the first accepted correction. The order and gates match exactly in all three releases.

Thus every receiver-side state update is equivalent when the initial player state and incoming payload are held equal. This is a source comparison, not a claim about server-generated payloads or a runtime trajectory.

## Evidence limits

This bounded disposition covers only the direct inbound `ClientboundPlayerPositionPacket` payload and the `handleMovePlayer` receiver, including relative flags, existing velocity/position/rotation baselines, the receiver's vehicle-removal gate and its immediate packet acknowledgements. It does not cover server production of correction packets, collision-based numeric position reconstruction, later client ticks, other packet handlers, or 1.17 and later teleport handling. No difference was found within this receiver slice; this is not full movement inventory coverage or runtime parity evidence.