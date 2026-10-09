# 1.13.2 water-current player velocity impulse — cutoff-qualified source memo

**Status:** corrected source-only candidate; fresh independent review requested. This memo does not inherit acceptance from r2; the r2 review requested this single qualification.

## Snapshot and review lineage

This is a separate corrected snapshot. The original memo remains unchanged at commit `aac1ea3010da3de7956130b2a008e3f8af755708`, blob `4a347380378ea1acebce15f56c3c9ab8f7f22384`, raw SHA-256 `9138b0314678d5c116b90a23006777e62397139f0b9c0bb01487c663f6743e4d`. The r2 memo remains unchanged at commit `3add7ee470c64a3b5d05ee1ce90792b72053f9cf`, blob `ff8bbbf238922b60f3340dbf5c1643a4070a9811`, raw SHA-256 `0160291faba21a1863871ccafe525af98bf46296ae7403e3ea4c4e67c182cce`.

The immutable r2 independent review is at commit `cc8d9585737e4a4d0e5f1af3a1837c0b24bd8544`, path `workflows/source-campaign-2026-10-07/source-memos/independent-review-fluid-current-player-impulse-1.13.2-r2-2026-10-09.md`, blob `2c5792a30a091bdcba10efb2fb06e6ca60c4ff91`, raw SHA-256 `444b178424c85ba26fde0fb5c92c63f8e500cb03d92cb5c8ae0e6914f05c9f77`. It returned REQUEST CHANGES solely because the prior same-tick conclusion omitted the intervening velocity cutoff. This r3 preserves the prior snapshots and rejection unchanged; it adds that qualification and makes no new claim about the supported flow math.

## Scope

This memo isolates the direct player-velocity producer in MCPK's open **Water and Lava** row (article 3122), for the exact 1.13.2 Ornithe Feather source set. It covers the flow vector supplied per qualifying water cell, the accumulated player vector, the 0.014 velocity increment, and its source call order relative to same-tick player travel, including the intervening component cutoff.

It does not close complete fluid overlap geometry, all fluid-height boundaries, all neighboring flow eligibility, lava behavior, or historical release transitions. It makes no runtime trajectory claim.

## Source identity

Ready source root: `build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather`.

- Ready-marker raw SHA-256: `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`.
- Source-manifest raw SHA-256: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

The inherited flow evidence uses the exact files and hashes previously bound and independently checked in r2:

