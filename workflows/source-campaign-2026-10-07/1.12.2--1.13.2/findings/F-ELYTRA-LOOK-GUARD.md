# F-ELYTRA-LOOK-GUARD — 1.13.2 guards glide pitch correction against zero horizontal look

- Status: provisional source candidate; artifact-integrity hold and input/math closure pending
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, glide pitch correction lines 1434-1455; `Entity.getRotationVector(float,float)` lines 1239-1245; SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` and `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`.
- B evidence: corresponding LivingEntity correction lines 1495-1518, Entity look-vector lines 1251-1258; SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` and `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- Difference: A executes the pitch-up correction when the pitch-derived value is negative; B additionally requires positive horizontal look length before dividing by it. The look-vector formulas themselves changed between versions, so the guard must be considered with the exact `MathHelper` float/trig source and camera pitch endpoint, not as an isolated algebraic simplification.
- Reachability: PlayerEntity camera pitch is clamped to [-90, 90] in both sources (A Entity lines 307-314; B lines 318-326); the local player's inherited fall-flying moveRelative reaches this branch when the Elytra movement state is active.
- Limits: exact trigonometric endpoint and branch proof should be rechecked after artifact repair. No trajectory was measured. Other gliding terms are F-ELYTRA-GLIDE-MATH.
- Integrity hold: do not accept/freeze until source owner/ops repair and freshly verify derived mapped artifacts.
