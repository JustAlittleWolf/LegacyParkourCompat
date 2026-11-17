# Direct player response consumer inventory — bounded correction addendum

This addendum corrects the omitted `handlePlayerAbilities(PlayerAbilitiesS2CPacket)` receiver in `direct-player-response-consumers.md`. It is a narrow inventory correction; it does not repeat or change the report’s other source comparisons.

## Bound inputs

- Base report: `build/source-early-patches-20261117/1.11--1.11.1/responses/direct-player-response-consumers.md`, SHA-256 `4f91c8c7d08e9ee7472db97f14291c9ef92d665bce26f2ee2338d0419297d6a1`.
- Initial independent audit: `build/source-early-patches-20261117/1.11--1.11.1/responses/direct-player-response-consumers-audit/verdict.md`, SHA-256 `2cca0607541d1b1ed446bf492f385cbeb18e9295d91cf5a4b7b2e662ad5d99f4` (REQUEST CHANGES solely for this omitted receiver).
- The base report’s exact 1.11 and 1.11.1 source markers/manifests and handler file hashes remain the bound identities. `ClientPlayNetworkHandler.java` source SHA-256: 1.11 `cef5d643eca0b32119571eff5201705cc8c99125f8266f763172202d89e3a40b`; 1.11.1 `65d362c00de1adfd55fae5f858b91bf9b227525b987c09a2d223279376a66175`.

## Omitted direct consumer

`handlePlayerAbilities(PlayerAbilitiesS2CPacket)` is at line 1348 in 1.11 and line 1362 in 1.11.1. Both endpoint bodies are identical. After `PacketUtils.ensureOnSameThread(packet, this, this.minecraft)`, the handler binds `PlayerEntity playerEntity = this.minecraft.player`, then in order assigns `abilities.flying`, `creativeMode`, `invulnerable`, and `canFly` from the packet and calls `setFlySpeed(packet.getFlySpeed())` followed by `setWalkSpeed(packet.getWalkSpeed())`. It has no additional player-identity or state gate. Flying/can-fly and the speed settings are direct movement-control inputs; creative/invulnerability are included because they are written in the same forced ability-state response but have no movement effect established here.

This handler is unchanged across the exact pair. Its addition to the receiver inventory does not alter the bounded conclusion that `ClientPlayNetworkHandler.java` contains no changed direct local-player response consumer across 1.11→1.11.1. Producer conditions and the remaining pair coverage dependencies stay open as stated in the base report.