# Independent audit: pane center-axis overlap epsilon

**Verdict: ACCEPT**, bounded to the source comparison in the input revision below.

- Input: `.native-source/20261028/collision-dependencies/pane-center-overlap/pane-center-overlap.md`
- Input SHA-256: `08BF4B732E62524C24A945A6E0455A965D5C5487CD9F5D9BBD13082957655925` (recomputed; exact match)

## Independent source check

The float geometry is correct. In all three releases, the player width used here is `0.6F`, and the AABB builder divides the float width by `2.0F` before widening for the double-coordinate box arithmetic. The resulting half-width is `0.30000001192092896`. At `(0.13749, 64.2, 0.13750005)`, the X gap to `0.4375` is `9.98807907104382e-6`, while the Z overlap is `6.192092893986256e-8`; the Y interval intersects `[64,65]`. Positive X displacement `0.003125` crosses the X gap.

- **1.12.2:** `PaneBlock.SHAPES[0]` is the post `[7/16,9/16] × [0,1] × [7/16,9/16]`; default arms are false and `addCollisions` always tries the center post. `Block.addCollision` keeps it because `Box.intersects` uses strict positive overlap. `World.getCollisions` scans the expanded movement box and invokes that collector. `Box.intersectX` uses strict Y/Z overlap gates without an inset, so the post clips positive X to the stated gap. The source paths/ranges in the finding match.
- **1.13.2:** `IronBarsBlock` passes `1.0F` to `PaneBlock`; the generated post bounds are the same. `WorldView.getCollisions` constructs the swept query with a `1e-7` expansion and a shifted-shape subtraction; the pane is included in the bounded witness. The original-box `AND` predicate does not treat the sub-`1e-7` Z overlap as an occupied merged interval. In the later X clipping pass, `VoxelShape.calculateMaxDistance` indexes the cross-axis maximum at `box.maxZ - 1e-7`, below the first occupied Z cells, leaving X at `0.003125`. The input distinguishes the earlier broad query from this clipping scan correctly.
- **26.2:** `EntityTypes.PLAYER` is `.sized(0.6F, 1.8F)`; `EntityDimensions.makeBoundingBox` performs the same float half-width calculation. `IronBarsBlock` uses false connection defaults and passes post width `2.0F`; `CrossCollisionBlock` starts each shape with the post and ORs only enabled arms. `Entity.collideBoundingBox` passes the swept AABB to `BlockCollisions`; enumeration coordinates have a `1e-7` margin, but `entityShape` is created from the uninflated swept AABB. The pane follows the non-full-block branch (`blockShape == Shapes.block()` is false), then must pass `!shape.isEmpty()` and `Shapes.joinIsNotEmpty(..., AND)`. The `IndirectMerger` tolerance coalesces the small Z overlap, producing no positive AND cell, so the pane is not collected. If supplied to clipping, `VoxelShape.collideX` uses `moving.max(cAxis) - 1e-7`, which indexes below the pane's occupied Z interval as described.

The 26.2 fast path is identity-based (`blockShape == Shapes.block()`) and is not taken by the pane. Its regular collector path and `joinIsNotEmpty` identity/empty/disjoint fast paths do not alter this witness: both shapes are nonempty and distinct, and their Z overlap is within the merger epsilon. The pane's waterlogged property is excluded from the cached shape key; with the stated false connections, it does not add shape geometry. No collision-equality decision is needed for this collector result.

## Source identities and integrity

Primary source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft` (all version exports present and read-only). Namespace/version IDs are `1.12.2 / ornithe-feather`, `1.13.2 / ornithe-feather`, and `26.2 / unobfuscated`. In each ready record, the recorded source-manifest SHA-256 matches the manifest file; every targeted Java file checked for this audit matches its entry in that manifest. The finding's targeted source hashes also match the files inspected.

- 1.12.2 provenance SHA-256 `747425A26DA363D1ABC8A2CECD83A74504AEB1F58BE93AA53F48C690E35318CE`; source-manifest SHA-256 `B8A37CCFCCD2AAC5F40F5E34FEC85873DBDFA93A266043C103E592E4A8C949DA`; ready record names Feather build.2 mergedv2 JAR and Tiny artifacts.
- 1.13.2 provenance SHA-256 `E25950B7855FC4F2AEE1A662F0BE6B0AF9F28788D9E27293075CFC5A3AA5D383`; source-manifest SHA-256 `2C8CFB646BF622FB26AC0CA0CB5E02A354FB512E29F8B1010F1AAACD509E1211`; ready record names Feather build.2 mergedv2 JAR and Tiny artifacts.
- 26.2 provenance SHA-256 `A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1`; source-manifest SHA-256 `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894`; ready record identifies unobfuscated source, with no mapping artifacts.

The user's initially specified `D:\.minecraft-decompile-cache` path is absent. The corrected canonical cache root `D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache` exists, but contains no `1.12.2`, `1.13.2`, or `26.2` subdirectory. The provenance-named `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts` directory is absent. The mapping artifact binaries therefore could not be reread; Feather mapping identities are supported by export metadata and manifests, not independently revalidated against JAR/Tiny bytes. This is the remaining evidence limit, not a source behavior disagreement.

## Direct dependencies and limits

Direct dependencies are the float player width and box construction; pane default/false connection state and shape selection; movement-swept block enumeration; collision candidate collection and its fast paths; epsilon-aware coordinate merging; and the X-axis clipper's cross-axis index bounds. The bounded comparison supports only the source-level result for the stated isolated witness. It does not establish runtime execution, a full trajectory, pair coverage, a patch introduction boundary, or an implementation result.
