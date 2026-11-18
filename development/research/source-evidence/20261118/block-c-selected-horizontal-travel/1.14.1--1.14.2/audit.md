# Independent source audit: 1.14.1 → 1.14.2 horizontal travel arithmetic

## Verdict

**No source difference found in the bounded operation.** For ordinary dry, unmounted, local-player travel with sprinting and swimming/flying disabled, the consumed-input acceleration, movement-speed selection, and horizontal ground/air drag are textually identical in the compared exact-version Yarn decompilations. This verdict covers only those operations and preconditions; it is not full-pair coverage or runtime-parity evidence.

## Evidence identity and integrity

- `decompiled_minecraft/1.14.1/yarn`, Yarn `1.14.1+build.10`; provenance manifest SHA-256 `308537C5ADEC3104C5CA243EB5010554F00978635ED74F0D7801C804DD4D4907`.
- `decompiled_minecraft/1.14.2/yarn`, Yarn `1.14.2+build.7`; provenance manifest SHA-256 `786546F99684423CC2563BE45C9C3334DFCC2C7DB58DDB74BA39CE403BD0869A`.
- Both provenance manifests identify the exact requested Minecraft release and Yarn artifact/build. The consumed source files were rehashed and match their respective manifest `sources` entries:
  - 1.14.1 `net/minecraft/entity/LivingEntity.java`: `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda`.
  - 1.14.2 `net/minecraft/entity/LivingEntity.java`: `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78`.
  - 1.14.1 `net/minecraft/entity/Entity.java`: `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b`.
  - 1.14.2 `net/minecraft/entity/Entity.java`: `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc`.
  - 1.14.1 `net/minecraft/entity/player/PlayerEntity.java`: `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319`.
  - 1.14.2 `net/minecraft/entity/player/PlayerEntity.java`: `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398`.

## Paired source trace

All references are relative to each version's `yarn` root.

1. `net/minecraft/entity/player/PlayerEntity.java`, `travel(Vec3d)`:
   - 1.14.1 lines 1362–1389; 1.14.2 lines 1362–1389.
   - The swimming adjustment is gated by `isSwimming() && !hasVehicle()`. The flying branch is gated by `abilities.flying && !hasVehicle()`. Under the assigned conditions neither applies and both versions call `super.travel(arg)`; the paired method text is identical.
2. `net/minecraft/entity/LivingEntity.java`, `travel(Vec3d)` ordinary dry branch:
   - 1.14.1 lines 1818–1844; 1.14.2 lines 1824–1850 (six-line offset from earlier code).
   - Both sample `v` from the block at `(x, boundingBox.minY - 1.0, z)`, then select `w = onGround ? v * 0.91F : 0.91F`. Both call `updateVelocity(method_18802(v), arg)`, apply `method_18801` to velocity, move, and then multiply horizontal velocity by `w` (vertical math is outside this audit). The paired text is identical.
3. `net/minecraft/entity/LivingEntity.java`, `method_18802(float)` and movement-speed accessors:
   - 1.14.1 lines 1938–1947; 1.14.2 lines 1944–1953.
   - Both select ground input acceleration as `getMovementSpeed() * (0.21600002F / (f * f * f))`; airborne acceleration is `field_6281`. Getter/setter code is unchanged.
4. `net/minecraft/entity/Entity.java`, `updateVelocity(float, Vec3d)` and `movementInputToVelocity(Vec3d, float, float)`:
   - 1.14.1 lines 1009–1024; 1.14.2 lines 1011–1026.
   - Both add the rotated input vector to current velocity. The helper uses the same `lengthSquared < 1.0E-7` early return, normalization only when squared length exceeds `1.0`, multiplication by the supplied float acceleration, float yaw radians, and sine/cosine rotation expressions. Paired text is identical.
5. `net/minecraft/entity/player/PlayerEntity.java`, `tickMovement()` movement-speed preparation:
   - 1.14.1 and 1.14.2 lines 493–503.
   - Both preserve the movement-speed attribute value with `setMovementSpeed((float) lv.getValue())`; set airborne `field_6281` to `0.02F`, adding the same sprint increment only when sprinting. In the assigned nonsprinting case the selected airborne value is `0.02F`. Paired text is identical.

Independent exact-line comparisons of the paired ranges above returned identical text for each compared slice. The relevant ground/air block coefficient, yaw and `onGround` state are treated as supplied inputs, as assigned.

## Limits

This audit does not cover input-axis production, sprinting, swimming or fluid travel, flight/fall-flying, vehicles, jumping and climbing, gravity/vertical thresholds, collision resolution/support/stepping, block-coefficient production, camera/setters, or any other tick stages. It does not establish which intermediate patch introduced behavior or whether the pair is fully covered.

## Phase 2 independent review

**Verdict: QUALIFIED ACCEPT** of the bounded no-difference conclusion and paired source evidence in the author report (exact author artifact: SHA-256 `1B65E78A28332F1DA464C0B05E03E396379AAC1F4238D08F604EDED9E856556A`, 4,829 bytes). Its exact provenance identities, verified source hashes, dry/non-flying travel branch, input transform, ground/air acceleration formula, horizontal drag, and scope limits agree with my independent trace. The newly included `PlayerEntity.getMovementSpeed()` override is identical at `PlayerEntity.java:1406–1408` on both sides and returns the movement-speed attribute value used by the grounded player path.

Qualification on tick order: `PlayerEntity.tickMovement()` calls `super.tickMovement()` at line 492 before writing `field_6281` and invoking `setMovementSpeed` at lines 498–503. The travel inside that super call therefore consumes the carried-in values; the writes prepare subsequent travel. In the stable nonsprinting case the airborne value remains `0.02F`, so this timing nuance does not alter the paired no-difference verdict. Read the author report's “preceding travel call” as referring to the carried-in field, not the value assigned later in that same method. This also narrows my blind audit's movement-speed note: the player ground selector dispatches to the `PlayerEntity.getMovementSpeed()` override rather than relying on the inherited stored field. Neither clarification identifies a 1.14.1–1.14.2 change.