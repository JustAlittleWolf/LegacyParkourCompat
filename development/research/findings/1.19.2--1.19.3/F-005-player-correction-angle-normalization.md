# F-005 — Absolute player corrections bypass angle normalization

1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/world/entity/Entity.java`, absMoveTo 1194-1200; setXRot 3059-3065.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/Entity.java`, absMoveTo 1199-1205; setXRot 3094-3100.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, handleMovePlayer 563-628.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/util/Mth.java`, clamp 97-103.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/util/Mth.java`, clamp 102-108.

Difference: For an absolute correction carrying pitch `100.0F`, 1.19.2 calls `absMoveTo` and sets the client player's pitch to `90.0F`. 1.19.3 calls `setXRot(100.0F)`, which accepts that finite value. The paired vanilla server teleport method can produce this input when an absolute teleport caller supplies pitch `100.0F`: it clamps its own player state through `absMoveTo` but sends the unmodified target pitch in the absolute correction packet.

Movement meaning: `ServerGamePacketListenerImpl.teleport` -> `ClientboundPlayerPositionPacket` -> client `ClientPacketListener.handleMovePlayer` -> player pitch -> `Entity.getLookAngle` -> fall-flying `LivingEntity.travel` steering. The correction packet is an external movement input; this finding relies on the exact vanilla server producer method and an out-of-range teleport argument, not an ordinary-play frequency assumption. Source-proven: 1.19.2 clamps the client pitch to 90 degrees while 1.19.3 retains 100 degrees for this packet path. Predicted only: if the player is fall-flying after the correction, the changed look vector and pitch input can change glide steering and subsequent movement. No actual teleport or trajectory is claimed. Absolute yaw normalization is also bypassed by 1.19.3, but the concrete movement path recorded here uses pitch during fall-flying.


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
  "applicability": [],
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
      "lines": "1194-1200",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "absMoveTo",
      "lines": "1199-1205",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/util/Mth.java",
      "member": "clamp",
      "lines": "97-103",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 61727897223a78846a5f1fa270d57aa714c21b3c8d6f2b0e12fea4a07e6c327f; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/util/Mth.java",
      "member": "clamp",
      "lines": "102-108",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f5b2738a7145303f8598b9876c0f6950e853110f594fc4cffa633ab7bab98d3f; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "setXRot",
      "lines": "3059-3065",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "setXRot",
      "lines": "3094-3100",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
