# 1.13.2 → 1.14 pitch-directed swim vertical adjustment

## Source publication

Compared the published exact decompilations under `decompiled_minecraft/1.13.2/ornithe-feather/` and `decompiled_minecraft/1.14/ornithe-feather/`. The readiness records identify exact IDs `1.13.2` and `1.14` (Minecraft's 1.14.0 release), mapping `ornithe-feather`, and mapping inputs `feather-gen2-1.13.2+build.2-mergedv2.jar` / `feather-gen2-1.13.2+build.2.tiny` and `feather-gen2-1.14+build.2-mergedv2.jar` / `feather-gen2-1.14+build.2.tiny`. Both record ready status, Vineflower, Java 25, source-file counts (2,711; 3,152), and the pinned client SHA-256.

- 1.13.2 readiness sidecar SHA-256: `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`; provenance sidecar: `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383`; source manifest: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- 1.14 readiness sidecar SHA-256: `e0ecb319a63d5d664b87ad7c00638e1cdb9dc302ee2c05bbc05a88ce96d1e60b`; provenance sidecar: `bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa`; source manifest: `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98`.

The ready records' source-manifest hashes match the SHA-256 of the corresponding published manifest. Each relied source's SHA-256 matches its line in that manifest. Both provenance records report Java 25.0.3+9-LTS, Gradle 9.7.1 and Vineflower 1.12.0. The 1.13.2 readiness record reports movement-diagnostic review 10; the 1.14 record has no review value.

## Comparison

1.13.2, Feather: `net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative(float,float,float)`, lines 1442–1452; caller in `net/minecraft/entity/living/LivingEntity.java`, line 1926.
1.14, Feather: `net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative(Vec3d)`, lines 1363–1374; caller in `net/minecraft/entity/living/LivingEntity.java`, line 2220.

Difference: The operation is behaviorally unchanged. In both, before the superclass movement-input operation and before the flight-specific branch, it gates on `isSwimming() && !isRiding()`, reads `g = getLookVector().y`, selects `h = 0.085` when `g < -0.2` and `0.06` otherwise, then adjusts vertical velocity only if `g <= 0.0 || jumping || fluid-at-(x, y + 1.0 - 0.1, z)-is-nonempty`. The fluid lookup remains last and short-circuited by the preceding alternatives. Old code writes `velocityY + (g - velocityY) * h`; new code reads the motion vector and writes its y after adding the same `(g - y) * h`. It preserves the numeric operation order for y. The input signature changed from three floats to a `Vec3d`; the swim adjustment still runs before that input reaches the superclass.

The look-vector Y and sine-table indexing also persist: `Entity.getLookVector()` calls `getRotationVector(pitch,yaw)` on both sides; that method computes radians in float and stores `-MathHelper.sin(f)` as a float in `Vec3d` (1.13.2 Entity.java lines 1251–1258; 1.14 Entity.java lines 1181–1188). In both MathHelper.java files, `sin(float x)` returns `SINE_TABLE[(int)(x * 10430.378F) & 65535]` (line 24). The operation then reads this float-backed component as double. There is no table-index lookup in `PlayerEntity.moveRelative` itself and no threshold/coefficient change.

Legal numeric witness: a locally swimming, non-riding player at pitch +30° looking down has float-backed look Y approximately −0.5, so the shared `0.085` branch applies; with starting vertical velocity 0 and the gate true (`g <= 0)), the resulting Y velocity is approximately −0.0425 on both releases. The sine table means the component is its indexed float approximation, not an ideal real-valued sine. This witnesses unchanged behavior; it is not a release-difference witness.

## Relied source bytes

| Release | Source | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 | `net/minecraft/util/math/MathHelper.java` | `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69` |
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.14 | `net/minecraft/entity/living/player/PlayerEntity.java` | `f73879bd42103fa45f39cfe178ac82c7574c6306f00555af7f7e795976275dd7` |
| 1.14 | `net/minecraft/entity/Entity.java` | `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9` |
| 1.14 | `net/minecraft/util/math/MathHelper.java` | `e39d5dfc69c17a9032be086d7d4d14cc63525962a0ff83615b1befdcca599843` |
| 1.14 | `net/minecraft/entity/living/LivingEntity.java` | `1db4dd1aa95a06c48511e5567c0d3836f2a6e5a12a5cf03ab5c892e429198b79` |

Movement meaning: this player swim vertical response has no source-confirmed boundary between 1.13.2 and 1.14.0. Exact intervening-release introduction boundaries are not established here.

