# 1.9.2 → 1.9.3 cached-chunk and player-query gate follow-up

## Exact inputs

- Pair and source namespace: Minecraft `1.9.2` → `1.9.3`, `ornithe-feather`.
- 1.9.2 mapping-local source manifest SHA-256: `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`.
- 1.9.3 mapping-local source manifest SHA-256: `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`.
- Current accepted source-coverage revision: `patches/1.9.2--1.9.3/coverage-r3.md`, SHA-256 `E1D3BC5FD6D778C87CB9337884440D7E742B1DB493A8D51C09E1F35F71E9A636`.
- Bounded query report: `development/research/source-evidence/20261117/checkpoint04/source-189-19-20261117/patches/1.9.2--1.9.3/world-query-limit-followup.md`, SHA-256 `5BFBA153A7AC91BE980B3126FA38FA59F1056116F02C0F22F2260A1B003F1CE8`.
- Bounded query audit ACCEPT: `patches/1.9.2--1.9.3/world-query-followup-audit/verdict.md`, SHA-256 `FB2F3CC8813782B5324FCA5FE10F386F36CC93BA92F0B6F1568EACB1B82BBA26`.
- Full-pair r4 continuation accepts the corrected coverage report as PARTIAL: `patches/1.9.2--1.9.3/audit-r4/final-verdict.md`, SHA-256 `ED503ABEAD5DA07741A8BB164DE907703D68B8D8232A23157D3623B246BC18CF`.

## Result

The ordinary server-to-client chunk path has no hard-world-coordinate or world-border filter for its view-distance fan-out. `ChunkMap.addPlayer` centers on the server player's chunk and loops over the square `center ± chunkViewDistance`; `getOrAddChunk` and `ServerChunkCache.getChunk` accept those coordinates. `ChunkHolder` sends a populated chunk using `WorldChunkS2CPacket`. On the client, `ClientPlayNetworkHandler.handleWorldChunk` passes the packet coordinates to `ClientWorld.updateChunk`/`getChunkAt`; `updateChunk` calls `ClientChunkCache.loadChunk`, which inserts the exact `ChunkPos.toLong(x,z)` key without a coordinate clamp. Thus, with a server player at the hard-capped border and a view distance of at least 1, ordinary streaming can populate cache entries for chunks beyond the horizontal world-coordinate limit. The server-side `ChunkMap.updateViewDistance` clamps view distance to 3–32 (1.9.2 line 281 onward; 1.9.3 line 284 onward).

This closes cache *presence* as an impossible-route objection, but does not close the movement-query gate. `WorldBorder.maxSize` is `29,999,984`; `World.contains`'s horizontal limit begins at `30,000,000`. In `World.getCollisions(entity, box)`, the scan is `floor(min)-1` to exclusive `ceil(max)+1`, and actual block state is read for an outside-border scan only when `!bl2` (`bl2` is `isWithinBorder(worldBorder, entity)`). An entity still within the border uses the synthetic stone state for border-exterior scan cells, even in 1.9.3. The hard-coordinate-outside cell is at least 16 blocks beyond the capped border, so a local player standing/moving at that border does not establish the required outside-border entity plus scan reaching `x/z >= 30,000,000` (or below `-30,000,000`). `Entity.move` queries an expanded box before axis clipping, but the source-only movement path does not establish an ordinary displacement/state large enough to span that gap.

`PlayerEntity.tick` clamps x/z to ±`29,999,999` only after `super.tick()`; this is a late guard, not proof that ordinary local movement first places the player there. `LocalClientPlayerEntity.tick`'s initial chunk gate also differs as recorded in coverage-r3, but that does not by itself establish a normal way to enter the out-of-border state. A teleport/externally supplied position or unusually large displacement could satisfy the conditional query; no such ordinary ingress is established here. The cached chunk's actual state and whether it supplies a non-air collision box at the queried out-of-range coordinate are also not proven.

The pose-size caller is distinct: `PlayerEntity.updatePlayerPose` calls `World.getCollisions(Box)` without an entity argument. It therefore does not use `Entity.move`'s entity-border `bl2` gate, but it still needs a pose box whose scan reaches an out-of-hard-limit coordinate. Its presence is not evidence of a changed movement collision response or ordinary player reachability. This report does not merge the pose query and entity-aware movement query.

**Disposition:** normal boundary chunk streaming/cache population is source-supported; normal local-player reachability of the state/query needed to consume an out-of-hard-limit collision state remains unresolved. Keep the accepted query observation conditional, retain the pair's PARTIAL status, and do not promote it to a normal-session movement finding or implementation-ready claim.

## Source methods and exact source hashes

Every listed file hash was rechecked against the corresponding mapping-local `.decompile-provenance.json` `sources` row; all 28 rows matched on disk.

