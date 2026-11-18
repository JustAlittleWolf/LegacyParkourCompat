# Exact source comparison: 1.13.2 → 1.14

Scope: ordinary local mouse yaw turning, current/previous yaw and finite gates, and the first dry ground/air relative-acceleration operation in the local player path. This is a source comparison of the exact release IDs 1.13.2 and 1.14, not 1.14.4.

## Exact source identity

The canonical source roots are `decompiled_minecraft/1.13.2/ornithe-feather/` and `decompiled_minecraft/1.14/ornithe-feather/`. The version-root ready/provenance/source-hash manifests identify those literal releases and mapping family. Both source-manifest SHA-256 values match the ready markers; the 1.14 provenance-file SHA-256 matches its ready marker. Each source-file digest below was recalculated from the file and matches its row in the corresponding `ornithe-feather.sources.sha256`.

| Exact release | Client SHA-256 | Mapping identity and pinned inputs | Source-manifest SHA-256 |
|---|---|---|---|
| 1.13.2 | `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` | `ornithe-feather`; Feather build.2 merged JAR and Tiny mapping: `feather-gen2-1.13.2+build.2-mergedv2.jar`, `feather-gen2-1.13.2+build.2.tiny` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |
| 1.14 | `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a` | `ornithe-feather`; `net.ornithemc:feather-gen2:1.14+build.2`; merged JAR SHA-256 `57f0e3227099d010c4a0c7f2fea03dbd665bc7e612ad204db80fa5b5728e65a3`, Tiny SHA-256 `d865d01921c115a1c20dfdc1b7cdca1c59ff1694cad54bd79e146d553417d31f` | `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98` |

Relevant inspected-file pins:

| Path relative to the release’s `ornithe-feather/` root | 1.13.2 SHA-256 | 1.14 SHA-256 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | `1db4dd1aa95a06c48511e5567c0d3836f2a6e5a12a5cf03ab5c892e429198b79` |
| `net/minecraft/client/MouseHandler.java` | `b045dc9eaf5b5ab1d9f13dd795465a106f52571dda9e5c837f5c2bbbdfddf4ce` | `6f9a4c35254c0fb09cfebcd21e784414d52686ea50fb2ec89c278598d8ddcf1a` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` | `2b1ad3b3416949a9da2607a3ec2251aa69b2d9eebd76638c92e20749d625338f` |
| `net/minecraft/client/entity/living/player/KeyboardInput.java` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` | `5932453a9e48e7a798ae1be1cd3a4bf660b6b3be43bb7e5dc22686b3c4a82526` |

## Ordinary local yaw turn: no difference found

`MouseHandler.turnPlayer()` has the same movement-relevant gates and arithmetic in both releases: mouse grabbed and fullscreen; sensitivity computed as `double f = mouseSensitivity * 0.6F + 0.2F`, then `double g = f * f * f * 8.0`; smooth/direct mouse deltas remain `double`; and `updateLocalPlayerCamera` is called only if the player is non-null (`1.13.2 MouseHandler.java:181–212`; `1.14 MouseHandler.java:188–219`). The GLFW timer call is named differently in the 1.14 decompilation and does not alter the delta arithmetic.

`Entity.updateLocalPlayerCamera(double cursorDeltaX, double cursorDeltaY)` is identical (`1.13.2 Entity.java:318–330`; `1.14 Entity.java:314–326`). It calculates `d = cursorDeltaY * 0.15` then `e = cursorDeltaX * 0.15` as doubles; adds `d`/`e` to current float pitch/yaw in double precision and casts each result to float; clamps current pitch; then adds the same double deltas to previous pitch/yaw and casts to float; clamps previous pitch. Yaw is neither clamped nor wrapped here. Neither version applies a finite-value gate to the ordinary turn path.

A separate load-time gate is present in 1.14. Both readers first assign current yaw/pitch from the NBT float list and copy them into previous yaw/pitch (`1.13.2 Entity.java:1425–1428`; `1.14 Entity.java:1357–1360`). The 1.13.2 reader later calls `setPosition` and `setRotation` unconditionally (`Entity.java:1446–1447`). In 1.14, finite position is required and non-finite position throws; `Double.isFinite(yaw) && Double.isFinite(pitch)` gates `setPosition`, `setRotation`, and later NBT processing (`Entity.java:1378–1384`). Because the current and previous angles were assigned before that guard, it does not sanitize them. This is malformed-NBT behavior, not a finite witness or change to ordinary local turning.

## Relative acceleration: float intermediates become double vector arithmetic

