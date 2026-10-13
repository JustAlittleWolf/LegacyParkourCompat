# F-ELYTRA-LOOK-GUARD — 1.13.2 guards glide pitch correction against zero horizontal look

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1425-1599.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public Vec3d getRotationVector(float tickDelta) {`, lines 1229-1237.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`, `protected final Vec3d getRotationVector(float pitch, float yaw) {`, lines 1239-1245.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1478-1669.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public final Vec3d getRotationVector(float tickDelta) {`, lines 1239-1241.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `protected final Vec3d getRotationVector(float pitch, float yaw) {`, lines 1251-1259.

Difference: 1.12.2 executes the pitch-up correction when the pitch-derived value is negative; 1.13.2 additionally requires positive horizontal look length before dividing by it.

The look-vector formulas themselves changed between versions, so the guard must be considered with the exact `MathHelper` float/trig source and camera pitch endpoint, not as an isolated algebraic simplification.

Movement meaning: - Reachability and dependencies: PlayerEntity camera pitch is clamped to [-90, 90] in both sources (1.12.2 Entity lines 307-314; 1.13.2 lines 318-326); the local player's inherited fall-flying moveRelative reaches this branch when the Elytra movement state is active.

Other gliding terms are F-ELYTRA-GLIDE-MATH.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_12/VerticalPitchGlideLook.java"
    ],
    "hooks": [
      "FallFlyingLookHook"
    ],
    "limits": [
      "VerticalPitchGlideLook changes only the look vector at pitch -90 for profiles >=V1_9; it does not replace the positive-horizontal-look denominator gate in travel. Exact combined travel/look operation fidelity remains open."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java",
      "member": "public void moveRelative(float forwards, float sideways, float upwards) {",
      "lines": "1425-1599",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 a6fc8b4c678a9d87c512d62b80b1ebd28fa28b997281a03498a822cfa07e49c0."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public Vec3d getRotationVector(float tickDelta) {",
      "lines": "1229-1237",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 8fefe9e6164839695cde9840610853f02df4a019971c8c3c175936dc45b9cda1."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "protected final Vec3d getRotationVector(float pitch, float yaw) {",
      "lines": "1239-1245",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 33e75c4484333653798ba92c5cf0db66aad879e55d973e3afaf5fb0b6272c5b7."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java",
      "member": "public void moveRelative(float forwards, float sideways, float upwards) {",
      "lines": "1478-1669",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 78de2dc443a97a6d6d08a3e430b0a5dbcb967ad73a5dddd14d9f7169cec56e82."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public final Vec3d getRotationVector(float tickDelta) {",
      "lines": "1239-1241",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 774d28a11c6db1573a5531115a5ccd744fc193031fc95a813517460e59bf3414."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "protected final Vec3d getRotationVector(float pitch, float yaw) {",
      "lines": "1251-1259",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 2ba72b5ed269f2fd32cd3d77866dffec2974b968458bf4628a084210c56968fc."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-ELYTRA-LOOK-GUARD is unknown within (1.12.2, 1.13.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-ELYTRA-LOOK-GUARD",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for glide-vertical-gaze",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
