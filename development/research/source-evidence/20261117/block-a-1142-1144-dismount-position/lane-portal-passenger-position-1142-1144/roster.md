# Source roster: 1.14.2 → 1.14.4 portal passenger dismount

## Environment and evidence identities

- Execution environment: Windows 11 host, PowerShell.
- 1.14.2 source root: `decompiled_minecraft/1.14.2/yarn/`; mapping namespace Yarn, `1.14.2+build.7`, Intermediary `1.14.2-v2`.
  - Exact client artifact SHA-256: `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25`.
  - Yarn artifact SHA-256: `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b`.
  - Intermediary artifact SHA-256: `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e`.
  - Legacy provenance file: `.decompile-provenance.json`; relevant source hashes below match its `sources` entries. The legacy manifest has no `versionId` field; exact identity is supported by its exact 1.14.2 artifact URLs, mapping URLs, and version-root.
- 1.14.4 source root: `decompiled_minecraft/1.14.4/mojmap/`; mapping namespace Mojmap.
  - Ready marker says exact version `1.14.4`, mapping `mojmap`, status `ready`; client SHA-256: `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`.
  - Exact Mojang client mapping file SHA-256: `2dd53a5e70ba493cf6e33c0fc52bbdf4c57f9429c7a13842565aa825fd44d910` (source manifest entry `1.14.4/client_mappings.txt`).
  - `mojmap.provenance.json` records exact run version `1.14.4` and mapping option `feather,mojmap`; `mojmap.sources.sha256` SHA-256 is `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`, matching `mojmap.ready.json`.
- Mapping families are different because the prepared roots contain no 1.14.4 Yarn output. Each release was verified against its own exact mapping/source manifest; mapped names were not treated as evidence of behavioral identity by themselves.

## Inspected source files and verified SHA-256

All listed 1.14.2 file hashes were checked against `1.14.2/yarn/.decompile-provenance.json`. All listed 1.14.4 hashes were checked against `1.14.4/mojmap.sources.sha256`. The 1.14.4 source manifest hash was independently checked against its ready marker.

| Release / mapping | Relative file | SHA-256 |
| --- | --- | --- |
| 1.14.2 Yarn | `net/minecraft/entity/LivingEntity.java` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` |
| 1.14.2 Yarn | `net/minecraft/entity/Entity.java` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` |
| 1.14.2 Yarn | `net/minecraft/block/BlockState.java` | `c35e64104be023c3f7940dec7b459fb4252d552b6770baa45f4fb4c345dd7035` |
| 1.14.2 Yarn | `net/minecraft/block/PortalBlock.java` | `97ee27b8065a79e40eaaa25cd275746b87bcc4d975c02a458237cc0560f6b9a6` |
| 1.14.2 Yarn | `net/minecraft/block/Blocks.java` | `f5ba7169237468c68c085ecbf101e0e1c620013c00744a20dfae7c8af97c74dd` |
| 1.14.2 Yarn | `net/minecraft/block/Block.java` | `e90cdc7b0b78310c59eb5a96cfbfed0f0ec8aaf7156d20f0876e4a04711c2d1f` |
| 1.14.2 Yarn | `net/minecraft/server/network/ServerPlayerEntity.java` | `5dd585383d9cb52bf1db7304fc755ae392d48a6d1513ff9143e3b49934340381` |
| 1.14.2 Yarn | `net/minecraft/server/network/ServerPlayNetworkHandler.java` | `554278bbc6bed6b776312d35d1e4c964288cb349f4c06b2e9ff8f01fabc8c20a` |
| 1.14.2 Yarn | `net/minecraft/client/network/ClientPlayNetworkHandler.java` | `5055088f2e944c50be032cce0a921054eba2c13a022f60306e5e9411c400697b` |
| 1.14.2 Yarn | `net/minecraft/client/network/ClientPlayerEntity.java` | `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947` |
| 1.14.2 Yarn | `net/minecraft/client/network/packet/PlayerPositionLookS2CPacket.java` | `9303cb6dfa7f4db4b775d6e57304ebaf58790dcea915cdb89564edd05a9308e2` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |
| 1.14.4 Mojmap | `net/minecraft/world/level/block/state/BlockState.java` | `2bcc0167cb566df8745abbe967f0a8750038ad5a9b7843f5fb25bab714d73188` |
| 1.14.4 Mojmap | `net/minecraft/world/level/block/NetherPortalBlock.java` | `1256ce10afd7a6d355451773b8832c42464fbe4e3a7a4fdfab1b5eedf4b7208d` |
| 1.14.4 Mojmap | `net/minecraft/world/level/block/Blocks.java` | `983d0cde25f55ddb055015b682bbdf3b131394208561805f26d9b2a240dee9a9` |
| 1.14.4 Mojmap | `net/minecraft/world/level/block/Block.java` | `276022cfc5bc00437fe65a23bbcdc9c078026e68d88930eb3244581f6dbfcf5f` |
| 1.14.4 Mojmap | `net/minecraft/server/level/ServerPlayer.java` | `0b07bb15e2e3001f03dab2dc943cbca4814ada36c82b6d83f933be4ff01c4b92` |
| 1.14.4 Mojmap | `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `912f528c03da34fa703646b6383c2b49ac3493efd7ba78cb8ff27aa7f693b67d` |
| 1.14.4 Mojmap | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `6dddf37b79830d5413d91675e0bed8b4a3f0a179ac4535616dc57b510b778b45` |
| 1.14.4 Mojmap | `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |
| 1.14.4 Mojmap | `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` |

## Coverage disposition

- Examined: ordinary living-entity detachment and server-only stand-up selection; player and server-player overrides; mount-shape branch; Nether portal callback and capability predicate; portal no-collision registration; support-face predicates; server teleport argument-set handling and packet fields; client relative/absolute coordinate application; local-player stop-riding override.
- Result: no independent difference in the assigned portal dismount / correction slice; no finding draft created.
- Dependencies checked: superclass dispatch, old-to-new vehicle relationship, logical side, collision-support predicate, post-selection server correction, packet relative flags/position fields, client position writer, portal callback relation gate.
- Not examined: other player movement areas or full 1.14.2 → 1.14.4 inventory; this bounded result does not close the pair and is not an independent audit.
- Checks performed: source/static reading and SHA-256 verification only. No primary source tree, product file, Git history, tests, game/runtime or network was modified or launched.
