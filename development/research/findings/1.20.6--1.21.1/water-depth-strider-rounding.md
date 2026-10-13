# water-depth-strider-rounding: Depth Strider III water speed factor changes by one float ulp

1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel 2104-2129.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel 2091-2112; createLivingAttributes 321-322.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java`, getDepthStrider 184-186.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/item/enchantment/Enchantments.java`, class declaration / field initialization 74-79.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/item/enchantment/Enchantments.java`, bootstrap 344-368.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/item/enchantment/LevelBasedValue.java`, perLevel 30-32.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/ai/attributes/Attributes.java`, class declaration / field initialization 101-103.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/item/ItemStack.java`, forEachModifier 915-923.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java`, forEachModifier 311-316.

Difference: Under the same reachable setup—player in water, affected by fluids, unable to stand on the fluid, on ground, sprinting, wearing Depth Strider III, and without Dolphin's Grace—1.20.6 starts `$$5` at `0.9F` and uses integer enchantment factor `3.0F`, then performs multiply and divide by `3.0F`. 1.21.1's registered level-3 attribute contribution evaluates to the bounded water-efficiency value `1.0F`, and its formula multiplies by that value without the final division. These algebraically equivalent expressions do not produce the same binary32 result: Java float evaluation gives 1.20.6 `$$5 = 0.5460000038146973` and 1.21.1 `$$5 = 0.546000063419342` after this adjustment. The change is in the horizontal water-speed multiplier; the later movement and drag order is otherwise unchanged in this branch.

Movement meaning: The local player enters `LivingEntity#travel` through `LocalPlayer#aiStep`, `Player#travel`, and `LivingEntity#aiStep`; the water branch is guarded by `isInWater()`, `isAffectedByFluids()`, and `!canStandOnFluid($$3)`. 1.20.6 obtains the equipped enchantment level through `EnchantmentHelper`; 1.21.1 declares and applies the attribute modifier for eligible foot armor. 1.21.1's attribute is included in every LivingEntity attribute supplier and syncable. The vanilla Depth Strider maximum is 3 on both sides. The source proves a one-float-ulp difference in the multiplier under the stated preconditions. It predicts a corresponding small difference in horizontal velocity after the water drag multiplication; no observed position trace or gameplay impact is claimed. This endpoint pair does not establish the first release containing the change. Other water-branch dependencies and all remaining pair coverage continue in the run ledger.


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
      "version": "1.20.6",
      "mapping": "mojmap",
      "path": "1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "travel",
      "lines": "2104-2129",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745 *1.20.6/client_mappings.txt. Canonical source-manifest SHA-256 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.6",
      "mapping": "mojmap",
      "path": "1.20.6/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java",
      "member": "getDepthStrider",
      "lines": "184-186",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e0b4c410e0aa9499d67b38f57b34467628e882be960dce6958d4bc8ef717a6b6; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745 *1.20.6/client_mappings.txt. Canonical source-manifest SHA-256 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "travel",
      "lines": "2091-2112",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.6",
      "mapping": "mojmap",
      "path": "1.20.6/mojmap/net/minecraft/world/item/enchantment/Enchantments.java",
      "member": "class declaration / field initialization",
      "lines": "74-79",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 26eeafdf667fa57b711fab3112aa9636f20a9a38cadfbff42740e66a14c38381; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745 *1.20.6/client_mappings.txt. Canonical source-manifest SHA-256 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/item/enchantment/Enchantments.java",
      "member": "bootstrap",
      "lines": "344-368",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 7848a2cd677aa0700434f85de37d8a0c47597741d2d1f26c8dc270f73d65b266; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/item/enchantment/LevelBasedValue.java",
      "member": "perLevel",
      "lines": "30-32",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f9a0bed1d7693606f8657fadd4a59d95eea9955fcc036687e7a9dd08a8b360e3; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/entity/ai/attributes/Attributes.java",
      "member": "class declaration / field initialization",
      "lines": "101-103",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a9a19f556bb77fc218b5b2f4831eb6b285f4398c24fa2a3519009ca8dad2db7c; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "createLivingAttributes",
      "lines": "321-322",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/item/ItemStack.java",
      "member": "forEachModifier",
      "lines": "915-923",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 15094cc5a115bbbc81c550fafcfba51451fcc7afed2ad374ca1029ba1d1db687; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java",
      "member": "forEachModifier",
      "lines": "311-316",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 9e2f9acd5bc6a8f3b5292d720db81464954d41c9afb635f1a7c6cb31d5a2bf84; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
