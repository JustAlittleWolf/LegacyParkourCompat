# Blind source report: local yaw precision, 1.12.2 → 1.13

## Source identity and readiness

The compared release IDs are exactly **1.12.2** and **1.13**. Both source roots are `decompiled_minecraft/<release>/ornithe-feather/` and both ready markers report `mapping: ornithe-feather`.

| Release | Source identity | Readiness evidence |
| --- | --- | --- |
| 1.12.2 | `feather-gen2-1.12.2+build.2-mergedv2.jar` and `feather-gen2-1.12.2+build.2.tiny`; client SHA-256 `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f` | Ready marker says `versionId: 1.12.2`, 2,050 source files, source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; the current manifest has the same digest. Verification marker reports all 2,050 published source files identical, zero source-file differences, and no raw-input differences. Its one derived remapped-cache JAR difference is explicitly described as not affecting the identical published source tree. |
| 1.13 | `feather-gen2-1.13+build.2-mergedv2.jar` and `feather-gen2-1.13+build.2.tiny`; resolved coordinate `net.ornithemc:feather-gen2:1.13+build.2`; client SHA-256 `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` | Ready marker and provenance both identify `versionId: 1.13`; 2,702 source files; source-manifest SHA-256 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`; the current manifest has the same digest. The provenance batch also contains 1.13.1, but the selected output, ready marker, exact metadata ID, mapping coordinate, and source manifest are for **1.13**. No 1.13.1 or 1.13.2 source was substituted. |

The cited `Entity.java`, `LivingEntity.java`, `MathHelper.java`, and local-player source files on both sides were individually hashed and each digest matched its source-manifest entry. The caller files used to identify the input-pipeline boundary also matched: 1.12.2 `GameRenderer.java` SHA-256 `c6634f86b93421a4f1d05ab3df5bfdf8edef3a295190a3a06eba48a4cac874bb`; 1.13 `MouseHandler.java` SHA-256 `b045dc9eaf5b5ab1d9f13dd795465a106f52571dda9e5c837f5c2bbbdfddf4ce`.

## Direct local-yaw operation

1.12.2 `net/minecraft/entity/Entity.java`, `updateLocalPlayerCamera`, lines 307–315, accepts float cursor deltas, saves the old current yaw, then computes `yaw = (float)(yaw + delta * 0.15)`. The `0.15` literal is double, so the multiplication and sum are evaluated as double before narrowing the current yaw back to float. It clamps pitch only. It updates `lastYaw` afterward as `lastYaw + (newFloatYaw - savedFloatYaw)`, with the subtraction and addition in float arithmetic.

1.13 `net/minecraft/entity/Entity.java`, `updateLocalPlayerCamera`, lines 318–329, accepts double cursor deltas, computes a double yaw increment `e = cursorDeltaX * 0.15`, and narrows only the result of `this.yaw + e` to float. It then adds the unrounded double `e` directly to `lastYaw` and narrows that result to float. It additionally clamps `lastPitch`; neither version clamps or wraps current yaw here, nor checks it for finiteness. This method does not call the separate yaw-wrapping `setRotation` method.

With equal pre-conversion turn intent, a method-boundary witness shows a current-yaw precision delta. Let the initial current yaw be the float `-171.32066345214844` and the shared double intent be `-0.7700093678791946`. The old float parameter receives `-0.7700093388557434`; 1.12.2 stores current yaw `-171.4361572265625`, while 1.13 stores `-171.43617248535156`. With initial `lastYaw == yaw`, the corresponding `lastYaw` values also follow those respective narrowed yaw results. This is a constructed IEEE float/double witness to the local conversion boundary, not a claim that the two input systems deliver the same physical mouse event.

## Movement reachability and fixed-kernel witness

For the local player, both `LocalClientPlayerEntity.serverTickAi` implementations copy `input.movementSideways` and `input.movementForward` to `sidewaysSpeed` and `forwardSpeed` (1.12.2 and 1.13 lines 631–640). `LivingEntity.moveRelative` permits locally controlled entities (1.12.2 lines 1425–1428; 1.13 lines 1459–1469) and routes its non-water, non-lava, non-gliding branch to ordinary dry movement. Its ground branch chooses an acceleration scale from ground speed, and its airborne branch uses `speedInAir` (0.02F by default on both releases). `Entity.updateVelocity` reads the stored current `this.yaw` directly for both the sine and cosine transforms (1.12.2 `Entity.java` lines 1049–1065; 1.13 lines 1056–1073). Its arithmetic is otherwise identical across the pair.

The trig lookup is also identical: `MathHelper.sin/cos` use the same float multipliers and 65,536-entry table lookup on both sides (1.12.2 lines 15–20; 1.13 lines 24–29), and both table initializers fill entries with `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)` (1.12.2 line 428; 1.13 lines 11–15).

Holding the movement kernel fixed demonstrates a horizontal response. For the camera witness above, a pure sideways input of `1.0F`, zero forward/up input, zero initial horizontal velocity, and dry-air scale `0.02F` produce yaw lookup indices 34328/50712 in 1.12.2 and 34327/50711 in 1.13 (sine/cosine respectively). The source table formula makes those adjacent entries unequal. Therefore the same update-velocity kernel produces different X/Z increments before its move call. This is a source-expression calculation, not a runtime test. On dry ground the same kernel reads the changed current yaw, but 1.12.2's `0.16277136F` versus 1.13's `0.16277137F` ground-scale coefficient is a concurrent acceleration change; it is not attributed to yaw in this witness.

## Input-pipeline dependency and bounded disposition

The local method-level comparison holds one turn amount fixed before the old float-parameter narrowing. End-to-end mouse parity remains dependent on the excluded input pipeline: in 1.12.2 `GameRenderer.render` computes and forwards float `h`/`i` values (lines 852–867); 1.13 `MouseHandler` accumulates cursor movement and computes/forwards double `j`/`k` values in event-driven `turnPlayer` (lines 186–212). These are different scheduling and precision paths, so source alone does not establish that the physical cursor intent presented to `updateLocalPlayerCamera` is otherwise equal. Treat the local yaw conversion as a conditional movement-relevant candidate, with that input-boundary dependency explicit.

The `lastYaw` operation-order difference is independently source-confirmed, but movement orientation consumes current `yaw`; this source slice does not show `lastYaw` affecting dry ground/air relative movement. No broader 1.12.2 → 1.13 tick inventory or pair freeze is claimed by this bounded yaw report.
