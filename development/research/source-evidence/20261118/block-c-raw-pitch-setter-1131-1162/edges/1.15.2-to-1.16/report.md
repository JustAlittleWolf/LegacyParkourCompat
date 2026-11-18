# 1.15.2 → 1.16.0 direct pitch setter slice

## Evidence identity and pin check

Host/environment: Windows PowerShell; product checkout `D:\Javastuff\LegacyParkourCompat`. This is a source-only comparison. I read the canonical version-root pins at `decompiled_minecraft/1.15.2/` and `decompiled_minecraft/1.16/`, mapping `mojmap`; no source generation, Git operations, product/wiki review, build, test, runtime, network access, or delegation was performed.

| Pin | 1.15.2 Mojmap | 1.16 Mojmap |
|---|---|---|
| Ready marker SHA-256 | `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` | `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2` |
| Provenance sidecar SHA-256 | `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` | `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9` |
| Source manifest SHA-256 (matches ready marker) | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` |
| Version/mapping in ready marker | `1.15.2` / `mojmap` | `1.16` / `mojmap` |

For every source named below, its SHA-256 was checked against its row in that version's `mojmap.sources.sha256`; expected and actual bytes matched. The relevant source pins are:

| Relative source | 1.15.2 SHA-256 | 1.16 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` |
| `net/minecraft/world/entity/LivingEntity.java` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |
| `net/minecraft/util/Mth.java` | `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |
| `net/minecraft/client/player/LocalPlayer.java` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `db13c55b0ba08cb2a40cf2da7a3954de24e1e1f55a197f125cc1aa6ed109be0b` | `89dde15acaa210ecdfd6bb429a8fc969acdd189feb03c0ea20faea13865ea64f` |
| `net/minecraft/world/entity/player/Player.java` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` |

The 1.15.2 ready marker predates the 1.16 marker's `provenanceSha256` field; the provenance sidecar itself is pinned above. The 1.16 ready marker's provenance hash agrees with the sidecar hash above.

## Compared operation 1: `Entity.turn` width, first pitch assignment, clamp, and previous pitch

1.15.2 Mojmap `Entity.java:326–338`; 1.16 Mojmap `Entity.java:332–344`. Both bodies perform the same ordered operations:

```java
double f = e * 0.15;
...
this.xRot = (float)(this.xRot + f);
...
this.xRot = Mth.clamp(this.xRot, -90.0F, 90.0F);
this.xRotO = (float)(this.xRotO + f);
...
this.xRotO = Mth.clamp(this.xRotO, -90.0F, 90.0F);
```

There is no release difference in parameter width (`double`), scaling width (double literal), cast point (the sum narrows when assigned to the float field), or write/clamp order. `xRotO` remains independently updated from its prior value after the current pitch has already been clamped; the method does not copy current pitch into previous pitch. `Mth.clamp(float,float,float)` is also identical: 1.15.2 `Mth.java:98–104`; 1.16 `Mth.java:94–100` (`f < min ? min : f > max ? max : f`).

Finite in-range illustration: with prior `xRot=xRotO=0` and finite `e=200.0`, `f=30.0`, so both versions first store `30.0F`, then clamp current and previous pitch to `30.0F`.

Finite-input narrowing overflow, considered separately: finite `e=3.0e39` gives a finite double `f=4.5e38`, beyond binary32's finite range. The first assignment's double sum narrows to positive infinity; the subsequent float clamp compares it above `90.0F` and stores `90.0F`. The independently updated `xRotO` follows the same cast-then-clamp path. Both versions do this in the same order. This is not double arithmetic overflowing, nor evidence that the first assignment itself clamps.

## Compared operation 2: direct pitch reader and active local Elytra movement consumer

Both `Entity.getLookAngle()` implementations pass current `xRot` and `yRot` to `calculateViewVector`. The identical helper converts each float angle with a float degree-to-radian factor and uses the same trig/output order: 1.15.2 `Entity.java:1219–1227`; 1.16 `Entity.java:1237–1245`. No change appears between setting pitch and building the look vector.

The local-player route is present in both source sets: `LocalPlayer` extends `AbstractClientPlayer`, which extends `Player`, which extends `LivingEntity`. `LocalPlayer.aiStep()` delegates through `AbstractClientPlayer` to `Player.aiStep()` (1.15.2 `LocalPlayer.java:778`; 1.16 `LocalPlayer.java:813`), and `Player.aiStep()` delegates to `LivingEntity.aiStep()` (1.15.2 `Player.java:514`; 1.16 `Player.java:500`). There, `updateFallFlying()` precedes `travel(new Vec3(...))` (1.15.2 `LivingEntity.java:2285–2288`; 1.16 `LivingEntity.java:2455–2458`). The `isFallFlying()` predicate reads shared flag 7 in both (1.15.2 `LivingEntity.java:2690–2691`; 1.16 `LivingEntity.java:2875–2876`). Thus a direct `turn` call that has completed before the next local player step supplies the current pitch to that step's living travel; this finding does not assume or inspect device scheduling.

For the bounded dry, non-lava route with local control and the fall-flying flag active, both `LivingEntity.travel` implementations consume pitch in the same Elytra math. 1.15.2 `LivingEntity.java:1842–1871` and 1.16 `LivingEntity.java:1977–2006` both read `getLookAngle()`, compute `float angle = xRot * (float)(Math.PI / 180.0)`, derive `Mth.cos(angle)`, add pitch-dependent vertical motion, conditionally add look-direction/downward or upward-pitch terms, damp velocity, and call `move` with the resulting velocity. Variable names differ; math and order in this branch do not. Example `turn(0, 200)` changes current pitch to `30°`; on the next active Elytra travel the shared branch uses that `30°` both in the look vector and angle terms, changing the vertical-control coefficient relative to zero pitch. The surrounding `travel` structure did change in 1.16 (including fluid handling), so this is a branch-bounded no-difference statement for the stated dry, active-Elytra case, not a claim that the whole `travel` method is unchanged.

## Result and coverage limit

No source-confirmed difference was found in the assigned pitch setter/cast/clamp/previous-state operation, or in its consumption by the next active local Elytra movement on the bounded dry route. Finite narrowing overflow is handled after the first float write by the same clamp in both versions. This covers only the assigned source slice and scenarios above; it does not freeze the pair or establish a runtime trace.