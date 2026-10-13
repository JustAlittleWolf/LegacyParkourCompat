# F-007: Underwater boat contact selects a different player travel branch

1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/world/entity/Entity.java`, updateInWaterStateAndDoWaterCurrentPushing 1069-1085.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/Entity.java`, updateInWaterStateAndDoWaterCurrentPushing 1074-1093.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2497-2620; travel 2042-2178.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2508-2631; travel 2052-2185.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, rideTick 953-961; isEffectiveAi 581-583.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, rideTick 874-882; isEffectiveAi 493-495.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/world/entity/player/Player.java`, travel 1464-1494; removeVehicle 1045-1048.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/player/Player.java`, travel 1448-1478; removeVehicle 1034-1037.

Difference: While the local player is a Boat passenger, let the Boat be underwater and the player's own bounds intersect water. With flying and fall-flying disabled and the player unable to stand on that fluid, the water-travel guard is reachable. In 1.19.2, the boat-passenger special case forces `wasTouchingWater` false. In 1.19.3, the guard falls through to the normal fluid-contact update and sets the flag from the player's water overlap. Thus `isInWater()` selects the water `LivingEntity.travel` branch in 1.19.3 and the ordinary land/air branch in 1.19.2 during the mounted local-player tick. The mounted tick later repositions the player to the Boat, but the different travel result remains in the player's delta-movement field. On dismount, the paired `stopRiding`/`removeVehicle` methods do not clear that field. 1.19.2 subsequent on-foot player movement tick therefore consumes a delta produced by different travel branches. This is a direct player-state path; no Boat movement simulation is included.

Movement meaning: `ClientLevel` passenger tick -> `LocalPlayer.rideTick` -> `Entity.rideTick` -> player `baseTick` water-contact update -> `LivingEntity.aiStep` / `Player.travel` -> `isInWater` branch selection and player delta write -> vehicle reposition -> dismount without delta reset -> later on-foot player travel. The submerged Boat condition and fluid overlap are external world state. Server correction timing can alter the local prediction and is not resolved here. Source-proven: with the listed mounted water-contact preconditions, 1.19.2 and 1.19.3 select different player travel branches and write the corresponding player delta; after dismount that delta is not reset by the paired dismount methods and is available to the next player movement tick. Predicted: the local player's later movement can diverge. No actual mounting sequence, server correction outcome, or measured trajectory is claimed.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_18_2/BoatPassengerFluidPush.java",
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_19/BoatPassengerFluidPush.java"
    ],
    "hooks": [
      "BoatPassengerFluidPushHook"
    ],
    "limits": [
      "Boat current gate exists, but hook presence alone does not reproduce older wasTouchingWater and travel-branch state; exact 1.19.2 cutover needs reconciliation. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "updateInWaterStateAndDoWaterCurrentPushing",
      "lines": "1069-1085",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "updateInWaterStateAndDoWaterCurrentPushing",
      "lines": "1074-1093",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "rideTick",
      "lines": "953-961",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "rideTick",
      "lines": "874-882",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "aiStep",
      "lines": "2497-2620",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "aiStep",
      "lines": "2508-2631",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "travel",
      "lines": "1464-1494",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "travel",
      "lines": "1448-1478",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "isEffectiveAi",
      "lines": "581-583",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "isEffectiveAi",
      "lines": "493-495",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "travel",
      "lines": "2042-2178",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "travel",
      "lines": "2052-2185",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "removeVehicle",
      "lines": "1045-1048",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "removeVehicle",
      "lines": "1034-1037",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
