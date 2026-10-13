# F-009: Historical mounts stop allowing the passenger to start sprinting

1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, canSprint 3163-3165.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 687-716.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 685-708; aiStep 645-800.

Difference: For a local player riding a shared historical mount, holding sprint with sufficient forward input and food (or mayfly ability), while not using an item or blinded and outside water, 1.18.2 can call `setSprinting(true)`. 1.19.4's `canStartSprinting()` reaches `vehicleCanSprint()` because the player is a passenger, then rejects the start because that historical vehicle inherits `Entity.canSprint() == false`. 1.18.2's manual sprint-key branch has no mount eligibility check. This finding is limited to this passenger sprint-start path; 1.19.4's modern Camel-specific opt-in is outside the historical map feature set.

Movement meaning: Local-player tick/input -> `LocalPlayer.aiStep()` sprint-key branch -> `setSprinting(true)` in 1.18.2, or 1.19.4 `canStartSprinting()` -> vehicle capability/control checks -> sprint flag. 1.18.2's food condition is `foodLevel > 6 || mayfly`; 1.19.4's helper additionally treats a passenger as having enough food, but the common historical vehicle fails the earlier `canSprint()` check. The setter updates the player's sprint state and the shared living-entity sprint movement-speed modifier path documented in slice 3.11. Source proves that under the stated conditions the passenger sprint flag can become true in 1.18.2 and is refused in 1.19.4 for the shared historical mount set. The flag is a player movement input and the setter applies the sprint movement-speed modifier; this comparison does not establish a changed mounted vehicle trajectory or measure movement after dismount. No position/velocity trace was run.


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
      "VehicleSprintHook restores historical mount food/mayfly eligibility. Separate initial vehicle-ground/start state and exact 1.19.3 range are not proved by this hook. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.18.2",
      "mapping": "mojmap",
      "path": "1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "687-716",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: a2aa6ee1030bfef79e9b2e08e79de1637fdd7ecb5bf8891cf2e9a4b186042543 *1.18.2/client_mappings.txt."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "685-708",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "canSprint",
      "lines": "3163-3165",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "645-800",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
