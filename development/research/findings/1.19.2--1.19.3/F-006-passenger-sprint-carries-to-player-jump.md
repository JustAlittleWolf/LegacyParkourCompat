# F-006: Passenger sprint state can change a later on-foot jump impulse

1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/world/entity/Entity.java`, isSprinting 1984-1990.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, stopRiding 2715-2720; aiStep 2566-2571.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2566-2571.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 772-785.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 719-729.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/world/entity/player/Player.java`, removeVehicle 1045-1048.

Difference: Choose the same input guards on both sides: the player is a passenger of a grounded vehicle, the player itself is neither on ground nor underwater, forward impulse is at least `0.8`, food is above 6, sprint is pressed, and the player is not already sprinting, shifting, using an item or blinded. 1.19.3's added passenger/vehicle-ground clause starts the player sprint flag; 1.19.2's `onGround || isUnderWater()` predicate does not. After dismount, keep sufficient forward input and food, release sprint, and avoid collision/water stop conditions. 1.19.3 retains the previously set sprint flag through `stopRiding`; 1.19.2 has not set it and only arms its sprint trigger. On a subsequent grounded jump, the shared player jump code therefore adds the sprint-only horizontal `0.2F` velocity vector in 1.19.3 and not 1.19.2. This finding uses food above the threshold on both sides; the separate low-food passenger shortcut is not claimed as an in-scope movement effect.

Movement meaning: `LocalPlayer.aiStep` -> passenger/vehicle-ground sprint guard -> `LocalPlayer.setSprinting` -> `Entity` shared sprint flag -> dismount without a sprint reset -> on-foot sprint-retention guard -> `LivingEntity.aiStep` grounded jump -> `LivingEntity.jumpFromGround` player velocity write. 1.19.3 also sends the changed state through the existing sprint command path, and the paired vanilla server command handler accepts it without a passenger restriction. Vehicle position/velocity simulation is not part of this finding. Source-proven: under the listed state and input conditions, 1.19.3 can carry a sprint flag from a grounded passenger state into a later on-foot jump while 1.19.2 remains non-sprinting; the shared jump method then takes its sprint branch only in 1.19.3. Predicted: the resulting player velocity and later movement can diverge. No actual mounting sequence, server replication timing or trajectory is claimed.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_19/VehicleSprint.java"
    ],
    "hooks": [
      "VehicleSprintHook"
    ],
    "limits": [
      "Historical mount sprint eligibility exists; the newly added vehicle-ground start path and retained flag/jump sequence are distinct and not fully covered. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "772-785",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "isSprinting",
      "lines": "1984-1990",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "removeVehicle",
      "lines": "1045-1048",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "stopRiding",
      "lines": "2715-2720",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "719-729",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "aiStep",
      "lines": "2566-2571",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "aiStep",
      "lines": "2566-2571",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
