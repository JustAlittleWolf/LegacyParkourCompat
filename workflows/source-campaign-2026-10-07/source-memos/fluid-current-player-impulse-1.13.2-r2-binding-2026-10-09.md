# Independent-review binding: 1.13.2 player water-current impulse r2

**Candidate:** corrected source-only MCPK memo. **Review status:** pending a fresh independent review; no acceptance is implied.

## Immutable candidate identity

- Memo: fluid-current-player-impulse-1.13.2-r2-2026-10-09.md
- Commit: 3add7ee470c64a3b5d05ee1ce90792b72053f9cf
- Git blob: ff8bbbf238922b60f3340dbf5c1643a4070a9811
- Raw file SHA-256: 0160291faba21a1863871ccafe525af98bf46296ae7403e3ea4c4e67c182cce

The predecessor remains unchanged: commit aac1ea3010da3de7956130b2a008e3f8af755708, blob 4a347380378ea1acebce15f56c3c9ab8f7f22384, raw SHA-256 9138b0314678d5c116b90a23006777e62397139f0b9c0bb01487c663f6743e4d. Prior review reference 9bc84d4e identified prohibited-lane exposure; it is retained as a rejection/non-clean disposition and is not an acceptance of this successor.

## Bound source identity

The candidate is bound to build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather:

- Ready marker raw SHA-256: 1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38.
- Source manifest raw SHA-256: 2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211.
- Entity.java: 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269.
- LivingEntity.java: bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c.
- PlayerEntity.java: 4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633.
- FlowingFluid.java: 2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253.
- FluidState.java: 05e2df9377a3bc1e91645ce4282bcaa4edebe93d7ef1907407961baaecff23a6.
- WaterFluid.java: 183894d0a4f7a85becd97eb2c0f6cedc1d5adc4648dc6d3d2b5624df173df28b.
- Vec3d.java (normalization implementation): 6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5.

Each listed source file was checked against the bound source manifest. The separate artifact manifest raw SHA-256 is fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e; it expects mapped-JAR SHA-256 d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5, while the current artifact path hashes to b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c. The mapped JAR is excluded from this candidate's corroboration; the source hashes remain the evidence binding.

## Review boundary

Review this r2 memo and its bound exact-source claims only. In particular, assess the corrected flying-player gate statement, the Vec3d.normalize() source binding and threshold, the distinction between per-cell and aggregate normalization, and the same-tick player call order. Keep the prior review record and r1 snapshot immutable. Do not treat this narrow water-current candidate as resolving full water geometry/flow coverage, other fluid behavior, a release boundary, runtime parity, or the mapped-JAR discrepancy.

The separate Y=256 cutover memos and their independently accepted bounded reviews remain unchanged. Their runtime-trajectory and first-protected-release limitations remain in force and are outside this candidate's acceptance scope.
