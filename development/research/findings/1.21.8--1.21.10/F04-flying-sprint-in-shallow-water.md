# F04: Flying bypasses the shallow-water sprint restriction

1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/world/entity/Entity.java`, isUnderWater 1394-1396.
1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/world/entity/LivingEntity.java`, travelInFluid 2301-2330.
1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/client/player/LocalPlayer.java`, canStartSprinting 1065-1075; aiStep 722-734.
1.21.10, mojmap: `1.21.10/mojmap/net/minecraft/world/entity/player/Player.java`, isMobilityRestricted 1857-1859.

Difference: For an otherwise sprint-eligible local player in shallow water, 1.21.8's `canStartSprinting()` rejects the state because the player is in water but not underwater. 1.21.10 passes the flying ability to `isSprintingPossible`; when flying is true, its final shallow-water clause is true. The other common requirements, including forward input, no active item use, no blindness, and any vehicle gate, still apply. The two paths can therefore differ in whether the local player enters sprint state under this precondition. The inherited water-travel path then reads `isSprinting()` to select its water drag coefficient. It uses `0.9F` for sprinting and the water-slowdown value otherwise, before applying later movement-efficiency and Dolphin's Grace adjustments.

Movement meaning: Local-player forward/sprint input -> `LocalPlayer.aiStep` -> `canStartSprinting` -> 1.21.8 shallow-water rejection or 1.21.10 flying exception -> local sprint flag -> `LivingEntity.travel` water branch -> sprint-dependent delta-movement drag. The source proof is client-player reachable. It depends on vanilla water/eye-water and ability state; no external resource is needed. Food producer logic remains outside scope; only the gate's direct read is relevant. Source-proven: under the stated preconditions and with other sprint gates satisfied, 1.21.8 rejects the shallow-water sprint transition while 1.21.10 can set sprint state; the water travel branch consumes that state to choose a different coefficient. Predicted only: player velocity and position can diverge when that branch changes.


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
      "version": "1.21.8",
      "mapping": "mojmap",
      "path": "1.21.8/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "canStartSprinting",
      "lines": "1065-1075",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca *1.21.8/client_mappings.txt. Canonical source-manifest SHA-256 8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "isMobilityRestricted",
      "lines": "1857-1859",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.8",
      "mapping": "mojmap",
      "path": "1.21.8/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "isUnderWater",
      "lines": "1394-1396",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca *1.21.8/client_mappings.txt. Canonical source-manifest SHA-256 8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.8",
      "mapping": "mojmap",
      "path": "1.21.8/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "722-734",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca *1.21.8/client_mappings.txt. Canonical source-manifest SHA-256 8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.8",
      "mapping": "mojmap",
      "path": "1.21.8/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "travelInFluid",
      "lines": "2301-2330",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca *1.21.8/client_mappings.txt. Canonical source-manifest SHA-256 8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
