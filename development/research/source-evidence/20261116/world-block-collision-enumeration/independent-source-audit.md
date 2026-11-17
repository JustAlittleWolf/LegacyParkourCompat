# Independent source audit: world block-collision enumeration

Verdict: ACCEPT.

This verdict applies to finding SHA-256 `10ae291d7278204561087f224e7777fbfeef68f57862a15f511c63e8c42537d2` and Git blob `f40ce83fd4225eb6ed9b44db360ef045f71555e3` only.

## Reviewed source identities

- `decompiled_minecraft/1.13.2/ornithe-feather`: Ornithe Feather Gen2 build 2. Ready marker records source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; exact source mapping artifacts are `feather-gen2-1.13.2+build.2-mergedv2.jar` and `feather-gen2-1.13.2+build.2.tiny`.
- `decompiled_minecraft/1.14/yarn`: Yarn build 21, authenticated by `.decompile-provenance.json`: mapping input `yarn-1.14+build.21-v2.jar`, SHA-256 `0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64`; client input SHA-256 `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a`. Recorded source hashes for cited `ViewableWorld.java`, `VoxelShapes.java`, `Entity.java`, and `ClientPlayerEntity.java` matched the files (`1a21a52f…`, `73089de0…`, `f52483d5…`, `92d0f97a…`).
- `decompiled_minecraft/1.14.1/yarn`: Yarn build 10, in-tree provenance mapping input SHA-256 `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`.
- `decompiled_minecraft/1.14.2/yarn`: Yarn build 7, in-tree provenance mapping input SHA-256 `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b`.
- `decompiled_minecraft/1.14.3/yarn`: Yarn build 13, in-tree provenance mapping input SHA-256 `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`.
- `decompiled_minecraft/1.14.4/ornithe-feather`: Ornithe Feather Gen2 build 2. Ready marker records source-manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`; source mapping artifacts are `feather-gen2-1.14.4+build.2-mergedv2.jar` and `feather-gen2-1.14.4+build.2.tiny`.

For 1.14.1–1.14.3, the in-tree provenance records mapping, client, and cited source-file hashes; these matched the checked source files. The 1.14.1 and 1.14.2 `ViewableWorld.java` and `VoxelShapes.java` hashes are identical.

## Accepted boundary and scope

The world-query enumeration changes from the old `floor(min) - 1` / `ceil(max) + 1` half-open traversal in 1.14 to epsilon-adjusted bounds with inclusive endpoints in 1.14.1. The 1.14 movement clipping helper already uses epsilon-expanded bounds and a direction-dependent interval; the 1.14.1 query change reaches local-player auto-jump, while 1.14.3+ also uses the world block query for multi-axis clipping. The finding keeps candidate query bounds separate from the queried entity's own box and describes the border exterior probe separately. The accepted scope is source behavior and reachable player paths; the enumeration formulas alone do not establish a trajectory difference.
