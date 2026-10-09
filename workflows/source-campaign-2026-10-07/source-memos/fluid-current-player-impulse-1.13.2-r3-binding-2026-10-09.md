# Independent-review binding: 1.13.2 player water-current impulse r3

**Candidate:** cutoff-qualified source-only MCPK memo. **Review status:** pending a fresh independent review; no acceptance is implied.

## Immutable candidate identity

- Memo path: `workflows/source-campaign-2026-10-07/source-memos/fluid-current-player-impulse-1.13.2-r3-2026-10-09.md`
- Memo commit: `77417f7abf8deb92c4eb81309a3bd7db8284c58e`
- Git blob: `a1a658915279baa655e94a571f2549ffe7533502`
- Raw file SHA-256: `bedcac589fb134ccb57547e3f07c8c36fdc76592ee2a17aacc8a4b12ee0e9bac`

The r3 memo is a separate snapshot. The original memo remains at commit `aac1ea3010da3de7956130b2a008e3f8af755708`, blob `4a347380378ea1acebce15f56c3c9ab8f7f22384`, raw SHA-256 `9138b0314678d5c116b90a23006777e62397139f0b9c0bb01487c663f6743e4d`. The r2 memo remains at commit `3add7ee470c64a3b5d05ee1ce90792b72053f9cf`, blob `ff8bbbf238922b60f3340dbf5c1643a4070a9811`, raw SHA-256 `0160291faba21a1863871ccafe525af98bf46296ae7403e3ea4c4e67c182cce`. Its binding at base commit `5ede13dafa339e9c6c482c63ff711b19ac6e74e9` has blob `af0b5ebdbf1e97320f4023ab67a7e01ec1d6d85e` and raw SHA-256 `0a8c1c4308fb137476dd51cbf41199896c82e156d7992599e56a52628ffda4c5`.

The r2 independent review is preserved at commit `cc8d9585737e4a4d0e5f1af3a1837c0b24bd8544`, path `workflows/source-campaign-2026-10-07/source-memos/independent-review-fluid-current-player-impulse-1.13.2-r2-2026-10-09.md`, blob `2c5792a30a091bdcba10efb2fb06e6ca60c4ff91`, raw SHA-256 `444b178424c85ba26fde0fb5c92c63f8e500cb03d92cb5c8ae0e6914f05c9f77`. It requested one correction: qualify the same-tick statement because `LivingEntity.mobTick()` may clear components below `0.003` before travel. The r3 memo preserves the r2 flow math and limits its conclusion to producer-before-travel order with the intervening strict component-wise cutoff. The earlier rejection reference `9bc84d4e` and all prior snapshots remain unchanged; none is treated as acceptance of r3.

## Bound exact source identity

Source root: `build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather`.

- Ready marker `build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather.ready.json`, raw SHA-256: `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`.
- Source manifest `build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather.sources.sha256`, raw SHA-256: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

Exact source-file SHA-256 values, checked against that manifest for the r2 snapshot and rechecked for the cutoff's two files:

| Source file | SHA-256 |
| --- | --- |
| `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| `net/minecraft/fluid/FlowingFluid.java` | `2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253` |
| `net/minecraft/fluid/state/FluidState.java` | `05e2df9377a3bc1e91645ce4282bcaa4edebe93d7ef1907407961baaecff23a6` |
| `net/minecraft/fluid/WaterFluid.java` | `183894d0a4f7a85becd97eb2c0f6cedc1d5adc4648dc6d3d2b5624df173df28b` |
| `net/minecraft/util/math/Vec3d.java` | `6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5` |

The ready marker records version `1.13.2`, mapping `ornithe-feather`, the source-manifest identity, and the artifact-manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`. The separate mapped-JAR expected-original-bytes discrepancy remains unresolved; no mapped-JAR bytecode is used.

## Review boundary

Review the r3 memo and its exact-source claims. In particular, confirm the cutoff order in `LivingEntity.mobTick()` (`LivingEntity.java:1878–1888`) relative to the later travel section (`LivingEntity.java:1920–1927`), that each component is zeroed only when `Math.abs(component) < 0.003`, and that the memo does not claim unconditional delivery of the impulse to movement. Also recheck that the inherited flow math, flying gate, and producer ordering remain bounded by the exact source identities listed above.

This candidate does not close full water geometry/flow coverage, lava behavior, a historical release boundary, runtime/TAS parity, or the mapped-JAR discrepancy. Do not use implementation, wiki information, ordinary source-pair reports, decompilation, builds, tests, or runtime results as evidence for this narrow source review.