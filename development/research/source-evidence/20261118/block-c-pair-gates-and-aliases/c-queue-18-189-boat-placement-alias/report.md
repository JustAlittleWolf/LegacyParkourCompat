# Boat rider placement alias audit: 1.8 to 1.8.9

**Terminal: COMPLETED.** The placement operation is safe to model as an early versioned position-only delta for 1.8, with the first corrected constant evidenced in 1.8.1. The 1.8.1–1.8.9 placement method is otherwise identical. This scoped result does not cover boat motion, steering, collision, damage, or other passengers.

## Exact source comparison

All source paths are under `decompiled_minecraft/<version>/ornithe-feather/`.

- `net/minecraft/entity/vehicle/BoatEntity.java`, `updateRiderPositon()` is at line 365 in 1.8 and line 366 in 1.8.1–1.8.9. In each release it gates on `this.rider != null`, computes `cos(yaw * PI / 180.0) * 0.4` and `sin(yaw * PI / 180.0) * 0.4`, then passes `boat.x + dx`, `boat.y + getMountHeight() + rider.getRideHeight()`, `boat.z + dz` to `rider.setPosition(double,double,double)`. No yaw, head-yaw, body-yaw, or old-angle field is written by this callback.
- The sole difference for this operation is `BoatEntity.getMountHeight()` (line 79): 1.8 returns `this.height * 0.0 - 0.3F`; 1.8.1 through 1.8.9 return `-0.3` (double literal). In 1.8 the `0.0` multiplication is double arithmetic and subtracting the float literal promotes the float-rounded `-0.3F` value (`-0.30000001192092896`) to double. From 1.8.1, the value is double `-0.3`.
- `PlayerEntity.getRideHeight()` returns double `-0.35` at both endpoints. `Entity.rideTick()` calls `tick()`, then if `vehicle != null` invokes `vehicle.updateRiderPositon()`, followed by yaw/pitch-delta bookkeeping; it does not itself write rider yaw in that segment. These relevant method bodies and `setPosition(double,double,double)` signatures are unchanged at the endpoints.

## Reachable default-player witness

For a mounted ordinary player who remains mounted through the player tick, with boat `(10.0, 20.0, 30.0)` and yaw `0.0F`, the callback places X/Z at `(10.4, 30.0)` in both versions. In 1.8, the ordered Y expression evaluates to `19.34999998807907`; from 1.8.1 it evaluates to `19.35`. Thus the only confirmed endpoint behavior difference in this bounded callback is the float-versus-double mount-height constant, about `1.1920929e-8` in rider Y for this witness. The callback has no yaw write in either version; the historical no-yaw-write contract is identical across the pair.

## Patch boundary / scope disposition

The source pin establishes the constant change between 1.8 and 1.8.1: do not extend the 1.8.1 double-literal behavior back to 1.8. The entire placement method body, ordinary-player ride height, and caller order otherwise remain the same across this range. A position-only historical boat rider placement change is therefore safe for a 1.8 profile and later profiles can use native behavior for this callback; it should preserve the 1.8 float-rounded mount-height evaluation. If the proposed feature meant only horizontal X/Z offset or the no-yaw-write rule, there is no 1.8→1.8.9 difference to emulate.

## Evidence pins

The approved prior audit verdict was verified before use: `early-boat-rider-transform-audit/verdict.md`, SHA-256 `b10c9857e6689e8bc95ea4f255911bcc317f175611c13dd688d3aae2260484fd`. It describes the later 1.8.9→1.9 change and is used here only as a source-only scoped dependency; it does not establish this earlier interval.

Verified canonical `BoatEntity.java` SHA-256 pins:

| Release | SHA-256 |
| --- | --- |
| 1.8 | `d6f9f8ac97f5f337db499f0fe0b34a6e7b154b9fa79db270ef687ae0b412ec55` |
| 1.8.1 | `5bafd066ccccdb2931fb26f35f32d80006d9c2e174680beb5e2586dcc64ea366` |
| 1.8.2 | `77ef72c28024b754ff76f2c67e14dd61832394bac2bd7b62b29708560e588da2` |
| 1.8.3 | Same as 1.8.2 |
| 1.8.4 | Same as 1.8.2 |
| 1.8.5 | Same as 1.8.2 |
| 1.8.6 | Same as 1.8.2 |
| 1.8.7 | Same as 1.8.2 |
| 1.8.8 | Same as 1.8.2 |
| 1.8.9 | Same as 1.8.2 |

The identical 1.8.2–1.8.9 file hashes directly pin the exact method text across those aliases. The 1.8.1 method text was inspected and matches the operation expression; its only relevant `BoatEntity` body change is the corrected `getMountHeight()` literal. The canonical tree contains no per-release provenance sidecars for 1.8–1.8.8; the available 1.8.9 `ornithe-feather.provenance.json` names version 1.8.9, namespace `ornithe-feather`, and Feather mapping artifacts `feather-gen2-1.8.9+build.2-mergedv2.jar` and `feather-gen2-1.8.9+build.2.tiny` (sidecar SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`). Therefore the exact source body comparison is pinned, but source publication/mapping provenance for 1.8–1.8.8 is not present locally and remains a provenance limitation. No code, tests, launch, or generation was performed.