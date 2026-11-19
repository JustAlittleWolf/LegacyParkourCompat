# Early AutoJump east-border candidate applicability

## Result

For the bounded fixture—stationary border, player center strictly within the border-eroded interior, loaded corridor, ordinary stone floor, and an eastward AutoJump sweep—the exact pinned 1.10, 1.10.1, 1.10.2, 1.11, 1.11.1, 1.11.2, and 1.12.2 sources all admit a per-cell synthetic stone collision candidate on the east face. In 1.12.2, the queried outside cell is replaced by `Blocks.STONE.defaultState()` when `getCollisions` is called with `enforceWorldBorder == false` and `isWithinBorder(entity)` is true. A candidate box intersecting either lateral AutoJump segment survives the player's side tests. The one-cell-above collision-shape read sees the fixture's air cap; if candidate rise is `> 0.5F` and `<= 1.2F`, AutoJump sets `ticksToNextAutojump = 1`.

There is no gate requiring the player's full AABB or the entire swept path to remain inside the border. The border applicability predicate checks the entity center against a one-block-expanded or one-block-eroded rectangle depending on `isOutsideWorldBorder`; the stated strictly-eroded-center fixture necessarily passes the inside-state branch. This is a named limit on generalizing the result: **missing AABB/path-containment guard**. It does not block the stated fixture result.

The 1.10 and 1.10.1 source trees have no version-level ready/source-manifest sidecars, but each contains an embedded `.decompile-provenance.json`. Its exact mapping build, raw client, version metadata, and full source inventory are pinned. Each source used in this trace was SHA-256 checked against the embedded `sources` entry. The bounded fixture is therefore source-pinned for every listed checked version from 1.10 through 1.12.2. This is not a claim about uninspected patch boundaries.

## 1.12.2 east-face trace

- `LocalClientPlayerEntity.java:846-859`: `move` records prior X/Z, delegates movement, then calls `autoJump`; AutoJump requires enabled state, cooldown `<= 0`, grounded, not sneaking/riding, nonzero movement input, and forward-facing dot not below `-0.15F`.
- `LocalClientPlayerEntity.java:873-889`: it checks the two blocks above the player's current feet for null collision shapes, sets `o = 1.2F` (plus jump boost), constructs a swept query box with `.grown(width, 0, width)`, offsets both side segments upward by `0.51F`, and requests `world.getCollisions(this, box)`.
- `World.java:964-1014`: the entity collision query is the non-enforcing path. It computes the inside predicate once, seeds a synthetic state with `Blocks.STONE.defaultState()`, requires the sampled X/Z chunk loaded at Y=64, avoids the X/Z scan corners, and for an outside-border block position uses the synthetic state iff `bl2` is true. Thus a loaded east-face cell outside `WorldBorder.contains(pos)` becomes stone. The candidate is passed through ordinary block collision generation.
- `WorldBorder.java:24-26`: a block is outside the east face when its integer X has no overlap with the border's strict max-X comparison (`pos.getX() < getMaxX()`).
- `World.java:1042-1058`: with `isOutsideWorldBorder == false`, `isWithinBorder` tests strict center coordinates against `[minX-1,maxX+1]` and `[minZ-1,maxZ+1]`. There is no entity-box or path containment test here.
- `LocalClientPlayerEntity.java:916-948`: either lateral segment (`vec3d10→vec3d11` or `vec3d12→vec3d13`) must intersect the candidate box. It reads `blockPos2.up(1)` because the loop is `t=1; t<1.2F`; air leaves the synthetic block's top as `s`. The final strict/inclusive tests are `u > 0.5F` and `u <= 1.2F`; passing schedules the next AutoJump.
- `Block.java:52,307-308,372-379`: the default block shape is a unit cube, and `addCollisions` translates it to the candidate block position and appends it only when it intersects the swept query box. The synthetic stone therefore contributes the expected per-cell unit collision box.
- In 1.12.2 the X/Z side scan excludes corners and skips its top cap, but does not skip its bottom cap. For the fixture's wall cell at the player's foot-level, this leaves the candidate eligible. The AutoJump headroom check and candidate-top cap read remain in force.

## Earlier checked source extent

The same exact AutoJump source digest is present in local 1.10, 1.10.1, and 1.10.2 (`a9637065…fed443`); 1.11, 1.11.1, and 1.11.2 share `65c2747b…8f59ed`. Their World collision routines seed a stone fallback and use the border/entity predicate to choose that fallback for exterior block positions. In 1.10.2 and the 1.11 line, border-side scan cells exclude both bottom and top cap Y positions; the fixture's foot-level wall cell remains eligible. In 1.12.2 the side cap filter changes to omit only the upper cap for side cells; this does not remove the fixture's foot-level candidate.

