# Frozen source audit roster — portal/passenger position, 1.14.2 → 1.14.4

Audit mode: blind source audit; no product implementation, wiki inventory, or author report/roster consulted before this roster freeze.
Scope: only the reported portal/passenger position operation, its vehicle/passenger relationship, LivingEntity server-side dismount placement, connection movement/correction path, and the client player XYZ writer. No other patches or unrelated portal behavior.

## Exact source publication identity

- Older: Minecraft 1.14.2, Yarn 1.14.2+build.7, canonical source root `decompiled_minecraft/1.14.2/yarn`.
  - Provenance manifest SHA-256: `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a`.
  - Manifest pins Mojang client artifact `ca6c5a139045967229975c0c0b7f93e78b4314c2/client.jar` to `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25`.
  - Yarn artifact URL is `https://maven.fabricmc.net/net/fabricmc/yarn/1.14.2+build.7/yarn-1.14.2+build.7-v2.jar`, SHA-256 `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b`; cached Yarn metadata SHA-256 `a65478802722f65b4dfec15e8a732872f2836e1e9b7ae95d7086c601b07f1d81`.
  - Exact version metadata input SHA-256: `237aa8d499b71bb17122cbee40b8748efcd5ac9d8362c68c89e99ab0703e66b8`.

- Newer: Minecraft 1.14.4, Mojmap (published Mojang names), canonical source root `decompiled_minecraft/1.14.4/mojmap`.
  - Provenance SHA-256: `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97`.
  - Ready marker pins exact version ID 1.14.4 and client SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`. It binds source manifest SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` and artifact manifest SHA-256 `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970`.
  - Pinned published Mojang client mappings file `1.14.4/client_mappings.txt` SHA-256 `2dd53a5e70ba493cf6e33c0fc52bbdf4c57f9429c7a13842565aa825fd44d910`; remapped client `1.14.4/client-mojmap.jar` SHA-256 `850d17d7e0f78e064d522474396753b3545e822feaa33c1ff9f8ff0ba2acd9aa`.
  - Verification sidecar records 3,250/3,250 source files identical, zero missing files, and identical raw input artifacts.

## Frozen operation inventory

| Slice | 1.14.2 Yarn source | 1.14.4 Mojmap source | Hash binding |
|---|---|---|---|
| Portal activation/transfer gate and vehicle detach on dimension transfer | `net/minecraft/entity/Entity.java`, portal `tickPortal`/`setInPortal`; `changeDimension`/`detach` | `net/minecraft/world/entity/Entity.java`, `handleInsidePortal`/`handleNetherPortal`; `changeDimension`/`unRide` | Old `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc`; new `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |
| LivingEntity dismount placement and gate | `net/minecraft/entity/LivingEntity.java`, `tick` around fluid handling | `net/minecraft/world/entity/LivingEntity.java`, `tick` around fluid handling | Old `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78`; new `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` |
| Server passenger/player and vehicle move handling; absolute correction construction | `net/minecraft/server/network/ServerPlayNetworkHandler.java` | `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | Old `554278bbc6bed6b776312d35d1e4c964288cb349f4c06b2e9ff8f01fabc8c20a`; new `912f528c03da34fa703646b6383c2b49ac3493efd7ba78cb8ff27aa7f693b67d` |
| Client player's canonical position writer and passenger sentinel | `net/minecraft/client/network/ClientPlayerEntity.java`, `sendMovementPackets` | `net/minecraft/client/player/LocalPlayer.java`, `sendPosition` | Old `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947`; new `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |
| Server→client vehicle correction packet | `net/minecraft/client/network/packet/VehicleMoveS2CPacket.java` | `net/minecraft/network/protocol/game/ClientboundMoveVehiclePacket.java` | Old `1cdf97c6738c2329b98fc0c31ed90ee05255f7862981b6b8bdac1f2b73c684ef`; new `ef2b3cae24d1683a3cf063b5252fb3ed7a6fd533ee2bec59df2bb3b3165165a9` |
| Server→client player correction packet and client→server player move packet fields | `net/minecraft/client/network/packet/PlayerPositionLookS2CPacket.java`; `net/minecraft/server/network/packet/PlayerMoveC2SPacket.java` | `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java`; `net/minecraft/network/protocol/game/ServerboundMovePlayerPacket.java` | Old `9303cb6dfa7f4db4b775d6e57304ebaf58790dcea915cdb89564edd05a9308e2`; `44d64d9032b891b11271d02073bb416ab3c320e4c0404b5d7e653cc1fb0804e8`; new `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d`; `d507e0f95601bd5ec9c180b58e456891dbcaa3b186ec8099e9654c5797c220d1` |

## Audit questions frozen

1. Are vehicle/passenger predicates and dimension-transfer detach behavior equivalent at these endpoints?
2. Is the LivingEntity dismount correction server-only, where is it nested, and does it test a portal block?
3. What movement packet path applies to a passenger versus a player on foot?
4. Do server corrections encode absolute position fields, and does the client writer submit current canonical XYZ or a passenger-specific sentinel?
5. Is there a source-confirmed movement difference in this bounded operation, or only mapping/API refactoring?

Roster frozen before any author-scratch access. No other source slices will be used to infer patch introduction or unrelated portal behavior.
