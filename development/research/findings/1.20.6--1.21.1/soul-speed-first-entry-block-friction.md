# soul-speed-first-entry-block-friction: First local entry step uses the block factor before Soul Speed attribute sync

1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/Entity.java`, move 705-706.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/Entity.java`, move 706-707.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, onSoulSpeedBlock 489-496.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, getBlockSpeedFactor 485-488; baseTick 433-439; checkFallDamage 327-355.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/client/player/LocalPlayer.java`, tick 191-205.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/player/Player.java`, onSoulSpeedBlock 975-977; getBlockSpeedFactor 1978-1980.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/player/Player.java`, getBlockSpeedFactor 1973-1975.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/ai/attributes/Attributes.java`, class declaration / field initialization 71-73.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/item/enchantment/Enchantments.java`, bootstrap 506-522.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/item/enchantment/effects/EnchantmentAttributeEffect.java`, onChangedBlock 39-57.

Difference: For a non-flying, non-fall-flying local player with Soul Speed boots, start from a non-Soul-Speed block with the client `MOVEMENT_EFFICIENCY` at its normal `0.0` baseline, then make an ordinary walking step onto Soul Sand. In 1.20.6, the local move reaches `Entity#move()` and the current-block query directly returns `1.0F`. In 1.21.1, the local `super.tick()` performs the move and reads the still-current `0.0` attribute before `LocalPlayer#tick()` sends the new position to the server. With Soul Sand's base factor `0.4F`, 1.21.1's interpolation returns `0.4F` for that entry move. The server can only observe the new position after that client movement packet and runs the location-changed producer on its server-level entity; the resulting dirty attribute is synchronized afterward through the tracked-entity send path, including to the owning `ServerPlayer` connection.

Movement meaning: Client local tick -> `LivingEntity#aiStep` -> `Player#travel` -> `LivingEntity#travel` -> `Entity#move` -> block-speed factor -> horizontal delta-movement multiplier. The ordinary non-passenger `LocalPlayer#tick` sends position only after `super.tick()` completes. On the server, the changed block position activates the Soul Speed location effect, and the dirty syncable attribute is later sent back to the same player and applied by `ClientPacketListener`. The affected local entry move therefore has a concrete client-side precondition where 1.20.6 and 1.21.1 read different values; later movement can converge after the server update arrives. The tag, player flight exclusions, Soul Sand property, modifier registration/application, and packet consumer are all traced above. The source proves a one-entry-move difference in the horizontal multiplier under the stated reachable precondition: 1.20.6 uses `1.0F`; 1.21.1 uses `0.4F` while its local attribute remains at baseline. This predicts a lower horizontal delta movement after the 1.21.1 move. No position trace or gameplay result is claimed. Packet latency can affect later local ticks; it does not precede the local movement that first reports the newly entered block. The endpoint pair does not establish the first release containing the change.


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
      "path": "1.20.6/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "onSoulSpeedBlock",
      "lines": "975-977",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745 *1.20.6/client_mappings.txt. Canonical source-manifest SHA-256 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.6",
      "mapping": "mojmap",
      "path": "1.20.6/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "getBlockSpeedFactor",
      "lines": "1978-1980",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745 *1.20.6/client_mappings.txt. Canonical source-manifest SHA-256 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.6",
      "mapping": "mojmap",
      "path": "1.20.6/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": "705-706",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745 *1.20.6/client_mappings.txt. Canonical source-manifest SHA-256 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 matches archived publication; cited file hash is present in that manifest."
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
      "path": "1.21.1/mojmap/net/minecraft/world/entity/ai/attributes/Attributes.java",
      "member": "class declaration / field initialization",
      "lines": "71-73",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a9a19f556bb77fc218b5b2f4831eb6b285f4398c24fa2a3519009ca8dad2db7c; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
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
      "path": "1.21.1/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": "706-707",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
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
      "path": "1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "checkFallDamage",
      "lines": "327-355",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "tick",
      "lines": "191-205",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/server/level/ChunkMap.java",
      "member": "tick",
      "lines": "1069-1093",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 079b7c5e3d5bcdfff8b18a69513c93aaca56dfd739bde642c2ece79d127c5088; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/server/level/ServerEntity.java",
      "member": "sendChanges",
      "lines": "86-221",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 0994216505c8df7cc67a7a068ae8e71d1f023dbef5dc1dc31360e3e5bdc2ce14; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/server/level/ServerEntity.java",
      "member": "sendDirtyEntityData",
      "lines": "314-335",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 0994216505c8df7cc67a7a068ae8e71d1f023dbef5dc1dc31360e3e5bdc2ce14; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
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
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
