# Auto-jump low-pose full-cube fringe scope — 1.15.2 → 1.16.5

## Verdict: ACCEPT the narrow low-pose exclusion

The remaining `SLEEPING` and `DYING` pose question does not leave a full-cube collector-fringe dependency for `LocalPlayer.updateAutoJump`. Both poses use an exact height of `0.2F` at both endpoints. The query AABB's vertical interval has that same exact span: `vec37.y` equals `vec36.y` because movement/fallback vectors have zero Y, and `updateAutoJump` adds `t = getBbHeight()` to `vec37` before constructing the AABB. In old 1.15.2 `Shapes.create(AABB)`, `findBits` must accept the min/max pair on every axis before it rounds any endpoint. The vertical span `0.2` is not a multiple of any supported grid step (1, 1/2, 1/4, or 1/8); at the finest grid, `0.2 * 8 = 1.6`, at least `0.4` from an integer. The `1.0E-7` endpoint tolerance cannot make it pass. Therefore `findBits` returns -1 for Y, `Shapes.create` uses its exact `ArrayVoxelShape` branch, and the old full-cube join does not erase a tiny vertical query overlap. The 1.16.5 raw-AABB full-block fast path has no old/new difference for these poses.

This closes only the canonical full-block collector optimization dependency inside `LocalPlayer.updateAutoJump`; it does not close unrelated collision providers, state routes, or the whole movement pair. No pose caller gate is needed for this geometric exclusion; the paired `Player` dimension maps and `LivingEntity` sleeping dimensions supply the same fixed 0.2 height at both endpoints. The direct `LocalPlayer.move` override calls `updateAutoJump` in both versions, and `canAutoJump` has no pose-specific branch, so this finding relies on the interval proof rather than assuming these poses are unreachable.

## Source basis

- 1.15.2 `LocalPlayer.java`: `move`/`updateAutoJump` at lines 855–863; `vec32`, zero-Y `vec33`, `vec37`, `t`, and AABB at lines 865–906. SHA-256 `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`.
- 1.16.5 `LocalPlayer.java`: same route at lines 867–875 and 877–918. SHA-256 `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`.
- 1.15.2 `Player.java`: `SLEEPING` uses `SLEEPING_DIMENSIONS`, `DYING` uses `EntityDimensions.fixed(0.2F,0.2F)` at lines 112–120; pose lookup at :2047 onward. SHA-256 `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`.
- 1.16.5 `Player.java`: same dimensions at lines 113–120; pose lookup at :1990–1992. SHA-256 `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`.
- 1.15.2 `LivingEntity.java`: `SLEEPING_DIMENSIONS = fixed(0.2F,0.2F)` at :122; `getDimensions` returns it for SLEEPING at :2770–2771. SHA-256 `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`.
- 1.16.5 `LivingEntity.java`: same at :126 and :2971–2972. SHA-256 `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`.
- 1.15.2 `Shapes.java`: `create(AABB)` requires all three `findBits` results and falls back to `ArrayVoxelShape` if any is negative at lines 55–86; `findBits` checks `i=0..3` with `1.0E-7` tolerance at lines 89–105. SHA-256 `81bf70d2c8a1d1336df72beedb30b0c9504f14decc9a06814a794aabf38203c2`.
- Existing collector report: `collision-collection-residual/report.md`, SHA-256 `3a67f32fdc97d048cd31a1ee735ff5668f486c15d2c44583dc1605cc6bd3eb7c`.

No decompilation, builds, tests, runtime, product, wiki, or backlog changes were made.
