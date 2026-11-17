# OOB collision guard reachability — source facts

## Scope and result

Fresh source-only comparison of the legacy pose-size collision query in exact 1.9, 1.9.1, 1.9.2, 1.9.3, and 1.9.4 Feather-mapped source roots. The X/Z checks in `World.getCollisions(Box)` execute only after the scan's `isChunkLoaded(BlockPos)` gate. In 1.9–1.9.2, the gate rejects an OOB scan position; the OOB return is unreachable for such positions on this path. In 1.9.3–1.9.4, the client-world override makes the gate true for `allowEmpty=true`, so an OOB scan position can reach the guard, which returns `true`; `PlayerEntity.updatePlayerPose` then refuses the proposed size change. The effect is live in these later client paths, subject to the pose box scan reaching a coordinate outside the ±30,000,000 limits.

This packet is source facts only; it makes no acceptance decision. Exact published source/input bindings were verified for all five releases. For 1.9–1.9.3, each mapping root has a hidden `.decompile-provenance.json`; all six examined source files per release match the file digests in its `sources` map, and the exact client and Feather artifact identities are recorded in `inputs`. For 1.9.4, the ready/source/artifact/client bindings were verified as described below. No hash or binding mismatch was found.

## Exact method facts

### 1.9, 1.9.1, 1.9.2

`net/minecraft/world/World.java`, `contains` around lines 167–169, checks inclusive lower and exclusive upper X/Z bounds (`>= -30000000`, `< 30000000`) and Y in `[0,256)`. `isChunkLoaded(BlockPos)` delegates to `isChunkLoaded(pos, true)` (lines 176–177). The two-argument overload (lines 180–182) is `!contains(pos) ? false : isChunkLoadedAt(pos.getX() >> 4, pos.getZ() >> 4, allowEmpty)`. Thus the scan's y=64 probe is rejected for any X/Z outside the limits before the chunk implementation runs.

`World.getCollisions(Box)` (1.9/1.9.1/1.9.2, line 1012 onward) derives integer bounds in this order: `i=floor(minX)-1`, `j=ceil(maxX)+1`, `k=floor(minY)-1`, `l=ceil(maxY)+1`, `m=floor(minZ)-1`, `n=ceil(maxZ)+1`; obtains a pooled mutable position; loops X `o=i..j-1`, then Z `p=m..n-1`; computes `q` as one for each X/Z scan edge; skips corner positions where `q==2`; calls `isChunkLoaded(pooledMutable.set(o,64,p))`; only when that gate succeeds loops Y `r=k..l-1`, skipping Y edge positions only for interior X/Z (`q<=0 || r!=k && r!=l-1`). For each visited position it sets `(o,r,p)`, checks `o < -30000000 || o >= 30000000 || p < -30000000 || p >= 30000000`, then returns true on that condition; otherwise reads block state and accumulates collisions, returning true if the list is nonempty. The pooled position is released in `finally`; normal exhaustion returns false.

Because `contains` also rejects OOB X/Z at the earlier y=64 probe, the later X/Z guard cannot be reached for the OOB coordinate. The ordinary in-range scan proceeds into the Y loop and evaluates the guard false.

### 1.9.3, 1.9.4

`World.contains(BlockPos)` (1.9.3 line 169; 1.9.4 line 169) is still defined with the same inclusive-lower/exclusive-upper X/Z limits and calls `isOutsideWorldHeight(pos)`. The helper rejects Y `<0` or `>=256`. However `World.isChunkLoaded(BlockPos, boolean)` (1.9.3 line 186; 1.9.4 line 186) now directly delegates to `isChunkLoadedAt(pos.getX() >> 4, pos.getZ() >> 4, allowEmpty)`; it no longer invokes `contains`. The one-argument overload still passes `true`.

`ClientWorld.isChunkLoadedAt` (1.9.3 line 104; 1.9.4 line 104) is `return allowEmpty || !this.getChunkSource().getChunk(chunkX, chunkZ).isEmpty();`. The collision scan calls the one-argument overload, so `allowEmpty` is true and short-circuits to true even for OOB chunk coordinates. There is no `ClientWorld` override of `isChunkLoaded(BlockPos)` or `contains(BlockPos)` in any of the five releases; the client override is the abstract chunk-coordinate operation `isChunkLoadedAt`.

