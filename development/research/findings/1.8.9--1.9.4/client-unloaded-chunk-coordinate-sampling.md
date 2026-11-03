# unloaded-client-chunk sampling uses different block coordinates

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float sideways, float forwards) {`, lines 1119-1239.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float sideways, float forwards) {`, lines 1302-1469.
1.9 stable, ornithe-feather; `feather-gen2-1.9+build.2-mergedv2.jar` (artifact SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`), embedded `1.9/ornithe-feather/.decompile-provenance.json` (SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`): `1.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1387-1394; `1.9/ornithe-feather/net/minecraft/util/math/BlockPos.java`, `PooledMutable#set(double, double, double)`, lines 360-361.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1585-1598; `1.13.2/ornithe-feather/net/minecraft/util/math/BlockPos.java`, `Mutable#set(double, double, double)`, lines 308-310 and `PooledMutable#set(double, double, double)`, lines 381-382.

The inspected stable 1.8.0-1.8.8 sources also use explicit casts in both post-move chunk predicates. Each row's gate lines refer to `decompiled_minecraft/<release>/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; its Feather Gen2 build.2 artifact SHA-256 and embedded provenance SHA-256 are recorded here:

| Stable release | `LivingEntity.moveRelative`, gate lines | Feather Gen2 build.2 artifact SHA-256 | Embedded `.decompile-provenance.json` SHA-256 |
| --- | --- | --- | --- |
| 1.8 | 1168-1169 | `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe` | `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05` |
| 1.8.1 | 1174-1175 | `dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7` | `f3a3baa039f0e0ade6e656dfa11a4529909a4b709ac489703c4250262cefb1ca` |
| 1.8.2 | 1172-1173 | `8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b` | `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472` |
| 1.8.3 | 1172-1173 | `e0ad2917dcc3a91586fb6f9b1bde78e55f1ece908572672885395e0d045dd9a2` | `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed` |
| 1.8.4 | 1172-1173 | `8ee5582ac3f6581e85ac27de5c9df9427a4bf67d0652531cf2e94eac4dde9b8c` | `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00` |
| 1.8.5 | 1172-1173 | `18ec5e1a8f4cdfc3a886a7e24fe6a8594f04f51f6669bb0619d727f27cdd887d` | `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` |
| 1.8.6 | 1172-1173 | `d1507e3253a944d59dd0b924b6495deb7732ecadb5f8bd06f7139c241639ad81` | `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9` |
| 1.8.7 | 1172-1173 | `e0cd7842acda42ae23d2e9d031358c1faac1ac01a139bb29ac8034fbaa5fa27b` | `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef` |
| 1.8.8 | 1172-1173 | `934d6112c5c1fa2d1801dbe78aee37ffbcec7ec03e1ed3a62c73de3c7925437f` | `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` |

Difference: 1.8.0-1.8.9 truncate the post-move floating coordinates toward zero before choosing a chunk. The stable 1.9 source already floors them through `PooledMutable#set(double, double, double)`, matching the later 1.9.4 source and establishing the first stable release boundary at 1.9. Releases 1.9.1-1.9.3 are not independently reviewed here; no claim is made about their exact behavior.

The resulting chunk differs only at negative chunk boundaries for coordinates just below a multiple of 16, such as `-0.2` (1.8.9 stores 0, 1.9.4 stores -1).

If this maps the two versions to chunks with different loadedness on a client, their Y-velocity update takes different branches: ordinary gravity versus the unloaded fallback (`-0.1` above Y=0, otherwise `0`), followed by the shared `0.98F` multiplier.

For the 1.8.9 versus 1.9.4 comparison, no chunk-loading state or subsequent trajectory is inferred.

Endpoint witness: The same coordinate-sampling difference is present between 1.8.9 and 1.13.2. In 1.8.9, both the `isChunkLoaded` and `getChunk(...).isLoaded()` predicates construct their `BlockPos` from `(int)this.x` and `(int)this.z`, truncating toward zero. In 1.13.2, `moveRelative` sets one pooled mutable position from the double coordinates and reuses it for both predicates; `BlockPos.Mutable#set(double, double, double)` floors each component. For a clear tick starting at `(x=0.2,z=0.5)` in loaded chunk 0 and moving to post-move `(x=-0.2,z=0.5)`, the 1.8.9 predicates query `(0,0,0)`, while the 1.13.2 predicates query `(-1,0,0)`. If chunk 0 is loaded and chunk -1 is absent, 1.8.9 therefore takes ordinary gravity while 1.13.2 takes the unloaded fallback. This is an endpoint applicability witness only; it does not locate the change within the intervening releases.

Movement meaning: 1.8.9 truncates the floating coordinate toward zero before choosing a chunk; 1.9.4 floors it.

The resulting chunk differs only at negative chunk boundaries for coordinates just below a multiple of 16, such as `-0.2` (1.8.9 stores 0, 1.9.4 stores -1).

If this maps the two versions to chunks with different loadedness on a client, their Y-velocity update takes different branches: ordinary gravity versus the unloaded fallback (`-0.1` above Y=0, otherwise `0`), followed by the shared `0.98F` multiplier.

The conditional witnesses establish only the immediate gravity branch; no later position or subsequent-tick trajectory is inferred.
