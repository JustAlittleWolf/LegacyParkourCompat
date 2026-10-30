# Independent source-only audit: 1.13.2 → 1.14.4 input witness

## Verdict

The one-Forward-tick witness reaches the SELF-movement resolver on both exact source endpoints, and its horizontal requests are comfortably larger than the fixed fixture's wall gap. The published horizontal values are correct to the stated precision. At the fixture, the 1.13.2 resolver applies the clipped nonzero remainder; 1.14.4 resolves it but skips shape/position update because the squared resolved vector is below its strict `1.0E-7` test.

Two entry-state qualifications are needed for the phrase “ordinary tick.” First, `LivingEntity.speed` is a cached field set by `PlayerEntity.mobTick()` *after* its superclass has run movement. A current `MOVEMENT_SPEED` attribute of `0.1F` alone does not establish the `getSpeed()` value used by this tick; the field must already have been cached by an earlier player tick. Second, a settled grounded cycle enters the tick with the previous tick's downward gravity residual (about `-0.0784` on Y), rather than zero Y velocity. The floor clips that request to zero before the cutoff is evaluated. These corrections leave the resolved horizontal witness unchanged.

The fixture's initial Z offset (`centerZ=0.6999`, width `0.6F`) remains an imposed start state from the permitted witness/query reports. The allowed evidence does not prove an ordinary preceding trajectory to that offset. This audit therefore accepts a same-tick input-to-resolver witness conditional on the fixture start, not a spawn-to-divergence trajectory.

## Independent trace

### Sampling and same-tick handoff

In both versions `LocalClientPlayerEntity.mobTick()` calls the keyboard input sampler before `super.mobTick()`. The ensuing `LivingEntity.mobTick()` calls virtual `serverTickAi()` before travel, and the local-player override copies `input.movementSideways` and `input.movementForward` there. Thus the sampled Forward value is copied into the movement speeds used by that same tick; there is no one-tick sampling lag in this route. References: 1.13.2 `LocalClientPlayerEntity.java:698–707,837`, `:635–645`; 1.14.4 `LocalClientPlayerEntity.java:625–636,773`, `:601–611`; `LivingEntity.java:1890–1926` and `:2191–2227` respectively.

The 1.13.2 keyboard sampler sets Forward to `1.0F` and only applies the `0.3` factor while sneaking (`KeyboardInput.java:13–49`). The 1.14.4 sampler also sets it to `1.0F`; slowdown requires the supplied pose/sneak condition (`KeyboardInput.java:13–25`). With the stated standing, non-sneaking, non-spectator fixture, both pass `1.0F` to local-player input. The local `isUsingItem()` branch's `0.2F` multiplier is inactive by the stated condition (`LocalClientPlayerEntity.java:704–707` and `:632–636`).

Living travel scales forward speed by `0.98F` before calling movement-relative travel (`LivingEntity.java:1921–1926` and `:2222–2227`). The resulting input component is the float `0.9800000190734863`; at yaw zero, the rotation maps forward to positive Z. With no prior horizontal velocity, the request is positive Z.

### Acceleration and cached speed

The report's two horizontal request values reproduce from the source's distinct float expressions:

| Endpoint | Ground coefficient expression | Float acceleration | Forward SELF Z request at yaw 0 |
|---|---|---:|---:|
| 1.13.2 | `getSpeed() * (0.16277137F / (t*t*t))`, `t=0.6F*0.91F` | `0.09999998658895493F` | `0.09799998998641968` |
| 1.14.4 | `getSpeed() * (0.21600002F / (v*v*v))`, `v=0.6F` | `0.10000000149011612F` | `0.09800000336766246` |

These follow 1.13.2 `LivingEntity.java:1540–1553`, `Entity.java:1057–1074`, and 1.14.4 `LivingEntity.java:1841–1846,1961–1971`, `Entity.java:1062–1076`. Stone's `0.6F` slipperiness and both formula evaluations give the values above; the 1.14.4 expression uses raw block slipperiness `v`, while its `0.91F` factor is applied to post-move velocity. The 1.13.2 formula uses the multiplied `t` for acceleration and recomputes it for post-move friction. Neither post-move friction expression scales the current SELF request.

There is a cache dependency behind both calculations: `LivingEntity.speed` is the field returned by `getSpeed()`. `PlayerEntity.mobTick()` sets it from the movement-speed attribute only after `super.mobTick()` completes movement (`PlayerEntity.java:479–492` in 1.13.2; `:507–520` in 1.14.4). The player attribute is initialized to `0.1F` (`PlayerEntity.java:156–160`; `:188–194`). Consequently, the witness needs the cached field already equal to `0.1F` on entry, as it would be after an earlier ordinary player tick with the default attribute; the attribute declaration by itself is not same-tick proof. On an otherwise fresh instance before that cache has been set, these formulas would use the field's initial zero and would not produce the reported request.

