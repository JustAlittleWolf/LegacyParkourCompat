# Ordinary local yaw and dry acceleration: 1.14.1 → 1.14.2

**Finding:** No difference in the assigned source slices. The scoped current/prior yaw precision, update order, finite gate, and first dry ground/air movement-acceleration path are identical in the exact decompiled Yarn trees. This closes only the assigned slices; it does not assert full-pair coverage or runtime parity.

## Source identity

Both trees are canonical read-only outputs under `decompiled_minecraft/` with `.decompile-provenance.json` manifests. For each tree, the manifest-published SHA-256 values for `Entity.java`, `LivingEntity.java`, `MathHelper.java`, and `Vec3d.java` match the files on disk.

- **1.14.1, Yarn 1.14.1+build.10:** client artifact `piston-data.mojang.com/v1/objects/55ba86ddcbc3579397f41910463ffd4056e1e523/client.jar` (SHA-256 `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814`); Yarn artifact SHA-256 `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`; manifest SHA-256 `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907`.
  - `Entity.java`: `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b`
  - `LivingEntity.java`: `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda`
- **1.14.2, Yarn 1.14.2+build.7:** client artifact `piston-data.mojang.com/v1/objects/ca6c5a139045967229975c0c0b7f93e78b4314c2/client.jar` (SHA-256 `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25`); Yarn artifact SHA-256 `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b`; manifest SHA-256 `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a`.
  - `Entity.java`: `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc`
  - `LivingEntity.java`: `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78`

The `MathHelper.java` SHA-256 is `21ed846ac48a5a2fbcdb2ac31fd5b74a461485effd881112c903c09d9297504a` on both sides; `Vec3d.java` SHA-256 is `c56acd6b7b03e1a05d01687c490b5533c53c1b97ba637f7492a2d6d1744b55dc` on both sides.

## Paired source slices

All listed slices were compared line-for-line; each pair is identical.

| Operation | 1.14.1 Yarn lines | 1.14.2 Yarn lines | Source and movement meaning |
|---|---:|---:|---|
| `Entity` yaw/pitch/current/prior fields | `Entity.java:118–121` | `Entity.java:120–123` | Both yaw and `prevYaw` are `float`. |
| `Entity.changeLookDirection` | `Entity.java:313–325` | `Entity.java:315–327` | Mouse deltas are multiplied as `double` by `0.15`; current yaw and prior yaw each add that delta and cast to `float`. Pitch clamping and current/prior write order match. |
| `Entity.setRotation` and `setPositionAnglesAndUpdate` | `Entity.java:299–302`, `1045–1068` | `Entity.java:301–304`, `1047–1070` | Rotation remains float `% 360.0F`. Position-angle update writes yaw, then initializes `prevYaw` from it, computes a `double` delta against the supplied float yaw, applies the same `< -180.0` / `>= 180.0` adjustments, then calls `setRotation`. |
| `Entity` tick prior-yaw copy and `getYaw(float)` | `Entity.java:345–351`, `1176–1178` | `Entity.java:347–353`, `1178–1180` | Tick copies current yaw to prior yaw; interpolation returns current yaw at `1.0F`, otherwise interpolates `prevYaw` to yaw. |
| Entity NBT yaw/pitch finite gate | `Entity.java:1381–1389` | `Entity.java:1383–1391` | Both gate position/rotation and subsequent tag reads on `Double.isFinite(yaw) && Double.isFinite(pitch)` after the same finite-position check. |
| `Entity.updateVelocity` and `movementInputToVelocity` | `Entity.java:1009–1024` | `Entity.java:1011–1026` | Acceleration uses **current** `this.yaw`; yaw is passed as float to float sine/cosine after the same float degree conversion. Input length normalization threshold, multiplication, trig evaluation order, and double-vector component expressions match. |
| First ordinary dry ground/air acceleration in `LivingEntity.travel` | `LivingEntity.java:1817–1844` | `LivingEntity.java:1823–1850` | The non-fall-flying dry branch chooses `this.onGround ? slipperiness * 0.91F : 0.91F`, calls `updateVelocity(method_18802(slipperiness), input)` before movement, and then applies the same later friction/gravity operations. |
| Ground/air acceleration selection, `method_18802` | `LivingEntity.java:1938–1940` | `LivingEntity.java:1944–1946` | Ground uses `getMovementSpeed() * (0.21600002F / (slipperiness³))`; air uses `field_6281`. Order, float width and ground predicate match. |

The local `PlayerEntity.travel` path delegates to `LivingEntity.travel` in both versions for the non-flying case; its dry, non-fall-flying branch therefore reaches the same first yaw-oriented `updateVelocity` call. The client-player update order is outside this bounded difference claim.

**Conclusion:** No lawful difference witness was found in the assigned ordinary local yaw precision/order/current/prior/finite-check or first dry ground/air acceleration slices for 1.14.1→1.14.2. No behavior change is supported by this comparison.