In 1.13.2 `Entity.updateVelocity(float sideways, float forwards, float upwards, float scale)` forms the squared input magnitude in float, returns only below `1.0E-4F`, takes a float square root, floors the length at `1.0F`, divides/scales the float components, and obtains float sine/cosine from float yaw. The rotated horizontal products are float operations before being added to the double velocity fields (`Entity.java:1057–1074`).

In 1.14 `Entity.updateVelocity(float, Vec3d)` computes the rotated movement vector with doubles and adds it to the current `Vec3d` velocity (`Entity.java:1010–1013`). Its helper uses double squared length, a `1.0E-7` zero threshold, normalization only above length 1, double vector scaling, float sine/cosine, and double component products (`Entity.java:1015–1025`). The horizontal rotation signs/axes match, but the 1.14 vector is widened from the float movement fields and its scaling/products retain double precision instead of rounding at the 1.13 float intermediate points.

The first normal dry ground/air relative-acceleration call is inside `LivingEntity.moveRelative`. Both releases require local control or logical-side movement updating and take the same dry, non-water/non-lava, non-fall-flying branch (`1.13.2 LivingEntity.java:1478–1488`; `1.14 LivingEntity.java:1779–1790`). The effective input order is the same: 1.13.2 passes `(sidewaysSpeed, verticalSpeed, forwardSpeed)` to `moveRelative` and forwards those positions to `Entity.updateVelocity` (`LivingEntity.java:1478, 1921–1926, 1553`); 1.14 constructs `Vec3d` in that same field order (`LivingEntity.java:2214–2220`). At the ordinary ground/air branch, 1.13.2 selects `speedInAir` when airborne and calls the float method (`LivingEntity.java:1545–1553`); 1.14’s helper likewise returns `speedInAir` when airborne, and calls the vector method (`LivingEntity.java:1833–1839, 1954–1956`). The immediate position-moving consumer follows the update: `move(SELF, velocityX, velocityY, velocityZ)` at 1.13.2 line 1575; in 1.14 the vector is stored, left unchanged by the climbing-only helper for a non-climbing witness, and passed to `move(SELF, velocity)` at line 1839.

## Lawful finite local-player witness

A local player starts on dry ground at rest, jumps straight up with no horizontal input, then on the first airborne tick presses only right, with no sneak, no water/lava, no fall-flying, no climb, no other movement modifiers, and yaw `0.0F`. This is reachable with ordinary keyboard input: right produces `movementSideways = -1.0F` in each `KeyboardInput` for right-only input (`1.13.2 KeyboardInput.java:30–42`; `1.14 KeyboardInput.java:14–24`). In 1.14, `LocalClientPlayerEntity` calls `input.tick(bl4, isSpectator)`; with normal standing pose, no sneak, no swim, and a non-spectator, `bl4` and `isSpectator` are false, so the keyboard input is not reduced (`LocalClientPlayerEntity.java:596–598, 626–635`; `Entity.java:1746–1748, 1770–1772`). The camera-controlled local player copies the value to `sidewaysSpeed` (`1.13.2 LocalClientPlayerEntity.java:634–640`; `1.14 LocalClientPlayerEntity.java:600–606`); the living tick multiplies it by `0.98F` before passing the movement vector (`1.13.2 LivingEntity.java:1921–1926`; `1.14 LivingEntity.java:2214–2220`). `LocalClientPlayerEntity.isLocallyControlled()` returns true (`1.13.2 LocalClientPlayerEntity.java:488–491`; `1.14 LocalClientPlayerEntity.java:485–488`), so the method gate is satisfied. Air acceleration is finite `speedInAir = 0.02F` in both (`LivingEntity.java:132` and `150`). The vector is `(-0.98F, 0, 0)`, nonzero and below unit length, so it passes both zero thresholds and neither version normalizes it. At yaw zero, cosine is 1 and sine is 0.

For this witness, with starting horizontal velocity zero, 1.13.2 scales and rounds the X input in float: Java’s float result string is `-0.0196F` for `-0.98F * 0.02F`. The 1.14 double vector path computes the exact product of those widened float operands as `-0.01959999994337558`. The values differ by approximately `2.8908253568715736E-10`; both remain finite. This difference enters the velocity consumed by the first movement call above. It is a source-level acceleration witness, not a full-tick trajectory claim.

The ground acceleration coefficient is outside this yaw-transform witness: 1.13.2 expresses it using `0.16277137F / (friction * 0.91F)^3`, while 1.14 uses a `0.21600002F / slipperiness^3` helper. The witness deliberately uses the airborne `speedInAir` branch so that comparison isolates the yaw-relative vector precision change.