| Source file | SHA-256 |
| --- | --- |
| `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| `net/minecraft/fluid/FlowingFluid.java` | `2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253` |
| `net/minecraft/fluid/state/FluidState.java` | `05e2df9377a3bc1e91645ce4282bcaa4edebe93d7ef1907407961baaecff23a6` |
| `net/minecraft/fluid/WaterFluid.java` | `183894d0a4f7a85becd97eb2c0f6cedc1d5adc4648dc6d3d2b5624df173df28b` |
| `net/minecraft/util/math/Vec3d.java` | `6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5` |

The precise cutoff/order check uses the manifest-matched `LivingEntity.java` above. Its `mobTick()` body independently clears `velocityX`, `velocityY`, or `velocityZ` when `Math.abs(component) < 0.003` (`LivingEntity.java:1878–1888`). The travel section follows at lines 1920–1927. The comparison is strict: a component whose absolute value is exactly `0.003` is not cleared by these conditions. The source manifest entries for `Entity.java` and `LivingEntity.java` match their file bytes. The mapped-JAR identity discrepancy remains open; this memo uses no mapped-JAR bytecode claim.

## Flow vector and player impulse

`Entity.checkWaterState()` calls `m_69693160(FluidTags.WATER)` (`Entity.java:961–965, 2509–2569`). In the scanner, the entity box is contracted by `0.001`; qualifying tagged fluid cells whose surface reaches the contracted lower Y update the water-presence flag and the running maximum depth `d`. When `hasLiquidCollision()` is true, each such cell supplies `fluidState.getFlow(world,pos)`. The vector is scaled by the current `d` when `d < 0.4`, added to the sum, and counted. A positive-length sum is divided by the cell count; the aggregate is normalized only for non-players. The method then adds `0.014` times the resulting vector to each of `velocityX`, `velocityY`, and `velocityZ` (`Entity.java:2521–2566`).

`FluidState.getFlow(world,pos)` delegates to `this.getFluid().getFlow(world,pos,this)` (`FluidState.java:67–68`). Water uses the inherited `FlowingFluid.getFlow` implementation (`WaterFluid.java`; `FlowingFluid.java:54–98`): horizontal neighbor height differences form the base vector; for a falling state next to a solid face, the horizontal vector is normalized, `(0,-6,0)` is added, and the result is normalized. Both calls use the exact `Vec3d.normalize()` implementation (`Vec3d.java:25–28`): compute Euclidean length, return `ZERO` if the length is below `1.0E-4`, otherwise divide each component by that length.

This means each cell's flow vector is normalized by `FlowingFluid` before the entity scanner's shallow-depth scaling and averaging. For a player, the averaged sum is not normalized a second time; its magnitude therefore retains the effects of averaging and any `d < 0.4` scale. Non-player entities do receive that final aggregate normalization.

## Flying-player gate

`PlayerEntity.hasLiquidCollision()` returns `!abilities.flying` (`PlayerEntity.java:1826–1828`). The scanner computes this gate before scanning. A qualifying fluid cell still updates water presence and `d` when the player is flying, but the false gate skips every per-cell flow addition and count increment (`Entity.java:2522–2544`). The accumulated vector stays `Vec3d.ZERO`, so the later positive-length check fails and this invocation adds no water-current velocity delta. This statement is limited to the current impulse in `m_69693160`; it does not describe other code that may update velocity.

## Same-tick order and cutoff

The exact source call order places the water-current producer before player travel in the same tick:

1. `PlayerEntity.tick()` calls `super.tick()` (`PlayerEntity.java:176–208`).
2. `LivingEntity.tick()` calls `super.tick()` first (`LivingEntity.java:1689–1690`). This reaches `Entity.tick()` and `baseTick()` (`Entity.java:332–340`); base tick calls the water-state path, which runs the impulse method before returning (`Entity.java:947–965, 2509–2566`).
3. After that superclass call returns, `LivingEntity.tick()` calls `this.mobTick()` (`LivingEntity.java:1752`). Dynamic dispatch reaches `PlayerEntity.mobTick()`, which calls `super.mobTick()` (`PlayerEntity.java:464–481`).
4. In `LivingEntity.mobTick()`, each velocity component with absolute value strictly below `0.003` is set to zero (`LivingEntity.java:1878–1888`). The travel section follows (`LivingEntity.java:1920–1927`); the applicable movement branch passes velocity components to `move(MoverType.SELF, velocityX, velocityY, velocityZ)` (`LivingEntity.java:1478–1655`, including the water branch at 1639–1640).

Therefore, the source proves that the current impulse is applied before same-tick travel, and that its resulting velocity components are subject to an intervening per-component `0.003` cutoff. Any component below that strict magnitude threshold at the cutoff is cleared before the later travel section; this memo does not claim that every component of the impulse reaches movement or that a particular trajectory results. The conclusion is limited to source call order and the cutoff conditions.

## Finding boundary

- Source-supported: per-cell flow delegation and normalization; `Vec3d.normalize()` threshold and arithmetic; shallow-depth scale; vector averaging; player aggregate-normalization exception; flying gate behavior; `0.014` velocity delta; producer-before-travel call order; and the intervening component-wise `< 0.003` velocity cutoff.
- Still open: complete cell-scan and surface-height boundary coverage; all neighboring flow eligibility and solid-face edge cases; corresponding lava behavior; exact historical release deltas; independent review of r3; and runtime/TAS parity.
- Artifact limitation: mapped-JAR bytecode corroboration remains excluded until the sole preparation owner reconciles the manifest mismatch.