| Source file | 1.9.2 SHA-256 | 1.9.3 SHA-256 |
|---|---|---|
| `net/minecraft/world/World.java` | `CDDA70C97F0E4D6F6C454C8F6D3C0D47F0E0AE612E75062D8FE93DDD3963E6B0` | `2FE063E0EC224EED9FC7D01B8F788C32035EED5FEE5A9D98EA5E82296236E05A` |
| `net/minecraft/entity/Entity.java` | `74D3AC34D7E6355C8FDCDD777DCD95BA9EF4626718C8772C1B58D0E28419E42A` | `BCD7FA2206BF8D7271102F6DA7FE2771F96DBE22EC79DAB9F2101A3E29C17EF0` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `1FC5107B9B7F5711105D521A28829F2466FE9F297E832F4D209E6EC422111AAC` | `EF96C8A00443F724EADAD05A6A4DCC8A8A2E23CD4C3390B9E0F1F78C9FF4D1AF` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `DE9AD48BCFB2705EF7A9A63E9401C1742AEAD183E1E9C2D13F8FE40CCA0139EB` | `8AAF711948B7602C2E6C015A37E36ED06073D39727D999D80480B4910B704F5D` |
| `net/minecraft/client/world/ClientWorld.java` | `17322CE757CEF1D167A4E99E45FA98EBF8A134ADE80C9637AF657359269723A8` | `8BBDA6E31E1424BF65B89A0C35110F213841695766870CF526BF6E707F67A74B` |
| `net/minecraft/client/world/chunk/ClientChunkCache.java` | `027FE333AB8BB1F3B70F445C137FF817A1B6426AD9F11E81BB24A8CBAC0E01EB` | `08CDDB35ED4FDEE05E28943C16D61EF8119A1DDAC4BAF533F3487202B63CF048` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `7F67BD2DAA792CFC4AAC1744D30F9CFDB28306EC55583D474DDC1A251561B1D6` | `5A2FC039B81EC0E77DF3354826AAAF00DFA6E0D1296CF111864900932E811A4C` |
| `net/minecraft/server/ChunkMap.java` | `2184BC9F35C24E10E6E5BE6762424619D26BF3387CAFA1AAD5624995A2600D5E` | `0EE61CFAD881DFB0FD1FD079C1FAA47EB27B4BEE1D1272F760541FEC9D2CBA4B` |
| `net/minecraft/server/ChunkHolder.java` | `39120C9177EE527D69C66070100A0BC331E1F057EAA656A98C6FAD30135D99A5` | `58A0EE2953C798CD19E8E3ED8EC2A0688B27592ABAD17D999FCD9D3764CEFDB5` |
| `net/minecraft/server/world/chunk/ServerChunkCache.java` | `E7335A22414108AA4979602E1F5F91A886FDF73C17B00A0E5DE2A912CB8185A9` | `CB53198EAFF56342C5E747A44D66D4ED83F2AD574FDE59266719A2D9479794A6` |
| `net/minecraft/util/math/ChunkPos.java` | `C8C6E1E009E79740D24528C302CE2C8C7A22DB58CBD601DCECADBAA5EBE4D4E9` | `C8C6E1E009E79740D24528C302CE2C8C7A22DB58CBD601DCECADBAA5EBE4D4E9` |
| `net/minecraft/world/chunk/WorldChunk.java` | `B98BCCF16277A5A62B09AC2E77DB7D77E098A62D8029C03A44FBED62E01FF267` | `EB5C4B9ECEFDCC17CEE106FBFC4C3EEE0768E88E4BB36E503FCCAE852CB276E6` |
| `net/minecraft/world/chunk/EmptyChunk.java` | `6514D63A884CADE2E93024C90186767582FA36A5F283F0F49AC47D664CFC2804` | `FFC3414F16CCB2B1523A7B9B2C9ADFC2946C006BDB97B83C3C29F405EE5F8FD0` |
| `net/minecraft/world/border/WorldBorder.java` | `1F0052BC10592684EA845AA36B99B4DC78D1F4368DF21625349FDD887B561F95` | `1F0052BC10592684EA845AA36B99B4DC78D1F4368DF21625349FDD887B561F95` |

Key locations: 1.9.2 `World.contains`/`isChunkLoaded` (167–182), `getBlockState` (614–620), `getCollisions` (893 onward) and `isWithinBorder` (957 onward); 1.9.3 corresponding methods (169–188, 621–628, 899 onward, 963 onward). `WorldBorder.maxSize` is line 18 and `getMaxX` line 79 in both versions. Server fan-out is `ChunkMap.addPlayer` (202 / 205), `getOrAddChunk`, and `updateViewDistance` (281 / 284); `ChunkHolder.addPlayer`, `populate`, and `sendChunk`; and `ServerChunkCache.getChunk` (82 / 89). Client receipt is `ClientPlayNetworkHandler.handleWorldChunk` (606 / 607), `ClientWorld.updateChunk` (153 / 154) and `isChunkLoadedAt` (103 / 104), and `ClientChunkCache.loadChunk/getChunk` (42–53 / 48–58). The distinct pose path is `PlayerEntity.updatePlayerPose` (284 / 285), while `LocalClientPlayerEntity.tick` is line 142 / 143, `PlayerEntity.tick` line 169 / 170, and `Entity.move` contains the entity-aware calls to `World.getCollisions` (1.9.2 lines 464–539; 1.9.3 lines 467–542).

No source, product, wiki, backlog or runtime files were changed or consulted beyond the exact-pair source coverage and accepted query evidence named above.



