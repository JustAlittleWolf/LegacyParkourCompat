# Blind source audit: 1.14.2 to 1.14.3

## Scope and verdict

This is a bounded, independent source audit of the four assigned related operations. It is not full-pair coverage or acceptance. No relevant player movement delta was found in these operations. The paired source differs in mappings and class/type naming; where the bodies vary, the matching enum values and order preserve the same movement behavior.

## Exact source identity

- Host/execution: Windows 11, PowerShell; canonical shared source root `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\`.
- 1.14.2: published client artifact `https://piston-data.mojang.com/v1/objects/ca6c5a139045967229975c0c0b7f93e78b4314c2/client.jar`, SHA-256 `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25`; Yarn `1.14.2+build.7`, artifact SHA-256 `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b`; Intermediary `1.14.2`, artifact SHA-256 `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e`.
- 1.14.3: published client artifact `https://piston-data.mojang.com/v1/objects/af100b34ec7ef2b8b9cf7775b544d21d690dddec/client.jar`, SHA-256 `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07`; Yarn `1.14.3+build.13`, artifact SHA-256 `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`; Intermediary `1.14.3`, artifact SHA-256 `f2e9990c00fa87ddc7158a95afb735a947ae26d1997b52916226e11c612542a9`.
- Both mapping roots contain `.decompile-provenance.json`. Its pinned input identities match the listed client, Yarn and Intermediary artifacts. The manifests list the same `lpc-decompile-v1` pipeline and Vineflower 1.12.0. Each roster source SHA-256 below was recomputed from disk and matches its manifest entry. This verifies the local source identities and intact files; it does not make endpoint comparison evidence for intermediate releases.

## Frozen source roster

Paths are relative to `decompiled_minecraft/<version>/yarn/`. Hashes are SHA-256 and match the corresponding local provenance manifest.

