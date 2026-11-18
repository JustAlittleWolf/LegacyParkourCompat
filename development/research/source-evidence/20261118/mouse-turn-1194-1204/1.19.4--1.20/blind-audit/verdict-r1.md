# Independent verdict: comparison revision 1

## Bound inputs

- `source/comparison-r1.md` SHA-256: `ea497165a8f694b94ac524a08d46d632023a2c99d4ccbadd4f9dffd293beb2f1`.
- Original `source/comparison.md` SHA-256: `ad94e58bc818d4f6ce63e8f4f1dc40ce2a8ebc87ced76a94811e097ea400b47c`.
- Prior blind `frozen.md` SHA-256: `a3b09ac8ebdbe3a77bd64d584c4f23e413eef634ad952a3ddb9db5c4841f67e2`.
- Prior independent `verdict.md` SHA-256: `7d68e54f7cdefd6ef3776fd2c6a97cbcde873d57607d2db2e5537c2bec6b287c`.

The newly supplied revision hash matches the exact draft bytes. The original draft and previous verdict also retain the named hashes. This review is source bounded and does not infer pair-wide coverage or runtime parity.

## Correction and evidence review

The repaired `MouseHandler` path is now correctly spelled. Its 1.19.4 and 1.20 callback bodies remain identical on the bounded ordinary path. The scheduler references are accurate: each `Minecraft.tick()` calls `level.tickEntities()` at 1.19.4 line 1771 / 1.20 line 1759 when unpaused; `runTick(boolean)` calls `mouseHandler.turnPlayer()` after the timer-controlled client tick loop and before rendering at 1.19.4 line 1085 / 1.20 line 1075. The updated pitch therefore cannot affect any simulation ticks already run in that loop; the first later movement tick may read it. No claim is made about callback delivery or accumulation timing.

The 1.20 embedded provenance assertion is correct. Its `inputs` map contains exact `client.txt` mapping URL `https://piston-data.mojang.com/v1/objects/a4cd9a97400f7ecfe4dba23e427549ebc5815d66/client.txt` and SHA-256 `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de`. The consumed Java body digests stated in revision 1 match the exact per-file hashes in the 1.19.4 source manifest and 1.20 provenance. The previous freeze's contrary mapping-input caveat was erroneous and remains preserved only as frozen history; the previous verdict correctly corrected it.

`Entity.turn`, inherited virtual `Entity.setXRot`, and the bound player path remain correctly described. `LocalPlayer`, `Player`, and `LivingEntity` do not override `setXRot` in these sources. `Entity.turn` applies the virtual setter and current-pitch clamp in the same order in both versions. The source equivalence claim is correctly conditioned on same pre-turn state and same delta.

## Active flight and witness verdicts

### Elytra position and velocity witness — ACCEPT, BOUNDED

With the stated locally controlled, airborne, non-fluid Elytra state, water and lava checks do not take priority and both versions enter the `isFallFlying()` branch. For the same position, velocity, current pitch/yaw, environment, and collision outcome, the pre-move glide vector arithmetic and damping match. `Entity.getLookAngle()` in both releases directly calls `calculateViewVector(getXRot(), getYRot())`, so it uses current rotation. `Entity.move` receives the same vector. Under the draft's narrower no-movement-response witness (no collision, edge backoff, stuck multiplier, supporting contact or block response that alters movement), both add the same resolved movement to position, apply no horizontal collision clipping, and use the same block-speed factor. Accept equality of that move's position translation and outgoing velocity under this explicit witness, not arbitrary collision or contact outcomes. The new supporting-block, on-ground, and movement-emission logic in 1.20 may leave other entity fields/events different; revision 1 properly withholds full entity-state equivalence.

The fall-distance prelude condition is exactly `deltaMovement.y <= 0.0`, not all slow-falling travel. With positive Y and slow falling, neither version executes the gravity/reset block, so this prelude introduces no difference. At zero or negative Y with slow falling, both use gravity `0.01`; only 1.19.4 immediately writes `fallDistance = 0.0F`. That write is transiently different for nonzero incoming fall distance; if incoming fall distance is already zero, omitting the same-value reset alone yields no counter-state difference.

Same-tick downstream handling matters:

- On the accepted no-contact airborne Elytra witness, `Entity.move` does not take the grounded reset or fall-damage-resetting-block clip. If the move descends, its common `checkFallDamage` path adds the same downward distance after the prelude; this does not erase a difference from a nonzero incoming counter. `checkSlowFallDistance()` may clamp the 1.20 counter to `1.0F` when its Y and counter conditions hold, but does not generally reset it to zero. Therefore the fall-distance exit state can still differ while the witnessed position/velocity remains equal.
- If `Entity.move` lands, its `checkFallDamage` path resets the counter to zero in both releases. That makes the counter equal at method exit, but the differing pre-reset counter can be read by the landing/block fall response before the reset. Such contact behavior is outside the no-contact witness and not covered by its equivalence.
- In the separate abilities-flying wrapper, `Player.travel` resets fall distance after the superclass travel in both versions, so the prelude counter difference is overwritten before that wrapper returns. This supports equal final counter state in the stated ordinary creative-flight path, but does not establish equivalence for arbitrary contacts inside the delegated movement.

Revision 1's residual and bounded conclusion are sound. The explicit exit-state distinctions above close the same-tick overwrite question without widening its claim.

### Pitch-insensitive abilities flight — ACCEPT, BOUNDED

For the stated ordinary abilities-flight case (airborne, non-fluid, non-swimming, non-passenger, fall-flying false), the `Player.travel` branch is distinct from Elytra. `LocalPlayer.aiStep` vertical jump/shift input is based on ability state and `getFlyingSpeed`, not pitch. Its delegated ordinary `LivingEntity.travel` movement uses `Entity.moveRelative`, whose input rotation is `getYRot()` in both releases; pitch is not an input to that vector. Swimming is explicitly excluded because `Player.travel` can use `getLookAngle().y` there; Elytra is excluded because its glide vector depends on pitch. The revision supplies these necessary boundaries and its claim is accepted for the specified path.

## Verdict and residual gates

Accept the r1 corrections: callback citation repair, scheduler ordering, exact 1.20 mapping pin, same-state pitch equivalence, narrow Elytra position/velocity witness, and separately bounded pitch-insensitive abilities flight. Accept the prelude description with the precise `Y <= 0` gate and its counter-state qualification. Downstream grounded reset and creative-wrapper reset can overwrite the counter by exit, while pre-reset contact consumers may still observe it. The no-contact Elytra witness excludes those overwrites and retains a counter-state residual when incoming fall distance is nonzero.

Unresolved and outside this bounded audit: exact intermediate patch boundary; complete player movement inventory; general collision/contact-state equivalence; callback event acquisition timing; and runtime parity.