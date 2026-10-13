# soul-speed-client-friction-sync-candidate: Soul Speed block-speed selection moves to a server-activated attribute

1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/Entity.java`, move 706-707; move 705-706.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, onSoulSpeedBlock 489-496.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, getBlockSpeedFactor 485-488; baseTick 433-439.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/player/Player.java`, onSoulSpeedBlock 975-977.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/player/Player.java`, getBlockSpeedFactor 1973-1975.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java`, getEnchantmentLevel 150-166.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/ai/attributes/Attributes.java`, class declaration / field initialization 71-73.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/item/enchantment/Enchantments.java`, bootstrap 506-522.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/item/enchantment/effects/EnchantmentAttributeEffect.java`, onChangedBlock 39-57.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, handleUpdateAttributes 2062-2076.

Difference: For a non-flying player with Soul Speed boots on a tagged block, 1.20.6 evaluates block membership and enchantment level locally and returns `1.0F` directly. 1.21.1 evaluates the current `MOVEMENT_EFFICIENCY` attribute. The base attribute is zero; Soul Speed raises it to `1.0F` through a location-changed effect whose movement callback requires `ServerLevel`. The client therefore consumes a server-produced attribute value, with the normal block factor used while that value is absent or stale.

Movement meaning: Local player tick -> `LivingEntity#travel` -> `Entity#move` -> `Player#getBlockSpeedFactor` -> `LivingEntity#getBlockSpeedFactor` -> horizontal delta-movement multiplier. On a transition onto Soul Sand with Soul Speed boots, 1.20.6's current-block query returns `1.0F`; 1.21.1's first local movement uses the currently available movement-efficiency attribute. 1.21.1's server block-position callback activates the location effect, and tracked dirty attributes are sent to the client. The tag and Soul Sand `0.4F` base speed factor establish a concrete block where the multipliers differ if the attribute has not yet reached the local client. Flight and fall-flying are excluded by the unchanged Player override. Source proves the client-side consumer changed from a direct local tag/enchantment check to a syncable attribute and that the effect producer is server-only. If a local move uses the default/stale value after entering Soul Sand, 1.21.1 multiplies horizontal velocity by the block's `0.4F` speed factor while 1.20.6 uses `1.0F`. No position trace or gameplay result is claimed.


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
      "member": "onSoulSpeedBlock",
      "lines": "489-496",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745 *1.20.6/client_mappings.txt. Canonical source-manifest SHA-256 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.6",
      "mapping": "mojmap",
      "path": "1.20.6/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java",
      "member": "getEnchantmentLevel",
      "lines": "150-166",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e0b4c410e0aa9499d67b38f57b34467628e882be960dce6958d4bc8ef717a6b6; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745 *1.20.6/client_mappings.txt. Canonical source-manifest SHA-256 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.6",
      "mapping": "mojmap",
      "path": "1.20.6/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "onSoulSpeedBlock",
      "lines": "975-977",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745 *1.20.6/client_mappings.txt. Canonical source-manifest SHA-256 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "getBlockSpeedFactor",
      "lines": "485-488",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "getBlockSpeedFactor",
      "lines": "1973-1975",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/entity/ai/attributes/Attributes.java",
      "member": "class declaration / field initialization",
      "lines": "71-73",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a9a19f556bb77fc218b5b2f4831eb6b285f4398c24fa2a3519009ca8dad2db7c; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/item/enchantment/Enchantments.java",
      "member": "bootstrap",
      "lines": "506-522",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 7848a2cd677aa0700434f85de37d8a0c47597741d2d1f26c8dc270f73d65b266; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/item/enchantment/effects/EnchantmentAttributeEffect.java",
      "member": "onChangedBlock",
      "lines": "39-57",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c1e2edd5c870e1ecba364cbee07ae2f12720e47681dd1dbf16ac081e392f4270; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "baseTick",
      "lines": "433-439",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java",
      "member": "handleUpdateAttributes",
      "lines": "2062-2076",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 822e00d89e8def4cf7a8c22fdfefba3b9dc2135a7630e4ebe561a9d547d7f79d; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.6",
      "mapping": "mojmap",
      "path": "1.20.6/mojmap/net/minecraft/world/level/block/Blocks.java",
      "member": "class declaration / field initialization",
      "lines": "2282-2298",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 684579bbcbe48b1f0984090a4ca044c0b2cee6d08c2dc1e449e2259389c5b129; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745 *1.20.6/client_mappings.txt. Canonical source-manifest SHA-256 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/level/block/Blocks.java",
      "member": "class declaration / field initialization",
      "lines": "2282-2298",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 538ac50c164484ca4c2367a2a6c07cf2faedcac0c4d5117d8817d36c2d1bde6d; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": "706-707",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": "705-706",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
