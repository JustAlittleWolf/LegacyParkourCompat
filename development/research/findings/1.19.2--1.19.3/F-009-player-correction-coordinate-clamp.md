# F-009: Absolute player correction bypasses the X/Z entity-coordinate clamp

1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/world/entity/Entity.java`, absMoveTo 1194-1209; setPos 376-379; absMoveTo 1199-1214.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/Entity.java`, setPos 376-379; absMoveTo 1199-1214.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, handleMovePlayer 563-628; handleMovePlayer 592-667.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/server/network/ServerGamePacketListenerImpl.java`, teleport 994-1010; teleport 988-1004.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/server/network/ServerGamePacketListenerImpl.java`, teleport 994-1010; teleport 988-1004.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java`, ClientboundPlayerPositionPacket 18-52.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java`, ClientboundPlayerPositionPacket 18-52.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, handleMovePlayer 592-667.

Difference: For an absolute correction, 1.19.2's `ClientPacketListener.handleMovePlayer` resolves the coordinates, writes the player position and velocity, then calls `Entity.absMoveTo`. That helper clamps X and Z to `[-3.0E7, 3.0E7]` before setting position; Y is not clamped. 1.19.3 writes position through `Entity.setPos` and does not call `absMoveTo` from this handler, so finite packet X/Z values outside that interval remain outside the clamp. `Entity.setPos` updates the position and bounding box but applies no coordinate clamp. The paired packet codec stores and writes X/Y/Z as doubles without clamping them. The client handler therefore applies the coordinate behavior described above to the packet values it receives. The server-side `ServerGamePacketListenerImpl.teleport` helper constructs a correction packet from its coordinate arguments, but that helper alone does not establish that a vanilla producer can supply an out-of-range value. This is separate from F-005's angle normalization and the interpolation-history disposition.

Movement meaning: An external/custom server can send a correction packet with the stated coordinate; the packet codec preserves its double value, and the client receives it in `ClientPacketListener.handleMovePlayer`, which updates the local player's position, velocity and bounding box, acknowledges the teleport, and sends a movement packet using the resulting player position. The changed coordinate is therefore direct player movement state. Later movement and collision queries read that current position and bounding box. The paired vanilla `/teleport` command path is checked negative evidence for reachability: `TeleportCommand.performTeleport` constructs a `BlockPos` and rejects it with `INVALID_POSITION` when `Level.isInSpawnableBounds` is false, before the player connection teleport call. `Level.isInWorldBoundsHorizontal` requires both X and Z block coordinates to be at least `-30000000` and strictly less than `30000000`. Thus ordinary vanilla `/teleport` does not establish a producer for finite X/Z coordinates beyond the client clamp. 1.19.2/1.19.3 `TeleportCommand.java` are byte-identical 1.19.2 `Level.java` 1.19.3 `Level.java` Source-proven: when the client receives the stated external/custom-server correction, 1.19.2 clamps the corrected client X/Z position while 1.19.3 retains the packet value. The position and bounding box consumed by subsequent movement differ at the correction boundary.



## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "No catalog Change implements the operation stated in this record. The current 104 annotated Changes/catalog were checked; unrelated hooks do not close this operation. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [
    "Conditional client response to external/custom-server absolute packet; no ordinary vanilla occurrence claimed."
  ],
  "sources": [
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java",
      "member": "handleMovePlayer",
      "lines": "563-628",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 98453fac8b16623d7b7f94f19199fa70c3b1d4885c464a2ab521a978a58ecbd3; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "absMoveTo",
      "lines": "1194-1209",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "setPos",
      "lines": "376-379",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "setPos",
      "lines": "376-379",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest. Historical literal654898 is a transcription error; actual654498 file digest is bound by the unchanged exact source manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/server/network/ServerGamePacketListenerImpl.java",
      "member": "teleport",
      "lines": "994-1010",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 976992cd0cf4ce387e12543861aab66cc7cf8a58f078f00e0fe8f91df47b4cd2; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/server/network/ServerGamePacketListenerImpl.java",
      "member": "teleport",
      "lines": "994-1010",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 81288e1e99d88796e096c1386e8a1f108a077078467d4843a56d14f7cb48006b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java",
      "member": "ClientboundPlayerPositionPacket",
      "lines": "18-52",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ecdfc5706756b92c6ab6de7d0cf467f42ce4952c9ca02237ca96d7c1053745d0; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java",
      "member": "ClientboundPlayerPositionPacket",
      "lines": "18-52",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 18f1e4f2ebc30510eefb53f139bb97402cf3cf4e1ba803f95d486e983a76e114; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java",
      "member": "handleMovePlayer",
      "lines": "592-667",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 98453fac8b16623d7b7f94f19199fa70c3b1d4885c464a2ab521a978a58ecbd3; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java",
      "member": "handleMovePlayer",
      "lines": "592-667",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "absMoveTo",
      "lines": "1199-1214",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "absMoveTo",
      "lines": "1199-1214",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest. Historical literal654898 is a transcription error; actual654498 file digest is bound by the unchanged exact source manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/server/network/ServerGamePacketListenerImpl.java",
      "member": "teleport",
      "lines": "988-1004",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 976992cd0cf4ce387e12543861aab66cc7cf8a58f078f00e0fe8f91df47b4cd2; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/server/network/ServerGamePacketListenerImpl.java",
      "member": "teleport",
      "lines": "988-1004",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 81288e1e99d88796e096c1386e8a1f108a077078467d4843a56d14f7cb48006b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "ACCEPT, external/custom-server conditional client response",
      "assertion_preserved": true,
      "review_commit": "9ed69387b21f384ab800fec335e6a59b422ee027",
      "review_sha256": "0d7c2018c343fb5514a83ccd8ee7312c85f261cb7129559bb527a50d7fc1285c",
      "snapshot": "0f17dc31e6619d38752c862b9ec3a30c13b79f48",
      "snapshot_sha256": "26fca3cbdc84084458484c7cc305f684dc94cc43ccc4d77b0609a7fc296926e3",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/reconciliation/additional-evidence/0d7c2018c343fb5514a83ccd8ee7312c85f261cb7129559bb527a50d7fc1285c.md"
    }
  ],
  "openIssues": [],
  "runtime": "unverified"
}
```