### Vertical entry state and collision result

The witness report's zero prior Y velocity is a valid explicit static fixture value, but it is not the entry value of a settled grounded movement cycle. On an ordinary grounded cycle, a downward SELF request is clipped by the floor, then dry travel applies gravity and `0.98F` vertical damping after movement (`LivingEntity.java:1575–1599` in 1.13.2; `:1846–1866` in 1.14.4). This leaves approximately `-0.0784` Y velocity for the following grounded tick. With no jump and `verticalSpeed=0`, the following SELF request therefore has negative Y as well as the positive Z request. The stone floor clips Y back to zero. The report should not describe prior vertical velocity as ordinary-grounded zero; its zero-Y figure is a static input simplification. No movement-order or threshold conclusion depends on that simplification.

For the stated start, the standing half-width is `0.30000001192092896`; at `centerZ=0.6999`, the positive footprint edge is about `0.9999000119209289`, leaving about `9.9988071E-5` to the wall at Z=1. Each calculated Z request exceeds that gap by about three orders of magnitude. The permitted query closure establishes the candidate wall and floor as ordinary loaded base full-cube blocks. The base shape/collision provider is `Block.getShape`/`getCollisionShape` (`Block.java:388–395` in 1.13.2; `:371–378` in 1.14.4). Both resolvers clip vertical motion before horizontal movement; 1.13.2 then clips X and Z (`Entity.java:578–599`), while the 1.14.4 helper clips the axes in the described resolver order (`Entity.java:656–685,708–738`). For this isolated positive-Z wall, both resolve Z to the same face gap. Squaring that gap gives about `9.9976E-9`.

The possible step-up does not improve horizontal displacement. Both LivingEntity constructors set `stepHeight=0.6F` (`LivingEntity.java:175–186`; `:196–208`). The ceiling at Y=3 leaves only `0.2` upward clearance from the initial top at `2.8`; raising the box by that amount still leaves its positive-volume vertical extent in the two-block wall spanning Y=1..3. The ordinary clipped route therefore has the same horizontal Z progress as the raised candidates. The 1.13.2 comparison restores the original route on a horizontal-distance tie (`Entity.java:601–672`); 1.14.4 returns a step result only for strictly greater horizontal progress (`Entity.java:669–685`). With autojump disabled in the client option, the local-player post-move callback is gated out (`LocalClientPlayerEntity.java:261,871–885` and `:254,848–860`).

Finally, 1.13.2 calls `setPositionFromShape()` after resolving any nonzero requested motion (`Entity.java:578–599,674–680`). In 1.14.4 the resolved movement is computed first, but shape and position are updated only if its squared distance is strictly greater than `1.0E-7` (`Entity.java:463–468`). The floor-clipped ordinary Y component is zero; the remaining horizontal gap still squares to about `9.9976E-9`. So the 1.13.2 position advances by the gap and the 1.14.4 position does not. The strict cutoff result survives the vertical-entry correction.

## Limits and input provenance

The exact initial position, block layout, and base-provider/collision-query assumptions are taken only from the permitted witness and query closure reports; the applicable full-cube shape and resolver math were checked in the exact source files below. The fixed initial offset remains unproved by prior ordinary input history. This audit confirms only the one-key same-tick resolver witness conditional on that offset and suitable ordinary cached speed state. It does not close arbitrary movement history, the pair-wide collision inventory, or runtime parity.

Permitted report input hashes:

- `collision-player-witness/witness-report.md`: `23e6248ea7d349a7e3761005456a59def2459c16224a90796e6c1c44bbc8601f`
- `collision-query/closure-report.md`: `30670ce15cba31f5dc7ced918ae9911dd68ace3e9b5fcb1b7b2230d91a872aa8`

Exact source-file SHA-256 values, relative to each canonical `ornithe-feather` root:

| Source file | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `net/minecraft/client/entity/living/player/KeyboardInput.java` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` | `5932453a9e48e7a798ae1be1cd3a4bf660b6b3be43bb7e5dc22686b3c4a82526` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` | `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` | `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df` |
| `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` |
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/shape/VoxelShapes.java` | `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95` | `51a09c297c7f01c8bad9b66af2a80900e95f33c6e3a250ce599f4817433587d9` |

No source generation, decompilation, build, test, game launch, or product implementation was performed. Only this audit folder was written.
