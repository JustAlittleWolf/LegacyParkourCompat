# Independent blind source audit — swim pitch vertical correction

## Pre-report source freeze (completed before opening the assigned lab report)

**Scope:** exact Mojmap decompilations 1.16.1 → 1.16.2; local active ordinary `Player`, nonpassenger; `Player.travel` swim pitch correction, its actual view-angle/LUT numeric path and gates/order, and the inherited water-travel call through `Entity.move`. Rendering, non-player physics, and unrelated state changes are outside this claim.

**Readiness/provenance identity:** both readiness markers say `status=ready`, exact IDs `1.16.1` and `1.16.2`, mapping `mojmap`, Vineflower, Java runtime 25, 3,572 and 3,516 source files respectively. The exact pair was generated in one run with `decompileMinecraft --versions=1.16.1,1.16.2 --mappings=mojmap --decompiler-heap=4G`, using Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1, Java 25.0.3+9-LTS and Gradle 9.7.1. Each ready marker's embedded source-manifest SHA matches the SHA-256 of the corresponding manifest, and each embedded artifact-manifest SHA matches the artifact-manifest file. Each relevant source file's SHA below matches its entry in the source manifest.

| Evidence | 1.16.1 SHA-256 | 1.16.2 SHA-256 |
|---|---|---|
| `mojmap.ready.json` | `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb` | `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214` |
| `mojmap.provenance.json` | `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98` | `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c` |
| `mojmap.sources.sha256` (embedded ready hash) | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` |
| `artifacts.sha256` (embedded ready hash) | `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` | `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108` |
| `movement-diagnostics.txt` (embedded ready hash) | `3a7c0831f5c6d4b5d3bc8aef2b7ef88f0c1ee7e3981379ea0181d77733a4a827` | `a14aaa94b80b2783eb8903f2eaff520c9f623067df4d376480ca87cd26768902` |
| Pinned client SHA from ready marker | `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef` | `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d` |

**Relied source hashes (all checked against the corresponding source-manifest row):**

| Source | 1.16.1 SHA-256 | 1.16.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/world/entity/Entity.java` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `net/minecraft/util/Mth.java` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |

## Frozen paired trace

**`Player.travel(Vec3)`** is at `Player.java:1380` (1.16.1) and `:1387` (1.16.2). The swim correction statements and their order are textually identical:

1. Capture starting `x`, `y`, `z` as doubles.
2. Enter only when `isSwimming() && !isPassenger()`.
3. Sample `g` as `this.getLookAngle().y` (`double`), then set `h` to `0.085` iff `g < -0.2`, otherwise `0.06` (both double literals).
4. Apply vertical adjustment only if `g <= 0.0 || jumping || !fluidStateAt(BlockPos(x, y + 1.0 - 0.1, z)).isEmpty()`. The block position uses the current position at this point; this state probe is after reading look angle and before delegating travel.
5. On that gate, sample current delta movement and assign `delta + (0.0, (g - delta.y) * h, 0.0)`. All correction arithmetic is double. No vertical adjustment is made on the complementary condition.
6. Next, the unchanged flying/nonpassenger branch may save current vertical velocity, change flying speed, call `super.travel`, restore vertical velocity at `savedY * 0.6`, restore flying speed, clear fall distance/shared flag; otherwise it calls `super.travel`. Movement statistics are checked afterward.

**Actual view-angle numerics:** `Entity.getLookAngle()` calls `calculateViewVector(this.xRot, this.yRot)`; it does not use interpolated rotations or a pre-normalized double vector. `calculateViewVector(float f, float g)` first evaluates `float h = f * (float)(Math.PI / 180.0)` and `float i = -g * (float)(Math.PI / 180.0)`. It obtains `j=Mth.cos(i)`, `k=Mth.sin(i)`, `l=Mth.cos(h)`, `m=Mth.sin(h)`, then calls `new Vec3(k * l, -m, j * l)`. These products and unary negation are float operations before each argument widens to the `Vec3` double components; in particular `getLookAngle().y` is the widened float `-m`.

