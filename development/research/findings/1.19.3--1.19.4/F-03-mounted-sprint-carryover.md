# F-03: mounted sprint state can carry into player movement after dismount

1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, canSprint 3163-3165; removeVehicle 1798-1804.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/Entity.java`, removeVehicle 1784-1790.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, setSprinting 1964-1974; getFrictionInfluencedSpeed 2246-2248.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, getFrictionInfluencedSpeed 2197-2199.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 693-719; removeVehicle 172-176.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 685-701; removeVehicle 172-176.

Difference: 1.19.3 permits a local player to enter sprinting while a passenger, subject to its input, vehicle-ground, item-use, blindness and other existing guards; its food helper explicitly allows passengers. 1.19.4 routes both sprint-start paths through `canStartSprinting`, which denies passengers when the vehicle does not report `canSprint()` and local control. Existing vanilla rideable entities inherit the 1.19.4 `Entity` default of false. The added Camel override is a new-version vehicle and is outside this historical comparison. Both versions retain sprint state through `Entity#removeVehicle` and the LocalPlayer override. If 1.19.3 entered sprint while riding an existing vehicle and a dismount occurs before the next local tick, the sprint flag and speed modifier remain. With food above the normal sprint threshold, continued forward input, and a grounded post-dismount player tick whose other stop conditions are false, 1.19.3's grounded movement consumes the sprint-modified movement speed. 1.19.4 cannot enter that mounted sprint state under the same vehicle precondition, so that carry-over path is absent.

Movement meaning: LocalPlayer passenger/input gate -> `setSprinting(true)` -> `LivingEntity#setSprinting` sprint flag and movement-speed modifier -> entity/player dismount clears vehicle link without clearing sprint -> next LocalPlayer tick retains sprint while forward input and food satisfy the existing stop check -> grounded `LivingEntity` movement-speed consumer. Vehicle physics are excluded; only the player's sprint state and post-dismount movement are in scope. Source proves the eligibility and persistence differences and a reachable post-dismount grounded movement path under the stated conditions. The direct food value is a movement predicate input; food-system production remains excluded.


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
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "693-719",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "685-701",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "canSprint",
      "lines": "3163-3165",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "removeVehicle",
      "lines": "1784-1790",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "removeVehicle",
      "lines": "1798-1804",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "removeVehicle",
      "lines": "172-176",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "removeVehicle",
      "lines": "172-176",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "setSprinting",
      "lines": "1964-1974",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "getFrictionInfluencedSpeed",
      "lines": "2246-2248",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "getFrictionInfluencedSpeed",
      "lines": "2197-2199",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
