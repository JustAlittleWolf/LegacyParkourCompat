# Lead C: ordinary Elytra pitch conversion

## Verdict

For the same finite float pitch after normalizing both releases to the same signed pitch convention, the direct degrees-to-radians operation is source-identical. I found no independent 1.12.2→26.2 change in this operation. A common signed pitch p is multiplied by the same positive float constant (float)(Math.PI / 180.0) in each version, with a float result. Therefore the resulting angle and the sign test (< 0.0F) are bit-identical for equal p; this includes signed zero (both comparisons are false). This is a static arithmetic witness, not a measured gameplay trace.

## Actual local dry-flight path

- **1.12.2:** LivingEntity.mobTick reaches flyingTick() then moveRelative(...) (LivingEntity.java:1842-1847). The local client player reports isLocallyControlled() == true (LocalClientPlayerEntity.java:484-487). On the active Elytra path, flyingTick requires the fall-flying flag, airborne state, and not riding; it retains flight for an Elytra that can fly (LivingEntity.java:1854-1872). In moveRelative, a dry player reaches the active-flight branch and then move(SELF, velocity) (LivingEntity.java:1425-1464).
- **26.2:** the local player reports isLocalPlayer() == true (LocalPlayer.java:378-380); Player.canSimulateMovement and isEffectiveAi both accept the local client player (Player.java:1290-1297). In the nonpassenger branch, LivingEntity calls travel(input) when these gates pass (LivingEntity.java:3137-3141). With no fluid travel selected, an active fall-flying flag selects travelFallFlying (LivingEntity.java:2430-2438), which updates velocity and calls move(SELF, ...) (LivingEntity.java:2581-2594).

These establish the reachable next local movement path under the stated dry, active, nonpassenger preconditions. They do not establish a runtime recording.

## Isolation and limits

The angle expressions are float i = this.pitch * (float) (Math.PI / 180.0) (1.12.2 LivingEntity.java:1434-1435) and float leanAngle = this.getXRot() * (float) (Math.PI / 180.0) (26.2 LivingEntity.java:2602-2605). The old pitch is a float field (Entity.java:107-110); modern getXRot() returns its float field (Entity.java:3906-3908). The corresponding nose-up gates are both < 0.0F (old LivingEntity.java:1449-1453, modern LivingEntity.java:2615-2618). For this comparison, the current pitch is a fixed movement input; mouse/device scheduling is not modeled.

This conclusion is only about direct pitch conversion and sign preservation. The surrounding flight kernels differ: 1.12.2 uses MathHelper.cos/sin, scales its cosine term by min(1.0, lookLength / 0.4), and adds -0.08 + m * 0.06 (LivingEntity.java:1436-1453); 26.2 derives liftForce from Math.cos, uses effective gravity, and has different lift terms (LivingEntity.java:2605-2626). Look-vector generation also remains a separate trigonometric path (old Entity.java:1239-1245; modern Entity.java:1966-1973). Holding each release's other inputs and kernel fixed, substituting the counterpart direct angle expression cannot change that release's result. A cross-release next-motion delta cannot be attributed to pitch conversion from these sources, and no numeric counterfactual was run.

## Authenticated source identities

Both published trees were checked against their ready markers and full source manifests before reading movement method bodies. Every manifest entry matched the source tree (no missing, extra, or hash-mismatched files).

- **1.12.2 / ornithe-feather:** provenance states exact version 1.12.2, Feather mapping option and artifacts feather-gen2-1.12.2+build.2-mergedv2.jar plus feather-gen2-1.12.2+build.2.tiny. Ready marker source manifest SHA-256: b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da; 2,050 files. Relevant source file hashes: LivingEntity.java 190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6; Entity.java 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a; LocalClientPlayerEntity.java 01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc.
- **26.2 / unobfuscated:** provenance states exact version 26.2, unobfuscated mapping option and no mapping artifacts. Ready marker source manifest SHA-256: a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894; 7,055 files. Relevant source file hashes: LivingEntity.java 7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a; Entity.java 7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5; Player.java 8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531; LocalPlayer.java 8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6.

No tests, game launch, decompilation or product/research/Git access were used.