`World.getCollisions(Box)` has the same loop order, edge/corner filter, loaded probe at `(o,64,p)`, Y-loop filter and OOB guard ordering as in 1.9–1.9.2 (1.9.3 line 1018 onward; 1.9.4 line 1018 onward). The OOB guard precedes `getBlockState`, hence this true return does not depend on an OOB block lookup. For an OOB coordinate that is not a doubly-edged X/Z corner (`q != 2`), the client loaded gate admits the scan and the Y loop reaches the OOB guard. Consequently the guard is reachable on the ordinary `ClientWorld` path in these releases.

### Pose-query caller and concrete client world

`PlayerEntity.updatePlayerPose` (1.9/1.9.1/1.9.2 line 284; 1.9.3/1.9.4 line 285) chooses the target width/height by flying, sleeping, sneaking, or standing state. If target dimensions differ, it obtains the current shape, constructs a candidate box from the current minimum corner plus the target width/height, calls `this.world.getCollisions(box)`, and changes size only if that call returns false. `setSneaking` calls `updatePlayerPose` (1.9–1.9.2 line 244; 1.9.3–1.9.4 line 245).

The ordinary local-client construction path is `Minecraft.setWorld(ClientWorld world)` (1.9–1.9.2 line 1848; 1.9.3–1.9.4 line 1851), which stores that typed `ClientWorld` and, when creating the player, passes the same local `world` to `interactionManager.createPlayer`. `ClientPlayerInteractionManager.createPlayer(World, ...)` constructs `LocalClientPlayerEntity` with that world; its constructor passes the same reference to `ClientPlayerEntity`/`PlayerEntity` via `super(world, profile)`. Thus the local player's pose query runs on the client `ClientWorld` and reaches its `isChunkLoadedAt` override.

### Narrow version comparison

The code in `getCollisions` contains the same literal OOB check in all five versions. The observable distinction in this path is whether its earlier scan-position loaded gate admits an OOB X/Z coordinate: it does not for 1.9–1.9.2, and does for 1.9.3–1.9.4 because the base coordinate-range gate was removed from `isChunkLoaded(BlockPos)` and the client implementation short-circuits for `allowEmpty=true`. No trajectory or behavior beyond this direct collision result and caller size gate is inferred.

## Evidence identity and source hashes

Exact 1.9.4 publication binding checked:

