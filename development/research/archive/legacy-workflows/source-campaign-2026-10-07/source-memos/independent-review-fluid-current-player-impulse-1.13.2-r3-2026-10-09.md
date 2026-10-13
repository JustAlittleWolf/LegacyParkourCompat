# Independent review: 1.13.2 player water-current impulse r3

**Verdict: ACCEPT — bounded source finding only.** This accepts the cutoff-qualified r3 memo's stated source claims against the bound 1.13.2 source set. It does not close the 1.12.2–1.13.2 pair, the MCPK Water and Lava row, runtime validation, or the mapped-JAR provenance issue.

## Immutable candidate identity

- Candidate memo commit: `77417f7abf8deb92c4eb81309a3bd7db8284c58e`
- Memo path: `workflows/source-campaign-2026-10-07/source-memos/fluid-current-player-impulse-1.13.2-r3-2026-10-09.md`
- Memo blob: `a1a658915279baa655e94a571f2549ffe7533502`
- Memo raw SHA-256: `bedcac589fb134ccb57547e3f07c8c36fdc76592ee2a17aacc8a4b12ee0e9bac`
- Binding commit: `d279b9576bf12dd58b132ca2a5a09add40bd4abb`
- Binding path: `workflows/source-campaign-2026-10-07/source-memos/fluid-current-player-impulse-1.13.2-r3-binding-2026-10-09.md`
- Binding blob: `add0416f975496e376a98bb885faf7e3fa561cbf`
- Binding raw SHA-256: `1e5ef1b1fc7cdfe096a00262bc826e91a815db5abc75ca808abee9b5403c58c3`
- Review base: `5dd9db435a2c79cad6be75dd37c8e92ac572ea98`

The candidate and binding commits are ancestors of the review base. Their tree blobs and raw bytes match the binding. The immutable r2 review at `cc8d9585737e4a4d0e5f1af3a1837c0b24bd8544` requested only the cutoff qualification. The r3 memo preserves the original, r2, and prior rejection identities and does not inherit acceptance from them.

## Exact source identity

The ready marker and source manifest are unchanged from the r2 binding: raw SHA-256 `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` and `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`, respectively. Both raw digests match the published files. I rechecked the exact source-file hashes against the source manifest:

| Source file | SHA-256 |
| --- | --- |
| `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| `net/minecraft/fluid/FlowingFluid.java` | `2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253` |
| `net/minecraft/fluid/state/FluidState.java` | `05e2df9377a3bc1e91645ce4282bcaa4edebe93d7ef1907407961baaecff23a6` |
| `net/minecraft/fluid/WaterFluid.java` | `183894d0a4f7a85becd97eb2c0f6cedc1d5adc4648dc6d3d2b5624df173df28b` |
| `net/minecraft/util/math/Vec3d.java` | `6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5` |

## Cutoff and source order verified

- `LivingEntity.mobTick()` applies three separate strict conditions: `Math.abs(velocityX) < 0.003`, `Math.abs(velocityY) < 0.003`, and `Math.abs(velocityZ) < 0.003`; each matching component is set to `0.0` (`LivingEntity.java:1878–1888`). A component exactly at magnitude `0.003` is not cleared by that condition.
- The travel profiler section and `moveRelative(...)` follow later in the same method (`LivingEntity.java:1920–1927`). The r3 wording correctly places the cutoff before travel and does not say every impulse component reaches movement or predict a trajectory.
- Producer-before-consumer order remains supported: `PlayerEntity.tick()` calls `super.tick()` (`PlayerEntity.java:206–208`); `LivingEntity.tick()` calls `super.tick()` and later dispatches `this.mobTick()` (`LivingEntity.java:1689–1691`, `1752`); `PlayerEntity.mobTick()` calls `super.mobTick()` (`PlayerEntity.java:464–481`). Entity base tick calls the water-state path (`Entity.java:393–395`, `947–965`) before the later `mobTick()` travel work.
- The flying gate remains as previously verified: player `hasLiquidCollision()` returns `!abilities.flying` (`PlayerEntity.java:1825–1828`), and the scan skips per-cell flow accumulation when that gate is false (`Entity.java:2521–2546`).

The prior review's supported per-cell flow normalization, shallow-depth scaling, averaging, player aggregate-normalization exception, and `0.014` velocity increment remain bound to the unchanged exact source identities above. The mapped-JAR expected-original-bytes discrepancy remains unresolved and is not used as evidence. No release boundary, full fluid coverage, lava behavior, or runtime/TAS parity is accepted here.

## Resumable next action

The campaign snapshot owner may record an immutable `ACCEPT` event bound to this memo identity and this review commit. That finding-only handoff may then proceed; keep pair discovery open and runtime status unvalidated. Re-review is required if the memo bytes, source publication identity, or finding-specific evidence changes or is challenged.

No ordinary source-pair report, Minecraft Wiki record, mod implementation, decompilation, build, test, or runtime result informed this verdict. No shared source/cache files were changed.