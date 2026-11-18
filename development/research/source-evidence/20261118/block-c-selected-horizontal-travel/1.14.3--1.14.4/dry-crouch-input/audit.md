# Independent source audit: dry crouching horizontal input

## Bounded verdict

For the supplied ordinary crouching pose and ordinary logical keyboard input, the compared source has **no horizontal input production difference** between 1.14.3 and 1.14.4. Both keyboard implementations derive each axis as zero when opposing keys agree, otherwise `+1` for the positive direction or `-1` for the negative direction. With spectator false and either the crouching-pose argument or the raw sneak-key flag true, both multiply sideways first and forward second by `0.3`, converting each double product back to `float`. In the specified crouching case the pose argument alone is sufficient; the key flag can be either value.

The local-player copy and travel-argument path also preserve the resulting horizontal component order and apply the same `0.98F` speed-field multiplication before constructing the travel vector. This verdict concerns the supplied crouching state and bounded keyboard-to-travel input path; it does not establish that the differing pose predicates feeding that state are equivalent.

## Exact source identity and integrity

- Older: Minecraft `1.14.3`, Yarn `1.14.3+build.13` (intermediary 1.14.3). Source: `decompiled_minecraft/1.14.3/yarn/`. `.decompile-provenance.json` SHA-256: `81EA0021EB122D4E3340E20D47D6ECB7079365BA767A038D26A8264CDDACC28A`. Provenance pins the 1.14.3 client metadata and client jar, and Yarn artifact `yarn-1.14.3+build.13-v2.jar` (`ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`). Relevant source-file SHA-256 values matched the manifest: `KeyboardInput.java` `ade8c1b46b4b25888d326a963f645dd4fe82f53ef0797c2e499fe37e9bb47aa0`, `Input.java` `0a14d641cea95ecd6a836a2aecf0a7627dbf236460160a263a85b574a0216e4e`, `ClientPlayerEntity.java` `28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae`.
- Newer: Minecraft `1.14.4`, Mojmap. Source: `decompiled_minecraft/1.14.4/mojmap/`. Root `mojmap.ready.json` SHA-256: `EC7B6A6D9BA72F8A19908527977C970A8E2B3007B0E0B4323A725FB5943D3043`; root `mojmap.provenance.json` SHA-256: `9D1AD405E1E787D2FCA5ED1897E6797B1F3CD79527DA50D713232F46E2F30D97`. Ready marker identifies version `1.14.4`, mapping `mojmap`, 3,250 sources, and `mojmap.sources.sha256` whose SHA-256 is `AF98406F3D4ED31494FBDCE1078D1F3DF3F712501878F9A0E9AD5879BDF23C3B`; the source-file hashes below matched entries in that manifest. Provenance records `feather,mojmap` exact-run mapping selection and Feather build.2 inputs.

## Paired evidence

- `1.14.3`, Yarn, `net/minecraft/client/input/KeyboardInput.java:13-25`: reads forward/back/left/right booleans; sets `movementForward` and `movementSideways` by equal-key zero / positive-key `1` / negative-key `-1` ternaries; then stores jump and sneak flags. Gate is `!bl2 && (this.sneaking || bl)`. On the gate it assigns sideways as `(float)(movementSideways * 0.3)` and then forward as `(float)(movementForward * 0.3)`.
- `1.14.4`, Mojmap, `net/minecraft/client/player/KeyboardInput.java:13-25`: same assignments and ordering with Mojmap names (`up/down`, `left/right`, `forwardImpulse`, `leftImpulse`, `sneakKeyDown`). Gate is `!bl2 && (this.sneakKeyDown || bl)`. On the gate it assigns left impulse as `(float)(leftImpulse * 0.3)` and then forward impulse as `(float)(forwardImpulse * 0.3)`.
- `1.14.3`, Yarn, `net/minecraft/client/input/Input.java:5-24`: stores the axes as floats; `getMovementInput()` constructs `Vec2f(movementSideways, movementForward)`.
- `1.14.4`, Mojmap, `net/minecraft/client/player/Input.java:5-24`: stores the axes as floats; `getMoveVector()` constructs `Vec2(leftImpulse, forwardImpulse)`.
- `1.14.3`, Yarn, `net/minecraft/client/network/ClientPlayerEntity.java:607-619`: computes the input tick booleans, with `bl4 = isInSneakingPose() || shouldLeaveSwimmingPose()`, then calls `input.tick(bl4, isSpectator())`. `:588-599` copies the two input floats into `sidewaysSpeed` and `forwardSpeed` under `isCamera()`.
- `1.14.4`, Mojmap, `net/minecraft/client/player/LocalPlayer.java:618-630`: computes `bl4 = isVisuallySneaking() || isVisuallyCrawling()`, then calls `input.tick(bl4, isSpectator())`. `:600-610` copies the input floats into `xxa` and `zza` under `isControlledCamera()`.
- `1.14.3`, Yarn, `net/minecraft/entity/LivingEntity.java:2214-2221`: multiplies `sidewaysSpeed` and `forwardSpeed` by `0.98F`, then invokes travel with `new Vec3d(sidewaysSpeed, upwardSpeed, forwardSpeed)`.
- `1.14.4`, Mojmap, `net/minecraft/world/entity/LivingEntity.java:2217-2223`: multiplies `xxa` and `zza` by `0.98F`, then invokes travel with `new Vec3(xxa, yya, zza)`.

## Scope limits

Pose producers differ in spelling and composition (`isInSneakingPose() || shouldLeaveSwimmingPose()` versus `isVisuallySneaking() || isVisuallyCrawling()`). Their internals, pose selection and clearance are not adjudicated here; the crouching-pose boolean is treated as supplied. The spectator false gate is held fixed. The later item-use `0.2F` scaling is outside this ordinary case. Travel-body physics and vector normalization are not compared. No intermediate patch boundary or runtime trajectory is inferred.
## Independent review of author report

Report identity: `author/report.md`, SHA-256 `EA9F997DB217B1A29FE7AA3B5521EACC3BE870132AE7DE062AD6F63E451687EE`, 4,965 bytes.

Verdict: **ACCEPT** for the assigned bounded claim. I independently retraced the keyboard axis and slowdown branches, supplied crouch/spectator call arguments, local horizontal field copies, and `0.98F` travel-vector construction. I also rechecked all eight listed source-file SHA-256 values against the source bytes; they match. The report correctly limits its conclusion to the supplied ordinary crouching pose and excludes pose-predicate equivalence and travel-body behavior. No finding is warranted for this bounded slice.