- `ornithe-feather.ready.json`: `status=ready`, `versionId=1.9.4`, `mapping=ornithe-feather`, source manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`, artifact manifest SHA-256 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`, and client SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`.
- Computed source-manifest and artifact-manifest hashes match the ready bindings. `artifacts.sha256` binds `1.9.4/client.jar` to the same client hash, the exact release metadata to version ID 1.9.4, and Feather `feather-gen2-1.9.4+build.2-mergedv2.jar` / `.tiny` artifacts to hashes `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125` / `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027`. Every examined 1.9.4 source file below matches its `ornithe-feather.sources.sha256` entry.
- The local `ornithe-feather.verification.json` reports 1819 source files verified, zero source differences, and no missing published source files; the noted artifact difference is only the regenerated derived remap jar, with raw input artifacts unchanged.

Computed source hashes for the six examined files per release. For 1.9–1.9.3, all listed hashes match each release's `.decompile-provenance.json` `sources` entries. For 1.9.4, all listed hashes match the published `ornithe-feather.sources.sha256` entries. Exact input bindings from the four earlier provenance manifests are:

| Release | Feather mapping artifact SHA-256 | Mojang client SHA-256 |
|---|---|---|
| 1.9 | `feather-gen2-1.9+build.2-mergedv2.jar`: `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` | `client.jar` (`2f67dfe8953299440d1902f9124f0f2c3a2c940f`): `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307` |
| 1.9.1 | `feather-gen2-1.9.1+build.2-mergedv2.jar`: `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` | `client.jar` (`9bc7f02323d90b9385c1a5dbd47fb144a3fb8835`): `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9` |
| 1.9.2 | `feather-gen2-1.9.2+build.2-mergedv2.jar`: `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` | `client.jar` (`19106fd5e222dca0f2dde9f66db8384c9a7db957`): `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65` |
| 1.9.3 | `feather-gen2-1.9.3+build.2-mergedv2.jar`: `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3` | `client.jar` (`b6985b0d3a0520dfb6f17eeb1e8ba58ce9577061`): `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b` |
| Release | `World.java` | `ClientWorld.java` | `PlayerEntity.java` | `LocalClientPlayerEntity.java` | `ClientPlayerInteractionManager.java` | `Minecraft.java` |
|---|---|---|---|---|---|---|
| 1.9 | `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` | `17322ce757cef1d167a4e99e45fa98ebf8a134ade80c9637af657359269723a8` | `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1` | `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06` | `7f64e714377fba1e09d47b1a89a55e5bee961040b6707ecf9c1065df740fea53` | `85ae1e4910b90a1d3e5196427e1409300e3d62f60b83919e26e50eff23ce5fff` |
| 1.9.1 | `cdda70c97f0e4d6f6c454c8f6d3c0d47f0e0ae612e75062d8fe93ddd3963e6b0` | `17322ce757cef1d167a4e99e45fa98ebf8a134ade80c9637af657359269723a8` | `d7035f08f03b01823dff70ca504f7244f9b0753542cc0f6dd7301e66f8866b71` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` | `7f64e714377fba1e09d47b1a89a55e5bee961040b6707ecf9c1065df740fea53` | `f6b11d65fc630fa9ca5f462c0c14d04eed488e3aba2bc3ad2b6e6b2c3eb17a0b` |
| 1.9.2 | `cdda70c97f0e4d6f6c454c8f6d3c0d47f0e0ae612e75062d8fe93ddd3963e6b0` | `17322ce757cef1d167a4e99e45fa98ebf8a134ade80c9637af657359269723a8` | `1fc5107b9b7f5711105d521a28829f2466fe9f297e832f4d209e6ec422111aac` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` | `7f64e714377fba1e09d47b1a89a55e5bee961040b6707ecf9c1065df740fea53` | `9f90629608f2b120427e608668cbc6900b1fc56b8f433f8591272f5e82ae2056` |
| 1.9.3 | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` | `8bbda6e31e1424bf65b89a0c35110f213841695766870cf526bf6e707f67a74b` | `ef96c8a00443f724eadad05a6a4dcc8a8a2e23cd4c3390b9e0f1f78c9ff4d1af` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` | `a1ce0dd0b78121581f35bf501291e85a24b18d94daff1ad6e3965c85f75d43cd` | `3c203116f02fcff474fdb36f21352b1845066bdf3d3cf1ae420fa68037d98142` |
| 1.9.4 | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` | `8bbda6e31e1424bf65b89a0c35110f213841695766870cf526bf6e707f67a74b` | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` | `a1ce0dd0b78121581f35bf501291e85a24b18d94daff1ad6e3965c85f75d43cd` | `6778bd1f0e8aa8a29fdce3bc5285cbd3ea56fa18ca8a43afc4d88439ab12133e` |

## Files and methods examined

- `net/minecraft/world/World.java`: `contains`, `isChunkLoaded(BlockPos[, boolean])`, `getCollisions(Box)`.
- `net/minecraft/client/world/ClientWorld.java`: `isChunkLoadedAt(int,int,boolean)` and absence of overrides for the above coordinate-level methods.
- `net/minecraft/entity/living/player/PlayerEntity.java`: `updatePlayerPose`, caller `setSneaking`.
- `net/minecraft/client/Minecraft.java`: typed `ClientWorld world`, `setWorld` player construction.
- `net/minecraft/client/ClientPlayerInteractionManager.java`: local player factory.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: constructor's world forwarding.

No source generation, edits outside this packet, builds, tests, game launches, or runtime checks were performed.

