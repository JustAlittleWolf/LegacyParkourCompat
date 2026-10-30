# Independent source audit: external player responses, 1.11.2 → 1.12.2

## Verdict and bound

Bounded audit of `.native-source/20261030/1112-1122-pool31/responses/report.md`, SHA-256 `DBD17701E69E4650B58B70A1A98A801EC168B343E7365BA92854E897A88DE388`. Verdict: listed client response claims are supported; gaps named by the report were closed for the listed methods and writer paths. No eligible external-response difference was found in the retraced scope. This audit does not establish full-pair coverage or freeze.

## Source identity

Canonical roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.11.2\ornithe-feather` and `...\1.12.2\ornithe-feather` (Ornithe Feather Gen2 build 2). Reported endpoint identities were checked against its source identity record: 1.11.2 client `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`, merged mappings `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757`, tiny `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`; 1.12.2 client `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`, merged mappings `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca`, tiny `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`.

Files independently hashed:

- `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`: 1.11.2 `65d362c00de1adfd55fae5f858b91bf9b227525b987c09a2d223279376a66175`; 1.12.2 `fce21d9902e555fd46545fb04bdb6c4a912eef6398790776ad1a122e09960c7c`.
- `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`: 1.11.2 `947be17cf3d7217e7c8e563d4dd312cd133de1dc06bcd82b4431c9e5e74bc0bf`; 1.12.2 `77bf65b2c48ff952713942e183af1cd5fb243ad4f8fd2e53aa1b97272e7acd7c`.
- `net/minecraft/server/PlayerManager.java`: 1.11.2 `5f92907db38432cc50b65efe23d125a56502a95be3edd0e31b361dd56d664163`; 1.12.2 `882937cb6794bc34366dd67a0b43f2c8122b54608ed822b5bd67880af503d53d`.
- `net/minecraft/network/packet/s2c/play/PlayerMoveS2CPacket.java`: identical SHA-256 `8bad09e146ce746085103081b7e3c6d09855114868267e12be8d869f34f0e77e`.

## Retraced comparisons and corrections

- `ClientPlayNetworkHandler.handleExplosion`, `handlePlayerMove`, `handleEntityVelocity`, `handleEntityTeleport`, and `handleEntityMove` are method-body identical between the endpoints. Full `handleExplosion` confirms the same world particle/sound/block-effect handling and the same unconditional addition of the packet's three player-velocity components to `minecraft.player` (not merely the velocity-write lines previously sampled). The report's “no difference” disposition stands.
- Client correction retains relative-coordinate additions, absolute-coordinate velocity reset, relative yaw/pitch handling, `updatePositionAndAngles`, teleport confirmation and response. Velocity packet still divides each fixed-point component by `8000.0` before `lerpVelocity`; remote position handlers retain the same lerp gates and calls. No correction needed.
- `ServerPlayNetworkHandler.teleport` is method-body identical. It resolves relative offsets from current player position/angles, sends the same `PlayerMoveS2CPacket` with the requested teleport id, updates position/angles, and resets fall distance. `PlayerMoveS2CPacket.java` is byte-identical. This closes the report's packet-construction gap.
- Respawn: `PlayerManager.respawn(ServerPlayerEntity,int,boolean)` is identical across endpoints, including spawn selection and calls to position the replacement entity; client `LocalClientPlayerEntity.respawn()` is identical. `ClientPlayNetworkHandler.handlePlayerRespawn` differs only in constructing `DownloadingTerrainScreen()` versus `DownloadingTerrainScreen(this)`; subsequent `minecraft.respawnPlayer(packet.getDimension())` is unchanged. The screen constructor change does not write player position or velocity. Server dimension transfer in `ServerPlayNetworkHandler` retains the same `setWorld` / interaction-manager world handoff and transfer call sequence in inspected context. These close the named respawn/world-transfer writer gap for this bounded path.
- Small correction to scope wording: server `ServerPlayNetworkHandler.handlePlayerMove` is not wholly unchanged. Its invalid-packet disconnect string changes to a translatable text key and a warning uses the non-array logger overload. Both are server-side validation/logging changes, not client response writers or historical client movement; they do not change the report's player-response verdict. Do not describe that entire method as identical if cited elsewhere.

## Remaining limit

The response report's terminal-method gap is closed for the listed client packet responses, server teleport packet creation, and respawn/dimension-transfer paths. This remains only a bounded lane audit: it does not exhaust every possible indirect/external state writer, every item/effect/status callback, piston collision/provider dependencies, or the complete movement tick and collision/provider inventories. Full-pair closure still requires the other assigned source lanes and independent complete-inventory audit. No build, test, game launch, or source generation was run.