`Mth.SIN` is a `float[65536]`, filled as `(float)Math.sin(ix * Math.PI * 2.0 / 65536.0)`. `sin(float f)` indexes `SIN[(int)(f * 10430.378F) & 65535]`; `cos(float f)` indexes `SIN[(int)(f * 10430.378F + 16384.0F) & 65535]`. Thus multiplication by the float constant, truncating float-to-int cast, and 16-bit mask all precede array lookup; lookup result is float. These bodies and the view-vector bodies are identical in the pair. The correction then widens/uses the sampled y as double for thresholds and delta arithmetic; no alternate `Math.sin`, extra cast of `g`, or LUT-index change appears in either paired slice.

**Inherited travel path and real movement call:** after `Player.travel` calls `super.travel(vec3)`, `LivingEntity.travel` proceeds only under `isEffectiveAi() || isControlledByLocalInstance()`. Its water branch requires `isInWater() && isAffectedByFluids() && !canStandOnFluid(fluidState.getType())`; before movement it computes slowdown/depth-strider and calls `moveRelative(g, vec3)`. It then calls `this.move(MoverType.SELF, this.getDeltaMovement())` with the current corrected velocity. After move it performs climbable vertical response, water drag, fluid-falling adjustment, and a horizontal-collision step-up probe. This order is the same in both releases. `Entity.move(MoverType, Vec3)` is the inherited implementation for the ordinary Player: `noPhysics` directly moves box/location; otherwise piston limiting is gated to PISTON, then stuck multiplier, edge backoff, `collide`, bounding-box/location update, collision flags/on-ground calculation, on-pos state/fall-damage handling, blocked-axis velocity zeroing, fall-on and step callbacks, then movement-noise/stat/sound handling. `Entity.move` is inherited (no `Player` or `LivingEntity` override declares this signature). Its collision/movement path executes the same order; there is one adjacent difference after the movement and post-move callbacks: 1.16.1 clears `isTouchingLava` immediately before `checkInsideBlocks()`, while 1.16.2 has no such reset. This is a separate Entity-level lava-state transition, not a pitch-correction change.

**Pre-report conclusion frozen:** no 1.16.1→1.16.2 change is present in the scoped swim pitch vertical correction, its view-angle/LUT numeric path, the swim or pitch-adjustment gates/order, or the applicable inherited water `move` path. This is an exact no-difference result for the scoped swim pitch correction and the water-travel caller through its movement/collision response. `Entity.move` has the separate lava-state delta described above; because it is an inherited `Entity` behavior, it is not inherently non-player-only, but it remains distinct from this correction. This does not close other Player movement inventory.

## Post-freeze reconciliation and independent verdict

The assigned report was opened only after the source trace above was recorded. Its supplied SHA-256 is verified as `f39bb4d7ac0ae20f24b667e71dc3b4331e8669aa9c198e3b90e5aaed9f7a2a45`. I independently extracted the paired method text and confirmed the report's exact results: `Player.travel(Vec3)` is identical at 1,289 characters with SHA-256 `cd4385e5f6986c16dedd181882c022c8acf8339538e3fb455761d24b64dfe60c` on both sides; `LivingEntity.travel(Vec3)` is identical at 6,220 characters with SHA-256 `39d6496758dd957db4c5dd06453db1f5e5341fafb4693b281fac86bc0b86765e` on both sides. The complete extracted `Entity.move(MoverType,Vec3)` differs only by the 1.16.1 line `this.isTouchingLava = false;` immediately before `checkInsideBlocks()`; that line is absent in 1.16.2. The state accessor further differs: 1.16.1 `setInLava()` sets the latch and `isInLava()` reads it; 1.16.2 `isInLava()` computes `!firstTick && fluidHeight.getDouble(FluidTags.LAVA) > 0.0`. `checkInsideBlocks()` continues to run in both versions.

I confirm the report's no-difference finding for the swim pitch correction, actual view-vector float/LUT casts and widening, gate/sample/order, and `LivingEntity.travel` water path. I classify the removed lava-latch reset as an adjacent, independent base-`Entity` state change. It is outside the pitch-correction claim; because ordinary players inherit `Entity.move`, the source does not establish that this lava-state behavior is non-player-only. Any acceptance or implementation of that adjacent state operation needs its own bounded claim and applicability review.
