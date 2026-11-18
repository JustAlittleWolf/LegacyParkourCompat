# Independent source audit: local pitch setter (1.12.2 → 1.13.0)

## Verdict

**Accept the core setter and movement-use claim, with a required correction to the source report’s 1.13 mouse-scaling description.** The exact setter bodies support the claimed type, sign, clamp, and previous-pitch update differences. The same finite setter argument can produce opposite clamped current pitches, and the stored current pitch reaches a live dry fall-flying travel calculation. One cited input-scale equality is false as written; it does not invalidate the setter witness or its movement consequence.

## Exact source readiness and integrity

The local ready markers report status `ready`, mapping `ornithe-feather`, and exact `versionId` values `1.12.2` and `1.13` (the latter is Minecraft 1.13.0, not 1.13.2). Their ready-marker source-manifest hashes match the actual manifest bytes:

- 1.12.2 source manifest: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; artifact manifest: `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`; client pin: `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`.
- 1.13 source manifest: `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`; artifact manifest: `a2c17075b601d9b83703f7ee188b04c6d9f68cac08d97b1a4f838fe8cd2e2c24`; diagnostics: `efb942576b5b4a3bcbf1e5ba1a53feb19e21c79d1b57e4ed429d52db2fadd4d3`; client pin: `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e`.

The 1.12.2 ready marker names the exact Feather Gen2 build.2 merged jar and Tiny; its artifact manifest contains the cited hashes `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca` and `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`. The 1.13 ready marker names Feather Gen2 `1.13+build.2`; its artifact manifest and provenance record both contain the cited merged-jar and Tiny hashes `a7a61604c7b365db1f4250c4c0079172c3beff37f4e16f7a53a13d77f3976897` and `7fa6b27aab7245c2d47ecbfe86ad9178272d7b1fd9ba69a482a41659387ffd84`. The 1.13 provenance file hash is `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d`.

I recomputed every source Java hash cited by the report and matched it both to the report and to its side’s `ornithe-feather.sources.sha256` entry. This includes both `Entity.java` files, both client mouse files, 1.12.2 `GameRenderer.java`, and both `LivingEntity.java` files. No 1.13.2 source was used.

## Setter audit

Exact source: `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/entity/Entity.java:307-317` and `decompiled_minecraft/1.13/ornithe-feather/net/minecraft/entity/Entity.java:318-329`.

- In 1.12.2, the method accepts float yaw/pitch deltas, snapshots current `pitch`, applies yaw, then evaluates `(float)(this.pitch - pitch * 0.15)`. The `0.15` literal is double, so the float argument is promoted for that multiply and the result cast back to float. Current pitch is clamped to `[-90.0F, 90.0F]`. Previous pitch is advanced by `this.pitch - oldPitch`, i.e. the realized clamped current change, without a setter clamp.
- In 1.13.0, the method accepts double cursor deltas, calculates `d = cursorDeltaY * 0.15` and `e = cursorDeltaX * 0.15`, adds `d`/`e` to current pitch/yaw and casts each result to float, clamps current pitch, then adds raw `d` to previous pitch, casts, and clamps previous pitch. The changed sign, parameter width, realized-versus-requested previous-pitch update, and previous-pitch clamp are all present in source and in the report.

A bounded, finite ordinary-input witness survives the input-scale correction below. With sensitivity 0.5, smooth camera off, inversion off, initial pitch/lastPitch 0, and signed setter pitch argument `+601`, the 1.12.2 mouse path produces its finite float pitch delta and the setter clamps current pitch to `-90`; the 1.13 path produces a finite double delta and the setter clamps current pitch to `+90`. Both mouse paths admit such an overrange signed delta before the setter clamps it. This fixes the setter argument so the witness isolates setter semantics; it does not assert that the same physical device motion has the same numeric/sign representation in LWJGL 2 and GLFW.

## Movement reachability and consequence

