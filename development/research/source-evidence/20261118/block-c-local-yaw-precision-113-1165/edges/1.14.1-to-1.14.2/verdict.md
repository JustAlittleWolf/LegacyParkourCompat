# Blinded verdict: direct local yaw update, 1.14.1 → 1.14.2

## Verdict

**No source-confirmed change in the assigned operation.** The `Entity.changeLookDirection(double, double)` implementation is text-identical in the exact Yarn decompilations for 1.14.1 and 1.14.2 (extracted method text SHA-256 on both sides: `b60fc591a02f1ba0c247846959450374fd91d48accf306e7c82218d26dec0914`). The candidate precision boundary is therefore not supported for this pair. This bounded verdict does not establish coverage of the whole pair or runtime parity.

## Exact evidence and behavior

- 1.14.1 Yarn: `decompiled_minecraft/1.14.1/yarn/net/minecraft/entity/Entity.java`, `changeLookDirection`, lines 313–324.
- 1.14.2 Yarn: `decompiled_minecraft/1.14.2/yarn/net/minecraft/entity/Entity.java`, `changeLookDirection`, lines 315–326.
- Both methods compute `f = e * 0.15` and `g = d * 0.15` as doubles, then write current pitch/yaw with `(float)(field + delta)`. Thus the current yaw addition is evaluated with double precision and narrowed to float afterward on both sides. Neither current yaw write is clamped or wrapped.
- Both then independently write `prevPitch = (float)(prevPitch + f)` and `prevYaw = (float)(prevYaw + g)`; previous yaw advances on every call with no separate gate. Only pitch is clamped. The sole conditional after these writes is `vehicle != null`, which triggers `vehicle.onPassengerLookAround(this)`; it does not guard either yaw write. The ordinary non-vehicle path therefore performs the same current and previous yaw updates on both releases.
- In both `Mouse.java` files, lines 213–214 call `client.player.changeLookDirection(j, k * n)` when `client.player != null`. This supplies the local player path; the call and gate are the same.

## First ordinary dry ground/air movement yaw read

For an unmounted player following ordinary movement, `LivingEntity.travel(Vec3d)` enters its non-fall-flying branch when the water/lava checks permit dry movement (the flying exceptions are unchanged). In 1.14.1, lines 1818–1822 sample support slipperiness, compute the ground/air factor, then call `updateVelocity(method_18802(v), arg)`; in 1.14.2 the corresponding lines are 1824–1828. Both grounded and airborne movement pass through this call. `Entity.updateVelocity` immediately calls `movementInputToVelocity(arg, f, this.yaw)` (1.14.1 lines 1009–1011; 1.14.2 lines 1011–1013), so the first direct yaw read in this ordinary dry path is **current** `this.yaw`; `prevYaw` is not used for movement projection.

The projection dependency is unchanged: `movementInputToVelocity` has the same body on both sides (method-text SHA-256 `c32fcbfbee1e36b31a2def97fc3f73ff6f27cbb38929b412c2e7302dc14fd147`). It gates tiny input with `lengthSquared() < 1.0E-7`, normalizes only above length-squared 1, multiplies the input by the acceleration factor, then computes sine/cosine from `g * (float)(PI / 180.0)` and rotates the horizontal components. `MathHelper.java` is byte-identical across the pair (same full-file SHA-256 below), including its sine/cosine table lookups.

Acceleration is a separate input to that projection and is not evidence of a yaw precision change. The same `LivingEntity.method_18802(float)` expression appears on both sides: grounded `getMovementSpeed() * (0.21600002F / (slipperiness * slipperiness * slipperiness))`, airborne `field_6281`. The on-ground branch also selects drag separately (`v * 0.91F` versus `0.91F` in air). Neither helper reads yaw.

## Nested source provenance

Both trees carry `.decompile-provenance.json`; manifest file SHA-256, exact client/mapping inputs, and the manifest-recorded source hashes are listed here. The paired manifests use pipeline `lpc-decompile-v1;threads=1;generic=true;ascii=true;synthetic=remove;indent=4;inner-warnings=false;runtime=false;prefix=net/minecraft,com/mojang;remap=ignoreConflicts,renameInvalidLocals,rebuildSourceFilenames,fixPackageAccess` under Eclipse Adoptium 25.0.3+9-LTS on Windows 11 amd64. Each listed manifest source hash was checked against the current file SHA-256.

| Release / mapping | Provenance manifest SHA-256 | Client input SHA-256 | Intermediary input SHA-256 | Yarn input SHA-256 |
|---|---|---|---|---|
| 1.14.1 / Yarn 1.14.1+build.10 | `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` | `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814` | `486f4e72a87d697c5d9e155a49158bec28117faa73e3f1201c3f8a616b42569d` | `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630` |
| 1.14.2 / Yarn 1.14.2+build.7 | `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` | `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25` | `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e` | `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b` |

Source hashes (manifest value = current file value):

| Relative source | 1.14.1 SHA-256 | 1.14.2 SHA-256 |
|---|---|---|
| `net/minecraft/client/Mouse.java` | `dedc0f972addc92ad335ea86210f71c4f3de56dcbebb81958cc509189b94d742` | `b3724c88b74c005d741259f89329b92b4ab3af7a3f44cd0bdf7ac0222733f243` |
| `net/minecraft/entity/Entity.java` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` |
| `net/minecraft/entity/LivingEntity.java` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` |
| `net/minecraft/util/math/MathHelper.java` | `21ed846ac48a5a2fbcdb2ac31fd5b74a461485effd881112c903c09d9297504a` | `21ed846ac48a5a2fbcdb2ac31fd5b74a461485effd881112c903c09d9297504a` |


## Phase-two comparison against frozen source report

The author report was verified before comparison at `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-yaw-precision-1141-1142-source\report.md`: SHA-256 `c39153365dec91fbb28c806e6af69ecd6b44050461916480f84d9b691dd499c0`, matching the authorized identity. Its SHA-256 was rechecked after comparison and remained the same.

I independently compared every paired source slice listed in that report, plus the Mouse caller and `PlayerEntity.travel` wrapper. The listed slice pairs were line-for-line identical, and the author’s bounded **no-difference** verdict is supported. I also verified the report’s `Vec3d.java` hash against both provenance manifests and current files. The local player wrapper calls `super.travel(arg)` on the ordinary non-flying route in both versions; the dry, non-fall-flying route then reaches the same `LivingEntity.travel` acceleration and current-yaw projection path described above.

One precision description needs narrowing: in `Entity.setPositionAnglesAndUpdate`, the expression `this.prevYaw - g` is assigned to a `double`, but both source operands are `float`, so Java performs the subtraction in `float` before widening the result. Immediately beforehand, `this.yaw = g` and `this.prevYaw = this.yaw`, so the operands are the same float; the delta is zero for finite `g` and NaN for non-finite `g`, leaving the `< -180.0` and `>= 180.0` adjustments unreachable in this method. This wording correction does not alter the report’s no-difference conclusion.

Prefix preservation: the original 5,694-byte verdict prefix was retained verbatim; its SHA-256 before this append was `c03097575a572ce2a0486f9d2b6002ef6862462b8358eb94e77bd6b7d6bad366` and was verified again after the append.