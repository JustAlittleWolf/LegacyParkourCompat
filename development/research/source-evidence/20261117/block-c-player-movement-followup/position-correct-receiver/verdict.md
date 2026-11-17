# Independent blind source audit: position-correction receiver

Verdict: **ACCEPT — no difference found within the assigned receiver slice.** For a fixed incoming `ClientboundPlayerPositionPacket` and identical pre-handler client/player state, 1.16.3, 1.16.4 and 1.16.5 execute equivalent receiver-side packet application. The concrete handler source is byte-for-byte equal across all three releases.

## Immutable assignment input

- Comparison: `position-correct-receiver-source/comparison.md`
- Required SHA-256: `89ac2ea0a19cc4056e1587e7bdcf13f316e525d70792ac83a046db94aa331dbc`
- Observed SHA-256: `89ac2ea0a19cc4056e1587e7bdcf13f316e525d70792ac83a046db94aa331dbc` (match)

## Exact source identity and equality

Canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap\` (Mojmap). The selected source hashes match the nested provenance source entries for 1.16.3/1.16.4, and the 1.16.5 source manifest is bound by `mojmap.ready.json` SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`.

| Release | `ClientPacketListener.java` SHA-256 | `ClientboundPlayerPositionPacket.java` SHA-256 | `handleMovePlayer` lines 671–740 SHA-256 |
| --- | --- | --- | --- |
| 1.16.3 | `4d043b866c970910ef079334b0d506f7bc0f78f679e5225f11a0418bab7a72f9` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` | `89ba00bea0de4df0a779b3c09bd4e733e5e772db4e2022be727c0ee7d2f5733b` |
| 1.16.4 | `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` | `89ba00bea0de4df0a779b3c09bd4e733e5e772db4e2022be727c0ee7d2f5733b` |
| 1.16.5 | `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` | `89ba00bea0de4df0a779b3c09bd4e733e5e772db4e2022be727c0ee7d2f5733b` |

The packet class is identical by full source-file hash. The 70-line handler slice was extracted and hashed independently per release; all three hashes match.

## Caller and data-dependency classification

`ClientboundPlayerPositionPacket.handle(ClientGamePacketListener)` delegates to `handleMovePlayer(this)` in all three versions (the packet source file is identical). This is an inbound protocol callback, not a player-tick/travel caller. It first schedules/enforces execution on the client thread.

The receiver’s direct inputs are packet XYZ doubles, yaw/pitch floats, relative-argument flags, and teleport ID; plus the current player’s position, velocity, old-position fields, rotation, tick count, and vehicle. Relative position flags add each packet axis to current position and old position while preserving that velocity component. Absolute axes replace the coordinate and old coordinate and zero that velocity component. Relative rotation flags add the existing player angle. The receiver then performs the same vehicle-removal gate (`tickCount > 0` and a vehicle exists), raw position/old-position/velocity writes, `absMoveTo`, teleport acknowledgement, position-and-rotation response, and first-correction screen clear in the same order.

Dependency closure for this verdict is limited to the incoming packet’s decoded fields/flags and the immediate receiver state reads/writes listed above. Server-side production of the payload, the broader network dispatch implementation, subsequent ticks, and trajectory/runtime effects were not audited. Therefore this verdict establishes receiver equivalence only when the payload and initial receiver state are held equal; it does not establish full movement coverage or runtime parity.

## Bounded slice and checks

- Compared: 1.16.3 ↔ 1.16.4 ↔ 1.16.5, Mojmap `ClientPacketListener.handleMovePlayer(ClientboundPlayerPositionPacket)` (source lines 671–740), its immediate packet callback, and the packet source class.
- Confirmed: selected file hashes equal their recorded provenance entries; packet file hash equal across releases; receiver slice hash equal across releases; immutable comparison SHA matches the assigned identity.
- Excluded: server correction-packet production, collision-based correction reconstruction, other packet handlers, following client ticks, later releases, and runtime testing.