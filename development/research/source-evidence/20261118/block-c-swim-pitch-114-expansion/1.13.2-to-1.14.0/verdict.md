# Independent source audit: 1.13.2 → 1.14 swim pitch vertical adjustment

## Verdict

**Accepted as scoped no-difference coverage.** The source report's conclusion that `PlayerEntity.moveRelative` has no behavior change for this vertical swim adjustment is supported by the exact paired sources. The evidence does not establish the feature's introduction boundary before 1.13.2, and this verdict does not freeze full-pair coverage.

## Blind source freeze and evidence identity

Before opening the author report, independently checked both exact Feather source sets and traced the operation and its movement caller. The report was opened only after that freeze. I then verified its SHA-256 against the assigned identity.

- 1.13.2 readiness: `decompiled_minecraft/1.13.2/ornithe-feather.ready.json`, SHA-256 `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`; provenance: `ornithe-feather.provenance.json`, SHA-256 `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383`; source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; artifact manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`.
- 1.14.0 readiness (metadata ID `1.14`): `decompiled_minecraft/1.14/ornithe-feather.ready.json`, SHA-256 `e0ecb319a63d5d664b87ad7c00638e1cdb9dc302ee2c05bbc05a88ce96d1e60b`; provenance: `ornithe-feather.provenance.json`, SHA-256 `bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa`; source manifest SHA-256 `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98`; artifact manifest SHA-256 `2fdf423e2fbe0d20ec961cc42cd69b3107caeed88edf3e097ee588d29868f5df`.
- Ready markers identify `ornithe-feather`, exact versions 1.13.2 and 1.14, Feather build.2 mapping inputs and Vineflower; both provenance records name Java 25.0.3+9-LTS, Gradle 9.7.1 and Vineflower 1.12.0. Each ready marker's source-manifest digest matches the published manifest. Independently checked every manifest entry against the published tree: 2,711/2,711 files and 14,770,525 bytes for 1.13.2; 3,152/3,152 files and 16,046,421 bytes for 1.14; zero missing files or hash mismatches. Each relied file below matches its manifest entry.
- Assigned author report identity: `c-swim-pitch-vertical-1132-114-source/report.md`, SHA-256 `5e6e5c8a4ee1e3e0bad46fd1623451730b1286e27fea34b3d2ee19823d9b7db1`.

## Source trace

The compared methods are `PlayerEntity.moveRelative(float,float,float)` at 1.13.2 lines 1442–1452 and `PlayerEntity.moveRelative(Vec3d)` at 1.14 lines 1363–1374. In both, before the superclass movement-input call and before the flying-player branch, the operation gates on `isSwimming() && !isRiding()`, reads `g = getLookVector().y`, selects double `h = 0.085` when `g < -0.2` and `0.06` otherwise, then applies the vertical correction only when `g <= 0.0 || jumping || !fluidStateAt(new BlockPos(x, y + 1.0 - 0.1, z)).isEmpty()`. The fluid lookup remains short-circuited by the first two alternatives. `PlayerEntity.isSwimming()` has the same flying/spectator/flag gate in both releases; entity swimming-state refresh also has the same sprint, water/submersion and riding gates.

The old write is `velocityY = velocityY + (g - velocityY) * h`. The new write snapshots the motion vector and calls `add(0.0, (g - currentY) * h, 0.0)`, then stores it. `Vec3d.add(double,double,double)` constructs `(x + dx, y + dy, z + dz)` in both releases; the new y addition therefore preserves the same double operation order without a float cast. Both implementations run this before the real superclass movement. The caller is `LivingEntity.tick`'s travel section: 1.13.2 line 1926 calls `moveRelative(sidewaysSpeed, verticalSpeed, forwardSpeed)`; 1.14 line 2220 calls it with `new Vec3d(sidewaysSpeed, verticalSpeed, forwardSpeed)`. `LocalClientPlayerEntity.tick()` calls `super.tick()` in both releases, and the class chain reaches `PlayerEntity`'s override. The movement caller is virtual, so the player-specific adjustment is reached for that path.

`Entity.getLookVector()` delegates to `getRotationVector(pitch, yaw)` on both sides. Both `getRotationVector(float,float)` implementations calculate pitch radians in `float`, call `MathHelper.sin(float)`, retain `-sin(pitchRadians)` as a float component passed to `Vec3d`, and only then widen it to double. `MathHelper.sin` is identical: `SINE_TABLE[(int)(x * 10430.378F) & 65535]` (both `MathHelper.java`, line 24). Thus the float rounding and sine-table index are preserved; the threshold, coefficient, lookup coordinates and gates are also unchanged.

A reachable witness is a non-riding, non-flying, non-spectator player whose swimming flag is active and whose pitch is +30°: look-Y is approximately −0.5, selecting `h = 0.085`; with current vertical velocity 0, `g <= 0` opens the gate and both versions produce approximately −0.0425. This verifies the shared operation, not a release difference. A fully dry tick whose state refresh has cleared swimming does not enter this branch; the shared gate limits the finding to active swimming.

## Reliance hashes

Hashes are SHA-256 of exact UTF-8 published source bytes, and were matched against the corresponding source manifest.

| Release | Source | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 | `net/minecraft/util/math/MathHelper.java` | `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69` |
| 1.13.2 | `net/minecraft/util/math/Vec3d.java` | `6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5` |
| 1.13.2 | `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` |
| 1.13.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.14 | `net/minecraft/entity/living/player/PlayerEntity.java` | `f73879bd42103fa45f39cfe178ac82c7574c6306f00555af7f7e795976275dd7` |
| 1.14 | `net/minecraft/entity/living/LivingEntity.java` | `1db4dd1aa95a06c48511e5567c0d3836f2a6e5a12a5cf03ab5c892e429198b79` |
| 1.14 | `net/minecraft/entity/Entity.java` | `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9` |
| 1.14 | `net/minecraft/util/math/MathHelper.java` | `e39d5dfc69c17a9032be086d7d4d14cc63525962a0ff83615b1befdcca599843` |
| 1.14 | `net/minecraft/util/math/Vec3d.java` | `b69b990b768e52d6fac6b3ed82f60e7526e78d0837c868a61a4171ebca21040b` |
| 1.14 | `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `b1ac72bad8c09f5e68e4d0682d431e3b54471e854810b94d91f830a95ae408dd` |
| 1.14 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2b1ad3b3416949a9da2607a3ec2251aa69b2d9eebd76638c92e20749d625338f` |
