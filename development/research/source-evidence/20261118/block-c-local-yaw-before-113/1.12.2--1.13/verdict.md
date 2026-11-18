# Independent C audit: local yaw precision, 1.12.2 → 1.13

## Verdict

**Verified, with a narrow scope:** the stored `yaw`/`lastYaw` fields stay `float`, but the local camera update changes from float cursor deltas and a current-yaw-derived previous-yaw increment to double cursor deltas independently applied to current and previous yaw before each is rounded to float. This can change the current stored yaw for cursor deltas that fall on different sides of a float rounding boundary; the ordinary yaw-relative input transform then consumes that stored float. The previous-yaw update also changes, though `Entity.updateVelocity` itself reads current `yaw`, not `lastYaw`.

This does not establish that the complete movement/acceleration path is otherwise unchanged or that every mouse input produces a movement difference. In particular, the source callers and normal movement scale calculations changed across the pair; those effects are outside this isolated witness.

## Frozen source identity and byte checks

The compared pair is exactly **1.12.2 → 1.13**, both `ornithe-feather`; the newer release is literal `1.13`, not `1.13.1` or `1.13.2`.

- 1.12.2 provenance records namespace `ornithe-feather`, Feather gen2 build 2 mapping artifacts, Java 25.0.3, Vineflower 1.12.0, and exact `versionId: 1.12.2`. Its ready marker binds source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; the recorded verification reports all 2,050 published source files identical to its verification output.
- 1.13 provenance records `versionId` and `requestedVersionId` both `1.13`, resolved Feather gen2 build 2, the exact client metadata and input hashes, Java 25.0.3, and Vineflower 1.12.0. Its source-manifest SHA-256 is `0509c2614b0bb0e616e2eebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`, matching its ready marker.
- Before comparing behavior, I checked both manifest file hashes against those marker identities. Each cited Java source's SHA-256 also matches its entry in that release's source manifest:

| Release/source file | SHA-256 |
| --- | --- |
| 1.12.2 `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.12.2 `net/minecraft/client/render/GameRenderer.java` | `c6634f86b93421a4f1d05ab3df5bfdf8edef3a295190a3a06eba48a4cac874bb` |
| 1.12.2 `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.13 `net/minecraft/entity/Entity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` |
| 1.13 `net/minecraft/client/MouseHandler.java` | `b045dc9eaf5b5ab1d9f13dd795465a106f52571dda9e5c837f5c2bbbdfddf4ce` |
| 1.13 `net/minecraft/entity/living/LivingEntity.java` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` |

## Compared source behavior

- **Fields and direct camera update, 1.12.2:** `Entity.java`, lines 107–110 declares `yaw`, `pitch`, `lastYaw`, and `lastPitch` as `float`. Lines 307–314 expose `updateLocalPlayerCamera(float yaw, float pitch)`: it stores old current yaw in a float, computes current yaw as `(float)(this.yaw + yaw * 0.15)`, then updates `lastYaw` with `this.lastYaw + (this.yaw - g)`. The input multiplication is double because `0.15` is a double literal; the method parameter, current field, and current-minus-old increment are float.
- **Fields and direct camera update, 1.13:** `Entity.java`, lines 118–121 keeps the same four fields as `float`. Lines 318–326 expose `updateLocalPlayerCamera(double cursorDeltaX, double cursorDeltaY)`, compute double `e = cursorDeltaX * 0.15`, update current yaw as `(float)(this.yaw + e)`, and independently update previous yaw as `(float)(this.lastYaw + e)`. The pitch clamp and `lastPitch` clamp shown there are pitch behavior, not part of this yaw verdict.
- **Reachable local input callers:** 1.12.2 `GameRenderer.java`, lines 847–867 calculates sensitivity and mouse deltas in floats (`f`, `g`, `h`, `i`) and invokes the float method. 1.13 `MouseHandler.java`, lines 187–211 calculates `double` sensitivity/deltas and invokes the double method. These caller/scheduling changes support reachability and show why a whole-tick comparison cannot attribute every difference to the method alone.
- **Fixed yaw-relative movement witness:** both versions' `Entity.java` have the same `updateVelocity(float sideways, float forwards, float upwards, float scale)` operation (1.12.2 lines 1049–1067; 1.13 lines 1056–1074). It normalizes the local input vector, evaluates `sin` and `cos` from `this.yaw * (float)(Math.PI / 180.0)`, then applies `velocityX += sideways * cos - upwards * sin`, `velocityY += forwards`, and `velocityZ += upwards * cos + sideways * sin`, in that order. With the same already-converted yaw, input vector, scale, and initial velocity, this transform is unchanged. Thus a different current float yaw produced by the camera update can feed a different direction into the same normal dry-ground/air kernel.
- **Acceleration boundary:** ordinary dry movement calls this transform from `LivingEntity.moveRelative`; the ground scale is calculated from speed and friction, while air uses `speedInAir` (`1.12.2 LivingEntity.java`, lines 1478–1493; `1.13`, lines 1518–1534). Even those scale calculations are not identical: the shown ground factor changes from `0.16277136F` to `0.16277137F`. Hold `scale` fixed for the yaw witness. Do not fold this separate acceleration change, the changed mouse scheduling, or the rest of `moveRelative` into the yaw finding.

## Acceptance boundary

The direct local-camera precision/state-update difference is supported by exact, hash-checked source and is movement-relevant through current `yaw` in the common yaw-relative transform. The claim that yaw fields themselves widened is **not** supported; they remain binary32 floats on both sides. No claim is made here about other camera consumers, full tick scheduling parity, acceleration parity, an exact intermediate introduction patch, or runtime parity.

## Phase-two comparison to the blind source report

I rehashed the sole comparison input before reading it: `c-yaw-precision-1122-113-source/report.md`, 6,767 bytes, SHA-256 `f4a2802285822d900acd5b3e24fc2564659033e63c9c6d745b88c007822d5c02`. I rechecked that same hash after this comparison. The report supports the narrow verdict above; no conflicting method-boundary claim was found.

The report adds a concrete method-boundary witness with one shared double turn intent, showing that 1.12.2's float parameter and 1.13's double parameter can produce different current float yaw values. It correctly treats this as conditional precision evidence, not proof that the releases deliver the same physical mouse event: the caller input types and scheduling differ. Its fixed-kernel witness also addresses the remaining movement question by holding the yaw-relative updateVelocity inputs and scale fixed, and reports adjacent trig-table indices for the distinct current yaw values. This supports the movement-relevance claim while keeping the method-level input intent separate from end-to-end mouse parity.

The report also preserves the necessary physics qualification: `updateVelocity` is the isolated shared transform; the ground scale coefficient differs (`0.16277136F` versus `0.16277137F`), and no complete ground/air tick parity follows. The previous-yaw expression is independently changed, but the cited dry ground/air orientation kernel reads current `yaw`; the report does not attribute a movement effect to `lastYaw` alone.

Acceptance remains bounded to the direct camera yaw conversion/state update and the conditional fixed-kernel movement witness. It does not establish equal real-device input, whole-tick scheduling parity, full acceleration parity, a precise intermediate patch boundary, or runtime parity.