This narrow check does not assess other faces, moving borders, entity modes outside the fixture, non-player physics, aggregate-border implementations, or runtime tick parity.

## Canonical source identities

All paths below are under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather/`. Versions 1.10 and 1.10.1 embed their provenance and `sources` hash map in `.decompile-provenance.json`; later versions use version-level source manifests. Listed source hashes were checked against the applicable manifest/map and current file bytes.

| Version | Source/provenance SHA-256 | AutoJump source SHA-256 | `World.java` SHA-256 | Exact mapping artifacts |
|---|---|---|---|---|
| 1.10 | embedded provenance `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `827fea19d95ddc0a2847859f0c288bf0b6d6dc3692feb30b52b84c60fa19d877` | Feather Gen2 build.2 mergedv2 jar `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd` |
| 1.10.1 | embedded provenance `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `41fd1edc24b39a5b9a979d15d54fbd3ebf9818651628109f232fcc24c7e28d0a` | Feather Gen2 build.2 mergedv2 jar `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37` |
| 1.10.2 | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` | Feather Gen2 build.2 jar `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0`; Tiny `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1` |
| 1.11 | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` | Feather Gen2 build.2 jar `658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44`; Tiny `46bd6702a0983ad667109aadad90b2e6390ed5cd5999561a8a904956f4baf9c0` |
| 1.11.1 | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | Feather Gen2 build.2 jar `83c2f49749b8eff663bf67664204e9cf02c75174e264d4cd57a6ba9f01bd2928`; Tiny `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6` |
| 1.11.2 | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | Feather Gen2 build.2 jar `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757`; Tiny `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6` |
| 1.12.2 | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` | Feather Gen2 build.2 jar `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca`; Tiny `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58` |

For 1.10 and 1.10.1, the mapping artifact, raw client and exact version metadata pins are embedded in `.decompile-provenance.json`; the provenance-file SHA is shown above. The six relied-on source entries in each embedded `sources` map were all confirmed byte-for-byte against current source files. Their exact hashes and upstream input pins follow. For 1.10.2--1.12.2, mapping artifact digests come from each version's `artifacts.sha256`; source-manifest digests are also recorded in `ornithe-feather.ready.json`.



### Embedded 1.10 / 1.10.1 pin audit

These hashes are from `decompiled_minecraft/<version>/ornithe-feather/.decompile-provenance.json`. For each listed relied-on Java file, the current local SHA-256 equals the corresponding embedded `sources[relativePath]` value.

| Relative source path | 1.10 SHA-256 | 1.10.1 SHA-256 |
|---|---|---|
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` |
| `net/minecraft/world/World.java` | `827fea19d95ddc0a2847859f0c288bf0b6d6dc3692feb30b52b84c60fa19d877` | `41fd1edc24b39a5b9a979d15d54fbd3ebf9818651628109f232fcc24c7e28d0a` |
| `net/minecraft/world/border/WorldBorder.java` | `58904ff4e4905f2c3fff8b2a7f40dd0e0689110497ec49e39eb8c9b1ebdd2257` | `58904ff4e4905f2c3fff8b2a7f40dd0e0689110497ec49e39eb8c9b1ebdd2257` |
| `net/minecraft/block/Block.java` | `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01` | `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01` |
| `net/minecraft/block/Blocks.java` | `40ed1ada6eb5a36221b3b576a7eec21824911c92131d54d4f620615e1e71d1c9` | `40ed1ada6eb5a36221b3b576a7eec21824911c92131d54d4f620615e1e71d1c9` |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` |

| Input pin | 1.10 | 1.10.1 |
|---|---|---|
| Feather metadata URL digest | `a1eb1ccff25e2b76fa1d08e9b515508e97ac4837fbcacbd7f77b3a01026d3950` | `8648e97ee398b11151cb2d441161629abde561afed9c933c18fbc622e2c9904a` |
| Raw client SHA-256 | `c00f8170ebf4fd834f552246743a64885a6dffb3dd1e3a3f3dba9676b7a0af35` | `338b205f20c22b9c676d9d3227a5e6c5e190f30ad84d7caad035150b3e05ba72` |
| Exact version metadata SHA-256 | `462441ee38c35c6ad52ffe2ab668b217eb2b29b51de25f1774a454560bc3a261` | `9f61acf31fe84987954b2bdb9f839b57047d5938a8ed4f673f380a1d74ad041f` |

Feather mapping artifacts are `feather-gen2-1.10+build.2-mergedv2.jar` (SHA-256 `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd`) and `feather-gen2-1.10.1+build.2-mergedv2.jar` (SHA-256 `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37`). The exact release IDs, mapping artifact names, immutable input digests, source inventory and decompiler pipeline are bound by the embedded provenance file hashes in the canonical source identity table above.