The direct current-pitch read is reached from the `LivingEntity.travel` call into `moveRelative` in both releases (`1.12.2` lines 1842-1848; `1.13` lines 1901-1908). For a locally controlled, unmounted player who is actively fall-flying in dry conditions, `moveRelative` passes the local-control, non-water, non-lava and `isFallFlying()` gates, calls `getLookVector()` (which directly reads `Entity.pitch` in both `Entity.java:1690-1692`), and then reads current pitch again to form radians (`1.12.2 LivingEntity.java:1434-1435`; `1.13 LivingEntity.java:1474-1475`). Thus this is a movement read of current pitch, not just a rendered/interpolated value.

For the witness above, the old clamped current pitch is -90 degrees and the newer one is +90 degrees. The old dry fall-flying branch therefore has negative pitch radians and enters its pitch-lift condition at `LivingEntity.java:1449`; with horizontal speed `k = 1`, its lift contribution is approximately `+0.128` to vertical velocity (`0.04 * 3.2`, before later movement multipliers). At +90 degrees the 1.13 `j < 0` pitch-lift condition at line 1489 is false, so that pitch-triggered term is absent. This quantifies a reachable, pitch-dependent movement response. It is not a claim that the total cross-version vertical-velocity delta is exactly 0.128: the Elytra travel equations also changed independently (for example, the newer branch’s gravity scalar and base expression at lines 1461-1464 and 1481), so total trajectories must not be attributed to the setter alone.

`lastPitch` is separately used for tick-delta interpolation (for example, 1.12.2 `Entity.java:1234` and 1.13 `Entity.java:1243`, and camera rendering). The cited travel path reads `pitch`; it does not consume `lastPitch` interpolation. The witness’s previous-pitch result confirms the setter’s separate state effect, but interpolation is not the reason for the movement witness.

## Required correction / limitation

The source report says the 1.13 `turnPlayer` scale local is a `float` and that at sensitivity 0.5 both releases have `f = 0.5` and `g = 1.0`. The exact 1.13 source at `MouseHandler.java:186-187` declares `double f`, while 1.13 `GameOptions.mouseSensitivity` is `double`. Because `0.6F` and `0.2F` are float literals promoted into the double expression, this is not exactly 0.5: `f` is about `0.5000000149011612`, and `g` is about `1.00000008940697`. The 1.12.2 locals are floats, and at the same setting its values are exactly `f = 0.5F`, `g = 1.0F`. The corrected 1.13 scale still makes the 601-delta witness overrange and does not change either clamped endpoint; revise the report’s type/equality claim.

The caller paths also differ in sampling representation: 1.12.2 samples integer `getDY()` in `Mouse.tick` then scales in `GameRenderer`; 1.13 accumulates cursor coordinate differences as doubles and scales in `MouseHandler.turnPlayer`. These input-path changes can alter the value delivered to the setter, but the setter witness fixes its argument and so is not evidence of device-event equivalence. Separately, the last-pitch interpolation state and the independent Elytra-equation changes must not be folded into the setter claim.

## Phase-two clarification: report identity and witness interpretation

The audited input report has SHA-256 `3985c7cbe4891f3643ee5054623950aec5fa4019164336e92c53d1b5da4fc0db`, matching the assigned immutable input.

The `+601` comparison is explicitly a shared **unnormalized signed numeric argument** at the local-camera setter: `pitch = +601` in 1.12.2 and `cursorDeltaY = +601` in 1.13.0, with inversion off. It is not a comparison of equal physical mouse orientation or equal native-device motion. With this same numeric sign, the setters produce opposite clamped pitches (-90° and +90°), which is the accepted source-level witness and yields the pitch-gate movement consequence described above. A pitch-direction-normalized comparison would need opposite-sign arguments because the setters have opposite signs; at the 601 overrange those arguments clamp to the same endpoint, so this particular movement witness would no longer demonstrate a current-pitch difference. The mapped sources do not establish enough native axis semantics to equate a particular physical device orientation across LWJGL 2 and GLFW. The report itself properly disclaims that inference.

The report's sensitivity statement is independently incorrect in its declared type and exact numeric claim: the 1.13 source local `f` is `double`, so its same-argument 601 witness remains valid but does not have the exact stated `f = 0.5`, `g = 1.0` scale. The correction is limited to input scaling; it does not change the shared-numeric-setter-argument interpretation above.
