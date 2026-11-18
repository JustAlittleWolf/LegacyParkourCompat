# Independent negative-boundary audit

## Verdict

The supplemental cutoff claim is supported. In 1.7.10, `World.containsLiquid(Box)` includes the special `if (bounds.minX < 0.0) { var2--; }` adjustment (as well as corresponding negative-Y/Z adjustments). In the first stable 1.8 release and every inspected 1.8 patch release (1.8.1–1.8.9), the method uses inclusive loops from floored min through floored max and has no negative-minimum decrement. Thus the claimed stable source boundary is 1.7.10 vs 1.8, with no evidence here for an exact development-commit cutoff.

The accepted conditional player path remains reachable in 1.8: the local client player inherits `LivingEntity`; its tick reaches `PlayerEntity.tick` and `LivingEntity.tick`; `LivingEntity.tick` calls `moveRelative`; the local player's `isLocallyControlled()` is true; in the non-flying water branch a horizontal collision calls `canMove`; `Entity.canMove` reaches `doesNotCollide`, which calls `World.containsLiquid` after the collision-list predicate. This confirms method reachability under the stated gates, not a continuous trajectory for the stipulated world snapshot.

## Frozen inputs and integrity

- Accepted finding SHA-256: `13b73e56971cf79a47afe2476a8a39f90a42952512ee8771b18c15f0c4605bd0`.
- Supplemental draft SHA-256: `3174f80222ce8dbecf2928a6f1f38acca62d310347358552b9d9b7426c58f716`; its claimed source roster was committed before opening the draft at `a6b7ea8d0a29f56f3b043ad8567f7756b0018169` (`source-roster.md`).
- Each inspected mapping input is the pinned Ornithe Feather `build.2` merged mapping artifact. The provenance for 1.7.10 and 1.8.0–1.8.8 directly names the version-specific `feather-gen2-<version>+build.2-mergedv2.jar`; 1.8.9's exact provenance and ready markers name `feather-gen2-1.8.9+build.2-mergedv2.jar` and its Tiny file.
- For every inspected version, SHA-256 values of `World.java`, `Entity.java`, `LivingEntity.java`, `PlayerEntity.java`, `ClientPlayerEntity.java`, and `LocalClientPlayerEntity.java` match that version's pinned source manifest. 1.8.9 stores its provenance, ready marker, and SHA-256 source list beside (not inside) its `ornithe-feather` source directory; the ready marker's source-list digest is `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`.

## Exact scan coverage

| Version | `World.containsLiquid(Box)` | Source result | `World.java` SHA-256 |
| --- | ---: | --- | --- |
| 1.7.10 | 1517 | negative-minimum decrement present | `2d648edfe7fdc953825a1df94709e889ea21354494d85762bca49b4a109d01f9` |
| 1.8 | 1417 | no decrement; inclusive floored bounds | `afbc701ef8b66020bdbb2831fd80683705d20237c68fbf23a4001c020876fc6d` |
| 1.8.1 | 1420 | no decrement; inclusive floored bounds | `f0873207c5c7a793189597470392fa0184513b3c5a682a6f8795e1c9ffde9f71` |
| 1.8.2 | 1438 | no decrement; inclusive floored bounds | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` |
| 1.8.3 | 1438 | no decrement; inclusive floored bounds | same as 1.8.2 |
| 1.8.4 | 1438 | no decrement; inclusive floored bounds | same as 1.8.2 |
| 1.8.5 | 1438 | no decrement; inclusive floored bounds | same as 1.8.2 |
| 1.8.6 | 1438 | no decrement; inclusive floored bounds | same as 1.8.2 |
| 1.8.7 | 1438 | no decrement; inclusive floored bounds | same as 1.8.2 |
| 1.8.8 | 1438 | no decrement; inclusive floored bounds | same as 1.8.2 |
| 1.8.9 | 1438 | no decrement; inclusive floored bounds | same as 1.8.2 |

1.8.2–1.8.9 `World.java` files have identical whole-file SHA-256 and therefore identical method bodies. 1.8.1 is a distinct file and was separately inspected; its method has the same floored inclusive bounds without the decrement. This covers every 1.8 patch member rather than inferring continuity from endpoints.

## 1.8 player call gate

Exact 1.8 source references: `LocalClientPlayerEntity` extends `ClientPlayerEntity` and returns true from `isLocallyControlled()` (`client/entity/living/player/LocalClientPlayerEntity.java:59, 389`); `ClientPlayerEntity` extends `PlayerEntity` (`client/entity/living/player/ClientPlayerEntity.java:20`); `PlayerEntity` extends `LivingEntity` (`entity/living/player/PlayerEntity.java:81`) and its tick calls `super.tick()` (line 230); `LivingEntity.tick` calls `moveRelative` (line 1446). In `LivingEntity.moveRelative`, the water branch is taken when `isInWater()` and the player is not flying (lines 1115–1123); the collision gate calls `canMove` (line 1219). `Entity.baseTick` calls `checkWaterCollisions` (line 299), which updates water state by `World.applyLiquidDrag` (lines 745–758). `Entity.canMove` calls `doesNotCollide`, whose expression includes `!this.world.containsLiquid(box)` (lines 361–367). The inclusive `containsLiquid` method therefore remains on a reachable player branch in the first stable 1.8 release.

## Limits

Source/static review only. No trajectory assertion, runtime parity assertion, gameplay, test, build, implementation-source review, decompilation, or push was performed. No discrepancy found between the supplemental draft's stable-cutoff claim and the exact source evidence reviewed.
