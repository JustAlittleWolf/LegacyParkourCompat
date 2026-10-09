# Independent review: 1.13.2 player water-current impulse r2

**Verdict:** REQUEST CHANGES
**Review scope:** source-only verification of the bound r2 memo. This review does not close the 1.12.2–1.13.2 pair or the MCPK Water and Lava row.

## Immutable candidate identity

- Candidate commit: `3add7ee470c64a3b5d05ee1ce90792b72053f9cf`
- Candidate path: `workflows/source-campaign-2026-10-07/source-memos/fluid-current-player-impulse-1.13.2-r2-2026-10-09.md`
- Git blob: `ff8bbbf238922b60f3340dbf5c1643a4070a9811`
- Raw file SHA-256: `0160291faba21a1863871ccafe525af98bf46296ae7403e3ea4c4e67c182cce`
- Review base: `5ede13dafa339e9c6c482c63ff711b19ac6e74e9`
- Binding path: `workflows/source-campaign-2026-10-07/source-memos/fluid-current-player-impulse-1.13.2-r2-binding-2026-10-09.md`
- Binding blob at review base: `af0b5ebdbf1e97320f4023ab67a7e01ec1d6d85e`
- Binding raw SHA-256: `0a8c1c4308fb137476dd51cbf41199896c82e156d7992599e56a52628ffda4c5`

The candidate commit is an ancestor of the review base, and the candidate blob matches the binding. The original r1 memo, prior review/rejection and stated provenance caveats remain untouched. The prior review is not acceptance of r2.

## Source identity verified

Exact source root: `build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather`.

- Ready marker raw SHA-256: `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`
- Source manifest raw SHA-256: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`
- `Entity.java`: `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`
- `LivingEntity.java`: `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`
- `PlayerEntity.java`: `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`
- `FlowingFluid.java`: `2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253`
- `FluidState.java`: `05e2df9377a3bc1e91645ce4282bcaa4edebe93d7ef1907407961baaecff23a6`
- `WaterFluid.java`: `183894d0a4f7a85becd97eb2c0f6cedc1d5adc4648dc6d3d2b5624df173df28b`
- `Vec3d.java`: `6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5`

Each listed source-file digest matches its entry in the bound source manifest. Source evidence is limited to this published 1.13.2 source set. The mapped-JAR expected-original-bytes discrepancy remains open; no mapped-JAR bytecode claim is used.

## Verified claims

- `Entity.baseTick()` calls `m_03231680()` before returning (`Entity.java:393–395`); that method calls `checkWaterState()` (`Entity.java:947–950`), which reaches the water scan (`Entity.java:961–965`).
- The scan computes `hasLiquidCollision()` once before scanning (`Entity.java:2521–2524`). `PlayerEntity.hasLiquidCollision()` returns `!abilities.flying` (`PlayerEntity.java:1825–1828`). A qualifying fluid cell still updates the water flag and maximum depth, while the false gate skips flow accumulation/counting (`Entity.java:2532–2546`). For that invocation the sum remains zero, so the positive-length guard prevents an impulse (`Entity.java:2554–2567`).
- Each qualifying cell contributes `FluidState.getFlow(...)`, scaled by the current maximum depth when that depth is below `0.4`, before summation (`Entity.java:2539–2545`). The positive sum is averaged by cell count; aggregate normalization is skipped for `PlayerEntity` (`Entity.java:2554–2561`). Each component receives `0.014 * component` (`Entity.java:2563–2566`).
- `FluidState.getFlow` delegates to the fluid implementation (`FluidState.java:67–69`). `WaterFluid` extends `FlowingFluid` and has no `getFlow` override in the bound class. `FlowingFluid.getFlow` builds horizontal height-difference flow; in the falling/solid-face case it normalizes before adding `(0,-6,0)`, then the method normalizes its return (`FlowingFluid.java:54–98`). `Vec3d.normalize()` computes Euclidean length, returns `ZERO` below `1.0E-4`, and otherwise divides each component by that length (`Vec3d.java:25–28`). The memo correctly distinguishes per-cell normalization from the player's skipped aggregate normalization.
- Player tick order reaches the producer before inherited movement: `PlayerEntity.tick()` calls `super.tick()` (`PlayerEntity.java:176–208`); `LivingEntity.tick()` calls `super.tick()` and later `this.mobTick()` (`LivingEntity.java:1689–1691`, `1752`); `PlayerEntity.mobTick()` calls `super.mobTick()` (`PlayerEntity.java:464–481`). `LivingEntity.mobTick()` reaches the travel section and calls `moveRelative(...)` (`LivingEntity.java:1920–1927`); the water movement branch passes velocity components to `move(MoverType.SELF, ...)` (`LivingEntity.java:1639–1643`). The source call order is confirmed.

## Required correction

The memo's final same-tick conclusion says the impulse “changes the velocity state read by the inherited movement operation later in that same tick.” That is too broad as written. After the producer and before travel, `LivingEntity.mobTick()` sets each velocity component to zero when its absolute value is below `0.003` (`LivingEntity.java:1878–1888`). The later travel section follows at lines 1920–1927. Therefore the call order is established, but a small component of the added impulse may be cleared before movement reads it. Please qualify the conclusion to say the impulse is applied before same-tick travel, with components subject to the intervening `0.003` cutoff (and do not claim an unconditional velocity change reaches movement).

This is the sole change requested. The flying-player gate, averaging/normalization order, shallow-depth scaling, `0.014` arithmetic and same-tick source call order are otherwise supported by the bound sources. The limitation is a source-order/cutoff qualification only; this review makes no trajectory, runtime-parity, mapped-JAR, or release-boundary claim.

## Review boundary and disposition

No normal source-pair report, Minecraft Wiki record, implementation, decompilation, build, test or runtime result informed this review. The MCPK reference only identifies the candidate's Water and Lava lane. The exact source-safe workflow was read from the review-base worktree; the separately specified `C:/Users/Wolfi/.codex/worktrees/c96b/LegacyParkourCompat` checkout was unavailable. No shared source/cache files were changed.
