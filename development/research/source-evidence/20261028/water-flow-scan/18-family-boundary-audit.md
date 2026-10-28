# Liquid body-contact scan: 1.8 source boundary audit

**Audit date:** 2026-10-28  
**Method:** read-only inspection of already-published local Feather/Ornithe source trees and their provenance/manifest records. No decompiler, online mapping lookup, candidate/product source, build, test, or runtime was used.

## Result

The `floor(max + 1.0)` body-contact scan and its associated water query are byte-identical in every locally published exact Feather source tree from **1.8.2 through 1.8.9**, including 1.8.8. The relevant `World`, `Entity`, `LiquidBlock`, `ClientWorld`, and `ClientChunkCache` source files have the same SHA-256 across all eight releases. Thus this arithmetic applies across the available 1.8.2–1.8.9 sources.

There is no local `ornithe-feather` tree for 1.8 or 1.8.1 under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`; their applicability is not established here. Do not extend this result to those two releases by extrapolation. Consequently, the full `V1_8` alias is source-closed only if its supported set begins at 1.8.2; otherwise the 1.8/1.8.1 portion remains unresolved. In the already accepted comparison, the scan arithmetic changes by 1.9 (`ceil(max)`); this audit does not restate that comparison or claim a transition inside 1.8.

## Provenance and per-file identity

Source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather`.

For each 1.8.2–1.8.8 tree, the exact local `.decompile-provenance.json` was parsed, SHA-256 checked, and its five relevant `sources` entries were compared with the actual files on disk. All five entries match in every tree. The provenance record also pins that release's Feather Gen2 mergedv2 mapping artifact, Feather version metadata, and Mojang version metadata. The exact provenance record hashes are:

| Release | `.decompile-provenance.json` SHA-256 |
|---|---|
| 1.8.2 | `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472` |
| 1.8.3 | `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed` |
| 1.8.4 | `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00` |
| 1.8.5 | `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` |
| 1.8.6 | `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9` |
| 1.8.7 | `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef` |
| 1.8.8 | `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` |

The 1.8.9 tree publishes a ready record and a source manifest. The ready record is SHA-256 `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95`; it identifies version `1.8.9`, mapping `ornithe-feather`, 1,612 source files, client SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`, and source-manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`. The actual five files below match their lines in that source manifest. The 1.8.9 provenance record itself hashes to `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`.

The identical source-file identities for every release 1.8.2–1.8.9 are:

| Exact source file | SHA-256 in each release |
|---|---|
| `net/minecraft/world/World.java` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` |
| `net/minecraft/entity/Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` |
| `net/minecraft/block/LiquidBlock.java` | `1537fd6b1bca65e0f910f9a12bcd3bb27f5a86be7f13a1caa380c5e66544f1d5` |
| `net/minecraft/client/world/ClientWorld.java` | `710abde0f70c471a081abdfb3b70da681c6d817329801f8a2a49af88a25ee02b` |
| `net/minecraft/client/world/chunk/ClientChunkCache.java` | `5f22bccf9270a107e8471bab996cf6f9dbb4c92ccd48c8ad0a42144dbf919920` |

The 1.8.2–1.8.8 per-file hashes above were individually verified against each version's provenance `sources` map as well as the on-disk file. The 1.8.9 hashes were individually verified against the source manifest. Byte-identical file hashes make the following member lines identical across the eight versions.

## Exact member evidence

References are Feather source paths relative to each version's `ornithe-feather/` root. Ranges below were read from 1.8.8; the five full-file identity checks above bind those exact bytes for 1.8.2–1.8.9.

- `net/minecraft/world/World.java:1486-1527`, `applyLiquidDrag`: each min bound is `floor(min)`, each exclusive max is `floor(max + 1.0)` (lines 1487–1492), and loops use `o < j`, `p < l`, `q < n` (1501–1503). It gates on `isAreaLoaded(i,k,m,j,l,n,true)` (1493–1495). Matching material is checked at 1507; the liquid surface is `p + 1 - LiquidBlock.getHeightLoss(level)` and compared to the computed upper Y bound `l >= d` at 1508–1509. Drag aggregation and the `0.014` velocity addition are at 1510–1524.
- `net/minecraft/world/World.java:1529-1548`, `containsMaterial`, uses the same six `floor(min)` / `floor(max + 1.0)` bounds and half-open loops (1530–1545).
- `net/minecraft/entity/Entity.java:746-760`, `checkWaterCollisions`, passes `getShape().grown(0.0,-0.4F,0.0).contract(0.001,0.001,0.001)` to the water `applyLiquidDrag` call (747); a positive result resets fall distance, sets `inWater`, and clears fire time; a negative result clears `inWater` (748–760).
- `net/minecraft/block/LiquidBlock.java:41-47`, `getHeightLoss`, resets levels `>= 8` to `0`, then returns `(state + 1) / 9.0F`.
- `net/minecraft/world/World.java:205-228`, `isAreaLoaded`, admits only `maxY >= 0 && minY < 256` (206), shifts X/Z endpoints right by four, checks chunk endpoints inclusively (207–218), and delegates `allowEmpty` to `isChunkLoadedAt` (226–227).
- `net/minecraft/client/world/ClientWorld.java:39-50,87-90,119-124` constructs the client world, creates a `ClientChunkCache` as its chunk source, and routes chunk updates through its load/unload methods.
- `net/minecraft/client/world/chunk/ClientChunkCache.java:30-57,121-124` returns `true` unconditionally from `hasChunk`, unloads/removes cached entries, returns a shared empty chunk for absent coordinates, loads actual chunks, and resolves block positions by chunk coordinates. Because the scan calls `isAreaLoaded(..., true)`, this client `hasChunk` result makes the World loaded-area gate pass for client coordinates even when the cache returns its empty chunk; these exact client semantics are byte-identical in 1.8.2–1.8.9.

## Scope limits

This closes only source equivalence for the specified body-contact scan/query and its loaded-area/client-cache dependencies across the local exact Feather trees 1.8.2–1.8.9. It does not prove runtime movement parity, inspect releases 1.8 or 1.8.1 (no local Feather trees were available), or close any behavior outside the named files and members.

