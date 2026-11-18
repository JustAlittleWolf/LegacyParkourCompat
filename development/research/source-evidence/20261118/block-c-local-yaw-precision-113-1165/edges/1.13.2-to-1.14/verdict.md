# Independent audit: ordinary local yaw update, Feather 1.13.2 → 1.14

## Verdict

The claim that the ordinary local yaw update changed numeric precision or current/previous-yaw behavior between these versions is **not supported**. `Entity.updateLocalPlayerCamera(double, double)` has the same expression types, casts, write order, and gates in both exact source trees. Both store `yaw` and `lastYaw` as `float`. The method adds the same double `cursorDeltaX * 0.15` to each field and casts each result to float: current `yaw` is written first; `lastYaw` is written after the current-pitch clamp. Neither yaw field is clamped. There is no finite-value check in this updater. The mouse caller is gated by grabbed mouse plus fullscreen and by a non-null player; otherwise it clears accumulated deltas. These are mode/presence gates, not numeric-finiteness gates.

This verdict is bounded to the ordinary local yaw accumulator and its first dry, non-flying ground/air movement consumer. The consumer's acceleration representation did change in the pair. That is a separately attributable movement difference and must not be described as a yaw-update precision change.

## Exact source identity and freeze

The version-root markers are both `status: ready`, with exact `versionId` values `1.13.2` and literal `1.14`, and `mapping: ornithe-feather`. Their stated mapping inputs are Feather Gen2 build 2 for the corresponding releases. I verified the complete source trees against their version-root manifests before authoring: 1.13.2 has 2,711 entries / 14,770,525 bytes, 1.14 has 3,152 entries / 16,046,421 bytes, and every entry hash matches. The whole source-manifest SHA-256 values match the `sourceManifestSha256` in the ready markers:

- `decompiled_minecraft/1.13.2/ornithe-feather.sources.sha256`: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- `decompiled_minecraft/1.14/ornithe-feather.sources.sha256`: `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98`.

The version-root provenance files were also inspected. 1.13.2 records an exact Feather run and matching version/mapping artifacts; its ready marker does not contain a provenance-file SHA field. 1.14 provenance run `prep-1.14-exact-ornithe-feather-2026-10-09-r1` identifies version `1.14`, Feather Gen2 `1.14+build.2`, and the same tools as the marker; its provenance SHA matches the ready marker (`bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa`). No nested readiness marker was required.

I froze the source-set identity before writing this verdict. Every cited Java file's full SHA-256 matched its entry in the corresponding complete manifest. The main source-byte identities are:

| Release | Source | SHA-256 |
| --- | --- | --- |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.13.2 | `net/minecraft/client/MouseHandler.java` | `b045dc9eaf5b5ab1d9f13dd795465a106f52571dda9e5c837f5c2bbbdfddf4ce` |
| 1.14 | `net/minecraft/entity/Entity.java` | `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9` |
| 1.14 | `net/minecraft/entity/living/LivingEntity.java` | `1db4dd1aa95a06c48511e5567c0d3836f2a6e5a12a5cf03ab5c892e429198b79` |
| 1.14 | `net/minecraft/util/math/Vec3d.java` | `b69b990b768e52d6fac6b3ed82f60e7526e78d0837c868a61a4171ebca21040b` |

Additional checked caller/input source bytes are the versioned `MouseHandler.java`, `KeyboardInput.java`, `LocalClientPlayerEntity.java`, and `MathHelper.java`; each matches its full-manifest entry.

## Yaw write and caller comparison

In 1.13.2 `Entity.java:318–329` and 1.14 `Entity.java:314–325`, the method body is the same:

1. `d` and `e` are `double`, respectively `cursorDeltaY * 0.15` and `cursorDeltaX * 0.15`.
2. Current `pitch` and then current `yaw` are updated with `(float)(floatField + doubleDelta)`.
3. Current pitch alone is clamped to `[-90.0F, 90.0F]`.
4. Previous `lastPitch` and then `lastYaw` receive the same double deltas with float casts; previous pitch alone is clamped.
5. A mounted entity gets `onPassengerLookAround` afterward.

The declarations are `float yaw` / `float lastYaw` (1.13.2 `Entity.java:118–120`; 1.14 `Entity.java:121–123`). No yaw finite check, normalization, or wrap gate appears in this operation. `MouseHandler.turnPlayer` passes the computed deltas to the player in 1.13.2 at line 211 and in 1.14 at line 218; the relevant mouse-grab/fullscreen and null-player gates are otherwise equivalent. Mouse smoothing and sensitivity are upstream of this method and do not alter its numeric contract between the compared sources.

## First ordinary dry ground/air consumer

The reachable local-player movement path copies keyboard movement to the player's sideways/forward speeds (`LocalClientPlayerEntity.java:638–639` in 1.13.2; `604–605` in 1.14). Unsneaked diagonal keyboard input yields finite `(1, 1)` components in both `KeyboardInput` sources. The living-entity movement tick damps those two values by `0.98F` and calls `moveRelative` (`LivingEntity.java:1921–1926` in 1.13.2; `2215–2220` in 1.14).