| Source | 1.14.2 | 1.14.3 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| `net/minecraft/entity/LivingEntity.java` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` |
| `net/minecraft/entity/player/PlayerEntity.java` | `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398` | `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963` |
| `net/minecraft/entity/passive/HorseBaseEntity.java` | `317ec0256a38c0f35b3dd20e8a03b17a7819bb903ad41c77f95cde6e78e47387` | `df26ff8249c826512ac49846c7cbba342fb4ca3c2ea6dc4927b75a62aa5bfd61` |
| `net/minecraft/entity/vehicle/BoatEntity.java` | `9b639ab039d20b4f224be4f7b15dbee77e4e7bfba11c64e92060fe97c06e7725` | `b2156dce2d641188d793ef83327fa1abd385ceb8e29c6050ef34f522f0938101` |
| `net/minecraft/client/network/ClientPlayNetworkHandler.java` | `5055088f2e944c50be032cce0a921054eba2c13a022f60306e5e9411c400697b` | `5dab323ebb5147be91219928928abaadaa320a3281fad8f6957d3d96d5eeb4a7` |
| `net/minecraft/client/network/packet/PlayerPositionLookS2CPacket.java` | `9303cb6dfa7f4db4b775d6e57304ebaf58790dcea915cdb89564edd05a9308e2` | `f2650155fc20362b51de1e8eaed6fc28a60796846d366a7e7dbac9bcd5ab0fe3` |
| `net/minecraft/network/NetworkThreadUtils.java` | `bb670e08a0c105a08c121491e736a6628696db5f76b1c74ce95ca13b8f1108da` | `bb670e08a0c105a08c121491e736a6628696db5f76b1c74ce95ca13b8f1108da` |
| `net/minecraft/server/network/ServerPlayNetworkHandler.java` | `554278bbc6bed6b776312d35d1e4c964288cb349f4c06b2e9ff8f01fabc8c20a` | `0467b26f218892f6ed2851688cc20ecde313fa76f6d84419af775c849eb568c3` |
| `net/minecraft/util/AbsoluteHand.java` / `net/minecraft/util/Arm.java` | `AbsoluteHand.java`: `adab76f8a18da074616b804fe8dfc021c094071581f1e5561896e3460202add7` | `Arm.java`: `7894f353ab99f6d245d894f7d6b13c31ade280cccb8c1772aa80b6368179701b` |

## Operation verdicts

### Generic passenger stand-up positioning on the logical server

`LivingEntity.stopRiding` calls `method_6038(vehicle)` only after the vehicle link changes and only when `!world.isClient` (1.14.2 lines 2312–2316; 1.14.3 lines 2322–2326). The ordinary-vehicle branch in `method_6038` searches candidate offsets from movement direction, checks collision and block/fluid support, and issues `requestTeleport` (1.14.2 lines 1656–1708; 1.14.3 lines 1667–1719). The gate, candidate order, `Math.floor`, dimensions, support checks, fallback heights and request order match.

The dismount-side expression changes from `getMainHand() == AbsoluteHand.field_6183` to `getMainArm() == Arm.RIGHT` (1.14.2 line 1712; 1.14.3 line 1723). The source enum definitions both encode left as zero and right as one; the player accessors map those values respectively to `field_6182/field_6183` and `LEFT/RIGHT` (`PlayerEntity` lines 1886–1891 in both; `AbsoluteHand.java`, `Arm.java`). The right-side comparison and resulting sign are equivalent. Verdict: no movement delta in the assigned generic server dismount operation.

### Virtual teleport correction and enclosing packet gate

`ServerPlayNetworkHandler.onPlayerMove` first calls `NetworkThreadUtils.forceMainThread`, then runs `validatePlayerMove`; while `requestedTeleportPos != null`, ordinary movement application is withheld and the correction is resent only when more than 20 ticks have elapsed (1.14.2 lines 678–699; 1.14.3 lines 679–700). `teleportRequest` saves the requested destination, advances/wraps the teleport id, stamps the request tick, positions the server player, then sends `PlayerPositionLookS2CPacket` with the selected relative flags (1.14.2 lines 808–821; 1.14.3 lines 812–825). These gates and operation order match; changed flag names are mapping names.

The entity-level `requestTeleport` remains gated by `world instanceof ServerWorld` in both releases (Entity 1.14.2 lines 2181–2187; 1.14.3 lines 2222–2228). The outer packet gate, timeout, correction order and server-only write therefore show no assigned movement delta. This trace covers the handler and thread gate; it does not claim guarantees about transport arrival ordering beyond the inspected packet listener path.

### Horse and boat stand-up overrides as a shared contract

`LivingEntity.method_6038` routes boat and `HorseBaseEntity` dismounts to the same alternate branch in both releases. It uses zero angular offset for a boat, the main-arm-dependent quarter-turn for other special vehicles, then applies the same side distance, vertical fallback sequence and collision checks (1.14.2 lines 1709–1737; 1.14.3 lines 1720–1748). The referenced `HorseBaseEntity.updatePassengerPosition` and `BoatEntity.updatePassengerPosition` overrides are unchanged in their guards, position calculations, rotation writes and data copy order (Horse 1.14.2/1.14.3 lines 914–930; Boat lines 627–657 / 626–656). Their shared dismount contract is preserved; no horse/boat movement delta was found.

### Local application of absolute XYZ and relative flags

`PlayerPositionLookS2CPacket.apply` invokes `onPlayerPositionLook` (packet source line 55 in both). The receiver first forces client-main-thread execution, obtains absolute packet values and velocity, then processes X/Y/Z in order: relative flags add current coordinates and increment previous-render coordinates; absolute flags replace previous-render coordinates and zero that velocity component. It writes velocity, applies relative pitch then yaw, writes position/angles, confirms the teleport and sends the resulting movement packet (ClientPlayNetworkHandler 1.14.2 lines 642–689; 1.14.3 lines 641–688). All gates and ordering match; only Yarn enum spellings differ. No local correction delta was found.

## Limits

The four verdicts are bounded to the listed methods and direct inputs/call gates. No full tick graph, complete 1.14.2/1.14.3 pair, unrelated lane, intermediate patch, runtime result or server movement policy was audited here. No implementation or build was performed.