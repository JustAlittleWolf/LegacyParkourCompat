# 1.14.1 → 1.14.2: ordinary pitch turn response

## Bounded result

No source difference found in the direct ordinary `Entity.changeLookDirection(double, double)` pitch response. The Yarn-mapped bodies have the same signed inputs, numeric widths, expression order, casts, clamps, and old-rotation update order. This is a bounded no-difference disposition, not full-pair movement coverage.

## Exact source evidence

- 1.14.1, Yarn `1.14.1+build.10`: `decompiled_minecraft/1.14.1/yarn/net/minecraft/entity/Entity.java`, `changeLookDirection`, lines 313–325.
- 1.14.2, Yarn `1.14.2+build.7`: `decompiled_minecraft/1.14.2/yarn/net/minecraft/entity/Entity.java`, `changeLookDirection`, lines 315–327.

Both methods compute `double f = e * 0.15` and `double g = d * 0.15`; assign `pitch = (float)(pitch + f)` and `yaw = (float)(yaw + g)`; clamp only the new pitch to `[-90.0F, 90.0F]`; then add the same double deltas to `prevPitch` and `prevYaw`, cast each result to float, and clamp only `prevPitch`. The optional vehicle look callback is after these writes in both versions. There is no finite-value guard in this method; the ordinary witness below uses finite values.

A legal ordinary witness is `pitch=0.0F`, `prevPitch=0.0F`, `yaw=prevYaw=0.0F`, `d=0.0`, `e=1.0`, no vehicle. Both versions compute `f=0.15` as a double, cast the sum to `0.15F`, leave it inside the clamp, and produce `pitch=prevPitch=0.15F` (same float result). This confirms identical ordinary pitch response for the witness; it does not claim all possible inputs or pair coverage.

## First live Elytra movement read

In both versions, the client-reachable `LivingEntity.travel(Vec3d)` Elytra branch is guarded by voluntary/logical movement, water/lava conditions, and `isFallFlying()`. After obtaining velocity and potentially updating fall distance, it calls `getRotationVector()`; `Entity.getRotationVector()` passes the current `this.pitch` and `this.yaw` to the rotation-vector calculation. The branch next reads `this.pitch` again into the float radians local `l`. Sources: `LivingEntity.java` 1.14.1 lines 1763–1787 and 1.14.2 lines 1769–1793; `Entity.java` 1.14.1 lines 1635–1636 and 1.14.2 lines 1637–1638. The Elytra math/order in this slice is otherwise the same across the pair.

## Read-only source provenance

The buildSrc decompiler guide says the default source root is resolved from the primary checkout’s Git common directory. For this primary checkout, the canonical root is `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft`; the matching cache is `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache`. I consumed the published sources and cache read-only.

Each side has `.decompile-provenance.json`; the file SHA-256 and relevant entries are:

- 1.14.1 manifest SHA-256 `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907`. It pins Yarn `1.14.1+build.10` artifact SHA-256 `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`, intermediary artifact `486f4e72a87d697c5d9e155a49158bec28117faa73e3f1201c3f8a616b42569d`, and client artifact `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814`.
- 1.14.2 manifest SHA-256 `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a`. It pins Yarn `1.14.2+build.7` artifact SHA-256 `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b`, intermediary artifact `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e`, and client artifact `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25`.

The manifest records the decompiler pipeline and tool classpath; both record Vineflower `1.12.0` SHA-256 `1dfcfe974395734fa467ce620661c7623d05ba83670de0529b1fbd63ff548b9d` and Eclipse Adoptium Java `25.0.3+9-LTS` on Windows 11 x64. The source hashes in each manifest were independently checked against the corresponding files: `Entity.java` is `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` (1.14.1) and `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` (1.14.2); `LivingEntity.java` is `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` and `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78`, respectively. Cache `client.jar` hashes matched the manifest inputs and corresponding `.pin.json` entries.