For the ordinary non-water, non-lava, non-fall-flying branch, 1.13.2 `LivingEntity.java:1478–1554` selects the ground friction factor or `speedInAir`, then calls `updateVelocity(forwards, sideways, upwards, v)`. With the local-player argument order, this is the first regular ground/air consumer of the stored yaw. In 1.14 `LivingEntity.java:1779–1839` builds a `Vec3d`, selects the corresponding factor through `m_70235197`, then calls `updateVelocity(factor, vec3d)`.

The trig angle expression remains float in both `Entity` methods: `yaw * (float)(Math.PI / 180.0)`, followed by `MathHelper.sin` and `cos`. The adjacent acceleration math is not identical:

- 1.13.2 `Entity.java:1057–1073` forms the input squared length and normalization factor in `float`, scales each component in `float`, then performs the yaw rotation with float products/sums before widening the result into the double velocity fields. Its small-input gate is `!(f < 1.0E-4F)`.
- 1.14 `Entity.java:1010–1024` routes through a `Vec3d` helper. The squared length is accumulated in `double`; it gates at `d < 1.0E-7`, normalizes when `d > 1.0`, scales the vector, then rotates its double components using float sine/cosine values. `Vec3d.normalize` uses `MathHelper.sqrt(double)`, whose return type is float, but divides the double components by that widened float and retains double precision for subsequent scaling/rotation (`Vec3d.java:25–27`; `MathHelper.java:36`). This changes component normalization and rotation rounding independently of the unchanged yaw update.

A legal finite witness for that separate consumer difference is an unsneaked local player pressing forward plus left, airborne, not sprinting or fall-flying, with zero stored yaw and zero prior horizontal velocity. The keyboard input is `(1, 1)`; after the movement tick's `0.98F` damping, the vector components are each `a = 0.9800000190734863F`, vertical input is zero, and the air factor is `0.02F` (`LivingEntity.speedInAir`). The yaw trig values are exactly sin(0)=0 and cos(0)=1. Applying the cited source expressions gives old float-path horizontal increments of about `0.01414213515818119` each and 1.14 double-vector increments of about `0.014142135022068599` each. The increment differs in the stored double velocity; this witness demonstrates the downstream representation change, not a different yaw accumulator. It stays away from either small-input cutoff and uses no NaN/infinity.

## Audit disposition

Reject any source claim that 1.13.2→1.14 changed the ordinary local yaw update's numeric width, casts, current/previous ordering, or finite gates. The directly yaw-dependent ground/air acceleration transform is a separate changed operation with a reachable finite diagonal-input witness; it needs its own operation-level claim and acceptance boundary if pursued. This audit does not assert full-pair coverage or any intermediate patch cutover.


## Phase-two audit of the frozen exact report

The bounded input `c-yaw-precision-1132-114-source/exact-report.md` was accepted for review only after its full SHA-256 matched the assigned identity `da0399ca1fe6c9a621a25a96d0d1c7e5291389369815f0a60daa576c950ca5ba`. The exact-release source manifests and the cited source-file hashes in the preceding audit remained unchanged.

The report's ordinary `MouseHandler.turnPlayer` → `Entity.updateLocalPlayerCamera` comparison agrees with this audit: the operation's doubles, casts to float fields, current/previous write order, pitch-only clamp, mouse-grab/fullscreen gate, non-null-player gate, and absence of a finite-value guard are the same across the exact pair. The report's additional NBT writer is a real but separate source difference. Both readers load `yaw` and `pitch` from the Rotation float list and copy each to its previous field before angle validation (`1.13.2 Entity.java:1425–1428`; `1.14 Entity.java:1357–1360`). 1.13.2 later calls `setRotation` unconditionally (`Entity.java:1446–1447`); 1.14 first rejects non-finite position, then calls `setPosition`/`setRotation` and processes the remaining NBT only under `Double.isFinite(yaw) && Double.isFinite(pitch)` (`Entity.java:1378–1384`). Thus the 1.14 finite gate is present for load-time setter/post-load behavior, but it occurs after both angle fields are assigned and does not sanitize them. This is malformed-NBT initialization behavior, not a change to ordinary local yaw turning or its mouse finite gates.

The report's airborne right-only witness is source-valid and independently demonstrates a finite precision difference in relative acceleration. Right-only input yields `-1.0F`; movement-tick damping yields `a = -0.98F`; default air acceleration is `0.02F`; and the vector's squared length is below 1 but above both zero cutoffs. At yaw `0.0F`, sin is zero and cos is one. The 1.13.2 float multiplication stores `-0.019600000232458115` as a widened double velocity increment; 1.14's widened-double component multiplied by widened-double `0.02F` is `-0.01959999994337558`. The magnitude difference is `2.8908253568715736E-10`. The witness has no NaN/infinity and uses an ordinary local-player input path.

This witness is deliberately independent of yaw accumulation: with yaw zero the rotation is identity, so it establishes scalar/vector acceleration rounding in `updateVelocity`, not a changed yaw value, trigonometric result, or yaw-dependent rotation effect. It supports the separate acceleration-precision observation above, while the direct ordinary-yaw verdict remains no difference. The report's malformed-NBT gate is accepted as a distinct load-time behavior claim, with applicability outside this ordinary mouse-turn verdict. No full-pair coverage or patch cutover is inferred.