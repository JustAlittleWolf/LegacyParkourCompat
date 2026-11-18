# Direct pitch response and active Elytra use: 1.14.2 → 1.14.3

## Exact source identity

Compared `1.14.2` Yarn `1.14.2+build.7` with `1.14.3` Yarn `1.14.3+build.13`. Canonical source roots are `decompiled_minecraft/1.14.2/yarn/` and `decompiled_minecraft/1.14.3/yarn/`. Their hidden `.decompile-provenance.json` manifests have SHA-256 `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` and `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`, respectively. Both record the same Vineflower 1.12.0/toolchain build identity and the `lpc-decompile-v1` single-thread pipeline.

The pinned source inputs in those manifests identify the exact client and mappings:

- `1.14.2`: client `client.jar` SHA-256 `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25`; intermediary 1.14.2-v2 artifact `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e`; Yarn 1.14.2+build.7-v2 artifact `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b`.
- `1.14.3`: client `client.jar` SHA-256 `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07`; intermediary 1.14.3-v2 artifact `f2e9990c00fa87ddc7158a95afb735a947ae26d1997b52916226e11c612542a9`; Yarn 1.14.3+build.13-v2 artifact `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`.

The corresponding local cache files were present: `.minecraft-decompile-cache/<version>/client-intermediary.jar` and `client-yarn.jar`. Their SHA-256 values are, respectively, `b733333c77a7319a023be7d8a03894ba3bdf81895c1840e3e214097281346128` and `9719335fde7c193e2bb2e7a3bc5c8c149a91040ffffced206700f6db88d980bd` for 1.14.2; `44fe2e80c60b4efa12cf762f066fe4060f9427c11de22e31c365886ccd3a93d7` and `90794427c7bb7b208fa0fbec62e60c2f26db79bd3250d204fa09345cb9353efa` for 1.14.3.

The relied-on source bytes match the manifest `sources` entries: `net/minecraft/entity/Entity.java` is `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` (1.14.2) and `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` (1.14.3); `net/minecraft/entity/LivingEntity.java` is `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` and `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b`, respectively.

## Direct look response and setters

In 1.14.2 `Entity.changeLookDirection(double,double)` (315–326) and 1.14.3 (314–325) have the same ordered math: multiply the pitch delta by `0.15` as a `double`; add it to `pitch` and cast the sum to `float`; clamp current pitch to `[-90.0F,90.0F]`; add the same double delta to `prevPitch` and cast to `float`; clamp `prevPitch`; then notify the vehicle if present. Yaw uses the same `0.15` scale and float cast, with no clamp in this method. There is no source-level pitch-response delta in this direct operation.

The adjacent first position setter is a type/name migration, not a changed width calculation. `Entity.setPosition(double,double,double)` uses `float width / 2.0F` and `float height`, then supplies `d-g`, `e`, `f-g`, `d+g`, `e+h`, `f+g` to the bounding-box constructor in both versions (1.14.2 lines 306–313; 1.14.3 lines 305–312). The stored field changes from `EntitySize size` to `EntityDimensions dimensions`. The corresponding size-refresh methods (`refreshSize`, 1.14.2 lines 2199–2216; `calculateDimensions`, 1.14.3 lines 2240–2257) retain the same narrower-box half-width calculation, wider-box expansion, float width subtraction and `Vec3d` conversion, and the server/first-update guard. The guard's `field_5953` is initialized `true` and cleared at line 386 in 1.14.2; `firstUpdate` is initialized `true` and cleared at line 385 in 1.14.3. This adjacent rename does not alter the direct pitch response.

The later `setPositionAnglesAndUpdate` path also retains operation order (1.14.2 lines 1048–1070; 1.14.3 lines 1092–1114): clamp supplied pitch first; store yaw and pitch; copy both into previous orientation; perform yaw wrap correction; set position; then call `setRotation`, whose assignments remain `% 360.0F`. The separate `setPositionAndAngles(double,double,double,float,float)` directly assigns yaw and pitch without this pitch clamp or previous-orientation copy in both releases (1.14.2 lines 1076–1089; 1.14.3 lines 1120–1133). No finite-value-only difference was found in these relevant setter paths.

## Reachable active Elytra witness

`LivingEntity.tickMovement` invokes `travel(...)` each tick (1.14.2 line 2210; 1.14.3 line 2220). In `travel`, after voluntary/logical-movement and water/lava gates, `isFallFlying()` selects the live Elytra branch (1.14.2 lines 1769–1809; 1.14.3 lines 1779–1819). That branch reads `this.pitch` in radians, uses `cos(pitch)` for the glide factor, and, when pitch is negative and horizontal look magnitude is positive, adds lift `r * 3.2` with `r = horizontalSpeed * -sin(pitch) * 0.04`. These formulas and guards are the same in both versions; the movement enum spelling changed from `field_6308` to `SELF`.

A lawful finite witness is initial `pitch = prevPitch = yaw = 0`, then direct `changeLookDirection(0.0, -2.0)`: the scaled pitch delta is the finite `double -0.3`, yielding finite `pitch = prevPitch = -0.3F` after the unchanged clamp. With active fall-flying, movement allowed, outside water and lava, yaw `0`, and finite velocity `(1,0,0)`, the rotation vector has positive horizontal magnitude and `l < 0`, so the live Elytra lift branch executes on the next `travel` tick. This verifies that the direct setter reaches active player movement without using NaN, infinity, an excluded rendering/device path, or a hypothetical state write. The witness is behaviorally reachable in both versions and establishes no 1.14.2→1.14.3 pitch operation delta.

## Bounded conclusion

For the assigned direct pitch-response, adjacent width/cast setter, orientation clamp/order and active Elytra use, the inspected exact sources show no movement-behavior difference between 1.14.2 and 1.14.3. This result is limited to those requested slices; it does not close the full release-pair movement inventory.
