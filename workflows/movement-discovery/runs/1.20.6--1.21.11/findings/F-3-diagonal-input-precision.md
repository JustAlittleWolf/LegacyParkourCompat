# F-3: Diagonal keyboard input normalizes at different precision

- Older version A: 1.20.6.
- Newer version B: 1.21.11.
- Mechanic / coverage slice IDs: stage 1, slice 1.1 (keyboard input vector); stage 3 movement-input consumer.
- Classification: changed behavior (small floating-point input delta).
- Confidence: source-confirmed; numeric consequence is a direct evaluation of the cited Java float/double operations.
- Applicability: local keyboard diagonal movement with no sneaking/crawling or item-use scaling.
- First changed release: unknown within (1.20.6, 1.21.11].
- Runtime validation: not performed.

## Paired evidence

- A source: `decompiled_minecraft/1.20.6/mojmap/net/minecraft/client/player/KeyboardInput.java`, `KeyboardInput.tick(boolean,float)`, lines 21–37; SHA-256 `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0`. With both a forward and lateral key held and no slow-movement scaling, the method stores both raw impulses as `1.0F`.
- A consumer: `decompiled_minecraft/1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, `LivingEntity.aiStep()`, lines 2671–2672; SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`, multiplies `xxa` and `zza` by `0.98F` before travel. `decompiled_minecraft/1.20.6/mojmap/net/minecraft/world/entity/Entity.java`, `Entity.getInputVector(Vec3,float,float)`, lines 1316–1325; SHA-256 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`, normalizes a vector when its squared length exceeds `1.0`. A `Vec3.normalize()` uses double `Math.sqrt` and double components at lines 67–70; SHA-256 `dc05337c94e64d5c137138ef98b7f10768a420af23bffb0812b68a8320c7c813`.
- B source: `decompiled_minecraft/1.21.11/mojmap/net/minecraft/client/player/KeyboardInput.java`, `KeyboardInput.tick()`, lines 23–36; SHA-256 `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3`. The two `1.0F` impulses are normalized immediately by `Vec2.normalized()`, which computes a float square root and divides float components (`decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/phys/Vec2.java:48-50`, SHA-256 `c30f834079433af143151a684cf49d86de4c1102397221fe006031576046d0ff`; `Mth.sqrt(float)` casts `Math.sqrt` back to float (`decompiled_minecraft/1.21.11/mojmap/net/minecraft/util/Mth.java:58-60`, SHA-256 `1b547c6b896c9b149d4bc03e3ed106ece485d7ac481a792ae30f600d0239726c`).
- B consumer: `decompiled_minecraft/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java`, `modifyInput()` and `modifyInputSpeedForSquareMovement()`, lines 668–705; SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`. It applies `0.98F`, reshapes the vector to the unit square, and passes the result to `applyInput()`. `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/Entity.java`, `Entity.getInputVector(Vec3,float,float)`, lines 1613–1622; SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`, retains the `lengthSqr() > 1.0` normalization guard.

## Source-level difference

For a diagonal keyboard vector with no sneak or item-use multiplier, A delays normalization until `Entity.getInputVector`: each axis first becomes `0.98F`, then the vector is normalized in double precision because its squared length exceeds 1. At yaw 0, each horizontal component evaluates to approximately `0.7071067811865476`.

B first normalizes `(1.0F, 1.0F)` in `Vec2` float precision, then `modifyInputSpeedForSquareMovement()` returns the float direction scaled to the unit-square diagonal. The resulting components evaluate to approximately `0.7071067690849304`; their squared vector length is below 1, so the later `Entity.getInputVector()` guard does not renormalize them. The difference is approximately `-1.21e-8` per horizontal component for this precondition.

## Reachability and dependencies

`KeyboardInput.tick()` runs on the local client. A's `LivingEntity.aiStep()` applies the 0.98 factor before travel; B's local-player `applyInput()` override transfers the transformed input to the same movement fields before travel. `Entity.moveRelative()` / `getInputVector()` turns those fields into yaw-relative acceleration. The finding is limited to the keyboard diagonal/no-slowdown path; analog/controller input and crouch/item scaling remain separate open dependencies.

## Consequence and uncertainty

The source and direct float/double evaluation prove a small input-component difference. It predicts a correspondingly small difference in the horizontal acceleration input for a diagonal move. The later physics response and accumulated trajectory were not evaluated or runtime-tested. Exact release introduction is unknown within the endpoint interval.

## Handoff

Independent delta: diagonal keyboard input normalization precision. Related work: DEP-INPUT-SHAPE tracks alternate producers and sneak/item-scaling operation order. Implementation and testing decisions are deferred.
