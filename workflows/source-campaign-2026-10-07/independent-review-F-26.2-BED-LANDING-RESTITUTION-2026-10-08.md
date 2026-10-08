# Independent blind review: F-26.2-BED-LANDING-RESTITUTION

- Decision: **REQUEST CHANGES**
- Pair: 26.1.2 → 26.2; the pair remains `PARTIAL`.
- No implementation, other pair finding, wiki or MCPK material was read.

## Immutable snapshot binding

- Commit: `118cc0d89130c9b0709e71a4455cf8585031fb14`
- Path: `workflows/source-campaign-2026-10-07/26.1.2--26.2/findings/F-26.2-BED-LANDING-RESTITUTION.md`
- Blob: `ddeacbffb7db229000dc14af1d6805582f35a4f7`
- Raw SHA-256: `ce0a428abe3a3c66e15a90be9d289b41227d650616d1f8945f7e9e088b650fc5`

The 26.1.2 unobfuscated source/artifact manifest hashes are `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` / `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`; 26.2 hashes are `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` / `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`. Manifest bytes match the exact ready markers; all finding-cited Java/resource digests were checked against their bytes and manifests.

## Source review

The underlying delta is real for qualifying inputs. In 26.1.2, after collision resolution and effect-block selection, `Entity.move` dispatches to the bed's `updateEntityMovementAfterFallOn`; the living-entity callback uses `-movement.y * 0.66F`. In 26.2, the old callback dispatch is replaced with generic restitution after collision flags and the effect state are known. Beds register `bounceRestitution(0.75F)`, `BOUNCINESS` defaults to `0.0` and is in the living/player attributes, and the default suppression tag lists honey only. The local Player passes `canSimulateMovement`; its default unsuppressed bed landing reaches the new response. The bed surface remains the same pre-existing 9/16 collision shape in both endpoints. No modern-only block/state is needed.

The snapshot's numeric conclusion is not supported as written. It says that on a clipped landing the resolved vertical movement is zero, so `portionWithMovement = movement.y / currentMovement.y` is zero and B writes exactly `-currentMovement.y * 0.75`. In the cited 26.2 source, `movement` is the vector returned by `collide(delta)` and is then added to position before the restitution method runs. For a normal fall with a positive gap above the bed, a collision clips the downward distance to that remaining gap; `movement.y` is generally negative and nonzero. Therefore the portion, gravity compensation and `Mth.lerp` air-drag term are not generally zero/one. The current velocity is retained separately as `currentMovement`, so the two values cannot be treated as interchangeable.

## Concrete correction request

Replace the general assertion that every clipped landing has zero resolved Y movement. Bind a specific reachable bed-landing witness and calculate the actual `movement.y/currentMovement.y`, effective-gravity compensation and air-drag interpolation for it, or explicitly establish a reachable collision beginning exactly at the bed surface where the clipped Y displacement is zero. Then recompute the A/B output formulas for that witness. The registration/default/suppression and local-player evidence may remain; the bed restitution difference itself is plausible, but the immutable snapshot's asserted B result needs corrected, bounded arithmetic and a fresh review.

No gameplay trajectory was run. The finding's statement about damage is out of scope and was not evaluated.
