# 1.20.4 → 1.20.5 packet-response residual (with 1.20.5 → 1.20.6 context)

## Scope and evidence identity

Read-only exact Mojmap source trees: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.20.4,1.20.5,1.20.6}\mojmap`. The supplied exact-case three-way inventory is `.native-source/20261030/1204-1206-pool44/whole-pair-inventory/three-way-java-path-inventory.csv`. This audit used its exact path statuses and SHA-256 values and independently checked selected source file hashes against those values. The inventory records `ClientPacketListener.java` as changed 1.20.4→1.20.5 (SHA-256 `ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f` → `e121e998ec25211aaecf91fffd0ea699cebd47eddea9134efd3f2ffbef8eb7cd`) and byte-identical 1.20.5→1.20.6. The three supplied source folders currently contain only `com/` and `net/`; no `mojmap.ready.json` or source manifest is present in those folders. The 1.20.4/1.20.5 identities and official Mojmap family are also stated in the prior direct-response report and its audit, but I could not independently re-check those markers in the current source roots.

This is a bounded residual response audit only. It does not claim complete movement inventory, independent full-pair audit, pair freeze, an exact patch introduction boundary, or runtime parity. Logical input and packet payload values are held equal; server packet production/physics, damage or health results, vehicle state, and world block updates are excluded.

## Changed ClientPacketListener handler inventory

Extracted handler bodies were compared across all three listener files. These are the complete changed/added/removed `handle*` method names found by body-hash comparison between 1.20.4 and 1.20.5; no removed handler was found. The table also includes unchanged `handleSetEntityPassengersPacket` because the residual state-writer scan found its direct LocalPlayer yaw update. Each disposition is for the requested LocalPlayer state surface.

| Member | Disposition |
|---|---|
| `handleLogin` | Relevant initialization/position receiver; reset/creation and yaw path retained under same packet input. API/UI and secure-chat additions do not add a movement-state delta. |
| `handleAddEntity` | Packet-spawned entity recreation/addition; no LocalPlayer target in the packet entity path. Excluded from LocalPlayer response. |
| `handleSetEntityMotion` | Relevant velocity receiver; prior audit covered it. Same body and `/8000.0` signed-short decode/application. |
| `handleSetEntityData` | Relevant player metadata/pose receiver; listener body is identical and has no LocalPlayer exclusion. The downstream `assignValues` implementation changes lock/null-check mechanics and adds an aggregate callback; for a valid pose value, the per-value `DATA_POSE` callback still refreshes dimensions, and the new Entity aggregate callback is empty. Pose enum values/code remain the same. |
| `handleTeleportEntity` | Relevant position/rotation/onGround receiver; prior audit covered it. Same body and controlled-instance guard. |
| `handleSetCarriedItem` | Relevant equipment context; identical hotbar bounds check and selected-slot write. |
| `handleMoveEntity` | Relevant position/rotation/onGround receiver; prior audit covered it. Same body and controlled-instance guard. |
| `handleMovePlayer` | Relevant direct position/velocity/rotation response; prior audit covered it. Exact body equal; does not write onGround. |
| `handleRespawn` | Relevant LocalPlayer replacement/reset, position/yaw and copied attributes/metadata. Same reset/copy/order for movement state; changes are level-loading API/UI and non-movement copied fields. |
| `handleExplosion` | **Prior selected inventory omitted this direct LocalPlayer velocity response.** Adds the three packet knockback floats to current player velocity identically in both versions; no source delta. |
| `handleSetEquipment` | Relevant equipment receiver; LocalPlayer remains a `LivingEntity`; receiver condition narrows to LivingEntity but the local-player response is still slot/item assignment. No changed movement delta proven. |
| `handleGameEvent` | Relevant candidate due weather/game mode and player state messages; game-mode ability update flows through unchanged `MultiPlayerGameMode.setLocalMode`, which calls the selected game type ability update in both versions. No changed position, velocity, rotation, ground, pose, ability, effect, attribute, or equipment response. End-portal screen reason is UI-only. |
| `handleSetEntityPassengersPacket` | Relevant direct rotation response on vehicle/passenger change: for LocalPlayer boarding a Boat, copies boat yaw to player yaw, old yaw and head yaw. Same listener body and same passenger list inputs; vehicle physics remains excluded. |
| `handleLookAt` | **Prior selected inventory omitted this direct LocalPlayer rotation response.** Same packet target-vector resolution and `player.lookAt(anchor, position)` call; no source delta. |
| `handleUpdateMobEffect` | Relevant effect receiver; prior audit covered it. Effect payload's factor/blend representation differs; same force-add receiver path, and the earlier bounded audit found no client movement attribute application from this response. |
| `handleRemoveMobEffect` | Relevant effect receiver; same LivingEntity/no-update removal semantics (holder/accessor API adaptation only). |
| `handlePlayerAbilities` | Relevant ability/speed receiver; prior audit covered it. Same five local ability/speed writes. |
| `handleUpdateAttributes` | Relevant movement attribute receiver; prior audit covered it. Same base value → clear modifiers → add transient modifiers order; API representation changes. |
| `handleBundlePacket` | Newly changed receiver/wrapper: iterates and dispatches bundled client packets in list order; does not itself write LocalPlayer state. Its effect is composition of member handlers in packet order. Bundle protocol is newly exposed in 1.20.5, but no changed per-member LocalPlayer response established. |
| `handleProjectilePowerPacket` | Added in 1.20.5; applies packet state to a projectile, not LocalPlayer. Excluded non-player state. |
| `handleDebugSample` | Added in 1.20.5; debug sample/UI handling only. Excluded. |
| `handleBlockEntityData` | Block-entity/world data response; no LocalPlayer state writes. Excluded world state. |
| `handleCommandSuggestions` | UI/chat completion response only. Excluded. |
| `handleConfigurationStart` | Connection phase transition only. Excluded. |
| `handleCustomPayload` | Custom channel dispatch; no direct LocalPlayer state writer in the changed receiver. Payload-specific gameplay behavior is outside this packet contract audit. |
| `handleUnknownCustomPayload` | Logs/ignores unknown channel payload; excluded. |
| `handleDisguisedChat` | Chat/UI response only. Excluded. |
| `handlePlayerChat` | Chat/UI response only. Excluded. |
| `handlePlayerCombatKill` | Combat/death messaging response; damage/health/death effects excluded. No eligible movement-state write compared. |
| `handleItemCooldown` | Inventory item-cooldown response only; no eligible movement-state write. Excluded. |
| `handleMapItemData` | Map/UI response only. Excluded. |
| `handleOpenBook` | UI response only. Excluded. |
| `handleAwardStats` | Statistics response only. Excluded. |
| `handleResetScore` | Scoreboard response only. Excluded. |
| `handleServerData` | Server metadata/UI response only. Excluded. |
| `handleSetDisplayObjective` | Scoreboard response only. Excluded. |
| `handleSetPlayerTeamPacket` | Scoreboard/team presentation response; no LocalPlayer movement state writer. Excluded. |
| `handleSetScore` | Scoreboard response only. Excluded. |
| `handleTabListCustomisation` | Tab-list/UI response only. Excluded. |
| `handleUpdateRecipes` | Recipe response only. Excluded. |
| `handleUpdateTags` | Registry/tag refresh; no direct LocalPlayer state write in this receiver. No movement effect established in this packet-response slice. |

A listener body-hash sweep additionally found no other changed `handle*` methods. All listed changed members have the same bodies in 1.20.5 and 1.20.6, including the three new 1.20.5 receivers (`handleBundlePacket`, `handleProjectilePowerPacket`, `handleDebugSample`).

## Previously omitted direct response paths

`ClientPacketListener.handleExplosion` is at 1.20.4 line 1132 and 1.20.5/1.20.6 line 1167. Extracted body SHA-256 in all three versions: `b91c77a87aaf8b26a5b4a17207ce3544ee6b3621a74e104d1fb82a227c079655`. It constructs/finalizes the explosion, then writes `minecraft.player.setDeltaMovement(current.add(knockbackX, knockbackY, knockbackZ))`. The packet carries three floats in the same order and decodes/encodes them in the same order; 1.20.5 changes particle/sound stream APIs and the sound field to a holder, not the knockback response. Packet full-source SHA-256: 1.20.4 `9895537d2e55927b9ffff3a8d4599e117693e655fcbfba0e2aec173b2dc1905b`; 1.20.5 and 1.20.6 `41b8d60fd85e4811bbc0b7b6bc623825f7824ec8266bb00ab4e2fb371438038c`.

`ClientPacketListener.handleSetEntityPassengersPacket` is at 1.20.4 line 953 and 1.20.5/1.20.6 line 974. Extracted body SHA-256 in all three versions: `3db92e21b347f1b7dd368031ffebc72755cb94757591a41adbc172d68e3ecf5a`. When the LocalPlayer newly becomes a passenger of a Boat, it copies boat yaw into the player current yaw, old yaw and head yaw. The changed `ClientboundSetPassengersPacket` retains the vehicle id and ordered passenger ids under the same packet input; full source SHA-256 is 1.20.4 `0d9f8c3494debb65b9de834ce20baf678089f8be79c22652939399540665cdf3`, 1.20.5 and 1.20.6 `6b194a346ae005be3d1c490d015546392c17ae63a3e56ed370795c48ddf8b371`. Vehicle motion/physics remains excluded; this is the direct player yaw response. This handler is unchanged, but was not in the prior selected response set.\n\n`ClientPacketListener.handleLookAt` is at 1.20.4 line 1430 and 1.20.5/1.20.6 line 1463. Extracted body SHA-256 in all three versions: `e26ba9b7dbf66e95e2642957e02d4a2ba4cc7a5b886c449378f52c35d3dd058a`. It resolves packet position against the level; if non-null, it invokes `minecraft.player.lookAt(packet anchor, resolved position)`. The changed `ClientboundPlayerLookAtPacket` file retains the same two anchor/target construction forms, target entity-id-or-explicit-position encoding, and anchor read/write values; new StreamCodec wrapper/access modifiers do not change receiver input. Full source SHA-256: 1.20.4 `d57bba9dbc71371e521ecdc93bed8648d280d072660a0dd193899a3fc6a2a761`; 1.20.5 and 1.20.6 `1809b15082da296e4f4606075a7dbca5968f902a2cb612538ee7df6f363a8d49`.

These are omitted coverage, not findings: under identical packet payload and client state, both versions execute identical player writes. No changed in-scope behavior is established by either path.

## Changed/added packet contracts checked

For already selected response handlers, the relevant changed packet contracts were checked by source path and whole-file SHA-256 against the inventory. Across 1.20.4→1.20.5, the position correction retains three doubles, two floats, packed relative-axis byte and VarInt teleport id; entity motion retains entity VarInt plus three signed shorts; entity move/teleport retains entity id, delta/rotation and onGround fields; player abilities retains its flags and two speed floats; entity metadata retains entity id and ordered packed data values; selected slot retains the slot VarInt; equipment retains entity id and slot/ItemStack pairs; attributes retain entity id and ordered attribute/base/modifier snapshots; mob-effect add/remove retain the entity/effect selection and duration/amplifier/flags required by the receiver. The previously documented effect-factor/blend representation change remains non-movement in the inspected client response path.

Other changed packet contracts that connect to changed listener handlers are `ClientboundLoginPacket`, `ClientboundRespawnPacket`, `ClientboundGameEventPacket`, `ClientboundBundlePacket` and the new `ClientboundBundleDelimiterPacket`, `ClientboundProjectilePowerPacket` (added), and `ClientboundDebugSamplePacket` (added). The login/respawn packets preserve common player spawn information and keep flags used by the same reset/copy sequence. Game-event type ids/float parameter are unchanged; only stream codec wrappers changed. Bundle adds ordered packet encapsulation; the bundle listener dispatches members in order. Projectile-power is non-player projectile state; debug-sample is diagnostic data. No newly added changed packet receiver writes LocalPlayer movement state beyond the identical bundle composition of existing handlers.

The only changed/added relevant direct-response packet contract not included by the earlier selected report is `ClientboundExplodePacket`, covered above. All changed packet classes mentioned in this section have inventory status `same` for 1.20.5→1.20.6; this source context therefore shows no further intervening change through 1.20.6.

## Exact extracted listener method hashes and line references

Hashes below are SHA-256 of the complete extracted Java method text, including signature/braces, UTF-8 encoded. The source file full hashes are recorded in the inventory identity section. `1.20.5 = 1.20.6` for every listed method.

| Member | 1.20.4 line/hash | 1.20.5–1.20.6 line/hash | Result |
|---|---|---|---|
| `handleExplosion` | 1132 / `b91c77a87aaf8b26a5b4a17207ce3544ee6b3621a74e104d1fb82a227c079655` | 1167 / same | identical local velocity add |
| `handleSetEntityPassengersPacket` | 953 / `3db92e21b347f1b7dd368031ffebc72755cb94757591a41adbc172d68e3ecf5a` | 974 / same | same direct boat yaw response |
| `handleLookAt` | 1430 / `e26ba9b7dbf66e95e2642957e02d4a2ba4cc7a5b886c449378f52c35d3dd058a` | 1463 / same | identical local rotation response |
| `handleSetEntityData` | 503 / `cf9f3e2e028c0b382ec72e9d42110c2b1c6305cbf4ce3a7af03efd2b6c7a86ae` | 524 / same | identical metadata receiver |
| `handleSetCarriedItem` | 549 / `684a9f53f582b0a05abf7ef46afaa712dd474e79a083ee701c41a6961fa11ac2` | 570 / same | identical selected-slot write |
| `handleLogin` | 371 / `ed4f192f8fe9c610baea5af897f594beda4dde177fc180183e9db88599b6e6fb` | 385 / `761c6ec0408d884247b6cab159d2e433b0dad93f42c476c13b4d13d4804541d3` | changed API/UI additions; same movement init |
| `handleAddEntity` | 435 / `ce8ef435df6cc7b9fc2bac909d6e9fd176a02edac05f33965b9c22ad3dbceb5f` | 456 / same | same packet entity recreation |
| `handleRespawn` | 1052 / `3ac32697e9789b599bca94bd9dd57687b796e666a1e1ca49e3ad438d219dcc05` | 1073 / `542404314074c78f3651ea6a425572ad1875de604f333016ff385ee0216ee00b` | changed level-loading path; same player movement reset/copy sequence |
| `handleGameEvent` | 1281 / `f73ded94979f88996374e2f4c6207258e938660b727567fcbe3ae6846d034d9a` | 1315 / `1b08bf74a466c80e450b79b4f41bd7434fe0a87b5f6d7addb9e7b7ede0195610` | changed UI call only |
| `handlePlayerAbilities` | 1783 / `02447c91e0cf55de44b537f6c7588ed76cb55dd86a7bd4a6f7400c0dc59160eb` | 1796 / same | identical ability/speed writes |
| `handleSetEquipment` | 1254 / `1def97d0a6424b3be89f6696863eb9daceebb0abc0290099b29b49222538c569` | 1289 / `4ff15afefb4b98373cb28e026ef6ae8ae38549cf9f2e4d74dd8060552184c167` | receiver narrowed to LivingEntity; LocalPlayer eligible |
| `handleUpdateAttributes` | 2049 / `db16448e4e00d68a9af2c004f2e1616fcba0f506c1fd10b5afc16f06274557c9` | 2059 / `011f0815a7298cf43627f22010d6480538ed853b8fa753fe5666fc6f4f5b8630` | API/accessor change; same operation order |
| `handleUpdateMobEffect` | 1501 / `1c9a9dba638f2962fda3e52afbfe921ee61e3e29276e4bfd2803f7e74d86090b` | 1534 / `e0d050099f5dc462729099c0495a479d3a493d960413658a7dfb8eb33213ec3b` | factor/blend payload difference; same force-add path |
| `handleRemoveMobEffect` | 1685 / `4d0fe5bac54d7ddc783b0271bc2cfa85f4a20eacdf6e28fa1fa536ecbcc0e8dc` | 1699 / `7861b298e30d7200547c49d853568563a3f71e671ad89dc2c44c84e07b11483d` | holder/pattern API; same removal |
| `handleMovePlayer` | 597 / `862438c7b838a725d0de148405912f377b58fbee9b5ad76b6e6f05fa92c29687` | 618 / same | identical correction |
| `handleSetEntityMotion` | 494 / `497c1c1d2560839fdc96429bd568438e14e14fc52d0a592f6cbcc454d431b4b9` | 515 / same | identical motion decode path |
| `handleTeleportEntity` | 512 / `1b6eb3984d5c2bf0fae582bf9974f8463f1624150749490624b62d534241e104` | 533 / same | identical response/gates |
| `handleMoveEntity` | 557 / `a9f06929d0b3f7a591b9bd066e4e58b541e3954f656c350bbe64aa582a6cbef8` | 578 / same | identical response/gates |
| `handleBundlePacket` | 2152 / `889285ddc769def7a0ab0989c8677cda0ba50ecb83e2ec05db105bf811a8bda0` | 2162 / `bd966b2b7b3352ff57c25c72b1ccbb6a7bfd2829607b58ab8f8786a29a6826a0` | ordered member dispatch wrapper |
| `handleProjectilePowerPacket` | absent | 2171 / `6f44434d34769fb2748e619427695d66730fbf01554c4cf3afaec7132d600e94` | added projectile state only |
| `handleDebugSample` | absent | 2192 / `01837f08eb774a7e9925d09e1903ea89fb3b75d6ad09d4aa14457e4cd3b107d2` | added diagnostic state only |

## Conclusion and remaining boundary

The residual sweep found three direct LocalPlayer responses omitted from the previous selected handler set: explosion knockback (velocity), look-at (rotation), and boat boarding yaw synchronization (rotation). Their handlers and movement-relevant packet data produce the same LocalPlayer writes for equal input in 1.20.4, 1.20.5 and 1.20.6. The only concrete outcome is a coverage gap closed here; no in-scope source difference/finding is established. This does not cover server-produced packet state, all non-packet player writers, ordinary tick/travel/pose/collision dependencies, or other release boundaries. No product files, canonical research, builds, tests, launches or runtime checks were changed or run.

## Source hashes for compared packet contracts

All paths below are relative to `net/minecraft/network/protocol/game/`; SHA-256 values are full file hashes from the supplied inventory and match the read-only source files. The 1.20.5 hash is also the 1.20.6 hash for every row (inventory status `same` for .5→.6), except the two packets added in .5, which are absent in .4 and identical .5→.6.

| Packet source | 1.20.4 SHA-256 | 1.20.5–1.20.6 SHA-256 | Contract locations checked |
|---|---|---|---|
| `ClientboundExplodePacket.java` | `9895537d2e55927b9ffff3a8d4599e117693e655fcbfba0e2aec173b2dc1905b` | `41b8d60fd85e4811bbc0b7b6bc623825f7824ec8266bb00ab4e2fb371438038c` | 4: read 64, write 97; 5: read 69, write 92; knockback getters 126–136 |
| `ClientboundPlayerLookAtPacket.java` | `d57bba9dbc71371e521ecdc93bed8648d280d072660a0dd193899a3fc6a2a761` | `1809b15082da296e4f4606075a7dbca5968f902a2cb612538ee7df6f363a8d49` | 4: read 41, write 57, `getPosition` 78; 5: read 46, write 61, `getPosition` 87 |
| `ClientboundPlayerPositionPacket.java` | `73e3823469af2d8f776255954bc8977297ce8335091ce8fbec048ae834e4d17a` | `03f4c58cb1f801cb62fe48a207a575b7bfd2a9fdcb5a0683bef1c0c9c75bed69` | 4: read 27, write 38; 5: read 32, write 42 |
| `ClientboundSetEntityMotionPacket.java` | `95c5c6568658e6ceaae42fb819c786a73c0f256deb4652c7ac93109f4ec05dc3` | `34e141d58c2d3585ef6a4b00ed4819c42fa11301ba8428ca06c5379fb7ecdae9` | 4: constructor 19, read 30, write 38; 5: constructor 24, read 35, write 42 |
| `ClientboundTeleportEntityPacket.java` | `71be3f6b2e51b29d9238b3752508b221bab898fb2ed1675163e8b6f8c89fa722` | `8584742ddacadb6ae2e156e1b66ba573a5cafda0693bb70601d5f42e6eb5917f` | 4: read 28, write 39; 5: read 33, write 43 |
| `ClientboundMoveEntityPacket.java` | `91c6c950b7bc1048deda1fd4567bb173005f9740881184dadb2d55839a1f9ec0` | `9653391a5b189c04903b36e5a2f7ae41bc22741b598121ff3b1ac06741583375` | 4: Pos/PosRot/Rot read-write 82–149; 5: 91–178 |
| `ClientboundSetEntityDataPacket.java` | `3f0ae7a85345fd0c474d2aab2e28a7b4f0c4b5344470875b387fd067cd86631b` | `bb588c05c9d664feeec1f665c925bb8ae7c661ea2ee868a568c83f96b1ecee3d` | 4: unpack 24, write 36; 5: unpack 29, write 40 |
| `ClientboundSetCarriedItemPacket.java` | `7a7a3af6224519f959ae320a902f4288367434a753b935c50078be73cfe6b2d9` | `c4e7a7a73fe5cf14afd0f671c26edbdfeb3475d41da9bc3d21fbcbe76b0e983a` | 4: read 13, write 18; 5: read 18, write 22 |
| `ClientboundSetEquipmentPacket.java` | `1cf1aca6f7a5c3106056c2ca5017d0022e5c8d6e837b31a30e47d5e8dc4759e0` | `249b4085d53d73d6874b1981248c90a5bd72e831190896effad1cd1dc776c3cb` | 4: read 21, write 36; 5: read 26, write 40 |
| `ClientboundPlayerAbilitiesPacket.java` | `021097261edf7119e04e4ccc150c8d907233705a366a102cc94e83d6136a90ab` | `d8a97051e88636df0502912d41e389a23ab9a199217eda206fa1f6fe678a0e65` | 4: read 28, write 39; 5: read 33, write 43 |
| `ClientboundUpdateAttributesPacket.java` | `2db489f1121f062a5756c4371a824b7fdbf54d5b2c9c8647e539235b48d35d1e` | `9a1e3d3ef78da7c72324ad9d526b9cf0ee983dace618a56337ab6d97381d7d2d` | 4: read 27, write 45; 5: snapshot codecs 62–81 |
| `ClientboundUpdateMobEffectPacket.java` | `88a2692d0a0fc83753ee1b6ba558030c7836d12d25b4752457fb86ac353a3f3a` | `268e9368525964192b0fb8f15f4a6418a8efcbcb657035b73752367b8ebc7f2e` | 4: read 45, write 55; 5: read 52, write 60 |
| `ClientboundRemoveMobEffectPacket.java` | `105e55f1ce42f1703410883401e33ea7cbc2b97953ebe251c78112acfd3e0dfb` | `d6835677f408245b93d76e9554a9eec9fce2db653a33ebc689e3efbd036a1ee6` | 4: read 20, write 26; 5: record codec 15–23 |
| `ClientboundLoginPacket.java` | `4e3059de683ed5462842016630623f061ffa8df920472424c29ed980c99588e0` | `c35034688ecb4b1774221f4617b37b15278bb184bd22cba007c350574d191c43` | 4: read 23, write 39; 5: read 31, write 47 |
| `ClientboundRespawnPacket.java` | `94ef015e43ef8d8ce3a4136aa94cddb651b0181319bbc0bc6c001d115ca31af4` | `272d6c1981ded5f5adf4c560e649aad0e6705bc157bc46d41686881943e38c78` | 4: read 11, write 16, keep flag 25; 5: read 16, write 20, keep flag 34 |
| `ClientboundSetPassengersPacket.java` | `0d9f8c3494debb65b9de834ce20baf678089f8be79c22652939399540665cdf3` | `6b194a346ae005be3d1c490d015546392c17ae63a3e56ed370795c48ddf8b371` | 4: read/write packet entity id + passenger ids; 5: same payload through codec |
| `ClientboundGameEventPacket.java` | `5d2e6c3d03c05772e634942f663824fb474cd89ed530c675db22868bcbbbc6e6` | `b005c75c55409f82627fe471806bde23931e39c03d471dcc8e94c14dfa8042f9` | 4: read 36, write 42; 5: read 41, write 46 |
| `ClientboundBundlePacket.java` | `34ca9f6c4c99e004a132047a7156a1dcda94c94cdacbb56abd7824e51a633dc5` | `afa32bca840ca79b8423261aee725ff63c75df1b9384f0a92fdd26f2ea2fb10f` | 4: ctor 7 / dispatch 11; 5: ctor 8 / dispatch 17 |
| `ClientboundProjectilePowerPacket.java` | absent | `d5cd5cab1da90db10d2a73110d0687383cb1579405791589e3c185aab5a576e3` | added contract; projectile-only receiver |
| `ClientboundBundleDelimiterPacket.java` | absent | `b8be3d13de6f31a518915822eb06b6712f273455e5d9eb4cee37af80dce8a011` | added bundle framing marker; no LocalPlayer writer |
| `ClientboundDebugSamplePacket.java` | absent | `e4c644fdfb6c33a72a6eafc81c0b4923cc07cf158c6a0cd2a0b2ed97c010aec5` | added diagnostic contract; no movement receiver |

## Pose metadata dependency closure

The metadata path is in-scope because `Entity.DATA_POSE` is movement-relevant. `ClientboundSetEntityDataPacket` still decodes a VarInt entity id and an ordered list of `(byte id, VarInt serializer id, serializer payload)` values; its handler calls `assignValues` on any found entity. In 1.20.4, `SynchedEntityData.DataValue.read/write` call serializer `read/write`; in 1.20.5 they call the serializer codec. `Pose` retains the same 18-value order (`STANDING` through `INHALING`); 1.20.5 assigns explicit ids 0–17 and uses `ByteBufCodecs.idMapper`, matching the earlier enum ordinal ids. For a `DATA_POSE` value, the per-accessor callback still calls `refreshDimensions()` (Entity.java 1.20.4 line 2708; 1.20.5/1.20.6 line 2719). 1.20.5 additionally invokes `onSyncedDataUpdated(List)` after item callbacks, but the base Entity implementation is empty and a search found no override of that list overload for LocalPlayer or its superclasses. Thus the downstream metadata delta establishes no changed pose/dimension response for the same valid packet values. `SynchedEntityData.java` full SHA-256 is 1.20.4 `2fd8ab1c4309ba66f0b0249d89f4209cf720fc8923be63277ae695232824f3e7`, 1.20.5–1.20.6 `755998494338969b6d47b91c463057d9a009d6804e8d06b0ab30719634cc9234`; `EntityDataSerializers.java`: 1.20.4 `7e70d3fddc979692f33c81ffec31a0ba85d49ca5249f97c8de78da0f5ae24c49`, 1.20.5–1.20.6 `3f8379c1669d51d5715621e54f7b9f7685a369f94161e8a8d36b6fa1deb530d9`; `Pose.java`: 1.20.4 `a8d0e2b720ae19f59ba832ad8a6e689bcc0505c2eda529ba6e36e581614587b2`, 1.20.5–1.20.6 `85c714072555d18f9bfac2e2a70460bd2c69318e11122e8dbc8c7814a7019fca`. The Entity callback file hashes are those already recorded in the prior audit and three-way inventory (1.20.4 `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9`; 1.20.5–1.20.6 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`).
