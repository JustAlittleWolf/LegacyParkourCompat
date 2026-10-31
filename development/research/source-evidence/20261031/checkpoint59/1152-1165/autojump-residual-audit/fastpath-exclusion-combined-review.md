# Combined independent review: auto-jump full-cube collector dependency

## Verdict: ACCEPT — collector dependency closed for all vanilla player poses

This combined review binds:

- Main proof `.native-source/20261030/1152-1165-finish43/autojump-residual-audit/fastpath-exclusion-proof.md`, SHA-256 `9d9a396c672fb2dc8a3102fc57f8e2ba7268b851c1cc7795ded769306bd8d87b`.
- Low-pose supplement `.native-source/20261030/1152-1165-finish43/autojump-pose-scope/report.md`, SHA-256 `f146e2ae8cdd496d455aa0c5d637cfd6df7a150e312314ce3c2906ab2de64357`.
- Underlying collector report `.native-source/20261030/1152-1165-finish43/collision-collection-residual/report.md`, SHA-256 `3a67f32fdc97d048cd31a1ee735ff5668f486c15d2c44583dc1605cc6bd3eb7c`.

Together, these prove the accepted full-block raw-AABB versus `Shapes.create(AABB)` fringe cannot change `LocalPlayer.updateAutoJump`'s tested segment result in the listed ordinary poses, and that sleeping/dying poses do not enter the rounded-grid branch at all. This closes only this one collector dependency for this LocalPlayer caller; it does not close `updateAutoJump`'s other provider/state routes, entity-collision semantics, or the 1.15.2→1.16.5 pair.

## Main proof checks

The broadphase AABB is constructed before the `+0.51F` offsets and lateral segment endpoints in both exact LocalPlayer bodies (1.15.2 `:905–913`; 1.16.5 `:917–925`). The horizontal extent proof correctly includes a conservative `1.01` factor for the approximate normalized vector; with player width `s=0.6`, the minimum query overlap at a tested segment hit is `s - 1.01s/2 = 0.297`. For the covered heights `t>=0.6`, the vertical overlap lower bound `min(t-0.51,0.51,1)` is at least `0.09`.

`Mth.fastInvSqrt(float)` is the same bit-hack seed plus one Newton refinement at old `Mth.java:394–400` and new `:374–380`. For positive finite float inputs greater than `0.001F`, the stated `[0.99,1.01]` conservative relative length bound is valid. I checked the source expression's mantissa behavior over both exponent parity classes and verified the result lies well inside that allowance; the vector component bound and 0.297 margin follow.

The old `Shapes.findBits` accepts resolutions `i=0..3` only when each endpoint is within `<1.0E-7` of a grid point after scaling by `2^i`. Therefore an endpoint-rounding displacement is `<1.0E-7` globally. The covered-pose segment/query margins are much larger, so the collector's canonical full-cube fringe cannot affect either side-segment hit.

## Low-pose supplement check

Both old and new Player pose tables use fixed height `0.2F` for SLEEPING and DYING; SLEEPING also uses width `0.2F` from `LivingEntity.SLEEPING_DIMENSIONS`. The movement vector and fallback vector have zero Y, so the auto-jump broadphase vertical endpoints differ by exactly `getBbHeight() = 0.2F` (within ordinary finite entity coordinate arithmetic). A shared supported `findBits` resolution would require both scaled Y endpoints to be within `<1.0E-7` of integers, making their scaled difference within `<2.0E-7` of an integer. But `0.2F * 1`, `*2`, `*4`, and `*8` are approximately `0.2`, `0.4`, `0.8`, and `1.6`; their distances to the nearest integer are at least `0.2` (the finest grid is `1.6`, distance `0.4`). Thus the Y `findBits` call must return `-1`, `Shapes.create(AABB)` uses its exact `ArrayVoxelShape` fallback, and the old join cannot erase a positive overlap with a full block. The supplement's conclusion is source-supported and does not need a pose caller exclusion assumption.

## Evidence identities

The cited source hashes match their own canonical source manifests. Manifest hashes checked: 1.15.2 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; 1.16.5 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`.

- 1.15.2 `LocalPlayer.java` `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`; 1.16.5 `LocalPlayer.java` `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`.
- 1.15.2 `Mth.java` `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4`; 1.16.5 `Mth.java` `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290`.
- 1.15.2 `Player.java` `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`; 1.16.5 `Player.java` `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`.
- 1.15.2 `LivingEntity.java` `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`; 1.16.5 `LivingEntity.java` `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`.
- 1.15.2 `Shapes.java` `81bf70d2c8a1d1336df72beedb30b0c9504f14decc9a06814a794aabf38203c2`; 1.16.5 `Shapes.java` `ec218dca68cad3525364967aadfbb8c0bead0cc44aa3ee2beba31e00e55ae2d4`.
- 1.16.5 `AABB.java` `514558cf4827679d84a4debd4f400d7d65bd4b8982b7ae8124a0d83a916ba878`; `CollisionGetter.java` `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac`; `CollisionSpliterator.java` `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297`.

No product, wiki, backlog, build, test, decompilation or runtime files were changed or used.
