# F-S7.4-BOAT-RIDER-ROTATION: boat yaw is conditionally applied to passengers

1.20.2, mojmap: `1.20.2/mojmap/net/minecraft/world/entity/vehicle/Boat.java`, positionRider 700-709.
1.20.4, mojmap: `1.20.4/mojmap/net/minecraft/world/entity/vehicle/Boat.java`, positionRider 665-676.

Difference: In 1.20.2, each Boat rider receives the boat's `deltaRotation` in both body yaw (`YRot`) and head yaw, followed by `clampRotation`. In 1.20.4, those writes occur only when the rider entity type is not in `EntityTypeTags.CAN_TURN_IN_BOATS`. The additional animal passenger rotation branch is inside the same new guard. The direct x/y/z passenger placement call remains the unchanged shared superclass call.

Movement meaning: Client packet handling can attach the local player from a `ClientboundSetPassengersPacket`: 1.20.2 `net.minecraft.client.multiplayer.ClientPacketListener.handleSetEntityPassengersPacket(ClientboundSetEntityPassengersPacket)` lines 923-950 1.20.4 same member lines 953-980 its brace-bounded method hash is identical, . The player then follows `net.minecraft.world.entity.Entity.rideTick()` (1.20.2 lines 1827-1833 1.20.4 lines 1828-1834 that method dispatches to its vehicle's `positionRider`, so Boat's override receives the rider entity. This callback directly writes the player's yaw/head yaw when its condition passes. The 1.20.4 source declares `EntityTypeTags.CAN_TURN_IN_BOATS`, but the supplied exact-version source roots do not establish the default tag members. Resolving the candidate requires exact 1.20.4 default tag membership; do not infer it from the tag name or the Java declaration. Source-proven: 1.20.4 adds a type-tag condition around Boat rider yaw and head-yaw writes; the shared rider-position writer is called before that condition and is unchanged. If a Player is absent from the 1.20.4 tag, those rotation writes remain reachable each Boat rider tick. If Player is present, the writes are skipped. Because player travel reads yaw to orient movement input, a changed yaw can change subsequent movement direction; this is a predicted consequence, not an observed trajectory.


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
      "version": "1.20.2",
      "mapping": "mojmap",
      "path": "1.20.2/mojmap/net/minecraft/world/entity/vehicle/Boat.java",
      "member": "positionRider",
      "lines": "700-709",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8d01cdf2502e17ffd03f85c4add4b87316baaeea609f40a3d6f66d0a3c110686; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: d4e2eb2b32ae7bd2e95ea3a8391813a7b6795e20884cf01d989dce68f906e2fd *1.20.2/client_mappings.txt. Canonical source-manifest SHA-256 e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.4",
      "mapping": "mojmap",
      "path": "1.20.4/mojmap/net/minecraft/world/entity/vehicle/Boat.java",
      "member": "positionRider",
      "lines": "665-676",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f0296d2198d1d0d1f65162126197249789c5d22f081841af454af3a074024109; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f *1.20.4/client_mappings.txt. Canonical source-manifest SHA-256 fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Exact 1.20.4 default CAN_TURN_IN_BOATS resource membership for Player; the Java declaration alone cannot select the player branch.",
      "next_action": "Resolve exact bundled tag bytes from the archived artifact publication with source owner. No new discovery/regeneration is authorized."
    }
  ],
  "runtime": "unverified"
}
```
