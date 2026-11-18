# Current yaw precision applicability: 1.13 → 1.16.5

## Verdict

**Preliminary applicability is unresolved for the full 1.13–1.16.5 interval.** The accepted 1.12.2→1.13 boundary establishes a movement-relevant change from float camera-delta input to double camera-delta input while yaw fields remain floats. A bounded adjacent-source archive reports no change in direct current/prior yaw math across every edge from 1.13.1→1.13.2 through 1.16.4→1.16.5, and its 1.16.5→1.17 witness establishes a new float-first yaw update at 1.17. However, 1.13→1.13.1 remains pending in the continuity archive, so there is no closed, contiguous applicability proof from 1.13.

This is about the local current-yaw update. It excludes pitch clamping, acceleration formulas, input scheduling, and runtime parity.

## Frozen lower boundary and exact operation

The supplied 1.12.2→1.13 source report SHA-256 is f4a2802285822d900acd5b3e24fc2564659033e63c9c6d745b88c007822d5c02; its independent audit SHA-256 is a33479ee36bfdd27206f23db429eda352924eacf1ec555610e1e9234576423f5. Both supplied identities rehash as given. In 1.12.2, Entity.updateLocalPlayerCamera(float, float) computes (float)(yaw + delta * 0.15) and advances lastYaw using the float difference between the narrowed new and saved current yaw. In 1.13, the same-named method takes double cursor deltas, computes a double e = cursorDeltaX * 0.15, and independently narrows yaw + e and lastYaw + e to float. Yaw fields stay binary32 on both sides; neither method wraps or finite-checks current yaw. The independent audit bounds the movement relevance through the ordinary current-yaw relative-input transform and explicitly leaves physical mouse-event equivalence unresolved because caller scheduling and input widths differ.

The report's finite shared-intent witness yields different current float yaw values, and its fixed-kernel witness crosses adjacent trig-table entries. It is a method-boundary witness, not proof that the two mouse pipelines deliver the same event. This exact lower edge does not prove that all 1.13+ releases retain the operation.

## Continuity and upper witness

The continuity archive at c-yaw-continuity-evidence/development/research/source-evidence/20261118/block-c-local-yaw-precision-113-1165/README.md states that it contains fifteen accepted exact adjacent comparisons from a 16-edge assignment. It expressly lists **1.13→1.13.1 as the pending edge**. Its first closed edge is 1.13.1→1.13.2; its last is 1.16.4→1.16.5. The listed reports and verdicts bound the operation to ordinary local direct yaw updates and the first dry movement consumer; they do not close unrelated travel operations. Do not infer continuity across the missing first edge.

At the upper endpoint, the frozen 1.16.5→1.17 report says 1.16.5 computes double g = d * 0.15, then stores (float)(yRot + g) followed by (float)(yRotO + g). 1.17 first narrows d and multiplies in float (float g = (float)d * 0.15F), writes current yaw through setYRot, then adds to prior yaw in float. The report's finite witness crosses a live ordinary moveRelative sine-table index and changes horizontal velocity. This identifies 1.17 as the bounded upper transition for this source operation, subject to the separate audit and accepted identities in the supplied evidence bundle. It is independent of pitch and of the 1.13–1.16.5 acceleration math.

The bundle also contains the 1.16.5→26.2 direct-turn endpoint: 26.2 narrows the first double yaw argument before multiplying by 0.15F, and its ordinary movement consumer reads current yaw. Its fixed-26.2-trig witness changes both X and Z for finite direct-turn input, isolating yaw from the concurrent trig implementation change. That endpoint corroborates the modern witness but does not fill 1.13→1.13.1 or independently locate the cutover.

## 26.2 injection and numeric caveats

The exact checked source is decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/entity/Entity.java. turn(double xo, double yo) has JVM descriptor (DD)V on an instance method: this is slot 0, the original double yaw argument xo is slot 1 (occupying slots 1–2), and yo is slot 3 (slots 3–4). The first yaw setter operation is this.setYRot(this.getYRot() + yDelta) at source line 493, after float yDelta = (float)xo * 0.15F at line 491. A narrowly placed hook must read the original double argument, not infer it from the rounded yDelta, and must not alter the pitch setter/clamp path.

setYRot(float) rejects non-finite values at lines 3898–3903. The historical 1.16.5 turn writes current and prior yaw directly without that gate; 1.17 adds the current-yaw setter gate while prior-yaw addition remains unchecked. Neither historical or modern turn wraps/modulos yaw. The separate setRot path applies % 360.0F; it is not the ordinary local-turn operation. The supplied endpoint witnesses use finite inputs, so they do not rely on the non-finite gate.

## Existing product coverage and resolver reach

ChangeResolver chooses, per hook, the closest emulates version greater than or equal to the selected profile. Thus a V1_17 change is eligible for older selections and ineligible at V1_17 and later, unless a closer change for that same hook wins. ParkourVersion has distinct V1_13 and V1_13_1 entries; V1_13 covers only 1.13, while V1_13_1 aliases 1.13.1 and 1.13.2. V1_16_2 aliases 1.16.2–1.16.5.

Existing product code emulates separate movement-consumer operations: InputAcceleration at V1_13_1 restores the 1.13-era float input/rotation and velocity-add sequence; InputVectorNormalization at V1_16_2 handles the Vec3 normalization divisor and float yaw rotation. Neither alters how Entity.turn updates current and previous yaw from mouse input. No current product hook/mixin found by the scoped search dispatches the direct local turn yaw write. Those consumer changes cannot be treated as coverage of this turn operation.

## Minimal contract and blocker

If the adjacent boundary is closed, the minimal neutral operation contract for profiles 1.13–1.16.5 is an independently resolved current/prior yaw update receiving the entity's current yaw, prior yaw, and original double yaw delta. It computes double g = delta * 0.15, then narrows currentYaw + g and priorYaw + g independently to float, preserving current-then-prior write order. The mixin seam is the first yaw setter in Entity.turn; pitch and movement acceleration remain separate operations. This describes the 1.16.5 operation to retain against 1.17+ native behavior; it is not an implementation recommendation for older 1.12.x camera input, whose parameter/caller boundary is float.

**Blocker:** obtain and independently audit exact 1.13→1.13.1 source coverage for this same direct yaw operation and first ordinary consumer. Until that gate closes, do not claim a continuous 1.13–1.16.5 applicability range or implement a range-wide change. No product or source files were modified.
