# Lily Pad pre-1.8.9 applicability addendum

## Result

The old Lily Pad collision geometry applies throughout every 1.8 profile alias: 1.8, 1.8.1 through 1.8.4, and 1.8.5 through 1.8.9. All ten exact release trees retain constructor bounds `[0,0,0]–[1,1/64,1]`, a non-boat `addCollisions` gate, and translation of those bounds by the queried block position. Each `Blocks.LILY_PAD` field resolves the `waterlily` registry entry. No pre-1.8.9 alias introduces a different Lily Pad collision result.

## Source identity and mapping

Readonly source trees are under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather`. The recorded mapping artifact for every version is its exact Feather Gen 2 build 2 release artifact. Provenance records for 1.8–1.8.8 name the corresponding mapped source hash entries; I rehashed the current `LilyPadBlock.java` and matched each record. For 1.8.9 the current source hash matches `ornithe-feather.sources.sha256`.

| Release | `LilyPadBlock.java` SHA-256 | Provenance record SHA-256 |
|---|---|---|
| 1.8 | `9a5544144f65fdd51b072240549f43d53e9cd81dbac5dafb5c8e689f575548ad` | `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05` |
| 1.8.1 | `9a5544144f65fdd51b072240549f43d53e9cd81dbac5dafb5c8e689f575548ad` | `f3a3baa039f0e0ade6e656dfa11a4529909a4b709ac489703c4250262cefb1ca` |
| 1.8.2 | `c5d5c92a927e5d86feb5fbf22102e7200037314cdba6c1207abbe52445580f35` | `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472` |
| 1.8.3 | `c5d5c92a927e5d86feb5fbf22102e7200037314cdba6c1207abbe52445580f35` | `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed` |
| 1.8.4 | `c5d5c92a927e5d86feb5fbf22102e7200037314cdba6c1207abbe52445580f35` | `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00` |
| 1.8.5 | `c5d5c92a927e5d86feb5fbf22102e7200037314cdba6c1207abbe52445580f35` | `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` |
| 1.8.6 | `c5d5c92a927e5d86feb5fbf22102e7200037314cdba6c1207abbe52445580f35` | `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9` |
| 1.8.7 | `c5d5c92a927e5d86feb5fbf22102e7200037314cdba6c1207abbe52445580f35` | `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef` |
| 1.8.8 | `c5d5c92a927e5d86feb5fbf22102e7200037314cdba6c1207abbe52445580f35` | `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` |
| 1.8.9 | `c5d5c92a927e5d86feb5fbf22102e7200037314cdba6c1207abbe52445580f35` | `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` |

## Static method hash reuse

For the hashes below I extracted each method body from its opening `{` through its matching `}` and SHA-256 hashed the exact UTF-8 text. This confirms where source bodies can be reused across the selectable releases, while preserving the minor-release mapping identities above.

| Method body | Releases | SHA-256 |
|---|---|---|
| `LilyPadBlock` constructor | 1.8, 1.8.1 | `a3695db1f869be2b02b28abaaeab32e9f7fb0b3c5bff911e7b63b6baebaebd71` |
| `LilyPadBlock` constructor | 1.8.2–1.8.9 | `efc173dd01299033b7ddaed08b467357fdef558268946cca3b3387ddc41fe3f3` |
| `LilyPadBlock.addCollisions` | 1.8–1.8.9 | `2704dbcbe03a9943ec6d4d0b76d968ebd2dc321efe67f1287c50efb96ea995c3` |
| `LilyPadBlock.getCollisionShape` | 1.8–1.8.9 | `8e36e1a670670d3a60b6f84e3df0c7d0f2837ad510f71751738c91b5190aa34d` |
| `Block.setShape` | 1.8–1.8.9 | `c6ae74817238c44d38a0fec8803653c7e21257877f5fb9255fcb9d02336908d8` |
| `Block.addCollisions` | 1.8, 1.8.1 | `c01742da2590a54bef1ba5f24ab19d9cb9218fb5e787935744d93f780613ba5d` |
| `Block.addCollisions` | 1.8.2–1.8.9 | `237a07fa3701cbba102d90327057ee956a9c36d03907a4e58cbb07feb27826e8` |
| `Block.getCollisionShape` | 1.8–1.8.9 | `8e36e1a670670d3a60b6f84e3df0c7d0f2837ad510f71751738c91b5190aa34d` |
| `World.getCollisions(Entity,Box)` | 1.8 | `3d41b3558e9d1ebc3315cf773d0459c60e3501d4130df5a104c30b19c2d0db78` |
| `World.getCollisions(Entity,Box)` | 1.8.1 | `bfca68c8955735071a53e9b42d70cd12169c2b6cf2343e33d1f15bcf69e7f123` |
| `World.getCollisions(Entity,Box)` | 1.8.2–1.8.9 | `822ffb91ccf7483043dc315448a908aba0e7961a1a1b4b58b8379573e8457697` |

The 1.8/1.8.1 Lily Pad constructor bodies differ only in decompiler local variable names (`var1`/`var2` vs `f`/`g`); both use the same `0.5F` and `0.015625F` inputs. The 1.8/1.8.1 `Block.addCollisions` source uses a raw `List` and local `var7`; 1.8.2 onward adds a generic type and renames the local to `box`. All have the same null check, shape intersection, and append order. The Lily Pad's own `addCollisions` body is identical across all ten versions.

`World.getCollisions`' source text changes among three method-body hash groups, but each group dispatches the selected block's `addCollisions` with the same swept query and local player entity. The exact dispatch anchors are 1.8 `World.java:902`, 1.8.1 `:903`, and 1.8.2–1.8.9 `:921`. The method bodies were read for 1.8, 1.8.1, 1.8.2, and 1.8.9; hash reuse covers every intervening release in the final group. The registration in each version is `Blocks.LILY_PAD = getOrThrow("waterlily")`.

## Canonical finding and carried review

The canonical finding is `development/research/findings/1.8.9--1.9.4/lily-pad-collision-geometry.md`, SHA-256 `09a8f87642a8d294d8892a9d499c7d2633be871d563ae0662b8b6a71e4d83e79`. I reviewed this canonical text and confirm its 1.8.9→1.9.4 geometry and conditional clipping claim is the same claim as the previously reviewed revision; the clarified feet-position wording does not change the witness. The accepted independent verdict carries forward to this exact finding hash and remains recorded at `.native-source/20261103/189-lilypad-review/verdict.md`, SHA-256 `fb82ea7a412cb080b7a371b69fd30ba7badf52b86b0076360203776d043b22c8`. That earlier verdict separately cites the terminal report SHA-256 `63453fe3fef80a37190d1593e37ec2005da59dd720e12513e0857f53a05e17cd`. This alias addendum extends only the old-geometry applicability evidence and does not claim full-pair closure.


